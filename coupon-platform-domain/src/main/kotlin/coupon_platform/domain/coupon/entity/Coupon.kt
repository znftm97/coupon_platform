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

    val externalId: String = "",

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
            externalId: String,
        ) = Coupon(name, applyType, discountType, externalId)
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

