package coupon_platform.infrastructure.random_number_generator

import com.github.f4b6a3.tsid.Tsid
import com.github.f4b6a3.tsid.TsidCreator
import coupon_platform.domain.common.exception.InvalidRandomNumberLengthException
import coupon_platform.domain.common.SuspendableRandomNumberGenerator
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
@Qualifier("TSIDGenerator")
class TSIDGenerator() : SuspendableRandomNumberGenerator {

    /**
     * @param length 범위 -> 0 < length <= 13 (TSID는 최대길이가 13 이다.)
     */
    override suspend fun generate(length: Int): String {
        require(length in 13 downTo 1) {
            throw InvalidRandomNumberLengthException()
        }

        val tsid: Tsid
        coroutineScope {
            val job = async {
                TsidCreator.getTsid()
            }
            tsid = job.await()
        }

        return tsid.toString().substring(0 until length)
    }

    /**
     * @param length 범위 -> 0 < length <= 13
     * @param prefix 범위 -> 0 <= prefix <= 10
     */
    override suspend fun generateWithPrefix(length: Int, prefix: String): String {
        require(length in 13 downTo 1 && prefix.length in 10 downTo 0) {
            throw InvalidRandomNumberLengthException()
        }

        val tsid: Tsid
        coroutineScope {
            val job = async {
                TsidCreator.getTsid()
            }
            tsid = job.await()
        }

        return prefix.plus(tsid.toString()).substring(0 until length)
    }

}