package it.gruppoinit.utils;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utilities {

    private static final Logger log = LoggerFactory.getLogger(Utilities.class);

    /**
     * Converte un oggetto GregorianCalendar in un oggetto XMLGregorianCalendar non impostando il timezone
     * 
     * @return
     */
    public static XMLGregorianCalendar getXMLGregorianCalendarWithoutTimeZone(GregorianCalendar c) {

	XMLGregorianCalendar xmldata;
	try {
	    xmldata = DatatypeFactory.newInstance().newXMLGregorianCalendar();
	    xmldata.setYear(c.get(Calendar.YEAR));
	    // DA RICORDARSI: il Mese per XmlGregorianCalendar parte da 1 e non da 0 come GregorianCalendar
	    xmldata.setMonth(c.get(Calendar.MONTH) + 1);
	    xmldata.setDay(c.get(Calendar.DATE));
	    return xmldata;
	} catch (DatatypeConfigurationException e) {
	    throw new RuntimeException("Non è stato possibile costruire un oggetto XMLGregorianCalendar a causa di:" + e.getMessage(), e);
	}
    }

    public static String marshallObject(Object obj) {

	StringWriter stringWriter = new StringWriter();
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
	    Marshaller marshaller = jaxbContext.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
	    marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    marshaller.marshal(obj, stringWriter);
	    return stringWriter.toString();
	} catch (Exception e1) {
	    log.error("marshallObject: {}", e1);
	    throw new RuntimeException(e1);
	}
    }

    public static void gracefullyReleaseResources(Connection c, PreparedStatement pstmt, ResultSet rs) {

	if (rs != null) {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	}
	if (pstmt != null) {
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
	if (c != null) {
	    try {
		c.close();
	    } catch (Exception e) {
	    }
	}
    }

    public static void logAndThrowException(String message, Exception e, Class<?> c) {

	LoggerFactory.getLogger(c).error(message + ": {}\n{}", e.getMessage(), e);
	throw new RuntimeException(message + ": " + e.getMessage(), e);
    }

    public static void logAndThrowException(String message, Class<?> c) {

	LoggerFactory.getLogger(c).error(message);
	throw new RuntimeException(message);
    }

    public static String extractExtension(String nomeFile) {

	if (nomeFile == null || nomeFile.equals("")) {
	    throw new RuntimeException("Attenzione!! il nome del file non può essere nullo o vuoto");
	}
	return nomeFile.substring(nomeFile.lastIndexOf('.') + 1);
    }
}
