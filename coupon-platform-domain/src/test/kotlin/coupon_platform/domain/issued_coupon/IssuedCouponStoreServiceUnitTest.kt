package coupon_platform.domain.issued_coupon

import coupon_platform.domain.common.CommonConstants.EXTERNAL_ID_LENGTH
import coupon_platform.domain.common.RandomNumberGenerator
import coupon_platform.domain.issued_coupon.dto.IssueCouponCommand
import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import coupon_platform.domain.issued_coupon.repository.IssuedCouponStore
import coupon_platform.domain.issued_coupon.service.IssuedCouponStoreService
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
            ZonedDateTime.now().plusDays(7)
        )
        val issuedCoupon = IssuedCoupon.of(
            1L,
            1L,
            ZonedDateTime.now().plusDays(7),
            "externalId"
        )

        every { issuedCouponStore.issueCoupon(any()) } returns issuedCoupon
        every { randomNumberGenerator.generate(EXTERNAL_ID_LENGTH) } returns "externalId"

        val result = issuedCouponStoreService.issueCoupon(issuedCouponCommand)

        verify { issuedCouponStore.issueCoupon(any()) }
        verify { randomNumberGenerator.generate(EXTERNAL_ID_LENGTH) }
        result shouldBeEqual issuedCoupon.couponId
    }
})