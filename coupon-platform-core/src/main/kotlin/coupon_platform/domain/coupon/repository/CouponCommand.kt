package coupon_platform.domain.coupon.repository

import coupon_platform.domain.coupon.entity.Coupon
import org.springframework.stereotype.Component

interface CouponCommand {
    fun saveCoupon(coupon: Coupon) : String
}

@Component
class CouponCommandImpl (
    val couponJpaRepository: CouponJpaRepository,
) : CouponCommand {

    override fun saveCoupon(coupon: Coupon) : String {
        return couponJpaRepository.save(coupon).name
    }

}