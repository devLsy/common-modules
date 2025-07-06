package com.example.commonmodules.common.validate.sample;

import com.example.commonmodules.common.annotation.sample.ValidContent;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class CustomContentValidator implements ConstraintValidator<ValidContent, String> {

    @Override
    public boolean isValid(String content, ConstraintValidatorContext context) {
        return StringUtils.isNotEmpty(content) && content.length() <= 150;
    }
}
