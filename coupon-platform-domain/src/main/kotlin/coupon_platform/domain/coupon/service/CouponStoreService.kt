package coupon_platform.domain.coupon.service

import coupon_platform.domain.coupon.CouponCreateCommand
import coupon_platform.domain.coupon.repository.CouponStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponStoreService(
    val couponStore: CouponStore,
) {

    fun createCoupon(couponCreateCommand: CouponCreateCommand): String {
        val coupon = couponCreateCommand.toEntity()
        return couponStore.saveCoupon(coupon).name
    }

}