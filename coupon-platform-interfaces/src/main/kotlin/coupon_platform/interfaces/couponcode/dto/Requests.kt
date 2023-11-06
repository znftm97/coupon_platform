package coupon_platform.interfaces.couponcode.dto

import com.fasterxml.jackson.annotation.JsonFormat
import coupon_platform.domain.couponcode.dto.CouponCodeCreateCommand
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

data class CouponCodeCreateRequest(
    val couponId: Long,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val expirationPeriod: LocalDateTime,
) {
    fun toCommand() = CouponCodeCreateCommand(couponId, ZonedDateTime.of(expirationPeriod, ZoneId.of("UTC")))
}
