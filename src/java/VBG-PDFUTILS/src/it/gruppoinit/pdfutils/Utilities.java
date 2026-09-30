package it.gruppoinit.pdfutils;

import it.gruppoinit.pdfutils.schemas.messages.ObjectFactory;
import it.gruppoinit.pdfutils.schemas.messages.RecuperaDatiDaPDFResponseType;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.cxf.common.util.Base64Exception;
import org.apache.cxf.common.util.Base64Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utilities {

    private final static Logger log = LoggerFactory.getLogger(Utilities.class);
    public static final String ALGORITHM_MD5 = "MD5";
    public static final String ALGORITHM_SHA1 = "SHA1";

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
	    //log.error("marshallObject: {}", e1.getMessage());
	    throw new RuntimeException(e1);
	}
    }

    public static String marshallDecodificaPDFResponseType(RecuperaDatiDaPDFResponseType value) {

	StringWriter stringWriter = new StringWriter();
	try {
	    ObjectFactory factory = new ObjectFactory();
	    JAXBElement<RecuperaDatiDaPDFResponseType> scheda = factory.createRecuperaDatiDaPDFResponse(value);
	    JAXBContext jaxbContext = JAXBContext.newInstance(value.getClass());
	    Marshaller marshaller = jaxbContext.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
	    marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    marshaller.marshal(scheda, stringWriter);
	    return stringWriter.toString();
	} catch (Exception e1) {
	    //log.error("marshallObject: {}", e1.getMessage());
	    throw new RuntimeException(e1);
	}
    }

    public static Object unMarshallString(String xml, Class<?> clazz) {

	try {
	    JAXBContext jc = JAXBContext.newInstance(clazz);
	    Unmarshaller u = jc.createUnmarshaller();
	    Object response = u.unmarshal(new ByteArrayInputStream(xml.getBytes("UTF-8")));
	    return response;
	} catch (Exception e1) {
	    // log.error("unMarshallString: {}", e1.getMessage());
	    throw new RuntimeException(e1);
	}
    }

    public static XMLGregorianCalendar getXMLGregorianCalendar(String date, String pattern) throws ParseException {

	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	Date d = sdf.parse(date);
	GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
	c.setTime(d);
	XMLGregorianCalendar calendar;
	try {
	    calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
	    return calendar;
	} catch (DatatypeConfigurationException e) {
	    throw new RuntimeException("Non è stato possibile costruire un oggetto XMLGregorianCalendar a causa di:" + e.getMessage(), e);
	}
    }

    public static XMLGregorianCalendar getXMLGregorianCalendar(GregorianCalendar c) {

	XMLGregorianCalendar calendar;
	try {
	    calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
	    return calendar;
	} catch (DatatypeConfigurationException e) {
	    throw new RuntimeException("Non è stato possibile costruire un oggetto XMLGregorianCalendar a causa di:" + e.getMessage(), e);
	}
    }

    public static String getDate(XMLGregorianCalendar cal, String pattern) {

	GregorianCalendar c = cal.toGregorianCalendar();
	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	return sdf.format(c.getTime());
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

    /**
     * codifica una stringa secondo un algoritmo predefinito
     * 
     * @param plainText
     *            la stringa da codificare
     * @param algorithmType
     *            il tipo di algoritmo da utilizzare MD5, SHA1
     * @param isUpperCase
     *            false = ritorna la stringa composta da lettere in minuscolo <br />
     *            true = ritorna la stringa composta da lettere in maiuscolo
     * @return la stringa codificata
     * @throws NoSuchAlgorithmException
     */
    public static String getHashText(String plainText, String algorithmType, boolean isUpperCase) {

	MessageDigest algorithm = null;
	try {
	    algorithm = MessageDigest.getInstance(algorithmType);
	} catch (NoSuchAlgorithmException e) {
	    throw new RuntimeException(e.getMessage(), e.getCause());
	}
	algorithm.update(plainText.getBytes());
	byte[] digest = algorithm.digest();
	StringBuffer hexString = new StringBuffer();
	for (int i = 0; i < digest.length; i++) {
	    String hex = Integer.toHexString(0xFF & digest[i]);
	    if (hex.length() == 1) {
		hexString.append('0');
	    }
	    hexString.append(hex);
	}
	if (isUpperCase) {
	    return hexString.toString().toUpperCase();
	}
	return hexString.toString();
    }

    public static String base64Encode(byte[] toEncode) {

	return Base64Utility.encode(toEncode);
    }

    public static byte[] base64Decode(String toDecode) {

	byte[] bytes = null;
	try {
	    bytes = Base64Utility.decode(toDecode);
	} catch (Base64Exception e) {
	    log.error("base64Decode - errore nella decodifica Base64");
	}
	return bytes;
    }
}
