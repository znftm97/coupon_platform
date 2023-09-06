package coupon_platform.interfaces.issue_coupon

import coupon_platform.application.issued_coupon.IssuedCouponFacade
import coupon_platform.interfaces.common.response.BaseResponse
import coupon_platform.interfaces.issue_coupon.dto.IssueCouponRequest
import coupon_platform.interfaces.issue_coupon.dto.toCommand
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/issued_coupons")
class IssuedCouponController(
    val issuedCouponFacade: IssuedCouponFacade,
) {

    @PostMapping
    fun issueCoupon(@RequestBody issueCouponRequest: IssueCouponRequest): BaseResponse<Long> {
        val issuedCouponId = issuedCouponFacade.issueCoupon(issueCouponRequest.toCommand())
        return BaseResponse.success(issuedCouponId)
    }
}