package it.gruppoinit.ws.client.atti;

import it.gruppoinit.domain.helper.InserisciDeterminaHelper;
import it.gruppoinit.domain.helper.LeggiAttoHelper;
import it.gruppoinit.domain.helper.LeggiAttoPlusHelper;
import it.gruppoinit.sigepro.definitions.oggetti.OggettiWSClient;
import it.gruppoinit.sigepro.schemas.messages.oggetti.OggettiFindResponse;
import it.gruppoinit.utilities.Utilities;
import it.gruppoinit.ws.wsatti.AttoInseritoOut;
import it.gruppoinit.ws.wsatti.AttoOut;
import it.gruppoinit.ws.wsatti.InserisciDeterminaString;
import it.gruppoinit.ws.wsatti.ObjectFactory;
import it.gruppoinit.ws.wsatti.WSatti;
import it.gruppoinit.ws.wsatti.WSattiSoap;
import it.gruppoinit.ws.wsatti.inserisciattistring.AllegatoIn;
import it.gruppoinit.ws.wsatti.inserisciattistring.ArrayOfAllegatoIn;
import it.gruppoinit.ws.wsatti.leggiattoplus.FiltroAttoIn;
import it.init.sigepro.rte.types.DocumentiType;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.MTOMFeature;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttiWSServiceClient {

    private static final Logger log = LoggerFactory.getLogger(AttiWSServiceClient.class);
    private String attiWsUrl;
    private boolean mtom;
    private long timeout = 120000;
    private String wsUsername;
    @Autowired
    private OggettiWSClient oggettiWSClient;

    private WSattiSoap getAttiWsPort() throws Exception {

	log.debug("getAttiWsPort: url={}", attiWsUrl);
	//WSatti client = new WSatti(new URL(this.attiWsUrl));
	WSatti client = new WSatti(new URL(attiWsUrl));
	MTOMFeature mtomFeature = new MTOMFeature(mtom, 0);
	WSattiSoap port = client.getWSattiSoap(mtomFeature);
	BindingProvider bp = (BindingProvider) port;
	bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, attiWsUrl);
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	// HTTPClientPolicy - Properties used to configure a client-side HTTP port  
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	httpClientPolicy.setConnectionTimeout(this.timeout); // Line #2  
	httpClientPolicy.setReceiveTimeout(this.timeout); // Line #3  
	conduit.setClient(httpClientPolicy);
	return port;
    }

    public AttoOut leggiAtto(LeggiAttoHelper attoHelper) {

	AttoOut attoOut = new AttoOut();
	try {
	    log.debug("inserisciDetermina# Invoco il metodo leggiAtto().... ");
	    attoOut = getAttiWsPort().leggiAtto(attoHelper.getIdDocumento(), attoHelper.getTipo(), attoHelper.getOrgano(), attoHelper.getAnno(),
		    attoHelper.getNumero(), attoHelper.getUtente(), attoHelper.getRuolo(), attoHelper.getCodiceAmm(), attoHelper.getCodiceAOO());
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
	return attoOut;
    }

    public it.gruppoinit.ws.wsatti.leggiattoplus.attoout.AttoOut leggiAttoPlus(LeggiAttoPlusHelper attoPlusHelper) {

	it.gruppoinit.ws.wsatti.leggiattoplus.attoout.ObjectFactory objectFactory = new it.gruppoinit.ws.wsatti.leggiattoplus.attoout.ObjectFactory();
	it.gruppoinit.ws.wsatti.leggiattoplus.attoout.AttoOut attoOut = objectFactory.createAttoOut();
	try {
	    log.debug("inserisciDetermina# Invoco il metodo leggiAttoPlus().... ");
	    it.gruppoinit.ws.wsatti.leggiattoplus.ObjectFactory factory = new it.gruppoinit.ws.wsatti.leggiattoplus.ObjectFactory();
	    FiltroAttoIn attoIn = factory.createFiltroAttoIn();
	    attoIn.setIdDocumento(attoPlusHelper.getIdDocumento());
	    attoIn.setNumero(attoPlusHelper.getNumero());
	    attoIn.setAnno(attoPlusHelper.getAnno());
	    String request = Utilities.marshallObject(attoIn);
	    log.debug(request);
	    String _attoOut = getAttiWsPort().leggiAttoPlus(request, wsUsername, null);
	    attoOut = (it.gruppoinit.ws.wsatti.leggiattoplus.attoout.AttoOut) Utilities.unMarshallString(_attoOut,
		    it.gruppoinit.ws.wsatti.leggiattoplus.attoout.AttoOut.class, "UTF-8");
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw new RuntimeException(e.getMessage());
	    //e.printStackTrace();
	}
	return attoOut;
    }

    public AttoInseritoOut inserisciDetermina(InserisciDeterminaHelper inserisciDeterminaHelper) {

	// secodo maggioli il metodo effettua le stesse operazioni "inserisciDeterminaString", ma non è implemenetato in jIride
	throw new NotImplementedException("Metodo non implementati");
	//	ObjectFactory objectFactory = new ObjectFactory();
	//	//InserisciDeterminaResponse inserisciDeterminaResponse = factory.createInserisciDeterminaResponse();
	//	AttoInseritoOut inseritoOut = objectFactory.createAttoInseritoOut();
	//	try {
	//	    InserisciDetermina inserisciDetermina = new InserisciDetermina();
	//	    /**
	//	     * o "Tipo" o "Trattamento" o "Proponente" o "Dirigente" o "Classifica" o "Utente" o "Ruolo"
	//	     */
	//	    // E' Determina In gerato dal wsdl
	//	    DeterminaIn determinaIn = objectFactory.createDeterminaIn();
	//	    JAXBElement<String> trattamento = objectFactory.createDeterminaInTrattamento(inserisciDeterminaHelper.getTrattamento());
	//	    JAXBElement<String> proponente = objectFactory.createDeterminaInProponente(inserisciDeterminaHelper.getProponente());
	//	    JAXBElement<String> dirigente = objectFactory.createDeterminaInDirigente(inserisciDeterminaHelper.getDirigente());
	//	    JAXBElement<String> classifica = objectFactory.createDeterminaInClassifica(inserisciDeterminaHelper.getClassifica());
	//	    JAXBElement<String> utente = objectFactory.createDeterminaInUtente(inserisciDeterminaHelper.getUtente());
	//	    JAXBElement<String> ruolo = objectFactory.createDeterminaInRuolo(inserisciDeterminaHelper.getRuolo());
	//	    determinaIn.setTrattamento(trattamento);
	//	    determinaIn.setProponente(proponente);
	//	    determinaIn.setDirigente(dirigente);
	//	    determinaIn.setClassifica(classifica);
	//	    determinaIn.setUtente(utente);
	//	    determinaIn.setRuolo(ruolo);
	//	    // MANCA IL TIPO
	//	    // TIPO NON C'E' - objectFactory.createDeterminain
	//	    inserisciDetermina.setDeterminaIn(determinaIn);
	//	    //String inserisciDeterminaString = Utilities.marshallObject(inserisciDetermina);
	//	    log.debug("inserisciDetermina# Invoco il metodo inserisciDetermina().... ");
	//	    inseritoOut = getAttiWsPort().inserisciDetermina(determinaIn, "", "");
	//	} catch (Exception e) {
	//	    log.error(e.getMessage());
	//	}
	//	return inseritoOut;
    }

    //    public static void main(String[] args) {
    //
    //	AttiWSServiceClient o = new AttiWSServiceClient();
    //	ObjectFactory objectFactory = new ObjectFactory();
    //	InserisciDeterminaString inserisciDeterminaString = objectFactory.createInserisciDeterminaString();
    //	it.gruppoinit.ws.wsatti.inserisciattistring.ObjectFactory f = new it.gruppoinit.ws.wsatti.inserisciattistring.ObjectFactory();
    //	it.gruppoinit.ws.wsatti.inserisciattistring.DeterminaIn determinaIn = f.createDeterminaIn();
    //	String determinaInString = Utilities.marshallObject(determinaIn);
    //	System.out.println(determinaInString);
    //    }
    public it.gruppoinit.ws.wsatti.inserisciattistring.AttoInseritoOut inserisciDeterminaString(InserisciDeterminaHelper inserisciDeterminaHelper,
	    String token) {

	ObjectFactory objectFactory = new ObjectFactory();
	it.gruppoinit.ws.wsatti.inserisciattistring.ObjectFactory f = new it.gruppoinit.ws.wsatti.inserisciattistring.ObjectFactory();
	InserisciDeterminaString inserisciDeterminaString = objectFactory.createInserisciDeterminaString();
	it.gruppoinit.ws.wsatti.inserisciattistring.AttoInseritoOut attoInseritoOut = new it.gruppoinit.ws.wsatti.inserisciattistring.AttoInseritoOut();
	try {
	    //DeterminaIn determinaIn = objectFactory.createDeterminaIn();
	    it.gruppoinit.ws.wsatti.inserisciattistring.DeterminaIn determinaIn = f.createDeterminaIn();
	    String trattamento = inserisciDeterminaHelper.getTrattamento();
	    String proponente = inserisciDeterminaHelper.getProponente();
	    String dirigente = inserisciDeterminaHelper.getDirigente();
	    String classifica = inserisciDeterminaHelper.getClassifica();
	    String utente = inserisciDeterminaHelper.getUtente();
	    String ruolo = inserisciDeterminaHelper.getRuolo();
	    String tipo = inserisciDeterminaHelper.getTipo();
	    String oggetto = inserisciDeterminaHelper.getOggetto();
	    determinaIn.setTipo(tipo);
	    determinaIn.setTrattamento(trattamento);
	    determinaIn.setProponente(proponente);
	    determinaIn.setDirigente(dirigente);
	    determinaIn.setClassifica(classifica);
	    determinaIn.setUtente(utente);
	    determinaIn.setRuolo(ruolo);
	    determinaIn.setOggetto(oggetto);
	    ArrayOfAllegatoIn arrayOfAllegatoIn = populateAllegati(inserisciDeterminaHelper.getDocumenti(), token);
	    determinaIn.setAllegati(arrayOfAllegatoIn);
	    String determinaInString = Utilities.marshallObject(determinaIn);
	    inserisciDeterminaString.setDeterminaInStr(determinaInString);
	    String AttoInseritoOutString = getAttiWsPort().inserisciDeterminaString(determinaInString, wsUsername, null);
	    attoInseritoOut = (it.gruppoinit.ws.wsatti.inserisciattistring.AttoInseritoOut) Utilities.unMarshallString(AttoInseritoOutString,
		    it.gruppoinit.ws.wsatti.inserisciattistring.AttoInseritoOut.class, "UTF-8");
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw new RuntimeException(e.getMessage());
	}
	return attoInseritoOut;
    }

    private ArrayOfAllegatoIn populateAllegati(List<DocumentiType> documenti, String token) {

	it.gruppoinit.ws.wsatti.inserisciattistring.ObjectFactory f = new it.gruppoinit.ws.wsatti.inserisciattistring.ObjectFactory();
	ArrayOfAllegatoIn arrayOfAllegatoIn = f.createArrayOfAllegatoIn();
	AllegatoIn allegatoIn = null;
	for (DocumentiType documentiType : documenti) {
	    allegatoIn = f.createAllegatoIn();
	    allegatoIn.setCommento(StringUtils.defaultIfEmpty(documentiType.getAnnotazioni(), ""));
	    OggettiFindResponse findResponse = oggettiWSClient.find(new BigInteger(documentiType.getAllegati().getId()), token);
	    allegatoIn.setContentType(StringUtils.defaultIfEmpty(findResponse.getMimeType(), ""));
	    //allegatoIn.setIdAllegatoPrincipale();
	    InputStream in;
	    try {
		if (findResponse != null && findResponse.getBinaryData() != null) {
		    in = findResponse.getBinaryData().getInputStream();
		    byte[] byteArray = IOUtils.toByteArray(in);
		    allegatoIn.setImage(byteArray);
		}
	    } catch (IOException e) {
		log.error("Errore durante la conversione del da oggetto Datahandeler a array di byte");
	    }
	    allegatoIn.setNomeAllegato(findResponse.getFileName());
	    //allegatoIn.setSchema(value);
	    allegatoIn.setTipoAllegato("0");
	    allegatoIn.setTipoFile(Utilities.getFileExtension(findResponse.getFileName()));
	    arrayOfAllegatoIn.getAllegato().add(allegatoIn);
	}
	return arrayOfAllegatoIn;
    }

    public String getAttiWsUrl() {

	return attiWsUrl;
    }

    public void setAttiWsUrl(String attiWsUrl) {

	this.attiWsUrl = attiWsUrl;
    }

    public boolean isMtom() {

	return mtom;
    }

    public void setMtom(boolean mtom) {

	this.mtom = mtom;
    }

    public long getTimeout() {

	return timeout;
    }

    public void setTimeout(long timeout) {

	this.timeout = timeout;
    }

    public void setWsUsername(String wsUsername) {

	this.wsUsername = wsUsername;
    }
}
