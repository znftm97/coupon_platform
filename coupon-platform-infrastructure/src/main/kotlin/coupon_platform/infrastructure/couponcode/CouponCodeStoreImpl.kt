package coupon_platform.infrastructure.couponcode

import coupon_platform.domain.couponcode.entity.CouponCode
import coupon_platform.domain.couponcode.repository.CouponCodeStore
import org.springframework.stereotype.Repository

@Repository
class CouponCodeStoreImpl(
    private val couponCodeJpaRepository: CouponCodeJpaRepository,
) : CouponCodeStore {

    override fun createCouponCode(couponCode: CouponCode): CouponCode = couponCodeJpaRepository.save(couponCode)
}