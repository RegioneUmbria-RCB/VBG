package it.gruppoinit.pal.gp.core.features.autorizzazioni;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.AutorizzazioneSpuntistaHelper;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniAttivita;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniCsi;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentriConc;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.Concessionitipi;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.DehorsCfg;
import it.gruppoinit.pal.gp.core.domain.DehorsLog;
import it.gruppoinit.pal.gp.core.domain.DehorsMqIstanze;
import it.gruppoinit.pal.gp.core.domain.DocumentiAutorizzazione;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzecollegate;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiId;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiStoricoId;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitStorico;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitStoricoId;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeStorico;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.SpuntistiMercati;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.VwEntilocali;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniDTO;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniExportHelper;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.EstremiAutDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzaAutConcHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzecollegateHelper;
import it.gruppoinit.pal.gp.core.domain.helper.Istanzedyn2datiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniCommand;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniFilter;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniSubentriCommand;
import it.gruppoinit.pal.gp.core.domain.web.ValidaEliminazioneAutConcCommand;
import it.gruppoinit.pal.gp.core.domain.web.ValidazioneDataCessazioneSubentroCommand;
import it.gruppoinit.pal.gp.core.domain.web.ValidazioneOccupanteCommand;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.RuoloAutorizzazioneEnum;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.MercatoSrvRequest;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.auditing.ModificaOccupanteLogger;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoCancellazioneAutOConc;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoModificaDataCessazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoModificaOccupante;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneBloccata;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneConcessioneEliminata;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneInserita;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneSbloccata;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoCheckEliminazioneSubentro;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoCheckModificaDataCessazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoCheckModificaOccupante;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoCheckSubentro;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoConcessioneInserita;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoDataCessazioneSubentroModificata;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoOccupanteModificato;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoRichiestaBloccoAutorizzazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoRichiestaSbloccoAutorizzazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoSubentroEffettuato;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioneCancellazioneAutConcException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioniSubentriException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.model.AutorizzazioniMercatoSrvBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.EstremiAutorizzazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.NumerazioneEnum;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.NumerazioneFactory;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri.CheckSubentroRequest;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri.DatiCausaliSubentroRequest;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri.EsitoElaborazioneSubentri;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri.ModificheScambioPosteggioHelper;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.AutorizzazioniComposteSpostaPresenzeDTO;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati.CodiceFirmatario;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.IstanzecollegateService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IBorsellinoAutorizzazioniDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.TipoOperazioneBorsAutEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi.EventoOperazioneAutorizzazione;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.IBorsellinoMovimentiDAO;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniAttivitaService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniCsiService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriConcService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;
import it.gruppoinit.pal.gp.core.service.ConcessionicausaliService;
import it.gruppoinit.pal.gp.core.service.ConcessionitipiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.DehorsCfgService;
import it.gruppoinit.pal.gp.core.service.DehorsLogService;
import it.gruppoinit.pal.gp.core.service.DehorsMqIstanzeService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellidService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiStoricoService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitStoricoService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeStoricoService;
import it.gruppoinit.pal.gp.core.service.SpuntistiMercatiService;
import it.gruppoinit.pal.gp.core.service.TmpEsportazioniService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VwEntilocaliService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniConcessioniRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author fabrizioc
 */
@Service
public class AutorizzazioniServiceImpl extends BaseServiceImpl<Autorizzazioni, PkId> implements AutorizzazioniService {

    private static final Logger log = LoggerFactory.getLogger(AutorizzazioniServiceImpl.class);
    private AutorizzazioniDAO autorizzazioniDAO;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private AutorizzazioniConcessioniService autorizzazioniConcessioniService;
    @Autowired
    private DehorsCfgService dehorsCfgService;
    @Autowired
    private DehorsMqIstanzeService dehorsMqIstanzeService;
    @Autowired
    private DehorsLogService dehorsLogService;
    @Autowired
    private VwEntilocaliService vwEntilocaliService;
    @Autowired
    private ConcessionicausaliService concessionicausaliService;
    @Autowired
    private ConcessionitipiService concessionitipiService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private MercatiConfigurazioneService mercatiConfigurazioneService;
    @Autowired
    private ProtocollazioneService protocollazioneService;
    @Autowired
    private TipologiaregistriService tipologiaregistriService;
    @Autowired
    private AutorizzazioniSubentriService autorizzazioniSubentriService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private Istanzedyn2datiService istanzedyn2datiService;
    @Autowired
    private MercatipresenzeStoricoService mercatipresenzeStoricoService;
    @Autowired
    private SpuntistiMercatiService spuntistiMercatiService;
    @Autowired
    private DocumentiAutorizzazioneService documentiAutorizzazioneService;
    @Autowired
    private AutorizzazioniSubentriConcService autorizzazioniSubentriConcService;
    private static String DEFAULT_ID_PROTOCOLLO = "000000";
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private IstanzecollegateService istanzecollegateService;
    @Autowired
    private Istanzedyn2modellitStoricoService istanzedyn2modellitStoricoService;
    @Autowired
    private Istanzedyn2datiStoricoService istanzedyn2datiStoricoService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private AutorizzazioniCsiService autorizzazioniCsiService;
    @Autowired
    private IEventPublisher eventPublisher;
    @Autowired
    private TmpEsportazioniService tmpEsportazioniService;
    @Autowired
    private AutorizzazioniAttivitaService autorizzazioniAttivitaService;
    @Autowired
    private AttivitaService attivitaService;
    private AutorizzazioniMetadatiService metadatiService;
    private IBorsellinoMovimentiDAO borsellinoMovimentiDAO;
    private IBorsellinoAutorizzazioniDAO borsellinoAutorizzazioniDAO;
    private IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService;
    @Autowired
    private MercatipresenzeDService mercatipresenzeDService;

    @Autowired
    public void setMetadatiService(AutorizzazioniMetadatiService metadatiService) {

	this.metadatiService = metadatiService;
    }

    @Autowired
    public void setAutorizzazioniDAO(AutorizzazioniDAO autorizzazioniDAO) {

	this.autorizzazioniDAO = autorizzazioniDAO;
    }

    @Autowired
    public void setBorsellinoMovimentiDAO(IBorsellinoMovimentiDAO borsellinoMovimentiDAO) {

	this.borsellinoMovimentiDAO = borsellinoMovimentiDAO;
    }

    @Autowired
    public void setBorsellinoAutorizzazioniDAO(IBorsellinoAutorizzazioniDAO borsellinoAutorizzazioniDAO) {

	this.borsellinoAutorizzazioniDAO = borsellinoAutorizzazioniDAO;
    }

    @Autowired
    public void setComportamentiMercatiService(IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService) {

	this.comportamentiMercatiService = comportamentiMercatiService;
    }

    @Override
    protected Class<Autorizzazioni> getEntityClass() {

	return Autorizzazioni.class;
    }

    @Override
    public List<Autorizzazioni> findAll(Integer firstResult, Integer maxResult) {

	return autorizzazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insertConcessione(AutorizzazioniConcessioni entity) {

	dataIntegration(entity.getAutorizzazioniByFkAutconcAutatt());
	gestisciNumeroAutorizzazione(entity.getAutorizzazioniByFkAutconcAutatt(), ORMHelper.getToken());
	try {
	    validateEntity(entity.getAutorizzazioniByFkAutconcAutatt());
	} catch (Exception e) {
	    List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	    if (e instanceof BaseValidationException) {
		ivs = ((BaseValidationException) e).getInvalidValues();
		for (InvalidValue invalidValue : ivs) {
		    invalidValue.addParentBean(entity, "autorizzazioniByFkAutconcAutatt");
		    //ivs.add(invalidValue);
		}
	    }
	    this.throwValidationMessages(ivs);
	}
	entity.getAutorizzazioniByFkAutconcAutatt().setAutorizzazioniSubentris(null);//Usato per evitare errori con hibernate in fase di insert
	if (true) {
	    Autorizzazioni autorizzazioneEsistente = null;
	    if (overrideUniqueConstraint()) {
		autorizzazioneEsistente = this.findByNumeroAndComune(entity.getAutorizzazioniByFkAutconcAutatt().getAutoriznumero(),
			entity.getAutorizzazioniByFkAutconcAutatt().getAutorizcomune().getCodicecomune());
	    } else {
		autorizzazioneEsistente = this.findAutOConcByEstremi(entity.getAutorizzazioniByFkAutconcAutatt().getAutoriznumero(),
			entity.getAutorizzazioniByFkAutconcAutatt().getAutorizdata(),
			entity.getAutorizzazioniByFkAutconcAutatt().getAutorizcomune().getCodicecomune(),
			entity.getAutorizzazioniByFkAutconcAutatt().getTipologiaregistro().getId().getCodice());
	    }
	    if (autorizzazioneEsistente != null) {
		this.throwValidationMessage(
			new InvalidValue("autorizzazioni.service_error.autorizzazione_con_estremi_gia_presenti", null, null, "", null));
	    } else {
		AutorizzazioniSubentri autSubEsistente = null;
		if (overrideUniqueConstraint()) {
		    autSubEsistente = autorizzazioniSubentriService.findByNumeroAndComune(
			    entity.getAutorizzazioniByFkAutconcAutatt().getAutoriznumero(),
			    entity.getAutorizzazioniByFkAutconcAutatt().getAutorizcomune().getCodicecomune());
		} else {
		    autSubEsistente = autorizzazioniSubentriService.findAutSubOConcSubByEstremi(
			    entity.getAutorizzazioniByFkAutconcAutatt().getAutoriznumero(),
			    entity.getAutorizzazioniByFkAutconcAutatt().getAutorizdata(),
			    entity.getAutorizzazioniByFkAutconcAutatt().getAutorizcomune().getCodicecomune(),
			    entity.getAutorizzazioniByFkAutconcAutatt().getTipologiaregistro().getId().getCodice());
		}
		if (autSubEsistente != null) {
		    this.throwValidationMessage(
			    new InvalidValue("autorizzazioni.service_error.autorizzazione_con_estremi_gia_presenti", null, null, "", null));
		}
	    }
	    // se flag_attiva è falso allora non devo registrare la data e la causale di cessazione
	    if (BooleanUtils.isTrue(entity.getAutorizzazioniByFkAutconcAutatt().getFlagAttiva())) {
		entity.getAutorizzazioniByFkAutconcAutatt().setDataCessazione(null);
		entity.getAutorizzazioniByFkAutconcAutatt().setConcessionicausaliByFkAutConccausCess(null);
	    }
	    EstremiAutorizzazione estremi = new NumerazioneFactory(configurazioneService, tipologiaregistriService, userSecurityService,
		    protocollazioneService, entity.getAutorizzazioniByFkAutconcAutatt()).getService().assegnaNumero();
	    if (StringUtils.isNotBlank(estremi.getNumero())) {
		entity.getAutorizzazioniByFkAutconcAutatt().setFkidprotocollo(estremi.getIdRiferimento());
		entity.getAutorizzazioniByFkAutconcAutatt().setAutoriznumero(estremi.getNumero());
	    }
	    entity.getAutorizzazioniByFkAutconcAutatt().setAutorizzazioniConcessionisForFkAutconcAutatt(null);
	    Set<MercatipresenzeStorico> s = entity.getAutorizzazioniByFkAutconcAutatt().getMercatipresenzeStoricos();
	    Set<MercatipresenzeStorico> s2 = new HashSet<MercatipresenzeStorico>();
	    if (s != null && !s.isEmpty()) {
		s2.addAll(s);
	    }
	    entity.getAutorizzazioniByFkAutconcAutatt().setMercatipresenzeStoricos(null);
	    autorizzazioniDAO.insert(entity.getAutorizzazioniByFkAutconcAutatt());
	    childDataInsert(entity.getAutorizzazioniByFkAutconcAutatt(), s2);
	    Autorizzazioni autCollegata = entity.getAutorizzazioniByFkAutconcAutcoll();
	    if (null != autCollegata) {
		if (EntityUtils.getNestedProperty(autCollegata, "id.codice") == null) {
		    gestisciNumeroAutorizzazione(autCollegata, ORMHelper.getToken());
		    try {
			validateEntity(autCollegata);
		    } catch (Exception e) {
			List<InvalidValue> ivs = new ArrayList<InvalidValue>();
			if (e instanceof BaseValidationException) {
			    ivs = ((BaseValidationException) e).getInvalidValues();
			    for (InvalidValue invalidValue : ivs) {
				invalidValue.addParentBean(entity, "autorizzazioniByFkAutconcAutcoll");
			    }
			}
			entity.setAutorizzazioniByFkAutconcAutcoll(autCollegata);
			entity.setAutorizzazioniByFkAutconcAutatt(entity.getAutorizzazioniByFkAutconcAutatt());
			this.throwValidationMessages(ivs);
		    }
		    estremi = new NumerazioneFactory(configurazioneService, tipologiaregistriService, userSecurityService, protocollazioneService,
			    autCollegata).getService().assegnaNumero();
		    if (StringUtils.isNotBlank(estremi.getNumero())) {
			entity.getAutorizzazioniByFkAutconcAutatt().setFkidprotocollo(estremi.getIdRiferimento());
			entity.getAutorizzazioniByFkAutconcAutatt().setAutoriznumero(estremi.getNumero());
		    }
		    autCollegata.setDatascadenza(entity.getAutorizzazioniByFkAutconcAutatt().getDatascadenza());
		    autorizzazioniDAO.insert(autCollegata);
		} else {
		    // l'utente ha ricercato ed assegnato un'autorizzazione esistente
		    autCollegata = autorizzazioniDAO.findById(new PkId(autCollegata.getId().getCodice()));
		}
		entity.setAutorizzazioniByFkAutconcAutcoll(autCollegata);
	    }
	    entity.setAutorizzazioniByFkAutconcAutatt(entity.getAutorizzazioniByFkAutconcAutatt());
	    try {
		autorizzazioniConcessioniService.insert(entity);
		// Operazioni che vengono svolte se si verifica condizione specifiche
		this.eventPublisher.publish(new EventoConcessioneInserita(entity.getId().getCodice(),
			entity.getAutorizzazioniByFkAutconcAutatt().getIstanza().getComune().getCodicecomune()));
		operazioniOpzionali(entity);
	    } catch (Exception e) {
		List<InvalidValue> ivs = new ArrayList<InvalidValue>();
		if (e instanceof BaseValidationException) {
		    ivs = ((BaseValidationException) e).getInvalidValues();
		}
		this.throwValidationMessages(ivs);
	    }
	}
    }

    private void operazioniOpzionali(AutorizzazioniConcessioni entity) {

	//1. Operazione svoltai caso di mercato che gestisce la poszione dei posteggi
	if (entity.getMercati().getFlagGestisciPosizioni()) {
	    log.debug("operazioniOpzionali# Mercato gestisce posizione dei posteggi");
	    Integer posizioneMax = mercatiDService.findPosizioneMaxByMercato(entity.getMercati().getId().getCodice());
	    MercatiD mercatoD = entity.getMercatiD();
	    mercatoD.setPosizione(posizioneMax + 1);
	    mercatiDService.update(mercatoD);
	}
    }

    /**
     * il metodo controlla per l'entity passata se il registro protocolla o deve scrivere un progressivo. Se protocolla
     * allora assegna un numero temporaneo DEFAULT_ID_PROTOCOLLO per permettere di passare la validazione dell'entity.
     * 
     * @param entity
     * @param webServiceBaseUrl
     * @param token
     */
    private void gestisciNumeroAutorizzazione(Autorizzazioni entity, String token) {

	if (EntityUtils.getNestedProperty(entity, "tipologiaregistro.id.codice") == null) {
	    throw new RuntimeException("Non e' stato specificato il registro");
	}
	if (tipologiaregistriService.checkSeNumeratoreEsterno(entity.getTipologiaregistro().getId().getCodice().intValue())) {
	    if (StringUtils.isBlank(entity.getAutoriznumero())) {
		entity.setAutoriznumero(DEFAULT_ID_PROTOCOLLO);
	    }
	    if (entity.getAutorizdata() == null) {
		entity.setAutorizdata(Calendar.getInstance().getTime());
	    }
	    if (entity.getDataRilascio() == null) {
		entity.setDataRilascio(Calendar.getInstance().getTime());
	    }
	}
	//else {
	//    try {
	//	if (StringUtils.isNotBlank(entity.getAutoriznumero())) {
	//	    tipologiaregistriService.scriviProgressivoRegistro(entity.getTipologiaregistro().getId().getCodice().intValue(),
	//		    entity.getAutoriznumero());
	//	}
	//  } catch (Exception e) {
	//	this.throwValidationMessage(
	//		new InvalidValue("autorizzazioni.service_error.aggiornamento_progressivo_registro_fallito", null, null, "", null));
	//  }
	//}
    }

    @Override
    public Autorizzazioni findById(PkId id) {

	return autorizzazioniDAO.findById(id);
    }

    private void validateEntityOnUpdate(AutorizzazioniConcessioni entity, Autorizzazioni aut) {

	try {
	    validateEntity(aut);
	} catch (BaseValidationException e) {
	    List<InvalidValue> ivs = e.getInvalidValues();
	    for (InvalidValue invalidValue : ivs) {
		invalidValue.addParentBean(entity, "autorizzazioniByFkAutconcAutatt");
	    }
	    this.throwValidationMessages(ivs);
	} catch (Exception e) {
	    this.throwValidationMessages(e);
	}
    }

    private void aggiornaProgressivoRegistro(Autorizzazioni autOriginale, Autorizzazioni autModificata) {

	String numeroConcessioneOriginale = autOriginale.getAutoriznumero();
	String numeroConcessioneModificata = autModificata.getAutoriznumero();
	if (numeroConcessioneModificata.equalsIgnoreCase(numeroConcessioneOriginale)) {
	    return;
	}
	int codiceTipoRegistro = autModificata.getTipologiaregistro().getId().getCodice().intValue();
	try {
	    this.tipologiaregistriService.scriviProgressivoRegistro(codiceTipoRegistro, numeroConcessioneModificata);
	} catch (Exception e) {
	    this.throwValidationMessage(
		    new InvalidValue("autorizzazioni.service_error.aggiornamento_progressivo_registro_fallito", null, null, "", null));
	}
    }

    private void valorizzaInfoAggiuntive(Autorizzazioni autorizzazione) {

	Concessionicausali concessionicausaliAcq = null;
	Concessionicausali concessionicausaliCess = null;
	if (EntityUtils.getNestedProperty(autorizzazione.getConcessionicausaliByFkAutConccausAcq(), "id.codice") != null) {
	    concessionicausaliAcq = concessionicausaliService
		    .findById(new PkId(autorizzazione.getConcessionicausaliByFkAutConccausAcq().getId().getCodice()));
	}
	if (EntityUtils.getNestedProperty(autorizzazione.getConcessionicausaliByFkAutConccausCess(), "id.codice") != null) {
	    concessionicausaliCess = concessionicausaliService
		    .findById(new PkId(autorizzazione.getConcessionicausaliByFkAutConccausCess().getId().getCodice()));
	    autorizzazione.setConcessionicausaliByFkAutConccausCess(concessionicausaliCess);
	}
	this.valorizzaInfoAggiuntive(autorizzazione, concessionicausaliAcq, concessionicausaliCess);
    }

    public void valorizzaInfoAggiuntive(Autorizzazioni autorizzazione, Concessionicausali causaleAcquisizione, Concessionicausali causaleCessazione) {

	autorizzazione.setConcessionicausaliByFkAutConccausAcq(causaleAcquisizione);
	autorizzazione.setConcessionicausaliByFkAutConccausCess(causaleCessazione);
	if (BooleanUtils.isTrue(autorizzazione.getFlagAttiva())) {
	    autorizzazione.setDataCessazione(null);
	    autorizzazione.setConcessionicausaliByFkAutConccausCess(null);
	}
    }

    @Override
    public void updateConcessione(AutorizzazioniConcessioni entity, Integer codiceIstanza) {

	Autorizzazioni autorizzazioneDaAggiornare = entity.getAutorizzazioniByFkAutconcAutatt();
	Autorizzazioni autorizzazioneOriginale = this.autorizzazioniDAO.findById(autorizzazioneDaAggiornare.getId());
	//verifico se la concessione è in sola lettura ( perchè, ad esempio, è un passaggio intermedio di una catena di subenti )
	this.checkAutorizzazioneIsReadonly(codiceIstanza, autorizzazioneDaAggiornare);
	//verifico se è possibile aggiornare la concessione ( intesa come dati della concessione e autorizzazione )
	this.validateEntityOnUpdate(entity, autorizzazioneDaAggiornare);
	//validazione della data di affitto
	this.validateDataFineAffitto(autorizzazioneDaAggiornare);
	//aggiorno il progressivo sul registro
	this.aggiornaProgressivoRegistro(autorizzazioneOriginale, autorizzazioneDaAggiornare);
	//recupero tutte le informazioni aggiuntive
	this.valorizzaInfoAggiuntive(autorizzazioneDaAggiornare);
	//aggiorno l'autorizzazione della concessione
	this.autorizzazioniDAO.update(autorizzazioneDaAggiornare);
	//aggiorno i dati delle concessioni legate all'autorizzazione
	List<AutorizzazioniConcessioni> concs = autorizzazioniConcessioniService
		.findByAutorizzazioneAttuale(autorizzazioneDaAggiornare.getId().getCodice());
	for (AutorizzazioniConcessioni concessione : concs) {
	    concessione.setAutorizzazioniByFkAutconcAutcoll(entity.getAutorizzazioniByFkAutconcAutcoll());
	    concessione.setConcessionitipi(entity.getConcessionitipi());
	    concessione.setDatascadenza(entity.getDatascadenza());
	    concessione.setStagionalea(entity.getStagionalea());
	    concessione.setStagionaleda(entity.getStagionaleda());
	    if (EntityUtils.getNestedProperty(concessione.getAutorizzazioniByFkAutconcAutcoll(), "id.codice") != null) {
		Autorizzazioni autCollegata = this.bindDomainObject(concessione.getAutorizzazioniByFkAutconcAutcoll(), PkId.class, "id.codice");
		concessione.setAutorizzazioniByFkAutconcAutcoll(autCollegata);
	    }
	    autorizzazioniConcessioniService.update(concessione);
	}
    }

    /**
     * Se il flag causali fine affitto è false setto la data di fine affitto a null in modo che non ne venga tenuto
     * conto
     * 
     * @param autorizzazioneDaAggiornare
     */
    private void validateDataFineAffitto(Autorizzazioni autorizzazioneDaAggiornare) {

	Concessionicausali concessionicausali = concessionicausaliService
		.findById(new PkId(autorizzazioneDaAggiornare.getConcessionicausaliByFkAutConccausAcq().getId().getCodice()));
	if (concessionicausali != null && !concessionicausali.isFlagCausaliAffitto()) {
	    autorizzazioneDaAggiornare.setDataFineAffitto(null);
	}
    }

    public void update(Autorizzazioni entity) {

	if (validateEntity(entity)) {
	    Autorizzazioni autorizzazione = null;
	    if (overrideUniqueConstraint()) {
		autorizzazione = this.findByNumeroAndComune(entity.getAutoriznumero(), entity.getAutorizcomune().getCodicecomune());
	    } else {
		autorizzazione = this.findAutOConcByEstremi(entity.getAutoriznumero(), entity.getAutorizdata(),
			entity.getAutorizcomune().getCodicecomune(), entity.getTipologiaregistro().getId().getCodice());
	    }
	    if (autorizzazione != null) {
		if (!EntityUtils.equals(autorizzazione.getId(), entity.getId())) {
		    this.throwValidationMessage(
			    new InvalidValue("autorizzazioni.service_error.autorizzazione_con_estremi_gia_presenti", null, null, "", null));
		}
	    }
	    if (BooleanUtils.isTrue(entity.getFlagAttiva())) {
		entity.setDataCessazione(null);
		entity.setConcessionicausaliByFkAutConccausCess(null);
	    }
	    if (entity.getConcessionicausaliByFkAutConccausAcq() != null
		    && BooleanUtils.isFalse(entity.getConcessionicausaliByFkAutConccausAcq().isFlagCausaliAffitto())) {
		entity.setDataFineAffitto(null);
	    }
	    // se il registro è da configurazione allora controllo se devo aggiornare il progressivo
	    NumerazioneEnum numerazione = NumerazioneEnum.daRegistro(entity.getTipologiaregistro());
	    if (NumerazioneEnum.DA_CONFIGURAZIONE.equals(numerazione)) {
		Autorizzazioni copia = autorizzazioniDAO.findById(entity.getId());
		String numeroAutOriginale = "";
		String numeroAutModificata = "";
		numeroAutOriginale = copia.getAutoriznumero();
		numeroAutModificata = entity.getAutoriznumero();
		if (!numeroAutModificata.equalsIgnoreCase(numeroAutOriginale)) {
		    try {
			tipologiaregistriService.scriviProgressivoRegistro(entity.getTipologiaregistro().getId().getCodice().intValue(),
				numeroAutModificata);
		    } catch (Exception e) {
			this.throwValidationMessage(
				new InvalidValue("autorizzazioni.service_error.aggiornamento_progressivo_registro_fallito", null, null, "", null));
		    }
		}
	    }
	    autorizzazioniDAO.update(entity);
	}
    }

    @Override
    public void delete(Autorizzazioni entity) {

	if (!entity.getAutorizzazioniSubentris().isEmpty()) {
	    // nel caso ci siano subentri trovo l'ultimo subentro che sarebbe il primo elemento dei
	    // subentri (il set è ordinato per DATA_CESSAZIONE desc)
	    AutorizzazioniSubentri ultimoSubentro = entity.getAutorizzazioniSubentris().iterator().next();
	    // copio i dati dal subentro all'autorizzazione che intendo cancellare
	    this.copySubentroToAutorizzazioneDTO(entity, ultimoSubentro);
	    // aggiorno l'autorizzazione con i nuovi dati
	    this.update(entity);
	    // cancello il subentro
	    autorizzazioniSubentriService.delete(ultimoSubentro);
	} else {
	    if (isDeleteAllowed(entity)) {
		childDelete(entity);
		autorizzazioniDAO.delete(entity);
	    }
	}
    }

    @Override
    public void deleteAutorizzazione(ValidaEliminazioneAutConcCommand cmd) throws OperazioneCancellazioneAutConcException {

	Autorizzazioni aut = this.findById(new PkId(cmd.getIdAutorizzazioni()));
	this.delete(aut);
	autorizzazioniDAO.flush();
	try {
	    this.eventPublisher.publishThrowOnFailure(new EventoAutorizzazioneConcessioneEliminata(cmd));
	} catch (EventAbortedException e) {
	    log.error("Errore nel subentro dell'autorizzazione/concessione " + e.getMessage(), e);
	    throw new OperazioneCancellazioneAutConcException(e);
	}
    }

    @Override
    protected void childDelete(Autorizzazioni entity) {

	//1. Metadati
	this.metadatiService.deleteByIdAutorizzazione(entity.getId().getCodice());
	List<DehorsMqIstanze> dehorsMqIstanzes = dehorsMqIstanzeService.findCessateByAutorizzazione(entity.getId().getCodice());
	for (DehorsMqIstanze _dehorsMqIstanze : dehorsMqIstanzes) {
	    dehorsMqIstanzeService.delete(_dehorsMqIstanze);
	}
	//2. Dehors
	DehorsMqIstanze dehorsMqIstanze = dehorsMqIstanzeService.findAttiveByAutorizzazione(entity.getId().getCodice());
	if (EntityUtils.getNestedProperty(dehorsMqIstanze, "id.codice") != null) {
	    dehorsMqIstanzeService.delete(dehorsMqIstanze);
	}
	List<DehorsLog> dehorsLogs = dehorsLogService.findByAutorizzazione(entity.getId().getCodice());
	for (DehorsLog dehorsLog : dehorsLogs) {
	    dehorsLogService.delete(dehorsLog);
	}
	//3. Documenti
	List<DocumentiAutorizzazione> autorizzaziones = documentiAutorizzazioneService.findByAutorizzazioni(entity.getId().getCodice(), null, null);
	if (!autorizzaziones.isEmpty()) {
	    for (DocumentiAutorizzazione documentiAutorizzazione : autorizzaziones) {
		documentiAutorizzazioneService.delete(documentiAutorizzazione);
	    }
	}
	//4. Borsellino autorizzazioni
	try {
	    eventPublisher.publishThrowOnFailure(new EventoOperazioneAutorizzazione(null, null, entity.getId().getCodice(), TipoOperazioneBorsAutEnum.CANCELLAZIONE));
	} catch (EventAbortedException e) {
	    throw new RuntimeException(e);
	}
	this.borsellinoAutorizzazioniDAO.scollegaAutorizzazione(entity.getId().getCodice());	
    }

    protected boolean isDeleteAllowed(Autorizzazioni entity) {

	//1. Verifico le presenze
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	if (!entity.getMercatipresenzeDsForFkMercpresdautconcAut().isEmpty()) {
	    ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATIPRESENZE_D", null));
	}
	//2. Verifico se autorizzazione usata per la spunta
	List<SpuntistiMercati> spuntistiMercatis = spuntistiMercatiService.findByAutorizzazioni(entity.getId().getCodice());
	if (!spuntistiMercatis.isEmpty()) {
	    ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "SPUNTISTI_MERCATI", null));
	}
	//3. Verifco la presenza di movimentazioni nel borsellino
	if (this.borsellinoMovimentiDAO.isAutorizzazioneMovimentata(entity.getId().getCodice())) {
	    ivs.add(new InvalidValue("errors.autorizzazioni.cancellazione.borsellino.movimenti", null, null, "BORSELLINO_MOVIMENTI", null));
	}
	if (!ivs.isEmpty()) {
	    this.throwValidationMessages(ivs);
	}
	return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Autorizzazioni> findByIstanzaRegistro(Istanze istanze, Integer codiceregistro) {

	return autorizzazioniDAO.findByIstanzaRegistro(istanze, codiceregistro);
    }

