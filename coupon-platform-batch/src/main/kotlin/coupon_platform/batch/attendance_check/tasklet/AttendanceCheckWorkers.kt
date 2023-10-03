package coupon_platform.batch.attendance_check.tasklet

import coupon_platform.domain.common.CommonConstants.EXTERNAL_ID_LENGTH
import coupon_platform.domain.common.SuspendableRandomNumberGenerator
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import javax.sql.DataSource

@Component
class Processor(
    private val randomNumberGenerator: SuspendableRandomNumberGenerator,
) {

    @Value("\${coupon-id}")
    private var couponId: Long? = 0

    @Value("\${expiration-period-str}")
    private var expirationPeriodStr: String? = ""

    fun process(bitSet: BitSet): List<BatchParameters> {
        val checkedAttendanceAccountIds = (0 until bitSet.size())
            .asSequence()
            .filter { bitSet.get(it) }
            .map { it.toLong() }
            .toList()

        if (couponId == null || expirationPeriodStr.isNullOrEmpty()) {
            return emptyList()
        }

        return runBlocking {
            checkedAttendanceAccountIds.map { accountId ->
                BatchParameters(
                    randomNumberGenerator.generate(EXTERNAL_ID_LENGTH),
                    accountId,
                    couponId!!,
                    convertStringToZonedDateTime(expirationPeriodStr),
                )
            }
        }
    }

    private fun convertStringToZonedDateTime(str: String?) =
        ZonedDateTime.of(LocalDate.parse(str).atStartOfDay(), ZoneId.of("UTC"))

}

@Component
class Writer(
    private val dataSource: DataSource,
) {

    private val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)

    fun write(parameters: List<BatchParameters>) {
        val sql = "INSERT INTO issued_coupon (external_id, account_id, coupon_id, expiration_period, is_used) " +
                "VALUES (:externalId, :accountId, :couponId, :expirationPeriod, :isUsed)"

        namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(parameters)
        )
    }
}

data class BatchParameters(
    val externalId: String,
    val accountId: Long,
    val couponId: Long,
    val expirationPeriod: ZonedDateTime,
    val isUsed: Boolean = false,
)