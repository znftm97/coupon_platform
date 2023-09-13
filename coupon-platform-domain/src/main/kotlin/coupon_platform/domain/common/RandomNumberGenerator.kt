package coupon_platform.domain.common

interface RandomNumberGenerator {
    fun generate(length: Int): String
    fun generateWithPrefix(length: Int, prefix: String): String
}

interface SuspendableRandomNumberGenerator {
    suspend fun generate(length: Int): String
    suspend fun generateWithPrefix(length: Int, prefix: String): String
}