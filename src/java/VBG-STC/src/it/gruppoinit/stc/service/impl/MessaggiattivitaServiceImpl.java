package it.gruppoinit.stc.service.impl;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.stc.dao.MessaggiattivitaDAO;
import it.gruppoinit.stc.domain.Messaggiattivita;
import it.gruppoinit.stc.service.MessaggiattivitaService;

@Service
public class MessaggiattivitaServiceImpl extends BaseServiceImpl<Messaggiattivita, Integer> implements MessaggiattivitaService {

    @Autowired
    private MessaggiattivitaDAO messaggiattivitaDAO;

    @Override
    protected Class<Messaggiattivita> getEntityClass() {

	return Messaggiattivita.class;
    }

    @Override
    public void delete(Messaggiattivita entity) {

	messaggiattivitaDAO.delete(entity);
    }

    @Override
    public List<Messaggiattivita> findAll(Integer firstResult, Integer maxResult) {

	return messaggiattivitaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Messaggiattivita findById(Integer id) {

	return messaggiattivitaDAO.findById(id);
    }

    @Override
    public void insert(Messaggiattivita entity) {

	if (validate(entity)) {
	    messaggiattivitaDAO.insert(entity);
	}
    }

    @Override
    public void update(Messaggiattivita entity) {

	if (validate(entity)) {
	    messaggiattivitaDAO.update(entity);
	}
    }

    @Override
    public void deleteCollegamento() {

	throw new NotImplementedException("Non implementato");
    }

    @Override
    public List<Messaggiattivita> findByIdAttivita(int idAttivita, TIPO_COLLEGAMENTO collegamento) {

	return messaggiattivitaDAO.findByIdAttivita(idAttivita, collegamento);
    }
}
