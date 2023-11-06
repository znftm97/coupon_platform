package coupon_platform.domain.couponstats.service

import coupon_platform.domain.couponstats.domain.CouponDailyStats
import coupon_platform.domain.couponstats.dto.CouponDailyStatsInfo
import coupon_platform.domain.couponstats.repository.CouponDailyStatsReader
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