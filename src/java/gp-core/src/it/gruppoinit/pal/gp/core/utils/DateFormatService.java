package it.gruppoinit.pal.gp.core.utils;

import java.util.Date;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

@Service
public class DateFormatService implements IDateFormatService {

    @Override
    public Date getDateDDMMYYYY(String data) {

	return Utilities.parseDateString(data, WebConstants.DATE_FORMAT_PATTERN);
    }

    @Override
    public Date getDate(String data, String format) {

	return Utilities.parseDateString(data, format);
    }
}
