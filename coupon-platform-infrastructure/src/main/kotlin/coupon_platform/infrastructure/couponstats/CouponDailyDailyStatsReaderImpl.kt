package coupon_platform.infrastructure.couponstats

import coupon_platform.domain.couponstats.domain.CouponDailyStats
import coupon_platform.domain.couponstats.repository.CouponDailyStatsReader
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class CouponDailyDailyStatsReaderImpl(
    private val couponDailyStatsJpaRepository: CouponDailyStatsJpaRepository,
) : CouponDailyStatsReader {

    override fun findCouponDailyStats(startDate: LocalDate, endDate: LocalDate): List<CouponDailyStats> {
        return couponDailyStatsJpaRepository.findCouponDailyStatsByDateRange(startDate, endDate)
    }
}