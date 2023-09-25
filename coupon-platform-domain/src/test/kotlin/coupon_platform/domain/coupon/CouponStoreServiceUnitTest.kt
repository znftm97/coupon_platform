package coupon_platform.domain.coupon

import coupon_platform.domain.coupon.entity.Coupon
import coupon_platform.domain.coupon.service.CouponStoreService
import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk

class CouponStoreServiceUnitTest : FunSpec({

    val couponStoreServiceMock = mockk<CouponStoreService> {
        every { couponStore } returns mockk()
        every { randomNumberGenerator } returns mockk()
    }

    test("쿠폰을 생성할 수 있다.") {
        val name = "name"
        val couponCreateCommand = CouponCreateCommand(
            name,
            Coupon.DiscountType.PRICE,
            1000,
        )
        every { runBlocking { couponStoreServiceMock.createCoupon(couponCreateCommand) } } returns name
        couponStoreServiceMock.createCoupon(couponCreateCommand) shouldBeEqual name
    }

})