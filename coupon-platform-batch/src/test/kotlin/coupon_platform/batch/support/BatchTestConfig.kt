package coupon_platform.batch.support

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@ComponentScan(
    basePackages = ["coupon_platform"],
    excludeFilters = [
        ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = [".*(Batch).*"]
        )
    ]
)
class BatchTestConfig {
}