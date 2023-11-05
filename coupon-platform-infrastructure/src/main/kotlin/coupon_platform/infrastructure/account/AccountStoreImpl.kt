package coupon_platform.infrastructure.account

import coupon_platform.domain.account.AccountStore
import coupon_platform.domain.common.CommonConstants.MAX_ACCOUNTS_NUMBER
import coupon_platform.infrastructure.cache.global_redis.handler.RedisHandlerOfBitset
import coupon_platform.infrastructure.cache.global_redis.util.CacheConstants.ATTENDANCE_CHECK_BITOP_RESULT_KEY_TTL
import org.springframework.stereotype.Repository
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Repository
class AccountStoreImpl(
    private val redisHandler: RedisHandlerOfBitset,
) : AccountStore {

    companion object {
        const val ATTENDANCE_CHECK_PREFIX = "attendance:check:"
    }

    override fun attendance(userId: Long) {
        val key = generateKey()
        val findBitSet: BitSet = redisHandler.get(key) ?: BitSet(MAX_ACCOUNTS_NUMBER)
        findBitSet.set(userId.toInt(), true)

        redisHandler.set(
            key = key,
            value = findBitSet,
            ttl = Duration.ofDays(ATTENDANCE_CHECK_BITOP_RESULT_KEY_TTL)
        )
    }

    private fun generateKey(): String =
        ATTENDANCE_CHECK_PREFIX + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
}