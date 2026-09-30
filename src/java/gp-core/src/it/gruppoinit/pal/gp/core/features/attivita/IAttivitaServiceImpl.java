package it.gruppoinit.pal.gp.core.features.attivita;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.infocamera.schema.sigeproexport.LISTAISTANZE;
import it.gruppoinit.infocamera.schema.sigeproexport.LISTAISTANZE.ISTANZA;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniAttivitaHelperComparator;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2dati;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiId;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshotId;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshotId;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellit;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellitStorico;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeAttivitaHelper;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.VwIAttivitalista;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniAttivitaHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IAttivitaDaChiudereHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IAttivitaListHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeHelper;
import it.gruppoinit.pal.gp.core.domain.helper.WsExportBean;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitaFilter;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitadyn2datiFilter;
import it.gruppoinit.pal.gp.core.features.attivita.datidinamici.IDatiDinamiciService;
import it.gruppoinit.pal.gp.core.features.attivita.datilocalizzativi.LocalizzazioniAttivitaDTO;
import it.gruppoinit.pal.gp.core.features.attivita.denominazione.DenominazioneResolver;
import it.gruppoinit.pal.gp.core.features.attivita.denominazione.IDenominazioneResolverDAO;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoAttivitaCreata;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.IAttivitaIstanzeService;
import it.gruppoinit.pal.gp.core.features.attivita.restrizioni.RestrizioneAttivitaEsistenteException;
import it.gruppoinit.pal.gp.core.features.attivita.restrizioni.RestrizioneVerificataBean;
import it.gruppoinit.pal.gp.core.features.attivita.restrizioni.RestrizioniCreazioneAttivitaFactory;
import it.gruppoinit.pal.gp.core.features.attivita.restrizioni.RestrizioniCreazioneAttivitaParams;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic.ISnapshotDAO;
import it.gruppoinit.pal.gp.core.features.attivita.verticalizzazione.IVerticalizzazioneIAttivitaService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.ICartograficoService;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.ParametriResponse;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.ParametroResponse;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.IAttivitaSnapshotService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2datiService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2datiSnapshotService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2modTSnapshotService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2modellitService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2modellitStoricoService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeattivitaService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.OrariaperturatestataService;
import it.gruppoinit.pal.gp.core.service.TmpEsportazioniService;
import it.gruppoinit.pal.gp.core.service.VwIAttivitalistaService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.SigeproExportWsClient;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.Parametro;

/**
 * 
 * @author
 */
@Service
public class IAttivitaServiceImpl extends BaseServiceImpl<IAttivita, PkId> implements IAttivitaService {

    private IstanzerichiedentiService istanzerichiedentiService;
    private IstanzestradarioService istanzestradarioService;
    private OrariaperturatestataService orariaperturaService;
    private IstanzeattivitaService istanzeattivitaService;
    private IAttivitadyn2modellitService iAttivitadyn2modellitService;
    private Istanzedyn2datiService istanzedyn2datiService;
    private IAttivitadyn2datiService iAttivitadyn2datiService;
    private IAttivitaSnapshotService iAttivitaSnapshotService;
    private IAttivitadyn2datiSnapshotService iAttivitadyn2datiSnapshotService;
    private IAttivitadyn2modTSnapshotService iAttivitadyn2modTSnapshotService;
    private IAttivitadyn2modellitStoricoService iAttivitadyn2modellitStoricoService;
    private Dyn2CampiService dyn2CampiService;
    private TmpEsportazioniService tmpEsportazioniService;
    private IAttivitaDAO iattivitaDAO;
    @Autowired
    private IDenominazioneResolverDAO denominazioneResolverDAO;
    @Autowired
    private IstanzeService istanzeService;
    private IstanzeDAO istanzeDAO;
    private VwIAttivitalistaService vwIAttivitalistaService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private IVerticalizzazioneIAttivitaService verticalizzazioneIAttivitaService;
    @Autowired
    private IEventPublisher eventPublisher;
    @Autowired
    private IDatiDinamiciService datiDinamiciService;
    @Autowired
    private ISnapshotDAO snapshotDAO;
    @Autowired
    private IAttivitaIstanzeService attivitaIstanzeService;
    private ICartograficoService cartograficoService;
    private static final Integer MAX_RESULT = 5; //PORTATO DA 100 A 5 PER PROBLEMI DI PERFORMANCE
    private static final Logger log = LoggerFactory.getLogger(IAttivitaServiceImpl.class);

    @Autowired
    public void setIstanzerichiedentiService(IstanzerichiedentiService istanzerichiedentiService) {

	this.istanzerichiedentiService = istanzerichiedentiService;
    }

    @Autowired
    public void setIstanzestradarioService(IstanzestradarioService istanzestradarioService) {

	this.istanzestradarioService = istanzestradarioService;
    }

    @Autowired
    public void setOrariaperturaService(OrariaperturatestataService orariaperturaService) {

	this.orariaperturaService = orariaperturaService;
    }

    @Autowired
    public void setIstanzeattivitaService(IstanzeattivitaService istanzeattivitaService) {

	this.istanzeattivitaService = istanzeattivitaService;
    }

    @Autowired
    public void setiAttivitadyn2modellitService(IAttivitadyn2modellitService iAttivitadyn2modellitService) {

	this.iAttivitadyn2modellitService = iAttivitadyn2modellitService;
    }

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Autowired
    public void setiAttivitadyn2datiService(IAttivitadyn2datiService iAttivitadyn2datiService) {

	this.iAttivitadyn2datiService = iAttivitadyn2datiService;
    }

    @Autowired
    public void setiAttivitaSnapshotService(IAttivitaSnapshotService iAttivitaSnapshotService) {

	this.iAttivitaSnapshotService = iAttivitaSnapshotService;
    }

    @Autowired
    public void setiAttivitadyn2datiSnapshotService(IAttivitadyn2datiSnapshotService iAttivitadyn2datiSnapshotService) {

	this.iAttivitadyn2datiSnapshotService = iAttivitadyn2datiSnapshotService;
    }

    @Autowired
    public void setiAttivitadyn2modTSnapshotService(IAttivitadyn2modTSnapshotService iAttivitadyn2modTSnapshotService) {

	this.iAttivitadyn2modTSnapshotService = iAttivitadyn2modTSnapshotService;
    }

    @Autowired
    public void setiAttivitadyn2modellitStoricoService(IAttivitadyn2modellitStoricoService iAttivitadyn2modellitStoricoService) {

	this.iAttivitadyn2modellitStoricoService = iAttivitadyn2modellitStoricoService;
    }

    @Autowired
    public void setDyn2CampiService(Dyn2CampiService dyn2CampiService) {

	this.dyn2CampiService = dyn2CampiService;
    }

    @Autowired
    public void setTmpEsportazioniService(TmpEsportazioniService tmpEsportazioniService) {

	this.tmpEsportazioniService = tmpEsportazioniService;
    }

    @Autowired
    public void setIAttivitaDAO(IAttivitaDAO iattivitaDAO) {

	this.iattivitaDAO = iattivitaDAO;
    }

    @Autowired
    public void setIstanzeDAO(IstanzeDAO istanzeDAO) {

	this.istanzeDAO = istanzeDAO;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setVwIAttivitalistaService(VwIAttivitalistaService vwIAttivitalistaService) {

	this.vwIAttivitalistaService = vwIAttivitalistaService;
    }

    @Autowired
    public void setCartograficoService(ICartograficoService cartograficoService) {

	this.cartograficoService = cartograficoService;
    }

    @Override
    protected Class<IAttivita> getEntityClass() {

	return IAttivita.class;
    }

    @Override
    public List<IAttivita> findAll(Integer firstResult, Integer maxResult) {

	return iattivitaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IAttivita entity) {

	throw new NotImplementedException();
    }

    @Override
    public IAttivita findById(PkId id) {

	return iattivitaDAO.findById(id);
    }

    @Override
    public void update(IAttivita entity) {

	if (validateEntity(entity)) {
	    this.iattivitaDAO.update(entity);
	    IAttivitaSnapshot snapshot = this.snapshotDAO.findSnapshotPiuRecente(entity.getId().getCodice());
	    if (snapshot != null) {
		snapshot.setCodiceOsservatorio(entity.getCodiceOsservatorio());
		snapshot.setDenominazione(entity.getDenominazione());
		snapshot.setTipologiaAttivita(entity.getTipologiaAttivita());
		this.iAttivitaSnapshotService.update(snapshot);
	    }
	}
    }

    @Override
    protected boolean isDeleteAllowed(IAttivita entity) {

	boolean delete = true;
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	if (!ivs.isEmpty()) {
	    this.throwValidationMessages(ivs);
	}
	return delete;
    }

    @Override
    public IAttivita creaAttivita(Istanze istanza, boolean skipCheckControlloEsistenza) throws RestrizioneAttivitaEsistenteException {

	//1. Calcolo della denominazione dell'attività
	String denominazione = new DenominazioneResolver(verticalizzazioneIAttivitaService, denominazioneResolverDAO, "", istanza).risolvi();
	//2. Eventuali verifiche di controllo attività esistente
	if (!skipCheckControlloEsistenza) {
	    this.verificaRestrizioni(istanza, denominazione);
	}
	//3. Inserimento dell'attività
	IAttivita attivita = new IAttivita(denominazione, istanza, true, true);
	this.iattivitaDAO.insert(attivita);
	//4. Aggancio dei modelli dinamici derivanti dall'albero dei procedimenti
	this.datiDinamiciService.aggiungiSchedeDinamicheDaAlbero(attivita);
	//5. Pubblico l'evento di attività creata
	EventoAttivitaCreata evento = new EventoAttivitaCreata(attivita);
	this.eventPublisher.publish(evento);
	//9. Fine e restituzione dell'attività
	return attivita;
    }

    private void verificaRestrizioni(Istanze istanza, String denominazione) throws RestrizioneAttivitaEsistenteException {

	if (istanza == null) {
	    throw new IllegalArgumentException("Impossibile verificare le restrizioni senza passare l'istanza di riferimento");
	}
	Set<Istanzestradario> localizzazioni = istanza.getIstanzestradarios();
	RestrizioniCreazioneAttivitaParams parametri = new RestrizioniCreazioneAttivitaParams(denominazione, localizzazioni, true);
	//
	List<RestrizioneVerificataBean> attivitaEsistenti = new RestrizioniCreazioneAttivitaFactory(verticalizzazioneIAttivitaService,
		vwIAttivitalistaService, parametri).verificaRestrizioni();
	for (RestrizioneVerificataBean restrizione : attivitaEsistenti) {
	    if (!restrizione.getElencoIdAttivita().isEmpty()) {
		String message = getMessageFromBundle(restrizione.getEtichettaEccezione(), new Object[] { istanza.getNumeroistanza(),
		    restrizione.getCriterioDiRicerca(), StringUtils.join(restrizione.getElencoIdAttivita().toArray(), ",") });
		throw new RestrizioneAttivitaEsistenteException(message);
	    }
	}
    }

    @Override
    public void delete(IAttivita attivita) {

	if (isDeleteAllowed(attivita)) {
	    // 1. setto a nullo iAttivita dalle istanze che appartengono a quella attività
	    childDelete(attivita);
	    // 2. Cancello iattivita
	    iattivitaDAO.delete(attivita);
	}
    }

    private String estraiDenominazione(Integer codiceIstanza) {

	String result = "";
	// §§§BEGIN§§§
	Verticalizzazioniparametri queryDenominazione = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_I_ATTIVITA, WebConstants.VERTICALIZZAZIONE_I_ATTIVITA_QUERYDENOMINAZIONE);
	String queryDenominazioneStr = "";
	if (queryDenominazione != null) {
	    if (StringUtils.isNotBlank(queryDenominazione.getValore())) {
		queryDenominazioneStr = queryDenominazione.getValore();
	    }
	}
	if (StringUtils.isNotBlank(queryDenominazioneStr)) {
	    queryDenominazioneStr = queryDenominazioneStr.replaceAll("&CODICEISTANZA", String.valueOf(codiceIstanza));
	    queryDenominazioneStr = queryDenominazioneStr.replaceAll("&IDCOMUNE", ORMHelper.getIdcomune());
	    result = this.denominazioneResolverDAO.findDenominazioneDaQuery(queryDenominazioneStr);
	}
	if (StringUtils.isBlank(result)) {
	    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	    if (StringUtils.isNotBlank(istanza.getNomeattivita())) {
		// 1. Se il campo "Denominazone Attività" dell'istanza è valorizzato viene utilizzato come denominazione
		result = istanza.getNomeattivita();
	    } else if (EntityUtils.getNestedProperty(istanza.getTitolarelegale(), "id.codice") != null) {
		// 2. Se non è valorizzata la "Denominazione Attività" viene utilizzata la "Ragione Sociale"
		// dell'istanza
		result = istanza.getTitolarelegale().getNominativo();
		if (StringUtils.isNotBlank(istanza.getTitolarelegale().getNome())) {
		    result += " " + istanza.getTitolarelegale().getNome();
		}
	    } else {
		// 3. Se non sono valorizzate ne la "Denominazione Attività" ne "Ragione Sociale" viene utilizzato il
		// richiedente
		result = istanza.getRichiedente().getNominativo();
		if (StringUtils.isNotBlank(istanza.getRichiedente().getNome())) {
		    result += " " + istanza.getRichiedente().getNome();
		}
	    }
	}
	// §§§END§§§
	return result;
    }

