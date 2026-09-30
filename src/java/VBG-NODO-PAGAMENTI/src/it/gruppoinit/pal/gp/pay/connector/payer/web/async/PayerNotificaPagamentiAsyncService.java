package it.gruppoinit.pal.gp.pay.connector.payer.web.async;

import static it.gruppoinit.pal.gp.pay.connector.payer.PayerConnector.statoPagamentoType.CANCELLATO;

import static it.gruppoinit.pal.gp.pay.connector.payer.PayerConnector.statoPagamentoType.FALLITO;
import static it.gruppoinit.pal.gp.pay.connector.payer.PayerConnector.statoPagamentoType.IN_ATTESA;
import static it.gruppoinit.pal.gp.pay.connector.payer.PayerConnector.statoPagamentoType.IN_CORSO;
import static it.gruppoinit.pal.gp.pay.connector.payer.PayerConnector.statoPagamentoType.PAGATO;
import static it.gruppoinit.pal.gp.pay.connector.payer.PayerConnector.statoPagamentoType.PAGATO_ALTRO_SISTEMA;
import static it.gruppoinit.pal.gp.pay.connector.payer.PayerConnector.statoPagamentoType.SCADUTO;
import static it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento.ANNULLATO;
import static it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento.ATTIVATO_IN_PSP;
import static it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento.NOTIFICATO_DA_PSP;
import static it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento.PAGATO_OFFLINE_ANNULLATO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.payer.PayerConnector;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.AnagraficaPagatore;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.EsitoListaPagamenti;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.EsitoRecuperaPagamento;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.Pagamento;
import it.gruppoinit.pal.gp.pay.connector.payer.web.client.ApiClient;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfig;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorService;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayRegistrazioniCausaliService;
import it.gruppoinit.pal.gp.pay.service.PayRegistrazioniContabiliService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.async.AsyncProcessException;
import it.gruppoinit.pal.gp.pay.service.async.BaseAsync;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RTHelper;
import it.gruppoinit.pal.gp.pay.service.impl.PayPagamentiServiceImpl;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.DettaglioImportoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ImportoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.InserisciPosizioniDebitorieType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RateizzazioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;

public class PayerNotificaPagamentiAsyncService extends BaseAsync {

    private static final Logger log = LoggerFactory.getLogger(PayerNotificaPagamentiAsyncService.class);
    private static final int MAX_RISULTATI_PAGAMENTI = 100;
    private static final Set<String> ELABORAZIONI_ATTIVE = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    private static final Map<PayerConnector.statoPagamentoType, PayStatoPagamenti.StatiPagamento> MAPPATURA_STATI_PAYER = new EnumMap<>(
	    PayerConnector.statoPagamentoType.class);
    private static String causalePagamentiFuoriVBG;
    private static ApiClient apiClient;
    static {
	MAPPATURA_STATI_PAYER.put(IN_ATTESA, ATTIVATO_IN_PSP);
	MAPPATURA_STATI_PAYER.put(IN_CORSO, ATTIVATO_IN_PSP);
	MAPPATURA_STATI_PAYER.put(PAGATO, NOTIFICATO_DA_PSP);
	MAPPATURA_STATI_PAYER.put(PAGATO_ALTRO_SISTEMA, PAGATO_OFFLINE_ANNULLATO);
	MAPPATURA_STATI_PAYER.put(SCADUTO, ATTIVATO_IN_PSP);
	MAPPATURA_STATI_PAYER.put(FALLITO, ATTIVATO_IN_PSP);
	MAPPATURA_STATI_PAYER.put(CANCELLATO, ANNULLATO);
    }
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    private PayConnectorConfigValuesService configValuesService;
    private PayConnectorService payConnectorService;
    private PayRegistrazioniCausaliService payRegistrazioniCausaliService;
    private PayStatoPagamentiService payStatoPagamentiService;
    private PayPagamentiService payPagamentiService;
    private ConfigurazionePagamentiService configurazionePagamentiService;
    private PayRegistrazioniContabiliService payRegistrazioniContabiliService;
    private String cfEnteCreditore;
    private String cfDebitore;

