package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocAtecoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocAteco;
import it.gruppoinit.pal.gp.core.domain.AlberoprocAtecoId;
import it.gruppoinit.pal.gp.core.domain.Ateco;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocAtecoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AtecoService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class AlberoprocAtecoServiceImpl extends BaseServiceImpl<AlberoprocAteco, AlberoprocAtecoId> implements AlberoprocAtecoService {

    private AlberoprocAtecoDAO alberoprocatecoDAO;
    private AtecoService atecoService;
    private AlberoprocService alberoprocService;

    @Autowired
    public void setAlberoprocAtecoDAO(AlberoprocAtecoDAO alberoprocatecoDAO) {

	this.alberoprocatecoDAO = alberoprocatecoDAO;
    }

    @Autowired
    public void setAtecoService(AtecoService atecoService) {

	this.atecoService = atecoService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Override
    protected Class<AlberoprocAteco> getEntityClass() {

	return AlberoprocAteco.class;
    }

    @Override
    public List<AlberoprocAteco> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocatecoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AlberoprocAteco entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    alberoprocatecoDAO.insert(entity);
	}
    }

    @Override
    public AlberoprocAteco findById(AlberoprocAtecoId id) {

	return alberoprocatecoDAO.findById(id);
    }

    @Override
    public void update(AlberoprocAteco entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    alberoprocatecoDAO.update(entity);
	}
    }

    @Override
    public void delete(AlberoprocAteco entity) {

	if (isDeleteAllowed(entity)) {
	    alberoprocatecoDAO.delete(entity);
	}
    }

    private void dataIntegration(AlberoprocAteco entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("l'AlberoprocAteco passato è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(AlberoprocAteco entity) {

	Ateco ateco = atecoService.bindDomainObject(entity.getAteco(), Integer.class, "id");
	entity.setAteco(ateco);
	Alberoproc alberoproc = alberoprocService.bindDomainObject(entity.getAlberoproc(), PkId.class, "id.codice");
	entity.setAlberoproc(alberoproc);
    }

    protected boolean isInsertAllowed(AlberoprocAteco entity) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (this.findById(entity.getId()) != null) {
	    _ivs.add(new InvalidValue("service_error.stesso_ateco", null, null, entity.getId(), null));
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    @Override
    public List<AlberoprocAteco> findByAlberoproc(Alberoproc entity) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkScid", entity.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("codice", "ateco"));
	return alberoprocatecoDAO.findByFilterTable(ft);
    }
}
