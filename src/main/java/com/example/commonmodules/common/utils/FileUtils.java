package com.example.commonmodules.common.utils;

import com.example.commonmodules.common.config.FileUploadConfig;
import com.example.commonmodules.common.dto.FileUploadResult;
import com.example.commonmodules.common.enums.common.ApiReturnCode;
import com.example.commonmodules.common.enums.file.UploadFileType;
import com.example.commonmodules.common.exception.BusinessException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

/**
 * 파일관련 util
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileUtils {

    private final FileUploadConfig fileUploadProperties;

    private static String ROOT_PATH;
    private static long FILE_MAX_SIZE; // 10MB

    @PostConstruct
    public void init() {
        ROOT_PATH = fileUploadProperties.getRootPath();
        FILE_MAX_SIZE = fileUploadProperties.getFileMaxSize();
    }

    /**
     * 파일 업로드
     *
     * @param uploadFileType
     * @param file
     * @return
     * @throws IOException
     */
    public static FileUploadResult uploadFile(MultipartFile file, UploadFileType uploadFileType) throws IOException {

        // 파일 validate
        validateFile(file, uploadFileType);

        // 서버에 파일 저장시 uuid 값으로 변경
        String saveFileName = getFileNameWithUUID(file.getOriginalFilename());

        // 저장 경로 설정
        Path filePath = Paths.get(ROOT_PATH, uploadFileType.getCode(), saveFileName);

        // 디렉토리 없을 경우 생성
        Files.createDirectories(filePath.getParent());

        // 파일 저장(중복시 덮어쓰기)
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        log.info("파일 저장 경로: {}", filePath.toAbsolutePath());
        log.info("파일 저장 경로에  파일 존재 여부: {}", Files.exists(filePath));

        // 파일정보 반환
        return FileUploadResult.builder()
                .realFileNm(file.getOriginalFilename())
                .saveFileNm(saveFileName)
                .filePath(filePath.toString())
                .fileSize(file.getSize())
                .build();
    }

    /**
     * 파일 업로드시 validate
     *
     * @param file
     */
    public static void validateFile(MultipartFile file, UploadFileType uploadFileType) {

        if (ObjectUtils.isEmpty(file)) {
            throw new BusinessException(ApiReturnCode.FILE_UPLOAD_ERROR);
        }

        // 파일 사이즈
        if (file.getSize() > FILE_MAX_SIZE) {
            throw new BusinessException(ApiReturnCode.FILE_SIZE_EXCEEDING_ERROR);
        }

        // 파일 확장자
        if (!uploadFileType.getFileExtTypes().contains(getExtension(Objects.requireNonNull(file.getOriginalFilename())))) {
            throw new BusinessException(ApiReturnCode.FILE_EXTENSION_ERROR);
        }
    }

    /**
     * 파일명에서 확장자 추출
     *
     * @param fileName
     * @return
     */
    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 원본 파일명을 UUID값으로 변경
     *
     * @param fileName
     * @return
     */
    public static String getFileNameWithUUID(String fileName) {
        return UUID.randomUUID().toString().replace("-", "") + "." + getExtension(fileName);
    }

    /**
     * 서버에 저장된 파일 삭제
     *
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath) {
        File deleteFile = new File(filePath);
        if (!deleteFile.exists()) {
            throw new BusinessException(ApiReturnCode.NO_FILE_DATA_ERROR);
        }

        return deleteFile.delete();
    }
}
