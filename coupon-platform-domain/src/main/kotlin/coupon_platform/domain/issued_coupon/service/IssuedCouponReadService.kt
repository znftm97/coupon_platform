package coupon_platform.domain.issued_coupon.service

import coupon_platform.domain.issued_coupon.dto.IssuedCouponInfo
import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import coupon_platform.domain.issued_coupon.repository.IssuedCouponReader
import coupon_platform.domain.issued_coupon.repository.IssuedCouponStore
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class IssuedCouponReadService(
    private val issuedCouponReader: IssuedCouponReader,
    private val issuedCouponStore: IssuedCouponStore,
) {

    fun findIssuedCouponsInDates(reqDates: List<LocalDate>): List<IssuedCouponInfo> {
        val issuedCouponsFromRedis: List<IssuedCoupon> = issuedCouponReader.findIssuedCouponsInDatesFromRedis(reqDates)
        val emptyStatsDates: List<LocalDate> = getEmptyStatsDates(reqDates.toSet(), issuedCouponsFromRedis)

        if(emptyStatsDates.isEmpty()) {
            return issuedCouponsFromRedis.map { IssuedCouponInfo.toInfo(it) }
        }

        val issuedCoupons: List<IssuedCoupon> = issuedCouponReader.findIssuedCouponsInDates(emptyStatsDates)
        issuedCouponStore.saveIssuedCouponFromRedis(issuedCoupons)
        issuedCoupons.toMutableList().addAll(issuedCouponsFromRedis)

        return issuedCoupons.map { IssuedCouponInfo.toInfo(it) }
    }

    private fun getEmptyStatsDates(
        reqDates: Set<LocalDate>,
        issuedCouponsFromRedis: List<IssuedCoupon>,
    ): List<LocalDate> = issuedCouponsFromRedis.map { it.createdAt.toLocalDate() }
        .filterNot { reqDates.contains(it) }

}