package coupon_platform.batch.listener

import mu.KLogging
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.annotation.AfterJob
import org.springframework.batch.core.annotation.BeforeJob
import org.springframework.stereotype.Component

@Component
class JobListener {

    private val log = KLogging().logger

    @BeforeJob
    fun beforeJob(jobExecution: JobExecution) {
        log.info { "Job - {$jobExecution.jobInstance.jobName} 실행" }
    }

    @AfterJob
    fun afterJob(jobExecution: JobExecution) {
        val jobName = jobExecution.jobInstance.jobName
        val executionTime = jobExecution.endTime?.compareTo(jobExecution.startTime)

        log.info("Job - {} 종료", jobName)
        log.info("Job - {} 수행 시간 : {}", jobName, executionTime)
        log.info("Job - {} 상태 : {}", jobName, jobExecution.status)
    }
}