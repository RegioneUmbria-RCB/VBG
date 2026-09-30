package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentisoftwareDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentisoftwareService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class InventarioprocedimentisoftwareServiceImpl extends BaseServiceImpl<Inventarioprocedimentisoftware, PkId> implements
	InventarioprocedimentisoftwareService {

    private InventarioprocedimentisoftwareDAO inventarioprocedimentisoftwareDAO;

    @Autowired
    public void setInventarioprocedimentisoftwareDAO(InventarioprocedimentisoftwareDAO inventarioprocedimentisoftwareDAO) {

	this.inventarioprocedimentisoftwareDAO = inventarioprocedimentisoftwareDAO;
    }

    @Override
    protected Class<Inventarioprocedimentisoftware> getEntityClass() {

	return Inventarioprocedimentisoftware.class;
    }

    @Override
    public List<Inventarioprocedimentisoftware> findAll(Integer firstResult, Integer maxResult) {

	return inventarioprocedimentisoftwareDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Inventarioprocedimentisoftware entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    inventarioprocedimentisoftwareDAO.insert(entity);
	}
    }

    private void dataIntegration(Inventarioprocedimentisoftware entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro Inventarioprocedimentisoftware entity non può esere nullo");
	}
	if (entity.getFlagMovOpzionale() == null) {
	    entity.setFlagMovOpzionale(Boolean.FALSE);
	}
    }

    @Override
    public Inventarioprocedimentisoftware findById(PkId id) {

	return inventarioprocedimentisoftwareDAO.findById(id);
    }

    @Override
    public void update(Inventarioprocedimentisoftware entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    inventarioprocedimentisoftwareDAO.update(entity);
	}
    }

    @Override
    public void delete(Inventarioprocedimentisoftware entity) {

	if (isDeleteAllowed(entity)) {
	    inventarioprocedimentisoftwareDAO.delete(entity);
	}
    }

    @Override
    public Inventarioprocedimentisoftware findByEndoAndSoftware(Inventarioprocedimenti endo, Software software) {

	if (EntityUtils.getNestedProperty(endo, "id.codice") == null) {
	    throw new IllegalArgumentException("Il parametro endo non è valido");
	}
	if (EntityUtils.getNestedProperty(software, "codice") == null) {
	    throw new IllegalArgumentException("Il parametro software non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("inventarioprocedimentoId", endo.getId().getCodice(), Integer.class));
	criterio.addFilterField(FilterUtils.equals("software.codice", software.getCodice(), String.class));
	ft.addRestriction(criterio);
	List<Inventarioprocedimentisoftware> list = inventarioprocedimentisoftwareDAO.findByFilterTable(ft);
	if (list.size() > 0) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public List<Inventarioprocedimentisoftware> findByTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult) {

	if (StringUtils.isBlank(tipomovimento)) {
	    throw new IllegalArgumentException("Il parametro tipomovimento non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("tipimovimentoId", tipomovimento, String.class));
	ft.addRestriction(criterio);
	ft.addOrder(FilterUtils.orderAsc("ordine", "inventarioprocedimento"));
	ft.addOrder(FilterUtils.orderAsc("procedimento", "inventarioprocedimento"));
	List<Inventarioprocedimentisoftware> list = inventarioprocedimentisoftwareDAO.findByFilterTable(ft, firstResult, maxResult);
	return list;
    }

    @Override
    public List<Inventarioprocedimentisoftware> findByCodiceInventarioprocedimenti(Integer codiceendo) {

	if (codiceendo == null) {
	    throw new IllegalArgumentException("Il parametro codiceendo non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("inventarioprocedimentoId", codiceendo, Integer.class));
	ft.addRestriction(criterio);
	ft.addOrder(FilterUtils.orderAsc("ordine", "software"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "software"));
	ft.addOrder(FilterUtils.orderAsc("movimento", "tipimovimento"));
	ft.addOrder(FilterUtils.orderAsc("amministrazione", "amministrazioni"));
	List<Inventarioprocedimentisoftware> list = inventarioprocedimentisoftwareDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<Inventarioprocedimentisoftware> findByEndoprocedimentiAndSoftware(Integer codiceinventario, String software) {

	if (codiceinventario == null) {
	    throw new IllegalArgumentException("Il parametro codiceendo non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("inventarioprocedimentoId", codiceinventario, Integer.class));
	criterio.addFilterField(FilterUtils.equals("software.codice", software, String.class));
	ft.addRestriction(criterio);
	ft.addOrder(FilterUtils.orderAsc("movimento", "tipimovimento"));
	ft.addOrder(FilterUtils.orderAsc("amministrazione", "amministrazioni"));
	List<Inventarioprocedimentisoftware> list = inventarioprocedimentisoftwareDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<Inventarioprocedimentisoftware> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "amministrazioni", Integer.class));
	filterTable.addRestriction(fr);
	return inventarioprocedimentisoftwareDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }
}
