package coupon_platform.domain.issuedcoupon.service

import coupon_platform.domain.issuedcoupon.dto.IssuedCouponInfo
import coupon_platform.domain.issuedcoupon.entitiy.IssuedCoupon
import coupon_platform.domain.issuedcoupon.repository.IssuedCouponReader
import coupon_platform.domain.issuedcoupon.repository.IssuedCouponStore
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class IssuedCouponReadServiceV2(
    private val issuedCouponReader: IssuedCouponReader,
    private val issuedCouponStore: IssuedCouponStore,
) {

    fun findIssuedCouponsInDates(reqDates: List<LocalDate>): List<IssuedCouponInfo> {
        val issuedCouponsFromLocal: List<IssuedCoupon> = issuedCouponReader.findIssuedCouponInDatesFromLocal(reqDates)
        val emptyStatsDates: List<LocalDate> = getEmptyStatsDates(reqDates.toSet(), issuedCouponsFromLocal)

        if(emptyStatsDates.isEmpty()) {
            return issuedCouponsFromLocal.map { IssuedCouponInfo.toInfo(it) }
        }

        val issuedCoupons: List<IssuedCoupon> = issuedCouponReader.findIssuedCouponsInDates(emptyStatsDates)
        issuedCouponStore.saveIssuedCouponToLocal(issuedCoupons)
        issuedCoupons.toMutableList().addAll(issuedCouponsFromLocal)

        return issuedCoupons.map { IssuedCouponInfo.toInfo(it) }
    }

    private fun getEmptyStatsDates(
        reqDates: Set<LocalDate>,
        issuedCouponsFromRedis: List<IssuedCoupon>,
    ): List<LocalDate> = issuedCouponsFromRedis.map { it.createdAt.toLocalDate() }
        .filterNot { reqDates.contains(it) }

}