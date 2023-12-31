package coupon_platform.domain.coupon.service

import coupon_platform.domain.common.CommonConstants.EXTERNAL_ID_LENGTH
import coupon_platform.domain.common.RandomNumberGenerator
import coupon_platform.domain.coupon.CouponCreateCommand
import coupon_platform.domain.coupon.repository.CouponStore
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponStoreService(
    private val couponStore: CouponStore,
    @Qualifier("UUIDGenerator")
    private val randomNumberGenerator: RandomNumberGenerator,
) {

    fun createCoupon(couponCreateCommand: CouponCreateCommand): String {
        val coupon = couponCreateCommand.toEntity(randomNumberGenerator.generate(EXTERNAL_ID_LENGTH))
        return couponStore.saveCoupon(coupon).name
    }

}