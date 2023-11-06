package coupon_platform.domain.issuedcoupon.service

import coupon_platform.domain.common.CommonConstants.EXTERNAL_ID_LENGTH
import coupon_platform.domain.common.RandomNumberGenerator
import coupon_platform.domain.issuedcoupon.dto.IssueCouponCommand
import coupon_platform.domain.issuedcoupon.entitiy.IssuedCoupon
import coupon_platform.domain.issuedcoupon.repository.IssuedCouponReader
import coupon_platform.domain.issuedcoupon.repository.IssuedCouponStore
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class IssuedCouponStoreService(
    private val issuedCouponStore: IssuedCouponStore,
    private val issuedCouponReader: IssuedCouponReader,
    @Qualifier("UUIDGenerator")
    private val randomNumberGenerator: RandomNumberGenerator,
) {

    fun issueCoupon(issueCouponCommand: IssueCouponCommand): Long {
        val issuedCoupon = issueCouponCommand.toEntity(randomNumberGenerator.generate(EXTERNAL_ID_LENGTH))
        return issuedCouponStore.saveIssuedCoupon(issuedCoupon).couponId
    }

    fun cachingIssuedCouponToLocal() {
        val yesterday = LocalDate.now().minusDays(1)
        val findIssuedCoupons: List<IssuedCoupon> = issuedCouponReader.findIssuedCouponsInDates(listOf(yesterday))
        issuedCouponStore.saveIssuedCouponToLocal(findIssuedCoupons)
    }

}