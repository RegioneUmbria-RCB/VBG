package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocLeggiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.InventarioprocLeggi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.InventarioprocLeggiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class InventarioprocLeggiServiceImpl extends BaseServiceImpl<InventarioprocLeggi, PkId> implements InventarioprocLeggiService {

    private InventarioprocLeggiDAO inventarioprocleggiDAO;

    @Autowired
    public void setInventarioprocLeggiDAO(InventarioprocLeggiDAO inventarioprocleggiDAO) {

	this.inventarioprocleggiDAO = inventarioprocleggiDAO;
    }

    @Override
    protected Class<InventarioprocLeggi> getEntityClass() {

	return InventarioprocLeggi.class;
    }

    @Override
    public List<InventarioprocLeggi> findAll(Integer firstResult, Integer maxResult) {

	return inventarioprocleggiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(InventarioprocLeggi entity) {

	if (validateEntity(entity)) {
	    inventarioprocleggiDAO.insert(entity);
	}
    }

    @Override
    public InventarioprocLeggi findById(PkId id) {

	return inventarioprocleggiDAO.findById(id);
    }

    @Override
    public void update(InventarioprocLeggi entity) {

	if (validateEntity(entity)) {
	    inventarioprocleggiDAO.update(entity);
	}
    }

    @Override
    public void delete(InventarioprocLeggi entity) {

	if (isDeleteAllowed(entity)) {
	    inventarioprocleggiDAO.delete(entity);
	}
    }

    @Override
    public List<InventarioprocLeggi> findByCodiceinventario(Integer codiceInventario) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("inventarioprocedimentiId", codiceInventario, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("leDescrizione", "leggi"));
	return inventarioprocleggiDAO.findByFilterTable(ft);
    }
}
