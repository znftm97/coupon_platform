package coupon_platform.domain.coupon.service

import coupon_platform.domain.coupon.dto.CouponInfo
import coupon_platform.domain.coupon.repository.CouponReader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CouponReadService(
    private val couponReader: CouponReader,
) {

    fun findCouponByName(name: String): CouponInfo {
        val coupon = couponReader.findCouponByName(name)
        return CouponInfo.toInfo(coupon)
    }

    fun findCouponById(id: Long): CouponInfo {
        val coupon = couponReader.findCouponById(id)
        return CouponInfo.toInfo(coupon)
    }
}