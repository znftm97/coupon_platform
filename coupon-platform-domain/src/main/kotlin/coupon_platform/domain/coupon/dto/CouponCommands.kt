package coupon_platform.domain.coupon

import coupon_platform.domain.coupon.entity.Coupon

data class CouponCreateCommand(
    val name: String,
    val discountType: Coupon.DiscountType,
    val price: Int,
) {
    fun toEntity(externalId: String): Coupon = Coupon.of(name, discountType, price, externalId)
}