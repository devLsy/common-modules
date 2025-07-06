package com.example.commonmodules.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "Exception Msg")
@Builder
@Getter
public class ExceptionMsg {

    @Schema(description = "success")
    private Boolean success;

    @Schema(description = "path")
    private String path;

    @Schema(description = "TimeStamp")
    private LocalDateTime timestamp;

    @Schema(description = "ErrorCode")
    private int errorCode;

    @Schema(description = "ErrorMessage")
    private String errorMessage;
}
