package coupon_platform.domain.couponstats

import coupon_platform.domain.couponstats.dto.CouponDailyStatsInfo
import coupon_platform.domain.couponstats.dto.CouponSummaryStatsInfo
import coupon_platform.domain.couponstats.service.CouponStatsCalculator
import coupon_platform.domain.issuedcoupon.dto.IssuedCouponInfo
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.time.LocalDate
import java.time.ZonedDateTime

class CouponStatsCalculatorUnitTest: FeatureSpec({

    val couponStatsCalculator = CouponStatsCalculator()

    feature("발급된 쿠폰정보가 주어지면, 일일 통계 데이터를 계산한다.") {
        scenario("발급된 쿠폰이 존재하지 않는 경우") {
            val results: List<CouponDailyStatsInfo> = couponStatsCalculator.calculateCouponDailyStats(emptyList())
            results.size shouldBeEqual 0
        }

        scenario("하루에 3개의 쿠폰이 발급된 경우") {
            val info1 = IssuedCouponInfo(true, ZonedDateTime.now().plusDays(7), LocalDate.now())
            val info2 = IssuedCouponInfo(true, ZonedDateTime.now().plusDays(7), LocalDate.now())
            val info3 = IssuedCouponInfo(false, ZonedDateTime.now().minusDays(7), LocalDate.now())
            val issuedCouponInfos: List<IssuedCouponInfo> = listOf(info1, info2, info3)

            val results: List<CouponDailyStatsInfo> = couponStatsCalculator.calculateCouponDailyStats(issuedCouponInfos)
            val couponDailyStatsInfo = results.get(0)

            results.size shouldBeEqual 1
            couponDailyStatsInfo.issuedCouponCount shouldBeEqual 3
            couponDailyStatsInfo.usedCouponCount shouldBeEqual 2
            couponDailyStatsInfo.expiredCouponCount shouldBeEqual 1
            couponDailyStatsInfo.couponUsageRate shouldBeEqual 66
        }

        scenario("3일에 각각 1개의 쿠폰이 발급된 경우") {
            val info1 = IssuedCouponInfo(true, ZonedDateTime.now().plusDays(7), LocalDate.now())
            val info2 = IssuedCouponInfo(true, ZonedDateTime.now().plusDays(7), LocalDate.now().plusDays(1))
            val info3 = IssuedCouponInfo(false, ZonedDateTime.now().minusDays(7), LocalDate.now().plusDays(2))
            val issuedCouponInfos: List<IssuedCouponInfo> = listOf(info1, info2, info3)

            val results: List<CouponDailyStatsInfo> = couponStatsCalculator.calculateCouponDailyStats(issuedCouponInfos)
            val result1 = results.get(0)
            val result2 = results.get(1)
            val result3 = results.get(2)

            results.size shouldBeEqual 3
            result1.issuedCouponCount shouldBeEqual 1
            result1.usedCouponCount shouldBeEqual 1
            result1.expiredCouponCount shouldBeEqual 0
            result1.couponUsageRate shouldBeEqual 100

            result2.issuedCouponCount shouldBeEqual 1
            result2.usedCouponCount shouldBeEqual 1
            result2.expiredCouponCount shouldBeEqual 0
            result2.couponUsageRate shouldBeEqual 100

            result3.issuedCouponCount shouldBeEqual 1
            result3.usedCouponCount shouldBeEqual 0
            result3.expiredCouponCount shouldBeEqual 1
            result3.couponUsageRate shouldBeEqual 0
        }
    }

    feature("일일 통계 데이터의 합계 및 평균 데이터를 계산한다.") {
        scenario("일일 통계 데이터가 존재하지 않는 경우") {
            val result: CouponSummaryStatsInfo = couponStatsCalculator.calculateCouponStatsSummary(emptyList())

            result.totalIssuedCouponCount shouldBeEqual 0
            result.totalUsedCouponCount shouldBeEqual 0
            result.totalExpiredCouponCount shouldBeEqual 0
            result.totalCouponUsageRate shouldBeEqual 0
        }

        scenario("4일간의 일일 통계 데이터가 주어진다.") {
            val info1 = CouponDailyStatsInfo(10, 5, 5, 50, LocalDate.now())
            val info2 = CouponDailyStatsInfo(10, 0, 0, 0, LocalDate.now())
            val info3 = CouponDailyStatsInfo(5, 5, 0, 100, LocalDate.now())
            val info4 = CouponDailyStatsInfo(5, 0, 5, 0, LocalDate.now())
            val couponDailyStatsInfos: List<CouponDailyStatsInfo> = listOf(info1, info2, info3, info4)

            val result: CouponSummaryStatsInfo = couponStatsCalculator.calculateCouponStatsSummary(couponDailyStatsInfos)

            result.totalIssuedCouponCount shouldBeEqual couponDailyStatsInfos.sumOf { it.issuedCouponCount }
            result.totalUsedCouponCount shouldBeEqual couponDailyStatsInfos.sumOf { it.usedCouponCount }
            result.totalExpiredCouponCount shouldBeEqual couponDailyStatsInfos.sumOf { it.expiredCouponCount }
            result.totalCouponUsageRate shouldBeEqual couponDailyStatsInfos.sumOf { it.couponUsageRate } / couponDailyStatsInfos.size
        }

        scenario("2일간 쿠폰이 발급되지 않은 통계 데이터가 주어진다.") {
            val info1 = CouponDailyStatsInfo(0, 0, 0, 0, LocalDate.now())
            val info2 = CouponDailyStatsInfo(0, 0, 0, 0, LocalDate.now())
            val couponDailyStatsInfos: List<CouponDailyStatsInfo> = listOf(info1, info2)

            val result: CouponSummaryStatsInfo = couponStatsCalculator.calculateCouponStatsSummary(couponDailyStatsInfos)

            result.totalIssuedCouponCount shouldBeEqual couponDailyStatsInfos.sumOf { it.issuedCouponCount }
            result.totalUsedCouponCount shouldBeEqual couponDailyStatsInfos.sumOf { it.usedCouponCount }
            result.totalExpiredCouponCount shouldBeEqual couponDailyStatsInfos.sumOf { it.expiredCouponCount }
            result.totalCouponUsageRate shouldBeEqual couponDailyStatsInfos.sumOf { it.couponUsageRate } / couponDailyStatsInfos.size
        }

        scenario("2일간 발급된 쿠폰을 모두 사용한 통계 데이터가 주어진다.") {
            val info1 = CouponDailyStatsInfo(10, 10, 0, 100, LocalDate.now())
            val info2 = CouponDailyStatsInfo(10, 10, 0, 100, LocalDate.now())
            val couponDailyStatsInfos: List<CouponDailyStatsInfo> = listOf(info1, info2)

            val result: CouponSummaryStatsInfo = couponStatsCalculator.calculateCouponStatsSummary(couponDailyStatsInfos)

            result.totalIssuedCouponCount shouldBeEqual couponDailyStatsInfos.sumOf { it.issuedCouponCount }
            result.totalUsedCouponCount shouldBeEqual couponDailyStatsInfos.sumOf { it.usedCouponCount }
            result.totalExpiredCouponCount shouldBeEqual couponDailyStatsInfos.sumOf { it.expiredCouponCount }
            result.totalCouponUsageRate shouldBeEqual couponDailyStatsInfos.sumOf { it.couponUsageRate } / couponDailyStatsInfos.size
        }
    }

})