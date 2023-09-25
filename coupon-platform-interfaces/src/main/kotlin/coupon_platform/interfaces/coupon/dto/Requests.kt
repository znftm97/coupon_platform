package coupon_platform.interfaces.coupon.dto

import coupon_platform.domain.coupon.CouponCreateCommand
import coupon_platform.domain.coupon.entity.Coupon.DiscountType

data class CouponCreateRequest(
    val name: String,
    val discountType: DiscountType,
    val price: Int,
) {
    fun toCommand() = CouponCreateCommand(name, discountType, price)
}