package coupon_platform.domain.issued_coupon

import coupon_platform.domain.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import java.security.InvalidParameterException
import java.time.ZonedDateTime

@Entity
class IssuedCoupon (

    @GeneratedValue(strategy = GenerationType.IDENTITY)
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