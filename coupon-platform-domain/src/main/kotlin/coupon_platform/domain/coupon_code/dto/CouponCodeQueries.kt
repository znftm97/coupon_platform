package coupon_platform.domain.coupon_code.dto

import coupon_platform.domain.coupon_code.entity.CouponCode
import java.time.ZonedDateTime

data class CouponCodeInfo(
    val externalId: String,
    val couponId: Long,
    val couponCode: String,
    val expirationPeriod: ZonedDateTime
) {
    companion object {
        fun toInfo(couponCode: CouponCode): CouponCodeInfo {
            return CouponCodeInfo(
                externalId = couponCode.externalId,
                couponId = couponCode.couponId,
                couponCode = couponCode.code,
                expirationPeriod = couponCode.expirationPeriod,
            )
        }
    }
}