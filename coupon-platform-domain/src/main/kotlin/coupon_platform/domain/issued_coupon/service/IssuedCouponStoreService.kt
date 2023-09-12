package coupon_platform.domain.issued_coupon.service

import coupon_platform.domain.common.CommonConstants.Companion.EXTERNAL_ID_LENGTH
import coupon_platform.domain.coupon_code.SuspendableRandomNumberGenerator
import coupon_platform.domain.issued_coupon.dto.IssueCouponCommand
import coupon_platform.domain.issued_coupon.repository.IssuedCouponStore
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class IssuedCouponStoreService(
    val issuedCouponStore: IssuedCouponStore,
    @Qualifier("TSIDGenerator")
    val randomNumberGenerator: SuspendableRandomNumberGenerator,
) {

    suspend fun issueCoupon(issueCouponCommand: IssueCouponCommand): Long {
        val issuedCoupon = issueCouponCommand.toEntity(randomNumberGenerator.generate(EXTERNAL_ID_LENGTH))
        return issuedCouponStore.issueCoupon(issuedCoupon).couponId
    }

}