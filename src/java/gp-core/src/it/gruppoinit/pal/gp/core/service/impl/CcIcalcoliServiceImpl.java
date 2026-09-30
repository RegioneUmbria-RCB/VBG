package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoliDAO;
import it.gruppoinit.pal.gp.core.dao.CcItabella1DAO;
import it.gruppoinit.pal.gp.core.dao.CcItabella2DAO;
import it.gruppoinit.pal.gp.core.dao.CcItabella3DAO;
import it.gruppoinit.pal.gp.core.dao.CcItabella4DAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoli;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoliDettagliot;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontributo;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloTcontributo;
import it.gruppoinit.pal.gp.core.domain.CcIcalcolotcontributoRiduz;
import it.gruppoinit.pal.gp.core.domain.CcItabella1;
import it.gruppoinit.pal.gp.core.domain.CcItabella2;
import it.gruppoinit.pal.gp.core.domain.CcItabella3;
import it.gruppoinit.pal.gp.core.domain.CcItabella4;
import it.gruppoinit.pal.gp.core.domain.CcTabellaClassiedificio;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListsDTO;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CcIcalcoliDettagliotService;
import it.gruppoinit.pal.gp.core.service.CcIcalcoliService;
import it.gruppoinit.pal.gp.core.service.CcIcalcoloDcontributoService;
import it.gruppoinit.pal.gp.core.service.CcIcalcoloTcontributoService;
import it.gruppoinit.pal.gp.core.service.CcIcalcolotcontributoRiduzService;
import it.gruppoinit.pal.gp.core.service.CcItabella1Service;
import it.gruppoinit.pal.gp.core.service.CcItabella2Service;
import it.gruppoinit.pal.gp.core.service.CcItabella3Service;
import it.gruppoinit.pal.gp.core.service.CcItabella4Service;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class CcIcalcoliServiceImpl extends BaseServiceImpl<CcIcalcoli, PkId> implements CcIcalcoliService {

    private CcIcalcoliDAO ccicalcoliDAO;

    @Autowired
    public void setCcIcalcoliDAO(CcIcalcoliDAO ccicalcoliDAO) {

	this.ccicalcoliDAO = ccicalcoliDAO;
    }

    private CcItabella1DAO tab1DAO;

    @Autowired
    public void setTab1DAO(CcItabella1DAO tab1dao) {

	tab1DAO = tab1dao;
    }

    private CcItabella2DAO tab2DAO;

    @Autowired
    public void setTab2DAO(CcItabella2DAO tab2dao) {

	tab2DAO = tab2dao;
    }

    private CcItabella3DAO tab3DAO;

    @Autowired
    public void setTab3DAO(CcItabella3DAO tab3dao) {

	tab3DAO = tab3dao;
    }

    private CcItabella4DAO tab4DAO;

    @Autowired
    public void setTab4DAO(CcItabella4DAO tab4dao) {

	tab4DAO = tab4dao;
    }

    private CcItabella1Service tab1service;

    @Autowired
    public void setTab1service(CcItabella1Service tab1service) {

	this.tab1service = tab1service;
    }

    private CcItabella2Service tab2service;

    @Autowired
    public void setTab2service(CcItabella2Service tab2service) {

	this.tab2service = tab2service;
    }

    private CcItabella3Service tab3service;

    @Autowired
    public void setTab3service(CcItabella3Service tab3service) {

	this.tab3service = tab3service;
    }

    private CcItabella4Service tab4service;

    @Autowired
    public void setTab4service(CcItabella4Service tab4service) {

	this.tab4service = tab4service;
    }

    private CcIcalcoloTcontributoService tContributiCalcoloService;

    @Autowired
    public void settContributiCalcoloService(CcIcalcoloTcontributoService tContributiCalcoloService) {

	this.tContributiCalcoloService = tContributiCalcoloService;
    }

    private CcIcalcoloDcontributoService dContributiCalcoloService;

    @Autowired
    public void setdContributiCalcoloService(CcIcalcoloDcontributoService dContributiCalcoloService) {

	this.dContributiCalcoloService = dContributiCalcoloService;
    }

    private CcIcalcolotcontributoRiduzService contributoRiduzService;

    @Autowired
    public void setContributoRiduzService(CcIcalcolotcontributoRiduzService contributoRiduzService) {

	this.contributoRiduzService = contributoRiduzService;
    }

    private CcIcalcoliDettagliotService ccIcalcoliDettagliotService;

    @Autowired
    public void setCcIcalcoliDettagliotService(CcIcalcoliDettagliotService ccIcalcoliDettagliotService) {

	this.ccIcalcoliDettagliotService = ccIcalcoliDettagliotService;
    }

    @Override
    protected Class<CcIcalcoli> getEntityClass() {

	return CcIcalcoli.class;
    }

    @Override
    public List<CcIcalcoli> findAll(Integer firstResult, Integer maxResult) {

	return ccicalcoliDAO.findAll(firstResult, maxResult);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CcIcalcoliService#insertOrUpdate(it.gruppoinit.pal.gp.core.domain.CcIcalcoli)
     */
    @Override
    public void insertOrUpdate(CcIcalcoli calcolo) {

	if (calcolo.getId() != null && calcolo.getId().getCodice() != null) {
	    update(calcolo);
	} else {
	    insert(calcolo);
	}
    }

    @Override
    public void insert(CcIcalcoli entity) {

	if (validateEntity(entity)) {
	    IstanzeListsDTO copy = new IstanzeListsDTO();
	    try {
		BeanUtils.copyProperties(copy, entity);
	    } catch (IllegalAccessException e) {
		throw new RuntimeException(e);
	    } catch (InvocationTargetException e) {
		throw new RuntimeException(e);
	    }
	    entity.setCcIcalcoloTcontributos(new HashSet<CcIcalcoloTcontributo>());
	    entity.setCcItabella1s(new HashSet<CcItabella1>());
	    entity.setCcItabella2s(new HashSet<CcItabella2>());
	    entity.setCcItabella3s(new HashSet<CcItabella3>());
	    entity.setCcItabella4s(new HashSet<CcItabella4>());
	    //entity.setCcIcalcoliDettagliots(new HashSet<CcIcalcoliDettagliot>());
	    ccicalcoliDAO.insert(entity);
	    childDataInsert(entity, copy);
	}
    }

    @Override
    public CcIcalcoli findById(PkId id) {

	return ccicalcoliDAO.findById(id);
    }

    @Override
    public void update(CcIcalcoli entity) {

	if (validateEntity(entity)) {
	    IstanzeListsDTO copy = new IstanzeListsDTO();
	    try {
		BeanUtils.copyProperties(copy, entity);
	    } catch (IllegalAccessException e) {
		throw new RuntimeException(e);
	    } catch (InvocationTargetException e) {
		throw new RuntimeException(e);
	    }
	    entity.setCcIcalcoloTcontributos(new HashSet<CcIcalcoloTcontributo>());
	    entity.setCcItabella1s(new HashSet<CcItabella1>());
	    entity.setCcItabella2s(new HashSet<CcItabella2>());
	    entity.setCcItabella3s(new HashSet<CcItabella3>());
	    entity.setCcItabella4s(new HashSet<CcItabella4>());
	    //entity.setCcIcalcoliDettagliots(new HashSet<CcIcalcoliDettagliot>());
	    ccicalcoliDAO.update(entity);
	    childDataUpdate(entity, copy);
	}
    }

    @Override
    public void delete(CcIcalcoli entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    ccicalcoliDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(CcIcalcoli entity) {
    
        Set<CcIcalcoliDettagliot> ccIcalcoliDettagliots=entity.getCcIcalcoliDettagliots();
        for (CcIcalcoliDettagliot ccIcalcoliDettagliot : ccIcalcoliDettagliots) {
	    ccIcalcoliDettagliotService.delete(ccIcalcoliDettagliot);
	}
        Set<CcItabella1> ccItabella1s=entity.getCcItabella1s();
        for (CcItabella1 ccItabella1 : ccItabella1s) {
            tab1service.delete(ccItabella1);
	}
        
        Set<CcItabella2> ccItabella2s=entity.getCcItabella2s();
        for (CcItabella2 ccItabella2 : ccItabella2s) {
            tab2service.delete(ccItabella2);
	}
        Set<CcItabella3> ccItabella3s=entity.getCcItabella3s();
        for (CcItabella3 ccItabella3 : ccItabella3s) {
            tab3service.delete(ccItabella3);
	}
        Set<CcItabella4> ccItabella4s =entity.getCcItabella4s();
        for (CcItabella4 ccItabella4 : ccItabella4s) {
            tab4service.delete(ccItabella4);
    	}
    }

    protected boolean isDeleteAllowed(CcIcalcoli entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO _validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public int countByCcTabellaClassiedificio(CcTabellaClassiedificio entity) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.codice", entity.getId().getCodice(), "ccTabellaClassiedificio", Integer.class));
	filterTable.addRestriction(filterRestriction);
	return ccicalcoliDAO.countRecord(filterTable);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CcIcalcoliService#findRigheTabella1(it.gruppoinit.pal.gp.core.domain.CcIcalcoli)
     */
    @Override
    public List<CcItabella1> findRigheTabella1(CcIcalcoli ccicalcoli) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	ft.addOrder(FilterUtils.orderAsc("da", "ccClassisuperfici"));
	ft.addOrder(FilterUtils.orderAsc("a", "ccClassisuperfici"));
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", ccicalcoli.getId().getCodice(), "ccIcalcoli", Integer.class));
	ft.addRestriction(fr);
	return tab1DAO.findByFilterTable(ft);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CcIcalcoliService#findRigheTabella2(it.gruppoinit.pal.gp.core.domain.CcIcalcoli)
     */
    @Override
    public List<CcItabella2> findRigheTabella2(CcIcalcoli ccicalcoli) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	ft.addOrder(FilterUtils.orderAsc("descrizione", "ccDettaglisuperficie"));
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", ccicalcoli.getId().getCodice(), "ccIcalcoli", Integer.class));
	ft.addRestriction(fr);
	return tab2DAO.findByFilterTable(ft);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CcIcalcoliService#findRigheTabella3(it.gruppoinit.pal.gp.core.domain.CcIcalcoli)
     */
    @Override
    public List<CcItabella3> findRigheTabella3(CcIcalcoli ccicalcoli) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	ft.addOrder(FilterUtils.orderAsc("rapportoSuSnrDa", "ccTabella3"));
	ft.addOrder(FilterUtils.orderAsc("rapportoSuSnrA", "ccTabella3"));
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", ccicalcoli.getId().getCodice(), "ccIcalcoli", Integer.class));
	ft.addRestriction(fr);
	return tab3DAO.findByFilterTable(ft);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CcIcalcoliService#findRigheTabella4(it.gruppoinit.pal.gp.core.domain.CcIcalcoli)
     */
    @Override
    public List<CcItabella4> findRigheTabella4(CcIcalcoli ccicalcoli) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	ft.addOrder(FilterUtils.orderAsc("descrizione", "ccTabellaCaratterist"));
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", ccicalcoli.getId().getCodice(), "ccIcalcoli", Integer.class));
	ft.addRestriction(fr);
	return tab4DAO.findByFilterTable(ft);
    }

    @Override
    public CcIcalcoli findByCcIcalcoloTcontributo(CcIcalcoloTcontributo entity) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", entity.getId().getCodice(), "ccIcalcoloTcontributos", Integer.class));
	ft.addRestriction(fr);
	List<CcIcalcoli> list = ccicalcoliDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    /**
     * Inserisce le righe in CcItabella1, CcItabella2, CcItabella3k, CcItabella4 e CcIcalcoloTcontributo impostando in
     * esse il riferimento a CcIcalcoli che deve essere già stato inserito (ID valorizzato)
     * 
     * @param calcolo
     */
    private void childDataInsert(CcIcalcoli calcolo, IstanzeListsDTO copy) {

	if (calcolo.getId() != null && calcolo.getId().getCodice() != null) {
	    CcIcalcoli calcoloCopy = new CcIcalcoli();
	    calcoloCopy.setId(calcolo.getId());
	    Set<CcItabella1> tab1 = copy.getCcItabella1s();
	    for (CcItabella1 tab1Row : tab1) {
		tab1Row.setCcIcalcoli(calcoloCopy);
		tab1service.insert(tab1Row);
	    }
	    Set<CcItabella2> tab2 = copy.getCcItabella2s();
	    for (CcItabella2 tab2Row : tab2) {
		tab2Row.setCcIcalcoli(calcoloCopy);
		tab2service.insert(tab2Row);
	    }
	    Set<CcItabella3> tab3 = copy.getCcItabella3s();
	    for (CcItabella3 tab3Row : tab3) {
		tab3Row.setCcIcalcoli(calcoloCopy);
		tab3service.insert(tab3Row);
	    }
	    Set<CcItabella4> tab4 = copy.getCcItabella4s();
	    for (CcItabella4 tab4Row : tab4) {
		tab4Row.setCcIcalcoli(calcoloCopy);
		tab4service.insert(tab4Row);
	    }
	    Set<CcIcalcoloTcontributo> contributi = copy.getCcIcalcoloTcontributos();
	    if (contributi != null && contributi.size() > 0) {
		CcIcalcoloTcontributo contributo = contributi.iterator().next();
		contributo.setCcIcalcoli(calcoloCopy);
		tContributiCalcoloService.insert(contributo);
	    }
	} else {
	    throw new BusinessValidationException(
		    "Impossibile inserire le righe dei dettagli del calcolo perchè la testata del calcolo non ha l'ID impostato.");
	}
    }

    /**
     * Aggiorna le righe in CcItabella1, CcItabella2, CcItabella3, CcItabella4 e CcIcalcoloTcontributo impostando in
     * esse il riferimento a CcIcalcoli che deve essere già stato inserito (ID valorizzato)
     * 
     * @param calcolo
     */
    private void childDataUpdate(CcIcalcoli calcolo, IstanzeListsDTO copy) {

	if (calcolo.getId() != null && calcolo.getId().getCodice() != null) {
	    CcIcalcoli calcoloCopy = new CcIcalcoli();
	    calcoloCopy.setId(calcolo.getId());
	    Set<CcItabella1> tab1 = copy.getCcItabella1s();
	    for (CcItabella1 tab1Row : tab1) {
		tab1Row.setCcIcalcoli(calcoloCopy);
		tab1service.update(tab1Row);
	    }
	    Set<CcItabella2> tab2 = copy.getCcItabella2s();
	    for (CcItabella2 tab2Row : tab2) {
		tab2Row.setCcIcalcoli(calcoloCopy);
		tab2service.update(tab2Row);
	    }
	    Set<CcItabella3> tab3 = copy.getCcItabella3s();
	    for (CcItabella3 tab3Row : tab3) {
		tab3Row.setCcIcalcoli(calcoloCopy);
		tab3service.update(tab3Row);
	    }
	    Set<CcItabella4> tab4 = copy.getCcItabella4s();
	    for (CcItabella4 tab4Row : tab4) {
		tab4Row.setCcIcalcoli(calcoloCopy);
		tab4service.update(tab4Row);
	    }
	    Set<CcIcalcoloTcontributo> contributi = copy.getCcIcalcoloTcontributos();
	    if (contributi != null && contributi.size() > 0) {
		CcIcalcoloTcontributo contributo = contributi.iterator().next();
		contributo.setCcIcalcoli(calcoloCopy);
		tContributiCalcoloService.update(contributo);
	    }
	} else {
	    throw new BusinessValidationException(
		    "Impossibile aggiornare le righe dei dettagli del calcolo perchè la testata del calcolo non ha l'ID impostato.");
	}
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CcIcalcoliService#salvaCoefficienteContributo(it.gruppoinit.pal.gp.core.domain.CcIcalcoloTcontributo, java.util.Set)
     */
    @Override
    public void salvaCoefficienteContributo(CcIcalcoloTcontributo tContributo, Set<CcIcalcolotcontributoRiduz> riduzContributo) {

	// 
	Set<CcIcalcoloDcontributo> dContributos = tContributo.getCcIcalcoloDcontributos();
	tContributo.setCcIcalcoloDcontributos(new HashSet<CcIcalcoloDcontributo>());
	Set<CcIcalcolotcontributoRiduz> contibRiduzDelete = tContributo.getCcIcalcolotcontributoRiduzs();
	tContributo.setCcIcalcolotcontributoRiduzs(new HashSet<CcIcalcolotcontributoRiduz>());
	//cancellazione vecchi CcIcalcolotcontributoRiduz
	for (CcIcalcolotcontributoRiduz deleteRiduz : contibRiduzDelete) {
	    contributoRiduzService.delete(deleteRiduz);
	}
	//aggiornamento CcIcalcoloTcontributo
	tContributiCalcoloService.update(tContributo);
	CcIcalcoloDcontributo dContributo = null;
	if (dContributos.size() > 0) {
	    dContributo = dContributos.iterator().next();
	}
	if (dContributo != null) {
	    if (dContributo.getId().getCodice() != null) {
		//aggiornamento CcIcalcoloDcontributo
		dContributiCalcoloService.update(dContributo);
	    } else {
		//inserimento CcIcalcoloDcontributo
		dContributiCalcoloService.insert(dContributo);
	    }
	}
	//inserimento nuovi record CcIcalcolotcontributoRiduz
	for (CcIcalcolotcontributoRiduz insertRiduz : riduzContributo) {
	    contributoRiduzService.insert(insertRiduz);
	}
    }
}
