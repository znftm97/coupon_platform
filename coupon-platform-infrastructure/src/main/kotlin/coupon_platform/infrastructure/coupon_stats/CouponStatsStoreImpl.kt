package coupon_platform.infrastructure.coupon_stats

import coupon_platform.domain.coupon_stats.domain.CouponDailyStats
import coupon_platform.domain.coupon_stats.repository.CouponStatsStore
import org.springframework.stereotype.Repository

@Repository
class CouponStatsStoreImpl(
    private val couponStatsJpaRepository: CouponStatsJpaRepository,
): CouponStatsStore {

    override fun saveDailyStats(couponDailyStats: List<CouponDailyStats>) {
        couponStatsJpaRepository.saveAll(couponDailyStats)
    }

}