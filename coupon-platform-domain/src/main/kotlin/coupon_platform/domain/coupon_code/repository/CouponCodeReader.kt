package coupon_platform.domain.coupon_code.repository

import coupon_platform.domain.coupon_code.entity.CouponCode

interface CouponCodeReader {
    fun findCouponCodeByCode(code: String): CouponCode
}