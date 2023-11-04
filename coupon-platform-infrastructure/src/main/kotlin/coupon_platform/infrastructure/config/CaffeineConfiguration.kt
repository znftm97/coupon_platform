package coupon_platform.infrastructure.config

import com.github.benmanes.caffeine.cache.Caffeine
import coupon_platform.infrastructure.cache.local_caffeine.CacheType
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
class CaffeineConfiguration {

    @Bean
    fun caffeineConfig(): List<CaffeineCache> =
        CacheType.values()
            .map { cache ->
                CaffeineCache(
                    cache.cacheName,
                    Caffeine.newBuilder()
                        .recordStats()
                        .expireAfterWrite(cache.ttl, TimeUnit.SECONDS)
                        .maximumSize(cache.maxSize)
                        .build()
                )
            }

    @Bean
    fun cacheManager(caffeine: List<CaffeineCache>): CacheManager {
        val cacheManager = SimpleCacheManager()
        cacheManager.setCaches(caffeine)

        return cacheManager
    }
}