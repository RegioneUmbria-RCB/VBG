package it.gruppoinit.utilities;

import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.gruppoinit.constants.WebConstants;
import it.gruppoinit.service.DeployProperties;
import it.init.sigepro.rte.types.SportelloType;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom2.Attribute;
import org.jdom2.CDATA;
import org.jdom2.Comment;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

public class Utilities {

    private static final Logger log = LoggerFactory.getLogger(Utilities.class);
    private static Boolean flagIsBackoffice = null;
    public static final String ALGORITHM_MD5 = "MD5";
    public static final String ALGORITHM_SHA1 = "SHA1";
    public static String ROOT = "root";
    public static String SEPARATORE_VALORE_VALORE_DECODIFICATO = "#@#";

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

    /**
     * @see Set#contains(Object)
     */
    public static boolean contains(@SuppressWarnings("rawtypes") Set coll, Object o) {

	if (coll == null) {
	    return false;
	}
	if (o == null) {
	    return false;
	}
	return coll.contains(o);
    }

    private static boolean isConsonant(String carattere) {

	boolean answer = true;
	// Tolto questo controllo "|| carattere.equalsIgnoreCase("y")" la y non deve essere considerata una vocale.
	if (carattere.equalsIgnoreCase("a") || carattere.equalsIgnoreCase("e") || carattere.equalsIgnoreCase("i") || carattere.equalsIgnoreCase("o")
		|| carattere.equalsIgnoreCase("u") || carattere.equalsIgnoreCase("à") || carattere.equalsIgnoreCase("è")
		|| carattere.equalsIgnoreCase("é") || carattere.equalsIgnoreCase("ì") || carattere.equalsIgnoreCase("ò")
		|| carattere.equalsIgnoreCase("ù")) {
	    answer = false;
	}
	return answer;
    }

    public static String formatMessage(String message, Object... arguments) {

	String result = MessageFormat.format(message, arguments);
	return result;
    }

