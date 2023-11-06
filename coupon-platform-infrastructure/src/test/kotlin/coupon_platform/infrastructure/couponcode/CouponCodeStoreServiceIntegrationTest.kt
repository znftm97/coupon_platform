package coupon_platform.infrastructure.couponcode

import coupon_platform.domain.common.CommonConstants.EXTERNAL_ID_LENGTH
import coupon_platform.domain.couponcode.dto.CouponCodeCreateCommand
import coupon_platform.domain.couponcode.service.CouponCodeStoreService
import coupon_platform.domain.couponcode.service.CouponCodeStoreService.Companion.COUPON_CODE_LENGTH
import coupon_platform.infrastructure.support.BaseIntegrationTest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.time.ZonedDateTime

class CouponCodeStoreServiceIntegrationTest(
    private val couponCodeStoreService: CouponCodeStoreService,
) : BaseIntegrationTest, BehaviorSpec({

    given("쿠폰 코드 생성 객체(couponCodeCreateCommand)가 주어지고") {
        val couponCodeCreateCommand = CouponCodeCreateCommand(
            1L,
            ZonedDateTime.now().plusDays(7),
        )
        `when`("couponCodeStoreService의 createCouponCode() 함수를 호출했을 때") {
            val result = couponCodeStoreService.createCouponCode(couponCodeCreateCommand)

            then("쿠폰 코드를 생성할 수 있다.") {
                result.couponCode.length shouldBeEqual COUPON_CODE_LENGTH
                result.couponId shouldBeEqual couponCodeCreateCommand.couponId
                result.expirationPeriod shouldBeEqual couponCodeCreateCommand.expirationPeriod
                result.externalId.length shouldBeEqual EXTERNAL_ID_LENGTH
            }
        }
    }
})