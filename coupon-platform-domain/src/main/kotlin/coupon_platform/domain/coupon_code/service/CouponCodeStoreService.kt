package coupon_platform.domain.coupon_code.service

import coupon_platform.domain.common.ExternalIdGenerator
import coupon_platform.domain.coupon_code.RandomNumberGenerator
import coupon_platform.domain.coupon_code.dto.CouponCodeCreateCommand
import coupon_platform.domain.coupon_code.dto.CouponCodeInfo
import coupon_platform.domain.coupon_code.entity.CouponCode
import coupon_platform.domain.coupon_code.repository.CouponCodeStore
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponCodeStoreService(
    val couponCodeStore: CouponCodeStore,
    val externalIdGenerator: ExternalIdGenerator,
    @Qualifier("UUIDGenerator")
    val randomNumberGenerator: RandomNumberGenerator,
) {

    companion object {
        const val COUPON_CODE_LENGTH = 19
    }

    fun createCouponCode(couponCodeCreateCommand: CouponCodeCreateCommand): CouponCodeInfo {
        val couponCode = CouponCode.of(
            couponCodeCreateCommand.couponId,
            generateCouponCode(),
            couponCodeCreateCommand.expirationPeriod,
            externalIdGenerator.generate(),
        )
        val storedCouponCode = couponCodeStore.createCouponCode(couponCode)

        return CouponCodeInfo.toCouponCodeInfo(storedCouponCode)
    }

    /**
     * @return formant: xxxx-xxxx-xxxx-xxxx
     */
    private fun generateCouponCode(): String {
        var couponCode = randomNumberGenerator.generate(COUPON_CODE_LENGTH).replace('-', '1')
        for (i in 4 until couponCode.length step 5) {
            couponCode = couponCode.replaceRange(i, i + 1, "-")
        }

        return couponCode
    }
}