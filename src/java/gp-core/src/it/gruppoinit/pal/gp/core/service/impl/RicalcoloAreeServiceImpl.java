package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.StradarioDAO;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAree;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeId;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.RicalcoloAreeDAO;
import it.gruppoinit.pal.gp.core.service.RicalcoloAreeService;

@Service
public class RicalcoloAreeServiceImpl extends BaseServiceImpl<RicalcoloAree, RicalcoloAreeId> implements RicalcoloAreeService{

    @Autowired
    private RicalcoloAreeDAO ricalcoloAreeDAO;
    
    /*@Autowired
    public void setRicalcoloAreeDAO(RicalcoloAreeDAO ricalcoloAreeDAO) {
    
    this.ricalcoloAreeDAO = ricalcoloAreeDAO;
    }*/
    
    @Override
    public void insert(RicalcoloAree entity) {

	// TODO Auto-generated method stub
	
    }

    @Override
    public void update(RicalcoloAree entity) {

	// TODO Auto-generated method stub
	
    }

    @Override
    public void delete(RicalcoloAree entity) {

	// TODO Auto-generated method stub
	
    }

    @Override
    public List<RicalcoloAree> findAll(Integer firstResult, Integer maxResult) {

	return ricalcoloAreeDAO.findAll(firstResult, maxResult);

    }

    @Override
    public RicalcoloAree findById(RicalcoloAreeId id) {
	return ricalcoloAreeDAO.findById(id);
    }

    @Override
    protected Class<RicalcoloAree> getEntityClass() {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<RicalcoloAree> getMonitorRicalcolaAree() {
	return ricalcoloAreeDAO.getMonitorRicalcolaAree();
    }
}
