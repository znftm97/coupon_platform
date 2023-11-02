package coupon_platform.domain.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.ZonedDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntityForIssuedCoupon {

    @CreationTimestamp
    @Column(updatable = false)
    lateinit var createdAt: ZonedDateTime

    @CreationTimestamp
    @Column(updatable = false)
    lateinit var createdAtDate: LocalDate

    @UpdateTimestamp
    var updatedAt: ZonedDateTime = ZonedDateTime.now()
}