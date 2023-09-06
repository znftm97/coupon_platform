package coupon_platform.domain.coupon.service

import coupon_platform.domain.coupon.CreateCouponCommand
import coupon_platform.domain.coupon.repository.CouponStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponStoreService(
    val couponStore: CouponStore,
) {

    fun createCoupon(createCouponCommand: CreateCouponCommand): String {
        val coupon = createCouponCommand.toEntity()
        return couponStore.saveCoupon(coupon).name
    }

}