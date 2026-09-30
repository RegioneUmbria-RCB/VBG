package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.IstanzeeventiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Categorieeventibase;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeeventiListHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeeventiFilter;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.CategorieeventibaseService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author fabrizioc
 */
@Service
public class IstanzeeventiServiceImpl extends BaseServiceImpl<Istanzeeventi, PkId> implements IstanzeeventiService {

    private IstanzeeventiDAO istanzeeventiDAO;
    private CategorieeventibaseService categorieeventibaseService;
    private ComuniassociatiService comuniassociatiService;
    private SoftwareService softwareService;
    private ResponsabiliService responsabiliService;
    private AlberoprocService alberoprocService;
    private static final Logger log = LoggerFactory.getLogger(IstanzeeventiServiceImpl.class);

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setIstanzeeventiDAO(IstanzeeventiDAO istanzeeventiDAO) {

	this.istanzeeventiDAO = istanzeeventiDAO;
    }

    @Autowired
    public void setCategorieeventibaseService(CategorieeventibaseService categorieeventibaseService) {

	this.categorieeventibaseService = categorieeventibaseService;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Override
    protected Class<Istanzeeventi> getEntityClass() {

	return Istanzeeventi.class;
    }

    @Override
    public List<Istanzeeventi> findAll(Integer firstResult, Integer maxResult) {

	return istanzeeventiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public List<Istanzeeventi> findAllByResponsabile(Responsabili responsabile, Integer firstResult, Integer maxResult) {

	IstanzeeventiFilter filter = new IstanzeeventiFilter();
	filter.setFlagLetto(Boolean.FALSE);
	// Gestione del filtro software
	if (ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
	    // se la chiamata arriva da TT allora recupero gli eventi per tutti i software attivi
	    List<Software> softwareAttiviList = new ArrayList<Software>();
	    if (EntityUtils.getNestedProperty(responsabile, "id.codice") != null) {
		softwareAttiviList = softwareService.findSoftwareAbilitati(responsabile);
	    } else {
		softwareAttiviList = softwareService.findSoftwareAttivi(false);
	    }
	    filter.setSoftwares(softwareAttiviList);
	}
	List<Istanzeeventi> eventi = this.findByFilter(filter, firstResult, maxResult);
	if (EntityUtils.getNestedProperty(responsabile, "id.codice") == null) {
	    return eventi;
	}
	List<Istanzeeventi> eventiVisualizzabili = new ArrayList<Istanzeeventi>();
	if (eventi != null) {
	    for (Istanzeeventi evento : eventi) {
	    }
	}
	return eventiVisualizzabili;
    }

    @Override
    public void insert(Istanzeeventi entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeeventiDAO.insert(entity);
	}
    }

    private void dataIntegration(Istanzeeventi entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'evento passato è nullo");
	}
	if (entity.getFlagLetto() == null) {
	    entity.setFlagLetto(Boolean.FALSE);
	}
	if (entity.getData() == null) {
	    entity.setData(Calendar.getInstance().getTime());
	}
	if (StringUtils.isNotBlank(entity.getDescrizione())) {
	    if (entity.getDescrizione().length() > 4000) {
		entity.setDescrizione(StringUtils.left(entity.getDescrizione(), 3999));
	    }
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Istanzeeventi entity) {

	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
	// dato che l'id della categoria è settato a livello di codice senza la ricerca per controllare se esiste
	// catturo eventuali eccezioni nel caso di codici che non esistono
	if (EntityUtils.getNestedProperty(entity.getCategorieeventibase(), "id") != null) {
	    Categorieeventibase cat = categorieeventibaseService.findById(entity.getCategorieeventibase().getId());
	    entity.setCategorieeventibase(cat);
	} else {
	    entity.setCategorieeventibase(null);
	}
    }

    @Override
    public Istanzeeventi findById(PkId id) {

	return istanzeeventiDAO.findById(id);
    }

    @Override
    public void update(Istanzeeventi entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeeventiDAO.update(entity);
	}
    }

