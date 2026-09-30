package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.beanutils.Converter;

public class XMLGregorianCalendarConverter implements Converter {

    @SuppressWarnings({ "rawtypes", "unchecked"})
    @Override
    public Object convert(Class arg0, Object arg1) {

	if (arg1 != null) {
	    if (arg0.equals(XMLGregorianCalendar.class)) {
		//se l'ioggetto da convertire è già un XMLGregorianCalendar non faccio nulla
		if (!arg0.isAssignableFrom(arg1.getClass())) {
		    GregorianCalendar gc = null;
		    if (arg1 instanceof String) {
			gc = Utilities.getDate(arg1.toString(), WebConstants.DATE_FORMAT_PATTERN);
		    } else if (arg1 instanceof Calendar) {
			if (arg1 instanceof GregorianCalendar) {
			    gc = (GregorianCalendar) arg1;
			} else {
			    Calendar c = (Calendar) arg1;
			    gc = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
			}
		    } else if (arg1 instanceof Date) {
			Date d = (Date) arg1;
			gc = new GregorianCalendar();
			gc.setTime(d);
		    }
		    if (gc != null) {
			arg1 = Utilities.getXMLGregorianCalendar(gc);
		    } else {
			throw new IllegalArgumentException("XMLGregorianCalendarConverter non può effettuare conversioni dal tipo "
				+ arg1.getClass().getName());
		    }
		}
	    } else {
		throw new IllegalArgumentException("XMLGregorianCalendarConverter non può effettuare conversioni nel tipo " + arg0.getName());
	    }
	}
	return arg1;
    }
}
