package it.gruppoinit.pal.gp.core.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTests {

    private final String separatoreDefault = ", ";

    @Test()
    public void intervallo_tra_due_date_torna_array_di_date() {

	Calendar s = new GregorianCalendar(1983, 6, 26, 12, 34, 56);
	Calendar c = new GregorianCalendar(1983, 8, 12, 12, 34, 56);
	List<Date> date = DateUtils.getDaysBetweenDates(s.getTime(), c.getTime());
	Assert.assertEquals("Deve tornare 49 giorni ", 49, date.size());
    }

    @Test()
    public void data_convertita_in_stringa() {

	Calendar s = new GregorianCalendar(1983, 6, 26, 12, 34, 56);
	Calendar c = new GregorianCalendar(1983, 8, 12, 12, 34, 56);
	Calendar a = new GregorianCalendar(2013, 1, 21, 12, 34, 56);
	Calendar l = new GregorianCalendar(2015, 0, 26, 12, 34, 56);
	List<Date> date = new ArrayList<Date>(0);
	date.add(s.getTime());
	date.add(c.getTime());
	date.add(a.getTime());
	date.add(l.getTime());
	String retVal = DateUtils.getDateConcatenate(date, this.separatoreDefault);
	String expected = "26/07/1983, 12/09/1983, 21/02/2013, 26/01/2015";
	Assert.assertEquals("Deve tornare: " + expected, expected, retVal);
    }
}
