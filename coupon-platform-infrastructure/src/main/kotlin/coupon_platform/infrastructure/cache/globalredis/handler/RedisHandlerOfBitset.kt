package coupon_platform.infrastructure.cache.globalredis.handler

import coupon_platform.infrastructure.cache.globalredis.util.CacheConstants.ATTENDANCE_CHECK_BITOP_RESULT_KEY_TTL
import org.springframework.data.redis.connection.RedisStringCommands
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

interface RedisHandlerOfBitset {
    fun bitopAndCommand(destKey: String, keys: Array<String>)
    fun get(key: String): BitSet?
    fun set(key: String, value: BitSet, ttl: Duration)
}

@Component
class LettuceHandlerOfBitset(
    private val redisTemplate: RedisTemplate<String, BitSet>,
) : RedisHandlerOfBitset {

    companion object {
        const val BIT_AND_OPERATOR = "AND"
    }

    override fun bitopAndCommand(destKey: String, keys: Array<String>) {
        redisTemplate.execute { connection ->
            connection.stringCommands().bitOp(
                RedisStringCommands.BitOperation.valueOf(BIT_AND_OPERATOR),
                destKey.toByteArray(),
                *keys.map { it.toByteArray() }.toTypedArray(),
            )
        }

        redisTemplate.expire(destKey, Duration.ofDays(ATTENDANCE_CHECK_BITOP_RESULT_KEY_TTL))
    }

    override fun get(key: String): BitSet? = redisTemplate.opsForValue().get(key)

    override fun set(key: String, value: BitSet, ttl: Duration) = redisTemplate.opsForValue().set(key, value, ttl)
}