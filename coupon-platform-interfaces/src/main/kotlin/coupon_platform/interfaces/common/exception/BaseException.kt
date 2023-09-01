package coupon_platform.interfaces.common.exception

class BaseException : RuntimeException {
    val exceptionCode: ExceptionCode

    constructor(exceptionCode: ExceptionCode) : super(exceptionCode.message) {
        this.exceptionCode = exceptionCode
    }

    constructor(message: String, exceptionCode: ExceptionCode) : super(message) {
        this.exceptionCode = exceptionCode
    }

    constructor(message: String, exceptionCode: ExceptionCode, cause: Throwable) : super(message, cause) {
        this.exceptionCode = exceptionCode
    }
}