package coupon_platform.infrastructure.couponstats

import coupon_platform.domain.couponstats.domain.CouponDailyStats
import coupon_platform.domain.couponstats.repository.CouponDailyStatsStore
import org.springframework.stereotype.Repository

@Repository
class CouponDailyDailyStatsStoreImpl(
    private val couponDailyStatsJpaRepository: CouponDailyStatsJpaRepository,
) : CouponDailyStatsStore {

    override fun saveDailyStats(couponDailyStats: List<CouponDailyStats>) {
        couponDailyStatsJpaRepository.saveAll(couponDailyStats)
    }
}