package coupon_platform.infrastructure.issued_coupon

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import coupon_platform.domain.issued_coupon.repository.IssuedCouponStore
import coupon_platform.infrastructure.cache.global_redis.handler.RedisHandlerOfIssuedCoupon
import coupon_platform.infrastructure.cache.global_redis.util.CacheConstants.ISSUED_COUPON_KEY_TTL
import coupon_platform.infrastructure.cache.local_caffeine.CaffeineHandlerForIssuedCoupon
import org.springframework.stereotype.Repository

@Repository
class IssuedCouponStoreImpl(
    private val issuedCouponJpaRepository: IssuedCouponJpaRepository,
    private val redisHandler: RedisHandlerOfIssuedCoupon,
    private val caffeineHandler: CaffeineHandlerForIssuedCoupon
) : IssuedCouponStore {

    override fun saveIssuedCoupon(issuedCoupon: IssuedCoupon): IssuedCoupon = issuedCouponJpaRepository.save(issuedCoupon)

    override fun saveIssuedCouponToRedis(issuedCoupons: List<IssuedCoupon>) {
        redisHandler.setIssuedCouponsWithTTL(issuedCoupons, ISSUED_COUPON_KEY_TTL)
    }

    override fun saveIssuedCouponToLocal(issuedCoupons: List<IssuedCoupon>) {
        issuedCoupons.groupBy {
            it.createdAt.toLocalDate().toString()
        }.forEach { (key, value) ->
            caffeineHandler.set(key, value)
        }
    }

}