    public List<Autorizzazioni> findByIstanzaRegistro(Integer codIstanze, Integer codiceregistro) {

	return autorizzazioniDAO.findByIstanzaRegistro(codIstanze, codiceregistro);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Autorizzazioni> findByIstanzaMovimento(Istanze istanze, Movimenti movimento) {

	return autorizzazioniDAO.findByIstanzaMovimento(istanze, movimento);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Autorizzazioni> findByAnagrafe(Integer codiceAnagrafe) {

	return autorizzazioniDAO.findByAnagrafe(codiceAnagrafe);
    }

    @Override
    public Autorizzazioni findAutOConcAttivaByEstremi(String autoriznumero, Date autorizdata, String codicecomune, Integer codiceregistro) {

	log.debug("findAutOConcAttivaByEstremi");
	Autorizzazioni aut = autorizzazioniDAO.findAutOConcAttivaByEstremi(autoriznumero, autorizdata, codicecomune, codiceregistro);
	if (aut == null) {
	    AutorizzazioniSubentri autSub = autorizzazioniSubentriService.findAutSubOConcSubByEstremi(autoriznumero, autorizdata, codicecomune,
		    codiceregistro);
	    if (autSub != null) {
		log.debug("aut trovata in AUTORIZZAZIONI_SUBENTRI.");
		// la restituisco solo se attiva.
		if (BooleanUtils.isTrue(autSub.getAutorizzazioni().getFlagAttiva())) {
		    aut = autSub.getAutorizzazioni();
		}
	    }
	}
	return aut;
    }

    @Override
    public EstremiAutDTO populateEstremiByIstanzaDyn2Dati(Integer codiceIstanza) {

	log.debug("populateEstremiByIstanzaDyn2Dati(). codice:{}", codiceIstanza);
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findConfigurazione();
	if (mercatiConfigurazione == null) {
	    log.error("Manca la configurazione dei mercati per il software:{}", ORMHelper.getSoftware());
	    throw new RuntimeException("Configurazione della manifestazione non trovata.");
	}
	EstremiAutDTO autorizzazioni = new EstremiAutDTO();
	Dyn2Campi dynNumAut = mercatiConfigurazione.getDyn2CampiByFkMerconfDyn2campiNa();
	Dyn2Campi dynDataAut = mercatiConfigurazione.getDyn2CampiByFkMerconfDyn2campiDa();
	Dyn2Campi dynComuneAut = mercatiConfigurazione.getDyn2CampiByFkMerconfDyn2campiCa();
	Dyn2Campi dynCodiceRegistroAut = mercatiConfigurazione.getDyn2CampiByFkMerconfDyn2campiCodregaut();
	if (dynNumAut == null || dynDataAut == null || dynComuneAut == null || dynCodiceRegistroAut == null) {
	    log.error("Nella configurazione dei mercati per il software:{} mancano alcuni campi dinamici dell'autorizzazione",
		    ORMHelper.getSoftware());
	    throw new RuntimeException("Nella configurazione della manifestazione mancano alcuni campi dinamici dell'autorizzazione.");
	}
	boolean numAutOk = false;
	boolean dataAutOk = false;
	boolean comAutOk = false;
	boolean regAutOk = false;
	log.debug("id dyn2campi num aut della conf: {}", dynNumAut.getId().getCodice());
	Istanzedyn2datiId istanzedyn2datiIdNumAut = new Istanzedyn2datiId();
	istanzedyn2datiIdNumAut.setCodiceistanza(codiceIstanza);
	istanzedyn2datiIdNumAut.setFkD2cId(dynNumAut.getId().getCodice());
	istanzedyn2datiIdNumAut.setIndice(0);
	istanzedyn2datiIdNumAut.setIndiceMolteplicita(0);
	String istanzedyn2datiNumAut = istanzedyn2datiService.findValoreById(istanzedyn2datiIdNumAut);
	if (StringUtils.isNotBlank(istanzedyn2datiNumAut)) {
	    log.debug("INSERITO NUM AUT IN AUT: num={}", istanzedyn2datiNumAut);
	    autorizzazioni.setAutNumero(istanzedyn2datiNumAut);
	    numAutOk = true;
	}
	log.debug("id dyn2campi data aut della conf: {}", dynDataAut.getId().getCodice());
	Istanzedyn2datiId istanzedyn2datiIdDataAut = new Istanzedyn2datiId();
	istanzedyn2datiIdDataAut.setCodiceistanza(codiceIstanza);
	istanzedyn2datiIdDataAut.setFkD2cId(dynDataAut.getId().getCodice());
	istanzedyn2datiIdDataAut.setIndice(0);
	istanzedyn2datiIdDataAut.setIndiceMolteplicita(0);
	String istanzedyn2datiDataAut = istanzedyn2datiService.findValoreById(istanzedyn2datiIdDataAut);
	if (StringUtils.isNotBlank(istanzedyn2datiDataAut)) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	    Date dataAut = null;
	    try {
		dataAut = dateFormat.parse(istanzedyn2datiDataAut);
		log.debug("INSERITA DATA IN AUT: data={}", istanzedyn2datiDataAut);
		autorizzazioni.setAutData(dataAut);
		dataAutOk = true;
	    } catch (ParseException e) {
		log.error("Errore nel parsing della data in istanzedyn2dati: {}. errore: {}", istanzedyn2datiDataAut, e.getMessage());
	    }
	}
	log.debug("id dyn2campi comune aut della conf: {}", dynComuneAut.getId().getCodice());
	Istanzedyn2datiId istanzedyn2datiIdComAut = new Istanzedyn2datiId();
	istanzedyn2datiIdComAut.setCodiceistanza(codiceIstanza);
	istanzedyn2datiIdComAut.setFkD2cId(dynComuneAut.getId().getCodice());
	istanzedyn2datiIdComAut.setIndice(0);
	istanzedyn2datiIdComAut.setIndiceMolteplicita(0);
	String istanzedyn2datiComAut = istanzedyn2datiService.findValoreById(istanzedyn2datiIdComAut);
	if (StringUtils.isNotBlank(istanzedyn2datiComAut)) {
	    log.debug("INSERITO COMUNE IN AUT. codiceComune={}", istanzedyn2datiComAut);
	    autorizzazioni.setAutCodiceComune(istanzedyn2datiComAut);
	    comAutOk = true;
	}
	log.debug("id dyn2campi cod reg della conf: {}", dynCodiceRegistroAut.getId().getCodice());
	Istanzedyn2datiId istanzedyn2datiIdRegAut = new Istanzedyn2datiId();
	istanzedyn2datiIdRegAut.setCodiceistanza(codiceIstanza);
	istanzedyn2datiIdRegAut.setFkD2cId(dynCodiceRegistroAut.getId().getCodice());
	istanzedyn2datiIdRegAut.setIndice(0);
	istanzedyn2datiIdRegAut.setIndiceMolteplicita(0);
	String istanzedyn2datiRegAut = istanzedyn2datiService.findValoreById(istanzedyn2datiIdRegAut);
	if (StringUtils.isNotBlank(istanzedyn2datiRegAut)) {
	    Integer _cod = null;
	    try {
		_cod = Integer.parseInt(istanzedyn2datiRegAut);
	    } catch (NumberFormatException e) {
		log.error("Errore nel parsing del cod reg aut: {}. errore: {}", istanzedyn2datiRegAut, e.getMessage());
	    }
	    if (_cod != null) {
		log.debug("INSERITO REG IN AUT. id={}", _cod);
		autorizzazioni.setAutTipologiaRegistro(_cod);
		regAutOk = true;
	    }
	}
	// obbligatorio numaut,dataaut,comuneaut e registro
	if (numAutOk && dataAutOk && comAutOk && regAutOk) {
	    return autorizzazioni;
	}
	return null;
    }

    @Override
    public Autorizzazioni populateEstremiByIstanzaDyn2Dati(Istanze istanza, MercatiConfigurazione mercatiConfigurazione) {

	log.debug("populateEstremiByIstanzaDyn2Dati(). codice:{}, numero:{}", istanza.getId().getCodice(), istanza.getNumeroistanza());
	Autorizzazioni autorizzazioni = new Autorizzazioni();
	// Set<Istanzedyn2dati> istanzedyn2datis = istanza.getIstanzedyn2datis();
	Dyn2Campi dynNumAut = mercatiConfigurazione.getDyn2CampiByFkMerconfDyn2campiNa();
	Dyn2Campi dynDataAut = mercatiConfigurazione.getDyn2CampiByFkMerconfDyn2campiDa();
	Dyn2Campi dynComuneAut = mercatiConfigurazione.getDyn2CampiByFkMerconfDyn2campiCa();
	Dyn2Campi dynCodiceRegistroAut = mercatiConfigurazione.getDyn2CampiByFkMerconfDyn2campiCodregaut();
	if (dynNumAut == null || dynDataAut == null || dynComuneAut == null || dynCodiceRegistroAut == null) {
	    log.error("Nella configurazione dei mercati per il software:{} mancano alcuni campi dinamici dell'autorizzazione",
		    ORMHelper.getSoftware());
	    throw new RuntimeException("Nella configurazione della manifestazione mancano alcuni campi dinamici dell'autorizzazione.");
	}
	boolean numAutOk = false;
	boolean dataAutOk = false;
	boolean comAutOk = false;
	boolean regAutOk = false;
	log.debug("id dyn2campi num aut della conf: {}", dynNumAut.getId().getCodice());
	Istanzedyn2datiId istanzedyn2datiIdNumAut = new Istanzedyn2datiId();
	istanzedyn2datiIdNumAut.setCodiceistanza(istanza.getId().getCodice());
	istanzedyn2datiIdNumAut.setFkD2cId(dynNumAut.getId().getCodice());
	istanzedyn2datiIdNumAut.setIndice(0);
	istanzedyn2datiIdNumAut.setIndiceMolteplicita(0);
	Istanzedyn2dati istanzedyn2datiNumAut = istanzedyn2datiService.findById(istanzedyn2datiIdNumAut);
	if (istanzedyn2datiNumAut != null) {
	    if (istanzedyn2datiNumAut.getValore() != null) {
		log.debug("INSERITO NUM AUT IN AUT: num={}", istanzedyn2datiNumAut.getValore());
		autorizzazioni.setAutoriznumero(istanzedyn2datiNumAut.getValore());
		numAutOk = true;
	    }
	}
	log.debug("id dyn2campi data aut della conf: {}", dynDataAut.getId().getCodice());
	Istanzedyn2datiId istanzedyn2datiIdDataAut = new Istanzedyn2datiId();
	istanzedyn2datiIdDataAut.setCodiceistanza(istanza.getId().getCodice());
	istanzedyn2datiIdDataAut.setFkD2cId(dynDataAut.getId().getCodice());
	istanzedyn2datiIdDataAut.setIndice(0);
	istanzedyn2datiIdDataAut.setIndiceMolteplicita(0);
	Istanzedyn2dati istanzedyn2datiDataAut = istanzedyn2datiService.findById(istanzedyn2datiIdDataAut);
	if (istanzedyn2datiDataAut != null) {
	    if (istanzedyn2datiDataAut.getValore() != null) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date dataAut = null;
		try {
		    dataAut = dateFormat.parse(istanzedyn2datiDataAut.getValore());
		    log.debug("INSERITA DATA IN AUT: data={}", istanzedyn2datiDataAut.getValore());
		    autorizzazioni.setAutorizdata(dataAut);
		    autorizzazioni.setDataRilascio(dataAut);
		    dataAutOk = true;
		} catch (ParseException e) {
		    log.error("Errore nel parsing della data in istanzedyn2dati: {}. errore: {}", istanzedyn2datiDataAut.getValore(), e.getMessage());
		}
	    }
	}
	log.debug("id dyn2campi comune aut della conf: {}", dynComuneAut.getId().getCodice());
	Istanzedyn2datiId istanzedyn2datiIdComAut = new Istanzedyn2datiId();
	istanzedyn2datiIdComAut.setCodiceistanza(istanza.getId().getCodice());
	istanzedyn2datiIdComAut.setFkD2cId(dynComuneAut.getId().getCodice());
	istanzedyn2datiIdComAut.setIndice(0);
	istanzedyn2datiIdComAut.setIndiceMolteplicita(0);
	Istanzedyn2dati istanzedyn2datiComAut = istanzedyn2datiService.findById(istanzedyn2datiIdComAut);
	if (istanzedyn2datiComAut != null) {
	    if (istanzedyn2datiComAut.getValore() != null) {
		log.debug("INSERITO COMUNE IN AUT. id={}", istanzedyn2datiComAut.getValore());
		VwEntilocali comune = new VwEntilocali();
		comune.setCodicecomune(istanzedyn2datiComAut.getValore());
		autorizzazioni.setAutorizcomune(comune);
		comAutOk = true;
	    }
	}
	log.debug("id dyn2campi cod reg della conf: {}", dynCodiceRegistroAut.getId().getCodice());
	Istanzedyn2datiId istanzedyn2datiIdRegAut = new Istanzedyn2datiId();
	istanzedyn2datiIdRegAut.setCodiceistanza(istanza.getId().getCodice());
	istanzedyn2datiIdRegAut.setFkD2cId(dynCodiceRegistroAut.getId().getCodice());
	istanzedyn2datiIdRegAut.setIndice(0);
	istanzedyn2datiIdRegAut.setIndiceMolteplicita(0);
	Istanzedyn2dati istanzedyn2datiRegAut = istanzedyn2datiService.findById(istanzedyn2datiIdRegAut);
	if (istanzedyn2datiRegAut != null) {
	    String codRegAut = istanzedyn2datiRegAut.getValore();
	    if (codRegAut != null) {
		Integer _cod = null;
		try {
		    _cod = Integer.parseInt(codRegAut);
		} catch (NumberFormatException e) {
		    log.error("Errore nel parsing del cod reg aut: {}. errore: {}", codRegAut, e.getMessage());
		}
		if (_cod != null) {
		    Tipologiaregistri reg = new Tipologiaregistri();
		    reg.getId().setCodice(_cod);
		    if (reg != null) {
			log.debug("INSERITO REG IN AUT. id={}", reg.getId().getCodice());
			autorizzazioni.setTipologiaregistro(reg);
			regAutOk = true;
		    }
		}
	    }
	}
	// obbligatorio numaut,dataaut,comuneaut e registro
	if (numAutOk && dataAutOk && comAutOk && regAutOk) {
	    return autorizzazioni;
	}
	return null;
    }

