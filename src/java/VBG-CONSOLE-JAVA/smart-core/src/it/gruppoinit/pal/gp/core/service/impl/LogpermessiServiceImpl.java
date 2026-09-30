package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.LogpermessiDAO;
import it.gruppoinit.pal.gp.core.domain.Logpermessi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.LogpermessiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 */
@Service
public class LogpermessiServiceImpl extends BaseServiceImpl<Logpermessi, PkId> implements LogpermessiService {

    private LogpermessiDAO logpermessiDAO;

    @Autowired
    public void setLogpermessiDAO(LogpermessiDAO logpermessiDAO) {

	this.logpermessiDAO = logpermessiDAO;
    }

    @Override
    protected Class<Logpermessi> getEntityClass() {

	return Logpermessi.class;
    }

    @Override
    public List<Logpermessi> findAll(Integer firstResult, Integer maxResult) {

	return logpermessiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Logpermessi entity) {

	if (validateEntity(entity)) {
	    logpermessiDAO.insert(entity);
	}
    }

    @Override
    public Logpermessi findById(PkId id) {

	return logpermessiDAO.findById(id);
    }

    @Override
    public void update(Logpermessi entity) {

	if (validateEntity(entity)) {
	    logpermessiDAO.update(entity);
	}
    }

    @Override
    public void delete(Logpermessi entity) {

	if (isDeleteAllowed(entity)) {
	    logpermessiDAO.delete(entity);
	}
    }
}
