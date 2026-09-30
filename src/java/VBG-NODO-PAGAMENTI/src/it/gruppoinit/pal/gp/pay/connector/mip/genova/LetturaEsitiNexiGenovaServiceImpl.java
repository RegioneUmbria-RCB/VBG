package it.gruppoinit.pal.gp.pay.connector.mip.genova;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnectorCaricamentoMassivo;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayRegistrazioniCausaliService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.helper.EsitoElaborazione;
import it.gruppoinit.pal.gp.pay.service.helper.InfoCausaleRestBean;
import it.gruppoinit.pal.gp.pay.service.helper.InfoCausaliPerConnettore;
import it.gruppoinit.pal.gp.pay.tracciati.TracciatoRecordSet;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordEsiti;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordEsiti.CAMPI_ESITO;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;

@Service
public class LetturaEsitiNexiGenovaServiceImpl extends BaseOperazioniMassiveService implements ILetturaEsitiNexiGenovaService {

    private enum TIPO_FILE_NEXI {
	ESITI_CARICAMENTO,
	NOTIFICHE_PAGAMENTO
    }

    private static final Logger log = LoggerFactory.getLogger(LetturaEsitiNexiGenovaServiceImpl.class);
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;
    @Autowired
    private PayRegistrazioniCausaliService causaliService;

    @Override
    public EsitoElaborazione leggiEsiti(IPayConnector connector, Map<String, String> params) {
	// DEVE TROVARE LE POSIZIONI DEBITORIE CARICATE MASSIVAMENTE CON STATO CaricamentoMassivoStatiEnum.PROCESSATO.getValore()

	// trovo i codici versamento e elaboro i file trovati per 
	// esiti caricamento 
	// ==== cartella ==> Root\PG_<Nome Procedura Gestionale>\Esiti 
	// ===== nome file  MER_VAR_AVV_APPMER_38300000000000002082_CodiceAvviso_20221220152041.txt maschera ricerca file <TIPOLOGIA_ENTRATA>_*_CodiceAvviso_*.txt 
	// notifiche pagamento
	// ==== cartella ==> Root\PG_<Nome Procedura Gestionale>\Pagati 
	// ===== nome file  MER_VAR_AVV_APPMER_NODO_Notifica_20230117105303.txt maschera ricerca file <TIPOLOGIA_ENTRATA>_*_Notifica_*.txt 
	// la notifica potrebbe arrivare anche traemite WS
	boolean esitoGlobale = true;
	Set<String> codiciVersamento = getCodiciVersamento(params.get(IPayConnectorCaricamentoMassivo.PARAMS_ELABORAZIONE.CODICE_PROFILO.name()));
	log.debug("leggiEsiti codici versamento = {}", codiciVersamento);
	EsitoElaborazione esitoCaricamenti = leggiEsitiCaricamento(connector, codiciVersamento, TIPO_FILE_NEXI.ESITI_CARICAMENTO);
	EsitoElaborazione esitoPagati = leggiEsitiCaricamento(connector, codiciVersamento, TIPO_FILE_NEXI.NOTIFICHE_PAGAMENTO);
	esitoGlobale = esitoCaricamenti.isEsito() && esitoPagati.isEsito();
	String messaggio = "";
	if (!esitoCaricamenti.isEsito()) {
	    messaggio += esitoCaricamenti.getMessaggio();
	}
	if (!esitoPagati.isEsito()) {
	    messaggio += esitoPagati.getMessaggio();
	}
	return new EsitoElaborazione(esitoGlobale, messaggio);
    }

