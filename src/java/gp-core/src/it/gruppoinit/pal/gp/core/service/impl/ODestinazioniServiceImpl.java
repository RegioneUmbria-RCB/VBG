package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ODestinazioniDAO;
import it.gruppoinit.pal.gp.core.domain.ODestinazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.ODestinazioniService;

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
public class ODestinazioniServiceImpl extends BaseServiceImpl<ODestinazioni, PkId> implements ODestinazioniService {

    private ODestinazioniDAO odestinazioniDAO;

    @Autowired
    public void setODestinazioniDAO(ODestinazioniDAO odestinazioniDAO) {

	this.odestinazioniDAO = odestinazioniDAO;
    }

    @Override
    protected Class<ODestinazioni> getEntityClass() {

	return ODestinazioni.class;
    }

    @Override
    public List<ODestinazioni> findAll(Integer firstResult, Integer maxResult) {

	return odestinazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ODestinazioni entity) {

	if (validateEntity(entity)) {
	    odestinazioniDAO.insert(entity);
	}
    }

    @Override
    public ODestinazioni findById(PkId id) {

	return odestinazioniDAO.findById(id);
    }

    @Override
    public void update(ODestinazioni entity) {

	if (validateEntity(entity)) {
	    odestinazioniDAO.update(entity);
	}
    }

    @Override
    public void delete(ODestinazioni entity) {

	if (isDeleteAllowed(entity)) {
	    odestinazioniDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(ODestinazioni entity) {

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
