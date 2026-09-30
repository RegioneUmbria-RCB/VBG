package it.gruppoinit.nlapec.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

    public static Date getDate(String giorno, String ora, String zona) {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyyHH:mm:ssZ");
	String data = giorno + ora + zona;
	Date d = null;
	try {
	    d = sdf.parse(data);
	} catch (ParseException e) {
	    log.error("getDate(): {}", e.getMessage());
	}
	return d;
    }

    public static String formatDate(XMLGregorianCalendar cal, String format) {

	String ds = "";
	SimpleDateFormat sdf = new SimpleDateFormat(format);
	GregorianCalendar g = cal.toGregorianCalendar();
	GregorianCalendar now = new GregorianCalendar();
	// setto ora e monuti a quelli attuali perchè le date dell'xml non contengono l'ora
	g.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY));
	g.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
	Date d = g.getTime();
	ds = sdf.format(d);
	return ds;
    }

    public static String getOreMinuti(Date date) {

	if (date != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	    try {
		return sdf.format(date);
	    } catch (Exception e) {
		log.error("getOreMinuti({}): {}", date, e.getMessage());
	    }
	}
	return null;
    }
}