package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeAccessoAttiTDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiAnagrafe;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiAnagrafeId;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiD;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiLog;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.VwIstanzecollegate;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeAccessoAttiDHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeAccessoAttiTHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzecollegateHelper;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiAnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiDService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiLogService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiTService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.VwIstanzecollegateService;

import java.util.ArrayList;
import java.util.List;

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
public class IstanzeAccessoAttiTServiceImpl extends BaseServiceImpl<IstanzeAccessoAttiT, PkId> implements IstanzeAccessoAttiTService {

    private static final Logger log = LoggerFactory.getLogger(IstanzeAccessoAttiTServiceImpl.class);
    private IstanzeAccessoAttiTDAO istanzeaccessoattitDAO;
    private ResponsabiliService responsabiliService;
    private IstanzeService istanzeService;
    private IstanzeAccessoAttiDService istanzeAccessoAttiDService;
    private IstanzeAccessoAttiAnagrafeService istanzeAccessoAttiAnagrafeService;
    private IstanzeAccessoAttiLogService istanzeAccessoAttiLogService;
    private VwIstanzecollegateService vwistanzecollegateService;

    @Autowired
    public void setVwistanzecollegateService(VwIstanzecollegateService vwistanzecollegateService) {

	this.vwistanzecollegateService = vwistanzecollegateService;
    }

    @Autowired
    public void setIstanzeAccessoAttiTDAO(IstanzeAccessoAttiTDAO istanzeaccessoattitDAO) {

	this.istanzeaccessoattitDAO = istanzeaccessoattitDAO;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzeAccessoAttiDService(IstanzeAccessoAttiDService istanzeAccessoAttiDService) {

	this.istanzeAccessoAttiDService = istanzeAccessoAttiDService;
    }

    @Autowired
    public void setIstanzeAccessoAttiAnagrafeService(IstanzeAccessoAttiAnagrafeService istanzeAccessoAttiAnagrafeService) {

	this.istanzeAccessoAttiAnagrafeService = istanzeAccessoAttiAnagrafeService;
    }

    @Autowired
    public void setIstanzeAccessoAttiLogService(IstanzeAccessoAttiLogService istanzeAccessoAttiLogService) {

	this.istanzeAccessoAttiLogService = istanzeAccessoAttiLogService;
    }

    @Override
    protected Class<IstanzeAccessoAttiT> getEntityClass() {

	return IstanzeAccessoAttiT.class;
    }

    @Override
    public List<IstanzeAccessoAttiT> findAll(Integer firstResult, Integer maxResult) {

	return istanzeaccessoattitDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IstanzeAccessoAttiT entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeaccessoattitDAO.insert(entity);
	    childDataInsert(entity);
	}
    }

    private void childDataInsert(IstanzeAccessoAttiT entity) {

	if (entity.getIstanze() != null) {
	    if (entity.getIstanze().getRichiedente() != null) {
		IstanzeAccessoAttiAnagrafe e1 = new IstanzeAccessoAttiAnagrafe();
		IstanzeAccessoAttiAnagrafeId id = new IstanzeAccessoAttiAnagrafeId(entity.getId().getCodice(), entity.getIstanze().getRichiedente()
			.getId().getCodice());
		e1.setId(id);
		e1.setIstanzeAccessoAttiT(entity);
		e1.setAnagrafe(entity.getIstanze().getRichiedente());
		istanzeAccessoAttiAnagrafeService.insert(e1);
	    }
	}
    }

    @Override
    public IstanzeAccessoAttiT findById(PkId id) {

	return istanzeaccessoattitDAO.findById(id);
    }

