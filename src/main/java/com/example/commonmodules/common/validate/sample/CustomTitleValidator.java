package com.example.commonmodules.common.validate.sample;

import com.example.commonmodules.common.annotation.sample.ValidTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class CustomTitleValidator implements ConstraintValidator<ValidTitle, String> {

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        return !StringUtils.isNotEmpty(title) && title.length() <= 30;
    }
}
