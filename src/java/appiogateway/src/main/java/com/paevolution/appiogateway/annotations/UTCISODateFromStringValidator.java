package com.paevolution.appiogateway.annotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class UTCISODateFromStringValidator implements ConstraintValidator<UTCISODateFromStringConstraint, String> {

    private static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Override
    public void initialize(UTCISODateFromStringConstraint date) {

    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext cxt) {

	if (!StringUtils.isEmpty(date)) {
	    try {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_8601_FORMAT);
		LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(date));
	    } catch (Exception e) {
		return false;
	    }
	}
	return true;
    }
}
