package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiId;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.attivita.datilocalizzativi.LocalizzazioniAttivitaDTO;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoSchedaDinamicaIstanzaSalvata;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.CallbackBean;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.CartograficoClient;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.ComuneBean;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.GetInfoResponse;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.InnescoRequest;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.InnescoResponse;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.LocalizzazioneBean;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.ParametriResponse;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.ParametroResponse;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.verticalizzazione.IVerticalizzazioneCartograficoAttivoService;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.verticalizzazione.VerticalizzazioneCartograficoAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeStradarioExtendedDTO;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitService;
import it.gruppoinit.pal.gp.core.service.StradarioService;

@Service
public class CartograficoServiceImpl implements ICartograficoService {

    private static final Logger logger = LoggerFactory.getLogger(CartograficoServiceImpl.class);
    private IstanzestradarioService istanzestradarioService;
    private Dyn2ModellitService dyn2ModellitService;
    private Dyn2CampiService campiService;
    private Istanzedyn2modellitService istanzedyn2modellitService;
    private Istanzedyn2datiService istanzedyn2datiService;
    private IEventPublisher publisher;
    private VerticalizzazioniService verticalizzazioniService;
    private StradarioService stradarioService;

    @Autowired
    public void setIstanzestradarioService(IstanzestradarioService istanzestradarioService) {

	this.istanzestradarioService = istanzestradarioService;
    }

    @Autowired
    public void setDyn2ModellitService(Dyn2ModellitService dyn2ModellitService) {

	this.dyn2ModellitService = dyn2ModellitService;
    }

    @Autowired
    public void setCampiService(Dyn2CampiService campiService) {

	this.campiService = campiService;
    }

    @Autowired
    public void setIstanzedyn2modellitService(Istanzedyn2modellitService istanzedyn2modellitService) {

	this.istanzedyn2modellitService = istanzedyn2modellitService;
    }

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Autowired
    public void setPublisher(IEventPublisher publisher) {

	this.publisher = publisher;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setStradarioService(StradarioService stradarioService) {

	this.stradarioService = stradarioService;
    }

    @Override
    public InnescoResponse getUrlInnescoIstanza(Integer codiceIstanza, UtilizzoEnum utilizzo, String uuidLocalizzazione, String returnTo) {

	try {
	    if (StringUtils.isBlank(uuidLocalizzazione)) {
		throw new Exception("Non è stato passato l'uuid di istanzestradario");
	    }
	    //1. Recupero la localizzazione
	    Istanzestradario localizzazione = this.istanzestradarioService.findByUuid(codiceIstanza, uuidLocalizzazione);
	    //2. Recupero le informazioni necessarie
	    List<IstanzeStradarioExtendedDTO> localizzazioni = new ArrayList<IstanzeStradarioExtendedDTO>();
	    localizzazioni.add(IstanzeStradarioExtendedDTO.fromIstanzestradario(localizzazione));
	    return this.getUrlInnescoIstanze(utilizzo, localizzazioni, returnTo);
	} catch (Exception ex) {
	    return InnescoResponse.fromGenericException(ex);
	}
    }

    @Override
    public InnescoResponse getUrlInnescoAttivita(UtilizzoEnum utilizzo, List<LocalizzazioniAttivitaDTO> localizzazioni, String returnTo) {

	try {
	    //1. Preparo la request
	    InnescoRequest innescoRequest = this.createRequestAttivita(ApplicativoChiamanteEnum.BACKEND, utilizzo, returnTo, localizzazioni);
	    //5. Recupero l'endpoint del componente
	    String url = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_CARTOGRAFICO);
	    return new CartograficoClient(url).getUrlInnescoAttivita(innescoRequest);
	} catch (Exception ex) {
	    return InnescoResponse.fromGenericException(ex);
	}
    }

