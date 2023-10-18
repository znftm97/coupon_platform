package coupon_platform.infrastructure.coupon_stats

import coupon_platform.domain.coupon_stats.dto.CouponDailyStatsInfo
import coupon_platform.domain.coupon_stats.service.CouponStatsReadService
import coupon_platform.infrastructure.support.BaseIntegrationTest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.time.LocalDate

class CouponStatsReadServiceIntegrationTest(
    private val couponStatsReadService: CouponStatsReadService,
) : BaseIntegrationTest, FunSpec({

    test("쿠폰 통계 조회") {
        val startDate = LocalDate.of(2023, 10, 15)
        val endDate = LocalDate.of(2023, 10, 16)

        val results: List<CouponDailyStatsInfo> = couponStatsReadService.findCouponStats(startDate, endDate)

        results.size shouldBeEqual 2
    }

})