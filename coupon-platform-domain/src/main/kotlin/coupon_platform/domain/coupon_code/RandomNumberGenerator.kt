package coupon_platform.domain.coupon_code

import coupon_platform.domain.common.exception.InvalidLengthException

interface RandomNumberGenerator {
    fun generate(length: Int): String
    fun generateWithPrefix(length: Int, prefix: String): String

    fun validateLength(length: Int) {
        require(length <= 0) {
            throw InvalidLengthException()
        }
    }

}