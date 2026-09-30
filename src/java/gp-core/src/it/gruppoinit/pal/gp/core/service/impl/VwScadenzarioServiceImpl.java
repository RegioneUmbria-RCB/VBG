package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VwScadenzarioDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwScadenzario;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.VwScadenzarioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class VwScadenzarioServiceImpl extends BaseServiceImpl<VwScadenzario, PkId> implements VwScadenzarioService {

    private VwScadenzarioDAO vwscadenzarioDAO;

    @Autowired
    public void setVwScadenzarioDAO(VwScadenzarioDAO vwscadenzarioDAO) {

	this.vwscadenzarioDAO = vwscadenzarioDAO;
    }

    @Override
    protected Class<VwScadenzario> getEntityClass() {

	return VwScadenzario.class;
    }

    @Override
    public List<VwScadenzario> findByFilterTable(FilterTable ft) {

	return vwscadenzarioDAO.findByFilterTable(ft);
    }

    @Override
    public List<VwScadenzario> findAll(Integer firstResult, Integer maxResult) {

	throw new RuntimeException("Not implemented");
    }

    @Override
    public void insert(VwScadenzario entity) {

	throw new RuntimeException("Not implemented");
    }

    @Override
    public VwScadenzario findById(PkId id) {

	return vwscadenzarioDAO.findById(id);
    }

    @Override
    public void update(VwScadenzario entity) {

	throw new RuntimeException("Not implemented");
    }

    @Override
    public void delete(VwScadenzario entity) {

	throw new RuntimeException("Not implemented");
    }
}
