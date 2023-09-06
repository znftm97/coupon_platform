package coupon_platform.domain.account

import coupon_platform.domain.common.BaseEntity
import jakarta.persistence.*

@Entity
class Account(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    val id: Long,

    val externalId: Long, // FIXME 쿠폰코드 기능 구현시 수정 필요

) : BaseEntity()