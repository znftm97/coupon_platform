package coupon_platform.infrastructure.cache.localcaffeine

import coupon_platform.infrastructure.cache.globalredis.util.CacheConstants.ISSUED_COUPON
import coupon_platform.infrastructure.cache.globalredis.util.CacheConstants.ISSUED_COUPON_KEY_TTL

enum class CacheType(
    val cacheName: String,
    val ttl: Long,
    val maxSize: Long,
) {
    ISSUED_COUPON_CACHE(ISSUED_COUPON, ISSUED_COUPON_KEY_TTL, 10000L),
}