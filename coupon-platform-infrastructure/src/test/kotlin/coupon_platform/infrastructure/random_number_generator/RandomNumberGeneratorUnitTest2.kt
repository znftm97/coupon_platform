package coupon_platform.infrastructure.random_number_generator

import coupon_platform.domain.common.exception.InvalidRandomNumberLengthException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual

class RandomNumberGeneratorUnitTest2 : FunSpec({

    val uuidGenerator = UUIDGenerator()
    val tsidGenerator = TSIDGenerator()

    data class Length(val length: Int)
    data class Prefix(val length: Int, val prefix: String)

    context("uuidGenerator는 length > 32 || length <= 0 인 경우 InvalidRandomNumberLengthException 예외를 던진다.") {
        withData(
            Length(100),
            Length(33),
            Length(0),
            Length(-1),
        ) { (length) ->
            shouldThrow<InvalidRandomNumberLengthException> {
                uuidGenerator.generate(length).length
            }
        }
    }

    context("uuidGenerator는 0 < length <= 32 인 경우 지정한 length 길이의 난수를 반환한다.") {
        withData(
            Length(1),
            Length(10),
            Length(32),
        ) { (length) ->
            uuidGenerator.generate(length).length shouldBeEqual length
        }
    }

    context("uuidGenerator는 prefix의 길이가 > 10 인 경우 InvalidRandomNumberLengthException 예외를 던진다.") {
        withData(
            Prefix(10, "abcdefghijk"),
            Prefix(10, "abcdefghijkabcdefghijk"),
            Prefix(10, "abcdefghijkabcdefghijkabcdefghijkabcdefghijkabcdefghijk"),
        ) { (length, prefix) ->
            shouldThrow<InvalidRandomNumberLengthException> {
                uuidGenerator.generateWithPrefix(length, prefix).length
            }
        }
    }

    context("uuidGenerator는 0 <= prefix의 길이 <= 10 인 경우, prefix를 포함하여 지정한 length 길이의 난수를 반환한다.") {
        withData(
            Prefix(10, "a"),
            Prefix(10, "abc"),
            Prefix(10, "abcdefghij"),
        ) { (length, prefix) ->
            uuidGenerator.generateWithPrefix(length, prefix).length shouldBeEqual length
        }
    }

    context("tsidGenerator는 length > 13 || length <= 0 인 경우 InvalidRandomNumberLengthException 예외를 던진다.") {
        withData(
            Length(100),
            Length(14),
            Length(0),
            Length(-1),
        ) { (length) ->
            shouldThrow<InvalidRandomNumberLengthException> {
                tsidGenerator.generate(length).length
            }
        }
    }

    context("tsidGenerator는 0 < length <= 13 인 경우 지정한 length 길이의 난수를 반환한다.") {
        withData(
            Length(1),
            Length(10),
            Length(13),
        ) { (length) ->
            tsidGenerator.generate(length).length shouldBeEqual length
        }
    }

    context("tsidGenerator는 prefix의 길이가 > 10 인 경우 InvalidRandomNumberLengthException 예외를 던진다.") {
        withData(
            Prefix(10, "abcdefghijk"),
            Prefix(10, "abcdefghijkabcdefghijk"),
            Prefix(10, "abcdefghijkabcdefghijkabcdefghijkabcdefghijkabcdefghijk"),
        ) { (length, prefix) ->
            shouldThrow<InvalidRandomNumberLengthException> {
                tsidGenerator.generateWithPrefix(length, prefix).length
            }
        }
    }

    context("tsidGenerator는 0 <= prefix의 길이 <= 10 인 경우, prefix를 포함하여 지정한 length 길이의 난수를 반환한다.") {
        withData(
            Prefix(10, "a"),
            Prefix(10, "abc"),
            Prefix(10, "abcdefghij"),
        ) { (length, prefix) ->
            tsidGenerator.generateWithPrefix(length, prefix).length shouldBeEqual length
        }
    }
})