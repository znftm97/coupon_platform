package coupon_platform.infrastructure.account

import coupon_platform.infrastructure.account.AccountStoreImpl.Companion.ATTENDANCE_CHECK_PREFIX
import coupon_platform.infrastructure.support.BaseIntegrationTest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.data.redis.core.RedisTemplate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AccountIntegrationTest(
    private val accountStoreImpl: AccountStoreImpl,
    private val redisTemplate: RedisTemplate<String, BitSet>,
) : BaseIntegrationTest, BehaviorSpec({

    val key = ATTENDANCE_CHECK_PREFIX + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))

    given("accountId가 1인 사용자가") {
        val accountId = 1L

        `when`("로그인을 한 번 하면") {
            accountStoreImpl.attendance(accountId)
            val bitSet = redisTemplate.opsForValue().get(key)

            then("출석체크 처리가 되어야 한다.") {
                bitSet shouldNotBe null
                bitSet?.isEmpty shouldBe false
                bitSet?.get(accountId.toInt()) shouldBe true
                bitSet?.get(0) shouldBe false
                bitSet?.get(2) shouldBe false
            }
        }

        `when`("로그인을 두 번 해도") {
            accountStoreImpl.attendance(accountId)
            accountStoreImpl.attendance(accountId)
            val bitSet = redisTemplate.opsForValue().get(key)

            then("출석체크 처리가 되어야 한다.") {
                bitSet shouldNotBe null
                bitSet?.isEmpty shouldBe false
                bitSet?.get(accountId.toInt()) shouldBe true
                bitSet?.get(0) shouldBe false
                bitSet?.get(2) shouldBe false
            }
        }
    }

    afterTest {
        redisTemplate.opsForValue().getAndDelete(key)
    }
})