    @Override
    public List<Autorizzazioni> findByFilter(AutorizzazioniFilter filter, Integer firstResult, Integer maxResult) {

	FilterTable filterTable = autorizzazioniFilterToFilterTable(filter);
	return autorizzazioniDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    private FilterTable autorizzazioniFilterToFilterTable(AutorizzazioniFilter filter) {

	// Implementare il filtro per autorizzazioni filter passato
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (StringUtils.isNotBlank(filter.getAutoriznumero())) {
	    fr.addFilterField(FilterUtils.equals("autoriznumero", filter.getAutoriznumero(), String.class));
	}
	if (filter.getDallaData() != null) {
	    fr.addFilterField(FilterUtils.greaterEqual("autorizdata", filter.getDallaData(), Date.class));
	}
	if (filter.getAllaData() != null) {
	    fr.addFilterField(FilterUtils.smallerEqual("autorizdata", filter.getAllaData(), Date.class));
	}
	if (filter.getDallaDataScadenza() != null) {
	    fr.addFilterField(FilterUtils.greaterEqual("datascadenza", filter.getDallaDataScadenza(), Date.class));
	}
	if (filter.getAllaDataScadenza() != null) {
	    fr.addFilterField(FilterUtils.smallerEqual("datascadenza", filter.getAllaDataScadenza(), Date.class));
	}
	if (filter.getAutorizcomune() != null && StringUtils.isNotBlank(filter.getAutorizcomune().getCodicecomune())) {
	    fr.addFilterField(FilterUtils.equals("codicecomune", filter.getAutorizcomune().getCodicecomune(), "autorizcomune", String.class));
	}
	if (filter.getTipologiaregistro() != null && filter.getTipologiaregistro().getId() != null
		&& filter.getTipologiaregistro().getId().getCodice() != null) {
	    fr.addFilterField(FilterUtils.equals("tipologiaregistroId", filter.getTipologiaregistro().getId().getCodice(), Integer.class));
	}
	if (filter.getAnagrafe() != null && filter.getAnagrafe().getId() != null && filter.getAnagrafe().getId().getCodice() != null) {
	    FilterRestriction anagrafiche = new FilterRestriction();
	    anagrafiche.setAndOrRestriction(AndOrRestriction.OR);
	    anagrafiche.addFilterField(FilterUtils.equals("anagrafeId", filter.getAnagrafe().getId().getCodice(), Integer.class));
	    anagrafiche.addFilterField(FilterUtils.equals("occupanteId", filter.getAnagrafe().getId().getCodice(), Integer.class));
	    ft.addRestriction(anagrafiche);
	} else {
	    // det.createAlias("anagrafe", "_anagrafe", Criteria.LEFT_JOIN);
	    // det.addOrder(Order.asc("_anagrafe.nominativo"));
	}
	if (!filter.getIncludiCessate()) {
	    fr.addFilterField(FilterUtils.equals("flagAttiva", true, Boolean.class));
	}
	if (filter.isEscludiConcessioni()) {
	    //	    det.createCriteria("autorizzazioniConcessionisForFkAutconcAutatt", "aut_conc", DetachedCriteria.LEFT_JOIN);
	    //	    det.add(Restrictions.isNull("aut_conc.id.codice"));
	    fr.addFilterField(FilterUtils.isNull("id.codice", "autorizzazioniConcessionisForFkAutconcAutatt"));
	}
	//	////----------------------------------------------------  START ------------------------------------------------------------------///
	//	// FILTRO DATI DELLA MANIFESTAZIONE (PRESENTI SOLO SE PER IDCOMUNE E SOFTWARE IN USO E' ATTIVA LA CONFIGURAZIONE DELLE MANIFESTAZIONI)
	//	// Se è impostato almeno uno dei tre filtri creo la condizione di join
	//	if (EntityUtils.getNestedProperty(filter.getMercati(), "id.codice") != null
	//		|| EntityUtils.getNestedProperty(filter.getMercatiUso(), "id.codice") != null
	//		|| EntityUtils.getNestedProperty(filter.getMercatiD(), "id.codice") != null) {
	//	    det.createCriteria("autorizzazioniConcessionisForFkAutconcAutatt", "_autorizzazioniConcessionisForFkAutconcAutatt",
	//		    DetachedCriteria.LEFT_JOIN);
	//	}
	if (EntityUtils.getNestedProperty(filter.getMercati(), "id.codice") != null) {
	    //det.add(Restrictions.eq("_autorizzazioniConcessionisForFkAutconcAutatt.mercatiId", filter.getMercati().getId().getCodice()));
	    fr.addFilterField(FilterUtils.equals("id.codice", filter.getMercati().getId().getCodice(),
		    "autorizzazioniConcessionisForFkAutconcAutatt.mercati", Integer.class));
	}
	if (EntityUtils.getNestedProperty(filter.getMercatiUso(), "id.codice") != null) {
	    // det.add(Restrictions.eq("_autorizzazioniConcessionisForFkAutconcAutatt.mercatiUsoId", filter.getMercatiUso().getId().getCodice()));
	    fr.addFilterField(FilterUtils.equals("id.codice", filter.getMercatiUso().getId().getCodice(),
		    "autorizzazioniConcessionisForFkAutconcAutatt.mercatiUso", Integer.class));
	}
	if (EntityUtils.getNestedProperty(filter.getMercatiD(), "id.codice") != null) {
	    //det.add(Restrictions.eq("_autorizzazioniConcessionisForFkAutconcAutatt.mercatiDId", filter.getMercatiD().getId().getCodice()));
	    fr.addFilterField(FilterUtils.equals("id.codice", filter.getMercatiD().getId().getCodice(),
		    "autorizzazioniConcessionisForFkAutconcAutatt.mercatiD", Integer.class));
	}
	if (filter.isEscludiLeAutorizzazioniCollegate()) {
	    // devo escludere le autorizzazioni che sono collegate ad una concessione "DOPPIO ATTO"
	    fr.addFilterField(FilterUtils.isEmpty("autorizzazioniConcessionisForFkAutconcAutcoll"));
	}
	if (filter.isSoloFlagManifestazioni()) {
	    fr.addFilterField(FilterUtils.equals("flagManifestazioni", Boolean.TRUE, "tipologiaregistro", Boolean.class));
	}
	ft.addRestriction(fr);
	//	////----------------------------------------------------  END ------------------------------------------------------------------///
	// criterio per includere le aut non collegate all'istanza (quindi collegate all'anagrafica)
	//	FilterRestriction frSoftware = new FilterRestriction();
	//	frSoftware.setAndOrRestriction(AndOrRestriction.OR);
	//	frSoftware.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "istanza.software", String.class));
	//	frSoftware.addFilterField(FilterUtils.isNull("id.codice", "istanza"));
	//	ft.addRestriction(frSoftware);
	FilterRestriction frSoftware = new FilterRestriction();
	frSoftware.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "tipologiaregistro.software", String.class));
	ft.addRestriction(frSoftware);
	FilterRestriction frCodiceIstanzaCrit = new FilterRestriction();
	if (filter.getCodiceIstanzaDaEscludere() != null) {
	    frCodiceIstanzaCrit.setAndOrRestriction(AndOrRestriction.OR);
	    frCodiceIstanzaCrit.addFilterField(FilterUtils.isNull("istanzeId"));
	    frCodiceIstanzaCrit.addFilterField(FilterUtils.notEquals("istanzeId", filter.getCodiceIstanzaDaEscludere(), Integer.class));
	    ft.addRestriction(frCodiceIstanzaCrit);
	}
	// GESTIONI FILTRI PER DATI DELL'ISTANZA
	if (StringUtils.isNotBlank(filter.getIstanzeFilter().getNumeroistanza())) {
	    fr.addFilterField(FilterUtils.equals("numeroistanza", filter.getIstanzeFilter().getNumeroistanza(), "istanza", String.class));
	}
	if (filter.getIstanzeFilter().getDallaData() != null) {
	    fr.addFilterField(FilterUtils.greaterEqual("data", filter.getIstanzeFilter().getDallaData(), "istanza", Date.class));
	}
	if (filter.getIstanzeFilter().getAllaData() != null) {
	    fr.addFilterField(FilterUtils.smallerEqual("data", filter.getIstanzeFilter().getAllaData(), "istanza", Date.class));
	}
	if (EntityUtils.getNestedProperty(filter.getIstanzeFilter().getRichiedente(), "id.codice") != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", filter.getIstanzeFilter().getAllaData(), "istanza.richiedente", Date.class));
	}
	if (EntityUtils.getNestedProperty(filter.getIstanzeFilter().getAlberoproc(), "id.codice") != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", filter.getIstanzeFilter().getAlberoproc().getId().getCodice(), "istanza.alberoproc",
		    Integer.class));
	}
	if (EntityUtils.getNestedProperty(filter.getIstanzeFilter().getProcedura(), "id.codice") != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", filter.getIstanzeFilter().getProcedura().getId().getCodice(), "istanza.procedura",
		    Integer.class));
	}
	if (EntityUtils.getNestedProperty(filter.getIstanzeFilter().getIstanzestradario().getStradario(), "id.codice") != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", filter.getIstanzeFilter().getIstanzestradario().getStradario().getId().getCodice(),
		    "istanza.istanzestradarios.stradario", Integer.class));
	}
	if (StringUtils.isNotBlank(filter.getIstanzeFilter().getIstanzestradario().getCap())) {
	    fr.addFilterField(
		    FilterUtils.equals("cap", filter.getIstanzeFilter().getIstanzestradario().getCap(), "istanza.istanzestradarios", String.class));
	}
	if (filter.getIstanzeFilter().getComune() != null && StringUtils.isNotBlank(filter.getIstanzeFilter().getComune().getCodicecomune())) {
	    fr.addFilterField(
		    FilterUtils.equals("codicecomune", filter.getIstanzeFilter().getComune().getCodicecomune(), "istanza.comune", String.class));
	}
	//	// ///ORDINAMENTI
	// Controllo che siano stati passati ii campi di ordinamento
	if (StringUtils.isNotBlank(filter.getOrderBy())) {
	    // I campi di ordinamento vengono passati dal form nella forma field1,filed2..fieldN e indicano l'ordine 
	    // dei campi per cui si deve ordinare
	    // Faccio lo split per recuperare ogni singolo campo    
	    String[] field = filter.getOrderBy().split(",");
	    String[] padNumeroautorizzazione = new String[] { "20", "' '" };
	    String[] padNumeroistanza = new String[] { "20", "' '" };
	    // Ciclo tutti i campi in modo per cui devo ordinare in modo che se verranno aggiunti successivamente dei nuovi
	    // basterà inserire la nuova condizione di ordinamento
	    for (int i = 0; i < field.length; i++) {
		if (field[i].equals("autorizdata")) {
		    ft.addOrder(FilterUtils.order(field[i], filter.getOrderAscDesc()));
		}
		if (field[i].equalsIgnoreCase("tipologiaregistro.trDescrizione")) {
		    ft.addOrder(FilterUtils.order("trDescrizione", "tipologiaregistro", filter.getOrderAscDesc()));
		}
		if (field[i].equalsIgnoreCase("istanza.data")) {
		    ft.addOrder(FilterUtils.order("data", "istanza", filter.getOrderAscDesc()));
		}
		if (field[i].equalsIgnoreCase("istanza.richiedente.nominativo")) {
		    ft.addOrder(FilterUtils.order("nominativo", "istanza.richiedente", filter.getOrderAscDesc()));
		    ft.addOrder(FilterUtils.order("nome", "istanza.richiedente", filter.getOrderAscDesc()));
		}
		if (field[i].equals("istanza.numeroistanza")) {
		    switch (filter.getOrderAscDesc()) {
		    case ASC:
			ft.addOrder(FilterUtils.orderAsc(field[i], "istanza", FunctionsEnum.LPAD_FUNCTION, padNumeroistanza));
			break;
		    case DESC:
			ft.addOrder(FilterUtils.orderDesc(field[i], "istanza", FunctionsEnum.LPAD_FUNCTION, padNumeroistanza));
			break;
		    default:
			ft.addOrder(FilterUtils.order(field[i], filter.getOrderAscDesc()));
			break;
		    }
		}
		if (field[i].equals("autoriznumero")) {
		    switch (filter.getOrderAscDesc()) {
		    case ASC:
			ft.addOrder(FilterUtils.orderAsc(field[i], FunctionsEnum.LPAD_FUNCTION, padNumeroautorizzazione));
			break;
		    case DESC:
			ft.addOrder(FilterUtils.orderDesc(field[i], FunctionsEnum.LPAD_FUNCTION, padNumeroautorizzazione));
			break;
		    default:
			ft.addOrder(FilterUtils.order(field[i], filter.getOrderAscDesc()));
			break;
		    }
		}
	    }
	}
	return ft;
    }

    @Override
    public AutorizzazioniConcessioni precompilaConcessione(Integer codiceIstanza) {

	AutorizzazioniConcessioni concessione = new AutorizzazioniConcessioni();
	Autorizzazioni autorizzazione = new Autorizzazioni();
	Autorizzazioni autorizzazioneCollegata = new Autorizzazioni();
	PkId idIstanza = new PkId(codiceIstanza);
	Istanze istanza = istanzeService.findById(idIstanza);
	autorizzazioneCollegata.setIstanza(istanza);
	autorizzazione.setIstanza(istanza);
	Anagrafe titolare = anagrafeService.findById(new PkId(istanza.getTitolareLegaleORichiedente().getId().getCodice()));
	autorizzazione.setAnagrafe(titolare);
	autorizzazione.setOccupante(titolare);
	autorizzazioneCollegata.setAnagrafe(titolare);
	autorizzazioneCollegata.setOccupante(titolare);
	Mercati mercato = null;
	MercatiUso mercatoUso = null;
	if (istanza.getAlberoproc().getMercato() != null && istanza.getAlberoproc().getMercato().getId().getCodice() != null) {
	    mercato = mercatiService.findById(new PkId(istanza.getAlberoproc().getMercato().getId().getCodice()));
	    if (istanza.getAlberoproc().getMercatoUso() != null && istanza.getAlberoproc().getMercatoUso().getId().getCodice() != null) {
		mercatoUso = mercatiUsoService.findById(new PkId(istanza.getAlberoproc().getMercatoUso().getId().getCodice()));
	    }
	    concessione.setMercati(mercato);
	    concessione.setMercatiUso(mercatoUso);
	}
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findById(new MercatiConfigurazioneId());
	Concessionitipi concessionitipi = null;
	Concessionicausali concessionicausali = null;
	Tipologiaregistri registroConcessione = null;
	Tipologiaregistri registroAutorizzazione = null;
	if (null != mercatiConfigurazione) {
	    if (mercatiConfigurazione.getTipoConcessione() != null
		    && StringUtils.isNotBlank(mercatiConfigurazione.getTipoConcessione().getTipoconcessione())) {
		concessionitipi = concessionitipiService.findById(mercatiConfigurazione.getTipoConcessione().getTipoconcessione());
	    }
	    if (null != mercatiConfigurazione.getRegistroConcessioni()
		    && mercatiConfigurazione.getRegistroConcessioni().getId().getCodice() != null) {
		registroConcessione = tipologiaregistriService.findById(new PkId(mercatiConfigurazione.getRegistroConcessioni().getId().getCodice()));
		if (registroConcessione != null) {
		    if (null != registroConcessione.getTrFlagprotocollo() && registroConcessione.getTrFlagprotocollo().booleanValue() == false) {
			autorizzazione.setAutoriznumero(registroConcessione.getTrProgressivo());
		    }
		}
	    }
	    if (null != mercatiConfigurazione.getCausaleConcessione() && mercatiConfigurazione.getCausaleConcessione().getId().getCodice() != null) {
		concessionicausali = concessionicausaliService.findById(new PkId(mercatiConfigurazione.getCausaleConcessione().getId().getCodice()));
	    }
	    if (mercatiConfigurazione.getRegistroAutorizzazioni() != null
		    && mercatiConfigurazione.getRegistroAutorizzazioni().getId().getCodice() != null) {
		registroAutorizzazione = tipologiaregistriService
			.findById(new PkId(mercatiConfigurazione.getRegistroAutorizzazioni().getId().getCodice()));
		if (null != registroAutorizzazione) {
		    if (null != registroAutorizzazione.getTrFlagprotocollo()
			    && registroAutorizzazione.getTrFlagprotocollo().booleanValue() == false) {
			autorizzazioneCollegata.setAutoriznumero(registroAutorizzazione.getTrProgressivo());
		    }
		}
	    }
	    if (StringUtils.isNotBlank(mercatiConfigurazione.getDurataTipo())) {
		if (mercatiConfigurazione.getDurata() != null) {
		    String dt = mercatiConfigurazione.getDurataTipo();
		    Short durata = mercatiConfigurazione.getDurata();
		    if (durata.shortValue() > 0) {
			Calendar oggi = Calendar.getInstance();
			int fieldAmount = -100;
			if (dt.equalsIgnoreCase("Y")) {
			    fieldAmount = Calendar.YEAR;
			} else if (dt.equalsIgnoreCase("M")) {
			    fieldAmount = Calendar.MONTH;
			} else if (dt.equalsIgnoreCase("D")) {
			    fieldAmount = Calendar.DATE;
			}
			if (fieldAmount > 0) {
			    oggi.add(fieldAmount, durata.intValue());
			    concessione.setDatascadenza(oggi.getTime());
			    autorizzazione.setDatascadenza(concessione.getDatascadenza());
			}
		    }
		}
	    }
	}
	autorizzazione.setTipologiaregistro(registroConcessione);
	autorizzazioneCollegata.setTipologiaregistro(registroAutorizzazione);
	if (concessionitipi != null) {
	    concessione.setConcessionitipi(concessionitipi);
	}
	autorizzazione.setConcessionicausaliByFkAutConccausAcq(concessionicausali);
	autorizzazioneCollegata.setConcessionicausaliByFkAutConccausAcq(concessionicausali);
	autorizzazione.setAutorizdata(Calendar.getInstance().getTime());
	autorizzazioneCollegata.setAutorizdata(Calendar.getInstance().getTime());
	autorizzazione.setDataRilascio(Calendar.getInstance().getTime());
	autorizzazioneCollegata.setDataRilascio(Calendar.getInstance().getTime());
	autorizzazione.setAutorizdataregistr(Calendar.getInstance().getTime());
	autorizzazioneCollegata.setAutorizdataregistr(Calendar.getInstance().getTime());
	Responsabili operatore = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	autorizzazione.setAutorizresponsabile(operatore.getResponsabile());
	autorizzazioneCollegata.setAutorizresponsabile(operatore.getResponsabile());
	autorizzazione.setFlagAttiva(true);
	autorizzazioneCollegata.setFlagAttiva(true);
	Comuni comune = istanza.getComune();
	VwEntilocali c = vwEntilocaliService.findById(comune.getCodicecomune());
	autorizzazione.setAutorizcomune(c);
	autorizzazioneCollegata.setAutorizcomune(c);
	concessione.setAutorizzazioniByFkAutconcAutcoll(autorizzazioneCollegata);
	Set<AutorizzazioniConcessioni> concessionis = new HashSet<AutorizzazioniConcessioni>(0);
	concessionis.add(concessione);
	autorizzazione.setAutorizzazioniConcessionisForFkAutconcAutatt(concessionis);
	concessione.setAutorizzazioniByFkAutconcAutatt(autorizzazione);
	concessione.setAutorizzazioniByFkAutconcAutcoll(autorizzazioneCollegata);
	return concessione;
    }

    @Override
    public Set<Integer> insertSubentri(AutorizzazioniSubentriCommand autorizzazioniSubentriCommand) throws OperazioniSubentriException {

	ENUM_COPIA_ONERI copiaONERI = ENUM_COPIA_ONERI.valueOf(autorizzazioniSubentriCommand.getSubentriComportamentoOneri());
	Set<AutorizzazioniHelper> auts = autorizzazioniSubentriCommand.getListAutDaSubentrare();
	Istanze subentro = autorizzazioniSubentriCommand.getIstanzaDiSubentro();
	Concessionicausali causaleAcquisizione = concessionicausaliService
		.findById(new PkId(autorizzazioniSubentriCommand.getCausaleAcquisizione().getId().getCodice()));
	Concessionicausali causaleCessazione = autorizzazioniSubentriCommand.getCausaleCessazione();
	Date dataCessazione = autorizzazioniSubentriCommand.getDataCessazione();
	Set<Integer> listaCodiciSubentri = new LinkedHashSet<Integer>();
	Istanze i = istanzeService.findById(new PkId(autorizzazioniSubentriCommand.getIstanzaDiSubentro().getId().getCodice()));
	String codiceComune = i.getComune().getCodicecomune();
	Integer codiceResponsabile = ((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails()).getId().getCodice();
	for (AutorizzazioniHelper autH : auts) {
	    if (autH.isDaSubentrare()) {
		validateInsertSubentro(autH);
		Autorizzazioni aut = autH.getAutorizzazione();
		aut = this.findById(aut.getId());
		// inserisco l'aut in subentri
		AutorizzazioniSubentri sub = getAutorizzazioneSubentro(aut, causaleCessazione, dataCessazione);
		autorizzazioniSubentriService.insert(sub);
		// aggiorno l'aut attuale
		setEstremiAut(aut, autH, subentro, causaleAcquisizione, codiceComune, codiceResponsabile);
		if (BooleanUtils.isFalse(aut.getFlagAttiva())) {
		    aut.setFlagAttiva(Boolean.TRUE);
		    aut.setConcessionicausaliByFkAutConccausCess(null);
		    aut.setDataCessazione(null);
		}
		aut.setMovimenti(null);
		aut.setAnagrafe(calcolaTitolare(aut, causaleAcquisizione, i));
		aut.setOccupante(calcolaOccupante(aut, causaleAcquisizione, i));
		if (causaleAcquisizione.isFlagCausaliAffitto()) {
		    aut.setDataFineAffitto(autorizzazioniSubentriCommand.getFilter().getDataFineAffitto());
		}
		this.update(aut);
		listaCodiciSubentri.add(aut.getId().getCodice());
		// subentro l'eventuale aut collegata
		// se aut rappresenta una concessione verifico se ha un aut collegata da subentrare.
		List<AutorizzazioniConcessioni> concessionis = autorizzazioniConcessioniService.findByAutorizzazioneAttuale(aut.getId().getCodice());
		// 20190308 BOCCI MODIFICHE PIU' CONCESSIONI PER UN AUTORIZZAZIONE BEGIN
		AutorizzazioniSubentri autorizzazioniSubentriByFkAutconcAutcoll = null;
		for (AutorizzazioniConcessioni autorizzazioniConcessioni : concessionis) {
		    // autorizzazioneCollegata
		    Autorizzazioni autColl = autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutcoll();
		    if (autorizzazioniSubentriByFkAutconcAutcoll == null && autColl != null && autColl.getId() != null
			    && autColl.getId().getCodice() != null) {
			validateInsertSubentro(autH.getAutorizzazioneCollegataHelper());
			// inserisco l'aut collegata in subentri
			autorizzazioniSubentriByFkAutconcAutcoll = getAutorizzazioneSubentro(autColl, causaleCessazione, dataCessazione);
			autorizzazioniSubentriService.insert(autorizzazioniSubentriByFkAutconcAutcoll);
			// aggiorno la concessione subentrata con il riferimento all'aut collegata subentrata
			// aggiorno l'aut collegata
			setEstremiAut(autColl, autH.getAutorizzazioneCollegataHelper(), subentro, causaleAcquisizione, codiceComune,
				codiceResponsabile);
			autColl.setAnagrafe(aut.getAnagrafe()); // lo stesso della concesssione che collego)
			autColl.setOccupante(aut.getOccupante()); // lo stesso della concessione che collego
			autColl.setDataFineAffitto(aut.getDataFineAffitto());
			this.update(autColl);
			listaCodiciSubentri.add(autColl.getId().getCodice());
		    }
		    AutorizzazioniSubentriConc entity = new AutorizzazioniSubentriConc();
		    entity.setAutorizzazioniSubentri(sub);
		    entity.setAutorizzazioniByFkAutconcAutatt(aut);
		    entity.setAutorizzazioniSubentriByFkAutconcAutcoll(autorizzazioniSubentriByFkAutconcAutcoll);
		    entity.setConcessionitipi(autorizzazioniConcessioni.getConcessionitipi());
		    entity.setMercati(autorizzazioniConcessioni.getMercati());
		    entity.setMercatiUso(autorizzazioniConcessioni.getMercatiUso());
		    entity.setMercatiD(autorizzazioniConcessioni.getMercatiD());
		    entity.setStagionalea(autorizzazioniConcessioni.getStagionalea());
		    entity.setStagionaleda(autorizzazioniConcessioni.getStagionaleda());
		    autorizzazioniSubentriConcService.insert(entity);
		}
		// 20190308 BOCCI MODIFICHE PIU' CONCESSIONI PER UN AUTORIZZAZIONE END
		String tipoAut = this.tipoAutorizzazione(aut.getTipologiaregistro().getId().getCodice());
		if (StringUtils.isNotBlank(tipoAut)) {
		    childDataInsertSubentri(aut, autorizzazioniSubentriCommand.getDehorsMqIstanzeNuova(),
			    autorizzazioniSubentriCommand.getDehorsMqIstanzePrecendete(), tipoAut);
		}
		try {
		    eventPublisher.publishAndThrowOnAllSubscriberFailure(
			    new EventoSubentroEffettuato(sub.getId().getCodice(), copiaONERI, autorizzazioniSubentriCommand.getEsito()));
		} catch (EventAbortedException e) {
		    log.error("Errore nel subentro dell'autorizzazione/concessione " + e.getMessage(), e);
		    throw new OperazioniSubentriException(e);
		}
	    }
	}
	return listaCodiciSubentri;
    }

    private Anagrafe calcolaTitolare(Autorizzazioni aut, Concessionicausali causaleAcquisizione, Istanze i) {

	// .. VERIFICARE CON STEFANO MENDICHI
	// NEL CASO DI SUBENTRO CON AFFITTO L'ANAGRAFE DELL'AUTORIZZAZIONE E' QUELLA DELL'AUTORIZZAZIONE PRECEDENTE AL SUBENTRO
	// E NON DEVO MODIFICARE I DATI
	boolean riottenimento = checkCausaleRiottenimento(causaleAcquisizione); // se causale  è di riottenimento allora il titolare è il titolare dell'autorizzazione
	if (causaleAcquisizione.isFlagCausaliAffitto() || riottenimento) {
	    return aut.getAnagrafe();
	}
	return i.getTitolareLegaleORichiedente();
    }

    private Anagrafe calcolaOccupante(Autorizzazioni aut, Concessionicausali causaleAcquisizione, Istanze i) {

	if (checkCausaleRiottenimento(causaleAcquisizione)) { // se causale  è di riottenimento allora il titolare è il titolare dell'autorizzazione
	    return aut.getAnagrafe();
	}
	return i.getTitolareLegaleORichiedente();
    }

    private boolean checkCausaleRiottenimento(Concessionicausali causaleAcquisizione) {

	MercatiConfigurazione mercCfg = mercatiConfigurazioneService.findById(new MercatiConfigurazioneId());
	return (mercCfg != null && mercCfg.getCausaleAcqRiottenimento() != null && mercCfg.getCausaleAcqRiottenimento().getId() != null
		&& causaleAcquisizione.getId().getCodice().equals(mercCfg.getCausaleAcqRiottenimento().getId().getCodice()));
    }

    @Override
    public EsitoElaborazioneSubentri validaInserimentoSubentri(AutorizzazioniSubentriCommand command) throws OperazioniSubentriException {

	/////////////////////////
	// validazione input
	validazioneInputSubentri(command);
	DatiCausaliSubentroRequest dcs = new DatiCausaliSubentroRequest(command);
	EsitoElaborazioneSubentri esiti = new EsitoElaborazioneSubentri();
	for (AutorizzazioniHelper aut : command.getListAutDaSubentrare()) {
	    if (aut.isDaSubentrare()) {
		CheckSubentroRequest request = new CheckSubentroRequest(command.getIstanzaDiSubentro().getId().getCodice(),
			aut.getAutorizzazione().getId().getCodice(), dcs);
		try {
		    this.eventPublisher.publishAndThrowOnAllSubscriberFailure(new EventoCheckSubentro(request));
		} catch (EventAbortedException e) {
		    if (e.getEsito() != null) {
			esiti.addEsito(aut, e.getEsito(), this, autorizzazioniConcessioniService);
		    } else {
			log.error("Errore nella validazione dei subentri", e);
			throw new OperazioniSubentriException(e.getMessage());
		    }
		}
	    }
	}
	return esiti;
    }

    private void validazioneInputSubentri(AutorizzazioniSubentriCommand command) throws OperazioniSubentriException {

	if (StringUtils.isEmpty(command.getSubentriComportamentoOneri())) {
	    throw new OperazioniSubentriException("Comportamento oneri non specificato");
	}
	try {
	    ENUM_COPIA_ONERI.valueOf(command.getSubentriComportamentoOneri());
	} catch (Exception e) {
	    throw new OperazioniSubentriException("Comportamento copia oneri non specificato");
	}
	if (command.getListAutDaSubentrare() == null || command.getListAutDaSubentrare().isEmpty()) {
	    throw new OperazioniSubentriException("Non sono state selezionate autorizzazioni o concessioni da subentrare.");
	}
	if (command.getDataCessazione() == null) {
	    throw new OperazioniSubentriException("La data di cessazione non può essere vuota.");
	}
	boolean almenoUnaScelta = false;
	for (AutorizzazioniHelper autH : command.getListAutDaSubentrare()) {
	    if (autH.isDaSubentrare()) {
		almenoUnaScelta = true;
		break;
	    }
	}
	if (!almenoUnaScelta) {
	    throw new OperazioniSubentriException("Non ci sono record selezionati.");
	}
	//
	// controllo che per ogni concessione scelta non sia stata scelta anche l'aut collegata
	for (AutorizzazioniHelper autH : command.getListAutDaSubentrare()) {
	    if (autH.isDaSubentrare()) {
		AutorizzazioniConcessioni conc = autH.getConcessione();
		if (conc != null && conc.getId() != null && conc.getId().getCodice() != null) {
		    Autorizzazioni autColl = conc.getAutorizzazioniByFkAutconcAutcoll();
		    if (autColl != null) {
			for (AutorizzazioniHelper autH1 : command.getListAutDaSubentrare()) {
			    if (autH1.getAutorizzazione().getId().getCodice().intValue() == autColl.getId().getCodice().intValue()
				    && autH1.isDaSubentrare()) {
				throw new BusinessValidationException("Non puoi selezionare la concessione [" +
					autH.getAutorizzazione().getTransientEstremiAut() +
					"] e l'autorizzazione [" +
					autH1.getAutorizzazione().getTransientEstremiAut() +
					"] perchè sono collegate.");
			    }
			}
		    }
		}
	    }
	}
	if (command.getIstanzaDiSubentro() == null || command.getIstanzaDiSubentro().getId() == null
		|| command.getIstanzaDiSubentro().getId().getCodice() == null) {
	    throw new OperazioniSubentriException("Codice istanza Subentro non specificata");
	}
	if (command.getCausaleAcquisizione() == null || command.getCausaleAcquisizione().getId() == null
		|| command.getCausaleAcquisizione().getId().getCodice() == null) {
	    throw new OperazioniSubentriException("Causale acquisizione non specificata");
	}
	if (command.getCausaleCessazione() == null || command.getCausaleCessazione().getId() == null
		|| command.getCausaleCessazione().getId().getCodice() == null) {
	    throw new OperazioniSubentriException("Causale cessazione non specificata");
	}
	Concessionicausali acq = concessionicausaliService.findById(new PkId(command.getCausaleAcquisizione().getId().getCodice()));
	if (acq == null) {
	    throw new OperazioniSubentriException("Causale acquisizione non valida");
	}
	if (acq.isFlagCausaliAffitto() && command.getFilter().getDataFineAffitto() == null) {
	    throw new OperazioniSubentriException("La causale " +
		    acq.getDescrizione() +
		    "(" +
		    acq.getId() +
		    ") è una causale di affitto e non è stata specificata la data di fine affitto");
	}
    }

    private void childDataInsertSubentri(Autorizzazioni aut, DehorsMqIstanze dehorsMqIstanzeNuova, DehorsMqIstanze dehorsMqIstanzePrecedente,
	    String tipoAut) {

	if (tipoAut.equals(WebConstants.AUTORIZZAZIONE_DEHORS)) {
	    dehorsMqIstanzeNuova.setAutorizzazioni(aut);
	    if (EntityUtils.getNestedProperty(aut.getIstanza(), "id.codice") != null) {
		dehorsMqIstanzeNuova.setIstanze(aut.getIstanza());
	    }
	    dehorsMqIstanzeNuova.setCessata(false);
	    dehorsMqIstanzeService.insertPerSubentro(dehorsMqIstanzeNuova, dehorsMqIstanzePrecedente);
	    // cesso il record della precedente se non era cessato
	    dehorsMqIstanzePrecedente.setCessata(true);
	    dehorsMqIstanzeService.update(dehorsMqIstanzePrecedente);
	    // Introduco il log dell'operazione
	    String messLog = "Inserito rinnovo autorizzazione dehors";
	    // oggetto utilizzato per invocare il metodo populateDehorsLog
	    AutorizzazioniCommand autorizzazioniCommand = new AutorizzazioniCommand();
	    autorizzazioniCommand.setEntity(aut);
	    BigDecimal mqvariati = dehorsMqIstanzeNuova.getMqassegnati().subtract(dehorsMqIstanzePrecedente.getMqassegnati());
	    DehorsLog dehorsLog = dehorsLogService.populateDehorsLog(autorizzazioniCommand, dehorsMqIstanzeNuova.getMqassegnati(), mqvariati,
		    messLog);
	    dehorsLogService.insert(dehorsLog);
	}
    }

    private void setEstremiAut(Autorizzazioni aut, AutorizzazioniHelper autH, Istanze subentro, Concessionicausali causaleAcquisizione,
	    String codiceComune, Integer codiceResponsabile) {

	// setto gli estremi dell'aut se non nulli
	if (StringUtils.isNotBlank(autH.getAutoriznumeroSubentro())) {
	    aut.setAutoriznumero(autH.getAutoriznumeroSubentro());
	}
	if (autH.getAutorizdataSubentro() != null) {
	    aut.setAutorizdata(autH.getAutorizdataSubentro());
	}
	if (autH.getDataRilascioSubentro() != null) {
	    aut.setDataRilascio(autH.getDataRilascioSubentro());
	}
	if (autH.getAutorizdatascadenzaSubentro() != null) {
	    aut.setDatascadenza(autH.getAutorizdatascadenzaSubentro());
	}
	if (autH.getAutorizcomuneSubentro() != null && StringUtils.isNotBlank(autH.getAutorizcomuneSubentro().getCodicecomune())) {
	    VwEntilocali _comSub = vwEntilocaliService.findById(autH.getAutorizcomuneSubentro().getCodicecomune());
	    aut.setAutorizcomune(_comSub);
	}
	Tipologiaregistri registro;
	if (autH.getAutorizregistroSubentro() != null && autH.getAutorizregistroSubentro().getId() != null
		&& autH.getAutorizregistroSubentro().getId().getCodice() != null) {
	    Tipologiaregistri _tipoRegSub = tipologiaregistriService.findById(autH.getAutorizregistroSubentro().getId());
	    aut.setTipologiaregistro(_tipoRegSub);
	    registro = autH.getAutorizregistroSubentro();
	} else {
	    registro = aut.getTipologiaregistro();
	}
	if (!autH.isFlagMantieniNumero()) {
	    registro = tipologiaregistriService.findById(new PkId(registro.getId().getCodice()));
	    log.debug("setEstremiAut# Per l'autorizzazione {}, impostato il flag usa lo stesso numero {}",
		    autH.getAutorizzazione().getAutoriznumero(), autH.isFlagMantieniNumero());
	    log.debug("setEstremiAut# Calcolo numero autorizzazione da Registro o Protocollo");
	    NumerazioneEnum tipoRegConf = NumerazioneEnum.daRegistro(registro);
	    switch (tipoRegConf) {
	    case DA_CONFIGURAZIONE: {
		aut.setAutoriznumero(null); // la get prende il progressivo corretto quindi setto a null il numero
		EstremiAutorizzazione estremi = new NumerazioneFactory(configurazioneService, tipologiaregistriService, userSecurityService,
			protocollazioneService, aut).getService().get();
		aut.setAutoriznumero(estremi.getNumero());
		if (autH.getAutorizdataSubentro() != null) {
		    aut.setAutorizdata(autH.getAutorizdataSubentro());
		}
		if (autH.getAutorizdatascadenzaSubentro() != null) {
		    aut.setDatascadenza(autH.getAutorizdatascadenzaSubentro());
		}
		break;
	    }
	    case CUSTOM: {
		throw new NotImplementedException("Impossibile utilizzare il registro da numerazione esterna in quanto non prevista");
	    }
	    default:
		break;
	    }
	    // verifica del registro per la numerazione
	    EstremiAutorizzazione estremi = new NumerazioneFactory(configurazioneService, tipologiaregistriService, userSecurityService,
		    protocollazioneService, aut).getService().assegnaNumero();
	    if (StringUtils.isNotBlank(estremi.getNumero())) {
		aut.setFkidprotocollo(estremi.getIdRiferimento());
		aut.setAutoriznumero(estremi.getNumero());
	    }
	} else {
	    log.debug("setEstremiAut# Per l'autorizzazione {}, impostato il flag usa lo stesso numero {}",
		    autH.getAutorizzazione().getAutoriznumero(), autH.isFlagMantieniNumero());
	}
	// setto istanza ed anagrafe dell'aut.
	// per l'anagrafe setto il titolare legale, se nullo il richiedente
	Istanze istanzaSubentro = istanzeService.findById(subentro.getId());
	aut.setIstanza(istanzaSubentro);
	Concessionicausali causaleAcq = concessionicausaliService.findById(causaleAcquisizione.getId());
	aut.setConcessionicausaliByFkAutConccausAcq(causaleAcq);
    }

    private AutorizzazioniSubentri getAutorizzazioneSubentro(Autorizzazioni aut, Concessionicausali causaleCessazione, Date dataCessazione) {

	AutorizzazioniSubentri sub = new AutorizzazioniSubentri();
	sub.setAnagrafe(aut.getAnagrafe());
	sub.setOccupante(aut.getOccupante());
	sub.setAutorizcomune(aut.getAutorizcomune());
	sub.setAutorizdata(aut.getAutorizdata());
	sub.setAutorizdataregistr(aut.getAutorizdataregistr());
	sub.setAutoriznumero(aut.getAutoriznumero());
	sub.setAutorizresponsabile(aut.getAutorizresponsabile());
	// collego l'aut attuale
	sub.setAutorizzazioni(aut);
	sub.setConcessionicausaliByFkAutsubConccausAcq(aut.getConcessionicausaliByFkAutConccausAcq());
	Concessionicausali causaleCess = concessionicausaliService.findById(causaleCessazione.getId());
	sub.setConcessionicausaliByFkAutsubConccausCess(causaleCess);
	sub.setDataCessazione(dataCessazione);
	sub.setIstanze(aut.getIstanza());
	sub.setMovimenti(aut.getMovimenti());
	sub.setDatascadenza(aut.getDatascadenza());
	sub.setTipologiaregistro(aut.getTipologiaregistro());
	sub.setDataFineAffitto(aut.getDataFineAffitto());
	return sub;
    }

    /**
     * metodo per verificare se esiste già un'autorizzazione/concessione con i nuovi dati inseriti.<br />
     * Se almeno uno dei dati della nuova aut/conc è cambiato rispetto a quella vecchia allora è eseguita una
     * {@link AutorizzazioniService#findAutOConcByEstremi(String, Date, String, Integer)}.<br />
     * Se la query torna un record allora è lanciata una RuntimeException()
     * 
     * @param autH
     */
    private void validateInsertSubentro(AutorizzazioniHelper autH) {

	Autorizzazioni aut = autH.getAutorizzazione();
	boolean eseguiFindPreventiva = false;
	String tempAutoriznumero = aut.getAutoriznumero();
	Date tempAutorizdata = aut.getAutorizdata();
	VwEntilocali tempAutorizcomune = aut.getAutorizcomune();
	Tipologiaregistri tempAutorizregistro = aut.getTipologiaregistro();
	if (StringUtils.isNotBlank(autH.getAutoriznumeroSubentro())) {
	    eseguiFindPreventiva = true;
	    tempAutoriznumero = autH.getAutoriznumeroSubentro();
	}
	if (autH.getAutorizdataSubentro() != null) {
	    eseguiFindPreventiva = true;
	    tempAutorizdata = autH.getAutorizdataSubentro();
	}
	if (autH.getAutorizcomuneSubentro() != null && StringUtils.isNotBlank(autH.getAutorizcomuneSubentro().getCodicecomune())) {
	    eseguiFindPreventiva = true;
	    tempAutorizcomune = autH.getAutorizcomuneSubentro();
	}
	if (autH.getAutorizregistroSubentro() != null && autH.getAutorizregistroSubentro().getId() != null
		&& autH.getAutorizregistroSubentro().getId().getCodice() != null) {
	    eseguiFindPreventiva = true;
	    tempAutorizregistro = autH.getAutorizregistroSubentro();
	}
	if (eseguiFindPreventiva) {
	    Autorizzazioni autDaEstremi = null;
	    if (overrideUniqueConstraint()) {
		autDaEstremi = this.findByNumeroAndComune(tempAutoriznumero, tempAutorizcomune.getCodicecomune());
	    } else {
		autDaEstremi = this.findAutOConcByEstremi(tempAutoriznumero, tempAutorizdata, tempAutorizcomune.getCodicecomune(),
			tempAutorizregistro.getId().getCodice());
	    }
	    if (autDaEstremi != null && autDaEstremi.getId().getCodice().intValue() != aut.getId().getCodice().intValue()) {
		// reset disabled campo numero
		Tipologiaregistri reg = autH.getAutorizregistroSubentro();
		if (EntityUtils.getNestedProperty(reg, "id.codice") != null) {
		    Tipologiaregistri registro = tipologiaregistriService.findById(new PkId(reg.getId().getCodice()));
		    NumerazioneEnum confReg = NumerazioneEnum.daRegistro(registro);
		    if (NumerazioneEnum.VUOTO.equals(confReg)) {
			autH.setRegistroAutomatico(false);
		    }
		}
		throw new RuntimeException("I valori inseriti nell'autorizzazione/concessione [" +
			aut.getTransientEstremiAut() +
			"] sono già presenti nell'autorizzazione/concessione [" +
			autDaEstremi.getTransientEstremiAut() +
			"]");
	    }
	}
    }

    @Override
    public AutorizzazioniConcessioni findConcessione(Autorizzazioni aut) {

	Autorizzazioni _aut = this.findById(aut.getId());
	if (_aut.getAutorizzazioniConcessionisForFkAutconcAutatt() != null && !_aut.getAutorizzazioniConcessionisForFkAutconcAutatt().isEmpty()) {
	    AutorizzazioniConcessioni concessione = _aut.getAutorizzazioniConcessionisForFkAutconcAutatt().iterator().next();
	    return concessione;
	}
	return null;
    }

    @Override
    public Autorizzazioni findAutOConcByEstremi(String autoriznumero, Date autorizdata, String codicecomune, Integer codiceregistro) {

	return autorizzazioniDAO.findAutOConcByEstremi(autoriznumero, autorizdata, codicecomune, codiceregistro);
    }

    @Override
    public void insert(Autorizzazioni entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && validateInsert(entity)) {
	    autorizzazioniDAO.insert(entity);
	}
    }

    /**
     * verifica che l'aut non sia già presente e che il registro selezionato non sia automatico
     * 
     * @param entity
     * @return
     */
    private boolean validateInsert(Autorizzazioni entity) {

	Autorizzazioni aut = null;
	if (overrideUniqueConstraint()) {
	    aut = this.findByNumeroAndComune(entity.getAutoriznumero(), entity.getAutorizcomune().getCodicecomune());
	} else {
	    aut = this.findAutOConcByEstremi(entity.getAutoriznumero(), entity.getAutorizdata(), entity.getAutorizcomune().getCodicecomune(),
		    entity.getTipologiaregistro().getId().getCodice());
	}
	if (aut != null) {
	    throw new RuntimeException("Autorizzazione presente. codice " + aut.getId().getCodice());
	}
	Tipologiaregistri registro = tipologiaregistriService.findById(new PkId(entity.getTipologiaregistro().getId().getCodice()));
	NumerazioneEnum numerazioneRegistro = NumerazioneEnum.daRegistro(registro);
	if (!NumerazioneEnum.VUOTO.equals(numerazioneRegistro)) {
	    log.error("Il registro selezionato esegue una numerazione automatica. codice:{}", entity.getTipologiaregistro().getId().getCodice());
	    throw new RuntimeException("Il registro selezionato esegue una numerazione automatica.");
	}
	return true;
    }

    @Override
    public IstanzaAutConcHelper findByIstanza(Istanze istanza) {

	List<Autorizzazioni> aut = autorizzazioniDAO.findByIstanza(istanza);
	List<AutorizzazioniSubentri> autSub = autorizzazioniSubentriService.findAutorizzazioniSubentriByIstanza(istanza);
	IstanzaAutConcHelper istanzaAutConcHelper = new IstanzaAutConcHelper();
	istanzaAutConcHelper.setAutorizzazioni(aut);
	istanzaAutConcHelper.setAutorizzazioniSubentri(autSub);
	return istanzaAutConcHelper;
    }

    @Override
    public IstanzaAutConcHelper findConcESubByIstanza(Integer codiceIstanza) {

	List<AutorizzazioniConcessioni> conc = autorizzazioniConcessioniService.findConcessioniByIstanza(codiceIstanza);
	List<AutorizzazioniSubentri> concSub = autorizzazioniSubentriService.findConcessioniSubentriByIstanza(codiceIstanza);
	IstanzaAutConcHelper istanzaAutConcHelper = new IstanzaAutConcHelper();
	istanzaAutConcHelper.setConcessioni(conc);
	istanzaAutConcHelper.setConcessioniSubentri(concSub);
	return istanzaAutConcHelper;
    }

    @Override
    public IstanzaAutConcHelper findAutEConcESubByIstanza(Istanze istanza) {

	IstanzaAutConcHelper istanzaAutConcHelper = new IstanzaAutConcHelper();
	IstanzaAutConcHelper istanzaAutHelper = this.findByIstanza(istanza);
	IstanzaAutConcHelper istanzaConcHelper = this.findConcESubByIstanza(istanza.getId().getCodice());
	istanzaAutConcHelper.setAutorizzazioni(istanzaAutHelper.getAutorizzazioni());
	istanzaAutConcHelper.setAutorizzazioniSubentri(istanzaAutHelper.getAutorizzazioniSubentri());
	istanzaAutConcHelper.setConcessioni(istanzaConcHelper.getConcessioni());
	istanzaAutConcHelper.setConcessioniSubentri(istanzaConcHelper.getConcessioniSubentri());
	return istanzaAutConcHelper;
    }

    @Override
    public List<Autorizzazioni> findConcessioniByAnagrafe(Integer codiceAnagrafe) {

	return autorizzazioniDAO.findConcessioniByAnagrafe(codiceAnagrafe);
    }

    @Override
    public void updateRegistro(Autorizzazioni autorizzazione, Integer nuovoCodiceRegistro) {

	Tipologiaregistri registroAutorizzazione = tipologiaregistriService.findById(new PkId(nuovoCodiceRegistro));
	if (registroAutorizzazione != null) {
	    autorizzazione.setTipologiaregistro(registroAutorizzazione);
	    EstremiAutorizzazione estremi = new NumerazioneFactory(configurazioneService, tipologiaregistriService, userSecurityService,
		    protocollazioneService, autorizzazione).getService().get();
	    autorizzazione.setTipologiaregistro(registroAutorizzazione);
	    autorizzazione.setAutoriznumero(estremi.getNumero());
	    if (NumerazioneEnum.DA_PROTOCOLLO.equals(NumerazioneEnum.daRegistro(registroAutorizzazione))
		    || Boolean.TRUE.equals(registroAutorizzazione.getTrFlagdataauto())) {
		autorizzazione.setAutorizdata(estremi.getData());
		autorizzazione.setDataRilascio(null);
		autorizzazione.setAutorizdataregistr(null);
	    } else {
		if (autorizzazione.getAutorizdata() == null) {
		    autorizzazione.setAutorizdata(estremi.getData());
		}
		if (autorizzazione.getAutorizdataregistr() == null) {
		    autorizzazione.setAutorizdataregistr(Calendar.getInstance().getTime());
		}
	    }
	}
    }

    @Override
    public void updateRegistroConcessione(Autorizzazioni autorizzazione, Integer nuovoCodiceRegistro) {

	Tipologiaregistri registroConcessione = tipologiaregistriService.findById(new PkId(nuovoCodiceRegistro));
	if (null != registroConcessione) {
	    autorizzazione.setTipologiaregistro(registroConcessione);
	    if (null != registroConcessione.getTrFlagprotocollo() && registroConcessione.getTrFlagprotocollo().booleanValue() == false) {
		if (BooleanUtils.isTrue(registroConcessione.getFlagUsaProgrConf())) {
		    ConfigurazioneId id = new ConfigurazioneId(registroConcessione.getSoftware().getCodice());
		    Configurazione c = configurazioneService.findById(id);
		    if (StringUtils.isBlank(c.getProgressivoRegistriAut())) {
			FlashMessages.getWarnings().add(
				"Attenzione!!! La configurazione del registro prevede che sia usato il valore della configurazione (dati tecnici) ma il valore non è stato impostato. ");
		    }
		    autorizzazione.setAutoriznumero(c.getProgressivoRegistriAut());
		} else {
		    autorizzazione.setAutoriznumero(registroConcessione.getTrProgressivo());
		}
	    } else {
		autorizzazione.setAutoriznumero(null);
		autorizzazione.setAutorizdata(null);
		autorizzazione.setDataRilascio(null);
	    }
	    if (registroConcessione.getTrFlagdataauto() != null && registroConcessione.getTrFlagdataauto().booleanValue()) {
		autorizzazione.setAutorizdata(null);
		autorizzazione.setAutorizdataregistr(null);
		autorizzazione.setDataRilascio(null);
	    }
	}
    }

    @Override
    public void deleteConcessione(ValidaEliminazioneAutConcCommand cmd) throws OperazioneCancellazioneAutConcException {

	Autorizzazioni aut = this.findById(new PkId(cmd.getIdAutorizzazioni()));
	checkAutorizzazioneIsReadonly(cmd.getCodiceIstanza(), aut);
	List<AutorizzazioniSubentri> subentris = autorizzazioniSubentriService.findByAutorizzazione(cmd.getIdAutorizzazioni(), 0, 1);
	if (!subentris.isEmpty()) {
	    // nel caso ci siano subentri alla concessione trovo l'ultimo subentro che sarebbe il primo elemento dei
	    // subentri (il set è ordinato per DATA_CESSAZIONE desc)
	    AutorizzazioniSubentri ultimoSubentro = subentris.get(0);
	    // copio i dati dal subentro all'autorizzazione che intendo cancellare
	    this.copySubentroToAutorizzazioneDTO(aut, ultimoSubentro);
	    // aggiorno l'autorizzazione con i nuovi dati
	    this.update(aut);
	    // cerco se il subentro aveva un'autorizzazione collegata
	    List<AutorizzazioniSubentriConc> autsubconcs = autorizzazioniSubentriConcService.findByIdSubentro(ultimoSubentro.getId().getCodice());
	    AutorizzazioniSubentri autCollegataAlSubentro = null;
	    for (AutorizzazioniSubentriConc autConc : autsubconcs) {
		if (autConc.getAutorizzazioniSubentriByFkAutconcAutcoll() != null) {
		    autCollegataAlSubentro = autConc.getAutorizzazioniSubentriByFkAutconcAutcoll();
		    break;
		}
	    }
	    // trovo la concessione per verificare se aveva autorizzazioni collegate
	    List<AutorizzazioniConcessioni> concs = autorizzazioniConcessioniService.findByAutorizzazioneAttuale(cmd.getIdAutorizzazioni());
	    boolean deleteAutCollegataAlSub = false;
	    for (AutorizzazioniConcessioni concessione : concs) {
		Autorizzazioni autorizzazioneCollegataAllaConcessione = concessione.getAutorizzazioniByFkAutconcAutcoll();
		if (autCollegataAlSubentro != null && autCollegataAlSubentro.getId() != null && autCollegataAlSubentro.getId().getCodice() != null) {
		    if (autorizzazioneCollegataAllaConcessione != null && autorizzazioneCollegataAllaConcessione.getId() != null
			    && autorizzazioneCollegataAllaConcessione.getId().getCodice() != null) {
			// nel caso che la concessione aveva una autorizzazione collegata
			// nel caso che il subentro abbia una autorizzazione collegata copio i dati dell'autorizzazione
			// collegata del subentro sulla autorizzazione collegata alla concessione
			copySubentroToAutorizzazioneDTO(autorizzazioneCollegataAllaConcessione, autCollegataAlSubentro);
			// aggiorno i dati della autorizzazione collegata alla concessione
			this.update(autorizzazioneCollegataAllaConcessione);
			// cancello l'autorizzazione collegata al subentro
			deleteAutCollegataAlSub = true;
			break;
		    } else {
			// la concessione attuale non aveva autorizzazioni collegate mentre quella subentrata si quindi devo
			// trovare l'autorizzazione di quella subentrata ed associarla
			Autorizzazioni autDaCollegareAllaConcessione = findById(autCollegataAlSubentro.getAutorizzazioni().getId());
			concessione.setAutorizzazioniByFkAutconcAutcoll(autDaCollegareAllaConcessione);
			autorizzazioniConcessioniService.update(concessione);
			// cancello l'autorizzazione subentrata
			deleteAutCollegataAlSub = true;
			break;
		    }
		}
	    }
	    // cancello il subentro
	    autorizzazioniSubentriService.delete(ultimoSubentro);
	    if (deleteAutCollegataAlSub) {
		autorizzazioniSubentriService.delete(autCollegataAlSubentro);
	    }
	} else {
	    // nel caso non ci siano subentri alla concessione
	    List<AutorizzazioniConcessioni> concs = autorizzazioniConcessioniService.findByAutorizzazioneAttuale(cmd.getIdAutorizzazioni());
	    for (AutorizzazioniConcessioni autorizzazioniConcessioni : concs) {
		autorizzazioniConcessioniService.delete(autorizzazioniConcessioni);
	    }
	    aut.setAutorizzazioniConcessionisForFkAutconcAutatt(new HashSet<AutorizzazioniConcessioni>(0));
	    this.delete(aut);
	}
	try {
	    this.eventPublisher.publishThrowOnFailure(new EventoAutorizzazioneConcessioneEliminata(cmd));
	} catch (EventAbortedException e) {
	    log.error("Errore nel subentro dell'autorizzazione/concessione " + e.getMessage(), e);
	    throw new OperazioneCancellazioneAutConcException(e);
	}
    }

    private void copySubentroToAutorizzazioneDTO(Autorizzazioni autorizzazione, AutorizzazioniSubentri subentro) {

	autorizzazione.setAnagrafe(subentro.getAnagrafe());
	autorizzazione.setOccupante(subentro.getOccupante());
	autorizzazione.setAutorizcomune(subentro.getAutorizcomune());
	autorizzazione.setAutorizdata(subentro.getAutorizdata());
	autorizzazione.setDataRilascio(subentro.getDataRilascio());
	autorizzazione.setAutorizdataregistr(subentro.getAutorizdataregistr());
	autorizzazione.setAutoriznumero(subentro.getAutoriznumero());
	autorizzazione.setAutorizresponsabile(subentro.getAutorizresponsabile());
	autorizzazione.setConcessionicausaliByFkAutConccausAcq(subentro.getConcessionicausaliByFkAutsubConccausAcq());
	// RIATTIVO LA CONCESSIONE
	autorizzazione.setConcessionicausaliByFkAutConccausCess(null);
	autorizzazione.setDataCessazione(null);
	autorizzazione.setFlagAttiva(true);
	//
	autorizzazione.setIstanza(subentro.getIstanze());
	autorizzazione.setMovimenti(subentro.getMovimenti());
	autorizzazione.setTipologiaregistro(subentro.getTipologiaregistro());
	autorizzazione.setDatascadenza(subentro.getDatascadenza());
	autorizzazione.setDataFineAffitto(subentro.getDataFineAffitto());
    }

    /**
     * Il metodo controlla se la concessione è dell'istanza indicata come parametro. se non è la stessa è probabile che
     * sto trattando i dati di un subentro e devo lanciare un' eccezione. Il metodo serve più che altro come controllo
     * prima di cancellare o modificare i dati
     * 
     * @param codiceIstanza
     *            l'istanza che serve per il confronto
     * @param entity
     *            l'entity che devo aggiornare
     */
    private void checkAutorizzazioneIsReadonly(Integer codiceIstanza, Autorizzazioni entity) {

	Autorizzazioni autorizzazione = this.findById(entity.getId());
	if (autorizzazione.getIstanza().getId().getCodice().equals(codiceIstanza)) {
	    return;
	}
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	if (!entity.getAutorizzazioniSubentris().isEmpty()) {
	    ivs.add(new InvalidValue("autorizzazioni.service_error.errore_in_modifica_passaggio_intermedio", null, null, "", null));
	}
	if (!ivs.isEmpty()) {
	    this.throwValidationMessages(ivs);
	}
	throw new RuntimeException("Non è possibile modificare/cancellare un passaggio intermedio delle Concessioni/Autorizzazioni.");
    }

    @Override
    public Istanze findIstanzaInizialePerPresenzeManifestazione(Integer idAutorizzazione, Integer codiceIstanza) {

	if (codiceIstanza == null || idAutorizzazione == null) {
	    throw new IllegalArgumentException("Non è presente l'istanza o l'autorizzazione");
	}
	Istanze istanzaIniziale = istanzeService.findById(new PkId(codiceIstanza));
	// verifico se è subentrato a qualcuno su questo mercato,uso,posteggio
	List<AutorizzazioniSubentri> concSubs = autorizzazioniSubentriService.findSubentriByConcessione(idAutorizzazione, 0, 1, OrderTypeEnum.ASC);
	if (concSubs != null && !concSubs.isEmpty()) {
	    // sono presenti dei subentri per questo mercato,uso,posteggio. restituisco l'ultima che corrisponde a
	    // quella iniziale per la quale esiste l'istanza con la scheda dyn dalla quale recuperare gli estremi
	    // dell'aut con la
	    // quale fare la presenza.	    
	    istanzaIniziale = concSubs.get(0).getIstanze();
	}
	return istanzaIniziale;
    }

    @Override
    public void insertAutorizzazione(Autorizzazioni entity) {

	dataIntegration(entity);
	gestisciNumeroAutorizzazione(entity, ORMHelper.getToken());
	if (validateEntity(entity)) {
	    //1. Controllo se autorizzazione già presente
	    if (this.checkAutorizzazionePresente(entity)) {
		this.throwValidationMessage(
			new InvalidValue("autorizzazioni.service_error.autorizzazione_con_estremi_gia_presenti", null, null, "", null));
	    }
	    //2 Se flag_attiva è true allora non devo registrare la data e la causale di cessazione
	    if (BooleanUtils.isTrue(entity.getFlagAttiva())) {
		entity.setDataCessazione(null);
		entity.setConcessionicausaliByFkAutConccausCess(null);
	    }
	    //3 Numero definitivamente l'autorizzazione
	    EstremiAutorizzazione estremi = new NumerazioneFactory(configurazioneService, tipologiaregistriService, userSecurityService,
		    protocollazioneService, entity).getService().assegnaNumero();
	    if (StringUtils.isNotBlank(estremi.getNumero())) {
		entity.setFkidprotocollo(estremi.getIdRiferimento());
		entity.setAutoriznumero(estremi.getNumero());
	    }
	    //4 Sistemazioni post numerazione autorizzazione
	    Set<MercatipresenzeStorico> s = entity.getMercatipresenzeStoricos();
	    Set<MercatipresenzeStorico> s2 = new HashSet<MercatipresenzeStorico>();
	    if (s != null && !s.isEmpty()) {
		s2.addAll(s);
	    }
	    entity.setMercatipresenzeStoricos(null);
	    //5. Tolgo i metadati prima della insert
	    Set<AutorizzazioniMetadati> metadati = entity.getMetadati();
	    entity.setMetadati(new HashSet<AutorizzazioniMetadati>());
	    autorizzazioniDAO.insert(entity);
	    autorizzazioniDAO.flush();
	    autorizzazioniDAO.commit();
	    autorizzazioniDAO.flush();
	    entity.setMetadati(metadati);
	    childDataIntegration(entity);
	    childDataInsert(entity, s2);
	}
    }

    private void childDataIntegration(Autorizzazioni entity) {

	if (entity.getMetadati() != null) {
	    for (AutorizzazioniMetadati metadato : entity.getMetadati()) {
		if (metadato.getId() == null) {
		    metadato.setId(new AutorizzazioniMetadatiId());
		}
		metadato.getId().setIdcomune(entity.getId().getIdcomune());
		metadato.getId().setFkIdAutorizzazione(entity.getId().getCodice());
	    }
	}
    }

    private boolean checkAutorizzazionePresente(Autorizzazioni autorizzazione) {

	//1. verifico tra le autorizzazioni
	Autorizzazioni autorizzazioneEsistente = null;
	if (overrideUniqueConstraint()) {
	    autorizzazioneEsistente = this.findByNumeroAndComune(autorizzazione.getAutoriznumero(),
		    autorizzazione.getAutorizcomune().getCodicecomune());
	} else {
	    autorizzazioneEsistente = this.findAutOConcByEstremi(autorizzazione.getAutoriznumero(), autorizzazione.getAutorizdata(),
		    autorizzazione.getAutorizcomune().getCodicecomune(), autorizzazione.getTipologiaregistro().getId().getCodice());
	}
	if (autorizzazioneEsistente != null) {
	    return true;
	}
	//2. verifico tra i subentri ???????
	AutorizzazioniSubentri autSubEsistente = null;
	if (overrideUniqueConstraint()) {
	    autSubEsistente = autorizzazioniSubentriService.findByNumeroAndComune(autorizzazione.getAutoriznumero(),
		    autorizzazione.getAutorizcomune().getCodicecomune());
	} else {
	    autSubEsistente = autorizzazioniSubentriService.findAutSubOConcSubByEstremi(autorizzazione.getAutoriznumero(),
		    autorizzazione.getAutorizdata(), autorizzazione.getAutorizcomune().getCodicecomune(),
		    autorizzazione.getTipologiaregistro().getId().getCodice());
	}
	return autSubEsistente != null;
    }

    private void childDataInsert(Autorizzazioni entity, Set<MercatipresenzeStorico> s) {

	if (s != null) {
	    if (s.size() > 0) {
		for (MercatipresenzeStorico mps : s) {
		    mps.setAutorizzazioni(entity);
		    mercatipresenzeStoricoService.insert(mps);
		}
	    }
	}
	if (entity.getMetadati() != null) {
	    for (AutorizzazioniMetadati metadato : entity.getMetadati()) {
		this.metadatiService.insert(metadato);
	    }
	}
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_AUTORIZ_ACCESSI)) {
	    if (entity.getTipologiaregistro() != null) {
		if (entity.getNumPreavvisiRimasti() == null) {
		    int numeroPreavvisi = entity.getTipologiaregistro().getNumeroPreavvisi() == null ? 0
			    : entity.getTipologiaregistro().getNumeroPreavvisi();
		    entity.setNumPreavvisiRimasti(numeroPreavvisi);
		}
		if (entity.getNumProrogheRimaste() == null) {
		    int numeroProroghe = entity.getTipologiaregistro().getNumeroProroghe() == null ? 0
			    : entity.getTipologiaregistro().getNumeroProroghe();
		    entity.setNumProrogheRimaste(numeroProroghe);
		}
		if (entity.getNumRinnoviRimasti() == null) {
		    int numeroRinnovi = entity.getTipologiaregistro().getNumeroRinnovi() == null ? 0
			    : entity.getTipologiaregistro().getNumeroRinnovi();
		    entity.setNumRinnoviRimasti(numeroRinnovi);
		}
		autorizzazioniDAO.update(entity);
	    }
	}
    }

    @Override
    public void insertAutorizzazione(AutorizzazioniCommand command, boolean standardModeInsert) {

	Autorizzazioni aut = command.getEntity();
	//1.Imposto il codicefirmatario dell'autorizzazione tra i suoi metadati
	if (StringUtils.isNotBlank(command.getCodiceFirmatario())) {
	    aut.getMetadati().add(new CodiceFirmatario(null, command.getCodiceFirmatario()).toAutorizzazioniMetadati());
	}
	this.insertAutorizzazione(aut);
	this.eventPublisher.publish(new EventoAutorizzazioneInserita(aut.getId().getCodice(), aut.getIstanza().getComune().getCodicecomune(),
		command.getCodiceFirmatario()));
	// Se è standardModeInsert==true, mantiene il vecchio comportamento, altrimenti svolgerà altre operazioni
	if (!standardModeInsert) {
	    // verifico se si tratta di un particolare inserimento
	    if (EntityUtils.getNestedProperty(aut, "id.codice") != null) {
		// Tramite il tipo registro controlla se è un autorizzazione particolre che segue particolari logiche 
		// per l'inserimento
		String tipoAut = this.tipoAutorizzazione(aut.getTipologiaregistro().getId().getCodice());
		if (StringUtils.isNotBlank(tipoAut)) {
		    childDataInsert(command, tipoAut, false);
		}
	    }
	}
    }

    @Override
    public void updateAutorizzazione(AutorizzazioniCommand entity, boolean standardModeInsert) {

	this.update(entity.getEntity());
	if (!standardModeInsert) {
	    // verifico se si tratta di un particolare inserimento
	    if (EntityUtils.getNestedProperty(entity.getEntity(), "id.codice") != null) {
		String tipoAut = this.tipoAutorizzazione(entity.getEntity().getId().getCodice());
		if (StringUtils.isNotBlank(tipoAut)) {
		    childDataInsert(entity, tipoAut, true);
		}
	    }
	}
    }

    private void childDataInsert(AutorizzazioniCommand entity, String tipoAut, boolean isUpdate) {

	boolean existRecord = false;
	if (tipoAut.equals(WebConstants.AUTORIZZAZIONE_DEHORS)) {
	    // popolo l'oggetto DehorsMqIstanze con l'autorizzazione,istanza e attivo
	    // Controllo se per l'istanza esiste gia un oggetto "DehorsMqIstanze"
	    List<DehorsMqIstanze> dehorsMqIstanzes = dehorsMqIstanzeService.findByIstanza(entity.getEntity().getIstanza().getId().getCodice(), true);
	    DehorsMqIstanze dehorsMqIstanze = null;
	    if (!dehorsMqIstanzes.isEmpty() && EntityUtils.getNestedProperty(dehorsMqIstanzes.get(0), "id.codice") != null) {
		dehorsMqIstanze = dehorsMqIstanzes.get(0);
		dehorsMqIstanze.setMqassegnati(entity.getDehorsMqIstanze().getMqassegnati());
		existRecord = true;
	    } else {
		dehorsMqIstanze = entity.getDehorsMqIstanze();
	    }
	    dehorsMqIstanze.setAutorizzazioni(entity.getEntity());
	    dehorsMqIstanze.setIstanze(entity.getEntity().getIstanza());
	    dehorsMqIstanze.setCessata(false);
	    if (!existRecord) {
		dehorsMqIstanzeService.insert(dehorsMqIstanze);
	    } else {
		dehorsMqIstanzeService.update(dehorsMqIstanze);
	    }
	    // Introduco il log dell'operazione
	    String messLog = "Inserita nuova autorizzazione dehors";
	    if (isUpdate) {
		messLog = "Modificati i dati dehors";
	    }
	    DehorsLog dehorsLog = dehorsLogService.populateDehorsLog(entity, entity.getDehorsMqIstanze().getMqassegnati(), new BigDecimal(0),
		    messLog);
	    dehorsLogService.insert(dehorsLog);
	}
    }

    @Override
    public String tipoAutorizzazione(Integer codiceRegistro) {

	log.debug("setPageAttributesAutorizzazioni# Controllo se è un autorizzazione di tipo dehors....");
	DehorsCfg dehorsCfg = dehorsCfgService.findByTipologiaregistro(codiceRegistro);
	if (EntityUtils.getNestedProperty(dehorsCfg, "id.codice") != null) {
	    log.debug("tipoAutorizzazione# Autorizzazione di tipo dehors");
	    return WebConstants.AUTORIZZAZIONE_DEHORS;
	}
	//}
	return "";
    }

    /**
     * Il metodo si occupa di effettuare delle validazioni a monte prima di procedere all'inserimento
     * dell'autorizzazioni le validazione possono variare a seconda delle configurazioni.
     * 
     * @param entity
     * @return
     */
    public boolean validateInsertAutorizzazione(Autorizzazioni entity) {

	// Controlle se si tratta di un' autorizzazione DEHORS e verifico se sono rispettati tutti vincoli 
	if (entity != null && entity.getTipologiaregistro() != null && entity.getTipologiaregistro().getId() != null
		&& entity.getTipologiaregistro().getId().getCodice() != null) {
	    Integer codiceregistro = entity.getTipologiaregistro().getId().getCodice();
	    DehorsCfg dehorsCfg = dehorsCfgService.findByTipologiaregistro(codiceregistro);
	    List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	    if (EntityUtils.getNestedProperty(dehorsCfg, "id.codice") != null) {
		log.debug("validateInsertAutorizzazione# Il registro è configurato per autorizzazione di tipo dehors effetuo validazione");
		ivs = checkCondizioniAutorizzazioniDehors(entity);
		if (ivs.size() > 1) {
		    this.throwValidationMessages(ivs);
		}
	    }
	}
	return true;
    }

    private List<InvalidValue> checkCondizioniAutorizzazioniDehors(Autorizzazioni entity) {

	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	// devo controllare che siano presenti data rilascio e data fine. Inoltre data inizio deve verficare che la
	// data odierna sia quella di sistema.
	ivs.add(new InvalidValue("autorizzazioni.service_error.autorizzazione_tipo_dehors", null, null, "", null));
	if (entity.getAutorizdata() == null) {
	    ivs.add(new InvalidValue("autorizzazioni.service_error.data_autorizzazione_non_presente", null, null, "", null));
	} else {
	    if (Utilities.compareDates(entity.getAutorizdata(), new Date()) != 0) {
		ivs.add(new InvalidValue("autorizzazioni.service_error.data_autorizzazione_no_data_sistema", null, null, "", null));
	    }
	}
	if (entity.getDatascadenza() == null) {
	    ivs.add(new InvalidValue("autorizzazioni.service_error.data_scadenza_autorizzazione_non_presente", null, null, "", null));
	}
	return ivs;
    }

    private void dataIntegration(Autorizzazioni entity) {

	if (entity == null) {
	    throw new RuntimeException("Non si può inserire/aggiornare un'autorizzazione nulla");
	}
	if (!EntityUtils.isNestedPropertyBlank(entity.getIstanza(), "id.codice") && (entity.getAnagrafe() == null || entity.getOccupante() == null)) {
	    Istanze istanza = istanzeService.findById(entity.getIstanza().getId());
	    if (entity.getAnagrafe() == null) {
		entity.setAnagrafe(istanza.getTitolareLegaleORichiedente());
	    }
	    if (entity.getOccupante() == null) {
		entity.setOccupante(istanza.getTitolareLegaleORichiedente());
	    }
	}
	if (entity.getOccupante() == null || entity.getOccupante().getId() == null || entity.getOccupante().getId().getCodice() == null) {
	    // default occupante è titolare
	    entity.setOccupante(entity.getAnagrafe());
	}
	if (entity.getFlagAttiva() == null) {
	    entity.setFlagAttiva(Boolean.TRUE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Autorizzazioni entity) {

	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanza(), PkId.class, "id.codice");
	entity.setIstanza(istanza);
	Anagrafe anagrafe = anagrafeService.bindDomainObject(entity.getAnagrafe(), PkId.class, "id.codice");
	entity.setAnagrafe(anagrafe);
	Anagrafe occupante = anagrafeService.bindDomainObject(entity.getOccupante(), PkId.class, "id.codice");
	entity.setOccupante(occupante);
	Concessionicausali causaleacquisizione = concessionicausaliService.bindDomainObject(entity.getConcessionicausaliByFkAutConccausAcq(),
		PkId.class, "id.codice");
	entity.setConcessionicausaliByFkAutConccausAcq(causaleacquisizione);
	Concessionicausali causalecessazione = concessionicausaliService.bindDomainObject(entity.getConcessionicausaliByFkAutConccausCess(),
		PkId.class, "id.codice");
	entity.setConcessionicausaliByFkAutConccausCess(causalecessazione);
	VwEntilocali comune = vwEntilocaliService.bindDomainObject(entity.getAutorizcomune(), String.class, "codicecomune");
	entity.setAutorizcomune(comune);
    }

    @Override
    public List<Autorizzazioni> findByEstremi(String estremi, Integer maxResult) {

	return autorizzazioniDAO.findByEstremi(estremi, maxResult);
    }

    @Override
    public Autorizzazioni findAutOConcPerMercatiWS(Autorizzazioni estremi) {

	boolean estremiCompleti = this.checkEstremiAut(estremi);
	if (estremiCompleti) {
	    log.debug("Estremi completi. Ricerco su db da estremi...");
	    Autorizzazioni aut = this.findAutOConcByEstremi(estremi.getAutoriznumero(), estremi.getAutorizdata(),
		    estremi.getAutorizcomune().getCodicecomune(), estremi.getTipologiaregistro().getId().getCodice());
	    if (aut == null) {
		log.warn("Aut o conc non esiste.");
		throw new RuntimeException("Autorizzazione non presente. Controllare gli estremi inseriti.");
	    } else {
		log.debug("Aut o conc trovata. codice:{}", aut.getId().getCodice());
		if (BooleanUtils.isFalse(aut.getFlagAttiva())) {
		    log.error("Aut o conc cessata. codice:{}", aut.getId().getCodice());
		    throw new RuntimeException("Autorizzazione cessata.");
		}
		return aut;
	    }
	} else {
	    log.warn("Estremi dell'aut o conc non completi.");
	    throw new RuntimeException("Estremi dell'autorizzazione incompleti.");
	}
    }

    protected boolean checkEstremiAut(Autorizzazioni aut) {

	if (EntityUtils.getNestedProperty(aut, "autoriznumero") == null) {
	    log.warn("autoriznumero nullo.");
	    return false;
	}
	if (EntityUtils.getNestedProperty(aut, "autorizdata") == null) {
	    log.warn("autorizdata nullo.");
	    return false;
	}
	if (EntityUtils.getNestedProperty(aut, "autorizcomune.codicecomune") == null) {
	    log.warn("autorizcomune.codicecomune nullo.");
	    return false;
	} else {
	    VwEntilocali _autorizcomune = vwEntilocaliService.findById(aut.getAutorizcomune().getCodicecomune());
	    if (EntityUtils.getNestedProperty(_autorizcomune, "codicecomune") == null) {
		log.warn("autorizcomune.codicecomune invalido:{}", aut.getAutorizcomune().getCodicecomune());
		return false;
	    }
	    aut.setAutorizcomune(_autorizcomune);
	}
	if (EntityUtils.getNestedProperty(aut, "tipologiaregistro.id.codice") == null) {
	    log.warn("tipologiaregistro.id.codice nullo.");
	    return false;
	} else {
	    Tipologiaregistri autorizregistro = tipologiaregistriService.findById(new PkId(aut.getTipologiaregistro().getId().getCodice()));
	    if (EntityUtils.getNestedProperty(autorizregistro, "id.codice") == null) {
		log.warn("tipologiaregistro.id.codice invalido:{}", aut.getTipologiaregistro().getId().getCodice());
		return false;
	    }
	    aut.setTipologiaregistro(autorizregistro);
	}
	return true;
    }

    @Override
    public Autorizzazioni insertAutOConcPerMercatiWS(Istanze istanza, Autorizzazioni estremi, String attivitaIstatIdCatMerc) {

	if (log.isDebugEnabled()) {
	    if (istanza != null) {
		log.debug("insertAutOConcDaSchedaDyn(). codice istanza:{}, numero istanza:{}", istanza.getId().getCodice(),
			istanza.getNumeroistanza());
	    } else {
		log.debug("insertAutOConcDaSchedaDyn() istanza nulla");
	    }
	}
	boolean estremiCompleti = this.checkEstremiAut(estremi);
	if (estremiCompleti) {
	    log.debug("Estremi completi. Ricerco su db da estremi...");
	    Autorizzazioni _aut = null;
	    if (overrideUniqueConstraint()) {
		_aut = this.findByNumeroAndComune(estremi.getAutoriznumero(), estremi.getAutorizcomune().getCodicecomune());
	    } else {
		_aut = this.findAutOConcByEstremi(estremi.getAutoriznumero(), estremi.getAutorizdata(), estremi.getAutorizcomune().getCodicecomune(),
			estremi.getTipologiaregistro().getId().getCodice());
	    }
	    if (_aut == null) {
		log.debug("Autorizzazione non trovata");
		// controllo se è presente tra i subentri
		AutorizzazioniSubentri autSub = null;
		if (overrideUniqueConstraint()) {
		    autSub = autorizzazioniSubentriService.findByNumeroAndComune(estremi.getAutoriznumero(),
			    estremi.getAutorizcomune().getCodicecomune());
		} else {
		    autSub = autorizzazioniSubentriService.findAutSubOConcSubByEstremi(estremi.getAutoriznumero(), estremi.getAutorizdata(),
			    estremi.getAutorizcomune().getCodicecomune(), estremi.getTipologiaregistro().getId().getCodice());
		}
		if (autSub != null) {
		    log.error("Gli estremi inseriti corrispondono ad un'aut o conc cessata per subentro. codice:{}", autSub.getId().getCodice());
		    throw new RuntimeException("Gli estremi inseriti corrispondono ad un'autorizzazione cessata per subentro.");
		}
		// il registro lo controllo solo se non trovo l'aut su db.
		Tipologiaregistri registroAutorizzazione = tipologiaregistriService
			.findById(new PkId(estremi.getTipologiaregistro().getId().getCodice()));
		NumerazioneEnum numerazioneRegistro = NumerazioneEnum.daRegistro(registroAutorizzazione);
		if (!NumerazioneEnum.VUOTO.equals(numerazioneRegistro)) {
		    log.error("Il registro selezionato esegue una numerazione automatica. codice:{}",
			    estremi.getTipologiaregistro().getId().getCodice());
		    throw new RuntimeException("Il registro selezionato esegue una numerazione automatica.");
		}
		Autorizzazioni aut = new Autorizzazioni();
		aut.setAutoriznumero(estremi.getAutoriznumero());
		aut.setAutorizdata(estremi.getAutorizdata());
		aut.setDataRilascio(estremi.getAutorizdata());
		aut.setAutorizcomune(estremi.getAutorizcomune());
		aut.setTipologiaregistro(estremi.getTipologiaregistro());
		if (istanza != null) {
		    aut.setAnagrafe(istanza.getTitolareLegaleORichiedente());
		    aut.setOccupante(istanza.getTitolareLegaleORichiedente());
		    aut.setIstanza(istanza);
		}
		aut.setAutorizdataregistr(new Date());
		aut.setFlagAttiva(true);
		try {
		    log.debug("Inserisco l'autorizzazione...");
		    this.insert(aut);
		    this.eventPublisher
			    .publish(new EventoAutorizzazioneInserita(aut.getId().getCodice(), aut.getIstanza().getComune().getCodicecomune(), null));
		    AttivitaId idAtt = new AttivitaId(attivitaIstatIdCatMerc);
		    Attivita att = attivitaService.findById(idAtt);
		    if (att != null) {
			AutorizzazioniAttivita autatt = new AutorizzazioniAttivita();
			autatt.setAutorizzazioni(aut);
			autatt.setAttivita(att);
			autorizzazioniAttivitaService.insert(autatt);
		    }
		} catch (Exception e) {
		    log.error("Errore durante l'inserimento: {}", e.getMessage());
		    throw new RuntimeException("Errore durante l'inserimento: (" + e.getMessage() + ")");
		}
		log.debug("Aut inserita. codice:{}", aut.getId().getCodice());
		return aut;
	    } else {
		if (BooleanUtils.isFalse(_aut.getFlagAttiva())) {
		    log.error("Aut o conc trovata ma cessata. codice:{}", _aut.getId().getCodice());
		    throw new RuntimeException("Autorizzazione cessata.");
		}
		log.debug("Aut o conc trovata. codice:{}", _aut.getId().getCodice());
		return _aut;
	    }
	} else {
	    log.warn("Estremi dell'aut o conc non completi.");
	    throw new RuntimeException("Estremi dell'autorizzazione incompleti.");
	}
    }

    @Override
    public Autorizzazioni findAutOConcPerPresenze(Istanze istanza, MercatiConfigurazione mercatiConfigurazione) {

	log.debug("findAutOConcPerPresenze(). codice istanza:{}, numero istanza:{}", istanza.getId().getCodice(), istanza.getNumeroistanza());
	Autorizzazioni aut = populateEstremiByIstanzaDyn2Dati(istanza, mercatiConfigurazione);
	if (aut != null) {
	    log.debug("Estremi completi. Ricerco su db da estremi chiamando findAutOConcAttivaByEstremi...");
	    aut = this.findAutOConcAttivaByEstremi(aut.getAutoriznumero(), aut.getAutorizdata(), aut.getAutorizcomune().getCodicecomune(),
		    aut.getTipologiaregistro().getId().getCodice());
	}
	return aut;
    }

    @Override
    public Autorizzazioni findAutOConcPerPresenze(Istanze istanza) {

	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findConfigurazione();
	if (mercatiConfigurazione == null) {
	    throw new RuntimeException("Configurazione delle manifestazioni non trovata");
	}
	return this.findAutOConcPerPresenze(istanza, mercatiConfigurazione);
    }

    @Override
    public void cessaConcessioniDellaManifestazione(Mercati mercato, MercatiUso uso, Concessionicausali causaleCessazione, Date dataCessazione,
	    boolean escludiNuoveDaSubentro) {

	validateCessaConcessioni(causaleCessazione, dataCessazione);
	// Nel caso il valore della proprita uso è null, la ricerca verrà effettuata su tutte le concessioni del mercato
	List<AutorizzazioniConcessioni> concAttive = autorizzazioniConcessioniService.findConcessioniAttive(mercato, uso);
	if (concAttive != null) {
	    for (AutorizzazioniConcessioni autorizzazioniConcessioni : concAttive) {
		Autorizzazioni aut = autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutatt();
		if (escludiNuoveDaSubentro) {
		    if (!aut.getAutorizzazioniSubentris().isEmpty()) {
			continue;
		    }
		}
		aut.setFlagAttiva(false);
		aut.setConcessionicausaliByFkAutConccausCess(causaleCessazione);
		aut.setDataCessazione(dataCessazione);
		Autorizzazioni autColl = autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutcoll();
		autorizzazioniDAO.update(aut);
		if (EntityUtils.getNestedProperty(autColl, "id.codice") != null) {
		    autColl.setFlagAttiva(false);
		    autColl.setConcessionicausaliByFkAutConccausCess(causaleCessazione);
		    autColl.setDataCessazione(dataCessazione);
		    autorizzazioniDAO.update(autColl);
		}
	    }
	}
    }

    private boolean validateCessaConcessioni(Concessionicausali causaleCessazione, Date dataCessazione) {

	boolean success = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (causaleCessazione == null) {
	    _ivs.add(new InvalidValue("Causale cessazione obbligatoria.", null, null, null, null));
	}
	if (dataCessazione == null) {
	    _ivs.add(new InvalidValue("Data cessazione obbligatoria.", null, null, null, null));
	}
	if (!_ivs.isEmpty()) {
	    throwValidationMessages(_ivs);
	}
	return success;
    }

    @Override
    public int countByFilter(AutorizzazioniFilter filter) {

	FilterTable filterTable = autorizzazioniFilterToFilterTable(filter);
	int count = autorizzazioniDAO.countRecord(filterTable);
	return count;
	//return autorizzazioniDAO.countByFilter(filter);
    }

    @Override
    public int countByAutorizzazioniFilter(AutorizzazioniFilter filter) {

	return autorizzazioniDAO.countByFilter(filter);
    }

    @Override
    public List<Autorizzazioni> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	filterTable.addRestriction(fr);
	return autorizzazioniDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<IstanzaAutConcHelper> findAutEConcESubByIstanzas(List<Istanze> listIstanze) {

	List<IstanzaAutConcHelper> risultato = new ArrayList<IstanzaAutConcHelper>();
	for (Istanze istanza : listIstanze) {
	    IstanzaAutConcHelper autConcHelper = this.findAutEConcESubByIstanza(istanza);
	    risultato.add(autConcHelper);
	}
	return risultato;
    }

    @Override
    public List<AutorizzazioniDTO> findConcESub(Integer codiceIstanza) {

	return autorizzazioniDAO.findConcESub(codiceIstanza);
    }

    @Override
    public void clear() {

	autorizzazioniDAO.clear();
    }

    @Override
    public List<Autorizzazioni> findByAutorizzazioniFilter(AutorizzazioniFilter filter, Integer firstResult, Integer maxResult) {

	return autorizzazioniDAO.findByAutorizzazioniFilter(filter, firstResult, maxResult);
    }

    public List<AutorizzazioniDTO> findConcESub(Integer codiceIstanza, Boolean escludiCessate) {

	return autorizzazioniDAO.findConcESub(codiceIstanza, escludiCessate);
    }

    @Override
    public void updateCessaAutorizzazioniDehorsScaduteDallaData(Date date) {

	AutorizzazioniFilter filter = new AutorizzazioniFilter();
	filter.setDallaDataScadenza(date);
	List<Autorizzazioni> autorizzazionis = this.findByAutorizzazioniDehorsBeforeData();
	for (Autorizzazioni autorizzazioni : autorizzazionis) {
	    log.debug("updateCessaAutorizzazioniScaduteDallaData# Cesso l'autorizzazione {}[{}] scaduta il {} ",
		    new Object[] { autorizzazioni.getAutoriznumero(), autorizzazioni.getId().getCodice(), autorizzazioni.getDatascadenza() });
	    autorizzazioni.setDataCessazione(autorizzazioni.getDatascadenza());
	    autorizzazioni.setFlagAttiva(false);
	    DehorsCfg cfg = dehorsCfgService.findByTipologiaregistro(autorizzazioni.getTipologiaregistro().getId().getCodice());
	    if (EntityUtils.getNestedProperty(cfg, "id.codice") != null) {
		autorizzazioni.setConcessionicausaliByFkAutConccausCess(cfg.getConcessionicausali());
	    }
	    log.debug(
		    "updateCessaAutorizzazioniScaduteDallaData# Metto il flag cessata sui record della tabella DehorsMqIstanze per l'autorizzazione cessata {}[{}] ",
		    new Object[] { autorizzazioni.getAutoriznumero(), autorizzazioni.getId().getCodice() });
	    DehorsMqIstanze dehorsMqIstanze = dehorsMqIstanzeService.findAttiveByAutorizzazione(autorizzazioni.getId().getCodice());
	    if (EntityUtils.getNestedProperty(dehorsMqIstanze, "id.codice") != null) {
		dehorsMqIstanze.setCessata(true);
		dehorsMqIstanzeService.update(dehorsMqIstanze);
	    }
	}
    }

    @Override
    public void updateCessaAutorizzazioniDehorsScadute() {

	this.updateCessaAutorizzazioniDehorsScaduteDallaData(new Date());
    }

    @Override
    public List<Autorizzazioni> findByAutorizzazioniDehorsBeforeData(Date date) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.isNotNull("id.codice", "tipologiaregistro.dehorsCfgs"));
	fr.addFilterField(FilterUtils.smaller("datascadenza", date, Date.class));
	filterTable.addRestriction(fr);
	return autorizzazioniDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Autorizzazioni> findByAutorizzazioniDehorsBeforeData() {

	return this.findByAutorizzazioniDehorsBeforeData(new Date());
    }

    @Override
    public int countByIstanza(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("countByIstanza: il parametro codiceIstanza e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanza = new FilterRestriction();
	istanza.addFilterField(FilterUtils.equals("istanzeId", codiceIstanza, Integer.class));
	filterTable.addRestriction(istanza);
	int count = autorizzazioniDAO.countRecord(filterTable);
	return count;
    }

    @Override
    public List<Autorizzazioni> findByIstanza(Integer codice) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "istanza", Integer.class));
	filterTable.addRestriction(fr);
	List<Autorizzazioni> list = autorizzazioniDAO.findByFilterTable(filterTable);
	return list;
    }

    @Override
    public int countByTipologiaRegistri(Integer codiceRegistro) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipologiaregistroId", codiceRegistro, Integer.class));
	filterTable.addRestriction(fr);
	return autorizzazioniDAO.countRecord(filterTable);
    }

    @Override
    public void updateScambiaPosteggio(Integer codiceConcPartenza, Integer codiceConcDestinazione, Integer codiceCausaleCessazione,
	    Integer codiceCausaleAcquisizione, Integer codicePosteggioDestinazione) {

	if (codiceConcPartenza.equals(codiceConcDestinazione)) {
	    // Rilancia errore
	    throw new BusinessValidationException("Le concessioni non possono essere le stesse");
	}
	Concessionicausali causaleAcquisizione = concessionicausaliService.findById(new PkId(codiceCausaleAcquisizione));
	AutorizzazioniConcessioni autConcPartenza = autorizzazioniConcessioniService.findById(new PkId(codiceConcPartenza));
	if (codiceConcDestinazione != null) {
	    //		VERSO POSTO OCCUPATO
	    //		SE CI SONO PRESENZE, BLOCCA ED EVENTUALMENTE L'OPERATORE CANCELLA LE REGISTRAZIONI A PARTIRE DALLA DATA DI SCAMBIO
	    //		SE NON CI SONO PRESENZE PROCEDE COME ORA	    
	    //		IL BLOCCO DEVE VERIFICARSI NON SOLO PER IL MITTENTE DELLO SCAMBIO POSTEGGIO MA ANCHE SE IL DESTINATARIO DELLO SCAMBIO POSTEGGIO HA PRESENZE NEL FUTURO
	    AutorizzazioniConcessioni autConcDestinazione = autorizzazioniConcessioniService.findById(new PkId(codiceConcDestinazione));
	    log.debug(
		    "updateScambiaPosteggio#CASO 1: Scambio tra posteggi occupati. Conc. posteggio partenza : {},Conc. posteggio destinazione : {} ",
		    autConcPartenza.getAutorizzazioniByFkAutconcAutatt().getAutoriznumero(),
		    autConcDestinazione.getAutorizzazioniByFkAutconcAutatt().getAutoriznumero());
	    log.debug(
		    "updateScambiaPosteggio# Genero riga subentro per concessione posteggio di partenza e l'eventuale riga di subentro dell'autorizzazione collegata");
	    ModificheScambioPosteggioHelper presenzeDaModificarePartenza = insertSubentriConcessioneAndAutorizzazioneCollegataPerScambioPosteggio(
		    autConcPartenza, codiceCausaleCessazione, new Date());
	    log.debug(
		    "updateScambiaPosteggio# Genero riga subentro per concessione posteggio di destinazione e l'eventuale riga di subentro dell'autorizzazione collegata");
	    ModificheScambioPosteggioHelper presenzeDaModificareDest = insertSubentriConcessioneAndAutorizzazioneCollegataPerScambioPosteggio(
		    autConcDestinazione, codiceCausaleCessazione, new Date());
	    verificaErroriScambioPosteggi(presenzeDaModificareDest, presenzeDaModificarePartenza);
	    MercatiD posteggioPartenza = autConcPartenza.getMercatiD();
	    MercatiD posteggioDestinazione = autConcDestinazione.getMercatiD();
	    // Scambio riferimenti codice posteggio
	    autConcPartenza.setMercatiD(posteggioDestinazione);
	    autConcDestinazione.setMercatiD(posteggioPartenza);
	    // Cambio la causale di acquisiazione presa dal form dello scambio posteggio
	    autConcPartenza.getAutorizzazioniByFkAutconcAutatt().setConcessionicausaliByFkAutConccausAcq(causaleAcquisizione);
	    autConcDestinazione.getAutorizzazioniByFkAutconcAutatt().setConcessionicausaliByFkAutConccausAcq(causaleAcquisizione);
	    autorizzazioniConcessioniService.update(autConcPartenza);
	    autorizzazioniConcessioniService.update(autConcDestinazione);
	    aggiornaPresenzeConcessionarioPerScambioPosteggio(presenzeDaModificarePartenza, autConcDestinazione.getAutorizzazioniByFkAutconcAutatt());
	    aggiornaPresenzeConcessionarioPerScambioPosteggio(presenzeDaModificareDest, autConcPartenza.getAutorizzazioniByFkAutconcAutatt());
	} else {
	    //		VERSO POSTO LIBERO
	    //		SE CI SONO PRESENZE, BLOCCA ED EVENTUALMENTE L'OPERATORE CANCELLA LE REGISTRAZIONI A PARTIRE DALLA DATA DI SCAMBIO
	    //		SE NON CI SONO PRESENZE PROCEDE COME ORA
	    log.debug("updateScambiaPosteggio#CASO 2: Scambio su posteggio libero. Conc. posteggio partenza : {}",
		    autConcPartenza.getAutorizzazioniByFkAutconcAutatt().getAutoriznumero());
	    log.debug(
		    "updateScambiaPosteggio# Genero riga subentro per concessione posteggio di partenza e l'eventuale riga di subentro dell'autorizzazione collegata");
	    ModificheScambioPosteggioHelper presenzeDaModificare = insertSubentriConcessioneAndAutorizzazioneCollegataPerScambioPosteggio(
		    autConcPartenza, codiceCausaleCessazione, new Date());
	    verificaErroriScambioPosteggi(presenzeDaModificare);
	    MercatiD posteggioDestinazione = mercatiDService.findById(new PkId(codicePosteggioDestinazione));
	    autConcPartenza.setMercatiD(posteggioDestinazione);
	    // Cambio la causale di acquisiazione presa dal form dello scambio posteggio
	    autConcPartenza.getAutorizzazioniByFkAutconcAutatt().setConcessionicausaliByFkAutConccausAcq(causaleAcquisizione);
	    autorizzazioniConcessioniService.update(autConcPartenza);
	    aggiornaPresenzeConcessionarioPerScambioPosteggio(presenzeDaModificare, null);
	}
    }

    private void verificaErroriScambioPosteggi(ModificheScambioPosteggioHelper... presenzeDaModificare) {

	StringBuilder errori = new StringBuilder();
	boolean errore = false;
	for (ModificheScambioPosteggioHelper s : presenzeDaModificare) {
	    if (s.isErrore()) {
		errore = true;
		errori.append("<div>").append(s.getErrori()).append("</div>");
	    }
	}
	if (errore) {
	    throw new BusinessValidationException(errori.toString());
	}
    }

    private void aggiornaPresenzeConcessionarioPerScambioPosteggio(ModificheScambioPosteggioHelper presenzeDaModificare,
	    Autorizzazioni autCheSostituisce) {

	for (Integer idPresenza : presenzeDaModificare.getPresenzeDaModificare()) {
	    MercatipresenzeD p = mercatipresenzeDService.findById(new PkId(idPresenza));
	    p.setAutorizzazioneConcessionarioAssente(autCheSostituisce);
	    Anagrafe concessionario = null;
	    if (autCheSostituisce != null) {
		concessionario = autCheSostituisce.getOccupante();
	    }
	    p.setConcessionario(concessionario);
	    mercatipresenzeDService.update(p);
	}
    }

    /***
     * Torna la lista degli identificativi delle presenze per le quali modificare autconcessionario e
     * codiceconcessionario. Sono quelle dove AUT_CONCESSIONARIO= autConc ed effettivamente non è stata presa la
     * presenza quindi con FK_AUTORIZZAZIONI_ID null o FK_AUTORIZZAZIONI_ID<> AUT_CONCESSIONARIO
     * 
     * @param autConc
     * @param codiceCausaleCessazione
     * @param dataCessazione
     * @return
     */
    private ModificheScambioPosteggioHelper insertSubentriConcessioneAndAutorizzazioneCollegataPerScambioPosteggio(AutorizzazioniConcessioni autConc,
	    Integer codiceCausaleCessazione, Date dataCessazione) {

	ModificheScambioPosteggioHelper presenzeDaModificare = ModificheScambioPosteggioHelper.empty();
	int presenzeFatte = mercatipresenzeDService.countPresenzeConcessionarioDallaData(autConc, dataCessazione);
	if (presenzeFatte > 0) {
	    // Rilancia errore
	    presenzeDaModificare = presenzeDaModificarePerScambioPosteggio(autConc, dataCessazione, presenzeFatte);
	}
	log.debug("insertSubentriConcesioneAndAutorizzazioneCollegata# Recupero la causale di cessazione con codice {}", codiceCausaleCessazione);
	Concessionicausali causaleCessazione = concessionicausaliService.findById(new PkId(codiceCausaleCessazione));
	log.debug("insertSubentriConcesioneAndAutorizzazioneCollegata# Populate riga subentro per concessione {}[{}]",
		autConc.getTransientEstremiConcessione(), autConc.getId().getCodice());
	// Creo questi due oggetti per usare il metodo già esistente per creare un subentro in modo che qualsiasi modifica
	// venga riportata in entrambe le funzionalità : quella di subentro e quella scambio posteggi (che usa i subentri)
	Autorizzazioni aut = autConc.getAutorizzazioniByFkAutconcAutatt();
	AutorizzazioniHelper autH = new AutorizzazioniHelper();
	autH.setConcessione(autConc);
	AutorizzazioniSubentri subentrAutConc = getAutorizzazioneSubentro(aut, causaleCessazione, dataCessazione);
	log.debug("updateScambiaPosteggio# Inserisco il subentro per concessione");
	autorizzazioniSubentriService.insert(subentrAutConc);
	log.debug("insertSubentriConcesioneAndAutorizzazioneCollegata# Verifico se la concessione ha un' autorizzazione collegata");
	AutorizzazioniSubentri autorizzazioniSubentriByFkAutconcAutcoll = null;
	// autorizzazioneCollegata
	Autorizzazioni autColl = autConc.getAutorizzazioniByFkAutconcAutcoll();
	if (autColl != null && autColl.getId() != null && autColl.getId().getCodice() != null) {
	    autH = autH.getAutorizzazioneCollegataHelper();
	    if (autH == null) {
		autH = new AutorizzazioniHelper();
		autH.setAutorizzazione(autColl);
	    }
	    validateInsertSubentro(autH);
	    // inserisco l'aut collegata in subentri
	    autorizzazioniSubentriByFkAutconcAutcoll = getAutorizzazioneSubentro(autColl, causaleCessazione, dataCessazione);
	    autorizzazioniSubentriService.insert(autorizzazioniSubentriByFkAutconcAutcoll);
	}
	AutorizzazioniSubentriConc entity = new AutorizzazioniSubentriConc();
	entity.setAutorizzazioniSubentri(subentrAutConc);
	entity.setAutorizzazioniByFkAutconcAutatt(aut);
	entity.setAutorizzazioniSubentriByFkAutconcAutcoll(autorizzazioniSubentriByFkAutconcAutcoll);
	entity.setConcessionitipi(autConc.getConcessionitipi());
	entity.setMercati(autConc.getMercati());
	entity.setMercatiUso(autConc.getMercatiUso());
	entity.setMercatiD(autConc.getMercatiD());
	entity.setStagionalea(autConc.getStagionalea());
	entity.setStagionaleda(autConc.getStagionaleda());
	autorizzazioniSubentriConcService.insert(entity);
	return presenzeDaModificare;
    }

    private ModificheScambioPosteggioHelper presenzeDaModificarePerScambioPosteggio(AutorizzazioniConcessioni autConc, Date dataCessazione,
	    int presenzeFatte) {

	if (presenzeFatte == 0) {
	    return ModificheScambioPosteggioHelper.empty();
	}
	List<Integer> ret = new ArrayList<Integer>(0);
	List<MercatipresenzeD> presenzeConErrore = new ArrayList<MercatipresenzeD>();
	boolean isErrore = false;
	for (MercatipresenzeD p : mercatipresenzeDService.findPresenzeConcessionarioDallaData(autConc, dataCessazione, 0, presenzeFatte)) {
	    //
	    if (p.getAutorizzazioni() != null
		    && p.getAutorizzazioni().getId().getCodice().equals(autConc.getAutorizzazioniByFkAutconcAutatt().getId().getCodice())) {
		// il concessionario ha preso la presenza e non posso modificare lo deve fare l'operatore da interfaccia segno come errore
		presenzeConErrore.add(p);
		isErrore = true;
	    } else {
		ret.add(p.getId().getCodice());
	    }
	}
	return new ModificheScambioPosteggioHelper(ret, presenzeConErrore, autConc, dataCessazione, isErrore);
    }

    @Override
    public List<AutorizzazioniConcessioniRestHelper> findByCodiceFiscaleAnagrafe(String cf, Integer firstResult, Integer maxResults) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction a = new FilterRestriction();
	a.addFilterField(FilterUtils.equalsIgnoreCase("codicefiscale", cf, "anagrafe"));
	ft.addRestriction(a);
	ft.addOrder(FilterUtils.orderAsc("autorizdata"));
	List<Autorizzazioni> auts = autorizzazioniDAO.findByFilterTable(ft, firstResult, maxResults);
	List<AutorizzazioniConcessioniRestHelper> ret = new ArrayList<AutorizzazioniConcessioniRestHelper>();
	for (Autorizzazioni autorizzazioni : auts) {
	    ret.addAll(newAutorizzazioniHelper(autorizzazioni));
	}
	return ret;
    }

    @Override
    public List<AutorizzazioniConcessioniRestHelper> newAutorizzazioniHelper(Autorizzazioni aut) {

	List<AutorizzazioniConcessioniRestHelper> result = new ArrayList<AutorizzazioniConcessioniRestHelper>();
	List<AutorizzazioniConcessioni> concs = autorizzazioniConcessioniService.findByAutorizzazioneAttuale(aut.getId().getCodice());
	if (concs.size() > 0) {
	    for (AutorizzazioniConcessioni conc : concs) {
		AutorizzazioniConcessioniRestHelper ret = new AutorizzazioniConcessioniRestHelper();
		VwEntilocali autorizcomune = aut.getAutorizcomune();
		if (autorizcomune != null) {
		    ret.setComune(newNVBean(autorizcomune.getComune(), autorizcomune.getCodicecomune()));
		}
		ret.setAutorizzataDa(aut.getAutorizresponsabile());
		ret.setDataAutorizzazione(aut.getAutorizdata());
		ret.setId(aut.getId().getCodice());
		ret.setNumero(aut.getAutoriznumero());
		if (aut.getTipologiaregistro() != null) {
		    ret.setRegistro(
			    newNVBean(aut.getTipologiaregistro().getTrDescrizione(), aut.getTipologiaregistro().getId().getCodice().toString()));
		}
		ret.setConcessione(true);
		if (conc.getMercati() != null) {
		    ret.setMercato(newNVBean(conc.getMercati().getDescrizione(), conc.getMercati().getId().getCodice().toString()));
		}
		if (conc.getMercatiUso() != null) {
		    ret.setGiorno(newNVBean(conc.getMercatiUso().getDescrizione(), conc.getMercatiUso().getId().getCodice().toString()));
		}
		if (conc.getMercatiD() != null) {
		    ret.setPosteggio(newNVBean(conc.getMercatiD().getCodiceposteggio(), conc.getMercatiD().getId().getCodice().toString()));
		}
		if (conc.getConcessionitipi() != null) {
		    ret.setTipoConcessione(conc.getConcessionitipi().getDescrizione());
		}
		result.add(ret);
	    }
	} else {
	    AutorizzazioniConcessioniRestHelper ret = new AutorizzazioniConcessioniRestHelper();
	    VwEntilocali autorizcomune = aut.getAutorizcomune();
	    if (autorizcomune != null) {
		ret.setComune(newNVBean(autorizcomune.getComune(), autorizcomune.getCodicecomune()));
	    }
	    ret.setAutorizzataDa(aut.getAutorizresponsabile());
	    ret.setDataAutorizzazione(aut.getAutorizdata());
	    ret.setId(aut.getId().getCodice());
	    ret.setNumero(aut.getAutoriznumero());
	    if (aut.getTipologiaregistro() != null) {
		ret.setRegistro(newNVBean(aut.getTipologiaregistro().getTrDescrizione(), aut.getTipologiaregistro().getId().getCodice().toString()));
	    }
	    result.add(ret);
	}
	return result;
    }

    protected CodiceDescrizioneBean newNVBean(String descrizione, String codice) {

	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	result.setDescrizione(descrizione);
	result.setCodice(codice);
	return result;
    }

    @Override
    public List<AutorizzazioneSpuntistaHelper> findAutorizzazioniSpuntisti(Integer codiceMercato, Integer codiceUso, Integer idGiornataRiferimento) {

	return autorizzazioniDAO.findAutorizzazioniObjSpuntisti(codiceMercato, codiceUso, idGiornataRiferimento);
    }

    @Override
    public List<AutorizzazioniRestHelper> findRestHelper(Set<Integer> auts, Integer idGiornata, Integer codiceMercato, Integer codiceUso) {

	return autorizzazioniDAO.findRestHelper(auts, idGiornata, codiceMercato, codiceUso);
    }

    @Override
    public List<CodiceDescrizioneBean> findAttivitaInAutorizzazioni() {

	return autorizzazioniDAO.findAttivitaInAutorizzazioni();
    }

    @Override
    public List<AutorizzazioniRestHelper> findAnagraficheConAutorizzazione(String testo, Integer firstResult, Integer maxResults) {

	return autorizzazioniDAO.findAnagraficheConAutorizzazione(testo, firstResult, maxResults);
    }

    @Override
    public void updateNoteAutorizzazione(Integer idAutorizzazione, String note) {

	int length = 4000;
	if (StringUtils.isNotBlank(note) && note.length() > length) {
	    throw new RuntimeException("Il campo Note accetta solamente " + length + " caratteri");
	}
	Autorizzazioni entity = autorizzazioniDAO.findById(new PkId(idAutorizzazione));
	entity.setNote(note);
	autorizzazioniDAO.update(entity);
    }

    @Override
    public Autorizzazioni findByNumeroEDataEAzienda(String numeroAutorizzazione, Date dataAutorizzazione, Integer codiceAnagrafeAzienda) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction a = new FilterRestriction();
	a.addFilterField(FilterUtils.equalsIgnoreCase("autoriznumero", numeroAutorizzazione));
	a.addFilterField(FilterUtils.equals("autorizdata", dataAutorizzazione, Date.class));
	if (null != codiceAnagrafeAzienda) {
	    a.addFilterField(FilterUtils.equals("anagrafeId", codiceAnagrafeAzienda, Integer.class));
	} // altrimenti lo verifico a posteriori
	ft.addRestriction(a);
	List<Autorizzazioni> auts = autorizzazioniDAO.findByFilterTable(ft, 0, 2);
	int size = auts.size();
	if (size == 1) {
	    return auts.get(0);
	} else if (size == 0) {
	    throw new RuntimeException("Non sono state trovate Autorizzazioni con i riferimenti numero:" +
		    numeroAutorizzazione +
		    ", data: " +
		    Utilities.formatDate(dataAutorizzazione, false));
	} else {
	    throw new RuntimeException("Sono state trovate più Autorizzazioni con i riferimenti numero:" +
		    numeroAutorizzazione +
		    ", data: " +
		    Utilities.formatDate(dataAutorizzazione, false));
	}
    }

    private Autorizzazioni recuperaAutorizzazione(Integer codicePraticaOrigine) {

	Autorizzazioni a = null;
	Istanze i = istanzeService.findById(new PkId(codicePraticaOrigine));
	if (i == null) {
	    throw new RuntimeException("La pratica non è collegata ad altre pratiche");
	}
	List<Istanzecollegate> istanzaPadre = istanzecollegateService.findIstanzeCollegateByIstanza(i);
	int size = istanzaPadre.size();
	if (size == 0) {
	    throw new RuntimeException("La pratica non è collegata ad altre pratiche");
	} else if (size > 1) {
	    throw new RuntimeException("La pratica è collegata a più pratiche");
	}
	Istanze istanza = istanzaPadre.get(0).getIstanzaDacollegare();
	if (istanza == null) {
	    throw new RuntimeException("La pratica non è collegata ad altre pratiche");
	}
	List<Autorizzazioni> auts = this.findByIstanza(istanza.getId().getCodice());
	size = auts.size();
	if (size == 0) {
	    throw new RuntimeException("La pratica " + istanza.getId() + " non ha autorizzazioni collegate");
	} else if (size > 1) {
	    throw new RuntimeException("La pratica " + istanza.getId() + " ha più autorizzaioni collegate");
	}
	a = this.findById(new PkId(auts.get(0).getId().getCodice()));
	return a;
    }

    @Override
    public CodiceDescrizioneBean aggiornaAutorizzazioneCollegataProroga(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine) {

	// Modificare la data dell'autorizzazione collegata usando la colonna durata_autorizzazione di tipologia registri. 
	// Riscontro all'utente dell’operazione effettuata. Scalare il contatore N_pror_rimaste delle proroghe dall’autorizzazione. 
	// Nel caso che non ci siano rimaste proroghe. Messaggio di errore all’utente e rollback dell’operazione.
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	result.setCodice("OK");
	Autorizzazioni a = recuperaAutorizzazione(codicePraticaOrigine);
	result.setDescrizione("Proroga autorizzata per l'autorizzazione " + a.getTransientEstremiAut());
	int numProrogherimaste = a.getNumProrogheRimaste() == null ? 0 : a.getNumProrogheRimaste().intValue();
	if (numProrogherimaste < 1) {
	    result.setCodice("KO");
	    result.setDescrizione("Non sono rimaste proroghe per l'autorizzazione " + a.getTransientEstremiAut());
	    return result;
	}
	Tipologiaregistri tr = a.getTipologiaregistro();
	String aggiornaDuration = tr.getDurataAutorizzazione();
	if (StringUtils.isNotBlank(aggiornaDuration)) {
	    Date scadenza = Utilities.addDuration(a.getDatascadenza(), aggiornaDuration);
	    a.setDatascadenza(scadenza);
	    numProrogherimaste = numProrogherimaste - 1;
	    a.setNumPreavvisiStorico(a.getNumPreavvisiRimasti());
	    //	     Fix: Ticket#2021091010000282 — contatore viaggi proroga
	    //	     nel caso di autorizzazioni all'atto del rilascio dell'autorizzazione alla proroga 
	    //	     (per le autorizzazioni multple - nel nostro casso non abbiamo uin flag che separa le aut singole da quelle multiple e periodiche - ) 
	    //	     il contatore non dovrebbe essere reimpostato su zero ma riportare i viaggi
	    //	     residui dell'autorizzazione principale
	    if (tr.getNumeroPreavvisi() == null) {
		// periodiche
		a.setNumPreavvisiRimasti(0);
	    } else {
		// multiple e singole //
		a.setNumPreavvisiRimasti(a.getNumPreavvisiRimasti());
	    }
	    a.setNumProrogheRimaste(numProrogherimaste);
	    autorizzazioniDAO.update(a);
	}
	return result;
    }

    @Override
    public CodiceDescrizioneBean aggiornaAutorizzazioneCollegataPreavviso(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine) {

	// Scalare contatore se non nullo o errore in caso che il contatore N_PREAVV_RIMASTI sia a zero e rollback dell’operazione.
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	result.setCodice("OK");
	Autorizzazioni a = recuperaAutorizzazione(codicePraticaOrigine);
	if (a.getTipologiaregistro() != null && a.getTipologiaregistro().getNumeroPreavvisi() != null) {
	    // FIX SE IL REGISTRO GESTISCE I PREAVVISI ESEGUO IL CODICE ALTRIMENTI NO
	    result.setDescrizione("Preavviso autorizzato per l'autorizzazione " + a.getTransientEstremiAut());
	    int numPreavvisiRimasti = a.getNumPreavvisiRimasti() == null ? 0 : a.getNumPreavvisiRimasti().intValue();
	    if (numPreavvisiRimasti < 1) {
		result.setCodice("KO");
		result.setDescrizione("Non sono rimasti preavvisi per l'autorizzazione " + a.getTransientEstremiAut());
		return result;
	    }
	    numPreavvisiRimasti = numPreavvisiRimasti - 1;
	    a.setNumPreavvisiRimasti(numPreavvisiRimasti);
	    autorizzazioniDAO.update(a);
	}
	return result;
    }

    @Override
    public CodiceDescrizioneBean aggiornaAutorizzazioneCollegataRinnovo(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine) {

	// Modificare la data di scadenza dell’autorizzazione collegata usando la colonna durata_autorizzazione di tipologia registri. 
	// Riscontro all’utente dell’operazione effettuata. Scalare contatore se non nullo o errore in caso che il contatore N_RINN_RIMASTI 
	// sia a zero e rollback dell’operazione.
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	result.setCodice("OK");
	Autorizzazioni a = recuperaAutorizzazione(codicePraticaOrigine);
	result.setDescrizione("Rinnovo autorizzato per l'autorizzazione " + a.getTransientEstremiAut());
	int numrinnovirimasti = a.getNumRinnoviRimasti() == null ? 0 : a.getNumRinnoviRimasti().intValue();
	if (numrinnovirimasti < 1) {
	    result.setCodice("KO");
	    result.setDescrizione("Non sono rimasti rinnovi da effettuare per l'autorizzazione " + a.getTransientEstremiAut());
	    return result;
	}
	Tipologiaregistri tr = a.getTipologiaregistro();
	String aggiornaDuration = tr.getDurataAutorizzazione();
	if (StringUtils.isNotBlank(aggiornaDuration)) {
	    if (a.getDatascadenza() == null) {
		throw new RuntimeException("Data di scadenza non impostata per l'autorizzazione");
	    }
	    Date scadenza = Utilities.addDuration(a.getDatascadenza(), aggiornaDuration);
	    a.setDatascadenza(scadenza);
	    numrinnovirimasti = numrinnovirimasti - 1;
	    a.setNumRinnoviRimasti(numrinnovirimasti);
	    autorizzazioniDAO.update(a);
	}
	return result;
    }

    @Override
    public CodiceDescrizioneBean aggiornaAutorizzazioneCollegataModifica(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine) {

	// Modificare il codice istanza legato all’autorizzazione con quello dell’istanza dove viene eseguito il movimento
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	result.setCodice("OK");
	Autorizzazioni a = recuperaAutorizzazione(codicePraticaOrigine);
	result.setDescrizione("Modifica autorizzata per l'autorizzazione " + a.getTransientEstremiAut());
	Istanze iCheHaAutorizzazione = istanzeService.findById(new PkId(a.getIstanza().getId().getCodice()));
	// Istanze istanzaModifica = istanzeService.findById(new PkId(codicePraticaOrigine));
	List<Istanzedyn2dati> datiDaCopiare = istanzedyn2datiService.findByIstanza(new PkId(codicePraticaOrigine));
	Set<Integer> modelliComuni = new HashSet<Integer>();
	List<Istanzedyn2modellit> modelliIstanzaAut = istanzedyn2modellitService.findByIstanza(new PkId(a.getIstanza().getId().getCodice()));
	List<Istanzedyn2modellit> modelliIstanzaModifica = istanzedyn2modellitService.findByIstanza(new PkId(codicePraticaOrigine));
	for (Istanzedyn2modellit istanzedyn2modellit : modelliIstanzaAut) {
	    Integer codiceModello = istanzedyn2modellit.getId().getFkD2mtId();
	    for (Istanzedyn2modellit istanzedyn2modellit2 : modelliIstanzaModifica) {
		if (istanzedyn2modellit2.getId().getFkD2mtId().equals(codiceModello)) {
		    modelliComuni.add(codiceModello);
		    break;
		}
	    }
	}
	// copio in storico i dati dei modelli in comune
	// elimino i dati 
	// aggiorno i dati  
	Set<Integer> codiciCampiDaCopiare = new HashSet<Integer>();
	if (!modelliComuni.isEmpty()) {
	    int progressivoVersione = istanzedyn2modellitStoricoService.calcolaProgressivoVersione(a.getIstanza().getId().getCodice());
	    for (Integer codiceModello : modelliComuni) {
		Dyn2Modellit d2mt = dyn2ModellitService.findById(new PkId(codiceModello));
		Istanzedyn2modellitStorico entity = new Istanzedyn2modellitStorico();
		Istanzedyn2modellitStoricoId idmdts = new Istanzedyn2modellitStoricoId(progressivoVersione, a.getIstanza().getId().getCodice(),
			codiceModello);
		entity.setId(idmdts);
		entity.setDataversione(Calendar.getInstance().getTime());
		entity.setDyn2Modellit(d2mt);
		entity.setIstanze(iCheHaAutorizzazione);
		istanzedyn2modellitStoricoService.insert(entity);
		List<Dyn2Modellid> righeModello = dyn2ModellidService.findRigheModello(codiceModello);
		for (Dyn2Modellid d2md : righeModello) {
		    if (d2md.getDyn2Campi() != null) {
			codiciCampiDaCopiare.add(d2md.getDyn2Campi().getId().getCodice());
		    }
		}
		// copio in storico i dati dei modelli in comune
		for (Integer codiceCampo : codiciCampiDaCopiare) {
		    List<Istanzedyn2datiDTO> datis = istanzedyn2datiService.findDTOByIstanzaAndDyn2Campi(a.getIstanza().getId().getCodice(),
			    codiceCampo);
		    for (Istanzedyn2datiDTO iddto : datis) {
			Istanzedyn2datiStorico copiaStorico = new Istanzedyn2datiStorico();
			Istanzedyn2datiStoricoId id = new Istanzedyn2datiStoricoId(progressivoVersione, a.getIstanza().getId().getCodice(),
				codiceModello, iddto.getId().getFkD2cId(), iddto.getId().getIndice(), iddto.getId().getIndiceMolteplicita());
			copiaStorico.setId(id);
			copiaStorico.setValore(iddto.getValore());
			istanzedyn2datiStoricoService.insert(copiaStorico);
		    }
		}
	    }
	    // elimino i dati 
	    for (Integer codiceCampo : codiciCampiDaCopiare) {
		List<Istanzedyn2dati> datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(a.getIstanza().getId().getCodice(), codiceCampo);
		for (Istanzedyn2dati istanzedyn2dati : datis) {
		    istanzedyn2datiService.delete(istanzedyn2dati);
		}
	    }
	}
	// aggiorno i dati  
	for (Istanzedyn2dati istanzedyn2dati : datiDaCopiare) {
	    Istanzedyn2dati copia = new Istanzedyn2dati();
	    Istanzedyn2datiId id = new Istanzedyn2datiId(a.getIstanza().getId().getCodice(), istanzedyn2dati.getId().getFkD2cId(),
		    istanzedyn2dati.getId().getIndice(), istanzedyn2dati.getId().getIndiceMolteplicita());
	    copia.setDyn2Campi(istanzedyn2dati.getDyn2Campi());
	    copia.setIstanza(iCheHaAutorizzazione);
	    copia.setValore(istanzedyn2dati.getValore());
	    copia.setValoredecodificato(istanzedyn2dati.getValoredecodificato());
	    copia.setId(id);
	    istanzedyn2datiService.insert(copia);
	}
	return result;
    }

    @Autowired
    private Istanzedyn2modellitService istanzedyn2modellitService;
    @Autowired
    private Dyn2ModellidService dyn2ModellidService;

    @Override
    public boolean overrideUniqueConstraint() {

	return this.comportamentiMercatiService.autorizUniqueNumeroComune();
    }

    @Override
    public Autorizzazioni findByNumeroAndComune(String autoriznumero, String codicecomune) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("autoriznumero", autoriznumero));
	fr.addFilterField(FilterUtils.equals("codicecomune", codicecomune, "autorizcomune", String.class));
	fr.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), "tipologiaregistro", String.class));
	ft.addRestriction(fr);
	List<Autorizzazioni> list = autorizzazioniDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public boolean existAutorizzazioniInIstanzeCollegate(Integer codiceIstanza) {

	boolean result = false;
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	List<Autorizzazioni> auts = autorizzazioniDAO.findByIstanza(istanza);
	int size = auts.size();
	if (size == 0) {
	    // 1. risalgo alle istanze collegate padre
	    IstanzecollegateHelper istanzeCollegateHelperPrecedenti = istanzecollegateService.getSchemaPrecedentiAndSuccessive(istanza);
	    List<Istanze> istanzeCollegatePrecedenti = istanzeCollegateHelperPrecedenti.getListaIstanzePrecedenti();
	    if (!istanzeCollegatePrecedenti.isEmpty()) {
		for (Istanze istanzacollegata : istanzeCollegatePrecedenti) {
		    // 2. verifico chi di queste ha una autorizzazione e se ce ne è qualcuna esco su
		    List<Autorizzazioni> autsOfIstanza = autorizzazioniDAO.findByIstanza(istanzacollegata);
		    int size2 = 0;
		    //result = false;
		    if (!autsOfIstanza.isEmpty()) {
			size2 = autsOfIstanza.size();
		    }
		    if (size2 >= 1) {
			result = true;
			break;
		    }
		}
	    }
	} else if (size >= 1) {
	    result = true;
	}
	return result;
    }

    @Override
    public CodiceDescrizioneBean rollbackAutorizzazioneCollegataModifica(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine) {

	throw new NotImplementedException("rollbackAutorizzazioneCollegataModifica non implementato");
    }

    @Override
    public CodiceDescrizioneBean rollbackAutorizzazioneCollegataPreavviso(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine) {

	// AGGIUNGE DI UNO IL NUMEROPREAVVISI RIMASTI
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	result.setCodice("OK");
	Autorizzazioni a = recuperaAutorizzazione(codicePraticaOrigine);
	if (a.getTipologiaregistro() != null && a.getTipologiaregistro().getNumeroPreavvisi() != null) {
	    // FIX SE IL REGISTRO GESTISCE I PREAVVISI ESEGUO IL CODICE ALTRIMENTI NO
	    result.setDescrizione("Il preavviso autorizzato è stato rimosso dall'autorizzazione " + a.getTransientEstremiAut());
	    int numPreavvisiRimasti = a.getNumPreavvisiRimasti() == null ? 0 : a.getNumPreavvisiRimasti().intValue();
	    numPreavvisiRimasti = numPreavvisiRimasti + 1;
	    a.setNumPreavvisiRimasti(numPreavvisiRimasti);
	    autorizzazioniDAO.update(a);
	}
	return result;
    }

    @Override
    public CodiceDescrizioneBean rollbackAutorizzazioneCollegataRinnovo(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine) {

	throw new NotImplementedException("rollbackAutorizzazioneCollegataModifica non implementato");
    }

    @Override
    public CodiceDescrizioneBean rollbackAutorizzazioneCollegataProroga(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine) {

	// AGGIUNGE DI UNO IL NUMPROROGHE RIMASTE E AGGIORNA LA SCADENZA IN NEGATIVO
	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	result.setCodice("OK");
	Autorizzazioni a = recuperaAutorizzazione(codicePraticaOrigine);
	result.setDescrizione("Rimossa la proroga per l'autorizzazione " + a.getTransientEstremiAut());
	int numProrogherimaste = a.getNumProrogheRimaste() == null ? 0 : a.getNumProrogheRimaste().intValue();
	Tipologiaregistri tr = a.getTipologiaregistro();
	a.setNumProrogheRimaste(++numProrogherimaste);
	String aggiornaDuration = tr.getDurataAutorizzazione();
	if (StringUtils.isNotBlank(aggiornaDuration)) {
	    Date scadenza = Utilities.addDuration(a.getDatascadenza(), "-" + aggiornaDuration);
	    a.setDatascadenza(scadenza);
	    a.setNumPreavvisiRimasti(a.getNumPreavvisiStorico() == null ? 0 : a.getNumPreavvisiStorico());
	    a.setNumPreavvisiStorico(null);
	    autorizzazioniDAO.update(a);
	}
	return result;
    }

    @Override
    public List<AutorizzazioniRestHelper> findAutorizzazioniAnagrafiche(Set<Integer> codiciAnagrafe,
	    boolean consideraAncheIlProprietarioTraLeAnagrafiche, RuoloAutorizzazioneEnum ruolo, boolean soloAutorizzazioniAttive) {

	return autorizzazioniDAO.findAutorizzazioniAnagrafiche(codiciAnagrafe, consideraAncheIlProprietarioTraLeAnagrafiche, ruolo,
		soloAutorizzazioniAttive);
    }

    @Override
    public AutorizzazioniRestHelper findAutorizzazioneRestHelper(Integer idAutorizzazione) {

	return autorizzazioniDAO.findAutorizzazioneRestHelper(idAutorizzazione);
    }

    @Override
    public Anagrafe findAnagrafeAutorizzazione(Integer idAutorizzazione) {

	AutorizzazioniCsi autCSI = autorizzazioniCsiService.findByAutorizzazione(idAutorizzazione);
	if (autCSI != null) {
	    if (autCSI.getAnagrafe() != null && autCSI.getAnagrafe().getId() != null && autCSI.getAnagrafe().getId().getCodice() != null) {
		return anagrafeService.findById(new PkId(autCSI.getAnagrafe().getId().getCodice()));
	    }
	}
	Autorizzazioni aut = this.findById(new PkId(idAutorizzazione));
	if (aut != null) {
	    if (aut.getAnagrafe() != null && aut.getAnagrafe().getId() != null && aut.getAnagrafe().getId().getCodice() != null) {
		return anagrafeService.findById(new PkId(aut.getAnagrafe().getId().getCodice()));
	    }
	}
	return null;
    }

    @Override
    public String exportModalitaPentaho(AutorizzazioniExportHelper autorizzazioniExportHelper, Esportazioni esportazioni, Date _data, String email,
	    String contestoExport, boolean isInvioMail) {

	long t1 = System.currentTimeMillis();
	log.debug("exportModalitaPentaho# Start export pentaho, contesto : {}", contestoExport);
	String sessionId = ORMHelper.getToken();
	log.debug("exportModalitaPentaho# Cancello i record su tmp_esportazioni con sessionId: {}", sessionId);
	tmpEsportazioniService.deleteBysessionId(sessionId);
	log.debug("exportModalitaPentaho# Inizio esportazione concessioni. Invio email. {}", isInvioMail);
	autorizzazioniDAO.exportModalitaPentaho(autorizzazioniExportHelper, esportazioni, _data, email, contestoExport, isInvioMail);
	long t2 = System.currentTimeMillis();
	String time = String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(t2 - t1),
		TimeUnit.MILLISECONDS.toSeconds(t2 - t1) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(t2 - t1)));
	log.debug("exportModalitaPentaho# Processo servito in {}", time);
	return sessionId;
    }

    @Override
    public void cessaAutorizzazione(Integer idAutorizzazione, Date dataCessazione, int idCausaleCessazione) {

	this.autorizzazioniDAO.cessaAutorizzazione(idAutorizzazione, dataCessazione, idCausaleCessazione);
    }

    @Override
    public void completaAutorizzazione(Integer codiceAutorizzazione, Boolean completa) {

	try {
	    //1. Pubblicazione evento inizio cambio completamento autorizzazione
	    this.eventPublisher.publishThrowOnFailure(this.getEventoRichiestaBloccoOSblocco(codiceAutorizzazione, completa));
	    //2. Cambio completamento autorizzazione
	    this.autorizzazioniDAO.cambiaBloccoAutorizzazione(codiceAutorizzazione, completa);
	    //3. Pubblicazione evento fine cambio completamento autorizzazione
	    this.eventPublisher.publishThrowOnFailure(this.getEventoBloccoOSblocco(codiceAutorizzazione, completa));
	} catch (EventAbortedException e) {
	    throw new RuntimeException(e.getMessage());
	}
    }

    private IEvent getEventoRichiestaBloccoOSblocco(Integer codiceAutorizzazione, Boolean completa) {

	if (completa) {
	    return new EventoRichiestaBloccoAutorizzazione(codiceAutorizzazione);
	}
	return new EventoRichiestaSbloccoAutorizzazione(codiceAutorizzazione);
    }

    private IEvent getEventoBloccoOSblocco(Integer codiceAutorizzazione, Boolean completa) {

	if (completa) {
	    return new EventoAutorizzazioneBloccata(codiceAutorizzazione);
	}
	return new EventoAutorizzazioneSbloccata(codiceAutorizzazione);
    }

    @Override
    public void aggiornaEstremiAutorizzazioni(Integer codiceAutorizzazione, String numero, Date data) {

	this.autorizzazioniDAO.aggiornaEstremiAutorizzazioni(codiceAutorizzazione, numero, data);
    }

    @Override
    public EsitoModificaOccupante validaModificaOccupante(Integer idAutConc, Integer codiceAnagrafeNuovoOccupante) {

	EsitoModificaOccupante esiti = new EsitoModificaOccupante();
	try {
	    this.eventPublisher.publishAndThrowOnAllSubscriberFailure(new EventoCheckModificaOccupante(idAutConc, codiceAnagrafeNuovoOccupante));
	} catch (EventAbortedException e) {
	    if (e.getEsito() != null) {
		esiti.addEsito(e.getEsito());
	    } else {
		log.error("Errore nella validazione dei subentri", e);
	    }
	}
	return esiti;
    }

    @Override
    public void updateModificaOccupante(ValidazioneOccupanteCommand command) throws EventAbortedException {

	Integer idAutorizzazione = command.getIdAuOConc();
	Autorizzazioni aut = this.findById(new PkId(idAutorizzazione));
	Anagrafe nuovoOccupante = anagrafeService.findById(new PkId(command.getCodiceAnagrafeNuovoOccupante()));
	ModificaOccupanteLogger audtiLogger = new ModificaOccupanteLogger(userSecurityService.getCurrentlyAuthenticatedUserDetails().toString(), aut,
		aut.getOccupante(), nuovoOccupante);
	audtiLogger.log();
	aut.setOccupante(nuovoOccupante);
	autorizzazioniDAO.update(aut);
	try {
	    this.eventPublisher.publishAndThrowOnAllSubscriberFailure(new EventoOccupanteModificato(idAutorizzazione,
		    command.getVecchioOccupante().getId(), command.getCodiceAnagrafeNuovoOccupante(), command.getEsito()));
	} catch (EventAbortedException e) {
	    String messaggio = "Errore nella modifica dell'occcupante " + e.getMessage();
	    audtiLogger.logError(messaggio);
	    log.error(messaggio, e);
	    throw e;
	} finally {
	    audtiLogger.logFineMetodo();
	}
    }

    @Override
    public EsitoModificaDataCessazione validaModificaDataCessazioneSubentro(Integer idAutorizzazioniSubentri, Date nuovaDataCessazione)
	    throws OperazioniSubentriException {

	EsitoModificaDataCessazione esiti = new EsitoModificaDataCessazione();
	try {
	    this.eventPublisher
		    .publishAndThrowOnAllSubscriberFailure(new EventoCheckModificaDataCessazione(idAutorizzazioniSubentri, nuovaDataCessazione));
	} catch (EventAbortedException e) {
	    if (e.getEsito() != null) {
		esiti.addEsito(e.getEsito());
	    } else {
		log.error("Errore nella validazione dei subentri", e);
		throw new OperazioniSubentriException("Errore nella verifica di modifica data cessazione" + e.getMessage(), e);
	    }
	}
	return esiti;
    }

    @Override
    public void updateModificaDataCessazioneSubentro(ValidazioneDataCessazioneSubentroCommand command) throws EventAbortedException {

	AutorizzazioniSubentri subentro = autorizzazioniSubentriService.findById(new PkId(command.getIdAutorizzazioniSubentri()));
	Date dataCessazioneOrig = subentro.getDataCessazione();
	subentro.setDataCessazione(command.getNuovaDataCessazione());
	autorizzazioniSubentriService.update(subentro);
	if (!subentro.getAutSubentrisConcs().isEmpty()) {
	    // Devo Cambiare anche la data di cessazione dell'atto collegato
	    for (AutorizzazioniSubentriConc autSobConc : subentro.getAutSubentrisConcs()) {
		if (autSobConc.getAutorizzazioniSubentriByFkAutconcAutcoll() != null
			&& autSobConc.getAutorizzazioniSubentriByFkAutconcAutcoll().getId() != null
			&& autSobConc.getAutorizzazioniSubentriByFkAutconcAutcoll().getId().getCodice() != null) {
		    AutorizzazioniSubentri subentroDellAutCollegata = autorizzazioniSubentriService
			    .findById(new PkId(autSobConc.getAutorizzazioniSubentriByFkAutconcAutcoll().getId().getCodice()));
		    subentroDellAutCollegata.setDataCessazione(command.getNuovaDataCessazione());
		    autorizzazioniSubentriService.update(subentroDellAutCollegata);
		}
	    }
	}
	this.eventPublisher.publishThrowOnFailure(
		new EventoDataCessazioneSubentroModificata(command.getIdAutorizzazioniSubentri(), dataCessazioneOrig, command.getEsito()));
    }

    @Override
    public EsitoCancellazioneAutOConc validaCancellazioneAutConc(ValidaEliminazioneAutConcCommand cmd)
	    throws OperazioneCancellazioneAutConcException {

	EsitoCancellazioneAutOConc esiti = new EsitoCancellazioneAutOConc();
	Autorizzazioni autConc = autorizzazioniDAO.findById(new PkId(cmd.getIdAutorizzazioni()));
	checkAutorizzazioneIsReadonly(cmd.getCodiceIstanza(), autConc);
	List<AutorizzazioniSubentri> subentris = autorizzazioniSubentriService.findByAutorizzazione(cmd.getIdAutorizzazioni(), 0, 1);
	if (!subentris.isEmpty()) {
	    // nel caso ci siano subentri alla concessione trovo l'ultimo subentro che sarebbe il primo elemento dei
	    // subentri (il set è ordinato per DATA_CESSAZIONE desc)
	    AutorizzazioniSubentri ultimoSubentro = subentris.get(0);
	    try {
		this.eventPublisher.publishThrowOnFailure(new EventoCheckEliminazioneSubentro(ultimoSubentro.getId().getCodice()));
	    } catch (EventAbortedException e) {
		if (e.getEsito() != null) {
		    esiti.addEsito(e.getEsito());
		} else {
		    log.error("Errore nella validazione dei subentri", e);
		    throw new OperazioneCancellazioneAutConcException("Errore nella verifica ddella cancellazione dell'atto " + e.getMessage(), e);
		}
	    }
	}
	return esiti;
    }

    @Override
    public List<AutorizzazioniMercatoSrvBean> findAutorizzazioniMercatoSrvBean(MercatoSrvRequest req) {

	if (req.getAutorizzazione().isEmpty() && req.getCodiceFiscale().isEmpty()) {
	    return new ArrayList<AutorizzazioniMercatoSrvBean>(0);
	}
	return autorizzazioniDAO.findAutorizzazioniMercatoSrvBean(req);
    }

    @Override
    public List<AutorizzazioniComposteSpostaPresenzeDTO> findAutorizzazioniComposteSpostaPresenze(Integer codice, String idComune) {

	return autorizzazioniDAO.findAutorizzazioniComposteSpostaPresenze(codice, idComune);
    }
}
