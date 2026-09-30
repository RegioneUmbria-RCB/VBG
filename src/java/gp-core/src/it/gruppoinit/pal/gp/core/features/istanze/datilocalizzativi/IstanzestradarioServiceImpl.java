package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzelavoriT;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Stradariocolore;
import it.gruppoinit.pal.gp.core.domain.StradariocoloreId;
import it.gruppoinit.pal.gp.core.domain.TipiLocalizzazioni;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.IstanzeStradarioRestBean;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.eventi.EventoLocalizzazioneIstanzaDel;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.eventi.EventoLocalizzazioneIstanzaIns;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.eventi.EventoLocalizzazioneIstanzaUpd;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.IstanzelavoriTService;
import it.gruppoinit.pal.gp.core.service.IstanzemappaliService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.StradariocoloreService;
import it.gruppoinit.pal.gp.core.service.TipiLocalizzazioniService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;

/**
 * 
 * @author francescop
 */
@Service
public class IstanzestradarioServiceImpl extends BaseServiceImpl<Istanzestradario, PkId> implements IstanzestradarioService {

    private DocumentiistanzaService documentiistanzaService;
    private IstanzeService istanzeService;
    private IstanzelavoriTService istanzelavoriTService;
    private IstanzemappaliService istanzemappaliService;
    private IstanzestradarioDAO istanzestradarioDAO;
    private StradarioService stradarioService;
    private StradariocoloreService stradariocoloreService;
    private TipiLocalizzazioniService tipiLocalizzazioniService;
    private Istanzedyn2datiService istanzedyn2datiService;
    private OggettiService oggettiService;

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Autowired
    public void setTipiLocalizzazioniService(TipiLocalizzazioniService tipiLocalizzazioniService) {

	this.tipiLocalizzazioniService = tipiLocalizzazioniService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzelavoriTService(IstanzelavoriTService istanzelavoriTService) {

	this.istanzelavoriTService = istanzelavoriTService;
    }

    @Autowired
    public void setIstanzemappaliService(IstanzemappaliService istanzemappaliService) {

	this.istanzemappaliService = istanzemappaliService;
    }

    @Autowired
    public void setIstanzestradarioDAO(IstanzestradarioDAO istanzestradarioDAO) {

	this.istanzestradarioDAO = istanzestradarioDAO;
    }

    @Autowired
    public void setStradarioService(StradarioService stradarioService) {

	this.stradarioService = stradarioService;
    }

    @Autowired
    public void setStradariocoloreService(StradariocoloreService stradariocoloreService) {

	this.stradariocoloreService = stradariocoloreService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    private IEventPublisher eventPublisher;

    @Override
    protected Class<Istanzestradario> getEntityClass() {

	return Istanzestradario.class;
    }

    @Override
    public List<Istanzestradario> findAll(Integer firstResult, Integer maxResult) {

	return istanzestradarioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzestradario entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Set<Istanzemappali> istanzemappalis = entity.getIstanzemappalis();
	    entity.setIstanzemappalis(null);
	    istanzestradarioDAO.insert(entity);
	    entity.setIstanzemappalis(istanzemappalis);
	    childInsert(entity);
	}
    }

    private void childInsert(Istanzestradario entity) {

	if (entity.getIstanzemappalis().size() > 0) {
	    Set<Istanzemappali> istanzemappalis = entity.getIstanzemappalis();
	    int i = 0;
	    boolean primario = false;
	    boolean isIstanzestradarioPrimario = entity.getPrimario() == null ? false : entity.getPrimario().booleanValue();
	    for (Istanzemappali istanzemappali : istanzemappalis) {
		istanzemappali.setIstanza(entity.getIstanza());
		istanzemappali.setIstanzestradario(entity);
		if (isIstanzestradarioPrimario) {
		    if (!primario) {
			istanzemappali.setPrimario(Boolean.TRUE);
			primario = true;
		    }
		}
		try {
		    istanzemappaliService.insert(istanzemappali);
		} catch (Exception e) {
		    List<InvalidValue> ivs = new ArrayList<InvalidValue>();
		    if (e instanceof BaseValidationException) {
			ivs = ((BaseValidationException) e).getInvalidValues();
			for (InvalidValue invalidValue : ivs) {
			    invalidValue.addParentBean(entity, "istanzemappalis[" + i + "]");
			}
		    }
		    throwValidationMessages(ivs);
		}
		i++;
	    }
	}
	checkPrimario(entity);
	//istanzeareeService.insertFromIstanzeStradario(entity);
	try {
	    this.eventPublisher.publishThrowOnFailure(new EventoLocalizzazioneIstanzaIns(entity.getIstanza().getUuid()));
	} catch (EventAbortedException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    /**
     * Se in aggiornamento / inserimento primario è settato allora setta a non primarie le altre righe
     * 
     * @param entity
     */
    private void checkPrimario(Istanzestradario entity) {

	if (BooleanUtils.isTrue(entity.getPrimario())) {
	    List<Istanzestradario> istanzestradarios = findByIstanza(entity.getIstanza().getId().getCodice());
	    for (Istanzestradario istanzestradario : istanzestradarios) {
		if (!istanzestradario.getId().equals(entity.getId())) {
		    istanzestradario = this.findById(new PkId(istanzestradario.getId().getCodice()));
		    istanzestradarioDAO.refreshEntity(istanzestradario);
		    istanzestradario.setPrimario(Boolean.FALSE);
		    this.update(istanzestradario);
		}
	    }
	}
    }

    @Override
    public Istanzestradario findById(PkId id) {

	return istanzestradarioDAO.findById(id);
    }

    @Override
    public void update(Istanzestradario entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    childUpdate(entity);
	    istanzestradarioDAO.update(entity);
	    postUpdateActions(entity);
	}
    }

    @Override
    protected boolean validateEntity(Istanzestradario entity) {

	boolean result = super.validateEntity(entity);
	ServiceValidationRules validationRule = (ServiceValidationRules) SigeproBusinessRules.getClassRules(ServiceValidationRules.class);
	boolean doBusinessValidation = true;
	if (validationRule != null) {
	    doBusinessValidation = validationRule.getCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name());
	}
	if (doBusinessValidation) {
	    if (entity != null) {
		String latitudine = StringUtils.defaultString(entity.getLatitudine()).trim();
		String longitudine = StringUtils.defaultString(entity.getLongitudine()).trim();
		if (!(StringUtils.isNotBlank(longitudine) && StringUtils.isNotBlank(latitudine))
			&& !(StringUtils.isBlank(longitudine) && StringUtils.isBlank(latitudine))) {
		    List<InvalidValue> ivs = new ArrayList<InvalidValue>();
		    String message = "Errore nei dati dello stradario: se viene indicato uno dei due valori Latitudine o Longitudine allora è obbligatorio indicare l'altro.";
		    InvalidValue iv = new InvalidValue("04", null, "*", message, null);
		    ivs.add(iv);
		    if (StringUtils.isBlank(longitudine)) {
			iv = new InvalidValue("alert.required", entity.getClass(), "longitudine", null, entity);
			ivs.add(iv);
		    } else {
			iv = new InvalidValue("alert.required", entity.getClass(), "latitudine", null, entity);
			ivs.add(iv);
		    }
		    throwValidationMessages(ivs);
		}
	    }
	}
	return result;
    }

    private void postUpdateActions(Istanzestradario entity) {

	// devo ripetere le operazioni di salvataggio sui mappali
	if (entity.getIstanzemappalis() != null) {
	    if (entity.getIstanzemappalis().size() > 0) {
		Set<Istanzemappali> istanzemappalis = entity.getIstanzemappalis();
		int i = 0;
		boolean isIstanzestradarioPrimario = entity.getPrimario() == null ? false : entity.getPrimario().booleanValue();
		for (Istanzemappali istanzemappali : istanzemappalis) {
		    istanzemappali.setIstanza(entity.getIstanza());
		    istanzemappali.setIstanzestradario(entity);
		    if (!isIstanzestradarioPrimario) {
			istanzemappali.setPrimario(Boolean.FALSE);
		    }
		    try {
			istanzemappaliService.update(istanzemappali);
		    } catch (Exception e) {
			List<InvalidValue> ivs = new ArrayList<InvalidValue>();
			if (e instanceof BaseValidationException) {
			    ivs = ((BaseValidationException) e).getInvalidValues();
			    for (InvalidValue invalidValue : ivs) {
				invalidValue.addParentBean(entity, "istanzemappalis[" + i + "]");
			    }
			}
			throwValidationMessages(ivs);
		    }
		    i++;
		}
	    }
	}
	//istanzeareeService.ricalcolaPerIstanza(entity.getIstanza().getId().getCodice());
	try {
	    this.eventPublisher.publishThrowOnFailure(new EventoLocalizzazioneIstanzaUpd(entity.getIstanza().getUuid()));
	} catch (EventAbortedException e) {
	    throw new RuntimeException(e);
	}
    }

    private void childUpdate(Istanzestradario entity) {

	if (entity.getIstanzemappalis() != null) {
	    if (entity.getIstanzemappalis().size() > 0) {
		Set<Istanzemappali> istanzemappalis = entity.getIstanzemappalis();
		int i = 0;
		boolean isIstanzestradarioPrimario = entity.getPrimario() == null ? false : entity.getPrimario().booleanValue();
		for (Istanzemappali istanzemappali : istanzemappalis) {
		    istanzemappali.setIstanza(entity.getIstanza());
		    istanzemappali.setIstanzestradario(entity);
		    if (!isIstanzestradarioPrimario) {
			istanzemappali.setPrimario(Boolean.FALSE);
		    }
		    try {
			istanzemappaliService.update(istanzemappali);
		    } catch (Exception e) {
			List<InvalidValue> ivs = new ArrayList<InvalidValue>();
			if (e instanceof BaseValidationException) {
			    ivs = ((BaseValidationException) e).getInvalidValues();
			    for (InvalidValue invalidValue : ivs) {
				invalidValue.addParentBean(entity, "istanzemappalis[" + i + "]");
			    }
			}
			throwValidationMessages(ivs);
		    }
		    i++;
		}
	    }
	}
	checkPrimario(entity);
    }

    private void dataIntegration(Istanzestradario entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro stradario è nullo");
	}
	if (entity != null) {
	    if (entity.getPrimario() == null) {
		entity.setPrimario(Boolean.FALSE);
	    }
	    if (entity.getValido() == null) {
		entity.setValido(Boolean.TRUE);
	    }
	    if (StringUtils.isBlank(StringUtils.defaultString(entity.getUuid()).trim())) {
		entity.setUuid(UUID.randomUUID().toString());
	    }
	    fixMergeEntityProperties(entity);
	}
    }

