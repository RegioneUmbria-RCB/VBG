package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcIcalcolototDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CcBasetipocalcolo;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloTcontributo;
import it.gruppoinit.pal.gp.core.domain.CcIcalcolotot;
import it.gruppoinit.pal.gp.core.domain.CcValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.OccBasedestinazioni;
import it.gruppoinit.pal.gp.core.domain.OccBasetipointervento;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CcBasetipocalcoloService;
import it.gruppoinit.pal.gp.core.service.CcIcalcoloTcontributoService;
import it.gruppoinit.pal.gp.core.service.CcIcalcolototService;
import it.gruppoinit.pal.gp.core.service.CcValiditacoefficientiService;
import it.gruppoinit.pal.gp.core.service.OccBasedestinazioniService;
import it.gruppoinit.pal.gp.core.service.OccBasetipointerventoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class CcIcalcolototServiceImpl extends BaseServiceImpl<CcIcalcolotot, PkId> implements CcIcalcolototService {

    private CcIcalcoloTcontributoService ccIcalcoloTcontributoService;

    @Autowired
    public void CcIcalcoloTcontributoService(CcIcalcoloTcontributoService ccIcalcoloTcontributoService) {

	this.ccIcalcoloTcontributoService = ccIcalcoloTcontributoService;
    }

    private CcIcalcolototDAO ccicalcolototDAO;

    @Autowired
    public void setCcIcalcolototDAO(CcIcalcolototDAO ccicalcolototDAO) {

	this.ccicalcolototDAO = ccicalcolototDAO;
    }

    private OccBasetipointerventoService occBasetipointerventoService;

    @Autowired
    public void setOccBasetipointerventoService(OccBasetipointerventoService occBasetipointerventoService) {

	this.occBasetipointerventoService = occBasetipointerventoService;
    }

    private OccBasedestinazioniService occBasedestinazioniService;

    @Autowired
    public void setOccBasedestinazioniService(OccBasedestinazioniService occBasedestinazioniService) {

	this.occBasedestinazioniService = occBasedestinazioniService;
    }

    private CcValiditacoefficientiService ccValiditacoefficientiService;

    @Autowired
    public void setCcValiditacoefficientiService(CcValiditacoefficientiService ccValiditacoefficientiService) {

	this.ccValiditacoefficientiService = ccValiditacoefficientiService;
    }

    private CcBasetipocalcoloService ccBasetipocalcoloService;

    @Autowired
    public void setCcBasetipocalcoloService(CcBasetipocalcoloService ccBasetipocalcoloService) {

	this.ccBasetipocalcoloService = ccBasetipocalcoloService;
    }

    @Override
    protected Class<CcIcalcolotot> getEntityClass() {

	return CcIcalcolotot.class;
    }

    @Override
    public List<CcIcalcolotot> findAll(Integer firstResult, Integer maxResult) {

	return ccicalcolototDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcIcalcolotot entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    ccicalcolototDAO.insert(entity);
	}
    }

    @Override
    public CcIcalcolotot findById(PkId id) {

	return ccicalcolototDAO.findById(id);
    }

    @Override
    public void update(CcIcalcolotot entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    ccicalcolototDAO.update(entity);
	}
    }

    @Override
    public void delete(CcIcalcolotot entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    ccicalcolototDAO.delete(entity);
	}
    }

    @Override
    public int countByCcValiditacoefficienti(CcValiditacoefficienti entity) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.codice", entity.getId().getCodice(), "ccValiditacoefficienti", Integer.class));
	filterTable.addRestriction(filterRestriction);
	return ccicalcolototDAO.countRecord(filterTable);
    }

    @Override
    public List<CcIcalcolotot> findByIstanza(Istanze istanze) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.codice", istanze.getId().getCodice(), "istanze", Integer.class));
	filterTable.addOrder(FilterUtils.orderAsc("descrizione"));
	filterTable.addRestriction(filterRestriction);
	return ccicalcolototDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<CcIcalcolotot> findByFilterTable(FilterTable filterTable) {

	return ccicalcolototDAO.findByFilterTable(filterTable);
    }

    protected boolean isDeleteAllowed(CcIcalcolotot entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO _validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    protected void childDelete(CcIcalcolotot entity) {

	super.childDelete(entity);
	Set<CcIcalcoloTcontributo> ccIcalcoloTcontributos = entity.getCcIcalcoloTcontributos();
	for (CcIcalcoloTcontributo ccIcalcoloTcontributo : ccIcalcoloTcontributos) {
	    ccIcalcoloTcontributoService.delete(ccIcalcoloTcontributo);
	}
    }

    private void dataIntegration(CcIcalcolotot entity) {

	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(CcIcalcolotot entity) {

	OccBasetipointervento occBasetipointervento = occBasetipointerventoService.bindDomainObject(entity.getOccBasetipointervento(), String.class,
		"id");
	entity.setOccBasetipointervento(occBasetipointervento);
	OccBasedestinazioni occBasedestinazioni = occBasedestinazioniService.bindDomainObject(entity.getOccBasedestinazioni(), String.class, "id");
	entity.setOccBasedestinazioni(occBasedestinazioni);
	CcValiditacoefficienti ccValiditacoefficienti = ccValiditacoefficientiService.bindDomainObject(entity.getCcValiditacoefficienti(),
		PkId.class, "id.codice");
	entity.setCcValiditacoefficienti(ccValiditacoefficienti);
	CcBasetipocalcolo ccBasetipocalcolo = ccBasetipocalcoloService.bindDomainObject(entity.getCcBasetipocalcolo(), String.class, "id");
	entity.setCcBasetipocalcolo(ccBasetipocalcolo);
    }
}
