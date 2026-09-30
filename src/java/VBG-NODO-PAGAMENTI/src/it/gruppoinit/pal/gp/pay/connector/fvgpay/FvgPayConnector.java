package it.gruppoinit.pal.gp.pay.connector.fvgpay;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.FVGPayConstants.ElencoModelliPagamentoEnum;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.exceptions.ProblemException;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.BasicAuthParams;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.FvgPayClient;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.FvgPayClientParams;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.AvvisoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.DatiSpecificiRiscossioneRendicontazioneAscotType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.DatiUpdatePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.DettaglioPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.DettaglioRichiestaPagamentoImmediatoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.DettaglioRichiestaPagamentoRendicontazioneAscotType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.DettaglioRichiestaVocePagamentoRendicontazioneAscotType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.DettaglioVocePagamentoImmediatoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.EsitoValidazioneType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.IdentificativoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.IdentificativoUnivocoPagatoreType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.IdentificativoUnivocoPagatoreType.TipoIdentificativoUnivocoEnum;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.ImportoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.ManagementOptionsRendicontazioneAscotType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.ManagementOptionsTypeModelliPagamento;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.OkMessage;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.Problem;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.RecapitiTelematiciType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.RecapitoPostaleType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.RecapitoTelematicoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.RichiestaPagamentoImmediatoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.RichiestaPosizioneDebitoriaRendicontazioneAscotType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.SoggettoPagatoreType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.StatoPagamentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.xsd.daticontabili.Accertamento;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.xsd.daticontabili.CodBilancio;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.xsd.daticontabili.DatiContabili;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.service.FvgPayBackendService;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.parameters.ParametroAggiungiGiorniADataScadenzaAvviso;
import it.gruppoinit.pal.gp.pay.parameters.ParametroCodiceTassonomia;
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
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.HttpMethodType;
import it.gruppoinit.pal.gp.pay.ws.schema.ImportoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;

public class FvgPayConnector extends AbstractPayConnector implements IPayConnector {

