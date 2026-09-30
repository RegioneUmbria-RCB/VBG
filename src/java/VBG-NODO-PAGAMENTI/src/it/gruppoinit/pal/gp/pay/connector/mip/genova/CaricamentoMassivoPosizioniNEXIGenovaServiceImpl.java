package it.gruppoinit.pal.gp.pay.connector.mip.genova;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.ElaborazioneResult;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.IInvioFlussoService;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.IPDFDebitoService;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.InvioFlussoResult;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.ParametriBean;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosDebMassive;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.parameters.ParametroDescrizioneCausalePSP;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayPosDebMassiveService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayRegistrazioniContabiliService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.CaricamentoMassivoStatiEnum;
import it.gruppoinit.pal.gp.pay.service.helper.EsitoElaborazione;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RiferimentiPosizioniDebitorieHelper;
import it.gruppoinit.pal.gp.pay.tracciati.RecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.StringRecordProperty;
import it.gruppoinit.pal.gp.pay.tracciati.TracciatoRecordSet;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.RipartizioneHelper;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordDebito;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordDebito.CAMPI_DEBITO;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordLotto;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordLotto.CAMPI_LOTTO;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordRata;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordRata.CAMPI_RATA;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordRipartizione;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordRipartizione.CAMPI_RIPARTIZIONE;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.sigeprosecurity.ws.ISecurityClient;
import it.gruppoinit.sigeprosecurity.ws.SecurityConfig;

