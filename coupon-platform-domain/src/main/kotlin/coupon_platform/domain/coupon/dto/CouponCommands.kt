package coupon_platform.domain.coupon

import coupon_platform.domain.coupon.entity.ApplyType
import coupon_platform.domain.coupon.entity.Coupon
import coupon_platform.domain.coupon.entity.DiscountType

data class CreateCouponCommand (
    val name: String,
    val applyType: ApplyType,
    val discountType: DiscountType,
) {
    fun toEntity(): Coupon = Coupon.of(name, applyType, discountType)
}