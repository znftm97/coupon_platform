package coupon_platform.application.coupon_code

import coupon_platform.domain.coupon.service.CouponReadService
import coupon_platform.domain.coupon_code.dto.CouponCodeCreateCommand
import coupon_platform.domain.coupon_code.service.CouponCodeStoreService
import org.springframework.stereotype.Service

@Service
class CouponCodeFacade(
    val couponCodeStoreService: CouponCodeStoreService,
    val couponReadService: CouponReadService,
) {
    suspend fun createCouponCode(couponCodeCreateCommand: CouponCodeCreateCommand): String {
        // 존재하는 쿠폰인지 확인하기 위해 조회
        couponReadService.findCouponById(couponCodeCreateCommand.couponId)
        val couponCodeInfo = couponCodeStoreService.createCouponCode(couponCodeCreateCommand)

        return couponCodeInfo.couponCode
    }
}