package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzehummingbirdDAO;
import it.gruppoinit.pal.gp.core.domain.Istanzehummingbird;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.IstanzehummingbirdService;

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
public class IstanzehummingbirdServiceImpl extends BaseServiceImpl<Istanzehummingbird, PkId> implements IstanzehummingbirdService {

    private IstanzehummingbirdDAO istanzehummingbirdDAO;

    @Autowired
    public void setIstanzehummingbirdDAO(IstanzehummingbirdDAO istanzehummingbirdDAO) {

	this.istanzehummingbirdDAO = istanzehummingbirdDAO;
    }

    @Override
    protected Class<Istanzehummingbird> getEntityClass() {

	return Istanzehummingbird.class;
    }

    @Override
    public List<Istanzehummingbird> findAll(Integer firstResult, Integer maxResult) {

	return istanzehummingbirdDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzehummingbird entity) {

	if (validateEntity(entity)) {
	    istanzehummingbirdDAO.insert(entity);
	}
    }

    @Override
    public Istanzehummingbird findById(PkId id) {

	return istanzehummingbirdDAO.findById(id);
    }

    @Override
    public void update(Istanzehummingbird entity) {

	if (validateEntity(entity)) {
	    istanzehummingbirdDAO.update(entity);
	}
    }

    @Override
    public void delete(Istanzehummingbird entity) {

	if (isDeleteAllowed(entity)) {
	    istanzehummingbirdDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Istanzehummingbird entity) {

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

    @Override
    public List<Movimentiallegati> insertAllegatiDocArea(Movimenti movimento) {

	// TODO
	return new ArrayList<Movimentiallegati>();
    }
}
