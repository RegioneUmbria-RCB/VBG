package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocProtocolloDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocProtAndFascHelper;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocProtocolloService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class AlberoprocProtocolloServiceImpl extends BaseServiceImpl<AlberoprocProtocollo, PkId> implements AlberoprocProtocolloService {

    private AlberoprocProtocolloDAO alberoprocprotocolloDAO;
    private AmministrazioniService amministrazioniService;
    private AlberoprocService alberoprocService;
    private ComuniService comuniService;
    private ResponsabiliService responsabiliService;

    @Autowired
    public void setAlberoprocProtocolloDAO(AlberoprocProtocolloDAO alberoprocprotocolloDAO) {

	this.alberoprocprotocolloDAO = alberoprocprotocolloDAO;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Override
    protected Class<AlberoprocProtocollo> getEntityClass() {

	return AlberoprocProtocollo.class;
    }

    @Override
    public List<AlberoprocProtocollo> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocprotocolloDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AlberoprocProtocollo entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity) && insertAllowed(entity)) {
	    alberoprocprotocolloDAO.insert(entity);
	}
    }

    private boolean insertAllowed(AlberoprocProtocollo entity) {

	boolean isInsert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	String codiceComune = null;
	if (entity.getComuni() != null) {
	    if (StringUtils.isNotBlank(entity.getComuni().getCodicecomune())) {
		codiceComune = entity.getComuni().getCodicecomune();
	    }
	}
	List<AlberoprocProtocollo> list = this.findByAlberoprocAndComune(entity.getAlberoproc().getId().getCodice(), codiceComune);
	if (list.size() > 0) {
	    _ivs.add(new InvalidValue("service_error.configurazione_esistente_duplicata", null, null, null, null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return isInsert;
    }

    @Override
    public AlberoprocProtocollo findById(PkId id) {

	return alberoprocprotocolloDAO.findById(id);
    }

    @Override
    public void update(AlberoprocProtocollo entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    alberoprocprotocolloDAO.update(entity);
	}
    }

    @Override
    public void delete(AlberoprocProtocollo entity) {

	if (isDeleteAllowed(entity)) {
	    alberoprocprotocolloDAO.delete(entity);
	}
    }

    @Override
    public List<AlberoprocProtAndFascHelper> findByComuniPerOperatore(Integer codice, Responsabili responsabile) {

	List<AlberoprocProtAndFascHelper> alberoprocProtAndFascHelpers = new ArrayList<AlberoprocProtAndFascHelper>();
	AlberoprocProtAndFascHelper alberoprocProtAndFascHelper = null;
	Set<Responsabilicomuni> responsabilicomunis = responsabiliService.findListResponsabilicomuni(responsabile);
	for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
	    String codicecomune = responsabilicomuni.getComune().getCodicecomune();
	    alberoprocProtAndFascHelper = new AlberoprocProtAndFascHelper();
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("codicecomune", codicecomune, "comuni", String.class));
	    fr.addFilterField(FilterUtils.equals("alberoprocId", codice, Integer.class));
	    filterTable.addRestriction(fr);
	    List<AlberoprocProtocollo> amministrProtocollos = alberoprocprotocolloDAO.findByFilterTable(filterTable);
	    if (amministrProtocollos.size() > 0) {
		alberoprocProtAndFascHelper.setComune(responsabilicomuni.getComune().getComune());
		alberoprocProtAndFascHelper.setAlberoprocProtocollos(amministrProtocollos);
		alberoprocProtAndFascHelpers.add(alberoprocProtAndFascHelper);
	    }
	}
	// CODICECOMUNE  NULL
	alberoprocProtAndFascHelper = new AlberoprocProtAndFascHelper();
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.isNull("codicecomune", "comuni"));
	fr.addFilterField(FilterUtils.equals("alberoprocId", codice, Integer.class));
	filterTable.addRestriction(fr);
	List<AlberoprocProtocollo> amministrProtocollos = alberoprocprotocolloDAO.findByFilterTable(filterTable);
	if (amministrProtocollos.size() > 0) {
	    alberoprocProtAndFascHelper.setComune(getMessageFromBundle("label.tutti", new Object[] {}));
	    alberoprocProtAndFascHelper.setAlberoprocProtocollos(amministrProtocollos);
	    alberoprocProtAndFascHelpers.add(alberoprocProtAndFascHelper);
	}
	return alberoprocProtAndFascHelpers;
    }

    private void dataIntegration(AlberoprocProtocollo entity, boolean isInsert) {

	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(AlberoprocProtocollo entity) {

	Comuni comune = comuniService.bindDomainObject(entity.getComuni(), String.class, "codicecomune");
	entity.setComuni(comune);
	Amministrazioni amministrazioni = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	entity.setAmministrazioni(amministrazioni);
	Alberoproc alberoproc = alberoprocService.bindDomainObject(entity.getAlberoproc(), PkId.class, "id.codice");
	entity.setAlberoproc(alberoproc);
    }

    protected boolean isDeleteAllowed(AlberoprocProtocollo entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    private List<AlberoprocProtocollo> findByAlberoprocAndComune(Integer codiceAlberoproc, String codiceComune) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("alberoprocId", codiceAlberoproc, Integer.class));
	if (StringUtils.isBlank(codiceComune)) {
	    fr.addFilterField(FilterUtils.isNull("codicecomune", "comuni"));
	} else {
	    fr.addFilterField(FilterUtils.equals("codicecomune", codiceComune, "comuni", String.class));
	}
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("comune", "comuni", FunctionsEnum.NVL_FUNCTION, "'AAAAAAAAAAAAAAA'"));
	List<AlberoprocProtocollo> list = alberoprocprotocolloDAO.findByFilterTable(filterTable);
	return list;
    }

    @Override
    public List<AlberoprocProtocollo> findByAlberoprocId(Integer codiceAlberoproc, Integer firstResult, Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("alberoprocId", codiceAlberoproc, Integer.class));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("comune", "comuni", FunctionsEnum.NVL_FUNCTION, "'AAAAAAAAAAAAAAA'"));
	List<AlberoprocProtocollo> list = alberoprocprotocolloDAO.findByFilterTable(filterTable);
	return list;
    }

    @Override
    public Object findProprietaByAlberoprocId(Integer codiceAlberoproc, String propertyName, String codiceComune) {

	return alberoprocprotocolloDAO.findProprietaByAlberoprocId(codiceAlberoproc, propertyName, codiceComune);
    }

    @Override
    public Map<String, AlberoprocProtocollo> findConfigurazioniHelper(Integer codiceAlberoproc) {

	return alberoprocprotocolloDAO.findConfigurazioniHelper(codiceAlberoproc);
    }

    @Override
    public AlberoprocProtocollo findByAlberoprocIdAndComune(String idcomune, Integer codiceAlberoproc, String codiceComune) {

	List<Integer> l = alberoprocService.findGerarchiaNodiPadreInversa(idcomune, codiceAlberoproc, false);
	AlberoprocProtocollo ap = null;
	for (Integer scId : l) {
	    List<AlberoprocProtocollo> aps = this.findByAlberoprocAndComune(scId, codiceComune);
	    for (AlberoprocProtocollo alberoprocProtocollo : aps) {
		ap = alberoprocProtocollo;
	    }
	    if (ap != null) {
		return ap;
	    }
	    aps = this.findByAlberoprocAndComune(scId, null);
	    for (AlberoprocProtocollo alberoprocProtocollo : aps) {
		ap = alberoprocProtocollo;
	    }
	    if (ap != null) {
		return ap;
	    }
	}
	return ap;
    }
}
