package it.gruppoinit.stc.service.impl;

import it.gruppoinit.stc.dao.MessaggipraticheDAO;
import it.gruppoinit.stc.domain.Messaggipratiche;
import it.gruppoinit.stc.service.MessaggipraticheService;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessaggipraticheServiceImpl extends BaseServiceImpl<Messaggipratiche, Integer> implements MessaggipraticheService {

    @Autowired
    private MessaggipraticheDAO messaggipraticheDAO;

    @Override
    protected Class<Messaggipratiche> getEntityClass() {

	return Messaggipratiche.class;
    }

    @Override
    public void delete(Messaggipratiche entity) {

	messaggipraticheDAO.delete(entity);
    }

    @Override
    public List<Messaggipratiche> findAll(Integer firstResult, Integer maxResult) {

	return messaggipraticheDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Messaggipratiche findById(Integer id) {

	return messaggipraticheDAO.findById(id);
    }

    @Override
    public void insert(Messaggipratiche entity) {

	if (validate(entity)) {
	    messaggipraticheDAO.insert(entity);
	}
    }

    @Override
    public void update(Messaggipratiche entity) {

	if (validate(entity)) {
	    messaggipraticheDAO.update(entity);
	}
    }

    @Override
    public void deleteCollegamento() {

	throw new NotImplementedException("Non implementato");
	// TODO
	// il metodo deve:
    }
}
