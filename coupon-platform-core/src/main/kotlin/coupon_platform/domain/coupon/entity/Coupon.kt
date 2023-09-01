package coupon_platform.domain.coupon.entity

import coupon_platform.domain.common.BaseEntity
import jakarta.persistence.*

@Entity
class Coupon private constructor (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    val id: Long? = null,

    val externalId: Long? = null, // FIXME 쿠폰코드 기능 구현시 수정 필요

    var name: String,

    @Enumerated(value = EnumType.STRING)
    var applyType: ApplyType,

    @Enumerated(value = EnumType.STRING)
    var discountType: DiscountType

) : BaseEntity() {

    companion object {
        fun of(
            name: String,
            applyType: ApplyType,
            discountType: DiscountType,
        ): Coupon = Coupon(name = name, applyType = applyType, discountType = discountType)
    }

    enum class DiscountType(description: String) {
        PRICE("정액"),
        RATE("정률")
    }

    enum class ApplyType(description: String) {
        PRODUCT("상품"),
        ACCOUNT("사용자")
    }
}

