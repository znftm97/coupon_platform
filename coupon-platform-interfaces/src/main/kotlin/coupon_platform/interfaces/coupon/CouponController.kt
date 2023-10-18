package coupon_platform.interfaces.coupon

import coupon_platform.application.coupon.CouponFacade
import coupon_platform.interfaces.common.response.BaseResponse
import coupon_platform.interfaces.coupon.dto.CouponCreateRequest
import coupon_platform.interfaces.coupon.dto.CouponResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/coupons")
class CouponController(
    private val couponFacade: CouponFacade,
) {

    @PostMapping
    fun createCoupon(@RequestBody couponCreateRequest: CouponCreateRequest): BaseResponse<String> {
        val couponName = couponFacade.createCoupon(couponCreateRequest.toCommand())
        return BaseResponse.success(couponName)
    }

    @GetMapping
    fun findCoupon(@RequestParam("name") name: String): BaseResponse<CouponResponse> {
        val couponInfo = couponFacade.findCouponByName(name)
        return BaseResponse.success(CouponResponse.toResponse(couponInfo))
    }

}