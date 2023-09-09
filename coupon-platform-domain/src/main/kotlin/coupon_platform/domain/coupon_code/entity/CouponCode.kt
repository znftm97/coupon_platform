package coupon_platform.domain.coupon_code.entity

import coupon_platform.domain.common.BaseEntity
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
class CouponCode private constructor(

    val couponId: Long,

    val code: String,

    val expirationPeriod: ZonedDateTime,

    val externalId: String = "",

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_code_id")
    val id: Long = 0,

    ) : BaseEntity() {

    companion object {
        fun of(couponId: Long, code: String, expirationPeriod: ZonedDateTime, externalId: String): CouponCode {
            return CouponCode(couponId, code, expirationPeriod, externalId)
        }
    }
}