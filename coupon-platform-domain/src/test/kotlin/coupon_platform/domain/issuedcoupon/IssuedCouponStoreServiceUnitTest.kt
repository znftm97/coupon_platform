package coupon_platform.domain.issuedcoupon

import coupon_platform.domain.common.CommonConstants.EXTERNAL_ID_LENGTH
import coupon_platform.domain.common.RandomNumberGenerator
import coupon_platform.domain.issuedcoupon.dto.IssueCouponCommand
import coupon_platform.domain.issuedcoupon.entitiy.IssuedCoupon
import coupon_platform.domain.issuedcoupon.repository.IssuedCouponStore
import coupon_platform.domain.issuedcoupon.service.IssuedCouponStoreService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.ZonedDateTime

class IssuedCouponStoreServiceUnitTest : FunSpec({

    val issuedCouponStore = mockk<IssuedCouponStore>()
    val randomNumberGenerator = mockk<RandomNumberGenerator>()
    val issuedCouponStoreService = IssuedCouponStoreService(issuedCouponStore, randomNumberGenerator)

    test("쿠폰 발급") {
        val issuedCouponCommand = IssueCouponCommand(
            1L,
            1L,
            ZonedDateTime.now().plusDays(7),
        )
        val issuedCoupon = IssuedCoupon.of(
            1L,
            1L,
            ZonedDateTime.now().plusDays(7),
            "externalId",
        )

        every { issuedCouponStore.saveIssuedCoupon(any()) } returns issuedCoupon
        every { randomNumberGenerator.generate(EXTERNAL_ID_LENGTH) } returns "externalId"

        val result = issuedCouponStoreService.issueCoupon(issuedCouponCommand)

        verify { issuedCouponStore.saveIssuedCoupon(any()) }
        verify { randomNumberGenerator.generate(EXTERNAL_ID_LENGTH) }
        result shouldBeEqual issuedCoupon.couponId
    }
})