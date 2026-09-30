package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AutorizzazioniSubentriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentriConc;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniFilter;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriConcService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;

/**
 * 
 * @author fabrizioc
 */
@Service
public class AutorizzazioniSubentriServiceImpl extends BaseServiceImpl<AutorizzazioniSubentri, PkId> implements AutorizzazioniSubentriService {

    private AutorizzazioniSubentriDAO autorizzazionisubentriDAO;
    private AutorizzazioniService autorizzazioniService;
    private AutorizzazioniSubentriConcService autorizzazioniSubentriConcService;
    private AnagrafeService anagrafeService;

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setAutorizzazioniSubentriConcService(AutorizzazioniSubentriConcService autorizzazioniSubentriConcService) {

	this.autorizzazioniSubentriConcService = autorizzazioniSubentriConcService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setAutorizzazioniSubentriDAO(AutorizzazioniSubentriDAO autorizzazionisubentriDAO) {

	this.autorizzazionisubentriDAO = autorizzazionisubentriDAO;
    }

    @Override
    protected Class<AutorizzazioniSubentri> getEntityClass() {

	return AutorizzazioniSubentri.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AutorizzazioniSubentri> findAll(Integer firstResult, Integer maxResult) {

	return autorizzazionisubentriDAO.findAll(firstResult, maxResult);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AutorizzazioniSubentri findAutSubOConcSubByEstremi(String autoriznumero, Date autorizdata, String codicecomune, Integer codiceregistro) {

	return autorizzazionisubentriDAO.findByEstremi(autoriznumero, autorizdata, codicecomune, codiceregistro);
    }

    @Override
    public void insert(AutorizzazioniSubentri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    autorizzazionisubentriDAO.insert(entity);
	}
    }

    @Override
    public AutorizzazioniSubentri findById(PkId id) {

	return autorizzazionisubentriDAO.findById(id);
    }

    @Override
    public void update(AutorizzazioniSubentri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    autorizzazionisubentriDAO.update(entity);
	}
    }

    @Override
    public void delete(AutorizzazioniSubentri entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    autorizzazionisubentriDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(AutorizzazioniSubentri entity) {

	List<AutorizzazioniSubentriConc> autSubsConcs = autorizzazioniSubentriConcService.findByIdSubentro(entity.getId().getCodice());
	for (AutorizzazioniSubentriConc autorizzazioniSubentriConc : autSubsConcs) {
	    autorizzazioniSubentriConcService.delete(autorizzazioniSubentriConc);
	}
    }

    @Override
    public List<AutorizzazioniSubentri> findAutorizzazioniSubentriByIstanza(Istanze istanza) {

	return autorizzazionisubentriDAO.findAutorizzazioniSubentriByIstanza(istanza);
    }

    @Override
    public List<AutorizzazioniSubentri> findConcessioniSubentriByIstanza(Integer codiceIstanza) {

	return autorizzazionisubentriDAO.findConcessioniSubentriByIstanza(codiceIstanza);
    }

    @Override
    public List<AutorizzazioniSubentri> findSubentriByConcessione(Integer codiceConcessioneAttiva, Integer firstResult, Integer maxResults,
	    OrderTypeEnum order) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("autorizzazioniId", codiceConcessioneAttiva, Integer.class));
	ft.addRestriction(filterRestriction);
	if (order == null) {
	    order = OrderTypeEnum.DESC;
	}
	switch (order) {
	case ASC:
	    ft.addOrder(FilterUtils.orderAsc("dataCessazione"));
	    ft.addOrder(FilterUtils.orderAsc("id.codice"));
	case DESC:
	    ft.addOrder(FilterUtils.orderDesc("dataCessazione"));
	    ft.addOrder(FilterUtils.orderDesc("id.codice"));
	}
	if (firstResult != null && maxResults != null) {
	    return autorizzazionisubentriDAO.findByFilterTable(ft, firstResult, maxResults);
	} else {
	    return autorizzazionisubentriDAO.findByFilterTable(ft);
	}
    }

    @Override
    public List<AutorizzazioniSubentri> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	filterTable.addRestriction(fr);
	return autorizzazionisubentriDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public int countByFilter(AutorizzazioniFilter filter) {

	return autorizzazionisubentriDAO.countByFilter(filter);
    }

    @Override
    public List<AutorizzazioniSubentri> findAutorizzazioniSubentriByFilter(AutorizzazioniFilter filter, Integer firstResult, Integer maxResult) {

	return autorizzazionisubentriDAO.findAutorizzazioniSubentriByFilter(filter, firstResult, maxResult);
    }

    protected boolean isDeleteAllowed(AutorizzazioniSubentri entity) {

	boolean delete = true;
	// Controlla il tipo di autorizzazione
	String tipoAut = autorizzazioniService.tipoAutorizzazione(entity.getTipologiaregistro().getId().getCodice());
	if (tipoAut.equals(WebConstants.AUTORIZZAZIONE_DEHORS)) {
	    this.throwValidationMessage(
		    new InvalidValue("autorizzazioni_subentri.service_error.impossibile_cancellare_autorizzazione", null, null, "", null));
	}
	return delete;
    }

