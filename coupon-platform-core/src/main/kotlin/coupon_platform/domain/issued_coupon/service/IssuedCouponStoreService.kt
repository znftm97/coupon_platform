package coupon_platform.domain.issued_coupon.service

import coupon_platform.domain.issued_coupon.dto.IssueCouponCommand
import coupon_platform.domain.issued_coupon.dto.toEntity
import coupon_platform.domain.issued_coupon.repository.IssuedCouponStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface IssuedCouponStoreService {
    fun issueCoupon(issueCouponCommand: IssueCouponCommand): Long
}

@Service
@Transactional
class IssuedCouponStoreServiceImpl(
    val issuedCouponStore: IssuedCouponStore,
) : IssuedCouponStoreService {

    override fun issueCoupon(issueCouponCommand: IssueCouponCommand): Long {
        val issuedCoupon = issueCouponCommand.toEntity()
        return issuedCouponStore.issueCoupon(issuedCoupon).couponId
    }

}