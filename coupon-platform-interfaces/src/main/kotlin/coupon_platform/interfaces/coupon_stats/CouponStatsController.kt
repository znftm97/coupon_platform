package coupon_platform.interfaces.coupon_stats

import coupon_platform.application.coupon_stats.CouponStatsFacade
import coupon_platform.domain.coupon_stats.dto.CouponStatsInfo
import coupon_platform.interfaces.common.response.BaseResponse
import coupon_platform.interfaces.coupon_stats.dto.CouponStatsResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/coupon-stats")
class CouponStatsController(
    private val couponStatsFacade: CouponStatsFacade,
) {

    @GetMapping("/{startDate}/{endDate}")
    fun findCouponStats(
        @PathVariable("startDate") startDate: LocalDate,
        @PathVariable("endDate") endDate: LocalDate,
    ): BaseResponse<CouponStatsResponse> {
        val couponStatsInfo: CouponStatsInfo = couponStatsFacade.findCouponStats(startDate, endDate)
        return BaseResponse.success(CouponStatsResponse.toResponse(couponStatsInfo))
    }
}