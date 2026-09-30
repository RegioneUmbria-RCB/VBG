package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeAccessoAttiDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiD;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiDService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiTService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class IstanzeAccessoAttiDServiceImpl extends BaseServiceImpl<IstanzeAccessoAttiD, PkId> implements IstanzeAccessoAttiDService {

    private static final Logger log = LoggerFactory.getLogger(IstanzeAccessoAttiDServiceImpl.class);
    private IstanzeAccessoAttiDDAO istanzeaccessoattidDAO;
    private IstanzeAccessoAttiTService istanzeAccessoAttiTService;
    private IstanzeService istanzeService;

    @Autowired
    public void setIstanzeAccessoAttiDDAO(IstanzeAccessoAttiDDAO istanzeaccessoattidDAO) {

	this.istanzeaccessoattidDAO = istanzeaccessoattidDAO;
    }

    @Autowired
    public void setIstanzeAccessoAttiTService(IstanzeAccessoAttiTService istanzeAccessoAttiTService) {

	this.istanzeAccessoAttiTService = istanzeAccessoAttiTService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Override
    protected Class<IstanzeAccessoAttiD> getEntityClass() {

	return IstanzeAccessoAttiD.class;
    }

    @Override
    public List<IstanzeAccessoAttiD> findAll(Integer firstResult, Integer maxResult) {

	return istanzeaccessoattidDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IstanzeAccessoAttiD entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeaccessoattidDAO.insert(entity);
	}
    }

    @Override
    public IstanzeAccessoAttiD findById(PkId id) {

	return istanzeaccessoattidDAO.findById(id);
    }

    @Override
    public void update(IstanzeAccessoAttiD entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeaccessoattidDAO.update(entity);
	}
    }

    @Override
    public void delete(IstanzeAccessoAttiD entity) {

	if (isDeleteAllowed(entity)) {
	    istanzeaccessoattidDAO.delete(entity);
	}
    }

    private void dataIntegration(IstanzeAccessoAttiD entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'istanza di accesso agli atti D è nulla");
	}
	fixMergeEntityProperties(entity);
	if (entity.getFlgVisualizzaDoc() == null) {
	    entity.setFlgVisualizzaDoc(0);
	}
    }

    protected void fixMergeEntityProperties(IstanzeAccessoAttiD entity) {

	IstanzeAccessoAttiT istAccAttiT = istanzeAccessoAttiTService.bindDomainObject(entity.getIstanzeAccessoAttiT(), PkId.class, "id.codice");
	entity.setIstanzeAccessoAttiT(istAccAttiT);
	Istanze istanze = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanze);
    }

    protected boolean isDeleteAllowed(IstanzeAccessoAttiD entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<IstanzeAccessoAttiD> findByIstanzeAccessoAttiT(Integer codice) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "istanzeAccessoAttiT", Integer.class));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("data", "istanze"));
	filterTable.addOrder(FilterUtils.orderAsc("numeroistanza", "istanze"));
	return istanzeaccessoattidDAO.findByFilterTable(filterTable);
    }

    @Override
    public void insert(String[] arrayCodiceIstanza, String[] arrayCodiceIstanzaMostraDocValidi, Integer codiceIstanzaAccessoAtti) {

	IstanzeAccessoAttiD istanzeAccessoAttiD = null;
	IstanzeAccessoAttiT istanzeAccessoAttiT = istanzeAccessoAttiTService.findById(new PkId(codiceIstanzaAccessoAtti));
	Set<String> setCodiciIstanza = new HashSet<String>(Arrays.asList(arrayCodiceIstanza));
	for (String codiceIstanza : setCodiciIstanza) {
	    log.debug("insert# Verifico se l'istanza è già presente nel fascicolo");
	    IstanzeAccessoAttiD accessoAttiD = this.findByIstanzaAndAttiT(Integer.parseInt(codiceIstanza), codiceIstanzaAccessoAtti);
	    if (accessoAttiD != null) {
		log.debug("insert# Istanza  = {}, già presente nel fascicolo", codiceIstanza);
	    } else {
		istanzeAccessoAttiD = new IstanzeAccessoAttiD();
		istanzeAccessoAttiD.setIstanzeAccessoAttiT(istanzeAccessoAttiT);
		Istanze istanze = istanzeService.findById(new PkId(Integer.parseInt(codiceIstanza)));
		istanzeAccessoAttiD.setIstanze(istanze);
		// Il valore sarà true se il codice istanza in esame sarà presente nell'array arrayCodiceIstanzaMostraDocValidi, altrimenti sarà false
		// Boolean flgVisualizzaDoc = Boolean.FALSE;
		Integer flgVisualizzaDoc = 0;
		for (String impostazione : arrayCodiceIstanzaMostraDocValidi) {
		    if (impostazione.startsWith((String) codiceIstanza + "-")) {
			flgVisualizzaDoc = Integer.parseInt(impostazione.replace(((String) codiceIstanza + "-"), ""));
			break;
		    }
		}
		log.debug("insert# Codice istanza = {}, flgVisualizzaDocSelezionato = {} ", codiceIstanza, flgVisualizzaDoc);
		istanzeAccessoAttiD.setFlgVisualizzaDoc(flgVisualizzaDoc);
		insert(istanzeAccessoAttiD);
	    }
	}
    }

    @Override
    public IstanzeAccessoAttiD findByIstanzaAndAttiT(Integer codiceIstanza, Integer codiceIstanzaAccessoAtti) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanzaAccessoAtti, "istanzeAccessoAttiT", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanze", Integer.class));
	filterTable.addRestriction(fr);
	List<IstanzeAccessoAttiD> l = istanzeaccessoattidDAO.findByFilterTable(filterTable);
	if (!l.isEmpty()) {
	    return l.get(0);
	}
	return null;
    }

    @Override
    public List<IstanzeAccessoAttiD> findByIstanza(Integer codiceIstanza) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanze", Integer.class));
	filterTable.addRestriction(fr);
	List<IstanzeAccessoAttiD> l = istanzeaccessoattidDAO.findByFilterTable(filterTable);
	return l;
    }
}
