package coupon_platform.infrastructure.couponcode

import coupon_platform.domain.couponcode.entity.CouponCode
import org.springframework.data.jpa.repository.JpaRepository

interface CouponCodeJpaRepository : JpaRepository<CouponCode, Long> {
    fun findCouponCodeByCode(code: String): CouponCode?
}