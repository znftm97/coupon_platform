package coupon_platform.interfaces.common.response

import coupon_platform.interfaces.common.exception.BaseException
import coupon_platform.interfaces.common.exception.ExceptionCode
import coupon_platform.interfaces.common.filter.CommonHttpRequestWebFilter
import mu.KLogging
import org.slf4j.MDC
import org.springframework.core.NestedExceptionUtils
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = KLogging().logger

    companion object {
        private val SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST: List<ExceptionCode> = mutableListOf()
    }

    /**
     * http status: 500 AND result: FAIL
     * 시스템 예외 상황. 집중 모니터링 대상
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [Exception::class])
    fun onException(e: Exception?): BaseResponse<Nothing> {
        val eventId = MDC.get(CommonHttpRequestWebFilter.HEADER_REQUEST_UUID_KEY)
        log.error("eventId = {} ", eventId, e)

        return BaseResponse.fail(ExceptionCode.COMMON_SYSTEM_ERROR)
    }

    /**
     * http status: 200 AND result: FAIL
     * 시스템은 이슈 없고, 비즈니스 로직 처리에서 에러가 발생함
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = [BaseException::class])
    fun onBaseException(e: BaseException): BaseResponse<Nothing> {
        val eventId = MDC.get(CommonHttpRequestWebFilter.HEADER_REQUEST_UUID_KEY)
        if (SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST.contains(e.exceptionCode)) {
            log.error(
                "[BaseException] eventId = {}, cause = {}, errorMsg = {}",
                eventId,
                NestedExceptionUtils.getMostSpecificCause(e),
                NestedExceptionUtils.getMostSpecificCause(e).message
            )
        } else {
            log.warn(
                "[BaseException] eventId = {}, cause = {}, errorMsg = {}",
                eventId,
                NestedExceptionUtils.getMostSpecificCause(e),
                NestedExceptionUtils.getMostSpecificCause(e).message
            )
        }

        return BaseResponse.fail(e.message, e.exceptionCode.name)
    }


    /**
     * http status: 400 AND result: FAIL
     * request parameter 에러
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): BaseResponse<Nothing> {
        val eventId = MDC.get(CommonHttpRequestWebFilter.HEADER_REQUEST_UUID_KEY)
        log.warn(
            "[BaseException] eventId = {}, errorMsg = {}",
            eventId,
            NestedExceptionUtils.getMostSpecificCause(e).message
        )

        val fieldError: FieldError? = e.bindingResult.fieldError
        return when(fieldError) {
            null -> BaseResponse.fail(ExceptionCode.COMMON_INVALID_PARAMETER.message, ExceptionCode.COMMON_INVALID_PARAMETER.name)
            else -> {
                val message = "Request Error ${fieldError.field}=${fieldError.rejectedValue} (${fieldError.defaultMessage})"
                BaseResponse.fail(message, ExceptionCode.COMMON_INVALID_PARAMETER.name)
            }
        }
    }

}