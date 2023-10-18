package coupon_platform.domain.issued_coupon.service

import coupon_platform.domain.issued_coupon.dto.IssuedCouponInfo
import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import coupon_platform.domain.issued_coupon.repository.IssuedCouponReader
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class IssuedCouponReadService(
    private val issuedCouponReader: IssuedCouponReader,
) {

    fun findIssuedCouponsInDates(dates: List<LocalDate>): List<IssuedCouponInfo> {
        val convertedDates: List<ZonedDateTime> = dates.map {
            it.atStartOfDay(ZoneId.of("UTC"))
        }

        val issuedCoupons: List<IssuedCoupon> = issuedCouponReader.findIssuedCouponsInDates(convertedDates)
        return issuedCoupons.map { IssuedCouponInfo.toInfo(it) }
    }

}