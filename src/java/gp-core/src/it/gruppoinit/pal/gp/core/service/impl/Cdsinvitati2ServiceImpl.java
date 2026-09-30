package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.Cdsinvitati2DAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Cds;
import it.gruppoinit.pal.gp.core.domain.Cdsinvitati2;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.CdsService;
import it.gruppoinit.pal.gp.core.service.Cdsinvitati2Service;
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
public class Cdsinvitati2ServiceImpl extends BaseServiceImpl<Cdsinvitati2, PkId> implements Cdsinvitati2Service {

    private AnagrafeService anagrafeService;
    private Cdsinvitati2DAO cdsinvitati2DAO;
    private CdsService cdsService;
    private IstanzeService istanzeService;

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setCdsinvitati2DAO(Cdsinvitati2DAO cdsinvitati2DAO) {

	this.cdsinvitati2DAO = cdsinvitati2DAO;
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
    protected Class<Cdsinvitati2> getEntityClass() {

	return Cdsinvitati2.class;
    }

    @Override
    public List<Cdsinvitati2> findAll(Integer firstResult, Integer maxResult) {

	return cdsinvitati2DAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Cdsinvitati2 entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    cdsinvitati2DAO.insert(entity);
	}
    }

    @Override
    public Cdsinvitati2 findById(PkId id) {

	return cdsinvitati2DAO.findById(id);
    }

    @Override
    public void update(Cdsinvitati2 entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    cdsinvitati2DAO.update(entity);
	}
    }

    @Override
    public void delete(Cdsinvitati2 entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    cdsinvitati2DAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(Cdsinvitati2 entity) {

    }

    protected boolean isDeleteAllowed(Cdsinvitati2 entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    private void dataIntegration(Cdsinvitati2 entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro CDSINVITATI2 è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Cdsinvitati2 entity) {

	Anagrafe richiedente = anagrafeService.bindDomainObject(entity.getAnagrafe(), PkId.class, "id.codice");
	entity.setAnagrafe(richiedente);
	Istanze istanze = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanze);
	Cds cds = cdsService.bindDomainObject(entity.getCds(), PkId.class, "id.codice");
	entity.setCds(cds);
    }

    @Override
    public List<Cdsinvitati2> findByFilterTable(FilterTable filterTable) {

	return cdsinvitati2DAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Cdsinvitati2> findByCds(Cds cds) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("cds", cds, Cds.class));
	ft.addRestriction(criterio);
	ft.addOrder(FilterUtils.orderAsc("nominativo", "anagrafe"));
	return this.findByFilterTable(ft);
    }

    @Override
    public List<Cdsinvitati2> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	filterTable.addRestriction(fr);
	return cdsinvitati2DAO.findByFilterTable(filterTable, firstResult, maxResult);
    }
}
