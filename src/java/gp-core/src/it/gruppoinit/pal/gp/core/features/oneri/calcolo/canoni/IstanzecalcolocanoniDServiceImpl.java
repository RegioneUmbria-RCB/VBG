package it.gruppoinit.pal.gp.core.features.oneri.calcolo.canoni;

import it.gruppoinit.pal.gp.core.dao.IstanzecalcolocanoniDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniD;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

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
public class IstanzecalcolocanoniDServiceImpl extends BaseServiceImpl<IstanzecalcolocanoniD, PkId> implements IstanzecalcolocanoniDService {

    private IstanzecalcolocanoniDDAO istanzecalcolocanonidDAO;

    @Autowired
    public void setIstanzecalcolocanoniDDAO(IstanzecalcolocanoniDDAO istanzecalcolocanonidDAO) {

	this.istanzecalcolocanonidDAO = istanzecalcolocanonidDAO;
    }

    @Override
    protected Class<IstanzecalcolocanoniD> getEntityClass() {

	return IstanzecalcolocanoniD.class;
    }

    @Override
    public List<IstanzecalcolocanoniD> findAll(Integer firstResult, Integer maxResult) {

	return istanzecalcolocanonidDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IstanzecalcolocanoniD entity) {

	if (validateEntity(entity)) {
	    istanzecalcolocanonidDAO.insert(entity);
	}
    }

    @Override
    public IstanzecalcolocanoniD findById(PkId id) {

	return istanzecalcolocanonidDAO.findById(id);
    }

    @Override
    public void update(IstanzecalcolocanoniD entity) {

	if (validateEntity(entity)) {
	    istanzecalcolocanonidDAO.update(entity);
	}
    }

    @Override
    public void delete(IstanzecalcolocanoniD entity) {

	if (isDeleteAllowed(entity)) {
	    istanzecalcolocanonidDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(IstanzecalcolocanoniD entity) {

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
}
