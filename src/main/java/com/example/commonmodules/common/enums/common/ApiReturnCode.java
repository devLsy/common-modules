package com.example.commonmodules.common.enums.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApiReturnCode {

    /* ==================== 2XX ==================== */
    NO_DATA("데이터가 없습니다.", 204),

    /* ==================== 4XX ==================== */
    BAD_REQUEST_TEXT("잘못된 요청 본문 형식입니다.", 400),
    INVALID_SORT_FIELD("잘못된 정렬 필드입니다", 400),
    NO_DATA_ERROR("데이터가 없습니다.", 404),
    NO_URL_ERROR("잘못된 URL 입니다.", 404),
    METHOD_NOT_ALLOWED("해당 요청에 대해 허용되지 않은 HTTP 메서드입니다. (Method Not Allowed)", 405),
    UNSUPPORTED_MEDIA_TYPE("지원하지 않는 미디어 타입입니다.", 415),

    // ===== token =====
    EXPIRED_TOKEN_ERROR("만료된 토큰입니다.", 401),
    UNAUTHORIZED_ERROR("인증이 필요합니다.", 401),
    UNAUTHORIZED_TOKEN_ERROR("유효하지 않은 토큰입니다.", 401),
    FORBIDDEN_ERROR("접근 권한이 없습니다.", 403),

    // ===== user =====
    LOGIN_ID_FAIL_ERROR("등록된 아이디가 없습니다.", 404),
    LOGIN_PWD_FAIL_ERROR("비밀번호가 틀렸습니다.", 401),
    ID_CONFLICT_ERROR("중복된 아이디 입니다.", 409),
    PHONE_CONFLICT_ERROR("중복된 전화번호 입니다.", 409),
    EMAIL_CONFLICT_ERROR("중복된 이메일 입니다.", 409),

    // ===== file =====
    REQUIRED_FILE_ERROR("필수 첨부파일이 누락되었습니다.", 400),
    FILE_UPLOAD_ERROR("파일 업로드중 에러가 발생했습니다.", 404),
    NO_FILE_DATA_ERROR("첨부파일이 없습니다.", 404),
    FILE_SIZE_EXCEEDING_ERROR("파일 사이즈(10MB)가 초과되었습니다.", 413),
    FILE_EXTENSION_ERROR("허용된 파일 확장자가 아닙니다.", 415),

    /* ==================== 5XX ==================== */
    SERVER_ERROR("서버에서 오류가 발생했습니다.", 500);

    public static final String RETURN_CODE = "리턴 코드 구분";

    private final String message;
    private final int code;
}
