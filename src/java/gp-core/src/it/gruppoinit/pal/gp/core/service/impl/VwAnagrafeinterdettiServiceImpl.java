package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VwAnagrafeinterdettiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.VwAnagrafeinterdetti;
import it.gruppoinit.pal.gp.core.domain.VwAnagrafeinterdettiId;
import it.gruppoinit.pal.gp.core.service.VwAnagrafeinterdettiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class VwAnagrafeinterdettiServiceImpl extends BaseServiceImpl<VwAnagrafeinterdetti, VwAnagrafeinterdettiId> implements
	VwAnagrafeinterdettiService {

    private VwAnagrafeinterdettiDAO vwanagrafeinterdettiDAO;

    @Autowired
    public void setVwAnagrafeinterdettiDAO(VwAnagrafeinterdettiDAO vwanagrafeinterdettiDAO) {

	this.vwanagrafeinterdettiDAO = vwanagrafeinterdettiDAO;
    }

    @Override
    protected Class<VwAnagrafeinterdetti> getEntityClass() {

	return VwAnagrafeinterdetti.class;
    }

    @Override
    public List<VwAnagrafeinterdetti> findAll(Integer firstResult, Integer maxResult) {

	return vwanagrafeinterdettiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(VwAnagrafeinterdetti entity) {

	throw new NotImplementedException();
    }

    @Override
    public VwAnagrafeinterdetti findById(VwAnagrafeinterdettiId id) {

	return vwanagrafeinterdettiDAO.findById(id);
    }

    @Override
    public void update(VwAnagrafeinterdetti entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(VwAnagrafeinterdetti entity) {

	throw new NotImplementedException();
    }
    //    protected boolean isDeleteAllowed(VwAnagrafeinterdetti entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
}
