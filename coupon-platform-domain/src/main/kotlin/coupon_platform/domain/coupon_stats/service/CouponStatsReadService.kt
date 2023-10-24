package coupon_platform.domain.coupon_stats.service

import coupon_platform.domain.coupon_stats.domain.CouponDailyStats
import coupon_platform.domain.coupon_stats.dto.CouponDailyStatsInfo
import coupon_platform.domain.coupon_stats.repository.CouponDailyStatsReader
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CouponStatsReadService(
    private val couponDailyStatsReader: CouponDailyStatsReader,
) {
    fun findCouponStats(startDate: LocalDate, endDate: LocalDate): List<CouponDailyStatsInfo> {
        val couponStats: List<CouponDailyStats> = couponDailyStatsReader.findCouponStats(startDate, endDate)
        return CouponDailyStatsInfo.toInfos(couponStats)
    }
}