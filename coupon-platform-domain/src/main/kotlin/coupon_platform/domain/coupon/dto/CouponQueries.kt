package coupon_platform.domain.coupon.dto

import coupon_platform.domain.coupon.entity.Coupon

data class CouponInfo(
    val id: Long,
    val externalId: String,
    val name: String,
    val discountType: Coupon.DiscountType,
    val price: Int,
) {
    companion object {
        fun toCouponInfo(coupon: Coupon): CouponInfo {
            return CouponInfo(
                coupon.id,
                coupon.externalId,
                coupon.name,
                coupon.discountType,
                coupon.price,
            )
        }
    }
}