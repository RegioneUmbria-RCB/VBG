package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoArjStepsParamsBaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsParamsBase;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FoArjStepsParamsBaseService;

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
public class FoArjStepsParamsBaseServiceImpl extends BaseServiceImpl<FoArjStepsParamsBase, Integer> implements FoArjStepsParamsBaseService {

    private FoArjStepsParamsBaseDAO foarjstepsparamsbaseDAO;

    @Autowired
    public void setFoArjStepsParamsBaseDAO(FoArjStepsParamsBaseDAO foarjstepsparamsbaseDAO) {

	this.foarjstepsparamsbaseDAO = foarjstepsparamsbaseDAO;
    }

    @Override
    protected Class<FoArjStepsParamsBase> getEntityClass() {

	return FoArjStepsParamsBase.class;
    }

    @Override
    public List<FoArjStepsParamsBase> findAll(Integer firstResult, Integer maxResult) {

	return foarjstepsparamsbaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoArjStepsParamsBase entity) {

	if (validateEntity(entity)) {
	    foarjstepsparamsbaseDAO.insert(entity);
	}
    }

    @Override
    public FoArjStepsParamsBase findById(Integer id) {

	return foarjstepsparamsbaseDAO.findById(id);
    }

    @Override
    public void update(FoArjStepsParamsBase entity) {

	if (validateEntity(entity)) {
	    foarjstepsparamsbaseDAO.update(entity);
	}
    }

    @Override
    public void delete(FoArjStepsParamsBase entity) {

	if (isDeleteAllowed(entity)) {
	    foarjstepsparamsbaseDAO.delete(entity);
	}
    }

    @Override
    public List<FoArjStepsParamsBase> findByFoArjStepsBase(String idFoArjStepsBase) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("nomeStep", idFoArjStepsBase, "foArjStepsBase", String.class));
	ft.addRestriction(fr);
	return foarjstepsparamsbaseDAO.findByFilterTable(ft);
    }

    protected boolean isDeleteAllowed(FoArjStepsParamsBase entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
