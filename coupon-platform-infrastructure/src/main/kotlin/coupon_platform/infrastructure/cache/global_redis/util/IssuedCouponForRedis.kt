package coupon_platform.infrastructure.cache.global_redis.util

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer
import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import java.time.ZonedDateTime

data class IssuedCouponForRedis(
    @JsonProperty("couponId")
    val couponId: Long,

    @JsonProperty("accountId")
    val accountId: Long,

    @JsonProperty("externalId")
    val externalId: String,

    @JsonProperty("id")
    val id: Long = 0,

    @JsonProperty("expirationPeriod")
    @JsonSerialize(using = ZonedDateTimeSerializer::class)
    @JsonDeserialize(using = ZonedDateTimeDeserializer::class)
    val expirationPeriod: ZonedDateTime,

    @JsonProperty("createdAt")
    @JsonSerialize(using = ZonedDateTimeSerializer::class)
    @JsonDeserialize(using = ZonedDateTimeDeserializer::class)
    val createdAt: ZonedDateTime,

    @JsonProperty("updatedAt")
    @JsonSerialize(using = ZonedDateTimeSerializer::class)
    @JsonDeserialize(using = ZonedDateTimeDeserializer::class)
    val updatedAt: ZonedDateTime,

    @JsonProperty("used")
    val isUsed: Boolean,
) {
    companion object {
        fun of(issuedCoupon: IssuedCoupon): IssuedCouponForRedis {
            return IssuedCouponForRedis(
                issuedCoupon.couponId,
                issuedCoupon.accountId,
                issuedCoupon.externalId,
                issuedCoupon.id,
                issuedCoupon.expirationPeriod,
                issuedCoupon.createdAt,
                issuedCoupon.updatedAt,
                issuedCoupon.isUsed,
            )
        }

        fun toIssuedCoupon(issuedCouponForRedis: IssuedCouponForRedis): IssuedCoupon {
            return IssuedCoupon.of(
                couponId = issuedCouponForRedis.couponId,
                accountId = issuedCouponForRedis.accountId,
                isUsed = issuedCouponForRedis.isUsed,
                expirationPeriod = issuedCouponForRedis.expirationPeriod,
                externalId = issuedCouponForRedis.externalId,
                createdAt = issuedCouponForRedis.createdAt,
                updatedAt = issuedCouponForRedis.updatedAt,
                id = issuedCouponForRedis.id,
            )
        }
    }
}
