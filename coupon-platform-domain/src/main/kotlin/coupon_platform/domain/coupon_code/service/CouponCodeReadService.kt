package coupon_platform.domain.coupon_code.service

import coupon_platform.domain.coupon_code.dto.CouponCodeInfo
import coupon_platform.domain.coupon_code.repository.CouponCodeReader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CouponCodeReadService(
    val couponCodeReader: CouponCodeReader,
) {
    fun findCouponCodeByCode(code: String): CouponCodeInfo {
        val couponCode = couponCodeReader.findCouponCodeByCode(code)
        return CouponCodeInfo.toCouponCodeInfo(couponCode)
    }
}