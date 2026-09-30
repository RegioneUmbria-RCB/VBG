package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.service.StradarioARJService;
import it.gruppoinit.pal.gp.core.dao.StradarioDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StradarioARJServiceImpl extends BaseServiceImpl<Stradario, PkId> implements StradarioARJService {

    private StradarioDAO stradarioDAO;

    @Autowired
    public void setStradarioDAO(StradarioDAO stradarioDAO) {

	this.stradarioDAO = stradarioDAO;
    }

    @Override
    public void insert(Stradario entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void update(Stradario entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void delete(Stradario entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<Stradario> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Stradario findById(PkId id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected Class<Stradario> getEntityClass() {

	return Stradario.class;
    }

    @Override
    public List<Stradario> findByDescrizione(String descrizione, String codiceComune, Integer firstResult, Integer maxResult) {

	List<Stradario> list = stradarioDAO.findByDescrizione(descrizione, codiceComune, null, firstResult, maxResult, false);
	return list;
    }
}
