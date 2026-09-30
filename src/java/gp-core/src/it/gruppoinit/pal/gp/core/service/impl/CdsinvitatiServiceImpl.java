package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CdsinvitatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Cds;
import it.gruppoinit.pal.gp.core.domain.Cdsinvitati;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.CdsService;
import it.gruppoinit.pal.gp.core.service.CdsinvitatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class CdsinvitatiServiceImpl extends BaseServiceImpl<Cdsinvitati, PkId> implements CdsinvitatiService {

    private AmministrazioniService amministrazioniService;
    private CdsinvitatiDAO cdsinvitatiDAO;
    private CdsService cdsService;
    private IstanzeService istanzeService;

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setCdsinvitatiDAO(CdsinvitatiDAO cdsinvitatiDAO) {

	this.cdsinvitatiDAO = cdsinvitatiDAO;
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
    protected Class<Cdsinvitati> getEntityClass() {

	return Cdsinvitati.class;
    }

    @Override
    public List<Cdsinvitati> findAll(Integer firstResult, Integer maxResult) {

	return cdsinvitatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Cdsinvitati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    cdsinvitatiDAO.insert(entity);
	}
    }

    @Override
    public Cdsinvitati findById(PkId id) {

	return cdsinvitatiDAO.findById(id);
    }

    @Override
    public void update(Cdsinvitati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    cdsinvitatiDAO.update(entity);
	}
    }

    @Override
    public void delete(Cdsinvitati entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    cdsinvitatiDAO.delete(entity);
	}
    }

    @Override
    public List<Cdsinvitati> findByFilterTable(FilterTable filterTable) {

	return cdsinvitatiDAO.findByFilterTable(filterTable);
    }

    private void dataIntegration(Cdsinvitati entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro CDSINVITATI è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Cdsinvitati entity) {

	Cds cds = cdsService.bindDomainObject(entity.getCds(), PkId.class, "id.codice");
	entity.setCds(cds);
	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanza);
	Amministrazioni amministrazione = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	entity.setAmministrazioni(amministrazione);
    }

    protected boolean isDeleteAllowed(Cdsinvitati entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    protected void childDelete(Cdsinvitati entity) {

    }

    @Override
    public List<Cdsinvitati> findByCds(Cds cds) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("cds", cds, Cds.class));
	ft.addRestriction(criterio);
	ft.addOrder(FilterUtils.orderAsc("amministrazione", "amministrazioni"));
	return this.findByFilterTable(ft);
    }

    @Override
    public List<Cdsinvitati> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "amministrazioni", Integer.class));
	filterTable.addRestriction(fr);
	return cdsinvitatiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }
}
