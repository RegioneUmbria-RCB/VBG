package it.sgp.middleware.security.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.sgp.middleware.security.domain.Comunisecurity;

public class Utilities {

    private static final Logger log = LoggerFactory.getLogger(Utilities.class);
    public static final String ALGORITHM_MD5 = "MD5";
    public static final String ALGORITHM_SHA1 = "SHA1";
    public static final String STANDARD_DATE_FORMAT = "dd/MM/yyyy";

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

    public static void main(String[] args) {

	String plain1 = "";
	String plain2 = " ";
	String plain3 = "a";
	String plain4 = "aajkdajk 18937138917389 djkdhadj kadhajk";
	String hash1 = Utilities.getHashText(plain1, Utilities.ALGORITHM_MD5, false);
	System.out.println(hash1 + " (" + hash1.length() + ")");
	String hash2 = Utilities.getHashText(plain2, Utilities.ALGORITHM_MD5, false);
	System.out.println(hash2 + " (" + hash2.length() + ")");
	String hash3 = Utilities.getHashText(plain3, Utilities.ALGORITHM_MD5, false);
	System.out.println(hash3 + " (" + hash3.length() + ")");
	String hash4 = Utilities.getHashText(plain4, Utilities.ALGORITHM_MD5, true);
	System.out.println(hash4 + " (" + hash4.length() + ")");
    }

    public static String formatDate(Date d) {

	String _d = "";
	if (d != null) {
	    try {
		//String pattern = formatDateAndTime ? "dd/MM/yyyy - HH:mm" : "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		_d = sdf.format(d);
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
	GregorianCalendar gd = new GregorianCalendar();
	try {
	    if (StringUtils.isNotBlank(format)) {
		sdf = new SimpleDateFormat(format);
	    } else {
		sdf = new SimpleDateFormat("dd/MM/yyyy");
	    }
	    Date d = sdf.parse(date);
	    gd.setTime(d);
	} catch (ParseException e) {
	    log.error("getDate(): {}", e.getMessage());
	}
	return gd;
    }

    public static GregorianCalendar getDateFromStandardFormat(String date) throws ParseException {

	SimpleDateFormat sdf = null;
	GregorianCalendar gd = new GregorianCalendar();
	sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
	Date d = sdf.parse(date);
	gd.setTime(d);
	return gd;
    }

    public static GregorianCalendar getDateFromFormat(String date, String format) throws ParseException {

	SimpleDateFormat sdf = null;
	GregorianCalendar gd = new GregorianCalendar();
	sdf = new SimpleDateFormat(format);
	Date d = sdf.parse(date);
	gd.setTime(d);
	return gd;
    }

    public static String resolveIdComune(Comunisecurity comunisecurity) {

	return StringUtils.isBlank(comunisecurity.getIdcomune()) ? comunisecurity.getId() : comunisecurity.getIdcomune();
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
}
