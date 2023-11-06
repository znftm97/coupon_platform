package coupon_platform.domain.coupon

import coupon_platform.domain.coupon.entity.Coupon
import coupon_platform.domain.coupon.repository.CouponReader
import coupon_platform.domain.coupon.service.CouponReadService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk

class CouponReadServiceUnitTest : FunSpec({

    val couponReader = mockk<CouponReader>()
    val couponReadService = CouponReadService(couponReader)

    test("쿠폰 이름으로 쿠폰을 조회할 수 있다.") {
        val name = "name"
        val coupon = Coupon.of(
            name,
            Coupon.DiscountType.PRICE,
            1000,
            "externalId",
        )
        every { couponReader.findCouponByName(name) } returns coupon
        val result = couponReadService.findCouponByName(name)

        result.externalId shouldBeEqual "externalId"
        result.name shouldBeEqual name
        result.discountType shouldBeEqual Coupon.DiscountType.PRICE
        result.price shouldBeEqual coupon.price
    }

    test("쿠폰id로 쿠폰을 조회할 수 있다.") {
        val couponId = 1L
        val coupon = Coupon.of(
            "name",
            Coupon.DiscountType.PRICE,
            1000,
            "externalId",
        )
        every { couponReader.findCouponById(couponId) } returns coupon
        val result = couponReadService.findCouponById(couponId)

        result.externalId shouldBeEqual "externalId"
        result.name shouldBeEqual "name"
        result.discountType shouldBeEqual Coupon.DiscountType.PRICE
        result.price shouldBeEqual coupon.price
    }
})