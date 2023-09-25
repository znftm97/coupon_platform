package coupon_platform.batch.attendance_check.job

import coupon_platform.batch.attendance_check.job.SevenDayAttendanceCheckJobConfig.Companion.JOB_NAME_OF_SEVEN_DAY
import coupon_platform.batch.attendance_check.tasklet.SevenDayAttendanceCheckTasklet
import coupon_platform.batch.attendance_check.tasklet.SevenDaysBitopOperatorTasklet
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
@ConditionalOnProperty(value = ["spring.batch.job.names"], havingValue = JOB_NAME_OF_SEVEN_DAY)
class SevenDayAttendanceCheckJobConfig(
    private val sevenDayBitopOperatorTasklet: SevenDaysBitopOperatorTasklet,
    private val sevenDayAttendanceCheckTasklet: SevenDayAttendanceCheckTasklet,
    private val jobListener: JobListener,
    private val stepListener: StepListener,
) {

    companion object {
        const val JOB_NAME_OF_SEVEN_DAY = "seven.attendance.check.job"
    }

    @Bean
    fun attendanceCheckJob(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
    ): Job {
        return JobBuilder(JOB_NAME_OF_SEVEN_DAY, jobRepository)
            .start(bitopOperatorStep(jobRepository, platformTransactionManager))
            .next(attendanceCheckStep(jobRepository, platformTransactionManager))
            .listener(jobListener)
            .build()
    }

    @Bean
    fun bitopOperatorStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
    ): Step = StepBuilder("seven.bitop.operator.step", jobRepository)
        .tasklet(sevenDayBitopOperatorTasklet, platformTransactionManager)
        .listener(stepListener)
        .build()

    @Bean
    fun attendanceCheckStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
    ): Step = StepBuilder("seven.attendance.check.step", jobRepository)
        .tasklet(sevenDayAttendanceCheckTasklet, platformTransactionManager)
        .listener(stepListener)
        .build()
}