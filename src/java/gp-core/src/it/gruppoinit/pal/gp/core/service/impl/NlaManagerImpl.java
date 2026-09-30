package it.gruppoinit.pal.gp.core.service.impl;

import java.io.ByteArrayInputStream;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Domandestc;
import it.gruppoinit.pal.gp.core.domain.DomandestcAllegati;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiId;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Mappature;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeIstanze;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeNlaHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeStrRicalcoloRestClient;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.RicalcoloMaxReqParams;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.RicalcoloMaxRequest;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.RicalcoloAreeIstanzeDAO;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.IApplicaQRCodeService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.rest.client.DSSRestClient;
import it.gruppoinit.pal.gp.core.rest.client.models.dss.FileOriginaleDSSBean;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.DomandestcAllegatiService;
import it.gruppoinit.pal.gp.core.service.DomandestcService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService.TipoInserimento;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MappatureService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.NlaHelperService;
import it.gruppoinit.pal.gp.core.service.NlaManager;
import it.gruppoinit.pal.gp.core.service.NlaService;
import it.gruppoinit.pal.gp.core.service.SitService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.StcService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.OperazioniAutomaticheException;
import it.gruppoinit.pal.gp.core.service.exception.RemoteCallException;
import it.gruppoinit.pal.gp.core.service.helper.FileExcelParser;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.service.helper.NodoNLAEnum;
import it.gruppoinit.pal.gp.core.service.helper.SitCampiAmmessi;
import it.gruppoinit.pal.gp.core.service.rules.AnagrafeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.IstanzeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.OggettiBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.StcUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.LDPWsClient;
import it.gruppoinit.pal.gp.core.ws.client.PdfUtilsWSClient;
import it.gruppoinit.pal.gp.core.ws.client.StcWsClient;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.Sit;
import it.init.sigepro.rte.AllegatoBinarioRequest;
import it.init.sigepro.rte.AllegatoBinarioResponse;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticaNLARequest;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;
import it.init.sigepro.rte.types.CampoDinamicoType;
import it.init.sigepro.rte.types.CampoSchedaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ElementoValoreCampoDinamicoType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.RiferimentiAllegatoType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.SchedaType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.ValoreParametroType;

@Service
public class NlaManagerImpl extends NlaStcBaseServiceImpl implements NlaManager {

    private static final Logger log = LoggerFactory.getLogger(NlaManagerImpl.class);
    private DomandestcService domandestcService;
    private DomandestcAllegatiService domandestcAllegatiService;
    private IstanzeeventiService istanzeeventiService;
    private IstanzeService istanzeService;
    private IstanzestradarioService istanzestradarioService;
    private SoftwareService softwareService;
    private StcWsClient stcWsClient;
    private NlaService nlaService;
    private MappatureService mappatureService;
    private Istanzedyn2modellitService istanzedyn2modellitService;
    private Dyn2ModellitService dyn2ModellitService;
    private Istanzedyn2datiService istanzedyn2datiService;
    private Dyn2CampiService dyn2CampiService;
    private NlaHelperService nlaHelperService;
    private OggettiService oggettiService;
    private SitService sitService;
    private VerticalizzazioniService verticalizzazioniService;
    private DocumentiistanzaService documentiistanzaService;
    private IstanzeallegatiService istanzeallegatiService;
    private MovimentiallegatiService movimentiallegatiService;
    private LDPWsClient LDPWsClient;
    private ProtocollazioneService protocollazioneService;
    private StatiistanzaService statiistanzaService;
    private IApplicaQRCodeService applicaQRCodeService;
    private RicalcoloAreeIstanzeDAO ricalcoloAreeIstanzeDAO;

    @Autowired
    public void setApplicaQRCodeService(IApplicaQRCodeService applicaQRCodeService) {

	this.applicaQRCodeService = applicaQRCodeService;
    }

    @Autowired
    public void setStatiistanzaService(StatiistanzaService statiistanzaService) {

	this.statiistanzaService = statiistanzaService;
    }

    @Autowired
    public void setProtocollazioneService(ProtocollazioneService protocollazioneService) {

	this.protocollazioneService = protocollazioneService;
    }

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setIstanzeallegatiService(IstanzeallegatiService istanzeallegatiService) {

	this.istanzeallegatiService = istanzeallegatiService;
    }

