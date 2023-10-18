package coupon_platform.infrastructure.issued_coupon.repository

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import coupon_platform.domain.issued_coupon.repository.IssuedCouponStore
import org.springframework.stereotype.Repository

@Repository
class IssuedCouponStoreImpl(
    private val issuedCouponJpaRepository: IssuedCouponJpaRepository,
) : IssuedCouponStore {

    override fun issueCoupon(issuedCoupon: IssuedCoupon): IssuedCoupon = issuedCouponJpaRepository.save(issuedCoupon)

}