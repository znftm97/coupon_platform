package coupon_platform.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CouponPlatformBatchApplication

fun main(args: Array<String>) {
    runApplication<CouponPlatformBatchApplication>(*args)
}
