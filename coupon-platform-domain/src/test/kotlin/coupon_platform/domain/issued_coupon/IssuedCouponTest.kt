package coupon_platform.domain.issued_coupon

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.security.InvalidParameterException
import java.time.ZonedDateTime

class IssuedCouponTest : FunSpec({

    test("쿠폰 발급시 만료 기간이 현재 시간보다 이전이라면, InvalidParameterException 예외를 던진다") {
        shouldThrow<InvalidParameterException> {
            IssuedCoupon.of(1, ZonedDateTime.now().minusDays(1), "1")
        }
    }

    test("발급된 쿠폰의 isUsed는 항상 false여야 한다.") {
        val result = IssuedCoupon.of(1, ZonedDateTime.now().plusDays(1), "1")
        result.isUsed shouldBe false
    }

})