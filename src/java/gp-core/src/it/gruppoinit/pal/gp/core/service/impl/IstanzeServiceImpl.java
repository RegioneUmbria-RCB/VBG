package it.gruppoinit.pal.gp.core.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.xml.bind.JAXBElement;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.infocamera.schema.sigeproexport.LISTAISTANZE;
import it.gruppoinit.infocamera.schema.sigeproexport.LISTAISTANZE.ISTANZA;
import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.*;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DomandestcHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDaChiudereHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListsDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerInterventiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerProcedimentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiDataComparator;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.SorteggidettaglioDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.CambioInterventoCommand;
import it.gruppoinit.pal.gp.core.domain.web.CambioInterventoCompareHelper;
import it.gruppoinit.pal.gp.core.domain.web.CambioInterventoComparePropertiesHelper;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeOnLineHelper;
import it.gruppoinit.pal.gp.core.domain.web.IstanzePerAttivitaFilter;
import it.gruppoinit.pal.gp.core.domain.web.TracciatoEquitaliaFilter;
import it.gruppoinit.pal.gp.core.domain.web.ValidaEliminazioneAutConcCommand;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoIstanzaCancellata;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoIstanzaModificaAzione;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoIstanzaModificaDataValidita;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.IAttivitaIstanzeService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoCancellazioneAutOConc;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioneCancellazioneAutConcException;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.HelperTypeEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.custom.DettaglioRigaIstanze;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.ICartograficoService;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.ParametriResponse;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.ParametroResponse;
import it.gruppoinit.pal.gp.core.features.istanze.cambiointervento.AuditSpostamentoPratiche;
import it.gruppoinit.pal.gp.core.features.istanze.cambiointervento.metadati.SpostamentoPraticheMetadato;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeareeService;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.features.istanze.eventi.EventoSoggettiIstanzaAggiornati;
import it.gruppoinit.pal.gp.core.features.istanze.eventi.EventoStatoIstanzaModificato;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.IstanzecollegateService;
import it.gruppoinit.pal.gp.core.features.istanze.metadati.IIstanzeMetadatiService;
import it.gruppoinit.pal.gp.core.features.istanze.rest.AggiornaRiferimentiProtocolloIstanzaRequest;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.features.movimenti.rest.AggiornaRiferimentiProtocolloMovimentoRequest;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.features.oneri.calcolo.canoni.IstanzecalcolocanoniTService;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoIstanzaProtocollata;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.destinatari.IndirizzoMailResolver;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.destinatari.TipoDestinatarioEnum;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.AggiornamentoProtocolloException;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.rabbitmq.eventi.EventoInserimentoIstanza;
import it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio.SorteggidettaglioService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AnagrafedocumentiService;
import it.gruppoinit.pal.gp.core.service.Aree2Service;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;
import it.gruppoinit.pal.gp.core.service.AzioniService;
import it.gruppoinit.pal.gp.core.service.CambioInterventoManager;
import it.gruppoinit.pal.gp.core.service.CcIcalcolototService;
import it.gruppoinit.pal.gp.core.service.CdsService;
import it.gruppoinit.pal.gp.core.service.ChiusureistanzaService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.DomandestcService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.FoArconfigurazioneService;
import it.gruppoinit.pal.gp.core.service.GraduatoriedService;
import it.gruppoinit.pal.gp.core.service.GraduatorietService;
import it.gruppoinit.pal.gp.core.service.GruppiIstruttoriRespService;
import it.gruppoinit.pal.gp.core.service.GruppiIstruttoriService;
import it.gruppoinit.pal.gp.core.service.IPostIstanzeInsertCallBack;
import it.gruppoinit.pal.gp.core.service.ImpiantiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiDService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiLogService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiTService;
import it.gruppoinit.pal.gp.core.service.IstanzeRiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeTempisticaService;
import it.gruppoinit.pal.gp.core.service.IstanzeaffissioniService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeattivitaService;
import it.gruppoinit.pal.gp.core.service.IstanzedeleteService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiStoricoService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitStoricoService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.IstanzefidejussioniService;
import it.gruppoinit.pal.gp.core.service.IstanzefrontofficeService;
import it.gruppoinit.pal.gp.core.service.IstanzehummingbirdService;
import it.gruppoinit.pal.gp.core.service.IstanzelavoriTService;
import it.gruppoinit.pal.gp.core.service.IstanzemappaliService;
import it.gruppoinit.pal.gp.core.service.IstanzepeopledService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocureService;
import it.gruppoinit.pal.gp.core.service.IstanzereplicateService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeruoliService;
import it.gruppoinit.pal.gp.core.service.MovimentiContromovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiTempisticaService;
import it.gruppoinit.pal.gp.core.service.NatureProcedureService;
import it.gruppoinit.pal.gp.core.service.OIcalcolototService;
import it.gruppoinit.pal.gp.core.service.OrariaperturatestataService;
import it.gruppoinit.pal.gp.core.service.PecInboxService;
import it.gruppoinit.pal.gp.core.service.PermistanzeService;
import it.gruppoinit.pal.gp.core.service.ProtocolloConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.PuFormatiService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliAssenzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.RuoliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.SpuntistiMercatiService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipiarchivioistanzeService;
import it.gruppoinit.pal.gp.core.service.TipicontromovimentoService;
import it.gruppoinit.pal.gp.core.service.TipimovimentoDisService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureDyn2modellitService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.TipisoggettoService;
import it.gruppoinit.pal.gp.core.service.TipologiaistanzaService;
import it.gruppoinit.pal.gp.core.service.TmpEsportazioniService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.NumeroIstanzaUtilizzatoException;
import it.gruppoinit.pal.gp.core.service.exception.OperazioniAutomaticheException;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniAccessiHelper;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniAccessiOperazioniHelper;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.InserimentoIstanzeFlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.service.helper.NumeroIstanzaComparator;
import it.gruppoinit.pal.gp.core.service.helper.ResponsabiliAssegnazioniHelper;
import it.gruppoinit.pal.gp.core.service.helper.ResponsabiliAssegnazioniHelperBean;
import it.gruppoinit.pal.gp.core.service.helper.TempisticaIstanzaHelper;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;
import it.gruppoinit.pal.gp.core.service.rules.IstanzeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.OperazioniAutomaticheBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.LoggerModificheIstanze;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.LDPWsClient;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;
import it.gruppoinit.pal.gp.core.ws.client.SigeproExportWsClient;
import it.gruppoinit.protocollo.schemas.messages.ArrayOfDatiAnagraficiType;
import it.gruppoinit.protocollo.schemas.messages.DatiAnagraficiType;
import it.gruppoinit.protocollo.schemas.messages.DatiMittentiType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;
import it.gruppoinit.protocollo.schemas.messages.ObjectFactory;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.Parametro;

/**
 * @author Riccardo Bocci
 * 
 */
@Service
public class IstanzeServiceImpl extends BaseServiceImpl<Istanze, PkId> implements IstanzeService {

    private static final Logger log = LoggerFactory.getLogger(IstanzeServiceImpl.class);
    private PecInboxService pecInboxService;
    private CambioInterventoManager cambioInterventoManager;
    private IstanzedeleteService istanzedeleteService;
    private TipicausalioneriService tipicausalioneriService;
    private TmpEsportazioniService tmpEsportazioniService;
    private double pageSize = 100;
    private TipicontromovimentoService tipicontromovimentoService;
    private AmministrazioniService amministrazioniService;
    private GruppiIstruttoriService gruppiIstruttoriService;
    private GruppiIstruttoriRespService gruppiIstruttoriRespService;
    private MailServiceWSClient mailServiceWSClient;
    private LDPWsClient ldpWsClient;
    private NatureProcedureService natureProcedureService;
    private ResponsabiliAssenzeService responsabiliAssenzeService;
    private OggettiMetadatiService oggettiMetadatiService;
    private IstanzeAccessoAttiTService istanzeAccessoAttiTService;
    private IstanzeAccessoAttiDService istanzeAccessoAttiDService;
    private SpuntistiMercatiService spuntistiMercatiService;
    private IstanzeAccessoAttiLogService istanzeAccessoAttiLogService;
    @Autowired
    private IIstanzeMetadatiService istanzeMetadatiService;
    @Autowired
    private IEventPublisher eventPublisher;
    @Autowired
    private IAttivitaIstanzeService attivitaIstanzeService;
    private ICartograficoService cartograficoService;

    @Autowired
    public void setIstanzeAccessoAttiDService(IstanzeAccessoAttiDService istanzeAccessoAttiDService) {

	this.istanzeAccessoAttiDService = istanzeAccessoAttiDService;
    }

    @Autowired
    public void setIstanzeAccessoAttiTService(IstanzeAccessoAttiTService istanzeAccessoAttiTService) {

	this.istanzeAccessoAttiTService = istanzeAccessoAttiTService;
    }

    @Autowired
    public void setIstanzeAccessoAttiLogService(IstanzeAccessoAttiLogService istanzeAccessoAttiLogService) {

	this.istanzeAccessoAttiLogService = istanzeAccessoAttiLogService;
    }

    @Autowired
    public void setOggettiMetadatiService(OggettiMetadatiService oggettiMetadatiService) {

	this.oggettiMetadatiService = oggettiMetadatiService;
    }

    @Autowired
    public void setResponsabiliAssenzeService(ResponsabiliAssenzeService responsabiliAssenzeService) {

	this.responsabiliAssenzeService = responsabiliAssenzeService;
    }

    @Autowired
    public void setNatureProcedureService(NatureProcedureService natureProcedureService) {

	this.natureProcedureService = natureProcedureService;
    }

    @Autowired
    public void setLdpWsClient(LDPWsClient ldpWsClient) {

	this.ldpWsClient = ldpWsClient;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setTipicontromovimentoService(TipicontromovimentoService tipicontromovimentoService) {

	this.tipicontromovimentoService = tipicontromovimentoService;
    }

    @Autowired
    public void setIstanzedeleteService(IstanzedeleteService istanzedeleteService) {

	this.istanzedeleteService = istanzedeleteService;
    }

    @Autowired
    public void setTipicausalioneriService(TipicausalioneriService tipicausalioneriService) {

	this.tipicausalioneriService = tipicausalioneriService;
    }

    @Autowired
    public void setGruppiIstruttoriService(GruppiIstruttoriService gruppiIstruttoriService) {

	this.gruppiIstruttoriService = gruppiIstruttoriService;
    }

    @Autowired
    public void setGruppiIstruttoriRespService(GruppiIstruttoriRespService gruppiIstruttoriRespService) {

	this.gruppiIstruttoriRespService = gruppiIstruttoriRespService;
    }

    @Autowired
    public void setMailServiceWSClient(MailServiceWSClient mailServiceWSClient) {

	this.mailServiceWSClient = mailServiceWSClient;
    }

    @Autowired
    public void setSpuntistiMercatiService(SpuntistiMercatiService spuntistiMercatiService) {

	this.spuntistiMercatiService = spuntistiMercatiService;
    }

    @Override
    protected Class<Istanze> getEntityClass() {

	return Istanze.class;
    }

    @Autowired
    public void setPecInboxService(PecInboxService pecInboxService) {

	this.pecInboxService = pecInboxService;
    }

    @Autowired
    public void setCambioInterventoManager(CambioInterventoManager cambioInterventoManager) {

	this.cambioInterventoManager = cambioInterventoManager;
    }

    @Autowired
    public void setTmpEsportazioniService(TmpEsportazioniService tmpEsportazioniService) {

	this.tmpEsportazioniService = tmpEsportazioniService;
    }

    @Autowired
    public void setCartograficoService(ICartograficoService cartograficoService) {

	this.cartograficoService = cartograficoService;
    }

    @Override
    public void clear() {

	istanzeDAO.clear();
    }

    @Override
    public Istanze findById(PkId id) {

	Istanze istanza = istanzeDAO.findById(id);
	return istanza;
    }

    @Override
    public void insert(Istanze entity, TipoInserimento tipoInserimento) {

	this.insert(entity, tipoInserimento, null);
    }

    @Override
    public void insert(Istanze entity, TipoInserimento tipoInserimento, IPostIstanzeInsertCallBack postCallBack) {

	if (tipoInserimento == null) {
	    tipoInserimento = TipoInserimento.PROTOCOLLAZIONE_PARAMETRI_INSERIMENTO_NORMALE;
	}
	if (log.isDebugEnabled()) {
	    log.debug("insert: inserimento dell'istanza [tipoInserimento={}]", tipoInserimento);
	}
	log.debug("insert: prima di eseguire dataIntegration");
	AlberoprocHelper helper = dataIntegration(entity, true);
	log.debug("insert: prima della validateEntity");
	if (validateEntity(entity, true)) {
	    log.debug("insert: copio le proprietà");
	    IstanzeListsDTO copia = copyObjectProperties(entity);
	    log.debug("insert: prima di inserire");
	    istanzeDAO.insert(entity);
	    log.debug("insert: istanza Inserita");
	    IstanzeBusinessRules rules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
	    log.debug("insert: prima di ChildDataIntegration");
	    childDataIntegration(entity, copia, rules);
	    log.debug("insert: prima di ChildDataInsert");
	    childDataInsert(entity, copia, helper, rules);
	    try {
		operazioniAutomatiche(entity, tipoInserimento, helper, postCallBack);
		insertEventiCheckCodiceFiscale(entity);
		insertInserimentoIstanzeFlashMessages(entity);
	    } catch (OperazioniAutomaticheException e) {
		log.error("insert: {}", e.getMessage());
	    }
	}
    }

    private void insertInserimentoIstanzeFlashMessages(Istanze istanza) {

	List<String> ifms = InserimentoIstanzeFlashMessages.getEventis();
	if (ifms != null) {
	    if (ifms.size() > 0) {
		for (String evento : ifms) {
		    if (StringUtils.isNotBlank(evento)) {
			istanzeeventiService.insert(StringUtils.left(evento, 3999), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
		    }
		}
	    }
	    InserimentoIstanzeFlashMessages.removeWarnings();
	}
    }

    public void insertEventiCheckCodiceFiscale(Istanze entity) throws OperazioniAutomaticheException {

	// Per ogni oggetto di tipo anagrafe collegato all'istanza (richiedente, intermediario e soggetti collegati) 
	// esegue un check sul codice fiscale della scheda anagrafica. 
	// Il controllo verifica che il CodiceFiscale sia di 16 caratteri e che la penultima lettera del codicefiscale 
	// sia una cifra numerica
	if (entity == null) {
	    throw new OperazioniAutomaticheException("Il parametro entity è obbligatorio");
	}
	entity = this.findById(entity.getId());
	if (entity != null) {
	    if (EntityUtils.isNestedPropertyBlank(entity.getRichiedente(), "id.codice") == false) {
		Anagrafe richiedente = anagrafeService.findById(entity.getRichiedente().getId());
		String controllo = checkCodiceFiscale(richiedente);
		if (StringUtils.isNotBlank(controllo)) {
		    istanzeeventiService.insert(controllo, IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, entity);
		}
	    }
	    if (EntityUtils.isNestedPropertyBlank(entity.getTitolarelegale(), "id.codice") == false) {
		Anagrafe azienda = anagrafeService.findById(entity.getTitolarelegale().getId());
		String controllo = checkCodiceFiscale(azienda);
		if (StringUtils.isNotBlank(controllo)) {
		    istanzeeventiService.insert(controllo, IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, entity);
		}
	    }
	    if (EntityUtils.isNestedPropertyBlank(entity.getProfessionista(), "id.codice") == false) {
		Anagrafe professionista = anagrafeService.findById(entity.getProfessionista().getId());
		String controllo = checkCodiceFiscale(professionista);
		if (StringUtils.isNotBlank(controllo)) {
		    istanzeeventiService.insert(controllo, IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, entity);
		}
	    }
	    List<Istanzerichiedenti> richiedentis = istanzerichiedentiService.findByIstanza(entity);
	    for (Istanzerichiedenti istanzerichiedenti : richiedentis) {
		if (EntityUtils.isNestedPropertyBlank(istanzerichiedenti.getRichiedente(), "id.codice") == false) {
		    Anagrafe richiedente = anagrafeService.findById(istanzerichiedenti.getRichiedente().getId());
		    String controllo = checkCodiceFiscale(richiedente);
		    if (StringUtils.isNotBlank(controllo)) {
			istanzeeventiService.insert(controllo, IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, entity);
		    }
		}
		if (EntityUtils.isNestedPropertyBlank(istanzerichiedenti.getAnagrafeCollegata(), "id.codice") == false) {
		    Anagrafe anagrafeColl = anagrafeService.findById(istanzerichiedenti.getAnagrafeCollegata().getId());
		    String controllo = checkCodiceFiscale(anagrafeColl);
		    if (StringUtils.isNotBlank(controllo)) {
			istanzeeventiService.insert(controllo, IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, entity);
		    }
		}
		if (EntityUtils.isNestedPropertyBlank(istanzerichiedenti.getProcuratore(), "id.codice") == false) {
		    Anagrafe richiedente = anagrafeService.findById(istanzerichiedenti.getProcuratore().getId());
		    String controllo = checkCodiceFiscale(richiedente);
		    if (StringUtils.isNotBlank(controllo)) {
			istanzeeventiService.insert(controllo, IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, entity);
		    }
		}
	    }
	}
    }

    @Override
    public void evict(Istanze entity) {

	istanzeDAO.evict(entity);
    }

    private String checkCodiceFiscale(Anagrafe anagrafe) {

	if (anagrafe != null) {
	    String descrizioneSoggetto = anagrafe.getDescrizioneRichiedente() + "(" + anagrafe.getId() + ")";
	    String tipoAnagrafe = StringUtils.defaultIfEmpty(anagrafe.getTipoanagrafe(), WebConstants.PERSONA_FISICA);
	    if (tipoAnagrafe.equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
		if (StringUtils.isNotBlank(anagrafe.getCodicefiscale())) {
		    if (anagrafe.getCodicefiscale().length() != 16) {
			return "Il codice fiscale del soggetto " + descrizioneSoggetto + " non risulta corretto [" + anagrafe.getCodicefiscale() +
			       "] in quanto composto di " + anagrafe.getCodicefiscale().length() + " caratteri";
		    } else {
			char cifra = anagrafe.getCodicefiscale().charAt(14);
			if (Character.isDigit(cifra) == false) {
			    return "Controllare il codice fiscale [" + anagrafe.getCodicefiscale() + "] del richiedente " + descrizioneSoggetto;
			}
		    }
		} else {
		    return "Il soggetto " + descrizioneSoggetto + " non ha nessun codice fiscale definito";
		}
	    } else {
		if (StringUtils.isNotBlank(anagrafe.getCodicefiscale())) {
		    if ((anagrafe.getCodicefiscale().length() != 16) && (anagrafe.getCodicefiscale().length() != 11)) {
			return "Il codice fiscale del soggetto " + descrizioneSoggetto + " non risulta corretto [" + anagrafe.getCodicefiscale() +
			       "] in quanto composto di " + anagrafe.getCodicefiscale().length() + " caratteri";
		    }
		}
	    }
	}
	return null;
    }

    @Override
    public void operazioniAutomatiche(Istanze entity, TipoInserimento tipoInserimento, AlberoprocHelper helper,
	    IPostIstanzeInsertCallBack postCallBack) throws OperazioniAutomaticheException {

	// NB: deve essere fatto prima del protocollo perchè la gestione automatica del protocollo crea/inserisci i valori, ma non li mette sulla entity
	//. Devo controllare se la voce dell'albero scelta prevede la scelta dell'istruttore all'interno di un gruppo e l'assegnazione sia fatta in modo automatico
	// casualmente
	GruppiIstruttori gruppiIstruttori = helper.getGruppiIstruttori();
	if (gruppiIstruttori != null && BooleanUtils.isTrue(helper.getGrpFlagAssegnazioneAut())) {
	    log.debug(
		    "operazioniAutomatiche# L'assegnazione del responsabile dell'istrutoria verrà fatta in modo automatico casualmente all'interno del gruppo {}[{}]",
		    new Object[] { gruppiIstruttori.getDescrizione(), gruppiIstruttori.getId().getCodice() });
	    List<GruppiIstruttoriResp> istruttoriNelGruppo = gruppiIstruttoriRespService
		    .findByGruppoIstruttoriSelezionabili(gruppiIstruttori.getId().getCodice());
	    if (!istruttoriNelGruppo.isEmpty()) {
		int numCasuale = Utilities.geratoreInteriCasuali(istruttoriNelGruppo.size());
		GruppiIstruttoriResp istruttoriResp = istruttoriNelGruppo.get(numCasuale);
		entity.setIstruttoreTemp(istruttoriResp.getResponsabili());
		if (!helper.getGrpFlagAccettazione()) {
		    entity.setIstruttore(istruttoriResp.getResponsabili());
		    entity.setGprDataAccettazione(new Date());
		}
		istanzeDAO.update(entity);
		try {
		    this.sendEmailNoticheFunzionalitaAntiCorruzione(entity, istruttoriResp.getResponsabili(), false, false, true);
		} catch (Exception e) {
		    log.error("accettaOrRigettaAssegnazione# Errore durante l'operazione di accettazione: {}[{}]",
			    new Object[] { e.getMessage(), e });
		}
	    } else {
		try {
		    istanzeeventiService.insert("Non è stato possibile individuare un istruttore del gruppo " + gruppiIstruttori.getDescrizione() +
						" configurato per la pratica ",
			    IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, entity);
		} catch (Exception ex) {
		    log.error("operazioniAutomatiche: non è stato possibile inserire l'evento a causa di: {}", ex.getMessage());
		}
	    }
	}
	// Commit poi esecuzione eventi registrati per l'istanza 
	// ES: Creazione allegati, Protocollazione, Notifiche automatiche movimenti
	OperazioniAutomaticheBusinessRules opautRules = (OperazioniAutomaticheBusinessRules) SigeproBusinessRules
		.getClassRules(OperazioniAutomaticheBusinessRules.class);
	boolean isProtocollaIstanza = opautRules.isProtocollazioneIstanza();
	boolean isNotificaStcAutomatica = opautRules.isNotificaStcAutomatica();
	if (postCallBack != null) {
	    log.debug("Prima di chiamata Callback");
	    postCallBack.callback(entity);
	    log.debug("Dopo chiamata Callback");
	}
	istanzeDAO.flush();
	istanzeDAO.commit();
	this.eventPublisher.publish(new EventoInserimentoIstanza(entity.getId().getCodice()));
	istanzeDAO.flush();
	istanzeDAO.commit();
	IstanzeBusinessRules istanzeRules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
	if (!istanzeRules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaProceduraImport.name())) {
	    try {
		eseguiFormuleDelleSchedeDinamiche(entity); // in caso di errore rilancia operazioni automatiche exception
		// che eviterebbe la protocollazione. 
		// La protocollazione deve essere comunque fatta e quindi non rilancio l'errore se le formule non sono state salvate
	    } catch (OperazioniAutomaticheException e) {
		log.error("Errore nell'elaborazionedelle schede dinamiche {}", e.getMessage(), e);
	    }
	}
	if (isProtocollaIstanza || isNotificaStcAutomatica) {
	    if (isProtocollaIstanza) {
		try {
		    gestProtocolloEFascicolo(entity, tipoInserimento);
		} catch (Exception e) {
		    throw new OperazioniAutomaticheException(
			    "Non è stato possibile eseguire la protocollazione della pratica a causa di: " + e.getMessage(), e);
		}
	    }
	    if (isNotificaStcAutomatica) {
		// ATTENZIONE!! IL SEGUENTE CODICE VIENE ESEGUITO IN QUANTO DURANTE L'INSERIMENTO DEI MOVIMENTI VIENE MESSA A FALSE LA REGOLA DELLA NOTIFICA AUTOMATICA
		// QUINDI LE NOTIFICHE AUTOMATICHE IN CASO DI INSERIMENTO PRATICA VENGONO EFFETTIVAMENTE NOTIFICATE QUI E NON NELL'ELABORAZIONE DEI MOVIMENTI
		// §§§BEGIN§§§
		List<Movimenti> movimentiDanotificare = movimentiService.findEseguitiByIstanza(entity);
		for (Movimenti movimenti : movimentiDanotificare) {
		    try {
			movimentiService.notificaStc(movimenti);
		    } catch (Exception e) {
			throw new OperazioniAutomaticheException("Non è stato possibile notificare il movimento a causa di: " + e.getMessage(), e);
		    }
		}
		// §§§END§§§
	    }
	}
    }

    @Override
    public DatiProtocolloResponseType insertProtocolloEfascicolo(Istanze entity, TipoInserimento tipoInserimento) {

	return this.gestProtocolloEFascicolo(entity, tipoInserimento);
    }

    /**
     * Il metodo protocolla e fascicola secondo le seguenti regole:<br>
     * Se la pratica è già protocollata, si crea la copia e si fascicola solo se la pratica proviene da STC (ma non dai
     * nodi di front-end). La funzione creaCopie è implementata a seconda del tipo di protocollo ed il controllo è
     * eseguito dal ws di protocollazione(.NET)<br>
     * Se la pratica NON è protocollata allora si protocolla e si fascicola. Le regole di fascicolazione e
     * protocollazione sono gestite dal ws di protocollazione(.NET), quindi la chiamata di fascicolazione potrebbe non
     * causare effetto.
     * 
     * @param entity
     * @param tipoInserimento
     */
    private DatiProtocolloResponseType gestProtocolloEFascicolo(Istanze entity, TipoInserimento tipoInserimento) {

	DatiProtocolloResponseType datiProtocolloResponseType = null;
	try {
	    if (tipoInserimento == null) {
		tipoInserimento = TipoInserimento.PROTOCOLLAZIONE_PARAMETRI_INSERIMENTO_NORMALE;
	    }
	    log.debug("gestProtocolloEFascicolo(istanza n.={}, tipoInserimento={})", new Object[] { entity.getNumeroistanza(), tipoInserimento });
	    IstanzeBusinessRules rules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
	    boolean isInserimentoDaStc = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name());
	    boolean isInserimentoDaStcDiretto = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStcDiretto.name());
	    boolean isFascicolare = false;
	    if (StringUtils.isNotBlank(entity.getNumeroprotocollo())) {
		//esiste un protocollo
		if (isInserimentoDaStc) {
		    //la pratica proviene da STC
		    DatiProtocolloResponseType datiProtocolloCopia = null;
		    if (!isInserimentoDaStcDiretto) {
			//la pratica proviene da un nodo NLA non di frontend, quindi creo la copia
			datiProtocolloCopia = protocollazioneService.creaCopie(entity.getId().getCodice(), ORMHelper.getToken());
		    }
		    if (datiProtocolloCopia != null && StringUtils.isNotBlank(datiProtocolloCopia.getNumeroProtocollo())) {
			//la creazione copia è implementata nel prot. destinatario quindi fascicolo la copia del protocollo
			isFascicolare = true;
		    }
		}
	    } else {
		DatiMittentiType aom = null;
		//non esiste un protocollo
		if (isInserimentoDaStc || isInserimentoDaStcDiretto) {
		    // POPOLA STRUTTURA ArrayOfMittenti
		    log.debug("GEST_PROT: INSERIMENTO DA STC");
		    boolean isProtocollo = new VerticalizzazioneProtocolloAttivoServiceImpl(verticalizzazioniService,
			    entity.getComune().getCodicecomune()).isAttiva();
		    if (isProtocollo) {
			log.debug("GEST_PROT: PROTOCOLLO ATTIVO CERCO LA LISTA DEI NODI");
			Verticalizzazioniparametri lnmitt = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
				VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
				VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_LISTA_NODI_SOSTITUISCI_MITTENTI,
				entity.getComune().getCodicecomune());
			if (lnmitt != null) {
			    log.debug("GEST_PROT: LA LISTA DEI NODI PRESENTE");
			    if (StringUtils.isNotBlank(lnmitt.getValore())) {
				log.debug("GEST_PROT: LA LISTA DEI NODI VALORE PARAMETRO: {}", lnmitt.getValore());
				String idnodomitt = entity.getTransientIdNodoMittente();
				String identemitt = entity.getTransientIdEnteMittente();
				String idsportellomitt = entity.getTransientIdSportelloMittente();
				log.debug("GEST_PROT: LA LISTA DEI NODI VALORI: {},{},{}", new String[] { idnodomitt, identemitt, idsportellomitt });
				if (StringUtils.isNotBlank(idnodomitt) && StringUtils.isNotBlank(identemitt)
					&& StringUtils.isNotBlank(idsportellomitt)) {
				    String codiceMitt = idnodomitt + "_" + identemitt + "_" + idsportellomitt;
				    if (lnmitt.getValore().indexOf(codiceMitt) >= 0) {
					String mezzo = "";
					String trasmissione = "";
					Verticalizzazioniparametri mezzoDefault = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
						VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
						VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MEZZO_DEFAULT,
						entity.getComune().getCodicecomune());
					if (mezzoDefault != null) {
					    mezzo = StringUtils.defaultString(mezzoDefault.getValore());
					}
					Verticalizzazioniparametri trasDefault = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
						VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
						VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MODALITA_TRASMISSIONE_DEFAULT,
						entity.getComune().getCodicecomune());
					if (trasDefault != null) {
					    trasmissione = StringUtils.defaultString(trasDefault.getValore());
					}
					ObjectFactory pfactory = new ObjectFactory();
					JAXBElement<DatiMittentiType> dmt = pfactory.createDatiMittentiType(new DatiMittentiType());
					aom = dmt.getValue();
					log.debug("GEST_PROT: CERCO L'AMMINISTRAZIONE CON I RIFERIMENTI: {},{},{}",
						new String[] { idnodomitt, identemitt, idsportellomitt });
					Amministrazioni amm = amministrazioniService.findAmministrazioneSTC(idnodomitt, identemitt, idsportellomitt,
						null);
					String codAmm = StringUtils.trim(StringUtils.defaultString(entity.getTransientAmministrazioneSTCMittente()));
					if (StringUtils.isNotBlank(codAmm) && Utilities.isInteger(codAmm)) {
					    amm = amministrazioniService.findById(new PkId(Integer.parseInt(codAmm)));
					}
					if (amm != null) {
					    log.debug("GEST_PROT: AMMINISTRAZIONE TROVATA: {},{}",
						    new String[] { amm.getId().toString(), amm.getAmministrazione() });
					    JAXBElement<ArrayOfDatiAnagraficiType> jbAODA = pfactory
						    .createArrayOfDatiAnagraficiType(new ArrayOfDatiAnagraficiType());
					    ArrayOfDatiAnagraficiType a1 = jbAODA.getValue();
					    JAXBElement<DatiAnagraficiType> jDAT = pfactory.createDatiAnagraficiType(new DatiAnagraficiType());
					    DatiAnagraficiType dat = jDAT.getValue();
					    dat.setCod(String.valueOf(amm.getId().getCodice()));
					    dat.setMezzo(mezzo);
					    dat.setModalitaTrasmissione(trasmissione);
					    a1.getDatiAnagraficiType().add(dat);
					    aom.setAmministrazione(a1);
					    log.debug("aom.amm.da.size(): {}", aom.getAmministrazione().getDatiAnagraficiType().size());
					}
				    }
				}
			    }
			}
		    }
		}
		log.debug("GEST_PROT: AOM-->{}", aom);
		datiProtocolloResponseType = protocollazioneService.protocollaIstanza(entity, ORMHelper.getToken(), tipoInserimento, aom);
		isFascicolare = true;
	    }
	    if (isFascicolare) {
		protocollazioneService.fascicolaIstanza(entity, ORMHelper.getToken(), tipoInserimento);
	    }
	} catch (Exception e) {
	    log.error("gestProtocolloEFascicolo: {}", e.getMessage());
	    List<String> warnings = new ArrayList<String>();
	    warnings.add(e.getMessage());
	    if (e instanceof BaseValidationException) {
		List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
		for (InvalidValue invalidValue : ivs) {
		    warnings.add(invalidValue.getMessage());
		}
	    }
	    String descrizioneEvento = "";
	    for (String warning : warnings) {
		descrizioneEvento = descrizioneEvento.concat(warning).concat("\n");
	    }
	    try {
		istanzeeventiService.insert(descrizioneEvento, IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, entity);
	    } catch (Exception ex) {
		log.error("gestProtocolloEFascicolo: non è stato possibile inserire l'evento a causa di: {}", ex.getMessage());
	    }
	    FlashMessages.setWarnings(warnings);
	}
	return datiProtocolloResponseType;
    }

    @Override
    public void insert(Istanze entity) {

	this.insert(entity, TipoInserimento.PROTOCOLLAZIONE_PARAMETRI_INSERIMENTO_NORMALE, null);
    }

    private void childDataIntegration(Istanze entity, IstanzeListsDTO copia, IstanzeBusinessRules rules) {

	log.debug("childDataIntegration: istanzeAree");
	Set<Istanzearee> istanzearees = copia.getIstanzearees();
	if (istanzearees == null) {
	    istanzearees = new HashSet<Istanzearee>();
	}
	boolean isAreaPrimariaSettata = false;
	if (istanzearees.size() > 0) {
	    for (Istanzearee istanzearee : istanzearees) {
		istanzearee.getId().setCodiceistanza(entity.getId().getCodice());
		istanzearee.setIstanza(entity);
		if (BooleanUtils.isTrue(istanzearee.getPrimario())) {
		    isAreaPrimariaSettata = true;
		}
	    }
	    if (isAreaPrimariaSettata == false) {
		for (Istanzearee istanzearee : istanzearees) {
		    istanzearee.setPrimario(Boolean.TRUE);
		    break;
		}
	    }
	}
	log.debug("childDataIntegration: autorizzazioni");
	// autorizzazioni
	Set<Autorizzazioni> autorizzazionis = copia.getAutorizzazionis();
	for (Autorizzazioni aut : autorizzazionis) {
	    aut.setIstanza(entity);
	    // Anagrafe anagrafe = anagrafeService.bindDomainObject(aut.getAnagrafe(), PkId.class, "id.codice");
	    // aut.setAnagrafe(anagrafe);
	    // Comuni comune = comuniService.bindDomainObject(aut.getAutorizcomune(), String.class, "codicecomune");
	    // aut.setAutorizcomune(comune);
	    // Tipologiaregistri tipologiaregistro =
	    // tipologiaregistriService.bindDomainObject(aut.getTipologiaregistro(), PkId.class, "id.codice");
	    // aut.setTipologiaregistro(tipologiaregistro);
	}
	// istanzestradario
	log.debug("childDataIntegration: istanzeStradario");
	Set<Istanzestradario> istanzestradarios = copia.getIstanzestradarios();
	if (istanzestradarios.size() > 0) {
	    boolean primariosettato = false;
	    for (Istanzestradario istanzestradario : istanzestradarios) {
		istanzestradario.setIstanza(entity);
		if (BooleanUtils.isTrue(istanzestradario.getPrimario())) {
		    primariosettato = true;
		}
		Set<Istanzemappali> mappalis = istanzestradario.getIstanzemappalis();
		for (Istanzemappali mappale : mappalis) {
		    mappale.setIstanza(entity);
		}
	    }
	    if (primariosettato == false) {
		for (Istanzestradario istanzestradario : istanzestradarios) {
		    istanzestradario.setPrimario(Boolean.TRUE);
		    break;
		}
	    }
	}
	AlberoprocHelper alberoprocHelper = alberoprocService.findAlberoprocHelper(entity.getAlberoproc());
	Tipiprocedure procedura = tipiprocedureService.findById(entity.getProcedura().getId());
	///////////////////////////////////////
	Set<Istanzedyn2modellit> istanzedyn2modellits = copia.getIstanzedyn2modellit();
	if (istanzedyn2modellits == null) {
	    istanzedyn2modellits = new HashSet<Istanzedyn2modellit>();
	}
	if (istanzedyn2modellits.size() > 0) {
	    for (Istanzedyn2modellit istanzedyn2modellit : istanzedyn2modellits) {
		Dyn2Modellit dyn2Modellit = dyn2ModellitService.bindDomainObject(istanzedyn2modellit.getDyn2Modellit(), PkId.class, "id.codice");
		Istanzedyn2modellitId id = new Istanzedyn2modellitId(entity.getId().getCodice(), dyn2Modellit.getId().getCodice());
		istanzedyn2modellit.setId(id);
		istanzedyn2modellit.setIstanza(entity);
		istanzedyn2modellit.setDyn2Modellit(dyn2Modellit);
	    }
	}
	boolean isModelliRule = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.generaAutomaticamenteModelliDinamiciIstanza.name());
	if (isModelliRule) {
	    log.debug("childDataIntegration: istanzeDyn2Modellits");
	    // i modelli associati all'albero dei procedimenti
	    // entity.setIstanzedyn2modellit(new HashSet<Istanzedyn2modellit>());
	    Set<AlberoprocDyn2modellit> alberoprocModellis = alberoprocHelper.getAlberoprocDyn2modellits();
	    for (AlberoprocDyn2modellit alberoprocDyn2modellit : alberoprocModellis) {
		Istanzedyn2modellit istanzedyn2modelli = new Istanzedyn2modellit();
		Istanzedyn2modellitId id = new Istanzedyn2modellitId(entity.getId().getCodice(),
			alberoprocDyn2modellit.getDyn2Modellit().getId().getCodice());
		istanzedyn2modelli.setId(id);
		istanzedyn2modelli.setIstanza(entity);
		istanzedyn2modelli.setDyn2Modellit(alberoprocDyn2modellit.getDyn2Modellit());
		copia.getIstanzedyn2modellit().add(istanzedyn2modelli);
	    }
	    // Schede dinamiche associate alla procedura
	    Set<TipiprocedureDyn2modellit> tipiprocedureDyn2modellits = procedura.getTipiprocedureDyn2modellits();
	    for (TipiprocedureDyn2modellit tipiprocedureDyn2modellit : tipiprocedureDyn2modellits) {
		Istanzedyn2modellit istanzedyn2modelli = new Istanzedyn2modellit();
		Istanzedyn2modellitId id = new Istanzedyn2modellitId(entity.getId().getCodice(),
			tipiprocedureDyn2modellit.getDyn2Modellit().getId().getCodice());
		istanzedyn2modelli.setId(id);
		istanzedyn2modelli.setIstanza(entity);
		istanzedyn2modelli.setDyn2Modellit(tipiprocedureDyn2modellit.getDyn2Modellit());
		copia.getIstanzedyn2modellit().add(istanzedyn2modelli);
	    }
	}
	///////////////////////////////////////
	// I documenti legati all'intervento selezionato
	log.debug("childDataIntegration: documentiIstanzas");
	Set<Documentiistanza> documentiistanzas = copia.getDocumentiistanzas();
	if (documentiistanzas == null) {
	    documentiistanzas = new HashSet<Documentiistanza>();
	}
	for (Documentiistanza documentiistanza : documentiistanzas) {
	    documentiistanza.setIstanza(entity);
	}
	boolean isDocumentiRule = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.generaAutomaticamenteDocumentiIstanza.name());
	if (isDocumentiRule) {
	    Set<AlberoprocDocumenti> alberoprocDocumentis = alberoprocHelper.getAlberoprocDocumentis();
	    for (AlberoprocDocumenti alberoprocDocumenti : alberoprocDocumentis) {
		Documentiistanza documento = new Documentiistanza();
		documento.setIstanza(entity);
		documento.setDocumento(alberoprocDocumenti.getDescrizione());
		documento.setData(entity.getData());
		documento.setNecessario(alberoprocDocumenti.getRichiesto());
		documento.setPresente(false);
		documento.setAlberoprocDocumenticat(alberoprocDocumenti.getAlberoprocDocumenticat());
		copia.getDocumentiistanzas().add(documento);
	    }
	    // recupero ed inserisco i documenti legati alla procedura
	    Set<TipiprocedureDocumenti> documentis = procedura.getTipiprocedureDocumentis();
	    for (TipiprocedureDocumenti tipiprocedureDocumenti : documentis) {
		Documentiistanza documento = new Documentiistanza();
		documento.setIstanza(entity);
		documento.setDocumento(tipiprocedureDocumenti.getDescrizione());
		documento.setData(entity.getData());
		documento.setNecessario(true);
		documento.setPresente(false);
		copia.getDocumentiistanzas().add(documento);
	    }
	}
	log.debug("childDataIntegration: istanzeoneris");
	// entity.setIstanzeoneris(new HashSet<Istanzeoneri>());
	Set<Istanzeoneri> oneris = copia.getIstanzeoneris();
	if (oneris == null) {
	    oneris = new HashSet<Istanzeoneri>();
	}
	for (Istanzeoneri istanzeoneri : oneris) {
	    istanzeoneri.setIstanza(entity);
	}
	boolean isOneriRule = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.generaAutomaticamenteOneriIstanza.name());
	if (isOneriRule) {
	    Set<AlberoprocOneri> alberoprocOneris = alberoprocHelper.getAlberoprocOneris();
	    for (AlberoprocOneri alberoprocOneri : alberoprocOneris) {
		boolean inserisciOnere = controllaSeInserireOnere(alberoprocOneri, copia.getIstanzeoneris(), copia.getIstanzeeventis());
		if (inserisciOnere) {
		    Istanzeoneri istanzeoneri = new Istanzeoneri();
		    istanzeoneri.setIstanza(entity);
		    istanzeoneri.setData(entity.getData());
		    istanzeoneri.setTipicausalioneri(alberoprocOneri.getTipicausalioneri());
		    istanzeoneri.setFlentratauscita(Boolean.TRUE);
		    istanzeoneri.setFlribasso(Boolean.FALSE);
		    istanzeoneri.setPercribasso(100);
		    // Integer numerorata = istanzeoneriService.findNumeroRata(entity, alberoprocOneri.getTipicausalioneri());
		    // Istanzeoneri.setNumerorata(numerorata); // E' il progressivo in base a IDCOMUNE, ISTANZA, CAUSALEONERE
		    istanzeoneri.setResponsabile(entity.getResponsabile());
		    istanzeoneri.setPrezzo(alberoprocOneri.getAoImportocausale());
		    istanzeoneri.setPrezzoistruttoria(alberoprocOneri.getAoImportoistruttoria());
		    copia.getIstanzeoneris().add(istanzeoneri);
		}
	    }
	}
	// entity.setAutorizzazionisubentris(new HashSet<AutorizzazioniSubentri>());
	log.debug("childDataIntegration: istanzeallegatis");
	Set<Istanzeallegati> istanzeallegatis = copia.getIstanzeallegatis();
	if (istanzeallegatis == null) {
	    istanzeallegatis = new HashSet<Istanzeallegati>();
	}
	for (Istanzeallegati istanzeallegati : istanzeallegatis) {
	    istanzeallegati.setIstanza(entity);
	}
	log.debug("childDataIntegration: istanzeattivita");
	Set<Istanzeattivita> istanzeattivitas = copia.getIstanzeattivitas();
	if (istanzeattivitas == null) {
	    istanzeattivitas = new HashSet<Istanzeattivita>();
	}
	for (Istanzeattivita istanzeattivita : istanzeattivitas) {
	    istanzeattivita.setIstanza(entity);
	}
	copia.setIstanzeattivitas(istanzeattivitas);
	Set<Istanzedyn2dati> istanzedyn2datis = copia.getIstanzedyn2datis();
	if (istanzedyn2datis == null) {
	    istanzedyn2datis = new HashSet<Istanzedyn2dati>();
	}
	for (Istanzedyn2dati istanzedyn2dati : istanzedyn2datis) {
	    istanzedyn2dati.setIstanza(entity);
	    istanzedyn2dati.getId().setCodiceistanza(entity.getId().getCodice());
	}
	copia.setIstanzedyn2datis(istanzedyn2datis);
	// entity.setIstanzeeventis(new HashSet<Istanzeeventi>());
	// entity.setIstanzefidejussionis(new HashSet<Istanzefidejussioni>());
	log.debug("childDataIntegration: istanzeMovimentis");
	Set<Movimenti> ims = new LinkedHashSet<Movimenti>();
	Tipimovimento tipomovimento = entity.getTipoMovimentoAvvio();
	tipomovimento = tipiMovimentoService.bindDomainObject(tipomovimento, TipimovimentoId.class, "id.tipomovimento");
	Movimenti movimento = new Movimenti();
	movimento.setTipomovimento(tipomovimento);
	movimento.setData(entity.getData());
	movimento.setNumeroprotocollo(entity.getNumeroprotocollo());
	movimento.setDataprotocollo(entity.getDataprotocollo());
	movimento.setDatainserimento(GregorianCalendar.getInstance().getTime());
	Integer tipologiaEsito = tipomovimento.getTipologiaesito() == null ? 0 : tipomovimento.getTipologiaesito();
	if (tipologiaEsito.equals(2)) {
	    movimento.setEsito(Boolean.TRUE);
	} else if (tipologiaEsito.equals(1)) {
	    movimento.setEsito(Boolean.FALSE);
	}
	movimento.setIstanza(entity);
	movimento.setMovimento(tipomovimento.getMovimento());
	movimento.setResponsabile(entity.getResponsabile());
	movimento.setPubblica(tipomovimento.getFlagPubblicamovimento());
	movimento.setPubblicaparere(tipomovimento.getFlagPubblicaparere());
	if (StringUtils.isNotBlank(entity.getTransientNumeroProtocolloMittente())) {
	    movimento.setNumprotMittente(entity.getTransientNumeroProtocolloMittente());
	}
	if (entity.getTransientDataProtocolloMittente() != null) {
	    movimento.setDataProtMittente(entity.getTransientDataProtocolloMittente());
	}
	ims.add(movimento);
	Set<Movimenti> istanzemovimentis = copia.getIstanzemovimentis();
	if (istanzemovimentis == null) {
	    istanzemovimentis = new LinkedHashSet<Movimenti>();
	}
	for (Movimenti movimenti : istanzemovimentis) {
	    if (EntityUtils.getNestedProperty(movimenti.getResponsabile(), "id.codice") == null) {
		movimenti.setResponsabile(entity.getResponsabile());
	    }
	    movimenti.setIstanza(entity);
	    ims.add(movimenti);
	}
	copia.setIstanzemovimentis(ims);
	// entity.setIstanzeprocedimentis(new HashSet<Istanzeprocedimenti>());
	log.debug("childDataIntegration: istanzeProcedimentis");
	Set<Istanzeprocedimenti> istanzeprocedimentis = copia.getIstanzeprocedimentis();
	if (istanzeprocedimentis != null && istanzeprocedimentis.size() > 0) {
	    for (Istanzeprocedimenti istanzeprocedimenti : istanzeprocedimentis) {
		Inventarioprocedimenti inventarioprocedimentiTemp = new Inventarioprocedimenti();
		if (EntityUtils.getNestedProperty(istanzeprocedimenti.getInventarioprocedimenti(), "id.codice") == null) {
		    inventarioprocedimentiTemp.setId(new PkId(istanzeprocedimenti.getId().getCodiceinventario()));
		} else {
		    inventarioprocedimentiTemp.setId(new PkId(istanzeprocedimenti.getInventarioprocedimenti().getId().getCodice()));
		}
		if (EntityUtils.getNestedProperty(inventarioprocedimentiTemp, "id.codice") == null) {
		    throw new BusinessValidationException(
			    "Non è stato passato correttamente il parametro inventarioprocedimenti di istanzeprocedimenti");
		}
		Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.bindDomainObject(inventarioprocedimentiTemp, PkId.class,
			"id.codice");
		if (inventarioprocedimenti != null) {
		    istanzeprocedimenti.setDataattivazione(entity.getData());
		    istanzeprocedimenti.setInventarioprocedimenti(inventarioprocedimenti);
		    istanzeprocedimenti.setIstanza(entity);
		    istanzeprocedimenti.getId().setCodiceistanza(entity.getId().getCodice());
		    istanzeprocedimenti.getId().setCodiceinventario(inventarioprocedimenti.getId().getCodice());
		    //		    if (istanzeprocedimenti.getPerprovvedimento() == null) {
		    //			String tipomovimentoStr = (String) EntityUtils.getNestedProperty(inventarioprocedimenti, "tipomovimento.id.tipomovimento");
		    //			if (StringUtils.isNotBlank(tipomovimentoStr)) {
		    //			    istanzeprocedimenti.setPerprovvedimento(true);
		    //			}
		    //		    }
		}
	    }
	}
	// entity.setIstanzerichiedentis(new HashSet<Istanzerichiedenti>());
	log.debug("childDataIntegration: istanzeRichiedentis");
	Set<Istanzerichiedenti> istanzerichiedentis = copia.getIstanzerichiedentis();
	if (istanzerichiedentis == null) {
	    istanzerichiedentis = new HashSet<Istanzerichiedenti>();
	}
	for (Istanzerichiedenti istanzerichiedenti : istanzerichiedentis) {
	    istanzerichiedenti.setIstanza(entity);
	}
	//
	// entity.setPermistanzes(new HashSet<Permistanze>());
	log.debug("childDataIntegration: permistanzes");
	Set<Permistanze> permistanzes = copia.getPermistanzes();
	if (permistanzes == null) {
	    permistanzes = new HashSet<Permistanze>();
	}
	for (Permistanze permistanze : permistanzes) {
	    permistanze.setIstanze(entity);
	}
	PermistanzeId id = new PermistanzeId(entity.getId().getCodice(), entity.getResponsabile().getId().getCodice());
	Permistanze permistanze = new Permistanze();
	permistanze.setId(id);
	permistanze.setIstanze(entity);
	permistanze.setResponsabile(entity.getResponsabile());
	copia.getPermistanzes().add(permistanze);
	Responsabili respProcedimento = entity.getResponsabileProcedimento();
	if (respProcedimento != null) {
	    id = new PermistanzeId(entity.getId().getCodice(), respProcedimento.getId().getCodice());
	    permistanze = new Permistanze();
	    permistanze.setId(id);
	    permistanze.setIstanze(entity);
	    permistanze.setResponsabile(respProcedimento);
	    copia.getPermistanzes().add(permistanze);
	}
	Responsabili respIstruttoria = entity.getIstruttore();
	if (respIstruttoria != null) {
	    id = new PermistanzeId(entity.getId().getCodice(), respIstruttoria.getId().getCodice());
	    permistanze = new Permistanze();
	    permistanze.setId(id);
	    permistanze.setIstanze(entity);
	    permistanze.setResponsabile(respIstruttoria);
	    copia.getPermistanzes().add(permistanze);
	}
	// 2. se l'operatore è legato a qualche amministrazione interna
	// allora inserisco gli operatori collegati all'istanza
	Responsabili operatore = entity.getResponsabile();
	if (operatore != null) {
	    operatore = responsabiliService.findById(new PkId(operatore.getId().getCodice()));
	    Set<Amministrazioniresponsabili> responsabileAmministrazionis = operatore.getResponsabiliamministrazionis();
	    for (Amministrazioniresponsabili responsabileAmministrazione : responsabileAmministrazionis) {
		Amministrazioni amministrazioni = responsabileAmministrazione.getAmministrazioni();
		Set<Amministrazioniresponsabili> amministrazioniresponsabiles = amministrazioni.getAmministrazioniresponsabilis();
		for (Amministrazioniresponsabili amministrazioniresponsabili : amministrazioniresponsabiles) {
		    Responsabili operatoreAmm = amministrazioniresponsabili.getResponsabili();
		    id = new PermistanzeId(entity.getId().getCodice(), operatoreAmm.getId().getCodice());
		    permistanze = new Permistanze();
		    permistanze.setId(id);
		    permistanze.setIstanze(entity);
		    permistanze.setResponsabile(operatoreAmm);
		    copia.getPermistanzes().add(permistanze);
		}
	    }
	}
	boolean isPermessiRule = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.generaAutomaticamentePermessiIstanza.name());
	if (isPermessiRule) {
	    // 3. Tutti i ruoli dell’alberoproc dell'istanza
	    // entity.setIstanzeruolis(new HashSet<Istanzeruoli>());
	    Set<Istanzeruoli> istanzeruolis = copia.getIstanzeruolis();
	    if (istanzeruolis == null) {
		istanzeruolis = new HashSet<Istanzeruoli>();
	    }
	    for (Istanzeruoli istanzeruoli : istanzeruolis) {
		istanzeruoli.setIstanze(entity);
	    }
	    Set<AlberoprocRuoli> alberoprocRuoli = alberoprocHelper.getAlberoprocRuolis();
	    for (AlberoprocRuoli alberoprocRuolo : alberoprocRuoli) {
		// this.inserisciRuoloIstanza(entity, alberoprocRuolo.getRuoli());
		Istanzeruoli istanzeruoli = new Istanzeruoli();
		IstanzeruoliId idRuolo = new IstanzeruoliId(entity.getId().getCodice(), alberoprocRuolo.getRuoli().getId().getCodice());
		istanzeruoli.setId(idRuolo);
		istanzeruoli.setIstanze(entity);
		istanzeruoli.setRuolo(alberoprocRuolo.getRuoli());
		copia.getIstanzeruolis().add(istanzeruoli);
	    }
	}
	Set<Orariaperturatestata> orariaperturatestatas = copia.getOrariaperturatestatas();
	for (Orariaperturatestata orariaperturatestata : orariaperturatestatas) {
	    orariaperturatestata.setIstanze(entity);
	}
	Set<Istanzeeventi> istanzeeventis = copia.getIstanzeeventis();
	for (Istanzeeventi istanzeeventi : istanzeeventis) {
	    istanzeeventi.setIstanze(entity);
	}
	Set<Istanzeprocure> istanzeprocures = copia.getIstanzeprocures();
	for (Istanzeprocure istanzeprocure : istanzeprocures) {
	    istanzeprocure.setIstanze(entity);
	}
	// LA LOGICA DEL POPOLAMENTO VIENE FATTA IN CHILDDATAINSERT
	//	Set<Istanzecollegate> istanzecollegates = copia.getIstanzecollegates();
	//	for (Istanzecollegate istanzecollegate : istanzecollegates) {
	//	    istanzecollegate.setIstanza(entity);
	//	}
    }

    private boolean controllaSeInserireOnere(AlberoprocOneri alberoprocOneri, Set<Istanzeoneri> istanzeoneris, Set<Istanzeeventi> istanzeeventis) {

	Integer codiceCausaleC = -100;
	if (alberoprocOneri.getTipicausalioneri() != null && alberoprocOneri.getTipicausalioneri().getId() != null
		&& alberoprocOneri.getTipicausalioneri().getId().getCodice() != null) {
	    codiceCausaleC = alberoprocOneri.getTipicausalioneri().getId().getCodice();
	}
	if (istanzeoneris != null) {
	    for (Istanzeoneri ion : istanzeoneris) {
		Integer codiceCausaleIO = -200;
		if (ion.getTipicausalioneri() != null && ion.getTipicausalioneri().getId() != null
			&& ion.getTipicausalioneri().getId().getCodice() != null) {
		    codiceCausaleIO = ion.getTipicausalioneri().getId().getCodice();
		}
		Integer codiceInventario = (Integer) EntityUtils.getNestedProperty(ion.getInventarioprocedimenti(), "id.codice");
		if (codiceCausaleC.intValue() == codiceCausaleIO.intValue() && codiceInventario == null) {
		    // se il prezzo è uguale non lo inserisco
		    BigDecimal iop = ion.getPrezzo();
		    if (iop == null) {
			iop = BigDecimal.valueOf(0);
		    }
		    BigDecimal aop = alberoprocOneri.getAoImportocausale();
		    if (aop == null) {
			aop = BigDecimal.valueOf(0);
		    }
		    if (iop.doubleValue() == aop.doubleValue()) {
			return false;
		    } else {
			if (verificaWarning(alberoprocOneri, aop)) {
			    Istanzeeventi ie = new Istanzeeventi();
			    Categorieeventibase ceb = new Categorieeventibase();
			    ceb.setId(IstanzeeventiConstants.CATEGORIA_STC_IP);
			    ie.setCategorieeventibase(ceb);
			    ie.setDescrizione(
				    "Durante l'inserimento dell'onere configurato in albero interventi è stato trovato un onere con causale (" +
					      codiceCausaleIO.intValue() + ") e importo=" + aop.doubleValue() +
					      " mentre è stato ricevuto un importo=" + iop.doubleValue() +
					      ". L'onere della configurazione non è stato inserito");
			    // Se il prezzo è diverso metto su istanze eventi
			    // il fatto che l'importo è diverso
			    istanzeeventis.add(ie);
			    return false;
			}
		    }
		}
	    }
	}
	return true;
    }

    /**
     * L'onere è configurato a 0 per via che poi l'importo vero e proprio viene determinato da un campo dinamico.
     * Sarebbe meglio non generare lo warning se l'importo è configurato a 0 e viene determinato successivamente da
     * campo dinamico
     */
    private boolean verificaWarning(AlberoprocOneri alberoprocOneri, BigDecimal aop) {

	return !(aop.intValue() == 0 && (alberoprocOneri.getDyn2Campi() != null && alberoprocOneri.getDyn2Campi().getId() != null
		&& alberoprocOneri.getDyn2Campi().getId().getCodice() != null));
    }

    @Override
    public void update(Istanze entity) {

	log.debug("update: dataIntegration");
	dataIntegration(entity, false);
	log.debug("update: validateEntity");
	if (validateEntity(entity, false)) {
	    log.debug("update: copio le proprieta");
	    Istanze copy = istanzeDAO.findById(entity.getId());
	    log.debug("update: checkProtocollo");
	    checkLoggaCambioOperatore(copy, entity, false, null);
	    checkProtocollo(entity, copy);
	    childDataUpdate(entity, copy);
	}
    }

    @Override
    public void updateLavoriestesa(Integer codiceIstanza, String lavoriestesa) {

	istanzeDAO.updateLavoriestesa(codiceIstanza, lavoriestesa);
    }

    @Override
    public void updateTipoProtFallita(Integer codiceIstanza, String tipoProtFallita) {

	istanzeDAO.updateTipoProtFallita(codiceIstanza, tipoProtFallita);
    }

    @Override
    public void updateComuneIstanza(Integer codiceIstanza, String codiceComune) {

	istanzeDAO.updateComuneIstanza(codiceIstanza, codiceComune);
    }

    @Override
    public void updateNumeroistanza(Integer codiceIstanza, String numeroistanza) {

	checkModificaNumeroIstanza(numeroistanza, codiceIstanza, false);
	Istanze istanza = this.findById(new PkId(codiceIstanza));
	String vecchioNumeroIstanza = istanza.getNumeroistanza();
	if (!(StringUtils.defaultIfEmpty(vecchioNumeroIstanza, "").equals(StringUtils.defaultIfEmpty(numeroistanza, "")))) {
	    // se il numeroistanza è cambiato allora scrivo i progressivi su configurazione alberoproc
	    log.debug("childDataUpdate: il progressivo è cambiato");
	    scriviProgressivo(numeroistanza, istanza.getAlberoproc().getId().getCodice(), istanza.getSoftware().getCodice(), false);
	}
	istanzeDAO.updateNumeroistanza(codiceIstanza, numeroistanza);
    }

    private Azioni calcolaNuovaAzionePerIstanza(Integer codiceIntervento, Istanze istanza) {

	istanzeDAO.flush();
	// BOCCI 2011-11-15 AL CAMBIO DELLA VOCE DELL'ALBERO VA SETTATA DI NUOVO L'AZIONE (RICHIESTA MOLINO-CHIOCCI)
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceIntervento));
	List<Istanzeprocedimenti> ips = istanzeprocedimentiService.findByIstanze(istanza);
	List<Integer> codiciInventarioList = new ArrayList<Integer>();
	for (Istanzeprocedimenti ip : ips) {
	    codiciInventarioList.add(ip.getId().getCodiceinventario());
	}
	Azioni azione = alberoprocService.findAzioniDaEndoOAlberoproc(alberoproc, codiciInventarioList);
	return azione;
    }

    private void postUpdateActions(Istanze entity, boolean insertMovimentoAvvio, Movimenti movimentoAvvio, boolean proceduraModificata) {

	boolean elabora = false;
	if (insertMovimentoAvvio) {
	    log.debug("postUpdateActions: è cambiato il movimento di avvio");
	    insertMovimentoAvvio(entity);
	    elabora = true;
	} else {
	    if (movimentoAvvio != null) {
		Date dataMov = movimentoAvvio.getData();
		Date dataIstanza = entity.getData();
		if (dataMov != null) {
		    if (dataIstanza != null) {
			int diff = Utilities.calculateDifferenceInDays(dataIstanza, dataMov);
			if (diff != 0) {
			    log.debug("postUpdateActions: è cambiata la data dell'istanza");
			    // le date sono diverse setto al movimento la data dell'istanza
			    movimentoAvvio.setData(dataIstanza);
			    movimentoAvvio.setDataprotocollo(entity.getDataprotocollo());
			    movimentoAvvio.setNumeroprotocollo(entity.getNumeroprotocollo());
			    movimentoAvvio.setFkidprotocollo(entity.getFkidprotocollo());
			    movimentiService.update(movimentoAvvio);
			    elabora = true;
			}
		    }
		}
		if (!elabora) {
		    String numeroprotocolloIstanza = StringUtils.defaultIfEmpty(entity.getNumeroprotocollo(), "");
		    String numeroprotocolloMovimento = StringUtils.defaultIfEmpty(movimentoAvvio.getNumeroprotocollo(), "");
		    // Controllo se la data del protocollo istanza è uguale a null e quella de movimento diversa da null
		    // e viversa
		    int diffDataProt = 0;
		    if (entity.getDataprotocollo() == null && movimentoAvvio.getDataprotocollo() != null
			    || entity.getDataprotocollo() != null && movimentoAvvio.getDataprotocollo() == null
			    || entity.getDataprotocollo() == null && movimentoAvvio.getDataprotocollo() == null) {
			// Serve per evitare il NPE quando si sarebbe effettuato Utilities.calculateDifferenceInDays(...)
			diffDataProt = 1000;
		    } else {
			diffDataProt = Utilities.calculateDifferenceInDays(entity.getDataprotocollo(), movimentoAvvio.getDataprotocollo());
		    }
		    if (numeroprotocolloMovimento.equalsIgnoreCase(numeroprotocolloIstanza) == false || diffDataProt != 0) {
			log.debug("postUpdateActions: è cambiato il numero di protocollo dell'istanza");
			if (entity.getDataprotocollo() != null) {
			    movimentoAvvio.setDataprotocollo(entity.getDataprotocollo());
			} else {
			    movimentoAvvio.setDataprotocollo(null);
			}
			movimentoAvvio.setNumeroprotocollo(entity.getNumeroprotocollo());
			movimentoAvvio.setFkidprotocollo(entity.getFkidprotocollo());
			movimentiService.update(movimentoAvvio);
			elabora = true;
		    }
		}
	    }
	}
	if (proceduraModificata) {
	    elabora = true;
	    IstanzeTempistica istTempistica = istanzeTempisticaService.findById(new PkId(entity.getId().getCodice()));
	    istanzeTempisticaService.delete(istTempistica);
	}
	if (elabora) {
	    this.elabora(entity.getId().getCodice(), false);
	}
    }

    /**
     * Controllo se i dati di protocollazione sono stati modificati. Se si allora devo resettare il campo
     * fkidprotocollo, che verrà calcolato successivamente da protocollazioneservice.protocollaistanza richiamato da
     * IstanzeManager.gestProtocolloEFascicolo
     * 
     * @param entity
     * @param copy
     */
    private void checkProtocollo(Istanze entity, Istanze copy) {

	String numeroprotocollo = StringUtils.defaultIfEmpty(entity.getNumeroprotocollo(), "");
	Date dataProtocollo = entity.getDataprotocollo();
	String oldNumeroprotocollo = StringUtils.defaultIfEmpty(copy.getNumeroprotocollo(), "");
	Date oldDataProtocollo = copy.getDataprotocollo();
	// se la data di protocollazione è cambiata
	boolean isDataToCheck = false;
	if (dataProtocollo != null && oldDataProtocollo != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    String data = sdf.format(dataProtocollo);
	    String oldData = sdf.format(oldDataProtocollo);
	    if (!data.equals(oldData)) {
		isDataToCheck = true;
	    }
	} else {
	    if (dataProtocollo == null && oldDataProtocollo != null) {
		isDataToCheck = true;
	    }
	    if (!isDataToCheck) {
		if (dataProtocollo != null && oldDataProtocollo == null) {
		    isDataToCheck = true;
		}
	    }
	}
	if ((!numeroprotocollo.equals(oldNumeroprotocollo)) || isDataToCheck) {
	    entity.setFkidprotocollo(null);
	}
    }

    private void childDataUpdate(Istanze entity, Istanze copy) {

	//
	log.debug("childDataUpdate: prima di gestisciIstanzetempistica");
	gestisciIstanzetempistica(entity);
	// Aggiorno area primaria
	// Aggiorno i permessi solamente se sono cambiati operatore, responsabile proc, responsabile istr
	aggiornaPermessiIstanza(entity);
	// aggiorno il numero istanza, se cambiato
	String numeroIstanza = entity.getNumeroistanza();
	String nuovoNumeroIstanza = copy.getNumeroistanza();
	if (!(StringUtils.defaultIfEmpty(numeroIstanza, "").equals(StringUtils.defaultIfEmpty(nuovoNumeroIstanza, "")))) {
	    // se il numeroistanza è cambiato allora scrivo i progressivi su configurazione alberoproc
	    log.debug("childDataUpdate: il progressivo è cambiato");
	    scriviProgressivo(entity.getNumeroistanza(), entity.getAlberoproc().getId().getCodice(), entity.getSoftware().getCodice(), false);
	}
	Set<Istanzeprocedimenti> nuoviEndo = entity.getTransientIstanzeprocedimentis();
	if (nuoviEndo != null && !nuoviEndo.isEmpty()) {
	    for (Istanzeprocedimenti istanzeprocedimenti : nuoviEndo) {
		Istanzeprocedimenti ip = istanzeprocedimentiService
			.findById(new IstanzeprocedimentiId(entity.getId().getCodice(), istanzeprocedimenti.getId().getCodiceinventario()));
		if (ip == null) {
		    ip = new Istanzeprocedimenti();
		    IstanzeprocedimentiId idip = new IstanzeprocedimentiId(entity.getId().getCodice(),
			    istanzeprocedimenti.getId().getCodiceinventario());
		    ip.setId(idip);
		    ip.setIstanza(entity);
		    ip.setInventarioprocedimenti(istanzeprocedimenti.getInventarioprocedimenti());
		    ip.setDataattivazione(entity.getData());
		    istanzeprocedimentiService.insert(ip);
		}
	    }
	}
	// controllo se cambiato movimento di avvio	
	String tipoMovAvvio = StringUtils.defaultIfEmpty((String) EntityUtils.getNestedProperty(entity.getTipoMovimentoAvvio(), "id.tipomovimento"),
		"");
	String tipoMovAvvioCopia = StringUtils
		.defaultIfEmpty((String) EntityUtils.getNestedProperty(copy.getTipoMovimentoAvvio(), "id.tipomovimento"), "");
	boolean insertMovAvvio = !tipoMovAvvio.equals(tipoMovAvvioCopia);
	Integer nuovoCodiceProcedura = (Integer) EntityUtils.getNestedProperty(entity, "procedura.id.codice");
	Integer oldCodiceProcedura = (Integer) EntityUtils.getNestedProperty(copy, "procedura.id.codice");
	// controllo se cambiato alberoproc
	// BOCCI 2011-11-15 FINE AL CAMBIO DELLA VOCE DELL'ALBERO VA SETTATA DI NUOVO L'AZIONE (RICHIESTA MOLINO-CHIOCCI) 
	Integer codiceIntervento = (Integer) EntityUtils.getNestedProperty(entity, "alberoproc.id.codice");
	Integer oldCodiceIntervento = (Integer) EntityUtils.getNestedProperty(copy, "alberoproc.id.codice");
	if (codiceIntervento != null && oldCodiceIntervento != null) {
	    if (!codiceIntervento.equals(oldCodiceIntervento)) {
		Azioni azione = calcolaNuovaAzionePerIstanza(codiceIntervento, entity);
		if (azione != null) {
		    if (StringUtils.isNotBlank(azione.getAzAzione())) {
			entity.setAzione(azione.getAzAzione());
		    }
		}
	    }
	}
	// END BOCCI 2011-11-15 FINE AL CAMBIO DELLA VOCE DELL'ALBERO VA SETTATA DI NUOVO L'AZIONE (RICHIESTA MOLINO-CHIOCCI)
	String vecchiaAzione = StringUtils.defaultIfEmpty(copy.getAzione(), "");
	String nuovaAzione = StringUtils.defaultIfEmpty(entity.getAzione(), "");
	// BOCCI 2012-03-13 in caso di cambio richiedenti e questi sono selezionati nella tabella istanzeprocure allora devo inserire eventi 
	Integer codiceRichiedenteOld = (Integer) EntityUtils.getNestedProperty(copy.getRichiedente(), "id.codice");
	Integer codiceAziendaOld = (Integer) EntityUtils.getNestedProperty(copy.getTitolarelegale(), "id.codice");
	Integer codiceprofessionistaOld = (Integer) EntityUtils.getNestedProperty(copy.getProfessionista(), "id.codice");
	// END BOCCI 2012-03-13 in caso di cambio richiedenti e questi sono selezionati nella tabella istanzeprocure allora devo inserire eventi
	istanzeDAO.update(entity);
	istanzeDAO.flush();
	// BOCCI 2012-03-13 in caso di cambio richiedenti e questi sono selezionati nella tabella istanzeprocure allora devo inserire eventi 
	Integer codiceRichiedenteNew = (Integer) EntityUtils.getNestedProperty(entity.getRichiedente(), "id.codice");
	Integer codiceAziendaNew = (Integer) EntityUtils.getNestedProperty(entity.getTitolarelegale(), "id.codice");
	Integer codiceprofessionistaNew = (Integer) EntityUtils.getNestedProperty(entity.getProfessionista(), "id.codice");
	if (!Utilities.nullSafeEqual(codiceRichiedenteOld, codiceRichiedenteNew) || // 
		!Utilities.nullSafeEqual(codiceAziendaOld, codiceAziendaNew) || //
		!Utilities.nullSafeEqual(codiceprofessionistaOld, codiceprofessionistaNew)) {
	    eventPublisher.publish(new EventoSoggettiIstanzaAggiornati(entity.getId().getCodice()));
	}
	if (!istanzeprocureService.findByIstanza(entity.getId().getCodice()).isEmpty()) {
	    checkProcuraChange(entity.getId().getCodice(), codiceRichiedenteOld, codiceRichiedenteNew, entity);
	    checkProcuraChange(entity.getId().getCodice(), codiceAziendaOld, codiceAziendaNew, entity);
	    checkProcuraChange(entity.getId().getCodice(), codiceprofessionistaOld, codiceprofessionistaNew, entity);
	}
	// END BOCCI 2012-03-13 in caso di cambio richiedenti e questi sono selezionati nella tabella istanzeprocure allora devo inserire eventi
	Movimenti movimentoAvvio = movimentiService.findMovimentoAvvioIstanza(entity);
	boolean proceduraModificata = false;
	if (nuovoCodiceProcedura != null && oldCodiceProcedura != null) {
	    if (!nuovoCodiceProcedura.equals(oldCodiceProcedura)) {
		proceduraModificata = true;
	    }
	}
	// BOCCI 2011-11-24 - SE L'AZIONE DELL'ISTANZA È CAMBIATA RICALCOLO LE PROPRIETA' DELL'ATTIVITA'
	if (!vecchiaAzione.equalsIgnoreCase(nuovaAzione)) {
	    if (EntityUtils.getNestedProperty(entity.getAttivita(), "id.codice") != null) {
		log.debug("update: L'azione è cambiata da {} a {}.");
		IAttivita attivita = iAttivitaService.findById(entity.getAttivita().getId());
		if (attivita != null) {
		    this.eventPublisher.publish(new EventoIstanzaModificaAzione(entity.getId().getCodice()));
		}
	    }
	}
	// END BOCCI 2011-11-24
	// controllo se cambiata procedura
	postUpdateActions(entity, insertMovAvvio, movimentoAvvio, proceduraModificata);
    }

    private void checkProcuraChange(Integer codiceIstanza, Integer codiceRichiedenteOld, Integer codiceRichiedenteNew, Istanze entity) {

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
		    istanzeeventiService.insert("E' stata modificata un'anagrafica legata alle procure (rif old=" + codiceRichiedenteOld +
						", rif new=" + codiceRichiedenteNew + ")",
			    IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, entity);
		}
	    }
	}
    }

    /**
     * La funzione aggiorna i permessi per l'istanza. Se valorizzati inserisce i permessi per operatore, responsabile
     * del procedimento e dell'istruttoria (se non sono stati già inseriti).
     * 
     * @param entity
     */
    private void aggiornaPermessiIstanza(Istanze entity) {

	// 1. gestisco i permessi dei responsabili individuati per la pratica
	Responsabili operatore = entity.getResponsabile();
	this.inserisciPermessoIstanza(entity, operatore);
	Responsabili respProcedimento = entity.getResponsabileProcedimento();
	this.inserisciPermessoIstanza(entity, respProcedimento);
	Responsabili respIstruttoria = entity.getIstruttore();
	this.inserisciPermessoIstanza(entity, respIstruttoria);
	// 2. se l'operatore è legato a qualche amministrazione interna
	// allora inserisco gli operatori/ruoli collegati all'istanza
	//	if (operatore != null) {
	//	    operatore = responsabiliService.findById(new PkId(operatore.getId().getCodice()));
	//	    Set<Amministrazioniresponsabili> responsabileAmministrazionis = operatore.getResponsabiliamministrazionis();
	//	    for (Amministrazioniresponsabili responsabileAmministrazione : responsabileAmministrazionis) {
	//		Amministrazioni amministrazioni = responsabileAmministrazione.getAmministrazioni();
	//		Set<Amministrazioniresponsabili> amministrazioniresponsabiles = amministrazioni.getAmministrazioniresponsabilis();
	//		for (Amministrazioniresponsabili amministrazioniresponsabili : amministrazioniresponsabiles) {
	//		    Responsabili operatoreAmm = amministrazioniresponsabili.getResponsabili();
	//		    this.inserisciPermessoIstanza(entity, operatoreAmm);
	//		}
	//		Set<Amministrazioniruoli> ruoli = amministrazioni.getAmministrazioniruolis();
	//		for (Amministrazioniruoli amministrazioniruoli : ruoli) {
	//		    this.inserisciRuoloIstanza(entity, amministrazioniruoli.getRuoli());
	//		}
	//	    }
	//	}
	// 3. Tutti i ruoli dell'intervento (Alberoproc) dell'istanza	
	if (EntityUtils.getNestedProperty(entity.getAlberoproc(), "id.codice") != null) {
	    AlberoprocHelper alberoprocHelper = alberoprocService.findAlberoprocHelper(entity.getAlberoproc());
	    Set<AlberoprocRuoli> alberoprocRuoli = alberoprocHelper.getAlberoprocRuolis();
	    for (AlberoprocRuoli alberoprocRuolo : alberoprocRuoli) {
		this.inserisciRuoloIstanza(entity, alberoprocRuolo.getRuoli());
	    }
	}
    }

    @Override
    public void delete(Istanze entity) {

	// Creo un oggetto ISTANZEDELETE
	log.debug("Creazione dell'oggetto IstanzeDelete....");
	Istanzedelete istanzedelete = istanzedeleteService.populateIstanzedelete(entity);
	// Salviamo sulla tabella ISTANZEDELETE le informazioni di testata dell'istanza e la data di cancellazione
	log.debug("Salvataggio dati dell'istanza cancellata,nella tabella ISTANZEDELETE....");
	istanzedeleteService.insert(istanzedelete);
	log.debug("Cancellaziome dell'istanza......");
	if (isDeleteAllowed(entity)) {
	    Integer codiceIstanza = entity.getId().getCodice();
	    String uuidIstanza = entity.getUuid();
	    CodiceDescrizioneBean identificativipraticaldp = null;
	    boolean attivaVertLDP = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAIONE_SIT_LDP);
	    if (attivaVertLDP) {
		Verticalizzazioniparametri mostraGestioneAreeLDP = verticalizzazioniService.getVerticalizzazioniparametri(
			WebConstants.VERTICALIZZAIONE_SIT_LDP, WebConstants.VERTICALIZZAZIONE_SIT_LDP_GESTISCI_MOD_DEL_AREE);
		if (mostraGestioneAreeLDP != null && StringUtils.defaultString(mostraGestioneAreeLDP.getValore(), "N").equalsIgnoreCase("S")) {
		    identificativipraticaldp = ldpWsClient.getIdentificativiPratica(entity.getId().getCodice());
		} else {
		    attivaVertLDP = false;
		}
	    }
	    childDelete(entity);
	    Boolean isCreataDaStc = entity.getCreatoDaStc();
	    istanzeDAO.delete(entity);
	    if (BooleanUtils.isTrue(isCreataDaStc)) {
		FlashMessages.getInfos().add("È stata cancellata un'istanza derivante da STC");
	    }
	    istanzeDAO.flush();
	    this.eventPublisher.publish(new EventoIstanzaCancellata(codiceIstanza, uuidIstanza));
	    try {
		cancellazioneRemota(identificativipraticaldp, attivaVertLDP);
	    } catch (OperazioniAutomaticheException e) {
		log.error("Errore nelle operazionid di cancellazione remota", e);
	    }
	}
    }

    protected void childDelete(Istanze entity) {

	// Non andrò a cancellare il documento , ma solo a togliere il riferimento all'istanza passata, ina quanto non è 
	// detto che il documento sia strettametente legato all'istanza.
	List<Anagrafedocumenti> anagrafedocumentis = anagrafedocumentiService.findByIstanza(entity);
	for (Anagrafedocumenti anagrafedocumenti : anagrafedocumentis) {
	    anagrafedocumenti.setIstanza(null);
	    anagrafedocumentiService.update(anagrafedocumenti);
	}
	// a. ISTANZELAVORI_T
	Set<IstanzelavoriT> istanzelavoriTs = entity.getIstanzelavoriTs();
	for (IstanzelavoriT istanzelavoriT : istanzelavoriTs) {
	    istanzelavoriTService.delete(istanzelavoriT);
	}
	// b. ISTANZEONERI
	List<Istanzeoneri> istanzeoneris = istanzeoneriService.findByIstanza(entity.getId().getCodice());
	for (Istanzeoneri istanzeoneri : istanzeoneris) {
	    istanzeoneriService.delete(istanzeoneri);
	}
	// c. I_ATTIVITA
	// verificare se l'istanza appartiene ad una attività ( ISTANZE.FK_IDI_ATTIVITA )
	if (EntityUtils.getNestedProperty(entity.getAttivita(), "id.codice") != null) {
	    IAttivita attivita = iAttivitaService.findById(new PkId(entity.getAttivita().getId().getCodice()));
	    if (attivita.getIstanzes().size() > 1) {
		// - se appartiene ad una attività ma non è l'unica istanza
		// - viene cancellata l'istanza
		// - viene ricalcolata l'attività (codiceistanzaultima, attiva, operante)
		// ( il ricalcolo va fatto sempre perché l'istanza cancellata potrebbe essere l'ultima oppure potrebbe
		// essere l'istanza con il movimento che rende operante l'attività )
		try {
		    this.attivitaIstanzeService.scollegaIstanze(entity);
		} catch (BusinessValidationException e) {
		    String messaggio = getMessageFromBundle(e.getMessage(), new Object[] { entity.getNumeroistanza(), attivita.getDenominazione() });
		    throw new RuntimeException(messaggio);
		}
	    } else {
		// - se appartiene ad una attività ed è anche l'unica istanza dell'attività
		// - viene cancellata l'istanza
		// - viene cancellata l'attività
		iAttivitaService.delete(attivita);
	    }
	}
	// d. ISTANZEATTIVITA
	//Set<Istanzeattivita> istanzeattivitas = entity.getIstanzeattivitas();
	List<Istanzeattivita> istanzeattivitas = istanzeattivitaService.findByIstanza(entity, false);
	for (Istanzeattivita istanzeattivita : istanzeattivitas) {
	    istanzeattivitaService.delete(istanzeattivita);
	}
	// f. ISTANZEFIDEJUSSIONI
	//Set<Istanzefidejussioni> istanzefidejussionis = entity.getIstanzefidejussionis();
	List<Istanzefidejussioni> istanzefidejussionis = istanzefidejussioniService.findByIstanze(entity.getId().getCodice());
	for (Istanzefidejussioni istanzefidejussioni : istanzefidejussionis) {
	    istanzefidejussioniService.delete(istanzefidejussioni);
	}
	// g. ISTANZEAFFISSIONI
	//Set<Istanzeaffissioni> istanzeaffissionis = entity.getIstanzeaffissionis();
	List<Istanzeaffissioni> istanzeaffissionis = istanzeaffissioniService.findByIstanze(entity.getId().getCodice());
	for (Istanzeaffissioni istanzeaffissioni : istanzeaffissionis) {
	    istanzeaffissioniService.delete(istanzeaffissioni);
	}
	// i. ISTANZEMAPPALI
	//Set<Istanzemappali> istanzemappalis = entity.getIstanzemappalis();
	List<Istanzemappali> istanzemappalis = istanzemappaliService.findByIstanza(entity.getId().getCodice());
	for (Istanzemappali istanzemappali : istanzemappalis) {
	    istanzemappaliService.delete(istanzemappali);
	}
	// j. CDS
	List<Cds> cdss = cdsService.findByIstanza(entity);
	for (Cds cds : cdss) {
	    cdsService.delete(cds);
	}
	// k. AUTORIZZAZIONI_SUBENTRI	
	List<AutorizzazioniSubentri> autorizzazioniSubentris = autorizzazioniSubentriService.findAutorizzazioniSubentriByIstanza(entity);
	for (AutorizzazioniSubentri autorizzazioniSubentri : autorizzazioniSubentris) {
	    autorizzazioniSubentriService.delete(autorizzazioniSubentri);
	}
	// l-Bis. CONCESSIONI (Dobbiamo cancellarle prima delle autorizzazioni in quanto c'è una FK tra autorizzazioni e autorizzazioniConcessioni
	// quindi se andiamo a cancellare un autorizzazione che ha una conscessione collegata ci da errore)
	List<AutorizzazioniConcessioni> autorizzazioniConcessionis = autorizzazioniConcessioniService
		.findConcessioniByIstanza(entity.getId().getCodice());
	for (AutorizzazioniConcessioni autorizzazioniConcessioni : autorizzazioniConcessionis) {
	    ValidaEliminazioneAutConcCommand cmd = new ValidaEliminazioneAutConcCommand("",
		    autorizzazioniService.findById(new PkId(autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutatt().getId().getCodice())));
	    try {
		EsitoCancellazioneAutOConc esito = autorizzazioniService.validaCancellazioneAutConc(cmd);
		cmd.setEsito(esito);
		autorizzazioniService.deleteConcessione(cmd);
	    } catch (OperazioneCancellazioneAutConcException e) {
		throw new BusinessValidationException(e);
	    }
	}
	// l. AUTORIZZAZIONI 
	List<Autorizzazioni> autorizzazionis = autorizzazioniService.findByIstanza(entity.getId().getCodice());
	for (Autorizzazioni autorizzazioni : autorizzazionis) {
	    ValidaEliminazioneAutConcCommand cmd = new ValidaEliminazioneAutConcCommand("",
		    autorizzazioniService.findById(new PkId(autorizzazioni.getId().getCodice())));
	    try {
		EsitoCancellazioneAutOConc esito = autorizzazioniService.validaCancellazioneAutConc(cmd);
		cmd.setEsito(esito);
		autorizzazioniService.deleteAutorizzazione(cmd);
	    } catch (OperazioneCancellazioneAutConcException e) {
		throw new BusinessValidationException(e);
	    }
	}
	// m. OT_ISTANZE
	// n. ISTANZEALLEGATI	
	List<Istanzeallegati> istanzeallegatis = istanzeallegatiService.findByIstanza(entity.getId().getCodice());
	for (Istanzeallegati istanzeallegati : istanzeallegatis) {
	    istanzeallegatiService.delete(istanzeallegati);
	}
	// q. CONTROLLO
	// 
	// r. CONTROLLOVERIFICHE
	// 
	// s. COLLAUDO
	// 
	// t. COLLAUDOVERIFICHE
	// u. CHIUSUREISTANZA o a cascata
	if (entity.getChiusureistanza() != null) {
	    chiusureistanzaService.delete(entity.getChiusureistanza());
	}
	//	// BATCH_SCAD_ISTANZA
	//	if (entity.getBatchScadIstanze() != null) {
	//	    batchScadIstanzeService.delete(entity.getBatchScadIstanze());
	//	}
	// w. ISTANZERICHIEDENTI
	List<Istanzerichiedenti> istanzerichiedentis = istanzerichiedentiService.findByIstanza(entity);
	for (Istanzerichiedenti istanzerichiedenti : istanzerichiedentis) {
	    istanzerichiedentiService.delete(istanzerichiedenti);
	}
	// x. PERMCDSISTANZE
	// TODO in CDSSERVICE
	// y. TIPIMOVIMENTO_DIS
	List<TipimovimentoDis> tipimovimentoDisList = tipimovimentoDisService.findByIstanza(entity);
	for (TipimovimentoDis tipimovimentoDis : tipimovimentoDisList) {
	    tipimovimentoDisService.delete(tipimovimentoDis);
	}
	// z. DYN_DATI
	//
	// aa. ISTANZEDYN2MODELLIT
	List<Istanzedyn2modellit> istanzedyn2modellits = istanzedyn2modellitService.findByIstanza(new PkId(entity.getId().getCodice()));
	for (Istanzedyn2modellit istanzedyn2modellit : istanzedyn2modellits) {
	    istanzedyn2modellitService.delete(istanzedyn2modellit);
	}
	// aa. ISTANZEDYN2MODELLIT_STORICO 
	List<Istanzedyn2modellitStorico> istanzedyn2modellitStoricos = istanzedyn2modellitStoricoService.findByIstanza(entity.getId().getCodice());
	for (Istanzedyn2modellitStorico istanzedyn2modellit : istanzedyn2modellitStoricos) {
	    istanzedyn2modellitStoricoService.delete(istanzedyn2modellit);
	}
	// bb. ISTANZEDYN2DATI
	List<Istanzedyn2dati> istanzedyn2datis = istanzedyn2datiService.findByIstanza(new PkId(entity.getId().getCodice()));
	for (Istanzedyn2dati istanzedyn2dati : istanzedyn2datis) {
	    istanzedyn2datiService.delete(istanzedyn2dati);
	}
	// bb. ISTANZEDYN2DATI_STORICO 
	List<Istanzedyn2datiStorico> istanzedyn2datiStoricos = istanzedyn2datiStoricoService.findByIstanza(entity.getId().getCodice());
	for (Istanzedyn2datiStorico istanzedyn2dati : istanzedyn2datiStoricos) {
	    istanzedyn2datiStoricoService.delete(istanzedyn2dati);
	}
	// h. ISTANZESTRADARIO SPOSTATO DOPO DYN2DATI PER DIPENDENZA CON CAMPO LOCALIZZAZIONE
	istanzestradarioService.deleteByCodiceIstanza(entity.getId().getCodice());
	// v. DOCUMENTIISTANZA
	List<Documentiistanza> documentiistanzas = documentiistanzaService.findByIstanza(entity.getId().getCodice());
	for (Documentiistanza documentiistanza : documentiistanzas) {
	    documentiistanzaService.delete(documentiistanza);
	}
	// dd. ORARIAPERTURATESTATA	
	List<Orariaperturatestata> orariaperturatestatas = orariaperturatestataService.findByIstanza(entity);
	for (Orariaperturatestata orariaperturatestata : orariaperturatestatas) {
	    orariaperturatestataService.delete(orariaperturatestata);
	}
	// ff. ISTANZEAREE
	List<Istanzearee> istanzearees = istanzeareeService.findByIstanza(entity);
	for (Istanzearee istanzearee : istanzearees) {
	    istanzeareeService.delete(istanzearee);
	}
	// hh. BATCH_SCADENZARIO
	//	Set<BatchScadenzario> batchScadenzarios = entity.getBatchScadenzarios();
	//	for (BatchScadenzario batchScadenzario : batchScadenzarios) {
	//	    batchScadenzarioService.delete(batchScadenzario);
	//	}
	// ii. MOVIMENTI
	List<Movimenti> movimentis = movimentiService.findEseguitiByIstanza(entity);
	for (Movimenti movimenti : movimentis) {
	    movimentiService.delete(movimenti);
	}
	List<Movimenti> movimentiDaEseguires = movimentiService.findDaEseguireByIstanza(entity);
	for (Movimenti movimenti : movimentiDaEseguires) {
	    movimentiService.delete(movimenti);
	}
	List<Movimenti> movimentiDisabilitati = movimentiService.findDisabilitatiByIstanza(entity);
	for (Movimenti movimenti : movimentiDisabilitati) {
	    movimentiService.delete(movimenti);
	}
	// p. ISTANZEPROCEDIMENTI
	List<Istanzeprocedimenti> istanzeprocedimentis = istanzeprocedimentiService.findByIstanze(entity);
	for (Istanzeprocedimenti istanzeprocedimenti : istanzeprocedimentis) {
	    istanzeprocedimentiService.delete(istanzeprocedimenti);
	}
	// jj. ISTANZEHUMMINGBIRD
	if (entity.getIstanzehummingbird() != null) {
	    istanzehummingbirdService.delete(entity.getIstanzehummingbird());
	}
	// Commentato perchè vengono cancellati a cascata quando vengono cancellati  "CcIcalcolotot"
	//	// kk. CC_ICALCOLI
	//	Set<CcIcalcoli> ccIcalcolis = entity.getCcIcalcolis();
	//	for (CcIcalcoli ccIcalcoli : ccIcalcolis) {
	//	    ccIcalcoliService.delete(ccIcalcoli);
	//	}
	// ll. O_ICALCOLOTOT
	List<OIcalcolotot> icalcolotots = oIcalcolototService.findByIstanza(entity.getId().getCodice());
	for (OIcalcolotot oIcalcolotot : icalcolotots) {
	    oIcalcolototService.delete(oIcalcolotot);
	}
	// mm. PARERI
	// nn. SIT_CARTECH
	//	SitCartech sitCartech = entity.getSitCartech();
	//	if (sitCartech != null) {
	//	    sitCartechService.delete(sitCartech);
	//	}
	// oo. ISTANZECALCOLOCANONI_T
	List<IstanzecalcolocanoniT> istanzecalcolocanoniTs = istanzecalcolocanoniTService.findByIstanza(entity);
	for (IstanzecalcolocanoniT istanzecalcolocanoniT : istanzecalcolocanoniTs) {
	    istanzecalcolocanoniTService.delete(istanzecalcolocanoniT);
	}
	// pp. ISTANZEPEOPLED
	//Set<Istanzepeopled> istanzepeopleds = entity.getIstanzepeopleds();
	Istanzepeopled i_peopled = istanzepeopledService.findByIstanza(entity);
	List<Istanzepeopled> istanzepeopleds = new ArrayList<Istanzepeopled>();
	if (i_peopled != null) {
	    istanzepeopleds.add(i_peopled);
	}
	for (Istanzepeopled istanzepeopled : istanzepeopleds) {
	    istanzepeopledService.delete(istanzepeopled);
	}
	// e. ISTANZEREPLICATE //
	Set<Istanzereplicate> istanzereplicatePadres = entity.getIstanzesForFkIstanzapadre();
	for (Istanzereplicate istanzereplicate : istanzereplicatePadres) {
	    istanzereplicateService.delete(istanzereplicate);
	}
	// qq. ISTANZEREPLICATE
	Set<Istanzereplicate> istanzereplicateFiglias = entity.getIstanzesForFkIstanzafiglia();
	for (Istanzereplicate istanzereplicate : istanzereplicateFiglias) {
	    istanzereplicateService.delete(istanzereplicate);
	}
	// REGISTRAZIONI
	Set<Registrazioni> registrazionis = entity.getRegistrazionis();
	for (Registrazioni registrazioni : registrazionis) {
	    registrazioniService.delete(registrazioni);
	}
	// GRADUATORIED
	Set<Graduatoried> graduatorieds = entity.getGraduatorieds();
	for (Graduatoried graduatoried : graduatorieds) {
	    graduatoriedService.delete(graduatoried);
	}
	// SORTEGGIDETTAGLIO
	Set<Sorteggidettaglio> sorteggidettaglios = entity.getSorteggidettaglios();
	for (Sorteggidettaglio sorteggidettaglio : sorteggidettaglios) {
	    sorteggidettaglioService.delete(sorteggidettaglio);
	}
	// ISTANZEEVENTI
	Set<Istanzeeventi> istanzeeventis = entity.getIstanzeeventis();
	for (Istanzeeventi istanzeeventi : istanzeeventis) {
	    istanzeeventiService.delete(istanzeeventi);
	}
	// ISTANZEFRONTOFFICE
	Set<Istanzefrontoffice> istanzefrontoffices = entity.getIstanzefrontoffices();
	for (Istanzefrontoffice istanzefrontoffice : istanzefrontoffices) {
	    istanzefrontofficeService.delete(istanzefrontoffice);
	}
	// ISTANZECOLLEGATE
	istanzecollegateService.deleteCollegamenti(entity);
	// DOMANDESTC
	Set<Domandestc> domandestcs = entity.getDomandestcs();
	for (Domandestc domandestc : domandestcs) {
	    domandestcService.delete(domandestc);
	}
	//CC_ICALCOLITOT
	List<CcIcalcolotot> ccIcalcolitos = ccIcalcolototService.findByIstanza(entity);
	for (CcIcalcolotot ccIcalcolitot : ccIcalcolitos) {
	    ccIcalcolototService.delete(ccIcalcolitot);
	}
	List<Istanzeprocure> istanzeprocures = istanzeprocureService.findByIstanza(entity.getId().getCodice());
	for (Istanzeprocure istanzeprocure : istanzeprocures) {
	    istanzeprocureService.delete(istanzeprocure);
	}
	// o. PERMISTANZE
	Set<Permistanze> permistanzes = entity.getPermistanzes();
	for (Permistanze permistanze : permistanzes) {
	    permistanzeService.delete(permistanze);
	}
	// cc. ISTANZERUOLI
	Set<Istanzeruoli> istanzeruolis = entity.getIstanzeruolis();
	for (Istanzeruoli istanzeruoli : istanzeruolis) {
	    istanzeruoliService.delete(istanzeruoli);
	}
	List<IstanzeRi> istanzeRis = istanzeRiService.findByIstanza(entity.getId().getCodice());
	for (IstanzeRi istanzeRi : istanzeRis) {
	    istanzeRiService.delete(istanzeRi);
	}
	// cd. ISTANZE_ACCESSO_ATTI_T
	List<IstanzeAccessoAttiT> IstanzeAccessoAttiTs = istanzeAccessoAttiTService.findByIstanza(entity.getId().getCodice());
	for (IstanzeAccessoAttiT istanzeAccessoAttiT : IstanzeAccessoAttiTs) {
	    istanzeAccessoAttiTService.delete(istanzeAccessoAttiT);
	}
	// cd. ISTANZE_ACCESSO_ATTI_LOG
	List<IstanzeAccessoAttiLog> istanzeAccessoAttiLogs = istanzeAccessoAttiLogService.findByIstanza(entity.getId().getCodice(), null, null);
	for (IstanzeAccessoAttiLog istanzeAccessoAttiLog : istanzeAccessoAttiLogs) {
	    istanzeAccessoAttiLogService.delete(istanzeAccessoAttiLog);
	}
	istanzeMetadatiService.deleteByIstanza(entity.getId().getCodice());
    }

    @Override
    public List<Istanze> findAll(Integer firstResult, Integer maxResult) {

	return istanzeDAO.findAll(null, null, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "numeroistanza", DAOOrderTypeEnum.ASC);
    }

    /**
     * Facciamo ritornare l'oggetto AlberoProcHelper perchè, verrà utilizzato dal childDataInsert(..) per inserire gli
     * endo procedimenti con figurati come "richiesti da back office" sulla voce dell'albero (ed ereditati)
     * 
     * @param entity
     * @param isInsert
     * @return
     */
    private AlberoprocHelper dataIntegration(Istanze entity, boolean isInsert) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'istanza passata è nulla");
	}
	Date datavalidita = entity.getDatavalidita();
	if (datavalidita != null) {
	    Calendar c = Calendar.getInstance();
	    c.setTime(datavalidita);
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    Date _datavaliditaWithoutTime = new Date(c.getTimeInMillis());
	    entity.setDatavalidita(_datavaliditaWithoutTime);
	}
	// 
	if (isInsert) {
	    entity.setUuid(UUID.randomUUID().toString());
	}
	AlberoprocHelper helper = null;
	if (EntityUtils.getNestedProperty(entity, "alberoproc.id.codice") != null) {
	    Alberoproc alberoproc = alberoprocService.bindDomainObject(entity.getAlberoproc(), PkId.class, "id.codice");
	    helper = alberoprocService.findAlberoprocHelper(alberoproc);
	}
	if (StringUtils.isBlank(entity.getAzione())) {
	    if (helper != null) {
		if (EntityUtils.getNestedProperty(helper.getAzione(), "azId") != null) {
		    entity.setAzione(helper.getAzione().getAzAzione());
		    if (entity.getIstanzeprocedimentis() != null) {
			if (entity.getIstanzeprocedimentis().size() > 0) {
			    List<Integer> codiciInventarioList = new ArrayList<Integer>();
			    for (Istanzeprocedimenti procedimento : entity.getIstanzeprocedimentis()) {
				codiciInventarioList.add(procedimento.getId().getCodiceinventario());
			    }
			    Azioni azione = alberoprocService.findAzioniDaEndoOAlberoproc(helper.getCurrentAlberoproc(), codiciInventarioList);
			    entity.setAzione(azione.getAzAzione());
			}
		    }
		}
	    }
	}
	// ricavo la procedura se non presente
	if (EntityUtils.getNestedProperty(entity, "procedura.id.codice") == null) {
	    if (StringUtils.isNotBlank(entity.getNatura())) {
		//	Se dettaglioPratica.NATURA_FO non è impostato allora la procedura scelta è quella indicata nell’albero (logica attuale).
		//	Se dettaglioPratica.NATURA_FO è impostata ed è diversa dalla natura recuperata dall’albero allora la procedura 
		//	legata all’istanza si calcola in base alla tabella NATURE_PROCEDURE.
		//	La natura della procedura setta ISTANZE.NATURA, quindi la natura dell’istanza è sempre impostata.
		//	Nel metodo richiestaPratica di STC il tag dettaglioPratica.NATURA_FO non deve essere popolato con ISTANZE.NATURA.
		String naturaEndo = "Non Definita";
		Tipiprocedure procedura = null;
		if (helper != null) {
		    procedura = helper.getTipoProcedura();
		    if (procedura != null) {
			if (procedura.getNaturaendo() != null) {
			    naturaEndo = StringUtils.defaultString(procedura.getNaturaendo().getNaturabase());
			}
		    }
		}
		if (entity.getNatura().equalsIgnoreCase(naturaEndo) && procedura != null) {
		    entity.setProcedura(procedura);
		} else {
		    Tipiprocedure tp = natureProcedureService.findByNatura(entity.getNatura());
		    if (tp != null) {
			entity.setProcedura(tp);
		    } else {
			Istanzeeventi ie = new Istanzeeventi();
			Categorieeventibase ceb = new Categorieeventibase();
			ceb.setId(IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI);
			ie.setCategorieeventibase(ceb);
			ie.setDescrizione("E' stata definita una natura di base di tipo " + entity.getNatura() +
					  " ma non e' stata trovata la configurazione nella tabella NATURE_PROCEDURE. Viene impostata la procedura predefinita prevista nella configurazione dell'intervento");
			// Se il prezzo è diverso metto su istanze eventi
			// il fatto che l'importo è diverso			    
			entity.getIstanzeeventis().add(ie);
			entity.setProcedura(procedura);
		    }
		}
	    } else {
		if (helper != null) {
		    log.debug("tipoprocedura" + helper.getTipoProcedura() + "-->" + EntityUtils.getNestedProperty(entity, "alberoproc.id.codice"));
		    Tipiprocedure procedura = helper.getTipoProcedura();
		    if (procedura != null) {
			entity.setProcedura(procedura);
			if (procedura.getNaturaendo() != null) {
			    entity.setNatura(procedura.getNaturaendo().getNaturabase());
			}
		    }
		}
	    }
	} else {
	    //	TODO	La natura della procedura setta ISTANZE.NATURA, quindi la natura dell’istanza è sempre impostata.
	}
	// ricavo il tipo movimento di avvio se non presente
	String tipomovimento = (String) EntityUtils.getNestedProperty(entity, "tipoMovimentoAvvio.id.tipomovimento");
	if (StringUtils.isBlank(tipomovimento)) {
	    if (EntityUtils.getNestedProperty(entity, "procedura.id.codice") != null) {
		Tipiprocedure procedura = tipiprocedureService.findById(new PkId(entity.getProcedura().getId().getCodice()));
		Set<Tipiprocedureavvio> tpAvvios = procedura.getTipiProcedureavvios();
		for (Tipiprocedureavvio tipiprocedureavvio : tpAvvios) {
		    if (BooleanUtils.isTrue(tipiprocedureavvio.getDefaultsn())) {
			entity.setTipoMovimentoAvvio(tipiprocedureavvio.getTipoMovimento());
			break;
		    }
		}
	    }
	}
	if (EntityUtils.getNestedProperty(entity.getComune(), "codicecomune") == null) {
	    List<Comuniassociati> comuniassociatis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	    if (comuniassociatis.size() == 1) {
		Comuniassociati ca = comuniassociatis.get(0);
		Comuni comune = ca.getComune();
		entity.setComune(comune);
	    }
	}
	// cerco di ricavare il responsabile
	if (EntityUtils.getNestedProperty(entity.getResponsabile(), "id.codice") == null) {
	    // Ci vuole una regola per le chiamate WS
	    IstanzeBusinessRules rules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
	    boolean sistemaOperatore = rules
		    .getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserisciResponsabileProcedimentoseNonPresenteOperatore.name());
	    if (sistemaOperatore) {
		if (EntityUtils.getNestedProperty(entity.getResponsabileProcedimento(), "id.codice") != null) {
		    Responsabili operatore = responsabiliService.bindDomainObject(entity.getResponsabileProcedimento(), PkId.class, "id.codice");
		    entity.setResponsabile(operatore);
		} else {
		    if (helper != null) {
			if (helper.getResponsabile() != null) {
			    entity.setResponsabile(helper.getResponsabile());
			}
		    }
		}
	    }
	}
	if (isInsert) {
	    // Chiocci 27 Settembre 2011 - così di default anche le istanze da online recuperano il responsabile del procedimento
	    if (EntityUtils.getNestedProperty(entity.getResponsabileProcedimento(), "id.codice") == null) {
		if (helper != null) {
		    if (helper.getResponsabile() != null) {
			entity.setResponsabileProcedimento(helper.getResponsabile());
		    }
		}
	    }
	    // Chiocci 30 Dicembre 2013 - così di default anche le istanze da online recuperano il responsabile dell'istruttoria
	    // il secondo controllo controlla che per l'albero proc scelto non ci sia un assegnazione dell'istruttore all'interno di un gruppo
	    if (EntityUtils.getNestedProperty(entity.getIstruttore(), "id.codice") == null && helper != null
		    && EntityUtils.getNestedProperty(helper.getGruppiIstruttori(), "id.codice") == null) {
		if (helper != null) {
		    if (helper.getRespistruttoria() != null) {
			entity.setIstruttore(helper.getRespistruttoria());
		    }
		}
	    }
	}
	//. Gruppi istruttori //GIANAPOLO
	if (EntityUtils.getNestedProperty(entity.getGruppiIstruttori(), "id.codice") == null) {
	    if (helper != null) {
		if (EntityUtils.getNestedProperty(helper.getGruppiIstruttori(), "id.codice") != null) {
		    entity.setGruppiIstruttori(helper.getGruppiIstruttori());
		    entity.setGrpFlagAssegnazioneAut(helper.getGrpFlagAssegnazioneAut());
		    entity.setGrpFlagAccettazione(helper.getGrpFlagAccettazione());
		}
	    }
	}
	if (EntityUtils.getNestedProperty(entity.getChiusura(), "id.codicestato") == null) {
	    Statiistanza statiistanza = null;
	    StatiistanzaId id = new StatiistanzaId(WebConstants.STATO_ISTANZA_APERTA_DEFAULT);
	    statiistanza = statiistanzaService.findById(id);
	    if (statiistanza == null) { // cerco il primo con comportamento aperto
		List<Statiistanza> statistanzas = statiistanzaService.findByStatocomportamentoAperte();
		for (Statiistanza si : statistanzas) {
		    statiistanza = si;
		    break;
		}
	    }
	    if (statiistanza == null) {
		throw new InvalidConfigurationException(
			"Errore in cofigurazione. Non sono stati configurati stati istanza con comportamento aperto, e non è presente lo stato con codice AT");
	    }
	    entity.setChiusura(statiistanza);
	}
	// NUMERO ISTANZA
	if (StringUtils.isBlank(entity.getNumeroistanza())) {
	    if (EntityUtils.getNestedProperty(entity, "alberoproc.id.codice") != null) {
		String numeroistanza = this.findProgressivoIstanza(entity.getAlberoproc().getId().getCodice(), false);
		entity.setNumeroistanza(numeroistanza);
	    }
	}
	// GESTIONE PASSWORD DEL TECNICO
	if (StringUtils.isBlank(entity.getPassword())) {
	    String password = Utilities.generaPassword(WebConstants.LUNGHEZZA_PASSWORD);
	    entity.setPassword(password);
	}
	fixMergeEntityProperties(entity);
	gestAnagrafeStorico(entity);
	// CHIOCCI 2012-11-27 Nel caso di software SS,AP,SU se ISTANZE.CODICEPRATICATELEMATICA è vuoto allora viene generato con CFRICHIEDENTE-YYYYMMDD-HHMI
	gestCodicePraticaTelematica(entity);
	// gestisce il campo transiet orario che va associata la campo date (data di inserimento
	//istanza visualizzata a video)
	if (StringUtils.isNotBlank(entity.getOraInserimento())) {
	    Date date = entity.getData();
	    date = Utilities.addTime(date, entity.getOraInserimento());
	    entity.setData(date);
	}
	//. Popola il campo Lavori (Oggetto della pratica) con un valore di default. Il valore
	// è la descrizione dell'intervento e viene recuperato solo se il flag flagOggettoPraticaDefault==true
	if (StringUtils.isBlank(entity.getLavori())) {
	    if (helper != null) {
		if (helper.isFlagOggettoPraticaDefault()) {
		    entity.setLavori(entity.getAlberoproc().getVwAlberoproc().getScDescrizione());
		}
	    }
	}
	if (EntityUtils.isNestedPropertyBlank(entity.getAmministrazioni(), "id.codice")) {
	    if (helper != null) {
		if (helper.getAmministrazioni() != null && helper.getAmministrazioni().getId() != null
			&& helper.getAmministrazioni().getId().getCodice() != null) {
		    entity.setAmministrazioni(helper.getAmministrazioni());
		}
	    }
	}
	return helper;
    }

    /**
     * Nel caso di software SS,AP,SU se ISTANZE.CODICEPRATICATELEMATICA è vuoto allora viene generato con
     * CFRICHIEDENTE-YYYYMMDD-HHMI
     * 
     * @param entity
     */
    private void gestCodicePraticaTelematica(Istanze entity) {

	// String software = ORMHelper.getSoftware();
	// BOCCI 2020-03-24 IL COMPORTAMENTO DELLA GENERAZIONE CODPRATICA TELEMATICA SENZA CONTROLLARE LA REGOLA E VALIDO PER TUTTI
	// Verifica se attiva la verticalizzazione GENERA_COD_PRATICA_TELEMATICA per il software corrento o TT, nel caso lo 
	// sia viene calcolato il codice pratica telematico
	//	boolean attiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_GENERA_COD_PRATICA_TELEMATICA, software);
	//	if (attiva) {
	//	if (software.equalsIgnoreCase("AP") || software.equalsIgnoreCase("SS") || software.equalsIgnoreCase("SU")) {
	if (entity != null) {
	    if (StringUtils.isBlank(entity.getCodicepraticatel())) {
		if (entity.getRichiedente() != null) {
		    String cfPiva = "";
		    if (StringUtils.isNotBlank(entity.getRichiedente().getCodicefiscale())) {
			cfPiva = entity.getRichiedente().getCodicefiscale();
		    } else if (StringUtils.isNotBlank(entity.getRichiedente().getPartitaiva())) {
			cfPiva = entity.getRichiedente().getPartitaiva();
		    }
		    if (StringUtils.isNotBlank(cfPiva)) {
			String codicepraticatel = entity.getRichiedente().getCodicefiscale();
			String dataDellaPratica = "";
			Calendar c = Calendar.getInstance();
			if (entity.getData() != null) {
			    Calendar now = Calendar.getInstance();
			    c.setTime(entity.getData());
			    c.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY));
			    c.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
			    c.set(Calendar.SECOND, now.get(Calendar.SECOND));
			}
			try {
			    SimpleDateFormat sdf = new SimpleDateFormat("-ddMMyyyy-HHmm");
			    dataDellaPratica = sdf.format(c.getTime());
			} catch (Exception e) {
			    log.error("formatDate: {}", e.getMessage());
			}
			codicepraticatel += dataDellaPratica;
			entity.setCodicepraticatel(StringUtils.defaultString(codicepraticatel).toUpperCase());
		    }
		}
	    }
	}
	//	} else {
	//	    log.warn("Non è attiva per il software la verticalizzazione che permette la generazione automatica del codice pratica telematica");
	//	}
    }

    protected void fixMergeEntityProperties(Istanze entity) {

	Amministrazioni amm = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	entity.setAmministrazioni(amm);
	Responsabili responsabile = responsabiliService.bindDomainObject(entity.getResponsabile(), PkId.class, "id.codice");
	entity.setResponsabile(responsabile);
	Responsabili op = responsabiliService.bindDomainObject(entity.getOperatoreInCarico(), PkId.class, "id.codice");
	entity.setOperatoreInCarico(op);
	Responsabili istruttore = responsabiliService.bindDomainObject(entity.getIstruttore(), PkId.class, "id.codice");
	entity.setIstruttore(istruttore);
	Responsabili respproc = responsabiliService.bindDomainObject(entity.getResponsabileProcedimento(), PkId.class, "id.codice");
	entity.setResponsabileProcedimento(respproc);
	Anagrafe richiedente = anagrafeService.bindDomainObject(entity.getRichiedente(), PkId.class, "id.codice");
	entity.setRichiedente(richiedente);
	Anagrafe professionista = anagrafeService.bindDomainObject(entity.getProfessionista(), PkId.class, "id.codice");
	entity.setProfessionista(professionista);
	Anagrafe titolarelegale = anagrafeService.bindDomainObject(entity.getTitolarelegale(), PkId.class, "id.codice");
	entity.setTitolarelegale(titolarelegale);
	Tipiprocedure procedura = tipiprocedureService.bindDomainObject(entity.getProcedura(), PkId.class, "id.codice");
	entity.setProcedura(procedura);
	Impianti impianto = impiantiService.bindDomainObject(entity.getImpianto(), PkId.class, "id.codice");
	entity.setImpianto(impianto);
	Tipimovimento tipimovimento = tipiMovimentoService.bindDomainObject(entity.getTipoMovimentoAvvio(), TipimovimentoId.class,
		"id.tipomovimento");
	entity.setTipoMovimentoAvvio(tipimovimento);
	Tipiarchivioistanze tipiarchivioistanze = tipiarchivioistanzeService.bindDomainObject(entity.getTipiarchivioistanza(), PkId.class,
		"id.codice");
	entity.setTipiarchivioistanza(tipiarchivioistanze);
	Comuni comune = comuniService.bindDomainObject(entity.getComune(), String.class, "codicecomune");
	entity.setComune(comune);
	Alberoproc alberoproc = alberoprocService.bindDomainObject(entity.getAlberoproc(), PkId.class, "id.codice");
	entity.setAlberoproc(alberoproc);
	Tipologiaistanza tipologiaistanza = tipologiaistanzaService.bindDomainObject(entity.getTipologiaistanza(), PkId.class, "id.codice");
	entity.setTipologiaistanza(tipologiaistanza);
	PuFormati formato = puFormatiService.bindDomainObject(entity.getFormato(), PkId.class, "id.codice");
	entity.setFormato(formato);
	Tipisoggetto tipisoggetto = tipisoggettoService.bindDomainObject(entity.getTipisoggetto(), PkId.class, "id.codice");
	entity.setTipisoggetto(tipisoggetto);
	Aree2 area2 = aree2Service.bindDomainObject(entity.getAree2(), PkId.class, "id.codice");
	entity.setAree2(area2);
	IAttivita attivita = iAttivitaService.bindDomainObject(entity.getAttivita(), PkId.class, "id.codice");
	entity.setAttivita(attivita);
	Statiistanza statiistanza = statiistanzaService.bindDomainObject(entity.getChiusura(), StatiistanzaId.class, "id.codicestato");
	entity.setChiusura(statiistanza);
	GruppiIstruttori gruppiIstruttori = gruppiIstruttoriService.bindDomainObject(entity.getGruppiIstruttori(), PkId.class, "id.codice");
	entity.setGruppiIstruttori(gruppiIstruttori);
	Responsabili istruttoreTemp = responsabiliService.bindDomainObject(entity.getIstruttoreTemp(), PkId.class, "id.codice");
	entity.setIstruttoreTemp(istruttoreTemp);
    }

    private void childDataInsert(Istanze entity, IstanzeListsDTO copia, AlberoprocHelper helper, IstanzeBusinessRules rules) {

	// BEGIN OneToOneDefaultInsert
	// CHIUSUREISTANZA,
	Chiusureistanza chiusuraIstanza = new Chiusureistanza();
	chiusuraIstanza.getId().setCodice(entity.getId().getCodice());
	chiusuraIstanza.getId().setIdcomune(entity.getId().getIdcomune());
	chiusureistanzaService.insert(chiusuraIstanza);
	// ISTANZEHUMMINGBIRD,
	//	Istanzehummingbird istanzehummingbird = new Istanzehummingbird();
	//	istanzehummingbird.getId().setCodice(entity.getId().getCodice());
	//	istanzehummingbird.getId().setIdcomune(entity.getId().getIdcomune());
	//	istanzehummingbirdService.insert(istanzehummingbird);
	// BATCH_SCAD_ISTANZA
	//	BatchScadIstanze batchScadIstanze = new BatchScadIstanze();
	//	batchScadIstanze.getId().setCodice(entity.getId().getCodice());
	//	batchScadIstanze.getId().setIdcomune(entity.getId().getIdcomune());
	//	batchScadIstanze.setSoftware(entity.getSoftware());
	//	batchScadIstanzeService.insert(batchScadIstanze);
	// SIT_CARTECH
	//	SitCartech sitCartech = new SitCartech();
	//	sitCartech.getId().setCodice(entity.getId().getCodice());
	//	sitCartech.getId().setIdcomune(entity.getId().getIdcomune());
	//	sitCartechService.insert(sitCartech);
	// BEGIN OneToOneDefaultInsert
	log.debug("childDataInsert: prima di tempisticaIStanza");
	this.gestisciIstanzetempistica(entity);
	log.debug("childDataInsert: prima di scriviProgressivo");
	this.scriviProgressivo(entity.getNumeroistanza(), entity.getAlberoproc().getId().getCodice(), entity.getSoftware().getCodice(), false);
	// istanzearee
	Set<Istanzearee> istanzearees = copia.getIstanzearees();
	log.debug("childDataInsert: inserisco IstanzeArees={}", istanzearees.size());
	for (Istanzearee istanzearee : istanzearees) {
	    istanzeareeService.insert(istanzearee);
	}
	// permessi e ruoli istanza
	Set<Permistanze> permistanzes = copia.getPermistanzes();
	log.debug("childDataInsert: inserisco PermIstanzes={}", permistanzes.size());
	for (Permistanze permistanze : permistanzes) {
	    permistanzeService.insert(permistanze);
	}
	Set<Istanzeruoli> istanzeruolis = copia.getIstanzeruolis();
	log.debug("childDataInsert: inserisco IstanzeRuolis={}", istanzeruolis.size());
	for (Istanzeruoli istanzeruoli : istanzeruolis) {
	    istanzeruoliService.insert(istanzeruoli);
	}
	// movimenti
	OperazioniAutomaticheBusinessRules opautRules = (OperazioniAutomaticheBusinessRules) SigeproBusinessRules
		.getClassRules(OperazioniAutomaticheBusinessRules.class);
	boolean isNotificaAutomatica = opautRules.isNotificaStcAutomatica();
	// ATTENZIONE (@NOTIFICA_AUTOMATICA)!!! DEVO MODIFICARE LE REGOLE SOLO SE NOTIFICAUTOMAICA È SETTATO TRUE A MONTE DELL'INSERIMENTO DELL'ISTANZA
	// ES. IL PROGRAMMA DI IMPORT SETTA A FALSE LA NOTIFICA AUTOMATICA ED IN QUESTO CASO NON DEVO SETTARLO A CASCATA SUI MOVIMENTI
	// IN QUANTO È GIA' FALSE E LA RIGA SEGUENTE LO METTEREBBE A TRUE ANNULLANDO L'IMPOSTAZIONE GENERALE
	if (isNotificaAutomatica) {
	    opautRules.setNotificaStcAutomatica(false);
	}
	Set<Movimenti> movimentis = copia.getIstanzemovimentis();
	if (!movimentis.isEmpty()) {
	    Movimenti[] movs = new Movimenti[movimentis.size()];
	    movs = movimentis.toArray(movs);
	    Arrays.sort(movs, new MovimentiDataComparator());
	    Date dataValidita = entity.getDatavalidita();
	    log.debug("childDataInsert: inserisco IstanzeMovimentis={}", movimentis.size());
	    for (Movimenti movimenti : movs) {
		movimentiService.insert(movimenti);
		if (dataValidita == null) {
		    dataValidita = movimenti.getIstanza().getDatavalidita();
		}
	    }
	    entity.setDatavalidita(dataValidita);
	}
	// VEDI SOPRA SPIEGAZIONE @NOTIFICA_AUTOMATICA
	if (isNotificaAutomatica) {
	    opautRules.setNotificaStcAutomatica(true);
	}
	// documentiistanza
	Set<Documentiistanza> documentiistanzas = copia.getDocumentiistanzas();
	log.debug("childDataInsert: inserisco Documentiistanzas={}", documentiistanzas.size());
	for (Documentiistanza documentiistanza : documentiistanzas) {
	    documentiistanzaService.insert(documentiistanza);
	}
	Set<Istanzeallegati> istanzeAllegatis = copia.getIstanzeallegatis();
	log.debug("childDataInsert: inserisco IstanzeAllegatis={}", istanzeAllegatis.size());
	for (Istanzeallegati istanzeallegati : istanzeAllegatis) {
	    istanzeallegatiService.insert(istanzeallegati);
	}
	// gli oneri vanno inseriti prima di istanzeprocedimenti
	Set<Istanzeoneri> istanzeoneris = copia.getIstanzeoneris();
	log.debug("childDataInsert: inserisco Istanzeoneris={}", istanzeoneris.size());
	for (Istanzeoneri istanzeoneri : istanzeoneris) {
	    boolean onereDisabilitato = false;
	    if (istanzeoneri.getTipicausalioneri() != null) {
		if (istanzeoneri.getTipicausalioneri().getId() != null) {
		    if (istanzeoneri.getTipicausalioneri().getId().getCodice() != null) {
			Tipicausalioneri tc = tipicausalioneriService.findById(new PkId(istanzeoneri.getTipicausalioneri().getId().getCodice()));
			if (tc == null) {
			    throw new InvalidConfigurationException(
				    "Errore nel recupero della causale onere con codice " + istanzeoneri.getTipicausalioneri().getId().getCodice() +
								    ". Non è stato trpvato nessun record con questo codice");
			}
			onereDisabilitato = tc.getCoDisabilitato() == null ? false : tc.getCoDisabilitato().booleanValue();
		    }
		}
	    }
	    if (!onereDisabilitato) {
		istanzeoneriService.insert(istanzeoneri);
	    }
	}
	Set<Istanzestradario> istanzestradarios = copia.getIstanzestradarios();
	log.debug("childDataInsert: inserisco Istanzestradarios={}", istanzestradarios.size());
	for (Istanzestradario istanzestradario : istanzestradarios) {
	    istanzestradarioService.insert(istanzestradario);
	}
	Set<Istanzedyn2modellit> istanzedyn2modellits = copia.getIstanzedyn2modellit();
	log.debug("childDataInsert: inserisco Istanzed2mts={}", istanzedyn2modellits.size());
	for (Istanzedyn2modellit istanzedyn2modellit : istanzedyn2modellits) {
	    istanzedyn2modellitService.insert(istanzedyn2modellit);
	}
	Set<Istanzerichiedenti> istanzerichiedentis = copia.getIstanzerichiedentis();
	log.debug("childDataInsert: inserisco Istanzerichiedentis={}", istanzerichiedentis.size());
	for (Istanzerichiedenti istanzerichiedenti : istanzerichiedentis) {
	    istanzerichiedentiService.insert(istanzerichiedenti);
	}
	// VEDI SOPRA SPIEGAZIONE @NOTIFICA_AUTOMATICA
	if (isNotificaAutomatica) {
	    opautRules.setNotificaStcAutomatica(false);
	}
	Set<Istanzeprocedimenti> istanzeprocedimentis = copia.getIstanzeprocedimentis();
	log.debug("childDataInsert: inserisco Istanzeprocedimentis={}", istanzeprocedimentis.size());
	for (Istanzeprocedimenti istanzeprocedimenti : istanzeprocedimentis) {
	    istanzeprocedimentiService.insert(istanzeprocedimenti);
	}
	// ///////////////////////////////////////////GIANPAOLO ///////////////////////////////////////////////////////
	// Inserire quelli richiesti da BO
	// Recupero la lista degli endo procedimenti associati alla voce dell'albero dell'istanza
	if (helper.getAlberoprocEndos() != null) {
	    Set<AlberoprocEndo> alberoprocEndoEreditatiList = helper.getAlberoprocEndos();
	    for (AlberoprocEndo alberoprocEndoEreditati : alberoprocEndoEreditatiList) {
		// Se l'endo ha il flag FlagRichiestoBo allora deve essere inserito sempre
		if (alberoprocEndoEreditati.getFlagRichiestoBo() != null && alberoprocEndoEreditati.getFlagRichiestoBo().equals(Boolean.TRUE)) {
		    // Controllo se già l'endo è stato inserito, se no lo inserisco
		    IstanzeprocedimentiId idEreditato = new IstanzeprocedimentiId(entity.getId().getCodice(),
			    alberoprocEndoEreditati.getInventarioprocedimento().getId().getCodice());
		    Istanzeprocedimenti istanzeprocedimentiEreditatiTemp = istanzeprocedimentiService.findById(idEreditato);
		    if (istanzeprocedimentiEreditatiTemp == null) {
			istanzeprocedimentiEreditatiTemp = new Istanzeprocedimenti();
			istanzeprocedimentiEreditatiTemp.setIstanza(entity);
			istanzeprocedimentiEreditatiTemp.setId(idEreditato);
			istanzeprocedimentiEreditatiTemp.setInventarioprocedimenti(alberoprocEndoEreditati.getInventarioprocedimento());
			istanzeprocedimentiEreditatiTemp.setDataattivazione(new Date());
			istanzeprocedimentiService.insert(istanzeprocedimentiEreditatiTemp);
		    }
		}
	    }
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Lo stesso ragionamento deve essere fatto per quelli dell'albero
	// VEDI SOPRA SPIEGAZIONE @NOTIFICA_AUTOMATICA
	if (isNotificaAutomatica) {
	    opautRules.setNotificaStcAutomatica(true);
	}
	Set<Istanzeattivita> istanzeattivitas = copia.getIstanzeattivitas();
	log.debug("childDataInsert: inserisco Istanzeattivitas={}", istanzeattivitas.size());
	for (Istanzeattivita istanzeattivita : istanzeattivitas) {
	    istanzeattivitaService.insert(istanzeattivita);
	}
	Set<Orariaperturatestata> orariaperturatestatas = copia.getOrariaperturatestatas();
	log.debug("childDataInsert: inserisco orariaperturatestatas={}", orariaperturatestatas.size());
	for (Orariaperturatestata orariaperturatestata : orariaperturatestatas) {
	    orariaperturatestataService.insert(orariaperturatestata);
	}
	Set<Istanzeeventi> istanzeeventis = copia.getIstanzeeventis();
	log.debug("childDataInsert: inserisco Istanzeeventis={}", istanzeeventis.size());
	for (Istanzeeventi istanzeeventi : istanzeeventis) {
	    istanzeeventiService.insert(istanzeeventi);
	}
	Set<Istanzedyn2dati> istanzedyn2datis = copia.getIstanzedyn2datis();
	log.debug("childDataInsert: inserisco istanzedyn2datis={}", istanzedyn2datis.size());
	for (Istanzedyn2dati istanzedyn2dati : istanzedyn2datis) {
	    istanzedyn2datiService.insert(istanzedyn2dati);
	}
	Set<Autorizzazioni> autorizzazionis = copia.getAutorizzazionis();
	log.debug("childDataInsert: inserisco autorizzazionis={}", autorizzazionis.size());
	for (Autorizzazioni autorizzazioni : autorizzazionis) {
	    if (autorizzazioni.getAutorizzazioniConcessionisForFkAutconcAutatt().size() > 0) {
		AutorizzazioniConcessioni conc = null;
		Set<AutorizzazioniConcessioni> concs = autorizzazioni.getAutorizzazioniConcessionisForFkAutconcAutatt();
		for (AutorizzazioniConcessioni autorizzazioniConcessioni : concs) {
		    conc = autorizzazioniConcessioni;
		    break;
		}
		if (conc != null) {
		    conc.setAutorizzazioniByFkAutconcAutatt(autorizzazioni);
		    autorizzazioniService.insertConcessione(conc);
		}
	    } else {
		autorizzazioniService.insertAutorizzazione(autorizzazioni);
	    }
	}
	Set<Istanzeprocure> istanzeprocures = copia.getIstanzeprocures();
	log.debug("childDataInsert: inserisco Istanzeprocures={}", istanzeprocures.size());
	for (Istanzeprocure istanzeprocure : istanzeprocures) {
	    istanzeprocureService.insert(istanzeprocure);
	}
	Set<Istanzecollegate> istanzecollegates = copia.getIstanzecollegates();
	for (Istanzecollegate ic : istanzecollegates) {
	    // TODO COLLEGATO A NLAHELPERSERVICEIMPL.populateIstanzacollegata
	    if (ic.getIstanzaDacollegare() != null && ic.getIstanzaDacollegare().getId() != null
		    && ic.getIstanzaDacollegare().getId().getCodice() != null) {
		// se istanza dacollegare non è nulla allora questa istanza è padre
		istanzecollegateService.insertCollegamento(ic.getIstanzaDacollegare(), entity, false);
	    } else {
		// inserisciComePadre
		// se istanza non è nulla allora questa istanza è figlia
		if (ic.getIstanza() != null && ic.getIstanza().getId() != null && ic.getIstanza().getId().getCodice() != null)
		    istanzecollegateService.insertCollegamento(ic.getIstanza(), entity, false);
	    }
	}
	for (IstanzeMetadati istanzeMetadati : entity.getIstanzeMetadati()) {
	    istanzeMetadati.getId().setCodiceistanza(entity.getId().getCodice());
	    istanzeMetadatiService.insert(istanzeMetadati);
	}
	// entity.setAttivitas(new HashSet<IAttivita>());
	// entity.setAutorizzazionisubentris(new HashSet<AutorizzazioniSubentri>());
	// entity.setCcIcalcoliDettagliors(new HashSet<CcIcalcoliDettaglior>());
	// entity.setCcIcalcoliDettagliots(new HashSet<CcIcalcoliDettagliot>());
	// entity.setCcIcalcolis(new HashSet<CcIcalcoli>());
	// entity.setCcIcalcoloDcontribattivs(new HashSet<CcIcalcoloDcontribattiv>());
	// entity.setCcIcalcoloDcontributos(new HashSet<CcIcalcoloDcontributo>());
	// entity.setCcIcalcolotcontributoRiduzs(new HashSet<CcIcalcolotcontributoRiduz>());
	// entity.setCcIcalcoloTcontributos(new HashSet<CcIcalcoloTcontributo>());
	// entity.setCcIcalcolotots(new HashSet<CcIcalcolotot>());
	// entity.setCcItabella1s(new HashSet<CcItabella1>());
	// entity.setCcItabella2s(new HashSet<CcItabella2>());
	// entity.setCcItabella3s(new HashSet<CcItabella3>());
	// entity.setCcItabella4s(new HashSet<CcItabella4>());
	// entity.setCdss(new HashSet<Cds>());
	// entity.setGraduatorieds(new HashSet<Graduatoried>());
	// entity.setIstanzeaffissionis(new HashSet<Istanzeaffissioni>());
	// entity.setIstanzecalcolocanoniTs(new HashSet<IstanzecalcolocanoniT>());
	// entity.setIstanzeeventis(new HashSet<Istanzeeventi>());
	// entity.setIstanzefidejussionis(new HashSet<Istanzefidejussioni>());
	// entity.setIstanzefrontoffices(new HashSet<Istanzefrontoffice>());
	// entity.setIstanzelavoriTs(new HashSet<IstanzelavoriT>());
	// entity.setIstanzemovimentis(new HashSet<Movimenti>());
	// entity.setIstanzeoneriDettaglios(new HashSet<IstanzeoneriDettaglio>());
	// entity.setIstanzeoneris(new HashSet<Istanzeoneri>());
	// entity.setIstanzepeopleds(new HashSet<Istanzepeopled>());
	// entity.setIstanzeprocedimentis(new HashSet<Istanzeprocedimenti>());
	// entity.setIstanzesForFkIstanzafiglia(new HashSet<Istanzereplicate>());
	// entity.setIstanzesForFkIstanzapadre(new HashSet<Istanzereplicate>());
	// entity.setOIcalcolocontribrRiduzs(new HashSet<OIcalcolocontribrRiduz>());
	// entity.setOIcalcolocontribrs(new HashSet<OIcalcolocontribr>());
	// entity.setOIcalcolocontribtBtos(new HashSet<OIcalcolocontribtBto>());
	// entity.setOIcalcolocontribts(new HashSet<OIcalcolocontribt>());
	// entity.setOIcalcoloDettagliors(new HashSet<OIcalcoloDettaglior>());
	// entity.setOIcalcoloDettagliots(new HashSet<OIcalcoloDettagliot>());
	// entity.setOIcalcolotots(new HashSet<OIcalcolotot>());
	// entity.setOrariaperturatestatas(new HashSet<Orariaperturatestata>());
	// entity.setRegistrazionis(new HashSet<Registrazioni>());
	// entity.setSorteggidettaglios(new HashSet<Sorteggidettaglio>());
    }

    private void gestisciIstanzetempistica(Istanze entity) {

	if (EntityUtils.getNestedProperty(entity.getIstanzeTempistica(), "id.codice") == null) {
	    Tipiprocedure procedura = tipiprocedureService.bindDomainObject(entity.getProcedura(), PkId.class, "id.codice");
	    IstanzeTempistica istanzeTempistica = new IstanzeTempistica();
	    PkId id = new PkId(entity.getId().getIdcomune(), entity.getId().getCodice());
	    istanzeTempistica.setId(id);
	    istanzeTempistica.setGiorniprocedura(procedura.getGiorni());
	    istanzeTempisticaService.insert(istanzeTempistica);
	    entity.setIstanzeTempistica(istanzeTempistica);
	}
    }

    /**
     * @param entity
     */
    // private void gestIstanzearee(Istanze entity, boolean isInsert) {
    //
    // boolean primariosettato = false;
    // if (isInsert) {
    // Set<Istanzearee> istanzearees = entity.getTransientIstanzearees();
    // for (Istanzearee istanzearee : istanzearees) {
    // if (EntityUtils.getNestedProperty(istanzearee, "id.codicearea") != null) {
    // Integer codicearea = (Integer) EntityUtils.getNestedProperty(istanzearee, "id.codicearea");
    // Aree area = areeService.findById(new PkId(codicearea));
    // IstanzeareeId id = new IstanzeareeId(entity.getId().getCodice(), codicearea);
    // istanzearee.setId(id);
    // istanzearee.setArea(area);
    // istanzearee.setIstanza(entity);
    // if (istanzearee.getPrimario() == null) {
    // if (!primariosettato) {
    // istanzearee.setPrimario(true);
    // primariosettato = true;
    // } else {
    // istanzearee.setPrimario(false);
    // }
    // }
    // istanzearee.setPrimario(true);
    // istanzeareeService.insert(istanzearee);
    // }
    // }
    // } else {
    // Set<Istanzearee> istanzeareesDTO = entity.getTransientIstanzearees();
    // Set<Istanzearee> istanzearees = entity.getIstanzearees();
    // if (istanzearees.size() > 0) {
    // // se non è stata indicata nel DTO alcuna area devo controllare se posso cancellare quella primaria
    // if (istanzeareesDTO.size() == 0) {
    // primariosettato = false;
    // for (Istanzearee istanzearee : istanzearees) {
    // boolean primario = BooleanUtils.toBoolean(istanzearee.getPrimario());
    // if (primario) {
    // istanzeareeService.delete(istanzearee);
    // // entity.getIstanzearees().remove(istanzearee);
    // } else {
    // if (!primariosettato) {
    // istanzearee.setPrimario(true);
    // istanzeareeService.update(istanzearee);
    // primariosettato = true;
    // }
    // }
    // }
    // } else {
    // primariosettato = false;
    // for (Istanzearee istanzearee : istanzeareesDTO) {
    // if (EntityUtils.getNestedProperty(istanzearee, "id.codicearea") != null) {
    // Integer codicearea = (Integer) EntityUtils.getNestedProperty(istanzearee, "id.codicearea");
    // Aree area = areeService.findById(new PkId(codicearea));
    // IstanzeareeId id = new IstanzeareeId(entity.getId().getCodice(), codicearea);
    // Istanzearee iarea = istanzeareeService.findById(id);
    // if (iarea == null) {
    // istanzearee.setId(id);
    // istanzearee.setArea(area);
    // istanzearee.setIstanza(entity);
    // if (istanzearee.getPrimario() == null) {
    // if (!primariosettato) {
    // istanzearee.setPrimario(true);
    // primariosettato = true;
    // } else {
    // istanzearee.setPrimario(false);
    // }
    // }
    // istanzeareeService.insert(istanzearee);
    // } else {
    // if (iarea.getPrimario() == null) {
    // if (!primariosettato) {
    // iarea.setPrimario(true);
    // primariosettato = true;
    // }
    // }
    // istanzeareeService.update(iarea);
    // }
    // }
    // }
    // }
    // } else {
    // primariosettato = false;
    // for (Istanzearee istanzearee : istanzeareesDTO) {
    // if (EntityUtils.getNestedProperty(istanzearee, "id.codicearea") != null) {
    // Integer codicearea = (Integer) EntityUtils.getNestedProperty(istanzearee, "id.codicearea");
    // Aree area = areeService.findById(new PkId(codicearea));
    // IstanzeareeId id = new IstanzeareeId(entity.getId().getCodice(), codicearea);
    // istanzearee.setId(id);
    // istanzearee.setArea(area);
    // istanzearee.setIstanza(entity);
    // if (istanzearee.getPrimario() == null) {
    // if (!primariosettato) {
    // istanzearee.setPrimario(true);
    // primariosettato = true;
    // } else {
    // istanzearee.setPrimario(false);
    // }
    // }
    // istanzeareeService.insert(istanzearee);
    // }
    // }
    // }
    // }
    // }
    private void insertMovimentoAvvio(Istanze entity) {

	Tipimovimento tipomovimento = entity.getTipoMovimentoAvvio();
	tipomovimento = tipiMovimentoService.findById(tipomovimento.getId());
	Movimenti movimento = new Movimenti();
	movimento.setTipomovimento(tipomovimento);
	movimento.setData(entity.getData());
	movimento.setNumeroprotocollo(entity.getNumeroprotocollo());
	movimento.setDataprotocollo(entity.getDataprotocollo());
	movimento.setDatainserimento(GregorianCalendar.getInstance().getTime());
	if (tipomovimento.getTipologiaesito() != null) {
	    if (tipomovimento.getTipologiaesito().intValue() == 1) {
		movimento.setEsito(Boolean.FALSE);
	    }
	    if (tipomovimento.getTipologiaesito().intValue() == 2) {
		movimento.setEsito(Boolean.TRUE);
	    }
	}
	movimento.setIstanza(entity);
	movimento.setMovimento(tipomovimento.getMovimento());
	movimento.setResponsabile(entity.getResponsabile());
	movimento.setPubblica(tipomovimento.getFlagPubblicamovimento());
	movimento.setPubblicaparere(tipomovimento.getFlagPubblicaparere());
	movimentiService.insert(movimento);
    }

    @Override
    public void inserisciRuoloIstanza(Istanze entity, Ruoli ruolo) {

	if (EntityUtils.getNestedProperty(ruolo, "id.codice") != null) {
	    IstanzeruoliId id = new IstanzeruoliId(entity.getId().getCodice(), ruolo.getId().getCodice());
	    Istanzeruoli istanzeruoli = istanzeruoliService.findById(id);
	    if (istanzeruoli == null) {
		istanzeruoli = new Istanzeruoli();
		istanzeruoli.setId(id);
		istanzeruoli.setIstanze(entity);
		istanzeruoli.setRuolo(ruolo);
		istanzeruoliService.insert(istanzeruoli);
	    }
	}
    }

    @Override
    public void inserisciPermessoIstanza(Istanze entity, Responsabili responsabile) {

	if (EntityUtils.getNestedProperty(responsabile, "id.codice") != null) {
	    boolean esistePermesso = permistanzeService.checkByIstanzaAndResponsabile(entity, responsabile);
	    if (!esistePermesso) {
		PermistanzeId id = new PermistanzeId(entity.getId().getCodice(), responsabile.getId().getCodice());
		Permistanze permistanze = new Permistanze();
		permistanze.setId(id);
		permistanze.setIstanze(entity);
		permistanze.setResponsabile(responsabile);
		permistanzeService.insert(permistanze);
	    }
	}
    }

    private void gestAnagrafeStorico(Istanze entity) {

	if (EntityUtils.getNestedProperty(entity, "richiedente.id.codice") != null) {
	    checkStoricoAnagrafe(entity, "richiedente", "richiedentestorico", entity.getData());
	} else {
	    //..  richiedente è nullo azzero anche il richiedente storico
	    if (EntityUtils.getNestedProperty(entity.getRichiedentestorico(), "id.codice") != null) {
		entity.setRichiedentestorico(null);
	    }
	}
	if (EntityUtils.getNestedProperty(entity, "titolarelegale.id.codice") != null) {
	    checkStoricoAnagrafe(entity, "titolarelegale", "titolarelegalestorico", entity.getData());
	} else {
	    //..  titolare legale è nullo azzero anche il titolare legale storico
	    if (EntityUtils.getNestedProperty(entity.getTitolarelegalestorico(), "id.codice") != null) {
		entity.setTitolarelegalestorico(null);
	    }
	}
	if (EntityUtils.getNestedProperty(entity, "professionista.id.codice") != null) {
	    checkStoricoAnagrafe(entity, "professionista", "professionistastorico", entity.getData());
	} else {
	    // .. professionista è nullo azzero anche il professionista storico
	    if (EntityUtils.getNestedProperty(entity.getProfessionistastorico(), "id.codice") != null) {
		entity.setProfessionistastorico(null);
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

    private boolean validateEntity(Istanze entity, boolean isInsert) {

	ServiceValidationRules validationRule = (ServiceValidationRules) SigeproBusinessRules.getClassRules(ServiceValidationRules.class);
	boolean doBusinessValidation = true;
	if (validationRule != null) {
	    doBusinessValidation = validationRule.getCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name());
	}
	// 1. CONTROLLO CHE IL NUMERO ISTANZA NON SIA GIA' UTILIZZATO
	if (doBusinessValidation) {
	    Configurazione configurazione = configurazioneService.findById(new ConfigurazioneId(ORMHelper.getSoftware()));
	    String numeroIstanza = "";
	    Integer codiceIstanza = null;
	    // se in update devo vedere se il numero istanza è usato da altri
	    if (!isInsert) {
		Istanze copy = this.findById(entity.getId());
		// è importante fare l'evict altrimenti al primo metodo che accede ai dati esegue l'update
		istanzeDAO.evict(copy);
		numeroIstanza = entity.getNumeroistanza();
		codiceIstanza = copy.getId().getCodice();
	    } else {
		// anche in insert
		numeroIstanza = entity.getNumeroistanza();
	    }
	    if (StringUtils.isNotBlank(entity.getNumeroistanza())) {
		checkModificaNumeroIstanza(numeroIstanza, codiceIstanza, isInsert);
	    }
	    // validazione numeroprotocollo e data protocollo obbligatori
	    if (configurazione != null) {
		boolean protocolloObbligatorio = BooleanUtils.toBoolean(configurazione.getProtgenobblig());
		if (protocolloObbligatorio) {
		    List<InvalidValue> ivs = new ArrayList<InvalidValue>();
		    if (StringUtils.isBlank(entity.getNumeroprotocollo())) {
			InvalidValue iv = new InvalidValue("alert.required", entity.getClass(), "numeroprotocollo", null, entity);
			ivs.add(iv);
		    }
		    if (entity.getDataprotocollo() == null) {
			InvalidValue iv = new InvalidValue("alert.required", entity.getClass(), "dataprotocollo", null, entity);
			ivs.add(iv);
		    }
		    if (ivs.size() > 0) {
			throwValidationMessages(ivs);
		    }
		}
		//2. VALIDATIONE CONFIGURAZIONE (regole di validazione date dai valori presenti nella tabella configurazione)
		List<InvalidValue> ivs = new ArrayList<InvalidValue>();
		// A. VALIDAZIONE RICHIEDENTE (se in configurazione il campo flagRichiedentepf=true allora posso inserire solo una
		// anafrafica di tipo PERSONA FISICA)
		if (configurazione.getFlagRichiedentepf() != null && configurazione.getFlagRichiedentepf().equals(true)) {
		    if (EntityUtils.getNestedProperty(entity.getRichiedente(), "id.codice") != null
			    && entity.getRichiedente().getTipoanagrafe().equals(WebConstants.PERSONA_GIURIDICA)) {
			InvalidValue iv = new InvalidValue(getMessageFromBundle("service_error.richiedente_deve_essere_pf", null), null, "", null,
				null);
			ivs.add(iv);
		    }
		}
		// 2012-02-15 BOCCI: LA VALIDAZIONE flagAziendarappresentata DEVE ESSERE EFFETTUATA 
		// B. VALIDAZIONE AZIENDA (rag. sociale) se il flagAziendarappresentata==false ed è stata passata l'azienda allora 
		// si deve rilanciare l'eccezione che il software non prevede la gestione del campo titolare legale
		boolean isFlagAziendarappresentata = configurazione.getFlagAziendarappresentata() == null ? false
			: configurazione.getFlagAziendarappresentata().booleanValue();
		if (!isFlagAziendarappresentata) {
		    if (EntityUtils.getNestedProperty(entity.getTitolarelegale(), "id.codice") != null) {
			InvalidValue iv = new InvalidValue(getMessageFromBundle("service_error.configurazione.flagAziendarappresentata", null), null,
				"", ORMHelper.getSoftware(), null);
			ivs.add(iv);
		    }
		    // 2012-02-15 BOCCI: il flag deve controllare l'obbligatorietà della sola azienda rappresentata e non del tipo soggetto
		    //	    if (EntityUtils.getNestedProperty(entity.getTipisoggetto(), "id.codice") == null) {
		    //		InvalidValue iv = new InvalidValue("alert.required", entity.getClass(), "tipisoggetto", null, entity);
		    //		ivs.add(iv);
		    //	    }
		}
		if (ivs.size() > 0) {
		    throwValidationMessages(ivs);
		}
	    }
	}
	// 3. VALIDO L'OGGETTO DI DOMINIO
	return super.validateEntity(entity);
    }

    /**
     * La funzione verifica che sia possibile modificare il numeroistanza secondo queste condizioni:<br />
     * <ul>
     * <li>Nello stesso software NON sia già presente una pratica con quel numero</li>
     * <li>NON esiste nella tabella domandestc un numeroistanza prenotato di una pratica con errore</li>
     * </ul>
     * 
     * @param numeroIstanza
     */
    private void checkModificaNumeroIstanza(String numeroIstanza, Integer codiceIstanza, boolean isInsert) throws NumeroIstanzaUtilizzatoException {

	if (StringUtils.isBlank(StringUtils.defaultString(numeroIstanza).trim())) {
	    throw new NumeroIstanzaUtilizzatoException("Attenzione!! il parametro numeroistanza passato è nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("numeroistanza", numeroIstanza, String.class));
	if (!isInsert) {
	    fr.addFilterField(FilterUtils.notEquals("id.codice", codiceIstanza, Integer.class));
	}
	ft.addRestriction(fr);
	List<Istanze> list = this.findByFilterTable(ft);
	if (list.size() > 0) {
	    throw new NumeroIstanzaUtilizzatoException("Attenzione! L'istanza " + numeroIstanza + " è presente nel database.");
	}
	// BOCCI 2011-11-22 devo controllare che il numeroistanza non sia stato prenotato da STC. 
	// In questo caso infatti il numeroistanza è nella tabella domandestc e la pratica non è inserita in Istanze
	checkNumeroIstanzaPrenotatoSTC(numeroIstanza);
    }

    /**
     * La funzione controlla se il numero istanza è già usato per una pratica proveniente da STC ma non importata in
     * SIGEPRO. In questo caso il controllo del numero istanza già usato non è valido perchè non presente nella tabella
     * istanze e devo andare a controllare su domandestc dove numeroistanza è uguale a quello passato (CASE INSENSITIVE)
     * e codiceistanza==null (Non è stata creata una pratica altrimenti varrebbe il controllo su ISTANZE.NUMEROISTANZA).
     * 
     * @param numeroistanza
     */
    private void checkNumeroIstanzaPrenotatoSTC(String numeroistanza) {

	IstanzeBusinessRules rules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
	boolean isInserimentoDaStc = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name());
	if (!isInserimentoDaStc) { // solamente se non è inserimento pratica da STC altrimenti non riesco a reimportare le pratiche
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equalsIgnoreCase("numeroistanza", numeroistanza));
	    fr.addFilterField(FilterUtils.isNull("istanzaId"));
	    ft.addRestriction(fr);
	    int domandePresenti = domandestcService.countRecord(ft);
	    if (domandePresenti > 0) {
		log.error("checkNumeroIstanzaPrenotatoSTC: Attenzione! L'istanza con numero " + numeroistanza +
			  " è già presente nel database. Rif.: Domande in errore provenienti da STC [" + ORMHelper.getIdcomune() + "," +
			  ORMHelper.getSoftware() + "]");
		throw new NumeroIstanzaUtilizzatoException("Attenzione! L'istanza con numero " + numeroistanza +
							   " è già presente nel database. Rif.: Domande in errore provenienti da STC [" +
							   ORMHelper.getIdcomune() + "," + ORMHelper.getSoftware() + "] ");
	    }
	}
    }

    // @Override
    // public List<Istanze> findIstanzeByMercatoMercatousoAnnoAnagrafe(Mercati mercati, MercatiUso uso, Anagrafe
    // occupante, MercatiD posteggio) {
    //
    // return istanzeDAO.findIstanzeByMercatoMercatousoAnnoAnagrafe(mercati, uso, occupante, posteggio);
    // }
    @Override
    public List<Istanze> findByNumeroistanzaOrRichiedente(String filterString) {

	return istanzeDAO.findByNumeroistanzaOrRichiedente(filterString);
    }

    @Override
    public List<Istanze> findByProtocolloNumeroRichiedenteAziendaOrPEC(String filterString, boolean protocolloInMovimenti) {

	/*
	 *  TODO filtrare per numero istanza, numero protocollo, domicilio elettronico dell'istanza, 
	 *  per descrizione, CF e pec del richiedente e per descrizione, CF, PI e PEC dell'azienda.
	 */
	boolean searchByProtocollo = protocolloInMovimenti;
	String protSearchIdentifier = "prot.";
	String checkString = filterString.toLowerCase().trim();
	if (checkString.toLowerCase().startsWith(protSearchIdentifier)) {
	    if (checkString.equalsIgnoreCase(protSearchIdentifier)) {
		return new ArrayList<Istanze>();
	    } else {
		filterString = filterString.toLowerCase().replace(protSearchIdentifier, "").trim();
		if (StringUtils.isNotBlank(filterString)) {
		    searchByProtocollo = true;
		} else {
		    return new ArrayList<Istanze>();
		}
	    }
	}
	FilterTable filter = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction restrict = new FilterRestriction();
	//numero protocollo iniziaper filtro
	restrict.addFilterField(FilterUtils.startsWith("numeroprotocollo", filterString));
	restrict.setAndOrRestriction(AndOrRestriction.OR);
	if (protocolloInMovimenti) {
	    //TODO cercare le istanze in cui filterString figura anche nel numero protocollo dei movimenti
	    FilterField<String> existsProtocolloMovimenti = new FilterField<String>("numeroprotocollo", "istanzemovimentis",
		    FieldOperationsEnum.STARTSWITH, new String[] { filterString }, Movimenti.class);
	    restrict.addFilterField(existsProtocolloMovimenti);
	}
	if (!searchByProtocollo) {
	    //numero istanza inizia per filtro
	    restrict.addFilterField(FilterUtils.startsWith("numeroistanza", filterString));
	    restrict.addFilterField(FilterUtils.like("domicilioElettronico", filterString));
	    restrict.addFilterField(FilterUtils.like("nominativo", filterString, "richiedente"));
	    restrict.addFilterField(FilterUtils.like("nome", filterString, "richiedente"));
	    restrict.addFilterField(FilterUtils.like("codicefiscale", filterString, "richiedente"));
	    restrict.addFilterField(FilterUtils.like("pec", filterString, "richiedente"));
	    restrict.addFilterField(FilterUtils.like("nominativo", filterString, "titolarelegale"));
	    restrict.addFilterField(FilterUtils.like("codicefiscale", filterString, "titolarelegale"));
	    restrict.addFilterField(FilterUtils.like("partitaiva", filterString, "titolarelegale"));
	    restrict.addFilterField(FilterUtils.like("pec", filterString, "titolarelegale"));
	}
	filter.addRestriction(restrict);
	// TODO NON POSSO METTERE L'ORDINAMENTO PROBABILE BUG HIBERNATE
	//	FilterOrder sort = FilterUtils.orderAsc("data", FunctionsEnum.LPAD_FUNCTION, new String[] { "60", "' '" });
	//	filter.addOrder(sort);
	// TODO NON POSSO METTERE L'ORDINAMENTO PROBABILE BUG HIBERNATE
	//filter.
	List<Istanze> results = findByFilterTable(filter, 0, 100);
	Collections.sort(results, new NumeroIstanzaComparator());
	return results;
    }

    public List<Istanze> findIstanzeDaGraduatoria(Integer codiceGraduatoria, String tipoMovimento, String soggettoMovimento) {

	List<Istanze> istanzeList = new ArrayList<Istanze>();
	PkId id = new PkId(codiceGraduatoria);
	Graduatoriet graduatoriet = graduatorietService.findById(id);
	Set<Tipibandooutput> tipibandooutputSet = graduatoriet.getTipigraduatoriet().getTipibandooutputs();
	Set<Graduatoried> graduatorieSet = graduatoriet.getGraduatorieds();
	Set<Movimenti> movimentiList;
	boolean isToAdd = false;
	// itero la graduatoria per recuperare tutte le istanza
	for (Graduatoried graduatoried : graduatorieSet) {
	    Istanze istanza = graduatoried.getIstanza();
	    istanza.setTransientPosGrad(graduatoried.getPosizione());
	    movimentiList = istanza.getIstanzemovimentis();
	    Set<Istanzedyn2dati> istanzedyn2datiSet = istanza.getIstanzedyn2datis();
	    isToAdd = true;
	    // se il movimento è stato eseguito allora non lo inserisco nella lista
	    for (Movimenti movimento : movimentiList) {
		if (movimento.getData() != null) {
		    if (movimento.getTipomovimento().getId().getTipomovimento().equals(tipoMovimento)) {
			isToAdd = false;
			break;
		    }
		}
	    }
	    if (isToAdd) {
		if (soggettoMovimento.equals(WebConstants.MERCATO_ASSEGNATARI)) {
		    // recupero il campo di output relativo al checkbox ASSEGNATARI e da questo recupero il valore del
		    // campo dyn2dati dell'istanza, poi
		    // verifico se il valore=1 cioè checkbox assegnatari checked
		    // FIXME se ci sono più campi di output non è corretto!!!
		    for (Tipibandooutput tipibandooutput : tipibandooutputSet) {
			Dyn2Campi dyn2CampiOut = tipibandooutput.getDyn2CampiOut();
			for (Istanzedyn2dati istanzedyn2dati : istanzedyn2datiSet) {
			    if (istanzedyn2dati.getDyn2Campi().getId().getCodice().equals(dyn2CampiOut.getId().getCodice())) {
				if (!istanzedyn2dati.getValore().equals("0")) {
				    istanza.setTransientCheckMov(true);
				    istanzeList.add(istanza);
				}
			    }
			}
		    }
		} else if (soggettoMovimento.equals(WebConstants.MERCATO_NONASSEGNATARI)) {
		    // recupero il campo di output relativo al checkbox ASSEGNATARI e da questo recupero il valore del
		    // campo dyn2dati dell'istanza, poi
		    // verifico se il valore=0 cioè checkbox assegnatari non checked
		    // FIXME se ci sono più campi di output non è corretto!!!
		    for (Tipibandooutput tipibandooutput : tipibandooutputSet) {
			Dyn2Campi dyn2CampiOut = tipibandooutput.getDyn2CampiOut();
			for (Istanzedyn2dati istanzedyn2dati : istanzedyn2datiSet) {
			    if (istanzedyn2dati.getDyn2Campi().getId().getCodice().equals(dyn2CampiOut.getId().getCodice())) {
				if (istanzedyn2dati.getValore().equals("0")) {
				    istanza.setTransientCheckMov(true);
				    istanzeList.add(istanza);
				}
			    }
			}
		    }
		} else {
		    // tutti, assegnatari e non assegnatari
		    istanza.setTransientCheckMov(true);
		    istanzeList.add(istanza);
		}
	    }
	}
	return istanzeList;
    }

    @Override
    public List<Istanze> findByFilterTable(FilterTable filterTable) {

	return istanzeDAO.findByFilterTable(filterTable);
    }

    @Override
    public int countByFilterTable(FilterTable filter) {

	return istanzeDAO.countRecord(filter);
    }

    @Override
    public List<Istanze> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	return istanzeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<Istanze> findByFilter(IstanzeFilter filter) {

	FilterTable filterTable = istanzeFilterToFilterTable(filter);
	List<Istanze> istanzeList = istanzeDAO.findByFilterTable(filterTable);
	return istanzeList;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private FilterTable istanzeFilterToFilterTable(IstanzeFilter filter) {

	// FILTRI
	// DATI ISTANZA
	FilterRestriction istanzeRestriction = new FilterRestriction();
	FilterTable ft = null;
	boolean almenoUno = false;
	if (filter.getModulo() != null && StringUtils.isNotBlank(filter.getModulo().getCodice())) {
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    istanzeRestriction.addFilterField(FilterUtils.equals("software.codice", filter.getModulo().getCodice(), String.class));
	    almenoUno = true;
	} else {
	    ft = new FilterTable(filter.getDefaultWhereCondition());
	}
	if (EntityUtils.getNestedProperty(filter, "comune.codicecomune") != null) {
	    if (StringUtils.isNotBlank(filter.getComune().getCodicecomune())) {
		if (filter.getComune().getCodicecomune().indexOf(",") > 0) {
		    String[] comuni = filter.getComune().getCodicecomune().split(",");
		    istanzeRestriction.addFilterField(FilterUtils.in("comune.codicecomune", comuni, String.class));
		} else {
		    istanzeRestriction.addFilterField(FilterUtils.equals("comune.codicecomune", filter.getComune().getCodicecomune(), String.class));
		}
		almenoUno = true;
	    }
	}
	if (StringUtils.isNotBlank(filter.getNumeroistanza())) {
	    istanzeRestriction.addFilterField(FilterUtils.startsWith("numeroistanza", filter.getNumeroistanza()));
	    almenoUno = true;
	}
	// Filtro per il campo id domanda mittente delle istanze pervenute da stc.
	if (StringUtils.isNotBlank(filter.getCodicedomandastc()) && !filter.isCercasolodomandestc()) {
	    istanzeRestriction.addFilterField(FilterUtils.like("idDomandamitt", filter.getCodicedomandastc(), "domandestcs"));
	    almenoUno = true;
	} else {
	    if (filter.isCercasolodomandestc()) {
		istanzeRestriction.addFilterField(FilterUtils.equals("creatoDaStc", true, Boolean.class));
		if (StringUtils.isNotBlank(filter.getIdNodoStc())) {
		    istanzeRestriction.addFilterField(FilterUtils.like("idNodo", filter.getIdNodoStc(), "domandestcs"));
		}
		// istanzeRestriction.addFilterField(FilterUtils.isNotEmpty("domandestcs"));
	    }
	}
	// Filtro per il codice pratica telematica
	if (StringUtils.isNotBlank(filter.getCodicepraticatel())) {
	    istanzeRestriction.addFilterField(FilterUtils.like("codicepraticatel", filter.getCodicepraticatel()));
	}
	if (EntityUtils.getNestedProperty(filter, "dallaData") != null) {
	    istanzeRestriction
		    .addFilterField(new FilterField<Date>("data", FieldOperationsEnum.GE, new Date[] { filter.getDallaData() }, Date.class));
	    almenoUno = true;
	}
	if (EntityUtils.getNestedProperty(filter, "allaData") != null) {
	    istanzeRestriction.addFilterField(new FilterField<Date>("data", FieldOperationsEnum.LE, new Date[] { filter.getAllaData() }, Date.class));
	    almenoUno = true;
	}
	if (StringUtils.isNotBlank(filter.getNumeroprotocollo())) {
	    // anche tra i movimenti
	    if (filter.isCercaprotocolloinmovimenti()) {
		FilterRestriction numeroProtocollo = new FilterRestriction();
		numeroProtocollo.setAndOrRestriction(AndOrRestriction.OR);
		String hierarchyMovimento = "istanzemovimentis";
		FilterField<String> existsProtocolloInMovimenti = new FilterField<String>("numeroprotocollo", hierarchyMovimento,
			FieldOperationsEnum.EXISTS, new String[] { filter.getNumeroprotocollo() }, Movimenti.class);
		existsProtocolloInMovimenti.setExistsChildEntityId("istanza.id");
		existsProtocolloInMovimenti.setExistsParentEntityId("id");
		numeroProtocollo.addFilterField(existsProtocolloInMovimenti);
		numeroProtocollo.addFilterField(FilterUtils.equals("numeroprotocollo", filter.getNumeroprotocollo(), String.class));
		ft.addRestriction(numeroProtocollo);
	    } else {
		istanzeRestriction.addFilterField(FilterUtils.startsWith("numeroprotocollo", filter.getNumeroprotocollo()));
		almenoUno = true;
	    }
	    // fine
	}
	// Soggetti dell'istanza (Ricerca per nominativo)
	if (StringUtils.isNotBlank((String) EntityUtils.getNestedProperty(filter, "soggettiistanza"))) {
	    FilterRestriction soggettiIstanza = new FilterRestriction();
	    soggettiIstanza.setAndOrRestriction(AndOrRestriction.OR);
	    soggettiIstanza.addFilterField(FilterUtils.like("nominativo", filter.getSoggettiistanza(), "richiedente.vwAnagrafeLocalizzazioni"));
	    soggettiIstanza.addFilterField(FilterUtils.like("nominativo", filter.getSoggettiistanza(), "titolarelegale.vwAnagrafeLocalizzazioni"));
	    soggettiIstanza.addFilterField(FilterUtils.like("nominativo", filter.getSoggettiistanza(), "professionista.vwAnagrafeLocalizzazioni"));
	    // Istanzerichiedenti richiedente AL MOMENTO NON E' STATO POSSIBILE RICERCARE ANCHE PER ISTANZERICHIEDENTI
	    //	TODO DA COMPLETARE INCLUDENDO NELLA RICERCA ISTANZERICHIEDENTI
	    //	    String hierarchyIstRichiedenti = "istanzerichiedentis.richiedente.vwAnagrafeLocalizzazioni";
	    //	    FilterField<String> existsRichiedenteNominativoInIstRic = new FilterField<String>("nominativo", hierarchyIstRichiedenti,
	    //		    FieldOperationsEnum.EXISTS_LIKE, new String[] { filter.getSoggettiistanza() }, VwAnagrafeLocalizzazioni.class);
	    //	    existsRichiedenteNominativoInIstRic.setExistsChildEntityId("istanza.id");
	    //	    existsRichiedenteNominativoInIstRic.setExistsParentEntityId("id");
	    //	    existsRichiedenteNominativoInIstRic.setInverseJoinChain(new String[] { "anagrafes.istanzerichiedentisForRichiedente" });
	    //	    soggettiIstanza.addFilterField(existsRichiedenteNominativoInIstRic);
	    //	    // Istanzerichiedenti anagrafeCollegata
	    //	    String hierarchyIstAnagrafeColl = "istanzerichiedentis.anagrafeCollegata.vwAnagrafeLocalizzazioni";
	    //	    FilterField<String> existsAnagrafeNominativoCollInIstRic = new FilterField<String>("nominativo", hierarchyIstAnagrafeColl,
	    //		    FieldOperationsEnum.EXISTS_LIKE, new String[] { filter.getSoggettiistanza() }, VwAnagrafeLocalizzazioni.class);
	    //	    existsAnagrafeNominativoCollInIstRic.setExistsChildEntityId("istanza.id");
	    //	    existsAnagrafeNominativoCollInIstRic.setExistsParentEntityId("id");
	    //	    existsAnagrafeNominativoCollInIstRic.setInverseJoinChain(new String[] { "anagrafes.istanzerichiedentisForAnagrafeCollegata" });
	    //	    soggettiIstanza.addFilterField(existsAnagrafeNominativoCollInIstRic);
	    //	    // Istanzerichiedenti procuratore
	    //	    String hierarchyIstProcuratore = "istanzerichiedentis.procuratore.vwAnagrafeLocalizzazioni";
	    //	    FilterField<String> existsProcuratoreNominativoInIstRic = new FilterField<String>("nominativo", hierarchyIstProcuratore,
	    //		    FieldOperationsEnum.EXISTS_LIKE, new String[] { filter.getSoggettiistanza() }, VwAnagrafeLocalizzazioni.class);
	    //	    existsProcuratoreNominativoInIstRic.setExistsChildEntityId("istanza.id");
	    //	    existsProcuratoreNominativoInIstRic.setExistsParentEntityId("id");
	    //	    existsProcuratoreNominativoInIstRic.setInverseJoinChain(new String[] { "anagrafes.istanzerichiedentisForProcuratore" });
	    //	    soggettiIstanza.addFilterField(existsProcuratoreNominativoInIstRic);
	    ft.addRestriction(soggettiIstanza);
	}
	if (StringUtils.isNotBlank((String) EntityUtils.getNestedProperty(filter, "soggettiistanzaPivaCF"))) {
	    FilterRestriction soggettiIstanza = new FilterRestriction();
	    soggettiIstanza.setAndOrRestriction(AndOrRestriction.OR);
	    soggettiIstanza.addFilterField(FilterUtils.like("codicefiscale", filter.getSoggettiistanzaPivaCF(), "richiedente"));
	    soggettiIstanza.addFilterField(FilterUtils.like("partitaiva", filter.getSoggettiistanzaPivaCF(), "richiedente"));
	    soggettiIstanza.addFilterField(FilterUtils.like("codicefiscale", filter.getSoggettiistanzaPivaCF(), "titolarelegale"));
	    soggettiIstanza.addFilterField(FilterUtils.like("partitaiva", filter.getSoggettiistanzaPivaCF(), "titolarelegale"));
	    soggettiIstanza.addFilterField(FilterUtils.like("codicefiscale", filter.getSoggettiistanzaPivaCF(), "professionista"));
	    soggettiIstanza.addFilterField(FilterUtils.like("partitaiva", filter.getSoggettiistanzaPivaCF(), "professionista"));
	    // Istanzerichiedenti richiedente AL MOMENTO NON E' STATO POSSIBILE RICERCARE ANCHE PER ISTANZERICHIEDENTI
	    //	TODO DA COMPLETARE INCLUDENDO NELLA RICERCA ISTANZERICHIEDENTI
	    //	    String hierarchyIstRichiedenti = "istanzerichiedentis.richiedente.vwAnagrafeLocalizzazioni";
	    //	    FilterField<String> existsRichiedenteNominativoInIstRic = new FilterField<String>("nominativo", hierarchyIstRichiedenti,
	    //		    FieldOperationsEnum.EXISTS_LIKE, new String[] { filter.getSoggettiistanza() }, VwAnagrafeLocalizzazioni.class);
	    //	    existsRichiedenteNominativoInIstRic.setExistsChildEntityId("istanza.id");
	    //	    existsRichiedenteNominativoInIstRic.setExistsParentEntityId("id");
	    //	    existsRichiedenteNominativoInIstRic.setInverseJoinChain(new String[] { "anagrafes.istanzerichiedentisForRichiedente" });
	    //	    soggettiIstanza.addFilterField(existsRichiedenteNominativoInIstRic);
	    //	    // Istanzerichiedenti anagrafeCollegata
	    //	    String hierarchyIstAnagrafeColl = "istanzerichiedentis.anagrafeCollegata.vwAnagrafeLocalizzazioni";
	    //	    FilterField<String> existsAnagrafeNominativoCollInIstRic = new FilterField<String>("nominativo", hierarchyIstAnagrafeColl,
	    //		    FieldOperationsEnum.EXISTS_LIKE, new String[] { filter.getSoggettiistanza() }, VwAnagrafeLocalizzazioni.class);
	    //	    existsAnagrafeNominativoCollInIstRic.setExistsChildEntityId("istanza.id");
	    //	    existsAnagrafeNominativoCollInIstRic.setExistsParentEntityId("id");
	    //	    existsAnagrafeNominativoCollInIstRic.setInverseJoinChain(new String[] { "anagrafes.istanzerichiedentisForAnagrafeCollegata" });
	    //	    soggettiIstanza.addFilterField(existsAnagrafeNominativoCollInIstRic);
	    //	    // Istanzerichiedenti procuratore
	    //	    String hierarchyIstProcuratore = "istanzerichiedentis.procuratore.vwAnagrafeLocalizzazioni";
	    //	    FilterField<String> existsProcuratoreNominativoInIstRic = new FilterField<String>("nominativo", hierarchyIstProcuratore,
	    //		    FieldOperationsEnum.EXISTS_LIKE, new String[] { filter.getSoggettiistanza() }, VwAnagrafeLocalizzazioni.class);
	    //	    existsProcuratoreNominativoInIstRic.setExistsChildEntityId("istanza.id");
	    //	    existsProcuratoreNominativoInIstRic.setExistsParentEntityId("id");
	    //	    existsProcuratoreNominativoInIstRic.setInverseJoinChain(new String[] { "anagrafes.istanzerichiedentisForProcuratore" });
	    //	    soggettiIstanza.addFilterField(existsProcuratoreNominativoInIstRic);
	    ft.addRestriction(soggettiIstanza);
	}
	if (EntityUtils.getNestedProperty(filter, "richiedente.id.codice") != null) {
	    FilterRestriction richiedentiSoggColl = new FilterRestriction();
	    richiedentiSoggColl.setAndOrRestriction(AndOrRestriction.OR);
	    richiedentiSoggColl
		    .addFilterField(FilterUtils.equals("id.codice", filter.getRichiedente().getId().getCodice(), "richiedente", Integer.class));
	    richiedentiSoggColl
		    .addFilterField(FilterUtils.equals("id.codice", filter.getRichiedente().getId().getCodice(), "titolarelegale", Integer.class));
	    richiedentiSoggColl
		    .addFilterField(FilterUtils.equals("id.codice", filter.getRichiedente().getId().getCodice(), "professionista", Integer.class));
	    // Istanzerichiedenti
	    String hierarchyIstRichiedenti = "istanzerichiedentis";
	    FilterField<Integer> existsRichiedenteInIstRic = new FilterField<Integer>("richiedenteId", hierarchyIstRichiedenti,
		    FieldOperationsEnum.EXISTS, new Integer[] { filter.getRichiedente().getId().getCodice() }, Istanzerichiedenti.class);
	    existsRichiedenteInIstRic.setExistsChildEntityId("istanza.id");
	    existsRichiedenteInIstRic.setExistsParentEntityId("id");
	    richiedentiSoggColl.addFilterField(existsRichiedenteInIstRic);
	    // String hierarchyIstAnagrafeColl = "istanzerichiedentis.anagrafeCollegata";
	    FilterField<Integer> existsAnagrafeCollInIstRic = new FilterField<Integer>("anagrafeCollegataId", hierarchyIstRichiedenti,
		    FieldOperationsEnum.EXISTS, new Integer[] { filter.getRichiedente().getId().getCodice() }, Istanzerichiedenti.class);
	    existsAnagrafeCollInIstRic.setExistsChildEntityId("istanza.id");
	    existsAnagrafeCollInIstRic.setExistsParentEntityId("id");
	    richiedentiSoggColl.addFilterField(existsAnagrafeCollInIstRic);
	    // String hierarchyIstProcuratore = "istanzerichiedentis.procuratore";
	    FilterField<Integer> existsProcuratoreInIstRic = new FilterField<Integer>("procuratoreId", hierarchyIstRichiedenti,
		    FieldOperationsEnum.EXISTS, new Integer[] { filter.getRichiedente().getId().getCodice() }, Istanzerichiedenti.class);
	    existsProcuratoreInIstRic.setExistsChildEntityId("istanza.id");
	    existsProcuratoreInIstRic.setExistsParentEntityId("id");
	    richiedentiSoggColl.addFilterField(existsProcuratoreInIstRic);
	    //	    private Anagrafe procuratore
	    //	    richiedentiSoggColl.addFilterField(FilterUtils.equals("id.codice", filter.getRichiedente().getId().getCodice(),
	    //		    "istanzerichiedentis.richiedente", Integer.class));
	    //	    richiedentiSoggColl.addFilterField(FilterUtils.equals("id.codice", filter.getRichiedente().getId().getCodice(),
	    //		    "istanzerichiedentis.anagrafeCollegata", Integer.class));
	    //	    richiedentiSoggColl.addFilterField(FilterUtils.equals("id.codice", filter.getRichiedente().getId().getCodice(),
	    //		    "istanzerichiedentis.procuratore", Integer.class));
	    ft.addRestriction(richiedentiSoggColl);
	}
	if (EntityUtils.getNestedProperty(filter, "tipiarchivioistanza.id.codice") != null) {
	    istanzeRestriction.addFilterField(
		    FilterUtils.equals("id.codice", filter.getTipiarchivioistanza().getId().getCodice(), "tipiarchivioistanza", Integer.class));
	    almenoUno = true;
	}
	if (StringUtils.isNotBlank(filter.getPosizionearchivio())) {
	    istanzeRestriction.addFilterField(FilterUtils.equalsIgnoreCase("posizionearchivio", filter.getPosizionearchivio()));
	    almenoUno = true;
	}
	if (EntityUtils.getNestedProperty(filter, "tipologiaistanza.id.codice") != null) {
	    istanzeRestriction.addFilterField(
		    FilterUtils.equals("id.codice", filter.getTipologiaistanza().getId().getCodice(), "tipologiaistanza", Integer.class));
	    almenoUno = true;
	}
	if (EntityUtils.getNestedProperty(filter, "responsabile.id.codice") != null) {
	    FilterRestriction opeRespIstru = new FilterRestriction();
	    opeRespIstru.setAndOrRestriction(AndOrRestriction.OR);
	    opeRespIstru.addFilterField(FilterUtils.equals("id.codice", filter.getResponsabile().getId().getCodice(), "responsabile", Integer.class));
	    opeRespIstru.addFilterField(
		    FilterUtils.equals("id.codice", filter.getResponsabile().getId().getCodice(), "responsabileProcedimento", Integer.class));
	    opeRespIstru.addFilterField(FilterUtils.equals("id.codice", filter.getResponsabile().getId().getCodice(), "istruttore", Integer.class));
	    ft.addRestriction(opeRespIstru);
	}
	if (EntityUtils.getNestedProperty(filter, "professionista.id.codice") != null) {
	    istanzeRestriction
		    .addFilterField(FilterUtils.equals("id.codice", filter.getProfessionista().getId().getCodice(), "professionista", Integer.class));
	}
	// DATI DELLA CONCESSIONE
	if (filter.getDatiAutorizzazione() != null && (StringUtils.isNotBlank(filter.getDatiAutorizzazione().getAutoriznumero())
		|| filter.getDatiAutorizzazione().getAutorizdata() != null
		|| EntityUtils.getNestedProperty(filter, "datiAutorizzazione.tipologiaregistro.id.codice") != null
		|| StringUtils.isNotBlank(filter.getDatiAutorizzazione().getAutorizcomune().getCodicecomune()))) {
	    if (StringUtils.isNotBlank(filter.getDatiAutorizzazione().getAutoriznumero())) {
		FilterField<?> autoriznumero = FilterUtils.like("autoriznumero", filter.getDatiAutorizzazione().getAutoriznumero(),
			"autorizzazionis");
		istanzeRestriction.addFilterField(autoriznumero);
	    }
	    if (filter.getDatiAutorizzazione().getAutorizdata() != null) {
		FilterField<?> autorizdata = FilterUtils.equals("autorizdata", filter.getDatiAutorizzazione().getAutorizdata(), "autorizzazionis",
			Date.class);
		istanzeRestriction.addFilterField(autorizdata);
	    }
	    if (EntityUtils.getNestedProperty(filter, "datiAutorizzazione.tipologiaregistro.id.codice") != null) {
		FilterField<?> registroAut = FilterUtils.equals("id.codice",
			filter.getDatiAutorizzazione().getTipologiaregistro().getId().getCodice(), "autorizzazionis.tipologiaregistro",
			Integer.class);
		istanzeRestriction.addFilterField(registroAut);
	    }
	    if (StringUtils.isNotBlank(filter.getDatiAutorizzazione().getAutorizcomune().getCodicecomune())) {
		FilterField<?> comune = FilterUtils.equals("codicecomune", filter.getDatiAutorizzazione().getAutorizcomune().getCodicecomune(),
			"autorizzazionis.autorizcomune", String.class);
		istanzeRestriction.addFilterField(comune);
	    }
	    almenoUno = true;
	}
	// DATI DELLA LOCALIZZAZIONE
	if (filter.getIstanzearee() != null && (EntityUtils.getNestedProperty(filter, "istanzearee.id.codicearea") != null)) {
	    istanzeRestriction.addFilterField(
		    FilterUtils.equals("id.codicearea", filter.getIstanzearee().getId().getCodicearea(), "istanzearees", Integer.class));
	    almenoUno = true;
	}
	boolean cercaLocalizzazione = false;
	if (EntityUtils.getNestedProperty(filter, "istanzestradario.stradario.id.codice") != null) {
	    istanzeRestriction.addFilterField(FilterUtils.equals("id.codice", filter.getIstanzestradario().getStradario().getId().getCodice(),
		    "istanzestradarios.stradario", Integer.class));
	    almenoUno = true;
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getCivico())) {
		istanzeRestriction
			.addFilterField(FilterUtils.equalsIgnoreCase("civico", filter.getIstanzestradario().getCivico(), "istanzestradarios"));
	    }
	    cercaLocalizzazione = true;
	}
	///////////////////////////////////////////////
	if (StringUtils.isNotBlank(filter.getIstanzestradario().getEsponente())) {
	    istanzeRestriction
		    .addFilterField(FilterUtils.equalsIgnoreCase("esponente", filter.getIstanzestradario().getEsponente(), "istanzestradarios"));
	}
	if (StringUtils.isNotBlank(filter.getIstanzestradario().getScala())) {
	    istanzeRestriction.addFilterField(FilterUtils.equalsIgnoreCase("scala", filter.getIstanzestradario().getScala(), "istanzestradarios"));
	}
	if (StringUtils.isNotBlank(filter.getIstanzestradario().getPiano())) {
	    istanzeRestriction.addFilterField(FilterUtils.equalsIgnoreCase("piano", filter.getIstanzestradario().getPiano(), "istanzestradarios"));
	}
	if (StringUtils.isNotBlank(filter.getIstanzestradario().getInterno())) {
	    istanzeRestriction
		    .addFilterField(FilterUtils.equalsIgnoreCase("interno", filter.getIstanzestradario().getInterno(), "istanzestradarios"));
	}
	if (StringUtils.isNotBlank(filter.getIstanzestradario().getEsponenteinterno())) {
	    istanzeRestriction.addFilterField(
		    FilterUtils.equalsIgnoreCase("esponenteinterno", filter.getIstanzestradario().getEsponenteinterno(), "istanzestradarios"));
	}
	if (StringUtils.isNotBlank(filter.getIstanzestradario().getFabbricato())) {
	    istanzeRestriction
		    .addFilterField(FilterUtils.equalsIgnoreCase("fabbricato", filter.getIstanzestradario().getFabbricato(), "istanzestradarios"));
	}
	if (StringUtils.isNotBlank(filter.getIstanzestradario().getFrazione())) {
	    istanzeRestriction
		    .addFilterField(FilterUtils.equalsIgnoreCase("frazione", filter.getIstanzestradario().getFrazione(), "istanzestradarios"));
	}
	if (StringUtils.isNotBlank(filter.getIstanzestradario().getCap())) {
	    istanzeRestriction.addFilterField(FilterUtils.equalsIgnoreCase("cap", filter.getIstanzestradario().getCap(), "istanzestradarios"));
	}
	if (StringUtils.isNotBlank(filter.getIstanzestradario().getQuartiere())) {
	    istanzeRestriction
		    .addFilterField(FilterUtils.equalsIgnoreCase("quartiere", filter.getIstanzestradario().getQuartiere(), "istanzestradarios"));
	}
	////////////////////////////////////////////////
	if (StringUtils.isNotBlank(filter.getIstanzestradario().getCircoscrizione())) {
	    istanzeRestriction
		    .addFilterField(FilterUtils.like("circoscrizione", filter.getIstanzestradario().getCircoscrizione(), "istanzestradarios"));
	    almenoUno = true;
	    cercaLocalizzazione = true;
	}
	if (StringUtils.isNotBlank(filter.getIstanzestradario().getNote())) {
	    istanzeRestriction.addFilterField(FilterUtils.like("note", filter.getIstanzestradario().getNote(), "istanzestradarios"));
	    almenoUno = true;
	    cercaLocalizzazione = true;
	}
	if (EntityUtils.getNestedProperty(filter, "istanzestradario.stradariocolore.id.codicecolore") != null) {
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getStradariocolore().getId().getCodicecolore())) {
		istanzeRestriction.addFilterField(
			FilterUtils.equals("id.codicecolore", filter.getIstanzestradario().getStradariocolore().getId().getCodicecolore(),
				"istanzestradarios.stradariocolore", Integer.class));
		almenoUno = true;
		cercaLocalizzazione = true;
	    }
	}
	if (cercaLocalizzazione) {
	    if (!filter.isCercalocalizzazioneinaltri()) {
		istanzeRestriction.addFilterField(FilterUtils.equals("primario", Boolean.TRUE, "istanzestradarios", Boolean.class));
		almenoUno = true;
	    }
	}
	if (StringUtils.isNotBlank(filter.getIstanzemappali().getCatasto().getCodice())) {
	    istanzeRestriction.addFilterField(
		    FilterUtils.equals("codice", filter.getIstanzemappali().getCatasto().getCodice(), "istanzemappalis.catasto", String.class));
	    almenoUno = true;
	}
	if (StringUtils.isNotBlank(filter.getIstanzemappali().getFoglio())) {
	    istanzeRestriction.addFilterField(FilterUtils.equals("foglio", filter.getIstanzemappali().getFoglio(), "istanzemappalis", String.class));
	    almenoUno = true;
	}
	if (StringUtils.isNotBlank(filter.getIstanzemappali().getParticella())) {
	    istanzeRestriction
		    .addFilterField(FilterUtils.equals("particella", filter.getIstanzemappali().getParticella(), "istanzemappalis", String.class));
	    almenoUno = true;
	}
	if (StringUtils.isNotBlank(filter.getIstanzemappali().getSub())) {
	    istanzeRestriction.addFilterField(FilterUtils.equals("sub", filter.getIstanzemappali().getSub(), "istanzemappalis", String.class));
	    almenoUno = true;
	}
	// DATI PROGETTO
	if (EntityUtils.getNestedProperty(filter, "alberoproc.id.codice") != null) {
	    Alberoproc alberoproc = alberoprocService.bindDomainObject(filter.getAlberoproc(), PkId.class, "id.codice");
	    String hierarchyalberoproc = "alberoproc";
	    istanzeRestriction.addFilterField(FilterUtils.startsWith("scCodice", alberoproc.getScCodice(), hierarchyalberoproc));
	    almenoUno = true;
	}
	if (EntityUtils.getNestedProperty(filter, "procedura.id.codice") != null) {
	    istanzeRestriction.addFilterField(FilterUtils.equals("id.codice", filter.getProcedura().getId().getCodice(), "procedura", Integer.class));
	    almenoUno = true;
	}
	if (StringUtils.isNotBlank((String) EntityUtils.getNestedProperty(filter, "tipoMovimento.id.tipomovimento"))) {
	    FilterRestriction tipoMovimento = new FilterRestriction();
	    String hierarchyMovimento = "istanzemovimentis";
	    FilterField<?>[] movimentiNonEseguiti = new FilterField<?>[1];
	    movimentiNonEseguiti[0] = FilterUtils.isNotNull("data");
	    FilterField<?> existsTipoMovimento = new FilterField("tipomovimento.id.tipomovimento", hierarchyMovimento, FieldOperationsEnum.EXISTS,
		    new String[] { filter.getTipoMovimento().getId().getTipomovimento() }, Movimenti.class, movimentiNonEseguiti);
	    existsTipoMovimento.setExistsChildEntityId("istanza.id");
	    existsTipoMovimento.setExistsParentEntityId("id");
	    tipoMovimento.addFilterField(existsTipoMovimento);
	    ft.addRestriction(tipoMovimento);
	}
	if (EntityUtils.getNestedProperty(filter, "inventarioprocedimenti.tipoendo.tipifamiglieendo.id.codice") != null) {
	    if (EntityUtils.getNestedProperty(filter, "inventarioprocedimenti.tipoendo.id.codice") == null) {
		if (EntityUtils.getNestedProperty(filter, "inventarioprocedimenti.id.codice") == null) {
		    FilterRestriction famigliaEndo = new FilterRestriction();
		    String hierarchy = "istanzeprocedimentis.inventarioprocedimenti.tipoendo.tipifamiglieendo";
		    FilterField<Integer> existsFamigliaEndo = new FilterField<Integer>("id.codice", hierarchy, FieldOperationsEnum.EXISTS,
			    new Integer[] { filter.getInventarioprocedimenti().getTipoendo().getTipifamiglieendo().getId().getCodice() },
			    Tipifamiglieendo.class);
		    existsFamigliaEndo.setExistsChildEntityId("istanza.id");
		    existsFamigliaEndo.setExistsParentEntityId("id");
		    existsFamigliaEndo.setInverseJoinChain(new String[] { "tipiendos", "inventarioprocedimentis", "istanzeprocedimentis" });
		    famigliaEndo.addFilterField(existsFamigliaEndo);
		    ft.addRestriction(famigliaEndo);
		}
	    }
	}
	if (EntityUtils.getNestedProperty(filter, "inventarioprocedimenti.tipoendo.id.codice") != null) {
	    if (EntityUtils.getNestedProperty(filter, "inventarioprocedimenti.id.codice") == null) {
		FilterRestriction tipoEndo = new FilterRestriction();
		String hierarchy = "istanzeprocedimentis.inventarioprocedimenti.tipoendo";
		FilterField<Integer> existsFamigliaEndo = new FilterField<Integer>("id.codice", hierarchy, FieldOperationsEnum.EXISTS,
			new Integer[] { filter.getInventarioprocedimenti().getTipoendo().getId().getCodice() }, Tipiendo.class);
		existsFamigliaEndo.setExistsChildEntityId("istanza.id");
		existsFamigliaEndo.setExistsParentEntityId("id");
		existsFamigliaEndo.setInverseJoinChain(new String[] { "inventarioprocedimentis", "istanzeprocedimentis" });
		tipoEndo.addFilterField(existsFamigliaEndo);
		ft.addRestriction(tipoEndo);
	    }
	}
	if (EntityUtils.getNestedProperty(filter, "inventarioprocedimenti.id.codice") != null) {
	    FilterRestriction endoprocedimento = new FilterRestriction();
	    String hierarchy = "istanzeprocedimentis.inventarioprocedimenti";
	    FilterField<Integer> existsFamigliaEndo = new FilterField<Integer>("id.codice", hierarchy, FieldOperationsEnum.EXISTS,
		    new Integer[] { filter.getInventarioprocedimenti().getId().getCodice() }, Inventarioprocedimenti.class);
	    existsFamigliaEndo.setExistsChildEntityId("istanza.id");
	    existsFamigliaEndo.setExistsParentEntityId("id");
	    existsFamigliaEndo.setInverseJoinChain(new String[] { "istanzeprocedimentis" });
	    endoprocedimento.addFilterField(existsFamigliaEndo);
	    ft.addRestriction(endoprocedimento);
	}
	if (StringUtils.isNotBlank(filter.getIstanzeattivita().getAttivita().getSettori().getId().getCodicesettore())) {
	    if (StringUtils.isBlank(filter.getIstanzeattivita().getAttivita().getId().getCodiceistat())) {
		istanzeRestriction.addFilterField(
			FilterUtils.equals("id.codicesettore", filter.getIstanzeattivita().getAttivita().getSettori().getId().getCodicesettore(),
				"istanzeattivitas.attivita.settori", String.class));
	    }
	}
	if (StringUtils.isNotBlank(filter.getIstanzeattivita().getAttivita().getId().getCodiceistat())) {
	    istanzeRestriction.addFilterField(FilterUtils.equals("id.codiceistat", filter.getIstanzeattivita().getAttivita().getId().getCodiceistat(),
		    "istanzeattivitas.attivita", String.class));
	}
	if (StringUtils.isNotBlank(filter.getLavori())) {
	    istanzeRestriction.addFilterField(FilterUtils.like("lavori", filter.getLavori()));
	    almenoUno = true;
	}
	if (StringUtils.isNotBlank(filter.getNomeattivita())) {
	    istanzeRestriction.addFilterField(FilterUtils.like("nomeattivita", filter.getNomeattivita()));
	    almenoUno = true;
	}
	if (StringUtils.isNotBlank(filter.getLavoriestesa())) {
	    istanzeRestriction.addFilterField(FilterUtils.like("lavoriestesa", filter.getLavoriestesa()));
	    almenoUno = true;
	}
	if (filter.isCercasolodomandeareariservata()) {
	    FilterField<String> existsDomandaAreaRiservata = new FilterField<String>("codice", "istanzefrontoffices.software",
		    FieldOperationsEnum.EXISTS, new String[] { ORMHelper.getSoftware() }, Software.class);
	    existsDomandaAreaRiservata.setExistsChildEntityId("istanze.id");
	    existsDomandaAreaRiservata.setExistsParentEntityId("id");
	    existsDomandaAreaRiservata.setInverseJoinChain(new String[] { "istanzefrontoffices" });
	    istanzeRestriction.addFilterField(existsDomandaAreaRiservata);
	    almenoUno = true;
	}
	if (filter.getDaMq() != null) {
	    istanzeRestriction.addFilterField(
		    new FilterField<BigDecimal>("metriquadrati", FieldOperationsEnum.GE, new BigDecimal[] { filter.getDaMq() }, BigDecimal.class));
	    almenoUno = true;
	}
	if (filter.getaMq() != null) {
	    istanzeRestriction.addFilterField(
		    new FilterField<BigDecimal>("metriquadrati", FieldOperationsEnum.LE, new BigDecimal[] { filter.getDaMq() }, BigDecimal.class));
	    almenoUno = true;
	}
	if (EntityUtils.getNestedProperty(filter, "chiusura.id.codicestato") != null) {
	    String stato = filter.getChiusura().getId().getCodicestato();
	    if (!stato.equals("stato_tutte")) { // non esegue filtri su stato
		if (stato.equalsIgnoreCase("stato_chiuse") || stato.equalsIgnoreCase("stato_aperte")) {
		    List<Statiistanza> statistStatiistanzas = null;
		    if (stato.equalsIgnoreCase("stato_chiuse")) {
			statistStatiistanzas = statiistanzaService.findByStatocomportamentoChiuse();
		    } else {
			statistStatiistanzas = statiistanzaService.findByStatocomportamentoAperte();
		    }
		    if (statistStatiistanzas.size() > 0) {
			String[] stati = new String[statistStatiistanzas.size()];
			int i = 0;
			for (Statiistanza statiistanza : statistStatiistanzas) {
			    stati[i] = statiistanza.getId().getCodicestato();
			    i++;
			}
			istanzeRestriction.addFilterField(FilterUtils.in("id.codicestato", stati, "chiusura", String.class));
			almenoUno = true;
		    }
		} else {
		    istanzeRestriction.addFilterField(FilterUtils.equals("id.codicestato", stato, "chiusura", String.class));
		    almenoUno = true;
		}
	    }
	}
	if (almenoUno) {
	    ft.addRestriction(istanzeRestriction);
	}
	// ///ORDINAMENTI
	String[] padNumeroistanza = new String[] { "20", "' '" };
	if (StringUtils.isNotBlank(filter.getOrderBy())) {
	    if (filter.getOrderBy().equalsIgnoreCase("data")) {
		ft.addOrder(FilterUtils.order("data", filter.getOrderAscDesc()));
		// aggiungo anche l'ordine per numeroistanza
		// ft.addOrder(FilterUtils.order("numeroistanza", filter.getOrderAscDesc()));
		switch (filter.getOrderAscDesc()) {
		case ASC:
		    ft.addOrder(FilterUtils.orderAsc("numeroistanza", FunctionsEnum.LPAD_FUNCTION, padNumeroistanza));
		    break;
		case DESC:
		    ft.addOrder(FilterUtils.orderDesc("numeroistanza", FunctionsEnum.LPAD_FUNCTION, padNumeroistanza));
		    break;
		default:
		    ft.addOrder(FilterUtils.order("numeroistanza", filter.getOrderAscDesc()));
		    break;
		}
	    } else if (filter.getOrderBy().equalsIgnoreCase("richiedente.nominativo")) {
		ft.addOrder(FilterUtils.order("nominativo", "richiedente", filter.getOrderAscDesc()));
		ft.addOrder(FilterUtils.order("nome", "richiedente", filter.getOrderAscDesc()));
		ft.addOrder(FilterUtils.order("numeroistanza", filter.getOrderAscDesc()));
	    } else if (filter.getOrderBy().equalsIgnoreCase("numeroistanza")) {
		switch (filter.getOrderAscDesc()) {
		case ASC:
		    ft.addOrder(FilterUtils.orderAsc("numeroistanza", FunctionsEnum.LPAD_FUNCTION, padNumeroistanza));
		    break;
		case DESC:
		    ft.addOrder(FilterUtils.orderDesc("numeroistanza", FunctionsEnum.LPAD_FUNCTION, padNumeroistanza));
		    break;
		default:
		    ft.addOrder(FilterUtils.order("numeroistanza", filter.getOrderAscDesc()));
		    break;
		}
		// ft.addOrder(FilterUtils.order("numeroistanza", filter.getOrderAscDesc()));
	    } else if (filter.getOrderBy().equalsIgnoreCase("istanzestradarios.stradario.descrizione")) {
		ft.addOrder(FilterUtils.order("descrizione", "istanzestradarios.stradario", filter.getOrderAscDesc()));
	    } else if (filter.getOrderBy().equalsIgnoreCase("dataprotocollo")) {
		ft.addOrder(FilterUtils.order("dataprotocollo", filter.getOrderAscDesc()));
	    } else if (filter.getOrderBy().equalsIgnoreCase("posizionearchivio")) {
		ft.addOrder(FilterUtils.order("posizionearchivio", filter.getOrderAscDesc()));
	    } else if (filter.getOrderBy().equalsIgnoreCase("responsabile.responsabile")) {
		ft.addOrder(FilterUtils.order("responsabile", "responsabile", filter.getOrderAscDesc()));
	    } else if (filter.getOrderBy().equalsIgnoreCase("alberoproc.tipoProcedura.procedura")) {
		ft.addOrder(FilterUtils.order("procedura", "alberoproc.tipoProcedura", filter.getOrderAscDesc()));
	    } else if (filter.getOrderBy().equalsIgnoreCase("chiusura.stato")) {
		ft.addOrder(FilterUtils.order("stato", "chiusura", filter.getOrderAscDesc()));
	    } else if (filter.getOrderBy().equalsIgnoreCase("alberoproc.vwAlberoproc.scDescrizione")) {
		ft.addOrder(FilterUtils.order("scDescrizione", "alberoproc.vwAlberoproc", filter.getOrderAscDesc()));
	    }
	}
	return ft;
    }

    @Override
    public TipoAccessoEnum checkAccessoIstanza(Istanze istanza, Responsabili responsabile) {

	String amministratore = StringUtils.defaultIfEmpty(responsabile.getAmministratore(), "0");
	String amministratoreSoftware = StringUtils.defaultIfEmpty(responsabile.getAmministratoresoftware(), "0");
	if (BooleanUtils.isTrue(responsabile.getReadonly())) {
	    boolean esistePermesso = permistanzeService.checkByIstanzaAndResponsabile(istanza, responsabile);
	    if (esistePermesso) {
		return TipoAccessoEnum.SOLA_LETTURA;
	    }
	    if (amministratore.equals("1")) {
		return TipoAccessoEnum.SOLA_LETTURA;
	    }
	    if (amministratoreSoftware.equals("1")) {
		String softwareIstanza = (String) EntityUtils.getNestedProperty(istanza, "software.codice");
		if (StringUtils.isNotBlank(softwareIstanza)) {
		    boolean isAbilitato = softwareService.isSoftwareAbilitato(responsabile, softwareIstanza);
		    if (isAbilitato) {
			return TipoAccessoEnum.SOLA_LETTURA;
		    }
		}
	    }
	    // BOCCI 2012-03-14 Se operatore è readonly può accedere alle istanze solamente se ha i ruoli sull'istanza presa in considerazione
	    // vedi bugzilla 479 'operatore readonly deve poter accedere solamente alle istanze su cui ha i ruoli o permessi'
	    TipoAccessoEnum tipoaccesso = istanzeruoliService.checkByIstanzaAndResponsabile(istanza, responsabile);
	    switch (tipoaccesso) {
	    case CONSENTITO:
	    case SOLA_LETTURA_MOVIMENTI_AMM_INTERNA:
	    case SOLA_LETTURA_TUTTI_MOVIMENTI:
	    case SOLA_LETTURA:
		return TipoAccessoEnum.SOLA_LETTURA;
	    case NON_CONSENTITO:
		return TipoAccessoEnum.NON_CONSENTITO;
	    }
	    return TipoAccessoEnum.SOLA_LETTURA;
	}
	if (amministratore.equals("1")) {
	    return TipoAccessoEnum.CONSENTITO;
	}
	if (amministratoreSoftware.equals("1")) {
	    String softwareIstanza = (String) EntityUtils.getNestedProperty(istanza, "software.codice");
	    if (StringUtils.isNotBlank(softwareIstanza)) {
		boolean isAbilitato = softwareService.isSoftwareAbilitato(responsabile, softwareIstanza);
		if (isAbilitato) {
		    return TipoAccessoEnum.CONSENTITO;
		}
	    }
	}
	// verifico se su permistanze
	boolean esistePermesso = permistanzeService.checkByIstanzaAndResponsabile(istanza, responsabile);
	if (esistePermesso) {
	    return TipoAccessoEnum.CONSENTITO;
	}
	// verifico se su istanzeruoli
	TipoAccessoEnum tipoaccesso = istanzeruoliService.checkByIstanzaAndResponsabile(istanza, responsabile);
	if (tipoaccesso.equals(TipoAccessoEnum.NON_CONSENTITO)) {
	    // Per funzionalità ANTICORRUZIONE O PRESA IN CARICO DEGLI ISTRUTTORI
	    // VIENE CARICATO COME ITRUTTORE TEMP E QUINDI è SENZA PERMESSI
	    // NON PUO' MODIFICARE LA PRATICA MA LA DEVE VEDERE E USARE SOLO LA FUNZIONALITA' PRENDI INCARICO
	    if (istanza.getIstruttoreTemp() != null && istanza.getIstruttoreTemp().getId() != null
		    && istanza.getIstruttoreTemp().getId().getCodice() != null) {
		if (istanza.getIstruttore() == null || istanza.getIstruttore().getId() == null
			|| istanza.getIstruttore().getId().getCodice() == null) {
		    if (istanza.getIstruttoreTemp().getId().getCodice().equals(responsabile.getId().getCodice())) {
			return TipoAccessoEnum.SOLA_LETTURA;
		    }
		}
	    }
	}
	return tipoaccesso;
    }

    @Override
    public synchronized String findProgressivoIstanza(Integer alberoprocCodice, boolean scriviSubito) {

	String progressivo = "";
	Configurazione configurazione = configurazioneService.findById(new ConfigurazioneId());
	if (configurazione == null) {
	    throw new InvalidConfigurationException("Nessuna configurazione trovata per il software [" + ORMHelper.getSoftware() + "]");
	}
	if (BooleanUtils.isTrue(configurazione.getNumerazione())) {
	    if (BooleanUtils.isFalse(configurazione.getFlagAttivacontatorealberoproc())) {
		progressivo = configurazione.getProgressivoistanze();
	    } else {
		if (alberoprocCodice == null) {
		    progressivo = ""; // non è possibile calcolarlo
		} else {
		    Alberoproc alberoproc = alberoprocService.findById(new PkId(alberoprocCodice));
		    if (null == alberoproc) {
			throw new InvalidConfigurationException("Nessuna alberoproc trovato per il codice [" + alberoprocCodice + "]");
		    }
		    progressivo = alberoprocService.findProgressivo(alberoproc);
		}
		if (StringUtils.isBlank(progressivo)) {
		    progressivo = configurazione.getProgressivoistanze();
		}
	    }
	}
	if (scriviSubito) {
	    if (StringUtils.isNotBlank(progressivo)) {
		scriviProgressivo(progressivo, alberoprocCodice, ORMHelper.getSoftware(), true);
	    }
	}
	return progressivo;
    }

    @Override
    public synchronized void scriviProgressivo(String numeroIstanza, Integer alberoProcCodice, String codiceSoftware, boolean eseguiCommitImmediata) {

	log.debug("scriviProgressivo: inizio");
	Configurazione configurazione = configurazioneService.findById(new ConfigurazioneId(ORMHelper.getSoftware()));
	if (configurazione == null) {
	    throw new RuntimeException("Non è presente la configurazione per il modulo software " + ORMHelper.getSoftware());
	}
	if (BooleanUtils.isTrue(configurazione.getNumerazione())) {
	    log.debug("scriviProgressivo: la numerazione dei progressivi è configurata");
	    if (BooleanUtils.isFalse(configurazione.getFlagAttivacontatorealberoproc())) {
		log.debug("scriviProgressivo: la numerazione viene generata dalla configurazione");
		// aggiorna la configurazione
		if (StringUtils.isBlank(configurazione.getProgressivoistanze())) {
		    throw new RuntimeException(
			    "Errore nel calcolo del progressivo. Verificare le impostazioni in configurazione-->dati tecnici del modulo " +
					       ORMHelper.getSoftware());
		}
		String nuovoProgressivo = tipologiaregistriService.calcolaNuovoProgressivo(configurazione.getProgressivoistanze(), numeroIstanza);
		log.debug("scriviProgressivo: nuovoProgressivo={}", nuovoProgressivo);
		if (StringUtils.isNotBlank(nuovoProgressivo)) {
		    configurazione.setProgressivoistanze(nuovoProgressivo);
		    log.debug("scriviProgressivo: aggiorno la configurazione");
		    configurazioneService.update(configurazione);
		    if (eseguiCommitImmediata) {
			istanzeDAO.flush();
			istanzeDAO.commit();
			istanzeDAO.flush();
		    }
		}
	    } else {
		log.debug("scriviProgressivo: la numerazione viene generata dall'alberoprocedimento scelto");
		// aggiorna alberoprocedimenti
		if (alberoProcCodice != null) {
		    Alberoproc alberoproc = alberoprocService.findById(new PkId(alberoProcCodice));
		    String ultimoProg = alberoprocService.findProgressivo(alberoproc);
		    log.debug("scriviProgressivo: ultimoProg={}", ultimoProg);
		    if (StringUtils.isBlank(ultimoProg)) {
			if (StringUtils.isBlank(configurazione.getProgressivoistanze())) {
			    throw new RuntimeException(
				    "Errore nel calcolo del progressivo. Nel Modulo " + ORMHelper.getSoftware() + " la voce dell'albero " +
						       alberoproc.getVwAlberoproc().getScDescrizione() + " [" + alberoproc.getId() +
						       "] non ha definito un progressivo e questo non è stato trovato neanche in configurazione-->dati tecnici del modulo " +
						       ORMHelper.getSoftware());
			}
			ultimoProg = tipologiaregistriService.calcolaNuovoProgressivo(configurazione.getProgressivoistanze(), numeroIstanza);
			log.debug("scriviProgressivo: nuovoProgressivo={}", ultimoProg);
			if (StringUtils.isNotBlank(ultimoProg)) {
			    configurazione.setProgressivoistanze(ultimoProg);
			    log.debug("scriviProgressivo: aggiorno la configurazione");
			    configurazioneService.update(configurazione);
			    if (eseguiCommitImmediata) {
				istanzeDAO.flush();
				istanzeDAO.commit();
				istanzeDAO.flush();
			    }
			}
		    } else {
			if (StringUtils.isBlank(ultimoProg)) {
			    throw new RuntimeException(
				    "Errore nel calcolo del progressivo. Nel Modulo " + ORMHelper.getSoftware() + " la voce dell'albero " +
						       alberoproc.getVwAlberoproc().getScDescrizione() + " [" + alberoproc.getId() +
						       "] non ha definito un progressivo e questo non è stato trovato neanche in configurazione-->dati tecnici del modulo " +
						       ORMHelper.getSoftware());
			}
			ultimoProg = tipologiaregistriService.calcolaNuovoProgressivo(ultimoProg, numeroIstanza);
			log.debug("scriviProgressivo: ultimoProgressivo={}", ultimoProg);
			if (StringUtils.isNotBlank(ultimoProg)) {
			    log.debug("scriviProgressivo: scrivo il progressivo sulla voce di albero");
			    alberoprocService.updateProgressivo(alberoproc, ultimoProg);
			    if (eseguiCommitImmediata) {
				istanzeDAO.flush();
				istanzeDAO.commit();
				istanzeDAO.flush();
			    }
			}
		    }
		} else {
		    if (StringUtils.isBlank(configurazione.getProgressivoistanze())) {
			throw new RuntimeException("Errore nel calcolo del progressivo. Nel Modulo " + ORMHelper
				.getSoftware() + " non e' stato definito un progressivo e questo non è stato trovato neanche in configurazione-->dati tecnici del modulo " +
						   ORMHelper.getSoftware());
		    }
		    String ultimoProg = tipologiaregistriService.calcolaNuovoProgressivo(configurazione.getProgressivoistanze(), numeroIstanza);
		    log.debug("scriviProgressivo: nuovoProgressivo={}", ultimoProg);
		    if (StringUtils.isNotBlank(ultimoProg)) {
			configurazione.setProgressivoistanze(ultimoProg);
			log.debug("scriviProgressivo: aggiorno la configurazione");
			configurazioneService.update(configurazione);
			if (eseguiCommitImmediata) {
			    istanzeDAO.flush();
			    istanzeDAO.commit();
			    istanzeDAO.flush();
			}
		    }
		}
	    }
	}
    }

    @Override
    public String checkModificaIntervento(Istanze istanza) {

	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    throw new IllegalArgumentException("Attenzione!! Non è stata indicata nessuna istanza come parametro");
	}
	boolean verticalizzazioneAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_MODIFICA_INTERVENTO);
	if (verticalizzazioneAttiva) {
	    return "";
	}
	Istanze copy = this.findById(istanza.getId());
	StringBuilder answer = new StringBuilder("");
	String tipoMovAvvio = copy.getTipoMovimentoAvvio().getId().getTipomovimento();
	List<Movimenti> movimentis = movimentiService.findEseguitiByIstanza(copy);
	for (Movimenti movimenti : movimentis) {
	    String tipoMovimento = movimenti.getTipomovimento().getId().getTipomovimento();
	    if (!tipoMovAvvio.equalsIgnoreCase(tipoMovimento)) {
		answer.append("- l' istanza ha dei movimenti oltre a quello di avvio.\n");
		break;
	    }
	}
	int countOneri = istanzeoneriService.countByIstanza(copy.getId().getCodice());
	if (countOneri > 0) {
	    answer.append("- l' istanza ha degli oneri registrati.\n");
	}
	int countProcedimenti = istanzeprocedimentiService.countByIstanza(copy.getId().getCodice());
	if (countProcedimenti > 0) {
	    answer.append("- l' istanza ha degli endoprocedimenti registrati.\n");
	}
	int countDocumenti = documentiistanzaService.countByIstanza(copy.getId().getCodice());
	if (countDocumenti > 0) {
	    answer.append("- l' istanza ha dei documenti collegati.\n");
	}
	int countDatiDinamici = istanzedyn2datiService.countByIstanza(copy.getId().getCodice());
	if (countDatiDinamici > 0) {
	    answer.append("- l' istanza ha degli altri dati specificati.\n");
	}
	if (answer.length() > 0) {
	    answer.insert(0, "ATTENZIONE: Non è possibile modificare l'intervento perchè:\n");
	}
	return answer.toString();
    }

    @Override
    protected boolean isDeleteAllowed(Istanze entity) {

	boolean delete = true;
	IstanzeBusinessRules rules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
	boolean forzaCancellazione = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.forzaCancellazionePratica.name());
	if (forzaCancellazione == false) {
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    if (entity.getRegistrazionis() != null && !entity.getRegistrazionis().isEmpty()) {
		_ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "REGISTRAZIONI", null));
		delete = false;
	    }
	    if (entity.getIstanzesForFkIstanzapadre() != null && !entity.getIstanzesForFkIstanzapadre().isEmpty()) {
		_ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ISTANZEREPLICATE", null));
		delete = false;
	    }
	    if (entity.getGraduatorieds() != null && !entity.getGraduatorieds().isEmpty()) {
		_ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "GRADUATORIED", null));
		delete = false;
	    }
	    if (entity.getSorteggidettaglios() != null && !entity.getSorteggidettaglios().isEmpty()) {
		_ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "SORTEGGIDETTAGLIO", null));
		delete = false;
	    }
	    if (hasOneriPagatiEsternamente(entity)) {
		_ivs.add(new InvalidValue(WebConstants.ALERT_ONERI_PAGATI_ESTERNAMENTE, null, "", "", null));
		delete = false;
	    }
	    if (!pecInboxService.findByIstanza(entity.getId().getCodice(), 0, 1).isEmpty()) {
		_ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "PEC_INBOX", null));
		delete = false;
	    }
	    List<IstanzeAccessoAttiD> iaaDs = istanzeAccessoAttiDService.findByIstanza(entity.getId().getCodice());
	    if (!iaaDs.isEmpty()) {
		_ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZE_ACCESSO_ATTI_D", null));
		delete = false;
	    }
	    List<SpuntistiMercati> spuntmercati = spuntistiMercatiService.findByIstanza(entity.getId().getCodice(), null);
	    if (!spuntmercati.isEmpty()) {
		_ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "SPUNTISTI_MERCATI", null));
		delete = false;
	    }
	    if (!delete) {
		this.throwValidationMessages(_ivs);
	    }
	}
	return delete;
    }

    @Override
    public Mailtipo findProtocolloOggetto(Istanze entity) {

	Istanze istanze = this.findById(entity.getId());
	Mailtipo mailtipoDefault = new Mailtipo();
	mailtipoDefault.setOggetto(mailtipoService.getOggettoProtocollazioneDefault() + istanze.getNumeroistanza());
	Integer codTesto = (Integer) alberoprocService.findParametroprotocollo(istanze.getAlberoproc(), "testoProtocolloId",
		entity.getComune().getCodicecomune());
	if (codTesto == null) {
	    ProtocolloConfigurazione protocolloConfigurazione = protocolloConfigurazioneService
		    .findById(new ProtocolloConfigurazioneId(ORMHelper.getSoftware()));
	    if (protocolloConfigurazione != null) {
		Mailtipo oggettoIstanza = protocolloConfigurazione.getMailtipoByFkIstanza();
		if (oggettoIstanza != null) {
		    if (StringUtils.isNotBlank(oggettoIstanza.getOggetto())) {
			Mailtipo result = mailtipoService.replaceOggettoCorpo(oggettoIstanza, istanze, null);
			Mailtipo mailProtocolloReplaced = mailtipoService.replaceOggettoCorpoProtocollo(oggettoIstanza, istanze, null);
			result.setProtocolloOggettoMail(mailProtocolloReplaced.getProtocolloOggettoMail());
			result.setProtocolloCorpoMail(mailProtocolloReplaced.getProtocolloCorpoMail());
			return result;
		    }
		}
	    }
	} else {
	    Mailtipo mailtipo = mailtipoService.findById(new PkId(codTesto));
	    if (mailtipo != null) {
		if (StringUtils.isNotBlank(mailtipo.getOggetto())) {
		    Mailtipo result = mailtipoService.replaceOggettoCorpo(mailtipo, istanze, null);
		    Mailtipo mailProtocolloReplaced = mailtipoService.replaceOggettoCorpoProtocollo(mailtipo, istanze, null);
		    result.setProtocolloOggettoMail(mailProtocolloReplaced.getProtocolloOggettoMail());
		    result.setProtocolloCorpoMail(mailProtocolloReplaced.getProtocolloCorpoMail());
		    return result;
		}
	    }
	}
	return mailtipoDefault;
    }

    @Override
    public String findFascicoloOggetto(Istanze entity) {

	Istanze istanze = this.findById(entity.getId());
	String oggettoDefault = mailtipoService.getOggettoFascicolazioneDefault() + istanze.getNumeroistanza();
	Integer codTesto = (Integer) alberoprocService.findParametroprotocollo(istanze.getAlberoproc(), "testoFascicoloId",
		entity.getComune().getCodicecomune());
	if (codTesto != null) {
	    Mailtipo mailtipo = mailtipoService.findById(new PkId(codTesto));
	    if (mailtipo != null) {
		if (StringUtils.isNotBlank(mailtipo.getOggetto())) {
		    Mailtipo result = mailtipoService.replaceOggettoCorpo(mailtipo, istanze, null);
		    oggettoDefault = result.getOggetto();
		    return oggettoDefault;
		}
	    }
	}
	return oggettoDefault;
    }

    private AlberoprocService alberoprocService;
    private AnagrafeService anagrafeService;
    private Aree2Service aree2Service;
    private AutorizzazioniService autorizzazioniService;
    private AutorizzazioniSubentriService autorizzazioniSubentriService;
    private CcIcalcolototService ccIcalcolototService;
    private ComuniService comuniService;
    private ComuniassociatiService comuniassociatiService;
    private CdsService cdsService;
    private Dyn2ModellitService dyn2ModellitService;
    private FoArconfigurazioneService foArconfigurazioneService;
    private GraduatoriedService graduatoriedService;
    private IstanzeareeService istanzeareeService;
    private IstanzeattivitaService istanzeattivitaService;
    private IstanzecalcolocanoniTService istanzecalcolocanoniTService;
    private IstanzeeventiService istanzeeventiService;
    private IstanzefrontofficeService istanzefrontofficeService;
    private IstanzelavoriTService istanzelavoriTService;
    private IstanzepeopledService istanzepeopledService;
    private IstanzeoneriService istanzeoneriService;
    private IstanzereplicateService istanzereplicateService;
    private IAttivitaService iAttivitaService;
    private ImpiantiService impiantiService;
    private IstanzefidejussioniService istanzefidejussioniService;
    private IstanzeaffissioniService istanzeaffissioniService;
    private IstanzestradarioService istanzestradarioService;
    private IstanzemappaliService istanzemappaliService;
    private IstanzeallegatiService istanzeallegatiService;
    private IstanzecollegateService istanzecollegateService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private IstanzeprocedimentiService istanzeprocedimentiService;
    private ChiusureistanzaService chiusureistanzaService;
    private DocumentiistanzaService documentiistanzaService;
    private IstanzerichiedentiService istanzerichiedentiService;
    private Istanzedyn2modellitService istanzedyn2modellitService;
    private Istanzedyn2modellitStoricoService istanzedyn2modellitStoricoService;
    private Istanzedyn2datiService istanzedyn2datiService;
    private Istanzedyn2datiStoricoService istanzedyn2datiStoricoService;
    private IstanzeprocureService istanzeprocureService;
    private IstanzeruoliService istanzeruoliService;
    private IstanzeTempisticaService istanzeTempisticaService;
    private OggettiService oggettiService;
    private OrariaperturatestataService orariaperturatestataService;
    private ProtocollazioneService protocollazioneService;
    private MovimentiService movimentiService;
    private MovimentiNoSecurityService movimentiNoSecurityService;
    private MovimentiTempisticaService movimentiTempisticaService;
    private IstanzehummingbirdService istanzehummingbirdService;
    private OIcalcolototService oIcalcolototService;
    private IstanzeDAO istanzeDAO;
    private IstanzeRiService istanzeRiService;
    private GraduatorietService graduatorietService;
    private StatiistanzaService statiistanzaService;
    private ConfigurazioneService configurazioneService;
    private MailtipoService mailtipoService;
    private MovimentiContromovimentiService movimentiContromovimentiService;
    private PermistanzeService permistanzeService;
    private ProtocolloConfigurazioneService protocolloConfigurazioneService;
    private PuFormatiService puFormatiService;
    private RegistrazioniService registrazioniService;
    private ResponsabiliService responsabiliService;
    private SoftwareService softwareService;
    private SorteggidettaglioService sorteggidettaglioService;
    private TipiarchivioistanzeService tipiarchivioistanzeService;
    private TipiprocedureService tipiprocedureService;
    private TipiMovimentoService tipiMovimentoService;
    private TipimovimentoDisService tipimovimentoDisService;
    private TipisoggettoService tipisoggettoService;
    private TipologiaistanzaService tipologiaistanzaService;
    private TipologiaregistriService tipologiaregistriService;
    private VerticalizzazioniService verticalizzazioniService;
    private DomandestcService domandestcService;
    private AutorizzazioniConcessioniService autorizzazioniConcessioniService;
    private AnagrafedocumentiService anagrafedocumentiService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setAree2Service(Aree2Service aree2Service) {

	this.aree2Service = aree2Service;
    }

    //    @Autowired
    //    public void setBatchScadIstanzeService(BatchScadIstanzeService batchScadIstanzeService) {
    //
    //	this.batchScadIstanzeService = batchScadIstanzeService;
    //    }
    @Autowired
    public void setCcIcalcolototService(CcIcalcolototService ccIcalcolototService) {

	this.ccIcalcolototService = ccIcalcolototService;
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
    public void setGraduatoriedService(GraduatoriedService graduatoriedService) {

	this.graduatoriedService = graduatoriedService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setIstanzefrontofficeService(IstanzefrontofficeService istanzefrontofficeService) {

	this.istanzefrontofficeService = istanzefrontofficeService;
    }

    @Autowired
    public void setIstanzeRiService(IstanzeRiService istanzeRiService) {

	this.istanzeRiService = istanzeRiService;
    }

    @Autowired
    public void setIstanzeDAO(IstanzeDAO istanzeDAO) {

	this.istanzeDAO = istanzeDAO;
    }

    @Autowired
    public void setGraduatorietService(GraduatorietService graduatorietService) {

	this.graduatorietService = graduatorietService;
    }

    @Autowired
    public void setStatiistanzaService(StatiistanzaService statiistanzaService) {

	this.statiistanzaService = statiistanzaService;
    }

    @Autowired
    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setImpiantiService(ImpiantiService impiantiService) {

	this.impiantiService = impiantiService;
    }

    @Autowired
    public void setIstanzelavoriTService(IstanzelavoriTService istanzelavoriTService) {

	this.istanzelavoriTService = istanzelavoriTService;
    }

    @Autowired
    public void setIstanzeoneriService(IstanzeoneriService istanzeoneriService) {

	this.istanzeoneriService = istanzeoneriService;
    }

    @Autowired
    public void setiAttivitaService(IAttivitaService iAttivitaService) {

	this.iAttivitaService = iAttivitaService;
    }

    @Autowired
    public void setIstanzefidejussioniService(IstanzefidejussioniService istanzefidejussioniService) {

	this.istanzefidejussioniService = istanzefidejussioniService;
    }

    @Autowired
    public void setIstanzeaffissioniService(IstanzeaffissioniService istanzeaffissioniService) {

	this.istanzeaffissioniService = istanzeaffissioniService;
    }

    @Autowired
    public void setIstanzestradarioService(IstanzestradarioService istanzestradarioService) {

	this.istanzestradarioService = istanzestradarioService;
    }

    @Autowired
    public void setIstanzemappaliService(IstanzemappaliService istanzemappaliService) {

	this.istanzemappaliService = istanzemappaliService;
    }

    @Autowired
    public void setCdsService(CdsService cdsService) {

	this.cdsService = cdsService;
    }

    @Autowired
    public void setDyn2ModellitService(Dyn2ModellitService dyn2ModellitService) {

	this.dyn2ModellitService = dyn2ModellitService;
    }

    @Autowired
    public void setFoArconfigurazioneService(FoArconfigurazioneService foArconfigurazioneService) {

	this.foArconfigurazioneService = foArconfigurazioneService;
    }

    @Autowired
    public void setAutorizzazioniSubentriService(AutorizzazioniSubentriService autorizzazioniSubentriService) {

	this.autorizzazioniSubentriService = autorizzazioniSubentriService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setIstanzeallegatiService(IstanzeallegatiService istanzeallegatiService) {

	this.istanzeallegatiService = istanzeallegatiService;
    }

    @Autowired
    public void setIstanzecollegateService(IstanzecollegateService istanzecollegateService) {

	this.istanzecollegateService = istanzecollegateService;
    }

    @Autowired
    public void setPermistanzeService(PermistanzeService permistanzeService) {

	this.permistanzeService = permistanzeService;
    }

    @Autowired
    public void setIstanzeprocedimentiService(IstanzeprocedimentiService istanzeprocedimentiService) {

	this.istanzeprocedimentiService = istanzeprocedimentiService;
    }

    @Autowired
    public void setChiusureistanzaService(ChiusureistanzaService chiusureistanzaService) {

	this.chiusureistanzaService = chiusureistanzaService;
    }

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setIstanzerichiedentiService(IstanzerichiedentiService istanzerichiedentiService) {

	this.istanzerichiedentiService = istanzerichiedentiService;
    }

    @Autowired
    public void setIstanzedyn2modellitService(Istanzedyn2modellitService istanzedyn2modellitService) {

	this.istanzedyn2modellitService = istanzedyn2modellitService;
    }

    @Autowired
    public void setIstanzedyn2modellitStoricoService(Istanzedyn2modellitStoricoService istanzedyn2modellitStoricoService) {

	this.istanzedyn2modellitStoricoService = istanzedyn2modellitStoricoService;
    }

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Autowired
    public void setIstanzedyn2datiStoricoService(Istanzedyn2datiStoricoService istanzedyn2datiStoricoService) {

	this.istanzedyn2datiStoricoService = istanzedyn2datiStoricoService;
    }

    @Autowired
    public void setIstanzeprocureService(IstanzeprocureService istanzeprocureService) {

	this.istanzeprocureService = istanzeprocureService;
    }

    @Autowired
    public void setIstanzeruoliService(IstanzeruoliService istanzeruoliService) {

	this.istanzeruoliService = istanzeruoliService;
    }

    @Autowired
    public void setIstanzeTempisticaService(IstanzeTempisticaService istanzeTempisticaService) {

	this.istanzeTempisticaService = istanzeTempisticaService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setOrariaperturatestataService(OrariaperturatestataService orariaperturatestataService) {

	this.orariaperturatestataService = orariaperturatestataService;
    }

    @Autowired
    public void setProtocollazioneService(ProtocollazioneService protocollazioneService) {

	this.protocollazioneService = protocollazioneService;
    }

    @Autowired
    public void setIstanzeareeService(IstanzeareeService istanzeareeService) {

	this.istanzeareeService = istanzeareeService;
    }

    @Autowired
    public void setIstanzeattivitaService(IstanzeattivitaService istanzeattivitaService) {

	this.istanzeattivitaService = istanzeattivitaService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setMovimentiNoSecurityService(MovimentiNoSecurityService movimentiNoSecurityService) {

	this.movimentiNoSecurityService = movimentiNoSecurityService;
    }

    @Autowired
    public void setMovimentiTempisticaService(MovimentiTempisticaService movimentiTempisticaService) {

	this.movimentiTempisticaService = movimentiTempisticaService;
    }

    @Autowired
    public void setIstanzehummingbirdService(IstanzehummingbirdService istanzehummingbirdService) {

	this.istanzehummingbirdService = istanzehummingbirdService;
    }

    @Autowired
    public void setoIcalcolototService(OIcalcolototService oIcalcolototService) {

	this.oIcalcolototService = oIcalcolototService;
    }

    @Autowired
    public void setIstanzecalcolocanoniTService(IstanzecalcolocanoniTService istanzecalcolocanoniTService) {

	this.istanzecalcolocanoniTService = istanzecalcolocanoniTService;
    }

    @Autowired
    public void setIstanzepeopledService(IstanzepeopledService istanzepeopledService) {

	this.istanzepeopledService = istanzepeopledService;
    }

    @Autowired
    public void setIstanzereplicateService(IstanzereplicateService istanzereplicateService) {

	this.istanzereplicateService = istanzereplicateService;
    }

    @Autowired
    public void setRegistrazioniService(RegistrazioniService registrazioniService) {

	this.registrazioniService = registrazioniService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setSorteggidettaglioService(SorteggidettaglioService sorteggidettaglioService) {

	this.sorteggidettaglioService = sorteggidettaglioService;
    }

    @Autowired
    public void setTipiarchivioistanzeService(TipiarchivioistanzeService tipiarchivioistanzeService) {

	this.tipiarchivioistanzeService = tipiarchivioistanzeService;
    }

    @Autowired
    public void setTipiprocedureService(TipiprocedureService tipiprocedureService) {

	this.tipiprocedureService = tipiprocedureService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setTipimovimentoDisService(TipimovimentoDisService tipimovimentoDisService) {

	this.tipimovimentoDisService = tipimovimentoDisService;
    }

    @Autowired
    public void setTipisoggettoService(TipisoggettoService tipisoggettoService) {

	this.tipisoggettoService = tipisoggettoService;
    }

    @Autowired
    public void setTipologiaistanzaService(TipologiaistanzaService tipologiaistanzaService) {

	this.tipologiaistanzaService = tipologiaistanzaService;
    }

    @Autowired
    public void setTipologiaregistriService(TipologiaregistriService tipologiaregistriService) {

	this.tipologiaregistriService = tipologiaregistriService;
    }

    @Autowired
    public void setPuFormatiService(PuFormatiService puFormatiService) {

	this.puFormatiService = puFormatiService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setMovimentiContromovimentiService(MovimentiContromovimentiService movimentiContromovimentiService) {

	this.movimentiContromovimentiService = movimentiContromovimentiService;
    }

    @Autowired
    public void setProtocolloConfigurazioneService(ProtocolloConfigurazioneService protocolloConfigurazioneService) {

	this.protocolloConfigurazioneService = protocolloConfigurazioneService;
    }

    @Autowired
    public void setDomandestcService(DomandestcService domandestcService) {

	this.domandestcService = domandestcService;
    }

    @Autowired
    public void setAutorizzazioniConcessioniService(AutorizzazioniConcessioniService autorizzazioniConcessioniService) {

	this.autorizzazioniConcessioniService = autorizzazioniConcessioniService;
    }

    @Autowired
    public void setAnagrafedocumentiService(AnagrafedocumentiService anagrafedocumentiService) {

	this.anagrafedocumentiService = anagrafedocumentiService;
    }

    @Override
    public void elabora(Integer codiceIstanza, boolean forzaElaborazionePerIstanzeChiuse) {

	this.elabora(codiceIstanza, forzaElaborazionePerIstanzeChiuse, null);
    }

    @Override
    public void elabora(Integer codiceIstanza, boolean forzaElaborazionePerIstanzeChiuse, Date elaboraAttivitaDopoData) {

	// BOCCI 2012-01-22 IL  parametro forzaElaborazionePerIstanzeChiuse è stato aggiunto per forzare l'esecuzione 
	// della elaborazione anche dei movimenti precedenti a quello di chiusura istanza 
	Istanze istanza = this.findById(new PkId(codiceIstanza));
	//1. Eseguo l'aggiornamento di tutti i movimenti 
	List<Movimenti> movimentis = movimentiService.findEseguitiByIstanza(istanza);
	ServiceValidationRules serviceValidationRules = (ServiceValidationRules) SigeproBusinessRules.getClassRules(ServiceValidationRules.class);
	boolean oldBusinessValidationRuleValue = serviceValidationRules
		.getCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name());
	try {
	    if (forzaElaborazionePerIstanzeChiuse) {
		/**
		 * DISABILITO LA VALIDAZIONE BUSINESS PER EVITARE ERRORI DEL TIPO "Errori di validazione. Impossibile
		 * inserire o modificare un movimento con data di presentazione antecedente la data di chiusura
		 * dell'istanza"
		 */
		serviceValidationRules.setCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name(), false);
		SigeproBusinessRules.setClassRules(ServiceValidationRules.class, serviceValidationRules);
	    }
	    Responsabili resp = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    for (Movimenti movimenti : movimentis) {
		// Fix Errore org.hibernate.LazyInitializationException: could not initialize proxy - no Session Ticket#2015021710000089
		Movimenti m = movimentiNoSecurityService.findById(new PkId(movimenti.getId().getCodice()));
		if (movimentiNoSecurityService.isMovimentoModificabile(m) || forzaElaborazionePerIstanzeChiuse) {
		    boolean elabora = true;
		    if (elaboraAttivitaDopoData != null) {
			if (Utilities.compareDates(elaboraAttivitaDopoData, m.getData()) > 0) {
			    // se la data del movimento che sto considerando è uguale o successiva a quella della data passata allora elaboro altrimenti no
			    elabora = false; // è STATA PASSATA UNA DATA OLTRE LA QUALE CONSIDERARE I MOVIMENTI DA AGGIORNARE
			}
		    }
		    if (elabora) {
			if (movimentiService.checkPermessiMovimento(m, resp, true)) {
			    movimentiService.update(m);
			}
		    }
		}
	    }
	    boolean isIstanzaModificabile = this.checkModificaIstanza(istanza);
	    if (isIstanzaModificabile || forzaElaborazionePerIstanzeChiuse) {
		//2. Controllo che i movimenti degli endo procedimenti siano stati inseriti
		List<Istanzeprocedimenti> istanzeprocedimentis = istanzeprocedimentiService.findByIstanze(istanza);
		for (Istanzeprocedimenti istanzeprocedimenti : istanzeprocedimentis) {
		    istanzeprocedimentiService.inserisciMovimentoIstanzeprocedimenti(istanzeprocedimenti);
		}
	    }
	} finally {
	    // IN CASO DI ERRORE NELL'ELABORAZIONE RISETTO LA REGOLA DI VALIDAZIONE
	    if (forzaElaborazionePerIstanzeChiuse) {
		serviceValidationRules.setCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name(),
			oldBusinessValidationRuleValue);
		SigeproBusinessRules.setClassRules(ServiceValidationRules.class, serviceValidationRules);
	    }
	}
	this.calcolaTempisticaIstanza(istanza);
    }

    @Override
    public int calcolaDurataProcedimento(Istanze istanza) {

	this.gestisciIstanzetempistica(istanza);
	istanza = bindDomainObject(istanza, PkId.class, "id.codice");
	Integer durata = istanza.getIstanzeTempistica().getTransientDurataProcedimento();
	if (durata == null) {
	    durata = Integer.valueOf(0);
	}
	return durata.intValue();
    }

    @Override
    public void calcolaDataValidita(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    log.error("calcolaDataValidita: Il parametro istanza è nullo");
	    throw new IllegalArgumentException("Il parametro istanza è nullo");
	}
	Istanze istanza = this.findById(new PkId(codiceIstanza));
	Tipiprocedure procedura = istanza.getProcedura();
	procedura = tipiprocedureService.bindDomainObject(procedura, PkId.class, "id.codice");
	Date vecchiaDataValidita = istanza.getDatavalidita();
	// BOCCI-MENDICHI 2019-05-06 nel caso di istanza chiusa negativamente setto la data validita' nulla
	// le istanze chiuse negativamente non dovrebbero influire nella storia di una attivita' anche se ne dovrebbero far parte
	if (istanza.getChiusura() != null && istanza.getChiusura().getStaticomportamento() != null
		&& istanza.getChiusura().getStaticomportamento().getCodcomportamento() != null
		&& istanza.getChiusura().getStaticomportamento().getCodcomportamento().equals(-1)) {
	    // l'istanza è chiusa negativamente ma la data di validita' era già nulla allora non faccio niente soprattutto sulle attivita'
	    if (istanza.getDatavalidita() == null) {
		return;
	    }
	    istanza.setDatavalidita(null);
	    istanzeDAO.updateDatavalidita(ORMHelper.getIdcomune(), codiceIstanza, null);
	    istanzeDAO.flush();
	    //	    if (isAttivita) {
	    //IAttivita ia=iAttivitaService.findById(id)
	    //		log.debug(
	    //			"calcolaDataValidita# Ricalcolo data validità istanza {} secondo la regola {}.L'istanza fa parte dell'attivita {}; Ricalcolare data inizio e data fine",
	    //			new Object[] { istanza.getId().getCodice(), WebConstants.NC, istanza.getAttivita().getId().getCodice() });
	    //		iAttivitaService.updateDataInizioEFineAttivita(istanza.getAttivita().getId().getCodice());
	    /*
	     * Va rielaborato l'eventuale snapshot fatto nella data di validità dell'istanza prima di essere impostata a null
	     * Vanno rielaborati gli snapshot successivi
	     * */
	    this.eventPublisher.publish(new EventoIstanzaModificaDataValidita(istanza.getId().getCodice(), vecchiaDataValidita, null));
	    //		aggiornaIattivitaSnapshot(istanza, vecchiaDataValidita, istanza.getDatavalidita());
	    //			aggiornaIattivitaSnapshot(istanza.getAttivita(), istanza);
	    //	    }
	    return;
	}
	if (StringUtils.isNotBlank(procedura.getDeterminazioneefficacia())) {
	    String determinazioneDataVal = procedura.getDeterminazioneefficacia();
	    if (determinazioneDataVal.equalsIgnoreCase(WebConstants.NC)) {
		if (log.isDebugEnabled()) {
		    log.debug("validitaIstanza: la procedura {} non calcola la validità istanza : {}",
			    new Object[] { procedura.getId(), codiceIstanza });
		}
		if (vecchiaDataValidita != null) {
		    istanza.setDatavalidita(null);
		    istanzeDAO.updateDatavalidita(ORMHelper.getIdcomune(), codiceIstanza, null);
		    istanzeDAO.flush();
		    //		    if (isAttivita) {
		    //			//IAttivita ia=iAttivitaService.findById(id)
		    //			log.debug(
		    //				"calcolaDataValidita# Ricalcolo data validità istanza {} secondo la regola {}.L'istanza fa parte dell'attivita {}; Ricalcolare data inizio e data fine",
		    //				new Object[] { istanza.getId().getCodice(), WebConstants.NC, istanza.getAttivita().getId().getCodice() });
		    //			iAttivitaService.updateDataInizioEFineAttivita(istanza.getAttivita().getId().getCodice());
		    //			/*
		    //			 * Va rielaborato l'eventuale snapshot fatto nella data di validità dell'istanza prima di essere impostata a null
		    //			 * Vanno rielaborati gli snapshot successivi
		    //			 * */
		    //			//			iAttivitaService.updateSettaUltimaIstanza(istanza.getAttivita());
		    //			//			///////////////////////// RICHIAMA AGGIORNAMETO E RUTINE DI SNAPSHOT//////////////////////////
		    //			//			iAttivitaService.updateSchedeAndSnapshot(istanza, null, SnapshotType.CALCOLA_DATA_VALIDITA_ISTANZA_SNAPSHOT);
		    //			aggiornaIattivitaSnapshot(istanza, vecchiaDataValidita, istanza.getDatavalidita());
		    //			//			aggiornaIattivitaSnapshot(istanza.getAttivita(), istanza);
		    //		    }
		    this.eventPublisher.publish(new EventoIstanzaModificaDataValidita(istanza.getId().getCodice(), vecchiaDataValidita, null));
		    return;
		}
		return;
	    }
	    if (determinazioneDataVal.equalsIgnoreCase(WebConstants.MA)) {
		Movimenti movimentoAvvio = movimentiNoSecurityService.findMovimentiByTipoMovimento(codiceIstanza,
			istanza.getTipoMovimentoAvvio().getId().getTipomovimento());
		if (movimentoAvvio != null) {
		    Date dataMovimento = movimentoAvvio.getData();
		    if (doUpdateDataValidita(vecchiaDataValidita, dataMovimento)) { // se data istanze.validita è nulla o le due date sono differenti
			if (log.isDebugEnabled()) {
			    log.debug("calcolaDataValidita# la data di validità istanza {} è cambiata: [{}][{}]",
				    new Object[] { codiceIstanza, vecchiaDataValidita, dataMovimento });
			}
			dataMovimento = eliminaOra(dataMovimento);
			istanza.setDatavalidita(dataMovimento);
			istanzeDAO.updateDatavalidita(ORMHelper.getIdcomune(), codiceIstanza, istanza.getDatavalidita());
			istanzeDAO.flush();
			//			if (isAttivita) {
			//			    log.debug(
			//				    "calcolaDataValidita# Ricalcolo data validità istanza {} secondo la regola {}.L'istanza fa parte dell'attivita {}; Ricalcolare data inizio e data fine",
			//				    new Object[] { istanza.getId().getCodice(), WebConstants.MA, istanza.getAttivita().getId().getCodice() });
			//			    iAttivitaService.updateDataInizioEFineAttivita(istanza.getAttivita().getId().getCodice());
			//			    //			    iAttivitaService.updateSettaUltimaIstanza(istanza.getAttivita());
			//			    //			    ///////////////////////// RICHIAMA AGGIORNAMETO E RUTINE DI SNAPSHOT//////////////////////////
			//			    //			    iAttivitaService.updateSchedeAndSnapshot(istanza, null, SnapshotType.CALCOLA_DATA_VALIDITA_ISTANZA_SNAPSHOT);
			//			    //aggiornaIattivitaSnapshot(istanza.getAttivita(), istanza);
			//			    aggiornaIattivitaSnapshot(istanza, vecchiaDataValidita, istanza.getDatavalidita());
			//			}
			this.eventPublisher.publish(
				new EventoIstanzaModificaDataValidita(istanza.getId().getCodice(), vecchiaDataValidita, istanza.getDatavalidita()));
		    }
		    return;
		} else {
		    istanza.setDatavalidita(null);
		    istanzeDAO.updateDatavalidita(ORMHelper.getIdcomune(), codiceIstanza, null);
		    this.eventPublisher.publish(new EventoIstanzaModificaDataValidita(istanza.getId().getCodice(), vecchiaDataValidita, null));
		    // BOCCI MENDICHI 24/06/2019
		    // DURANTE L'IMPORT POTREBBERO ESSERCI MOVIMENTI CON DATA ANTECEDENTE A QUELLA DI AVVIO E QUINDI INSERITI PRIMA. 
		    // NON DEVO DARE ERRORE
		    // throw new BusinessValidationException(null, "L'istanza non ha un movimento di avvio inserito", null);
		    // BOCCI MENDICHI 24/06/2019
		    // DURANTE L'IMPORT POTREBBERO ESSERCI MOVIMENTI CON DATA ANTECEDENTE A QUELLA DI AVVIO E QUINDI INSERITI PRIMA. 
		    // NON DEVO DARE ERRORE
		}
	    } else if (determinazioneDataVal.equalsIgnoreCase(WebConstants.DP)) { // TODO VERIFICARE QUESTO
		Date dataInizioIstanza = this.calcolaDataInizioIstanza(istanza);
		if (dataInizioIstanza != null) {
		    int giorniProcedura = this.calcolaDurataProcedimento(istanza);
		    Calendar dataValiditaIstanza = Calendar.getInstance();
		    dataValiditaIstanza.setTime(dataInizioIstanza);
		    dataValiditaIstanza.add(Calendar.DATE, giorniProcedura);
		    if (doUpdateDataValidita(vecchiaDataValidita, dataValiditaIstanza.getTime())) { // se data istanze.validita è nulla o le due date sono differenti
			if (log.isDebugEnabled()) {
			    log.debug("calcolaDataValidita# la data di validità istanza {} è cambiata: [{}][{}]",
				    new Object[] { codiceIstanza, vecchiaDataValidita, dataValiditaIstanza.getTime() });
			}
			Date data = eliminaOra(dataValiditaIstanza.getTime());
			istanza.setDatavalidita(data);
			istanzeDAO.updateDatavalidita(ORMHelper.getIdcomune(), codiceIstanza, istanza.getDatavalidita());
			istanzeDAO.flush();
			//			if (isAttivita) {
			//			    log.debug(
			//				    "calcolaDataValidita# Ricalcolo data validità istanza {} secondo la regola {}.L'istanza fa parte dell'attivita {}; Ricalcolare data inizio e data fine",
			//				    new Object[] { istanza.getId().getCodice(), WebConstants.DP, istanza.getAttivita().getId().getCodice() });
			//			    iAttivitaService.updateDataInizioEFineAttivita(istanza.getAttivita().getId().getCodice());
			//			    //			    iAttivitaService.updateSettaUltimaIstanza(istanza.getAttivita());
			//			    //			    ///////////////////////// RICHIAMA AGGIORNAMETO E RUTINE DI SNAPSHOT//////////////////////////
			//			    //			    iAttivitaService.updateSchedeAndSnapshot(istanza, null, SnapshotType.CALCOLA_DATA_VALIDITA_ISTANZA_SNAPSHOT);
			//			    //aggiornaIattivitaSnapshot(istanza.getAttivita(), istanza);
			//			    aggiornaIattivitaSnapshot(istanza, vecchiaDataValidita, istanza.getDatavalidita());
			//			}
			this.eventPublisher.publish(
				new EventoIstanzaModificaDataValidita(istanza.getId().getCodice(), vecchiaDataValidita, istanza.getDatavalidita()));
		    }
		    return;
		} else {
		    if (vecchiaDataValidita != null) {
			istanza.setDatavalidita(null);
			istanzeDAO.updateDatavalidita(ORMHelper.getIdcomune(), codiceIstanza, null);
			istanzeDAO.flush();
			//			if (isAttivita) {
			//			    //			    iAttivitaService.updateSettaUltimaIstanza(istanza.getAttivita());
			//			    //			    ///////////////////////// RICHIAMA AGGIORNAMETO E RUTINE DI SNAPSHOT//////////////////////////
			//			    //			    iAttivitaService.updateSchedeAndSnapshot(istanza, null, SnapshotType.CALCOLA_DATA_VALIDITA_ISTANZA_SNAPSHOT);
			//			    //aggiornaIattivitaSnapshot(istanza.getAttivita(), istanza);
			//			    aggiornaIattivitaSnapshot(istanza, vecchiaDataValidita, istanza.getDatavalidita());
			//			}
			this.eventPublisher.publish(new EventoIstanzaModificaDataValidita(istanza.getId().getCodice(), vecchiaDataValidita, null));
		    }
		    return;
		}
	    } else if (determinazioneDataVal.equalsIgnoreCase(WebConstants.MS)) {
		Tipimovimento tipoMovimentoSpecificato = procedura.getTipimovimentoDeterminazione();
		if (tipoMovimentoSpecificato != null) {
		    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		    FilterRestriction tipoMovEsito = new FilterRestriction();
		    tipoMovEsito.addFilterField(FilterUtils.equals("istanzaId", istanza.getId().getCodice(), Integer.class));
		    tipoMovEsito
			    .addFilterField(FilterUtils.equals("tipomovimentoId", tipoMovimentoSpecificato.getId().getTipomovimento(), String.class));
		    int tipoEsitoConfigurato = procedura.getDeterminazioneesito() == null ? 0 : procedura.getDeterminazioneesito();
		    switch (tipoEsitoConfigurato) {
		    case 0: // qualsiasi
			break;
		    case 1: // esito negativo
			tipoMovEsito.addFilterField(FilterUtils.equals("esito", Boolean.FALSE, Boolean.class));
			break;
		    case 2: // esito positivo
			tipoMovEsito.addFilterField(FilterUtils.equals("esito", Boolean.TRUE, Boolean.class));
			break;
		    default:
			break;
		    }
		    tipoMovEsito.addFilterField(FilterUtils.isNotNull("data"));
		    ft.addRestriction(tipoMovEsito);
		    ft.addOrder(FilterUtils.orderDesc("data"));
		    ft.addOrder(FilterUtils.orderDesc("id.codice"));
		    List<Movimenti> movimentis = movimentiNoSecurityService.findByFilterTable(ft, 0, 1);
		    boolean doUpdate = false;
		    if (movimentis.size() == 0) {
			if (vecchiaDataValidita != null) {
			    istanza.setDatavalidita(null);
			    doUpdate = true;
			}
		    } else {
			Movimenti ultimoMovimento = movimentis.get(0);
			Date dv = ultimoMovimento.getData();
			if (vecchiaDataValidita == null) {
			    doUpdate = true;
			} else if (Utilities.compareDates(dv, vecchiaDataValidita) != 0) {
			    doUpdate = true;
			}
			if (doUpdate) {
			    dv = eliminaOra(dv);
			    istanza.setDatavalidita(dv);
			}
		    }
		    if (doUpdate == true) {
			if (log.isDebugEnabled()) {
			    log.debug("calcolaDataValidita# la data di validità istanza {} è cambiata: [{}][{}]",
				    new Object[] { codiceIstanza, vecchiaDataValidita, istanza.getDatavalidita() });
			}
			istanzeDAO.updateDatavalidita(ORMHelper.getIdcomune(), codiceIstanza, istanza.getDatavalidita());
			istanzeDAO.flush();
			//			if (isAttivita) {
			//			    log.debug(
			//				    "calcolaDataValidita# Ricalcolo data validità istanza {} secondo la regola {}.L'istanza fa parte dell'attivita {}; Ricalcolare data inizio e data fine",
			//				    new Object[] { istanza.getId().getCodice(), WebConstants.MS, istanza.getAttivita().getId().getCodice() });
			//			    iAttivitaService.updateDataInizioEFineAttivita(istanza.getAttivita().getId().getCodice());
			//			    //			    iAttivitaService.updateSettaUltimaIstanza(istanza.getAttivita());
			//			    //			    ///////////////////////// RICHIAMA AGGIORNAMETO E RUTINE DI SNAPSHOT//////////////////////////
			//			    //			    iAttivitaService.updateSchedeAndSnapshot(istanza, null, SnapshotType.CALCOLA_DATA_VALIDITA_ISTANZA_SNAPSHOT);
			//			    //aggiornaIattivitaSnapshot(istanza.getAttivita(), istanza);
			//			    aggiornaIattivitaSnapshot(istanza, vecchiaDataValidita, istanza.getDatavalidita());
			//			}
			this.eventPublisher.publish(
				new EventoIstanzaModificaDataValidita(istanza.getId().getCodice(), vecchiaDataValidita, istanza.getDatavalidita()));
		    }
		    return;
		} else {
		    log.error(
			    "validitaIstanza: La configurazione della procedura {} [{}] prevede di specificare un movimento per determinare il calcolo della validità istanza e il movimento non è stato specificato",
			    procedura.getProcedura(), procedura.getId());
		    throw new RuntimeException("La configurazione della procedura " + procedura
			    .getProcedura() + " prevede di specificare un movimento per determinare il calcolo della validità istanza e il movimento non è stato specificato");
		}
	    } else {
		log.error("validitaIstanza: determinazione della validità istanza non riconosciuta [{}] per la procedura {}", determinazioneDataVal,
			procedura.getId());
		throw new NotImplementedException("validitaIstanza: determinazione della validità istanza non riconosciuta [" +
						  determinazioneDataVal + "] per la procedura " + procedura.getId());
	    }
	} else {
	    log.warn("validitaIstanza: la procedura {} [{}] non ha nessun criterio di determinazione data validità",
		    new Object[] { procedura.getProcedura(), procedura.getId() });
	}
    }

    private Date eliminaOra(Date dataMovimento) {

	if (dataMovimento != null) {
	    Calendar c = Calendar.getInstance();
	    c.setTime(dataMovimento);
	    c.set(Calendar.HOUR, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.AM_PM, Calendar.AM);
	    return c.getTime();
	}
	return null;
    }

    /*
    private void aggiornaIattivitaSnapshot(Istanze istanza, Date dataSnapshotOrigine, Date dataSnapshotDestinazione) {
    
    	IAttivita attivita = istanza.getAttivita();
    	if (attivita != null) {
    	    // avoid could not initialize proxy - no Session
    	    attivita = iAttivitaService.findById(new PkId(attivita.getId().getCodice()));
    	    // Casi gestiti:
    	    // Caso 1 (Da data NULL ---> data validità valorizzata)
    	    // Caso 2 (Da data validità valorizzata ---> nuova data validità )
    	    // Caso 3 (Da data validità valorizzata ---> NULL )
    	    // Caso 4 (Da NULL ---> NULL )
    	    if (dataSnapshotOrigine != null) {
    		aggiornaIattivitaSnapshotPrimaCambioDV(attivita, istanza, dataSnapshotOrigine, dataSnapshotDestinazione);
    	    }
    	    if (dataSnapshotDestinazione != null) {
    		aggiornaIattivitaSnapshotDopoCambioDV(attivita, istanza, dataSnapshotOrigine, dataSnapshotDestinazione);
    	    }
    	}
    }
    */
    /*
    private void aggiornaIattivitaSnapshotPrimaCambioDV(IAttivita attivita, Istanze istanza, Date dataSnapshotOrigine,
        Date dataSnapshotDestinazione) {
    
    // Recupero la lista delle istanza alla data di validità uguale alla data di origine
    List<Istanze> list = this.findByAttivitaAndDataValidita(attivita.getId().getCodice(), dataSnapshotOrigine);
    if (list.isEmpty() && dataSnapshotDestinazione == null) {
        IAttivitaSnapshot iAttivitaSnapshot = iAttivitaSnapshotService.findByData(attivita, dataSnapshotOrigine);
        iAttivitaSnapshotService.delete(iAttivitaSnapshot);
        iAttivitaService.updateAndAggiornaSnapshot(attivita, dataSnapshotOrigine, istanza.getId().getCodice());
    } else {
        if (!list.isEmpty()) {
    	// Aggiorna l'attività e ricalcola gli snapshot a partire dalla data di validità dell'istanza
    	iAttivitaService.updateAndAggiornaSnapshot(attivita, dataSnapshotOrigine, istanza.getId().getCodice());
    	// Rilascia le risorse per evitare conflitti con le operazioni succesive 
    	istanzeDAO.flush();
    	istanzeDAO.clear();
    	iAttivitaService.updateSettaUltimaIstanza(attivita);
        }
    }
    }
    */
    /*
    private void aggiornaIattivitaSnapshotDopoCambioDV(IAttivita attivita, Istanze istanza, Date dataSnapshotOrigine, Date dataSnapshotDestinazione) {
    
    // Verifico se esiste lo snapshot alla data di destinazione passata
    IAttivitaSnapshot iAttivitaSnapshot = iAttivitaSnapshotService.findByData(attivita, dataSnapshotDestinazione);
    if (iAttivitaSnapshot == null && dataSnapshotOrigine != null) {
        // Recupero la lista delle istanza alla data di validità uguale alla data di origine
        List<Istanze> list = this.findByAttivitaAndDataValidita(attivita.getId().getCodice(), dataSnapshotOrigine);
        if (list.isEmpty()) {
    	// recupero lo snapshot dell'istanza a cui ho cambiato la data lo aggiorno con la nuova data calcolata.
    	IAttivitaSnapshot iAttivitaSnapshotOrigine = iAttivitaSnapshotService.findByData(attivita, dataSnapshotOrigine);
    	if (iAttivitaSnapshotOrigine == null) {
    	    iAttivitaSnapshotService.updateElaboraSnapshotAttivita(attivita.getId().getCodice());
    	} else {
    	    iAttivitaSnapshotOrigine.setData(dataSnapshotDestinazione);
    	    iAttivitaSnapshotService.update(iAttivitaSnapshotOrigine);
    	}
    	istanzeDAO.flush();
    	istanzeDAO.clear();
        }
    }
    this.updateCalcoloIattivitaOrdine(istanza, attivita);
    // Aggiorna l'attività e ricalcola gli snapshot a partire dalla data di validità dell'istanza
    iAttivitaService.updateAndAggiornaSnapshot(attivita, dataSnapshotDestinazione, istanza.getId().getCodice());
    // Rilascia le risorse per evitare conflitti con le operazioni succesive 
    istanzeDAO.flush();
    istanzeDAO.clear();
    iAttivitaService.updateSettaUltimaIstanza(attivita);
    }
    */
    /**
     * La funzione serve per verificare se aggiornare la data di validità istanza ed è usata internamente da
     * Calcoladatavalidità.<br />
     * Torna true se dataValiditaIstanza=null o le due date sono differenti.
     * 
     * @param dataValiditaIstanza
     * @param nuovaDataValidita
     * @return
     */
    private boolean doUpdateDataValidita(Date dataValiditaIstanza, Date nuovaDataValidita) {

	if (dataValiditaIstanza == null) {
	    return true;
	} else if (Utilities.compareDates(dataValiditaIstanza, nuovaDataValidita) != 0) {
	    return true;
	}
	return false;
    }

    @Override
    public Date calcolaDataInizioIstanza(Istanze istanza) {

	istanza = checkIstanzaParamNotNull(istanza);
	// Tipiprocedure procedura = istanza.getProcedura();
	// procedura = tipiprocedureService.bindDomainObject(procedura, PkId.class, "id.codice");
	DynaProperty[] properties = { new DynaProperty("determinazioneinizioistanza", String.class), new DynaProperty("procedura", String.class) };
	DynaClass userDynaClass = new BasicDynaClass("TipiprocedureDC", null, properties);
	DynaBean procedura = istanzeDAO.findDynaBeanById(ORMHelper.getIdcomune(), istanza.getProcedura().getId().getCodice(), userDynaClass,
		Tipiprocedure.class);
	String determinazioneinizioistanza = (String) procedura.get("determinazioneinizioistanza");
	String proceduraStr = (String) procedura.get("procedura");
	if (StringUtils.isNotBlank(determinazioneinizioistanza)) {
	    if (determinazioneinizioistanza.equalsIgnoreCase(WebConstants.PD)) {
		// data presentazione domanda
		Movimenti movimentoAvvio = movimentiNoSecurityService.findMovimentiByTipoMovimento(istanza.getId().getCodice(),
			istanza.getTipoMovimentoAvvio().getId().getTipomovimento());
		if (movimentoAvvio != null) {
		    Date dataMovimento = movimentoAvvio.getData();
		    return dataMovimento;
		}
	    } else if (determinazioneinizioistanza.equalsIgnoreCase(WebConstants.UT)) {
		// data ultima trasmissione
		Movimenti movimentoUltimaTrasmissione = this.dataUltimaTrasmissione(istanza);
		if (movimentoUltimaTrasmissione != null) {
		    return movimentoUltimaTrasmissione.getData();
		}
	    } else if (determinazioneinizioistanza.equalsIgnoreCase(WebConstants.PG)) {
		// data protocollo generale
		return istanza.getDataprotocollo();
	    }
	} else {
	    log.error(
		    "calcolaDataInizioIstanza: la procedura {} non è stata configurata correttamente: Manca il criterio di determinazione inizio istanza ",
		    proceduraStr);
	    throw new BusinessValidationException(null,
		    "La procedura " + proceduraStr + " non è stata configurata correttamente: Manca il criterio di determinazione inizio istanza",
		    null);
	}
	return null;
    }

    @Override
    public Movimenti dataUltimaTrasmissione(Istanze istanza) {

	checkIstanzaParamNotNull(istanza);
	Set<Istanzeprocedimenti> istanzeprocedimentis = istanza.getIstanzeprocedimentis();
	int movimentiDaTrasmettere = 0;
	if (istanzeprocedimentis.size() > 0) {
	    Map<Integer, String> endoMovimentiMap = new HashMap<Integer, String>();
	    for (Istanzeprocedimenti istanzeprocedimenti : istanzeprocedimentis) {
		if (EntityUtils.getNestedProperty(istanzeprocedimenti.getInventarioprocedimenti(), "tipomovimento.id.tipomovimento") != null) {
		    if (BooleanUtils.isTrue(istanzeprocedimenti.getPerprovvedimento())) {
			endoMovimentiMap.put(istanzeprocedimenti.getInventarioprocedimenti().getId().getCodice(),
				istanzeprocedimenti.getInventarioprocedimenti().getTipomovimento().getId().getTipomovimento());
			movimentiDaTrasmettere++;
		    }
		}
	    }
	    if (movimentiDaTrasmettere > 0) {
		FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		FilterRestriction istanzeprocedimentiFr = new FilterRestriction();
		istanzeprocedimentiFr
			.addFilterField(FilterUtils.equals("perprovvedimento", Boolean.TRUE, "istanza.istanzeprocedimentis", Boolean.class));
		istanzeprocedimentiFr.addFilterField(FilterUtils.equals("istanzaId", istanza.getId().getCodice(), Integer.class));
		ft.addRestriction(istanzeprocedimentiFr);
		Integer[] endoprocedimenti = new Integer[endoMovimentiMap.size()];
		endoprocedimenti = endoMovimentiMap.keySet().toArray(endoprocedimenti);
		istanzeprocedimentiFr.addFilterField(FilterUtils.in("endoprocedimentoId", endoprocedimenti, Integer.class));
		String[] tipoMovimento = new String[endoMovimentiMap.size()];
		tipoMovimento = endoMovimentiMap.values().toArray(tipoMovimento);
		istanzeprocedimentiFr.addFilterField(FilterUtils.in("tipomovimentoId", tipoMovimento, String.class));
		FilterRestriction movEseguiti = new FilterRestriction();
		movEseguiti.addFilterField(FilterUtils.isNotNull("data"));
		ft.addRestriction(movEseguiti);
		ft.addOrder(FilterUtils.orderDesc("data"));
		List<Movimenti> movimentis = movimentiService.findByFilterTable(ft);
		if (movimentis.size() == movimentiDaTrasmettere) {
		    // TUTTE LE TRASMISSIONI SONO STATE FATTE
		    // prendo il primo movimento della lista che è ordinata per data desc
		    return movimentis.get(0);
		} else {
		    // TUTTE LE TRASMISSIONI NON SONO STATE FATTE E RITORNO NULL
		    return null;
		}
	    }
	}
	return null;
    }

    private Istanze checkIstanzaParamNotNull(Istanze istanza) {

	if (istanza == null) {
	    log.error("checkIstanzaParamNotNull: Il parametro istanza passato è nullo");
	    throw new BusinessValidationException(null, "Il parametro istanza passato è nullo", null);
	}
	istanza = this.bindDomainObject(istanza, PkId.class, "id.codice");
	return istanza;
    }

    @Override
    public String checkDeleteIstanza(Istanze istanza) {

	String messaggio = "";
	try {
	    isDeleteAllowed(istanza);
	} catch (Exception e) {
	    String messaggioGenerale = getMessageFromBundle("alert.non_e_possibile_cancellare", null);
	    messaggio = messaggio.concat(messaggioGenerale).concat("<br />");
	    if (e instanceof BaseValidationException) {
		List<InvalidValue> warnings = ((BaseValidationException) e).getInvalidValues();
		for (InvalidValue invalidValue : warnings) {
		    String bundleMessage = getMessageFromBundle(invalidValue.getMessage(), new Object[] { invalidValue.getValue() });
		    messaggio = messaggio.concat("- ").concat(bundleMessage).concat("<br />");
		}
	    }
	}
	return messaggio;
    }

    private boolean hasOneriPagatiEsternamente(Istanze entity) {

	boolean isSistemaPagamento = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_SISTEMAPAGAMENTI_ATTIVO);
	if (isSistemaPagamento) {
	    Verticalizzazioniparametri tipoSistemaPagamento = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_SISTEMAPAGAMENTI_ATTIVO, WebConstants.VERTICALIZZAZIONE_SISTEMAPAGAMENTI_TIPOPAGAMENTO);
	    if (tipoSistemaPagamento != null) {
		if (StringUtils.defaultIfEmpty(tipoSistemaPagamento.getValore(), "")
			.equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_SISTEMAPAGAMENTI_TIPOPAGAMENTO_REGULUS)) {
		    Set<Istanzeoneri> oneri = entity.getIstanzeoneris();
		    for (Istanzeoneri istanzeoneri : oneri) {
			if (istanzeoneri.getIstanzeoneriReguluses().size() > 0) {
			    return true;
			}
		    }
		}
	    }
	}
	return false;
    }

    @Override
    public boolean checkModificaIstanza(Istanze istanza) {

	istanza = checkIstanzaParamNotNull(istanza);
	if (EntityUtils.getNestedProperty(istanza.getChiusura(), "id.codicestato") != null) {
	    return BooleanUtils.toBoolean(istanza.getChiusura().getModificaistanza());
	} else {
	    throw new BusinessValidationException(null, "L'istanza non ha uno stato valido [istanza.chiusura == null]", null);
	}
    }

    @Override
    public IstanzeOnLineHelper findIstanzeOnline() {

	IstanzeOnLineHelper helper = new IstanzeOnLineHelper();
	int numIstanzeConErrore = domandestcService.countDomandeConErrore();
	Statiistanza statoIOL = foArconfigurazioneService.findStatoInizialeIstanzaOnline();
	String stato = null;
	if (statoIOL != null) {
	    stato = statoIOL.getId().getCodicestato();
	}
	List<DomandestcHelper> listIstanzePervenute = domandestcService.countDomandestc(ORMHelper.getSoftware(), stato);
	helper.setNumDomandeConErrori(numIstanzeConErrore);
	Map<String, Integer> domandePervenute = new HashMap<String, Integer>();
	if (listIstanzePervenute.size() > 0) {
	    for (DomandestcHelper domandestc : listIstanzePervenute) {
		String key = domandestc.getIdnodo();
		String idNodo = decodeNodo(domandestc.getIdnodo());
		if (idNodo.equalsIgnoreCase(domandestc.getIdnodo())) {
		    idNodo = "Ente: " + domandestc.getIdentemittente() + ", Sportello: " + domandestc.getIdsportellomittente();
		}
		key = key + "|" + idNodo + "|" + domandestc.getIdentemittente() + "|" + domandestc.getIdsportellomittente();
		domandePervenute.put(key, domandestc.getNumero());
	    }
	}
	helper.setDomandePervenute(domandePervenute);
	if (numIstanzeConErrore > 0 || !listIstanzePervenute.isEmpty()) {
	    helper.setNotificaVisibile(true);
	}
	return helper;
    }

    private String decodeNodo(String idNodo) {

	String defaultNodo = "STC";
	if (idNodo == null) {
	    return defaultNodo;
	}
	List<Verticalizzazioniparametri> verticalizzazioniparametriSTC = verticalizzazioniService
		.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC);
	if (verticalizzazioniparametriSTC == null) {
	    return defaultNodo;
	}
	if (verticalizzazioniparametriSTC.size() == 0) {
	    return defaultNodo;
	}
	for (Verticalizzazioniparametri verticalizzazioniparametri : verticalizzazioniparametriSTC) {
	    String valore = StringUtils.defaultIfEmpty(verticalizzazioniparametri.getValore(), "");
	    if (verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro().equalsIgnoreCase("NLA_IDNODO_AREARISERVATA")) {
		if (valore.equalsIgnoreCase(idNodo)) {
		    return "Area Riservata";
		}
	    }
	    if (verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro().equalsIgnoreCase("NLA_IDNODO_PEOPLE")) {
		if (valore.equalsIgnoreCase(idNodo)) {
		    return "People";
		}
	    }
	    if (verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro()
		    .equalsIgnoreCase("NLA_IDNODO_PROTOCOLLOSIGEPRO")) {
		if (valore.equalsIgnoreCase(idNodo)) {
		    return "Protocollo";
		}
	    }
	    if (verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro().equalsIgnoreCase("NLA_IDNODO_AIDA")) {
		if (valore.equalsIgnoreCase(idNodo)) {
		    return "Aida";
		}
	    }
	}
	return idNodo;
    }

    //    private List<Domandestc> findIstanzeAreaRiservata(String idNodo) {
    //
    //	// recupero la lista di tutte le domande stc importate correttamente per il nodo passato
    //	List<Domandestc> listDomandeStc = domandestcService.findDomandeImportateENonElaborate(idNodo);
    //	//	List<Istanze> risultato = new ArrayList<Istanze>();
    //	//	// per ognuna delle domandestc trovate vado a recuperare l'istanza a cui è associata
    //	//	// e l' aggiungo alla lista delle istanze che ritorna il metodo
    //	//	for (Domandestc domandestc : listDomandeStc) {
    //	//	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
    //	//	    FilterRestriction restriction = new FilterRestriction();
    //	//	    // ricavo lo stato dell'istanze per cui cui si vuole filtrare dalla verticalizzazione
    //	//	    FoArconfigurazioneId id = new FoArconfigurazioneId();
    //	//	    id.setSoftware(ORMHelper.getSoftware());
    //	//	    // cerco l'oggetto FoArconfigurazione per il software passato 
    //	//	    FoArconfigurazione foArconfigurazione = foArconfigurazioneService.findById(id);
    //	//	    // Se non esiste lo cerco per il software TT
    //	//	    if (foArconfigurazione == null) {
    //	//		id.setSoftware(WebConstants.SOFTWARE_TT);
    //	//		foArconfigurazione = foArconfigurazioneService.findById(id);
    //	//	    }
    //	//	    // Se nei due passaggi trovo l'oggetto FoArconfigurazione lo setto come filtro per la ricerca
    //	//	    if (foArconfigurazione != null) {
    //	//		restriction.addFilterField(FilterUtils.equals("chiusura", foArconfigurazione.getStatoInizialeIstanza(), Statiistanza.class));
    //	//	    }
    //	//	    restriction.addFilterField(FilterUtils.equals("flagImport", Boolean.TRUE, "domandestcs", Boolean.class));
    //	//	    restriction.addFilterField(FilterUtils.equals("idNodo", idNodo, "domandestcs", String.class));
    //	//	    // restriction.addFilterField(FilterUtils.equals("id.codice", domandestc.getIstanza().getId().getCodice(), Integer.class));
    //	//	    ft.addRestriction(restriction);
    //	//	    List<Istanze> list = this.findByFilterTable(ft);
    //	//	    if (!list.isEmpty()) {
    //	//		risultato.add(list.get(0));
    //	//	    }
    //	//	}
    //	return listDomandeStc;
    //    }
    @Override
    public void calcolaTempisticaIstanza(Istanze entity) {

	int ggProcedura = 0;
	int ggProroga = 0;
	int giorniProcedimento = 0;
	int ggSospensione = 0;
	int ggCds = 0;
	// estrapolo i dati della procedura
	IstanzeTempistica istanzeTempistica = istanzeTempisticaService.findById(new PkId(entity.getId().getCodice()));
	DynaProperty[] properties = { new DynaProperty("giorni", Integer.class), new DynaProperty("numggcds", Integer.class),
		new DynaProperty("procedura", String.class), new DynaProperty("tipimovimentoChiusura_id_tipomovimento", String.class) };
	DynaClass userDynaClass = new BasicDynaClass("TipiprocedureDC", null, properties);
	DynaBean procedura = istanzeDAO.findDynaBeanById(ORMHelper.getIdcomune(), entity.getProcedura().getId().getCodice(), userDynaClass,
		Tipiprocedure.class);
	Integer giorni = (Integer) procedura.get("giorni");
	Integer numggcds = (Integer) procedura.get("numggcds");
	String tipomovChiusuraStr = (String) procedura.get("tipimovimentoChiusura_id_tipomovimento");
	// estrapolo i dati della procedura
	if (istanzeTempistica != null && istanzeTempistica.getGiorniprocedura() != null) {
	    ggProcedura = istanzeTempistica.getGiorniprocedura();
	} else {
	    ggProcedura = giorni;
	}
	// Se cds allora devo aggiungere i giorni configurati in tipiprocedure
	ggCds = numggcds == null ? 0 : numggcds.intValue();
	if (ggCds > 0) {
	    int cdss = cdsService.countByIstanza(entity.getId().getCodice());
	    if (cdss > 0) {
		ggProcedura = ggCds;
	    }
	}
	FilterRestriction istanze = new FilterRestriction();
	istanze.addFilterField(FilterUtils.equals("istanzaId", entity.getId().getCodice(), "movimentoByFkApertura", Integer.class));
	// trovo tutti i giorni di proroga e li sommo
	ggProroga = movimentiTempisticaService.findDurataProrogaPerIstanza(entity.getId().getCodice());
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	// fr.addFilterField(FilterUtils.equals("evento", MovimentiTempisticaService.TIPO_EVENTO.P.toString(), String.class));
	// ft.addRestriction(fr);
	// ft.addRestriction(istanze);
	Date dataInizioIstanza = this.calcolaDataInizioIstanza(entity);
	boolean isInterruzione = false;
	boolean isSospensione = false;
	// Trovo se ci sono INTERRUZIONI IN CORSO, ossia se ci sono movimenti che interrompono che non sono stati chiusi
	isInterruzione = movimentiTempisticaService.isIstanzaInterrotta(entity.getId().getCodice());
	if (isInterruzione) {
	    // L'istanza è interrotta e non ha importanza la data inizio / fine
	    // i tempi dell'istanza riprendono da quando è stata chiusa l'istanza
	    dataInizioIstanza = null;
	} else {
	    // cerco e calcolo le sospensioni solamente se l'istanza non è attualmente interrotta
	    // trovo la data dell'ultima interruzione e diventa la datainizio istanza
	    Date dataUltimaInterruzione = movimentiTempisticaService.findDataUltimaInterruzione(entity.getId().getCodice());
	    if (dataUltimaInterruzione != null) {
		// for (MovimentiTempistica movimentiTempistica : movimentiTempisticas) {
		// la data inizio istanza è la data termine del movimento che ha chiuso l'ultima interruzione
		dataInizioIstanza = dataUltimaInterruzione;
		//break;
	    }
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("evento", MovimentiTempisticaService.TIPO_EVENTO.S.toString(), String.class));
	    ft.addRestriction(fr);
	    ft.addRestriction(istanze);
	    FilterRestriction dataRestriction = new FilterRestriction();
	    dataRestriction.setAndOrRestriction(AndOrRestriction.OR);
	    dataRestriction.addFilterField(FilterUtils.greater("data", dataInizioIstanza, "movimentoByFkChiusura", Date.class));
	    dataRestriction.addFilterField(FilterUtils.isNull("data", "movimentoByFkChiusura"));
	    ft.addRestriction(dataRestriction);
	    ft.addOrder(FilterUtils.orderAsc("data", "movimentoByFkApertura"));
	    List<MovimentiTempistica> movimentiTempisticas = movimentiTempisticaService.findByFilterTable(ft);
	    Date datadiriferimento = null;
	    Date datatermineprecedente = null;
	    boolean conta = false;
	    for (MovimentiTempistica movimentiTempistica : movimentiTempisticas) {
		if (EntityUtils.getNestedProperty(movimentiTempistica.getMovimentoByFkChiusura(), "id.codice") == null) {
		    // se non c'è movimento di chiusura allora sono l'istanza è sospesa
		    isSospensione = true;
		    break;
		} //else {
		if (!conta) {
		    // la prima volta verifico se la datainizio istanza ( che potrebbe essere quello dell'ultima
		    // interruzione)
		    // sia più grande di quella di apertura della sospensione
		    // in questo caso il conteggio parte dalla data inizio e non dalla data di apertura della
		    // sospensione
		    datadiriferimento = movimentiTempistica.getMovimentoByFkApertura().getData();
		    if (dataInizioIstanza.after(datadiriferimento)) {
			datadiriferimento = dataInizioIstanza;
		    }
		    // memorizzo la data termine della prima sospensione
		    datatermineprecedente = movimentiTempistica.getMovimentoByFkChiusura().getData();
		    // calcolo la durata della prima sospensione
		    ggSospensione = Utilities.calculateDifferenceInDays(movimentiTempistica.getMovimentoByFkChiusura().getData(), datadiriferimento);
		    conta = true;
		}
		// controllo se sono intersecati o c'è il gap
		if (datatermineprecedente.after(movimentiTempistica.getMovimentoByFkApertura().getData())
			|| (datatermineprecedente.compareTo(movimentiTempistica.getMovimentoByFkApertura().getData()) == 0)) {
		    // non c'è il gap
		    // controllo se è interno a quello precedente
		    // se si lo salto
		    if (datatermineprecedente.before(movimentiTempistica.getMovimentoByFkChiusura().getData())) {
			// non è interno
			ggSospensione += Utilities.calculateDifferenceInDays(movimentiTempistica.getMovimentoByFkChiusura().getData(),
				datatermineprecedente);
			datatermineprecedente = movimentiTempistica.getMovimentoByFkChiusura().getData();
		    }
		} else {
		    // c'è il gap
		    ggSospensione += Utilities.calculateDifferenceInDays(movimentiTempistica.getMovimentoByFkChiusura().getData(),
			    movimentiTempistica.getMovimentoByFkApertura().getData());
		    // int gap = Utilities.calculateDifferenceInDays(movimentiTempistica.getMovimentoByFkApertura().getData(), datatermineprecedente);
		    // ggSospensione -= gap;
		    datatermineprecedente = movimentiTempistica.getMovimentoByFkChiusura().getData();
		}
		//}
	    }
	}
	String statoIstanza = "";
	if (isInterruzione) {
	    statoIstanza = MovimentiTempisticaService.TIPO_EVENTO.I.toString();
	} else if (isSospensione) {
	    statoIstanza = MovimentiTempisticaService.TIPO_EVENTO.S.toString();
	} else {
	    //@fabrizioc 
	    //nella colonna STATO di ISTANZE_TEMPISTICA si inserisce solo I per interruzione o S per sospensione
	    //Statiistanza stato = statiistanzaService.findById(entity.getChiusura().getId());
	    //statoIstanza = stato.getStato();
	}
	Date dataFine = null;
	Date dataFineEffettiva = null;
	int ggaggiuntivi = ggProroga + ggSospensione;
	if (!isInterruzione) {
	    if (dataInizioIstanza != null) {
		giorniProcedimento = ggProcedura + ggaggiuntivi;
		Calendar calDataFine = Calendar.getInstance();
		calDataFine.setTime(dataInizioIstanza);
		calDataFine.add(Calendar.DATE, giorniProcedimento);
		dataFine = calDataFine.getTime();
	    }
	}
	if (StringUtils.isNotBlank(tipomovChiusuraStr)) {
	    Movimenti movChiusura = movimentiService.findMovimentiByTipoMovimento(entity.getId().getCodice(), tipomovChiusuraStr);
	    if (movChiusura != null) {
		Calendar calDataFineEffettiva = Calendar.getInstance();
		calDataFineEffettiva.setTime(movChiusura.getData());
		dataFineEffettiva = calDataFineEffettiva.getTime();
	    }
	}
	if (istanzeTempistica == null) {
	    // inserisco
	    istanzeTempistica = new IstanzeTempistica();
	    PkId idTempistica = new PkId();
	    idTempistica.setCodice(entity.getId().getCodice());
	    idTempistica.setIdcomune(entity.getId().getIdcomune());
	    istanzeTempistica.setId(idTempistica);
	    istanzeTempistica.setGiorniprocedura(ggProcedura);
	    istanzeTempistica.setDatainizio(dataInizioIstanza);
	    istanzeTempistica.setDatafine(dataFine);
	    istanzeTempistica.setStato(statoIstanza);
	    istanzeTempistica.setDatafineeffettiva(dataFineEffettiva);
	    istanzeTempistica.setGgaggiuntivi(ggaggiuntivi);
	    istanzeTempisticaService.insert(istanzeTempistica);
	} else {
	    // aggiorno la tempistica
	    istanzeTempistica.setDatainizio(dataInizioIstanza);
	    istanzeTempistica.setDatafine(dataFine);
	    istanzeTempistica.setStato(statoIstanza);
	    if (istanzeTempistica.getGiorniprocedura() == null) {
		istanzeTempistica.setGiorniprocedura(ggProcedura);
	    }
	    istanzeTempistica.setDatafineeffettiva(dataFineEffettiva);
	    istanzeTempistica.setGgaggiuntivi(ggaggiuntivi);
	    istanzeTempisticaService.update(istanzeTempistica);
	}
    }

    @Override
    public MovimentiHelper updateStatoIstanza(Istanze istanza, String nuovoStato) {

	String statoAttuale = "";
	if (istanza.getChiusura() != null && istanza.getChiusura().getId() != null
		&& StringUtils.isNotBlank(istanza.getChiusura().getId().getCodicestato())) {
	    statoAttuale = istanza.getChiusura().getId().getCodicestato();
	}
	if (StringUtils.defaultString(statoAttuale).equalsIgnoreCase(nuovoStato)) {
	    return null;
	}
	StatiistanzaId id = new StatiistanzaId(nuovoStato);
	Statiistanza nuovoStatoObj = statiistanzaService.findById(id);
	if (nuovoStatoObj == null) {
	    throw new RuntimeException(
		    "non è stato trovato lo stato istanza con codice [" + nuovoStato + "] software [" + ORMHelper.getSoftware() + "]");
	}
	boolean flagWarning = nuovoStatoObj.getFlagWarning() == null ? false : nuovoStatoObj.getFlagWarning().booleanValue();
	String testoWarning = StringUtils.defaultString(nuovoStatoObj.getTestoWarning());
	MovimentiHelper mh = null;
	if (nuovoStatoObj.getStaticomportamento().getCodcomportamento().intValue() != 0) {
	    // lo stato comporta la chiusura dell'istanza
	    Movimenti movChiusura = movimentiService.findMovimentoChiusuraIstanza(istanza.getId().getCodice());
	    if (movChiusura == null) {
		boolean isEsitoPositivo = nuovoStatoObj.getStaticomportamento().getCodcomportamento().intValue() == 1 ? true : false;
		// modificato 2019-01-23 BOCCI/TODINI ripristinato il comportamento di far calcolare la data con la data di sistema
		// La modifica era stata introdotta per la chiusura automatica delle istanze tramite JOB schedulato
		// ma ha comportato problemi nella gestione manuale. il Calcolo della data fine effettiva per la chiusura automatica è
		// stato spostato nel manager che chude le istanze
		movChiusura = insertMovimentoChiusura(istanza.getId().getCodice(), isEsitoPositivo);
	    }
	    if (movChiusura != null) {
		IstanzeTempistica it = istanzeTempisticaService.findById(new PkId(istanza.getId().getCodice()));
		if (it != null) {
		    if (it.getDatafineeffettiva() == null) {
			it.setDatafineeffettiva(movChiusura.getData());
			istanzeTempisticaService.update(it);
		    }
		}
	    }
	    mh = movimentiService.findCaratteristicheMovimento(movChiusura);
	    if (mh.isRilascioAutorizzazione()) {
		return mh;
	    }
	    // SI E' DECISO DI NON IMPOSTARE L'ESITO IN QUANTO LA SCELTA DELLA CHIUSURA POSITIVIA O NEGATIVA LA FA L'OPERATORE
	    // DOPO AVER SALVATO IL MOVIMENTO DI CHIUSURA
	} else {
	    IstanzeTempistica it = istanzeTempisticaService.findById(new PkId(istanza.getId().getCodice()));
	    if (it != null) {
		if (it.getDatafineeffettiva() != null) {
		    it.setDatafineeffettiva(null);
		    istanzeTempisticaService.update(it);
		}
	    }
	}
	istanzeDAO.updateStatoIstanza(istanza, nuovoStato);
	// lancio evento cambio stato
	eventPublisher.publish(new EventoStatoIstanzaModificato(istanza.getId().getCodice()));
	Statiistanza sprecedente = statiistanzaService.findById(new StatiistanzaId(statoAttuale));
	if (sprecedente.getStaticomportamento().getCodcomportamento().intValue() != nuovoStatoObj.getStaticomportamento().getCodcomportamento()
		.intValue()) {
	    // se c'è differenza tra attuale e vecchio allora va fatto quello sotto allora richiamo calcola data validità
	    this.calcolaDataValidita(istanza.getId().getCodice());
	}
	try {
	    loggaCambioStato(istanza, nuovoStato, statoAttuale, flagWarning, testoWarning);
	    ldpWsClient.setStatoOccupazione(istanza.getId().getCodice(), nuovoStatoObj);
	} catch (Exception e) {
	    log.error("{}", e);
	}
	return null;
    }

    private void loggaCambioStato(Istanze istanza, String nuovoStato, String statoAttuale, boolean flagWarning, String testoWarning) {

	Responsabili caud = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	String descrizioneRichiedente = "";
	if (caud != null) {
	    descrizioneRichiedente = caud.getResponsabile() + "[" + caud.getId().getCodice() + "]";
	}
	String messaggio = "";
	if (StringUtils.isNotBlank(descrizioneRichiedente)) {
	    messaggio = "L'operatore " + descrizioneRichiedente + " ha modificato lo stato della pratica ";
	} else {
	    messaggio = "E' stato modificato lo stato della pratica ";
	}
	messaggio += istanza.getNumeroistanza() + "[" + istanza.getId().getCodice() + "]. ";
	StatiistanzaId nuovoStatoId = new StatiistanzaId(nuovoStato);
	Statiistanza statoNuovo = statiistanzaService.findById(nuovoStatoId);
	String descrizioneNuovo = nuovoStato;
	if (statoNuovo != null) {
	    descrizioneNuovo = statoNuovo.getStato();
	}
	if (StringUtils.isNotBlank(statoAttuale)) {
	    StatiistanzaId attualeId = new StatiistanzaId(statoAttuale);
	    Statiistanza statoOld = statiistanzaService.findById(attualeId);
	    String descrizioneOld = statoAttuale;
	    if (statoOld != null) {
		descrizioneOld = statoOld.getStato();
	    }
	    messaggio += " Il vecchio stato era \"" + descrizioneOld + "\" il nuovo \"" + descrizioneNuovo + "\".";
	} else {
	    messaggio += " Lo stato della pratica è \"" + descrizioneNuovo + "\".";
	}
	LoggerModificheIstanze.log("#MODIFICASTATOISTANZA#" + messaggio);
	Istanzeeventi evt = new Istanzeeventi();
	evt.setFlagLetto(Boolean.TRUE);
	evt.setIstanze(istanza);
	evt.setDescrizione(messaggio);
	evt.setSoftware(istanza.getSoftware());
	Categorieeventibase ceb = new Categorieeventibase();
	ceb.setId(IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI);
	evt.setCategorieeventibase(ceb);
	istanzeeventiService.insert(evt);
	if (flagWarning && StringUtils.isNotBlank(testoWarning)) {
	    istanzeeventiService.insert(testoWarning, IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
	}
    }

    private void checkLoggaCambioOperatore(Istanze oldistanza, Istanze nuovoistanza, boolean flagWarning, String testoWarning) {

	String operatoreAttuale = oldistanza.getResponsabile() != null ? oldistanza.getResponsabile().getResponsabile() : null;
	String nuovoOperatore = nuovoistanza.getResponsabile() != null ? nuovoistanza.getResponsabile().getResponsabile() : null;
	if (StringUtils.isBlank(operatoreAttuale) && StringUtils.isBlank(nuovoOperatore)) {
	    return;
	}
	if (!StringUtils.isBlank(operatoreAttuale) && operatoreAttuale.equals(nuovoOperatore)) {
	    return;
	}
	Responsabili caud = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	String descrizioneRichiedente = "";
	if (caud != null) {
	    descrizioneRichiedente = caud.getResponsabile() + " [" + caud.getId().getCodice() + "]";
	}
	String messaggio = "";
	if (StringUtils.isNotBlank(descrizioneRichiedente)) {
	    messaggio = "L'operatore " + descrizioneRichiedente + " ha modificato l'operatore della pratica ";
	} else {
	    messaggio = "E' stato modificato l'operatore della pratica ";
	}
	messaggio += oldistanza.getNumeroistanza() + " [" + oldistanza.getId().getCodice() + "]. ";
	//operatore nuovo
	String descrizioneNuovo = nuovoOperatore + "";
	String descrizioneOld = operatoreAttuale + "";
	messaggio += " Il vecchio operatore era \"" + descrizioneOld + "\" il nuovo \"" + descrizioneNuovo + "\".";
	LoggerModificheIstanze.log("#MODIFICASTATOISTANZA#" + messaggio);
	Istanzeeventi evt = new Istanzeeventi();
	evt.setFlagLetto(Boolean.TRUE);
	evt.setIstanze(oldistanza);
	evt.setDescrizione(messaggio);
	evt.setSoftware(oldistanza.getSoftware());
	Categorieeventibase ceb = new Categorieeventibase();
	ceb.setId(IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI);
	evt.setCategorieeventibase(ceb);
	istanzeeventiService.insert(evt);
	if (flagWarning && StringUtils.isNotBlank(testoWarning)) {
	    istanzeeventiService.insert(testoWarning, IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, oldistanza);
	}
    }

    // modificato 2019-01-23 BOCCI/TODINI ripristinato il comportamento di far calcolare la data con la data di sistema
    // La modifica era stata introdotta per la chiusura automatica delle istanze tramite JOB schedulato
    // ma ha comportato problemi nella gestione manuale. il Calcolo della data fine effettiva per la chiusura automatica è
    // stato spostato nel manager che chude le istanze
    @Override
    public Movimenti insertMovimentoChiusura(Integer codiceIstanza, boolean isEsitoPositivo) {

	DynaProperty[] properties = new DynaProperty[] { new DynaProperty("procedura_id_codice", Integer.class),
		new DynaProperty("numeroistanza", String.class) };
	DynaClass userDynaClass = new BasicDynaClass("IstanzeDC", null, properties);
	DynaBean istanza = istanzeDAO.findDynaBeanById(ORMHelper.getIdcomune(), codiceIstanza, userDynaClass, Istanze.class);
	Integer codiceProcedura = (Integer) istanza.get("procedura_id_codice");
	String numeroIstanza = (String) istanza.get("numeroistanza");
	properties = new DynaProperty[] { new DynaProperty("tipimovimentoChiusura_id_tipomovimento", String.class),
		new DynaProperty("procedura", String.class) };
	userDynaClass = new BasicDynaClass("TipiprocedureDC", null, properties);
	DynaBean procedura = istanzeDAO.findDynaBeanById(ORMHelper.getIdcomune(), codiceProcedura, userDynaClass, Tipiprocedure.class);
	String tipomovChiusuraStr = (String) procedura.get("tipimovimentoChiusura_id_tipomovimento");
	String proceduraDesc = (String) procedura.get("procedura");
	if (StringUtils.isBlank(tipomovChiusuraStr)) {
	    log.error("insertMovimentoChiusura# La procedura \"" + proceduraDesc + "\" (codice=" + codiceProcedura +
		      ") non ha definito il tipo movimento di chiusura e l'istanza " + numeroIstanza + " non può essere chiusa");
	    throw new InvalidConfigurationException("La procedura \"" + proceduraDesc + "\" (codice=" + codiceProcedura +
						    ") non ha definito il tipo movimento di chiusura e l'istanza " + numeroIstanza +
						    " non può essere chiusa");
	}
	Movimenti movChiusura = movimentiService.findMovimentiByTipoMovimento(codiceIstanza, tipomovChiusuraStr);
	if (movChiusura == null) {
	    PkId idIstanza = new PkId(codiceIstanza);
	    Istanze istanzaD = istanzeDAO.findById(idIstanza);
	    movChiusura = new Movimenti();
	    TipimovimentoId id = new TipimovimentoId(tipomovChiusuraStr);
	    Tipimovimento tm = tipiMovimentoService.findById(id);
	    movChiusura.setIstanza(istanzaD);
	    movChiusura.setTipomovimento(tm);
	    movChiusura.setEsito(isEsitoPositivo);
	    movChiusura.setData(Calendar.getInstance().getTime());
	    Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    if (r != null) {
		movChiusura.setResponsabile(r);
	    }
	    movimentiService.insert(movChiusura);
	}
	return movChiusura;
    }

    @Override
    public void updateMqIstanza(Integer codiceIstanza, BigDecimal metriquadrati) {

	istanzeDAO.updateMqIstanza(codiceIstanza, metriquadrati);
    }

    @Override
    public List<Istanze> findByIstanzePerAttivitaFilter(IstanzePerAttivitaFilter istanzePerAttivitaFilter) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	if (StringUtils.isNotBlank(istanzePerAttivitaFilter.getSoftware())) {
	    String[] software = istanzePerAttivitaFilter.getSoftware().split(",");
	    filterRestriction.addFilterField(FilterUtils.in("software.codice", software, String.class));
	}
	if (StringUtils.isNotBlank(istanzePerAttivitaFilter.getComuni().getCodicecomune())) {
	    filterRestriction.addFilterField(FilterUtils.equals("comune", istanzePerAttivitaFilter.getComuni(), Comuni.class));
	}
	if (istanzePerAttivitaFilter.getAzioni().getAzId() != null) {
	    filterRestriction.addFilterField(FilterUtils.equals("azione", istanzePerAttivitaFilter.getAzioni().getAzId(), Integer.class));
	}
	filterRestriction.addFilterField(FilterUtils.isNotNull("id.codice", "attivita"));
	filterTable.addRestriction(filterRestriction);
	return this.findByFilterTable(filterTable);
    }

    /**
     * copia le proprietà su un oggetto DTO e setta tutti i Set a null
     * 
     * @param entity
     * @return
     */
    private IstanzeListsDTO copyObjectProperties(Istanze entity) {

	IstanzeListsDTO istanzecopia = new IstanzeListsDTO();
	try {
	    BeanUtils.copyProperties(istanzecopia, entity);
	} catch (Exception e) {
	    throw new RuntimeException(e.getMessage());
	}
	entity.setAttivitas(new HashSet<IAttivita>());
	entity.setAutorizzazionis(new HashSet<Autorizzazioni>());
	entity.setAutorizzazionisubentris(new HashSet<AutorizzazioniSubentri>());
	entity.setBatchScadenzarios(new HashSet<BatchScadenzario>());
	entity.setCcIcalcoliDettagliors(new HashSet<CcIcalcoliDettaglior>());
	entity.setCcIcalcoliDettagliots(new HashSet<CcIcalcoliDettagliot>());
	entity.setCcIcalcolis(new HashSet<CcIcalcoli>());
	entity.setCcIcalcoloDcontribattivs(new HashSet<CcIcalcoloDcontribattiv>());
	entity.setCcIcalcoloDcontributos(new HashSet<CcIcalcoloDcontributo>());
	entity.setCcIcalcolotcontributoRiduzs(new HashSet<CcIcalcolotcontributoRiduz>());
	entity.setCcIcalcoloTcontributos(new HashSet<CcIcalcoloTcontributo>());
	entity.setCcIcalcolotots(new HashSet<CcIcalcolotot>());
	entity.setCcItabella1s(new HashSet<CcItabella1>());
	entity.setCcItabella2s(new HashSet<CcItabella2>());
	entity.setCcItabella3s(new HashSet<CcItabella3>());
	entity.setCcItabella4s(new HashSet<CcItabella4>());
	entity.setCdss(new HashSet<Cds>());
	entity.setDocumentiistanzas(new HashSet<Documentiistanza>());
	entity.setGraduatorieds(new HashSet<Graduatoried>());
	entity.setIstanzeaffissionis(new HashSet<Istanzeaffissioni>());
	entity.setIstanzeallegatis(new HashSet<Istanzeallegati>());
	entity.setIstanzearees(new HashSet<Istanzearee>());
	entity.setIstanzecollegates(new HashSet<Istanzecollegate>());
	entity.setIstanzeattivitas(new HashSet<Istanzeattivita>());
	entity.setIstanzecalcolocanoniTs(new HashSet<IstanzecalcolocanoniT>());
	entity.setIstanzedyn2datis(new HashSet<Istanzedyn2dati>());
	entity.setIstanzedyn2modellit(new HashSet<Istanzedyn2modellit>());
	entity.setIstanzeeventis(new HashSet<Istanzeeventi>());
	entity.setIstanzefidejussionis(new HashSet<Istanzefidejussioni>());
	entity.setIstanzefrontoffices(new HashSet<Istanzefrontoffice>());
	entity.setIstanzelavoriTs(new HashSet<IstanzelavoriT>());
	entity.setIstanzemappalis(new HashSet<Istanzemappali>());
	entity.setIstanzemovimentis(new LinkedHashSet<Movimenti>());
	entity.setIstanzeoneriDettaglios(new HashSet<IstanzeoneriDettaglio>());
	entity.setIstanzeoneris(new HashSet<Istanzeoneri>());
	entity.setIstanzepeopleds(new HashSet<Istanzepeopled>());
	entity.setIstanzeprocedimentis(new HashSet<Istanzeprocedimenti>());
	entity.setIstanzerichiedentis(new HashSet<Istanzerichiedenti>());
	entity.setIstanzeruolis(new HashSet<Istanzeruoli>());
	entity.setIstanzesForFkIstanzafiglia(new HashSet<Istanzereplicate>());
	entity.setIstanzesForFkIstanzapadre(new HashSet<Istanzereplicate>());
	entity.setIstanzestradarios(new HashSet<Istanzestradario>());
	entity.setOIcalcolocontribrRiduzs(new HashSet<OIcalcolocontribrRiduz>());
	entity.setOIcalcolocontribrs(new HashSet<OIcalcolocontribr>());
	entity.setOIcalcolocontribtBtos(new HashSet<OIcalcolocontribtBto>());
	entity.setOIcalcolocontribts(new HashSet<OIcalcolocontribt>());
	entity.setOIcalcoloDettagliors(new HashSet<OIcalcoloDettaglior>());
	entity.setOIcalcoloDettagliots(new HashSet<OIcalcoloDettagliot>());
	entity.setOIcalcolotots(new HashSet<OIcalcolotot>());
	entity.setOrariaperturatestatas(new HashSet<Orariaperturatestata>());
	entity.setPermistanzes(new HashSet<Permistanze>());
	entity.setRegistrazionis(new HashSet<Registrazioni>());
	entity.setSorteggidettaglios(new HashSet<Sorteggidettaglio>());
	entity.setTipimovimentoDis(new HashSet<TipimovimentoDis>());
	entity.setIstanzeprocures(new HashSet<Istanzeprocure>());
	return istanzecopia;
    }

    @Override
    public PkId newIdFromSequencetable(Istanze entity) {

	return istanzeDAO.newIdFromSequence(entity);
    }

    @Override
    public int countByFilter(IstanzeFilter filter) {

	FilterTable filterTable = istanzeFilterToFilterTable(filter);
	int count = istanzeDAO.countRecord(filterTable);
	return count;
    }

    @Override
    public List<Istanze> findByFilter(IstanzeFilter filter, Integer firstResult, Integer maxResult) {

	FilterTable filterTable = istanzeFilterToFilterTable(filter);
	List<Istanze> list = this.findByFilterTable(filterTable, firstResult, maxResult);
	return list;
    }

    @Override
    public void primaElaborazione(Istanze istanza) {

	log.debug("primaElaborazione: per l'istanza {}", istanza.getId());
	boolean daEseguire = false;
	//1. se ci sono movimenti nella tabella tipimovimentoDis eseguo l'elaborazione
	List<TipimovimentoDis> tipimovimentiDisabilitati = tipimovimentoDisService.findByIstanza(istanza);
	if (tipimovimentiDisabilitati.size() > 0) {
	    log.debug("primaElaborazione: trovati {} movimenti disabilitati", tipimovimentiDisabilitati.size());
	    daEseguire = true;
	}
	if (daEseguire == false) {
	    //2. se non sono presenti movimenti nella tabella movimenti_contromovimenti eseguo l'elaborazione
	    List<Movimenti> movimentis = movimentiService.findDaEseguireByIstanza(istanza);
	    if (movimentis.size() == 0) {
		daEseguire = true;
		Tipimovimento movAvvio = istanza.getTipoMovimentoAvvio();
		if (movAvvio != null) {
		    // 2015-09-14 LA PRIMA ELABORAZIONE LA FA SOLAMENTE SE IL MOVIMENTO DI AVVIO HA UN CONTROMOVIMENTO ALTRIMENTI NON 
		    // C'E' NULLA DA ELABORARE
		    List<Tipicontromovimento> contros = tipicontromovimentoService.findByTipimovimento(movAvvio.getId().getTipomovimento(), 0, 2);
		    if (contros.isEmpty()) {
			daEseguire = false;
		    }
		}
		if (daEseguire) {
		    List<Movimenti> movimentieseguitis = movimentiService.findEseguitiByIstanza(istanza);
		    for (Movimenti movimento : movimentieseguitis) {
			if (movimento.getMovimentiContromovimentisForFkPadre().size() > 0) {
			    daEseguire = false;
			    break;
			}
			List<MovimentiContromovimenti> mcs = movimentiContromovimentiService.findByMovimentoByFkFiglio(movimento);
			if (mcs.size() > 0) {
			    daEseguire = false;
			    break;
			}
		    }
		}
	    }
	}
	if (daEseguire) {
	    log.debug("primaElaborazione: eseguo la prima elaborazione ciclo i movimentiDisabilitati");
	    try {
		/**
		 * DISABILITO LA VALIDAZIONE BUSINESS PER EVITARE ERRORI DEL TIPO "Errori di validazione. Impossibile
		 * inserire o modificare un movimento con data di presentazione antecedente la data di chiusura
		 * dell'istanza"
		 */
		ServiceValidationRules serviceValidationRules = (ServiceValidationRules) SigeproBusinessRules
			.getClassRules(ServiceValidationRules.class);
		serviceValidationRules.setCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name(), false);
		SigeproBusinessRules.setClassRules(ServiceValidationRules.class, serviceValidationRules);
		for (TipimovimentoDis tipimovimentoDis : tipimovimentiDisabilitati) {
		    // per ogni movimento disabilitato inserisco una scadenza
		    Movimenti scadenza = new Movimenti();
		    scadenza.setIstanza(istanza);
		    scadenza.setAmministrazioni(tipimovimentoDis.getAmministrazioni());
		    scadenza.setEndoprocedimento(tipimovimentoDis.getInventarioprocedimenti());
		    scadenza.setDataScadenza(tipimovimentoDis.getDatascad());
		    scadenza.setTipomovimento(tipimovimentoDis.getTipomovimento());
		    scadenza.setFlagDisabilitato(Boolean.TRUE);
		    scadenza.setFlagCmovObblig(Boolean.FALSE);
		    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		    if (EntityUtils.getNestedProperty(tipimovimentoDis, "amministrazioni.id.codice") != null) {
			FilterRestriction amm = new FilterRestriction();
			amm.addFilterField(FilterUtils.equals("amministrazioniId",
				(Integer) EntityUtils.getNestedProperty(tipimovimentoDis, "amministrazioni.id.codice"), Integer.class));
			ft.addRestriction(amm);
		    }
		    if (EntityUtils.getNestedProperty(tipimovimentoDis, "inventarioprocedimenti.id.codice") != null) {
			FilterRestriction endo = new FilterRestriction();
			endo.addFilterField(FilterUtils.equals("endoprocedimentoId",
				(Integer) EntityUtils.getNestedProperty(tipimovimentoDis, "inventarioprocedimenti.id.codice"), Integer.class));
			ft.addRestriction(endo);
		    }
		    FilterRestriction tipomov = new FilterRestriction();
		    tipomov.addFilterField(FilterUtils.equals("tipomovimentoId",
			    (String) EntityUtils.getNestedProperty(tipimovimentoDis, "tipomovimento.id.tipomovimento"), String.class));
		    ft.addRestriction(tipomov);
		    FilterRestriction dataScad = new FilterRestriction();
		    tipomov.addFilterField(FilterUtils.equals("dataScadenza", tipimovimentoDis.getDatascad(), Date.class));
		    ft.addRestriction(dataScad);
		    List<Movimenti> movInseriti = movimentiService.findByFilterTable(ft);
		    if (movInseriti.size() == 0) {
			log.debug("primaElaborazione: inserisco il movimento disabilitato [{}-{}-{}-{}]",
				new Object[] { EntityUtils.getNestedProperty(tipimovimentoDis, "amministrazioni.id.codice"),
					EntityUtils.getNestedProperty(tipimovimentoDis, "inventarioprocedimenti.id.codice"),
					EntityUtils.getNestedProperty(tipimovimentoDis, "tipomovimento.id.tipomovimento"),
					tipimovimentoDis.getDatascad() });
			movimentiService.insertScadenza(scadenza);
		    } else {
			log.debug("primaElaborazione: esiste già un movimento con le informazioni: [{}-{}-{}-{}]",
				new Object[] { EntityUtils.getNestedProperty(tipimovimentoDis, "amministrazioni.id.codice"),
					EntityUtils.getNestedProperty(tipimovimentoDis, "inventarioprocedimenti.id.codice"),
					EntityUtils.getNestedProperty(tipimovimentoDis, "tipomovimento.id.tipomovimento"),
					tipimovimentoDis.getDatascad() });
		    }
		}
		//elaboro
		log.debug("primaElaborazione: eseguo l'elaborazione");
		this.elabora(istanza.getId().getCodice(), false);
		// al termine dell'elaborazione elimino i record presenti su tipiMovimento_dis
		log.debug("primaElaborazione: elimino i tipimovimentidisabilitati");
		for (TipimovimentoDis tipimovimentoDis : tipimovimentiDisabilitati) {
		    tipimovimentoDisService.delete(tipimovimentoDis);
		}
	    } catch (Exception e) {
		log.error("{}", e);
		FlashMessages.getWarnings().add("Errore nella prima elaborazione della pratica: " + e.getMessage());
	    } finally {
		SigeproBusinessRules.buildDefaultRules();
	    }
	}
	log.debug("primaElaborazione: fine elaborazione");
    }

    @Override
    public List<Istanze> findIstanzePerInserimentoMassivo(IstanzeFilter filter) {

	return istanzeDAO.findIstanzePerInserimentoMassivo(filter);
    }

    @Override
    public List<Istanze> visualizzaRepliche(Istanze istanza) {

	return istanzereplicateService.findIstanzeReplicate(istanza);
    }

    @Override
    public Istanze isReplicata(Istanze istanza) {

	Istanzereplicate istanzereplicate = istanzereplicateService.findSeIstanzaReplicata(istanza);
	if (istanzereplicate != null) {
	    PkId codiceIstanza = new PkId(istanzereplicate.getId().getCodiceistanzapadre());
	    Istanze istanzaPadre = this.findById(codiceIstanza);
	    return istanzaPadre;
	}
	return null;
    }

    @Override
    public Istanze createTemplateFromIstanzaForReplica(Istanze istanza) {

	if (EntityUtils.isNestedPropertyBlank(istanza, "id.codice")) {
	    throw new IllegalArgumentException("L'istanza non è stata passata correttamente");
	}
	log.debug("createTemplateFromIstanzaForReplica: iniziata la copia delle proprietà dell'istanza [{}]", istanza.getId());
	Istanze result = new Istanze();
	result.setAltezza(istanza.getAltezza());
	result.setAree2(istanza.getAree2());
	result.setAttiva(istanza.getAttiva());
	result.setAttivita(istanza.getAttivita());
	// result.setAttivitaOrdine(attivitaOrdine); ricalcolato
	// result.setAzione(azione) ricalcolato
	result.setBase(istanza.getBase());
	// result.setChiusura(chiusura); ricalcolato
	result.setCodicepraticatel(istanza.getCodicepraticatel());
	result.setComune(istanza.getComune());
	// result.setCreatoDaStc(creatoDaStc);
	result.setData(istanza.getData());
	result.setDataprotocollo(istanza.getDataprotocollo());
	result.setDatavalidita(istanza.getDatavalidita());
	result.setDescrsoggetto(istanza.getDescrsoggetto());
	result.setFkidprotocollo(istanza.getFkidprotocollo());
	result.setFlagvia(istanza.getFlagvia());
	result.setGiornochiusura(istanza.getGiornochiusura());
	result.setIdmodello(istanza.getIdmodello());
	result.setImpianto(istanza.getImpianto());
	// result.setIstanzaCollegata(istanza.getIstanzaCollegata());
	result.setIstruttore(istanza.getIstruttore());
	result.setLavori(istanza.getLavori());
	result.setLavoriestesa(istanza.getLavoriestesa());
	result.setMetriquadrati(istanza.getMetriquadrati());
	result.setNomeattivita(istanza.getNomeattivita());
	// result.setNumeroistanza(numeroistanza);
	result.setNumeroprotocollo(istanza.getNumeroprotocollo());
	result.setPassword(istanza.getPassword());
	result.setPosizionearchivio(istanza.getPosizionearchivio());
	// result.setProcedura(procedura)
	result.setProfessionista(istanza.getProfessionista());
	// result.setResponsabileProcedimento(responsabileProcedimento);
	result.setResponsabile(istanza.getResponsabile());
	result.setRichiedente(istanza.getRichiedente());
	result.setSoftware(istanza.getSoftware());
	result.setTipiarchivioistanza(istanza.getTipiarchivioistanza());
	result.setTipisoggetto(istanza.getTipisoggetto());
	result.setTipologiaistanza(istanza.getTipologiaistanza());
	// result.setTipoMovimentoAvvio(tipoMovimentoAvvio);
	result.setTitolarelegale(istanza.getTitolarelegale());
	result.setVariantepr(istanza.getVariantepr());
	result.setRichiedente(istanza.getRichiedente());
	result.setTitolarelegale(istanza.getTitolarelegale());
	Integer codiceistanza = istanza.getId().getCodice();
	// DOCUMENTI ISTANZA
	List<Documentiistanza> documentiistanzas = documentiistanzaService.findByIstanza(codiceistanza);
	for (Documentiistanza sorgente : documentiistanzas) {
	    Documentiistanza destinazione = new Documentiistanza();
	    destinazione.setAlberoprocDocumenticat(sorgente.getAlberoprocDocumenticat());
	    destinazione.setData(sorgente.getData());
	    destinazione.setDocumento(sorgente.getDocumento());
	    destinazione.setNecessario(sorgente.getNecessario());
	    destinazione.setNote(sorgente.getNote());
	    destinazione.setOggetto(copyOggetto(sorgente.getOggetto()));
	    destinazione.setPresente(sorgente.getPresente());
	    destinazione.setStcIdallegato(sorgente.getStcIdallegato());
	    destinazione.setStcIddocumento(sorgente.getStcIddocumento());
	    destinazione.setAlberoprocDocumenticat(sorgente.getAlberoprocDocumenticat());
	    result.getDocumentiistanzas().add(destinazione);
	}
	// ISTANZEAREE
	List<Istanzearee> istanzearees = istanzeareeService.findByIstanza(istanza);
	for (Istanzearee sorgente : istanzearees) {
	    Istanzearee destinazione = new Istanzearee();
	    destinazione.setArea(sorgente.getArea());
	    destinazione.getId().setCodicearea(sorgente.getId().getCodicearea());
	    destinazione.setAutoins(sorgente.getAutoins());
	    destinazione.setPrimario(sorgente.getPrimario());
	    result.getIstanzearees().add(destinazione);
	}
	// ISTANZEATTIVITA
	List<Istanzeattivita> istanzeattivitas = istanzeattivitaService.findByIstanza(istanza, false);
	for (Istanzeattivita sorgente : istanzeattivitas) {
	    Istanzeattivita destinazione = new Istanzeattivita();
	    destinazione.setAttivita(sorgente.getAttivita());
	    destinazione.setMetriq(sorgente.getMetriq());
	    destinazione.setNote(sorgente.getNote());
	    destinazione.setSoftware(sorgente.getSoftware());
	    result.getIstanzeattivitas().add(destinazione);
	}
	// ISTANZEDYN2DATI
	List<Istanzedyn2dati> istanzedyn2datis = istanzedyn2datiService.findByIstanza(istanza.getId());
	for (Istanzedyn2dati sorgente : istanzedyn2datis) {
	    Istanzedyn2dati destinazione = new Istanzedyn2dati();
	    destinazione.getId().setFkD2cId(sorgente.getId().getFkD2cId());
	    destinazione.getId().setIndice(sorgente.getId().getIndice());
	    destinazione.getId().setIndiceMolteplicita(sorgente.getId().getIndiceMolteplicita());
	    destinazione.setValore(sorgente.getValore());
	    destinazione.setValoredecodificato(sorgente.getValoredecodificato());
	    destinazione.setDyn2Campi(sorgente.getDyn2Campi());
	    result.getIstanzedyn2datis().add(destinazione);
	}
	// ISTANZEDYN2MODELLIT
	List<Istanzedyn2modellit> istanzedyn2modellits = istanzedyn2modellitService.findByIstanza(istanza.getId());
	for (Istanzedyn2modellit sorgente : istanzedyn2modellits) {
	    Istanzedyn2modellit destinazione = new Istanzedyn2modellit();
	    destinazione.getId().setFkD2mtId(sorgente.getId().getFkD2mtId());
	    destinazione.setDyn2Modellit(sorgente.getDyn2Modellit());
	    result.getIstanzedyn2modellit().add(destinazione);
	}
	// ISTANZERICHIEDENTI
	List<Istanzerichiedenti> istanzerichiedentis = istanzerichiedentiService.findByIstanza(istanza);
	for (Istanzerichiedenti sorgente : istanzerichiedentis) {
	    Istanzerichiedenti destinazione = new Istanzerichiedenti();
	    destinazione.setAnagrafeCollegata(sorgente.getAnagrafeCollegata());
	    destinazione.setDescrsoggetto(sorgente.getDescrsoggetto());
	    destinazione.setOggettoProcuratore(copyOggetto(sorgente.getOggettoProcuratore()));
	    destinazione.setProcuratore(sorgente.getProcuratore());
	    destinazione.setRichiedente(sorgente.getRichiedente());
	    destinazione.setTiposoggetto(sorgente.getTiposoggetto());
	    result.getIstanzerichiedentis().add(destinazione);
	}
	// ISTANZERUOLI
	List<Istanzeruoli> istanzeruolis = istanzeruoliService.findByIstanza(istanza);
	for (Istanzeruoli sorgente : istanzeruolis) {
	    Istanzeruoli destinazione = new Istanzeruoli();
	    destinazione.getId().setIdruolo(sorgente.getId().getIdruolo());
	    destinazione.setRuolo(sorgente.getRuolo());
	    result.getIstanzeruolis().add(destinazione);
	}
	// AUTORIZZAZIONI
	//	IstanzaAutConcHelper helper = autorizzazioniService.findAutEConcESubByIstanza(istanza);
	//	List<Autorizzazioni> autorizzazionis = helper.getAutorizzazioni();
	//	for (Autorizzazioni sorgente : autorizzazionis) {
	//	    Autorizzazioni destinazione = new Autorizzazioni();
	//	    destinazione.setAnagrafe(sorgente.getAnagrafe());
	//	    destinazione.setAutorizcomune(sorgente.getAutorizcomune());
	//	    destinazione.setAutorizdata(sorgente.getAutorizdata());
	//	    destinazione.setAutorizdataregistr(sorgente.getAutorizdataregistr());
	//	    destinazione.setAutoriznumero(sorgente.getAutoriznumero());
	//	    destinazione.setAutorizresponsabile(sorgente.getAutorizresponsabile());
	//	    destinazione.setDataCessazione(sorgente.getDataCessazione());
	//	    destinazione.setDatascadenza(sorgente.getDatascadenza());
	//	    destinazione.setFlagAttiva(sorgente.getFlagAttiva());
	//	    destinazione.setTipologiaregistro(sorgente.getTipologiaregistro());
	//	    result.getAutorizzazionis().add(destinazione);
	//	}
	// ISTANZESTRADARIO E MAPPALI
	List<Istanzestradario> istanzestradarios = istanzestradarioService.findByIstanza(istanza.getId().getCodice());
	for (Istanzestradario sorgente : istanzestradarios) {
	    Istanzestradario destinazione = new Istanzestradario();
	    destinazione.setCap(sorgente.getCap());
	    destinazione.setCircoscrizione(sorgente.getCircoscrizione());
	    destinazione.setCivico(sorgente.getCivico());
	    destinazione.setCodicecivico(sorgente.getCodicecivico());
	    destinazione.setEsponente(sorgente.getEsponente());
	    destinazione.setEsponenteinterno(sorgente.getEsponenteinterno());
	    destinazione.setFabbricato(sorgente.getFabbricato());
	    destinazione.setFrazione(sorgente.getFrazione());
	    destinazione.setInterno(sorgente.getInterno());
	    destinazione.setKm(sorgente.getKm());
	    destinazione.setNote(sorgente.getNote());
	    destinazione.setPrimario(sorgente.getPrimario());
	    destinazione.setScala(sorgente.getScala());
	    destinazione.setStradario(sorgente.getStradario());
	    destinazione.setStradariocolore(sorgente.getStradariocolore());
	    destinazione.setValido(sorgente.getValido());
	    if (sorgente.getIstanzemappalis().size() > 0) {
		Set<Istanzemappali> istanzemappalis = sorgente.getIstanzemappalis();
		for (Istanzemappali istanzemappali : istanzemappalis) {
		    Istanzemappali mappale = new Istanzemappali();
		    mappale.setCatasto(istanzemappali.getCatasto());
		    mappale.setFoglio(istanzemappali.getFoglio());
		    mappale.setParticella(istanzemappali.getParticella());
		    mappale.setPrimario(istanzemappali.getPrimario());
		    mappale.setSezione(istanzemappali.getSezione());
		    mappale.setSub(istanzemappali.getSub());
		    mappale.setUnitaimmob(istanzemappali.getUnitaimmob());
		    destinazione.getIstanzemappalis().add(mappale);
		}
	    }
	    result.getIstanzestradarios().add(destinazione);
	}
	log.debug("createTemplateFromIstanzaForReplica: terminata la copia delle proprietà dell'istanza [{}]", istanza.getId());
	return result;
    }

    private Oggetti copyOggetto(Oggetti oggetto) {

	if (oggetto == null) {
	    return null;
	}
	if (EntityUtils.isNestedPropertyBlank(oggetto, "id.codice")) {
	    return null;
	}
	Integer codiceOggetto = oggetto.getId().getCodice();
	Oggetti oggettoDaRecuperare = oggettiService.findById(new PkId(codiceOggetto));
	if (oggettoDaRecuperare != null) {
	    Oggetti result = new Oggetti();
	    result.setDimensioneFile(oggettoDaRecuperare.getDimensioneFile());
	    result.setNomefile(oggettoDaRecuperare.getNomefile());
	    result.setOggetto(oggettoDaRecuperare.getOggetto());
	    result.setPercorso(oggettoDaRecuperare.getPercorso());
	    return result;
	}
	return null;
    }

    @Override
    public List<Integer> findTuttiCodiciIstanzaPerSoftwareAndIntervento(String pSoftware, List<Integer> codiceIntervento) {

	return istanzeDAO.findTuttiCodiciIstanzaPerSoftwareAndIntervento(pSoftware, codiceIntervento);
    }

    @Override
    public List<Istanze> findByAnagrafeRichiedente(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafeRichiedente: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "richiedente", Integer.class));
	filterTable.addRestriction(fr);
	return istanzeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<Istanze> findByAnagrafeProfessionista(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafeProfessionista: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "professionista", Integer.class));
	filterTable.addRestriction(fr);
	return istanzeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<Istanze> findByAnagrafeTitolarelegale(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafeTitolarelegale: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "titolarelegale", Integer.class));
	filterTable.addRestriction(fr);
	return istanzeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<IstanzeListHelper> findIstanzeListHelperByFilter(IstanzeFilter filter, Integer firstResult, Integer maxResult) {

	return istanzeDAO.findIstanzeListHelperByFilter(filter, firstResult, maxResult);
    }

    @Override
    public List<DettaglioRigaIstanze> findIstanzeListHelperByFilterMass(IstanzeFilter filter, HelperTypeEnum type, Integer firstResult,
	    Integer maxResult) {

	return istanzeDAO.findIstanzeListHelperByFilterMass(filter, type, firstResult, maxResult);
    }

    @Override
    public int countIstanzeListHelperByFilter(IstanzeFilter filter) {

	return istanzeDAO.countIstanzeListHelperByFilter(filter);
    }

    @Override
    public List<Istanze> findByAlberoproc(Integer codiceAlberoproc, Integer firstResult, Integer maxResult) {

	if (codiceAlberoproc == null) {
	    throw new IllegalArgumentException("findByAlberoproc: il parametro codiceAlberoproc e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("alberoprocId", codiceAlberoproc, Integer.class));
	filterTable.addRestriction(fr);
	return istanzeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public int countByAlberoproc(Integer codiceAlberoproc) {

	if (codiceAlberoproc == null) {
	    throw new IllegalArgumentException("findByAlberoproc: il parametro codiceAlberoproc e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("alberoprocId", codiceAlberoproc, Integer.class));
	filterTable.addRestriction(fr);
	return istanzeDAO.countRecord(filterTable);
    }

    @Override
    public void updateDataInizioIstanzaPerProcedura(Tipiprocedure tipiprocedure, Integer limiteIstanzeAggiornabiliPerCiclo) {

	log.debug("Inizio procedura di aggiornamento data inizio ");
	// Ricavo il numero totale di pratiche
	int numeroIstanzeTotali = this.countByProcedure(tipiprocedure);
	log.debug("Numero pratiche trovate da aggiornare: {} ", numeroIstanzeTotali);
	// variabile che tiene conto del numero di istanze aggiornate
	int firstResult = 0;
	// Calcolo il numero di cicli che devo fare secondo ala logica:
	//1- numeroIstanzeTotali modulo (limiteIstanzeAggiornabiliPerCiclo) uguale a zero allora la divisione tra i due numeri mi da un risultato perfetto che sarnno 
	// il numero dei cicli esatto
	//2- numeroIstanzeTotali modulo (limiteIstanzeAggiornabiliPerCiclo) diverso da zero il numero dei cicli minimi da fare sarà dato dal risultato intero 
	//della divisione +1
	int numeroPaginazione = ((numeroIstanzeTotali % limiteIstanzeAggiornabiliPerCiclo == 0)
		? (numeroIstanzeTotali / limiteIstanzeAggiornabiliPerCiclo)
		: (numeroIstanzeTotali / limiteIstanzeAggiornabiliPerCiclo) + 1);
	log.debug("Numero pagine: {} ", numeroPaginazione);
	// Fino a quando il numero di cicli non è zero continuo ad aggiornare
	int numeropratiche = 0;
	while (numeroPaginazione > 0) {
	    // Recupero le istanze, al primo ciclo firstResult=0.
	    List<Istanze> istanzes = this.findByProcedure(tipiprocedure, firstResult, limiteIstanzeAggiornabiliPerCiclo);
	    // Per ogni istanza invoco il metodo che aggiorna la data di validità
	    for (Istanze istanze : istanzes) {
		//calcolaTempisticaIstanza(istanze);
		Date dateInizio = calcolaDataInizioIstanza(istanze);
		if (dateInizio != null) {
		    istanze.setData(dateInizio);
		    istanzeDAO.update(istanze);
		}
		istanzeDAO.flush();
		this.clear();
		numeropratiche++;
		//	log.debug("Aggiornata istanza codice istanza: {}, numero istanza: {} ", istanze.getId().getCodice(), istanze.getNumeroistanza());
	    }
	    //decremento la variabile che tiene in memoria il numero di cicli da effettuare
	    log.debug("Fine aggiornamento pagina numero pagine: {} ", numeroPaginazione);
	    numeroPaginazione--;
	    //aggiorno il valore che mi indica da quale record comiciare al quety che recupera le istanze.
	    //log.debug("Aggiornate numero istanze: {}", firstResult);
	    firstResult = firstResult + limiteIstanzeAggiornabiliPerCiclo;
	}
	log.debug("Fine procedura di aggiornamento data inzio ");
	log.debug("Numero pratiche aggiornate : {} ", numeropratiche);
    }

    @Override
    public void updateDataValiditaIstanzaPerProcedura(Tipiprocedure tipiprocedure, Integer limiteIstanzeAggiornabiliPerCiclo) {

	log.debug("Inizio procedura di aggiornamento data validità ");
	// Ricavo il numero totale di pratiche
	int numeroIstanzeTotali = this.countByProcedure(tipiprocedure);
	log.debug("Numero pratiche trovate da aggiornare: {} ", numeroIstanzeTotali);
	// variabile che tiene conto del numero di istanze aggiornate
	int firstResult = 0;
	// Calcolo il numero di cicli che devo fare secondo ala logica:
	//1- numeroIstanzeTotali modulo (limiteIstanzeAggiornabiliPerCiclo) uguale a zero allora la divisione tra i due numeri mi da un risultato perfetto che sarnno 
	// il numero dei cicli esatto
	//2- numeroIstanzeTotali modulo (limiteIstanzeAggiornabiliPerCiclo) diverso da zero il numero dei cicli minimi da fare sarà dato dal risultato intero 
	//della divisione +1
	int numeroPaginazione = ((numeroIstanzeTotali % limiteIstanzeAggiornabiliPerCiclo == 0)
		? (numeroIstanzeTotali / limiteIstanzeAggiornabiliPerCiclo)
		: (numeroIstanzeTotali / limiteIstanzeAggiornabiliPerCiclo) + 1);
	log.debug("Numero pagine: {} ", numeroPaginazione);
	// Fino a quando il numero di cicli non è zero continuo ad aggiornare
	int numeropratiche = 0;
	while (numeroPaginazione > 0) {
	    // Recupero le istanze, al primo ciclo firstResult=0.
	    List<Istanze> istanzes = this.findByProcedure(tipiprocedure, firstResult, limiteIstanzeAggiornabiliPerCiclo);
	    // Per ogni istanza invoco il metodo che aggiorna la data di validità
	    for (Istanze istanze : istanzes) {
		//calcolaTempisticaIstanza(istanze);
		calcolaDataValidita(istanze.getId().getCodice());
		istanzeDAO.flush();
		this.clear();
		numeropratiche++;
		//	log.debug("Aggiornata istanza codice istanza: {}, numero istanza: {} ", istanze.getId().getCodice(), istanze.getNumeroistanza());
	    }
	    //decremento la variabile che tiene in memoria il numero di cicli da effettuare
	    log.debug("Fine aggiornamento pagina numero pagine: {} ", numeroPaginazione);
	    numeroPaginazione--;
	    //aggiorno il valore che mi indica da quale record comiciare al quety che recupera le istanze.
	    //log.debug("Aggiornate numero istanze: {}", firstResult);
	    firstResult = firstResult + limiteIstanzeAggiornabiliPerCiclo;
	}
	log.debug("Fine procedura di aggiornamento data validità ");
	log.debug("Numero pratiche aggiornate : {} ", numeropratiche);
    }

    @Override
    public int countByProcedure(Tipiprocedure tipiprocedure) {

	FilterTable ft = getFilterByProcedura(tipiprocedure);
	return istanzeDAO.countRecord(ft);
    }

    public List<Istanze> findByProcedure(Tipiprocedure tipiprocedure, Integer firstResult, Integer maxResult) {

	FilterTable ft = getFilterByProcedura(tipiprocedure);
	return istanzeDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    private FilterTable getFilterByProcedura(Tipiprocedure tipiprocedure) {

	FilterTable ft = null;
	if (tipiprocedure.getSoftware().getCodice().equals(WebConstants.SOFTWARE_TT)) {
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	} else {
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	}
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", tipiprocedure.getId().getCodice(), "procedura", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("id.codice"));
	return ft;
    }

    /*
    @Override
    public void updateAttivitaOrdine(String ordineIstanze, String codiceIstanze) {
    
    if (StringUtils.isNotBlank(ordineIstanze) && StringUtils.isNotBlank(codiceIstanze)) {
        //String[] ordini = ordineIstanze.split(",");
        String[] ordini = StringUtils.splitPreserveAllTokens(ordineIstanze, ',');
        String[] istanze = codiceIstanze.split(",");
        Istanze istanzeTemp = null;
        Istanze primaIstanza = null;
        Integer codiceAttivitaInElaborazione = null;
        for (int i = 0; i < istanze.length; i++) {
    	String codiceIstanza = istanze[i];
    	istanzeTemp = istanzeDAO.findById(new PkId(Integer.parseInt(codiceIstanza)));
    	// Mi serve per parire in quale attivita stiamo riordinando le istanze
    	if (codiceAttivitaInElaborazione == null) {
    	    codiceAttivitaInElaborazione = istanzeTemp.getAttivita().getId().getCodice();
    	}
    	if (istanzeTemp.getDatavalidita() != null) {
    	    // recupero la prima istanza e la utilizzo per ricalcolare tutti gli snapshot a partire dalla medesima.
    	    // L'ultima istanza sarà l'ultima con data validità diversa da NULL.
    	    primaIstanza = istanzeTemp;
    	}
    	String ordine = "0";
    	if (StringUtils.isNotBlank(ordini[i])) {
    	    ordine = ordini[i];
    	}
    	istanzeTemp.setAttivitaOrdine(Integer.parseInt(ordine));
    	istanzeDAO.update(istanzeTemp);
        }
        istanzeDAO.flush();
        istanzeDAO.clear();
        /////////////////////////////////////////////////////////////////////////////////////
        //  CALCOLO DATA FINE e DATA INIZIO
        ///////////////////////////////////////////////////////////////////////////////////////
        // devo ricalcolare sempre perchè, mentre ala data inizio è data sempre dalla prima, la data fine è data dalla prima partendo dal basso
        // che ha nei campi dinamici il campo che determina la data fine (quindi non è detto sia l'ultimo in senso assoluto)
        if (codiceAttivitaInElaborazione != null) {
    	log.debug(
    		"updateUpOrdine# Calcolo la data inizio e data fine dell'attività: {} dopo riordino delle posizioni delle istanze associate all'attività. ",
    		codiceAttivitaInElaborazione);
    	iAttivitaService.updateDataInizioEFineAttivita(codiceAttivitaInElaborazione);
        } else {
    	log.debug("updateAttivitaOrdine# Codice attività NULL impossibile il ricalcolo delle date di inizio e fine attività");
        }
        if (istanze.length > 0) {
    	IAttivita iattivita = iAttivitaService.findByIstanze(Integer.parseInt(istanze[0]));
    	iAttivitaService.updateSettaUltimaIstanza(iattivita);
    	if (primaIstanza != null) {
    	    iAttivitaService.updateAndAggiornaSnapshot(iattivita, primaIstanza.getDatavalidita(), primaIstanza.getId().getCodice());
    	}
        }
    }
    }
    */
    @Override
    public List<Istanze> findProfessionistaOrRichiedenteOrTitLegaleStorico(Anagrafestorico anagrafeStorico) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.equals("id.codice", anagrafeStorico.getId().getCodice(), "richiedentestorico", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", anagrafeStorico.getId().getCodice(), "titolarelegalestorico", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", anagrafeStorico.getId().getCodice(), "professionistastorico", Integer.class));
	ft.addRestriction(fr);
	return istanzeDAO.findByFilterTable(ft);
    }

    @Override
    public void updateCopiaSchedeIstanza(Istanze istanzaSorgente, Istanze istanzaDestinatario) {

	log.debug("Copio i modelli dinamici dall'istanza {}[{}] all'istanza {}[{}]", new Object[] { istanzaSorgente.getNumeroistanza(),
		istanzaSorgente.getId().getCodice(), istanzaDestinatario.getNumeroistanza(), istanzaDestinatario.getId().getCodice() });
	istanzedyn2modellitService.updateCopiaDyn2ModelliIstanza(istanzaSorgente, istanzaDestinatario);
	log.debug("Fine copia modelli dinamici dall'istanza {}[{}] all'istanza {}[{}]", new Object[] { istanzaSorgente.getNumeroistanza(),
		istanzaSorgente.getId().getCodice(), istanzaDestinatario.getNumeroistanza(), istanzaDestinatario.getId().getCodice() });
	log.debug("Copio i dati dinamici dall'istanza {}[{}] all'istanza {}[{}]", new Object[] { istanzaSorgente.getNumeroistanza(),
		istanzaSorgente.getId().getCodice(), istanzaDestinatario.getNumeroistanza(), istanzaDestinatario.getId().getCodice() });
	istanzedyn2datiService.updateCopiaDyn2DatiIstanza(istanzaSorgente, istanzaDestinatario);
	log.debug("Fine copia dati dinamici dall'istanza {}[{}] all'istanza {}[{}]", new Object[] { istanzaSorgente.getNumeroistanza(),
		istanzaSorgente.getId().getCodice(), istanzaDestinatario.getNumeroistanza(), istanzaDestinatario.getId().getCodice() });
	log.debug("Eseguo validazione delle schede dinamiche dell'istanza destinataria {}[{}]",
		new Object[] { istanzaDestinatario.getNumeroistanza(), istanzaDestinatario.getId().getCodice() });
	try {
	    String[] risultato = dyn2ModellitService.eseguiScriptSchedeIstanza(istanzaDestinatario);
	    if (!ArrayUtils.isEmpty(risultato)) {
		log.debug(
			"Si sono verificati errori nella validazione degli script delle schede dinamiche, verificare all'interno degli eventi dell'istanza {}[{}] ",
			new Object[] { istanzaDestinatario.getNumeroistanza(), istanzaDestinatario.getId().getCodice() });
		StringBuffer descrizioneEvento = new StringBuffer();
		for (int i = 0; i < risultato.length - 1; i++) {
		    descrizioneEvento.append(risultato[i] + ",\n");
		}
		descrizioneEvento.append(risultato[risultato.length - 1]);
		istanzeeventiService.insert(descrizioneEvento.toString(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanzaDestinatario);
		// Riporto il flash message
		FlashMessages.getWarnings().add(
			"Impossibile validare gli script delle schede dinamiche, controllare negli eventi dell'istanza gli errori che si sono verificati");
	    }
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Errore nell'esecuzione del WS eseguiScriptSchedeIstanza causato da," + e.getMessage());
	    log.error("Errore nell'esecuzione del WS eseguiScriptSchedeIstanza causato da {}", e.getMessage());
	}
    }

    @Override
    public List<Istanze> findByAttivitaAndBeforeDataValiditaIstanza(Integer codiceAttivita, Date dataValidita, Integer ordine) {

	return istanzeDAO.findByAttivitaAndBeforeDataValiditaIstanza(codiceAttivita, dataValidita, ordine);
    }

    @Override
    public List<Istanze> findByAttivitaAndBeforeDataValiditaIstanza(Integer codiceAttivita, Date dataValidita, boolean includiDataValiditaNull) {

	return istanzeDAO.findByAttivitaAndBeforeDataValiditaIstanza(codiceAttivita, dataValidita, includiDataValiditaNull);
    }

    @Override
    public boolean findIfIsAttivitaAttivaFromDataValuditaAndAttivita(Date dataValidita, Integer codiceAttivita) {

	return istanzeDAO.findByAttivitaAndBeforeDataValiditaIstanza(codiceAttivita, dataValidita);
    }

    @Override
    public boolean findIfIsAttivitaAttivaFromIstanze(List<Istanze> istanzes) {

	log.debug("Inizio calcolo del valore boolean attiva per un attività.......");
	int piu = 0;
	int meno = 0;
	boolean attiva = false;
	// int contatore = 0;
	// Integer codiceAttivita = null;
	for (Istanze istanza : istanzes) {
	    // Al primo passo recupero il codice attivita, tutte le altre iustanze dovranno avere questo
	    // codice attività altrimenti rilancio un eccezione, il metodo è stato usato in modo non corretto.
	    // if (contatore == 0) {
	    //	codiceAttivita = istanza.getAttivita().getId().getCodice();
	    //		log.debug("Il calcolo viene fatto per il codice attivita {}", istanza.getAttivita());
	    //	    } else {
	    //		log.error(
	    //			"Impossibile calcolare il valore del campo attivo, sono state passate istanze non apparteneti alla stessa attività.(Codice istanza errato : {})",
	    //			+istanza.getId().getCodice());
	    //		throw new RuntimeException(
	    //			"Attenzione: Impossibile calcolare il valore del campo attivo,sono state passate istanze non apparteneti alla stessa attività. (Codice istanza errato :"
	    //				+ istanza.getId().getCodice() + ")");
	    //	    }
	    if (!istanza.getChiusura().getStaticomportamento().getCodcomportamento().equals(-1)) {
		// le istanze chiuse non devono partecipare
		String azione = StringUtils.defaultIfEmpty(istanza.getAzione(), "=");
		if (azione.equalsIgnoreCase("+")) {
		    piu++;
		} else if (azione.equalsIgnoreCase("-")) {
		    meno++;
		}
	    }
	}
	attiva = piu > meno ? true : false;
	log.debug("Fine calcolo del valore boolean attiva per un attività.......");
	return attiva;
    }

    @Override
    public List<Istanze> findByAttivita(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "attivita", Integer.class));
	ft.addRestriction(fr);
	return this.findByFilterTable(ft);
    }

    @Override
    public List<Istanze> findByAttivitaAndIntervalloDataValidita(Integer codiceattivita, Date fromDate, Date toDate) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceattivita, "attivita", Integer.class));
	if (fromDate != null) {
	    fr.addFilterField(FilterUtils.greater("datavalidita", fromDate, Date.class));
	}
	fr.addFilterField(FilterUtils.smallerEqual("datavalidita", toDate, Date.class));
	ft.addOrder(FilterUtils.orderAsc("datavalidita"));
	ft.addOrder(FilterUtils.orderDesc("attivitaOrdine"));
	ft.addRestriction(fr);
	List<Istanze> list = istanzeDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<Istanze> findByAttivitaAndDataValidita(Integer codiceattivita, Date dataValidita) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceattivita, "attivita", Integer.class));
	fr.addFilterField(FilterUtils.equals("datavalidita", dataValidita, Date.class));
	ft.addOrder(FilterUtils.orderAsc("datavalidita"));
	ft.addOrder(FilterUtils.orderDesc("attivitaOrdine"));
	ft.addRestriction(fr);
	List<Istanze> list = istanzeDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public Istanze findIstanzaUltimaAttivitaAllaData(Integer codiceAttivita, Date dataValidita) {

	return istanzeDAO.findIstanzaUltimaAttivitaAllaData(codiceAttivita, dataValidita);
    }

    @Override
    public int findOrdineAttivitaMaxByData(IAttivita attivita, Date datavalidita) {

	return istanzeDAO.findOrdineAttivitaMaxByData(attivita, datavalidita);
    }

    @Override
    public void updateCalcoloIattivitaOrdine(Istanze istanza, IAttivita attivita) {

	List<IstanzeDTO> list = this.findAttivitaOrdineByAttivitaAndDatavalidita(attivita.getId().getCodice(), istanza.getDatavalidita());
	int ordine = 1;
	for (IstanzeDTO istanzeDTO : list) {
	    this.updateOrdineAttivita(istanzeDTO.getId().getCodice(), ordine);
	    ordine++;
	}
	//	// Recupero il campo ordine massimo tra le istanze che appartengono all'attivita e hanno data 
	//	// uguale a quella passata
	//	int ordineIattivitaMax = this.findOrdineAttivitaMaxByData(attivita, istanza.getDatavalidita());
	//	istanza.setAttivitaOrdine(ordineIattivitaMax + 1);
	//	this.update(istanza);
	//	istanzeDAO.flush();
	//	istanzeDAO.clear();
    }

    @Override
    public void populateCambioInterventoCommand(Istanze istanza, CambioInterventoCommand cambioInterventoCommand) {

	if (cambioInterventoCommand == null) {
	    log.error("populateCambioInterventoCommand# cambioInterventoCommand is null");
	    throw new RuntimeException("cambioInterventoCommand is null");
	}
	if (cambioInterventoCommand.getIntervento() == null) {
	    log.error("populateCambioInterventoCommand# cambioInterventoCommand.getIntervento is null");
	    throw new RuntimeException("Il nuovo intervento non è stato specificato");
	}
	String codiceIntervento = StringUtils.defaultString(cambioInterventoCommand.getIntervento().getCodice()).trim();
	if (StringUtils.isBlank(codiceIntervento)) {
	    log.error("populateCambioInterventoCommand# cambioInterventoCommand.getIntervento is null");
	    throw new RuntimeException("Il nuovo intervento non è stato specificato");
	}
	if (!Utilities.isInteger(codiceIntervento)) {
	    log.error("populateCambioInterventoCommand# cambioInterventoCommand.getIntervento non è stato specificato correttamente {}",
		    codiceIntervento);
	    throw new RuntimeException("Il nuovo intervento non è stato specificato correttamente [" + codiceIntervento + "]");
	}
	cambioInterventoCommand.resetAllProperties();
	Integer codiceAlberoProc = Integer.parseInt(codiceIntervento);
	Alberoproc ap = alberoprocService.findById(new PkId(codiceAlberoProc));
	AlberoprocHelper helper = alberoprocService.findAlberoprocHelper(ap);
	if (helper.getAzione() == null) {
	    log.error(
		    "populateCambioInterventoCommand# AlberoprocHelper.getAzione la nuova voce dell'albero non ha definito un azione per l'intervento {}",
		    codiceIntervento);
	    throw new RuntimeException("La nuova voce di intervento non ha definito una azione [" + codiceIntervento + "]");
	}
	Tipiprocedure procedura = helper.getTipoProcedura();
	if (procedura == null) {
	    if (StringUtils.isNotBlank(istanza.getNatura())) {
		procedura = natureProcedureService.findByNatura(istanza.getNatura());
	    }
	    if (procedura == null) {
		log.error("populateCambioInterventoCommand# AlberoprocHelper.getAzione la nuova voce dell'albero non ha definito una procedura {}",
			codiceIntervento);
		throw new RuntimeException("La nuova voce di intervento non ha definito una procedura [" + codiceIntervento +
					   "], e non è stato possibile recuperarla dalla natura (ISTANZE.NATURA) della pratica. Configurare correttamente l'intervento ");
	    }
	}
	Set<Tipiprocedureavvio> tps = procedura.getTipiProcedureavvios();
	if (tps == null || tps.isEmpty()) {
	    log.error(
		    "populateCambioInterventoCommand# AlberoprocHelper.getProcedura().getTipimovAvvio() la procedura non ha definito un tipomovimento avvio {}",
		    codiceIntervento);
	    throw new RuntimeException("La procedura associata [" + procedura.getProcedura() + ",id:" + procedura.getId() +
				       "] non ha definito un movimento di avvio . Configurare correttamente la procedura ");
	}
	Tipimovimento tm = null;
	for (Tipiprocedureavvio tipiprocedureavvio : tps) {
	    if (BooleanUtils.isTrue(tipiprocedureavvio.getDefaultsn())) {
		tm = tipiprocedureavvio.getTipoMovimento();
		break;
	    }
	    tm = tipiprocedureavvio.getTipoMovimento();
	}
	//////////////////////// INIZIO DOCUMENTI
	cambioInterventoCommand = cambioInterventoManager.popolaCambioInterventoCommandDocumenti(istanza, cambioInterventoCommand);
	//	//////////////////////// FINE DOCUMENTI
	//////////////////////// INIZIO ENDOPROCEDIMENTI
	List<Istanzeprocedimenti> ips = istanzeprocedimentiService.findByIstanze(istanza);
	Set<AlberoprocEndo> apendos = helper.getAlberoprocEndos();
	Set<String> endos = new TreeSet<String>();
	for (Istanzeprocedimenti endo : ips) {
	    endos.add(endo.getId().getCodiceinventario().toString());
	}
	List<Integer> codiciInventarioList = new ArrayList<Integer>(); // SERVE PER IL CALCOLO DELLA NUOVA AZIONE
	for (AlberoprocEndo endo : apendos) {
	    codiciInventarioList.add(endo.getId().getCodiceinventario());
	    endos.add(endo.getId().getCodiceinventario().toString());
	}
	List<CambioInterventoCompareHelper> endoCI = cambioInterventoCommand.getEndos();
	for (String endoStr : endos) {
	    String descrizioneEndo = "";
	    CambioInterventoComparePropertiesHelper attuale = new CambioInterventoComparePropertiesHelper();
	    for (Istanzeprocedimenti endo : ips) {
		String codiceEndo = endo.getId().getCodiceinventario().toString();
		if (codiceEndo.equalsIgnoreCase(endoStr)) {
		    descrizioneEndo = endo.getInventarioprocedimenti().getTransientDescrizioneWithSoftware();
		    attuale.setPresente(true);
		    attuale.setChecked(true);
		    attuale.setReadonly(true);
		    break;
		}
	    }
	    CambioInterventoComparePropertiesHelper nuovo = new CambioInterventoComparePropertiesHelper();
	    for (AlberoprocEndo endo : apendos) {
		String codiceEndo = endo.getId().getCodiceinventario().toString();
		if (codiceEndo.equalsIgnoreCase(endoStr)) {
		    boolean principale = BooleanUtils.isTrue(endo.getFlagPrincipale());
		    boolean proposto = BooleanUtils.isTrue(endo.getFlagRichiesto());
		    descrizioneEndo = endo.getInventarioprocedimento().getTransientDescrizioneWithSoftware();
		    nuovo.setPresente(Boolean.TRUE);
		    nuovo.setChecked(attuale.isChecked());
		    nuovo.setReadonly(attuale.isReadonly());
		    if (principale) {
			descrizioneEndo += " [<i>Principale</i>]";
			nuovo.setPresente(Boolean.TRUE);
			nuovo.setChecked(Boolean.TRUE);
			nuovo.setReadonly(Boolean.TRUE);
		    } else {
			if (proposto) {
			    descrizioneEndo += " [<i>Proposto</i>]";
			    nuovo.setPresente(Boolean.TRUE);
			    nuovo.setChecked(Boolean.TRUE);
			} else {
			    nuovo.setChecked(Boolean.FALSE);
			}
		    }
		    break;
		}
	    }
	    CambioInterventoCompareHelper cich = new CambioInterventoCompareHelper();
	    cich.setCodice(endoStr);
	    cich.setDescrizione(descrizioneEndo);
	    cich.setAttuale(attuale);
	    cich.setNuovo(nuovo);
	    endoCI.add(cich);
	}
	Collections.sort(endoCI);
	cambioInterventoCommand.setEndos(endoCI);
	//////////////////////// FINE ENDOPROCEDIMENTI
	//////////////////////// INIZIO SCHEDE
	List<Istanzedyn2modellit> schedeIs = istanzedyn2modellitService.findByIstanza(new PkId(istanza.getId().getCodice()));
	Set<AlberoprocDyn2modellit> schedeAps = helper.getAlberoprocDyn2modellits();
	Set<Integer> schedes = new TreeSet<Integer>();
	for (Istanzedyn2modellit endo : schedeIs) {
	    schedes.add(endo.getId().getFkD2mtId());
	}
	for (AlberoprocDyn2modellit endo : schedeAps) {
	    schedes.add(endo.getId().getFkD2mtId());
	}
	List<CambioInterventoCompareHelper> schedeCI = cambioInterventoCommand.getSchedes();
	for (Integer codScheda : schedes) {
	    String descrizioneScheda = "";
	    CambioInterventoComparePropertiesHelper attuale = new CambioInterventoComparePropertiesHelper();
	    for (Istanzedyn2modellit s : schedeIs) {
		if (s.getId().getFkD2mtId().equals(codScheda)) {
		    descrizioneScheda = s.getDyn2Modellit().getDescrizioneEstesa();
		    attuale.setPresente(Boolean.TRUE);
		    attuale.setChecked(Boolean.TRUE);
		    attuale.setReadonly(Boolean.TRUE);
		    break;
		}
	    }
	    CambioInterventoComparePropertiesHelper nuovo = new CambioInterventoComparePropertiesHelper();
	    for (AlberoprocDyn2modellit s : schedeAps) {
		if (s.getId().getFkD2mtId().equals(codScheda)) {
		    descrizioneScheda = s.getDyn2Modellit().getDescrizioneEstesa();
		    nuovo.setPresente(Boolean.TRUE);
		    // nuovo.setChecked(attuale.isChecked());
		    nuovo.setChecked(Boolean.TRUE);
		    nuovo.setReadonly(attuale.isReadonly());
		    break;
		}
	    }
	    CambioInterventoCompareHelper cich = new CambioInterventoCompareHelper();
	    cich.setCodice(codScheda.toString());
	    cich.setDescrizione(descrizioneScheda);
	    cich.setAttuale(attuale);
	    cich.setNuovo(nuovo);
	    schedeCI.add(cich);
	}
	Collections.sort(schedeCI);
	cambioInterventoCommand.setSchedes(schedeCI);
	//////////////////////// FINE SCHEDE
	//////////////////////// INIZIO PERMESSI/RUOLI
	List<Istanzeruoli> ruoliIs = istanzeruoliService.findByIstanza(istanza);
	Set<AlberoprocRuoli> ruoliAps = helper.getAlberoprocRuolis();
	Set<Integer> ruolis = new TreeSet<Integer>();
	for (Istanzeruoli ruolo : ruoliIs) {
	    ruolis.add(ruolo.getId().getIdruolo());
	}
	for (AlberoprocRuoli ruolo : ruoliAps) {
	    ruolis.add(ruolo.getId().getFkIdruolo());
	}
	List<CambioInterventoCompareHelper> ruoliCI = cambioInterventoCommand.getRuolis();
	for (Integer codRuolo : ruolis) {
	    String descrizioneRuolo = "";
	    CambioInterventoComparePropertiesHelper attuale = new CambioInterventoComparePropertiesHelper();
	    for (Istanzeruoli s : ruoliIs) {
		if (s.getId().getIdruolo().equals(codRuolo)) {
		    descrizioneRuolo = s.getRuolo().getRuolo();
		    attuale.setPresente(Boolean.TRUE);
		    attuale.setChecked(Boolean.TRUE);
		    attuale.setReadonly(Boolean.TRUE);
		    break;
		}
	    }
	    CambioInterventoComparePropertiesHelper nuovo = new CambioInterventoComparePropertiesHelper();
	    for (AlberoprocRuoli s : ruoliAps) {
		if (s.getId().getFkIdruolo().equals(codRuolo)) {
		    descrizioneRuolo = s.getRuoli().getRuolo();
		    nuovo.setPresente(Boolean.TRUE);
		    nuovo.setChecked(Boolean.TRUE);
		    // nuovo.setChecked(attuale.isChecked());
		    nuovo.setReadonly(attuale.isReadonly());
		    break;
		}
	    }
	    CambioInterventoCompareHelper cich = new CambioInterventoCompareHelper();
	    cich.setCodice(codRuolo.toString());
	    cich.setDescrizione(descrizioneRuolo);
	    cich.setAttuale(attuale);
	    cich.setNuovo(nuovo);
	    ruoliCI.add(cich);
	}
	Collections.sort(ruoliCI);
	cambioInterventoCommand.setRuolis(ruoliCI);
	List<Permistanze> permistanzeIs = permistanzeService.findByIstanza(istanza);
	Set<Integer> permistanzas = new TreeSet<Integer>();
	for (Permistanze permistanza : permistanzeIs) {
	    permistanzas.add(permistanza.getId().getCodiceresponsabile());
	}
	if (helper.getResponsabile() != null) {
	    if (helper.getResponsabile().getId() != null) {
		if (helper.getResponsabile().getId().getCodice() != null) {
		    permistanzas.add(helper.getResponsabile().getId().getCodice());
		}
	    }
	}
	if (helper.getRespistruttoria() != null) {
	    if (helper.getRespistruttoria().getId() != null) {
		if (helper.getRespistruttoria().getId().getCodice() != null) {
		    permistanzas.add(helper.getRespistruttoria().getId().getCodice());
		}
	    }
	}
	//    permistanzas.add(permistanza.getId().getFkD2mtId());
	List<CambioInterventoCompareHelper> permistanzaCI = cambioInterventoCommand.getPermessis();
	for (Integer codpermistanza : permistanzas) {
	    String descrizionepermistanza = "";
	    CambioInterventoComparePropertiesHelper attuale = new CambioInterventoComparePropertiesHelper();
	    for (Permistanze s : permistanzeIs) {
		if (s.getId().getCodiceresponsabile().equals(codpermistanza)) {
		    descrizionepermistanza = s.getResponsabile().getResponsabile();
		    attuale.setPresente(Boolean.TRUE);
		    attuale.setChecked(Boolean.TRUE);
		    attuale.setReadonly(Boolean.TRUE);
		    break;
		}
	    }
	    CambioInterventoComparePropertiesHelper nuovo = new CambioInterventoComparePropertiesHelper();
	    boolean responsabileTrovato = false;
	    if (helper.getResponsabile() != null) {
		if (helper.getResponsabile().getId() != null) {
		    if (helper.getResponsabile().getId().getCodice() != null) {
			if (helper.getResponsabile().getId().getCodice().equals(codpermistanza)) {
			    descrizionepermistanza = helper.getResponsabile().getResponsabile();
			    nuovo.setPresente(Boolean.TRUE);
			    // nuovo.setChecked(attuale.isChecked());
			    nuovo.setChecked(Boolean.TRUE);
			    nuovo.setReadonly(attuale.isReadonly());
			    responsabileTrovato = true;
			}
		    }
		}
	    }
	    if (!responsabileTrovato) {
		if (helper.getRespistruttoria() != null) {
		    if (helper.getRespistruttoria().getId() != null) {
			if (helper.getRespistruttoria().getId().getCodice() != null) {
			    if (helper.getRespistruttoria().getId().getCodice().equals(codpermistanza)) {
				descrizionepermistanza = helper.getRespistruttoria().getResponsabile();
				nuovo.setPresente(Boolean.TRUE);
				// nuovo.setChecked(attuale.isChecked());
				nuovo.setChecked(Boolean.TRUE);
				nuovo.setReadonly(attuale.isReadonly());
			    }
			}
		    }
		}
	    }
	    CambioInterventoCompareHelper cich = new CambioInterventoCompareHelper();
	    cich.setCodice(codpermistanza.toString());
	    cich.setDescrizione(descrizionepermistanza);
	    cich.setAttuale(attuale);
	    cich.setNuovo(nuovo);
	    permistanzaCI.add(cich);
	}
	Collections.sort(permistanzaCI);
	cambioInterventoCommand.setPermessis(permistanzaCI);
	//////////////////////// FINE PERMESSI
	//////////////////////// INIZIO AZIONI
	// Azioni nuovaAzione = alberoprocService.findAzioniDaEndoOAlberoproc(ap, codiciInventarioList);
	// CodiceDescrizioneBean newAzione = new CodiceDescrizioneBean();
	// newAzione.setCodice(nuovaAzione.getAzAzione());
	// newAzione.setDescrizione(nuovaAzione.getDescrizioneTransient());
	// cambioInterventoCommand.setAzioneNew(newAzione);
	CodiceDescrizioneBean oldAzione = new CodiceDescrizioneBean();
	oldAzione.setCodice(istanza.getAzione());
	List<Azioni> azionis = azioniService.findAll(null, null);
	String descrizioneAzione = "";
	for (Azioni azione : azionis) {
	    if (azione.getAzAzione().equalsIgnoreCase(istanza.getAzione())) {
		descrizioneAzione = azione.getDescrizioneTransient();
	    }
	}
	oldAzione.setDescrizione(descrizioneAzione);
	cambioInterventoCommand.setAzioneOld(oldAzione);
	//////////////////////// FINE AZIONI
	////////////////////////INIZIO PROCEDURA
	CodiceDescrizioneBean newProcedura = new CodiceDescrizioneBean();
	newProcedura.setCodice(procedura.getId().getCodice().toString());
	newProcedura.setDescrizione(procedura.getDescrizioneEstesa());
	cambioInterventoCommand.setProceduraNew(newProcedura);
	CodiceDescrizioneBean oldProcedura = new CodiceDescrizioneBean();
	oldProcedura.setCodice(istanza.getProcedura().getId().getCodice().toString());
	oldProcedura.setDescrizione(istanza.getProcedura().getDescrizioneEstesa());
	cambioInterventoCommand.setProceduraOld(oldProcedura);
	//////////////////////// FINE PROCEDURA
	////////////////////////INIZIO PROCEDURA
	CodiceDescrizioneBean newMovAvvio = new CodiceDescrizioneBean();
	newMovAvvio.setCodice(tm.getId().getTipomovimento());
	newMovAvvio.setDescrizione(tm.getDescrizioneEstesa());
	cambioInterventoCommand.setMovAvvioNew(newMovAvvio);
	CodiceDescrizioneBean oldMovAvvio = new CodiceDescrizioneBean();
	oldMovAvvio.setCodice(istanza.getTipoMovimentoAvvio().getId().getTipomovimento());
	oldMovAvvio.setDescrizione(istanza.getTipoMovimentoAvvio().getDescrizioneEstesa());
	cambioInterventoCommand.setMovAvvioOld(oldMovAvvio);
	//////////////////////// FINE PROCEDURA
    }

    @Override
    public void updateCambioInterventoIstanza(Istanze istanza, CambioInterventoCommand cambioInterventoCommand) {

	if (log.isDebugEnabled()) {
	    log.debug("updateCambioInterventoIstanza# entro nel metodo, aggiorno i dati dell'istanza");
	}
	Integer codiceProceduraOld = Integer.parseInt(cambioInterventoCommand.getProceduraOld().getCodice());
	Integer codiceintervento = Integer.parseInt(cambioInterventoCommand.getIntervento().getCodice());
	Integer codiceprocedura = Integer.parseInt(cambioInterventoCommand.getProceduraNew().getCodice());
	//0: METTO IL VECCHIO INTERVENTO TRA I METADATI
	IstanzeMetadati metaDato = IstanzeMetadati
		.fromMetadati(new SpostamentoPraticheMetadato(istanza.getId().getCodice(), istanza.getAlberoproc().getId().getCodice()));
	this.istanzeDAO.saveEntity(metaDato);
	//1: LOGGO NELL'AUDIT QUESTA OPERAZIONE
	Responsabili responsabile = (Responsabili) this.userSecurityService.getCurrentlyAuthenticatedUserDetails();
	AuditSpostamentoPratiche.tracciaSpostamento(responsabile.getResponsabile(), istanza.getAlberoproc().getId().getCodice(), codiceintervento, 1);
	//2: AGGIORNO INTERVENTO, PROCEDURA, MOVIMENTO AVVIO, AZIONE DELL'ISTANZA
	boolean proceduraModificata = false;
	if (codiceProceduraOld.intValue() != codiceprocedura.intValue()) {
	    proceduraModificata = true;
	}
	String movimentoAvvioNew = cambioInterventoCommand.getMovAvvioNew().getCodice();
	String movimentoAvvioOld = cambioInterventoCommand.getMovAvvioOld().getCodice();
	// 3: AGGIORNO GLI ENDOPROCEDIMENTI
	List<CambioInterventoCompareHelper> endos = cambioInterventoCommand.getEndos();
	List<Integer> codiciEndos = new ArrayList<Integer>();
	for (CambioInterventoCompareHelper endo : endos) {
	    Integer codiceEndo = Integer.parseInt(endo.getCodice());
	    boolean aggiungi = false;
	    if (endo.getNuovo().isPresente()) {
		if (endo.getNuovo().isChecked()) {
		    aggiungi = true;
		    if (endo.getAttuale().isPresente()) {
			if (endo.getAttuale().isChecked()) {
			    aggiungi = false;
			}
		    }
		}
	    }
	    if (aggiungi) {
		codiciEndos.add(codiceEndo);
		Inventarioprocedimenti ip = inventarioprocedimentiService.findById(new PkId(codiceEndo));
		Istanzeprocedimenti entity = new Istanzeprocedimenti();
		entity.setInventarioprocedimenti(ip);
		entity.setIstanza(istanza);
		entity.setDataattivazione(Calendar.getInstance().getTime());
		istanzeprocedimentiService.insert(entity);
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug("updateCambioInterventoIstanza# endoprocedimenti aggiornati, aggiorno l'istanza");
	}
	boolean movAvvioChanged = movimentoAvvioNew.equalsIgnoreCase(movimentoAvvioOld) ? false : true;
	istanza = istanzeDAO.findById(new PkId(istanza.getId().getCodice()));
	Movimenti movAvvio = movimentiService.findMovimentoAvvioIstanza(istanza);
	if (movAvvio == null) {
	    throw new BusinessValidationException("L'istanza " + istanza + " non ha il movimento di avvio configurato");
	}
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceintervento));
	AlberoprocHelper hlp = alberoprocService.findAlberoprocHelper(alberoproc);
	if (hlp.getAmministrazioni() != null && hlp.getAmministrazioni().getId() != null && hlp.getAmministrazioni().getId().getCodice() != null) {
	    istanza.setAmministrazioni(hlp.getAmministrazioni());
	}
	if (istanza.getGruppiIstruttori() == null && hlp.getGruppiIstruttori() != null && hlp.getGruppiIstruttori().getId() != null
		&& hlp.getGruppiIstruttori().getId().getCodice() != null) {
	    istanza.setGruppiIstruttori(hlp.getGruppiIstruttori());
	    istanza.setGrpFlagAssegnazioneAut(hlp.getGrpFlagAssegnazioneAut());
	    istanza.setGrpFlagAccettazione(hlp.getGrpFlagAccettazione());
	}
	istanza.setAlberoproc(alberoproc);
	Tipiprocedure procedura = tipiprocedureService.findById(new PkId(codiceprocedura));
	istanza.setProcedura(procedura);
	Azioni azione = alberoprocService.findAzioniDaEndoOAlberoproc(alberoproc, codiciEndos);
	istanza.setAzione(azione.getAzAzione());
	if (movAvvioChanged) {
	    TipimovimentoId id = new TipimovimentoId(movimentoAvvioNew);
	    Tipimovimento tipoMovimentoAvvio = tipiMovimentoService.findById(id);
	    istanza.setTipoMovimentoAvvio(tipoMovimentoAvvio);
	}
	istanzeDAO.update(istanza);
	istanzeDAO.flush();
	if (log.isDebugEnabled()) {
	    log.debug("updateCambioInterventoIstanza# dati dell'istanza aggiornati, aggiorno i documenti");
	}
	// 4: AGGIORNO I DOCUMENTI
	List<CambioInterventoCompareHelper> docs = cambioInterventoCommand.getDocs();
	documentiistanzaService.updateAllineaDocumenti(istanza, docs);
	if (log.isDebugEnabled()) {
	    log.debug("updateCambioInterventoIstanza# Documenti aggiornati, aggiorno i documenti");
	}
	// 5: AGGIORNO LE SCHEDE
	List<CambioInterventoCompareHelper> schedes = cambioInterventoCommand.getSchedes();
	for (CambioInterventoCompareHelper scheda : schedes) {
	    Integer codiceScheda = Integer.parseInt(scheda.getCodice());
	    boolean aggiungi = false;
	    if (scheda.getNuovo().isPresente()) {
		if (scheda.getNuovo().isChecked()) {
		    aggiungi = true;
		    if (scheda.getAttuale().isPresente()) {
			if (scheda.getAttuale().isChecked()) {
			    aggiungi = false;
			}
		    }
		}
	    }
	    if (aggiungi) {
		Dyn2Modellit id2t = dyn2ModellitService.findById(new PkId(codiceScheda));
		Istanzedyn2modellit entity = new Istanzedyn2modellit();
		entity.setDyn2Modellit(id2t);
		entity.setIstanza(istanza);
		istanzedyn2modellitService.insert(entity);
	    }
	}
	//6: SCHEDE DELLA PROCEDURA
	List<TipiprocedureDyn2modellit> schedeProcs = tipiprocedureDyn2modellitService.findByTipoprocedura(procedura.getId().getCodice());
	for (TipiprocedureDyn2modellit tpd2mt : schedeProcs) {
	    Istanzedyn2modellit mod = istanzedyn2modellitService
		    .findById(new Istanzedyn2modellitId(istanza.getId().getCodice(), tpd2mt.getId().getFkD2mtId()));
	    if (mod == null) {
		Dyn2Modellit id2t = dyn2ModellitService.findById(new PkId(tpd2mt.getId().getFkD2mtId()));
		Istanzedyn2modellit entity = new Istanzedyn2modellit();
		entity.setDyn2Modellit(id2t);
		entity.setIstanza(istanza);
		istanzedyn2modellitService.insert(entity);
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug("updateCambioInterventoIstanza# Aggiorno i ruoli");
	}
	//7: AGGIORNO I RUOLI
	List<CambioInterventoCompareHelper> ruolis = cambioInterventoCommand.getRuolis();
	for (CambioInterventoCompareHelper ruolo : ruolis) {
	    Integer codiceRuolo = Integer.parseInt(ruolo.getCodice());
	    boolean aggiungi = false;
	    if (ruolo.getNuovo().isPresente()) {
		if (ruolo.getNuovo().isChecked()) {
		    aggiungi = true;
		    if (ruolo.getAttuale().isPresente()) {
			if (ruolo.getAttuale().isChecked()) {
			    aggiungi = false;
			}
		    }
		}
	    }
	    if (aggiungi) {
		Ruoli r = ruoliService.findById(new PkId(codiceRuolo));
		Istanzeruoli entity = new Istanzeruoli();
		entity.setRuolo(r);
		entity.setIstanze(istanza);
		istanzeruoliService.insert(entity);
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug("updateCambioInterventoIstanza# Aggiorno i permessi");
	}
	//8: AGGIORNO I PERMESSI
	List<CambioInterventoCompareHelper> permessis = cambioInterventoCommand.getPermessis();
	for (CambioInterventoCompareHelper permesso : permessis) {
	    Integer codiceResponsabile = Integer.parseInt(permesso.getCodice());
	    boolean aggiungi = false;
	    if (permesso.getNuovo().isPresente()) {
		if (permesso.getNuovo().isChecked()) {
		    aggiungi = true;
		    if (permesso.getAttuale().isPresente()) {
			if (permesso.getAttuale().isChecked()) {
			    aggiungi = false;
			}
		    }
		}
	    }
	    if (aggiungi) {
		Responsabili r = responsabiliService.findById(new PkId(codiceResponsabile));
		Permistanze entity = new Permistanze();
		entity.setResponsabile(r);
		entity.setIstanze(istanza);
		permistanzeService.insert(entity);
	    }
	}
	if (movAvvioChanged) {
	    if (log.isDebugEnabled()) {
		log.debug("updateCambioInterventoIstanza# Aggiorno con il nuovo movimento di avvio");
	    }
	    // verifico che il movimento di avvio non sia già stato inserito
	    movAvvio.setTipomovimento(istanza.getTipoMovimentoAvvio());
	    movAvvio.setMovimento(null);
	    movimentiService.update(movAvvio);
	}
	if (log.isDebugEnabled()) {
	    log.debug("updateCambioInterventoIstanza# eseguo l'elaborazione dell'istanza");
	}
	if (proceduraModificata) {
	    IstanzeTempistica istTempistica = istanzeTempisticaService.findById(new PkId(istanza.getId().getCodice()));
	    istanzeTempisticaService.delete(istTempistica);
	}
	this.elabora(istanza.getId().getCodice(), true);
    }

    private RuoliService ruoliService;
    private AzioniService azioniService;

    @Autowired
    public void setAzioniService(AzioniService azioniService) {

	this.azioniService = azioniService;
    }

    @Autowired
    public void setRuoliService(RuoliService ruoliService) {

	this.ruoliService = ruoliService;
    }

    private TipiprocedureDyn2modellitService tipiprocedureDyn2modellitService;

    @Autowired
    public void setTipiprocedureDyn2modellitService(TipiprocedureDyn2modellitService tipiprocedureDyn2modellitService) {

	this.tipiprocedureDyn2modellitService = tipiprocedureDyn2modellitService;
    }

    @Override
    public List<Istanze> findByTipoSoggetto(Integer codiceTiposoggetto, int firstResult, int maxResults) {

	if (codiceTiposoggetto == null) {
	    throw new IllegalArgumentException("findByTipoSoggetto: il parametro codiceTiposoggetto e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipisoggettoId", codiceTiposoggetto, Integer.class));
	filterTable.addRestriction(fr);
	return istanzeDAO.findByFilterTable(filterTable, firstResult, maxResults);
    }

    @Override
    public List<Istanze> findByTipologiaIstanza(Integer codiceTipologia, int firstResult, int maxResults) {

	if (codiceTipologia == null) {
	    throw new IllegalArgumentException("findByTipologiaIstanza: il parametro codiceTipologia e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipologiaistanzaId", codiceTipologia, Integer.class));
	filterTable.addRestriction(fr);
	return istanzeDAO.findByFilterTable(filterTable, firstResult, maxResults);
    }

    @Override
    public List<Istanze> findByTipimovimentoAvvio(String tipomovimento, int firstResult, int maxResults) {

	if (tipomovimento == null) {
	    throw new IllegalArgumentException("findByTipimovimentoAvvio: il parametro tipomovimento e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipoMovimentoAvvioId", tipomovimento, String.class));
	filterTable.addRestriction(fr);
	return istanzeDAO.findByFilterTable(filterTable, firstResult, maxResults);
    }

    @Override
    public List<Istanze> findByTipiarchivioistanze(Integer codiceTipoarchivio, int firstResult, int maxResults) {

	if (codiceTipoarchivio == null) {
	    throw new IllegalArgumentException("findByTipiarchivioistanze: il parametro codiceTipoarchivio e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipoarchivioistanzaId", codiceTipoarchivio, Integer.class));
	filterTable.addRestriction(fr);
	return istanzeDAO.findByFilterTable(filterTable, firstResult, maxResults);
    }

    @Override
    public List<Istanze> findByImpianti(Integer codiceImpianto, int firstResult, int maxResults) {

	if (codiceImpianto == null) {
	    throw new IllegalArgumentException("findByImpianti: il parametro codiceImpianto e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("impiantiId", codiceImpianto, Integer.class));
	filterTable.addRestriction(fr);
	return istanzeDAO.findByFilterTable(filterTable, firstResult, maxResults);
    }

    @Override
    public List<Istanze> findByAree2(Integer codiceArea2, int firstResult, int maxResults) {

	if (codiceArea2 == null) {
	    throw new IllegalArgumentException("findByAree2: il parametro codiceArea2 e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("aree2Id", codiceArea2, Integer.class));
	filterTable.addRestriction(fr);
	return istanzeDAO.findByFilterTable(filterTable, firstResult, maxResults);
    }

    @Override
    public List<IstanzeDTO> findAttivitaOrdineByAttivitaAndDatavalidita(Integer codiceAttivita, Date dataValidita) {

	return istanzeDAO.findAttivitaOrdineByAttivitaAndDatavalidita(codiceAttivita, dataValidita);
    }

    @Override
    public void updateOrdineAttivita(Integer codIstanza, Integer nuovoOrdine) {

	istanzeDAO.updateOrdineAttivita(codIstanza, nuovoOrdine);
    }

    @Override
    public Istanze findIstanzaOrigineAttivita(Integer codiceAttivita) {

	List<Istanze> istanzes = this.findByAttivitaAndBeforeDataValiditaIstanza(codiceAttivita, new Date(), false);
	if (!istanzes.isEmpty()) {
	    return istanzes.get(0);
	}
	return null;
    }

    @Override
    public String exportModalitaPentaho(IstanzeFilter filter, Esportazioni esportazioni, String emailResponsabile, boolean isInviaMail) {

	String sessionId = ORMHelper.getToken();
	log.debug("exportModalitaPentaho# Cancello i record su tmp_esportazioni con sessionId: {}", sessionId);
	tmpEsportazioniService.deleteBysessionId(sessionId);
	log.debug("exportModalitaPentaho# Inizio esportazione istanze. Invio email. {}", isInviaMail);
	istanzeDAO.exportModalitaPentaho(filter, esportazioni, emailResponsabile, isInviaMail);
	return sessionId;
    }

    @Override
    public byte[] export(IstanzeFilter filter, Integer codiceEsp, String idComuneEsportazione, String emailResponsabile, boolean isInviaMail) {

	// §§§BEGIN§§§
	//
	// Definisco la formattazione della data
	Date today = new Date();
	DateFormat myDateFormatOut = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String outdate = myDateFormatOut.format(today);
	LISTAISTANZE listaIstanze = new LISTAISTANZE();
	//List<IAttivita> iattivitaList = new ArrayList<IAttivita>();
	double pageNumber = 0;
	double conta = 0;
	int startRow = 0;
	int rowEnd;
	//Integer count = this.countByFilter(filter);
	Integer count = this.countIstanzeListHelperByFilter(filter);
	if (count > 0) {
	    if (count < pageSize) {
		pageNumber = 1;
	    } else {
		conta = Double.valueOf(count) / Double.valueOf(pageSize);
		pageNumber = Math.ceil(conta);
	    }
	    for (int i = 0; i < pageNumber; i++) {
		startRow = i * (Double.valueOf(pageSize).intValue());
		rowEnd = (Double.valueOf(pageSize).intValue());
		List<IstanzeListHelper> iIstanzesTemp = this.findIstanzeListHelperByFilter(filter, startRow, rowEnd);
		/* Vengono utilizzati gli Stub creati per Infocamere quindi la lista istanze in realtà
		 * rappresenta la lista di IAttività.  
		 * Popolo l'oggetto ISTANZA (Oggetto del WS) con i dati dell' attivita (IDCOMUNE,CODICE_ATTIVITA,CODICE_COMUNE)
		 * e la data passata (se non passata verrà di default inserita quella di sistema.)
		 */
		for (IstanzeListHelper iIstanzaH : iIstanzesTemp) {
		    ISTANZA istanza = new ISTANZA();
		    istanza.setIDCOMUNE(ORMHelper.getIdcomune());
		    //String codiceattivita = iAttivita.getId().getCodice().toString();
		    istanza.setCODICE(iIstanzaH.getCodiceistanza().toString());
		    istanza.setDATA(outdate);
		    istanza.setCODICECOMUNE(iIstanzaH.getCodicecomune());
		    listaIstanze.getISTANZA().add(istanza);
		}
	    }
	}
	// WEB SERVICE SIGEPROEXPORT
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
	if (log.isDebugEnabled()) {
	    log.debug("exportIAttivita# L'esportazione prevende invio email: {}", isInviaMail);
	}
	if (isInviaMail) {
	    if (log.isDebugEnabled()) {
		log.debug("exportIAttivita# L'esportazione inviata alla  email: {}", emailResponsabile);
	    }
	    responseByte = SigeproExportWsClient.exportMail(ORMHelper.getToken(), listaIstanze, codiceEsp, idComuneEsportazione, listaParametri,
		    emailResponsabile, true);
	} else {
	    responseByte = SigeproExportWsClient.export(ORMHelper.getToken(), listaIstanze, codiceEsp, idComuneEsportazione, listaParametri, true);
	}
	return responseByte;
    }

    @Override
    public List<IstanzeDaChiudereHelper> findIstanzedaChiudere() {

	return istanzeDAO.findIstanzedaChiudere();
    }

    @Override
    public List<IstanzePerInterventiHelper> countNumeroIstanzeGrupByInterventi(String codiceSoftware, Date fromDate, Date toDate, Integer startRow,
	    Integer maxRow) {

	return istanzeDAO.countNumeroIstanzeGrupByInterventi(codiceSoftware, fromDate, toDate, startRow, maxRow);
    }

    @Override
    public List<IstanzePerProcedimentiHelper> countNumeroIstanzeGrupByProcedimenti(String codiceSoftware, Date fromDate, Date toDate,
	    Integer startRow, Integer maxRow) {

	return istanzeDAO.countNumeroIstanzeGrupByProcedimenti(codiceSoftware, fromDate, toDate, startRow, maxRow);
    }

    @Override
    public int countIstanzeDaAssegnareAdIstruttoreByRespProcedimento(Responsabili responsabiliProcedimento) {

	//	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	//	FilterRestriction fr = new FilterRestriction();
	//	fr.addFilterField(FilterUtils.equals("id.codice", responsabiliProcedimento.getId().getCodice(), "responsabileProcedimento", Integer.class));
	//	fr.addFilterField(FilterUtils.isNull("id.codice", "istruttoreTemp"));
	//	fr.addFilterField(FilterUtils.isNull("id.codice", "istruttore"));
	//	fr.addFilterField(FilterUtils.isNotNull("id.codice", "gruppiIstruttori"));
	//	ft.addRestriction(fr);
	//	int count = istanzeDAO.countRecord(ft);
	IstanzeFilter filter = new IstanzeFilter();
	filter.setIsPraticheDaAssegnareAdIstruttore(true);
	filter.setUtenteLoggato(responsabiliProcedimento);
	int count = istanzeDAO.countIstanzeListHelperByFilter(filter);
	return count;
    }

    @Override
    public int countIstanzeAssegnareAdIstruttoreByRespProcedimento(Responsabili responsabile) {

	//	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	//	FilterRestriction fr = new FilterRestriction();
	//	fr.addFilterField(FilterUtils.isNotNull("id.codice", "istruttoreTemp"));
	//	fr.addFilterField(FilterUtils.equals("id.codice", responsabile.getId().getCodice(), "istruttoreTemp", Integer.class));
	//	fr.addFilterField(FilterUtils.isNull("id.codice", "istruttore"));
	//	fr.addFilterField(FilterUtils.isNotNull("id.codice", "gruppiIstruttori"));
	//	ft.addRestriction(fr);
	//	int count = istanzeDAO.countRecord(ft);
	IstanzeFilter filter = new IstanzeFilter();
	filter.setIsPraticheDaAccettareComeIstruttore(true);
	filter.setUtenteLoggato(responsabile);
	int count = istanzeDAO.countIstanzeListHelperByFilter(filter);
	return count;
    }

    @Override
    public void accettaOrRifiutaRuoloIstruttore(Integer codiceIstanza, Responsabili istruttore, Boolean accetta, Boolean rigetta) {

	Istanze istanza = this.findById(new PkId(codiceIstanza));
	if (accetta) {
	    istanza.setIstruttore(istruttore);
	    istanza.setGprDataAccettazione(new Date());
	    // Devo settare i permessi per l'istruttore che ha appena accettato l'incarico
	    if (istruttore != null) {
		PermistanzeId id = new PermistanzeId(codiceIstanza, istruttore.getId().getCodice());
		Permistanze permistanze = new Permistanze();
		permistanze.setId(id);
		permistanze.setIstanze(istanza);
		permistanze.setResponsabile(istruttore);
		permistanzeService.notCheckinsertInFaseDiaccettazioneRuoloIstruttore(permistanze);
	    }
	}
	if (rigetta) {
	    istanza.setIstruttoreTemp(null);
	    // rimuovo il permesso
	    if (istruttore != null) {
		PermistanzeId id = new PermistanzeId(codiceIstanza, istruttore.getId().getCodice());
		Permistanze permistanze = new Permistanze();
		permistanze.setId(id);
		permistanze.setIstanze(istanza);
		permistanze.setResponsabile(istruttore);
		permistanzeService.notCheckinsertInFaseDiaccettazioneRuoloIstruttore(permistanze);
	    }
	}
	istanzeDAO.update(istanza);
	try {
	    this.sendEmailNoticheFunzionalitaAntiCorruzione(istanza, istruttore, accetta, rigetta, false);
	} catch (Exception e) {
	    log.error("accettaOrRifiutaRuoloIstruttore#Errore durante l'invio delle email di comunicazione accettazione : {} [{}] ", e.getMessage(),
		    e);
	}
    }

    /**
     * Nel caso della funzionalità anti corruzione e conflitto di interessi gli attori in ballo devono essere informati
     * tramie email per quel che riguarda accettazioni di presa in carico o assegnazione del ruolo di istruttore. La
     * email verra inviate se nella verticalizzazione ANTI_CORRUZIONE sono configurate le mail tipo.
     */
    @Override
    public void sendEmailNoticheFunzionalitaAntiCorruzione(Istanze istanza, Responsabili istruttore, boolean accetta, boolean rigetta,
	    boolean assegnazione) throws FunzioneBusinessRemotaException {

	Verticalizzazioniparametri verticalizzazioniparametri = null;
	Mailtipo mTipo = null;
	boolean isAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ANTICORRUZIONE);
	if (isAttiva) {
	    if (accetta) {
		verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_ANTICORRUZIONE,
			"MAIL_TIPO_PRESA_IN_CARICO", ORMHelper.getSoftware());
		if (verticalizzazioniparametri != null && StringUtils.isNotBlank(verticalizzazioniparametri.getValore())) {
		    try {
			mTipo = mailtipoService.findById(new PkId(Integer.parseInt(verticalizzazioniparametri.getValore())));
		    } catch (NumberFormatException ne) {
			log.error("sendEmailNoticheFunzionalitaAntiCorruzione#Il codice salvato in verticalizzazione non è un intero ");
		    }
		    if (EntityUtils.getNestedProperty(mTipo, "id.codice") != null) {
			mTipo = mailtipoService.replaceOggettoCorpo(mTipo, istanza, null);
		    }
		}
	    }
	    if (rigetta) {
		verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_ANTICORRUZIONE,
			WebConstants.VERTICALIZZAZIONE_ANTICORRUZIONE_MAIL_TIPO_RIFIUTO_INCARICO, ORMHelper.getSoftware());
		if (verticalizzazioniparametri != null && StringUtils.isNotBlank(verticalizzazioniparametri.getValore())) {
		    try {
			mTipo = mailtipoService.findById(new PkId(Integer.parseInt(verticalizzazioniparametri.getValore())));
		    } catch (NumberFormatException ne) {
			log.error("sendEmailNoticheFunzionalitaAntiCorruzione#Il codice salvato in verticalizzazione non è un intero ");
		    }
		    if (EntityUtils.getNestedProperty(mTipo, "id.codice") != null) {
			mTipo = mailtipoService.replaceOggettoCorpo(mTipo, istanza, null);
		    }
		}
	    }
	    if (assegnazione) {
		verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_ANTICORRUZIONE,
			WebConstants.VERTICALIZZAZIONE_ANTICORRUZIONE_MAIL_TIPO_ASSEGNAZIONE, ORMHelper.getSoftware());
		if (verticalizzazioniparametri != null && StringUtils.isNotBlank(verticalizzazioniparametri.getValore())) {
		    try {
			mTipo = mailtipoService.findById(new PkId(Integer.parseInt(verticalizzazioniparametri.getValore())));
		    } catch (NumberFormatException ne) {
			log.error("sendEmailNoticheFunzionalitaAntiCorruzione#Il codice salvato in verticalizzazione non è un intero ");
		    }
		    if (EntityUtils.getNestedProperty(mTipo, "id.codice") != null) {
			mTipo = mailtipoService.replaceOggettoCorpo(mTipo, istanza, null);
		    }
		}
	    }
	    if (mTipo != null && StringUtils.isNotBlank(istruttore.getEmail())) {
		Mailtipo mt = mailtipoService.replaceOggettoCorpo(mTipo, istanza, null);
		MailMessageType mailMessage = new MailMessageType();
		mailMessage.setOggetto(mt.getOggetto());
		mailMessage.setCorpoMail(mt.getCorpo());
		mailMessage.setDestinatari(istruttore.getEmail());
		mailMessage.setInviaComeHtml(true);
		mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), null, ORMHelper.getToken(), mailMessage);
	    }
	}
    }

    @Override
    public void updateIstruttore(Integer codiceIstanza, Integer codiceIstruttore, boolean tracciaInEventi) {

	log.debug("updateIstruttore# recupero i dati necessari per creare il log di avvenuta modifica");
	Istanze istanza = this.findById(new PkId(codiceIstanza));
	String istruttoreSostituito = "";
	// Recupero il Responsabile loggato
	log.debug("updateIstruttore# Recupero il responsabile loggato");
	LoggedUser userDetail = (LoggedUser) userSecurityService.getCurrentlyAuthenticatedUser();
	Responsabili responsabile = responsabiliService.findById(new PkId(userDetail.getCodiceResponsabile()));
	log.debug("updateIstruttore# Recupero l'attuale istruttore della pratica");
	if (EntityUtils.getNestedProperty(istanza.getIstruttore(), "id.codice") != null) {
	    istruttoreSostituito = istanza.getIstruttore().getResponsabile();
	}
	log.debug("updateIstruttore# Recupero l'istruttore con cui andremo a sostituire quello attuale ");
	Responsabili istruttore = responsabiliService.findById(new PkId(codiceIstruttore));
	log.debug("updateIstruttore# Aggiorno responsabile istruttore con  {}({})",
		new Object[] { istruttore.getResponsabile(), istruttore.getId().getCodice() });
	istanzeDAO.updateIstruttore(codiceIstanza, codiceIstruttore);
	log.debug("updateIstruttore# traccio la modifica sul file di log di audit");
	LoggerModificheIstanze.logMofificaIstruttoreAssegnato(responsabile.getResponsabile(), istruttoreSostituito, istruttore.getResponsabile(),
		istanza.getNumeroistanza());
	if (tracciaInEventi) {
	    log.debug("updateIstruttore# La procedura prevede anche l'inserimento nella tabella istanza eventi");
	    StringBuffer evento = new StringBuffer("In data ");
	    evento.append(Utilities.getToday(true)).append(" l'operatore ").append(responsabile.getResponsabile())
		    .append(" ha sostituito il responsabile istruttore ").append(istruttoreSostituito).append(" con l'istruttore ")
		    .append(istruttore.getResponsabile()).append(" per l''istanza ").append(istanza.getNumeroistanza());
	    istanzeeventiService.insert(evento.toString(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
	}
    }

    @Override
    public Istanze findByUiid(String uuid) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("uuid", uuid, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("data"));
	List<Istanze> list = this.findByFilterTable(ft, 0, 1);
	if (list.size() > 0) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public SorteggidettaglioDTO findByIstanza(Integer codiceIstanza) {

	return istanzeDAO.findByIstanza(codiceIstanza);
    }

    @Override
    public ResponsabiliAssegnazioniHelper calcolaAssegnazioneResponsabiliProc(Integer codiceIstanza) {

	ResponsabiliAssegnazioniHelper ret = new ResponsabiliAssegnazioniHelper();
	Istanze ist = this.findById(new PkId(codiceIstanza));
	if (ist.getGruppiIstruttori() != null && ist.getGruppiIstruttori().getId() != null && ist.getGruppiIstruttori().getId().getCodice() != null) {
	    Integer idAlberoproc = ist.getAlberoproc().getId().getCodice();
	    String scCodice = alberoprocService.findGerarchiaAlberoGruppi(idAlberoproc, ist.getGruppiIstruttori().getId().getCodice());
	    Map<Integer, Integer> codResp = new HashMap<Integer, Integer>();
	    List<GruppiIstruttoriResp> grsits = gruppiIstruttoriRespService.findByGruppoIstruttori(ist.getGruppiIstruttori().getId().getCodice(),
		    true, true, null, null);
	    for (GruppiIstruttoriResp gist : grsits) {
		if (gist.getResponsabili() != null && gist.getResponsabili().getId() != null && gist.getResponsabili().getId().getCodice() != null) {
		    if (!responsabiliAssenzeService.isAssente(gist.getResponsabili(), ist.getData())) {
			codResp.put(gist.getResponsabili().getId().getCodice(), Integer.valueOf(0));
		    }
		}
	    }
	    if (codResp.size() > 0) {
		List<ChiaveValoreBean<Integer, Integer>> ls = new ArrayList<ChiaveValoreBean<Integer, Integer>>();
		ls = istanzeDAO.countPresenzeResponsabiliPerIstanzeInCorso(codiceIstanza, codResp, scCodice);
		for (ChiaveValoreBean<Integer, Integer> cbv : ls) {
		    Integer codiceResponsabile = cbv.getChiave();
		    Integer istanzeResponsabile = cbv.getValore();
		    codResp.put(codiceResponsabile, istanzeResponsabile);
		}
		int max = 0;
		// trovate il conteggio istanze per operatore adesso devo valutare il peso
		for (Map.Entry<Integer, Integer> entry : codResp.entrySet()) {
		    Integer valore = entry.getValue();
		    if (valore.intValue() > max) {
			max = valore.intValue();
		    }
		}
		int valoreCarrelloIpotetico = max + 100;
		for (Map.Entry<Integer, Integer> entry : codResp.entrySet()) {
		    Integer codiceResponsabile = entry.getKey();
		    Responsabili resp = responsabiliService.findById(new PkId(codiceResponsabile));
		    int peso = resp.getPesoCaricoLavoro() == null ? peso = 0 : resp.getPesoCaricoLavoro();
		    if (peso > 0) {
			int valore = entry.getValue() == null ? 0 : entry.getValue().intValue();
			ResponsabiliAssegnazioniHelperBean r = new ResponsabiliAssegnazioniHelperBean(resp, valore, valoreCarrelloIpotetico);
			ret.addResponsabiliAssegnatari(r);
		    }
		}
	    }
	}
	return ret;
    }

    @Override
    public ResponsabiliAssegnazioniHelper calcolaAssegnazioneIstruttori(Integer codiceIstanza) {

	ResponsabiliAssegnazioniHelper ret = new ResponsabiliAssegnazioniHelper();
	Istanze ist = this.findById(new PkId(codiceIstanza));
	boolean isAssegnazioneOperatori = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI);
	Integer gruppoIstruttori = null;
	if (isAssegnazioneOperatori) {
	    Verticalizzazioniparametri idGruppo = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    WebConstants.VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI,
		    WebConstants.VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI_GRUPPO_ISTRUTTORI_DEFAULT, ist.getComune().getCodicecomune(),
		    ist.getSoftware().getCodice());
	    if (idGruppo != null && StringUtils.isNotBlank(idGruppo.getValore())) {
		if (Utilities.isInteger(idGruppo.getValore().trim())) {
		    gruppoIstruttori = Integer.parseInt(idGruppo.getValore().trim());
		}
	    }
	}
	Map<Integer, Integer> codResp = new HashMap<Integer, Integer>();
	String scCodice = "";
	if (gruppoIstruttori != null) {
	    List<GruppiIstruttoriResp> grsits = gruppiIstruttoriRespService.findByGruppoIstruttori(gruppoIstruttori, true, true, null, null);
	    for (GruppiIstruttoriResp gist : grsits) {
		if (gist.getResponsabili() != null && gist.getResponsabili().getId() != null && gist.getResponsabili().getId().getCodice() != null) {
		    if (!responsabiliAssenzeService.isAssente(gist.getResponsabili(), ist.getData())) {
			codResp.put(gist.getResponsabili().getId().getCodice(), Integer.valueOf(0));
		    }
		}
	    }
	} else {
	    if (ist.getGruppiIstruttori() != null && ist.getGruppiIstruttori().getId() != null
		    && ist.getGruppiIstruttori().getId().getCodice() != null) {
		Integer idAlberoproc = ist.getAlberoproc().getId().getCodice();
		scCodice = alberoprocService.findGerarchiaAlberoGruppi(idAlberoproc, ist.getGruppiIstruttori().getId().getCodice());
		List<GruppiIstruttoriResp> grsits = gruppiIstruttoriRespService.findByGruppoIstruttori(ist.getGruppiIstruttori().getId().getCodice(),
			true, true, null, null);
		for (GruppiIstruttoriResp gist : grsits) {
		    if (gist.getResponsabili() != null && gist.getResponsabili().getId() != null
			    && gist.getResponsabili().getId().getCodice() != null) {
			if (!responsabiliAssenzeService.isAssente(gist.getResponsabili(), ist.getData())) {
			    codResp.put(gist.getResponsabili().getId().getCodice(), Integer.valueOf(0));
			}
		    }
		}
	    }
	}
	if (codResp.size() > 0) {
	    List<ChiaveValoreBean<Integer, Integer>> ls = new ArrayList<ChiaveValoreBean<Integer, Integer>>();
	    ls = istanzeDAO.countPresenzeIstruttoriPerIstanzeInCorso(codiceIstanza, codResp, scCodice);
	    for (ChiaveValoreBean<Integer, Integer> cbv : ls) {
		Integer codiceResponsabile = cbv.getChiave();
		Integer istanzeResponsabile = cbv.getValore();
		codResp.put(codiceResponsabile, istanzeResponsabile);
	    }
	    int max = 0;
	    // trovate il conteggio istanze per operatore adesso devo valutare il peso
	    for (Map.Entry<Integer, Integer> entry : codResp.entrySet()) {
		Integer valore = entry.getValue();
		if (valore.intValue() > max) {
		    max = valore.intValue();
		}
	    }
	    int valoreCarrelloIpotetico = max + 100;
	    for (Map.Entry<Integer, Integer> entry : codResp.entrySet()) {
		Integer codiceResponsabile = entry.getKey();
		Responsabili resp = responsabiliService.findById(new PkId(codiceResponsabile));
		int peso = resp.getPesoCaricoLavoro() == null ? peso = 0 : resp.getPesoCaricoLavoro();
		if (peso > 0) {
		    int valore = entry.getValue() == null ? 0 : entry.getValue().intValue();
		    ResponsabiliAssegnazioniHelperBean r = new ResponsabiliAssegnazioniHelperBean(resp, valore, valoreCarrelloIpotetico);
		    ret.addResponsabiliAssegnatari(r);
		}
	    }
	}
	return ret;
    }

    @Override
    public Set<Integer> findDocumentiIstanzaByMetadati(Integer codiceIstanza, List<CodiceDescrizioneBean> mds) {

	Set<Integer> result = new HashSet<Integer>();
	List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza = documentiistanzaService.findDocumentiistanzaDTOByIstanza(codiceIstanza, false);
	findDocumentiistanzaDTOByIstanza.addAll(documentiistanzaService.findDocumentiistanzaDTOByIstanza(codiceIstanza, true));
	for (DocumentiistanzaDTO documentiistanzaDTO : findDocumentiistanzaDTOByIstanza) {
	    boolean trovato = false;
	    if (documentiistanzaDTO.getCodiceOggetto() != null) {
		for (CodiceDescrizioneBean cvh : mds) {
		    if (trovato) {
			break;
		    }
		    String codiceMetadato = cvh.getCodice();
		    String valoreMetadato = cvh.getDescrizione();
		    List<OggettiMetadati> mdss = oggettiMetadatiService.findByOggetto(documentiistanzaDTO.getCodiceOggetto(), codiceMetadato);
		    for (OggettiMetadati oggettiMetadati : mdss) {
			if (StringUtils.defaultString(oggettiMetadati.getValore(), "-999-1*2")
				.equalsIgnoreCase(StringUtils.defaultString(valoreMetadato, "-999-2*3"))) {
			    trovato = true;
			    result.add(documentiistanzaDTO.getCodiceOggetto());
			    break;
			}
		    }
		}
	    }
	}
	return result;
    }

    public void cancellazioneRemota(CodiceDescrizioneBean identificativipraticaldp, boolean attivacancellazioneLDP)
	    throws OperazioniAutomaticheException {

	if (attivacancellazioneLDP && identificativipraticaldp != null) {
	    try {
		ldpWsClient.deleteOccupazioneSuoloByIdentificativo(identificativipraticaldp, null);
	    } catch (Exception e) {
		throw new OperazioniAutomaticheException(e);
	    }
	}
    }

    @Override
    public void updateContatori(boolean processaSoloIlPrimoGiornoDEllanno) {

	istanzeDAO.updateContatori(processaSoloIlPrimoGiornoDEllanno);
    }

    @Override
    public boolean verificaResponsabileNellIstanze(Integer codiceResponsabile) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.equals("operatoreInCaricoId", codiceResponsabile, Integer.class));
	fr.addFilterField(FilterUtils.equals("responsabileProcedimentoId", codiceResponsabile, Integer.class));
	fr.addFilterField(FilterUtils.equals("responsabileId", codiceResponsabile, Integer.class));
	fr.addFilterField(FilterUtils.equals("istruttoreTempId", codiceResponsabile, Integer.class));
	ft.addRestriction(fr);
	return istanzeDAO.countRecord(ft) > 0;
    }

    @Override
    public void updatePrendiInCarico(Integer codiceIstanza, Integer codiceResponsabile) {

	istanzeDAO.updatePrendiInCarico(codiceIstanza, codiceResponsabile);
    }

    @Override
    public int countIstanzeConStatoInWarning() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("flagWarning", Boolean.TRUE, "chiusura", Boolean.class));
	ft.addRestriction(fr);
	return istanzeDAO.countRecord(ft);
    }

    @Override
    public int countByAmministrazioni(Integer codiceAmministrazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazione, Integer.class));
	ft.addRestriction(fr);
	return istanzeDAO.countRecord(ft);
    }

    @Override
    public String findEmailSoggettoPratica(Integer codiceAnagrafe, Integer codiceIstanza, Integer codiceMovimento) {

	if (codiceAnagrafe == null) {
	    return null;
	}
	Anagrafe anagrafe = anagrafeService.findById(new PkId(codiceAnagrafe));
	if (anagrafe == null) {
	    return null;
	}
	if (codiceIstanza == null) {
	    if (codiceMovimento != null) {
		Movimenti mov = movimentiService.findById(new PkId(codiceMovimento));
		codiceIstanza = mov.getIstanza().getId().getCodice();
	    }
	}
	if (codiceIstanza != null) {
	    Istanze i = this.findById(new PkId(codiceIstanza));
	    IVerticalizzazioneProtocolloAttivoService iVertProtAttivoService = new VerticalizzazioneProtocolloAttivoServiceImpl(
		    verticalizzazioniService, i.getComune().getCodicecomune());
	    IndirizzoMailResolver mailResolver = new IndirizzoMailResolver(iVertProtAttivoService, i.getComune().getCodicecomune(),
		    i.getSoftware().getCodice());
	    if (i.getRichiedente() != null && i.getRichiedente().getId() != null && i.getRichiedente().getId().getCodice() != null) {
		if (codiceAnagrafe.equals(i.getRichiedente().getId().getCodice())) {
		    return mailResolver.getMailAnagrafe(i.getRichiedente(), i.getDomicilioElettronico(), TipoDestinatarioEnum.RICHIEDENTE);
		}
	    }
	    if (i.getTitolarelegale() != null && i.getTitolarelegale().getId() != null && i.getTitolarelegale().getId().getCodice() != null) {
		if (codiceAnagrafe.equals(i.getTitolarelegale().getId().getCodice())) {
		    return mailResolver.getMailAnagrafe(i.getTitolarelegale(), i.getDomicilioElettronico(), TipoDestinatarioEnum.AZIENDA);
		}
	    }
	}
	return StringUtils.defaultIfEmpty(anagrafe.getPec(), anagrafe.getEmail());
    }

    @Override
    public boolean isDataSuccessivaAllaChiusura(Integer codiceIstanza, Date data) {

	IstanzeTempistica istanzaTempistica = istanzeTempisticaService.findById(new PkId(codiceIstanza));
	Istanze istanza = this.findById(new PkId(codiceIstanza));
	return (!(istanzaTempistica != null && istanzaTempistica.getDatafineeffettiva() != null && data != null //
		&& istanza.getChiusura().getStaticomportamento().isComportamentoChiusura() //
		&& !data.after(istanzaTempistica.getDatafineeffettiva())));
    }

    @Override
    public void updateStatoIstanze(Set<Integer> istanzes, String codicestato) {

	if (istanzes != null && istanzes.isEmpty()) {
	    return;
	}
	StatiistanzaId id = new StatiistanzaId(codicestato);
	Statiistanza si = statiistanzaService.findById(id);
	if (si == null) {
	    return;
	}
	for (Integer codiceIstanza : istanzes) {
	    Istanze istanza = this.findById(new PkId(codiceIstanza));
	    this.updateStatoIstanza(istanza, codicestato);
	}
    }

    @Override
    public List<IstanzeListHelper> findIstanzaLocalizzazioneSimile(Integer codiceStradario, String civico, String esponente, String colore,
	    Integer codiceIstanza, Integer firstResult, Integer maxResult) {

	return istanzeDAO.findIstanzaLocalizzazioneSimile(codiceStradario, civico, esponente, colore, codiceIstanza, firstResult, maxResult);
    }

    @Override
    public AutorizzazioniAccessiHelper findAutorizzazioniAccessiHelper(Integer idAutorizzazione) throws BusinessValidationException {

	Autorizzazioni a = autorizzazioniService.findById(new PkId(idAutorizzazione));
	AutorizzazioniAccessiHelper h = new AutorizzazioniAccessiHelper();
	h.setAutorizzazione(a);
	h.setIstanza(a.getIstanza());
	List<Istanzecollegate> ics = istanzecollegateService.findIstanzeCollegateByIstanzaCollegata(a.getIstanza());
	AutorizzazioniAccessiOperazioniHelper istanzaPrincipale = new AutorizzazioniAccessiOperazioniHelper();
	istanzaPrincipale.setIstanza(a.getIstanza());
	istanzaPrincipale.setTipo(a.getIstanza().getProcedura().getProcedura());
	istanzaPrincipale.setStato(a.getIstanza().getChiusura().getStato());
	IstanzeTempistica it = istanzeTempisticaService.findById(new PkId(a.getIstanza().getId().getCodice()));
	if (it != null) {
	    Date datafineeffettiva = it.getDatafineeffettiva();
	    istanzaPrincipale.setDataChiusuraIstanza(datafineeffettiva);
	}
	istanzaPrincipale.setTipo(a.getIstanza().getProcedura().getProcedura());
	istanzaPrincipale.setStato(a.getIstanza().getChiusura().getStato());
	h.getAutorizzazioniAccessiOperazioniHelper().add(istanzaPrincipale);
	for (Istanzecollegate ic : ics) {
	    Istanze istanzaFiglio = ic.getIstanza();
	    AutorizzazioniAccessiOperazioniHelper aoh = new AutorizzazioniAccessiOperazioniHelper();
	    aoh.setIstanza(istanzaFiglio);
	    aoh.setTipo(istanzaFiglio.getProcedura().getProcedura());
	    aoh.setStato(istanzaFiglio.getChiusura().getStato());
	    it = istanzeTempisticaService.findById(new PkId(istanzaFiglio.getId().getCodice()));
	    if (it != null) {
		Date datafineeffettiva = it.getDatafineeffettiva();
		aoh.setDataChiusuraIstanza(datafineeffettiva);
	    }
	    h.getAutorizzazioniAccessiOperazioniHelper().add(aoh);
	}
	h.setNumeroAccessiRimanenti(a.getNumPreavvisiRimasti());
	h.setProroga(a.getNumProrogheRimaste().intValue() > 0);
	h.setRinnovo(a.getNumRinnoviRimasti().intValue() > 0);
	h.setPreavviso(a.getNumPreavvisiRimasti().intValue() > 0);
	if (a.getTipologiaregistro().getNumeroPreavvisi() == null) {
	    // FIX: AUTORIZZAZIONI_ACCESSI richiesta preavviso di transito per autorizzazioni periodiche
	    // nel caso che l'autorizzazione sia periodica, ovvero non preveda un numero prefissato di transiti come 
	    // la singola o la multipla - OVVERO CHE TIPOLOGIAREGISTRI.NUMERO_PREAVVISI == NULL - allora setto a true preavvisi.
	    // lo step dell'area riservata verifica questo valore per far proseguire l'utente nella richiesta di un preavviso
	    h.setPreavviso(true);
	} else {
	    // autorizzazioni multiple e singole
	    // se il numero preavvisi/accessi rimanenti è 0 è la proroga è true allora devo settare la proroga a false
	    // fix: Ticket#2021110810000157 — istanza di proroga n. 1037
	    // è stata inviata un istanza di proroga autorizzazione (n. 1037) per l'autorizzazione singola n. 381/2021/AUTS del 02/08/2021.
	    // Per tale autorizzazione era già stato autorizzato il transito e pertanto dovrebbe essere presente un blocco che non permette più, una volta che il contatore raggiunge lo zero, la richiesta di proroga.
	    // Tale blocco dovrebbe essere presente anche per le autorizzazioni multiple.
	    if (h.getNumeroAccessiRimanenti() == 0 && h.isProroga()) {
		h.setProroga(false);
	    }
	}
	h.setNumeroTransitiConsentiti(a.getTipologiaregistro().getNumeroPreavvisi());
	return h;
    }

    @Override
    public AutorizzazioniAccessiHelper findAutorizzazioniAccessiHelper(String numeroAutorizzazione, Date dataAutorizzazione, String cfImpresa,
	    String pivaImpresa) throws BusinessValidationException {

	Autorizzazioni a = null;
	List<Anagrafe> imprese = anagrafeService.findByCf(cfImpresa, WebConstants.PERSONA_GIURIDICA, true);
	Integer codiceAnagrafe = null;
	if (imprese.size() == 1) {
	    codiceAnagrafe = imprese.get(0).getId().getCodice();
	}
	try {
	    a = autorizzazioniService.findByNumeroEDataEAzienda(numeroAutorizzazione, dataAutorizzazione, codiceAnagrafe);
	} catch (Exception e) {
	    throw new BusinessValidationException(e.getMessage());
	}
	if (codiceAnagrafe == null) {
	    // siccome non sono riuscito a recuperare una anagrafe verifico se l'autorizzazione appartiene a quel cf PIVA
	    Anagrafe titolare = a.getAnagrafe();
	    String cf = StringUtils.defaultString(titolare.getCodicefiscale(), "-1-1-1-1-1-1-1-1");
	    String piva = StringUtils.defaultString(titolare.getPartitaiva(), "-1-1-1-1-2-2-2-2");
	    boolean trovata = false;
	    if (cfImpresa.equalsIgnoreCase(cf)) {
		trovata = true;
	    }
	    if (!(trovata)) {
		if (piva.equals(pivaImpresa)) {
		    trovata = true;
		}
	    }
	    if (!trovata) {
		log.error("Autorizzazione con numero {} non legata a cf {} o piva {}",
			new Object[] { a.getTransientEstremiAut(), cfImpresa, pivaImpresa });
		throw new BusinessValidationException("Autorizzazione con numero " + numeroAutorizzazione + " non trovata");
	    }
	}
	return this.findAutorizzazioniAccessiHelper(a.getId().getCodice());
    }

    @Override
    public List<Integer> findIstanzaPerTracciatoEquitalia(TracciatoEquitaliaFilter tracciatoEquitaliaFilter, boolean isInviate, Integer firstResult,
	    Integer maxResults) {

	return istanzeDAO.findIstanzaPerTracciatoEquitalia(tracciatoEquitaliaFilter, isInviate, firstResult, maxResults);
    }

    @Override
    public String findNumeroIstanzaPadre(Integer codiceIstanza) {

	List<Domandestc> domstc = domandestcService.findByIstanza(codiceIstanza);
	if (domstc.isEmpty()) {
	    return null;
	}
	Domandestc d = domstc.get(0);
	String idNodo = d.getIdNodo();
	String idSportello = d.getIdSportellomitt();
	if (idSportello.length() == 2) {
	    Verticalizzazioniparametri p = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		    WebConstants.VERTICALIZZAZIONE_STC_NLA_IDNODO);
	    if (p == null || StringUtils.isBlank(StringUtils.defaultString(p.getValore()).trim())) {
		return null;
	    }
	    String idNodoVert = StringUtils.defaultString(p.getValore()).trim();
	    if (idNodoVert.equalsIgnoreCase(idNodo)) {
		String idDomanda = d.getIdDomandamitt();
		if (Utilities.isInteger(idDomanda)) {
		    Istanze istanzaPadre = istanzeDAO.findById(new PkId(Integer.parseInt(idDomanda)));
		    if (istanzaPadre != null) {
			return istanzaPadre.getNumeroistanza();
		    }
		}
	    }
	}
	return null;
    }

    @Override
    public List<Integer> findIstanzaProtocollazioneFallitaByTipoProtocollazione(String[] codicetipoprotocollazione) {

	return istanzeDAO.findIstanzaProtocollazioneFallitaByTipoProtocollazione(codicetipoprotocollazione);
    }

    @Override
    public void collegaIstanzaAdAttivita(IAttivita attivita, Istanze istanza) {

	if (attivita == null) {
	    throw new IllegalArgumentException("Impossibile collegare un'istanza all'attività senza passare l'attività");
	}
	if (istanza == null) {
	    throw new IllegalArgumentException("Impossibile collegare un'istanza all'attività senza passare l'istanza");
	}
	istanza.setAttivita(attivita);
	this.istanzeDAO.update(istanza);
    }

    @Override
    public void eseguiFormuleDelleSchedeDinamiche(Istanze istanza) throws OperazioniAutomaticheException {

	String[] error = null;
	try {
	    error = dyn2ModellitService.eseguiScriptSchedeIstanza(istanza);
	} catch (Exception e) {
	    log.error("Errore nell'invocazione del WS dyn2ModellitService#eseguiScriptSchedeIstanza(istanza):  " + e.getMessage(), e);
	    Istanzeeventi istanzeeventi = new Istanzeeventi();
	    istanzeeventi.setIstanze(istanza);
	    istanzeeventi.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_STC_IP);
	    istanzeeventi.setData(Calendar.getInstance().getTime());
	    istanzeeventi
		    .setDescrizione("Non è stato possibile contattare il servizio per l'esecuzione degli script delle schede dinamiche a causa di: " +
				    e.getMessage());
	    log.debug("Salvo istanze eventi");
	    istanzeeventiService.insert(istanzeeventi);
	    log.debug("Salvato istanze eventi");
	    throw new OperazioniAutomaticheException(istanzeeventi.getDescrizione());
	}
	if (error != null && error.length > 0) {
	    log.debug("Scrivo gli errori riportati su istanze eventi per l'istanza [{}]", istanza.getId());
	    Istanzeeventi istanzeeventi = new Istanzeeventi();
	    istanzeeventi.setIstanze(istanza);
	    istanzeeventi.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_STC_IP);
	    istanzeeventi.setData(Calendar.getInstance().getTime());
	    StringBuilder stringError = new StringBuilder();
	    for (int i = 0; i < error.length; i++) {
		stringError.append(error[i]).append(",");
	    }
	    istanzeeventi.setDescrizione(stringError.toString());
	    log.debug("Salvo istanze eventi");
	    istanzeeventiService.insert(istanzeeventi);
	    log.debug("Salvato istanze eventi");
	    throw new OperazioniAutomaticheException(istanzeeventi.getDescrizione());
	}
    }

    @Override
    public void updateRiferimentiProtocollo(AggiornaRiferimentiProtocolloIstanzaRequest request) throws AggiornamentoProtocolloException {

	Istanze i = validaProtocolloRequest(request);
	Date dataProtocolllo = Utilities.parseDateString(request.getDataProtocollo(), "yyyy-MM-dd");
	if (dataProtocolllo == null) {
	    throw new AggiornamentoProtocolloException("Data non valida " + request);
	}
	String istanza = i.toString();
	i.setDataprotocollo(dataProtocolllo);
	i.setNumeroprotocollo(request.getNumeroProtocollo());
	i.setFkidprotocollo(request.getFkidProtocollo());
	istanzeDAO.update(i);
	LoggerModificheIstanze
		.log("#MODIFICAPROTOCOLLOISTANZA#INIZIO updateRiferimentiProtocollo per l'istanza " + istanza + " con request: " + request);
	try {
	    this.eventPublisher.publishThrowOnFailure(new EventoIstanzaProtocollata(request.getCodiceIstanza(), new HashSet<Integer>(0)));
	} catch (EventAbortedException e) {
	    log.error("Errore nella sottoscrizione dell'evento " + e.getMessage(), e);
	    LoggerModificheIstanze.log("#MODIFICAPROTOCOLLOISTANZA#ERRORE updateRiferimentiProtocollo per l'istanza " + istanza + " con request: " +
				       request + ", dettaglio errore:" + e.getMessage());
	    throw new AggiornamentoProtocolloException(
		    "Errore nell'aggiornamento dei riferimenti protocollo per l'istanza " + istanza + ", dettaglio: " + e.getMessage(), e);
	}
	Movimenti mavv = movimentiNoSecurityService.findMovimentoAvvioIstanza(i);
	AggiornaRiferimentiProtocolloMovimentoRequest r1 = new AggiornaRiferimentiProtocolloMovimentoRequest();
	r1.setCodiceMovimento(mavv.getId().getCodice());
	r1.setNumeroProtocollo(request.getNumeroProtocollo());
	r1.setDataProtocollo(request.getDataProtocollo());
	r1.setFkidProtocollo(request.getFkidProtocollo());
	movimentiService.updateRiferimentiProtocolloMovimentoAvvio(r1);
	LoggerModificheIstanze
		.log("#MODIFICAPROTOCOLLOISTANZA#FINE updateRiferimentiProtocollo per l'istanza " + istanza + " con request: " + request);
    }

    private Istanze validaProtocolloRequest(AggiornaRiferimentiProtocolloIstanzaRequest request) throws AggiornamentoProtocolloException {

	if (request.getCodiceIstanza() == null || StringUtils.isBlank(request.getNumeroProtocollo())
		|| StringUtils.isBlank(request.getDataProtocollo())) {
	    throw new AggiornamentoProtocolloException("Dati non validi " + request);
	}
	Istanze istanza = istanzeDAO.findById(new PkId(request.getCodiceIstanza()));
	if (istanza == null) {
	    throw new AggiornamentoProtocolloException("Istanza non trovata " + request.getCodiceIstanza());
	}
	if (!StringUtils.defaultIfEmpty(StringUtils.defaultString(istanza.getNumeroprotocollo()).trim(),
		ProtocollazioneService.NUMERO_PROTOCOLLO_ASINCRONO_CHAR).equals(ProtocollazioneService.NUMERO_PROTOCOLLO_ASINCRONO_CHAR)) {
	    throw new AggiornamentoProtocolloException("Istanza ha già i riferimenti di protocollo: " + istanza);
	}
	return istanza;
    }

    @Override
    public Set<String> getCfRichiedentiPrincipaliIstanza(Integer codiceIstanza) {

	Istanze istanza = istanzeDAO.findById(new PkId(codiceIstanza));
	if (istanza == null) {
	    return new HashSet<String>(0);
	}
	Set<String> cfs = new HashSet<String>();
	if (istanza.getRichiedente() != null && istanza.getRichiedente().getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_FISICA)
		&& StringUtils.isNotBlank(istanza.getRichiedente().getCodicefiscale())) {
	    cfs.add(istanza.getRichiedente().getCodicefiscale().trim().toUpperCase());
	}
	if (istanza.getProfessionista() != null && istanza.getProfessionista().getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_FISICA)
		&& StringUtils.isNotBlank(istanza.getProfessionista().getCodicefiscale())) {
	    cfs.add(istanza.getProfessionista().getCodicefiscale().trim().toUpperCase());
	}
	return cfs;
    }

    @Override
    public TempisticaIstanzaHelper tempisticaDettaglio(Integer codiceIstanza) {

	Istanze i = istanzeDAO.findById(new PkId(codiceIstanza));
	List<MovimentiTempistica> movtempisticas = movimentiTempisticaService.findByIstanza(i);
	return new TempisticaIstanzaHelper(i, movtempisticas);
    }

    @Override
    public Set<String> getCfRichiedentiPrincipaliIstanzaAut(Integer codiceIstanza) {

	Istanze istanza = istanzeDAO.findById(new PkId(codiceIstanza));
	if (istanza == null) {
	    return new HashSet<String>(0);
	}
	Set<String> cfs = new HashSet<String>();
	if (istanza.getRichiedente() != null && istanza.getRichiedente().getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_FISICA)
		&& StringUtils.isNotBlank(istanza.getRichiedente().getCodicefiscale())) {
	    cfs.add(istanza.getRichiedente().getCodicefiscale().trim().toUpperCase());
	}
	if (istanza.getProfessionista() != null && istanza.getProfessionista().getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_FISICA)
		&& StringUtils.isNotBlank(istanza.getProfessionista().getCodicefiscale())) {
	    cfs.add(istanza.getProfessionista().getCodicefiscale().trim().toUpperCase());
	}
	List<Istanzerichiedenti> richiedenti = istanzerichiedentiService.findByIstanza(codiceIstanza);
	for (Istanzerichiedenti ir : richiedenti) {
	    if (ir.getRichiedente().getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_FISICA)
		    && StringUtils.isNotBlank(ir.getRichiedente().getCodicefiscale()) && ir.getTiposoggetto().getFlagLivelliVisuraPratica() != null
		    && ir.getTiposoggetto().getFlagLivelliVisuraPratica() > 0) {
		cfs.add(ir.getRichiedente().getCodicefiscale());
	    }
	}
	return cfs;
    }

    @Override
    public boolean isChiusa(Integer codiceIstanza) {

	return istanzeDAO.isChiusa(codiceIstanza);
    }

    @Override
    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeResponsabiliPerIstanzeInCorso(Integer codiceIstanza, Map<Integer, Integer> codResp,
	    String scCodice) {

	return this.istanzeDAO.countPresenzeResponsabiliPerIstanzeInCorso(codiceIstanza, codResp, scCodice);
    }

    @Override
    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeIstruttoriPerIstanzeInCorso(Integer codiceIstanza, Map<Integer, Integer> codResp,
	    String scCodice) {

	return this.istanzeDAO.countPresenzeIstruttoriPerIstanzeInCorso(codiceIstanza, codResp, scCodice);
    }

    @Override
    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeResponsabiliPerIstanzeInCorsoNew(Integer codiceIstanza,
	    Map<Integer, Integer> codResp, String scCodice, Integer idTestata) {

	return istanzeDAO.countPresenzeResponsabiliPerIstanzeInCorsoNew(codiceIstanza, codResp, scCodice, idTestata);
    }

    @Override
    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeIstruttoriPerIstanzeInCorsoNew(Integer codiceIstanza, Map<Integer, Integer> codResp,
	    String scCodice, Integer idTestata) {

	return istanzeDAO.countPresenzeIstruttoriPerIstanzeInCorsoNew(codiceIstanza, codResp, scCodice, idTestata);
    }

    @Override
    public boolean existsById(Integer codiceIstanza) {

	istanzeDAO.flush();
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, Integer.class));
	ft.addRestriction(fr);
	return istanzeDAO.existsRecords(ft);
    }

    @Override
    public boolean aggiornaLocalizzazionePrimariaDaCartografico(Integer codiceIstanza) {

	Istanzestradario localizzazione = this.istanzestradarioService.findPrimarioByCodiceIstanza(codiceIstanza);
	if (localizzazione == null) {
	    return false;
	}
	try {
	    ParametriResponse response = this.cartograficoService.getParametri(localizzazione.getUuid());
	    if (response == null || response.getParametri() == null || response.getParametri().size() != 1) {
		return false;
	    }
	    ParametroResponse parametri = response.getParametri().get(0);
	    BigDecimal latitudine = parametri.getLatitudine();
	    BigDecimal longitudine = parametri.getLongitudine();
	    if (latitudine != null && longitudine != null) {
		this.istanzestradarioService.updateCoordinateByUuId(codiceIstanza, localizzazione.getUuid(), latitudine, longitudine);
		return true;
	    }
	} catch (Exception e) {
	    log.debug("Errore durante il recupero dei parametri dal cartografico: ", e);
	}
	return false;
    }
}
