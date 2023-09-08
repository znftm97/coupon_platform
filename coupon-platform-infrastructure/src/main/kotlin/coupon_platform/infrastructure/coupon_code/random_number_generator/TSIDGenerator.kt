package coupon_platform.infrastructure.coupon_code.random_number_generator

import com.github.f4b6a3.tsid.TsidCreator
import coupon_platform.domain.coupon_code.RandomNumberGenerator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
@Qualifier("TSIDGenerator")
class TSIDGenerator() : RandomNumberGenerator {
    override fun generate(length: Int): String = TsidCreator.getTsid().toString().substring(0 until length)

    override fun generateWithPrefix(length: Int, prefix: String): String =
        prefix.plus(TsidCreator.getTsid().toString()).substring(0 until length)
}