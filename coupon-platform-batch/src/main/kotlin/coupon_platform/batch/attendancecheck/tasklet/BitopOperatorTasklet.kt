package coupon_platform.batch.attendancecheck.tasklet

import coupon_platform.batch.attendancecheck.CommonConstants.DEST_KEY_OF_SEVEN_DAY
import coupon_platform.batch.attendancecheck.CommonConstants.DEST_KEY_OF_THIRTY_DAY
import coupon_platform.batch.attendancecheck.CommonConstants.DEST_KEY_OF_THREE_DAY
import coupon_platform.batch.attendancecheck.CommonConstants.EVENT_DAY_SEVEN
import coupon_platform.batch.attendancecheck.CommonConstants.EVENT_DAY_THIRTY
import coupon_platform.batch.attendancecheck.CommonConstants.EVENT_DAY_THREE
import coupon_platform.batch.attendancecheck.KeyGenerator
import coupon_platform.infrastructure.cache.globalredis.handler.RedisHandlerOfBitset
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ThreeDaysBitopOperatorTasklet(
    private val keyGenerator: KeyGenerator,
    private val redisHandler: RedisHandlerOfBitset,
) : Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val destKey = keyGenerator.generateKey(DEST_KEY_OF_THREE_DAY, LocalDateTime.now())
        val keys: Array<String> = keyGenerator.generateKeys(EVENT_DAY_THREE)
        redisHandler.bitopAndCommand(destKey, keys)

        return RepeatStatus.FINISHED
    }
}

@Component
class SevenDaysBitopOperatorTasklet(
    private val keyGenerator: KeyGenerator,
    private val redisHandler: RedisHandlerOfBitset,
) : Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val destKey = keyGenerator.generateKey(DEST_KEY_OF_SEVEN_DAY, LocalDateTime.now())
        val keys: Array<String> = keyGenerator.generateKeys(EVENT_DAY_SEVEN)
        redisHandler.bitopAndCommand(destKey, keys)

        return RepeatStatus.FINISHED
    }
}

@Component
class ThirtyDaysBitopOperatorTasklet(
    private val keyGenerator: KeyGenerator,
    private val redisHandler: RedisHandlerOfBitset,
) : Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val destKey = keyGenerator.generateKey(DEST_KEY_OF_THIRTY_DAY, LocalDateTime.now())
        val keys: Array<String> = keyGenerator.generateKeys(EVENT_DAY_THIRTY)
        redisHandler.bitopAndCommand(destKey, keys)

        return RepeatStatus.FINISHED
    }
}