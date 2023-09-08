package coupon_platform.infrastructure.coupon_code.random_number_generator

import com.github.f4b6a3.tsid.TsidCreator
import coupon_platform.domain.common.exception.InvalidRandomNumberLengthException
import coupon_platform.domain.coupon_code.RandomNumberGenerator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
@Qualifier("TSIDGenerator")
class TSIDGenerator() : RandomNumberGenerator {

    /**
     * @param length 범위 -> 0 < length <= 13 (TSID는 최대길이가 13 이다.)
     */
    override fun generate(length: Int): String {
        require(length in 14 downTo 0) {
            throw InvalidRandomNumberLengthException()
        }

        return TsidCreator.getTsid().toString().substring(0 until length)
    }

    /**
     * @param length 범위 -> 0 < length <= 13
     * @param prefix 범위 -> 0 <= prefix <= 10
     */
    override fun generateWithPrefix(length: Int, prefix: String): String {
        require(length in 14 downTo 0 || prefix.length in 11 downTo 0) {
            throw InvalidRandomNumberLengthException()
        }

        return prefix.plus(TsidCreator.getTsid().toString()).substring(0 until length)
    }

}