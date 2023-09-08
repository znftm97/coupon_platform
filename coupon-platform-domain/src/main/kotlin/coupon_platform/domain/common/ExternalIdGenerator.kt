package coupon_platform.domain.common

import coupon_platform.domain.coupon_code.RandomNumberGenerator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class ExternalIdGenerator(
    @Qualifier("UUIDGenerator")
    val randomNumberGenerator: RandomNumberGenerator
) {
    companion object {
        const val EXTERNAL_ID_LENGTH = 16
    }

    fun generate() = randomNumberGenerator.generate(EXTERNAL_ID_LENGTH)
}