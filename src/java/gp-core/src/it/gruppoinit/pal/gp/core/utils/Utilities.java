package it.gruppoinit.pal.gp.core.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.fileconverter.MergeAndConvertRequest;
import it.gruppoinit.fileconverter.MergeAndConvertResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.ValoreParametroType;

public class Utilities {

    private static final Logger log = LoggerFactory.getLogger(Utilities.class);
    private static Boolean flagIsBackoffice = null;
    public static final String ALGORITHM_MD5 = "MD5";
    public static final String ALGORITHM_SHA1 = "SHA1";
    private static SecureRandom random = new SecureRandom();
    /** different dictionaries used */
    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";

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

    /**
     * Restituisce una stringa di caratteri di lunghezza pari al parametro passato. La stringa è composta dalla
     * concatenazione dei caratteri ascii associati ai numeri generati casualmente
     * 
     * @param lunghezza
     *            :lunghezza della password
     * @return
     */
    public static String generaPassword(Integer lunghezza) {

	if (lunghezza == null) {
	    lunghezza = Integer.valueOf(8);
	}
	String dic = ALPHA + NUMERIC;
	StringBuilder result = new StringBuilder();
	for (int i = 0; i < lunghezza; i++) {
	    int index = random.nextInt(dic.length());
	    result.append(dic.charAt(index));
	}
	return result.toString();
    }

    /**
     * Genera un codice Numerico di un numero specificato di caratteri numerici
     * 
     * @param lunghezza
     *            :lunghezza della password
     * @return
     */
    public static String generaCodiceNumerico(Integer lunghezza) {

	if (lunghezza == null) {
	    lunghezza = Integer.valueOf(8);
	}
	String dic = NUMERIC;
	StringBuilder result = new StringBuilder();
	for (int i = 0; i < lunghezza; i++) {
	    int index = random.nextInt(dic.length());
	    result.append(dic.charAt(index));
	}
	return result.toString();
    }

