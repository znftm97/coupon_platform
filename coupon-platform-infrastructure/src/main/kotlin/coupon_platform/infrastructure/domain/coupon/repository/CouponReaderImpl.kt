package coupon_platform.infrastructure.domain.coupon.repository

import coupon_platform.domain.coupon.entity.Coupon
import coupon_platform.domain.coupon.repository.CouponReader
import org.springframework.stereotype.Component

@Component
class CouponReaderImpl(
    val couponJpaRepository: CouponJpaRepository,
) : CouponReader {

    override fun readCoupon(name: String): Coupon = couponJpaRepository.findByName(name)

}