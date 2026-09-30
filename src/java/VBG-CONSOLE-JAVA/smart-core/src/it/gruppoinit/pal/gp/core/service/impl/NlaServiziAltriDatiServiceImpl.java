package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.NlaServiziAltriDatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.NlaServiziAltriDati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.NlaServiziAltriDatiService;

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
public class NlaServiziAltriDatiServiceImpl extends BaseServiceImpl<NlaServiziAltriDati, PkId> implements NlaServiziAltriDatiService {

    private NlaServiziAltriDatiDAO nlaservizialtridatiDAO;

    @Autowired
    public void setNlaServiziAltriDatiDAO(NlaServiziAltriDatiDAO nlaservizialtridatiDAO) {

	this.nlaservizialtridatiDAO = nlaservizialtridatiDAO;
    }

    @Override
    protected Class<NlaServiziAltriDati> getEntityClass() {

	return NlaServiziAltriDati.class;
    }

    @Override
    public List<NlaServiziAltriDati> findAll(Integer firstResult, Integer maxResult) {

	return nlaservizialtridatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(NlaServiziAltriDati entity) {

	if (validateEntity(entity)) {
	    nlaservizialtridatiDAO.insert(entity);
	}
    }

    @Override
    public NlaServiziAltriDati findById(PkId id) {

	return nlaservizialtridatiDAO.findById(id);
    }

    @Override
    public void update(NlaServiziAltriDati entity) {

	if (validateEntity(entity)) {
	    nlaservizialtridatiDAO.update(entity);
	}
    }

    @Override
    public void delete(NlaServiziAltriDati entity) {

	if (isDeleteAllowed(entity)) {
	    nlaservizialtridatiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(NlaServiziAltriDati entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<NlaServiziAltriDati> findByNlaServizi(Integer codiceservizio) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("nlaServiziId", codiceservizio, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("nomeparametro"));
	return nlaservizialtridatiDAO.findByFilterTable(ft);
    }
}
