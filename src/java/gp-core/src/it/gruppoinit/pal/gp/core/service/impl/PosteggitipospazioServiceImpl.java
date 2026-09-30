package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.PosteggitipospazioDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggitipospazio;
import it.gruppoinit.pal.gp.core.service.PosteggitipospazioService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PosteggitipospazioServiceImpl extends BaseServiceImpl<Posteggitipospazio, PkId> implements PosteggitipospazioService {

    private PosteggitipospazioDAO posteggitipospazioDAO;

    @Autowired
    public void setPosteggitipospazioDAO(PosteggitipospazioDAO posteggitipospazioDAO) {

	this.posteggitipospazioDAO = posteggitipospazioDAO;
    }

    @Override
    protected Class<Posteggitipospazio> getEntityClass() {

	return Posteggitipospazio.class;
    }

    @Override
    public void delete(Posteggitipospazio entity) {

	if (isDeleteAllowed(entity)) {
	    posteggitipospazioDAO.delete(entity);
	}
    }

    @Override
    public List<Posteggitipospazio> findAll(Integer firstResult, Integer maxResult) {

	return posteggitipospazioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Posteggitipospazio findById(PkId id) {

	return posteggitipospazioDAO.findById(id);
    }

    @Override
    public void insert(Posteggitipospazio entity) {

	if (validateEntity(entity)) {
	    posteggitipospazioDAO.insert(entity);
	}
    }

    @Override
    public void update(Posteggitipospazio entity) {

	if (validateEntity(entity)) {
	    posteggitipospazioDAO.update(entity);
	}
    }

    @Override
    public List<Posteggitipospazio> findByTipoSpazio(Posteggitipospazio posteggitipospazio) {

	return posteggitipospazioDAO.findByTipoSpazio(posteggitipospazio);
    }

    protected boolean isDeleteAllowed(Posteggitipospazio entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getMercatiDs().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATI_D", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
