package coupon_platform.infrastructure.coupon_stats

import coupon_platform.domain.couponstats.dto.CouponDailyStatsInfo
import coupon_platform.domain.couponstats.service.CouponDailyStatsReadService
import coupon_platform.domain.couponstats.service.CouponDailyStatsStoreService
import coupon_platform.infrastructure.support.BaseIntegrationTest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.time.LocalDate

class CouponStatsStoreServiceIntegrationTest(
    private val couponDailyStatsStoreService: CouponDailyStatsStoreService,
    private val couponDailyStatsReadService: CouponDailyStatsReadService,
) : BaseIntegrationTest, FunSpec({

    test("쿠폰 통계 저장") {
        val couponDailyStatsInfos: List<CouponDailyStatsInfo> = listOf(
            CouponDailyStatsInfo(1, 1, 0, 100, LocalDate.now()),
            CouponDailyStatsInfo(1, 1, 0, 100, LocalDate.now().plusDays(1))
        )

        couponDailyStatsStoreService.saveDailyStats(couponDailyStatsInfos)

        val result: List<CouponDailyStatsInfo> = couponDailyStatsReadService.findCouponStats(LocalDate.now(), LocalDate.now().plusDays(1))
        result.size shouldBeEqual 2
    }
})