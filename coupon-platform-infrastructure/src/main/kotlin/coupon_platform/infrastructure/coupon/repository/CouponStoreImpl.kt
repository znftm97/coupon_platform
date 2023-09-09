package coupon_platform.infrastructure.coupon.repository

import coupon_platform.domain.coupon.entity.Coupon
import coupon_platform.domain.coupon.repository.CouponStore
import org.springframework.stereotype.Component

@Component
class CouponStoreImpl(
    val couponJpaRepository: CouponJpaRepository,
) : CouponStore {

    override fun saveCoupon(coupon: Coupon): Coupon = couponJpaRepository.save(coupon)

}