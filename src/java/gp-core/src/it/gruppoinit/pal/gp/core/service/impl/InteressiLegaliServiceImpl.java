package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.InteressiLegaliDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.InteressiLegali;
import it.gruppoinit.pal.gp.core.service.InteressiLegaliService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteressiLegaliServiceImpl extends BaseServiceImpl<InteressiLegali, Integer> implements InteressiLegaliService {

    private InteressiLegaliDAO interessiLegaliDAO;

    @Autowired
    public void setInteressiLegaliDAO(InteressiLegaliDAO interessiLegaliDAO) {

	this.interessiLegaliDAO = interessiLegaliDAO;
    }

    @Override
    protected Class<InteressiLegali> getEntityClass() {

	return InteressiLegali.class;
    }

    @Override
    public void delete(InteressiLegali entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<InteressiLegali> findAll(Integer firstResult, Integer maxResult) {

	return interessiLegaliDAO.findAll(null, null);
    }

    @Override
    public InteressiLegali findById(Integer id) {

	throw new NotImplementedException();
    }

    @Override
    public void insert(InteressiLegali entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(InteressiLegali entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<InteressiLegali> findByDataInizioFine(Date dataInizio, Date dataFine) {

	return interessiLegaliDAO.findByDataInizioFine(dataInizio, dataFine);
    }
}
