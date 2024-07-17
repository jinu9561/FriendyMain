package web.mvc.exception.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {


    ACCESS_DENIED(600, "로그인하고 이용해주세요."),

    NOTFOUND_ID(601, "존재하지 않는 ID입니다."),
    WRONG_PASS( 602, "비밀번호 오류입니다.."),
    FIND_ID(603,"존재하는 아이디 입니다."),
    WRONG_DATE(604,"날짜 형식이 아닙니다."),
    NOTFOUND_EMAILTOKEN(605,"이메일이 조회되지 않습니다"),
    NOTFOUND_SMSTOKEN(606,"핸드폰 번호가 조회되지 않습니다"),
    WRONG_IMG(607,"이미지 파일형식이 올바르지 않습니다"),
    OVER_FILE(608,"파일의 크기가 초과되었습니다."),
    NOTFOUND_PROFILE(609, "프로필 조회에 실패했습니다"),
    NOTFOUND_PLACE(610,"장소가 조회에 실패했습니다"),
    WRONG_TYPE(611, "비밀번호는 숫자로 입력해주세요."),
    NOTFOUND_TRANSACTION(612,"주문 내역 조회에 실패했습니다."),
    MAX_ENTRY(700, "최대인원입니다"),
    JSON_PROCESSING_ERROR(701,"파싱에러"),
    NOTFOUND_PHOTOBOARD(613,"사진 게시글 조회에 실패했습니다."),
    INVALID_USER_ID(614,"아이디 형식이 맞지 않습니다."),
    INVALID_BIRTH_DATE(615,"생년월일을 확인해 주세요."),
    INVALID_PHONE_NUMBER(616,"전화번호를 확인해 주세요.");

    private final int status;
    private final String message;
}
