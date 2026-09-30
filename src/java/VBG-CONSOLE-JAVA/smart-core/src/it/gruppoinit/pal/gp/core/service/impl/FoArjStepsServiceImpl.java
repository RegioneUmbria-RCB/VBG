package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoArjStepsDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoArjSteps;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsBase;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsParams;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FoArjStepsBaseService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsParamsService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsTestataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class FoArjStepsServiceImpl extends BaseServiceImpl<FoArjSteps, PkId> implements FoArjStepsService {

    private FoArjStepsParamsService foArjStepsParamsService;
    private FoArjStepsBaseService foArjStepsBaseService;
    private FoArjStepsTestataService foArjStepsTestataService;
    private FoArjStepsDAO foarjstepsDAO;

    @Autowired
    public void setFoArjStepsParamsService(FoArjStepsParamsService foArjStepsParamsService) {

	this.foArjStepsParamsService = foArjStepsParamsService;
    }

    @Autowired
    public void setFoArjStepsBaseService(FoArjStepsBaseService foArjStepsBaseService) {

	this.foArjStepsBaseService = foArjStepsBaseService;
    }

    @Autowired
    public void setFoArjStepsTestataService(FoArjStepsTestataService foArjStepsTestataService) {

	this.foArjStepsTestataService = foArjStepsTestataService;
    }

    @Autowired
    public void setFoArjStepsDAO(FoArjStepsDAO foarjstepsDAO) {

	this.foarjstepsDAO = foarjstepsDAO;
    }

    @Override
    protected Class<FoArjSteps> getEntityClass() {

	return FoArjSteps.class;
    }

    @Override
    public List<FoArjSteps> findAll(Integer firstResult, Integer maxResult) {

	return foarjstepsDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoArjSteps entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    foarjstepsDAO.insert(entity);
	}
    }

    @Override
    public FoArjSteps findById(PkId id) {

	return foarjstepsDAO.findById(id);
    }

    @Override
    public void update(FoArjSteps entity) {

	dataIntegration(entity);
	fixMergeEntityProperties(entity);
	if (validateEntity(entity)) {
	    foarjstepsDAO.update(entity);
	}
    }

    private void dataIntegration(FoArjSteps entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'oggetto passato è nullo");
	}
	fixMergeEntityProperties(entity);
	if (entity.getAbilitato() == null) {
	    entity.setAbilitato(Boolean.FALSE);
	}
	if (entity.getOrdine() == null) {
	    entity.setOrdine(findMaxOrdine(entity.getFoArjStepsTestata().getId().getCodice()) + 1);
	}
    }

    @Override
    protected void fixMergeEntityProperties(FoArjSteps entity) {

	FoArjStepsTestata testata = foArjStepsTestataService.bindDomainObject(entity.getFoArjStepsTestata(), PkId.class, "id.codice");
	entity.setFoArjStepsTestata(testata);
	FoArjStepsBase base = foArjStepsBaseService.bindDomainObject(entity.getFoArjStepsBase(), String.class, "nomeStep");
	entity.setFoArjStepsBase(base);
    }

    @Override
    public void delete(FoArjSteps entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    foarjstepsDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(FoArjSteps entity) {

	List<FoArjStepsParams> params = foArjStepsParamsService.findByIdStep(entity.getId().getIdcomune(), entity.getId().getCodice());
	for (FoArjStepsParams foArjStepsParams : params) {
	    foArjStepsParamsService.delete(foArjStepsParams);
	}
    }

    protected boolean isDeleteAllowed(FoArjSteps entity) {

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
    public List<FoArjSteps> findByTestata(String idcomuneTestata, Integer codiceTestata) {

	if (codiceTestata == null) {
	    throw new RuntimeException("il parametro codice testata è nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomuneTestata, "foArjStepsTestata", String.class));
	fr.addFilterField(FilterUtils.equals("foArjStepsTestataId", codiceTestata, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	return foarjstepsDAO.findByFilterTable(ft);
    }

    @Override
    public String verificaConfigurazioneStep(String idComuneTesta, Integer codiceTestata) {

	String result = "";
	Map<String, FoArjStepsBase> stepObbligatori = new HashMap<String, FoArjStepsBase>();
	DynaProperty[] properties = { new DynaProperty("step", FoArjStepsBase.class), new DynaProperty("dipendeDa", String.class),
		new DynaProperty("incompatibileCon", String.class) };
	DynaClass caratteristicheStep = new BasicDynaClass("CaratteristicheStepDC", null, properties);
	Map<String, DynaBean> caratteristicheStepMap = new HashMap<String, DynaBean>();
	List<FoArjStepsBase> stepBases = foArjStepsBaseService.findAll(null, null);
	for (FoArjStepsBase fas : stepBases) {
	    if (BooleanUtils.isTrue(fas.getFlagObbligatorio())) {
		stepObbligatori.put(fas.getNomeStep(), fas);
	    }
	    try {
		DynaBean csBean = caratteristicheStep.newInstance();
		csBean.set("step", fas);
		csBean.set("dipendeDa", fas.getDipendeDaStep());
		csBean.set("incompatibileCon", fas.getIncompatibileConStep());
		caratteristicheStepMap.put(fas.getNomeStep(), csBean);
	    } catch (Exception e) {
		throw new RuntimeException(e);
	    }
	}
	if (stepObbligatori.size() > 0) {
	    boolean found = false;
	    for (Map.Entry<String, FoArjStepsBase> entry : stepObbligatori.entrySet()) {
		List<FoArjSteps> stepObbligatoris = this.findByTestataAndNomeStepBase(idComuneTesta, codiceTestata, entry.getKey());
		if (stepObbligatoris.size() == 0) {
		    found = true;
		    result += "<li>" + entry.getValue().getTitolo() + "(" + entry.getKey() + ")</li>";
		}
	    }
	    if (found) {
		result = "Attenzione! Non sono stati configurati i seguenti step obbligatori: <ol>" + result;
		result += "</ol>";
	    }
	}
	{
	    for (Map.Entry<String, DynaBean> c : caratteristicheStepMap.entrySet()) {
		String erroriMancataDipendenza = "";
		String erroriOrdineDipendenza = "";
		String erroriIncompatibilita = "";
		String nomeStep = c.getKey();
		String dipendeDa = (String) c.getValue().get("dipendeDa");
		String incompatibileCon = (String) c.getValue().get("incompatibileCon");
		List<FoArjSteps> stepOs = this.findByTestataAndNomeStepBase(idComuneTesta, codiceTestata, nomeStep);
		if (stepOs.size() > 0) {
		    FoArjSteps stepThis = stepOs.get(0);
		    // lo step esiste
		    if (StringUtils.isNotBlank(dipendeDa)) {
			// verifico che lo step da cui dipende questo esiste ed è messo come ordine prima di questo
			List<FoArjSteps> stepObbligatoris = this.findByTestataAndNomeStepBase(idComuneTesta, codiceTestata, dipendeDa);
			if (stepObbligatoris.size() == 0) {
			    // non esiste
			    FoArjStepsBase base = foArjStepsBaseService.findById(dipendeDa);
			    erroriMancataDipendenza += "Lo step " + stepThis.getTitolo() + "(" + nomeStep + ") deve essere preceduto da "
				    + base.getTitolo() + "(" + base.getNomeStep() + ")";
			} else {
			    int ordine = stepThis.getOrdine() == null ? 0 : stepThis.getOrdine().intValue();
			    // verifica l'ordine
			    FoArjSteps dipende = stepObbligatoris.get(0);
			    int ordineDipende = dipende.getOrdine() == null ? 0 : dipende.getOrdine().intValue();
			    if (ordine <= ordineDipende) {
				erroriOrdineDipendenza += "Lo step " + stepThis.getTitolo() + "(" + nomeStep + ") non può precedere lo step "
					+ dipende.getTitolo() + "(" + dipendeDa + ")";
			    }
			}
		    }
		    if (StringUtils.isNotBlank(incompatibileCon)) {
			List<FoArjSteps> stepIncompatibilis = this.findByTestataAndNomeStepBase(idComuneTesta, codiceTestata, incompatibileCon);
			if (stepIncompatibilis.size() > 0) {
			    erroriIncompatibilita += "Lo step " + stepThis.getTitolo() + "(" + nomeStep + ") è incompatibile con lo step "
				    + stepIncompatibilis.get(0).getTitolo() + "(" + incompatibileCon + ")";
			}
		    }
		}
		if (StringUtils.isNotBlank(erroriIncompatibilita) || StringUtils.isNotBlank(erroriOrdineDipendenza)
			|| StringUtils.isNotBlank(erroriMancataDipendenza)) {
		    result += "<li>Step: " + ((FoArjStepsBase) c.getValue().get("step")).getTitolo() + "(" + nomeStep + ")";
		    result += "<ol>";
		    if (StringUtils.isNotBlank(erroriIncompatibilita)) {
			result += "<li>" + erroriIncompatibilita + "</li>";
		    }
		    if (StringUtils.isNotBlank(erroriMancataDipendenza)) {
			result += "<li>" + erroriMancataDipendenza + "</li>";
		    }
		    if (StringUtils.isNotBlank(erroriOrdineDipendenza)) {
			result += "<li>" + erroriOrdineDipendenza + "</li>";
		    }
		    result += "</ol></li>";
		}
	    }
	}
	if (StringUtils.isNotBlank(result)) {
	    result = "Riportate le seguenti anomalie di configurazione: <ul>" + result + "</ul>";
	}
	return result;
    }

    @Override
    public List<FoArjSteps> findByTestataAndNomeStepBase(String idComuneTestata, Integer codiceTestata, String nomeStepBase) {

	if (codiceTestata == null) {
	    throw new RuntimeException("il parametro codice testata è nullo");
	}
	if (StringUtils.isBlank(nomeStepBase)) {
	    throw new RuntimeException("il parametro nomeStepBase è vuoto o nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idComuneTestata, "foArjStepsTestata", String.class));
	fr.addFilterField(FilterUtils.equals("foArjStepsTestataId", codiceTestata, Integer.class));
	fr.addFilterField(FilterUtils.equals("nomeStep", nomeStepBase, "foArjStepsBase", String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	return foarjstepsDAO.findByFilterTable(ft);
    }

    @Override
    public void updateStepSpostaOrdine(String idcomune, Integer codiceTestata, Integer codiceStep, boolean isUp) {

	FoArjSteps step = this.findById(new PkId(codiceStep));
	List<FoArjSteps> listaStep = findByTestata(idcomune, codiceTestata);
	List<FoArjSteps> listaStepO = new ArrayList<FoArjSteps>();
	int i = 0;
	int pos = 0;
	for (FoArjSteps foArjSteps : listaStep) {
	    listaStepO.add(i, foArjSteps);
	    if (codiceStep.intValue() == foArjSteps.getId().getCodice().intValue()) {
		pos = i;
	    }
	    i++;
	}
	FoArjSteps prevNext = null;
	if (!isUp) {
	    prevNext = listaStepO.get(pos - 1);
	    listaStepO.set(pos - 1, step);
	    listaStepO.set(pos, prevNext);
	} else {
	    prevNext = listaStepO.get(pos + 1);
	    listaStepO.set(pos + 1, step);
	    listaStepO.set(pos, prevNext);
	}
	i = 0;
	for (FoArjSteps foArjSteps : listaStepO) {
	    foArjSteps.setOrdine(i);
	    this.update(foArjSteps);
	    i++;
	}
    }

    private int findMaxOrdine(Integer codiceTestata) {

	if (codiceTestata == null) {
	    throw new RuntimeException("il parametro codice testata è nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("foArjStepsTestataId", codiceTestata, Integer.class));
	Object max = foarjstepsDAO.max(ft, "ordine");
	if (max == null) {
	    return 0;
	}
	return (Integer) max;
    }

    @Override
    public List<FoArjSteps> findAttivi(String idcomuneTestata, Integer codiceTestata) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction pk = new FilterRestriction();
	pk.addFilterField(FilterUtils.equals("id.idcomune", idcomuneTestata, "foArjStepsTestata", String.class));
	pk.addFilterField(FilterUtils.equals("foArjStepsTestataId", codiceTestata, Integer.class));
	ft.addRestriction(pk);
	FilterRestriction attivi = new FilterRestriction();
	attivi.addFilterField(FilterUtils.equals("abilitato", Boolean.TRUE, Boolean.class));
	ft.addRestriction(attivi);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	List<FoArjSteps> list = foarjstepsDAO.findByFilterTable(ft);
	return list;
    }
}
