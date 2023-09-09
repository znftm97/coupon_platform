package coupon_platform.interfaces.common.response

import coupon_platform.domain.common.exception.ExceptionCode

data class BaseResponse<T>(
    private val result: Result,
    val data: T?,
    val message: String?,
    val exceptionCode: String?
) {

    enum class Result {
        SUCCESS, FAIL
    }

    companion object {
        fun <T> success(data: T?, message: String?): BaseResponse<T> {
            return BaseResponse(
                Result.SUCCESS,
                data,
                message,
                null
            )
        }

        fun <T> success(data: T?): BaseResponse<T> {
            return success(data, null)
        }

        fun fail(message: String?, errorCode: String?): BaseResponse<Nothing> {
            return BaseResponse(
                Result.FAIL,
                null,
                message,
                errorCode
            )
        }

        fun fail(errorCode: ExceptionCode): BaseResponse<Nothing> {
            return BaseResponse(
                Result.FAIL,
                null,
                errorCode.message,
                errorCode.name
            )
        }
    }

}