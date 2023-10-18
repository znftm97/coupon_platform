package coupon_platform.infrastructure.coupon_code

import coupon_platform.domain.coupon_code.entity.CouponCode
import coupon_platform.domain.coupon_code.repository.CouponCodeStore
import org.springframework.stereotype.Repository

@Repository
class CouponCodeStoreImpl(
    private val couponCodeJpaRepository: CouponCodeJpaRepository,
) : CouponCodeStore {

    override fun createCouponCode(couponCode: CouponCode): CouponCode = couponCodeJpaRepository.save(couponCode)
}