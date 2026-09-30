/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.activation.DataHandler;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.DatiPagamentoCommand;
import it.gruppoinit.pal.gp.pay.command.GenerazioneFattureCommand;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaFatturaCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.dao.PayPosizioniDebitorieDAO;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayDocumenti;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PayRichieste;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.domain.TipiEvento;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.exception.PayInvalidRequestException;
import it.gruppoinit.pal.gp.pay.service.AttivazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.NotificheRabbitService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorService;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayRegistrazioniCausaliService;
import it.gruppoinit.pal.gp.pay.service.PayRegistrazioniContabiliService;
import it.gruppoinit.pal.gp.pay.service.PayRichiesteService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PaySoggettiDebitoriService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.RegistrazioniContabiliConverterService;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RegistrazioniContabiliAccorpamentoParser;
import it.gruppoinit.pal.gp.pay.service.helper.RichiesteHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RiferimentiPosizioniDebitorieHelper;
import it.gruppoinit.pal.gp.pay.service.helper.StatoPosizioneDebitoriaHelper;
import it.gruppoinit.pal.gp.pay.service.helper.TipoCaricamentoPosizionidebitorie;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiFatturaType;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.DettaglioStatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.DocumentiPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPagamentiOfflineType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.HttpMethodType;
import it.gruppoinit.pal.gp.pay.ws.schema.ModificaDataFineValiditaType;
import it.gruppoinit.pal.gp.pay.ws.schema.ModificaDataScadenzaType;
import it.gruppoinit.pal.gp.pay.ws.schema.OperazionePosizioniDebitorieResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.PagamentoOfflineType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaWsInType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileWsInType;
import it.gruppoinit.pal.gp.pay.ws.schema.RiferimentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.VerificaStatoPosizioniResponseType;

/**
 * @author francol
 *
 */
@Service
public class AttivazionePagamentiServiceImpl implements AttivazionePagamentiService {

    private static final Logger log = LoggerFactory.getLogger(AttivazionePagamentiServiceImpl.class);
    @Autowired
    private PayConnectorService payConnectorService;
    @Autowired
    private PayRegistrazioniContabiliService payRegistrazioniContabiliService;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PaySessioniPagamentoService paySessioniPagamentoService;
    @Autowired
    private PayPagamentiService payPagamentiService;
    @Autowired
    private PaySoggettiDebitoriService paySoggettiDebitoriService;
    @Autowired
    private PayRichiesteService payRichiesteService;
    @Autowired
    private RegistrazioniContabiliConverterService registrazioniContabiliConverterService;
    @Autowired
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;
    @Autowired
    private NotificheRabbitService notificheRabbitService;
    @Autowired
    private PayRegistrazioniCausaliService payRegistrazioniCausaliService;
    /*
     * si inietta un riferimento allo stesso service perchè abbiamo bisogno di invocare un'altro metodo della stessa
     * classe in un'altra transazione e per far ciò c'è bisogno che Spring crei il proxy AOP. Se invochiamo
     * this.metodoTransazionale() non funziona perché il metodo chiamato condivide la transazione del chiamante.
     */
    @Autowired
    private AttivazionePagamentiService attivazionePagamentiService;
    @Autowired
    private PayPosizioniDebitorieDAO payPosizioniDebitorieDAO;

