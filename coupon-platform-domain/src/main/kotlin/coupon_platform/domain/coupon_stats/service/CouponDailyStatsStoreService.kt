package coupon_platform.domain.coupon_stats.service

import coupon_platform.domain.coupon_stats.domain.CouponDailyStats
import coupon_platform.domain.coupon_stats.dto.CouponDailyStatsInfo
import coupon_platform.domain.coupon_stats.repository.CouponDailyStatsStore
import org.springframework.stereotype.Service

@Service
class CouponDailyStatsStoreService(
    private val couponDailyStatsStore: CouponDailyStatsStore,
) {

    fun saveDailyStats(couponDailyStatsInfos: List<CouponDailyStatsInfo>) {
        val couponDailyStats: List<CouponDailyStats> = couponDailyStatsInfos.map { CouponDailyStatsInfo.toEntity(it) }
        couponDailyStatsStore.saveDailyStats(couponDailyStats)
    }
}