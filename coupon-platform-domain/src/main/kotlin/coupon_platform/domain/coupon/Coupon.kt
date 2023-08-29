package coupon_platform.domain.coupon

import coupon_platform.domain.common.BaseEntity
import jakarta.persistence.*

@Entity
class Coupon (

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val externalId: Long, // FIXME 쿠폰코드 기능 구현시 수정 필요

    var name: String,

    @Enumerated(value = EnumType.STRING)
    var applyType: ApplyType,

    @Enumerated(value = EnumType.STRING)
    var discountType: DiscountType

) : BaseEntity() {
}

