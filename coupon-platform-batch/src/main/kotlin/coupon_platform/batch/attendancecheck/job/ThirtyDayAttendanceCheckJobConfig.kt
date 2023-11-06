package coupon_platform.batch.attendancecheck.job

import coupon_platform.batch.attendancecheck.job.ThirtyDayAttendanceCheckJobConfig.Companion.JOB_NAME_OF_THIRTY_DAY
import coupon_platform.batch.attendancecheck.tasklet.ThirtyDayAttendanceCheckTasklet
import coupon_platform.batch.attendancecheck.tasklet.ThirtyDaysBitopOperatorTasklet
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
@ConditionalOnProperty(value = ["spring.batch.job.names"], havingValue = JOB_NAME_OF_THIRTY_DAY)
class ThirtyDayAttendanceCheckJobConfig(
    private val thirtyDayBitopOperatorTasklet: ThirtyDaysBitopOperatorTasklet,
    private val thirtyDayAttendanceCheckTasklet: ThirtyDayAttendanceCheckTasklet,
    private val jobListener: JobListener,
    private val stepListener: StepListener,
) {

    companion object {
        const val JOB_NAME_OF_THIRTY_DAY = "thirty.attendance.check.job"
    }

    @Bean
    fun attendanceCheckJob(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
    ): Job {
        return JobBuilder(JOB_NAME_OF_THIRTY_DAY, jobRepository)
            .start(bitopOperatorStep(jobRepository, platformTransactionManager))
            .next(attendanceCheckStep(jobRepository, platformTransactionManager))
            .listener(jobListener)
            .build()
    }

    @Bean
    fun bitopOperatorStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
    ): Step = StepBuilder("thirty.bitop.operator.step", jobRepository)
        .tasklet(thirtyDayBitopOperatorTasklet, platformTransactionManager)
        .listener(stepListener)
        .build()

    @Bean
    fun attendanceCheckStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
    ): Step = StepBuilder("thirty.attendance.check.step", jobRepository)
        .tasklet(thirtyDayAttendanceCheckTasklet, platformTransactionManager)
        .listener(stepListener)
        .build()

}