package coupon_platform.infrastructure.coupon_stats

import coupon_platform.domain.coupon_stats.domain.CouponDailyStats
import coupon_platform.domain.coupon_stats.repository.CouponStatsReader
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class CouponStatsReaderImpl(
    private val couponStatsJpaRepository: CouponStatsJpaRepository,
): CouponStatsReader {

    override fun findCouponStats(startDate: LocalDate, endDate: LocalDate): List<CouponDailyStats> {
        return couponStatsJpaRepository.findCouponStatsByDateRange(startDate, endDate)
    }

}