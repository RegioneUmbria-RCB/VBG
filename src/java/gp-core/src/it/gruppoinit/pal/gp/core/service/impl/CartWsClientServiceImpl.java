package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.CartWsClientService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openspcoop.pdd.services.IntegrationManagerService;
import org.openspcoop.pdd.services.IntegrationManagerServiceLocator;
import org.openspcoop.pdd.services.IntegrationManagerSoapBindingStub;
import org.openspcoop.pdd.services.SPCoopException;
import org.openspcoop.pdd.services.SPCoopHeaderInfo;
import org.openspcoop.pdd.services.SPCoopMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartWsClientServiceImpl implements CartWsClientService {

    private VerticalizzazioniService verticalizzazioniService;
    private Map<String, Map<String, String>> parametriVerticalizzazioni = new HashMap<String, Map<String, String>>();

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    private Map<String, IntegrationManagerSoapBindingStub> portMap = new HashMap<String, IntegrationManagerSoapBindingStub>();
    private static final Logger log = LoggerFactory.getLogger(CartWsClientServiceImpl.class);

    @Override
    public String[] getAllMessagesIdByService(String service) throws SPCoopException, RemoteException, MalformedURLException, ServiceException {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("Chiamo getAllMessagesIdByService({})", new Object[] { service });
	}
	String servizio = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_SERVIZIO);// verticalizzazioniparametriSERVIZIO.getValore();
	String tipo_servizio = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_TIPO_SERVIZIO);// verticalizzazioniparametriTIPO_SERVIZIO.getValore();
	if (log.isDebugEnabled()) {
	    log.debug("Chiamo integrationManager.getAllMessagesIdByService({},{},{})", new Object[] { tipo_servizio, servizio, service });
	}
	String[] messages = this.getPort().getAllMessagesIdByService(tipo_servizio, servizio, service);
	if (log.isDebugEnabled()) {
	    for (String idEgov : messages) {
		log.debug("id messaggio: {}", idEgov);
	    }
	    log.debug("chiamata comletata a getAllMessagesIdByService({})", new Object[] { service });
	}
	return messages;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private IntegrationManagerSoapBindingStub getPort() throws MalformedURLException, ServiceException {

	// §§§BEGIN§§§
	IntegrationManagerSoapBindingStub port = portMap.get(ORMHelper.getIdcomune());
	if (port == null) {
	    String USERNAME = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_USERNAME); // verticalizzazioniparametriUSERNAME.getValore();
	    String PASSWORD = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_PASSWORD);// verticalizzazioniparametriPASSWORD.getValore();
	    String INTEGRATION_MANAGER_URL = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_INTEGRATION_MANAGER_URL); // verticalizzazioniparametriINTEGRATION_MANAGER_URL.getValore();
	    if (log.isDebugEnabled()) {
		log.debug("Instanzio IntegrationManager con URL: {}, UserName={}", new Object[] { INTEGRATION_MANAGER_URL, USERNAME });
	    }
	    IntegrationManagerService service = new IntegrationManagerServiceLocator();
	    port = (IntegrationManagerSoapBindingStub) service.getIntegrationManager(new URL(INTEGRATION_MANAGER_URL));
	    port.setUsername(USERNAME);
	    port.setPassword(PASSWORD);
	    if (log.isDebugEnabled()) {
		log.debug("IntegrationManager istanziato correttamente", new Object[] { INTEGRATION_MANAGER_URL, USERNAME });
	    }
	    portMap.put(ORMHelper.getIdcomune(), port);
	} else {
	    if (log.isDebugEnabled()) {
		log.debug("Riutilizzo IntegrationManager per l'entità {} ", ORMHelper.getIdcomune());
	    }
	}
	return port;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    //    public String encodeB64(String arg) {
    //
    //	byte[] content = null;
    //	try {
    //	    content = arg.getBytes("UTF-8");
    //	} catch (UnsupportedEncodingException e) {
    //	    content = arg.getBytes();
    //	}
    //	byte currentXMLBytes[] = content;
    //	BASE64Encoder encoder = new BASE64Encoder();
    //	return encoder.encode(currentXMLBytes);
    //    }
    public SPCoopMessage getMessaggio(String idEgov) throws Exception {

	SPCoopMessage messaggio = getPort().getMessage(idEgov);
	return messaggio;
    }

    @Override
    public String getDescrizioneEccezione(SPCoopException e) {

	return ToStringBuilder.reflectionToString(e, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public String getMessaggioBody(SPCoopMessage messaggio) {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("Entro in getMessaggioBody del messaggio: {}", messaggio.getSpcoopHeaderInfo().getID());
	}
	byte[] mioArray = messaggio.getMessage();
	try {
	    MessageFactory mf = MessageFactory.newInstance();
	    SOAPMessage sm = mf.createMessage();
	    SOAPPart soapPart = sm.getSOAPPart();
	    StreamSource msgSrc = new StreamSource(new ByteArrayInputStream(mioArray));
	    soapPart.setContent(msgSrc);
	    // Save the message
	    sm.saveChanges();
	    SOAPBody body = sm.getSOAPBody();
	    DOMSource domSource = new DOMSource(body.getFirstChild());
	    StringWriter writer = new StringWriter();
	    StreamResult result = new StreamResult(writer);
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.transform(domSource, result);
	    return writer.toString();
	} catch (UnsupportedOperationException e) {
	    e.printStackTrace();
	    log.error("errore in getMessaggioBody UnsupportedOperationException: {}", e.getMessage());
	} catch (SOAPException e) {
	    e.printStackTrace();
	    log.error("errore in getMessaggioBody SOAPException: {}", e.getMessage());
	} catch (Exception e) {
	    e.printStackTrace();
	    log.error("errore in getMessaggioBody Exception: {}", e.getMessage());
	}
	return "";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public static String getDescrizioneMessaggio(SPCoopMessage messaggio) {

	// §§§BEGIN§§§
	String answer = "messaggio:[";
	answer += "\nIDApplicativo: " + messaggio.getIDApplicativo();
	answer += "\nServizio Applicativo: " + messaggio.getServizioApplicativo();
	answer += "\nImbustamento: " + messaggio.isImbustamento();
	SPCoopHeaderInfo headerInfo = messaggio.getSpcoopHeaderInfo();
	byte[] mioArray = messaggio.getMessage();
	StringBuffer message = new StringBuffer();
	for (int i = 0; i < mioArray.length; i++) {
	    message.append((char) mioArray[i]);
	}
	answer += "\nMessaggio: \n\n" + message.toString() + "\n\n";
	String headerInfostring = ToStringBuilder.reflectionToString(headerInfo, ToStringStyle.MULTI_LINE_STYLE);
	answer += "\nSpcoopHeaderInfo: " + headerInfostring;
	answer += "\n]";
	if (log.isDebugEnabled()) {
	    log.debug("getDescrizioneMessaggio: {}", answer);
	}
	return answer;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public static InputStream fromString(String str) {

	// §§§BEGIN§§§
	byte[] content = null;
	try {
	    content = str.getBytes("UTF-8");
	} catch (UnsupportedEncodingException e) {
	    content = str.getBytes();
	}
	return new ByteArrayInputStream(content);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public static InputStream fromByteArray(byte[] bytes) {

	return new ByteArrayInputStream(bytes);
    }

    public static String getSourceAsString(Source s) throws Exception {

	// §§§BEGIN§§§
	Transformer transformer = TransformerFactory.newInstance().newTransformer();
	transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	OutputStream out = new ByteArrayOutputStream();
	StreamResult streamResult = new StreamResult();
	streamResult.setOutputStream(out);
	transformer.transform(s, streamResult);
	return streamResult.getOutputStream().toString();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public SPCoopMessage invocaPortaDelegata(String pddLocation, SPCoopMessage messaggio) throws SPCoopException, RemoteException,
	    MalformedURLException, ServiceException {

	// §§§BEGIN§§§
	return getPort().invocaPortaDelegata(pddLocation, messaggio);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public SPCoopMessage getMessaggioPerRifermimento(String riferimentoMsg) throws SPCoopException, RemoteException, MalformedURLException,
	    ServiceException {

	// §§§BEGIN§§§
	return getPort().getMessageByReference(riferimentoMsg);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public IntegrationManagerSoapBindingStub getIntegrationManager() throws MalformedURLException, ServiceException {

	// §§§BEGIN§§§
	return portMap.get(ORMHelper.getIdcomune());
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void deleteMessage(String idMessage) throws MalformedURLException, ServiceException, SPCoopException, RemoteException {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("Cancello il messaggio eGov: {}", idMessage);
	}
	getPort().deleteMessage(idMessage);
	// §§§END§§§
    }

    @Override
    public void deleteAllMessage() throws MalformedURLException, ServiceException, SPCoopException, RemoteException {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("Cancello tutti i messaggi");
	}
	getPort().deleteAllMessages();
	// §§§END§§§
    }

    @Override
    public String[] getAllMessagesId() throws SPCoopException, RemoteException, MalformedURLException, ServiceException {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("Richiedo tutti i messaggi");
	}
	String[] messages = this.getPort().getAllMessagesId();
	if (log.isDebugEnabled()) {
	    if (messages != null) {
		log.debug("Tornati {} messaggi, segue la lista...", messages.length);
		for (String idEgov : messages) {
		    log.debug("id messaggio:" + idEgov);
		}
	    } else {
		log.debug("Tornati 0 messaggi");
	    }
	}
	return messages;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void reloadPortConfiguration() {

	// §§§BEGIN§§§
	portMap.remove(ORMHelper.getIdcomune());
	parametriVerticalizzazioni.remove(ORMHelper.getIdcomune());
	// §§§END§§§
    }

    private Map<String, String> getParametriConfigurazione() {

	// §§§BEGIN§§§
	if (parametriVerticalizzazioni.get(ORMHelper.getIdcomune()) == null) {
	    Map<String, String> parametri = new HashMap<String, String>();
	    // azioni
	    Verticalizzazioniparametri verticalizzazioniparametriAZIONI = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_AZIONI);
	    if (verticalizzazioniparametriAZIONI == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO_ALBEROPROC_ENDO.FKAZID");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO_ALBEROPROC_ENDO.FKAZID");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_AZIONI, verticalizzazioniparametriAZIONI.getValore());
	    // CODICE NATURA
	    Verticalizzazioniparametri verticalizzazioniparametriCODICENATURA = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_CODICENATURA);
	    if (verticalizzazioniparametriCODICENATURA == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO_INVENTARIOPROC.CODNATURA");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO_INVENTARIOPROC.CODNATURA");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_CODICENATURA, verticalizzazioniparametriCODICENATURA.getValore());
	    // destinatario
	    Verticalizzazioniparametri verticalizzazioniparametriDESTINATARIO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_DESTINATARIO);
	    if (verticalizzazioniparametriDESTINATARIO == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO DESTINATARIO");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO DESTINATARIO");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_DESTINATARIO, verticalizzazioniparametriDESTINATARIO.getValore());
	    // famiglia endo dei procedimenti di tipo 2
	    Verticalizzazioniparametri verticalizzazioniparametriFAMIGLIAENDO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_FAMIGLIAENDO);
	    if (verticalizzazioniparametriFAMIGLIAENDO == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO2_TIPIFAMIGLIEENDO.CODICE");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO2_TIPIFAMIGLIEENDO.CODICE");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_FAMIGLIAENDO, verticalizzazioniparametriFAMIGLIAENDO.getValore());
	    // integration manager url
	    Verticalizzazioniparametri verticalizzazioniparametriINTEGRATION_MANAGER_URL = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_INTEGRATION_MANAGER_URL);
	    if (verticalizzazioniparametriINTEGRATION_MANAGER_URL == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO INTEGRATION_MANAGER_URL");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO INTEGRATION_MANAGER_URL");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_INTEGRATION_MANAGER_URL, verticalizzazioniparametriINTEGRATION_MANAGER_URL.getValore());
	    // RECUPERO PARAMETRI VERTICALIZZAZIONE CART VERIFICO SE E' ABILITATA LA GESTIONE DEGLI
	    // INVENTARIPROCEDIMENTI.
	    Verticalizzazioniparametri verticalizzazioniparametriINVENTARIO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_INVENTARIO);
	    if (verticalizzazioniparametriINVENTARIO == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO2_INVENTARIOPROCEDIMENTI");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO2_INVENTARIOPROCEDIMENTI");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_INVENTARIO, verticalizzazioniparametriINVENTARIO.getValore());
	    // mittente
	    Verticalizzazioniparametri verticalizzazioniparametriMITTENTE = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_MITTENTE);
	    if (verticalizzazioniparametriMITTENTE == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO MITTENTE");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO MITTENTE");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_MITTENTE, verticalizzazioniparametriMITTENTE.getValore());
	    // password
	    Verticalizzazioniparametri verticalizzazioniparametriPASSWORD = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_PASSWORD);
	    if (verticalizzazioniparametriPASSWORD == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO PASSWORD");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO PASSWORD");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_PASSWORD, verticalizzazioniparametriPASSWORD.getValore());
	    // pdd location
	    Verticalizzazioniparametri verticalizzazioniparametriPDD_LOCATION = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_PDD_LOCATION);
	    if (verticalizzazioniparametriPDD_LOCATION == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO PDD_LOCATION");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO PDD_LOCATION");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_PDD_LOCATION, verticalizzazioniparametriPDD_LOCATION.getValore());
	    // root alberoproc
	    Verticalizzazioniparametri verticalizzazioniparametriROOTALBEROPROC = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_ROOT_ALBEROPROC);
	    if (verticalizzazioniparametriROOTALBEROPROC == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ROOT_ALBEROPROC_ID");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ROOT_ALBEROPROC_ID");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_ROOT_ALBEROPROC, verticalizzazioniparametriROOTALBEROPROC.getValore());
	    // servizio applicativo
	    Verticalizzazioniparametri verticalizzazioniparametriSERVIZIOAPPLICATIVO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_SERVIZIOAPPLICATIVO);
	    if (verticalizzazioniparametriSERVIZIOAPPLICATIVO == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO SERVIZIOAPPLICATIVO");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO SERVIZIOAPPLICATIVO");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_SERVIZIOAPPLICATIVO, verticalizzazioniparametriSERVIZIOAPPLICATIVO.getValore());
	    // servizio
	    Verticalizzazioniparametri verticalizzazioniparametriSERVIZIO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_SERVIZIO);
	    if (verticalizzazioniparametriSERVIZIO == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO SERVIZIO");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO SERVIZIO");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_SERVIZIO, verticalizzazioniparametriSERVIZIO.getValore());
	    // TIPI MOVIMENTO
	    Verticalizzazioniparametri verticalizzazioniparametriTIPIMOVIMENTO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_TIPIMOVIMENTO);
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_TIPIMOVIMENTO, verticalizzazioniparametriTIPIMOVIMENTO.getValore());
	    // tipo destinatario
	    Verticalizzazioniparametri verticalizzazioniparametriTIPODESTINATARIO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_TIPODESTINATARIO);
	    if (verticalizzazioniparametriTIPODESTINATARIO == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO TIPODESTINATARIO");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO TIPODESTINATARIO");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_TIPODESTINATARIO, verticalizzazioniparametriTIPODESTINATARIO.getValore());
	    // tipo mittente
	    Verticalizzazioniparametri verticalizzazioniparametriTIPOMITTENTE = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_TIPOMITTENTE);
	    if (verticalizzazioniparametriTIPOMITTENTE == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO TIPOMITTENTE");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO TIPOMITTENTE");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_TIPOMITTENTE, verticalizzazioniparametriTIPOMITTENTE.getValore());
	    // tipo servizio
	    Verticalizzazioniparametri verticalizzazioniparametriTIPO_SERVIZIO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_TIPO_SERVIZIO);
	    if (verticalizzazioniparametriTIPO_SERVIZIO == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO TIPO_SERVIZIO");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO TIPO_SERVIZIO");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_TIPO_SERVIZIO, verticalizzazioniparametriTIPO_SERVIZIO.getValore());
	    // username
	    Verticalizzazioniparametri verticalizzazioniparametriUSERNAME = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_USERNAME);
	    if (verticalizzazioniparametriUSERNAME == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO USERNAME");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO USERNAME");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_USERNAME, verticalizzazioniparametriUSERNAME.getValore());
	    parametriVerticalizzazioni.put(ORMHelper.getIdcomune(), parametri);
	    return parametri;
	} else {
	    return parametriVerticalizzazioni.get(ORMHelper.getIdcomune());
	}
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
