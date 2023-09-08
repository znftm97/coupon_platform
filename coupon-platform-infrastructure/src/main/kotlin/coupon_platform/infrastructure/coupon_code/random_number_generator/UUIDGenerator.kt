package coupon_platform.infrastructure.coupon_code.random_number_generator

import coupon_platform.domain.coupon_code.RandomNumberGenerator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.*

@Component
@Qualifier("UUIDGenerator")
class UUIDGenerator() : RandomNumberGenerator {

    override fun generate(length: Int): String {
        validateLength(length)
        return UUID.randomUUID().toString().substring(0 until length)
    }

    override fun generateWithPrefix(length: Int, prefix: String): String {
        validateLength(length)
        return prefix.plus(UUID.randomUUID().toString()).substring(0 until length)
    }

}