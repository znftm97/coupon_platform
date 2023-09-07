package coupon_platform.infrastructure.issued_coupon.repository

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import org.springframework.data.jpa.repository.JpaRepository

interface IssuedCouponJpaRepository : JpaRepository<IssuedCoupon, Long>