package coupon_platform.batch.listener

import mu.KLogging
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.annotation.AfterStep
import org.springframework.batch.core.annotation.BeforeStep
import org.springframework.stereotype.Component

@Component
class StepListener {

    private val log = KLogging().logger

    @BeforeStep
    fun beforeStep(stepExecution: StepExecution) {
        log.info { "Step - {${stepExecution.stepName}} 실행" }
    }

    @AfterStep
    fun afterStep(stepExecution: StepExecution): ExitStatus {
        val stepName = stepExecution.stepName
        val exitStatus = stepExecution.exitStatus

        log.info("Step - {} 종료", stepName)
        log.info("Step - {} 종료 상태 : {}", stepName, exitStatus)
        log.info("Step - {} 상태 : {}", stepName, stepExecution.status)

        return exitStatus
    }
}