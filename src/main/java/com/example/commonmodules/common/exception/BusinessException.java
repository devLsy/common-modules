package com.example.commonmodules.common.exception;

import com.example.commonmodules.common.enums.common.ApiReturnCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private ApiReturnCode apiReturnCode;
    private String message;

    public BusinessException(ApiReturnCode apiReturnCode) {
        this.apiReturnCode = apiReturnCode;
    }
}
