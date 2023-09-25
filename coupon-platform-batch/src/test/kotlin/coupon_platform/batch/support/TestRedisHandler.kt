package coupon_platform.batch.support

import coupon_platform.domain.common.CommonConstants
import org.springframework.data.redis.core.RedisTemplate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TestRedisHandler(
    private val redisTemplate: RedisTemplate<String, BitSet>,
) {

    /* accountIds에 들어있는 id를 가진 사용자는 checkDay일 동안 출석체크 하도록 세팅*/
    fun set(accountIds : List<Int>, checkDay: Long) {
        val bitSet = BitSet(CommonConstants.MAX_ACCOUNTS_NUMBER)
        accountIds.forEach {
            bitSet.set(it, true)
        }

        (0 until checkDay).forEach { idx ->
            val dateTime = LocalDateTime.now().minusDays(checkDay - idx - 1)
            val key = coupon_platform.batch.attendance_check.CommonConstants.ATTENDANCE_CHECK_PREFIX + dateTime.format(
                DateTimeFormatter.ofPattern("yyyyMMdd"))
            redisTemplate.opsForValue().set(key, bitSet)
        }
    }

    /*checkDay일 동안 연속으로 출석체크한 사용자가 없도록 세팅*/
    fun setNobodyAttendanceCheck(checkDay: Long){
        val bitSet = BitSet(CommonConstants.MAX_ACCOUNTS_NUMBER)

        (0 until checkDay).forEach { idx ->
            val dateTime = LocalDateTime.now().minusDays(checkDay - idx - 1)
            val key = coupon_platform.batch.attendance_check.CommonConstants.ATTENDANCE_CHECK_PREFIX + dateTime.format(
                DateTimeFormatter.ofPattern("yyyyMMdd"))
            redisTemplate.opsForValue().set(key, bitSet)
        }
    }

    fun delete(checkDay: Long) {
        (0 until checkDay).forEach { idx ->
            val dateTime = LocalDateTime.now().minusDays(checkDay - idx - 1)
            val key = coupon_platform.batch.attendance_check.CommonConstants.ATTENDANCE_CHECK_PREFIX + dateTime.format(
                DateTimeFormatter.ofPattern("yyyyMMdd"))
            redisTemplate.opsForValue().getAndDelete(key)
        }
    }
}