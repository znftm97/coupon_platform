package coupon_platform.domain.issued_coupon.repository

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import java.time.ZonedDateTime

interface IssuedCouponReader {
    fun findIssuedCouponsInDates(dates: List<ZonedDateTime>): List<IssuedCoupon>
}