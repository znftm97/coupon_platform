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
        fun toInfo(coupon: Coupon): CouponInfo {
            return CouponInfo(
                id = coupon.id,
                externalId = coupon.externalId,
                name = coupon.name,
                discountType = coupon.discountType,
                price = coupon.price,
            )
        }
    }
}