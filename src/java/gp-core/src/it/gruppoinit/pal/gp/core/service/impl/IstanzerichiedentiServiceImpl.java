package it.gruppoinit.pal.gp.core.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.IstanzerichiedentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisogBack;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocure;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.istanze.eventi.EventoSoggettiIstanzaAggiornati;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.CfRichiedentiBean;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocureService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.TipisoggettoService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author francescop
 */
@Service
public class IstanzerichiedentiServiceImpl extends BaseServiceImpl<Istanzerichiedenti, PkId> implements IstanzerichiedentiService {

    private static final Logger log = LoggerFactory.getLogger(IstanzerichiedentiServiceImpl.class);
    private AnagrafeService anagrafeService;
    private IstanzeeventiService istanzeeventiService;
    private IstanzerichiedentiDAO istanzerichiedentiDAO;
    private IstanzeprocureService istanzeprocureService;
    private IstanzeService istanzeService;
    private OggettiService oggettiService;
    private TipisoggettoService tipisoggettoService;
    @Autowired
    private IEventPublisher eventPublisher;

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setIstanzerichiedentiDAO(IstanzerichiedentiDAO istanzerichiedentiDAO) {

	this.istanzerichiedentiDAO = istanzerichiedentiDAO;
    }

    @Autowired
    public void setIstanzeprocureService(IstanzeprocureService istanzeprocureService) {

	this.istanzeprocureService = istanzeprocureService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setTipisoggettoService(TipisoggettoService tipisoggettoService) {

	this.tipisoggettoService = tipisoggettoService;
    }

    @Override
    protected Class<Istanzerichiedenti> getEntityClass() {

	return Istanzerichiedenti.class;
    }

    @Override
    public List<Istanzerichiedenti> findAll(Integer firstResult, Integer maxResult) {

	return istanzerichiedentiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzerichiedenti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzerichiedentiDAO.insert(entity);
	    eventPublisher.publish(new EventoSoggettiIstanzaAggiornati(entity.getIstanza().getId().getCodice()));
	}
    }

    @Override
    public Istanzerichiedenti findById(PkId id) {

	return istanzerichiedentiDAO.findById(id);
    }

