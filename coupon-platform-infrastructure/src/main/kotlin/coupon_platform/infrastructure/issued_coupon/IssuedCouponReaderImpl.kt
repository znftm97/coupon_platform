package coupon_platform.infrastructure.issued_coupon

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import coupon_platform.domain.issued_coupon.repository.IssuedCouponReader
import coupon_platform.infrastructure.cache.global_redis.handler.RedisHandlerOfIssuedCoupon
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class IssuedCouponReaderImpl(
    private val issuedCouponJpaRepository: IssuedCouponJpaRepository,
    private val redisHandler: RedisHandlerOfIssuedCoupon,
): IssuedCouponReader {

    override fun findIssuedCouponsInDates(dates: List<LocalDate>): List<IssuedCoupon> {
        return issuedCouponJpaRepository.findIssuedCouponsInDates(dates)
    }

    override fun findIssuedCouponsInDatesFromRedis(dates: List<LocalDate>): List<IssuedCoupon> {
        return redisHandler.getIssuedCouponInDates(dates)
    }
}