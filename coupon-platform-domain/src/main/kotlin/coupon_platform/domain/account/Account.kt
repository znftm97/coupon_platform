package coupon_platform.domain.account

import coupon_platform.domain.common.BaseEntity
import jakarta.persistence.*

@Entity
class Account private constructor (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    val id: Long = 0,

    val externalId: Long = 0, // FIXME 쿠폰코드 기능 구현시 수정 필요

) : BaseEntity()