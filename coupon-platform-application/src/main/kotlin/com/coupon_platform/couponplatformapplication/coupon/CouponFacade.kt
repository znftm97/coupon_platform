package com.coupon_platform.couponplatformapplication.coupon

import coupon_platform.domain.coupon.CreateCouponCommand
import coupon_platform.domain.coupon.service.CouponReadService
import coupon_platform.domain.coupon.service.CouponStoreService
import org.springframework.stereotype.Service

@Service
class CouponFacade(
    val couponReadService: CouponReadService,
    val couponStoreService: CouponStoreService,
) {
    fun createCoupon(createCouponCommand: CreateCouponCommand) = couponStoreService.createCoupon(createCouponCommand)
    fun readCoupon(name: String) = couponReadService.readCoupon(name)
}