package coupon_platform.infrastructure.coupon.repository

import coupon_platform.domain.coupon.entity.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponJpaRepository : JpaRepository<Coupon, Long> {
    fun findByName(name: String): Coupon
}