    public PayerNotificaPagamentiAsyncService(ApplicationContext context) {

	this.applicationContext = context;
    }

    public PayerNotificaPagamentiAsyncService(ApplicationContext context, String cfEnteCreditore, String cfDebitore) {

	this.applicationContext = context;
	this.payPosizioniDebitorieService = getBeanOfType(PayPosizioniDebitorieService.class);
	this.configValuesService = getBeanOfType(PayConnectorConfigValuesService.class);
	this.payConnectorService = getBeanOfType(PayConnectorService.class);
	this.payRegistrazioniCausaliService = getBeanOfType(PayRegistrazioniCausaliService.class);
	this.payStatoPagamentiService = getBeanOfType(PayStatoPagamentiService.class);
	this.payPagamentiService = getBeanOfType(PayPagamentiService.class);
	this.configurazionePagamentiService = getBeanOfType(ConfigurazionePagamentiService.class);
	this.payRegistrazioniContabiliService = getBeanOfType(PayRegistrazioniContabiliService.class);
	this.cfEnteCreditore = cfEnteCreditore;
	this.cfDebitore = cfDebitore;
    }

    @Override
    public String getIdOperazione() {

	return "PayerNotificaPagamentiAsyncService[" + cfEnteCreditore + "-" + UUID.randomUUID().toString() + "]";
    }

    @Override
    public void process() throws AsyncProcessException {

	boolean isconclusa = true;
	try {
	    if (ELABORAZIONI_ATTIVE.contains(cfEnteCreditore + "_" + cfDebitore)) {
		log.info("Sincronizzazione debiti per cf_ente_creditore: {} e cf_debitore: {} gia in corso...", cfEnteCreditore, cfDebitore); //su questo progetto preferisco evitare caratteri accentati e caratteri strani
		isconclusa = false;
		return;
	    } else {
		ELABORAZIONI_ATTIVE.add(cfEnteCreditore + "_" + cfDebitore);
	    }
	    PayProfiliEntiCreditori configurazione = configurazionePagamentiService.configuraRequestPerEnteCreditore(cfEnteCreditore);
	    IPayConnector conn = this.payConnectorService.getPayConnectorInstance();
	    if (!conn.supportaSincronizzaDebitiPerSoggetto()) {
		log.debug("sincronizzazione non supportata per cfEnteCreditore {}_", cfEnteCreditore);
		return;
	    }
	    log.info("avvio sincronizzazione per {}_{} ", cfEnteCreditore, cfDebitore);
	    setupApiClient(configValuesService);
	    setupCausalePagamentiFuoriVBG(configValuesService);
	    List<Pagamento> pagamenti = recuperaPagamentiDaLepida(cfDebitore, configurazione.getPayConnector().getCodice());
	    if (pagamenti == null || pagamenti.isEmpty()) {
		log.warn("Non sono presenti pagamenti per il soggetto {}", cfDebitore);
		return;
	    }
	    List<Pagamento> nuoviPagamenti = new ArrayList<>();
	    List<Pagamento> pagamentiDaVerificare = new ArrayList<>();
	    List<PayPosizioniDebitorie> posizioniDaVerificare = new ArrayList<>();
	    for (Pagamento p : pagamenti) {
		PayPosizioniDebitorie posizione = this.payPosizioniDebitorieService.findByIdPosizionePSPOrIUVOrCodiceAvviso(p.getNumeroDocumento(),
			p.getIuv(), p.getCodiceAvviso());
		if (posizione != null) {
		    //posizione trovata, la ignoro e quindi non la inserisco perche gia presente
		    log.debug("Posizione trovata! -> Va verificata. IUV {}", p.getIuv());
		    pagamentiDaVerificare.add(p);
		    posizioniDaVerificare.add(posizione);
		} else {
		    // posizione non trovata, va inserita a DB
		    log.debug("Posizione NON trovata! -> Da inserire a DB. IUV {}", p.getIuv());
		    nuoviPagamenti.add(p);
		}
	    }
	    verificaEAggiornaPagamentiDB(pagamentiDaVerificare, posizioniDaVerificare, configurazione.getPayConnector().getCodice());
	    registraPosizioniDebitorie(nuoviPagamenti, cfEnteCreditore, configurazione.getPayConnector().getCodice());
	    log.info("sincronizzazione conclusa per {}_{} ", cfEnteCreditore, cfDebitore);
	} catch (Exception e) {
	    e.printStackTrace();
	    log.error("Errore durante il processo per operationId {}", getIdOperazione(), e);
	} finally {
	    if (isconclusa) {
		ELABORAZIONI_ATTIVE.remove(cfEnteCreditore + "_" + cfDebitore);
	    }
	}
    }

