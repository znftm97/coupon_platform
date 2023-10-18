package coupon_platform.domain.coupon_code.dto

import java.time.ZonedDateTime

data class CouponCodeCreateCommand(
    val couponId: Long,
    val expirationPeriod: ZonedDateTime,
)
