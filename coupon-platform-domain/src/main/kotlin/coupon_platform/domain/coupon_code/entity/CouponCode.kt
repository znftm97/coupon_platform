package coupon_platform.domain.coupon_code.entity

import coupon_platform.domain.common.BaseEntity
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
class CouponCode private constructor(


    val couponId: Long,

    var code: String,

    var expirationPeriod: ZonedDateTime,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_code_id")
    val id: Long = 0,

    val externalId: Long = 0,

    ) : BaseEntity() {
}