package coupon_platform.interfaces.common.filter

import coupon_platform.domain.common.RandomNumberGenerator
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class CommonHttpRequestInterceptor(
    val randomNumberGenerator: RandomNumberGenerator
) : HandlerInterceptor {

    companion object {
        const val HEADER_REQUEST_UUID_KEY = "x-request-id"
        const val REQUEST_EVENT_ID_LENGTH = 16;
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        var requestEventId: String = request.getHeader(HEADER_REQUEST_UUID_KEY)
        if (requestEventId.isEmpty()) {
            requestEventId =
                randomNumberGenerator.generateWithPrefix(REQUEST_EVENT_ID_LENGTH, Thread.currentThread().id.toString())
        }

        MDC.put(HEADER_REQUEST_UUID_KEY, requestEventId)

        return true
    }
}

