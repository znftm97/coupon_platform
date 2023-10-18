package coupon_platform.infrastructure.issued_coupon.repository

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import coupon_platform.domain.issued_coupon.repository.IssuedCouponReader
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.ZonedDateTime

@Repository
class IssuedCouponReaderImpl(
    private val issuedCouponJpaRepository: IssuedCouponJpaRepository,
): IssuedCouponReader {

    override fun findIssuedCouponsInDates(dates: List<ZonedDateTime>): List<IssuedCoupon> {
        return issuedCouponJpaRepository.findIssuedCouponsInDates(dates)
    }
}