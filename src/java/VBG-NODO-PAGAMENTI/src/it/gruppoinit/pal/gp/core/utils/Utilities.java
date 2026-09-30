package it.gruppoinit.pal.gp.core.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

public class Utilities {

    private static final Logger log = LoggerFactory.getLogger(Utilities.class);
    public static final String DATE_WITH_TIME_FORMAT_PATTERN = "dd/MM/yyyy - HH:mm:ss";
    public static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String JSON_INTERCHANGE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final String RFC3339_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";

    /**
     * metodo per il recupero dell'etichetta associata alla chiave passata come argomento.
     * 
     * @see ApplicationContext#getMessage(String, Object[], java.util.Locale)
     * @param context
     * @param chiave
     * @param args
     *            lista di valori da sostituire nel caso in cul l'etichetta associata alla chiave presenti dei
     *            segnaposto es.{0}
     * @return la risoluzione del messaggio oppure una stringa del tipo "???chiave???" se non è stato possibile
     *         recuperare il messaggio dalla chiave
     * @throws RuntimeException
     *             se il contesto è nullo
     */
    public static String getMessageFromBundle(ApplicationContext context, String chiave, Object[] args) {

	if (context == null) {
	    throw new RuntimeException("Il parametro contesto non può essere nullo");
	}
	String message = "";
	try {
	    message = context.getMessage(chiave, args, LocaleContextHolder.getLocale());
	} catch (NoSuchMessageException e) {
	    log.error(e.getMessage());
	    message = "???" + chiave + "???";
	}
	return message;
    }

    /**
     * Converte un oggetto XmlGregorianCalendar in un oggetto Date
     * 
     * @param c
     * @return
     */
    public static Date getDate(XMLGregorianCalendar c) {

	java.util.Date dt = null;
	if (c != null) {
	    try {
		Calendar _ct = c.toGregorianCalendar();
		dt = _ct.getTime();
	    } catch (Exception e) {
		throw new RuntimeException("Non è stato possibile costruire un oggetto Date a causa di:" + e.getMessage(), e);
	    }
	}
	return dt;
    }

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

    public static String trimToLength(String input, int maxlength) {

	if (input != null && input.length() > maxlength) {
	    input = input.substring(0, maxlength);
	}
	return input;
    }

    /**
     * converte l'oggetto Date in String secondo il formato {@link WebConstants#DATE_FORMAT_PATTERN} o
     * {@link WebConstants#DATE_WITH_TIME_FORMAT_PATTERN} a seconda del valore del parametro formatDateAndTime. Nel caso
     * di oggetto nullo o errore di parsing ritorna stringa vuota.
     * 
     * @param d
     * @param formatDateAndTime
     * @return
     */
    public static String formatDate(Date d, boolean formatDateAndTime) {

	String _d = "";
	if (d != null) {
	    try {
		String pattern = formatDateAndTime ? DATE_WITH_TIME_FORMAT_PATTERN : DATE_FORMAT_PATTERN;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		_d = sdf.format(d);
	    } catch (Exception e) {
		log.error("formatDate: {}", e.getMessage());
	    }
	}
	return _d;
    }

    public static String formatDateWithPattern(Date d, String pattern) {

	String _d = "";
	if (d != null) {
	    try {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		_d = sdf.format(d);
	    } catch (Exception e) {
		log.error("formatDate: " + e.getMessage(), e);
	    }
	}
	return _d;
    }

    public static String formatDateWithJsonInterchange(Date d) {

	String _d = "";
	if (d != null) {
	    try {
		SimpleDateFormat sdf = new SimpleDateFormat(JSON_INTERCHANGE_DATE_FORMAT);
		_d = sdf.format(d);
	    } catch (Exception e) {
		log.error("formatDate: " + e.getMessage(), e);
		throw new RuntimeException(e);
	    }
	}
	return _d;
    }

