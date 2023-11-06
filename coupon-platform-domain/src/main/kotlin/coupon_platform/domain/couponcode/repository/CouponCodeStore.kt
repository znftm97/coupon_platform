package coupon_platform.domain.couponcode.repository

import coupon_platform.domain.couponcode.entity.CouponCode

interface CouponCodeStore {
    fun createCouponCode(couponCode: CouponCode): CouponCode
}