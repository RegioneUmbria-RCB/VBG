package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.VerticalizzazioniDAO;
import it.gruppoinit.pal.gp.core.dao.VerticalizzazioniparametriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Softwareattivi;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazionibase;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametribase;
import it.gruppoinit.pal.gp.core.domain.helper.ConfigurazioneRegoleParametroHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ConfigurazioneRegoleSoftwareHelper;
import it.gruppoinit.pal.gp.core.domain.helper.VerticalizzazioniconfigurazioniHelper;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareattiviService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazionibaseService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametribaseService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerticalizzazioniServiceImpl extends BaseServiceImpl<Verticalizzazioni, PkId> implements VerticalizzazioniService {

    private VerticalizzazioniDAO verticalizzazioniDAO;
    private VerticalizzazioniparametriService verticalizzazioniparametriService;
    private VerticalizzazioniparametribaseService verticalizzazioniparametribaseService;
    private VerticalizzazioniparametriDAO verticalizzazioniparametriDAO;
    private ResponsabiliService responsabiliService;
    private ComuniassociatiService comuniassociatiService;
    private ComuniService comuniService;
    private SoftwareService softwareService;
    private VerticalizzazionibaseService verticalizzazionibaseService;
    private UserSecurityService userSecurityService;
    private Map<String, Boolean> installazioniMap;
    private Map<String, Boolean> verticalizzazioniAttive;
    private SoftwareattiviService softwareattiviService;

    @Autowired
    public void setSoftwareattiviService(SoftwareattiviService softwareattiviService) {

	this.softwareattiviService = softwareattiviService;
    }

    @Autowired
    public void setVerticalizzazioniparametribaseService(VerticalizzazioniparametribaseService verticalizzazioniparametribaseService) {

	this.verticalizzazioniparametribaseService = verticalizzazioniparametribaseService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setVerticalizzazioniDAO(VerticalizzazioniDAO verticalizzazioniDAO) {

	this.verticalizzazioniDAO = verticalizzazioniDAO;
    }

    @Autowired
    public void setVerticalizzazioniparametriService(VerticalizzazioniparametriService verticalizzazioniparametriService) {

	this.verticalizzazioniparametriService = verticalizzazioniparametriService;
    }

    @Autowired
    public void setVerticalizzazioniparametriDAO(VerticalizzazioniparametriDAO verticalizzazioniparametriDAO) {

	this.verticalizzazioniparametriDAO = verticalizzazioniparametriDAO;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setVerticalizzazionibaseService(VerticalizzazionibaseService verticalizzazionibaseService) {

	this.verticalizzazionibaseService = verticalizzazionibaseService;
    }

    @Override
    protected Class<Verticalizzazioni> getEntityClass() {

	return Verticalizzazioni.class;
    }

    @Override
    public void delete(Verticalizzazioni entity) {

	verticalizzazioniDAO.delete(entity);
	resetObjectCached();
    }

    @Override
    public List<Verticalizzazioni> findAll(Integer firstResult, Integer maxResult) {

	return verticalizzazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Verticalizzazioni findById(PkId id) {

	return verticalizzazioniDAO.findById(id);
    }

    @Override
    public void insert(Verticalizzazioni entity) {

	dataIntegration(entity);
	//if (validateEntity(entity) && insertAllowed(entity)) {
	if (validateEntity(entity) && insertAllowed(entity)) {
	    verticalizzazioniDAO.insert(entity);
	}
	resetObjectCached();
    }

    @Override
    public void update(Verticalizzazioni entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    verticalizzazioniDAO.update(entity);
	    resetObjectCached();
	}
    }

    private void dataIntegration(Verticalizzazioni entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro entity non può essere nullo");
	}
	if (entity.getAttivo() == null) {
	    entity.setAttivo(0);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Verticalizzazioni entity) {

	Comuni c = comuniService.bindDomainObject(entity.getComune(), String.class, "codicecomune");
	entity.setComune(c);
	Verticalizzazionibase vb = verticalizzazionibaseService.bindDomainObject(entity.getVerticalizzazionibase(), String.class, "modulo");
	entity.setVerticalizzazionibase(vb);
    }

    @Override
    public Verticalizzazioniparametri getVerticalizzazioniparametri(String modulo, String parametro) {

	return this.getVerticalizzazioniparametri(modulo, parametro, false);
    }

    @Override
    public Verticalizzazioniparametri getVerticalizzazioniparametriPerComune(String modulo, String parametro, String codiceComune) {

	boolean isAttiva = this.isAttivaPerComune(modulo, codiceComune);
	if (isAttiva) {
	    return verticalizzazioniparametriDAO.findByModuloAndParametroAndComune(modulo, parametro, codiceComune);
	}
	return null;
    }

    @Override
    public Verticalizzazioniparametri getVerticalizzazioniparametri(String modulo, String parametro, String codiceSoftware) {

	Verticalizzazioni verticalizzazioni = this.findByModulo(modulo);
	Verticalizzazioniparametri verticalizzazioniparametri = null;
	if (verticalizzazioni != null && verticalizzazioni.getAttivo() == 1) {
	    verticalizzazioniparametri = verticalizzazioniparametriDAO.findByModuloAndParametroAndSoftware(modulo, parametro, codiceSoftware);
	}
	return verticalizzazioniparametri;
    }

    @Override
    public String getVerticalizzazioniparametriValore(String modulo, String parametro) {

	Verticalizzazioniparametri vp = getVerticalizzazioniparametri(modulo, parametro);
	if (vp != null) {
	    return vp.getValore();
	}
	return null;
    }

    @Override
    public Verticalizzazioni findByModulo(String modulo) {

	return this.findByModuloEComune(modulo, null);
    }

    @Override
    public boolean isAttiva(String modulo) {

	return isAttiva(modulo, ORMHelper.getSoftware());
    }

    private String getVerticalizzazioniAttiveKey(String idcomuneAlias, String software, String modulo, String codiceComune) {

	String locCodiceComune = codiceComune;
	if (StringUtils.isBlank(codiceComune)) {
	    locCodiceComune = "TUTTI";
	}
	return idcomuneAlias + "-" + software + "-" + modulo + "-" + locCodiceComune;
    }

    @Override
    public List<Verticalizzazioni> findByVerticalizzazionibase(Verticalizzazionibase verticalizzazionibase) {

	return verticalizzazioniDAO.findByVerticalizzazionibase(verticalizzazionibase);
    }

    @Override
    public Set<Verticalizzazioni> findByVerticalizzazionibaseAndCheckConfigurabilePerOperatore(Verticalizzazionibase verticalizzazionibase) {

	List<Verticalizzazioni> verticalizzazionis = this.findByVerticalizzazionibase(verticalizzazionibase);
	// Recupero il Responsabile loggato
	LoggedUser userDetail = (LoggedUser) userSecurityService.getCurrentlyAuthenticatedUser();
	Responsabili responsabile = responsabiliService.findById(new PkId(userDetail.getCodiceResponsabile()));
	Set<Responsabilisoftware> softwaresAbilitati = responsabile.getSoftwareAbilitati();
	// controllo per ogni verticalizzazione se il software per cui è configurata è ablitato per l'utente loggato
	for (Verticalizzazioni verticalizzazioni : verticalizzazionis) {
	    for (Responsabilisoftware responsabilisoftware : softwaresAbilitati) {
		if (verticalizzazioni.getSoftware().getCodice().equals(responsabilisoftware.getSoftware().getCodice())) {
		    verticalizzazioni.setFlagSoftwarePerAbilitatotransiet(true);
		    break;
		}
	    }
	    // non è stato trovato il sotware abilitato per l'utente allora lo setto a false
	    if (verticalizzazioni.getFlagSoftwarePerAbilitatotransiet() == null)
		verticalizzazioni.setFlagSoftwarePerAbilitatotransiet(false);
	}
	Set<Verticalizzazioni> setVerticalizzazioni = new LinkedHashSet<Verticalizzazioni>();
	for (Verticalizzazioni verticalizzazioni : verticalizzazionis) {
	    setVerticalizzazioni.add(verticalizzazioni);
	}
	return setVerticalizzazioni;
    }

    private boolean insertAllowed(Verticalizzazioni entity) {

	boolean isInsert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	List<Verticalizzazioni> list = this.findByVerticalizzazionibase(entity.getVerticalizzazionibase());
	String software = entity.getSoftware().getCodice();
	String comune = "";
	if (entity.getComune() != null) {
	    if (StringUtils.isNotBlank(entity.getComune().getCodicecomune())) {
		comune = entity.getComune().getCodicecomune();
	    }
	}
	for (Verticalizzazioni verticalizzazioni : list) {
	    String vSoftware = verticalizzazioni.getSoftware().getCodice();
	    String vComune = "";
	    if (verticalizzazioni.getComune() != null) {
		if (StringUtils.isNotBlank(verticalizzazioni.getComune().getCodicecomune())) {
		    vComune = verticalizzazioni.getComune().getCodicecomune();
		}
	    }
	    if (vSoftware.equals(software) && vComune.equals(comune)) {
		_ivs.add(new InvalidValue("service_error.software_configurato", null, null, null, null));
		this.throwValidationMessages(_ivs);
	    }
	}
	return isInsert;
    }

    @Override
    public boolean isAttiva(String modulo, String software) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("verticalizzazionibase.modulo", modulo, String.class));
	fr.addFilterField(FilterUtils.in("software.codice", new String[] { software, "TT" }, String.class));
	fr.addFilterField(FilterUtils.equals("attivo", 1, Integer.class));
	ft.addRestriction(fr);
	List<Verticalizzazioni> vert = verticalizzazioniDAO.findByFilterTable(ft);
	return vert.size() > 0;
    }

    @Override
    public List<Verticalizzazioniparametri> getVerticalizzazioniparametri(String modulo) {

	return verticalizzazioniparametriService.findParametriConfiguratiByModulo(modulo);
    }

    @Override
    public Map<String, String> getVerticalizzazioniparametriMap(String modulo) {

	Map<String, String> map = new HashMap<String, String>();
	List<Verticalizzazioniparametri> list = verticalizzazioniparametriService.findParametriConfiguratiByModulo(modulo);
	for (Verticalizzazioniparametri vp : list) {
	    map.put(vp.getVerticalizzazioniparametribase().getId().getParametro(), vp.getValore());
	}
	return map;
    }

    private Map<String, Boolean> getInstallazioniMap() {

	if (installazioniMap == null) {
	    installazioniMap = new HashMap<String, Boolean>();
	}
	return installazioniMap;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	installazioniMap = new HashMap<String, Boolean>();
	verticalizzazioniAttive = new HashMap<String, Boolean>();
    }

    @Override
    public boolean isInstallazioneEnterprise() {

	Map<String, Boolean> map = getInstallazioniMap();
	boolean isBusiness = true;
	if (map.get(ORMHelper.getIdcomune()) != null) {
	    isBusiness = map.get(ORMHelper.getIdcomune());
	} else {
	    if (isAttiva(WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE)) {
		Verticalizzazioniparametri vert = verticalizzazioniparametriDAO.findByModuloAndParametro(
			WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE, WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_TIPO);
		if (vert != null) {
		    if (StringUtils.defaultIfEmpty(vert.getValore(), WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_TIPO_ENTERPRISE)
			    .equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_TIPO_STANDARD)) {
			isBusiness = false;
		    }
		}
	    }
	    map.put(ORMHelper.getIdcomune(), new Boolean(isBusiness));
	}
	return isBusiness;
    }

    @Override
    public boolean isAttivaAndParametroEqualsToValore(String modulo, String parametro, String valore) {

	boolean success = false;
	Verticalizzazioniparametri vp = this.getVerticalizzazioniparametri(modulo, parametro);
	if (vp != null) {
	    if (vp.getValore() != null && vp.getValore().trim().equalsIgnoreCase(valore)) {
		success = true;
	    }
	}
	return success;
    }

    @Override
    public Verticalizzazioni findByComuneAndModulo(Verticalizzazionibase verticalizzazionibase, String codicecomune, String software) {

	if (verticalizzazionibase == null) {
	    throw new IllegalArgumentException(
		    "VerticalizzazioniServiceImpl.findByComuneAndModulo: l'oggetto verticalizzazionibase non può essere null");
	}
	if (StringUtils.isBlank(software)) {
	    throw new IllegalArgumentException("VerticalizzazioniServiceImpl.findByComuneAndModulo: Il software non può essere null o stringa vuota");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("verticalizzazionibase", verticalizzazionibase, Verticalizzazionibase.class));
	fr.addFilterField(FilterUtils.equals("codice", software, "software", String.class));
	if (StringUtils.isNotBlank(codicecomune)) {
	    fr.addFilterField(FilterUtils.equals("codicecomune", codicecomune, "comune", String.class));
	} else {
	    fr.addFilterField(FilterUtils.isNull("comune"));
	}
	filterTable.addRestriction(fr);
	List<Verticalizzazioni> verticalizzazionis = verticalizzazioniDAO.findByFilterTable(filterTable);
	if (!verticalizzazionis.isEmpty()) {
	    return verticalizzazionis.get(0);
	}
	return null;
    }

    @Override
    public void insert(String modulo, String codicecomune, String software) {

	if (modulo == null) {
	    throw new IllegalArgumentException("VerticalizzazioniServiceImpl.insert: Il modulo non può essere null o stringa vuota");
	}
	if (StringUtils.isBlank(software)) {
	    throw new IllegalArgumentException("VerticalizzazioniServiceImpl.insert: Il software non può essere null o stringa vuota");
	}
	//	VerticalizzazioniId id = new VerticalizzazioniId();
	//	id.setIdcomune(ORMHelper.getIdcomune());
	//	id.setModulo(modulo);
	//	id.setSoftware(software);
	Verticalizzazioni verticalizzazioni = new Verticalizzazioni();
	// La setto de default attiva;
	verticalizzazioni.setAttivo(1);
	// Il comune se non viene passato, viene settato  a null, la verticalizzazione vale per tutti i comuni
	if (StringUtils.isNotBlank(codicecomune)) {
	    Comuni comune = comuniService.findById(codicecomune);
	    verticalizzazioni.setComune(comune);
	} else {
	    verticalizzazioni.setComune(null);
	}
	Verticalizzazionibase vert = verticalizzazionibaseService.findById(modulo);
	Software softwareOggetto = softwareService.findById(software);
	verticalizzazioni.setVerticalizzazionibase(vert);
	verticalizzazioni.setSoftware(softwareOggetto);
	Verticalizzazionibase verticalizzazionibase = verticalizzazionibaseService.findById(modulo);
	verticalizzazioni.setVerticalizzazionibase(verticalizzazionibase);
	this.insert(verticalizzazioni);
    }

    @Override
    public void delete(String modulo, String codicecomune, String software) {

	if (modulo == null) {
	    throw new IllegalArgumentException("VerticalizzazioniServiceImpl.delete: Il modulo non può essere null o stringa vuota");
	}
	if (StringUtils.isBlank(software)) {
	    throw new IllegalArgumentException("VerticalizzazioniServiceImpl.delete: Il software non può essere null o stringa vuota");
	}
	Verticalizzazionibase verticalizzazionibase = verticalizzazionibaseService.findById(modulo);
	Verticalizzazioni verticalizzazioni = this.findByComuneAndModulo(verticalizzazionibase, codicecomune, software);
	delete(verticalizzazioni);
    }

    @Override
    public boolean isAttivaPerComune(String modulo, String codiceComune) {

	boolean attivo = false;
	if (verticalizzazioniAttive == null) {
	    verticalizzazioniAttive = new HashMap<String, Boolean>();
	}
	String key = getVerticalizzazioniAttiveKey(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware(), modulo, codiceComune);
	if (verticalizzazioniAttive.get(key) == null) {
	    Verticalizzazioni verticalizzazioni = this.findByModuloEComune(modulo, codiceComune);
	    if (verticalizzazioni != null) {
		if (verticalizzazioni.getAttivo() != null) {
		    attivo = BooleanUtils.toBooleanObject(verticalizzazioni.getAttivo());
		}
	    }
	    verticalizzazioniAttive.put(key, Boolean.valueOf(attivo));
	} else {
	    attivo = verticalizzazioniAttive.get(key);
	}
	return attivo;
    }

    @Override
    public boolean isAttivaPerComuneESoftware(String modulo, String software, String codiceComune) {

	boolean attivo = false;
	if (verticalizzazioniAttive == null) {
	    verticalizzazioniAttive = new HashMap<String, Boolean>();
	}
	String key = getVerticalizzazioniAttiveKey(ORMHelper.getIdcomuneAlias(), software, modulo, codiceComune);
	if (verticalizzazioniAttive.get(key) == null) {
	    Verticalizzazioni verticalizzazioni = this.findByModuloEComuneESoftware(modulo, codiceComune, software);
	    if (verticalizzazioni != null) {
		if (verticalizzazioni.getAttivo() != null) {
		    attivo = BooleanUtils.toBooleanObject(verticalizzazioni.getAttivo());
		}
	    }
	    verticalizzazioniAttive.put(key, Boolean.valueOf(attivo));
	} else {
	    attivo = verticalizzazioniAttive.get(key);
	}
	return attivo;
    }

    @Override
    public Verticalizzazioni findByModuloEComune(String modulo, String codiceComune) {

	return verticalizzazioniDAO.findByModuloEComuneESoftware(modulo, codiceComune, null);
    }

    private Verticalizzazioni findByModuloEComuneESoftware(String modulo, String codiceComune, String software) {

	return verticalizzazioniDAO.findByModuloEComuneESoftware(modulo, codiceComune, null);
    }

    @Override
    public Verticalizzazioniparametri getVerticalizzazioniparametriPerComuneESoftware(String modulo, String parametro, String codiceComune,
	    String software) {

	return verticalizzazioniparametriDAO.findByModuloAndParametroAndSoftwareAndComune(modulo, parametro, software, codiceComune);
    }

    @Override
    public boolean checkConfigurazioneMultiplaPerComune(String modulo, String parametro) {

	if (!comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune())) {
	    return false;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.modulo", modulo, "verticalizzazioniparametribase", String.class));
	fr.addFilterField(FilterUtils.equals("id.parametro", parametro, "verticalizzazioniparametribase", String.class));
	ft.addRestriction(fr);
	List<Verticalizzazioniparametri> list = verticalizzazioniparametriDAO.findByFilterTable(ft, 0, 10);
	Set<String> comuni = new HashSet<String>();
	for (Verticalizzazioniparametri vp : list) {
	    String codiceComune = vp.getComune() == null ? "TUTTI" : vp.getComune().getCodicecomune();
	    comuni.add(codiceComune);
	}
	return comuni.size() > 1;
    }

    @Override
    public boolean checkConfigurazioneMultiplaPerSoftware(String modulo, String parametro) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.modulo", modulo, "verticalizzazioniparametribase", String.class));
	fr.addFilterField(FilterUtils.equals("id.parametro", parametro, "verticalizzazioniparametribase", String.class));
	ft.addRestriction(fr);
	List<Verticalizzazioniparametri> list = verticalizzazioniparametriDAO.findByFilterTable(ft, 0, 10);
	Set<String> softwareConfigurati = new HashSet<String>();
	for (Verticalizzazioniparametri vp : list) {
	    String software = vp.getSoftware().getCodice();
	    softwareConfigurati.add(software);
	}
	return softwareConfigurati.size() > 1;
    }

    @Override
    public List<VerticalizzazioniconfigurazioniHelper> findListaConfigurazioniPerComuneESoftware(String modulo, String parametro) {

	List<Comuniassociati> ass = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.modulo", modulo, "verticalizzazioniparametribase", String.class));
	fr.addFilterField(FilterUtils.equals("id.parametro", parametro, "verticalizzazioniparametribase", String.class));
	if (!ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
	    fr.addFilterField(FilterUtils.in("codice", new String[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }, "software", String.class));
	}
	ft.addRestriction(fr);
	List<Verticalizzazioniparametri> list = verticalizzazioniparametriDAO.findByFilterTable(ft);
	List<VerticalizzazioniconfigurazioniHelper> helpers = new ArrayList<VerticalizzazioniconfigurazioniHelper>();
	HashMap<String, VerticalizzazioniconfigurazioniHelper> m = new HashMap<String, VerticalizzazioniconfigurazioniHelper>(0);
	for (Verticalizzazioniparametri vp : list) {
	    String codiceComune = "TUTTI";
	    String software = vp.getSoftware().getCodice();
	    if (vp.getComune() == null) {
		// vale per tutti i comuni
		if (!checkConfigurazioneMultiplaPerComune(modulo, parametro)) {
		    VerticalizzazioniconfigurazioniHelper h = new VerticalizzazioniconfigurazioniHelper();
		    h.setModulo(modulo);
		    h.setParametro(parametro);
		    h.setValore(vp.getValore());
		    h.setComune(vp.getComune());
		    h.setSoftware(vp.getSoftware());
		    m.put(codiceComune + "-" + software, h);
		    // helpers.add(h);
		} else {
		    for (Comuniassociati ca : ass) {
			codiceComune = ca.getComune().getCodicecomune();
			VerticalizzazioniconfigurazioniHelper h = new VerticalizzazioniconfigurazioniHelper();
			h.setModulo(modulo);
			h.setParametro(parametro);
			h.setValore(vp.getValore());
			h.setComune(ca.getComune());
			h.setSoftware(vp.getSoftware());
			// helpers.add(h);
			m.put(codiceComune + "-" + software, h);
		    }
		}
	    } else {
		codiceComune = vp.getComune().getCodicecomune();
		VerticalizzazioniconfigurazioniHelper h = new VerticalizzazioniconfigurazioniHelper();
		h.setModulo(modulo);
		h.setParametro(parametro);
		h.setValore(vp.getValore());
		h.setComune(vp.getComune());
		h.setSoftware(vp.getSoftware());
		m.put(codiceComune + "-" + software, h);
	    }
	}
	for (String k : m.keySet()) {
	    VerticalizzazioniconfigurazioniHelper h = m.get(k);
	    helpers.add(h);
	}
	Collections.sort(helpers, new VerticalizzazioniconfigurazioniHelper());
	return helpers;
    }

    @Override
    public List<ConfigurazioneRegoleParametroHelper> findConfigurazioneComune(String modulo, String codiceComune) {

	List<ConfigurazioneRegoleParametroHelper> result = new ArrayList<ConfigurazioneRegoleParametroHelper>();
	List<Verticalizzazioniparametribase> list = verticalizzazioniparametribaseService.findByModulo(modulo);
	for (Verticalizzazioniparametribase vpb : list) {
	    List<Softwareattivi> softs = softwareattiviService.findAllAndExcludeTT(false);
	    ConfigurazioneRegoleParametroHelper rh = new ConfigurazioneRegoleParametroHelper();
	    rh.setParametro(vpb.getId().getParametro());
	    rh.setDescrizioneParametro(vpb.getDescrizione());
	    List<ConfigurazioneRegoleSoftwareHelper> valori = new ArrayList<ConfigurazioneRegoleSoftwareHelper>();
	    for (Softwareattivi sa : softs) {
		Verticalizzazioniparametri param = getVerticalizzazioniparametriPerComuneESoftware(modulo, vpb.getId().getParametro(), codiceComune,
			sa.getId().getFkSoftware());
		ConfigurazioneRegoleSoftwareHelper h = new ConfigurazioneRegoleSoftwareHelper();
		h.setSoftware(sa.getSoftware().getDescrizione());
		h.setCodiceSoftware(sa.getSoftware().getCodice());
		h.setValore(param == null ? "" : param.getValore());
		valori.add(h);
	    }
	    Collections.sort(valori, new ConfigurazioneRegoleSoftwareHelper());
	    rh.setValori(valori);
	    result.add(rh);
	}
	Collections.sort(result, new ConfigurazioneRegoleParametroHelper());
	return result;
    }

    @Override
    public Verticalizzazioniparametri getVerticalizzazioniparametri(String modulo, String parametro, boolean ignoraVerticalizzazioneAttiva) {

	boolean isAttiva = true;
	if (!ignoraVerticalizzazioneAttiva) {
	    isAttiva = this.isAttiva(modulo);
	}
	Verticalizzazioniparametri verticalizzazioniparametri = null;
	if (isAttiva) {
	    verticalizzazioniparametri = verticalizzazioniparametriDAO.findByModuloAndParametro(modulo, parametro);
	}
	return verticalizzazioniparametri;
    }
}
