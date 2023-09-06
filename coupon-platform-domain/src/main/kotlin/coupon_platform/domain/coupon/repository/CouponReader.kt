package coupon_platform.domain.coupon.repository

import coupon_platform.domain.coupon.entity.Coupon

interface CouponReader {
    fun readCoupon(name: String): Coupon
}

