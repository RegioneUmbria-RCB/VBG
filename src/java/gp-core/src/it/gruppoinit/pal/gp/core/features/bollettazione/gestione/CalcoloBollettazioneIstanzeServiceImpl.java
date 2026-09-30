package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.audit.Loggable;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PeriodiEnum;
import it.gruppoinit.pal.gp.core.domain.BollCfgCausalioneri;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.domain.BollGestFiltri;
import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollettazioneDAO;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.BollettazioneIstanzeFiltriRicerca;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.soggettopendenza.SoggettiPendenzaEnum;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.soggettopendenza.SoggettoPendenzaService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;

@Loggable(featureName = "bollettazione")
@Service
public class CalcoloBollettazioneIstanzeServiceImpl extends CalcoloBollettazioneServiceBase implements CalcoloBollettazioneIstanzeService {

    private BollettazioneDAO bollettazioneDAO;
    private VerticalizzazioniService verticalizzazioniService;
    private SoggettoPendenzaService pendenzaService;
    private ComuniassociatiService comuniassociatiService;

    @Autowired
    public CalcoloBollettazioneIstanzeServiceImpl(BollettazioneDAO bollettazioneDAO, VerticalizzazioniService verticalizzazioniService,
	    SoggettoPendenzaService pendenzaService, ComuniassociatiService comuniassociatiService) {

	super(verticalizzazioniService);
	this.bollettazioneDAO = bollettazioneDAO;
	this.verticalizzazioniService = verticalizzazioniService;
	this.pendenzaService = pendenzaService;
	this.comuniassociatiService = comuniassociatiService;
    }

    @Override
    public EsitoCalcoloBollettazione calcola(RichiestaCalcoloBollettazioneIstanza richiesta, Boolean conguaglio) {

	//	boolean isAzienda = isAzienda(richiesta);
	//	List<RigaDettaglioCalcolo> result = bollettazioneDAO.findByFiltriBollettazioneIstanza(richiesta.getFiltriCodiceComune(),
	//		richiesta.getFiltriScCodice(), richiesta.getFiltriCodiceEndo(), richiesta.getFiltriCausaleOnere(), richiesta.getIntervalloDate(),
	//		conguaglio, isAzienda);
	//	return new EsitoCalcoloBollettazione(richiesta.getIntervalloDate(), result);
	// logica di calcolo sostituita con ottimizzazioni insert as select 
	// Product Backlog Item 17183: Miglioramento funzionalità bollettazione (java) - Task 16423: Creazione bollettazione DA ISTANZE: inserimento elementi in nuova bollettazione
	throw new NotImplementedException("Errore nella chiamata al metodo calcola");
    }

