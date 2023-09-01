package coupon_platform.domain.coupon.repository

import coupon_platform.domain.coupon.entity.Coupon
import org.springframework.stereotype.Component

interface CouponStore {
    fun saveCoupon(coupon: Coupon): Coupon
}

@Component
class CouponStoreImpl(
    val couponJpaRepository: CouponJpaRepository,
) : CouponStore {

    override fun saveCoupon(coupon: Coupon): Coupon {
        return couponJpaRepository.save(coupon)
    }

}