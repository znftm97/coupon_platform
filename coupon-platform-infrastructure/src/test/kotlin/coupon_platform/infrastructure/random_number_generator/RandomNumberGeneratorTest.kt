package coupon_platform.infrastructure.random_number_generator

import coupon_platform.domain.common.exception.InvalidRandomNumberLengthException
import coupon_platform.infrastructure.coupon_code.random_number_generator.TSIDGenerator
import coupon_platform.infrastructure.coupon_code.random_number_generator.UUIDGenerator
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual

class RandomNumberGeneratorTest : BehaviorSpec({

    val uuidGenerator = UUIDGenerator()
    val tsidGenerator = TSIDGenerator()

    given("uuidGenerator는") {
        `when`("length <= 0 이면") {
            val length = 0;
            then("예외를 던진다.") {
                shouldThrow<InvalidRandomNumberLengthException> {
                    uuidGenerator.generate(length)
                }
            }
        }

        `when`("length > 32 이면") {
            val length = 33;
            then("예외를 던진다.") {
                shouldThrow<InvalidRandomNumberLengthException> {
                    uuidGenerator.generate(length)
                }
            }
        }

        `when`("0 < length <= 32 이면") {
            val length = 15
            then("지정한 length 길이의 난수를 반환한다.") {
                uuidGenerator.generate(length).length shouldBeEqual length
            }
        }

        `when`("prefix의 길이가 > 10 이면") {
            val length = 15
            val prefix = "abcdefghijk"
            then("예외를 던진다.") {
                shouldThrow<InvalidRandomNumberLengthException> {
                    uuidGenerator.generateWithPrefix(length, prefix)
                }
            }
        }

        `when`("0 <= prefix의 길이 <= 10 이면") {
            val length = 15
            val prefix = "prefix"
            then("prefix를 포함한, 지정한 length 길이의 난수를 반환한다.") {
                uuidGenerator.generateWithPrefix(length, prefix).length shouldBeEqual length
            }
        }
    }

    given("tsidGenerator는") {
        `when`("length <= 0 이면") {
            val length = 0
            then("예외를 던진다.") {
                shouldThrow<InvalidRandomNumberLengthException> {
                    tsidGenerator.generate(length)
                }
            }
        }

        `when`("length > 13 이면") {
            val length = 14
            then("예외를 던진다.") {
                shouldThrow<InvalidRandomNumberLengthException> {
                    tsidGenerator.generate(length)
                }
            }
        }

        `when`("0 < length <= 13 이면") {
            val length = 13
            then("지정한 length 길이의 난수를 반환한다.") {
                tsidGenerator.generate(length).length shouldBeEqual length
            }
        }

        `when`("prefix의 길이가 > 10 이면") {
            val length = 5
            val prefix = "abcdefghijk"
            then("예외를 던진다.") {
                shouldThrow<InvalidRandomNumberLengthException> {
                    tsidGenerator.generateWithPrefix(length, prefix)
                }
            }
        }

        `when`("0 <= prefix의 길이 <= 10 이면") {
            val length = 5
            val prefix = "abc"
            then("prefix를 포함한, 지정한 length 길이의 난수를 반환한다.") {
                tsidGenerator.generateWithPrefix(length, prefix).length shouldBeEqual length
            }
        }
    }

})