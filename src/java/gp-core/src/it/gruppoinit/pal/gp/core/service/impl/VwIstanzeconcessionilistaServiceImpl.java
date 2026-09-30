package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VwIstanzeconcessionilistaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwIstanzeconcessionilista;
import it.gruppoinit.pal.gp.core.service.VwIstanzeconcessionilistaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class VwIstanzeconcessionilistaServiceImpl extends BaseServiceImpl<VwIstanzeconcessionilista, PkId> implements
	VwIstanzeconcessionilistaService {

    private VwIstanzeconcessionilistaDAO vwistanzeconcessionilistaDAO;

    @Autowired
    public void setVwIstanzeconcessionilistaDAO(VwIstanzeconcessionilistaDAO vwistanzeconcessionilistaDAO) {

	this.vwistanzeconcessionilistaDAO = vwistanzeconcessionilistaDAO;
    }

    @Override
    protected Class<VwIstanzeconcessionilista> getEntityClass() {

	return VwIstanzeconcessionilista.class;
    }

    @Override
    public List<VwIstanzeconcessionilista> findAll(Integer firstResult, Integer maxResult) {

	return vwistanzeconcessionilistaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(VwIstanzeconcessionilista entity) {

	throw new NotImplementedException();
    }

    @Override
    public VwIstanzeconcessionilista findById(PkId id) {

	return vwistanzeconcessionilistaDAO.findById(id);
    }

    @Override
    public void update(VwIstanzeconcessionilista entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(VwIstanzeconcessionilista entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<VwIstanzeconcessionilista> findByPosteggioAndUso(MercatiD mercatid, MercatiUso mercatiUso) {

	return vwistanzeconcessionilistaDAO.findByPosteggioAndUso(mercatid, mercatiUso);
    }
}
