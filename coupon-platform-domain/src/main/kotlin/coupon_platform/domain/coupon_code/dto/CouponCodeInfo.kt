package coupon_platform.domain.coupon_code.dto

import coupon_platform.domain.coupon_code.entity.CouponCode
import java.time.ZonedDateTime

data class CouponCodeInfo(
    val id: Long? = null,
    val externalId: Long? = null,
    val couponId: Long,
    val couponCode: String,
    val expirationPeriod: ZonedDateTime
) {
    companion object {
        fun toCouponCodeInfo(couponCode: CouponCode): CouponCodeInfo {
            return CouponCodeInfo(
                couponCode.id,
                couponCode.externalId,
                couponCode.couponId,
                couponCode.code,
                couponCode.expirationPeriod,
            )
        }
    }
}