    @Override
    public InnescoResponse getUrlInnescoAutorizzazioni(UtilizzoEnum utilizzo, List<IstanzeStradarioExtendedDTO> localizzazioni, String returnTo) {

	try {
	    //1. Preparo la request
	    InnescoRequest innescoRequest = this.createRequest(ApplicativoChiamanteEnum.BACKEND, utilizzo, returnTo, localizzazioni);
	    //5. Recupero l'endpoint del componente
	    String url = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_CARTOGRAFICO);
	    return new CartograficoClient(url).getUrlInnescoAutorizzazioni(innescoRequest);
	} catch (Exception ex) {
	    return InnescoResponse.fromGenericException(ex);
	}
    }

    @Override
    public InnescoResponse getUrlInnescoIstanze(UtilizzoEnum utilizzo, List<IstanzeStradarioExtendedDTO> localizzazioni, String returnTo) {

	try {
	    //1. Preparo la request
	    InnescoRequest innescoRequest = this.createRequest(ApplicativoChiamanteEnum.BACKEND, utilizzo, returnTo, localizzazioni);
	    //5. Recupero l'endpoint del componente
	    String url = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_CARTOGRAFICO);
	    return new CartograficoClient(url).getUrlInnescoIstanze(innescoRequest);
	} catch (Exception ex) {
	    return InnescoResponse.fromGenericException(ex);
	}
    }

    @Override
    public ParametriResponse getParametri(String uuIdLocalizzazione) {

	try {
	    String url = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_CARTOGRAFICO);
	    logger.debug("Chiamata alla url {} per il recupero dei parametri per la sessione {}", url, uuIdLocalizzazione);
	    return new CartograficoClient(url).getParametri(uuIdLocalizzazione);
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public GetInfoResponse getInfoConnettore() {

	try {
	    String url = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_CARTOGRAFICO);
	    if (StringUtils.isBlank(url)) {
		return GetInfoResponse.NonInstallato();
	    }
	    return new CartograficoClient(url).getInfo();
	} catch (Exception e) {
	    //Finchè non gestiamo tramite cache la presenza o meno di un connettore SIC attivo su un determinato alias
	    //non possiamo abilitare il log sotto perchè satura il file di log del backend
	    //logger.error("Errore: ", e);
	    return GetInfoResponse.NonInstallato();
	}
    }

    @Override
    public void salvaParametri(Integer codiceIstanza, String uuidLocalizzazione, ParametriResponse response) {

	logger.debug("Salvataggio dei parametri sulla scheda dinamica per la sessione {}", uuidLocalizzazione);
	IVerticalizzazioneCartograficoAttivoService verticalizzazione = new VerticalizzazioneCartograficoAttivoServiceImpl(
		this.verticalizzazioniService);
	if (verticalizzazione.modelloAltriDati() == null || verticalizzazione.campoAltriDati() == null) {
	    logger.debug("La verticalizzazione non è configurata per salvare i dati sulle schede dinamiche");
	    return;
	}
	Istanzestradario localizzazione = this.istanzestradarioService.findByUuid(codiceIstanza, uuidLocalizzazione);
	if (localizzazione == null || localizzazione.getId() == null || localizzazione.getId().getCodice() == null) {
	    logger.debug("La localizzazione con uuid {} non esiste sull'istanza {}", uuidLocalizzazione, codiceIstanza);
	    return;
	}
	Dyn2Campi campo = this.campiService.findByNomeCampo(verticalizzazione.campoAltriDati());
	if (campo == null || campo.getId() == null || campo.getId().getCodice() == null) {
	    logger.debug("Il campo dinamico {} configurato sulla verticalizzazione non esiste", verticalizzazione.campoAltriDati());
	    return;
	}
	//1. Recupero tutte le schede dell'istanza che usano quel campo
	List<Integer> idModelli = this.istanzedyn2modellitService.findIdModelloByIstanzaAndIdCampo(codiceIstanza, campo.getId().getCodice());
	if (idModelli.isEmpty()) {
	    logger.debug("Nell'istanza {} non è presente nessuna scheda dinamica con il campo con id {}", codiceIstanza, campo.getId().getCodice());
	    return;
	}
	//2. Recupero gli altri dati di tutti gli stradari dell'istanza
	logger.debug("Recupero gli altri dati di tutti gli stradari dell'istanza {}", codiceIstanza);
	List<Istanzestradario> localizzazioni = this.istanzestradarioService.findByIstanza(codiceIstanza);
	List<ParametriResponse> responseList = new ArrayList<ParametriResponse>();
	String url = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_CARTOGRAFICO);
	CartograficoClient client = new CartograficoClient(url);
	for (Istanzestradario loc : localizzazioni) {
	    if (!StringUtils.isBlank(loc.getUuid())) {
		try {
		    responseList.add(client.getParametri(loc.getUuid()));
		} catch (FunzioneBusinessRemotaException e) {
		    throw new RuntimeException(e);
		}
	    }
	}
	//4. Serializzo la lista
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	String jsLista = "";
	try {
	    jsLista = objectMapper.writeValueAsString(responseList);
	} catch (JsonProcessingException e) {
	    logger.error(e.getMessage());
	}
	logger.debug("Serializzo la lista trovata {}", jsLista);
	//2. Verifico se presente il campo dinamico ed eventualmente lo aggiorno o lo inserisco
	Istanzedyn2datiId idDatoDinamico = new Istanzedyn2datiId();
	idDatoDinamico.setIdcomune(ORMHelper.getIdcomune());
	idDatoDinamico.setCodiceistanza(localizzazione.getIstanza().getId().getCodice());
	idDatoDinamico.setFkD2cId(campo.getId().getCodice());
	idDatoDinamico.setIndice(0);
	idDatoDinamico.setIndiceMolteplicita(0);
	Istanzedyn2dati dyn2dati = this.istanzedyn2datiService.findById(idDatoDinamico);
	if (dyn2dati == null || dyn2dati.getId() == null || dyn2dati.getId().getCodiceistanza() == null) {
	    logger.debug("Il campo dinamico non esiste su istanzedyn2dati, lo inserisco");
	    dyn2dati = new Istanzedyn2dati();
	    dyn2dati.setId(idDatoDinamico);
	    dyn2dati.setDyn2Campi(campo);
	    dyn2dati.setIstanza(localizzazione.getIstanza());
	    dyn2dati.setValore(jsLista);
	    dyn2dati.setValoredecodificato(jsLista);
	    this.istanzedyn2datiService.insert(dyn2dati);
	} else {
	    logger.debug("Il campo dinamico esiste su istanzedyn2dati, lo aggiorno");
	    dyn2dati.setValore(jsLista);
	    dyn2dati.setValoredecodificato(jsLista);
	    this.istanzedyn2datiService.update(dyn2dati);
	}
	//3. Avvio l'elaborazione delle schede dinamiche
	try {
	    for (Integer idModello : idModelli) {
		logger.debug("Esecuzione degli script della scheda dinamica {} dell'istanza {}", idModello, codiceIstanza);
		this.dyn2ModellitService.eseguiScriptSchedaIstanza(codiceIstanza, idModello);
	    }
	} catch (FunzioneBusinessRemotaException e) {
	    logger.error(e.getMessage(), e);
	}
	for (Integer idModello : idModelli) {
	    //4. Pubblico l'evento del salvataggio della scheda dinamica
	    logger.debug("Pubblico l'evento del salvataggio della scheda dinamica della scheda dinamica {} dell'istanza {}", idModello,
		    codiceIstanza);
	    this.publisher.publish(new EventoSchedaDinamicaIstanzaSalvata(localizzazione.getIstanza().getId().getCodice(), idModello));
	}
	logger.debug("Fine salvataggio dei parametri sulla scheda dinamica per la sessione {}", uuidLocalizzazione);
    }

    @Override
    public void aggiornaLocalizzazione(Integer codiceIstanza, String uuidLocalizzazione, ParametriResponse response) {

	logger.debug("Aggiornamento dati localizzaztivi per l'istanza {} per la localizzazione {}", codiceIstanza, uuidLocalizzazione);
	if (response == null || response.getParametri() == null || response.getParametri().size() != 1) {
	    logger.debug("Non ci sono dati localizzaztivi da aggiornare per l'istanza {} per la localizzazione {}", codiceIstanza,
		    uuidLocalizzazione);
	    return;
	}
	Istanzestradario localizzazione = this.istanzestradarioService.findByUuid(codiceIstanza, uuidLocalizzazione);
	if (localizzazione == null || localizzazione.getId() == null || localizzazione.getId().getCodice() == null) {
	    logger.debug("Non ci sono dati localizzaztivi per l'istanza {} per la localizzazione {}", codiceIstanza, uuidLocalizzazione);
	    return;
	}
	ParametroResponse parametri = response.getParametri().get(0);
	String codiceViario = parametri.getAdditionalPropertyValue("Strada");
	if (!StringUtils.isBlank(codiceViario)) {
	    logger.debug("Risalgo alla via con codice viario {}", codiceViario);
	    Stradario stradario = this.stradarioService.findByCodiceViario(codiceViario);
	    if (stradario == null) {
		throw new RuntimeException("Lo stradario con codice viario " + codiceViario + " non è stato trovato");
	    }
	    localizzazione.setStradario(stradario);
	}
	String km = parametri.getAdditionalPropertyValue("km");
	if (!StringUtils.isEmpty(km)) {
	    km = km.replace("+", ",");
	    localizzazione.setKm(km);
	}
	String civico = parametri.getAdditionalPropertyValue("civico");
	if (!StringUtils.isBlank(civico)) {
	    localizzazione.setCivico(civico);
	}
	String latitudine = parametri.getLatitudine().toString();
	String longitudine = parametri.getLongitudine().toString();
	localizzazione.setLatitudine(latitudine);
	localizzazione.setLongitudine(longitudine);
	logger.debug("Richiamo il service per aggiornare i dati della localizzazione {}", uuidLocalizzazione);
	this.istanzestradarioService.update(localizzazione);
	logger.debug("Fine aggiornamento dati localizzaztivi per l'istanza {} per la localizzazione {}", codiceIstanza, uuidLocalizzazione);
    }

    private InnescoRequest createRequestAttivita(ApplicativoChiamanteEnum applicativo, UtilizzoEnum utilizzo, String returnTo,
	    List<LocalizzazioniAttivitaDTO> localizzazioni) {

	InnescoRequest innescoRequest = new InnescoRequest();
	innescoRequest.setChiamante(applicativo.toString());
	innescoRequest.setUtilizzo(utilizzo.toString());
	innescoRequest.setCallback(new CallbackBean());
	innescoRequest.getCallback().setCallbackURL(returnTo);
	innescoRequest.setLocalizzazioni(new ArrayList<LocalizzazioneBean>());
	//2. Ciclo le localizzazioni
	Map<String, String> comuni = new HashMap<String, String>();
	IVerticalizzazioneCartograficoAttivoService verticalizzazione = new VerticalizzazioneCartograficoAttivoServiceImpl(
		this.verticalizzazioniService);
	for (LocalizzazioniAttivitaDTO localizzazione : localizzazioni) {
	    //2.1 Verifico la presenza di localizzazioni su più comuni per impostare successivamente il livello di zoom
	    if (StringUtils.isBlank(comuni.get(localizzazione.getCodiceIstat()))) {
		comuni.put(localizzazione.getCodiceIstat(), localizzazione.getComune());
	    }
	    LocalizzazioneBean localizzazioneBean = new LocalizzazioneBean();
	    localizzazioneBean.setCivico(localizzazione.getCivico());
	    localizzazioneBean.setCodViario(localizzazione.getCodiceViario());
	    localizzazioneBean.setDescrizione(localizzazione.getPrefisso() + " " + localizzazione.getDescrizione());
	    localizzazioneBean.setIdentificativo(localizzazione.getIdAttivita().toString());
	    if (StringUtils.isNotBlank(localizzazione.getKm())) {
		localizzazioneBean.setKm(localizzazione.getKm().replace(',', '+'));
	    }
	    String latitudine = verticalizzazione.posizioneLatitudine() == 0 ? localizzazione.getLatitudine() : localizzazione.getLongitudine();
	    String longitudine = verticalizzazione.posizioneLongitudine() == 1 ? localizzazione.getLongitudine() : localizzazione.getLatitudine();
	    localizzazioneBean.setLatitudine(latitudine);
	    localizzazioneBean.setLongitudine(longitudine);
	    localizzazioneBean.setUuid(localizzazione.getUuidIstanzeStradario());
	    innescoRequest.getLocalizzazioni().add(localizzazioneBean);
	}
	//3. Se sono presenti più comuni, imposto il livello di zoom sulla provincia del primo comune trovato
	Map.Entry<String, String> com = comuni.entrySet().iterator().next();
	ComuneBean comune = new ComuneBean();
	if (comuni.size() > 1) {
	    comune.setCodiceIstat(com.getKey().substring(0, 3));
	    comune.setNome(null);
	} else {
	    comune.setCodiceIstat(com.getKey());
	    comune.setNome(com.getValue());
	}
	innescoRequest.setComune(comune);
	return innescoRequest;
    }

    private InnescoRequest createRequest(ApplicativoChiamanteEnum applicativo, UtilizzoEnum utilizzo, String returnTo,
	    List<IstanzeStradarioExtendedDTO> localizzazioni) {

	InnescoRequest innescoRequest = new InnescoRequest();
	innescoRequest.setChiamante(applicativo.toString());
	innescoRequest.setUtilizzo(utilizzo.toString());
	innescoRequest.setCallback(new CallbackBean());
	innescoRequest.getCallback().setCallbackURL(returnTo);
	innescoRequest.setLocalizzazioni(new ArrayList<LocalizzazioneBean>());
	//2. Ciclo le localizzazioni
	Map<String, String> comuni = new HashMap<String, String>();
	IVerticalizzazioneCartograficoAttivoService verticalizzazione = new VerticalizzazioneCartograficoAttivoServiceImpl(
		this.verticalizzazioniService);
	for (IstanzeStradarioExtendedDTO localizzazione : localizzazioni) {
	    //2.1 Verifico la presenza di localizzazioni su più comuni per impostare successivamente il livello di zoom
	    if (StringUtils.isBlank(comuni.get(localizzazione.getCodiceIstat()))) {
		comuni.put(localizzazione.getCodiceIstat(), localizzazione.getComune());
	    }
	    LocalizzazioneBean localizzazioneBean = new LocalizzazioneBean();
	    localizzazioneBean.setCivico(localizzazione.getCivico());
	    localizzazioneBean.setCodViario(localizzazione.getCodiceViario());
	    localizzazioneBean.setDescrizione(localizzazione.getPrefisso() + " " + localizzazione.getDescrizione());
	    localizzazioneBean.setIdentificativo(localizzazione.getNumeroIstanza());
	    if (StringUtils.isNotBlank(localizzazione.getKm())) {
		localizzazioneBean.setKm(localizzazione.getKm().replace(',', '+'));
	    }
	    String latitudine = verticalizzazione.posizioneLatitudine() == 0 ? localizzazione.getLatitudine() : localizzazione.getLongitudine();
	    String longitudine = verticalizzazione.posizioneLongitudine() == 1 ? localizzazione.getLongitudine() : localizzazione.getLatitudine();
	    localizzazioneBean.setLatitudine(latitudine);
	    localizzazioneBean.setLongitudine(longitudine);
	    localizzazioneBean.setUuid(localizzazione.getUuidIstanzeStradario());
	    innescoRequest.getLocalizzazioni().add(localizzazioneBean);
	}
	//3. Se sono presenti più comuni, imposto il livello di zoom sulla provincia del primo comune trovato
	Map.Entry<String, String> com = comuni.entrySet().iterator().next();
	ComuneBean comune = new ComuneBean();
	if (comuni.size() > 1) {
	    comune.setCodiceIstat(com.getKey().substring(0, 3));
	    comune.setNome(null);
	} else {
	    comune.setCodiceIstat(com.getKey());
	    comune.setNome(com.getValue());
	}
	innescoRequest.setComune(comune);
	return innescoRequest;
    }
}
