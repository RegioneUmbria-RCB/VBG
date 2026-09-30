package it.gruppoinit.pal.gp.pay.connector.jcitygov;

import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import it.gov.digitpa.schemas._2011.pagamenti.v_6_2_0.CtRicevutaTelematica;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.AvvisoPagamento;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.AvvisoPagamentoResponse;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.AvvisoPagamentoResultDto;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.BackUrlDto;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.ChiaveDebitoDto;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.DatiAccertamento;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.DatiContribuente;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.DettaglioDovuto;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.Dovuto;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.EliminaDebitoRequest;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.EliminaDebitoResponse;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.ErroreRisposta;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.InfoPagamentoDebitoResponse;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.NumeroAvviso;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.PagamentoResponse;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.RichiestaCheckoutDto;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.RichiestaDovuto;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.RichiestaInfoPagamentoDebitoDto;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.StampaAvvisaturaChiaviDebito;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.StampaAvvisaturaRequest;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.StampaAvvisaturaResponse;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.TestataDovuto;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.client.JCityGovClient;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.exc.JCityGovRestException;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.pagamenti.InfoPagamentoTelematicoDtoV2;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema.CtIdentificativoUnivocoPersonaFG;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema.StCodiceMotivoEliminazione;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema.StEsito;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema.StTipoIdentificativoUnivocoPersFG;
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
import it.gruppoinit.pal.gp.pay.parameters.ParametroChiaveApplicationCodeIUV;
import it.gruppoinit.pal.gp.pay.parameters.ParametroCodiceServizio;
import it.gruppoinit.pal.gp.pay.parameters.ParametroDescrizioneCausalePSP;
import it.gruppoinit.pal.gp.pay.parameters.ParametroInviaDettagliPagamento;
import it.gruppoinit.pal.gp.pay.service.PagoPAService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.AgidEsitiPagamentoEnum;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.ParametriConnettoreHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RTHelper;
import it.gruppoinit.pal.gp.pay.service.impl.PagoPAServiceImpl;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.HttpMethodType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;

public class JCityGovConnector extends AbstractPayConnector implements IPayConnector {

