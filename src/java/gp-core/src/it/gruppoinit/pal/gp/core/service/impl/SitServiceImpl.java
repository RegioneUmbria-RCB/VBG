package it.gruppoinit.pal.gp.core.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.axis.AxisFault;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.SitService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.RemoteCallException;
import it.gruppoinit.pal.gp.core.service.helper.SitCampiAmmessi;
import it.gruppoinit.pal.gp.core.service.helper.WebServiceClient;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.BaseDtoOfTipoVisualizzazioneString;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.DetailField;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.DetailSit;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.DettagliVia;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.FiltroRicercaListaVie;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.ListSit;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.Sit;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.SitFeatures;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.ValidateSit;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.WsSitSoap;

@Service
public class SitServiceImpl extends BaseServiceImpl<Sit, String> implements SitService {

    private static final Logger log = LoggerFactory.getLogger(SitServiceImpl.class);
    private StradarioService stradarioService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private ComuniService comuniService;
    private IstanzeService istanzeService;

    @Autowired
    public void setStradarioService(StradarioService stradarioService) {

	this.stradarioService = stradarioService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Override
    public List<String> getListaValori(String token, SitCampiAmmessi campo, Istanzestradario filter) throws RemoteCallException {

	WsSitSoap port = selectPortWsSit();
	List<String> listaValori = new ArrayList<String>();
	try {
	    Sit sitFilter = istanzeStradarioToSit(filter);
	    ListSit result = port.getListField(token, campo.getNome(), sitFilter, ORMHelper.getSoftware());
	    if (result.getField().length == 0) { // possibile eccezione o messaggio di errore
		if (StringUtils.isNotBlank(result.getMessageCode()) || StringUtils.isNotBlank(result.getMessage())) {
		    log.error("getListaValori: codice [{}], descrizione: {}", result.getMessageCode(), result.getMessage());
		    throw new BusinessValidationException(
			    "Errore nel recupero delle informazioni: codice [" + result.getMessageCode() + "], descrizione: " + result.getMessage());
		}
		return listaValori;
	    } else {
		listaValori = Arrays.asList(result.getField());
		return listaValori;
	    }
	} catch (AxisFault e) {
	    log.error("getListaValori: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	} catch (RemoteException e) {
	    log.error("getListaValori: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	}
    }

    @Override
    public List<Stradario> getListaVie(String token, FiltroRicercaListaVie filtroRicercaListaVie, List<String> codiciComuni)
	    throws RemoteCallException {

	WsSitSoap port = selectPortWsSit();
	List<Stradario> listaValori = new ArrayList<Stradario>();
	try {
	    String[] _codiciComuni = codiciComuni.toArray(new String[codiciComuni.size()]);
	    DettagliVia[] dettagliVias = port.getListaVie(token, ORMHelper.getSoftware(), filtroRicercaListaVie, _codiciComuni);
	    if (dettagliVias != null && dettagliVias.length > 0) {
		Stradario stradario = null;
		for (DettagliVia dettagliVia : dettagliVias) {
		    stradario = new Stradario();
		    stradario.setCodviario(dettagliVia.getCodiceViario());
		    Comuni comune = comuniService.findById(dettagliVia.getCodiceComune());
		    stradario.setComune(comune);
		    if (dettagliVia.getDataFineValidita() != null) {
			stradario.setDatavalidita(dettagliVia.getDataFineValidita().getTime());
		    }
		    stradario.setPrefisso(dettagliVia.getToponimo());
		    stradario.setDescrizione(dettagliVia.getDenominazione());
		    if (StringUtils.isNotBlank(dettagliVia.getLocalita())) {
			stradario.setLocfraz(dettagliVia.getLocalita());
		    }
		    listaValori.add(stradario);
		}
		return listaValori;
	    } else {
		log.debug("getListaVie: Non sono stati trovate vie per i parametri specicificati");
		return listaValori;
	    }
	} catch (AxisFault e) {
	    log.error("getListaVie: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	} catch (RemoteException e) {
	    log.error("getListaVie: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	}
    }

    @Override
    public Map<String, String> getDetailField(String token, SitCampiAmmessi campo, Istanzestradario filter) throws RemoteCallException {

	WsSitSoap port = selectPortWsSit();
	Map<String, String> map = new HashMap<String, String>();
	try {
	    Sit sitFilter = istanzeStradarioToSit(filter);
	    DetailSit result = port.getDetailField(token, campo.getNome(), sitFilter, ORMHelper.getSoftware());
	    if (result.getField() == null || result.getField().length == 0) { // possibile eccezione o messaggio di errore
		// I codice dell'errore sono mappati sul ws sit
		if (StringUtils.isNotBlank(result.getMessageCode()) && result.getMessageCode().equals("002")) {
		    log.error("getDetailField: codice [{}], descrizione: {}", result.getMessageCode(), result.getMessage());
		    throw new BusinessValidationException("Errore nel recupero delle informazioni della visura: codice [" +
			    result.getMessageCode() +
			    "], descrizione: " +
			    result.getMessage());
		}
		return map;
	    } else {
		map = this.getMappaDetaiSit(result);
		return map;
	    }
	} catch (AxisFault e) {
	    log.error("getDetailField: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	} catch (RemoteException e) {
	    log.error("getDetailField: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	}
    }

    private Map<String, String> getMappaDetaiSit(DetailSit result) {

	Map<String, String> map = new HashMap<String, String>();
	DetailField[] detailFields = result.getField();
	for (int i = 0; i < detailFields.length; i++) {
	    map.put(detailFields[i].getCampo(), detailFields[i].getValore());
	}
	return map;
    }

    @Override
    public Sit validaValore(String token, SitCampiAmmessi campo, Istanzestradario filter) throws RemoteCallException {

	WsSitSoap port = selectPortWsSit();
	try {
	    Sit sitFilter = istanzeStradarioToSit(filter);
	    ValidateSit result = port.validateField(token, campo.getNome(), sitFilter, ORMHelper.getSoftware());
	    if (!result.isReturnValue()) { // possibile eccezione o messaggio di errore
		if (StringUtils.isNotBlank(result.getMessageCode()) || StringUtils.isNotBlank(result.getMessage())) {
		    log.error("validaValore: codice [{}], descrizione: {}", result.getMessageCode(), result.getMessage());
		    throw new BusinessValidationException(
			    "Errore nella validazione: codice [" + result.getMessageCode() + "], descrizione: " + result.getMessage());
		}
		return null;
	    } else {
		return result.getDataSit();
	    }
	} catch (AxisFault e) {
	    log.error("validaValore: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	} catch (RemoteException e) {
	    log.error("validaValore: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	}
    }

    private Sit istanzeStradarioToSit(Istanzestradario filter) {

	boolean isStradario = false;
	if (EntityUtils.getNestedProperty(filter.getStradario(), "id.codice") != null) {
	    Stradario stradario = stradarioService.bindDomainObject(filter.getStradario(), PkId.class, "id.codice");
	    if (stradario != null) {
		filter.setStradario(stradario);
		isStradario = true;
	    }
	}
	Sit sitFilter = new Sit();
	// TODO VERIFICA NPE
	Istanze ist = istanzeService.findById(new PkId(filter.getIstanza().getId().getCodice()));
	if (ist != null) {
	    sitFilter.setCodiceComune(ist.getComune().getCodicecomune());
	} else {
	    sitFilter.setCodiceComune(filter.getIstanza().getComune().getCodicecomune());
	}
	sitFilter.setCAP(filter.getCap());
	sitFilter.setCircoscrizione(filter.getCircoscrizione());
	sitFilter.setCivico(filter.getCivico());
	sitFilter.setCodCivico(filter.getCodicecivico());
	sitFilter.setAccessoTipo(filter.getAccessoTipo());
	sitFilter.setAccessoNumero(filter.getAccessoNumero());
	sitFilter.setAccessoDescrizione(filter.getAccessoDescrizione());
	if (isStradario) {
	    if (StringUtils.isNotBlank(filter.getStradario().getCodviario())) {
		sitFilter.setCodVia(filter.getStradario().getCodviario());
	    } else {
		sitFilter.setCodVia(String.valueOf(filter.getStradario().getId().getCodice()));
	    }
	}
	if (filter.getStradariocolore() != null) {
	    sitFilter.setColore(filter.getStradariocolore().getId().getCodicecolore());
	}
	if (isStradario) {
	    sitFilter.setDescrizioneVia(filter.getStradario().getDescrizioneCompleta());
	}
	sitFilter.setEsponente(filter.getEsponente());
	sitFilter.setEsponenteInterno(filter.getEsponenteinterno());
	sitFilter.setFabbricato(filter.getFabbricato());
	sitFilter.setFrazione(filter.getFrazione());
	sitFilter.setIdComune(ORMHelper.getIdcomune());
	sitFilter.setInterno(filter.getInterno());
	sitFilter.setKm(filter.getKm());
	sitFilter.setPiano(filter.getPiano());
	sitFilter.setQuartiere(filter.getQuartiere());
	sitFilter.setScala(filter.getScala());
	if (isStradario) {
	    if (EntityUtils.getNestedProperty(filter.getStradario().getStradariozone(), "id.codice") != null) {
		sitFilter.setZona(filter.getStradario().getStradariozone().getZona());
	    }
	}
	if (filter.getIstanzemappalis().size() == 1) {
	    for (Istanzemappali mappale : filter.getIstanzemappalis()) {
		sitFilter.setSezione(mappale.getSezione());
		sitFilter.setFoglio(mappale.getFoglio());
		sitFilter.setParticella(mappale.getParticella());
		sitFilter.setSub(mappale.getSub());
		sitFilter.setTipoCatasto(mappale.getCatasto().getCodice());
		sitFilter.setUI(mappale.getUnitaimmob());
	    }
	} else {
	    sitFilter.setTipoCatasto("F");
	}
	// dati mappali
	return sitFilter;
    }

    private Map<String, Set<String>> campiGestitiCache = new HashMap<String, Set<String>>();

    private String getMapKey(String idcomunealias) {

	return idcomunealias;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	this.campiGestitiCache = new HashMap<String, Set<String>>();
	this.campiGestitoDettaglioCache = new HashMap<String, Map<String, String>>();
    }

    @Override
    public Set<String> getCampiGestiti(String token, String software) {

	//1. Verifico se presente in cache
	String key = getMapKey(ORMHelper.getIdcomuneAlias());
	if (campiGestitiCache == null) {
	    campiGestitiCache = new HashMap<String, Set<String>>();
	} else {
	    if (campiGestitiCache.get(key) != null) {
		return campiGestitiCache.get(key);
	    }
	}
	//2. Li chiedo al SIT
	try {
	    Set<String> listaValori = this.getCampiGestitiInternal(token, software);
	    campiGestitiCache.put(key, listaValori);
	    return listaValori;
	} catch (RemoteCallException e) {
	    log.error("Si è verificata un anomalia nel recupero delle informazioni [getCampiGestiti] dal servizio SIT: {}", e.getMessage());
	}
	//3. Torno una lista vuota
	return new HashSet<String>();
    }

    private Set<String> getCampiGestitiInternal(String token, String software) throws RemoteCallException {

	WsSitSoap port = selectPortWsSit();
	Set<String> listaValori = new HashSet<String>();
	try {
	    String[] result = port.getCampiGestiti(token, software);
	    if (result != null) {
		for (String campo : result) {
		    SitCampiAmmessi value = SitCampiAmmessi.fromNome(campo);
		    listaValori.add(value.name().toLowerCase());
		}
	    }
	    return listaValori;
	} catch (AxisFault e) {
	    log.error("getCampiGestiti: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	} catch (RemoteException e) {
	    log.error("getCampiGestiti: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	}
    }

    private Map<String, Map<String, String>> campiGestitoDettaglioCache = new HashMap<String, Map<String, String>>();

    @Override
    public Map<String, String> getCampoDettaglioGestito(String token, String software) {

	String key = getMapKey(ORMHelper.getIdcomuneAlias());
	if (campiGestitoDettaglioCache == null) {
	    campiGestitoDettaglioCache = new HashMap<String, Map<String, String>>();
	} else {
	    if (campiGestitoDettaglioCache.get(key) != null) {
		return campiGestitoDettaglioCache.get(key);
	    }
	}
	try {
	    Map<String, String> listaValori = this.getCampiGestitoDettaglioInternal(token, software);
	    campiGestitoDettaglioCache.put(key, listaValori);
	    return listaValori;
	} catch (RemoteCallException e) {
	    log.error("Si è verificata un anomalia nel recupero delle informazioni [getCampiGestitoDettaglio] dal servizio SIT: {}", e.getMessage());
	}
	return new HashMap<String, String>();
    }

    private Map<String, String> getCampiGestitoDettaglioInternal(String token, String software) throws RemoteCallException {

	WsSitSoap port = selectPortWsSit();
	Map<String, String> listaValori = new HashMap<String, String>();
	try {
	    String[] result = port.getCampiGestiti(token, software);
	    if (result != null) {
		for (String campo : result) {
		    SitCampiAmmessi value = SitCampiAmmessi.fromNome(campo);
		    if (campo.contains("@dett")) {
			// Se c'è il tag dettaglio devo controllare se è in aggiunta a quello
			// di ricerca o deve essere unico
			// dett+ : aggiungo a quello di ricerca anche il dett
			// dett  : mostro solo il dett
			String iSdett_ = (campo.contains("dett+") ? "1" : "0");
			listaValori.put(value.name().toLowerCase(), iSdett_);
		    } else {
			listaValori.put(value.name().toLowerCase(), "false");
		    }
		}
	    }
	    return listaValori;
	} catch (AxisFault e) {
	    log.error("getCampiGestiti: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	} catch (RemoteException e) {
	    log.error("getCampiGestiti: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	}
    }

    @Override
    public boolean effettuaValidazioneFormale(String token, String software, Istanzestradario filter) throws RemoteCallException {

	WsSitSoap port = selectPortWsSit();
	boolean b = false;
	try {
	    Sit sitFilter = istanzeStradarioToSit(filter);
	    b = port.effettuaValidazioneFormale(token, software, sitFilter);
	} catch (AxisFault e) {
	    log.error("effettuaValidazioneFormale: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	} catch (RemoteException e) {
	    log.error("effettuaValidazioneFormale: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	}
	return b;
    }

    @Override
    public Map<String, String> getBoFeatures(String token, String software) throws RemoteCallException {

	Map<String, String> res = new HashMap<String, String>();
	WsSitSoap port = selectPortWsSit();
	SitFeatures b = new SitFeatures();
	try {
	    b = port.getFeatures(token, software);
	} catch (AxisFault e) {
	    log.error("getFeatures: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	} catch (RemoteException e) {
	    log.error("getFeatures: {}, \n{}", e.getMessage(), e);
	    throw new RemoteCallException(e.getMessage(), e);
	}
	if (b != null) {
	    BaseDtoOfTipoVisualizzazioneString[] s = b.getVisualizzazioniBackoffice();
	    if (s != null) {
		for (BaseDtoOfTipoVisualizzazioneString dto : s) {
		    res.put(dto.getCodice().getValue(), dto.getDescrizione());
		}
	    }
	}
	return res;
    }

    /**
     * <pre>
     * Il metodo sceglie quale port istanziare per lachiamata al componente di integrazione sit.
     *    1. Se il parametro URL_WSSIT della verticalizzazione SIT_ATTIVO è popolato allora verrà chiamato il 
     *       componetente di integrazione SIT presente all'indirizzo configurato su URL_WSSIT
     *    2. Se il parametro URL_WSSIT della verticalizzazione SIT_ATTIVO non è popolato allora verrà chiamato il componente .Net di deafult    
     * 
     * @return
     * </pre>
     */
    private WsSitSoap selectPortWsSit() {

	// Ricerca se il sit chiama il componente di intregrazione ws sit in java
	Verticalizzazioniparametri urlWsS = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO,
		WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO_URL_WSSIT);
	log.debug("Url componente ws java sit");
	WsSitSoap port = null;
	if (urlWsS != null && StringUtils.isNotBlank(urlWsS.getValore())) {
	    log.debug("Url componenete ws java sit chiamato : {}", urlWsS.getValore());
	    port = WebServiceClient.getWsSITPort(urlWsS.getValore());
	} else {
	    log.debug("Chiamato componente standard in dotNet");
	    port = WebServiceClient.getWsSITPort();
	}
	return port;
    }

    @Override
    public void insert(Sit entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(Sit entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(Sit entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<Sit> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public Sit findById(String id) {

	throw new NotImplementedException();
    }

    @Override
    protected Class<Sit> getEntityClass() {

	return Sit.class;
    }
}
