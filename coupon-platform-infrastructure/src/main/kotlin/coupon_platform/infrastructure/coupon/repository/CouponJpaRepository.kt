package coupon_platform.infrastructure.coupon.repository

import coupon_platform.domain.coupon.entity.Coupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CouponJpaRepository : JpaRepository<Coupon, Long> {
    fun findByName(name: String): Coupon?

    @Query("select c from Coupon c where c.id = :id")
    fun customFindById(@Param("id") id: Long): Coupon?
}