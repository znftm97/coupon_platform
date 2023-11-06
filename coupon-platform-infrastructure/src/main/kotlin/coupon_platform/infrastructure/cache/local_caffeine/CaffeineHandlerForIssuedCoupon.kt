package coupon_platform.infrastructure.cache.local_caffeine

import coupon_platform.domain.issuedcoupon.entitiy.IssuedCoupon
import coupon_platform.infrastructure.cache.global_redis.util.CacheConstants.ISSUED_COUPON
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CaffeineHandlerForIssuedCoupon(
    private val cacheManager: CacheManager,
) {

    fun getIssuedCouponInDates(reqDates: List<LocalDate>): List<IssuedCoupon> {
        val cache = cacheManager.getCache(ISSUED_COUPON) ?: return emptyList()

        return reqDates.flatMap { date ->
            cache.get(date.toString(), List::class.java)
                ?.mapNotNull { it as? IssuedCoupon }
                ?: emptyList()
        }
    }

    fun set(key: String, value: List<IssuedCoupon>) {
        val cache = cacheManager.getCache(ISSUED_COUPON) ?: return
        cache.put(key, value)
    }
}