    private static final String ESITO = "esito";
    private static final String ID_PAGAMENTO_OTF = "idPagamentoOtf";
    private static final String ANONIMO = "ANONIMO"; // Codice identificativo univoco: Codice Fiscale, Partita IVA o 'ANONIMO'
    private static final String CF_ANONIMO = "XYZXYZ80A01H501C"; // Codice identificativo univoco: Codice Fiscale, Partita IVA o 'ANONIMO'
    private static final Logger log = LoggerFactory.getLogger(FvgPayConnector.class);
    private static final String VALUTA_EUR = "EUR";
    private static final String ID_SESSIONE = "idSessione";
    private static final String MOTIVO = "motivo";
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    @Autowired
    private PayStatoPagamentiService pagamenPayStatoPagamentiService;
    @Autowired
    private PaySessioniPagamentoService paySessioniPagamentoService;
    @Autowired
    private FvgPayBackendService fvgPayBackendService;
    @Autowired
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	FvgPayClient client = getClient(this.getWsCaricamentoConfig());
	ElencoPosizioniDebitorieEsitoType result = new ElencoPosizioniDebitorieEsitoType();
	Map<EsitoOperazionePosizioneDebitoriaType, RichiestaPosizioneDebitoriaRendicontazioneAscotType> mDaInviare = new HashMap<EsitoOperazionePosizioneDebitoriaType, RichiestaPosizioneDebitoriaRendicontazioneAscotType>();
	boolean almenoUnErroreValidazione = false;
	String codiceTassonomia = posizioniDebitorieCommandService.findValoreUnicoParametroFromCommand(datiRegistrazioniCommand,
		new ParametroCodiceTassonomia());
	String aggiungiGiorniADataScadenzaAvviso = posizioniDebitorieCommandService.findValoreUnicoParametroFromCommand(datiRegistrazioniCommand,
		new ParametroAggiungiGiorniADataScadenzaAvviso());
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		RichiestaPosizioneDebitoriaRendicontazioneAscotType pendenza = popolaRichiestaAscot(payPos, codiceTassonomia,
			aggiungiGiorniADataScadenzaAvviso);
		this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(payPos, null, null);
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		esitoPos.setEsito(false);
		esitoPos.setErroreTemporaneo(false);
		esitoPos.setCodiceErrore("Validazione posizioni ASCOT");
		esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
		try {
		    EsitoValidazioneType ret = client.validaCaricamentoPosizioneDebitoriaAscot(pendenza);
		    if (!ret.isEsito()) {
			almenoUnErroreValidazione = true;
			String motivi = "";
			List<String> motiviRifiuto = ret.getMotiviRifiuto();
			if (motivi != null) {
			    for (String m : motiviRifiuto) {
				motivi = m + ", \n";
			    }
			}
			esitoPos.setMessaggio(motivi);
		    }
		} catch (ProblemException e) {
		    almenoUnErroreValidazione = true;
		    log.error("Errore nell' inserimento della pendenza: {}", e);
		    String messaggio = getMessaggioFromProblem(e);
		    esitoPos.setMessaggio(messaggio);
		}
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		mDaInviare.put(esitoPos, pendenza);
	    }
	}
	if (almenoUnErroreValidazione) {
	    String errori = "Errore nella validazione delle richieste di pagamento";
	    for (Entry<EsitoOperazionePosizioneDebitoriaType, RichiestaPosizioneDebitoriaRendicontazioneAscotType> entry : mDaInviare.entrySet()) {
		// result.getEsitoPosizione().add(entry.getKey());
		if (StringUtils.isNotEmpty(entry.getKey().getMessaggio())) {
		    errori += entry.getKey().getMessaggio() + "\n\n";
		}
	    }
	    throw new PayException(errori);
	}
	// la validazione formale è passata creo le 
	for (Entry<EsitoOperazionePosizioneDebitoriaType, RichiestaPosizioneDebitoriaRendicontazioneAscotType> entry : mDaInviare.entrySet()) {
	    RichiestaPosizioneDebitoriaRendicontazioneAscotType pendenza = entry.getValue();
	    EsitoOperazionePosizioneDebitoriaType esitoPos = entry.getKey();
	    PayPosizioniDebitorie payPos = payPosizioniDebitorieService.findById(new PkId(esitoPos.getIdPosizione().intValue()));
	    // RichiestaPosizioneDebitoriaRendicontazioneAscotType pendenza = popolaRichiestaAscot(payPos);
	    // this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(payPos, null, null);
	    // EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
	    try {
		OkMessage ret = client.registraPagamento(pendenza);
		esitoPos.setEsito(true);
		esitoPos.setStato(StatoPagamentoType.ACQUISITO);
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		log.debug("registraPosizioniDebitorie {} - {}", ret, pendenza);
	    } catch (ProblemException e) {
		log.error("Errore nell' inserimento della pendenza: {}", e);
		esitoPos.setEsito(false);
		String messaggio = getMessaggioFromProblem(e);
		esitoPos.setMessaggio(messaggio);
		esitoPos.setStato(StatoPagamentoType.NON_ACQUISITO);
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	    }
	    result.getEsitoPosizione().add(esitoPos);
	}
	return result;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException {

	FvgPayClient client = getClient(this.getWsAnnullamentoConfig());
	ElencoPosizioniDebitorieEsitoType result = new ElencoPosizioniDebitorieEsitoType();
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		EsitoOperazionePosizioneDebitoriaType esito = new EsitoOperazionePosizioneDebitoriaType();
		esito.setIdPosizione(BigInteger.valueOf(payPos.getId().getCodice()));
		PayStatoPagamenti payStato = this.pagamenPayStatoPagamentiService.getStatoPosizioneDebitoria(payPos);
		esito.setStato(StatoPagamentoType.fromValue(payStato.getStato()));
		result.getEsitoPosizione().add(esito);
		try {
		    client.annullaPosizione(payPos.getIdPosizionePsp());
		    esito.setStato(pagatoOffline ? StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO : StatoPagamentoType.ANNULLATO);
		    esito.setEsito(true);
		    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, payPos,
			    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		} catch (ProblemException e) {
		    this.handleException(e, esito);
		}
	    }
	}
	return result;
    }

    @Override
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	List<PaySessioniPagamento> sessioneTrovata = new ArrayList<>();
	String[] idPagamentoOtf = reqParams.get(ID_PAGAMENTO_OTF);
	String[] idSessioneVal = reqParams.get(ID_SESSIONE);
	String[] esitoVal = reqParams.get(ESITO);
	String[] motivoVal = reqParams.get(MOTIVO);
	log.debug("idPagamentoOtf {}", (Object[]) idPagamentoOtf);
	log.debug("idSessione {}", (Object[]) idSessioneVal);
	log.debug("esitoVal {}", (Object[]) esitoVal);
	String idSessione = null;
	String esito = null;
	String motivo = null;
	if (idSessioneVal != null && idSessioneVal.length > 0) {
	    idSessione = idSessioneVal[0];
	}
	if (esitoVal != null && esitoVal.length > 0) {
	    esito = esitoVal[0];
	}
	if (motivoVal != null && motivoVal.length > 0) {
	    motivo = motivoVal[0];
	}
	if (StringUtils.isNotBlank(idSessione)) {
	    sessioneTrovata = this.paySessioniPagamentoService.findBySessionId(idSessione);
	}
	boolean isEsitoSuccess = "1".equalsIgnoreCase(esito);
	log.debug("idSessione={}, esito={}, motivo={}", idSessione, esito, motivo);
	if (!sessioneTrovata.isEmpty()) {
	    for (PaySessioniPagamento paySessioniPagamento : sessioneTrovata) {
		PayPosizioniDebitorie posizioneDebitoria = payPosizioniDebitorieService
			.findById(new PkId(paySessioniPagamento.getPosizioneDebitoria().getId().getCodice()));
		if (BooleanUtils.isTrue(posizioneDebitoria.getFlagOTF()) && !isEsitoSuccess) {
		    // annullo le posizioni debitorie
		    log.debug("annullo la posizione debitoria con ID = {}", posizioneDebitoria.getId());
		    this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(posizioneDebitoria, StatiPagamento.ANNULLATO,
			    "Posizione OTF annullata da gestisci sessione con esito " + esito);
		}
		paySessioniPagamento.setEsito("1".equals(esito));
		this.paySessioniPagamentoService.update(paySessioniPagamento);
	    }
	    return sessioneTrovata.get(0); // ne ritorno una perché la redirect è sempre quella
	}
	return null;
    }

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

	List<PosizioneDebitoriaType> rate = registrazioneContabile.getRate().getRata();
	// RECUPERA LA CAUSALE DI DEFAULT
	//	if (registrazioneContabile.getCausale().getId() == null) {
	//	    throw new ValidazionePosizioniDebitorieException(
	//		    "Il codice versamento è obbligatorio. Riferimento posizione debitoria: " + registrazioneContabile.getDescrizione());
	//	}
	for (PosizioneDebitoriaType rata : rate) {
	    if (rata.getDataScadenza() == null) {
		throw new ValidazionePosizioniDebitorieException(
			"La data di scadenza è obbligatoria. Riferimento posizione debitoria: " + registrazioneContabile.getDescrizione());
	    }
	    // data scadenza maggiore dell'odierna
	    for (ImportoPagamentoType importoPagamento : rata.getImporto().getComponenteImporto()) {
		if (importoPagamento.getNumeroAccertamento() == null) {
		    throw new ValidazionePosizioniDebitorieException(
			    "Il numero accertamento è obbligatorio.Riferimento posizione debitoria: " + registrazioneContabile.getDescrizione());
		}
		if (importoPagamento.getDescrizioneCausale() == null) {
		    throw new ValidazionePosizioniDebitorieException(
			    "La causale è obbligatoria.Riferimento posizione debitoria: " + registrazioneContabile.getDescrizione());
		}
	    }
	}
	SoggettoDebitoreType sd = registrazioneContabile.getSoggettoDebitore();
	// sono obbligatorie le informazioni relative alla localizzazione
	// indirizzo, località, provincia, cap, stato (stato non l'abbiamo e dobbiamo impostarlo a IT fisso)
	if (StringUtils.isBlank(sd.getVia()) || StringUtils.isBlank(sd.getProvincia()) || StringUtils.isBlank(sd.getLocalita())
		|| StringUtils.isBlank(sd.getCap())) {
	    throw new ValidazionePosizioniDebitorieException(
		    "Non sono state specificate le informazioni postali del debitore (indirizzo, città, provincia, cap): "
			    + registrazioneContabile.getDescrizione());
	}
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
	String codiceTassonomia = posizioniDebitorieCommandService.findValoreUnicoParametroFromCommand(cmd, new ParametroCodiceTassonomia());
	String aggiungiGiorniADataScadenzaAvviso = posizioniDebitorieCommandService.findValoreUnicoParametroFromCommand(cmd,
		new ParametroAggiungiGiorniADataScadenzaAvviso());
	for (PayRegistrazioniContabili prc : payRegistrazioniContabilis) {
	    payPosizioniDebitorie.addAll(prc.getPosizioniDebitorie());
	}
	try {
	    String idSessione = ORMHelper.getIdcomune() + "_" + UUID.randomUUID().toString();
	    RichiestaPagamentoImmediatoType pagamentoImmediatoType = popolaNuovoPagamentoImmediato(payPosizioniDebitorie, idSessione,
		    codiceTassonomia, aggiungiGiorniADataScadenzaAvviso);
	    for (PayPosizioniDebitorie pd : payPosizioniDebitorie) {
		this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(pd, null, null);
	    }
	    FvgPayClient client = getClient(this.getWsAttivaSessioneConfig());
	    OkMessage pagamentoImmediato = client.pagamentoImmediato(pagamentoImmediatoType);
	    log.debug("OkMessage {}", pagamentoImmediato);
	    attivaSessioneOTF.setPayUrl(pagamentoImmediato.getMessage());
	    attivaSessioneOTF.setEsito(true);
	    attivaSessioneOTF.setIdSessione(idSessione);
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
    public AttivaSessionePagamentoResponseType attivaSessionePagamento(PayPosizioniDebitorie payPos) throws PayException {

	log.debug("uso il servizio della classe astratta per la sessione di pagamento");
	AttivaSessionePagamentoResponseType response = super.attivaSessionePagamento(payPos);
	response.setHttpMethodRequired(HttpMethodType.POST);
	return response;
    }

    @Override
    public boolean supportaAttivaSessionePagamento() {

	// al momento il pagamento modello 3 è un link dove l'utente può pagare online
	// http://ponline.regione.fvg.it/FVGPaymentGateway/Login
	return true;
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
			FvgPayClient client = getClient(this.getWsAvvisoConfig());
			log.debug("inviaAvvisiPagamento invoco il metodo getAvviso {}-{}-{}", ente.getCfCodiceProfiloPSP(), pos.getCodiceAvviso(),
				pos.getId().getCodice());
			AvvisoType avviso = client.generaBollettino(pos.getIdPosizionePsp());
			log.debug("inviaAvvisiPagamento chiamata effettuata con successo per la posizione debitoria {}", pos.getId().getCodice());
			esitoDoc.setEsito(true);
			if (StringUtils.isBlank(pos.getIuv())) {
			    esitoDoc.setNomeDocumento("AVVISO_" + pos.getIdPosizionePsp() + ".pdf");
			} else {
			    esitoDoc.setNomeDocumento("AVVISO_" + pos.getIuv() + ".pdf");
			}
			ByteArrayDataSource bs = new ByteArrayDataSource(avviso.getAvviso(), "application/pdf");
			esitoDoc.setDocumento(new DataHandler(bs));
		    } catch (ProblemException e) {
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

    /**
     * 
     * <PRE>
    
    "stato_pagamento": "PAGATO CON ERRORE",
    Significa che il pagamento è andato a buon fine ma il nostro sistema non è riuscito a comunicare la ricevuta telematica  
    l'elenco esiti
    
    IN ATTESA DI PAGAMENTO = Pendenza non pagata
    PAGATO = Pendenza pagata
    Pagato extra sistema = pendenza pagata fuori nodo (esempio bonifico)
    Pagato con errore = pendenza pagata ma non comunicata al verticale
    Parzialmente pagato = Riguardano le rate. Significa che ci sono ancora rate da pagare
    Non approvato = pendenza non pagata, ancora pagabile. Per qualche motivo non hanno approvato la transazione
    Abbandonato = pendenza non pagata , ancora pagabile . Per qualche motivo l’utente abbandona il pagamento
    In elaborazione = Pendenza non ancora elaborata o in fase di modifica( esempio modifica importo)
    Errore= ci sono tante casistiche quindi da analizzare singolarmente
    Cancellata = pendenza cancellata
    
    E nello stato della ricevuta_pagamento l'esito è con successo
    "ricevuta_pagamento": {
                    "data_ora_messaggio_ricevuta": "2021-11-16",
                    "identificativo_messaggio_ricevuta": "0e9d65c8dba74159b2ae88140b219b1f",
                    "esito_pagamento": "0",
    Esito_pagamento nella ricevuta telematica significa che il pagameto è andato a buon fine
     * 
     * 
     * </PRE>
     * 
     * 
     * <pre>
     *  
    0. Pagamento eseguito 
    1. Pagamento non eseguito 
    2. Pagamento parzialmente eseguito 
    3. Decorrenza termini 
    4. Decorrenza termini parziale
     * </pre>
     * 
     * @author riccardob
     *
     */
    @Override
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	// INVOCA api PENDENZE
	// GET
	// ​/pendenze​/{idA2A}​/{idPendenza}
	// Aggiornamento di uno o più campi di una pendenza
	ElencoStatoPosizioniType result = new ElencoStatoPosizioniType();
	FvgPayClient client = getClient(this.getWsVerificaConfig());
	for (PayPosizioniDebitorie payPos : cmd.getPosizioni()) {
	    StatoPosizioneType retStatus = new StatoPosizioneType();
	    retStatus.setEsito(false);
	    try {
		DettaglioPosizioneDebitoriaType pendenza = null;
		if (BooleanUtils.isTrue(payPos.getFlagOTF())) {
		    pendenza = client.verificaPagamentoOTF(payPos.getIdPosizionePsp());
		} else {
		    pendenza = client.getStatoPosizioneDebitoria(payPos.getIdPosizionePsp());
		}
		log.debug("pendenza {}", pendenza);
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		StatoPagamentoPosizioneDebitoriaType spd = pendenza.getStatoPagamentoPosizioneDebitoria();
		if (spd != null) {
		    retStatus = fvgPayBackendService.gestisciStatoPagamento(payPos, spd);
		}
	    } catch (ProblemException e) {
		retStatus.setEsito(false);
		this.handleException(e, retStatus);
	    }
	    result.getStatoPosizioni().add(retStatus);
	}
	return result;
    }

    @Override
    public void modificaDataScadenzaPosizioneDebitoria(Integer idPosizioneDebitoria, Date nuovaDataScadenza) throws PayException {

	log.debug("modificaDataScadenzaPosizioneDebitoria {}-{}", idPosizioneDebitoria, nuovaDataScadenza);
	PayPosizioniDebitorie payPos = payPosizioniDebitorieService.findById(new PkId(idPosizioneDebitoria));
	log.debug("modificaDataScadenzaPosizioneDebitoria prima di create il client aggiornaPosizioneDebitoria {}-{}", idPosizioneDebitoria,
		nuovaDataScadenza);
	FvgPayClient client = getClient(this.getWsCaricamentoConfig());
	DatiUpdatePosizioneDebitoriaType updatePos = popolaAggiornamentoDati(payPos, nuovaDataScadenza);
	try {
	    client.aggiornaPosizioneDebitoria(payPos.getIdPosizionePsp(), updatePos);
	} catch (ProblemException e) {
	    String messaggio = "Errore nella modifica della data di scadenza della posizione debitoria " + payPos.getId()
		    + ". Si sono verificati problemi a raggiungere il servizio modificaPagamentiAttesi a causa di " + e.getMessage() + "\n"
		    + e.getProblem();
	    log.error(messaggio);
	    throw new PayException(messaggio, e);
	}
    }

    @Override
    public boolean supportaModificaDataScadenza() {

	return true;
    }

    @Override
    public boolean supportaPagamentoOnTheFly() {

	return true;
    }

    private String getMessaggioFromProblem(ProblemException e) {

	String ret = e.getMessage();
	Problem p = e.getProblem();
	if (p != null) {
	    ret = p.getDetail();
	    if (p.getErrorInfos() != null && p.getErrorInfos().size() > 0) {
		for (String err : p.getErrorInfos()) {
		    ret += "\n" + err + ";";
		}
	    }
	}
	return ret;
    }

    private DatiUpdatePosizioneDebitoriaType popolaAggiornamentoDati(PayPosizioniDebitorie payPos, Date nuovaDataScadenza) throws PayException {

	DatiUpdatePosizioneDebitoriaType dati = new DatiUpdatePosizioneDebitoriaType();
	dati.setCausale(payPos.getRegistrazioneContabile().getDescrizione());
	dati.setDataScadenzaPagamento(nuovaDataScadenza);
	String aggiungiGGADataScadenza = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(payPos,
		new ParametroAggiungiGiorniADataScadenzaAvviso());
	if (StringUtils.isNotBlank(aggiungiGGADataScadenza)) {
	    aggiungiGGADataScadenza = aggiungiGGADataScadenza.trim();
	    if (Utilities.isInteger(aggiungiGGADataScadenza)) {
		Integer ggDaAggiungere = Integer.parseInt(aggiungiGGADataScadenza);
		Date dataScadenza = payPos.getDataScadenza();
		Date dataScadenzaAvviso = Utilities.addDays(dataScadenza, ggDaAggiungere.intValue());
		log.debug("la casuale prevede l'aggiornamento della data scadenza avviso [{}]-[{}]-[{}]", dataScadenza, ggDaAggiungere,
			dataScadenzaAvviso);
		dati.setDataScadenzaAvviso(dataScadenzaAvviso);
	    }
	} else {
	    dati.setDataScadenzaAvviso(payPos.getDataScadenza());
	}
	BigDecimal importoPos = BigDecimal.ZERO;
	for (PayDettaglioImporti payImporto : payPos.getDettagliImporto()) {
	    importoPos = importoPos.add(payImporto.getImporto());
	}
	dati.setImporto(popolaImporto(importoPos));
	return dati;
    }

    private RichiestaPosizioneDebitoriaRendicontazioneAscotType popolaRichiestaAscot(PayPosizioniDebitorie payPos, String codiceTassonomia,
	    String aggiungiGiorniADataScadenzaAvviso) {

	RichiestaPosizioneDebitoriaRendicontazioneAscotType pendenza = new RichiestaPosizioneDebitoriaRendicontazioneAscotType();
	pendenza.setDettaglioRichiestaPagamento(popolaDettaglioRichiesta(payPos, codiceTassonomia, aggiungiGiorniADataScadenzaAvviso));
	IdentificativoPosizioneDebitoriaType ipd = popolaIdentificativoPosizioneDebitoria(payPos);
	payPos.setIdPosizionePsp(ipd.getIdDebito());
	pendenza.setIdentificativoPosizioneDebitoria(ipd);
	ManagementOptionsRendicontazioneAscotType managementOption = new ManagementOptionsRendicontazioneAscotType();
	popolaManagementOption(managementOption, payPos);
	pendenza.setManagementOptions(managementOption);
	SoggettoPagatoreType soggettoPagatore = popolaSoggettoPagatore(payPos);
	pendenza.setSoggettoPagatore(soggettoPagatore);
	return pendenza;
    }

    private FvgPayClient getClient(PayConnectorWsEndpoint wsConfig) {

	BasicAuthParams basic = new BasicAuthParams(wsConfig.getPassword(), wsConfig.getUtente());
	PayProfiliEntiCreditori profEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	FvgPayClientParams params = new FvgPayClientParams(wsConfig.getEndpointUrl(), basic, getIdEnte(profEnte), getIdServizio(profEnte));
	return new FvgPayClient(params);
    }

    private String getIdEnte(PayProfiliEntiCreditori profEnte) {

	return profEnte.getCfCodiceProfiloPSP();
    }

    private String getIdServizio(PayProfiliEntiCreditori profEnte) {

	return profEnte.getIdAppPSP();
    }

    private RichiestaPagamentoImmediatoType popolaNuovoPagamentoImmediato(List<PayPosizioniDebitorie> payPosizioniDebitorie, String idSessione,
	    String codiceTassonomia, String aggiungiGiorniADataScadenzaAvviso) {

	RichiestaPagamentoImmediatoType pagamentoImmediatoType = new RichiestaPagamentoImmediatoType();
	SoggettoPagatoreType soggPagatore = null;
	String identificativoDebito = null;
	String idEnte = null;
	String idServizio = null;
	String descrizionePagamento = null;
	DettaglioRichiestaPagamentoImmediatoType dettRicPagImm = new DettaglioRichiestaPagamentoImmediatoType();
	boolean usaAutenticazione = true;
	String usaAutenticazioneInPagamentoImmediato = this.payConnectorConfigValuesService
		.getValoreParametroConfigurazione(ConfigParamNames.FVG_PAY_USA_AUTH_PAG_IMMED);
	if (StringUtils.defaultString(usaAutenticazioneInPagamentoImmediato, "true").equalsIgnoreCase("false")) {
	    usaAutenticazione = false;
	}
	dettRicPagImm.setAutenticazione(usaAutenticazione);// 
	String urlAnnullamento = null;
	String urlEsito = null;
	BigDecimal importoTotale = BigDecimal.ZERO;
	List<DettaglioVocePagamentoImmediatoType> list = new ArrayList<DettaglioVocePagamentoImmediatoType>();
	DettaglioVocePagamentoImmediatoType dettVoce = new DettaglioVocePagamentoImmediatoType();
	DatiSpecificiRiscossioneRendicontazioneAscotType dr = new DatiSpecificiRiscossioneRendicontazioneAscotType();
	dr.setTassonomia(codiceTassonomia);
	dettVoce.setDatiSpecificiRiscossione(dr);
	for (PayPosizioniDebitorie payPosDebitoria : payPosizioniDebitorie) {
	    if (urlAnnullamento == null) {
		urlAnnullamento = payPosDebitoria.getProfiloEnte().getUrlAnnullamentoPagamento();
		urlEsito = payPosDebitoria.getProfiloEnte().getUrlEsitoPagamento();
	    }
	    if (descrizionePagamento == null) {
		descrizionePagamento = payPosDebitoria.getRegistrazioneContabile().getDescrizione();
	    }
	    if (identificativoDebito == null) {
		identificativoDebito = getIdDebitoForPagamentoOTF(payPosDebitoria.getRegistrazioneContabile());
	    }
	    if (soggPagatore == null) {
		soggPagatore = popolaSoggettoPagatore(payPosDebitoria);
	    }
	    if (idEnte == null) {
		idEnte = getIdEnte(payPosDebitoria.getProfiloEnte());
		idServizio = getIdServizio(payPosDebitoria.getProfiloEnte());
	    }
	    for (PayDettaglioImporti dettImporto : payPosDebitoria.getDettagliImporto()) {
		importoTotale = importoTotale.add(dettImporto.getImporto());
	    }
	    payPosDebitoria.setIdPosizionePsp(identificativoDebito);
	}
	dettVoce.setCausale(descrizionePagamento);
	dettRicPagImm.setDatiContabili(popolaDatiContabili(payPosizioniDebitorie));
	dettVoce.setImporto(popolaImporto(importoTotale));
	list.add(dettVoce);
	dettRicPagImm.setDettaglioVociPagamento(list);
	dettRicPagImm.setImportoTotale(popolaImporto(importoTotale));
	dettRicPagImm.setDescrizionePagamento(descrizionePagamento);
	dettRicPagImm.setUrlAbbandono(urlAnnullamento + "&" + ID_PAGAMENTO_OTF + "=" + identificativoDebito + "&" + ID_SESSIONE + "=" + idSessione
		+ "&" + MOTIVO + "=ABBANDONO");
	dettRicPagImm.setUrlKo(
		urlAnnullamento + "&" + ID_PAGAMENTO_OTF + "=" + identificativoDebito + "&" + ID_SESSIONE + "=" + idSessione + "&" + MOTIVO + "=KO");
	dettRicPagImm.setUrlOk(urlEsito + "&" + ID_PAGAMENTO_OTF + "=" + identificativoDebito + "&" + ID_SESSIONE + "=" + idSessione);
	pagamentoImmediatoType.setDettaglioRichiestaPagamentoImmediato(dettRicPagImm);
	IdentificativoPosizioneDebitoriaType ipd = popolaIdentificativoPosizioneDebitoria(idEnte, idServizio, identificativoDebito);
	pagamentoImmediatoType.setIdentificativoPosizioneDebitoria(ipd);
	pagamentoImmediatoType.setSoggettoPagatore(soggPagatore);
	return pagamentoImmediatoType;
    }

    private String getIdDebitoForPagamentoOTF(PayRegistrazioniContabili registrazioneContabile) {

	return "OTF-" + registrazioneContabile.getId().getIdcomune() + "-" + registrazioneContabile.getId().getCodice();
    }

    private SoggettoPagatoreType popolaSoggettoPagatore(PayPosizioniDebitorie payPosDebitoria) {

	SoggettoPagatoreType pagatore = new SoggettoPagatoreType();
	String cfPi = StringUtils.defaultString(payPosDebitoria.getSoggettoDebitore().getCfPi());
	IdentificativoUnivocoPagatoreType identificativoUnivoco = new IdentificativoUnivocoPagatoreType();
	if (cfPi.length() == 11) {
	    identificativoUnivoco.setTipoIdentificativoUnivoco(TipoIdentificativoUnivocoEnum.G);
	    identificativoUnivoco.setCodiceIdentificativoUnivoco(cfPi);
	    pagatore.setCognome(payPosDebitoria.getSoggettoDebitore().getNome());
	} else if (cfPi.length() == 16) {
	    identificativoUnivoco.setTipoIdentificativoUnivoco(TipoIdentificativoUnivocoEnum.F);
	    identificativoUnivoco.setCodiceIdentificativoUnivoco(cfPi);
	    pagatore.setCognome(payPosDebitoria.getSoggettoDebitore().getCognome());
	    pagatore.setNome(payPosDebitoria.getSoggettoDebitore().getNome());
	} else {
	    identificativoUnivoco.setTipoIdentificativoUnivoco(TipoIdentificativoUnivocoEnum.A);
	    identificativoUnivoco.setCodiceIdentificativoUnivoco(CF_ANONIMO);
	    pagatore.setCognome(ANONIMO);
	    pagatore.setNome(ANONIMO);
	}
	pagatore.setIdentificativoUnivoco(identificativoUnivoco);
	pagatore.setRecapitoPostale(popolaRecapitoPostale(payPosDebitoria.getSoggettoDebitore()));
	pagatore.setRecapitoTelematico(popolaRecapitoTelematico(payPosDebitoria.getSoggettoDebitore()));
	return pagatore;
    }

    private RecapitiTelematiciType popolaRecapitoTelematico(PaySoggettiDebitori soggettoDebitore) {

	RecapitiTelematiciType ret = null;
	if (StringUtils.isNotBlank(soggettoDebitore.getEmail())) {
	    ret = new RecapitiTelematiciType();
	    RecapitoTelematicoType rt = new RecapitoTelematicoType();
	    rt.setTipo("mail");
	    rt.setRecapito(soggettoDebitore.getEmail());
	    if (ret.getElenco() == null) {
		ret.setElenco(new ArrayList<RecapitoTelematicoType>());
	    }
	    ret.addElencoItem(rt);
	}
	return ret;
    }

    private RecapitoPostaleType popolaRecapitoPostale(PaySoggettiDebitori paySoggettiDebitori) {

	// non lo possiamo passare perché nazione è uno dei dati obbligatori e non lo gestiamo 
	RecapitoPostaleType postaleType = new RecapitoPostaleType();
	postaleType.setIndirizzo(paySoggettiDebitori.getVia());
	postaleType.setLocalita(paySoggettiDebitori.getLocalita());
	postaleType.setProvincia(paySoggettiDebitori.getProvincia());
	postaleType.setCap(paySoggettiDebitori.getCap());
	postaleType.setNazione("IT"); //TODO: rivedere perché noi non lo salviamo
	return postaleType;
    }

    private IdentificativoPosizioneDebitoriaType popolaIdentificativoPosizioneDebitoria(PayPosizioniDebitorie payPosDebitoria) {

	IdentificativoPosizioneDebitoriaType ipd = new IdentificativoPosizioneDebitoriaType();
	ipd.setIdentificativoBeneficiario(getIdEnte(payPosDebitoria.getProfiloEnte()));
	ipd.setIdServizio(getIdServizio(payPosDebitoria.getProfiloEnte()));
	ipd.setIdDebito(getIdDebito(payPosDebitoria));
	return ipd;
    }

    private IdentificativoPosizioneDebitoriaType popolaIdentificativoPosizioneDebitoria(String idEnte, String idServizio, String idDebito) {

	IdentificativoPosizioneDebitoriaType ipd = new IdentificativoPosizioneDebitoriaType();
	ipd.setIdentificativoBeneficiario(idEnte);
	ipd.setIdServizio(idServizio);
	ipd.setIdDebito(idDebito);
	return ipd;
    }

    private String getIdDebito(PayPosizioniDebitorie payPosDebitoria) {

	return payPosDebitoria.getId().getIdcomune() + "_" + payPosDebitoria.getId().getCodice();
    }

    private void popolaManagementOption(ManagementOptionsRendicontazioneAscotType managementOption, PayPosizioniDebitorie payPosDebitoria) {

	// managementOption.setDataPubblicazioneAvviso(payPosDebitoria.getDataGenerazioneFattura());
	//Data in cui l'avviso di pagamento deve essere reso disponibile L'avviso di pagamento non viene reso disponibile fino alla data eventualmente specificata Se non viene specificata, l'avviso viene reso disponibile immediatamente Quando un avviso viene pubblicato viene reso disponibile sul FrontEnd Utente e viene notificato in forma digitale ed analogica, da quel momento viene quindi reso disponibile per il pagamento - usare il formato ISO 8601 (YYYY-MM-DD) - deve essere precedente alla data di scadenza del pagamento 	
	// RichiestaRateizzazioneRendicontazioneAscotType richiestaRateizzazione = new RichiestaRateizzazioneRendicontazioneAscotType();
	// popolaRichiestaRateizzazione(richiestaRateizzazione, payPosDebitoria);
	ManagementOptionsTypeModelliPagamento modelliPagamento = popolaModelliPagamento(payPosDebitoria);
	managementOption.setModelliPagamento(modelliPagamento);
	// managementOption.setRichiestaRateizzazione(richiestaRateizzazione);
    }

    private ManagementOptionsTypeModelliPagamento popolaModelliPagamento(PayPosizioniDebitorie payPosDebitoria) {

	ManagementOptionsTypeModelliPagamento mo = new ManagementOptionsTypeModelliPagamento();
	List<String> elencoList = new ArrayList<String>();
	elencoList.add(ElencoModelliPagamentoEnum.UNO.valore());
	elencoList.add(ElencoModelliPagamentoEnum.TRE.valore());
	mo.setElenco(elencoList);
	return mo;
    }

    private ImportoType popolaImporto(BigDecimal payImporto) {

	ImportoType importoType = new ImportoType();
	importoType.setImporto(payImporto.doubleValue());
	importoType.setValuta(VALUTA_EUR);
	return importoType;
    }

    private DettaglioRichiestaPagamentoRendicontazioneAscotType popolaDettaglioRichiesta(PayPosizioniDebitorie payPosDebitoria,
	    String codiceTassonomia, String aggiungiGGADataScadenza) {

	DettaglioRichiestaPagamentoRendicontazioneAscotType ascotType = new DettaglioRichiestaPagamentoRendicontazioneAscotType();
	ascotType.setScadenzaPagamento(payPosDebitoria.getDataScadenza());
	if (StringUtils.isNotBlank(aggiungiGGADataScadenza)) {
	    aggiungiGGADataScadenza = aggiungiGGADataScadenza.trim();
	    if (Utilities.isInteger(aggiungiGGADataScadenza)) {
		Integer ggDaAggiungere = Integer.parseInt(aggiungiGGADataScadenza);
		Date dataScadenza = payPosDebitoria.getDataScadenza();
		Date dataScadenzaAvviso = Utilities.addDays(dataScadenza, ggDaAggiungere.intValue());
		log.debug("la casuale prevede l'aggiornamento della data scadenza avviso [{}]-[{}]-[{}]", dataScadenza, ggDaAggiungere,
			dataScadenzaAvviso);
		ascotType.setScadenzaAvviso(dataScadenzaAvviso);
	    }
	} else {
	    ascotType.setScadenzaAvviso(payPosDebitoria.getDataScadenza());
	}
	ascotType.setDescrizionePagamento(StringUtils.right(payPosDebitoria.getRegistrazioneContabile().getDescrizione(), 50).trim());
	List<DettaglioRichiestaVocePagamentoRendicontazioneAscotType> list = new ArrayList<>();
	BigDecimal importoTotale = BigDecimal.ZERO;
	DettaglioRichiestaVocePagamentoRendicontazioneAscotType dettVoce = new DettaglioRichiestaVocePagamentoRendicontazioneAscotType();
	DatiSpecificiRiscossioneRendicontazioneAscotType dr = new DatiSpecificiRiscossioneRendicontazioneAscotType();
	dr.setTassonomia(codiceTassonomia);
	dettVoce.setDatiSpecificiRiscossione(dr);
	dettVoce.setCausale(StringUtils.right(payPosDebitoria.getRegistrazioneContabile().getDescrizione(), 50).trim());
	for (PayDettaglioImporti dettImporto : payPosDebitoria.getDettagliImporto()) {
	    importoTotale = importoTotale.add(dettImporto.getImporto());
	}
	List<PayPosizioniDebitorie> l = new ArrayList<>();
	l.add(payPosDebitoria);
	ascotType.setDatiContabili(popolaDatiContabili(l));
	dettVoce.setImporto(popolaImporto(importoTotale));
	list.add(dettVoce); // ne posso avere solamente una
	ascotType.setDettaglioVociPagamento(list);
	ascotType.setImportoTotale(popolaImporto(importoTotale));
	return ascotType;
    }

    private byte[] popolaDatiContabili(List<PayPosizioniDebitorie> payPoss) {

	Map<String, List<Accertamento>> accertamentiPerBilancio = new HashMap<>();
	List<PayDettaglioImporti> dettagliImporto = new ArrayList<PayDettaglioImporti>();
	for (PayPosizioniDebitorie payPosizioniDebitorie : payPoss) {
	    dettagliImporto.addAll(payPosizioniDebitorie.getDettagliImporto());
	}
	for (PayDettaglioImporti di : dettagliImporto) {
	    String codBilancio = di.getDatiRiscossione();
	    List<Accertamento> list = accertamentiPerBilancio.get(codBilancio);
	    if (list == null) {
		list = new ArrayList<>();
	    }
	    Accertamento a = new Accertamento();
	    a.setCodAccertamento(di.getNumeroAccertamento());
	    a.setImporto(di.getImporto().doubleValue());
	    list.add(a);
	    accertamentiPerBilancio.put(codBilancio, list);
	}
	DatiContabili dc = new DatiContabili();
	for (Entry<String, List<Accertamento>> b : accertamentiPerBilancio.entrySet()) {
	    String codBilancio = b.getKey();
	    List<Accertamento> accertamenti = b.getValue();
	    CodBilancio cb = new CodBilancio();
	    cb.setCodCapitolo(codBilancio);
	    double importoTotale = 0d;
	    for (Accertamento acc : accertamenti) {
		importoTotale = importoTotale + acc.getImporto();
	    }
	    cb.getAccertamento().addAll(accertamenti);
	    dc.getCodBilancio().add(cb);
	}
	String dati = IOUtils.marshallObject(dc);
	try {
	    return dati.getBytes("UTF-8");
	} catch (UnsupportedEncodingException e) {
	    return dati.getBytes();
	}
    }

    @Override
    public boolean supportaMolteCausaliRaggruppate() {

	return false;
    }
    //    public static void main(String[] args) {
    //
    //	FvgPayConnector c = new FvgPayConnector();
    //	Set<PayDettaglioImporti> s = new HashSet<>();
    //	s.add(c.createDettaglioImporti("1", 100d, 1));
    //	s.add(c.createDettaglioImporti("1", 50d, 2));
    //	s.add(c.createDettaglioImporti("2", 105d, 3));
    //	System.out.println(new String(c.popolaDatiContabili(s)));
    //    }
    //
    //    private PayDettaglioImporti createDettaglioImporti(String string, double d, int id) {
    //
    //	PayDettaglioImporti pd = new PayDettaglioImporti();
    //	pd.setId(new PkId("LOCAL", id));
    //	pd.setAnnoAccertamento(2021);
    //	pd.setDatiRiscossione(string);
    //	pd.setNumeroAccertamento("numero " + id);
    //	pd.setImporto(BigDecimal.valueOf(d));
    //	return pd;
    //    }

    @Override
    public List<IParameter> getListaParametriRichiesti() {

	return ParametriConnettoreHelper.getMappaParametriConnettore(this.getClass().getName());
    }

    @Override
    public IUVHelper generaIUV(PayPosizioniDebitorie pos, String identificativoCausalePerCalcolo) {

	return null;
    }
}