    private static void setupApiClient(PayConnectorConfigValuesService configValuesService) {

	if (apiClient != null)
	    return;
	String chiave = configValuesService.getValoreParametroConfigurazione(PayConnectorConfigParams.ConfigParamNames.PAYER_CLIENT_KEY);
	String secret = configValuesService.getValoreParametroConfigurazione(PayConnectorConfigParams.ConfigParamNames.PAYER_CLIENT_SECRET);
	apiClient = new ApiClient(secret, chiave);
    }

    private static void setupCausalePagamentiFuoriVBG(PayConnectorConfigValuesService configValuesService) {

	if (causalePagamentiFuoriVBG != null)
	    return;
	causalePagamentiFuoriVBG = configValuesService
		.getValoreParametroConfigurazione(PayConnectorConfigParams.ConfigParamNames.PAGAMENTI_FUORI_VBG);
    }

    private List<Pagamento> recuperaPagamentiDaLepida(String cfDebitore, String codiceconnettore) throws Exception {

	PayConnectorConfig endpointT = payConnectorService.findById(codiceconnettore);
	if (endpointT == null) {
	    log.warn("nessuna configurazione trovata con codice connnettore {}", codiceconnettore);
	    throw new RuntimeException("Nessuna configurazione trovata per l endpooint");
	}
	if (endpointT.getWsSincronizzaDebitiPerSoggetto() == null
		|| StringUtils.isBlank(endpointT.getWsSincronizzaDebitiPerSoggetto().getEndpointUrl())) {
	    log.warn("nessun endpoint trovato con codice connnettore {}", codiceconnettore);
	    throw new RuntimeException("Nessun endpoint trovato");
	}
	String baseurl = endpointT.getWsSincronizzaDebitiPerSoggetto().getEndpointUrl();
	EsitoListaPagamenti esitoCall;
	List<Pagamento> pagamenti;
	int count = 1;
	log.info("Chiamata/e verso Lepida per codice_pagatore: {} in corso...", cfDebitore);
	esitoCall = apiClient.call(baseurl + "/v2/pagamenti?codice_pagatore=" + cfDebitore + "&limite=" + MAX_RISULTATI_PAGAMENTI, "GET",
		MediaType.APPLICATION_JSON_TYPE, null, EsitoListaPagamenti.class);
	pagamenti = esitoCall.getPagamento();
	while (esitoCall.getPaginazione().getNumPagina() < esitoCall.getPaginazione().getTotPagine()) {
	    count++;
	    esitoCall = apiClient.call(
		    baseurl + "/v2/pagamenti?codice_pagatore=" + cfDebitore + "&limite=" + MAX_RISULTATI_PAGAMENTI + "&pagina=" + count, "GET",
		    MediaType.APPLICATION_JSON_TYPE, null, EsitoListaPagamenti.class);
	    pagamenti.addAll(esitoCall.getPagamento());
	}
	log.info("Chiamata/e verso Lepida per codice_pagatore: {} TERMINATA/E", cfDebitore);
	return pagamenti;
    }

