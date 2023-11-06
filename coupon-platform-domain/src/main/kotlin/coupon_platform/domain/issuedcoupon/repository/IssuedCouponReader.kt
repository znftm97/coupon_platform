package coupon_platform.domain.issuedcoupon.repository

import coupon_platform.domain.issuedcoupon.entitiy.IssuedCoupon
import java.time.LocalDate

interface IssuedCouponReader {
    fun findIssuedCouponsInDates(dates: List<LocalDate>): List<IssuedCoupon>
    fun findIssuedCouponsInDatesFromRedis(dates: List<LocalDate>): List<IssuedCoupon>
    fun findIssuedCouponInDatesFromLocal(dates: List<LocalDate>): List<IssuedCoupon>
}