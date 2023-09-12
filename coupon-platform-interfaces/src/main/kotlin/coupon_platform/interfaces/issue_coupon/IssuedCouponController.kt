package coupon_platform.interfaces.issue_coupon

import coupon_platform.application.issued_coupon.IssuedCouponFacade
import coupon_platform.interfaces.common.response.BaseResponse
import coupon_platform.interfaces.issue_coupon.dto.CouponIssueRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/issued_coupons")
class IssuedCouponController(
    val issuedCouponFacade: IssuedCouponFacade,
) {

    @PostMapping
    suspend fun issueCoupon(@RequestBody couponIssueRequest: CouponIssueRequest): BaseResponse<Long> {
        val issuedCouponId = issuedCouponFacade.issueCoupon(couponIssueRequest.toCommand())
        return BaseResponse.success(issuedCouponId)
    }

    @PostMapping("/coupon_code/{coupon-code}")
    suspend fun issueCouponByCouponCode(@PathVariable("coupon-code") couponCode: String): BaseResponse<Long> {
        val issuedCouponInfo = issuedCouponFacade.issueCouponByCouponCode(couponCode)
        return BaseResponse.success(issuedCouponInfo)
    }

}