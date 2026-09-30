package it.gruppoinit.pal.gp.core.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AnagrafeDAO;
import it.gruppoinit.pal.gp.core.dao.AnagrafeVerificheMailDAO;
import it.gruppoinit.pal.gp.core.dao.AnagrafestoricoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeRicercaBean;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.dao.helper.RicercaAnagraficeCollegateEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ScadenzecategoriebaseEnum;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniAnagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.AnagrafeIndirizzi;
import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.Bachecalavorocerca;
import it.gruppoinit.pal.gp.core.domain.Bachecalavorooffro;
import it.gruppoinit.pal.gp.core.domain.Cittadinanza;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Decodifiche;
import it.gruppoinit.pal.gp.core.domain.Elenchiprofessionalibase;
import it.gruppoinit.pal.gp.core.domain.Elencocassaedilebase;
import it.gruppoinit.pal.gp.core.domain.Elencoinailbase;
import it.gruppoinit.pal.gp.core.domain.Elencoinpsbase;
import it.gruppoinit.pal.gp.core.domain.Emailanagr;
import it.gruppoinit.pal.gp.core.domain.FoRichieste;
import it.gruppoinit.pal.gp.core.domain.Formegiuridiche;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocure;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeTPrenot;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Scadenze;
import it.gruppoinit.pal.gp.core.domain.Titoli;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeAvvisiHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AnagrafeFilter;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DettaglioAnagrafePFRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DettaglioAnagrafePGRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DettaglioAnagrafeRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DettaglioAnagrafeSedeRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.FiltroSoggetti;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.anagrafe.indirizzi.AnagrafeIndirizziDAO;
import it.gruppoinit.pal.gp.core.features.anagrafe.model.CodiceVerificaMailAnagrafeBean;
import it.gruppoinit.pal.gp.core.features.anagrafe.verticalizzazioni.IVertRicercaAnagrafeCollegataInternalService;
import it.gruppoinit.pal.gp.core.features.anagrafe.verticalizzazioni.IVertRicercaAnagrafeCollegataInternalService.STRATEGIA_RICERCA;
import it.gruppoinit.pal.gp.core.features.anagrafica.eventi.EventoEmailAnagrafeAggiornata;
import it.gruppoinit.pal.gp.core.features.areariservata.IVerticalizzazioneAreaRiservataService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.decodifiche.DecodificheService;
import it.gruppoinit.pal.gp.core.features.decodifiche.ListaDecodifiche;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IBorsellinoDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoOperazioneAggiornamento;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioniAnagrafeVerificaMailService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.filters.TipoRicercaEnum;
import it.gruppoinit.pal.gp.core.service.AmministrazioniAnagrafeService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AnagrafedocumentiService;
import it.gruppoinit.pal.gp.core.service.Anagrafedyn2datiService;
import it.gruppoinit.pal.gp.core.service.AnagrafemercatipresenzeService;
import it.gruppoinit.pal.gp.core.service.AnagrafestoricoService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;
import it.gruppoinit.pal.gp.core.service.BachecalavorocercaService;
import it.gruppoinit.pal.gp.core.service.BachecalavorooffroService;
import it.gruppoinit.pal.gp.core.service.Cdsinvitati2Service;
import it.gruppoinit.pal.gp.core.service.CittadinanzaService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.ElenchiprofessionalibaseService;
import it.gruppoinit.pal.gp.core.service.ElencocassaedilebaseService;
import it.gruppoinit.pal.gp.core.service.ElencoinailbaseService;
import it.gruppoinit.pal.gp.core.service.ElencoinpsbaseService;
import it.gruppoinit.pal.gp.core.service.EmailanagrService;
import it.gruppoinit.pal.gp.core.service.FDomandeService;
import it.gruppoinit.pal.gp.core.service.FoDomandeService;
import it.gruppoinit.pal.gp.core.service.FoRichiesteService;
import it.gruppoinit.pal.gp.core.service.FormegiuridicheService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocureService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.MercatiConsorziService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeStoricoService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeTPrenotService;
import it.gruppoinit.pal.gp.core.service.PermcdsinvitatiService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniInOutService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniService;
import it.gruppoinit.pal.gp.core.service.ScadenzeService;
import it.gruppoinit.pal.gp.core.service.TitoliService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.RecuperoDatiAnagraficaException;
import it.gruppoinit.pal.gp.core.service.helper.InserimentoIstanzeFlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.TipoWSAnagrafeAttivoEnum;
import it.gruppoinit.pal.gp.core.service.rules.AnagrafeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.IstanzeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.AAEPCSIClient;
import it.gruppoinit.pal.gp.core.ws.client.AdrierWsClient;
import it.gruppoinit.pal.gp.core.ws.client.Anagrafe2WsClient;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;
import it.gruppoinit.pal.gp.core.ws.client.ParixGateV2WsClient;
import it.gruppoinit.pal.gp.core.ws.client.ParixGateWsClient;
import it.gruppoinit.wsanagrafe2.ws.WsAnagrafe2Soap;
import it.piemonte.reteunitaria.csi.aaep.model.ListaPersonaCaricaInfoc;

@Service
public class AnagrafeServiceImpl extends BaseServiceImpl<Anagrafe, PkId> implements AnagrafeService {

    private static final Logger log = LoggerFactory.getLogger(AnagrafeServiceImpl.class);
    private static final String ENCRYPTING_ALGORITHM = "MD5";
    @Autowired
    private UserSecurityService userSecurityService;
    private AnagrafeDAO anagrafeDAO;
    private AnagrafeVerificheMailDAO anagrafeVerificheMailDAO;
    private AnagrafedocumentiService anagrafedocumentiService;
    private Anagrafedyn2datiService anagrafedyn2datiService;
    private AnagrafemercatipresenzeService anagrafemercatipresenzeService;
    private AnagrafestoricoDAO anagrafestoricoDAO;
    private AnagrafestoricoService anagrafestoricoService;
    private AutorizzazioniService autorizzazioniService;
    private AutorizzazioniSubentriService autorizzazioniSubentriService;
    private BachecalavorocercaService bachecalavorocercaService;
    private BachecalavorooffroService bachecalavorooffroService;
    private Cdsinvitati2Service cdsinvitati2Service;
    private CittadinanzaService cittadinanzaService;
    private ComuniService comuniService;
    private ConfigurazioneService configurazioneService;
    private ElencoinailbaseService elencoinailbaseService;
    private ElencoinpsbaseService elencoinpsbaseService;
    private ElencocassaedilebaseService elencocassaedilebaseService;
    private ElenchiprofessionalibaseService elenchiprofessionalibaseService;
    private EmailanagrService emailanagrService;
    private FDomandeService fDomandeService;
    private FoRichiesteService foRichiesteService;
    private FormegiuridicheService formegiuridicheService;
    private IstanzeprocureService istanzeprocureService;
    private IstanzerichiedentiService istanzerichiedentiService;
    private IstanzeService istanzeService;
    private MercatipresenzeDService mercatipresenzeDService;
    private MercatipresenzeStoricoService mercatipresenzeStoricoService;
    private MercatiConsorziService mercatiConsorziService;
    private OggettiService oggettiService;
    private PermcdsinvitatiService permcdsinvitatiService;
    private RegistrazioniInOutService registrazioniInOutService;
    private RegistrazioniService registrazioniService;
    private ScadenzeService scadenzeService;
    private TitoliService titoliService;
    private VerticalizzazioniService verticalizzazioniService;
    private FoDomandeService foDomandeService;
    private Anagrafe2WsClient anagrafe2WsClient;
    private ParixGateWsClient parixGateWsClient;
    private AdrierWsClient adrierWsClient;
    private AAEPCSIClient aaepcsiClient;
    private MailtipoService mailtipoService;
    private MailServiceWSClient mailServiceWSClient;
    private AmministrazioniAnagrafeService amministrazioniAnagrafeService;
    private IVerticalizzazioneAreaRiservataService verticalizzazioneAreaRiservataService;
    private AnagrafeIndirizziDAO anagrafeIndirizziDAO;
    @Autowired
    private IBorsellinoDAO borsellinoDAO;
    @Autowired
    private IEventPublisher eventPublisher;
    @Autowired
    private IVerticalizzazioniAnagrafeVerificaMailService verticalizzazioniAnagrafeVerificaMailService;
    @Autowired
    private IVertRicercaAnagrafeCollegataInternalService vertAnagCollegataInternalService;
    @Autowired
    private DecodificheService decodificheService;
    private ParixGateV2WsClient parixGateV2WsClient;

    @Autowired
    public void setAnagrafeVerificheMailDAO(AnagrafeVerificheMailDAO anagrafeVerificheMailDAO) {

	this.anagrafeVerificheMailDAO = anagrafeVerificheMailDAO;
    }

    @Autowired
    private void setAAEPCSIClient(AAEPCSIClient aaepcsiClient) {

	this.aaepcsiClient = aaepcsiClient;
    }

    @Autowired
    private MercatipresenzeTPrenotService mercatipresenzeTPrenotService;

