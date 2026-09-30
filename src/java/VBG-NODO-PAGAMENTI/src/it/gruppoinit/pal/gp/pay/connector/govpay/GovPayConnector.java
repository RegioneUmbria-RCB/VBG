package it.gruppoinit.pal.gp.pay.connector.govpay;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gov.digitpa.schemas._2011.pagamenti.v_6_2_0.CtDatiSingoloVersamentoRPT;
import it.gov.digitpa.schemas._2011.pagamenti.v_6_2_0.CtRichiestaPagamentoTelematico;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.InputStreamDataSource;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.client.BasicAuthParams;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.client.GovPayClient;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.client.GovPayClientParameters;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.client.SSLClientParameters;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.Soggetto;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti.NuovoPagamento;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti.Pagamento;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti.PagamentoCreato;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti.RiferimentoAvviso;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti.StatoPagamento;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.NuovaPendenza;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.NuovaVocePendenza;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.PatchOp;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.Pendenza;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.PendenzaCreata;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.StatoPendenza;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.TipoPendenzaTipologia;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.TipoSoggetto;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.parameters.ParametroIdUnitaOperativa;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.ParametriConnettoreHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.ImportoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;

public class GovPayConnector extends AbstractPayConnector implements IPayConnector {

    private static final Logger log = LoggerFactory.getLogger(GovPayConnector.class);
    @Autowired
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    @Autowired
    private PayStatoPagamentiService pagamenPayStatoPagamentiService;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String PATH_AGGIORNAMENTO = "/stato";
    private static final String VALUE_AGGIORNAMENTO = StatoPendenza.ANNULLATA.name();
    @Autowired
    private PaySessioniPagamentoService paySessioniPagamentoService;
    @Autowired
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;

    public enum TipiPagamento {

	BBT("Bonifico Bancario di Tesoreria") //
	, BP("Bollettino Postale on-line") // 
	, AD("Addebito diretto") //
	, CP("Carta di pagamento") //
	, PO("Pagamento attivato presso PSP") //
	, OBEP("On-line Banking E-Payment") //
	, OTH("Altro") // 
	, JIF("JIF") //
	, MYBK("My Banking");

	private String name;

	private TipiPagamento(String name) {

	    this.name = name;
	}

	public String value() {

	    return this.name;
	}
    }

