package coupon_platform.domain.couponcode.dto

import java.time.ZonedDateTime

data class CouponCodeCreateCommand(
    val couponId: Long,
    val expirationPeriod: ZonedDateTime,
)
