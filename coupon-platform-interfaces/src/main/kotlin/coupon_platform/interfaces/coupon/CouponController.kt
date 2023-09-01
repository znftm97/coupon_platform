package coupon_platform.interfaces.coupon

import coupon_platform.domain.coupon.service.CouponService
import coupon_platform.interfaces.common.response.BaseResponse
import coupon_platform.interfaces.coupon.dto.CreateCouponRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/coupons")
class CouponController(
    val couponService: CouponService,
) {

    @PostMapping
    fun createCoupon(createCouponRequest: CreateCouponRequest): BaseResponse<String> {
        val couponName = couponService.createCoupon(createCouponRequest.toCommand())
        return BaseResponse.success(couponName)
    }

}