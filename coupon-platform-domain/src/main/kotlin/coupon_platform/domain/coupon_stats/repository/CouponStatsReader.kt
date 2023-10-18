package coupon_platform.domain.coupon_stats.repository

import coupon_platform.domain.coupon_stats.domain.CouponDailyStats
import java.time.LocalDate
import java.time.LocalDateTime

interface CouponStatsReader {
    fun findCouponStats(startDate: LocalDate, endDate: LocalDate): List<CouponDailyStats>
}