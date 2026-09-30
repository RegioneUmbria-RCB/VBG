package it.gruppoinit.nlaproxy.service.stc;

import it.gruppoinit.domain.helper.InserisciDeterminaHelper;
import it.gruppoinit.domain.helper.LeggiAttoHelper;
import it.gruppoinit.domain.helper.MovimentiAtti;
import it.gruppoinit.service.AttiService;
import it.gruppoinit.service.DTOService;
import it.gruppoinit.service.MovimentiAttiService;
import it.gruppoinit.utilities.Campi;
import it.gruppoinit.utilities.Utilities;
import it.gruppoinit.ws.client.security.SecurityWSClient;
import it.gruppoinit.ws.wsatti.AttoOut;
import it.gruppoinit.ws.wsatti.ObjectFactory;
import it.init.sigepro.rte.AggiungiDocumentiNLARequest;
import it.init.sigepro.rte.AggiungiDocumentiNLAResponse;
import it.init.sigepro.rte.AllegatoBinarioNLARequest;
import it.init.sigepro.rte.AllegatoBinarioNLAResponse;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoAttivitaNLAResponse;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticaNLARequest;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticheListaNLARequest;
import it.init.sigepro.rte.RichiestaPraticheListaNLAResponse;
import it.init.sigepro.rte.TestNLARequest;
import it.init.sigepro.rte.TestNLAResponse;
import it.init.sigepro.rte.XsdNlaVersion;
import it.init.sigepro.rte.definitions.Nla;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.RiferimentiAttivitaType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.XsdTypesVersion;

import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@WebService(targetNamespace = "http://sigepro.init.it/rte/definitions", name = "Nla", serviceName = "NlaService", portName = "NlaSoap11", endpointInterface = "it.init.sigepro.rte.definitions.Nla")
public class NlaWebService implements Nla {

    private static final Logger log = LoggerFactory.getLogger(NlaWebService.class);
    public static String NLAREQUEST_ALTRIDATI_PARAMNAME_TIPOOPERAZIONE = "tipo_operazione";
    private AttiService attiService;
    private DTOService dtoService;
    private StcWebServiceClient stcWebServiceClient;
    private MovimentiAttiService movimentiAttiService;
    private SecurityWSClient securityWSClient;

    @Autowired
    public void setSecurityWSClient(SecurityWSClient securityWSClient) {

	this.securityWSClient = securityWSClient;
    }

    @Autowired
    public void setAttiService(AttiService attiService) {

	this.attiService = attiService;
    }

    @Autowired
    public void setDtoService(DTOService dtoService) {

	this.dtoService = dtoService;
    }

    @Autowired
    public void setMovimentiAttiService(MovimentiAttiService movimentiAttiService) {

	this.movimentiAttiService = movimentiAttiService;
    }

    public void setStcWebServiceClient(StcWebServiceClient stcWebServiceClient) {

	this.stcWebServiceClient = stcWebServiceClient;
    }

    @Override
    public InserimentoPraticaNLAResponse inserimentoPraticaNLA(InserimentoPraticaNLARequest inserimentoPraticaNLARequest) {

	InserimentoPraticaNLAResponse inserimentoPraticaNLAResponse = new InserimentoPraticaNLAResponse();
	RiferimentiPraticaType rifPratica = new RiferimentiPraticaType();
	rifPratica.setIdPratica(getIdPratica(inserimentoPraticaNLARequest.getSportelloMittente().getIdEnte(), inserimentoPraticaNLARequest
		.getDettaglioPratica().getIdPratica()));
	rifPratica.setNumeroPratica(inserimentoPraticaNLARequest.getDettaglioPratica().getNumeroPratica());
	rifPratica.setDataPratica(inserimentoPraticaNLARequest.getDettaglioPratica().getDataPratica());
	rifPratica.setDataProtocolloGenerale(inserimentoPraticaNLARequest.getDettaglioPratica().getDataProtocolloGenerale());
	rifPratica.setNumeroProtocolloGenerale(inserimentoPraticaNLARequest.getDettaglioPratica().getNumeroProtocolloGenerale());
	inserimentoPraticaNLAResponse.setDettaglioPratica(rifPratica);
	return inserimentoPraticaNLAResponse;
    }

    @Override
    public RichiestaPraticheListaNLAResponse richiestaPraticheListaNLA(RichiestaPraticheListaNLARequest richiestaPraticheListaNLARequest) {

	throw new NotImplementedException("Metodo non Implementato");
    }

    @Override
    public AllegatoBinarioNLAResponse allegatoBinarioNLA(AllegatoBinarioNLARequest allegatoBinarioNLARequest) {

	throw new NotImplementedException("Metodo non Implementato");
    }

