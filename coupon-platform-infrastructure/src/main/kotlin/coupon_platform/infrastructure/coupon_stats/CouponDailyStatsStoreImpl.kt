package coupon_platform.infrastructure.coupon_stats

import coupon_platform.domain.coupon_stats.domain.CouponDailyStats
import coupon_platform.domain.coupon_stats.repository.CouponStatsStore
import org.springframework.stereotype.Repository

@Repository
class CouponDailyStatsStoreImpl(
    private val couponDailyStatsJpaRepository: CouponDailyStatsJpaRepository,
): CouponStatsStore {

    override fun saveDailyStats(couponDailyStats: List<CouponDailyStats>) {
        couponDailyStatsJpaRepository.saveAll(couponDailyStats)
    }

}