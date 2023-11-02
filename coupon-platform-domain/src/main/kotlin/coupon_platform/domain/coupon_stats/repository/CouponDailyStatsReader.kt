package coupon_platform.domain.coupon_stats.repository

import coupon_platform.domain.coupon_stats.domain.CouponDailyStats
import java.time.LocalDate

interface CouponDailyStatsReader {
    fun findCouponDailyStats(startDate: LocalDate, endDate: LocalDate): List<CouponDailyStats>
}