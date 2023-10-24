package coupon_platform.infrastructure.dummy_data_creator_not_test

import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.sql.DataSource

@SpringBootTest
@ActiveProfiles("test")
class CouponDailyStatsDummyDataCreator(
    private val dataSource: DataSource
): FunSpec({

    val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)

    test("쿠폰 통계 더미 데이터 생성") {
        val numThreads = 4
        val batchSize = 250000
        val executor = Executors.newFixedThreadPool(numThreads)

        for (i in 0 until numThreads) {
            executor.submit {
                val start = i * batchSize
                val end = (i + 1) * batchSize
                val random = Random()

                val sql = "INSERT INTO coupon_daily_stats (stats_date, coupon_usage_rate, expired_coupon_count, issued_coupon_count, used_coupon_count) " +
                        "VALUES (:statsDate, :couponUsageRate, :expiredCouponCount, :issuedCouponCount, :usedCouponCount)"

                val batchParameters = (start until end).map { j ->
                    val issuedCouponCount = random.nextInt(1001)
                    val maxUsedCouponCount = issuedCouponCount.coerceAtMost(1001 - 1)
                    val usedCouponCount = random.nextInt(maxUsedCouponCount + 1)
                    val maxExpiredCouponCount = issuedCouponCount - usedCouponCount
                    val expiredCouponCount = if (maxExpiredCouponCount >= 0) random.nextInt(maxExpiredCouponCount + 1) else 0
                    val couponUsageRate = (usedCouponCount.toDouble() / issuedCouponCount) * 100

                    CouponDailyStatsBatchParameters(
                        statsDate = LocalDate.now().plusDays((start + j - 1).toLong()),
                        couponUsageRate = couponUsageRate.toInt(),
                        expiredCouponCount = expiredCouponCount.toLong(),
                        issuedCouponCount = issuedCouponCount.toLong(),
                        usedCouponCount = usedCouponCount.toLong()
                    )
                }

                namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(batchParameters))
            }
        }

        executor.shutdown()
        executor.awaitTermination(1, TimeUnit.HOURS)
    }
})

data class CouponDailyStatsBatchParameters(
    val statsDate: LocalDate,
    val couponUsageRate: Int,
    val expiredCouponCount: Long,
    val issuedCouponCount: Long,
    val usedCouponCount: Long
)