    private EsitoElaborazione leggiEsitiCaricamento(IPayConnector connector, Set<String> codiciVersamento, TIPO_FILE_NEXI tipoFile) {

	// esiti caricamento 
	// ==== cartella ==> Root\PG_<Nome Procedura Gestionale>\Esiti 
	// ===== nome file  MER_VAR_AVV_APPMER_38300000000000002082_CodiceAvviso_20221220152041.txt maschera ricerca file <TIPOLOGIA_ENTRATA>_*_CodiceAvviso_*.txt 
	if (codiciVersamento.isEmpty()) {
	    return new EsitoElaborazione(true, null);
	}
	List<EsitoElaborazione> esitiElaborazioni = new ArrayList<>();
	for (String codiceVersamento : codiciVersamento) {
	    log.debug("leggiEsiti elaboro il codice versamento = {}", codiceVersamento);
	    esitiElaborazioni.add(analizzaEsitiFlussoV2(codiceVersamento, connector, tipoFile));
	}
	StringBuilder messaggio = new StringBuilder();
	boolean esitoGlobale = true;
	for (EsitoElaborazione esitoElaborazione : esitiElaborazioni) {
	    if (!esitoElaborazione.isEsito()) {
		esitoGlobale = false;
		if (StringUtils.isNotBlank(esitoElaborazione.getMessaggio())) {
		    messaggio.append(esitoElaborazione.getMessaggio());
		}
	    }
	}
	return new EsitoElaborazione(esitoGlobale, messaggio.toString());
    }

