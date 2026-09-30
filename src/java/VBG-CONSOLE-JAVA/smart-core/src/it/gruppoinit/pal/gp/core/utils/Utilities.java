package it.gruppoinit.pal.gp.core.utils;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.ValoreParametroType;

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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
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

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.AnnotationUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

public class Utilities {

    private static final Logger log = LoggerFactory.getLogger(Utilities.class);
    private static Boolean flagIsBackoffice = null;
    public static final String ALGORITHM_MD5 = "MD5";
    public static final String ALGORITHM_SHA1 = "SHA1";
    public static final String ALGORITHM_SHA256 = "SHA-256";
    public static final int BUFFER_SIZE = 1024 * 512;

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
     * @return la stringa codificata secondo l'algoritmo specificato espressa in formato esadecimale
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

    public static String computeHashFromStream(InputStream is, String algorithmType) {

	byte[] mdbytes = null;
	ReadableByteChannel channel = null;
	try {
	    MessageDigest md = MessageDigest.getInstance(algorithmType);
	    channel = Channels.newChannel(is);
	    ByteBuffer bb = ByteBuffer.allocate(BUFFER_SIZE);
	    int nread = 0;
	    while ((nread = channel.read(bb)) != -1) {
		bb.flip();
		md.update(bb);
		bb.clear();
	    }
	    mdbytes = md.digest();
	} catch (IOException e) {
	    throw new RuntimeException(e);
	} catch (NoSuchAlgorithmException e) {
	    throw new RuntimeException(e);
	} finally {
	    if (channel != null && channel.isOpen()) {
		try {
		    channel.close();
		} catch (IOException e) {
		    throw new RuntimeException(e);
		}
	    }
	}
	return new String(mdbytes);
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
     * concatenazione dei caratteri ascii associati ai numeri gerenati casualmente
     * 
     * @param lunghezza
     *            :lunghezza della password
     * @return
     */
    public static String generaPassword(Integer lunghezza) {

	if (lunghezza == null) {
	    lunghezza = Integer.valueOf(8);
	}
	StringBuffer pass = new StringBuffer();
	int i = 1;
	while (i <= lunghezza) {
	    int x = (RandomUtils.nextInt(123));
	    if ((x > 96 && x < 123) || (x > 47 && x < 58)) {
		pass.append((char) x);
		i = i + 1;
	    }
	}
	return pass.toString();
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
	Calendar c = Calendar.getInstance();
	c.setTime(dateToDecrement);
	c.roll(Calendar.DATE, amount);
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
     * Restituisce la rappresentazione stringa migliore dell'oggetto passato a seconda del tipo
     * TODO gestire altri tipi se si rende necessario
     * @param val
     * @return
     */
    public static String valueAsString(Object val){
	
	String retVal = "";
	if(val != null){
	    if(val instanceof Date){
		retVal = formatDate((Date)val, false);
	    }
	    else if(val instanceof BigDecimal){
		BigDecimal bdVal = (BigDecimal)val;
		retVal = formatImporto(bdVal, bdVal.scale(), bdVal.scale(), false);
	    }
	    else {
		retVal = val.toString();
	    }
	}
	return retVal;
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

    public static Date parseDateStringFormatAAAAmmGG(String d, boolean formatDateAndTime) {

	String anno = d.substring(0, 4);
	String mese = d.substring(4, 6);
	String giorno = d.substring(6, 8);
	String _data = giorno + "/" + mese + "/" + anno;
	Date _d = null;
	if (d != null) {
	    try {
		String pattern = formatDateAndTime ? WebConstants.DATE_WITH_TIME_FORMAT_PATTERN : WebConstants.DATE_FORMAT_PATTERN;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		_d = sdf.parse(_data);
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
		sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    }
	    Date d = sdf.parse(date);
	    gd.setTime(d);
	} catch (ParseException e) {
	    log.error("getDate(): {}", e.getMessage());
	}
	return gd;
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

    public static XMLGregorianCalendar getTodayDateTime() {

	XMLGregorianCalendar calendar;
	try {
	    calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
	    Calendar todayCal = Calendar.getInstance();
	    calendar.setDay(todayCal.get(Calendar.DATE));
	    // DA RICORDARSI: il Mese per XmlGregorianCalendar parte da 1 e non da 0 come GregorianCalendar
	    calendar.setMonth(todayCal.get(Calendar.MONTH) + 1);
	    calendar.setYear(todayCal.get(Calendar.YEAR));
	    calendar.setHour(todayCal.get(Calendar.HOUR_OF_DAY));
	    calendar.setMinute(todayCal.get(Calendar.MINUTE));
	    calendar.setSecond(todayCal.get(Calendar.SECOND));
	    calendar.setMillisecond(todayCal.get(Calendar.MILLISECOND));
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
     * @param datanascita
     * @return
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

    public static BigDecimal parseBigDecimal(String input) {

	BigDecimal val = null;
	if(StringUtils.isNotBlank(input)){
	    DecimalFormat nf = new DecimalFormat("");
	    try {
		//val = NumberUtils.createBigDecimal(input);
		Number numVal = nf.parse(input);
		val = new BigDecimal(numVal.doubleValue());
	    } catch (ParseException e) {
		throw new RuntimeException("Non è stato possibile costruire un oggetto BigDecimal a partire dal valore stringa: \"" + input + "\" a causa di:" + e.getMessage(), e);
	    }
	}
	return val;
    }
    /**
     * <pre>
     * Il metodo ritorna l'annotation associata al metodo passato
     * 
     * @param <T>
     * @param clazz
     *            : Classe dove si trova l'annotation che si vuole ricavare
     * @param clazzAnnotation
     *            : Classe dell'annotation che vogliamo ricavare
     * @param method
     *            : medoto di cui vogliamo ricavare la specifica annotation
     * @return    :ritorna un istanza della classe <b>clazzAnnotation</b>
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
     * @return <ul>
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
     * @return <ul>
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
     * @param date1
     * @param date2
     * @return ritorna true se sono uguali, false se sono diverse, IllegalArgumentException se la date1 è null
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

	List<File> unzippedFiles = new ArrayList<File>();
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
		unzippedFiles.add(unzipped);
	    }
	}
	zis.close();
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
    public static DataHandler bytesToDataHandler(byte[] content) {

	try {
	    DataSource ds = new ByteArrayDataSource(content, "application/octet-stream");
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

    private static final Pattern FILENAME_ALLOWED_CHARS = Pattern.compile("(\\.?[^A-Za-z0-9\\.\\-\\_]+)");
    private static final String PATH_SEPARATOR_CHARS = "[\\\\/]";
    /**
     * String da usare per indicare una stringa vuota senza dover usare "" (che crea un nuovo oggetto stringa)
     */
    public static final String EMPTY_CACHED_FINAL_STRING = "_";

    public static String correggiNomeFile(String nomeFile) {

	//se la stringa passata contiene anche il path del file, viene prima eliminato (in IE i campi file postano l'intero path locale del file selezionato)
	String[] pathElems = nomeFile.split(PATH_SEPARATOR_CHARS);
	if(pathElems.length > 0){
	    nomeFile = pathElems[pathElems.length -1];
	}
	return FILENAME_ALLOWED_CHARS.matcher(StringUtils.defaultString(nomeFile)).replaceAll(EMPTY_CACHED_FINAL_STRING);
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

    public static void main(String[] args) {

	System.out.println(isEmptyString(null, true));
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

    public static String getValueFromXml(String xml, String xpathParam) throws ParserConfigurationException, SAXException, IOException,
	    XPathExpressionException {

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
	    in = Utilities.class.getClassLoader().getResource(filename).openStream();
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

    public static Oggetti convertXmlToPdf(Oggetti xml, InputStream xsltStream) throws Exception, IOException {

	byte[] out = getHTML(xml, xsltStream);
	FileConverterWsClient fileConverterWService = new FileConverterWsClient();
	ConvertBinaryRequest cbr = new ConvertBinaryRequest(ORMHelper.getToken(), out, "HTM", "PDF");
	Oggetti oggetti = new Oggetti();
	try {
	    ConvertBinaryResponse cResp = fileConverterWService.convertBinary(cbr);
	    oggetti.setDimensioneFile(cResp.getBinaryData().length);
	    oggetti.setNomefile("Durc");
	    oggetti.setOggetto(cResp.getBinaryData());
	} catch (RemoteException e) {
	    throw new RuntimeException("Errore nella conversione del file in PDF:" + e.getMessage(), e);
	}
	return oggetti;
    }

    /**
     * Trasforma una stringa xml in html tramite un atrasformazione xslt
     * 
     * @param xml
     * @return
     */
    public static byte[] getHTML(Oggetti xml, InputStream xsltStream) {

	//InputStream in = null;
	try {
	    //in = this.getClass().getClassLoader().getResource(xsltPath).openStream();
	    byte[] xslBytes = IOUtils.toByteArray(xsltStream);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer = tFactory.newTransformer(new StreamSource(new ByteArrayInputStream(xslBytes)));
	    transformer.transform(new StreamSource(new ByteArrayInputStream(xml.getOggetto())), new StreamResult(baos));
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

    private static final String MAC_QUERY_STRING_PARAM = "&_MAC_=";
    private static final String CHIAVE_SEGRETA = "SECRET_" + System.currentTimeMillis();

    public static String getLinkForFile(String queryString) {

	String mac = encode(queryString + CHIAVE_SEGRETA);
	return queryString + MAC_QUERY_STRING_PARAM + mac;
    }

    public static boolean verificaLinkFile(String queryStringWithMAC) {

	String qsOrig = queryStringWithMAC.substring(0, queryStringWithMAC.indexOf(MAC_QUERY_STRING_PARAM));
	String mac = encode(qsOrig + CHIAVE_SEGRETA);
	String macQs = queryStringWithMAC.substring(queryStringWithMAC.indexOf(MAC_QUERY_STRING_PARAM)).replace(MAC_QUERY_STRING_PARAM, "");
	return macQs.equalsIgnoreCase(mac);
    }

    private static String encode(String toEncode) {

	try {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update((toEncode).getBytes());
	    byte[] out = md.digest();
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < out.length; i++) {
		sb.append(Integer.toString((out[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    return sb.toString();
	} catch (NoSuchAlgorithmException e) {
	    throw new RuntimeException(e);
	}
    }

    public static String calcolaMd5SUM(byte[] content) throws IOException {

	String md5Val = "";
	InputStream is = new ByteArrayInputStream(content);
	if (log.isDebugEnabled()) {
	    log.debug("calcolaMd5# InpuntStream recuperato {}, calcolo MD5", (is == null ? Boolean.FALSE : Boolean.TRUE));
	}
	if (is == null) {
	    throw new IOException("Non è stato possibile estrarre il contenuto del file");
	}
	try {
	    md5Val = DigestUtils.md5Hex(is);
	} catch (IOException e) {
	    log.error("calcolaMd5# Errore nel calcolo del checksum MD5 {}", e);
	    throw e;
	}
	if (log.isDebugEnabled()) {
	    log.debug("calcolaMd5# md5 calcolato del file {}", md5Val);
	}
	return md5Val;
    }

    public static boolean isEmptyString(String s, boolean isTrim) {

	if (s == null) {
	    return true;
	}
	if (isTrim) {
	    return s.trim().length() == 0;
	} else {
	    return s.length() == 0;
	}
    }

    public static String stripNonPrintableCharacters(String sourceString) {

	if (StringUtils.isNotBlank(sourceString)) {
	    sourceString = sourceString.replaceAll("\\p{C}", "");
	}
	return sourceString;
    }
    
    public static String stripSpecialCharacters(String sourceString) {

	if (StringUtils.isNotBlank(sourceString)) {
	    sourceString = sourceString.replaceAll("[^a-zA-Z_0-9\\-]", "");
	}
	return sourceString;
    }
    
    public static String stripHtmlTags(String sourceString) {

	if (StringUtils.isNotBlank(sourceString)) {
	    sourceString = sourceString.replaceAll("\\<.*?>", "");
	}
	return sourceString;
    }
    
    public static String stripBlankSpaces(String sourceString,String replaceWith) {

	if (StringUtils.isNotBlank(sourceString)) {
	    sourceString = sourceString.replaceAll("\\s+", replaceWith);
	}
	return sourceString;
    }
}