    public static String getDataRfc3339(Date data) {

	String dataOut = "";
	if (data != null) {
	    try { //2024-10-30T18:46:13.8+01:00
		SimpleDateFormat sdf = new SimpleDateFormat(RFC3339_DATE_FORMAT);
		dataOut = sdf.format(data);
	    } catch (Exception e) {
		log.error("formatDate: " + e.getMessage(), e);
		throw new RuntimeException(e);
	    }
	}
	log.debug("getDataRfc3339: {}, {}", data, dataOut);
	return dataOut;
    }

    public static String decodeBase64Binary(byte[] base64String) {

	if (base64String != null) {
	    return decodeBase64Binary(new String(base64String));
	} else {
	    return null;
	}
    }

    public static String decodeBase64Binary(String base64String) {

	if (StringUtils.isNotBlank(base64String)) {
	    return new String(DatatypeConverter.parseBase64Binary(base64String));
	} else {
	    return null;
	}
    }

    /**
     * metodo per creare un oggetto GregorianCalendar partendo da una stringa
     * 
     * @param date
     *            valore della data
     * @param format
     *            formato della data (se nullo è considerato il formato {@link WebConstants#DATE_FORMAT_PATTERN})
     * @return oggetto GregorianCalendar che rappresenta la data passata come stringa
     * 
     */
    public static Date getDate(String date, String format) {

	SimpleDateFormat sdf = null;
	try {
	    sdf = new SimpleDateFormat(format);
	    if (StringUtils.isNotBlank(StringUtils.defaultString(date))) {
		return sdf.parse(date);
	    }
	} catch (ParseException e) {
	    log.error("getDate(): {}", e.getMessage());
	}
	return null;
    }

    /**
     * Controlla se la string passata rappresenta un numero intero (positivo o negativo): nel controllo la stringa viene
     * ripulita degli spazi a destra e sinistra (TRIM)
     * 
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {

	if (StringUtils.isBlank(StringUtils.defaultString(str).trim())) {
	    return false;
	}
	return StringUtils.defaultString(str).trim().matches("^-?(\\d)+$");
    }

    /**
     * Verifica se una stringa rappresenta un numero. Il separatore decimale preso in considerazione viene passato come
     * argomento
     * 
     * @param value
     * @param decimalSeparator
     * @return
     */
    public static boolean isNumeric(String value, String decimalSeparator) {

	if (StringUtils.isBlank(StringUtils.defaultString(value).trim())) {
	    return false;
	}
	return StringUtils.defaultString(value).trim().matches("^-?(\\d)+[" + decimalSeparator + "]*(\\d)*$");
    }

    /**
     * Metodo di utilità per aggiungere giorni ad una data.
     * 
     * @see Calendar#add(int, int)
     * @param dateToIncrease
     *            la data da aggiornare
     * @param amount
     *            il numero di giorni
     * @return
     */
    public static Date addDays(Date dateToIncrease, int amount) {

	Calendar c = Calendar.getInstance();
	c.setTime(dateToIncrease);
	c.add(Calendar.DATE, amount);
	return c.getTime();
    }

    public static final String JAXB_ENCODING_UTF_8 = "UTF-8";

