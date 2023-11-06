package coupon_platform.infrastructure.couponcode

import coupon_platform.domain.common.exception.coupon_code.NotFoundCouponCodeException
import coupon_platform.domain.couponcode.entity.CouponCode
import coupon_platform.domain.couponcode.repository.CouponCodeReader
import org.springframework.stereotype.Repository

@Repository
class CouponCodeReaderImpl(
    private val couponCodeJpaRepository: CouponCodeJpaRepository,
) : CouponCodeReader {

    override fun findCouponCodeByCode(code: String): CouponCode {
        return couponCodeJpaRepository.findCouponCodeByCode(code) ?: throw NotFoundCouponCodeException()
    }
}