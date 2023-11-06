package coupon_platform.infrastructure.issuedcoupon

import coupon_platform.domain.issuedcoupon.entitiy.IssuedCoupon
import coupon_platform.domain.issuedcoupon.repository.IssuedCouponReader
import coupon_platform.infrastructure.cache.globalredis.handler.RedisHandlerOfIssuedCoupon
import coupon_platform.infrastructure.cache.localcaffeine.CaffeineHandlerForIssuedCoupon
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class IssuedCouponReaderImpl(
    private val issuedCouponJpaRepository: IssuedCouponJpaRepository,
    private val redisHandler: RedisHandlerOfIssuedCoupon,
    private val caffeineHandler: CaffeineHandlerForIssuedCoupon,
) : IssuedCouponReader {

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