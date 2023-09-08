package coupon_platform.domain.issued_coupon.service

import coupon_platform.domain.common.ExternalIdGenerator
import coupon_platform.domain.issued_coupon.dto.IssueCouponCommand
import coupon_platform.domain.issued_coupon.repository.IssuedCouponStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class IssuedCouponStoreService(
    val issuedCouponStore: IssuedCouponStore,
    val externalIdGenerator: ExternalIdGenerator,
) {

    fun issueCoupon(issueCouponCommand: IssueCouponCommand): Long {
        val issuedCoupon = issueCouponCommand.toEntity(externalIdGenerator.generate())
        return issuedCouponStore.issueCoupon(issuedCoupon).couponId
    }

}