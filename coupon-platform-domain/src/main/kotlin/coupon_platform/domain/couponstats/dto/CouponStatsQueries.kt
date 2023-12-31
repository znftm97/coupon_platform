package coupon_platform.domain.couponstats.dto

import coupon_platform.domain.couponstats.domain.CouponDailyStats
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
                issuedCouponCount = couponDailyStatsInfo.issuedCouponCount,
                usedCouponCount = couponDailyStatsInfo.usedCouponCount,
                expiredCouponCount = couponDailyStatsInfo.expiredCouponCount,
                couponUsageRate = couponDailyStatsInfo.couponUsageRate,
                statsDate = couponDailyStatsInfo.statsDate,
            )
        }

        private fun toInfo(couponDailyStats: CouponDailyStats): CouponDailyStatsInfo {
            return CouponDailyStatsInfo(
                issuedCouponCount = couponDailyStats.issuedCouponCount,
                usedCouponCount = couponDailyStats.usedCouponCount,
                expiredCouponCount = couponDailyStats.expiredCouponCount,
                couponUsageRate = couponDailyStats.couponUsageRate,
                statsDate = couponDailyStats.statsDate,
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
)