package coupon_platform.domain.issued_coupon

import coupon_platform.domain.issued_coupon.dto.IssueCouponCommand
import coupon_platform.domain.issued_coupon.service.IssuedCouponStoreService
import io.kotest.common.runBlocking
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk
import java.time.ZonedDateTime

class IssuedCouponStoreServiceUnitTest : BehaviorSpec({

    val issuedCouponStoreServiceMock = mockk<IssuedCouponStoreService> {
        every { issuedCouponStore } returns mockk()
        every { randomNumberGenerator } returns mockk()
    }

    given("쿠폰 발급 요청 객체가 주어지고") {
        val couponId = 1L
        val issuedCouponCommand = IssueCouponCommand(couponId, ZonedDateTime.now())
        every { runBlocking { issuedCouponStoreServiceMock.issueCoupon(issuedCouponCommand) } } returns couponId

        `when`("쿠폰 발급 요청시") {
            val result = issuedCouponStoreServiceMock.issueCoupon(issuedCouponCommand)

            then("쿠폰은 발급되어야 한다.") {
                result shouldBeEqual couponId
            }
        }
    }
})