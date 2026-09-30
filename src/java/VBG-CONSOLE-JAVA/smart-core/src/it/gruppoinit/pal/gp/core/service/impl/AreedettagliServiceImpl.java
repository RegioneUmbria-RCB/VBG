package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AreedettagliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Areedettagli;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AreedettagliService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Riccardo BOcci
 * 
 */
@Service
public class AreedettagliServiceImpl extends BaseServiceImpl<Areedettagli, PkId> implements AreedettagliService {

    private AreedettagliDAO areedettagliDAO;

    @Autowired
    public void setAreedettagliDAO(AreedettagliDAO areedettagliDAO) {

	this.areedettagliDAO = areedettagliDAO;
    }

    @Override
    protected Class<Areedettagli> getEntityClass() {

	return Areedettagli.class;
    }

    @Override
    public void delete(Areedettagli entity) {

	areedettagliDAO.delete(entity);
    }

    @Override
    public List<Areedettagli> findAll(Integer firstResult, Integer maxResult) {

	return areedettagliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Areedettagli findById(PkId id) {

	return areedettagliDAO.findById(id);
    }

    @Override
    public void insert(Areedettagli entity) {

	if (validateEntity(entity)) {
	    areedettagliDAO.insert(entity);
	}
    }

    @Override
    public void update(Areedettagli entity) {

	if (validateEntity(entity)) {
	    areedettagliDAO.update(entity);
	}
    }

    @Override
    public List<Areedettagli> findByAree(Aree area) {

	return areedettagliDAO.findByAree(area);
    }

    @Override
    public List<Areedettagli> findByStradario(Stradario stradario) {

	return areedettagliDAO.findByStradario(stradario);
    }

    @Override
    public int countRecordByStradario(Stradario stradario) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.codice", stradario.getId().getCodice(), "stradario", Integer.class));
	filterTable.addRestriction(filterRestriction);
	return areedettagliDAO.countRecord(filterTable);
    }

    @Override
    protected boolean validateEntity(Areedettagli entity) {

	super.validateEntity(entity);
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getCivicoDa() >= entity.getCivicoA()) {
	    _ivs.add(new InvalidValue("areedettagli.error.civico_da_maggiore_civico_a", null, "civicoDa", null, null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }
}
