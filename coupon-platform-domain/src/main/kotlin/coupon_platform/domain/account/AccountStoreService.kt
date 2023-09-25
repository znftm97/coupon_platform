package coupon_platform.domain.account

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AccountStoreService(
    val accountStore: AccountStore,
) {

    fun attendance(userId: Long) {
        accountStore.attendance(userId)
    }
}