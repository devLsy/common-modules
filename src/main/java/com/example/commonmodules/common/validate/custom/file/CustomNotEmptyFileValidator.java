package com.example.commonmodules.common.validate.custom.file;

import com.example.commonmodules.common.annotation.file.ValidNotEmptyFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class CustomNotEmptyFileValidator implements ConstraintValidator<ValidNotEmptyFile, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file != null && !file.isEmpty();
    }
}
