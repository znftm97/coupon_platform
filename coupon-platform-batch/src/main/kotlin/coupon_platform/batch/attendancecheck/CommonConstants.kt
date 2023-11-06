package coupon_platform.batch.attendancecheck

object CommonConstants {
    const val ATTENDANCE_CHECK_PREFIX = "attendance:check:"

    /*redis bitop 연산 결과를 저장할 key 이름, 사용시 suffix로 오늘 날짜 추가*/
    const val DEST_KEY_OF_THREE_DAY = "dest:three:"
    const val DEST_KEY_OF_SEVEN_DAY = "dest:seven:"
    const val DEST_KEY_OF_THIRTY_DAY = "dest:thirty:"

    /*n일 연속 출석체크 이벤트 일수*/
    const val EVENT_DAY_THREE = 3L;
    const val EVENT_DAY_SEVEN = 7L;
    const val EVENT_DAY_THIRTY = 30L;

}