package coupon_platform.infrastructure.config

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import coupon_platform.infrastructure.redis.util.IssuedCouponForRedis
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.util.*

@Configuration
class LettuceConfiguration(
    private val redisProperties: RedisProperties
) {
    @Bean
    fun lettuceConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory(redisProperties.host, redisProperties.port)
    }

    @Bean
    fun redisTemplateOfIssuedCoupons(connectionFactory: RedisConnectionFactory): RedisTemplate<String, List<IssuedCouponForRedis>> {
        val template = RedisTemplate<String, List<IssuedCouponForRedis>>()
        template.connectionFactory = connectionFactory

        val objectMapper = ObjectMapper().registerModule(JavaTimeModule())

        val keySerializer = StringRedisSerializer()
        val listType: JavaType = TypeFactory.defaultInstance().constructCollectionType(List::class.java, IssuedCouponForRedis::class.java)
        val valueSerializer = Jackson2JsonRedisSerializer<List<IssuedCouponForRedis>>(objectMapper, listType)
        val hashKeySerializer = StringRedisSerializer()
        val hashValueSerializer = Jackson2JsonRedisSerializer<List<IssuedCouponForRedis>>(listType)

        template.keySerializer = keySerializer
        template.valueSerializer = valueSerializer
        template.hashKeySerializer = hashKeySerializer
        template.hashValueSerializer = hashValueSerializer

        template.afterPropertiesSet()

        return template
    }

    @Bean
    fun redisTemplateOfBitset(): RedisTemplate<String, BitSet> {
        val template = RedisTemplate<String, BitSet>()
        template.connectionFactory = lettuceConnectionFactory()

        val keySerializer = StringRedisSerializer()
        val valueSerializer = BitSetRedisSerializer()

        template.keySerializer = keySerializer
        template.valueSerializer = valueSerializer
        template.hashKeySerializer = keySerializer
        template.hashValueSerializer = valueSerializer

        template.afterPropertiesSet()

        return template
    }
}

class BitSetRedisSerializer : RedisSerializer<BitSet> {
    override fun serialize(bitset: BitSet?): ByteArray? {
        bitset ?: return null

        val byteArray = ByteArray(bitset.size() / 8 + 1)
        for (i in 0 until bitset.size()) {
            if (bitset.get(i)) {
                byteArray[i / 8] = (byteArray[i / 8].toInt() or (1 shl (i % 8))).toByte()
            }
        }

        return byteArray
    }

    override fun deserialize(bytes: ByteArray?): BitSet? {
        bytes ?: return null

        val bitset = BitSet(bytes.size * 8)
        for (i in bytes.indices) {
            for (j in 0 until 8) {
                if ((bytes[i].toInt() and (1 shl j)) != 0) {
                    bitset.set(i * 8 + j)
                }
            }
        }

        return bitset
    }
}