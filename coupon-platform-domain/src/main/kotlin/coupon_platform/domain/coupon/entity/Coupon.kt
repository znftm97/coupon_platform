package coupon_platform.domain.coupon.entity

import coupon_platform.domain.common.BaseEntity
import jakarta.persistence.*

@Entity
class Coupon private constructor(

    val name: String,

    @Enumerated(value = EnumType.STRING)
    val applyType: ApplyType,

    @Enumerated(value = EnumType.STRING)
    val discountType: DiscountType,

    val externalId: String = "", // FIXME 쿠폰코드 기능 구현시 수정 필요

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    val id: Long = 0,

    ) : BaseEntity() {

    companion object {
        fun of(
            name: String,
            applyType: ApplyType,
            discountType: DiscountType,
        ) = Coupon(name, applyType, discountType)
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

