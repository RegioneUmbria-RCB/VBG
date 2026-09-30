package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Areedettagli;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzearee;
import it.gruppoinit.pal.gp.core.domain.IstanzeareeId;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.RicalcoloFilter;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.RicalcoloRequest;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AreeService;
import it.gruppoinit.pal.gp.core.service.AreedettagliService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

/**
 * 
 * @author francescop
 * @author gianpaolot
 */
@Service
public class IstanzeareeServiceImpl extends BaseServiceImpl<Istanzearee, IstanzeareeId> implements IstanzeareeService {

    private static final Logger log = LoggerFactory.getLogger(IstanzeareeServiceImpl.class);
    private IstanzeareeDAO istanzeareeDAO;
    private IstanzeService istanzeService;
    private AreedettagliService areedettagliService;
    private AreeService areeService;
    private IstanzestradarioService istanzestradarioService;
    private ComuniassociatiService comuniAssociatiService;

    @Autowired
    public void setComuniAssociatiService(ComuniassociatiService comuniAssociatiService) {

	this.comuniAssociatiService = comuniAssociatiService;
    }

    @Autowired
    public void setIstanzestradarioService(IstanzestradarioService istanzestradarioService) {

	this.istanzestradarioService = istanzestradarioService;
    }

    @Autowired
    public void setAreeService(AreeService areeService) {

	this.areeService = areeService;
    }

    @Autowired
    public void setAreedettagliService(AreedettagliService areedettagliService) {

	this.areedettagliService = areedettagliService;
    }

    @Autowired
    public void setIstanzeareeDAO(IstanzeareeDAO istanzeareeDAO) {

	this.istanzeareeDAO = istanzeareeDAO;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Override
    protected Class<Istanzearee> getEntityClass() {

	return Istanzearee.class;
    }

    @Override
    public List<Istanzearee> findAll(Integer firstResult, Integer maxResult) {

	return istanzeareeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzearee entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeareeDAO.insert(entity);
	    childDataInsert(entity);
	}
    }

    @Override
    public Istanzearee findById(IstanzeareeId id) {

	return istanzeareeDAO.findById(id);
    }

