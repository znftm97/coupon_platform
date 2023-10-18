package coupon_platform.infrastructure.coupon_stats

import coupon_platform.domain.coupon_stats.domain.CouponDailyStats
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface CouponStatsJpaRepository: JpaRepository<CouponDailyStats, Long> {

    @Query("SELECT cds FROM CouponDailyStats cds WHERE cds.statsDate BETWEEN :startDate AND :endDate")
    fun findCouponStatsByDateRange(
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): List<CouponDailyStats>

}