    @Autowired
    public void setDomandestcService(DomandestcService domandestcService) {

	this.domandestcService = domandestcService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzestradarioService(IstanzestradarioService istanzestradarioService) {

	this.istanzestradarioService = istanzestradarioService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setStcWsClient(StcWsClient stcWsClient) {

	this.stcWsClient = stcWsClient;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setNlaService(NlaService nlaService) {

	this.nlaService = nlaService;
    }

    @Autowired
    public void setMappatureService(MappatureService mappatureService) {

	this.mappatureService = mappatureService;
    }

    @Autowired
    public void setIstanzedyn2modellitService(Istanzedyn2modellitService istanzedyn2modellitService) {

	this.istanzedyn2modellitService = istanzedyn2modellitService;
    }

    @Autowired
    public void setDyn2ModellitService(Dyn2ModellitService dyn2ModellitService) {

	this.dyn2ModellitService = dyn2ModellitService;
    }

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Autowired
    public void setDyn2CampiService(Dyn2CampiService dyn2CampiService) {

	this.dyn2CampiService = dyn2CampiService;
    }

    @Autowired
    public void setNlaHelperService(NlaHelperService nlaHelperService) {

	this.nlaHelperService = nlaHelperService;
    }

    @Autowired
    public void setDomandestcAllegatiService(DomandestcAllegatiService domandestcAllegatiService) {

	this.domandestcAllegatiService = domandestcAllegatiService;
    }

    @Autowired
    public void setSitService(SitService sitService) {

	this.sitService = sitService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setLDPWsClient(LDPWsClient lDPWsClient) {

	LDPWsClient = lDPWsClient;
    }

    @Autowired
    public void setRicalcoloAreeIstanzeDAO(RicalcoloAreeIstanzeDAO ricalcoloAreeIstanzeDAO) {

	this.ricalcoloAreeIstanzeDAO = ricalcoloAreeIstanzeDAO;
    }

    @DeletableCacheElements
    public void resetObjectCached() {

	mappaVerticalizzazioniListaNodiSTCInviaAllegati = new HashMap<String, String>();
	mappaVerticalizzazioniListaNodiSTCNonAggAnagrafe = new HashMap<String, String>();
    }

    @Override
    public InserimentoPraticaNLAResponse inserimentoPratica(InserimentoPraticaNLARequest request, String token) throws Exception {

	stcWsClient.checkToken(request.getToken());
	boolean isInserimentoDiretto = false;
	if (request.getDettaglioPratica().getAltriDati() != null) {
	    for (ParametroType param : request.getDettaglioPratica().getAltriDati()) {
		if (param != null && "$INSERIMENTO_DIRETTO$".equals(param.getNome())) {
		    isInserimentoDiretto = true;
		    break;
		}
	    }
	}
	if (!isInserimentoDiretto) {
	    // NELLA CREAZIONE DI PRATICHE DA STESSO NODO ( ES: NLA_IDNODO: 400) MA ENTE DIFFERENTE ESEMPIO TRA IDENTE E256 , IDENTE REGUMB
	    // DOBBIAMO CONSIDERARLO COME SE FOSSE INSERIMENTO PRATICA DA SISTEMA ESTERNO
	    // IL PROBLEMA E' NEL DONWLOAD DEGLI ALLEGATI CHE NON VENGONO SCARICATI IN FASE DI RICEZIONE PRATICA
	    isInserimentoDiretto = nlaHelperService.checkIsStessoNodoEnteDifferente(request.getSportelloMittente(),
		    request.getSportelloDestinatario());
	    log.debug("#inserimentoPratica isInserimentoDiretto è false ricalcolato è: {}", isInserimentoDiretto);
	}
	InserimentoPraticaNLAResponse response = new InserimentoPraticaNLAResponse();
	Istanze istanza = null;
	Domandestc domandestc = null;
	boolean passaProt = false;
	boolean erroreInProtocollazione = false;
	try {
	    Integer codiceIstanzaPrenotato = nlaHelperService.generateCodiceIstanza();
	    boolean isNuovaLogicaSuaper = nlaHelperService.verificaNuovaLogicaSuaper(request);
	    log.debug("#inserimentoPratica isNuovaLogicaSuaper: {}", isNuovaLogicaSuaper);
	    String numeroIstanzaPrenotato = nlaHelperService.generateNumeroistanza(request, isNuovaLogicaSuaper);
	    log.debug("#inserimentoPratica numeroistanzaprenotato: {}", numeroIstanzaPrenotato);
	    if (isInserimentoDiretto) {
		domandestc = inserisciDomandaStc(request, codiceIstanzaPrenotato, numeroIstanzaPrenotato, null, false);
	    }
	    // isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	    passaProt = passaProt(request.getSportelloMittente(), request.getSportelloDestinatario());
	    // Se è presente rifPraticaDestinatario allora non devo inserire la pratica ma ricercarla nel DB.
	    // Probabilmente STC non chiamerà mai questo metodo con i riferimenti pratica perchè se li trova chiama richiestaPratica
	    if (request.getRifPraticaDestinatario() != null) {
		RichiestaPraticaNLARequest richiestaPraticaNLARequest = new RichiestaPraticaNLARequest();
		richiestaPraticaNLARequest.setRifPratica(request.getRifPraticaDestinatario());
		richiestaPraticaNLARequest.setToken(request.getToken());
		richiestaPraticaNLARequest.setSportelloDestinatario(request.getSportelloDestinatario());
		richiestaPraticaNLARequest.setSportelloMittente(request.getSportelloDestinatario());
		// cerco in STC se la pratica esiste con i riferimenti request.getRifPraticaDestinatario()
		RichiestaPraticaNLAResponse praticaNLAResponse = nlaService.richiestaPratica(richiestaPraticaNLARequest);
		if (praticaNLAResponse.getDettaglioErrore() != null && praticaNLAResponse.getDettaglioErrore().size() > 0) {
		    List<ErroreType> errori = praticaNLAResponse.getDettaglioErrore();
		    for (ErroreType erroreType : errori) {
			response.getDettaglioErrore().add(erroreType);
			log.error("inserimentoPratica(): {}", erroreType.getDescrizione());
		    }
		    return response;
		}
		Integer idPratica = new Integer(praticaNLAResponse.getDettaglioPratica().getDettaglioPratica().getIdPratica());
		istanza = istanzeService.findById(new PkId(idPratica));
	    } else {
		istanza = new Istanze();
		istanza.setNumeroistanza(numeroIstanzaPrenotato);
		istanza.setId(new PkId(codiceIstanzaPrenotato));
		IstanzeNlaHelper istanzeNlaHelper = nlaHelperService.populateIstanza(istanza, request, passaProt, isNuovaLogicaSuaper, false);
		if (isNuovaLogicaSuaper && StringUtils.isNotBlank(istanzeNlaHelper.getNumeroistanzaprenotato())) {
		    numeroIstanzaPrenotato = istanzeNlaHelper.getNumeroistanzaprenotato();
		    log.debug("inserimento pratica isNuovaLogicaSuaper={} & numeroIstanzaPrenotato={}", isNuovaLogicaSuaper, numeroIstanzaPrenotato);
		    istanza.setNumeroistanza(numeroIstanzaPrenotato);
		    domandestc.setNumeroistanza(numeroIstanzaPrenotato);
		}
		istanza = istanzeNlaHelper.getIstanze();
		// business rules
		Verticalizzazioniparametri protocollaprimadinotificare = verticalizzazioniService.getVerticalizzazioniparametri(
			WebConstants.VERTICALIZZAZIONE_STC, WebConstants.VERTICALIZZAZIONE_STC_IPRA_PROT_PRIMA_DI_INSERIRE);
		if (protocollaprimadinotificare != null
			&& StringUtils.defaultString(protocollaprimadinotificare.getValore(), "0").equalsIgnoreCase("1")) {
		    boolean isNLAAreaRiservata = nlaHelperService.checkSportello(request.getSportelloMittente(),
			    NodoNLAEnum.NLA_IDNODO_AREARISERVATA);
		    if (isNLAAreaRiservata) {
			try {
			    protocollaPrimadiInserire(request, istanza);
			} catch (FunzioneBusinessRemotaException e) {
			    erroreInProtocollazione = true;
			    throw new FunzioneBusinessRemotaException(e);
			}
		    }
		}
		boolean isUpdateAnagrafe = isUpdateAnagrafePerNodo(request.getSportelloMittente());
		AnagrafeBusinessRules anagrafeBusinessRules = new AnagrafeBusinessRules(true, isUpdateAnagrafe);
		SigeproBusinessRules.setClassRules(AnagrafeBusinessRules.class, anagrafeBusinessRules);
		OggettiBusinessRules oggettiBusinessRules = new OggettiBusinessRules();
		oggettiBusinessRules.setInsert(true);
		SigeproBusinessRules.setClassRules(OggettiBusinessRules.class, oggettiBusinessRules);
		IstanzeBusinessRules istanzeBusinessRules = new IstanzeBusinessRules(true, true);
		istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.generaAutomaticamenteDocumentiIstanza.name(), false);
		istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserisciResponsabileProcedimentoseNonPresenteOperatore.name(),
			true);
		istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name(), true);
		//		//	
		//		// Se il nodo non aggiorna le anagrafiche allora setto la regola per l'aggiornamento anagrafica = false (si riflette in AnagrafeService)
		//		//
		if (isInserimentoDiretto) {
		    istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStcDiretto.name(), true);
		}
		SigeproBusinessRules.setClassRules(IstanzeBusinessRules.class, istanzeBusinessRules);
		TipoInserimento tipoInserimento = checkTipoInserimento(request.getSportelloMittente(), request.getSportelloDestinatario(),
			istanza.getComune());
		boolean isInsertOneriDaEndoAutomaticamente = isInsertOneriDaEndoAutomaticamentePerNodo(request.getSportelloMittente(),
			request.getSportelloDestinatario());
		istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.generaAutomaticamenteOneriIstanza.name(),
			isInsertOneriDaEndoAutomaticamente);
		setRifMittente(istanza, request);
		istanzeService.insert(istanza, tipoInserimento, new PostIstanzeCallBackNlaManagerImpl(this, request, istanzeeventiService));
		if (isInserimentoDiretto) {
		    aggiornaDomandaStcConSuccesso(domandestc, istanza);
		} else {
		    domandestc = inserisciDomandaStc(request, codiceIstanzaPrenotato, numeroIstanzaPrenotato, istanza, true);
		}
		// VALIDAZIONE DEGLI STRADARI ASSOCIATO ALL'ISTANZA TRAMITE SERVIZIO SIT
		boolean isAttivoSit = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO);
		if (isAttivoSit) {
		    try {
			this.effettuaValidazioneIstanzeStardario(istanza);
		    } catch (Exception e) {
			istanzeeventiService.insert("Errore nella validazione dello stradario: " + e.getMessage(),
				IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
		    }
		}
		// Inserisce in istanza eventi le informazioni che vengono riportate alla ricerca di un intervento
		try {
		    this.insertEventoRicercaInterventi(istanzeNlaHelper);
		} catch (Exception e) {
		    istanzeeventiService.insert("Errore nella esecuzione delle formule delle schede dinamiche: " + e.getMessage(),
			    IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
		}
	    }
	    // Sono operazioni automatiche Es. notifiche ad altri servizi che vengono eseguite in base 
	    // a delle regole
	    operazioniAutomatiche(istanza, domandestc);
	    RiferimentiPraticaType riferimentiPraticaType = new RiferimentiPraticaType();
	    GregorianCalendar dataPratica = new GregorianCalendar();
	    dataPratica.setTime(istanza.getData());
	    riferimentiPraticaType.setDataPratica(Utilities.getXMLGregorianCalendar(dataPratica));
	    //GIANPAOLO-ORA
	    String orarioData = Utilities.getOrario(istanza.getData());
	    riferimentiPraticaType.setOraDataPratica(orarioData);
	    riferimentiPraticaType.setIdPratica(istanza.getId().getCodice().toString());
	    riferimentiPraticaType.setNumeroPratica(istanza.getNumeroistanza());
	    addUUIDIstanza(istanza, riferimentiPraticaType);
	    // gestione protocollo su response
	    if (passaProt && (istanza != null && istanza.getDataprotocollo() != null)) {
		riferimentiPraticaType.setNumeroProtocolloGenerale(istanza.getNumeroprotocollo());
		GregorianCalendar dataProtocollo = new GregorianCalendar();
		dataProtocollo.setTime(istanza.getDataprotocollo());
		riferimentiPraticaType.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(dataProtocollo));
	    }
	    response.setDettaglioPratica(riferimentiPraticaType);
	} catch (Exception e) {
	    String err = this.getErrore(e);
	    log.error("Errore in inserimentoPratica()", e);
	    try {
		istanzeeventiService.insertEventoBackoffice("ERRORE " + err, "STC-INS-PRA", ORMHelper.getSoftware());
	    } catch (Exception e1) {
		log.error("Errore durante l'inserimento dell'evento: {}", e.getMessage(), e);
	    }
	    if (isInserimentoDiretto && domandestc != null) {
		if (erroreInProtocollazione) {
		    // cancellaDomandaStc
		    domandestcService.delete(domandestc, false);
		    //		    ErroreType et = new ErroreType();
		    //		    et.setNumeroErrore("9001");
		    //		    et.setDescrizione("Non è stato possibile protocollare la pratica. riprovare in un secondo momento");
		    //		    response.getDettaglioErrore().add(et);
		    throw new RuntimeException("Non è stato possibile protocollare la pratica. riprovare in un secondo momento");
		}
		this.aggiornaDomandaStcConErrore(domandestc, istanza, err);
		RiferimentiPraticaType riferimentiPraticaType = new RiferimentiPraticaType();
		GregorianCalendar dataPratica = new GregorianCalendar();
		dataPratica.setTime(request.getDettaglioPratica().getDataPratica().toGregorianCalendar().getTime());
		riferimentiPraticaType.setDataPratica(Utilities.getXMLGregorianCalendar(dataPratica));
		riferimentiPraticaType.setIdPratica(String.valueOf(domandestc.getCodiceistanzaprenotata()));
		riferimentiPraticaType.setNumeroPratica(domandestc.getNumeroistanza());
		// gestione protocollo su response
		if (passaProt && (istanza != null && istanza.getDataprotocollo() != null)) {
		    riferimentiPraticaType.setNumeroProtocolloGenerale(istanza.getNumeroprotocollo());
		    GregorianCalendar dataProtocollo = new GregorianCalendar();
		    dataProtocollo.setTime(istanza.getDataprotocollo());
		    riferimentiPraticaType.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(dataProtocollo));
		}
		response.setDettaglioPratica(riferimentiPraticaType);
	    } else {
		throw new Exception(err);
	    }
	} finally {
	    SigeproBusinessRules.buildDefaultRules();
	}
	return response;
    }

    /**
     * Aggiunge su altri DATI l'UUID della pratica
     * 
     * @param istanza
     * @param riferimentiPraticaType
     */
    private void addUUIDIstanza(Istanze istanza, RiferimentiPraticaType riferimentiPraticaType) {

	ParametroType altroDatoUUID = new ParametroType();
	altroDatoUUID.setNome(StcService.ALTRO_DATO_UUID_ISTANZA);
	ValoreParametroType vptUiid = new ValoreParametroType();
	vptUiid.setCodice(istanza.getUuid());
	vptUiid.setDescrizione(istanza.getUuid());
	altroDatoUUID.getValore().add(vptUiid);
	riferimentiPraticaType.getAltriDati().add(altroDatoUUID);
    }

    private void protocollaPrimadiInserire(InserimentoPraticaNLARequest request, Istanze istanza) throws FunzioneBusinessRemotaException {

	// solo se area riservata perché gli oggetti con codiceoggetto già esistono
	try {
	    DatiProtocolloResponseType protocollaDomandaOnline = protocollazioneService.protocollaDomandaOnline(request, istanza);
	    istanza.setNumeroprotocollo(protocollaDomandaOnline.getNumeroProtocollo());
	    Date d = Utilities.getDate(protocollaDomandaOnline.getDataProtocollo(), WebConstants.DATE_FORMAT_PATTERN).getTime();
	    istanza.setDataprotocollo(d);
	    istanza.setFkidprotocollo(protocollaDomandaOnline.getIdProtocollo());
	    if (request.getDettaglioPratica() != null) {
		if (StringUtils.isBlank(request.getDettaglioPratica().getNumeroProtocolloGenerale())) {
		    request.getDettaglioPratica().setNumeroProtocolloGenerale(protocollaDomandaOnline.getNumeroProtocollo());
		}
		if (request.getDettaglioPratica().getDataProtocolloGenerale() == null) {
		    GregorianCalendar c = new GregorianCalendar();
		    c.setTime(d);
		    request.getDettaglioPratica().setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(c));
		}
	    }
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    /**
     * Verifica in base alla configurazione della vericalizzazione STC se l'inserimento pratica deve inserire o no gli
     * oneri associati all'endo in modo automatico. Il metodo controlla se per il software di destinazione (idSportello
     * dest) esiste la configurazione nella verticalizzazione STC nella quale il parametro LISTA_NODI_MITT_NO_ONERI_ENDO
     * abbia l'id nodo dello sportello mittente:
     * 
     * ES. Sportello mitt: nodo 400, sportello SS Sportello dest: nodo 400, sportello CE
     * 
     * se per il software CE: LISTA_NODI_MITT_NO_ONERI_ENDO contiene il nodo 400 1. LISTA_NODI_MITT_NO_ONERI_ENDO
     * contiene il nodo 400 gli oneri non saranno inserti 2. LISTA_NODI_MITT_NO_ONERI_ENDO non contiene il nodo 400 gli
     * oneri saranno inserti
     * 
     * Il metodo ritorna un booleano che pilota una bussinen rule 1. true : inserisce oneri 2. false : non inserisce
     * oneri
     * 
     * @param sportelloMittente
     * @param sportelloDestinatario
     * @return
     */
    private boolean isInsertOneriDaEndoAutomaticamentePerNodo(SportelloType sportelloMittente, SportelloType sportelloDestinatario) {

	log.debug(
		"isInsertOneriDaEndoAutomaticamentePerNodo# Controllo se il nodo dello sportello mittente è configurato per non inserire automaticamente gli" +
		  " oneri asociati all'endo [{}-{}]",
		new Object[] { WebConstants.VERTICALIZZAZIONE_STC_LISTA_NODI_MITT_NO_ONERI_ENDO, WebConstants.VERTICALIZZAZIONE_STC });
	boolean insert = true;
	String nodoMitt = sportelloMittente.getIdNodo();
	String sportelloDest = sportelloDestinatario.getIdSportello();
	if (sportelloDestinatario != null && StringUtils.isNotBlank(sportelloDestinatario.getIdSportello())) {
	    log.debug("isInsertOneriDaEndoAutomaticamentePerNodo# Nodo mittente {}", nodoMitt);
	    // Cerco la lista di nodi configurati per il software dello sportello destinatario (idSportello), se non trovo niente
	    // per lo sportello controllo per TT
	    Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_STC, WebConstants.VERTICALIZZAZIONE_STC_LISTA_NODI_MITT_NO_ONERI_ENDO, sportelloDest);
	    // Verifico se trovo la configurazione
	    if (verticalizzazioniparametri != null && StringUtils.isNotBlank(verticalizzazioniparametri.getValore())) {
		log.debug(
			"isInsertOneriDaEndoAutomaticamentePerNodo# Lista nodi mittenti che non inseriscono gli oneri degli endo in fase di notifica {} configurati in {} di {}",
			new Object[] { verticalizzazioniparametri.getValore(), WebConstants.VERTICALIZZAZIONE_STC_LISTA_NODI_MITT_NO_ONERI_ENDO,
				WebConstants.VERTICALIZZAZIONE_STC });
		String[] ArryNodi = StringUtils.split(verticalizzazioniparametri.getValore(), ",");
		if (ArrayUtils.contains(ArryNodi, nodoMitt)) {
		    insert = false;
		}
	    } else {
		log.debug(
			"isInsertOneriDaEndoAutomaticamentePerNodo# Non ci sono nodi mittenti configurati nella verticalizzazione  {} per cui non devo essere inseriti gli oneri dell'endo in fase di notifica",
			WebConstants.VERTICALIZZAZIONE_STC);
	    }
	} else {
	    log.debug(
		    "isInsertOneriDaEndoAutomaticamentePerNodo# Non è stato trovato l'id (software) dello sportello destinatario, non posso sapere se la regola è configurata nella verticalizzazione {}",
		    WebConstants.VERTICALIZZAZIONE_STC);
	}
	return insert;
    }

    private void setRifMittente(Istanze istanza, InserimentoPraticaNLARequest request) {

	istanza.setTransientIdNodoMittente(request.getSportelloMittente().getIdNodo());
	istanza.setTransientIdEnteMittente(request.getSportelloMittente().getIdEnte());
	istanza.setTransientIdSportelloMittente(request.getSportelloMittente().getIdSportello());
	int countTuple = amministrazioniService.countAmministrazioniSTC(request.getSportelloMittente().getIdNodo(),
		request.getSportelloMittente().getIdEnte(), request.getSportelloMittente().getIdSportello());
	if (countTuple != 1) {
	    ValoreParametroType vpt = StcUtils.getCampoDaAltriDati(request, NlaHelperService.NOTIFICA_ATTIVITA_CODICE_AMMINISTRAZIONE_STC_MITTENTE);
	    if (vpt != null) {
		String codiceAmm = StringUtils.trim(StringUtils.defaultString(vpt.getCodice()));
		log.debug("NLA_WS.GEST_PROT: La configurazione trovata nell'altro dato {} ha codice amministrazione {}",
			new String[] { NlaHelperService.NOTIFICA_ATTIVITA_CODICE_AMMINISTRAZIONE_STC_MITTENTE, codiceAmm });
		if (Utilities.isInteger(codiceAmm)) {
		    istanza.setTransientAmministrazioneSTCMittente(String.valueOf(codiceAmm));
		}
	    }
	}
    }

    /**
     * 
     * <pre>
     * Effettua la verifica sugli stradari associati all'istanza:
     * 	       1- Validazione dello stradario per il campo esponente. Per ogni stradario effettua la validazione per il campo esponenete
     *            attraverso il sistema SIT e aggiorna il valore istanzestradario.codViario, se viene ritornato dal sistema SIT
     *         2- Validazione formale sugli stradari associati all'istanza. Per ogni stradario effettua la verifica
     *            formale attraverso il sistema SIT e aggiorna il valore istanzestradario.valido con il valore tornato da SIT se
     *            diverso da quello già salvato.
     * 
     * &#64;param istanza
     * </pre>
     */
    private void effettuaValidazioneIstanzeStardario(Istanze istanza) {

	log.debug("effettuaValidazioneIstanzeStardario# Inizio validazione  dei singoli staradari associati all'istanza.....");
	// REcupero tutti gli stradario associati
	List<Istanzestradario> istanzestradarios = istanzestradarioService.findByIstanza(istanza.getId().getCodice());
	Istanzestradario istanzeStradarioPerValidazione = null;
	// Ciclo gli stradari
	for (Istanzestradario istanzestradario : istanzestradarios) {
	    // PASSO 1 : Effettuo la validazione delle stradario per il campo "ESPONENTE", se l'oggetto SIT ritornato
	    // ha popolato il "Sit.CodCivico" allora significa che lo stradario è validato per il campo esponenete
	    // (si è deciso di fare la validazione solo sul campo esponenete.)
	    log.debug("effettuaValidazioneIstanzeStardario# Inizio validazione dell' istanza stradario: {} per il campo esponente",
		    istanzestradario.getId().getCodice());
	    istanzeStradarioPerValidazione = istanzestradarioService.copyObjecIstanzeStradarioWithoutIstanzeLavotiT(istanzestradario);
	    //Istanzestradario istStrd = istanzestradarioService.findById(new PkId(istanzestradario.getId().getCodice()));
	    Sit sit = new Sit();
	    try {
		// Invoco il servizio sit
		sit = sitService.validaValore(ORMHelper.getToken(), SitCampiAmmessi.valueOf("ESPONENTE_ID"), istanzeStradarioPerValidazione);
	    } catch (BusinessValidationException bussEx) {
		log.error(
			"effettuaValidazioneIstanzeStardario#Non è stato possibile fare la validazione dello stradario {} (cod. istanze stradario {}. Errore: {})",
			new Object[] { istanzeStradarioPerValidazione.getStradario().getDescrizione(),
				istanzeStradarioPerValidazione.getId().getCodice(), bussEx.getMessage() });
	    } catch (RemoteCallException e1) {
		log.error(
			"effettuaValidazioneIstanzeStardario#Non è stato possibile fare la validazione dello stradario {} (cod. istanze stradario {}. Errore: {})",
			new Object[] { istanzeStradarioPerValidazione.getStradario().getDescrizione(),
				istanzeStradarioPerValidazione.getId().getCodice(), e1.getMessage() });
	    }
	    if (sit != null && StringUtils.isNotBlank(sit.getCodCivico())) {
		log.debug("effettuaValidazioneIstanzeStardario# L'istanza stradario con codice {} è stato validato. Codice civico : {}",
			new Object[] { istanzestradario.getId().getCodice(), sit.getCodCivico() });
		istanzeStradarioPerValidazione.setCodicecivico(sit.getCodCivico());
		// Aggiorno lo stradario con il sit.CodCivico settato
		//istanzestradarioService.update(istanzestradario);
		istanzestradarioService.updateFieldCodicecivico(istanzeStradarioPerValidazione.getId().getCodice(), sit.getCodCivico());
	    }
	    log.debug("effettuaValidazioneIstanzeStardario# Fine validazione del singolo stradario staradario:{} ",
		    istanzestradario.getId().getCodice());
	    // PASSO 2 : Effettuo la validazione formale delle stradario se ritorna:
	    // true: stradario validato formalemente
	    // false: stradario non validato formalmente
	    // (si è deciso di fare la validazione solo sul campo esponenete.)
	    try {
		log.debug("effettuaValidazioneIstanzeStardario# Inizio validazione  formale dell' istanza stradario: {} ",
			istanzestradario.getId().getCodice());
		// Variabile di appoggio che utilizziamo per decidere se aggiornare o no
		boolean aggiorna = false;
		// Valore che verrà utilizzato per aggiornare il valore di istanzestradario.valido recuperato dal sit,
		//se la variabile aggiorna ritorna true
		boolean isValidatoFormalmente = sitService.effettuaValidazioneFormale(ORMHelper.getToken(), ORMHelper.getSoftware(),
			istanzeStradarioPerValidazione);
		log.debug("effettuaValidazioneIstanzeStardario# valore di validazione ritornato dal SIT: {}", isValidatoFormalmente);
		// Ritorna true se istanzestradario.getValido()=true, ritorna false se istanzestradario.getValido()==false or istanzestradario.getValido()=null
		Boolean isValido = null;
		if (istanzestradario.getValido() == null || istanzestradario.getValido().equals(false)) {
		    isValido = new Boolean(false);
		} else {
		    isValido = new Boolean(true);
		}
		// CASO A: il sit ritorna stradario validato formalmente (true)
		if (isValidatoFormalmente == true) {
		    log.debug("effettuaValidazioneIstanzeStardario# CASO A: stradario validato formalmente ({}), valore istanzastradario.valido : {}",
			    new Object[] { isValidatoFormalmente, isValido });
		    aggiorna = (isValido == true ? false : true);
		    log.debug("effettuaValidazioneIstanzeStardario# Aggiorno valore {}", aggiorna);
		} else {// CASO B: il sit ritorna stradario non validato formalmente (false)
		    log.debug("effettuaValidazioneIstanzeStardario# CASO B: stradario validato formalmente ({}), valore istanzastradario.valido : {}",
			    new Object[] { isValidatoFormalmente, isValido });
		    aggiorna = (isValido == true ? true : false);
		    log.debug("effettuaValidazioneIstanzeStardario# Aggiorno valore {}", aggiorna);
		}
		log.debug("effettuaValidazioneIstanzeStardario# Fine validazione formale dell' istanza stradario: {} ",
			istanzestradario.getId().getCodice());
		// Se aggiorna==true allora facciamo update di istanzestradario.valido con il valore ritornato dal sit
		if (aggiorna) {
		    //		    Istanzestradario istanzeStradarioDaModificare = istanzestradarioService.findById(new PkId(istanzestradario.getId().getCodice()));
		    //		    istanzeStradarioDaModificare.setValido(new Boolean(isValidatoFormalmente));
		    istanzestradarioService.updateFildValido(istanzestradario.getId().getCodice(), isValidatoFormalmente);
		}
	    } catch (RemoteCallException e) {
		log.error("Validazione formale per l'istanza stradario cod. {} non effettuata a causa : {}({})",
			new Object[] { istanzestradario.getId().getCodice(), e.getMessage(), e });
	    }
	}
    }

    /**
     * La funzione legge se è configurato il parametro di verticalizzazione
     * {@link WebConstants#VERTICALIZZAZIONE_STC_LISTA_NODI_NON_AGG_ANAGRAFE}. <br />
     * Se configurato, questo contiene una stringa che rappresenta gli id dei nodi che non devono aggiornare gli
     * attributi della scheda anagrafica (qualora venisse identificata per CF o PIVA). Questo controllo è stato
     * realizzato per la richiesta di Cervia (Provincia di Ravenna) che non vuole che ad ogni domanda proveniente da
     * PEOPLE produca una scheda storicizzata.
     * 
     * @param sportelloMittente
     * @return
     */
    private boolean isUpdateAnagrafePerNodo(SportelloType sportelloMittente) {

	String valore = getValoreListaNodiSTCNonAggAnagrafe();
	if (StringUtils.isNotBlank(valore)) {
	    String idNodoMittente = StringUtils.defaultIfEmpty(sportelloMittente.getIdNodo(), "").trim();
	    String[] listaNodi = valore.split(",");
	    for (String nodo : listaNodi) {
		if (idNodoMittente.equalsIgnoreCase(StringUtils.defaultIfEmpty(nodo, "").trim())) {
		    return false;
		}
	    }
	}
	return true;
    }

    /**
     * Inserisce l'xml di InserimentoPraticaNLARequest in Domandestc
     * 
     * @param request
     * @param isInserimentoDiretto
     *            true=esegue la insert in oggetti e domandestc, false=calcola solamente codice e numero istanza
     * @return
     */
    private Domandestc inserisciDomandaStc(InserimentoPraticaNLARequest request, Integer codiceIstanzaPrenotato, String numeroIstanzaPrenotato,
	    Istanze istanza, boolean isInserimentoIstanzaRiuscito) {

	Domandestc domandestc = new Domandestc();
	domandestc.setCodiceistanzaprenotata(codiceIstanzaPrenotato);
	domandestc.setNumeroistanza(numeroIstanzaPrenotato);
	domandestc.setIstanza(istanza);
	Software software = softwareService.findById(request.getSportelloDestinatario().getIdSportello());
	domandestc.setFlagImport(isInserimentoIstanzaRiuscito);
	domandestc.setSoftware(software);
	domandestc.setIdNodo(request.getSportelloMittente().getIdNodo());
	domandestc.setIdEntemitt(request.getSportelloMittente().getIdEnte());
	domandestc.setIdSportellomitt(request.getSportelloMittente().getIdSportello());
	domandestc.setIdDomandamitt(request.getDettaglioPratica().getIdPratica());
	domandestc.setComune(nlaHelperService.getComune(request.getDettaglioPratica()));
	if (request.getDettaglioPratica().getRichiedente() != null && request.getDettaglioPratica().getRichiedente().getAnagrafica() != null) {
	    PersonaFisicaType richiedente = request.getDettaglioPratica().getRichiedente().getAnagrafica();
	    String nomeCompleto = richiedente.getNome() + " " + richiedente.getCognome();
	    domandestc.setRichiedente(nomeCompleto);
	    domandestc.setCodicefiscaleRichiedente(richiedente.getCodiceFiscale());
	}
	List<DomandestcAllegati> domandestcAllegatis = new ArrayList<DomandestcAllegati>();
	allegatiPraticaStc(request.getDettaglioPratica(), domandestcAllegatis, request);
	Oggetti oggettoDomanda = new Oggetti();
	String xmlDomanda = Utilities.marshallObject(request);
	try {
	    oggettoDomanda.setOggetto(xmlDomanda.getBytes("UTF-8"));
	} catch (UnsupportedEncodingException e1) {
	    oggettoDomanda.setOggetto(xmlDomanda.getBytes());
	}
	//FIXME questa è una cazzata
	oggettoDomanda.setNomefile("domanda" + domandestc.getIdDomandamitt() + ".xml");
	oggettiService.insert(oggettoDomanda);
	domandestc.setOggetti(oggettoDomanda);
	domandestcService.insert(domandestc);
	for (DomandestcAllegati domandestcAllegati : domandestcAllegatis) {
	    domandestcAllegati.setDomandestc(domandestc);
	    domandestcAllegatiService.insert(domandestcAllegati);
	}
	return domandestc;
    }

    private void allegatiPraticaStc(DettaglioPraticaType dettaglioPratica, List<DomandestcAllegati> domandestcAllegatis,
	    InserimentoPraticaNLARequest iprequest) {

	boolean isCodiceOggettoAltroSistema = nlaHelperService.isCodiceOggettoAltroSistema(iprequest.getSportelloMittente(),
		iprequest.getSportelloDestinatario());
	List<DocumentiType> docs = nlaHelperService.getDocsPerPratica(dettaglioPratica);
	if (nlaHelperService.isScaricaSubitoAllegatiFisiciPerNodo(iprequest.getSportelloMittente())) {
	    log.debug("il nodo mittente {} è configurato per scaricare subito gli allegati assati come riferimento",
		    iprequest.getSportelloMittente().getIdNodo());
	    for (DocumentiType doc : docs) {
		if (doc != null) {
		    log.debug("processo il documento {}", doc.getId());
		    manageAllegatiDaScaricareSubito(doc, domandestcAllegatis, iprequest, isCodiceOggettoAltroSistema);
		    log.debug("finito il processamente del documento {}", doc.getId());
		}
	    }
	} else {
	    for (DocumentiType doc : docs) {
		manageAllegatiTypeForMTOM(doc, domandestcAllegatis, isCodiceOggettoAltroSistema);
	    }
	}
    }

    private void manageAllegatiDaScaricareSubito(DocumentiType doc, List<DomandestcAllegati> domandestcAllegatis,
	    InserimentoPraticaNLARequest iprequest, boolean isCodiceOggettoAltroSistema) {

	if (doc != null && doc.getAllegati() != null && StringUtils.isNotBlank(doc.getAllegati().getId())) {
	    if (doc.getAllegati().getFile() == null) {
		log.debug("Non è presente il datahandler del doc {}, lo processo chiamando allegato binario", doc.getId());
		AllegatoBinarioRequest request = new AllegatoBinarioRequest();
		request.setToken(iprequest.getToken());
		request.setSportelloMittente(iprequest.getSportelloDestinatario());
		request.setSportelloDestinatario(iprequest.getSportelloMittente());
		RiferimentiAllegatoType rif = new RiferimentiAllegatoType();
		rif.setIdAllegato(doc.getAllegati().getId());
		rif.setIdDocumento(doc.getId());
		rif.setIdPratica(iprequest.getDettaglioPratica().getIdPratica());
		request.setRiferimentiAllegato(rif);
		AllegatoBinarioResponse response = null;
		String dettaglioRequest = ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE);
		try {
		    log.debug("Prima della chiamata allegato binario per il doc {}", dettaglioRequest);
		    response = stcWsClient.allegatoBinario(request);
		    log.debug("Allegato binario OK");
		} catch (Exception e) {
		    log.error("manageAllegatiDaScaricareSubito# errore nel recupero dell'allegato STC {}-{}: {}",
			    new Object[] { doc.getId(), doc.getAllegati().getId(), e });
		    throw new RuntimeException("non è stato possibile scaricare l'allegato " + doc.getDocumento() + " con id " + doc.getId() + ":" +
					       doc.getAllegati().getId() + " dal sistema remoto identificato da " + iprequest.getSportelloMittente());
		}
		if (response != null) {
		    try {
			DataHandler dh = response.getBinaryData();
			if (dh != null) {
			    if (isCodiceOggettoAltroSistema) {
				doc.setId(NlaHelperService.CODICEOGGETTO_CONST_ALLEGATI_CODICE_ALTRO_SISTEMA + doc.getAllegati().getId());
			    }
			    log.debug("Inserisco gli oggetti di allegato binario per il doc {}", dettaglioRequest);
			    Oggetti ogg = new Oggetti();
			    ogg.setNomefile(response.getFileName());
			    ogg.setOggetto(Utilities.dataHandlerToBytes(dh));
			    oggettiService.insert(ogg);
			    log.debug("Oggetti di allegato binario per il doc {} INSERITI", dettaglioRequest);
			    DomandestcAllegati domandestcAllegati = new DomandestcAllegati();
			    domandestcAllegati.setOggetti(ogg);
			    domandestcAllegatis.add(domandestcAllegati);
			    doc.getAllegati().setFile(null);
			    doc.getAllegati().setId(NlaHelperService.CODICEOGGETTO_CONST_ALLEGATI + ogg.getId().getCodice());
			}
		    } catch (Exception e) {
			log.error("manageAllegatiDaScaricareSubito# errore nel recupero dell'allegato STC {}-{}: {}",
				new Object[] { doc.getId(), doc.getAllegati().getId(), e });
			throw new RuntimeException("non è stato possibile scaricare l'allegato " + doc.getDocumento() + " con id " + doc.getId() +
						   ":" + doc.getAllegati().getId() + " dal sistema remoto identificato da " +
						   iprequest.getSportelloMittente());
		    }
		}
	    } else {
		// SE PRESENTE MTOM PROCESSO ALLA VECCHIA MANIERA
		manageAllegatiTypeForMTOM(doc, domandestcAllegatis, isCodiceOggettoAltroSistema);
	    }
	}
    }

    /**
     * il metodo esegue le seguenti operazioni:<br />
     * 1. inserisco nella tabella oggetti l'allegato binario contenuto in allegatiType.<br />
     * 2. aggiungo alla lista domandestcAllegatis l'oggetto appena inserito (serve per hibernate)<br />
     * 3. setto a NULL la property file (allegatoBinarioType) di allegatiType 3. modifico la property id di allegatiType
     * con la stringa "CODICEOGGETTO:"+codiceoggetto dell'oggetto inserito al punto 1.
     * 
     * @param allegatiType
     * @param domandestcAllegatis
     * @param isCodiceOggettoAltroSistema
     */
    private void manageAllegatiTypeForMTOM(DocumentiType documentiType, List<DomandestcAllegati> domandestcAllegatis,
	    boolean isCodiceOggettoAltroSistema) {

	if (documentiType != null && documentiType.getAllegati() != null && documentiType.getAllegati().getFile() != null
		&& documentiType.getAllegati().getFile().getFileName() != null && documentiType.getAllegati().getFile().getBinaryData() != null) {
	    Oggetti ogg = new Oggetti();
	    ogg.setNomefile(documentiType.getAllegati().getFile().getFileName());
	    ogg.setOggetto(Utilities.dataHandlerToBytes(documentiType.getAllegati().getFile().getBinaryData()));
	    oggettiService.insert(ogg);
	    DomandestcAllegati domandestcAllegati = new DomandestcAllegati();
	    domandestcAllegati.setOggetti(ogg);
	    domandestcAllegatis.add(domandestcAllegati);
	    if (isCodiceOggettoAltroSistema) {
		documentiType.setId(NlaHelperService.CODICEOGGETTO_CONST_ALLEGATI_CODICE_ALTRO_SISTEMA + documentiType.getAllegati().getId());
	    }
	    documentiType.getAllegati().setFile(null);
	    documentiType.getAllegati().setId(NlaHelperService.CODICEOGGETTO_CONST_ALLEGATI + ogg.getId().getCodice());
	}
    }

    /**
     * metodo per l'aggiornamento di DOMANDE_STC
     * 
     * @param domandestc
     * 
     * @param istanza
     * 
     */
    private void aggiornaDomandaStcConSuccesso(Domandestc domandestc, Istanze istanza) {

	domandestc.setFlagImport(true);
	domandestc.setIstanza(istanza);
	domandestcService.update(domandestc);
    }

    /**
     * metodo per l'aggiornamento di DOMANDE_STC
     * 
     * @param domandestc
     * @param istanza
     * @param ultimoErrore
     */
    private void aggiornaDomandaStcConErrore(Domandestc domandestc, Istanze istanza, String ultimoErrore) {

	boolean istanzaInserita = false;
	if (istanza != null && istanza.getId() != null && istanza.getId().getCodice() != null) {
	    istanzaInserita = istanzeService.existsById(istanza.getId().getCodice());
	}
	if (istanzaInserita) {
	    domandestc.setIstanza(istanza);
	    domandestc.setFlagImport(true); // la segno come importata e segno l'evento nella istanza
	    try {
		istanzeeventiService.insert("Istanza inserita ma si è verificato il seguente errore " + ultimoErrore,
			IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
	    } catch (Exception e) {
		log.error("aggiornaDomandaStc ", e);
	    }
	} else {
	    domandestc.setFlagImport(false);
	    domandestc.setIstanza(null);
	}
	if (StringUtils.isNotBlank(ultimoErrore)) {
	    domandestc.setUltimoerrore(ultimoErrore);
	    domandestc.setDataUltimoerrore(Calendar.getInstance().getTime());
	}
	domandestcService.update(domandestc);
    }

    private String getErrore(Exception e) {

	StringBuffer err = new StringBuffer();
	if (e instanceof BaseValidationException) {
	    List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
	    for (InvalidValue invalidValue : ivs) {
		String messaggio = invalidValue.getBeanClass() + " " + invalidValue.getPropertyName() + " " + invalidValue.getMessage();
		err.append(messaggio).append("\n");
	    }
	} else {
	    err.append(e.getMessage());
	}
	return err.toString();
    }

    /**
     * 
     * Metodo che inserisce un evento se non è stato individuato univocamente un intervento.<br />
     * Se non è stato individuato nessun intervento l'evento sarà: Non è stato possibile individuare un intervento per
     * la domanda.<br />
     * Se è stato individuato più di un intervento l'evento sarà: Trovati procedimenti proposti in più di un intervento
     * 
     * @param istanzeNlaHelper
     * 
     * 
     */
    private void insertEventoRicercaInterventi(IstanzeNlaHelper istanzeNlaHelper) {

	Istanzeeventi istanzeeventi = new Istanzeeventi();
	istanzeeventi.setIstanze(istanzeNlaHelper.getIstanze());
	istanzeeventi.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_STC_IP);
	istanzeeventi.setData(Calendar.getInstance().getTime());
	if (istanzeNlaHelper.getNumeroInterventi() == null || istanzeNlaHelper.getNumeroInterventi().intValue() == 0) {
	    istanzeeventi.setDescrizione("Non è stato possibile individuare un intervento per la domanda");
	    istanzeeventiService.insert(istanzeeventi);
	    log.warn("insertEventoRicercaInterventi(): Non è stato possibile individuare un intervento per l'istanza codice: {}",
		    EntityUtils.getNestedProperty(istanzeNlaHelper.getIstanze(), "id.codice"));
	} else if (istanzeNlaHelper.getNumeroInterventi().intValue() >= 2) {
	    istanzeeventi.setDescrizione("Trovati procedimenti proposti in più di un intervento");
	    istanzeeventiService.insert(istanzeeventi);
	    log.warn("insertEventoRicercaInterventi(): Trovati procedimenti proposti in più di un intervento per l'istanza codice: {}",
		    EntityUtils.getNestedProperty(istanzeNlaHelper.getIstanze(), "id.codice"));
	}
    }

    @Override
    public void inserimentoPraticaDaLocale(Domandestc domandestc, String token) {

	Istanze istanza = new Istanze();
	if (domandestc.getCodiceistanzaprenotata() != null) {
	    PkId id = new PkId(domandestc.getCodiceistanzaprenotata());
	    istanza.setId(id);
	}
	istanza.setNumeroistanza(domandestc.getNumeroistanza());
	Oggetti oggetti = oggettiService.findById(new PkId(domandestc.getOggetti().getId().getCodice()));
	String xmlString = "";
	try {
	    // verifica se è attiva la verticalizzazione stc , altrimenti rilancia un eccezione.
	    isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	    // creo la request a partire dall'xml
	    xmlString = new String(oggetti.getOggetto(), "UTF-8");
	    InputStream is = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
	    JAXBContext jc;
	    InserimentoPraticaNLARequest request = null;
	    jc = JAXBContext.newInstance(InserimentoPraticaNLARequest.class);
	    Unmarshaller u = jc.createUnmarshaller();
	    request = (InserimentoPraticaNLARequest) u.unmarshal(is);
	    boolean isInserimentoDiretto = false;
	    if (request.getDettaglioPratica().getAltriDati() != null) {
		for (ParametroType param : request.getDettaglioPratica().getAltriDati()) {
		    if ("$INSERIMENTO_DIRETTO$".equals(param.getNome())) {
			isInserimentoDiretto = true;
			break;
		    }
		}
	    }
	    boolean passaProt = passaProt(request.getSportelloMittente(), request.getSportelloDestinatario());
	    boolean isUpdateAnagrafe = isUpdateAnagrafePerNodo(request.getSportelloMittente());
	    AnagrafeBusinessRules anagrafeBusinessRules = new AnagrafeBusinessRules(true, isUpdateAnagrafe);
	    SigeproBusinessRules.setClassRules(AnagrafeBusinessRules.class, anagrafeBusinessRules);
	    OggettiBusinessRules oggettiBusinessRules = new OggettiBusinessRules();
	    oggettiBusinessRules.setInsert(true);
	    SigeproBusinessRules.setClassRules(OggettiBusinessRules.class, oggettiBusinessRules);
	    IstanzeBusinessRules istanzeBusinessRules = new IstanzeBusinessRules(true, true);
	    // in automatico durante l'inserimento da NLA non vengono generati i documenti da Albero e procedura
	    istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.generaAutomaticamenteDocumentiIstanza.name(), false);
	    istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserisciResponsabileProcedimentoseNonPresenteOperatore.name(),
		    true);
	    istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name(), true);
	    if (isInserimentoDiretto) {
		istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStcDiretto.name(), true);
	    }
	    boolean isInsertOneriDaEndoAutomaticamente = isInsertOneriDaEndoAutomaticamentePerNodo(request.getSportelloMittente(),
		    request.getSportelloDestinatario());
	    istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.generaAutomaticamenteOneriIstanza.name(),
		    isInsertOneriDaEndoAutomaticamente);
	    //	    //	
	    //	    // Se il nodo non aggiorna le anagrafiche allora setto la regola per l'aggiornamento anagrafica = false (si riflette in AnagrafeService)
	    //	    //
	    //	    if (!isUpdateAnagrafe) {
	    //		istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.aggiornaLeAnagrafiche.name(), false);
	    //	    }
	    SigeproBusinessRules.setClassRules(IstanzeBusinessRules.class, istanzeBusinessRules);
	    // Popolo l'oggetto istanza
	    boolean isNuovaLogicaSuaper = nlaHelperService.verificaNuovaLogicaSuaper(request);
	    IstanzeNlaHelper istanzeNlaHelper = nlaHelperService.populateIstanza(istanza, request, passaProt, isNuovaLogicaSuaper, true);
	    istanza = istanzeNlaHelper.getIstanze();
	    String numeroIstanzaPrenotato = domandestc.getNumeroistanza();
	    if (isNuovaLogicaSuaper && StringUtils.isNotBlank(istanzeNlaHelper.getNumeroistanzaprenotato())) {
		numeroIstanzaPrenotato = istanzeNlaHelper.getNumeroistanzaprenotato();
		log.debug("inserimento pratica isNuovaLogicaSuaper={} & numeroIstanzaPrenotato={}", isNuovaLogicaSuaper, numeroIstanzaPrenotato);
		istanza.setNumeroistanza(numeroIstanzaPrenotato);
		domandestc.setNumeroistanza(numeroIstanzaPrenotato);
	    }
	    // Inserisco l'oggetto istanza popolato
	    istanzeService.insert(istanza, TipoInserimento.PROTOCOLLAZIONE_PARAMETRI_INSERIMENTO_NORMALE,
		    new PostIstanzeCallBackNlaManagerImpl(this, request, istanzeeventiService));
	    // VALIDAZIONE DEGLI STRADARI ASSOCIATO ALL'ISTANZA TRAMITE SERVIZIO SIT
	    boolean isAttivoSit = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO);
	    if (isAttivoSit) {
		this.effettuaValidazioneIstanzeStardario(istanza);
	    }
	    ///////////////////////////////////////////////////////////////////////////
	    //Applicazione delle mappature configurate 
	    // Effetto le mappature se e solo se l'inserimento è andato a buon fine altrimenti l'operazione
	    // Inserimento dellla pratica inviata da people in SiGePro Avvenuto quindi andrò ad aggiornare i campi del record 
	    // DomandeStc inserito
	    //  3- FlagImport=true
	    // Se l'inserimento va bene dovrò aggiornare il flag importato=true
	    //String codiceIstanza = response.getDettaglioPratica().getIdPratica();
	    domandestc.setFlagImport(true);
	    domandestc.setIstanza(istanza);
	    domandestcService.update(domandestc);
	    // Inserisce nella tabella istanzaeventi un evento relativo al numero di interventi creati 
	    // alla creazione dell'istanza a partire dai dati inviati da people
	    //	1- Non ne ho trovato nessuno
	    //	2- Ne ho trovati più di 2
	    insertEventoRicercaInterventi(istanzeNlaHelper);
	    // Sono operazioni automatiche Es. notifiche ad altri servizi che vengono eseguite in base 
	    // a delle regole
	    operazioniAutomatiche(istanza, domandestc);
	} catch (Exception e) {
	    log.error("inserimentoPraticaDaLocale", e);
	    StringBuffer dettaglioErrore = new StringBuffer();
	    if (e instanceof BaseValidationException) {
		List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
		for (InvalidValue invalidValue : ivs) {
		    String messaggio = invalidValue.getPropertyName() + " " + invalidValue.getMessage();
		    dettaglioErrore.append(messaggio).append("\n");
		}
		if (e.getMessage() != null) {
		    dettaglioErrore.append(e.getMessage());
		}
	    } else {
		dettaglioErrore.append(e.getMessage());
	    }
	    domandestc.setUltimoerrore(dettaglioErrore.toString());
	    domandestcService.update(domandestc);
	    //
	    // questo inserimento deve settare le regole di business per le anagrafiche e gli oggetti
	    //
	    throw new RuntimeException(dettaglioErrore.toString(), e);
	} finally {
	    SigeproBusinessRules.buildDefaultRules();
	}
    }

    // Sono operazioni automatiche Es. notifiche ad altri servizi che vengono eseguite in base 
    // a delle regole
    private void operazioniAutomatiche(Istanze entity, Domandestc domandestc) {

	// L'operazione serve per notificare l'arrivo di una pratica ad un servizio esterno. L'operazione verrà svolta solo
	// se attiva la verticalizzazione SIT_LDP è attiva e la pratica proviene dalla stesso nodo configurato nel parametro NODI_SERVIZIO_DOMANDE
	log.debug("operazioniAutomatiche# Inizio....");
	try {
	    LDPWsClient.setNumeroPratica(entity, domandestc);
	    StatiistanzaId id = new StatiistanzaId(ORMHelper.getIdcomune(), ORMHelper.getSoftware(), entity.getChiusura().getId().getCodicestato());
	    Statiistanza si = statiistanzaService.findById(id);
	    LDPWsClient.setStatoOccupazione(entity.getId().getCodice(), si);
	    applicaQRCodeService.applicaQRCode(entity.getId().getCodice(), null, true);
	    try {
		log.debug("ricalcolo started...");
		RicalcoloMaxRequest req = new RicalcoloMaxRequest();
		List<String> ricalcoloAreeIdL = new ArrayList<String>();
		if (!StringUtils.isBlank(entity.getUuid())) {
		    List<RicalcoloAreeIstanze> testateId = ricalcoloAreeIstanzeDAO.findRicalcoloInProgressIst(entity.getUuid());
		    if (testateId != null && !testateId.isEmpty()) {
			for (RicalcoloAreeIstanze testataId : testateId) {
			    if (testataId != null) {
				ricalcoloAreeIdL.add(testataId.getIdRicalcoloAree());
			    }
			}
		    }
		} else {
		    log.debug("no uuid istanza populated");
		}
		req.setRicalcoloAreeIdL(ricalcoloAreeIdL);
		RicalcoloMaxReqParams params = new RicalcoloMaxReqParams();
		params.setToken(ORMHelper.getToken()); //Prendo questo
		params.setSoftware(ORMHelper.getSoftware());
		params.setType("singleistanza");
		new IstanzeStrRicalcoloRestClient().ricalcolaArea(req, params);
		log.debug("ricalcolo ended...");
	    } catch (Exception e) {
		log.error("Error in ricalcolo aree", e);
		try {
		    log.info("Inserting in event table");
		    istanzeeventiService.insert("Errore sul ricalcolo aree: " + e.getMessage(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null,
			    entity);
		    log.info("Insert executed");
		} catch (Exception e1) {
		    log.error("Error in insert eventi", e1); //va reso il meno bloccante possibile
		}
	    }
	} catch (OperazioniAutomaticheException e) {
	    istanzeeventiService.insert(e.getMessage(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, entity);
	}
	log.debug("operazioniAutomatiche# Fine....");
    }

    @Override
    public void gestioneApplicazioneMappatureSchedeDinamiche(Istanze istanza, InserimentoPraticaNLARequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("gestioneApplicazioneMappatureSchedeDinamiche# entro nel metodo");
	}
	boolean isNLABackoffice = nlaHelperService.checkSportello(request.getSportelloMittente(), NodoNLAEnum.NLA_IDNODO);
	boolean isCodiceOggettoAltroSistema = nlaHelperService.isCodiceOggettoAltroSistema(request.getSportelloMittente(),
		request.getSportelloDestinatario());
	// se il nodo mi passa dei riferimenti in codice scheda e codice campo allora elaboro anche le mappature come se fosse un nodo interno
	// se i riferimenti non sono numerici o non esistono nella base dati (non esiste il modello o il campo) allora il programma non li elabora
	if (log.isDebugEnabled()) {
	    log.debug("gestioneApplicazioneMappatureSchedeDinamiche# prima di gestioneApplicazioneMappatureSchedeDinamicheAreaRiservata");
	}
	this.gestioneApplicazioneMappatureSchedeDinamicheAreaRiservata(istanza, request, isCodiceOggettoAltroSistema);
	if (!(isNLABackoffice)) {
	    // in caso di nodi esterni e WebConstants.VERTICALIZZAZIONE_STC,WebConstants.VERTICALIZZAZIONE_STC_MAPPATURE_SCHEDE=1
	    // elaboro le mappature tabella mappature 
	    if (log.isDebugEnabled()) {
		log.debug("gestioneApplicazioneMappatureSchedeDinamiche# prima di gestioneApplicazioneMappatureSchedeDinamicheFrontend");
	    }
	    this.gestioneApplicazioneMappatureSchedeDinamicheFrontend(istanza, request, isCodiceOggettoAltroSistema);
	}
	if (log.isDebugEnabled()) {
	    log.debug("gestioneApplicazioneMappatureSchedeDinamiche# esco dal metodo");
	}
    }

    /**
     * <pre>
     * Applicazione delle mappature. 1. Per ogni scheda "SchedaType" 2. Per ogni campo "CampoSchedaType" a. verifica se
     * esiste una mappatura associata: record sulla tabella MAPPATURE con
     * NomeTagPeople=campoSchedaType.getCampoDinamico().getValoreUtente().getNome(). Sono possibili 0..N valori b.
     * Controllo se estiste almeno un mappatura b1.se la mappatura non esiste passa al campo successivo b2. se la
     * mappatura esiste b2.1 Controllo se sull'istanza esiste già la mappatura: Esiste record in istanzedyn2modellit con
     * codice di Dyn2Modellit uguale a FkIdScheda della record della tabella MAPPATURE in esame: b2.2.1 se lo trova
     * salta al sucessivo. b.2.2.2 se non lo trova lo inserice. c. Per ogni valore del campo dinamico
     * (campoDinamicoType.getValoreUtente().getValore() ) inserisco un recordo nella tabella ISTANZEDYN2DATI con: 1-
     * Istanza quella passata 2- Dyn2Campi quello di mappature.getDyn2Campi() 3- Codice 0 4- Molteplicità uguale
     * all'incrementatore i dell for dei valori (serve un numero unico) 5- Valore e Valoredecodificato scelto secondo la
     * regola presente sul record mappature: 0: valore= codice dell'i-esimo valoreParametroType 1: valore= Descrizione
     * di SchedaType 2: valore= Valoredecodifica di mappature se e solo se il valore del codice dell'i-esimo
     * valoreParametroType è uguale a Valoreconfronto di mappature
     * 
     * @param istanza
     * @param request
     * 
     *            <pre>
     * @param isNodoArConsole
     * @param domandestc
     */
    private void gestioneApplicazioneMappatureSchedeDinamicheFrontend(Istanze istanza, InserimentoPraticaNLARequest request,
	    boolean isCodiceOggettoAltroSistema) {

	log.debug("gestioneApplicazioneMappatureSchedeDinamicheFrontend");
	String nomeTagPeople = "";
	boolean mappaturaStcDaVerticalizzazione = this.verticalizzazioneStcMappaturaSchedeAttiva();
	addSchedeDaPDFCompilabili(request, istanza);
	//metodo per creazione scheda da file excel (vedi redmine 776)
	addSchedeDaFileExcel(request, istanza);
	List<SchedaType> schedaTypes = request.getDettaglioPratica().getSchede();
	for (SchedaType schedaType : schedaTypes) {
	    if (schedaType != null) {
		List<CampoSchedaType> campoSchedaTypes = schedaType.getCampi();
		for (CampoSchedaType campoSchedaType : campoSchedaTypes) {
		    // Setto il filtro della query
		    if (campoSchedaType != null && campoSchedaType.getCampoDinamico() != null
			    && StringUtils.isNotBlank(campoSchedaType.getCampoDinamico().getValoreUtente().getNome())) {
			nomeTagPeople = campoSchedaType.getCampoDinamico().getValoreUtente().getNome();
			//A.verifica se esiste una mappatura associata: record sulla tabella MAPPATURE 
			// Recupero tutto i record delle mappature presenti per il filtro passato
			log.debug("Verifico se esiste una mappatura associata per il nomeTagPeople {}", nomeTagPeople);
			List<Mappature> mappatures = mappatureService.findByNomeTagPeople(nomeTagPeople);
			//B.Controllo se estiste almeno un mappatura
			//B2. se la mappatura esiste
			if (!mappatures.isEmpty()) {
			    log.debug("Mappature trovate per il nomeTagPeople {}", nomeTagPeople);
			    Set<Integer> insertIstanzedyn2modellit = new HashSet<Integer>();
			    boolean istanzedyn2modellitPresente = false;
			    for (Mappature mappature : mappatures) {
				//B2.1 Controllo se sull'istanza esiste già la mappatura: 
				// vedo se per l'istanza in esame è già presente la scheda recuperata dalla mappatura
				Istanzedyn2modellitId id = new Istanzedyn2modellitId(istanza.getId().getCodice(),
					mappature.getDyn2Modellit().getId().getCodice());
				log.debug(
					"Vedo se per l'istanza in esame è già presente il modello dinamico {} la scheda recuperata dalla mappatura {}",
					mappature.getDyn2Modellit().getId(), mappature.getId().getCodice());
				Istanzedyn2modellit istanzedyn2modellit = istanzedyn2modellitService.findById(id);
				// Genera la lista dei modelli da aggiungere in istanzedyn2modellit
				// il modello va aggiunto se non è presente in istanzedyn2modellit e se nessuno dei modelli
				// collegati alla mappatura è presente (sempre in istanzedyn2modellit)
				if (!istanzedyn2modellitPresente && istanzedyn2modellit == null) {
				    if (mappaturaStcDaVerticalizzazione) {
					continue;
				    } else {
					insertIstanzedyn2modellit.add(mappature.getDyn2Modellit().getId().getCodice());
				    }
				} else {
				    istanzedyn2modellitPresente = true;
				    insertIstanzedyn2modellit.clear();
				}
				//C.Per ogni valore del campo dinamico (campoDinamicoType.getValoreUtente().getValore() ) inserisco un recordo nella
				// tabella ISTANZEDYN2DATI 
				log.debug("Inserimento del campo dinamico (ISTANZEDYN2DATI)");
				if (campoSchedaType.getCampoDinamico() != null) {
				    CampoDinamicoType campoDinamicoType = campoSchedaType.getCampoDinamico();
				    Dyn2Campi d2c = mappature.getDyn2Campi();
				    boolean isCampoUpload = dyn2CampiService.isCampoUpload(d2c.getId().getCodice());
				    if (campoDinamicoType.getValoreUtente().getValore() != null
					    && !campoDinamicoType.getValoreUtente().getValore().isEmpty()) {
					List<ElementoValoreCampoDinamicoType> valoreParametroTypes = campoDinamicoType.getValoreUtente().getValore();
					for (int i = 0; i < valoreParametroTypes.size(); i++) {
					    // Creo l'oggetto Istanzedyn2dati
					    log.debug("Creo l'oggetto Istanzedyn2dati");
					    Istanzedyn2dati istanzedyn2dati = new Istanzedyn2dati();
					    // Creo e setto l'id						    
					    int indice = 0;
					    int indiceMolteplicita = i;
					    if (valoreParametroTypes.get(i) != null) {
						if (valoreParametroTypes.get(i).getIndice() != null) {
						    indice = valoreParametroTypes.get(i).getIndice();
						}
						if (valoreParametroTypes.get(i).getIndiceMolteplicita() != null) {
						    indiceMolteplicita = valoreParametroTypes.get(i).getIndiceMolteplicita();
						}
					    }
					    Istanzedyn2datiId idId2d = new Istanzedyn2datiId(istanza.getId().getCodice(), d2c.getId().getCodice(),
						    indice, indiceMolteplicita);
					    istanzedyn2dati.setId(idId2d);
					    // Setto l'istanza in esame e il campo dinamico
					    istanzedyn2dati.setIstanza(istanza);
					    istanzedyn2dati.setDyn2Campi(d2c);
					    //Setto il valore in base alle regole di comportamento "TipoRegola" presente sulla record della tabella MAPPATURE
					    // in esame
					    log.debug(
						    "Setto il valore in base alle regole di comportamento (TipoRegola) presente sulla record della tabella MAPPATURE");
					    log.debug("Codice della regola applicata {}", mappature.getTiporegola());
					    if (mappature.getTiporegola().intValue() == 0) {
						this.applicaRegola0(isCampoUpload, valoreParametroTypes, i, istanza, request,
							isCodiceOggettoAltroSistema, nomeTagPeople, d2c, istanzedyn2dati);
					    } else if (mappature.getTiporegola().intValue() == 1) {
						this.applicaRegola1(campoSchedaType, istanzedyn2dati);
					    } else if (mappature.getTiporegola().intValue() == 2) {
						this.applicaRegola2(mappature, isCampoUpload, valoreParametroTypes, i, istanza, request,
							isCodiceOggettoAltroSistema, istanzedyn2dati);
					    } else if (mappature.getTiporegola().intValue() == 3) {
						this.applicaRegola3(mappature, isCampoUpload, valoreParametroTypes, i, istanza, request,
							isCodiceOggettoAltroSistema, istanzedyn2dati);
					    } else if (mappature.getTiporegola().intValue() == 4) {
						this.applicaRegola4(campoSchedaType, valoreParametroTypes, i, istanzedyn2dati);
					    }
					    if (!StringUtils.isBlank(istanzedyn2dati.getValore())) {
						log.debug("Prima di istanzedyn2datiService.insert(istanzedyn2dati)");
						istanzedyn2datiService.insert(istanzedyn2dati);
						log.debug("Dopo di istanzedyn2datiService.insert(istanzedyn2dati)");
					    }
					}
				    }
				}
				// Aggiungo a istanzedyn2modellit i modelli dinamici che non sono presenti nell'istanza
				for (Integer fkD2mtId : insertIstanzedyn2modellit) {
				    id = new Istanzedyn2modellitId(istanza.getId().getCodice(), fkD2mtId);
				    log.debug("Creo l'oggetto ISTANZEDYN2_MODELLIT da inserire");
				    //Inserisco un record nella tabella  ISTANZEDYN2_MODELLIT con:
				    //	1- Istanza quella passata
				    //	2- Dyn2Modellit quello recuperato dalla tabella delle MAPPATURE
				    //	3- Idcomune quello dell'ORMHelper
				    // Creo una nuova istanza di istanzedyn2modellit e gli setto l'id creato precedentemente
				    istanzedyn2modellit = new Istanzedyn2modellit();
				    istanzedyn2modellit.setId(id);
				    // Setto l'Istanza e Dyn2Modellit
				    istanzedyn2modellit.setIstanza(istanza);
				    log.debug("Ricerco il modello dinamico (Dyn2Modellit) per l'oggetto mappatura {}", fkD2mtId);
				    Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(new PkId(fkD2mtId));
				    if (dyn2Modellit == null) {
					log.error("gestioneApplicazioneMappatureSchedeDinamicheFrontend# modello dinamico non trovato. fkD2mtId={}",
						fkD2mtId);
				    }
				    istanzedyn2modellit.setDyn2Modellit(dyn2Modellit);
				    // Inserisco il record
				    log.debug("Prima di istanzedyn2modellitService.insert(istanzedyn2modellit)");
				    istanzedyn2modellitService.insert(istanzedyn2modellit);
				    log.debug("Dopo di istanzedyn2modellitService.insert(istanzedyn2modellit)");
				}
			    }
			} else {
			    //TODO DEVE NOTIFICARE (eventi) CHE LE MAPPATURE NON CI SONO
			    //recuperare la lista delle mappature mancanti ed inserire un solo evento con la lista
			}
		    }
		}
	    }
	}
    }

    private void applicaRegola0(boolean isCampoUpload, List<ElementoValoreCampoDinamicoType> valoreParametroTypes, int i, Istanze istanza,
	    InserimentoPraticaNLARequest request, boolean isCodiceOggettoAltroSistema, String nomeTagPeople, Dyn2Campi d2c,
	    Istanzedyn2dati istanzedyn2dati) {

	String valore = getValoreCampoUploadODefault(isCampoUpload, StringUtils.defaultString(valoreParametroTypes.get(i).getCodice()),
		istanza.getId().getCodice(), request, isCodiceOggettoAltroSistema);
	// controllo che il tipo dato sia data, nel caso devo effettuare 
	// delle regole di conversione
	String valoreDecodificato = valore;
	if (checkTipoDatoIsDate(d2c)) {
	    // se è uguale a 10 ci aspettiamo dd/MM/yyyy
	    CodiceDescrizioneBean cdb = gestisciDate(valore, nomeTagPeople);
	    valore = cdb.getCodice();
	    valoreDecodificato = cdb.getDescrizione();
	}
	if (checkTipoDatoIsNumericoDouble(d2c)) {
	    // se è uguale a 10.01 ci aspettiamo 10,01
	    CodiceDescrizioneBean cdb = gestisciNumericoDouble(valore, nomeTagPeople);
	    valore = cdb.getCodice();
	    valoreDecodificato = cdb.getDescrizione();
	}
	// setto la variabile ricavata trovato al campo "valore" e "valoredecodificato" di istanzedyn2dati
	istanzedyn2dati.setValore(valore);
	istanzedyn2dati.setValoredecodificato(valoreDecodificato);
    }

    private void applicaRegola1(CampoSchedaType campoSchedaType, Istanzedyn2dati istanzedyn2dati) {

	String valore = StringUtils.defaultString(campoSchedaType.getDescrizione());
	// setto la variabile ricavata trovato al campo "valore" e "valoredecodificato" di istanzedyn2dati
	istanzedyn2dati.setValore(valore);
	istanzedyn2dati.setValoredecodificato(valore);
    }

    private void applicaRegola2(Mappature mappature, boolean isCampoUpload, List<ElementoValoreCampoDinamicoType> valoreParametroTypes, int i,
	    Istanze istanza, InserimentoPraticaNLARequest request, boolean isCodiceOggettoAltroSistema, Istanzedyn2dati istanzedyn2dati) {

	// in questo caso uso il valore trovato per fare il confronto con 
	// il campo "ValoreConfronto" dell'oggetto mappature in esame.
	// Se valore è uguale a valoreConfronto di mappature allora uso come 
	// variabile da passare a istanzedyn2dati il valore del campo "ValoreDecodifica" di mappature 
	String valore = getValoreCampoUploadODefault(isCampoUpload, StringUtils.defaultString(valoreParametroTypes.get(i).getCodice()),
		istanza.getId().getCodice(), request, isCodiceOggettoAltroSistema);
	if (StringUtils.isNotBlank(mappature.getValoreconfronto()) && StringUtils.isNotBlank(mappature.getValoredecodifica())
		&& valore.equalsIgnoreCase(mappature.getValoreconfronto())) {
	    istanzedyn2dati.setValore(mappature.getValoredecodifica());
	    istanzedyn2dati.setValoredecodificato(mappature.getValoredecodifica());
	}
    }

    private void applicaRegola3(Mappature mappature, boolean isCampoUpload, List<ElementoValoreCampoDinamicoType> valoreParametroTypes, int i,
	    Istanze istanza, InserimentoPraticaNLARequest request, boolean isCodiceOggettoAltroSistema, Istanzedyn2dati istanzedyn2dati) {

	// in questo caso uso il valore trovato per fare il match la regex expression presente  
	// nel campo "ValoreConfronto" dell'oggetto mappature in esame.
	// Se il risultato  è true
	// 1- true: allora converto il valore con il valoreDecodifica
	// 2- false: lascio vuoto
	// variabile da passare a istanzedyn2dati il valore del campo "ValoreDecodifica" di mappature 
	String valore = getValoreCampoUploadODefault(isCampoUpload, StringUtils.defaultString(valoreParametroTypes.get(i).getCodice()),
		istanza.getId().getCodice(), request, isCodiceOggettoAltroSistema);
	if (StringUtils.isNotBlank(mappature.getValoreconfronto()) && StringUtils.isNotBlank(mappature.getValoredecodifica())) {
	    Pattern datePattern = Pattern.compile(mappature.getValoreconfronto().trim());
	    boolean isMatch = Pattern.matches(mappature.getValoreconfronto().trim(), valore.trim());
	    if (isMatch) {
		Matcher dateMatcher = datePattern.matcher(valore.trim());
		StringBuffer sb = new StringBuffer();
		while (dateMatcher.find()) {
		    dateMatcher.appendReplacement(sb, mappature.getValoredecodifica().trim());
		}
		dateMatcher.appendTail(sb);
		istanzedyn2dati.setValore(sb.toString());
		istanzedyn2dati.setValoredecodificato(sb.toString());
	    }
	}
    }

    private void applicaRegola4(CampoSchedaType campoSchedaType, List<ElementoValoreCampoDinamicoType> valoreParametroTypes, int i,
	    Istanzedyn2dati istanzedyn2dati) {

	String valore = StringUtils.defaultString(valoreParametroTypes.get(i).getCodice());
	String valoreDecodificato = StringUtils.defaultString(valoreParametroTypes.get(i).getDescrizione());
	// setto la variabile ricavata trovato al campo "valore" e "valoredecodificato" di istanzedyn2dati
	istanzedyn2dati.setValore(valore);
	istanzedyn2dati.setValoredecodificato(valoreDecodificato);
    }

    private boolean verticalizzazioneStcMappaturaSchedeAttiva() {

	Verticalizzazioniparametri vpms = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		WebConstants.VERTICALIZZAZIONE_STC_MAPPATURE_SCHEDE);
	return (vpms != null && StringUtils.defaultIfEmpty(vpms.getValore(), "0").equalsIgnoreCase("1"));
    }

    private String getValoreCampoUploadODefault(boolean isCampoUpload, String defaultValore, Integer codiceIstanza,
	    InserimentoPraticaNLARequest request, boolean isCodiceOggettoAltroSistema) {

	if (isCampoUpload && isCodiceOggettoAltroSistema) {
	    Integer codiceOggetto = documentiistanzaService.findOggettoArConsole(riferimentoArConsole(request, defaultValore), codiceIstanza);
	    return String.valueOf(codiceOggetto);
	} else {
	    return defaultValore;
	}
    }

    private void addSchedeDaPDFCompilabili(InserimentoPraticaNLARequest request, Istanze istanza) {

	boolean isATTIVA_LETTURA_SCHEDE_DA_PDF = false;
	Verticalizzazioniparametri vpms = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		WebConstants.VERTICALIZZAZIONE_STC_ATTIVA_LETTURA_SCHEDE_DA_PDF);
	if (vpms != null) {
	    if (StringUtils.defaultIfEmpty(vpms.getValore(), "N").equalsIgnoreCase("S")) {
		isATTIVA_LETTURA_SCHEDE_DA_PDF = true;
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug("addSchedeDaPDFCompilabili# la lettura dei dati dal PDF è attiva? {}", isATTIVA_LETTURA_SCHEDE_DA_PDF);
	}
	if (isATTIVA_LETTURA_SCHEDE_DA_PDF) {
	    if (log.isDebugEnabled()) {
		log.debug(
			"addSchedeDaPDFCompilabili# cerco di recuperare le schede compilabili dai file pdf. Recupero gli allegati della domanda STC");
	    }
	    List<DocumentiType> docs = nlaHelperService.getDocsPerPratica(request.getDettaglioPratica());
	    if (docs.size() > 0) {
		boolean isNodoInterno = nlaHelperService.isChiamataDaNodoInterno(request.getSportelloDestinatario(), request.getSportelloMittente(),
			false);
		Set<Integer> codiciOggettoDaInviare = new TreeSet<Integer>();
		for (DocumentiType doc : docs) {
		    Integer codiceOggetto = null;
		    if (doc != null) {
			if (doc.getAllegati() != null) {
			    if (isNodoInterno) {
				// in caso di nodi interni vado direttamente al codiceoggetto
				if (doc.getAllegati().getId() != null) {
				    if (Utilities.isInteger(doc.getAllegati().getId())) {
					codiceOggetto = Integer.parseInt(doc.getAllegati().getId());
				    }
				}
			    } else {
				// nodi non interni
				// se passato attachment allora la procedura ha modificato i riferimenti mettendo il prefisso CODICEOGGETTO:
				if (doc.getAllegati().getId() != null) {
				    String id = doc.getAllegati().getId();
				    if (StringUtils.isNotBlank(id)) {
					if (id.startsWith(NlaHelperService.CODICEOGGETTO_CONST_ALLEGATI)) {
					    if (id.indexOf(":") > 0) {
						String codiceOggettoStr = id.substring(id.indexOf(":") + 1);
						if (StringUtils.isNotBlank(codiceOggettoStr)) {
						    if (Utilities.isInteger(codiceOggettoStr)) {
							codiceOggetto = Integer.parseInt(codiceOggettoStr.trim());
						    } else {
							log.error(
								"Il codiceoggetto tornato dal riferimento allegato non è numerico. RIF(id={}, CO={})",
								id, codiceOggettoStr);
							throw new RuntimeException(
								"Il codiceoggetto tornato dal riferimento allegato non è numerico. RIF(id=" + id +
										   ", CO=" + codiceOggettoStr + ")");
						    }
						}
					    }
					}
				    }
				}
			    }
			}
		    }
		    if (codiceOggetto != null) {
			Oggetti o = oggettiService.findByIdLazy(new PkId(codiceOggetto));
			if (o != null) {
			    String fileName = o.getNomefile();
			    if (log.isDebugEnabled()) {
				log.debug("addSchedeDaPDFCompilabili# verifico se il file {} è un PDF", fileName);
			    }
			    if (StringUtils.isNotBlank(fileName)) {
				if (StringUtils.defaultString(fileName).toLowerCase().contains(".pdf")) {
				    if (log.isDebugEnabled()) {
					log.debug("addSchedeDaPDFCompilabili# il file {} è un PDF", fileName);
				    }
				    codiciOggettoDaInviare.add(codiceOggetto);
				}
			    }
			}
		    }
		}
		if (log.isDebugEnabled()) {
		    log.debug("addSchedeDaPDFCompilabili# lista dei file dai quali recuperare le informazioni {}", codiciOggettoDaInviare);
		}
		if (codiciOggettoDaInviare.size() > 0) {
		    PdfUtilsWSClient pdfu = new PdfUtilsWSClient();
		    for (Integer co : codiciOggettoDaInviare) {
			if (log.isDebugEnabled()) {
			    log.debug("addSchedeDaPDFCompilabili# recupero l'oggetto con id {}", co);
			}
			Oggetti o = oggettiService.findById(new PkId(co));
			if (o != null) {
			    String fileName = StringUtils.defaultString(o.getNomefile(), System.currentTimeMillis() + ".pdf");
			    if (log.isDebugEnabled()) {
				log.debug("addSchedeDaPDFCompilabili# elaboro l'allegato {}({})", fileName, o.getId());
			    }
			    // String messaggio = "Errore nel recupero dei dati dal File " + fileName;
			    DataHandler content = Utilities.bytesToDataHandler(o.getOggetto());
			    try {
				SchedaType s = pdfu.leggiDatiDaPDF(fileName, co.toString(), content);
				if (log.isDebugEnabled()) {
				    log.debug("addSchedeDaPDFCompilabili# dati recuperati dall'allegato {},({})", fileName, o.getId());
				}
				if (s != null) {
				    if (log.isDebugEnabled()) {
					log.debug("addSchedeDaPDFCompilabili# la scheda non è vuota l'associo alla request");
				    }
				    request.getDettaglioPratica().getSchede().add(s);
				}
			    } catch (InvalidConfigurationException e) {
				// messaggio += ". Configurazione non valida: " + e.getMessage();
				log.error("addSchedeDaPDFCompilabili# Eccezione di tipo InvalidConfigurationException: {}", e);
				// insertEvento(istanza, messaggio);
			    } catch (FunzioneBusinessRemotaException e) {
				// messaggio += ". Errore tornato dal servizio pdfutils: " + e.getMessage();
				log.error("addSchedeDaPDFCompilabili# Eccezione di tipo FunzioneBusinessRemotaException: {}", e);
				// insertEventoRecuperoDatiPDFCompilabile(istanza, messaggio);
			    } catch (Exception e) {
				log.error("addSchedeDaPDFCompilabili# errore imprevisto: {}", e);
				// messaggio += ". Errore imprevisto: " + e.getCause() + "-->" + e.getMessage();
				// insertEvento(istanza, messaggio);
			    }
			}
		    }
		}
	    }
	}
    }

    /**
     * questo metodo converte gli allegati di tipo excel in schede dinamiche
     * 
     * 1. verifico che siano presenti delle mappaure con prefisso #XLS#<br>
     * 2. recupero tutti gli allegati della pratica stc con estensione xls (se firmato digitalmente recupero il file
     * originale interrogando il dss)<br>
     * 3. converto il file nella corrispondente scheda dinamica
     * 
     * @param request
     * @param istanza
     */
    private void addSchedeDaFileExcel(InserimentoPraticaNLARequest request, Istanze istanza) {

	if (log.isDebugEnabled()) {
	    log.debug("addSchedeDaFileExcel");
	}
	List<String> mappaturePerFileExcel = this.getMappaturePerFileExcel();
	if (!mappaturePerFileExcel.isEmpty()) {
	    List<DocumentiType> documentiPraticaSTC = nlaHelperService.getDocsPerPratica(request.getDettaglioPratica());
	    if (documentiPraticaSTC.size() > 0) {
		boolean isNodoInterno = nlaHelperService.isChiamataDaNodoInterno(request.getSportelloDestinatario(), request.getSportelloMittente(),
			false);
		Set<Integer> listaCodiciOggetto = new TreeSet<Integer>();
		for (DocumentiType doc : documentiPraticaSTC) {
		    Integer codiceOggetto = null;
		    if (doc != null) {
			if (doc.getAllegati() != null) {
			    if (isNodoInterno) {
				// in caso di nodi interni vado direttamente al codiceoggetto
				if (doc.getAllegati().getId() != null) {
				    if (Utilities.isInteger(doc.getAllegati().getId())) {
					codiceOggetto = Integer.parseInt(doc.getAllegati().getId());
				    }
				}
			    } else {
				// nodi non interni
				// se passato attachment allora la procedura ha modificato i riferimenti mettendo il prefisso CODICEOGGETTO:
				if (doc.getAllegati().getId() != null) {
				    String id = doc.getAllegati().getId();
				    if (StringUtils.isNotBlank(id)) {
					if (id.startsWith(NlaHelperService.CODICEOGGETTO_CONST_ALLEGATI)) {
					    if (id.indexOf(":") > 0) {
						String codiceOggettoStr = id.substring(id.indexOf(":") + 1);
						if (StringUtils.isNotBlank(codiceOggettoStr)) {
						    if (Utilities.isInteger(codiceOggettoStr)) {
							codiceOggetto = Integer.parseInt(codiceOggettoStr.trim());
						    } else {
							log.error(
								"addSchedeDaFileExcel: codiceoggetto non numerico. (documentotype.allegatotype.id={}, cod.ogg.={})",
								id, codiceOggettoStr);
							throw new RuntimeException(
								"[addSchedeDaFileExcel] Errore durante la verifica del codiceoggetto=" +
										   codiceOggettoStr + " per l'allegato con id=" + id);
						    }
						}
					    }
					}
				    }
				}
			    }
			}
		    }
		    if (codiceOggetto != null) {
			Oggetti o = oggettiService.findByIdLazy(new PkId(codiceOggetto));
			if (o != null) {
			    String fileName = o.getNomefile();
			    if (log.isDebugEnabled()) {
				log.debug("addSchedeDaFileExcel# verifico se il file {} ha estensione xls", fileName);
			    }
			    if (StringUtils.isNotBlank(fileName)) {
				if (StringUtils.defaultString(fileName).toLowerCase().contains(".xls")) {
				    if (log.isDebugEnabled()) {
					log.debug("addSchedeDaFileExcel# il file {} ha estensione xls", fileName);
				    }
				    listaCodiciOggetto.add(codiceOggetto);
				}
			    }
			}
		    }
		}
		if (listaCodiciOggetto.size() > 0) {
		    for (Integer co : listaCodiciOggetto) {
			if (log.isDebugEnabled()) {
			    log.debug("addSchedeDaFileExcel# recupero l'oggetto con codiceoggetto={}", co);
			}
			Oggetti o = oggettiService.findById(new PkId(co));
			if (o != null) {
			    try {
				DataHandler dh = Utilities.bytesToDataHandler(o.getOggetto());
				//se firmato digitalmente estraggo il contenuto
				if (o.getNomefile().toLowerCase().contains((".p7m"))) {				    
				    FileOriginaleDSSBean bean = new DSSRestClient().checkFirmaScaricaFileNonFirmato(dh, o.getNomefile());
				    dh = Utilities.bytesToDataHandler(bean.getContent());
				}
				//parsing file excel
				FileExcelParser fileExcelParser = new FileExcelParser(dh);
				fileExcelParser.populateSchede(request.getDettaglioPratica().getSchede(), mappaturePerFileExcel);
			    } catch (Exception e) {
				log.error("addSchedeDaFileExcel", e);
				String messaggio = "Errore durante la creazione della scheda dinamica relativa al file excel " + o.getNomefile() +
						   ": " + e.getMessage();
				insertEvento(istanza, messaggio);
			    }
			}
		    }
		}
	    }
	}
    }

    private List<String> getMappaturePerFileExcel() {

	return mappatureService.findByNometagpeopleDistinct("#XLS#");
    }

    /**
     * 
     * @param istanze
     * @param messaggio
     */
    private void insertEvento(Istanze istanze, String messaggio) {

	Istanzeeventi istanzeeventi = new Istanzeeventi();
	istanzeeventi.setIstanze(istanze);
	istanzeeventi.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_STC_IP);
	istanzeeventi.setData(Calendar.getInstance().getTime());
	istanzeeventi.setDescrizione(messaggio);
	istanzeeventiService.insert(istanzeeventi);
    }

    private boolean checkTipoDatoIsDate(Dyn2Campi d2c) {

	boolean result = false;
	Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(d2c.getId().getCodice()));
	if (dyn2Campi.getTipodato().equalsIgnoreCase("Data")) {
	    return true;
	}
	return result;
    }

    /**
     * <pre>
     * Applicazione delle mappature. 1. Per ogni scheda "SchedaType"
     * 
     * a. Controllo se estiste almeno un record su Istanzedyn2modellit b1.se esiste passo alla scheda successiva b2.
     * altrimento creo un oggetto Istanzedyn2modellit e lo inserisco per l'istanza che ho creato b. Per CampoSchedaType
     * di SchedaType inserisco un oggetto Istanzedyn2dati asscociato all'istanza che ho creato
     * 
     * 
     * @param istanza
     * @param request
     * 
     *            <pre>
     * @param isNodoArConsole
     */
    private void gestioneApplicazioneMappatureSchedeDinamicheAreaRiservata(Istanze istanza, InserimentoPraticaNLARequest request,
	    boolean isCodiceOggettoAltroSistema) {

	List<SchedaType> schedaTypes = request.getDettaglioPratica().getSchede();
	if (schedaTypes != null && !schedaTypes.isEmpty()) {
	    for (SchedaType schedaType : schedaTypes) {
		if (StringUtils.isNotBlank(schedaType.getCodice())) {
		    String codiceScheda = schedaType.getCodice();
		    log.debug("Verifico che l'istanza abbia il modello dinamico associato con codice scheda = {}", codiceScheda);
		    Integer codiceModello = null;
		    if (Utilities.isInteger(codiceScheda) && // 
			    !codiceScheda.startsWith("0")) { // Ticket#2022092110000072 
							     // non posso filtrare per nodo interno/areariservata/stesso nodo alias differente
							     // anche perché ci sono nodi tipo NLA_SUAP_INRETE che inviano gli identificativi
							     // Accesso unitario invia le schede con l'identificativo tipo 000000151 ed escluderei questi
			try {
			    codiceModello = Integer.parseInt(codiceScheda);
			} catch (Exception e) {
			    log.error("gestioneApplicazioneMappatureSchedeDinamicheAreaRiservata# il codiceModello {} non è un numero.",
				    codiceModello);
			}
		    }
		    if (codiceModello != null) {
			Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(new PkId(codiceModello));
			if (dyn2Modellit != null) {
			    // Cerco la scheda se è gia collegata all'istanza
			    Istanzedyn2modellitId id = new Istanzedyn2modellitId(istanza.getId().getCodice(), codiceModello);
			    Istanzedyn2modellit istanzedyn2modellit = istanzedyn2modellitService.findById(id);
			    // Se non è presente la inserisco, altrimenti continuo il ciclo di ricerca
			    if (istanzedyn2modellit == null) {
				log.debug("Il modello dinamico non è associato, lo vado ad inserire");
				//Inserisco un record nella tabella  ISTANZEDYN2_MODELLIT con:
				//	1- Istanza quella passata
				//	2- Dyn2Modellit quello recuperato dalla tabella della request
				//	3- Idcomune quello dell'ORMHelper
				// Creo una nuova istanza di istanzedyn2modellit e gli setto l'id creato precedentemente
				istanzedyn2modellit = new Istanzedyn2modellit();
				istanzedyn2modellit.setId(id);
				// Setto l'Istanza e Dyn2Modellit
				istanzedyn2modellit.setIstanza(istanza);
				istanzedyn2modellit.setDyn2Modellit(dyn2Modellit);
				// Inserisco il record
				istanzedyn2modellitService.insert(istanzedyn2modellit);
			    }
			    if (schedaType.getCampi() != null && !schedaType.getCampi().isEmpty()) {
				List<CampoSchedaType> campoSchedaTypes = schedaType.getCampi();
				for (CampoSchedaType campoSchedaType : campoSchedaTypes) {
				    if (campoSchedaType != null) {
					String codiceCampo = StringUtils.defaultString(campoSchedaType.getCodice(), "").trim();
					if (!codiceCampo.equals("-1")) {
					    Integer codiceCampoInt = null;
					    try {
						codiceCampoInt = Integer.parseInt(codiceCampo);
					    } catch (Exception e) {
						log.error(
							"gestioneApplicazioneMappatureSchedeDinamicheAreaRiservata# il campo con codice {} non è valido.",
							codiceCampo);
					    }
					    if (codiceCampoInt != null) {
						CampoDinamicoType campoDinamicoType = campoSchedaType.getCampoDinamico();
						if (campoDinamicoType != null) {
						    Dyn2Campi d2c = dyn2CampiService.findById(new PkId(codiceCampoInt));
						    if (d2c != null) {
							boolean isCampoUpload = dyn2CampiService.isCampoUpload(d2c.getId().getCodice());
							List<ElementoValoreCampoDinamicoType> valoreParametroTypes = campoDinamicoType
								.getValoreUtente().getValore();
							for (int i = 0; i < valoreParametroTypes.size(); i++) {
							    // Creo l'oggetto Istanzedyn2dati
							    Istanzedyn2dati istanzedyn2dati = new Istanzedyn2dati();
							    // Creo e setto l'id
							    int indice = 0;
							    int indiceMolteplicita = i;
							    if (valoreParametroTypes.get(i) != null) {
								if (valoreParametroTypes.get(i).getIndice() != null) {
								    indice = valoreParametroTypes.get(i).getIndice();
								}
								if (valoreParametroTypes.get(i).getIndiceMolteplicita() != null) {
								    indiceMolteplicita = valoreParametroTypes.get(i).getIndiceMolteplicita();
								}
							    }
							    Istanzedyn2datiId idId2d = new Istanzedyn2datiId(istanza.getId().getCodice(),
								    codiceCampoInt, indice, indiceMolteplicita);
							    istanzedyn2dati.setId(idId2d);
							    // Setto l'istanza in esame e il campo dinamico
							    istanzedyn2dati.setIstanza(istanza);
							    istanzedyn2dati.setDyn2Campi(d2c);
							    if (isCampoUpload && isCodiceOggettoAltroSistema) {
								String valoreOggetto = valoreParametroTypes.get(i).getCodice();
								Integer codiceOggetto = documentiistanzaService.findOggettoArConsole(
									riferimentoArConsole(request, valoreOggetto), istanza.getId().getCodice());
								istanzedyn2dati.setValore(String.valueOf(codiceOggetto));
								istanzedyn2dati.setValoredecodificato(String.valueOf(codiceOggetto));
							    } else {
								istanzedyn2dati.setValore(valoreParametroTypes.get(i).getCodice());
								istanzedyn2dati.setValoredecodificato(valoreParametroTypes.get(i).getDescrizione());
							    }
							    istanzedyn2datiService.insert(istanzedyn2dati);
							}
						    } else {
							log.error(
								"gestioneApplicazioneMappatureSchedeDinamicheAreaRiservata# il codice campo {} non esiste nella base dati",
								codiceCampo);
						    }
						}
					    }
					}
				    }
				}
			    }
			} else {
			    log.error("gestioneApplicazioneMappatureSchedeDinamicheAreaRiservata# il codice modello {} non esiste nella base dati",
				    codiceModello);
			}
		    }
		}
	    }
	}
    }

    private String riferimentoArConsole(InserimentoPraticaNLARequest request, String valoreOggetto) {

	return NlaHelperService.CODICEOGGETTO_CONST_ALLEGATI_CODICE_ALTRO_SISTEMA + request.getSportelloMittente().getIdEnte() + "@" + valoreOggetto;
    }

    /**
     * se la vert non è attiva rilancia una RuntimeException
     * 
     * @param modulo
     * @return
     */
    private boolean isVerticalizzazioneAttiva(String modulo) {

	if (!verticalizzazioniService.isAttiva(modulo)) {
	    throw new RuntimeException("Attenzione: Non è configurata la verticalizzazione STC");
	}
	return true;
    }

    @Override
    public void downloadAllegatiSTCIstanza(Integer codiceIstanza, SportelloType sportelloDestinatario, String idPraticaDestinatario) {

	String token = stcWsClient.login();
	SportelloType mitt = nlaService.getSportelloMittente();
	List<DocumentiistanzaDTO> docs = documentiistanzaService.findDocumentiistanzaDTOByIstanza(codiceIstanza, false);
	for (DocumentiistanzaDTO doc : docs) {
	    if (doc.getCodiceOggetto() == null) {
		if (StringUtils.isNotBlank(doc.getStcIddocumento()) && StringUtils.isNotBlank(doc.getStcIdallegato())) {
		    AllegatoBinarioRequest request = new AllegatoBinarioRequest();
		    request.setToken(token);
		    request.setSportelloMittente(mitt);
		    request.setSportelloDestinatario(sportelloDestinatario);
		    RiferimentiAllegatoType rif = new RiferimentiAllegatoType();
		    rif.setIdAllegato(doc.getStcIdallegato());
		    rif.setIdDocumento(doc.getStcIdallegato());
		    rif.setIdPratica(idPraticaDestinatario);
		    request.setRiferimentiAllegato(rif);
		    AllegatoBinarioResponse response = null;
		    try {
			response = stcWsClient.allegatoBinario(request);
		    } catch (Exception e) {
			log.error("downloadAllegatiSTCIstanza# errore nel recupero dell'allegato STC {}-{} dell'istanza {}: {}",
				new Object[] { doc.getStcIddocumento(), doc.getStcIdallegato(), codiceIstanza, e });
			// TODO INSERISCI EVENTO
		    }
		    if (response != null) {
			try {
			    DataHandler dh = response.getBinaryData();
			    if (dh != null) {
				Oggetti ogg = new Oggetti();
				Documentiistanza doci = documentiistanzaService.findById(new PkId(doc.getId().getCodice()));
				if (doci != null) {
				    ogg.setNomefile(response.getFileName());
				    ogg.setOggetto(Utilities.dataHandlerToBytes(dh));
				    oggettiService.insert(ogg);
				    doci.setOggetto(ogg);
				    documentiistanzaService.update(doci);
				}
			    }
			} catch (Exception e) {
			    log.error("downloadAllegatiSTCIstanza# errore nel recupero dell'allegato STC {}-{} dell'istanza {}: {}",
				    new Object[] { doc.getStcIddocumento(), doc.getStcIdallegato(), codiceIstanza, e });
			    // TODO INSERISCI EVENTO
			}
		    }
		}
	    }
	}
	List<IstanzeallegatiDTO> allegatis = istanzeallegatiService.findIstanzeallegatiDTOByIstanza(codiceIstanza);
	for (IstanzeallegatiDTO doc : allegatis) {
	    if (doc.getCodiceOggetto() == null) {
		if (StringUtils.isNotBlank(doc.getStcIddocumento()) && StringUtils.isNotBlank(doc.getStcIdallegato())) {
		    AllegatoBinarioRequest request = new AllegatoBinarioRequest();
		    request.setToken(token);
		    request.setSportelloMittente(mitt);
		    request.setSportelloDestinatario(sportelloDestinatario);
		    RiferimentiAllegatoType rif = new RiferimentiAllegatoType();
		    rif.setIdAllegato(doc.getStcIdallegato());
		    rif.setIdDocumento(doc.getStcIdallegato());
		    rif.setIdPratica(idPraticaDestinatario);
		    request.setRiferimentiAllegato(rif);
		    AllegatoBinarioResponse response = null;
		    try {
			response = stcWsClient.allegatoBinario(request);
		    } catch (Exception e) {
			log.error("downloadAllegatiSTCIstanza# errore nel recupero dell'allegato STC {}-{} dell'istanza {}: {}",
				new Object[] { doc.getStcIddocumento(), doc.getStcIdallegato(), codiceIstanza, e });
			// TODO INSERISCI EVENTO
		    }
		    if (response != null) {
			try {
			    DataHandler dh = response.getBinaryData();
			    if (dh != null) {
				Istanzeallegati doci = istanzeallegatiService.findById(new PkId(doc.getId().getCodice()));
				if (doci != null) {
				    Oggetti ogg = new Oggetti();
				    ogg.setNomefile(response.getFileName());
				    ogg.setOggetto(Utilities.dataHandlerToBytes(dh));
				    oggettiService.insert(ogg);
				    doci.setOggetto(ogg);
				    istanzeallegatiService.update(doci);
				}
			    }
			} catch (Exception e) {
			    log.error("downloadAllegatiSTCIstanza# errore nel recupero dell'allegato STC {}-{} dell'istanza {}: {}",
				    new Object[] { doc.getStcIddocumento(), doc.getStcIdallegato(), codiceIstanza, e });
			    // TODO INSERISCI EVENTO
			}
		    }
		}
	    }
	}
    }

    @Override
    public void downloadAllegatiSTCMovimento(Integer codiceMovimento, SportelloType sportelloDestinatario, String idPraticaDestinatario,
	    String idAttivitaDestinatario) {

	String token = stcWsClient.login();
	SportelloType mitt = nlaService.getSportelloMittente();
	List<MovimentiallegatiDTO> allegatis = movimentiallegatiService.findMovimentiallegatiDTOByMovimenti(codiceMovimento);
	for (MovimentiallegatiDTO doc : allegatis) {
	    if (doc.getCodiceOggetto() == null) {
		if (StringUtils.isNotBlank(doc.getStcIddocumento()) && StringUtils.isNotBlank(doc.getStcIdallegato())) {
		    AllegatoBinarioRequest request = new AllegatoBinarioRequest();
		    request.setToken(token);
		    request.setSportelloMittente(mitt);
		    request.setSportelloDestinatario(sportelloDestinatario);
		    RiferimentiAllegatoType rif = new RiferimentiAllegatoType();
		    rif.setIdAllegato(doc.getStcIdallegato());
		    rif.setIdDocumento(doc.getStcIdallegato());
		    rif.setIdPratica(idPraticaDestinatario);
		    if (StringUtils.isNotBlank(idAttivitaDestinatario)) {
			rif.setIdAttivita(idAttivitaDestinatario);
		    }
		    request.setRiferimentiAllegato(rif);
		    AllegatoBinarioResponse response = null;
		    try {
			response = stcWsClient.allegatoBinario(request);
		    } catch (Exception e) {
			log.error("downloadAllegatiSTCMovimento# errore nel recupero dell'allegato STC {}-{} del movimento {}-{}: {}",
				new Object[] { doc.getStcIddocumento(), doc.getStcIdallegato(), codiceMovimento, ORMHelper.getIdcomune(), e });
			// TODO INSERISCI EVENTO
		    }
		    if (response != null) {
			try {
			    DataHandler dh = response.getBinaryData();
			    if (dh != null) {
				Movimentiallegati doci = movimentiallegatiService.findById(new PkId(doc.getId().getCodice()));
				if (doci != null) {
				    Oggetti ogg = new Oggetti();
				    ogg.setNomefile(response.getFileName());
				    ogg.setOggetto(Utilities.dataHandlerToBytes(dh));
				    oggettiService.insert(ogg);
				    doci.setOggetto(ogg);
				    movimentiallegatiService.update(doci);
				}
			    }
			} catch (Exception e) {
			    log.error("downloadAllegatiSTCMovimento# errore nel recupero dell'allegato STC {}-{} del movimento {}-{}: {}",
				    new Object[] { doc.getStcIddocumento(), doc.getStcIdallegato(), codiceMovimento, ORMHelper.getIdcomune(), e });
			    // TODO INSERISCI EVENTO
			}
		    }
		}
	    }
	}
    }

    private final static String dateFormatPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private final static String regexDate = "^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{0,3})Z$";

    private static CodiceDescrizioneBean gestisciDate(String valore, String nomeTagPeople) {

	CodiceDescrizioneBean cdb = new CodiceDescrizioneBean(valore, valore);
	if (valore.length() == 10) { // ci aspettiamo il formato dd/MM/yyyy
	    SimpleDateFormat formatDD_MM_YYYY = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    SimpleDateFormat formatYYYY_MM_DD = new SimpleDateFormat("yyyyMMdd");
	    Date date = null;
	    try {
		date = formatDD_MM_YYYY.parse(valore);
		String valoreyyyyMMdd = formatYYYY_MM_DD.format(date);
		cdb.setCodice(valoreyyyyMMdd);
		cdb.setDescrizione(valore);
	    } catch (ParseException e) {
		log.error("Formato data {} non corretto della scheda con nomeTagPeople  {}", new Object[] { valore, nomeTagPeople, e });
	    }
	} else if (valore.matches(regexDate)) {
	    SimpleDateFormat formatDD_MM_YYYY = new SimpleDateFormat(dateFormatPattern);
	    SimpleDateFormat formatYYYY_MM_DD = new SimpleDateFormat("yyyyMMdd");
	    Date date = null;
	    try {
		date = formatDD_MM_YYYY.parse(valore);
		String valoreyyyyMMdd = formatYYYY_MM_DD.format(date);
		cdb.setCodice(valoreyyyyMMdd);
		cdb.setDescrizione(new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN).format(date));
	    } catch (ParseException e) {
		log.error("Formato data {} non corretto della scheda con nomeTagPeople  {}", new Object[] { valore, nomeTagPeople, e });
	    }
	}
	return cdb;
    }

    private CodiceDescrizioneBean gestisciNumericoDouble(String valore, String nomeTagPeople) {

	CodiceDescrizioneBean cdb = new CodiceDescrizioneBean(valore, valore);
	if (StringUtils.defaultString(valore).indexOf(".") >= 0) {
	    // nel db il valore numericodouble viene salvato con la ,
	    String nuovoValore = valore.replace(".", ",");
	    cdb.setCodice(nuovoValore);
	    cdb.setDescrizione(nuovoValore);
	}
	return cdb;
    }

    private boolean checkTipoDatoIsNumericoDouble(Dyn2Campi d2c) {

	boolean result = false;
	Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(d2c.getId().getCodice()));
	if (dyn2Campi.getTipodato().equalsIgnoreCase(TipoControlloEnum.NumericoDouble.name())) {
	    return true;
	}
	return result;
    }
}
