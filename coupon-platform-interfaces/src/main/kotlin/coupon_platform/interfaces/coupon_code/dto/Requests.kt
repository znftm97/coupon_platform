package coupon_platform.interfaces.coupon_code.dto

import com.fasterxml.jackson.annotation.JsonFormat
import coupon_platform.domain.coupon_code.dto.CouponCodeCreateCommand
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

data class CouponCodeCreateRequest(
    val couponId: Long,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val expirationPeriod: LocalDateTime,
)

fun CouponCodeCreateRequest.toCommand() =
    CouponCodeCreateCommand(couponId, ZonedDateTime.of(expirationPeriod, ZoneId.of("UTC")))