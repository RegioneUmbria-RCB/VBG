package it.gruppoinit.utils;

import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.ValoreParametroType;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.sql.rowset.serial.SerialException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author riccardob
 *
 */
public class Utils {

    public static boolean isPresenteParametroDaAltriDatiFromRequest(String nomeParametro, InserimentoAttivitaNLARequest request) {

	List<ParametroType> altriDati = request.getDatiAttivita().getAltriDati();
	ParametroType tipoOpParam = null;
	for (ParametroType param : altriDati) {
	    if (param.getNome().equalsIgnoreCase(nomeParametro)) {
		tipoOpParam = param;
		break;
	    }
	}
	return (tipoOpParam != null);
    }

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

    public static String getParametroDaAltriDatiFromRequest(String nomeParametro, InserimentoAttivitaNLARequest request) {

	String retVal = null;
	List<ParametroType> altriDati = request.getDatiAttivita().getAltriDati();
	ParametroType tipoOpParam = null;
	for (ParametroType param : altriDati) {
	    if (param.getNome().equalsIgnoreCase(nomeParametro)) {
		tipoOpParam = param;
		break;
	    }
	}
	if (tipoOpParam != null) {
	    List<ValoreParametroType> paramValues = tipoOpParam.getValore();
	    if (paramValues.size() > 0) {
		retVal = paramValues.get(0).getCodice();
	    }
	}
	return retVal;
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

    public static String formatDate(Date d, String formatDateAndTimePattern) {

	String _d = "";
	if (d != null) {
	    try {
		SimpleDateFormat sdf = new SimpleDateFormat(formatDateAndTimePattern);
		_d = sdf.format(d);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	return _d;
    }

    public static byte[] readBytesFromStream(FileInputStream fileInputStream) throws IOException {

	StringWriter writer = new StringWriter();
	IOUtils.copy(fileInputStream, writer, "UTF-8");
	String theString = writer.toString();
	return theString.getBytes();
    }

    public static String marshallObject(Object obj, String pathPackageObjectfactory) {

	StringWriter stringWriter = new StringWriter();
	try {
	    //	    JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
	    JAXBContext jaxbContext = JAXBContext.newInstance(pathPackageObjectfactory);
	    Marshaller marshaller = jaxbContext.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
	    marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    marshaller.marshal(obj, stringWriter);
	    return stringWriter.toString();
	} catch (Exception e1) {
	    throw new RuntimeException(e1);
	}
    }

    public static Object unMarshallString(String xml, Class<?> clazz, String path) {

	try {
	    //JAXBContext jc = JAXBContext.newInstance(clazz, it.elisa.factory.civico.ObjectFactory.class);
	    JAXBContext jc = JAXBContext.newInstance(path);
	    Unmarshaller u = jc.createUnmarshaller();
	    Object response = u.unmarshal(new ByteArrayInputStream(xml.getBytes("UTF-8")));
	    return response;
	} catch (Exception e1) {
	    throw new RuntimeException(e1);
	}
    }

    public static Object unMarshallInputStream(InputStream in, Class<?> clazz) {

	try {
	    JAXBContext jc = JAXBContext.newInstance(clazz);
	    Unmarshaller u = jc.createUnmarshaller();
	    Object response = u.unmarshal(in);
	    return response;
	} catch (Exception e1) {
	    throw new RuntimeException(e1);
	}
    }

    public static String marshallFile(Object obj, URI pathFileDB) {

	StringWriter stringWriter = new StringWriter();
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
	    Marshaller marshaller = jaxbContext.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
	    marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    //File file=new File(uri)
	    marshaller.marshal(obj, new File(pathFileDB));
	    return stringWriter.toString();
	} catch (Exception e1) {
	    throw new RuntimeException(e1);
	}
    }

    private static final Pattern NON_ASCII_PATTERN = Pattern.compile("[^\\x20-\\x7e]");
    private static final Pattern FILENAME_ALLOWED_CHARS = Pattern.compile("(\\.?[^A-Za-z0-9\\.\\-\\_]+)");
    public static final String EMPTY_CACHED_FINAL_STRING = "_";

    public static String eliminaCaratteriNonAscii(String nomeFile) {

	String ret = NON_ASCII_PATTERN.matcher(StringUtils.defaultString(nomeFile)).replaceAll(EMPTY_CACHED_FINAL_STRING);
	ret = FILENAME_ALLOWED_CHARS.matcher(StringUtils.defaultString(ret)).replaceAll(EMPTY_CACHED_FINAL_STRING);
	return ret;
    }

    /// SQL UTILS
    public static Integer getIntegerOrNull(int indiceRs, ResultSet rs) throws SQLException {

	if (rs.getObject(indiceRs) != null) {
	    return rs.getInt(indiceRs);
	}
	return null;
    }

    public static void setIntOrNUll(Integer o, PreparedStatement ps, int indexSuStatement) throws SQLException {

	if (o == null) {
	    ps.setNull(indexSuStatement, Types.INTEGER);
	} else {
	    ps.setInt(indexSuStatement, o);
	}
    }

    public static void setBlobOrNUll(InputStream o, PreparedStatement ps, int indexSuStatement) throws SQLException {

	if (o == null) {
	    ps.setNull(indexSuStatement, Types.BLOB);
	} else {
	    // ps.setBlob(indexSuStatement, o);
	    ps.setBinaryStream(indexSuStatement, o);
	}
    }

    public static void setDateOrNUll(java.sql.Date o, PreparedStatement ps, int indexSuStatement) throws SQLException {

	if (o == null) {
	    ps.setNull(indexSuStatement, Types.DATE);
	} else {
	    ps.setDate(indexSuStatement, o);
	}
    }

    private static void graceFullyClose(PreparedStatement ps) {

	try {
	    ps.close();
	} catch (Exception e) {
	}
    }

    private static void graceFullyClose(ResultSet rs) {

	try {
	    rs.close();
	} catch (Exception e) {
	}
    }

    private static void graceFullyClose(Connection c) {

	try {
	    c.close();
	} catch (Exception e) {
	}
    }

    public static void closeObjects(Object... o) {

	for (Object object : o) {
	    if (object instanceof ResultSet) {
		graceFullyClose((ResultSet) object);
	    } else if (object instanceof PreparedStatement) {
		graceFullyClose((PreparedStatement) object);
	    } else if (object instanceof Connection) {
		graceFullyClose((Connection) object);
	    }
	}
    }

    public static String readBlobAsString(Blob script) throws SQLException {

	if (script != null) {
	    byte[] b = script.getBytes(1L, (int) script.length());
	    return Base64.encodeBase64String(b);
	}
	return null;
    }

    public static InputStream decodeBase64ToBlob(String scriptBase64) throws SerialException, SQLException {

	if (StringUtils.isBlank(scriptBase64)) {
	    return null;
	}
	return new ByteArrayInputStream(Base64.decodeBase64(scriptBase64));
    }

    public static String decodeBase64ToString(String scriptCodeBase64) {

	if (StringUtils.isBlank(scriptCodeBase64)) {
	    return null;
	}
	return new String(Base64.decodeBase64(scriptCodeBase64));
    }
}
