package coupon_platform.domain.coupon.service

import coupon_platform.domain.coupon.dto.CouponInfo
import coupon_platform.domain.coupon.repository.CouponReader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CouponReadService(
    val couponReader: CouponReader,
) {

    fun readCoupon(name: String): CouponInfo {
        val coupon = couponReader.readCoupon(name)
        return CouponInfo.toCouponInfo(coupon)
    }
}