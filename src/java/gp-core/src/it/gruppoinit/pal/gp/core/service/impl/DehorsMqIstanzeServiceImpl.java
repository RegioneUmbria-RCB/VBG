package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.DehorsMqIstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.DehorsLog;
import it.gruppoinit.pal.gp.core.domain.DehorsMqIstanze;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DehorsMqIstanzeHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniCommand;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniSubentriCommand;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.DehorsLogService;
import it.gruppoinit.pal.gp.core.service.DehorsMqIstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class DehorsMqIstanzeServiceImpl extends BaseServiceImpl<DehorsMqIstanze, PkId> implements DehorsMqIstanzeService {

    private DehorsMqIstanzeDAO dehorsmqistanzeDAO;
    private DehorsLogService dehorsLogService;
    private AutorizzazioniService autorizzazioniService;
    private IstanzeService istanzeService;

    @Autowired
    public void setDehorsMqIstanzeDAO(DehorsMqIstanzeDAO dehorsmqistanzeDAO) {

	this.dehorsmqistanzeDAO = dehorsmqistanzeDAO;
    }

    @Autowired
    public void setDehorsLogService(DehorsLogService dehorsLogService) {

	this.dehorsLogService = dehorsLogService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Override
    protected Class<DehorsMqIstanze> getEntityClass() {

	return DehorsMqIstanze.class;
    }

    @Override
    public List<DehorsMqIstanze> findAll(Integer firstResult, Integer maxResult) {

	return dehorsmqistanzeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(DehorsMqIstanze entity) {

	dataIntegration(entity);
	if (isInsertAllowed(entity) && validateEntity(entity)) {
	    dehorsmqistanzeDAO.insert(entity);
	}
    }

    @Override
    public void insertPerSubentro(DehorsMqIstanze entity, DehorsMqIstanze dehorsMqIstanzePrecedente) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertDehorsSubentroAllowed(entity, dehorsMqIstanzePrecedente)) {
	    dehorsmqistanzeDAO.insert(entity);
	}
    }

    private boolean isInsertDehorsSubentroAllowed(DehorsMqIstanze entity, DehorsMqIstanze dehorsMqIstanzePrecedente) {

	//	DehorsMqIstanze dehorsMqIstanzeBD = new DehorsMqIstanze();
	//	this.prepopulateDehorsMqAutorizzazione(dehorsMqIstanzeBD, entity.getAutorizzazioni().getId().getCodice());
	DehorsMqIstanzeHelper dehorsMqIstanzeHelper = this.findDehorsMqIstanzeHelper(entity.getAree().getId().getCodice());
	// dai disponibili devo togliere quelli che già sono assegnata a questa autorizzazione, il record è già associato
	// ad un autorizzazione, nel conteggio dei disponibili non devo considerare quelli già associati
	if (dehorsMqIstanzePrecedente.getCessata() == false) {
	    // significa che nel calcolo dei dispsonibili è stato considerato anche il valore assegnato precedentemente
	    // lo dovremo aggiungere ai disponibili
	    dehorsMqIstanzeHelper.setDisponibili(dehorsMqIstanzeHelper.getDisponibili().add(dehorsMqIstanzePrecedente.getMqassegnati()));
	}
	// faccio la sottrazione tra quelli disponibili e quelli assegnati (quelli che ho passato 
	// dal form come da assegnare), dovra essere maggiore>=0
	if (dehorsMqIstanzeHelper.getDisponibili().subtract(entity.getMqassegnati()).doubleValue() < 0) {
	    this.throwValidationMessage(new InvalidValue("autorizzazioni.service_error.superato_numero_mq_assegnabili", null, null, "", null));
	}
	///
	return true;
    }

    @Override
    public DehorsMqIstanze findById(PkId id) {

	return dehorsmqistanzeDAO.findById(id);
    }

    @Override
    public void update(DehorsMqIstanze entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    dehorsmqistanzeDAO.update(entity);
	}
    }

    @Override
    public void update(DehorsMqIstanze entity, BigDecimal mqIniziali) {

	if (isUpdateAllowed(entity, mqIniziali)) {
	    this.update(entity);
	    //calcola variazione mq
	    AutorizzazioniCommand autorizzazioniCommand = new AutorizzazioniCommand();
	    autorizzazioniCommand.setEntity(entity.getAutorizzazioni());
	    autorizzazioniCommand.getEntity().setIstanza(entity.getIstanze());
	    autorizzazioniCommand.setDehorsMqIstanze(entity);
	    BigDecimal mqvariati = entity.getMqassegnati().subtract(mqIniziali);
	    DehorsLog dehorsLog = dehorsLogService.populateDehorsLog(autorizzazioniCommand, mqIniziali, mqvariati, "Modificati valori mq dehors");
	    dehorsLogService.insert(dehorsLog);
	}
    }

    @Override
    public void insert(DehorsMqIstanze entity, BigDecimal mqIniziali) {

	if (isInsertAllowed(entity)) {
	    this.insert(entity);
	    //calcola variazione mq
	    AutorizzazioniCommand autorizzazioniCommand = new AutorizzazioniCommand();
	    autorizzazioniCommand.setEntity(entity.getAutorizzazioni());
	    autorizzazioniCommand.getEntity().setIstanza(entity.getIstanze());
	    autorizzazioniCommand.setDehorsMqIstanze(entity);
	    BigDecimal mqvariati = entity.getMqassegnati().subtract(mqIniziali);
	    DehorsLog dehorsLog = dehorsLogService.populateDehorsLog(autorizzazioniCommand, mqIniziali, mqvariati, "Modificati valori mq dehors");
	    dehorsLogService.insert(dehorsLog);
	}
    }

    private boolean isInsertAllowed(DehorsMqIstanze entity) {

	// prima controllo che tutti i dati sono stati inseriti
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getMqrichiesti() == null) {
	    _ivs.add(new InvalidValue("dehors.service_error.mq_richiesti_obbligatorio", null, null, "", null));
	}
	if (entity.getMqassegnati() == null) {
	    _ivs.add(new InvalidValue("dehors.service_error.mq_assegnati_obbligatorio", null, null, "", null));
	}
	if (EntityUtils.getNestedProperty(entity.getAree(), "id.codice") == null) {
	    _ivs.add(new InvalidValue("dehors.service_error.area_obbligatorio", null, null, "", null));
	}
	if (entity.getMqassegnati().doubleValue() > entity.getMqrichiesti().doubleValue()) {
	    _ivs.add(new InvalidValue("dehors.service_error.mq_assegnati_maggiore_richiesti", null, null, "", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	DehorsMqIstanzeHelper dehorsMqIstanzeHelper = this.findDehorsMqIstanzeHelper(entity.getAree().getId().getCodice());
	// dai disponibili devo togliere quelli che già sono assegnata a questa autorizzazione, il record è già associato
	// ad un autorizzazione, nel conteggio dei disponibili non devo considerare quelli già associati
	dehorsMqIstanzeHelper.setDisponibili(dehorsMqIstanzeHelper.getDisponibili());
	// faccio la sottrazione tra quelli disponibili e quelli assegnati (quelli che ho passato 
	// dal form come da assegnare), dovra essere maggiore>=0
	if (dehorsMqIstanzeHelper.getDisponibili().subtract(entity.getMqassegnati()).doubleValue() < 0) {
	    this.throwValidationMessage(new InvalidValue("autorizzazioni.service_error.superato_numero_mq_assegnabili", null, null, "", null));
	}
	///
	return true;
    }

    private boolean isUpdateAllowed(DehorsMqIstanze entity, BigDecimal mqIniziali) {

	DehorsMqIstanzeHelper dehorsMqIstanzeHelper = this.findDehorsMqIstanzeHelper(entity.getAree().getId().getCodice());
	// dai disponibili devo togliere quelli che già sono assegnata a questa autorizzazione, il record è già associato
	// ad un autorizzazione, nel conteggio dei disponibili non devo considerare quelli già associati
	dehorsMqIstanzeHelper.setDisponibili(dehorsMqIstanzeHelper.getDisponibili().add(mqIniziali));
	// faccio la sottrazione tra quelli disponibili e quelli assegnati (quelli che ho passato 
	// dal form come da assegnare), dovra essere maggiore>=0
	if (dehorsMqIstanzeHelper.getDisponibili().subtract(entity.getMqassegnati()).doubleValue() < 0) {
	    this.throwValidationMessage(new InvalidValue("autorizzazioni.service_error.superato_numero_mq_assegnabili", null, null, "", null));
	}
	if (entity.getMqassegnati().doubleValue() > entity.getMqrichiesti().doubleValue()) {
	    this.throwValidationMessage(new InvalidValue("dehors.service_error.mq_assegnati_maggiore_richiesti", null, null, "", null));
	}
	///
	return true;
    }

    @Override
    public void delete(DehorsMqIstanze entity) {

	if (isDeleteAllowed(entity)) {
	    dehorsmqistanzeDAO.delete(entity);
	    AutorizzazioniCommand autorizzazioniCommand = new AutorizzazioniCommand();
	    autorizzazioniCommand.setEntity(entity.getAutorizzazioni());
	    DehorsLog dehorsLog = dehorsLogService.populateDehorsLog(autorizzazioniCommand, entity.getMqassegnati(), entity.getMqassegnati(),
		    "Eliminati valori dehors ");
	    dehorsLogService.insert(dehorsLog);
	}
    }

    @Override
    public List<DehorsMqIstanze> findByIstanza(Integer codicesistanza, boolean isAutorizzazioneNull) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codicesistanza, "istanze", Integer.class));
	if (isAutorizzazioneNull) {
	    fr.addFilterField(FilterUtils.isNull("id.codice", "autorizzazioni"));
	} else {
	    fr.addFilterField(FilterUtils.isNotNull("id.codice", "autorizzazioni"));
	}
	ft.addRestriction(fr);
	return dehorsmqistanzeDAO.findByFilterTable(ft);
    }

    @Override
    public void prepopulateDehorsMqIstanze(DehorsMqIstanze dehorsMqIstanze, Integer codiceIstanza) {

	List<DehorsMqIstanze> dehorsMqIstanzes = this.findByIstanza(codiceIstanza, true);
	if (!dehorsMqIstanzes.isEmpty()) {
	    DehorsMqIstanze dehorsMqIstanzeDB = dehorsMqIstanzes.get(0);
	    if (EntityUtils.getNestedProperty(dehorsMqIstanzeDB.getAree(), "id.codice") != null) {
		dehorsMqIstanze.setAree(dehorsMqIstanzeDB.getAree());
	    }
	    dehorsMqIstanze.setMqassegnati(dehorsMqIstanzeDB.getMqassegnati());
	    dehorsMqIstanze.setMqrichiesti(dehorsMqIstanzeDB.getMqrichiesti());
	    dehorsMqIstanze.setCessata(dehorsMqIstanzeDB.getCessata());
	}
    }

    @Override
    public void prepopulateDehorsMqAutorizzazione(DehorsMqIstanze dehorsMqIstanze, Integer codiceAut) {

	DehorsMqIstanze dehorsMqIstanzeDB = this.findAttiveByAutorizzazione(codiceAut);
	if (EntityUtils.getNestedProperty(dehorsMqIstanzeDB.getAree(), "id.codice") != null) {
	    dehorsMqIstanze.setAree(dehorsMqIstanzeDB.getAree());
	}
	dehorsMqIstanze.setMqassegnati(dehorsMqIstanzeDB.getMqassegnati());
	dehorsMqIstanze.setMqrichiesti(dehorsMqIstanzeDB.getMqrichiesti());
	dehorsMqIstanze.setCessata(dehorsMqIstanzeDB.getCessata());
    }

    @Override
    public DehorsMqIstanzeHelper findDehorsMqIstanzeHelper(Integer codiceArea) {

	return dehorsmqistanzeDAO.findDehorsMqIstanzeHelper(codiceArea);
    }

    @Override
    public DehorsMqIstanze findAttiveByAutorizzazione(Integer codiceAut) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAut, "autorizzazioni", Integer.class));
	fr.addFilterField(FilterUtils.equals("cessata", false, Boolean.class));
	ft.addRestriction(fr);
	List<DehorsMqIstanze> list = dehorsmqistanzeDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public List<DehorsMqIstanze> findCessateByAutorizzazione(Integer codiceAut) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAut, "autorizzazioni", Integer.class));
	fr.addFilterField(FilterUtils.equals("cessata", true, Boolean.class));
	ft.addRestriction(fr);
	List<DehorsMqIstanze> list = dehorsmqistanzeDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public Map<String, Object> preViewInsertSubentriDehors(AutorizzazioniSubentriCommand autorizzazioniSubentriCommand) {

	Map<String, Object> map = new HashMap<String, Object>();
	String page = "";
	Integer codiceAut = autorizzazioniSubentriCommand.getListAutDaSubentrare().iterator().next().getAutorizzazione().getId().getCodice();
	DehorsMqIstanze dehorsMqIstanze = this.findAttiveByAutorizzazione(codiceAut);
	//	    model.addAttribute("codiceAut", codiceAut);
	// configuro per la visualizzazione della situazione precedente
	DehorsMqIstanze dehorsMqIstanzeNuova = autorizzazioniSubentriCommand.getDehorsMqIstanzeNuova();
	if (dehorsMqIstanze != null) {
	    autorizzazioniSubentriCommand.setDehorsMqIstanzePrecendete(dehorsMqIstanze);
	    if (EntityUtils.getNestedProperty(dehorsMqIstanzeNuova.getAree(), "id.codice") == null) {
		dehorsMqIstanzeNuova.setAree(dehorsMqIstanze.getAree());
	    }
	    dehorsMqIstanzeNuova.setAutorizzazioni(dehorsMqIstanze.getAutorizzazioni());
	    dehorsMqIstanzeNuova.setCessata(false);
	    dehorsMqIstanzeNuova.setIstanze(dehorsMqIstanze.getIstanze());
	    if (dehorsMqIstanzeNuova.getMqassegnati() == null) {
		dehorsMqIstanzeNuova.setMqassegnati(dehorsMqIstanze.getMqassegnati());
	    }
	    if (dehorsMqIstanzeNuova.getMqrichiesti() == null) {
		dehorsMqIstanzeNuova.setMqrichiesti(dehorsMqIstanze.getMqrichiesti());
	    }
	    autorizzazioniSubentriCommand.setDehorsMqIstanzeNuova(dehorsMqIstanzeNuova);
	} else {
	    List<DehorsMqIstanze> dehorsMqIstanzeCessate = this.findCessateByAutorizzazione(codiceAut);
	    if (!dehorsMqIstanzeCessate.isEmpty() && dehorsMqIstanzeCessate.size() == 1) {
		if (EntityUtils.getNestedProperty(dehorsMqIstanzeCessate.get(0).getAree(), "id.codice") != null) {
		    dehorsMqIstanzeNuova.setAree(dehorsMqIstanzeCessate.get(0).getAree());
		}
		dehorsMqIstanzeNuova.setAutorizzazioni(dehorsMqIstanzeCessate.get(0).getAutorizzazioni());
		dehorsMqIstanzeNuova.setCessata(dehorsMqIstanzeCessate.get(0).getCessata());
		dehorsMqIstanzeNuova.setIstanze(dehorsMqIstanzeCessate.get(0).getIstanze());
		if (dehorsMqIstanzeNuova.getMqassegnati() == null) {
		    dehorsMqIstanzeNuova.setMqassegnati(dehorsMqIstanzeCessate.get(0).getMqassegnati());
		}
		if (dehorsMqIstanzeNuova.getMqrichiesti() == null) {
		    dehorsMqIstanzeNuova.setMqrichiesti(dehorsMqIstanzeCessate.get(0).getMqrichiesti());
		}
		autorizzazioniSubentriCommand.setDehorsMqIstanzePrecendete(dehorsMqIstanzeCessate.get(0));
	    } else {
		autorizzazioniSubentriCommand.setDehorsMqIstanzePrecendete(new DehorsMqIstanze());
	    }
	}
	page = "autorizzazionisubentri/datiDehorsSubentri";
	map.put("page", (String) page);
	map.put("codiceAut", (Integer) codiceAut);
	map.put("autorizzazioniSubentriCommand", (AutorizzazioniSubentriCommand) autorizzazioniSubentriCommand);
	return map;
    }

    private void dataIntegration(DehorsMqIstanze entity) {

	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(DehorsMqIstanze entity) {

	Autorizzazioni autorizzazioni = autorizzazioniService.bindDomainObject(entity.getAutorizzazioni(), PkId.class, "id.codice");
	entity.setAutorizzazioni(autorizzazioni);
	Istanze istanze = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanze);
    }
    //    protected boolean isDeleteAllowed(DehorsMqIstanze entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
}
