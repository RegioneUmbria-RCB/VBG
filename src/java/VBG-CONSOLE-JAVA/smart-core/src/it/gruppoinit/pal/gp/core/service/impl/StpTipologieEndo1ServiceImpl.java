package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.StpTipologieEndo1DAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo1;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.service.StpTipologieEndo1Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StpTipologieEndo1ServiceImpl extends BaseServiceImpl<StpTipologieEndo1, PkId> implements StpTipologieEndo1Service {

    private StpTipologieEndo1DAO stpTipologieEndo1DAO;

    @Autowired
    public void setStpTipologieEndo1DAO(StpTipologieEndo1DAO stpTipologieEndo1DAO) {

	this.stpTipologieEndo1DAO = stpTipologieEndo1DAO;
    }

    @Override
    protected Class<StpTipologieEndo1> getEntityClass() {

	return StpTipologieEndo1.class;
    }

    @Override
    public void delete(StpTipologieEndo1 entity) {

	stpTipologieEndo1DAO.delete(entity);
    }

    @Override
    public List<StpTipologieEndo1> findAll(Integer firstResult, Integer maxResult) {

	return stpTipologieEndo1DAO.findAll(firstResult, maxResult);
    }

    @Override
    public StpTipologieEndo1 findById(PkId id) {

	return stpTipologieEndo1DAO.findById(id);
    }

    @Override
    public void insert(StpTipologieEndo1 entity) {

	if (validateEntity(entity)) {
	    stpTipologieEndo1DAO.insert(entity);
	}
    }

    @Override
    public void update(StpTipologieEndo1 entity) {

	if (validateEntity(entity)) {
	    stpTipologieEndo1DAO.update(entity);
	}
    }

    @Override
    public StpTipologieEndo1 findbyStpCodice(String idcomune, Integer stpCodice) {

	return stpTipologieEndo1DAO.findbyStpCodice(idcomune, stpCodice);
    }

    @Override
    public StpTipologieEndo1 findbyTipifamiglieendo(Tipifamiglieendo tipifamiglieendo) {

	return stpTipologieEndo1DAO.findbyTipifamiglieendo(tipifamiglieendo);
    }
}
