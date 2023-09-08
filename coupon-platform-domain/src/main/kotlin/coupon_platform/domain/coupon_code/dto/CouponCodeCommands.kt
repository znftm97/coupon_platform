package coupon_platform.domain.coupon_code.dto

import coupon_platform.domain.coupon_code.entity.CouponCode
import java.time.ZonedDateTime

data class CouponCodeCreateCommand(
    val couponId: Long,
    val expirationPeriod: ZonedDateTime,
) {
    fun toEntity(code: String, externalId: String) = CouponCode.of(couponId, code, expirationPeriod, externalId)
}