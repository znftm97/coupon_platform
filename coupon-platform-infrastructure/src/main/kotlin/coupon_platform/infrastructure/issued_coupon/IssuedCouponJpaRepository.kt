package coupon_platform.infrastructure.issued_coupon

import coupon_platform.domain.issuedcoupon.entitiy.IssuedCoupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface IssuedCouponJpaRepository : JpaRepository<IssuedCoupon, Long> {

    @Query(
        "SELECT * FROM issued_coupon WHERE created_at_date IN (:dates)",
        nativeQuery = true
    )
    fun findIssuedCouponsInDates(
        @Param("dates") dates: List<LocalDate>
    ): List<IssuedCoupon>
}