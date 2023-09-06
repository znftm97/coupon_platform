package coupon_platform.interfaces.coupon

import com.coupon_platform.couponplatformapplication.coupon.CouponFacade
import coupon_platform.interfaces.common.response.BaseResponse
import coupon_platform.interfaces.coupon.dto.CreateCouponRequest
import coupon_platform.interfaces.coupon.dto.QueryCouponResponse
import coupon_platform.interfaces.coupon.dto.toCommand
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/coupons")
class CouponController(
    val coupoonFacade: CouponFacade,
) {

    @PostMapping
    fun createCoupon(@RequestBody createCouponRequest: CreateCouponRequest): BaseResponse<String> {
        val couponName = coupoonFacade.createCoupon(createCouponRequest.toCommand())
        return BaseResponse.success(couponName)
    }

    @GetMapping
    fun queryCoupon(@RequestParam("name") name: String): BaseResponse<QueryCouponResponse> {
        val couponInfo = coupoonFacade.readCoupon(name)
        return BaseResponse.success(QueryCouponResponse.toResponse(couponInfo))
    }

}