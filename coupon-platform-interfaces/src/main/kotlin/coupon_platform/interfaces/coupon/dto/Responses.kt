package coupon_platform.interfaces.coupon.dto

import coupon_platform.domain.coupon.dto.CouponInfo
import coupon_platform.domain.coupon.entity.Coupon

data class CouponResponse(
    val externalId: String,
    val name: String,
    val discountType: Coupon.DiscountType,
    val price: Int,
) {
    companion object {
        fun toResponse(couponInfo: CouponInfo) = CouponResponse(
            couponInfo.externalId,
            couponInfo.name,
            couponInfo.discountType,
            couponInfo.price,
        )
    }
}