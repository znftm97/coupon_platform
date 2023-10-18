package coupon_platform.interfaces.coupon_stats.dto

import coupon_platform.domain.coupon_stats.dto.CouponDailyStatsInfo
import coupon_platform.domain.coupon_stats.dto.CouponStatsInfo
import coupon_platform.domain.coupon_stats.dto.CouponSummaryStatsInfo
import java.time.LocalDate

data class CouponStatsResponse(
    val couponDailyStatsResponses: List<CouponDailyStatsResponse>,
    val couponSummaryStats: CouponSummaryStatsResponse,
) {
    companion object {
        fun toResponse(info: CouponStatsInfo): CouponStatsResponse {
            val couponDailyStatsResponses: List<CouponDailyStatsResponse> = info.couponDailyStatsInfos
                .map { CouponDailyStatsResponse.toResponse(it) }

            return CouponStatsResponse(
                couponDailyStatsResponses = couponDailyStatsResponses,
                couponSummaryStats = CouponSummaryStatsResponse.toResponse(info.couponSummaryStatsInfo)
            )
        }
    }
}

data class CouponDailyStatsResponse(
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
        fun toResponse(info: CouponDailyStatsInfo): CouponDailyStatsResponse {
            return CouponDailyStatsResponse(
                issuedCouponCount = info.issuedCouponCount,
                usedCouponCount = info.usedCouponCount,
                expiredCouponCount = info.expiredCouponCount,
                couponUsageRate = info.couponUsageRate,
                statsDate = info.statsDate,
            )
        }
    }
}

data class CouponSummaryStatsResponse(
    /*기간 내 쿠폰 발급 총 개수*/
    val totalIssuedCouponCount: Long,

    /*기간 내 쿠폰 총 사용 개수*/
    val totalUsedCouponCount: Long,

    /*기간 내, 기간이 만료된 쿠폰 총 개수*/
    val totalExpiredCouponCount: Long,

    /*기간 내 쿠폰 총 사용 비율*/
    val totalCouponUsageRate: Int,
) {
    companion object {
        fun toResponse(info: CouponSummaryStatsInfo): CouponSummaryStatsResponse {
            return CouponSummaryStatsResponse(
                totalIssuedCouponCount = info.totalIssuedCouponCount,
                totalUsedCouponCount = info.totalUsedCouponCount,
                totalExpiredCouponCount = info.totalExpiredCouponCount,
                totalCouponUsageRate = info.totalCouponUsageRate,
            )
        }
    }
}
