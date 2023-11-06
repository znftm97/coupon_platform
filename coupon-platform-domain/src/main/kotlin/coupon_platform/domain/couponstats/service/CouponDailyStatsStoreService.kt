package coupon_platform.domain.couponstats.service

import coupon_platform.domain.couponstats.domain.CouponDailyStats
import coupon_platform.domain.couponstats.dto.CouponDailyStatsInfo
import coupon_platform.domain.couponstats.repository.CouponDailyStatsStore
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