package it.gruppoinit.utilities;

import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.ValoreParametroType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utilities {

    private static final Logger log = LoggerFactory.getLogger(Utilities.class);

    public static String getValoreAltroDato(InserimentoAttivitaNLARequest request, String nomeParametro) {

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

    public static String getTipoOperazioneFromRequest(InserimentoAttivitaNLARequest request) {

	String retVal = null;
	List<ParametroType> altriDati = request.getDatiAttivita().getAltriDati();
	ParametroType tipoOpParam = null;
	for (ParametroType param : altriDati) {
	    if (param.getNome().equalsIgnoreCase(Campi.NLAREQUEST_ALTRIDATI_PARAMNAME_TIPOOPERAZIONE)) {
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
	//	try {
	//	    //JAXBContext jc = JAXBContext.newInstance(clazz, it.elisa.factory.civico.ObjectFactory.class);
	//	    JAXBContext jc = JAXBContext.newInstance("it.gruppoinit.ws.wsatti");
	//	    Unmarshaller u = jc.createUnmarshaller();
	//	    Object response = u.unmarshal(new ByteArrayInputStream(xml.getBytes("UTF-8")));
	//	    return response;
	//	} catch (Exception e1) {
	//	    throw new RuntimeException(e1);
	//	}
    }

    public static void gracefullyReleaseResources(Connection c, PreparedStatement pstmt, ResultSet rs) {

	if (c != null) {
	    try {
		c.close();
	    } catch (Exception e) {
	    }
	}
	if (pstmt != null) {
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
	if (rs != null) {
	    try {
		rs.close();
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

    public static java.sql.Date getDate(Date date) {

	//java.util.Date date = new java.util.Date();
	return new java.sql.Date(date.getTime());
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
}
