package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ScadenzecategoriebaseDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Scadenzecategoriebase;
import it.gruppoinit.pal.gp.core.service.ScadenzecategoriebaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class ScadenzecategoriebaseServiceImpl extends BaseServiceImpl<Scadenzecategoriebase, PkId> implements ScadenzecategoriebaseService {

    private ScadenzecategoriebaseDAO scadenzecategoriebaseDAO;

    @Autowired
    public void setScadenzecategoriebaseDAO(ScadenzecategoriebaseDAO scadenzecategoriebaseDAO) {

	this.scadenzecategoriebaseDAO = scadenzecategoriebaseDAO;
    }

    @Override
    protected Class<Scadenzecategoriebase> getEntityClass() {

	return Scadenzecategoriebase.class;
    }

    @Override
    public List<Scadenzecategoriebase> findAll(Integer firstResult, Integer maxResult) {

	return scadenzecategoriebaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Scadenzecategoriebase entity) {

	throw new NotImplementedException();
    }

    @Override
    public Scadenzecategoriebase findById(PkId id) {

	return scadenzecategoriebaseDAO.findById(id);
    }

    @Override
    public void update(Scadenzecategoriebase entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(Scadenzecategoriebase entity) {

	throw new NotImplementedException();
    }
    //    protected boolean isDeleteAllowed(Scadenzecategoriebase entity) {
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
