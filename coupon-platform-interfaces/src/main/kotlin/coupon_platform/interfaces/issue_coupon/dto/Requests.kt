package coupon_platform.interfaces.issue_coupon.dto

import com.fasterxml.jackson.annotation.JsonFormat
import coupon_platform.domain.issued_coupon.dto.IssueCouponCommand
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

data class CouponIssueRequest(
    val couponId: Long,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val expirationPeriod: LocalDateTime,
) {
    fun toCommand() = IssueCouponCommand(couponId, ZonedDateTime.of(expirationPeriod, ZoneId.of("UTC")))
}