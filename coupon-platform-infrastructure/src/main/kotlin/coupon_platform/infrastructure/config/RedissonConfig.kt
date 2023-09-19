package coupon_platform.infrastructure.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RedissonConfig {

    @Value("\${spring.data.redis.host}")
    private val host: String? = null

    @Value("\${spring.data.redis.port}")
    private val port: String? = null

    @Bean
    fun redissonClient(): RedissonClient? {
        val config = Config()
        config.useSingleServer().address = "redis://$host:$port"
        return Redisson.create(config)
    }
}