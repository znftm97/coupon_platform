package coupon_platform.infrastructure.issued_coupon

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import coupon_platform.domain.issued_coupon.repository.IssuedCouponStore
import coupon_platform.infrastructure.cache.global_redis.handler.RedisHandlerOfIssuedCoupon
import coupon_platform.infrastructure.cache.global_redis.util.CacheConstants.ISSUED_COUPON_KEY_TTL
import org.springframework.stereotype.Repository

@Repository
class IssuedCouponStoreImpl(
    private val issuedCouponJpaRepository: IssuedCouponJpaRepository,
    private val redisHandler: RedisHandlerOfIssuedCoupon
) : IssuedCouponStore {

    override fun saveIssuedCoupon(issuedCoupon: IssuedCoupon): IssuedCoupon = issuedCouponJpaRepository.save(issuedCoupon)

    override fun saveIssuedCouponFromRedis(issuedCoupons: List<IssuedCoupon>) {
        redisHandler.setIssuedCouponsWithTTL(issuedCoupons, ISSUED_COUPON_KEY_TTL)
    }

}