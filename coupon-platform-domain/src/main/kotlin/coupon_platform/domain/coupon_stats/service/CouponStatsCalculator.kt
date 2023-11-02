package coupon_platform.domain.coupon_stats.service

import coupon_platform.domain.coupon_stats.dto.CouponDailyStatsInfo
import coupon_platform.domain.coupon_stats.dto.CouponSummaryStatsInfo
import coupon_platform.domain.issued_coupon.dto.IssuedCouponInfo
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZonedDateTime

@Service
class CouponStatsCalculator {

    fun calculateCouponStatsSummary(
        findCouponStatsInfos: List<CouponDailyStatsInfo>,
    ): CouponSummaryStatsInfo {
        if (findCouponStatsInfos.isEmpty()) {
            return CouponSummaryStatsInfo(0,0,0,0)
        }

        return CouponSummaryStatsInfo(
            totalIssuedCouponCount = findCouponStatsInfos.sumOf { it.issuedCouponCount },
            totalUsedCouponCount = findCouponStatsInfos.sumOf { it.usedCouponCount },
            totalExpiredCouponCount = findCouponStatsInfos.sumOf { it.expiredCouponCount },
            totalCouponUsageRate = findCouponStatsInfos.sumOf { it.couponUsageRate } / findCouponStatsInfos.size,
        )
    }

    fun calculateCouponDailyStats(
        issuedCoupons: List<IssuedCouponInfo>,
    ): List<CouponDailyStatsInfo> {
        val groupedByDate: Map<LocalDate, List<IssuedCouponInfo>> = issuedCoupons.groupBy { it.createdAt }

        return groupedByDate.map { (statsDate: LocalDate, coupons: List<IssuedCouponInfo>) ->
            val issuedCouponCount = coupons.size.toLong()
            val usedCouponCount = coupons.count { it.isUsed }.toLong()
            val expiredCouponCount = coupons.count { it.expirationPeriod.isBefore(ZonedDateTime.now()) }.toLong()
            val couponUsageRate = when {
                issuedCouponCount > 0 -> usedCouponCount.toDouble() / issuedCouponCount * 100
                else -> 0
            }

            CouponDailyStatsInfo(
                issuedCouponCount = issuedCouponCount,
                usedCouponCount = usedCouponCount,
                expiredCouponCount = expiredCouponCount,
                couponUsageRate = couponUsageRate.toInt(),
                statsDate = statsDate
            )
        }
    }
}