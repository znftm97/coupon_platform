package coupon_platform.domain.coupon_code

interface RandomNumberGenerator {
    fun generate(length: Int): String
    fun generateWithPrefix(length: Int, prefix: String): String
}