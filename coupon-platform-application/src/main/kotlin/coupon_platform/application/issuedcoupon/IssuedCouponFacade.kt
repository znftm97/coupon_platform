package coupon_platform.application.issuedcoupon

import coupon_platform.domain.coupon.service.CouponReadService
import coupon_platform.domain.couponcode.service.CouponCodeReadService
import coupon_platform.domain.issuedcoupon.dto.IssueCouponCommand
import coupon_platform.domain.issuedcoupon.service.IssuedCouponStoreService
import org.springframework.stereotype.Service

@Service
class IssuedCouponFacade(
    private val issuedCouponStoreService: IssuedCouponStoreService,
    private val couponCodeReadService: CouponCodeReadService,
    private val couponReadService: CouponReadService,
) {
    fun issueCoupon(issueCouponCommand: IssueCouponCommand) =
        issuedCouponStoreService.issueCoupon(issueCouponCommand)

    fun issueCouponByCouponCode(couponCode: String, accountId: Long): Long {
        val couponCodeInfo = couponCodeReadService.findCouponCodeByCode(couponCode)
        val couponInfo = couponReadService.findCouponById(couponCodeInfo.couponId)
        return issuedCouponStoreService.issueCoupon(
            IssueCouponCommand(couponInfo.id, accountId, couponCodeInfo.expirationPeriod),
        )
    }

    fun cachingIssuedCouponToLocal() {
        issuedCouponStoreService.cachingIssuedCouponToLocal()
    }
}