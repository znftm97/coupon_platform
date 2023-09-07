package coupon_platform.domain.issued_coupon.repository

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon

interface IssuedCouponStore {
    fun issueCoupon(issuedCoupon: IssuedCoupon): IssuedCoupon
}