    /**
     * Ritorna il codice fiscale per i dati passati
     * 
     * @param cognome
     * @param nome
     * @param data
     * @param sesso
     * @param codcomune
     * @return
     * @throws IllegalArgumentException
     *             :nessun campo può essere vuoto o nullo
     */
    public static String calcolaCodiceFiscale(String cognome, String nome, String data, String sesso, String codcomune)
	    throws IllegalArgumentException {

	if (!StringUtils.isNotBlank(cognome)) {
	    throw new IllegalArgumentException("Cognome: non può essere null ");
	}
	if (!StringUtils.isNotBlank(nome)) {
	    throw new IllegalArgumentException("nome: non può essere null ");
	}
	if (!StringUtils.isNotBlank(sesso)) {
	    throw new IllegalArgumentException("sesso: non può essere null ");
	}
	if (!StringUtils.isNotBlank(codcomune)) {
	    throw new IllegalArgumentException("codcomune: non può essere null ");
	}
	if (data == null) {
	    throw new IllegalArgumentException("data: non può essere null ");
	}
	cognome = cognome.replaceAll(" ", "");
	cognome = cognome.replaceAll("'", "");
	nome = nome.replaceAll(" ", "");
	nome = nome.replaceAll("'", "");
	int giorno = Integer.parseInt(data.substring(0, 2));
	String mese = data.substring(3, 5);
	String anno = data.substring(8);
	String cod = "";
	String[] codice = new String[16];
	String[] surname = new String[100];
	String[] name = new String[100];
	String[] consonanti = new String[100];
	String[] vocali = new String[100];
	int i = 0;
	int j = 0;
	int k = 0;
	for (i = 0; i < 100; i++) {
	    consonanti[i] = "";
	    vocali[i] = "";
	    surname[i] = "";
	    name[i] = "";
	}
	for (i = 0; i < 16; i++) {
	    codice[i] = "";
	}
	for (i = 0; i < cognome.length(); i++) {
	    surname[i] = cognome.substring(i, i + 1);
	}
	for (i = 0; i < nome.length(); i++) {
	    name[i] = nome.substring(i, i + 1);
	}
	for (i = 0; i < cognome.length(); i++) {
	    if (isConsonant(surname[i])) {
		consonanti[j] = surname[i];
		j++;
	    }
	}
	// cognome
	j = 0;
	for (i = 0; i < cognome.length(); i++) {
	    if (!isConsonant(surname[i])) {
		vocali[j] = surname[i];
		j++;
	    }
	}
	k = 0;
	while (consonanti[k] != "" && k < 3) {
	    codice[k] = consonanti[k].toUpperCase();
	    k++;
	}
	if (k == 2) {
	    if (vocali[0] != "") {
		codice[2] = vocali[0].toUpperCase();
	    } else {
		codice[2] = "X";
	    }
	} else if (k == 1) {
	    if (!vocali[0].equalsIgnoreCase("")) {
		codice[1] = vocali[0].toUpperCase();
	    } else {
		codice[1] = "X";
		codice[2] = "X";
	    }
	    if (!vocali[1].equalsIgnoreCase("")) {
		codice[2] = vocali[1].toUpperCase();
	    } else {
		codice[2] = "X";
	    }
	} else if (k == 0) {
	    i = 0;
	    while (!vocali[i].equalsIgnoreCase("")) {
		codice[i] = vocali[i].toUpperCase();
		i++;
	    }
	    for (j = i; j < 3; j++) {
		codice[j] = "X";
	    }
	}
	for (i = 0; i < 100; i++) {
	    consonanti[i] = "";
	    vocali[i] = "";
	}
	// nome
	j = 0;
	for (i = 0; i < nome.length(); i++) {
	    if (isConsonant(name[i])) {
		consonanti[j] = name[i];
		j++;
	    }
	}
	j = 0;
	for (i = 0; i < nome.length(); i++) {
	    if (!isConsonant(name[i])) {
		vocali[j] = name[i];
		j++;
	    }
	}
	k = 0;
	i = 0;
	while (consonanti[k] != "" && k < 4) {
	    if (k != 1) {
		codice[3 + i] = consonanti[k].toUpperCase();
		i++;
	    }
	    k++;
	}
	if (k != 4)// ripeto la stessa cosa del cognome!
	{
	    k = 0;
	    while (consonanti[k] != "" && k < 3) {
		codice[3 + k] = consonanti[k].toUpperCase();
		k++;
	    }
	    if (k == 2) {
		if (!vocali[0].equalsIgnoreCase("")) {
		    codice[3 + 2] = vocali[0].toUpperCase();
		} else {
		    codice[3 + 2] = "X";
		}
	    } else if (k == 1) {
		if (!vocali[0].equalsIgnoreCase("")) {
		    codice[3 + 1] = vocali[0].toUpperCase();
		    if (!vocali[1].equalsIgnoreCase("")) {
			codice[3 + 2] = vocali[1].toUpperCase();
		    } else {
			codice[3 + 2] = "X";
		    }
		} else {
		    codice[3 + 1] = "X";
		    codice[3 + 2] = "X";
		}
	    } else if (k == 0) {
		i = 0;
		while (!vocali[i].equalsIgnoreCase("")) {
		    codice[i + 3] = vocali[i].toUpperCase();
		    i++;
		}
		for (j = i; j < 3; j++) {
		    codice[j + 3] = "X";
		}
	    }
	}
	// anno di nascita
	codice[6] = anno.substring(0, 1);
	codice[7] = anno.substring(1, 2);
	// mese di nascita
	String letteraMese = "";
	if (mese.equalsIgnoreCase("01")) {
	    letteraMese = "A";
	} else if (mese.equalsIgnoreCase("02")) {
	    letteraMese = "B";
	} else if (mese.equalsIgnoreCase("03")) {
	    letteraMese = "C";
	} else if (mese.equalsIgnoreCase("04")) {
	    letteraMese = "D";
	} else if (mese.equalsIgnoreCase("05")) {
	    letteraMese = "E";
	} else if (mese.equalsIgnoreCase("06")) {
	    letteraMese = "H";
	} else if (mese.equalsIgnoreCase("07")) {
	    letteraMese = "L";
	} else if (mese.equalsIgnoreCase("08")) {
	    letteraMese = "M";
	} else if (mese.equalsIgnoreCase("09")) {
	    letteraMese = "P";
	} else if (mese.equalsIgnoreCase("10")) {
	    letteraMese = "R";
	} else if (mese.equalsIgnoreCase("11")) {
	    letteraMese = "S";
	} else if (mese.equalsIgnoreCase("12")) {
	    letteraMese = "T";
	}
	codice[8] = letteraMese;
	// giorno di nascita
	String _giorno = "";
	if (sesso.equalsIgnoreCase("F")) {
	    giorno += 40;
	    _giorno = String.valueOf(giorno);
	} else {
	    if (giorno < 10) {
		_giorno = "0" + String.valueOf(giorno);
	    } else {
		_giorno = String.valueOf(giorno);
	    }
	}
	codice[9] = _giorno.substring(0, 1);
	codice[10] = _giorno.substring(1, 2);
	// codice del comune di nascita
	codice[11] = codcomune.substring(0, 1);
	codice[12] = codcomune.substring(1, 2);
	codice[13] = codcomune.substring(2, 3);
	codice[14] = codcomune.substring(3, 4);
	codice[15] = "0";
	// lettera di controllo
	String[] codicePari = new String[8];
	String[] codiceDispari = new String[8];
	for (i = 0; i < 8; i++) {
	    codicePari[i] = codice[i * 2 + 1];
	    codiceDispari[i] = codice[i * 2];
	}
	// codiceDispari[7] = codice[14];
	int sommaPari = 0;
	int sommaDispari = 0;
	for (i = 0; i < 8; i++) {
	    int valore = 0;
	    if (codiceDispari[i].equalsIgnoreCase("A") || codiceDispari[i].equalsIgnoreCase("0")) {
		valore = 1;
	    } else if (codiceDispari[i].equalsIgnoreCase("B") || codiceDispari[i].equalsIgnoreCase("1")) {
		valore = 0;
	    } else if (codiceDispari[i].equalsIgnoreCase("C") || codiceDispari[i].equalsIgnoreCase("2")) {
		valore = 5;
	    } else if (codiceDispari[i].equalsIgnoreCase("D") || codiceDispari[i].equalsIgnoreCase("3")) {
		valore = 7;
	    } else if (codiceDispari[i].equalsIgnoreCase("E") || codiceDispari[i].equalsIgnoreCase("4")) {
		valore = 9;
	    } else if (codiceDispari[i].equalsIgnoreCase("F") || codiceDispari[i].equalsIgnoreCase("5")) {
		valore = 13;
	    } else if (codiceDispari[i].equalsIgnoreCase("G") || codiceDispari[i].equalsIgnoreCase("6")) {
		valore = 15;
	    } else if (codiceDispari[i].equalsIgnoreCase("H") || codiceDispari[i].equalsIgnoreCase("7")) {
		valore = 17;
	    } else if (codiceDispari[i].equalsIgnoreCase("I") || codiceDispari[i].equalsIgnoreCase("8")) {
		valore = 19;
	    } else if (codiceDispari[i].equalsIgnoreCase("J") || codiceDispari[i].equalsIgnoreCase("9")) {
		valore = 21;
	    } else if (codiceDispari[i].equalsIgnoreCase("K")) {
		valore = 2;
	    } else if (codiceDispari[i].equalsIgnoreCase("L")) {
		valore = 4;
	    } else if (codiceDispari[i].equalsIgnoreCase("M")) {
		valore = 18;
	    } else if (codiceDispari[i].equalsIgnoreCase("N")) {
		valore = 20;
	    } else if (codiceDispari[i].equalsIgnoreCase("O")) {
		valore = 11;
	    } else if (codiceDispari[i].equalsIgnoreCase("P")) {
		valore = 3;
	    } else if (codiceDispari[i].equalsIgnoreCase("Q")) {
		valore = 6;
	    } else if (codiceDispari[i].equalsIgnoreCase("R")) {
		valore = 8;
	    } else if (codiceDispari[i].equalsIgnoreCase("S")) {
		valore = 12;
	    } else if (codiceDispari[i].equalsIgnoreCase("T")) {
		valore = 14;
	    } else if (codiceDispari[i].equalsIgnoreCase("U")) {
		valore = 16;
	    } else if (codiceDispari[i].equalsIgnoreCase("V")) {
		valore = 10;
	    } else if (codiceDispari[i].equalsIgnoreCase("W")) {
		valore = 22;
	    } else if (codiceDispari[i].equalsIgnoreCase("X")) {
		valore = 25;
	    } else if (codiceDispari[i].equalsIgnoreCase("Y")) {
		valore = 24;
	    } else if (codiceDispari[i].equalsIgnoreCase("Z")) {
		valore = 23;
	    }
	    sommaPari += valore;
	    if (codicePari[i].equalsIgnoreCase("A") || codicePari[i].equalsIgnoreCase("0")) {
		valore = 0;
	    } else if (codicePari[i].equalsIgnoreCase("B") || codicePari[i].equalsIgnoreCase("1")) {
		valore = 1;
	    } else if (codicePari[i].equalsIgnoreCase("C") || codicePari[i].equalsIgnoreCase("2")) {
		valore = 2;
	    } else if (codicePari[i].equalsIgnoreCase("D") || codicePari[i].equalsIgnoreCase("3")) {
		valore = 3;
	    } else if (codicePari[i].equalsIgnoreCase("E") || codicePari[i].equalsIgnoreCase("4")) {
		valore = 4;
	    } else if (codicePari[i].equalsIgnoreCase("F") || codicePari[i].equalsIgnoreCase("5")) {
		valore = 5;
	    } else if (codicePari[i].equalsIgnoreCase("G") || codicePari[i].equalsIgnoreCase("6")) {
		valore = 6;
	    } else if (codicePari[i].equalsIgnoreCase("H") || codicePari[i].equalsIgnoreCase("7")) {
		valore = 7;
	    } else if (codicePari[i].equalsIgnoreCase("I") || codicePari[i].equalsIgnoreCase("8")) {
		valore = 8;
	    } else if (codicePari[i].equalsIgnoreCase("J") || codicePari[i].equalsIgnoreCase("9")) {
		valore = 9;
	    } else if (codicePari[i].equalsIgnoreCase("K")) {
		valore = 10;
	    } else if (codicePari[i].equalsIgnoreCase("L")) {
		valore = 11;
	    } else if (codicePari[i].equalsIgnoreCase("M")) {
		valore = 12;
	    } else if (codicePari[i].equalsIgnoreCase("N")) {
		valore = 13;
	    } else if (codicePari[i].equalsIgnoreCase("O")) {
		valore = 14;
	    } else if (codicePari[i].equalsIgnoreCase("P")) {
		valore = 15;
	    } else if (codicePari[i].equalsIgnoreCase("Q")) {
		valore = 16;
	    } else if (codicePari[i].equalsIgnoreCase("R")) {
		valore = 17;
	    } else if (codicePari[i].equalsIgnoreCase("S")) {
		valore = 18;
	    } else if (codicePari[i].equalsIgnoreCase("T")) {
		valore = 19;
	    } else if (codicePari[i].equalsIgnoreCase("U")) {
		valore = 20;
	    } else if (codicePari[i].equalsIgnoreCase("V")) {
		valore = 21;
	    } else if (codicePari[i].equalsIgnoreCase("W")) {
		valore = 22;
	    } else if (codicePari[i].equalsIgnoreCase("X")) {
		valore = 23;
	    } else if (codicePari[i].equalsIgnoreCase("Y")) {
		valore = 24;
	    } else if (codicePari[i].equalsIgnoreCase("Z")) {
		valore = 25;
	    }
	    sommaDispari += valore;
	}
	int risultato = ((sommaDispari + sommaPari) % 26);
	switch (risultato) {
	case 0:
	    codice[15] = "A";
	    break;
	case 1:
	    codice[15] = "B";
	    break;
	case 2:
	    codice[15] = "C";
	    break;
	case 3:
	    codice[15] = "D";
	    break;
	case 4:
	    codice[15] = "E";
	    break;
	case 5:
	    codice[15] = "F";
	    break;
	case 6:
	    codice[15] = "G";
	    break;
	case 7:
	    codice[15] = "H";
	    break;
	case 8:
	    codice[15] = "I";
	    break;
	case 9:
	    codice[15] = "J";
	    break;
	case 10:
	    codice[15] = "K";
	    break;
	case 11:
	    codice[15] = "L";
	    break;
	case 12:
	    codice[15] = "M";
	    break;
	case 13:
	    codice[15] = "N";
	    break;
	case 14:
	    codice[15] = "O";
	    break;
	case 15:
	    codice[15] = "P";
	    break;
	case 16:
	    codice[15] = "Q";
	    break;
	case 17:
	    codice[15] = "R";
	    break;
	case 18:
	    codice[15] = "S";
	    break;
	case 19:
	    codice[15] = "T";
	    break;
	case 20:
	    codice[15] = "U";
	    break;
	case 21:
	    codice[15] = "V";
	    break;
	case 22:
	    codice[15] = "W";
	    break;
	case 23:
	    codice[15] = "X";
	    break;
	case 24:
	    codice[15] = "Y";
	    break;
	case 25:
	    codice[15] = "Z";
	    break;
	}
	cod = StringUtils.join(codice);
	return cod;
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
     * Metodo di utilità per rimuovere anni ad una data.
     * 
     * @see Calendar#roll(int, int)
     * @param dateToDecrement
     *            la data da aggiornare
     * @param amount
     *            il numero di anni
     * @return
     */
    public static Date removeYears(Date dateToDecrement, int amount) {

	amount = amount * (-1);
	GregorianCalendar c = new GregorianCalendar();
	c.setTime(dateToDecrement);
	c.roll(Calendar.YEAR, amount);
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

    public static List<String> getWarnings() {

	List<String> warnings = FlashMessages.getWarnings();
	FlashMessages.removeWarnings();
	return warnings;
    }

    public static List<String> getInfos() {

	List<String> infos = FlashMessages.getInfos();
	FlashMessages.removeInfos();
	return infos;
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

    public static String formatDateLocale(Date d, String formatDateAndTimePattern, Locale locale) {

	String _d = "";
	if (d != null) {
	    try {
		DateFormat sdf = new SimpleDateFormat(formatDateAndTimePattern, locale);
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

    public static GregorianCalendar getDate(String date) {

	return Utilities.getDate(date, WebConstants.DATE_FORMAT_PATTERN);
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

    public static Date dateWithoutTime(Date data) {

	if (data == null) {
	    return null;
	}
	return impostaOrarioAData(data, 0, 0, 0, Calendar.AM);
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
     * @param first
     *            la prima data. può essere nulla
     * @param later
     *            la second data. può essere nulla
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
    public static boolean isDateGreater(Date first, Date second) {

	if (first == null && second == null) {
	    return false;
	}
	if (first == null) {
	    return false;
	}
	if (second == null) {
	    return true;
	}
	Calendar earlierCal = Calendar.getInstance();
	earlierCal.setTime(first);
	Calendar laterCal = Calendar.getInstance();
	laterCal.setTime(second);
	Integer earlierYear = earlierCal.get(Calendar.YEAR);
	Integer laterYear = laterCal.get(Calendar.YEAR);
	if (earlierYear.intValue() != laterYear.intValue()) {
	    return earlierYear.compareTo(laterYear) > 0;
	}
	Integer earlierMonth = earlierCal.get(Calendar.MONTH);
	Integer laterMonth = laterCal.get(Calendar.MONTH);
	if (earlierMonth.intValue() != laterMonth.intValue()) {
	    return earlierMonth.compareTo(laterMonth) > 0;
	}
	Integer earlierDate = earlierCal.get(Calendar.DATE);
	Integer laterDate = laterCal.get(Calendar.DATE);
	return earlierDate.compareTo(laterDate) > 0;
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

    public final static String JAXB_ENCODING_UTF_8 = "UTF-8";

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
	unmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	unmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, includeRoot);
	return (T) unmarshaller.unmarshal(new ByteArrayInputStream(jsonInput.getBytes(encoding)));
    }

    @SuppressWarnings("unchecked")
    public static <T> T unMarshallJsonStream(InputStream is, Class<T> clazz, boolean includeRoot) throws JAXBException {

	Unmarshaller unmarshaller = JAXBContextFactory.createContext(new Class[] { clazz }, null).createUnmarshaller();
	unmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, includeRoot);
	unmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	return (T) unmarshaller.unmarshal(new StreamSource(is), clazz).getValue();
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
	    log.error("marshallObject: {}", e1.getMessage());
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

    public static void permessiFSLinux(File file, int mode) {

	String filePath = file.getParent() + "/";
	if (filePath.equals("//")) {
	    return;
	}
	String cmd = "chmod -R " + mode + " " + filePath;
	try {
	    String operatingSystem = System.getProperty("os.name");
	    if (StringUtils.isNotBlank(operatingSystem)) {
		if (operatingSystem.toLowerCase().contains("linux")) {
		    Runtime rt = Runtime.getRuntime();
		    log.debug("permessiFSLinux: eseguo [{}]", cmd);
		    Process pr = rt.exec(cmd);
		    if (log.isDebugEnabled()) {
			int i = pr.waitFor();
			String line = "";
			String esito = "";
			BufferedReader buff = null;
			InputStreamReader isr = null;
			InputStream is = null;
			if (i == 0) {
			    is = pr.getInputStream();
			    isr = new InputStreamReader(is);
			    buff = new BufferedReader(isr);
			    // read the output from the command
			    while ((line = buff.readLine()) != null) {
				esito = esito.concat(line).concat("\n");
			    }
			    log.debug("permessiFSLinux: output di esecuzione del comando[{}], esito[{}]", esito, pr.exitValue());
			} else {
			    is = pr.getErrorStream();
			    isr = new InputStreamReader(is);
			    buff = new BufferedReader(isr);
			    // read the output from the command
			    while ((line = buff.readLine()) != null) {
				esito = esito.concat(line).concat("\n");
			    }
			    log.error("permessiFSLinux: output di esecuzione del comando[{}], esito[{}]", esito, pr.exitValue());
			}
			if (buff != null) {
			    try {
				buff.close();
			    } catch (Exception e) {
			    }
			}
		    }
		}
	    }
	} catch (Exception e) {
	    log.error("permessiFSLinux: errore nel cercare di rendere la directory scrivibile [eseguo [{}]-[{}]",
		    new Object[] { cmd, e.getMessage() });
	}
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
		log.debug("E' stato invocato un metodo inesistente");
	    }
	}
	return target;
    }

    public static String getMessageFromBundle(ApplicationContext context, String chiave) {

	return getMessageFromBundle(context, chiave, null);
    }

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
     * 
     * @param codiceoggettoInt
     * @return
     */
    public static String buildPathFromCodiceOggetto(int codiceoggettoInt) {

	NumberFormat nf = NumberFormat.getIntegerInstance();
	nf.setGroupingUsed(false);
	String codiceoggetto = nf.format(codiceoggettoInt);
	codiceoggetto = StringUtils.leftPad(codiceoggetto, 10, '0');
	StringBuilder sb = new StringBuilder();
	sb.append(codiceoggetto.substring(0, 4));
	sb.append(File.separatorChar);
	sb.append(codiceoggetto.substring(4, 6));
	sb.append(File.separatorChar);
	sb.append(codiceoggetto.substring(6, 8));
	return sb.toString();
    }

    /**
     * 
     * @param codiceoggettoInt
     * @return
     */
    public static String[] foldersFromCodiceOggetto(int codiceoggettoInt) {

	String[] ret = new String[3];
	NumberFormat nf = NumberFormat.getIntegerInstance();
	nf.setGroupingUsed(false);
	String codiceoggetto = nf.format(codiceoggettoInt);
	codiceoggetto = StringUtils.leftPad(codiceoggetto, 10, '0');
	ret[0] = (codiceoggetto.substring(0, 4));
	ret[1] = (codiceoggetto.substring(4, 6));
	ret[2] = (codiceoggetto.substring(6, 8));
	return ret;
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
     * Legge un file zip da <code>zipFileInputStream</code> e lo scompatta nella directory <code>unzipDestDir</code>
     * 
     * @param zipFileInputStream
     *            : flusso in lettura su un file ZIP.
     * @param unzipDestDir
     *            : directory di destinazione in cui scompattare il file ZIP. Se non esiste viene creata.
     * @return restituisce una {@link List} di {@link File} ciascuno dei quali punta ad uno dei files estratti
     *         dall'archivio.
     * @throws IOException
     *             se il file non è in formato ZIP o se si verifica un qualunque errore di lettura o scrittura dei files
     */
    public static List<File> unzipToDirAndReturnEntries(InputStream zipFileInputStream, File unzipDestDir) throws IOException {

	ZipArchiveInputStream zais = new ZipArchiveInputStream(zipFileInputStream);
	List<File> unzippedFiles = new ArrayList<File>();
	int ZIP_READ_BUFFER_SIZE = 1024 * 8;
	// ZipInputStream zis = new ZipInputStream(zipFileInputStream);
	// ZipEntry entry = null;
	BufferedOutputStream bos = null;
	FileOutputStream fos = null;
	byte[] buffer = null;
	ArchiveEntry entry;
	while ((entry = zais.getNextEntry()) != null) {
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
		while ((read = zais.read(buffer, 0, ZIP_READ_BUFFER_SIZE)) > -1) {
		    bos.write(buffer, 0, read);
		}
		bos.flush();
		bos.close();
		unzippedFiles.add(unzipped);
	    }
	}
	zais.close();
	//return the temp directory where we unzipped the archive
	return unzippedFiles;
    }

    /**
     * Legge un file zip da <code>zipFileInputStream</code> e lo scompatta nella directory <code>unzipDestDir</code>
     * 
     * @param rarFile
     *            : file RAR da scompattare.
     * @param unzipDestDir
     *            : directory di destinazione in cui scompattare il file ZIP. Se non esiste viene creata.
     * @return restituisce una {@link List} di {@link File} ciascuno dei quali punta ad uno dei files estratti
     *         dall'archivio.
     * @throws IOException
     *             se il file non è in formato ZIP o se si verifica un qualunque errore di lettura o scrittura dei files
     */
    public static List<File> unrarToDirAndReturnEntries(File rarFile, File unzipDestDir) throws IOException, RarException {

	List<File> unzippedFiles = new ArrayList<File>();
	Archive rar = new Archive(rarFile);
	int ZIP_READ_BUFFER_SIZE = 1024 * 8;
	//ZipInputStream zis = new ZipInputStream(rarFile);
	BufferedOutputStream bos = null;
	FileOutputStream fos = null;
	FileHeader fh = rar.nextFileHeader();
	while (fh != null) {
	    File unzipped = new File(unzipDestDir, fh.getFileNameString());
	    if (fh.isDirectory()) {
		unzipped.mkdirs();
	    } else {
		File parentFolder = unzipped.getParentFile();
		if (!parentFolder.exists()) {
		    parentFolder.mkdirs();
		}
		unzipped.createNewFile();
		fos = new FileOutputStream(unzipped);
		bos = new BufferedOutputStream(fos, ZIP_READ_BUFFER_SIZE);
		rar.extractFile(fh, bos);
		bos.flush();
		bos.close();
		unzippedFiles.add(unzipped);
	    }
	    fh = rar.nextFileHeader();
	}
	rar.close();
	//zis.close();
	//return the temp directory where we unzipped the archive
	return unzippedFiles;
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

    public static void writeZipEntries(File entry, ZipOutputStream writeTo, File parentPath) throws IOException {

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

    public static void writeZipEntries(InputStream fileStream, ZipOutputStream writeTo, String nomeFile) throws IOException {

	int ZIP_READ_BUFFER_SIZE = 1024 * 8;
	int bytesRead = 0;
	byte[] buffer = new byte[ZIP_READ_BUFFER_SIZE];
	BufferedInputStream bis = new BufferedInputStream(fileStream);
	ZipEntry zentry = new ZipEntry(nomeFile);
	writeTo.putNextEntry(zentry);
	while ((bytesRead = bis.read(buffer)) > -1) {
	    writeTo.write(buffer, 0, bytesRead);
	}
	writeTo.closeEntry();
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
     * Crea un DataHandler a partire da un array di byte
     * 
     * @param dh
     * @return byte[]
     * @throws IOException
     */
    public static DataHandler bytesToDataHandler(byte[] content) {

	try {
	    DataSource ds = new ByteArrayDataSource(content, "application/octet-stream");
	    return new DataHandler(ds);
	} catch (Exception e) {
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

    public static String formatImporto(BigDecimal importo, int minimumFractionDigits, int maximumFractionDigits, boolean groupingUsed) {

	if (importo != null) {
	    DecimalFormat df = new DecimalFormat();
	    df.setMaximumFractionDigits(maximumFractionDigits);
	    df.setMinimumFractionDigits(minimumFractionDigits);
	    df.setGroupingUsed(false);
	    return df.format(importo);
	}
	return "";
    }

    /**
     * <pre>
     * If you need to remove all non-US-ASCII (i.e. outside 0x20-0x7E) characters, you can do something like this:
     * 
     * s = s.replaceAll("[^\\x20-\\x7e]", ""); If you need to filter many strings, it would be better to use a
     * precompiled pattern:
     * 
     * private static final Pattern nonASCII = Pattern.compile("[^\\x20-\\x7e]");
     * 
     * 
    32=" "
    33="!"
    34="""
    35="#"
    36="$"
    37="%"
    38="&"
    39="'"
    40="("
    41=")"
    42="*"
    43="+"
    44=","
    45="-"
    46="."
    47="/"
    48="0"
    49="1"
    50="2"
    51="3"
    52="4"
    53="5"
    54="6"
    55="7"
    56="8"
    57="9"
    58=":"
    59=";"
    60="<"
    61="="
    62=">"
    63="?"
    64="@"
    65="A"
    66="B"
    67="C"
    68="D"
    69="E"
    70="F"
    71="G"
    72="H"
    73="I"
    74="J"
    75="K"
    76="L"
    77="M"
    78="N"
    79="O"
    80="P"
    81="Q"
    82="R"
    83="S"
    84="T"
    85="U"
    86="V"
    87="W"
    88="X"
    89="Y"
    90="Z"
    91="["
    92="\"
    93="]"
    94="^"
    95="_"
    96="`"
    97="a"
    98="b"
    99="c"
    100="d"
    101="e"
    102="f"
    103="g"
    104="h"
    105="i"
    106="j"
    107="k"
    108="l"
    109="m"
    110="n"
    111="o"
    112="p"
    113="q"
    114="r"
    115="s"
    116="t"
    117="u"
    118="v"
    119="w"
    120="x"
    121="y"
    122="z"
    123="{"
    124="|"
    125="}"
    126="~"
     * 
     * </pre>
     */
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

    public static String rimuoviCaratteriNonAsciiDallaStringa(String daVerificare, String stringaDaSostituire) {

	return NON_ASCII_PATTERN.matcher(StringUtils.defaultString(daVerificare)).replaceAll(stringaDaSostituire);
    }

    public static String buildHistoryBackFromRequest(HttpServletRequest request, String servletPathUriBack) {

	String urlBack = "";
	try {
	    StringBuffer qs = new StringBuffer("");
	    @SuppressWarnings("unchecked")
	    Enumeration<String> requestParam = request.getParameterNames();
	    while (requestParam.hasMoreElements()) {
		String parametro = requestParam.nextElement();
		if (StringUtils.isNotBlank(request.getParameter(parametro))) {
		    qs.append(parametro).append("=").append(StringUtils.defaultIfEmpty(request.getParameter(parametro), "")).append("&");
		}
	    }
	    String queryString = qs.toString();
	    if (queryString.endsWith("&")) {
		queryString = queryString.concat("1=1");
	    }
	    urlBack = servletPathUriBack;
	    if (StringUtils.isNotBlank(queryString)) {
		urlBack += "?" + queryString;
	    }
	    if (log.isDebugEnabled()) {
		log.debug("buildHistoryBackFromRequest: urlBack={}", urlBack);
	    }
	    urlBack = URLEncoder.encode(urlBack, "UTF-8");
	    urlBack = URLEncoder.encode(urlBack, "UTF-8");
	} catch (java.io.UnsupportedEncodingException e) {
	}
	return urlBack;
    }

    public static void main(String[] args) throws DatatypeConfigurationException {

	System.out.println(formatDateISO8601(new Date()));
	DatatypeFactory df = DatatypeFactory.newInstance();
	Duration warrantyDuration = df.newDuration("P1M");
	Date d = new Date();
	warrantyDuration.addTo(d);
	System.out.println(d);
	//	for (int i = 0; i < 20; i++) {
	//	    System.out.println(Utilities.generaPassword(10));
	//	}
	String mercato = "San Giovanni di Ostellato MER";
	System.out.println(replaceDescrizioneGiorno(mercato, Calendar.getInstance().getTime()));
	mercato = "SANTA RITA GIO";
	System.out.println(replaceDescrizioneGiornoDaUso(mercato, "GIO"));
	String t450 = "M40000011603496A10  `–’è#à°^  2019001000000023001IN20190226548/2019    20190503 080860";
	System.out.println("'" + t450 + "'");
	System.out.println("'" + rimuoviCaratteriNonAsciiDallaStringa(t450, "_") + "'");
	System.out.println(Utilities.formatDate(new Date(), "MMM yyyy"));
	System.out.println(Utilities.formatDate(new Date(), "E d MMM yyyy"));
	System.out.println(Utilities.formatDate(new Date(), "dd/MM/yyyy"));
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

    public static ValoreParametroType getCampoDaAltriDati(List<ParametroType> altriDati, String nomeParametroType) {

	log.debug("Cerco nella sezione altri dati il valore con ParametroType.nome {} ", nomeParametroType);
	ValoreParametroType valoreParametroType = null;
	if (altriDati != null) {
	    Iterator<ParametroType> it = altriDati.iterator();
	    log.debug("Itero tutti i valori presenti nella sezioni altri dati");
	    while (it.hasNext()) {
		ParametroType parametroType = (ParametroType) it.next();
		if (parametroType != null && StringUtils.isNotBlank(parametroType.getNome()) && parametroType.getNome().equals(nomeParametroType)) {
		    log.debug("Trovato campo di Altri dati con ParametroType.nome {}", nomeParametroType);
		    List<ValoreParametroType> valoreParametroTypes = parametroType.getValore();
		    if (valoreParametroTypes != null && !valoreParametroTypes.isEmpty()) {
			log.debug("Trovato campo di Altri dati con ParametroType.nome con valoreParametroType non vuoto");
			valoreParametroType = new ValoreParametroType();
			valoreParametroType = valoreParametroTypes.get(0);
			log.debug("Valore trovato, fine iterazione lista");
			break;
		    }
		}
	    }
	}
	return valoreParametroType;
    }

    public static String getValueFromXml(String xml, String xpathParam)
	    throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

	InputSource source = new InputSource(new StringReader(xml));
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder db = dbf.newDocumentBuilder();
	org.w3c.dom.Document document = db.parse(source);
	XPathFactory xpathFactory = XPathFactory.newInstance();
	XPath xpath = xpathFactory.newXPath();
	String result = xpath.evaluate(xpathParam, document);
	return result;
    }

    public static String getCurrentAppUrl(HttpServletRequest request) throws MalformedURLException {

	int port = request.getServerPort();
	if (request.getScheme().equals("http") && port == 80) {
	    port = -1;
	} else if (request.getScheme().equals("https") && port == 443) {
	    port = -1;
	}
	String context = request.getContextPath();
	URL serverURL = new URL(request.getScheme(), request.getServerName(), port, context);
	return serverURL.toString();
    }

    //private static final Pattern PATTERN_EMAIL_VALIDATION = Pattern.compile("^[\\w-\\._\\+%]+@(?:[\\w-]+\\.)+[\\w]{2,6}$");
    private static final Pattern PATTERN_EMAIL_VALIDATION = Pattern.compile(WebConstants.EMAIL_ADDRESS_VALIDATION_PATTERN);

    public static boolean validaIndirizzoMail(String email) {

	if (StringUtils.isBlank(email)) {
	    return false;
	}
	return PATTERN_EMAIL_VALIDATION.matcher(email).matches();
    }

    private static final Pattern ALFANUMERIC_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    public static boolean validaTestoAlfanumerico(String testo) {

	if (StringUtils.isBlank(testo)) {
	    return true;
	}
	return ALFANUMERIC_PATTERN.matcher(testo).matches();
    }

    public static String buildAbsoluteURL(HttpServletRequest request) {

	StringBuilder sb = new StringBuilder();
	String prot = request.getProtocol();
	int indexOfSlash = prot.indexOf('/');
	if (indexOfSlash > -1) {
	    prot = prot.substring(0, indexOfSlash);
	}
	sb.append(prot.toLowerCase()).append("://");
	sb.append(request.getLocalName());
	if (request.getLocalPort() != 80) {
	    sb.append(":").append(request.getLocalPort());
	}
	sb.append(request.getContextPath());
	return sb.toString();
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

    public static boolean isBackOffice() {

	if (flagIsBackoffice != null) {
	    return flagIsBackoffice;
	} else {
	    Properties props = null;
	    boolean isBO = false;
	    try {
		props = Utilities.loadPropertiesFromClasspath(WebConstants.DEPLOY_PROPS);
		String tokenUser = props.getProperty("ws.token.user");
		if (StringUtils.isNotEmpty(tokenUser) && tokenUser.trim().equalsIgnoreCase("BACKOFFICE")) {
		    isBO = true;
		}
	    } catch (IOException e) {
		log.error("isBackOffice() - impossibile eccedere al file deploy.properties: {}", e);
	    }
	    flagIsBackoffice = new Boolean(isBO);
	    return isBO;
	}
    }

    @Deprecated
    public static Oggetti convertXmlToPdf(Oggetti xml, InputStream xsltStream, String nomeFile) throws Exception, IOException {

	byte[] out = getHTML(new ByteArrayInputStream(xml.getOggetto()), xsltStream);
	FileConverterWsClient fileConverterWService = new FileConverterWsClient();
	ConvertBinaryRequest cbr = new ConvertBinaryRequest(ORMHelper.getToken(), out, "HTM", "PDF");
	Oggetti oggetti = new Oggetti();
	try {
	    ConvertBinaryResponse cResp = fileConverterWService.convertBinary(cbr);
	    oggetti.setDimensioneFile(cResp.getBinaryData().length);
	    if (StringUtils.isNotBlank(nomeFile)) {
		oggetti.setNomefile(nomeFile);
	    }
	    oggetti.setOggetto(cResp.getBinaryData());
	} catch (RemoteException e) {
	    throw new RuntimeException("Errore nella conversione del file in PDF:" + e.getMessage(), e);
	}
	return oggetti;
    }

    public static Oggetti convertXmlToPdf(byte[] xml, byte[] xsltStream, String nomeFile) throws Exception, IOException {

	FileConverterWsClient fileConverterWService = new FileConverterWsClient();
	MergeAndConvertRequest cbr = new MergeAndConvertRequest(ORMHelper.getToken(), xml, "XML", xsltStream, "XSL", "PDF");
	Oggetti oggetti = new Oggetti();
	try {
	    MergeAndConvertResponse cResp = fileConverterWService.mergeAndConvert(cbr);
	    oggetti.setDimensioneFile(cResp.getBinaryData().length);
	    if (StringUtils.isNotBlank(nomeFile)) {
		oggetti.setNomefile(nomeFile);
	    }
	    oggetti.setOggetto(cResp.getBinaryData());
	} catch (Exception e) {
	    throw new RuntimeException("Errore nella conversione del file in PDF:" + e.getMessage(), e);
	}
	return oggetti;
    }

    public static Oggetti convertXmlToPdf(Oggetti xml, InputStream xsltStream) throws Exception, IOException {

	return convertXmlToPdf(xml, xsltStream, null);
    }

    /**
     * Trasforma una stringa xml in html tramite un atrasformazione xslt
     * 
     * @param xml
     * @return
     */
    public static byte[] getHTML(Oggetti xml, InputStream xsltStream) {

	return getHTML(new ByteArrayInputStream(xml.getOggetto()), xsltStream);
    }

    /**
     * Trasforma una stringa xml in html tramite un atrasformazione xslt
     * 
     * @param xml
     * @return
     */
    public static byte[] getHTML(InputStream xml, InputStream xsltStream) {

	//InputStream in = null;
	try {
	    //in = this.getClass().getClassLoader().getResource(xsltPath).openStream();
	    byte[] xslBytes = IOUtils.toByteArray(xsltStream);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer = tFactory.newTransformer(new StreamSource(new ByteArrayInputStream(xslBytes)));
	    transformer.transform(new StreamSource(xml), new StreamResult(baos));
	    return baos.toByteArray();
	} catch (Exception e) {
	    //log.error("Errore durante il caricamento del file it/gruppoinit/xslt/visurainfocamere.xsl: {}", e);
	    throw new RuntimeException("Errore durante il caricamento del file:" + e.getMessage(), e);
	} finally {
	    if (xsltStream != null) {
		try {
		    xsltStream.close();
		} catch (IOException e) {
		}
	    }
	}
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

    public static InputStream callHttp(String url, boolean useBasicAuth, String user, String psw, HttpServletResponse response)
	    throws Exception, IOException {

	HttpClient cli = new HttpClient();
	// Imposto le creadenziali di basic authentication
	Credentials defaultcreds = new UsernamePasswordCredentials(user, psw);
	cli.getState().setCredentials(AuthScope.ANY, defaultcreds);
	int _status = 0;
	//String _url = "http://devel3.init.gruppoinit.it:8081/kettle/runJob/?job=init%2Fjob%2FListaIstanze&SESSIONID=a3fae734-6afb-4aa9-a458-06f284ea7731&PENTAHO_EXP_PATH=%2F%2Fdevel3%2FD%24%2FProgetti%2FSiGePro_SRC%2FSiGePro%2Fpentaho%2Ftemp";
	log.debug("Invoco url : {}", url);
	HttpMethod method = new GetMethod(url);
	method.setDoAuthentication(useBasicAuth);
	try {
	    _status = cli.executeMethod(method);
	} catch (Exception e) {
	    throw new RuntimeException(e.getMessage());
	    //	    response.setStatus(500);
	    //	    response.getOutputStream().print("<b>Si e' verificato un errore durante la chiamata a </b> " + url + " <p /> ");
	    //return null;
	}
	// Trasformo lo stream in una stringa 
	InputStream inputStream = method.getResponseBodyAsStream();
	return inputStream;
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

    public static String createLink(Map<String, String> parameterAndvalue, String url) {

	for (Map.Entry<String, String> entry : parameterAndvalue.entrySet()) {
	    url = StringUtils.replace(url, entry.getKey(), entry.getValue());
	}
	return url;
    }

    public static HashMap sortByValues(HashMap map) {

	List list = new LinkedList(map.entrySet());
	// Defined Custom Comparator here
	Collections.sort(list, new Comparator() {

	    public int compare(Object o1, Object o2) {

		return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
	    }
	});
	// Here I am copying the sorted list in HashMap
	// using LinkedHashMap to preserve the insertion order
	HashMap sortedHashMap = new LinkedHashMap();
	for (Iterator it = list.iterator(); it.hasNext();) {
	    Map.Entry entry = (Map.Entry) it.next();
	    sortedHashMap.put(entry.getKey(), entry.getValue());
	}
	return sortedHashMap;
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

    /**
     * crea una cartella temporanea partendo dalla "java.io.tmpdir"/{folderName}<br />
     * Es.: <b>C:\DOCUME~1\RICCAR~1\IMPOST~1\Temp\{folderName}</b> <br />
     * 
     * @param folderName
     * @return
     */
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

    /**
     * Ritorna un numero casuale tra 0 e max
     * 
     * @param max
     * @return
     */
    public static int geratoreInteriCasuali(int max) {

	Random random = new Random();
	int n = 7;
	int k = random.nextInt(max);
	return k;
    }

    public static List<MultipartFile> getFileFromMultipartRequest(HttpServletRequest request) {

	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	MultipartFile multipartFile = null;
	List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>();
	Iterator<String> iterator = multipartRequest.getFileNames();
	boolean isMultiFileInsert = false;
	log.debug("Controllo se è un inserimento multiplo di file");
	while (iterator.hasNext()) {
	    isMultiFileInsert = true;
	    String key = (String) iterator.next();
	    multipartFile = (MultipartFile) multipartRequest.getFile(key);
	    multipartFiles.add(multipartFile);
	}
	return multipartFiles;
    }

    public static String toCheckedString(String valore) {

	String result = "";
	if (StringUtils.defaultIfEmpty(valore, "0").equalsIgnoreCase("1")) {
	    result = " checked ";
	}
	return result;
    }

    public static boolean verificaPresenzaValoreIn(String valore, String cercaIn) {

	if (StringUtils.isEmpty(StringUtils.defaultString(valore).trim())) {
	    return false;
	}
	String[] n = StringUtils.defaultString(cercaIn).trim().split(",");
	for (String valInLista : n) {
	    if (StringUtils.defaultString(valInLista).trim().equalsIgnoreCase(valore.trim())) {
		return true;
	    }
	}
	return false;
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
     * @see https://www.w3.org/TR/xmlschema-2/#duration
     *
     *      <pre>
     * https://www.w3.org/TR/xmlschema-2/#duration
     * 
     * 3.2.6.1 Lexical representation
     * The lexical representation for duration is the [ISO 8601] extended format PnYn MnDTnH nMnS, where nY represents the number of years, nM the number of months, nD the number of days, 'T' is the date/time separator, nH the number of hours, nM the number of minutes and nS the number of seconds. The number of seconds can include decimal digits to arbitrary precision.
     * 
     * The values of the Year, Month, Day, Hour and Minutes components are not restricted but allow an arbitrary unsigned integer, i.e., an integer that conforms to the pattern [0-9]+.. Similarly, the value of the Seconds component allows an arbitrary unsigned decimal. Following [ISO 8601], at least one digit must follow the decimal point if it appears. That is, the value of the Seconds component must conform to the pattern [0-9]+(\.[0-9]+)?. Thus, the lexical representation of duration does not follow the alternative format of § 5.5.3.2.1 of [ISO 8601].
     * 
     * An optional preceding minus sign ('-') is allowed, to indicate a negative duration. If the sign is omitted a positive duration is indicated. See also ISO 8601 Date and Time Formats (§D).
     * 
     * For example, to indicate a duration of 1 year, 2 months, 3 days, 10 hours, and 30 minutes, one would write: P1Y2M3DT10H30M. One could also indicate a duration of minus 120 days as: -P120D.
     * 
     * Reduced precision and truncated representations of this format are allowed provided they conform to the following:
     * 
     * If the number of years, months, days, hours, minutes, or seconds in any expression equals zero, the number and its corresponding designator ·may· be omitted. However, at least one number and its designator ·must· be present.
     * The seconds part ·may· have a decimal fraction.
     * The designator 'T' must be absent if and only if all of the time items are absent. The designator 'P' must always be present.
     * For example, P1347Y, P1347M and P1Y2MT2H are all allowed; P0Y1347M and P0Y1347M0D are allowed. P-1347M is not allowed although -P1347M is allowed. P1Y2MT is not allowed.
     * 
     * 3.2.6.2 Order relation on duration
     * In general, the ·order-relation· on duration is a partial order since there is no determinate relationship between certain durations such as one month (P1M) and 30 days (P30D). The ·order-relation· of two duration values x and y is x < y iff s+x < s+y for each qualified dateTime s in the list below. These values for s cause the greatest deviations in the addition of dateTimes and durations. Addition of durations to time instants is defined in Adding durations to dateTimes (§E).
     * 
     * 1696-09-01T00:00:00Z
     * 1697-02-01T00:00:00Z
     * 1903-03-01T00:00:00Z
     * 1903-07-01T00:00:00Z
     * The following table shows the strongest relationship that can be determined between example durations. The symbol <> means that the order relation is indeterminate. Note that because of leap-seconds, a seconds field can vary from 59 to 60. However, because of the way that addition is defined in Adding durations to dateTimes (§E), they are still totally ordered.
     * 
     *  	Relation
     * P1Y	> P364D	<> P365D	 	<> P366D	< P367D
     * P1M	> P27D	<> P28D	<> P29D	<> P30D	<> P31D	< P32D
     * P5M	> P149D	<> P150D	<> P151D	<> P152D	<> P153D	< P154D
     * Implementations are free to optimize the computation of the ordering relationship. For example, the following table can be used to compare durations of a small number of months against days.
     * 
     *  	Months	1	2	3	4	5	6	7	8	9	10	11	12	13	...
     * Days	Minimum	28	59	89	120	150	181	212	242	273	303	334	365	393	...
     * Maximum	31	62	92	123	153	184	215	245	276	306	337	366	397	...
     * 
     *
     *      </pre>
     * 
     * @param data
     * @param durationStringLiteral
     * @return
     */
    public static Date addDuration(final Date datadaAggiornare, String durationStringLiteral) {

	try {
	    Calendar c = Calendar.getInstance();
	    c.setTime(datadaAggiornare);
	    Date d = c.getTime();
	    DatatypeFactory df = DatatypeFactory.newInstance();
	    Duration warrantyDuration = df.newDuration(durationStringLiteral);
	    warrantyDuration.addTo(d);
	    return d;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    public static void gracefullyDeleteFiles(File tempFile) {

	try {
	    FileUtils.forceDelete(tempFile);
	} catch (Exception e) {
	    log.error("gracefullyDeleteFiles# {}-{}", tempFile.getName(), e.getMessage());
	}
    }

    private static String matcher = "(?i)\\bLUN\\b|\\bMAR\\b|\\bMER\\b|\\bGIO\\b|\\b VEN\\b|\\bSAB\\b|\\bDOM\\b";
    private static SimpleDateFormat sdf = new SimpleDateFormat("E", Locale.ITALIAN);

    public static String replaceDescrizioneGiorno(String descrizioneMercato, Date dataMercato) {

	String giorno = " ";
	if (dataMercato != null) {
	    giorno = sdf.format(dataMercato).toUpperCase();
	}
	return descrizioneMercato.replaceAll(matcher, giorno);
    }

    public static String replaceDescrizioneGiornoDaUso(String descrizioneMercato, String uso) {

	return descrizioneMercato.replaceAll(matcher, "").trim();
    }

    public static String getStringFromFile(String File) {

	byte a[] = null;
	try {
	    InputStream fso = new FileInputStream(File);
	    a = new byte[fso.available()];
	    fso.read(a);
	    fso.close();
	} catch (Exception e) {
	    log.error("getStringFromFile: {}", e);
	}
	return (new String(a, 0, a.length));
    }

    public static byte[] getByteFromFile(String File) {

	byte a[] = null;
	try {
	    InputStream fso = new FileInputStream(File);
	    a = new byte[fso.available()];
	    fso.read(a);
	    fso.close();
	} catch (Exception e) {
	    log.error("getByteFromFile: {}", e);
	}
	return a;
    }

    public static final String ISO_8601BASIC_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static String formatDateISO8601(Date data) {

	SimpleDateFormat sdf = new SimpleDateFormat(ISO_8601BASIC_DATE_PATTERN);
	return sdf.format(data);
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

    @SuppressWarnings("unchecked")
    public static <T> T getBeanOfType(ApplicationContext applicationContext, String type) throws ClassNotFoundException {

	Class<?> forName = Class.forName(type);
	Map<String, Collection<T>> ret = applicationContext.getBeansOfType(forName);
	if (ret.isEmpty()) {
	    throw new ClassNotFoundException("Non è stato trovato il bean della classe " + type.getClass());
	}
	return (T) ret.values().iterator().next();
    }

    public static boolean nullSafeEqual(Integer valore1, Integer valore2) {

	if (valore1 == null && valore2 == null) {
	    return true;
	}
	if (valore1 == null) {
	    valore1 = Integer.MAX_VALUE;
	}
	if (valore2 == null) {
	    valore2 = Integer.MIN_VALUE;
	}
	return valore1.equals(valore2);
    }
}
