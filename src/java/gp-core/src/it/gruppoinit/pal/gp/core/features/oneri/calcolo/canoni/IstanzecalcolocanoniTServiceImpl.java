package it.gruppoinit.pal.gp.core.features.oneri.calcolo.canoni;

import it.gruppoinit.pal.gp.core.dao.IstanzecalcolocanoniTDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniD;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniO;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class IstanzecalcolocanoniTServiceImpl extends BaseServiceImpl<IstanzecalcolocanoniT, PkId> implements IstanzecalcolocanoniTService {

    private IstanzecalcolocanoniDService istanzecalcolocanoniDService;
    private IstanzecalcolocanoniOService istanzecalcolocanoniOService;
    private IstanzecalcolocanoniTDAO istanzecalcolocanonitDAO;

    @Autowired
    public void setIstanzecalcolocanoniDService(IstanzecalcolocanoniDService istanzecalcolocanoniDService) {

	this.istanzecalcolocanoniDService = istanzecalcolocanoniDService;
    }

    @Autowired
    public void setIstanzecalcolocanoniOService(IstanzecalcolocanoniOService istanzecalcolocanoniOService) {

	this.istanzecalcolocanoniOService = istanzecalcolocanoniOService;
    }

    @Autowired
    public void setIstanzecalcolocanoniTDAO(IstanzecalcolocanoniTDAO istanzecalcolocanonitDAO) {

	this.istanzecalcolocanonitDAO = istanzecalcolocanonitDAO;
    }

    @Override
    protected Class<IstanzecalcolocanoniT> getEntityClass() {

	return IstanzecalcolocanoniT.class;
    }

    @Override
    public List<IstanzecalcolocanoniT> findAll(Integer firstResult, Integer maxResult) {

	return istanzecalcolocanonitDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IstanzecalcolocanoniT entity) {

	if (validateEntity(entity)) {
	    istanzecalcolocanonitDAO.insert(entity);
	}
    }

    @Override
    public IstanzecalcolocanoniT findById(PkId id) {

	return istanzecalcolocanonitDAO.findById(id);
    }

    @Override
    public void update(IstanzecalcolocanoniT entity) {

	if (validateEntity(entity)) {
	    istanzecalcolocanonitDAO.update(entity);
	}
    }

    @Override
    public void delete(IstanzecalcolocanoniT entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    istanzecalcolocanonitDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(IstanzecalcolocanoniT entity) {

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
    public List<IstanzecalcolocanoniT> findByIstanza(Istanze istanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", istanza.getId().getCodice(), "istanze", Integer.class));
	ft.addRestriction(fr);
	return istanzecalcolocanonitDAO.findByFilterTable(ft);
    }

    protected void childDelete(IstanzecalcolocanoniT entity) {

	// a. ISTANZECALCOLOCANONI_D
	Set<IstanzecalcolocanoniD> istanzecalcolocanoniDs = entity.getIstanzecalcolocanoniDs();
	for (IstanzecalcolocanoniD istanzecalcolocanoniD : istanzecalcolocanoniDs) {
	    istanzecalcolocanoniDService.delete(istanzecalcolocanoniD);
	}
	// b. ISTANZECALCOLOCANONI_O
	Set<IstanzecalcolocanoniO> istanzecalcolocanoniOs = entity.getIstanzecalcolocanoniOs();
	for (IstanzecalcolocanoniO istanzecalcolocanoniO : istanzecalcolocanoniOs) {
	    istanzecalcolocanoniOService.delete(istanzecalcolocanoniO);
	}
    }
}
