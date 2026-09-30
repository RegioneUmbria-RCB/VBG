package com.paevolution.appiogateway.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class Utils {

    private static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static Timestamp convertDateToSQLTimestamp(Date fromDate) throws ParseException {

	String formattedISODate = new SimpleDateFormat(ISO_8601_FORMAT).format(fromDate);
	SimpleDateFormat outputDateFormat = new SimpleDateFormat(ISO_8601_FORMAT);
	return new Timestamp(outputDateFormat.parse(formattedISODate).getTime());
    }

    public static Timestamp convertStringToSQLTimestamp(String timestampAsString) {

	ZoneId idz = ZoneId.of("Europe/Rome");
	ZonedDateTime zdt = ZonedDateTime.parse(timestampAsString).toInstant().atZone(idz);
	return Timestamp.from(zdt.toInstant());
    }
}
