package coupon_platform.domain.couponstats.repository

import coupon_platform.domain.couponstats.domain.CouponDailyStats
import java.time.LocalDate

interface CouponDailyStatsReader {
    fun findCouponDailyStats(startDate: LocalDate, endDate: LocalDate): List<CouponDailyStats>
}