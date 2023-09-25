package coupon_platform.infrastructure.account

import coupon_platform.domain.account.AccountStore
import coupon_platform.domain.common.CommonConstants.MAX_ACCOUNTS_NUMBER
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class AccountStoreImpl(
    val redisTemplate: RedisTemplate<String, BitSet>
) : AccountStore {

    companion object {
        const val ATTENDANCE_CHECK_PREFIX = "attendance:check:"
        const val ATTENDANCE_CHECK_BITOP_RESULT_KEY_TTL = 60L
    }

    override fun attendance(userId: Long) {
        val key = generateKey()
        val redis = redisTemplate.opsForValue()
        val findBitSet: BitSet? = redis.get(key)

        when (findBitSet?.isEmpty) {
            false -> findBitSet.set(userId.toInt(), true)
            else -> {
                val bitSet = BitSet(MAX_ACCOUNTS_NUMBER)
                bitSet.set(userId.toInt(), true)
                redis.set(key, bitSet, Duration.ofDays(ATTENDANCE_CHECK_BITOP_RESULT_KEY_TTL))
            }
        }
    }

    private fun generateKey() =
        ATTENDANCE_CHECK_PREFIX + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
}