    private EsitoRecuperaPagamento recuperaRTPagamentoDaLepida(String iuv, String codiceconnettore) throws Exception {

	PayConnectorConfig endpointT = payConnectorService.findById(codiceconnettore);
	if (endpointT == null) {
	    log.warn("nessuna configurazione trovata con codice connnettore {}", codiceconnettore);
	    throw new RuntimeException("Nessuna configurazione trovata per l endpooint");
	}
	if (endpointT.getWsSincronizzaDebitiPerSoggetto() == null
		|| StringUtils.isBlank(endpointT.getWsSincronizzaDebitiPerSoggetto().getEndpointUrl())) {
	    log.warn("nessuna endpoint trovato con codice connnettore {}", codiceconnettore);
	    throw new RuntimeException("Nessun endpoint trovato");
	}
	String baseurl = endpointT.getWsSincronizzaDebitiPerSoggetto().getEndpointUrl();
	EsitoRecuperaPagamento esitoCall;
	log.info("Chiamata verso Lepida per iuv: {} in corso...", iuv);
	//DEBUG: scommentare
	esitoCall = apiClient.call(baseurl + "/v2/pagamenti/pagamento/" + iuv, "GET", MediaType.APPLICATION_JSON_TYPE, null,
		EsitoRecuperaPagamento.class);
	//		esitoCall = apiClient.call("localhost:8082/api-segnalazioni-proxy/mockApiCall",
	//			"GET", MediaType.APPLICATION_JSON_TYPE, null, EsitoRecuperaPagamento.class)
	log.info("Chiamata verso Lepida per iuv: {} TERMINATA", iuv);
	return esitoCall;
    }

