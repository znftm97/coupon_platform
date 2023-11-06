package coupon_platform.infrastructure.issuedcoupon

import coupon_platform.domain.issuedcoupon.entitiy.IssuedCoupon
import coupon_platform.domain.issuedcoupon.repository.IssuedCouponStore
import coupon_platform.infrastructure.cache.globalredis.handler.RedisHandlerOfIssuedCoupon
import coupon_platform.infrastructure.cache.globalredis.util.CacheConstants.ISSUED_COUPON_KEY_TTL
import coupon_platform.infrastructure.cache.localcaffeine.CaffeineHandlerForIssuedCoupon
import org.springframework.stereotype.Repository

@Repository
class IssuedCouponStoreImpl(
    private val issuedCouponJpaRepository: IssuedCouponJpaRepository,
    private val redisHandler: RedisHandlerOfIssuedCoupon,
    private val caffeineHandler: CaffeineHandlerForIssuedCoupon,
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