    private EsitoElaborazione analizzaEsitiFlussoV2(String codiceVersamento, IPayConnector connector, TIPO_FILE_NEXI tipoFile) {

	BaseFolderCaricamento config = getWsCaricamentoLetturaEsiti(connector);
	TracciatoRecordSet<TracciatoRecordEsiti> parsedRecords = null;
	Set<String> rateProcessate = null;
	String errorAll = null;
	MipFileHelper fh = null;
	Set<File> files = new HashSet<>();
	String idLotto = "";
	String operation = "creazione del servizio di scambio files";
	boolean isNotificaPagamento = tipoFile.equals(TIPO_FILE_NEXI.NOTIFICHE_PAGAMENTO);
	boolean esitoOperazione = true;
	StringBuilder messaggioEsito = new StringBuilder();
	try {
	    fh = new MipFileHelper(codiceVersamento, config);
	    operation = "connessione al servizio di scambio files";
	    operation = "ricerca dei files degli esiti ";
	    if (isNotificaPagamento) {
		files = fh.trovaFileCartellaPagati();
	    } else {
		files = fh.trovaFileCartellaEsiti();
	    }
	    if (log.isInfoEnabled()) {
		log.info("analizzaEsitiFlussoV2 - files di esito trovati per l'id lotto {}, codice versamento {}: {}", idLotto, codiceVersamento,
			ArrayUtils.toString(files));
	    }
	} catch (Exception e1) {
	    errorAll = MessageFormat.format("Errore durante la {0} all URL {1} per id lotto {2}.", operation, config.getEndpointURL(), idLotto);
	    esitoOperazione = false;
	    messaggioEsito.append("\n").append(errorAll);
	    log.error(errorAll, e1);
	}
	Iterator<File> filesIter = files.iterator();
	while (filesIter.hasNext()) {
	    File fileEsiti = filesIter.next();
	    try {
		//invoco il metodo del nexifilehelper che parsa il file degli esiti, 
		parsedRecords = fh.parseEsiti(fileEsiti.getName(), isNotificaPagamento);
		if (log.isInfoEnabled()) {
		    log.info("analizzaEsitiFlussoV2 - letti {} record di esito dal file degli esiti {} per il lotto {}",
			    parsedRecords.getRecords().size(), fileEsiti.getName(), idLotto);
		}
		//recupero dal file temporaneo l'elenco delle rate già processate in passaggi precedenti dello scheduler
		rateProcessate = fh.getRateProcessate(fileEsiti.getName(), isNotificaPagamento);
		if (log.isInfoEnabled()) {
		    log.info("analizzaEsitiFlussoV2 - {} record di esito risultano già processati in precedenza per il file degli esiti {}",
			    rateProcessate.size(), fileEsiti.getName());
		}
	    } catch (PayException e) {
		log.error("analizzaEsitiFlussoV2 - " + errorAll, e);
		errorAll = MessageFormat.format("impossibile leggere il file degli esiti {0} per id lotto {1}. {2} ", fileEsiti.getName(), idLotto,
			e.toString());
		esitoOperazione = false;
		messaggioEsito.append("\n").append(errorAll);
	    }
	    for (TracciatoRecordEsiti recEsito : parsedRecords.getRecords()) {
		String idDebito = (String) recEsito.getValue(CAMPI_ESITO.identificativo_debito.name());
		String idRata = (String) recEsito.getValue(CAMPI_ESITO.identificativo_rata.name());
		log.info(
			"analizzaEsitiFlussoV2 - inizio elaborazione dei dati dal tracciato degli esiti con identificativo_debito = {}, identificativo_rata = {}",
			idDebito, idRata);
		//recupero il riferimento alla stessa posizione debitoria nei dati da restituire 
		PkId pkPos = null;
		try {
		    pkPos = this.pkNodoDaIdNexi(idRata);//ricostruisco l'id della posizione dall'id rata del tracciato
		} catch (PayException e) {
		    errorAll = MessageFormat.format(
			    "errore nel parsing dell''id del record {0} in {1}: {2}. Non è possibile identificare la posizione debitoria associata a questo record del flusso e il record sarà ignorato ",
			    idRata, fileEsiti.getName(), e.toString());
		    log.error(errorAll, e);
		    esitoOperazione = false;
		    messaggioEsito.append("\n").append(errorAll);
		    continue;
		}
		if (pkPos != null && pkPos.getCodice() != null) {
		    PayPosizioniDebitorie payPos = null;
		    try {
			payPos = this.payPosizioniDebitorieService.findByIdExtended(pkPos.getCodice());
		    } catch (PayException e) {
			log.error("" + e.getMessage(), e);
		    }
		    if (payPos == null) {
			errorAll = MessageFormat.format(
				" posizione debitoria non trovata con id del record {0} in {1}: {2}. Non è possibile identificare la posizione debitoria associata a questo record del flusso e il record sarà ignorato ",
				idRata, fileEsiti.getName(), pkPos.getCodice());
			log.error(errorAll);
			esitoOperazione = false;
			messaggioEsito.append("\n").append(errorAll);
			continue;
		    }
		    PayStatoPagamenti statoCorrente = payPos.recuperaStatoCorrente();
		    BigInteger idPos = BigInteger.valueOf(pkPos.getCodice());
		    StatoPosizioneType statusPos = new StatoPosizioneType();
		    statusPos.setIdPosizione(idPos);
		    statusPos.setIdRegistrazioneContabile(BigInteger.valueOf(payPos.getRegistrazioneContabile().getId().getCodice()));
		    //se il record del tracciato che sto leggendo esiste fra le posizioni del command che sto verificando 
		    //l'esito restituito sarà comunque true indipendentemente dai valori presenti nel record
		    statusPos.setEsito(true);
		    rateProcessate.add(idRata);
		    //associo l'evento di lettura alle richieste pendenti che sto elaborando
		    // imposto i dati da restituire in base ai valori degli esiti letti dal tracciato (COdice avviso, iuv, stato posizione, dati pagamento)
		    String stato = (String) recEsito.getValue(CAMPI_ESITO.stato_rata.name());
		    StatiRata retStatus = StatiRata.fromValue(stato);
		    StatoPagamentoType newStatus = null;
		    switch (retStatus) {
		    case INFO_PAGAMENTO:
		    case INSOLUTO:
			//avvenuto caricamento della posizone se codice avviso e iuv sono valorizzati imposto stato a ATTIVATO_IN_PSP
			String iuv = (String) recEsito.getValue(CAMPI_ESITO.iuv.name());
			String codiceAvviso = (String) recEsito.getValue(CAMPI_ESITO.codice_avviso.name());
			if (StringUtils.isNotBlank(codiceAvviso)) {
			    statusPos.setCodiceAvviso(codiceAvviso);
			    newStatus = StatoPagamentoType.ATTIVATO_IN_PSP;
			}
			if (StringUtils.isNotBlank(iuv)) {
			    statusPos.setIUV(iuv);
			    newStatus = StatoPagamentoType.ATTIVATO_IN_PSP;
			}
			break;
		    case PAGATO:
		    case PAGATO_IN_DIFETTO:
		    case PAGATO_IN_ECCESSO:
		    case QUADRATO:
		    case RIPARTITO:
			newStatus = retStatus.equals(StatiRata.QUADRATO) ? StatoPagamentoType.RENDICONTATO_DA_IC
				: StatoPagamentoType.NOTIFICATO_DA_PSP;
			//popolare i dati di dettaglio del pagamento nella response
			DatiPagamentoType datiPag = new DatiPagamentoType();
			Date dt = (Date) recEsito.getValue(CAMPI_ESITO.data_ora_autorizzazione.name());
			if (dt != null) {
			    datiPag.setDataOraAutorizzazione(Utilities.getXMLGregorianCalendar(dt));
			}
			dt = (Date) recEsito.getValue(CAMPI_ESITO.data_ora_transazione.name());
			if (dt != null) {
			    datiPag.setDataOraInizioTransazione(Utilities.getXMLGregorianCalendar(dt));
			}
			dt = (Date) recEsito.getValue(CAMPI_ESITO.data_ora_operazione.name());
			if (dt != null) {
			    datiPag.setDataOraPagamento(Utilities.getXMLGregorianCalendar(dt));
			}
			String str = (String) recEsito.getValue(CAMPI_ESITO.causale_pagamento.name());
			if (StringUtils.isBlank(str)) {
			    str = (String) recEsito.getValue(CAMPI_ESITO.causale_banca.name());
			}
			datiPag.setDescrizioneCausale(str);
			Integer importo = (Integer) recEsito.getValue(CAMPI_ESITO.importo_totale_pagato.name());
			if (importo != null && importo != 0) {
			    BigDecimal bd = new BigDecimal(importo);
			    bd = bd.movePointLeft(2);
			    datiPag.setImportoPagato(bd);
			}
			BigDecimal impComm = BigDecimal.ZERO;
			importo = (Integer) recEsito.getValue(CAMPI_ESITO.importo_commissioni.name());
			if (importo != null && importo != 0) {
			    impComm = impComm.add(new BigDecimal(importo));
			}
			//sommo ad importo commissioni eventuali altre spese aggiuntive 
			importo = (Integer) recEsito.getValue(CAMPI_ESITO.importo_bollo.name());
			if (importo != null && importo != 0) {
			    impComm = impComm.add(new BigDecimal(importo));
			}
			importo = (Integer) recEsito.getValue(CAMPI_ESITO.importo_spese_invio_quietanza.name());
			if (importo != null && importo != 0) {
			    impComm = impComm.add(new BigDecimal(importo));
			}
			importo = (Integer) recEsito.getValue(CAMPI_ESITO.importo_spese_supplementari_rata.name());
			if (importo != null && importo != 0) {
			    impComm = impComm.add(new BigDecimal(importo));
			}
			importo = (Integer) recEsito.getValue(CAMPI_ESITO.importo_spese_varie.name());
			if (importo != null && importo != 0) {
			    impComm = impComm.add(new BigDecimal(importo));
			}
			impComm = impComm.movePointLeft(2);
			datiPag.setImportoCommissioni(impComm);
			str = (String) recEsito.getValue(CAMPI_ESITO.metodo_pagamento.name());
			datiPag.setModalitaPagamento(
				this.getMetodoPagamento(str, (String) recEsito.getValue(CAMPI_ESITO.dettaglio_metodo_pagamento.name())));
			str = (String) recEsito.getValue(CAMPI_ESITO.canale_pagamento.name());
			datiPag.setIdPSP(this.getCanalePagamento(str));
			datiPag.setNote((String) recEsito.getValue(CAMPI_ESITO.note_pagamento.name()));
			//dati soggetto pagatore
			str = (String) recEsito.getValue(CAMPI_ESITO.cf_pagante.name());
			//se il cf corrisponde al soggetto debitore non popolo i dati del soggetto pagatore per lasciare che il nodo pagamenti imposti come pagante il soggetto debitore della posizione
			if (StringUtils.isNotBlank(str)) {
			    PaySoggettiDebitori soggDeb = payPos.getSoggettoDebitore();
			    boolean nuovoSoggetto = true;
			    if (StringUtils.isNotBlank(soggDeb.getCfPi())) {
				nuovoSoggetto = !soggDeb.getCfPi().equalsIgnoreCase(str);
			    }
			    if (nuovoSoggetto) {
				SoggettoDebitoreType soggPag = new SoggettoDebitoreType();
				soggPag.setCfpi(str);
				soggPag.setNome((String) recEsito.getValue(CAMPI_ESITO.nome_pagante.name()));
				soggPag.setCognome((String) recEsito.getValue(CAMPI_ESITO.cognome_pagante.name()));
				//soggPag.set
				datiPag.setSoggettoPagatore(soggPag);
				//L'inserimento del nuovo soggetto che ha effettuato il pagamento viene effettuato dal nodo pagamenti quando riceve la risposta del connettore
			    }
			}
			statusPos.setDatiPagamento(datiPag);
			//Errore temporaneo = false per far considerare la richiesta conclusa nello scheduler del nodo. Una volta che è stata pagata non c'è più niente da controllare
			statusPos.setErroreTemporaneo(Boolean.FALSE);
			break;
		    default:
			break;
		    }
		    String messaggioErrore = gestisciEsitoPosizioneDebitoria(payPos, newStatus, statusPos, isNotificaPagamento,
			    statoCorrente.getStato());
		    if (StringUtils.isNotEmpty(messaggioErrore)) {
			log.warn(messaggioErrore);
			esitoOperazione = false;
			messaggioEsito.append("\n").append(messaggioErrore);
		    }
		}
	    }
	    //////////////////////////////////////////////
	    if (fh != null) {
		/*
		 * se tutte le richieste di verifica di questa chiamta al connettore sono state eleborate e se il numero
		 * di record presente nel tracciato è uguale al numero di record elaborati posso spostare il file degli
		 * esiti nella sua cartella di destinazione. Questo significa che nel caso di caricamenti multipli o da
		 * scheduler se il 'verifica stato' non è impostato come servizio schedulato che aggrega tutte le
		 * richieste di uno stesso lotto in una stessa chiamata, non si verificherebbero mai le condizioni per
		 * lo spostamento del file perchè avremmo molti debiti nel lotto e non avremmo mai tutti i debiti del
		 * lotto nella richiesta della chiamata al connettore.
		 */
		if (rateProcessate.size() == parsedRecords.getRecords().size()) {
		    String msg = MessageFormat.format(
			    "tutti e {0} i record del tracciato sono stati elaborati con successo, è possibile spostatre il file {1}",
			    parsedRecords.getRecords().size(), fileEsiti.getName());
		    log.info(msg);
		    fh.spostaTracciatiEScriviReport(fileEsiti.getName(), isNotificaPagamento, false);
		} else {
		    String msg = MessageFormat.format(
			    "{0} record del tracciato sono stati elaborati con successo su {1}, non è ancora possibile spostare il file {2}",
			    rateProcessate.size(), parsedRecords.getRecords().size(), fileEsiti.getName());
		    log.info(msg);
		    fh.aggiornaRateProcessate(rateProcessate, fileEsiti.getName(), isNotificaPagamento);
		}
	    }
	    /////////////////////////////////////////////
	}
	return new EsitoElaborazione(esitoOperazione, messaggioEsito.toString());
    }

