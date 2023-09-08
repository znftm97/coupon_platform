package coupon_platform.application.issued_coupon

import coupon_platform.domain.coupon.service.CouponReadService
import coupon_platform.domain.coupon_code.service.CouponCodeReadService
import coupon_platform.domain.issued_coupon.dto.IssueCouponCommand
import coupon_platform.domain.issued_coupon.service.IssuedCouponStoreService
import org.springframework.stereotype.Service

@Service
class IssuedCouponFacade(
    val issuedCouponStoreService: IssuedCouponStoreService,
    val couponCodeReadService: CouponCodeReadService,
    val couponReadService: CouponReadService,
) {
    fun issueCoupon(issueCouponCommand: IssueCouponCommand) = issuedCouponStoreService.issueCoupon(issueCouponCommand)

    fun issueCouponByCouponCode(couponCode: String): Long {
        val couponCodeInfo = couponCodeReadService.findCouponCodeByCode(couponCode)
        val couponInfo = couponReadService.findCouponById(couponCodeInfo.couponId)
        return issuedCouponStoreService.issueCoupon(IssueCouponCommand(couponInfo.id, couponCodeInfo.expirationPeriod))
    }
}