    @Autowired
    public void setMailServiceWSClient(MailServiceWSClient mailServiceWSClient) {

	this.mailServiceWSClient = mailServiceWSClient;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setParixGateWsClient(ParixGateWsClient parixGateWsClient) {

	this.parixGateWsClient = parixGateWsClient;
    }

    @Autowired
    public void setAdrierWsClient(AdrierWsClient adrierWsClient) {

	this.adrierWsClient = adrierWsClient;
    }

    @Autowired
    public void setMercatiConsorziService(MercatiConsorziService mercatiConsorziService) {

	this.mercatiConsorziService = mercatiConsorziService;
    }

    @Autowired
    public void setScadenzeService(ScadenzeService scadenzeService) {

	this.scadenzeService = scadenzeService;
    }

    @Autowired
    public void setBachecalavorocercaService(BachecalavorocercaService bachecalavorocercaService) {

	this.bachecalavorocercaService = bachecalavorocercaService;
    }

    @Autowired
    public void setAnagrafedocumentiService(AnagrafedocumentiService anagrafedocumentiService) {

	this.anagrafedocumentiService = anagrafedocumentiService;
    }

    @Autowired
    public void setFoRichiesteService(FoRichiesteService foRichiesteService) {

	this.foRichiesteService = foRichiesteService;
    }

    @Autowired
    public void setBachecalavorooffroService(BachecalavorooffroService bachecalavorooffroService) {

	this.bachecalavorooffroService = bachecalavorooffroService;
    }

    @Autowired
    public void setAnagrafedyn2datiService(Anagrafedyn2datiService anagrafedyn2datiService) {

	this.anagrafedyn2datiService = anagrafedyn2datiService;
    }

    @Autowired
    public void setAnagrafemercatipresenzeService(AnagrafemercatipresenzeService anagrafemercatipresenzeService) {

	this.anagrafemercatipresenzeService = anagrafemercatipresenzeService;
    }

    @Autowired
    public void setEmailanagrService(EmailanagrService emailanagrService) {

	this.emailanagrService = emailanagrService;
    }

    @Autowired
    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setMercatipresenzeDService(MercatipresenzeDService mercatipresenzeDService) {

	this.mercatipresenzeDService = mercatipresenzeDService;
    }

    @Autowired
    public void setMercatipresenzeStoricoService(MercatipresenzeStoricoService mercatipresenzeStoricoService) {

	this.mercatipresenzeStoricoService = mercatipresenzeStoricoService;
    }

    @Autowired
    public void setRegistrazioniInOutService(RegistrazioniInOutService registrazioniInOutService) {

	this.registrazioniInOutService = registrazioniInOutService;
    }

    @Autowired
    public void setRegistrazioniService(RegistrazioniService registrazioniService) {

	this.registrazioniService = registrazioniService;
    }

    @Autowired
    public void setFormegiuridicheService(FormegiuridicheService formegiuridicheService) {

	this.formegiuridicheService = formegiuridicheService;
    }

    @Autowired
    public void setTitoliService(TitoliService titoliService) {

	this.titoliService = titoliService;
    }

    @Autowired
    public void setCittadinanzaService(CittadinanzaService cittadinanzaService) {

	this.cittadinanzaService = cittadinanzaService;
    }

    @Autowired
    public void setElencoinailbaseService(ElencoinailbaseService elencoinailbaseService) {

	this.elencoinailbaseService = elencoinailbaseService;
    }

    @Autowired
    public void setElencoinpsbaseService(ElencoinpsbaseService elencoinpsbaseService) {

	this.elencoinpsbaseService = elencoinpsbaseService;
    }

    @Autowired
    public void setElenchiprofessionalibaseService(ElenchiprofessionalibaseService elenchiprofessionalibaseService) {

	this.elenchiprofessionalibaseService = elenchiprofessionalibaseService;
    }

    @Autowired
    public void setElencocassaedilebaseService(ElencocassaedilebaseService elencocassaedilebaseService) {

	this.elencocassaedilebaseService = elencocassaedilebaseService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setIstanzeprocureService(IstanzeprocureService istanzeprocureService) {

	this.istanzeprocureService = istanzeprocureService;
    }

    @Autowired
    public void setIstanzerichiedentiService(IstanzerichiedentiService istanzerichiedentiService) {

	this.istanzerichiedentiService = istanzerichiedentiService;
    }

    @Autowired
    public void setCdsinvitati2Service(Cdsinvitati2Service cdsinvitati2Service) {

	this.cdsinvitati2Service = cdsinvitati2Service;
    }

    @Autowired
    public void setAutorizzazioniSubentriService(AutorizzazioniSubentriService autorizzazioniSubentriService) {

	this.autorizzazioniSubentriService = autorizzazioniSubentriService;
    }

    @Autowired
    public void setfDomandeService(FDomandeService fDomandeService) {

	this.fDomandeService = fDomandeService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setPermcdsinvitatiService(PermcdsinvitatiService permcdsinvitatiService) {

	this.permcdsinvitatiService = permcdsinvitatiService;
    }

    @Autowired
    public void setAnagrafestoricoDAO(AnagrafestoricoDAO anagrafestoricoDAO) {

	this.anagrafestoricoDAO = anagrafestoricoDAO;
    }

    @Autowired
    public void setAnagrafestoricoService(AnagrafestoricoService anagrafestoricoService) {

	this.anagrafestoricoService = anagrafestoricoService;
    }

    @Autowired
    public void setAnagrafeDAO(AnagrafeDAO anagrafeDAO) {

	this.anagrafeDAO = anagrafeDAO;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setFoDomandeService(FoDomandeService foDomandeService) {

	this.foDomandeService = foDomandeService;
    }

    @Autowired
    public void setAnagrafe2WsClient(Anagrafe2WsClient anagrafe2WsClient) {

	this.anagrafe2WsClient = anagrafe2WsClient;
    }

    @Autowired
    public void setAmministrazioniAnagrafeService(AmministrazioniAnagrafeService amministrazioniAnagrafeService) {

	this.amministrazioniAnagrafeService = amministrazioniAnagrafeService;
    }

    @Autowired
    public void setVerticalizzazioneAreaRiservataService(IVerticalizzazioneAreaRiservataService verticalizzazioneAreaRiservataService) {

	this.verticalizzazioneAreaRiservataService = verticalizzazioneAreaRiservataService;
    }

    @Autowired
    public void setAnagrafeIndirizziDAO(AnagrafeIndirizziDAO anagrafeIndirizziDAO) {

	this.anagrafeIndirizziDAO = anagrafeIndirizziDAO;
    }

    @Autowired
    public void setParixGateV2WsClient(ParixGateV2WsClient parixGateV2WsClient) {

	this.parixGateV2WsClient = parixGateV2WsClient;
    }

    @Override
    public void delete(Anagrafe entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    anagrafeDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(Anagrafe entity) {

	anagrafeVerificheMailDAO.deleteByAnagrafe(entity.getId().getCodice());
	Set<AnagrafeIndirizzi> indirizzi = entity.getIndirizzi();
	for (AnagrafeIndirizzi indirizzo : indirizzi) {
	    this.anagrafeIndirizziDAO.delete(indirizzo);
	}
	List<Anagrafestorico> anagrafestoricos = anagrafestoricoService.findStoricoByAnagrafe(entity);
	for (Anagrafestorico anagrafestorico : anagrafestoricos) {
	    anagrafestoricoService.delete(anagrafestorico);
	}
	Set<Anagrafedocumenti> anagrafedocumentis = entity.getAnagrafedocumentis();
	for (Anagrafedocumenti anagrafedocumenti : anagrafedocumentis) {
	    anagrafedocumentiService.delete(anagrafedocumenti);
	}
	List<Scadenze> scadenzes = scadenzeService.findAvvisiForAnagrafe(entity.getId().getCodice(), ScadenzecategoriebaseEnum.ALL);
	for (Scadenze scadenze : scadenzes) {
	    scadenzeService.delete(scadenze);
	}
	Set<Emailanagr> emailanagrs = entity.getEmailanagrs();
	for (Emailanagr emailanagr : emailanagrs) {
	    emailanagrService.delete(emailanagr);
	}
	//	// TODO
	//	Set<FDomande> anagrafeFDomandes = entity.getAnagrafeFDomandes();
	//	// TODO
	//	Set<FDomande> societaFDomandes = entity.getSocietaFDomandes();
	//	// TODO
	//	Set<FDomande> subentroFDomandes = entity.getSubentroFDomandes();
	Set<Bachecalavorooffro> bachecalavorooffros = entity.getBachecalavorooffros();
	for (Bachecalavorooffro bachecalavorooffro : bachecalavorooffros) {
	    bachecalavorooffroService.delete(bachecalavorooffro);
	}
	Set<Bachecalavorocerca> bachecalavorocercas = entity.getBachecalavorocercas();
	for (Bachecalavorocerca bachecalavorocerca : bachecalavorocercas) {
	    bachecalavorocercaService.delete(bachecalavorocerca);
	}
	Set<Anagrafedyn2dati> anagrafedyn2datis = entity.getAnagrafedyn2datis();
	for (Anagrafedyn2dati anagrafedyn2dati : anagrafedyn2datis) {
	    anagrafedyn2datiService.delete(anagrafedyn2dati);
	}
	Set<Anagrafedyn2modellit> anagrafedyn2modellits = entity.getAnagrafedyn2modellits();
	for (Anagrafedyn2modellit anagrafedyn2modellit : anagrafedyn2modellits) {
	}
	Set<FoRichieste> foRichiestes = entity.getFoRichiestes();
	for (FoRichieste foRichieste : foRichiestes) {
	    foRichiesteService.delete(foRichieste);
	}
	List<Istanzeprocure> istanzeprocures = istanzeprocureService.findByAnagrafeProcuratore(entity.getId().getCodice());
	for (Istanzeprocure istanzeprocure : istanzeprocures) {
	    istanzeprocureService.delete(istanzeprocure);
	}
	List<Istanzeprocure> istanzeprocuresrap = istanzeprocureService.findByAnagrafeRappresentato(entity.getId().getCodice());
	for (Istanzeprocure istanzeprocure : istanzeprocuresrap) {
	    istanzeprocureService.delete(istanzeprocure);
	}
	//. Cancellazione riferimenti ad AMMINISTRAZIONI_ANAGRAFE
	List<AmministrazioniAnagrafe> amministrazioniAnagrafes = amministrazioniAnagrafeService.findByAnagrafe(entity.getId().getCodice());
	for (AmministrazioniAnagrafe amministrazioniAnagrafe : amministrazioniAnagrafes) {
	    amministrazioniAnagrafeService.delete(amministrazioniAnagrafe);
	}
	List<MercatipresenzeTPrenot> mpps = mercatipresenzeTPrenotService.findByAnagrafe(entity.getId().getCodice());
	for (MercatipresenzeTPrenot mercatipresenzeTPrenot : mpps) {
	    mercatipresenzeTPrenotService.delete(mercatipresenzeTPrenot);
	}
    }

    @Override
    public List<Anagrafe> findAll(Integer firstResult, Integer maxResult) {

	return anagrafeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Anagrafe findById(PkId id) {

	return anagrafeDAO.findById(id);
    }

    @Override
    public Class<Anagrafe> getEntityClass() {

	return Anagrafe.class;
    }

    @Override
    public void insert(Anagrafe entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    if (controllaUnivocitaCfoPiva(entity)) {
		anagrafeDAO.insert(entity);
		childDataInsert(entity);
	    }
	}
    }

    private boolean controllaUnivocitaCfoPiva(Anagrafe entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro anagrafe è nullo");
	}
	if (StringUtils.isNotBlank(entity.getCodicefiscale()) || StringUtils.isNotBlank(entity.getPartitaiva())) {
	    ServiceValidationRules validationRule = (ServiceValidationRules) SigeproBusinessRules.getClassRules(ServiceValidationRules.class);
	    AnagrafeBusinessRules anagrafeRules = (AnagrafeBusinessRules) SigeproBusinessRules.getClassRules(AnagrafeBusinessRules.class);
	    boolean forzaInserimentoAnagrafe = false;
	    if (anagrafeRules != null) {
		forzaInserimentoAnagrafe = anagrafeRules.getCustomRule(AnagrafeBusinessRules.CustomRuleEnum.forzaInserimentoAnagrafe.name());
	    }
	    boolean doBusinessValidation = true;
	    if (validationRule != null) {
		doBusinessValidation = validationRule.getCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name());
	    }
	    doBusinessValidation = (doBusinessValidation && !forzaInserimentoAnagrafe);
	    if (doBusinessValidation) {
		String criterio = "";
		AnagrafeFilter filtro = new AnagrafeFilter();
		filtro.setDefaultWhereCondition(DAOEnum.FIND_BY_IDCOMUNE);
		filtro.setTipoRicercaEnum(TipoRicercaEnum.EQUALSIGNORECASE);
		Anagrafe datiAnagrafici = new Anagrafe();
		datiAnagrafici.setFlagDisabilitato(0);
		// BOCCI 2012-02-01 CONTROLLO L'UNIVOCITA' PER TIPOANAGRAFE
		// SE TIPO ANAGRAFE NON è STATO SETTATO RICAVO PF SE SETTATO NOME ALTRIMENTI E' PG 
		if (StringUtils.isNotBlank(entity.getTipoanagrafe())) {
		    datiAnagrafici.setTipoanagrafe(entity.getTipoanagrafe());
		} else {
		    if (StringUtils.isNotBlank(entity.getNome())) {
			datiAnagrafici.setTipoanagrafe(WebConstants.PERSONA_FISICA);
		    } else {
			datiAnagrafici.setTipoanagrafe(WebConstants.PERSONA_GIURIDICA);
		    }
		}
		if (entity.getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
		    // PERSONA FISICA
		    datiAnagrafici.setCodicefiscale(entity.getCodicefiscale());
		    criterio = "codice fiscale=" + entity.getCodicefiscale();
		} else {
		    // PERSONA GIURIDICA SE SETTATO PIVA CONTROLLO SOLAMENTE PER PIVA
		    if (StringUtils.isNotBlank(entity.getPartitaiva())) {
			datiAnagrafici.setPartitaiva(entity.getPartitaiva());
			criterio = "partita iva=" + entity.getPartitaiva();
		    } else if (StringUtils.isNotBlank(entity.getCodicefiscale())) {
			// ALTRIMENTI PER CF
			datiAnagrafici.setCodicefiscale(entity.getCodicefiscale());
			criterio = "codice fiscale=" + entity.getCodicefiscale();
		    }
		}
		// END BOCCI 2012-02-01 
		filtro.setDatiAnagrafe(datiAnagrafici);
		List<Anagrafe> result = findByFilter(filtro, 0, 4);
		if (result.isEmpty() == false) {
		    String message = getMessageFromBundle("service_error.anagrafe_codice_fiscale_piva_presente", new Object[] { criterio });
		    throw new BusinessValidationException(message);
		}
	    }
	}
	return true;
    }

    private void childDataInsert(Anagrafe entity) {

	Anagrafestorico anagrafestorico = new Anagrafestorico();
	String[] arrayFieldExclude = getArrayFieldExcludes();
	List<String> fielExclude = Arrays.asList(arrayFieldExclude);
	anagrafestorico = copyAnagrafeToAnagrafestorico(entity, anagrafestorico, fielExclude);
	// setto l'anagrafe
	anagrafestorico.setAnagrafe(entity);
	// il primo record di anagrafestorico riferito ad una anagrafica
	// avrà la data inizio valità = null che per nostra convenzione significa valida da sempre
	//anagrafestoricoDAO.insert(anagrafestorico);
	anagrafestoricoService.insert(anagrafestorico);
    }

    @Override
    public void update(Anagrafe entity) {

	// Recupero l'oggetto presente sul DB in anagrafe prima che venga modificato
	// verra utilizzato in entrambi i casi successivi:
	// 1 - nel caso di dati incoerenti servirà per inserire in anagrafe storico un record con i vecchi dati
	// 2 - in caso di update verrà utilizzato per controllare se sono cambiati i dati ritenuti fondamentali
	// per cui l'update dell'anagrafica comporti anche un update dell'anagrafica storico.
	PkId id = new PkId(entity.getId().getCodice());
	Anagrafe anagrafeDB = this.findById(id);
	String[] arrayFieldExclude = getArrayFieldExcludes();
	List<String> fielExclude = Arrays.asList(arrayFieldExclude);
	dataIntegration(entity, true);
	if (validateEntity(entity)) {
	    controllaModificaCFoPiva(entity.getId().getCodice(), entity.getTipoanagrafe(), entity.getCodicefiscale(), entity.getPartitaiva());
	    anagrafeDAO.update(entity);
	    // controllo che il DB sia coerente,esista almeno un record nella tabella anagrafe storico per questa anagrafica
	    Integer codiceAnagrafe = entity.getId().getCodice();
	    anagrafeDAO.evict(entity);
	    entity = this.findById(new PkId(codiceAnagrafe));
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction restriction = new FilterRestriction();
	    restriction.addFilterField(FilterUtils.equals("anagrafeId", entity.getId().getCodice(), Integer.class));
	    ft.addRestriction(restriction);
	    //	    List<Anagrafestorico> storicos = anagrafestoricoDAO.findByFilterTable(ft);
	    List<Anagrafestorico> storicos = anagrafestoricoDAO.findByFilterTable(ft);
	    if (!storicos.isEmpty()) {
		// Controllo che l'anagrafe che si sta inserendo non sia cambiata nei
		// suoi campi "rilevanti"  (nome, nominativo,sesso,tipologia, tipoanagrafe, partitaiva,codicefiscale,indirizzo,indirizzocorrispondenza,email,pec)
		// SE si faccio l'insert in anagrafica storico.
		if (isAnagrafeChange(anagrafeDB, entity)) {
		    // ricerco l'ultimo record inserito nella tabella anagrafe storico per l'anagrafe passata
		    // e setto la data fine validita a quella odierna.
		    Calendar dataOdierna = Calendar.getInstance();
		    //		    Anagrafestorico oldAnagrafestorico = anagrafestoricoDAO.findUltimoAnagrafestoricoByAnagrafe(entity);
		    Anagrafestorico oldAnagrafestorico = anagrafestoricoService.findUltimoAnagrafestoricoByAnagrafe(entity);
		    oldAnagrafestorico.setDatafinevalidita(dataOdierna.getTime());
		    dataIntegrationAnagrafeStorico(oldAnagrafestorico, true);
		    //		    anagrafestoricoDAO.update(oldAnagrafestorico);
		    anagrafestoricoService.update(oldAnagrafestorico);
		    // creo il nuovo record in anagrafe storico che indica quello corrente in anagrafe.
		    Anagrafestorico anagrafestorico = new Anagrafestorico();
		    anagrafestorico = copyAnagrafeToAnagrafestorico(entity, anagrafestorico, fielExclude);
		    anagrafestorico.setDatainiziovalidita(dataOdierna.getTime());
		    anagrafestorico.setAnagrafe(entity);
		    dataIntegrationAnagrafeStorico(anagrafestorico, false);
		    // anagrafestoricoDAO.insert(anagrafestorico);
		    anagrafestoricoService.insert(anagrafestorico);
		}
	    } else {
		// dati DB incoerenti, operazioni di bonifica
		Anagrafestorico anagrafestorico = new Anagrafestorico();
		anagrafestorico = copyAnagrafeToAnagrafestorico(entity, anagrafestorico, fielExclude);
		// setto l'angrafe
		anagrafestorico.setAnagrafe(entity);
		// il primo record di anagrafestorico riferito a una anagrafica
		// avra la data inizio valità uguale a quella attuale			
		dataIntegrationAnagrafeStorico(anagrafestorico, false);
		//anagrafestoricoDAO.insert(anagrafestorico);
		anagrafestoricoService.insert(anagrafestorico);
	    }
	}
    }

    private void controllaModificaCFoPiva(Integer codiceAnagrafe, String tipoanagrafe, String codicefiscale, String partitaiva) {

	if (StringUtils.isNotBlank(codicefiscale) || StringUtils.isNotBlank(codicefiscale)) {
	    ServiceValidationRules validationRule = (ServiceValidationRules) SigeproBusinessRules.getClassRules(ServiceValidationRules.class);
	    AnagrafeBusinessRules anagrafeRules = (AnagrafeBusinessRules) SigeproBusinessRules.getClassRules(AnagrafeBusinessRules.class);
	    boolean forzaInserimentoAnagrafe = false;
	    if (anagrafeRules != null) {
		forzaInserimentoAnagrafe = anagrafeRules.getCustomRule(AnagrafeBusinessRules.CustomRuleEnum.forzaInserimentoAnagrafe.name());
	    }
	    boolean doBusinessValidation = true;
	    if (validationRule != null) {
		doBusinessValidation = validationRule.getCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name());
	    }
	    doBusinessValidation = (doBusinessValidation && !forzaInserimentoAnagrafe);
	    if (doBusinessValidation) {
		IstanzeBusinessRules istanzeRules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
		boolean isInserimentoDaStc = istanzeRules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name());
		if (!isInserimentoDaStc) { // controllo solamente se non sto aggiornando il metodo da inserimento pratica STC
					   // non si vogliono generare errori. Il controllo dovrebbe essere fatto solamente da aggiornamento da interfaccia web 
		    if (tipoanagrafe.equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
			if (StringUtils.isNotBlank(codicefiscale)) {
			    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
			    FilterRestriction fr = new FilterRestriction();
			    fr.addFilterField(FilterUtils.notEquals("id.codice", codiceAnagrafe, Integer.class));
			    ft.addRestriction(fr);
			    FilterRestriction tp = new FilterRestriction();
			    tp.addFilterField(FilterUtils.equalsIgnoreCase("codicefiscale", codicefiscale));
			    tp.addFilterField(FilterUtils.equals("tipoanagrafe", tipoanagrafe, String.class));
			    ft.addRestriction(tp);
			    FilterRestriction dis = new FilterRestriction();
			    dis.setAndOrRestriction(AndOrRestriction.OR);
			    dis.addFilterField(FilterUtils.equals("flagDisabilitato", 0, Integer.class));
			    dis.addFilterField(FilterUtils.isNull("flagDisabilitato"));
			    ft.addRestriction(dis);
			    boolean presente = anagrafeDAO.existsRecords(ft);
			    if (presente) {
				String message = getMessageFromBundle("service_error.anagrafe_codice_fiscale_piva_presente",
					new Object[] { "codice fiscale=" + codicefiscale });
				throw new BusinessValidationException(message);
			    }
			}
		    } else if (tipoanagrafe.equalsIgnoreCase(WebConstants.PERSONA_GIURIDICA)) {
			if (StringUtils.isNotBlank(codicefiscale)) {
			    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
			    FilterRestriction fr = new FilterRestriction();
			    fr.addFilterField(FilterUtils.notEquals("id.codice", codiceAnagrafe, Integer.class));
			    ft.addRestriction(fr);
			    FilterRestriction tp = new FilterRestriction();
			    tp.addFilterField(FilterUtils.equalsIgnoreCase("codicefiscale", codicefiscale));
			    tp.addFilterField(FilterUtils.equals("tipoanagrafe", tipoanagrafe, String.class));
			    ft.addRestriction(tp);
			    FilterRestriction dis = new FilterRestriction();
			    dis.setAndOrRestriction(AndOrRestriction.OR);
			    dis.addFilterField(FilterUtils.equals("flagDisabilitato", 0, Integer.class));
			    dis.addFilterField(FilterUtils.isNull("flagDisabilitato"));
			    ft.addRestriction(dis);
			    boolean presente = anagrafeDAO.existsRecords(ft);
			    if (presente) {
				String message = getMessageFromBundle("service_error.anagrafe_codice_fiscale_piva_presente",
					new Object[] { "codice fiscale=" + codicefiscale });
				throw new BusinessValidationException(message);
			    }
			} else if (StringUtils.isNotBlank(partitaiva)) {
			    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
			    FilterRestriction fr = new FilterRestriction();
			    fr.addFilterField(FilterUtils.notEquals("id.codice", codiceAnagrafe, Integer.class));
			    ft.addRestriction(fr);
			    FilterRestriction tp = new FilterRestriction();
			    tp.addFilterField(FilterUtils.equalsIgnoreCase("partitaiva", partitaiva));
			    tp.addFilterField(FilterUtils.equals("tipoanagrafe", tipoanagrafe, String.class));
			    ft.addRestriction(tp);
			    FilterRestriction dis = new FilterRestriction();
			    dis.setAndOrRestriction(AndOrRestriction.OR);
			    dis.addFilterField(FilterUtils.equals("flagDisabilitato", 0, Integer.class));
			    dis.addFilterField(FilterUtils.isNull("flagDisabilitato"));
			    ft.addRestriction(dis);
			    boolean presente = anagrafeDAO.existsRecords(ft);
			    if (presente) {
				String message = getMessageFromBundle("service_error.anagrafe_codice_fiscale_piva_presente",
					new Object[] { "partita iva=" + partitaiva });
				throw new BusinessValidationException(message);
			    }
			}
		    }
		}
	    }
	}
    }

    private void dataIntegrationAnagrafeStorico(Anagrafestorico entity, boolean isUpdate) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro anagrafe da validare è nullo");
	}
	if (entity.getFlagDisabilitato() == null) {
	    entity.setFlagDisabilitato(Integer.valueOf(0));
	}
	if (entity.getTipologia() == null) {
	    entity.setTipologia(Integer.valueOf(0));
	}
	if (StringUtils.isEmpty(entity.getTipoanagrafe())) {
	    if (StringUtils.isNotBlank(entity.getAnagrafe().getTipoanagrafe())) {
		entity.setTipoanagrafe(entity.getAnagrafe().getTipoanagrafe());
	    } else {
		throw new BusinessValidationException("Tipo anagrafe non trovata per l'anagrafe " + entity.getAnagrafe().getId());
	    }
	}
	fixMergeStoricoProperties(entity);
    }

    protected void fixMergeStoricoProperties(Anagrafestorico entity) {

	Cittadinanza cittadinanza = cittadinanzaService.bindDomainObject(entity.getCittadinanza(), Integer.class, "codice");
	entity.setCittadinanza(cittadinanza);
	Comuni comregditte = comuniService.bindDomainObject(entity.getComunecomregditte(), String.class, "codicecomune");
	entity.setComunecomregditte(comregditte);
	Comuni comcorris = comuniService.bindDomainObject(entity.getComunecorrispondenza(), String.class, "codicecomune");
	entity.setComunecorrispondenza(comcorris);
	Comuni comNascita = comuniService.bindDomainObject(entity.getComuneNascita(), String.class, "codicecomune");
	entity.setComuneNascita(comNascita);
	Comuni comregtrib = comuniService.bindDomainObject(entity.getComuneregtrib(), String.class, "codicecomune");
	entity.setComuneregtrib(comregtrib);
	Comuni comresidenza = comuniService.bindDomainObject(entity.getComuneResidenza(), String.class, "codicecomune");
	entity.setComuneResidenza(comresidenza);
	Formegiuridiche fg = formegiuridicheService.bindDomainObject(entity.getFormagiuridica(), PkId.class, "id.codice");
	entity.setFormagiuridica(fg);
	Titoli titolo = titoliService.bindDomainObject(entity.getTitolo(), PkId.class, "id.codice");
	entity.setTitolo(titolo);
    }

    private void dataIntegration(Anagrafe entity, boolean isUpdate) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro anagrafe da validare è nullo");
	}
	if (entity.getFlagDisabilitato() == null) {
	    entity.setFlagDisabilitato(0);
	}
	if (entity.getTipologia() == null) {
	    entity.setTipologia(0);
	}
	if (entity.getFoUtentetester() == null) {
	    entity.setFoUtentetester(Boolean.FALSE);
	}
	if (StringUtils.isNotBlank(entity.getCodicefiscale())) {
	    entity.setCodicefiscale(entity.getCodicefiscale().toUpperCase());
	}
	if (entity.getFlagMailVerificata() == null) {
	    entity.setFlagMailVerificata(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
	checkPassword(entity, isUpdate);
    }

    protected void fixMergeEntityProperties(Anagrafe entity) {

	Cittadinanza cittadinanza = cittadinanzaService.bindDomainObject(entity.getCittadinanza(), Integer.class, "codice");
	entity.setCittadinanza(cittadinanza);
	Comuni comregditte = comuniService.bindDomainObject(entity.getComunecomregditte(), String.class, "codicecomune");
	entity.setComunecomregditte(comregditte);
	Comuni comcorris = comuniService.bindDomainObject(entity.getComunecorrispondenza(), String.class, "codicecomune");
	entity.setComunecorrispondenza(comcorris);
	Comuni comNascita = comuniService.bindDomainObject(entity.getComuneNascita(), String.class, "codicecomune");
	entity.setComuneNascita(comNascita);
	Comuni comregtrib = comuniService.bindDomainObject(entity.getComuneregtrib(), String.class, "codicecomune");
	entity.setComuneregtrib(comregtrib);
	Comuni comresidenza = comuniService.bindDomainObject(entity.getComuneResidenza(), String.class, "codicecomune");
	entity.setComuneResidenza(comresidenza);
	Elenchiprofessionalibase eb = elenchiprofessionalibaseService.bindDomainObject(entity.getElenchiprofessionalibase(), Integer.class, "id");
	entity.setElenchiprofessionalibase(eb);
	Formegiuridiche fg = formegiuridicheService.bindDomainObject(entity.getFormagiuridica(), PkId.class, "id.codice");
	entity.setFormagiuridica(fg);
	Titoli titolo = titoliService.bindDomainObject(entity.getTitolo(), PkId.class, "id.codice");
	entity.setTitolo(titolo);
	Elencoinailbase einailb = elencoinailbaseService.bindDomainObject(entity.getSedeInail(), String.class, "codice");
	entity.setSedeInail(einailb);
	Elencoinpsbase einpsbase = elencoinpsbaseService.bindDomainObject(entity.getSedeInps(), String.class, "codice");
	entity.setSedeInps(einpsbase);
	Elencocassaedilebase elencocassaedilebase = elencocassaedilebaseService.bindDomainObject(entity.getSedeCassaedile(), String.class, "codice");
	entity.setSedeCassaedile(elencocassaedilebase);
    }

    @Override
    public List<Anagrafe> findActiveByFilter(Anagrafe anagrafe) {

	return anagrafeDAO.findByNominativo(anagrafe.getNominativo(), AnagrafeEnum.ACTIVE);
    }

    @Override
    public List<Anagrafe> findAllByFilter(Anagrafe anagrafe) {

	return anagrafeDAO.findByNominativo(anagrafe.getNominativo(), AnagrafeEnum.ALL);
    }

    @Override
    public List<Anagrafe> findDisabledByFilter(Anagrafe anagrafe) {

	return anagrafeDAO.findByNominativo(anagrafe.getNominativo(), AnagrafeEnum.DISABLED);
    }

    @Override
    public List<Anagrafe> findAnagrafeByRegistrazioni(Anagrafe entity) {

	return registrazioniService.findAnagrafeByRegistrazioni(entity);
    }

    @Override
    public Anagrafestorico findStoricoId(Anagrafe entity, Date dataValidita) {

	Anagrafe an = new Anagrafe();
	anagrafeDAO.flush();
	anagrafeDAO.evict(entity);
	an = this.findById(new PkId(entity.getId().getCodice()));
	return _findStoricoId(an, dataValidita);
    }

    private Anagrafestorico _findStoricoId(Anagrafe entity, Date dataValidita) {

	//	Anagrafestorico anagrafestorico = anagrafestoricoDAO.findStoricoId(entity, dataValidita);
	Anagrafestorico anagrafestorico = anagrafestoricoService.findStoricoId(entity, dataValidita);
	if (EntityUtils.getNestedProperty(anagrafestorico, "id.codice") != null) {
	    return anagrafestorico;
	} else {
	    log.error(
		    "Nessun record presente per i criteri selezionati({}-{}).\nPossibile anomalia nel salvataggio dei dati sulla tabella ANAGRAFESTORICO-eseguo una bonifica dei dati inserendo un record in anagrafestorico",
		    EntityUtils.getNestedProperty(entity, "id"), dataValidita);
	    anagrafestorico = new Anagrafestorico();
	    String[] arrayFieldExclude = getArrayFieldExcludes();
	    List<String> fielExclude = Arrays.asList(arrayFieldExclude);
	    anagrafestorico = copyAnagrafeToAnagrafestorico(entity, anagrafestorico, fielExclude);
	    // setto l'anagrafe
	    anagrafestorico.setAnagrafe(entity);
	    dataIntegrationAnagrafeStorico(anagrafestorico, false);
	    // anagrafestoricoDAO.insert(anagrafestorico);
	    anagrafestoricoService.insert(anagrafestorico);
	    return anagrafestorico;
	    //	    throw new RuntimeException(
	    //		    "AnagraficaServiceImpl.findStoricoId: Errore : Nessun record presente per i criteri selezionati.\nPossibile anomalia nel salvataggio dei dati sulla tabella ANAGRAFESTORICO");
	}
    }

    @Override
    public Anagrafestorico findStoricoId(Anagrafe entity) {

	return this.findStoricoId(entity, Calendar.getInstance().getTime());
    }

    @Override
    public String calcolaCodicefiscale(String nominativo, String nome, Date datanascita, String sesso, String codicecomune) {

	String cf = "";
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (StringUtils.isBlank(nominativo)) {
	    _ivs.add(new InvalidValue("service_error.cognome_non_presente", null, null, null, null));
	}
	if (StringUtils.isBlank(nome)) {
	    _ivs.add(new InvalidValue("service_error.nome_non_presente", null, null, null, null));
	}
	if (datanascita == null) {
	    _ivs.add(new InvalidValue("service_error.data_nascita_non_presente", null, null, null, null));
	}
	if (StringUtils.isBlank(sesso)) {
	    _ivs.add(new InvalidValue("service_error.sesso_non_presente", null, null, null, null));
	}
	if (StringUtils.isBlank(codicecomune)) {
	    _ivs.add(new InvalidValue("service_error.comune_nascita_non_presente", null, null, null, null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String dataNascita = dateFormat.format(datanascita);
	try {
	    cf = Utilities.calcolaCodiceFiscale(nominativo, nome, dataNascita, sesso, codicecomune);
	} catch (IllegalArgumentException e) {
	}
	return cf;
    }

    @Override
    public Anagrafe findAnagrafeAggiornataByCF(Anagrafe anagrafeSigepro) {

	Anagrafe anagrafeSigeproModificata = null;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (isPresentCodiceFiscale(anagrafeSigepro)) {
	    String token = ORMHelper.getToken();
	    WsAnagrafe2Soap port = null;
	    it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafeEnteTerzo = new it.gruppoinit.wsanagrafe2.schema.Anagrafe();
	    try {
		// Controllo se l'anagrafica passata sia Una persono giuridica o un persona fisica per recuperare l'url corretto
		if (StringUtils.defaultString(anagrafeSigepro.getTipoanagrafe(), WebConstants.PERSONA_FISICA)
			.equalsIgnoreCase(WebConstants.PERSONA_GIURIDICA)) {
		    // Istanzio la porta per la chiamata al WS con il puntamento al servizio ricerca di persone giuridiche
		    if (log.isDebugEnabled()) {
			log.debug("findAnagrafeAggiornataByCF# Recupero le modifiche dell'anagrafica per PI");
		    }
		    port = getPortWS(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_URL_RICERCA_PG);
		    anagrafeEnteTerzo = port.getPersonaGiuridica(token, anagrafeSigepro.getCodicefiscale());
		} else {
		    // Istanzio la porta per la chiamata al WS con il puntamento al servizio ricerca di persone giuridiche
		    if (log.isDebugEnabled()) {
			log.debug("findAnagrafeAggiornataByCF# Recupero le modifiche dell'anagrafica per partiva CF");
		    }
		    port = getPortWS(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_URL_RICERCA_PF);
		    anagrafeEnteTerzo = port.getPersonaFisica(token, anagrafeSigepro.getCodicefiscale());
		}
		// Gianpaolo : Spostato sopra l' "if" come per il metodo "findAnagrafeAggiornataByPI"	
		//  perchè quando tornava un errore di anagrafe non trovata rilanciava l'eccezione del catch, comportamento sbagliato.	
	    } catch (RuntimeException e) {
		log.error("findAnagrafeAggiornataByCF: Errore nel recupero delle modifiche dell'anagrafica={}", e.getMessage());
		throw new RuntimeException("Errore nel recupero delle modifiche dell'anagrafica=" + e.getMessage(), e);
	    }
	    if (anagrafeEnteTerzo == null) {
		_ivs.add(new InvalidValue("service_error.anagrafe_non_presente_cf", anagrafeSigepro.getClass(), "procedimento", null,
			anagrafeSigepro));
		this.throwValidationMessages(_ivs);
	    }
	    // Metodo che controlla i campi che potrebbero essere stati modificati;
	    // se il campo è stato modificato viene inserito quello recuperato dal WS
	    // altrimenti il campo rimane inalterato
	    anagrafeSigeproModificata = getAnagrafeSigeproConModifiche(anagrafeEnteTerzo, anagrafeSigepro, true);
	    return anagrafeSigeproModificata;
	}
	return anagrafeSigeproModificata;
    }

    @Override
    public Anagrafe findAnagrafeAggiornataByPI(Anagrafe anagrafeSigepro) {

	Anagrafe anagrafeSigeproModificata = null;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (isPresentPartitaiva(anagrafeSigepro)) {
	    String token = ORMHelper.getToken();
	    WsAnagrafe2Soap port = null;
	    it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafeEnteTerzo = new it.gruppoinit.wsanagrafe2.schema.Anagrafe();
	    // Istanzio la porta per chiamate il WS
	    port = getPortWS(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_URL_RICERCA_PG);
	    try {
		// Chiamo il WS che recupera l'anagrafica a partire dalla partita IVA
		if (log.isDebugEnabled()) {
		    log.debug("findAnagrafeAggiornataByPI# Recupero le modifiche dell'anagrafica per partiva PI");
		}
		anagrafeEnteTerzo = port.getPersonaGiuridica(token, anagrafeSigepro.getPartitaiva());
	    } catch (RuntimeException e) {
		log.error("findAnagrafeAggiornataByPI: Errore nel recupero delle modifiche dell'anagrafica={}", e.getMessage());
		throw new RuntimeException("Errore nel recupero delle modifiche dell'anagrafica=" + e.getMessage(), e);
	    }
	    if (anagrafeEnteTerzo == null) {
		_ivs.add(new InvalidValue("service_error.anagrafe_non_presente_pi", anagrafeSigepro.getClass(), "procedimento", null,
			anagrafeSigepro));
		this.throwValidationMessages(_ivs);
	    }
	    // Metodo che controlla i campi che potrebbero essere stati modificati;
	    // se il campo è stato modificato viene inserito quello recuperato dal WS
	    // altrimenti il campo rimane inalterato
	    anagrafeSigeproModificata = getAnagrafeSigeproConModifiche(anagrafeEnteTerzo, anagrafeSigepro, true);
	    return anagrafeSigeproModificata;
	}
	return anagrafeSigeproModificata;
    }

    /**
     * se il codice passato è il codice istat il metodo ritorna una stringa che rapppresenta il codice belfiore,
     * altrimenti ritorna il codice passato
     * 
     * @param codiceComune
     * @return
     */
    private String convertCodiceIstatInCodiceBelfiore(String codiceComune) {

	String _codiceComune = StringUtils.defaultIfEmpty(codiceComune, "X");
	if (StringUtils.isNumeric(_codiceComune)) {
	    log.debug("getAnagrafeSigeproConModifiche# Comune - Codice istat = {}", _codiceComune);
	    Comuni cr = comuniService.findByCodiceIstat(_codiceComune);
	    if (cr != null) {
		log.debug("getAnagrafeSigeproConModifiche# Trasformo il codice istat = {}, nel codice belfiore = {}", _codiceComune,
			cr.getCodicecomune());
		return cr.getCodicecomune();
	    }
	}
	return codiceComune;
    }

    // Il metodo confronta l'anagrafe presente in sigepro con quella ricevuta tramite Ws da un ente terzo.
    // Se un campo è modificato allora dovrà essere modificato sull' oggetto Anagrafe di SiGePro che viene restituito
    // Campi Controllati:
    // - DATI GENERALI
    // - RESIDENZA : Tutti.
    // - CORRISPONDENZA : Tutti.
    // - ALBO (se tecnico): Tutti
    // - DATI NASCITA : Luogo di nascita, Data di nascita.
    // - ALTRI DATI
    private Anagrafe getAnagrafeSigeproConModifiche(it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafeEnteTerzo, Anagrafe anagrafeSigepro,
	    boolean lookupObject) {

	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Inizio controllo differenze tra Anagrafe del DB e Anagrafe recuperata dal servizio WS.....");
	}
	Anagrafe obj = null;
	if (lookupObject) {
	    obj = anagrafeDAO.findById(anagrafeSigepro.getId());
	    anagrafeDAO.evict(obj);
	} else {
	    obj = anagrafeSigepro;
	}
	if (!lookupObject) {
	    obj.setTipologia(anagrafeSigepro.getTipologia());
	    obj.setTipoanagrafe(anagrafeSigepro.getTipoanagrafe());
	}
	// ----------------------------------------DATI GENERALI--------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ---------------------------------------NOMINATIVO---------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza NOMINATIVO ");
	}
	if (anagrafeEnteTerzo.getNOMINATIVO() == null) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getNominativo())) {
		anagrafeEnteTerzo.setNOMINATIVO(anagrafeSigepro.getNominativo().trim());
	    } else {
		anagrafeEnteTerzo.setNOMINATIVO(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getNOMINATIVO(), anagrafeSigepro.getNominativo())) {
		obj.setNominativo(anagrafeEnteTerzo.getNOMINATIVO().trim());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getNOMINATIVO(), anagrafeSigepro.getNominativo())) {
		obj.setNominativo(anagrafeEnteTerzo.getNOMINATIVO().trim());
	    }
	}
	// ---------------------------------------NOME---------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza NOME ");
	}
	if (anagrafeEnteTerzo.getNOME() == null) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getNome())) {
		anagrafeEnteTerzo.setNOME(anagrafeSigepro.getNome());
	    } else {
		anagrafeEnteTerzo.setNOME(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getNOME(), anagrafeSigepro.getNome())) {
		obj.setNome(anagrafeEnteTerzo.getNOME());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getNOME(), anagrafeSigepro.getNome())) {
		obj.setNome(anagrafeEnteTerzo.getNOME());
	    }
	}
	// ---------------------------------------CODICEFISCALE---------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza CODICEFISCALE ");
	}
	if (anagrafeEnteTerzo.getCODICEFISCALE() == null) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getCodicefiscale())) {
		anagrafeEnteTerzo.setCODICEFISCALE(anagrafeSigepro.getCodicefiscale());
	    } else {
		anagrafeEnteTerzo.setCODICEFISCALE(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getCODICEFISCALE(), anagrafeSigepro.getCodicefiscale())) {
		obj.setCodicefiscale(anagrafeEnteTerzo.getCODICEFISCALE());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getCODICEFISCALE(), anagrafeSigepro.getCodicefiscale())) {
		obj.setCodicefiscale(anagrafeEnteTerzo.getCODICEFISCALE());
	    }
	}
	// ---------------------------------------PEC---------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza PEC ");
	}
	if (anagrafeEnteTerzo.getPec() == null) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getPec())) {
		anagrafeEnteTerzo.setPec(anagrafeSigepro.getPec());
	    } else {
		anagrafeEnteTerzo.setPec(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getPec(), anagrafeSigepro.getPec())) {
		obj.setPec(anagrafeEnteTerzo.getPec());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getPec(), anagrafeSigepro.getPec())) {
		obj.setPec(anagrafeEnteTerzo.getPec());
	    }
	}
	// ---------------------------------------SESSO---------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza SESSO ");
	}
	if (anagrafeEnteTerzo.getSESSO() == null) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getSesso())) {
		anagrafeEnteTerzo.setSESSO(anagrafeSigepro.getSesso());
	    } else {
		anagrafeEnteTerzo.setSESSO(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getSESSO(), anagrafeSigepro.getSesso())) {
		obj.setSesso(anagrafeEnteTerzo.getSESSO());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getSESSO(), anagrafeSigepro.getSesso())) {
		obj.setSesso(anagrafeEnteTerzo.getSESSO());
	    }
	}
	// ---------------------------------------PARTITAIVA---------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza PARTITAIVA ");
	}
	if (anagrafeEnteTerzo.getPARTITAIVA() == null) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getPartitaiva())) {
		anagrafeEnteTerzo.setPARTITAIVA(anagrafeSigepro.getPartitaiva());
	    } else {
		anagrafeEnteTerzo.setPARTITAIVA(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getPARTITAIVA(), anagrafeSigepro.getPartitaiva())) {
		obj.setPartitaiva(anagrafeEnteTerzo.getPARTITAIVA());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getPARTITAIVA(), anagrafeSigepro.getPartitaiva())) {
		obj.setPartitaiva(anagrafeEnteTerzo.getPARTITAIVA());
	    }
	}
	// ----------------------------------------CITTADINANZA------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza CITTADINANZA ");
	}
	if (anagrafeSigepro.getCittadinanza() != null && anagrafeSigepro.getCittadinanza().getCodice() != null) {
	    // controllo che quello dall'ente terzo sia diverso da null
	    // se è null o ""
	    // setto la cittadinaza vuota come nuova cittadinanza in quanto sono differenti
	    if (StringUtils.isBlank(anagrafeEnteTerzo.getCODICECITTADINANZA())) {
		obj.setCittadinanza(anagrafeSigepro.getCittadinanza());
		// se quello del sistema terzo è diverso da null, controllo se sono uguali, se si non cambio
		// l'oggetto
		// se sono diversi setto all'oggetto ciitadinanza ricevuto dall'ente terzo
	    } else {
		if (Integer.parseInt(anagrafeEnteTerzo.getCODICECITTADINANZA()) != (Integer) EntityUtils
			.getNestedProperty(anagrafeSigepro.getCittadinanza(), "codice")) {
		    Cittadinanza cittadinanza = cittadinanzaService.findById(Integer.parseInt(anagrafeEnteTerzo.getCODICECITTADINANZA()));
		    obj.setCittadinanza(cittadinanza);
		}
	    }
	    // se la cittadinanza è null (non è settato)
	} else {
	    // controllo se esiste la cittadinanza nell'anagrafe dell'ente terzo
	    // se si lo setto all'oggetto , se non esiste significa che sono uguali (non sono settati) e
	    // non faccio nulla
	    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getCODICECITTADINANZA())) {
		Cittadinanza cittadinanza = cittadinanzaService.findById(Integer.parseInt(anagrafeEnteTerzo.getCODICECITTADINANZA()));
		obj.setCittadinanza(cittadinanza);
	    }
	}
	// ----------------------------------------TITOLO------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza TITOLO ");
	}
	if (anagrafeSigepro.getTitolo() != null && anagrafeSigepro.getTitolo().getId().getCodice() != null) {
	    // controllo che quello dall'ente terzo sia diverso da null
	    // se è null o ""
	    // setto il titolo è vuoto come nuovo titolo in quanto sono differenti
	    if (StringUtils.isBlank(anagrafeEnteTerzo.getTITOLO())) {
		obj.setTitolo(anagrafeSigepro.getTitolo());
		// se quello del sistema terzo è diverso da null, controllo se sono uguali, se si non cambio
		// l'oggetto
		// se sono diversi setto all'oggetto titolo quello ricevuto dall'ente terzo
	    } else {
		if (Integer.parseInt(anagrafeEnteTerzo.getTITOLO()) != anagrafeSigepro.getTitolo().getId().getCodice()) {
		    Titoli titolo = titoliService.findById(new PkId(Integer.parseInt(anagrafeEnteTerzo.getTITOLO())));
		    obj.setTitolo(titolo);
		}
	    }
	    // se titolo è null (non è settato)
	} else {
	    // controllo se esiste il titolo nell'anagrafe dell'ente terzo
	    // se si lo setto all'oggetto , se non esiste significa che sono uguali (non sono settati) e
	    // non faccio nulla
	    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getTITOLO())) {
		Titoli titolo = titoliService.findById(new PkId(Integer.parseInt(anagrafeEnteTerzo.getTITOLO())));
		obj.setTitolo(titolo);
	    }
	}
	// ----------------------------------------TIPOLOGIA------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza TIPOLOGIA ");
	}
	if (anagrafeSigepro.getTipologia() != null) {
	    // controllo che quello dall'ente terzo sia diverso da null
	    // se è null o ""
	    // setto il titolo è vuoto come nuovo titolo in quanto sono differenti
	    if (StringUtils.isBlank(anagrafeEnteTerzo.getTIPOLOGIA())) {
		obj.setTipologia(anagrafeSigepro.getTipologia());
		// se quello del sistema terzo è diverso da null, controllo se sono uguali, se si non cambio
		// l'oggetto
		// se sono diversi setto all'oggetto titolo quello ricevuto dall'ente terzo
	    } else {
		if (Integer.parseInt(anagrafeEnteTerzo.getTIPOLOGIA()) != anagrafeSigepro.getTipologia()) {
		    obj.setTipologia(Integer.parseInt(anagrafeEnteTerzo.getTIPOLOGIA()));
		}
	    }
	    // se titolo è null (non è settato)
	} else {
	    // controllo se esiste il titolo nell'anagrafe dell'ente terzo
	    // se si lo setto all'oggetto , se non esiste significa che sono uguali (non sono settati) e
	    // non faccio nulla
	    // se è null è come dire che non è tecnico quindo lo setto a zero
	    anagrafeSigepro.setTipologia(0);
	    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getTIPOLOGIA())
		    && anagrafeSigepro.getTipologia() != Integer.parseInt(anagrafeEnteTerzo.getTIPOLOGIA())) {
		obj.setTipologia(Integer.parseInt(anagrafeEnteTerzo.getTIPOLOGIA()));
	    } else {
		obj.setTipologia(anagrafeSigepro.getTipologia());
	    }
	}
	// ----------------------------------------FORMA GIURIDICA------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza FORMA GIURIDICA ");
	}
	/**
	 * String _codiceBelfioreRegTrib = convertCodiceIstatInCodiceBelfiore(anagrafeEnteTerzo.getCODCOMREGTRIB());
	 * anagrafeEnteTerzo.setCODCOMREGTRIB(_codiceBelfioreRegTrib);
	 */
	if (StringUtils.isBlank(anagrafeEnteTerzo.getFORMAGIURIDICA())) {
	    log.debug("getAnagrafeSigeproConModifiche# Forma giuridica vuoto.");
	    if (anagrafeEnteTerzo.getFormaGiuridicaClass() != null
		    && StringUtils.isNotBlank(anagrafeEnteTerzo.getFormaGiuridicaClass().getCODICECCIAA())) {
		log.debug("getAnagrafeSigeproConModifiche# verifico se popolato Formagiuridica.codiceccia, valore = {}",
			anagrafeEnteTerzo.getFormaGiuridicaClass().getCODICECCIAA());
		Formegiuridiche fg = formegiuridicheService
			.findByCodiceRiFormegiuridiche(anagrafeEnteTerzo.getFormaGiuridicaClass().getCODICECCIAA());
		if (EntityUtils.getNestedProperty(fg, "id.codice") != null) {
		    log.debug("getAnagrafeSigeproConModifiche# Forma giuridica trovata. valore = {} ", fg.getFormagiuridica());
		    anagrafeEnteTerzo.setFORMAGIURIDICA(fg.getId().getCodice().toString());
		}
	    }
	}
	if (anagrafeSigepro.getFormagiuridica() != null && anagrafeSigepro.getFormagiuridica().getId().getCodice() != null) {
	    // controllo che quello dall'ente terzo sia diverso da null
	    // se è null o ""
	    // setto il titolo è vuoto come nuovo titolo in quanto sono differenti
	    if (StringUtils.isBlank(anagrafeEnteTerzo.getFORMAGIURIDICA())) {
		obj.setFormagiuridica(anagrafeSigepro.getFormagiuridica());
		// se quello del sistema terzo è diverso da null, controllo se sono uguali, se si non cambio
		// l'oggetto
		// se sono diversi setto all'oggetto titolo quello ricevuto dall'ente terzo
	    } else {
		if (Integer.parseInt(anagrafeEnteTerzo.getFORMAGIURIDICA()) != anagrafeSigepro.getFormagiuridica().getId().getCodice()) {
		    Formegiuridiche formegiuridiche = formegiuridicheService
			    .findById(new PkId(Integer.parseInt(anagrafeEnteTerzo.getFORMAGIURIDICA())));
		    obj.setFormagiuridica(formegiuridiche);
		}
	    }
	    // se titolo è null (non è settato)
	} else {
	    // controllo se esiste il titolo nell'anagrafe dell'ente terzo
	    // se si lo setto all'oggetto , se non esiste significa che sono uguali (non sono settati) e
	    // non faccio nulla
	    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getFORMAGIURIDICA())) {
		Formegiuridiche formegiuridiche = formegiuridicheService.findById(new PkId(Integer.parseInt(anagrafeEnteTerzo.getFORMAGIURIDICA())));
		obj.setFormagiuridica(formegiuridiche);
	    }
	}
	// ------------------------------------------FLAG NO PROFIT--------------------------------------------------
	//	anagrafeEnteTerzo.setFLAG_NOPROFIT("0");
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza FLAG NO PROFIT ");
	}
	anagrafeEnteTerzo.setFLAGNOPROFIT("0");
	if (anagrafeSigepro.getFlagNoprofit() != null) {
	    // se è diverso da null quello dell'ente terzo allora lo confronto con quello sul db
	    if (!StringUtils.isBlank(anagrafeEnteTerzo.getFLAGNOPROFIT())) {
		if (Boolean.valueOf(anagrafeEnteTerzo.getFLAGNOPROFIT()) != anagrafeSigepro.getFlagNoprofit()) {
		    Boolean value = (anagrafeEnteTerzo.getFLAGNOPROFIT().equals("1") ? true : false);
		    obj.setFlagNoprofit(value);
		}
	    }
	    // se il flag è null (non è settato)
	} else {
	    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getFLAGNOPROFIT())) {
		Boolean value = (anagrafeEnteTerzo.getFLAGNOPROFIT().equals("1") ? true : false);
		obj.setFlagNoprofit(value);
	    }
	}
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	// ----------------------------------------CAMPI RESIDENZA--------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	// --------------------------------------INDIRIZZO---------------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza INDIRIZZO RESIDENZA ");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getINDIRIZZO())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getIndirizzo())) {
		anagrafeEnteTerzo.setINDIRIZZO(anagrafeSigepro.getIndirizzo());
	    } else {
		anagrafeEnteTerzo.setINDIRIZZO(anagrafeSigepro.getIndirizzo());
		//anagrafeEnteTerzo.setINDIRIZZO(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getINDIRIZZO(), anagrafeSigepro.getIndirizzo())) {
		obj.setIndirizzo(anagrafeEnteTerzo.getINDIRIZZO());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getINDIRIZZO(), anagrafeSigepro.getIndirizzo())) {
		obj.setIndirizzo(anagrafeEnteTerzo.getINDIRIZZO());
	    }
	}
	// ---------------------------------------CAP---------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza CAP RESIDENZA ");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getCAP())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getCap())) {
		anagrafeEnteTerzo.setCAP(anagrafeSigepro.getCap());
	    } else {
		anagrafeEnteTerzo.setCAP(anagrafeSigepro.getCap());
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getCAP(), anagrafeSigepro.getCap())) {
		obj.setCap(anagrafeEnteTerzo.getCAP());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getCAP(), anagrafeSigepro.getCap())) {
		obj.setCap(anagrafeEnteTerzo.getCAP());
	    }
	}
	// ---------------------------------------CITTA----------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza CITTA RESIDENZA ");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getCITTA())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getCitta())) {
		anagrafeEnteTerzo.setCITTA(anagrafeSigepro.getCitta());
	    } else {
		anagrafeEnteTerzo.setCITTA(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getCITTA(), anagrafeSigepro.getCitta())) {
		obj.setCitta(anagrafeEnteTerzo.getCITTA());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getCITTA(), anagrafeSigepro.getCitta())) {
		obj.setCitta(anagrafeEnteTerzo.getCITTA());
	    }
	}
	// --------------------------------------PROVINCIA------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza PROVINCIA RESIDENZA ");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getPROVINCIA())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getProvincia())) {
		anagrafeEnteTerzo.setPROVINCIA(anagrafeSigepro.getProvincia());
	    } else {
		anagrafeEnteTerzo.setPROVINCIA(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getPROVINCIA(), anagrafeSigepro.getProvincia())) {
		obj.setProvincia(anagrafeEnteTerzo.getPROVINCIA());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getPROVINCIA(), anagrafeSigepro.getProvincia())) {
		obj.setProvincia(anagrafeEnteTerzo.getPROVINCIA());
	    }
	}
	// -------------------------------------COMUNE--------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza COMUNE RESIDENZA  ");
	}
	//	// se il cumune di residenza è settato
	//	// se è vuoto metto codiceComuneResidenza uguale ad x in modo che il sistema lo riconosca come alfanumerico
	//	// e non applihi la ricerca per codice istat
	//	String codiceComuneResidenza = StringUtils.defaultIfEmpty(anagrafeEnteTerzo.getCOMUNERESIDENZA(), "X");
	//	if (StringUtils.isNumeric(codiceComuneResidenza)) {
	//	    log.debug("getAnagrafeSigeproConModifiche# Comune Residenza - Codice istat = {}", anagrafeEnteTerzo.getCOMUNERESIDENZA());
	//	    Comuni cr = comuniService.findByCodiceIstat(anagrafeEnteTerzo.getCOMUNERESIDENZA());
	//	    if (cr != null) {
	//		log.debug("getAnagrafeSigeproConModifiche# Trasformo il codice istat = {}, nel codice belfiore = {}",
	//			anagrafeEnteTerzo.getCOMUNERESIDENZA(), cr.getCodicecomune());
	//		anagrafeEnteTerzo.setCOMUNERESIDENZA(cr.getCodicecomune());
	//	    }
	//	}
	String _codiceBelfiore = convertCodiceIstatInCodiceBelfiore(anagrafeEnteTerzo.getCOMUNERESIDENZA());
	anagrafeEnteTerzo.setCOMUNERESIDENZA(_codiceBelfiore);
	if (anagrafeSigepro.getComuneResidenza() != null) {
	    // controllo che quell dall'ente terzo sia diverso da null
	    // se è null o ""
	    // setto un comune vuoto come nuovo comune in quanto sono differenti
	    if (StringUtils.isBlank(anagrafeEnteTerzo.getCOMUNERESIDENZA())) {
		obj.setComuneResidenza(anagrafeSigepro.getComuneResidenza());
		// se quello del sistema terzo è diverso da null, controllo se sono uguali, se si non cambio l'oggetto
		// se sono diversi setto all'oggetto il comune ricevuto dall'ente terzo
	    } else {
		if (!StringUtils.equals(anagrafeEnteTerzo.getCOMUNERESIDENZA(),
			(String) EntityUtils.getNestedProperty(anagrafeSigepro.getComuneResidenza(), "codicecomune"))) {
		    Comuni comuni = comuniService.findById(anagrafeEnteTerzo.getCOMUNERESIDENZA());
		    obj.setComuneResidenza(comuni);
		}
	    }
	    // se il comune di residenza è null (non è settato)
	} else {
	    // controllo se esiste il cumune nell'anagrafe dell'ente terzo
	    // se si lo setto all'oggetto , se non esiste significa che sono uguali (non sono settati) e
	    // non faccio nulla
	    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getCOMUNERESIDENZA())) {
		Comuni comuni = comuniService.findById(anagrafeEnteTerzo.getCOMUNERESIDENZA());
		obj.setComuneResidenza(comuni);
	    }
	}
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------CONTROLLO CAMPI CORRISPONDENZA------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------INDIRIZZO-------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza INDIRIZZO CORRISPONDENZA ");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getINDIRIZZOCORRISPONDENZA())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getIndirizzocorrispondenza())) {
		anagrafeEnteTerzo.setINDIRIZZOCORRISPONDENZA(anagrafeSigepro.getIndirizzocorrispondenza());
	    } else {
		anagrafeEnteTerzo.setINDIRIZZOCORRISPONDENZA(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getINDIRIZZOCORRISPONDENZA(), anagrafeSigepro.getIndirizzocorrispondenza())) {
		obj.setIndirizzocorrispondenza(anagrafeEnteTerzo.getINDIRIZZOCORRISPONDENZA());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getINDIRIZZOCORRISPONDENZA(), anagrafeSigepro.getIndirizzocorrispondenza())) {
		obj.setIndirizzocorrispondenza(anagrafeEnteTerzo.getINDIRIZZOCORRISPONDENZA());
	    }
	}
	// ----------------------------------------CAP---------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza CAP CORRISPONDENZA ");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getCAPCORRISPONDENZA())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getCapcorrispondenza())) {
		anagrafeEnteTerzo.setCAPCORRISPONDENZA(anagrafeSigepro.getCapcorrispondenza());
	    } else {
		anagrafeEnteTerzo.setCAPCORRISPONDENZA(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getCAPCORRISPONDENZA(), anagrafeSigepro.getCapcorrispondenza())) {
		obj.setCapcorrispondenza(anagrafeEnteTerzo.getCAPCORRISPONDENZA());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getCAPCORRISPONDENZA(), anagrafeSigepro.getCapcorrispondenza())) {
		obj.setCapcorrispondenza(anagrafeEnteTerzo.getCAPCORRISPONDENZA());
	    }
	}
	// -----------------------------------------CITTA----------------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza CITTA CORRISPONDENZA ");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getCITTACORRISPONDENZA())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getCittacorrispondenza())) {
		anagrafeEnteTerzo.setCITTACORRISPONDENZA(anagrafeSigepro.getCittacorrispondenza());
	    } else {
		anagrafeEnteTerzo.setCITTACORRISPONDENZA(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getCITTACORRISPONDENZA(), anagrafeSigepro.getCittacorrispondenza())) {
		obj.setCittacorrispondenza(anagrafeEnteTerzo.getCITTACORRISPONDENZA());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getCITTACORRISPONDENZA(), anagrafeSigepro.getCittacorrispondenza())) {
		obj.setCittacorrispondenza(anagrafeEnteTerzo.getCITTACORRISPONDENZA());
	    }
	}
	// --------------------------------------PROVINCIA---------------------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza PROVINCIA CORRISPONDENZA ");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getPROVINCIACORRISPONDENZA())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getProvinciacorrispondenza())) {
		anagrafeEnteTerzo.setPROVINCIACORRISPONDENZA(anagrafeSigepro.getProvinciacorrispondenza());
	    } else {
		anagrafeEnteTerzo.setPROVINCIACORRISPONDENZA(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getPROVINCIACORRISPONDENZA(), anagrafeSigepro.getProvinciacorrispondenza())) {
		obj.setProvinciacorrispondenza(anagrafeEnteTerzo.getPROVINCIACORRISPONDENZA());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getPROVINCIACORRISPONDENZA(), anagrafeSigepro.getProvinciacorrispondenza())) {
		obj.setProvinciacorrispondenza(anagrafeEnteTerzo.getPROVINCIACORRISPONDENZA());
	    }
	}
	// -----------------------------------------COMUNE---------------------------------------------------------------
	// se il cumune di corrispondenza è settato
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza COMUNE CORRISPONDENZA ");
	}
	String _codiceBelfioreCorrispondenza = convertCodiceIstatInCodiceBelfiore(anagrafeEnteTerzo.getCOMUNECORRISPONDENZA());
	anagrafeEnteTerzo.setCOMUNECORRISPONDENZA(_codiceBelfioreCorrispondenza);
	if (anagrafeSigepro.getComunecorrispondenza() != null) {
	    // controllo che quell dall'ente terzo sia diverso da null
	    // se è null o ""
	    // setto un comune vuoto come nuovo comune in quanto sono differenti
	    if (StringUtils.isBlank(anagrafeEnteTerzo.getCOMUNECORRISPONDENZA())) {
		obj.setComunecorrispondenza(anagrafeSigepro.getComunecorrispondenza());
		// se quello del sistema terzo è diverso da null, controllo se sono uguali, se si non cambio l'oggetto
		// se sono diversi setto all'oggetto il comune ricevuto dall'ente terzo
	    } else {
		if (!StringUtils.equals(anagrafeEnteTerzo.getCOMUNECORRISPONDENZA(),
			(String) EntityUtils.getNestedProperty(anagrafeSigepro.getComunecorrispondenza(), "codicecomune"))) {
		    Comuni comuni = comuniService.findById(anagrafeEnteTerzo.getCOMUNECORRISPONDENZA());
		    obj.setComunecorrispondenza(comuni);
		}
	    }
	    // se il comune di residenza è null (non è settato)
	} else {
	    // controllo se esiste il cumune nell'anagrafe dell'ente terzo
	    // se si lo setto all'oggetto , se non esiste significa che sono uguali (non sono settati) e
	    // non faccio nulla
	    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getCOMUNECORRISPONDENZA())) {
		Comuni comuni = comuniService.findById(anagrafeEnteTerzo.getCOMUNECORRISPONDENZA());
		obj.setComunecorrispondenza(comuni);
	    }
	}
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ---------------------------------------- CONTROLLO DATI NASCITA------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// -----------------------------------------DATA NASCITA--------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza DATA NASCITA ");
	}
	if (anagrafeEnteTerzo.getDATANASCITA() != null) {
	    if (anagrafeSigepro.getDatanascita() == null
		    || Utilities.compareDates(anagrafeSigepro.getDatanascita(), anagrafeEnteTerzo.getDATANASCITA().toGregorianCalendar()) == 0) {
		Date data_nascita = Utilities.createDate(anagrafeEnteTerzo.getDATANASCITA());
		obj.setDatanascita(data_nascita);
	    }
	} else {
	    if (anagrafeSigepro.getDatanascita() != null) {
		obj.setDatanascita(anagrafeSigepro.getDatanascita());
	    }
	}
	// -----------------------------------------DATA COSTITUZIONE--------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza DATA COSTITUZIONE ");
	}
	if (anagrafeEnteTerzo.getDATANOMINATIVO() != null) {
	    if (anagrafeSigepro.getDatanominativo() == null || Utilities.compareDates(anagrafeSigepro.getDatanominativo(),
		    anagrafeEnteTerzo.getDATANOMINATIVO().toGregorianCalendar()) == 0) {
		Date data_nominativo = Utilities.createDate(anagrafeEnteTerzo.getDATANOMINATIVO());
		obj.setDatanominativo(data_nominativo);
	    }
	} else {
	    if (anagrafeSigepro.getDatanominativo() != null) {
		obj.setDatanominativo(anagrafeSigepro.getDatanominativo());
	    }
	}
	// -----------------------------------------DATA INIZIO ATTIVITA'--------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza DATA INIZIO ATTIVITA' ");
	}
	if (anagrafeEnteTerzo.getDataInizioAttivita() != null) {
	    if (anagrafeSigepro.getDataInizioAttivita() == null || Utilities.compareDates(anagrafeSigepro.getDataInizioAttivita(),
		    anagrafeEnteTerzo.getDataInizioAttivita().toGregorianCalendar()) == 0) {
		Date data_inizio_attivita = Utilities.createDate(anagrafeEnteTerzo.getDataInizioAttivita());
		obj.setDataInizioAttivita(data_inizio_attivita);
	    }
	} else {
	    if (anagrafeSigepro.getDataInizioAttivita() != null) {
		obj.setDataInizioAttivita(anagrafeSigepro.getDataInizioAttivita());
	    }
	}
	// -----------------------------------------COMUNE-------------------.------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza COMUNE ");
	}
	String _codiceBelfiorenascita = convertCodiceIstatInCodiceBelfiore(anagrafeEnteTerzo.getCODCOMNASCITA());
	anagrafeEnteTerzo.setCODCOMNASCITA(_codiceBelfiorenascita);
	// se il cumune di nascita è settato
	if (anagrafeSigepro.getComuneNascita() != null) {
	    // controllo che quell dall'ente terzo sia diverso da null
	    // se è null o ""
	    // setto un comune vuoto come nuovo comune in quanto sono differenti
	    if (StringUtils.isBlank(anagrafeEnteTerzo.getCODCOMNASCITA())) {
		obj.setComuneNascita(anagrafeSigepro.getComuneNascita());
		// se quello del sistema terzo è diverso da null, controllo se sono uguali, se si non cambio l'oggetto
		// se sono diversi setto all'oggetto il comune ricevuto dall'ente terzo
	    } else {
		if (!StringUtils.equals(anagrafeEnteTerzo.getCODCOMNASCITA(),
			(String) EntityUtils.getNestedProperty(anagrafeSigepro.getComuneNascita(), "codicecomune"))) {
		    Comuni comuni = comuniService.findById(anagrafeEnteTerzo.getCODCOMNASCITA());
		    obj.setComuneNascita(comuni);
		}
	    }
	    // se il comune di nascita è null (non è settato)
	} else {
	    // controllo se esiste il cumune nell'anagrafe dell'ente terzo
	    // se si lo setto all'oggetto , se non esiste significa che sono uguali (non sono settati) e
	    // non faccio nulla
	    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getCODCOMNASCITA())) {
		Comuni comuni = comuniService.findById(anagrafeEnteTerzo.getCODCOMNASCITA());
		obj.setComuneNascita(comuni);
	    }
	}
	// --------------------------------------REGDITTE-----------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza REGDITTE ");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getREGDITTE())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getRegditte())) {
		anagrafeEnteTerzo.setREGDITTE(anagrafeSigepro.getRegditte());
	    } else {
		anagrafeEnteTerzo.setREGDITTE(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getREGDITTE(), anagrafeSigepro.getRegditte())) {
		obj.setRegditte(anagrafeEnteTerzo.getREGDITTE());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getREGDITTE(), anagrafeSigepro.getRegditte())) {
		obj.setRegditte(anagrafeEnteTerzo.getREGDITTE());
	    }
	}
	// ---------------------------------DATA REG-----------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza DATA REG ");
	}
	if (anagrafeEnteTerzo.getDATAREGDITTE() != null) {
	    if (anagrafeSigepro.getDataregditte() == null
		    || Utilities.compareDates(anagrafeSigepro.getDataregditte(), anagrafeEnteTerzo.getDATAREGDITTE().toGregorianCalendar()) == 0) {
		Date data_reg_ditte = Utilities.createDate(anagrafeEnteTerzo.getDATAREGDITTE());
		obj.setDataregditte(data_reg_ditte);
	    }
	} else {
	    if (anagrafeSigepro.getDataregditte() != null) {
		obj.setDataregditte(anagrafeSigepro.getDataregditte());
	    }
	}
	// ------------------------------------COMUNE REG-------------------------------------------------
	// se il cumune di regditte è settato
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza COMUNE REG ");
	}
	String _codiceBelfioreRegDitte = convertCodiceIstatInCodiceBelfiore(anagrafeEnteTerzo.getCODCOMREGDITTE());
	anagrafeEnteTerzo.setCODCOMREGDITTE(_codiceBelfioreRegDitte);
	if (anagrafeSigepro.getComunecomregditte() != null) {
	    // controllo che quell dall'ente terzo sia diverso da null
	    // se è null o ""
	    // setto un comune vuoto come nuovo comune in quanto sono differenti
	    if (StringUtils.isBlank(anagrafeEnteTerzo.getCODCOMREGDITTE())) {
		obj.setComunecomregditte(anagrafeSigepro.getComunecomregditte());
		// se quello del sistema terzo è diverso da null, controllo se sono uguali, se si non cambio l'oggetto
		// se sono diversi setto all'oggetto il comune ricevuto dall'ente terzo
	    } else {
		if (!StringUtils.equals(anagrafeEnteTerzo.getCODCOMREGDITTE(),
			(String) EntityUtils.getNestedProperty(anagrafeSigepro.getComunecomregditte(), "codicecomune"))) {
		    Comuni comuni = comuniService.findById(anagrafeEnteTerzo.getCODCOMREGDITTE());
		    obj.setComunecomregditte(comuni);
		}
	    }
	    // se il comune di regditte è null (non è settato)
	} else {
	    // controllo se esiste il regditte nell'anagrafe dell'ente terzo
	    // se si lo setto all'oggetto , se non esiste significa che sono uguali (non sono settati) e
	    // non faccio nulla
	    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getCODCOMREGDITTE())) {
		Comuni comuni = comuniService.findById(anagrafeEnteTerzo.getCODCOMREGDITTE());
		obj.setComunecomregditte(comuni);
	    }
	}
	// ------------------------------------ REGTRIB-------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza REGTRIB ");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getREGTRIB())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getRegtrib())) {
		anagrafeEnteTerzo.setREGTRIB(anagrafeSigepro.getRegtrib());
	    } else {
		anagrafeEnteTerzo.setREGTRIB(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getREGTRIB(), anagrafeSigepro.getRegtrib())) {
		obj.setRegtrib(anagrafeEnteTerzo.getREGTRIB());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getREGTRIB(), anagrafeSigepro.getRegtrib())) {
		obj.setRegtrib(anagrafeEnteTerzo.getREGTRIB());
	    }
	}
	// ------------------------------------ DATA -------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza DATA ");
	}
	if (anagrafeEnteTerzo.getDATAREGTRIB() != null) {
	    if (anagrafeSigepro.getDataregtrib() == null
		    || Utilities.compareDates(anagrafeSigepro.getDataregtrib(), anagrafeEnteTerzo.getDATAREGTRIB().toGregorianCalendar()) == 0) {
		Date data_reg_trib = Utilities.createDate(anagrafeEnteTerzo.getDATAREGTRIB());
		obj.setDataregtrib(data_reg_trib);
	    }
	} else {
	    if (anagrafeSigepro.getDataregtrib() != null) {
		obj.setDataregtrib(anagrafeSigepro.getDataregtrib());
	    }
	}
	// ------------------------------------COMUNE REGTRIB-------------------------------------------------
	// se il comune di regditte è settato
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza COMUNE REGTRIB ");
	}
	String _codiceBelfioreRegTrib = convertCodiceIstatInCodiceBelfiore(anagrafeEnteTerzo.getCODCOMREGTRIB());
	anagrafeEnteTerzo.setCODCOMREGTRIB(_codiceBelfioreRegTrib);
	if (anagrafeSigepro.getComuneregtrib() != null) {
	    // controllo che quell dall'ente terzo sia diverso da null
	    // se è null o ""
	    // setto un comune vuoto come nuovo comune in quanto sono differenti
	    if (StringUtils.isBlank(anagrafeEnteTerzo.getCODCOMREGTRIB())) {
		obj.setComuneregtrib(anagrafeSigepro.getComuneregtrib());
		// se quello del sistema terzo è diverso da null, controllo se sono uguali, se si non cambio l'oggetto
		// se sono diversi setto all'oggetto il comune ricevuto dall'ente terzo
	    } else {
		if (!StringUtils.equals(anagrafeEnteTerzo.getCODCOMREGTRIB(),
			(String) EntityUtils.getNestedProperty(anagrafeSigepro.getComuneregtrib(), "codicecomune"))) {
		    Comuni comuni = comuniService.findById(anagrafeEnteTerzo.getCODCOMREGTRIB());
		    obj.setComuneregtrib(comuni);
		}
	    }
	    // se il comune di regditte è null (non è settato)
	} else {
	    // controllo se esiste il regditte nell'anagrafe dell'ente terzo
	    // se si lo setto all'oggetto , se non esiste significa che sono uguali (non sono settati) e
	    // non faccio nulla
	    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getCODCOMREGTRIB())) {
		Comuni comuni = comuniService.findById(anagrafeEnteTerzo.getCODCOMREGTRIB());
		obj.setComuneregtrib(comuni);
	    }
	}
	// ------------------------------------PROVINCIA REA-------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza PROVINCIA REA ");
	}
	if (anagrafeSigepro.getProvinciarea() != null) {
	    // controllo che quell dall'ente terzo sia diverso da null
	    // se è null o ""
	    // setto provincia vuota come nuova provincia in quanto sono differenti
	    if (StringUtils.isBlank(anagrafeEnteTerzo.getPROVINCIAREA())) {
		obj.setProvinciarea(anagrafeSigepro.getProvinciarea());
		// se quello del sistema terzo è diverso da null, controllo se sono uguali, se si non cambio
		// l'oggetto
		// se sono diversi setto all'oggetto provincia ricevuto dall'ente terzo
	    } else {
		if (!StringUtils.equals(anagrafeEnteTerzo.getPROVINCIAREA(), anagrafeSigepro.getProvinciarea())) {
		    obj.setProvinciarea(anagrafeEnteTerzo.getPROVINCIAREA());
		}
	    }
	    // se la provincia è null (non è settato)
	} else {
	    // controllo se esiste la provincia nell'anagrafe dell'ente terzo
	    // se si lo setto all'oggetto , se non esiste significa che sono uguali (non sono settati) e
	    // non faccio nulla
	    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getPROVINCIAREA())) {
		obj.setProvinciarea(anagrafeEnteTerzo.getPROVINCIAREA());
	    }
	}
	// ------------------------------------DATA REA-------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza DATA REA ");
	}
	if (anagrafeEnteTerzo.getDATAISCRREA() != null) {
	    if (anagrafeSigepro.getDataiscrrea() == null
		    || Utilities.compareDates(anagrafeSigepro.getDataiscrrea(), anagrafeEnteTerzo.getDATAISCRREA().toGregorianCalendar()) == 0) {
		Date data_isc_rea = Utilities.createDate(anagrafeEnteTerzo.getDATAISCRREA());
		obj.setDataiscrrea(data_isc_rea);
	    }
	} else {
	    if (anagrafeSigepro.getDataiscrrea() != null) {
		obj.setDataiscrrea(anagrafeSigepro.getDataiscrrea());
	    }
	}
	// ------------------------------------NUMERO REA-------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza NUMERO REA ");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getNUMISCRREA())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getNumiscrrea())) {
		anagrafeEnteTerzo.setNUMISCRREA(anagrafeSigepro.getNumiscrrea());
	    } else {
		anagrafeEnteTerzo.setNUMISCRREA(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getNUMISCRREA(), anagrafeSigepro.getNumiscrrea())) {
		obj.setNumiscrrea(anagrafeEnteTerzo.getNUMISCRREA());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getNUMISCRREA(), anagrafeSigepro.getNumiscrrea())) {
		obj.setNumiscrrea(anagrafeEnteTerzo.getNUMISCRREA());
	    }
	}
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ---------------------------------------- CONTROLLO DATI ALBO----------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// Il controllo va fatto solo è un tecnico
	// ---------------------------------------NUMERO ALBO-------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza NUMERO ALBO ");
	}
	if (anagrafeSigepro.getTipologia() == -1) {
	    if (StringUtils.isBlank(anagrafeEnteTerzo.getNUMEROELENCOPRO())) {
		if (StringUtils.isNotBlank(anagrafeSigepro.getNumeroelencopro())) {
		    anagrafeEnteTerzo.setNUMEROELENCOPRO(anagrafeSigepro.getNumeroelencopro());
		} else {
		    anagrafeEnteTerzo.setNUMEROELENCOPRO(null);
		}
		if (!StringUtils.equals(anagrafeEnteTerzo.getNUMEROELENCOPRO(), anagrafeSigepro.getNumeroelencopro())) {
		    obj.setNumeroelencopro(anagrafeEnteTerzo.getNUMEROELENCOPRO());
		}
	    } else {
		if (!StringUtils.equals(anagrafeEnteTerzo.getEMAIL(), anagrafeSigepro.getNumeroelencopro())) {
		    obj.setNumeroelencopro(anagrafeEnteTerzo.getNUMEROELENCOPRO());
		}
	    }
	    // -------------------------------------PROVINCIA ALBO-----------------------------------------------------
	    if (log.isDebugEnabled()) {
		log.debug("getAnagrafeSigeproConModifiche#Controllo differenza PROVINCIA ALBO ");
	    }
	    // se la provincia dell'albo è settata
	    if (anagrafeSigepro.getProvinciaelencopro() != null) {
		// controllo che quell dall'ente terzo sia diverso da null
		// se è null o ""
		// setto provincia vuota come nuova provincia in quanto sono differenti
		if (StringUtils.isBlank(anagrafeEnteTerzo.getPROVINCIAELENCOPRO())) {
		    obj.setProvinciaelencopro(anagrafeSigepro.getProvinciaelencopro());
		    // se quello del sistema terzo è diverso da null, controllo se sono uguali, se si non cambio
		    // l'oggetto
		    // se sono diversi setto all'oggetto provincia ricevuto dall'ente terzo
		} else {
		    if (!StringUtils.equals(anagrafeEnteTerzo.getPROVINCIAELENCOPRO(), anagrafeSigepro.getProvinciaelencopro())) {
			obj.setProvinciaelencopro(anagrafeEnteTerzo.getPROVINCIAELENCOPRO());
		    }
		}
		// se la provincia è null (non è settato)
	    } else {
		// controllo se esiste la provincia nell'anagrafe dell'ente terzo
		// se si lo setto all'oggetto , se non esiste significa che sono uguali (non sono settati) e
		// non faccio nulla
		if (StringUtils.isNotBlank(anagrafeEnteTerzo.getPROVINCIAELENCOPRO())) {
		    obj.setProvinciaelencopro(anagrafeEnteTerzo.getPROVINCIAELENCOPRO());
		}
	    }
	    // se l'albo è settato
	    // ----------------------------------------ALBO-------------------------------------------------
	    if (log.isDebugEnabled()) {
		log.debug("getAnagrafeSigeproConModifiche#Controllo differenza ALBO ");
	    }
	    if (anagrafeSigepro.getElenchiprofessionalibase() != null && anagrafeSigepro.getElenchiprofessionalibase().getId() != null) {
		// controllo che quello dall'ente terzo sia diverso da null
		// se è null o ""
		// setto l'albo vuoto come nuovo albo in quanto sono differenti
		if (StringUtils.isBlank(anagrafeEnteTerzo.getCODICEELENCOPRO())) {
		    obj.setElenchiprofessionalibase(anagrafeSigepro.getElenchiprofessionalibase());
		    // se quello del sistema terzo è diverso da null, controllo se sono uguali, se si non cambio
		    // l'oggetto
		    // se sono diversi setto all'oggetto albo ricevuto dall'ente terzo
		} else {
		    if (Integer.parseInt(anagrafeEnteTerzo.getCODICEELENCOPRO()) != anagrafeSigepro.getElenchiprofessionalibase().getId()) {
			Elenchiprofessionalibase albo = elenchiprofessionalibaseService
				.findById(Integer.parseInt(anagrafeEnteTerzo.getCODICEELENCOPRO()));
			obj.setElenchiprofessionalibase(albo);
		    }
		}
		// se l'albo è null (non è settato)
	    } else {
		// controllo se esiste l'albo nell'anagrafe dell'ente terzo
		// se si lo setto all'oggetto , se non esiste significa che sono uguali (non sono settati) e
		// non faccio nulla
		if (StringUtils.isNotBlank(anagrafeEnteTerzo.getCODICEELENCOPRO())) {
		    Elenchiprofessionalibase albo = elenchiprofessionalibaseService
			    .findById(Integer.parseInt(anagrafeEnteTerzo.getCODICEELENCOPRO()));
		    obj.setElenchiprofessionalibase(albo);
		}
	    }
	}
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------CONTROLLO ALTRI DATI---------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// ------------------------------------------TELEFONO---------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza TELEFONO ");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getTELEFONO())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getTelefono())) {
		anagrafeEnteTerzo.setTELEFONO(anagrafeSigepro.getTelefono());
	    } else {
		anagrafeEnteTerzo.setTELEFONO(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getTELEFONO(), anagrafeSigepro.getTelefono())) {
		obj.setTelefono(anagrafeEnteTerzo.getTELEFONO());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getTELEFONO(), anagrafeSigepro.getTelefono())) {
		obj.setTelefono(anagrafeEnteTerzo.getTELEFONO());
	    }
	}
	// -------------------------------------CELLULARE---------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza CELLULARE ");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getTELEFONOCELLULARE())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getTelefonocellulare())) {
		anagrafeEnteTerzo.setTELEFONOCELLULARE(anagrafeSigepro.getTelefonocellulare());
	    } else {
		anagrafeEnteTerzo.setTELEFONOCELLULARE(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getTELEFONOCELLULARE(), anagrafeSigepro.getTelefonocellulare())) {
		obj.setTelefonocellulare(anagrafeEnteTerzo.getTELEFONOCELLULARE());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getTELEFONOCELLULARE(), anagrafeSigepro.getTelefonocellulare())) {
		obj.setTelefonocellulare(anagrafeEnteTerzo.getTELEFONOCELLULARE());
	    }
	}
	// -----------------------------------------FAX-------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza FAX ");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getFAX())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getFax())) {
		anagrafeEnteTerzo.setFAX(anagrafeSigepro.getFax());
	    } else {
		anagrafeEnteTerzo.setFAX(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getFAX(), anagrafeSigepro.getFax())) {
		obj.setFax(anagrafeEnteTerzo.getFAX());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getFAX(), anagrafeSigepro.getFax())) {
		obj.setFax(anagrafeEnteTerzo.getFAX());
	    }
	}
	// ----------------------------------------EMAIL----------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza EMAIL ");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getEMAIL())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getEmail())) {
		anagrafeEnteTerzo.setEMAIL(anagrafeSigepro.getEmail());
	    } else {
		anagrafeEnteTerzo.setEMAIL(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getEMAIL(), anagrafeSigepro.getEmail())) {
		obj.setEmail(anagrafeEnteTerzo.getEMAIL());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getEMAIL(), anagrafeSigepro.getEmail())) {
		obj.setEmail(anagrafeEnteTerzo.getEMAIL());
	    }
	}
	// -------------------------------------------REFERENTE---------------------------------------------------
	//	if (StringUtils.isBlank(anagrafeEnteTerzo.getREFERENTE())) {
	//	    anagrafeEnteTerzo.setREFERENTE("");
	//	    if (!StringUtils.equals(anagrafeEnteTerzo.getREFERENTE(), anagrafeSigepro.getReferente())) {
	//		obj.setReferente(anagrafeEnteTerzo.getREFERENTE());
	//	    }
	//	} else {
	//	    if (!StringUtils.equals(anagrafeEnteTerzo.getREFERENTE(), anagrafeSigepro.getReferente())) {
	//		obj.setReferente(anagrafeEnteTerzo.getREFERENTE());
	//	    }
	//	}
	// ------------------------------------------INVIO MAIL---------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza INVIO MAIL ");
	}
	if (anagrafeSigepro.getInvioemail() != null) {
	    // se è diverso da null quello dell'ente terzo allora lo confronto con quello sul db
	    if (!StringUtils.isBlank(anagrafeEnteTerzo.getINVIOEMAIL())) {
		if (Boolean.valueOf(anagrafeSigepro.getInvioemail()) != anagrafeSigepro.getInvioemail()) {
		    obj.setInvioemail(Boolean.valueOf(anagrafeSigepro.getInvioemail()));
		}
	    }
	    // se titolo è null (non è settato)
	} else {
	    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getINVIOEMAIL())) {
		obj.setInvioemail(Boolean.valueOf(anagrafeEnteTerzo.getINVIOEMAIL()));
	    }
	}
	anagrafeEnteTerzo.setINVIOEMAIL("0");
	if (anagrafeSigepro.getInvioemail() != null) {
	    // se è diverso da null quello dell'ente terzo allora lo confronto con quello sul db
	    if (!StringUtils.isBlank(anagrafeEnteTerzo.getINVIOEMAIL())) {
		if (Boolean.valueOf(anagrafeEnteTerzo.getINVIOEMAIL()) != anagrafeSigepro.getInvioemail()) {
		    Boolean value = (anagrafeEnteTerzo.getINVIOEMAIL().equals("1") ? true : false);
		    obj.setInvioemail(value);
		}
	    }
	    // se il flag è null (non è settato)
	} else {
	    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getINVIOEMAIL())) {
		Boolean value = (anagrafeEnteTerzo.getINVIOEMAIL().equals("1") ? true : false);
		obj.setInvioemail(value);
	    }
	}
	// ------------------------------------------INVIO MAIL TECNICO---------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza INVIO MAIL TECNICO ");
	}
	if (anagrafeSigepro.getInvioemailtec() != null) {
	    // se è diverso da null quello dell'ente terzo allora lo confronto con quello sul db
	    if (!StringUtils.isBlank(anagrafeEnteTerzo.getINVIOEMAILTEC())) {
		if (Boolean.valueOf(anagrafeEnteTerzo.getINVIOEMAILTEC()) != anagrafeSigepro.getInvioemailtec()) {
		    Boolean value = (anagrafeEnteTerzo.getINVIOEMAIL().equals("1") ? true : false);
		    obj.setInvioemailtec(value);
		}
	    }
	    // se il flag è null (non è settato)
	} else {
	    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getINVIOEMAILTEC())) {
		Boolean value = (anagrafeEnteTerzo.getINVIOEMAIL().equals("1") ? true : false);
		obj.setInvioemailtec(value);
	    }
	}
	// ----------------------------------------MATRICOLAINPS----------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza MATRICOLA INPS");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getInpsMatricola())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getInpsMatricola())) {
		anagrafeEnteTerzo.setInpsMatricola(anagrafeSigepro.getInpsMatricola());
	    } else {
		anagrafeEnteTerzo.setInpsMatricola(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getInpsMatricola(), anagrafeSigepro.getInpsMatricola())) {
		obj.setInpsMatricola(anagrafeEnteTerzo.getInpsMatricola());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getInpsMatricola(), anagrafeSigepro.getInpsMatricola())) {
		obj.setInpsMatricola(anagrafeEnteTerzo.getInpsMatricola());
	    }
	}
	// ------------------------------------SEDE INPS-------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza SEDE INPS ");
	}
	if (anagrafeSigepro.getSedeInps() != null) {
	    // controllo che quell dall'ente terzo sia diverso da null
	    // se è null o ""
	    // setto un VALORE vuoto come nuovo SEDE in quanto sono differenti
	    if (anagrafeEnteTerzo.getSedeInps() == null) {
		obj.setSedeInps(anagrafeSigepro.getSedeInps());
		// se quello del sistema terzo è diverso da null, controllo se sono uguali, se si non cambio l'oggetto
		// se sono diversi setto all'oggetto ricevuto dall'ente terzo
	    } else {
		if (anagrafeEnteTerzo.getSedeInps() != null) {
		    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getSedeInps().getCodice())) {
			if (!StringUtils.equals(anagrafeEnteTerzo.getSedeInps().getCodice(),
				(String) EntityUtils.getNestedProperty(anagrafeSigepro.getSedeInps(), "codice"))) {
			    Elencoinpsbase sedeInps = elencoinpsbaseService.findById(anagrafeEnteTerzo.getSedeInps().getCodice());
			    obj.setSedeInps(sedeInps);
			}
		    }
		}
	    }
	    // se il comune di regditte è null (non è settato)
	} else {
	    // controllo se esiste il VALORE nell'anagrafe dell'ente terzo
	    // se si lo setto all'oggetto , se non esiste significa che sono uguali (non sono settati) e
	    // non faccio nulla
	    if (anagrafeEnteTerzo.getSedeInps() != null) {
		if (StringUtils.isNotBlank(anagrafeEnteTerzo.getSedeInps().getCodice())) {
		    Elencoinpsbase sedeInps = elencoinpsbaseService.findById(anagrafeEnteTerzo.getSedeInps().getCodice());
		    obj.setSedeInps(sedeInps);
		}
	    }
	}
	// ----------------------------------------MATRICOLAINAIL----------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza MATRICOLA INAIL");
	}
	if (StringUtils.isBlank(anagrafeEnteTerzo.getInailMatricola())) {
	    if (StringUtils.isNotBlank(anagrafeSigepro.getInailMatricola())) {
		anagrafeEnteTerzo.setInailMatricola(anagrafeSigepro.getInailMatricola());
	    } else {
		anagrafeEnteTerzo.setInailMatricola(null);
	    }
	    if (!StringUtils.equals(anagrafeEnteTerzo.getInailMatricola(), anagrafeSigepro.getInailMatricola())) {
		obj.setInailMatricola(anagrafeEnteTerzo.getInailMatricola());
	    }
	} else {
	    if (!StringUtils.equals(anagrafeEnteTerzo.getInailMatricola(), anagrafeSigepro.getInailMatricola())) {
		obj.setInailMatricola(anagrafeEnteTerzo.getInailMatricola());
	    }
	}
	// ------------------------------------SEDE INAIL-------------------------------------------------
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Controllo differenza SEDE INAIL ");
	}
	if (anagrafeSigepro.getSedeInail() != null) {
	    // controllo che quell dall'ente terzo sia diverso da null
	    // se è null o ""
	    // setto un VALORE vuoto come nuovo SEDE in quanto sono differenti
	    if (anagrafeEnteTerzo.getSedeInail() == null) {
		obj.setSedeInail(anagrafeSigepro.getSedeInail());
		// se quello del sistema terzo è diverso da null, controllo se sono uguali, se si non cambio l'oggetto
		// se sono diversi setto all'oggetto il VALORE ricevuto dall'ente terzo
	    } else {
		if (anagrafeEnteTerzo.getSedeInail() != null) {
		    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getSedeInail().getCodice())) {
			if (!StringUtils.equals(anagrafeEnteTerzo.getSedeInail().getCodice(),
				(String) EntityUtils.getNestedProperty(anagrafeSigepro.getSedeInail(), "codice"))) {
			    Elencoinailbase sedeInail = elencoinailbaseService.findById(anagrafeEnteTerzo.getSedeInail().getCodice());
			    obj.setSedeInail(sedeInail);
			}
		    }
		}
	    }
	    // se il comune di regditte è null (non è settato)
	} else {
	    // controllo se esiste il VALORE nell'anagrafe dell'ente terzo
	    // se si lo setto all'oggetto , se non esiste significa che sono uguali (non sono settati) e
	    // non faccio nulla
	    if (anagrafeEnteTerzo.getSedeInail() != null) {
		if (StringUtils.isNotBlank(anagrafeEnteTerzo.getSedeInail().getCodice())) {
		    Elencoinailbase sedeInail = elencoinailbaseService.findById(anagrafeEnteTerzo.getSedeInail().getCodice());
		    obj.setSedeInail(sedeInail);
		}
	    }
	}
	if (StringUtils.isNotBlank(anagrafeSigepro.getNote())) {
	    if (StringUtils.isNotBlank(anagrafeEnteTerzo.getNOTE()) && !anagrafeSigepro.getNote().contains(anagrafeEnteTerzo.getNOTE())) {
		obj.setNote(anagrafeSigepro.getNote() + " " + StringUtils.defaultIfEmpty(anagrafeEnteTerzo.getNOTE(), ""));
	    }
	} else {
	    obj.setNote(StringUtils.defaultIfEmpty(anagrafeEnteTerzo.getNOTE(), ""));
	}
	if (log.isDebugEnabled()) {
	    log.debug("getAnagrafeSigeproConModifiche#Fine controllo differenze tra Anagrafe del DB e Anagrafe recuperata dal servizio WS.....");
	}
	return obj;
    }

    /**
     * Controlla che sia presente il codice fiscale quando si richiama il ws cerca aggiornamenti da codice fiscale
     * 
     * @param anagrafeSigepro
     * @return
     */
    private boolean isPresentCodiceFiscale(Anagrafe anagrafeSigepro) {

	boolean isPresent = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!StringUtils.isNotBlank(anagrafeSigepro.getCodicefiscale())) {
	    _ivs.add(new InvalidValue("service_error.codice_fiscale_non presente", null, null, null, null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return isPresent;
    }

    /**
     * Controlla che sia presente la partita iva quando si richiama il ws cerca aggiornamenti da partita iva
     * 
     * @param anagrafeSigepro
     * @return
     */
    private boolean isPresentPartitaiva(Anagrafe anagrafeSigepro) {

	boolean isPresent = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!StringUtils.isNotBlank(anagrafeSigepro.getPartitaiva())) {
	    _ivs.add(new InvalidValue("service_error.partita_iva_non presente", null, null, null, null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return isPresent;
    }

    protected boolean isDeleteAllowed(Anagrafe anagrafeSigepro) {

	boolean isPresent = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!istanzeService.findByAnagrafeRichiedente(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZE-RICHIEDENTE", null));
	}
	if (!istanzeService.findByAnagrafeTitolarelegale(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZE-TITOLARE LEGALE", null));
	}
	if (!istanzeService.findByAnagrafeProfessionista(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZE-PROFESSIONISTA", null));
	}
	if (!istanzerichiedentiService.findByAnagrafeRichiedente(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZERICHIEDENTI-RICHIEDENTE", null));
	}
	if (!istanzerichiedentiService.findByAnagrafeAnagrafeCollegata(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZERICHIEDENTI-ANAGRAFE COLLEGATA", null));
	}
	if (!istanzerichiedentiService.findByAnagrafeProcuratore(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZERICHIEDENTI-PROCURATORE", null));
	}
	if (!emailanagrService.findByAnagrafe(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "EMAILANAGR", null));
	}
	if (!fDomandeService.findByAnagrafe(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "F_DOMANDE - ANAGRAFE", null));
	}
	if (!fDomandeService.findBySocieta(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "F_DOMANDE - SOCIETA", null));
	}
	if (!fDomandeService.findBySubentro(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "F_DOMANDE - SUBENTRI", null));
	}
	if (!bachecalavorocercaService.findByAnagrafe(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "BACHECALAVOROCERCA", null));
	}
	if (!bachecalavorooffroService.findByAnagrafe(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "BACHECALAVOROOFFRO", null));
	}
	if (!cdsinvitati2Service.findByAnagrafe(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "CDSINVITATI2", null));
	}
	if (!mercatipresenzeDService.findByAnagrafeOccupante(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATIPRESENZE_D - OCCUPANTE", null));
	}
	if (!mercatipresenzeDService.findByAnagrafeConcessionario(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATIPRESENZE_D - CONCESSIONARIO", null));
	}
	if (!registrazioniService.findByAnagrafe(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "REGISTRAZIONI", null));
	}
	if (!registrazioniInOutService.findByAnagrafe(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "REGISTRAZIONI_IN_OUT", null));
	}
	if (!mercatipresenzeStoricoService.findByAnagrafe(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATIPRESENZE_STORICO", null));
	}
	if (!anagrafemercatipresenzeService.findByAnagrafe(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ANAGRAFEMERCATIPRESENZE", null));
	}
	if (!autorizzazioniService.findByAnagrafe(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "AUTORIZZAZIONI", null));
	}
	if (!autorizzazioniSubentriService.findByAnagrafe(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "AUTORIZZAZIONI_SUBENTRI", null));
	}
	if (!permcdsinvitatiService.findByAnagrafe(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "PERMCDSINVITATI", null));
	}
	if (istanzeprocureService.countByAnagrafeProcuratore(anagrafeSigepro.getId().getCodice()) > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZEPROCURE - PROCURATORE", null));
	}
	if (istanzeprocureService.countByAnagrafeRappresentato(anagrafeSigepro.getId().getCodice()) > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZEPROCURE - RAPPRESENTATO", null));
	}
	if (!foDomandeService.findByAnagrafe(anagrafeSigepro.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "FO_DOMANDE", null));
	}
	if (!mercatiConsorziService.findByCodiceAnagrafe(anagrafeSigepro.getId().getCodice()).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATI_CONSORZI", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return isPresent;
    }

    /**
     * Controlla che i dati passi per creare il codice fiscale siano tutti presenti
     * 
     * @param entity
     * @return
     */
    private boolean checkDati(Anagrafe entity) {

	boolean validate = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!StringUtils.isNotBlank(entity.getNominativo())) {
	    _ivs.add(new InvalidValue("service_error.cognome_non presente", null, null, null, null));
	}
	if (!StringUtils.isNotBlank(entity.getNome())) {
	    _ivs.add(new InvalidValue("service_error.nome_non_presente", null, null, null, null));
	}
	if (entity.getDatanascita() == null) {
	    _ivs.add(new InvalidValue("service_error.data_nascita_non_presente", null, null, null, null));
	}
	if (!StringUtils.isNotBlank(entity.getSesso())) {
	    _ivs.add(new InvalidValue("service_error.sesso_non presente", null, null, null, null));
	}
	if (entity.getComuneNascita() != null && !StringUtils.isNotBlank(entity.getComuneNascita().getCodicecomune())) {
	    _ivs.add(new InvalidValue("service_error.comune_nascita_non_presente", null, null, null, null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return validate;
    }

    /**
     * Il metodo prende due oggetti uno master e uno copy. E' necessario che le proprietà dei due oggetti siano uguali.
     * E' possibile escludere campi non necessari o diversi, passandoli come stringa all'interno della lista
     * 
     * @param master
     *            oggetto che si deve copiare
     * @param copy
     *            ogggetto copia che verrà restituito
     * @param fielExclude
     *            campi che non vogliamo siano copiati
     * @return un oggetto copia del master
     */
    private Anagrafestorico copyAnagrafeToAnagrafestorico(Anagrafe master, Anagrafestorico copy, List<String> fielExclude) {

	Method[] m = master.getClass().getDeclaredMethods();
	for (Method method : m) {
	    if (method.getName().equals("getHibernateLazyInitializer")) {
		// se l'oggetto è legato alla session
		try {
		    Object o = method.invoke(master, new Object[0]);
		    // se l'oggetto è legato alla session allora l'oggetto è di tipo JavassistLazyInitializer
		    if (o instanceof org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer) {
			Object o1 = ((org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer) o).getImplementation();
			if (o1 instanceof Anagrafe) {
			    // recupero l'oggetto Anagrafe dal proxy
			    master = (Anagrafe) o1;
			    break;
			}
		    }
		} catch (Exception e) {
		    log.error("copyAnagrafeToAnagrafestorico {}", e.getMessage());
		    e.printStackTrace();
		}
	    }
	}
	Field[] f = master.getClass().getDeclaredFields();
	for (int i = 0; i < f.length; i++) {
	    // evita di inserire i campi che si vogliono escludere dalla copia
	    // primo livello
	    if (!fielExclude.contains(f[i].getName())) {
		try {
		    Method set = copy.getClass().getMethod("set" + StringUtils.capitalize(f[i].getName()), f[i].getType());
		    Method get = master.getClass().getMethod("get" + StringUtils.capitalize(f[i].getName()));
		    try {
			set.invoke(copy, get.invoke(master, new Object[0]));
		    } catch (IllegalArgumentException e) {
			e.printStackTrace();
		    } catch (IllegalAccessException e) {
			e.printStackTrace();
		    } catch (InvocationTargetException e) {
			e.printStackTrace();
		    }
		} catch (SecurityException e) {
		    e.printStackTrace();
		} catch (NoSuchMethodException e) {
		    log.debug("E' stato invocato un metodo inesistente");
		}
	    }
	}
	return copy;
    }

    /**
     * Il metodo deve controllare se l'anagrafica che si sta modificando è cambiata nei sui campi "importanti" (nome,
     * nominativo,sesso,tipologia, tipoanagrafe, partitaiva,codicefiscale,indirizzo,indirizzocorrispondenza,email,pec)
     * il metodo considera i campi null e stringa vuota ("") uguali
     * 
     * @param oldAnagrafe
     * @param anagrafe
     * @return
     */
    private boolean isAnagrafeChange(Anagrafe oldAnagrafe, Anagrafe anagrafe) {

	boolean ischange = false;
	if (isChangeString(oldAnagrafe.getNome(), anagrafe.getNome()))
	    return true;
	if (isChangeString(oldAnagrafe.getNominativo(), anagrafe.getNominativo()))
	    return true;
	if (isChangeString(oldAnagrafe.getPartitaiva(), anagrafe.getPartitaiva()))
	    return true;
	if (isChangeString(oldAnagrafe.getCodicefiscale(), anagrafe.getCodicefiscale()))
	    return true;
	// INDIRIZZO
	if (isChangeString(oldAnagrafe.getIndirizzo(), anagrafe.getIndirizzo()))
	    return true;
	if (isChangeString(oldAnagrafe.getCap(), anagrafe.getCap()))
	    return true;
	if (isChangeString(oldAnagrafe.getCitta(), anagrafe.getCitta()))
	    return true;
	if (isChangeString(oldAnagrafe.getProvincia(), anagrafe.getProvincia()))
	    return true;
	if (oldAnagrafe.getComuneResidenza() != null && anagrafe.getComuneResidenza() != null) {
	    if (isChangeString(oldAnagrafe.getComuneResidenza().getCodicecomune(), anagrafe.getComuneResidenza().getCodicecomune())) {
		return true;
	    }
	}
	// CORRISPONDENZA
	if (isChangeString(oldAnagrafe.getIndirizzocorrispondenza(), anagrafe.getIndirizzocorrispondenza()))
	    return true;
	if (isChangeString(oldAnagrafe.getCapcorrispondenza(), anagrafe.getCapcorrispondenza()))
	    return true;
	if (isChangeString(oldAnagrafe.getCittacorrispondenza(), anagrafe.getCittacorrispondenza()))
	    return true;
	if (isChangeString(oldAnagrafe.getProvinciacorrispondenza(), anagrafe.getProvinciacorrispondenza()))
	    return true;
	if (oldAnagrafe.getComunecorrispondenza() != null && anagrafe.getComunecorrispondenza() != null) {
	    if (isChangeString(oldAnagrafe.getComunecorrispondenza().getCodicecomune(), anagrafe.getComunecorrispondenza().getCodicecomune())) {
		return true;
	    }
	}
	if (isChangeString(oldAnagrafe.getPec(), anagrafe.getPec())) {
	    return true;
	}
	if (isChangeString(oldAnagrafe.getEmail(), anagrafe.getEmail())) {
	    return true;
	}
	if (isChangeString(oldAnagrafe.getSesso(), anagrafe.getSesso())) {
	    return true;
	}
	if (isChangeInteger(oldAnagrafe.getTipologia(), anagrafe.getTipologia())) {
	    return true;
	}
	if (isChangeString(oldAnagrafe.getTipoanagrafe(), anagrafe.getTipoanagrafe())) {
	    return true;
	}
	if (isChangeString(oldAnagrafe.getInailMatricola(), anagrafe.getInailMatricola())) {
	    return true;
	}
	if (oldAnagrafe.getSedeInail() != null && anagrafe.getSedeInail() != null) {
	    if (isChangeString(oldAnagrafe.getSedeInail().getCodice(), anagrafe.getSedeInail().getCodice())) {
		return true;
	    }
	}
	if (isChangeString(oldAnagrafe.getInpsMatricola(), anagrafe.getInpsMatricola())) {
	    return true;
	}
	if (oldAnagrafe.getSedeInps() != null && anagrafe.getSedeInps() != null) {
	    if (isChangeString(oldAnagrafe.getSedeInps().getCodice(), anagrafe.getSedeInps().getCodice())) {
		return true;
	    }
	}
	if (isChangeString(oldAnagrafe.getCassaedileMatricola(), anagrafe.getCassaedileMatricola())) {
	    return true;
	}
	if (oldAnagrafe.getSedeCassaedile() != null && anagrafe.getSedeCassaedile() != null) {
	    if (isChangeString(oldAnagrafe.getSedeCassaedile().getCodice(), anagrafe.getSedeCassaedile().getCodice())) {
		return true;
	    }
	}
	if (isChangeBoolean(oldAnagrafe.getFlagIdentificato(), anagrafe.getFlagIdentificato())) {
	    return true;
	}
	if (isChangeInteger(oldAnagrafe.getFlagDisabilitato(), anagrafe.getFlagDisabilitato())) {
	    return true;
	}
	if (isChangeData(oldAnagrafe.getDataDisabilitato(), anagrafe.getDataDisabilitato())) {
	    return true;
	}
	if (isChangeData(oldAnagrafe.getDatanascita(), anagrafe.getDatanascita())) {
	    return true;
	}
	if (isChangeString(oldAnagrafe.getNumiscrrea(), anagrafe.getNumiscrrea())) {
	    return true;
	}
	if (isChangeData(oldAnagrafe.getDataiscrrea(), anagrafe.getDataiscrrea())) {
	    return true;
	}
	if (isChangeString(oldAnagrafe.getRegditte(), anagrafe.getRegditte())) {
	    return true;
	}
	if (isChangeData(oldAnagrafe.getDataregditte(), anagrafe.getDataregditte())) {
	    return true;
	}
	return ischange;
    }

    /**
     * 
     * Questo metodo di uguaglianza tra stringhe considera due // stringhe null e stringa vuota come uguali senza
     * guardare a maiuscole minuscole
     * 
     * @param old
     * @param nuova
     * @return
     */
    private boolean isChangeString(String old, String nuova) {

	// trasforma la stringa inizializzata a null in stringa vuota
	old = StringUtils.trimToEmpty(old);
	nuova = StringUtils.trimToEmpty(nuova);
	// fa il controllo
	return !old.equalsIgnoreCase(nuova);
    }

    private static boolean isChangeBoolean(Boolean old, Boolean nuova) {

	if (old == null && nuova == null) {
	    return false;
	}
	if (old != null) {
	    return !old.equals(BooleanUtils.toBoolean(nuova));
	}
	if (nuova != null) {
	    return !nuova.equals(BooleanUtils.toBoolean(old));
	}
	// fa il controllo
	return false;
    }

    private static boolean isChangeData(Date vecchia, Date nuova) {

	if (vecchia == null && nuova == null) {
	    return false;
	}
	if (vecchia != null && nuova == null) {
	    return true;
	}
	if (nuova != null && vecchia == null) {
	    return true;
	}
	String vecchiaData = Utilities.formatDate(vecchia, false);
	String nuovaData = Utilities.formatDate(nuova, false);
	// fa il controllo
	return !nuovaData.equalsIgnoreCase(vecchiaData);
    }

    /**
     * 
     * Questo metodo di uguaglianza tra Interi.
     * 
     * @param old
     * @param nuova
     * @return
     */
    private boolean isChangeInteger(Integer old, Integer nuova) {

	if (old == null || nuova == null) {
	    if (old == null && nuova == null) {
		return false;
	    }
	    if (old == null && nuova != null) {
		return true;
	    }
	    if (old != null && nuova == null) {
		return true;
	    }
	    return false;
	}
	return old.compareTo(nuova) != 0;
    }

    /**
     * La funzione controlla se la password è stata passata e nel caso la cripta con l'algoritmo MD5
     * 
     * @param entity
     * @param isUpdate
     */
    private void checkPassword(Anagrafe entity, boolean isUpdate) {

	String password = "";
	if (StringUtils.isBlank(entity.getPasswordClear())) {
	    if (isUpdate) {
		Anagrafe copy = this.findById(entity.getId());
		password = copy.getPassword();
		anagrafeDAO.evict(copy);
		entity.setPassword(password);
		return;
	    }
	} else {
	    String passwordClear = entity.getPasswordClear();
	    password = Utilities.getHashText(passwordClear, ENCRYPTING_ALGORITHM, false);
	    entity.setPassword(password);
	    entity.setPasswordClear(null);
	}
    }

    /**
     * 
     * @param request
     *            Recupera la connessione al WS da invocare
     */
    private WsAnagrafe2Soap getPortWS(String parametro_url_verticalizzazione_anagrafe_ws) {

	//	WsAnagrafeLocator locator = new WsAnagrafeLocator();
	// Stringa di connessione al WS anagarfe, di default viene impostata quella del componente
	//di .net presente sulle BackofficeNETConstants
	String webServiceUrl = BackofficeNETConstants.getURL_WS_ANAGRAFE();
	// Controlliamo se la verticalizzazione ANAGRAFE WS è attiva; nel caso lo sia controlliamo se è popolato 
	// il parametro in verticalizzaione  passato (parametro_url_verticalizzazione_anagrafe_ws).
	if (log.isDebugEnabled()) {
	    log.debug("getPortWS# Parametro verticalizzazione URL passato : {}", parametro_url_verticalizzazione_anagrafe_ws);
	}
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE)) {
	    //	    Verticalizzazioniparametri ws_anagarfe_url = verticalizzazioniService.getVerticalizzazioniparametri(
	    //		    WebConstants.VERTICALIZZAZIONE_WSANAGRAFE, WebConstants.WS_ANAGRAFE_URL);
	    // Recupero se presente il parametro in verticalizzazione
	    Verticalizzazioniparametri ws_anagarfe_url = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_WSANAGRAFE, parametro_url_verticalizzazione_anagrafe_ws, ORMHelper.getSoftware());
	    // Se il parametro è non vuoto allora sovrascrivo l'indirizzo al WS anagrafe con quello trovato 
	    //in verticalizzazione
	    if (ws_anagarfe_url != null && StringUtils.isNotBlank(ws_anagarfe_url.getValore())) {
		webServiceUrl = ws_anagarfe_url.getValore();
	    }
	}
	//	WsAnagrafeSoap port = null;
	WsAnagrafe2Soap port = null;
	try {
	    port = anagrafe2WsClient.getAnagrafe2WsPort(webServiceUrl);
	} catch (Exception e) {
	    log.error("Servizio non funzionante o non disponibile: " + e.getMessage());
	    throw new RuntimeException("Servizio WS temporaneamente non disponibile: " + e.getMessage());
	}
	//	} catch (MalformedURLException e) {
	//	    log.error("Servizio non funzionante non disponibile: " + e.getMessage());
	//	    throw new RuntimeException("Servizio WS temporaneamente non disponibile: " + e.getMessage());
	//	}
	//	//	    port = locator.getWsAnagrafeSoap(new URL(webServiceUrl));
	//	catch (ServiceException e) {
	//	    log.debug("Servizio non funzionante non disponibile: " + e.getMessage());
	//	    throw new RuntimeException("Servizio WS temporaneamente non disponibile: " + e.getMessage());
	//	}
	return port;
    }

    @Override
    protected Anagrafe customBindDomainObject(Anagrafe entity) {

	if (entity == null) {
	    return null;
	}
	Anagrafe dbEntity = null;
	AnagrafeBusinessRules rules = (AnagrafeBusinessRules) SigeproBusinessRules.getClassRules(AnagrafeBusinessRules.class);
	IstanzeBusinessRules istanzeRules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
	boolean cercaSoloPerCfOPiva = rules.getCustomRule(AnagrafeBusinessRules.CustomRuleEnum.ricercaSoloCf_Piva.name());
	boolean inserimentoSTC = istanzeRules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name())
		|| istanzeRules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStcDiretto.name());
	if (cercaSoloPerCfOPiva) { // RICERCA SOLO PER CODICEFISCALE O PARTITA IVA
	    if (StringUtils.isBlank(entity.getCodicefiscale()) && StringUtils.isBlank(entity.getPartitaiva())) {
		log.error("customBindDomainObject: Si è tentato di effettuare una ricerca per cf e piva ma entrambe non sono stati valorizzati");
		throw new RecuperoDatiAnagraficaException(
			"AnagrafeService.customBindDomainObject: Si è tentato di effettuare una ricerca per cf e piva ma entrambe non sono stati valorizzati");
	    }
	    AnagrafeFilter filtro = new AnagrafeFilter();
	    filtro.setDefaultWhereCondition(DAOEnum.FIND_BY_IDCOMUNE);
	    Anagrafe datiAnagrafici = new Anagrafe();
	    datiAnagrafici.setCodicefiscale(entity.getCodicefiscale());
	    datiAnagrafici.setPartitaiva(entity.getPartitaiva());
	    if (inserimentoSTC) {
		datiAnagrafici.setFlagDisabilitato(0);
	    }
	    filtro.setDatiAnagrafe(datiAnagrafici);
	    filtro.setTipoRicercaEnum(TipoRicercaEnum.EQUALSIGNORECASE);
	    List<Anagrafe> anagrafes = this.findByFilter(filtro);
	    if (anagrafes.size() == 1) {
		dbEntity = anagrafes.get(0);
		verificaIncongruenze(dbEntity, entity);
		return dbEntity;
	    } else if (anagrafes.size() > 1) {
		erroreOmonimiaNellaRicerca(anagrafes.get(0));
	    }
	} else {
	    // se non è impostato tipo anagrafe
	    if (StringUtils.isBlank(entity.getTipoanagrafe())) {
		// se nome non è vuoto allora tipoanagrafe = F
		if (StringUtils.isNotBlank(entity.getNome())) {
		    entity.setTipoanagrafe(WebConstants.PERSONA_FISICA);
		} else {
		    // se nome è vuoto e nominativo non è vuoto allora tipoanagrafe = G
		    if (StringUtils.isNotBlank(entity.getNominativo())) {
			entity.setTipoanagrafe(WebConstants.PERSONA_GIURIDICA);
		    }
		}
	    }
	    // se tipo anagrafe non è specificato rilancio un'eccezione
	    // non posso rilanciare l'eccezione in quanto tutte le invocazioni al metodo darebbero errore. 
	    // Torno sempliecente un oggetto nullo in quanto non lo posso associare. Eventualmente posso settare 
	    // di default tipoanagrafe=F o G
	    if (StringUtils.isBlank(entity.getTipoanagrafe())) {
		return null;
	    }
	    AnagrafeFilter filtro = new AnagrafeFilter();
	    filtro.setDefaultWhereCondition(DAOEnum.FIND_BY_IDCOMUNE);
	    filtro.setOrderBy(new String[] { "dataDisabilitato" });
	    filtro.setOrderAscDesc(new OrderTypeEnum[] { OrderTypeEnum.DESC });
	    Anagrafe datiAnagrafici = new Anagrafe();
	    // SE PERSONA FISICA RICERCO PER CODICE FISCALE PRIMA ABILITATO  POI DISABILITATO
	    if (entity.getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
		if (StringUtils.isNotBlank(entity.getCodicefiscale())) {
		    // se codice fiscale è impostato ricerca per questo campo
		    // 1. quelli abilitati filtro.setFlagDisabilitato(0) 
		    datiAnagrafici.setFlagDisabilitato(0);
		    datiAnagrafici.setTipoanagrafe(entity.getTipoanagrafe());
		    datiAnagrafici.setCodicefiscale(entity.getCodicefiscale());
		    //datiAnagrafici.setTipologia(entity.getTipologia())
		    filtro.setDatiAnagrafe(datiAnagrafici);
		    filtro.setTipoRicercaEnum(TipoRicercaEnum.EQUALSIGNORECASE);
		    List<Anagrafe> anagrafes = this.findByFilter(filtro);
		    if (anagrafes.size() == 1) {
			dbEntity = anagrafes.get(0);
			verificaIncongruenze(dbEntity, entity);
			return dbEntity;
		    } else if (anagrafes.size() > 1) {
			erroreOmonimiaNellaRicerca(anagrafes.get(0));
		    }
		    if (!inserimentoSTC) { // se STC NON DEVO CERCARE TRA I DISABILITATI
			// 2. se non trovato per cf e abilitati cerca se disabilitati filtro.setFlagDisabilitato(1)
			datiAnagrafici.setFlagDisabilitato(1);
			// ordinati per datadisabilitato desc
			filtro.setDatiAnagrafe(datiAnagrafici);
			anagrafes = this.findByFilter(filtro);
			// se più di uno ritorno il primo
			if (!anagrafes.isEmpty()) {
			    dbEntity = anagrafes.get(0);
			    verificaIncongruenze(dbEntity, entity);
			    return dbEntity;
			}
		    }
		}
	    } else {
		// SE PERSONA FISICA RICERCO PER PIVA PRIMA ABILITATO POI DISABILITATO
		if (StringUtils.isNotBlank(entity.getPartitaiva()) || StringUtils.isNotBlank(entity.getCodicefiscale())) {
		    if (StringUtils.isNotBlank(entity.getPartitaiva())) {
			// se Partita iva è impostato ricerca per questo campo
			// 1. quelli abilitati filtro.setFlagDisabilitato(0)
			datiAnagrafici = new Anagrafe();
			datiAnagrafici.setFlagDisabilitato(0);
			//datiAnagrafici.setTipologia(entity.getTipologia())
			datiAnagrafici.setTipoanagrafe(entity.getTipoanagrafe());
			datiAnagrafici.setPartitaiva(entity.getPartitaiva().trim());
			filtro.setDatiAnagrafe(datiAnagrafici);
			filtro.setTipoRicercaEnum(TipoRicercaEnum.EQUALSIGNORECASE);
			List<Anagrafe> anagrafes = this.findByFilter(filtro);
			if (anagrafes.size() == 1) {
			    dbEntity = anagrafes.get(0);
			    verificaIncongruenze(dbEntity, entity);
			    return dbEntity;
			} else if (anagrafes.size() > 1) {
			    erroreOmonimiaNellaRicerca(anagrafes.get(0));
			}
			if (!inserimentoSTC) { // se STC NON DEVO CERCARE TRA I DISABILITATI
			    // 2. se non trovato per piva e abilitati cerca se disabilitati filtro.setFlagDisabilitato(1)
			    datiAnagrafici.setFlagDisabilitato(1);
			    // ordinati per datadisabilitato desc
			    filtro.setDatiAnagrafe(datiAnagrafici);
			    anagrafes = this.findByFilter(filtro);
			    // se più di uno ritorno il primo
			    if (!anagrafes.isEmpty()) {
				dbEntity = anagrafes.get(0);
				verificaIncongruenze(dbEntity, entity);
				return dbEntity;
			    }
			}
		    }
		    if (StringUtils.isNotBlank(entity.getCodicefiscale())) {
			// SE PERSONA GIURIDICA E NON E' STATO TROVATO PER PIVA RICERCO PER CODICE FISCALE PRIMA ABILITATO  POI DISABILITATO
			datiAnagrafici = new Anagrafe();
			// se codice fiscale è impostato ricerca per questo campo
			// 1. quelli abilitati filtro.setFlagDisabilitato(0) 
			datiAnagrafici.setFlagDisabilitato(0);
			datiAnagrafici.setTipoanagrafe(entity.getTipoanagrafe());
			datiAnagrafici.setCodicefiscale(entity.getCodicefiscale().trim());
			//datiAnagrafici.setTipologia(entity.getTipologia())
			filtro.setDatiAnagrafe(datiAnagrafici);
			filtro.setTipoRicercaEnum(TipoRicercaEnum.EQUALSIGNORECASE);
			List<Anagrafe> anagrafes = this.findByFilter(filtro);
			if (anagrafes.size() == 1) {
			    dbEntity = anagrafes.get(0);
			    verificaIncongruenze(dbEntity, entity);
			    return dbEntity;
			} else if (anagrafes.size() > 1) {
			    erroreOmonimiaNellaRicerca(anagrafes.get(0));
			}
			if (!inserimentoSTC) { // se STC NON DEVO CERCARE TRA I DISABILITATI
			    // 2. se non trovato per cf e abilitati cerca se disabilitati filtro.setFlagDisabilitato(1)
			    datiAnagrafici.setFlagDisabilitato(1);
			    // ordinati per datadisabilitato desc
			    filtro.setDatiAnagrafe(datiAnagrafici);
			    anagrafes = this.findByFilter(filtro);
			    // se più di uno ritorno il primo
			    if (!anagrafes.isEmpty()) {
				dbEntity = anagrafes.get(0);
				verificaIncongruenze(dbEntity, entity);
				return dbEntity;
			    }
			}
		    }
		}
	    }
	    if (StringUtils.isNotBlank(entity.getNome()) || StringUtils.isNotBlank(entity.getNominativo())) {
		// se il lookup fallisce cerco per nome+cognome in caso di PF
		if (entity.getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
		    if (StringUtils.isNotBlank(entity.getNome()) && StringUtils.isNotBlank(entity.getNominativo())) {
			// 1 . cerca per nome+nominativo + disabilitato=0
			datiAnagrafici = new Anagrafe();
			datiAnagrafici.setNome(entity.getNome());
			datiAnagrafici.setNominativo(entity.getNominativo());
			if (StringUtils.isNotBlank(entity.getCodicefiscale())) {
			    datiAnagrafici.setCodicefiscale(entity.getCodicefiscale());
			}
			// datiAnagrafici.setTipologia(entity.getTipologia())
			datiAnagrafici.setFlagDisabilitato(0);
			datiAnagrafici.setTipoanagrafe(WebConstants.PERSONA_FISICA);
			filtro.setTipoRicercaEnum(TipoRicercaEnum.EQUALSIGNORECASE);
			filtro.setDatiAnagrafe(datiAnagrafici);
			List<Anagrafe> anagrafes = this.findByFilter(filtro);
			if (anagrafes.size() == 1) {
			    dbEntity = anagrafes.get(0);
			    verificaIncongruenze(dbEntity, entity);
			    return dbEntity;
			} else if (anagrafes.size() > 1) {
			    erroreOmonimiaNellaRicerca(anagrafes.get(0));
			}
			if (!inserimentoSTC) { // se STC NON DEVO CERCARE TRA I DISABILITATI
			    // 2 . cerca per nome+nominativo + disabilitato=1
			    datiAnagrafici.setFlagDisabilitato(1);
			    // ordinati per datadisabilitato desc
			    filtro.setDatiAnagrafe(datiAnagrafici);
			    anagrafes = this.findByFilter(filtro);
			    // se più di uno ritorno il primo
			    if (!anagrafes.isEmpty()) {
				dbEntity = anagrafes.get(0);
				verificaIncongruenze(dbEntity, entity);
				return dbEntity;
			    }
			}
		    }
		} else {
		    // se il lookup fallisce cerco per nominativo in caso di PG
		    // 1 . cerca per nominativo + disabilitato=0
		    if (StringUtils.isNotBlank(entity.getNominativo())) {
			datiAnagrafici = new Anagrafe();
			datiAnagrafici.setNominativo(entity.getNominativo());
			// datiAnagrafici.setTipologia(entity.getTipologia())
			datiAnagrafici.setFlagDisabilitato(0);
			datiAnagrafici.setTipoanagrafe(WebConstants.PERSONA_GIURIDICA);
			if (StringUtils.isNotBlank(entity.getCodicefiscale())) {
			    datiAnagrafici.setCodicefiscale(entity.getCodicefiscale());
			}
			if (StringUtils.isNotBlank(entity.getPartitaiva())) {
			    datiAnagrafici.setPartitaiva(entity.getPartitaiva());
			}
			filtro.setDatiAnagrafe(datiAnagrafici);
			filtro.setTipoRicercaEnum(TipoRicercaEnum.EQUALSIGNORECASE);
			List<Anagrafe> anagrafes = this.findByFilter(filtro);
			if (anagrafes.size() == 1) {
			    dbEntity = anagrafes.get(0);
			    verificaIncongruenze(dbEntity, entity);
			    return dbEntity;
			} else if (anagrafes.size() > 1) {
			    erroreOmonimiaNellaRicerca(anagrafes.get(0));
			}
			if (!inserimentoSTC) { // se STC NON DEVO CERCARE TRA I DISABILITATI
			    // 2 . cerca per nominativo + disabilitato=1
			    datiAnagrafici.setFlagDisabilitato(1);
			    filtro.setDatiAnagrafe(datiAnagrafici);
			    anagrafes = this.findByFilter(filtro);
			    // se più di uno ritorno il primo
			    if (!anagrafes.isEmpty()) {
				dbEntity = anagrafes.get(0);
				verificaIncongruenze(dbEntity, entity);
				return dbEntity;
			    }
			}
		    }
		}
	    }
	    ///
	}
	return null;
    }

    /**
     * Usa la regola AnagrafeBusinessRules.CustomRuleEnum.verificaIncongruenze.name() e chiama
     * _verificaIncongruenze(Anagrafe dbEntity, Anagrafe entityDaConfrontare, true)
     * 
     * @param dbEntity
     * @param entityDaConfrontare
     */
    private void verificaIncongruenze(Anagrafe dbEntity, Anagrafe entityDaConfrontare) {

	if (dbEntity == null || entityDaConfrontare == null) {
	    return;
	}
	AnagrafeBusinessRules rules = (AnagrafeBusinessRules) SigeproBusinessRules.getClassRules(AnagrafeBusinessRules.class);
	boolean isVerificaIncongruenze = rules.getCustomRule(AnagrafeBusinessRules.CustomRuleEnum.verificaIncongruenze.name());
	if (isVerificaIncongruenze) {
	    _verificaIncongruenze(dbEntity, entityDaConfrontare, true);
	}
    }

    private void _verificaIncongruenze(Anagrafe dbEntity, Anagrafe entityDaConfrontare, boolean isRilanciaEccezione) {

	if (dbEntity == null || entityDaConfrontare == null) {
	    return;
	}
	//	Quando viene trovata una anagrafica che corrisponde a quella ricercata viene 
	//	fatta una verifica dei dati sensibili per verificare che effettivamente le due anagrafice coincidano.
	//	I dati confrontati sono i seguenti ( naturalmente il confronto può avvenire solamente se entrambe hanno il dato valorizzato ):
	//
	//	CODICEFISCALE ( sia per quelle fisiche che per quelle giuridiche )
	//	PARTITAIVA ( sia per quelle fisiche che per quelle giuridiche )
	//	NOMINATIVO ( sia per quelle fisiche che per quelle giuridiche )
	//	NOME ( sia per quelle fisiche che per quelle giuridiche )
	//	COMUNERESIDENZA ( sia per quelle fisiche che per quelle giuridiche )
	//	INDIRIZZO ( sia per quelle fisiche che per quelle giuridiche )
	//	DATANASCITA ( solo se l'anagrafica cercata è fisica )
	//	SESSO ( solo se l'anagrafica cercata è fisica )
	//	CODCOMNASCITA ( solo se l'anagrafica cercata è fisica )
	//	DATANOMINATIVO ( solo se l'anagrafica cercata è giuridica )
	//
	//	nel caso in cui uno di questi dati non corrisponde viene sollevata un'eccezione.
	if (StringUtils.isNotBlank(dbEntity.getCodicefiscale()) && StringUtils.isNotBlank(entityDaConfrontare.getCodicefiscale())) {
	    if (!dbEntity.getCodicefiscale().equalsIgnoreCase(entityDaConfrontare.getCodicefiscale())) {
		if (isRilanciaEccezione) {
		    erroreIncongruenzaDati("ANAGRAFE", "codicefiscale", dbEntity.getCodicefiscale(), entityDaConfrontare.getCodicefiscale());
		} else {
		    addToInserimentoIstanzeFlashMessages(dbEntity);
		    return;
		}
	    }
	}
	if (StringUtils.isNotBlank(dbEntity.getPartitaiva()) && StringUtils.isNotBlank(entityDaConfrontare.getPartitaiva())) {
	    if (!dbEntity.getPartitaiva().equalsIgnoreCase(entityDaConfrontare.getPartitaiva())) {
		if (isRilanciaEccezione) {
		    erroreIncongruenzaDati("ANAGRAFE", "partitaiva", dbEntity.getPartitaiva(), entityDaConfrontare.getPartitaiva());
		} else {
		    addToInserimentoIstanzeFlashMessages(dbEntity);
		    return;
		}
	    }
	}
	if (StringUtils.isNotBlank(dbEntity.getNominativo()) && StringUtils.isNotBlank(entityDaConfrontare.getNominativo())) {
	    if (!dbEntity.getNominativo().equalsIgnoreCase(entityDaConfrontare.getNominativo())) {
		if (isRilanciaEccezione) {
		    erroreIncongruenzaDati("ANAGRAFE", "nominativo", dbEntity.getNominativo(), entityDaConfrontare.getNominativo());
		} else {
		    addToInserimentoIstanzeFlashMessages(dbEntity);
		    return;
		}
	    }
	}
	if (StringUtils.isNotBlank(dbEntity.getNome()) && StringUtils.isNotBlank(entityDaConfrontare.getNome())) {
	    if (!dbEntity.getNome().equalsIgnoreCase(entityDaConfrontare.getNome())) {
		if (isRilanciaEccezione) {
		    erroreIncongruenzaDati("ANAGRAFE", "nome", dbEntity.getNome(), entityDaConfrontare.getNome());
		} else {
		    addToInserimentoIstanzeFlashMessages(dbEntity);
		    return;
		}
	    }
	}
	if (EntityUtils.getNestedProperty(dbEntity.getComuneResidenza(), "codicecomune") != null
		&& EntityUtils.getNestedProperty(entityDaConfrontare.getComuneResidenza(), "codicecomune") != null) {
	    String comuneResidenza = dbEntity.getComuneResidenza().getCodicecomune();
	    String comuneResidenzaCfg = entityDaConfrontare.getComuneResidenza().getCodicecomune();
	    if (!comuneResidenza.equals(comuneResidenzaCfg)) {
		if (isRilanciaEccezione) {
		    erroreIncongruenzaDati("ANAGRAFE", "comuneResidenza.codicecomune", comuneResidenza, comuneResidenzaCfg);
		} else {
		    addToInserimentoIstanzeFlashMessages(dbEntity);
		    return;
		}
	    }
	}
	//	    if (StringUtils.isNotBlank(dbEntity.getIndirizzo()) && StringUtils.isNotBlank(entityDaConfrontare.getIndirizzo())) {
	//		String indirizzo = dbEntity.getIndirizzo();
	//		String indirizzoCfg = entityDaConfrontare.getIndirizzo();
	//		if (!indirizzo.equals(indirizzoCfg)) {
	//		    erroreIncongruenzaDati("ANAGRAFE", "indirizzo", indirizzo, indirizzoCfg);
	//		}
	//	    }
	if (StringUtils.defaultIfEmpty(dbEntity.getTipoanagrafe(), WebConstants.PERSONA_FISICA).equals(WebConstants.PERSONA_FISICA)) {
	    //	DATANASCITA ( solo se l'anagrafica cercata è fisica )
	    //	SESSO ( solo se l'anagrafica cercata è fisica )
	    //	CODCOMNASCITA ( solo se l'anagrafica cercata è fisica )
	    if (dbEntity.getDatanascita() != null && entityDaConfrontare.getDatanascita() != null) {
		SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
		String left = sdf.format(dbEntity.getDatanascita());
		String right = sdf.format(entityDaConfrontare.getDatanascita());
		if (!left.equalsIgnoreCase(right)) {
		    if (isRilanciaEccezione) {
			erroreIncongruenzaDati("ANAGRAFE", "datanascita", left, right);
		    } else {
			addToInserimentoIstanzeFlashMessages(dbEntity);
			return;
		    }
		}
	    }
	    if (StringUtils.isNotBlank(dbEntity.getSesso()) && StringUtils.isNotBlank(entityDaConfrontare.getSesso())) {
		if (!dbEntity.getSesso().equalsIgnoreCase(entityDaConfrontare.getSesso())) {
		    if (isRilanciaEccezione) {
			erroreIncongruenzaDati("ANAGRAFE", "sesso", dbEntity.getSesso(), entityDaConfrontare.getSesso());
		    } else {
			addToInserimentoIstanzeFlashMessages(dbEntity);
			return;
		    }
		}
	    }
	    if (EntityUtils.getNestedProperty(dbEntity.getComuneNascita(), "codicecomune") != null
		    && EntityUtils.getNestedProperty(entityDaConfrontare.getComuneNascita(), "codicecomune") != null) {
		String comuneNascita = dbEntity.getComuneNascita().getCodicecomune();
		String comuneNascitaCfg = entityDaConfrontare.getComuneNascita().getCodicecomune();
		if (!comuneNascita.equals(comuneNascitaCfg)) {
		    if (isRilanciaEccezione) {
			erroreIncongruenzaDati("ANAGRAFE", "comuneNascita.codicecomune", comuneNascita, comuneNascitaCfg);
		    } else {
			addToInserimentoIstanzeFlashMessages(dbEntity);
			return;
		    }
		}
	    }
	} else {
	    // DATANOMINATIVO ( solo se l'anagrafica cercata è giuridica )
	    if (dbEntity.getDatanominativo() != null && entityDaConfrontare.getDatanominativo() != null) {
		SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
		String left = sdf.format(dbEntity.getDatanominativo());
		String right = sdf.format(entityDaConfrontare.getDatanominativo());
		if (!left.equalsIgnoreCase(right)) {
		    if (isRilanciaEccezione) {
			erroreIncongruenzaDati("ANAGRAFE", "datanominativo", left, right);
		    } else {
			addToInserimentoIstanzeFlashMessages(dbEntity);
			return;
		    }
		}
	    }
	}
    }

    private void addToInserimentoIstanzeFlashMessages(Anagrafe dbEntity) {

	if (dbEntity == null) {
	    return;
	}
	String nominativo = "";
	if (StringUtils.isNotBlank(dbEntity.getNome())) {
	    nominativo += dbEntity.getNome() + " ";
	}
	if (StringUtils.isNotBlank(dbEntity.getNominativo())) {
	    nominativo += dbEntity.getNominativo() + " ";
	}
	if (StringUtils.isNotBlank(dbEntity.getCodicefiscale())) {
	    nominativo += "[CF: " + dbEntity.getCodicefiscale() + "]";
	}
	if (StringUtils.isNotBlank(dbEntity.getPartitaiva())) {
	    nominativo += "[PIVA: " + dbEntity.getPartitaiva() + "]";
	}
	// I dati presenti nella domanda pervenuta da STC, per l’anagrafica <Mario Rossi>, sono discordanti 
	// rispetto all’anagrafica presente nel back-office. L’anagrafica non è stata aggiornata. Si rende necessaria una verifica da parte dell'operatore.
	String message = getMessageFromBundle("service_error.anagrafe.incongruenza_dati_warning", new Object[] { nominativo });
	List<String> ifm = InserimentoIstanzeFlashMessages.getEventis();
	if (ifm == null) {
	    ifm = new ArrayList<String>();
	}
	ifm.add(message);
	InserimentoIstanzeFlashMessages.setEventis(ifm);
    }

    private void erroreIncongruenzaDati(String tableName, String property, String leftValue, String rightValue) {

	String message = getMessageFromBundle("service_error.incongruenza_dati", new Object[] { tableName, property, leftValue, rightValue });
	throw new RecuperoDatiAnagraficaException(message);
    }

    private void erroreOmonimiaNellaRicerca(Anagrafe entity) {

	String descrizioneRichiedente = entity.getDescrizioneRichiedente();
	String message = getMessageFromBundle("service_error.anagrafe_omonimia_nella_ricerca", new Object[] { descrizioneRichiedente });
	throw new RecuperoDatiAnagraficaException(message);
    }

    private List<Anagrafe> findByFilter(AnagrafeFilter filtro, Integer firstResult, Integer maxResults) {

	if (filtro == null) {
	    throw new BusinessValidationException("Anagrafe.findByFilter: La ricerca non può essere effettuata il filtro passato è nullo ");
	}
	FilterTable ft = null;
	if (filtro.getDefaultWhereCondition() == null) {
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	} else {
	    ft = new FilterTable(filtro.getDefaultWhereCondition());
	}
	if (filtro.getTipoRicercaEnum() == null) {
	    filtro.setTipoRicercaEnum(TipoRicercaEnum.LIKE);
	}
	Anagrafe dati = filtro.getDatiAnagrafe();
	if (dati != null) {
	    if (StringUtils.isNotBlank(dati.getCodicefiscale())) {
		FilterRestriction cf = new FilterRestriction();
		settaRestriction(cf, "codicefiscale", dati.getCodicefiscale(), filtro.getTipoRicercaEnum());
		ft.addRestriction(cf);
	    }
	    if (StringUtils.isNotBlank(dati.getPartitaiva())) {
		FilterRestriction piva = new FilterRestriction();
		settaRestriction(piva, "partitaiva", dati.getPartitaiva(), filtro.getTipoRicercaEnum());
		ft.addRestriction(piva);
	    }
	    if (StringUtils.isNotBlank(dati.getTipoanagrafe())) {
		FilterRestriction ta = new FilterRestriction();
		ta.addFilterField(FilterUtils.equals("tipoanagrafe", dati.getTipoanagrafe(), String.class));
		ft.addRestriction(ta);
	    }
	    if (dati.getTipologia() != null) {
		FilterRestriction tp = new FilterRestriction();
		tp.addFilterField(FilterUtils.equals("tipologia", dati.getTipologia(), Integer.class));
		ft.addRestriction(tp);
	    }
	    if (dati.getFlagDisabilitato() != null) {
		FilterRestriction dis = new FilterRestriction();
		if (dati.getFlagDisabilitato().intValue() == 0) {
		    dis.setAndOrRestriction(AndOrRestriction.OR);
		    dis.addFilterField(FilterUtils.isNull("flagDisabilitato"));
		}
		dis.addFilterField(FilterUtils.equals("flagDisabilitato", dati.getFlagDisabilitato(), Integer.class));
		ft.addRestriction(dis);
	    }
	    if (StringUtils.isNotBlank(dati.getNominativo())) {
		FilterRestriction nomStr = new FilterRestriction();
		settaRestriction(nomStr, "nominativo", dati.getNominativo(), filtro.getTipoRicercaEnum());
		ft.addRestriction(nomStr);
	    }
	    if (StringUtils.isNotBlank(dati.getNome())) {
		FilterRestriction nomeStr = new FilterRestriction();
		settaRestriction(nomeStr, "nome", dati.getNome(), filtro.getTipoRicercaEnum());
		ft.addRestriction(nomeStr);
	    }
	    /*
	    * Lion 29-01-14 aggiunta opzione per filtrare anche in base all'indirizzo PEC. 
	    * Utile in PEC-Inbox per individuare i mittenti delle PEC. 
	    */
	    if (StringUtils.isNotBlank(dati.getPec())) {
		FilterRestriction pecStr = new FilterRestriction();
		settaRestriction(pecStr, "pec", dati.getPec(), filtro.getTipoRicercaEnum());
		ft.addRestriction(pecStr);
	    }
	    //END Lion 29-01-14
	}
	if (filtro.getOrderBy() != null) {
	    int numOrderBy = filtro.getOrderBy().length;
	    String[] orderBy = filtro.getOrderBy();
	    OrderTypeEnum[] orderAscDesc = new OrderTypeEnum[numOrderBy];
	    if (filtro.getOrderAscDesc() == null) {
		for (OrderTypeEnum orderTypeEnum : orderAscDesc) {
		    orderTypeEnum = OrderTypeEnum.ASC;
		}
	    } else {
		if (filtro.getOrderAscDesc().length < numOrderBy) {
		    for (int i = 0; i < orderBy.length; i++) {
			if (i < filtro.getOrderAscDesc().length) {
			    orderAscDesc[i] = filtro.getOrderAscDesc()[i];
			} else {
			    orderAscDesc[i] = OrderTypeEnum.ASC;
			}
		    }
		} else {
		    orderAscDesc = filtro.getOrderAscDesc();
		}
	    }
	    for (int i = 0; i < orderBy.length; i++) {
		if (StringUtils.defaultIfEmpty(orderBy[i], "").equalsIgnoreCase("partitaiva")
			|| StringUtils.defaultIfEmpty(orderBy[i], "").equalsIgnoreCase("codicefiscale")) {
		    if (orderAscDesc[i].equals(OrderTypeEnum.ASC)) {
			ft.addOrder(FilterUtils.orderAsc(orderBy[i], FunctionsEnum.NVL_FUNCTION, "'9999999999999999'"));
		    } else {
			ft.addOrder(FilterUtils.orderDesc(orderBy[i], FunctionsEnum.NVL_FUNCTION, "'9999999999999999'"));
		    }
		} else {
		    ft.addOrder(FilterUtils.order(orderBy[i], orderAscDesc[i]));
		}
	    }
	}
	if (firstResult != null && maxResults != null) {
	    return anagrafeDAO.findByFilterTable(ft, firstResult, maxResults);
	} else {
	    return anagrafeDAO.findByFilterTable(ft);
	}
    }

    @Override
    public List<Anagrafe> findByFilter(AnagrafeFilter filtro) {

	return findByFilter(filtro, null, null);
    }

    private void settaRestriction(FilterRestriction fr, String property, String valore, TipoRicercaEnum tipoRicercaEnum) {

	switch (tipoRicercaEnum) {
	case LIKE:
	    fr.addFilterField(FilterUtils.like(property, valore));
	    break;
	case ENDSWIDTH:
	    fr.addFilterField(FilterUtils.endsWith(property, valore));
	    break;
	case EQUALSIGNORECASE:
	    fr.addFilterField(FilterUtils.equalsIgnoreCase(property, valore));
	    break;
	case EXACT:
	    fr.addFilterField(FilterUtils.equals(property, valore, String.class));
	    break;
	case STARTSWITH:
	    fr.addFilterField(FilterUtils.startsWith(property, valore));
	    break;
	default:
	    fr.addFilterField(FilterUtils.equals(property, valore, String.class));
	    break;
	}
    }

    @Override
    public Anagrafe bindDomainObject(Anagrafe entity, Class<?> idClass, String idPath) {

	AnagrafeBusinessRules rules = (AnagrafeBusinessRules) SigeproBusinessRules.getClassRules(AnagrafeBusinessRules.class);
	boolean forzaInserimentoAnagrafe = false;
	if (rules != null) {
	    forzaInserimentoAnagrafe = rules.getCustomRule(AnagrafeBusinessRules.CustomRuleEnum.forzaInserimentoAnagrafe.name());
	}
	Anagrafe result = null;
	try {
	    result = super.bindDomainObject(entity, idClass, idPath);
	} catch (RecuperoDatiAnagraficaException e) {
	    if (!forzaInserimentoAnagrafe) {
		throw e;
	    }
	}
	if (rules != null) {
	    if (rules.isInsert() || rules.isUpdate()) {
		if (result != null) {
		    if (rules.isUpdate()) {
			this.getModificheAnagrafe(result, entity);
			this.update(result);
		    } else {
			IstanzeBusinessRules istanzeRules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
			boolean isInserimentoDaStc = istanzeRules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name());
			if (isInserimentoDaStc) {
			    _verificaIncongruenze(result, entity, false);
			}
		    }
		} else {
		    if (rules.isInsert()) {
			// validazione minima
			if (entity != null && StringUtils.isNotBlank(entity.getNominativo()) && StringUtils.isNotBlank(entity.getTipoanagrafe())) {
			    this.insert(entity);
			    result = entity;
			}
		    }
		}
	    }
	}
	return result;
    }

    /**
     * Confronta due oggetti anagrafe. Se ci sono modifiche sul secondo, setta i relativi campi nel primo solamente se
     * in questo il valore è nullo
     * 
     * @param dbEntity
     * @param entityDaConfrontare
     */
    private void getModificheAnagrafe(Anagrafe dbEntity, Anagrafe entityDaConfrontare) {

	fixMergeEntityProperties(entityDaConfrontare);
	anagrafeDAO.evict(dbEntity);
	checkModifiedProperties(dbEntity, entityDaConfrontare);
    }

    private void checkModifiedProperties(Anagrafe dbCopy, Anagrafe newValueEntity) {

	Field[] f = dbCopy.getClass().getDeclaredFields();
	for (int i = 0; i < f.length; i++) {
	    try {
		if (!f[i].getName().equalsIgnoreCase("id")) {
		    Object dbCopyValue = null;
		    Object newValueEntityValue = null;
		    Method setDbValueInDbCopy = dbCopy.getClass().getMethod("set" + StringUtils.capitalize(f[i].getName()), f[i].getType());
		    Method getNewValue = newValueEntity.getClass().getMethod("get" + StringUtils.capitalize(f[i].getName()));
		    Method getOldValue = dbCopy.getClass().getMethod("get" + StringUtils.capitalize(f[i].getName()));
		    try {
			boolean copyValue = false;
			dbCopyValue = getOldValue.invoke(dbCopy, new Object[0]);
			if (null != dbCopyValue) {
			    if (dbCopyValue instanceof String) {
				if (StringUtils.isNotBlank((String) dbCopyValue)) {
				    copyValue = true;
				}
			    } else {
				copyValue = true;
			    }
			} else {
			    copyValue = true;
			}
			if (copyValue) {
			    // recupera il valore dal nuovo oggetto
			    newValueEntityValue = getNewValue.invoke(newValueEntity, new Object[0]);
			    if (newValueEntityValue != null) {
				if (newValueEntityValue instanceof String) {
				    // setta il valore
				    if (StringUtils.isNotBlank((String) newValueEntityValue)) {
					setDbValueInDbCopy.invoke(dbCopy, newValueEntityValue);
				    }
				} else {
				    setDbValueInDbCopy.invoke(dbCopy, newValueEntityValue);
				}
			    }
			}
		    } catch (IllegalArgumentException e) {
			log.error("checkModifiedProperties: {}", e.getMessage());
			throw e;
		    } catch (IllegalAccessException e) {
			log.error("checkModifiedProperties: {}", e.getMessage());
			throw new RuntimeException(e);
		    } catch (InvocationTargetException e) {
			log.warn("checkModifiedProperties: {}", e.getMessage());
			// throw new RuntimeException(e);
		    }
		}
	    } catch (SecurityException e) {
		log.error("checkModifiedProperties: {}", e.getMessage());
		throw new RuntimeException(e);
	    } catch (NoSuchMethodException e) {
		log.warn("checkModifiedProperties: {}", e.getMessage());
		// throw new RuntimeException(e);
	    } catch (Exception e) {
		log.warn("checkModifiedProperties:{}, {}", i, e.getMessage());
	    }
	}
    }

    @Override
    protected boolean validateEntity(Anagrafe entity) {

	AnagrafeBusinessRules abr = (AnagrafeBusinessRules) SigeproBusinessRules.getClassRules(AnagrafeBusinessRules.class);
	boolean forzaInserimentoAnagrafe = abr.getCustomRule(AnagrafeBusinessRules.CustomRuleEnum.forzaInserimentoAnagrafe.name());
	if (forzaInserimentoAnagrafe) {
	    return true;
	}
	super.validateEntity(entity);
	ServiceValidationRules validationRule = (ServiceValidationRules) SigeproBusinessRules.getClassRules(ServiceValidationRules.class);
	boolean doBusinessValidation = true;
	if (validationRule != null) {
	    doBusinessValidation = validationRule.getCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name());
	}
	if (doBusinessValidation) {
	    this.validaCFoPiva(entity);
	    this.validaCampiPerTipoAnagrafe(entity);
	}
	return true;
    }

    private void validaCFoPiva(Anagrafe entity) {

	String tipoAnagrafe = entity.getTipoanagrafe();
	ConfigurazioneId id = new ConfigurazioneId(WebConstants.SOFTWARE_TT);
	Configurazione conf = configurazioneService.findById(id);
	boolean codFisPivaObbligatori = false;
	if (conf == null) {
	    throw new BusinessValidationException(
		    "Errore nella configurazione dell'applicativo. Nessuna record presente nella configurazione [" + id + "]");
	} else {
	    codFisPivaObbligatori = BooleanUtils.toBoolean(conf.getCodfisobblig());
	}
	if (codFisPivaObbligatori) {
	    if (tipoAnagrafe.equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
		if (StringUtils.isBlank(entity.getCodicefiscale())) {
		    this.throwValidationMessage(
			    new InvalidValue("field.required", Anagrafe.class, "codicefiscale", entity.getCodicefiscale(), entity));
		}
	    } else if (tipoAnagrafe.equalsIgnoreCase(WebConstants.PERSONA_GIURIDICA)) {
		if (StringUtils.isBlank(entity.getCodicefiscale()) && StringUtils.isBlank(entity.getPartitaiva())) {
		    this.throwValidationMessage(new InvalidValue("service_error.cf_o_piva_obbligatori", Anagrafe.class, "codicefiscale",
			    entity.getCodicefiscale(), entity));
		}
	    }
	}
    }

    /**
     * @param entity
     */
    private void validaCampiPerTipoAnagrafe(Anagrafe entity) {

	if (StringUtils.isNotBlank(entity.getTipoanagrafe())) {
	    List<String> campiDaNonSettarePerTipoAnagrafe = new ArrayList<String>();
	    String tipoAnagrafeDescrizione = "";
	    if (entity.getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
		tipoAnagrafeDescrizione = getMessageFromBundle("label.persona_fisica", new Object[] {});
		// 
		if (StringUtils.isBlank(entity.getNome())) {
		    this.throwValidationMessage(new InvalidValue("field.required", Anagrafe.class, "nome", entity.getNome(), entity));
		}
		// i campi che seguono se impostati devono dare errore se tipoanagrafe = F
		//		if (EntityUtils.getNestedProperty(entity.getFormagiuridica(), "id.codice") != null) {
		//		    campiDaNonSettarePerTipoAnagrafe.add(getMessageFromBundle("label.forma_giuridica", new Object[] {}));
		//		}
		//		if (EntityUtils.getNestedProperty(entity.getComuneregtrib(), "codicecomune") != null) {
		//		    campiDaNonSettarePerTipoAnagrafe.add(getMessageFromBundle("label.comune_reg_trib", new Object[] {}));
		//		}
		//		if (entity.getDataregtrib() != null) {
		//		    String dataRegTrib = getMessageFromBundle("label.data", new Object[] {}) + " "
		//			    + getMessageFromBundle("label.reg_trib", new Object[] {});
		//		    campiDaNonSettarePerTipoAnagrafe.add(dataRegTrib);
		//		}
		//		if (entity.getDatanominativo() != null) {
		//		    campiDaNonSettarePerTipoAnagrafe.add(getMessageFromBundle("label.data_costituzione", new Object[] {}));
		//		}
		//		if (StringUtils.isNotBlank(entity.getProvinciarea())) {
		//		    campiDaNonSettarePerTipoAnagrafe.add(getMessageFromBundle("label.provincia_area", new Object[] {}));
		//		}
		//		if (StringUtils.isNotBlank(entity.getNumiscrrea())) {
		//		    campiDaNonSettarePerTipoAnagrafe.add(getMessageFromBundle("label.numero_iscrizione_rea", new Object[] {}));
		//		}
		//		if (entity.getDataiscrrea() != null) {
		//		    campiDaNonSettarePerTipoAnagrafe.add(getMessageFromBundle("label.data", new Object[] {}) + " R.E.A. ");
		//		}
		// di default settato a False
		//		if (entity.getFlagNoprofit() != null) {
		//		    campiDaNonSettarePerTipoAnagrafe.add(getMessageFromBundle("label.flag_no_profit", new Object[] {}) + " R.E.A. ");
		//		}
	    } else if (entity.getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_GIURIDICA)) {
		tipoAnagrafeDescrizione = getMessageFromBundle("label.persona_giuridica", new Object[] {});
		// i campi che seguono se impostati devono dare errore se tipoanagrafe = G
		if (EntityUtils.getNestedProperty(entity.getComuneNascita(), "codicecomune") != null) {
		    campiDaNonSettarePerTipoAnagrafe.add(getMessageFromBundle("label.comune_nascita", new Object[] {}));
		}
		if (entity.getDatanascita() != null) {
		    campiDaNonSettarePerTipoAnagrafe.add(getMessageFromBundle("label.data_nascita", new Object[] {}));
		}
		if (StringUtils.isNotBlank(entity.getSesso())) {
		    campiDaNonSettarePerTipoAnagrafe.add(getMessageFromBundle("label.sesso", new Object[] {}));
		}
		if (StringUtils.isNotBlank(entity.getNome())) {
		    campiDaNonSettarePerTipoAnagrafe.add(getMessageFromBundle("label.nome", new Object[] {}));
		}
		if (EntityUtils.getNestedProperty(entity.getTitolo(), "id.codice") != null) {
		    campiDaNonSettarePerTipoAnagrafe.add(getMessageFromBundle("label.titolo", new Object[] {}));
		}
		//		if (EntityUtils.getNestedProperty(entity.getCittadinanza(), "codice") != null) {
		//		    campiDaNonSettarePerTipoAnagrafe.add(getMessageFromBundle("label.cittadinanza", new Object[] {}));
		//		}
	    } else {
		// non dovrebbe mai accadere
		throw new BusinessValidationException(
			"Errore nella validazione dell'anagrafica. Il tipo anagrafe [" + entity.getTipoanagrafe() + "] non è gestito");
	    }
	    if (campiDaNonSettarePerTipoAnagrafe.size() > 0) {
		String[] campi = new String[campiDaNonSettarePerTipoAnagrafe.size()];
		campi = campiDaNonSettarePerTipoAnagrafe.toArray(campi);
		String listaCampi = Arrays.toString(campi);
		String messaggioErrore = getMessageFromBundle("service_error.campi_non_validi_tipo_anagrafe",
			new Object[] { tipoAnagrafeDescrizione, listaCampi });
		throw new BusinessValidationException(messaggioErrore);
	    }
	} else {
	    // non dovrebbe mai accadere
	    throw new BusinessValidationException("Errore nella validazione dell'anagrafica. Il tipo anagrafe è obbligatorio");
	}
    }

    @Override
    public Anagrafe insertNewStoricoFromAnagrafe(Anagrafe entity, Anagrafe oldAnagrafe, Date dataInizioValidità) {

	Anagrafestorico anagrafestorico = new Anagrafestorico();
	// array dei campi che non devono essere copiati nell'oggetto copy
	String[] arrayFieldExclude = getArrayFieldExcludes();
	List<String> fielExclude = Arrays.asList(arrayFieldExclude);
	anagrafestorico = copyAnagrafeToAnagrafestorico(oldAnagrafe, anagrafestorico, fielExclude);
	// setto l'anagrafe
	anagrafestorico.setAnagrafe(entity);
	anagrafestorico.setDatainiziovalidita(dataInizioValidità);
	anagrafestorico.setDatafinevalidita(dataInizioValidità);
	// il primo record di anagrafestorico riferito ad una anagrafica
	// avrà la data inizio valità = null che per nostra convenzione significa valida da sempre
	fixMergeStoricoProperties(anagrafestorico);
	//anagrafestoricoDAO.insert(anagrafestorico);
	anagrafestoricoService.insert(anagrafestorico);
	return entity;
    }

    /**
     * @return
     */
    private String[] getArrayFieldExcludes() {

	String[] arrayFieldExclude = { "id", "formagiuridicaId", "titoloId", "serialVersionUID", "descrizioneRichiedente", "descrizioneResidenza",
		"istanze", "tecniciIstanzes", "aziendaIstanzes", "autorizzazionis", "autorizzazioniSubentris", "anagrafestoricos", "corrispondenza",
		"richiedente", "passwordClear", "istanzerichiedentisForRichiedente", "istanzerichiedentisForAnagrafeCollegata",
		"istanzerichiedentisForProcuratore", "scadenzes", "emailanagrs", "anagrafedocumentis", "anagrafeFDomandes", "societaFDomandes",
		"subentroFDomandes", "bachecalavorooffros", "bachecalavorocercas", "cdsinvitati2s", "concessionarimercatipresenzeDs",
		"mercatipresenzeDs", "registrazionis", "registrazioniInOuts", "mercatipresenzeStoricos", "anagrafemercatipresenzes",
		"anagrafedyn2datis", "permcdsinvitatis", "anagrafedyn2modellits", "foRichiestes" };
	return arrayFieldExclude;
    }

    public FilterTable createFilterTableByEntity(Anagrafe anagrafe) {

	if (anagrafe == null) {
	    throw new IllegalArgumentException("Anagrafe non può essere null");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	if (anagrafe.getId().getCodice() != null) {
	    restriction.addFilterField(FilterUtils.equals("id.codice", anagrafe.getId().getCodice(), Integer.class));
	}
	if (anagrafe.getTipologia() != null) {
	    restriction.addFilterField(FilterUtils.equals("tipologia", anagrafe.getTipologia(), Integer.class));
	}
	if (anagrafe.getTipoanagrafe() != null) {
	    restriction.addFilterField(FilterUtils.equals("tipoanagrafe", anagrafe.getTipoanagrafe(), String.class));
	}
	if (anagrafe.getFlagDisabilitato() != null) {
	    restriction.addFilterField(FilterUtils.equals("flagDisabilitato", anagrafe.getFlagDisabilitato(), String.class));
	}
	// Gestisce la ricerca sul campo transient richiedente
	FilterRestriction filterRestrictionRichiedente = new FilterRestriction();
	// filterRestrictionRichiedente.setAndOrRestriction(AndOrRestriction.OR);
	if (StringUtils.isNotBlank(anagrafe.getNominativo())) {
	    filterRestrictionRichiedente.addFilterField(FilterUtils.like("nominativo", anagrafe.getNominativo()));
	}
	if (StringUtils.isNotBlank(anagrafe.getNome())) {
	    filterRestrictionRichiedente.addFilterField(FilterUtils.like("nome", anagrafe.getNome()));
	}
	if (StringUtils.isNotBlank(anagrafe.getPartitaiva())) {
	    filterRestrictionRichiedente.addFilterField(FilterUtils.like("partitaiva", anagrafe.getPartitaiva()));
	}
	if (StringUtils.isNotBlank(anagrafe.getCodicefiscale())) {
	    filterRestrictionRichiedente.addFilterField(FilterUtils.like("codicefiscale", anagrafe.getCodicefiscale()));
	}
	// Gestisce la ricerca sul campo transient residenza
	FilterRestriction filterRestrictionResidenza = new FilterRestriction();
	filterRestrictionResidenza.setAndOrRestriction(AndOrRestriction.OR);
	if (StringUtils.isNotBlank(anagrafe.getIndirizzo())) {
	    filterRestrictionResidenza.addFilterField(FilterUtils.like("indirizzo", anagrafe.getIndirizzo()));
	}
	if (StringUtils.isNotBlank(anagrafe.getCitta())) {
	    filterRestrictionResidenza.addFilterField(FilterUtils.like("citta", anagrafe.getCitta()));
	}
	if (StringUtils.isNotBlank(anagrafe.getProvincia())) {
	    filterRestrictionResidenza.addFilterField(FilterUtils.like("provincia", anagrafe.getProvincia()));
	}
	if (anagrafe.getComuneResidenza() != null && StringUtils.isNotBlank(anagrafe.getComuneResidenza().getComune())) {
	    filterRestrictionResidenza.addFilterField(FilterUtils.like("comune", anagrafe.getComuneResidenza().getComune(), "comuneResidenza"));
	}
	// Gestisce la ricerca sul campo transient corrispondenza
	FilterRestriction filterRestrictionCorrispondenza = new FilterRestriction();
	filterRestrictionCorrispondenza.setAndOrRestriction(AndOrRestriction.OR);
	if (StringUtils.isNotBlank(anagrafe.getIndirizzo())) {
	    filterRestrictionResidenza.addFilterField(FilterUtils.like("indirizzocorrispondenza", anagrafe.getIndirizzo()));
	}
	if (StringUtils.isNotBlank(anagrafe.getCitta())) {
	    filterRestrictionResidenza.addFilterField(FilterUtils.like("cittacorrispondenza", anagrafe.getCitta()));
	}
	if (StringUtils.isNotBlank(anagrafe.getProvincia())) {
	    filterRestrictionResidenza.addFilterField(FilterUtils.like("provinciacorrispondenza", anagrafe.getProvincia()));
	}
	if (anagrafe.getComuneResidenza() != null && StringUtils.isNotBlank(anagrafe.getComuneResidenza().getComune())) {
	    filterRestrictionResidenza.addFilterField(FilterUtils.like("comune", anagrafe.getComuneResidenza().getComune(), "comunecorrispondenza"));
	}
	Set<FilterRestriction> restrictions = new HashSet<FilterRestriction>();
	restrictions.add(filterRestrictionRichiedente);
	restrictions.add(filterRestrictionResidenza);
	restrictions.add(filterRestrictionCorrispondenza);
	restrictions.add(restriction);
	filterTable.setRestrictions(restrictions);
	filterTable.addOrder(FilterUtils.order("nominativo", OrderTypeEnum.ASC));
	return filterTable;
    }

    @Override
    public List<Anagrafe> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	return anagrafeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public int countRecord(FilterTable filterTable) {

	return anagrafeDAO.countRecord(filterTable);
    }

    @Override
    public List<Anagrafe> findByDescrizione(String descrizione, String tipoAnagrafe, AnagrafeEnum statoAnagrafe, Integer firstResult,
	    Integer maxResult) {

	return anagrafeDAO.findByDescrizione(descrizione, tipoAnagrafe, statoAnagrafe, firstResult, maxResult, false);
    }

    @Override
    public void convertPersonaFisicaToGiuridica(Anagrafe anagrafe) {

	// inserisce l'anagrafe che si sta convertendo nello storico
	// per non perdere i dati.
	this.inserisciAnagrafeNelloStorico(anagrafe);
	// campi da annullare perchè non hanno senso
	// in una persona giuridica
	anagrafe.setComuneNascita(null);
	anagrafe.setDatanascita(null);
	anagrafe.setSesso(null);
	anagrafe.setTitolo(null);
	anagrafe.setCittadinanza(null);
	// il nominativo sarà composta da nominativo + nome
	anagrafe.setNominativo(anagrafe.getNominativo() + " " + anagrafe.getNome());
	anagrafe.setNome("");
	anagrafe.setTipoanagrafe(WebConstants.PERSONA_GIURIDICA);
	this.update(anagrafe);
    }

    @Override
    public void convertPersonaGiuridicaToFisica(Anagrafe anagrafe) {

	// inserisce l'anagrafe che si sta convertendo nello storico
	// per non perdere i dati.
	this.inserisciAnagrafeNelloStorico(anagrafe);
	// campi da annullare perchè non hanno senso
	// in una persona giuridica
	anagrafe.setFormagiuridica(null);
	anagrafe.setRegtrib(null);
	anagrafe.setComuneregtrib(null);
	anagrafe.setDataregtrib(null);
	anagrafe.setDatanominativo(null);
	anagrafe.setProvinciarea(null);
	anagrafe.setDataiscrrea(null);
	anagrafe.setNumiscrrea(null);
	anagrafe.setFlagNoprofit(Boolean.FALSE);
	anagrafe.setTipoanagrafe(WebConstants.PERSONA_FISICA);
	//Splitto il nominativo usando come regex gli spazi.
	String nominativo = anagrafe.getNominativo();
	String field[] = nominativo.split(" ");
	String newNominativo = "";
	//Se l'array recuperato dallo split è
	//	1- >1 nominantivo sarà dato dalla concatenzaione delle n-1 strighe dell'array 
	//	      e il nome dall'ultima stringa dell'array.
	//	2- =1 nominativo sarà dall'unica stringa presente nell'array e nome dal "."
	if (field.length > 1) {
	    for (int i = 0; i < field.length - 1; i++) {
		newNominativo = newNominativo + field[i] + " ";
	    }
	    anagrafe.setNominativo(newNominativo);
	    anagrafe.setNome(field[field.length - 1]);
	} else {
	    anagrafe.setNome(".");
	}
	// Controllo dalla configuraione se il codice fiscale sia obbligatorio
	ConfigurazioneId id = new ConfigurazioneId(WebConstants.SOFTWARE_TT);
	Configurazione conf = configurazioneService.findById(id);
	// Codice fiscale obbligatorio
	if (conf != null && conf.getCodfisobblig()) {
	    // Se non è presente lo sostituisco con la partita iva
	    if (StringUtils.isBlank((anagrafe.getCodicefiscale()))) {
		// se non è presente la partita iva rilanzio l'errore
		if (StringUtils.isNotBlank(anagrafe.getPartitaiva())) {
		    anagrafe.setCodicefiscale(anagrafe.getPartitaiva());
		} else {
		    this.throwValidationMessage(
			    new InvalidValue("field.required", Anagrafe.class, "codicefiscale", anagrafe.getCodicefiscale(), anagrafe));
		}
	    }
	}
	this.update(anagrafe);
    }

    private void inserisciAnagrafeNelloStorico(Anagrafe anagrafe) {

	String[] arrayFieldExclude = getArrayFieldExcludes();
	List<String> fielExclude = Arrays.asList(arrayFieldExclude);
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("anagrafeId", anagrafe.getId().getCodice(), Integer.class));
	ft.addRestriction(restriction);
	//	List<Anagrafestorico> storicos = anagrafestoricoDAO.findByFilterTable(ft);
	List<Anagrafestorico> storicos = anagrafestoricoDAO.findByFilterTable(ft);
	// Controllo che l'anagrafe che si sta inserendo non sia cambiata nei
	// suoi campi "rilevanti" (nome, nominativo,partitaiva,codicefiscale,indirizzo)
	// SE si faccio l'insert in anagrafica storico.
	if (!storicos.isEmpty()) {
	    // ricerco l'ultimo record inserito nella tabella anagrafe storico per l'anagrafe passata
	    // e setto la data fine validita a quella odierna.
	    Calendar dataOdierna = Calendar.getInstance();
	    //	    Anagrafestorico oldAnagrafestorico = anagrafestoricoDAO.findUltimoAnagrafestoricoByAnagrafe(anagrafe);
	    Anagrafestorico oldAnagrafestorico = anagrafestoricoService.findUltimoAnagrafestoricoByAnagrafe(anagrafe);
	    oldAnagrafestorico.setDatafinevalidita(dataOdierna.getTime());
	    //	    anagrafestoricoDAO.update(oldAnagrafestorico);
	    anagrafestoricoService.update(oldAnagrafestorico);
	    // creo il nuovo record in anagrafe storico che indica quello corrente in anagrafe.
	    Anagrafestorico anagrafestorico = new Anagrafestorico();
	    anagrafestorico = copyAnagrafeToAnagrafestorico(anagrafe, anagrafestorico, fielExclude);
	    anagrafestorico.setDatainiziovalidita(dataOdierna.getTime());
	    anagrafestorico.setAnagrafe(anagrafe);
	    dataIntegrationAnagrafeStorico(anagrafestorico, false);
	    //anagrafestoricoDAO.insert(anagrafestorico);
	    anagrafestoricoService.insert(anagrafestorico);
	} else {
	    // dati DB incoerenti, operazioni di bonifica
	    Anagrafestorico anagrafestorico = new Anagrafestorico();
	    anagrafestorico = copyAnagrafeToAnagrafestorico(anagrafe, anagrafestorico, fielExclude);
	    // setto l'angrafe
	    anagrafestorico.setAnagrafe(anagrafe);
	    // il primo record di anagrafestorico riferito a una anagrafica
	    // avrà la data inizio valità uguale a quella attuale			
	    dataIntegrationAnagrafeStorico(anagrafestorico, false);
	    // anagrafestoricoDAO.insert(anagrafestorico);
	    anagrafestoricoService.insert(anagrafestorico);
	}
    }

    @Override
    public Anagrafe findDatiAnagrafeDaWs(String cfPivaRicercaWs, Anagrafe anagrafe) {

	//	Anagrafe anagrafeSigeproModificata = null;
	//	if (StringUtils.isNotBlank(cfPivaRicercaWs)) {
	//	    String token = ORMHelper.getToken();
	//	    WsAnagrafe2Soap port = null;
	//	    it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafeDaws = new it.gruppoinit.wsanagrafe2.schema.Anagrafe();
	//	    try {
	//		if (StringUtils.defaultIfEmpty(anagrafe.getTipoanagrafe(), WebConstants.PERSONA_FISICA).equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
	//		    port = getPortWS(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_URL_RICERCA_PF);
	//		    anagrafeDaws = port.getPersonaFisica(token, cfPivaRicercaWs);
	//		} else {
	//		    port = getPortWS(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_URL_RICERCA_PG);
	//		    anagrafeDaws = port.getPersonaGiuridica(token, cfPivaRicercaWs);
	//		}
	//		if (anagrafeDaws != null) {
	//		    anagrafeSigeproModificata = getAnagrafeSigeproConModifiche(anagrafeDaws, anagrafe, false);
	//		    return anagrafeSigeproModificata;
	//		} else {
	//		    throw new BusinessValidationException("Nessun record trovato per i criteri selezionati");
	//		}
	//	    } catch (Exception e) {
	//		log.error("findDatiAnagrafeDaWs: Errore nel recupero  dell'anagrafica={}", e.getMessage());
	//		throw new RuntimeException("Errore nel recupero  dell'anagrafica=" + e.getMessage(), e);
	//	    }
	//	    // Metodo che controlla i campi che potrebbero essere stati modificati;
	//	    // se il campo è stato modificato viene inserito quello recuperato dal WS
	//	    // altrimenti il campo rimane inalterato
	//	}
	//	return anagrafeSigeproModificata;
	return findDatiAnagrafeDaWs(cfPivaRicercaWs, anagrafe, true);
    }

    @Override
    public Anagrafe findDatiAnagrafeDaWs(String cfPivaRicercaWs, Anagrafe anagrafe, boolean rilanciaEccezione) {

	Anagrafe anagrafeSigeproModificata = null;
	if (StringUtils.isNotBlank(cfPivaRicercaWs)) {
	    String token = ORMHelper.getToken();
	    WsAnagrafe2Soap port = null;
	    it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafeDaws = new it.gruppoinit.wsanagrafe2.schema.Anagrafe();
	    try {
		if (StringUtils.defaultIfEmpty(anagrafe.getTipoanagrafe(), WebConstants.PERSONA_FISICA)
			.equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
		    port = getPortWS(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_URL_RICERCA_PF);
		    anagrafeDaws = port.getPersonaFisica(token, cfPivaRicercaWs);
		} else {
		    port = getPortWS(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_URL_RICERCA_PG);
		    anagrafeDaws = port.getPersonaGiuridica(token, cfPivaRicercaWs);
		}
		if (anagrafeDaws != null) {
		    anagrafeSigeproModificata = getAnagrafeSigeproConModifiche(anagrafeDaws, anagrafe, false);
		    return anagrafeSigeproModificata;
		} else {
		    if (rilanciaEccezione) {
			throw new BusinessValidationException("Nessun record trovato per i criteri selezionati");
		    }
		}
	    } catch (Exception e) {
		log.error("findDatiAnagrafeDaWs: Errore nel recupero  dell'anagrafica={}", e.getMessage());
		if (rilanciaEccezione) {
		    throw new RuntimeException("Errore nel recupero  dell'anagrafica=" + e.getMessage(), e);
		}
	    }
	    // Metodo che controlla i campi che potrebbero essere stati modificati;
	    // se il campo è stato modificato viene inserito quello recuperato dal WS
	    // altrimenti il campo rimane inalterato
	} else {
	    throw new BusinessValidationException("Nessun record trovato per i criteri selezionati");
	}
	// altrimenti il campo rimane inalterato
	return anagrafeSigeproModificata;
    }

    @Override
    public List<Anagrafestorico> findStorico(Anagrafe anagrafe) {

	return anagrafestoricoService.findStoricoByAnagrafe(anagrafe);
    }

    @Override
    public Anagrafestorico findAnagrafeStoricoById(PkId id) {

	return anagrafestoricoService.findById(id);
    }

    @Override
    public List<AnagrafeRicercaBean> findRichiedentiByIstanza(String descrizione, Integer codiceIstanza, String tipoAnagrafe) {

	return anagrafeDAO.findRichiedentiByIstanza(descrizione, codiceIstanza, tipoAnagrafe);
    }

    @Override
    public Anagrafe findAnagrafeAggiornataByFE(Integer codiceFoRichiesta) {

	FoRichieste foRichieste = foRichiesteService.findById(new PkId(codiceFoRichiesta));
	Anagrafe anagrafeSigeproModificata = null;
	Anagrafe oldAnagrafe = this.findById(foRichieste.getAnagrafe().getId());
	anagrafeDAO.evict(oldAnagrafe);
	//	it.sigepro.init.Anagrafe anagrafeFE = new it.sigepro.init.Anagrafe();
	it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafeFE = new it.gruppoinit.wsanagrafe2.schema.Anagrafe();
	Oggetti oggetto = oggettiService.findById(foRichieste.getOggetto().getId());
	leggiXmlFoRichiesta(oggetto.getOggetto(), anagrafeFE);
	anagrafeSigeproModificata = getAnagrafeSigeproConModifiche(anagrafeFE, oldAnagrafe, true);
	return anagrafeSigeproModificata;
    }

    @Override
    public void leggiXmlFoRichiesta(byte[] file, it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafeFE) {

	SAXBuilder builder = new SAXBuilder();
	try {
	    ByteArrayInputStream in = new ByteArrayInputStream(file);
	    Document document = builder.build(in);
	    //Prendo la radice 
	    Element root = document.getRootElement();
	    //Estraggo i figli dalla radice 
	    List children = root.getChildren();
	    Iterator<Element> iterator = children.iterator();
	    //Per ogni figlio 
	    while (iterator.hasNext()) {
		Element item = (Element) iterator.next();
		if (item.getName().equalsIgnoreCase("CODICEANAGRAFE")) {//..
		    anagrafeFE.setCODICEANAGRAFE(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("IDCOMUNE")) {//..
		    anagrafeFE.setIDCOMUNE(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("NOMINATIVO")) {//..
		    anagrafeFE.setNOMINATIVO(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("FORMAGIURIDICA")) {//..
		    anagrafeFE.setFORMAGIURIDICA(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("TIPOLOGIA")) {//..
		    anagrafeFE.setTIPOLOGIA(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("INDIRIZZO")) {//..
		    anagrafeFE.setINDIRIZZO(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("CITTA")) {//..
		    anagrafeFE.setCITTA(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("CAP")) {//..
		    anagrafeFE.setCAP(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("PROVINCIA")) {//..
		    anagrafeFE.setPROVINCIA(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("TELEFONO")) {//..
		    anagrafeFE.setTELEFONO(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("TELEFONOCELLULARE")) {//..
		    anagrafeFE.setTELEFONOCELLULARE(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("FAX")) {//..
		    anagrafeFE.setFAX(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("PARTITAIVA")) {//..
		    anagrafeFE.setPARTITAIVA(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("CODICEFISCALE")) {//..
		    anagrafeFE.setCODICEFISCALE(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("NOTE")) {//..
		    anagrafeFE.setNOTE(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("EMAIL")) {//..
		    anagrafeFE.setEMAIL(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("REGDITTE")) {//..
		    anagrafeFE.setREGDITTE(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("REGTRIB")) {//..
		    anagrafeFE.setREGTRIB(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("CODCOMREGDITTE")) {//..
		    anagrafeFE.setCODCOMREGDITTE(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("CODCOMREGTRIB")) {//..
		    anagrafeFE.setCODCOMREGTRIB(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("CODCOMNASCITA")) {//..
		    anagrafeFE.setCODCOMNASCITA(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("DATANASCITA")) {//..
		    GregorianCalendar _dateDataNascita = gestData(item.getValue());
		    if (_dateDataNascita != null) {
			XMLGregorianCalendar dateDataNascita = Utilities.getXMLGregorianCalendar(_dateDataNascita);
			anagrafeFE.setDATANASCITA(dateDataNascita);
		    }
		}
		if (item.getName().equalsIgnoreCase("DATAREGDITTE")) {//..
		    GregorianCalendar _dateRegDitte = gestData(item.getValue());
		    if (_dateRegDitte != null) {
			XMLGregorianCalendar dateRegDitte = Utilities.getXMLGregorianCalendar(_dateRegDitte);
			anagrafeFE.setDATAREGDITTE(dateRegDitte);
		    }
		}
		if (item.getName().equalsIgnoreCase("DATAREGTRIB")) {//..
		    GregorianCalendar _dateRegTrib = gestData(item.getValue());
		    if (_dateRegTrib != null) {
			XMLGregorianCalendar dateRegTrib = Utilities.getXMLGregorianCalendar(_dateRegTrib);
			anagrafeFE.setDATAREGTRIB(dateRegTrib);
		    }
		}
		if (item.getName().equalsIgnoreCase("INVIOEMAIL")) {//..
		    anagrafeFE.setINVIOEMAIL(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("SESSO")) {//..
		    anagrafeFE.setSESSO(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("NOME")) {//..
		    anagrafeFE.setNOME(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("TITOLO")) {//..
		    anagrafeFE.setTITOLO(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("TIPOANAGRAFE")) {//..
		    anagrafeFE.setTIPOANAGRAFE(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("DATANOMINATIVO")) {//..
		    GregorianCalendar _dateDataNominativo = gestData(item.getValue());
		    if (_dateDataNominativo != null) {
			XMLGregorianCalendar dateDataNominativo = Utilities.getXMLGregorianCalendar(_dateDataNominativo);
			anagrafeFE.setDATANOMINATIVO(dateDataNominativo);
		    }
		}
		if (item.getName().equalsIgnoreCase("INVIOEMAILTEC")) {//..
		    anagrafeFE.setINVIOEMAILTEC(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("CODICECITTADINANZA")) {//..
		    anagrafeFE.setCODICECITTADINANZA(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("COMUNERESIDENZA")) {//..
		    anagrafeFE.setCOMUNERESIDENZA(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("PASSWORD")) {//..
		    anagrafeFE.setPASSWORD(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("INDIRIZZOCORRISPONDENZA")) {//..
		    anagrafeFE.setINDIRIZZOCORRISPONDENZA(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("CITTACORRISPONDENZA")) {//..
		    anagrafeFE.setCITTACORRISPONDENZA(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("CAPCORRISPONDENZA")) {//..
		    anagrafeFE.setCAPCORRISPONDENZA(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("PROVINCIACORRISPONDENZA")) {//..
		    anagrafeFE.setPROVINCIACORRISPONDENZA(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("COMUNECORRISPONDENZA")) {//..
		    anagrafeFE.setCOMUNECORRISPONDENZA(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("PROVINCIAREA")) {//..
		    anagrafeFE.setPROVINCIAREA(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("NUMISCRREA")) {//..
		    anagrafeFE.setNUMISCRREA(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("DATAISCRREA")) {//..
		    GregorianCalendar _dateDataIscREA = gestData(item.getValue());
		    if (_dateDataIscREA != null) {
			XMLGregorianCalendar dateDataIscREA = Utilities.getXMLGregorianCalendar(_dateDataIscREA);
			anagrafeFE.setDATAISCRREA(dateDataIscREA);
		    }
		}
		if (item.getName().equalsIgnoreCase("FLAG_NOPROFIT")) {//..
		    //		    anagrafeFE.setFLAG_NOPROFIT(item.getValue());
		    anagrafeFE.setFLAGNOPROFIT(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("FLAG_DISABILITATO")) {//..
		    //		    anagrafeFE.setFLAG_DISABILITATO(item.getValue());
		    anagrafeFE.setFLAGDISABILITATO(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("DATA_DISABILITATO")) {//..
		    GregorianCalendar _dateDataDisabilitato = gestData(item.getValue());
		    if (_dateDataDisabilitato != null) {
			XMLGregorianCalendar dateDataDisabilitato = Utilities.getXMLGregorianCalendar(_dateDataDisabilitato);
			anagrafeFE.setDATADISABILITATO(dateDataDisabilitato);
		    }
		}
		if (item.getName().equalsIgnoreCase("username")) {//..
		    anagrafeFE.setUsername(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("CODICEELENCOPRO")) {//..
		    anagrafeFE.setCODICEELENCOPRO(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("NUMEROELENCOPRO")) {//..
		    anagrafeFE.setNUMEROELENCOPRO(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("PROVINCIAELENCOPRO")) {//..
		    anagrafeFE.setPROVINCIAELENCOPRO(item.getValue());
		}
		if (item.getName().equalsIgnoreCase("pec")) {//..
		    anagrafeFE.setPec(item.getValue());
		}
		//		if (item.getName().equalsIgnoreCase("foUtenteTester")) {//..
		//
		//		}
		//		if (item.getName().equalsIgnoreCase("titoloClass")) {//..
		//		    anagrafeFE.setTitoloClass(item.getValue());
		//		}
		//		if (item.getName().equalsIgnoreCase("elencoProfessionale")) {//..
		//		    anagrafeFE.setElencoProfessionale(item.getValue());
		//		}
		//		if (item.getName().equalsIgnoreCase("formaGiuridicaClass")) {//..
		//		    anagrafeFE.setFormaGiuridicaClass(item.getValue());
		//		}
		//		if (item.getName().equalsIgnoreCase("anagrafeDocumenti")) {//..
		//		    
		//		}
		//		if (item.getName().equalsIgnoreCase("comuneNascita")) {//..
		//		    anagrafeFE.setComuneNascita(item.getValue());
		//		}
		//		if (item.getName().equalsIgnoreCase("comuneRegDitte")) {//..
		//		    anagrafeFE.setComuneRegDitte(item.getValue());
		//		}
		//		if (item.getName().equalsIgnoreCase("comuneRegTrib")) {//..
		//		    anagrafeFE.setComuneRegTrib(item.getValue());
		//		}
		//		if (item.getName().equalsIgnoreCase("comuneCorrispondenza")) {//..
		//		    item
		//		}
		//		if (item.getName().equalsIgnoreCase("comuneResidenza")) {//..
		//		}
		//		if (item.getName().equalsIgnoreCase("cittadinanza")) {//..
		//		}
	    }
	} catch (Exception e) {
	    System.err.println("Errore durante la lettura dal file");
	    e.printStackTrace();
	}
    }

    private static GregorianCalendar gestData(String value) {

	if (StringUtils.isNotBlank(value)) {
	    GregorianCalendar c = Utilities.getDate(value, "yyyy-MM-dd'T'HH:mm:ss");
	    return c;
	}
	return null;
    }

    @Override
    public Anagrafe popolateAnagrafeByFoRichiesta(Integer codiceFoRichiesta) {

	Anagrafe result = new Anagrafe();
	FoRichieste foRichieste = foRichiesteService.findById(new PkId(codiceFoRichiesta));
	//it.sigepro.init.Anagrafe anagrafeFE = new it.sigepro.init.Anagrafe();
	it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafeFE = new it.gruppoinit.wsanagrafe2.schema.Anagrafe();
	Oggetti oggetto = oggettiService.findById(foRichieste.getOggetto().getId());
	leggiXmlFoRichiesta(oggetto.getOggetto(), anagrafeFE);
	result = getAnagrafeSigeproConModifiche(anagrafeFE, result, false);
	return result;
    }

    @Override
    public void deleteAnagrafeStorico(Integer codiceAnagrafeStorico) {

	//	Anagrafestorico anagrafeStorico = anagrafestoricoDAO.findById(new PkId(codiceAnagrafeStorico));
	Anagrafestorico anagrafeStorico = anagrafestoricoService.findById(new PkId(codiceAnagrafeStorico));
	if (isDeleteAnagrafeStoricoAllowed(anagrafeStorico)) {
	    //	    anagrafestoricoDAO.ricalcoloStoricoAnagrafiche(anagrafeStorico);
	    anagrafestoricoService.ricalcoloStoricoAnagrafiche(anagrafeStorico);
	    //	    anagrafestoricoDAO.delete(anagrafeStorico);
	    anagrafestoricoService.delete(anagrafeStorico);
	}
    }

    private boolean isDeleteAnagrafeStoricoAllowed(Anagrafestorico anagrafeStorico) {

	boolean isDelete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// Controlla che non sia utilizzato in istanze sia come  richiedentestorico; titolarelegalestorico; professionistastorico;
	List<Istanze> istanzes = istanzeService.findProfessionistaOrRichiedenteOrTitLegaleStorico(anagrafeStorico);
	if (!istanzes.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZE", null));
	}
	// Controlla che non sia utilizzato in istanzerichiedenti sia come  richiedentestorico; anagrafeCollegatastorico; procuratorestorico;
	List<Istanzerichiedenti> istanzerichiedentis = istanzerichiedentiService.findAnagrafeCollOrRichiedenteOrProcuratoreStorico(anagrafeStorico);
	if (!istanzerichiedentis.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZE-RICHIEDENTE", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return isDelete;
    }

    @Override
    public List<Integer> findCodiciAnagrafe() {

	return anagrafeDAO.findCodiciAnagrafe();
    }

    @Override
    public AnagrafeAvvisiHelper findAvvisiAnagrafe(Integer codiceAnagrafe) {

	AnagrafeAvvisiHelper h = new AnagrafeAvvisiHelper();
	h.setInterdizione("");
	h.setAvvisi(scadenzeService.findExistsAvvisiForAnagrafe(codiceAnagrafe, true));
	List<Scadenze> scadenzes = scadenzeService.findAvvisiForAnagrafe(codiceAnagrafe, ScadenzecategoriebaseEnum.INTERDIZIONE);
	if (!scadenzes.isEmpty()) {
	    String soggettoInterdetto = this.getMessageFromBundle("label.soggetto_interdetto_fino_al", null);
	    h.setInterdizione(soggettoInterdetto + " " + Utilities.formatDate(scadenzes.get(0).getDatascadenza(), false));
	}
	return h;
    }

    @Override
    public Anagrafedocumenti insertVisuraParix(Integer codiceAnagrafe, Integer codiceIstanza) {

	Istanze istanza = null;
	if (codiceIstanza != null) {
	    istanza = istanzeService.findById(new PkId(codiceIstanza));
	}
	Anagrafe azienda = this.findById(new PkId(codiceAnagrafe));
	if (azienda == null) {
	    log.error("insertVisuraParix# anagrafe con codice {} non trovata", codiceAnagrafe);
	    throw new RuntimeException("L'anagrafe con codice " + codiceAnagrafe + " non è stata trovata");
	}
	if (!WebConstants.PERSONA_GIURIDICA.equals(azienda.getTipoanagrafe())) {
	    log.error("insertVisuraParix# anagrafe {} non è una persona giuridica ", azienda.getDescrizioneRichiedente());
	    throw new RuntimeException("L'anagrafe [" + azienda.getDescrizioneRichiedente() + "] non è una persona giuridica");
	}
	String provinciaREA = StringUtils.defaultString(azienda.getProvinciarea()).trim();
	String numeroREA = StringUtils.defaultString(azienda.getNumiscrrea()).trim();
	if (StringUtils.isBlank(provinciaREA) || StringUtils.isBlank(numeroREA)) {
	    throw new RuntimeException("L'anagrafe [" + azienda.getDescrizioneRichiedente() +
				       "] non ha definito i campi \"provincia rea\" e/o \"numero iscrizione rea\" ");
	}
	try {
	    if (log.isDebugEnabled()) {
		log.debug("insertVisuraParix# chiamo il servizio paric");
	    }
	    //String result = parixGateWsClient.getDettaglioImpresa(provinciaREA, numeroREA);
	    String result = this.getDettaglioImpresa(provinciaREA, numeroREA);
	    if (log.isDebugEnabled()) {
		log.debug("insertVisuraParix# chiamata effettuata risultato: {}", result);
	    }
	    try {
		String esito = Utilities.getValueFromXml(result, "/RISPOSTA/HEADER/ESITO");
		if ("KO".equalsIgnoreCase(esito)) {
		    String errore = Utilities.getValueFromXml(result, "/RISPOSTA/DATI/ERRORE/MSG_ERR");
		    log.error("insertVisuraParix# esito della chiamta KO: {}", errore);
		    throw new RuntimeException("Si è verificato un errore nel recupero del file di visura. Dettaglio errore: " + errore);
		}
	    } catch (XPathExpressionException e1) {
		log.error("insertVisuraParix# {}", e1);
		throw new RuntimeException("Si è verificato un errore nel recupero del file di visura: XML non corretto" + e1.getMessage(), e1);
	    } catch (ParserConfigurationException e1) {
		log.error("insertVisuraParix# {}", e1);
		throw new RuntimeException("Si è verificato un errore nel recupero del file di visura: XML non corretto" + e1.getMessage(), e1);
	    } catch (SAXException e1) {
		log.error("insertVisuraParix# {}", e1);
		throw new RuntimeException("Si è verificato un errore nel recupero del file di visura: XML non corretto" + e1.getMessage(), e1);
	    } catch (IOException e1) {
		log.error("insertVisuraParix# {}", e1);
		throw new RuntimeException("Si è verificato un errore nel recupero del file di visura: XML non corretto" + e1.getMessage(), e1);
	    }
	    if (log.isDebugEnabled()) {
		log.debug("insertVisuraParix# inserisco l'oggetto");
	    }
	    Oggetti oggetto = new Oggetti();
	    try {
		oggetto.setOggetto(result.getBytes("UTF-8"));
	    } catch (UnsupportedEncodingException e) {
		oggetto.setOggetto(result.getBytes());
	    }
	    oggetto.setNomefile("VisuraAzienda.xml");
	    oggettiService.insert(oggetto);
	    if (log.isDebugEnabled()) {
		log.debug("insertVisuraParix# inserisco il documento");
	    }
	    Anagrafedocumenti newDoc = new Anagrafedocumenti();
	    // newDoc.setRifdocumento("Visura azienda da Infocamere");
	    newDoc.setDataregistrazione(Calendar.getInstance().getTime());
	    newDoc.setDatainiziovalidita(newDoc.getDataregistrazione());
	    newDoc.setAnagrafe(azienda);
	    newDoc.setIstanza(istanza);
	    newDoc.setOggetto(oggetto);
	    newDoc.setFlagXmlvisuraparix(Boolean.TRUE);
	    anagrafedocumentiService.insert(newDoc);
	    if (log.isDebugEnabled()) {
		log.debug("insertVisuraParix# effettuo la redirect");
	    }
	    return newDoc;
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException("Si è verificato un errore nel recupero del file di visura: " + e.getMessage(), e);
	}
    }

    @Override
    public List<Anagrafe> findByFormegiuridiche(Integer codiceFormagiuridica, int firstResult, int maxResults) {

	if (codiceFormagiuridica == null) {
	    throw new IllegalArgumentException("findByFormegiuridiche: il parametro codiceFormagiuridica e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("formagiuridicaId", codiceFormagiuridica, Integer.class));
	filterTable.addRestriction(fr);
	return anagrafeDAO.findByFilterTable(filterTable, firstResult, maxResults);
    }

    @Override
    public List<Anagrafe> findByTitoli(Integer codiceTitolo, int firstResult, int maxResults) {

	if (codiceTitolo == null) {
	    throw new IllegalArgumentException("findByTitoli: il parametro codiceTitolo e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("titoloId", codiceTitolo, Integer.class));
	filterTable.addRestriction(fr);
	return anagrafeDAO.findByFilterTable(filterTable, firstResult, maxResults);
    }

    @Override
    public void clear() {

	anagrafeDAO.clear();
    }

    @Override
    public void updateAbiltaOrDisabilita(Integer codiceanagrafe, Boolean statoAttuale) {

	if (!BooleanUtils.isTrue(statoAttuale)) {
	    validaDisabilitaAnagrafe(codiceanagrafe);
	}
	anagrafeDAO.updateAbiltaOrDisabilita(codiceanagrafe, statoAttuale);
    }

    private void validaDisabilitaAnagrafe(Integer codiceanagrafe) {

	if (!borsellinoDAO.findByCodiceAnagrafe(codiceanagrafe).isEmpty()) {
	    throw new BusinessValidationException("Non è possibile disabilitare una anagrafe con abbonamenti/borsellini attivi");
	}
    }

    @Override
    public boolean inviaMailUtente(Mailtipo m, Integer codiceAnagrafe, boolean forzaCreazionePassword) throws FunzioneBusinessRemotaException {

	Anagrafe a = this.findById(new PkId(codiceAnagrafe));
	String passwordClear = Utilities.generaPassword(8);
	if (StringUtils.isNotBlank(a.getEmail()) || StringUtils.isNotBlank(a.getPec())) {
	    if (a != null) {
		if (forzaCreazionePassword) {
		    // if (StringUtils.isBlank(a.getPassword())) {
		    String password = Utilities.getHashText(passwordClear, ENCRYPTING_ALGORITHM, false);
		    a.setPassword(password);
		    this.update(a);
		    a.setPasswordClear(passwordClear);
		    //} else {
		    // LA PASSWORD ESISTE CHE FACCIO???? 
		    // }
		}
		if (m != null) {
		    Mailtipo mt = mailtipoService.eseguiSostituzioniAnagrafe(m.getId().getCodice(), a);
		    MailMessageType mailMessage = new MailMessageType();
		    mailMessage.setOggetto(mt.getOggetto());
		    mailMessage.setCorpoMail(mt.getCorpo());
		    String destinatario = a.getEmail();
		    if (StringUtils.isBlank(destinatario)) {
			destinatario = a.getPec();
		    }
		    mailMessage.setDestinatari(destinatario);
		    mailMessage.setInviaComeHtml(true);
		    //String msg = mailServiceWSClient.sendMail(null, ORMHelper.getSoftware(), ORMHelper.getToken(), mailMessage);
		    String msg = mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), null, ORMHelper.getToken(), mailMessage);
		    return "OK".equalsIgnoreCase(msg);
		}
	    }
	}
	return false;
    }

    @Override
    public boolean inviaMailResetCredenziali(String oggetto, String corpo, String email, String cf, Integer codiceAnagrafe)
	    throws FunzioneBusinessRemotaException {

	String urlResetCredenziali = this.verticalizzazioneAreaRiservataService.getUrlServletPerResetCredenziali();
	if (StringUtils.isBlank(urlResetCredenziali)) {
	    throw new RuntimeException("Parametro URL_SERVLET_RESETCREDENZIALI non configurato correttamente");
	}
	urlResetCredenziali = urlResetCredenziali.trim();
	String oggi = Utilities.getToday("yyyyMMdd");
	String password = WebConstants.getDeployProperties().getProperty("ws.token.pwd");
	String alias = ORMHelper.getIdcomuneAlias();
	String mac = alias + cf + oggi + password;
	mac = Utilities.getHashText(mac, Utilities.ALGORITHM_SHA1, false);
	urlResetCredenziali += "?ticket=on&idcomunealias=" + alias + "&user=" + cf + "&ca=" + codiceAnagrafe + "&mac=" + mac;
	corpo = StringUtils.defaultString(corpo.replace("$URL_SERVLET_RESETCREDENZIALI$", urlResetCredenziali));
	MailMessageType mailMessage = new MailMessageType();
	mailMessage.setOggetto(oggetto);
	mailMessage.setCorpoMail(corpo);
	mailMessage.setDestinatari(email);
	mailMessage.setInviaComeHtml(true);
	String msg = mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), null, ORMHelper.getToken(), mailMessage);
	return "OK".equalsIgnoreCase(msg);
    }

    @Override
    public void updateIdentificato(Integer codiceAnagrafe, Responsabili responsabile) {

	Anagrafe a = this.findById(new PkId(codiceAnagrafe));
	if (a == null) {
	    throw new RuntimeException("Nessuna anagrafica trovata per il codice " + codiceAnagrafe);
	}
	anagrafeDAO.evict(a);
	a.setFlagIdentificato(Boolean.TRUE);
	a.setDataIdentificazione(Calendar.getInstance().getTime());
	a.setOperatoreIdentificazione(responsabile);
	this.update(a);
	LoggerUpdaterecord.log("L'operatore " + responsabile.toString() + " ha constrassegnato come IDENTIFICATO l'anagrafe " + a.toString());
    }

    public void updateRimuoviIdentificato(Integer codiceAnagrafe, Responsabili responsabile) {

	Anagrafe a = this.findById(new PkId(codiceAnagrafe));
	if (a == null) {
	    throw new RuntimeException("Nessuna anagrafica trovata per il codice " + codiceAnagrafe);
	}
	anagrafeDAO.evict(a);
	a.setFlagIdentificato(Boolean.FALSE);
	a.setDataIdentificazione(Calendar.getInstance().getTime());
	a.setOperatoreIdentificazione(responsabile);
	LoggerUpdaterecord.log("L'operatore " + responsabile.toString() + " ha constrassegnato come NON IDENTIFICATO l'anagrafe " + a.toString());
	this.update(a);
    }

    @Override
    public List<Anagrafe> findByCf(String codiceFiscale, String tipoAnagrafe, boolean escludiDisabilitati) {

	if (StringUtils.isBlank(codiceFiscale)) {
	    throw new RuntimeException("Il codice Fiscale non può essere nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("codicefiscale", codiceFiscale));
	fr.addFilterField(FilterUtils.equalsIgnoreCase("tipoanagrafe", tipoAnagrafe));
	if (escludiDisabilitati) {
	    FilterRestriction dis = new FilterRestriction();
	    dis.setAndOrRestriction(AndOrRestriction.OR);
	    dis.addFilterField(FilterUtils.isNull("flagDisabilitato"));
	    dis.addFilterField(FilterUtils.equals("flagDisabilitato", Integer.valueOf(0), Integer.class));
	    filterTable.addRestriction(dis);
	}
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("nominativo"));
	filterTable.addOrder(FilterUtils.orderAsc("nome"));
	return anagrafeDAO.findByFilterTable(filterTable, null, null);
    }

    @Override
    public List<Anagrafe> findByPI(String partitaIva, boolean escludiDisabilitati, boolean iscercaPIinCFAzienda) {

	if (StringUtils.isBlank(partitaIva)) {
	    throw new RuntimeException("La Partita iva non può essere nulla");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (iscercaPIinCFAzienda) {
	    FilterRestriction orCFAzienda = new FilterRestriction();
	    orCFAzienda.setAndOrRestriction(AndOrRestriction.OR);
	    orCFAzienda.addFilterField(FilterUtils.equalsIgnoreCase("partitaiva", partitaIva));
	    orCFAzienda.addFilterField(FilterUtils.equalsIgnoreCase("codicefiscale", partitaIva));
	    filterTable.addRestriction(orCFAzienda);
	} else {
	    fr.addFilterField(FilterUtils.equalsIgnoreCase("partitaiva", partitaIva));
	}
	fr.addFilterField(FilterUtils.equalsIgnoreCase("tipoanagrafe", "G"));
	filterTable.addRestriction(fr);
	if (escludiDisabilitati) {
	    FilterRestriction dis = new FilterRestriction();
	    dis.setAndOrRestriction(AndOrRestriction.OR);
	    dis.addFilterField(FilterUtils.isNull("flagDisabilitato"));
	    dis.addFilterField(FilterUtils.equals("flagDisabilitato", Integer.valueOf(0), Integer.class));
	    filterTable.addRestriction(dis);
	}
	filterTable.addOrder(FilterUtils.orderAsc("nominativo"));
	filterTable.addOrder(FilterUtils.orderAsc("nome"));
	return anagrafeDAO.findByFilterTable(filterTable, null, null);
    }

    @Override
    public void evict(Anagrafe entity) {

	anagrafeDAO.evict(entity);
    }

    @Override
    public String getDettaglioImpresa(String provinciaREA, String numeroREA) throws FunzioneBusinessRemotaException {

	TipoWSAnagrafeAttivoEnum wsAnagrafeAttivoEnum = findServizioWSAnagrafeAttivo();
	String ris = "";
	switch (wsAnagrafeAttivoEnum) {
	case ADRIER:
	    log.debug("getRicercaImpreseNoncessateByCodiceFiscale# {}", WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_ADRIER);
	    try {
		ris = adrierWsClient.getDettaglioImpresa(provinciaREA, numeroREA);
	    } catch (Exception e) {
		throw new FunzioneBusinessRemotaException(e);
	    }
	    break;
	case PARIX_GATE:
	    log.debug("getRicercaImpreseNoncessateByCodiceFiscale# {}", WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_PARIX_GATE);
	    ris = parixGateWsClient.getDettaglioImpresa(provinciaREA, numeroREA);
	    break;
	case PARIX_GATE_CLOUD:
	    log.debug("getRicercaImpreseNoncessateByCodiceFiscale# {}", WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_PARIX_GATE_CLOUD);
	    ris = parixGateV2WsClient.getDettaglioImpresa(provinciaREA, numeroREA);
	    break;
	default:
	    String _servizi = "";
	    TipoWSAnagrafeAttivoEnum[] servizi = TipoWSAnagrafeAttivoEnum.values();
	    for (int i = 0; i < servizi.length; i++) {
		_servizi += servizi[i].name() + ",";
	    }
	    _servizi = StringUtils.substringBeforeLast(_servizi, ",");
	    log.error("getRicercaImpreseNoncessateByCodiceFiscale# {}", "Nessun servizio di visura anagrafica è stato configurato");
	    throw new RuntimeException("Nessun servizio di visura anagrafica è stato configurato. Possibili servizi: " + _servizi);
	}
	return ris;
    }

    @Override
    public String getRicercaImpreseNoncessateByCodiceFiscale(String codiceFiscale) throws FunzioneBusinessRemotaException {

	TipoWSAnagrafeAttivoEnum wsAnagrafeAttivoEnum = findServizioWSAnagrafeAttivo();
	String ris = "";
	switch (wsAnagrafeAttivoEnum) {
	case ADRIER:
	    log.debug("getRicercaImpreseNoncessateByCodiceFiscale# {}", WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_ADRIER);
	    try {
		ris = adrierWsClient.getRicercaImpreseNoncessateByCodiceFiscale(codiceFiscale);
	    } catch (Exception e) {
		throw new FunzioneBusinessRemotaException(e);
	    }
	    break;
	case PARIX_GATE:
	    log.debug("getRicercaImpreseNoncessateByCodiceFiscale# {}", WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_PARIX_GATE);
	    ris = parixGateWsClient.getRicercaImpreseNoncessateByCodiceFiscale(codiceFiscale);
	    break;
	case PARIX_GATE_CLOUD:
	    log.debug("getRicercaImpreseNoncessateByCodiceFiscale# {}", WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_PARIX_GATE_CLOUD);
	    ris = parixGateV2WsClient.getRicercaImpreseNoncessateByCodiceFiscale(codiceFiscale);
	    break;
	default:
	    String _servizi = "";
	    TipoWSAnagrafeAttivoEnum[] servizi = TipoWSAnagrafeAttivoEnum.values();
	    for (int i = 0; i < servizi.length; i++) {
		_servizi += servizi[i].name() + ",";
	    }
	    _servizi = StringUtils.substringBeforeLast(_servizi, ",");
	    log.error("getRicercaImpreseNoncessateByCodiceFiscale# {}", "Nessun servizio di visura anagrafica è stato configurato");
	    throw new RuntimeException("Nessun servizio di visura anagrafica è stato configurato. Possibili servizi: " + _servizi);
	}
	return ris;
    }

    @Override
    public TipoWSAnagrafeAttivoEnum findServizioWSAnagrafeAttivo() {

	log.debug("TipoWSAnagrafeAttivoEnum# Verifico tipo servizio ws anagrafe attivo");
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_ADRIER)) {
	    log.debug("TipoWSAnagrafeAttivoEnum# ws anagrafe attivo: {}", WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_ADRIER);
	    return TipoWSAnagrafeAttivoEnum.ADRIER;
	} else if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_PARIX_GATE)) {
	    log.debug("TipoWSAnagrafeAttivoEnum# ws anagrafe attivo: {}", WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_PARIX_GATE);
	    return TipoWSAnagrafeAttivoEnum.PARIX_GATE;
	} else if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_PARIX_GATE_CLOUD)) {
	    log.debug("TipoWSAnagrafeAttivoEnum# ws anagrafe attivo: {}", WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_PARIX_GATE_CLOUD);
	    return TipoWSAnagrafeAttivoEnum.PARIX_GATE_CLOUD;
	} else {
	    log.debug("TipoWSAnagrafeAttivoEnum# ws anagrafe non attivo");
	    return TipoWSAnagrafeAttivoEnum.NESSUNO;
	}
    }

    @Override
    public String visuraParixHTML(String cfImpresa) {

	String token = ORMHelper.getToken();
	WsAnagrafe2Soap port = null;
	it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafeEnteTerzo = new it.gruppoinit.wsanagrafe2.schema.Anagrafe();
	try {
	    // Controllo se l'anagrafica passata sia Una persono giuridica o un persona fisica per recuperare l'url corretto
	    // Istanzio la porta per la chiamata al WS con il puntamento al servizio ricerca di persone giuridiche
	    if (log.isDebugEnabled()) {
		log.debug("visuraParixHTML# Recupero le modifiche dell'anagrafica per PI");
	    }
	    port = getPortWS(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_URL_RICERCA_PG);
	    anagrafeEnteTerzo = port.getPersonaGiuridica(token, cfImpresa);
	    // Gianpaolo : Spostato sopra l' "if" come per il metodo "findAnagrafeAggiornataByPI"	
	    //  perchè quando tornava un errore di anagrafe non trovata rilanciava l'eccezione del catch, comportamento sbagliato.	
	} catch (RuntimeException e) {
	    log.error("visuraParixHTML: Errore nel recupero delle modifiche dell'anagrafica={}", e.getMessage());
	    throw new RuntimeException("Errore nel recupero delle modifiche dell'anagrafica=" + e.getMessage(), e);
	}
	if (anagrafeEnteTerzo == null) {
	    throw new RuntimeException("Il CF IMPRESA non ha tornato risultati [" + cfImpresa + "]");
	}
	// Metodo che controlla i campi che potrebbero essere stati modificati;
	// se il campo è stato modificato viene inserito quello recuperato dal WS
	// altrimenti il campo rimane inalterato
	String provinciaREA = StringUtils.defaultString(anagrafeEnteTerzo.getPROVINCIAREA()).trim();
	String numeroREA = StringUtils.defaultString(anagrafeEnteTerzo.getNUMISCRREA()).trim();
	if (StringUtils.isBlank(provinciaREA) || StringUtils.isBlank(numeroREA)) {
	    throw new RuntimeException("L'anagrafe [" + anagrafeEnteTerzo.getNOMINATIVO() +
				       "] non ha definito i campi \"provincia rea\" e/o \"numero iscrizione rea\" ");
	}
	try {
	    if (log.isDebugEnabled()) {
		log.debug("visuraParixHTML# chiamo il servizio paric");
	    }
	    String result = this.getDettaglioImpresa(provinciaREA, numeroREA);
	    if (log.isDebugEnabled()) {
		log.debug("visuraParixHTML# chiamata effettuata risultato: {}", result);
	    }
	    try {
		String esito = Utilities.getValueFromXml(result, "/RISPOSTA/HEADER/ESITO");
		if ("KO".equalsIgnoreCase(esito)) {
		    String errore = Utilities.getValueFromXml(result, "/RISPOSTA/DATI/ERRORE/MSG_ERR");
		    log.error("visuraParixHTML# esito della chiamta KO: {}", errore);
		    throw new RuntimeException("Si è verificato un errore nel recupero del file di visura. Dettaglio errore: " + errore);
		}
		byte[] html = getHTML(result.getBytes("UTF-8"));
		return new String(html);
	    } catch (XPathExpressionException e1) {
		log.error("visuraParixHTML# {}", e1);
		throw new RuntimeException("Si è verificato un errore nel recupero del file di visura: XML non corretto" + e1.getMessage(), e1);
	    } catch (ParserConfigurationException e1) {
		log.error("visuraParixHTML# {}", e1);
		throw new RuntimeException("Si è verificato un errore nel recupero del file di visura: XML non corretto" + e1.getMessage(), e1);
	    } catch (SAXException e1) {
		log.error("visuraParixHTML# {}", e1);
		throw new RuntimeException("Si è verificato un errore nel recupero del file di visura: XML non corretto" + e1.getMessage(), e1);
	    } catch (IOException e1) {
		log.error("visuraParixHTML# {}", e1);
		throw new RuntimeException("Si è verificato un errore nel recupero del file di visura: XML non corretto" + e1.getMessage(), e1);
	    }
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException("Si è verificato un errore nel recupero del file di visura: " + e.getMessage(), e);
	}
    }

    private byte[] getHTML(byte[] xml) {

	InputStream in = null;
	try {
	    in = this.getClass().getClassLoader().getResource("it/gruppoinit/xslt/visurainfocamere.xsl").openStream();
	    byte[] xslBytes = IOUtils.toByteArray(in);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer = tFactory.newTransformer(new StreamSource(new ByteArrayInputStream(xslBytes)));
	    transformer.transform(new StreamSource(new ByteArrayInputStream(xml)), new StreamResult(baos));
	    return baos.toByteArray();
	} catch (Exception e) {
	    log.error("Errore durante il caricamento del file it/gruppoinit/xslt/visurainfocamere.xsl", e);
	    throw new RuntimeException("Errore durante il caricamento del file it/gruppoinit/xslt/visurainfocamere.xsl: " + e.getMessage(), e);
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		}
	    }
	}
    }

    public static void main(String[] args) throws Exception {

	InputStream in = null;
	try {
	    in = new FileInputStream(new File("c:/temp/firenze/visurainfocamere.xsl"));
	    byte[] xslBytes = IOUtils.toByteArray(in);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer = tFactory.newTransformer(new StreamSource(new ByteArrayInputStream(xslBytes)));
	    transformer.transform(
		    new StreamSource(new ByteArrayInputStream(
			    Utilities.getStringFromInputStream(new FileInputStream(new File("c:/temp/firenze/visura.xml"))).getBytes())),
		    new StreamResult(baos));
	    System.out.println(new String(baos.toByteArray()));
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		}
	    }
	}
    }

    @Override
    public List<Anagrafe> findAnagraficheConAutorizzazione(String descrizione, String tipoAnagrafe, AnagrafeEnum statoAnagrafe, Integer firstResult,
	    Integer maxResult) {

	return anagrafeDAO.findByDescrizione(descrizione, tipoAnagrafe, statoAnagrafe, firstResult, maxResult, true);
    }

    @Override
    public List<Anagrafe> findAnagraficheCollegate(Integer codiceAnagrafe, RicercaAnagraficeCollegateEnum contesto) {

	List<Anagrafe> as = new ArrayList<Anagrafe>();
	Set<Integer> codiciAnagrafe = this.findCodiciAnagraficheCollegate(codiceAnagrafe, contesto);
	for (Integer ca : codiciAnagrafe) {
	    Anagrafe an = this.findById(new PkId(ca));
	    as.add(an);
	}
	return as;
    }

    @Override
    public Set<Integer> findCodiciAnagraficheCollegate(Integer codiceAnagrafe, RicercaAnagraficeCollegateEnum contesto) {

	Anagrafe a = this.findById(new PkId(codiceAnagrafe));
	String cf = StringUtils.defaultString(a.getCodicefiscale()).trim().toUpperCase();
	Set<String> listaCf = new HashSet<String>();
	listaCf.add(cf);
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_RIC_ANAG_COLLEGATE)) {
	    Verticalizzazioniparametri modulo = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_RIC_ANAG_COLLEGATE, WebConstants.VERTICALIZZAZIONE_RIC_ANAG_COLLEGATE_COMPONENTE);
	    String moduloAttivo = modulo == null ? "" : StringUtils.defaultString(modulo.getValore(), "");
	    if (moduloAttivo.equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_RIC_ANAG_COLL_CSI)) {
		popolaListaCfCSI(listaCf, cf);
	    } else if (moduloAttivo.equalsIgnoreCase(IVertRicercaAnagrafeCollegataInternalService.NOME_VERTICALIZZAZIONE)
		    && vertAnagCollegataInternalService.isAttiva()) {
		popolaListCFInternal(listaCf, codiceAnagrafe, contesto);
	    }
	}
	return popolaCodiciAnagrafeDaCF(listaCf, StringUtils.defaultString(a.getPartitaiva()).trim(), codiceAnagrafe);
    }

    private void popolaListCFInternal(Set<String> listaCf, Integer codiceAnagrafe, RicercaAnagraficeCollegateEnum contesto) {

	STRATEGIA_RICERCA strategiaRicerca = vertAnagCollegataInternalService.getStrategiaRicerca();
	if (strategiaRicerca.equals(STRATEGIA_RICERCA.DEFAULT)) {
	    listaCf.addAll(anagrafeDAO.findCfPivaAnagraficheCollegateDelleIstanze(codiceAnagrafe, contesto));
	}
    }

    private Set<Integer> popolaCodiciAnagrafeDaCF(Set<String> listaCf, String partitaIva, Integer codiceAnagrafe) {

	Set<Integer> codiciAnagrafe = new HashSet<Integer>();
	codiciAnagrafe.add(codiceAnagrafe);
	List<Anagrafe> anagrafiche = null;
	if (!listaCf.isEmpty()) {
	    for (String codiceFiscale : listaCf) {
		anagrafiche = this.findByCf(codiceFiscale, WebConstants.PERSONA_FISICA, false);
		for (Anagrafe anagrafe : anagrafiche) {
		    codiciAnagrafe.add(anagrafe.getId().getCodice());
		}
		anagrafiche = this.findByPI(codiceFiscale, false, true);
		for (Anagrafe anagrafe : anagrafiche) {
		    codiciAnagrafe.add(anagrafe.getId().getCodice());
		}
	    }
	}
	if (StringUtils.isNotBlank(partitaIva)) {
	    anagrafiche = this.findByPI(partitaIva, false, true);
	    for (Anagrafe anagrafe : anagrafiche) {
		codiciAnagrafe.add(anagrafe.getId().getCodice());
	    }
	}
	return codiciAnagrafe;
    }

    private void popolaListaCfCSI(Set<String> listaCf, String cf) {

	try {
	    List<ListaPersonaCaricaInfoc> pers = aaepcsiClient.cercaPerCodiceFiscale(cf);
	    for (ListaPersonaCaricaInfoc p : pers) {
		listaCf.add(p.getCodFiscaleAzienda());
	    }
	} catch (Exception e) {
	    log.error("aaepcsiClient.cercaPerCodiceFiscale(" + cf + ");", e);
	}
    }

    @Override
    public DettaglioAnagrafeRestBean populateDettaglioAnagrafeRestBean(Anagrafe rich) {

	DettaglioAnagrafeRestBean d = new DettaglioAnagrafeRestBean();
	if (rich == null || rich.getId() == null || rich.getId().getCodice() == null) {
	    return d;
	}
	rich = this.findById(new PkId(rich.getId().getCodice()));
	d.setId(rich.getId().getCodice());
	d.setMail_verificata(rich.getFlagMailVerificata());
	d.setProcedura_verifica_in_corso(anagrafeVerificheMailDAO.isProceduraVerificaInCorso(rich.getId().getCodice()));
	DettaglioAnagrafeSedeRestBean corrispondenza = new DettaglioAnagrafeSedeRestBean();
	corrispondenza.setIndirizzo(rich.getIndirizzocorrispondenza());
	corrispondenza.setLocalita(rich.getCittacorrispondenza());
	corrispondenza.setCap(rich.getCapcorrispondenza());
	corrispondenza.setProvincia(rich.getProvinciacorrispondenza());
	if (rich.getComunecorrispondenza() != null) {
	    corrispondenza.setComune(rich.getComunecorrispondenza().getComune());
	}
	DettaglioAnagrafeSedeRestBean sede_legale = new DettaglioAnagrafeSedeRestBean();
	sede_legale.setIndirizzo(rich.getIndirizzo());
	sede_legale.setLocalita(rich.getCitta());
	sede_legale.setCap(rich.getCap());
	sede_legale.setProvincia(rich.getProvincia());
	if (rich.getComuneResidenza() != null) {
	    sede_legale.setComune(rich.getComuneResidenza().getComune());
	}
	if (WebConstants.PERSONA_GIURIDICA.equalsIgnoreCase(rich.getTipoanagrafe())) {
	    DettaglioAnagrafePGRestBean pg = new DettaglioAnagrafePGRestBean();
	    pg.setDenominazione(rich.getNominativo());
	    pg.setCodice_fiscale(rich.getCodicefiscale());
	    pg.setPartita_iva(rich.getPartitaiva());
	    pg.setNumero_rea(rich.getNumiscrrea());
	    if (rich.getDataiscrrea() != null) {
		pg.setData_rea(Utilities.formatDate(rich.getDataiscrrea(), false));
	    }
	    pg.setProvincia_rea(rich.getProvinciarea());
	    pg.setEmail(rich.getEmail());
	    pg.setPec(rich.getPec());
	    pg.setTelefono(rich.getTelefono());
	    pg.setCorrispondenza(corrispondenza);
	    pg.setSede_legale(sede_legale);
	    d.setPersona_giuridica(pg);
	} else {
	    DettaglioAnagrafePFRestBean pg = new DettaglioAnagrafePFRestBean();
	    pg.setNome(rich.getNome());
	    pg.setCognome(rich.getNominativo());
	    pg.setCodice_fiscale(rich.getCodicefiscale());
	    if (rich.getDatanascita() != null) {
		pg.setData_nascita(Utilities.formatDate(rich.getDatanascita(), false));
	    }
	    if (rich.getComuneNascita() != null) {
		pg.setComune_nascita(rich.getComuneNascita().getComune());
	    }
	    pg.setEmail(rich.getEmail());
	    pg.setPec(rich.getPec());
	    pg.setTelefono(rich.getTelefono());
	    pg.setCorrispondenza(corrispondenza);
	    pg.setResidenza(sede_legale);
	    d.setPersona_fisica(pg);
	}
	return d;
    }

    @Override
    public void updateCfAnagrafe(Integer codiceanagrafe, String cf) {

	Anagrafe a = this.findById(new PkId(codiceanagrafe));
	if (a == null) {
	    throw new RuntimeException("Nessuna anagrafica trovata per il codice " + codiceanagrafe);
	}
	Object r = userSecurityService.getCurrentlyAuthenticatedUserDetails(); // potrebbe venire aggiornato anche da utenti non operatori Token App o Frontend
	String responsabile = r.toString();
	String anagrafe = a.toString();
	anagrafeDAO.evict(a);
	if (StringUtils.isNotBlank(a.getCodicefiscale())) {
	    throw new RuntimeException("Codice fiscale già presente per l'anagrafe " + anagrafe);
	}
	a.setCodicefiscale(cf);
	a.setFlagDisabilitato(1);
	anagrafeDAO.update(a);
	LoggerUpdaterecord.log("L'utente " + responsabile + " ha modificato il cf dell'anagrafe " + anagrafe);
    }

    @Override
    public void aggiornaMailEPec(Integer codiceAnagrafe, String email, String pec) {

	if (StringUtils.isBlank(email) && StringUtils.isBlank(pec)) {
	    throw new IllegalArgumentException("Il campo mail o pec non può essere nullo");
	}
	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("Il campo codiceAnagrafe non può essere nullo");
	}
	Anagrafe anagrafe = this.findById(new PkId(codiceAnagrafe));
	if (anagrafe == null) {
	    throw new IllegalArgumentException("Anagrafe con codice " + codiceAnagrafe + " non trovata");
	}
	if (StringUtils.isNotBlank(email)) {
	    anagrafe.setEmail(email);
	}
	if (StringUtils.isNotBlank(pec)) {
	    anagrafe.setPec(pec);
	}
	this.update(anagrafe);
	this.eventPublisher.publish(new EventoEmailAnagrafeAggiornata(codiceAnagrafe, email, pec));
    }

    @Override
    public CodiceVerificaMailAnagrafeBean generaCodiceVerificaMail(Integer codiceAnagrafe, String nuovaEmail)
	    throws FunzioneBusinessRemotaException, InvalidConfigurationException {

	boolean attiva = verticalizzazioniAnagrafeVerificaMailService.isAttiva();
	if (!attiva) {
	    throw new InvalidConfigurationException("E' stato rischiesto di generare un codice di verifica mail ma non è stata attivata la regola");
	}
	// recupera le configurazioni verticalizzazione
	MailConfig account = verticalizzazioniAnagrafeVerificaMailService.codiceVerificaMailAccountId();
	Mailtipo mailTipo = verticalizzazioniAnagrafeVerificaMailService.codiceVerificaMailEmailTipo();
	anagrafeVerificheMailDAO.eliminaVerificheInCorso(codiceAnagrafe);
	String codiceVerifica = Utilities.generaCodiceNumerico(6);
	Calendar dataScadenza = Calendar.getInstance();
	dataScadenza.add(Calendar.MINUTE, 30); // aggiunti 30 minuti
	CodiceVerificaMailAnagrafeBean ret = new CodiceVerificaMailAnagrafeBean(codiceAnagrafe, codiceVerifica, dataScadenza.getTime(), nuovaEmail);
	anagrafeVerificheMailDAO.generaCodiceVerificaMail(ret);
	// genera il messaggio mail
	Mailtipo mt = mailtipoService.eseguiSostituzioniAnagrafe(mailTipo.getId().getCodice(), findById(new PkId(codiceAnagrafe)));
	// Invio al mail Service
	MailMessageType mailMessage = new MailMessageType();
	mailMessage.setOggetto(mt.getOggetto());
	mailMessage.setCorpoMail(mt.getCorpo());
	mailMessage.setDestinatari(nuovaEmail);
	mailMessage.setInviaComeHtml(true);
	String msg = mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), account.getId().getCodice(), ORMHelper.getToken(), mailMessage);
	if (!"OK".equalsIgnoreCase(msg)) {
	    throw new FunzioneBusinessRemotaException("Errore nell'invio della mail di verifica: " + msg);
	}
	return ret;
    }

    @Override
    public EsitoOperazioneAggiornamento updateVerificaMail(Integer codiceAnagrafe, String codiceVerifica) {

	Anagrafe a = this.findById(new PkId(codiceAnagrafe));
	log.debug("updateVerificaMail: {}==>Anagrafe {}, codiceVerifica: {}", new Object[] { codiceAnagrafe, a, codiceVerifica });
	String nuovaEmail = anagrafeVerificheMailDAO.updateVerificaCambioMail(codiceAnagrafe, codiceVerifica);
	log.debug("updateVerificaMail: codiceAnagrafe {}: nuovaEmail {}", codiceAnagrafe, nuovaEmail);
	if (StringUtils.isNotBlank(nuovaEmail)) {
	    try {
		a.setEmail(nuovaEmail);
		a.setFlagMailVerificata(Boolean.TRUE);
		this.update(a);
		return new EsitoOperazioneAggiornamento(true, null);
	    } catch (Exception e) {
		log.error("updateVerificaMail: " + e.getMessage(), e);
		return new EsitoOperazioneAggiornamento(false, e.getMessage());
	    }
	}
	log.error("updateVerificaMail: non sono riuscito ad aggiornare la mail per il codice anagrafe {}, codiceVerifica {}", codiceAnagrafe,
		codiceVerifica);
	return new EsitoOperazioneAggiornamento(false, "Non e' stato possibile aggiornare la mail con il codice verifica " + codiceVerifica);
    }

    @Override
    public Set<FiltroSoggetti> findSoggettiPersoneCollegate(Integer codiceAnagrafe, RicercaAnagraficeCollegateEnum contesto) {

	Set<FiltroSoggetti> ret = new HashSet<FiltroSoggetti>();
	Anagrafe a = this.findById(new PkId(codiceAnagrafe));
	String cfRiferimento = StringUtils.defaultString(a.getCodicefiscale()).trim().toUpperCase();
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_RIC_ANAG_COLLEGATE)) {
	    Verticalizzazioniparametri modulo = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_RIC_ANAG_COLLEGATE, WebConstants.VERTICALIZZAZIONE_RIC_ANAG_COLLEGATE_COMPONENTE);
	    String moduloAttivo = modulo == null ? "" : StringUtils.defaultString(modulo.getValore(), "");
	    if (moduloAttivo.equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_RIC_ANAG_COLL_CSI)) {
		Verticalizzazioniparametri chiaviRicerca = verticalizzazioniService.getVerticalizzazioniparametri(
			WebConstants.VERTICALIZZAZIONE_RIC_ANAG_COLL_CSI,
			WebConstants.VERTICALIZZAZIONE_RIC_ANAG_COLL_CSI_DECODIFICHE_TABELLA_RAGGRUPP_LR);
		String tabella = "CARICHE_AEEP";
		String raggrupp = "LEGALE_RAPPRESENTANTE";
		if (chiaviRicerca != null) {
		    String valore = StringUtils.defaultString(chiaviRicerca.getValore()).trim();
		    if (valore.indexOf("#") >= 0) {
			String[] vals = valore.split("#");
			tabella = vals[0].trim();
			raggrupp = vals[1].trim();
		    }
		}
		ListaDecodifiche dec = decodificheService.findByTabellaAndRaggruppamento(tabella, raggrupp);
		// A) in caso di CSI VERTICALIZZAZIONE_RIC_ANAG_COLL_CSI
		// ==> RECUPERA LE DECODIFICHE LEGALE RAPPRESENTANTE
		// ==> USA IL METODO PER VERIFICARE se ha la titolarità e ritorna la lista
		Map<String, List<ListaPersonaCaricaInfoc>> cfAzListaCariche = new HashMap<String, List<ListaPersonaCaricaInfoc>>();
		List<ListaPersonaCaricaInfoc> pers = aaepcsiClient.cercaPerCodiceFiscale(cfRiferimento);
		for (ListaPersonaCaricaInfoc lpci : pers) {
		    String codiceFiscaleAzienda = lpci.getCodFiscaleAzienda();
		    List<ListaPersonaCaricaInfoc> cariche = cfAzListaCariche.get(codiceFiscaleAzienda);
		    if (null == cariche) {
			cariche = new ArrayList<ListaPersonaCaricaInfoc>();
		    }
		    cariche.add(lpci);
		    cfAzListaCariche.put(codiceFiscaleAzienda, cariche);
		}
		for (Entry<String, List<ListaPersonaCaricaInfoc>> e : cfAzListaCariche.entrySet()) {
		    FiltroSoggetti s = new FiltroSoggetti();
		    s.setCf(e.getKey());
		    String descrizione = null;
		    boolean titolarita = false;
		    for (ListaPersonaCaricaInfoc l : e.getValue()) {
			descrizione = l.getDescrAzienda();
			titolarita = verificaTitolarita(dec, l.getCodiceCarica());
			if (titolarita) {
			    break;
			}
		    }
		    s.setNominativo(descrizione);
		    s.setTitolarita(titolarita);
		    ret.add(s);
		}
		return ret;
	    } else if (moduloAttivo.equalsIgnoreCase(IVertRicercaAnagrafeCollegataInternalService.NOME_VERTICALIZZAZIONE)
		    && vertAnagCollegataInternalService.isAttiva()) {
		// B) in caso di ricerca interna. La titolarità è sempre true 
		STRATEGIA_RICERCA strategiaRicerca = vertAnagCollegataInternalService.getStrategiaRicerca();
		if (strategiaRicerca.equals(STRATEGIA_RICERCA.DEFAULT)) {
		    Set<String> cfpivaris = anagrafeDAO.findCfPivaAnagraficheCollegateDelleIstanze(codiceAnagrafe, contesto);
		    Set<Integer> cas = popolaCodiciAnagrafeDaCF(cfpivaris, StringUtils.defaultString(a.getPartitaiva()).trim(), codiceAnagrafe);
		    Map<String, List<FiltroSoggetti>> cfAzListaCariche = new HashMap<String, List<FiltroSoggetti>>();
		    for (Integer codiceana : cas) {
			Anagrafe an = this.findById(new PkId(codiceana));
			String cfPiva = StringUtils.defaultIfEmpty(an.getCodicefiscale(), an.getPartitaiva()).toUpperCase();
			List<FiltroSoggetti> list = cfAzListaCariche.get(cfPiva);
			if (list == null) {
			    list = new ArrayList<FiltroSoggetti>();
			}
			FiltroSoggetti s = new FiltroSoggetti();
			s.setCf(cfPiva);
			s.setNominativo(an.getDescrizioneRichiedente());
			s.setTitolarita(true);
			list.add(s);
			cfAzListaCariche.put(cfPiva, list);
		    }
		    for (Entry<String, List<FiltroSoggetti>> e : cfAzListaCariche.entrySet()) {
			ret.add(e.getValue().get(0));
		    }
		    return ret;
		}
	    }
	}
	// C) solo il CF passato. La titolarità è sempre true
	//	Set<String> listaCf = new HashSet<String>();
	//	listaCf.add(cf);
	//	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_RIC_ANAG_COLLEGATE)) {
	//	    Verticalizzazioniparametri modulo = verticalizzazioniService.getVerticalizzazioniparametri(
	//		    WebConstants.VERTICALIZZAZIONE_RIC_ANAG_COLLEGATE, WebConstants.VERTICALIZZAZIONE_RIC_ANAG_COLLEGATE_COMPONENTE);
	//	    String moduloAttivo = modulo == null ? "" : StringUtils.defaultString(modulo.getValore(), "");
	//	    if (moduloAttivo.equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_RIC_ANAG_COLL_CSI)) {
	//		popolaListaCfCSI(listaCf, cf);
	//	    } else if (moduloAttivo.equalsIgnoreCase(IVertRicercaAnagrafeCollegataInternalService.NOME_VERTICALIZZAZIONE)
	//		    && vertAnagCollegataInternalService.isAttiva()) {
	//		popolaListCFInternal(listaCf, codiceAnagrafe, contesto);
	//	    }
	//	}
	FiltroSoggetti s = new FiltroSoggetti();
	s.setCf(cfRiferimento);
	s.setNominativo(a.getDescrizioneRichiedente());
	s.setTitolarita(true);
	ret.add(s);
	return ret;
    }

    private boolean verificaTitolarita(ListaDecodifiche dec, String codiceCarica) {

	if (dec == null || dec.getDecodificheList().isEmpty()) {
	    return false;
	}
	log.debug("codice carica {}", codiceCarica);
	for (Decodifiche d : dec.getDecodificheList()) {
	    log.debug("decodifica per carica {}===>{}", d.getChiave(), codiceCarica);
	    if (codiceCarica.equalsIgnoreCase(d.getChiave())) {
		return true;
	    }
	}
	return false;
    }
}
