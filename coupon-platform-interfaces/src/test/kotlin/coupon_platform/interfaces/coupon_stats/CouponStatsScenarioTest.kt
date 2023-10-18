package coupon_platform.interfaces.coupon_stats

import coupon_platform.interfaces.support.BaseScenarioTest
import coupon_platform.interfaces.support.When
import io.kotest.core.spec.style.FeatureSpec
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.springframework.boot.test.web.server.LocalServerPort

class CouponStatsScenarioTest(
    @LocalServerPort
    port: Int
) : BaseScenarioTest, FeatureSpec() {

    init {
        beforeSpec {
            RestAssured.port = port
        }

        feature("계산된 쿠폰 통계 데이터가 이미 존재할 때 조회") {

            scenario("2023-10-15 날짜의 통계 데이터를 조회") {
                given()
                    .contentType("application/json")
                    .pathParam("startDate", "2023-10-15")
                    .pathParam("endDate", "2023-10-15")
                .When()
                    .get("/api/v1/coupon-stats/{startDate}/{endDate}")
                .then()
                    .statusCode(200)
                    .body("data.couponSummaryStats.totalIssuedCouponCount", equalTo(1))
                    .body("data.couponSummaryStats.totalUsedCouponCount", equalTo(0))
                    .body("data.couponSummaryStats.totalExpiredCouponCount", equalTo(0))
                    .body("data.couponSummaryStats.totalCouponUsageRate", equalTo(0))
                    .body("data.couponDailyStatsResponses[0].issuedCouponCount", equalTo(1))
                    .body("data.couponDailyStatsResponses[0].usedCouponCount", equalTo(0))
                    .body("data.couponDailyStatsResponses[0].expiredCouponCount", equalTo(0))
                    .body("data.couponDailyStatsResponses[0].couponUsageRate", equalTo(0))
                    .body("data.couponDailyStatsResponses[0].statsDate", equalTo("2023-10-15"))
                    .body("message", nullValue())
                    .body("exceptionCode", nullValue())
            }

            scenario("2023-10-15 ~ 2023-10-17 날짜의 통계 데이터를 조회") {
                given()
                    .contentType("application/json")
                    .pathParam("startDate", "2023-10-15")
                    .pathParam("endDate", "2023-10-17")
                .When()
                    .get("/api/v1/coupon-stats/{startDate}/{endDate}")
                .then()
                    .statusCode(200)
                    .body("data.couponSummaryStats.totalIssuedCouponCount", equalTo(3))
                    .body("data.couponSummaryStats.totalUsedCouponCount", equalTo(1))
                    .body("data.couponSummaryStats.totalExpiredCouponCount", equalTo(0))
                    .body("data.couponSummaryStats.totalCouponUsageRate", equalTo(33))
                    .body("data.couponDailyStatsResponses[0].statsDate", equalTo("2023-10-15"))
                    .body("data.couponDailyStatsResponses[0].issuedCouponCount", equalTo(1))
                    .body("data.couponDailyStatsResponses[0].usedCouponCount", equalTo(0))
                    .body("data.couponDailyStatsResponses[0].expiredCouponCount", equalTo(0))
                    .body("data.couponDailyStatsResponses[0].couponUsageRate", equalTo(0))
                    .body("data.couponDailyStatsResponses[1].statsDate", equalTo("2023-10-16"))
                    .body("data.couponDailyStatsResponses[1].issuedCouponCount", equalTo(1))
                    .body("data.couponDailyStatsResponses[1].usedCouponCount", equalTo(0))
                    .body("data.couponDailyStatsResponses[1].expiredCouponCount", equalTo(0))
                    .body("data.couponDailyStatsResponses[1].couponUsageRate", equalTo(0))
                    .body("data.couponDailyStatsResponses[2].statsDate", equalTo("2023-10-17"))
                    .body("data.couponDailyStatsResponses[2].issuedCouponCount", equalTo(1))
                    .body("data.couponDailyStatsResponses[2].usedCouponCount", equalTo(1))
                    .body("data.couponDailyStatsResponses[2].expiredCouponCount", equalTo(0))
                    .body("data.couponDailyStatsResponses[2].couponUsageRate", equalTo(100))
                    .body("message", nullValue())
                    .body("exceptionCode", nullValue())
            }
        }

        feature("계산된 쿠폰 통계 데이터가 존재하지 않을 때 조회") {
            scenario("2030-01-01 날짜의 통계 데이터를 조회") {
                given()
                    .contentType("application/json")
                    .pathParam("startDate", "2030-01-01")
                    .pathParam("endDate", "2030-01-01")
                .When()
                    .get("/api/v1/coupon-stats/{startDate}/{endDate}")
                .then()
                    .statusCode(200)
                    .body("data.couponSummaryStats.totalIssuedCouponCount", equalTo(1))
                    .body("data.couponSummaryStats.totalUsedCouponCount", equalTo(0))
                    .body("data.couponSummaryStats.totalExpiredCouponCount", equalTo(0))
                    .body("data.couponSummaryStats.totalCouponUsageRate", equalTo(0))
                    .body("data.couponDailyStatsResponses[0].issuedCouponCount", equalTo(1))
                    .body("data.couponDailyStatsResponses[0].usedCouponCount", equalTo(0))
                    .body("data.couponDailyStatsResponses[0].expiredCouponCount", equalTo(0))
                    .body("data.couponDailyStatsResponses[0].couponUsageRate", equalTo(0))
                    .body("data.couponDailyStatsResponses[0].statsDate", equalTo("2030-01-01"))
                    .body("message", nullValue())
                    .body("exceptionCode", nullValue())
            }

            scenario("2030-01-02 ~ 2020-01-04 날짜의 통계 데이터를 조회") {
                given()
                    .contentType("application/json")
                    .pathParam("startDate", "2030-01-02")
                    .pathParam("endDate", "2030-01-04")
                .When()
                    .get("/api/v1/coupon-stats/{startDate}/{endDate}")
                .then()
                    .statusCode(200)
                    .body("data.couponSummaryStats.totalIssuedCouponCount", equalTo(3))
                    .body("data.couponSummaryStats.totalUsedCouponCount", equalTo(1))
                    .body("data.couponSummaryStats.totalExpiredCouponCount", equalTo(0))
                    .body("data.couponSummaryStats.totalCouponUsageRate", equalTo(33))
                    .body("data.couponDailyStatsResponses[0].statsDate", equalTo("2030-01-02"))
                    .body("data.couponDailyStatsResponses[0].issuedCouponCount", equalTo(1))
                    .body("data.couponDailyStatsResponses[0].usedCouponCount", equalTo(0))
                    .body("data.couponDailyStatsResponses[0].expiredCouponCount", equalTo(0))
                    .body("data.couponDailyStatsResponses[0].couponUsageRate", equalTo(0))
                    .body("data.couponDailyStatsResponses[1].statsDate", equalTo("2030-01-03"))
                    .body("data.couponDailyStatsResponses[1].issuedCouponCount", equalTo(1))
                    .body("data.couponDailyStatsResponses[1].usedCouponCount", equalTo(0))
                    .body("data.couponDailyStatsResponses[1].expiredCouponCount", equalTo(0))
                    .body("data.couponDailyStatsResponses[1].couponUsageRate", equalTo(0))
                    .body("data.couponDailyStatsResponses[2].statsDate", equalTo("2030-01-04"))
                    .body("data.couponDailyStatsResponses[2].issuedCouponCount", equalTo(1))
                    .body("data.couponDailyStatsResponses[2].usedCouponCount", equalTo(1))
                    .body("data.couponDailyStatsResponses[2].expiredCouponCount", equalTo(0))
                    .body("data.couponDailyStatsResponses[2].couponUsageRate", equalTo(100))
                    .body("message", nullValue())
                    .body("exceptionCode", nullValue())
            }
        }
    }
}