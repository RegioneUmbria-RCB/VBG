package it.gruppoinit.pdd.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.activation.MimetypesFileTypeMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.soap.Detail;
import javax.xml.soap.DetailEntry;
import javax.xml.soap.SOAPFault;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.staxutils.StaxUtils;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class Utilities {

    private static MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

    public static XMLGregorianCalendar convertFromDate(Date d) {

	GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
	c.setTime(d);
	return convertFromGregorianCalendar(c);
    }

    public static XMLGregorianCalendar convertFromGregorianCalendar(GregorianCalendar c) {

	XMLGregorianCalendar anno = null;
	try {
	    anno = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
	} catch (DatatypeConfigurationException e) {
	    Utilities.logAndThrowException("Errore nella trasformazione di  xmlgregoriancalendar [" + c + "] a causa di " + e.getMessage(), e,
		    Utilities.class);
	}
	return anno;
    }

    /**
     * crea una cartella temporanea partendo dalla
     * "java.io.tmpdir"/NLA-PDD-RI/idcomunealias/codiceIstanza-yyyy-MM-dd_hh-mm-ss-S<br />
     * Es.: <b>C:\DOCUME~1\RICCAR~1\IMPOST~1\Temp\NLA-PDD-RI\E256\123-2012-10-19_12-29-01-194</b> <br />
     * Se idcomunealias e codiceIstanza non sono definiti il loro valore viene sostituito con ND (Non definito)
     * 
     * @param idcomunealias
     * @param codiceIstanza
     * @return
     */
    public static File createPDDTmpDir(String idcomunealias, String codiceIstanza) {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss-S");
	File tmpDir = new File(System.getProperty("java.io.tmpdir"));
	if (!tmpDir.isDirectory()) {
	    tmpDir.mkdirs();
	}
	tmpDir = new File(tmpDir, "NLA-PDD-RI");
	if (!tmpDir.isDirectory()) {
	    tmpDir.mkdirs();
	}
	tmpDir = new File(tmpDir, StringUtils.defaultString(idcomunealias, "ND"));
	if (!tmpDir.isDirectory()) {
	    tmpDir.mkdirs();
	}
	tmpDir = new File(tmpDir, (StringUtils.defaultString(codiceIstanza, "ND") + "-" + sdf.format(new Date())));
	if (!tmpDir.isDirectory()) {
	    tmpDir.mkdirs();
	}
	return tmpDir;
    }

    /**
     * Torna il mime type associato al nomefile (La mappa delle configurazioni è nel jar _mime-types.jar).<br />
     * Se file è nullo o stringa vuota torna stringa vuota
     * 
     * @param nomeFile
     * @return
     */
    public static String getContentType(String nomeFile) {

	if (nomeFile != null) {
	    return mimetypesFileTypeMap.getContentType(nomeFile);
	}
	return "";
    }

    /**
     * Torna il mime type associato al file (La mappa delle configurazioni è nel jar _mime-types.jar)<br />
     * Se file è nullo o stringa vuota torna stringa vuota
     * 
     * @param nomeFile
     * @return
     */
    public static String getContentType(File file) {

	if (file != null) {
	    return mimetypesFileTypeMap.getContentType(file);
	}
	return "";
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

    public static String marshallObject(Object obj) throws JAXBException, UnsupportedEncodingException {

	StringWriter stringWriter = new StringWriter();
	JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
	Marshaller marshaller = jaxbContext.createMarshaller();
	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
	marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	marshaller.marshal(obj, stringWriter);
	return stringWriter.toString();
    }

    /**
     * Torna una stringa leggibile del dettaglio di un SoapFault <br />
     * Nel caso di SpcoopException torna anche l'xml del dettaglio di errore.<br />
     * Es. <code>
     * <pre>
       &lt;eGov_IT_Ecc:MessaggioDiErroreApplicativo 
              xmlns:eGov_IT_Ecc="http://www.cnipa.it/schemas/2003/eGovIT/Exception1_0/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
              &lt;OraRegistrazione>2010-08-27T17:21:25.195&lt;/OraRegistrazione>
              &lt;IdentificativoPorta>OpenSPCoopSPCoopIT&lt;/IdentificativoPorta>
              &lt;IdentificativoFunzione>RicezioneContenutiApplicativi_PD&lt;/IdentificativoFunzione>
              &lt;Eccezione>
                &lt;EccezioneProcessamento codiceEccezione="OPENSPCOOP_ORG_401" descrizioneEccezione="La porta 
                  delegata invocata non esiste location[GetDatecdcdcdcccd] urlInvocazione[GetDatecdcdcdcccd]"/>
              &lt;/Eccezione>
            &lt;/eGov_IT_Ecc:MessaggioDiErroreApplicativo>    
    </pre>
     * </code>
     * 
     * @param fault
     * @return
     */
    public static String soapFaultToString(SOAPFault fault) {

	String errore = "Ricevuto Messaggio di Errore Applicativo [" + fault.getFaultCode() + "]: " + fault.getFaultString();
	Detail detail = fault.getDetail();
	Iterator<DetailEntry> it = detail.getDetailEntries();
	while (it.hasNext()) {
	    DetailEntry de = (DetailEntry) it.next();
	    if (de != null) {
		if ("MessaggioDiErroreApplicativo".equalsIgnoreCase(StringUtils.defaultString(de.getLocalName()))) {
		    String xml = StaxUtils.toString(de);
		    errore += "\nDettagli Errore Applicativo:\n " + xml;
		}
		if ("ErroreValidazione".equalsIgnoreCase(StringUtils.defaultString(de.getLocalName()))) {
		    String xml = StaxUtils.toString(de);
		    errore += "\nDettagli Errore Validazione:\n " + xml;
		}
	    }
	}
	return errore;
    }

    public static Object unMarshallString(String xml, Class<?> clazz) throws JAXBException, UnsupportedEncodingException {

	JAXBContext jc = JAXBContext.newInstance(clazz);
	Unmarshaller u = jc.createUnmarshaller();
	Object response = u.unmarshal(new ByteArrayInputStream(xml.getBytes("UTF-8")));
	return response;
    }

    /**
     * Carica l'xsd dello schema con il metodo XmlUtils.class.getClassLoader().getResource(xsdUrl);
     * 
     * @param xsdUrl
     * @return
     */
    public static Schema getSchemaForMessage(String xsdUrl) {

	try {
	    SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
	    Schema compiledSchema = factory.newSchema(Utilities.class.getClassLoader().getResource(xsdUrl));
	    return compiledSchema;
	} catch (Exception e) {
	    throw new RuntimeException("Non è stato possibile creare l'oggetto schema all'url " + xsdUrl, e);
	}
    }

    /**
     * Valida l'xml tramite un' oggetto schema. se schema è nullo non fa niente.<br />
     * In caso di errore nella validazione rilancia runtimeException
     * 
     * @param xml
     * @param schema
     */
    public static void validaXml(Source xml, Schema schema) {

	if (schema != null) {
	    Validator validator = schema.newValidator();
	    try {
		validator.validate(xml);
	    } catch (SAXException e) {
		throw new RuntimeException(e);
	    } catch (IOException e) {
		throw new RuntimeException(e);
	    }
	}
    }
}
