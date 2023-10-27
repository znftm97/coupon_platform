package coupon_platform.infrastructure.redis.util

object RedisTTLConstants {
    /*출석체크 BITOP 연산 결과 데이터 TTL*/
    const val ATTENDANCE_CHECK_BITOP_RESULT_KEY_TTL = 60L

    /*발급된 쿠폰 데이터 TTL*/
    const val ISSUED_COUPON_KEY_TTL = 7L
}