package coupon_platform.infrastructure.issued_coupon.repository

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.ZonedDateTime

interface IssuedCouponJpaRepository : JpaRepository<IssuedCoupon, Long> {

    @Query(
        "SELECT * FROM issued_coupon WHERE Date(created_at) IN (:dates)",
        nativeQuery = true
    )
    fun findIssuedCouponsInDates(
        @Param("dates") dates: List<ZonedDateTime>
    ): List<IssuedCoupon>
}