package coupon_platform.application.couponstats

import coupon_platform.domain.couponstats.dto.CouponDailyStatsInfo
import coupon_platform.domain.couponstats.dto.CouponStatsInfo
import coupon_platform.domain.couponstats.service.CouponDailyStatsReadService
import coupon_platform.domain.couponstats.service.CouponDailyStatsStoreService
import coupon_platform.domain.couponstats.service.CouponStatsCalculator
import coupon_platform.domain.issuedcoupon.dto.IssuedCouponInfo
import coupon_platform.domain.issuedcoupon.service.IssuedCouponReadService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CouponStatsFacade(
    private val issuedCouponReadService: IssuedCouponReadService,
    private val couponDailyStatsReadService: CouponDailyStatsReadService,
    private val couponDailyStatsStoreService: CouponDailyStatsStoreService,
    private val couponStatsCalculator: CouponStatsCalculator,
) {
    fun findCouponStats(startDate: LocalDate, endDate: LocalDate): CouponStatsInfo {
        val findCouponDailyStatsInfos: MutableList<CouponDailyStatsInfo> =
            couponDailyStatsReadService.findCouponStats(startDate, endDate)
                .sortedBy { it.statsDate }
                .toMutableList()

        if (isAllExistStats(endDate, findCouponDailyStatsInfos)) {
            return CouponStatsInfo(
                couponDailyStatsInfos = findCouponDailyStatsInfos,
                couponSummaryStatsInfo = couponStatsCalculator.calculateCouponStatsSummary(findCouponDailyStatsInfos),
            )
        }

        val emptyDates: List<LocalDate> = getEmptyStatsDates(startDate, endDate, findCouponDailyStatsInfos)
        val couponDailyStatsInfos: List<CouponDailyStatsInfo> = calculateCouponDailyStats(emptyDates)
        findCouponDailyStatsInfos.addAll(couponDailyStatsInfos)

        return CouponStatsInfo(
            couponDailyStatsInfos = findCouponDailyStatsInfos.sortedBy { it.statsDate },
            couponSummaryStatsInfo = couponStatsCalculator.calculateCouponStatsSummary(findCouponDailyStatsInfos),
        )
    }

    private fun calculateCouponDailyStats(emptyDates: List<LocalDate>): List<CouponDailyStatsInfo> {
        val issuedCoupons: List<IssuedCouponInfo> = issuedCouponReadService.findIssuedCouponsInDates(emptyDates)
        val couponDailyStatsInfos: List<CouponDailyStatsInfo> = couponStatsCalculator.calculateCouponDailyStats(issuedCoupons)
        couponDailyStatsStoreService.saveDailyStats(couponDailyStatsInfos)

        return couponDailyStatsInfos
    }

    private fun isAllExistStats(
        endDate: LocalDate,
        findCouponStatsInfos: List<CouponDailyStatsInfo>,
    ): Boolean {
        if (findCouponStatsInfos.isEmpty()) {
            return false
        }

        return findCouponStatsInfos.last().statsDate == endDate
    }

    private fun getEmptyStatsDates(
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