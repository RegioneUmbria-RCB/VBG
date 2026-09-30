package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.naming.ConfigurationException;
import javax.print.PrintService;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PecInbox;
import it.gruppoinit.pal.gp.core.domain.PecInboxId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloMezzi;
import it.gruppoinit.pal.gp.core.domain.ProtocolloModalitainvio;
import it.gruppoinit.pal.gp.core.domain.ProtocolloSmistamenti;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.TempLinkallegati;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.AllegatoDataHandlerHelper;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.CdsattiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.PercorsoAlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AzioniProtocollazioneCommand;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand;
import it.gruppoinit.pal.gp.core.domain.web.ProtocolloSoggettoCommand;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoIstanzaProtocollata;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoMovimentoProtocollato;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.autorizzazioni.IProtocollazioneAutorizzazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.autorizzazioni.ProtocollaAutorizzazioneResponse;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.destinatari.DestinatariResolver;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.destinatari.IndirizzoMailResolver;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.AggiungiAllegatiException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.AnnullamentoProtocolloException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.CambiaFascicoloIstanzaException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.CreaCopieException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.CreaUnitaDocumentaleException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.EseguiAccettazioneException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.FascicolaIstanzaException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.FascicolaMovimentoException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.InvioPECException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.LeggiProtocolloException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.PopolaAllegatiPECException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.ProtocollaIstanzaException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.ProtocollazioneAutorizzazioneException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.ProtocollazioneFallitaException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.ProtocollazioneMovimentoException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.RecuperaClassificheException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.RecuperaMotiviAnnullamentoException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.RecuperaTipiDocumentoException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.RegistrazioneIstanzaXmlException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.RicercaFascicoliException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.StampaEtichetteException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.VerificaProtocolloAnnullatoException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.VerificaProtocolloFascicolatoException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.flusso.FlussoResolver;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.mittenti.MittentiResolver;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.tipodocumento.TipoDocumentoResolver;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocProtocolloService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrProtocolloService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.DocumentiHelperService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService.TipoInserimento;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.NlaHelperService;
import it.gruppoinit.pal.gp.core.service.PecInboxService;
import it.gruppoinit.pal.gp.core.service.ProtocolloConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.ProtocolloSmistamentiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TempLinkallegatiService;
import it.gruppoinit.pal.gp.core.service.TipimovStcMappingService;
import it.gruppoinit.pal.gp.core.service.TipisoggettopeopleService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.service.helper.TipoDocumentoType;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.service.impl.NlaHelperServiceImpl;
import it.gruppoinit.pal.gp.core.service.rules.AnagrafeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.BusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.ProtocolloWSClient;
import it.gruppoinit.pal.gp.core.ws.client.StcWsClient;
import it.gruppoinit.protocollo.schemas.messages.AllegatoResponseType;
import it.gruppoinit.protocollo.schemas.messages.AllegatoType;
import it.gruppoinit.protocollo.schemas.messages.ArrayOfAllegatoResponseType;
import it.gruppoinit.protocollo.schemas.messages.ArrayOfAllegatoType;
import it.gruppoinit.protocollo.schemas.messages.ArrayOfDatiFascType;
import it.gruppoinit.protocollo.schemas.messages.ArrayOfDatiProtocolloLettoResponseType;
import it.gruppoinit.protocollo.schemas.messages.ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType;
import it.gruppoinit.protocollo.schemas.messages.ArrayOfMetadatoType;
import it.gruppoinit.protocollo.schemas.messages.ArrayOfProtocolloAmministrazioni;
import it.gruppoinit.protocollo.schemas.messages.ArrayOfProtocolloAnagrafe;
import it.gruppoinit.protocollo.schemas.messages.ArrayOfint;
import it.gruppoinit.protocollo.schemas.messages.CreaUnitaDocumentaleRequestType;
import it.gruppoinit.protocollo.schemas.messages.CreaUnitaDocumentaleResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiAnagraficiType;
import it.gruppoinit.protocollo.schemas.messages.DatiDestinatariXmlType;
import it.gruppoinit.protocollo.schemas.messages.DatiFascType;
import it.gruppoinit.protocollo.schemas.messages.DatiFascicoloResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiMailType;
import it.gruppoinit.protocollo.schemas.messages.DatiMittentiType;
import it.gruppoinit.protocollo.schemas.messages.DatiMittentiXmlType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloAnnullatoResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloEsitatoResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloFascicolatoResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloLettoResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiRequestType;
import it.gruppoinit.protocollo.schemas.messages.EnumStatusType;
import it.gruppoinit.protocollo.schemas.messages.ErroreProtocolloType;
import it.gruppoinit.protocollo.schemas.messages.EseguiAccettazioneResponseType;
import it.gruppoinit.protocollo.schemas.messages.EtichetteResponseType;
import it.gruppoinit.protocollo.schemas.messages.IProtocollazioneService;
import it.gruppoinit.protocollo.schemas.messages.LeggiProtocolloRequest;
import it.gruppoinit.protocollo.schemas.messages.ListaFascicoliResponseType;
import it.gruppoinit.protocollo.schemas.messages.ListaMotiviAnnullamentoMotivoAnnullamentoType;
import it.gruppoinit.protocollo.schemas.messages.ListaMotiviAnnullamentoResponseType;
import it.gruppoinit.protocollo.schemas.messages.ListaTipiClassificaClassifica;
import it.gruppoinit.protocollo.schemas.messages.ListaTipiClassificaType;
import it.gruppoinit.protocollo.schemas.messages.ListaTipiDocumentoDocumentoType;
import it.gruppoinit.protocollo.schemas.messages.ListaTipiDocumentoResponseType;
import it.gruppoinit.protocollo.schemas.messages.MetadatoType;
import it.gruppoinit.protocollo.schemas.messages.ProtocollazioneMovimentoXmlRequestType;
import it.gruppoinit.protocollo.schemas.messages.ProtocolloAmministrazioni;
import it.gruppoinit.protocollo.schemas.messages.ProtocolloAnagrafe;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaRequest;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.NotificaAttivitaRequest;
import it.init.sigepro.rte.NotificaAttivitaResponse;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AllegatoBinarioType;
import it.init.sigepro.rte.types.AnagrafeType;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DettaglioAttivitaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.InterventoType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.RichiedenteType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.RuoloType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.TipoAttivitaType;
import it.init.sigepro.rte.types.ValoreParametroType;

@Service
public class ProtocollazioneServiceImpl extends BaseServiceImpl<ProtocollazioneCommand, String> implements ProtocollazioneService {

    private static final Logger log = LoggerFactory.getLogger(ProtocollazioneServiceImpl.class);
    private static final String proto_temp_unzip_dir = "PROT_ALLEGATI/";
    private DocumentiHelperService documentiHelperService;
    private IstanzeDAO istanzeDAO;
    private IstanzeService istanzeService;
    private MovimentiService movimentiService;
    private TipologiaregistriService tipologiaregistriService;
    private MailtipoService mailtipoService;
    private OggettiService oggettiService;
    private VerticalizzazioniService verticalizzazioniService;
    private IstanzeeventiService istanzeeventiService;
    private ProtocolloSmistamentiService protocolloSmistamentiService;
    private PecInboxService pecInboxService;
    private AnagrafeService anagrafeService;
    private AlberoprocService alberoprocService;
    private ComuniService comuniService;
    private NlaHelperService nlaHelperService;
    private SoftwareService softwareService;
    private StcWsClient stcWsClient;
    private ContenttypesService contenttypesService;
    private TempLinkallegatiService tempLinkallegatiService;
    private MovimentiallegatiService movimentiallegatiService;
    private AlberoprocProtocolloService alberoprocProtocolloService;
    private AmministrazioniService amministrazioniService;
    private AmministrProtocolloService amministrazioniProtocolloService;
    private MovimentiZipLogicoService movimentiZipLogicoService;
    private IEventPublisher eventPublisher;
    private OggettiMetadatiService oggettiMetadatiService;
    private IProtocollazioneDAO protocollazioneDAO;
    private IProtocollazioneAutorizzazioneService protocollazioneAutorizzazioniService;
    private TipisoggettopeopleService tipisoggettopeopleService;
    private TipimovStcMappingService tipimovStcMappingService;
    private ProtocolloConfigurazioneService protConfigService;
    private IProtocolloWSFactory iProtocolloWSFactory;

