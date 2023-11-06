package coupon_platform.domain.issuedcoupon.dto

import coupon_platform.domain.issuedcoupon.entitiy.IssuedCoupon
import java.time.LocalDate
import java.time.ZonedDateTime

data class IssuedCouponInfo(
    val isUsed: Boolean,
    val expirationPeriod: ZonedDateTime,
    val createdAt: LocalDate,
) {
    companion object {
        fun toInfo(issuedCoupon: IssuedCoupon): IssuedCouponInfo {
            return IssuedCouponInfo(
                isUsed = issuedCoupon.isUsed,
                expirationPeriod = issuedCoupon.expirationPeriod,
                createdAt = issuedCoupon.createdAt.toLocalDate(),
            )
        }
    }
}