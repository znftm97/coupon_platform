package coupon_platform.application.coupon

import coupon_platform.domain.coupon.CouponCreateCommand
import coupon_platform.domain.coupon.service.CouponReadService
import coupon_platform.domain.coupon.service.CouponStoreService
import org.springframework.stereotype.Service

@Service
class CouponFacade(
    private val couponReadService: CouponReadService,
    private val couponStoreService: CouponStoreService,
) {
    fun createCoupon(couponCreateCommand: CouponCreateCommand) =
        couponStoreService.createCoupon(couponCreateCommand)

    fun findCouponByName(name: String) = couponReadService.findCouponByName(name)
}