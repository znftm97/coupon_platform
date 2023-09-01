package coupon_platform.interfaces.common.exception

enum class ExceptionCode(
    val message: String,
) {
    COMMON_SYSTEM_ERROR("알 수 없는 오류가 발생했습니다."),
    COMMON_INVALID_PARAMETER("올바르지 않은 요청값입니다."),
}