    @Override
    public Integer creaBollettazione(CreazioneBollTestata creazioneBollTestata, Integer codiceResponsabile) {

	BollCfgTipo bollCfgTipo = this.bollettazioneDAO.getByIdForBollettazione(BollCfgTipo.class, creazioneBollTestata.getBollCfgTipoId());
	PeriodiEnum tipologiaPeriodo = PeriodiEnum.valueOf(bollCfgTipo.getPeriodo().toUpperCase());
	RichiestaCalcoloBollettazioneIstanza richiesta = new RichiestaCalcoloBollettazioneIstanza(tipologiaPeriodo, codiceResponsabile,
		creazioneBollTestata.getDescrizione(), creazioneBollTestata.getIntervalloDate());
	List<String> filtriCodiceComune = null;
	if (creazioneBollTestata.getComuni() != null && creazioneBollTestata.getComuni().size() > 0) {
	    filtriCodiceComune = new ArrayList<String>(creazioneBollTestata.getComuni());
	    richiesta.getFiltriCodiceComune().addAll(filtriCodiceComune);
	}
	List<String> filtriScCodice = null;
	if (creazioneBollTestata.getInterventi() != null && creazioneBollTestata.getInterventi().size() > 0) {
	    filtriScCodice = new ArrayList<String>(creazioneBollTestata.getInterventi());
	    richiesta.getFiltriScCodice().addAll(filtriScCodice);
	}
	List<Integer> filtriCodiceEndo = null;
	if (creazioneBollTestata.getEndoprocedimenti() != null && creazioneBollTestata.getEndoprocedimenti().size() > 0) {
	    filtriCodiceEndo = new ArrayList<Integer>(creazioneBollTestata.getEndoprocedimenti());
	    richiesta.getFiltriCodiceEndo().addAll(filtriCodiceEndo);
	}
	List<Integer> filtriCausaleOnere = getFiltriCausaleOnere(bollCfgTipo);
	if (filtriCausaleOnere != null && filtriCausaleOnere.size() > 0) {
	    richiesta.getFiltriCausaleOnere().addAll(filtriCausaleOnere);
	}
	//invoco il calcolo
	this.validaConfigurazioneBollettazione(toSet(filtriCodiceComune));
	BollettazioneIstanzeFiltriRicerca filtriRicerca = BollettazioneIstanzeFiltriRicerca.fromRichiestaCalcoloBollettazioneIstanza(richiesta);
	// trovo quanti record di istanzeoneri e sistemo le sequenze di
	boolean isAzienda = isAzienda(richiesta);
	boolean conguaglio = false; // non implementato conguaglio per Istanze
	int totaleOneri = this.contaOneri(filtriRicerca, conguaglio, isAzienda);
	if (totaleOneri > 0) {
	    //invoco il salvataggio della testata
	    BollGestTestata testata = new BollGestTestata(creazioneBollTestata, bollCfgTipo);
	    this.bollettazioneDAO.save(testata);
	    // this.bollettazioneDAO.
	    Integer bollTestataId = testata.getId().getCodice();
	    //invoco il salvataggio dei filtri
	    List<BollGestFiltri> filtri = this.getFiltri(testata, filtriScCodice, filtriCodiceEndo, filtriCodiceComune, filtriCausaleOnere);
	    this.bollettazioneDAO.save(filtri);
	    // inserisco le righe (attenzione importoivato e azienda)
	    inserisciRighe(filtriRicerca, testata.getDataScadenza(), totaleOneri, conguaglio, isAzienda, bollTestataId);
	    //return dell'id di bollettazione
	    return bollTestataId;
	}
	return null;
    }

