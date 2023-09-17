package coupon_platform.domain.coupon.service

import coupon_platform.domain.common.CommonConstants.EXTERNAL_ID_LENGTH
import coupon_platform.domain.common.SuspendableRandomNumberGenerator
import coupon_platform.domain.coupon.CouponCreateCommand
import coupon_platform.domain.coupon.repository.CouponStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponStoreService(
    val couponStore: CouponStore,
    val randomNumberGenerator: SuspendableRandomNumberGenerator,
) {

    suspend fun createCoupon(couponCreateCommand: CouponCreateCommand): String {
        val coupon = couponCreateCommand.toEntity(randomNumberGenerator.generate(EXTERNAL_ID_LENGTH))
        return couponStore.saveCoupon(coupon).name
    }

}