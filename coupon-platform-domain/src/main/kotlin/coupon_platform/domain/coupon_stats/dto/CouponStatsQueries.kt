package coupon_platform.domain.coupon_stats.dto

import coupon_platform.domain.coupon_stats.domain.CouponDailyStats
import java.time.LocalDate

data class CouponStatsInfo(
    val couponDailyStatsInfos: List<CouponDailyStatsInfo>,
    val couponSummaryStatsInfo: CouponSummaryStatsInfo,
)

data class CouponDailyStatsInfo(
    /*쿠폰 발급 개수*/
    val issuedCouponCount: Long,

    /*쿠폰 사용 개수*/
    val usedCouponCount: Long,

    /*기간이 만료된 쿠폰 개수*/
    val expiredCouponCount: Long,

    /*쿠폰 사용 비율*/
    val couponUsageRate: Int,

    val statsDate: LocalDate,
) {

    companion object {
        fun toInfos(couponDailyStatsList: List<CouponDailyStats>): List<CouponDailyStatsInfo> {
            return couponDailyStatsList.map { toInfo(it) }
        }

        fun toEntity(couponDailyStatsInfo: CouponDailyStatsInfo): CouponDailyStats {
            return CouponDailyStats.of(
                couponDailyStatsInfo.issuedCouponCount,
                couponDailyStatsInfo.usedCouponCount,
                couponDailyStatsInfo.expiredCouponCount,
                couponDailyStatsInfo.couponUsageRate,
                couponDailyStatsInfo.statsDate,
            )
        }

        private fun toInfo(couponDailyStats: CouponDailyStats): CouponDailyStatsInfo {
            return CouponDailyStatsInfo(
                couponDailyStats.issuedCouponCount,
                couponDailyStats.usedCouponCount,
                couponDailyStats.expiredCouponCount,
                couponDailyStats.couponUsageRate,
                couponDailyStats.statsDate,
            )
        }
    }

}

data class CouponSummaryStatsInfo(
    /*기간 내 쿠폰 발급 총 개수*/
    val totalIssuedCouponCount: Long,

    /*기간 내 쿠폰 총 사용 개수*/
    val totalUsedCouponCount: Long,

    /*기간 내, 기간이 만료된 쿠폰 총 개수*/
    val totalExpiredCouponCount: Long,

    /*기간 내 쿠폰 총 사용 비율*/
    val totalCouponUsageRate: Int,
) {

}