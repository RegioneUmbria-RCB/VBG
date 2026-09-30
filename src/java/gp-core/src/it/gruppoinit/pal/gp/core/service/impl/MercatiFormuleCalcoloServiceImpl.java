package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.MercatiFormuleCalcoloDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiContabilitaTributi;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiFormuleCalcoloContestoEnum;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiFormuleCalcoloHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.LivelloServizioHelper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.TokenizerFormuleFactory;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.ValidazioneFormulaFallitaException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleBattitore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleCoefficienteMercato;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleConcessionario;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleGG;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleGGPres;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleSpuntista;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.LivelloServizioService;
import it.gruppoinit.pal.gp.core.service.MercatiContabilitaTributiService;
import it.gruppoinit.pal.gp.core.service.MercatiFormuleCalcoloService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;

/**
 * 
 * @author
 */
@Service
public class MercatiFormuleCalcoloServiceImpl extends BaseServiceImpl<MercatiFormuleCalcolo, PkId> implements MercatiFormuleCalcoloService {

    private MercatiFormuleCalcoloDAO mercatiformulecalcoloDAO;
    private MercatiUsoService mercatiUsoService;
    private MercatiService mercatiService;
    private MercatiContabilitaTributiService mercatiContabilitaTributiService;
    private LivelloServizioService livelloServizioService;
    private IVerticalizzazioneComportamentiMercatiService verticalizzazioneComportamentiMercatiService;
    private static final String SegnapostoGG = SegnapostoFormuleGG.SEGNAPOSTO;
    private static final String SegnapostoGGDescrizione = SegnapostoFormuleGG.DESCRIZIONE;
    private static final String SegnapostoGGPres = SegnapostoFormuleGGPres.SEGNAPOSTO;
    private static final String SegnapostoGGPresDescrizione = SegnapostoFormuleGGPres.DESCRIZIONE;
    private static final String SegnapostoGGPresOrNonGius = SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata.SEGNAPOSTO;
    private static final String SegnapostoGGPresOrNonGiusDescrizione = SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata.DESCRIZIONE;
    private static final String SegnapostoConcessionario = SegnapostoFormuleConcessionario.SEGNAPOSTO;
    private static final String SegnapostoConcessionarioDescrizione = SegnapostoFormuleConcessionario.DESCRIZIONE;
    private static final String SegnapostoSpuntista = SegnapostoFormuleSpuntista.SEGNAPOSTO;
    private static final String SegnapostoSpuntistaDescrizione = SegnapostoFormuleSpuntista.DESCRIZIONE;
    private static final String SegnapostoCoefficienteMercato = SegnapostoFormuleCoefficienteMercato.SEGNAPOSTO;
    private static final String SegnapostoCoefficienteMercatoDescrizione = SegnapostoFormuleCoefficienteMercato.DESCRIZIONE;
    private static final String SegnapostoBattitore = SegnapostoFormuleBattitore.SEGNAPOSTO;
    private static final String SegnapostoBattitoreDescrizione = SegnapostoFormuleBattitore.DESCRIZIONE;

    @Autowired
    public void setVerticalizzazioneComportamentiMercatiService(
	    IVerticalizzazioneComportamentiMercatiService verticalizzazioneComportamentiMercatiService) {

	this.verticalizzazioneComportamentiMercatiService = verticalizzazioneComportamentiMercatiService;
    }

    @Autowired
    public void setLivelloServizioService(LivelloServizioService livelloServizioService) {

	this.livelloServizioService = livelloServizioService;
    }

    @Autowired
    public void setMercatiContabilitaTributiService(MercatiContabilitaTributiService mercatiContabilitaTributiService) {

	this.mercatiContabilitaTributiService = mercatiContabilitaTributiService;
    }

    @Autowired
    public void setMercatiFormuleCalcoloDAO(MercatiFormuleCalcoloDAO mercatiformulecalcoloDAO) {

	this.mercatiformulecalcoloDAO = mercatiformulecalcoloDAO;
    }

