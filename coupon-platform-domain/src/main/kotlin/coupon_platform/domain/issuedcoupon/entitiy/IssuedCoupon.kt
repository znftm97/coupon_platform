package coupon_platform.domain.issuedcoupon.entitiy

import coupon_platform.domain.common.BaseEntityForIssuedCoupon
import jakarta.persistence.*
import java.security.InvalidParameterException
import java.time.ZonedDateTime

@Entity
@Table(
    name = "issued_coupon",
    indexes = [Index(name = "idx_created_at_date", columnList = "createdAtDate")]
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

    ) : BaseEntityForIssuedCoupon() {

    companion object {
        fun of(
            couponId: Long,
            accountId: Long,
            expirationPeriod: ZonedDateTime,
            externalId: String,
        ): IssuedCoupon {
            if(expirationPeriod.isBefore(ZonedDateTime.now())) {
                throw InvalidParameterException("만료 기간은 쿠폰을 발급하는 시점의 시간 이후여야 한다.")
            }

            return IssuedCoupon(couponId, accountId, false, expirationPeriod, externalId)
        }

        fun of(
            id: Long,
            couponId: Long,
            accountId: Long,
            isUsed: Boolean,
            expirationPeriod: ZonedDateTime,
            externalId: String,
            createdAt: ZonedDateTime,
            updatedAt: ZonedDateTime,
        ): IssuedCoupon {
            val issuedCoupon = IssuedCoupon(couponId, accountId, isUsed, expirationPeriod, externalId, id)
            issuedCoupon.createdAt = createdAt
            issuedCoupon.updatedAt = updatedAt

            return issuedCoupon
        }
    }
}