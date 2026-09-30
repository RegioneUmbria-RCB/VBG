package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.RaggruppamentocausalioneriDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;
import it.gruppoinit.pal.gp.core.service.RaggruppamentocausalioneriService;

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
public class RaggruppamentocausalioneriServiceImpl extends BaseServiceImpl<Raggruppamentocausalioneri, PkId> implements
	RaggruppamentocausalioneriService {

    private RaggruppamentocausalioneriDAO raggruppamentocausalioneriDAO;

    @Autowired
    public void setRaggruppamentocausalioneriDAO(RaggruppamentocausalioneriDAO raggruppamentocausalioneriDAO) {

	this.raggruppamentocausalioneriDAO = raggruppamentocausalioneriDAO;
    }

    @Override
    protected Class<Raggruppamentocausalioneri> getEntityClass() {

	return Raggruppamentocausalioneri.class;
    }

    @Override
    public List<Raggruppamentocausalioneri> findAll(Integer firstResult, Integer maxResult) {

	return raggruppamentocausalioneriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Raggruppamentocausalioneri entity) {

	if (validateEntity(entity)) {
	    raggruppamentocausalioneriDAO.insert(entity);
	}
    }

    @Override
    public Raggruppamentocausalioneri findById(PkId id) {

	return raggruppamentocausalioneriDAO.findById(id);
    }

    @Override
    public void update(Raggruppamentocausalioneri entity) {

	if (validateEntity(entity)) {
	    raggruppamentocausalioneriDAO.update(entity);
	}
    }

    @Override
    public void delete(Raggruppamentocausalioneri entity) {

	if (isDeleteAllowed(entity)) {
	    raggruppamentocausalioneriDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Raggruppamentocausalioneri entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	
	if (entity.getTipicausalioneris().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPICAUSALIONERI", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