    private boolean isAzienda(RichiestaCalcoloBollettazioneIstanza richiesta) {

	boolean isAzienda = false;
	String codiceComune = null;
	if (richiesta.getFiltriCodiceComune() != null && !richiesta.getFiltriCodiceComune().isEmpty()) {
	    codiceComune = richiesta.getFiltriCodiceComune().get(0);
	}
	VerticalizzazioneNodoPagamentiServiceImpl v = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune);
	if (v.isAttiva()) {
	    SoggettiPendenzaEnum soggettoPendenza = v.soggettoPendenza();
	    if (pendenzaService.isAzienda(soggettoPendenza)) {
		isAzienda = true;
	    }
	}
	return isAzienda;
    }

    private void inserisciRighe(BollettazioneIstanzeFiltriRicerca filtriRicerca, Date dataScadenza, int totaleOneri, boolean conguaglio,
	    boolean azienda, Integer bollTestataId) {

	String guidOperazione = UUID.randomUUID().toString();
	int numeroInizialeBollGestDettaglio = aggiornaSequenzaBollGestDettaglio(totaleOneri);
	int numeroInizialeBollGestIstanzeOneri = aggiornaSequenzaBollGestIstanzeOneri(totaleOneri);
	bollettazioneDAO.inserisciRigheIstanzeOneri(filtriRicerca, numeroInizialeBollGestDettaglio, numeroInizialeBollGestIstanzeOneri, conguaglio,
		azienda, guidOperazione, bollTestataId, dataScadenza);
    }

    private int aggiornaSequenzaBollGestIstanzeOneri(int totaleRecordDaInserire) {

	return bollettazioneDAO.aggiornaSequenzaBollGestIstanzeOneri(totaleRecordDaInserire);
    }

    private int aggiornaSequenzaBollGestDettaglio(int totaleRecordDaInserire) {

	return bollettazioneDAO.aggiornaSequenzaBollGestDettaglio(totaleRecordDaInserire);
    }

    private int contaOneri(BollettazioneIstanzeFiltriRicerca filtriRicerca, boolean conguaglio, boolean azienda) {

	return bollettazioneDAO.contaRigheIstanzeOneri(filtriRicerca, conguaglio, azienda);
    }

    private Set<String> toSet(List<String> filtriCodiceComune) {

	if (filtriCodiceComune != null && !filtriCodiceComune.isEmpty()) {
	    return new HashSet<String>(filtriCodiceComune);
	}
	List<Comuniassociati> cs = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	HashSet<String> s = new HashSet<String>();
	for (Comuniassociati comuniassociati : cs) {
	    s.add(comuniassociati.getId().getCodicecomune());
	}
	return s;
    }

    private List<BollGestFiltri> getFiltri(BollGestTestata testata, List<String> filtriScCodice, List<Integer> filtriCodiceEndo,
	    List<String> filtriCodiceComune, List<Integer> filtriCausaleOnere) {

	List<BollGestFiltri> filtri = new ArrayList<BollGestFiltri>();
	if (filtriScCodice != null) {
	    for (String fkScCodice : filtriScCodice) {
		BollGestFiltri filtro = new BollGestFiltri();
		filtro.setBollGestTestata(testata);
		filtro.setFkScCodice(fkScCodice);
		filtri.add(filtro);
	    }
	}
	if (filtriCodiceEndo != null) {
	    for (Integer codiceInventario : filtriCodiceEndo) {
		BollGestFiltri filtro = new BollGestFiltri();
		filtro.setBollGestTestata(testata);
		Inventarioprocedimenti proc = new Inventarioprocedimenti();
		proc.setId(new PkId(codiceInventario));
		filtro.setInventarioprocedimenti(proc);
		filtri.add(filtro);
	    }
	}
	if (filtriCodiceComune != null) {
	    for (String codiceComune : filtriCodiceComune) {
		BollGestFiltri filtro = new BollGestFiltri();
		filtro.setBollGestTestata(testata);
		Comuni comune = new Comuni();
		comune.setCodicecomune(codiceComune);
		filtro.setComuni(comune);
		filtri.add(filtro);
	    }
	}
	if (filtriCausaleOnere != null) {
	    for (Integer codiceCausale : filtriCausaleOnere) {
		BollGestFiltri filtro = new BollGestFiltri();
		filtro.setBollGestTestata(testata);
		Tipicausalioneri causale = new Tipicausalioneri();
		causale.setId(new PkId(codiceCausale));
		filtro.setTipicausalioneri(causale);
		filtri.add(filtro);
	    }
	}
	return filtri;
    }

    private List<Integer> getFiltriCausaleOnere(BollCfgTipo bollCfgTipo) {

	Set<BollCfgCausalioneri> cfgOneri = bollCfgTipo.getBollCfgCausalioneris();
	if (cfgOneri != null) {
	    List<Integer> filtriCausaleOnere = new ArrayList<Integer>();
	    for (BollCfgCausalioneri cfgOnere : cfgOneri) {
		filtriCausaleOnere.add(cfgOnere.getTipicausalioneri().getId().getCodice());
	    }
	    return filtriCausaleOnere;
	}
	return null;
    }

    @Override
    public EsitoCalcoloBollettazione calcolaConguaglio(Integer codiceTipologiaBollettazione, Date dataPartenzaBollettazioneAttuale,
	    Integer codiceResponsabile) {

	// TODO gestire implementazione conguaglio bollettazione oneri istanza
	throw new RuntimeException("La funzionalità di conguaglio non è ancora stata implementata per la bollettazione degli oneri delle istanze");
    }
}