    private void verificaEAggiornaPagamentiDB(List<Pagamento> pagamentiDaVerificare, List<PayPosizioniDebitorie> posizioniDaVerificare,
	    String codiceconnettore) throws Exception {

	if (pagamentiDaVerificare.size() != posizioniDaVerificare.size()) {
	    log.error("INCONGRUENZA tra il numero di pagamenti trovati da verificare e numero di posizioni presenti a DB!!! ");
	}
	for (int i = 0; i < pagamentiDaVerificare.size(); i++) {
	    Pagamento pagamento = pagamentiDaVerificare.get(i);
	    PayPosizioniDebitorie posizioneDebitoria = posizioniDaVerificare.get(i);
	    String mappaturaClient = payRegistrazioniCausaliService.findMappaturaClientByPosizioneDebitoria(posizioneDebitoria);
	    if (!mappaturaClient.equals(causalePagamentiFuoriVBG)) {
		// Posizione debitoria gestita da VBG -> da ignorare
		continue;
	    }
	    // Verifica se lo stato presente nel Pagamento Lepida e presente tra quelli censiti a codice
	    PayerConnector.statoPagamentoType statoPagamentoEnum = PayerConnector.statoPagamentoType.fromString(pagamento.getStato());
	    if (statoPagamentoEnum == null) {
		log.warn("Valore sconosciuto, non censito o non valido: {}", pagamento.getStato());
		log.warn("Pagamento IUV: {} ignorato.", pagamento.getIuv());
		continue;
	    }
	    StatiPagamento statoPagamentoDB = payStatoPagamentiService.findStatoByPosizioneDebitoria(posizioneDebitoria);
	    //DB stato="NOTIFICATO_DA_PSP" = stato="PAGATO" LEPIDA
	    if (statoPagamentoDB == NOTIFICATO_DA_PSP) {
		// SE lo stato a DB = Pagato -> ok, ignora, non fare nulla
		log.debug("-------------------- Il pagamento con IUV {} a DB risulta gia PAGATO! -> lo salto", pagamento.getIuv());
		continue;
	    }
	    if (isStatiCorrispondenti(statoPagamentoEnum, statoPagamentoDB)) {
		// SE lo stato del pagamento = stato posizione da DB	-> ok, ignora, non fare nulla
		log.debug("-------------------- Il pagamento con IUV {} risulta UGUALE a quello a DB! -> lo salto", pagamento.getIuv());
		continue;
	    }
	    // SE lo stato del pagamento != stato posizione da DB
	    log.debug("Sto aggiornando lo status sulla posizione debitoria con IUV {}", pagamento.getIuv());
	    if (statoPagamentoEnum != PAGATO) {
		log.debug("Stato posizione con IUV {} diverso da pagato", pagamento.getIuv());
		PayStatoPagamenti statoPos = new PayStatoPagamenti();
		statoPos.setDataEvento(pagamento.getTimeStampInizio() != null
			? DatatypeFactory.newInstance().newXMLGregorianCalendar(getCorrectedTimestamp(pagamento.getTimeStampInizio()))
				.toGregorianCalendar().getTime()
			: new Date());
		statoPos.setPosizioneDebitoria(posizioneDebitoria);
		statoPos.setStato(String.valueOf(MAPPATURA_STATI_PAYER.get(statoPagamentoEnum)));
		statoPos.setDescStato(MAPPATURA_STATI_PAYER.get(statoPagamentoEnum).description());
		payStatoPagamentiService.insert(statoPos);
	    } else {
		log.debug("Stato posizione con IUV {} uguale a pagato", pagamento.getIuv());
		;
		PayPagamenti datiPag = this.payPagamentiService.getPagamentoByPosizioneDebitoria(posizioneDebitoria);
		if (datiPag != null) {
		    //dati di pagamento gia presenti
		    StringBuilder sb = new StringBuilder("dati di pagamento gia presenti per la posizione ")
			    .append(PkId.toStringId(posizioneDebitoria.getId())).append(" la notifica di pagamento e stata ignorata.");
		    log.warn("{}", sb);
		} else {
		    //SE da Lepida = Pagato
		    //Se pagato allora i dati di pagamento li riprendo da altra API
		    //per le posizioni PAGATE va invocato il dettaglio del pagamento "/v2/pagamenti/pagamento/{IUV}" che contiene l'elemento RT (esempio gia gestito in verifica stato)
		    EsitoRecuperaPagamento esito;
		    try {
			esito = recuperaRTPagamentoDaLepida(pagamento.getIuv(), codiceconnettore);
		    } catch (Exception e) {
			log.warn("Errore nel recupero delle informazioni dei pagamenti per la posizione debitoria {}", posizioneDebitoria.getId(), e);
			continue;
		    }
		    /*
		     * per come l ho intepretata, da questo esito dovrebbe interessarci solo l rt
		     */
		    if (!StringUtils.isBlank(esito.getRt())) {
			try {
			    DatiPagamentoType datiPagXml = RTHelper.popolaDatiPagamentoDaReceiptORicevutaTelematica(esito.getRt(), null);
			    PayPagamenti payPagamenti = PayPagamentiServiceImpl.populateDomainObject(datiPagXml, posizioneDebitoria,
				    StatoPagamentoType.fromValue(String.valueOf(MAPPATURA_STATI_PAYER.get(statoPagamentoEnum))));
			    this.payPagamentiService.registraAvvenutoPagamento(payPagamenti, posizioneDebitoria, esito.getRt());
			} catch (Exception e) {
			    log.warn("Errore durante la registrazione dell avvenuto pagamento, pd {}", posizioneDebitoria.getId(), e);
			}
		    } else {
			log.warn("Dati del pagamento mancati, manca la ricevuta, pd {} ", posizioneDebitoria.getId());
		    }
		}
	    }
	}
    }

    private boolean isStatiCorrispondenti(PayerConnector.statoPagamentoType statoLepida, StatiPagamento statoDB) {

	//to do: eventualmente da mettere direttamente nel codice
	return statoDB == MAPPATURA_STATI_PAYER.get(statoLepida);
    }

