package it.gruppoinit.pal.gp.core.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class DateUtils {

    /**
     * La funzione torna tutti i giorni che intercorrono tra la data di inizio ( compresa ) e la data di fine ( compresa
     * )
     * 
     * @param startdate
     * @param enddate
     * @return
     */
    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate) {

	if (startdate == null || enddate == null)
	    return null;
	List<Date> dates = new ArrayList<Date>();
	Calendar calendar = new GregorianCalendar();
	calendar.setTime(startdate);
	while (calendar.getTime().before(enddate)) {
	    Date result = calendar.getTime();
	    dates.add(result);
	    calendar.add(Calendar.DATE, 1);
	}
	dates.add(enddate);
	return dates;
    }

    public static String getDateConcatenate(List<Date> date, String separatore) {

	String retVal = "";
	if (StringUtils.isEmpty(separatore)) {
	    throw new IllegalArgumentException("Impossibile utilizzare la funzionalità DateUtils.getDateConcatenate senza valorizzare il separatore");
	}
	if (date != null) {
	    for (Date data : date) {
		retVal += Utilities.formatDate(data, Boolean.FALSE) + separatore;
	    }
	}
	if (!StringUtils.isEmpty(retVal)) {
	    retVal = retVal.substring(0, retVal.length() - separatore.length());
	}
	return retVal;
    }
}
