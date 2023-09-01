package coupon_platform.domain.coupon.facade

import coupon_platform.domain.coupon.CreateCouponCommand
import coupon_platform.domain.coupon.service.CouponService
import org.springframework.stereotype.Service

@Service
class CouponFacade(
    val couponService: CouponService
) {

    fun createCoupon(createCouponCommand: CreateCouponCommand): String {
        return couponService.createCoupon(createCouponCommand)
    }

}