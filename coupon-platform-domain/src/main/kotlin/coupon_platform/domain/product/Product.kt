package coupon_platform.domain.product

import coupon_platform.domain.common.BaseEntity
import jakarta.persistence.*

@Entity
class Product(

    val externalId: String = "", // FIXME 쿠폰코드 기능 구현시 수정 필요

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    val id: Long = 0,

    ) : BaseEntity()