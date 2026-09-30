package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.VwPosteggiconcessionipresenzeDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessionipresenze;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessionipresenzeId;
import it.gruppoinit.pal.gp.core.service.VwPosteggiconcessionipresenzeService;

@Service
public class VwPosteggiconcessionipresenzeServiceImpl extends BaseServiceImpl<VwPosteggiconcessionipresenze, VwPosteggiconcessionipresenzeId>
	implements VwPosteggiconcessionipresenzeService {

    @Autowired
    private VwPosteggiconcessionipresenzeDAO vwPosteggiconcessionipresenzeDAO;

    @Override
    protected Class<VwPosteggiconcessionipresenze> getEntityClass() {

	return VwPosteggiconcessionipresenze.class;
    }

    @Override
    public void delete(VwPosteggiconcessionipresenze entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<VwPosteggiconcessionipresenze> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public VwPosteggiconcessionipresenze findById(VwPosteggiconcessionipresenzeId id) {

	return vwPosteggiconcessionipresenzeDAO.findById(id);
    }

    @Override
    public void insert(VwPosteggiconcessionipresenze entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(VwPosteggiconcessionipresenze entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<VwPosteggiconcessionipresenze> findPosteggiConcessioniPresenze(MercatipresenzeT giorno) {

	return vwPosteggiconcessionipresenzeDAO.findPosteggiConcessioniPresenze(giorno);
    }
}
