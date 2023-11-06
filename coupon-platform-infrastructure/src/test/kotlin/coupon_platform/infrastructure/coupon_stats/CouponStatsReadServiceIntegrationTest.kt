package coupon_platform.infrastructure.coupon_stats

import coupon_platform.domain.couponstats.dto.CouponDailyStatsInfo
import coupon_platform.domain.couponstats.service.CouponDailyStatsReadService
import coupon_platform.infrastructure.support.BaseIntegrationTest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.time.LocalDate

class CouponStatsReadServiceIntegrationTest(
    private val couponDailyStatsReadService: CouponDailyStatsReadService,
) : BaseIntegrationTest, FunSpec({

    test("쿠폰 통계 조회") {
        val startDate = LocalDate.of(2023, 10, 15)
        val endDate = LocalDate.of(2023, 10, 16)

        val results: List<CouponDailyStatsInfo> = couponDailyStatsReadService.findCouponStats(startDate, endDate)

        results.size shouldBeEqual 2
    }

})