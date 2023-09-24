package coupon_platform.batch.attendance_check

import coupon_platform.batch.attendance_check.CommonConstants.ATTENDANCE_CHECK_PREFIX
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class KeyGenerator {
    fun generateKeys(checkDay: Long): Array<String> {
        return (0 until checkDay).map {
            val dateTime = LocalDateTime.now().minusDays(checkDay - it - 1)
            generateKey(ATTENDANCE_CHECK_PREFIX, dateTime)
        }.toTypedArray()
    }

    fun generateKey(
        prefix: String,
        date: LocalDateTime,
    ) = prefix + date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
}