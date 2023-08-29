package coupon_platform.interfaces.common.filter

import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.*

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CommonHttpRequestWebFilter : WebFilter {

    companion object {
        const val HEADER_REQUEST_UUID_KEY = "x-request-id"
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        var requestEventId = exchange.request.headers.getFirst(HEADER_REQUEST_UUID_KEY)
        if (requestEventId.isNullOrEmpty()) {
            requestEventId = UUID.randomUUID().toString()
        }

        MDC.put(HEADER_REQUEST_UUID_KEY, requestEventId)

        return chain.filter(exchange).doFinally {
            MDC.remove(HEADER_REQUEST_UUID_KEY)
        }
    }

}

