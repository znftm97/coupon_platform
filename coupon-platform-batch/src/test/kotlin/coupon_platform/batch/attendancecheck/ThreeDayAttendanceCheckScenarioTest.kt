package coupon_platform.batch.attendancecheck

import coupon_platform.batch.attendancecheck.tasklet.ThreeDayAttendanceCheckTasklet
import coupon_platform.batch.attendancecheck.tasklet.ThreeDaysBitopOperatorTasklet
import coupon_platform.batch.support.BatchTestConfig
import coupon_platform.batch.support.TestRedisHandler
import coupon_platform.interfaces.support.BaseScenarioTest
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.TestPropertySource
import java.util.*

@SpringBootTest(
    classes = [
        ThreeDaysBitopOperatorTasklet::class,
        ThreeDaysBitopOperatorTasklet::class,
        ThreeDayAttendanceCheckTasklet::class,
        BatchTestConfig::class,
    ],
    properties = ["spring.batch.job.names=three.attendance.check.job"]
)
@TestPropertySource(properties = ["coupon-id=2", "expiration-period-str=2030-10-01"])
class ThreeDayAttendanceCheckScenarioTest(
    private val redisTemplate: RedisTemplate<String, BitSet>,
    private val jobLauncherTestUtils: JobLauncherTestUtils,
) : BaseScenarioTest, FeatureSpec({

    feature("3일 연속 출석체크한 사용자들에게 쿠폰 발급") {
        val redisHandler = TestRedisHandler(redisTemplate)

        scenario("성공 - accountId가 1인 사용자가 3일 연속 출석체크한 경우") {
            redisHandler.set(listOf(1), 3L)

            val jobExecution: JobExecution = jobLauncherTestUtils.launchJob()
            jobExecution.status shouldBe BatchStatus.COMPLETED
            jobExecution.exitStatus shouldBe ExitStatus.COMPLETED

            redisHandler.delete(3L)
        }

        scenario("성공 - accountId가 1, 5, 10인 사용자들이 3일 연속 출석체크한 경우") {
            redisHandler.set(listOf(1, 5, 10), 3L)

            val jobExecution: JobExecution = jobLauncherTestUtils.launchJob()
            jobExecution.status shouldBeEqual BatchStatus.COMPLETED
            jobExecution.exitStatus shouldBeEqual ExitStatus.COMPLETED

            redisHandler.delete(3L)
        }

        scenario("성공 - 3일 연속 출석체크한 사용자가 아무도 없는 경우") {
            redisHandler.setNobodyAttendanceCheck(3L)

            val jobExecution: JobExecution = jobLauncherTestUtils.launchJob()
            jobExecution.status shouldBeEqual BatchStatus.COMPLETED
            jobExecution.exitStatus shouldBeEqual ExitStatus.COMPLETED

            redisHandler.delete(3L)
        }
    }
})