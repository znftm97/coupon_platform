package coupon_platform.infrastructure.cache.globalredis.handler

import coupon_platform.domain.issuedcoupon.entitiy.IssuedCoupon
import coupon_platform.infrastructure.cache.globalredis.util.IssuedCouponForRedis
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDate

interface RedisHandlerOfIssuedCoupon {
    fun getIssuedCouponInDates(dates: List<LocalDate>): List<IssuedCoupon>
    fun setIssuedCouponsWithTTL(issuedCoupons: List<IssuedCoupon>, timeoutDay: Long)
}

@Component
class LettuceHandlerOfIssuedCoupon(
    private val redisTemplate: RedisTemplate<String, List<IssuedCouponForRedis>>,
) : RedisHandlerOfIssuedCoupon {

    override fun getIssuedCouponInDates(dates: List<LocalDate>): List<IssuedCoupon> {
        return dates.flatMap { key ->
            redisTemplate.opsForValue().get(key.toString()) ?: emptyList()
        }.map(IssuedCouponForRedis::toIssuedCoupon)
    }

    override fun setIssuedCouponsWithTTL(issuedCoupons: List<IssuedCoupon>, timeoutDay: Long) {
        issuedCoupons.asSequence()
            .map { IssuedCouponForRedis.of(it) }
            .groupBy { it.createdAt.toLocalDate().toString() }
            .forEach { (key, value) ->
                redisTemplate.opsForValue().set(key, value, Duration.ofDays(timeoutDay))
            }
    }
}