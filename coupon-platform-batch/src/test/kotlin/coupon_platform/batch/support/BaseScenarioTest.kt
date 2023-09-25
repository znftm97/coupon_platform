package coupon_platform.interfaces.support

import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql

@SpringBatchTest
@ActiveProfiles("test")
@Sql(value = ["/sql/data.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = ["/sql/clean-up.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
interface BaseScenarioTest