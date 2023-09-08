package coupon_platform.domain.coupon

import coupon_platform.domain.coupon.entity.Coupon

data class CouponCreateCommand(
    val name: String,
    val applyType: Coupon.ApplyType,
    val discountType: Coupon.DiscountType,
) {
    fun toEntity(): Coupon = Coupon.of(name, applyType, discountType)
}