package coupon_platform.domain.coupon

import coupon_platform.domain.coupon.dto.CouponInfo
import coupon_platform.domain.coupon.entity.Coupon
import coupon_platform.domain.coupon.service.CouponReadService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk

class CouponReadServiceUnitTest : FunSpec({

    val couponReadServiceMock = mockk<CouponReadService> {
        every { couponReader } returns mockk()
    }

    test("쿠폰 이름으로 쿠폰을 조회할 수 있다.") {
        val name = "name"
        val couponInfo = CouponInfo(
            1,
            "externalId",
            name,
            Coupon.ApplyType.ACCOUNT,
            Coupon.DiscountType.PRICE,
        )
        every { couponReadServiceMock.findCouponByName(name) } returns couponInfo
        val result = couponReadServiceMock.findCouponByName(name)

        result.id shouldBeEqual 1
        result.externalId shouldBeEqual "externalId"
        result.name shouldBeEqual name
        result.applyType shouldBeEqual Coupon.ApplyType.ACCOUNT
        result.discountType shouldBeEqual Coupon.DiscountType.PRICE

    }

    test("쿠폰id로 쿠폰을 조회할 수 있다.") {
        val couponId = 1L
        val couponInfo = CouponInfo(
            couponId,
            "externalId",
            "name",
            Coupon.ApplyType.ACCOUNT,
            Coupon.DiscountType.PRICE,
        )
        every { couponReadServiceMock.findCouponById(couponId) } returns couponInfo
        val result = couponReadServiceMock.findCouponById(couponId)

        result.id shouldBeEqual couponId
        result.externalId shouldBeEqual "externalId"
        result.name shouldBeEqual "name"
        result.applyType shouldBeEqual Coupon.ApplyType.ACCOUNT
        result.discountType shouldBeEqual Coupon.DiscountType.PRICE
    }

})