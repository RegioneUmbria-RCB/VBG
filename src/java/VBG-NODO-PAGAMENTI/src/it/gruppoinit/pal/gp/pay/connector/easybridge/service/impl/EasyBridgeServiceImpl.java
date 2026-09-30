package it.gruppoinit.pal.gp.pay.connector.easybridge.service.impl;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.connector.easybridge.EasyBridgeUtils;
import it.gruppoinit.pal.gp.pay.connector.easybridge.EasyBridgeUtils.STATO_ESITO_PAGAMENTO;
import it.gruppoinit.pal.gp.pay.connector.easybridge.service.EasyBridgeService;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.WEBSEasyBridgeInterfaceSoap;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.dp.model.DpInviaEsitoPagamento;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.dp.model.DpInviaEsitoPagamentoResponse;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.dp.model.Output;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model.DpInviaEsitoPagamentoRequest;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model.DpInviaEsitoPagamentoResult;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model.PdpRecuperaRTRequest;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model.PdpRecuperaRTResult;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayProfiliEntiCreditoriService;
import it.gruppoinit.pal.gp.pay.service.helper.RTHelper;
import it.gruppoinit.pal.gp.pay.service.impl.PayPagamentiServiceImpl;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;

@Service
public class EasyBridgeServiceImpl implements EasyBridgeService {

    private static final Logger log = LoggerFactory.getLogger(EasyBridgeServiceImpl.class);
    private static final String DP_STATO_NON_VALIDO = "DP_STATO_NON_VALIDO";
    private static final String DP_PAGAMENTO_SCONOSCIUTO = "DP_PAGAMENTO_SCONOSCIUTO";
    @Autowired
    private PayProfiliEntiCreditoriService payProfiliEntiCreditoriService;
    @Autowired
    private ConfigurazionePagamentiService configurazionePagamentiService;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayPagamentiService payPagamentiService;

    public enum ESITO_OPERAZIONE {
	OK,
	KO
    }