    @Override
    public void delete(Istanzeeventi entity) {

	if (isDeleteAllowed(entity)) {
	    istanzeeventiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Istanzeeventi entity) {

	// La cancellazione è sempre permessa
	return true;
    }

    @Override
    public List<Istanzeeventi> findByFilter(IstanzeeventiFilter filter, Integer firstResult, Integer maxResult) {

	return istanzeeventiDAO.findByFilter(filter, firstResult, maxResult);
    }

    @Override
    public List<Istanzeeventi> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	return istanzeeventiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<Istanzeeventi> findAllByScadenzarioFilter(BatchScadenzarioFilter batchScadenzarioFilter, Integer firstResult, Integer maxResult) {

	//	BatchScadenzarioFilterHelper helper = new BatchScadenzarioFilterHelper(responsabiliService, softwareService, alberoprocService,
	//		statiistanzaService, comuniassociatiService);
	//	FilterTable ft = helper.getFilterTable(batchScadenzarioFilter, false, QUERY_PER.ISTANZE_EVENTI);
	//	FilterRestriction letto = new FilterRestriction();
	//	letto.addFilterField(FilterUtils.equals("flagLetto", Boolean.FALSE, Boolean.class));
	//	// Controllo se l'istanza è multi comune, nel saco devo aggiungere i filtro per codice comune dell'istanza.I record dovranno essere
	//	// filtrati per i soli comuni abilitati all'operatore
	//	if (log.isDebugEnabled()) {
	//	    log.debug("buildQuery# Controllo se si tratta di un installazione con idcomune {} è multi comune", ORMHelper.getIdcomune());
	//	}
	//	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	//	if (isComuniAssociati) {
	//	    Responsabili responsabile = batchScadenzarioFilter.getResponsabile();
	//	    if (log.isDebugEnabled()) {
	//		log.debug("buildQuery# E' un installazione multicomune, recupero i comuni configurati per l'operatore {} ({})", new Object[] {
	//			responsabile.getResponsabile(), responsabile.getId().getCodice() });
	//	    }
	//	    List<Responsabilicomuni> responsabilicomunis = comuniassociatiService.checkComuniAbilitatiPerResponsabile();
	//	    if (responsabilicomunis != null && !responsabilicomunis.isEmpty()) {
	//		String[] codiceComune = new String[responsabilicomunis.size()];
	//		int i = 0;
	//		for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
	//		    codiceComune[i] = responsabilicomuni.getId().getCodicecomune();
	//		    i++;
	//		}
	//		letto.addFilterField(FilterUtils.in("codicecomune", codiceComune, "istanze.comune", String.class));
	//	    }
	//	}
	//	ft.addRestriction(letto);
	return new ArrayList<Istanzeeventi>();
    }

    @Override
    public List<IstanzeeventiListHelper> findByScadenzarioFilterHelper(BatchScadenzarioFilter batchScadenzarioFilter, Integer firstResult,
	    Integer maxResult) {

	return istanzeeventiDAO.findByScadenzarioFilterHelper(batchScadenzarioFilter, firstResult, maxResult);
    }

    @Override
    public int countByScadenzarioFilterHelper(BatchScadenzarioFilter batchScadenzarioFilter) {

	return istanzeeventiDAO.countByScadenzarioFilterHelper(batchScadenzarioFilter);
    }

    @Override
    public void clear() {

	istanzeeventiDAO.clear();
    }

    @Override
    public void insertEventoBackoffice(String descrizione, String idCategoria, Software software) {

	Istanzeeventi evento = new Istanzeeventi();
	if (StringUtils.isNotBlank(idCategoria)) {
	    Categorieeventibase cat = categorieeventibaseService.findById(idCategoria);
	    evento.setCategorieeventibase(cat);
	}
	evento.setData(new Date());
	evento.setDescrizione(descrizione);
	evento.setFlagLetto(Boolean.FALSE);
	evento.setSoftware(software);
	this.insert(evento);
    }

    @Override
    public int countByEventiSistema(Responsabili responsabile, boolean isLetto) {

	FilterTable filterTable = getFilterForEventiSistema(responsabile, isLetto);
	filterTable.addOrder(FilterUtils.orderDesc("data"));
	return istanzeeventiDAO.countRecord(filterTable);
    }

    @Override
    public List<Istanzeeventi> findEventiSistema(Responsabili responsabile, Integer firstResult, Integer maxResult, boolean isLetto) {

	FilterTable filterTable = getFilterForEventiSistema(responsabile, isLetto);
	filterTable.addOrder(FilterUtils.orderDesc("data"));
	return istanzeeventiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    private FilterTable getFilterForEventiSistema(Responsabili responsabile, boolean isLetto) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.isNull("id.codice", "istanze"));
	fr.addFilterField(FilterUtils.isNull("id.codice", "movimenti"));
	fr.addFilterField(FilterUtils.equals("flagLetto", isLetto, Boolean.class));
	//System.out.println(ORMHelper.getSoftware());
	if (!ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT) || responsabile == null) {
	    fr.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "software", String.class));
	} else {
	    List<Software> softwareAttiviList = softwareService.findSoftwareAbilitati(responsabile, false);
	    List<String> codiciSoftware = new ArrayList<String>();
	    for (Iterator iterator = softwareAttiviList.iterator(); iterator.hasNext();) {
		Software software = (Software) iterator.next();
		codiciSoftware.add(software.getCodice());
	    }
	    fr.addFilterField(FilterUtils.in("codice", codiciSoftware.toArray(), "software", String.class));
	}
	ft.addRestriction(fr);
	return ft;
    }

    @Override
    public int countEventiNonLettiByMovimento(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "movimenti", Integer.class));
	fr.addFilterField(FilterUtils.equals("flagLetto", false, Boolean.class));
	ft.addRestriction(fr);
	int ris = istanzeeventiDAO.countRecord(ft);
	return ris;
    }
}
