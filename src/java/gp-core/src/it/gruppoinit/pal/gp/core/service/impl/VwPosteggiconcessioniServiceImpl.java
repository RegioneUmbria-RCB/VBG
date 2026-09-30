package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VwPosteggiconcessioniDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessioni;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessioniId;
import it.gruppoinit.pal.gp.core.service.VwPosteggiconcessioniService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VwPosteggiconcessioniServiceImpl extends BaseServiceImpl<VwPosteggiconcessioni, VwPosteggiconcessioniId>
	implements VwPosteggiconcessioniService {

    private VwPosteggiconcessioniDAO vwPosteggiconcessioniDAO;

    @Autowired
    public void setVwPosteggiconcessioniDAO(VwPosteggiconcessioniDAO vwPosteggiconcessioniDAO) {

	this.vwPosteggiconcessioniDAO = vwPosteggiconcessioniDAO;
    }

    @Override
    protected Class<VwPosteggiconcessioni> getEntityClass() {

	return VwPosteggiconcessioni.class;
    }

    @Override
    public List<VwPosteggiconcessioni> findPosteggiMercatoUso(Integer codiceMercato, Integer idUso, Integer firstResult, Integer maxResults) {

	return vwPosteggiconcessioniDAO.findPosteggiMercatoUso(codiceMercato, idUso, firstResult, maxResults);
    }

    @Override
    public int countPosteggiMercatoUso(Integer codiceMercato, Integer idUso) {

	return vwPosteggiconcessioniDAO.countPosteggiMercatoUso(codiceMercato, idUso);
    }

    @Override
    public void delete(VwPosteggiconcessioni entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<VwPosteggiconcessioni> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public VwPosteggiconcessioni findById(VwPosteggiconcessioniId id) {

	return vwPosteggiconcessioniDAO.findById(id);
    }

    @Override
    public void insert(VwPosteggiconcessioni entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(VwPosteggiconcessioni entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<VwPosteggiconcessioni> findByMercatoUsoAndPosteggio(Integer codiceMercato, Integer idUso, Integer idPosteggio, Integer firstResult,
	    Integer maxResults) {

	return vwPosteggiconcessioniDAO.findByMercatoUsoAndPosteggio(codiceMercato, idUso, idPosteggio, firstResult, maxResults);
    }
}
