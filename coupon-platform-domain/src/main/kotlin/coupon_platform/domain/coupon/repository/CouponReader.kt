package coupon_platform.domain.coupon.repository

import coupon_platform.domain.coupon.entity.Coupon
import org.springframework.stereotype.Component

interface CouponReader {
    fun readCoupon(name: String): Coupon
}

@Component
class CouponReaderImpl(
    val couponJpaRepository: CouponJpaRepository,
) : CouponReader {

    override fun readCoupon(name: String): Coupon {
        return couponJpaRepository.findByName(name)
    }

}

