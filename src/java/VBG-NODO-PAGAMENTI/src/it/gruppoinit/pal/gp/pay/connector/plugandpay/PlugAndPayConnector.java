/**
 * 
 */
package it.gruppoinit.pal.gp.pay.connector.plugandpay;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.net.ssl.TrustManager;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.common.Servizio;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.common.TipoPagatore;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver.IPlugAndPayDeliver;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver.RichiestaRicercaPosizionePerIdentificavo;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver.RispostaRicercaPosizionePerIdentificavo;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver.StatoPosizione;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.Accertamento;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.ArrayOfAccertamento;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.ArrayOfErroreDiValidazionePosizione;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.Creditore;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.Debitore;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.ErroreDiValidazionePosizione;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.Esito;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.EsitoDiCaricamento;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.IPlugAndPayFeed;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.IPlugAndPayFeedCaricaPosizioneSoapFaultErroreEasyPAFaultFaultMessage;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.IPlugAndPayFeedCaricaPosizioneSoapFaultErroreInternoFaultFaultMessage;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.IPlugAndPayFeedCaricaPosizioneSoapFaultOperazioneProibitaFaultFaultMessage;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.Nazione;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.ObjectFactory;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.Posizione;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.RichiestaCaricaPosizione;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.RichiestaRimuoviPosizione;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.RispostaCaricaPosizione;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed.RispostaRimuoviPosizione;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.generatorpdf.IGeneratorPdfBytes;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.generatorpdf.RichiestaCreaAvvisoPdf;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.generatorpdf.RichiestaCreaRTPdf;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.generatorpdf.RispostaCreaAvvisoPdf;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.generatorpdf.RispostaCreaRTPdf;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.ArrayOfEsitoInvioCarrelloPosizioni;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.ArrayOfParametroPosizione;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.ArrayOfPosizione;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.EsitoInvioCarrelloPosizioni;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.IPlugAndPayPayment;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.RichiestaDownloadDatiRicevuta;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.RichiestaInviaCarrelloPosizioni;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.RispostaDownloadDatiRicevuta;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.RispostaInviaCarrelloPosizioni;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PayRichieste;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.TipiEvento;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.parameters.ParametroChiaveApplicationCodeIUV;
import it.gruppoinit.pal.gp.pay.parameters.ParametroDescrizioneCausalePSP;
import it.gruppoinit.pal.gp.pay.service.PagoPAService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayRichiesteService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.CodiceAvvisoHelper;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.ParametriConnettoreHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RTHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RiferimentiPosizioniDebitorieHelper;
import it.gruppoinit.pal.gp.pay.service.impl.PagoPAServiceImpl;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.utils.TrustAllX509TrustManager;

/**
 * @author the lion
 *
 */
public class PlugAndPayConnector extends AbstractPayConnector implements IPayConnector {

    private static final Logger log = LoggerFactory.getLogger(PlugAndPayConnector.class);
    private static final String CODICE_ISO_3166_ITA = "IT";
    private static final String SEPARATORE_CODICI_ERRORE = " - ";
    private static final String CODICE_ERRORE_IUV_DUPLICATO = "IuvNonUnivoco";
    private static final String MSG_ERRORE_POSIZIONE_GIA_ANNULLATA = "risulta già revocata";
    private IPlugAndPayFeed feedService;
    private IPlugAndPayDeliver deliverService;
    private IPlugAndPayPayment paymentService;
    private IGeneratorPdfBytes pdfService;
    private ObjectFactory feedof = new ObjectFactory();
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    private PagoPAService pagoPAService;
    private PayRichiesteService payRichiesteService;
    private PaySessioniPagamentoService paySessioniPagamentoService;
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;

    @Autowired
    public void setPayPosizioniDebitorieService(PayPosizioniDebitorieService payPosizioniDebitorieService) {

	this.payPosizioniDebitorieService = payPosizioniDebitorieService;
    }

    @Autowired
    public void setPagoPAService(PagoPAService pagoPAService) {

	this.pagoPAService = pagoPAService;
    }

    @Autowired
    public void setPayRichiesteService(PayRichiesteService payRichiesteService) {

	this.payRichiesteService = payRichiesteService;
    }

    @Autowired
    public void setPaySessioniPagamentoService(PaySessioniPagamentoService paySessioniPagamentoService) {

	this.paySessioniPagamentoService = paySessioniPagamentoService;
    }