    private String gestisciEsitoPosizioneDebitoria(PayPosizioniDebitorie payPos, StatoPagamentoType newStatus, StatoPosizioneType statusPos,
	    boolean isNotificaPagamento, String statoCorrente) {

	if (newStatus == null) {
	    return "Status nullo per posizione debitoria " + payPos.getId();
	}
	// se evento di notifica pagamento allora se lo stato ha tornato ATTIVATO IN PSP ESCO
	if (newStatus.equals(StatoPagamentoType.ATTIVATO_IN_PSP) && isNotificaPagamento) {
	    return "Ho ricevuto un evento di notifica per una posizione in stato " + newStatus + " per posizione debitoria " + payPos.getId();
	}
	// se evento non è notifica pagamento e lo stato è NOTIFICATO_DA_PSP o RENDICONTATO_DA_IC esco
	if ((newStatus.equals(StatoPagamentoType.NOTIFICATO_DA_PSP) || newStatus.equals(StatoPagamentoType.RENDICONTATO_DA_IC))
		&& !isNotificaPagamento) {
	    return "Ho ricevuto un evento di caricamento per una posizione in stato " + newStatus + " per posizione debitoria " + payPos.getId();
	}
	// se sto gestendo un esito caricamento e lo stato è già in attivato_in_psp allora non faccio niente
	if (newStatus.equals(StatoPagamentoType.ATTIVATO_IN_PSP) && statoCorrente.equalsIgnoreCase(StatoPagamentoType.ATTIVATO_IN_PSP.name())) {
	    return null;
	}
	// se sto gestendo una notifica di pagamento e lo stato è già in notificato o rendicontato allora non faccio niente
	if ((newStatus.equals(StatoPagamentoType.NOTIFICATO_DA_PSP) || newStatus.equals(StatoPagamentoType.RENDICONTATO_DA_IC))
		&& (statoCorrente.equalsIgnoreCase(StatoPagamentoType.NOTIFICATO_DA_PSP.name())
			|| statoCorrente.equalsIgnoreCase(StatoPagamentoType.RENDICONTATO_DA_IC.name()))) {
	    return null;
	}
	try {
	    statusPos.setStato(newStatus);
	    payStatoPagamentiService.registraStatoPosizioneDebitoria(statusPos, payPos);
	} catch (PayException e) {
	    String messaggio = "Errore in gestisciEsitoPosizioneDebitoria errore " + e.getMessage() + " per la posizione " + payPos.getId();
	    log.error(messaggio, e);
	}
	return null;
    }

