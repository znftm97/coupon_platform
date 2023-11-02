package coupon_platform.batch.attendance_check.tasklet

import coupon_platform.batch.attendance_check.CommonConstants
import coupon_platform.batch.attendance_check.KeyGenerator
import coupon_platform.infrastructure.redis.handler.RedisHandlerOfBitset
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class ThreeDayAttendanceCheckTasklet(
    private val keyGenerator: KeyGenerator,
    private val redisHandler: RedisHandlerOfBitset,
    private val processor: Processor,
    private val writer: Writer,
) : Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val bitSet: BitSet = read() ?: return RepeatStatus.FINISHED
        val batchParameters: List<BatchParameters> = processor.process(bitSet)
        writer.issueCoupon(batchParameters)

        return RepeatStatus.FINISHED
    }

    private fun read(): BitSet? {
        val destKey = keyGenerator.generateKey(CommonConstants.DEST_KEY_OF_THREE_DAY, LocalDateTime.now())
        return redisHandler.get(destKey)
    }

}

