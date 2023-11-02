package coupon_platform.infrastructure.coupon_stats

import coupon_platform.domain.coupon_stats.domain.CouponDailyStats
import coupon_platform.domain.coupon_stats.repository.CouponDailyStatsReader
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class CouponDailyDailyStatsReaderImpl(
    private val couponDailyStatsJpaRepository: CouponDailyStatsJpaRepository,
): CouponDailyStatsReader {

    override fun findCouponDailyStats(startDate: LocalDate, endDate: LocalDate): List<CouponDailyStats> {
        return couponDailyStatsJpaRepository.findCouponDailyStatsByDateRange(startDate, endDate)
    }

}