package coupon_platform.interfaces.coupon.dto

import coupon_platform.domain.coupon.CreateCouponCommand
import coupon_platform.domain.coupon.entity.Coupon

data class CreateCouponRequest(
    val name: String,
    val applyType: Coupon.ApplyType,
    val discountType: Coupon.DiscountType,
)

fun CreateCouponRequest.toCommand() = CreateCouponCommand(name, applyType, discountType)