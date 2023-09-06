package coupon_platform.application.issued_coupon

import coupon_platform.domain.issued_coupon.dto.IssueCouponCommand
import coupon_platform.domain.issued_coupon.service.IssuedCouponStoreService
import org.springframework.stereotype.Service

@Service
class IssuedCouponFacade(
    val issuedCouponStoreService: IssuedCouponStoreService,
) {
    fun issueCoupon(issueCouponCommand: IssueCouponCommand) = issuedCouponStoreService.issueCoupon(issueCouponCommand)
}