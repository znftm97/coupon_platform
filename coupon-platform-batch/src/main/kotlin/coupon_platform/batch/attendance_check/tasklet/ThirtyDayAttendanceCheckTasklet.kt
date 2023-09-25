package coupon_platform.batch.attendance_check.tasklet

import coupon_platform.batch.attendance_check.CommonConstants
import coupon_platform.batch.attendance_check.KeyGenerator
import coupon_platform.batch.attendance_check.RedisHandler
import coupon_platform.domain.issued_coupon.dto.IssueCouponCommand
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class ThirtyDayAttendanceCheckTasklet(
    private val keyGenerator: KeyGenerator,
    private val redisHandler: RedisHandler,
    private val processor: Processor,
    private val writer: Writer,
) : Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val bitSet: BitSet = read()
        val issuedCouponCommands: List<IssueCouponCommand> = processor.process(bitSet)
        writer.write(issuedCouponCommands)

        return RepeatStatus.FINISHED
    }

    private fun read(): BitSet {
        val destKey = keyGenerator.generateKey(CommonConstants.DEST_KEY_OF_THIRTY_DAY, LocalDateTime.now())
        return redisHandler.get(destKey)
    }

}
