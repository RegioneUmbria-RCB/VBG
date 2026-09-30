package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.infocamere.schema.legaldocs.ConservazioneResponseHelper;
import it.gruppoinit.infocamere.schema.legaldocs.Error;
import it.gruppoinit.infocamere.schema.legaldocs.LegaldocConnectorTrigger;
import it.gruppoinit.infocamere.schema.legaldocs.LoginResponse;
import it.gruppoinit.infocamere.schema.legaldocs.index.LegaldocIndex;
import it.gruppoinit.infocamere.schema.legaldocs.index.LegaldocIndex.Field;
import it.gruppoinit.infocamere.schema.legaldocs.parametri.Parameters;
import it.gruppoinit.infocamere.schema.legaldocs.parametri.Parameters.IndexFile;
import it.gruppoinit.infocamere.schema.legaldocs.pindex.PIndex;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.firmadigitale.FileNonFirmatoException;
import it.gruppoinit.pal.gp.core.rest.client.DSSRestClient;
import it.gruppoinit.pal.gp.core.rest.client.models.dss.ValidationResultDTO;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchiviazioneDocumentaleXMLHelper {

    public static final Logger log = LoggerFactory.getLogger(ArchiviazioneDocumentaleXMLHelper.class);

    public static String getFileIndice(Set<ArchiviazioneDocumentaleIndice> indici,
	    VerticalizzazioneArchiviazioneDocumentale verticalizzazioneArchiviazioneDocumentale) throws Exception {

	String buff = "";
	try {
	    it.gruppoinit.infocamere.schema.legaldocs.index.ObjectFactory objectFactory = new it.gruppoinit.infocamere.schema.legaldocs.index.ObjectFactory();
	    LegaldocIndex legaldocIndex = objectFactory.createLegaldocIndex();
	    legaldocIndex.setLabel(verticalizzazioneArchiviazioneDocumentale.getLEGALDOC_INDEX_LABEL());
	    legaldocIndex.setDocumentClass(verticalizzazioneArchiviazioneDocumentale.getLEGALDOC_INDEX_DOCUMENT_CLASS());
	    Field field = null;
	    for (ArchiviazioneDocumentaleIndice indice : indici) {
		field = new Field();
		field.setLabel(indice.getLabel());
		field.setName(indice.getNome());
		field.setValue(indice.getValore());
		legaldocIndex.getField().add(field);
	    }
	    buff = Utilities.marshallObject(legaldocIndex);
	} catch (Exception e) {
	    log.error("getFileIndice", e);
	    throw e;
	}
	return buff;
    }

    public static void writeFileAvvioConservazioneSuCartella(ArchiviazioneDocumentaleFileAvvio fAvvio, String folderName) throws Exception {

	try {
	    File fileAvvio = new File(folderName, "legaldoc_connector_trigger_invio.xml");
	    JAXBContext jc = JAXBContext.newInstance("it.gruppoinit.infocamere.schema.legaldocs");
	    Marshaller marshaller = jc.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    marshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
	    LegaldocConnectorTrigger trigger = populateLegaldocConnectorTriggerConservazioneSuCartella(fAvvio);
	    marshaller.marshal(trigger, fileAvvio);
	} catch (Exception e) {
	    log.error("writeFileAvvioConservazioneSuCartella", e);
	    throw e;
	}
    }

    public static String writeFileAvvioConservazioneSerizioRest(ArchiviazioneDocumentaleFileAvvio fAvvio, String hashFileIndiceContetnt)
	    throws Exception {

	String r = "";
	try {
	    Parameters trigger = populateLegaldocConnectorTriggerConservazioneServizioRest(fAvvio, hashFileIndiceContetnt);
	    r = Utilities.marshallObject(trigger);
	} catch (Exception e) {
	    log.error("writeFileAvvioConservazioneSerizioRest", e);
	    throw e;
	}
	return r;
    }

    public static Parameters populateLegaldocConnectorTriggerConservazioneServizioRest(ArchiviazioneDocumentaleFileAvvio fAvvio,
	    String hashFileIndiceContetnt) {

	it.gruppoinit.infocamere.schema.legaldocs.parametri.ObjectFactory objFactory = new it.gruppoinit.infocamere.schema.legaldocs.parametri.ObjectFactory();
	Parameters trigger = objFactory.createParameters();
	trigger.setPolicyId(fAvvio.getPolicy());
	IndexFile indexFile = new IndexFile();
	for (ChiaveValoreBean<String, DocumentoLegalDocHelper> _doc : fAvvio.getDocumentList()) {
	    indexFile.setIndexName(_doc.getChiave());
	    indexFile.setIndexHash(hashFileIndiceContetnt);
	    indexFile.setIndexMimetype("text/xml;1.0");
	    trigger.setIndexFile(indexFile);
	    //.
	    it.gruppoinit.infocamere.schema.legaldocs.parametri.Parameters.DataFile dataFile = new it.gruppoinit.infocamere.schema.legaldocs.parametri.Parameters.DataFile();
	    dataFile.setDataName(_doc.getValore().getFileName());
	    dataFile.setDataHash(_doc.getValore().getHashFile());
	    String mt = getMimeType(_doc.getValore().getFileName().toLowerCase());
	    if (mt != null) {
		dataFile.setDataMimetype(mt);
	    }
	    trigger.setDataFile(dataFile);
	}
	trigger.setPath(fAvvio.getPath());
	///
	return trigger;
    }

    public static LegaldocConnectorTrigger populateLegaldocConnectorTriggerConservazioneSuCartella(ArchiviazioneDocumentaleFileAvvio fAvvio) {

	throw new NotImplementedException("Implemenazione salvataggio su cartella non più attivo");
	//	ObjectFactory objFactory = new ObjectFactory();
	//	LegaldocConnectorTrigger trigger = objFactory.createLegaldocConnectorTrigger();
	//	trigger.setDescription(fAvvio.getNome());
	//	trigger.setService(fAvvio.getService());
	//	if (StringUtils.isNotBlank(fAvvio.getFeedbackEmail())) {
	//	    trigger.setFeedbackEmail(fAvvio.getFeedbackEmail());
	//	}
	//	DocumentList docList = objFactory.createDocumentList();
	//	ServiceParameters servParams = objFactory.createServiceParameters();
	//	for (ChiaveValoreBean<String, DocumentoLegalDocHelper> _doc : fAvvio.getDocumentList()) {
	//	    Document doc = new Document();
	//	    doc.setIndexFile(_doc.getChiave());
	//	    DataFile df = new DataFile();
	//	    df.setValue(_doc.getValore().getFileName());
	//	    //fabrizioc: richiesta della Mazzoni
	//	    String mt = getMimeType(df.getValue().toLowerCase());
	//	    if (mt != null) {
	//		df.setMimeType(mt);
	//	    }
	//	    //
	//	    doc.setDataFile(df);
	//	    docList.getDocument().add(doc);
	//	}
	//	trigger.setDocumentList(docList);
	//	Parameter operation = new Parameter();
	//	operation.setId("operation");
	//	operation.setValue(fAvvio.getOperation());
	//	servParams.getParameter().add(operation);
	//	trigger.setServiceParameters(servParams);
	//	Parameter bucket = new Parameter();
	//	bucket.setId("bucket");
	//	bucket.setValue(fAvvio.getBucket());
	//	trigger.getServiceParameters().getParameter().add(bucket);
	//	Parameter policy = new Parameter();
	//	policy.setId("policy");
	//	policy.setValue(fAvvio.getPolicy());
	//	trigger.getServiceParameters().getParameter().add(policy);
	//	return trigger;
    }

    public static String getIdSession(VerticalizzazioneArchiviazioneDocumentale vad) {

	String idSession = "";
	LoginResponse o = null;
	String user = vad.getUSER_NAME_REST_SERVICE();
	String psw = vad.getPSW_NAME_REST_SERVICE();
	String url = vad.getLEGALDOC_URL_REST() + "session?userid=" + user + "&password=" + psw;
	////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////// LOGIN /////////////////////////////////////////
	log.debug("getIdSession# url = {}", url);
	//WebClient client1 = WebClient.create("https://conservazionecl.infocert.it/ws/session?userid=&password=");
	WebClient client1 = WebClient.create(url);
	client1.type("application/x-www-form-urlencoded");
	client1.accept(MediaType.APPLICATION_XML);
	Response r1 = client1.post(null);
	InputStream l = (InputStream) r1.getEntity();
	String s = "";
	byte[] b;
	if (r1.getStatus() == 200) {
	    try {
		b = IOUtils.toByteArray(l);
		s = new String(b);
		o = (LoginResponse) Utilities.unMarshallString(s, LoginResponse.class);
		idSession = o.getLDSessionId();
		log.debug("getIdSession# idsessione = {}", idSession);
	    } catch (Exception e) {
		log.error("getIdSession# {}", e);
		if (e.getMessage().contains("local:\"error\"")) {
		    it.gruppoinit.infocamere.schema.legaldocs.Error er = (it.gruppoinit.infocamere.schema.legaldocs.Error) Utilities
			    .unMarshallString(s, it.gruppoinit.infocamere.schema.legaldocs.Error.class);
		    throw new RuntimeException(er.getCode() + ": " + er.getDescription());
		}
		throw new RuntimeException(e);
	    }
	} else {
	    String err = "Errore durante la creazione del sessionId";
	    try {
		b = IOUtils.toByteArray(l);
		s = new String(b);
		throw new RuntimeException(err + ". " + s);
	    } catch (IOException e) {
		log.error("getIdSession# status : {}", r1.getStatus());
		throw new RuntimeException(err + ". " + String.valueOf(r1.getStatus()));
	    }
	}
	return idSession;
    }

    public static void logoutIdSession(VerticalizzazioneArchiviazioneDocumentale vad, String idSession) {

	String url = vad.getLEGALDOC_URL_REST() + "session";
	WebClient clientLogout = WebClient.create(url);
	//WebClient client1 = WebClient.create("https://conservazionecl.infocert.it/ws/session");
	clientLogout.type("application/x-www-form-urlencoded");
	clientLogout.header("LDSessionId", idSession);
	clientLogout.accept(MediaType.APPLICATION_XML);
	Response rLogout = clientLogout.delete();
	InputStream lLogout = (InputStream) rLogout.getEntity();
	byte[] bLogout;
	try {
	    bLogout = IOUtils.toByteArray(lLogout);
	    String sLogout = new String(bLogout);
	    log.debug("logoutIdSession# {}", sLogout);
	} catch (IOException e) {
	    log.error("logoutIdSession# {}", e);
	}
    }        
    
    public static ConservazioneResponseHelper invokeConservazioneDocumento(Oggetti oggetti, String legaldoc_connector_trigger_invio,
	    String fileIndiceContent, String idSession, VerticalizzazioneArchiviazioneDocumentale vad) {

	log.debug("invokeConservazioneDocumento# invoke...");
	it.gruppoinit.infocamere.schema.legaldocs.pindex.ObjectFactory objFactory = new it.gruppoinit.infocamere.schema.legaldocs.pindex.ObjectFactory();
	ConservazioneResponseHelper conservazioneResponseHelper = new ConservazioneResponseHelper();
	File f = null;
	try {
	    boolean checkProcediConservazione = true;
	    if ("1".equals(vad.getSOLO_FIRMATI())) {
		try {
		    checkProcediConservazione = false;
		    DataHandler fileDaConservare = Utilities.bytesToDataHandler(oggetti.getOggetto());		    
		    
		    if (checkProcediConservazione(fileDaConservare, oggetti)) {
			checkProcediConservazione = true;
		    } else {
			conservazioneResponseHelper.setConservazioneSospesa(true);
		    }
		} catch (Exception e) {
		    it.gruppoinit.infocamere.schema.legaldocs.Error er = new Error();
		    er.setCode("DSS-000");
		    er.setDescription("Errore di validazione del DSS (controllare il file) - " + e.getMessage());
		    conservazioneResponseHelper.setConservazioneSospesa(false);
		    conservazioneResponseHelper.setError(er);
		    //conservazioneResponseHelper.setIdC(idC);
		}
	    }
	    if (checkProcediConservazione) {
		InputStream PARAMFILEByteArray = new ByteArrayInputStream(legaldoc_connector_trigger_invio.getBytes());
		InputStream INDEXFILEByteArray = new ByteArrayInputStream(fileIndiceContent.getBytes());
		InputStream DATAFILEByteArray = new ByteArrayInputStream(oggetti.getOggetto());
		String url = vad.getLEGALDOC_URL_REST() + vad.getBucket() + "/document";
		log.debug("invokeConservazioneDocumento# url = {}", url);
		WebClient client = WebClient.create(url);
		//WebClient client = WebClient.create("https://conservazionecl.infocert.it//ws/B578/document");
		client.header("LDSessionId", idSession);
		// connection timeout
		HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
		conduit.getClient().setConnectionTimeout(30000);
		conduit.getClient().setReceiveTimeout(30000);
		client.type("multipart/form-data").accept(MediaType.APPLICATION_XML, MediaType.MEDIA_TYPE_WILDCARD);
		List<Attachment> atts = new ArrayList<Attachment>();
		ContentDisposition cdPARAMFILE = new ContentDisposition("form-data;name=\"PARAMFILE\";filename=\"PARAMFILE\"");
		Attachment attPARAMFILE = new Attachment("PARAMFILE", PARAMFILEByteArray, cdPARAMFILE);
		atts.add(attPARAMFILE);
		//
		ContentDisposition cdINDEXFILE = new ContentDisposition("form-data;name=\"INDEXFILE\";filename=\"INDEXFILE\"");
		Attachment attINDEXFILE = new Attachment("INDEXFILE", INDEXFILEByteArray, cdINDEXFILE);
		atts.add(attINDEXFILE);
		//
		ContentDisposition cdDATAFILE = new ContentDisposition("form-data;name=\"DATAFILE\";filename=\"DATAFILE\"");
		Attachment attDATAFILE = new Attachment("DATAFILE", DATAFILEByteArray, cdDATAFILE);
		atts.add(attDATAFILE);
		MultipartBody mpb = new MultipartBody(atts);
		Response response = client.post(mpb);
		InputStream l1 = (InputStream) response.getEntity();
		if (response.getStatus() == 201) {
		    log.debug("invokeConservazioneDocumento# response = {}", response.getStatus());
		    f = File.createTempFile("IDC", ".xml.p7m");
		    OutputStream outputStream = null;
		    try {
			outputStream = new FileOutputStream(f);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = l1.read(bytes)) != -1) {
			    outputStream.write(bytes, 0, read);
			}
		    } finally {
			if (outputStream != null) {
			    outputStream.close();
			}
		    }
		    DataSource ds = new FileDataSource(f);
		    DataHandler signedFileContent = null;
		    signedFileContent = new DataHandler(ds);
		    byte[] b = getS1NoFirmata(signedFileContent, f);
		    String _s1_no_firmata = new String(b);
		    log.debug("invokeConservazioneDocumento# risposta invoke conservazione ok.... {}", _s1_no_firmata);
		    JAXBElement<PIndex> idc = (JAXBElement<PIndex>) Utilities.unMarshallString(_s1_no_firmata, PIndex.class);
		    log.debug("invokeConservazioneDocumento# unMarshallString avvenuto con successo");
		    conservazioneResponseHelper.setIndex(idc.getValue());
		} else if (response.getStatus() == 400) {
		    log.debug("invokeConservazioneDocumento# response = {}", response.getStatus());
		    log.debug("invokeConservazioneDocumento# risposta invoke conservazione ko....");
		    BufferedReader in = new BufferedReader(new InputStreamReader(l1, "UTF-8"));
		    byte[] b1 = IOUtils.toByteArray(in);
		    String s1 = new String(b1);
		    it.gruppoinit.infocamere.schema.legaldocs.Error er = (it.gruppoinit.infocamere.schema.legaldocs.Error) Utilities
			    .unMarshallString(s1, it.gruppoinit.infocamere.schema.legaldocs.Error.class);
		    conservazioneResponseHelper.setError(er);
		} else {
		    throw new RuntimeException("Response: " + response.getStatus());
		}
	    }
	} catch (Exception e) {
	    log.debug("invokeConservazioneDocumento# errore durante l'archiviazione (servizio rest) = {}", e);
	    throw new RuntimeException(e);
	} finally {
	    if (f != null) {
		Utilities.gracefullyDeleteFiles(f);
	    }
	}
	return conservazioneResponseHelper;
    }
    
    private static boolean checkProcediConservazione(DataHandler fileDaConservare, Oggetti oggetti) throws FileNonFirmatoException{	
	    ValidationResultDTO report = new DSSRestClient().checkFirmaReport(fileDaConservare, oggetti.getNomefile(), true, false);
	    return report.getSimpleReportSummary() != null && report.getSimpleReportSummary().getSignatureSummaries() != null && !report.getSimpleReportSummary().getSignatureSummaries().isEmpty();	    
    }
    
    private static byte[] getS1NoFirmata(DataHandler signedFileContent, File f){
	    ValidationResultDTO report = new DSSRestClient().checkFirmaReport(signedFileContent, f.getName(), true, true);
	    return report.getExtractedContent();	
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {

	File f = new File("C:\\temp\\livorno\\risposta_2_5_2.xml");
	String _s1_no_firmata = Utilities.inputStreamToString(new FileInputStream(f));
	log.debug("invokeConservazioneDocumento# risposta invoke conservazione ok....");
	JAXBElement<PIndex> idc = (JAXBElement<PIndex>) Utilities.unMarshallString(_s1_no_firmata, PIndex.class);
	System.out.println(idc.getValue().getSelfDescription().getID().getValue());
    }

    private static String getMimeType(String fileExt) {

	if (fileExt.endsWith(".docx")) {
	    return "application/msword;2007";
	}
	if (fileExt.endsWith(".doc")) {
	    return "application/msword;NA";
	}
	if (fileExt.endsWith(".pdf")) {
	    return "application/pdf;NA";
	}
	if (fileExt.endsWith(".doc.p7m")) {
	    return "application/pkcs7;NA|application/msword;NA";
	}
	if (fileExt.endsWith(".docx.p7m")) {
	    return "application/pkcs7;NA|application/msword;2007";
	}
	if (fileExt.endsWith(".pdf.p7m")) {
	    return "application/pkcs7;NA|application/pdf;NA";
	}
	if (fileExt.endsWith(".odt.p7m")) {
	    return "application/pkcs7;NA|application/vnd.oasis.opendocument.text;NA";
	}
	if (fileExt.endsWith(".jpg.p7m")) {
	    return "application/pkcs7;NA|image/jpeg;NA";
	}
	if (fileExt.endsWith(".jpeg.p7m")) {
	    return "application/pkcs7;NA|image/jpeg;NA";
	}
	if (fileExt.endsWith(".xml.p7m")) {
	    return "application/pkcs7;NA|text/xml;1.0";
	}
	if (fileExt.endsWith(".odt")) {
	    return "application/vnd.oasis.opendocument.text;NA";
	}
	if (fileExt.endsWith(".jpg")) {
	    return "image/jpeg;NA";
	}
	if (fileExt.endsWith(".jpeg")) {
	    return "image/jpeg;NA";
	}
	if (fileExt.endsWith(".rtf")) {
	    return "text/rtf;NA";
	}
	if (fileExt.endsWith(".rtf.p7m")) {
	    return "text/rtf;NA|application/pkcs7;NA";
	}
	if (fileExt.endsWith(".xml.p7m")) {
	    return "text/xml;1.0";
	}
	/**
	 * pdf con marca temporale
	 **/
	// pdf marcato
	if (fileExt.endsWith(".pdf.tsr")) {
	    return "application/timestamp-reply;NA|application/pdf;NA";
	}
	// pdf firmato e marcato
	if (fileExt.endsWith(".pdf.p7m.tsr")) {
	    return "application/timestamp-reply;NA|application/pkcs7;NA|application/pdf;NA";
	}
	// pdf firmato e marcato temporatlmente
	if (fileExt.endsWith(".pdf.p7m.tsd")) {
	    return "application/timestamped-data;NA|application/pkcs7;NA|application/pdf;NA";
	}
	//file dwf
	if (fileExt.endsWith(".dwf")) {
	    return "application/dwf;NA";
	}
	if (fileExt.endsWith(".dwf.p7m")) {
	    return "application/pkcs7;NA|application/dwf;NA";
	}
	if (fileExt.endsWith(".dwf.p7m.tsd")) {
	    return "application/timestamped-data;NA|application/pkcs7;NA|application/dwf;NA";
	}
	return null;
    }
}
