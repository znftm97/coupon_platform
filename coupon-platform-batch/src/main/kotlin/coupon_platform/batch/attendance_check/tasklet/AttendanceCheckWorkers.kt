package coupon_platform.batch.attendance_check.tasklet

import coupon_platform.application.issued_coupon.IssuedCouponFacade
import coupon_platform.domain.issued_coupon.dto.IssueCouponCommand
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Component
class Processor {

    @Value("\${coupon-id}")
    private var couponId: Long? = 0

    @Value("\${expiration-period-str}")
    private var expirationPeriodStr: String? = ""

    fun process(bitSet: BitSet): List<IssueCouponCommand> {
        val checkedAttendanceAccountIds = mutableListOf<Long>()
        for (i in 0 until bitSet.size()) {
            if (bitSet.get(i)) {
                checkedAttendanceAccountIds.add(i.toLong())
            }
        }

        couponId ?: return emptyList()
        if(expirationPeriodStr.isNullOrEmpty()) return emptyList()

        return checkedAttendanceAccountIds.map { accountId ->
            IssueCouponCommand(
                couponId!!,
                accountId,
                convertStringToZonedDateTime(expirationPeriodStr!!)
            )
        }
    }

    private fun convertStringToZonedDateTime(str: String?) : ZonedDateTime {
        return ZonedDateTime.of(LocalDate.parse(str).atStartOfDay(), ZoneId.of("UTC"))
    }
}

@Component
class Writer(
    private val issuedCouponFacade: IssuedCouponFacade,
) {
    fun write(issuedCouponCommands: List<IssueCouponCommand>) {
        issuedCouponCommands.forEach { issuedCouponCommand ->
            runBlocking {
                issuedCouponFacade.issueCoupon(issuedCouponCommand)
            }
        }
    }
}