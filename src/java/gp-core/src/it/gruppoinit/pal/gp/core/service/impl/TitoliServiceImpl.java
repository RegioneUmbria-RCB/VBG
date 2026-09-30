package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TitoliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Formegiuridiche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Titoli;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.TitoliService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 * 
 */
@Service
public class TitoliServiceImpl extends BaseServiceImpl<Titoli, PkId> implements TitoliService {

    private TitoliDAO titoliDAO;
    private AnagrafeService anagrafeService;

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setTitoliDAO(TitoliDAO titoliDAO) {

	this.titoliDAO = titoliDAO;
    }

    @Override
    public void delete(Titoli entity) {

	if (isDeleteAllowed(entity)) {
	    titoliDAO.delete(entity);
	}
    }

    @Override
    public List<Titoli> findAll(Integer firstResult, Integer maxResult) {

	return titoliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Titoli findById(PkId id) {

	return titoliDAO.findById(id);
    }

    @Override
    public Class<Titoli> getEntityClass() {

	return Titoli.class;
    }

    @Override
    public void insert(Titoli entity) {

	if (validateEntity(entity)) {
	    titoliDAO.insert(entity);
	}
    }

    @Override
    public void update(Titoli entity) {

	if (validateEntity(entity)) {
	    titoliDAO.update(entity);
	}
    }

    @Override
    public List<Titoli> findByDescrizione(String descrizione) {

	return titoliDAO.findByDescrizione(descrizione);
    }

    @Override
    protected Titoli customBindDomainObject(Titoli entity) {

	if (entity == null) {
	    return null;
	}
	if (StringUtils.isBlank(entity.getTitolo())) {
	    return null;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (Utilities.isInteger(entity.getTitolo().trim())) { //L'AREA RISERVATA MANDA IL TITOLO COME CODICE
	    fr.addFilterField(FilterUtils.equals("id.codice", Integer.parseInt(entity.getTitolo().trim()), Integer.class));
	} else {
	    fr.addFilterField(FilterUtils.equalsIgnoreCase("titolo", entity.getTitolo()));
	}
	ft.addRestriction(fr);
	List<Titoli> results = titoliDAO.findByFilterTable(ft);
	if (results.size() == 1) {
	    return results.get(0);
	}
	return null;
    }

    protected boolean isDeleteAllowed(Titoli entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	List<Anagrafe> anagrafes = anagrafeService.findByTitoli(entity.getId().getCodice(), 0, 2);
	if (!anagrafes.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ANAGRAFE", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
