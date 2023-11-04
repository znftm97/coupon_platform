package coupon_platform.batch.issued_coupon.global_caching

import coupon_platform.batch.issued_coupon.global_caching.SaveIssuedCouponToRedisJob.Companion.JOB_NAME_OF_ISSUE_COUPON
import coupon_platform.batch.listener.JobListener
import coupon_platform.batch.listener.StepListener
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@ConditionalOnProperty(value = ["spring.batch.job.names"], havingValue = JOB_NAME_OF_ISSUE_COUPON)
class SaveIssuedCouponToRedisJob(
    private val issueCouponToRedisTasklet: IssueCouponToRedisTasklet,
    private val jobListener: JobListener,
    private val stepListener: StepListener,
) {
    companion object {
        const val JOB_NAME_OF_ISSUE_COUPON = "issue.coupon.to.redis.job"
        const val STEP_NAME_OF_ISSUE_COUPON = "issue.coupon.to.redis.step"
    }

    @Bean
    fun issueCouponJob(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
    ): Job {
        return JobBuilder(JOB_NAME_OF_ISSUE_COUPON, jobRepository)
            .start(issueCouponStep(jobRepository, platformTransactionManager))
            .listener(jobListener)
            .build()
    }

    @Bean
    fun issueCouponStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
    ): Step = StepBuilder(STEP_NAME_OF_ISSUE_COUPON, jobRepository)
        .tasklet(issueCouponToRedisTasklet, platformTransactionManager)
        .listener(stepListener)
        .build()
}