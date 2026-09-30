package it.gruppoinit.stc.service.impl;

import it.gruppoinit.stc.dao.VwMessaggipraticheDAO;
import it.gruppoinit.stc.domain.VwMessaggipratiche;
import it.gruppoinit.stc.service.VwMessaggipraticheService;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VwMessaggipraticheServiceImpl extends BaseServiceImpl<VwMessaggipratiche, Integer> implements VwMessaggipraticheService {

    private VwMessaggipraticheDAO vwMessaggipraticheDAO;

    @Autowired
    public void setVwMessaggipraticheDAO(VwMessaggipraticheDAO vwMessaggipraticheDAO) {

	this.vwMessaggipraticheDAO = vwMessaggipraticheDAO;
    }

    @Override
    public void insert(VwMessaggipratiche entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(VwMessaggipratiche entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(VwMessaggipratiche entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<VwMessaggipratiche> findAll(Integer firstResult, Integer maxResult) {

	return vwMessaggipraticheDAO.findAll(firstResult, maxResult);
    }

    @Override
    public VwMessaggipratiche findById(Integer id) {

	return vwMessaggipraticheDAO.findById(id);
    }

    @Override
    protected Class<VwMessaggipratiche> getEntityClass() {

	return VwMessaggipratiche.class;
    }

    @Override
    public Collection<VwMessaggipratiche> findByFilter(VwMessaggipratiche vwMessaggipratiche, Integer firstResult, Integer maxResult) {

	return vwMessaggipraticheDAO.findByFilter(vwMessaggipratiche, firstResult, maxResult);
    }

    @Override
    public int countByFilter(VwMessaggipratiche vwMessaggipratiche) {

	return vwMessaggipraticheDAO.countByFilter(vwMessaggipratiche);
    }
}