    @Autowired
    public void setPosizioniDebitorieCommandService(PosizioniDebitorieCommandService posizioniDebitorieCommandService) {

	this.posizioniDebitorieCommandService = posizioniDebitorieCommandService;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	//recupero la configurazione dell'ente nel nodo pagamenti
	PayProfiliEntiCreditori profiloEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	ElencoPosizioniDebitorieEsitoType retEsito = new ElencoPosizioniDebitorieEsitoType();
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		//evento invocazione servizio wsfeed
		//popolamento messaggio WS feed per il caricamento di una singola posizione
		RichiestaCaricaPosizione loadRequest = new RichiestaCaricaPosizione();
		loadRequest.setIdApplicazione(profiloEnte.getIdAppPSP());
		loadRequest.setPosizione(this.caricaPosizioneFeed(payPos, profiloEnte));
		//esito restituito al nodo pagamenti relativo al caricamento della posizione
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		esitoPos.setEsito(false);
		esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
		try {
		    //invocazione WS feed
		    if (log.isInfoEnabled()) {
			log.info("registraPosizioniDebitorie - invio a Plug&Pay della posizione {}, IUV: {}", payPos.getId(), payPos.getIuv());
		    }
		    RispostaCaricaPosizione result = this.getFeedServicePort().caricaPosizione(loadRequest);
		    EsitoDiCaricamento esitoPsp = result.getEsitoDiCaricamento().getValue();
		    //se l'esito contiene l'errore IUV duplicato restituisco esito OK perché significa che eravamo già riusciti a caricare la posizione oin P&P
		    //ma non è stato possibile gestire l'esito OK al primo caricamento a causa di errori temporanei
		    if (Esito.OK.equals(esitoPsp.getEsito()) || checkErroreIuvDuplicato(esitoPsp)) {
			esitoPos.setEsito(true);
			esitoPos.setStato(StatoPagamentoType.TRASMESSO_A_PSP);
		    } else {
			ArrayOfErroreDiValidazionePosizione elencoErrori = null;
			if (esitoPsp.getErroriDiValidazione() != null) {
			    StringBuilder sbErr = new StringBuilder();
			    StringBuilder sbCod = new StringBuilder();
			    elencoErrori = esitoPsp.getErroriDiValidazione();
			    List<ErroreDiValidazionePosizione> errors = elencoErrori.getErroreDiValidazionePosizione();
			    Iterator<ErroreDiValidazionePosizione> errIter = errors.iterator();
			    while (errIter.hasNext()) {
				ErroreDiValidazionePosizione err = errIter.next();
				if (StringUtils.isNotBlank(err.getCodice())) {
				    sbCod.append(err.getCodice());
				}
				if (StringUtils.isNotBlank(err.getDescrizione())) {
				    sbErr.append(err.getDescrizione());
				}
				if (errIter.hasNext()) {
				    sbCod.append(SEPARATORE_CODICI_ERRORE);
				    sbErr.append(SEPARATORE_CODICI_ERRORE);
				}
			    }
			    esitoPos.setMessaggio(sbErr.toString());
			    esitoPos.setCodiceErrore(sbCod.toString());
			}
		    }
		} catch (Exception e) {
		    log.error("registraPosizioniDebitorie - Errore nell'invocazione di DigitBusFeed.CaricaPosizione: ", e);
		    handleException(e, esitoPos);
		}
		//IMPOSTO NELLA RICHIESTA IL RIFERIMENTO ALL'EVENTO OUT (CHIAMATA AL WS FEED)		
		retEsito.getEsitoPosizione().add(esitoPos);
	    }
	}
	return retEsito;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException {

	//recupero la configurazione dell'ente nel nodo pagamenti
	PayProfiliEntiCreditori profiloEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	ElencoPosizioniDebitorieEsitoType retEsito = new ElencoPosizioniDebitorieEsitoType();
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		//evento invocazione servizio wsfeed
		//popolamento messaggio WS feed per l'annullamento della posizione
		RichiestaRimuoviPosizione delRequest = new RichiestaRimuoviPosizione();
		delRequest.setIdApplicazione(profiloEnte.getIdAppPSP());
		delRequest.setCodiceEnte(profiloEnte.getCfCodiceProfiloPSP());
		delRequest.setCodiceIdentificativo(payPos.getIuv());
		//esito restituito al nodo pagamenti relativo al caricamento della posizione
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		try {
		    //invocazione WS feed
		    RichiestaRimuoviPosizione rrp = new RichiestaRimuoviPosizione();
		    rrp.setCodiceEnte(profiloEnte.getCfCodiceProfiloPSP());
		    rrp.setIdApplicazione(profiloEnte.getIdAppPSP());
		    rrp.setCodiceIdentificativo(payPos.getIuv());
		    RispostaRimuoviPosizione result = this.getFeedServicePort().rimuoviPosizione(rrp);
		    if (Esito.OK.equals(result.getEsito())) {
			esitoPos.setEsito(true);
			//poiché la verifica dello stato non ci restituisce lo stato annullato considero annullata la posizione alla ricezione di esito OK
			esitoPos.setStato(pagatoOffline ? StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO : StatoPagamentoType.ANNULLATO);
			esitoPos.setMessaggio("Posizione debitoria annullata con successo");
		    } else {
			esitoPos.setEsito(false);
			esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
			esitoPos.setMessaggio("Annullamento della posizione debitoria non riuscito");
		    }
		} catch (Exception e) {
		    log.error("annullaPosizioniDebitorie - Errore nell'invocazione di DigitBusFeed.RimuoviPosizione: ", e);
		    handleException(e, esitoPos);
		    if (checkErrorePosizioneGiaAnnullata(e)) {
			esitoPos.setEsito(true);
			esitoPos.setMessaggio("Posizione già annullata in precedenza");
		    }
		}
		retEsito.getEsitoPosizione().add(esitoPos);
	    }
	}
	return retEsito;
    }

    @Override
    public boolean supportaPagamentoOnTheFly() {

	return true;
    }

    @Override
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(PosizioniDebitorieCommand cmd) throws PayException {

	AttivaPagamentoOnTheFlyResponseType result = new AttivaPagamentoOnTheFlyResponseType();
	List<PayRegistrazioniContabili> prcs = cmd.getRegistrazioniPosizioni();
	if (prcs.isEmpty()) {
	    throw new PayException("dati della posizione da attivare mancanti");
	}
	PayProfiliEntiCreditori profEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	//predispongo la request al WS di P&P
	RichiestaInviaCarrelloPosizioni pspReq = new RichiestaInviaCarrelloPosizioni();
	pspReq.setIdApplicazione(profEnte.getIdAppPSP());
	pspReq.setUrlBack(profEnte.getUrlAnnullamentoPagamento());
	pspReq.setUrlReturn(profEnte.getUrlEsitoPagamento());
	ArrayOfPosizione posArray = new ArrayOfPosizione();
	for (PayRegistrazioniContabili prc : prcs) {
	    Set<PayPosizioniDebitorie> posizioniDebitories = prc.getPosizioniDebitorie();
	    for (PayPosizioniDebitorie payPos : posizioniDebitories) {
		posArray.getPosizione().add(caricaPosizionePayment(payPos, true));
		EsitoOperazionePosizioneDebitoriaType esitoNodo = new EsitoOperazionePosizioneDebitoriaType();
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoNodo, payPos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		result.getPosizioneInserita().add(esitoNodo);
	    }
	}
	pspReq.setCarrello(posArray);
	String logMsg = "invio a Plug&Pay il carrello ";
	//evento invocazione servizio wsfeed
	if (log.isInfoEnabled()) {
	    log.info("attivaPagamentoOnTheFly - {}", logMsg);
	}
	boolean isAllOk = false;
	AttivaSessionePagamentoResponseType sesResp = new AttivaSessionePagamentoResponseType();
	EsitoOperazionePosizioneDebitoriaType esitoKO = new EsitoDocumentoPosizioneDebitoriaType();
	esitoKO.setEsito(false);
	RiferimentiPosizioniDebitorieHelper esitiHelper = new RiferimentiPosizioniDebitorieHelper(result.getPosizioneInserita());
	try {
	    RispostaInviaCarrelloPosizioni pspResp = this.getPaymentServicePort().inviaCarrelloPosizioni(pspReq);
	    ArrayOfEsitoInvioCarrelloPosizioni esitiPsp = pspResp.getEsitiInvioCarrelloPosizioni();
	    if (!esitiPsp.getEsitoInvioCarrelloPosizioni().isEmpty()) {
		//la lista contiene sempre solo un elemento
		List<EsitoInvioCarrelloPosizioni> esitoPsps = esitiPsp.getEsitoInvioCarrelloPosizioni();
		for (EsitoInvioCarrelloPosizioni esitoPsp : esitoPsps) {
		    it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Esito esitoVal = esitoPsp.getEsito();
		    String iuv = esitoPsp.getIdentificativoPosizione(); // nel caso OTF è lo IUV
		    EsitoOperazionePosizioneDebitoriaType esitoPos = (EsitoOperazionePosizioneDebitoriaType) esitiHelper
			    .findRiferimentoPosizioneByIUV(iuv);
		    if (esitoVal.equals(it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Esito.OK)) {
			isAllOk = true;
			esitoPos.setEsito(true);
			esitoPos.setMessaggio("Pagamento OTF attivato");
			esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
			if (log.isInfoEnabled()) {
			    log.info("attivaPagamentoOnTheFly - pagamento OTF attivato per la posizione: {}, IUV: {}", esitoPos.getIdPosizione(),
				    iuv);
			}
		    } else {
			isAllOk = false;
			esitoPos.setEsito(false);
			esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
			StringBuilder sbErr = new StringBuilder("Errori: ");
			StringBuilder sbErrCode = new StringBuilder("Codici errore: ");
			it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.ArrayOfErroreDiValidazionePosizione pspErrors = esitoPsp
				.getErroriDiValidazione();
			Iterator<it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.ErroreDiValidazionePosizione> errIter = pspErrors
				.getErroreDiValidazionePosizione().iterator();
			while (errIter.hasNext()) {
			    it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.ErroreDiValidazionePosizione pspError = errIter.next();
			    sbErr.append(pspError.getDescrizione());
			    sbErrCode.append(pspError.getCodice());
			    if (errIter.hasNext()) {
				sbErr.append(SEPARATORE_CODICI_ERRORE);
				sbErrCode.append(SEPARATORE_CODICI_ERRORE);
			    }
			}
			esitoPos.setMessaggio(sbErr.toString());
			esitoPos.setCodiceErrore(sbErrCode.toString());
		    }
		}
	    } else {
		isAllOk = false;
		esitoKO.setMessaggio("Plug&Play non ha restituito l'esito dell'operazione di caricamento della posizione.");
	    }
	    //esito creazione sessione pagamento da restituire al service chiamante
	    sesResp.setEsito(false);
	    if (isAllOk && StringUtils.isNotBlank(pspResp.getUrlRedirect())) {
		sesResp.setEsito(true);
		sesResp.setPayUrl(pspResp.getUrlRedirect());
		sesResp.setIdSessione(pspResp.getIdTransazione());
		String msg = "sessione di pagamento attivata";
		sesResp.setDescEsito(msg);
		msg += ", id sessione: " + pspResp.getIdTransazione();
		if (log.isInfoEnabled()) {
		    log.info("attivaPagamentoOnTheFly - {}", msg);
		}
	    } else {
		sesResp.setDescEsito("sessione di pagamento non attivata da Plug&Pay");
	    }
	    result.setSessionePagamento(sesResp);
	} catch (Exception e) {
	    StringBuilder sbExc = new StringBuilder("si è verificato un errore durante il tentativo di attivazione del pagamento OTF , errore: ")
		    .append(e);
	    log.error("attivaPagamentoOnTheFly - " + sbExc.toString(), e);
	    this.handleException(e, esitoKO);
	}
	return result;
    }

    @Override
    public ElencoDocumentiEsitoType scaricaRicevuteTelematiche(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	ElencoDocumentiEsitoType elencoDocumentiEsitoType = new ElencoDocumentiEsitoType();
	for (PayPosizioniDebitorie pos : cmd.getPosizioni()) {
	    EsitoDocumentoPosizioneDebitoriaType esitoRt = this.scaricaRicevutaPdfWs(pos);
	    elencoDocumentiEsitoType.getEsitoPosizione().add(esitoRt);
	}
	return elencoDocumentiEsitoType;
    }

    @Override
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	ElencoStatoPosizioniType retEsiti = new ElencoStatoPosizioniType();
	PayProfiliEntiCreditori profEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	for (PayPosizioniDebitorie payPos : cmd.getPosizioni()) {
	    //evento invocazione servizio wsfeed
	    StatoPosizioneType retStatus = new StatoPosizioneType();
	    retStatus.setEsito(false);
	    retStatus.setMessaggio("Invocazione del servizio verifica stato posizione ...");
	    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos,
		    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	    RichiestaRicercaPosizionePerIdentificavo reqPos = new RichiestaRicercaPosizionePerIdentificavo();
	    reqPos.setCodiceIdentificativo(payPos.getIuv());
	    reqPos.setIdApplicazione(profEnte.getIdAppPSP());
	    reqPos.setCodiceEnte(profEnte.getCfCodiceProfiloPSP());
	    try {
		RispostaRicercaPosizionePerIdentificavo resp = this.getDeliverServicePort().ricercaPosizionePerIdentificavo(reqPos);
		if (resp.getPosizione() != null && !resp.getPosizione().isNil()) {
		    it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver.Posizione respPos = resp.getPosizione().getValue();
		    //le posizioni per cui è stato richiesto l'annullamento non vengono verificate perché in caso di chiamata con esito positivo 
		    //al servizio di annullamento lo stato della posizione viene già impostato su ANNULLATO
		    //simulazione pagamento avvenuto per test rendicontazione
		    if (respPos.getStatoPosizione().equals(StatoPosizione.NON_PAGATA)) {
			retStatus.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		    }
		    //gli stati PAGATA e PAGATA_PARZIALMENTE vengono gestiti comunque come (pagamento) NOTIFICATO_DA_PSP perchè non si può pagare due volte la stessa posizione
		    else if (respPos.getStatoPosizione().equals(StatoPosizione.PAGATA)
			    || respPos.getStatoPosizione().equals(StatoPosizione.PAGATA_PARZIALMENTE)) {
			//scarico in modo sincrono i dati della ricevuta telematica per ecuperare i dati del pagamento
			retStatus = this.scaricaRicevutaXmlWs(payPos);
			//se il download della ricevuta ha dato esito negativo allora salvo una richiesta per la ricezione della notifica di pagamento schedulata 
			if (!retStatus.isEsito()) {
			    retStatus.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP);
			    //memorizzo la richiesta attualmente collegata alla posizione debitoria per poterla ripristinare dopo la chiamata 
			    //che in automatico imposta nella posizione il riferiemento alla nuova richiesta appena registrata
			    PayRichieste ricVerifica = payPos.recuperaRichiestaCorrente();
			    this.payRichiesteService.registraRichiestaPerPosizioneDebitoriaTrans(payPos, TipiEvento.RENDICONTAZIONE_PAGAMENTO_PSP);
			    payPos.impostaRichiestaCorrente(ricVerifica);
			}
		    }
		    retStatus.setEsito(true);
		    retStatus.setMessaggio(
			    retStatus.getMessaggio() + "verifica dello stato completata con successo per la posizione " + payPos.getIuv());
		} else {
		    retStatus
			    .setMessaggio(retStatus.getMessaggio() +
					  "Errore nell'invocazione del servizio DigitBusDeliver per la verifica dello stato del pagamento: identificativo di pagamento sconosciuto " +
					  payPos.getIuv());
		}
	    } catch (Exception e) {
		log.error("verificaStatoPagamenti - Errore nell'invocazione di DigitBusDeliver.ricercaPosizionePerIdentificavo: ", e);
		retStatus.setMessaggio(retStatus.getMessaggio() +
				       "Errore nell'invocazione del servizio DigitBusDeliver per la verifica dello stato del pagamento: " + e);
		retStatus.setEsito(false);
	    }
	    retEsiti.getStatoPosizioni().add(retStatus);
	}
	return retEsiti;
    }

    @Override
    public AttivaSessionePagamentoResponseType attivaSessionePagamento(PayPosizioniDebitorie payPos) throws PayException {

	AttivaSessionePagamentoResponseType sesResp = new AttivaSessionePagamentoResponseType();
	if (payPos != null && payPos.getId() != null && payPos.getId().getCodice() != null) {
	    PayProfiliEntiCreditori profEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	    //predispongo la request al WS di P&P
	    RichiestaInviaCarrelloPosizioni pspReq = new RichiestaInviaCarrelloPosizioni();
	    pspReq.setIdApplicazione(profEnte.getIdAppPSP());
	    pspReq.setUrlBack(profEnte.getUrlAnnullamentoPagamento());
	    pspReq.setUrlReturn(profEnte.getUrlEsitoPagamento());
	    ArrayOfPosizione posArray = new ArrayOfPosizione();
	    posArray.getPosizione().add(caricaPosizionePayment(payPos, false));
	    pspReq.setCarrello(posArray);
	    String logMsg = "attivazione della sessione di pagamento in Plug&Pay per lla posizione OTF " + payPos.getId() + ", IUV: " +
			    payPos.getIuv();
	    //evento invocazione servizio digitbuspayment
	    StringBuilder sbInfo = new StringBuilder("posizione: ").append(payPos.getId()).append(", IUV: ").append(payPos.getIuv());
	    if (log.isInfoEnabled()) {
		log.info("attivaSessionePagamento - {}", logMsg);
	    }
	    try {
		//esito creazione sessione pagamento da restituire al service chiamante
		sesResp.setEsito(false);
		RispostaInviaCarrelloPosizioni pspResp = this.getPaymentServicePort().inviaCarrelloPosizioni(pspReq);
		sbInfo.append(", idSessione: ").append(pspResp.getIdTransazione()).append(", URL: ").append(pspResp.getUrlRedirect());
		ArrayOfEsitoInvioCarrelloPosizioni esitiPsp = pspResp.getEsitiInvioCarrelloPosizioni();
		if (!esitiPsp.getEsitoInvioCarrelloPosizioni().isEmpty()) {
		    //la lista contiene sempre solo un elemento
		    EsitoInvioCarrelloPosizioni esitoPsp = esitiPsp.getEsitoInvioCarrelloPosizioni().get(0);
		    sesResp.setPayUrl(pspResp.getUrlRedirect());
		    sesResp.setIdSessione(pspResp.getIdTransazione());
		    it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Esito esitoVal = esitoPsp.getEsito();
		    if (esitoVal.equals(it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Esito.OK)) {
			sesResp.setEsito(true);
			sesResp.setDescEsito("Sessione di pagamento attivata");
		    } else {
			StringBuilder sbErr = new StringBuilder("Errore nell'attivazione della sessione di pagamento: ");
			it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.ArrayOfErroreDiValidazionePosizione pspErrors = esitoPsp
				.getErroriDiValidazione();
			Iterator<it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.ErroreDiValidazionePosizione> errIter = pspErrors
				.getErroreDiValidazionePosizione().iterator();
			while (errIter.hasNext()) {
			    it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.ErroreDiValidazionePosizione pspError = errIter.next();
			    sbErr.append(pspError.getDescrizione());
			    if (errIter.hasNext()) {
				sbErr.append(SEPARATORE_CODICI_ERRORE);
			    }
			}
			sesResp.setDescEsito(sbErr.toString());
		    }
		} else {
		    //se non ci sono esiti di caricamento posizioni ma c'è l'id transazione e lURL redirect l'esito è comunque positivo
		    if (StringUtils.isBlank(pspResp.getIdTransazione()) || StringUtils.isBlank(pspResp.getUrlRedirect())) {
			sesResp.setDescEsito("Errore nell'attivazione della sessione di pagamento, URL o id sessione non restituiti da Plug&Play");
		    } else {
			sesResp.setEsito(true);
			sesResp.setDescEsito("Sessione di pagamento attivata");
		    }
		}
		if (log.isInfoEnabled()) {
		    log.info("attivaSessionePagamento - sessione di pagamento {} attivata per la posizione {}, IUV: {}, id sessione: {}",
			    sesResp.isEsito() ? "" : "non ", payPos.getId(), payPos.getIuv(), sesResp.getPayUrl());
		}
	    } catch (Exception e) {
		StringBuilder sbExc = new StringBuilder("si è verificato un errore durante l'attivazione della sessione di pagamento: ").append(e);
		log.error("attivaSessionePagamento - " + sbExc.toString(), e);
		sesResp.setDescEsito(sbExc.toString());
	    }
	} else {
	    throw new PayException("dati della posizione da attivare mancanti");
	}
	return sesResp;
    }

    /**
     * Lo IUV viene generato secondo lo schema: <codice segregazione (2)> <codice IUV (13)> <modulo 93 (2)> In P&P il
     * codice IUV di 13 cifre deve essere a sua volta essere costruito secondo lo schema: <id gestionale (2)>
     * <identificativo cliente (11)> L'id gestionale viene recuperato dalla colonna APPLICATION_CODE della tabella
     * PAY_PROFILI_ENTI_CREDITORI
     */
    @Override
    public IUVHelper generaIUV(PayPosizioniDebitorie posDeb, String identificativoCausaleVersamento) {

	StringBuilder iuv = new StringBuilder();
	PayProfiliEntiCreditori profEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	if (profEnte != null && posDeb != null) {
	    int segregationCode = 0;
	    if (profEnte.getCodiceSegregazione() != null) {
		segregationCode = profEnte.getCodiceSegregazione();
	    }
	    //2 caratteri numerici che rappresentano il codice segregazione dello IUV configurato nel profilo dell'ente creditore
	    iuv.append(StringUtils.leftPad(Integer.toString(segregationCode), 2, '0'));
	    String appCodeDaCausale = "";
	    ParametroChiaveApplicationCodeIUV parametroChiaveApplicationCodeIUV = new ParametroChiaveApplicationCodeIUV();
	    try {
		appCodeDaCausale = StringUtils.defaultString(
			posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(posDeb, parametroChiaveApplicationCodeIUV))
			.trim();
	    } catch (PayException e) {
		throw new RuntimeException("Errore nel recupero del parametro " + parametroChiaveApplicationCodeIUV.getNomeParametro() +
					   " per la posizione debitoria " + posDeb,
			e);
	    }
	    Integer appCode = null;
	    log.debug("appCodeDaCausale {}", appCodeDaCausale);
	    if (StringUtils.isNotBlank(appCodeDaCausale) && Utilities.isInteger(appCodeDaCausale)) {
		appCode = Integer.parseInt(appCodeDaCausale);
	    } else {
		throw new RuntimeException("IL parametro " + parametroChiaveApplicationCodeIUV.getNomeParametro() +
					   " non è stato impostato correttamente per la posizione debitoria " + posDeb);
	    }
	    //2 caratteri numerici che rappresentano l'id gestionale per P&P configurato nel profilo dell'ente creditore
	    iuv.append(StringUtils.leftPad(Integer.toString(appCode), 2, '0'));
	    //11 caratteri numerici univoci per idcomune codificano l'id della posizione debitoria
	    iuv.append(StringUtils.leftPad(Integer.toString(posDeb.getId().getCodice()), 11, '0'));
	    //2 caratteri numerici di controllo calcolati come resto della divisione per 93 della parte precedente dello IUV preceduta dall'aux digit (3) 
	    BigInteger checkDigit = new BigInteger(IUVHelper.DEFAULT_AUX_DIGIT + iuv.toString());
	    checkDigit = checkDigit.remainder(BigInteger.valueOf(PagoPAServiceImpl.IUV_CHECK_DIGIT_DIVISOR));
	    iuv.append(StringUtils.leftPad(checkDigit.toString(), 2, '0'));
	}
	return new IUVHelper(iuv.toString());
    }

    @Override
    public ElencoStatoPosizioniType rendicontazionePagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	//i dati dei pagamenti vengono recuperati dalle ricevute digitali XML uno alla volta
	ElencoStatoPosizioniType retStati = new ElencoStatoPosizioniType();
	for (PayPosizioniDebitorie pos : cmd.getPosizioni()) {
	    StatoPosizioneType retEsito = this.scaricaRicevutaXmlWs(pos);
	    retStati.getStatoPosizioni().add(retEsito);
	}
	return retStati;
    }

    @Override
    public ElencoDocumentiEsitoType inviaAvvisiPagamento(PosizioniDebitorieCommand cmd) throws PayException {

	ElencoDocumentiEsitoType retStati = new ElencoDocumentiEsitoType();
	for (PayRegistrazioniContabili reg : cmd.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie pos : reg.getPosizioniDebitorie()) {
		EsitoDocumentoPosizioneDebitoriaType retEsito = this.scaricaAvvisoWs(pos);
		retStati.getEsitoPosizione().add(retEsito);
	    }
	}
	return retStati;
    }

    @Override
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	List<PaySessioniPagamento> sex = new ArrayList<>();
	String[] idSessioneVals = reqParams.get("idTransazione");
	String[] esitoVal = reqParams.get("stato");
	if (log.isDebugEnabled()) {
	    log.debug("idSessioneVals {}", Arrays.toString(idSessioneVals));
	    log.debug("esitoVal {}", Arrays.toString(idSessioneVals));
	}
	String idSessione = null;
	String esito = null;
	if (idSessioneVals != null && idSessioneVals.length > 0) {
	    idSessione = idSessioneVals[0];
	}
	if (esitoVal != null && esitoVal.length > 0) {
	    esito = esitoVal[0];
	}
	if (StringUtils.isNotBlank(idSessione)) {
	    sex = this.paySessioniPagamentoService.findBySessionId(idSessione);
	}
	if (!sex.isEmpty()) {
	    for (PaySessioniPagamento paySessioniPagamento : sex) {
		paySessioniPagamento.setEsito("OK".equals(esito));
		this.paySessioniPagamentoService.update(paySessioniPagamento);
	    }
	    return sex.get(0); // ne ritorno una perché la redirect è sempre quella
	}
	return null;
    }

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

    }

    private StatoPosizioneType scaricaRicevutaXmlWs(PayPosizioniDebitorie payPos) {

	StatoPosizioneType esito = new StatoPosizioneType();
	PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, payPos,
		payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	PayProfiliEntiCreditori ente = PayConfigurationHelper.getProfiloEnteCreditore();
	try {
	    IPlugAndPayPayment iPlugAndPayPayment = this.getPaymentServicePort();
	    RichiestaDownloadDatiRicevuta ricevuta = new RichiestaDownloadDatiRicevuta();
	    ricevuta.setCodiceEnteCreditore(ente.getCfCodiceProfiloPSP());
	    ricevuta.setIdApplicazione(ente.getIdAppPSP());
	    ricevuta.setIdentificativoPosizione(payPos.getIdPosizionePsp());
	    //registro l'evento di chiata del servizio di P&P
	    RispostaDownloadDatiRicevuta risposta = iPlugAndPayPayment.downloadDatiRicevuta(ricevuta);
	    it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Esito esitoPnp = risposta.getEsito();
	    if (esitoPnp.equals(it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Esito.OK)) {
		if (StringUtils.isNotBlank(risposta.getRicevuta())) {
		    DatiPagamentoType datiPag = RTHelper.popolaDatiPagamentoDaReceiptORicevutaTelematica(risposta.getRicevuta(), null);
		    DataSource ds = new ByteArrayDataSource(risposta.getRicevuta(), ContentType.APPLICATION_OCTET_STREAM.getMimeType());
		    DataHandler ricevutaXML = new DataHandler(ds);
		    datiPag.setRicevutaXml(ricevutaXML);
		    esito.setEsito(true);
		    esito.setDatiPagamento(datiPag);
		    esito.setStato(StatoPagamentoType.RENDICONTATO_DA_IC);
		    if (log.isInfoEnabled()) {
			log.info(
				"scaricaRicevutaWs - il servizio downloadDatiRicevuta i dati della ricevuta XML sono stati a recuperati per la posizione {}",
				PkId.toStringId(payPos.getId()));
		    }
		} else {
		    esito.setEsito(false);
		    esito.setMessaggio("il servizio downloadDatiRicevuta ha restituito esito OK ma i dati della ricevuta non sono disponibili");
		    if (log.isInfoEnabled()) {
			log.info(
				"scaricaRicevutaWs - il servizio downloadDatiRicevuta ha restituito esito OK ma i dati della ricevuta non sono disponibili");
		    }
		}
	    } else {
		esito.setEsito(false);
		esito.setMessaggio("il servizio downloadDatiRicevuta ha restituito esito NOK");
		/*
		 * impostare erroreTemporaneo a true se la ricevuta può essere non disponibile solo temporaneamente 
		 * ad esempio se una posizione può risultare pagata ma la ricevuta non essere subito disponibile
		 */
		esito.setErroreTemporaneo(false);
		if (log.isInfoEnabled()) {
		    log.info("scaricaRicevutaWs - il servizio downloadDatiRicevuta ha restituito esito NOK");
		}
	    }
	} catch (Exception e) {
	    this.handleException(e, esito);
	    log.error("scaricaRicevutaWs", e);
	}
	return esito;
    }

    private EsitoDocumentoPosizioneDebitoriaType scaricaAvvisoWs(PayPosizioniDebitorie payPos) {

	EsitoDocumentoPosizioneDebitoriaType esito = new EsitoDocumentoPosizioneDebitoriaType();
	esito.setTipoDocumento(TipoDocumentoType.AVVISO);
	PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, payPos,
		payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	PayProfiliEntiCreditori ente = PayConfigurationHelper.getProfiloEnteCreditore();
	try {
	    IGeneratorPdfBytes generatorPdf = this.getGeneratorPdfPort();
	    RichiestaCreaAvvisoPdf ricPdf = new RichiestaCreaAvvisoPdf();
	    ricPdf.setCodiceEnteCreditore(ente.getCfCodiceProfiloPSP());
	    ricPdf.setIdApplicazione(ente.getIdAppPSP());
	    ricPdf.setIdentificativoPosizione(payPos.getIdPosizionePsp());
	    //registro l'evento di chiata del servizio di P&P
	    RispostaCreaAvvisoPdf risposta = generatorPdf.createPdfAvviso(ricPdf);
	    if (null != risposta.getAvvisoPdf() && risposta.getAvvisoPdf().length > 0) {
		DataSource ds = new ByteArrayDataSource(risposta.getAvvisoPdf(), ContentType.APPLICATION_OCTET_STREAM.getMimeType());
		DataHandler avvisoPdf = new DataHandler(ds);
		esito.setEsito(true);
		esito.setNomeDocumento("AVVISO_" + payPos.getCodiceAvviso() + ".pdf");
		esito.setDocumento(avvisoPdf);
		esito.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
		if (log.isInfoEnabled()) {
		    log.info(
			    "scaricaRicevutaWs - il servizio downloadDatiRicevuta i dati della ricevuta XML sono stati a recuperati per la posizione {}",
			    PkId.toStringId(payPos.getId()));
		}
	    } else {
		esito.setEsito(false);
		esito.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
		esito.setMessaggio("il servizio downloadDatiRicevuta ha restituito esito OK ma i dati della ricevuta non sono disponibili");
		if (log.isInfoEnabled()) {
		    log.info(
			    "scaricaRicevutaWs - il servizio downloadDatiRicevuta ha restituito esito OK ma i dati della ricevuta non sono disponibili");
		}
	    }
	} catch (Exception e) {
	    this.handleException(e, esito);
	    log.error("scaricaRicevutaWs", e);
	}
	return esito;
    }

    private EsitoDocumentoPosizioneDebitoriaType scaricaRicevutaPdfWs(PayPosizioniDebitorie payPos) {

	EsitoDocumentoPosizioneDebitoriaType esito = new EsitoDocumentoPosizioneDebitoriaType();
	esito.setTipoDocumento(TipoDocumentoType.RICEVUTA);
	PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, payPos,
		payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	PayProfiliEntiCreditori ente = PayConfigurationHelper.getProfiloEnteCreditore();
	try {
	    IGeneratorPdfBytes generatorPdf = this.getGeneratorPdfPort();
	    RichiestaCreaRTPdf ricPdf = new RichiestaCreaRTPdf();
	    ricPdf.setCodiceEnteCreditore(ente.getCfCodiceProfiloPSP());
	    ricPdf.setIdApplicazione(ente.getIdAppPSP());
	    ricPdf.setIdentificativoPosizione(payPos.getIdPosizionePsp());
	    ricPdf.setCodiceFiscalePartitaIva(payPos.getSoggettoDebitore().getCfPi());
	    //registro l'evento di chiata del servizio di P&P
	    RispostaCreaRTPdf risposta = generatorPdf.createPdfRT(ricPdf);
	    if (null != risposta.getRTPdf() && risposta.getRTPdf().length > 0) {
		DataSource ds = new ByteArrayDataSource(risposta.getRTPdf(), ContentType.APPLICATION_OCTET_STREAM.getMimeType());
		DataHandler rt = new DataHandler(ds);
		esito.setEsito(true);
		esito.setDocumento(rt);
		esito.setNomeDocumento("RICEVUTA_" + payPos.getIuv() + ".pdf");
		esito.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
		if (log.isInfoEnabled()) {
		    log.info("scaricaRicevutaPdfWs - il servizio createPdfRT i dati della ricevuta PDF sono stati recuperati per la posizione {}",
			    PkId.toStringId(payPos.getId()));
		}
	    } else {
		esito.setEsito(false);
		esito.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
		esito.setMessaggio("il servizio createPdfRT ha restituito esito OK ma i dati della ricevuta non sono disponibili");
		if (log.isInfoEnabled()) {
		    log.info("scaricaRicevutaPdfWs - il servizio createPdfRT ha restituito esito OK ma i dati della ricevuta non sono disponibili");
		}
	    }
	} catch (Exception e) {
	    this.handleException(e, esito);
	    log.error("scaricaRicevutaPdfWs", e);
	}
	return esito;
    }
    /* METODI PER IL POPOLAMENTO DEI MESSAGGI SOAP */

    /*
     * metodo che popola l'oggetto Posizione da inviare al WS DigitBusFeed, stacca lo IUV (+ codice avviso e qrcode) da
     * inviare e ne effettua l'update in PAY_POSIZIONI_DEBITORIE. Nel caso di caricamento di più posizioni in una sola
     * chiamata ogni update delle posizioni nel DB avviene in una transazione separata.
     */
    private Posizione caricaPosizioneFeed(PayPosizioniDebitorie payPos, PayProfiliEntiCreditori payEnte) throws PayException {

	Posizione p = new Posizione();
	//generazione IUV, Codice Avviso e QRCODE
	String iuv = getIUV(payPos, null);
	payPos.setIdPosizionePsp(iuv);
	payPos.setIuv(iuv);
	payPos.setCodiceAvviso(new CodiceAvvisoHelper(iuv).getCodiceAvviso());
	payPos.setQrCode(this.pagoPAService.generaQRCode(payPos));
	this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(payPos, null, null);
	//dati della posizione
	p.setIdentificativoPosizione(payPos.getIuv());
	p.setCodiceRiferimentoCreditore(payPos.getIuv());
	p.setCausale(payPos.getDescrizioneCausale());
	long totCents = 0L;//tot posizione in centesimi calcolato
	if (payPos.getDataScadenza() != null) {
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    try {
		XMLGregorianCalendar scad = DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(payPos.getDataScadenza()));
		p.setDataScadenza(scad);
	    } catch (DatatypeConfigurationException e) {
		log.error("caricaPosizioneFeed: errore di formattazione dataScadenza {}", e.getMessage());
	    }
	}
	//Servizio
	String descrizioneServizio = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(payPos,
		new ParametroDescrizioneCausalePSP());
	String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos);
	Servizio ser = new Servizio();
	ser.setCodiceServizio(codiceVersamento);
	ser.setDescrizione(descrizioneServizio);
	p.setServizio(ser);
	p.setTipoRiferimentoCreditore(codiceVersamento);
	//debitore
	PaySoggettiDebitori paySogg = payPos.getSoggettoDebitore();
	if (paySogg != null && paySogg.getId() != null && paySogg.getId().getCodice() != null) {
	    Debitore deb = new Debitore();
	    deb.setCellulare(null);
	    if (StringUtils.isNotBlank(paySogg.getCivico())) {
		deb.setCivico(paySogg.getCivico());
	    }
	    if (StringUtils.isNotBlank(paySogg.getCap())) {
		deb.setCodiceAvviamentoPostale(paySogg.getCap());
	    }
	    deb.setCodiceFiscalePartitaIva(paySogg.getCfPi());
	    if (StringUtils.isNotBlank(paySogg.getEmail())) {
		deb.setEmail(paySogg.getEmail());
	    }
	    if (StringUtils.isNotBlank(paySogg.getVia())) {
		deb.setIndirizzo(paySogg.getVia());
	    }
	    if (StringUtils.isNotBlank(paySogg.getLocalita())) {
		deb.setLocalita(paySogg.getLocalita());
	    }
	    deb.setNazione(getNazioneFeed());
	    StringBuilder sbName = new StringBuilder();
	    if (StringUtils.isNotBlank(paySogg.getNome())) {
		sbName.append(paySogg.getNome());
	    }
	    if (sbName.length() > 0) {
		sbName.append(" ");
	    }
	    if (StringUtils.isNotBlank(paySogg.getCognome())) {
		sbName.append(paySogg.getCognome());
	    }
	    deb.setNominativo(sbName.toString());
	    if (StringUtils.isNotBlank(paySogg.getProvincia())) {
		deb.setProvincia(paySogg.getProvincia());
	    }
	    //la natura giuridica del soggetto pagatore viene determinata in base alla lunghezza dell'identificativo fiscale
	    TipoPagatore tp = paySogg.getCfPi().length() == 16 ? TipoPagatore.PERSONA_FISICA : TipoPagatore.PERSONA_GIURIDICA;
	    deb.setTipoPagatore(tp);
	    p.setDebitore(deb);
	    //creditore
	    Creditore cr = feedof.createCreditore();
	    cr.setCodiceEnte(payEnte.getCfCodiceProfiloPSP());
	    cr.setCodiceFiscalePartitaIva(payEnte.getAmministrazione().getPartitaiva());
	    cr.setIntestazione(payEnte.getAmministrazione().getAmministrazione());
	    p.setCreditore(cr);
	    //accertamenti (componenti importo)
	    Set<PayDettaglioImporti> payDettList = payPos.getDettagliImporto();
	    ArrayOfAccertamento accerts = new ArrayOfAccertamento();
	    //      marco.disomma@e-fil.eu
	    //	le posizioni devono essere caricate sull' accertamento di default?
	    //	Se la risposta è positiva può eliminare il tag degli accertamenti.
	    //	L'accertamento di default abilitato è Accertamento Generico.
	    for (PayDettaglioImporti payDett : payDettList) {
		BigDecimal importoDec = payDett.getImporto();
		importoDec = importoDec.multiply(new BigDecimal(100));
		long importoAcc = importoDec.intValue();
		totCents += importoAcc;
		if (StringUtils.isNotBlank(payDett.getNumeroAccertamento())) {
		    Accertamento acc = new Accertamento();
		    acc.setCodice(payDett.getNumeroAccertamento());
		    acc.setImportoInCentesimi(importoAcc);
		    accerts.getAccertamento().add(acc);
		}
	    }
	    // eventuali parametri posizione se servono
	    if (!accerts.getAccertamento().isEmpty()) { //Se la risposta è positiva può eliminare il tag degli accertamenti.
		p.setAccertamenti(accerts);
	    }
	}
	p.setImportoInCentesimi(totCents);
	return p;
    }

    /*
     * metodo che popola l'oggetto Posizione da inviare al WS DigitBusPayment, stacca lo IUV (+ codice avviso e qrcode)
     * da inviare e ne effettua l'update in PAY_POSIZIONI_DEBITORIE.
     */
    private it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Posizione caricaPosizionePayment(PayPosizioniDebitorie payPos,
	    boolean otf) throws PayException {

	it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Posizione p = new it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Posizione();
	//Servizio
	PayRegistrazioniContabili payReg = payPos.getRegistrazioneContabile();
	PayProfiliEntiCreditori payEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	//Servizio
	String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos);
	Servizio ser = new Servizio();
	ser.setCodiceServizio(codiceVersamento);
	p.setServizio(ser);
	p.setSpontaneo(otf);
	if (otf) {
	    //creditore
	    it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Creditore cr = new it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Creditore();
	    cr.setCodiceEnte(payEnte.getCfCodiceProfiloPSP());
	    p.setCreditore(cr);
	    //generazione IUV, Codice Avviso e QRCODE
	    String iuv = getIUV(payPos, null);
	    if (StringUtils.isEmpty(payPos.getIuv())) {
		payPos.setIdPosizionePsp(iuv);
		payPos.setIuv(iuv);
		payPos.setCodiceAvviso(new CodiceAvvisoHelper(iuv).getCodiceAvviso());
		payPos.setQrCode(this.pagoPAService.generaQRCode(payPos));
		//scrivo nel DB l'id della posizione trasmesso al PSP
		this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(payPos, null, null);
	    }
	    //dati della posizione
	    p.setIdentificativoPosizione(payPos.getIuv());
	    p.setCodiceRiferimentoCreditore(payPos.getIuv());
	    p.setCausale(payReg.getDescrizione());
	    if (payPos.getDataScadenza() != null) {
		Calendar c = Calendar.getInstance();
		c.setTime(payPos.getDataScadenza());
		c.add(Calendar.DATE, 1);
		XMLGregorianCalendar scad = Utilities.getXMLGregorianCalendar(c.getTime());
		p.setDataScadenza(scad);
	    }
	    //debitore
	    PaySoggettiDebitori paySogg = payPos.getSoggettoDebitore();
	    if (paySogg != null && paySogg.getId() != null && paySogg.getId().getCodice() != null) {
		it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Debitore deb = new it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Debitore();
		deb.setCellulare(null);
		if (StringUtils.isNotBlank(paySogg.getCivico())) {
		    deb.setCivico(paySogg.getCivico());
		}
		if (StringUtils.isNotBlank(paySogg.getCap())) {
		    deb.setCodiceAvviamentoPostale(paySogg.getCap());
		}
		deb.setCodiceFiscalePartitaIva(paySogg.getCfPi());
		if (StringUtils.isNotBlank(paySogg.getEmail())) {
		    deb.setEmail(paySogg.getEmail());
		}
		if (StringUtils.isNotBlank(paySogg.getVia())) {
		    deb.setIndirizzo(paySogg.getVia());
		}
		if (StringUtils.isNotBlank(paySogg.getLocalita())) {
		    deb.setLocalita(paySogg.getLocalita());
		}
		deb.setNazione(getNazionePayment());
		StringBuilder sbName = new StringBuilder();
		if (StringUtils.isNotBlank(paySogg.getNome())) {
		    sbName.append(paySogg.getNome());
		}
		if (sbName.length() > 0) {
		    sbName.append(" ");
		}
		if (StringUtils.isNotBlank(paySogg.getCognome())) {
		    sbName.append(paySogg.getCognome());
		}
		deb.setNominativo(sbName.toString());
		if (StringUtils.isNotBlank(paySogg.getProvincia())) {
		    deb.setProvincia(paySogg.getProvincia());
		}
		//la natura giuridica del soggetto pagatore viene determinata in base alla lunghezza dell'identificativo fiscale
		TipoPagatore tp = paySogg.getCfPi().length() == 16 ? TipoPagatore.PERSONA_FISICA : TipoPagatore.PERSONA_GIURIDICA;
		deb.setTipoPagatore(tp);
		p.setDebitore(deb);
	    }
	    ArrayOfParametroPosizione posParams = new ArrayOfParametroPosizione();
	    p.setParametriPosizione(posParams);
	} else {
	    //riferimenti posizione
	    p.setIdentificativoPosizione(payPos.getIdPosizionePsp());
	    p.setCausale("");
	    //creditore
	    it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Creditore cr = new it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Creditore();
	    cr.setCodiceEnte(payEnte.getCfCodiceProfiloPSP());
	    cr.setCodiceFiscalePartitaIva(payEnte.getAmministrazione().getPartitaiva());
	    cr.setIntestazione(payEnte.getAmministrazione().getAmministrazione());
	    p.setCreditore(cr);
	    //debitore
	    PaySoggettiDebitori paySogg = payPos.getSoggettoDebitore();
	    if (paySogg != null && paySogg.getId() != null && paySogg.getId().getCodice() != null) {
		it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Debitore deb = new it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Debitore();
		//la natura giuridica del soggetto pagatore viene determinata in base alla lunghezza dell'identificativo fiscale
		TipoPagatore tp = paySogg.getCfPi().length() == 16 ? TipoPagatore.PERSONA_FISICA : TipoPagatore.PERSONA_GIURIDICA;
		deb.setTipoPagatore(tp);
		deb.setCodiceFiscalePartitaIva(paySogg.getCfPi());
		p.setDebitore(deb);
	    }
	    ArrayOfParametroPosizione posParams = new ArrayOfParametroPosizione();
	    p.setParametriPosizione(posParams);
	}
	it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.ArrayOfAccertamento accerts = new it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.ArrayOfAccertamento();
	// trasmetto anche gli accertamenti (componenti importo) non vengono trasmessi a P&P, servono solo per calcolare l'importo totale
	int totCents = 0;
	//      marco.disomma@e-fil.eu	
	//	le posizioni devono essere caricate sull' accertamento di default?
	//	Se la risposta è positiva può eliminare il tag degli accertamenti.
	//	L'accertamento di default abilitato è Accertamento Generico.
	Set<PayDettaglioImporti> payDettList = payPos.getDettagliImporto();
	for (PayDettaglioImporti payDett : payDettList) {
	    BigDecimal importoDec = payDett.getImporto();
	    importoDec = importoDec.multiply(new BigDecimal(100));
	    int importoAcc = importoDec.intValue();
	    totCents += importoAcc;
	    if (StringUtils.isNotBlank(payDett.getNumeroAccertamento())) {
		it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Accertamento acc = new it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Accertamento();
		acc.setCodice(payDett.getNumeroAccertamento());
		acc.setPeriodoDiRiferimento(String.valueOf(payDett.getAnnoAccertamento()));
		acc.setImportoInCentesimi(importoAcc);
		accerts.getAccertamento().add(acc);
	    }
	}
	if (!accerts.getAccertamento().isEmpty()) { //Se la risposta è positiva può eliminare il tag degli accertamenti.
	    p.setAccertamenti(accerts);
	}
	p.setImportoInCentesimi(totCents);
	return p;
    }

    private Nazione getNazioneFeed() {

	/*
	 * poichè non siamo in grado di risolvere il codice della nazione secondo lo standard ISO 3166 per ora
	 * impostiamo ITA-Italia fisso si potrebbe utilizzare la colonna COMUNI.CODICEISTATREGIONE per decodificare il
	 * codice OSO dalla descrizione in anagrafica che attualmente rimane null per i record di COMUNI che
	 * rappresentano stati esteri
	 */
	Nazione it = this.feedof.createNazione();
	it.setCodiceIsoNazione(PlugAndPayConnector.CODICE_ISO_3166_ITA);
	it.setNomeNazione("Italia");
	return it;
    }

    private it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Nazione getNazionePayment() {

	/*
	 * poichè non siamo in grado di risolvere il codice della nazione secondo lo standard ISO 3166 per ora
	 * impostiamo ITA-Italia fisso si potrebbe utilizzare la colonna COMUNI.CODICEISTATREGIONE per decodificare il
	 * codice OSO dalla descrizione in anagrafica che attualmente rimane null per i record di COMUNI che
	 * rappresentano stati esteri
	 */
	it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Nazione nazioneIt = new it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment.Nazione();
	nazioneIt.setCodiceIsoNazione(PlugAndPayConnector.CODICE_ISO_3166_ITA);
	nazioneIt.setNomeNazione("Italia");
	return nazioneIt;
    }

    private boolean checkErroreIuvDuplicato(EsitoDiCaricamento esito) {

	boolean iuvDuplicato = false;
	if (esito != null && esito.getErroriDiValidazione() != null) {
	    ArrayOfErroreDiValidazionePosizione errorsArray = esito.getErroriDiValidazione();
	    List<ErroreDiValidazionePosizione> errors = errorsArray.getErroreDiValidazionePosizione();
	    if (errors != null) {
		Iterator<ErroreDiValidazionePosizione> errorsIter = errors.iterator();
		while (errorsIter.hasNext()) {
		    ErroreDiValidazionePosizione error = errorsIter.next();
		    if (error.getCodice().equals(CODICE_ERRORE_IUV_DUPLICATO)) {
			return true;
		    }
		}
	    }
	}
	return iuvDuplicato;
    }

    private boolean checkErrorePosizioneGiaAnnullata(Throwable sfe) {

	boolean annullato = false;
	if (sfe instanceof SOAPFaultException && sfe.getMessage().contains(MSG_ERRORE_POSIZIONE_GIA_ANNULLATA)) {
	    annullato = true;
	}
	return annullato;
    }

    /* GESTIONE ECCEZIONI COME ESITI NEGATIVI */
    @Override
    protected void handleException(Throwable exc, EsitoOperazionePosizioneDebitoriaType esitoOp) {

	if (exc != null && esitoOp != null) {
	    esitoOp.setEsito(false);
	    esitoOp.setStato(StatoPagamentoType.CON_ERRORE);
	    StringBuilder msg = new StringBuilder("Errore ");
	    if (exc instanceof IPlugAndPayFeedCaricaPosizioneSoapFaultOperazioneProibitaFaultFaultMessage) {
		IPlugAndPayFeedCaricaPosizioneSoapFaultOperazioneProibitaFaultFaultMessage pnpExc = (IPlugAndPayFeedCaricaPosizioneSoapFaultOperazioneProibitaFaultFaultMessage) exc;
		if (pnpExc.getFaultInfo() != null) {
		    if (pnpExc.getFaultInfo().getDescrizioneErrore() != null) {
			msg.append(pnpExc.getFaultInfo().getDescrizioneErrore());
		    }
		    esitoOp.setCodiceErrore(pnpExc.getFaultInfo().getCodiceErrore());
		}
	    } else if (exc instanceof IPlugAndPayFeedCaricaPosizioneSoapFaultErroreEasyPAFaultFaultMessage) {
		IPlugAndPayFeedCaricaPosizioneSoapFaultErroreEasyPAFaultFaultMessage pnpExc = (IPlugAndPayFeedCaricaPosizioneSoapFaultErroreEasyPAFaultFaultMessage) exc;
		if (pnpExc.getFaultInfo() != null) {
		    if (pnpExc.getFaultInfo().getDescrizioneErrore() != null) {
			msg.append(pnpExc.getFaultInfo().getDescrizioneErrore());
		    }
		    esitoOp.setCodiceErrore(pnpExc.getFaultInfo().getCodiceErrore());
		}
	    } else if (exc instanceof IPlugAndPayFeedCaricaPosizioneSoapFaultErroreInternoFaultFaultMessage) {
		IPlugAndPayFeedCaricaPosizioneSoapFaultErroreInternoFaultFaultMessage pnpExc = (IPlugAndPayFeedCaricaPosizioneSoapFaultErroreInternoFaultFaultMessage) exc;
		if (pnpExc.getFaultInfo() != null) {
		    if (pnpExc.getFaultInfo().getDescrizioneErrore() != null) {
			msg.append(pnpExc.getFaultInfo().getDescrizioneErrore());
		    }
		    esitoOp.setCodiceErrore(pnpExc.getFaultInfo().getCodiceErrore());
		}
	    } else {
		super.handleException(exc, esitoOp);
		return;
	    }
	    esitoOp.setMessaggio(msg.toString());
	}
    }

    /* METODI PER LA CHIAMATA DEI WS SOAP */
    private IPlugAndPayFeed getFeedServicePort() throws PayException {

	PayConnectorWsEndpoint wsCfg = this.getWsCaricamentoConfig();
	if (wsCfg == null) {
	    throw new PayConfigurationException(
		    "Il WS per il caricamento/annullamento delle posizioni non è configurato per il connettorre Plug&Play.");
	}
	IPlugAndPayFeed ws = this.feedService == null ? (IPlugAndPayFeed) this.getWSPort(wsCfg, IPlugAndPayFeed.class) : this.feedService;
	this.prepareWsPort(ws, wsCfg);
	this.feedService = ws;
	return ws;
    }

    private IPlugAndPayDeliver getDeliverServicePort() throws PayException {

	PayConnectorWsEndpoint wsCfg = this.getWsVerificaConfig();
	if (wsCfg == null) {
	    throw new PayConfigurationException(
		    "Il WS per la verifica/rendicontazione dello stato delle posizioni non è configurato per il connettorre Plug&Play.");
	}
	IPlugAndPayDeliver ws = this.deliverService == null ? (IPlugAndPayDeliver) this.getWSPort(wsCfg, IPlugAndPayDeliver.class)
		: this.deliverService;
	this.prepareWsPort(ws, wsCfg);
	this.deliverService = ws;
	return ws;
    }

    private IPlugAndPayPayment getPaymentServicePort() throws PayException {

	PayConnectorWsEndpoint wsCfg = this.getWsAttivaSessioneConfig();
	if (wsCfg == null) {
	    throw new PayConfigurationException("Il WS per l'attivazione dei pagamenti non è configurato per il connettorre Plug&Play.");
	}
	IPlugAndPayPayment ws = this.paymentService == null ? (IPlugAndPayPayment) this.getWSPort(wsCfg, IPlugAndPayPayment.class)
		: this.paymentService;
	this.prepareWsPort(ws, wsCfg);
	this.paymentService = ws;
	return ws;
    }

    private IGeneratorPdfBytes getGeneratorPdfPort() throws PayException {

	PayConnectorWsEndpoint wsCfg = this.getWsAvvisoConfig();
	if (wsCfg == null) {
	    wsCfg = this.getWsRicevutaConfig();
	}
	if (wsCfg == null) {
	    throw new PayConfigurationException(
		    "Il WS per il caricamento/annullamento delle posizioni non è configurato per il connettorre Plug&Play.");
	}
	IGeneratorPdfBytes ws = this.pdfService == null ? (IGeneratorPdfBytes) this.getWSPort(wsCfg, IGeneratorPdfBytes.class) : this.pdfService;
	this.prepareWsPort(ws, wsCfg);
	this.pdfService = ws;
	return ws;
    }

    private void prepareWsPort(Object wsPort, PayConnectorWsEndpoint wsCfg) {

	Client client = ClientProxy.getClient(wsPort);
	HTTPConduit conduit = (HTTPConduit) client.getConduit();
	if (wsCfg.getTimeout() != null) {
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(wsCfg.getTimeout());
	    httpClientPolicy.setReceiveTimeout(wsCfg.getTimeout());
	    conduit.setClient(httpClientPolicy);
	}
	TLSClientParameters params = conduit.getTlsClientParameters();
	if (params == null) {
	    params = new TLSClientParameters();
	    conduit.setTlsClientParameters(params);
	}
	params.setTrustManagers(new TrustManager[] { new TrustAllX509TrustManager() });
	params.setDisableCNCheck(true);
    }

    @Override
    public boolean supportaAttivaSessionePagamento() {

	return true;
    }

    @Override
    public boolean supportaModificaDataScadenza() {

	return false;
    }

    @Override
    public boolean supportaMolteCausaliRaggruppate() {

	return true;
    }

    @Override
    public List<IParameter> getListaParametriRichiesti() {

	return ParametriConnettoreHelper.getMappaParametriConnettore(this.getClass().getName());
    }
}
