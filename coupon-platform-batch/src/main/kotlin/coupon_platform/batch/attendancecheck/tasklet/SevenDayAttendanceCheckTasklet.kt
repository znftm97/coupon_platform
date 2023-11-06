package coupon_platform.batch.attendancecheck.tasklet

import coupon_platform.batch.attendancecheck.CommonConstants
import coupon_platform.batch.attendancecheck.KeyGenerator
import coupon_platform.infrastructure.cache.global_redis.handler.RedisHandlerOfBitset
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class SevenDayAttendanceCheckTasklet(
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
        val destKey = keyGenerator.generateKey(CommonConstants.DEST_KEY_OF_SEVEN_DAY, LocalDateTime.now())
        return redisHandler.get(destKey)
    }

}

