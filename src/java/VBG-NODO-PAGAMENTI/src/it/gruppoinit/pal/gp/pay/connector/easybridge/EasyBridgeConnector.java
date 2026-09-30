package it.gruppoinit.pal.gp.pay.connector.easybridge;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.activation.DataHandler;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gov.pagopa.rendicontazione.FlussoRiversamento;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.core.utils.InputStreamDataSource;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.WEBSEasyBridgeInterfaceSoap;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model.DatiPagamentoInAttesa;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model.PdpCancellaPagamentoRequest;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model.PdpCancellaPagamentoResult;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model.PdpCaricaPagamentoRequest;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model.PdpCaricaPagamentoResult;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model.PdpPaymentNoticeRequest;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model.PdpPaymentNoticeResult;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model.SoggettoPagatore;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.model.Output;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.TipoSoggetto;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.parameters.ParametroAggiungiGiorniADataScadenzaAvviso;
import it.gruppoinit.pal.gp.pay.scheduler.ServiziSchedulatiEnum;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.ParametriConnettoreHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.FormParamType;
import it.gruppoinit.pal.gp.pay.ws.schema.FormParametersType;
import it.gruppoinit.pal.gp.pay.ws.schema.HttpMethodType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;

public class EasyBridgeConnector extends AbstractPayConnector implements IPayConnector {

    private static final String ESITO_VALUE_PARAM = "esitoParameter";
    private static final Logger log = LoggerFactory.getLogger(EasyBridgeConnector.class);
    private static final String IUV_CODE_PARAM = "IuvCode";
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PaySessioniPagamentoService paySessioniPagamentoService;
    @Autowired
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;
    private EasyBridgeUtils utils = new EasyBridgeUtils();

