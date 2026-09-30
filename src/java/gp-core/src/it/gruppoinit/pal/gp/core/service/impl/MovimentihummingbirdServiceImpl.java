package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MovimentihummingbirdDAO;
import it.gruppoinit.pal.gp.core.domain.Movimentihummingbird;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.MovimentihummingbirdService;

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
public class MovimentihummingbirdServiceImpl extends BaseServiceImpl<Movimentihummingbird, PkId> implements MovimentihummingbirdService {

    private MovimentihummingbirdDAO movimentihummingbirdDAO;

    @Autowired
    public void setMovimentihummingbirdDAO(MovimentihummingbirdDAO movimentihummingbirdDAO) {

	this.movimentihummingbirdDAO = movimentihummingbirdDAO;
    }

    @Override
    protected Class<Movimentihummingbird> getEntityClass() {

	return Movimentihummingbird.class;
    }

    @Override
    public List<Movimentihummingbird> findAll(Integer firstResult, Integer maxResult) {

	return movimentihummingbirdDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Movimentihummingbird entity) {

	if (validateEntity(entity)) {
	    movimentihummingbirdDAO.insert(entity);
	}
    }

    @Override
    public Movimentihummingbird findById(PkId id) {

	return movimentihummingbirdDAO.findById(id);
    }

    @Override
    public void update(Movimentihummingbird entity) {

	if (validateEntity(entity)) {
	    movimentihummingbirdDAO.update(entity);
	}
    }

    @Override
    public void delete(Movimentihummingbird entity) {

	if (isDeleteAllowed(entity)) {
	    movimentihummingbirdDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Movimentihummingbird entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
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
