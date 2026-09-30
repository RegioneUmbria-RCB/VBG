package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OBasetipionereDAO;
import it.gruppoinit.pal.gp.core.domain.OBasetipionere;
import it.gruppoinit.pal.gp.core.service.OBasetipionereService;

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
public class OBasetipionereServiceImpl extends BaseServiceImpl<OBasetipionere, String> implements OBasetipionereService {

    private OBasetipionereDAO obasetipionereDAO;

    @Autowired
    public void setOBasetipionereDAO(OBasetipionereDAO obasetipionereDAO) {

	this.obasetipionereDAO = obasetipionereDAO;
    }

    @Override
    protected Class<OBasetipionere> getEntityClass() {

	return OBasetipionere.class;
    }

    @Override
    public List<OBasetipionere> findAll(Integer firstResult, Integer maxResult) {

	return obasetipionereDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OBasetipionere entity) {

	if (validateEntity(entity)) {
	    obasetipionereDAO.insert(entity);
	}
    }

    @Override
    public OBasetipionere findById(String id) {

	return obasetipionereDAO.findById(id);
    }

    @Override
    public void update(OBasetipionere entity) {

	if (validateEntity(entity)) {
	    obasetipionereDAO.update(entity);
	}
    }

    @Override
    public void delete(OBasetipionere entity) {

	if (isDeleteAllowed(entity)) {
	    obasetipionereDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OBasetipionere entity) {

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
