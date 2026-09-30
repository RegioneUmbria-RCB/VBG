package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.Anagrafedyn2modellitStoricoDAO;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellitStorico;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellitStoricoId;
import it.gruppoinit.pal.gp.core.service.Anagrafedyn2modellitStoricoService;

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
public class Anagrafedyn2modellitStoricoServiceImpl extends BaseServiceImpl<Anagrafedyn2modellitStorico, Anagrafedyn2modellitStoricoId> implements
	Anagrafedyn2modellitStoricoService {

    private Anagrafedyn2modellitStoricoDAO anagrafedyn2modellitstoricoDAO;

    @Autowired
    public void setAnagrafedyn2modellitStoricoDAO(Anagrafedyn2modellitStoricoDAO anagrafedyn2modellitstoricoDAO) {

	this.anagrafedyn2modellitstoricoDAO = anagrafedyn2modellitstoricoDAO;
    }

    @Override
    protected Class<Anagrafedyn2modellitStorico> getEntityClass() {

	return Anagrafedyn2modellitStorico.class;
    }

    @Override
    public List<Anagrafedyn2modellitStorico> findAll(Integer firstResult, Integer maxResult) {

	return anagrafedyn2modellitstoricoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Anagrafedyn2modellitStorico entity) {

	if (validateEntity(entity)) {
	    anagrafedyn2modellitstoricoDAO.insert(entity);
	}
    }

    @Override
    public Anagrafedyn2modellitStorico findById(Anagrafedyn2modellitStoricoId id) {

	return anagrafedyn2modellitstoricoDAO.findById(id);
    }

    @Override
    public void update(Anagrafedyn2modellitStorico entity) {

	if (validateEntity(entity)) {
	    anagrafedyn2modellitstoricoDAO.update(entity);
	}
    }

    @Override
    public void delete(Anagrafedyn2modellitStorico entity) {

	if (isDeleteAllowed(entity)) {
	    anagrafedyn2modellitstoricoDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Anagrafedyn2modellitStorico entity) {

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