    private GovPayClient getClient(PayConnectorWsEndpoint payConnectorWsEndpoint) {

	String trustStorelocation = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SSL_TRUST_STORE_LOCATION);
	String certAlias = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SSL_KEY_STORE_CERT_ALIAS);
	String trustStorePassword = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SSL_TRUST_STORE_PASSWORD);
	String keyStoreLocation = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SSL_KEY_STORE_LOCATION);
	String keyStorePassword = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SSL_KEY_STORE_PASSWORD);
	String urlApiProfilo = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.GOV_PAY_URL_API_PROFILO); // "/govpay/backend/api/pendenze/rs/basic/v2";
	String urlApiPendenze = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.GOV_PAY_URL_API_PENDENZE);// "/govpay/backend/api/pendenze/rs/basic/v2/pendenze"; 
	String urlApiPagamento = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.GOV_PAY_URL_API_PAGAMENTI); //"/govpay/frontend/api/pagamento/rs/basic/pagamenti";
	SSLClientParameters sslAuth = null;
	if (StringUtils.isNotBlank(trustStorelocation) && trustStorelocation.indexOf("jks") != -1) {
	    sslAuth = new SSLClientParameters(certAlias, trustStorelocation, trustStorePassword, keyStoreLocation, keyStorePassword);
	}
	String urlPatchOperations = this.payConnectorConfigValuesService
		.getValoreParametroConfigurazione(ConfigParamNames.GOV_PAY_BASE_URL_PATCH_OPS);
	Map<ConfigParamNames, String> connectorConfigParams = new HashMap<>();
	if (StringUtils.isNotBlank(urlPatchOperations)) {
	    connectorConfigParams.put(ConfigParamNames.GOV_PAY_BASE_URL_PATCH_OPS, urlPatchOperations);
	}
	BasicAuthParams basicAuth = new BasicAuthParams(payConnectorWsEndpoint.getPassword(), payConnectorWsEndpoint.getUtente());
	GovPayClientParameters params = new GovPayClientParameters(payConnectorWsEndpoint.getEndpointUrl(), urlApiProfilo, urlApiPendenze,
		urlApiPagamento, basicAuth, sslAuth, connectorConfigParams);
	return GovPayClient.getClient(params);
	// con basic authentication
	// return new GovPayClient(payConnectorWsEndpoint.getUtente(), payConnectorWsEndpoint.getPassword(), payConnectorWsEndpoint.getEndpointUrl());
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	// invoca API PENDENZE
	//	PUT
	//	​/pendenze​/{idA2A}​/{idPendenza}
	//	Inserimento o aggiornamento di una pendenza
	GovPayClient client = getClient(this.getWsCaricamentoConfig());
	ElencoPosizioniDebitorieEsitoType result = new ElencoPosizioniDebitorieEsitoType();
	PayProfiliEntiCreditori profiloEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		NuovaPendenza pendenza = new NuovaPendenza();
		String idUnitaOperativa = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(payPos,
			new ParametroIdUnitaOperativa());
		popolaNuovaPendenza(payPos, pendenza, false, idUnitaOperativa);
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		esitoPos.setStato(StatoPagamentoType.ACQUISITO);
		try {
		    boolean stampaAvviso = false;
		    PendenzaCreata pendenzaCreata = client.inserisciPendenza(profiloEnte.getIdAppPSP(), payPos.getIdPosizionePsp(), pendenza,
			    stampaAvviso);
		    log.debug("pendenzaCreata {}", pendenzaCreata);
		    esitoPos.setCodiceAvviso(pendenzaCreata.getNumeroAvviso());
		    //TODO vedere come ottenere  IUV
		    esitoPos.setEsito(true);
		    esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPos,
			    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		    this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(payPos, null, null);
		    result.getEsitoPosizione().add(esitoPos);
		} catch (IOException | GeneralSecurityException | URISyntaxException e) {
		    log.error("Errore nel inserimento della pendenza: {}", payPos.getIdPosizionePsp());
		    this.handleException(e, esitoPos);
		}
	    }
	}
	return result;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException {

	// TODO Auto-generated method stub
	// INVOCA api PENDENZE
	//	PATCH
	//	​/pendenze​/{idA2A}​/{idPendenza}
	//	Aggiornamento di uno o più campi di una pendenza
	//	Inserimento o aggiornamento di una pendenza
	GovPayClient client = getClient(this.getWsAnnullamentoConfig());
	PayProfiliEntiCreditori profiloEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	ElencoPosizioniDebitorieEsitoType result = new ElencoPosizioniDebitorieEsitoType();
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		PatchOp patchOp = new PatchOp();
		patchOp.setOp(PatchOp.OpEnum.REPLACE);
		patchOp.path(PATH_AGGIORNAMENTO);
		patchOp.setValue(VALUE_AGGIORNAMENTO);
		List<PatchOp> listaPatch = new ArrayList<PatchOp>();
		listaPatch.add(patchOp);
		EsitoOperazionePosizioneDebitoriaType esito = new EsitoOperazionePosizioneDebitoriaType();
		esito.setIdPosizione(BigInteger.valueOf(payPos.getId().getCodice()));
		PayStatoPagamenti payStato = this.pagamenPayStatoPagamentiService.getStatoPosizioneDebitoria(payPos);
		esito.setStato(StatoPagamentoType.fromValue(payStato.getStato()));
		result.getEsitoPosizione().add(esito);
		try {
		    client.aggiornaPendenza(profiloEnte.getIdAppPSP(), payPos.getIdPosizionePsp(), listaPatch);
		    esito.setStato(pagatoOffline ? StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO : StatoPagamentoType.ANNULLATO);
		    esito.setEsito(true);
		    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, payPos,
			    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		} catch (IOException | GeneralSecurityException | URISyntaxException | PayException e) {
		    this.handleException(e, esito);
		}
	    }
	}
	return result;
    }

    @Override
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	// INVOCA api PENDENZE
	// GET
	// ​/pendenze​/{idA2A}​/{idPendenza}
	// Aggiornamento di uno o più campi di una pendenza
	ElencoStatoPosizioniType result = new ElencoStatoPosizioniType();
	PayProfiliEntiCreditori profiloEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	for (PayPosizioniDebitorie payPos : cmd.getPosizioni()) {
	    StatoPosizioneType retStatus = new StatoPosizioneType();
	    retStatus.setEsito(false);
	    GovPayClient client = getClient(this.getWsVerificaConfig());
	    try {
		Pendenza pendenza = client.getDettaglioPendenza(profiloEnte.getIdAppPSP(), payPos.getIdPosizionePsp());
		log.debug("pendenza {}", pendenza);
		if (StringUtils.isNotBlank(pendenza.getIuvAvviso())) {
		    payPos.setIuv(pendenza.getIuvAvviso());
		}
		if (StringUtils.isNotBlank(pendenza.getNumeroAvviso())) {
		    payPos.setCodiceAvviso(pendenza.getNumeroAvviso());
		}
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		if (pendenza.getStato().equals(StatoPendenza.ANNULLATA)) {
		    retStatus.setStato(StatoPagamentoType.ANNULLATO);
		} else if (pendenza.getStato().equals(StatoPendenza.ESEGUITA) || pendenza.getStato().equals(StatoPendenza.ESEGUITA_PARZIALE)) {
		    retStatus.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP);
		    DatiPagamentoType pagamenti = new DatiPagamentoType();
		    // Accediamo al primo elemento della lista perchè paghiamo una sola posizione 
		    CtRichiestaPagamentoTelematico ctRichiestaPagamentoTelematico = (CtRichiestaPagamentoTelematico) pendenza.getRpp().get(0)
			    .getRpt();
		    pagamenti.setIuv(ctRichiestaPagamentoTelematico.getDatiVersamento().getIdentificativoUnivocoVersamento());
		    TipiPagamento tipiPagamento = TipiPagamento
			    .valueOf(ctRichiestaPagamentoTelematico.getDatiVersamento().getTipoVersamento().name());
		    pagamenti.setModalitaPagamento(tipiPagamento.value());
		    // Accediamo al primo elemento della lista perchè paghiamo una sola posizione 
		    CtDatiSingoloVersamentoRPT ctDatiSingoloVersamentoRPT = ctRichiestaPagamentoTelematico.getDatiVersamento()
			    .getDatiSingoloVersamento().get(0);
		    pagamenti.setImportoPagato(ctDatiSingoloVersamentoRPT.getImportoSingoloVersamento());
		    pagamenti.setImportoCommissioni(ctDatiSingoloVersamentoRPT.getCommissioneCaricoPA());
		    pagamenti.setDescrizioneCausale(ctDatiSingoloVersamentoRPT.getCausaleVersamento());
		    pagamenti.setNote(ctDatiSingoloVersamentoRPT.getDatiSpecificiRiscossione());
		    pagamenti.setDataOraPagamento(ctRichiestaPagamentoTelematico.getDatiVersamento().getDataEsecuzionePagamento());
		    SoggettoDebitoreType paySoggettiDebitori = new SoggettoDebitoreType();
		    Soggetto soggettoPagatore = pendenza.getSoggettoPagatore();
		    paySoggettiDebitori.setCap(soggettoPagatore.getCap());
		    paySoggettiDebitori.setCfpi(soggettoPagatore.getIdentificativo());
		    paySoggettiDebitori.setCivico(soggettoPagatore.getCivico());
		    paySoggettiDebitori.setNome(soggettoPagatore.getAnagrafica());
		    paySoggettiDebitori.setEmail(soggettoPagatore.getEmail());
		    paySoggettiDebitori.setLocalita(soggettoPagatore.getLocalita());
		    paySoggettiDebitori.setProvincia(soggettoPagatore.getProvincia());
		    paySoggettiDebitori.setStato(soggettoPagatore.getNazione());
		    paySoggettiDebitori.setVia(soggettoPagatore.getIndirizzo());
		    pagamenti.setSoggettoPagatore(paySoggettiDebitori);
		    retStatus.setDatiPagamento(pagamenti);
		} else if (pendenza.getStato().equals(StatoPendenza.NON_ESEGUITA)) {
		    if (pendenza.getTipo().equals(TipoPendenzaTipologia.SPONTANEO)) {
			// caso OTF se non pagato
			// se lo stato del pagamento è in corso devo attendere e ritorno attivato_in_psp
			retStatus.setStato(StatoPagamentoType.ANNULLATO);
			log.debug("Cerco le sessioni di pagamento per la posizione {}", payPos.getId().getCodice());
			List<PaySessioniPagamento> sessioni = paySessioniPagamentoService
				.findSessioniPerPosizioneDebitoria(payPos.getId().getCodice());
			Set<String> idSessione = new HashSet<>();
			for (PaySessioniPagamento pSessione : sessioni) {
			    idSessione.add(pSessione.getIdSessionePagamento());
			}
			log.debug("Le sessioni di pagamento per la posizione {} sono {}", payPos.getId().getCodice(), idSessione);
			if (idSessione.size() == 1) {
			    String sessionPagamento = idSessione.iterator().next();
			    log.debug("idSessione {} di pagamento per la posizione {} chiamo il dettaglio pagamento", idSessione,
				    payPos.getId().getCodice());
			    try {
				Pagamento dettaglioPagamento = client.getDettaglioPagamento(sessionPagamento);
				log.debug("dettaglioPagamento {}", dettaglioPagamento);
				if (dettaglioPagamento == null || dettaglioPagamento.getStato().equals(StatoPagamento.IN_CORSO)) {
				    // SE NON HO INFORMAZIONI DETTAGLIOPAGAMENTO==NULL O LO STATO E' IN CORSO il client deve riprovare a verificare lo stato
				    retStatus.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
				}
			    } catch (Exception e) {
				log.error("Errore nella richiesta di dettagliopagamento per la sessione {}, {}", sessionPagamento, e);
				// in caso di errore rimando lo stato ATTIVATO_IN_PSP così il client ritenta
				retStatus.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
			    }
			}
		    } else {
			// nel caso di posizioni debitorie
			retStatus.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		    }
		} else if (pendenza.getStato().equals(StatoPendenza.SCADUTA)) {
		    retStatus.setStato(StatoPagamentoType.ANNULLAMENTO_RICHIESTO);
		}
		retStatus.setEsito(true);
		// PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos);
		// this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(payPos, null, null);
	    } catch (IOException | GeneralSecurityException | URISyntaxException | PayException e) {
		retStatus.setEsito(false);
		this.handleException(e, retStatus);
	    }
	    result.getStatoPosizioni().add(retStatus);
	}
	return result;
    }

    @Override
    public AttivaSessionePagamentoResponseType attivaSessionePagamento(PayPosizioniDebitorie payPos) throws PayException {

	AttivaSessionePagamentoResponseType sesResp = new AttivaSessionePagamentoResponseType();
	if (payPos != null && payPos.getId() != null && payPos.getId().getCodice() != null) {
	    NuovoPagamento nuovoPagamento = new NuovoPagamento();
	    List<PayPosizioniDebitorie> l = new ArrayList<PayPosizioniDebitorie>();
	    l.add(payPos);
	    popolaNuovoPagamento(l, nuovoPagamento, false);
	    GovPayClient client = getClient(this.getWsAttivaSessioneConfig());
	    try {
		PagamentoCreato pagamentoCreato = client.avvioPagamento(nuovoPagamento);
		log.debug("pagamentoCreato {}", pagamentoCreato);
		sesResp.setPayUrl(pagamentoCreato.getRedirect());
		sesResp.setIdSessione(pagamentoCreato.getId());
		sesResp.setEsito(true);
	    } catch (IOException | GeneralSecurityException | URISyntaxException | PayException e) {
		log.debug("[Attiva Sessione Pagamento] chiamata al client falita", e);
		sesResp.setEsito(false);
	    }
	}
	return sesResp;
    }

    @Override
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	List<PaySessioniPagamento> sex = new ArrayList<>();
	String[] idSessioneVals = reqParams.get("idPagamento");
	String[] idPayPosDebVals = reqParams.get("idPayPos");
	String[] esitoVal = reqParams.get("stato");
	log.debug("idSessioneVals {}", idSessioneVals);
	log.debug("idPayPosDebVals {}", idSessioneVals);
	log.debug("esitoVal {}", idSessioneVals);
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
	if (sex.isEmpty()) {
	    for (String idP : idPayPosDebVals) {
		List<PaySessioniPagamento> patt = this.paySessioniPagamentoService.findSessioniAttivePerPosizioneDebitoria(Integer.parseInt(idP));
		sex.addAll(patt);
	    }
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
    public boolean supportaPagamentoOnTheFly() {

	return true;
    }

    @Override
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(PosizioniDebitorieCommand cmd) throws PayException {

	AttivaPagamentoOnTheFlyResponseType result = new AttivaPagamentoOnTheFlyResponseType();
	EsitoOperazionePosizioneDebitoriaType esitoKO = new EsitoOperazionePosizioneDebitoriaType();
	AttivaSessionePagamentoResponseType attivaSessioneOTF = new AttivaSessionePagamentoResponseType();
	List<EsitoOperazionePosizioneDebitoriaType> esiti = new ArrayList<>();
	result.setSessionePagamento(attivaSessioneOTF);
	List<PayRegistrazioniContabili> payRegistrazioniContabilis = cmd.getRegistrazioniPosizioni();
	List<PayPosizioniDebitorie> payPosizioniDebitorie = new ArrayList<PayPosizioniDebitorie>();
	for (PayRegistrazioniContabili prc : payRegistrazioniContabilis) {
	    payPosizioniDebitorie.addAll(prc.getPosizioniDebitorie());
	}
	NuovoPagamento nuovoPagamento = new NuovoPagamento();
	List<PayPosizioniDebitorie> posDeb = new ArrayList<PayPosizioniDebitorie>();
	try {
	    popolaNuovoPagamento(payPosizioniDebitorie, nuovoPagamento, true);
	    for (PayPosizioniDebitorie pd : payPosizioniDebitorie) {
		this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(pd, null, null);
	    }
	    GovPayClient client = getClient(this.getWsAttivaSessioneConfig());
	    PagamentoCreato pagamentoCreato = client.avvioPagamento(nuovoPagamento);
	    log.debug("pagamentoCreato {}", pagamentoCreato);
	    attivaSessioneOTF.setPayUrl(pagamentoCreato.getRedirect());
	    attivaSessioneOTF.setIdSessione(pagamentoCreato.getId());
	    attivaSessioneOTF.setEsito(true);
	    for (PayPosizioniDebitorie pd : payPosizioniDebitorie) {
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		esitoPos.setEsito(true);
		esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		esitoPos.setMessaggio("Posizione OTF creata con successo");
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, pd,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pd.getId().getCodice()));
		esiti.add(esitoPos);
	    }
	} catch (Exception e) {
	    attivaSessioneOTF.setEsito(false);
	    attivaSessioneOTF.setDescEsito("Errore nel creazione della posizione OTF");
	    for (PayPosizioniDebitorie pd : payPosizioniDebitorie) {
		esitoKO.setEsito(false);
		this.handleException(e, esitoKO);
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoKO, pd,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pd.getId().getCodice()));
		esiti.add(esitoKO);
	    }
	}
	result.getPosizioneInserita().addAll(esiti);
	return result;
    }

    @Override
    public ElencoStatoPosizioniType rendicontazionePagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	// TODO Auto-generated method stub
	return super.rendicontazionePagamenti(cmd);
    }

    /**
     * 
     * @param payPosDeb
     * @param pendenza
     * @param isPagamento
     *            se true allora la modellazione della nuova pendenza è quella relativa ai servizi pagamenti e non
     *            pendenze cambia che vanno impostate IdA2A e IdPendenza nel caso di pagamenti
     * @throws PayConfigurationException
     */
    private void popolaNuovaPendenza(PayPosizioniDebitorie payPosDeb, NuovaPendenza pendenza, boolean isPagamento, String idUnitaOperativa)
	    throws PayConfigurationException {

	PayProfiliEntiCreditori profiloEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	String codEntrata = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPosDeb);
	// pendenza.setIdTipoPendenza(payPosDeb.getRegistrazioneContabile().getCausaleRegistrazione().getCodiceVersamento());
	pendenza.setIdDominio(profiloEnte.getCfCodiceProfiloPSP());
	if (StringUtils.isNotBlank(idUnitaOperativa)) {
	    pendenza.setIdUnitaOperativa(idUnitaOperativa);
	}
	pendenza.setCausale(payPosDeb.getDescrizioneCausale());
	String idPosizionePsp = this.generaIdPosizioneDebitoria(payPosDeb);
	payPosDeb.setIdPosizionePsp(idPosizionePsp);
	payPosDeb.setDataInvioAPsp(Calendar.getInstance().getTime());
	if (isPagamento) {
	    pendenza.setIdA2A(profiloEnte.getIdAppPSP());
	    pendenza.setIdPendenza(idPosizionePsp);
	}
	Soggetto soggettoPagatore = new Soggetto();
	if (payPosDeb.getSoggettoDebitore().getCfPi().length() == 16) {
	    soggettoPagatore.setTipo(TipoSoggetto.F);
	} else {
	    soggettoPagatore.setTipo(TipoSoggetto.G);
	}
	PaySoggettiDebitori paySoggettiDebitori = payPosDeb.getSoggettoDebitore();
	StringBuilder anagrafica = new StringBuilder(payPosDeb.getSoggettoDebitore().getNome());
	if (StringUtils.isNotEmpty(payPosDeb.getSoggettoDebitore().getCognome())) {
	    anagrafica.append(" ").append(payPosDeb.getSoggettoDebitore().getCognome());
	}
	soggettoPagatore.setAnagrafica(anagrafica.toString());
	soggettoPagatore.setIdentificativo(paySoggettiDebitori.getCfPi());
	soggettoPagatore.setIndirizzo(paySoggettiDebitori.getVia());
	soggettoPagatore.setCap(paySoggettiDebitori.getCap());
	soggettoPagatore.setCivico(paySoggettiDebitori.getCivico());
	soggettoPagatore.setEmail(paySoggettiDebitori.getEmail());
	soggettoPagatore.setLocalita(paySoggettiDebitori.getLocalita());
	soggettoPagatore.setNazione(paySoggettiDebitori.getStato());
	soggettoPagatore.setProvincia(paySoggettiDebitori.getProvincia());
	pendenza.setSoggettoPagatore(soggettoPagatore);
	BigDecimal importo = BigDecimal.ZERO;
	List<NuovaVocePendenza> voci = new ArrayList<>();
	for (PayDettaglioImporti payImporto : payPosDeb.getDettagliImporto()) {
	    importo = importo.add(payImporto.getImporto());
	    NuovaVocePendenza nuovaVocePendenza = new NuovaVocePendenza();
	    nuovaVocePendenza.setCodEntrata(codEntrata);
	    nuovaVocePendenza.setIdVocePendenza(PkId.toStringId(payImporto.getId()));
	    BigDecimal newImporto = payImporto.getImporto().setScale(2, RoundingMode.HALF_EVEN);
	    nuovaVocePendenza.setImporto(newImporto);
	    nuovaVocePendenza.setDescrizione(payImporto.getDescCausale());
	    voci.add(nuovaVocePendenza);
	}
	pendenza.setVoci(voci);
	importo = importo.setScale(2, RoundingMode.HALF_EVEN);
	pendenza.setImporto(importo);
	// pendenza.setTassonomia(payPosDeb.getDescrizioneCausale());
	if (!isPagamento && payPosDeb.getDataScadenza() != null) {
	    pendenza.setDataScadenza(new SimpleDateFormat(DATE_PATTERN).format(payPosDeb.getDataScadenza()));
	}
    }

    private void popolaNuovoPagamento(List<PayPosizioniDebitorie> payPosDebs, NuovoPagamento nuovoPagamento, boolean isOTF) throws PayException {

	PayProfiliEntiCreditori profEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	nuovoPagamento.setUrlRitorno(getUrlBack(profEnte, payPosDebs));
	Soggetto soggettoVersante = new Soggetto();
	List<Object> lisRiferimento = new ArrayList<>();
	for (PayPosizioniDebitorie payPosDeb : payPosDebs) {
	    if (payPosDeb.getSoggettoDebitore().getCfPi().length() == 16) {
		soggettoVersante.setTipo(TipoSoggetto.F);
	    } else {
		soggettoVersante.setTipo(TipoSoggetto.G);
	    }
	    PaySoggettiDebitori paySoggettiDebitori = payPosDeb.getSoggettoDebitore();
	    StringBuilder anagrafica = new StringBuilder(payPosDeb.getSoggettoDebitore().getNome());
	    if (StringUtils.isNotEmpty(payPosDeb.getSoggettoDebitore().getCognome())) {
		anagrafica.append(" ").append(payPosDeb.getSoggettoDebitore().getCognome());
	    }
	    soggettoVersante.setAnagrafica(anagrafica.toString());
	    soggettoVersante.setIdentificativo(paySoggettiDebitori.getCfPi());
	    soggettoVersante.setIndirizzo(paySoggettiDebitori.getVia());
	    soggettoVersante.setCap(paySoggettiDebitori.getCap());
	    soggettoVersante.setCivico(paySoggettiDebitori.getCivico());
	    soggettoVersante.setEmail(paySoggettiDebitori.getEmail());
	    soggettoVersante.setLocalita(paySoggettiDebitori.getLocalita());
	    soggettoVersante.setNazione(paySoggettiDebitori.getStato());
	    soggettoVersante.setProvincia(paySoggettiDebitori.getProvincia());
	    if (isOTF) {
		NuovaPendenza nuovaPendenza = new NuovaPendenza();
		String idUnitaOperativa = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(payPosDeb,
			new ParametroIdUnitaOperativa());
		popolaNuovaPendenza(payPosDeb, nuovaPendenza, true, idUnitaOperativa);
		lisRiferimento.add(nuovaPendenza);
	    } else {
		RiferimentoAvviso riferimentoAvviso = new RiferimentoAvviso();
		riferimentoAvviso.setIdDominio(profEnte.getCfEnteQrcodePagopa());
		riferimentoAvviso.setNumeroAvviso(payPosDeb.getCodiceAvviso());
		lisRiferimento.add(riferimentoAvviso);
	    }
	}
	nuovoPagamento.setSoggettoVersante(soggettoVersante);
	nuovoPagamento.setPendenze(lisRiferimento);
    }

    private String getUrlBack(PayProfiliEntiCreditori profEnte, List<PayPosizioniDebitorie> payPosDebs) {

	String qsAggiuntiva = "";
	if (payPosDebs != null && !payPosDebs.isEmpty()) {
	    for (PayPosizioniDebitorie ppd : payPosDebs) {
		qsAggiuntiva += "&idPayPos=" + ppd.getId().getCodice();
	    }
	}
	return profEnte.getUrlEsitoPagamento() + qsAggiuntiva;
    }

    @Override
    public ElencoDocumentiEsitoType inviaAvvisiPagamento(PosizioniDebitorieCommand cmd) throws PayException {

	log.debug("inviaAvvisiPagamento");
	PayConnectorWsEndpoint wsAvviso = this.getWsAvvisoConfig();
	PayProfiliEntiCreditori ente = PayConfigurationHelper.getProfiloEnteCreditore();
	if (wsAvviso == null) {
	    log.debug("inviaAvvisiPagamento endpoint getWsAvvisoConfig non configurato");
	    throw new PayConfigurationException(
		    "l'endpoint per il servizio di generazione degli avvisi non è configurato per il connettore " + this.getConnectorName());
	}
	ElencoDocumentiEsitoType retEsiti = new ElencoDocumentiEsitoType();
	String msg = null;
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
		    try {
			GovPayClient client = getClient(wsAvviso);
			log.debug("inviaAvvisiPagamento invoco il metodo getAvviso {}-{}-{}", ente.getCfCodiceProfiloPSP(), pos.getCodiceAvviso(),
				pos.getId().getCodice());
			InputStream avviso = client.getAvviso(ente.getCfCodiceProfiloPSP(), pos.getCodiceAvviso());
			log.debug("inviaAvvisiPagamento chiamata effettuata con successo per la posizione debitoria {}", pos.getId().getCodice());
			esitoDoc.setEsito(true);
			esitoDoc.setNomeDocumento("AVVISO_" + pos.getId().getCodice() + ".pdf");
			esitoDoc.setDocumento(new DataHandler(new InputStreamDataSource(avviso)));
		    } catch (IOException | GeneralSecurityException | URISyntaxException | PayException e) {
			this.handleException(e, esitoDoc);
			msg = "impossibile scaricare l'avviso PDF a causa dell'errore: " + e.toString();
			log.error("inviaAvvisiPagamento - {}", msg, e);
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

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

	List<PosizioneDebitoriaType> rate = registrazioneContabile.getRate().getRata();
	for (PosizioneDebitoriaType rata : rate) {
	    if (rata.getDataScadenza() == null) {
		throw new ValidazionePosizioniDebitorieException(
			"La data di scadenza è obbligatoria. Riferimento posizione debitoria: " + registrazioneContabile.getDescrizione());
	    }
	    for (ImportoPagamentoType importoPagamento : rata.getImporto().getComponenteImporto()) {
		if (importoPagamento.getDescrizioneCausale() == null) {
		    throw new ValidazionePosizioniDebitorieException(
			    "La causale è obbligatoria.Riferimento posizione debitoria: " + registrazioneContabile.getDescrizione());
		}
	    }
	}
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

    @Override
    public IUVHelper generaIUV(PayPosizioniDebitorie pos, String identificativoCausalePerCalcolo) {

	return null;
    }
}
