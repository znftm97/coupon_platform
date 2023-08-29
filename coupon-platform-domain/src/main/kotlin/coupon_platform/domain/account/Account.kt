package coupon_platform.domain.account

import coupon_platform.domain.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType

@Entity
class Account(

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val externalId: Long, // FIXME 쿠폰코드 기능 구현시 수정 필요

) : BaseEntity()