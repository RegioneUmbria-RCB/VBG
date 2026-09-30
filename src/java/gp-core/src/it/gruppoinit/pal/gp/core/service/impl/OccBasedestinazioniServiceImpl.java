package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OccBasedestinazioniDAO;
import it.gruppoinit.pal.gp.core.domain.OccBasedestinazioni;
import it.gruppoinit.pal.gp.core.service.OccBasedestinazioniService;

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
public class OccBasedestinazioniServiceImpl extends BaseServiceImpl<OccBasedestinazioni, String> implements OccBasedestinazioniService {

    private OccBasedestinazioniDAO occbasedestinazioniDAO;

    @Autowired
    public void setOccBasedestinazioniDAO(OccBasedestinazioniDAO occbasedestinazioniDAO) {

	this.occbasedestinazioniDAO = occbasedestinazioniDAO;
    }

    @Override
    protected Class<OccBasedestinazioni> getEntityClass() {

	return OccBasedestinazioni.class;
    }

    @Override
    public List<OccBasedestinazioni> findAll(Integer firstResult, Integer maxResult) {

	return occbasedestinazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OccBasedestinazioni entity) {

	if (validateEntity(entity)) {
	    occbasedestinazioniDAO.insert(entity);
	}
    }

    @Override
    public OccBasedestinazioni findById(String id) {

	return occbasedestinazioniDAO.findById(id);
    }

    @Override
    public void update(OccBasedestinazioni entity) {

	if (validateEntity(entity)) {
	    occbasedestinazioniDAO.update(entity);
	}
    }

    @Override
    public void delete(OccBasedestinazioni entity) {

	if (isDeleteAllowed(entity)) {
	    occbasedestinazioniDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OccBasedestinazioni entity) {

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
