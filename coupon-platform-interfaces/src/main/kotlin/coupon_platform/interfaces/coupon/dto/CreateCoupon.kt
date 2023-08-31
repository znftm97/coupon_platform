package coupon_platform.interfaces.coupon.dto

import coupon_platform.domain.coupon.CreateCouponCommand
import coupon_platform.domain.coupon.entity.ApplyType
import coupon_platform.domain.coupon.entity.DiscountType

data class CreateCouponRequest (
    val name: String,
    val applyType: ApplyType,
    val discountType: DiscountType,
) {
    fun toCommand()= CreateCouponCommand(name, applyType, discountType)
}