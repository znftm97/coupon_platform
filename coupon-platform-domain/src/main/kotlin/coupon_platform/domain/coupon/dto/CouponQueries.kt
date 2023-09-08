package coupon_platform.domain.coupon.dto

import coupon_platform.domain.coupon.entity.Coupon

data class CouponInfo(
    val id: Long,
    val externalId: Long?,
    val name: String,
    val applyType: Coupon.ApplyType,
    val discountType: Coupon.DiscountType,
) {
    companion object {
        fun toCouponInfo(coupon: Coupon): CouponInfo {
            return CouponInfo(
                coupon.id,
                coupon.externalId,
                coupon.name,
                coupon.applyType,
                coupon.discountType,
            )
        }
    }
}