    private void dataIntegration(AutorizzazioniSubentri entity) {

	if (entity == null) {
	    throw new RuntimeException("Non si può inserire/aggiornare un'autorizzazione nulla");
	}
	if (entity.getOccupante() == null) {
	    // default occupante è titolare
	    entity.setOccupante(entity.getAnagrafe());
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(AutorizzazioniSubentri entity) {

	//	Mercati mercati = mercatiService.bindDomainObject(entity.getMercati(), PkId.class, "id.codice");
	//	entity.setMercati(mercati);
	//	MercatiUso mercatiUso = mercatiUsoService.bindDomainObject(entity.getMercatiUso(), PkId.class, "id.codice");
	//	entity.setMercatiUso(mercatiUso);
	//	MercatiD mercatiD = mercatiDService.bindDomainObject(entity.getMercatiD(), PkId.class, "id.codice");
	//	entity.setMercatiD(mercatiD);
	//	Concessionitipi concessionitipi = concessionitipiService.bindDomainObject(entity.getConcessionitipi(), String.class, "tipoconcessione");
	//	entity.setConcessionitipi(concessionitipi);
    }

    @Override
    public List<AutorizzazioniSubentri> findByAutorizzazione(Integer codiceAutorizzazione, Integer firstResult, Integer maxResults) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("autorizzazioniId", codiceAutorizzazione, Integer.class));
	ft.addRestriction(filterRestriction);
	ft.addOrder(FilterUtils.orderDesc("dataCessazione"));
	ft.addOrder(FilterUtils.orderDesc("id.codice"));
	if (firstResult != null && maxResults != null) {
	    return autorizzazionisubentriDAO.findByFilterTable(ft, firstResult, maxResults);
	} else {
	    return autorizzazionisubentriDAO.findByFilterTable(ft);
	}
    }

    @Override
    public AutorizzazioniSubentri findByNumeroAndComune(String autoriznumero, String codicecomune) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("autoriznumero", autoriznumero));
	fr.addFilterField(FilterUtils.equals("codicecomune", codicecomune, "autorizcomune", String.class));
	fr.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), "tipologiaregistro", String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("dataCessazione"));
	List<AutorizzazioniSubentri> list = autorizzazionisubentriDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public void clear() {

	autorizzazionisubentriDAO.clear();
    }

    @Override
    public AutorizzazioniSubentri inserisciSubentroDaImport(AutorizzazioniSubentri autorizzazioniSubentri, Autorizzazioni autorizzazioneSubentrata) {

	Set<AutorizzazioniSubentriConc> autSubentrisConcs = autorizzazioniSubentri.getAutSubentrisConcs();
	autorizzazioniSubentri.setAutSubentrisConcs(null);//Usato per evitare errori con hibernate in fase di insert
	if (autorizzazioniSubentri.getIstanze() == null) {
	    autorizzazioniSubentri.setIstanze(autorizzazioneSubentrata.getIstanza());
	}
	autorizzazioniSubentri.setAutorizzazioni(autorizzazioneSubentrata);
	autorizzazioniSubentri.setAnagrafe(verificaAnagrafe(autorizzazioniSubentri));
	autorizzazioniSubentri.setOccupante(verificaOccupante(autorizzazioniSubentri));
	try {
	    insert(autorizzazioniSubentri);
	} catch (Exception e) {
	    List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	    if (e instanceof BaseValidationException) {
		ivs = ((BaseValidationException) e).getInvalidValues();
	    }
	    this.throwValidationMessages(ivs);
	}
	if (autSubentrisConcs.size() > 0) {
	    inserisciSubentriConcDaAutorizzazione(autSubentrisConcs, autorizzazioniSubentri);
	}
	return autorizzazioniSubentri;
    }

    private Anagrafe verificaAnagrafe(AutorizzazioniSubentri entity) {

	Anagrafe anagrafe = null;
	if (entity.getAnagrafe() != null) {
	    anagrafe = anagrafeService.bindDomainObject(entity.getAnagrafe(), PkId.class, "id.codice");
	}
	return anagrafe;
    }

    private Anagrafe verificaOccupante(AutorizzazioniSubentri entity) {

	Anagrafe anagrafe = null;
	if (entity.getOccupante() != null) {
	    anagrafe = anagrafeService.bindDomainObject(entity.getOccupante(), PkId.class, "id.codice");
	}
	return anagrafe;
    }

    private void inserisciSubentriConcDaAutorizzazione(Set<AutorizzazioniSubentriConc> autSubentrisConcs,
	    AutorizzazioniSubentri autorizzazioniSubentri) {

	for (AutorizzazioniSubentriConc autorizzazioniSubentriConc : autSubentrisConcs) {
	    try {
		autorizzazioniSubentriConc.setAutorizzazioniSubentri(autorizzazioniSubentri);
		autorizzazioniSubentriConcService.insert(autorizzazioniSubentriConc);
	    } catch (Exception e) {
		List<InvalidValue> ivs = new ArrayList<InvalidValue>();
		if (e instanceof BaseValidationException) {
		    ivs = ((BaseValidationException) e).getInvalidValues();
		}
		this.throwValidationMessages(ivs);
	    }
	}
    }
}
