package coupon_platform.interfaces.coupon.dto

import coupon_platform.domain.coupon.dto.CouponInfo
import coupon_platform.domain.coupon.entity.Coupon

data class CouponResponse(
    val externalId: String,
    val name: String,
    val discountType: Coupon.DiscountType,
) {
    companion object {
        fun toResponse(couponInfo: CouponInfo) = CouponResponse(
            couponInfo.externalId,
            couponInfo.name,
            couponInfo.discountType
        )
    }
}