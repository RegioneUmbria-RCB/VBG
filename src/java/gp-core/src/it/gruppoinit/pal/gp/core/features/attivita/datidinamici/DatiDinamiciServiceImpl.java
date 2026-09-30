package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic.ISnapshotDAO;

@Service
public class DatiDinamiciServiceImpl implements IDatiDinamiciService {

    @Autowired
    private IDatiDinamiciDAO datiDinamiciDAO;
    @Autowired
    private ISnapshotDAO snapshotDAO;

    public DatiDinamiciServiceImpl() {

	super();
    }

    @Override
    public void aggiungiSchedeDinamiche(Integer idAttivita, List<Integer> idSchede) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile aggiungere schede dinamiche all'attività senza passare l'attività di riferimento");
	}
	if (idSchede == null) {
	    throw new IllegalArgumentException("Impossibile aggiungere schede dinamiche all'attività senza passare le schede da aggiungere");
	}
	//1. Prendo la lista delle schede dinamiche presenti nell'attività
	List<Integer> idSchedePresenti = this.datiDinamiciDAO.findIdSchedeDinamicheAttivita(idAttivita);
	//2. Prendo la lista degli eventuali snapshots presenti
	Map<Date, Integer> snapshots = this.snapshotDAO.findSnapshots(idAttivita);
	List<Integer> idSnapshots = new ArrayList<Integer>();
	for (Map.Entry<Date, Integer> snapshot : snapshots.entrySet()) {
	    idSnapshots.add(snapshot.getValue());
	}
	//3. Verifico la lista passata per capire se sono già presenti
	for (Integer idScheda : idSchede) {
	    boolean presente = false;
	    for (Integer idSchedaPresente : idSchedePresenti) {
		if (idScheda.equals(idSchedaPresente)) {
		    presente = true;
		    break;
		}
	    }
	    if (!presente) {
		this.datiDinamiciDAO.aggiungiSchedeDinamicheAdAttivita(idAttivita, idScheda, idSnapshots);
		//3. Recupero gli id dei campi dinamici previsti dal modello
		List<Integer> idCampi = this.datiDinamiciDAO.findIdCampiScheda(idScheda);
		//4. Recupero la mappa dei campi della scheda passata valorizzata in base ad ogni snapshot presente di questa attività
		Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiSnapshot = this.datiDinamiciDAO.findCampiPresentiSnapshot(idAttivita,
			idScheda);
		//5. Recupero la mappa dei campi della scheda passata valorizzata in base ad ogni istanza con data validità presente di questa attività
		TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiIstanze = this.datiDinamiciDAO.findCampiPresentiIstanze(idAttivita,
			idScheda);
		//6. Ciclo la mappa degli snapshot
		Date dataUltimoSnapshot = null;
		Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiDaAggiungerePerUltimoSnaphost = new HashMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>();
		for (Map.Entry<Date, Integer> snapshot : snapshots.entrySet()) {
		    Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiDaAggiungere = this.recuperaCampiInData(snapshot.getKey(), idCampi,
			    campiIstanze, campiSnapshot);
		    if (dataUltimoSnapshot == null) {
			dataUltimoSnapshot = snapshot.getKey();
		    }
		    if (snapshot.getKey().compareTo(dataUltimoSnapshot) >= 0) {
			dataUltimoSnapshot = snapshot.getKey();
			campiDaAggiungerePerUltimoSnaphost = campiDaAggiungere;
		    }
		    this.datiDinamiciDAO.insertAutoIns(idAttivita, snapshot.getValue(), campiDaAggiungere);
		}
		//7.L'ultimo snapshot aggiorna i dati dinamici dell'attività
		this.datiDinamiciDAO.insertAutoIns(idAttivita, campiDaAggiungerePerUltimoSnaphost);
	    }
	}
    }

    @Override
    public void aggiungiSchedeDinamicheDaAlbero(IAttivita attivita) {

	if (attivita == null) {
	    throw new IllegalArgumentException("Impossibile recuperare le schede dinamiche senza passare l'attività di riferimento");
	}
	if (attivita.getIstanza() == null || attivita.getIstanza().getAlberoproc() == null || attivita.getIstanza().getAlberoproc().getId() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile recuperare le schede dinamiche perchè l'attività passata non contiene un'istanza da cui recuperare il codice dell'albero");
	}
	//1. Prendo la lista delle schede dinamiche previste nell'albero dei procedimenti
	List<Integer> idSchedeDinamiche = this.datiDinamiciDAO
		.findIdSchedeDinamicheDaAlberoProc(attivita.getIstanza().getAlberoproc().getId().getCodice());
	//2. Prendo la lista degli eventuali snapshots presenti
	Map<Date, Integer> snapshots = this.snapshotDAO.findSnapshots(attivita.getId().getCodice());
	List<Integer> idSnapshots = new ArrayList<Integer>();
	for (Map.Entry<Date, Integer> snapshot : snapshots.entrySet()) {
	    idSnapshots.add(snapshot.getValue());
	}
	for (Integer idScheda : idSchedeDinamiche) {
	    this.datiDinamiciDAO.aggiungiSchedeDinamicheAdAttivita(attivita.getId().getCodice(), idScheda, idSnapshots);
	}
    }

    @Override
    public void gestisciSchedaDinamicaAggiuntaAdAttivita(Integer idAttivita, Integer idScheda) {

	// Quando viene aggiunta una scheda dinamica devono essere ricalcolati i dati dinamici di tutti gli snapshot e deve essere invocata la copia dei dati dinamici dell'ultimo snap sull'attività
	//Verifica esistenza campi scheda nelle istanze dell'attività
	//1. Recupero la mappa degli snapshot ( Data - Id )
	Map<Date, Integer> snapshots = this.snapshotDAO.findSnapshots(idAttivita);
	List<Integer> idSnapshots = new ArrayList<Integer>();
	for (Map.Entry<Date, Integer> snapshot : snapshots.entrySet()) {
	    idSnapshots.add(snapshot.getValue());
	}
	//2. Aggiungo la scheda dinamica agli snapshots
	this.datiDinamiciDAO.aggiungiSchedeDinamicheASnapshots(idAttivita, idScheda, idSnapshots);
	//3. Recupero gli id dei campi dinamici previsti dal modello
	List<Integer> idCampi = this.datiDinamiciDAO.findIdCampiScheda(idScheda);
	//4. Recupero la mappa dei campi della scheda passata valorizzata in base ad ogni snapshot presente di questa attività
	Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiSnapshot = this.datiDinamiciDAO.findCampiPresentiSnapshot(idAttivita, idScheda);
	//5. Recupero la mappa dei campi della scheda passata valorizzata in base ad ogni istanza con data validità presente di questa attività
	TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiIstanze = this.datiDinamiciDAO.findCampiPresentiIstanze(idAttivita, idScheda);
	//6. Ciclo la mappa degli snapshot
	Date dataUltimoSnapshot = null;
	Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiDaAggiungerePerUltimoSnaphost = new HashMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>();
	for (Map.Entry<Date, Integer> snapshot : snapshots.entrySet()) {
	    Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiDaAggiungere = this.recuperaCampiInData(snapshot.getKey(), idCampi,
		    campiIstanze, campiSnapshot);
	    if (dataUltimoSnapshot == null) {
		dataUltimoSnapshot = snapshot.getKey();
	    }
	    if (snapshot.getKey().compareTo(dataUltimoSnapshot) >= 0) {
		dataUltimoSnapshot = snapshot.getKey();
		campiDaAggiungerePerUltimoSnaphost = campiDaAggiungere;
	    }
	    this.datiDinamiciDAO.insertAutoIns(idAttivita, snapshot.getValue(), campiDaAggiungere);
	}
	//7.L'ultimo snapshot aggiorna i dati dinamici dell'attività
	this.datiDinamiciDAO.insertAutoIns(idAttivita, campiDaAggiungerePerUltimoSnaphost);
    }

    @Override
    public void gestisciSchedaDinamicaAttivitaSalvata(Integer idAttivita, Integer idScheda) {

	//Il salvataggio di una scheda dinamica dell'attività, genera solo l'aggiornamento dell'ultimo snapshot dell'attività
	//1. Recupero la lista dei campi salvati nell'attività
	List<CampoDinamicoAttivita> elenco = this.datiDinamiciDAO.findCampiPresentiAttivita(idAttivita, idScheda);
	//2. Recupero il riferimento allo snapshot rappresentativo dell'attività
	Integer idSnapshot = this.datiDinamiciDAO.findIdSnapshotRappresentativo(idAttivita);
	//3. Sovrascrivo i dati dello snapshot rappresentativo se presente
	if (idSnapshot != null) {
	    this.datiDinamiciDAO.sovrascriviSnapshot(idAttivita, idSnapshot, idScheda, elenco);
	}
    }

    @Override
    public void gestisciSchedaDinamicaAttivitaEliminata(Integer idAttivita, Integer idScheda, List<Integer> idCampiDinamiciDaEliminare) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile cancellare i dati dinamici senza passare l'attività di riferimento");
	}
	if (idScheda == null) {
	    throw new IllegalArgumentException("Impossibile cancellare i dati dinamici senza passare la scheda di riferimento");
	}
	if (idCampiDinamiciDaEliminare == null) {
	    throw new IllegalArgumentException("Impossibile cancellare i dati dinamici senza sapere quali cancellare");
	}
	this.datiDinamiciDAO.deleteSchedeDaSnapshots(idAttivita, idScheda, idCampiDinamiciDaEliminare);
    }

    @Override
    public void gestisciSchedaDinamicaIstanzaSalvata(Integer idAttivita, Integer idIstanza, Integer idScheda, Date dataRicalcolo) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile verificare i campi dinamici da aggiornare senza passare l'attività di riferimento");
	}
	if (idIstanza == null) {
	    throw new IllegalArgumentException("Impossibile verificare i campi dinamici da aggiornare senza passare l'istanza di riferimento");
	}
	if (idScheda == null) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare i campi dinamici da aggiornare senza passare la scheda che è stata salvata sull'istanza");
	}
	if (dataRicalcolo == null) {
	    throw new IllegalArgumentException("Impossibile verificare  i campi dinamici da aggiornare senza passare data da cui ricalcolare");
	}
	//1. Recupero la mappa degli snapshot ( Data - Id ) a partire dalla data di ricalcolo
	Map<Date, Integer> snapshots = this.snapshotDAO.findSnapshots(idAttivita, dataRicalcolo);
	List<Integer> idSnapshots = new ArrayList<Integer>();
	for (Map.Entry<Date, Integer> snapshot : snapshots.entrySet()) {
	    idSnapshots.add(snapshot.getValue());
	}
	//2. Recupero la lista dei campi dinamici in comune tra istanza e attività per quella specifica scheda
	List<Integer> idCampi = this.datiDinamiciDAO.findIdCampiAttivitaGestibiliDaIstanze(idAttivita, idScheda);
	//3. I campi in comune li cancello dagli snapshot e dall'attibità perchè saranno ricalcolati
	if (!idCampi.isEmpty()) {
	    this.datiDinamiciDAO.deleteCampiDinamici(idAttivita, idSnapshots, idCampi);
	}
	//4. Recupero la mappa dei campi della scheda passata valorizzata in base ad ogni istanza con data validità presente di questa attività
	TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiIstanze = this.datiDinamiciDAO.findCampiPresentiIstanze(idAttivita, idScheda);
	//5. Ciclo la mappa degli snapshot
	Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiDaAggiungere = new HashMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>();
	for (Map.Entry<Date, Integer> snapshot : snapshots.entrySet()) {
	    campiDaAggiungere = this.recuperaCampiInData(snapshot.getKey(), idCampi, campiIstanze,
		    new HashMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>());
	    this.datiDinamiciDAO.insertAutoIns(idAttivita, snapshot.getValue(), campiDaAggiungere);
	}
	//6.L'ultimo snapshot aggiorna i dati dinamici dell'attività
	this.datiDinamiciDAO.insertAutoIns(idAttivita, campiDaAggiungere);
    }

    @Override
    public void gestisciSchedaDinamicaIstanzaEliminata(Integer idAttivita, Integer idScheda, Date dataRicalcolo) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile ricalcolare i campi dinamici senza passare l'attività di riferimento");
	}
	if (idScheda == null) {
	    throw new IllegalArgumentException("Impossibile ricalcolare i campi dinamici senza passare la scheda di riferimento");
	}
	if (dataRicalcolo == null) {
	    throw new IllegalArgumentException("Impossibile ricalcolare i campi dinamici senza passare data da cui ricalcolare");
	}
	//1. Prendere i riferimenti dei campi dinamici legati alla scheda
	List<Integer> idCampi = this.datiDinamiciDAO.findIdCampiAttivitaGestibiliDaIstanze(idAttivita, idScheda);
	if (idCampi.isEmpty()) {
	    return;
	}
	//2. Recupero gli snapshot presenti a partire dalla data di ricalcolo compresa
	Map<Date, Integer> snapshots = this.snapshotDAO.findSnapshots(idAttivita, dataRicalcolo);
	//3. I campi in comune li cancello dagli snapshot e dall'attività perchè saranno ricalcolati
	List<Integer> idSnapshots = new ArrayList<Integer>();
	for (Map.Entry<Date, Integer> snapshot : snapshots.entrySet()) {
	    idSnapshots.add(snapshot.getValue());
	}
	this.datiDinamiciDAO.deleteCampiDinamici(idAttivita, idSnapshots, idCampi);
	//4. Recupero la mappa dei campi della scheda passata valorizzata in base ad ogni istanza con data validità presente di questa attività
	TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiIstanze = this.datiDinamiciDAO.findCampiPresentiIstanze(idAttivita, idCampi);
	//5. Ciclo la mappa degli snapshot
	Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiDaAggiungere = new HashMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>();
	for (Map.Entry<Date, Integer> snapshot : snapshots.entrySet()) {
	    campiDaAggiungere = this.recuperaCampiInData(snapshot.getKey(), idCampi, campiIstanze,
		    new HashMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>());
	    this.datiDinamiciDAO.insertAutoIns(idAttivita, snapshot.getValue(), campiDaAggiungere);
	}
	//6.L'ultimo snapshot aggiorna i dati dinamici dell'attività
	this.datiDinamiciDAO.insertAutoIns(idAttivita, campiDaAggiungere);
    }

    @Override
    public void gestisciRicalcoloDatiDinamici(Integer idAttivita, Date dataRicalcolo) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile ricalcolare i campi dinamici senza passare l'attività di riferimento");
	}
	if (dataRicalcolo == null) {
	    throw new IllegalArgumentException("Impossibile ricalcolare i campi dinamici senza passare la data da cui ricalcolare");
	}
	//1. Recupero la mappa degli snapshot ( Data - Id ) a partire dalla data di ricalcolo
	Map<Date, Integer> snapshots = this.snapshotDAO.findSnapshots(idAttivita, dataRicalcolo);
	//2. Se la lista è vuota vuol dire che è stato cancellato lo snapshot rappresentativo, per cui ricalcolo a partire dal nuovo snapshot rappresentativo
	if (snapshots.isEmpty()) {
	    IAttivitaSnapshot snapshot = this.snapshotDAO.findSnapshotPiuRecente(idAttivita);
	    //2.1 Se non ci sono più snapshot esco
	    if (snapshot == null) {
		return;
	    }
	    snapshots.put(snapshot.getData(), snapshot.getId().getCodice());
	}
	//3. Estraggo la lista di id dalla mappa
	List<Integer> idSnapshots = new ArrayList<Integer>();
	for (Map.Entry<Date, Integer> snapshot : snapshots.entrySet()) {
	    idSnapshots.add(snapshot.getValue());
	}
	//4. Prendere i riferimenti dei campi dinamici
	List<Integer> idCampi = this.datiDinamiciDAO.findIdCampiAttivitaGestibiliDaIstanze(idAttivita);
	if (idCampi.isEmpty()) {
	    return;
	}
	//5. Cancello i campi dinamici interessati dal ricalcolo
	this.datiDinamiciDAO.deleteCampiDinamici(idAttivita, idSnapshots, idCampi);
	//6. Recupero la mappa dei campi valorizzati in base ad ogni istanza con data validità presente di questa attività
	TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiIstanze = this.datiDinamiciDAO.findCampiPresentiIstanze(idAttivita, idCampi);
	//7. Ciclo la mappa degli snapshot
	Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiDaAggiungere = new HashMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>();
	for (Map.Entry<Date, Integer> snapshot : snapshots.entrySet()) {
	    campiDaAggiungere = this.recuperaCampiInData(snapshot.getKey(), idCampi, campiIstanze,
		    new HashMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>());
	    this.datiDinamiciDAO.insertAutoIns(idAttivita, snapshot.getValue(), campiDaAggiungere);
	}
	//8.L'ultimo snapshot aggiorna i dati dinamici dell'attività
	this.datiDinamiciDAO.insertAutoIns(idAttivita, campiDaAggiungere);
    }

    @Override
    public void gestisciNuovoSnapshot(Integer idAttivita, Date dataRicalcolo) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile ricalcolare i campi dinamici senza passare l'attività di riferimento");
	}
	if (dataRicalcolo == null) {
	    throw new IllegalArgumentException("Impossibile ricalcolare i campi dinamici senza passare la data da cui ricalcolare");
	}
	//1. Recupero lo snapshot aggiunto 
	Integer idSnapshot = this.snapshotDAO.findIdSnapshot(idAttivita, dataRicalcolo);
	//2. Recupero gli id delle schede dinamiche presenti nell'attività
	List<Integer> idSchede = this.datiDinamiciDAO.findIdSchedeDinamicheAttivita(idAttivita);
	if (!idSchede.isEmpty()) {
	    //2. Gli aggiungo le schede dinamiche dell'attività
	    this.datiDinamiciDAO.aggiungiSchedeDinamicheASnapshots(idAttivita, idSchede, idSnapshot);
	    //3. Passo a ricalcolare i dati dinamici
	    this.gestisciRicalcoloDatiDinamici(idAttivita, dataRicalcolo);
	}
    }

    protected Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> recuperaCampiInData(Date dataDiRiferimento, List<Integer> idCampi,
	    TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> elencoCompleto,
	    Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> elencoGiaPresenti) {

	Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> mappa = new HashMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>();
	for (Integer idCampo : idCampi) {
	    Date dataSnapshotConCampoPresente = null;
	    for (Map.Entry<ChiaveCampoDinamicoPerData, ValoreCampoPresente> item : elencoCompleto.entrySet()) {
		if (dataDiRiferimento.compareTo(item.getKey().getDataSnapshot()) >= 0 && item.getKey().getIdCampo().equals(idCampo)) {
		    dataSnapshotConCampoPresente = item.getKey().getDataSnapshot();
		    break;
		}
	    }
	    if (dataSnapshotConCampoPresente == null) {
		continue;
	    }
	    //in base alla data di riferimento estrapolata, prendo la lista delle chiavi
	    Set<ChiaveCampoDinamicoPerData> chiaviDaGestire = new HashSet<ChiaveCampoDinamicoPerData>();
	    for (Map.Entry<ChiaveCampoDinamicoPerData, ValoreCampoPresente> item : elencoCompleto.entrySet()) {
		if (dataSnapshotConCampoPresente.compareTo(item.getKey().getDataSnapshot()) == 0 && item.getKey().getIdCampo().equals(idCampo)) {
		    chiaviDaGestire.add(item.getKey());
		}
	    }
	    for (ChiaveCampoDinamicoPerData chiaveDaGestire : chiaviDaGestire) {
		//Verificare la presenza nell'elenco dei campi già presenti in base alla chiave
		if (this.campoGiaPresente(chiaveDaGestire, elencoGiaPresenti)) {
		    continue;
		}
		//recupero il valore e lo aggiungo alla mappa
		for (Map.Entry<ChiaveCampoDinamicoPerData, ValoreCampoPresente> item : elencoCompleto.entrySet()) {
		    if (chiaveDaGestire.hashCode() == item.getKey().hashCode()) {
			ChiaveCampoDinamicoPerData retChiave = new ChiaveCampoDinamicoPerData(dataDiRiferimento, idCampo, item.getKey().getIndice(),
				item.getKey().getIndiceMolteplicita());
			mappa.put(retChiave, item.getValue());
			break;
		    }
		}
	    }
	}
	return mappa;
    }

    private boolean campoGiaPresente(ChiaveCampoDinamicoPerData chiaveDaRicercare,
	    Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> elencoGiaPresenti) {

	for (ChiaveCampoDinamicoPerData chiave : elencoGiaPresenti.keySet()) {
	    if (chiave.hashCode() == chiaveDaRicercare.hashCode()) {
		return true;
	    }
	}
	return false;
    }
}
