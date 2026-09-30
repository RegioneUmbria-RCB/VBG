package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.http.entity.ContentType;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.InputStreamDataSource;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.CommonPiemontePayUtils;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.client.PiemontePayClient;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.service.PiemontepayService.EsitiPPAY;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.Enti2EPaywsoSecureProxyPortType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.AggiornaPosizioniDebitorieRequest;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.ElencoPosizioniDaAggiornareType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.ElencoPosizioniDaAggiornareType.PosizioniDaAggiornare;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.PosizioneDaAggiornareType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.TestataAggiornaPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.ResponseType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.ResultType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.TipoAggiornamentoType;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.exception.PayInvalidRequestException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.ParametriConnettoreHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RTHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.FormParametersType;
import it.gruppoinit.pal.gp.pay.ws.schema.HttpMethodType;
import it.gruppoinit.pal.gp.pay.ws.schema.ImportoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;
import it.gruppoinit.sigeprosecurity.ws.SecurityPwdCallBackHandler;

@Component
public class PiemontePayRestConnector extends AbstractPayConnector implements IPayConnector {

    private static final String ESITO_OPERAZIONI_REST_000 = "000";
    private static final String ESITO_OPERAZIONI_REST_300 = "300";
    private static final Logger log = LoggerFactory.getLogger(PiemontePayRestConnector.class);
    private static final String FORMATO_RICEVUTA = "XML";
    private static final String FORMATO_RICEVUTA_PDF = "PDF";
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;
    @Autowired
    private PaySessioniPagamentoService paySessioniPagamentoService;
    @Autowired
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    @Autowired
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	ElencoPosizioniDebitorieEsitoType results = new ElencoPosizioniDebitorieEsitoType();
	PiemontePayClient client = new PiemontePayClient(this.getWsCaricamentoConfig());
	for (PayRegistrazioniContabili registrazione : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie posizione : registrazione.getPosizioniDebitorie()) {
		//
		posizione.setIdPosizionePsp(this.generaIdPosizioneDebitoria(posizione));
		//
		String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(posizione);
		DebtPositionReference response = client.creaDebtPosition(creaDebtPosition(registrazione.getDescrizione(), posizione),
			PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfiloPSP(), codiceVersamento);
		EsitiPPAY esito = EsitiPPAY.fromValue(response.getCodiceEsito());
		EsitoOperazionePosizioneDebitoriaType esitoChiamata = new EsitoOperazionePosizioneDebitoriaType();
		if (esito != EsitiPPAY.CODE_000 && esito != EsitiPPAY.CODE_050) {
		    esitoChiamata.setEsito(false);
		    esitoChiamata.setStato(StatoPagamentoType.CON_ERRORE);
		    esitoChiamata.setCodiceErrore(esito.errorCode());
		    esitoChiamata.setMessaggio(response.getDescrizioneEsito());
		} else {
		    esitoChiamata.setEsito(true);
		    esitoChiamata.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		    esitoChiamata.setMessaggio(StatiPagamento.ATTIVATO_IN_PSP.description());
		    esitoChiamata.setIdPosizione(BigInteger.valueOf(posizione.getId().getCodice()));
		    esitoChiamata.setIdRegistrazioneContabile(BigInteger.valueOf(registrazione.getId().getCodice()));
		    esitoChiamata.setCodiceAvviso(response.getCodiceAvviso());
		    esitoChiamata.setIUV(response.getIuv());
		}
		results.getEsitoPosizione().add(esitoChiamata);
	    }
	}
	return results;
    }

    private DebtPositionData creaDebtPosition(String causale, PayPosizioniDebitorie posizione) {

	DebtPositionData request = new DebtPositionData();
	request.setCausale(causale);
	request.setCodiceFiscalePartitaIVAPagatore(posizione.getSoggettoDebitore().getCfPi());
	if (StringUtils.isNotBlank(posizione.getSoggettoDebitore().getCognome())) {
	    request.setCognome(posizione.getSoggettoDebitore().getCognome());
	    request.setNome(posizione.getSoggettoDebitore().getNome());
	} else {
	    request.setRagioneSociale(posizione.getSoggettoDebitore().getNome());
	}
	request.setEmail(posizione.getSoggettoDebitore().getEmail());
	request.setIdentificativoPagamento(this.generaIdPosizioneDebitoria(posizione));
	if (posizione.getDataScadenza() != null) {
	    XMLGregorianCalendar dataScadenza = Utilities.getXMLGregorianCalendar(posizione.getDataScadenza());
	    request.setDataScadenza(dataScadenza);
	    // request.setDataFineValidita(dataScadenza);
	}
	if (posizione.getDataFineValidita() != null) {
	    XMLGregorianCalendar dataFineValidita = Utilities.getXMLGregorianCalendar(posizione.getDataFineValidita());
	    request.setDataFineValidita(dataFineValidita);
	}
	Integer index = 1;
	for (PayDettaglioImporti dettaglio : posizione.getDettagliImporto()) {
	    request.getComponentiPagamento().add(new ComponentePagamentoType(index, dettaglio));
	    index++;
	}
	request.getImporto();
	return request;
    }

    private long getTempoVerificaStatoSincrono() throws IllegalArgumentException {

	long tempoAttesa = 0;
	String v = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.DELAY_VERIFICA_STATO);
	if (StringUtils.isNotBlank(v)) {
	    try {
		tempoAttesa = Long.parseLong(v.trim());
	    } catch (NumberFormatException e) {
		throw new IllegalArgumentException(
			"Errore nella configurazione del parametro " + ConfigParamNames.DELAY_VERIFICA_STATO + " impostato con valore " + v);
	    }
	}
	return tempoAttesa;
    }

    @Override
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	ElencoStatoPosizioniType results = new ElencoStatoPosizioniType();
	PiemontePayClient client = new PiemontePayClient(this.getWsVerificaConfig());
	PiemontePayClient clientRT = new PiemontePayClient(this.getWsCaricamentoConfig());
	for (PayPosizioniDebitorie posizione : cmd.getPosizioni()) {
	    StatoPosizioneType statoPosizione = new StatoPosizioneType();
	    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(statoPosizione, posizione,
		    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(posizione.getId().getCodice()));
	    statoPosizione.setEsito(true);
	    try {
		// lo stato della posizione debitoria lo verifico nel caso siano passati almeno 20 minuti dalla creazione per problema lentezza in creazione
		// con i servizi sincroni infatti il getRt dovrebbe essere una chiamata pesante e non ha senso farla subito dopo la creazione della posizione debitoria
		// la richiesta era di farla on demand ma con i metodi del nodo pagamenti è possibile farla solo all'evento verifica stato
		if (new Date().getTime() - posizione.getDataRegistrazione().getTime() >= getTempoVerificaStatoSincrono()) {
		    GetDebtPosizionStatusResponse debPositionStatus = client.getDebPositionStatus(this.createDebtPositionRequest(posizione));
		    log.debug("debPositionStatus {}", debPositionStatus);
		    if (debPositionStatus.getResult() != null
			    && StringUtils.defaultString(debPositionStatus.getResult().getCode()).equalsIgnoreCase(ESITO_OPERAZIONI_REST_000)) {
			GetDebPositionStatusEnum statoPPAy = GetDebPositionStatusEnum.fromDescrizione(debPositionStatus.getCode());
			StatoPagamentoType stato = statoPPAy.toStatoPagamentoType();
			// verifico se non più attiva
			if (statoPPAy.equals(GetDebPositionStatusEnum.NON_PIU_ATTIVA)) {
			    log.warn("La posizione debitoria {} non risulta attiva in PPAY verifico le condizioni per riportare lo stato",
				    posizione.getId());
			    if (verificaDataScadenzaMinoreDiOggi(posizione)) {
				log.warn("La posizione debitoria {} non risulta attiva in PPAY non eseguo la verifica e la data di scadenza è {}",
					posizione.getId(), posizione.getDataScadenza());
				continue;
			    }
			}
			if (statoPPAy.equals(GetDebPositionStatusEnum.PAGATO)) {
			    if (isSupportaGetRt()) {
				log.debug("isSupportaGetRt()");
				//
				GetRTResponse response = clientRT
					.getRT(this.createGetRTRequest(posizione, PiemontePayRestConnector.FORMATO_RICEVUTA));
				// recupera lo stato Nativo
				if (StringUtils.isNotBlank(response.getDescrizioneStatoPagamento())) {
				    statoPosizione.setStatoPagamentoNativo(response.getDescrizioneStatoPagamento());
				}
				log.debug("response.getDescrizioneStatoPagamento(): {}", response.getDescrizioneStatoPagamento());
				if (StringUtils.isNotBlank(response.getRtXml()) && stato.equals(StatoPagamentoType.NOTIFICATO_DA_PSP)) {
				    statoPosizione.setStato(StatoPagamentoType.RENDICONTATO_DA_IC);
				    DatiPagamentoType datiPag = this.getDatiPagamento(response);
				    DataHandler ricevutaXML = new DataHandler(new ByteArrayDataSource(response.ricevutaXMLToByte(),
					    ContentType.APPLICATION_OCTET_STREAM.getMimeType()));
				    datiPag.setRicevutaXml(ricevutaXML);
				    statoPosizione.setDatiPagamento(datiPag);
				}
			    } else {
				GetDebtPositionPaymentDataResponse debtPositionPaymentData = client
					.getDebtPositionPaymentData(this.createDebtPositionDataRequest(posizione));
				if (debtPositionPaymentData.getResult() != null && StringUtils
					.defaultString(debtPositionPaymentData.getResult().getCode()).equalsIgnoreCase(ESITO_OPERAZIONI_REST_000)) {
				    statoPosizione.setStato(StatoPagamentoType.RENDICONTATO_DA_IC);
				    //	     Nel caso di una posizione debitoria:
				    //		  pagata, pagamento effettuato, il campo di output dataEsitoPagamento sarà VALORIZZATO
				    //		  non pagata, pagamento non effettuato, il campo di output dataEsitoPagamento sarà NON VALORIZZATO
				    // P.S. LO CHIAMO SOLO SE IL GETSTATUS MI TORNA PAGATO
				    DatiPagamentoType datiPag = this.getDatiPagamentoFromPaymentData(debtPositionPaymentData);
				    statoPosizione.setDatiPagamento(datiPag);
				}
			    }
			} else if (statoPPAy.equals(GetDebPositionStatusEnum.ANNULLATO_DALL_ENTE)) {
			    log.debug("La posizione debitoria {} risulta annullata dall'ente imposto lo stato annullato", posizione.getId());
			    statoPosizione.setStato(StatoPagamentoType.ANNULLATO);
			}
		    } else {
			log.error("debPositionStatus {}", debPositionStatus);
		    }
		}
	    } catch (Exception e) {
		log.error("verificaStatoPagamenti: " + e.getMessage(), e);
	    }
	    results.getStatoPosizioni().add(statoPosizione);
	}
	return results;
    }

    private DatiPagamentoType getDatiPagamentoFromPaymentData(GetDebtPositionPaymentDataResponse risposta) {

	DatiPagamentoType pagamenti = new DatiPagamentoType();
	pagamenti.setIuv(risposta.getIdentificativoUnicoVersamento());
	//DatiPagamentoType.idPSP serve per memorizzare il codice identificativo del PSP effettivamente utilizzato per gestire la transazione,
	//e non per l'id della posizione nel sistema che funge da intermediario del pagamento.
	//viene settato già dall'RTHelper	
	pagamenti.setDataOraPagamento(Utilities.getXMLGregorianCalendar(risposta.getDataEsitoPagamento()));
	pagamenti.setImportoPagato(risposta.getImportoPagato() == null ? risposta.getImportoPagatoTotale() : risposta.getImportoPagato());
	pagamenti.setImportoTransato(risposta.getImportoPagatoTotale());
	pagamenti.setRiferimentiPagamento(risposta.getNumeroTransazione());
	pagamenti.setDataOraAutorizzazione(Utilities.getXMLGregorianCalendar(risposta.getDataEOraOperazione()));
	pagamenti.setIur(risposta.getIdentificativoUnicoRiscossione());
	pagamenti.setDescrizioneCausale(risposta.getInfoAggiuntive());
	//
	SoggettoDebitoreType soggetto = new SoggettoDebitoreType();
	soggetto.setCfpi(risposta.getCodiceFiscalePIva());
	soggetto.setNome(risposta.getNomeECognomeRagioneSociale());
	//
	pagamenti.setSoggettoPagatore(soggetto);
	pagamenti.setRagioneSocialePSP(risposta.getPrestatoreDiServiziDiPagamento());
	return pagamenti;
    }

    private GetDebPositionPaymentDataRequest createDebtPositionDataRequest(PayPosizioniDebitorie posizione) throws PayConfigurationException {

	String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(posizione);
	return new GetDebPositionPaymentDataRequest(PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfiloPSP(), codiceVersamento,
		posizione.getIuv());
    }

    private GetDebtPositionStatusRequest createDebtPositionRequest(PayPosizioniDebitorie posizione) throws PayConfigurationException {

	String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(posizione);
	return new GetDebtPositionStatusRequest(PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfiloPSP(), codiceVersamento,
		posizione.getIuv());
    }

    @Override
    public ElencoDocumentiEsitoType scaricaRicevuteTelematiche(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	ElencoDocumentiEsitoType elencoDocumentiEsitoType = new ElencoDocumentiEsitoType();
	PiemontePayClient client = new PiemontePayClient(this.getWsCaricamentoConfig());
	for (PayPosizioniDebitorie pos : cmd.getPosizioni()) {
	    EsitoDocumentoPosizioneDebitoriaType esitoRt = this.scaricaRicevutaPdfWs(client, pos);
	    elencoDocumentiEsitoType.getEsitoPosizione().add(esitoRt);
	}
	return elencoDocumentiEsitoType;
    }

    private EsitoDocumentoPosizioneDebitoriaType scaricaRicevutaPdfWs(PiemontePayClient client, PayPosizioniDebitorie posizione) {

	EsitoDocumentoPosizioneDebitoriaType esito = new EsitoDocumentoPosizioneDebitoriaType();
	esito.setTipoDocumento(TipoDocumentoType.RICEVUTA);
	esito.setIdPosizione(BigInteger.valueOf(posizione.getId().getCodice()));
	PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, posizione,
		payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(posizione.getId().getCodice()));
	try {
	    if (posizione.getRicevuta() != null) {
		esito.setEsito(true);
		esito.setDocumento(posizione.getRicevuta().getDataHandler());
		esito.setNomeDocumento(posizione.getRicevuta().getNomeDocumento());
		esito.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
		return esito;
	    }
	    if (isSupportaGetRt()) {
		GetRTRequest request = this.createGetRTRequest(posizione, PiemontePayRestConnector.FORMATO_RICEVUTA_PDF);
		GetRTResponse response = client.getRT(request);
		if (StringUtils.isBlank(response.getRicevutaPDF())) {
		    throw new PayException("Ricevuta non disponibile");
		}
		DataHandler ricevuta = new DataHandler(
			new ByteArrayDataSource(response.ricevutaPDFToByte(), ContentType.APPLICATION_OCTET_STREAM.getMimeType()));
		esito.setEsito(true);
		esito.setDocumento(ricevuta);
		esito.setNomeDocumento("RICEVUTA_" + posizione.getIuv() + ".pdf");
		esito.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
	    } else {
		// 
		String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(posizione);
		PaymentReceipt paymentReceipt = client.getPaymentReceipt(PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfiloPSP(),
			codiceVersamento, posizione.getIuv());
		esito.setEsito(true);
		byte[] b = paymentReceipt.ricevutaPDFToByte();
		if (b.length == 0) {
		    throw new PayException("Ricevuta vuota per la posizione debitoria " + posizione.getId());
		}
		DataHandler ricevuta = new DataHandler(new ByteArrayDataSource(b, ContentType.APPLICATION_OCTET_STREAM.getMimeType()));
		esito.setDocumento(ricevuta);
		esito.setNomeDocumento("RICEVUTA_" + posizione.getIuv() + ".pdf");
		esito.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
	    }
	} catch (Exception e) {
	    esito.setEsito(false);
	    esito.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
	    esito.setMessaggio(e.getMessage());
	    log.error(MessageFormat.format("scaricaRicevutaPdfWs: {0}", e.getMessage()));
	}
	return esito;
    }

    private GetRTRequest createGetRTRequest(PayPosizioniDebitorie posizione, String formatoRicevuta) {

	GetRTRequest request = new GetRTRequest();
	request.setIuv(posizione.getIuv());
	request.setCodiceFiscaleEnte(PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfiloPSP());
	request.setCodiceFiscale(posizione.getSoggettoDebitore().getCfPi());
	request.setIdentificativoPagamento(posizione.getIdPosizionePsp());
	request.setFormatoRT(formatoRicevuta);
	return request;
    }

    private DatiPagamentoType getDatiPagamento(GetRTResponse risposta) {

	DatiPagamentoType pagamenti = new DatiPagamentoType();
	pagamenti.setIuv(StringUtils.isNotBlank(risposta.getIuvEffettivo()) ? risposta.getIuvEffettivo() : risposta.getIuvOriginario());
	//DatiPagamentoType.idPSP serve per memorizzare il codice identificativo del PSP effettivamente utilizzato per gestire la transazione,
	//e non per l'id della posizione nel sistema che funge da intermediario del pagamento.
	//viene settato già dall'RTHelper
	RTHelper.popolaDatiPagamentoDaRicevutaTelematica(risposta.getRicevutaXML(), pagamenti);
	return pagamenti;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException {

	ElencoPosizioniDebitorieEsitoType results = new ElencoPosizioniDebitorieEsitoType();
	for (PayRegistrazioniContabili registrazione : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie posizione : registrazione.getPosizioniDebitorie()) {
		if (isUsaServizioRestAnnullamento()) {
		    results.getEsitoPosizione().add(annullamentoServizioRest(posizione, pagatoOffline));
		} else {
		    results.getEsitoPosizione().add(annullaPosizioneAsincrona(posizione, pagatoOffline));
		}
	    }
	}
	return results;
    }

    @Override
    public ElencoDocumentiEsitoType inviaAvvisiPagamento(PosizioniDebitorieCommand cmd) throws PayException {

	log.debug("inviaAvvisiPagamento");
	PayConnectorWsEndpoint wsAvviso = this.getWsAvvisoConfig();
	if (wsAvviso == null) {
	    log.debug("inviaAvvisiPagamento endpoint getWsAvvisoConfig non configurato");
	    throw new PayConfigurationException(
		    "l'endpoint per il servizio di generazione degli avvisi non è configurato per il connettore " + this.getConnectorName());
	}
	ElencoDocumentiEsitoType retEsiti = new ElencoDocumentiEsitoType();
	String msg = null;
	PiemontePayClient client = new PiemontePayClient(this.getWsAvvisoConfig());
	for (PayRegistrazioniContabili reg : cmd.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie pos : reg.getPosizioniDebitorie()) {
		msg = "invocazione del servizio getPagamentoAttesoPdf in corso";
		log.debug("inviaAvvisiPagamento elaboro la posizione debitoria {}", pos.getId().getCodice());
		EsitoDocumentoPosizioneDebitoriaType esitoDoc = new EsitoDocumentoPosizioneDebitoriaType();
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoDoc, pos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
		if (!StringUtils.isBlank(pos.getCodiceAvviso())) {
		    esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
		    esitoDoc.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
		    log.debug("inviaAvvisiPagamento recupero il client per la posizione debitoria {}", pos.getId().getCodice());
		    String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(pos);
		    GetPaymentNoticeRequest req = new GetPaymentNoticeRequest(
			    PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfiloPSP(), codiceVersamento, pos.getIuv());
		    log.debug("posizione da annullare {}", req);
		    GetPaymentNoticeResponse avviso = client.getPaymentNotice(req);
		    if (avviso.getResult() != null
			    && StringUtils.defaultString(avviso.getResult().getCode()).equalsIgnoreCase(ESITO_OPERAZIONI_REST_000)) {
			InputStream is = getAvviso(avviso.getPaymentnotice());
			log.debug("inviaAvvisiPagamento chiamata effettuata con successo per la posizione debitoria {}", pos.getId().getCodice());
			esitoDoc.setEsito(true);
			esitoDoc.setNomeDocumento("AVVISO_" + pos.getIuv() + ".pdf");
			esitoDoc.setDocumento(new DataHandler(new InputStreamDataSource(is)));
		    } else {
			this.handleException(new PayException(avviso.getResult().getDescription(), false, avviso.getResult().getCode()), esitoDoc);
			msg = "impossibile scaricare l'avviso PDF a causa dell'errore: " + avviso;
			log.error("inviaAvvisiPagamento - {}", msg);
		    }
		} else {
		    esitoDoc.setEsito(false);
		    esitoDoc.setErroreTemporaneo(true);
		    esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
		    esitoDoc.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
		    msg = "Impossibile richiedere l'avviso di pagamento in quanto non è ancora presente il codice avviso per la posizione debitoria";
		}
		esitoDoc.setMessaggio(msg);
		log.info("inviaAvvisiPagamento - {}", msg);
		retEsiti.getEsitoPosizione().add(esitoDoc);
	    }
	}
	return retEsiti;
    }

    private InputStream getAvviso(String noticePDF) {

	return new ByteArrayInputStream(Base64.decodeBase64(noticePDF));
    }

    private EsitoOperazionePosizioneDebitoriaType annullamentoServizioRest(PayPosizioniDebitorie posizione, boolean pagatoOffline)
	    throws PayException {

	PiemontePayClient client = new PiemontePayClient(this.getWsAnnullamentoConfig());
	String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(posizione);
	AnnullaPosizionePPAYRestRequest req = new AnnullaPosizionePPAYRestRequest(
		PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfiloPSP(), codiceVersamento, posizione.getIuv());
	log.debug("posizione da annullare {}", req);
	AnnullaPosizionePPAYRestResponse annullaPosizioneDebitoria = client.annullaPosizioneDebitoria(req);
	return this.getEsito(annullaPosizioneDebitoria, posizione, pagatoOffline);
    }

    private EsitoOperazionePosizioneDebitoriaType annullaPosizioneAsincrona(PayPosizioniDebitorie posizione, boolean pagatoOffline)
	    throws PayConfigurationException {

	Enti2EPaywsoSecureProxyPortType ppayWs = this.getPiemontepayWsAnnullamento();
	String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(posizione);
	//
	PosizioneDaAggiornareType posizioneDaAnnullare = new PosizioneDaAggiornareType();
	posizioneDaAnnullare.setTipoAggiornamento(TipoAggiornamentoType.ANNULLAMENTO);
	posizioneDaAnnullare.setIdPosizioneDebitoria(posizione.getIdPosizionePsp());
	//
	PosizioniDaAggiornare pda = new PosizioniDaAggiornare();
	pda.getPosizioneDaAggiornare().add(posizioneDaAnnullare);
	//
	ElencoPosizioniDaAggiornareType elencoPos = new ElencoPosizioniDaAggiornareType();
	elencoPos.setPosizioniDaAggiornare(pda);
	//
	TestataAggiornaPosizioniDebitorie testata = new TestataAggiornaPosizioniDebitorie();
	testata.setIdMessaggio(UUID.randomUUID().toString()); //POTREBBE ESSERE UUID UNIVOCO GENERATO AL MOMENTO
	testata.setCFEnteCreditore(PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfiloPSP());
	testata.setCodiceVersamento(codiceVersamento);
	testata.setNumeroPosizioniDebitorie(1);
	//
	AggiornaPosizioniDebitorieRequest deleteReq = new AggiornaPosizioniDebitorieRequest();
	deleteReq.setElencoPosizioniDaAggiornare(elencoPos);
	deleteReq.setTestata(testata);
	//
	ResultType ppayResult = null;
	try {
	    ResponseType response = ppayWs.aggiornaPosizioniDebitorie(deleteReq);
	    ppayResult = response.getResult();
	} catch (Exception e) {
	    log.error("errore nell'invocazione del servizio annullaPosizioniDebitorie: ", e);
	    ppayResult = new ResultType();
	    ppayResult.setCodice(EsitiPPAY.CODE_100.errorCode());
	    ppayResult.setMessaggio(e.toString());
	}
	return this.getEsito(ppayResult, posizione, pagatoOffline);
    }

    @Override
    /**
     * L'URL contiene la seguente query string
     * esitoSessionePagamento/toppay?idPagamento=CSI-T_L219TS_859070&iuv=20243392783254826&descEsito=FALLITO&codEsito=100&source=pagamentoIUV
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ATTENZIONE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! il metodo
     * invocato è quello di PIEMONTEPAYCONNECTOR in quanto per una configurazione su piemonte pay è stato comunicato
     * questo l'esito esitoSessionePagamento/toppay dove TOPPAY + cf_ente_creditore dell'altro connettore
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ATTENZIONE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	String[] idPagamento = reqParams.get("idPagamento");
	String[] codEsiti = reqParams.get("codEsito");
	String[] descEsiti = reqParams.get("descEsito");
	String[] iuv = reqParams.get("iuv");
	Boolean esitoOk = null;
	if (log.isDebugEnabled()) {
	    for (String s : idPagamento) {
		log.debug("idPagamento {}", s);
	    }
	    for (String s : descEsiti) {
		log.debug("descEsiti {}", s);
	    }
	    for (String s : codEsiti) {
		log.debug("codEsiti {}", s);
	    }
	    for (String s : iuv) {
		log.debug("iuv {}", s);
	    }
	}
	if (codEsiti != null && codEsiti.length > 0) {
	    String esitoStr = codEsiti[0];
	    log.debug("esitoStr {}", esitoStr);
	    esitoOk = esitoStr.equals(EsitiPPAY.CODE_000.errorCode());
	}
	List<PaySessioniPagamento> sessioni = new ArrayList<>();
	if (idPagamento != null && idPagamento.length > 0) {
	    String idPosizionePsp = idPagamento[0];
	    log.debug("idPosizionePsp {}", idPosizionePsp);
	    PayPosizioniDebitorie posDebs = payPosizioniDebitorieService.findByIdPosizionePSP(idPosizionePsp);
	    if (posDebs != null) {
		sessioni = this.paySessioniPagamentoService.findSessioniPerPosizioneDebitoria(posDebs.getId().getCodice());
		log.debug("sessioni {}", sessioni.size());
	    }
	}
	for (PaySessioniPagamento sessione : sessioni) {
	    if (log.isDebugEnabled()) {
		log.debug("sessione codice {}, esito {}", sessione.getId().getCodice(), sessione.getEsito());
	    }
	    sessione.setEsito(esitoOk);
	    this.paySessioniPagamentoService.update(sessione);
	}
	if (sessioni.isEmpty()) {
	    log.warn("sessione non trovata");
	    for (String s : idPagamento) {
		log.warn("idPagamento {}", s);
	    }
	    for (String s : descEsiti) {
		log.warn("descEsiti {}", s);
	    }
	    for (String s : codEsiti) {
		log.warn("codEsiti {}", s);
	    }
	    for (String s : iuv) {
		log.warn("iuv {}", s);
	    }
	    return null;
	}
	return sessioni.get(0);
    }

    @Override
    public AttivaSessionePagamentoResponseType attivaSessionePagamento(PayPosizioniDebitorie payPos) throws PayException {

	if (!isUsaServizioRestAttivaSessione()) {
	    log.debug("uso il servizio della classe astratta per la sessione di pagamento ");
	    AttivaSessionePagamentoResponseType response = super.attivaSessionePagamento(payPos);
	    response.setHttpMethodRequired(HttpMethodType.POST);
	    return response;
	}
	AttivaSessionePagamentoResponseType sessResp = new AttivaSessionePagamentoResponseType();
	if (payPos == null || payPos.getId() == null || payPos.getId().getCodice() == null) {
	    throw new PayInvalidRequestException("Dati della posizione debitoria mancanti");
	}
	sessResp.setEsito(true);
	sessResp.setDescEsito("sessione di pagamento attivata");
	String idSessione = this.generaIdSessionePagamento(payPos);
	sessResp.setIdSessione(idSessione);
	sessResp.setSecurityDigest(this.generaDigestSessionePagamento(payPos, idSessione));
	// sessResp.setFormParams(this.generaParametriSessionePagamentoRestV2(payPos, idSessione, sessResp.getSecurityDigest()));
	PiemontePayClient client = new PiemontePayClient(this.getWsCaricamentoConfig());
	PaymentReferences response = client.debtPositionPayment(PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfiloPSP(),
		payPos.getIuv());
	//
	EsitiPPAY esito = EsitiPPAY.fromValue(response.getCodiceEsito());
	log.debug("Esito sessione pagamento {}", esito);
	if (esito == EsitiPPAY.CODE_000 || esito == EsitiPPAY.CODE_050) {
	    sessResp.setPayUrl(response.getPaymentUrl());
	    sessResp.setHttpMethodRequired(HttpMethodType.GET);
	} else {
	    log.error("Errore nella generazione della sessione di pagamento, Esito sessione pagamento {}", esito);
	    throw new PayException("Errore nella generazione della sessione di pagamento, Esito sessione pagamento " + esito);
	}
	return sessResp;
    }

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

	List<PosizioneDebitoriaType> rate = registrazioneContabile.getRate().getRata();
	int indice = 1;
	for (PosizioneDebitoriaType rata : rate) {
	    int numeroRata = rata.getNumeroRata() != null ? rata.getNumeroRata().intValue() : indice;
	    String riferimentoPosizioneDebitoria = PopolamentoDatiHelper.getRiferimentoPosizioneDebitoria(rata, registrazioneContabile, numeroRata);
	    List<ImportoPagamentoType> ci = rata.getImporto().getComponenteImporto();
	    for (ImportoPagamentoType importo : ci) {
		if (StringUtils.isBlank(importo.getNumeroAccertamento())) {
		    throw new ValidazionePosizioniDebitorieException(
			    "Numero accertamento della componente importo mancante. Riferimento posizione debitoria: " +
								     riferimentoPosizioneDebitoria);
		}
	    }
	    indice++;
	}
    }

    @Override
    public String generaIdMessaggio() {

	String genId = super.generaIdMessaggio();
	genId = genId.replace("-", "");
	return genId;
    }

    private Enti2EPaywsoSecureProxyPortType getPiemontepayWsAnnullamento() {

	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	factory.setServiceClass(Enti2EPaywsoSecureProxyPortType.class);
	PayConnectorWsEndpoint annullamentoWs = this.getWsAnnullamentoConfig();
	factory.setAddress(annullamentoWs.getEndpointUrl());// <- must be /soap there, otherwise 404
	Enti2EPaywsoSecureProxyPortType info = (Enti2EPaywsoSecureProxyPortType) factory.create();
	BindingProvider bp = (BindingProvider) info;
	bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, annullamentoWs.getEndpointUrl());
	Client client = ClientProxy.getClient(info);
	Endpoint cxfEndpoint = client.getEndpoint();
	Map<String, Object> outProps = new HashMap<>();
	outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN + " " + WSHandlerConstants.TIMESTAMP);
	outProps.put(WSHandlerConstants.USER, annullamentoWs.getUtente());
	outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
	SecurityPwdCallBackHandler pwdCallback = new SecurityPwdCallBackHandler(annullamentoWs.getUtente(), annullamentoWs.getPassword());
	outProps.put(WSHandlerConstants.PW_CALLBACK_REF, pwdCallback);
	WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
	cxfEndpoint.getOutInterceptors().add(wssOut);
	if (annullamentoWs.getTimeout() != null) {
	    HTTPConduit conduit = (HTTPConduit) client.getConduit();
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(annullamentoWs.getTimeout());
	    httpClientPolicy.setReceiveTimeout(annullamentoWs.getTimeout());
	    conduit.setClient(httpClientPolicy);
	}
	return info;
    }

    private EsitoOperazionePosizioneDebitoriaType getEsito(ResultType ppayResult, PayPosizioniDebitorie posizione, boolean pagatoOffline) {

	PayStatoPagamenti statoAttuale = this.payStatoPagamentiService.getStatoPosizioneDebitoria(posizione);
	//
	StatoPagamentoType statoPagamento = null;
	if (statoAttuale != null) {
	    try {
		statoPagamento = StatoPagamentoType.fromValue(statoAttuale.getStato());
	    } catch (Exception e) {
		log.error("stato pagamento non valido: " + statoAttuale.getStato(), e);
	    }
	}
	//
	EsitiPPAY esitoPPay = this.decodificaEsito(ppayResult.getCodice());
	//
	EsitoOperazionePosizioneDebitoriaType esito = new EsitoOperazionePosizioneDebitoriaType();
	esito.setEsito(true);
	esito.setStato(statoPagamento);
	esito.setMessaggio(statoPagamento != null ? StatiPagamento.fromValue(statoPagamento.name()).description()
		: "stato della posizione debitoria non definito");
	//
	PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, posizione,
		payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(posizione.getId().getCodice()));
	if (esitoPPay != EsitiPPAY.CODE_000 && esitoPPay != EsitiPPAY.CODE_050) {
	    esito.setEsito(false);
	    esito.setCodiceErrore(ppayResult.getCodice());
	    esito.setMessaggio(ppayResult.getMessaggio());
	} else {
	    StatoPagamentoType nuovoStato = pagatoOffline ? StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE
		    : StatoPagamentoType.ANNULLAMENTO_RICHIESTO;
	    StatiPagamento nuovaDescrizioneStato = pagatoOffline ? StatiPagamento.PAGATO_OFFLINE_DA_ANNULLARE : StatiPagamento.ANNULLAMENTO_RICHIESTO;
	    esito.setStato(nuovoStato);
	    esito.setMessaggio(nuovaDescrizioneStato.description());
	}
	//
	return esito;
    }

    private EsitoOperazionePosizioneDebitoriaType getEsito(AnnullaPosizionePPAYRestResponse response, PayPosizioniDebitorie posizione,
	    boolean pagatoOffline) {

	EsitoOperazionePosizioneDebitoriaType esito = new EsitoOperazionePosizioneDebitoriaType();
	esito.setEsito(true);
	StatoPagamentoType nuovoStato = pagatoOffline ? StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO : StatoPagamentoType.ANNULLATO;
	String descrizioneStato = pagatoOffline ? StatiPagamento.PAGATO_OFFLINE_ANNULLATO.description() : StatiPagamento.ANNULLATO.description();
	PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, posizione,
		payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(posizione.getId().getCodice()));
	if (!(response.getCode().equalsIgnoreCase(ESITO_OPERAZIONI_REST_000) || response.getCode().equalsIgnoreCase(ESITO_OPERAZIONI_REST_300))) {
	    PayStatoPagamenti statoAttuale = this.payStatoPagamentiService.getStatoPosizioneDebitoria(posizione);
	    esito.setEsito(false);
	    nuovoStato = StatoPagamentoType.fromValue(statoAttuale.getStato());
	    esito.setCodiceErrore(response.getCode());
	    esito.setMessaggio(response.getDescription());
	}
	esito.setStato(nuovoStato);
	esito.setMessaggio(descrizioneStato);
	return esito;
    }

    private EsitiPPAY decodificaEsito(String codiceEsito) {

	EsitiPPAY retEsito = null;
	if (StringUtils.isNotBlank(codiceEsito)) {
	    try {
		retEsito = EsitiPPAY.fromValue(codiceEsito);
	    } catch (IllegalArgumentException e) {
		retEsito = EsitiPPAY.CODE_999;
	    }
	}
	return retEsito;
    }

    @Override
    public boolean supportaRicevutaTelematica() {

	return true;
    }

    @Override
    public boolean supportaAttivaSessionePagamento() {

	return true;
    }

    @Override
    public boolean supportaModificaDataScadenza() {

	return false;
    }

    private boolean isUsaServizioRestAnnullamento() {

	String v = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.PPAY_USA_SERV_REST_ANNULLA_POS);
	if (StringUtils.isNotBlank(v)) {
	    try {
		log.debug("isUsaServizioRestAnnullamento {}", v);
		return Boolean.parseBoolean(v);
	    } catch (NumberFormatException e) {
		throw new IllegalArgumentException("Errore nella configurazione del parametro " + ConfigParamNames.PPAY_USA_SERV_REST_ANNULLA_POS +
						   " impostato con valore " + v);
	    }
	}
	return true;
    }

    private boolean isUsaServizioRestAttivaSessione() throws IllegalArgumentException {

	String v = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.PPAY_USA_SERV_REST_ATTIVA_SESS);
	if (StringUtils.isNotBlank(v)) {
	    try {
		log.debug("getUsaServizioRestAttivaSessione {}", v);
		return Boolean.parseBoolean(v);
	    } catch (NumberFormatException e) {
		throw new IllegalArgumentException("Errore nella configurazione del parametro " + ConfigParamNames.PPAY_USA_SERV_REST_ATTIVA_SESS +
						   " impostato con valore " + v);
	    }
	}
	return true;
    }

    @Override
    protected String generaDigestSessionePagamento(PayPosizioniDebitorie payPos, String idSessionePagamento) throws PayException {

	return new CommonPiemontePayUtils().generaDigestSessionePagamento(payPos, idSessionePagamento);
    }

    @Override
    protected FormParametersType generaParametriSessionePagamento(PayPosizioniDebitorie posDeb, String idSessione, String digest)
	    throws PayException {

	return new CommonPiemontePayUtils().generaParametriSessionePagamento(posDeb, idSessione, digest);
    }

    @Override
    public boolean supportaMolteCausaliRaggruppate() {

	return true;
    }

    @Override
    public List<IParameter> getListaParametriRichiesti() {

	return ParametriConnettoreHelper.getMappaParametriConnettore(this.getClass().getName());
    }

    @Override
    public IUVHelper generaIUV(PayPosizioniDebitorie pos, String identificativoCausalePerCalcolo) {

	return null;
    }

    private boolean isSupportaGetRt() {

	String v = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.PPAY_USA_SERV_GET_RT_SUPPORT);
	if (StringUtils.isNotBlank(v)) {
	    try {
		log.debug("isUsaServizioRestAnnullamento {}", v);
		return Boolean.parseBoolean(v);
	    } catch (NumberFormatException e) {
		throw new IllegalArgumentException(
			"Errore nella configurazione del parametro " + ConfigParamNames.PPAY_USA_SERV_GET_RT_SUPPORT + " impostato con valore " + v);
	    }
	}
	return true;
    }

    /**
     * Se la data di fine validità (data scadenza) è nulla o uguale/futura rispetto alla data corrente allora interrogo
     * PPAY per sapere lo stato (come adesso) <br />
     * Se la data di fine validità è passata rispetto alla data corrente allora non interrogo PPAY e non aggiorniamo la
     * situazione su mercato.
     * 
     * @param posizione
     * @return
     */
    private boolean verificaDataScadenzaMinoreDiOggi(PayPosizioniDebitorie posizione) {

	if (posizione.getDataScadenza() == null) {
	    log.debug("verifico la dataFineValidita dataScadenza nulla per la posizione {}", posizione.getId());
	    return false;
	}
	Date oggi = new Date();
	log.debug("verifico la dataFineValidita oggi: {}, dataScadenza {}", oggi, posizione.getDataScadenza());
	return Utilities.compareDates(posizione.getDataScadenza(), oggi) < 0;
    }

    @Override
    public boolean supportaModificaDataFineValidita() {

	return true;
    }

    @Override
    public void modificaDataFineValiditaPosizioneDebitoria(Integer idPosizioneDebitoria, Date nuovaDataFineValidita) throws PayException {

	try {
	    PiemontePayClient client = new PiemontePayClient(this.getWsCaricamentoConfig());
	    PayPosizioniDebitorie posizione = payPosizioniDebitorieService.findById(new PkId(idPosizioneDebitoria));
	    String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(posizione);
	    posizione.setDataFineValidita(nuovaDataFineValidita);
	    DebtPositionDataDFV posizionePut = new DebtPositionDataDFV();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    posizionePut.setDataFineValidita(sdf.format(nuovaDataFineValidita));
	    client.putDebtPosition(posizionePut, PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfiloPSP(), codiceVersamento,
		    posizione.getIuv());
	} catch (Exception e) {
	    log.error("Errore su posizione debitoria pay con id {}", idPosizioneDebitoria, e);
	    throw new PayException(e);
	}
    }
}
