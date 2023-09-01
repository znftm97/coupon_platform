package coupon_platform.interfaces.coupon.dto

import coupon_platform.domain.coupon.dto.CouponInfo
import coupon_platform.domain.coupon.entity.Coupon

data class QueryCouponResponse(
    val externalId: Long?,
    val name: String,
    val applyType: Coupon.ApplyType,
    val discountType: Coupon.DiscountType,
) {
    companion object {
        fun toResponse(couponInfo: CouponInfo): QueryCouponResponse = QueryCouponResponse(
            couponInfo.externalId,
            couponInfo.name,
            couponInfo.applyType,
            couponInfo.discountType
        )
    }
}