    @SuppressWarnings("unused")
    @Override
    public OperazionePosizioniDebitorieResponseType caricaPosizioniDebitorie(TipoCaricamentoPosizionidebitorie tipocaricamento,
	    List<RegistrazioneContabileWsInType> regContabili, PayConfigurationHelper payCfg, boolean accorpaPosizioni, String oggettoPagamento)
	    throws PayException {

	List<PayRegistrazioniContabili> payRegs = new ArrayList<>();
	OperazionePosizioniDebitorieResponseType response = new OperazionePosizioniDebitorieResponseType();
	ElencoPosizioniDebitorieEsitoType esitiRegistrazioni = new ElencoPosizioniDebitorieEsitoType();
	List<PayRichieste> payReqs = new ArrayList<>();
	boolean allDone = true;
	boolean someDone = false;
	List<RegistrazioneContabileWsInType> regContabiliElaborate = new RegistrazioniContabiliAccorpamentoParser(payRegistrazioniCausaliService).gestisciAccorpamento(accorpaPosizioni, regContabili, oggettoPagamento);
	//loggo evento dell'avvenuta invocazione del servizio in nuova transazione
	for (RegistrazioneContabileWsInType regContabileWsIn : regContabiliElaborate) {
	    //registrazione interna al nodo delle posizioni debitorie e della registrazione contabile. 
	    //Un'istanza popolata di PayRegistrazioniContabili viene restituita comunque nell'esito anche se non corrisponde ad un record inserito nel db (id.codice == null).
	    boolean done = true;
	    try {
		RegistrazioneContabileType regCont = registrazioniContabiliConverterService.completaRegistrazioneContabile(regContabileWsIn);
		//validazioni generali dei dati del debito da caricare
		this.validateRichiestaInserimentoPosizioniDebitorie(regCont);
		//validazioni specifiche del connettore dei dati del debito da caricare
		this.payConnectorService.getPayConnectorInstance().validateRichiestaInserimentoPosizioniDebitorie(regCont);
		PayRegistrazioniContabili payRegCont = attivazionePagamentiService.richiestaCaricamentoPosizioniECommittaDati(regCont,
			TipiEvento.REGISTRA_POSIZIONI, payCfg, false);
		payRegs.add(payRegCont);
		//per ciascuna posizione debitoria registrata con successo 
		for (PayPosizioniDebitorie payPosDeb : payRegCont.getPosizioniDebitorie()) {
		    //predispongo l'esito da restituire al chiamante che sarà aggiornato in base all'esito della chiamata al connettore
		    EsitoOperazionePosizioneDebitoriaType esitoPosizione = new EsitoOperazionePosizioneDebitoriaType();
		    esitoPosizione.setIdPosizione(BigInteger.valueOf(payPosDeb.getId().getCodice()));
		    Set<String> riferimentiClientPosizione = payPosizioniDebitorieService
			    .findRiferimentiClientByPosizioneDebitoria(payPosDeb.getId().getCodice());
		    if (!riferimentiClientPosizione.isEmpty()) {
			esitoPosizione.getRiferimentoClient().addAll(riferimentiClientPosizione);
		    }
		    esitoPosizione.setEsito(true);
		    esitoPosizione.setStato(StatoPagamentoType.ACQUISITO);
		    if (payRegCont.getId() != null && payRegCont.getId().getCodice() != null) {
			esitoPosizione.setIdRegistrazioneContabile(BigInteger.valueOf(payRegCont.getId().getCodice()));
		    }
		    esitoPosizione.setMessaggio(StatiPagamento.ACQUISITO.description());
		    esitiRegistrazioni.getEsitoPosizione().add(esitoPosizione);
		    if (payPosDeb.recuperaRichiestaCorrente() != null && payPosDeb.getId() != null && payPosDeb.getId().getCodice() != null) {
			payReqs.add(payPosDeb.recuperaRichiestaCorrente());
		    }
		}
	    } catch (Exception e) {
		for (PosizioneDebitoriaWsInType datiPos : regContabileWsIn.getRate()) {
		    log.error("registraPosizioniDebitorie() - errore nell'acquisizione della registrazione contabile: ", e);
		    EsitoOperazionePosizioneDebitoriaType esitoKo = new EsitoOperazionePosizioneDebitoriaType();
		    esitoKo.setEsito(false);
		    esitoKo.setStato(StatoPagamentoType.NON_ACQUISITO);
		    esitoKo.setMessaggio(StatiPagamento.CON_ERRORE.description() + ": " + StringUtils.defaultString(e.getMessage()));
		    Set<String> riferimentiClientPosizione = new HashSet<String>();
		    if (!datiPos.getRiferimentiClient().isEmpty()) {
			esitoKo.getRiferimentoClient().addAll(datiPos.getRiferimentiClient());
		    }
		    esitiRegistrazioni.getEsitoPosizione().add(esitoKo);
		    done = false;
		}
	    }
	    allDone = allDone && done;
	}
	//delego le chiamate dei servizi del PSP al connettore attivo per il profilo ente creditore corrente
	boolean trasmesse = false;
	if (!payRegs.isEmpty()) {
	    PosizioniDebitorieCommand connectorCommand = posizioniDebitorieCommandService.popolaPosizioniDebitorie(payRegs,
		    this.generaCodiceComunicazione(TipiEvento.REGISTRA_POSIZIONI));
	    checkSupportaMolteCausaliRaggupate(connectorCommand, this.payConnectorService.getPayConnectorInstance());
	    ElencoPosizioniDebitorieEsitoType resposePsp = new ElencoPosizioniDebitorieEsitoType();
	    TipiEvento tipoServizio = null;
	    if (!payReqs.isEmpty()) {
		tipoServizio = TipiEvento.fromValue(payReqs.get(0).getTipoRichiesta());
	    }
	    if (tipocaricamento.isCaricamentoMassivo()) {
		payConnectorService.registraCaricamentoMassivoPosizioniDebitorie(connectorCommand, tipocaricamento.getIdentificativoOperazione());
	    } else {
		if (!this.payConnectorService.isServizioSoloSchedulato(tipoServizio)) {
		    //invoco il WS del PSP ed aggiorno lo stato e la richiesta di ciascuna posizione debitoria in base all'esito della chiamata
		    resposePsp = payConnectorService.registraPosizioniDebitorieInPSP(connectorCommand);
		    trasmesse = true;
		}
	    }
	    //gestione degli esiti da restituire nella response al client del servizio
	    List<EsitoOperazionePosizioneDebitoriaType> esitiPsp = resposePsp.getEsitoPosizione();
	    RiferimentiPosizioniDebitorieHelper esitiH = new RiferimentiPosizioniDebitorieHelper(esitiRegistrazioni);
	    RichiesteHelper reqH = new RichiesteHelper(payReqs);
	    for (EsitoOperazionePosizioneDebitoriaType esitoPsp : esitiPsp) {
		EsitoOperazionePosizioneDebitoriaType esitoNodo = (EsitoOperazionePosizioneDebitoriaType) esitiH
			.findRiferimentoPosizioneById(esitoPsp.getIdPosizione());
		boolean esitoOk = esitoPsp.isEsito();
		esitoNodo.setEsito(esitoOk);
		PopolamentoDatiHelper.completaDatiDaEsitoOperazioneDebitoriaType(esitoNodo, esitoPsp);
		esitoNodo.setMessaggio(esitoPsp.getMessaggio());
		esitoNodo.setCodiceErrore(esitoPsp.getCodiceErrore());
		esitoNodo.setErroreTemporaneo(esitoPsp.isErroreTemporaneo());
		esitoNodo.setStato(esitoPsp.getStato());
		someDone = someDone || esitoOk;
		allDone = allDone && esitoOk;
	    }
	}
	response.setPosizioniInserite(esitiRegistrazioni);
	EsitoType esito = EsitoType.KO;
	String message = "nessuna posizione debitoria è stata registrata in PagoPA";
	if (allDone) {
	    esito = EsitoType.OK;
	    message = trasmesse ? "tutte le posizioni debitorie sono state trasmesse a Pago PA"
		    : "tutte le posizioni sono state acquisite e in seguito saranno trasmesse a PagoPA";
	} else if (someDone) {
	    esito = EsitoType.PARZIALE;
	    message = trasmesse ? "alcune posizioni debitorie non sono state registrate in PagoPA a causa di errori"
		    : "alcune posizioni debitorie non sono state acquisite a causa di errori, le posizioni acquisite saranno in seguito trasmesse a PagoPA";
	}
	response.setEsito(esito);
	response.setMessaggio(message);
	popolaUUID(response.getPosizioniInserite());
	return response;
    }

    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType rc) throws ValidazionePosizioniDebitorieException {

	List<PosizioneDebitoriaType> rate = rc.getRate().getRata();
	if (rc.getSoggettoDebitore() == null) {
	    throw new ValidazionePosizioniDebitorieException(
		    "La posizione Debitoria non ha definito un soggetto debitore. Riferimento debito: " + rc.getDescrizione());
	}
	if (rate == null || rate.isEmpty()) {
	    throw new ValidazionePosizioniDebitorieException(
		    "Non sono state passate le rate della posizione debitoria. Riferimento debito: " + rc.getDescrizione());
	}
	for (int i = 0; i < rate.size(); i++) {
	    PosizioneDebitoriaType rata = rate.get(i);
	    String ref = PopolamentoDatiHelper.getRiferimentoPosizioneDebitoria(rata, rc,
		    rata.getNumeroRata() != null ? rata.getNumeroRata().intValue() : i + 1);
	    if (rata.getImporto() == null) {
		throw new ValidazionePosizioniDebitorieException("La componente importo della rata è nulla. Riferimento posizione debitoria: " + ref);
	    }
	}
    }

    @Override
    public OperazionePosizioniDebitorieResponseType annullaPosizioniDebitorie(ElencoPosizioniDebitorieType daAnnullare, PayConfigurationHelper payCfg)
	    throws PayException {

	if (daAnnullare == null || daAnnullare.getPosizione().isEmpty()) {
	    throw new PayException("Nessuna posizione da annullare è stata specificata");
	}
	//loggo evento dell'avvenuta invocazione del servizio in nuova transazione
	ElencoPosizioniDebitorieEsitoType elencoesiti = new ElencoPosizioniDebitorieEsitoType();
	OperazionePosizioniDebitorieResponseType response = new OperazionePosizioniDebitorieResponseType();
	//elenco di esiti non restituiti dal connectorService per cui occorre gestire l'esito nel DB
	ElencoPosizioniDebitorieEsitoType esitiNoPsp = new ElencoPosizioniDebitorieEsitoType();
	boolean allDone = true;
	boolean someDone = false;
	//Popolo il command per invocare il servizio del connettore
	List<PayRegistrazioniContabili> payRegs = new ArrayList<PayRegistrazioniContabili>();
	List<PayRichieste> payReqs = new ArrayList<PayRichieste>();
	TipiEvento tipoServizio = null;
	for (RiferimentoPosizioneDebitoriaType refPos : daAnnullare.getPosizione()) {
	    //TODO se il riferimento contiene solo l'id della registrazione contabile annullare tutte le rate non ancora pagate della registrazione
	    PayPosizioniDebitorie payPos = this.payPosizioniDebitorieService.findByRiferimentoPosizione(refPos);
	    PayStatoPagamenti statoPos = null;
	    boolean inviaAPSP = true;
	    if (payPos == null) {
		StringBuilder sb = new StringBuilder("la posizione debitoria id: ");
		if (refPos.getIdPosizione() != null) {
		    sb.append(refPos.getIdPosizione().intValue());
		}
		sb.append(", IUV: ").append(StringUtils.defaultString(refPos.getIUV())).append(" non esiste.");
		log.error("annullaPosizioniDebitorie: {}", sb);
		EsitoOperazionePosizioneDebitoriaType esitoKo = new EsitoOperazionePosizioneDebitoriaType();
		esitoKo.setEsito(false);
		esitoKo.setMessaggio(sb.toString());
		esitoKo.setStato(StatoPagamentoType.NON_ACQUISITO);
		PopolamentoDatiHelper.completaDatiDaRiferimentoPosizioneDebitoriaType(esitoKo, refPos);
		elencoesiti.getEsitoPosizione().add(esitoKo);
		inviaAPSP = false;
		allDone = false;
	    } else {
		List<PayStatoPagamenti> statiPos = this.payStatoPagamentiService.getCronologiaPosizioneDebitoria(payPos.getId().getCodice());
		StatoPosizioneDebitoriaHelper statusHelper = new StatoPosizioneDebitoriaHelper(statiPos);
		statoPos = statusHelper.getStatoCorrente();
		if (statoPos.getStato().equals(StatiPagamento.ACQUISITO.name())) {
		    //se la posizione non è stata inviata al PSP sarà annullata nel nodo pagamenti senza invocare il WS del PSP
		    inviaAPSP = false;
		    EsitoOperazionePosizioneDebitoriaType esitoOk = new EsitoOperazionePosizioneDebitoriaType();
		    esitoOk.setEsito(true);
		    esitoOk.setStato(StatoPagamentoType.ANNULLATO);
		    PopolamentoDatiHelper.completaDatiDaRiferimentoPosizioneDebitoriaType(esitoOk, refPos);
		    Set<String> riferimentiClientPosizione = payPosizioniDebitorieService
			    .findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice());
		    if (!riferimentiClientPosizione.isEmpty()) {
			esitoOk.getRiferimentoClient().addAll(riferimentiClientPosizione);
		    }
		    // se esiste una richiesta pendente di caricamento della posizione deve essere messa su completata
		    PayRichieste richiestaInvio = this.payRichiesteService.getRichiestaApertaPerTipoEPosizione(payPos,
			    TipiEvento.INVIA_POSIZIONI_A_PSP);
		    if (richiestaInvio != null) {
			try {
			    log.info(
				    "annullaPosizioniDebitorie: la posizione da annullare con id {} non è ancora stata trasmessa al PSP. La richiesta di caricamento sarà impostata su completata per impedirne il caricamento in PagoPA.",
				    payPos.getId());
			    this.payRichiesteService.annullaRichiestaTrans(richiestaInvio);
			    elencoesiti.getEsitoPosizione().add(esitoOk);
			    esitiNoPsp.getEsitoPosizione().add(esitoOk);
			} catch (Exception e) {
			    log.error("annullaPosizioniDebitorie: errore nell'annullamento della richiesta di caricamento della posizione " +
				      payPos.getId(),
				    e);
			    //se non è possibile annullare la richiesta di caricamento registro una richiesta di annullamento per far si che la posizione venga annullata dopo il caricamento
			    try {
				payReqs.add(
					this.payRichiesteService.registraRichiestaPerPosizioneDebitoriaTrans(payPos, TipiEvento.ANNULLA_POSIZIONI));
				esitoOk.setStato(StatoPagamentoType.ANNULLAMENTO_RICHIESTO);
				elencoesiti.getEsitoPosizione().add(esitoOk);
				esitiNoPsp.getEsitoPosizione().add(esitoOk);
			    } catch (Exception e1) {
				log.error("annullaPosizioniDebitorie: impossibile annullare la richiesta pendente di caricamento della posizione " +
					  payPos.getId() + " a cuasa dell'errore " + e1.toString(),
					e1);
				EsitoOperazionePosizioneDebitoriaType esitoKo = new EsitoOperazionePosizioneDebitoriaType();
				esitoKo.setEsito(false);
				esitoKo.setMessaggio(e.getMessage());
				esitoKo.setStato(StatoPagamentoType.fromValue(statoPos.getStato()));
				PopolamentoDatiHelper.completaDatiDaRiferimentoPosizioneDebitoriaType(esitoKo, refPos);
				if (!riferimentiClientPosizione.isEmpty()) {
				    esitoKo.getRiferimentoClient().addAll(riferimentiClientPosizione);
				}
				elencoesiti.getEsitoPosizione().add(esitoKo);
				inviaAPSP = false;
				allDone = false;
			    }
			}
		    } else {
			elencoesiti.getEsitoPosizione().add(esitoOk);
		    }
		    someDone = true;
		} else {
		    //gestione annullamento richieste già trasmesse
		    PayRichieste richAnnull = null;
		    EsitoOperazionePosizioneDebitoriaType esitoOk = new EsitoOperazionePosizioneDebitoriaType();
		    esitoOk.setEsito(true);
		    esitoOk.setStato(StatoPagamentoType.fromValue(statoPos.getStato()));
		    PopolamentoDatiHelper.completaDatiDaRiferimentoPosizioneDebitoriaType(esitoOk, refPos);
		    Set<String> riferimentiClientPosizione = payPosizioniDebitorieService
			    .findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice());
		    if (!riferimentiClientPosizione.isEmpty()) {
			esitoOk.getRiferimentoClient().addAll(riferimentiClientPosizione);
		    }
		    try {
			validaAnnullamentoPosizione(payPos, statusHelper);
			//se ho identificato la posizione debitoria registro la richiesta di annullamento (tranne che per le posizioni OTF che non vengono annullate sul PSP)
			richAnnull = this.payRichiesteService.registraRichiestaPerPosizioneDebitoriaTrans(payPos, TipiEvento.ANNULLA_POSIZIONI);
			payReqs.add(richAnnull);
			tipoServizio = TipiEvento.fromValue(richAnnull.getTipoRichiesta());
			if (this.payConnectorService.isSchedulerAttivo(tipoServizio)) {
			    //nel caso in cui l'annullamento schedulato sia attivo viene restituito ANNULLAMENTO_RICHIESTO 
			    //anche se la richiesta di annullamento al PSP potrebbe essere inviata solo in seguito
			    esitoOk.setStato(StatoPagamentoType.ANNULLAMENTO_RICHIESTO);
			    esitoOk.setMessaggio("La richiesta di annullamento è stata acquisita nel nodo pagamenti e sarà elaborata in seguito");
			    //se non è SOLO schedulato i valori restituiti dalla chiamata immediata al connettore sovrascriveranno questi valori
			}
			elencoesiti.getEsitoPosizione().add(esitoOk);
		    } catch (Exception e) {
			log.error("annullaPosizioniDebitorie: errore nella registrazione della richiesta di annullamento per la posizione " +
				  payPos.getId(),
				e);
			EsitoOperazionePosizioneDebitoriaType esitoKo = new EsitoOperazionePosizioneDebitoriaType();
			esitoKo.setEsito(false);
			esitoKo.setMessaggio(e.getMessage());
			esitoKo.setStato(StatoPagamentoType.fromValue(statoPos.getStato()));
			PopolamentoDatiHelper.completaDatiDaRiferimentoPosizioneDebitoriaType(esitoKo, refPos);
			elencoesiti.getEsitoPosizione().add(esitoKo);
			inviaAPSP = false;
			allDone = false;
		    }
		}
	    }
	    PayRegistrazioniContabili payRegCont = new PayRegistrazioniContabili();
	    if (inviaAPSP) {
		PkId regId = new PkId(payPos.getRegistrazioneContabile().getId().getCodice());
		payRegCont.setId(regId);
		int regContIdx = payRegs.indexOf(payRegCont);
		if (regContIdx > -1) {
		    payRegCont = payRegs.get(regContIdx);
		} else {
		    payRegCont = this.payRegistrazioniContabiliService.findById(regId);
		    if (payRegCont != null) {
			payRegCont.setPosizioniDebitorie(new HashSet<PayPosizioniDebitorie>());
			payRegs.add(payRegCont);
		    } else {
			//errore reg cont associata alla pos deb non trovata già gestito in validazione
		    }
		}
		payRegCont.getPosizioniDebitorie().add(payPos);
	    }
	}
	RiferimentiPosizioniDebitorieHelper esitiH = new RiferimentiPosizioniDebitorieHelper(elencoesiti);
	if (!payRegs.isEmpty()) {
	    String codiceComunicazione = this.generaCodiceComunicazione(TipiEvento.ANNULLA_POSIZIONI);
	    PosizioniDebitorieCommand connectorCommand = posizioniDebitorieCommandService.popolaPosizioniDebitorie(payRegs, codiceComunicazione);
	    //invoco il WS del PSP ed aggiorno lo styato in base all'esito della chiamata
	    ElencoPosizioniDebitorieEsitoType esitiPsp = new ElencoPosizioniDebitorieEsitoType();
	    if (!this.payConnectorService.isServizioSoloSchedulato(tipoServizio)) {
		esitiPsp = this.payConnectorService.annullaPosizioniDebitorieInPSP(connectorCommand, false);
	    }
	    for (EsitoOperazionePosizioneDebitoriaType esitoPsp : esitiPsp.getEsitoPosizione()) {
		EsitoOperazionePosizioneDebitoriaType esitoNodo = (EsitoOperazionePosizioneDebitoriaType) esitiH
			.findRiferimentoPosizioneById(esitoPsp.getIdPosizione());
		boolean esitoOk = esitoPsp.isEsito();
		esitoNodo.setEsito(esitoOk);
		PopolamentoDatiHelper.completaDatiDaEsitoOperazioneDebitoriaType(esitoNodo, esitoPsp);
		esitoNodo.setMessaggio(esitoPsp.getMessaggio());
		esitoNodo.setCodiceErrore(esitoPsp.getCodiceErrore());
		esitoNodo.setStato(esitoPsp.getStato());
		someDone = someDone || esitoPsp.isEsito();
		allDone = allDone && esitoPsp.isEsito();
	    }
	}
	//gestione esiti non restituiti dal connettore per aggiornanre lo stato delle posizioni debitorie 
	for (EsitoOperazionePosizioneDebitoriaType esitoNoPsp : esitiNoPsp.getEsitoPosizione()) {
	    PayPosizioniDebitorie pos = this.payPosizioniDebitorieService.findByRiferimentoPosizione(esitoNoPsp);
	    if (pos != null) {
		try {
		    this.payStatoPagamentiService.registraStatoPosizioneDebitoria(esitoNoPsp, pos);
		    log.info(
			    "annullaPosizioniDebitorie - la richiesta di annullamento non è stata inviata al connettore per la posizione debitoria {} nuovo stato della posizione: {} ",
			    pos.getId(), esitoNoPsp.getStato());
		} catch (Exception e) {
		    log.error(
			    "annullaPosizioniDebitorie - errore nell'aggiornamento dello stato della posizione per la richiesta di annullamento non trasmessa al PSP. posizione: " +
			      pos.getId(),
			    e);
		    EsitoOperazionePosizioneDebitoriaType esitoNodo = (EsitoOperazionePosizioneDebitoriaType) esitiH
			    .findRiferimentoPosizioneById(esitoNoPsp.getIdPosizione());
		    esitoNodo.setStato(StatoPagamentoType.CON_ERRORE);
		    esitoNodo.setEsito(false);
		    esitoNodo.setMessaggio("errore nell'aggiornamento dello stato della posizione su " + esitoNoPsp.getStato().name());
		}
	    }
	}
	response.setPosizioniInserite(elencoesiti);
	EsitoType esito = EsitoType.KO;
	String message = "nessuna posizione debitoria è stata annullata";
	if (allDone) {
	    esito = EsitoType.OK;
	    message = "la richiesta di annullamento è stata completata per tutte le posizioni debitorie";
	} else if (someDone) {
	    esito = EsitoType.PARZIALE;
	    message = "a causa di errori non è stato possibile richiedere l'annullamento di alcune posizioni debitorie";
	}
	response.setEsito(esito);
	response.setMessaggio(message);
	popolaUUID(response.getPosizioniInserite());
	return response;
    }

    private void validaAnnullamentoPosizione(PayPosizioniDebitorie posiz, StatoPosizioneDebitoriaHelper statusH) throws PayInvalidRequestException {

	StringBuilder sbErr = new StringBuilder("Impossibile annullare la posizione debitoria con id ").append(posiz.getId().getCodice());
	if (statusH == null) {
	    sbErr.append(": stato della posizione non definito.");
	    throw new PayInvalidRequestException(sbErr.toString());
	} else {
	    PayStatoPagamenti statoPos = statusH.getStatoCorrente();
	    StatiPagamento currentStatus = StatiPagamento.fromValue(statoPos.getStato());
	    if (statusH.isAnnullato()) {
		sbErr.append(": posizione già annullata");
		throw new PayInvalidRequestException(sbErr.toString());
	    } else if (statusH.isPagato()) {
		sbErr.append(": posizione già pagata");
		throw new PayInvalidRequestException(sbErr.toString());
	    } else if (currentStatus.equals(StatiPagamento.CON_ERRORE)) {
		sbErr.append(": posizione non attivata a causa di errori");
		throw new PayInvalidRequestException(sbErr.toString());
	    }
	    if (posiz.getRegistrazioneContabile() == null || posiz.getRegistrazioneContabile().getId() == null
		    || posiz.getRegistrazioneContabile().getId().getCodice() == null) {
		sbErr.append(": posizione non associata a registrazione contabile");
		throw new PayInvalidRequestException(sbErr.toString());
	    }
	}
    }

    @Override
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(AttivaPagamentoOnTheFlyType parameters, PayConfigurationHelper cfg)
	    throws PayException {

	AttivaPagamentoOnTheFlyResponseType retVal = new AttivaPagamentoOnTheFlyResponseType();
	//loggo evento dell'avvenuta invocazione del servizio in nuova transazione
	TipiEvento tipoServizio = TipiEvento.ATTIVA_PAGAMENTO_OTF;
	log.debug("attivaPagamentoOnTheFly - creo l'evento...");
	//recupero l'istanza del connettore associato al profilo ente
	log.debug("attivaPagamentoOnTheFly - recupero l'istanza del connettore...");
	IPayConnector conn = this.payConnectorService.getPayConnectorInstance();
	PayProfiliEntiCreditori currentProfile = PayConfigurationHelper.getProfiloEnteCreditore();
	//creazione della registrazione contabile nel nodo
	//la registrazione contabile viene committata prima di invocare il connettore
	List<RegistrazioneContabileWsInType> rcs = new RegistrazioniContabiliAccorpamentoParser(payRegistrazioniCausaliService).gestisciAccorpamento(parameters.isAccorpaPosizioni(), parameters.getRegistrazione(),
		parameters.getOggettoPagamentoUnico());
	List<PayRegistrazioniContabili> prcs = new ArrayList<>();
	List<PayPosizioniDebitorie> payPDs = new ArrayList<>();
	log.debug("attivaPagamentoOnTheFly - carico le Registrazioni contabili");
	for (RegistrazioneContabileWsInType rcWsIn : rcs) {
	    RegistrazioneContabileType rc = registrazioniContabiliConverterService.completaRegistrazioneContabile(rcWsIn);
	    log.debug("attivaPagamentoOnTheFly - carico la Registrazione {}", rc.getDescrizione());
	    PayRegistrazioniContabili r = this.attivazionePagamentiService.richiestaCaricamentoPosizioniECommittaDati(rc,
		    TipiEvento.ATTIVA_PAGAMENTO_OTF, cfg, conn.supportaPagamentoOnTheFly());
	    payPDs.addAll(r.getPosizioniDebitorie());
	    prcs.add(r);
	    log.debug("attivaPagamentoOnTheFly - Registrazione {} caricata {}", rc.getDescrizione(), r.getId());
	}
	if (!prcs.isEmpty()) {
	    EsitoOperazionePosizioneDebitoriaType esitoKo = new EsitoOperazionePosizioneDebitoriaType();
	    boolean supportaOTF = conn.supportaPagamentoOnTheFly();
	    log.debug("attivaPagamentoOnTheFly - conn.supportaPagamentoOnTheFly() {}", supportaOTF);
	    String codiceComunicazione = this.generaCodiceComunicazione(TipiEvento.ATTIVA_PAGAMENTO_OTF);
	    if (supportaOTF) {
		PosizioniDebitorieCommand otfCmd = posizioniDebitorieCommandService.popolaPosizioniDebitorie(prcs, codiceComunicazione);
		checkSupportaMolteCausaliRaggupate(otfCmd, conn);
		// invoco l'implementazione di attivaPagamentoOnTheFly del connettore
		log.info("attivaPagamentoOnTheFly - caricamento on the fly della posizione debitoria  in corso...");
		try {
		    retVal = conn.attivaPagamentoOnTheFly(otfCmd);
		    //se la sessione on the fly è creata con successo aggiorno lo stato della pos deb a ATTIVATO_IN_PSP
		    if (retVal != null) {
			if (log.isInfoEnabled()) {
			    boolean esitoSes = retVal.getSessionePagamento() != null && retVal.getSessionePagamento().isEsito();
			    log.info("attivaPagamentoOnTheFly - caricamento on the fly completato nel connettore. Esito creazione sessione {}",
				    esitoSes);
			}
			if (retVal.getSessionePagamento() != null && !retVal.getPosizioneInserita().isEmpty()
				&& retVal.getSessionePagamento().isEsito()) {
			    //nella PAY_SESSIONI_PAGAMENTO devo salvare l'url che mi è stato passato dal client 
			    String urlEsito = parameters.getUrlRedirectEsito();
			    String urlPortalePagamenti = StringUtils.isNotBlank(retVal.getSessionePagamento().getPayUrl())
				    ? retVal.getSessionePagamento().getPayUrl()
				    : currentProfile.getPayConnector().getUrlPortalePagamenti();
			    retVal.getSessionePagamento().setPayUrl(urlEsito);
			    log.debug("attivaPagamentoOnTheFly - isOTF ciclo le posizioni debitorie per creare la sessione di pagamento");
			    log.debug("attivaPagamentoOnTheFly - this.paySessioniPagamentoService.creaSessionePagamento");
			    this.paySessioniPagamentoService.creaSessionePagamento(retVal.getSessionePagamento(), payPDs);
			    //l'url per il portale del pagamento restituito dal connettore (o configurato nel DB) lo devo restitire al client nella response
			    retVal.getSessionePagamento().setPayUrl(urlPortalePagamenti);
			}
		    }
		} catch (Exception e) {
		    String errMsg = "errore nel connettore di pagamento durante il caricamento delle posizioni OTF: " + e.toString();
		    log.error("attivaPagamentoOnTheFly - {}", errMsg, e);
		    esitoKo.setEsito(false);
		    esitoKo.setMessaggio("errore nell'inserimento delle posizioni debitorie");
		    esitoKo.setStato(StatoPagamentoType.NON_ACQUISITO);
		    retVal.getPosizioneInserita().add(esitoKo);
		    retVal.setSessionePagamento(null);
		}
	    } else {
		//se attivaPagamentoOnTheFly non è implementato nel connettore viene gestito come un caricamento di una posizione normale e i dati sessione non vengono restituiti
		PosizioniDebitorieCommand connectorCommand = posizioniDebitorieCommandService.popolaPosizioniDebitorie(prcs, codiceComunicazione);
		//invoco il WS del PSP ed aggiorno lo styato in base all'esito della chiamata
		ElencoPosizioniDebitorieEsitoType resposePsp = new ElencoPosizioniDebitorieEsitoType();
		EsitoOperazionePosizioneDebitoriaType esitoNodo = new EsitoOperazionePosizioneDebitoriaType();
		if (!this.payConnectorService.isServizioSoloSchedulato(tipoServizio)) {
		    if (log.isInfoEnabled()) {
			log.info(
				"attivaPagamentoOnTheFly - pagamento on the fly non supportato dal connettore di pagamento, invocazione di registraPosizioniDebitorieInPSP...");
		    }
		    try {
			resposePsp = payConnectorService.registraPosizioniDebitorieInPSP(connectorCommand);
			if (log.isInfoEnabled()) {
			    log.info("attivaPagamentoOnTheFly - posizioni debitorie caricate nei sistemi del PSP.");
			}
		    } catch (Exception e) {
			String errMsg = "errore nel connettore di pagamento durante il caricamento delle posizioni : " + e.toString();
			log.error("attivaPagamentoOnTheFly - " + errMsg, e);
			esitoNodo.setMessaggio(errMsg);
			esitoNodo.setStato(StatoPagamentoType.CON_ERRORE);
		    }
		} else {
		    String msg = "pagamento on the fly non supportato dal connettore di pagamento. Il caricamento delle posizioni è solo schedulato impossibile attivare il pagamento.";
		    if (log.isInfoEnabled()) {
			log.info("attivaPagamentoOnTheFly - {}", msg);
		    }
		    esitoNodo.setMessaggio(msg);
		    esitoNodo.setStato(StatoPagamentoType.CON_ERRORE);
		}
		//gestione degli esiti da restituire nella response al client del servizio
		List<EsitoOperazionePosizioneDebitoriaType> esitiPsp = resposePsp.getEsitoPosizione();
		retVal = new AttivaPagamentoOnTheFlyResponseType();
		//gestione esito richiesta
		retVal.getPosizioneInserita().addAll(esitiPsp);
		AttivaSessionePagamentoResponseType sessResp = new AttivaSessionePagamentoResponseType();
		sessResp.setEsito(false);
		sessResp.setDescEsito("pagamento on the fly non supportato dal connettore " + currentProfile.getPayConnector().getDescrizione());
		retVal.setSessionePagamento(sessResp);
	    }
	    if (retVal != null && retVal.getSessionePagamento() != null && retVal.getSessionePagamento().isEsito()) {
		List<EsitoOperazionePosizioneDebitoriaType> pis = retVal.getPosizioneInserita();
		for (EsitoOperazionePosizioneDebitoriaType pi : pis) {
		    log.debug("attivaPagamentoOnTheFly - registro lo stato della posizione {} ", pi.getIdPosizione().intValue());
		    //aggiornamento dello stato della posizione debitoria in base all'esito dell'operazione di caricamento nel PSP
		    for (PayPosizioniDebitorie pd : payPDs) {
			if (pd.getId().getCodice().intValue() == pi.getIdPosizione().intValue()) {
			    this.payStatoPagamentiService.registraStatoPosizioneDebitoria(pi, pd);
			    //aggiornamento della richiesta in sospeso se presente
			    log.debug("attivaPagamentoOnTheFly - recupero la richiesta per la posizione {} ", pi.getIdPosizione().intValue());
			    PayRichieste richiestaApertaPerTipoEPosizione = this.payRichiesteService.getRichiestaApertaPerTipoEPosizione(pd,
				    tipoServizio);
			    log.debug("attivaPagamentoOnTheFly - aggiorno la richiesta {} per la posizione {} ", richiestaApertaPerTipoEPosizione,
				    pi.getIdPosizione().intValue());
			    this.payRichiesteService.aggiornaEsitoRichiesta(richiestaApertaPerTipoEPosizione,
				    pi.isEsito() || !pi.isErroreTemporaneo());
			    break;
			}
		    }
		}
	    } else {
		// qualcosa è andato male
		for (PayRegistrazioniContabili rc : prcs) {
		    Set<PayPosizioniDebitorie> pds = rc.getPosizioniDebitorie();
		    for (PayPosizioniDebitorie pd : pds) {
			log.debug("attivaPagamentoOnTheFly - qualcosa è andato male per la posizione {} ", pd.getId());
			//aggiornamento della richiesta in sospeso se presente
			PayRichieste richiestaApertaPerTipoEPosizione = this.payRichiesteService.getRichiestaApertaPerTipoEPosizione(pd,
				tipoServizio);
			log.debug("attivaPagamentoOnTheFly - qualcosa è andato male aggiorno la richiesta {} per la posizione {} ",
				richiestaApertaPerTipoEPosizione, pd.getId());
			this.payRichiesteService.aggiornaEsitoRichiesta(richiestaApertaPerTipoEPosizione, false);
		    }
		}
	    }
	}
	//condizione che non dovrebbe verificarsi 
	else {
	    //comunque ritorno esito negativo e sessione null
	    EsitoOperazionePosizioneDebitoriaType esitoKo = new EsitoOperazionePosizioneDebitoriaType();
	    esitoKo.setEsito(false);
	    esitoKo.setMessaggio("Errore nell'inserimento delle posizioni debitorie. Nessuna posizione in request");
	    esitoKo.setStato(StatoPagamentoType.NON_ACQUISITO);
	    retVal.getPosizioneInserita().add(esitoKo);
	    retVal.setSessionePagamento(null);
	}
	popolaUUID(retVal.getPosizioneInserita());
	//restituisco url e parametri della sessione di pagamento al chiamante insieme ai dati della posizione generata
	return retVal;
    }

    @Override
    public VerificaStatoPosizioniResponseType verificaStatoPagamenti(ElencoPosizioniDebitorieType posizioni, PayConfigurationHelper cfg)
	    throws PayException {

	VerificaStatoPosizioniResponseType response = new VerificaStatoPosizioniResponseType();
	response.setEsito(EsitoType.OK);
	ElencoStatoPosizioniType elencoStati = new ElencoStatoPosizioniType();
	List<RiferimentoPosizioneDebitoriaType> rifPosizioni = posizioni.getPosizione();
	IPayConnector ctor = this.payConnectorService.getPayConnectorInstance();
	List<PayPosizioniDebitorie> daVerificare = new ArrayList<>();
	TipiEvento tipoServizio = null;
	for (RiferimentoPosizioneDebitoriaType rifPosizione : rifPosizioni) {
	    PayPosizioniDebitorie payPos = this.payPosizioniDebitorieService.findByRiferimentoPosizione(rifPosizione);
	    StatoPosizioneType statoPos = new StatoPosizioneType();
	    if (payPos != null) {
		//in tutti i casi vengono predisposti i dati da restituire con lo stato del pagamento così come censito nel nodo pagamenti
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(statoPos, payPos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		PayStatoPagamenti payStato = this.payStatoPagamentiService.getStatoPosizioneDebitoria(payPos);
		StatoPagamentoType statoResp = null;
		String descStatus = payStato.getDescStato();
		if (payStato != null) {
		    //imposto un riferimento allo stato corrente nella posizione debitoria (campo transient) ad uso eventuale da parte del connettore per evitare altre query al DB
		    payPos.impostaStatoCorrente(payStato);
		    try {
			statoResp = StatoPagamentoType.fromValue(payStato.getStato());
		    } catch (Exception e) {
			descStatus = "valore di stato non valido o non gestito nel nodo pagamenti: " + payStato.getStato();
			log.error("verificaStatoPagamento - " + descStatus, e);
		    }
		}
		statoPos.setStato(statoResp);
		statoPos.setMessaggio(descStatus);
		PayPagamenti payPagam = this.payPagamentiService.getPagamentoByPosizioneDebitoria(payPos);
		if (payPagam != null) {
		    PaySoggettiDebitori pagatore = null;
		    if (payPagam.getSoggettoPagatore() != null && payPagam.getSoggettoPagatore().getId() != null
			    && payPagam.getSoggettoPagatore().getId().getCodice() != null) {
			pagatore = this.paySoggettiDebitoriService.findById(new PkId(payPagam.getSoggettoPagatore().getId().getCodice()));
		    }
		    statoPos.setDatiPagamento(PayPagamentiServiceImpl.populateSchemaObject(payPagam, pagatore));
		} else if (!statoResp.equals(StatoPagamentoType.ANNULLATO) && !statoResp.equals(StatoPagamentoType.CON_ERRORE)
			&& !statoResp.equals(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO)) {
		    //solo se i dati del pagamento non sono ancora presenti nel DB del nodo e la posizione non è annullata o con errore allora invoco il WS di verifica stato pagemnto del connettore se presente
		    daVerificare.add(payPos);
		    //e memorizzo la richiesta in PAY_RICHIESTE
		    PayRichieste rich = this.payRichiesteService.registraRichiestaPerPosizioneDebitoriaTrans(payPos,
			    TipiEvento.VERIFICA_STATO_PAGAMENTO);
		    if (rich != null) {
			tipoServizio = TipiEvento.fromValue(rich.getTipoRichiesta());
		    }
		}
	    } else {
		//posizione non trovata --> RESTITUISCO STATO NON_ACQUISITO
		PopolamentoDatiHelper.completaDatiDaRiferimentoPosizioneDebitoriaType(statoPos, rifPosizione);
		statoPos.setIdRegistrazioneContabile(rifPosizione.getIdRegistrazioneContabile());
		statoPos.setEsito(false);
		statoPos.setStato(StatoPagamentoType.NON_ACQUISITO);
		statoPos.setMessaggio("posizione debitoria non presente nel nodo pagamenti");
	    }
	    elencoStati.getStatoPosizioni().add(statoPos);
	}
	if (ctor.supportaVerificaPagamento() && !daVerificare.isEmpty() && !this.payConnectorService.isServizioSoloSchedulato(tipoServizio)) {
	    String codiceComunicazione = this.generaCodiceComunicazione(TipiEvento.VERIFICA_STATO_PAGAMENTO);
	    RichiestaSuListaPosizioniCommand veriCmd = new RichiestaSuListaPosizioniCommand(codiceComunicazione);
	    veriCmd.getPosizioni().addAll(daVerificare);
	    try {
		//invoco connectorservice.verificaPagamentiPSP che si occupa di recuperare dal PSP lo stato aggiornato dei pagamenti, 
		ElencoStatoPosizioniType statiPsp = this.payConnectorService.verificaStatoPosizioniInPSP(veriCmd);
		//i dati restituiti dal connettore devono essere sostituiti a quelli letti dal DB prima che vengano restituiti al chiamante
		RiferimentiPosizioniDebitorieHelper hDB = new RiferimentiPosizioniDebitorieHelper(elencoStati);
		for (StatoPosizioneType statoPsp : statiPsp.getStatoPosizioni()) {
		    //identifico lo statoposizione fra quelli letti dal db
		    StatoPosizioneType statoDB = null;
		    if (StringUtils.isNotBlank(statoPsp.getIUV())) {
			statoDB = (StatoPosizioneType) hDB.findRiferimentoPosizioneByIUV(statoPsp.getIUV());
		    }
		    if (statoDB == null && statoPsp.getIdPosizione() != null) {
			statoDB = (StatoPosizioneType) hDB.findRiferimentoPosizioneById(statoPsp.getIdPosizione());
		    }
		    //e lo aggiorno con i dati restituiti dal connettore prima di restituirlo al chiamante
		    if (statoDB != null) {
			PopolamentoDatiHelper.completaDatiDaEsitoOperazioneDebitoriaType(statoDB, statoPsp);
			statoDB.setMessaggio(statoPsp.getMessaggio());
			statoDB.setStato(statoPsp.getStato());
			statoDB.setEsito(statoPsp.isEsito());
			statoDB.setDatiPagamento(statoPsp.getDatiPagamento());
			//i connettori potrebbero aver restituito errore temporaneo = true per mantenere le richieste attive anche in caso di successo nella verifica dei pagamenti
			//ma al client viene restituito erroreTemporaneo = false se l'esito == true
			statoDB.setErroreTemporaneo(!statoPsp.isEsito() && statoPsp.isErroreTemporaneo());
		    } else {
			//il riferimento alla pos deb dovrebbe esistere già nel DB quindi dovrebbe essere già presente nei dati predisposti precedentemente letti dal DB
			if (log.isWarnEnabled()) {
			    log.warn("verificaStatoPagamenti impossibile recuperare nei dati del DB la posizione debitoria restituita dal PSP: {}",
				    RiferimentiPosizioniDebitorieHelper.riferimentoPosizioneToString(statoPsp));
			}
		    }
		}
	    } catch (PayException e) {
		String msgEsito = "Errore nella chiamata al connettore: " + e.getMessage();
		log.error(msgEsito, e);
		//L'esito KO indica che si è verificato un errore o un esito KO nella chiamata al WS del connettore. 
		//Lo stato delle posizioni viene comunque restituito così come registrato nel nodo pagamenti
		response.setEsito(EsitoType.KO);
		response.setMessaggio(msgEsito);
	    }
	} else {
	    //L'esito parziale indica che lo stato dei pagamenti restituito non è stato sottoposto ad aggiornamento tramite 
	    //specifici servizi del PSP --> i dati potrebbero non essere ancora stati aggiornati e sono affidabili fino ad un certo punto
	    String msg = ctor.supportaVerificaPagamento() ? "Elaborazione eseguita correttamente"
		    : "Servizio di verifica dello stato delle posizioni debitorie non disponibile per il connettore di pagamento (" +
		      ctor.getConnectorName() + "). Le informazioni potrebbero non essere aggiornate.";
	    EsitoType esito = ctor.supportaVerificaPagamento() ? EsitoType.OK : EsitoType.PARZIALE;
	    response.setEsito(esito);
	    response.setMessaggio(msg);
	}
	List<StatoPosizioneType> sps = elencoStati.getStatoPosizioni();
	for (StatoPosizioneType spt : sps) {
	    BigInteger idPosizione = spt.getIdPosizione();
	    List<PayStatoPagamenti> cronologiaPosizioneDebitoria = payStatoPagamentiService.getCronologiaPosizioneDebitoria(idPosizione.intValue());
	    boolean almostOne = false;
	    for (PayStatoPagamenti psp : cronologiaPosizioneDebitoria) {
		almostOne = true;
		DettaglioStatoPosizioneType dsp = new DettaglioStatoPosizioneType();
		StatoPagamentoType statoResp = null;
		String descStatus = psp.getDescStato();
		try {
		    statoResp = StatoPagamentoType.fromValue(psp.getStato());
		} catch (Exception e) {
		    statoResp = StatoPagamentoType.CON_ERRORE;
		    descStatus = "valore di stato non valido o non gestito nel nodo pagamenti: " + psp.getStato();
		    log.error("verificaStatoPagamento - " + descStatus, e);
		}
		dsp.setStato(statoResp);
		dsp.setDescrizioneStato(descStatus);
		dsp.setDataStato(Utilities.getXMLGregorianCalendar(psp.getDataEvento()));
		spt.getCronologiaStatiPosizione().add(dsp);
	    }
	    if (!almostOne) {
		// non ha cronologia
		DettaglioStatoPosizioneType dsp = new DettaglioStatoPosizioneType();
		StatoPagamentoType statoResp = StatoPagamentoType.NON_ACQUISITO;
		dsp.setStato(statoResp);
		dsp.setDescrizioneStato("Non presente la posizione debitoria con id" + idPosizione.intValue());
		dsp.setDataStato(Utilities.getXMLGregorianCalendar(new Date()));
		spt.getCronologiaStatiPosizione().add(dsp);
	    }
	}
	response.setStatoPosizioni(elencoStati);
	popolaUUIDDaStato(response);
	return response;
    }

    @Override
    public OperazionePosizioniDebitorieResponseType registraPagamentiOfflineEAnnulla(ElencoPagamentiOfflineType daAnnullare,
	    PayConfigurationHelper payCfg) throws PayException {

	ElencoPosizioniDebitorieEsitoType elencoesiti = new ElencoPosizioniDebitorieEsitoType();
	OperazionePosizioniDebitorieResponseType response = new OperazionePosizioniDebitorieResponseType();
	boolean allDone = true;
	boolean someDone = false;
	//Popolo il command per invocare il servizio del connettore	
	List<DatiPagamentoCommand> pagamenti = new ArrayList<>();
	List<PayRegistrazioniContabili> payRegs = new ArrayList<>();
	for (PagamentoOfflineType refPos : daAnnullare.getPosizione()) {
	    PayPosizioniDebitorie payPos = this.payPosizioniDebitorieService.findByRiferimentoPosizione(refPos);
	    PayStatoPagamenti statoPos = null;
	    boolean inviaAPSP = true;
	    if (payPos == null) {
		StringBuilder sb = new StringBuilder("la posizione debitoria id: ");
		if (refPos.getIdPosizione() != null) {
		    sb.append(refPos.getIdPosizione().intValue());
		}
		if (StringUtils.isNotBlank(refPos.getIUV())) {
		    sb.append(", IUV: ").append(StringUtils.defaultString(refPos.getIUV()));
		}
		sb.append(" non esiste.");
		log.error("annullaPosizioniDebitorie: {}", sb);
		EsitoOperazionePosizioneDebitoriaType esitoKo = new EsitoOperazionePosizioneDebitoriaType();
		esitoKo.setEsito(false);
		esitoKo.setMessaggio(sb.toString());
		esitoKo.setStato(StatoPagamentoType.NON_ACQUISITO);
		PopolamentoDatiHelper.completaDatiDaRiferimentoPosizioneDebitoriaType(esitoKo, refPos);
		elencoesiti.getEsitoPosizione().add(esitoKo);
		inviaAPSP = false;
		allDone = false;
	    } else {
		List<PayStatoPagamenti> statiPos = this.payStatoPagamentiService.getCronologiaPosizioneDebitoria(payPos.getId().getCodice());
		StatoPosizioneDebitoriaHelper statusHelper = new StatoPosizioneDebitoriaHelper(statiPos);
		statoPos = statusHelper.getStatoCorrente();
		DatiPagamentoType datiPagamento = refPos.getDatiPagamento();
		//se i dati pagamento non vangono passati vengono automaticamente impostati l'importo totale della posizione come importo pagato e il soggetto debitore come soggetto pagatore
		if (datiPagamento == null) {
		    datiPagamento = new DatiPagamentoType();
		    Set<PayDettaglioImporti> righeImporto = payPos.getDettagliImporto();
		    BigDecimal totImporto = BigDecimal.ZERO;
		    for (PayDettaglioImporti importo : righeImporto) {
			totImporto = totImporto.add(importo.getImporto());
		    }
		    datiPagamento.setImportoPagato(totImporto);
		    datiPagamento.setModalitaPagamento("offline");
		    datiPagamento.setDataOraPagamento(Utilities.getXMLGregorianCalendar(new Date()));
		}
		DatiPagamentoCommand pagamCmd = new DatiPagamentoCommand();
		pagamCmd.setIdPosizione(payPos.getId().getCodice());
		pagamCmd.setDatiPagamento(datiPagamento);
		pagamenti.add(pagamCmd);
		if (statoPos.getStato().equals(StatiPagamento.ACQUISITO.name())) {
		    //se la posizione non è stata inviata al PSP sarà annullata nel nodo pagamenti senza invocare il WS del PSP
		    inviaAPSP = false;
		    EsitoOperazionePosizioneDebitoriaType esitoOk = new EsitoOperazionePosizioneDebitoriaType();
		    esitoOk.setEsito(true);
		    esitoOk.setStato(StatoPagamentoType.ANNULLATO);
		    PopolamentoDatiHelper.completaDatiDaRiferimentoPosizioneDebitoriaType(esitoOk, refPos);
		    Set<String> riferimentiClientPosizione = payPosizioniDebitorieService
			    .findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice());
		    if (!riferimentiClientPosizione.isEmpty()) {
			esitoOk.getRiferimentoClient().addAll(riferimentiClientPosizione);
		    }
		    elencoesiti.getEsitoPosizione().add(esitoOk);
		    someDone = true;
		} else {
		    try {
			validaAnnullamentoPosizione(payPos, statusHelper);
		    } catch (Exception e) {
			log.error("annullaPosizioniDebitorie: " + e.getMessage(), e);
			EsitoOperazionePosizioneDebitoriaType esitoKo = new EsitoOperazionePosizioneDebitoriaType();
			esitoKo.setEsito(false);
			esitoKo.setMessaggio(e.getMessage());
			esitoKo.setStato(StatoPagamentoType.fromValue(statoPos.getStato()));
			PopolamentoDatiHelper.completaDatiDaRiferimentoPosizioneDebitoriaType(esitoKo, refPos);
			Set<String> riferimentiClientPosizione = payPosizioniDebitorieService
				.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice());
			if (!riferimentiClientPosizione.isEmpty()) {
			    esitoKo.getRiferimentoClient().addAll(riferimentiClientPosizione);
			}
			elencoesiti.getEsitoPosizione().add(esitoKo);
			inviaAPSP = false;
			allDone = false;
		    }
		}
	    }
	    if (inviaAPSP) {
		PayRegistrazioniContabili payRegCont = new PayRegistrazioniContabili();
		PkId regId = new PkId(payPos.getRegistrazioneContabile().getId().getCodice());
		payRegCont.setId(regId);
		int regContIdx = payRegs.indexOf(payRegCont);
		if (regContIdx > -1) {
		    payRegCont = payRegs.get(regContIdx);
		} else {
		    payRegCont = this.payRegistrazioniContabiliService.findById(regId);
		    payRegCont.setPosizioniDebitorie(new HashSet<PayPosizioniDebitorie>());
		    payRegs.add(payRegCont);
		}
		payRegCont.getPosizioniDebitorie().add(payPos);
		EsitoOperazionePosizioneDebitoriaType esitoOk = new EsitoOperazionePosizioneDebitoriaType();
		esitoOk.setEsito(true);
		if (statoPos != null) {
		    esitoOk.setStato(StatoPagamentoType.fromValue(statoPos.getStato()));
		}
		PopolamentoDatiHelper.completaDatiDaRiferimentoPosizioneDebitoriaType(esitoOk, refPos);
		Set<String> riferimentiClientPosizione = payPosizioniDebitorieService
			.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice());
		if (!riferimentiClientPosizione.isEmpty()) {
		    esitoOk.getRiferimentoClient().addAll(riferimentiClientPosizione);
		}
		elencoesiti.getEsitoPosizione().add(esitoOk);
		someDone = true;
	    }
	}
	String codiceComunicazione = this.generaCodiceComunicazione(TipiEvento.REGISTRA_PAGAMENTI_OFFLINE);
	PosizioniDebitorieCommand connectorCommand = posizioniDebitorieCommandService.popolaPosizioniDebitorie(payRegs, codiceComunicazione);
	connectorCommand.getPagamenti().addAll(pagamenti);
	//invoco il WS del PSP ed aggiorno lo stato in base all'esito della chiamata
	ElencoPosizioniDebitorieEsitoType esitiPsp = new ElencoPosizioniDebitorieEsitoType();
	if (!payRegs.isEmpty()) {
	    esitiPsp = this.payConnectorService.annullaPosizioniDebitorieInPSP(connectorCommand, true);
	}
	RiferimentiPosizioniDebitorieHelper esitiH = new RiferimentiPosizioniDebitorieHelper(elencoesiti);
	for (EsitoOperazionePosizioneDebitoriaType esitoPsp : esitiPsp.getEsitoPosizione()) {
	    EsitoOperazionePosizioneDebitoriaType esitoNodo = (EsitoOperazionePosizioneDebitoriaType) esitiH
		    .findRiferimentoPosizioneById(esitoPsp.getIdPosizione());
	    boolean esitoOk = esitoPsp.isEsito();
	    esitoNodo.setEsito(esitoOk);
	    PopolamentoDatiHelper.completaDatiDaEsitoOperazioneDebitoriaType(esitoNodo, esitoPsp);
	    esitoNodo.setMessaggio(esitoPsp.getMessaggio());
	    esitoNodo.setCodiceErrore(esitoPsp.getCodiceErrore());
	    esitoNodo.setStato(esitoPsp.getStato());
	    someDone = someDone || esitoPsp.isEsito();
	    allDone = allDone && esitoPsp.isEsito();
	}
	response.setPosizioniInserite(elencoesiti);
	EsitoType esito = EsitoType.KO;
	String message = "nessuna posizione debitoria è stata annullata";
	if (allDone) {
	    esito = EsitoType.OK;
	    message = "è stata inoltrata la richiesta di annullamento di tutte le posizioni debitorie";
	} else if (someDone) {
	    esito = EsitoType.PARZIALE;
	    message = "a causa di errori non è stato possibile richiedere l'annullamento di alcune posizioni debitorie";
	}
	response.setEsito(esito);
	response.setMessaggio(message);
	popolaUUID(response.getPosizioniInserite());
	return response;
    }

    @Override
    public PayRegistrazioniContabili richiestaCaricamentoPosizioniECommittaDati(RegistrazioneContabileType regCont, TipiEvento tipoRichiesta,
	    PayConfigurationHelper payCfg, boolean otf) throws PayException {

	//Inserisco nel DB la registrazione contabile e le posizioni debitorie da caricare
	log.debug("richiestaCaricamentoPosizioniTrans - carico la Registrazione {}", regCont.getDescrizione());
	PayRegistrazioniContabili payRegCont = this.payRegistrazioniContabiliService.creaRegistrazioneContabile(regCont, payCfg, otf);
	for (PayPosizioniDebitorie payPosDeb : payRegCont.getPosizioniDebitorie()) {
	    log.debug("richiestaCaricamentoPosizioniTrans - registraRichiestaPerPosizioneDebitoria {}", payPosDeb.getId());
	    // e registro la richiesta di caricamento posizione nel PSP in PAY_RICHIESTE se previsto dalla configurazione
	    PayRichieste req = this.payRichiesteService.registraRichiestaPerPosizioneDebitoria(payPosDeb, tipoRichiesta);
	    log.debug("richiestaCaricamentoPosizioniTrans - impostaRichiestaCorrente {}", payPosDeb.getId());
	    payPosDeb.impostaRichiestaCorrente(req);
	}
	payPosizioniDebitorieDAO.flush();
	payPosizioniDebitorieDAO.commit();
	payPosizioniDebitorieDAO.flush();
	payPosizioniDebitorieDAO.clear();
	return payRegCont;
    }

    @Override
    public AttivaSessionePagamentoResponseType attivaSessionePagamento(AttivaSessionePagamentoType sesRequest) throws PayException {

	//validazione della richiesta
	RiferimentoPosizioneDebitoriaType refPos = sesRequest.getRiferimentoPosizione();
	PayPosizioniDebitorie pos = this.payPosizioniDebitorieService.findByRiferimentoPosizione(refPos);
	if (pos == null) {
	    StringBuilder sbEx = new StringBuilder("Impossibile attivare la sessione di pagamento. Posizione debitoria inesistente, id: ");
	    if (refPos.getIdPosizione() != null) {
		sbEx.append(refPos.getIdPosizione().intValue());
	    }
	    sbEx.append(", IUV: ").append(StringUtils.defaultString(refPos.getIUV()));
	    throw new PayInvalidRequestException(sbEx.toString());
	}
	//recupero il connettore che mi restituisce i dati per la sessione di pagamento
	IPayConnector conn = this.payConnectorService.getPayConnectorInstance();
	try {
	    //invoco il connettore che popola i dati della sessione da restituire
	    this.paySessioniPagamentoService.validateSessionePagamento(pos);
	    if (log.isInfoEnabled()) {
		log.info(
			"attivaSessionePagamento - invio al connettore della richiesta di creazione della sessione di pagamento per la posizione {}.",
			pos.getId());
	    }
	    AttivaSessionePagamentoResponseType sesResponse = conn.attivaSessionePagamento(pos);
	    if (sesResponse.isEsito()) {
		PayProfiliEntiCreditori prof = PayConfigurationHelper.getProfiloEnteCreditore();
		//registro nel DB i dati della sessione restituiti dal connettore se l'esito è positivo
		//nella PAY_SESSIONI_PAGAMENTO devo salvare l'url che mi è stato passato dal client 
		String urlEsito = sesRequest.getUrlRedirectEsito();
		String urlPortalePagamenti = StringUtils.isNotBlank(sesResponse.getPayUrl()) ? sesResponse.getPayUrl()
			: prof.getPayConnector().getUrlPortalePagamenti();
		sesResponse.setPayUrl(urlEsito);
		List<PayPosizioniDebitorie> list = new ArrayList<>();
		list.add(pos);
		PaySessioniPagamento sesPag = this.paySessioniPagamentoService.creaSessionePagamento(sesResponse, list);
		if (log.isInfoEnabled()) {
		    log.info("attivaSessionePagamento - sessione di pagamento attivata per la posizione {}. Id sessione {}.", pos.getId(),
			    sesPag.getIdSessionePagamento());
		}
		//restituisco al chiamante l'url del portale dei pagamenti restituito dal connettore o configurato in PAY_CONNECTOR_CONFIG
		sesResponse.setPayUrl(urlPortalePagamenti);
		if (sesResponse.getHttpMethodRequired() == null) {
		    sesResponse.setHttpMethodRequired(HttpMethodType.GET);
		}
	    } else {
		if (log.isInfoEnabled()) {
		    log.info("attivaSessionePagamento - Errore nell'attivazione della sessione di pagamento: {}", sesResponse.getDescEsito());
		}
	    }
	    return sesResponse;
	} catch (Exception e) {
	    String msg = "errore durante la creazione della sessione di pagamento per la posizione " + pos.getId() + ": " + e.toString();
	    log.error("attivaSessionePagamento - " + msg, e);
	    AttivaSessionePagamentoResponseType sesResponse = new AttivaSessionePagamentoResponseType();
	    sesResponse.setEsito(false);
	    sesResponse.setDescEsito(msg);
	    return sesResponse;
	}
    }

    @Override
    public ElencoDocumentiEsitoType generaFatture(List<DatiFatturaType> datiFatture) throws PayException {

	//loggo evento dell'avvenuta invocazione del servizio in nuova transazione
	ElencoDocumentiEsitoType results = new ElencoDocumentiEsitoType();
	IPayConnector ctor = this.payConnectorService.getPayConnectorInstance();
	//ciclo le richieste per predisporre il command con cui invocare il connettore
	String codiceComunicazione = this.generaCodiceComunicazione(TipiEvento.GENERA_FATTURA);
	GenerazioneFattureCommand connCmd = new GenerazioneFattureCommand(codiceComunicazione);
	for (DatiFatturaType reqFattura : datiFatture) {
	    //rcupero la posizione per cui è richiesta la fattura
	    PayPosizioniDebitorie posDeb = this.payPosizioniDebitorieService.findByRiferimentoPosizione(reqFattura);
	    PayStatoPagamenti posStatus = this.payStatoPagamentiService.getStatoPosizioneDebitoria(posDeb);
	    posDeb.impostaStatoCorrente(posStatus);
	    EsitoDocumentoPosizioneDebitoriaType esitoFatt = new EsitoDocumentoPosizioneDebitoriaType();
	    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoFatt, posDeb,
		    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(posDeb.getId().getCodice()));
	    esitoFatt.setErroreTemporaneo(false);
	    esitoFatt.setStato(StatoPagamentoType.fromValue(posStatus.getStato()));
	    esitoFatt.setTipoDocumento(TipoDocumentoType.FATTURA);
	    //il campo FK_OGGETTO_FATTURA può essere valorizzato con il messaggio XML della richiesta schedulata perciò verifico che sia valorizzata la data di creazione della fattura
	    if (posDeb.getDataGenerazioneFattura() != null) {
		//se la fattura esiste già per questa posizione debitoria la richiesta corrispondente non viene passata al connettore
		esitoFatt.setEsito(true);
		//se nella posizione è presente anche il riferimento al documento richiesto lo restiuisco al client
		PayDocumenti fattura = posDeb.getFattura();
		if (fattura != null && fattura.getId() != null && fattura.getId().getCodice() != null) {
		    esitoFatt.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
		    esitoFatt.setDocumento(fattura.getDataHandler());
		    esitoFatt.setNomeDocumento(fattura.getNomeDocumento());
		}
		//se non è presente
		else {
		    //verifico se ci sono richieste pendenti di recupero fattura per determinare il corretto statoDocumento da restiuire al client
		    PayRichieste pendingReq = this.payRichiesteService.getRichiestaApertaPerTipoEPosizione(posDeb, TipiEvento.RECUPERA_FATTURA_PSP);
		    if (pendingReq != null) {
			esitoFatt.setStatoDocumento(StatoDocumentoType.RICHIESTO);
		    } else {
			esitoFatt.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
		    }
		}
	    } else {
		//se la fattura non esiste 
		if (ctor.supportaGenerazioneFattura()) {
		    //se è supportata dal connettore la generazione della fattura registro la richiesta nel DB e predispongo la richiesta da inoltrare al connettore (che sarà eseguita immediatamente se non è solo schedulato)
		    PayRichieste rich = this.payRichiesteService.registraRichiestaGenerazioneFatturaTrans(posDeb, reqFattura,
			    TipiEvento.GENERA_FATTURA);
		    posDeb.impostaRichiestaCorrente(rich);
		    RichiestaFatturaCommand fattCmd = new RichiestaFatturaCommand();
		    fattCmd.setPosizioneDebitoria(posDeb);
		    fattCmd.setDatiRichiestaFattura(reqFattura);
		    esitoFatt.setStatoDocumento(StatoDocumentoType.RICHIESTO);
		    connCmd.getRichieste().add(fattCmd);
		} else {
		    //se la generazione fattura non è supportata restituisco non disponibile
		    esitoFatt.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
		}
		//predispongo l'esito per la richiesta da inoltrare al connettore, che sarà eventualmente aggiornato con l'esito effettivamente restituito dal connettore dopo la chiamata
		esitoFatt.setEsito(true);
	    }
	    results.getEsitoPosizione().add(esitoFatt);
	}
	ElencoDocumentiEsitoType esitiPsp = new ElencoDocumentiEsitoType();
	if (!payConnectorService.isServizioSoloSchedulato(TipiEvento.GENERA_FATTURA_PSP)) {
	    try {
		esitiPsp = this.payConnectorService.generaFatturePSP(connCmd);
	    } catch (Exception e) {
		String message = "errore nella chiamata al servizio generazione fatture del connettore " + ctor.getConnectorName() + ": " +
				 e.toString();
		log.error("generaFatture - " + message, e);
		//in caso di eccezione non gestita dal connettore predispongo dei risultati con esito false e il messaggio dell'eccezione solo per le richieste effettivamente passate al command
		for (RichiestaFatturaCommand richiestaFattura : connCmd.getRichieste()) {
		    EsitoDocumentoPosizioneDebitoriaType esitoErr = new EsitoDocumentoPosizioneDebitoriaType();
		    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoErr, richiestaFattura.getPosizioneDebitoria(),
			    payPosizioniDebitorieService
				    .findRiferimentiClientByPosizioneDebitoria(richiestaFattura.getPosizioneDebitoria().getId().getCodice()));
		    esitoErr.setEsito(false);
		    esitoErr.setErroreTemporaneo(false);
		    esitoErr.setMessaggio(message);
		    esitoErr.setTipoDocumento(TipoDocumentoType.FATTURA);
		    esitiPsp.getEsitoPosizione().add(esitoErr);
		}
	    }
	}
	boolean allDone = true;
	boolean someDone = false;
	RiferimentiPosizioniDebitorieHelper esitiPspH = new RiferimentiPosizioniDebitorieHelper(esitiPsp);
	//riporto gli esiti restituiti dal connettore nell'elenco di esiti da restituire al client
	for (EsitoDocumentoPosizioneDebitoriaType esitoNodo : results.getEsitoPosizione()) {
	    //creco il corrispondente esito restituito dal connettore
	    EsitoDocumentoPosizioneDebitoriaType esitoPsp = (EsitoDocumentoPosizioneDebitoriaType) esitiPspH
		    .findRiferimentoPosizioneById(esitoNodo.getIdPosizione());
	    if (esitoPsp != null) {
		esitoNodo.setEsito(esitoPsp.isEsito());
		esitoNodo.setMessaggio(esitoPsp.getMessaggio());
		esitoNodo.setCodiceErrore(esitoPsp.getCodiceErrore());
		esitoNodo.setErroreTemporaneo(esitoPsp.isErroreTemporaneo());
		esitoNodo.setNomeDocumento(esitoPsp.getNomeDocumento());
		esitoNodo.setDocumento(esitoPsp.getDocumento());
		esitoNodo.setStatoDocumento(esitoPsp.getStatoDocumento());
	    }
	    allDone = allDone && esitoNodo.isEsito();
	    someDone = someDone || esitoNodo.isEsito();
	}
	popolaUUIDDocs(results.getEsitoPosizione());
	return results;
    }

    @Override
    public ElencoDocumentiEsitoType inviaAvvisi(List<RiferimentoPosizioneDebitoriaType> refPosizioni) throws PayException {

	ElencoDocumentiEsitoType results = new ElencoDocumentiEsitoType();
	IPayConnector ctor = this.payConnectorService.getPayConnectorInstance();
	//ciclo le richieste per predisporre il command con cui invocare il connettore
	List<PayRegistrazioniContabili> registrazioni = new ArrayList<>();
	for (RiferimentoPosizioneDebitoriaType refPos : refPosizioni) {
	    //rcupero la registrazione contabile per cui è richiesto l'invio dell'avviso di pagamento PagoPA
	    PayPosizioniDebitorie posDeb = this.payPosizioniDebitorieService.findByRiferimentoPosizione(refPos);
	    PayStatoPagamenti posStatus = this.payStatoPagamentiService.getStatoPosizioneDebitoria(posDeb);
	    if (posStatus == null) {
		String msg = "La posizione debitoria  del connettore " + ctor.getConnectorName() + " con identificativo " + posDeb.getId() +
			     " del connettore " + ctor.getConnectorName() + " non ha uno stato valido.";
		log.error(msg);
		throw new PayException(msg);
	    }
	    posDeb.impostaStatoCorrente(posStatus);
	    EsitoDocumentoPosizioneDebitoriaType esitoFatt = new EsitoDocumentoPosizioneDebitoriaType();
	    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoFatt, posDeb, payPosizioniDebitorieService
		    .findRiferimentiClientByPosizioneDebitoria(posDeb.getId().getCodice()));
	    esitoFatt.setErroreTemporaneo(false);
	    esitoFatt.setStato(StatoPagamentoType.fromValue(posStatus.getStato()));
	    esitoFatt.setTipoDocumento(TipoDocumentoType.AVVISO);
	    //verifico che sia valorizzata la data di invio dell'avviso
	    if (posDeb.getDataInvioAvviso() != null) {
		//se l'avviso esiste già per questa posizione debitoria la richiesta corrispondente non viene passata al connettore
		esitoFatt.setEsito(true);
		//se nella posizione è presente anche il riferimento al documento richiesto lo restiuisco al client
		PayDocumenti avviso = posDeb.getAvviso();
		if (avviso != null && avviso.getId() != null && avviso.getId().getCodice() != null) {
		    esitoFatt.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
		    esitoFatt.setDocumento(avviso.getDataHandler());
		    esitoFatt.setNomeDocumento(avviso.getNomeDocumento());
		}
		//se non è presente
		else {
		    //verifico se ci sono richieste pendenti di invio avviso per determinare il corretto statoDocumento da restiuire al client
		    PayRichieste pendingReq = this.payRichiesteService.getRichiestaApertaPerTipoEPosizione(posDeb, TipiEvento.RECUPERA_AVVISO_PSP);
		    if (pendingReq != null) {
			esitoFatt.setStatoDocumento(StatoDocumentoType.RICHIESTO);
		    } else {
			esitoFatt.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
		    }
		}
	    } else {
		if (ctor.supportaAvvisoPagamento()) {
		    //se l'avviso non esiste e il connettore supporta la generazione degli avvisi registro la richiesta nel DB e predispongo la richiesta da inoltrare al connettore (che sarà eseguita immediatamente se non è solo schedulato)
		    PayRichieste rich = this.payRichiesteService.registraRichiestaPerPosizioneDebitoriaTrans(posDeb, TipiEvento.INVIA_AVVISO);
		    posDeb.impostaRichiestaCorrente(rich);
		    //se non è già presente imposto nel command la registrazione contabile per cui deve essere inviato l'avviso
		    /*
		     * Gli oggetti PayRegistrazioneContabile che vengono passsati nel command sono istanze detached dalla
		     * session di hibernate e nella collection di posizioni debitorie al loro interno vengono impostati i
		     * riferimenti soltanto alle posizioni debitorie ricevute nella richiesta. Se per una registrazione
		     * contabile che contiene più di una posizione debitoria la logica del connettore prevedesse l'invio
		     * dell'avviso per tutte le posizioni comunque il connettore potrà reinterrogare il DB per recuperare le
		     * posizioni eventualmente mancanti. Se il connettore implementasse questo tipo di logica nella response
		     * ci sarebbero esiti anche per le posizioni debitorie non richieste. E' IMPORTANTE che non si persista
		     * mai la registrazione contabile passata nel command perché se la collection delle posizioni debitorie
		     * non corrispondesse coi dati del DB hibernate cercherebbe di aggiornare le relazioni fra le tabelle
		     * (darebbe errore perché non si può scollagare una posizione dalla registrazione contabile)
		     */
		    PayRegistrazioniContabili payRegCont = new PayRegistrazioniContabili();
		    PkId regId = new PkId(posDeb.getRegistrazioneContabile().getId().getCodice());
		    payRegCont.setId(regId);
		    int regContIdx = registrazioni.indexOf(payRegCont);
		    if (regContIdx > -1) {
			payRegCont = registrazioni.get(regContIdx);
		    } else {
			payRegCont = this.payRegistrazioniContabiliService.findById(regId);
			if (payRegCont != null) {
			    payRegCont.setPosizioniDebitorie(new HashSet<PayPosizioniDebitorie>());
			    registrazioni.add(payRegCont);
			} else {
			    throw new RuntimeException("Registrazione contabile associata alla posizione debitoria " + posDeb.getId() +
						       " non trovata. Impossibile richiedere l'invio dell'avviso");
			}
		    }
		    esitoFatt.setStatoDocumento(StatoDocumentoType.RICHIESTO);
		    payRegCont.getPosizioniDebitorie().add(posDeb);
		} else {
		    esitoFatt.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
		    esitoFatt.setMessaggio("servizio di generazione dell'avviso non configurato per il connettore");
		}
		//predispongo l'esito per la richiesta da inoltrare al connettore, che sarà aggiornato con l'esito effettivamente restituito dal connettore dopo la chiamata
		esitoFatt.setEsito(true);
	    }
	    results.getEsitoPosizione().add(esitoFatt);
	}
	boolean allDone = true;
	boolean someDone = false;
	ElencoDocumentiEsitoType esitiPsp = new ElencoDocumentiEsitoType();
	if (!registrazioni.isEmpty()) {
	    String codiceComunicazione = this.generaCodiceComunicazione(TipiEvento.INVIA_AVVISO);
	    PosizioniDebitorieCommand connCmd = posizioniDebitorieCommandService.popolaPosizioniDebitorie(registrazioni, codiceComunicazione);
	    if (!payConnectorService.isServizioSoloSchedulato(TipiEvento.GENERA_AVVISO_PSP)) {
		try {
		    esitiPsp = this.payConnectorService.inviaAvvisiPSP(connCmd);
		} catch (Exception e) {
		    String message = "errore nella chiamata al servizio di invio degli avvisi del connettore " + ctor.getConnectorName() + ": " +
				     e.toString();
		    log.error("inviaAvvisi - " + message, e);
		    //in caso di eccezione non gestita dal connettore predispongo dei risultati con esito false e il messaggio dell'eccezione solo per le richieste effettivamente passate al command
		    for (PayRegistrazioniContabili regCont : connCmd.getRegistrazioniPosizioni()) {
			for (PayPosizioniDebitorie pos : regCont.getPosizioniDebitorie()) {
			    EsitoDocumentoPosizioneDebitoriaType esitoErr = new EsitoDocumentoPosizioneDebitoriaType();
			    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoErr, pos, payPosizioniDebitorieService
				    .findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
			    esitoErr.setEsito(false);
			    esitoErr.setErroreTemporaneo(false);
			    esitoErr.setMessaggio(message);
			    esitoErr.setTipoDocumento(TipoDocumentoType.AVVISO);
			    esitiPsp.getEsitoPosizione().add(esitoErr);
			}
		    }
		}
	    }
	}
	RiferimentiPosizioniDebitorieHelper esitiPspH = new RiferimentiPosizioniDebitorieHelper(esitiPsp);
	//riporto gli esiti restituiti dal connettore nell'elenco di esiti da restituire al client
	for (EsitoDocumentoPosizioneDebitoriaType esitoNodo : results.getEsitoPosizione()) {
	    //creco il corrispondente esito restituito dal connettore
	    EsitoDocumentoPosizioneDebitoriaType esitoPsp = (EsitoDocumentoPosizioneDebitoriaType) esitiPspH
		    .findRiferimentoPosizioneById(esitoNodo.getIdPosizione());
	    if (esitoPsp != null) {
		esitoNodo.setEsito(esitoPsp.isEsito());
		esitoNodo.setMessaggio(esitoPsp.getMessaggio());
		esitoNodo.setCodiceErrore(esitoPsp.getCodiceErrore());
		esitoNodo.setErroreTemporaneo(esitoPsp.isErroreTemporaneo());
		esitoNodo.setNomeDocumento(esitoPsp.getNomeDocumento());
		// esitoNodo.setDocumento(esitoPsp.getDocumento()); // il datahandler potrebbe esser stato consumato dalla registrazione lo ricavo dalla posizione debitoria
		esitoNodo.setDocumento(getAvvisoFromPosizioneDebitoria(esitoPsp));
		esitoNodo.setStatoDocumento(esitoPsp.getStatoDocumento());
	    }
	    allDone = allDone && esitoNodo.isEsito();
	    someDone = someDone || esitoNodo.isEsito();
	}
	popolaUUIDDocs(results.getEsitoPosizione());
	return results;
    }

    @Override
    public ElencoDocumentiEsitoType scaricaRicevute(List<RiferimentoPosizioneDebitoriaType> refPosizioni) throws PayException {

	ElencoDocumentiEsitoType results = new ElencoDocumentiEsitoType();
	IPayConnector ctor = this.payConnectorService.getPayConnectorInstance();
	//ciclo le richieste per predisporre il command con cui invocare il connettore
	String codiceComunicazione = this.generaCodiceComunicazione(TipiEvento.RECUPERA_RICEVUTA);
	RichiestaSuListaPosizioniCommand connCmd = new RichiestaSuListaPosizioniCommand(codiceComunicazione);
	for (RiferimentoPosizioneDebitoriaType refPos : refPosizioni) {
	    //rcupero la posizione per cui è richiesta la ricevuta
	    PayPosizioniDebitorie posDeb = this.payPosizioniDebitorieService.findByRiferimentoPosizione(refPos);
	    PayStatoPagamenti posStatus = this.payStatoPagamentiService.getStatoPosizioneDebitoria(posDeb);
	    posDeb.impostaStatoCorrente(posStatus);
	    EsitoDocumentoPosizioneDebitoriaType esitoFatt = new EsitoDocumentoPosizioneDebitoriaType();
	    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoFatt, posDeb,
		    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(posDeb.getId().getCodice()));
	    esitoFatt.setErroreTemporaneo(false);
	    esitoFatt.setStato(StatoPagamentoType.fromValue(posStatus.getStato()));
	    esitoFatt.setTipoDocumento(TipoDocumentoType.RICEVUTA);
	    //verifico se ci sono richieste pendenti di recupero ricevuta per determinare il corretto statoDocumento da restiuire al client se non c'è la ricevuta associata alla posizione
	    PayRichieste pendingReq = this.payRichiesteService.getRichiestaApertaPerTipoEPosizione(posDeb, TipiEvento.RECUPERA_RICEVUTA_PSP);
	    if (posDeb.getRicevuta() != null && posDeb.getRicevuta().getId() != null && posDeb.getRicevuta().getId().getCodice() != null) {
		//se la ricevuta esiste già per questa posizione debitoria la richiesta corrispondente non viene passata al connettore
		esitoFatt.setEsito(true);
		//se nella posizione è presente anche il riferimento al documento richiesto lo restiuisco al client
		PayDocumenti ricevuta = posDeb.getRicevuta();
		esitoFatt.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
		esitoFatt.setDocumento(ricevuta.getDataHandler());
		esitoFatt.setNomeDocumento(ricevuta.getNomeDocumento());
	    } else {
		//se la ricevuta non esiste registro la richiesta nel DB e predispongo la richiesta da inoltrare al connettore (che sarà eseguita immediatamente se non è solo schedulato)
		if (pendingReq == null) {
		    pendingReq = this.payRichiesteService.registraRichiestaPerPosizioneDebitoriaTrans(posDeb, TipiEvento.RECUPERA_RICEVUTA);
		}
		posDeb.impostaRichiestaCorrente(pendingReq);
		connCmd.getPosizioni().add(posDeb);
		//predispongo l'esito per la richiesta da inoltrare al connettore, che sarà aggiornato con l'esito effettivamente restituito dal connettore dopo la chiamata
		esitoFatt.setEsito(true);
		esitoFatt.setStatoDocumento(StatoDocumentoType.RICHIESTO);
	    }
	    results.getEsitoPosizione().add(esitoFatt);
	}
	ElencoDocumentiEsitoType esitiPsp = new ElencoDocumentiEsitoType();
	if (!payConnectorService.isServizioSoloSchedulato(TipiEvento.RECUPERA_RICEVUTA_PSP)) {
	    try {
		esitiPsp = this.payConnectorService.scaricaRicevutePSP(connCmd);
	    } catch (Exception e) {
		String message = "errore nella chiamata al servizio download delle ricevute del connettore " + ctor.getConnectorName() + ": " +
				 e.toString();
		log.error("scaricaRicevute - " + message, e);
		//in caso di eccezione non gestita dal connettore predispongo dei risultati con esito false e il messaggio dell'eccezione solo per le richieste effettivamente passate al command
		for (PayPosizioniDebitorie cmdPos : connCmd.getPosizioni()) {
		    EsitoDocumentoPosizioneDebitoriaType esitoErr = new EsitoDocumentoPosizioneDebitoriaType();
		    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoErr, cmdPos,
			    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(cmdPos.getId().getCodice()));
		    esitoErr.setEsito(false);
		    esitoErr.setErroreTemporaneo(false);
		    esitoErr.setMessaggio(message);
		    esitoErr.setTipoDocumento(TipoDocumentoType.FATTURA);
		    esitiPsp.getEsitoPosizione().add(esitoErr);
		}
	    }
	}
	boolean allDone = true;
	boolean someDone = false;
	RiferimentiPosizioniDebitorieHelper esitiPspH = new RiferimentiPosizioniDebitorieHelper(esitiPsp);
	//riporto gli esiti restituiti dal connettore nell'elenco di esiti da restituire al client
	for (EsitoDocumentoPosizioneDebitoriaType esitoNodo : results.getEsitoPosizione()) {
	    //creco il corrispondente esito restituito dal connettore
	    EsitoDocumentoPosizioneDebitoriaType esitoPsp = (EsitoDocumentoPosizioneDebitoriaType) esitiPspH
		    .findRiferimentoPosizioneById(esitoNodo.getIdPosizione());
	    if (esitoPsp != null) {
		esitoNodo.setEsito(esitoPsp.isEsito());
		esitoNodo.setMessaggio(esitoPsp.getMessaggio());
		esitoNodo.setCodiceErrore(esitoPsp.getCodiceErrore());
		esitoNodo.setErroreTemporaneo(esitoPsp.isErroreTemporaneo());
		esitoNodo.setNomeDocumento(esitoPsp.getNomeDocumento());
		esitoNodo.setDocumento(getRicevutaFromPosizioneDebitoria(esitoPsp));
		esitoNodo.setStatoDocumento(esitoPsp.getStatoDocumento());
	    }
	    allDone = allDone && esitoNodo.isEsito();
	    someDone = someDone || esitoNodo.isEsito();
	}
	popolaUUIDDocs(results.getEsitoPosizione());
	return results;
    }

    @Override
    public ElencoDocumentiType getElencoTipiDocumenti(DocumentiPosizioneDebitoriaType documentiPosizioneDebitorieType) throws PayException {

	IPayConnector ctor = this.payConnectorService.getPayConnectorInstance();
	ElencoDocumentiType response = new ElencoDocumentiType();
	List<TipoDocumentoType> tipoDocumentoEnum = new ArrayList<>();
	int idPosizione = documentiPosizioneDebitorieType.getRiferimentoPosizione().getIdPosizione().intValue();
	PayPosizioniDebitorie payPos = this.payPosizioniDebitorieService.findById(new PkId(idPosizione));
	if (payPos == null) {
	    response.setErroreTipoDocumento("Non è stato trovato nessun posizione con id: " + idPosizione);
	} else {
	    PayStatoPagamenti statoPagamenti = this.payStatoPagamentiService.getStatoPosizioneDebitoria(payPos);
	    if (statoPagamenti.getStato().equalsIgnoreCase(PayStatoPagamenti.StatiPagamento.ACQUISITO.value())) {
		if (!payConnectorService.isSchedulerAttivo(TipiEvento.INVIA_POSIZIONI_A_PSP)) {
		    response.setErroreTipoDocumento("Non è possibile ottenere un documento di una posizione con stato ACQUISITO");
		}
	    } else if (statoPagamenti.getStato().equalsIgnoreCase(PayStatoPagamenti.StatiPagamento.CON_ERRORE.value())) {
		response.setErroreTipoDocumento("Non è possibile ottenere un documento di una posizione con stato CON_ERRORE");
	    } else {
		if (documentiPosizioneDebitorieType.getTipoDocumento() == null) {
		    tipoDocumentoEnum.add(TipoDocumentoType.AVVISO);
		    tipoDocumentoEnum.add(TipoDocumentoType.FATTURA);
		    tipoDocumentoEnum.add(TipoDocumentoType.RICEVUTA);
		} else {
		    tipoDocumentoEnum.add(documentiPosizioneDebitorieType.getTipoDocumento());
		}
	    }
	    for (TipoDocumentoType tipoDocumentoType : tipoDocumentoEnum) {
		switch (tipoDocumentoType) {
		case AVVISO:
		    PayDocumenti avviso = payPos.getAvviso();
		    if (payPos.getDataInvioAvviso() != null && avviso != null && avviso.getId() != null && avviso.getId().getCodice() != null) {
			response.getTipoDocumento().add(TipoDocumentoType.AVVISO);
		    } else {
			if (ctor.supportaAvvisoPagamento()) {
			    response.getTipoDocumento().add(TipoDocumentoType.AVVISO);
			}
		    }
		    break;
		case FATTURA:
		    PayDocumenti fattura = payPos.getFattura();
		    if (payPos.getDataGenerazioneFattura() != null && fattura != null && fattura.getId() != null
			    && fattura.getId().getCodice() != null) {
			response.getTipoDocumento().add(TipoDocumentoType.FATTURA);
		    } else {
			if (ctor.supportaGenerazioneFattura()) {
			    response.getTipoDocumento().add(TipoDocumentoType.FATTURA);
			}
		    }
		    break;
		case RICEVUTA:
		    PayDocumenti ricevuta = payPos.getRicevuta();
		    if (ricevuta != null && ricevuta.getId() != null && ricevuta.getId().getCodice() != null) {
			response.getTipoDocumento().add(TipoDocumentoType.RICEVUTA);
		    } else {
			if (ctor.supportaRicevutaTelematica()) {
			    response.getTipoDocumento().add(TipoDocumentoType.RICEVUTA);
			}
		    }
		    break;
		default:
		    response.setErroreTipoDocumento("Tipo di documento non è supportato: " + tipoDocumentoType.name());
		    break;
		}
	    }
	}
	return response;
    }

    @Override
    public void modificaDataScadenzaPosizioneDebitoria(ModificaDataScadenzaType modificaDataScadenzaType) throws PayException {

	// Verifica che la posizione debitoria esista
	log.debug("modificaDataScadenzaPosizioneDebitoria");
	int idPosizione = modificaDataScadenzaType.getRiferimentoPosizione().getIdPosizione().intValue();
	log.debug("modificaDataScadenzaPosizioneDebitoria {}", idPosizione);
	PayPosizioniDebitorie payPos = this.payPosizioniDebitorieService.findById(new PkId(idPosizione));
	if (payPos == null) {
	    log.error("modificaDataScadenzaPosizioneDebitoria posizione nulla con id {} e profilo {}", idPosizione,
		    modificaDataScadenzaType.getCfEnteCreditore());
	    throw new PayException("Non è stata trovata nessuna posizione con id: " + idPosizione + " per il profilo " +
				   modificaDataScadenzaType.getCfEnteCreditore());
	}
	log.debug("modificaDataScadenzaPosizioneDebitoria cerco gli stati delle posizioni per la posizione {} e profilo {}", idPosizione,
		modificaDataScadenzaType.getCfEnteCreditore());
	List<PayStatoPagamenti> statiPos = this.payStatoPagamentiService.getCronologiaPosizioneDebitoria(idPosizione);
	StatoPosizioneDebitoriaHelper statusHelper = new StatoPosizioneDebitoriaHelper(statiPos);
	if (!statusHelper.possoModificareLaDataDiScadenza()) {
	    String statoCorrente = "Non definito";
	    if (statiPos != null && !statiPos.isEmpty()) {
		statoCorrente = statiPos.get(0).getStato();
	    }
	    log.debug("modificaDataScadenzaPosizioneDebitoria non posso modificare la data scadenza della posizione {} perché in stato {}",
		    idPosizione, statoCorrente);
	    throw new PayException("Non è possibile modificare lo stato della posizione in quanto si trova nello stato " + statoCorrente);
	}
	Date dataScadenza = payPosizioniDebitorieService.dataScadenza(modificaDataScadenzaType.getDataScadenza());
	IPayConnector connector = this.payConnectorService.getPayConnectorInstance();
	try {
	    if (connector.supportaModificaDataScadenza()) {
		log.debug("modificaDataScadenzaPosizioneDebitoria il connettore supporta la modifica data scadenza chiamo il metodo del connettore");
		this.payConnectorService.modificaDataScadenzaPosizioneInPsp(idPosizione, dataScadenza);
		log.debug("modificaDataScadenzaPosizioneDebitoria il connettore è stato invocato correttamente");
	    }
	    log.debug("modificaDataScadenzaPosizioneDebitoria aggiorno la data scadenza della posizione debitoria");
	    payPos.setDataScadenza(dataScadenza);
	    this.payPosizioniDebitorieService.update(payPos);
	} catch (Exception e) {
	    log.error("modificaDataScadenzaPosizioneDebitoria {}", e.getMessage(), e);
	    throw new PayException(e.getMessage(), e);
	}
	try {
	    notificheRabbitService.notificaAggiornamentoDataScadenza(payPos.getId().getCodice(), dataScadenza);
	} catch (Exception e) {
	    log.error("Errore in notifica data scadenza per la posizione " + payPos.getId(), e);
	}
    }

    @Override
    public void modificaDataFineValiditaPosizioneDebitoria(ModificaDataFineValiditaType modificaDataFineValiditaType) throws PayException {

	// Verifica che la posizione debitoria esista
	log.debug("modificaDataFineValiditaPosizioneDebitoria");
	int idPosizione = modificaDataFineValiditaType.getRiferimentoPosizione().getIdPosizione().intValue();
	log.debug("modificaDataFineValiditaPosizioneDebitoria {}", idPosizione);
	PayPosizioniDebitorie payPos = this.payPosizioniDebitorieService.findById(new PkId(idPosizione));
	if (payPos == null) {
	    log.error("modificaDataFineValiditaPosizioneDebitoria posizione nulla con id {} e profilo {}", idPosizione,
		    modificaDataFineValiditaType.getCfEnteCreditore());
	    throw new PayException("Non è stata trovata nessuna posizione con id: " + idPosizione + " per il profilo " +
				   modificaDataFineValiditaType.getCfEnteCreditore());
	}
	log.debug("modificaDataFineValiditaPosizioneDebitoria cerco gli stati delle posizioni per la posizione {} e profilo {}", idPosizione,
		modificaDataFineValiditaType.getCfEnteCreditore());
	List<PayStatoPagamenti> statiPos = this.payStatoPagamentiService.getCronologiaPosizioneDebitoria(idPosizione);
	StatoPosizioneDebitoriaHelper statusHelper = new StatoPosizioneDebitoriaHelper(statiPos);
	if (!statusHelper.possoModificareLaDataFineValidita()) {
	    String statoCorrente = "Non definito";
	    if (statiPos != null && !statiPos.isEmpty()) {
		statoCorrente = statiPos.get(0).getStato();
	    }
	    log.debug("modificaDataFineValiditaPosizioneDebitoria non posso modificare la data scadenza della posizione {} perché in stato {}",
		    idPosizione, statoCorrente);
	    throw new PayException("Non è possibile modificare lo stato della posizione in quanto si trova nello stato " + statoCorrente);
	}
	Date dataFineValidita = payPosizioniDebitorieService.dataFineValidita(modificaDataFineValiditaType.getDataFineValidita());
	IPayConnector connector = this.payConnectorService.getPayConnectorInstance();
	try {
	    if (connector.supportaModificaDataFineValidita()) {
		log.debug(
			"modificaDataFineValiditaPosizioneDebitoria il connettore supporta la modifica data fine validita chiamo il metodo del connettore");
		this.payConnectorService.modificaDataFineValiditaPosizioneInPsp(idPosizione, dataFineValidita);
		log.debug("modificaDataFineValiditaPosizioneDebitoria il connettore è stato invocato correttamente");
	    }
	    log.debug("modificaDataFineValiditaPosizioneDebitoria aggiorno la data scadenza della posizione debitoria");
	    payPos.setDataFineValidita(dataFineValidita);
	    this.payPosizioniDebitorieService.update(payPos);
	} catch (Exception e) {
	    log.error("modificaDataFineValiditaPosizioneDebitoria {}", e.getMessage(), e);
	    throw new PayException(e.getMessage(), e);
	}
    }

    private void checkSupportaMolteCausaliRaggupate(PosizioniDebitorieCommand cmd, IPayConnector conn) throws PayException {

	if (!conn.supportaMolteCausaliRaggruppate() && cmd.getMappaInfoCausali().keySet().size() > 1) {
	    log.error("Il connettore {} non supporta la creazione di posizioni debitorie con più causali di pagamento", conn.getConnectorName());
	    throw new PayException(
		    "Il connettore " + conn.getConnectorName() + " non supporta la creazione di posizioni debitorie con più causali di pagamento");
	}
    }

    private DataHandler getAvvisoFromPosizioneDebitoria(EsitoDocumentoPosizioneDebitoriaType esitoPsp) {

	if (esitoPsp.getIdPosizione() == null) {
	    return null;
	}
	PayPosizioniDebitorie pos = payPosizioniDebitorieService.findById(new PkId(esitoPsp.getIdPosizione().intValue()));
	if (pos != null && pos.getAvviso() != null && pos.getAvviso().getId() != null && pos.getAvviso().getId().getCodice() != null) {
	    return pos.getAvviso().getDataHandler();
	}
	return null;
    }

    private DataHandler getRicevutaFromPosizioneDebitoria(EsitoDocumentoPosizioneDebitoriaType esitoPsp) {

	if (esitoPsp.getIdPosizione() == null) {
	    return null;
	}
	PayPosizioniDebitorie pos = payPosizioniDebitorieService.findById(new PkId(esitoPsp.getIdPosizione().intValue()));
	if (pos != null && pos.getRicevuta() != null && pos.getRicevuta().getId() != null && pos.getRicevuta().getId().getCodice() != null) {
	    return pos.getRicevuta().getDataHandler();
	}
	return null;
    }

    private void popolaUUID(ElencoPosizioniDebitorieEsitoType posizioniInserite) {

	if (posizioniInserite != null && !posizioniInserite.getEsitoPosizione().isEmpty()) {
	    popolaUUID(posizioniInserite.getEsitoPosizione());
	}
    }

    private void popolaUUIDDocs(List<EsitoDocumentoPosizioneDebitoriaType> esitoPosizione) {

	if (esitoPosizione != null && !esitoPosizione.isEmpty()) {
	    for (EsitoDocumentoPosizioneDebitoriaType ed : esitoPosizione) {
		if (ed.getIdPosizione() != null && StringUtils.isBlank(ed.getUuid())) {
		    PayPosizioniDebitorie pd = payPosizioniDebitorieService.findById(new PkId(ed.getIdPosizione().intValue()));
		    if (pd != null) {
			ed.setUuid(pd.getUuid());
		    }
		}
	    }
	}
    }

    private void popolaUUIDDaStato(VerificaStatoPosizioniResponseType response) {

	if (response != null && response.getStatoPosizioni() != null && !response.getStatoPosizioni().getStatoPosizioni().isEmpty()) {
	    for (StatoPosizioneType sp : response.getStatoPosizioni().getStatoPosizioni()) {
		if (sp.getIdPosizione() != null && StringUtils.isBlank(sp.getUuid())) {
		    PayPosizioniDebitorie pd = payPosizioniDebitorieService.findById(new PkId(sp.getIdPosizione().intValue()));
		    if (pd != null) {
			sp.setUuid(pd.getUuid());
		    }
		}
	    }
	}
    }

    private void popolaUUID(List<EsitoOperazionePosizioneDebitoriaType> posizioniInserite) {

	if (posizioniInserite != null && !posizioniInserite.isEmpty()) {
	    for (EsitoOperazionePosizioneDebitoriaType pi : posizioniInserite) {
		if (pi.getIdPosizione() != null && StringUtils.isBlank(pi.getUuid())) {
		    PayPosizioniDebitorie pd = payPosizioniDebitorieService.findById(new PkId(pi.getIdPosizione().intValue()));
		    if (pd != null) {
			pi.setUuid(pd.getUuid());
		    }
		}
	    }
	}
    }

    private String generaCodiceComunicazione(TipiEvento evento) {

	String idPrefix = evento.isInput() ? "IN_" : "OUT_";
	String uid = null;
	try {
	    uid = this.payConnectorService.getPayConnectorInstance().generaIdMessaggio();
	} catch (PayConfigurationException e) {
	    uid = UUID.randomUUID().toString();
	}
	return idPrefix + uid;
    }
}