    public static void main(String[] args) throws FileNotFoundException, IOException {

	File riv = new File(args[0]);
	String xml = IOUtils.readFileAsString(new FileInputStream(riv));
	FlussoRiversamento risposta = (FlussoRiversamento) IOUtils.unMarshallString(xml, FlussoRiversamento.class);
	log.error("{}", risposta);
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	ElencoPosizioniDebitorieEsitoType results = new ElencoPosizioniDebitorieEsitoType();
	WEBSEasyBridgeInterfaceSoap ppayWs = this.getCaricamentoWsPort();
	PayProfiliEntiCreditori profilo = PayConfigurationHelper.getProfiloEnteCreditore();
	//il codice versamento deve essere lo stesso per tutte le registrazioni che si trasmettono ==> valido che tutte le registrazioni contabili abbiano la stessa causale di registrazione
	for (PayRegistrazioniContabili payRegCont : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie payPosDeb : payRegCont.getPosizioniDebitorie()) {
		EsitoOperazionePosizioneDebitoriaType esitoPos = caricaPosizioneDebitoria(payPosDeb, profilo, ppayWs);
		results.getEsitoPosizione().add(esitoPos);
	    }
	}
	return results;
    }

    private String popolaPosizioneDebitoria(PayPosizioniDebitorie payPosDeb) throws PayException {

	payPosDeb.setIdPosizionePsp(this.generaIdPosizioneDebitoria(payPosDeb));
	PdpCaricaPagamentoRequest pagamentoRequest = new PdpCaricaPagamentoRequest();
	DatiPagamentoInAttesa datiPagamentoInAttesa = new DatiPagamentoInAttesa();
	String aggiungiGGADataScadenza = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(payPosDeb,
		new ParametroAggiungiGiorniADataScadenzaAvviso());
	if (StringUtils.isNotBlank(aggiungiGGADataScadenza)) {
	    aggiungiGGADataScadenza = aggiungiGGADataScadenza.trim();
	    if (Utilities.isInteger(aggiungiGGADataScadenza)) {
		Integer ggDaAggiungere = Integer.parseInt(aggiungiGGADataScadenza);
		Date dataScadenza = payPosDeb.getDataScadenza();
		Date dataScadenzaAvviso = Utilities.addDays(dataScadenza, ggDaAggiungere.intValue());
		log.debug("la casuale prevede l'aggiornamento della data scadenza avviso [{}]-[{}]-[{}]", dataScadenza, ggDaAggiungere,
			dataScadenzaAvviso);
		datiPagamentoInAttesa.setDataScadenzaPagamento(Utilities.formatDateWithPattern(dataScadenzaAvviso, "yyyy-MM-dd"));
	    }
	} else {
	    datiPagamentoInAttesa.setDataScadenzaPagamento(Utilities.formatDateWithPattern(payPosDeb.getDataScadenza(), "yyyy-MM-dd"));
	}
	datiPagamentoInAttesa.setVisualisationExpirationDate(Utilities.formatDateWithPattern(payPosDeb.getDataScadenza(), "yyyy-MM-dd"));
	datiPagamentoInAttesa.setUniqueClientID(payPosDeb.getIdPosizionePsp());
	//Soggetto pagatore
	SoggettoPagatore soggettoPagatore = new SoggettoPagatore();
	if (payPosDeb.getSoggettoDebitore().getCfPi().length() == 16) {
	    soggettoPagatore.setTipoIdentificativoUnivocoPagatore(TipoSoggetto.F.name());
	} else {
	    soggettoPagatore.setTipoIdentificativoUnivocoPagatore(TipoSoggetto.G.name());
	}
	soggettoPagatore.setCodiceIdentificativoUnivocoPagatore(payPosDeb.getSoggettoDebitore().getCfPi());
	soggettoPagatore.setAnagraficaPagatore(payPosDeb.getSoggettoDebitore().getDenominazioneCompleta());
	soggettoPagatore.setIndirizzoPagatore(payPosDeb.getSoggettoDebitore().getVia());
	soggettoPagatore.setLocalitaPagatore(payPosDeb.getSoggettoDebitore().getLocalita());
	soggettoPagatore.setProvinciaPagatore(payPosDeb.getSoggettoDebitore().getProvincia());
	soggettoPagatore.setEmailPagatore(payPosDeb.getSoggettoDebitore().getEmail());
	datiPagamentoInAttesa.setSoggettoPagatore(soggettoPagatore);
	datiPagamentoInAttesa.setImportoTotaleDaVersare(payPosDeb.getRegistrazioneContabile().getImporto().setScale(2));
	datiPagamentoInAttesa.setCausaleVersamentoModel3(StringUtils.left(payPosDeb.getDescrizioneCausale(), 70));
	datiPagamentoInAttesa.setDescrizioneTestualeCausaleVersamento(StringUtils.left(payPosDeb.getDescrizioneCausale(), 70));
	String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPosDeb);
	datiPagamentoInAttesa.setIdentificativoServizio(codiceVersamento);
	pagamentoRequest.setDatiPagamentoInAttesa(datiPagamentoInAttesa);
	return new EasyBridgeUtils().convertToBase64(pagamentoRequest);
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException {

	WEBSEasyBridgeInterfaceSoap bridgeWs = this.getCaricamentoWsPort();
	ElencoPosizioniDebitorieEsitoType result = new ElencoPosizioniDebitorieEsitoType();
	PayProfiliEntiCreditori profilo = PayConfigurationHelper.getProfiloEnteCreditore();
	for (PayRegistrazioniContabili reg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie pos : reg.getPosizioniDebitorie()) {
		String iuv = pos.getIuv();
		PdpCancellaPagamentoRequest req = new PdpCancellaPagamentoRequest();
		req.setIdentificativoUnivocoVersamento(iuv);
		String param = this.utils.convertToBase64(req);
		log.debug("PdpCancellaPagamentoRequest {}, {}, {}", new Object[] { profilo.getCfCodiceProfiloPSP(), profilo.getIdAppPSP(), param });
		Output pdpCancellaPagamento = bridgeWs.pdpCancellaPagamento(profilo.getCfCodiceProfiloPSP(), profilo.getIdAppPSP(), param);
		// verifica che sia andata a buon fine
		param = pdpCancellaPagamento.getParam();
		try {
		    PdpCancellaPagamentoResult response = this.utils.getObjectFromBase64(param, PdpCancellaPagamentoResult.class);
		    EsitoOperazionePosizioneDebitoriaType esito = new EsitoOperazionePosizioneDebitoriaType();
		    esito.setIdPosizione(BigInteger.valueOf(pos.getId().getCodice()));
		    if (response.getEsitoOperazione().equalsIgnoreCase("OK")) {
			esito.setStato(pagatoOffline ? StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO : StatoPagamentoType.ANNULLATO);
			esito.setEsito(true);
		    } else {
			esito.setEsito(false);
			esito.setCodiceErrore(response.getEsitoOperazione());
		    }
		    result.getEsitoPosizione().add(esito);
		} catch (UnsupportedEncodingException e) {
		    throw new PayException("Errore nella conversione del file " + param);
		}
	    }
	}
	// pdpCancellaPagamento
	return result;
    }

    @Override
    public AttivaSessionePagamentoResponseType attivaSessionePagamento(PayPosizioniDebitorie payPosizioneDebitoria) throws PayException {

	log.debug("attivaPagamentoOnTheFly ");
	AttivaSessionePagamentoResponseType attivaSessioneOTF = new AttivaSessionePagamentoResponseType();
	String idSessione = StringUtils
		.left(ORMHelper.getIdcomune() + "|" + payPosizioneDebitoria.getIdPosizionePsp() + "|" + UUID.randomUUID().toString(), 50);
	attivaSessioneOTF.setEsito(true);
	String url = PayConfigurationHelper.getProfiloEnteCreditore().getPayConnector().getUrlPortalePagamenti();
	log.debug("url = {}", url);
	attivaSessioneOTF.setHttpMethodRequired(HttpMethodType.POST);
	log.debug("attivaPagamentoOnTheFly payPosDeb={}-{}", payPosizioneDebitoria.getId(), payPosizioneDebitoria.getIuv());
	FormParametersType params = new FormParametersType();
	FormParamType iuv = new FormParamType();
	iuv.setParamName(IUV_CODE_PARAM);
	iuv.setValue(payPosizioneDebitoria.getIuv());
	params.getParam().add(iuv);
	attivaSessioneOTF.setPayUrl(url);
	attivaSessioneOTF.setFormParams(params);
	attivaSessioneOTF.setIdSessione(idSessione);
	return attivaSessioneOTF;
    }

    private EsitoOperazionePosizioneDebitoriaType caricaPosizioneDebitoria(PayPosizioniDebitorie payPosDeb, PayProfiliEntiCreditori profilo,
	    WEBSEasyBridgeInterfaceSoap ppayWs) throws PayException {

	String contentBase64 = popolaPosizioneDebitoria(payPosDeb);
	Output pdpCaricaPagamento = ppayWs.pdpCaricaPagamento(profilo.getCfCodiceProfiloPSP(), profilo.getIdAppPSP(), contentBase64);
	// verifica se andato a buon fine e recupera IUV
	// in caso di errore lo setta nel esito posizione debitoria
	String param = pdpCaricaPagamento.getParam();
	EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
	esitoPos.setIdPosizione(BigInteger.valueOf(payPosDeb.getId().getCodice()));
	PdpCaricaPagamentoResult objectFromBase64 = null;
	try {
	    objectFromBase64 = this.utils.getObjectFromBase64(param, PdpCaricaPagamentoResult.class);
	} catch (UnsupportedEncodingException e) {
	    log.error("Errore nel recupero dei dati da EasyBridge", e);
	    esitoPos.setEsito(false);
	    esitoPos.setCodiceErrore("501");
	    esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
	    esitoPos.setMessaggio("Errore nel recupero dei dati da EasyBridge" + e.getMessage());
	    return esitoPos;
	}
	if (!objectFromBase64.getEsitoOperazione().equalsIgnoreCase("OK")) {
	    log.error("Errore nella creazione della posizione debitoria {}", objectFromBase64.getCodiceErrore());
	    esitoPos.setCodiceErrore("502");
	    esitoPos.setEsito(false);
	    esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
	    esitoPos.setMessaggio("Errore nella creazione della posizione debitoria " + objectFromBase64.getCodiceErrore());
	    return esitoPos;
	}
	esitoPos.setEsito(true);
	esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
	esitoPos.setMessaggio(StatiPagamento.ATTIVATO_IN_PSP.description());
	// setta lo iuv
	esitoPos.setIUV(objectFromBase64.getDatiRestituiti().getIdentificativoUnivocoVersamento());
	String codiceAvviso = fromIUV(objectFromBase64.getDatiRestituiti().getIdentificativoUnivocoVersamento());
	esitoPos.setCodiceAvviso(codiceAvviso);
	if (payPosDeb.getRegistrazioneContabile() != null && payPosDeb.getRegistrazioneContabile().getId().getCodice() != null) {
	    esitoPos.setIdRegistrazioneContabile(BigInteger.valueOf(payPosDeb.getRegistrazioneContabile().getId().getCodice()));
	}
	Set<String> riferimentiClientPosizione = payPosizioniDebitorieService
		.findRiferimentiClientByPosizioneDebitoria(payPosDeb.getId().getCodice());
	if (!riferimentiClientPosizione.isEmpty()) {
	    esitoPos.getRiferimentoClient().addAll(riferimentiClientPosizione);
	}
	return esitoPos;
    }

    private String fromIUV(String identificativoUnivocoVersamento) {

	if (StringUtils.isBlank(identificativoUnivocoVersamento)) {
	    return null;
	}
	//	in ambiente di produzione: 3 + IUV (base 17)     
	//	in ambiente di collaudo: 010 + IUV (base 15)
	if (identificativoUnivocoVersamento.length() < 17) {
	    return "010" + identificativoUnivocoVersamento;
	}
	return "3" + identificativoUnivocoVersamento;
    }

    @Override
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(PosizioniDebitorieCommand cmd) throws PayException {

	// INOLTRA A https://preprod.pagaonlinepa.it:50061/POL_CitizenShortcut/GEN_Default.aspx?idDominio=01556360152&GroupKey=27092021
	// potrebbe essere fissa su pay_connector_config.url_portale_pagamenti 
	// IDOMINIO profiloEnte.getCfCodiceProfiloPSP()
	// group key da parametro??? 27092021
	//	parametro in post  IUV_CODE_PARAM;
	WEBSEasyBridgeInterfaceSoap ppayWs = this.getCaricamentoWsPort();
	PayProfiliEntiCreditori profilo = PayConfigurationHelper.getProfiloEnteCreditore();
	log.debug("attivaPagamentoOnTheFly ");
	AttivaPagamentoOnTheFlyResponseType result = new AttivaPagamentoOnTheFlyResponseType();
	AttivaSessionePagamentoResponseType attivaSessioneOTF = new AttivaSessionePagamentoResponseType();
	result.setSessionePagamento(attivaSessioneOTF);
	List<PayRegistrazioniContabili> rcs = cmd.getRegistrazioniPosizioni();
	attivaSessioneOTF.setEsito(true);
	String url = PayConfigurationHelper.getProfiloEnteCreditore().getPayConnector().getUrlPortalePagamenti();
	log.debug("url = {}", url);
	attivaSessioneOTF.setHttpMethodRequired(HttpMethodType.POST);
	FormParametersType params = new FormParametersType();
	Set<String> iuvs = new LinkedHashSet<String>();
	for (PayRegistrazioniContabili payRegCont : rcs) {
	    for (PayPosizioniDebitorie payPosDeb : payRegCont.getPosizioniDebitorie()) {
		EsitoOperazionePosizioneDebitoriaType esitoPos = caricaPosizioneDebitoria(payPosDeb, profilo, ppayWs);
		log.debug("attivaPagamentoOnTheFly payPosDeb={}-{}", payPosDeb.getId(), payPosDeb.getIuv());
		iuvs.add(esitoPos.getIUV());
		result.getPosizioneInserita().add(esitoPos);
	    }
	}
	FormParamType iuv = new FormParamType();
	iuv.setParamName(IUV_CODE_PARAM);
	iuv.setValue(StringUtils.join(iuvs, ";"));
	params.getParam().add(iuv);
	attivaSessioneOTF.setFormParams(params);
	attivaSessioneOTF.setPayUrl(url);
	attivaSessioneOTF.setIdSessione(UUID.randomUUID().toString());
	result.setSessionePagamento(attivaSessioneOTF);
	return result;
    }

    @Override
    public ElencoDocumentiEsitoType inviaAvvisiPagamento(PosizioniDebitorieCommand cmd) throws PayException {

	WEBSEasyBridgeInterfaceSoap bridgeWs = this.getCaricamentoWsPort();
	PayProfiliEntiCreditori profilo = PayConfigurationHelper.getProfiloEnteCreditore();
	ElencoDocumentiEsitoType retEsiti = new ElencoDocumentiEsitoType();
	String msg = null;
	for (PayRegistrazioniContabili payRegCont : cmd.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie pos : payRegCont.getPosizioniDebitorie()) {
		PdpPaymentNoticeRequest req = new PdpPaymentNoticeRequest();
		req.setIdentificativoUnivocoVersamento(pos.getIuv());
		req.setReturnNoticePDF("YES");
		String param = this.utils.convertToBase64(req);
		log.debug("PdpPaymentNoticeRequest {}, {}, {}", new Object[] { profilo.getCfCodiceProfiloPSP(), profilo.getIdAppPSP(), param });
		Output pdpPaymentNotice = bridgeWs.pdpPaymentNotice(profilo.getCfCodiceProfiloPSP(), profilo.getIdAppPSP(), param);
		param = pdpPaymentNotice.getParam();
		EsitoDocumentoPosizioneDebitoriaType esitoDoc = new EsitoDocumentoPosizioneDebitoriaType();
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoDoc, pos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
		try {
		    PdpPaymentNoticeResult response = this.utils.getObjectFromBase64(param, PdpPaymentNoticeResult.class);
		    esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
		    esitoDoc.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
		    if (response.getEsitoOperazione().equalsIgnoreCase("OK")) {
			esitoDoc.setEsito(true);
			InputStream avviso = getAvviso(response.getDatiRestituiti().getAvvisoDiPagamento());
			log.debug("inviaAvvisiPagamento chiamata effettuata con successo per la posizione debitoria {}", pos.getId().getCodice());
			esitoDoc.setEsito(true);
			esitoDoc.setNomeDocumento("AVVISO_" + pos.getIuv() + ".pdf");
			esitoDoc.setDocumento(new DataHandler(new InputStreamDataSource(avviso)));
		    } else {
			esitoDoc.setEsito(false);
			esitoDoc.setErroreTemporaneo(true);
			esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
			esitoDoc.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
			msg = "Impossibile richiedere l'avviso di pagamento errore tornato dal servizio " + response.getEsitoOperazione();
			if (response.getCodiceErrore() != null) {
			    msg += "(" + response.getCodiceErrore() + ")";
			}
			log.error("Errore nell'invio avviso {}", msg);
			esitoDoc.setMessaggio(msg);
		    }
		} catch (UnsupportedEncodingException e) {
		    throw new PayException("Errore nella conversione del file " + param);
		}
		retEsiti.getEsitoPosizione().add(esitoDoc);
	    }
	}
	// pdpPaymentNotice
	return retEsiti;
    }

    @Override
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	// nodo-pagamenti/esitoSessionePagamento/pes_a818?idDominio=01556360152&esitoValue=ERR
	// IN POST IL PARAMETRO IuvCode 215280000000512
	String[] iuvCaricati = reqParams.get(IUV_CODE_PARAM);
	String[] esitoVal = reqParams.get(ESITO_VALUE_PARAM);
	String esito = null;
	if (esitoVal != null && esitoVal.length > 0) {
	    esito = esitoVal[0];
	}
	PaySessioniPagamento ret = null;
	PayPosizioniDebitorie pos = null;
	for (String iuv : iuvCaricati) {
	    pos = this.payPosizioniDebitorieService.findByIUV(iuv);
	    if (pos != null) {
		List<PaySessioniPagamento> sessioni = this.paySessioniPagamentoService
			.findSessioniAttivePerPosizioneDebitoria(pos.getId().getCodice());
		if (!sessioni.isEmpty()) {
		    for (PaySessioniPagamento paySessioniPagamento : sessioni) {
			paySessioniPagamento.setEsito("OK".equals(esito));
			this.paySessioniPagamentoService.update(paySessioniPagamento);
		    }
		    ret = sessioni.get(0); // ne ritorno una perché la redirect è sempre quella
		}
	    }
	}
	return ret;
    }

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

    }

    @Override
    public boolean supportaAttivaSessionePagamento() {

	return false;
    }

    @Override
    public boolean supportaPagamentoOffLine() {

	return true;
    }

    @Override
    public boolean supportaModificaDataScadenza() {

	return false;
    }

    @Override
    public boolean supportaPagamentoOnTheFly() {

	return true;
    }

    private InputStream getAvviso(String noticePDF) {

	InputStream is = new ByteArrayInputStream(this.utils.decodeString(noticePDF));
	return is;
    }

    private WEBSEasyBridgeInterfaceSoap getCaricamentoWsPort() {

	return this.utils.getCaricamentoWsPort(this.getWsCaricamentoConfig());
    }

    @Override
    public List<IParameter> getListaParametriRichiesti() {

	return ParametriConnettoreHelper.getMappaParametriConnettore(this.getClass().getName());
    }

    @Override
    public IUVHelper generaIUV(PayPosizioniDebitorie pos, String identificativoCausalePerCalcolo) {

	return null;
    }

    @Override
    public boolean supportaMolteCausaliRaggruppate() {

	return true;
    }

    @Override
    public List<ServiziSchedulatiEnum> getListaServiziSchedulatiSupportati() {

	List<ServiziSchedulatiEnum> servizi = new ArrayList<>();
	servizi.add(ServiziSchedulatiEnum.ELABORAZIONE_TRACCIATI_PAGOPA);
	return servizi;
    }
}
