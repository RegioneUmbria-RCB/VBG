package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.Anagrafedyn2datiStoricoDAO;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2datiStoricoId;
import it.gruppoinit.pal.gp.core.service.Anagrafedyn2datiStoricoService;

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
public class Anagrafedyn2datiStoricoServiceImpl extends BaseServiceImpl<Anagrafedyn2datiStorico, Anagrafedyn2datiStoricoId> implements
	Anagrafedyn2datiStoricoService {

    private Anagrafedyn2datiStoricoDAO anagrafedyn2datistoricoDAO;

    @Autowired
    public void setAnagrafedyn2datiStoricoDAO(Anagrafedyn2datiStoricoDAO anagrafedyn2datistoricoDAO) {

	this.anagrafedyn2datistoricoDAO = anagrafedyn2datistoricoDAO;
    }

    @Override
    protected Class<Anagrafedyn2datiStorico> getEntityClass() {

	return Anagrafedyn2datiStorico.class;
    }

    @Override
    public List<Anagrafedyn2datiStorico> findAll(Integer firstResult, Integer maxResult) {

	return anagrafedyn2datistoricoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Anagrafedyn2datiStorico entity) {

	if (validateEntity(entity)) {
	    anagrafedyn2datistoricoDAO.insert(entity);
	}
    }

    @Override
    public Anagrafedyn2datiStorico findById(Anagrafedyn2datiStoricoId id) {

	return anagrafedyn2datistoricoDAO.findById(id);
    }

    @Override
    public void update(Anagrafedyn2datiStorico entity) {

	if (validateEntity(entity)) {
	    anagrafedyn2datistoricoDAO.update(entity);
	}
    }

    @Override
    public void delete(Anagrafedyn2datiStorico entity) {

	if (isDeleteAllowed(entity)) {
	    anagrafedyn2datistoricoDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Anagrafedyn2datiStorico entity) {

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
