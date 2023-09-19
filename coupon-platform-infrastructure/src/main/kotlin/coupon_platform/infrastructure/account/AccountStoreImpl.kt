package coupon_platform.infrastructure.account

import coupon_platform.domain.account.AccountStore
import coupon_platform.domain.common.CommonConstants.MAX_ACCOUNTS_NUMBER
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class AccountStoreImpl(
    val redissonClient: RedissonClient
) : AccountStore {

    companion object {
        const val ATTENDANCE_CHECK_PREFIX = "attendance:check:"
    }

    override fun attendance(userId: Long) {
        val key = generateKey()
        val bitSet = redissonClient.getBitSet(key)

        when (bitSet.isExists) {
            true -> bitSet.set(userId, true)
            else -> bitSet.set(BitSet(MAX_ACCOUNTS_NUMBER))
        }
    }

    private fun generateKey() =
        ATTENDANCE_CHECK_PREFIX + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
}