    @Override
    public RichiestaPraticaNLAResponse richiestaPraticaNLA(RichiestaPraticaNLARequest richiestaPraticaNLARequest) {

	throw new NotImplementedException("Metodo non Implementato");
    }

    @Override
    public InserimentoAttivitaNLAResponse inserimentoAttivitaNLA(InserimentoAttivitaNLARequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("inserimentoAttivita() - invocata l'operazione InserimentoAttivitaNLA");
	}
	try {
	    stcWebServiceClient.checkToken(request.getToken());
	} catch (Exception e) {
	    log.error("Errore nella verifica del token STC: {}, {}", e.getMessage(), e);
	    throw new RuntimeException("Errore nella verifica del token STC: " + e.getMessage(), e);
	}
	String tipoOperazione = Utilities.getTipoOperazioneFromRequest(request);
	if (log.isDebugEnabled()) {
	    log.debug(NLAREQUEST_ALTRIDATI_PARAMNAME_TIPOOPERAZIONE + " richiesta={}", tipoOperazione);
	}
	InserimentoAttivitaNLAResponse inserimentoAttivitaNLAResponse = null;
	if (tipoOperazione.equalsIgnoreCase(Campi.TIPOOPERAZIONE_INVIA_DETERMINA)) {
	    inserimentoAttivitaNLAResponse = operazioneInviaDetermina(request);
	    return inserimentoAttivitaNLAResponse;
	}
	// Leggi atto non vien più fatto come una notifica, ma come un chiamata ws dal backoffice 
	//	if (tipoOperazione.equalsIgnoreCase(Campi.TIPOOPERAZIONE_LEGGI_ATTO)) {
	//	    inserimentoAttivitaNLAResponse = operazioneLeggiAtto(request);
	//	    return inserimentoAttivitaNLAResponse;
	//	} 
	else {
	    throw new RuntimeException();
	}
	//	// 2 INSERIRE IN ISTANZE_COMUNICAZIONI_RI CON IL CODICEOGGETTO RESTITUITO
	//	InserimentoAttivitaNLAResponse response = new InserimentoAttivitaNLAResponse();
	//	RiferimentiAttivitaType rifAtt = new RiferimentiAttivitaType();
	//	rifAtt.setIdAttivita(getIdAttivita(request.getSportelloMittente().getIdEnte(), request.getDatiAttivita().getIdAttivita()));
	//	rifAtt.setIdPratica(getIdPratica(request.getSportelloMittente().getIdEnte(), request.getDatiAttivita().getIdPratica()));
	//	response.setDettaglioAttivita(rifAtt);
	//	return response;
    }

    private InserimentoAttivitaNLAResponse operazioneInviaDetermina(InserimentoAttivitaNLARequest request) {

	// Codice Movimento
	Integer codiceMovimento = Integer.parseInt(request.getDatiAttivita().getIdAttivita());
	//InserisciDeterminaStringResponse inserisciDeterminaStringResponse = factory.createInserisciDeterminaStringResponse();
	InserimentoAttivitaNLAResponse response = new InserimentoAttivitaNLAResponse();
	RiferimentiAttivitaType rifAtt = new RiferimentiAttivitaType();
	it.gruppoinit.ws.wsatti.inserisciattistring.AttoInseritoOut attoInseritoOut = new it.gruppoinit.ws.wsatti.inserisciattistring.AttoInseritoOut();
	try {
	    String token = securityWSClient.getToken();
	    log.debug("token: {}", token);
	    String idcomune = securityWSClient.getIdcomune(token);
	    log.debug("idcomune: {}", idcomune);
	    log.debug("operazioneInviaDetermina# recupero i dati inviati dalla notifica");
	    InserisciDeterminaHelper determinaHelper = dtoService.inserimentoAttivitaNLARequestToInserisciDeterminaHelper(request);
	    // Deve tornare una response di tipo InserimentoAttivitaNLAResponse o la response del ws e poi fare un nuovo DTO
	    log.debug("operazioneInviaDetermina# Inzio inserimento determina....");
	    attoInseritoOut = attiService.inserisciDetermina(determinaHelper, token);
	    //	    // TEST finto funzionante
	    //	    attoInseritoOut.setErrore(null);
	    log.debug("operazioneInviaDetermina# Analizzo la response del ws inserisciDeterminaString");
	    if (StringUtils.isNotBlank(attoInseritoOut.getErrore())) {
		ErroreType erroreType = new ErroreType();
		erroreType.setDescrizione(attoInseritoOut.getMessaggio());
		erroreType.setNumeroErrore("01");
		response.getDettaglioErrore().add(erroreType);
		log.error("operazioneInviaDetermina# Il ws ha ritornato l'errore: {} ", attoInseritoOut.getMessaggio());
	    } else {
		rifAtt.setNumeroProtocolloGenerale(String.valueOf(attoInseritoOut.getIdDocumento()));
		rifAtt.setIdAttivita(request.getDatiAttivita().getIdAttivita());
		rifAtt.setIdPratica(request.getDatiAttivita().getIdPratica());
		log.debug("operazioneInviaDetermina# Recupero le informazioni dalla response e inserisco il record su MOVIMENTI_ATTI ");
		log.debug("operazioneInviaDetermina# popolo l'oggetto movimenti atti ");
		MovimentiAtti movimentiAtti = populateMovimentiAtti(attoInseritoOut, idcomune, codiceMovimento);
		log.debug("operazioneInviaDetermina# inserisco l'oggetto movimenti atti ");
		movimentiAttiService.insert(movimentiAtti);
	    }
	} catch (Exception e) {
	    ErroreType erroreType = new ErroreType();
	    erroreType.setDescrizione(e.getMessage());
	    erroreType.setNumeroErrore("999999");
	    response.getDettaglioErrore().add(erroreType);
	    log.error("operazioneInviaDetermina# Il ws ha ritornato l'errore: {}[{}] ", new Object[] { e.getMessage(), e });
	    //	    throw new RuntimeException("Errore durante l'operazione di invio determina: " + e.getMessage());
	}
	response.setDettaglioAttivita(rifAtt);
	return response;
    }

    private MovimentiAtti populateMovimentiAtti(it.gruppoinit.ws.wsatti.inserisciattistring.AttoInseritoOut attoInseritoOut, String idcomune,
	    Integer codicemovimento) throws Exception {

	MovimentiAtti movimentiAtti = new MovimentiAtti();
	movimentiAtti = new MovimentiAtti();
	movimentiAtti.setAnno(attoInseritoOut.getAnno());
	movimentiAtti.setDataRicezioneAtto(null);
	movimentiAtti.setDataRichiestaAtto(new Date());
	movimentiAtti.setIdcomune(idcomune);
	movimentiAtti.setIdDocumento(attoInseritoOut.getIdDocumento());
	movimentiAtti.setMovimenti(codicemovimento);
	movimentiAtti.setNumero(attoInseritoOut.getNumero());
	movimentiAtti.setStato(0);
	String tipoDocumento = StringUtils.left(StringUtils.defaultIfEmpty(attoInseritoOut.getTipoDocumento(), ""), 4);
	movimentiAtti.setTipoDocumento(tipoDocumento);
	return movimentiAtti;
    }

    private InserimentoAttivitaNLAResponse operazioneLeggiAtto(InserimentoAttivitaNLARequest request) {

	ObjectFactory factory = new ObjectFactory();
	AttoOut attoOut = factory.createAttoOut();
	InserimentoAttivitaNLAResponse response = new InserimentoAttivitaNLAResponse();
	RiferimentiAttivitaType rifAtt = new RiferimentiAttivitaType();
	rifAtt.setIdAttivita(request.getDatiAttivita().getIdAttivita());
	rifAtt.setIdPratica(request.getDatiAttivita().getIdPratica());
	response.setDettaglioAttivita(rifAtt);
	log.debug("operazioneLeggiAtto#  recupero i dati inviati dalla notifica");
	LeggiAttoHelper leggiAttoHelper = dtoService.inserimentoAttivitaNLARequestToLeggiAttoHelper(request);
	// Deve tornare una response di tipo InserimentoAttivitaNLAResponse o la response del ws e poi fare un nuovo DTO
	log.debug("operazioneLeggiAtto# Inzio lettura atto....");
	attoOut = attiService.leggiAtto(leggiAttoHelper);
	//TODO Converto to NLA RESPONSE
	return response;
    }

    @Override
    public TestNLAResponse testNLA(TestNLARequest request) {

	TestNLAResponse response = new TestNLAResponse();
	response.setNlaXsdVersion(XsdNlaVersion.V_1_13);
	response.setTypesXsdVersion(XsdTypesVersion.V_1_13);
	return response;
    }

    public static String getIdAttivita(String idEnteMittente, String idAttivita) {

	return idEnteMittente + idAttivita;
    }

    public static String getIdPratica(String idEnteMittente, String idPratica) {

	return idEnteMittente + idPratica;
    }

    @Override
    @WebResult(name = "AggiungiDocumentiNLAResponse", targetNamespace = "http://sigepro.init.it/rte", partName = "AggiungiDocumentiNLAResponse")
    @WebMethod(operationName = "AggiungiDocumentiNLA")
    public AggiungiDocumentiNLAResponse aggiungiDocumentiNLA(
	    @WebParam(partName = "AggiungiDocumentiNLARequest", name = "AggiungiDocumentiNLARequest", targetNamespace = "http://sigepro.init.it/rte") AggiungiDocumentiNLARequest arg0) {

	throw new NotImplementedException("Metodo non Implementato");
    }
}