    protected void fixMergeEntityProperties(Istanzestradario entity) {

	Stradariocolore stradariocolore = stradariocoloreService.bindDomainObject(entity.getStradariocolore(), StradariocoloreId.class,
		"id.codicecolore");
	entity.setStradariocolore(stradariocolore);
	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanza(), PkId.class, "id.codice");
	entity.setIstanza(istanza);
	Stradario stradario = stradarioService.bindDomainObject(entity.getStradario(), PkId.class, "id.codice");
	entity.setStradario(stradario);
	TipiLocalizzazioni tl = tipiLocalizzazioniService.bindDomainObject(entity.getTipiLocalizzazioni(), PkId.class, "id.codice");
	entity.setTipiLocalizzazioni(tl);
    }

    @Override
    public void delete(Istanzestradario entity) {

	/*
	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    istanzestradarioDAO.delete(entity);
	}
	*/
	internalDelete(entity, false);
    }

    @Override
    public void deleteByCodiceIstanza(Integer codiceIstanza) {

	List<Istanzestradario> istanzestradarios = this.findByIstanza(codiceIstanza);
	for (Istanzestradario istanzestradario : istanzestradarios) {
	    this.internalDelete(istanzestradario, true);
	}
    }

    private void internalDelete(Istanzestradario entity, boolean cancellazioneIstanzaInCorso) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    istanzestradarioDAO.delete(entity);
	}
	try {
	    this.eventPublisher.publishThrowOnFailure(new EventoLocalizzazioneIstanzaDel(entity.getIstanza().getUuid(), cancellazioneIstanzaInCorso));
	} catch (EventAbortedException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    protected void childDelete(Istanzestradario entity) {

	Set<Istanzemappali> istanzemappalis = entity.getIstanzemappalis();
	for (Istanzemappali istanzemappali : istanzemappalis) {
	    istanzemappaliService.delete(istanzemappali);
	}
	Set<IstanzelavoriT> istanzelavoriTs = entity.getIstanzelavoriTs();
	for (IstanzelavoriT istanzelavoriT : istanzelavoriTs) {
	    istanzelavoriTService.delete(istanzelavoriT);
	}
    }

    @Override
    protected boolean isDeleteAllowed(Istanzestradario entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// Ricerco le istanze lavori collegati attraverso una query. Usando il get in caso di 
	// cancellazione dell'istanza il sistema non si accorgeva che già erano state cancellate nel service
	// dell'istanza e rilanciava l'errore di Business che esistevano record in istanzelavoriT collegati
	// ad istanzestradario.
	List<IstanzelavoriT> istanzelavoriTs = istanzelavoriTService.findByIstanza(entity.getIstanza());
	//	if (entity.getIstanzelavoriTs() != null && !entity.getIstanzelavoriTs().isEmpty()) {
	if (!istanzelavoriTs.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ISTANZELAVORI_T", null));
	}
	// List<Istanzedyn2dati> id2s = istanzedyn2datiService.findDyn2datiLocalizzazioneByUUID(entity.getIstanza().getId().getCodice(),
	// 	entity.getUuid(), 0, 2);
	List<CodiceDescrizioneBean> cdbs = istanzedyn2datiService.findModelliCheUsanoLocalizzazioneByUUID(entity.getIstanza().getId().getCodice(),
		entity.getUuid(), 0, 2);
	if (!cdbs.isEmpty()) {
	    String descrizione = "<ul>";
	    for (CodiceDescrizioneBean cd : cdbs) {
		descrizione += "<li>" + cd.getDescrizione() + " (" + cd.getCodice() + ")</li>";
	    }
	    descrizione += "</ul>";
	    _ivs.add(new InvalidValue("alert.cancellazione_istanze_stradario_usato_da_dyn2dati", null, "", descrizione, null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }

    @Override
    public Istanzestradario findPrimarioByCodiceIstanza(Integer codiceIstanza) {

	return istanzestradarioDAO.findPrimarioByCodiceIstanza(codiceIstanza);
    }

    @Override
    public List<Istanzestradario> findByIstanza(Integer codiceIstanza) {

	return istanzestradarioDAO.findByIstanza(codiceIstanza);
    }

    @Override
    public List<Istanzestradario> findByFilterTable(FilterTable filterTable) {

	return istanzestradarioDAO.findByFilterTable(filterTable);
    }

    @Override
    public void copiaLocalizzazioniWithMappali(Istanze istanzaSorgente, Istanze istanzaDestinatario, boolean isCopiaMappali) {

	// Le istanze stradario che devo copiare 
	// Set<Istanzestradario> listIstanzeattivitaSorgente = istanzaSorgente.getIstanzestradarios();
	//
	//1- lista delle istanze stradario che utilizzerò per il controllo per non creare doppioni
	Set<Istanzestradario> listIstanzestradarioDestinatario = istanzaDestinatario.getIstanzestradarios();
	// Inserisco Localizzazione e mappali.
	Istanzestradario istanzestradario = null;
	List<Istanzestradario> listIstanzeStradarioSorgente = findByIstanza(istanzaSorgente.getId().getCodice());
	for (Istanzestradario istanzestradarioSorgente : listIstanzeStradarioSorgente) {
	    // Se true inserisco sia le localizzazioni che i mappali
	    if (isCopiaMappali) {
		//Controllo preliminare è che lo stradario contenga dei mappali
		// Se non contiene mappali non deve essere fatto l'inserimento, anche se lo stradario non 
		//esiste.
		List<Istanzemappali> mappalisIstanzaSorgente = istanzemappaliService
			.findByIstanzaStradario(istanzestradarioSorgente.getId().getCodice());
		if (!mappalisIstanzaSorgente.isEmpty()) {
		    //Controllo se l'istanza destinatario ha già lo stradario in esame configurato.
		    //Se si (il metodo ritorna l'istanza stradario destinatario trovata).
		    Istanzestradario istanzestradarioDestinatario = isIstanzeStradarioExists(istanzestradarioSorgente,
			    listIstanzestradarioDestinatario);
		    // Trovo l'istanza destinatario diversa da null
		    // Verifico solo se ci sono dei mappali nell'istanza stradario sorgente 
		    // non presenti in istanza stradario destinatario e li inserisco. 
		    if (istanzestradarioDestinatario != null) {
			for (Istanzemappali istanzemappaliSorgente : mappalisIstanzaSorgente) {
			    Istanzemappali istanzemappali = null;
			    //Se non eiste il mappale nell'istanza stradale destinatario lo vado ad inserire
			    if (!isIstanzeMappaliExists(istanzemappaliSorgente, istanzestradarioDestinatario.getIstanzemappalis())) {
				istanzemappali = createCopiaMappali(istanzemappaliSorgente);
				istanzemappali.setIstanza(istanzaDestinatario);
				istanzemappali.setIstanzestradario(istanzestradarioDestinatario);
				istanzemappaliService.insert(istanzemappali);
			    }
			}
			// Se istanza stradario destinatario == null allora inserisco nell'istanza destinatario
			// 1- Istanza stradario sorgente e tutti i suoi mappali.
		    } else {
			// inserisco stradario e mappali senza ulteriori controlli
			istanzestradario = createCopiaStradario(istanzestradarioSorgente);
			istanzestradario.setIstanza(istanzaDestinatario);
			this.insert(istanzestradario);
			for (Istanzemappali istanzemappaliSorgente : mappalisIstanzaSorgente) {
			    Istanzemappali istanzemappali = null;
			    istanzemappali = createCopiaMappali(istanzemappaliSorgente);
			    istanzemappali.setIstanza(istanzaDestinatario);
			    istanzemappali.setIstanzestradario(istanzestradario);
			    istanzemappaliService.insert(istanzemappali);
			}
		    }
		}
	    } else// Inserisce le localizzazioni senza considerare i mappali.
	    {
		// controllo che lo stradrio che voglio copiare non sia già presente nel destinatario  
		if (isIstanzeStradarioExists(istanzestradarioSorgente, listIstanzestradarioDestinatario) == null) {
		    istanzestradario = createCopiaStradario(istanzestradarioSorgente);
		    istanzestradario.setIstanza(istanzaDestinatario);
		    this.insert(istanzestradario);
		}
	    }
	}
    }

    @Override
    public int countRecordByStradario(Stradario stradario) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.codice", stradario.getId().getCodice(), "stradario", Integer.class));
	filterTable.addRestriction(filterRestriction);
	return istanzestradarioDAO.countRecord(filterTable);
    }

    /**
     * Crea una copia dell' istanza stradario a partire da una passata
     * 
     * @param istanzestradarioSorgenete
     * @return
     */
    private Istanzestradario createCopiaStradario(Istanzestradario istanzestradarioSorgenete) {

	Istanzestradario istanzestradario = new Istanzestradario();
	if (istanzestradarioSorgenete.getStradario() != null) {
	    istanzestradario.setStradario(istanzestradarioSorgenete.getStradario());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getCivico())) {
	    istanzestradario.setCivico(istanzestradarioSorgenete.getCivico());
	}
	if (istanzestradarioSorgenete.getStradariocolore() != null) {
	    istanzestradario.setStradariocolore(istanzestradarioSorgenete.getStradariocolore());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getNote())) {
	    istanzestradario.setNote(istanzestradarioSorgenete.getNote());
	}
	//	if (istanzestradarioSorgenete.getPrimario() != null) {
	//	    istanzestradario.setPrimario(istanzestradarioSorgenete.getPrimario());
	//	}
	istanzestradario.setPrimario(Boolean.FALSE);
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getFrazione())) {
	    istanzestradario.setFrazione(istanzestradarioSorgenete.getFrazione());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getCircoscrizione())) {
	    istanzestradario.setCircoscrizione(istanzestradarioSorgenete.getCircoscrizione());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getCap())) {
	    istanzestradario.setCap(istanzestradarioSorgenete.getCap());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getKm())) {
	    istanzestradario.setKm(istanzestradarioSorgenete.getKm());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getEsponente())) {
	    istanzestradario.setEsponente(istanzestradarioSorgenete.getEsponente());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getScala())) {
	    istanzestradario.setScala(istanzestradarioSorgenete.getScala());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getInterno())) {
	    istanzestradario.setInterno(istanzestradarioSorgenete.getInterno());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getEsponenteinterno())) {
	    istanzestradario.setEsponenteinterno(istanzestradarioSorgenete.getEsponenteinterno());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getFabbricato())) {
	    istanzestradario.setFabbricato(istanzestradarioSorgenete.getFabbricato());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getCodicecivico())) {
	    istanzestradario.setFabbricato(istanzestradarioSorgenete.getCodicecivico());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getPiano())) {
	    istanzestradario.setPiano(istanzestradarioSorgenete.getPiano());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getFabbricato())) {
	    istanzestradario.setFabbricato(istanzestradarioSorgenete.getFabbricato());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getQuartiere())) {
	    istanzestradario.setQuartiere(istanzestradarioSorgenete.getQuartiere());
	}
	// Nuovi campi 
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getAccessoTipo())) {
	    istanzestradario.setAccessoTipo(istanzestradarioSorgenete.getAccessoTipo());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getAccessoNumero())) {
	    istanzestradario.setAccessoNumero(istanzestradarioSorgenete.getAccessoNumero());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getAccessoDescrizione())) {
	    istanzestradario.setAccessoDescrizione(istanzestradarioSorgenete.getAccessoDescrizione());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getIdPuntoSit())) {
	    istanzestradario.setIdPuntoSit(istanzestradarioSorgenete.getIdPuntoSit());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getLatitudine())) {
	    istanzestradario.setLatitudine(istanzestradarioSorgenete.getLatitudine());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getLongitudine())) {
	    istanzestradario.setLongitudine(istanzestradarioSorgenete.getLongitudine());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgenete.getUuid())) {
	    istanzestradario.setUuid(istanzestradarioSorgenete.getUuid());
	}
	if (istanzestradarioSorgenete.getValido() != null) {
	    istanzestradario.setValido(istanzestradarioSorgenete.getValido());
	}
	if (EntityUtils.getNestedProperty(istanzestradarioSorgenete.getTipiLocalizzazioni(), "id.codice") != null) {
	    istanzestradario.setTipiLocalizzazioni(istanzestradarioSorgenete.getTipiLocalizzazioni());
	}
	return istanzestradario;
    }

    /**
     * Crea una copia dell' istanza mappale a partire da uno passato
     * 
     * @param istanzemappaliSorgenti
     * @return
     */
    private Istanzemappali createCopiaMappali(Istanzemappali istanzemappaliSorgenti) {

	Istanzemappali istanzemappali = new Istanzemappali();
	if (StringUtils.isNotBlank(istanzemappaliSorgenti.getFoglio())) {
	    istanzemappali.setFoglio(istanzemappaliSorgenti.getFoglio());
	}
	if (StringUtils.isNotBlank(istanzemappaliSorgenti.getParticella())) {
	    istanzemappali.setParticella(istanzemappaliSorgenti.getParticella());
	}
	if (StringUtils.isNotBlank(istanzemappaliSorgenti.getSub())) {
	    istanzemappali.setSub(istanzemappaliSorgenti.getSub());
	}
	if (StringUtils.isNotBlank(istanzemappaliSorgenti.getSezione())) {
	    istanzemappali.setSezione(istanzemappaliSorgenti.getSezione());
	}
	if (StringUtils.isNotBlank(istanzemappaliSorgenti.getUnitaimmob())) {
	    istanzemappali.setUnitaimmob(istanzemappaliSorgenti.getUnitaimmob());
	}
	istanzemappali.setPrimario(Boolean.FALSE);
	if (istanzemappaliSorgenti.getCatasto() != null) {
	    istanzemappali.setCatasto(istanzemappaliSorgenti.getCatasto());
	}
	return istanzemappali;
    }

    // Controlla per ogni oggetto della lista destinatario se ce ne è uno uguale a quello sorgente passato
    // quando ne trova uno esce e ritona l'ogetto
    // altrimenti ritorna null.
    // Campi controllati :
    //	1- Civico
    //	2- Stradario
    private Istanzestradario isIstanzeStradarioExists(Istanzestradario istanzestradarioSorgente,
	    Set<Istanzestradario> listIstanzestradarioDestinatario) {

	int contatoreUguaglianze = 0;
	for (Istanzestradario istanzeattivitaDestinatario : listIstanzestradarioDestinatario) {
	    if ((StringUtils.isBlank(istanzestradarioSorgente.getCivico()) && StringUtils.isBlank(istanzeattivitaDestinatario.getCivico()))
		    || (StringUtils.isNotBlank(istanzestradarioSorgente.getCivico())
			    && StringUtils.isNotBlank(istanzeattivitaDestinatario.getCivico())
			    && istanzestradarioSorgente.getCivico().equalsIgnoreCase(istanzeattivitaDestinatario.getCivico()))) {
		contatoreUguaglianze++;
	    }
	    if ((istanzestradarioSorgente.getStradario() == null && istanzeattivitaDestinatario.getStradario() == null)
		    || (istanzestradarioSorgente.getStradario() != null && istanzeattivitaDestinatario.getStradario() != null
			    && istanzestradarioSorgente.getStradario().getId().getCodice()
				    .equals(istanzeattivitaDestinatario.getStradario().getId().getCodice()))) {
		contatoreUguaglianze++;
	    }
	    // significa che i 2 campi fondamentali sono uguali e quindi i due record li consideriamo uguali.
	    if (contatoreUguaglianze == 2) {
		return istanzeattivitaDestinatario;
	    }
	    contatoreUguaglianze = 0;
	}
	return null;
    }

    // Controlla per ogni oggetto della lista destinatario se ce ne è uno uguale a quello sorgente passato
    // quando ne trova uno esce e ritona true
    // altrimenti ritorna false.
    // Campi controllati :
    //	1- Foglio
    //	2- particella
    //  3- Sub
    private boolean isIstanzeMappaliExists(Istanzemappali istanzemappaliSorgente, Set<Istanzemappali> listIstanzemappaliDestinatario) {

	boolean isEquals = false;
	int contatoreUguaglianze = 0;
	for (Istanzemappali istanzemappaliDestinatario : listIstanzemappaliDestinatario) {
	    if ((StringUtils.isBlank(istanzemappaliSorgente.getFoglio()) && StringUtils.isBlank(istanzemappaliDestinatario.getFoglio()))
		    || (StringUtils.isNotBlank(istanzemappaliSorgente.getFoglio()) && StringUtils.isNotBlank(istanzemappaliDestinatario.getFoglio())
			    && istanzemappaliSorgente.getFoglio().equalsIgnoreCase(istanzemappaliDestinatario.getFoglio()))) {
		contatoreUguaglianze++;
	    }
	    if ((StringUtils.isBlank(istanzemappaliSorgente.getParticella()) && StringUtils.isBlank(istanzemappaliDestinatario.getParticella()))
		    || (StringUtils.isNotBlank(istanzemappaliSorgente.getParticella())
			    && StringUtils.isNotBlank(istanzemappaliDestinatario.getParticella())
			    && istanzemappaliSorgente.getParticella().equalsIgnoreCase(istanzemappaliDestinatario.getParticella()))) {
		contatoreUguaglianze++;
	    }
	    if ((StringUtils.isBlank(istanzemappaliSorgente.getSub()) && StringUtils.isBlank(istanzemappaliDestinatario.getSub()))
		    || (StringUtils.isNotBlank(istanzemappaliSorgente.getSub()) && StringUtils.isNotBlank(istanzemappaliDestinatario.getSub())
			    && istanzemappaliSorgente.getSub().equalsIgnoreCase(istanzemappaliDestinatario.getSub()))) {
		contatoreUguaglianze++;
	    }
	    // significa che i 3 campi fondamentali sono uguali e quindi i due record li consideriamo uguali.
	    if (contatoreUguaglianze == 3) {
		return true;
	    }
	    contatoreUguaglianze = 0;
	}
	return isEquals;
    }

    @Override
    public void insertReplicaStradario(Istanzemappali istanzemappaleSorgente) {

	// Definisco il nuovo stradario
	Istanzestradario istanzestradario = new Istanzestradario();
	//Setto l'istanza 
	istanzestradario.setIstanza(istanzemappaleSorgente.getIstanza());
	//Setto lo stradario
	Istanzestradario istanzestradarioSorgete = istanzemappaleSorgente.getIstanzestradario();
	istanzestradario.setStradario(istanzemappaleSorgente.getIstanzestradario().getStradario());
	// Setto le infomazioni aggiuntive dello stradario
	istanzestradario = copiaInfoStradario(istanzestradarioSorgete, istanzestradario);
	// Campi non replicabili
	istanzestradario.setPrimario(false);
	istanzestradario.setValido(false);
	// Inserisco il mappale sorgente
	//istanzestradarioDAO.insert(istanzestradario);
	this.insert(istanzestradario);
	// setto al mappale il nuovo istanzestradario creato
	istanzemappaleSorgente.setIstanzestradario(istanzestradario);
	istanzemappaliService.update(istanzemappaleSorgente);
    }

    @Override
    public Istanzestradario insertDuplicaStradario(Istanzestradario istanzeStradarioSorgente) {

	// Prima aggiorno l'istanza stradario sorgente per salvere evetuali modifiche effettuate
	this.update(istanzeStradarioSorgente);
	Istanzestradario istanzestradarioDestinazione = new Istanzestradario();
	//Setto l'istanza 
	istanzestradarioDestinazione.setIstanza(istanzeStradarioSorgente.getIstanza());
	//Setto lo stradario
	istanzestradarioDestinazione.setStradario(istanzeStradarioSorgente.getStradario());
	// Campi non replicabili
	istanzestradarioDestinazione.setPrimario(false);
	istanzestradarioDestinazione.setValido(istanzeStradarioSorgente.getValido());
	// Copio le informazioni aggiuntive per ogni stradario
	copiaInfoStradario(istanzeStradarioSorgente, istanzestradarioDestinazione);
	//istanzestradarioDAO.insert(istanzestradarioDestinazione);
	this.insert(istanzestradarioDestinazione);
	// Inserisco i mappali 
	Set<Istanzemappali> istanzemappalis = istanzeStradarioSorgente.getIstanzemappalis();
	//Set<Istanzemappali> istanzemappalisDestinazione = new HashSet<Istanzemappali>();
	Istanzemappali istanzemappaleDestinazione = null;
	for (Istanzemappali istanzemappaliSorgente : istanzemappalis) {
	    istanzemappaleDestinazione = replicaIstanzamappale(istanzemappaliSorgente);
	    // Setto al mappale il nuovo stradario
	    istanzemappaleDestinazione.setIstanzestradario(istanzestradarioDestinazione);
	    istanzemappaliService.insert(istanzemappaleDestinazione);
	}
	//istanzestradarioDestinazione.setIstanzemappalis(istanzemappalisDestinazione);
	return istanzestradarioDestinazione;
    }

    /**
     * Il metodo ritorna un oggetto istanza mappale replica del sorgente passato (l'oggetto avrà id null inmodo che
     * all'insert verrà inserito sul db un nuovo oggetto)
     * 
     * @param istanzemappaleSorgente
     * @param codiceIstanzaStrd
     * @return
     */
    private Istanzemappali replicaIstanzamappale(Istanzemappali istanzemappaleSorgente) {

	Istanzemappali istanzaMappaleReplica = new Istanzemappali();
	if (istanzemappaleSorgente.getCatasto() != null && StringUtils.isNotBlank(istanzemappaleSorgente.getCatasto().getCodice())) {
	    istanzaMappaleReplica.setCatasto(istanzemappaleSorgente.getCatasto());
	}
	if (StringUtils.isNotBlank(istanzemappaleSorgente.getDescrizioneEstesa())) {
	    istanzaMappaleReplica.setDescrizioneEstesa(istanzemappaleSorgente.getDescrizioneEstesa());
	}
	if (StringUtils.isNotBlank(istanzemappaleSorgente.getFoglio())) {
	    istanzaMappaleReplica.setFoglio(istanzemappaleSorgente.getFoglio());
	}
	istanzaMappaleReplica.setIstanza(istanzemappaleSorgente.getIstanza());
	if (StringUtils.isNotBlank(istanzemappaleSorgente.getParticella())) {
	    istanzaMappaleReplica.setParticella(istanzemappaleSorgente.getParticella());
	}
	if (StringUtils.isNotBlank(istanzemappaleSorgente.getSezione())) {
	    istanzaMappaleReplica.setSezione(istanzemappaleSorgente.getSezione());
	}
	if (StringUtils.isNotBlank(istanzemappaleSorgente.getSub())) {
	    istanzaMappaleReplica.setSub(istanzemappaleSorgente.getSub());
	}
	//istanzaMappaleReplica.setTransientCodicecatasto(istanzemappaleSorgente.getTransientCodicecatasto());
	if (StringUtils.isNotBlank(istanzemappaleSorgente.getUnitaimmob())) {
	    istanzaMappaleReplica.setUnitaimmob(istanzemappaleSorgente.getUnitaimmob());
	}
	istanzaMappaleReplica.setPrimario(Boolean.FALSE);
	return istanzaMappaleReplica;
    }

    private Istanzestradario copiaInfoStradario(Istanzestradario istanzestradarioSorgete, Istanzestradario istanzestradarioDestinazione) {

	if (StringUtils.isNotBlank(istanzestradarioSorgete.getCivico())) {
	    istanzestradarioDestinazione.setCivico(istanzestradarioSorgete.getCivico());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getEsponente())) {
	    istanzestradarioDestinazione.setEsponente(istanzestradarioSorgete.getEsponente());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getEsponenteinterno())) {
	    istanzestradarioDestinazione.setEsponenteinterno(istanzestradarioSorgete.getEsponenteinterno());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getAccessoTipo())) {
	    istanzestradarioDestinazione.setAccessoTipo(istanzestradarioSorgete.getAccessoTipo());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getAccessoNumero())) {
	    istanzestradarioDestinazione.setAccessoNumero(istanzestradarioSorgete.getAccessoNumero());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getAccessoDescrizione())) {
	    istanzestradarioDestinazione.setAccessoDescrizione(istanzestradarioSorgete.getAccessoDescrizione());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getScala())) {
	    istanzestradarioDestinazione.setScala(istanzestradarioSorgete.getScala());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getPiano())) {
	    istanzestradarioDestinazione.setPiano(istanzestradarioSorgete.getPiano());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getInterno())) {
	    istanzestradarioDestinazione.setInterno(istanzestradarioSorgete.getInterno());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getKm())) {
	    istanzestradarioDestinazione.setKm(istanzestradarioSorgete.getKm());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getNote())) {
	    istanzestradarioDestinazione.setNote(istanzestradarioSorgete.getNote());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getCap())) {
	    istanzestradarioDestinazione.setCap(istanzestradarioSorgete.getCap());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getFrazione())) {
	    istanzestradarioDestinazione.setFrazione(istanzestradarioSorgete.getFrazione());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getQuartiere())) {
	    istanzestradarioDestinazione.setQuartiere(istanzestradarioSorgete.getQuartiere());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getCircoscrizione())) {
	    istanzestradarioDestinazione.setCircoscrizione(istanzestradarioSorgete.getCircoscrizione());
	}
	if (EntityUtils.getNestedProperty(istanzestradarioSorgete.getStradariocolore(), "id.codicecolore") != null) {
	    Stradariocolore stradariocolore = stradariocoloreService
		    .findById(new StradariocoloreId(istanzestradarioSorgete.getStradariocolore().getId().getCodicecolore()));
	    istanzestradarioDestinazione.setStradariocolore(stradariocolore);
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getCodicecivico())) {
	    istanzestradarioDestinazione.setCodicecivico(istanzestradarioSorgete.getCodicecivico());
	}
	if (StringUtils.isNotBlank(istanzestradarioSorgete.getFabbricato())) {
	    istanzestradarioDestinazione.setFabbricato(istanzestradarioSorgete.getFabbricato());
	}
	return istanzestradarioDestinazione;
    }

    @Override
    public Istanzestradario copyObjecIstanzeStradarioWithoutIstanzeLavotiT(Istanzestradario istanzeStradarioSorgente) {

	Istanzestradario copy = new Istanzestradario();
	PkId id = new PkId(istanzeStradarioSorgente.getId().getCodice());
	copy.setId(id);
	copy.setCap(istanzeStradarioSorgente.getCap());
	copy.setCircoscrizione(istanzeStradarioSorgente.getCircoscrizione());
	copy.setCivico(istanzeStradarioSorgente.getCivico());
	copy.setCodicecivico(istanzeStradarioSorgente.getCodicecivico());
	//copy.setDescrizioneEstesaTransient(istanzeStradarioSorgente.getDescrizioneEstesaTransient());
	copy.setEsponente(istanzeStradarioSorgente.getEsponente());
	copy.setEsponenteinterno(istanzeStradarioSorgente.getEsponenteinterno());
	copy.setFabbricato(istanzeStradarioSorgente.getFabbricato());
	copy.setFrazione(istanzeStradarioSorgente.getFrazione());
	copy.setInterno(istanzeStradarioSorgente.getInterno());
	copy.setAccessoTipo(istanzeStradarioSorgente.getAccessoTipo());
	copy.setAccessoNumero(istanzeStradarioSorgente.getAccessoNumero());
	copy.setAccessoDescrizione(istanzeStradarioSorgente.getAccessoDescrizione());
	Istanze istaza = istanzeService.findById(new PkId(istanzeStradarioSorgente.getIstanza().getId().getCodice()));
	copy.setIstanza(istaza);
	copy.setKm(istanzeStradarioSorgente.getKm());
	copy.setNote(istanzeStradarioSorgente.getNote());
	copy.setPiano(istanzeStradarioSorgente.getPiano());
	if (istanzeStradarioSorgente.getPrimario() != null) {
	    copy.setPrimario(istanzeStradarioSorgente.getPrimario());
	}
	copy.setQuartiere(istanzeStradarioSorgente.getQuartiere());
	copy.setScala(istanzeStradarioSorgente.getScala());
	if (istanzeStradarioSorgente.getStradario() != null) {
	    copy.setStradario(istanzeStradarioSorgente.getStradario());
	}
	if (istanzeStradarioSorgente.getStradariocolore() != null) {
	    copy.setStradariocolore(istanzeStradarioSorgente.getStradariocolore());
	}
	copy.setValido(istanzeStradarioSorgente.getValido());
	// Inserisco la lista di mappali
	Set<Istanzemappali> istanzemappalisTemp = new HashSet<Istanzemappali>();
	Istanzemappali istanzemappaliTemp = null;
	List<Istanzemappali> istanzemappalis = istanzemappaliService.findByIstanzaStradario(istanzeStradarioSorgente.getId().getCodice());
	for (Istanzemappali istanzemappaliSorgente : istanzemappalis) {
	    istanzemappaliTemp = new Istanzemappali();
	    PkId idMAppale = new PkId(istanzemappaliSorgente.getId().getCodice());
	    istanzemappaliTemp.setId(idMAppale);
	    istanzemappaliTemp.setCatasto(istanzemappaliSorgente.getCatasto());
	    istanzemappaliTemp.setDescrizioneEstesa(istanzemappaliSorgente.getDescrizioneEstesa());
	    istanzemappaliTemp.setFoglio(istanzemappaliSorgente.getFoglio());
	    istanzemappaliTemp.setIstanza(istanzemappaliSorgente.getIstanza());
	    istanzemappaliTemp.setIstanzestradario(istanzemappaliSorgente.getIstanzestradario());
	    istanzemappaliTemp.setParticella(istanzemappaliSorgente.getParticella());
	    if (istanzemappaliSorgente.getPrimario() != null) {
		istanzemappaliTemp.setPrimario(istanzemappaliSorgente.getPrimario());
	    }
	    istanzemappaliTemp.setSezione(istanzemappaliSorgente.getSezione());
	    istanzemappaliTemp.setSub(istanzemappaliSorgente.getSub());
	    istanzemappaliTemp.setTransientCodicecatasto(istanzemappaliSorgente.getTransientCodicecatasto());
	    istanzemappaliTemp.setUnitaimmob(istanzemappaliSorgente.getUnitaimmob());
	    istanzemappalisTemp.add(istanzemappaliTemp);
	}
	copy.setIstanzemappalis(istanzemappalisTemp);
	return copy;
    }

    @Override
    public void updateFildValido(Integer codiceIstanzaStradario, Boolean value) {

	istanzestradarioDAO.updateFieldValido(codiceIstanzaStradario, value);
    }

    @Override
    public void updateFieldCodicecivico(Integer codiceIstanzaStradario, String codiceCivico) {

	istanzestradarioDAO.updateFieldCodicecivico(codiceIstanzaStradario, codiceCivico);
    }

    @Override
    public List<Istanzestradario> findByTipiLocalizzazioni(Integer idLocalizzazioneTipo, Integer firstResult, Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("tipiLocalizzazioniId", idLocalizzazioneTipo, Integer.class));
	filterTable.addRestriction(filterRestriction);
	filterTable.addOrder(FilterUtils.orderAsc("id.codice"));
	return istanzestradarioDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public void trasformAndInsertVisuraInPdf(Integer codice, String html) {

	byte[] b = html.getBytes(Charset.forName("UTF-8"));
	FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
	ConvertBinaryRequest cbr = new ConvertBinaryRequest(ORMHelper.getToken(), b, "HTML", "PDF");
	ConvertBinaryResponse resp = null;
	try {
	    resp = fileConverterWsClient.convertBinary(cbr);
	} catch (Exception e) {
	    //log.error("Errore durante la conversione del file in PDF: {}, \n{}", e.getMessage(), e);
	    throw new RuntimeException("Errore durante la conversione del file in PDF: " + e.getMessage(), e);
	}
	Oggetti nuovoOggetto = new Oggetti();
	Date data = new Date();
	String _data = Utilities.formatDate(data, true);
	String nomeOggetto = "Visura_" + _data + ".pdf";
	nuovoOggetto.setNomefile(nomeOggetto);
	nuovoOggetto.setOggetto(resp.getBinaryData());
	oggettiService.insert(nuovoOggetto);
	Documentiistanza copia = new Documentiistanza();
	Istanze istanza = istanzeService.findById(new PkId(5674));
	copia.setIstanza(istanza);
	copia.setOggetto(nuovoOggetto);
	copia.setData(data);
	documentiistanzaService.insert(copia);
    }

    @Override
    public void updateSettaANullNonValidi() {

	istanzestradarioDAO.updateSettaANullNonValidi();
    }

    @Override
    public List<IstanzeStradarioRestBean> listaIstanzeStradarioRest(Integer codiceIstanza) {

	List<Istanzestradario> istanzestradarioList = this.findByIstanza(codiceIstanza);
	List<IstanzeStradarioRestBean> istanzeStradarioRestBeans = new ArrayList<IstanzeStradarioRestBean>();
	for (Istanzestradario istanzestradario : istanzestradarioList) {
	    istanzeStradarioRestBeans.add(initializeIstanzeStradarioRestBean(istanzestradario, codiceIstanza));
	}
	return istanzeStradarioRestBeans;
    }

    @Override
    public IstanzeStradarioRestBean dettaglioIstanzastradarioRest(Integer codiceIstanzastradario, Integer codiceIstanza) {

	Istanzestradario istanzestradario = this.findById(new PkId(codiceIstanzastradario));
	return initializeIstanzeStradarioRestBean(istanzestradario, codiceIstanza);
    }

    private IstanzeStradarioRestBean initializeIstanzeStradarioRestBean(Istanzestradario istanzestradario, Integer codiceIstanza) {

	IstanzeStradarioRestBean istanzeStradarioRestBean = new IstanzeStradarioRestBean();
	istanzeStradarioRestBean.setAccesso_descrizione(istanzestradario.getAccessoDescrizione());
	istanzeStradarioRestBean.setAccesso_numero(istanzestradario.getAccessoNumero());
	istanzeStradarioRestBean.setAccesso_tipo(istanzestradario.getAccessoTipo());
	istanzeStradarioRestBean.setCap(istanzestradario.getCap());
	istanzeStradarioRestBean.setCircoscrizione(istanzestradario.getCircoscrizione());
	istanzeStradarioRestBean.setCivico(istanzestradario.getCivico());
	istanzeStradarioRestBean.setCodice_civico(istanzestradario.getCodicecivico());
	istanzeStradarioRestBean.setCodice_istanza(codiceIstanza);
	istanzeStradarioRestBean.setCodice_stradario(istanzestradario.getStradario().getId().getCodice());
	if (istanzestradario.getStradariocolore() != null && istanzestradario.getStradariocolore().getId() != null
		&& StringUtils.isNotBlank(istanzestradario.getStradariocolore().getId().getCodicecolore())) {
	    istanzeStradarioRestBean.setColore(istanzestradario.getStradariocolore().getId().getCodicecolore());
	}
	istanzeStradarioRestBean.setEsponente(istanzestradario.getEsponente());
	istanzeStradarioRestBean.setEsponente_interno(istanzestradario.getEsponenteinterno());
	istanzeStradarioRestBean.setFabbricato(istanzestradario.getFabbricato());
	istanzeStradarioRestBean.setFrazione(istanzestradario.getFrazione());
	istanzeStradarioRestBean.setId(istanzestradario.getId().getCodice());
	istanzeStradarioRestBean.setId_comune(istanzestradario.getId().getIdcomune());
	istanzeStradarioRestBean.setId_punto_sit(istanzestradario.getIdPuntoSit());
	istanzeStradarioRestBean.setInterno(istanzestradario.getInterno());
	istanzeStradarioRestBean.setKm(istanzestradario.getKm());
	istanzeStradarioRestBean.setLatitudine(istanzestradario.getLatitudine());
	istanzeStradarioRestBean.setLongitudine(istanzestradario.getLongitudine());
	istanzeStradarioRestBean.setNote(istanzestradario.getNote());
	istanzeStradarioRestBean.setPiano(istanzestradario.getPiano());
	istanzeStradarioRestBean.setPrimario(istanzestradario.getPrimario());
	istanzeStradarioRestBean.setQuartiere(istanzestradario.getQuartiere());
	istanzeStradarioRestBean.setScala(istanzestradario.getScala());
	if (istanzestradario.getTipiLocalizzazioni() != null && istanzestradario.getTipiLocalizzazioni().getId() != null
		&& istanzestradario.getTipiLocalizzazioni().getId().getCodice() != null) {
	    istanzeStradarioRestBean.setTipi_localizzazioni_id(istanzestradario.getTipiLocalizzazioni().getId().getCodice());
	}
	istanzeStradarioRestBean.setUuid(istanzestradario.getUuid());
	istanzeStradarioRestBean.setValido(istanzestradario.getValido());
	return istanzeStradarioRestBean;
    }

    @Override
    public void deleteIstanzastradarioRest(Integer codiceIstanza, Integer id) {

	Istanzestradario strad = this.findById(new PkId(id));
	if (strad.getIstanza() != null && strad.getIstanza().getId() != null && strad.getIstanza().getId().getCodice() != null) {
	    if (strad.getIstanza().getId().getCodice().equals(codiceIstanza)) {
		this.delete(strad);
		return;
	    }
	}
	throw new RuntimeException("Istanzestradario non trovato per i dati codiceIstanza " + codiceIstanza + ", id" + id);
    }

    @Override
    public IstanzeStradarioRestBean insertIstanzastradarioRest(IstanzeStradarioRestBean bean) {

	Istanzestradario entity = new Istanzestradario();
	convertToIstanzeStradarioEntity(bean, entity, true);
	// DA BEAN A ENTITY
	this.insert(entity);
	// DA ENTITY A BEAN
	IstanzeStradarioRestBean result = initializeIstanzeStradarioRestBean(entity, bean.getCodice_istanza());
	return result;
    }

    @Override
    public IstanzeStradarioRestBean updateIstanzastradarioRest(IstanzeStradarioRestBean bean, Integer codiceIstanza, Integer id) {

	Istanzestradario entity = this.findById(new PkId(id));
	if (entity != null) {
	    if (entity.getIstanza() != null && entity.getIstanza().getId() != null && entity.getIstanza().getId().getCodice() != null) {
		if (entity.getIstanza().getId().getCodice().equals(codiceIstanza)) {
		    convertToIstanzeStradarioEntity(bean, entity, false);
		    // DA BEAN A ENTITY
		    this.update(entity);
		    // DA ENTITY A BEAN
		    IstanzeStradarioRestBean result = initializeIstanzeStradarioRestBean(entity, bean.getCodice_istanza());
		    return result;
		}
	    }
	}
	throw new RuntimeException("Istanzestradario non trovato per i dati codiceIstanza " + codiceIstanza + ", id" + id);
    }

    private void convertToIstanzeStradarioEntity(IstanzeStradarioRestBean bean, Istanzestradario istanzestradario, boolean isInsert) {

	istanzestradario.setAccessoDescrizione(bean.getAccesso_descrizione());
	istanzestradario.setAccessoNumero(bean.getAccesso_numero());
	istanzestradario.setAccessoTipo(bean.getAccesso_tipo());
	istanzestradario.setCap(bean.getCap());
	istanzestradario.setCircoscrizione(bean.getCircoscrizione());
	istanzestradario.setCivico(bean.getCivico());
	istanzestradario.setCodicecivico(bean.getCodice_civico());
	if (!isInsert) {
	    if (bean.getId() == null) {
		throw new RuntimeException("Istanzestradario non valido id nullo. Non posso aggiornare");
	    }
	} else {
	    Istanze istanze = istanzeService.findById(new PkId(bean.getCodice_istanza()));
	    istanzestradario.setIstanza(istanze);
	}
	Stradario stradario = stradarioService.findById(new PkId(bean.getCodice_stradario()));
	if (stradario == null) {
	    throw new RuntimeException("Istanzestradario non valido codicestradario non valido o nullo. rif: " + bean.getCodice_stradario());
	}
	istanzestradario.setStradario(stradario);
	if (StringUtils.isNotBlank(bean.getColore())) {
	    StradariocoloreId id = new StradariocoloreId();
	    id.setCodicecolore(bean.getColore());
	    Stradariocolore stradariocolore = stradariocoloreService.findById(id);
	    istanzestradario.setStradariocolore(stradariocolore);
	} else {
	    istanzestradario.setStradariocolore(null);
	}
	istanzestradario.setEsponente(bean.getEsponente());
	istanzestradario.setEsponenteinterno(bean.getEsponente_interno());
	istanzestradario.setPrimario(bean.getPrimario());
	istanzestradario.setFrazione(bean.getFrazione());
	istanzestradario.setId(new PkId(bean.getId_comune(), bean.getId()));
	istanzestradario.setIdPuntoSit(bean.getId_punto_sit());
	istanzestradario.setInterno(bean.getInterno());
	istanzestradario.setKm(bean.getKm());
	istanzestradario.setLatitudine(bean.getLatitudine());
	istanzestradario.setLongitudine(bean.getLongitudine());
	istanzestradario.setNote(bean.getNote());
	istanzestradario.setPiano(bean.getPiano());
	istanzestradario.setPrimario(bean.getPrimario());
	istanzestradario.setQuartiere(bean.getQuartiere());
	istanzestradario.setScala(bean.getScala());
	if (bean.getTipi_localizzazioni_id() != null) {
	    TipiLocalizzazioni tipiLocalizzazioni = tipiLocalizzazioniService.findById(new PkId(bean.getTipi_localizzazioni_id()));
	    istanzestradario.setTipiLocalizzazioni(tipiLocalizzazioni);
	} else {
	    istanzestradario.setTipiLocalizzazioni(null);
	}
	//istanzestradario.setUuid(bean.getUuid()); Questi valori non deve essere inclusi in update o inserimento in quanto generato dal sistema
	istanzestradario.setValido(bean.getValido());
    }

    @Override
    public Istanzestradario findByUuid(Integer codiceIstanza, String uuid) {

	return this.istanzestradarioDAO.findByUuid(codiceIstanza, uuid);
    }

    @Override
    public List<IstanzeStradarioExtendedDTO> findByIstanzeFilter(IstanzeFilter filter, Integer firstResult, Integer maxResult) {

	return this.istanzestradarioDAO.findByIstanzeFilter(filter, firstResult, maxResult);
    }

    @Override
    public List<IstanzeStradarioExtendedDTO> findByTmp(String token) {

	return this.istanzestradarioDAO.findByTmp(token);
    }

    @Override
    public IstanzeStradarioExtendedDTO findById(Integer idIstanzeStradario) {

	return this.istanzestradarioDAO.findById(idIstanzeStradario);
    }

    @Override
    public List<IstanzeStradarioExtendedDTO> findByAutorizzazioniFilter(DetachedCriteria autorizzazioniCriteria, Integer firstResult,
	    Integer maxResult) {

	return this.istanzestradarioDAO.findByAutorizzazioniFilter(autorizzazioniCriteria, firstResult, maxResult);
    }

    @Override
    public void updateCoordinateByUuId(Integer codiceIstanza, String uuIdLocalizzazione, BigDecimal latitudine, BigDecimal longitudine) {

	if (StringUtils.isBlank(uuIdLocalizzazione)) {
	    throw new RuntimeException("La localizzazione con UUID " + uuIdLocalizzazione + " non è stata trovata");
	}
	Istanzestradario localizzazione = this.findByUuid(codiceIstanza, uuIdLocalizzazione);
	if (localizzazione == null || localizzazione.getId() == null || localizzazione.getId().getCodice() == null) {
	    throw new RuntimeException("La localizzazione con UUID " + uuIdLocalizzazione + " non è stata trovata");
	}
	localizzazione.setLatitudine(latitudine.toString());
	localizzazione.setLongitudine(longitudine.toString());
	this.update(localizzazione);
    }
}
