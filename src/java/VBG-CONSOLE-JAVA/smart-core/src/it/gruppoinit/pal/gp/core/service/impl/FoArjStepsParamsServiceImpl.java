package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoArjStepsParamsDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsParams;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FoArjStepsParamsService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class FoArjStepsParamsServiceImpl extends BaseServiceImpl<FoArjStepsParams, PkId> implements FoArjStepsParamsService {

    private FoArjStepsParamsDAO foarjstepsparamsDAO;

    @Autowired
    public void setFoArjStepsParamsDAO(FoArjStepsParamsDAO foarjstepsparamsDAO) {

	this.foarjstepsparamsDAO = foarjstepsparamsDAO;
    }

    @Override
    protected Class<FoArjStepsParams> getEntityClass() {

	return FoArjStepsParams.class;
    }

    @Override
    public List<FoArjStepsParams> findAll(Integer firstResult, Integer maxResult) {

	return foarjstepsparamsDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoArjStepsParams entity) {

	if (validateEntity(entity)) {
	    foarjstepsparamsDAO.insert(entity);
	}
    }

    @Override
    public FoArjStepsParams findById(PkId id) {

	return foarjstepsparamsDAO.findById(id);
    }

    @Override
    public void update(FoArjStepsParams entity) {

	if (validateEntity(entity)) {
	    foarjstepsparamsDAO.update(entity);
	}
    }

    @Override
    public void delete(FoArjStepsParams entity) {

	if (isDeleteAllowed(entity)) {
	    foarjstepsparamsDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(FoArjStepsParams entity) {

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

    @Override
    public List<FoArjStepsParams> findByIdStep(String idComuneStep, Integer codiceStep) {

	if (codiceStep == null) {
	    throw new RuntimeException("il parametro codice step è nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idComuneStep, "foArjSteps", String.class));
	fr.addFilterField(FilterUtils.equals("foArjStepsId", codiceStep, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("chiave", "foArjStepsParamsBase"));
	return foarjstepsparamsDAO.findByFilterTable(ft);
    }

    @Override
    public FoArjStepsParams findByIdStepAndNomeParametro(String idComuneStep, Integer codiceStep, String nomeParametro) {

	if (codiceStep == null || StringUtils.isEmpty(nomeParametro)) {
	    throw new RuntimeException("il parametro codicStep e nomeParametro sono obbligatori");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idComuneStep, "foArjSteps", String.class));
	fr.addFilterField(FilterUtils.equals("foArjStepsId", codiceStep, Integer.class));
	ft.addRestriction(fr);
	FilterRestriction frChiave = new FilterRestriction();
	frChiave.addFilterField(FilterUtils.equals("chiave", nomeParametro, "foArjStepsParamsBase", String.class));
	ft.addRestriction(frChiave);
	List<FoArjStepsParams> params = foarjstepsparamsDAO.findByFilterTable(ft);
	return params.isEmpty() ? null : params.get(0);
    }
}
