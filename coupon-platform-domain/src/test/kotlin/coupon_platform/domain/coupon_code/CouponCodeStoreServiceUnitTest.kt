package coupon_platform.domain.coupon_code

import coupon_platform.domain.common.RandomNumberGenerator
import coupon_platform.domain.coupon_code.dto.CouponCodeCreateCommand
import coupon_platform.domain.coupon_code.dto.CouponCodeInfo
import coupon_platform.domain.coupon_code.repository.CouponCodeStore
import coupon_platform.domain.coupon_code.service.CouponCodeStoreService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk
import java.time.ZonedDateTime

class CouponCodeStoreServiceUnitTest : BehaviorSpec({

    val couponCodeStoreMock = mockk<CouponCodeStore> {}
    val uuidGeneratorMock = mockk<RandomNumberGenerator> {}
    val tsidGeneratorMock = mockk<RandomNumberGenerator> {}
    val couponCodeStoreServiceMock = mockk<CouponCodeStoreService> {
        every { couponCodeStore } returns couponCodeStoreMock
        every { uuidGenerator } returns uuidGeneratorMock
        every { tsidGenerator } returns tsidGeneratorMock
    }

    given("쿠폰 코드 생성 객체(couponCodeCreateCommand)가 주어지고") {
        val couponCodeCreateCommand = CouponCodeCreateCommand(1, ZonedDateTime.now())
        val couponCodeInfo = CouponCodeInfo(
            "externalId",
            1,
            "xxxx-xxxx-xxxx-xxxx",
            ZonedDateTime.now(),
        )
        every {  couponCodeStoreServiceMock.createCouponCode(couponCodeCreateCommand) } returns couponCodeInfo

        `when`("couponCodeStoreService의 createCouponCode() 함수를 호출했을 때") {
            val result = couponCodeStoreServiceMock.createCouponCode(couponCodeCreateCommand)

            then("쿠폰 코드를 생성할 수 있다.") {
                result.externalId shouldBeEqual "externalId"
                result.couponId shouldBeEqual 1
                result.couponCode shouldBeEqual "xxxx-xxxx-xxxx-xxxx"
                result.expirationPeriod shouldBeEqual couponCodeInfo.expirationPeriod
            }
        }
    }

})