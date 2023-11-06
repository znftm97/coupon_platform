package coupon_platform.interfaces.issue_coupon.dto

import com.fasterxml.jackson.annotation.JsonFormat
import coupon_platform.domain.issuedcoupon.dto.IssueCouponCommand
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

data class CouponIssueRequest(
    val couponId: Long,
    val accountId: Long,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val expirationPeriod: LocalDateTime,
) {
    fun toCommand() = IssueCouponCommand(couponId, accountId, ZonedDateTime.of(expirationPeriod, ZoneId.of("UTC")))
}