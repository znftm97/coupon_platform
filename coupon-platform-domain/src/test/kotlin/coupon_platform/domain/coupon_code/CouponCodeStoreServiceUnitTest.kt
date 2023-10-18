package coupon_platform.domain.coupon_code

import coupon_platform.domain.common.CommonConstants.EXTERNAL_ID_LENGTH
import coupon_platform.domain.common.RandomNumberGenerator
import coupon_platform.domain.coupon_code.dto.CouponCodeCreateCommand
import coupon_platform.domain.coupon_code.dto.CouponCodeInfo
import coupon_platform.domain.coupon_code.entity.CouponCode
import coupon_platform.domain.coupon_code.repository.CouponCodeStore
import coupon_platform.domain.coupon_code.service.CouponCodeStoreService
import coupon_platform.domain.coupon_code.service.CouponCodeStoreService.Companion.COUPON_CODE_LENGTH
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.ZonedDateTime

class CouponCodeStoreServiceUnitTest : FunSpec({

    val couponCodeStore = mockk<CouponCodeStore> {}
    val uuidGenerator = mockk<RandomNumberGenerator> {}
    val tsidGenerator = mockk<RandomNumberGenerator> {}
    val couponCodeStoreService = CouponCodeStoreService(couponCodeStore, uuidGenerator, tsidGenerator)

    test("쿠폰 코드 생성") {
        val couponCodeCreateCommand = CouponCodeCreateCommand(1, ZonedDateTime.now())
        val couponCode = CouponCode.of(
            1,
            "xxxx-xxxx-xxxx-xxxx",
            ZonedDateTime.now().plusDays(7),
            "externalId"
        )
        every { couponCodeStore.createCouponCode(any()) } returns couponCode
        every { tsidGenerator.generate(EXTERNAL_ID_LENGTH) } returns "EXTERNAL_ID"
        every { uuidGenerator.generate(COUPON_CODE_LENGTH) } returns "xxxx-xxxx-xxxx-xxxx"

        val result = couponCodeStoreService.createCouponCode(couponCodeCreateCommand)

        verify { couponCodeStore.createCouponCode(couponCode) }
        verify { tsidGenerator.generate(EXTERNAL_ID_LENGTH) }
        verify { uuidGenerator.generate(COUPON_CODE_LENGTH) }
        result shouldBe CouponCodeInfo.toInfo(couponCode)
    }

})