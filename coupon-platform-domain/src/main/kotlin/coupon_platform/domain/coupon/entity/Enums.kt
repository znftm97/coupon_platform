package coupon_platform.domain.coupon.entity

enum class DiscountType(description: String) {
    PRICE("정액"),
    RATE("정률")
}

enum class ApplyType(description: String) {
    PRODUCT("상품"),
    ACCOUNT("사용자")
}