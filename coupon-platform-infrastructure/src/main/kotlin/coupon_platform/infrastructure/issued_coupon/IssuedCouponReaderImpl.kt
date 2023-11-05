package coupon_platform.infrastructure.issued_coupon

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import coupon_platform.domain.issued_coupon.repository.IssuedCouponReader
import coupon_platform.infrastructure.cache.global_redis.handler.RedisHandlerOfIssuedCoupon
import coupon_platform.infrastructure.cache.local_caffeine.CaffeineHandlerForIssuedCoupon
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class IssuedCouponReaderImpl(
    private val issuedCouponJpaRepository: IssuedCouponJpaRepository,
    private val redisHandler: RedisHandlerOfIssuedCoupon,
    private val caffeineHandler: CaffeineHandlerForIssuedCoupon,
): IssuedCouponReader {

    override fun findIssuedCouponsInDates(dates: List<LocalDate>): List<IssuedCoupon> {
        return issuedCouponJpaRepository.findIssuedCouponsInDates(dates)
    }

    override fun findIssuedCouponsInDatesFromRedis(dates: List<LocalDate>): List<IssuedCoupon> {
        return redisHandler.getIssuedCouponInDates(dates)
    }

    override fun findIssuedCouponInDatesFromLocal(dates: List<LocalDate>): List<IssuedCoupon> {
        return caffeineHandler.getIssuedCouponInDates(dates)
    }
}