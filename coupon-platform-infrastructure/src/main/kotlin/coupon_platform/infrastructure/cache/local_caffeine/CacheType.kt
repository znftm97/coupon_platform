package coupon_platform.infrastructure.cache.local_caffeine

import coupon_platform.infrastructure.cache.global_redis.util.CacheConstants.ISSUED_COUPON
import coupon_platform.infrastructure.cache.global_redis.util.CacheConstants.ISSUED_COUPON_KEY_TTL

enum class CacheType(
    val cacheName: String,
    val ttl: Long,
    val maxSize: Long,
) {
    ISSUED_COUPON_CACHE(ISSUED_COUPON, ISSUED_COUPON_KEY_TTL, 10000L)
}