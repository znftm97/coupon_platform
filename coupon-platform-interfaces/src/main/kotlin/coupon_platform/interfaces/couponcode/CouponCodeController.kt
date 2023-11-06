package coupon_platform.interfaces.couponcode

import coupon_platform.application.couponcode.CouponCodeFacade
import coupon_platform.interfaces.common.response.BaseResponse
import coupon_platform.interfaces.couponcode.dto.CouponCodeCreateRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/coupon-code")
class CouponCodeController(
    private val couponCodeFacade: CouponCodeFacade,
) {

    @PostMapping
    fun createCouponCode(@RequestBody couponCodeCreateRequest: CouponCodeCreateRequest): BaseResponse<String> {
        val couponCode = couponCodeFacade.createCouponCode(couponCodeCreateRequest.toCommand())
        return BaseResponse.success(couponCode)
    }

}