# coupon_platform
쿠폰과 관련된 다양한 기능들을 마음대로 구현해보기 위한 저장소<br>

[자세한 내용은 노션으로 문서화](https://languid-visage-6fe.notion.site/Wiki-967b2f3f21d14a7fb84f8bc9791a2f04?pvs=4)

# 기술 스택
- kotlin 1.8.22
- Spring Boot 3.1.3
- Spring Data JPA
- Spring Data Redis
- Spring Batch
- kotest, mockk
- MySQL 8.0.34
- Redis 7.0.10 , Lettuce 6.2.6

# 주요 기능
1. 코드입력을 통한 쿠폰 발급<br>
2. 출석체크 쿠폰 발급 이벤트<br> 

# 1. 코드입력을 통한 쿠폰 발급
[해당 기능에 대한 자세한 내용은 노션에 문서화](https://languid-visage-6fe.notion.site/6f5ee3a7208d4db3a7d42facfd8d814c?pvs=4)

## 1-1. 요구사항

난수를 사용하는 곳은 총 세군데다.
  1. **쿠폰 코드**
  2. **엔티티의 외부 노출 id**
  3. **요청 트래킹 id**

**[공통 조건]**
1. 난수는 중복되면 안된다.
    1. 분산 환경이어도 중복되서는 안된다.
2. 난수는 영문자, 숫자, '-' 로만 이루어져야 한다.

**[쿠폰 코드 조건]**
1. **쿠폰 코드**의 길이는 19글자여야 한다.
    1. ex) `xxxx-xxxx-xxxx-xxxx`
2. 사용자는 **쿠폰 코드**를 입력하면 쿠폰을 발급받는다.
3. 하나의 쿠폰당 하나의 **쿠폰 코드**만 가질 수 있다.
4. 동일한 사용자가, 동일한 **쿠폰 코드**를 여러번 입력해도, 한번만 발급되어야 한다.

**[엔티티의 외부 노출 id]**
- 외부 노출 id의 길이는 12 이어야한다.
- 정렬이 가능해야 한다.

**[요청 트래킹 id]**
- 난수의 길이는 12 이어야 한다.
    - `{prefix} + {난수}`
    - prefix는 쓰레드 id

## 1-2. 기술 선택
**[사용 가능한 기술]**
1. 티켓 서버
2. snowflake 서비스
3. UUID, ULID, TSID
   
**[쿠폰 코드, 요청 트래킹 id]**
- 각 19,16자리 길이 조건을 만족하고, 시간순으로 정렬할 필요가 없기 때문에 `UUID` 사용

**[외부 노출 id]**
- 12자리 길이 난수를 생성할 수 있고, 시간순으로 정렬할 수 있는 `TSID` 사용 

## 1-3. 클래스 다이어 그램
### 1-3-1. 기존 설계
![Untitled](https://github.com/znftm97/coupon_platform/assets/57134526/9112a3fc-257c-4d28-826b-44703690c921)
직접 `UUID.randomUUID()` 또는 `TSID.getTsid()`를 호출해서 사용해도 되지만, 객체지향적으로 구현하여 DIP 개념을 함께 활용했다.

### 1-3-2. 코루틴 사용으로 인한 설계 변경
![generator 클래스 다이어그램 수정 버전](https://github.com/znftm97/coupon_platform/assets/57134526/d0cbc2d3-26ce-46da-bf5f-bb14a5bdac4e)
TSID나 ULID 라이브러리는 시간순으로 정렬할 수 있는 난수를 생성한다. 같은 시간에 난수를 생성하는 경우가 존재할 수 있으니 내부적으로 synchronzied 키워드를 사용하고 있다. 이는 blocking 요소로 코루틴을 이용해 처리하고자 했고, 코루틴을 사용하려면 해당 함수는 suspend 함수여야 하지만 기존 설계한 인터페이스는 suspend 함수가 아니기 때문에, 설계를 변경해야 했다.
최종적으로 suspendable한 난수생성 인터페이스와, 그렇지 않은 난수생성 인터페이스로 분리했다. 현재는 1인터페이스-1구현체 구조이지만, 언제든지 여러 기술을 사용한 구현체들이 추가될 수 있고, 변경될 수 있는 유연한 구조라고 생각한다.

# 2. 출석체크 쿠폰 발급 이벤트
[해당 기능에 대한 자세한 내용은 노션에 문서화](https://languid-visage-6fe.notion.site/af71a9b1ae674fdaaaecd17967f45ca7?pvs=4)

## 2-1. 요구사항
1. **연속 출석체크시 쿠폰을 발급한다.**
    - 3일 연속 출석체크시 `3일 연속 출석체크 1000원 쿠폰` 발급
    - 7일 연속 출석체크시 `7일 연속 출석체크 5000원 쿠폰` 발급
    - 30일 연속 출석체크시 `30일 연속 출석체크 50000원 쿠폰` 발급
2. **로그인하면 출석체크된다고 가정하지만, 로그인 기능은 구현하지 않는다.**
    - 로그인 기능을 대신할 임시 API를 통해 출석체크 기능을 구현한다.
3. **출석체크 쿠폰 발급 이벤트 기간동안에는, 계속해서 참여할 수 있다.**
    - 예로 4일 연속 출석체크 후, 5일째 출석체크를 하지 않고, 6일째 다시 출석체크를 하는경우
    처음부터 다시 출석체크 이벤트에  참여하게 된다.
4. **각 쿠폰은 중복 발급되지 않는다.**
    - 예로 6일 연속 출석체크해도, `3일 연속 출석체크 쿠폰`은 3일째 한번만 발급된다.
    - 단, 연속성이 깨지면 즉 처음부터(1일차) 다시 출석체크를 하는 경우에는 쿠폰이 발급된다.
5. **애플리케이션 서버는 여러개로, 분산 환경이라고 가정한다.**
6. **해당 서비스에 가입한 사용자 수는 1000만명이라고 가정한다.**

## 2-2. 설계
### 2-2-1. Redis의 BITOP 명령어

[Redis Blog](https://redis.com/blog/bits-and-bats/)에서 얻은 아이디어를 기반으로 구현해보고자 한다.

Redis의 비트 연산자를 정확히는 `BITOP` 명령어를 이용하는 방법이다.

`[key,value]`를 `[prefix:날짜,bit]`로 구성한다.

![keyvalue](https://github.com/znftm97/coupon_platform/assets/57134526/8867ef51-abe4-4ede-b7cf-1eb596b026d4)

### 2-2-2. 동작 과정

1. **id가 1인 사용자가 2023-09-17에 출석체크를 하게 되면 1번째 offset의 bit를 1로 만든다.**
   
    ![Blank_diagram_(1)](https://github.com/znftm97/coupon_platform/assets/57134526/d1de8229-111d-4074-aae6-8360c28d7100)

2. **id가 5인 사용자가 2023-09-17에 출석체크를 하게 되면 5번째 offset의 bit를 1로 만든다.**
    
    ![Blank_diagram_(2)](https://github.com/znftm97/coupon_platform/assets/57134526/f392b192-2792-446f-bec1-2bd34f22cb81)
    
3. **id가 1인 사용자가 3일 연속 방문해서 데이터가 쌓이게 되면 아래와 같아진다.**
   
    ![Blank_diagram](https://github.com/znftm97/coupon_platform/assets/57134526/885a43a9-eb39-40cd-b0c9-382b21e9a054)

4. **3개의 key에 대해 `BITOP` 명령어를 통해 `AND` 연산을 하면 아래와 같은 결과가 나온다.**
   
    ![asd](https://github.com/znftm97/coupon_platform/assets/57134526/36feee6b-4c6c-4c75-a0bd-baf7ad872d48)

    ```sql
    BITOP AND destkey attendance:check:20230917 attendance:check:20230918 attendance:check:20230919
    ```
    
5. **`BITOP` 명령어 결과를 애플리케이션에서 몇 번째 offset이 1로 되어있는지 계산**
6. **offset이 곧, 사용자의 id가 되므로 offset이 1인 사용자에게 쿠폰 발급**

### 2-2-3. 연속 출석 체크를 계산하는 시기는?

1. **로그인할 때 마다, 즉 출석체크 할 때마다 x일 연속 출석체크했는지 확인하는 방법**
    - 하루동안 사용자가 계속 로그아웃, 로그인을 반복하면, 출석체크 확인을 반복해야 한다.
        - `BITOP` 명령어는 `O(N)` 시간복잡도를 가지기 때문에, 명령어 사용을 최소화 해야한다.
    - 이를 방지하려면 DB 어딘가에 출석체크 확인을 했다는 데이터를 저장해야 하는데, 로그인할 때마다 DB를 한번 더 확인해야 하는 오버헤드가 발생하기 때문에 좋은 방법은 아닌듯 하다.
2. **매일 한번씩 배치 도는 방법**
    - 정해진 시간에 배치를 돌리면, 하루에 한번만 확인하면 된다.

### 2-2-4. 전체적인 플로우

![%EC%8B%9C%ED%80%80%EC%8A%A4_%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8_(1)](https://github.com/znftm97/coupon_platform/assets/57134526/f6eb079e-029f-4635-b2f3-3e6377cafdda)

## 2-3. 구현

### 2-3-1. 로그인 기능(출석체크)

```kotlin
@Component
class AccountStoreImpl(
    private val redisHandler: RedisHandler,
) : AccountStore {

    companion object {
        const val ATTENDANCE_CHECK_PREFIX = "attendance:check:"
        const val ATTENDANCE_CHECK_BITOP_RESULT_KEY_TTL = 60L
    }

    override fun attendance(userId: Long) {
        val key = generateKey()
        val findBitSet: BitSet = redisHandler.get(key) ?: BitSet(MAX_ACCOUNTS_NUMBER)
        findBitSet.set(userId.toInt(), true)

        redisHandler.set(
            key,
            findBitSet,
            Duration.ofDays(ATTENDANCE_CHECK_BITOP_RESULT_KEY_TTL)
        )
    }

    private fun generateKey(): String =
        ATTENDANCE_CHECK_PREFIX + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
}
```

- key가 존재하지 않으면(당일 처음 로그인하는 사용자인 경우) 새 Bitset을 생성하고, `userId`값을 `offset`으로 넣어서 `1(true)`로 세팅한다.
- key가 존재하면 조회한 Bitset에 `userId`값을 `offset`으로 넣어서 `1(true)`로 세팅한다.

### 2-3-2. 출석체크 확인하는 배치

![a](https://github.com/znftm97/coupon_platform/assets/57134526/31d45330-b5f5-457c-91b0-34744c32c1b7)

**Job**

- 3일, 7일, 30일 Job이 따로 있고, 3일을 기준으로 설명한다.

**Step**

- **Step - Bitop**
    - Redis의 `Bitop`명령어을 수행하는 Step이다.
- **Step - 출석체크 확인**
    - 3일 연속 출석체크한 사용자를 조회해서, 쿠폰을 발급해주는 Step이다.

**Tasklet**

- **Tasklet - Bitop**
    - 오늘이 2023-09-25일이라면, 3일치 [09-23, 09-24, 09-25] key들에 대해 bit and 연산을 수행한다.
    - 연산을 수행한 결과가 지정한key:결과값 형태로 저장된다.
- **Tasklet - 출석체크 확인**
    - 처리해야 할 과정이 read-process-write로 딱 떨어져서 chunck 처리를 하려 했다.
    - 하지만 읽어야 할 데이터는 Redis의 `Bitop` 명령어 수행 결과값 하나 뿐이고, 여러명의 사용자에게 쿠폰을 발급한다.
        - 즉 reader와 writer의 `chunk size`가 다르다. 이를 해결하기 위해서는 별도의 구현이 필요한데 복잡해질 가능성이 크고, 조회 데이터는 1개 뿐이라 Tasklet으로 구현했다.

**Worker**

- **read**
    - Redis에서 `Bitop` 명령어 수행 결과를 조회한다.
- **process**
    - 비트 데이터를 기반으로 3일 연속 출석체크한 사용자의 ID 값을 추출한다.
- **write**
    - 추출된 사용자들에게 모두 쿠폰을 발급한다. (RDB에 발급한 쿠폰 저장)

**KeyGenerator**

- `Bitop` 명령어 사용시 저장될 key를 지정해야 하는데, 이 때 key를 생성하는 역할
- `Bitop` 명령어 수행 결과를 조회할 key를 생성하는 역할

**RedisHandler**

- Redis에 `Bitop` 명령어를 수행하거나, 데이터를 저장하고 조회하는 등, Redis 관련 작업을 처리하는 핸들러
- **LettuceHandler**
    - 여러 Redis 자바 클라이언트 중 `Lettuce`를 사용했고 `redisson`, `jedis`등 얼마든지 다른 라이브러리로 변경가능하므로 추상화했다.
 
## 2-4 요구사항 확장해보기
[자세한 내용은 노션으로 문서화](https://languid-visage-6fe.notion.site/6e4684a0dd4f41688d3533ff175682ce?pvs=4)

- **기존 요구사항**
    - 해당 서비스에 가입한 사용자 수는 **1000만명**이라고 가정한다.
- **변경된 요구사항**
    - 서비스가 흥행해서, 가입한 사용자 수가 **1억명**으로 늘어났다.

### 2-4-1. 개선 할 수 있는 부분 고민해보기
#### 1. **N일 출석체크 여부 확인 과정**
- N일 연속 출석체크 여부를 확인하는 과정은, 마찬가지로 N개의 비트데이터에 대해 한 번의 `BITOP` 명령어를 수행하고, 결과데이터를 하나만 조회하면 된다. 이 과정은 단순하다.
- 이후 결과데이터(bit 데이터)에서 bit값이 1인 사용자를 필터링 해야하는데, 1억명이라면, BitSet size가 1억이 된다. 즉 1억개의 element를 순회해야 하는데, **이때 Kotlin의 sequence를 이용해서 개선할 수 있다.**
- 사실 element 개수가 많을 뿐이지, 중간에 체이닝된 함수가 많지도 않고, `limit()`이나 `take()`로 인해 쇼트서킷이 되지 않는데 효과가 있을까 싶었는데, 간단히 테스트해보니 꽤 큰 차이(약 2초차이)가 있었다.
    
#### 2. **쿠폰을 발급하는 과정 - write**
- 전체 가입자 1억명 중 10%인 1000만명이 출석체크 이벤트에 참여해서, 1000만명에게 쿠폰을 발급해야 한다면?
- 현재는 1개씩 쿠폰 발급하면서, 1번씩 insert가 발생한다. 그러면 총 1000만번의 insert가 발생한다. → **batch insert를 통해 개선할 수 있다.**
    
#### 3. **insert 과정 병렬 또는 멀티스레드 처리**
Spring Batch에서 병렬 또는 멀티스레드 처리를 지원해준다. 문제는 chunk로 처리했을 때 가능하다. 
여기서는 간단히 Tasklet으로 처리했기 때문에, chunk로 처리하도록 변경이 필요하다.
- 문제는 데이터를 읽는 개수와, 데이터를 쓰는 개수가 달라서, 별도의 처리가 필요할 듯 하다.

게다가 병렬, 멀티스레드 이런 처리는 테스트, 디버깅, 트러블 슈팅이 쉽지 않다. 구현의 복잡도도 고려해야 하고, 코드의 가독성에도 영향을 미칠 것 같다. 
그러므로 정말로 성능에 문제가 있을 때 적용해야 한다는 것인데, 현재 배치 작업은 하루에 한번만 수행하면 된다. 즉 batch insert 만으로 충분할 듯 하다.

#### 4. **이벤트 참여한 사용자를 구할 때, 병렬처리**
1억개니까, 예로 쿼드코어라고 하면 2500만개씩 chunk로 쪼개서, 병렬처리한다면, 더 빠르게 처리할 수 있을 듯 하다. 하지만 위에서 이야기했듯이 병렬처리는 최후의 수단 느낌으로 생각해야 한다.

#### 5. **lua script로 이벤트 참여자 사용자 계산?**
애플리케이션에서 이벤트 참여한 사용자를 구하지 않고, redis에서 lua script로 계산하기면 어떨까?
[Redis docs](https://redis.io/commands/setbit/)에서 "`GETRANGE`및 `SETRANGE`문자열 명령을 사용하여 비트맵의 비트 오프셋 범위에 효율
적으로 액세스할 수도 있습니다.” 라고 설명한다. 그리고 lua script의 샘플을 보여주는데, 이를 통해 Redis에서 계산이 가능하다면, 애플리케이션에서 계산하는 것 보다 더 빠르게 처리할 수 있까?
애플리케이션 메모리에서 계산하나, Redis 메모리에서 계산하나 큰 차이 없을 것 같긴 하다.

#### 6. **이벤트 참여한 사용자를 구할 때, 1로 표시된 첫 번째 offset 부터 계산하기**
`BITPOS` 명령어를 통해 1로 표시된 첫 번째 offset값을 구할 수 있다. 이를 통해, 예로 0~5천번 사용자는, 서비스를 이용안한지 오래되어 이벤트 참여를 안해서 다 0이고 5001번부터 1인 경우에는, 앞의 5000개 데이터는 무시할 수 있다. 
근데 주의해야 하는점은 CPU 연산이 줄어들었고, Redis에 `BITPOS` 명령어를 날리기 때문에 네트워크 비용이 늘어났다. 즉 오히려 응답시간이 늘어나기 때문에, 클라이언트에게 응답하는 API 라면 역효과라고 생각한다.
하지만 API가 아니고, 하루에 한번씩 수행하는 배치 작업이므로, 불필요한 CPU 연산을 줄이는게 더 나은 방향 같다. <br><br>

#### * 개선점을 고민해보는 것 자체로 유의미하다고 생각하고 위 1,2번 방법만 적용했다. batch insert는 JPA, MyBatis, Exposed, JDBC 등 어떤 기술을 통해 batch insert를 구현할지 고민했고, 가장 적합하다고 생각한 JDBC를 이용했다.