@Service
public class CaricamentoMassivoPosizioniNEXIGenovaServiceImpl extends BaseOperazioniMassiveService
	implements ICaricamentoMassivoPosizioniNEXIGenovaService {

    private static final String FLAG_POSIZIONALE_TIPO_DOCUMENTO_PAGAMENTO = "00000020000000000000";
    private static final Logger log = LoggerFactory.getLogger(CaricamentoMassivoPosizioniNEXIGenovaServiceImpl.class);
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;
    private PayPosDebMassiveService payPosDebMassiveService;
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    private PayStatoPagamentiService payStatoPagamentiService;
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    private ILetturaEsitiNexiGenovaService letturaEsitiNexiGenovaService;
    private PayRegistrazioniContabiliService payRegistrazioniContabiliService;
    private ISecurityClient securityClient;
    private IPDFDebitoService pdfDebitoService;
    private IInvioFlussoService invioFlussoService;
    private static final String TIPO_CODICE_INTESTATARIO_CFPI = "3";
    private static final String NOME_COGNOME_SEPARATOR = "|";
    private static final String MODALITA_SPEDIZIONE_NO_MAV = "A";
    private static final String VERSIONE_SPECIFICHE = "04.05";

    @Autowired
    public void setPosizioniDebitorieCommandService(PosizioniDebitorieCommandService posizioniDebitorieCommandService) {

	this.posizioniDebitorieCommandService = posizioniDebitorieCommandService;
    }

    @Autowired
    public void setPayPosDebMassiveService(PayPosDebMassiveService payPosDebMassiveService) {

	this.payPosDebMassiveService = payPosDebMassiveService;
    }

    @Autowired
    public void setPayConnectorConfigValuesService(PayConnectorConfigValuesService payConnectorConfigValuesService) {

	this.payConnectorConfigValuesService = payConnectorConfigValuesService;
    }

    @Autowired
    public void setPayStatoPagamentiService(PayStatoPagamentiService payStatoPagamentiService) {

	this.payStatoPagamentiService = payStatoPagamentiService;
    }

    @Autowired
    public void setPayPosizioniDebitorieService(PayPosizioniDebitorieService payPosizioniDebitorieService) {

	this.payPosizioniDebitorieService = payPosizioniDebitorieService;
    }

    @Autowired
    public void setLetturaEsitiNexiGenovaService(ILetturaEsitiNexiGenovaService letturaEsitiNexiGenovaService) {

	this.letturaEsitiNexiGenovaService = letturaEsitiNexiGenovaService;
    }

    @Autowired
    public void setPayRegistrazioniContabiliService(PayRegistrazioniContabiliService payRegistrazioniContabiliService) {

	this.payRegistrazioniContabiliService = payRegistrazioniContabiliService;
    }

    @Autowired
    public void setSecurityClient(ISecurityClient securityClient) {

	this.securityClient = securityClient;
    }

    @Autowired
    public void setPdfDebitoService(IPDFDebitoService pdfDebitoService) {

	this.pdfDebitoService = pdfDebitoService;
    }

    @Autowired
    public void setInvioFlussoService(IInvioFlussoService invioFlussoService) {

	this.invioFlussoService = invioFlussoService;
    }

    @Override
    public EsitoElaborazione elaboraCaricamentoMassivoPosizioni(Map<String, String> params, IPayConnector connector) {

	if (params == null || params.isEmpty()) {
	    return new EsitoElaborazione(false, "Non sono stati passati i parametri di configurazione");
	}
	if (connector == null) {
	    return new EsitoElaborazione(false, "Non è stato passato il connettore da utilizzare");
	}
	boolean esito = true;
	// RECUPERA LE POSIZIONI DA CARICARE SU NEXI ==> pay_pos_deb_massive`.FLAG_PROCESSATA="I"
	Map<String, List<Integer>> daElaborare = payPosDebMassiveService.findPosizioniDaElaborare();
	BaseFolderCaricamento wsCaricamento = getWsCaricamentoMassivo(connector);
	if (wsCaricamento == null) {
	    return new EsitoElaborazione(false, "Non è stato impostato il wsCaricamenbtoMassivoConfig");
	}
	StringBuilder messaggi = new StringBuilder();
	// CARICA LE POSIZIONI SUDDIVISE PER GRUPPI
	for (Entry<String, List<Integer>> operazioni : daElaborare.entrySet()) {
	    log.info("elaboraCaricamentoMassivoPosizioni - elaboro le posizioni per idoperazione  {} - num posizioni {}", operazioni.getKey(),
		    operazioni.getValue().size());
	    ElencoPosizioniDebitorieEsitoType esiti = new ElencoPosizioniDebitorieEsitoType();
	    Map<String, PosizioniDebitorieMipHelperCommand> flussiCommands = null;
	    try {
		flussiCommands = this.predisponiEsitiESeparaFlussi(operazioni.getValue(), esiti);
	    } catch (PayConfigurationException e1) {
		String messaggio = "Errore in predisponiFlussi " + operazioni.getKey() + ": " + e1.getMessage();
		log.error(messaggio, e1);
		messaggi.append("\n").append(messaggio);
		esito = false;
		continue;
	    }
	    Iterator<String> tipiEntrata = flussiCommands.keySet().iterator();
	    while (tipiEntrata.hasNext()) {
		String te = tipiEntrata.next();
		log.info("elaboraCaricamentoMassivoPosizioni - trasmissione flussi per tipologia entrata: {}", te);
		PosizioniDebitorieMipHelperCommand phnc = flussiCommands.get(te);
		//phnc.getIdMessaggio() NON USATO
		PosizioniDebitorieCommand cmd = posizioniDebitorieCommandService.popolaPosizioniDebitorie(phnc.getRegistrazioniPosizioni(),
			phnc.getIdRichiesta());
		try {
		    this.caricaFlussoNexi(cmd, wsCaricamento, esiti);
		} catch (PayConfigurationException e) {
		    String messaggio = "Errore in caricaFlussi " + operazioni.getKey() + ": " + e.getMessage();
		    log.error(messaggio, e);
		    messaggi.append("\n").append(messaggio);
		    esito = false;
		    continue;
		}
		RiferimentiPosizioniDebitorieHelper esitoH = new RiferimentiPosizioniDebitorieHelper(esiti);
		for (PayRegistrazioniContabili regElaborata : phnc.getRegistrazioniPosizioni()) {
		    List<PayPosizioniDebitorie> posDebs = payPosizioniDebitorieService
			    .findByIdRegistrazioneContabile(regElaborata.getId().getCodice());
		    for (PayPosizioniDebitorie posElaborata : posDebs) {
			EsitoOperazionePosizioneDebitoriaType esitoPosDeb = (EsitoOperazionePosizioneDebitoriaType) esitoH
				.findRiferimentoPosizioneById(BigInteger.valueOf(posElaborata.getId().getCodice()));
			if (esitoPosDeb != null) {
			    try {
				aggiornaStatoPosizioneMassiva(esitoPosDeb, posElaborata, operazioni.getKey());
				payStatoPagamentiService.registraStatoPosizioneDebitoria(esitoPosDeb, posElaborata);
			    } catch (PayException e) {
				String messaggio = "Errore in registraStatoPosizioneDebitoria " + posElaborata.getId() + ": " + e.getMessage();
				log.error(messaggio, e);
				messaggi.append("\n").append(messaggio);
				esito = false;
			    }
			} else {
			    String messaggio = "Errore in registraStatoPosizioneDebitoria non trovato esito per posizione " + posElaborata.getId() +
					       ", operazione " + operazioni.getKey();
			    log.error(messaggio);
			    messaggi.append("\n").append(messaggio);
			    esito = false;
			}
		    }
		}
		log.info("elaboraCaricamentoMassivoPosizioni - trasmissione flussi per tipologia entrata: {}, idoperazione {} completata", te,
			operazioni.getKey());
	    }
	}
	// LEGGE GLI ESITI DEI FAIL CARICATI
	EsitoElaborazione esitoLettura = letturaEsitiNexiGenovaService.leggiEsiti(connector, params);
	if (!esitoLettura.isEsito()) {
	    esito = false;
	    messaggi.append(esitoLettura.getMessaggio());
	}
	return new EsitoElaborazione(esito, messaggi.toString());
    }

    private void aggiornaStatoPosizioneMassiva(EsitoOperazionePosizioneDebitoriaType esitoPosDeb, PayPosizioniDebitorie posElaborata,
	    String idoperazione) {

	String statoProcessamento = esitoPosDeb.isEsito() ? CaricamentoMassivoStatiEnum.PROCESSATO.getValore()
		: CaricamentoMassivoStatiEnum.SCARTATO.getValore();
	PayPosDebMassive pos = payPosDebMassiveService.findByIdPosizioneDebitoriaAndOperazione(posElaborata, idoperazione);
	pos.setFlagProcessata(statoProcessamento);
	if (!esitoPosDeb.isEsito()) {
	    pos.setMessaggio(esitoPosDeb.getMessaggio());
	} else {
	    pos.setMessaggio(null);
	}
	payPosDebMassiveService.update(pos);
    }

    private Map<String, PosizioniDebitorieMipHelperCommand> predisponiEsitiESeparaFlussi(List<Integer> posizioniDebitorie,
	    ElencoPosizioniDebitorieEsitoType esiti) throws PayConfigurationException {

	//le specifiche NeXI prevedono l'invio di flussi separati per tipologia entrata omogenea. 
	//La tipologia entrata è configurata in PAY_REGISTRAZIONI_CUSALI.CODICE_VERSAMENTO e quindi predispongo un caricamento FTP 
	//per ciascun codice versamento distinto che è presente nelle registrazioni contabili da elaborare
	Map<String, PosizioniDebitorieMipHelperCommand> flussiCommands = new HashMap<>();
	for (Integer posId : posizioniDebitorie) {
	    PayPosizioniDebitorie pos = payPosizioniDebitorieService.findById(new PkId(posId));
	    Integer idRegistrazioneContabile = pos.getRegistrazioneContabile().getId().getCodice();
	    String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(pos);
	    PosizioniDebitorieMipHelperCommand cmd = flussiCommands.get(codiceVersamento);
	    if (cmd == null) {
		cmd = new PosizioniDebitorieMipHelperCommand(null);
		flussiCommands.put(codiceVersamento, cmd);
	    }
	    PayRegistrazioniContabili prc = payRegistrazioniContabiliService.findById(new PkId(idRegistrazioneContabile));
	    cmd.getRegistrazioniPosizioni().add(prc);
	    EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
	    esitoPos.setEsito(true);
	    esitoPos.setIdPosizione(BigInteger.valueOf(pos.getId().getCodice()));
	    esitoPos.setIdRegistrazioneContabile(BigInteger.valueOf(pos.getRegistrazioneContabile().getId().getCodice()));
	    esitoPos.setStato(StatoPagamentoType.TRASMESSO_A_PSP);
	    esitoPos.setMessaggio(StatiPagamento.TRASMESSO_A_PSP.description());
	    esiti.getEsitoPosizione().add(esitoPos);
	}
	return flussiCommands;
    }

    /////////////////////////////////////////
    private void caricaFlussoNexi(PosizioniDebitorieCommand cmd, BaseFolderCaricamento endpoint, ElencoPosizioniDebitorieEsitoType esiti)
	    throws PayConfigurationException {

	TracciatoRecordSet<TracciatoRecordDebito> rsDebiti = new TracciatoRecordSet<>();
	TracciatoRecordSet<TracciatoRecordRata> rsRate = new TracciatoRecordSet<>();
	TracciatoRecordSet<TracciatoRecordRipartizione> rsRipartizioni = new TracciatoRecordSet<>();
	//1. Inizializzazione dei parametri
	ParametriBean parametri = this.inizializza(cmd, endpoint);
	String errorAll = parametri.getErrore();
	boolean errorAllTemp = parametri.getErroreTemporaneo() != null && Boolean.TRUE.equals(parametri.getErroreTemporaneo());
	BigDecimal totLotto = BigDecimal.ZERO;
	int numRate = 0;
	//ciclo le registrazioni->posizioni->dettagli_debito da elaborare
	Iterator<PayRegistrazioniContabili> regIter = cmd.getRegistrazioniPosizioni().iterator();
	int totaleRigheDebito = 0; // è successo che su una esportazione il totale righe debito del lotto 
				   // non era coerente con il numero dei del file dei debiti
	while (regIter.hasNext() && errorAll == null) {
	    PayRegistrazioniContabili registrazione = payRegistrazioniContabiliService.findById(new PkId(regIter.next().getId().getCodice()));
	    List<PayPosizioniDebitorie> posizioni = payPosizioniDebitorieService.findByIdRegistrazioneContabile(registrazione.getId().getCodice());
	    if (posizioni.isEmpty()) {
		break;
	    }
	    ElaborazioneResult result = this.elaboraRegistrazioneContabile(parametri, registrazione, posizioni, esiti);
	    errorAll = result.getErrore();
	    errorAllTemp = Boolean.TRUE.equals(result.getErroreTemporaneo());
	    //1. numero rate
	    numRate += result.getNumeroRate();
	    //2. ripartizioni
	    for (TracciatoRecordRipartizione ripartizione : result.getRipartizioni().getRecords()) {
		rsRipartizioni.getRecords().add(ripartizione);
	    }
	    //3. rate
	    for (TracciatoRecordRata rata : result.getRate().getRecords()) {
		rsRate.getRecords().add(rata);
	    }
	    //4. debiti
	    for (TracciatoRecordDebito debito : result.getDebiti().getRecords()) {
		rsDebiti.getRecords().add(debito);
	    }
	    //4.Verifico la presenza di errori
	    if (StringUtils.isBlank(result.getErrore())) {
		totaleRigheDebito++;
		totLotto = totLotto.add(result.getTotaleDebito());
	    }
	}
	//tracciato lotto
	if (errorAll == null && !rsDebiti.getRecords().isEmpty()) {
	    InvioFlussoResult result = this.inviaFlusso(parametri, numRate, totaleRigheDebito, totLotto, rsDebiti, rsRate, rsRipartizioni);
	    errorAll = result.getErrore();
	    errorAllTemp = Boolean.TRUE.equals(result.getErroreTemporaneo());
	}
	this.impostaErroriSuEsitiCaricamentoFlussiNexi(esiti, errorAll, errorAllTemp);
    }

    private InvioFlussoResult inviaFlusso(ParametriBean parametri, int numRate, int totaleRigheDebito, BigDecimal totLotto,
	    TracciatoRecordSet<TracciatoRecordDebito> rsDebiti, TracciatoRecordSet<TracciatoRecordRata> rsRate,
	    TracciatoRecordSet<TracciatoRecordRipartizione> rsRipartizioni) {

	try {
	    GeneraTracciatoLottoRequest request = new GeneraTracciatoLottoRequest();
	    request.setAccorpamento(false);
	    request.setCodiceEnte(parametri.getCodiceEnte());
	    request.setCodiceVersamento(parametri.getCodiceVersamento());
	    request.setColori(false); //se prevista stampa a colori
	    request.setDataCreazione(parametri.getFileHelper().getDataGenerazione());
	    request.setEnte(PayConfigurationHelper.getProfiloEnteCreditore());
	    request.setFlagTipoDocDebito(getFlagPosizionaleTipoDocumentoPagamento());
	    request.setFronteRetro(true); //se prevista stampa fronte/retro
	    request.setIdLotto(parametri.getIdLotto());
	    request.setNumRate(numRate);
	    request.setTipoCodiceEnte(TipiCodiceEnte.CODICE_BELFIORE);
	    request.setTipoOperazione(TipiOperazione.INSERT);
	    request.setTipoPost(TipoPostalizzazione.POSTA_MASSIVA);
	    request.setTotaleRigheDebito(totaleRigheDebito);
	    request.setTotLotto(totLotto);
	    request.setVettore(IdentificativoVettore.POSTE);
	    TracciatoRecordLotto trLotto = this.generaTracciatoLotto(request);
	    TracciatoRecordSet<TracciatoRecordLotto> rsFlusso = new TracciatoRecordSet<>();
	    rsFlusso.getRecords().add(trLotto);
	    /*
	     * (BaseFolderCaricamento config, TracciatoRecordSet<TracciatoRecordLotto> lotto, TracciatoRecordSet<TracciatoRecordDebito> debiti,
	    	TracciatoRecordSet<TracciatoRecordRata> rate, TracciatoRecordSet<TracciatoRecordRipartizione> ripartizioni, String connectorId,
	    	String idLotto) 
	     */
	    this.invioFlussoService.inviaFlusso(parametri.getBaseFolderCaricamento(), rsFlusso, rsDebiti, rsRate, rsRipartizioni,
		    parametri.getFileHelper().getConnectorId(), parametri.getIdLotto());
	    //parametri.getFileHelper().inviaFlusso(rsFlusso, rsDebiti, rsRate, rsRipartizioni);
	    return new InvioFlussoResult();
	} catch (PayException e) {
	    log.error("Errore nell'invio del flusso per il lotto {}", parametri.getIdLotto(), e);
	    EsitoOperazionePosizioneDebitoriaType esitoToCheckException = new EsitoOperazionePosizioneDebitoriaType();
	    this.handleException(e, esitoToCheckException);
	    return InvioFlussoResult.fromEsitoOperazionePosizioneDebitoriaType(esitoToCheckException);
	}
    }

    private GeneraTracciatoRecordDebito generaRichiestaDebito(ParametriBean parametri, PayRegistrazioniContabili registrazione, int totalePosizioni,
	    PayPosizioniDebitorie pos, PaySoggettiDebitori sogg, ElaborazioneResult result) {

	GeneraTracciatoRecordDebito req = new GeneraTracciatoRecordDebito();
	try {
	    String idDebito = idNexiDaPkNodo(registrazione.getId());
	    String urlbaseGeneraPdf = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.DOCUMENTI_SERVICE);
	    String token = parametri.getSecurityToken();
	    String connectorId = parametri.getFileHelper().getConnectorId();
	    PdfFile pdf = this.pdfDebitoService.generaPdfDebito(urlbaseGeneraPdf, token, pos, idDebito, connectorId, parametri.getIdLotto());
	    req.setIdDebito(idDebito);
	    req.setNomeDocumento(pdf.getNomeFile());
	    req.setNumPagine(pdf.getNumPagine());
	} catch (PayException e) {
	    EsitoOperazionePosizioneDebitoriaType info = new EsitoOperazionePosizioneDebitoriaType();
	    handleException(e, info);
	    result.setErrore(info.getMessaggio());
	}
	if (StringUtils.isNotBlank(result.getErrore()))
	    return req;
	// campi comuni
	req.setAnnoDebito(registrazione.getAnno());
	req.setDataEmissione(registrazione.getDataRegistrazione());
	req.setTipoCodiceIntestatario(TIPO_CODICE_INTESTATARIO_CFPI);
	req.setCodiceIntestatario(sogg.getCfPi());
	req.setTipoCodiceDebitore(TIPO_CODICE_INTESTATARIO_CFPI);
	req.setCodiceDebitore(sogg.getCfPi());
	req.setCodiceFiscaleDebitore(sogg.getCfPi());
	req.setIndirizzoDebitore(formatIndirizzoDebitore(sogg)); //la seconda parte dell'indirizzo non la gestiamo perché tanto serve solo per il MAV
	req.setCapDebitore(sogg.getCap());
	req.setComuneProvincia(sogg.getLocalita() + " " + sogg.getProvincia());
	req.setModalitaSpedizione(MODALITA_SPEDIZIONE_NO_MAV);
	req.setFlagPagamento(FlagPagamento.PAGABILE);
	req.setEmailDebitore(Utilities.validaIndirizzoMail(sogg.getEmail()) ? sogg.getEmail() : "");
	req.setFlagRateizzazione(
		totalePosizioni == 1 ? FlagRateizzazione.NON_RATEIZZATO : FlagRateizzazione.RATEIZZATO_IMPORTO_COMPLESSIVO_NON_PAGABILE);
	req.setNumeroRate(totalePosizioni == 1 ? 0 : totalePosizioni);
	req.setFlagRipartizione(FlagRipartizione.RIPARTIZIONE_SU_FLUSSO);
	req.setFlagTipoAccorpamento(FlagTipoAccorpamento.NON_ACCORPABILE);
	req.setDescrizione(registrazione.getDescrizione()); //la descrizione viene impostata col campo DESC_CAUSALE della registrazione contabile, se non impostato viene preso il campo DESCRIZIONE, se non impostato viene presa la DESCRIZIONE della causale registrazione
	req.setFlagPresenzaIndirizzo(FlagPresenzaIndirizzo.SI);
	req.setVersione(VERSIONE_SPECIFICHE);
	req.setIdentificativoLotto(parametri.getIdLotto());
	req.setCausaleVersamento(parametri.getDescrizioneCausale());
	req.setIntestatario(formatNomeCognome(sogg));
	return req;
    }

    private TracciatoRecordDebito generaTracciatoDebito(ParametriBean parametri, GeneraTracciatoRecordDebito req, PaySoggettiDebitori sogg) {

	TracciatoRecordDebito trDeb = this.generaTracciatoRecordDebito(parametri, req);
	String nome = formatNomeCognome(sogg);
	StringRecordProperty propNomePt1 = (StringRecordProperty) trDeb.getProperty(CAMPI_DEBITO.prima_parte_denominazione_debitore.name());
	StringRecordProperty propNomePt2 = (StringRecordProperty) trDeb.getProperty(CAMPI_DEBITO.seconda_parte_denominazione_debitore.name());
	if (nome.length() > propNomePt1.getLength()) {
	    propNomePt1.setValue(nome.substring(0, propNomePt1.getLength()));
	    propNomePt2.setValue(nome.substring(propNomePt1.getLength(), Math.min(nome.length(), propNomePt1.getLength() + propNomePt2.getLength())));
	} else {
	    propNomePt1.setValue(nome);
	}
	return trDeb;
    }

    private ElaborazioneResult elaboraRegistrazioneContabile(ParametriBean parametri, PayRegistrazioniContabili registrazione,
	    List<PayPosizioniDebitorie> posizioniDebitorie, ElencoPosizioniDebitorieEsitoType esiti) {

	ElaborazioneResult result = new ElaborazioneResult();
	PayPosizioniDebitorie pos = posizioniDebitorie.get(0);
	PaySoggettiDebitori sogg = pos.getSoggettoDebitore();
	//1. Validazione soggetto - tutte le posizioni debitorie hanno lo stesso soggetto debitore --> lo recupero dalla prima
	result.setErrore(this.validaDatiSoggetto(sogg));
	if (!StringUtils.isBlank(result.getErrore())) {
	    if (log.isWarnEnabled()) {
		log.warn("caricaFlussoNexi - la registrazione contabile {} non è stata trasmessa nel flusso a causa di un errore: {}",
			PkId.toStringId(registrazione.getId()), result.getErrore());
	    }
	    return result;
	}
	//2. Generazione request tracciato debito, PDF e idDebito  
	boolean errorDebitoTemp = false;
	GeneraTracciatoRecordDebito reqDebito = this.generaRichiestaDebito(parametri, registrazione, posizioniDebitorie.size(), pos, sogg, result);
	if (!StringUtils.isBlank(result.getErrore())) {
	    return result;
	}
	//3. Generazione tracciato debito e denominazione
	TracciatoRecordDebito trDeb = this.generaTracciatoDebito(parametri, reqDebito, sogg);
	//4. Generazione rate e ripartizioni
	result.setNumeroRate(posizioniDebitorie.size());
	Calendar cal = new GregorianCalendar(Locale.ITALY);
	cal.set(2099, 11, 31);
	Date defaultDataScadenza = cal.getTime();
	for (int i = 0; i < posizioniDebitorie.size(); i++) {
	    pos = posizioniDebitorie.get(i);
	    BigDecimal importoRata = BigDecimal.ZERO;
	    String idRata = null;
	    try {
		idRata = this.idNexiDaPkNodo(pos.getId());
		//imposto nella posizione l'identificativo usato da NEXI per poterla recuperare ed aggiornarne lo stato durante la lettura degli esiti
		pos.setIdPosizionePsp(idRata);
	    } catch (PayException e) {
		result.setErrore(e.getMessage());
	    }
	    Iterator<PayDettaglioImporti> dettRataIterator = pos.getDettagliImporto().iterator();
	    Map<String, RipartizioneHelper> mappaRipartizioni = new HashMap<>();
	    // LA RIPARTIZIONE VA RAGGRUPPATA PER ACCERTAMENTO
	    // SE DUE IMPORTI DI UNA RATA VANNO SU CONTI DIVERSI ALLORA SONO DUE RIGHE DI RIPARTIZIONE
	    // SE DUE IMPORTI HANNO UN ACCERTAMENTO ALLORA VA FATTA UNA RIGA DI RIPARTIZIONE CON LA SOMMA DI IMPORTI
	    while (dettRataIterator.hasNext() && StringUtils.isBlank(result.getErrore())) {
		PayDettaglioImporti dett = dettRataIterator.next();
		String kMap = getMapRipartizioniKey(dett);
		RipartizioneHelper trRipH = mappaRipartizioni.get(kMap);
		if (trRipH == null) {
		    //1. Genero la request
		    GeneraRipartizioneHelperRequest requestRH = new GeneraRipartizioneHelperRequest();
		    requestRH.setImporto(dett.getImporto());
		    requestRH.setAnnoDebito(registrazione.getAnno());
		    requestRH.setIdDebito(reqDebito.getIdDebito());
		    requestRH.setNumeroRata(posizioniDebitorie.size() == 1 ? 0 : i + 1);
		    requestRH.setIdRata(idRata);
		    requestRH.setTipoRipartizione(TipiRipartizione.ACCERTAMENTO);
		    requestRH.setNumeroAccertamento(dett.getNumeroAccertamento());
		    requestRH.setIdPosizioneDebitoria(pos.getId());
		    requestRH.setAnnoAccertamento(dett.getAnnoAccertamento());
		    requestRH.setNumeroSottoAccertamento(dett.getNumeroSottoAccertamento());
		    //2. Genero l'helper
		    trRipH = this.generaRipartizioneHelper(parametri, requestRH);
		    //3. Segno l'eventuale errore
		    result.setErrore(trRipH.getErroreDebito());
		} else {
		    trRipH.addImporto(dett.getImporto());
		}
		mappaRipartizioni.put(kMap, trRipH);
	    }
	    log.debug("Ciclo le ripartizioni della rata: {}", idRata);
	    for (Entry<String, RipartizioneHelper> ripartizioni : mappaRipartizioni.entrySet()) {
		log.debug("Ripartizione con chiave: {}", ripartizioni.getKey());
		RipartizioneHelper trRipH = ripartizioni.getValue();
		if (StringUtils.isBlank(trRipH.getErroreDebito())) {
		    trRipH.getTracciato().setValue(CAMPI_RIPARTIZIONE.importo_per_ripartizione.name(),
			    trRipH.getImporto().movePointRight(2).intValue());
		    log.debug("Ripartizione della rata {} con importo {}", idRata,
			    trRipH.getTracciato().getValue(CAMPI_RIPARTIZIONE.importo_per_ripartizione.name()));
		    trRipH.getTracciato().setValue(CAMPI_RIPARTIZIONE.versione_specifiche.name(), VERSIONE_SPECIFICHE);
		    result.getRipartizioni().getRecords().add(trRipH.getTracciato());
		    importoRata = importoRata.add(trRipH.getImporto());
		}
	    }
	    if (StringUtils.isBlank(result.getErrore())) {
		//1. Genero il tracciato della rata
		GeneraTracciatoRataRequest requestRata = new GeneraTracciatoRataRequest();
		requestRata.setAnnoDebito(registrazione.getAnno());
		requestRata.setIdDebito(reqDebito.getIdDebito());
		requestRata.setNumeroRata(posizioniDebitorie.size() == 1 ? 0 : i + 1);
		requestRata.setIdRata(idRata);
		requestRata.setDataScadenza(pos.getDataScadenza() != null ? pos.getDataScadenza() : defaultDataScadenza);
		requestRata.setDescrizioneRata(pos.getDescrizioneCausale());
		requestRata.setFlagPagamento(FlagPagamento.PAGABILE);
		requestRata.setVersione(VERSIONE_SPECIFICHE);
		requestRata.setImportoDaPagare(importoRata.movePointRight(2).intValue());
		result.getRate().getRecords().add(this.generaTracciatoRata(parametri, requestRata));
		result.addTotaleDebito(importoRata);
	    } else {
		//in caso di errore predispongo l'esito negativo per la posizione debitoria
		RiferimentiPosizioniDebitorieHelper esitiHelper = new RiferimentiPosizioniDebitorieHelper(esiti);
		EsitoOperazionePosizioneDebitoriaType esitoPos = (EsitoOperazionePosizioneDebitoriaType) esitiHelper
			.findRiferimentoPosizioneById(BigInteger.valueOf(pos.getId().getCodice()));
		esitoPos.setEsito(false);
		esitoPos.setMessaggio(result.getErrore());
		if (errorDebitoTemp) {
		    esitoPos.setErroreTemporaneo(true);
		    //in caso di annullamenti lo stato deve essere quello iniziale della posizione oppure null ad indicare al nodo 
		    //che lo stato della posizione non deve cambiare
		    esitoPos.setStato(StatoPagamentoType.ACQUISITO);
		} else {
		    esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
		}
		if (log.isInfoEnabled()) {
		    log.info("caricaFlussoNexi - errore nel caricamento del debito {}: {}", reqDebito.getIdDebito(), result.getErrore());
		}
	    }
	}
	if (StringUtils.isBlank(result.getErrore())) {
	    trDeb.setValue(CAMPI_DEBITO.importo_totale.name(), result.getTotaleDebito().movePointRight(2).intValue());
	    result.getDebiti().getRecords().add(trDeb);
	} else {
	    if (log.isWarnEnabled()) {
		log.warn("caricaFlussoNexi - la registrazione contabile {} non è stata trasmessa nel flusso a causa di un errore: {}",
			PkId.toStringId(registrazione.getId()), result.getErrore());
	    }
	}
	return result;
    }

    private ParametriBean inizializza(PosizioniDebitorieCommand cmd, BaseFolderCaricamento endpoint) throws PayConfigurationException {

	ParametriBean parametri = new ParametriBean();
	parametri.setBaseFolderCaricamento(endpoint);
	parametri.setTipoOperazione(TipiOperazione.INSERT);
	parametri.setTipoCodiceEnte(TipiCodiceEnte.CODICE_BELFIORE);
	parametri.setCodiceEnte(payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.MIPGE_CODICE_ENTE));
	////tutte le registrazioni contabili hanno lo stesso codice versamento --> lo recupero dalla prima
	parametri.setCodiceVersamento(posizioniDebitorieCommandService.findCodiceVersamentoFromCommand(cmd));
	parametri.setDescrizioneCausale(
		posizioniDebitorieCommandService.findValoreUnicoParametroFromCommand(cmd, new ParametroDescrizioneCausalePSP()));
	try {
	    parametri.setIdLotto(this.getIdLotto());
	    log.debug("idLotto {}", parametri.getIdLotto());
	    String invalidIdLotto = this.validateIdLotto(parametri.getIdLotto());
	    if (!StringUtils.isBlank(invalidIdLotto)) {
		parametri.setErrore(invalidIdLotto);
		return parametri;
	    }
	    String urlGeneraPdf = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.DOCUMENTI_SERVICE);
	    parametri.setFileHelper(new MipFileHelper(posizioniDebitorieCommandService.findCodiceVersamentoFromCommand(cmd), endpoint, urlGeneraPdf,
		    parametri.getIdLotto()));
	    //stacco il token di securiry
	    SecurityConfig securityConfig = SecurityConfig.fromPayConnectorConfigValuesService(payConnectorConfigValuesService);
	    parametri.setSecurityToken(this.securityClient.loginAPP(securityConfig));
	    if (log.isInfoEnabled()) {
		log.info("caricaFlussoNexi - staccato token da security: {}", parametri.getSecurityToken());
	    }
	} catch (Exception e) {
	    EsitoOperazionePosizioneDebitoriaType info = new EsitoOperazionePosizioneDebitoriaType();
	    handleException(e, info);
	    parametri.setErrore("Errore inizializzazione flusso NEXI: " + e.getMessage());
	    parametri.setErroreTemporaneo(info.isErroreTemporaneo());
	}
	return parametri;
    }

    private TracciatoRecordDebito generaTracciatoRecordDebito(ParametriBean parametri, GeneraTracciatoRecordDebito request) {

	TracciatoRecordDebito trDeb = new TracciatoRecordDebito();
	trDeb.setValue(CAMPI_DEBITO.nome_documento_debito.name(), request.getNomeDocumento());
	trDeb.setValue(CAMPI_DEBITO.numero_facciate_documento_debito.name(), request.getNumPagine());
	trDeb.setValue(CAMPI_DEBITO.tipo_operazione.name(), parametri.getTipoOperazione().value());
	trDeb.setValue(CAMPI_DEBITO.tipo_codice_ente.name(), parametri.getTipoCodiceEnte().value());
	trDeb.setValue(CAMPI_DEBITO.codice_ente.name(), parametri.getCodiceEnte());
	trDeb.setValue(CAMPI_DEBITO.tipologia_entrata.name(), parametri.getCodiceVersamento());
	trDeb.setValue(CAMPI_DEBITO.anno_debito.name(), request.getAnnoDebito());
	trDeb.setValue(CAMPI_DEBITO.identificativo_debito.name(), request.getIdDebito());
	trDeb.setValue(CAMPI_DEBITO.data_emissione_debito.name(), request.getDataEmissione());
	trDeb.setValue(CAMPI_DEBITO.tipo_codice_intestatario.name(), request.getTipoCodiceIntestatario());
	trDeb.setValue(CAMPI_DEBITO.codice_intestatario.name(), request.getCodiceIntestatario());
	trDeb.setValue(CAMPI_DEBITO.cognome_nome_intestatario.name(), request.getIntestatario());
	trDeb.setValue(CAMPI_DEBITO.tipo_codice_debitore.name(), request.getTipoCodiceDebitore());
	trDeb.setValue(CAMPI_DEBITO.codice_debitore.name(), request.getCodiceDebitore());
	trDeb.setValue(CAMPI_DEBITO.codice_fiscale_debitore.name(), request.getCodiceFiscaleDebitore());
	trDeb.setValue(CAMPI_DEBITO.indirizzo_debitore.name(), request.getIndirizzoDebitore());
	trDeb.setValue(CAMPI_DEBITO.cap_debitore.name(), request.getCapDebitore());
	trDeb.setValue(CAMPI_DEBITO.comune_provincia_debitore.name(), request.getComuneProvincia());
	trDeb.setValue(CAMPI_DEBITO.modalita_spedizione.name(), request.getModalitaSpedizione());
	trDeb.setValue(CAMPI_DEBITO.flag_pagamento.name(), request.getFlagPagamento().value());
	trDeb.setValue(CAMPI_DEBITO.flag_rateizzazione.name(), request.getFlagRateizzazione().value());
	trDeb.setValue(CAMPI_DEBITO.numero_rate.name(), request.getNumeroRate());
	trDeb.setValue(CAMPI_DEBITO.flag_ripartizione.name(), request.getFlagRipartizione().value());
	trDeb.setValue(CAMPI_DEBITO.flag_accorpamento.name(), request.getFlagTipoAccorpamento().value());
	trDeb.setValue(CAMPI_DEBITO.descrizione.name(), request.getDescrizione());
	trDeb.setValue(CAMPI_DEBITO.flag_presenza_indirizzo.name(), request.getFlagPresenzaIndirizzo().value());
	trDeb.setValue(CAMPI_DEBITO.nome_documento_allegato.name(), request.getNomeDocumentoAllegato());
	trDeb.setValue(CAMPI_DEBITO.indirizzo_email_debitore.name(), request.getEmailDebitore());
	trDeb.setValue(CAMPI_DEBITO.identificativo_lotto.name(), request.getIdentificativoLotto());
	trDeb.setValue(CAMPI_DEBITO.causale_versamento.name(), request.getCausaleVersamento());
	trDeb.setValue(CAMPI_DEBITO.versione_specifiche.name(), request.getVersione());
	return trDeb;
    }

    private TracciatoRecordRata generaTracciatoRata(ParametriBean parametri, GeneraTracciatoRataRequest request) {

	TracciatoRecordRata trRata = new TracciatoRecordRata();
	trRata.setValue(CAMPI_RATA.tipo_operazione.name(), parametri.getTipoOperazione().value());
	trRata.setValue(CAMPI_RATA.tipo_codice_ente.name(), parametri.getTipoCodiceEnte().value());
	trRata.setValue(CAMPI_RATA.codice_ente.name(), parametri.getCodiceEnte());
	trRata.setValue(CAMPI_RATA.tipologia_entrata.name(), parametri.getCodiceVersamento());
	trRata.setValue(CAMPI_RATA.anno_debito.name(), request.getAnnoDebito());
	trRata.setValue(CAMPI_RATA.identificativo_debito.name(), request.getIdDebito());
	trRata.setValue(CAMPI_RATA.numero_rata.name(), request.getNumeroRata());
	trRata.setValue(CAMPI_RATA.identificativo_rata.name(), request.getIdRata());
	trRata.setValue(CAMPI_RATA.data_scadenza.name(), request.getDataScadenza());
	trRata.setValue(CAMPI_RATA.descrizione_rata.name(), request.getDescrizioneRata());
	trRata.setValue(CAMPI_RATA.flag_pagabile.name(), request.getFlagPagamento().value());
	trRata.setValue(CAMPI_RATA.versione_specifiche.name(), request.getVersione());
	trRata.setValue(CAMPI_RATA.importo_da_pagare.name(), request.getImportoDaPagare());
	return trRata;
    }

    private RipartizioneHelper generaRipartizioneHelper(ParametriBean parametri, GeneraRipartizioneHelperRequest request) {

	RipartizioneHelper trRipH = new RipartizioneHelper(new TracciatoRecordRipartizione(), request.getImporto());
	trRipH.getTracciato().setValue(CAMPI_RIPARTIZIONE.tipo_operazione.name(), parametri.getTipoOperazione().value());
	trRipH.getTracciato().setValue(CAMPI_RIPARTIZIONE.tipo_codice_ente.name(), parametri.getTipoCodiceEnte().value());
	trRipH.getTracciato().setValue(CAMPI_RIPARTIZIONE.codice_ente.name(), parametri.getCodiceEnte());
	trRipH.getTracciato().setValue(CAMPI_RIPARTIZIONE.tipologia_entrata.name(), parametri.getCodiceVersamento());
	trRipH.getTracciato().setValue(CAMPI_RIPARTIZIONE.anno_debito.name(), request.getAnnoDebito());
	trRipH.getTracciato().setValue(CAMPI_RIPARTIZIONE.identificativo_debito.name(), request.getIdDebito());
	trRipH.getTracciato().setValue(CAMPI_RIPARTIZIONE.numero_rata.name(), request.getNumeroRata());
	trRipH.getTracciato().setValue(CAMPI_RIPARTIZIONE.identificativo_rata.name(), request.getIdRata());
	trRipH.getTracciato().setValue(CAMPI_RIPARTIZIONE.tipo_ripartizione.name(), request.getTipoRipartizione().value());
	//il codice ripartizione è un campo numerico perciò il campo del DB DEVE essere popolato con stringhe che possono essere parsate a int
	Integer numAcc = 0;
	if (StringUtils.isNotBlank(request.getNumeroAccertamento())) {
	    try {
		numAcc = Integer.parseInt(request.getNumeroAccertamento());
	    } catch (NumberFormatException e) {
		String msg = "il numero accertamento non contiene un valore numerico per la posizione debitoria " +
			     PkId.toStringId(request.getIdPosizioneDebitoria());
		log.error("caricaFlussoNexi - {}", msg, e);
		trRipH.setErroreDebito(msg);
	    }
	}
	trRipH.getTracciato().setValue(CAMPI_RIPARTIZIONE.codice_ripartizione.name(), numAcc);
	trRipH.getTracciato().setValue(CAMPI_RIPARTIZIONE.anno_riferimento_ripartizione.name(), request.getAnnoAccertamento());
	Integer numSottoAccertamento = null;
	if (StringUtils.isNotBlank(request.getNumeroSottoAccertamento())) {
	    try {
		numSottoAccertamento = Integer.parseInt(request.getNumeroSottoAccertamento());
	    } catch (NumberFormatException e) {
		String msg = "il numero sotto-accertamento non contiene un valore numerico per la posizione debitoria " +
			     PkId.toStringId(request.getIdPosizioneDebitoria());
		log.error("caricaFlussoNexi - {}", msg, e);
		trRipH.setErroreDebito(msg);
	    }
	}
	trRipH.getTracciato().setValue(CAMPI_RIPARTIZIONE.numero_sub_accertamento.name(), numSottoAccertamento);
	return trRipH;
    }

    @SuppressWarnings("unchecked")
    private TracciatoRecordLotto generaTracciatoLotto(GeneraTracciatoLottoRequest request) {

	TracciatoRecordLotto trLotto = new TracciatoRecordLotto();
	trLotto.setValue(CAMPI_LOTTO.tipo_operazione.name(), request.getTipoOperazione().value());
	trLotto.setValue(CAMPI_LOTTO.tipo_codice_ente.name(), request.getTipoCodiceEnte().value());
	trLotto.setValue(CAMPI_LOTTO.codice_ente.name(), request.getCodiceEnte());
	trLotto.setValue(CAMPI_LOTTO.tipologia_entrata.name(), request.getCodiceVersamento());
	trLotto.setValue(CAMPI_LOTTO.identificativo_lotto.name(), request.getIdLotto());
	trLotto.setValue(CAMPI_LOTTO.data_creazione_lotto.name(), request.getDataCreazione());
	trLotto.setValue(CAMPI_LOTTO.numero_totale_debiti.name(), request.getTotaleRigheDebito());
	trLotto.setValue(CAMPI_LOTTO.numero_totale_rate.name(), request.getNumRate());
	int totDebiti = request.getTotLotto().movePointRight(2).intValue();
	trLotto.setValue(CAMPI_LOTTO.importo_totale_debiti.name(), totDebiti);
	trLotto.setValue(CAMPI_LOTTO.importo_totale_rate.name(), totDebiti);
	trLotto.setValue(CAMPI_LOTTO.tipologia_documento_di_pag_da_emettere.name(), request.getFlagTipoDocDebito());
	trLotto.setValue(CAMPI_LOTTO.flag_accorpamento_debiti.name(),
		request.isAccorpamento() ? FlagAccorpamentoPerDestinatario.SI.value() : FlagAccorpamentoPerDestinatario.NO.value());
	trLotto.setValue(CAMPI_LOTTO.nome_documento_lotto.name(), " ");
	// SINIGAGLIA 
	// Nel file di flusso legato al Lotto, togliere il valore " MERCMERCIVARIEAVV_710100000000000 "
	// al campo 13 (pag.10 dell'allegato), che serve SOLO se si vuole una lettera d'accompagnamento unica per tutto il lotto 
	trLotto.setValue(CAMPI_LOTTO.flag_fronte_retro_lotto.name(), BooleanUtils.toInteger(request.isFronteRetro()) + "");
	trLotto.setValue(CAMPI_LOTTO.flag_bianco_nero_colore_lotto.name(), BooleanUtils.toInteger(request.isColori()) + "");
	trLotto.setValue(CAMPI_LOTTO.tipo_postalizzazione.name(), request.getTipoPost().value());
	trLotto.setValue(CAMPI_LOTTO.identificativo_vettore.name(), request.getVettore().value());
	String descEnte = null;
	if (request.getEnte().getAmministrazione() != null && request.getEnte().getAmministrazione().getId() != null
		&& request.getEnte().getAmministrazione().getId().getCodice() != null) {
	    Amministrazioni amministrazione = request.getEnte().getAmministrazione();
	    //descrizione ente facoltativa
	    descEnte = amministrazione.getAmministrazione();
	    RecordProperty<String> descProp = (RecordProperty<String>) trLotto.getProperty(CAMPI_LOTTO.prima_parte_denominazione_ente.name());
	    if (descEnte.length() > descProp.getLength()) {
		descProp.setValue(descEnte.substring(0, descProp.getLength()));
		trLotto.setValue(CAMPI_LOTTO.seconda_parte_denominazione_ente.name(),
			descEnte.substring(descProp.getLength(), descEnte.length() - descProp.getLength()));
	    } else {
		descProp.setValue(descEnte);
	    }
	    //campi facoltativi utili per la stampa dell'avviso
	    trLotto.setValue(CAMPI_LOTTO.indirizzo_ente.name(), amministrazione.getIndirizzo());
	    StringBuilder sbCapLocProv = new StringBuilder();
	    if (StringUtils.isNotBlank(amministrazione.getCap())) {
		sbCapLocProv.append(amministrazione.getCap()).append(" ");
	    }
	    if (StringUtils.isNotBlank(amministrazione.getCitta())) {
		sbCapLocProv.append(amministrazione.getCitta()).append(" ");
	    }
	    if (StringUtils.isNotBlank(amministrazione.getProvincia())) {
		sbCapLocProv.append(amministrazione.getProvincia());
	    }
	    trLotto.setValue(CAMPI_LOTTO.cap_localita_provincia_ente.name(), sbCapLocProv.toString());
	    trLotto.setValue(CAMPI_LOTTO.telefono_ente.name(),
		    StringUtils.isNotBlank(amministrazione.getTelefono1()) ? amministrazione.getTelefono1() : amministrazione.getTelefono2());
	}
	trLotto.setValue(CAMPI_LOTTO.versione_specifiche.name(), VERSIONE_SPECIFICHE);
	return trLotto;
    }

    private void impostaErroriSuEsitiCaricamentoFlussiNexi(ElencoPosizioniDebitorieEsitoType esiti, String errori, boolean erroriTemporanei) {

	if (StringUtils.isBlank(errori)) {
	    return;
	}
	List<EsitoOperazionePosizioneDebitoriaType> esitiPos = esiti.getEsitoPosizione();
	for (EsitoOperazionePosizioneDebitoriaType esitoPos : esitiPos) {
	    esitoPos.setEsito(false);
	    esitoPos.setMessaggio(errori);
	    esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
	    if (erroriTemporanei) {
		esitoPos.setErroreTemporaneo(true);
		// in caso di annullamenti lo stato deve essere quello iniziale della posizione oppure null ad indicare al nodo che lo stato della posizione non deve cambiare
		esitoPos.setStato(StatoPagamentoType.ACQUISITO);
	    }
	}
    }

    private String getIdLotto() {

	String centroDiCosto = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.CENTRO_DI_COSTO);
	String idNexi = this.idNexiDaMillis();
	return new IdLottoGenerator(idNexi, centroDiCosto).toString();
    }

    private String idNexiDaMillis() {

	return StringUtils.leftPad(Long.toString(System.currentTimeMillis()), 15, '0');
    }

    private String idNexiDaPkNodo(PkId pk) throws PayException {

	if (pk == null || pk.getCodice() == null) {
	    throw new PayException("Impossibile creare l'id NEXI perché non esiste nel DB del nodo pagamenti l'entità corrispondente.");
	}
	NumberFormat intFormat = NumberFormat.getIntegerInstance(Locale.ITALY);
	intFormat.setGroupingUsed(false);
	String idFlux = intFormat.format(pk.getCodice());
	idFlux = StringUtils.leftPad(idFlux, 15, '0');
	return idFlux;
    }

    /**
     * Validazione dell'd lotto, se non valido restituisce un messaggio di errore. non deve contenere il carattere "-"
     * non deve contenere caratteri che non siano validi nel nome dei files
     * 
     * @param idLotto
     * @return
     */
    private String validateIdLotto(String idLotto) {

	if (idLotto.contains("-")) {
	    return "L'id del lotto" + idLotto + "non è valido perché contiene il carattere '-'";
	}
	if (!IOUtils.isValidFileName(idLotto)) {
	    return "L'id del lotto" + idLotto + "non è valido perché contiene caratteri non consentiti all'interno del nome di un file";
	}
	return null;
    }

    protected void handleException(Throwable exc, EsitoOperazionePosizioneDebitoriaType esitoOp) {

	if (exc != null && esitoOp != null) {
	    esitoOp.setEsito(false);
	    esitoOp.setStato(StatoPagamentoType.CON_ERRORE);
	    Throwable rootExc = this.getRootCause(exc);
	    StringBuilder sb = new StringBuilder("Errore");
	    if (rootExc instanceof SocketException || rootExc instanceof UnknownHostException || rootExc instanceof UnknownServiceException
		    || rootExc instanceof SocketTimeoutException || rootExc instanceof MalformedURLException) {
		esitoOp.setErroreTemporaneo(true);
		sb.append(" temporaneo");
	    } else if (rootExc instanceof SOAPFaultException) {
		SOAPFaultException fault = (SOAPFaultException) rootExc;
		if (fault.getFault() != null) {
		    esitoOp.setCodiceErrore(fault.getFault().getFaultCode());
		}
	    } else if (exc instanceof PayException) {
		PayException payExc = (PayException) exc;
		esitoOp.setErroreTemporaneo(payExc.isResumable());
		esitoOp.setCodiceErrore(payExc.getErrorCode());
	    }
	    sb.append(": ").append(exc.toString());
	    esitoOp.setMessaggio(sb.toString());
	}
    }

    Throwable getRootCause(Throwable t) {

	while (t.getCause() != null) {
	    t = t.getCause();
	}
	return t;
    }

    /**
     * restituisce il valore del campo tipologia_documento_di_pag_da_emettere del tracciato record del lotto che
     * specifica quale tipo di documento di pagamento si desidera emettere. Per ora restituiamo fisso il valore
     * 00000020000000000000 che corrisponde all'avvisatura nodo PagoPA tramite postalizzatore (Extensis)
     */
    private String getFlagPosizionaleTipoDocumentoPagamento() {

	// implementare logiche che valorizzano il flag in base a valori di specifici parametri di configurazione del connettore
	return FLAG_POSIZIONALE_TIPO_DOCUMENTO_PAGAMENTO;
    }

    private String validaDatiSoggetto(PaySoggettiDebitori sogg) {

	if (StringUtils.isBlank(sogg.getVia())) {
	    return "indirizzo del soggetto debitore incompleto, via mancante";
	} else if (StringUtils.isBlank(sogg.getCap())) {
	    return "indirizzo del soggetto debitore incompleto, CAP mancante";
	} else if (StringUtils.isBlank(sogg.getLocalita())) {
	    return "indirizzo del soggetto debitore incompleto, comune mancante";
	} else {
	    //se è una persona giuridica (cf.length = 16) devono essere presenti sia il nome che il cognome
	    String cf = sogg.getCfPi();
	    if (StringUtils.isNotBlank(cf)) {
		if (cf.length() == 16 && StringUtils.isBlank(sogg.getCognome())) {
		    return "se il soggetto debitore è una persona fisica devono essere specificati sia il nome che il cognome";
		}
	    } else {
		return "identificativo fiscale del soggetto debitore mancante";
	    }
	}
	return null;
    }

    private String formatNomeCognome(PaySoggettiDebitori sogg) {

	StringBuilder sb = new StringBuilder();
	if (StringUtils.isNotBlank(sogg.getCognome())) {
	    sb.append(sogg.getCognome()).append(NOME_COGNOME_SEPARATOR);
	}
	//il nome c'è comunque e contiene la reagione sociale nel caso si tratti di persona giuridica
	sb.append(sogg.getNome());
	return sb.toString();
    }

    private String formatIndirizzoDebitore(PaySoggettiDebitori deb) {

	StringBuilder sb = new StringBuilder(StringUtils.defaultString(deb.getVia()));
	if (StringUtils.isNotBlank(deb.getCivico())) {
	    sb.append(" ");
	    sb.append(deb.getCivico());
	}
	return sb.toString();
    }

    private String getMapRipartizioniKey(PayDettaglioImporti dett) {

	return dett.getNumeroAccertamento() + "-" + StringUtils.defaultString(dett.getNumeroSottoAccertamento()) + "-" + dett.getAnnoAccertamento();
    }
}
