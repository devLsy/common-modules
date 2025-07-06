package com.example.commonmodules.common.exception;

import com.example.commonmodules.common.enums.common.ApiReturnCode;

import java.util.Set;

/**
 * 잘못된 정렬 필드 요청시 처리할 Custom Exception
 */
public class InvalidSortFieldException extends BusinessException {

    public InvalidSortFieldException(String field, Set<String> availableFields) {
        super(ApiReturnCode.INVALID_SORT_FIELD,
                String.format("잘못된 정렬 필드입니다: %s (사용 가능한 필드: %s)",
                        field,
                        String.join(", ", availableFields)
                ));
    }

    public InvalidSortFieldException(String field) {
        super(ApiReturnCode.INVALID_SORT_FIELD,
                String.format("잘못된 정렬 필드입니다: %s", field));
    }
}
