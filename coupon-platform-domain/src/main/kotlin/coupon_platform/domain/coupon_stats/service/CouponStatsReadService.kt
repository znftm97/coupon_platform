package coupon_platform.domain.coupon_stats.service

import coupon_platform.domain.coupon_stats.domain.CouponDailyStats
import coupon_platform.domain.coupon_stats.dto.CouponDailyStatsInfo
import coupon_platform.domain.coupon_stats.repository.CouponStatsReader
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CouponStatsReadService(
    private val couponStatsReader: CouponStatsReader,
) {
    fun findCouponStats(startDate: LocalDate, endDate: LocalDate): List<CouponDailyStatsInfo> {
        val couponStats: List<CouponDailyStats> = couponStatsReader.findCouponStats(startDate, endDate)
        return CouponDailyStatsInfo.toInfos(couponStats)
    }
}