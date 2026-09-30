package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.LayoutpagineDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Layoutpagine;
import it.gruppoinit.pal.gp.core.domain.LayoutpagineId;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.LayoutpagineService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class LayoutpagineServiceImpl extends BaseServiceImpl<Layoutpagine, LayoutpagineId> implements LayoutpagineService {

    private LayoutpagineDAO layoutpagineDAO;

    @Autowired
    public void setLayoutpagineDAO(LayoutpagineDAO layoutpagineDAO) {

	this.layoutpagineDAO = layoutpagineDAO;
    }

    @Override
    protected Class<Layoutpagine> getEntityClass() {

	return Layoutpagine.class;
    }

    @Override
    public List<Layoutpagine> findAll(Integer firstResult, Integer maxResult) {

	return layoutpagineDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Layoutpagine entity) {

	if (validateEntity(entity)) {
	    layoutpagineDAO.insert(entity);
	}
    }

    @Override
    public Layoutpagine findById(LayoutpagineId id) {

	return layoutpagineDAO.findById(id);
    }

    @Override
    public void update(Layoutpagine entity) {

	if (validateEntity(entity)) {
	    layoutpagineDAO.update(entity);
	}
    }

    @Override
    public void delete(Layoutpagine entity) {

	if (isDeleteAllowed(entity)) {
	    layoutpagineDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Layoutpagine entity) {

	return true;
    }

    @Override
    public Set<String> findOggettiDisabilitatiPerPagina(String nomePagina) {

	if (StringUtils.isBlank(nomePagina)) {
	    return new HashSet<String>();
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction software = new FilterRestriction();
	if (ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
	    software.addFilterField(FilterUtils.equals("id.software", WebConstants.SOFTWARE_TT, String.class));
	} else {
	    software.addFilterField(FilterUtils.in("id.software", new String[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }, String.class));
	}
	ft.addRestriction(software);
	FilterRestriction pagina = new FilterRestriction();
	pagina.setAndOrRestriction(AndOrRestriction.OR);
	pagina.addFilterField(FilterUtils.equals("id.lpPagina", "*", String.class));
	pagina.addFilterField(FilterUtils.equalsIgnoreCase("id.lpPagina", nomePagina));
	ft.addRestriction(pagina);
	List<Layoutpagine> list = layoutpagineDAO.findByFilterTable(ft);
	Set<String> listaOggettiDisabilitati = new HashSet<String>();
	for (Layoutpagine layoutpagine : list) {
	    if (StringUtils.equalsIgnoreCase(layoutpagine.getLpDisabilita(), "S")) {
		listaOggettiDisabilitati.add(layoutpagine.getId().getLpOggetto());
	    }
	}
	return listaOggettiDisabilitati;
    }
}
