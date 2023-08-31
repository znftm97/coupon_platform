package coupon_platform.interfaces.common.filter

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*

@Component
class CommonHttpRequestInterceptor : HandlerInterceptor {

    companion object {
        const val HEADER_REQUEST_UUID_KEY = "x-request-id"
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        var requestEventId: String = request.getHeader(HEADER_REQUEST_UUID_KEY)
        if (requestEventId.isEmpty()) {
            requestEventId = UUID.randomUUID().toString() // FIXME 코드 쿠폰 기능 구현시, 같이 수정
        }

        MDC.put(HEADER_REQUEST_UUID_KEY, requestEventId)

        return true
    }
}

