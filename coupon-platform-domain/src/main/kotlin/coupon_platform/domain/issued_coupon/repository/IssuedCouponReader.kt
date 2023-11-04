package coupon_platform.domain.issued_coupon.repository

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import java.time.LocalDate

interface IssuedCouponReader {
    fun findIssuedCouponsInDates(dates: List<LocalDate>): List<IssuedCoupon>
    fun findIssuedCouponsInDatesFromRedis(dates: List<LocalDate>): List<IssuedCoupon>
    fun findIssuedCouponInDatesFromLocal(dates: List<LocalDate>): List<IssuedCoupon>
}