package com.example.commonmodules.common.enums.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum UploadFileType {

    SAMPLE("/sample", "샘플 파일", Arrays.asList(FileExtType.PNG, FileExtType.JPG, FileExtType.JPEG)),
    USER("/user", "사용자 프로필 이미지 파일", Arrays.asList(FileExtType.PNG, FileExtType.JPG, FileExtType.JPEG)),
    ;

    private final String code;
    private final String codeName;
    private final List<FileExtType> fileExtTypes;

    /**
     * 파일 타입 목록
     *
     * @return
     */
    public List<String> getFileExtTypes() {
        return fileExtTypes.stream().map(FileExtType::getFileExt).collect(Collectors.toList());
    }

    @RequiredArgsConstructor
    @Getter
    public enum FileExtType {
        PNG("png"),
        JPG("jpg"),
        JPEG("jpeg"),
        PDF("pdf"),
        ;

        private final String fileExt;
    }
}
