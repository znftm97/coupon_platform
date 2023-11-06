package coupon_platform.infrastructure.couponstats

import coupon_platform.domain.couponstats.domain.CouponDailyStats
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface CouponDailyStatsJpaRepository : JpaRepository<CouponDailyStats, Long> {

    @Query("SELECT cds FROM CouponDailyStats cds WHERE cds.statsDate BETWEEN :startDate AND :endDate")
    fun findCouponDailyStatsByDateRange(
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate,
    ): List<CouponDailyStats>
}