package coupon_platform.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["coupon_platform"])
class CouponPlatformBatchApplication

fun main(args: Array<String>) {
    runApplication<CouponPlatformBatchApplication>(*args)
}