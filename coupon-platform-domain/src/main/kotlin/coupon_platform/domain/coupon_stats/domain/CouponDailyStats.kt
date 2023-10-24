package coupon_platform.domain.coupon_stats.domain

import coupon_platform.domain.common.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(
    name = "coupon_daily_stats",
    indexes = [Index(name = "idx_stats_date", columnList = "statsDate")]
)
class CouponDailyStats private constructor(

    /*쿠폰 발급 개수*/
    val issuedCouponCount: Long,

    /*쿠폰 사용 개수*/
    val usedCouponCount: Long,

    /*기간이 만료된 쿠폰 개수*/
    val expiredCouponCount: Long,

    /*쿠폰 사용 비율*/
    val couponUsageRate: Int,

    val statsDate: LocalDate,

    val externalId: String = "",

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_daily_stats_id")
    val id: Long = 0,

    ) : BaseEntity() {

    companion object {
        fun of(
            issuedCouponCount: Long,
            usedCouponCount: Long,
            expiredCouponCount: Long,
            couponUsageRate: Int,
            statsDate: LocalDate,
        ) = CouponDailyStats(
            issuedCouponCount,
            usedCouponCount,
            expiredCouponCount,
            couponUsageRate,
            statsDate
        )
    }

}

