package com.example.commonmodules.common.constant;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 공통 응답 메시지 상수
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE) // 인스턴스화 방지를 위한 private 생성자
public final class ResponseMessageConst {

    /* ==================== 공통 응답 ==================== */
    public static final String SELECT_SUCCESS = "정상적으로 조회되었습니다.";
    public static final String NO_CONTENT = "조회된 데이터가 없습니다.";

    /* ==================== 공통 로그인 응답 ==================== */
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGOUT_SUCCESS = "로그아웃 성공";
    public static final String LOGIN_ACCESS_TOKEN_SUCCESS = "access token 재발급 성공";
}
