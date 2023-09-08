package coupon_platform.interfaces.coupon.dto

import coupon_platform.domain.coupon.dto.CouponInfo
import coupon_platform.domain.coupon.entity.Coupon

data class CouponResponse(
    val externalId: Long?,
    val name: String,
    val applyType: Coupon.ApplyType,
    val discountType: Coupon.DiscountType,
) {
    companion object {
        fun toResponse(couponInfo: CouponInfo): CouponResponse = CouponResponse(
            couponInfo.externalId,
            couponInfo.name,
            couponInfo.applyType,
            couponInfo.discountType
        )
    }
}