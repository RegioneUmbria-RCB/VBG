package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CommedilizieVotiBaseDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.CommedilizieVotiBase;
import it.gruppoinit.pal.gp.core.service.CommedilizieVotiBaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class CommedilizieVotiBaseServiceImpl extends BaseServiceImpl<CommedilizieVotiBase, Integer> implements CommedilizieVotiBaseService {

    private CommedilizieVotiBaseDAO commedilizievotibaseDAO;

    @Autowired
    public void setCommedilizieVotiBaseDAO(CommedilizieVotiBaseDAO commedilizievotibaseDAO) {

	this.commedilizievotibaseDAO = commedilizievotibaseDAO;
    }

    @Override
    protected Class<CommedilizieVotiBase> getEntityClass() {

	return CommedilizieVotiBase.class;
    }

    @Override
    public List<CommedilizieVotiBase> findAll(Integer firstResult, Integer maxResult) {

	return commedilizievotibaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CommedilizieVotiBase entity) {

	throw new NotImplementedException();
    }

    @Override
    public CommedilizieVotiBase findById(Integer id) {

	return commedilizievotibaseDAO.findById(id);
    }

    @Override
    public void update(CommedilizieVotiBase entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(CommedilizieVotiBase entity) {

	throw new NotImplementedException();
    }
    //    protected boolean isDeleteAllowed(CommedilizieVotiBase entity) {
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
