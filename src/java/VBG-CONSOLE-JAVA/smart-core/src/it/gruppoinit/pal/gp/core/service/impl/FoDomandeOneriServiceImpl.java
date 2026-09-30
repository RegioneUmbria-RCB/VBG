/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoDomandeOneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggetti;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggettiId;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOneri;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.ErroreValidazione;
import it.gruppoinit.pal.gp.core.domain.cart.MessaggioErrore;
import it.gruppoinit.pal.gp.core.domain.helper.PkIdComparator;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.FoDomandeOggettiService;
import it.gruppoinit.pal.gp.core.service.FoDomandeOneriService;
import it.gruppoinit.pal.gp.core.service.FoDomandeService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentioneriService;
import it.gruppoinit.pal.gp.core.service.OggettiService;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francol
 *
 */
@Service
public class FoDomandeOneriServiceImpl extends BaseServiceImpl<FoDomandeOneri, PkId> implements FoDomandeOneriService {

    private FoDomandeOneriDAO foDomandeOneriDAO;
    private InventarioprocedimentioneriService inventarioprocedimentioneriService;
    private FoDomandeOggettiService foDomandeOggettiService;
    private OggettiService oggettiService;
    private FoDomandeService foDomandeService;

    @Autowired
    public void setFoDomandeOneriDAO(FoDomandeOneriDAO foDomandeOneriDAO) {

	this.foDomandeOneriDAO = foDomandeOneriDAO;
    }

    @Autowired
    public void setInventarioprocedimentioneriService(InventarioprocedimentioneriService inventarioprocedimentioneriService) {

	this.inventarioprocedimentioneriService = inventarioprocedimentioneriService;
    }

