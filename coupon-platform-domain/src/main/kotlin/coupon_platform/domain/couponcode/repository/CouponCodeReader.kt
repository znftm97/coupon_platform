package coupon_platform.domain.couponcode.repository

import coupon_platform.domain.couponcode.entity.CouponCode

interface CouponCodeReader {
    fun findCouponCodeByCode(code: String): CouponCode
}