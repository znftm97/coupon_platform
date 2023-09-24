package coupon_platform.batch.attendance_check

import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class KeyGenerator {
    fun generateKeys(checkDay: Long): Array<String> {
        val today = LocalDateTime.now()
        var beforeDays = today.minusDays(checkDay - 1)
        val keys = mutableListOf<String>()

        repeat(checkDay.toInt() - 1) {
            keys.add(generateKey(CommonConstants.ATTENDANCE_CHECK_PREFIX, beforeDays))
            beforeDays = beforeDays.plusDays(1)
        }
        keys.add(generateKey(CommonConstants.ATTENDANCE_CHECK_PREFIX, today))

        return keys.toTypedArray()
    }

    fun generateKey(
        prefix: String,
        date: LocalDateTime,
    ) = prefix + date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
}