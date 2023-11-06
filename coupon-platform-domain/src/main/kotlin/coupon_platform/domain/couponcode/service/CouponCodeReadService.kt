package coupon_platform.domain.couponcode.service

import coupon_platform.domain.couponcode.dto.CouponCodeInfo
import coupon_platform.domain.couponcode.repository.CouponCodeReader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CouponCodeReadService(
    private val couponCodeReader: CouponCodeReader,
) {
    fun findCouponCodeByCode(code: String): CouponCodeInfo {
        val couponCode = couponCodeReader.findCouponCodeByCode(code)
        return CouponCodeInfo.toInfo(couponCode)
    }
}