    @Override
    public DpInviaEsitoPagamentoResponse elaboraEsito(DpInviaEsitoPagamento esito) {

	String idDominio = esito.getIdentificativoDominio();
	log.debug("Ricevuto esito pagamento idDominio {}", idDominio);
	// trovare il payprofilienti da iddominio
	PayProfiliEntiCreditori profiloEnte = null;
	try {
	    profiloEnte = payProfiliEntiCreditoriService.findByCodiceProfiloPSP(idDominio);
	    configurazionePagamentiService.configuraRequestPerEnteCreditore(profiloEnte);
	} catch (PayConfigurationException e) {
	    log.error("Errore nella configurazione per idcominio " + idDominio, e);
	    return erroreProfilo(idDominio);
	}
	DpInviaEsitoPagamentoResponse response = new DpInviaEsitoPagamentoResponse();
	Output output = new Output();
	DpInviaEsitoPagamentoRequest req = null;
	log.debug("idDominio {}==> param {}", esito.getIdentificativoDominio(), esito.getParam());
	try {
	    req = getRequestFromParam(esito.getParam());
	} catch (UnsupportedEncodingException e1) {
	    return erroreGenerico("Conversione dati", e1);
	}
	String identificativoUnivocoVersamento = req.getIdentificativoUnivocoVersamento();
	log.debug("identificativoUnivocoVersamento: {}", identificativoUnivocoVersamento);
	log.debug("esitoPagamento: {}", req.getEsitoPagamento());
	PayPosizioniDebitorie posDeb = payPosizioniDebitorieService.findByIUV(identificativoUnivocoVersamento);
	if (posDeb == null) {
	    log.error("esitoPagamento: posizione non trovata esito {}, IUV {}", req.getEsitoPagamento(), identificativoUnivocoVersamento);
	    return posizioneNonTrovata(idDominio, identificativoUnivocoVersamento);
	}
	STATO_ESITO_PAGAMENTO esitoPagamento = new EasyBridgeUtils().fromString(req.getEsitoPagamento());
	String codiceErrore = "";
	String esitoOperazione = ESITO_OPERAZIONE.OK.name();
	//Possibili valori: 
	//  'ACCETTATO' = la richiesta di pagamento è stata correttamente inoltrata a NODO SPC
	//  'ESEGUITO' = pagamento completato con successo secondo indicazione della RT pervenuta da NODO SPC
	//  'NON_ESEGUITO' = tentativo di pagamento fallito secondo indicazione della RT pervenuta da NODO SPC.
	switch (esitoPagamento) {
	case ACCETTATO:
	    // al momento non fa niente dobbiamo capire se è pagabile ugualmente
	    break;
	case ESEGUITO:
	    // setta la posizione come notificato in psp
	    try {
		// dovremmo fare un GetRT per scaricare i dati di pagamento 
		PdpRecuperaRTRequest rtRequest = new PdpRecuperaRTRequest();
		rtRequest.setIdentificativoUnivocoVersamento(req.getIdentificativoUnivocoVersamento());
		rtRequest.setCodiceContestoPagamento(req.getCodiceContestoPagamento());
		this.registraPosizioni(idDominio, profiloEnte, posDeb, rtRequest);
	    } catch (Exception e) {
		log.error("esitoPagamento: errore nel salvataggio delle informazioni della posizione " + posDeb.getId().getCodice() + " esito " +
			  req.getEsitoPagamento() + ", IUV " + identificativoUnivocoVersamento,
			e);
		esitoOperazione = ESITO_OPERAZIONE.KO.name();
		codiceErrore = DP_STATO_NON_VALIDO;
	    }
	    ////
	    break;
	case NON_ESEGUITO:
	    // al momento non fa niente dobbiamo capire se è pagabile ugualmente
	    // Il pagamento è comunque pagabile ma si deve aspettare del tempo per poterlo ripagare. Il tempo dovrebbe dipendere da 	    
	    // quando PagoPa ritorna la RPT non pagata a EasyBridge
	    break;
	default:
	    break;
	}
	//‘ACCETTATO’ = La richiesta di pagamento è stata correttamente inoltrata a NODO SPC.
	//‘ESEGUITO’ = Pagamento completato con successo secondo indicazione della RT pervenuta da NODO SPC.
	//‘NON_ESEGUITO’ = Tentativo di pagamento fallito secondo indicazione della RT pervenuta da NODO SPC.
	// trovare la posizione debitoria dallo IUV
	output.setParam(getEsito(codiceErrore, esitoOperazione));
	response.setDpInviaEsitoPagamentoResult(output);
	return response;
    }

