package coupon_platform.batch.issued_coupon.local_caching

import coupon_platform.domain.issued_coupon.entitiy.IssuedCoupon
import coupon_platform.domain.issued_coupon.repository.IssuedCouponReader
import coupon_platform.infrastructure.cache.local_caffeine.CaffeineHandlerForIssuedCoupon
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class IssueCouponToLocalTasklet(
    private val issuedCouponReader: IssuedCouponReader,
    private val caffeineHandler: CaffeineHandlerForIssuedCoupon,
): Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val findIssuedCoupons: List<IssuedCoupon> = findIssuedCoupons()
        if (findIssuedCoupons.isEmpty()) {
            return RepeatStatus.FINISHED
        }

        saveIssuedCouponsToLocal(findIssuedCoupons)

        return RepeatStatus.FINISHED
    }

    private fun findIssuedCoupons(): List<IssuedCoupon> {
        val yesterday = LocalDate.now().minusDays(1L)
        return issuedCouponReader.findIssuedCouponsInDates(listOf(yesterday))
    }

    private fun saveIssuedCouponsToLocal(issuedCoupons: List<IssuedCoupon>) {
        issuedCoupons.groupBy {
            it.createdAt.toLocalDate().toString()
        }.forEach { (key, value) ->
            caffeineHandler.set(key, value)
        }
    }
}

