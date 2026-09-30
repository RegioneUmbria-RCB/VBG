package it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.features.attivita.denominazione.DenominazioneResolver;
import it.gruppoinit.pal.gp.core.features.attivita.denominazione.IDenominazioneResolverDAO;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoSnapshotInserito;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoSnapshotModificato;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.IAttivitaIstanzeService;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.ParametriCalcoloSnapshot;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.SequenzaSnapshot;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.Snapshot;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.SnapshotDataComparator;
import it.gruppoinit.pal.gp.core.features.attivita.verticalizzazione.IVerticalizzazioneIAttivitaService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.osservatorio.fvg.IVerticalizzazioneOsservatorioRegionaleFVGService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.impl.IstanzeServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class CalcoloSnapshotServiceImpl implements ICalcoloSnapshotService {

    private static final Logger log = LoggerFactory.getLogger(CalcoloSnapshotServiceImpl.class);
    @Autowired
    private IVerticalizzazioneOsservatorioRegionaleFVGService verticalizzazioneOsservatorioRegionaleFVGService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private IVerticalizzazioneIAttivitaService verticalizzazioneIAttivitaService;
    @Autowired
    private IDenominazioneResolverDAO denominazioneResolverDAO;
    @Autowired
    private IEventPublisher eventPublisher;
    @Autowired
    private ISnapshotService snapshotService;
    @Autowired
    private IAttivitaService attivitaService;
    @Autowired
    private IAttivitaIstanzeService attivitaIstanzeService;

    @Override
    public void ricalcola(Integer idAttivita) {

	Date dataSnapshotMinore = this.snapshotService.findMinDataSnapshot(idAttivita);
	Date dataValiditaMinore = this.snapshotService.findMinDataValidita(idAttivita);
	if (dataSnapshotMinore == null && dataValiditaMinore == null) {
	    //non ci sono snapshot per cui devo solamente ricalcolare alcuni parametri delle attività
	    this.aggiornaDatiAttivita(idAttivita);
	    return;
	}
	if (dataSnapshotMinore == null) {
	    dataSnapshotMinore = dataValiditaMinore;
	}
	if (dataValiditaMinore == null) {
	    dataValiditaMinore = dataSnapshotMinore;
	}
	Date dataRicalcolo = dataSnapshotMinore.compareTo(dataValiditaMinore) <= 0 ? dataSnapshotMinore : dataValiditaMinore;
	this.calcola(new ParametriCalcoloSnapshot(idAttivita, dataRicalcolo, dataRicalcolo));
    }

    @Override
    public void calcola(ParametriCalcoloSnapshot parametri) {

	if (parametri == null) {
	    throw new IllegalArgumentException("Parametri non validi");
	}
	if (log.isDebugEnabled()) {
	    log.debug("calcola {}", ReflectionToStringBuilder.toString(parametri, ToStringStyle.SHORT_PREFIX_STYLE));
	}
	//0. verifica gestione temporaryShop
	this.chiudiAttivitaTemporanea(parametri.getIdAttivita());
	//1. Recupero riferimenti temporali snap prima - snap corrente - snap successivo prima di qualsiasi calcolo
	SequenzaSnapshot seq = findSequenzaSnapshot(parametri.getIdAttivita(), parametri.getDataPrecedente());
	//1. Aggiorno l'eventuale snapshot ( esistente o nuovo ) alla data di riferimento
	Date dataEvento = this.calcolaSingoloSnapshot(parametri.getIdAttivita(), parametri.getDataPrecedente(), parametri.getDataSuccessiva());
	//2. Se dataEvento è null, vuol dire che lo snapshot va cancellato
	log.debug("calcola {} ==> data evento {}", parametri.getIdAttivita(), dataEvento);
	if (seq.getAttuale() != null && dataEvento == null) {
	    dataEvento = seq.getAttuale().getData();
	    log.debug("calcola {} ==> nuova data evento {}", parametri.getIdAttivita(), dataEvento);
	    this.snapshotService.delete(seq.getAttuale().getId());
	}
	//3. Verifico se devono essere aggiornati/inseriti eventuali snapshot successivi
	Date dataAltroSnapshot = this.verificaSnapshotSuccessivi(parametri.getIdAttivita(), dataEvento);
	log.debug("calcola {} ==> dataAltroSnapshot {}", parametri.getIdAttivita(), dataAltroSnapshot);
	this.aggiornaDatiAttivita(parametri.getIdAttivita());
	//3. Verifico se sollevare l'evento per il ricalcolo dei dati dinamici passando la data più vecchia degli snapshot ricalcolati
	if (dataEvento == null || (dataAltroSnapshot != null && dataAltroSnapshot.compareTo(dataEvento) < 0)) {
	    dataEvento = dataAltroSnapshot;
	    log.debug("calcola {} ==> dataEvento = dataAltrosnapshot {}", parametri.getIdAttivita(), dataAltroSnapshot);
	}
	this.gestioneEvento(seq, parametri.getIdAttivita(), dataEvento);
    }

    @Override
    public void chiudiAttivitaTemporanea(Integer idAttivita) {

	log.debug("Chiudi Attività {}", idAttivita);
	IAttivita iatt = attivitaService.findById(new PkId(idAttivita));
	if (iatt.getDataFine() != null && BooleanUtils.isTrue(iatt.getAttiva())
		&& Utilities.compareDates(iatt.getDataFine(), Calendar.getInstance().getTime()) <= 0) {
	    //	 --> verificare se è prevista una data di cessazione dell'attività
	    //		--> esiste ed è <= di oggi?
	    //		--> crea uno snapshot
	    //			- verificare ed eventualmente inserire lo snapshot di cessazione con le seguenti considerazioni
	    //     		- la data dello snapshot è la data di cessazione dell'attività
	    //			- gli altri dati vanno copiati dall'attività
	    // BOCCI 2022-07-14 - Se lo snapshot con quella data esiste non lo devo inserire nuovo ma sostituire quello esistente
	    Snapshot fromAttivitaCessata = Snapshot.fromAttivitaCessata(iatt);
	    Date dataRiferimentoChiusura = fromAttivitaCessata.getData();
	    Map<Date, Integer> findSnapshots = this.snapshotService.findSnapshots(idAttivita, dataRiferimentoChiusura);
	    Integer idSnapshotEsistente = findSnapshots.get(dataRiferimentoChiusura);
	    fromAttivitaCessata.setId(idSnapshotEsistente);
	    log.debug("Chiudi Attività - aggiorno snapshot{}", idAttivita);
	    this.snapshotService.salvaSnapshot(fromAttivitaCessata);
	    this.aggiornaDatiAttivita(idAttivita);
	    SequenzaSnapshot seq = findSequenzaSnapshot(idAttivita, dataRiferimentoChiusura);
	    this.gestioneEvento(seq, idAttivita, dataRiferimentoChiusura);
	}
    }

    private Date verificaSnapshotSuccessivi(Integer idAttivita, Date dataDiPartenza) {

	SortedSet<Date> dateRicalcolo = new TreeSet<Date>();
	//1. Aggiungo alla data di partenza da cui calcolare gli snapshot eventuali snap mancanti
	List<Date> nuoviSnapshot = this.attivitaIstanzeService.findSnapshotMancanti(idAttivita);
	if (nuoviSnapshot != null && !nuoviSnapshot.isEmpty()) {
	    dateRicalcolo.addAll(nuoviSnapshot);
	}
	//2. Verifico la data di partenza, se null devo ricalcolare a partire dalla data più bassa
	if (dataDiPartenza == null && dateRicalcolo.isEmpty()) {
	    return dataDiPartenza;
	}
	if (dataDiPartenza == null && !dateRicalcolo.isEmpty()) {
	    dataDiPartenza = dateRicalcolo.first();
	}
	//2. Aggiungo tutti gli snapshot presenti dopo la data di partenza
	Map<Date, Integer> snapshots = this.snapshotService.findSnapshotsSuccessivi(idAttivita, dataDiPartenza);
	for (Map.Entry<Date, Integer> snapshot : snapshots.entrySet()) {
	    dateRicalcolo.add(snapshot.getKey());
	}
	//3. Se non ci sono altre date ritorno la data di partenza
	if (dateRicalcolo.isEmpty()) {
	    return dataDiPartenza;
	}
	//4. Una volta ottenuta la sequenza temporale inizio i calcoli, in questo caso le date conincidono in quanto 
	// si tratta di snapshot nuovi o che comunque non hanno subito variazioni temporali
	for (Date dataRiferimento : dateRicalcolo) {
	    this.calcolaSingoloSnapshot(idAttivita, dataRiferimento, dataRiferimento);
	}
	//5. Torno la data più vecchia rispetto a quelle rielaborate
	return dateRicalcolo.first();
    }

    /**
     * 
     * @param idAttivita
     * @param dataPrecedente
     *            la data che lo snapshot potrebbe avere in questo momento
     * @param dataSuccessiva
     *            la data che lo snapshot potrebbe aver dopo l'elaborazione. La dataSuccessiva potrebbe essere
     *            antecedente alla dataPrecedente
     * @return
     */
    private Date calcolaSingoloSnapshot(Integer idAttivita, Date dataPrecedente, Date dataSuccessiva) {

	//1. recupero riferimenti snap prima - corrente - successivo ( data )
	SequenzaSnapshot seq = findSequenzaSnapshot(idAttivita, dataPrecedente);
	//2. Recupero l'attività di riferimento
	IAttivita attivita = this.snapshotService.findAttivitaById(idAttivita);
	//3. Ricalcolo l'istanza di riferimento per lo snapshot
	Istanze istanzaDelloSnapshot = istanzeService.findIstanzaUltimaAttivitaAllaData(idAttivita, dataSuccessiva);
	//4. Se non trovo l'istanza rappresentativa dello snapshot è perché è stata scollegata l'unica istanza di quello snapshot
	if (istanzaDelloSnapshot == null) {
	    return null;
	}
	//  se mi accorgo che l'istanza è passata da uno snapshot ad altro allora posso tornare dataPrecedente
	boolean unoDeiTreSnapshotHaLaDataPrecedente = verificaDateSnapshot(seq, dataPrecedente);
	boolean unoDeiTreSnapshotHaLaDataSuccessiva = verificaDateSnapshot(seq, dataSuccessiva);
	if (unoDeiTreSnapshotHaLaDataPrecedente && unoDeiTreSnapshotHaLaDataSuccessiva && dataPrecedente.compareTo(dataSuccessiva) != 0) {
	    int compareDate = dataPrecedente.compareTo(dataSuccessiva);
	    if (compareDate < 0) {
		return dataPrecedente;
	    }
	    return dataSuccessiva;
	}
	//5. Recupero lo snapshot attuale, se nullo lo genero nuovo
	Snapshot attuale = seq.getAttuale();
	if (attuale == null) {
	    attuale = new Snapshot();
	    attuale.setIattivita(attivita);
	    attuale.setDenominazione(new DenominazioneResolver(verticalizzazioneIAttivitaService, denominazioneResolverDAO,
		    attivita.getDenominazione(), istanzaDelloSnapshot).risolvi());
	}
	//6. Imposto l'istanza principale con quella ricalcolata
	attuale.setIstanza(istanzaDelloSnapshot);
	//7. Riporto la tipologia dell'attività leggendola dall'attività in quanto non prevede ricalcoli
	attuale.setTipologiaAttivita(attivita.getTipologiaAttivita());
	//8. Imposto attiva/operante nello snapshot
	this.impostaAttivaOperante(attuale);
	//9. Ricalcolo, se previso, il codice osservatorio o lo copio dall'attività
	this.gestisciCodiceOsservatorio(attivita, seq, attuale);
	//10. Salvataggio dello snapshot
	this.snapshotService.salvaSnapshot(attuale);
	return attuale.getData();
    }

    private boolean verificaDateSnapshot(SequenzaSnapshot seq, Date dataRiferimento) {

	if (dataRiferimento == null) {
	    return false;
	}
	if (seq.getSnapshotPrecedente() != null && seq.getSnapshotPrecedente().getData().compareTo(dataRiferimento) == 0) {
	    return true;
	}
	if (seq.getAttuale() != null && seq.getAttuale().getData().compareTo(dataRiferimento) == 0) {
	    return true;
	}
	if (seq.getSnapshotSuccessivo() != null && seq.getSnapshotSuccessivo().getData().compareTo(dataRiferimento) == 0) {
	    return true;
	}
	return false;
    }

    private void aggiornaDatiAttivita(Integer idAttivita) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile aggiornare i dati dell'attività perchè manca il riferimento all'attività");
	}
	//1. recupero l'attività
	IAttivita attivita = this.snapshotService.findAttivitaById(idAttivita);
	//2. recupero lo snapshot più recente
	IAttivitaSnapshot snapshotRecente = this.snapshotService.findSnapshotPiuRecente(idAttivita);
	if (snapshotRecente != null) {
	    log.debug("aggiornaDatiAttivita {} - snapshot recente !=null - {}", idAttivita, snapshotRecente.getId().getCodice());
	    //3. aggiorno i dati dell'attività
	    attivita.setAttiva(snapshotRecente.getAttiva());
	    attivita.setCodiceOsservatorio(snapshotRecente.getCodiceOsservatorio());
	    attivita.setDenominazione(snapshotRecente.getDenominazione());
	    attivita.setIstanza(snapshotRecente.getIstanza());
	    attivita.setOperante(snapshotRecente.getOperante());
	    attivita.setTipologiaAttivita(snapshotRecente.getTipologiaAttivita());
	    attivita.setDataInizio(this.attivitaService.calcoloDataInizioAttivita(attivita, null));
	    attivita.setDataFine(this.attivitaService.calcoloDataFineAttivita(attivita, null));
	} else {
	    log.debug("aggiornaDatiAttivita {} - snapshot recente ==null ", idAttivita);
	    //posso solo ricalcolare l'istanza rappresentativa, la denominazione
	    attivita.setIstanza(this.attivitaIstanzeService.findIstanzaSenzaDataValiditaPiuRecente(attivita.getId().getCodice()));
	    attivita.setAttiva(this.snapshotService.isAttivaAllaData(idAttivita, attivita.getIstanza().getData()));
	    if (Boolean.FALSE.equals(attivita.getAttiva())) {
		attivita.setOperante(false);
	    } else {
		attivita.setOperante(this.snapshotService.findIfIsAttivitaOperanteFromMovimenti(idAttivita, attivita.getIstanza().getData()));
	    }
	    attivita.setDenominazione(new DenominazioneResolver(verticalizzazioneIAttivitaService, denominazioneResolverDAO,
		    attivita.getDenominazione(), attivita.getIstanza()).risolvi());
	    attivita.setDataInizio(this.attivitaService.calcoloDataInizioAttivita(attivita, null));
	    attivita.setDataFine(this.attivitaService.calcoloDataFineAttivita(attivita, null));
	}
	log.debug("aggiornaDatiAttivita {} - snapshot update attivita ", idAttivita);
	this.snapshotService.updateAttivita(attivita);
    }

    private void gestioneEvento(SequenzaSnapshot seq, Integer idAttivita, Date dataDiPartenza) {

	boolean snapshotCambiaCardinalita = false; // se caso a) o b) o c)
	Date dataRiferimento = null;
	//1. si tratta di un nuovo snapshot
	if (seq.getAttuale() == null) {
	    log.debug("gestioneEvento {} ==> dataAltroSnapshot {}", idAttivita, dataDiPartenza);
	    this.eventPublisher.publish(new EventoSnapshotInserito(idAttivita, dataDiPartenza));
	    return;
	}
	//2. si tratta della modifica di uno snapshot esistente
	dataRiferimento = dataDiPartenza;
	Date dataPrecedente = seq.getSnapshotPrecedente() != null ? seq.getSnapshotPrecedente().getData() : dataDiPartenza;
	Date dataSuccessiva = seq.getSnapshotSuccessivo() != null ? seq.getSnapshotSuccessivo().getData() : dataDiPartenza;
	boolean variazionePrecedente = Utilities.compareDates(dataRiferimento, dataPrecedente) < 0;
	boolean variazioneSuccessiva = Utilities.compareDates(dataRiferimento, dataSuccessiva) > 0;
	log.debug("gestioneEvento {} ==> variazionePrecedente {}, variazioneSuccessiva {}, data precedente {}, data successiva {}",
		new Object[] { idAttivita, dataDiPartenza, variazioneSuccessiva, dataPrecedente, dataSuccessiva });
	if (variazionePrecedente || variazioneSuccessiva) {
	    // 	caso a) se data nuova non risulta in mezzo a precedente e successivo
	    // // // se lo snapshot attuale è finito prima del precedente la data dell'evento è la data sua
	    // // // se lo snapshot attuale è se finito dopo al successivo la data dell'evento è la data di seq.getSuccessivo
	    snapshotCambiaCardinalita = true;
	    dataRiferimento = dataPrecedente;
	    if (variazioneSuccessiva) {
		dataRiferimento = dataSuccessiva;
	    }
	    log.debug("gestioneEvento {} ==> dataRiferimento {}", idAttivita, dataRiferimento);
	}
	this.eventPublisher.publish(new EventoSnapshotModificato(idAttivita, dataRiferimento, snapshotCambiaCardinalita));
    }

    private void impostaAttivaOperante(Snapshot snapshot) {

	snapshot.setAttiva(this.snapshotService.isAttivaAllaData(snapshot.getIattivita().getId().getCodice(), snapshot.getData()));
	if (snapshot.isAttiva()) {
	    snapshot.setOperante(
		    this.snapshotService.findIfIsAttivitaOperanteFromMovimenti(snapshot.getIattivita().getId().getCodice(), snapshot.getData()));
	}
    }

    private void gestisciCodiceOsservatorio(IAttivita attivita, SequenzaSnapshot seq, Snapshot attuale) {

	//1. Se lo snapshot ha già il codice osservatorio esco
	if (attuale.getCodiceOsservatorio() != null) {
	    return;
	}
	//2. Verifico se è lo snapshot rappresentativo perchè eventualmente devo calcolarlo o prenderlo dall'attività
	if (seq.getSnapshotSuccessivo() == null) {
	    attuale.setCodiceOsservatorio(insertCheckCodiceOsservatorio(attuale.getIstanza()));
	    if (attuale.getCodiceOsservatorio() == null) {
		attuale.setCodiceOsservatorio(attivita.getCodiceOsservatorio());
	    }
	}
	//3. Se ancora vuoto provo a prenderlo dal precedente
	if (attuale.getCodiceOsservatorio() == null) {
	    attuale.setCodiceOsservatorio(seq.getCodiceOsservatorioSnapshotPrecedente());
	}
    }

    private SequenzaSnapshot findSequenzaSnapshot(Integer idAttivita, Date dataricalcolo) {

	// trova la sequenza degli snapshot
	SequenzaSnapshot seq = new SequenzaSnapshot();
	// Lista di IAttivitaSnapshot (Copia dell'attività a una certa data) ordinata per data snapshot desc
	List<IAttivitaSnapshot> l = this.snapshotService.findByAttivita(idAttivita);
	if (l.isEmpty()) {
	    return seq;
	}
	List<Snapshot> precedenti = new ArrayList<Snapshot>();
	List<Snapshot> successivi = new ArrayList<Snapshot>();
	for (IAttivitaSnapshot s : l) {
	    Date cfr = s.getData();
	    int compare = Utilities.compareDates(cfr, dataricalcolo);
	    Snapshot sgnappino = new Snapshot(s);
	    if (compare == 0) {
		// attuale se presente
		seq.setAttuale(sgnappino);
	    } else if (compare < 0) {
		precedenti.add(sgnappino);
	    } else {
		successivi.add(sgnappino);
	    }
	}
	// determina il precedente e il successivo
	boolean ordinamentoCrescente = true;
	if (!precedenti.isEmpty()) {
	    Collections.sort(precedenti, new SnapshotDataComparator(!ordinamentoCrescente));
	    // ordino per data desc cosi prendo il primo tra i precedenti e dovrebbe essere immediatamente precedente 
	    seq.setSnapshotPrecedente(precedenti.get(0));
	}
	if (!successivi.isEmpty()) {
	    Collections.sort(successivi, new SnapshotDataComparator(ordinamentoCrescente));
	    // ordino per data ASC così prendo il primo tra i successivi e dovrebbe essere immediatamente successivo 
	    seq.setSnapshotSuccessivo(successivi.get(0));
	}
	return seq;
    }

    private Integer insertCheckCodiceOsservatorio(Istanze istanza) {

	if (verticalizzazioneOsservatorioRegionaleFVGService.isAttiva() && BooleanUtils.isTrue(istanza.getAlberoproc().getFlagProgAttOsserv())) {
	    return this.snapshotService.generaCodiceOsservatorio();
	}
	return null;
    }
}