    private String getCanalePagamento(String inputTracciato) {

	String retVal = "";
	if (StringUtils.isNotBlank(inputTracciato)) {
	    CanalePagamento mp = null;
	    try {
		mp = CanalePagamento.valueOf(inputTracciato.replace('/', '_'));
		retVal = mp.description();
	    } catch (IllegalArgumentException e) {
		//valore non esistente nell'enum
		log.error("getCanalePagamento - valore non gestito nel campo canale di pagamento: {}", inputTracciato);
		retVal = inputTracciato;
	    }
	}
	return retVal;
    }

    private String getMetodoPagamento(String metodoPagamento, String dettaglioMetodo) {

	StringBuilder retVal = new StringBuilder();
	if (StringUtils.isNotBlank(metodoPagamento)) {
	    MetodoPagamento mp = null;
	    try {
		mp = MetodoPagamento.valueOf(metodoPagamento.replace('/', '_'));
		retVal.append(mp.description());
	    } catch (IllegalArgumentException e) {
		//valore non esistente nell'enum
		log.error("getMetodoPagamento - valore non gestito nel campo metodo_pagamento: {}", metodoPagamento);
		retVal.append(metodoPagamento);
	    }
	}
	if (StringUtils.isNotBlank(dettaglioMetodo)) {
	    DettaglioMetodoPagamento dmp = null;
	    try {
		dmp = DettaglioMetodoPagamento.valueOf(dettaglioMetodo.replace('/', '_'));
		if (retVal.length() > 0) {
		    retVal.append(" ");
		}
		retVal.append(dmp.description());
	    } catch (IllegalArgumentException e) {
		//valore non esistente nell'enum
		log.error("getMetodoPagamento - valore non gestito nel campo dettaglio_metodo_pagamento: {}", dettaglioMetodo);
	    }
	}
	return retVal.toString();
    }