    private void registraPosizioni(String idDominio, PayProfiliEntiCreditori profiloEnte, PayPosizioniDebitorie posDeb,
	    PdpRecuperaRTRequest rtRequest) throws PayException {

	EasyBridgeUtils utils = new EasyBridgeUtils();
	WEBSEasyBridgeInterfaceSoap port = utils.getCaricamentoWsPort(profiloEnte.getPayConnector().getWsCaricamento()); // non riusciamo ad utilizzare il metodo supporta ricevuta perché NON abbiamo il codice contesto pagamento
	String contentBase64 = utils.convertToBase64(rtRequest);
	it.gruppoinit.pal.gp.pay.connector.easybridge.ws.model.Output pdpRecuperaRT = port.pdpRecuperaRT(idDominio, profiloEnte.getIdAppPSP(),
		contentBase64);
	String param = pdpRecuperaRT.getParam();
	PdpRecuperaRTResult result = null;
	try {
	    result = utils.getObjectFromBase64(param, PdpRecuperaRTResult.class);
	    if (!result.getEsitoOperazione().equalsIgnoreCase("OK")) {
		log.error("Il servizio di recupero dello stato delle posizioni debitorie per la pos {} ha tornato il seguente errore {}",
			posDeb.getId().getCodice(), result.getCodiceErrore());
		throw new PayException("Il servizio ha tornato il seguente errore: " + result.getCodiceErrore());
	    }
	    PayPagamenti payPag = this.payPagamentiService.getPagamentoByPosizioneDebitoria(posDeb);
	    if (payPag != null) {
		log.warn("EasyBridgeConnector => elaboraEsito: dati pagamento già inseriti");
	    } else {
		String decodeXmlRT = null;
		if (result.getDatiRestituiti().getXmlRT() != null) {
		    decodeXmlRT = Utilities.decodeBase64Binary(result.getDatiRestituiti().getXmlRT());
		    DatiPagamentoType datiPagXml = RTHelper.popolaDatiPagamentoDaReceiptORicevutaTelematica(decodeXmlRT, null);
		    payPag = PayPagamentiServiceImpl.populateDomainObject(datiPagXml, posDeb, StatoPagamentoType.NOTIFICATO_DA_PSP);
		} else {
		    payPag = new PayPagamenti();
		}
		this.payPagamentiService.registraAvvenutoPagamento(payPag, posDeb, decodeXmlRT);
	    }
	} catch (Exception e) {
	    log.error("EasyBridgeConnector: eccezione nella chiamata elaboraEsitoper la posizione debitoria " + posDeb.getId().getCodice(), e);
	    throw new PayException(e);
	}
    }

    private DpInviaEsitoPagamentoResponse posizioneNonTrovata(String idDominio, String identificativoUnivocoVersamento) {

	log.error("Posizione non trovata idDominio={}, iuv={}", idDominio, identificativoUnivocoVersamento);
	DpInviaEsitoPagamentoResponse response = new DpInviaEsitoPagamentoResponse();
	String param = new EasyBridgeUtils().convertToBase64(result(DP_PAGAMENTO_SCONOSCIUTO, ESITO_OPERAZIONE.KO.name()));
	Output output = new Output();
	output.setParam(param);
	response.setDpInviaEsitoPagamentoResult(output);
	return response;
    }

    private String getEsito(String codiceErrore, String esitoOperazione) {

	return new EasyBridgeUtils().convertToBase64(result(codiceErrore, esitoOperazione));
    }

    private DpInviaEsitoPagamentoRequest getRequestFromParam(String param) throws UnsupportedEncodingException {

	return new EasyBridgeUtils().getObjectFromBase64(param, DpInviaEsitoPagamentoRequest.class);
    }

    private DpInviaEsitoPagamentoResponse erroreProfilo(String idDominio) {

	log.error("idDominio non valido {}", idDominio);
	DpInviaEsitoPagamentoResponse response = new DpInviaEsitoPagamentoResponse();
	String param = new EasyBridgeUtils().convertToBase64(result(DP_PAGAMENTO_SCONOSCIUTO, ESITO_OPERAZIONE.KO.name()));
	Output output = new Output();
	output.setParam(param);
	response.setDpInviaEsitoPagamentoResult(output);
	return response;
    }

    private DpInviaEsitoPagamentoResult result(String codiceErrore, String esitoOperazione) {

	DpInviaEsitoPagamentoResult result = new DpInviaEsitoPagamentoResult();
	result.setCodiceErrore(codiceErrore);
	result.setEsitoOperazione(esitoOperazione);
	return result;
    }

    private DpInviaEsitoPagamentoResponse erroreGenerico(String messaggio, UnsupportedEncodingException e1) {

	log.error(messaggio, e1);
	DpInviaEsitoPagamentoResponse response = new DpInviaEsitoPagamentoResponse();
	String param = new EasyBridgeUtils().convertToBase64(result(DP_STATO_NON_VALIDO, ESITO_OPERAZIONE.KO.name()));
	Output output = new Output();
	output.setParam(param);
	response.setDpInviaEsitoPagamentoResult(output);
	return response;
    }
}