    private void registraPosizioniDebitorie(List<Pagamento> nuoviPagamenti, String cfEnteCreditore, String codiceconnettore) throws PayException {
	// to do: registra nuove posizioni

	//non registra la posizione su pagopa/PAYER che e gia registrata. salva la posizione sulla base dati
	// crea gli oggetti necessari che servono e poi utilizza le funzionalita gia presenti per creare a DB le componenti + verifiche
	AnagraficaPagatore anagraficaPagatore;
	SoggettoDebitoreType soggettoDebitore;
	ImportoPagamentoType importoPagamento;
	DettaglioImportoType dettaglioImporto;
	PosizioneDebitoriaType posizioneDebitoria;
	RateizzazioneType rateizzazioneType;
	RegistrazioneContabileType registrazioneContabile;
	DatatypeFactory datatypeFactory = null;
	try {
	    datatypeFactory = DatatypeFactory.newInstance();
	} catch (DatatypeConfigurationException e) {
	    log.error("Errore nella creazione di DatatypeFactory per la creazione dei XMLGregorianCalendar: ", e);
	}
	for (Pagamento p : nuoviPagamenti) {
	    if (p.getStato().equals(CANCELLATO.getValue())) {
		log.info("Il pagamento con IUV: {} non verra salvato a DB VBG perche il suo stato e: {}", p.getIuv(), p.getStato());
		continue;
	    }
	    // L'anagrafica viene sempre rigenerata perche a DB i dati dei CF sono "storicizzati", quindi non vengono sovrascritti eventuali dati gia corretti
	    anagraficaPagatore = p.getAnagraficaPagatore();
	    soggettoDebitore = new SoggettoDebitoreType();
	    soggettoDebitore.setNome(anagraficaPagatore.getNomeCognomePagatore()); //questa va un attimo chiarita
	    soggettoDebitore.setCognome(null); //anche questa
	    soggettoDebitore.setCfpi(anagraficaPagatore.getCodicePagatore());
	    soggettoDebitore.setCap(anagraficaPagatore.getCapPagatore());
	    soggettoDebitore.setVia(anagraficaPagatore.getIndirizzoPagatore());
	    soggettoDebitore.setCivico(anagraficaPagatore.getNumeroCivicoPagatore());
	    soggettoDebitore.setLocalita(anagraficaPagatore.getLocalitaPagatore());
	    soggettoDebitore.setProvincia(anagraficaPagatore.getProvinciaPagatore());
	    soggettoDebitore.setStato(anagraficaPagatore.getStatoPagatore());
	    soggettoDebitore.setEmail(anagraficaPagatore.getEmailPagatore());
	    importoPagamento = new ImportoPagamentoType();
	    importoPagamento.setImporto(new BigDecimal(p.getImportoVersamento()));
	    importoPagamento.setDescrizioneCausale(p.getCausaleVersamento());
	    importoPagamento.setDatiRiscossione(null);
	    importoPagamento.setAnnoAccertamento(null);
	    importoPagamento.setNumeroAccertamento(null);
	    importoPagamento.setNumeroSottoAccertamento(null);
	    importoPagamento.setCodiceMappatura(causalePagamentiFuoriVBG);
	    dettaglioImporto = new DettaglioImportoType();
	    dettaglioImporto.getComponenteImporto().add(importoPagamento);
	    posizioneDebitoria = new PosizioneDebitoriaType();
	    posizioneDebitoria.setDescrizione(p.getCausaleVersamento());
	    posizioneDebitoria.setNumeroRata(BigInteger.valueOf(1)); // fisso ad 1
	    posizioneDebitoria.setImporto(dettaglioImporto);
	    // Conversione della stringa in XMLGregorianCalendar
	    posizioneDebitoria.setDataScadenza((datatypeFactory != null && p.getTimeStampScadenza() != null)
		    ? datatypeFactory.newXMLGregorianCalendar(getCorrectedTimestamp(p.getTimeStampScadenza()))
		    : null);
	    rateizzazioneType = new RateizzazioneType();
	    rateizzazioneType.getRata().add(posizioneDebitoria);
	    registrazioneContabile = new RegistrazioneContabileType();
	    registrazioneContabile
		    .setData(datatypeFactory != null ? datatypeFactory.newXMLGregorianCalendar(getCorrectedTimestamp(p.getTimeStampInizio())) : null);
	    registrazioneContabile.setDescrizione(p.getCausaleVersamento());
	    registrazioneContabile.setImporto(new BigDecimal(p.getImportoVersamento()));
	    registrazioneContabile.setAnno(
		    datatypeFactory != null ? datatypeFactory.newXMLGregorianCalendar(getCorrectedTimestamp(p.getTimeStampInizio())).getYear()
			    : null);
	    registrazioneContabile.setNote(null);
	    registrazioneContabile.setSoggettoDebitore(soggettoDebitore);
	    registrazioneContabile.setRate(rateizzazioneType);
	    InserisciPosizioniDebitorieType parameters = new InserisciPosizioniDebitorieType();
	    parameters.setCfEnteCreditore(cfEnteCreditore);
	    this.configurazionePagamentiService.configuraRequestPerEnteCreditore(parameters);
	    PayConfigurationHelper payCfg = this.configurazionePagamentiService.getConfigurazioneEnteCorrente();
	    log.debug("Sto per inserire iuv: {}, codiceAvviso: {}, numeroDocumento: {}", p.getIuv(), p.getCodiceAvviso(), p.getNumeroDocumento());
	    /*
	     * Facciamo cosi per ora: se lo status lato loro non e PAGATO allora registriamo la pd con lo status mappato,
	     * se invece e PAGATO allora lo metto come ATTIVATO_IN_PSP, e se ho la ricevuta, inserisco anche lo stato PAGATO piu tutte le info sul pagamento
	     */
	    PayerConnector.statoPagamentoType statoPagamentoEnum = PayerConnector.statoPagamentoType.fromString(p.getStato());
	    PayRegistrazioniContabili parRegistrazioneContabile = this.payRegistrazioniContabiliService.creaRegistrazioneContabileSingolaPd(
		    registrazioneContabile, payCfg, false, p.getIuv(), p.getCodiceAvviso(), p.getNumeroDocumento(),
		    statoPagamentoEnum == PAGATO ? StatiPagamento.ATTIVATO_IN_PSP
			    : MAPPATURA_STATI_PAYER.get(PayerConnector.statoPagamentoType.fromString(p.getStato())),
		    p.getTimeStampInizio() != null
			    ? datatypeFactory.newXMLGregorianCalendar(getCorrectedTimestamp(p.getTimeStampInizio())).toGregorianCalendar().getTime()
			    : new Date());
	    log.debug("INSERITO iuv: {}, codiceAvviso: {}, numeroDocumento: {}", p.getIuv(), p.getCodiceAvviso(), p.getNumeroDocumento());
	    if (statoPagamentoEnum == PAGATO) {
		EsitoRecuperaPagamento esito;
		try {
		    esito = recuperaRTPagamentoDaLepida(p.getIuv(), codiceconnettore);
		} catch (Exception e) {
		    log.warn("Errore nel recupero delle informazioni dei pagamenti per la posizione debitoria {}", p.getIuv(), e);
		    continue;
		}
		if (!StringUtils.isBlank(esito.getRt())) {
		    PayPosizioniDebitorie firstPd = parRegistrazioneContabile.getPosizioniDebitorie().iterator().next();
		    try {
			DatiPagamentoType datiPagXml = RTHelper.popolaDatiPagamentoDaReceiptORicevutaTelematica(esito.getRt(), null);
			PayPagamenti payPagamenti = PayPagamentiServiceImpl.populateDomainObject(datiPagXml, firstPd,
				StatoPagamentoType.fromValue(String.valueOf(MAPPATURA_STATI_PAYER.get(statoPagamentoEnum))));
			log.debug("Prima di registrare avvenuto pagamento per iuv: {}, codiceAvviso: {}, numeroDocumento: {}", p.getIuv(),
				p.getCodiceAvviso(), p.getNumeroDocumento());
			this.payPagamentiService.registraAvvenutoPagamento(payPagamenti, firstPd, esito.getRt());
			log.debug("Avvenuto pagamento registrato per iuv: {}, codiceAvviso: {}, numeroDocumento: {}", p.getIuv(), p.getCodiceAvviso(),
				p.getNumeroDocumento());
		    } catch (Exception e) {
			log.warn("Errore durante la registrazione dell avvenuto pagamento, pd {}", p.getIuv(), e);
		    }
		} else {
		    log.warn("Dati del pagamento mancati, manca la ricevuta, pd {} ", p.getIuv());
		}
	    }
	}
    }

    private static String getCorrectedTimestamp(String wrongTimestamp) {

	return wrongTimestamp.replaceFirst("(\\+\\d{2})(\\d{2})$", "$1:$2");
    }
}
