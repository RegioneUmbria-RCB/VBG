package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentioneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentioneriService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventarioprocedimentioneriServiceImpl extends BaseServiceImpl<Inventarioprocedimentioneri, PkId> implements
	InventarioprocedimentioneriService {

    private InventarioprocedimentioneriDAO inventarioprocedimentioneriDAO;
    private Dyn2CampiService dyn2CampiService;

    @Autowired
    public void setInventarioprocedimentioneriDAO(InventarioprocedimentioneriDAO inventarioprocedimentioneriDAO) {

	this.inventarioprocedimentioneriDAO = inventarioprocedimentioneriDAO;
    }

    @Autowired
    public void setDyn2CampiService(Dyn2CampiService dyn2CampiService) {

	this.dyn2CampiService = dyn2CampiService;
    }

    @Override
    protected Class<Inventarioprocedimentioneri> getEntityClass() {

	return Inventarioprocedimentioneri.class;
    }

    @Override
    public void delete(Inventarioprocedimentioneri entity) {

	inventarioprocedimentioneriDAO.delete(entity);
    }

    @Override
    public List<Inventarioprocedimentioneri> findAll(Integer firstResult, Integer maxResult) {

	return inventarioprocedimentioneriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Inventarioprocedimentioneri findById(PkId id) {

	return inventarioprocedimentioneriDAO.findById(id);
    }

    @Override
    public void insert(Inventarioprocedimentioneri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    inventarioprocedimentioneriDAO.insert(entity);
	}
    }

    @Override
    public void update(Inventarioprocedimentioneri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    inventarioprocedimentioneriDAO.update(entity);
	}
    }

    @Override
    public List<Inventarioprocedimentioneri> findByCodiceInventarioAndCausale(Integer codiceInventario, Integer codiceCausale) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("inventarioprocedimentiId", codiceInventario, Integer.class));
	fr.addFilterField(FilterUtils.equals("tipicausalioneriId", codiceCausale, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("importo"));
	return inventarioprocedimentioneriDAO.findByFilterTable(ft);
    }

    @Override
    public List<Inventarioprocedimentioneri> findByCodiceInventario(Integer codiceInventario, boolean escludiCausaliDisabilitate) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("inventarioprocedimentiId", codiceInventario, Integer.class));
	ft.addRestriction(fr);
	if (escludiCausaliDisabilitate) {
	    FilterRestriction dis = new FilterRestriction();
	    dis.setAndOrRestriction(AndOrRestriction.OR);
	    dis.addFilterField(FilterUtils.isNull("coDisabilitato", "tipicausalioneri"));
	    dis.addFilterField(FilterUtils.equals("coDisabilitato", Boolean.FALSE, "tipicausalioneri", Boolean.class));
	    ft.addRestriction(dis);
	}
	ft.addOrder(FilterUtils.orderAsc("importo"));
	return inventarioprocedimentioneriDAO.findByFilterTable(ft);
    }

    private void dataIntegration(Inventarioprocedimentioneri entity) {

	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Inventarioprocedimentioneri entity) {

	Dyn2Campi dyn2campi = dyn2CampiService.bindDomainObject(entity.getDyn2Campi(), PkId.class, "id.codice");
	entity.setDyn2Campi(dyn2campi);
	entity.setDyn2Modellit(null);
    }
}
