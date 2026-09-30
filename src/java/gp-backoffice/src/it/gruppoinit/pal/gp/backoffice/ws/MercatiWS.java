package it.gruppoinit.pal.gp.backoffice.ws;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.backoffice.definitions.mercati.Mercati;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mercati.EstremiAut;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mercati.PresenzeManifestazioneRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mercati.PresenzeManifestazioneResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mercati.PresenzeManifestazioneV2Request;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mercati.PresenzeManifestazioneV2Response;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mercati.PresenzePosteggioRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mercati.PresenzePosteggioResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mercati.PresenzeProprietarioRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mercati.PresenzeProprietarioResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mercati.PresenzeRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mercati.PresenzeResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiPresenzeDTO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.VwEntilocali;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeStoricoService;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

/**
 * WebService per il recupero delle presenze sulle Manifestazioni
 * 
 * @author fabrizioc
 * 
 */
@javax.jws.WebService(serviceName = "MercatiService", portName = "MercatiSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/mercati", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.mercati.Mercati")
public class MercatiWS extends BaseWS implements Mercati {

    private static final Logger log = LoggerFactory.getLogger(MercatiWS.class);
    private IstanzeService istanzeService;
    private MercatipresenzeStoricoService mercatipresenzeStoricoService;
    private MercatiService mercatiService;

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setMercatipresenzeStoricoService(MercatipresenzeStoricoService mercatipresenzeStoricoService) {

	this.mercatipresenzeStoricoService = mercatipresenzeStoricoService;
    }

    /**
     * WebService che restituisce il numero di presenze fatte come proprietario. Il WS richiede in ingresso il codice
     * istanza con il quale recupera gli estremi dell'autorizzazione (dai dyn2dati), con questi ricava l'autorizzazione
     * e calcola il numero di presenze fatte come proprietario sul mercato o fiera.
     * 
     * @author fabrizioc
     * @param request
     * @return
     * 
     */
    @Override
    public PresenzeProprietarioResponse presenzeProprietario(PresenzeProprietarioRequest request) {

	log.debug("getPresenzeProprietario: token={}, codiceIstanza={}, inserisciAutSeNonTrovata={}",
		new Object[] { request.getToken(), request.getCodIstanza(), request.isInserisciAutSeNonTrovata() });
	setORMHelper(null, request.getToken());
	try {
	    Istanze istanza = istanzeService.findById(new PkId(request.getCodIstanza()));
	    if (istanza != null) {
		Software software = istanza.getSoftware();
		ORMHelper.setSoftware(software.getCodice());
		PresenzeProprietarioResponse response = new PresenzeProprietarioResponse();
		try {
		    int numeroPresenzeProp = 0;
		    MercatiPresenzeDTO dto = mercatipresenzeStoricoService.getSommaDellePresenze(request.getCodIstanza(),
			    getAut(request.getEstremiAut()), request.getCatMerc(), request.isInserisciAutSeNonTrovata(), null, null, true, null);
		    numeroPresenzeProp = dto.getPresenzeComeProprietario() != null ? dto.getPresenzeComeProprietario() : 0;
		    response.setNumeroPresenzeProp(numeroPresenzeProp);
		    log.debug("WS getPresenzeProprietario response is: {}", numeroPresenzeProp);
		} catch (Exception e) {
		    log.error("getPresenzeProprietario(): {}", e.getMessage());
		    throw new RuntimeException("Errore durante il recupero delle presenze come proprietatio: " + e.getMessage());
		}
		return response;
	    } else {
		String e = "Errore durante il recupero delle presenze come proprietatio: Istanza non trovata (codice istanza=" +
			   request.getCodIstanza() + ")";
		log.error(e);
		throw new RuntimeException(e);
	    }
	} finally {
	    resetThreadLocalVars();
	}
    }

    /**
     * WebService che restituisce il numero di presenze fatte. Il WS richiede in ingresso il codice istanza con il quale
     * recupera gli estremi dell'autorizzazione (dai dyn2dati), con questi ricava l'autorizzazione e calcola il numero
     * di presenze fatte sul mercato o fiera.
     * 
     * @param request
     * @return
     */
    @Override
    public PresenzeResponse presenze(PresenzeRequest request) {

	log.debug("getPresenze: token={}, codiceIstanza={}, inserisciAutSeNonTrovata={}",
		new Object[] { request.getToken(), request.getCodiceIstanza(), request.isInserisciAutSeNonTrovata() });
	setORMHelper(null, request.getToken());
	try {
	    log.debug("getPresenze: cerco l'istanza [{}]", request.getCodiceIstanza());
	    Istanze istanza = istanzeService.findById(new PkId(request.getCodiceIstanza()));
	    if (istanza != null) {
		Software software = istanza.getSoftware();
		ORMHelper.setSoftware(software.getCodice());
		PresenzeResponse response = new PresenzeResponse();
		try {
		    log.debug("getPresenze: prima di mercatipresenzeStoricoService.getSommaDellePresenze");
		    MercatiPresenzeDTO dto = mercatipresenzeStoricoService.getSommaDellePresenze(request.getCodiceIstanza(),
			    getAut(request.getEstremiAut()), request.getCatMerc(), request.isInserisciAutSeNonTrovata(), null, null, true, null);
		    int numeroPresenze = dto.getPresenze() != null ? dto.getPresenze() : 0;
		    response.setNumeroPresenze(numeroPresenze);
		    log.debug("WS getPresenze response is: {}", numeroPresenze);
		} catch (Exception e) {
		    log.error("getPresenze(): {}", e.getMessage());
		    throw new RuntimeException("Errore durante il recupero delle presenze: " + e.getMessage());
		}
		return response;
	    } else {
		String e = "Errore durante il recupero delle presenze: Istanza non trovata. (codice istanza=" + request.getCodiceIstanza() + ")";
		log.error(e);
		throw new RuntimeException(e);
	    }
	} finally {
	    resetThreadLocalVars();
	}
    }

    /**
     * WebService che restituisce il numero di presenze fatte su un posteggio. Il WS richiede in ingresso il codice del
     * posteggio ed il codice istanza con il quale recupera gli estremi dell'autorizzazione (dai dyn2dati) , con questi
     * ricava l'autorizzazione e calcola il numero di presenze fatte sul mercato o fiera per quel posteggio.
     * 
     * @param request
     * @return
     * 
     */
    @Override
    public PresenzePosteggioResponse presenzePosteggio(PresenzePosteggioRequest request) {

	log.debug("getPresenzePosteggio: token={}, codiceistanza={}, codiceposteggio={}, inserisciAutSeNonTrovata={}",
		new Object[] { request.getToken(), request.getCodIstanza(), request.getCodPosteggio(), request.isInserisciAutSeNonTrovata() });
	setORMHelper(null, request.getToken());
	try {
	    PresenzePosteggioResponse response = new PresenzePosteggioResponse();
	    try {
		int numeroPresenze = 0;
		int numeroPresenzeProp = 0;
		Istanze istanza = istanzeService.findById(new PkId(request.getCodIstanza()));
		if (istanza != null) {
		    Software software = istanza.getSoftware();
		    ORMHelper.setSoftware(software.getCodice());
		    MercatiPresenzeDTO dto = mercatipresenzeStoricoService.getSommaDellePresenze(request.getCodIstanza(),
			    getAut(request.getEstremiAut()), request.getCatMerc(), request.isInserisciAutSeNonTrovata(), null, null, true, null);
		    numeroPresenze = dto.getPresenze() != null ? dto.getPresenze() : 0;
		    response.setNumeroPresenze(numeroPresenze);
		    numeroPresenzeProp = dto.getPresenzeComeProprietario() != null ? dto.getPresenzeComeProprietario() : 0;
		    response.setNumeroPresenzeProp(numeroPresenzeProp);
		} else {
		    String e = "Errore durante il recupero delle presenze per il posteggio (cod posteggio=" + request.getCodPosteggio() +
			       "): Istanza non trovata. (codice istanza=" + request.getCodIstanza() + ")";
		    log.error(e);
		    throw new RuntimeException(e);
		}
	    } catch (Exception e) {
		log.error("getPresenzePosteggio(): {}", e.getMessage());
		throw new RuntimeException("Errore durante il recupero delle presenze per il posteggio (cod posteggio=" + request.getCodPosteggio() +
					   "): " + e.getMessage());
	    }
	    return response;
	} finally {
	    resetThreadLocalVars();
	}
    }

    private Autorizzazioni getAut(EstremiAut estremiAut) {

	Autorizzazioni aut = new Autorizzazioni();
	aut.setAutoriznumero(estremiAut.getAutoriznumero());
	String _autorizdate = estremiAut.getAutorizdata();
	SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	try {
	    Date autorizdate = sdf.parse(_autorizdate);
	    aut.setAutorizdata(autorizdate);
	} catch (ParseException e) {
	    log.error("La data dell'autorizzazione:'{}' non è corretta:{}", _autorizdate, e.getMessage());
	    throw new RuntimeException("La data dell'autorizzazione non è corretta!");
	}
	VwEntilocali autorizcomune = new VwEntilocali();
	autorizcomune.setCodicecomune(estremiAut.getCodiceAutorizcomune());
	aut.setAutorizcomune(autorizcomune);
	Tipologiaregistri autorizregistro = new Tipologiaregistri();
	autorizregistro.getId().setCodice(estremiAut.getCodiceAutorizregistro());
	aut.setTipologiaregistro(autorizregistro);
	return aut;
    }

    @Override
    @WebResult(name = "PresenzeManifestazioneResponse", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/mercati", partName = "PresenzeManifestazioneResponse")
    @WebMethod(operationName = "PresenzeManifestazione", action = "getPresenzeManifestazione")
    public PresenzeManifestazioneResponse presenzeManifestazione(
	    @WebParam(partName = "PresenzeManifestazioneRequest", name = "PresenzeManifestazioneRequest", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/mercati") PresenzeManifestazioneRequest request) {

	String token = request.getToken();
	Integer codiceIstanza = request.getCodiceIstanza();
	int codiceManifestazione = request.getCodiceManifestazione();
	Integer codiceUso = request.getCodiceUso();
	boolean inserisciAutSeNonTrovata = request.isInserisciAutSeNonTrovata();
	String catMerc = request.getCatMerc();
	EstremiAut estremiAut = request.getEstremiAut();
	log.debug(
		"getPresenzeManifestazione: token={}, codiceIstanza={}, inserisciAutSeNonTrovata={}, codiceManifestazione={}, codiceUso={}, catMerc={}",
		new Object[] { token, codiceIstanza, inserisciAutSeNonTrovata, codiceManifestazione, codiceUso, catMerc });
	setORMHelper(null, token);
	try {
	    log.debug("getPresenzeManifestazione: cerco l'istanza [{}]", codiceIstanza);
	    setSoftware(codiceIstanza, codiceManifestazione);
	    PresenzeManifestazioneResponse response = new PresenzeManifestazioneResponse();
	    try {
		log.debug("getPresenzeManifestazione: prima di mercatipresenzeStoricoService.getSommaDellePresenze");
		MercatiPresenzeDTO dto = mercatipresenzeStoricoService.getSommaDellePresenze(codiceIstanza, getAut(estremiAut), catMerc,
			inserisciAutSeNonTrovata, codiceManifestazione, codiceUso, false, null);
		int numeroPresenze = dto.getPresenze() != null ? dto.getPresenze() : 0;
		int numeroPresenzeComeProp = dto.getPresenzeComeProprietario() != null ? dto.getPresenzeComeProprietario() : 0;
		response.setNumeroPresenze(numeroPresenze);
		response.setNumeroPresenzeProp(numeroPresenzeComeProp);
		log.debug("WS getPresenzeManifestazione response is: {}", numeroPresenze);
	    } catch (Exception e) {
		log.error("getPresenzeManifestazione(): {}", e.getMessage());
		throw new RuntimeException("Errore durante il recupero delle presenze: " + e.getMessage());
	    }
	    return response;
	} finally {
	    resetThreadLocalVars();
	}
    }

    private void setSoftware(Integer codiceIstanza, int codiceManifestazione) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	if (istanza != null) {
	    Software software = istanza.getSoftware();
	    ORMHelper.setSoftware(software.getCodice());
	} else {
	    it.gruppoinit.pal.gp.core.domain.Mercati m = mercatiService.findById(new PkId(codiceManifestazione));
	    if (m == null) {
		log.error("Il codice manifestazione non è corretto:{}", codiceManifestazione);
		throw new RuntimeException("Il codice manifestazione non è corretto: " + codiceManifestazione);
	    }
	    ORMHelper.setSoftware(m.getSoftware().getCodice());
	}
    }

    @Override
    @WebResult(name = "PresenzeManifestazioneV2Response", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/mercati", partName = "PresenzeManifestazioneV2Response")
    @WebMethod(operationName = "PresenzeManifestazioneV2", action = "getPresenzeManifestazioneV2")
    public PresenzeManifestazioneV2Response presenzeManifestazioneV2(
	    @WebParam(partName = "PresenzeManifestazioneV2Request", name = "PresenzeManifestazioneV2Request", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/mercati") PresenzeManifestazioneV2Request request) {

	String token = request.getToken();
	Integer codiceIstanza = request.getCodiceIstanza();
	boolean inserisciAutSeNonTrovata = request.isInserisciAutSeNonTrovata();
	int codiceManifestazione = request.getCodiceManifestazione();
	Integer codiceUso = request.getCodiceUso();
	String catMerc = request.getCatMerc();
	Integer presenzeDaAggiungere = request.getPresenzeDaAggiungere();
	EstremiAut estremiAut = request.getEstremiAut();
	log.debug(
		"getPresenzeManifestazioneV2: token={}, codiceIstanza={}, inserisciAutSeNonTrovata={}, codiceManifestazione={}, codiceUso={}, catMerc={}, presenzeDaAggiungere={}",
		new Object[] { token, codiceIstanza, inserisciAutSeNonTrovata, codiceManifestazione, codiceUso, catMerc, presenzeDaAggiungere });
	setORMHelper(null, token);
	try {
	    log.debug("getPresenzeManifestazioneV2: cerco l'istanza [{}]", codiceIstanza);
	    setSoftware(codiceIstanza, codiceManifestazione);
	    PresenzeManifestazioneV2Response response = new PresenzeManifestazioneV2Response();
	    try {
		log.debug("getPresenzeManifestazioneV2: prima di mercatipresenzeStoricoService.getSommaDellePresenze");
		MercatiPresenzeDTO dto = mercatipresenzeStoricoService.getSommaDellePresenze(codiceIstanza, getAut(estremiAut), catMerc,
			inserisciAutSeNonTrovata, codiceManifestazione, codiceUso, false, presenzeDaAggiungere);
		int numeroPresenze = dto.getPresenze() != null ? dto.getPresenze() : 0;
		int numeroPresenzeComeProp = dto.getPresenzeComeProprietario() != null ? dto.getPresenzeComeProprietario() : 0;
		response.setNumeroPresenze(numeroPresenze);
		response.setNumeroPresenzeProp(numeroPresenzeComeProp);
		response.setInseritoAutorizzazione(dto.isInseritoAutorizzazione());
		response.setInseritoPresenze(dto.isInseritoPresenze());
		response.setInseritoSpuntistiMercato(dto.isInseritoSpuntistiMercato());
		log.debug("WS getPresenzeManifestazioneV2 response is: {}", numeroPresenze);
	    } catch (Exception e) {
		log.error("getPresenzeManifestazione(): ", e);
		throw new RuntimeException("Errore durante il recupero delle presenze: " + e.getMessage());
	    }
	    return response;
	} finally {
	    resetThreadLocalVars();
	}
    }
}
