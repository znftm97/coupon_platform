package coupon_platform.domain.coupon_code.entity

import coupon_platform.domain.common.BaseEntity
import jakarta.persistence.*
import java.security.InvalidParameterException
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

    init {
        require(expirationPeriod > ZonedDateTime.now()) {
            throw InvalidParameterException("만료 기간은 쿠폰 코드를 생성하는 시점의 시간 이후여야 한다.")
        }
    }

    companion object {
        fun of(couponId: Long, code: String, expirationPeriod: ZonedDateTime, externalId: String): CouponCode {
            return CouponCode(couponId, code, expirationPeriod, externalId)
        }
    }
}