    @Autowired
    public void setEventPublisher(IEventPublisher eventPublisher) {

	this.eventPublisher = eventPublisher;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setAmministrazioniProtocolloService(AmministrProtocolloService amministrazioniProtocolloService) {

	this.amministrazioniProtocolloService = amministrazioniProtocolloService;
    }

    @Autowired
    public void setAlberoprocProtocolloService(AlberoprocProtocolloService alberoprocProtocolloService) {

	this.alberoprocProtocolloService = alberoprocProtocolloService;
    }

    @Autowired
    public void setDocumentiHelperService(DocumentiHelperService documentiHelperService) {

	this.documentiHelperService = documentiHelperService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setIstanzeDAO(IstanzeDAO istanzeDAO) {

	this.istanzeDAO = istanzeDAO;
    }

    @Autowired
    public void setTempLinkallegatiService(TempLinkallegatiService tempLinkallegatiService) {

	this.tempLinkallegatiService = tempLinkallegatiService;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setMovimentiZipLogicoService(MovimentiZipLogicoService movimentiZipLogicoService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
    }

    @Autowired
    public void setOggettiMetadatiService(OggettiMetadatiService oggettiMetadatiService) {

	this.oggettiMetadatiService = oggettiMetadatiService;
    }

    @Autowired
    public void setProtocollazioneDAO(IProtocollazioneDAO protocollazioneDAO) {

	this.protocollazioneDAO = protocollazioneDAO;
    }

    @Autowired
    public void setProtocollazioneAutorizzazioniService(IProtocollazioneAutorizzazioneService protocollazioneAutorizzazioniService) {

	this.protocollazioneAutorizzazioniService = protocollazioneAutorizzazioniService;
    }

    @Autowired
    public void setProtocolloSmistamentiService(ProtocolloSmistamentiService protocolloSmistamentiService) {

	this.protocolloSmistamentiService = protocolloSmistamentiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setTipologiaregistriService(TipologiaregistriService tipologiaregistriService) {

	this.tipologiaregistriService = tipologiaregistriService;
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
    public void setPecInboxService(PecInboxService pecInboxService) {

	this.pecInboxService = pecInboxService;
    }

    @Autowired
    public void setStcWsClient(StcWsClient stcWsClient) {

	this.stcWsClient = stcWsClient;
    }

    @Autowired
    public void setContenttypesService(ContenttypesService contenttypesService) {

	this.contenttypesService = contenttypesService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setNlaHelperService(NlaHelperService nlaHelperService) {

	this.nlaHelperService = nlaHelperService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setTipisoggettopeopleService(TipisoggettopeopleService tipisoggettopeopleService) {

	this.tipisoggettopeopleService = tipisoggettopeopleService;
    }

    @Autowired
    public void setTipimovStcMappingService(TipimovStcMappingService tipimovStcMappingService) {

	this.tipimovStcMappingService = tipimovStcMappingService;
    }

    @Autowired
    public void setProtConfigService(ProtocolloConfigurazioneService protConfigService) {

	this.protConfigService = protConfigService;
    }

    @Autowired
    public void setiProtocolloWSFactory(IProtocolloWSFactory iProtocolloWSFactory) {

	this.iProtocolloWSFactory = iProtocolloWSFactory;
    }

    @Override
    protected Class<ProtocollazioneCommand> getEntityClass() {

	return ProtocollazioneCommand.class;
    }

    @Override
    public void insert(ProtocollazioneCommand entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(ProtocollazioneCommand entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(ProtocollazioneCommand entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<ProtocollazioneCommand> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public ProtocollazioneCommand findById(String id) {

	throw new NotImplementedException();
    }

    @Override
    public ProtocollazioneCommand bindDomainObject(ProtocollazioneCommand entity, Class<?> idClass, String idPath) {

	throw new NotImplementedException();
    }

    @Override
    public DatiProtocolloAnnullatoResponseType isAnnullato(String token, String fkidProtocollo, String numeroprotocollo, Date dataProtocollo,
	    String software, String codiceComune) throws VerificaProtocolloAnnullatoException {

	try {
	    String annoProtocollo = getAnnoProtocollo(dataProtocollo);
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    DatiProtocolloAnnullatoResponseType result = port.isAnnullato(token, fkidProtocollo, annoProtocollo, numeroprotocollo, software,
		    codiceComune);
	    gestisciErroreType(result.getErrore(), true, "isAnnullato");
	    log.debug("isAnnullato: result [{}]", result);
	    return result;
	} catch (Exception e) {
	    log.error("isAnnullato: {}", e.getMessage());
	    throw new VerificaProtocolloAnnullatoException(e);
	}
    }

    @Override
    public DatiProtocolloLettoResponseType leggiProtocollo(String token, Istanze istanza) {

	return leggiProtocolloConData(token, istanza.getNumeroprotocollo(), istanza.getDataprotocollo(), istanza.getFkidprotocollo(),
		istanza.getSoftware().getCodice(), istanza.getComune().getCodicecomune());
    }

    @Override
    public DatiProtocolloLettoResponseType leggiProtocollo(String token, Movimenti movimento) {

	return leggiProtocolloConData(token, movimento.getNumeroprotocollo(), movimento.getDataprotocollo(), movimento.getFkidprotocollo(),
		movimento.getIstanza().getSoftware().getCodice(), movimento.getIstanza().getComune().getCodicecomune());
    }

    @Override
    public DatiProtocolloLettoResponseType leggiProtocolloForView(String token, Movimenti movimento) {

	//Mi serve questo metodo perché sennò non mi dà errori sui caricamenti LAZY
	try {
	    return leggiProtocollo(token, movimento);
	} catch (Exception e) {
	    log.error("errore durante leggiProtocolloForView ", e);
	    DatiProtocolloLettoResponseType resp = new DatiProtocolloLettoResponseType();
	    resp.setErrore(new ErroreProtocolloType());
	    resp.getErrore().setDescrizione(e + "");
	    resp.getErrore().setStackTrace(e + ""); //Per ora non ci serve lo stackTrace
	    return resp;
	}
    }

    @Override
    public DatiProtocolloLettoResponseType leggiProtocollo(String token, PecInbox pec, String codiceComune, String software) {

	String annoProtocollo = getAnnoProtocollo(pec.getDataprotocollo());
	return leggiProtocollo(token, pec.getNumeroprotocollo(), annoProtocollo, pec.getIdprotocollo(), software, codiceComune);
    }

    private void gestisciErroreType(ErroreProtocolloType err, boolean rilanciaEccezione, String nomeMetodo) throws FunzioneBusinessRemotaException {

	if (err != null) {
	    log.error("errore durante la chiamata al servizio di protocollazione {}: {}, \n stackTrace: {}",
		    new String[] { nomeMetodo, err.getDescrizione(), err.getStackTrace() });
	    if (rilanciaEccezione) {
		throw new FunzioneBusinessRemotaException(err.getDescrizione());
	    }
	}
    }

    @Override
    public DatiProtocolloResponseType protocolla(ProtocolloSourceEnum source, ProtocollazioneCommand protocollazioneCommand, String software,
	    String codiceComune) throws ProtocollazioneFallitaException {

	try {
	    validateCommand(protocollazioneCommand, true);
	} catch (ConfigurationException e) {
	    throw new ProtocollazioneFallitaException(e);
	}
	String token = protocollazioneCommand.getToken();
	Istanze istanza = null;
	if (EntityUtils.getNestedProperty(protocollazioneCommand.getEntity(), "id.codice") != null) {
	    istanza = istanzeService.findById(new PkId(protocollazioneCommand.getEntity().getId().getCodice()));
	}
	Movimenti movimento = null;
	if (EntityUtils.getNestedProperty(protocollazioneCommand.getMovimento(), "id.codice") != null) {
	    movimento = movimentiService.findById(new PkId(protocollazioneCommand.getMovimento().getId().getCodice()));
	    protocollazioneCommand.setMovimento(movimento);
	}
	protocollazioneCommand.setEntity(istanza);
	if (EntityUtils.getNestedProperty(protocollazioneCommand.getPec(), "id.id") != null) {
	    PecInbox pec = this.pecInboxService.findById(new PecInboxId(protocollazioneCommand.getPec().getId().getId()));
	    protocollazioneCommand.setPec(pec);
	}
	IVerticalizzazioneProtocolloAttivoService protAttivoService = new VerticalizzazioneProtocolloAttivoServiceImpl(this.verticalizzazioniService,
		codiceComune);
	DatiRequestType sFile = generaXml(protocollazioneCommand, istanza, protAttivoService, codiceComune, software);
	DatiProtocolloResponseType datiProtocollo = null;
	try {
	    //IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    IProtocollazioneService port = this.iProtocolloWSFactory.createPort();
	    if (BooleanUtils.isTrue(protocollazioneCommand.getMettiAllaFirma())) {
		// chiama ws metti alla firma
		datiProtocollo = port.mettiAllaFirmaXml(token, String.valueOf(movimento.getId().getCodice()), sFile);
		// inserisci eventi
		istanzeeventiService.insert("In attesa di notifica invio mail", IstanzeeventiConstants.CATEGORIA_MAIL, movimento, null);
		istanzeeventiService.insert("In attesa di notifica protocollo", IstanzeeventiConstants.CATEGORIA_PROTOCOLLO, movimento, null);
		istanzeeventiService.insert("In attesa di notifica firma", IstanzeeventiConstants.CATEGORIA_FIRMA, movimento, null);
	    } else {
		if (protocollazioneCommand.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
		    log.info("protocolla# Call protocollazioneIstanzaXml. Codice Istanza {} ", istanza.getId().getCodice());
		    datiProtocollo = port.protocollazioneIstanzaXml(token, String.valueOf(istanza.getId().getCodice()), sFile);
		    this.istanzeService.clear();
		    log.info("protocolla# Fine chiamata a protocollazioneIstanzaXml. Codice Istanza {} ", istanza.getId().getCodice());
		} else if (protocollazioneCommand.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
		    log.info("protocolla# Call protocollazioneMovimentoXml. Codice movimento {} ", movimento.getId().getCodice());
		    ProtocollazioneMovimentoXmlRequestType request = new ProtocollazioneMovimentoXmlRequestType();
		    request.setToken(token);
		    request.setCodiceMovimento(String.valueOf(movimento.getId().getCodice()));
		    request.setDati(sFile);
		    request.setSource(source.getValue());
		    datiProtocollo = port.protocollazioneMovimentoXml(request);
		    this.istanzeService.clear();
		    log.info("protocolla# Fine chiamata a protocollazioneMovimentoXml. Codice movimento {} ", movimento.getId().getCodice());
		} else if (protocollazioneCommand.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_PEC)) {
		    log.info("protocolla# Call protocollazionePecXml. Codice pec {} ", protocollazioneCommand.getPec().getId().getId());
		    datiProtocollo = port.protocollazionePecXml(token, protocollazioneCommand.getPec().getId().getId(), sFile);
		    this.istanzeService.clear();
		    log.info("protocolla# Fine chiamata a protocollazionePecXml. Codice pec {} ", protocollazioneCommand.getPec().getId().getId());
		} else {
		    log.info("protocolla# Call protocollazioneXml.");
		    datiProtocollo = port.protocollazioneXml(token, software, sFile, codiceComune);
		    this.istanzeService.clear();
		    log.info("protocolla# Fine chiamata a protocollazioneXml.");
		}
	    }
	    if (datiProtocollo != null) {
		gestisciErroreType(datiProtocollo.getErrore(), true, "protocolla");
		Set<Integer> documenti = new HashSet<Integer>();
		if (datiProtocollo != null && datiProtocollo.getCodiciOggettoAllegati() != null) {
		    documenti.addAll(datiProtocollo.getCodiciOggettoAllegati().getInt());
		}
		if (protocollazioneCommand.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
		    if (lanciaEventoProtocollazioneEseguita(datiProtocollo.getNumeroProtocollo())) {
			this.eventPublisher.publish(new EventoIstanzaProtocollata(protocollazioneCommand.getEntity().getId().getCodice(), documenti));
			this.setStampigliaturaProt(null, istanza, datiProtocollo);
		    }
		    gestisciRiferimentiProtocolloInPECInbox(istanza, null, datiProtocollo);
		} else if (protocollazioneCommand.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
		    if (lanciaEventoProtocollazioneEseguita(datiProtocollo.getNumeroProtocollo())) {
			this.eventPublisher
				.publish(new EventoMovimentoProtocollato(protocollazioneCommand.getMovimento().getId().getCodice(), documenti));
			this.setStampigliaturaProt(movimento, null, datiProtocollo);
		    }
		    gestisciRiferimentiProtocolloInPECInbox(null, movimento, datiProtocollo);
		}
		if (StringUtils.isNotBlank(datiProtocollo.getWarning())) {
		    String warning = "La protocollazione ha generato il seguente warning: " + datiProtocollo.getWarning();
		    log.warn(warning);
		    FlashMessages.getWarnings().add(warning);
		    if (protocollazioneCommand.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
			inserisciEvento(istanza, null, warning);
		    } else if (protocollazioneCommand.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
			inserisciEvento(null, movimento, warning);
		    }
		}
	    }
	    Verticalizzazioniparametri gestisciFacicolo = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTISCI_FASCICOLAZIONE,
		    protocollazioneCommand.getComune().getCodicecomune());
	    if (gestisciFacicolo != null && StringUtils.isNotBlank(gestisciFacicolo.getValore()) && gestisciFacicolo.getValore().equalsIgnoreCase("1")
		    && !protocollazioneCommand.isForzaNonFascicolareInProtocollazioneXML()) {
		try {
		    if (protocollazioneCommand.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
			this.fascicolaIstanzaXml(token, protocollazioneCommand);
		    } else if (protocollazioneCommand.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
			this.fascicolaMovimentoXml(token, protocollazioneCommand);
		    } else {
			// devo settare i dati che mi ritorna dal protocollo perchè verranno usati in questo tipo di fascicolazione
			protocollazioneCommand.setDatiProtocollo(datiProtocollo);
			this.fascicolaXml(protocollazioneCommand);
		    }
		} catch (Exception e) {
		    String warning = "La fascicolazione ha generato il seguente warning: " + e.getMessage();
		    FlashMessages.getWarnings().add(warning);
		    log.error("protocolla: Errore nella fascicolazione dell'istanza [{}]", e.getMessage());
		}
	    }
	    /*
	     * LION 2013-10-29: nel caso di protocollazione PEC vengono salvati in PEC_INBOX i riferimenti al protocollo appena generato
	     * e la PEC che prima era in carico all'operatore ora può di nuovo essere presa in carico da altri operatori per altre operazioni.
	     */
	    if (protocollazioneCommand.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_PEC)) {
		PecInbox pec = protocollazioneCommand.getPec();
		if (pec != null) {
		    pec.setIdprotocollo(datiProtocollo.getIdProtocollo());
		    pec.setNumeroprotocollo(datiProtocollo.getNumeroProtocollo());
		    Date dataProt = null;
		    if (StringUtils.isNotBlank(datiProtocollo.getDataProtocollo())) {
			dataProt = Utilities.parseDateString(datiProtocollo.getDataProtocollo(), false);
		    }
		    pec.setDataprotocollo(dataProt);
		    pec.setResponsabili(null);
		    if (protocollazioneCommand.getProtSoftware() != null) {
			if (StringUtils.isNotBlank(protocollazioneCommand.getProtSoftware().getCodice())) {
			    Software softwareProt = softwareService.findById(protocollazioneCommand.getProtSoftware().getCodice());
			    pec.setSoftwareProt(softwareProt);
			}
			if (protocollazioneCommand.getComune() != null
				&& StringUtils.isNotBlank(protocollazioneCommand.getComune().getCodicecomune())) {
			    Comuni comuniProt = comuniService.findById(protocollazioneCommand.getComune().getCodicecomune());
			    pec.setComuniProt(comuniProt);
			}
		    }
		    this.pecInboxService.update(pec);
		    /*
		     * se sto protocollando una PEC dopo la creazione del protocollo 
		     * riporto gli estremi del protocollo sul movimento o sull'istanza che sono associati alla PEC
		     */
		    Istanze pecInst = pec.getIstanze();
		    if (null != pecInst && pecInst.getId() != null && pecInst.getId().getCodice() != null) {
			// in caso di protocollazione da TT devo settare ORMHelper corretto per l'istanza altrimenti da errore.
			String softwareCorrente = ORMHelper.getSoftware();
			try {
			    pecInst = istanzeService.findById(new PkId(pecInst.getId().getCodice()));
			    ORMHelper.setSoftware(pecInst.getSoftware().getCodice());
			    pecInst.setFkidprotocollo(datiProtocollo.getIdProtocollo());
			    pecInst.setNumeroprotocollo(datiProtocollo.getNumeroProtocollo());
			    pecInst.setDataprotocollo(dataProt);
			    this.istanzeService.update(pecInst);
			} catch (Exception e) {
			    log.error("protocolla# Errore nell'aggiornamento dei riferimenti di protocollazione dell'istanza: {}", e.getMessage(), e);
			} finally {
			    ORMHelper.setSoftware(softwareCorrente);
			}
		    } else {
			Movimenti pecMov = pec.getMovimenti();
			if (null != pecMov && null != pecMov.getId() && null != pecMov.getId().getCodice()) {
			    String softwareCorrente = ORMHelper.getSoftware();
			    try {
				pecMov = movimentiService.findById(new PkId(pecMov.getId().getCodice()));
				ORMHelper.setSoftware(pecMov.getIstanza().getSoftware().getCodice());
				pecMov.setFkidprotocollo(datiProtocollo.getIdProtocollo());
				pecMov.setNumeroprotocollo(datiProtocollo.getNumeroProtocollo());
				pecMov.setDataprotocollo(dataProt);
				this.movimentiService.update(pecMov);
			    } catch (Exception e) {
				log.error("protocolla# Errore nell'aggiornamento dei riferimenti di protocollazione del movimento: {}", e);
			    } finally {
				ORMHelper.setSoftware(softwareCorrente);
			    }
			}
		    }
		} else {
		    throw new ProtocollazioneFallitaException(
			    "protocolla() - impossibile salvare i riferimenti al protocollo in PEC_INBOX perchè manca l'id della PEC da aggiornare");
		}
	    }
	    // END LION
	    return datiProtocollo;
	} catch (Exception e) {
	    log.error("protocolla: {}", e.getMessage());
	    throw new ProtocollazioneFallitaException("Errore nella chiamata al webservice protocolla=" + e.getMessage(), e);
	}
    }

    private void setStampigliaturaProt(Movimenti movimento, Istanze istanza, DatiProtocolloResponseType datiProtocollo) {

	if (this.isAttivaStampigliatura(datiProtocollo)) {
	    List<Integer> codiciOggetto = datiProtocollo.getCodiciOggettoAllegati().getInt();
	    for (Integer doc : codiciOggetto) {
		Oggetti oggetto = oggettiService.findById(new PkId(doc));
		if (oggetto != null && oggetto.getId() != null && oggetto.getId().getCodice() != null && oggetto.getNomefile().contains(".pdf")
			&& !oggetto.getNomefile().contains(".p7m")) {
		    if (movimento != null) {
			setStampigliaturaProtMovimento(movimento, oggetto, datiProtocollo);
		    }
		    if (istanza != null) {
			setStampigliaturaProtIstanza(istanza, oggetto, datiProtocollo);
		    }
		}
	    }
	}
    }

    private boolean isAttivaStampigliatura(DatiProtocolloResponseType datiProtocollo) {

	boolean attivaStampigliatura = false;
	log.debug("setStampigliaturaDataProt# avvio stampigliatura...");
	boolean isAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF);
	log.debug("setStampigliaturaDataProt# verifico se la verticalizzazione {} è attiva: valore - {}",
		WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF, isAttiva);
	if (isAttiva) {
	    String isProtLayer = new VerticalizzazioneProtocolloAttivoServiceImpl(verticalizzazioniService, ORMHelper.getIdcomune())
		    .isAttivoApplicaLayer();
	    attivaStampigliatura = isProtLayer.equalsIgnoreCase("S") && datiProtocollo.getCodiciOggettoAllegati() != null;
	}
	return attivaStampigliatura;
    }

    private void setStampigliaturaProtMovimento(Movimenti movimento, Oggetti oggetto, DatiProtocolloResponseType datiProtocollo) {

	movimento.setNumeroprotocollo(datiProtocollo.getNumeroProtocollo());
	movimento.setDataprotocollo(Utilities.parseDateString(datiProtocollo.getDataProtocollo(), WebConstants.DATE_FORMAT_PATTERN));
	movimentiService.update(movimento);
	Oggetti no = movimentiallegatiService.applicaAnnotazioneProtocolloPdf(null, movimento, oggetto);
	log.debug("setStampigliaturaDataProt# oggetto n {} aggiornato", no.getNomefile());
	oggettiService.update(no);
	istanzeDAO.flush();
    }

    private void setStampigliaturaProtIstanza(Istanze istanza, Oggetti oggetto, DatiProtocolloResponseType datiProtocollo) {

	istanza.setDataprotocollo(Utilities.parseDateString(datiProtocollo.getDataProtocollo(), WebConstants.DATE_FORMAT_PATTERN));
	istanza.setNumeroprotocollo(datiProtocollo.getNumeroProtocollo());
	istanzeService.update(istanza);
	Oggetti no = movimentiallegatiService.applicaAnnotazioneProtocolloPdf(istanza, null, oggetto);
	log.debug("setStampigliaturaDataProt# oggetto n {} aggiornato", no.getNomefile());
	oggettiService.update(no);
	istanzeDAO.flush();
    }

    @Override
    public DatiProtocolloResponseType protocollaIstanza(Istanze entity, String token, TipoInserimento tipoInserimento,
	    DatiMittentiType mittentiDaSovrascrivere) {

	String codicecomune = entity.getComune().getCodicecomune();
	boolean protocolloAttivo = new VerticalizzazioneProtocolloAttivoServiceImpl(verticalizzazioniService, codicecomune).isAttiva();
	if (!protocolloAttivo) {
	    return null;
	}
	log.debug("protocollaIstanza: la Verticalizzazione di protocollo è attiva");
	if (StringUtils.isBlank(entity.getNumeroprotocollo())) {
	    log.debug("protocollaIstanza: devo effettuare la protocollazione per l'istanza {}", entity.getId());
	    Integer codiceIstanza = entity.getId().getCodice();
	    try {
		log.debug("childDataInsert: chiamo il servizio di protocollazione per l'istanza: [{}], token: [{}], tipoProtocollazione: [{}]",
			new Object[] { codiceIstanza, token, tipoInserimento });
		IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
		DatiProtocolloResponseType datiProtocolloString = port.protocollazioneIstanza(token, codiceIstanza.toString(),
			tipoInserimento.value(), mittentiDaSovrascrivere);
		this.istanzeService.clear();
		log.debug("protocollaIstanza: protocollazione effettuata correttamente dati tornati: {}", datiProtocolloString);
		if (datiProtocolloString != null) {
		    gestisciErroreType(datiProtocolloString.getErrore(), true, "protocollaIstanza");
		    gestisciRiferimentiProtocolloInPECInbox(entity, null, datiProtocolloString);
		    if (StringUtils.isNotBlank(datiProtocolloString.getWarning())) {
			inserisciEvento(entity, null,
				"Errore durante la protocollazione dell'istanza. ProtocollazioneService#protocollaIstanza[WARNING: " +
						      datiProtocolloString.getWarning() + "]");
		    }
		}
		if (lanciaEventoProtocollazioneEseguita(datiProtocolloString.getNumeroProtocollo())) {
		    // Gestire l'evento istanzaProtocollata
		    Set<Integer> documenti = new HashSet<Integer>();
		    if (datiProtocolloString != null && datiProtocolloString.getCodiciOggettoAllegati() != null) {
			documenti.addAll(datiProtocolloString.getCodiciOggettoAllegati().getInt());
		    }
		    this.eventPublisher.publish(new EventoIstanzaProtocollata(codiceIstanza, documenti));
		    setStampigliaturaProt(null, entity, datiProtocolloString);
		}
		return datiProtocolloString;
	    } catch (Exception e) {
		log.debug("protocollaIstanza# Aggiorno l'istanza = {} con il campo tipoProtFallita = {}", entity.getNumeroistanza(),
			tipoInserimento.name());
		String val = verticalizzazioniService.getVerticalizzazioniparametriValore(
			VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
			VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_SEGNA_PROT_AUT_KO);
		if (StringUtils.isNotBlank(val) && WebConstants.S.equals(val)) {
		    log.debug("protocollaIstanza# Segno protocollazione automatica fallita = {}", val);
		    istanzeService.updateTipoProtFallita(codiceIstanza, tipoInserimento.value().toString());
		}
		DatiProtocolloResponseType datiProtocolloString = new DatiProtocolloResponseType();
		ErroreProtocolloType erroreProtocolloType = new ErroreProtocolloType();
		erroreProtocolloType.setDescrizione(e.getMessage());
		datiProtocolloString.setErrore(erroreProtocolloType);
		inserisciEvento(entity, null,
			"Errore durante la protocollazione dell'istanza. ProtocollazioneService#protocollaIstanza[" + e.getMessage() + "]");
		log.error("childDataInsert: Impossibile contattare il servizio di protocollazione a causa: {}", e.getMessage());
		List<InvalidValue> ivs = new ArrayList<InvalidValue>();
		ivs.add(new InvalidValue("Impossibile contattare il servizio di protocollazione a causa: " + e.getMessage(), entity.getClass(),
			"entity.numeroprotocollo", "", entity));
		return datiProtocolloString;
	    }
	}
	log.debug(
		"protocollaIstanza: è stato passato il numero di protocollo, controllo se sono stati passati dataprotocollo e fkidprotocollo per l'istanza {}",
		entity.getId());
	// controllo se i dati del protocollo sono corretti
	if (entity.getDataprotocollo() == null) {
	    log.error("protocollaIstanza: Dati di protocollazione incompleti: La data non è stata passata");
	    throw new BusinessValidationException("Dati di protocollazione incompleti: La data non è stata passata");
	}
	log.debug("protocollaIstanza: La data è stata passata");
	// se il campo fkidprotocollo è nullo allora lo devo andare a recuperare
	if (!StringUtils.isBlank(entity.getFkidprotocollo()) || !isLeggiProtocollo(codicecomune)) {
	    return null;
	}
	log.debug("protocollaIstanza: Il campo fkidprotocollo non è stato passato, devo leggere il servizio di protocollo per ricavarlo");
	DatiProtocolloLettoResponseType prot = this.leggiProtocollo(token, entity);
	log.debug("protocollaIstanza: protocollo letto aggiorno l'istanza");
	String idprotocollo = prot.getIdProtocollo();
	entity.setFkidprotocollo(idprotocollo);
	istanzeService.update(entity);
	return null;
    }

    /*
    @Override
    public DatiProtocolloResponseType protocollaIstanza(Istanze entity, String token, TipoInserimento tipoInserimento,
        DatiMittentiType mittentiDaSovrascrivere) throws ProtocollaIstanzaException {
    
    String codicecomune = entity.getComune().getCodicecomune();
    VerticalizzazioneProtocolloAttivoServiceImpl vertProtocolloAttivo = new VerticalizzazioneProtocolloAttivoServiceImpl(verticalizzazioniService,
    	codicecomune);
    boolean protocolloAttivo = vertProtocolloAttivo.isAttiva();
    if (!protocolloAttivo) {
        return null;
    }
    log.debug("protocollaIstanza: la Verticalizzazione di protocollo è attiva");
    if (StringUtils.isBlank(entity.getNumeroprotocollo())) {
        log.debug("protocollaIstanza: devo effettuare la protocollazione per l'istanza {}", entity.getId());
        Integer codiceIstanza = entity.getId().getCodice();
        try {
    	log.debug("childDataInsert: chiamo il servizio di protocollazione per l'istanza: [{}], token: [{}], tipoProtocollazione: [{}]",
    		new Object[] { codiceIstanza, token, tipoInserimento });
    	IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
    	//TODO: Passare alla chiamata come quella commentata perchè il protocollo verrà staccato dal db del backoffice
    	ProtocollazioneCommand protocollazioneCommand = new ProtocollazioneCommand();
    	protocollazioneCommand.setProvenienza(ProtocollazioneCommand.PROVENIENZA_ISTANZA);
    	//Con ProtocollazioneCommand.PROVENIENZA_ISTANZA il flusso deve essere in ARRIVO
    	protocollazioneCommand.setFlusso(ProtocollazioneCommand.FLUSSO_ARRIVO);
    	//Recupero il tipo documento configurato nell'albero 
    	Alberoproc ap = this.alberoprocService.findById(new PkId(entity.getAlberoproc().getId().getCodice()));
    	AlberoprocHelper helper = alberoprocService.findAlberoprocHelper(ap);
    	String tipoDocumento = getTipoDocumento(helper, codicecomune);
    	protocollazioneCommand.setTipoDocumento(tipoDocumento);
    	//Recupero lo smistamento di default
    	String smistamento = this.findProtocolloSmistamentoDefault(codicecomune, entity.getSoftware().getCodice());
    	protocollazioneCommand.setSmistamento(smistamento);
    	//Recupero l'oggetto del protocollo
    	Map<String, AlberoprocProtocollo> configurazioniProtocollo = this.alberoprocProtocolloService
    		.findConfigurazioniHelper(entity.getAlberoproc().getId().getCodice());
    	for (Map.Entry<String, AlberoprocProtocollo> config : configurazioniProtocollo.entrySet()) {
    	    if (config.getValue().getTestoProtocollo().getId().getCodice() != null) {
    		Integer codiceTipoOggettoProtocollo = config.getValue().getTestoProtocollo().getId().getCodice();
    		Mailtipo testoTipoOggettoProtocollo = this.mailtipoService.findById(new PkId(codiceTipoOggettoProtocollo));
    		Mailtipo testoTipoSostituito = this.mailtipoService.replaceOggettoCorpo(testoTipoOggettoProtocollo, entity, null);
    		protocollazioneCommand.setOggetto(testoTipoSostituito.getOggetto());
    		break;
    	    }
    	}
    	//Recupero la classifica
    	String classifica = this.getClassifica(helper, codicecomune);
    	protocollazioneCommand.setClassifica(classifica);
    	//Recupero il mezzo di default
    	ProtocolloMezzi mezzoDefault = vertProtocolloAttivo.getMezzoDefault();
    	//Recupero la modalita invio di default
    	ProtocolloModalitainvio modalitaInvioDefault = vertProtocolloAttivo.getModalitaTrasmissioneDefault();
    	//Mittenti -> Converto DatiMittentiType in ProtocolloSoggettoCommand
    	if (mittentiDaSovrascrivere != null) {
    	    if (mittentiDaSovrascrivere.getAmministrazione() != null
    		    && mittentiDaSovrascrivere.getAmministrazione().getDatiAnagraficiType() != null) {
    		for (DatiAnagraficiType amministrazione : mittentiDaSovrascrivere.getAmministrazione().getDatiAnagraficiType()) {
    		    if (StringUtils.isNotBlank(amministrazione.getCod())) {
    			Amministrazioni amm = this.amministrazioniService.findById(new PkId(Integer.parseInt(amministrazione.getCod())));
    			ProtocolloMezzi mezzo = StringUtils.isNotBlank(amministrazione.getMezzo())
    				? new ProtocolloMezzi(new PkId(Integer.parseInt(amministrazione.getMezzo())))
    				: mezzoDefault;
    			ProtocolloModalitainvio modInvio = StringUtils.isNotBlank(amministrazione.getModalitaTrasmissione())
    				? new ProtocolloModalitainvio(new PkId(Integer.parseInt(amministrazione.getModalitaTrasmissione())))
    				: modalitaInvioDefault;
    			protocollazioneCommand.getMittentis().add(ProtocolloSoggettoCommand.fromAmministrazione(amm, mezzo, modInvio));
    		    }
    		}
    	    }
    	    if (mittentiDaSovrascrivere.getAnagrafe() != null && mittentiDaSovrascrivere.getAnagrafe().getDatiAnagraficiType() != null) {
    		for (DatiAnagraficiType anagrafe : mittentiDaSovrascrivere.getAnagrafe().getDatiAnagraficiType()) {
    		    if (StringUtils.isNotBlank(anagrafe.getCod())) {
    			Anagrafe anag = this.anagrafeService.findById(new PkId(Integer.parseInt(anagrafe.getCod())));
    			ProtocolloMezzi mezzo = StringUtils.isNotBlank(anagrafe.getMezzo())
    				? new ProtocolloMezzi(new PkId(Integer.parseInt(anagrafe.getMezzo())))
    				: mezzoDefault;
    			ProtocolloModalitainvio modInvio = StringUtils.isNotBlank(anagrafe.getModalitaTrasmissione())
    				? new ProtocolloModalitainvio(new PkId(Integer.parseInt(anagrafe.getModalitaTrasmissione())))
    				: modalitaInvioDefault;
    			protocollazioneCommand.getMittentis().add(ProtocolloSoggettoCommand.fromRichiedente(null, null, null, null));
    		    }
    		}
    	    }
    	}
    	//Se non ci sono mittenti da sovrascrivere allora recupero i mittenti classici dell'istanza
    	if (protocollazioneCommand.getMittentis().isEmpty()) {
    	    ProtMittDestAutoResolverNotAutomatica mittDestResolver = new ProtMittDestAutoResolverNotAutomatica(vertProtocolloAttivo, entity,
    		    tipisoggettopeopleService);
    	    protocollazioneCommand.setMittentis(mittDestResolver.resolveMittDestAnagrafe());
    	}
    	//Destinatario
    	Integer codiceAmministrazione = this.getDestinatario(helper, codicecomune);
    	Amministrazioni ammDestinataria = this.amministrazioniService.findById(new PkId(codiceAmministrazione));
    	ProtocolloSoggettoCommand destinatario = ProtocolloSoggettoCommand.fromAmministrazione(ammDestinataria, mezzoDefault,
    		modalitaInvioDefault);
    	protocollazioneCommand.setDestinatario(destinatario);
    	//
    	DatiRequestType sFile = generaXml(protocollazioneCommand, entity, vertProtocolloAttivo, codicecomune,
    		entity.getSoftware().getCodice());
    	DatiProtocolloResponseType datiProtocolloString = port.protocollazioneIstanzaXml(token, codiceIstanza.toString(), sFile);
    	this.istanzeService.clear();
    	log.debug("protocollaIstanza: protocollazione effettuata correttamente dati tornati: {}", datiProtocolloString);
    	if (datiProtocolloString != null) {
    	    gestisciErroreType(datiProtocolloString.getErrore(), true, "protocollaIstanza");
    	    gestisciRiferimentiProtocolloInPECInbox(entity, null, datiProtocolloString);
    	    if (StringUtils.isNotBlank(datiProtocolloString.getWarning())) {
    		inserisciEvento(entity, null,
    			"Errore durante la protocollazione dell'istanza. ProtocollazioneService#protocollaIstanza[WARNING: " +
    					      datiProtocolloString.getWarning() + "]");
    	    }
    	    if (lanciaEventoProtocollazioneEseguita(datiProtocolloString.getNumeroProtocollo())) {
    		// Gestire l'evento istanzaProtocollata
    		Set<Integer> documenti = new HashSet<Integer>();
    		if (datiProtocolloString.getCodiciOggettoAllegati() != null) {
    		    documenti.addAll(datiProtocolloString.getCodiciOggettoAllegati().getInt());
    		}
    		this.eventPublisher.publish(new EventoIstanzaProtocollata(codiceIstanza, documenti));
    		setStampigliaturaProt(null, entity, datiProtocolloString);
    	    }
    	}
    	return datiProtocolloString;
        } catch (Exception e) {
    	log.debug("protocollaIstanza# Aggiorno l'istanza = {} con il campo tipoProtFallita = {}", entity.getNumeroistanza(),
    		tipoInserimento.name());
    	String val = verticalizzazioniService.getVerticalizzazioniparametriValore(
    		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
    		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_SEGNA_PROT_AUT_KO);
    	if (StringUtils.isNotBlank(val) && WebConstants.S.equals(val)) {
    	    log.debug("protocollaIstanza# Segno protocollazione automatica fallita = {}", val);
    	    istanzeService.updateTipoProtFallita(codiceIstanza, tipoInserimento.value().toString());
    	}
    	DatiProtocolloResponseType datiProtocolloString = new DatiProtocolloResponseType();
    	ErroreProtocolloType erroreProtocolloType = new ErroreProtocolloType();
    	erroreProtocolloType.setDescrizione(e.getMessage());
    	datiProtocolloString.setErrore(erroreProtocolloType);
    	inserisciEvento(entity, null,
    		"Errore durante la protocollazione dell'istanza. ProtocollazioneService#protocollaIstanza[" + e.getMessage() + "]");
    	log.error("childDataInsert: Impossibile contattare il servizio di protocollazione a causa: {}", e.getMessage());
    	return datiProtocolloString;
        }
    }
    log.debug(
    	"protocollaIstanza: è stato passato il numero di protocollo, controllo se sono stati passati dataprotocollo e fkidprotocollo per l'istanza {}",
    	entity.getId());
    // controllo se i dati del protocollo sono corretti
    if (entity.getDataprotocollo() == null) {
        log.error("protocollaIstanza: Dati di protocollazione incompleti: La data non è stata passata");
        throw new ProtocollaIstanzaException("Dati di protocollazione incompleti: La data non è stata passata");
    }
    log.debug("protocollaIstanza: La data è stata passata");
    // se il campo fkidprotocollo è nullo allora lo devo andare a recuperare
    if (!StringUtils.isBlank(entity.getFkidprotocollo()) || !isLeggiProtocollo(codicecomune)) {
        return null;
    }
    log.debug("protocollaIstanza: Il campo fkidprotocollo non è stato passato, devo leggere il servizio di protocollo per ricavarlo");
    DatiProtocolloLettoResponseType prot = this.leggiProtocollo(token, entity);
    log.debug("protocollaIstanza: protocollo letto aggiorno l'istanza");
    String idprotocollo = prot.getIdProtocollo();
    entity.setFkidprotocollo(idprotocollo);
    istanzeService.update(entity);
    return null;
    }
    */
    @Override
    public void protocollaAutorizzazione(int codiceRegistro, int codiceResponsabile, String token, Calendar dataAutorizzazione, Autorizzazioni entity,
	    String codiceComune) throws ProtocollazioneAutorizzazioneException {

	Tipologiaregistri registro = tipologiaregistriService.findById(new PkId(codiceRegistro));
	if (null == registro) {
	    throw new ProtocollazioneAutorizzazioneException("Il registro è nullo");
	}
	if (Boolean.FALSE.equals(BooleanUtils.toBoolean(registro.getTrFlagprotocollo()))) {
	    return;
	}
	try {
	    ProtocollaAutorizzazioneResponse response = protocollazioneAutorizzazioniService.protocolla(registro, codiceComune,
		    ORMHelper.getSoftware(), dataAutorizzazione, entity, codiceResponsabile, token);
	    this.istanzeService.clear();
	    gestisciErroreType(response.getDatiProtocollo().getErrore(), true, "protocollaAutorizzazione");
	    //2. Verifica la presenza dui errori
	    Movimenti movimento = response.getMovimento();
	    DatiProtocolloResponseType datiProtocollo = response.getDatiProtocollo();
	    //3. Parsing della risposta
	    log.debug("protocollaAutorizzazione: rispostaprotocollo {}", response);
	    String numeroProtocollo = datiProtocollo.getNumeroProtocollo();
	    if (log.isDebugEnabled()) {
		log.debug("Numero Protocollo ritornato: {}, data: {}, anno: {}",
			new Object[] { numeroProtocollo, datiProtocollo.getDataProtocollo(), datiProtocollo.getAnnoProtocollo() });
	    }
	    String dataProtocollo = datiProtocollo.getDataProtocollo();
	    SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    Date dataProtocollazione = sdf.parse(dataProtocollo);
	    if (response.getMovimento() != null) {
		movimento.setNumeroprotocollo(numeroProtocollo);
		movimento.setFkidprotocollo(datiProtocollo.getIdProtocollo());
		movimento.setDataprotocollo(dataProtocollazione);
		movimentiService.update(movimento);
	    }
	    entity.setFkidprotocollo(datiProtocollo.getIdProtocollo());
	    entity.setAutorizdata(dataProtocollazione);
	    entity.setDataRilascio(dataProtocollazione);
	    entity.setMovimenti(movimento);
	    entity.setAutoriznumero(numeroProtocollo);
	} catch (Exception e) {
	    log.error("Errore durante la protocollazione: {}", e.getMessage());
	    throw new ProtocollazioneAutorizzazioneException(
		    "Non è stato possibile effettuare la protocollazione della concessione per il registro " + registro.getId().getCodice() +
							     ".\n Errore: " + e.getMessage(),
		    e);
	}
    }

    private DatiRequestType generaXml(ProtocollazioneCommand command, Istanze istanza, IVerticalizzazioneProtocolloAttivoService protAttivoService,
	    String codiceComune, String software) {

	// Verifica se è attivo lo smistamento multiplo, agisce su Flusso INTERNO ed ARRIVO, pemette di inserire più destinatari	
	int smistamento = this.findSmistamentoMultiplo(command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	DatiRequestType bustaProtocollo = new DatiRequestType();
	if (!command.getMetadati().isEmpty()) {
	    ArrayOfMetadatoType protMetadati = new ArrayOfMetadatoType();
	    for (MetadatiBean m : command.getMetadati()) {
		MetadatoType metadato = new MetadatoType();
		metadato.setChiave(m.getChiave());
		metadato.setValore(m.getValore());
		protMetadati.getMetadatoType().add(metadato);
	    }
	    bustaProtocollo.setMetadati(protMetadati);
	}
	bustaProtocollo.setTipoDocumento(command.getTipoDocumento());
	bustaProtocollo.setTipoSmistamento(command.getSmistamento());
	bustaProtocollo.setOggetto(command.getOggetto());
	DatiMailType datiMailType = new DatiMailType();
	datiMailType.setOggetto(command.getOggettoProtocolloMail());
	datiMailType.setCorpo(command.getCorpoProtocolloMail());
	bustaProtocollo.setMail(datiMailType);
	bustaProtocollo.setFlusso(command.getFlusso());
	bustaProtocollo.setClassifica(command.getClassifica());
	this.impostaMittenti(bustaProtocollo, command.getFlusso(), command.getMittente(), command.getMittentis(), istanza, protAttivoService,
		codiceComune, software);
	this.impostaDestinatari(bustaProtocollo, command.getFlusso(), smistamento, command.getDestinatario(), command.getDestinataris(), istanza,
		protAttivoService, codiceComune, software);
	//allegati
	ArrayOfAllegatoType allegati = new ArrayOfAllegatoType();
	Verticalizzazioniparametri noAllegati = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_NOALLEGATI, command.getComune().getCodicecomune());
	boolean gestisciAllegati = true;
	if (noAllegati != null) {
	    String valoreNoAllegati = StringUtils.defaultIfEmpty(noAllegati.getValore(), "0");
	    if (valoreNoAllegati.equalsIgnoreCase("1")) {
		gestisciAllegati = false;
	    }
	}
	// Gestione della modalità invio allegati
	if (gestisciAllegati) {
	    if (command.getFlgProtocollalinkall() != null && command.getFlgProtocollalinkall()
		    && command.getFlusso().equals(ProtocollazioneCommand.FLUSSO_PARTENZA)) {
		log.debug("generaXml# Invio gli allegati come link all'interno di un documento");
		String uuid = UUID.randomUUID().toString();
		log.debug("generaXml# Creo i link e li inserisco nella tabella");
		creaLinkAndInsertInTempLinkAllegati(command, uuid);
		log.debug("generaXml# Creo l'oggetto contenete i link creati");
		Letteretipo lettereTipo = command.getLetteraTipoAllegati();
		Integer codiceOggettoCreato = oggettiService.creaDocumentoConLinkOggetti(lettereTipo,
			command.getMovimento().getIstanza().getId().getCodice(), command.getMovimento().getId().getCodice(),
			command.getMovimento().getTipomovimento().getId().getTipomovimento(), uuid);
		log.debug("generaXml# associo l'oggetto creato alla busta del protocollo");
		Oggetti oggettoRft = oggettiService.findById(new PkId(codiceOggettoCreato));
		byte[] oggettoPdfByte = oggettiService.trasformRtfInPdf(oggettoRft);
		Oggetti oggettoPdf = new Oggetti();
		oggettoPdf.setDimensioneFile(oggettoPdfByte.length);
		String nomeFile = oggettoRft.getNomefile().replace(".rtf", ".pdf");
		oggettoPdf.setNomefile(nomeFile);
		oggettoPdf.setOggetto(oggettoPdfByte);
		oggettiService.insert(oggettoPdf);
		istanzeDAO.flush();
		istanzeDAO.commit();
		popolaAllegatoContenteLink(oggettoPdf, allegati);
		// Vado a cancellare il file rtf generato e allegato al movimento
		Movimentiallegati movimentiallegati = movimentiallegatiService
			.findMovimentiallegatiConOggettoByMovimenti(command.getMovimento().getId().getCodice(), oggettoRft.getId().getCodice());
		movimentiallegatiService.delete(movimentiallegati);
	    } else {
		log.debug("generaXml# popolo la sezione allegati nella modalità standard inviandoli come attachement");
		popolaAllegatiDaCommand(command, allegati);
	    }
	}
	bustaProtocollo.setAllegati(allegati);
	return bustaProtocollo;
    }

    private void impostaDestinatari(DatiRequestType request, String flusso, Integer smistamento, ProtocolloSoggettoCommand destinatarioCommand,
	    List<ProtocolloSoggettoCommand> destinatariCommand, Istanze istanza, IVerticalizzazioneProtocolloAttivoService protAttivoService,
	    String codiceComune, String software) {

	DatiDestinatariXmlType destinatari = new DatiDestinatariXmlType();
	if (ProtocollazioneCommand.FLUSSO_INTERNO.equalsIgnoreCase(flusso)) {
	    if (smistamento == 1 || smistamento == 3) {
		log.debug("impostaDestinatari# Smistamento multiplo per il flusso {},  attivo. Recupero il destinatario da command.destinataris",
			ProtocollazioneCommand.FLUSSO_INTERNO);
		destinatari = this.populateDestinatari(destinatariCommand, istanza, protAttivoService, codiceComune, software);
	    } else {
		log.debug("impostaDestinatari# Smistamento multiplo per il flusso {}, non attivo. Recupero il destinatario da command.destinatario",
			ProtocollazioneCommand.FLUSSO_ARRIVO);
		Integer codiceDestinatario = destinatarioCommand.getAmministrazioni().getId().getCodice();
		Amministrazioni amministrazione = this.amministrazioniService.findById(new PkId(codiceDestinatario));
		ProtocolloAmministrazioniBuilder builder = new ProtocolloAmministrazioniBuilder(this.amministrazioniProtocolloService);
		ProtocolloAmministrazioni protAmm = builder.build(amministrazione, destinatarioCommand.getMezzo(), destinatarioCommand.getModInvio(),
			codiceComune, software);
		ArrayOfProtocolloAmministrazioni amministrazioni = new ArrayOfProtocolloAmministrazioni();
		amministrazioni.getProtocolloAmministrazioni().add(protAmm);
		destinatari.setAmministrazione(amministrazioni);
	    }
	} else if (ProtocollazioneCommand.FLUSSO_ARRIVO.equalsIgnoreCase(flusso)) {
	    if (smistamento == 1 || smistamento == 2) {
		// il destinatario è solo una amministrazione e viene preso da command.destinataris
		log.debug("impostaDestinatari# Smistamento multiplo per il flusso {},  attivo. Recupero il destinatario da command.destinataris",
			ProtocollazioneCommand.FLUSSO_ARRIVO);
		destinatari = populateDestinatari(destinatariCommand, istanza, protAttivoService, codiceComune, software);
	    } else {
		// il destinatario è solo una amministrazione e viene preso da command.destinatario
		log.debug("impostaDestinatari# Smistamento multiplo per il flusso {}, non attivo. Recupero il destinatario da command.destinatario",
			ProtocollazioneCommand.FLUSSO_ARRIVO);
		Integer codiceDestinatario = destinatarioCommand.getAmministrazioni().getId().getCodice();
		Amministrazioni amministrazione = this.amministrazioniService.findById(new PkId(codiceDestinatario));
		ProtocolloAmministrazioniBuilder builder = new ProtocolloAmministrazioniBuilder(this.amministrazioniProtocolloService);
		ProtocolloAmministrazioni protAmm = builder.build(amministrazione, destinatarioCommand.getMezzo(), destinatarioCommand.getModInvio(),
			codiceComune, software);
		ArrayOfProtocolloAmministrazioni amministrazioni = new ArrayOfProtocolloAmministrazioni();
		amministrazioni.getProtocolloAmministrazioni().add(protAmm);
		destinatari.setAmministrazione(amministrazioni);
	    }
	} else if (ProtocollazioneCommand.FLUSSO_PARTENZA.equalsIgnoreCase(flusso)) {
	    destinatari = populateDestinatari(destinatariCommand, istanza, protAttivoService, codiceComune, software);
	}
	request.setDestinatari(destinatari);
    }

    private void impostaMittenti(DatiRequestType request, String flusso, ProtocolloSoggettoCommand mittenteCommand,
	    List<ProtocolloSoggettoCommand> mittentiCommand, Istanze istanza, IVerticalizzazioneProtocolloAttivoService protAttivoService,
	    String codiceComune, String software) {

	DatiMittentiXmlType mittenti = new DatiMittentiXmlType();
	List<ProtocolloAmministrazioni> amms = new ArrayList<ProtocolloAmministrazioni>();
	List<ProtocolloAnagrafe> anags = new ArrayList<ProtocolloAnagrafe>();
	if (ProtocollazioneCommand.FLUSSO_PARTENZA.equalsIgnoreCase(flusso) || ProtocollazioneCommand.FLUSSO_INTERNO.equalsIgnoreCase(flusso)) {
	    Integer codiceMittente = mittenteCommand.getAmministrazioni().getId().getCodice();
	    if (codiceMittente != null) {
		Amministrazioni amministrazione = this.amministrazioniService.findById(new PkId(codiceMittente));
		ProtocolloAmministrazioniBuilder builder = new ProtocolloAmministrazioniBuilder(amministrazioniProtocolloService);
		ProtocolloAmministrazioni protAmm = builder.build(amministrazione, mittenteCommand.getMezzo(), mittenteCommand.getModInvio(),
			codiceComune, software);
		amms.add(protAmm);
	    }
	} else if (ProtocollazioneCommand.FLUSSO_ARRIVO.equalsIgnoreCase(flusso)) {
	    for (ProtocolloSoggettoCommand soggetto : mittentiCommand) {
		if (EntityUtils.getNestedProperty(soggetto, "amministrazioni.id.codice") != null) {
		    Integer codiceMittente = soggetto.getAmministrazioni().getId().getCodice();
		    if (codiceMittente != null) {
			Amministrazioni amministrazione = this.amministrazioniService.findById(new PkId(codiceMittente));
			ProtocolloAmministrazioniBuilder builder = new ProtocolloAmministrazioniBuilder(amministrazioniProtocolloService);
			ProtocolloAmministrazioni protAmm = builder.build(amministrazione, soggetto.getMezzo(), soggetto.getModInvio(), codiceComune,
				software);
			amms.add(protAmm);
		    }
		} else {
		    if (soggetto != null && soggetto.getAnagrafe() != null && soggetto.getAnagrafe().getId() != null
			    && soggetto.getAnagrafe().getId().getCodice() != null) {
			Integer codiceMittente = soggetto.getAnagrafe().getId().getCodice();
			Anagrafe anagrafe = this.anagrafeService.findById(new PkId(codiceMittente));
			ProtocolloAnagrafeBuilder builder = new ProtocolloAnagrafeBuilder();
			ProtocolloAnagrafe protAnag = builder.build(anagrafe, istanza, soggetto.getMezzo(), soggetto.getModInvio(),
				protAttivoService.getGestionePEC(), soggetto.getEmail());
			anags.add(protAnag);
		    }
		}
	    }
	}
	ArrayOfProtocolloAmministrazioni amministrazioneMit = new ArrayOfProtocolloAmministrazioni();
	ArrayOfProtocolloAnagrafe anagrafeMit = new ArrayOfProtocolloAnagrafe();
	if (!amms.isEmpty()) {
	    amministrazioneMit.getProtocolloAmministrazioni().addAll(amms);
	}
	if (!anags.isEmpty()) {
	    anagrafeMit.getProtocolloAnagrafe().addAll(anags);
	}
	mittenti.setAnagrafe(anagrafeMit);
	mittenti.setAmministrazione(amministrazioneMit);
	request.setMittenti(mittenti);
    }

    private DatiDestinatariXmlType populateDestinatari(List<ProtocolloSoggettoCommand> destinataris, Istanze istanza,
	    IVerticalizzazioneProtocolloAttivoService protAttivoService, String codiceComune, String software) {

	DatiDestinatariXmlType destinatari = new DatiDestinatariXmlType();
	List<ProtocolloAmministrazioni> amms = new ArrayList<ProtocolloAmministrazioni>();
	List<ProtocolloAnagrafe> anags = new ArrayList<ProtocolloAnagrafe>();
	for (ProtocolloSoggettoCommand soggetto : destinataris) {
	    if (EntityUtils.getNestedProperty(soggetto, "amministrazioni.id.codice") != null) {
		Integer codiceDest = soggetto.getAmministrazioni().getId().getCodice();
		if (codiceDest != null) {
		    Amministrazioni amministrazione = this.amministrazioniService.findById(new PkId(codiceDest));
		    ProtocolloAmministrazioniBuilder builder = new ProtocolloAmministrazioniBuilder(amministrazioniProtocolloService);
		    ProtocolloAmministrazioni protAmm = builder.build(amministrazione, soggetto.getMezzo(), soggetto.getModInvio(), codiceComune,
			    software);
		    amms.add(protAmm);
		}
	    } else {
		Integer codiceDest = soggetto.getAnagrafe().getId().getCodice();
		if (codiceDest != null) {
		    Anagrafe anagrafe = this.anagrafeService.findById(new PkId(codiceDest));
		    ProtocolloAnagrafeBuilder builder = new ProtocolloAnagrafeBuilder();
		    ProtocolloAnagrafe protAnag = builder.build(anagrafe, istanza, soggetto.getMezzo(), soggetto.getModInvio(),
			    protAttivoService.getGestionePEC(), soggetto.getEmail());
		    anags.add(protAnag);
		}
	    }
	}
	ArrayOfProtocolloAmministrazioni amministrazioneDest = new ArrayOfProtocolloAmministrazioni();
	ArrayOfProtocolloAnagrafe anagrafeDest = new ArrayOfProtocolloAnagrafe();
	if (!amms.isEmpty()) {
	    amministrazioneDest.getProtocolloAmministrazioni().addAll(amms);
	}
	if (!anags.isEmpty()) {
	    anagrafeDest.getProtocolloAnagrafe().addAll(anags);
	}
	destinatari.setAmministrazione(amministrazioneDest);
	destinatari.setAnagrafe(anagrafeDest);
	return destinatari;
    }

    private void popolaAllegatoContenteLink(Oggetti oggetti, ArrayOfAllegatoType allegati) {

	AllegatoType nuovoAllegato = new AllegatoType();
	nuovoAllegato.setCod(String.valueOf(oggetti.getId().getCodice()));
	nuovoAllegato.setDescrizione(oggetti.getNomefile());
	allegati.getAllegatoType().add(0, nuovoAllegato);
    }

    /**
     * 
     * @param codiceOggetto
     *            : codice della tabella oggetti
     * @param nomeAllegato
     *            : nome dell'allegato sulla tabella allegatiistanzata,movimentiallegati..
     * @param uuid
     */
    private void creaAndInsertTempLinkAllegati(Integer codiceOggetto, String nomeAllegato, String uuid, String descrizioneDocumento) {

	TempLinkallegati tempLinkallegati = new TempLinkallegati();
	tempLinkallegati.setUuid(uuid);
	String link = oggettiService.creaSingoloLinkAllegati(codiceOggetto);
	tempLinkallegati.setLink(link);
	tempLinkallegati.setCodiceoggetto(codiceOggetto);
	tempLinkallegati.setPin(codiceOggetto);
	if (StringUtils.isNotBlank(nomeAllegato)) {
	    tempLinkallegati.setNomedocumento(nomeAllegato);
	} else {
	    tempLinkallegati.setNomedocumento(oggettiService.findNomeByCodice(codiceOggetto));
	}
	tempLinkallegati.setDescrizioneDocumento(descrizioneDocumento);
	tempLinkallegatiService.insert(tempLinkallegati);
	istanzeDAO.flush();
	istanzeDAO.commit();
    }

    private void creaLinkAndInsertInTempLinkAllegati(ProtocollazioneCommand command, String uuid) {

	// RECUPERO L'OGGETTO CONTENENTE TUTTI I DOCUMENTI ALLEGATI 
	DocumentiHelper documentiHelper = command.getDocumentiHelper();
	// GESTIONE DOCUMENTI DELL' ISTANZA 
	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> documentiIstanzas = documentiHelper.getDocumentiIstanzaList();
	for (ChiaveValoreBean<String, List<DocumentiistanzaDTO>> chiaveValoreBeanDocIstanza : documentiIstanzas) {
	    List<DocumentiistanzaDTO> docistanzas = chiaveValoreBeanDocIstanza.getValore();
	    log.debug("popolaAllegatiComeLinkDaCommand# Inserisco i link per i documenti dell'istanza");
	    for (DocumentiistanzaDTO documentiistanza : docistanzas) {
		// Verifico se l'allegato è stato selezionato come da inviare
		if (documentiistanza.getTransientSegnaPerInvio() && documentiistanza.getCodiceOggetto() != null) {
		    Integer codiceOggetto = documentiistanza.getCodiceOggetto();
		    creaAndInsertTempLinkAllegati(codiceOggetto, documentiistanza.getDocumento(), uuid, documentiistanza.getDocumento());
		}
	    }
	}
	// GESTIONE DOCUMENTI DELL' ENDO DELL' ISTANZA 
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> istanzaAllegatiIstanzas = documentiHelper.getDocumentiEndoprocedimentiList();
	for (ChiaveValoreBean<String, List<IstanzeallegatiDTO>> chiaveValoreBeanIstAllegati : istanzaAllegatiIstanzas) {
	    List<IstanzeallegatiDTO> docEndoistanzas = chiaveValoreBeanIstAllegati.getValore();
	    log.debug("popolaAllegatiComeLinkDaCommand# Creo i link per i documenti dell' endo dell'istanza");
	    for (IstanzeallegatiDTO documentiEndo : docEndoistanzas) {
		// Verifico se l'allegato è stato selezionato come da inviare
		if (documentiEndo.isTransientSegnaPerInvio() && documentiEndo.getCodiceOggetto() != null) {
		    Integer codiceOggetto = documentiEndo.getCodiceOggetto();
		    creaAndInsertTempLinkAllegati(codiceOggetto, documentiEndo.getAllegatoextra(), uuid, documentiEndo.getAllegatoextra());
		}
	    }
	}
	// GESTIONE DOCUMENTI DELLE PROCURE DELL' ISTANZA
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> istanzaprocuras = documentiHelper.getIstanzeprocureList();
	for (ChiaveValoreBean<String, List<IstanzeprocureDTO>> chiaveValoreBeanprocure : istanzaprocuras) {
	    List<IstanzeprocureDTO> docProcuras = chiaveValoreBeanprocure.getValore();
	    log.debug("popolaAllegatiComeLinkDaCommand# Creo i link per i documenti delle procure");
	    for (IstanzeprocureDTO documentiProcura : docProcuras) {
		// Verifico se l'allelato è stato selezionato come da inviare
		if (documentiProcura.getTransientSegnaPerInvio()) {
		    if (documentiProcura.getCodiceOggetto() != null) {
			Integer codiceOggetto = documentiProcura.getCodiceOggetto();
			String nomeAllegato = "Documento della procura di " + documentiProcura.getAnagrafeProcuratore().getDescrizioneRichiedente();
			String descrizioneAllegato = nomeAllegato;
			creaAndInsertTempLinkAllegati(codiceOggetto, nomeAllegato, uuid, descrizioneAllegato);
		    }
		    if (documentiProcura.getCodiceOggettoDocId() != null) {
			Integer codiceOggettoDocId = documentiProcura.getCodiceOggettoDocId();
			String nomeAllegato = "Documento di identita' associato alla procura di " +
					      documentiProcura.getAnagrafeProcuratore().getDescrizioneRichiedente();
			String descrizioneAllegato = nomeAllegato;
			creaAndInsertTempLinkAllegati(codiceOggettoDocId, nomeAllegato, uuid, descrizioneAllegato);
		    }
		}
	    }
	}
	// GESTIONE DOCUMENTI DEL MOVIMENTO
	if (command.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
	    List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> documentiMovimentos = documentiHelper.getDocumentiMovimentoList();
	    for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> chiaveValoreBeanDocMovimenti : documentiMovimentos) {
		List<MovimentiallegatiDTO> documentiMovs = chiaveValoreBeanDocMovimenti.getValore();
		log.debug("popolaAllegatiComeLinkDaCommand# Creo i link per i documenti del movimento");
		for (MovimentiallegatiDTO documentiMovimento : documentiMovs) {
		    // Verifico se l'allegato è stato selezionato come da inviare
		    if (documentiMovimento.isTransientSegnaPerInvio() && documentiMovimento.getCodiceOggetto() != null) {
			Integer codiceOggetto = documentiMovimento.getCodiceOggetto();
			creaAndInsertTempLinkAllegati(codiceOggetto, documentiMovimento.getDescrizione(), uuid, documentiMovimento.getDescrizione());
		    }
		}
	    }
	}
	// GESTIONE  DOCUMENTI ALTRI MOVIMENTI
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> documentialtriMovimentos = documentiHelper.getDocumentiAltriMovimentiList();
	for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> chiaveValoreBeanAltriDocMovimenti : documentialtriMovimentos) {
	    List<MovimentiallegatiDTO> documentiAltriMovs = chiaveValoreBeanAltriDocMovimenti.getValore();
	    log.debug("popolaAllegatiComeLinkDaCommand# Creo i link per i documenti di altri movimenti");
	    for (MovimentiallegatiDTO documentiAltroMovimento : documentiAltriMovs) {
		// Verifico se l'allegato è stato selezionato come da inviare
		if (documentiAltroMovimento.isTransientSegnaPerInvio() && documentiAltroMovimento.getCodiceOggetto() != null) {
		    Integer codiceOggetto = documentiAltroMovimento.getCodiceOggetto();
		    creaAndInsertTempLinkAllegati(codiceOggetto, documentiAltroMovimento.getDescrizione(), uuid,
			    documentiAltroMovimento.getDescrizione());
		}
	    }
	}
	// GESTIONE DOCUMENTI DELL'ANAGRAFICA 
	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> documentiAnagrafes = documentiHelper.getDocumentiAnagrafeList();
	for (ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> chiaveValoreBeanDocAnagrafe : documentiAnagrafes) {
	    List<AnagrafedocumentiDTO> documentiAnagr = chiaveValoreBeanDocAnagrafe.getValore();
	    log.debug("popolaAllegatiComeLinkDaCommand# Creo i link per i documenti dell'anagrafica");
	    for (AnagrafedocumentiDTO anagrafedocumenti : documentiAnagr) {
		// Verifico se l'allegato è stato selezionato come da inviare
		if (anagrafedocumenti.isTransientSegnaPerInvio() && anagrafedocumenti.getCodiceOggetto() != null) {
		    Integer codiceOggetto = anagrafedocumenti.getCodiceOggetto();
		    creaAndInsertTempLinkAllegati(codiceOggetto, anagrafedocumenti.getDocumento(), uuid, anagrafedocumenti.getDocumento());
		}
	    }
	}
	// GESTIONE DOCUMENTI DELLA CDS 
	List<ChiaveValoreBean<String, List<CdsattiDTO>>> cdsattis = documentiHelper.getCdsattiList();
	for (ChiaveValoreBean<String, List<CdsattiDTO>> cdsDocChiaveValoreBeans : cdsattis) {
	    List<CdsattiDTO> cdsdocs = cdsDocChiaveValoreBeans.getValore();
	    log.debug("popolaAllegatiComeLinkDaCommand# Creo i link per i documenti dell'anagrafica");
	    for (CdsattiDTO cdsdocu : cdsdocs) {
		// Verifico se l'allegato è stato selezionato come da inviare
		if (cdsdocu.getTransientSegnaPerInvio() && cdsdocu.getCodiceoggetto() != null) {
		    Integer codiceOggetto = cdsdocu.getCodiceoggetto();
		    creaAndInsertTempLinkAllegati(codiceOggetto, cdsdocu.getNomefile(), uuid, cdsdocu.getNomefile());
		}
	    }
	}
	// Permette di rendere visibili le modifice sul db al servizio esterno ASP per la creazione dle link
	istanzeDAO.commit();
	istanzeDAO.flush();
    }

    private void popolaAllegatiDaCommand(ProtocollazioneCommand command, ArrayOfAllegatoType allegati) {

	if (command.getDocumentiHelper() != null) {
	    DocumentiHelper documentiHelper = command.getDocumentiHelper();
	    Integer codiceDocPrincipale = null;
	    if (StringUtils.isNotBlank(command.getDocumentoPrincipale())) {
		codiceDocPrincipale = Integer.parseInt(command.getDocumentoPrincipale());
	    }
	    // Variabile che utilizzeremo memorizzare l'evento: trovato documento principale e messo nella posizione 0 della lista
	    boolean isTrovatoprincipale = false;
	    isTrovatoprincipale = popolaAllegatiIstanza(command, allegati, isTrovatoprincipale, documentiHelper, codiceDocPrincipale);
	    isTrovatoprincipale = popolaAllegatiEndoprocedimenti(command, allegati, isTrovatoprincipale, documentiHelper, codiceDocPrincipale);
	    isTrovatoprincipale = popolaAllegatiProcura(command, allegati, isTrovatoprincipale, documentiHelper, codiceDocPrincipale);
	    isTrovatoprincipale = popolaAllegatiCDS(command, allegati, isTrovatoprincipale, documentiHelper, codiceDocPrincipale);
	    isTrovatoprincipale = popolaAllegatiDelMovimento(command, allegati, isTrovatoprincipale, documentiHelper, codiceDocPrincipale);
	    isTrovatoprincipale = popolaAllegatiAltriMovimenti(command, allegati, isTrovatoprincipale, documentiHelper, codiceDocPrincipale);
	    popolaAllegatiAnagrafica(command, allegati, isTrovatoprincipale, documentiHelper, codiceDocPrincipale);
	}
	if (command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_PEC)) {
	    popolaAllegatiPEC(command, allegati);
	}
	if (command.getAllegatiGenerici().isEmpty()) {
	    return;
	}
	allegati.getAllegatoType().addAll(command.getAllegatiGenerici());
    }

    private boolean popolaAllegatiIstanza(ProtocollazioneCommand command, ArrayOfAllegatoType allegati, boolean docPrincipalePresente,
	    DocumentiHelper documentiHelper, Integer codiceDocPrincipale) {

	boolean isTrovatoprincipale = docPrincipalePresente;
	// GESTIONE DOCUMENTI DELL' ISTANZA 	
	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> documentiIstanzas = documentiHelper.getDocumentiIstanzaList();
	for (ChiaveValoreBean<String, List<DocumentiistanzaDTO>> chiaveValoreBean : documentiIstanzas) {
	    List<DocumentiistanzaDTO> docistanzas = chiaveValoreBean.getValore();
	    for (DocumentiistanzaDTO documentiistanza : docistanzas) {
		// Verifico se l'asselato è stato selezionato come da inviare
		if (documentiistanza.getTransientSegnaPerInvio()) {
		    AllegatoType nuovoAllegato = new AllegatoType();
		    nuovoAllegato.setCod(String.valueOf(documentiistanza.getCodiceOggetto()));
		    String desc = getNomeAllegato(documentiistanza.getDocumento(), documentiistanza.getCodiceOggetto());
		    nuovoAllegato.setDescrizione(desc);
		    if (documentiistanza.isTransientSegnaPerInvioPec()) {
			nuovoAllegato.setInviaTramitePec(Boolean.TRUE);
		    }
		    if (!isTrovatoprincipale && codiceDocPrincipale != null && documentiistanza.getCodiceOggetto().equals(codiceDocPrincipale)) {
			if (log.isDebugEnabled()) {
			    log.debug("popolaAllegatiIstanza# Trovato il documento principale.Tipo documento: {},nome documento {}({})",
				    new Object[] { "Istanza", desc, command.getDocumentoPrincipale() });
			}
			allegati.getAllegatoType().add(0, nuovoAllegato);
			// può esserci un unico principale
			isTrovatoprincipale = true;
		    } else {
			allegati.getAllegatoType().add(nuovoAllegato);
		    }
		}
	    }
	}
	return isTrovatoprincipale;
    }

    private boolean popolaAllegatiEndoprocedimenti(ProtocollazioneCommand command, ArrayOfAllegatoType allegati, boolean docPrincipalePresente,
	    DocumentiHelper documentiHelper, Integer codiceDocPrincipale) {

	boolean isTrovatoprincipale = docPrincipalePresente;
	// GESTIONE DOCUMENTI DELL' ENDO DELL' ISTANZA 
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> istanzaAllegatiIstanzas = documentiHelper.getDocumentiEndoprocedimentiList();
	for (ChiaveValoreBean<String, List<IstanzeallegatiDTO>> chiaveValoreBean : istanzaAllegatiIstanzas) {
	    List<IstanzeallegatiDTO> docEndoistanzas = chiaveValoreBean.getValore();
	    for (IstanzeallegatiDTO documentiEndo : docEndoistanzas) {
		// Verifico se l'asselato è stato selezionato come da inviare
		if (documentiEndo.isTransientSegnaPerInvio()) {
		    AllegatoType nuovoAllegato = new AllegatoType();
		    nuovoAllegato.setCod(String.valueOf(documentiEndo.getCodiceOggetto()));
		    String desc = getNomeAllegato(documentiEndo.getAllegatoextra(), documentiEndo.getCodiceOggetto());
		    nuovoAllegato.setDescrizione(desc);
		    if (documentiEndo.isTransientSegnaPerInvioPec()) {
			nuovoAllegato.setInviaTramitePec(Boolean.TRUE);
		    }
		    if (!isTrovatoprincipale && codiceDocPrincipale != null && documentiEndo.getCodiceOggetto().equals(codiceDocPrincipale)) {
			if (log.isDebugEnabled()) {
			    log.debug("popolaAllegatiEndoprocedimenti# Trovato il documento principale.Tipo documento: {},nome documento {}({})",
				    new Object[] { "Istanza", desc, command.getDocumentoPrincipale() });
			}
			allegati.getAllegatoType().add(0, nuovoAllegato);
			// può esserci un unico principale
			isTrovatoprincipale = true;
		    } else {
			allegati.getAllegatoType().add(nuovoAllegato);
		    }
		}
	    }
	}
	return isTrovatoprincipale;
    }

    private boolean popolaAllegatiProcura(ProtocollazioneCommand command, ArrayOfAllegatoType allegati, boolean docPrincipalePresente,
	    DocumentiHelper documentiHelper, Integer codiceDocPrincipale) {

	boolean isTrovatoprincipale = docPrincipalePresente;
	// GESTIONE DOCUMENTI DELLA PROCURA
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> procurass = documentiHelper.getIstanzeprocureList();
	for (ChiaveValoreBean<String, List<IstanzeprocureDTO>> chiaveValoreBean : procurass) {
	    List<IstanzeprocureDTO> docPrucureistanzas = chiaveValoreBean.getValore();
	    for (IstanzeprocureDTO documentiProcura : docPrucureistanzas) {
		// Verifico se l'asselato è stato selezionato come da inviare
		if (documentiProcura.getTransientSegnaPerInvio()) {
		    AllegatoType nuovoAllegato = new AllegatoType();
		    nuovoAllegato.setCod(String.valueOf(documentiProcura.getCodiceOggetto()));
		    String desc = getNomeAllegato(
			    "Documento della procura di " + documentiProcura.getAnagrafeProcuratore().getDescrizioneRichiedente(),
			    documentiProcura.getCodiceOggetto());
		    nuovoAllegato.setDescrizione(desc);
		    if (documentiProcura.isTransientSegnaPerInvioPec()) {
			nuovoAllegato.setInviaTramitePec(Boolean.TRUE);
		    }
		    if (!isTrovatoprincipale && codiceDocPrincipale != null && documentiProcura.getCodiceOggetto().equals(codiceDocPrincipale)) {
			if (log.isDebugEnabled()) {
			    log.debug("popolaAllegatiProcura# Trovato il documento principale.Tipo documento: {},nome documento {}({})",
				    new Object[] { "Istanza", desc, command.getDocumentoPrincipale() });
			}
			allegati.getAllegatoType().add(0, nuovoAllegato);
			// può esserci un unico principale
			isTrovatoprincipale = true;
		    } else {
			allegati.getAllegatoType().add(nuovoAllegato);
		    }
		    if (documentiProcura.getCodiceOggettoDocId() != null) {
			nuovoAllegato = new AllegatoType();
			nuovoAllegato.setCod(String.valueOf(documentiProcura.getCodiceOggettoDocId()));
			desc = getNomeAllegato("Documento di identita' associato alla procura di " +
					       documentiProcura.getAnagrafeProcuratore().getDescrizioneRichiedente(),
				documentiProcura.getCodiceOggettoDocId());
			nuovoAllegato.setDescrizione(desc);
			allegati.getAllegatoType().add(nuovoAllegato);
		    }
		}
	    }
	}
	return isTrovatoprincipale;
    }

    private boolean popolaAllegatiCDS(ProtocollazioneCommand command, ArrayOfAllegatoType allegati, boolean docPrincipalePresente,
	    DocumentiHelper documentiHelper, Integer codiceDocPrincipale) {

	boolean isTrovatoprincipale = docPrincipalePresente;
	// GESTIONE DOCUMENTI DELLE CDS		
	List<ChiaveValoreBean<String, List<CdsattiDTO>>> cdsss = documentiHelper.getCdsattiList();
	for (ChiaveValoreBean<String, List<CdsattiDTO>> chiaveValoreBean : cdsss) {
	    List<CdsattiDTO> docCdss = chiaveValoreBean.getValore();
	    for (CdsattiDTO documentiCds : docCdss) {
		// Verifico se l'asselato è stato selezionato come da inviare
		if (documentiCds.getTransientSegnaPerInvio()) {
		    AllegatoType nuovoAllegato = new AllegatoType();
		    nuovoAllegato.setCod(String.valueOf(documentiCds.getCodiceoggetto()));
		    String desc = getNomeAllegato("", documentiCds.getCodiceoggetto());
		    nuovoAllegato.setDescrizione(desc);
		    if (documentiCds.isTransientSegnaPerInvioPec()) {
			nuovoAllegato.setInviaTramitePec(Boolean.TRUE);
		    }
		    if (!isTrovatoprincipale && codiceDocPrincipale != null && documentiCds.getCodiceoggetto().equals(codiceDocPrincipale)) {
			if (log.isDebugEnabled()) {
			    log.debug("popolaAllegatiCDS# Trovato il documento principale.Tipo documento: {},nome documento {}({})",
				    new Object[] { "Istanza", desc, command.getDocumentoPrincipale() });
			}
			allegati.getAllegatoType().add(0, nuovoAllegato);
			// può esserci un unico principale
			isTrovatoprincipale = true;
		    } else {
			allegati.getAllegatoType().add(nuovoAllegato);
		    }
		}
	    }
	}
	return isTrovatoprincipale;
    }

    private boolean popolaAllegatiDelMovimento(ProtocollazioneCommand command, ArrayOfAllegatoType allegati, boolean docPrincipalePresente,
	    DocumentiHelper documentiHelper, Integer codiceDocPrincipale) {

	boolean isTrovatoprincipale = docPrincipalePresente;
	// GESTIONE DOCUMENTI DEL MOVIMENTO
	if (command.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
	    /// solo per la protocollazione in uscita
	    if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_PARTENZA)) {
		Boolean isFlgProtocollaZipLogico = command.getFlgProtocollaZipLogico();
		if (isFlgProtocollaZipLogico != null && isFlgProtocollaZipLogico && Boolean.TRUE
			.equals(this.movimentiZipLogicoService.isZipLogicoExistInMovimento(command.getMovimento().getId().getCodice()))) {
		    DocumentiHelper docHelper = movimentiZipLogicoService.findDocumentiZipLogicoToDisplay(command.getMovimento().getId().getCodice());
		    setAllegatoTypeWithZipLogico(allegati, docHelper);
		}
	    }
	    List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> documentiMovimentos = documentiHelper.getDocumentiMovimentoList();
	    for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> chiaveValoreBean : documentiMovimentos) {
		List<MovimentiallegatiDTO> documentiMovs = chiaveValoreBean.getValore();
		for (MovimentiallegatiDTO documentiMovimento : documentiMovs) {
		    // Verifico se l'allegato è stato selezionato come da inviare
		    if (documentiMovimento.isTransientSegnaPerInvio()) {
			AllegatoType nuovoAllegato = new AllegatoType();
			nuovoAllegato.setCod(String.valueOf(documentiMovimento.getCodiceOggetto()));
			String desc = getNomeAllegato(documentiMovimento.getDescrizione(), documentiMovimento.getCodiceOggetto());
			nuovoAllegato.setDescrizione(desc);
			if (documentiMovimento.isTransientSegnaPerInvioPec()) {
			    nuovoAllegato.setInviaTramitePec(Boolean.TRUE);
			}
			if (!isTrovatoprincipale && codiceDocPrincipale != null
				&& documentiMovimento.getCodiceOggetto().equals(codiceDocPrincipale)) {
			    if (log.isDebugEnabled()) {
				log.debug("popolaAllegatiDelMovimento# Trovato il documento principale.Tipo documento: {},nome documento {}({})",
					new Object[] { "Istanza", desc, command.getDocumentoPrincipale() });
			    }
			    allegati.getAllegatoType().add(0, nuovoAllegato);
			    // può esserci un unico principale
			    isTrovatoprincipale = true;
			} else {
			    allegati.getAllegatoType().add(nuovoAllegato);
			}
		    }
		}
	    }
	}
	return isTrovatoprincipale;
    }

