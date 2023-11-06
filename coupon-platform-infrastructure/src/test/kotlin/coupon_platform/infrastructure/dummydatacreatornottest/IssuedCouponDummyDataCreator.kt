package coupon_platform.infrastructure.dummydatacreatornottest

import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils
import org.springframework.test.context.ActiveProfiles
import java.time.ZonedDateTime
import javax.sql.DataSource

@SpringBootTest
@ActiveProfiles("test")
class IssuedCouponDummyDataCreator(
    private val dataSource: DataSource,
) : FunSpec({

    val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)

    test("발급된 쿠폰 더미 데이터 생성") {
        val rows = 1000000 // 생성할 데이터 개수

        val parameters = (1..rows).map { i ->
            val used = kotlin.random.Random.nextBoolean()
            val daysDifference = kotlin.random.Random.nextLong(0, 7)
            val expirationPeriod = ZonedDateTime.now().plusDays(daysDifference)

            BatchParameters(
                used = used,
                expirationPeriod = expirationPeriod,
            )
        }

        val sql = "INSERT INTO issued_coupon (account_id, coupon_id, expiration_period, is_used) " +
            "VALUES (1, 1, :expirationPeriod, :used)"

        namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(parameters))
    }
})

data class BatchParameters(
    val used: Boolean,
    val expirationPeriod: ZonedDateTime,
)