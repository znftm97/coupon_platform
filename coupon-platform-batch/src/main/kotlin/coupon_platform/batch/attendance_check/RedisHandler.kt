package coupon_platform.batch.attendance_check

import coupon_platform.infrastructure.account.AccountStoreImpl.Companion.ATTENDANCE_CHECK_BITOP_RESULT_KEY_TTL
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.redis.connection.RedisStringCommands
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

interface RedisHandler {
    fun bitopAndOperation(destKey: String, keys: Array<String>)
    fun get(destKey: String): BitSet
}

@Component
class LettuceHandler(
    private val redisTemplate: RedisTemplate<String, BitSet>,
): RedisHandler {

    companion object {
        const val BIT_AND_OPERATOR = "AND"
    }

    override fun bitopAndOperation(destKey: String, keys: Array<String>) {
        redisTemplate.execute { connection ->
            connection.stringCommands().bitOp(
                RedisStringCommands.BitOperation.valueOf(BIT_AND_OPERATOR),
                destKey.toByteArray(),
                *keys.map { it.toByteArray() }.toTypedArray()
            )
        }

        redisTemplate.expire(destKey, Duration.ofDays(ATTENDANCE_CHECK_BITOP_RESULT_KEY_TTL))
    }

    override fun get(destKey: String): BitSet {
        val bitSet: BitSet? = redisTemplate.opsForValue().get(destKey)
        return when (bitSet) {
            null -> throw EmptyResultDataAccessException(1)
            else -> bitSet
        }
    }
}