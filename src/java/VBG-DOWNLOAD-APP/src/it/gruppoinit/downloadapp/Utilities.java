package it.gruppoinit.downloadapp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.activation.DataHandler;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

public class Utilities {

    // 8-byte Salt
    static byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };
    // Iteration count
    static int iterationCount = 19;
    public static final String DEFAULT_SECRET_KEY = "$1REDACTED";

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

    public static String getGCDate(GregorianCalendar cal, String pattern) {

	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	return sdf.format(cal.getTime());
    }

    public static String formatDate(Date d, String formatDateAndTimePattern) {

	String _d = "";
	if (d != null) {
	    try {
		SimpleDateFormat sdf = new SimpleDateFormat(formatDateAndTimePattern);
		_d = sdf.format(d);
	    } catch (Exception e) {
	    }
	}
	return _d;
    }

    /*
    private static Map<String, String> _comuni = null;
    
    private static void populateComuni() {
    
    if (_comuni != null) {
        if (_comuni.size() > 0) {
    	return;
        }
    }
    _comuni = new HashMap<String, String>();
    InputStream inputStream = Utilities.class.getClassLoader().getResourceAsStream("COMUNI.properties");
    try {
        Properties comuni = new Properties();
        comuni.load(inputStream);
        for (Entry<Object, Object> entry : comuni.entrySet()) {
    	_comuni.put((String) entry.getKey(), (String) entry.getValue());
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
    public static String getComuneFromCodiceCatastale(String codiceCatastale) {
    
    populateComuni();
    return _comuni.get(codiceCatastale);
    }
    
    public void reloadComuni() {
    
    this._comuni = null;
    }
    */
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
		sdf = new SimpleDateFormat("yyyyMMdd");
	    }
	    Date d = sdf.parse(date);
	    gd.setTime(d);
	} catch (ParseException e) {
	}
	return gd;
    }

    public static boolean matchMd5File(InputStream binaryData, String md5) throws IOException {

	String md5object = DigestUtils.md5Hex(binaryData);
	if (md5object.equalsIgnoreCase(md5)) {
	    return true;
	}
	return false;
    }

    public static byte[] toBytes(DataHandler dh) throws IOException {

	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	InputStream in = dh.getInputStream();
	byte[] buffer = new byte[1024];
	int bytesRead;
	while ((bytesRead = in.read(buffer)) >= 0) {
	    bos.write(buffer, 0, bytesRead);
	}
	return bos.toByteArray();
    }

    /**
     * md5 deve essere verificato con md5 di DigestUtils.md5Hex(alias + "-" + uuidistanza + "-" + codice_movimento_pin);
     * 
     * @param alias
     * @param uuidistanza
     * @param codice_movimento_pin
     * @param md5_movimento_istanza
     * @return
     * @throws UnsupportedEncodingException
     */
    public static boolean verificaDownloadZip(String alias, String uuidistanza, String codice_movimento_pin, String md5_movimento_istanza)
	    throws UnsupportedEncodingException {

	String calcolato = new String((alias + "-" + uuidistanza + "-" + codice_movimento_pin).getBytes(), "UTF-8");
	String md5object = DigestUtils.md5Hex(calcolato);
	return StringUtils.defaultString(md5_movimento_istanza).equalsIgnoreCase(md5object);
    }

    /**
     *
     * @param secretKey
     *            Key used to encrypt data
     * @param plainText
     *            Text input to be encrypted
     * @return Returns encrypted text
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws java.security.InvalidAlgorithmParameterException
     * @throws java.io.UnsupportedEncodingException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     *
     */
    public static String encrypt(String secretKey, String plainText) {

	try {
	    //Key generation for enc and desc
	    KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
	    SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
	    // Prepare the parameter to the ciphers
	    AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
	    //Enc process
	    Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
	    ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
	    String charSet = "UTF-8";
	    byte[] in = plainText.getBytes(charSet);
	    byte[] out = ecipher.doFinal(in);
	    //String encStr = new String(Base64.getEncoder().encode(out));
	    String encStr = new String(Base64.encodeBase64(out));
	    return encStr;
	} catch (Exception e) {
	    String msg = "errore nella codifica della stringa " + plainText + " con la chiave segreta";
	    throw new RuntimeException(msg, e);
	}
    }

    /**
     * @param secretKey
     *            Key used to decrypt data
     * @param encryptedText
     *            encrypted text input to decrypt
     * @return Returns plain text after decryption
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws java.security.InvalidAlgorithmParameterException
     * @throws java.io.UnsupportedEncodingException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     */
    public static String decrypt(String secretKey, String encryptedText) {

	try {
	    //Key generation for enc and desc
	    KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
	    SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
	    // Prepare the parameter to the ciphers
	    AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
	    //Decryption process; same key will be used for decr
	    Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
	    dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
	    //byte[] enc = Base64.getDecoder().decode(encryptedText);
	    byte[] enc = Base64.decodeBase64(encryptedText);
	    byte[] utf8 = dcipher.doFinal(enc);
	    String charSet = "UTF-8";
	    String plainStr = new String(utf8, charSet);
	    return plainStr;
	} catch (Exception e) {
	    //String msg = "errore nella decodifica della stringa " + encryptedText + " con la chiave segreta";
	    String msg = "Errore di Autenticazione";
	    throw new RuntimeException(msg, e);
	}
    }
}
