package coupon_platform.domain.issued_coupon.dto

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import java.time.ZonedDateTime

data class IssueCouponCommand(
    val couponId: Long,
    val expirationPeriod: ZonedDateTime,
) {
    fun toEntity(externalId: String): IssuedCoupon {
        return IssuedCoupon.of(couponId, expirationPeriod, externalId)
    }
}