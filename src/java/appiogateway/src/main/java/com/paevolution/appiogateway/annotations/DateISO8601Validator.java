package com.paevolution.appiogateway.annotations;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class DateISO8601Validator implements ConstraintValidator<DateISO8601Constraint, String> {

    private final String ISO_8601_REGEXP = "^([\\+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24\\:?00)([\\.,]\\d+(?!:))?)?(\\17[0-5]\\d([\\.,]\\d+)?)?([zZ]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?$";
    private final Pattern DATE_PATTERN = Pattern.compile(ISO_8601_REGEXP);
    /*
     * string <UTCISODateFromString> (Timestamp) A date-time field in ISO-8601
     * format and UTC timezone.
     */
    // REGEX
    /*
     * ^([\+-]?\d{4}(?!\d{2}\b))((-?)((0[1-9]|1[0-2])(\3([12]\d|0[1-9]|3[01]))?|W([0
     * -4]\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\d|[12]\d{2}|3([0-5]\d|6[1-6])))([T\s]
     * ((([01]\d|2[0-3])((:?)[0-5]\d)?|24\:?00)([\.,]\d+(?!:))?)?(\17[0-5]\d([\.,]\d
     * +)?)?([zZ]|([\+-])([01]\d|2[0-3]):?([0-5]\d)?)?)?)?$
     * 
     */

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

	if (StringUtils.isNotBlank(value)) {
	    return DATE_PATTERN.matcher(value).matches();
	}
	return true;
    }
}