package coupon_platform.domain.coupon_stats.repository

import coupon_platform.domain.coupon_stats.domain.CouponDailyStats

interface CouponStatsStore {
    fun saveDailyStats(couponDailyStats: List<CouponDailyStats>)
}