package coupon_platform.domain.issued_coupon.service

import coupon_platform.domain.issued_coupon.dto.IssueCouponCommand
import coupon_platform.domain.issued_coupon.dto.toEntity
import coupon_platform.domain.issued_coupon.repository.IssuedCouponStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class IssuedCouponStoreService(
    val issuedCouponStore: IssuedCouponStore,
) {

    fun issueCoupon(issueCouponCommand: IssueCouponCommand): Long {
        val issuedCoupon = issueCouponCommand.toEntity()
        return issuedCouponStore.issueCoupon(issuedCoupon).couponId
    }

}