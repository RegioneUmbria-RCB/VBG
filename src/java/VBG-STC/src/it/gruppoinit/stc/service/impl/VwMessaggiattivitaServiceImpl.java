package it.gruppoinit.stc.service.impl;

import it.gruppoinit.stc.dao.VwMessaggiattivitaDAO;
import it.gruppoinit.stc.domain.VwMessaggiattivita;
import it.gruppoinit.stc.service.VwMessaggiattivitaService;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VwMessaggiattivitaServiceImpl extends BaseServiceImpl<VwMessaggiattivita, Integer> implements VwMessaggiattivitaService {

    private VwMessaggiattivitaDAO vwMessaggiattivitaDAO;

    @Autowired
    public void setVwMessaggiattivitaDAO(VwMessaggiattivitaDAO vwMessaggiattivitaDAO) {

	this.vwMessaggiattivitaDAO = vwMessaggiattivitaDAO;
    }

    @Override
    public void insert(VwMessaggiattivita entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(VwMessaggiattivita entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(VwMessaggiattivita entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<VwMessaggiattivita> findAll(Integer firstResult, Integer maxResult) {

	return vwMessaggiattivitaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public VwMessaggiattivita findById(Integer id) {

	return vwMessaggiattivitaDAO.findById(id);
    }

    @Override
    protected Class<VwMessaggiattivita> getEntityClass() {

	return VwMessaggiattivita.class;
    }

    @Override
    public Collection<VwMessaggiattivita> findByFilter(VwMessaggiattivita vwMessaggiattivita, Integer firstResult, Integer maxResult) {

	return vwMessaggiattivitaDAO.findByFilter(vwMessaggiattivita, firstResult, maxResult);
    }

    @Override
    public int countByFilter(VwMessaggiattivita vwMessaggiattivita) {

	return vwMessaggiattivitaDAO.countByFilter(vwMessaggiattivita);
    }
}
