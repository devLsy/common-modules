package com.example.commonmodules.common.constant;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 스웨거 예제 json 상수
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE) // 인스턴스화 방지를 위한 private 생성자
public class SwaggerExampleConst {

    /* ==================== Sample ==================== */
    public static final String SAMPLE_SAVE_EXAMPLE_1 = """
                [
                    {
                        "title": "title11",
                        "content": "content11"
                    }
                ]
            """;

    public static final String SAMPLE_SAVE_LIST_EXAMPLE_1 = """
                [
                    {
                        "title": "title11",
                        "content": "content11"
                    },
                    {
                        "title": "title12",
                        "content": "content12"
                    }
                ]
            """;

    public static final String SAMPLE_UPDATE_EXAMPLE_1 = """
                {
                    "title": "수정된 제목1",
                    "content": "수정된 내용1"
                }
            """;
}
