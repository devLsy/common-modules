package com.example.commonmodules.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileUploadResult {
    private String realFileNm;      // 원본파일명
    private String saveFileNm;      // 저장파일명
    private String filePath;        // 파일경로
    private long fileSize;          // 파일크기
}
