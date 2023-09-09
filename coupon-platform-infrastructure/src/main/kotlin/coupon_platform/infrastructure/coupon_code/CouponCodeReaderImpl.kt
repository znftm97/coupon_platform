package coupon_platform.infrastructure.coupon_code

import coupon_platform.domain.common.exception.coupon_code.NotFoundCouponCodeException
import coupon_platform.domain.coupon_code.entity.CouponCode
import coupon_platform.domain.coupon_code.repository.CouponCodeReader
import org.springframework.stereotype.Component

@Component
class CouponCodeReaderImpl(
    val couponCodeJpaRepository: CouponCodeJpaRepository,
) : CouponCodeReader {

    override fun findCouponCodeByCode(code: String): CouponCode {
        return couponCodeJpaRepository.findCouponCodeByCode(code) ?: throw NotFoundCouponCodeException()
    }
}