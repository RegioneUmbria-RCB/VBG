package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ComuniassociatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComuniassociatiServiceImpl extends BaseServiceImpl<Comuniassociati, ComuniassociatiId> implements ComuniassociatiService {

    private ComuniassociatiDAO comuniassociatiDAO;

    @Override
    public List<Comuniassociati> findByDescrizione(String comune) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.startsWith("comune", comune, "comune"));
	fr.addFilterField(FilterUtils.startsWith("codicecomune", comune, "comune"));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("comune", "comune"));
	return comuniassociatiDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Comuniassociati> findAllImportAutomatico() {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("importAutomatico", true, Boolean.class));
	filterTable.addRestriction(fr);
	return comuniassociatiDAO.findByFilterTable(filterTable);
    }

    @Autowired
    public void setComuniassociatiDAO(ComuniassociatiDAO comuniassociatiDAO) {

	this.comuniassociatiDAO = comuniassociatiDAO;
    }

    @Override
    protected Class<Comuniassociati> getEntityClass() {

	return Comuniassociati.class;
    }

    @Override
    public Comuniassociati findById(ComuniassociatiId id) {

	return comuniassociatiDAO.findById(id);
    }

    @Override
    public void insert(Comuniassociati entity) {

	if (validateEntity(entity)) {
	    fixMerge(entity);
	    comuniassociatiDAO.insert(entity);
	}
    }

    @Override
    public void update(Comuniassociati entity) {

	if (validateEntity(entity)) {
	    fixMerge(entity);
	    comuniassociatiDAO.update(entity);
	}
    }

    private void fixMerge(Comuniassociati entity) {

	if (entity.getImportAutomatico() == null) {
	    entity.setImportAutomatico(false);
	}
	if (entity.getOnsite() == null) {
	    entity.setOnsite(false);
	}
    }

    @Override
    public List<Comuniassociati> findAll() {

	return comuniassociatiDAO.findAll();
    }

    @Override
    public void delete(Comuniassociati entity) {

	comuniassociatiDAO.delete(entity);
    }
}