    public static <T> String marshalJsonObject(Object object, Class<T> clazz, boolean includeRoot, String encoding) throws JAXBException {

	Marshaller marshaller = JAXBContextFactory.createContext(new Class[] { clazz }, null).createMarshaller();
	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
	marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, includeRoot);
	marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
	StringWriter stringWriter = new StringWriter();
	marshaller.marshal(object, stringWriter);
	return stringWriter.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> T unMarshallJsonString(String jsonInput, Class<T> clazz, String encoding, boolean includeRoot)
	    throws JAXBException, UnsupportedEncodingException {

	Unmarshaller unmarshaller = JAXBContextFactory.createContext(new Class[] { clazz }, null).createUnmarshaller();
	unmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, includeRoot);
	unmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	return (T) unmarshaller.unmarshal(new ByteArrayInputStream(jsonInput.getBytes(encoding)));
    }

    @SuppressWarnings("unchecked")
    public static <T> T unMarshallJsonStream(InputStream is, Class<T> clazz, boolean includeRoot) throws JAXBException {

	Unmarshaller unmarshaller = JAXBContextFactory.createContext(new Class[] { clazz }, null).createUnmarshaller();
	unmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, includeRoot);
	unmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	return unmarshaller.unmarshal(new StreamSource(is), clazz).getValue();
    }

    private static final Pattern PATTERN_EMAIL_VALIDATION = Pattern
	    .compile("^$|^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-]{2,})+\\.)+([a-zA-Z0-9]{2,})+$");

    public static boolean validaIndirizzoMail(String email) {

	if (StringUtils.isBlank(email)) {
	    return false;
	}
	return PATTERN_EMAIL_VALIDATION.matcher(email).matches();
    }

    public static Date impostaOrarioAData(Date inputDate, int hours, int minutes, int seconds, int calendarAMPM) {

	Calendar t = Calendar.getInstance();
	t.setTime(inputDate);
	t.set(Calendar.HOUR, hours);
	t.set(Calendar.MINUTE, minutes);
	t.set(Calendar.SECOND, seconds);
	t.set(Calendar.AM_PM, calendarAMPM);
	return t.getTime();
    }

    public static Date dateWithoutTime(Date data) {

	if (data == null) {
	    return null;
	}
	return impostaOrarioAData(data, 0, 0, 0, Calendar.AM);
    }

    /**
     * Se timestamp torna Calendar nullo
     * 
     * @param timestamp
     * @return
     */
    public static XMLGregorianCalendar getXMLGregorianCalendar(Long timestamp) {

	GregorianCalendar gc = new GregorianCalendar();
	gc.setTimeInMillis(timestamp);
	// to XML Gregorian Calendar
	try {
	    return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
	} catch (DatatypeConfigurationException e) {
	    log.error("getXMLGregorianCalendar():" + e.getMessage(), e);
	}
	return null;
    }
    
    public static String formatUTCJsonDate(Date date) {
    	if(date == null) {
    		return null;
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    	return sdf.format(date);
    }

    /**
     * Esegue un compare tra due date la comparazione non tiene conto di ore minuti secondi
     * 
     * @param first
     *            la prima data. può essere nulla
     * @param second
     *            la seconda data. può essere nulla
     * @return
     *         <ul>
     *         <li>0: se le date sono identiche</li>
     *         <li>-1: se first è precedente a second</li>
     *         <li>1: se first è successiva a second</li>
     *         </ul>
     *         se entrambe le date sono nulle allora torna 0<br />
     *         se first è nulla e second è non nulla allora torna -1<br />
     *         se first è non nulla e second è nulla allora torna 1<br />
     */
    public static int compareDates(Date first, Date second) {

	if (first == null && second == null) {
	    return 0;
	}
	if (first == null) {
	    return -1;
	}
	if (second == null) {
	    return 1;
	}
	Calendar earlierCal = Calendar.getInstance();
	earlierCal.setTime(first);
	Calendar laterCal = Calendar.getInstance();
	laterCal.setTime(second);
	Integer earlierYear = earlierCal.get(Calendar.YEAR);
	Integer laterYear = laterCal.get(Calendar.YEAR);
	if (earlierYear.intValue() != laterYear.intValue()) {
	    return earlierYear.compareTo(laterYear);
	}
	Integer earlierMonth = earlierCal.get(Calendar.MONTH);
	Integer laterMonth = laterCal.get(Calendar.MONTH);
	if (earlierMonth.intValue() != laterMonth.intValue()) {
	    return earlierMonth.compareTo(laterMonth);
	}
	Integer earlierDate = earlierCal.get(Calendar.DATE);
	Integer laterDate = laterCal.get(Calendar.DATE);
	return earlierDate.compareTo(laterDate);
    }    
}
