package coupon_platform.domain.coupon.repository

import coupon_platform.domain.coupon.entity.Coupon

interface CouponStore {
    fun saveCoupon(coupon: Coupon): Coupon
}