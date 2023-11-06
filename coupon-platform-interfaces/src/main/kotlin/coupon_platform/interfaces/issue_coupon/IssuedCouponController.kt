package coupon_platform.interfaces.issue_coupon

import coupon_platform.application.issuedcoupon.IssuedCouponFacade
import coupon_platform.interfaces.common.response.BaseResponse
import coupon_platform.interfaces.issue_coupon.dto.CouponIssueRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/issued-coupons")
class IssuedCouponController(
    private val issuedCouponFacade: IssuedCouponFacade,
) {

    @PostMapping
    fun issueCoupon(@RequestBody couponIssueRequest: CouponIssueRequest): BaseResponse<Long> {
        val issuedCouponId = issuedCouponFacade.issueCoupon(couponIssueRequest.toCommand())
        return BaseResponse.success(issuedCouponId)
    }

    @PostMapping("/coupon-code/{couponCode}/{accountId}")
    fun issueCouponByCouponCode(
        @PathVariable("couponCode") couponCode: String,
        @PathVariable("accountId") accountId: Long,
    ): BaseResponse<Long> {
        val couponId = issuedCouponFacade.issueCouponByCouponCode(couponCode, accountId)
        return BaseResponse.success(couponId)
    }

    @PostMapping("/local-caching")
    fun cachingIssuedCouponToLocal(): BaseResponse<Unit> {
        issuedCouponFacade.cachingIssuedCouponToLocal()
        return BaseResponse.success(Unit)
    }

}