    @Override
    public void update(IstanzeAccessoAttiT entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeaccessoattitDAO.update(entity);
	}
    }

    @Override
    public void delete(IstanzeAccessoAttiT entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    istanzeaccessoattitDAO.delete(entity);
	}
    }

    private void dataIntegration(IstanzeAccessoAttiT entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'istanza di accesso agli atti è nulla");
	}
	fixMergeEntityProperties(entity);
	if (entity.getFlgPubblica() == null) {
	    entity.setFlgPubblica(Boolean.FALSE);
	}
    }

    protected void fixMergeEntityProperties(IstanzeAccessoAttiT entity) {

	Responsabili resp = responsabiliService.bindDomainObject(entity.getResponsabili(), PkId.class, "id.codice");
	entity.setResponsabili(resp);
	Istanze ist = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(ist);
    }

    @Override
    protected boolean isDeleteAllowed(IstanzeAccessoAttiT entity) {

	boolean delete = true;
	//	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//	List<IstanzeAccessoAttiD> istanzeAccessoAttiDs = istanzeAccessoAttiDService.findByIstanzeAccessoAttiT(entity.getId().getCodice());
	//	if (!istanzeAccessoAttiDs.isEmpty()) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ISTANZE_ACCESSO_ATTI_D", null));
	//	    delete = false;
	//	}
	//	List<IstanzeAccessoAttiLog> istanzeAccessoAttiLogs = istanzeAccessoAttiLogService.findByIstanzeAccessoAttiT(entity.getId().getCodice(), null,
	//		null);
	//	if (!istanzeAccessoAttiLogs.isEmpty()) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ISTANZE_ACCESSO_ATTI_LOG", null));
	//	    delete = false;
	//	}
	//	List<IstanzeAccessoAttiAnagrafe> istanzeAccessoAttiAnagrafes = istanzeAccessoAttiAnagrafeService.findByIstanzeAccessoAttiT(entity.getId()
	//		.getCodice(), null, null);
	//	if (!istanzeAccessoAttiAnagrafes.isEmpty()) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ISTANZE_ACCESSO_ATTI_ANAGRAFE", null));
	//	    delete = false;
	//	}
	//	if (!_ivs.isEmpty()) {
	//	    this.throwValidationMessages(_ivs);
	//	}
	return delete;
    }

    /**
     * <pre>
     * Il metodo ritorna una lista dell'oggetto IstanzeAccessoAttiTHelper :
     * 1. codiceIstanzeAttiT   			: codice del fascicolo
     * 2. istanzaaccessoattiDHelper		: oggetto che rappresenta l'istanza principale del fascicolo
     * 3. List<istanzaaccessoattiDHelper>	: lista di oggetti che rappresentano le istanze collegate al fascicolo
     * </pre>
     */
    @Override
    public List<IstanzeAccessoAttiTHelper> findIstanzeCollegate(Integer codiceIstanzaAccessoAttiT, String[] codiceIstanze,
	    String[] codiciIstanzeFlagSelezionato) {

	List<IstanzeAccessoAttiTHelper> resultList = new ArrayList<IstanzeAccessoAttiTHelper>();
	List<IstanzeAccessoAttiDHelper> accessoAttiDHelpers = null;
	IstanzeAccessoAttiDHelper accessoAttiDHelper = null;
	IstanzeAccessoAttiTHelper accessoAttiTHelper = null;
	// per ogni codice istanza selezionata ricerco se sono presenti delle istanze collegate e creo un oggetto IstanzeAccessoAttiTHelper:
	// codiceIstanzeAttiT 			: Integer.codiceIstanzaAccessoAttiT
	// IstanzaaccessoattiDHelper 		: recupero le informazioni dall'oggetto istanza recuperato per il codice istanza in esame
	// List<istanzaaccessoattiDHelper>	: lo popolo con le informazioni delle istanze recuperate dalla vista vwistanzecollegateService
	// l'array codiciIstanzeFlagSelezionato mi permette di definire il valore IstanzeAccessoAttiDHelper.flgVisualizzaDoc che indica se mostrare o no 
	// solo i doc validi.
	// Il valore sarà true se il codice istanza in esame sarà presente nell'array codiciIstanzeFlagSelezionato, altrimenti sarà false
	for (int i = 0; i < codiceIstanze.length; i++) {
	    // creao l'oggetto IstanzeAccessoAttiTHelper...
	    log.debug("findIstanzeCollegate# codice istanza = {}", codiceIstanze);
	    accessoAttiTHelper = new IstanzeAccessoAttiTHelper();
	    Istanze istanza = istanzeService.findById(new PkId(Integer.parseInt(codiceIstanze[i])));
	    List<IstanzecollegateHelper> istanzeaccessoattitcollegates = vwistanzecollegateService.findIstanzecollegateByIstanza(istanza);
	    accessoAttiDHelper = new IstanzeAccessoAttiDHelper();
	    accessoAttiDHelper.setCodiceIstanza(istanza.getId().getCodice());
	    accessoAttiDHelper.setNumeroistanza(istanza.getNumeroistanza());
	    accessoAttiDHelper.setDescrizioneRichiedente(istanza.getRichiedente().getDescrizioneRichiedente());
	    Integer flgVisualizzaDocSelezionato = 0;
	    for (String impostazione : codiciIstanzeFlagSelezionato) {
		if (impostazione.startsWith((String) codiceIstanze[i] + "-")) {
		    flgVisualizzaDocSelezionato = Integer.parseInt(impostazione.replace(((String) codiceIstanze[i] + "-"), ""));
		    break;
		}
	    }
	    log.debug("findIstanzeCollegate# codice istanza = {}, flgVisualizzaDocSelezionato = {} ", codiceIstanze, flgVisualizzaDocSelezionato);
	    accessoAttiDHelper.setFlgVisualizzaDoc(flgVisualizzaDocSelezionato);
	    accessoAttiTHelper.setIstanzaaccessoattiDHelper(accessoAttiDHelper);
	    accessoAttiTHelper.setCodiceIstanzeAttiT(codiceIstanzaAccessoAttiT);
	    accessoAttiDHelpers = new ArrayList<IstanzeAccessoAttiDHelper>();
	    // Creo la List<istanzaaccessoattiDHelper> per ogni istanza collegate 
	    for (IstanzecollegateHelper istanzecollegateHelper : istanzeaccessoattitcollegates) {
		IstanzeAccessoAttiDHelper istanzeAccessoAttiDHelpercol = null;
		List<VwIstanzecollegate> vwIstanzecollegates = istanzecollegateHelper.getIstanzecollegates();
		for (VwIstanzecollegate vwIstanzecollegate : vwIstanzecollegates) {
		    if (!istanza.getId().getCodice().equals(vwIstanzecollegate.getIstanza().getId().getCodice())) {
			istanzeAccessoAttiDHelpercol = new IstanzeAccessoAttiDHelper();
			istanzeAccessoAttiDHelpercol.setCodiceIstanza(vwIstanzecollegate.getIstanza().getId().getCodice());
			istanzeAccessoAttiDHelpercol.setNumeroistanza(vwIstanzecollegate.getIstanza().getNumeroistanza());
			istanzeAccessoAttiDHelpercol
				.setDescrizioneRichiedente(vwIstanzecollegate.getIstanza().getRichiedente().getDescrizioneRichiedente());
			istanzeAccessoAttiDHelpercol.setFlgVisualizzaDoc(0);
			accessoAttiDHelpers.add(istanzeAccessoAttiDHelpercol);
		    }
		}
		accessoAttiTHelper.setIstanzaaccessoattiDHelpers(accessoAttiDHelpers);
	    }
	    if (accessoAttiTHelper.getIstanzaaccessoattiDHelpers() == null) {
		accessoAttiTHelper.setIstanzaaccessoattiDHelpers(new ArrayList<IstanzeAccessoAttiDHelper>());
	    }
	    resultList.add(accessoAttiTHelper);
	}
	return resultList;
    }

    @Override
    public List<IstanzeAccessoAttiT> findByIstanza(Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanze", Integer.class));
	ft.addRestriction(fr);
	ft.addRestriction(fr);
	return istanzeaccessoattitDAO.findByFilterTable(ft);
    }

    @Override
    protected boolean validateEntity(IstanzeAccessoAttiT entity) {

	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	if (entity.getDatafine() == null || entity.getDatainizio() == null) {
	    InvalidValue iv = new InvalidValue("service_error.accesso_atti.data_null.alert", entity.getClass(), "datafine", null, entity);
	    ivs.add(iv);
	    throwValidationMessages(ivs);
	}
	if (entity.getDatafine().before(entity.getDatainizio())) {
	    InvalidValue iv = new InvalidValue("service_error.accesso_atti.data.alert", entity.getClass(), "datafine", null, entity);
	    ivs.add(iv);
	}
	if (ivs.size() > 0) {
	    throwValidationMessages(ivs);
	}
	return super.validateEntity(entity);
    }

    protected void childDelete(IstanzeAccessoAttiT entity) {

	//a. ISTANZE_ACCESSO_ATTI_D
	List<IstanzeAccessoAttiD> istanzeaccessoattiDs = istanzeAccessoAttiDService.findByIstanzeAccessoAttiT(entity.getId().getCodice());
	if (!istanzeaccessoattiDs.isEmpty()) {
	    for (IstanzeAccessoAttiD istanzeaccessoattiD : istanzeaccessoattiDs) {
		istanzeAccessoAttiDService.delete(istanzeaccessoattiD);
	    }
	}
	//b. ISTANZE_ACCESSO_ATTI_ANAGRAFE
	List<IstanzeAccessoAttiAnagrafe> istanzeaccessoattiAnagrafes = istanzeAccessoAttiAnagrafeService
		.findByIstanzeAccessoAttiT(entity.getId().getCodice(), null, null);
	if (!istanzeaccessoattiAnagrafes.isEmpty()) {
	    for (IstanzeAccessoAttiAnagrafe istanzeaccessoattiAnagrafe : istanzeaccessoattiAnagrafes) {
		istanzeAccessoAttiAnagrafeService.delete(istanzeaccessoattiAnagrafe);
	    }
	}
	//c. ISTANZE_ACESSO_ATTI_LOG
	List<IstanzeAccessoAttiLog> istanzeaccessoattiLogs = istanzeAccessoAttiLogService.findByIstanzeAccessoAttiT(entity.getId().getCodice(), null,
		null);
	if (!istanzeaccessoattiLogs.isEmpty()) {
	    for (IstanzeAccessoAttiLog istanzeaccessoattiLog : istanzeaccessoattiLogs) {
		istanzeAccessoAttiLogService.delete(istanzeaccessoattiLog);
	    }
	}
    }
}
