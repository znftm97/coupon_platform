package coupon_platform.domain.couponstats.repository

import coupon_platform.domain.couponstats.domain.CouponDailyStats

interface CouponDailyStatsStore {
    fun saveDailyStats(couponDailyStats: List<CouponDailyStats>)
}