    @Override
    public void update(Istanzerichiedenti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Istanzerichiedenti copy = this.findById(entity.getId());
	    istanzerichiedentiDAO.evict(copy);
	    // BOCCI 2012-03-13 in caso di cambio richiedenti e questi sono selezionati nella tabella istanzeprocure allora devo inserire eventi 
	    Integer codiceRichiedenteOld = (Integer) EntityUtils.getNestedProperty(copy.getRichiedente(), "id.codice");
	    Integer codiceAziendaOld = (Integer) EntityUtils.getNestedProperty(copy.getAnagrafeCollegata(), "id.codice");
	    Integer codiceprocuratoreOld = (Integer) EntityUtils.getNestedProperty(copy.getProcuratore(), "id.codice");
	    // END BOCCI 2012-03-13 in caso di cambio richiedenti e questi sono selezionati nella tabella istanzeprocure allora devo inserire eventi
	    istanzerichiedentiDAO.update(entity);
	    istanzerichiedentiDAO.flush();
	    // BOCCI 2012-03-13 in caso di cambio richiedenti e questi sono selezionati nella tabella istanzeprocure allora devo inserire eventi 
	    Integer codiceRichiedenteNew = (Integer) EntityUtils.getNestedProperty(entity.getRichiedente(), "id.codice");
	    Integer codiceAziendaNew = (Integer) EntityUtils.getNestedProperty(entity.getAnagrafeCollegata(), "id.codice");
	    Integer codiceprocuratoreNew = (Integer) EntityUtils.getNestedProperty(entity.getProcuratore(), "id.codice");
	    if (!istanzeprocureService.findByIstanza(entity.getId().getCodice()).isEmpty()) {
		checkProcuraChange(entity.getId().getCodice(), codiceRichiedenteOld, codiceRichiedenteNew, entity.getIstanza());
		checkProcuraChange(entity.getId().getCodice(), codiceAziendaOld, codiceAziendaNew, entity.getIstanza());
		checkProcuraChange(entity.getId().getCodice(), codiceprocuratoreOld, codiceprocuratoreNew, entity.getIstanza());
	    }
	    // END BOCCI 2012-03-13 in caso di cambio richiedenti e questi sono selezionati nella tabella istanzeprocure allora devo inserire eventi
	    if (!Utilities.nullSafeEqual(codiceRichiedenteOld, codiceRichiedenteNew) || // 
		    !Utilities.nullSafeEqual(codiceAziendaOld, codiceAziendaNew) || //
		    !Utilities.nullSafeEqual(codiceprocuratoreOld, codiceprocuratoreNew)) {
		eventPublisher.publish(new EventoSoggettiIstanzaAggiornati(entity.getIstanza().getId().getCodice()));
	    }
	}
    }

    private void checkProcuraChange(Integer codiceIstanza, Integer codiceRichiedenteOld, Integer codiceRichiedenteNew, Istanze istanza) {

	if (codiceRichiedenteNew == null && codiceRichiedenteOld == null) {
	    return;
	}
	if (codiceRichiedenteNew == null) {
	    codiceRichiedenteNew = Integer.valueOf(-100);
	}
	if (codiceRichiedenteOld == null) {
	    codiceRichiedenteOld = Integer.valueOf(-100);
	}
	if (!codiceRichiedenteNew.equals(codiceRichiedenteOld)) {
	    if (!codiceRichiedenteOld.equals(Integer.valueOf(-100))) {
		List<Istanzeprocure> ipcs = istanzeprocureService.findByIstanzaAndAnagrafe(codiceIstanza, codiceRichiedenteOld);
		if (!ipcs.isEmpty()) {
		    // INSERISCI EVENTO modificata anagrafe legata a procura
		    istanzeeventiService.insert("In istanze richiedenti e' stata modificata un'anagrafica legata alle procure (rif old=" +
						codiceRichiedenteOld + ", rif new=" + codiceRichiedenteNew + ")",
			    IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
		}
	    }
	}
    }

    @Override
    public void delete(Istanzerichiedenti entity) {

	if (isDeleteAllowed(entity)) {
	    istanzerichiedentiDAO.delete(entity);
	}
    }

    @Override
    public List<Istanzerichiedenti> findByIstanza(Istanze istanza) {

	return istanzerichiedentiDAO.findByIstanza(istanza);
    }

    @Override
    public void copiaIstanzeRichiedenti(Istanze istanzaSorgente, Istanze istanzaDestinatario) {

	ServiceValidationRules validationRule = (ServiceValidationRules) SigeproBusinessRules.getClassRules(ServiceValidationRules.class);
	validationRule.setCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name(), false);
	log.debug("Inizio copia soggetti collegati dall'istanza sorgente ({}) all'istanza ({})", istanzaSorgente.getNumeroistanza(),
		istanzaDestinatario.getNumeroistanza());
	// le istanze richiedenti che devo copiare 
	Set<Istanzerichiedenti> listIstanzerichiedentiSorgenti = istanzaSorgente.getIstanzerichiedentis();
	//
	//1-lista delle istanze richiendenti che utilizzerò per i controllo per non creare doppioni
	Set<Istanzerichiedenti> listIstanzerichiedentiDestinatari = istanzaDestinatario.getIstanzerichiedentis();
	Istanzerichiedenti istanzerichiedenti = null;
	log.debug("Inizio ciclo dei soggetti collegati dell'istanza sorgente ({})", istanzaSorgente.getNumeroistanza());
	for (Istanzerichiedenti istanzerichiedentiSorgenete : listIstanzerichiedentiSorgenti) {
	    // se l'istanza richiedente sorgente non è presente nella lista destinatario 
	    // allora la inserisco
	    log.debug("Controllo che il soggetto collegato ({}) non sia già presente nell'istanza di destinazione",
		    istanzerichiedentiSorgenete.getId());
	    if (!isSoggettiCollegatiExists(istanzerichiedentiSorgenete, listIstanzerichiedentiDestinatari)) {
		istanzerichiedenti = new Istanzerichiedenti();
		if (istanzerichiedentiSorgenete.getProcuratore() != null) {
		    istanzerichiedenti.setProcuratore(istanzerichiedentiSorgenete.getProcuratore());
		}
		if (istanzerichiedentiSorgenete.getAnagrafeCollegata() != null) {
		    istanzerichiedenti.setAnagrafeCollegata(istanzerichiedentiSorgenete.getAnagrafeCollegata());
		}
		if (istanzerichiedentiSorgenete.getTiposoggetto() != null) {
		    istanzerichiedenti.setTiposoggetto(istanzerichiedentiSorgenete.getTiposoggetto());
		}
		if (istanzerichiedentiSorgenete.getRichiedente() != null) {
		    istanzerichiedenti.setRichiedente(istanzerichiedentiSorgenete.getRichiedente());
		}
		if (StringUtils.isNotBlank(istanzerichiedentiSorgenete.getDescrsoggetto())) {
		    istanzerichiedenti.setDescrsoggetto(istanzerichiedentiSorgenete.getDescrsoggetto());
		}
		if (istanzerichiedentiSorgenete.getOggettoProcuratore() != null) {
		    istanzerichiedenti.setOggettoProcuratore(istanzerichiedentiSorgenete.getOggettoProcuratore());
		}
		log.debug("Soggetto collegato ({}) non sia già presente nell'istanza di destinazione, lo inserisco");
		istanzerichiedenti.setIstanza(istanzaDestinatario);
		this.insert(istanzerichiedenti);
	    }
	}
	SigeproBusinessRules.buildDefaultRules();
    }

    // Controlla per ogni oggetto della lista destinatario se ce ne è uno uguale a quello sorgente passoto
    // quando ne trova uno esce e ritona true
    // altrimenti ritorna false.
    // campi controllati:
    //	1- Anagrafe collegata
    //	2- Procuratore
    //  3- Tiposoggetto
    //	4- Richiedente
    //	5- Descrizione soggetto
    private boolean isSoggettiCollegatiExists(Istanzerichiedenti istanzerichiedentiSorgenete,
	    Set<Istanzerichiedenti> istanzerichiedentiDestinatarios) {

	boolean isEquals = false;
	int contatoreUguaglianze = 0;
	for (Istanzerichiedenti istanzerichiedentiDestinatario : istanzerichiedentiDestinatarios) {
	    if ((istanzerichiedentiSorgenete.getAnagrafeCollegata() == null && istanzerichiedentiDestinatario.getAnagrafeCollegata() == null)
		    || (EntityUtils.equals(istanzerichiedentiSorgenete.getAnagrafeCollegata(),
			    istanzerichiedentiDestinatario.getAnagrafeCollegata()))) {
		contatoreUguaglianze++;
	    }
	    if ((istanzerichiedentiSorgenete.getProcuratore() == null && istanzerichiedentiDestinatario.getProcuratore() == null)
		    || (EntityUtils.equals(istanzerichiedentiSorgenete.getProcuratore(), istanzerichiedentiDestinatario.getProcuratore()))) {
		contatoreUguaglianze++;
	    }
	    if ((istanzerichiedentiSorgenete.getTiposoggetto() == null && istanzerichiedentiDestinatario.getTiposoggetto() == null)
		    || (EntityUtils.equals(istanzerichiedentiSorgenete.getTiposoggetto(), istanzerichiedentiDestinatario.getTiposoggetto()))) {
		contatoreUguaglianze++;
	    }
	    if ((istanzerichiedentiSorgenete.getRichiedente() == null && istanzerichiedentiDestinatario.getRichiedente() == null)
		    || (EntityUtils.equals(istanzerichiedentiSorgenete.getRichiedente(), istanzerichiedentiDestinatario.getRichiedente()))) {
		contatoreUguaglianze++;
	    }
	    if ((StringUtils.isBlank(istanzerichiedentiSorgenete.getDescrsoggetto())
		    && StringUtils.isBlank(istanzerichiedentiDestinatario.getDescrsoggetto()))
		    || (StringUtils.isNotBlank(istanzerichiedentiSorgenete.getDescrsoggetto())
			    && StringUtils.isNotBlank(istanzerichiedentiDestinatario.getDescrsoggetto())
			    && istanzerichiedentiSorgenete.getDescrsoggetto().equalsIgnoreCase(istanzerichiedentiDestinatario.getDescrsoggetto()))) {
		contatoreUguaglianze++;
	    }
	    // significa che i 5 campi fondamentali sono uguali e quindi i due record li consideriamo uguali.
	    if (contatoreUguaglianze == 5) {
		return true;
	    }
	    contatoreUguaglianze = 0;
	}
	return isEquals;
    }

    @Override
    protected boolean validateEntity(Istanzerichiedenti entity) {

	super.validateEntity(entity);
	ServiceValidationRules validationRule = (ServiceValidationRules) SigeproBusinessRules.getClassRules(ServiceValidationRules.class);
	boolean doBusinessValidation = true;
	if (validationRule != null) {
	    doBusinessValidation = validationRule.getCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name());
	}
	if (doBusinessValidation) {
	    if (EntityUtils.getNestedProperty(entity.getTiposoggetto(), "id.codice") != null) {
		Tipisoggetto tiposoggetto = entity.getTiposoggetto();
		boolean specificaDescrizione = BooleanUtils.toBoolean(tiposoggetto.getFlgSpecificadescrizione());
		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
		if (specificaDescrizione) {
		    if (StringUtils.isBlank(entity.getDescrsoggetto())) {
			_ivs.add(new InvalidValue("alert.required", entity.getClass(), "descrsoggetto", entity.getDescrsoggetto(), entity));
		    }
		}
		boolean richiedianagrafecollegata = BooleanUtils.toBoolean(tiposoggetto.getRichiedianagrafecoll());
		if (richiedianagrafecollegata) {
		    if (EntityUtils.getNestedProperty(entity.getAnagrafeCollegata(), "id.codice") == null) {
			_ivs.add(new InvalidValue("alert.required", entity.getClass(), "anagrafeCollegata", entity.getAnagrafeCollegata(), entity));
		    }
		}
		if (_ivs.size() > 0) {
		    this.throwValidationMessages(_ivs);
		}
	    }
	}
	return true;
    }

    private void dataIntegration(Istanzerichiedenti entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("È stato passato un oggetto Istanzerichiedenti nullo");
	}
	fixMergeEntityProperties(entity);
	gestAnagrafeStorico(entity);
    }

    protected void fixMergeEntityProperties(Istanzerichiedenti entity) {

	Anagrafe richiedente = anagrafeService.bindDomainObject(entity.getRichiedente(), PkId.class, "id.codice");
	entity.setRichiedente(richiedente);
	Anagrafe anagrafecollegata = anagrafeService.bindDomainObject(entity.getAnagrafeCollegata(), PkId.class, "id.codice");
	entity.setAnagrafeCollegata(anagrafecollegata);
	Anagrafe procuratore = anagrafeService.bindDomainObject(entity.getProcuratore(), PkId.class, "id.codice");
	entity.setProcuratore(procuratore);
	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanza(), PkId.class, "id.codice");
	entity.setIstanza(istanza);
	Tipisoggetto tiposoggetto = tipisoggettoService.bindDomainObject(entity.getTiposoggetto(), PkId.class, "id.codice");
	entity.setTiposoggetto(tiposoggetto);
	if (tiposoggetto != null) {
	    if (BooleanUtils.isFalse(tiposoggetto.getFlgSpecificadescrizione())) {
		entity.setDescrsoggetto(null);
	    }
	    if (BooleanUtils.isFalse(tiposoggetto.getRichiedianagrafecoll())) {
		entity.setAnagrafeCollegata(null);
	    }
	}
	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggettoProcuratore(), PkId.class, "id.codice");
	entity.setOggettoProcuratore(oggetto);
    }

    private void gestAnagrafeStorico(Istanzerichiedenti entity) {

	//	entity.setRichiedentestorico(null);
	//	entity.setAnagrafeCollegatastorico(null);
	//	entity.setProcuratorestorico(null);
	if (EntityUtils.getNestedProperty(entity.getIstanza(), "id.codice") != null) {
	    if (EntityUtils.getNestedProperty(entity, "richiedente.id.codice") != null) {
		checkStoricoAnagrafe(entity, "richiedente", "richiedentestorico", entity.getIstanza().getData());
	    } else {
		//..  richiedente è nullo azzero anche il richiedente storico
		if (EntityUtils.getNestedProperty(entity.getRichiedentestorico(), "id.codice") != null) {
		    entity.setRichiedentestorico(null);
		}
	    }
	    if (EntityUtils.getNestedProperty(entity, "anagrafeCollegata.id.codice") != null) {
		checkStoricoAnagrafe(entity, "anagrafeCollegata", "anagrafeCollegatastorico", entity.getIstanza().getData());
	    } else {
		//..  azienda collegata è nullo azzero anche il azienda colelgata storico
		if (EntityUtils.getNestedProperty(entity.getAnagrafeCollegatastorico(), "id.codice") != null) {
		    entity.setAnagrafeCollegatastorico(null);
		}
	    }
	    if (EntityUtils.getNestedProperty(entity, "procuratore.id.codice") != null) {
		checkStoricoAnagrafe(entity, "procuratore", "procuratorestorico", entity.getIstanza().getData());
	    } else {
		// .. professionista è nullo azzero anche il professionista storico
		if (EntityUtils.getNestedProperty(entity.getProcuratorestorico(), "id.codice") != null) {
		    entity.setProcuratorestorico(null);
		}
	    }
	}
    }

    private void checkStoricoAnagrafe(Object entity, String mainProperty, String storicoProperty, Date dataStorico) {

	try {
	    if (EntityUtils.getNestedProperty(entity, mainProperty + ".id.codice") != null) {
		String codiceAnagrafeStr = BeanUtils.getProperty(entity, mainProperty + ".id.codice");
		Integer codiceAnagrafe = null;
		if (StringUtils.isNotBlank(codiceAnagrafeStr)) {
		    codiceAnagrafe = Integer.valueOf(codiceAnagrafeStr);
		}
		Anagrafe richiedente = anagrafeService.findById(new PkId(codiceAnagrafe));
		if (EntityUtils.getNestedProperty(entity, storicoProperty + ".id.codice") == null) {
		    Anagrafestorico richiedenteStorico = anagrafeService.findStoricoId(richiedente, dataStorico);
		    BeanUtils.setProperty(entity, storicoProperty, richiedenteStorico);
		} else {
		    Integer codicestorico = (Integer) EntityUtils.getNestedProperty(entity, storicoProperty + ".id.codice");
		    Anagrafestorico as = null;
		    if (codicestorico != null) {
			PkId idStorico = new PkId(codicestorico);
			as = anagrafeService.findAnagrafeStoricoById(idStorico);
			if (as != null) {
			    if (!as.getAnagrafe().getId().getCodice().equals(richiedente.getId().getCodice())) {
				Anagrafestorico richiedenteStorico = anagrafeService.findStoricoId(richiedente, dataStorico);
				BeanUtils.setProperty(entity, storicoProperty, richiedenteStorico);
			    }
			} else {
			    log.error("checkStoricoAnagrafe: non è stata trovato nessun record in anagrafestorico con id={}", codicestorico);
			}
		    } else {
			log.error("checkStoricoAnagrafe: anomalia nell idstorico {} della property {}",
				EntityUtils.getNestedProperty(entity, storicoProperty + ".id.codice"), storicoProperty);
		    }
		}
	    }
	} catch (IllegalAccessException e) {
	    log.error("checkStoricoAnagrafe[{}]-[{}]-[{}]: {}",
		    new Object[] { entity.getClass().getName(), mainProperty, storicoProperty, e.getMessage() });
	    throw new RuntimeException("Errore nella gestione del campo anagrafeStorico. rif[checkStoricoAnagrafe][" + entity.getClass().getName() +
				       "]-[" + mainProperty + "]-[" + storicoProperty + "]",
		    e);
	} catch (InvocationTargetException e) {
	    log.error("checkStoricoAnagrafe[{}]-[{}]-[{}]: {}",
		    new Object[] { entity.getClass().getName(), mainProperty, storicoProperty, e.getMessage() });
	    throw new RuntimeException("Errore nella gestione del campo anagrafeStorico. rif[checkStoricoAnagrafe][" + entity.getClass().getName() +
				       "]-[" + mainProperty + "]-[" + storicoProperty + "]",
		    e);
	} catch (NoSuchMethodException e) {
	    log.error("checkStoricoAnagrafe[{}]-[{}]-[{}]: {}",
		    new Object[] { entity.getClass().getName(), mainProperty, storicoProperty, e.getMessage() });
	    throw new RuntimeException("Errore nella gestione del campo anagrafeStorico. rif[checkStoricoAnagrafe][" + entity.getClass().getName() +
				       "]-[" + mainProperty + "]-[" + storicoProperty + "]",
		    e);
	}
    }

    @Override
    public List<Istanzerichiedenti> findByAnagrafeRichiedente(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafeRichiedente: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "richiedente", Integer.class));
	filterTable.addRestriction(fr);
	return istanzerichiedentiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<Istanzerichiedenti> findByAnagrafeAnagrafeCollegata(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafeAnagrafeCollegata: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafeCollegata", Integer.class));
	filterTable.addRestriction(fr);
	return istanzerichiedentiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<Istanzerichiedenti> findByAnagrafeProcuratore(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafeProcuratore: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "procuratore", Integer.class));
	filterTable.addRestriction(fr);
	return istanzerichiedentiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<Istanzerichiedenti> findAnagrafeCollOrRichiedenteOrProcuratoreStorico(Anagrafestorico anagrafeStorico) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.equals("id.codice", anagrafeStorico.getId().getCodice(), "richiedentestorico", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", anagrafeStorico.getId().getCodice(), "anagrafeCollegatastorico", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", anagrafeStorico.getId().getCodice(), "procuratorestorico", Integer.class));
	ft.addRestriction(fr);
	return istanzerichiedentiDAO.findByFilterTable(ft);
    }

    @Override
    public List<Istanzerichiedenti> findByTiposoggetto(Integer codiceTiposoggetto, int firstResult, int maxResult) {

	if (codiceTiposoggetto == null) {
	    throw new IllegalArgumentException("findByTiposoggetto: il parametro codiceTiposoggetto e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tiposoggettoId", codiceTiposoggetto, Integer.class));
	filterTable.addRestriction(fr);
	return istanzerichiedentiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public int countSoggettiCollegatiByIstanza(Integer codiceIstanza) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	filterTable.addRestriction(criterio);
	return istanzerichiedentiDAO.countRecord(filterTable);
    }

    @Override
    public List<Istanzerichiedenti> findByIstanzaAndTiposoggetto(Integer codiceIstanza, Integer codiceTiposogg) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	criterio.addFilterField(FilterUtils.equals("tiposoggettoId", codiceTiposogg, Integer.class));
	filterTable.addRestriction(criterio);
	return istanzerichiedentiDAO.findByFilterTable(filterTable);
    }

    @Override
    public void checkTipisoggettoRichiesti(AlberoprocHelper alberoprocHelper, Integer codiceIstanza) {

	String warningMessage = "Attenzione tra i soggetti collegati non sono presenti le seguenti tipologie soggetti richieste: <br /><ul>";
	boolean isWarningTipiSogg = false;
	//List<String> warningMessages = new ArrayList<String>();
	//warningMessages.add("Attenzione tra i soggetti collegati non sono presenti le seguenti tipologie soggetti: <br /><ol>");
	if (!alberoprocHelper.getAlberoprocTipisogBacks().isEmpty()) {
	    Set<AlberoprocTipisogBack> alberoprocTipisogBacks = alberoprocHelper.getAlberoprocTipisogBacks();
	    for (AlberoprocTipisogBack alberoprocTipisogBack : alberoprocTipisogBacks) {
		List<Istanzerichiedenti> istanzerichiedentis = this.findByIstanzaAndTiposoggetto(codiceIstanza,
			alberoprocTipisogBack.getTipisoggetto().getId().getCodice());
		if (istanzerichiedentis.isEmpty()) {
		    isWarningTipiSogg = true;
		    warningMessage += "<li>" + alberoprocTipisogBack.getTipisoggetto().getTiposoggetto() + "</li>";
		}
	    }
	}
	if (isWarningTipiSogg) {
	    warningMessage += "</ul>";
	    FlashMessages.getWarnings().add(warningMessage);
	}
    }

    @Override
    public List<Istanzerichiedenti> findByIstanzaAndTiposoggeettoMostraInIstanza(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "istanza", Integer.class));
	fr.addFilterField(FilterUtils.equals("flagMostraDettIstanza", true, "tiposoggetto", Integer.class));
	ft.addRestriction(fr);
	return istanzerichiedentiDAO.findByFilterTable(ft);
    }

    @Override
    public List<Istanzerichiedenti> findByIstanza(Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanza", Integer.class));
	ft.addRestriction(fr);
	return istanzerichiedentiDAO.findByFilterTable(ft);
    }

    @Override
    public List<CfRichiedentiBean> findBeanByCodiceIstanza(Integer codiceIstanza) {

	return this.istanzerichiedentiDAO.findBeanByCodiceIstanza(codiceIstanza);
    }
}
