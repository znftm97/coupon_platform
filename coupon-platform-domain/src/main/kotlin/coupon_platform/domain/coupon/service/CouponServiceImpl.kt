package coupon_platform.domain.coupon.service

import coupon_platform.domain.coupon.CreateCouponCommand
import coupon_platform.domain.coupon.repository.CouponCommand
import org.springframework.stereotype.Service

@Service
class CouponServiceImpl(
    val couponCommand: CouponCommand,
) : CouponService {

    override fun createCoupon(createCouponCommand: CreateCouponCommand) : String {
        val coupon = createCouponCommand.toEntity()
        return couponCommand.saveCoupon(coupon)
    }
}