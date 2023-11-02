package coupon_platform.domain.coupon_stats.service

import coupon_platform.domain.coupon_stats.domain.CouponDailyStats
import coupon_platform.domain.coupon_stats.dto.CouponDailyStatsInfo
import coupon_platform.domain.coupon_stats.repository.CouponDailyStatsReader
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CouponDailyStatsReadService(
    private val couponDailyStatsReader: CouponDailyStatsReader,
) {
    fun findCouponStats(startDate: LocalDate, endDate: LocalDate): List<CouponDailyStatsInfo> {
        val couponDailyStats: List<CouponDailyStats> = couponDailyStatsReader.findCouponDailyStats(startDate, endDate)
        return CouponDailyStatsInfo.toInfos(couponDailyStats)
    }
}