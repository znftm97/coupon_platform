package coupon_platform.interfaces.couponcode

import coupon_platform.interfaces.couponcode.dto.CouponCodeCreateRequest
import coupon_platform.interfaces.support.BaseScenarioTest
import coupon_platform.interfaces.support.When
import io.kotest.core.spec.style.FeatureSpec
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.*
import org.springframework.boot.test.web.server.LocalServerPort
import java.time.LocalDateTime

class CouponCodeScenarioTest(
    @LocalServerPort
    port: Int,
) : BaseScenarioTest, FeatureSpec() {

    init {
        beforeSpec {
            RestAssured.port = port
        }

        feature("쿠폰 코드 생성 기능") {

            scenario("실패 - 사용자(관리자)가 존재하지 않는 쿠폰의 코드 생성을 요청한다.") {
                given()
                    .contentType("application/json")
                    .body(CouponCodeCreateRequest(99L, LocalDateTime.now()))
                .When()
                    .post("/api/v1/coupon-code")
                .then()
                    .statusCode(200)
                    .body("data", nullValue())
                    .body("message", equalTo("존재하지 않는 쿠폰입니다."))
                    .body("exceptionCode", equalTo("NOT_FOUND_COUPON_EXCEPTION"))
            }

            scenario("실패 - 사용자(관리자)가 쿠폰 코드 유효기간을 현재 시간보다 이전 시간으로 요청한다.") {
                given()
                    .contentType("application/json")
                    .body(CouponCodeCreateRequest(99L, LocalDateTime.now().minusDays(7)))
                .When()
                    .post("/api/v1/coupon-code")
                .then()
                    .statusCode(200)
                    .body("data", nullValue())
                    .body("message", equalTo("만료 기간은 쿠폰 코드를 생성하는 시점의 시간 이후여야 합니다."))
                    .body("exceptionCode", equalTo("INVALID_EXPIRATION_PERIOD_EXCEPTION"))
            }

            scenario("성공 - 사용자(관리자)가 존재하는 쿠폰의 코드 생성을 요청한다.") {
                given()
                    .contentType("application/json")
                    .body(CouponCodeCreateRequest(1, LocalDateTime.now().plusDays(7)))
                .When()
                    .post("/api/v1/coupon-code")
                .then()
                    .statusCode(200)
                    // xxxx-xxxx-xxxx-xxxx 형태인지 && 19자리인지 검사
                    .body("data", matchesPattern("\\w{4}-\\w{4}-\\w{4}-\\w{4}"))
                    .body("message", nullValue())
                    .body("exceptionCode", nullValue())
            }
        }
    }
}