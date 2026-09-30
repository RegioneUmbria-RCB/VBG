package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipiaffissioniDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiaffissioni;
import it.gruppoinit.pal.gp.core.service.TipiaffissioniService;

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
public class TipiaffissioniServiceImpl extends BaseServiceImpl<Tipiaffissioni, PkId> implements TipiaffissioniService {

    private TipiaffissioniDAO tipiaffissioniDAO;

    @Autowired
    public void setTipiaffissioniDAO(TipiaffissioniDAO tipiaffissioniDAO) {

	this.tipiaffissioniDAO = tipiaffissioniDAO;
    }

    @Override
    protected Class<Tipiaffissioni> getEntityClass() {

	return Tipiaffissioni.class;
    }

    @Override
    public List<Tipiaffissioni> findAll(Integer firstResult, Integer maxResult) {

	return tipiaffissioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Tipiaffissioni entity) {

	if (validateEntity(entity)) {
	    tipiaffissioniDAO.insert(entity);
	}
    }

    @Override
    public Tipiaffissioni findById(PkId id) {

	return tipiaffissioniDAO.findById(id);
    }

    @Override
    public void update(Tipiaffissioni entity) {

	if (validateEntity(entity)) {
	    tipiaffissioniDAO.update(entity);
	}
    }

    @Override
    public void delete(Tipiaffissioni entity) {

	if (isDeleteAllowed(entity)) {
	    tipiaffissioniDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Tipiaffissioni entity) {

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
