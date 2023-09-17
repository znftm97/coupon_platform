package coupon_platform.interfaces.coupon_code

import coupon_platform.domain.common.exception.coupon.NotFoundCouponException
import coupon_platform.domain.coupon_code.service.CouponCodeStoreService.Companion.COUPON_CODE_LENGTH
import coupon_platform.interfaces.coupon_code.dto.CouponCodeCreateRequest
import coupon_platform.interfaces.support.BaseScenarioTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.security.InvalidParameterException
import java.time.LocalDateTime

class CouponCodeScenarioTest(
    private val couponCodeController: CouponCodeController,
) : BaseScenarioTest, FeatureSpec({

    feature("쿠폰 코드 생성 기능") {
        scenario("실패 - 사용자(관리자)가 존재하지 않는 쿠폰의 코드 생성을 요청한다.") {
            val couponCodeCreateRequest = CouponCodeCreateRequest(99L, LocalDateTime.now().plusDays(7))
            shouldThrow<NotFoundCouponException> {
                couponCodeController.createCouponCode(couponCodeCreateRequest)
            }
        }

        scenario("실패 - 사용자(관리자)가 쿠폰 코드 유효기간을 현재 시간보다 이전 시간으로 요청한다.") {
            val couponCodeCreateRequest = CouponCodeCreateRequest(1L, LocalDateTime.now().minusDays(7))
            shouldThrow<InvalidParameterException> {
                couponCodeController.createCouponCode(couponCodeCreateRequest)
            }
        }

        scenario("성공 - 사용자(관리자)가 존재하는 쿠폰의 코드 생성을 요청한다.") {
            val couponCodeCreateRequest = CouponCodeCreateRequest(1L, LocalDateTime.now().plusDays(7))
            val result = couponCodeController.createCouponCode(couponCodeCreateRequest)
            result.data?.let { it.length shouldBeEqual COUPON_CODE_LENGTH }
        }
    }
})