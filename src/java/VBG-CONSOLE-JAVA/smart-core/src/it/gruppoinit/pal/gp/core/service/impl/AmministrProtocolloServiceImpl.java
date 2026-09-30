package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AmministrProtocolloDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AmministrProtocollo;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.AmministrProtocolloHelper;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AmministrProtocolloService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.ArrayList;
import java.util.HashMap;
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
public class AmministrProtocolloServiceImpl extends BaseServiceImpl<AmministrProtocollo, PkId> implements AmministrProtocolloService {

    private AmministrProtocolloDAO amministrprotocolloDAO;
    private AmministrazioniService amministrazioniService;
    private ComuniService comuniService;
    private ResponsabiliService responsabiliService;
    private SoftwareService softwareService;

    @Autowired
    public void setAmministrProtocolloDAO(AmministrProtocolloDAO amministrprotocolloDAO) {

	this.amministrprotocolloDAO = amministrprotocolloDAO;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Override
    protected Class<AmministrProtocollo> getEntityClass() {

	return AmministrProtocollo.class;
    }

    @Override
    public List<AmministrProtocollo> findAll(Integer firstResult, Integer maxResult) {

	return amministrprotocolloDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AmministrProtocollo entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity) && insertAllowed(entity)) {
	    amministrprotocolloDAO.insert(entity);
	}
    }

    private boolean insertAllowed(AmministrProtocollo entity) {

	boolean isInsert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	String codiceComune = null;
	if (entity.getComuni() != null) {
	    if (StringUtils.isNotBlank(entity.getComuni().getCodicecomune())) {
		codiceComune = entity.getComuni().getCodicecomune();
	    }
	}
	List<AmministrProtocollo> list = this.findByAmministrazioneComuneESoftware(entity.getAmministrazioni().getId().getCodice(), codiceComune,
		entity.getSoftware().getCodice(), true);
	if (list.size() > 0) {
	    _ivs.add(new InvalidValue("service_error.configurazione_esistente_duplicata", null, null, null, null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return isInsert;
    }

    private void dataIntegration(AmministrProtocollo entity, boolean isInsert) {

	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(AmministrProtocollo entity) {

	Comuni comune = comuniService.bindDomainObject(entity.getComuni(), String.class, "codicecomune");
	entity.setComuni(comune);
	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
	Amministrazioni amministrazioni = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	entity.setAmministrazioni(amministrazioni);
    }

    @Override
    public AmministrProtocollo findById(PkId id) {

	return amministrprotocolloDAO.findById(id);
    }

    @Override
    public void update(AmministrProtocollo entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    amministrprotocolloDAO.update(entity);
	}
    }

    @Override
    public void delete(AmministrProtocollo entity) {

	if (isDeleteAllowed(entity)) {
	    amministrprotocolloDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(AmministrProtocollo entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		TODO_validare_la_delete
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
    public List<AmministrProtocolloHelper> findByComuniAndSoftwarePerOperatore(Integer codiceAmministrazione, Responsabili responsabile) {

	List<AmministrProtocolloHelper> amministrProtocolloHelpers = new ArrayList<AmministrProtocolloHelper>();
	AmministrProtocolloHelper amministrProtocolloHelper = null;
	Set<Responsabilicomuni> responsabilicomunis = responsabiliService.findListResponsabilicomuni(responsabile);
	Set<Responsabilisoftware> responsabilisoftwares = responsabiliService.findListResponsabilisoftware(responsabile);
	List<String> listcodicesoftware = new ArrayList<String>();
	for (Responsabilisoftware responsabilisoftware : responsabilisoftwares) {
	    listcodicesoftware.add(responsabilisoftware.getSoftware().getCodice());
	}
	for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
	    String codicecomune = responsabilicomuni.getComune().getCodicecomune();
	    amministrProtocolloHelper = new AmministrProtocolloHelper();
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("codicecomune", codicecomune, "comuni", String.class));
	    fr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazione, Integer.class));
	    fr.addFilterField(FilterUtils.in("codice", listcodicesoftware.toArray(), "software", String.class));
	    filterTable.addRestriction(fr);
	    List<AmministrProtocollo> amministrProtocollos = amministrprotocolloDAO.findByFilterTable(filterTable);
	    if (amministrProtocollos.size() > 0) {
		amministrProtocolloHelper.setComune(responsabilicomuni.getComune().getComune());
		amministrProtocolloHelper.setAmministrProtocollos(amministrProtocollos);
		amministrProtocolloHelpers.add(amministrProtocolloHelper);
	    }
	}
	amministrProtocolloHelper = new AmministrProtocolloHelper();
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.isNull("codicecomune", "comuni"));
	fr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazione, Integer.class));
	fr.addFilterField(FilterUtils.in("codice", listcodicesoftware.toArray(), "software", String.class));
	filterTable.addRestriction(fr);
	List<AmministrProtocollo> amministrProtocollos = amministrprotocolloDAO.findByFilterTable(filterTable);
	if (amministrProtocollos.size() > 0) {
	    amministrProtocolloHelper.setComune(getMessageFromBundle("label.tutti", new Object[] {}));
	    amministrProtocolloHelper.setAmministrProtocollos(amministrProtocollos);
	    amministrProtocolloHelpers.add(amministrProtocolloHelper);
	}
	return amministrProtocolloHelpers;
    }

    private List<AmministrProtocollo> findByAmministrazioneComuneESoftware(Integer codiceAmministrazione, String codiceComune, String software,
	    boolean exact) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (StringUtils.isBlank(codiceComune)) {
	    fr.addFilterField(FilterUtils.isNull("codicecomune", "comuni"));
	} else {
	    if (!exact) {
		FilterRestriction orComuni = new FilterRestriction();
		orComuni.setAndOrRestriction(AndOrRestriction.OR);
		orComuni.addFilterField(FilterUtils.isNull("codicecomune", "comuni"));
		orComuni.addFilterField(FilterUtils.equals("codicecomune", codiceComune, "comuni", String.class));
		filterTable.addRestriction(orComuni);
	    } else {
		fr.addFilterField(FilterUtils.equals("codicecomune", codiceComune, "comuni", String.class));
	    }
	}
	fr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazione, Integer.class));
	if (!exact) {
	    fr.addFilterField(FilterUtils.in("codice", new String[] { WebConstants.SOFTWARE_TT, software }, "software", String.class));
	} else {
	    fr.addFilterField(FilterUtils.equals("codice", software, "software", String.class));
	}
	filterTable.addRestriction(fr);
	List<AmministrProtocollo> list = amministrprotocolloDAO.findByFilterTable(filterTable);
	return list;
    }

    @Override
    public AmministrProtocollo findByAmministrazioneComuneESoftware(Integer codiceAmministrazione, String codiceComune, String software) {

	List<AmministrProtocollo> list = this.findByAmministrazioneComuneESoftware(codiceAmministrazione, codiceComune, software, false);
	codiceComune = StringUtils.defaultString(codiceComune, "TUTTI");
	software = StringUtils.defaultString(software, WebConstants.SOFTWARE_TT);
	if (list != null && list.size() > 0) {
	    if (list.size() == 1) {
		return list.get(0);
	    } else {
		String key = codiceComune + "-" + software;
		String keyTT = codiceComune + "-" + WebConstants.SOFTWARE_TT;
		String keyTTSoft = "TUTTI" + "-" + software;
		String keyTTeTT = "TUTTI" + "-" + WebConstants.SOFTWARE_TT;
		Map<String, AmministrProtocollo> m = new HashMap<String, AmministrProtocollo>();
		for (AmministrProtocollo v : list) {
		    String kloc = (v.getComuni() == null ? "TUTTI" : v.getComuni().getCodicecomune()) + "-" + v.getSoftware().getCodice();
		    m.put(kloc, v);
		}
		if (m.get(key) != null) {
		    return m.get(key);
		} else {
		    if (m.get(keyTT) != null) {
			return m.get(keyTT);
		    }
		    if (m.get(keyTTSoft) != null) {
			return m.get(keyTTSoft);
		    }
		    return m.get(keyTTeTT);
		}
	    }
	}
	return null;
    }

    @Override
    public int countByAmministrazione(Integer codiceAmministrazione) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazione, Integer.class));
	filterTable.addRestriction(fr);
	return amministrprotocolloDAO.countRecord(filterTable);
    }
}
