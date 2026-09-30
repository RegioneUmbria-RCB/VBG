package it.gruppoinit.stc.utils;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class Utilities {

    /**
     * Converte un oggetto Date in un oggetto XMLGregorianCalendar
     * 
     * @param c
     * @return
     */
    public static XMLGregorianCalendar getXMLGregorianCalendar(Date d) {

	GregorianCalendar cvd = new GregorianCalendar();
	cvd.setTime(d);
	return getXMLGregorianCalendar(cvd);
    }

    /**
     * Converte un oggetto GregorianCalendar in un oggetto XMLGregorianCalendar
     * 
     * @param c
     * @return
     */
    public static XMLGregorianCalendar getXMLGregorianCalendar(GregorianCalendar c) {

	XMLGregorianCalendar calendar;
	try {
	    calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
	    return calendar;
	} catch (DatatypeConfigurationException e) {
	    throw new RuntimeException("Non è stato possibile costruire un oggetto XMLGregorianCalendar a causa di:" + e.getMessage(), e);
	}
    }
}
