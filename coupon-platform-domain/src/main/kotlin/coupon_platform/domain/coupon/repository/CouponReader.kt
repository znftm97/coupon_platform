package coupon_platform.domain.coupon.repository

import coupon_platform.domain.coupon.entity.Coupon

interface CouponReader {
    fun findCouponByName(name: String): Coupon
    fun findCouponById(id: Long): Coupon
}

