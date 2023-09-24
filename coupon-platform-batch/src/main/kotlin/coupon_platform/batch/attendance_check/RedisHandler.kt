package coupon_platform.batch.attendance_check

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.redis.connection.RedisStringCommands
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.*

interface RedisHandler {
    fun bitopAndOperation(destKey: String, keys: Array<String>)
    fun get(destKey: String): BitSet?
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
            val destKeyBytes = destKey.toByteArray()
            val keysBytes = keys.map { it.toByteArray() }.toTypedArray()

            connection.stringCommands().bitOp(
                RedisStringCommands.BitOperation.valueOf(BIT_AND_OPERATOR),
                destKeyBytes,
                *keysBytes
            )
        }
    }

    override fun get(destKey: String): BitSet {
        val bitSet: BitSet? = redisTemplate.opsForValue().get(destKey)
        return when (bitSet) {
            null -> throw EmptyResultDataAccessException(1)
            else -> bitSet
        }
    }
}