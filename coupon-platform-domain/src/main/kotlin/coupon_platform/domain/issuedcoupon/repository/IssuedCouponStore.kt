package coupon_platform.domain.issuedcoupon.repository

import coupon_platform.domain.issuedcoupon.entitiy.IssuedCoupon

interface IssuedCouponStore {
    fun saveIssuedCoupon(issuedCoupon: IssuedCoupon): IssuedCoupon
    fun saveIssuedCouponToRedis(issuedCoupons: List<IssuedCoupon>)
    fun saveIssuedCouponToLocal(issuedCoupons: List<IssuedCoupon>)
}