    @Autowired
    public void setFoDomandeOggettiService(FoDomandeOggettiService foDomandeOggettiService) {

	this.foDomandeOggettiService = foDomandeOggettiService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setFoDomandeService(FoDomandeService foDomandeService) {

	this.foDomandeService = foDomandeService;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#insert(java.lang.Object)
     */
    @Override
    public void insert(FoDomandeOneri entity) {

	if (this.validateEntity(entity)) {
	    fixMergeEntityProperties(entity);
	    this.foDomandeOneriDAO.insert(entity);
	}
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#update(java.lang.Object)
     */
    @Override
    public void update(FoDomandeOneri entity) {

	if (this.validateEntity(entity)) {
	    fixMergeEntityProperties(entity);
	    this.foDomandeOneriDAO.update(entity);
	}
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#delete(java.lang.Object)
     */
    @Override
    public void delete(FoDomandeOneri entity) {

	this.foDomandeOneriDAO.delete(entity);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#findAll(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<FoDomandeOneri> findAll(Integer firstResult, Integer maxResult) {

	return this.foDomandeOneriDAO.findAll(firstResult, maxResult);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#findById(java.io.Serializable)
     */
    @Override
    public FoDomandeOneri findById(PkId id) {

	return this.foDomandeOneriDAO.findById(id);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl#getEntityClass()
     */
    @Override
    protected Class<FoDomandeOneri> getEntityClass() {

	return FoDomandeOneri.class;
    }

    @Override
    public List<FoDomandeOneri> findOneriByDomanda(Integer idDomanda, String idComuneDomanda) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.AND);
	fr.addFilterField(FilterUtils.equals("domanda.id.codice", idDomanda, Integer.class));
	fr.addFilterField(FilterUtils.equals("id.idcomune", idComuneDomanda, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.order("procedimento", "onere.inventarioprocedimenti", OrderTypeEnum.ASC));
	return this.foDomandeOneriDAO.findByFilterTable(ft);
    }

    @Override
    public List<FoDomandeOneri> findOneriByDomanda(FoDomande domanda) {

	List<FoDomandeOneri> oneri = new ArrayList<FoDomandeOneri>();
	if (domanda != null && domanda.getId().getCodice() != null) {
	    oneri = this.findOneriByDomanda(domanda.getId().getCodice(), domanda.getId().getIdcomune());
	}
	return oneri;
    }

    @Override
    public List<FoDomandeOneri> findOneriByRicevuta(Integer codOggettoRicevuta, String idComune) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.AND);
	fr.addFilterField(FilterUtils.equals("oggettoRicevutaId", codOggettoRicevuta, Integer.class));
	fr.addFilterField(FilterUtils.equals("id.idcomune", idComune, String.class));
	ft.addRestriction(fr);
	return this.foDomandeOneriDAO.findByFilterTable(ft);
    }

    @Override
    public List<FoDomandeOneri> loadOneriForProcedimenti(FoDomande domanda, String codiceComuneGruppo, List<PkId> idProcedimenti) {

	//caricamento oneri configurati in consolle BDRL per la domanda
	//incrociati con i dati oneri della domanda eventualmente già registrati in precedenza
	List<FoDomandeOneri> datiOneriDB = this.findOneriByDomanda(domanda.getId().getCodice(), domanda.getId().getIdcomune());
	List<FoDomandeOneri> oneriDbDelete = new ArrayList<FoDomandeOneri>();
	List<FoDomandeOneri> oneriDbDaFront = new ArrayList<FoDomandeOneri>();
	Set<FoDomandeOneri> oneriBdrl = new HashSet<FoDomandeOneri>();
	//verifico i dati degli onedi della domanda già salvati per gestire i record che hanno fk_inventarioprocedimentioneri = null
	for (FoDomandeOneri onereDB : datiOneriDB) {
	    if (onereDB.getOnere() == null || onereDB.getOnere().getId().getCodice() == null) {
		/*
		* se vengono eliminate le configurazioni degli oneri per il procedimento la fk a inventarioprocedimentioneri 
		* rimane settata a null. Questi record vengono ricaricati in maschera solo se hanno il flag_da_front = 1 altrimenti saranno eliminati
		*/
		if (BooleanUtils.isTrue(onereDB.getFlagDaFront())) {
		    onereDB.setOnere(new Inventarioprocedimentioneri());
		    oneriDbDaFront.add(onereDB);
		} else {
		    oneriDbDelete.add(onereDB);
		}
	    } else if (BooleanUtils.isTrue(onereDB.getOnere().getFlagDisattivo())) {
		oneriDbDelete.add(onereDB);
	    }
	}
	datiOneriDB.removeAll(oneriDbDaFront);
	datiOneriDB.removeAll(oneriDbDelete);
	Map<PkId, SortedMap<PkId, FoDomandeOneri>> mappaOneri = new HashMap<PkId, SortedMap<PkId, FoDomandeOneri>>();
	//configurazioni di default BDR e predisposizione della mappa/lista degli oneri previsti a livello regionale
	List<Inventarioprocedimentioneri> oneris = this.inventarioprocedimentioneriService.findOneriPerProcedimenti(ORMHelper.getIdcomunebase(),
		null, idProcedimenti, true);
	for (Inventarioprocedimentioneri onere : oneris) {
	    FoDomandeOneri onereDomanda = new FoDomandeOneri();
	    //cerco se ci sono dati già registrati per l'onere in questa doomanda
	    for (FoDomandeOneri onereDB : datiOneriDB) {
		if (onereDB.getOnere().getId().equals(onere.getId())) {
		    onereDomanda = onereDB;
		    onereDomanda.setImportoOld(onereDomanda.getImporto());
		    oneriBdrl.add(onereDB);
		    break;
		}
	    }
	    onereDomanda.setDescCausale(onere.getTipicausalioneri().getCoDescrizione());
	    onereDomanda.setDescProcedimento(onere.getInventarioprocedimenti().getProcedimento());
	    onereDomanda.setDomanda(domanda);
	    onereDomanda.setOnere(onere);
	    if (onere.getImporto() != null && onere.getImporto().compareTo(BigDecimal.ZERO) != 0) {
		onereDomanda.setImporto(onere.getImporto());
	    }
	    SortedMap<PkId, FoDomandeOneri> oneriProc = mappaOneri.get(onere.getInventarioprocedimenti().getId());
	    if (oneriProc == null) {
		oneriProc = new TreeMap<PkId, FoDomandeOneri>(new PkIdComparator());
	    }
	    oneriProc.put(onere.getTipicausalioneri().getId(), onereDomanda);
	    mappaOneri.put(onere.getInventarioprocedimenti().getId(), oneriProc);
	}
	//configurazioni locali per singolo comune o di default per tutti i comuni di un gruppo
	oneris = this.inventarioprocedimentioneriService.findOneriPerProcedimenti(ORMHelper.getIdcomune(), null, idProcedimenti, false);
	for (Inventarioprocedimentioneri onere : oneris) {
	    SortedMap<PkId, FoDomandeOneri> oneriProc = mappaOneri.get(onere.getInventarioprocedimenti().getId());
	    FoDomandeOneri onereDomanda = null;
	    //se ci sono configurazioni locali per causali/procedimenti non presenti nelle configurazioni regionali 
	    //sono record sporchi che devono essere ignorati
	    if (oneriProc != null) {
		onereDomanda = oneriProc.get(onere.getTipicausalioneri().getId());
	    } else {
		oneriProc = new TreeMap<PkId, FoDomandeOneri>(new PkIdComparator());
		mappaOneri.put(onere.getInventarioprocedimenti().getId(), oneriProc);
	    }
	    if (onereDomanda == null) {
		onereDomanda = new FoDomandeOneri();
	    }
	    //se l'impostazione locale è flaggata come disattiva rimuovo la riga che non sarà nemmeno visualizzata all'utente
	    if (BooleanUtils.isTrue(onere.getFlagDisattivo())) {
		oneriProc.remove(onere.getTipicausalioneri().getId());
		continue;
	    }
	    //cerco se ci sono dati già registrati per l'onere in questa doomanda
	    for (FoDomandeOneri onereDB : datiOneriDB) {
		if (onereDB.getOnere().getId().equals(onere.getId())) {
		    onereDomanda = onereDB;
		    onereDomanda.setImportoOld(onereDomanda.getImporto());
		    oneriBdrl.add(onereDB);
		    break;
		}
	    }
	    onereDomanda.setOnere(onere);
	    onereDomanda.setDescCausale(onere.getTipicausalioneri().getCoDescrizione());
	    onereDomanda.setDescProcedimento(onere.getInventarioprocedimenti().getProcedimento());
	    onereDomanda.setDomanda(domanda);
	    if (onere.getImporto() != null && onere.getImporto().compareTo(BigDecimal.ZERO) != 0) {
		//onereDomanda.setImporto(onere.getImporto());
	    }
	    oneriProc.put(onere.getTipicausalioneri().getId(), onereDomanda);
	}
	if (StringUtils.isNotEmpty(codiceComuneGruppo)) {
	    //configurazioni locali per comune specifico di un gruppo
	    oneris = this.inventarioprocedimentioneriService.findOneriPerProcedimenti(ORMHelper.getIdcomune(), codiceComuneGruppo, idProcedimenti,
		    false);
	    for (Inventarioprocedimentioneri onere : oneris) {
		SortedMap<PkId, FoDomandeOneri> oneriProc = mappaOneri.get(onere.getInventarioprocedimenti().getId());
		FoDomandeOneri onereDomanda = null;
		//se ci sono configurazioni locali per causali/procedimenti non presenti nelle configurazioni regionali 
		//sono record sporchi o configurazioni locali invalidate dalla disattivazione a livello regionale che devono essere ignorati
		if (oneriProc != null) {
		    onereDomanda = oneriProc.get(onere.getTipicausalioneri().getId());
		} else {
		    oneriProc = new TreeMap<PkId, FoDomandeOneri>(new PkIdComparator());
		    mappaOneri.put(onere.getInventarioprocedimenti().getId(), oneriProc);
		}
		if (onereDomanda == null) {
		    onereDomanda = new FoDomandeOneri();
		}
		//se l'impostazione locale è flaggata come disattiva rimuovo la riga che non sarà nemmeno visualizzata all'utente
		if (BooleanUtils.isTrue(onere.getFlagDisattivo())) {
		    oneriProc.remove(onere.getTipicausalioneri().getId());
		    continue;
		}
		//cerco se ci sono dati già registrati per l'onere in questa doomanda
		for (FoDomandeOneri onereDB : datiOneriDB) {
		    //
		    if (onereDB.getOnere().getId().equals(onere.getId())) {
			onereDomanda = onereDB;
			onereDomanda.setImportoOld(onereDomanda.getImporto());
			oneriBdrl.add(onereDB);
			break;
		    }
		}
		onereDomanda.setDescCausale(onere.getTipicausalioneri().getCoDescrizione());
		onereDomanda.setDescProcedimento(onere.getInventarioprocedimenti().getProcedimento());
		onereDomanda.setDomanda(domanda);
		onereDomanda.setOnere(onere);
		if (onere.getImporto() != null && onere.getImporto().compareTo(BigDecimal.ZERO) != 0) {
		    //onereDomanda.setImporto(onere.getImporto());
		}
		oneriProc.put(onere.getTipicausalioneri().getId(), onereDomanda);
	    }
	}
	//cancello oneri della domanda relativi a inventarioprocediemntioneri non più esistenti 
	//e quelli relativi a endoprocedimenti non più selezionati nella domanda
	datiOneriDB.removeAll(oneriBdrl);
	for (FoDomandeOneri deleteMe : datiOneriDB) {
	    this.delete(deleteMe);
	}
	for (FoDomandeOneri deleteMe : oneriDbDelete) {
	    this.delete(deleteMe);
	}
	List<FoDomandeOneri> retOneri = new ArrayList<FoDomandeOneri>();
	//restituisco i valori come lista mantenendo l'ordine dei procedimenti selezionati dall'utente
	for (PkId id : idProcedimenti) {
	    SortedMap<PkId, FoDomandeOneri> oneriProc = mappaOneri.get(id);
	    if (oneriProc != null) {
		Iterator<PkId> iterCausali = oneriProc.keySet().iterator();
		while (iterCausali.hasNext()) {
		    PkId pkCausale = (PkId) iterCausali.next();
		    FoDomandeOneri onere = oneriProc.get(pkCausale);
		    if (onere != null) {
			//per le righe che derivano dalla configurazione consolle
			if(!onere.isOnereAggiunto()){
			    //imposto l'importo configurato in consolle se specificato
			    if(onere.getOnere().getImporto() != null && onere.getOnere().getImporto().compareTo(BigDecimal.ZERO) != 0 ){
				onere.setImporto(onere.getOnere().getImporto());
			    }
			}
			retOneri.add(onere);
		    }
		}
	    }
	}
	for (FoDomandeOneri onerFront : oneriDbDaFront) {
	    retOneri.add(onerFront);
	}
	return retOneri;
    }

    @Override
    public void deleteRicevutaOnere(Integer codiceOggetto, String idComuneOggetto) {

	// cancellazione eventuali allegati domanda 
	Oggetti ogg = this.oggettiService.findByIdLazy(new PkId(idComuneOggetto, codiceOggetto));
	if (ogg != null) {
	    List<FoDomandeOggetti> allDom = this.foDomandeOggettiService.findByOggetto(idComuneOggetto, codiceOggetto);
	    for (FoDomandeOggetti all : allDom) {
		this.foDomandeOggettiService.delete(all);
	    }
	    //imposto a null il riferimento in FoDomandeOneri
	    List<FoDomandeOneri> oneriOgg = this.findOneriByRicevuta(codiceOggetto, ORMHelper.getIdcomune());
	    for (FoDomandeOneri onereDom : oneriOgg) {
		onereDom.setOggettoRicevuta(null);
		this.update(onereDom);
	    }
	    this.oggettiService.delete(ogg);
	}
    }

    @Override
    public List<FoDomandeOneri> salvaDatiOneri(List<FoDomandeOneri> oneri, Integer idDomanda) {

	List<FoDomandeOneri> retList = new ArrayList<FoDomandeOneri>(oneri.size());
	if (idDomanda != null) {
	    FoDomande domanda = this.foDomandeService.findById(new PkId(idDomanda));
	    List<FoDomandeOneri> oneriDb = this.findOneriByDomanda(idDomanda, ORMHelper.getIdcomune());
	    for (FoDomandeOneri onere : oneri) {
		//se stato pagamento != effettuato vengono svuotati eventuali valori di tipoPagamento, dataPagamento, rifPagamento e oggettoRicevuta
		if (!onere.getStatoPagamento().equals(FoDomandeOneriService.StatiPagamentoEnum.Effettuato.value())) {
		    onere.setTipoPagamento(null);
		    onere.setDataPagamento(null);
		    onere.setRifPagamento(null);
		}
		//inserimento nuovo onere
		onere.setDomanda(domanda);
		FoDomandeOggettiId fdoId = new FoDomandeOggettiId();
		fdoId.setIdcomune(ORMHelper.getIdcomune());
		fdoId.setIddomanda(idDomanda);
		if (onere.getId().getCodice() == null || onere.getId().getCodice().equals(0)) {
		    this.insert(onere);
		    if (onere.getOggettoRicevuta() != null && onere.getOggettoRicevuta().getId().getCodice() != null
			    && onere.getOggettoRicevuta().getId().getCodice() != 0) {
			FoDomandeOggetti fdo = new FoDomandeOggetti();
			Oggetti obj = new Oggetti();
			obj.getId().setCodice(onere.getOggettoRicevuta().getId().getCodice());
			fdo.setOggetti(obj);
			fdo.setFoDomande(domanda);
			fdo.setTipoFile(FoDomandeOggettiService.TIPO_FILE.RICEVUTA_ONERE.toString());
			fdoId.setCodiceoggetto(onere.getOggettoRicevuta().getId().getCodice());
			fdo.setId(fdoId);
			this.foDomandeOggettiService.insert(fdo);
		    }
		} else {
		    //aggiornamento onere già presente nel db
		    FoDomandeOneri onereOld = null;//this.findById(onere.getId());
		    Oggetti oldRicevuta = null;
		    for (int i = 0; i < oneriDb.size(); i++) {
			FoDomandeOneri onereDb = oneriDb.get(i);
			if (onereDb.getId().equals(onere.getId())) {
			    onereOld = onereDb;
			    oldRicevuta = onereOld.getOggettoRicevuta();
			    oneriDb.remove(i);
			}
		    }
		    this.update(onere);
		    //ricevuta da inserire
		    if (onere.getOggettoRicevuta() != null && onere.getOggettoRicevuta().getId().getCodice() != null
			    && onere.getOggettoRicevuta().getId().getCodice() != 0) {
			boolean doInsert = true;
			//ricevuta già presente nei dati db
			if (oldRicevuta != null && oldRicevuta.getId().getCodice() != null
				&& !oldRicevuta.getId().getCodice().equals(onere.getOggettoRicevuta().getId().getCodice())) {
			    FoDomandeOggettiId fdoOldId = new FoDomandeOggettiId(idDomanda, oldRicevuta.getId().getCodice());
			    FoDomandeOggetti fdoOld = foDomandeOggettiService.findById(fdoOldId);
			    if (fdoOld != null) {
				this.foDomandeOggettiService.delete(fdoOld);
			    } else {
				doInsert = false;
			    }
			}
			if (doInsert) {
			    FoDomandeOggetti fdo = new FoDomandeOggetti();
			    Oggetti obj = new Oggetti();
			    obj.setId(onere.getOggettoRicevuta().getId());
			    fdo.setOggetti(obj);
			    fdo.setFoDomande(domanda);
			    fdo.setTipoFile(FoDomandeOggettiService.TIPO_FILE.RICEVUTA_ONERE.toString());
			    fdoId.setCodiceoggetto(onere.getOggettoRicevuta().getId().getCodice());
			    fdo.setId(fdoId);
			    this.foDomandeOggettiService.insert(fdo);
			}
		    }
		    //onere senza ricevuta cancellazione vecchia ricevuta
		    else if (oldRicevuta != null && oldRicevuta.getId().getCodice() != null) {
			//l'onere non ha più la ricevuta --> cancello l'allegato della domanda e l'oggetto se erano presenti
			fdoId = new FoDomandeOggettiId();
			fdoId.setIdcomune(ORMHelper.getIdcomune());
			fdoId.setIddomanda(idDomanda);
			fdoId.setCodiceoggetto(oldRicevuta.getId().getCodice());
			FoDomandeOggetti fdo = new FoDomandeOggetti();
			fdo.setId(fdoId);
			fdo.setTipoFile(FoDomandeOggettiService.TIPO_FILE.RICEVUTA_ONERE.toString());
			this.foDomandeOggettiService.delete(fdo);
		    }
		}
		onere = this.findById(onere.getId());
		retList.add(onere);
	    }
	    //dopo l'elaborazione dei dati dell'utente elimino i vecchi record che non sono più stati postati perchè evidentemente l'utente li ha eliminati
	    for (FoDomandeOneri onereToDelete : oneriDb) {
		this.delete(onereToDelete);
	    }
	} else {
	    throw new RuntimeException("Impossibile salvare i dati degli oneri, manca il riferimento alla domanda.");
	}
	return retList;
    }

    @Override
    public List<ErroreValidazione> validazioneOneri(List<FoDomandeOneri> oneri) {

	List<ErroreValidazione> errors = new ArrayList<ErroreValidazione>();
	String msg = "Specificare {0} per l''onere {1}";
	if (oneri != null) {
	    String campo = null;
	    String descOnere = null;
	    int countPagati = 0;
	    int countRicevute = 0;
	    for (int i = 0; i < oneri.size(); i++) {
		FoDomandeOneri onere = oneri.get(i);
		//importo obbligatorio
		if (onere.getImporto() == null) {
		    campo = "l'importo";
		    descOnere = new StringBuilder(onere.getDescCausale()).append(" [").append(onere.getDescProcedimento()).append("]").toString();
		    ErroreValidazione error = new ErroreValidazione();
		    MessaggioErrore errmsg = new MessaggioErrore(MessageFormat.format(msg, campo, descOnere));
		    errmsg.setRowIndex(new int[] { i });
		    error.setBloccante(true);
		    error.getErrori().add(errmsg);
		    error.setIdCampo("importo");
		    errors.add(error);
		}
		//Stato pagamento obbligatorio
		if (StringUtils.isBlank(onere.getStatoPagamento())) {
		    campo = "il pagamento";
		    descOnere = new StringBuilder(onere.getDescCausale()).append(" [").append(onere.getDescProcedimento()).append("]").toString();
		    ErroreValidazione error = new ErroreValidazione();
		    MessaggioErrore errmsg = new MessaggioErrore(MessageFormat.format(msg, campo, descOnere));
		    errmsg.setRowIndex(new int[] { i });
		    error.setBloccante(true);
		    error.getErrori().add(errmsg);
		    error.setIdCampo("statoPagamento");
		    errors.add(error);
		} else if (onere.getStatoPagamento().equals(StatiPagamentoEnum.Effettuato.value())) {
		    //se stato pagamento == pagato allora tipo, data e riferimenti pagamento sono obbligatori
		    countPagati++;
		    if (StringUtils.isBlank(onere.getTipoPagamento())) {
			campo = "il tipo di pagamento";
			descOnere = new StringBuilder(onere.getDescCausale()).append(" [").append(onere.getDescProcedimento()).append("]").toString();
			ErroreValidazione error = new ErroreValidazione();
			MessaggioErrore errmsg = new MessaggioErrore(MessageFormat.format(msg, campo, descOnere));
			errmsg.setRowIndex(new int[] { i });
			error.setBloccante(true);
			error.getErrori().add(errmsg);
			error.setIdCampo("tipoPagamento");
			errors.add(error);
		    }
		    if (StringUtils.isBlank(onere.getRifPagamento())) {
			campo = "il riferimento del pagamento";
			descOnere = new StringBuilder(onere.getDescCausale()).append(" [").append(onere.getDescProcedimento()).append("]").toString();
			ErroreValidazione error = new ErroreValidazione();
			MessaggioErrore errmsg = new MessaggioErrore(MessageFormat.format(msg, campo, descOnere));
			errmsg.setRowIndex(new int[] { i });
			error.setBloccante(true);
			error.getErrori().add(errmsg);
			error.setIdCampo("rifPagamento");
			errors.add(error);
		    }
		    if (onere.getDataPagamento() == null) {
			campo = "la data del pagamento";
			descOnere = new StringBuilder(onere.getDescCausale()).append(" [").append(onere.getDescProcedimento()).append("]").toString();
			ErroreValidazione error = new ErroreValidazione();
			MessaggioErrore errmsg = new MessaggioErrore(MessageFormat.format(msg, campo, descOnere));
			errmsg.setRowIndex(new int[] { i });
			error.setBloccante(true);
			error.getErrori().add(errmsg);
			error.setIdCampo("dataPagamento");
			errors.add(error);
		    }
		    if (onere.getOggettoRicevuta() != null && onere.getOggettoRicevuta().getId().getCodice() != null) {
			countRicevute++;
		    }
		}
		//per gli oneri aggiunti dall'utente è obbligatorio specificare la causale e selezionare il procedimento per cui si specifica il pagamento
		if (BooleanUtils.isTrue(onere.getFlagDaFront())) {
		    if (StringUtils.isBlank(onere.getDescCausale())) {
			campo = "la causale";
			descOnere = "alla riga " + (i + 1);
			ErroreValidazione error = new ErroreValidazione();
			MessaggioErrore errmsg = new MessaggioErrore(MessageFormat.format(msg, campo, descOnere));
			errmsg.setRowIndex(new int[] { i });
			error.setBloccante(true);
			error.getErrori().add(errmsg);
			error.setIdCampo("descCausale");
			errors.add(error);
		    }
		    if (StringUtils.isBlank(onere.getDescProcedimento())) {
			campo = "la causale";
			descOnere = "alla riga " + (i + 1);
			ErroreValidazione error = new ErroreValidazione();
			MessaggioErrore errmsg = new MessaggioErrore(MessageFormat.format(msg, campo, descOnere));
			errmsg.setRowIndex(new int[] { i });
			error.setBloccante(true);
			error.getErrori().add(errmsg);
			error.setIdCampo("descProcedimento");
			errors.add(error);
		    }
		}
	    }
	    //se almeno un onere ha stato pagamento == pagato allora è necessario caricare almeno una ricevuta
	    if (countPagati > 0 && countRicevute == 0) {
		ErroreValidazione error = new ErroreValidazione();
		MessaggioErrore errmsg = new MessaggioErrore("Caricare almeno una ricevuta che attesti l'avvenuto pagamento degli oneri");
		error.setBloccante(true);
		error.getErrori().add(errmsg);
		error.setIdCampo("oggettoRicevuta");
		errors.add(error);
	    }
	}
	return errors;
    }

    @Override
    public List<String> getListaStatiPagamento() {

	FoDomandeOneriService.StatiPagamentoEnum[] statiEnum = FoDomandeOneriService.StatiPagamentoEnum.values();
	List<String> statiPagamento = new ArrayList<String>();
	for (FoDomandeOneriService.StatiPagamentoEnum statoEnum : statiEnum) {
	    statiPagamento.add(statoEnum.value());
	}
	return statiPagamento;
    }

    @Override
    protected void fixMergeEntityProperties(FoDomandeOneri entity) {

	super.fixMergeEntityProperties(entity);
	if (entity.getOggettoRicevuta() != null) {
	    if (entity.getOggettoRicevuta().getId() == null || entity.getOggettoRicevuta().getId().getCodice() == null
		    || entity.getOggettoRicevuta().getId().getCodice().equals(0)) {
		entity.setOggettoRicevuta(null);
	    }
	}
	if (entity.getOnere() != null) {
	    if (entity.getOnere().getId() == null || entity.getOnere().getId().getCodice() == null || entity.getOnere().getId().getCodice().equals(0)) {
		entity.setOnere(null);
	    }
	}
    }
}
