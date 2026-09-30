package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.CcTabellaClassiedificioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CcTabellaClassiedificio;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CcIcalcoliService;
import it.gruppoinit.pal.gp.core.service.CcTabellaClassiedificioService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class CcTabellaClassiedificioServiceImpl extends BaseServiceImpl<CcTabellaClassiedificio, PkId> implements CcTabellaClassiedificioService {

    private CcIcalcoliService ccIcalcoliService;
    private CcTabellaClassiedificioDAO cctabellaclassiedificioDAO;

    @Autowired
    public void setCcIcalcoliService(CcIcalcoliService ccIcalcoliService) {

	this.ccIcalcoliService = ccIcalcoliService;
    }

    @Autowired
    public void setCcTabellaClassiedificioDAO(CcTabellaClassiedificioDAO cctabellaclassiedificioDAO) {

	this.cctabellaclassiedificioDAO = cctabellaclassiedificioDAO;
    }

    @Override
    protected Class<CcTabellaClassiedificio> getEntityClass() {

	return CcTabellaClassiedificio.class;
    }

    @Override
    public List<CcTabellaClassiedificio> findAll(Integer firstResult, Integer maxResult) {

	return cctabellaclassiedificioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcTabellaClassiedificio entity) {

	if (validateEntity(entity) && isInsertAllowed(entity, true)) {
	    cctabellaclassiedificioDAO.insert(entity);
	}
    }

    @Override
    public CcTabellaClassiedificio findById(PkId id) {

	return cctabellaclassiedificioDAO.findById(id);
    }

    @Override
    public void update(CcTabellaClassiedificio entity) {

	if (validateEntity(entity) && isInsertAllowed(entity, false)) {
	    cctabellaclassiedificioDAO.update(entity);
	}
    }

    @Override
    public void delete(CcTabellaClassiedificio entity) {

	if (isDeleteAllowed(entity)) {
	    cctabellaclassiedificioDAO.delete(entity);
	}
    }

    @Override
    public CcTabellaClassiedificio findByEqualsDescrizione(String descrizione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("descrizione", descrizione, String.class));
	ft.addRestriction(fr);
	List<CcTabellaClassiedificio> list = cctabellaclassiedificioDAO.findByFilterTable(ft);
	if (list != null && !list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    protected boolean isInsertAllowed(CcTabellaClassiedificio entity, boolean isInsert) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	CcTabellaClassiedificio ccTabellaClassiedificio = this.findByEqualsDescrizione(entity.getDescrizione());
	if (isInsert) {
	    if (ccTabellaClassiedificio != null && EntityUtils.getNestedProperty(entity, "id.codice") == null) {
		_ivs.add(new InvalidValue("service_error.descrizione_classi_edificio_presente", null, null, null, null));
	    }
	} else {
	    if (ccTabellaClassiedificio != null && EntityUtils.getNestedProperty(entity, "id.codice") != null
		    && !entity.getId().getCodice().equals(ccTabellaClassiedificio.getId().getCodice())) {
		_ivs.add(new InvalidValue("service_error.descrizione_classi_edificio_presente", null, null, null, null));
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    protected boolean isDeleteAllowed(CcTabellaClassiedificio entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	
	int numeroCcIcalcoli = ccIcalcoliService.countByCcTabellaClassiedificio(entity);
	if (numeroCcIcalcoli > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "CC_ICALCOLI", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<CcTabellaClassiedificio> listByIntervallo() {

	return cctabellaclassiedificioDAO.listByIntervallo();
    }
}
