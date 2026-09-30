package it.gruppoinit.stc.service.impl;

import it.gruppoinit.stc.dao.PraticheDAO;
import it.gruppoinit.stc.domain.Pratiche;
import it.gruppoinit.stc.service.PraticheService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PraticheServiceImpl extends BaseServiceImpl<Pratiche, Integer> implements PraticheService {

    @Autowired
    private PraticheDAO praticheDAO;

    @Override
    protected Class<Pratiche> getEntityClass() {
	return Pratiche.class;
    }

    @Override
    public void delete(Pratiche entity) {
	praticheDAO.delete(entity);
    }

    @Override
    public List<Pratiche> findAll(Integer firstResult, Integer maxResult) {
	return praticheDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Pratiche findById(Integer id) {
	return praticheDAO.findById(id);
    }

    @Override
    public void insert(Pratiche entity) {
	if (validate(entity)) {
	    praticheDAO.insert(entity);
	}

    }

    @Override
    public void update(Pratiche entity) {
	if (validate(entity)) {
	    praticheDAO.update(entity);
	}
    }

    @Override
    public Pratiche findByUniqueKey(Pratiche example) {
	return praticheDAO.findByUniqueKey(example);
    }

}
