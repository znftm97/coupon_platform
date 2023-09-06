package coupon_platform.interfaces.coupon

import coupon_platform.application.coupon.CouponFacade
import coupon_platform.domain.coupon.repository.CouponStore
import coupon_platform.interfaces.common.response.BaseResponse
import coupon_platform.interfaces.coupon.dto.CreateCouponRequest
import coupon_platform.interfaces.coupon.dto.QueryCouponResponse
import coupon_platform.interfaces.coupon.dto.toCommand
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/coupons")
class CouponController(
    val couponFacade: CouponFacade,
    val couponStore: CouponStore,
) {

    @PostMapping
    fun createCoupon(@RequestBody createCouponRequest: CreateCouponRequest): BaseResponse<String> {
        val couponName = couponFacade.createCoupon(createCouponRequest.toCommand())
        return BaseResponse.success(couponName)
    }

    @GetMapping
    fun queryCoupon(@RequestParam("name") name: String): BaseResponse<QueryCouponResponse> {
        val couponInfo = couponFacade.readCoupon(name)
        return BaseResponse.success(QueryCouponResponse.toResponse(couponInfo))
    }

}