    private static final Logger log = LoggerFactory.getLogger(JCityGovConnector.class);
    private static final String BENEFICIARIO_TYPE = "MONOBENEFICIARIO";
    private static final String LANG_LOWERCASE = "it";
    private static final String I797_ERR_PAGAMENTO_NON_TROVATO = "I797_ERR_PAGAMENTO_NON_TROVATO";
    @Autowired
    private PaySessioniPagamentoService paySessioniPagamentoService;
    @Autowired
    private PayStatoPagamentiService pagamenPayStatoPagamentiService;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PagoPAService pagoPAService;
    @Autowired
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    @Autowired
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	log.debug("Caricamento posizioni debitorie begin");
	ElencoPosizioniDebitorieEsitoType result = new ElencoPosizioniDebitorieEsitoType();
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    log.debug("Caricamento posizioni debitorie registrazione contabile {} ", payReg.getId());
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		log.debug("Caricamento posizioni debitorie posizione debitoria {} ", payPos.getId());
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		esitoPos.setStato(StatoPagamentoType.ACQUISITO);
		//populateIpaServizioRichiestaStandardRest begin
		RichiestaDovuto richiestaDovuto = new RichiestaDovuto();
		richiestaDovuto.setCodiceIPA(payPos.getProfiloEnte().getIdAppPSP());
		richiestaDovuto.setCodiceServizio(getCodiceServizio(payPos));
		//populateIpaServizioRichiestaStandardRest end
		log.debug("popolo i dati della richiesta per la posizione {} ", payPos.getId());
		/*
		 * CAMPI SETTATI A NULL:
		 * dataAttualizzazione
		 * dataLimitePagabilita
		 * marcaDaBollo
		 * parametriDebito
		 * speseNotificaDaAttualizzare
		 * speseNotificaVoceContabile
		 */
		popolaDatiRichiestaDovutoRest(richiestaDovuto, payPos);
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		try {
		    log.debug("prima di chiamare il ws per la posizione {} ", payPos.getId());
		    JCityGovClient restClient = new JCityGovClient(getWsCaricamentoConfig(), richiestaDovuto.getCodiceIPA());
		    AvvisoPagamentoResponse response;
		    try {
			response = (AvvisoPagamentoResponse) restClient.pagamentiExec(richiestaDovuto, "/rest/dovuti/v1/inviaDovuto",
				AvvisoPagamentoResponse.class, HttpMethod.POST);
		    } catch (Exception e) {
			log.debug("Dopo la chiamata al ws per la posizione {} - esito {}", payPos.getId(), "KO");
			throw new PayException(e + "");
		    }
		    log.debug("Dopo la chiamata al ws per la posizione {} - esito {}", payPos.getId(), "OK");
		    AvvisoPagamentoResultDto avviso = response.getAvvisoPagamentoResultDto();
		    if (avviso.getEsito() != null && avviso.getEsito().getEsito() != null
			    && avviso.getEsito().getEsito().equalsIgnoreCase(StEsito.ERROR.name())) {
			throw new PayException(avviso.getEsito().getMessaggio());
		    }
		    esitoPos.setCodiceAvviso(avviso.getNumeroAvvisoDto().getNumeroAvviso());
		    esitoPos.setIUV(populateIUVFromNumeroAvviso(avviso.getNumeroAvvisoDto().getNumeroAvviso()));
		    esitoPos.setQrCode(this.pagoPAService.generaQRCode(payPos));
		    esitoPos.setEsito(true);
		    esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		} catch (Exception e) {
		    this.handleException(e, esitoPos);
		}
		result.getEsitoPosizione().add(esitoPos);
	    }
	}
	log.debug("Caricamento posizioni debitorie end");
	return result;
    }

    private static String populateIUVFromNumeroAvviso(String numeroAvviso) {

	if (numeroAvviso.startsWith("0")) {
	    return StringUtils.substring(numeroAvviso, 3);
	}
	//	if (numeroAvviso.startsWith("1")) {
	//	    return StringUtils.substring(numeroAvviso, 1);
	//	}
	//	if (numeroAvviso.startsWith("2")) {
	//	    return StringUtils.substring(numeroAvviso, 1);
	//	}
	//	if (numeroAvviso.startsWith("3")) {
	//	    return StringUtils.substring(numeroAvviso, 1);
	//	}
	// Tabella 2 - Composizione del codice avviso in funzione dei punti di generazione dello IUV
	return StringUtils.substring(numeroAvviso, 1);
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException {

	log.debug("annullaPosizioniDebitorie BEGIN (REST Maggioli)");
	ElencoPosizioniDebitorieEsitoType result = new ElencoPosizioniDebitorieEsitoType();
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		EsitoOperazionePosizioneDebitoriaType esito = new EsitoOperazionePosizioneDebitoriaType();
		esito.setIdPosizione(BigInteger.valueOf(payPos.getId().getCodice()));
		JCityGovClient client = new JCityGovClient(getWsAnnullamentoConfig(), payPos.getProfiloEnte().getIdAppPSP());
		try {
		    PayStatoPagamenti statoCorrente = this.pagamenPayStatoPagamentiService.getStatoPosizioneDebitoria(payPos);
		    esito.setStato(StatoPagamentoType.fromValue(statoCorrente.getStato()));
		    if (BooleanUtils.isTrue(payPos.getFlagOTF())) {
			esito.setStato(StatoPagamentoType.ANNULLATO);
			esito.setEsito(true);
			PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, payPos,
				payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
			result.getEsitoPosizione().add(esito);
			continue;
		    }
		    // REST Maggioli - preparazione request
		    EliminaDebitoRequest request = new EliminaDebitoRequest();
		    request.setCodiceIPA(payPos.getProfiloEnte().getIdAppPSP());
		    request.setCodiceServizio(getCodiceServizio(payPos));
		    request.setCodiceMotivoEliminazione(StCodiceMotivoEliminazione.PAGAMENTO_ESTERNO.name());
		    // request.setDescrizioneMotivoEliminazione(""); lo posso non passare                    ;
		    NumeroAvviso ctNumAvviso = new NumeroAvviso();
		    ctNumAvviso.setFlagAttivaDebito(false);
		    //ctNumAvviso.setVersioneNumeroAvviso(1);
		    ctNumAvviso.setNumeroAvviso(payPos.getCodiceAvviso());
		    request.setNumeroAvviso(ctNumAvviso);
		    if (pagatoOffline) {
			request.setCodiceMotivoEliminazione(StCodiceMotivoEliminazione.PAGAMENTO_ESTERNO.value());
			request.setDescrizioneMotivoEliminazione("Pagamento effettuato fuori PagoPA");
		    }
		    client.pagamentiExec(request, "/rest/dovuti/v1/cancella", EliminaDebitoResponse.class, HttpMethod.DELETE);
		    //Se � 200 � OK altrimenti va in Exception
		    // Aggiorna stato locale
		    esito.setStato(pagatoOffline ? StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO : StatoPagamentoType.ANNULLATO);
		    esito.setEsito(true);
		    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, payPos,
			    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		} catch (Exception e) {
		    this.handleException(e, esito);
		}
		result.getEsitoPosizione().add(esito);
	    }
	}
	log.debug("annullaPosizioniDebitorie END (REST Maggioli)");
	return result;
    }

    @Override
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	List<PaySessioniPagamento> sex = new ArrayList<>();
	String[] idSessioneVals = reqParams.get("idSessionePagamento");
	String[] idPayPosDebVals = reqParams.get("idPayPos");
	String[] esitoVal = reqParams.get("stato");
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
	    return sex.get(0); // ne ritorno una perch� la redirect � sempre quella
	}
	return null;
    }

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

	// TODO
    }

    @Override
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(PosizioniDebitorieCommand cmd) throws PayException {

	// CHIAMA PAGADEBITI
	// nelle url inviare parametro idSessionePagamento
	// String[] idPayPosDebVals = reqParams.get("idPayPos");
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
	try {
	    String idSessione = ORMHelper.getIdcomune() + "_" + UUID.randomUUID().toString();
	    RichiestaCheckoutDto request = new RichiestaCheckoutDto();
	    request.setAvvisiPagamento(new ArrayList<AvvisoPagamento>());
	    request.setIdRichiestaPagamento(idSessione);
	    request.setLang(LANG_LOWERCASE);
	    BackUrlDto bu = new BackUrlDto();
	    String urlKO = getUrlBack(idSessione, payPosizioniDebitorie, "KO");
	    String urlNotify = getUrlNotify(idSessione, payPosizioniDebitorie);
	    String urlOK = getUrlBack(idSessione, payPosizioniDebitorie, "OK");
	    bu.setBackUrlKO(urlKO);
	    bu.setBackUrlNotify(urlNotify);
	    bu.setBackUrlOK(urlOK);
	    bu.setBackUrlCancel(urlKO); //useremo lo stesso url del KO	    
	    request.setBackUrlDto(bu);
	    //C'� posto per una sola mail
	    for (PayPosizioniDebitorie pd : payPosizioniDebitorie) {
		if (StringUtils.isNotBlank(StringUtils.defaultString(pd.getSoggettoDebitore().getEmail()).trim())) {
		    request.setEmailDiNotifica(StringUtils.defaultString(pd.getSoggettoDebitore().getEmail()).trim());
		}
	    }
	    //populateIpaServizioRichiestaStandard: non c'� pi� un populaterichiestastandard
	    /*
	     * Con il nuovo sistema REST, il pagamento si basa sul numero accertamento, a patto che le rispettive posizioni debitorie siano
	     * registrate. Quindi dobbiamo prima registrarle, ed eventualmente annullarle in caso di errore.
	     * Annullare le posizioni debitorie in caso di errore: TODO
	     */
	    ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie = registraPosizioniDebitorie(cmd);
	    //DEVO TRACCIARMI PER OGNI PD L'IMPORTO E IL NUMERO AVVISO
	    //begin
	    //Traccio i numeriavviso con le rispettive posizionidebitorie
	    Map<Integer, String> codiciAvvisoPDebMap = new HashMap<Integer, String>();
	    for (EsitoOperazionePosizioneDebitoriaType esitoposizione : registraPosizioniDebitorie.getEsitoPosizione()) {
		if (esitoposizione.getStato() == StatoPagamentoType.CON_ERRORE) {
		    throw new PayException("Si � verificato un errore durante la registrazione della posizione debitoria " +
					   esitoposizione.getIdPosizione() + " : " + esitoposizione.getMessaggio());
		}
		codiciAvvisoPDebMap.put(intValueExact(esitoposizione.getIdPosizione()), esitoposizione.getCodiceAvviso());
	    }
	    //Traccio gli importi per singola posizione debitoria
	    Map<Integer, BigDecimal> importiPDebMap = new HashMap<Integer, BigDecimal>();
	    for (PayPosizioniDebitorie pd : payPosizioniDebitorie) {
		BigDecimal importoTotale = BigDecimal.ZERO;
		for (PayDettaglioImporti imp : pd.getDettagliImporto()) {
		    importoTotale = importoTotale.add(imp.getImporto());
		}
		importiPDebMap.put(pd.getId().getCodice(), importoTotale);
	    }
	    //end
	    for (PayPosizioniDebitorie pd : payPosizioniDebitorie) {
		AvvisoPagamento avvisoPagamento = new AvvisoPagamento();
		avvisoPagamento.setImporto(importiPDebMap.get(pd.getId().getCodice()));
		avvisoPagamento.setNumeroAvviso(codiciAvvisoPDebMap.get(pd.getId().getCodice()));
		avvisoPagamento.setCodiceIpaEnte(pd.getProfiloEnte().getIdAppPSP());
		avvisoPagamento.setCodiceServizio(getCodiceServizio(pd));
		request.getAvvisiPagamento().add(avvisoPagamento);
		pd.setCodiceAvviso(codiciAvvisoPDebMap.get(pd.getId().getCodice()));
		pd.setIuv(populateIUVFromNumeroAvviso(pd.getCodiceAvviso()));
		pd.setQrCode(this.pagoPAService.generaQRCode(pd));
		this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(pd, null, null);
		log.debug("aggiunta pd {} nella request ", pd.getId());
	    }
	    log.debug("prima di chiamare il ws ");
	    JCityGovClient restClient = new JCityGovClient(getWsAttivaSessioneConfig(), request.getAvvisiPagamento().get(0).getCodiceIpaEnte());
	    PagamentoResponse response;
	    try {
		response = (PagamentoResponse) restClient.pagamentiExec(request, "/rest/debiti/v2/checkout", PagamentoResponse.class,
			HttpMethod.POST);
	    } catch (Exception e) {
		log.debug("Dopo la chiamata al ws - esito {}", "KO");
		throw new PayException(e + "");
	    }
	    log.debug("Dopo la chiamata al ws - esito {}", "OK");
	    if (response.getEsitoDto() != null && response.getEsitoDto().getEsito() != null
		    && response.getEsitoDto().getEsito().equalsIgnoreCase(StEsito.ERROR.name())) {
		throw new PayException(response.getEsitoDto().getMessaggio());
	    }
	    // String idTransazione = response.getIdentTransazione();
	    String url = response.getUrl();
	    attivaSessioneOTF.setEsito(true);
	    attivaSessioneOTF.setIdSessione(idSessione);
	    attivaSessioneOTF.setHttpMethodRequired(HttpMethodType.GET);
	    attivaSessioneOTF.setPayUrl(url);
	    for (PayPosizioniDebitorie pd : payPosizioniDebitorie) {
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		esitoPos.setIUV(pd.getIuv()); // li ho settati sopra
		esitoPos.setCodiceAvviso(pd.getCodiceAvviso()); // li ho settati sopra
		esitoPos.setQrCode(pd.getQrCode()); // li ho settati sopra
		esitoPos.setEsito(true);
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, pd,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pd.getId().getCodice()));
		esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		esitoPos.setMessaggio("Posizione OTF creata con successo");
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

    private static Integer intValueExact(BigInteger bi) {

	if (bi.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0 || bi.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) < 0) {
	    throw new ArithmeticException("BigInteger out of int range");
	}
	return bi.intValue();
    }

    private String getUrlNotify(String idSessione, List<PayPosizioniDebitorie> payPosizioniDebitorie) {

	PayProfiliEntiCreditori profiloEnte = null;
	String listaPosizioni = "";
	for (PayPosizioniDebitorie payPos : payPosizioniDebitorie) {
	    listaPosizioni += "&idPayPos=" + payPos.getId().getCodice();
	    profiloEnte = payPos.getProfiloEnte();
	    break;
	}
	String urlRicezioneNotifiche = this.payConnectorConfigValuesService
		.getValoreParametroConfigurazione(ConfigParamNames.URL_RICEZIONE_NOTIFICHE);
	return urlRicezioneNotifiche + "?idProfilo=" + profiloEnte.getCfCodiceProfilo() + "&idSessionePagamento=" + idSessione + listaPosizioni;
    }

    private String getUrlBack(String idSessione, List<PayPosizioniDebitorie> payPosizioniDebitorie, String stato) {

	String urlEsito = null;
	String listaPosizioni = "";
	for (PayPosizioniDebitorie p : payPosizioniDebitorie) {
	    if (StringUtils.isBlank(urlEsito)) {
		urlEsito = p.getProfiloEnte().getUrlEsitoPagamento() + "?idSessionePagamento=" + idSessione;
	    }
	    listaPosizioni += "&idPayPos=" + p.getId().getCodice();
	    //		String[] idSessioneVals = reqParams.get("idSessionePagamento");
	    //		String[] idPayPosDebVals = reqParams.get("idPayPos");
	    //		String[] esitoVal = reqParams.get("stato");
	}
	return urlEsito + listaPosizioni + "&stato=" + stato;
    }

    @Override
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	ElencoStatoPosizioniType result = new ElencoStatoPosizioniType();
	for (PayPosizioniDebitorie payPos : cmd.getPosizioni()) {
	    JCityGovClient restClient = new JCityGovClient(getWsVerificaConfig(), payPos.getProfiloEnte().getIdAppPSP());
	    StatoPosizioneType retStatus = new StatoPosizioneType();
	    retStatus.setEsito(true);
	    payPos = payPosizioniDebitorieService.findById(new PkId(payPos.getId().getCodice()));
	    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos,
		    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	    try {
		// ==============================
		// COSTRUZIONE REQUEST REST
		// ==============================
		String codiceTipoDebito = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos);
		RichiestaInfoPagamentoDebitoDto requestRest = new RichiestaInfoPagamentoDebitoDto();
		ChiaveDebitoDto chiaveDebito = new ChiaveDebitoDto();
		chiaveDebito.setiDeb(payPos.getIdPosizionePsp());
		chiaveDebito.setCodiceTipoDebito(codiceTipoDebito);
		chiaveDebito.setiPos(getIdPos(payPos));
		chiaveDebito.setCodEnteCreditore(payPos.getProfiloEnte().getIdAppPSP());
		requestRest.setChiaveDebito(chiaveDebito);
		requestRest.setCodiceServizio(getCodiceServizio(payPos));
		requestRest.setCodIpaRichiedente(chiaveDebito.getCodEnteCreditore());
		// ==============================
		// CHIAMATA REST
		// ==============================
		InfoPagamentoDebitoResponse response;
		try {
		    response = (InfoPagamentoDebitoResponse) restClient.pagamentiExec(requestRest, "/rest/pagamenti/v2/infoPerDovuto",
			    InfoPagamentoDebitoResponse.class, HttpMethod.POST);
		} catch (JCityGovRestException e) {
		    Map<String, Object> props = new HashMap<>();
		    props.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
		    props.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
		    JAXBContext jc = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] { ErroreRisposta.class }, props);
		    Unmarshaller unmarshaller = jc.createUnmarshaller();
		    ErroreRisposta errorResponse = unmarshaller
			    .unmarshal(new StreamSource(new StringReader(e.getBodyResponse())), ErroreRisposta.class).getValue();
		    if (I797_ERR_PAGAMENTO_NON_TROVATO.equals(errorResponse.getErrorCode())) {
			log.error("Errore su posizione debitoria {}: {}", requestRest.getChiaveDebito().getiDeb(), e.getMessage());
			continue;
		    } else {
			throw e;
		    }
		}
		log.debug("Risposta REST infoPagamentoDebito {}", response);
		// ==============================
		// INTERPRETAZIONE RISPOSTA
		// ==============================
		List<InfoPagamentoTelematicoDtoV2> infoPagamentoTelematico = response.getListaInfoPagamentoTelematicoDtoV2();
		if (infoPagamentoTelematico == null || infoPagamentoTelematico.isEmpty()) {
		    log.debug("infoPagamentoTelematico.isEmpty()");
		    continue;
		}
		for (InfoPagamentoTelematicoDtoV2 infoP : infoPagamentoTelematico) {
		    String statoTecnico = infoP.getStatoTecnicoPagamento();
		    String esitoRichiesta = infoP.getEsitoRichiestaPagamento();
		    log.debug("StatoTecnico={}, EsitoRichiesta={}", statoTecnico, esitoRichiesta);
		    if ("CONCLUSO_ESEGUITO".equalsIgnoreCase(esitoRichiesta)) {
			retStatus.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP);
			log.debug("StatoPagamentoType.NOTIFICATO_DA_PSP");
			if ("CONTABILIZZATO".equalsIgnoreCase(statoTecnico)) {
			    if (StringUtils.isNotBlank(infoP.getFlussoRicevuta())) {
				String rtXml = Utilities.decodeBase64Binary(infoP.getFlussoRicevuta());
				CtRicevutaTelematica ctrt = RTHelper.parseRicevutaTelematica(rtXml);
				if (ctrt.getDatiPagamento().getCodiceEsitoPagamento().equals(AgidEsitiPagamentoEnum.PAGAMENTO_ESEGUITO.getValore())
					|| ctrt.getDatiPagamento().getCodiceEsitoPagamento()
						.equals(AgidEsitiPagamentoEnum.PAGAMENTO_PARZIALMENTE_ESEGUITO.getValore())) {
				    DatiPagamentoType datiPag = RTHelper.popolaDatiPagamentoDaRicevutaTelematica(ctrt, null);
				    DataSource ds = new ByteArrayDataSource(rtXml, ContentType.APPLICATION_OCTET_STREAM.getMimeType());
				    datiPag.setRicevutaXml(new DataHandler(ds));
				    retStatus.setEsito(true);
				    retStatus.setDatiPagamento(datiPag);
				    retStatus.setStato(StatoPagamentoType.RENDICONTATO_DA_IC);
				    log.debug("StatoPagamentoType.RENDICONTATO_DA_IC");
				}
			    }
			}
			break;
		    }
		}
	    } catch (Exception e) {
		retStatus.setEsito(false);
		this.handleException(e, retStatus);
	    }
	    result.getStatoPosizioni().add(retStatus);
	}
	return result;
    }
    /*
    @Override
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {
    
    	// Aggiornamento di uno o pi� campi di una pendenza
    	ElencoStatoPosizioniType result = new ElencoStatoPosizioniType();
    	for (PayPosizioniDebitorie payPos : cmd.getPosizioni()) {
    	    StatoPosizioneType retStatus = new StatoPosizioneType();
    	    retStatus.setEsito(true);
    	    payPos = payPosizioniDebitorieService.findById(new PkId(payPos.getId().getCodice()));
    	    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos);
    	    try {
    		RichiestaStandard r = populateIpaServizioRichiestaStandard(payPos);
    		RichiestaInfoPagamentoDebito ipd = new RichiestaInfoPagamentoDebito();
    		CtChiaveDebito cd = new CtChiaveDebito();
    		String codiceTipoDebito = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos);
    		cd.setCodiceTipoDebito(codiceTipoDebito);
    		cd.setIDPos(getIdPos(payPos));
    		cd.setIDDeb(payPos.getIdPosizionePsp());
    		ipd.setChiaveDebito(cd);
    		String ddr = IOUtils.marshallObject(ipd);
    		r.setDatiDettaglioRichiesta(ddr);
    		RispostaStandard response = wsVerifica().infoPagamentoDebito(r);
    		String esitoOperazione = response.getEsitoOperazione();
    		log.debug("Dopo la chiamata al ws per la posizione {} - esito {}", payPos.getId(), esitoOperazione);
    		if (!esitoOperazione.equalsIgnoreCase("OK")) {
    		    // gestire caso di errore
    		    StringBuilder messaggioErrore = new StringBuilder();
    		    List<Messaggio> messaggio = response.getMessaggi().getMessaggio();
    		    for (Messaggio m : messaggio) {
    			messaggioErrore.append(m.getDescrizione()).append(" [").append(m.getCodice()).append("]\n");
    		    }
    		    throw new PayException(messaggioErrore.toString());
    		}
    		// interpretare le risposte
    		RispostaInfoPagamentoDebito risposta = (RispostaInfoPagamentoDebito) IOUtils.unMarshallString(response.getDatiDettaglioRisposta(),
    			RispostaInfoPagamentoDebito.class);
    		log.debug("risposta {}", risposta);
    		List<CtInfoPagamentoTelematico> infoPagamentoTelematico = risposta.getInfoPagamentoTelematico();
    		if (infoPagamentoTelematico.isEmpty()) {
    		    // non sono presenti informazioni sui pagamenti
    		    log.debug("infoPagamentoTelematico.isEmpty()");
    		    continue;
    		}
    		// Stato tecnico: INSERITO (non ancora inviato), RIFIUTATO (tentato invio, con esito negativo), 
    		// INVIATO (inviato al nodo), COMPLETATO (operazione completata, in attesa di ricevuta), 
    		// CONTABILIZZATO (ricevuta disponibile), NOTIFICATO (notificato al B.O.), 
    		// INDETERMINATO (pagamento non presente nel database locale)
    		// Il Tag DataAccredito esiste e corrisponde al momento in cui viene rendicontato il pagamento attraverso 
    		// il flusso AGID che viene invito a JPPA normalmente 24-47h dopo il pagamento, quindi la rappresenta la data contabile di
    		// regolamento del pagamento.
    		// Gli stati sono normalmente riconducibili a CONCLUSO_ESEGUITO, NON_ESEGUITO, CONCLUSO_NON_CONCLUSO 
    		// e l�importo � pari zero nel caso di annullo o scarto del pagamento.
    		// Il metodo restituisce una lista di pagamenti, in quanto in funzione delle chiavi possono esistere
    		// n tentativi di pagamento, ognuno con il proprio dettaglio ed esito. Tipicamente saranno negativi e solo 1 avr� un esito 
    		// CONCLUSO_ESEGUITO, ovvero quello effettivamente pagato.
    		// <StatoTecnicoPagamento>NOTIFICATO</StatoTecnicoPagamento>
    		// <EsitoRichiestaPagamento>CONCLUSO_ESEGUITO</EsitoRichiestaPagamento>
    		for (CtInfoPagamentoTelematico infoP : infoPagamentoTelematico) {
    		    String stato = infoP.getStatoTecnicoPagamento();
    		    log.debug("infoP.getStatoTecnicoPagamento()={}, infoP.getEsitoRichiestaPagamento()={}", stato,
    			    infoP.getEsitoRichiestaPagamento());
    		    if (StringUtils.defaultString(infoP.getEsitoRichiestaPagamento()).equalsIgnoreCase("CONCLUSO_ESEGUITO")) {
    			retStatus.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP);
    			log.debug("StatoPagamentoType.NOTIFICATO_DA_PSP");
    			// � lo stato 
    			// Da capire se torna contabilizzato anche un pagamento non andato a buon fine 
    			// (esempio ricevute con codice 9) 
    			if (stato.equalsIgnoreCase("CONTABILIZZATO")) {
    			    if (StringUtils.isNotBlank(infoP.getFlussoRicevuta())) {
    				String rt = Utilities.decodeBase64Binary(infoP.getFlussoRicevuta());
    				CtRicevutaTelematica ctrt = RTHelper.parseRicevutaTelematica(rt);
    				if (ctrt.getDatiPagamento().getCodiceEsitoPagamento().equals(AgidEsitiPagamentoEnum.PAGAMENTO_ESEGUITO.getValore())
    					|| ctrt.getDatiPagamento().getCodiceEsitoPagamento()
    						.equals(AgidEsitiPagamentoEnum.PAGAMENTO_PARZIALMENTE_ESEGUITO.getValore())) {
    				    DatiPagamentoType datiPag = RTHelper.popolaDatiPagamentoDaRicevutaTelematica(ctrt, null);
    				    DataSource ds = new ByteArrayDataSource(rt, ContentType.APPLICATION_OCTET_STREAM.getMimeType());
    				    DataHandler ricevutaXML = new DataHandler(ds);
    				    datiPag.setRicevutaXml(ricevutaXML);
    				    retStatus.setEsito(true);
    				    retStatus.setDatiPagamento(datiPag);
    				    retStatus.setStato(StatoPagamentoType.RENDICONTATO_DA_IC);
    				    log.debug("StatoPagamentoType.RENDICONTATO_DA_IC");
    				}
    			    }
    			}
    			break;
    		    }
    		}
    	    } catch (Exception e) {
    		retStatus.setEsito(false);
    		this.handleException(e, retStatus);
    	    }
    	    result.getStatoPosizioni().add(retStatus);
    	}
    	return result;
    }
    */

    @Override
    public boolean supportaAttivaSessionePagamento() {

	return false;
    }

    @Override
    public boolean supportaPagamentoOnTheFly() {

	return true;
    }

    @Override
    public boolean supportaAvvisoPagamento() {

	return true;
    }

    @Override
    public boolean supportaModificaDataScadenza() {

	return false;
    }

    /**
     * rappresenta l'identificativo del debito associamo all'identificativo complessivo del debito IDPOS la
     * registrazione contabile e all'identificativo della singola rata IDDEB l'id_posizione_psp
     * 
     * @param payPos
     * @return
     */
    private String getIdPos(PayPosizioniDebitorie payPos) {

	return String.valueOf(payPos.getId().getCodice());
    }

    @Override
    public ElencoDocumentiEsitoType inviaAvvisiPagamento(PosizioniDebitorieCommand cmd) throws PayException {

	log.debug("inviaAvvisiPagamento ");
	PayConnectorWsEndpoint wsAvviso = this.getWsAvvisoConfig();
	PayProfiliEntiCreditori ente = PayConfigurationHelper.getProfiloEnteCreditore();
	if (wsAvviso == null) {
	    throw new PayConfigurationException(
		    "l'endpoint per il servizio di generazione degli avvisi non è configurato per il connettore " + this.getConnectorName());
	}
	ElencoDocumentiEsitoType retEsiti = new ElencoDocumentiEsitoType();
	String msg = null;
	JCityGovClient restClient = new JCityGovClient(wsAvviso, ente.getCfCodiceProfilo());
	if (restClient != null) {
	    for (PayRegistrazioniContabili reg : cmd.getRegistrazioniPosizioni()) {
		for (PayPosizioniDebitorie pos : reg.getPosizioniDebitorie()) {
		    log.debug("inviaAvvisiPagamento pos {}", pos.getId());
		    msg = "invocazione del servizio getPagamentoAttesoPdf in corso";
		    EsitoDocumentoPosizioneDebitoriaType esitoDoc = new EsitoDocumentoPosizioneDebitoriaType();
		    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoDoc, pos,
			    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
		    esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
		    esitoDoc.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
		    try {
			log.debug("inviaAvvisiPagamento pos {}", pos.getId());
			StampaAvvisaturaRequest request = new StampaAvvisaturaRequest();
			StampaAvvisaturaChiaviDebito chiaviDebito = new StampaAvvisaturaChiaviDebito();
			chiaviDebito.setCodiceIpaEnte(pos.getProfiloEnte().getIdAppPSP());
			chiaviDebito.setCodiceServizio(getCodiceServizio(pos));
			chiaviDebito.setCodiceTipoDebito(posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(pos));
			chiaviDebito.setIdDebito(pos.getIdPosizionePsp());
			chiaviDebito.setIdPosizione(String.valueOf(pos.getId().getCodice()));
			request.setChiaviDebito(chiaviDebito);
			request.setNumeroAvviso(String.valueOf(pos.getCodiceAvviso()));
			request.setIdInstallazione(getIdInstallazioneJCITY());
			request.setBase64FileLogoEnte(getLogo(pos.getProfiloEnte().getLogoEnte()));
			log.debug("inviaAvvisiPagamento prima di chiamare client.generaAvviso pos {}", pos.getId());
			StampaAvvisaturaResponse response = (StampaAvvisaturaResponse) restClient.printerPost(request,
				"/rest/printer/v2/stampa-avvisatura", StampaAvvisaturaResponse.class);
			if (response != null && StringUtils.equalsIgnoreCase(response.getEsito(), "OK")) {
			    byte[] decodedBytes = Base64.decodeBase64(response.getFileBase64Encoded().getBytes());
			    DataSource dtS = new ByteArrayDataSource(decodedBytes, "application/pdf");
			    DataHandler avviso = new DataHandler(dtS);
			    if (avviso != null) {
				log.debug("inviaAvvisiPagamento client.generaAvviso pos {} OK", pos.getId());
				msg = "l'avviso di pagamento in PDF è stato scaricato correttamente";
				esitoDoc.setEsito(true);
				esitoDoc.setDocumento(avviso);
				esitoDoc.setNomeDocumento("AVVISO_" + pos.getIuv() + ".pdf");
				esitoDoc.setMessaggio(response.getMessage());
			    }
			} else {
			    esitoDoc.setEsito(false);
			    esitoDoc.setMessaggio(response.getMessage());
			    esitoDoc.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
			}
		    } catch (Exception e) {
			this.handleException(e, esitoDoc);
			msg = "impossibile scaricare l'avviso PDF a causa dell'errore: " + e.toString();
			log.error("inviaAvvisiPagamento - {}", msg, e);
		    }
		    log.debug("inviaAvvisiPagamento - {}", msg);
		    retEsiti.getEsitoPosizione().add(esitoDoc);
		}
	    }
	}
	return retEsiti;
    }

    @Override
    public boolean supportaMolteCausaliRaggruppate() {

	return true;
    }

    @Override
    public List<IParameter> getListaParametriRichiesti() {

	return ParametriConnettoreHelper.getMappaParametriConnettore(this.getClass().getName());
    }

    /**
     * Lo IUV viene generato nel seguente modo (per Comune di Jesi) -> <b>ASSZZNNNNNNNNNNNCC</b> dove:
     * <ul>
     * <li>Campo A: codice aux (3)</li>
     * <li>Campo SS: codice segregazione/applicazione (01)</li>
     * <li>Campo ZZ: prefisso iuv servizio (35)</li>
     * <li>Campo NNNNNNNNNNN: progressivo numerico univoco (a vostro piacimento)</li>
     * <li>Campo CC: CIN calcolato secondo le specifiche definite da PagoPA (resto della divisione
     * ASSZZNNNNNNNNNNN/93)</li>
     * </ul>
     *
     */
    @Override
    public IUVHelper generaIUV(PayPosizioniDebitorie posDeb, String identificativoCausalePerCalcolo) {

	log.debug("#generaIUV start");
	StringBuilder iuv = new StringBuilder();
	PayProfiliEntiCreditori profEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	if (profEnte != null && posDeb != null) {
	    // codice aux (application_code)
	    int codAux = 3;
	    if (profEnte.getApplicationCode() != null) {
		codAux = profEnte.getApplicationCode();
	    }
	    iuv.append(codAux);
	    log.debug("codice AUX: {}", codAux);
	    // codice segregazione/applicazione
	    int segr = 0;
	    if (profEnte.getCodiceSegregazione() != null) {
		segr = profEnte.getCodiceSegregazione();
	    }
	    iuv.append(StringUtils.leftPad(Integer.toString(segr), 2, '0'));
	    log.debug("codice segregazione {}", segr);
	    // prefisso iuv servizio
	    String prefServizio = "";
	    ParametroChiaveApplicationCodeIUV parametroChiaveApplicationCodeIUV = new ParametroChiaveApplicationCodeIUV();
	    try {
		prefServizio = StringUtils.defaultString(
			posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(posDeb, parametroChiaveApplicationCodeIUV))
			.trim();
	    } catch (PayException e) {
		throw new RuntimeException("Errore nel recupero del parametro " + parametroChiaveApplicationCodeIUV.getNomeParametro() +
					   " per la posizione debitoria " + posDeb,
			e);
	    }
	    Integer iuvServizio = null;
	    log.debug("prefisso IUV servizio {}", prefServizio);
	    if (StringUtils.isNotBlank(prefServizio) && Utilities.isInteger(prefServizio)) {
		iuvServizio = Integer.parseInt(prefServizio);
	    } else {
		throw new RuntimeException("Il parametro " + parametroChiaveApplicationCodeIUV.getNomeParametro() +
					   " non � stato impostato correttamente per la posizione debitoria " + posDeb);
	    }
	    iuv.append(StringUtils.leftPad(Integer.toString(iuvServizio), 2, '0'));
	    // progressivo numerico univoco
	    // 11 caratteri numerici univoci per idcomune codificano l'id della posizione debitoria
	    iuv.append(StringUtils.leftPad(Integer.toString(posDeb.getId().getCodice()), 11, '0'));
	    // CIN calcolato secondo le specifiche definite da PagoPA (resto della divisione ASSZZNNNNNNNNNNN/93)
	    BigInteger checkDigit = new BigInteger(iuv.toString());
	    checkDigit = checkDigit.remainder(BigInteger.valueOf(PagoPAServiceImpl.IUV_CHECK_DIGIT_DIVISOR));
	    iuv.append(StringUtils.leftPad(checkDigit.toString(), 2, '0'));
	}
	log.debug("#generaIUV end");
	return new IUVHelper(iuv.toString());
    }

    private String getCodiceServizio(PayPosizioniDebitorie pos) throws PayException {

	String ret = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(pos, new ParametroCodiceServizio());
	if (StringUtils.isBlank(ret)) {
	    log.error("Non � stato configurato correttamente il codiceServizio per la posizione {}", pos.getId());
	    throw new PayConfigurationException("Non � stato configurato correttamente il codiceServizio rif: " + pos.getId());
	}
	return ret;
    }

    private void popolaDatiRichiestaDovutoRest(RichiestaDovuto richiesta, PayPosizioniDebitorie payPos) throws PayException {

	richiesta.setTransactional(false);
	// Il <transactional>true</transactional> serve esclusivamente nel caso di caricamento    	    	
	// multiplo con la stessa chiamata, a far sì che la transazione sia unica o meno. Nel caso lo sia se ci fosse un problema su uno 
	// dei debiti verrebbe fatto un rollback per tutti i debiti in caricamento.
	// Nel caso non ci sia il tag e fosse una chiamata con inserimento multiplo, i debiti 
	//vengono gestiti con transazioni singole, quindi solo quelli problematici verrebbero scartati. 
	//La maggior parte dei fornitori non usa la transazionalità.
	richiesta.setDovuto(richiestaDovutoPerPosizioneRest(payPos, richiesta.getCodiceIPA()));
    }

    private Dovuto richiestaDovutoPerPosizioneRest(PayPosizioniDebitorie payPos, String codiceIpa) throws PayException {

	Dovuto ct = new Dovuto();
	ct.setDettaglioDovuto(new ArrayList<DettaglioDovuto>());
	ct.setContestoDovuto(BENEFICIARIO_TYPE);
	// popolare il numero avviso per Jesi 
	// se non impostato lo calcola JGOVCITY
	String popolaIUV = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(payPos,
		new ParametroChiaveApplicationCodeIUV());
	if (popolaIUV != null) {
	    NumeroAvviso numeroAvviso = new NumeroAvviso();
	    numeroAvviso.setVersioneNumeroAvviso(1);
	    numeroAvviso.setFlagAttivaDebito(Boolean.TRUE);
	    numeroAvviso.setNumeroAvviso(getIUV(payPos, null));
	    ct.setNumeroAvviso(numeroAvviso);
	}
	//boolean isOTF = false;
	//ct.setDebito(popolaDebitoFromPosizioneDebitoria(payPos, isOTF));   	
	ct.setTestataDovuto(popolaTestataDebitoRest(payPos));
	ct.getDettaglioDovuto().add(popolaDettaglioDebitoRest(payPos, codiceIpa));
	return ct;
    }

    private TestataDovuto popolaTestataDebitoRest(PayPosizioniDebitorie payPos) throws PayConfigurationException {

	TestataDovuto testata = new TestataDovuto();
	String idPosizionePsp = this.generaIdPosizioneDebitoria(payPos);
	payPos.setIdPosizionePsp(idPosizionePsp);
	testata.setIdPos(getIdPos(payPos));
	//String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos);
	//testata.setCodiceTipoDebito(codiceVersamento); andr� in dettaglio a quanto pare
	testata.setDatiContribuente(popolaSoggettoDebitoreRest(payPos));
	testata.setDettaglioPosizione(StringUtils.abbreviate(payPos.getRegistrazioneContabile().getDescrizione(), 70)); // 
	return testata;
    }

    private DatiContribuente popolaSoggettoDebitoreRest(PayPosizioniDebitorie payPos) {

	PaySoggettiDebitori soggettoDebitore = payPos.getSoggettoDebitore();
	DatiContribuente soggetto = new DatiContribuente();
	CtIdentificativoUnivocoPersonaFG idu = new CtIdentificativoUnivocoPersonaFG();
	idu.setTipoIdentificativoUnivoco(StTipoIdentificativoUnivocoPersFG.F);
	if (soggettoDebitore.getCfPi().trim().length() < 12) {
	    idu.setTipoIdentificativoUnivoco(StTipoIdentificativoUnivocoPersFG.G);
	}
	idu.setCodiceIdentificativoUnivoco(soggettoDebitore.getCfPi().trim());
	//soggetto.setIdentificativoUnivocoVersante(idu); dovrebbero essere questi due
	soggetto.setTipoIdentificativoUnivoco(idu.getTipoIdentificativoUnivoco().name());
	soggetto.setCodiceIdentificativoUnivoco(idu.getCodiceIdentificativoUnivoco());
	if (StringUtils.isNotBlank(soggettoDebitore.getCognome())) {
	    soggetto.setCognome(soggettoDebitore.getCognome());
	}
	soggetto.setNome(soggettoDebitore.getNome());
	if (StringUtils.isNotBlank(StringUtils.defaultString(soggettoDebitore.getEmail()).trim())) {
	    soggetto.setEmail(soggettoDebitore.getEmail());
	}
	/*
	 * Questi campi per ora lasciamoli vuoti. AS IS non li abbiamo mai settati,
	 * ed effettuand vari test non sono stati necessari finora
	 */
	//		soggetto.setCap(soggettoDebitore.getCap());
	//		soggetto.setCivico(soggettoDebitore.getCivico());
	//		soggetto.setIndirizzo(soggettoDebitore.getVia());
	//		soggetto.setLocalita(soggettoDebitore.getLocalita());
	//		soggetto.setNazione(soggettoDebitore.getStato());
	//		soggetto.setProvincia(soggettoDebitore.getProvincia());
	//		soggetto.setRagioneSociale(null);		
	return soggetto;
    }

    private DettaglioDovuto popolaDettaglioDebitoRest(PayPosizioniDebitorie payPos, String codiceIpa) throws PayException {

	DettaglioDovuto deb = new DettaglioDovuto();
	deb.setCodiceIpaCreditore(codiceIpa);
	String causaleDebito = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(payPos,
		new ParametroDescrizioneCausalePSP());
	deb.setCausaleDebito(StringUtils.left(causaleDebito, 140));
	deb.setCodiceTipoDebito(posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos)); //Spostato da testata a qui    	
	BigDecimal importoTotale = BigDecimal.ZERO;
	Set<PayDettaglioImporti> dettagliImporto = payPos.getDettagliImporto();
	DatiAccertamento di = new DatiAccertamento();
	String stampaDettImporto = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(payPos,
		new ParametroInviaDettagliPagamento());
	log.debug("popolaDettaglioDebito - valore: {}", stampaDettImporto);
	for (PayDettaglioImporti imp : dettagliImporto) {
	    di = fromImportoRest(imp);
	    importoTotale = importoTotale.add(imp.getImporto());
	    // dettagli importo non li inserisco per Trieste in quanto è configurato un solo accertamento
	    if (stampaDettImporto != null && stampaDettImporto.equals("SI")) {
		//deb.setDettagliImporto(di); CtDettagliImporto 
		if (deb.getDatiAccertamento() == null) {
		    deb.setDatiAccertamento(new ArrayList<DatiAccertamento>());
		}
		deb.getDatiAccertamento().add(di);
	    }
	}
	deb.setIdDeb(payPos.getIdPosizionePsp());
	deb.setGruppo(String.valueOf(payPos.getNumRata()));
	deb.setOrdinamento(deb.getGruppo());//al momento abbiamo deciso di popolarlo con il valore del gruppo
	deb.setDataInizioValidita(Utilities.formatUTCJsonDate(payPos.getDataRegistrazione()));
	deb.setDataFineValidita(Utilities.formatUTCJsonDate(payPos.getDataScadenza()));
	//	DataLimitePagabilita Definizione
	//	Data che indica la data limite di pagabilità nel caso in cui la data di fine validità ricada in 
	//	una data in cui non sia possibile effettuare il pagamento. Se non impostato la data limite di pagabilità sarà 
	//	impostata uguale alla data di fine validità
	// eb.setDataLimitePagabilita(null); //CAPIRE SE GESTIRE CON PARAMETRO DELLA CAUSALE PER AGGIUNGERE GIORNI ALLA VALIDITA'
	deb.setImportoDebito(importoTotale);
	deb.setImportoPaFee(BigDecimal.ZERO);
	deb.setImportoSpeseNotifica(BigDecimal.ZERO);
	return deb;
    }

    private DatiAccertamento fromImportoRest(PayDettaglioImporti imp) {

	DatiAccertamento di = new DatiAccertamento(); //da ridiscuter per quanto letto su documentazione
	di.setCodiceAccertamento(imp.getNumeroAccertamento());
	di.setDescrizioneAccertamento(StringUtils.defaultIfEmpty(imp.getDescCausale(), imp.getNumeroAccertamento()));
	di.setImportoAccertamento(imp.getImporto());
	di.setAnnoAccertamento(String.valueOf(imp.getAnnoAccertamento()));
	return di;
    }

    private String getIdInstallazioneJCITY() throws PayException {

	String ret = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.ID_INSTALLAZIONE);
	if (StringUtils.isBlank(ret)) {
	    log.error("Non è stato configurato correttamente il id installazione ");
	    throw new PayConfigurationException("Non  stato configurato correttamente il idInstallazione");
	}
	return ret;
    }

    private String getLogo(byte[] logo) throws PayException {

	if (logo == null) {
	    log.error("Non è stato configurato correttamente il logo ");
	    throw new PayConfigurationException("Non è stato configurato correttamente il logo");
	}
	return Base64.encodeBase64String(logo);
    }
}
