package coupon_platform.domain.issued_coupon.repository

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import org.springframework.stereotype.Component

interface IssuedCouponStore {
    fun issueCoupon(issuedCoupon: IssuedCoupon): IssuedCoupon
}

@Component
class IssuedCouponStoreImpl(
    val issuedCouponJpaRepository: IssuedCouponJpaRepository,
) : IssuedCouponStore {

    override fun issueCoupon(issuedCoupon: IssuedCoupon) = issuedCouponJpaRepository.save(issuedCoupon)

}