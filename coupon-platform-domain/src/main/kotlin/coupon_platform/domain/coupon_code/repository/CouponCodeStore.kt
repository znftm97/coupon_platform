package coupon_platform.domain.coupon_code.repository

import coupon_platform.domain.coupon_code.entity.CouponCode

interface CouponCodeStore {
    fun createCouponCode(couponCode: CouponCode): CouponCode
}