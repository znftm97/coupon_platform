package coupon_platform.batch.issued_coupon

import coupon_platform.domain.issuedcoupon.entitiy.IssuedCoupon
import coupon_platform.domain.issuedcoupon.repository.IssuedCouponReader
import coupon_platform.infrastructure.cache.global_redis.handler.RedisHandlerOfIssuedCoupon
import coupon_platform.infrastructure.cache.global_redis.util.CacheConstants.ISSUED_COUPON_KEY_TTL
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class IssueCouponToRedisTasklet(
    private val issuedCouponReader: IssuedCouponReader,
    private val redisHandler: RedisHandlerOfIssuedCoupon,
): Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val findIssuedCoupons: List<IssuedCoupon> = findIssuedCoupons()
        if (findIssuedCoupons.isEmpty()) {
            return RepeatStatus.FINISHED
        }

        saveIssuedCouponsToRedis(findIssuedCoupons)

        return RepeatStatus.FINISHED
    }

    private fun findIssuedCoupons(): List<IssuedCoupon> {
        val yesterday = LocalDate.now().minusDays(1)
        return issuedCouponReader.findIssuedCouponsInDates(listOf(yesterday))
    }

    private fun saveIssuedCouponsToRedis(findIssuedCoupons: List<IssuedCoupon>) {
        redisHandler.setIssuedCouponsWithTTL(findIssuedCoupons, ISSUED_COUPON_KEY_TTL)
    }
}

