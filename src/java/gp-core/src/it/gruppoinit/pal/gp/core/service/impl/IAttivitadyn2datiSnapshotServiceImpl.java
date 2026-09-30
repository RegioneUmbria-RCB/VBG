package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2datiSnapshotDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshotId;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IAttivitaSnapshotService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2datiSnapshotService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2modellitService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class IAttivitadyn2datiSnapshotServiceImpl extends BaseServiceImpl<IAttivitadyn2datiSnapshot, IAttivitadyn2datiSnapshotId>
	implements IAttivitadyn2datiSnapshotService {

    private static final Logger log = LoggerFactory.getLogger(IAttivitadyn2datiSnapshotServiceImpl.class);
    private IAttivitadyn2datiSnapshotDAO iattivitadyn2datisnapshotDAO;
    private IAttivitaService iAttivitaService;
    private Istanzedyn2datiService istanzedyn2datiService;
    private IAttivitadyn2modellitService iAttivitadyn2modellitService;
    private IAttivitaSnapshotService iAttivitaSnapshotService;

    @Autowired
    public void setIAttivitadyn2datiSnapshotDAO(IAttivitadyn2datiSnapshotDAO iattivitadyn2datisnapshotDAO) {

	this.iattivitadyn2datisnapshotDAO = iattivitadyn2datisnapshotDAO;
    }

    @Autowired
    public void setiAttivitaService(IAttivitaService iAttivitaService) {

	this.iAttivitaService = iAttivitaService;
    }

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Autowired
    public void setiAttivitadyn2modellitService(IAttivitadyn2modellitService iAttivitadyn2modellitService) {

	this.iAttivitadyn2modellitService = iAttivitadyn2modellitService;
    }

    @Autowired
    public void setiAttivitaSnapshotService(IAttivitaSnapshotService iAttivitaSnapshotService) {

	this.iAttivitaSnapshotService = iAttivitaSnapshotService;
    }

    @Override
    protected Class<IAttivitadyn2datiSnapshot> getEntityClass() {

	return IAttivitadyn2datiSnapshot.class;
    }

    @Override
    public List<IAttivitadyn2datiSnapshot> findAll(Integer firstResult, Integer maxResult) {

	return iattivitadyn2datisnapshotDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IAttivitadyn2datiSnapshot entity) {

	if (validateEntity(entity)) {
	    iattivitadyn2datisnapshotDAO.insert(entity);
	}
    }

    @Override
    public IAttivitadyn2datiSnapshot findById(IAttivitadyn2datiSnapshotId id) {

	return iattivitadyn2datisnapshotDAO.findById(id);
    }

    @Override
    public void update(IAttivitadyn2datiSnapshot entity) {

	if (validateEntity(entity)) {
	    iattivitadyn2datisnapshotDAO.update(entity);
	}
    }

    @Override
    public void delete(IAttivitadyn2datiSnapshot entity) {

	if (isDeleteAllowed(entity)) {
	    iattivitadyn2datisnapshotDAO.delete(entity);
	}
    }

    @Override
    public void updateCampiSchedeDinamicheSnapShot(Integer codiceIAttivita, Integer codiceIattivitaSnapShot, List<Integer> istanzes) {

	// Recupero l'oggetto iAttivita dal codice
	log.debug("Recupero attivita con codice {}", codiceIAttivita);
	// IAttivita iAttivita = iAttivitaService.findById(new PkId(codiceIAttivita));
	// Recupero per l'attività passata tutte le schede collegata ad essa.
	List<IAttivitadyn2modellit> iAttivitadyn2modellits = iAttivitadyn2modellitService.findByAttivita(codiceIAttivita, null, null);
	log.debug("Ciclo le schede dell'attività di codice {} (num. schede: {})", new Object[] { codiceIAttivita, iAttivitadyn2modellits.size() });
	for (IAttivitadyn2modellit iAttivitadyn2modellit : iAttivitadyn2modellits) {
	    Set<Dyn2Modellid> dyn2Modellids = iAttivitadyn2modellit.getDyn2Modellit().getDyn2Modellids();
	    for (Dyn2Modellid dyn2Modellid : dyn2Modellids) {
		if (EntityUtils.getNestedProperty(dyn2Modellid.getDyn2Campi(), "id.codice") != null) {
		    // Recupero tutti i valori dei associati al campo dinamico (Istanzedyn2dati) per l'istanza e per il campo dinamico passati. 
		    //(ultimaIstanza sarà la nuova istanza che rappresenta 
		    //l'attività)
		    log.debug("Recupero dati dinamici dell'istanza  corrispondenti al campo dinamico (dyn2dati) di codice: {}",
			    dyn2Modellid.getDyn2Campi().getId().getCodice());
		    List<Istanzedyn2dati> istanzedyn2datis = istanzedyn2datiService
			    .findByIstanzasAndDyn2Campi(dyn2Modellid.getDyn2Campi().getId().getCodice(), codiceIAttivita, istanzes, 0, 1);
		    // Per ogni valore dei campi dinamici dell'istanza (Istanzedyn2dati) faccio un confronto con i valori di quelli dell'attivita snapshot(IAttivitadyn2datiSnapShot)
		    // Cerco su IAttivitadyn2datiSnapShot se esiste un record con questi filtri:
		    //1. idcomune;
		    //2. fkIaId :riferimento all'attività
		    //3. fkD2cId:riferimento al campo
		    //4. indice
		    //5. indiceMolteplicita
		    //6. fkIaIds
		    if (istanzedyn2datis != null && !istanzedyn2datis.isEmpty()) {
			log.debug("Inserisco/Aggiorno campo dinamico (codice dyn2dati :{}) della schede dell'attivita({})....",
				new Object[] { istanzedyn2datis.get(0).getDyn2Campi().getId().getCodice(), codiceIAttivita });
			insertOrUpdateiAttivitadyn2datiSnapShot(codiceIattivitaSnapShot, codiceIAttivita, istanzedyn2datis.get(0));
			log.debug("Inserito/Aggiornato campo della scheda....");
		    }
		}
	    }
	}
    }

    private boolean insertOrUpdateiAttivitadyn2datiSnapShot(Integer codiceIattivitaSnapShot, Integer codiceIAttivita,
	    Istanzedyn2dati istanzedyn2dati) {

	try {
	    IAttivitadyn2datiSnapshotId id = new IAttivitadyn2datiSnapshotId();
	    id.setFkIaId(codiceIAttivita);
	    id.setFkIasId(codiceIattivitaSnapShot);
	    id.setFkD2cId(istanzedyn2dati.getId().getFkD2cId());
	    id.setIndice(istanzedyn2dati.getId().getIndice());
	    id.setIndiceMolteplicita(istanzedyn2dati.getId().getIndiceMolteplicita());
	    IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapShotTemp = this.findById(id);
	    //CASO A: già esiste quindi vado a modificare i valori con quelli di istanzedyn2dati,
	    // l' aggiormaneto verrà fatto solo sui campi "valore" e "valore decodficato"
	    if (iAttivitadyn2datiSnapShotTemp != null) {
		log.debug("Il campo dinamico della schede dell'istanza già esiste sulla scheda dell'attivita snpashot: aggiorno i valori");
		if (StringUtils.isNotBlank(istanzedyn2dati.getValore())) {
		    iAttivitadyn2datiSnapShotTemp.setValore(istanzedyn2dati.getValore());
		}
		if (StringUtils.isNotBlank(istanzedyn2dati.getValoredecodificato())) {
		    iAttivitadyn2datiSnapShotTemp.setValoredecodificato(istanzedyn2dati.getValoredecodificato());
		}
		this.update(iAttivitadyn2datiSnapShotTemp);
		return true;
	    } else {
		//CASO B: non  esiste quindi vado a inserire un nuovo record prendendo i valori da  istanzedyn2dati,
		log.debug("Il campo dinamico della schede dell'istanza non esiste sulla scheda dell'attivita snapshot: inserisco il campo");
		IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapShot = new IAttivitadyn2datiSnapshot();
		IAttivita attivita = iAttivitaService.findById(new PkId(codiceIAttivita));
		iAttivitadyn2datiSnapShot.setAttivita(attivita);
		IAttivitaSnapshot iAttivitaSnapshot = iAttivitaSnapshotService.findById(new PkId(codiceIattivitaSnapShot));
		iAttivitadyn2datiSnapShot.setAttivitaSnapshot(iAttivitaSnapshot);
		// Controllo se è presente il campo valore , se non è presente non sarà popolato neanche il campo valoredecodificato
		if (StringUtils.isNotBlank(istanzedyn2dati.getValore())) {
		    iAttivitadyn2datiSnapShot.setValore(istanzedyn2dati.getValore());
		    // Controllo se il campo valoredecodificato è popolato, se no, lo setto a NULL
		    if (StringUtils.isNotBlank(istanzedyn2dati.getValoredecodificato())) {
			iAttivitadyn2datiSnapShot.setValoredecodificato(istanzedyn2dati.getValoredecodificato());
		    } else {
			log.debug("Il campo dinamico {} della schede dell'istanza {} ha VALORE : {}, ma non VALOREDECODIFICATO",
				new Object[] { istanzedyn2dati.getDyn2Campi().getId().getCodice(), istanzedyn2dati.getIstanza().getNumeroistanza(),
					istanzedyn2dati.getValore() });
			iAttivitadyn2datiSnapShot.setValoredecodificato(null);
		    }
		}
		iAttivitadyn2datiSnapShot.setId(id);
		this.insert(iAttivitadyn2datiSnapShot);
		return true;
	    }
	} catch (Exception e) {
	    log.error("Non è stato possibile aggiornare il campo della scheda causa:", e);
	    return false;
	}
    }

    @Override
    public List<IAttivitadyn2datiSnapshot> findByIAttivitaSnapshot(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "attivitaSnapshot", Integer.class));
	ft.addRestriction(fr);
	return iattivitadyn2datisnapshotDAO.findByFilterTable(ft);
    }

    @Override
    public List<IAttivitadyn2datiSnapshot> findByAttivitaAndAttivitaSnapshotAndCampo(Integer codiceAttivita, Integer codiceAttivitaSnapshot,
	    Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkIaId", codiceAttivita, Integer.class));
	if (codiceAttivitaSnapshot != null) {
	    fr.addFilterField(FilterUtils.equals("id.fkIasId", codiceAttivitaSnapshot, Integer.class));
	}
	fr.addFilterField(FilterUtils.equals("id.fkD2cId", codice, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.indiceMolteplicita"));
	return iattivitadyn2datisnapshotDAO.findByFilterTable(ft);
    }

    //    protected boolean isDeleteAllowed(IAttivitadyn2datiSnapshot entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
    @Override
    public int getMaxIndice(Integer codiceAttivita, Integer idmodello) {

	return iattivitadyn2datisnapshotDAO.getMaxIndice(codiceAttivita, idmodello);
    }

    @Override
    public int getMaxIndiceMolteplicita(Integer codiceAttivita, Integer idmodello) {

	return iattivitadyn2datisnapshotDAO.getMaxIndiceMolteplicita(codiceAttivita, idmodello);
    }

    @Override
    public void deleteByAttivitaAndCampo(Integer codiceAttivita, Integer idCampo) {

	iattivitadyn2datisnapshotDAO.deleteByAttivitaAndCampo(codiceAttivita, idCampo);
    }

    @Override
    public void deleteBySnapshot(Integer idSnapshot, boolean deleteOnlyAutoIns) {

	iattivitadyn2datisnapshotDAO.deleteBySnapshot(idSnapshot, deleteOnlyAutoIns);
    }
}
