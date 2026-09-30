package it.gruppoinit.pal.gp.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Areedettagli> findByStradario(Integer stradario) {

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
	if (entity.getCivicoDa() != null && entity.getCivicoA() != null) {
	    if (entity.getCivicoDa() >= entity.getCivicoA()) {
		_ivs.add(new InvalidValue("areedettagli.error.civico_da_maggiore_civico_a", null, "civicoDa", null, null));
	    }
	} else if (entity.getKmDa() != null && entity.getKmA() != null) {
	    if (entity.getKmDa().compareTo(entity.getKmA()) > 0) {
		_ivs.add(new InvalidValue("areedettagli.error.km_da_maggiore_km_a", null, "kmDa", null, null));
	    }
	} else {
	    _ivs.add(new InvalidValue("areedettagli.error.almeno_civico_o_km", null, null, null, null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }

    @Override
    public List<Areedettagli> findByStradarioECivico(Integer codiceStradario, Integer civico) {

	return areedettagliDAO.findByStradarioECivico(codiceStradario, civico);
    }

    @Override
    public List<Areedettagli> findByStradarioEKm(Integer codiceStradario, BigDecimal km) {

	return areedettagliDAO.findByStradarioEKm(codiceStradario, km);
    }
}
