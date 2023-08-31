package coupon_platform.domain.coupon.service

import coupon_platform.domain.coupon.CreateCouponCommand

interface CouponService {
    fun createCoupon(createCouponCommand: CreateCouponCommand) : String
}