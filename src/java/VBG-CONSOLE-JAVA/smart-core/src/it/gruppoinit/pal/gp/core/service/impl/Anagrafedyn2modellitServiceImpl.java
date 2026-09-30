package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.Anagrafedyn2modellitDAO;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellitId;
import it.gruppoinit.pal.gp.core.service.Anagrafedyn2modellitService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class Anagrafedyn2modellitServiceImpl extends BaseServiceImpl<Anagrafedyn2modellit, Anagrafedyn2modellitId> implements Anagrafedyn2modellitService {

    private Anagrafedyn2modellitDAO anagrafedyn2modellitDAO;

    @Autowired
    public void setAnagrafedyn2modellitDAO(Anagrafedyn2modellitDAO anagrafedyn2modellitDAO) {

	this.anagrafedyn2modellitDAO = anagrafedyn2modellitDAO;
    }

    @Override
    protected Class<Anagrafedyn2modellit> getEntityClass() {

	return Anagrafedyn2modellit.class;
    }

    @Override
    public List<Anagrafedyn2modellit> findAll(Integer firstResult, Integer maxResult) {

	return anagrafedyn2modellitDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Anagrafedyn2modellit entity) {

	if (validateEntity(entity)) {
	    anagrafedyn2modellitDAO.insert(entity);
	}
    }

    @Override
    public Anagrafedyn2modellit findById(Anagrafedyn2modellitId id) {

	return anagrafedyn2modellitDAO.findById(id);
    }

    @Override
    public void update(Anagrafedyn2modellit entity) {

	if (validateEntity(entity)) {
	    anagrafedyn2modellitDAO.update(entity);
	}
    }

    @Override
    public void delete(Anagrafedyn2modellit entity) {

	if (isDeleteAllowed(entity)) {
	    anagrafedyn2modellitDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Anagrafedyn2modellit entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
