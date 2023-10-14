package coupon_platform.infrastructure.coupon.repository

import coupon_platform.domain.common.exception.coupon.NotFoundCouponException
import coupon_platform.domain.coupon.entity.Coupon
import coupon_platform.domain.coupon.repository.CouponReader
import org.springframework.stereotype.Repository

@Repository
class CouponReaderImpl(
    private val couponJpaRepository: CouponJpaRepository,
) : CouponReader {

    override fun findCouponByName(name: String): Coupon {
        return couponJpaRepository.findByName(name) ?: throw NotFoundCouponException()
    }

    override fun findCouponById(id: Long): Coupon {
        return couponJpaRepository.customFindById(id) ?: throw NotFoundCouponException()
    }

}