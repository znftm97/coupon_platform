package coupon_platform.application.couponcode

import coupon_platform.domain.coupon.service.CouponReadService
import coupon_platform.domain.couponcode.dto.CouponCodeCreateCommand
import coupon_platform.domain.couponcode.service.CouponCodeStoreService
import org.springframework.stereotype.Service

@Service
class CouponCodeFacade(
    private val couponCodeStoreService: CouponCodeStoreService,
    private val couponReadService: CouponReadService,
) {
    fun createCouponCode(couponCodeCreateCommand: CouponCodeCreateCommand): String {
        // 존재하는 쿠폰인지 확인하기 위해 조회
        couponReadService.findCouponById(couponCodeCreateCommand.couponId)
        val couponCodeInfo = couponCodeStoreService.createCouponCode(couponCodeCreateCommand)

        return couponCodeInfo.couponCode
    }
}