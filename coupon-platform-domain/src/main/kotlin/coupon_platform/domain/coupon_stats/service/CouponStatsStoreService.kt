package coupon_platform.domain.coupon_stats.service

import coupon_platform.domain.coupon_stats.domain.CouponDailyStats
import coupon_platform.domain.coupon_stats.dto.CouponDailyStatsInfo
import coupon_platform.domain.coupon_stats.repository.CouponStatsStore
import org.springframework.stereotype.Service

@Service
class CouponStatsStoreService(
    private val couponStatsStore: CouponStatsStore,
) {

    fun saveDailyStats(couponDailyStatsInfos: List<CouponDailyStatsInfo>) {
        val couponDailyStats: List<CouponDailyStats> = couponDailyStatsInfos.map { CouponDailyStatsInfo.toEntity(it) }
        couponStatsStore.saveDailyStats(couponDailyStats)
    }
}