    @Autowired
    public void setMercatiUsoService(MercatiUsoService mercatiUsoService) {

	this.mercatiUsoService = mercatiUsoService;
    }

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Override
    protected Class<MercatiFormuleCalcolo> getEntityClass() {

	return MercatiFormuleCalcolo.class;
    }

    @Override
    public List<MercatiFormuleCalcolo> findAll(Integer firstResult, Integer maxResult) {

	return mercatiformulecalcoloDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(MercatiFormuleCalcolo entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity) && isInserOrUpdateAllowed(entity)) {
	    mercatiformulecalcoloDAO.insert(entity);
	}
    }

    @Override
    public void insert(MercatiFormuleCalcolo entity, MercatiContabilitaTributi mercatiContabilitaTributi) {

	this.insert(entity);
	if (EntityUtils.getNestedProperty(mercatiContabilitaTributi.getConti(), "id.codice") != null) {
	    mercatiContabilitaTributi.setMercatiFormuleCalcolo(entity);
	    mercatiContabilitaTributi.setDataFineValidita(entity.getDataFineValidita());
	    mercatiContabilitaTributi.setDataInizioValidita(entity.getDataInizioValidita());
	    mercatiContabilitaTributiService.insert(mercatiContabilitaTributi);
	}
    }

    @Override
    public MercatiFormuleCalcolo findById(PkId id) {

	return mercatiformulecalcoloDAO.findById(id);
    }

    @Override
    public void update(MercatiFormuleCalcolo entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity) && isInserOrUpdateAllowed(entity)) {
	    mercatiformulecalcoloDAO.update(entity);
	}
    }

    @Override
    public void delete(MercatiFormuleCalcolo entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    mercatiformulecalcoloDAO.delete(entity);
	}
    }

    @Override
    public List<MercatiFormuleCalcolo> findByUso(Integer codiceuso) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceuso, "mercatiUso", Integer.class));
	ft.addRestriction(fr);
	return mercatiformulecalcoloDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatiFormuleCalcoloHelper> findByMecato(Integer codicemercato) {

	List<MercatiFormuleCalcoloHelper> r = new ArrayList<MercatiFormuleCalcoloHelper>();
	MercatiFormuleCalcoloHelper helper = null;
	Mercati m = mercatiService.findById(new PkId(codicemercato));
	List<MercatiUso> mercatiUsos = mercatiUsoService.findByMercato(m);
	for (MercatiUso mercatiUso : mercatiUsos) {
	    helper = new MercatiFormuleCalcoloHelper();
	    List<MercatiFormuleCalcolo> mercatiFormuleCalcolos = this.findByUso(mercatiUso.getId().getCodice());
	    helper.setMercatiUso(mercatiUso);
	    helper.setMercatiFormuleCalcolos(mercatiFormuleCalcolos);
	    r.add(helper);
	}
	return r;
    }

    @Override
    public List<MercatiFormuleCalcoloHelper> findByMecatoUso(Integer codiceuso) {

	List<MercatiFormuleCalcoloHelper> r = new ArrayList<MercatiFormuleCalcoloHelper>();
	MercatiFormuleCalcoloHelper helper = new MercatiFormuleCalcoloHelper();
	MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceuso));
	List<MercatiFormuleCalcolo> mercatiFormuleCalcolos = this.findByUso(codiceuso);
	helper.setMercatiUso(mercatiUso);
	helper.setMercatiFormuleCalcolos(mercatiFormuleCalcolos);
	r.add(helper);
	return r;
    }

    private void dataIntegration(MercatiFormuleCalcolo entity, boolean isUpdate) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro ComunicazioniT da validare è nullo");
	}
	if (entity.getContesto() == null) {
	    entity.setContesto(MercatiFormuleCalcoloContestoEnum.PRESENZA);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(MercatiFormuleCalcolo entity) {

    }

    private boolean isInserOrUpdateAllowed(MercatiFormuleCalcolo entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getDataFineValidita() != null) {
	    if (entity.getDataInizioValidita().after(entity.getDataFineValidita())) {
		String mess = getMessageFromBundle("service_error_data_fine_antecendente", null);
		_ivs.add(new InvalidValue(mess, MercatiFormuleCalcolo.class, "dataFineValidita", entity, new MercatiFormuleCalcolo()));
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }

    @Override
    protected void childDelete(MercatiFormuleCalcolo entity) {

	List<MercatiContabilitaTributi> mercatiContabilitaTributis = mercatiContabilitaTributiService.findByFormula(entity.getId().getCodice());
	if (!mercatiContabilitaTributis.isEmpty()) {
	    for (MercatiContabilitaTributi mercatiContabilitaTributi : mercatiContabilitaTributis) {
		mercatiContabilitaTributiService.delete(mercatiContabilitaTributi);
	    }
	}
    }

    protected boolean isDeleteAllowed(MercatiFormuleCalcolo entity) {

	boolean delete = true;
	//		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		TODO_validare_la_delete
	//		// esempio:
	//		// if (entity.getList().size() > 0) {
	//		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//		// }
	//		if (!_ivs.isEmpty()) {
	//			this.throwValidationMessages(_ivs);
	//		}
	return delete;
    }

    @Override
    public void verificaFormulaCalcolo(Integer idUso, String formula) {

	List<LivelloServizioHelper> livelli = findLivelliAttiviByMercatoUso(idUso);
	List<String> variabiliAmmesse = new ArrayList<String>(0);
	for (LivelloServizioHelper livello : livelli) {
	    variabiliAmmesse.add(livello.getSegnaposto());
	}
	TokenizerFormuleFactory tokenizerFactory = new TokenizerFormuleFactory(variabiliAmmesse);
	try {
	    tokenizerFactory.getTokenizerFormule().analizza(formula);
	} catch (ValidazioneFormulaFallitaException e) {
	    throw new RuntimeException(e.getMessage());
	}
    }

    public static void main(String[] args) {

	String formula = "[GG_PRES_OR_NON_GIUS][MAGAZZINO]";
	if (formula.contains("+") || formula.contains("-") || formula.contains("*") || formula.contains("/")) {
	    System.out.println("nulla");
	} else {
	    System.out.println("Non sono presenti i simboli");
	}
    }

    @Override
    public List<LivelloServizioHelper> findLivelliAttiviByMercatoUso(Integer idUso) {

	List<LivelloServizioHelper> livelli = new ArrayList<LivelloServizioHelper>(0);
	livelli.add(new LivelloServizioHelper(null, SegnapostoGG, SegnapostoGGDescrizione));
	livelli.add(new LivelloServizioHelper(null, SegnapostoGGPres, SegnapostoGGPresDescrizione));
	livelli.add(new LivelloServizioHelper(null, SegnapostoGGPresOrNonGius, SegnapostoGGPresOrNonGiusDescrizione));
	livelli.add(new LivelloServizioHelper(null, SegnapostoConcessionario, SegnapostoConcessionarioDescrizione));
	livelli.add(new LivelloServizioHelper(null, SegnapostoSpuntista, SegnapostoSpuntistaDescrizione));
	if (StringUtils.isNotBlank(verticalizzazioneComportamentiMercatiService.codiceIstatBattitori())) {
	    livelli.add(new LivelloServizioHelper(null, SegnapostoBattitore, SegnapostoBattitoreDescrizione));
	}
	livelli.add(new LivelloServizioHelper(null, SegnapostoCoefficienteMercato, SegnapostoCoefficienteMercatoDescrizione));
	List<LivelloServizio> servizi = this.livelloServizioService.findByMercatoUso(idUso, true);
	if (servizi != null) {
	    for (LivelloServizio servizio : servizi) {
		livelli.add(new LivelloServizioHelper(servizio.getId().getCodice(), servizio.getSegnaposto(), servizio.getDescrizione()));
	    }
	}
	return livelli;
    }
}
