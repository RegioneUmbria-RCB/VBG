package it.gruppoinit.pal.gp.core.features.attivita.istanze;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.attivita.datidinamici.IDatiDinamiciService;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoIstanzaCollegata;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoIstanzaScollegata;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoOrdineIstanzaModificato;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.model.IstanzeAttivitaHelper;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic.ISnapshotDAO;
import it.gruppoinit.pal.gp.core.features.attivita.verticalizzazione.IVerticalizzazioneIAttivitaService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.suapinrete.IVerticalizzazioneSuapInRete;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitService;

@Service
public class AttivitaIstanzeServiceImpl implements IAttivitaIstanzeService {

    @Autowired
    private IAttivitaIstanzeDAO attivitaIstanzeDAO;
    @Autowired
    private IVerticalizzazioneIAttivitaService verticalizzazioneService;
    @Autowired
    private IVerticalizzazioneSuapInRete verticalizzazioneSuapInRete;
    @Autowired
    private IstanzeDAO istanzeDAO;
    @Autowired
    private IEventPublisher eventPublisher;
    @Autowired
    private ISnapshotDAO snapshotDAO;
    @Autowired
    private Istanzedyn2modellitService istanzedyn2modellitService;
    @Autowired
    private IDatiDinamiciService datiDinamiciService;

    @Override
    public void collegaIstanze(IAttivita attivita) {

	this.collegaIstanze(attivita, attivita.getIstanza());
    }

    @Override
    public void collegaIstanze(IAttivita attivita, Istanze istanza) {

	istanza.setAttivita(attivita);
	this.istanzeDAO.update(istanza);
	this.collegaSchedeDinamicheIstanza(istanza);
	Date dataElaborazione = istanza.getDatavalidita();
	if (!this.verticalizzazioneService.isNonConsiderareIstanzeCollegate()) {
	    List<Integer> istanzeCollegate = this.attivitaIstanzeDAO.findCatenaIstanzeDaCollegare(istanza.getId().getCodice());
	    for (Integer codiceIstanza : istanzeCollegate) {
		Istanze istanzaCollegata = this.istanzeDAO.findById(new PkId(codiceIstanza));
		istanzaCollegata.setAttivita(attivita);
		this.istanzeDAO.update(istanzaCollegata);
		this.collegaSchedeDinamicheIstanza(istanzaCollegata);
	    }
	}
	EventoIstanzaCollegata evento = new EventoIstanzaCollegata(attivita.getId().getCodice(), dataElaborazione, istanza);
	this.eventPublisher.publish(evento);
    }

    @Override
    public void scollegaIstanze(Istanze istanza) throws ScollegamentoUnicaIstanzaException {

	if (istanza == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo scollegaIstanze senza passare l'istanza da scollegare");
	}
	if (istanza.getAttivita() == null || istanza.getAttivita().getId() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo scollegaIstanze perchè l'istanza passata non è collegata a nessuna attività");
	}
	Integer idAttivita = istanza.getAttivita().getId().getCodice();
	//1. Verifico se è l'unica istanza dell'attività perchè in quel caso non posso far scollegare ma devo rilanciare un'eccezione
	if (this.attivitaIstanzeDAO.isPresenteUnaSolaIstanza(idAttivita)) {
	    throw new ScollegamentoUnicaIstanzaException(istanza.getAttivita().getDenominazione(),
		    "service_error.impossibile_scollegare_unica_istanza");
	}
	//2. Scollego l'istanza
	istanza.setAttivita(null);
	this.istanzeDAO.update(istanza);
	//3. Sollevo l'evento dell'istanza scollegata
	Date dataElaborazione = istanza.getDatavalidita();
	if (dataElaborazione == null) {
	    dataElaborazione = this.snapshotDAO.findMinDataSnapshot(idAttivita);
	}
	EventoIstanzaScollegata evento = new EventoIstanzaScollegata(idAttivita, dataElaborazione);
	this.eventPublisher.publish(evento);
    }

    @Override
    public List<IstanzeAttivitaHelper> cercaIstanzeDaDataValidita(Integer idAttivita, Date dataValiditaRiferimento) {

	return attivitaIstanzeDAO.cercaIstanzeDaDataValidita(idAttivita, dataValiditaRiferimento);
    }

    @Override
    public boolean isAttivaAllaData(Integer idAttivita, Date dataValidita) {

	return this.attivitaIstanzeDAO.isAttivaAllaData(idAttivita, dataValidita);
    }

    @Override
    public List<Date> findSnapshotMancanti(Integer idAttivita) {

	return this.attivitaIstanzeDAO.findSnapshotMancanti(idAttivita);
    }

    private void collegaSchedeDinamicheIstanza(Istanze istanza) {

	if (istanza == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare il metodo collegaSchedeDinamicheIstanza senza passare il riferimento all'istanza");
	}
	if (this.verticalizzazioneSuapInRete.isAttiva() && this.verticalizzazioneSuapInRete.isCollegaSchedeIstanzeInAttivita()) {
	    List<Integer> modelli = this.istanzedyn2modellitService.findIdSchedeByIstanza(istanza.getId().getCodice());
	    this.datiDinamiciService.aggiungiSchedeDinamiche(istanza.getAttivita().getId().getCodice(), modelli);
	}
    }

    @Override
    public void scambiaOrdine(Integer idAttivita, Integer codiceIstanzaPrec, Integer codiceIstanzaSuc) {

	//1. Inverto l'ordine tra le due istanze
	Date dataRicalcolo = this.attivitaIstanzeDAO.scambiaOrdine(codiceIstanzaPrec, codiceIstanzaSuc);
	//2. Faccio partire il ricalcolo degli snapshot
	this.eventPublisher.publish(new EventoOrdineIstanzaModificato(idAttivita, dataRicalcolo));
    }

    @Override
    public void updateOrdine(Integer idAttivita, Integer[] arCodici, Integer[] arOrdini) {

	if (arCodici == null) {
	    throw new IllegalArgumentException("Impossibile richiamare updateOrdine senza passare le istanze da riordinare");
	}
	if (arOrdini == null) {
	    throw new IllegalArgumentException("Impossibile richiamare updateOrdine senza passare gli ordini che le istanze dovranno avere");
	}
	if (arCodici.length != arOrdini.length) {
	    throw new IllegalArgumentException("Impossibile richiamare updateOrdine perchè non è stato specificato un ordine per ogni istanza");
	}
	for (int i = 0; i < arCodici.length; i++) {
	    this.attivitaIstanzeDAO.updateOrdine(arCodici[i], arOrdini[i]);
	}
	Date dataRicalcolo = this.snapshotDAO.findMinDataSnapshot(idAttivita);
	this.eventPublisher.publish(new EventoOrdineIstanzaModificato(idAttivita, dataRicalcolo));
    }

    @Override
    public Istanze findIstanzaSenzaDataValiditaPiuRecente(Integer idAttivita) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare il metodo findIstanzaSenzaDataValiditaPiuRecente senza passare il riferimento all'attività");
	}
	Integer idIstanza = this.attivitaIstanzeDAO.findIstanzaSenzaDataValiditaPiuRecente(idAttivita);
	return this.istanzeDAO.findById(new PkId(idIstanza));
    }
}