    @Override
    public void update(Istanzearee entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeareeDAO.update(entity);
	    childDataUpdate(entity);
	}
    }

    @Override
    public void delete(Istanzearee entity) {

	if (isDeleteAllowed(entity)) {
	    istanzeareeDAO.delete(entity);
	}
    }

    public List<Istanzearee> findByFilterTable(FilterTable filterTable) {

	return istanzeareeDAO.findByFilterTable(filterTable);
    }

    @Override
    public Istanzearee findByPrimarioIstanza(Istanze istanza) {

	return istanzeareeDAO.findByPrimarioIstanza(istanza);
    }

    @Override
    public void ricalcolaAree(Date dallaData, Date allaData) {

	Integer codiceIstanzaCorrente = -1;
	try {
	    RicalcoloFilter filter = new RicalcoloFilter();
	    if (!ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
	    filter.setSoftware(ORMHelper.getSoftware());
	    }
	    filter.setDallaData(dallaData);
	    filter.setAllaData(allaData);
	    List<Integer> codiciIstanza = this.findCodiciIstanzaPerRicalcolo(filter);
	    for (Integer codiceIstanza : codiciIstanza) {
		codiceIstanzaCorrente = codiceIstanza;
		this.ricalcolaPerIstanza(codiceIstanza);
	    }
	} catch (Exception ex) {
	    throw new RuntimeException("Errore nell'elaborazione dell'istanza " + codiceIstanzaCorrente + ": " + ex.getLocalizedMessage());
	}
    }

    private String checkCivicoNumeric(String civico) {

	String answer = civico;
	String patternStr = "^([0-9]+)";
	Pattern pattern = Pattern.compile(patternStr);
	Matcher matcher = pattern.matcher(civico);
	if (matcher.find()) {
	    answer = (matcher.group());
	}
	return answer;
    }

    @Override
    public void insertAltraAreea(Istanzearee istanzearee) {

	if (istanzearee.getPrimario() == true) {
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("istanza", istanzearee.getIstanza(), Istanze.class));
	    fr.addFilterField(FilterUtils.equals("primario", true, Boolean.class));
	    filterTable.addRestriction(fr);
	    List<Istanzearee> list = this.findByFilterTable(filterTable);
	    if (!list.isEmpty()) {
		Istanzearee istanzeareePrimario = list.get(0);
		istanzeareePrimario.setPrimario(Boolean.valueOf(false));
	    }
	    istanzearee.getId().setCodiceistanza(istanzearee.getIstanza().getId().getCodice());
	    istanzearee.getId().setCodicearea(istanzearee.getArea().getId().getCodice());
	    this.insert(istanzearee);
	} else {
	    istanzearee.getId().setCodiceistanza(istanzearee.getIstanza().getId().getCodice());
	    istanzearee.getId().setCodicearea(istanzearee.getArea().getId().getCodice());
	    this.insert(istanzearee);
	}
    }

    @Override
    public List<Istanzearee> findByIstanza(Istanze istanza) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanza", istanza, Istanze.class));
	filterTable.addOrder(FilterUtils.orderDesc("primario"));
	filterTable.addRestriction(fr);
	List<Istanzearee> list = this.findByFilterTable(filterTable);
	return list;
    }

    @Override
    public void updatePrimario(Istanzearee istanzearee) {

	Istanzearee primario = this.findByPrimarioIstanza(istanzearee.getIstanza());
	if (primario != null) {
	    primario.setPrimario(Boolean.valueOf(false));
	    this.update(primario);
	}
	istanzearee.setPrimario(Boolean.valueOf(true));
	this.update(istanzearee);
    }

    private void childDataInsert(Istanzearee entity) {

	// non previste implementazioni
    }

    private void childDataUpdate(Istanzearee entity) {

	// non previste implementazioni
    }

    private void dataIntegration(Istanzearee entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'istanza procedimento passata è nulla");
	}
	if (entity.getAutoins() == null)
	    entity.setAutoins(Boolean.valueOf(false));
	if (entity.getPrimario() == null)
	    entity.setPrimario(Boolean.valueOf(false));
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Istanzearee entity) {

	Istanze istanze = istanzeService.bindDomainObject(entity.getIstanza(), PkId.class, "id.codice");
	entity.setIstanza(istanze);
	Aree aree = areeService.bindDomainObject(entity.getArea(), PkId.class, "id.codice");
	entity.setArea(aree);
	if (istanze != null) {
	    entity.getId().setCodiceistanza(istanze.getId().getCodice());
	} else {
	    if (entity.getId().getCodiceistanza() != null) {
		istanze = istanzeService.findById(new PkId(entity.getId().getCodiceistanza()));
		entity.setIstanza(istanze);
	    }
	}
	if (aree != null) {
	    entity.getId().setCodicearea(aree.getId().getCodice());
	} else {
	    if (entity.getId().getCodicearea() != null) {
		aree = areeService.findById(new PkId(entity.getId().getCodicearea()));
		entity.setArea(aree);
	    }
	}
    }

    @Override
    public void updateIstanzaareaPrimaria(Istanzearee istanzearee) {

	// Controllo se dal form ho passato un oggetto istanza area primario
	if (EntityUtils.getNestedProperty(istanzearee, "id.codicearea") != null) {
	    // 1. controllo che ci sia un record su istanze aree con primario
	    Istanzearee istanzeareePrimario = this.findByPrimarioIstanza(istanzearee.getIstanza());
	    //1.1 Controllo se esiste il primario
	    if (istanzeareePrimario != null) {
		//1.1.1 Controllo che quello passato e quello nel db siano diversi
		if (!istanzeareePrimario.getId().getCodicearea().equals(istanzearee.getId().getCodicearea())) {
		    // cancello il primario precedente
		    this.delete(istanzeareePrimario);
		    // Cerco se quella che voglio inserire non sia già presente in istanze , ma non come primario
		    Istanzearee istanzeareeTemp = this.findById(istanzearee.getId());
		    // Se è già presente la setto come primario
		    if (istanzeareeTemp != null) {
			istanzeareeTemp.setPrimario(true);
			this.update(istanzeareeTemp);
		    } else {// non è presente inserisco un nuovo oggetto istanze area come primario
			Aree area = areeService.findById(new PkId(istanzearee.getId().getCodicearea()));
			istanzearee.setArea(area);
			istanzearee.setPrimario(true);
			istanzearee.setAutoins(false);
			this.insert(istanzearee);
		    }
		}
	    } else // se non è presente faccio una nuova insert primaria
	    {
		Aree area = areeService.findById(new PkId(istanzearee.getId().getCodicearea()));
		istanzearee.setArea(area);
		istanzearee.setPrimario(true);
		istanzearee.setAutoins(false);
		this.insert(istanzearee);
	    }
	} else {// non ho passato nessun oggetto istanza area primario
		// 1. controllo che ci sia un record su istanze aree con primario
	    Istanzearee istanzeareePrimario = this.findByPrimarioIstanza(istanzearee.getIstanza());
	    //1.1 Se esiste lo cancello
	    if (istanzeareePrimario != null) {
		this.delete(istanzeareePrimario);
		// vedo se esistono record in istanze aree non primario, se li trovo setto 
		// il primo trovato come primario.
		List<Istanzearee> listAreeIstanza = this.findByIstanza(istanzearee.getIstanza());
		if (!listAreeIstanza.isEmpty()) {
		    Istanzearee istanzeareeTemp = listAreeIstanza.get(0);
		    istanzeareeTemp.setPrimario(true);
		    this.update(istanzeareeTemp);
		}
	    }
	}
    }

    @Override
    public void insertFromIstanzeStradario(Istanzestradario istanzestradario) {

	RicalcoloRequest request = RicalcoloRequest.fromIstanzestradario(istanzestradario);
	//ricalcolo le aree in base al civico, se presente
	boolean isCivico = this.ricalcolaAreeInBaseAlCivico(request);
	//ricalcolo le aree in base al km, se presente
	boolean isKm = this.ricalcolaAreeInBaseAlKm(request);
	if (!isCivico && !isKm) {
	    // non sono presenti ne civico ne KM
	    // gestione delle zone dello stradario, la logica implementata avrà queste regole:
	    //- se lo stradario non ha il civico o il km ma è associato ad una sola zona collegata allora viene collegata a questa
	    //- se lo stradario è associato a più zone si comporta come ora e quindi non associa la zona.
	    this.ricalcolaInBaseAllArea(request);
	}
	istanzeareeDAO.flush();
	istanzeareeDAO.clear();
	// §§§END§§§
    }

    private void ricalcolaInBaseAllArea(RicalcoloRequest request) {

	Integer idIstanzeStradario = request.getIdIstanzeStradario();
	log.debug("ricalcolaInBaseAllArea: elaboro istanzestradario id {}", idIstanzeStradario);
	Integer codiceStradario = request.getCodiceStradario();
	List<Areedettagli> areelist = areedettagliService.findByStradario(codiceStradario);
	log.debug("ricalcolaInBaseAllArea: trovate {} aree per lo stradario", areelist.size());
	if (areelist.size() == 1) {
	    Integer codiceIstanza = request.getCodiceIstanza();
	    boolean areaPrimariaPresente = this.istanzeareeDAO.existsPrimario(codiceIstanza);
	    for (Areedettagli areedettagli : areelist) {
		Aree area = areedettagli.getAree();
		if (!this.istanzeareeDAO.exists(codiceIstanza, areedettagli.getAree().getId().getCodice())) {
		    Istanzearee istanzearee = new Istanzearee();
		    IstanzeareeId id = new IstanzeareeId();
		    id.setCodicearea(area.getId().getCodice());
		    id.setCodiceistanza(request.getCodiceIstanza());
		    istanzearee.setId(id);
		    istanzearee.setAutoins(true);
		    istanzearee.setPrimario(!areaPrimariaPresente);
		    istanzearee.setIstanza(request.getIstanza());
		    istanzearee.setArea(area);
		    // 9) LA PRIMA CON PRIMARIO=1
		    this.insert(istanzearee);
		    if (log.isDebugEnabled()) {
			log.debug("ricalcolaInBaseAllArea: inserisco istanzearee {}, primario {}",
				ReflectionToStringBuilder.toString(id, ToStringStyle.MULTI_LINE_STYLE), areaPrimariaPresente);
		    }
		    areaPrimariaPresente = true;
		}
	    }
	}
    }

    private boolean ricalcolaAreeInBaseAlKm(RicalcoloRequest request) {

	if (StringUtils.isBlank(request.getKm())) {
	    return false;
	}
	Integer idIstanzeStradario = request.getIdIstanzeStradario();
	Integer codiceIstanza = request.getCodiceIstanza();
	Integer codiceStradario = request.getCodiceStradario();
	log.debug("ricalcolaAreeInBaseAlKm: elaboro istanzestradario id {} con km {}", idIstanzeStradario, request.getKm());
	BigDecimal km = null;
	try {
	    km = new BigDecimal(request.getKm().replace(",", "."));
	} catch (Exception e) {
	    log.debug("ricalcolaAreeInBaseAlKm: errore nel trasformare il km in numero decimale {}", request.getKm());
	    return true;
	}
	List<Areedettagli> aree = areedettagliService.findByStradarioEKm(codiceStradario, km);
	log.debug("ricalcolaAreeInBaseAlKm: trovate {} aree per lo stradario/km indicato", aree.size());
	if (aree.isEmpty()) {
	    return true;
	}
	boolean areaPrimariaPresente = this.istanzeareeDAO.existsPrimario(codiceIstanza);
	for (Areedettagli dettaglio : aree) {
	    if (!this.istanzeareeDAO.exists(codiceIstanza, dettaglio.getAree().getId().getCodice())) {
		Istanzearee istanzearee = new Istanzearee();
		IstanzeareeId id = new IstanzeareeId();
		id.setCodicearea(dettaglio.getAree().getId().getCodice());
		id.setCodiceistanza(codiceIstanza);
		istanzearee.setId(id);
		istanzearee.setAutoins(true);
		istanzearee.setPrimario(!areaPrimariaPresente);
		istanzearee.setIstanza(request.getIstanza());
		istanzearee.setArea(dettaglio.getAree());
		// 9) LA PRIMA CON PRIMARIO=1
		this.insert(istanzearee);
		if (log.isDebugEnabled()) {
		    log.debug("ricalcolaAreeInBaseAlKm: inserisco istanzearee {}, primario {}",
			    ReflectionToStringBuilder.toString(id, ToStringStyle.MULTI_LINE_STYLE), areaPrimariaPresente);
		}
		areaPrimariaPresente = true;
	    }
	}
	return true;
    }

    private boolean ricalcolaAreeInBaseAlCivico(RicalcoloRequest request) {

	if (StringUtils.isBlank(request.getCivico())) {
	    return false;
	}
	Integer civico = null;
	try {
	    civico = Integer.parseInt(checkCivicoNumeric(request.getCivico()));
	} catch (Exception e) {
	    log.debug("insertFromIstanzeStradario: errore nel trasformare il civico in numerico {}", request.getCivico());
	    return true;
	}
	Integer idIstanzeStradario = request.getIdIstanzeStradario();
	log.debug("insertFromIstanzeStradario: elaboro istanzestradario id {} con civico {}", idIstanzeStradario, civico);
	Integer codiceStradario = request.getCodiceStradario();
	List<Areedettagli> areelist = areedettagliService.findByStradarioECivico(codiceStradario, civico);
	log.debug("insertFromIstanzeStradario: trovate {} aree per lo stradario", areelist.size());
	if (areelist.isEmpty()) {
	    return true;
	}
	boolean isDispari = civico % 2 != 0;
	log.debug("insertFromIstanzeStradario: il civico è dispari? [{}]", isDispari);
	Integer codiceIstanza = request.getCodiceIstanza();
	boolean areaPrimariaPresente = this.istanzeareeDAO.existsPrimario(codiceIstanza);
	for (Areedettagli areedettagli : areelist) {
	    Boolean pariDispari = areedettagli.getParidispari();
	    log.debug("insertFromIstanzeStradario: l'area con id {} è pari? [{}]", areedettagli.getId().getCodice(),
		    (pariDispari == null ? "non definita" : pariDispari.booleanValue()));
	    // faccio attenzione a pari dispari
	    if (pariDispari == null || pariDispari.booleanValue() != isDispari) {
		Aree area = areedettagli.getAree();
		if (!this.istanzeareeDAO.exists(codiceIstanza, areedettagli.getAree().getId().getCodice())) {
		    Istanzearee istanzearee = new Istanzearee();
		    IstanzeareeId id = new IstanzeareeId();
		    id.setCodicearea(area.getId().getCodice());
		    id.setCodiceistanza(request.getCodiceIstanza());
		    istanzearee.setId(id);
		    istanzearee.setAutoins(true);
		    istanzearee.setPrimario(!areaPrimariaPresente);
		    istanzearee.setIstanza(request.getIstanza());
		    istanzearee.setArea(area);
		    // 9) LA PRIMA CON PRIMARIO=1
		    this.insert(istanzearee);
		    if (log.isDebugEnabled()) {
			log.debug("insertFromIstanzeStradario: inserisco istanzearee {}, primario {}",
				ReflectionToStringBuilder.toString(id, ToStringStyle.MULTI_LINE_STYLE), areaPrimariaPresente);
		    }
		    areaPrimariaPresente = true;
		}
	    }
	}
	return true;
    }

    @Override
    public void ricalcolaPerIstanza(Integer codiceistanza) {

	log.debug("ricalcolaPerIstanza: inizio elaborazione per l'istanza codice {} ", codiceistanza);
	log.debug("ricalcolaPerIstanza: eliminazione di istanzearee con autoins = 1  ");
	this.istanzeareeDAO.eliminaAreeAutoins(codiceistanza);
	List<Istanzestradario> istanzestradarios = istanzestradarioService.findByIstanza(codiceistanza);
	for (Istanzestradario istanzestradario : istanzestradarios) {
	    this.insertFromIstanzeStradario(istanzestradario);
	}
	this.istanzeareeDAO.flush();
	this.istanzeareeDAO.commit();
	log.debug("ricalcolaPerIstanza: fine elaborazione per l'istanza codice {} ", codiceistanza);
    }

    @Override
    public List<Integer> findCodiciIstanzaPerRicalcolo(RicalcoloFilter filter) {

	String[] comuniAbilitati = null;
	boolean isComuniAssociati = this.comuniAssociatiService.isComuniassociati(ORMHelper.getIdcomune());
	if (isComuniAssociati) {
	    if (log.isDebugEnabled()) {
		log.debug("buildQuery# E' un installazione multicomune, recupero i comuni configurati per l'opertaore {} ({})");
	    }
	    List<Responsabilicomuni> responsabilicomunis = this.comuniAssociatiService.checkComuniAbilitatiPerResponsabile(false);
	    if (responsabilicomunis != null && !responsabilicomunis.isEmpty()) {
		comuniAbilitati = new String[responsabilicomunis.size()];
		for (int i = 0; i < responsabilicomunis.size(); i++) {
		    Responsabilicomuni responsabilicomunimuni = (Responsabilicomuni) responsabilicomunis.get(i);
		    comuniAbilitati[i] = responsabilicomunimuni.getId().getCodicecomune();
		}
	    }
	}
	return this.istanzeareeDAO.findCodiciIstanzaPerRicalcolo(filter, comuniAbilitati);
    }
}
