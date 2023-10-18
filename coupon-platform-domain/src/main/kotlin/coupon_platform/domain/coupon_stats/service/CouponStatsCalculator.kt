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
            findCouponStatsInfos.sumOf { it.issuedCouponCount },
            findCouponStatsInfos.sumOf { it.usedCouponCount },
            findCouponStatsInfos.sumOf { it.expiredCouponCount },
            findCouponStatsInfos.sumOf { it.couponUsageRate } / findCouponStatsInfos.size,
        )
    }

    fun calculateCouponStatsDaily(
        issuedCoupons: List<IssuedCouponInfo>,
    ): List<CouponDailyStatsInfo> {
        val groupedByDate: Map<LocalDate, List<IssuedCouponInfo>> = issuedCoupons.groupBy { it.createdAt }

        return groupedByDate.map { (date: LocalDate, coupons: List<IssuedCouponInfo>) ->
            val issuedCount = coupons.size.toLong()
            val usedCount = coupons.count { it.isUsed }.toLong()
            val expiredCount = coupons.count { it.expirationPeriod.isBefore(ZonedDateTime.now()) }.toLong()
            val usageRate = when {
                issuedCount > 0 -> usedCount.toDouble() / issuedCount * 100
                else -> 0
            }

            println(usedCount / issuedCount)
            println(usedCount / issuedCount * 100)

            CouponDailyStatsInfo(
                issuedCouponCount = issuedCount,
                usedCouponCount = usedCount,
                expiredCouponCount = expiredCount,
                couponUsageRate = usageRate.toInt(),
                statsDate = date
            )
        }
    }
}