    private List<VwIAttivitalista> findBydenominazione(String denominazioneAttivita, Istanze istanza, Integer firstResult, Integer maxResult,
	    boolean checkAttiva) {

	// §§§BEGIN§§§
	Verticalizzazioniparametri gruppoSoftware = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_I_ATTIVITA,
		WebConstants.VERTICALIZZAZIONE_I_ATTIVITA_GRUPPOSOFTWARE);
	String softwares[] = null;
	if (gruppoSoftware != null) {
	    if (StringUtils.isNotBlank(gruppoSoftware.getValore())) {
		softwares = gruppoSoftware.getValore().split(",");
		softwares = StringUtils.stripAll(softwares);
	    }
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("denominazione", denominazioneAttivita));
	if (softwares != null) {
	    fr.addFilterField(FilterUtils.in("software", softwares, String.class));
	}
	if (checkAttiva) {
	    fr.addFilterField(FilterUtils.equals("attiva", Integer.valueOf(1), Integer.class));
	}
	ft.addRestriction(fr);
	List<VwIAttivitalista> vwIAttivitalistas = vwIAttivitalistaService.findByFilterTable(ft, firstResult, maxResult);
	return vwIAttivitalistas;
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@// §§§END§§§
    }

    @Override
    protected void childDelete(IAttivita entity) {

	entity = bindDomainObject(entity, PkId.class, "id.codice");
	List<Istanze> istanzes = istanzeService.findByAttivita(entity.getId().getCodice());
	for (Istanze istanza : istanzes) {
	    istanza.setAttivita(null);
	    istanza.setAttivitaOrdine(null);
	    istanzeDAO.update(istanza);
	    istanzeDAO.flush();
	    istanzeDAO.clear();
	}
	entity.setIstanzes(null);
	//1.Cancellazione record I_ATTIVITADYN2MODELLIT (Cancellazione delle schede associate all'attivita)
	List<IAttivitadyn2modellit> iAttivitadyn2modellits = iAttivitadyn2modellitService.findByAttivita(entity.getId().getCodice(), null, null);
	for (IAttivitadyn2modellit iAttivitadyn2modellit : iAttivitadyn2modellits) {
	    iAttivitadyn2modellitService.delete(iAttivitadyn2modellit);
	    istanzeDAO.flush();
	    istanzeDAO.clear();
	}
	//2.Cancellazione record I_ATTIVITADYN2DATI (Cancellazione dei dati dinamici collegati all'attività)
	// La cancellazione deve essere fatta in modo esplicito all'interno delle attitità in quanto non esiste un collegamento
	// DB tra i I_ATTIVITADYN2MODELLIT e I_ATTIVITADYN2DATI
	List<IAttivitadyn2dati> iAttivitadyn2datis = iAttivitadyn2datiService.findByAttivita(entity, null, null);
	for (IAttivitadyn2dati iAttivitadyn2dati : iAttivitadyn2datis) {
	    iAttivitadyn2datiService.delete(iAttivitadyn2dati);
	    istanzeDAO.flush();
	    istanzeDAO.clear();
	}
	//3.Cancellazione record IATTIVITA_SNAPSHOT_ATTIVITA (Cancellazione delle schede salvate a una certa data associate all'attivita)
	List<IAttivitaSnapshot> iAttivitaSnapshots = iAttivitaSnapshotService.findByAttivita(entity.getId().getCodice());
	for (IAttivitaSnapshot iAttivitaSnapshot : iAttivitaSnapshots) {
	    iAttivitaSnapshotService.delete(iAttivitaSnapshot);
	    istanzeDAO.flush();
	    istanzeDAO.clear();
	}
	//4.Cancellazione record I_ATTIVITADYN2MODELLIT_STORICO (Cancellazione della storicizzazione delle schede associate all'attivita)
	List<IAttivitadyn2modellitStorico> iAttivitadyn2modellitStoricos = iAttivitadyn2modellitStoricoService
		.findByAttivita(entity.getId().getCodice());
	for (IAttivitadyn2modellitStorico iAttivitadyn2modellitStorico : iAttivitadyn2modellitStoricos) {
	    iAttivitadyn2modellitStoricoService.delete(iAttivitadyn2modellitStorico);
	    istanzeDAO.flush();
	    istanzeDAO.clear();
	}
    }

    private boolean checkAggiornaDenominazione() {

	// §§§BEGIN§§§
	Verticalizzazioniparametri aggiornaDenominazione = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_I_ATTIVITA, WebConstants.VERTICALIZZAZIONE_I_ATTIVITA_AGGIORNADENOMINAZIONE);
	if (aggiornaDenominazione != null) {
	    if (StringUtils.defaultIfEmpty(aggiornaDenominazione.getValore(), "N").equalsIgnoreCase("S")) {
		return true;
	    }
	}
	// §§§END§§§
	return false;
    }

    @Override
    public IstanzeHelper populateIstanzeHelper(IAttivita iattivita, boolean visstorico) {

	IstanzeHelper helper = new IstanzeHelper();
	List<Istanze> istanzes = iattivitaDAO.findIstanzeOrdinate(iattivita, visstorico);
	helper.setIstanzes(istanzes);
	if (!visstorico) {
	    List<Istanze> storicoistanzes = iattivitaDAO.findIstanzeOrdinate(iattivita, true);
	    helper.setStoricoistanzes(storicoistanzes);
	}
	return helper;
    }

    @Override
    public List<IAttivita> findByFilter(IAttivitaFilter filter) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (EntityUtils.getNestedProperty(filter.getComune(), "codicecomune") != null) {
	    fr.addFilterField(FilterUtils.equals("codicecomune", filter.getComune().getCodicecomune(), "istanza.comune", String.class));
	}
	if (StringUtils.isNotBlank(filter.getDenominazioneAttivita())) {
	    fr.addFilterField(FilterUtils.like("denominazione", filter.getDenominazioneAttivita()));
	}
	if (filter.getAttiva() != null) {
	    fr.addFilterField(FilterUtils.equals("attiva", BooleanUtils.toBoolean(filter.getAttiva()), Boolean.class));
	}
	if (filter.getOperante() != null) {
	    fr.addFilterField(FilterUtils.equals("operante", BooleanUtils.toBoolean(filter.getOperante()), Boolean.class));
	}
	if (EntityUtils.getNestedProperty(filter.getSoftware(), "codice") != null) {
	    fr.addFilterField(FilterUtils.equals("codice", filter.getSoftware().getCodice(), "istanza.software", String.class));
	    if (StringUtils.isNotBlank(filter.getScCodice())) {
		if (BooleanUtils.isTrue(filter.getCheckIntervento())) {
		    fr.addFilterField(FilterUtils.startsWith("scCodice", filter.getScCodice(), "istanzes.alberoproc"));
		} else {
		    fr.addFilterField(FilterUtils.startsWith("scCodice", filter.getScCodice(), "istanza.alberoproc"));
		}
	    }
	    if (EntityUtils.getNestedProperty(filter.getTipimovimento(), "id.tipomovimento") != null) {
		fr.addFilterField(FilterUtils.equals("id.tipomovimento", filter.getTipimovimento().getId().getTipomovimento(),
			"istanza.istanzemovimentis.tipomovimento", String.class));
	    }
	    if (StringUtils.isNotBlank(filter.getPosizioneInArchivio())) {
		fr.addFilterField(FilterUtils.equalsIgnoreCase("posizionearchivio", filter.getPosizioneInArchivio(), "istanza"));
	    }
	    if (EntityUtils.getNestedProperty(filter.getTipiarchivioistanze(), "id.codice") != null) {
		fr.addFilterField(FilterUtils.equals("id.codice", filter.getTipiarchivioistanze().getId().getCodice(), "istanza.tipiarchivioistanza",
			Integer.class));
	    }
	    if (EntityUtils.getNestedProperty(filter.getTipologiaistanza(), "id.codice") != null) {
		fr.addFilterField(
			FilterUtils.equals("id.codice", filter.getTipologiaistanza().getId().getCodice(), "istanza.tipologiaistanza", Integer.class));
	    }
	    if (StringUtils.isNotBlank(filter.getDescrizioneLavori())) {
		fr.addFilterField(FilterUtils.like("lavori", filter.getDescrizioneLavori(), "istanza"));
	    }
	    if (StringUtils.isNotBlank(filter.getNote())) {
		fr.addFilterField(FilterUtils.like("lavoriestesa", filter.getNote(), "istanza"));
	    }
	}
	if (EntityUtils.getNestedProperty(filter.getRichiedente(), "id.codice") != null) {
	    // Istanzerichiedenti
	    FilterRestriction richiedentiSoggColl = new FilterRestriction();
	    richiedentiSoggColl.setAndOrRestriction(AndOrRestriction.OR);
	    richiedentiSoggColl.addFilterField(
		    FilterUtils.equals("id.codice", filter.getRichiedente().getId().getCodice(), "istanza.richiedente", Integer.class));
	    richiedentiSoggColl.addFilterField(
		    FilterUtils.equals("id.codice", filter.getRichiedente().getId().getCodice(), "istanza.titolarelegale", Integer.class));
	    richiedentiSoggColl.addFilterField(FilterUtils.equals("id.codice", filter.getRichiedente().getId().getCodice(),
		    "istanza.istanzerichiedentis.richiedente", Integer.class));
	    richiedentiSoggColl.addFilterField(FilterUtils.equals("id.codice", filter.getRichiedente().getId().getCodice(),
		    "istanza.istanzerichiedentis.anagrafeCollegata", Integer.class));
	    ft.addRestriction(richiedentiSoggColl);
	}
	if (EntityUtils.getNestedProperty(filter.getCittadinanza(), "codice") != null) {
	    // cittadinanza
	    FilterRestriction richiedentiSoggColl = new FilterRestriction();
	    richiedentiSoggColl.setAndOrRestriction(AndOrRestriction.OR);
	    richiedentiSoggColl.addFilterField(FilterUtils.equals("codice", filter.getRichiedente().getCittadinanza().getCodice(),
		    "istanza.richiedente.cittadinanza", Integer.class));
	    richiedentiSoggColl.addFilterField(FilterUtils.equals("codice", filter.getRichiedente().getCittadinanza().getCodice(),
		    "istanza.titolarelegale.cittadinanza", Integer.class));
	    richiedentiSoggColl.addFilterField(FilterUtils.equals("codice", filter.getRichiedente().getCittadinanza().getCodice(),
		    "istanza.istanzerichiedentis.richiedente", Integer.class));
	    richiedentiSoggColl.addFilterField(FilterUtils.equals("codice", filter.getRichiedente().getCittadinanza().getCodice(),
		    "istanza.istanzerichiedentis.anagrafeCollegata", Integer.class));
	    ft.addRestriction(richiedentiSoggColl);
	}
	if (EntityUtils.getNestedProperty(filter.getStradario(), "id.codice") != null) {
	    fr.addFilterField(
		    FilterUtils.equals("id.codice", filter.getStradario().getId().getCodice(), "istanza.istanzestradarios.stradario", Integer.class));
	    if (StringUtils.isNotBlank(filter.getCivico())) {
		fr.addFilterField(FilterUtils.equalsIgnoreCase("civico", filter.getCivico(), "istanza.istanzestradarios"));
	    }
	}
	if (EntityUtils.getNestedProperty(filter.getStradariozone(), "id.codice") != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", filter.getStradariozone().getId().getCodice(),
		    "istanza.istanzestradarios.stradario.stradariozone", Integer.class));
	}
	if (EntityUtils.getNestedProperty(filter.getStradariocolore(), "id.codicecolore") != null) {
	    fr.addFilterField(FilterUtils.equals("id.codicecolore", filter.getStradariocolore().getId().getCodicecolore(),
		    "istanza.istanzestradarios.stradariocolore", String.class));
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("denominazione"));
	return iattivitaDAO.findByFilterTable(ft);
    }

    @Override
    public List<IAttivita> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	return iattivitaDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<Istanze> findIstanzeOrdinate(IAttivita iattivita, boolean visstorico) {

	return iattivitaDAO.findIstanzeOrdinate(iattivita, visstorico);
    }

    @Override
    public void insertCopiaTutteLeInfo(Istanze istanzaSorgente, Istanze istanzaDestinatario) {

	this.orariaperturaService.copiaOrariAperturaAttivita(istanzaSorgente, istanzaDestinatario);
	this.istanzerichiedentiService.copiaIstanzeRichiedenti(istanzaSorgente, istanzaDestinatario);
	this.istanzestradarioService.copiaLocalizzazioniWithMappali(istanzaSorgente, istanzaDestinatario, true);
	this.istanzeattivitaService.copiaIstanzeAttivita(istanzaSorgente, istanzaDestinatario);
	this.istanzeService.updateCopiaSchedeIstanza(istanzaSorgente, istanzaDestinatario);
    }

    @Override
    public byte[] exportIAttivita(IAttivitaFilter filter, Integer codicetipoesportazione, String idComuneTipoesportazione, String email,
	    boolean isInvioMail) {

	// IL metodo è stato esteso aggiungendo il parametro data,nel caso della vecchia chiamata per l'export delle attivita
	// viene passata null
	return this.exportIAttivita(filter, codicetipoesportazione, idComuneTipoesportazione, email, null, isInvioMail);
    }

    public byte[] exportIAttivita(IAttivitaFilter filter, Integer codicetipoesportazione, String idComuneTipoesportazione, String email, Date date,
	    boolean isInvioMail) {

	// Definisco la formattazione della data
	DateFormat myDateFormatOut = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	boolean isExportInData = true;
	log.debug("exportIAttivita# entro nel metodo");
	if (date == null) {
	    if (log.isDebugEnabled()) {
		log.debug("exportIAttivita# Non è stata passata la data, setto la data odierna");
	    }
	    date = new Date();
	    // la data è null quindi la è l'esportazione delle attivita e non degli snapshot
	    isExportInData = false;
	}
	LISTAISTANZE listaattivita = new LISTAISTANZE();
	// Istanzio la data come stringa
	String outdate = myDateFormatOut.format(date);
	if (isExportInData == false) {
	    double pageNumber = 0;
	    double conta = 0;
	    int startRow = 0;
	    int rowEnd;
	    Integer count = this.countIAttivitaListHelperByFilter(filter);
	    if (count > 0) {
		Set<BigDecimal> codicis = new LinkedHashSet<BigDecimal>(count);
		if (count < pageSize) {
		    pageNumber = 1;
		} else {
		    conta = Double.valueOf(count) / Double.valueOf(pageSize);
		    pageNumber = Math.ceil(conta);
		}
		for (int i = 0; i < pageNumber; i++) {
		    startRow = i * (Double.valueOf(pageSize).intValue());
		    rowEnd = (Double.valueOf(pageSize).intValue());
		    log.debug("exportIAttivita# sono alla pagina {} di {}", i, pageNumber);
		    List<IAttivitaListHelper> iAttivitasTemp = this.findIAttivitaListHelperByFilter(filter, startRow, rowEnd);
		    //iattivitaList.addAll(iAttivitasTemp);
		    /* Vengono utilizzati gli Stub creati per Infocamere quindi la lista istanze in realtà
		     * rappresenta la lista di IAttività.  
		     * Popolo l'oggetto ISTANZA (Oggetto del WS) con i dati dell' attivita (IDCOMUNE,CODICE_ATTIVITA,CODICE_COMUNE)
		     * e la data passata (se non passata verrà di default inserita quella di sistema.)
		     */
		    for (IAttivitaListHelper iAttivitaH : iAttivitasTemp) {
			// Integer codiceattivita = getCodiceAttivitaOrCodiceAttivitaSnapShot(date, iAttivitaH.getId().intValue(), isExportInData);
			// if (codiceattivita != null) {
			if (!codicis.contains(iAttivitaH.getId())) {
			    codicis.add(iAttivitaH.getId());
			    ISTANZA attivita = new ISTANZA();
			    attivita.setIDCOMUNE(ORMHelper.getIdcomune());
			    attivita.setCODICE(iAttivitaH.getId().toString());
			    attivita.setDATA(outdate);
			    attivita.setCODICECOMUNE(iAttivitaH.getCodicecomune());
			    listaattivita.getISTANZA().add(attivita);
			}
			// }
		    }
		}
	    }
	} else { // export in data
	    log.debug("exportIAttivita# sono nel blocco di esportazione in data");
	    List<WsExportBean> res = iAttivitaSnapshotService.findIAttivitaSnapshotByFilter(filter, date);
	    log.debug("exportIAttivita# devo scrivere {} iattivitasnapshot", res.size());
	    for (WsExportBean eb : res) {
		ISTANZA attivita = new ISTANZA();
		attivita.setIDCOMUNE(eb.getIdcomune());
		//String codiceattivita = iAttivita.getId().getCodice().toString();
		attivita.setCODICE(eb.getCodice().toString());
		attivita.setDATA(outdate);
		attivita.setCODICECOMUNE(eb.getCodicecomune());
		listaattivita.getISTANZA().add(attivita);
	    }
	}
	// WEB SERVICE SIGEPROEXPORT
	/* Vengono utilizzati gli Stub creati per Infocamere quindi la lista istanze in realtà
	 * rappresenta la lista di IAttività  
	 */
	Parametro parametro1 = new Parametro();
	parametro1.setNOME("PROGRESSIVO_INVIO");
	parametro1.setVALORE("001");
	Parametro parametro2 = new Parametro();
	parametro2.setNOME("DATA_INVIO");
	Date _date = new Date();
	String dataInvio = myDateFormatOut.format(_date);
	parametro2.setVALORE(dataInvio);
	Parametro[] listaParametri = { parametro1, parametro2 };
	byte[] responseByte;
	// Controllo se è attivo l'invio mail 
	log.debug("exportIAttivita# prima di inviare");
	if (isInvioMail) {
	    if (log.isDebugEnabled()) {
		log.debug("exportIAttivita# L'esportazione inviata alla  email: {}", email);
	    }
	    responseByte = SigeproExportWsClient.exportMail(ORMHelper.getToken(), listaattivita, codicetipoesportazione, idComuneTipoesportazione,
		    listaParametri, email, true);
	} else {
	    responseByte = SigeproExportWsClient.export(ORMHelper.getToken(), listaattivita, codicetipoesportazione, idComuneTipoesportazione,
		    listaParametri, true);
	}
	return responseByte;
    }

    @Override
    public int countIstanzeWithDateValiditaNull(IAttivita iattivita, boolean visstorico) {

	return iattivitaDAO.countIstanzeWithDateValiditaNull(iattivita, visstorico);
    }

    private double pageSize = 100;

    @Override
    public List<IAttivitaListHelper> findIAttivitaListHelperByFilter(IAttivitaFilter filter, Integer firstResult, Integer maxResult) {

	return iattivitaDAO.findIAttivitaListHelperByFilter(filter, firstResult, maxResult);
    }

    @Override
    public int countIAttivitaListHelperByFilter(IAttivitaFilter filter) {

	return iattivitaDAO.countIAttivitaListHelperByFilter(filter);
    }

    @Override
    public void clear() {

	iattivitaDAO.clear();
    }

    @Override
    public void flush() {

	iattivitaDAO.flush();
    }

    @Override
    public void updateCampiSchedeDinamiche(Integer codiceIAttivita, Integer codiceScheda) {

	// Recupero l'oggetto iAttivita dal codice
	log.debug("Recupero le schede dell'attivita con codice {}", codiceIAttivita);
	// Recupero per l'attività passata tutte le schede collegata ad essa.
	List<IAttivitadyn2modellit> iAttivitadyn2modellits = iAttivitadyn2modellitService.findByAttivita(codiceIAttivita, null, null);
	// Recupero la lista delle istanze che fanno parte dell'attività
	log.debug("Recupero le istanze legate all'attivita con codice {}", codiceIAttivita);
	IAttivita att = this.findById(new PkId(codiceIAttivita));
	Integer codiceIstanzaUltima = att.getIstanza().getId().getCodice();
	List<Integer> istanze = new ArrayList<Integer>();
	istanze.add(codiceIstanzaUltima);
	// List<Integer> istanzes = this.findCodiciIstanza(codiceIAttivita);
	log.debug("Ciclo le schede dell'attività di codice {} (num. schede: {})", new Object[] { codiceIAttivita, iAttivitadyn2modellits.size() });
	for (IAttivitadyn2modellit iAttivitadyn2modellit : iAttivitadyn2modellits) {
	    Set<Dyn2Modellid> dyn2Modellids = iAttivitadyn2modellit.getDyn2Modellit().getDyn2Modellids();
	    for (Dyn2Modellid dyn2Modellid : dyn2Modellids) {
		if (EntityUtils.getNestedProperty(dyn2Modellid.getDyn2Campi(), "id.codice") != null) {
		    // Recupero tutti i valori dei associati al campo dinamico (Istanzedyn2dati) per l'istanze associate all'attività
		    // e per il campo dinamico passati. (ultimaIstanza sarà la nuova istanza che rappresenta l'attività)
		    log.debug("Recupero dati dinamici dell'istanza corrispondenti al campo dinamico (dyn2dati) di codice: {}",
			    dyn2Modellid.getDyn2Campi().getId().getCodice());
		    List<Istanzedyn2dati> istanzedyn2datis = istanzedyn2datiService
			    .findByIstanzasAndDyn2Campi(dyn2Modellid.getDyn2Campi().getId().getCodice(), codiceIAttivita, istanze, null, null); // li cerco tutti e non solo uno
		    // Per ogni valore dei campi dinamici dell'istanza (Istanzedyn2dati) faccio un confronto con i valori di quelli dell'attivita(IAttivitadyn2dati)
		    // Cerco su IAttivitadyn2dati se esiste un record con questi filtri:
		    //1. idcomune;
		    //2. fkIaId :riferimento all'attività
		    //3. fkD2cId:riferimento al campo
		    //4. indice
		    //5. indiceMolteplicita
		    if (istanzedyn2datis != null && !istanzedyn2datis.isEmpty()) {
			List<IAttivitadyn2dati> listaDaCancellare = iAttivitadyn2datiService.findByAttivitaAndDyn2Campi(codiceIAttivita,
				dyn2Modellid.getDyn2Campi().getId().getCodice(), null);
			for (IAttivitadyn2dati iAttivitadyn2dati : listaDaCancellare) {
			    iAttivitadyn2datiService.delete(iAttivitadyn2dati);
			    iattivitaDAO.flush();
			}
			for (Istanzedyn2dati istanzedyn2dati : istanzedyn2datis) {
			    log.debug("Inserisco/Aggiorno campo dinamico (codice dyn2dati :{}) della schede dell'attivita({})....",
				    new Object[] { istanzedyn2dati.getDyn2Campi().getId().getCodice(), codiceIAttivita });
			    // sopra è stata aggiunta la cancellazione dei campi dell'attività. quindi il metodo applica in questo caso sempre
			    // una insert
			    insertOrUpdateiAttivitadyn2dati(codiceIAttivita, istanzedyn2dati);
			}
			log.debug("Inserito/Aggiornato campo della scheda....");
		    } else {
			// Se la scheda è diversa da null significa che lato .NET è stata effettuato un evento del tipo:
			//1- Scollegata una scheda all'istanza con campo in comune a quella dell'attivita
			//2- Annullato un campo della scheda dell'istanza che è in comune con la scheda dell'attivita
			if (codiceScheda != null)// passato id scheda
			{
			    // Se viene passato il codice della scheda dell'istanza "codice scheda istanza", controllo se per il campo dimanico dell'attività che stiamo valutando
			    // esiste anche nella modello dell'istanze che è stato passato.Nel caso significa che il campo dell'attività deve essere messo a null
			    // perche era gestito tramite le schede dell'istanza, altrimenti non viene alterato in quanto la sua gestione era direttamente fatta 
			    // dallle schede dell'attività.
			    List<Dyn2Campi> dyn2CampiIstanza = dyn2CampiService.findByIdModelloAndIdCampo(codiceScheda,
				    dyn2Modellid.getDyn2Campi().getId().getCodice());
			    if (dyn2CampiIstanza != null && !dyn2CampiIstanza.isEmpty()) {
				IAttivitadyn2datiFilter filter = null;
				filter = new IAttivitadyn2datiFilter(codiceIAttivita, dyn2Modellid.getDyn2Campi().getId().getCodice(), null, null);
				List<IAttivitadyn2dati> iAttivitadyn2datisTemp = iAttivitadyn2datiService.findByFilter(filter);
				for (IAttivitadyn2dati iAttivitadyn2dati : iAttivitadyn2datisTemp) {
				    iAttivitadyn2datiService.delete(iAttivitadyn2dati);
				    //				    List<IAttivitadyn2datiSnapshot> iad2cs = iAttivitadyn2datiSnapshotService
				    //					    .findByAttivitaAndAttivitaSnapshotAndCampo(codiceIAttivita, null, dyn2Modellid.getDyn2Campi().getId()
				    //						    .getCodice());
				    //				    for (IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshot : iad2cs) {
				    //					iAttivitadyn2datiSnapshotService.delete(iAttivitadyn2datiSnapshot);
				    //				    }
				    iAttivitadyn2datiSnapshotService.deleteByAttivitaAndCampo(codiceIAttivita,
					    dyn2Modellid.getDyn2Campi().getId().getCodice());
				}
			    }
			}
		    }
		}
	    }
	}
	//	log.debug("updateCampiSchedeDinamiche# E' stata aggiornata una scheda. L'aggiornamento di una scheda por");
	//	IAttivita iAttivita=this.findById(new PkId(codiceIAttivita));
	//	this.calcoloDataFineAttivita(iAttivita);
    }

    private boolean insertOrUpdateiAttivitadyn2dati(Integer codiceIAttivita, Istanzedyn2dati istanzedyn2dati) {

	try {
	    IAttivitadyn2dati iAttivitadyn2datiTemp = new IAttivitadyn2dati();
	    IAttivitadyn2datiFilter filter = null;
	    filter = new IAttivitadyn2datiFilter(codiceIAttivita, istanzedyn2dati.getId().getFkD2cId(), istanzedyn2dati.getId().getIndice(),
		    istanzedyn2dati.getId().getIndiceMolteplicita());
	    List<IAttivitadyn2dati> iAttivitadyn2datisTemp = iAttivitadyn2datiService.findByFilter(filter);
	    if (!iAttivitadyn2datisTemp.isEmpty()) {
		iAttivitadyn2datiTemp = iAttivitadyn2datisTemp.get(0);
	    } else {
		iAttivitadyn2datiTemp = null;
	    }
	    //CASO A: già esiste quindi vado a modificare i valori con quelli di istanzedyn2dati,
	    // l' aggiormaneto verrà fatto solo sui campi "valore" e "valore decodficato"
	    if (iAttivitadyn2datiTemp != null) {
		log.debug("Il campo dinamico della schede dell'istanza già esiste sulla scheda dell'attivita: aggiorno i valori");
		if (StringUtils.isNotBlank(istanzedyn2dati.getValore())) {
		    iAttivitadyn2datiTemp.setValore(istanzedyn2dati.getValore());
		}
		if (StringUtils.isNotBlank(istanzedyn2dati.getValoredecodificato())) {
		    iAttivitadyn2datiTemp.setValoredecodificato(istanzedyn2dati.getValoredecodificato());
		}
		iAttivitadyn2datiTemp.setAutoins(true);
		iAttivitadyn2datiService.update(iAttivitadyn2datiTemp);
		return true;
	    } else {
		//CASO B: non  esiste quindi vado a inserire un nuovo record prendendo i valori da  istanzedyn2dati,
		log.debug("Il campo dinamico della schede dell'istanza non esiste sulla scheda dell'attivita: inserisco il campo");
		IAttivitadyn2dati iAttivitadyn2datiNew = new IAttivitadyn2dati();
		iAttivitadyn2datiNew.setDyn2Campi(istanzedyn2dati.getDyn2Campi());
		iAttivitadyn2datiNew.setId(new IAttivitadyn2datiId(codiceIAttivita, istanzedyn2dati.getId().getFkD2cId(),
			istanzedyn2dati.getId().getIndice(), istanzedyn2dati.getId().getIndiceMolteplicita()));
		if (StringUtils.isNotBlank(istanzedyn2dati.getValore())) {
		    iAttivitadyn2datiNew.setValore(istanzedyn2dati.getValore());
		}
		if (StringUtils.isNotBlank(istanzedyn2dati.getValoredecodificato())) {
		    iAttivitadyn2datiNew.setValoredecodificato(istanzedyn2dati.getValoredecodificato());
		}
		iAttivitadyn2datiNew.setAutoins(true);
		iAttivitadyn2datiService.insert(iAttivitadyn2datiNew);
		return true;
	    }
	} catch (Exception e) {
	    log.error("Non è stato possibile aggiornare il campo della scheda causa:", e);
	    return false;
	}
    }

    @Override
    public IAttivita findByUltimaIstanza(Integer codiceUltimaIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceUltimaIstanza, "istanza", Integer.class));
	ft.addRestriction(fr);
	List<IAttivita> risultato = this.findByFilterTable(ft, null, null);
	if (!risultato.isEmpty()) {
	    return risultato.get(0);
	}
	return null;
    }

    @Override
    public IAttivita findByIstanze(Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanzes", Integer.class));
	ft.addRestriction(fr);
	List<IAttivita> risultato = this.findByFilterTable(ft, null, null);
	if (!risultato.isEmpty()) {
	    return risultato.get(0);
	}
	return null;
    }

    /**
     * <pre>
     *  Esegue lo snapshot :
     * 		Caso 1. Se codiceIstanza == iattivita.istanza.id.codice allora esugue "SnapShotCopia" e Fine
     * 		Caso 2. Se l’istanza(codiceIstanza) non ha data validità allora no "SnapShotCopia" e Fine
     * 	        Caso 3. 
     * 		(Occorre fare uno snapshot con data=data validità)
     * 		a. Estrago data validità dall’istanza.
     *          b. Cancello gli snapshot associati all'attività per la data di valità recuperata.
     *          c. Recupero lo ShapShot  associato all'attività con data minore di quella di validità dell'istanza
     *          d. Controllo se esiste il record SnapShot Cercato:
     *          	d.1: Trovato	 : - Creo un nuovo record di IAttivitaSnapshot identico a quello trovato (anche le liste collegate)
     *          			     con il campo data = istanza.dataValidita
     *                                     - Aggiorno il codice ultima istanza con quello "codiceIstanza"
     *                                     - Ricalcolo il campo "attiva"
     *                                     - Ricalcolo il campo "operante"
     *                                     - Aggiorno i campi della tabella iAttivitadyn2datiSnapshot collegati iAttivitaSnapshot creata 
     *                                  
     *                                     
     *                  d.2: Non Trovato : Richiamo updateShapShotCopia(...)
     * </pre>
     */
    @Override
    public boolean updateSnapShot(Integer codiceIstanza) {

	try {
	    boolean isSnapshotDo = false;
	    Date dataSnapshotPartenza = null;
	    log.debug("Inizio operazione di Snapshot......");
	    log.debug("Recupero l'istanza con codice: {}", codiceIstanza);
	    Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
	    log.debug("Recupero l' attivita a cui è collegata l'istanza con codice {}", codiceIstanza);
	    //	    IAttivita attivita = this.findByUltimaIstanza(codiceIstanza);
	    IAttivita attivita = this.findByIstanze(codiceIstanza);
	    if (EntityUtils.getNestedProperty(attivita, "id.codice") == null) {
		log.error(
			"Impossibile eseguire l'operazione di SnapShot:Attenzione l'istanza (codiceIstanza: {}) passata non è associata a nessuna attività",
			codiceIstanza);
		throw new RuntimeException("Impossibile eseguire l'operazione di SnapShot:Attenzione l'istanza (codiceIstanza:" +
			codiceIstanza +
			") passata non è associata a nessuna attività");
	    } else {
		if (EntityUtils.getNestedProperty(attivita.getIstanza(), "id.codice") != null && EntityUtils.equals(attivita.getIstanza(), istanze)) {
		    //l'istanza passata è l'istanza rappresentativa dell'attività
		    // Se la data di validità è diversa da NULL allora calcolo lo snapshot, altrimenti 
		    // non fa niente.
		    if (istanze.getDatavalidita() != null) {
			log.debug("SnapShot Caso: codiceIstanza == iattivita.istanza.id.codice");
			IAttivitaSnapshot iAttivitaSnapshot = updateSnapShotCopia(attivita);
			if (EntityUtils.getNestedProperty(iAttivitaSnapshot, "id.codice") != null) {
			    isSnapshotDo = true;
			} else {
			    isSnapshotDo = false;
			}
			dataSnapshotPartenza = iAttivitaSnapshot.getData();
		    }
		} else if (EntityUtils.getNestedProperty(istanze, "id.codice") != null && istanze.getDatavalidita() == null) {
		    //l'istanza passata non ha data di validità
		    log.debug("SnapShot Caso: codiceIstanza == istanze.dataValidita==null");
		    log.debug("Terminata operazione di Snapshot......");
		    return true;
		} else {
		    //l'istanza passata ha data di validità e non è l'istanza rappresentativa dell'attività
		    //Se non esiste uno snapshot per quella data di validità
		    //	creare lo snapshot
		    //altrimenti
		    //	aggiornare lo snapshot
		    // estraggo data validità dell'istanza
		    Date dataValidita = istanze.getDatavalidita();
		    IAttivitaSnapshot iAttivitaSnapshotNew = null;
		    //Controllo se esiste uno snapshot con data uguale alla data di validità dell'istanza passata
		    iAttivitaSnapshotNew = iAttivitaSnapshotService.findByData(attivita, dataValidita);
		    if (iAttivitaSnapshotNew != null) {
			//Se esiste uno snapshot con data uguale alla data di validità dell'istanza passata allora non lo ricreo
			//devo ricalcolare l'istanza rappresentativa dello snapshot perchè potrebb essere cambiato
			Istanze istanzaUltimaSnapshot = istanzeService.findIstanzaUltimaAttivitaAllaData(attivita.getId().getCodice(),
				iAttivitaSnapshotNew.getData());
			iAttivitaSnapshotNew.setIstanza(istanzaUltimaSnapshot);
		    } else {
			//Non esiste uno snapshot con data uguale alla data di validità dell'istanza passata, allora lo creo
			log.debug("Recupero il record IAttivitaSnapshot precedente alla data {} per l'attivita con codice {} ",
				new Object[] { dataValidita.toString(), attivita.getId().getCodice() });
			//IAttivitaSnapshot attivitaSnapshotPrecedete = iAttivitaSnapshotService.findBeforeData(attivita.getId().getCodice(), dataValidita);
			IAttivitaSnapshot attivitaSnapshotPrecedete = iAttivitaSnapshotService.findBeforeDataAndOrdine(attivita.getId().getCodice(),
				dataValidita, istanze.getAttivitaOrdine());
			if (attivitaSnapshotPrecedete != null) {
			    //Esiste almeno uno snapshot per l'attivita precedente alla data di validità dell'istanza passata
			    log.debug("Trovato record di attivitaSnapshotPrecedete con codice :{} ", attivitaSnapshotPrecedete.getId().getCodice());
			    iAttivitaSnapshotNew = createNewIAttivitaSnapshotFromIAttivitaSnapshotAndDate(attivitaSnapshotPrecedete, dataValidita);
			    // Aggiorno il codiceUltimaistanza del nuovo oggetto IAttivitaSnapshot (iAttivitaSnapshotNew) con il codiceIstanza 
			    // passato al metodo
			} else {
			    //Non esiste uno snapshot per l'attivita precedente alla data di validità dell'istanza passata, controllo se ci sono altri snapshot
			    boolean isExsist = iAttivitaSnapshotService.isSnapshotExsistByAttivita(attivita.getId().getCodice());
			    if (isExsist) {
				//Esistono altri snapshot, pertanto sto inserendo lo snapshot iniziale della catena
				log.debug("Sto inserendo lo snapshot origine della catena che rappresenta lo storico dell'attività {} []",
					new Object[] { attivita.getDenominazione(), attivita.getId().getCodice() });
				iAttivitaSnapshotNew = this.updateSnapShotCrea(attivita, istanze);
				// il codice osservatorio non va ricalcolato ne ereditato
				iAttivitaSnapshotNew.setCodiceOsservatorio(null);
			    } else {
				//Non esistono altri snapshot
				log.debug("Sto inserendo lo snapshot ultimo dell'attività {} []",
					new Object[] { attivita.getDenominazione(), attivita.getId().getCodice() });
				iAttivitaSnapshotNew = this.updateSnapShotCopia(attivita);
			    }
			    istanzeDAO.flush();
			    iAttivitaSnapshotNew.setData(dataValidita);
			}
			iAttivitaSnapshotNew.setIstanza(istanze);
		    }
		    iAttivitaSnapshotService.update(iAttivitaSnapshotNew);
		    //Recupero la lista delle istanze appartenenti all'attività ordinate per data validità DESC, ordine ASC ed escludo
		    //quelle con data validità > della data di validità dell' istanza passata al metodo.
		    //ES. 1/2012(31/10/2012),2/2012(30/10/2012),4/2012(12/08/12),8/2012(10/06/2012), supponendo che il codice dell'istanza
		    //passata sia quello della 2/2012, la lista sarà composta dalle istanze 2/2012,4/2012,8/2012.
		    log.debug("Ricerca istanze dell' attivita {} e data validità <= {}",
			    new Object[] { attivita.getId().getCodice(), istanze.getDatavalidita() });
		    // I dati dinamici delle istanze con data validità NULL non verranno mai presi in considerazione dagli snapshot e
		    // dalla situazione attuale.
		    List<Istanze> listIstanzePerSnapShot = istanzeService.findByAttivitaAndBeforeDataValiditaIstanza(attivita.getId().getCodice(),
			    istanze.getDatavalidita(), istanze.getAttivitaOrdine());
		    //Ricalcolo il campo attiva del record IAttivitaShapShot
		    log.debug("Ricalcolo il campo attivita il record IAttivitaShapShot con codice: {} ", iAttivitaSnapshotNew.getId().getCodice());
		    iAttivitaSnapshotService.updateSettaAttiva(listIstanzePerSnapShot, iAttivitaSnapshotNew);
		    //Ricalcolo il campo operante di record IAttivitaShapShot
		    log.debug("Ricalcolo il campo attivita il record IAttivitaShapShot con codice: {} ", iAttivitaSnapshotNew.getId().getCodice());
		    // COMMENTATO iAttivitaSnapshotService.updateSettaOperante(listIstanzePerSnapShot, iAttivitaSnapshotNew);
		    // Verifico se deve ricalcolare la denominazione ed eventualmente l'aggiorna
		    if (checkAggiornaDenominazione()) {
			log.debug("Ricalcolo denominazione attività di IAttivitaShapShot con codice: {} ", iAttivitaSnapshotNew.getId().getCodice());
			String denominazione = estraiDenominazione(istanze.getId().getCodice());
			iAttivitaSnapshotNew.setDenominazione(denominazione);
			// devo aggiornare la denominazione ricalcolata
			iAttivitaSnapshotService.update(iAttivitaSnapshotNew);
		    }
		    isSnapshotDo = true;
		    dataSnapshotPartenza = dataValidita;
		}
	    }
	    // Ricalcola i dati dinamici dello snapshot, se dataSnapshotPartenza è diversa da NULL.
	    // Se è NULL lo snapshot non dovrebbe esistere!!!!
	    if (dataSnapshotPartenza != null) {
		iAttivitaSnapshotService.updateDatiDinamiciSnapshots(attivita, dataSnapshotPartenza);
	    }
	    return isSnapshotDo;
	} catch (Exception e) {
	    log.error("Errore nell' esecuzione del metodo updateSnapShot(Integer codiceIstanza) con codiceIstanza : {}, {}", codiceIstanza, e);
	    return false;
	}
    }

    /**
     * <pre>
     *    Il metodo effettua la copia delle seguenti informazioni: 
     * 	  <ul>
     *    	<li>I_ATTIVITADYN2MODELLIT -> I_ATTIVITADYN2MOD_T_SNAPSHOT</li>
     *    	<li>I_ATTIVITADYN2DATI -> I_ATTIVITADYN2DATI_SNAPSHOT</li>
     *   </ul>
     *   leggendoli dall'attività e riportandoli nello snapshot più recente ( calcolato prendendo la data di validità dell'istanza che rappresenta
     *   l'attività ). 
     *   I record presenti nello snapshot interessato vengono prima cancellati
     * </pre>
     * 
     * @param attivita
     * @return
     */
    @Override
    public boolean updateSnapShotCopiaDatiDinamici(IAttivita attivita) {

	try {
	    //recupero lo snapshot più recente
	    log.debug("Recuperolo lo snapshot più recente a partire dalla data dell'attività : {}", attivita.getIstanza().getDatavalidita());
	    IAttivitaSnapshot iAttivitaSnapshot = iAttivitaSnapshotService.findByData(attivita, attivita.getIstanza().getDatavalidita());
	    // recupero la lista dei modelli e dei dati dinamici associati allo snapshot e li elimino
	    Set<IAttivitadyn2modTSnapshot> attivitadyn2modTSnapshots = iAttivitaSnapshot.getAttivitadyn2modTSnapshots();
	    for (IAttivitadyn2modTSnapshot iAttivitadyn2modTSnapshot : attivitadyn2modTSnapshots) {
		iAttivitadyn2modTSnapshotService.delete(iAttivitadyn2modTSnapshot);
	    }
	    Set<IAttivitadyn2datiSnapshot> attivitadyn2datiSnapshots = iAttivitaSnapshot.getAttivitadyn2datiSnapshots();
	    for (IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshot : attivitadyn2datiSnapshots) {
		iAttivitadyn2datiSnapshotService.delete(iAttivitadyn2datiSnapshot);
	    }
	    // recupero i modelli e i campi dinamici delle schede dell'attività e li utilizzo per creare la lista di modelli e campi dello snapshot.
	    log.debug("Recupero la lista di tutti i modelli t presenti nell'attività : {}()",
		    new Object[] { attivita.getDenominazione(), attivita.getId().getCodice() });
	    List<IAttivitadyn2modellit> iAttivitadyn2modellits = iAttivitadyn2modellitService.findByAttivita(attivita.getId().getCodice(), null,
		    null);
	    // Variabile per creare un oggetto IAttivitadyn2modTSnapshot utilizzata per fare la copia di IAttivitadyn2modellit ed associarla 
	    // allo snapshot.
	    IAttivitadyn2modTSnapshot iAttivitadyn2modTSnapshot = null;
	    for (IAttivitadyn2modellit iAttivitadyn2modellit : iAttivitadyn2modellits) {
		iAttivitadyn2modTSnapshot = new IAttivitadyn2modTSnapshot();
		// Popolo l'id di iAttivitadyn2modTSnapshot
		IAttivitadyn2modTSnapshotId idAttivitadyn2modTSnapshot = new IAttivitadyn2modTSnapshotId();
		idAttivitadyn2modTSnapshot.setFkD2mtId(iAttivitadyn2modellit.getId().getFkD2mtId());
		idAttivitadyn2modTSnapshot.setFkIaId(attivita.getId().getCodice());
		idAttivitadyn2modTSnapshot.setFkIasId(iAttivitaSnapshot.getId().getCodice());
		idAttivitadyn2modTSnapshot.setIdcomune(ORMHelper.getIdcomune());
		// Popolo l'oggetto iAttivitadyn2modTSnapshot
		iAttivitadyn2modTSnapshot.setId(idAttivitadyn2modTSnapshot);
		iAttivitadyn2modTSnapshot.setAttivita(attivita);
		iAttivitadyn2modTSnapshot.setAttivitaSnapshot(iAttivitaSnapshot);
		iAttivitadyn2modTSnapshot.setDyn2Modellit(iAttivitadyn2modellit.getDyn2Modellit());
		// Faccio l'insert del modello
		iAttivitadyn2modTSnapshotService.insert(iAttivitadyn2modTSnapshot);
	    }
	    // Recupero la lista dei campi dinamici presenti nell'attività
	    log.debug("Recupero la lista di tutti i campi dinamici  presenti nell'attività : {}()",
		    new Object[] { attivita.getDenominazione(), attivita.getId().getCodice() });
	    List<IAttivitadyn2dati> iAttivitadyn2datis = iAttivitadyn2datiService.findByAttivita(attivita, null, null);
	    IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshot = null;
	    for (IAttivitadyn2dati iAttivitadyn2dati : iAttivitadyn2datis) {
		// Controllo che sia un campo dinamico e non un testo
		if (EntityUtils.getNestedProperty(iAttivitadyn2dati.getDyn2Campi(), "id.codice") != null) {
		    iAttivitadyn2datiSnapshot = new IAttivitadyn2datiSnapshot();
		    IAttivitadyn2datiSnapshotId idIAttivitadyn2datiSnapshot = new IAttivitadyn2datiSnapshotId();
		    idIAttivitadyn2datiSnapshot.setFkD2cId(iAttivitadyn2dati.getDyn2Campi().getId().getCodice());
		    idIAttivitadyn2datiSnapshot.setFkIaId(attivita.getId().getCodice());
		    idIAttivitadyn2datiSnapshot.setFkIasId(iAttivitaSnapshot.getId().getCodice());
		    idIAttivitadyn2datiSnapshot.setIdcomune(ORMHelper.getIdcomune());
		    idIAttivitadyn2datiSnapshot.setIndice(iAttivitadyn2dati.getId().getIndice());
		    idIAttivitadyn2datiSnapshot.setIndiceMolteplicita(iAttivitadyn2dati.getId().getIndiceMolteplicita());
		    iAttivitadyn2datiSnapshot.setId(idIAttivitadyn2datiSnapshot);
		    iAttivitadyn2datiSnapshot.setAttivita(attivita);
		    iAttivitadyn2datiSnapshot.setAttivitaSnapshot(iAttivitaSnapshot);
		    iAttivitadyn2datiSnapshot.setAutoins(iAttivitadyn2dati.getAutoins());
		    iAttivitadyn2datiSnapshot.setDyn2Campi(iAttivitadyn2dati.getDyn2Campi());
		    if (StringUtils.isNotBlank(iAttivitadyn2dati.getValore())) {
			iAttivitadyn2datiSnapshot.setValore(iAttivitadyn2dati.getValore());
		    }
		    if (StringUtils.isNotBlank(iAttivitadyn2dati.getValoredecodificato())) {
			iAttivitadyn2datiSnapshot.setValoredecodificato(iAttivitadyn2dati.getValoredecodificato());
		    }
		    // Inserisco il dato
		    iAttivitadyn2datiSnapshotService.insert(iAttivitadyn2datiSnapshot);
		}
	    }
	    return true;
	} catch (Exception e) {
	    log.error("Errore durante la chimata al metodo updateSnapShotCopiaDatiDinamici della classe IAttivitaServiceImpl: " + e.getMessage());
	    throw new RuntimeException(e.getMessage());
	}
    }

    /**
     * <pre>
     * 	o Prende la data validità dell'istanza passata se diversa da null, altrimenti la data di validità
     *    dell'istanza che rappresenta l'attività ( I_ATTIVITA.CODICEISTANZAULTIMA)
     * 	o elimina le righe delle tabelle I_ATTIVITA…_SNAPSHOT
     * 	  riferite a quella data 
     *  o Calcola un nuovo progressivo I_ATTIVITA_SNAPSHOT.IDS 
     * 	o Copia i dati delle tabelle
     * 	  I_ATTIVITA, I_ATTIVITADYN2MODELLIT e I_ATTIVITADYN2ATI nelle tabelle I_ATTIVITA_SNAPSHOT,I_ATTIVITADYN2MOD_T_SNAPSHOT,
     * 	  I_ATTIVITADYN2DATI_SNAPSHOT impostando il nuovo progressivo e la data (I_ATTIVITA_SNAPSHOT.DATA)
     * </pre>
     */
    @Override
    public IAttivitaSnapshot updateSnapShotCopia(IAttivita attivita) {

	try {
	    Istanze istanzeTemp = null;
	    istanzeTemp = istanzeService.findById(new PkId(attivita.getIstanza().getId().getCodice()));
	    log.debug(
		    "controllo se estiste  record in I_ATTIVITA_SNAPSHOT per l'attivita : {}({}) e per la data di validità dell'istanza ({}) rappresentativa : {})",
		    new Object[] { attivita.getDenominazione(), attivita.getId().getCodice(), istanzeTemp.getNumeroistanza(),
			istanzeTemp.getDatavalidita() });
	    IAttivitaSnapshot iAttivitaSnapshotDB = iAttivitaSnapshotService.findByData(attivita, istanzeTemp.getDatavalidita());
	    IAttivitaSnapshot iAttivitaSnapshot = new IAttivitaSnapshot();
	    if (iAttivitaSnapshotDB != null) {
		iAttivitaSnapshot = aggiornaIAttivitaSnapShot(attivita, iAttivitaSnapshotDB);
	    } else {
		iAttivitaSnapshot = createIAttivitaSnapShot(attivita, istanzeTemp);
	    }
	    iattivitaDAO.commit();
	    iattivitaDAO.flush();
	    log.debug("Record in _ATTIVITA_SNAPSHOT creato ......");
	    return iAttivitaSnapshot;
	} catch (Exception e) {
	    log.error("Errore nell' esecuzione del metodo updateSnapShotCopia(Integer codiceIstanza) con codiceIstanza : {}, Errore :{} ",
		    attivita.getIstanza().getId().getCodice(), e.getMessage());
	    throw new RuntimeException("Errore nell' esecuzione del metodo updateSnapShotCopia(Integer codiceIstanza) con codiceIstanza :" +
		    attivita.getIstanza().getId().getCodice() +
		    ", Errore " +
		    e.getMessage());
	}
    }

    private IAttivitaSnapshot aggiornaIAttivitaSnapShot(IAttivita attivita, IAttivitaSnapshot attivitaSnapshot) {

	log.debug("Aggiorno IAttivitaSnapShot passato a partire da IAttivita con codice {} ......", attivita.getId().getCodice());
	// setto i campi base di IATTIVITA_SNAPSHOT
	Istanze istanze = attivita.getIstanza();
	attivitaSnapshot.setAttiva(attivita.getAttiva());
	attivitaSnapshot.setAttivita(attivita);
	attivitaSnapshot.setData(istanze.getDatavalidita());
	attivitaSnapshot.setDenominazione(attivita.getDenominazione());
	attivitaSnapshot.setIstanza(istanze);
	attivitaSnapshot.setNumeropresenze(attivita.getNumeropresenze());
	attivitaSnapshot.setOperante(attivita.getOperante());
	attivitaSnapshot.setTipologiaAttivita(attivita.getTipologiaAttivita());
	// attivitaSnapshot.setCodiceOsservatorio(attivita.getCodiceOsservatorio());
	iAttivitaSnapshotService.update(attivitaSnapshot);
	log.debug("Aggionato IAttivitaSnapShot......");
	return attivitaSnapshot;
    }

    @Override
    public IAttivitaSnapshot updateSnapShotCrea(IAttivita attivita, Istanze istanza) {

	IAttivitaSnapshot iAttivitaSnapshot = createIAttivitaSnapShot(attivita, istanza);
	return iAttivitaSnapshot;
    }

    @Override
    public String updateAddSchedeDinamiche(IAttivitaFilter filter, Integer codiceScheda) {

	StringBuilder risultato = new StringBuilder("RISULTATO ELABORAZIONE ATTIVITA':<br /><br />");
	StringBuilder errori = new StringBuilder();
	List<Integer> idSchede = new ArrayList<Integer>();
	idSchede.add(codiceScheda);
	//Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(new PkId(codiceScheda));
	filter.setCodSchedaPerEscludereLeAttivita(codiceScheda);
	int count = this.countIAttivitaListHelperByFilter(filter);
	int coutErrori = 0;
	String numeroAttivita = String.valueOf(count);
	int firstResult = 0;
	List<IAttivitaListHelper> listAttivita = new ArrayList<IAttivitaListHelper>();
	//	filter.setCodSchedaPerEscludereLeAttivita(codiceScheda);
	listAttivita = this.findIAttivitaListHelperByFilter(filter, firstResult, MAX_RESULT);
	while (!listAttivita.isEmpty()) {
	    for (IAttivitaListHelper iAttivita : listAttivita) {
		try {
		    this.datiDinamiciService.aggiungiSchedeDinamiche(Integer.parseInt(iAttivita.getId().toString()), idSchede);
		} catch (Exception e) {
		    log.error("Errore durante l'inserimento della scheda cod {} e aggiornamento dei campi per l'attività {} : {}",
			    new Object[] { codiceScheda, iAttivita.getId(), e });
		    errori.append(
			    "Errore durante l'elaborazione dell'attività:" + iAttivita.getDenominazione() + "(" + iAttivita.getId() + ")<br />");
		    coutErrori++;
		}
	    }
	    // non viene più incrementato perchè a seguito è stato aggiunto il filtro setCodSchedaPerEscludereLeAttivita che 
	    // esclude chi ha già la scheda 
	    //firstResult = firstResult + MAX_RESULT;
	    listAttivita = this.findIAttivitaListHelperByFilter(filter, firstResult, MAX_RESULT);
	}
	int attivitaAggiornate = count - coutErrori;
	risultato.append("Num. attività da aggiornare: " + numeroAttivita + "<br />");
	risultato.append("Num. attività aggiornate: " + attivitaAggiornate + "<br />");
	if (coutErrori > 0) {
	    risultato.append("Per verificare gli errori controllare il file sigepro2.log<br />");
	    risultato.append("Errori:<br />");
	    risultato.append(errori + "<br />");
	}
	return risultato.toString();
    }

    private IAttivitaSnapshot createNewIAttivitaSnapshotFromIAttivitaSnapshotAndDate(IAttivitaSnapshot attivitaSnapshotSorgente, Date dataValidita) {

	log.debug("Creo una copia di IAttivitaSnapshot con codice {} con una nuova data validità: {} ......",
		new Object[] { attivitaSnapshotSorgente.getId().getCodice(), dataValidita.toString() });
	IAttivitaSnapshot attivitaSnapshotCopy = new IAttivitaSnapshot();
	// setto i campi base di IATTIVITA_SNAPSHOT
	attivitaSnapshotCopy.setAttiva(attivitaSnapshotSorgente.getAttiva());
	attivitaSnapshotCopy.setAttivita(attivitaSnapshotSorgente.getAttivita());
	attivitaSnapshotCopy.setData(dataValidita);
	attivitaSnapshotCopy.setDenominazione(attivitaSnapshotSorgente.getDenominazione());
	attivitaSnapshotCopy.setIstanza(attivitaSnapshotSorgente.getIstanza());
	attivitaSnapshotCopy.setNumeropresenze(attivitaSnapshotSorgente.getNumeropresenze());
	attivitaSnapshotCopy.setOperante(attivitaSnapshotSorgente.getOperante());
	attivitaSnapshotCopy.setTipologiaAttivita(attivitaSnapshotSorgente.getTipologiaAttivita());
	attivitaSnapshotCopy.setCodiceOsservatorio(attivitaSnapshotSorgente.getCodiceOsservatorio());
	iAttivitaSnapshotService.insert(attivitaSnapshotCopy);
	log.debug("Copia creata..............");
	return attivitaSnapshotCopy;
    }

    private IAttivitaSnapshot createIAttivitaSnapShot(IAttivita attivita, Istanze istanza) {

	log.debug("Creato IAttivitaSnapShot a partire da IAttivita con codice {} ......", attivita.getId().getCodice());
	IAttivitaSnapshot attivitaSnapshot = new IAttivitaSnapshot();
	// setto i campi base di IATTIVITA_SNAPSHOT
	attivitaSnapshot.setAttiva(attivita.getAttiva());
	attivitaSnapshot.setAttivita(attivita);
	attivitaSnapshot.setData(istanza.getDatavalidita());
	attivitaSnapshot.setDenominazione(attivita.getDenominazione());
	attivitaSnapshot.setIstanza(istanza);
	attivitaSnapshot.setNumeropresenze(attivita.getNumeropresenze());
	attivitaSnapshot.setOperante(attivita.getOperante());
	attivitaSnapshot.setTipologiaAttivita(attivita.getTipologiaAttivita());
	attivitaSnapshot.setCodiceOsservatorio(attivita.getCodiceOsservatorio());
	iAttivitaSnapshotService.insert(attivitaSnapshot);
	log.debug("Creata IAttivitaSnapShot......");
	return attivitaSnapshot;
    }

    //    
    @Override
    public List<IAttivita> findAttivitaWithoutSnapshot(Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.isNull("id.codice", "attivitaSnapshots"));
	ft.addRestriction(fr);
	List<IAttivita> list = iattivitaDAO.findByFilterTable(ft, firstResult, maxResult);
	return list;
    }

    @Override
    public List<Integer> findIdAttivitaWithoutSnapshot(Integer firstResult, Integer maxResult) {

	return iattivitaDAO.findIdAttivitaWithoutSnapshot(firstResult, maxResult);
    }

    @Override
    public List<IAttivita> findByIattivitaTipologie(Integer codiceIattivitaTipologia, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipologiaAttivitaId", codiceIattivitaTipologia, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("denominazione"));
	return iattivitaDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public void updateUpOrdine(Integer codiceIstanza, Integer codiceIstanzaSup) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	Istanze istanzeSup = istanzeService.findById(new PkId(codiceIstanzaSup));
	Integer ordineIstanza = istanza.getAttivitaOrdine();
	Integer ordineIstanzaSup = istanzeSup.getAttivitaOrdine();
	istanza.setAttivitaOrdine(ordineIstanzaSup);
	istanzeSup.setAttivitaOrdine(ordineIstanza);
	istanzeService.update(istanza);
	istanzeService.update(istanzeSup);
	iattivitaDAO.flush();
	iattivitaDAO.clear();
	istanzeDAO.commit();
	/////////////////////////////////////////////////////////////////////////////////////
	//  CALCOLO DATA FINE e DATA INIZIO
	///////////////////////////////////////////////////////////////////////////////////////
	// devo ricalcolare sempre perchè, mentre ala data inizio è data sempre dalla prima, la data fine è data dalla prima partendo dal basso
	// che ha nei campi dinamici il campo che determina la data fine (quindi non è detto sia l'ultimo in senso assoluto)
	log.debug("updateUpOrdine# Calcolo la data inizio e data fine dell'attività: {} dopo aver invertito le posizioni delle istanze. ",
		istanza.getAttivita().getId().getCodice());
	log.debug("updateUpOrdine#Istanza up {} - Nuovo Ordine {}", istanza.getId().getCodice(), ordineIstanzaSup);
	log.debug("updateUpOrdine#Istanza down {} - Nuovo Ordine {}", istanzeSup.getId().getCodice(), ordineIstanza);
	this.updateDataInizioEFineAttivita(istanza.getAttivita().getId().getCodice());
	this.ricalcolaSnapshot(istanza);
    }

    @Override
    public void scambiaOrdine(Integer idAttivita, Integer codiceIstanzaPrec, Integer codiceIstanzaSuc) {

	this.attivitaIstanzeService.scambiaOrdine(idAttivita, codiceIstanzaPrec, codiceIstanzaSuc);
    }

    public void ricalcolaSnapshot(Istanze istanza) {

	if (istanza.getDatavalidita() != null) {
	    iAttivitaSnapshotService.updateRicalcolaSnapshot(istanza.getAttivita(), istanza.getDatavalidita());
	}
    }

    private Set<Integer> findByLocalizzazione(Istanze istanza) {

	Verticalizzazioniparametri gruppoSoftware = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_I_ATTIVITA,
		WebConstants.VERTICALIZZAZIONE_I_ATTIVITA_GRUPPOSOFTWARE);
	String softwares[] = null;
	if (gruppoSoftware != null) {
	    if (StringUtils.isNotBlank(gruppoSoftware.getValore())) {
		softwares = gruppoSoftware.getValore().split(",");
		softwares = StringUtils.stripAll(softwares);
	    }
	}
	Set<Istanzestradario> istanzestradarios = istanza.getIstanzestradarios();
	Set<Integer> result = new HashSet<Integer>();
	for (Istanzestradario istanzestradario : istanzestradarios) {
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    if (softwares != null) {
		fr.addFilterField(FilterUtils.in("software", softwares, String.class));
	    }
	    if (StringUtils.isNotBlank(istanzestradario.getCivico())) {
		fr.addFilterField(FilterUtils.equals("civico", istanzestradario.getCivico(), String.class));
	    }
	    if (EntityUtils.getNestedProperty(istanzestradario.getStradariocolore(), "id.codicecolore") != null) {
		if (StringUtils.isNotBlank(istanzestradario.getStradariocolore().getId().getCodicecolore())) {
		    fr.addFilterField(FilterUtils.equals("stradariocoloreId", istanzestradario.getStradariocolore().getId().getCodicecolore(),
			    "istanza.istanzestradarios", String.class));
		}
	    }
	    fr.addFilterField(FilterUtils.equals("id.codice", istanzestradario.getStradario().getId().getCodice(), "stradario", Integer.class));
	    ft.addRestriction(fr);
	    List<VwIAttivitalista> vwIAttivitalistas = vwIAttivitalistaService.findByFilterTable(ft, 0, 50);
	    for (VwIAttivitalista vwIAttivitalista : vwIAttivitalistas) {
		result.add(vwIAttivitalista.getId().getCodice());
	    }
	}
	return result;
    }

    @Override
    public List<IAttivita> findListaAttivitaEsistenti(Integer codiceistanza, String tipoEccezione) {

	Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	Set<Integer> idAttivita = new HashSet<Integer>();
	List<VwIAttivitalista> attivitas = null;
	//1. Recupero la lista delle attività a parità di denominazione
	String denominazione = estraiDenominazione(codiceistanza);
	attivitas = this.findBydenominazione(denominazione, istanza, 0, 100, true);
	for (VwIAttivitalista vwal : attivitas) {
	    idAttivita.add(vwal.getId().getCodice());
	}
	//2. se la lista è vuota recupero anche quelle per localizzazione
	if (idAttivita.isEmpty()) {
	    idAttivita = this.findByLocalizzazione(istanza);
	}
	List<IAttivita> result = new ArrayList<IAttivita>();
	for (Integer idatt : idAttivita) {
	    result.add(this.findById(new PkId(idatt)));
	}
	return result;
    }

    @Override
    public List<Integer> findCodiciIstanza(Integer idiattivita) {

	return iattivitaDAO.findCodiciIstanza(idiattivita);
    }

    @Override
    public String exportModalitaPentaho(IAttivitaFilter attivitaFilter, Esportazioni esportazioni, Date data, String emailResponsabile,
	    String contesto, boolean isInviaMail) {

	String sessionId = ORMHelper.getToken();
	log.debug("exportModalitaPentaho# Cancello i record su tmp_esportazioni con sessionId: {}", sessionId);
	tmpEsportazioniService.deleteBysessionId(sessionId);
	log.debug("exportModalitaPentaho# Inizio esportazione attivita. Invio email. {}", isInviaMail);
	iattivitaDAO.exportModalitaPentaho(attivitaFilter, esportazioni, data, emailResponsabile, contesto, isInviaMail);
	return sessionId;
    }

    @Override
    public Date calcoloDataInizioAttivita(IAttivita iAttivita, Date allaData) {

	// la più vecchia con data validità non nulla
	if (allaData == null) {
	    allaData = Utilities.parseDateString("31/12/2999", false); // se nulla allora la imposto lontana
	}
	List<Istanze> istanzes = this.findIstanzeOrdinate(iAttivita, true); // capire se mettere true
	if (!istanzes.isEmpty()) {
	    log.debug("calcoloDataInizioAttivita# Cerco la data inizio per l'attivita: {}", iAttivita.getId().getCodice());
	    for (int j = istanzes.size() - 1; j >= 0; j--) {
		Istanze i = istanzes.get(j);
		if (i.getDatavalidita() != null && Utilities.compareDates(i.getDatavalidita(), allaData) <= 0) {
		    log.debug("calcoloDataInizioAttivita# Data fine: {} (alla data{}) trovata nell'istanza  {}",
			    new Object[] { Utilities.formatDate(i.getDatavalidita(), false), allaData, i.getId().getCodice() });
		    return i.getDatavalidita();
		}
	    }
	}
	log.debug("calcoloDataInizioAttivita# Data inizio attività non trovata");
	return null;
    }

    @Override
    public Date calcoloDataFineAttivita(IAttivita iAttivita, Date allaData) {

	//1. Verifico se la funzionalità è attiva
	if (!verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_I_ATTIVITA)) {
	    return null;
	}
	//2. Verifico se è configurato il campo da cui calcolare la fine attività
	Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_I_ATTIVITA,
		WebConstants.VERTICALIZZAZIONE_I_ATTIVITA_CAMPO_DYN_FINE_ATT);
	if (vp == null || StringUtils.isBlank(vp.getValore())) {
	    return null;
	}
	String nomeCampo = vp.getValore();
	//3. Inizio procedura di calcolo
	log.debug("calcoloDataFineAttivita# Inizio il calcolo della data fine per l'attivita: {}", iAttivita.getId().getCodice());
	//4. La più vecchia con data validità non nulla e successivo alla data inizio
	log.debug("calcoloDataFineAttivita# Cerco la data fine per l'attivita: {}", iAttivita.getId().getCodice());
	if (allaData == null) {
	    allaData = Utilities.parseDateString("31/12/2999", false); // se nulla allora la imposto lontana
	}
	List<Istanze> istanzes = this.findIstanzeOrdinate(iAttivita, true); // capire se mettere true
	// Parto dalla prima istanza (data validatà maggiore) e cerco nei campi dinamiche il valore del campo configurato in verticalizzazione
	// il primo che trovo le metto come data di fine validità
	for (Istanze i : istanzes) {
	    if (i.getDatavalidita() != null && // data validità non nulla e assumiamo che la attivtà abbia già una data inizio
		    Utilities.compareDates(iAttivita.getDataInizio(), i.getDatavalidita()) <= 0 && // la data inizio dell'attività deve essere minore uguale della data validita dell'istanza che sto verificando 
		    Utilities.compareDates(i.getDatavalidita(), allaData) <= 0) { //la data validità deve essere minore uguale a quella passata in argomento (per calcolo snapshot)
		log.debug("calcoloDataFineAttivita# Cerco nei nei campi delle schede dell'istanza {}, se trovo valorizzato il campo {}",
			i.getId().getCodice(), WebConstants.VERTICALIZZAZIONE_I_ATTIVITA_CAMPO_DYN_FINE_ATT);
		// Devo passare anche il software perche il metodo è richiamato anche da AttivitaWs.aggiornaCampiSchede() in cui viene 
		// impostato il software TT (in questo caso se non passo il softaware corretto non mi troverà mail il campo dinamico 
		// nell'istanza per verificare se modificare la data fine quando modifico una scheda)
		List<Istanzedyn2dati> id2ds = istanzedyn2datiService.findByIstanzaAndNomeCampo(i, nomeCampo, i.getSoftware().getCodice());
		if (!id2ds.isEmpty()) {
		    //Fine procedura di calcolo
		    log.debug("calcoloDataFineAttivita# Fine del calcolo della data fine per l'attivita: {}, la data fine trovata è {}",
			    iAttivita.getId().getCodice(), id2ds.get(0).getValoredecodificato());
		    return Utilities.parseDateString(id2ds.get(0).getValoredecodificato(), false);
		}
	    }
	}
	log.debug("calcoloDataFineAttivita# Fine del calcolo della data fine per l'attivita: {}, la data fine è vuota",
		iAttivita.getId().getCodice());
	return null;
    }

    @Override
    public IAttivita updateDataInizioEFineAttivita(Integer codiceAttivita) {

	log.debug("aggiornaDataInizioEFineAttivita# Start....");
	IAttivita iattivita = this.findById(new PkId(codiceAttivita));
	if (EntityUtils.getNestedProperty(iattivita, "id.codice") != null) {
	    Date dataInizio = calcoloDataInizioAttivita(iattivita, null);
	    Date dataFine = calcoloDataFineAttivita(iattivita, null);
	    iattivita.setDataInizio(dataInizio);
	    iattivita.setDataFine(dataFine);
	} else {
	    log.debug("aggiornaDataInizioEFineAttivita# Attivita con codice {} non esistente", codiceAttivita);
	}
	this.update(iattivita);
	iattivitaDAO.commit();
	iattivitaDAO.flush();
	iattivitaDAO.clear();
	log.debug("aggiornaDataInizioEFineAttivita# End ....");
	return iattivita;
    }

    @Override
    public List<IAttivitaDaChiudereHelper> findAttivitaScadute(Date data, Integer firstResult, Integer maxResult) {

	return iattivitaDAO.findAttivitaScadute(data, firstResult, maxResult);
    }

    @Override
    public int updateNonOperanteENonAttiva(IAttivitaDaChiudereHelper iAttivitaDaChiudereHelper) {

	return iattivitaDAO.updateNonOperanteENonAttiva(iAttivitaDaChiudereHelper);
    }

    @Override
    public void updateSistemaAttivaSuSnapshot(Integer codiceAttivita) {

	List<Integer> codiciSnapshot = iAttivitaSnapshotService.findTuttiICodici(codiceAttivita);
	Integer codiceAttivitaDaAggiornare = null;
	boolean isAttiva = false;
	for (Integer codicesnapshot : codiciSnapshot) {
	    try {
		log.debug("updateSistemaAttivaSuSnapshot# debug snapshot codice {}", codicesnapshot);
		IAttivitaSnapshot sna = iAttivitaSnapshotService.findById(new PkId(codicesnapshot));
		if (sna != null) {
		    log.warn("updateSistemaAttivaSuSnapshot# elabora attività codice {}", sna.getAttivita().getId().getCodice());
		    if (codiceAttivitaDaAggiornare != null && !codiceAttivitaDaAggiornare.equals(sna.getAttivita().getId().getCodice())) {
			// AGGIORNA ULTIMO RECORD ATTIVA SU I_ATTIVITA codiceAttivitaDaAggiornare E isAttiva
			iattivitaDAO.updateAttiva(codiceAttivitaDaAggiornare, isAttiva);
			iattivitaDAO.commit();
			iattivitaDAO.flush();
			iattivitaDAO.clear();
			log.warn("updateSistemaAttivaSuSnapshot# record attività codice {} aggiornato", sna.getAttivita().getId().getCodice());
		    }
		    codiceAttivitaDaAggiornare = sna.getAttivita().getId().getCodice();
		    isAttiva = istanzeService.findIfIsAttivitaAttivaFromDataValuditaAndAttivita(sna.getData(), sna.getAttivita().getId().getCodice());
		    sna.setAttiva(isAttiva);
		    if (!isAttiva) {
			sna.setOperante(false);
		    }
		    iAttivitaSnapshotService.update(sna);
		}
	    } catch (Exception e) {
		log.error("updateSistemaAttivaSuSnapshot: {}", e);
		throw new RuntimeException("Errore nell'elaborazione della attività " + codiceAttivitaDaAggiornare + ", errore: " + e.getMessage());
	    }
	}
	if (codiceAttivitaDaAggiornare != null) {
	    try {
		log.warn("updateSistemaAttivaSuSnapshot# elabora ultima attività codice {}", codiceAttivitaDaAggiornare);
		// AGGIORNA ULTIMO RECORD ATTIVA SU I_ATTIVITA codiceAttivitaDaAggiornare E isAttiva
		iattivitaDAO.updateAttiva(codiceAttivitaDaAggiornare, isAttiva);
		iattivitaDAO.commit();
		iattivitaDAO.flush();
		iattivitaDAO.clear();
	    } catch (Exception e) {
		e.printStackTrace();
		log.error("updateSistemaAttivaSuSnapshot: {}", e);
		throw new RuntimeException(
			"Errore nell'elaborazione dell'ultima attività " + codiceAttivitaDaAggiornare + ", errore: " + e.getMessage());
	    }
	}
    }

    @Override
    public void updateSistemaDenominazioneAttivita(Integer codiceAttivita) {

	List<Integer> codiciSnapshot = iAttivitaSnapshotService.findTuttiICodici(codiceAttivita);
	Integer codiceAttivitaDaAggiornare = null;
	if (!checkAggiornaDenominazione()) {
	    throw new RuntimeException("Non è possibile procedere è settato il parametro " +
		    WebConstants.VERTICALIZZAZIONE_I_ATTIVITA_AGGIORNADENOMINAZIONE +
		    " della verticalizzazione" +
		    WebConstants.VERTICALIZZAZIONE_I_ATTIVITA);
	}
	String denominazione = "";
	for (Integer codicesnapshot : codiciSnapshot) {
	    try {
		log.debug("updateSistemaDenominazioneAttivita# debug snapshot codice {}", codicesnapshot);
		IAttivitaSnapshot sna = iAttivitaSnapshotService.findById(new PkId(codicesnapshot));
		if (sna != null) {
		    log.warn("updateSistemaAttivaSuSnapshot# elabora attività codice {}", sna.getAttivita().getId().getCodice());
		    if (codiceAttivitaDaAggiornare != null && !codiceAttivitaDaAggiornare.equals(sna.getAttivita().getId().getCodice())) {
			// AGGIORNA ULTIMO RECORD DENOMINAZIONE SU I_ATTIVITA 
			iattivitaDAO.updateDenominazione(codiceAttivitaDaAggiornare, denominazione);
			iattivitaDAO.commit();
			iattivitaDAO.flush();
			iattivitaDAO.clear();
			log.warn("updateSistemaDenominazioneAttivita# record attività codice {} aggiornato", sna.getAttivita().getId().getCodice());
		    }
		    denominazione = estraiDenominazione(sna.getIstanza().getId().getCodice());
		    codiceAttivitaDaAggiornare = sna.getAttivita().getId().getCodice();
		    sna.setDenominazione(denominazione);
		    iAttivitaSnapshotService.update(sna);
		}
	    } catch (Exception e) {
		e.printStackTrace();
		log.error("updateSistemaDenominazioneAttivita: {}", e);
		throw new RuntimeException("Errore nell'elaborazione della attività " + codiceAttivitaDaAggiornare + ", errore: " + e.getMessage());
	    }
	}
	if (codiceAttivitaDaAggiornare != null) {
	    try {
		log.warn("updateSistemaDenominazioneAttivita# elabora ultima attività codice {}", codiceAttivitaDaAggiornare);
		// AGGIORNA ULTIMO RECORD DENOMINAZIONE SU I_ATTIVITA 
		iattivitaDAO.updateDenominazione(codiceAttivitaDaAggiornare, denominazione);
		iattivitaDAO.commit();
		iattivitaDAO.flush();
		iattivitaDAO.clear();
	    } catch (Exception e) {
		e.printStackTrace();
		log.error("updateSistemaDenominazioneAttivita: {}", e);
		throw new RuntimeException(
			"Errore nell'elaborazione dell'ultima attività " + codiceAttivitaDaAggiornare + ", errore: " + e.getMessage());
	    }
	}
    }

    @Override
    public List<IstanzeAttivitaHelper> populateIstanzeAttivitaAutorizzazioniHelper(List<Istanze> istanzes, boolean raggruppaPerIstanza) {

	List<IstanzeAttivitaHelper> attivitaHelpers = new ArrayList<IstanzeAttivitaHelper>();
	IstanzeAttivitaHelper iah = new IstanzeAttivitaHelper();
	AutorizzazioniAttivitaHelper aah = null;
	for (Istanze istanze : istanzes) {
	    String numIstanza = "";
	    if (raggruppaPerIstanza) {
		iah = new IstanzeAttivitaHelper();
		numIstanza = istanze.getNumeroistanza();
	    } else {
		numIstanza = "Dummy";
	    }
	    iah.setNumeroIstanza(numIstanza);
	    //
	    Set<Autorizzazioni> autorizzazionis = istanze.getAutorizzazionis();
	    //
	    for (Autorizzazioni autorizzazioni : autorizzazionis) {
		aah = new AutorizzazioniAttivitaHelper();
		if (autorizzazioni.getAutorizzazioniConcessionisForFkAutconcAutatt().isEmpty()) {
		    aah.setCodiceIstanza(istanze.getId().getCodice());
		    aah.setCodiceAutorizzazione(autorizzazioni.getId().getCodice());
		    aah.setNumeroIstanza(istanze.getNumeroistanza());
		    aah.setComune(autorizzazioni.getAutorizcomune().getComune());
		    aah.setDataAutorizzazione(autorizzazioni.getAutorizdata());
		    aah.setNumeroAutorizzazione(autorizzazioni.getAutoriznumero());
		    aah.setRegistroAutorizzazione(autorizzazioni.getTipologiaregistro().getTrDescrizione());
		    aah.setStato(autorizzazioni.getFlagAttiva());
		    aah.setSubentrata(false);
		    aah.setDataCessazione(autorizzazioni.getDataCessazione());
		    iah.getAutorizzazioniAttivitaHelpers().add(aah);
		}
	    }
	    //
	    Set<AutorizzazioniSubentri> autorizzazioniSubentris = istanze.getAutorizzazionisubentris();
	    for (AutorizzazioniSubentri autorizzazioniSubentri : autorizzazioniSubentris) {
		aah = new AutorizzazioniAttivitaHelper();
		if (autorizzazioniSubentri.getAutSubentrisConcs().isEmpty()) {
		    aah.setNumeroIstanza(istanze.getNumeroistanza());
		    aah.setComune(autorizzazioniSubentri.getAutorizcomune().getComune());
		    aah.setDataAutorizzazione(autorizzazioniSubentri.getAutorizdata());
		    aah.setNumeroAutorizzazione(autorizzazioniSubentri.getAutoriznumero());
		    aah.setRegistroAutorizzazione(autorizzazioniSubentri.getTipologiaregistro().getTrDescrizione());
		    aah.setStato(false);
		    aah.setDataCessazione(autorizzazioniSubentri.getDataCessazione());
		    aah.setSubentrata(true);
		    iah.getAutorizzazioniAttivitaHelpers().add(aah);
		}
	    }
	    if (raggruppaPerIstanza) {
		attivitaHelpers.add(iah);
	    }
	}
	if (!raggruppaPerIstanza) {
	    attivitaHelpers.add(iah);
	}
	AutorizzazioniAttivitaHelperComparator autorizzazioniAttivitaHelperComparator = new AutorizzazioniAttivitaHelperComparator(
		DAOOrderTypeEnum.DESC);
	Collections.sort(iah.getAutorizzazioniAttivitaHelpers(), autorizzazioniAttivitaHelperComparator);
	return attivitaHelpers;
    }

    @Override
    public List<IstanzeAttivitaHelper> populateIstanzeAttivitaConcessioniHelper(List<Istanze> istanzes, boolean raggruppaPerIstanza) {

	List<IstanzeAttivitaHelper> attivitaHelpers = new ArrayList<IstanzeAttivitaHelper>();
	IstanzeAttivitaHelper iah = new IstanzeAttivitaHelper();
	AutorizzazioniAttivitaHelper aah = null;
	for (Istanze istanze : istanzes) {
	    String numIstanza = "";
	    if (raggruppaPerIstanza) {
		iah = new IstanzeAttivitaHelper();
		numIstanza = istanze.getNumeroistanza();
	    } else {
		numIstanza = "Dummy";
	    }
	    iah.setNumeroIstanza(numIstanza);
	    //
	    Set<Autorizzazioni> autorizzazionis = istanze.getAutorizzazionis();
	    //
	    for (Autorizzazioni autorizzazioni : autorizzazionis) {
		aah = new AutorizzazioniAttivitaHelper();
		if (!autorizzazioni.getAutorizzazioniConcessionisForFkAutconcAutatt().isEmpty()) {
		    aah.setCodiceIstanza(istanze.getId().getCodice());
		    aah.setCodiceAutorizzazione(autorizzazioni.getId().getCodice());
		    aah.setNumeroIstanza(istanze.getNumeroistanza());
		    aah.setComune(autorizzazioni.getAutorizcomune().getComune());
		    aah.setDataAutorizzazione(autorizzazioni.getAutorizdata());
		    aah.setNumeroAutorizzazione(autorizzazioni.getAutoriznumero());
		    aah.setRegistroAutorizzazione(autorizzazioni.getTipologiaregistro().getTrDescrizione());
		    aah.setStato(autorizzazioni.getFlagAttiva());
		    aah.setSubentrata(false);
		    aah.setDataCessazione(autorizzazioni.getDataCessazione());
		    iah.getAutorizzazioniAttivitaHelpers().add(aah);
		}
	    }
	    //
	    Set<AutorizzazioniSubentri> autorizzazioniSubentris = istanze.getAutorizzazionisubentris();
	    for (AutorizzazioniSubentri autorizzazioniSubentri : autorizzazioniSubentris) {
		aah = new AutorizzazioniAttivitaHelper();
		if (!autorizzazioniSubentri.getAutSubentrisConcs().isEmpty()) {
		    aah.setNumeroIstanza(istanze.getNumeroistanza());
		    aah.setComune(autorizzazioniSubentri.getAutorizcomune().getComune());
		    aah.setDataAutorizzazione(autorizzazioniSubentri.getAutorizdata());
		    aah.setNumeroAutorizzazione(autorizzazioniSubentri.getAutoriznumero());
		    aah.setRegistroAutorizzazione(autorizzazioniSubentri.getTipologiaregistro().getTrDescrizione());
		    aah.setStato(false);
		    aah.setDataCessazione(autorizzazioniSubentri.getDataCessazione());
		    aah.setSubentrata(true);
		    iah.getAutorizzazioniAttivitaHelpers().add(aah);
		}
	    }
	    if (raggruppaPerIstanza) {
		attivitaHelpers.add(iah);
	    }
	}
	if (!raggruppaPerIstanza) {
	    attivitaHelpers.add(iah);
	}
	AutorizzazioniAttivitaHelperComparator autorizzazioniAttivitaHelperComparator = new AutorizzazioniAttivitaHelperComparator(
		DAOOrderTypeEnum.DESC);
	Collections.sort(iah.getAutorizzazioniAttivitaHelpers(), autorizzazioniAttivitaHelperComparator);
	return attivitaHelpers;
    }

    @Override
    public void collegaIstanza(IAttivita attivita, Istanze istanza) {

	this.attivitaIstanzeService.collegaIstanze(attivita, istanza);
    }

    @Override
    public Integer generaCodiceOsservatorio() {

	return this.iattivitaDAO.generaCodiceOsservatorio();
    }

    @Override
    public List<LocalizzazioniAttivitaDTO> findLocalizzazioniByFilter(IAttivitaFilter filter) {

	return this.iattivitaDAO.findLocalizzazioniByFilter(filter);
    }

    @Override
    public List<Integer> findIdAttivitaDaiParametriDelCartografico(String uuidChiamata) {

	ParametriResponse response = this.cartograficoService.getParametri(uuidChiamata);
	if (response == null || response.getParametri() == null || response.getParametri().isEmpty()) {
	    return null;
	}
	//Se nelle additional properties è definitia una proprietà chiamata id_attivita
	//questa conterrà l'id dell'attività di riferimento
	//E' una property standard e riservata da utilizzare tra i vari cartografici per questo scopo
	List<Integer> elencoIdAttivita = new ArrayList<Integer>();
	for (ParametroResponse parametri : response.getParametri()) {
	    String idAttivita = parametri.getAdditionalPropertyValue("id_attivita");
	    if (!StringUtils.isBlank(idAttivita)) {
		elencoIdAttivita.add(Integer.parseInt(idAttivita));
	    }
	}
	return elencoIdAttivita;
    }
}
