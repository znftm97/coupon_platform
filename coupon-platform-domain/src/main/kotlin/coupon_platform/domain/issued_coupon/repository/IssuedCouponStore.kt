package coupon_platform.domain.issued_coupon.repository

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon

interface IssuedCouponStore {
    fun saveIssuedCoupon(issuedCoupon: IssuedCoupon): IssuedCoupon
    fun saveIssuedCouponToRedis(issuedCoupons: List<IssuedCoupon>)
    fun saveIssuedCouponToLocal(issuedCoupons: List<IssuedCoupon>)
}