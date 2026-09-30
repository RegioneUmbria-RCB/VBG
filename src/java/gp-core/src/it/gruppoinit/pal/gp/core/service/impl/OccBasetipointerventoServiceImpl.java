package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OccBasetipointerventoDAO;
import it.gruppoinit.pal.gp.core.domain.OccBasetipointervento;
import it.gruppoinit.pal.gp.core.service.OccBasetipointerventoService;

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
public class OccBasetipointerventoServiceImpl extends BaseServiceImpl<OccBasetipointervento, String> implements OccBasetipointerventoService {

    private OccBasetipointerventoDAO occbasetipointerventoDAO;

    @Autowired
    public void setOccBasetipointerventoDAO(OccBasetipointerventoDAO occbasetipointerventoDAO) {

	this.occbasetipointerventoDAO = occbasetipointerventoDAO;
    }

    @Override
    protected Class<OccBasetipointervento> getEntityClass() {

	return OccBasetipointervento.class;
    }

    @Override
    public List<OccBasetipointervento> findAll(Integer firstResult, Integer maxResult) {

	return occbasetipointerventoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OccBasetipointervento entity) {

	if (validateEntity(entity)) {
	    occbasetipointerventoDAO.insert(entity);
	}
    }

    @Override
    public OccBasetipointervento findById(String id) {

	return occbasetipointerventoDAO.findById(id);
    }

    @Override
    public void update(OccBasetipointervento entity) {

	if (validateEntity(entity)) {
	    occbasetipointerventoDAO.update(entity);
	}
    }

    @Override
    public void delete(OccBasetipointervento entity) {

	if (isDeleteAllowed(entity)) {
	    occbasetipointerventoDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OccBasetipointervento entity) {

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