    private boolean popolaAllegatiAltriMovimenti(ProtocollazioneCommand command, ArrayOfAllegatoType allegati, boolean docPrincipalePresente,
	    DocumentiHelper documentiHelper, Integer codiceDocPrincipale) {

	boolean isTrovatoprincipale = docPrincipalePresente;
	// GESTIONE  DOCUMENTI ALTRI MOVIMENTI
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> documentialtriMovimentos = documentiHelper.getDocumentiAltriMovimentiList();
	for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> chiaveValoreBean : documentialtriMovimentos) {
	    List<MovimentiallegatiDTO> documentiAltriMovs = chiaveValoreBean.getValore();
	    for (MovimentiallegatiDTO documentiAltroMovimento : documentiAltriMovs) {
		// Verifico se l'allegato è stato selezionato come da inviare
		if (documentiAltroMovimento.isTransientSegnaPerInvio()) {
		    AllegatoType nuovoAllegato = new AllegatoType();
		    nuovoAllegato.setCod(String.valueOf(documentiAltroMovimento.getCodiceOggetto()));
		    String desc = getNomeAllegato(documentiAltroMovimento.getDescrizione(), documentiAltroMovimento.getCodiceOggetto());
		    nuovoAllegato.setDescrizione(desc);
		    if (documentiAltroMovimento.isTransientSegnaPerInvioPec()) {
			nuovoAllegato.setInviaTramitePec(Boolean.TRUE);
		    }
		    if (!isTrovatoprincipale && codiceDocPrincipale != null
			    && documentiAltroMovimento.getCodiceOggetto().equals(codiceDocPrincipale)) {
			if (log.isDebugEnabled()) {
			    log.debug("popolaAllegatiAltriMovimenti# Trovato il documento principale.Tipo documento: {},nome documento {}({})",
				    new Object[] { "Istanza", desc, command.getDocumentoPrincipale() });
			}
			allegati.getAllegatoType().add(0, nuovoAllegato);
			// può esserci un unico principale
			isTrovatoprincipale = true;
		    } else {
			allegati.getAllegatoType().add(nuovoAllegato);
		    }
		}
	    }
	}
	return isTrovatoprincipale;
    }

    private void popolaAllegatiAnagrafica(ProtocollazioneCommand command, ArrayOfAllegatoType allegati, boolean docPrincipalePresente,
	    DocumentiHelper documentiHelper, Integer codiceDocPrincipale) {

	boolean isTrovatoprincipale = docPrincipalePresente;
	// GESTIONE DOCUMENTI DELL'ANAGRAFICA 
	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> documentiAnagrafes = documentiHelper.getDocumentiAnagrafeList();
	for (ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> chiaveValoreBean : documentiAnagrafes) {
	    List<AnagrafedocumentiDTO> documentiAnagr = chiaveValoreBean.getValore();
	    for (AnagrafedocumentiDTO anagrafedocumenti : documentiAnagr) {
		// Verifico se l'allegato è stato selezionato come da inviare
		if (anagrafedocumenti.isTransientSegnaPerInvio()) {
		    AllegatoType nuovoAllegato = new AllegatoType();
		    nuovoAllegato.setCod(String.valueOf(anagrafedocumenti.getCodiceOggetto()));
		    String desc = getNomeAllegato(anagrafedocumenti.getDocumento(), anagrafedocumenti.getCodiceOggetto());
		    nuovoAllegato.setDescrizione(desc);
		    if (anagrafedocumenti.isTransientSegnaPerInvioPec()) {
			nuovoAllegato.setInviaTramitePec(Boolean.TRUE);
		    }
		    if (!isTrovatoprincipale && codiceDocPrincipale != null && anagrafedocumenti.getCodiceOggetto().equals(codiceDocPrincipale)) {
			if (log.isDebugEnabled()) {
			    log.debug("popolaAllegatiAnagrafica# Trovato il documento principale.Tipo documento: {},nome documento {}({})",
				    new Object[] { "Istanza", desc, command.getDocumentoPrincipale() });
			}
			allegati.getAllegatoType().add(0, nuovoAllegato);
			// può esserci un unico principale
			isTrovatoprincipale = true;
		    } else {
			allegati.getAllegatoType().add(nuovoAllegato);
		    }
		}
	    }
	}
    }

    private void popolaAllegatiPEC(ProtocollazioneCommand command, ArrayOfAllegatoType allegati) throws PopolaAllegatiPECException {

	/*
	 * LION 2013-10-28: protocollazione PEC - il documento principale da protocollare è già stato salvato in OGGETTI e la FK impostata in PEC_INBOX
	 */
	AllegatoType allegatoPec = new AllegatoType();
	PecInbox pec = command.getPec();
	if (pec == null || pec.getId() == null || StringUtils.isEmpty(pec.getId().getId())) {
	    throw new PopolaAllegatiPECException(
		    "Impossibile procedere alla protocollazione della PEC perchè manca il riferimento alla PEC da protocollare");
	}
	if (pec.getOggettoProtocollo() == null || pec.getOggettoProtocollo().getId() == null
		|| pec.getOggettoProtocollo().getId().getCodice() == null) {
	    throw new PopolaAllegatiPECException(
		    "Impossibile procedere alla protocollazione della PEC perchè manca il riferimento all'allegato che contiene il corpo della PEC da protocollare.");
	}
	Oggetti o = oggettiService.findByIdLazy(new PkId(pec.getOggettoProtocollo().getId().getCodice()));
	allegatoPec.setDescrizione(o.getNomefile());
	allegatoPec.setCod(pec.getOggettoProtocollo().getId().getCodice().toString());
	allegati.getAllegatoType().add(allegatoPec);
    }

    private void setAllegatoTypeWithZipLogico(ArrayOfAllegatoType allegati, DocumentiHelper docHelper) {

	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> beansOfDocumentiistanza = docHelper.getDocumentiIstanzaList();
	for (ChiaveValoreBean<String, List<DocumentiistanzaDTO>> bean : beansOfDocumentiistanza) {
	    List<DocumentiistanzaDTO> documentiistanzas = bean.getValore();
	    if (!documentiistanzas.isEmpty()) {
		for (DocumentiistanzaDTO docist : documentiistanzas) {
		    AllegatoType nuovoAllegato = new AllegatoType();
		    nuovoAllegato.setCod(String.valueOf(docist.getCodiceOggetto()));
		    String desc = getNomeAllegato(docist.getDocumento(), docist.getCodiceOggetto());
		    nuovoAllegato.setDescrizione(desc);
		    allegati.getAllegatoType().add(nuovoAllegato);
		}
	    }
	}
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> beansOfMovimentiallegati = docHelper.getDocumentiAltriMovimentiList();
	for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> bean : beansOfMovimentiallegati) {
	    List<MovimentiallegatiDTO> movimentiallegatis = bean.getValore();
	    if (!movimentiallegatis.isEmpty()) {
		for (MovimentiallegatiDTO movall : movimentiallegatis) {
		    AllegatoType nuovoAllegato = new AllegatoType();
		    nuovoAllegato.setCod(String.valueOf(movall.getCodiceOggetto()));
		    String desc = getNomeAllegato(movall.getDescrizione(), movall.getCodiceOggetto());
		    nuovoAllegato.setDescrizione(desc);
		    allegati.getAllegatoType().add(nuovoAllegato);
		}
	    }
	}
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> beansOfEndo = docHelper.getDocumentiEndoprocedimentiList();
	for (ChiaveValoreBean<String, List<IstanzeallegatiDTO>> bean : beansOfEndo) {
	    List<IstanzeallegatiDTO> istanzeallegatis = bean.getValore();
	    if (!istanzeallegatis.isEmpty()) {
		for (IstanzeallegatiDTO istall : istanzeallegatis) {
		    AllegatoType nuovoAllegato = new AllegatoType();
		    nuovoAllegato.setCod(String.valueOf(istall.getCodiceOggetto()));
		    String desc = getNomeAllegato(istall.getAllegatoextra(), istall.getCodiceOggetto());
		    nuovoAllegato.setDescrizione(desc);
		    allegati.getAllegatoType().add(nuovoAllegato);
		}
	    }
	}
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> beansOfProcure = docHelper.getIstanzeprocureList();
	for (ChiaveValoreBean<String, List<IstanzeprocureDTO>> bean : beansOfProcure) {
	    List<IstanzeprocureDTO> istanzeprocures = bean.getValore();
	    if (!istanzeprocures.isEmpty()) {
		for (IstanzeprocureDTO istproc : istanzeprocures) {
		    AllegatoType nuovoAllegato = new AllegatoType();
		    nuovoAllegato.setCod(String.valueOf(istproc.getCodiceOggetto()));
		    String desc = getNomeAllegato(istproc.getAnagrafeProcuratore().getDescrizioneRichiedente(), istproc.getCodiceOggetto());
		    nuovoAllegato.setDescrizione(desc);
		    allegati.getAllegatoType().add(nuovoAllegato);
		}
	    }
	}
	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> beansOfAnagrafe = docHelper.getDocumentiAnagrafeList();
	for (ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> bean : beansOfAnagrafe) {
	    List<AnagrafedocumentiDTO> anagrafedocumentis = bean.getValore();
	    if (!anagrafedocumentis.isEmpty()) {
		for (AnagrafedocumentiDTO docanag : anagrafedocumentis) {
		    AllegatoType nuovoAllegato = new AllegatoType();
		    nuovoAllegato.setCod(String.valueOf(docanag.getCodiceOggetto()));
		    String desc = getNomeAllegato(docanag.getDocumento(), docanag.getCodiceOggetto());
		    nuovoAllegato.setDescrizione(desc);
		    allegati.getAllegatoType().add(nuovoAllegato);
		}
	    }
	}
	List<ChiaveValoreBean<String, List<CdsattiDTO>>> beansOfCdsatti = docHelper.getCdsattiList();
	for (ChiaveValoreBean<String, List<CdsattiDTO>> bean : beansOfCdsatti) {
	    List<CdsattiDTO> atti = bean.getValore();
	    if (!atti.isEmpty()) {
		for (CdsattiDTO atto : atti) {
		    AllegatoType nuovoAllegato = new AllegatoType();
		    nuovoAllegato.setCod(String.valueOf(atto.getCodiceoggetto()));
		    String desc = getNomeAllegato("", atto.getCodiceoggetto());
		    nuovoAllegato.setDescrizione(desc);
		    allegati.getAllegatoType().add(nuovoAllegato);
		}
	    }
	}
	// I documenti dello zip logico vengono inoltrati come link in mail compilate
	for (AllegatoType allegatiType : allegati.getAllegatoType()) {
	    allegatiType.setInviaTramitePec(Boolean.FALSE);
	}
    }

    private String getNomeAllegato(String descrizione, Integer codiceoggetto) {

	Oggetti oggetto = oggettiService.findByIdLazy(new PkId(codiceoggetto));
	String nomeFile = oggetto.getNomefile();
	String estensione = "";
	if (StringUtils.isNotBlank(nomeFile) && nomeFile.indexOf(".") >= 0) {
	    estensione = nomeFile.substring(nomeFile.lastIndexOf('.'));
	}
	if (StringUtils.isBlank(descrizione)) {
	    return oggetto.getNomefile();
	} else {
	    return descrizione + estensione;
	}
    }

    private void validateCommand(ProtocollazioneCommand protocollazioneCommand, boolean validaFascicolo) throws ConfigurationException {

	// GESTISCE LO SMISTAMENTO MULTIPLO 
	// Se il parametro PROTOCOLLO_ATTIVO.IS_SMISTAMENTO_MULTIPLO==1 ALLORA SI POSSONO INSERIRE N DESTINATARI PER FLUSSO
	// INTERNO E ARRIVO
	// (DESTINATARI : AMMINISTRAZIONI INTERNE)
	if (protocollazioneCommand == null) {
	    throw new ConfigurationException("Non ci sono dati da validare: protocollazioneCommand nullo");
	}
	int smistamento = this.findSmistamentoMultiplo(protocollazioneCommand.getComune().getCodicecomune(),
		protocollazioneCommand.getProtSoftware().getCodice());
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	if (StringUtils.isBlank(protocollazioneCommand.getClassifica())) {
	    ivs.add(new InvalidValue("alert.required", protocollazioneCommand.getClass(), "classifica", protocollazioneCommand.getClassifica(),
		    protocollazioneCommand));
	}
	if (StringUtils.isBlank(protocollazioneCommand.getFlusso())) {
	    ivs.add(new InvalidValue("alert.required", protocollazioneCommand.getClass(), "flusso", protocollazioneCommand.getFlusso(),
		    protocollazioneCommand));
	}
	if (StringUtils.isBlank(protocollazioneCommand.getTipoDocumento())) {
	    CodiceDescrizioneBean[] list = this.getListaTipiDocumento(protocollazioneCommand.getProtSoftware().getCodice(),
		    protocollazioneCommand.getComune().getCodicecomune());
	    if (list != null && list.length > 0) {
		ivs.add(new InvalidValue("alert.required", protocollazioneCommand.getClass(), "tipoDocumento",
			protocollazioneCommand.getTipoDocumento(), protocollazioneCommand));
	    }
	}
	// Controllo della presenza dello smistamente va fatto solo per inserimenti da web
	if ((protocollazioneCommand.getInserimentoAutomatico() == null
		|| (protocollazioneCommand.getInserimentoAutomatico() != null && protocollazioneCommand.getInserimentoAutomatico().equals(false)))
		&& StringUtils.isBlank(protocollazioneCommand.getSmistamento())) {
	    List<ProtocolloSmistamenti> list = protocolloSmistamentiService.findByComuneAndSoftware(
		    protocollazioneCommand.getComune().getCodicecomune(), protocollazioneCommand.getProtSoftware().getCodice());
	    if (list != null && !list.isEmpty()) {
		ivs.add(new InvalidValue("alert.required", protocollazioneCommand.getClass(), "smistamento", protocollazioneCommand.getSmistamento(),
			protocollazioneCommand));
	    }
	}
	if (StringUtils.isNotBlank(protocollazioneCommand.getFlusso())) {
	    if (protocollazioneCommand.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_PARTENZA)) {
		// ci deve essere un mittente ed almeno un destinatario
		// mittente da command.mittente e deve essere una amministrazione
		if (EntityUtils.getNestedProperty(protocollazioneCommand.getMittente().getAmministrazioni(), "id.codice") == null) {
		    ivs.add(new InvalidValue("alert.required", protocollazioneCommand.getClass(), "mittente", protocollazioneCommand.getMittente(),
			    protocollazioneCommand));
		}
		// destinatari da command.destinataris e deve essere una amministrazione
		if (protocollazioneCommand.getDestinataris().isEmpty()) {
		    ivs.add(new InvalidValue("alert.required", protocollazioneCommand.getClass(), "destinataris",
			    protocollazioneCommand.getDestinataris(), protocollazioneCommand));
		} else {
		    List<ProtocolloSoggettoCommand> soggetti = protocollazioneCommand.getDestinataris();
		    boolean almenoUno = false;
		    for (ProtocolloSoggettoCommand psc : soggetti) {
			if (EntityUtils.getNestedProperty(psc.getAmministrazioni(), "id.codice") != null
				|| EntityUtils.getNestedProperty(psc.getAnagrafe(), "id.codice") != null) {
			    almenoUno = true;
			    break;
			}
		    }
		    if (!almenoUno) {
			ivs.add(new InvalidValue("alert.required", protocollazioneCommand.getClass(), "destinataris",
				protocollazioneCommand.getDestinataris(), protocollazioneCommand));
		    }
		}
		// destinatari da command.destinataris
	    } else if (protocollazioneCommand.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_ARRIVO)) {
		// ci possono essere più mittenti ed almeno un destinatario
		// destinatario da command.destinatario e deve essere una amministrazione
		if (EntityUtils.getNestedProperty(protocollazioneCommand.getDestinatario().getAmministrazioni(), "id.codice") == null) {
		    ivs.add(new InvalidValue("alert.required", protocollazioneCommand.getClass(), "destinatario",
			    protocollazioneCommand.getDestinatario(), protocollazioneCommand));
		}
		// mittenti da command.mittentis
		if (protocollazioneCommand.getMittentis().isEmpty()) {
		    ivs.add(new InvalidValue("alert.required", protocollazioneCommand.getClass(), "mittentis", protocollazioneCommand.getMittentis(),
			    protocollazioneCommand));
		} else {
		    List<ProtocolloSoggettoCommand> soggetti = protocollazioneCommand.getMittentis();
		    boolean almenoUno = false;
		    for (ProtocolloSoggettoCommand psc : soggetti) {
			if (EntityUtils.getNestedProperty(psc.getAmministrazioni(), "id.codice") != null) {
			    almenoUno = true;
			    break;
			}
			if (EntityUtils.getNestedProperty(psc.getAnagrafe(), "id.codice") != null) {
			    almenoUno = true;
			    break;
			}
		    }
		    if (!almenoUno) {
			ivs.add(new InvalidValue("alert.required", protocollazioneCommand.getClass(), "mittentis",
				protocollazioneCommand.getMittentis(), protocollazioneCommand));
		    }
		}
	    } else if (protocollazioneCommand.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_INTERNO)) {
		// ci devono essere solamente un mittente ed un destinatario e tutte e due amministrazioni
		if (smistamento == 0 || smistamento == 2) {
		    if (EntityUtils.getNestedProperty(protocollazioneCommand.getDestinatario().getAmministrazioni(), "id.codice") == null) {
			ivs.add(new InvalidValue("alert.required", protocollazioneCommand.getClass(), "destinatario",
				protocollazioneCommand.getDestinatario(), protocollazioneCommand));
		    }
		} else {
		    if (protocollazioneCommand.getDestinataris().isEmpty()) {
			ivs.add(new InvalidValue("alert.required", protocollazioneCommand.getClass(), "destinatario",
				protocollazioneCommand.getDestinatario(), protocollazioneCommand));
		    }
		}
		if (EntityUtils.getNestedProperty(protocollazioneCommand.getMittente().getAmministrazioni(), "id.codice") == null) {
		    ivs.add(new InvalidValue("alert.required", protocollazioneCommand.getClass(), "mittente", protocollazioneCommand.getMittente(),
			    protocollazioneCommand));
		}
	    }
	}
	// Devo controllare se è impostato il documento tipo da creare, se non c'è devo rilanciare l'eccezione.
	if (protocollazioneCommand.getFlgProtocollalinkall() != null && protocollazioneCommand.getFlgProtocollalinkall()
		&& EntityUtils.getNestedProperty(protocollazioneCommand.getLetteraTipoAllegati(), "id.codice") == null) {
	    ivs.add(new InvalidValue("service_error.lettera_tipo_mancante", null, null, null, null));
	}
	if (!ivs.isEmpty()) {
	    this.throwValidationMessages(ivs);
	}
	validazioneDocPrincipale(protocollazioneCommand);
    }

    private void validazioneDocPrincipale(ProtocollazioneCommand command) throws ConfigurationException {

	log.debug("validazioneDocPrincipale {}", command.getMettiAllaFirma());
	boolean mettiAllaFirma = command.getMettiAllaFirma() == null ? false : command.getMettiAllaFirma().booleanValue();
	if (!mettiAllaFirma) { // nel caso di funzionalità METTI ALLA FIRMA non devo fare la verifica
	    Verticalizzazioniparametri flussiVerificaFirmaDocPrincipale = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_FLUSSI_VER_FIRMA_DOC_PRINC,
		    command.getComune().getCodicecomune());
	    log.debug("validazioneDocPrincipale non è funzionalità metti alla Firma");
	    if (flussiVerificaFirmaDocPrincipale == null || StringUtils.isBlank(flussiVerificaFirmaDocPrincipale.getValore())) {
		log.debug(
			"validazioneDocPrincipale non è funzionalità metti alla Firma StringUtils.isBlank(flussiVerificaFirmaDocPrincipale.getValore()");
		return;
	    }
	    if (!flussiVerificaFirmaDocPrincipale.getValore().contains(command.getFlusso())) {
		log.debug("validazioneDocPrincipale non è funzionalità metti alla Firma {}-{}", flussiVerificaFirmaDocPrincipale,
			command.getFlusso());
		return;
	    }
	    if (StringUtils.isBlank(command.getDocumentoPrincipale())) {
		throw new ConfigurationException("Nessun documento principale specificato");
	    }
	    if (!this.oggettiMetadatiService.isOggettoFirmatoDigitalmente(Integer.parseInt(command.getDocumentoPrincipale()))) {
		throw new ConfigurationException("Il documento principale deve essere firmato digitalmente");
	    }
	}
    }

    @Override
    public void annullaProtocollo(String token, String fkidProtocollo, Date dataProtocollo, String numProtocollo, String motivoAnnullamento,
	    String noteAnnullamento, String software, String codiceComune) throws AnnullamentoProtocolloException {

	try {
	    String annoProtocollo = getAnnoProtocollo(dataProtocollo);
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    port.annullaProtocollo(token, fkidProtocollo, annoProtocollo, numProtocollo, motivoAnnullamento, noteAnnullamento, software,
		    codiceComune);
	    this.istanzeService.clear();
	} catch (Exception e) {
	    log.error("annullaProtocollo: {}", e.getMessage());
	    throw new AnnullamentoProtocolloException(e);
	}
    }

    /**
     * @param dataProtocollo
     * @return
     */
    private String getAnnoProtocollo(Date dataProtocollo) {

	String annoProtocollo = "";
	if (dataProtocollo != null) {
	    Calendar c = GregorianCalendar.getInstance();
	    c.setTime(dataProtocollo);
	    int year = c.get(Calendar.YEAR);
	    annoProtocollo = String.valueOf(year);
	}
	return annoProtocollo;
    }

    @Override
    public List<CodiceDescrizioneBean> getMotiviAnnullamento(String token, String software, String codiceComune)
	    throws RecuperaMotiviAnnullamentoException {

	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    ListaMotiviAnnullamentoResponseType res = port.getMotiviAnnullamento(token, software, codiceComune);
	    if (res == null) {
		return new ArrayList<CodiceDescrizioneBean>();
	    }
	    gestisciErroreType(res.getErrore(), true, "getMotiviAnnullamento");
	    ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType result = res.getMotivoAnnullamento();
	    if (result == null) {
		return new ArrayList<CodiceDescrizioneBean>();
	    }
	    List<CodiceDescrizioneBean> response = new ArrayList<CodiceDescrizioneBean>();
	    List<ListaMotiviAnnullamentoMotivoAnnullamentoType> motivos = result.getListaMotiviAnnullamentoMotivoAnnullamentoType();
	    for (ListaMotiviAnnullamentoMotivoAnnullamentoType motivo : motivos) {
		CodiceDescrizioneBean bean = new CodiceDescrizioneBean();
		bean.setCodice(motivo.getCodice());
		bean.setDescrizione(motivo.getDescrizione());
		response.add(bean);
	    }
	    return response;
	} catch (Exception e) {
	    log.error("annullaProtocollo: {}", e.getMessage());
	    throw new RecuperaMotiviAnnullamentoException(e);
	}
    }

    @Override
    public DatiProtocolloFascicolatoResponseType isFascicolato(String token, String fkidProtocollo, String numeroprotocollo, Date dataProtocollo,
	    String software, String codiceComune) throws VerificaProtocolloFascicolatoException {

	try {
	    String annoProtocollo = getAnnoProtocollo(dataProtocollo);
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    DatiProtocolloFascicolatoResponseType isFascicolato = port.isFascicolato(token, fkidProtocollo, annoProtocollo, numeroprotocollo,
		    software, codiceComune);
	    gestisciErroreType(isFascicolato.getErrore(), true, "isFascicolato");
	    return isFascicolato;
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw new VerificaProtocolloFascicolatoException(e);
	}
    }

    @Override
    public List<CodiceDescrizioneBean> getFascicoliPerIstanza(String token, Istanze istanza) throws RicercaFascicoliException {

	if (istanza == null || istanza.getId() == null || istanza.getId().getCodice() == null) {
	    return new ArrayList<CodiceDescrizioneBean>();
	}
	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    ListaFascicoliResponseType response = port.getFascicoli(token, String.valueOf(istanza.getId().getCodice()));
	    if (response == null) {
		return new ArrayList<CodiceDescrizioneBean>();
	    }
	    List<CodiceDescrizioneBean> list = new ArrayList<CodiceDescrizioneBean>();
	    gestisciErroreType(response.getErrore(), true, "getFascicoliPerIstanza");
	    ArrayOfDatiFascType fascicolos = response.getFascicolo();
	    if (fascicolos != null) {
		List<DatiFascType> l = fascicolos.getDatiFascType();
		for (DatiFascType resp : l) {
		    CodiceDescrizioneBean elemento = new CodiceDescrizioneBean();
		    elemento.setCodice(resp.getDataFascicolo());
		    elemento.setDescrizione(resp.getNumeroFascicolo());
		    list.add(elemento);
		}
	    }
	    return list;
	} catch (Exception e) {
	    log.error("getFascicoliPerIstanza: {}", e.getMessage());
	    throw new RicercaFascicoliException(e);
	}
    }

    @Override
    public DatiFascicoloResponseType cambiaFascicoloIstanzaXml(String token, ProtocollazioneCommand protocollazioneCommand)
	    throws CambiaFascicoloIstanzaException {

	Istanze istanza = istanzeService.findById(new PkId(protocollazioneCommand.getEntity().getId().getCodice()));
	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    DatiFascType sFile = generaFascicoloRequest(protocollazioneCommand);
	    DatiFascicoloResponseType response = port.cambiaFascicoloIstanzaXml(token, String.valueOf(istanza.getId().getCodice()), sFile);
	    this.istanzeService.clear();
	    gestisciErroreType(response.getErrore(), true, "cambiaFascicoloIstanzaXml");
	    if (StringUtils.isNotBlank(response.getWarning())) {
		inserisciEvento(istanza, null,
			"Errore durante la fascicolazione. ProtocollazioneService#cambiaFascicoloIstanzaXml[WARNING: " + response.getWarning() + "]");
	    }
	    return response;
	} catch (Exception e) {
	    inserisciEvento(istanza, null,
		    "Errore durante la fascicolazione. ProtocollazioneService#cambiaFascicoloIstanzaXml[" + e.getMessage() + "]");
	    log.error("cambiaFascicoloIstanzaXml: {}", e.getMessage());
	    throw new CambiaFascicoloIstanzaException(e);
	}
    }

    @Override
    public void fascicolaIstanza(Istanze entity, String token, TipoInserimento tipoInserimento) {

	String codiceComune = entity.getComune().getCodicecomune();
	boolean isProtocollo = new VerticalizzazioneProtocolloAttivoServiceImpl(verticalizzazioniService, codiceComune).isAttiva();
	if (!isProtocollo) {
	    return;
	}
	try {
	    log.debug("childDataInsert: chiamo il servizio di protocollazione per l'istanza: [{}], token: [{}], tipoProtocollazione: [{}]",
		    new Object[] { entity.getId().getCodice(), token, tipoInserimento });
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    DatiFascicoloResponseType response = port.fascicolazioneIstanza(token, String.valueOf(entity.getId().getCodice()),
		    tipoInserimento.value());
	    this.istanzeService.clear();
	    if (response == null) {
		return;
	    }
	    gestisciErroreType(response.getErrore(), true, "fascicolaIstanza");
	    if (StringUtils.isNotBlank(response.getWarning())) {
		inserisciEvento(entity, null,
			"Errore durante la fascicolazione. ProtocollazioneService#fascicolaIstanza[WARNING: " + response.getWarning() + "]");
	    }
	} catch (Exception e) {
	    log.error("childDataInsert: Impossibile contattare il servizio di fascicolazione a causa: {}", e.getMessage());
	    inserisciEvento(entity, null, "Errore durante la fascicolazione. ProtocollazioneService#fascicolaIstanza[" + e.getMessage() + "]");
	}
    }

    @Override
    public DatiFascicoloResponseType fascicolaIstanzaXml(String token, ProtocollazioneCommand protocollazioneCommand)
	    throws FascicolaIstanzaException {

	Istanze istanza = istanzeService.findById(new PkId(protocollazioneCommand.getEntity().getId().getCodice()));
	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    DatiFascType sFile = generaFascicoloRequest(protocollazioneCommand);
	    DatiFascicoloResponseType response = port.fascicolazioneIstanzaXml(token, String.valueOf(istanza.getId().getCodice()), sFile);
	    this.istanzeService.clear();
	    gestisciErroreType(response.getErrore(), true, "fascicolaIstanzaXml");
	    if (StringUtils.isNotBlank(response.getWarning())) {
		inserisciEvento(istanza, null,
			"Errore durante la fascicolazione. ProtocollazioneService#fascicolaIstanzaXml[WARNING: " + response.getWarning() + "]");
	    }
	    return response;
	} catch (Exception e) {
	    inserisciEvento(istanza, null, "Errore durante la fascicolazione. ProtocollazioneService#fascicolaIstanzaXml[" + e.getMessage() + "]");
	    log.error("fascicolaIstanzaXml: {}", e.getMessage());
	    throw new FascicolaIstanzaException(e);
	}
    }

    private DatiFascicoloResponseType fascicolaXml(ProtocollazioneCommand protocollazioneCommand) {

	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    DatiFascType datiFasc = generaFascicoloRequest(protocollazioneCommand);
	    DatiFascicoloResponseType response = port.fascicolazioneXml(ORMHelper.getToken(), protocollazioneCommand.getProtSoftware().getCodice(),
		    datiFasc, protocollazioneCommand.getComune().getCodicecomune(), protocollazioneCommand.getDatiProtocollo().getIdProtocollo(),
		    protocollazioneCommand.getDatiProtocollo().getNumeroProtocollo(), protocollazioneCommand.getDatiProtocollo().getAnnoProtocollo());
	    this.istanzeService.clear();
	    gestisciErroreType(response.getErrore(), true, "fascicolaXml");
	    if (StringUtils.isNotBlank(response.getWarning())) {
		FlashMessages.getWarnings()
			.add("Errore durante la fascicolazione. ProtocollazioneService#fascicolaXml[WARNING: " + response.getWarning() + "]");
	    }
	    return response;
	} catch (Exception e) {
	    log.error("fascicolaXml: {}", e.getMessage());
	    FlashMessages.getWarnings().add("Errore durante la fascicolazione. ProtocollazioneService#fascicolaXml[" + e.getMessage() + "]");
	    throw new RuntimeException(e);
	}
    }

    private void inserisciEvento(Istanze istanza, Movimenti movimento, String messaggio) {

	istanzeeventiService.insert(messaggio, IstanzeeventiConstants.CATEGORIA_PROTOCOLLO, movimento, istanza);
    }

    private DatiFascType generaFascicoloRequest(ProtocollazioneCommand command) {

	DatiFascType request = new DatiFascType();
	request.setClassificaFascicolo(command.getClassificaFascicolo());
	String dataFascicolo = "";
	if (command.getDataFascicolo() != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    dataFascicolo = sdf.format(command.getDataFascicolo());
	}
	request.setDataFascicolo(dataFascicolo);
	request.setNumeroFascicolo(command.getNumeroFascicolo());
	request.setOggettoFascicolo(command.getOggettoFascicolo());
	if (command.getAnnoFascicolo() != null) {
	    request.setAnnoFascicolo(String.valueOf(command.getAnnoFascicolo()));
	}
	return request;
    }

    @Override
    public EtichetteResponseType stampaEtichette(String token, String fkidIdProtocollo, String numeroProtocollo, Date dataProtocollo,
	    Integer numeroCopie, String stampante, String software, String codiceComune) throws StampaEtichetteException {

	if (StringUtils.isBlank(numeroProtocollo)) {
	    log.error("stampaEtichette: Il numero protocollo non è valido");
	    throw new StampaEtichetteException("stampaEtichette: Il numero protocollo non è valido");
	}
	if (dataProtocollo == null) {
	    log.error("stampaEtichette: Data protocollo nulla");
	    throw new StampaEtichetteException("stampaEtichette: Data protocollo nulla");
	}
	try {
	    validateStampa(numeroCopie, stampante);
	    GregorianCalendar c = new GregorianCalendar();
	    c.setTime(dataProtocollo);
	    XMLGregorianCalendar xc = Utilities.getXMLGregorianCalendar(c);
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    EtichetteResponseType result = port.stampaEtichette(token, fkidIdProtocollo, numeroProtocollo, xc, numeroCopie, stampante, software,
		    codiceComune);
	    gestisciErroreType(result.getErrore(), true, "stampaEtichette");
	    return result;
	} catch (Exception e) {
	    log.error("stampaEtichette: {}", e.getMessage());
	    throw new StampaEtichetteException(e);
	}
    }

    private void validateStampa(Integer numeroCopie, String stampante) {

	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	if (StringUtils.isBlank(stampante)) {
	    ivs.add(new InvalidValue("alert.required", ProtocollazioneCommand.class, "stampante", stampante, new ProtocollazioneCommand()));
	}
	if (numeroCopie == null) {
	    ivs.add(new InvalidValue("alert.required", ProtocollazioneCommand.class, "numeroCopie", null, new ProtocollazioneCommand()));
	} else {
	    if (numeroCopie.intValue() == 0) {
		ivs.add(new InvalidValue("alert.required", ProtocollazioneCommand.class, "numeroCopie", 0, new ProtocollazioneCommand()));
	    }
	}
	if (!ivs.isEmpty()) {
	    this.throwValidationMessages(ivs);
	}
    }

    @Override
    public List<String> getListaStampanti(String token) {

	PrintService[] printServices = PrinterJob.lookupPrintServices();
	if (printServices == null) {
	    return new ArrayList<String>();
	}
	List<String> listaStampanti = new ArrayList<String>();
	log.debug("getListaStampanti: numero di stampanti trovate {}", printServices.length);
	for (int i = 0; i < printServices.length; i++) {
	    listaStampanti.add(printServices[i].getName());
	    log.debug("getListaStampanti: stampante: {}", printServices[i]);
	}
	return listaStampanti;
    }

    @Override
    public DatiProtocolloResponseType protocollaMovimento(Movimenti movimento, String token) {

	return protocollaMovimento(movimento, token, null);
    }

    @Override
    public DatiProtocolloResponseType protocollaMovimento(Movimenti movimento, String token, DatiAnagraficiType amministrazioneMittente) {

	Integer codiceMovimento = movimento.getId().getCodice();
	movimento = movimentiService.findById(new PkId(codiceMovimento));
	if (!StringUtils.isBlank(movimento.getNumeroprotocollo())) {
	    return null;
	}
	Istanze istanza = movimento.getIstanza();
	boolean istanzaProtocollata = StringUtils.isNotBlank(istanza.getNumeroprotocollo());
	String codiceComune = istanza.getComune().getCodicecomune();
	String software = istanza.getSoftware().getCodice();
	boolean isProtocolloAttivo = new VerticalizzazioneProtocolloAttivoServiceImpl(verticalizzazioniService, codiceComune).isAttiva();
	if (!isProtocolloAttivo) {
	    return null;
	}
	log.debug("chiamo il servizio di protocollazione per il movimento: [{}], token: [{}]", new Object[] { codiceMovimento, token });
	try {
	    //
	    IVerticalizzazioneProtocolloAttivoService protAttivoService = new VerticalizzazioneProtocolloAttivoServiceImpl(
		    this.verticalizzazioniService, codiceComune);
	    //
	    ProtocollazioneCommand protocollazioneCommand = new ProtocollazioneCommand();
	    protocollazioneCommand.setProvenienza(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI);
	    //
	    List<ProtocolloSoggettoCommand> mittenti = new ArrayList<ProtocolloSoggettoCommand>();
	    //
	    String flusso = new FlussoResolver(this.tipimovStcMappingService, this.amministrazioniProtocolloService, protAttivoService,
		    amministrazioneMittente, istanzaProtocollata, codiceComune, software).resolveDaMovimento(movimento);
	    //
	    if (amministrazioneMittente != null) {
		String codAmm = amministrazioneMittente.getCod();
		if (!StringUtils.isBlank(codAmm)) {
		    Amministrazioni amm = this.amministrazioniService.findById(new PkId(Integer.parseInt(codAmm)));
		    ProtocolloMezzi mezzo = StringUtils.isNotBlank(amministrazioneMittente.getMezzo())
			    ? new ProtocolloMezzi(amministrazioneMittente.getMezzo())
			    : protAttivoService.getMezzoDefault();
		    ProtocolloModalitainvio modalita = StringUtils.isNotBlank(amministrazioneMittente.getModalitaTrasmissione())
			    ? new ProtocolloModalitainvio(amministrazioneMittente.getModalitaTrasmissione())
			    : protAttivoService.getModalitaTrasmissioneDefault();
		    mittenti.add(ProtocolloSoggettoCommand.fromAmministrazione(amm, mezzo, modalita));
		}
	    } else {
		MittentiResolver mittResolver = new MittentiResolver(this.alberoprocService, this.alberoprocProtocolloService, protAttivoService,
			this.amministrazioniService, this.amministrazioniProtocolloService, this.tipisoggettopeopleService, codiceComune, istanza,
			flusso);
		mittenti = mittResolver.resolve();
	    }
	    protocollazioneCommand.setMittentis(mittenti);
	    protocollazioneCommand.setFlusso(flusso);
	    //
	    String tipoDocumento = new TipoDocumentoResolver(this.alberoprocService, this.alberoprocProtocolloService, protAttivoService,
		    ProtocolloSourceEnum.ON_LINE, istanza).resolve();
	    protocollazioneCommand.setTipoDocumento(tipoDocumento);
	    //
	    String smistamento = new SmistamentoResolver(protAttivoService).resolve();
	    protocollazioneCommand.setSmistamento(smistamento);
	    //
	    OggettoECorpoMailProtocollo oggCorpoMail = new OggettoECorpoMailResolver(this.alberoprocService, this.alberoprocProtocolloService,
		    this.protConfigService, this.mailtipoService, AmbitoProtocollazioneEnum.DA_MOVIMENTO, false, istanza, movimento).resolve();
	    protocollazioneCommand.setOggettoProtocolloMail(oggCorpoMail.getOggetto());
	    protocollazioneCommand.setCorpoProtocolloMail(oggCorpoMail.getCorpo());
	    //
	    String oggetto = new OggettoResolver(protAttivoService, oggCorpoMail).resolve();
	    protocollazioneCommand.setOggetto(oggetto);
	    //
	    //
	    String classifica = new ClassificaResolver(this.alberoprocService, this.alberoprocProtocolloService, protAttivoService, istanza)
		    .resolve();
	    protocollazioneCommand.setClassifica(classifica);
	    //
	    DestinatariResolver destResolver = new DestinatariResolver(this.alberoprocService, this.alberoprocProtocolloService,
		    this.amministrazioniService, protAttivoService, this.tipisoggettopeopleService, protocollazioneCommand.getFlusso(), istanza);
	    protocollazioneCommand.setDestinataris(destResolver.resolve());
	    //
	    DatiRequestType dati = this.generaXml(protocollazioneCommand, movimento.getIstanza(), protAttivoService, codiceComune, software);
	    //
	    ProtocollazioneMovimentoXmlRequestType request = new ProtocollazioneMovimentoXmlRequestType();
	    request.setCodiceMovimento(String.valueOf(codiceMovimento));
	    request.setDati(dati);
	    request.setSource(ProtocolloSourceEnum.ON_LINE.getValue()); // su .NET era impostato così chiamando protocollazioneMovimento
	    request.setToken(token);
	    //
	    IProtocollazioneService prot = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    DatiProtocolloResponseType datiProtocollo = prot.protocollazioneMovimentoXml(request);
	    /*
	    DatiProtocolloResponseType datiProtocollo = prot.protocollazioneMovimento(token, String.valueOf(codiceMovimento),
	        mittentiDaSovrascrivere);
	    */
	    this.istanzeService.clear();
	    if (datiProtocollo == null) {
		return null;
	    }
	    log.debug("protocollaMovimento: datiProtocollo [{}]", datiProtocollo);
	    gestisciErroreType(datiProtocollo.getErrore(), true, "protocollaMovimento");
	    gestisciRiferimentiProtocolloInPECInbox(null, movimento, datiProtocollo);
	    if (StringUtils.isNotBlank(datiProtocollo.getWarning())) {
		inserisciEvento(null, movimento,
			"Errore durante la protocollazione del movimento. ProtocollazioneService#protocollaMovimento[WARNING: " +
						 datiProtocollo.getWarning() + "]");
	    }
	    if (lanciaEventoProtocollazioneEseguita(datiProtocollo.getNumeroProtocollo())) {
		Set<Integer> documenti = new HashSet<Integer>();
		if (datiProtocollo.getCodiciOggettoAllegati() != null) {
		    documenti.addAll(datiProtocollo.getCodiciOggettoAllegati().getInt());
		}
		this.eventPublisher.publish(new EventoMovimentoProtocollato(codiceMovimento, documenti));
		this.setStampigliaturaProt(movimento, null, datiProtocollo);
	    }
	    return datiProtocollo;
	} catch (Exception e) {
	    inserisciEvento(null, movimento,
		    "Errore durante la protocollazione del movimento. ProtocollazioneService#protocollaMovimento[" + e.getMessage() + "]");
	    FlashMessages.getWarnings().add("Impossibile contattare il servizio di protocollazione a causa:" + e.getMessage());
	    return null;
	}
    }

    //TODO: Verificare
    @Override
    public DatiProtocolloResponseType protocollaComunicazioneGraduatoria(Movimenti movimento, String token) {

	movimento = movimentiService.findById(new PkId(movimento.getId().getCodice()));
	if (!StringUtils.isBlank(movimento.getNumeroprotocollo())) {
	    return null;
	}
	String codiceComune = movimento.getIstanza().getComune().getCodicecomune();
	boolean isProtocollo = new VerticalizzazioneProtocolloAttivoServiceImpl(verticalizzazioniService, codiceComune).isAttiva();
	if (!isProtocollo) {
	    return null;
	}
	String datiProtocolloString = null;
	if (log.isDebugEnabled()) {
	    log.debug("chiamo il servizio di protocollaComunicazioneGraduatoria per il movimento: [{}], token: [{}]",
		    new Object[] { movimento.getId().getCodice(), token });
	}
	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    DatiProtocolloResponseType datiProtocollo = port.protocollazioneComunicazioneGraduatoria(token,
		    String.valueOf(movimento.getId().getCodice()));
	    this.istanzeService.clear();
	    log.debug("protocollaMovimento: datiProtocolloString [{}]", datiProtocolloString);
	    if (datiProtocollo != null) {
		gestisciErroreType(datiProtocollo.getErrore(), true, "protocollaComunicazioneGraduatoria");
		gestisciRiferimentiProtocolloInPECInbox(null, movimento, datiProtocollo);
		if (StringUtils.isNotBlank(datiProtocollo.getWarning())) {
		    inserisciEvento(null, movimento,
			    "Errore durante la chiamata a protocollaComunicazioneGraduatoria. ProtocollazioneService#protocollaComunicazioneGraduatoria[WARNING: " +
						     datiProtocollo.getWarning() + "]");
		}
	    }
	    return datiProtocollo;
	} catch (Exception e) {
	    inserisciEvento(null, movimento,
		    "Errore durante la protocollazione del movimento. ProtocollazioneService#protocollaComunicazioneGraduatoria[" + e.getMessage() +
					     "]");
	    FlashMessages.getWarnings().add("Impossibile contattare il servizio di protocollazione a causa:" + e.getMessage());
	}
	return null;
    }

    @Override
    public DatiProtocolloResponseType protocollaComunicazione(Movimenti movimento, String token) {

	log.debug("protocollaComunicazione# start ....");
	movimento = movimentiService.findById(new PkId(movimento.getId().getCodice()));
	String codiceComune = movimento.getIstanza().getComune().getCodicecomune();
	boolean isProtocollo = new VerticalizzazioneProtocolloAttivoServiceImpl(verticalizzazioniService, codiceComune).isAttiva();
	DatiProtocolloResponseType datiProtocollo = new DatiProtocolloResponseType();
	if (isProtocollo) {
	    log.debug("protocollaComunicazione# protocollazione attiva ...");
	    if (StringUtils.isBlank(movimento.getNumeroprotocollo())) {
		String datiProtocolloString = null;
		if (log.isDebugEnabled()) {
		    log.debug("protocollaComunicazione# chiamo il servizio di protocollaComunicazione per il movimento: [{}], token: [{}]",
			    new Object[] { movimento.getId().getCodice(), token });
		}
		try {
		    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
		    datiProtocollo = port.protocollazioneComunicazioneGraduatoria(token, String.valueOf(movimento.getId().getCodice()));
		    this.istanzeService.clear();
		    log.debug("protocollaComunicazione# datiProtocolloString [{}]", datiProtocolloString);
		    if (datiProtocollo != null) {
			gestisciErroreType(datiProtocollo.getErrore(), true, "protocollaComunicazione");
			gestisciRiferimentiProtocolloInPECInbox(null, movimento, datiProtocollo);
			if (StringUtils.isNotBlank(datiProtocollo.getWarning())) {
			    inserisciEvento(null, movimento,
				    "Errore durante la chiamata a protocollaComunicazioneGraduatoria. ProtocollazioneService#protocollaComunicazioneGraduatoria[WARNING: " +
							     datiProtocollo.getWarning() + "]");
			}
		    }
		    return datiProtocollo;
		} catch (Exception e) {
		    inserisciEvento(null, movimento,
			    "Errore durante la protocollazione del movimento. ProtocollazioneService#protocollaComunicazione[" + e.getMessage() +
						     "]");
		}
	    }
	}
	log.debug("protocollaComunicazione# end ....");
	return datiProtocollo;
    }

    @Override
    public DatiFascicoloResponseType fascicolaMovimento(String token, Movimenti movimento) {

	movimento = movimentiService.findById(new PkId(movimento.getId().getCodice()));
	if (StringUtils.isBlank(movimento.getNumeroprotocollo())) {
	    return null;
	}
	String codiceComune = movimento.getIstanza().getComune().getCodicecomune();
	boolean isProtocollo = new VerticalizzazioneProtocolloAttivoServiceImpl(verticalizzazioniService, codiceComune).isAttiva();
	if (!isProtocollo) {
	    return null;
	}
	try {
	    log.debug("chiamo il servizio di protocollazione per il movimento: [{}], token: [{}]",
		    new Object[] { movimento.getId().getCodice(), token });
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    DatiFascicoloResponseType datiProtocollo = port.fascicolazioneMovimento(token, String.valueOf(movimento.getId().getCodice()));
	    this.istanzeService.clear();
	    if (datiProtocollo != null) {
		gestisciErroreType(datiProtocollo.getErrore(), true, "fascicolaMovimento");
		if (StringUtils.isNotBlank(datiProtocollo.getWarning())) {
		    inserisciEvento(null, movimento, "Errore durante la fascicolazione. ProtocollazioneService#fascicolaMovimento[WARNING: " +
						     datiProtocollo.getWarning() + "]");
		}
	    }
	    return datiProtocollo;
	} catch (Exception e) {
	    inserisciEvento(null, movimento, "Errore durante la fascicolazione. ProtocollazioneService#fascicolaMovimento[" + e.getMessage() + "]");
	    log.error("Impossibile contattare il servizio di protocollazione a causa: {}", e.getMessage());
	    FlashMessages.getWarnings().add("Impossibile contattare il servizio di protocollazione a causa:" + e.getMessage());
	}
	return null;
    }

    @Override
    public DatiFascicoloResponseType fascicolaMovimentoXml(String token, ProtocollazioneCommand protocollazioneCommand)
	    throws FascicolaMovimentoException {

	Movimenti movimento = movimentiService.findById(new PkId(protocollazioneCommand.getMovimento().getId().getCodice()));
	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    DatiFascType sFile = generaFascicoloRequest(protocollazioneCommand);
	    DatiFascicoloResponseType response = port.fascicolazioneMovimentoXml(token, String.valueOf(movimento.getId().getCodice()), sFile);
	    this.istanzeService.clear();
	    if (response != null) {
		gestisciErroreType(response.getErrore(), true, "fascicolaMovimentoXml");
		if (StringUtils.isNotBlank(response.getWarning())) {
		    inserisciEvento(null, movimento,
			    "Errore durante la fascicolazione. ProtocollazioneService#fascicolaMovimentoXml[WARNING: " + response.getWarning() + "]");
		}
	    }
	    return response;
	} catch (Exception e) {
	    inserisciEvento(null, movimento,
		    "Errore durante la fascicolazione. ProtocollazioneService#fascicolaMovimentoXml[" + e.getMessage() + "]");
	    log.error("fascicolaMovimentoXml: {}", e.getMessage());
	    throw new FascicolaMovimentoException(e);
	}
    }

    @Override
    public boolean isLeggiProtocollo(String codiceComune) {

	Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONELEGGI, codiceComune);
	if (vp != null && vp.getVerticalizzazioniparametribase().getId().getParametro()
		.equalsIgnoreCase(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONELEGGI)) {
	    String valore = StringUtils.defaultIfEmpty(vp.getValore(), "0");
	    return BooleanUtils.toBoolean(valore);
	}
	return false;
    }

    @Override
    public DatiProtocolloResponseType creaCopie(Integer codiceistanza, String token) throws CreaCopieException {

	if (codiceistanza == null) {
	    return null;
	}
	DynaProperty[] properties = { new DynaProperty("comune_codicecomune", String.class) };
	DynaClass userDynaClass = new BasicDynaClass("IstanzeDC", null, properties);
	DynaBean istanzaDC = istanzeDAO.findDynaBeanById(ORMHelper.getIdcomune(), codiceistanza, userDynaClass, Istanze.class);
	String codiceComune = (String) istanzaDC.get("comune_codicecomune");
	boolean isProtocollo = new VerticalizzazioneProtocolloAttivoServiceImpl(verticalizzazioniService, codiceComune).isAttiva();
	if (!isProtocollo) {
	    return null;
	}
	if (log.isDebugEnabled()) {
	    log.debug("chiamo il servizio di creaCopie per l'istanza: [{}-{}], token: [{}]",
		    new Object[] { ORMHelper.getIdcomune(), codiceistanza, token });
	}
	Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	if (istanza == null) {
	    log.error("ProtocollazioneService#creaCopie: Non è stata trova l'istanza con id[{}-{}]", ORMHelper.getIdcomune(), codiceistanza);
	    throw new CreaCopieException(
		    "ProtocollazioneService#creaCopie: Non è stata trova l'istanza con id[" + ORMHelper.getIdcomune() + "-" + codiceistanza + "]");
	}
	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    DatiProtocolloResponseType datiProtocollo = port.creaCopie(token, String.valueOf(codiceistanza), "");
	    this.istanzeService.clear();
	    if (datiProtocollo != null) {
		gestisciErroreType(datiProtocollo.getErrore(), true, "creaCopie");
		if (StringUtils.isNotBlank(datiProtocollo.getWarning())) {
		    inserisciEvento(istanza, null,
			    "Errore durante il creaCopie. ProtocollazioneService#creaCopie[WARNING: " + datiProtocollo.getWarning() + "]");
		}
	    }
	    return datiProtocollo;
	} catch (Exception e) {
	    inserisciEvento(istanza, null, "Errore durante il creaCopie. ProtocollazioneService#creaCopie[" + e.getMessage() + "]");
	    log.error("creaCopie: {}", e.getMessage());
	    FlashMessages.getWarnings().add("Impossibile contattare il servizio di protocollazione a causa: " + e.getMessage());
	}
	return null;
    }

    @Override
    public AllegatoResponseType leggiAllegato(String token, String idAllegato, String software, String codiceComune) {

	boolean isProtocollo = verticalizzazioniService.isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE);
	if (isProtocollo) {
	    AllegatoResponseType response = null;
	    try {
		IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
		response = port.leggiAllegato(token, idAllegato, software, codiceComune);
		gestisciErroreType(response.getErrore(), true, "leggiAllegato");
		return response;
	    } catch (Exception e) {
		log.error("leggiAllegato: {}", e.getMessage());
		FlashMessages.getWarnings().add("Impossibile contattare il servizio di protocollazione a causa: " + e.getMessage());
	    }
	}
	return null;
    }

    @Override
    public AllegatoResponseType leggiAllegatoUORuolo(String token, String idAllegato, String uo, String ruolo, String software, String codiceComune) {

	AllegatoResponseType response = null;
	try {
	    boolean isProtocollo = verticalizzazioniService.isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE);
	    if (isProtocollo) {
		IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
		response = port.leggiAllegatoUORuolo(token, idAllegato, uo, ruolo, software, codiceComune);
		gestisciErroreType(response.getErrore(), true, "leggiAllegatoUORuolo");
	    }
	} catch (Exception e) {
	    log.error("leggiAllegatoUORuolo: {}", e.getMessage());
	    FlashMessages.getWarnings().add("Impossibile contattare il servizio di protocollazione a causa: " + e.getMessage());
	}
	return response;
    }

    private void gestisciRiferimentiProtocolloInPECInbox(Istanze istanza, Movimenti movimento, DatiProtocolloResponseType datiProtocollo) {

	if (datiProtocollo != null) {
	    String numeroprotocollo = StringUtils.defaultString(datiProtocollo.getNumeroProtocollo());
	    String dataprotocollo = StringUtils.defaultString(datiProtocollo.getDataProtocollo());
	    String fkidprotocollo = StringUtils.defaultString(datiProtocollo.getIdProtocollo());
	    if (StringUtils.isNotBlank(numeroprotocollo) && StringUtils.isNotBlank(dataprotocollo)) {
		List<PecInbox> pis = null;
		if (istanza != null) {
		    Integer codiceIstanza = istanza.getId().getCodice();
		    pis = pecInboxService.findByIstanza(codiceIstanza, null, null);
		} else if (movimento != null) {
		    Integer codiceMovimento = movimento.getId().getCodice();
		    pis = pecInboxService.findByMovimento(codiceMovimento, null, null);
		}
		if (pis != null) {
		    for (PecInbox pi : pis) {
			if (StringUtils.isBlank(StringUtils.defaultString(pi.getNumeroprotocollo()))) {
			    pi.setNumeroprotocollo(numeroprotocollo);
			    pi.setIdprotocollo(fkidprotocollo);
			    if (StringUtils.isNotBlank(dataprotocollo)) {
				Calendar d = Utilities.getDate(dataprotocollo, WebConstants.DATE_FORMAT_PATTERN);
				pi.setDataprotocollo(d.getTime());
			    }
			    pecInboxService.update(pi);
			}
		    }
		}
	    }
	}
    }

    private String getClassificheCacheKey(String software, String codiceComune) {

	String locCodiceComune = codiceComune;
	if (StringUtils.isBlank(codiceComune)) {
	    locCodiceComune = "TUTTI";
	}
	return "CLASSIFICHE-" + ORMHelper.getIdcomuneAlias() + "-" + software + locCodiceComune;
    }

    private String getTipiDocumentoCacheKey(String software, String codiceComune) {

	String locCodiceComune = codiceComune;
	if (StringUtils.isBlank(codiceComune)) {
	    locCodiceComune = "TUTTI";
	}
	return "TIPIDOCUMENTO-" + ORMHelper.getIdcomuneAlias() + "-" + software + locCodiceComune;
    }

    @Override
    public CodiceDescrizioneBean[] getListaClassifiche(String software, String codiceComune) throws RecuperaClassificheException {

	Map<String, CodiceDescrizioneBean[]> map = getListaClassificheMap();
	CodiceDescrizioneBean[] result = null;
	String cachekey = getClassificheCacheKey(software, codiceComune);
	if (map.get(cachekey) != null) {
	    result = map.get(cachekey);
	} else {
	    try {
		IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
		ListaTipiClassificaType response = port.getClassifiche(ORMHelper.getToken(), software, codiceComune);
		if (response != null) {
		    gestisciErroreType(response.getErrore(), true, "getListaClassifiche");
		    if (response.getClassifica() != null && !response.getClassifica().getListaTipiClassificaClassifica().isEmpty()) {
			List<ListaTipiClassificaClassifica> list = response.getClassifica().getListaTipiClassificaClassifica();
			result = new CodiceDescrizioneBean[list.size()];
			int i = 0;
			for (ListaTipiClassificaClassifica c : list) {
			    CodiceDescrizioneBean cb = new CodiceDescrizioneBean();
			    cb.setCodice(c.getCodice());
			    cb.setDescrizione(c.getDescrizione());
			    result[i] = cb;
			    i++;
			}
		    }
		}
		map.put(cachekey, result);
	    } catch (Exception e) {
		log.error("getListaClassifiche: {}", e.getMessage());
		throw new RecuperaClassificheException("Errore nel recupero delle informazioni dal webservice di protocollazione: " + e.getMessage(),
			e);
	    }
	}
	return result;
    }

    private Map<String, CodiceDescrizioneBean[]> listaClassificheMap = new HashMap<String, CodiceDescrizioneBean[]>();

    private Map<String, CodiceDescrizioneBean[]> getListaClassificheMap() {

	if (listaClassificheMap == null) {
	    listaClassificheMap = new HashMap<String, CodiceDescrizioneBean[]>();
	}
	return listaClassificheMap;
    }

    private Map<String, CodiceDescrizioneBean[]> listaTipiDocumentoMap = new HashMap<String, CodiceDescrizioneBean[]>();

    private Map<String, CodiceDescrizioneBean[]> getListaTipiDocumentoMap() {

	if (listaTipiDocumentoMap == null) {
	    listaTipiDocumentoMap = new HashMap<String, CodiceDescrizioneBean[]>();
	}
	return listaTipiDocumentoMap;
    }

    @Override
    public CodiceDescrizioneBean[] getListaTipiDocumento(String software, String codiceComune) throws RecuperaTipiDocumentoException {

	Map<String, CodiceDescrizioneBean[]> map = getListaTipiDocumentoMap();
	CodiceDescrizioneBean[] result = null;
	String cachekey = getTipiDocumentoCacheKey(software, codiceComune);
	if (map.get(cachekey) != null) {
	    result = map.get(cachekey);
	} else {
	    try {
		IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
		ListaTipiDocumentoResponseType response = port.getTipiDocumento(ORMHelper.getToken(), software, codiceComune);
		if (response != null) {
		    gestisciErroreType(response.getErrore(), true, "getListaTipiDocumento");
		    if (response.getDocumento() != null && !response.getDocumento().getListaTipiDocumentoDocumentoType().isEmpty()) {
			List<ListaTipiDocumentoDocumentoType> list = response.getDocumento().getListaTipiDocumentoDocumentoType();
			result = new CodiceDescrizioneBean[list.size()];
			int i = 0;
			for (ListaTipiDocumentoDocumentoType c : list) {
			    CodiceDescrizioneBean cb = new CodiceDescrizioneBean();
			    cb.setCodice(c.getCodice());
			    cb.setDescrizione(c.getDescrizione());
			    result[i] = cb;
			    i++;
			}
		    }
		}
		map.put(cachekey, result);
	    } catch (Exception e) {
		log.error("getListaTipiDocumento: {}", e.getMessage());
		throw new RecuperaTipiDocumentoException("Errore nel recupero delle informazioni dal webservice di protocollazione: " + e);
	    }
	}
	return result;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	listaClassificheMap = new HashMap<String, CodiceDescrizioneBean[]>();
	listaTipiDocumentoMap = new HashMap<String, CodiceDescrizioneBean[]>();
    }

    @Override
    public DatiProtocolloLettoResponseType leggiProtocolloConData(String token, String numeroProtocollo, Date dataProtocollo, String idProtocollo,
	    String software, String codiceComune) throws LeggiProtocolloException {

	DatiProtocolloLettoResponseType protocollo = null;
	try {
	    if (dataProtocollo == null) {
		throw new BusinessValidationException("La data di protocollo non puo' essere nulla");
	    }
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    ArrayOfDatiProtocolloLettoResponseType protocollos = port.leggiProtocolloConData(token, idProtocollo,
		    Utilities.getXMLGregorianCalendar(dataProtocollo), numeroProtocollo, software, codiceComune);
	    protocollo = checkProtocolloUnico(protocollos);
	    gestisciErroreType(protocollo.getErrore(), true, "leggiProtocolloConData");
	    log.debug("leggiProtocolloConData: protocollo [{}]", protocollo);
	    if (protocollo.getErrore() != null) {
		log.error("leggiProtocolloConData: {}, {}", protocollo.getErrore().getDescrizione(), protocollo.getErrore().getStackTrace());
		throw new LeggiProtocolloException(
			"Errore nella chiamata al webservice leggiProtocolloConData: " + protocollo.getErrore().getDescrizione());
	    }
	    return protocollo;
	} catch (Exception e) {
	    log.error("leggiProtocolloConData: {}", e.getMessage());
	    throw new LeggiProtocolloException("Errore nella chiamata al webservice leggiProtocolloConData=" + e.getMessage());
	}
    }

    @Override
    public DatiProtocolloLettoResponseType leggiProtocollo(String token, String numeroProtocollo, String annoProtocollo, String idProtocollo,
	    String software, String codiceComune) throws LeggiProtocolloException {

	DatiProtocolloLettoResponseType protocollo = null;
	LeggiProtocolloRequest leggiProtocolloRequest = leggiProtocolloRequest(token, idProtocollo, annoProtocollo, numeroProtocollo, software,
		codiceComune);
	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    ArrayOfDatiProtocolloLettoResponseType protocollos = port.leggiProtocollo(leggiProtocolloRequest);
	    protocollo = checkProtocolloUnico(protocollos);
	    gestisciErroreType(protocollo.getErrore(), true, "leggiProtocollo");
	    log.debug("leggiProtocollo: protocollo [{}]", protocollo);
	    if (protocollo.getErrore() != null) {
		log.error("leggiProtocollo: {}, {}", protocollo.getErrore().getDescrizione(), protocollo.getErrore().getStackTrace());
		throw new LeggiProtocolloException("Errore nella chiamata al webservice leggiprotocollo: " + protocollo.getErrore().getDescrizione());
	    }
	    return protocollo;
	} catch (Exception e) {
	    log.error("leggiProtocollo: errore nella lettura del protocollo con i dati: " +
		      ReflectionToStringBuilder.toString(leggiProtocolloRequest, ToStringStyle.SHORT_PREFIX_STYLE),
		    e);
	    throw new LeggiProtocolloException("Errore nella lettura del protocollo con i dati: " +
					       ReflectionToStringBuilder.toString(leggiProtocolloRequest, ToStringStyle.SHORT_PREFIX_STYLE),
		    e);
	}
    }

    private DatiProtocolloLettoResponseType checkProtocolloUnico(ArrayOfDatiProtocolloLettoResponseType protocollos)
	    throws FunzioneBusinessRemotaException {

	if (protocollos.getDatiProtocolloLettoResponseType().isEmpty()) {
	    throw new FunzioneBusinessRemotaException("La lettura del protocollo non è andata a buon fine. Nessun dato letto per i parametri ");
	}
	if (protocollos.getDatiProtocolloLettoResponseType().size() > 1) {
	    throw new FunzioneBusinessRemotaException("La lettura del protocollo non è andata a buon fine. Sono tornati " +
						      protocollos.getDatiProtocolloLettoResponseType().size() + " record per i parametri ");
	}
	return protocollos.getDatiProtocolloLettoResponseType().get(0);
    }

    private LeggiProtocolloRequest leggiProtocolloRequest(String token, String idProtocollo, String annoProtocollo, String numeroProtocollo,
	    String software, String codiceComune) {

	LeggiProtocolloRequest leggiProtocolloRequest = new LeggiProtocolloRequest();
	leggiProtocolloRequest.setAnnoProtocollo(annoProtocollo);
	leggiProtocolloRequest.setCodiceComune(codiceComune);
	leggiProtocolloRequest.setIdProtocollo(idProtocollo);
	leggiProtocolloRequest.setNumeroProtocollo(numeroProtocollo);
	leggiProtocolloRequest.setSoftware(software);
	leggiProtocolloRequest.setToken(token);
	return leggiProtocolloRequest;
    }

    @Override
    public DatiProtocolloLettoResponseType leggiProtocolloUORuolo(String token, String numeroProtocollo, String annoProtocollo, String idProtocollo,
	    String uo, String ruolo, String software, String codiceComune) throws LeggiProtocolloException {

	DatiProtocolloLettoResponseType protocollo = null;
	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    ArrayOfDatiProtocolloLettoResponseType protocollos = port.leggiProtocolloUORuolo(token, idProtocollo, annoProtocollo, numeroProtocollo,
		    uo, ruolo, software, codiceComune);
	    protocollo = checkProtocolloUnico(protocollos);
	    gestisciErroreType(protocollo.getErrore(), true, "leggiProtocollo");
	    log.debug("leggiProtocollo: protocollo [{}]", protocollo);
	    if (protocollo.getErrore() != null) {
		log.error("leggiProtocollo: {}, {}", protocollo.getErrore().getDescrizione(), protocollo.getErrore().getStackTrace());
		throw new LeggiProtocolloException("Errore nella chiamata al webservice leggiprotocollo: " + protocollo.getErrore().getDescrizione());
	    }
	    return protocollo;
	} catch (Exception e) {
	    log.error("leggiProtocollo: {}", e.getMessage());
	    throw new LeggiProtocolloException("Errore nella chiamata al webservice leggiprotocollo=" + e.getMessage(), e);
	}
    }

    @Override
    public InserimentoPraticaResponse insertPraticaSTCDaSistemaEsterno(AzioniProtocollazioneCommand cmd) throws FunzioneBusinessRemotaException {

	log.debug("insertPraticaSTCDaSistemaEsterno# start...");
	InserimentoPraticaRequest stcRequest = new InserimentoPraticaRequest();
	log.debug("insertPraticaSTCDaSistemaEsterno# Recupero i parametri di verticalizzazione ");
	Verticalizzazioniparametri vertParamMitt = this.verticalizzazioniService
		.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_PRATICA_DA_SISTEMA_ESTERNO, WebConstants.VERTICALIZZAZIONE_NLA_IDNODO);
	Verticalizzazioniparametri vertParamDest = this.verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_PRATICA_DA_SISTEMA_ESTERNO, WebConstants.VERTICALIZZAZIONE_IDNODO_INFOCAMERE);
	if (null == vertParamMitt) {
	    throw new FunzioneBusinessRemotaException(
		    "Impossibile inviare a STC la richiesta di creazione della pratica: manca la verticalizzazione PRATICA_DA_SISTEMA_ESTERNO.NLA_IDNODO");
	} else if (null == vertParamDest) {
	    throw new FunzioneBusinessRemotaException(
		    "Impossibile inviare a STC la richiesta di creazione della pratica: manca la verticalizzazione PRATICA_DA_SISTEMA_ESTERNO.IDNODO_INFOCAMERE");
	} else {
	    SportelloType mitt = new SportelloType();
	    mitt.setIdNodo(vertParamMitt.getValore());
	    mitt.setIdEnte(ORMHelper.getIdcomuneAlias());
	    mitt.setIdSportello(ORMHelper.getSoftware());
	    stcRequest.setSportelloMittente(mitt);
	    SportelloType dest = new SportelloType();
	    dest.setIdNodo(vertParamDest.getValore());
	    dest.setIdEnte(ORMHelper.getIdcomuneAlias());
	    dest.setIdSportello(ORMHelper.getSoftware());
	    stcRequest.setSportelloDestinatario(dest);
	    DettaglioPraticaType datiPratica = new DettaglioPraticaType();
	    log.debug("insertPraticaSTCDaSistemaEsterno# Creo idPratica e numeroPratica per salvare i riferimenti su STC");
	    String idPratica = "PROT_SISTEMA_ESTERNO_" + cmd.getDatiProtocolloLetto().getNumeroProtocollo() + "_DEL_" +
			       cmd.getDatiProtocolloLetto().getAnnoProtocollo();
	    if (StringUtils.isNotBlank(cmd.getDatiProtocolloLetto().getIdProtocollo())) {
		idPratica += "_ID_" + cmd.getDatiProtocolloLetto().getIdProtocollo();
	    }
	    idPratica = Utilities.getHashText(idPratica, Utilities.ALGORITHM_SHA1, true);
	    datiPratica.setIdPratica(idPratica);
	    datiPratica.setNumeroPratica(idPratica);
	    //imposto il richiedente
	    log.debug(
		    "insertPraticaSTCDaSistemaEsterno# Imposto richiedente fittizio per superare la validazione STC, in fase di inserimento verrà recuperato dall'xml della pratica suap in rete ");
	    RichiedenteType richiedenteStc = new RichiedenteType();
	    AnagrafeType stcAna = new AnagrafeType();
	    PersonaFisicaType pf = new PersonaFisicaType();
	    pf.setNome("Dummy nome");
	    pf.setCognome("Dummy cognome");
	    pf.setCodiceFiscale("0000000000000000");
	    stcAna.setPersonaFisica(pf);
	    richiedenteStc.setAnagrafica(stcAna.getPersonaFisica());
	    datiPratica.setRichiedente(richiedenteStc);
	    log.debug("insertPraticaSTCDaSistemaEsterno# Setto il valore della data protocollo letto da interfaccia web");
	    if (null != cmd.getDatiProtocolloLetto().getDataProtocollo()) {
		GregorianCalendar gc = new GregorianCalendar();
		Date d = Utilities.parseDateString(cmd.getDatiProtocolloLetto().getDataProtocollo(), false);
		gc.setTime(d);
		XMLGregorianCalendar dataprot = Utilities.getXMLGregorianCalendar(gc);
		datiPratica.setDataProtocolloGenerale(dataprot);
		log.debug(
			"insertPraticaSTCDaSistemaEsterno# Popolo la data e ora dell'inserimento pratica come data protocollo gg/MM/dd  ora protocollo 00:00");
		datiPratica.setDataPratica(dataprot);
		datiPratica.setOraDataPratica("00:00");
	    }
	    log.debug("insertPraticaSTCDaSistemaEsterno# Setto il valore del numero protocollo letto da interfaccia web");
	    if (StringUtils.isNotBlank(cmd.getDatiProtocolloLetto().getNumeroProtocollo())) {
		datiPratica.setNumeroProtocolloGenerale(cmd.getDatiProtocolloLetto().getNumeroProtocollo());
	    }
	    log.debug("insertPraticaSTCDaSistemaEsterno# Setto il valore del comune scelto dall' interfaccia web");
	    Comuni comuneIn = cmd.getEntity().getComune();
	    if (null != comuneIn) {
		Comuni comuneOut = this.comuniService.findByCodiceComune(comuneIn);
		if (null != comuneOut) {
		    ComuneType codComune = new ComuneType();
		    codComune.setCodiceIstat(comuneOut.getCodiceistat());
		    codComune.setComune(comuneOut.getComune());
		    codComune.setCodiceCatastale(comuneOut.getCodicecomune());
		    datiPratica.setCodiceComune(codComune);
		    cmd.getEntity().setComune(comuneOut);
		} else {
		    log.warn("creaIstanza - impossibile trovare il comune avente codice {}.", new Object[] { comuneIn.getCodicecomune() });
		}
	    }
	    //allegati
	    log.debug("insertPraticaSTCDaSistemaEsterno# Carico l'allegato presente sull'interfaccia web");
	    List<DocumentiType> stcDocs = datiPratica.getDocumenti();
	    //		//se richiesto dall'utente scompatto gli allegati compressi
	    File tempDir = Utilities.getSystemTempDir();
	    tempDir = new File(tempDir, proto_temp_unzip_dir + cmd.getDatiProtocolloLetto().getNumeroProtocollo() + "_" +
					cmd.getDatiProtocolloLetto().getAnnoProtocollo() + "_" + System.currentTimeMillis());
	    if (cmd.isInviaAllegati()) {
		ArrayOfAllegatoResponseType all = cmd.getDatiProtocolloLetto().getAllegati();
		if (all != null) {
		    List<AllegatoResponseType> alls = all.getAllegatoResponseType();
		    if (!alls.isEmpty()) {
			String codiceComune = comuneIn != null ? comuneIn.getCodicecomune() : null;
			List<AllegatoDataHandlerHelper> adhh = getAllegatiProtocolloPerIstanzaoMovimento(alls, cmd.isScompattaAllegati(), tempDir,
				ORMHelper.getSoftware(), codiceComune);
			for (AllegatoDataHandlerHelper allegato : adhh) {
			    DocumentiType stcDoc = new DocumentiType();
			    stcDoc.setId(allegato.getIdentificativo());
			    stcDoc.setDocumento(allegato.getNomeFile());
			    AllegatiType allType = new AllegatiType();
			    allType.setAllegato(allegato.getNomeFile());
			    AllegatoBinarioType abt = new AllegatoBinarioType();
			    abt.setBinaryData(allegato.getDataHandler());
			    abt.setFileName(allegato.getNomeFile());
			    String contentType = contenttypesService.findMimeTypeByFileName(allegato.getNomeFile());
			    abt.setMimeType(contentType);
			    allType.setFile(abt);
			    stcDoc.setAllegati(allType);
			    stcDoc.setTipoDocumento(TipoDocumentoType.ALTRO.value());
			    stcDocs.add(stcDoc);
			}
		    }
		}
	    }
	    stcRequest.setDettaglioPratica(datiPratica);
	    if (log.isDebugEnabled()) {
		log.debug("creaIstanza - invocazione in corso del servizio stc.inserimentoPratica.");
	    }
	    try {
		log.debug("insertPraticaSTCDaSistemaEsterno# Call stc.inserimentoPratica");
		InserimentoPraticaResponse response = this.stcWsClient.inserimentoPratica(stcRequest);
		log.debug("insertPraticaSTCDaSistemaEsterno# End...");
		return response;
	    } catch (Exception e) {
		log.error("insertPraticaSTCDaSistemaEsterno# errore alla chiamata di STC: ", e);
		throw new FunzioneBusinessRemotaException("Errore nella creazione della pratica causato da " + e.getMessage(), e);
	    }
	}
    }

    @Override
    public InserimentoPraticaResponse insertPraticaSTCDaSuapInRete(AzioniProtocollazioneCommand cmd)
	    throws FunzioneBusinessRemotaException, BusinessValidationException {

	log.debug("insertPraticaSTCDaSuapInRete# Start.......");
	List<String> errors = new ArrayList<String>();
	boolean notValid = false;
	InserimentoPraticaRequest stcRequest = new InserimentoPraticaRequest();
	log.debug("insertPraticaSTCDaSuapInRete# Recupero i parametri di verticalizzazione ");
	Verticalizzazioniparametri vertParamMitt = this.verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		WebConstants.VERTICALIZZAZIONE_STC_NLA_IDNODO);
	Verticalizzazioniparametri vertParamDest = this.verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE, WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_NODO_SUAP_INRETE);
	boolean verticalizzazioneElaboraAndAllinaSchedeNonCompatibile = false;
	Verticalizzazioniparametri vertParamElaboraDocumentiXML = this.verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE, WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_ELABORA_DOCUMENTI_XML);
	Verticalizzazioniparametri vertParamAllineaSchede = this.verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE, WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_ALLINEA_SCHEDE);
	if (vertParamElaboraDocumentiXML == null && vertParamAllineaSchede != null
		&& StringUtils.defaultIfEmpty(vertParamAllineaSchede.getValore(), "0").equals("1")) {
	    verticalizzazioneElaboraAndAllinaSchedeNonCompatibile = true;
	} else {
	    if (vertParamElaboraDocumentiXML != null && StringUtils.defaultIfEmpty(vertParamElaboraDocumentiXML.getValore(), "N").equals("N")
		    && vertParamAllineaSchede != null && StringUtils.defaultIfEmpty(vertParamAllineaSchede.getValore(), "0").equals("1")) {
		verticalizzazioneElaboraAndAllinaSchedeNonCompatibile = true;
	    }
	}
	if (null == vertParamMitt) {
	    throw new FunzioneBusinessRemotaException(
		    "Impossibile inviare a STC la richiesta di creazione della pratica: manca la verticalizzazione STC.NLA_IDNODO");
	} else if (null == vertParamDest) {
	    throw new FunzioneBusinessRemotaException(
		    "Impossibile inviare a STC la richiesta di creazione della pratica: manca la verticalizzazione FVG_SUAP_IN_RETE.FVG_SUAP_IN_RETE");
	} else if (verticalizzazioneElaboraAndAllinaSchedeNonCompatibile) {
	    throw new FunzioneBusinessRemotaException(
		    "Impossibile inviare a STC la richiesta di creazione della pratica: i valori delle verticalizzazioni " +
						      WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_ELABORA_DOCUMENTI_XML + " e " +
						      WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_ALLINEA_SCHEDE +
						      " non sono compatibili. Se " +
						      WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_ELABORA_DOCUMENTI_XML +
						      " ha valore nullo o 'N' non ha senso che il valore di " +
						      WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_ALLINEA_SCHEDE + " sia uguale ad 1");
	} else {
	    log.debug("insertPraticaSTCDaSuapInRete# Popolo sportello mittente [nodo/ente/sportello] {}/{}/{}",
		    new Object[] { vertParamMitt.getValore(), ORMHelper.getIdcomuneAlias(),
			    ORMHelper.getSoftware() + WebConstants.AZIONI_PROTOCOLLO_SUAP_IN_RETE_CLIENT_IDSPORTELLO_SUFFIX });
	    SportelloType mitt = new SportelloType();
	    mitt.setIdNodo(vertParamMitt.getValore());
	    mitt.setIdEnte(ORMHelper.getIdcomuneAlias());
	    mitt.setIdSportello(ORMHelper.getSoftware() + WebConstants.AZIONI_PROTOCOLLO_SUAP_IN_RETE_CLIENT_IDSPORTELLO_SUFFIX);
	    stcRequest.setSportelloMittente(mitt);
	    log.debug("insertPraticaSTCDaSuapInRete# Popolo sportello mittente [nodo/ente/sportello] {}/{}/{}",
		    new Object[] { vertParamDest.getValore(), ORMHelper.getIdcomuneAlias(),
			    ORMHelper.getSoftware() + WebConstants.AZIONI_PROTOCOLLO_SUAP_IN_RETE_CLIENT_IDSPORTELLO_SUFFIX });
	    SportelloType dest = new SportelloType();
	    dest.setIdNodo(vertParamDest.getValore());
	    dest.setIdEnte(ORMHelper.getIdcomuneAlias());
	    dest.setIdSportello(ORMHelper.getSoftware());
	    stcRequest.setSportelloDestinatario(dest);
	    DettaglioPraticaType datiPratica = new DettaglioPraticaType();
	    log.debug("insertPraticaSTCDaSuapInRete# Creo idPratica e numeroPratica per salvare i riferimenti su STC");
	    String idPratica = "PROT_SUAP_INRETE_" + cmd.getDatiProtocolloLetto().getNumeroProtocollo() + "_DEL_" +
			       cmd.getDatiProtocolloLetto().getAnnoProtocollo();
	    if (StringUtils.isNotBlank(cmd.getDatiProtocolloLetto().getIdProtocollo())) {
		idPratica += "_ID_" + cmd.getDatiProtocolloLetto().getIdProtocollo();
	    }
	    idPratica = Utilities.getHashText(idPratica, Utilities.ALGORITHM_SHA1, true);
	    datiPratica.setIdPratica(idPratica);
	    datiPratica.setNumeroPratica(idPratica);
	    //imposto il richiedente
	    log.debug(
		    "insertPraticaSTCDaSuapInRete# Imposto richiedente fittizio per superare la validazione STC, in fase di inserimento verrà recuperato dall'xml della pratica suap in rete ");
	    RichiedenteType richiedenteStc = new RichiedenteType();
	    AnagrafeType stcAna = new AnagrafeType();
	    PersonaFisicaType pf = new PersonaFisicaType();
	    pf.setNome("Dummy nome");
	    pf.setCognome("Dummy cognome");
	    pf.setCodiceFiscale("0000000000000000");
	    stcAna.setPersonaFisica(pf);
	    richiedenteStc.setAnagrafica(stcAna.getPersonaFisica());
	    datiPratica.setRichiedente(richiedenteStc);
	    if (!notValid) {
		log.debug("insertPraticaSTCDaSuapInRete# Setto il valore della data protocollo letto da interfaccia web");
		if (null != cmd.getDatiProtocolloLetto().getDataProtocollo()) {
		    GregorianCalendar gc = new GregorianCalendar();
		    Date d = Utilities.parseDateString(cmd.getDatiProtocolloLetto().getDataProtocollo(), false);
		    gc.setTime(d);
		    XMLGregorianCalendar dataprot = Utilities.getXMLGregorianCalendar(gc);
		    datiPratica.setDataProtocolloGenerale(dataprot);
		    log.debug(
			    "insertPraticaSTCDaSuapInRete# Popolo la data e ora dell'inserimento pratica come data protocollo gg/MM/dd  ora protocollo 00:00");
		    datiPratica.setDataPratica(dataprot);
		    datiPratica.setOraDataPratica("00:00");
		}
		log.debug("insertPraticaSTCDaSuapInRete# Setto il valore del numero protocollo letto da interfaccia web");
		if (StringUtils.isNotBlank(cmd.getDatiProtocolloLetto().getNumeroProtocollo())) {
		    datiPratica.setNumeroProtocolloGenerale(cmd.getDatiProtocolloLetto().getNumeroProtocollo());
		}
		log.debug("insertPraticaSTCDaSuapInRete# Setto il valore del comune scelto dall' interfaccia web");
		Comuni comuneIn = cmd.getEntity().getComune();
		if (null != comuneIn) {
		    Comuni comuneOut = this.comuniService.findByCodiceComune(comuneIn);
		    if (null != comuneOut) {
			ComuneType codComune = new ComuneType();
			codComune.setCodiceIstat(comuneOut.getCodiceistat());
			codComune.setComune(comuneOut.getComune());
			codComune.setCodiceCatastale(comuneOut.getCodicecomune());
			datiPratica.setCodiceComune(codComune);
			cmd.getEntity().setComune(comuneOut);
		    } else {
			log.warn("creaIstanza - impossibile trovare il comune avente codice {}.", new Object[] { comuneIn.getCodicecomune() });
		    }
		}
		//allegati
		log.debug("insertPraticaSTCDaSuapInRete# Carico l'allegato presente sull'interfaccia web");
		List<DocumentiType> stcDocs = datiPratica.getDocumenti();
		//		//se richiesto dall'utente scompatto gli allegati compressi
		File tempDir = Utilities.getSystemTempDir();
		tempDir = new File(tempDir, proto_temp_unzip_dir + cmd.getDatiProtocolloLetto().getNumeroProtocollo() + "_" +
					    cmd.getDatiProtocolloLetto().getAnnoProtocollo() + "_" + System.currentTimeMillis());
		if (cmd.isInviaAllegati()) {
		    ArrayOfAllegatoResponseType all = cmd.getDatiProtocolloLetto().getAllegati();
		    if (all != null) {
			List<AllegatoResponseType> alls = all.getAllegatoResponseType();
			if (!alls.isEmpty()) {
			    String codiceComune = comuneIn != null ? comuneIn.getCodicecomune() : null;
			    List<AllegatoDataHandlerHelper> adhh = getAllegatiProtocolloPerIstanzaoMovimento(alls, cmd.isScompattaAllegati(), tempDir,
				    ORMHelper.getSoftware(), codiceComune);
			    for (AllegatoDataHandlerHelper allegato : adhh) {
				DocumentiType stcDoc = new DocumentiType();
				stcDoc.setId(allegato.getIdentificativo());
				stcDoc.setDocumento(allegato.getNomeFile());
				AllegatiType allType = new AllegatiType();
				allType.setAllegato(allegato.getNomeFile());
				AllegatoBinarioType abt = new AllegatoBinarioType();
				abt.setBinaryData(allegato.getDataHandler());
				abt.setFileName(allegato.getNomeFile());
				String contentType = contenttypesService.findMimeTypeByFileName(allegato.getNomeFile());
				abt.setMimeType(contentType);
				allType.setFile(abt);
				stcDoc.setAllegati(allType);
				stcDoc.setTipoDocumento(TipoDocumentoType.ALTRO.value());
				stcDocs.add(stcDoc);
			    }
			}
		    }
		}
		stcRequest.setDettaglioPratica(datiPratica);
		if (log.isDebugEnabled()) {
		    log.debug("creaIstanza - invocazione in corso del servizio stc.inserimentoPratica.");
		}
		try {
		    log.debug("insertPraticaSTCDaSuapInRete# Call stc.inserimentoPratica");
		    return this.stcWsClient.inserimentoPratica(stcRequest);
		} catch (Exception e) {
		    log.error("insertPraticaSTCDaSuapInRete# errore alla chiamata di STC: ", e);
		    throw new FunzioneBusinessRemotaException("Errore nella creazione della pratica causato da " + e.getMessage(), e);
		}
	    } else {
		esciConerroreValidazioneBusiness(errors);
	    }
	}
	log.debug("insertPraticaSTCDaSuapInRete# End...");
	return null;
    }

    @Override
    public InserimentoPraticaResponse insertPraticaSTCDaAzioni(AzioniProtocollazioneCommand cmd)
	    throws FunzioneBusinessRemotaException, BusinessValidationException {

	List<String> errors = new ArrayList<String>();
	boolean notValid = false;
	InserimentoPraticaRequest stcRequest = new InserimentoPraticaRequest();
	Verticalizzazioniparametri vertParam = this.verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		WebConstants.VERTICALIZZAZIONE_STC_NLA_IDNODO);
	if (null == vertParam) {
	    throw new FunzioneBusinessRemotaException(
		    "Impossibile inviare a STC la richiesta di creazione della pratica: manca la verticalizzazione STC.NLA_IDNODO");
	} else {
	    SportelloType mitt = new SportelloType();
	    mitt.setIdNodo(vertParam.getValore());
	    mitt.setIdEnte(ORMHelper.getIdcomuneAlias());
	    mitt.setIdSportello(ORMHelper.getSoftware() + WebConstants.AZIONI_PROTOCOLLO_CLIENT_IDSPORTELLO_SUFFIX);
	    stcRequest.setSportelloMittente(mitt);
	    SportelloType dest = new SportelloType();
	    dest.setIdNodo(vertParam.getValore());
	    dest.setIdEnte(ORMHelper.getIdcomuneAlias());
	    dest.setIdSportello(ORMHelper.getSoftware());
	    stcRequest.setSportelloDestinatario(dest);
	    DettaglioPraticaType datiPratica = new DettaglioPraticaType();
	    String idPratica = "PROT_" + cmd.getDatiProtocolloLetto().getNumeroProtocollo() + "_DELLLLLLLL_" +
			       cmd.getDatiProtocolloLetto().getAnnoProtocollo();
	    if (StringUtils.isNotBlank(cmd.getDatiProtocolloLetto().getIdProtocollo())) {
		idPratica += "_ID_" + cmd.getDatiProtocolloLetto().getIdProtocollo();
	    }
	    idPratica = Utilities.getHashText(idPratica, Utilities.ALGORITHM_SHA1, true);
	    datiPratica.setIdPratica(idPratica);
	    datiPratica.setNumeroPratica(idPratica);
	    //validazioni
	    //richiedente
	    it.gruppoinit.pal.gp.core.domain.Anagrafe richiedente = findRichiedente(cmd);
	    if (null == richiedente) {
		errors.add("Richiedente non specificato.");
		notValid = true;
	    }
	    //intervento
	    Alberoproc intervento = cmd.getEntity().getAlberoproc();
	    if (null != intervento && null != intervento.getId()) {
		intervento = this.alberoprocService.findById(new PkId(intervento.getId().getCodice()));
	    }
	    if (null == intervento) {
		errors.add("Intervento non specificato.");
		notValid = true;
	    }
	    //indirizzo pec
	    String pecAddress = cmd.getEntity().getDomicilioElettronico();
	    if (StringUtils.isNotBlank(pecAddress)) {
		boolean pecValid = Utilities.validaIndirizzoMail(pecAddress);
		if (!pecValid) {
		    errors.add("Il domicilio elettronico sembra non essere un indirizzo mail valido");
		    notValid = true;
		}
	    }
	    datiPratica.setDomicilioElettronico(pecAddress);
	    Comuni comuneIn = cmd.getEntity().getComune();
	    if (null != comuneIn) {
		Comuni comuneOut = this.comuniService.findByCodiceComune(comuneIn);
		if (null != comuneOut) {
		    ComuneType codComune = new ComuneType();
		    codComune.setCodiceIstat(comuneOut.getCodiceistat());
		    codComune.setComune(comuneOut.getComune());
		    datiPratica.setCodiceComune(codComune);
		    cmd.getEntity().setComune(comuneOut);
		} else {
		    log.warn("creaIstanza - impossibile trovare il comune avente codice {}.", new Object[] { comuneIn.getCodicecomune() });
		}
	    }
	    if (null != cmd.getDatiProtocolloLetto().getDataProtocollo()) {
		GregorianCalendar gc = new GregorianCalendar();
		Date d = Utilities.parseDateString(cmd.getDatiProtocolloLetto().getDataProtocollo(), false);
		gc.setTime(d);
		datiPratica.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(gc));
		datiPratica.setDataPratica(datiPratica.getDataProtocolloGenerale());
	    }
	    datiPratica.setOggetto(cmd.getEntity().getLavori());
	    datiPratica.setAnnotazioni(cmd.getEntity().getLavoriestesa());
	    datiPratica.setCodicePraticaTelematica(cmd.getEntity().getCodicepraticatel());
	    if (!notValid) {
		//imposto il richiedente	    
		RichiedenteType richiedenteStc = new RichiedenteType();
		AnagrafeType stcAna = this.nlaHelperService.populateAnagrafeType(richiedente);
		if (WebConstants.PERSONA_FISICA.equalsIgnoreCase(richiedente.getTipoanagrafe())) { // se l'utente ha scelto una persona fisica come richiedente
		    richiedenteStc.setAnagrafica(stcAna.getPersonaFisica());
		    //il titolare legale
		    it.gruppoinit.pal.gp.core.domain.Anagrafe azienda = cmd.getEntity().getTitolarelegale();
		    if (azienda != null && azienda.getId() != null && azienda.getId().getCodice() != null) {
			azienda = anagrafeService.findById(new PkId(azienda.getId().getCodice()));
		    }
		    if (null != azienda && StringUtils.isNotBlank(azienda.getTipoanagrafe())) {
			stcAna = this.nlaHelperService.populateAnagrafeType(azienda);
			datiPratica.setAziendaRichiedente(stcAna.getPersonaGiuridica());
		    }
		} else {// se l'utente ha scelto una persona giuridica come richiedente allora lo imposto come azienda e STC poi lo segnerà come richiedente
			// in questo caso però perdo quello che l'utente ha messo nel campo azienda 
		    datiPratica.setAziendaRichiedente(stcAna.getPersonaGiuridica());
		}
		//ruolo richiedente 
		Tipisoggetto inQualitaDi = cmd.getEntity().getTipisoggetto();
		if (null != inQualitaDi && null != inQualitaDi.getId() && null != inQualitaDi.getId().getCodice()) {
		    RuoloType ruoloStc = new RuoloType();
		    ruoloStc.setRuolo(inQualitaDi.getTiposoggetto());
		    ruoloStc.setIdRuolo(inQualitaDi.getId().getCodice().toString());
		    richiedenteStc.setRuolo(ruoloStc);
		}
		datiPratica.setRichiedente(richiedenteStc);
		//intervento
		InterventoType interventoStc = new InterventoType();
		interventoStc.setCodice(intervento.getId().getCodice().toString());
		interventoStc.setDescrizione(intervento.getVwAlberoproc().getScDescrizione());
		datiPratica.setIntervento(interventoStc);
		//protocollo
		if (null != cmd.getDatiProtocolloLetto().getDataProtocollo()) {
		    GregorianCalendar gc = new GregorianCalendar();
		    Date d = Utilities.parseDateString(cmd.getDatiProtocolloLetto().getDataProtocollo(), false);
		    gc.setTime(d);
		    datiPratica.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(gc));
		}
		if (StringUtils.isNotBlank(cmd.getDatiProtocolloLetto().getNumeroProtocollo())) {
		    datiPratica.setNumeroProtocolloGenerale(cmd.getDatiProtocolloLetto().getNumeroProtocollo());
		}
		if (StringUtils.isNotEmpty(cmd.getDatiProtocolloLetto().getIdProtocollo())) {
		    List<ParametroType> altriDati = datiPratica.getAltriDati();
		    ParametroType idProtParam = new ParametroType();
		    idProtParam.setNome(NlaHelperServiceImpl.ALTRI_DATI_ISTANZE_MOVIMENTI_FKIDPROTOCOLLO);
		    ValoreParametroType idProtParamVal = new ValoreParametroType();
		    idProtParamVal.setCodice(cmd.getDatiProtocolloLetto().getIdProtocollo());
		    idProtParamVal.setDescrizione(cmd.getDatiProtocolloLetto().getIdProtocollo());
		    idProtParam.getValore().add(idProtParamVal);
		    altriDati.add(idProtParam);
		}
		//allegati
		List<DocumentiType> stcDocs = datiPratica.getDocumenti();
		//se richiesto dall'utente scompatto gli allegati compressi
		File tempDir = Utilities.getSystemTempDir();
		tempDir = new File(tempDir, proto_temp_unzip_dir + cmd.getDatiProtocolloLetto().getNumeroProtocollo() + "_" +
					    cmd.getDatiProtocolloLetto().getAnnoProtocollo() + "_" + System.currentTimeMillis());
		if (cmd.isInviaAllegati()) {
		    ArrayOfAllegatoResponseType all = cmd.getDatiProtocolloLetto().getAllegati();
		    if (all != null) {
			List<AllegatoResponseType> alls = all.getAllegatoResponseType();
			if (!alls.isEmpty()) {
			    List<AllegatoDataHandlerHelper> adhh = getAllegatiProtocolloPerIstanzaoMovimento(alls, cmd.isScompattaAllegati(), tempDir,
				    ORMHelper.getSoftware(), comuneIn.getCodicecomune());
			    for (AllegatoDataHandlerHelper allegato : adhh) {
				DocumentiType stcDoc = new DocumentiType();
				stcDoc.setId(allegato.getIdentificativo());
				stcDoc.setDocumento(allegato.getNomeFile());
				AllegatiType allType = new AllegatiType();
				allType.setAllegato(allegato.getNomeFile());
				AllegatoBinarioType abt = new AllegatoBinarioType();
				abt.setBinaryData(allegato.getDataHandler());
				abt.setFileName(allegato.getNomeFile());
				String contentType = contenttypesService.findMimeTypeByFileName(allegato.getNomeFile());
				abt.setMimeType(contentType);
				allType.setFile(abt);
				stcDoc.setAllegati(allType);
				stcDoc.setTipoDocumento(TipoDocumentoType.ALTRO.value());
				stcDocs.add(stcDoc);
			    }
			}
		    }
		}
		stcRequest.setDettaglioPratica(datiPratica);
		if (log.isDebugEnabled()) {
		    log.debug("creaIstanza - invocazione in corso del servizio stc.inserimentoPratica.");
		}
		try {
		    return this.stcWsClient.inserimentoPratica(stcRequest);
		} catch (Exception e) {
		    log.error("creaIstanza - errore alla chiamata di STC: ", e);
		    throw new FunzioneBusinessRemotaException("Errore nella creazione della pratica causato da " + e.getMessage(), e);
		} finally {
		    try {
			tempDir.deleteOnExit();
		    } catch (Exception e) {
			log.warn("Non è stato possibile cancellare la tempDir");
		    }
		}
	    } else {
		esciConerroreValidazioneBusiness(errors);
	    }
	}
	return null;
    }

    private void esciConerroreValidazioneBusiness(List<String> errori) {

	StringBuilder errore = new StringBuilder("");
	for (String err : errori) {
	    errore.append("\n").append(err);
	}
	throw new BusinessValidationException(errore.toString());
    }

    private Anagrafe findRichiedente(AzioniProtocollazioneCommand cmd) {

	it.gruppoinit.pal.gp.core.domain.Anagrafe retAna = null;
	if (null != cmd) {
	    Integer codiceRichiedente = null;
	    it.gruppoinit.pal.gp.core.domain.Anagrafe tempAna = cmd.getEntity().getRichiedente();
	    if (null != tempAna && tempAna.getId() != null) {
		codiceRichiedente = tempAna.getId().getCodice();
	    }
	    if (null == codiceRichiedente) {
		Verticalizzazioniparametri vertParam = this.verticalizzazioniService.getVerticalizzazioniparametri(
			WebConstants.VERTICALIZZAZIONE_PEC_CLIENT, WebConstants.VERTICALIZZAZIONE_PEC_CLIENT_RICHIEDENTE_DEFAULT);
		if (null != vertParam) {
		    String vertVal = vertParam.getValore();
		    if (NumberUtils.isNumber(vertVal)) {
			codiceRichiedente = Integer.parseInt(vertVal);
		    }
		} else {
		    log.error(
			    "findRichiedente - impossibile impostare il richiedente di default perchè la verticalizzazione {}.{} non è attiva o non è presente.",
			    new Object[] { WebConstants.VERTICALIZZAZIONE_PEC_CLIENT,
				    WebConstants.VERTICALIZZAZIONE_PEC_CLIENT_RICHIEDENTE_DEFAULT });
		}
	    }
	    if (null != codiceRichiedente) {
		retAna = this.anagrafeService.findById(new PkId(codiceRichiedente));
		cmd.getEntity().setRichiedente(retAna);
	    }
	}
	return retAna;
    }

    public List<AllegatoDataHandlerHelper> getAllegatiProtocolloPerIstanzaoMovimento(List<AllegatoResponseType> allegatiProtocollo, boolean scompatta,
	    File tempDir, String software, String codiceComune) throws FunzioneBusinessRemotaException {

	List<AllegatoDataHandlerHelper> result = new ArrayList<AllegatoDataHandlerHelper>();
	if (allegatiProtocollo != null && !allegatiProtocollo.isEmpty()) {
	    //se è richiesto di scopattare degli archivi compressi allora creo una directory temporanea specifica per questa PEC 
	    // che alla fiine della chiamata sarà cancellata
	    if (scompatta && !tempDir.isDirectory()) {
		tempDir.mkdirs();
	    }
	    File unpackDir = null;
	    List<File> uncompressed = null;
	    for (AllegatoResponseType allegatoProtocollo : allegatiProtocollo) {
		AllegatoResponseType all = leggiAllegatoUORuolo(ORMHelper.getToken(), allegatoProtocollo.getIDBase(), allegatoProtocollo.getUo(),
			allegatoProtocollo.getRuolo(), software, codiceComune);
		if (all == null) {
		    throw new FunzioneBusinessRemotaException("Non è stato possibile recuperare l'allegato " + allegatoProtocollo.getSerial());
		}
		byte[] fileContent = Utilities.dataHandlerToBytes(all.getImage());
		if (scompatta) {
		    uncompressed = null;
		    try {
			if (Utilities.isZipFile(new ByteArrayInputStream(fileContent))) {
			    try {
				unpackDir = new File(tempDir, Utilities.getHashText(all.getIDBase(), Utilities.ALGORITHM_MD5, false));
				if (!unpackDir.isDirectory()) {
				    unpackDir.mkdir();
				    if (log.isDebugEnabled()) {
					log.debug(
						"getAllegatiPECPerIstanzaoMovimento - creata directory temporanea {} per la decompressione dell' archivio ZIP: {}",
						new Object[] { unpackDir.getAbsolutePath(), all.getSerial() });
				    }
				}
				uncompressed = Utilities.unzipToDirAndReturnEntries(new ByteArrayInputStream(fileContent), unpackDir);
			    } catch (IOException e) {
				log.error(
					"getAllegatiPECPerIstanzaoMovimento - si è verificato un errore durante lo scompattamento dell'archivio ZIP {}, il file sarà trattato come se non fosse un file zip. Errore: {}",
					new Object[] { all.getSerial(), e.getMessage() });
			    }
			} else if (Utilities.isRarFile(new ByteArrayInputStream(fileContent))) {
			    OutputStream fos = null;
			    try {
				unpackDir = new File(tempDir, Utilities.getHashText(all.getIDBase(), Utilities.ALGORITHM_MD5, false));
				if (!unpackDir.isDirectory()) {
				    unpackDir.mkdir();
				    if (log.isDebugEnabled()) {
					log.debug(
						"getAllegatiPECPerIstanzaoMovimento - creata directory temporanea {} per la decompressione dell' archivio RAR: {}",
						new Object[] { unpackDir.getAbsolutePath(), all.getSerial() });
				    }
				}
				//copia locale del file RAR: per leggere direttamente il bytes array occorre realizzare implementazioni di Volume e VolumeMAnager basate du byte array
				File rarFile = new File(unpackDir, Utilities.cleanFilename(all.getSerial()));
				if (!rarFile.isFile()) {
				    boolean fileCreato = rarFile.createNewFile();
				    if (!fileCreato) {
					log.warn("Non è stato possibile creare la copia locale del file");
				    }
				}
				fos = new FileOutputStream(rarFile);
				IOUtils.copy(new ByteArrayInputStream(fileContent), fos);
				fos.close();
				if (log.isDebugEnabled()) {
				    log.debug("getAllegatiPECPerIstanzaoMovimento - file RAR copiato nel file temporaneo {}",
					    new Object[] { rarFile.getAbsolutePath() });
				}
				uncompressed = Utilities.unrarToDirAndReturnEntries(rarFile, unpackDir);
				if (!rarFile.delete()) {
				    log.warn("Non è stato possibile cancellare la copia locale del file");
				}
			    } catch (Exception e) {
				log.error(
					"getAllegatiPECPerIstanzaoMovimento - si è verificato un errore durante lo scompattamento dell'archivio RAR {}, il file sarà trattato come se non fosse un file rar. Errore: {}",
					new Object[] { all.getSerial(), e.getMessage() });
				if (fos != null) {
				    fos.close();
				}
			    }
			}
		    } catch (IOException e) {
			log.error(
				"getAllegatiPECPerIstanzaoMovimento - Impossibile verificare se il file {} è un archivio ZIP o RAR. Il file non sarà considerato come archivio compresso. Errore: {}",
				new Object[] { all.getSerial(), e.getMessage() });
		    }
		    if (uncompressed == null || uncompressed.isEmpty()) {
			//se l'allegato non è un archivio compresso restituisco il riferimento al record di oggetti che lo contiene
			AllegatoDataHandlerHelper dhh = new AllegatoDataHandlerHelper();
			dhh.setNomeFile(all.getSerial());
			dhh.setIdentificativo(all.getSerial());
			dhh.setContentType(all.getContentType());
			DataHandler dh = Utilities.bytesToDataHandler(fileContent);
			dhh.setDataHandler(dh);
			result.add(dhh);
		    } else {
			/*
			 * se invece si tratta di un archivi che è stato scompattato allora inserisco in Oggetti 
			 * tutti i files estratti e restituisco i riferimenti a questi
			 */
			for (File file : uncompressed) {
			    AllegatoDataHandlerHelper dhh = new AllegatoDataHandlerHelper();
			    dhh.setIdentificativo(file.getName());
			    dhh.setNomeFile(file.getName());
			    DataHandler dh = new DataHandler(new FileDataSource(file));
			    dhh.setDataHandler(dh);
			    result.add(dhh);
			}
		    }
		} else {
		    AllegatoDataHandlerHelper dhh = new AllegatoDataHandlerHelper();
		    dhh.setIdentificativo(all.getSerial());
		    dhh.setNomeFile(all.getSerial());
		    dhh.setContentType(all.getContentType());
		    DataHandler dh = Utilities.bytesToDataHandler(fileContent);
		    dhh.setDataHandler(dh);
		    result.add(dhh);
		}
	    }
	}
	return result;
    }

    @Override
    public NotificaAttivitaResponse insertMovimentoSTCSuapInRete(AzioniProtocollazioneCommand cmd)
	    throws FunzioneBusinessRemotaException, BusinessValidationException {

	log.debug("insertPraticaSTCDaSuapInRete# Recupero i parametri di verticalizzazione ");
	Verticalizzazioniparametri vertParamMitt = this.verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		WebConstants.VERTICALIZZAZIONE_STC_NLA_IDNODO);
	Verticalizzazioniparametri vertParamDest = this.verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE, WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_NODO_SUAP_INRETE);
	if (null == vertParamMitt) {
	    throw new FunzioneBusinessRemotaException(
		    "Impossibile inviare a STC la richiesta di creazione del movimento: manca la verticalizzazione STC.NLA_IDNODO");
	} else if (null == vertParamDest) {
	    throw new FunzioneBusinessRemotaException(
		    "Impossibile inviare a STC la richiesta di creazione del movimento: manca la verticalizzazione FVG_SUAP_IN_RETE.FVG_SUAP_IN_RETE");
	} else {
	    SportelloType mitt = new SportelloType();
	    mitt.setIdNodo(vertParamMitt.getValore());
	    mitt.setIdEnte(ORMHelper.getIdcomuneAlias());
	    mitt.setIdSportello(ORMHelper.getSoftware() + WebConstants.AZIONI_PROTOCOLLO_CLIENT_IDSPORTELLO_SUFFIX);
	    SportelloType dest = new SportelloType();
	    dest.setIdNodo(vertParamDest.getValore());
	    dest.setIdEnte(ORMHelper.getIdcomuneAlias());
	    dest.setIdSportello(ORMHelper.getSoftware());
	    NotificaAttivitaResponse stcResponse = this.insertMovimentoSTCDaAzioni(cmd, mitt, dest);
	    return stcResponse;
	}
    }

    @Override
    public NotificaAttivitaResponse insertMovimentoSistemaExt(AzioniProtocollazioneCommand cmd)
	    throws FunzioneBusinessRemotaException, BusinessValidationException {

	NotificaAttivitaResponse stcResponse = new NotificaAttivitaResponse();
	//	NotificaAttivitaRequest stcRequest = new NotificaAttivitaRequest();
	//	List<String> errors = new ArrayList<String>();
	//	boolean notValid = false;
	log.debug("insertPraticaSTCDaSuapInRete# Recupero i parametri di verticalizzazione ");
	Verticalizzazioniparametri vertParamMitt = this.verticalizzazioniService
		.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_PRATICA_DA_SISTEMA_ESTERNO, WebConstants.VERTICALIZZAZIONE_NLA_IDNODO);
	Verticalizzazioniparametri vertParamDest = this.verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_PRATICA_DA_SISTEMA_ESTERNO, WebConstants.VERTICALIZZAZIONE_IDNODO_INFOCAMERE);
	if (null == vertParamMitt) {
	    throw new FunzioneBusinessRemotaException(
		    "Impossibile inviare a STC la richiesta di creazione del movimento: manca la verticalizzazione STC.NLA_IDNODO");
	} else if (null == vertParamDest) {
	    throw new FunzioneBusinessRemotaException(
		    "Impossibile inviare a STC la richiesta di creazione del movimento: manca la verticalizzazione FVG_SUAP_IN_RETE.FVG_SUAP_IN_RETE");
	} else {
	    SportelloType mitt = new SportelloType();
	    mitt.setIdNodo(vertParamMitt.getValore());
	    mitt.setIdEnte(ORMHelper.getIdcomuneAlias());
	    mitt.setIdSportello(ORMHelper.getSoftware());
	    //stcRequest.setSportelloMittente(mitt);
	    SportelloType dest = new SportelloType();
	    dest.setIdNodo(vertParamDest.getValore());
	    dest.setIdEnte(ORMHelper.getIdcomuneAlias());
	    dest.setIdSportello(ORMHelper.getSoftware());
	    //stcRequest.setSportelloDestinatario(dest);
	    Map<String, String> altriparametri = new HashMap<String, String>();
	    altriparametri.put("TIPO_OPERAZIONE", "MOVIMENTO_SISTEMA_ESTERNO");
	    stcResponse = this.insertMovimentoSTC(cmd, mitt, dest, altriparametri);
	    return stcResponse;
	}
    }

    @Override
    public NotificaAttivitaResponse insertMovimentoSTCDaAzioni(AzioniProtocollazioneCommand cmd, SportelloType sportelloTypeMitt,
	    SportelloType sportelloTypeDest) throws FunzioneBusinessRemotaException, BusinessValidationException {

	return insertMovimentoSTC(cmd, sportelloTypeMitt, sportelloTypeDest, null);
    }

    private NotificaAttivitaResponse insertMovimentoSTC(AzioniProtocollazioneCommand cmd, SportelloType sportelloTypeMitt,
	    SportelloType sportelloTypeDest, Map<String, String> altriparametri) throws FunzioneBusinessRemotaException, BusinessValidationException {

	if ((sportelloTypeMitt != null && sportelloTypeDest == null) || (sportelloTypeMitt == null && sportelloTypeDest != null)) {
	    throw new BusinessValidationException(
		    "Impossibile inviare a STC la richiesta di creazione del movimento: sportelloTypeMitt e sportelloTypeDest o sono entrabi NULL o Popolati");
	}
	NotificaAttivitaResponse stcResponse = new NotificaAttivitaResponse();
	NotificaAttivitaRequest stcRequest = new NotificaAttivitaRequest();
	List<String> errors = new ArrayList<String>();
	boolean notValid = false;
	Verticalizzazioniparametri vertParam = null;
	vertParam = this.verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		WebConstants.VERTICALIZZAZIONE_STC_NLA_IDNODO);
	if (null == vertParam && (sportelloTypeMitt == null || sportelloTypeDest == null)) {
	    throw new BusinessValidationException(
		    "Impossibile inviare a STC la richiesta di creazione del movimento: manca la verticalizzazione STC.NLA_IDNODO");
	} else {
	    //sportelli mittente e destinatario
	    if (sportelloTypeMitt == null) {
		SportelloType mitt = new SportelloType();
		mitt.setIdNodo(vertParam.getValore());
		mitt.setIdEnte(ORMHelper.getIdcomuneAlias());
		mitt.setIdSportello(ORMHelper.getSoftware() + WebConstants.AZIONI_PROTOCOLLO_CLIENT_IDSPORTELLO_SUFFIX);
		stcRequest.setSportelloMittente(mitt);
	    } else {
		stcRequest.setSportelloMittente(sportelloTypeMitt);
	    }
	    if (sportelloTypeDest == null) {
		SportelloType dest = new SportelloType();
		dest.setIdNodo(vertParam.getValore());
		dest.setIdEnte(ORMHelper.getIdcomuneAlias());
		dest.setIdSportello(ORMHelper.getSoftware());
		stcRequest.setSportelloDestinatario(dest);
	    } else {
		stcRequest.setSportelloDestinatario(sportelloTypeDest);
	    }
	    //dettaglio attivita...
	    DettaglioAttivitaType attivita = new DettaglioAttivitaType();
	    if (null != cmd.getDatiProtocolloLetto().getDataProtocollo()) {
		GregorianCalendar gc = new GregorianCalendar();
		Date d = Utilities.parseDateString(cmd.getDatiProtocolloLetto().getDataProtocollo(), false);
		gc.setTime(d);
		XMLGregorianCalendar dataprot = Utilities.getXMLGregorianCalendar(gc);
		attivita.setDataProtocolloGenerale(dataprot);
		attivita.setDataAttivita(dataprot);
		attivita.setOraDataAttivita("00:00");
	    }
	    if (StringUtils.isNotBlank(cmd.getDatiProtocolloLetto().getNumeroProtocollo())) {
		attivita.setNumeroProtocolloGenerale(cmd.getDatiProtocolloLetto().getNumeroProtocollo());
	    }
	    if (StringUtils.isNotEmpty(cmd.getDatiProtocolloLetto().getIdProtocollo())) {
		List<ParametroType> altriDati = attivita.getAltriDati();
		ParametroType idProtParam = new ParametroType();
		idProtParam.setNome(NlaHelperService.ALTRI_DATI_ISTANZE_MOVIMENTI_FKIDPROTOCOLLO);
		ValoreParametroType idProtParamVal = new ValoreParametroType();
		idProtParamVal.setCodice(cmd.getDatiProtocolloLetto().getIdProtocollo());
		idProtParamVal.setDescrizione(cmd.getDatiProtocolloLetto().getIdProtocollo());
		idProtParam.getValore().add(idProtParamVal);
		altriDati.add(idProtParam);
	    }
	    if (altriparametri != null) {
		List<ParametroType> altriDati = attivita.getAltriDati();
		for (Map.Entry<String, String> entry : altriparametri.entrySet()) {
		    ParametroType idProtParam = new ParametroType();
		    idProtParam.setNome(entry.getKey());
		    ValoreParametroType idProtParamVal = new ValoreParametroType();
		    idProtParamVal.setCodice(entry.getValue());
		    idProtParamVal.setDescrizione("Altri parametri da insertMovimentoSTC");
		    idProtParam.getValore().add(idProtParamVal);
		    altriDati.add(idProtParam);
		}
	    }
	    //	    //data movimento
	    //	    attivita.setDataAttivita(Utilities.getXMLGregorianCalendar(calDataMov));
	    //	    //GIANPAOLO-ORA
	    //	    String orario = Utilities.getOrario(calDataMov.getTime());
	    //	    attivita.setOraDataAttivita(orario);
	    //protocollo		
	    //tipo attivita
	    TipoAttivitaType tipoAtt = new TipoAttivitaType();
	    Movimenti mov = cmd.getMovimento();
	    //istanza
	    Istanze istanza = null;
	    if (null != mov) {
		istanza = mov.getIstanza();
		if (null != istanza && null != istanza.getId() && null != istanza.getId().getCodice()) {
		    attivita.setIdPratica(istanza.getId().getCodice().toString());
		} else {
		    notValid = true;
		    errors.add("Istanza non specificata.");
		}
		//tipo movimento e movimento
		Tipimovimento tipoMov = mov.getTipomovimento();
		if (tipoMov != null && tipoMov.getId() != null && StringUtils.isNotEmpty(tipoMov.getId().getTipomovimento())) {
		    tipoAtt.setCodice(tipoMov.getId().getTipomovimento());
		    if (StringUtils.isBlank(mov.getMovimento())) {
			//se il nome del movimento è lasciato vuoto viene presa la descrizione estesa del tipo movimento
			mov.setMovimento(tipoMov.getDescrizioneEstesa());
		    }
		    tipoAtt.setDescrizione(mov.getMovimento());
		    attivita.setTipoAttivita(tipoAtt);
		} else {
		    notValid = true;
		    errors.add("Tipo movimento non specificato.");
		}
	    } else {
		notValid = true;
		errors.add("Tipo movimento non specificato.");
	    }
	    //se non ci sono errori di validazione procedo nel recupero dei dati inseriti dall'utente e alla chiamata di stc.notificaAttivita
	    if (!notValid) {
		//esito
		boolean esito = BooleanUtils.toBooleanDefaultIfNull(mov.getEsito(), false);
		attivita.setEsito(esito);
		attivita.setNote(mov.getNote());
		RiferimentiPraticaType rifPratica = new RiferimentiPraticaType();
		//rifPratica.
		rifPratica.setIdPratica(istanza.getId().getCodice().toString());
		stcRequest.setRifPraticaDestinatario(rifPratica);
		attivita.setTipoAttivita(tipoAtt);
		stcRequest.setDatiAttivita(attivita);
		//gestione allegati
		List<DocumentiType> stcDocs = attivita.getDocumenti();
		File tempDir = Utilities.getSystemTempDir();
		tempDir = new File(tempDir, proto_temp_unzip_dir + cmd.getDatiProtocolloLetto().getNumeroProtocollo() + "_" +
					    cmd.getDatiProtocolloLetto().getAnnoProtocollo() + "_" + System.currentTimeMillis());
		if (cmd.isInviaAllegati()) {
		    ArrayOfAllegatoResponseType all = cmd.getDatiProtocolloLetto().getAllegati();
		    if (all != null) {
			List<AllegatoResponseType> alls = all.getAllegatoResponseType();
			if (!alls.isEmpty()) {
			    try {
				List<AllegatoDataHandlerHelper> adhh = getAllegatiProtocolloPerIstanzaoMovimento(alls, cmd.isScompattaAllegati(),
					tempDir, cmd.getProtSoftware().getCodice(), cmd.getComune().getCodicecomune());
				for (AllegatoDataHandlerHelper allegato : adhh) {
				    DocumentiType stcDoc = new DocumentiType();
				    stcDoc.setId(allegato.getIdentificativo());
				    stcDoc.setDocumento(allegato.getNomeFile());
				    AllegatiType allType = new AllegatiType();
				    allType.setAllegato(allegato.getNomeFile());
				    AllegatoBinarioType abt = new AllegatoBinarioType();
				    abt.setBinaryData(allegato.getDataHandler());
				    abt.setFileName(allegato.getNomeFile());
				    String contentType = contenttypesService.findMimeTypeByFileName(allegato.getNomeFile());
				    abt.setMimeType(contentType);
				    allType.setFile(abt);
				    stcDoc.setAllegati(allType);
				    stcDoc.setTipoDocumento(TipoDocumentoType.ALTRO.value());
				    stcDocs.add(stcDoc);
				}
			    } catch (Exception e) {
				errors.add("Si è verificato un errore nel download degli allegati: " + e.getMessage());
			    }
			}
		    }
		}
		if (errors.isEmpty()) {
		    stcRequest.setDatiAttivita(attivita);
		    if (log.isDebugEnabled()) {
			log.debug("creaMovimento - invocazione in corso del servizio stc.notificaAttivita");
		    }
		    try {
			stcResponse = this.stcWsClient.notificaAttivita(stcRequest);
			//se inserimento a buon fine riporto nell'output il riferimento all'istanza creata
			List<ErroreType> stcErrors = stcResponse.getDettaglioErrore();
			if (stcErrors == null || stcErrors.isEmpty()) {
			    return stcResponse;
			} else {
			    StringBuilder sbErr = new StringBuilder("Si sono verificati errori durante la creazione del movimento:<br/><ul>");
			    for (ErroreType stcError : stcErrors) {
				sbErr.append("<li>").append(stcError.getNumeroErrore()).append(" - ").append(stcError.getDescrizione());
			    }
			    sbErr.append("</ul>");
			    errors.add(sbErr.toString());
			}
		    } catch (Exception e) {
			log.error("creaMovimento - errore alla chiamata di STC: ", e);
			throw new FunzioneBusinessRemotaException("Errore alla chiamata di STC: " + e.getMessage(), e);
		    } finally {
			try {
			    if (!tempDir.delete()) {
				log.warn("Non è stato possibile eliminare la cartella temporanea");
			    }
			} catch (Exception e) {
			    log.error("Errore durante la cancellazione della cartella temporanea: " + e.getMessage(), e);
			}
		    }
		}
	    }
	}
	if (!errors.isEmpty()) {
	    esciConerroreValidazioneBusiness(errors);
	}
	return null;
    }

    @Override
    public List<DatiFascType> cercaFascicoli(String software, String codiceComune, DatiFascType datiFascicolo) throws RicercaFascicoliException {

	ListaFascicoliResponseType response = null;
	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    response = port.searchFascicoli(ORMHelper.getToken(), software, codiceComune, datiFascicolo);
	    gestisciErroreType(response.getErrore(), true, "cercaFascicoli");
	    log.debug("cercaFascicoli: response [{}]", response);
	    ArrayOfDatiFascType a = response.getFascicolo();
	    if (a != null && a.getDatiFascType() != null) {
		return a.getDatiFascType();
	    }
	    if (response.getErrore() != null) {
		log.error("cercaFascicoli: {}, {}", response.getErrore().getDescrizione(), response.getErrore().getStackTrace());
		throw new RicercaFascicoliException("Errore nella chiamata al webservice cercaFascicoli: " + response.getErrore().getDescrizione());
	    }
	    return new ArrayList<DatiFascType>();
	} catch (Exception e) {
	    log.error("cercaFascicoli: {}", e.getMessage());
	    throw new RicercaFascicoliException("Errore nella chiamata al webservice cercaFascicoli=" + e.getMessage(), e);
	}
    }

    @Override
    public String creaUnitadocumentale(String software, String codiceComune, ProtocollazioneCommand command) throws CreaUnitaDocumentaleException {

	CreaUnitaDocumentaleResponseType response = new CreaUnitaDocumentaleResponseType();
	String result = "";
	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    CreaUnitaDocumentaleRequestType request = new CreaUnitaDocumentaleRequestType();
	    request.setTipoDocumento(command.getTipoDocumento());
	    ArrayOfAllegatoType alls = new ArrayOfAllegatoType();
	    popolaAllegatiDaCommand(command, alls);
	    request.setAllegati(alls);
	    if (command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
		response = port.creaUnitaDocumentaleIstanza(ORMHelper.getToken(), String.valueOf(command.getEntity().getId().getCodice()), request);
	    } else if (command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
		response = port.creaUnitaDocumentaleMovimento(ORMHelper.getToken(), String.valueOf(command.getMovimento().getId().getCodice()),
			request);
	    }
	    this.istanzeService.clear();
	    gestisciErroreType(response.getErrore(), true, "creaUnitadocumentale");
	    log.debug("creaUnitadocumentale: response [{}]", response);
	    result = response.getUnitaDocumentale();
	    if (response.getErrore() != null) {
		log.error("creaUnitadocumentale: {}, {}", response.getErrore().getDescrizione(), response.getErrore().getStackTrace());
		throw new CreaUnitaDocumentaleException(
			"Errore nella chiamata al webservice creaUnitadocumentale: " + response.getErrore().getDescrizione());
	    }
	    return result;
	} catch (Exception e) {
	    log.error("creaUnitadocumentale: {}", e.getMessage());
	    throw new CreaUnitaDocumentaleException("Errore nella chiamata al webservice creaUnitadocumentale=" + e.getMessage(), e);
	}
    }

    @Override
    public DatiProtocolloResponseType registrazioneDocer(ProtocollazioneCommand command) throws RegistrazioneIstanzaXmlException {

	if (StringUtils.isBlank(command.getRegistroDocEr())) {
	    throw new RegistrazioneIstanzaXmlException("Il registro è obbligatorio");
	}
	if (!(command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_ISTANZA)
		|| command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI))) {
	    throw new RegistrazioneIstanzaXmlException(
		    "E' possibile invocare la funzionalità solamente da istanze o movimenti (provenienza: " + command.getProvenienza() + ")");
	}
	try {
	    validateCommand(command, false);
	} catch (ConfigurationException e) {
	    throw new RegistrazioneIstanzaXmlException(e.getCause());
	}
	DatiProtocolloResponseType response = null;
	Integer codiceIstanza = null;
	Integer codiceMovimento = null;
	if (command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
	    codiceMovimento = command.getMovimento().getId().getCodice();
	    Movimenti m = movimentiService.findById(new PkId(codiceMovimento));
	    codiceIstanza = m.getIstanza().getId().getCodice();
	} else {
	    codiceIstanza = command.getEntity().getId().getCodice();
	}
	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    Istanze istanza = this.istanzeService.findById(new PkId(codiceIstanza));
	    String codiceComune = istanza.getComune().getCodicecomune();
	    String software = istanza.getSoftware().getCodice();
	    IVerticalizzazioneProtocolloAttivoService protAttivoService = new VerticalizzazioneProtocolloAttivoServiceImpl(
		    this.verticalizzazioniService, codiceComune);
	    DatiRequestType request = generaXml(command, istanza, protAttivoService, codiceComune, software);
	    if (command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
		log.debug("registrazioneDocer# prima di invocare port.registrazioneMovimentoXml(token,{},{})", codiceMovimento,
			command.getRegistroDocEr());
		response = port.registrazioneMovimentoXml(ORMHelper.getToken(), String.valueOf(codiceMovimento), command.getRegistroDocEr(), request);
	    } else { // ISTANZA
		log.debug("registrazioneDocer# prima di invocare port.registrazioneIstanzaXml(token,{},{})", codiceIstanza,
			command.getRegistroDocEr());
		response = port.registrazioneIstanzaXml(ORMHelper.getToken(), String.valueOf(codiceIstanza), command.getRegistroDocEr(), request);
	    }
	    this.istanzeService.clear();
	    gestisciErroreType(response.getErrore(), true, "registrazioneDocer");
	    log.debug("registrazioneDocer: response [{}]", response);
	    if (response.getErrore() != null) {
		log.error("registrazioneDocer: {}, {}", response.getErrore().getDescrizione(), response.getErrore().getStackTrace());
		throw new RegistrazioneIstanzaXmlException(
			"Errore nella chiamata al webservice registrazioneDocer: " + response.getErrore().getDescrizione());
	    }
	    return response;
	} catch (Exception e) {
	    log.error("registrazioneDocer: {}", e.getMessage());
	    throw new RegistrazioneIstanzaXmlException("Errore nella chiamata al webservice registrazioneDocer=" + e.getMessage(), e);
	}
    }

    @Override
    public void invioPECDocer(Integer codiceMovimento) throws InvioPECException {

	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    port.invioPec(ORMHelper.getToken(), String.valueOf(codiceMovimento));
	} catch (Exception e) {
	    log.error("invioPEC: {}", e.getMessage());
	    throw new InvioPECException("Errore nella chiamata al webservice invioPEC=" + e.getMessage(), e);
	}
    }

    @Override
    public void updateInviaDocumenti(ProtocollazioneCommand protocolloCommand) throws AggiungiAllegatiException {

	log.debug("updateInviaDocumenti# Recupero tutti i documenti da inviare ");
	DocumentiHelper documentiHelper = documentiHelperService.findDocumentiInvioTrue(protocolloCommand.getDocumentiHelper());
	log.debug("updateInviaDocumenti# Invoco ws invio documenti al protocollo");
	// Istanzio tutti le variabili per invocare il ws
	String token = ORMHelper.getToken();
	String numProt = "";
	XMLGregorianCalendar dateProt = null;
	String idProt = "";
	String software = "";
	String codComune = "";
	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    if (EntityUtils.getNestedProperty(protocolloCommand.getMovimento(), "id.codice") != null) {
		log.debug("updateInviaDocumenti#Imposto i parametri richiesti dal aggiungi documento per movimento");
		Movimenti movimento = protocolloCommand.getMovimento();
		numProt = StringUtils.defaultIfEmpty(movimento.getNumeroprotocollo(), "");
		if (movimento.getDataprotocollo() != null) {
		    GregorianCalendar calendar = new GregorianCalendar();
		    calendar.setTime(movimento.getDataprotocollo());
		    dateProt = Utilities.getXMLGregorianCalendar(calendar);
		}
		idProt = StringUtils.defaultIfEmpty(movimento.getFkidprotocollo(), "");
		software = movimento.getIstanza().getSoftware().getCodice();
		codComune = movimento.getIstanza().getComune().getCodicecomune();
	    } else {
		log.debug("updateInviaDocumenti#Imposto i parametri richiesti dal aggiungi documento per istanza");
		Istanze istanza = protocolloCommand.getEntity();
		numProt = StringUtils.defaultIfEmpty(istanza.getNumeroprotocollo(), "");
		if (istanza.getDataprotocollo() != null) {
		    GregorianCalendar calendar = new GregorianCalendar();
		    calendar.setTime(istanza.getDataprotocollo());
		    dateProt = Utilities.getXMLGregorianCalendar(calendar);
		}
		idProt = StringUtils.defaultIfEmpty(istanza.getFkidprotocollo(), "");
		software = istanza.getSoftware().getCodice();
		codComune = istanza.getComune().getCodicecomune();
	    }
	    log.debug("updateInviaDocumenti# Recupero i codici dei documenti da inviare");
	    ArrayOfint codiciAllegati = new ArrayOfint();
	    List<Integer> codiciAllegatiDaInviare = documentiHelperService.findCodiceOggettoAllegatiDaInviare(documentiHelper);
	    codiciAllegati.getInt().addAll(codiciAllegatiDaInviare);
	    port.aggiungiAllegati(token, numProt, dateProt, idProt, codiciAllegati, software, codComune);
	    this.istanzeService.clear();
	} catch (Exception e) {
	    log.error("Errore durante l'invocazione del ws aggiungiAllegati della protocollazione. {}[{}]", new Object[] { e, e.getMessage() });
	    throw new AggiungiAllegatiException("Errore durante l'invocazione dwl ws aggiungiAllegati: " + e.getMessage(), e);
	}
    }

    @Override
    public DatiProtocolloResponseType protocollaDomandaOnline(InserimentoPraticaNLARequest request, Istanze istanza)
	    throws ProtocollaIstanzaException {

	DatiProtocolloResponseType datiProtocollo = null;
	if (istanza == null || istanza.getAlberoproc() == null || istanza.getAlberoproc().getId() == null
		|| istanza.getAlberoproc().getId().getCodice() == null) {
	    return datiProtocollo;
	}
	try {
	    String codiceComune = istanza.getComune().getCodicecomune();
	    IVerticalizzazioneProtocolloAttivoService protAttivoService = new VerticalizzazioneProtocolloAttivoServiceImpl(
		    this.verticalizzazioniService, codiceComune);
	    ProtocollazioneCommand protocollazioneCommand = new ProtocollazioneCommand();
	    protocollazioneCommand.setProvenienza(ProtocollazioneCommand.PROVENIENZA_ISTANZA);
	    protocollazioneCommand.setFlusso(ProtocollazioneCommand.FLUSSO_ARRIVO);
	    log.info("protocolla# Call protocollazioneXml. recupero helper");
	    Alberoproc ap = alberoprocService.findById(new PkId(istanza.getAlberoproc().getId().getCodice()));
	    AlberoprocHelper helper = alberoprocService.findAlberoprocHelper(ap);
	    log.info("protocolla# Call protocollazioneXml. helper recuperato recupero tipo documento");
	    String tipoDocumento = getTipoDocumento(helper, codiceComune);
	    log.info("protocolla# Call protocollazioneXml. tipo documento {}", tipoDocumento);
	    protocollazioneCommand.setTipoDocumento(tipoDocumento);
	    String smistamento = findProtocolloSmistamentoDefault(codiceComune, istanza.getSoftware().getCodice());
	    log.info("protocolla# Call protocollazioneXml. smistamento {}", smistamento);
	    protocollazioneCommand.setSmistamento(smistamento);
	    Integer oggetto = getOggetto();
	    log.info("protocolla# Call protocollazioneXml. recupero mail tipo oggetto {}", oggetto);
	    Mailtipo eseguiSostituzioniFrontend = mailtipoService.eseguiSostituzioniFrontend(oggetto, request.getDettaglioPratica());
	    protocollazioneCommand.setOggetto(eseguiSostituzioniFrontend.getOggetto());
	    log.info("protocolla# Call protocollazioneXml. mail tipo oggetto {}", protocollazioneCommand.getOggetto());
	    String classifica = getClassifica(helper, codiceComune);
	    log.info("protocolla# Call protocollazioneXml. classifica{}", classifica);
	    protocollazioneCommand.setClassifica(classifica);
	    Integer destinatario = getDestinatario(helper, codiceComune);
	    log.info("protocolla# Call protocollazioneXml. destinatario{}", destinatario);
	    Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(destinatario));
	    protocollazioneCommand.setDestinatario(ProtocolloSoggettoCommand.fromAmministrazione(amministrazioni, null));
	    List<ProtocolloSoggettoCommand> mittentis = new ArrayList<ProtocolloSoggettoCommand>();
	    // devo rendere disponibile al protocollo l'anagrafica se non censita
	    IndirizzoMailResolver mailResolver = new IndirizzoMailResolver(protAttivoService, istanza.getComune().getCodicecomune(),
		    istanza.getSoftware().getCodice());
	    mittentis.add(ProtocolloSoggettoCommand.fromRichiedente(mailResolver, popolaAnagrafeByTemplate(istanza.getRichiedente()),
		    istanza.getDomicilioElettronico(), null));
	    // tecnico eventuale
	    if (istanza.getProfessionista() != null && StringUtils.isNotBlank(istanza.getProfessionista().getCodicefiscale())) {
		Anagrafe t = popolaAnagrafeByTemplate(istanza.getProfessionista());
		// devo rendere disponibile al protocollo l'anagrafica se non censita		  
		mittentis.add(ProtocolloSoggettoCommand.fromProfessionista(mailResolver, t, istanza.getDomicilioElettronico(), null));
	    }
	    protocollazioneCommand.setMittentis(mittentis);
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    log.info("protocolla# Call protocollazioneXml.");
	    DatiRequestType sFile = generaXml(protocollazioneCommand, istanza, protAttivoService, istanza.getComune().getCodicecomune(),
		    istanza.getSoftware().getCodice());
	    popolaAllegatiDaRequest(request, sFile);
	    datiProtocollo = port.protocollazioneXml(ORMHelper.getToken(), ORMHelper.getSoftware(), sFile, codiceComune);
	    this.istanzeService.clear();
	    if (datiProtocollo != null) {
		log.info("protocolla# Call protocollazioneXml dati restituiti {}, {}.", datiProtocollo.getNumeroProtocollo(),
			datiProtocollo.getDataProtocollo());
		gestisciErroreType(datiProtocollo.getErrore(), true, "protocollaDomandaOnline");
		if (StringUtils.isNotBlank(datiProtocollo.getWarning())) {
		    String warning = "La protocollazione ha generato il seguente warning: " + datiProtocollo.getWarning();
		    log.warn(warning);
		}
	    }
	    Verticalizzazioniparametri gestisciFacicolo = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTISCI_FASCICOLAZIONE,
		    protocollazioneCommand.getComune().getCodicecomune());
	    if (gestisciFacicolo != null && StringUtils.isNotBlank(gestisciFacicolo.getValore())
		    && gestisciFacicolo.getValore().equalsIgnoreCase("1")) {
		fascicolaMovimentoOnLine(datiProtocollo, protocollazioneCommand);
	    }
	    return datiProtocollo;
	} catch (Exception e) {
	    log.error("protocolla: {}", e.getMessage());
	    throw new ProtocollaIstanzaException("Errore nella chiamata al webservice protocolla=" + e.getMessage(), e);
	}
    }

    private Anagrafe popolaAnagrafeByTemplate(Anagrafe src) {

	if (src.getId() != null && src.getId().getCodice() != null) {
	    log.info("protocolla# popolaAnagrafeByTemplate anagrafe trovata con codice {}", src.getId().getCodice());
	    return anagrafeService.findById(new PkId(src.getId().getCodice()));
	}
	log.info("protocolla# popolaAnagrafeByTemplate anagrafe non trovata la inserisco");
	BusinessRules oldAnagrafeRule = SigeproBusinessRules.getClassRules(AnagrafeBusinessRules.class);
	AnagrafeBusinessRules anagrafeBusinessRules = new AnagrafeBusinessRules(true, true);
	SigeproBusinessRules.setClassRules(AnagrafeBusinessRules.class, anagrafeBusinessRules);
	Anagrafe rich = anagrafeService.bindDomainObject(src, PkId.class, "id.codice");
	istanzeDAO.flush();
	istanzeDAO.commit();
	if (rich.getId() != null && rich.getId().getCodice() != null) {
	    log.info("protocolla# popolaAnagrafeByTemplate anagrafe inserita con codice {}", rich.getId().getCodice());
	}
	SigeproBusinessRules.setClassRules(AnagrafeBusinessRules.class, oldAnagrafeRule);
	return rich;
    }

    private void popolaAllegatiDaRequest(InserimentoPraticaNLARequest request, DatiRequestType sFile) {

	DettaglioPraticaType dettaglioPratica = request.getDettaglioPratica();
	ArrayOfAllegatoType allegati = new ArrayOfAllegatoType();
	if (dettaglioPratica != null) {
	    List<DocumentiType> docsPerPratica = nlaHelperService.getDocsPerPratica(dettaglioPratica);
	    for (DocumentiType dt : docsPerPratica) {
		if (dt.getAllegati() != null && StringUtils.isNotBlank(dt.getAllegati().getId())) {
		    String codiceOggetto = dt.getAllegati().getId().replace(NlaHelperService.CODICEOGGETTO_CONST_ALLEGATI, "");
		    AllegatoType nuovoAllegato = new AllegatoType();
		    if (Utilities.isInteger(codiceOggetto)) {
			nuovoAllegato.setCod(dt.getAllegati().getId());
			String desc = getNomeAllegato(StringUtils.defaultString(dt.getDocumento()), Integer.parseInt(codiceOggetto));
			nuovoAllegato.setDescrizione(desc);
			allegati.getAllegatoType().add(nuovoAllegato);
		    }
		}
	    }
	    if (!allegati.getAllegatoType().isEmpty()) {
		sFile.setAllegati(allegati);
	    }
	}
    }

    private void popolaAllegatiDaRequest(InserimentoAttivitaNLARequest request, DatiRequestType sFile) {

	DettaglioAttivitaType dettaglioAttivita = request.getDatiAttivita();
	ArrayOfAllegatoType allegati = new ArrayOfAllegatoType();
	if (dettaglioAttivita != null) {
	    List<DocumentiType> docsPerPratica = dettaglioAttivita.getDocumenti();
	    for (DocumentiType dt : docsPerPratica) {
		if (dt.getAllegati() != null && StringUtils.isNotBlank(dt.getAllegati().getId())) {
		    String codiceOggetto = dt.getAllegati().getId().replace(NlaHelperService.CODICEOGGETTO_CONST_ALLEGATI, "");
		    AllegatoType nuovoAllegato = new AllegatoType();
		    if (Utilities.isInteger(codiceOggetto)) {
			nuovoAllegato.setCod(dt.getAllegati().getId());
			String desc = getNomeAllegato(StringUtils.defaultString(dt.getDocumento()), Integer.parseInt(codiceOggetto));
			nuovoAllegato.setDescrizione(desc);
			allegati.getAllegatoType().add(nuovoAllegato);
		    }
		}
	    }
	    if (!allegati.getAllegatoType().isEmpty()) {
		sFile.setAllegati(allegati);
	    }
	}
    }

    private Integer getDestinatario(AlberoprocHelper helper, String codiceComune) {

	String valore = getParametroDaVerticalizzazione(
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CODICEAMMINISTRAZIONEDEFAULT, codiceComune);
	if (helper != null) {
	    List<PercorsoAlberoprocHelper> percorsoAlberoprocHelpers = helper.getPercorsoAlberoprocHelpers();
	    for (PercorsoAlberoprocHelper pah : percorsoAlberoprocHelpers) {
		AlberoprocProtocollo ap = alberoprocProtocolloService.findByAlberoprocIdAndComune(pah.getId(), codiceComune);
		if (ap != null && ap.getAmministrazioni() != null && ap.getAmministrazioni().getId() != null
			&& ap.getAmministrazioni().getId().getCodice() != null) {
		    return ap.getAmministrazioni().getId().getCodice();
		}
	    }
	}
	return Integer.parseInt(valore);
    }

    private String getClassifica(AlberoprocHelper helper, String codiceComune) {

	String valore = getParametroDaVerticalizzazione(
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CLASSIFICADEFAULT_BO, codiceComune);
	if (helper != null) {
	    List<PercorsoAlberoprocHelper> percorsoAlberoprocHelpers = helper.getPercorsoAlberoprocHelpers();
	    for (PercorsoAlberoprocHelper pah : percorsoAlberoprocHelpers) {
		AlberoprocProtocollo ap = alberoprocProtocolloService.findByAlberoprocIdAndComune(pah.getId(), codiceComune);
		if (ap != null && StringUtils.isNotBlank(ap.getScProtclassifica())) {
		    return ap.getScProtclassifica();
		}
	    }
	}
	return valore;
    }

    private Integer getOggetto() throws ConfigurationException {

	Verticalizzazioniparametri v = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		WebConstants.VERTICALIZZAZIONE_STC_IPRA_PROT_MAILTIPOOGGETTO);
	String valore = "";
	if (v != null && StringUtils.isNotBlank(v.getValore())) {
	    valore = v.getValore();
	}
	if (StringUtils.isBlank(valore)) {
	    throw new ConfigurationException("Il valore della regola " + WebConstants.VERTICALIZZAZIONE_STC + "." +
					     WebConstants.VERTICALIZZAZIONE_STC_IPRA_PROT_MAILTIPOOGGETTO + " non è stato configurato.");
	}
	if (!(Utilities.isInteger(valore))) {
	    throw new ConfigurationException("Valore della regola " + WebConstants.VERTICALIZZAZIONE_STC + "." +
					     WebConstants.VERTICALIZZAZIONE_STC_IPRA_PROT_MAILTIPOOGGETTO + " non configurato correttamente.");
	}
	return Integer.parseInt(valore);
    }

    private Integer getOggettoDaMovimento() throws ConfigurationException {

	Verticalizzazioniparametri v = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		WebConstants.VERTICALIZZAZIONE_STC_IATT_PROT_MAILTIPOOGGETTO);
	String valore = "";
	if (v != null && StringUtils.isNotBlank(v.getValore())) {
	    valore = v.getValore();
	}
	if (StringUtils.isBlank(valore)) {
	    throw new ConfigurationException("Il valore della regola " + WebConstants.VERTICALIZZAZIONE_STC + "." +
					     WebConstants.VERTICALIZZAZIONE_STC_IATT_PROT_MAILTIPOOGGETTO + " non è stato configurato.");
	}
	if (!(Utilities.isInteger(valore))) {
	    throw new ConfigurationException("Valore della regola " + WebConstants.VERTICALIZZAZIONE_STC + "." +
					     WebConstants.VERTICALIZZAZIONE_STC_IATT_PROT_MAILTIPOOGGETTO + " non configurato correttamente.");
	}
	return Integer.parseInt(valore);
    }

    @Override
    public String findProtocolloSmistamentoDefault(String codiceComune, String software) {

	Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOSMISTAMENTODEFAULT, codiceComune, software);
	if (verticalizzazioniparametri != null && StringUtils.isNotBlank(verticalizzazioniparametri.getValore())) {
	    return verticalizzazioniparametri.getValore();
	}
	return "";
    }

    private String getTipoDocumento(AlberoprocHelper helper, String codiceComune) {

	String valore = getParametroDaVerticalizzazione(
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPODOCUMENTODEFAULT, codiceComune);
	if (helper != null) {
	    List<PercorsoAlberoprocHelper> percorsoAlberoprocHelpers = helper.getPercorsoAlberoprocHelpers();
	    for (PercorsoAlberoprocHelper pah : percorsoAlberoprocHelpers) {
		AlberoprocProtocollo ap = alberoprocProtocolloService.findByAlberoprocIdAndComune(pah.getId(), codiceComune);
		if (ap != null && StringUtils.isNotBlank(ap.getScProttipodocumento())) {
		    return ap.getScProttipodocumento();
		}
	    }
	}
	return valore;
    }

    private String getParametroDaVerticalizzazione(String nomeParametro, String codiceComune) {

	Verticalizzazioniparametri p = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE, nomeParametro, codiceComune);
	if (p != null) {
	    return StringUtils.defaultString(p.getValore());
	}
	return "";
    }

    @Override
    public DatiProtocolloResponseType protocollaMovimentoOnline(Movimenti mov, InserimentoAttivitaNLARequest request, Istanze istanza)
	    throws ProtocollazioneMovimentoException {

	DatiProtocolloResponseType datiProtocollo = null;
	if (istanza == null || istanza.getAlberoproc() == null || istanza.getAlberoproc().getId() == null
		|| istanza.getAlberoproc().getId().getCodice() == null) {
	    return null;
	}
	try {
	    String codiceComune = istanza.getComune().getCodicecomune();
	    ProtocollazioneCommand protocollazioneCommand = new ProtocollazioneCommand();
	    protocollazioneCommand.setProvenienza(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI);
	    protocollazioneCommand.setFlusso(ProtocollazioneCommand.FLUSSO_ARRIVO);
	    log.info("protocolla# Call protocollaMovimentoOnline. prima di popolare l'helper");
	    Alberoproc ap = alberoprocService.findById(new PkId(istanza.getAlberoproc().getId().getCodice()));
	    AlberoprocHelper helper = alberoprocService.findAlberoprocHelper(ap);
	    String tipoDocumento = getTipoDocumento(helper, codiceComune);
	    log.info("protocolla# Call protocollaMovimentoOnline. tipo documento {}", tipoDocumento);
	    protocollazioneCommand.setTipoDocumento(tipoDocumento);
	    String smistamento = findProtocolloSmistamentoDefault(codiceComune, istanza.getSoftware().getCodice());
	    log.info("protocolla# Call protocollaMovimentoOnline. smistamento{}", smistamento);
	    protocollazioneCommand.setSmistamento(smistamento);
	    Integer oggetto = getOggettoDaMovimento();
	    log.info("protocolla# Call protocollaMovimentoOnline. mailtipo per oggetto {}", oggetto);
	    Mailtipo mailtipo = mailtipoService.findById(new PkId(oggetto));
	    Mailtipo mtp = mailtipoService.replaceOggettoCorpo(mailtipo, istanza, null);
	    protocollazioneCommand.setOggetto(mtp.getOggetto());
	    log.info("protocolla# Call protocollaMovimentoOnline. oggetto {}", mtp.getOggetto());
	    String classifica = getClassifica(helper, codiceComune);
	    log.info("protocolla# Call protocollaMovimentoOnline. classifica {}", classifica);
	    protocollazioneCommand.setClassifica(classifica);
	    Integer destinatario = getDestinatario(helper, codiceComune);
	    log.info("protocolla# Call protocollaMovimentoOnline. destinatario {}", destinatario);
	    Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(destinatario));
	    protocollazioneCommand.setDestinatario(ProtocolloSoggettoCommand.fromAmministrazione(amministrazioni, null));
	    Anagrafe rich = anagrafeService.findById(new PkId(istanza.getRichiedente().getId().getCodice()));
	    log.info("protocolla# Call protocollazioneXml. richiedente istanza {}", rich.getId());
	    List<ProtocolloSoggettoCommand> mittentis = new ArrayList<ProtocolloSoggettoCommand>();
	    mittentis.add(ProtocolloSoggettoCommand.fromAltroSoggetto(rich, null));
	    // Se l'istanza ha un tecnico lo aggiunge tra i mittenti
	    if (istanza.getProfessionista() != null && istanza.getProfessionista().getId() != null
		    && istanza.getProfessionista().getId().getCodice() != null) {
		Anagrafe intermediario = anagrafeService.findById(new PkId(istanza.getProfessionista().getId().getCodice()));
		log.info("protocolla# Call protocollazioneXml. intermediario istanza {}", intermediario.getId());
		mittentis.add(ProtocolloSoggettoCommand.fromAltroSoggetto(intermediario, null));
	    }
	    protocollazioneCommand.setMittentis(mittentis);
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    log.info("protocolla# Call protocollazioneXml.");
	    IVerticalizzazioneProtocolloAttivoService protAttivoService = new VerticalizzazioneProtocolloAttivoServiceImpl(
		    this.verticalizzazioniService, codiceComune);
	    DatiRequestType sFile = generaXml(protocollazioneCommand, istanza, protAttivoService, istanza.getComune().getCodicecomune(),
		    istanza.getSoftware().getCodice());
	    popolaAllegatiDaRequest(request, sFile);
	    datiProtocollo = port.protocollazioneXml(ORMHelper.getToken(), ORMHelper.getSoftware(), sFile, codiceComune);
	    this.istanzeService.clear();
	    if (datiProtocollo != null) {
		log.info("protocolla# Call protocollazioneXml dati restituiti {}, {}.", datiProtocollo.getNumeroProtocollo(),
			datiProtocollo.getDataProtocollo());
		gestisciErroreType(datiProtocollo.getErrore(), true, "protocollaDomandaOnline");
		if (StringUtils.isNotBlank(datiProtocollo.getWarning())) {
		    String warning = "La protocollazione ha generato il seguente warning: " + datiProtocollo.getWarning();
		    log.warn(warning);
		}
	    }
	    // Gestione fascicolazione	    
	    Verticalizzazioniparametri gestisciFacicolo = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTISCI_FASCICOLAZIONE,
		    protocollazioneCommand.getComune().getCodicecomune());
	    if (gestisciFacicolo != null && StringUtils.isNotBlank(gestisciFacicolo.getValore())
		    && gestisciFacicolo.getValore().equalsIgnoreCase("1")) {
		this.fascicolaMovimentoOnLine(datiProtocollo, protocollazioneCommand);
	    }
	    return datiProtocollo;
	} catch (Exception e) {
	    log.error("protocolla: {}", e.getMessage());
	    throw new ProtocollazioneMovimentoException("Errore nella chiamata al webservice protocolla=" + e.getMessage(), e);
	}
    }

    private void fascicolaMovimentoOnLine(DatiProtocolloResponseType datiProtocollo, ProtocollazioneCommand protocollazioneCommand) {

	try {
	    // devo settare i dati che mi ritorna dal protocollo perchè verranno usati in questo tipo di fascicolazione
	    protocollazioneCommand.setDatiProtocollo(datiProtocollo);
	    this.fascicolaXml(protocollazioneCommand);
	} catch (Exception e) {
	    log.error("protocolla: Errore nella fascicolazione dell'istanza [{}]", e.getMessage());
	}
    }

    @Override
    public int findSmistamentoMultiplo(String codiceComune, String codiceSoftware) {

	Verticalizzazioniparametri isSmistamentoMultiploAttivo = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_IS_SMISTAMENTO_MULTIPLO, codiceComune,
		codiceSoftware);
	if (isSmistamentoMultiploAttivo != null && StringUtils.isNotBlank(isSmistamentoMultiploAttivo.getValore())) {
	    String v = StringUtils.defaultString(isSmistamentoMultiploAttivo.getValore()).trim();
	    if (Utilities.isInteger(v)) {
		if (Integer.parseInt(v) == 1) {
		    return 1;
		}
		if (Integer.parseInt(v) == 2) {
		    return 2;
		}
		if (Integer.parseInt(v) == 3) {
		    return 3;
		}
	    }
	}
	return 0; // 0 non attivo
    }

    @Override
    public String findClassifica(Istanze entity, String codiceComune, String software) {

	String classifica = null;
	if (entity != null) {
	    classifica = (String) alberoprocService.findParametroprotocollo(entity.getAlberoproc(), "scProtclassifica", codiceComune);
	}
	if (StringUtils.isBlank(StringUtils.defaultString(classifica).trim())) {
	    // recupero la classifica di default dalle verticalizzazioni
	    Verticalizzazioniparametri classificaDef = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CLASSIFICADEFAULT_BO, codiceComune, software);
	    if (classificaDef != null) {
		String valore = StringUtils.defaultString(classificaDef.getValore()).trim();
		if (StringUtils.isNotBlank(valore)) {
		    classifica = classificaDef.getValore(); // non lo posso trimmare
		}
	    }
	}
	return classifica;
    }

    protected boolean lanciaEventoProtocollazioneEseguita(String numeroProtocollo) {

	String check = StringUtils.defaultString(numeroProtocollo).trim();
	check = StringUtils.defaultIfEmpty(check, ProtocollazioneService.NUMERO_PROTOCOLLO_ASINCRONO_CHAR);
	return !check.equals(ProtocollazioneService.NUMERO_PROTOCOLLO_ASINCRONO_CHAR);
    }

    @Override
    public List<ProtocolloAttivoBean> verificaProtocolloAttivo(List<ISoftwareComuneData> softwareComuneFromIdDettaglioList) {

	return this.protocollazioneDAO.verificaProtocolloAttivo(softwareComuneFromIdDettaglioList);
    }

    @Override
    public void eseguiAccettazione(String token, String numeroProtocollo, String annoProtocollo, String idProtocollo, String software,
	    String codiceComune) throws EseguiAccettazioneException {

	try {
	    log.info("Eseguendo l'accettazione del protocollo {} {} {}", new Object[] { idProtocollo, numeroProtocollo, annoProtocollo });
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    EseguiAccettazioneResponseType response = port.eseguiAccettazione(token, idProtocollo, annoProtocollo, numeroProtocollo, software,
		    codiceComune);
	    if (EnumStatusType.OK == response.getStatus()) {
		return;
	    }
	    log.error("Errore durante l'accettazione del protocollo: {} {} {} {}",
		    new Object[] { idProtocollo, numeroProtocollo, annoProtocollo, response.getErroreProtocollo().getStackTrace() });
	    throw new EseguiAccettazioneException(response.getErroreProtocollo().getDescrizione());
	} catch (Exception e) {
	    log.error("Errore durante l'accettazione del protocollo: ", e);
	    throw new EseguiAccettazioneException(e);
	}
    }

    @Override
    public DatiProtocolloEsitatoResponseType isEsitato(String token, String numeroProtocollo, String annoProtocollo, String idProtocollo,
	    String software, String codiceComune) {

	try {
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    return port.isEsitato(token, idProtocollo, annoProtocollo, numeroProtocollo, software, codiceComune);
	} catch (Exception e) {
	    log.error("Errore durante la lettura esito del protocollo : " + "{idprotocollo: " + idProtocollo + " annoprotocollo: " + annoProtocollo +
		      " numeroprotocollo: " + numeroProtocollo + "}",
		    e);
	    DatiProtocolloEsitatoResponseType errResp = new DatiProtocolloEsitatoResponseType();
	    errResp.setErrore(new ErroreProtocolloType());
	    errResp.getErrore().setDescrizione(e + "");
	    errResp.getErrore().setStackTrace(e + ""); //Per ora lasciamo così, tanto il log ce l'abbiamo qui
	    return errResp;
	}
    }
}