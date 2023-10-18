package coupon_platform.application.account

import coupon_platform.domain.account.AccountStoreService
import org.springframework.stereotype.Service

@Service
class AccountFacade(
    private val accountStoreService: AccountStoreService,
) {

    fun login(userId: Long) {
        // accountStoreService.login() 로그인 과정 생략
        accountStoreService.attendance(userId)
    }
}