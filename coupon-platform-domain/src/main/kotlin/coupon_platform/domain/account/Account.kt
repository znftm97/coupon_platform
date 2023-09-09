package coupon_platform.domain.account

import coupon_platform.domain.common.BaseEntity
import jakarta.persistence.*

@Entity
class Account private constructor(

    val externalId: String = "",

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    val id: Long = 0,

    ) : BaseEntity()