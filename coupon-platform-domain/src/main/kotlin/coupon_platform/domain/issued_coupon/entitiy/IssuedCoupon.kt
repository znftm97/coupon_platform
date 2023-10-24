package coupon_platform.domain.issued_coupon.entitiy

import coupon_platform.domain.common.BaseEntity
import jakarta.persistence.*
import java.security.InvalidParameterException
import java.time.ZonedDateTime

@Entity
@Table(
    name = "issued_coupon",
    indexes = [Index(name = "idx_create_at", columnList = "createdAt")]
)
class IssuedCoupon private constructor(

    val couponId: Long,

    val accountId: Long,

    val isUsed: Boolean,

    val expirationPeriod: ZonedDateTime,

    val externalId: String = "",

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issued_coupon_id")
    val id: Long = 0,

    ) : BaseEntity() {

    init {
        require(expirationPeriod > ZonedDateTime.now()) {
            throw InvalidParameterException("만료 기간은 쿠폰을 발급하는 시점의 시간 이후여야 한다.")
        }
        require(!isUsed) {
            throw InvalidParameterException("쿠폰이 발급될 때, 사용 여부는 항상 false 여야 한다.")
        }
    }

    companion object {
        fun of(
            couponId: Long,
            accountId: Long,
            expirationPeriod: ZonedDateTime,
            externalId: String,
        ) = IssuedCoupon(couponId, accountId, false, expirationPeriod, externalId)
    }
}