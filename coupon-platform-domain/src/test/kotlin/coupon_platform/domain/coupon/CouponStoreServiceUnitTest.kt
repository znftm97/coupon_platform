package coupon_platform.domain.coupon

import coupon_platform.domain.common.CommonConstants.EXTERNAL_ID_LENGTH
import coupon_platform.domain.common.RandomNumberGenerator
import coupon_platform.domain.coupon.entity.Coupon
import coupon_platform.domain.coupon.repository.CouponStore
import coupon_platform.domain.coupon.service.CouponStoreService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class CouponStoreServiceUnitTest : FunSpec({

    val couponStore = mockk<CouponStore>()
    val randomNumberGenerator = mockk<RandomNumberGenerator>()
    val couponCreateCommand = mockk<CouponCreateCommand>()
    val couponStoreService = CouponStoreService(couponStore, randomNumberGenerator)

    test("쿠폰을 생성할 수 있다.") {
        val coupon = Coupon.of(
            "name",
            Coupon.DiscountType.PRICE,
            1000,
            "externalId",
        )

        every { couponStore.saveCoupon(coupon) } returns coupon
        every { couponCreateCommand.toEntity(coupon.externalId) } returns coupon
        every { randomNumberGenerator.generate(EXTERNAL_ID_LENGTH) } returns coupon.externalId

        val couponName = couponStoreService.createCoupon(couponCreateCommand)

        verify { couponStore.saveCoupon(coupon) }
        verify { couponCreateCommand.toEntity(coupon.externalId) }
        verify { randomNumberGenerator.generate(EXTERNAL_ID_LENGTH) }
        couponName shouldBeEqual coupon.name
    }
})