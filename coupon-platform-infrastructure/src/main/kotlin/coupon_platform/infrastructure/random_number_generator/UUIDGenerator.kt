package coupon_platform.infrastructure.random_number_generator

import coupon_platform.domain.common.exception.InvalidRandomNumberLengthException
import coupon_platform.domain.common.RandomNumberGenerator
import org.springframework.stereotype.Component
import java.util.*

@Component
class UUIDGenerator() : RandomNumberGenerator {

    /**
     * @param length 범위 -> 0 < length <= 32
     */
    override fun generate(length: Int): String {
        require(length in 32 downTo 1) {
            throw InvalidRandomNumberLengthException()
        }

        return UUID.randomUUID().toString().substring(0 until length)
    }

    /**
     * @param length 범위 -> 0 < length <= 32
     * @param prefix 범위 -> 0 <= prefix <= 10
     */
    override fun generateWithPrefix(length: Int, prefix: String): String {
        require(length in 32 downTo 1 && prefix.length in 10 downTo 0) {
            throw InvalidRandomNumberLengthException()
        }

        return prefix.plus(UUID.randomUUID().toString()).substring(0 until length)
    }

}