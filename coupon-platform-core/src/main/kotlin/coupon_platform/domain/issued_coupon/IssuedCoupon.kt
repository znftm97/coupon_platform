package coupon_platform.domain.issued_coupon

import coupon_platform.domain.common.BaseEntity
import jakarta.persistence.*
import java.security.InvalidParameterException
import java.time.ZonedDateTime

@Entity
class IssuedCoupon (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issued_coupon_id")
    val id: Long,

    val externalId: Long, // FIXME 쿠폰코드 기능 구현시 수정 필요

    val couponId: Long,

    var isUsed: Boolean,

    var expirationPeriod: ZonedDateTime

) : BaseEntity() {
    init {
        require(expirationPeriod > ZonedDateTime.now()) {
            throw InvalidParameterException("만료 기간은 쿠폰을 발급하는 시점의 시간 이후여야 한다.")
        }
        require(!isUsed) {
            throw InvalidParameterException("쿠폰이 발급될 때, 사용 여부는 항상 false 여야 한다.")
        }
    }
}