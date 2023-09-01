package coupon_platform.domain.issued_coupon.dto

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import java.time.ZonedDateTime

data class IssueCouponCommand(
    val couponId: Long,
    val expirationPeriod: ZonedDateTime,
)

fun IssueCouponCommand.toEntity() = IssuedCoupon.of(couponId, expirationPeriod)