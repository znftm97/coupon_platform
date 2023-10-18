package coupon_platform.domain.issued_coupon.dto

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
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
                issuedCoupon.isUsed,
                issuedCoupon.expirationPeriod,
                issuedCoupon.createdAt.toLocalDate(),
            )
        }
    }
}