    private PkId pkNodoDaIdNexi(String idNexi) throws PayException {

	PkId id = null;
	if (StringUtils.isNotBlank(idNexi)) {
	    idNexi = StringUtils.stripStart(idNexi, " 0");
	    NumberFormat intFormat = NumberFormat.getIntegerInstance(Locale.ITALY);
	    intFormat.setGroupingUsed(false);
	    try {
		Integer intId = intFormat.parse(idNexi).intValue();
		id = new PkId(intId);
	    } catch (ParseException e) {
		throw new PayException("impossibile parsare l'id nexi " + idNexi + " formato numerico non valido", e);
	    }
	}
	return id;
    }

    private Set<String> getCodiciVersamento(String codiceProfileConnettore) {

	Set<String> ret = new HashSet<>();
	List<String> profili = new ArrayList<>(1);
	profili.add(codiceProfileConnettore);
	List<InfoCausaliPerConnettore> causaliPerConnettore = causaliService.findCausaliPerConnettore(profili);
	for (InfoCausaliPerConnettore infoC : causaliPerConnettore) {
	    for (InfoCausaleRestBean g : infoC.getCausali()) {
		if (StringUtils.isNotEmpty(g.getCodiceVersamento())) {
		    ret.add(g.getCodiceVersamento());
		}
	    }
	}
	return ret;
    }
}
