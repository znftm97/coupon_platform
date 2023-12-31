package coupon_platform.interfaces.couponstats

import coupon_platform.application.couponstats.CouponStatsFacade
import coupon_platform.application.couponstats.CouponStatsFacadeV2
import coupon_platform.domain.couponstats.dto.CouponStatsInfo
import coupon_platform.interfaces.common.response.BaseResponse
import coupon_platform.interfaces.couponstats.dto.CouponStatsResponse
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api")
class CouponStatsController(
    private val couponStatsFacade: CouponStatsFacade,
    private val couponStatsFacadeV2: CouponStatsFacadeV2,
) {

    /*글로벌 캐싱 버전*/
    @GetMapping("/v1/coupon-stats/{startDate}/{endDate}")
    fun findCouponStats(
        @PathVariable("startDate") startDate: LocalDate,
        @PathVariable("endDate") endDate: LocalDate,
    ): BaseResponse<CouponStatsResponse> {
        val couponStatsInfo: CouponStatsInfo = couponStatsFacade.findCouponStats(startDate, endDate)
        return BaseResponse.success(CouponStatsResponse.toResponse(couponStatsInfo))
    }

    /*로컬 캐싱 버전*/
    @GetMapping("/v2/coupon-stats/{startDate}/{endDate}")
    fun findCouponStatsV2(
        @PathVariable("startDate") startDate: LocalDate,
        @PathVariable("endDate") endDate: LocalDate,
    ): BaseResponse<CouponStatsResponse> {
        val couponStatsInfo: CouponStatsInfo = couponStatsFacadeV2.findCouponStats(startDate, endDate)
        return BaseResponse.success(CouponStatsResponse.toResponse(couponStatsInfo))
    }
}