    public static String formatMessage(String message, String... arguments) {

	String result = MessageFormat.format(message, (Object[]) arguments);
	return result;
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

    /**
     * Metodo di utilità per rimuovere giorni ad una data.
     * 
     * @see Calendar#add(int, int)
     * @param dateToIncrease
     *            la data da aggiornare
     * @param amount
     *            il numero di giorni
     * @return
     */
    public static Date removeDays(Date dateToDecrement, int amount) {

	amount = amount * (-1);
	GregorianCalendar c = new GregorianCalendar();
	c.setTime(dateToDecrement);
	c.roll(Calendar.DATE, amount);
	return c.getTime();
    }

    /**
     * Metodo di utilità per rimuovere giorni ad una data.
     * 
     * @see Calendar#add(int, int)
     * @param dateToIncrease
     *            la data da aggiornare
     * @param amount
     *            il numero di giorni
     * @return
     */
    public static Date addAndremoveDays(Date dateToDecrement, int amount, boolean isAdd) {

	if (!isAdd) {
	    amount = amount * (-1);
	}
	Calendar c = Calendar.getInstance();
	c.setTime(dateToDecrement);
	c.add(Calendar.DATE, amount);
	return c.getTime();
    }

    /**
     * Metodo di utilità per aggiungere giorni ad una data.
     * 
     * @see Calendar#add(int, int)
     * 
     * @param dateToIncrease
     *            la data da aggiornare
     * @param amount
     *            il numero di mesi
     * @return
     */
    public static Date addMonth(Date dateToIncrease, int amount) {

	Calendar c = Calendar.getInstance();
	c.setTime(dateToIncrease);
	c.add(Calendar.MONTH, amount);
	return c.getTime();
    }

    /**
     * Metodo di utilità per aggiungere giorni ad una data.
     * 
     * @see Calendar#add(int, int)
     * @param dateToIncrease
     *            la data da aggiornare
     * @param amount
     *            il numero di anni
     * @return
     */
    public static Date addYear(Date dateToIncrease, int amount) {

	Calendar c = Calendar.getInstance();
	c.setTime(dateToIncrease);
	c.add(Calendar.YEAR, amount);
	return c.getTime();
    }

    /**
     * Calcola la differenza di giorni tra due date
     * 
     * @param earlierCal
     * @param laterCal
     * @return
     */
    public static int calculateDifferenceInDays(Calendar earlierCal, Calendar laterCal) {

	int tempDifference = 0;
	int difference = 0;
	if (earlierCal == null || laterCal == null) {
	    return -1;
	}
	Calendar earlier = Calendar.getInstance();
	Calendar later = Calendar.getInstance();
	earlier.setTime(earlierCal.getTime());
	later.setTime(laterCal.getTime());
	while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR)) {
	    tempDifference = 365 * (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR));
	    difference += tempDifference;
	    earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
	}
	if (earlier.get(Calendar.DAY_OF_YEAR) != later.get(Calendar.DAY_OF_YEAR)) {
	    tempDifference = later.get(Calendar.DAY_OF_YEAR) - earlier.get(Calendar.DAY_OF_YEAR);
	    difference += tempDifference;
	    earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
	}
	return difference;
    }

    /**
     * Calculate difference in days between two date
     * 
     */
    public static int calculateDifferenceInDays(Date a, Date b) {

	Calendar earlier = Calendar.getInstance();
	Calendar later = Calendar.getInstance();
	if (a.compareTo(b) < 0) {
	    earlier.setTime(a);
	    later.setTime(b);
	} else {
	    earlier.setTime(b);
	    later.setTime(a);
	}
	return calculateDifferenceInDays(earlier, later);
    }

    /**
     * Ritorna l'ora di sistema secondo la il pattern <b>hh:mm<b>
     * 
     * @return
     */
    public static String getOrariosistema() {

	Calendar calendar = Calendar.getInstance();
	String ora = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
	String minuti = String.valueOf(calendar.get(Calendar.MINUTE));
	// padding ora e minuti
	if (minuti.length() == 1) {
	    minuti = "0" + minuti;
	}
	if (ora.length() == 1) {
	    ora = "0" + ora;
	}
	return ora + ":" + minuti;
    }

    public static String getOrariosistema(int addMin) {

	Calendar calendar = Calendar.getInstance();
	String ora = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
	int min = calendar.get(Calendar.MINUTE) + addMin;
	String minuti = String.valueOf(min);
	// padding ora e minuti
	if (minuti.length() == 1) {
	    minuti = "0" + minuti;
	}
	if (ora.length() == 1) {
	    ora = "0" + ora;
	}
	return ora + ":" + minuti;
    }

    public static String getOrario(Date date) {

	String _date = formatDate(date, true);
	GregorianCalendar calendar = getDate(_date, WebConstants.DATE_WITH_TIME_FORMAT_PATTERN);
	String ora = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
	String minuti = String.valueOf(calendar.get(Calendar.MINUTE));
	// padding ora e minuti
	if (minuti.length() == 1) {
	    minuti = "0" + minuti;
	}
	if (ora.length() == 1) {
	    ora = "0" + ora;
	}
	return ora + ":" + minuti;
    }

    public static List<String> split(String campo, String regex) {

	List<String> list = new ArrayList<String>();
	String[] field = campo.split(regex);
	for (int i = 0; i < field.length; i++) {
	    list.add(i, field[i]);
	}
	return list;
    }

    public static boolean endsWith(String word, String suffix) {

	return StringUtils.defaultIfEmpty(word, "").toLowerCase().endsWith(StringUtils.defaultIfEmpty(suffix, "XXX").toLowerCase());
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
		String pattern = formatDateAndTime ? WebConstants.DATE_WITH_TIME_FORMAT_PATTERN : WebConstants.DATE_FORMAT_PATTERN;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		_d = sdf.format(d);
	    } catch (Exception e) {
		log.error("formatDate: {}", e.getMessage());
	    }
	}
	return _d;
    }

    public static String formatDate(Date d, String formatDateAndTimePattern) {

	String _d = "";
	if (d != null) {
	    try {
		SimpleDateFormat sdf = new SimpleDateFormat(formatDateAndTimePattern);
		_d = sdf.format(d);
	    } catch (Exception e) {
		log.error("formatDate: {}", e.getMessage());
	    }
	}
	return _d;
    }

    /**
     * converte l'oggetto String (che rappresenta una data) in Date secondo il formato
     * {@link WebConstants#DATE_FORMAT_PATTERN} o {@link WebConstants#DATE_WITH_TIME_FORMAT_PATTERN} a seconda del
     * valore del parametro formatDateAndTime. Nel caso di oggetto nullo o errore di parsing ritorna null.
     * 
     * @param d
     *            la stringa che rappresenta una data secondo i formati {@link WebConstants#DATE_FORMAT_PATTERN} o
     *            {@link WebConstants#DATE_WITH_TIME_FORMAT_PATTERN}
     * @param formatDateAndTime
     *            true se la stringa è del tipo {@link WebConstants#DATE_WITH_TIME_FORMAT_PATTERN}
     * @return
     */
    public static Date parseDateString(String d, boolean formatDateAndTime) {

	Date _d = null;
	if (d != null) {
	    try {
		String pattern = formatDateAndTime ? WebConstants.DATE_WITH_TIME_FORMAT_PATTERN : WebConstants.DATE_FORMAT_PATTERN;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		_d = sdf.parse(d);
	    } catch (Exception e) {
		log.error("formatDate: {}", e.getMessage());
	    }
	}
	return _d;
    }

    public static Date parseDateString(String d, String formatDate) {

	Date _d = null;
	if (d != null) {
	    try {
		SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
		_d = sdf.parse(d);
	    } catch (Exception e) {
		log.error("formatDate: {}", e.getMessage());
	    }
	}
	return _d;
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
    public static GregorianCalendar getDate(String date, String format) {

	SimpleDateFormat sdf = null;
	GregorianCalendar gd = null;
	try {
	    if (StringUtils.isNotBlank(format)) {
		sdf = new SimpleDateFormat(format);
	    } else {
		sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    }
	    if (StringUtils.isNotBlank(StringUtils.defaultString(date))) {
		gd = new GregorianCalendar();
		Date d = sdf.parse(date);
		gd.setTime(d);
	    }
	} catch (ParseException e) {
	    log.error("getDate(): {}", e.getMessage());
	}
	return gd;
    }

    public static Date addTime(Date date, String time) {

	String _date = Utilities.formatDate(date, false);
	//"dd/MM/yyyy - HH:mm";
	date = Utilities.parseDateString(_date + " - " + time, true);
	//	List<String> hourAndMinute = split(time, ":");
	//	Calendar c = Calendar.getInstance();
	//	c.setTime(date);
	//	c.add(Calendar.HOUR, Integer.parseInt(hourAndMinute.get(0).trim()));
	//	c.add(Calendar.MINUTE, Integer.parseInt(hourAndMinute.get(1).trim()));
	return date;
    }

    public static String getToday(boolean formatDateAndTime) {

	return formatDate(Calendar.getInstance().getTime(), formatDateAndTime);
    }

    /**
     * Torna la data di sistema secondo il pattern specificato {@link SimpleDateFormat}. In caso di errore di parsing
     * torna stringa vuota
     * 
     * @param dateFormatPattern
     * @return
     */
    public static String getToday(String dateFormatPattern) {

	String _d = "";
	try {
	    SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern);
	    _d = sdf.format(Calendar.getInstance().getTime());
	} catch (Exception e) {
	    log.error("formatDate: {}", e.getMessage());
	}
	return _d;
    }

    /**
     * Torna un oggetto XMLGregorianCalendar impostato con la data del Giorno
     * 
     * @return
     */
    public static XMLGregorianCalendar getToday() {

	XMLGregorianCalendar calendar;
	try {
	    calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
	    calendar.setDay(Calendar.getInstance().get(Calendar.DATE));
	    // DA RICORDARSI: il Mese per XmlGregorianCalendar parte da 1 e non da 0 come GregorianCalendar
	    calendar.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
	    calendar.setYear(Calendar.getInstance().get(Calendar.YEAR));
	    return calendar;
	} catch (DatatypeConfigurationException e) {
	    throw new RuntimeException("Non è stato possibile costruire un oggetto XMLGregorianCalendar a causa di:" + e.getMessage(), e);
	}
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

    /**
     * Converte un oggetto XmlGregorianCalendar in un oggetto Date
     * 
     * @param c
     * @return
     */
    public static Date getDate(XMLGregorianCalendar c) {

	java.util.Date dt;
	try {
	    Calendar _ct = c.toGregorianCalendar();
	    dt = _ct.getTime();
	    return dt;
	} catch (Exception e) {
	    throw new RuntimeException("Non è stato possibile costruire un oggetto Date a causa di:" + e.getMessage(), e);
	}
    }

    public static Date getTime(Calendar c) {

	java.util.Date dt;
	try {
	    dt = c.getTime();
	    return dt;
	} catch (Exception e) {
	    throw new RuntimeException("Non è stato possibile costruire un oggetto Date a causa di:" + e.getMessage(), e);
	}
    }

    /**
     * <pre>
     * Il metodo a partire da un XML Gragorian calendar ritorna un oggetto Date andando a considerare i campi 
     * <ol>
     * <li>YEAR</li>
     * <li>MONTH</li>
     * <li>DAY</li>
     * </ol>
     * &#64;param datanascita
     * &#64;return
     * 
     * </pre>
     */
    public static Date createDate(XMLGregorianCalendar xmlDate) {

	Calendar c = Calendar.getInstance();
	Calendar date = xmlDate.toGregorianCalendar();
	Integer YEAR = date.get(Calendar.YEAR);
	Integer MONTH = date.get(Calendar.MONTH);
	Integer DATE = date.get(Calendar.DAY_OF_MONTH);
	c.set(YEAR, MONTH, DATE, 0, 0, 0);
	java.util.Date dt;
	try {
	    dt = c.getTime();
	    return dt;
	} catch (Exception e) {
	    throw new RuntimeException("Non è stato possibile costruire un oggetto Date a causa di:" + e.getMessage(), e);
	}
    }

    /**
     * <pre>
     * Il metodo ritorna l'annotation associata al metodo passato
     * 
     * &#64;param <T>
     * &#64;param clazz
     *            : Classe dove si trova l'annotation che si vuole ricavare
     * &#64;param clazzAnnotation
     *            : Classe dell'annotation che vogliamo ricavare
     * &#64;param method
     *            : medoto di cui vogliamo ricavare la specifica annotation
     * &#64;return    :ritorna un istanza della classe <b>clazzAnnotation</b>
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public static <T> T getMethohAnnotation(Class clazz, Class clazzAnnotation, String method) {

	T annotation = null;
	try {
	    annotation = (T) AnnotationUtils.getAnnotation(clazz.getMethod(method), clazzAnnotation);
	} catch (SecurityException e) {
	    e.printStackTrace();
	} catch (NoSuchMethodException e) {
	    e.printStackTrace();
	}
	return annotation;
    }

    /**
     * Esegue un compare tra due date la comparazione non tiene conto di ore minuti secondi
     * 
     * @param earlier
     *            la prima data. può essere nulla
     * @param later
     *            la seconda data. può essere nulla
     * @return
     *         <ul>
     *         <li>0: se le date sono identiche</li>
     *         <li>&lt;0: se earlier è precedente a later</li>
     *         <li>&gt;0: se earlier è successiva a later</li>
     *         </ul>
     *         se entrambe le date sono nulle allora torna 0<br />
     *         se earlier è nulla e later è non nulla allora torna -1<br />
     *         se earlier è non nulla e later è nulla allora torna 1<br />
     */
    public static int compareDates(Date earlier, Date later) {

	if (earlier == null && later == null) {
	    return 0;
	}
	if (earlier == null && later != null) {
	    return -1;
	}
	if (earlier != null && later == null) {
	    return 1;
	}
	Calendar earlierCal = Calendar.getInstance();
	earlierCal.setTime(earlier);
	Calendar laterCal = Calendar.getInstance();
	laterCal.setTime(later);
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

    /**
     * Esegue un compare tra due date la comparazione non tiene conto di ore minuti secondi
     * 
     * @param earlier
     *            la prima data. può essere nulla
     * @param later
     *            la seconda data. può essere nulla
     * @return
     *         <ul>
     *         <li>0: se le date sono identiche</li>
     *         <li>&lt;0: se earlier è precedente a later</li>
     *         <li>&gt;0: se earlier è successiva a later</li>
     *         </ul>
     *         se entrambe le date sono nulle allora torna 0<br />
     *         se earlier è nulla e later è non nulla allora torna -1<br />
     *         se earlier è non nulla e later è nulla allora torna 1<br />
     */
    public static int compareDates(Date earlier, GregorianCalendar later) {

	if (earlier == null && later == null) {
	    return 0;
	}
	if (earlier == null && later != null) {
	    return -1;
	}
	if (earlier != null && later == null) {
	    return 1;
	}
	Calendar earlierCal = Calendar.getInstance();
	earlierCal.setTime(earlier);
	Integer earlierYear = earlierCal.get(Calendar.YEAR);
	Integer laterYear = later.get(Calendar.YEAR);
	if (earlierYear.intValue() != laterYear.intValue()) {
	    return earlierYear.compareTo(laterYear);
	}
	Integer earlierMonth = earlierCal.get(Calendar.MONTH);
	Integer laterMonth = later.get(Calendar.MONTH);
	if (earlierMonth.intValue() != laterMonth.intValue()) {
	    return earlierMonth.compareTo(laterMonth);
	}
	Integer earlierDate = earlierCal.get(Calendar.DATE);
	Integer laterDate = later.get(Calendar.DATE);
	return earlierDate.compareTo(laterDate);
    }

    /**
     * <pre>
     * Il metodo fa il confronto tra le due date.
     * 
     * &#64;param date1
     * &#64;param date2
     * &#64;return ritorna true se sono uguali, false se sono diverse, IllegalArgumentException se la date1 è null
     * </pre>
     */
    public static boolean isEqualsDates(Date date1, Date date2) {

	if (date1 != null) {
	    if (date1.compareTo(date2) == 0) {
		return true;
	    }
	} else {
	    throw new IllegalArgumentException("Il campo data 1 passato non può essere null");
	}
	return false;
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

    public static Object unMarshallString(String xml, Class<?> clazz) {

	return unMarshallString(xml, clazz, "UTF-8");
    }

    public static Object unMarshallString(String xml, Class<?> clazz, String encoding) {

	try {
	    JAXBContext jc = JAXBContext.newInstance(clazz);
	    Unmarshaller u = jc.createUnmarshaller();
	    Object response = u.unmarshal(new ByteArrayInputStream(xml.getBytes(encoding)));
	    return response;
	} catch (Exception e1) {
	    log.error("unMarshallString: {}", e1.getMessage());
	    throw new RuntimeException(e1);
	}
    }

    public static Object unMarshallFromStream(InputStream is, Class<?> clazz) {

	try {
	    JAXBContext jc = JAXBContext.newInstance(clazz);
	    Unmarshaller u = jc.createUnmarshaller();
	    Object response = u.unmarshal(new BufferedInputStream(is));
	    return response;
	} catch (Exception e1) {
	    log.error("unMarshallFromStream: {}", e1.getMessage());
	    throw new RuntimeException(e1);
	}
    }

    public static Object unMarshallFromByte(byte[] b, Class<?> clazz) {

	try {
	    JAXBContext jc = JAXBContext.newInstance(clazz);
	    Unmarshaller u = jc.createUnmarshaller();
	    Object response = (RiepilogoPraticaSUAP) u.unmarshal(new ByteArrayInputStream(b));
	    return response;
	} catch (Exception e1) {
	    log.error("unMarshallFromByte: {}", e1.getMessage());
	    throw new RuntimeException(e1);
	}
    }

    public static String normalizzaPath(String path) {

	if (StringUtils.isBlank(path)) {
	    return "";
	}
	String separatoreSistemaOperativo = File.separator;
	String windowsFileSeparator = "\\";
	String linuxFileSeparator = "/";
	String sepDaRipulire = "";
	String operatingSystem = System.getProperty("os.name");
	if (StringUtils.isNotBlank(operatingSystem)) {
	    if (operatingSystem.toLowerCase().contains("linux")) {
		sepDaRipulire = windowsFileSeparator;
	    } else {
		sepDaRipulire = linuxFileSeparator;
	    }
	} else {
	    if (StringUtils.isBlank(sepDaRipulire)) {
		sepDaRipulire = File.separator.equals(linuxFileSeparator) ? windowsFileSeparator : linuxFileSeparator;
	    }
	}
	if (StringUtils.isNotBlank(sepDaRipulire)) {
	    if (path.indexOf(sepDaRipulire) >= 0) {
		log.debug("normalizzaPath: percorso da normalizzare [{}]", path);
		path = path.replace(sepDaRipulire, separatoreSistemaOperativo);
		log.debug("normalizzaPath: percorso normalizzato a [{}]", path);
	    }
	}
	return path;
    }

    public static Object copyObjectProperties(Object source, Object target) {

	Field[] f = source.getClass().getDeclaredFields();
	for (int i = 0; i < f.length; i++) {
	    try {
		Method set = target.getClass().getMethod("set" + StringUtils.capitalize(f[i].getName()), f[i].getType());
		Method get = source.getClass().getMethod("get" + StringUtils.capitalize(f[i].getName()));
		try {
		    set.invoke(target, get.invoke(source, new Object[0]));
		} catch (IllegalArgumentException e) {
		    e.printStackTrace();
		} catch (IllegalAccessException e) {
		    e.printStackTrace();
		} catch (InvocationTargetException e) {
		    e.printStackTrace();
		}
	    } catch (SecurityException e) {
		e.printStackTrace();
	    } catch (NoSuchMethodException e) {
		log.error("E' stato invocato un metodo inesistente");
	    }
	}
	return target;
    }

    /**
     * Se toDelete indica un file, viene cancellato il file. Se toDelete indica una directory viene cancellato tutto il
     * contenuto della directory (invocando ricorsivamente questo stesso metodo) e poi viene cancellata la directory.
     * 
     * @param toDelete
     * @return true se è stato possibile cancellare <code>toDelete</code>, altrimenti false.
     */
    public static boolean deleteFolder(File toDelete) {

	boolean allDeleted = true;
	boolean thisDeleted = true;
	if (toDelete.isDirectory()) {
	    File[] contents = toDelete.listFiles();
	    for (File file : contents) {
		allDeleted &= deleteFolder(file);
	    }
	}
	thisDeleted = toDelete.delete();
	if (thisDeleted) {
	    if (log.isDebugEnabled())
		log.debug("Cancellato file/directory: " + toDelete.getAbsolutePath());
	} else {
	    log.warn("Impossibile cancellare il file/directory: " + toDelete.getAbsolutePath());
	}
	allDeleted &= thisDeleted;
	return allDeleted;
    }

    /**
     * Restituisce true se i bytes letti dal flusso corrispondono ad un file in formato ZIP.
     * 
     * @param checkFileInputStream
     *            , flusso in lettura sui dati di un file di cui verificare il formato.
     * @return true se il file è un file ZIP, false se non è un file ZIP.
     * @throws IOException
     */
    public static boolean isZipFile(InputStream checkFileInputStream) throws IOException {

	boolean isZip = false;
	if (checkFileInputStream != null) {
	    //try {
	    BufferedInputStream bis = new BufferedInputStream(checkFileInputStream);
	    if (bis.markSupported()) {
		bis.mark(10);
		byte[] buf = new byte[4];
		if (bis.read(buf) == 4) {
		    byte[] checkBytes = new byte[] { 0x50, 0x4B, 0x03, 0x04 };
		    for (int i = 0; i < buf.length; i++) {
			isZip = buf[i] == checkBytes[i];
			if (!isZip) {
			    break;
			}
		    }
		}
		bis.reset();
	    }
	    /*
	    } catch (IOException e) {
	    log.error("Errore I/O durante verifica del formato zip:", e);
	    } 
	    */
	}
	return isZip;
    }

    /**
     * Restituisce true se i bytes letti dal flusso corrispondono ad un file in formato RAR. Viene verificato che il
     * file inizi con i seguenti 7 bytes: 52 61 72 21 1A 07 00
     * 
     * @param checkFileInputStream
     *            , flusso in lettura sui dati di un file di cui verificare il formato.
     * @return true se il file è un file RAR, false se non è un file RAR.
     * @throws IOException
     */
    public static boolean isRarFile(InputStream is) throws IOException {

	boolean isRar = false;
	if (is != null) {
	    BufferedInputStream bis = new BufferedInputStream(is);
	    if (bis.markSupported()) {
		bis.mark(10);
		byte[] buf = new byte[7];
		if (bis.read(buf) == 7) {
		    byte[] checkBytes = new byte[] { 0x52, 0x61, 0x72, 0x21, 0x1A, 0x07, 0x00 };
		    for (int i = 0; i < buf.length; i++) {
			isRar = buf[i] == checkBytes[i];
			if (!isRar) {
			    break;
			}
		    }
		}
		bis.reset();
	    }
	}
	return isRar;
    }

    public static File createTmpDir(String... folderName) {

	File tmpDir = getSystemTempDir();
	if (!tmpDir.isDirectory()) {
	    tmpDir.mkdirs();
	}
	for (String f : folderName) {
	    tmpDir = new File(tmpDir, f);
	    if (!tmpDir.isDirectory()) {
		tmpDir.mkdirs();
	    }
	}
	return tmpDir;
    }

    /**
     * Restituisce un oggetto {@link File} che rappresenta la directory temporanea utilizzata dal sistema in uso.
     * L'oggetto restituito punta alla directory indicata dalla proprietà di sistema java.io.tmpdir. Se la proprietà di
     * sistema non è impostata l'oggetto restituito punta alla cartella /temp, sottodirectory della directory corrente
     * (quella in cui è in'esecuzione il processo Java).
     * 
     * @return un oggetto {@link java.io.File} che punta alla directory temporanea di sistema.
     */
    public static File getSystemTempDir() {

	File systemTempDir = null;
	String tempPath = System.getProperty("java.io.tmpdir");
	systemTempDir = new File(tempPath);
	return systemTempDir;
    }

    /**
     * Legge un file zip da <code>zipFileInputStream</code> e lo scompatta nella directory <code>unzipDestDir</code>
     * 
     * @param zipFileInputStream
     *            : flusso in lettura su un file ZIP.
     * @param unzipDestDir
     *            : directory di destinazione in cui scompattare il file ZIP. Se non esiste viene creata.
     * @return restituisce un riferimento alla directory in cui è stato scompattato il pacchetto ZIP.
     * @throws IOException
     *             se il file non è in formato ZIP o se si verifica un qualunque errore di lettura o scrittura dei files
     */
    public static File unzipTo(InputStream zipFileInputStream, File unzipDestDir) throws IOException {

	File retVal = null;
	int ZIP_READ_BUFFER_SIZE = 1024 * 8;
	ZipInputStream zis = new ZipInputStream(zipFileInputStream);
	ZipEntry entry = null;
	BufferedOutputStream bos = null;
	FileOutputStream fos = null;
	byte[] buffer = null;
	while ((entry = zis.getNextEntry()) != null) {
	    File unzipped = new File(unzipDestDir, entry.getName());
	    if (entry.isDirectory()) {
		unzipped.mkdirs();
	    } else {
		File parentFolder = unzipped.getParentFile();
		if (!parentFolder.exists()) {
		    parentFolder.mkdirs();
		}
		unzipped.createNewFile();
		fos = new FileOutputStream(unzipped);
		bos = new BufferedOutputStream(fos);
		buffer = new byte[ZIP_READ_BUFFER_SIZE];
		int read = 0;
		while ((read = zis.read(buffer, 0, ZIP_READ_BUFFER_SIZE)) > -1) {
		    bos.write(buffer, 0, read);
		}
		bos.flush();
		bos.close();
	    }
	}
	retVal = unzipDestDir;
	zis.close();
	//return the temp directory where we unzipped the archive
	return retVal;
    }

    /**
     * Crea un file zip con tutto il contenuto della cartella <code>sourceDir</code> e lo scrive nel flusso
     * <code>writeTo</code>.
     * 
     * @param writeTo
     *            : flusso in scrittura su un file ZIP.
     * @param sourceDir
     *            : directory il cui contenuto viene scritto nell'archivio zip
     * @return void
     * @throws IOException
     *             se si verifica un qualunque errore di lettura o scrittura dei files
     */
    public static void zipTo(File sourceDir, OutputStream writeTo) throws IOException {

	ZipOutputStream zos = new ZipOutputStream(writeTo);
	writeZipEntries(sourceDir, zos, null);
	zos.close();
	//return the temp directory where we unzipped the archive
    }

    private static void writeZipEntries(File entry, ZipOutputStream writeTo, File parentPath) throws IOException {

	int ZIP_READ_BUFFER_SIZE = 1024 * 8;
	int bytesRead = 0;
	byte[] buffer = new byte[ZIP_READ_BUFFER_SIZE];
	if (entry.isFile()) {
	    FileInputStream fis = new FileInputStream(entry);
	    BufferedInputStream bis = new BufferedInputStream(fis);
	    ZipEntry zentry = new ZipEntry(entry.getName());
	    writeTo.putNextEntry(zentry);
	    while ((bytesRead = bis.read(buffer)) > -1) {
		writeTo.write(buffer, 0, bytesRead);
	    }
	    writeTo.closeEntry();
	} else if (entry.isDirectory()) {
	    File[] dirContent = entry.listFiles();
	    for (File file : dirContent) {
		File entryFile = parentPath != null ? new File(parentPath, file.getName()) : new File(file.getName());
		if (file.isFile()) {
		    FileInputStream fis = new FileInputStream(file);
		    BufferedInputStream bis = new BufferedInputStream(fis);
		    ZipEntry zentry = new ZipEntry(entryFile.getPath());
		    writeTo.putNextEntry(zentry);
		    while ((bytesRead = bis.read(buffer)) > -1) {
			writeTo.write(buffer, 0, bytesRead);
		    }
		    writeTo.closeEntry();
		} else {
		    writeZipEntries(file, writeTo, entryFile);
		}
	    }
	}
    }

    public static String getFileExtension(String nomeFile) {

	String extension = "";
	if (StringUtils.isNotBlank(nomeFile)) {
	    if (nomeFile.indexOf(".") > 0) {
		extension = nomeFile.substring(nomeFile.lastIndexOf("."));
		extension = extension.replace(".", "").toUpperCase();
	    }
	}
	return extension;
    }

    /**
     * Read the bytes from the DataHandler
     * 
     * @param dh
     * @return byte[]
     * @throws IOException
     */
    public static byte[] dataHandlerToBytes(DataHandler dh) {

	try {
	    return IOUtils.toByteArray(dh.getInputStream());
	} catch (IOException e) {
	    log.error("dataHandlerToBytes: ", e);
	    throw new RuntimeException(e);
	}
    }

    /**
     * Crea un nuovo datahandler da un file di input
     * 
     * @param dh
     * @return DataHandler
     * @throws IOException
     */
    public static DataHandler fileToDataHandler(File file) {

	try {
	    DataSource ds = new FileDataSource(file);
	    return new DataHandler(ds);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private static final Pattern NON_ASCII_PATTERN = Pattern.compile("[^\\x20-\\x7e]");
    private static final Pattern FILENAME_ALLOWED_CHARS = Pattern.compile("(\\.?[^A-Za-z0-9\\.\\-\\_]+)");
    private static final Pattern INVALID_FILENAME_PATTERN = Pattern.compile("\"|\\||\\*|\\?|:|\\<|\\>|\\\\|/");
    /**
     * String da usare per indicare una stringa vuota senza dover usare "" (che crea un nuovo oggetto stringa)
     */
    public static final String EMPTY_CACHED_FINAL_STRING = "_";

    /**
     * Utilizza {@link #NON_ASCII_PATTERN} per rimuovere i caratteri non ascii da una stringa
     * 
     * @param nomeFile
     * @return
     */
    public static String eliminaCaratteriNonAscii(String nomeFile) {

	String ret = NON_ASCII_PATTERN.matcher(StringUtils.defaultString(nomeFile)).replaceAll(EMPTY_CACHED_FINAL_STRING);
	ret = FILENAME_ALLOWED_CHARS.matcher(StringUtils.defaultString(ret)).replaceAll(EMPTY_CACHED_FINAL_STRING);
	return ret;
    }

    /**
     * Elimina tutti i caratteri non ASCII e anche tutti i caratteri ASCII non validi nel nome di un file. I caratter
     * eliminati per ora sono sempre solo quelli non validi in ambienti Windows, ossia: "|*?:<>\/
     * 
     * @param nomeFile
     * @return
     */
    public static String cleanFilename(String nomeFile) {

	nomeFile = eliminaCaratteriNonAscii(nomeFile);
	return INVALID_FILENAME_PATTERN.matcher(nomeFile).replaceAll(EMPTY_CACHED_FINAL_STRING);
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

    public static Properties loadPropertiesFromClasspath(String filename) throws IOException {

	Properties result = new Properties();
	InputStream in = null;
	try {
	    in = Utilities.class.getClassLoader().getResourceAsStream(filename);
	    result.load(in);
	} catch (IOException e) {
	    log.error("Errore durante il caricamento del file {}: {}", filename, e.getMessage());
	    throw e;
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		}
	    }
	}
	return result;
    }

    public static String inputStreamToString(InputStream in) throws Exception, IOException {

	BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	StringBuilder sb = new StringBuilder();
	String line = null;
	while ((line = reader.readLine()) != null) {
	    sb.append(line);
	}
	in.close();
	String co = sb.toString();
	return co;
    }

    public static byte[] getBytesFromFile(File file) throws IOException {

	InputStream is = new FileInputStream(file);
	long length = file.length();
	if (length > Integer.MAX_VALUE) {
	    throw new IOException("File troppo grande!");
	}
	byte[] bytes = new byte[(int) length];
	int offset = 0;
	int numRead = 0;
	while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
	    offset += numRead;
	}
	if (offset < bytes.length) {
	    throw new IOException("Errore durante la lettura del file: " + file.getName());
	}
	is.close();
	return bytes;
    }

    public static List<Integer> contaInteri(int start, int end) {

	List<Integer> list = new ArrayList<Integer>();
	for (int i = start; i <= end; i++) {
	    list.add(i);
	}
	return list;
    }

    public static File createTempFileFromArrayByte(byte[] b, String estenzione) {

	File tempFile = null;
	try {
	    tempFile = File.createTempFile("tempFile", "." + estenzione);
	    FileOutputStream fos = new FileOutputStream(tempFile);
	    fos.write(b);
	    fos.close();
	} catch (IOException e) {
	    log.error("Errore durante la creazione del file temporaneo: {}", e.getMessage());
	}
	return tempFile;
    }

    public static String getStringFromInputStream(InputStream is) {

	BufferedReader br = null;
	StringBuilder sb = new StringBuilder();
	String line;
	try {
	    br = new BufferedReader(new InputStreamReader(is));
	    while ((line = br.readLine()) != null) {
		sb.append(line);
	    }
	} catch (IOException e) {
	    log.error("Errore la trasformazione di Inputstream in string: {}", e.getMessage());
	} finally {
	    if (br != null) {
		try {
		    br.close();
		} catch (IOException e) {
		    log.error("Errore la trasformazione di Inputstream in string: {}", e.getMessage());
		}
	    }
	}
	return sb.toString();
    }

    public static String extractExtension(String nomeFile) {

	if (nomeFile == null || nomeFile.equals("")) {
	    throw new RuntimeException("Attenzione!! il nome del file non può essere nullo o vuoto");
	}
	return nomeFile.substring(nomeFile.lastIndexOf('.') + 1);
    }

    public static boolean isFirstDayofMonth(Calendar calendar) {

	if (calendar == null) {
	    throw new IllegalArgumentException("Calendar cannot be null.");
	}
	int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
	return dayOfMonth == 1;
    }

    public static boolean isFirstMonth(Calendar calendar) {

	if (calendar == null) {
	    throw new IllegalArgumentException("Calendar cannot be null.");
	}
	int dayOfMonth = calendar.get(Calendar.MONTH);
	return dayOfMonth == 0;
    }

    /**
     * Visita ricorsiva di una struttura xml
     * 
     * @param o
     * @param depth
     * @param nomevariabile
     * @param mappaNomeVariabileValore
     * @param valueTemp
     */
    public static void listNodes(Object o, int depth, String nomevariabile, Map<String, String> mappaNomeVariabileValore, String valueTemp) {

	if (o instanceof Element) {
	    Element element = (Element) o;
	    if (((Element) o).isRootElement()) {
		log.trace("listNodes# Depth = {}. Root Element = {}", depth, ((Element) o).getName());
		mappaNomeVariabileValore.put(Utilities.ROOT, ((Element) o).getName());
	    } else {
		log.trace("listNodes# Depth = {}.  Element = {}", depth, ((Element) o).getName());
	    }
	    if (element.getChildren().isEmpty()) {
		// l'elemento non ha figli, essendo nodo foglia sarà l'ultima porzione di stringa che 
		// compone il nome della variabile
		log.trace("listNodes# Element = {} has child = {}", ((Element) o).getName(), false);
		if (!element.hasAttributes()) {
		    log.trace("listNodes# Element = {} has Attribute = {}", ((Element) o).getName(), false);
		    nomevariabile += "#" + element.getName();
		    String value = element.getValue();
		    log.trace("listNodes# Nome variabile = {}, Valore = {}. Aggiungo alla mappa", nomevariabile, value);
		    mappaNomeVariabileValore.put(nomevariabile, value);
		    // Annullo il valore variabile per crearne una nuova a partire dal primo nodo padre
		    nomevariabile = "";
		} else {
		    log.trace("listNodes# Element = {} has Attribute = {}", ((Element) o).getName(), true);
		    // log.debug("listNodes# Nome variabile = {}", nomevariabile);
		    valueTemp = element.getValue();
		    nomevariabile += "#" + element.getName();
		}
	    } else {
		nomevariabile += "#" + element.getName();
	    }
	    //Itera sugli eventuali attributi dell'elemento
	    if (!((Element) o).getAttributes().isEmpty()) {
		Iterator iter = element.getAttributes().iterator();
		while (iter.hasNext()) {
		    Object attribute = iter.next();
		    listNodes(attribute, depth + 1, nomevariabile, mappaNomeVariabileValore, valueTemp);
		}
	    }
	    //Itera sui figli dell'elemento
	    List children = element.getContent();
	    Iterator iterator = children.iterator();
	    while (iterator.hasNext()) {
		Object child = iterator.next();
		listNodes(child, depth + 1, nomevariabile, mappaNomeVariabileValore, valueTemp);
	    }
	} else if (o instanceof Document) {
	    log.trace("listNodes# Depth = {}. Document non gestito", depth);
	    Document doc = (Document) o;
	    List children = doc.getContent();
	    Iterator iterator = children.iterator();
	    while (iterator.hasNext()) {
		Object child = iterator.next();
		listNodes(child, depth + 1, nomevariabile, mappaNomeVariabileValore, valueTemp);
	    }
	} else if (o instanceof Comment) {
	    log.trace("listNodes# Depth = {}. Comment non gestito", depth);
	    //	    System.out.println("depth" + depth + " Comment:");
	    //	    System.out.println(((Comment) o).getText());
	} else if (o instanceof CDATA) {
	    log.trace("listNodes# Depth = {}. Sezione CDATA non gestito", depth);
	    //	    System.out.print("depth" + depth + " Sezione CDATA:");
	    //	    // CDATA è una sottoclasse di Text di conseguenza questo test
	    //	    // va prima di quello per Text.
	    //	    System.out.println(((CDATA) o).getText());
	} else if (o instanceof Text) {
	    log.trace("listNodes# Depth = {}. Text non gestito", depth);
	    //	    System.out.print("depth" + depth + "  Text:");
	    //	    System.out.println(((Text) o).getValue());
	} else if (o instanceof Attribute) {
	    log.trace("listNodes# Depth = {}.  Attribute = {}", depth, ((Attribute) o).getQualifiedName());
	    //System.out.println(((Attribute) o).getValue());
	    String value = valueTemp + SEPARATORE_VALORE_VALORE_DECODIFICATO + ((Attribute) o).getValue();
	    log.trace("listNodes# Nome variabile = {}, Valore = {}. Aggiungo alla mappa", nomevariabile, value);
	    mappaNomeVariabileValore.put(nomevariabile, value);
	    valueTemp = "";
	} else if (o instanceof DocType) {
	    log.trace("listNodes# Depth = {}. DocType non gestito", depth);
	    //	    System.out.print("depth" + depth + " DocType:");
	    //	    System.out.print(((DocType) o).getElementName());
	    //	    System.out.println(((DocType) o).getPublicID());
	} else {
	    log.error("listNodes#Tipo non previsto: {}", o.getClass());
	    //System.out.println("Tipo non previsto: " + o.getClass());
	}
    }

    public static void gracefullyDeleteFiles(File tempFile) {

	try {
	    FileUtils.forceDelete(tempFile);
	} catch (Exception e) {
	    log.error("gracefullyDeleteFiles# {}-{}", tempFile.getName(), e.getMessage());
	}
    }

    public static SportelloType getSportelloBackoffice(DeployProperties deployProperties, String idente, String idSportello) {

	SportelloType s = new SportelloType();
	s.setIdEnte(idente);
	s.setIdNodo(deployProperties.getStcIdNodoDestinatario());
	s.setIdSportello(idSportello);
	return s;
    }

    public static SportelloType getSportelloInfocamere(DeployProperties deployProperties) {

	SportelloType s = new SportelloType();
	s.setIdEnte(deployProperties.getStcIdEnteMittente());
	s.setIdNodo(deployProperties.getStcIdNodoMittente());
	s.setIdSportello(deployProperties.getStcIdSportelloMittente());
	return s;
    }

    public static String APP_TEMP_FOLDER = "NLA_INFOCAMERE";

    public static File createTmpAllegato(DataHandler binaryData, String folderPratica) throws IOException {

	File tmpDir = createTmpDir(APP_TEMP_FOLDER, folderPratica);
	File tempFile = File.createTempFile("tempFile", ".tmp", tmpDir);
	FileOutputStream fos = new FileOutputStream(tempFile);
	IOUtils.copy(binaryData.getInputStream(), fos);
	fos.close();
	return tempFile;
    }
}
