package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AreeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AreeService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreeServiceImpl extends BaseServiceImpl<Aree, PkId> implements AreeService {

    private AreeDAO areeDAO;
    private ComuniassociatiService comuniassociatiService;

    @Autowired
    public void setAreeDAO(AreeDAO areeDAO) {

	this.areeDAO = areeDAO;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Override
    protected Class<Aree> getEntityClass() {

	return Aree.class;
    }

    @Override
    public List<Aree> findByDescrizione(String descrizione, String codiceComune) {

	List<Responsabilicomuni> comuniList = comuniassociatiService.checkComuniAbilitatiPerResponsabile();
	String[] codiciComune = responsabiliComuniToCodici(comuniList);
	return areeDAO.findByDescrizione(descrizione, codiceComune, codiciComune);
    }

    @Override
    public void delete(Aree entity) {

	if (isDeleteAllowed(entity)) {
	    // con la cascade rimuove anche i lotti
	    areeDAO.delete(entity);
	    resetObjectCached();
	}
    }

    @Override
    public List<Aree> findAll(Integer firstResult, Integer maxResult) {

	List<Responsabilicomuni> comuniList = comuniassociatiService.checkComuniAbilitatiPerResponsabile();
	List<Aree> areeList = null;
	if (comuniList.isEmpty()) {
	    areeList = areeDAO.findAll(firstResult, maxResult);
	} else {
	    areeList = this.findByComuniAbilitati(comuniList);
	}
	return areeList;
    }

    private List<Aree> findByComuniAbilitati(List<Responsabilicomuni> comuniList) {

	if (comuniList == null || comuniList.size() == 0) {
	    return null;
	}
	String[] codiciComune = responsabiliComuniToCodici(comuniList);
	return areeDAO.findAllByCodiciComuni(codiciComune);
    }

    @Override
    public Aree findById(PkId id) {

	return areeDAO.findById(id);
    }

    @Override
    public void insert(Aree entity) {

	if (validateEntity(entity)) {
	    areeDAO.insert(entity);
	    resetObjectCached();
	}
    }

    @Override
    public void update(Aree entity) {

	if (validateEntity(entity)) {
	    areeDAO.update(entity);
	    resetObjectCached();
	}
    }

    public List<Aree> findByFilterTable(FilterTable filterTable) {

	return areeDAO.findByFilterTable(filterTable);
    }

    protected boolean isDeleteAllowed(Aree entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    private String[] responsabiliComuniToCodici(List<Responsabilicomuni> comuniList) {

	String[] codiciComune = new String[comuniList.size()];
	for (int i = 0; i < comuniList.size(); i++) {
	    codiciComune[i] = comuniList.get(i).getComune().getCodicecomune();
	}
	return codiciComune;
    }

    private Map<String, Boolean> isRecordPresentiMap = new HashMap<String, Boolean>();

    private String getMapKey(String idcomunealias, String software) {

	return idcomunealias + "-" + software;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	this.isRecordPresentiMap = new HashMap<String, Boolean>();
    }

    @Override
    public boolean existsRecords() {

	if (isRecordPresentiMap == null) {
	    isRecordPresentiMap = new HashMap<String, Boolean>();
	}
	String key = getMapKey(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware());
	if (isRecordPresentiMap.get(key) != null) {
	    return isRecordPresentiMap.get(key);
	} else {
	    FilterTable areeFt = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction areeSoftwareAndTT = new FilterRestriction();
	    areeSoftwareAndTT.addFilterField(FilterUtils.in("software.codice", new String[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT },
		    String.class));
	    areeFt.addRestriction(areeSoftwareAndTT);
	    boolean exists = areeDAO.existsRecords(areeFt);
	    isRecordPresentiMap.put(key, Boolean.valueOf(exists));
	    return exists;
	}
    }
}
