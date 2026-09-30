package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OnericomportamentoDAO;
import it.gruppoinit.pal.gp.core.domain.Onericomportamento;
import it.gruppoinit.pal.gp.core.service.OnericomportamentoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class OnericomportamentoServiceImpl extends BaseServiceImpl<Onericomportamento, Integer> implements OnericomportamentoService {

    private OnericomportamentoDAO onericomportamentoDAO;

    @Autowired
    public void setOnericomportamentoDAO(OnericomportamentoDAO onericomportamentoDAO) {

	this.onericomportamentoDAO = onericomportamentoDAO;
    }

    @Override
    protected Class<Onericomportamento> getEntityClass() {

	return Onericomportamento.class;
    }

    @Override
    public List<Onericomportamento> findAll(Integer firstResult, Integer maxResult) {

	return onericomportamentoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Onericomportamento entity) {

	if (validateEntity(entity)) {
	    onericomportamentoDAO.insert(entity);
	}
    }

    @Override
    public Onericomportamento findById(Integer id) {

	return onericomportamentoDAO.findById(id);
    }

    @Override
    public void update(Onericomportamento entity) {

	if (validateEntity(entity)) {
	    onericomportamentoDAO.update(entity);
	}
    }

    @Override
    public void delete(Onericomportamento entity) {

	if (isDeleteAllowed(entity)) {
	    onericomportamentoDAO.delete(entity);
	}
    }

    @Override
    public List<Onericomportamento> findByDescrizione(String descrizione) {

	return onericomportamentoDAO.findByDescrizione(descrizione);
    }
}
