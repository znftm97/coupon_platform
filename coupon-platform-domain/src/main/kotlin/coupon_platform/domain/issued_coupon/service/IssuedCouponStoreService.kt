package coupon_platform.domain.issued_coupon.service

import coupon_platform.domain.common.CommonConstants.EXTERNAL_ID_LENGTH
import coupon_platform.domain.common.RandomNumberGenerator
import coupon_platform.domain.issued_coupon.dto.IssueCouponCommand
import coupon_platform.domain.issued_coupon.repository.IssuedCouponStore
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class IssuedCouponStoreService(
    private val issuedCouponStore: IssuedCouponStore,
    @Qualifier("UUIDGenerator")
    private val randomNumberGenerator: RandomNumberGenerator,
) {

    fun issueCoupon(issueCouponCommand: IssueCouponCommand): Long {
        val issuedCoupon = issueCouponCommand.toEntity(randomNumberGenerator.generate(EXTERNAL_ID_LENGTH))
        return issuedCouponStore.saveIssuedCoupon(issuedCoupon).couponId
    }

}