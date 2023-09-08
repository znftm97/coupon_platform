package coupon_platform.interfaces.coupon.dto

import coupon_platform.domain.coupon.CreateCouponCommand
import coupon_platform.domain.coupon.entity.Coupon

data class CouponCreateRequest(
    val name: String,
    val applyType: Coupon.ApplyType,
    val discountType: Coupon.DiscountType,
)

fun CouponCreateRequest.toCommand() = CreateCouponCommand(name, applyType, discountType)