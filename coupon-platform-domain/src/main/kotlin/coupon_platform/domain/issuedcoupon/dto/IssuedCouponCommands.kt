package coupon_platform.domain.issuedcoupon.dto

import coupon_platform.domain.issuedcoupon.entitiy.IssuedCoupon
import java.time.ZonedDateTime

data class IssueCouponCommand(
    val couponId: Long,
    val accountId: Long,
    val expirationPeriod: ZonedDateTime,
) {
    fun toEntity(externalId: String): IssuedCoupon {
        return IssuedCoupon.of(couponId, accountId, expirationPeriod, externalId)
    }
}