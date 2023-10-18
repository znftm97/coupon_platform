package coupon_platform.infrastructure.coupon_stats

import coupon_platform.domain.coupon_stats.dto.CouponDailyStatsInfo
import coupon_platform.domain.coupon_stats.service.CouponStatsReadService
import coupon_platform.domain.coupon_stats.service.CouponStatsStoreService
import coupon_platform.infrastructure.support.BaseIntegrationTest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.time.LocalDate

class CouponStatsStoreServiceIntegrationTest(
    private val couponStatsStoreService: CouponStatsStoreService,
    private val couponStatsReadService: CouponStatsReadService,
) : BaseIntegrationTest, FunSpec({

    test("쿠폰 통계 저장") {
        val couponDailyStatsInfos: List<CouponDailyStatsInfo> = listOf(
            CouponDailyStatsInfo(1, 1, 0, 100, LocalDate.now()),
            CouponDailyStatsInfo(1, 1, 0, 100, LocalDate.now().plusDays(1))
        )

        couponStatsStoreService.saveDailyStats(couponDailyStatsInfos)

        val result: List<CouponDailyStatsInfo> = couponStatsReadService.findCouponStats(LocalDate.now(), LocalDate.now().plusDays(1))
        result.size shouldBeEqual 2
    }
})