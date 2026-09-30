package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CdsconvocazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Cds;
import it.gruppoinit.pal.gp.core.domain.Cdsconvocazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CdsService;
import it.gruppoinit.pal.gp.core.service.CdsconvocazioniService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class CdsconvocazioniServiceImpl extends BaseServiceImpl<Cdsconvocazioni, PkId> implements CdsconvocazioniService {

    private CdsconvocazioniDAO cdsconvocazioniDAO;
    private CdsService cdsService;
    private IstanzeService istanzeService;

    @Autowired
    public void setCdsconvocazioniDAO(CdsconvocazioniDAO cdsconvocazioniDAO) {

	this.cdsconvocazioniDAO = cdsconvocazioniDAO;
    }

    @Autowired
    public void setCdsService(CdsService cdsService) {

	this.cdsService = cdsService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Override
    protected Class<Cdsconvocazioni> getEntityClass() {

	return Cdsconvocazioni.class;
    }

    @Override
    public List<Cdsconvocazioni> findAll(Integer firstResult, Integer maxResult) {

	return cdsconvocazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Cdsconvocazioni entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    cdsconvocazioniDAO.insert(entity);
	    childDataInsert(entity);
	}
    }

    @Override
    public Cdsconvocazioni findById(PkId id) {

	return cdsconvocazioniDAO.findById(id);
    }

    @Override
    public void update(Cdsconvocazioni entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    cdsconvocazioniDAO.update(entity);
	    childDataUpdate(entity);
	}
    }

    @Override
    public void delete(Cdsconvocazioni entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    cdsconvocazioniDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(Cdsconvocazioni entity) {

	gestFlagEffettiva(entity);
    }

    private void childDataInsert(Cdsconvocazioni entity) {

	gestFlagEffettiva(entity);
    }

    private void childDataUpdate(Cdsconvocazioni entity) {

	gestFlagEffettiva(entity);
    }

    private void dataIntegration(Cdsconvocazioni entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro CDSCONVOCAZIONI è nullo");
	}
	fixMergeEntityProperties(entity);
	if (entity.getFlagEffettiva() == null) {
	    entity.setFlagEffettiva(Boolean.FALSE);
	}
    }

    /**
     * La funzione serve ad aggiornare il flag_effettiva. per ogni cds ci può essere solamente una convocazione con il
     * flag effettiva=true
     * 
     * @param entity
     */
    private void gestFlagEffettiva(Cdsconvocazioni entity) {

	List<Cdsconvocazioni> convocazionis = findByCds(entity.getCds());
	int id = entity.getId().getCodice();
	if (entity.getFlagEffettiva().booleanValue() == true) {
	    for (Cdsconvocazioni cdsconvocazioni : convocazionis) {
		int idAltra = cdsconvocazioni.getId().getCodice();
		if (id != idAltra) {
		    if (BooleanUtils.isTrue(cdsconvocazioni.getFlagEffettiva())) {
			cdsconvocazioni.setFlagEffettiva(Boolean.FALSE);
			cdsconvocazioniDAO.update(cdsconvocazioni);
		    }
		}
	    }
	}
    }

    @Override
    protected void fixMergeEntityProperties(Cdsconvocazioni entity) {

	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanza);
	Cds cds = cdsService.bindDomainObject(entity.getCds(), PkId.class, "id.codice");
	entity.setCds(cds);
    }

    protected boolean isDeleteAllowed(Cdsconvocazioni entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Cdsconvocazioni> findByCds(Cds cds) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", cds.getId().getCodice(), "cds", Integer.class));
	ft.addOrder(FilterUtils.orderAsc("dataconvocazione"));
	ft.addOrder(FilterUtils.orderAsc("oraconvocazione"));
	ft.addRestriction(fr);
	return cdsconvocazioniDAO.findByFilterTable(ft);
    }
}
