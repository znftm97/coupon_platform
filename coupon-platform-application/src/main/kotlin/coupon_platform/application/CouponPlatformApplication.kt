package coupon_platform.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CouponPlatformApplication

fun main(args: Array<String>) {
    runApplication<CouponPlatformApplication>(*args)
}
