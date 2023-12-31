package coupon_platform.domain.couponcode.service

import coupon_platform.domain.common.CommonConstants.EXTERNAL_ID_LENGTH
import coupon_platform.domain.common.RandomNumberGenerator
import coupon_platform.domain.couponcode.dto.CouponCodeCreateCommand
import coupon_platform.domain.couponcode.dto.CouponCodeInfo
import coupon_platform.domain.couponcode.entity.CouponCode
import coupon_platform.domain.couponcode.repository.CouponCodeStore
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponCodeStoreService(
    private val couponCodeStore: CouponCodeStore,
    @Qualifier("TSIDGenerator")
    private val tsidGenerator: RandomNumberGenerator,
    @Qualifier("UUIDGenerator")
    private val uuidGenerator: RandomNumberGenerator,
) {

    companion object {
        const val COUPON_CODE_LENGTH = 19
    }

    fun createCouponCode(couponCodeCreateCommand: CouponCodeCreateCommand): CouponCodeInfo {
        val couponCode = CouponCode.of(
            couponId = couponCodeCreateCommand.couponId,
            code = generateCouponCode(),
            expirationPeriod = couponCodeCreateCommand.expirationPeriod,
            externalId = tsidGenerator.generate(EXTERNAL_ID_LENGTH),
        )
        val storedCouponCode = couponCodeStore.createCouponCode(couponCode)

        return CouponCodeInfo.toInfo(storedCouponCode)
    }

    /**
     * @return formant: xxxx-xxxx-xxxx-xxxx
     */
    private fun generateCouponCode(): String {
        var couponCode = uuidGenerator.generate(COUPON_CODE_LENGTH).replace('-', '1')
        for (i in 4 until couponCode.length step 5) {
            couponCode = couponCode.replaceRange(i, i + 1, "-")
        }

        return couponCode
    }
}