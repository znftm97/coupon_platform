package coupon_platform.infrastructure.coupon_code

import coupon_platform.domain.coupon_code.entity.CouponCode
import coupon_platform.domain.coupon_code.repository.CouponCodeStore
import org.springframework.stereotype.Component

@Component
class CouponCodeStoreImpl(
    val couponCodeJpaRepository: CouponCodeJpaRepository,
) : CouponCodeStore {

    override fun createCouponCode(couponCode: CouponCode) = couponCodeJpaRepository.save(couponCode)
}