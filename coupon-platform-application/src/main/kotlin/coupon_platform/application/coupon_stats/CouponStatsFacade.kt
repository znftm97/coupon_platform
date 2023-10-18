package coupon_platform.application.coupon_stats

import coupon_platform.domain.coupon_stats.dto.CouponDailyStatsInfo
import coupon_platform.domain.coupon_stats.dto.CouponStatsInfo
import coupon_platform.domain.coupon_stats.service.CouponStatsCalculator
import coupon_platform.domain.coupon_stats.service.CouponStatsReadService
import coupon_platform.domain.coupon_stats.service.CouponStatsStoreService
import coupon_platform.domain.issued_coupon.dto.IssuedCouponInfo
import coupon_platform.domain.issued_coupon.service.IssuedCouponReadService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CouponStatsFacade(
    private val issuedCouponReadService: IssuedCouponReadService,
    private val couponStatsReadService: CouponStatsReadService,
    private val couponStatsStoreService: CouponStatsStoreService,
    private val couponStatsCalculator: CouponStatsCalculator,
) {
    fun findCouponStats(startDate: LocalDate, endDate: LocalDate): CouponStatsInfo {
        val findCouponDailyStatsInfos: MutableList<CouponDailyStatsInfo> =
            couponStatsReadService.findCouponStats(startDate, endDate)
                .sortedBy { it.statsDate }
                .toMutableList()

        if(isAllExistStats(endDate, findCouponDailyStatsInfos)) {
            return CouponStatsInfo(
                couponDailyStatsInfos = findCouponDailyStatsInfos,
                couponSummaryStatsInfo = couponStatsCalculator.calculateCouponStatsSummary(findCouponDailyStatsInfos),
            )
        }

        val emptyDates: List<LocalDate> = getEmptyStatsDate(startDate, endDate, findCouponDailyStatsInfos)
        val issuedCoupons: List<IssuedCouponInfo> = issuedCouponReadService.findIssuedCouponsInDates(emptyDates)
        val couponDailyStatsInfos: List<CouponDailyStatsInfo> = couponStatsCalculator.calculateCouponStatsDaily(issuedCoupons)
        couponStatsStoreService.saveDailyStats(couponDailyStatsInfos)
        findCouponDailyStatsInfos.addAll(couponDailyStatsInfos)

        return CouponStatsInfo(
            couponDailyStatsInfos = findCouponDailyStatsInfos.sortedBy { it.statsDate },
            couponSummaryStatsInfo = couponStatsCalculator.calculateCouponStatsSummary(findCouponDailyStatsInfos)
        )
    }

    private fun isAllExistStats(
        endDate: LocalDate,
        findCouponStatsInfos: List<CouponDailyStatsInfo>,
    ): Boolean {
        if(findCouponStatsInfos.isEmpty()) {
            return false
        }

        return findCouponStatsInfos.last().statsDate == endDate
    }

    private fun getEmptyStatsDate(
        startDate: LocalDate,
        endDate: LocalDate,
        findCouponStatsInfos: List<CouponDailyStatsInfo>,
    ): List<LocalDate> {
        val emptyDates: MutableList<LocalDate> = mutableListOf()
        val existDates: Set<LocalDate> = findCouponStatsInfos.map { it.statsDate }.toSet()
        var currentDate = startDate

        while (currentDate <= endDate) {
            if (!existDates.contains(currentDate)) {
                emptyDates.add(currentDate)
            }
            currentDate = currentDate.plusDays(1)
        }

        return emptyDates
    }
}