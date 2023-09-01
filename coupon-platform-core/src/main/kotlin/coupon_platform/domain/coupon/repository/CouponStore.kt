package coupon_platform.domain.coupon.repository

import coupon_platform.domain.coupon.entity.Coupon
import org.springframework.stereotype.Component

interface CouponStore {
    fun saveCoupon(coupon: Coupon): String
}

@Component
class CouponStoreImpl(
    val couponJpaRepository: CouponJpaRepository,
) : CouponStore {

    override fun saveCoupon(coupon: Coupon): String {
        return couponJpaRepository.save(coupon).name
    }

}