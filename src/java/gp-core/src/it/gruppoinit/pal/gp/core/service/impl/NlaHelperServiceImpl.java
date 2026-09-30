package it.gruppoinit.pal.gp.core.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocGruppiSmist;
import it.gruppoinit.pal.gp.core.domain.Alberoprocpeoplehref;
import it.gruppoinit.pal.gp.core.domain.Alberoprocpeopleoper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Categorieeventibase;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Elenchiprofessionalibase;
import it.gruppoinit.pal.gp.core.domain.Elencocassaedilebase;
import it.gruppoinit.pal.gp.core.domain.Elencoinailbase;
import it.gruppoinit.pal.gp.core.domain.Elencoinpsbase;
import it.gruppoinit.pal.gp.core.domain.FoArconfigurazione;
import it.gruppoinit.pal.gp.core.domain.FoArconfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Formegiuridiche;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentipeople;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeMetadati;
import it.gruppoinit.pal.gp.core.domain.IstanzeMetadatiId;
import it.gruppoinit.pal.gp.core.domain.IstanzeTempistica;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Istanzecollegate;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocure;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Stradariocolore;
import it.gruppoinit.pal.gp.core.domain.StradariocoloreId;
import it.gruppoinit.pal.gp.core.domain.TipiLocalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Tipologiaistanza;
import it.gruppoinit.pal.gp.core.domain.Titoli;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.GruppiSmistHelper;
import it.gruppoinit.pal.gp.core.domain.helper.GruppiSmistamentoClassiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzaAutConcHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeNlaHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.Istanzedyn2datiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.features.istanze.metadati.IIstanzeMetadatiService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.features.oneri.DecodificaCausaleOnereRequest;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.features.oneri.exceptions.DecodificaOneriExceptions;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocGruppiSmistService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AlberoprocpeoplehrefService;
import it.gruppoinit.pal.gp.core.service.AlberoprocpeopleoperService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.CategorieeventibaseService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.FoArconfigurazioneService;
import it.gruppoinit.pal.gp.core.service.FormegiuridicheService;
import it.gruppoinit.pal.gp.core.service.GruppiEndoprocedimentiDService;
import it.gruppoinit.pal.gp.core.service.Inventarioprocdyn2modellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentipeopleService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocureService;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.MovimentiTempisticaService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.NatureProcedureService;
import it.gruppoinit.pal.gp.core.service.NlaHelperService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.StradariocoloreService;
import it.gruppoinit.pal.gp.core.service.TipiLocalizzazioniService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;
import it.gruppoinit.pal.gp.core.service.TipisoggettoService;
import it.gruppoinit.pal.gp.core.service.TipisoggettopeopleService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.service.helper.NodoNLAEnum;
import it.gruppoinit.pal.gp.core.service.helper.TipoDocumentoType;
import it.gruppoinit.pal.gp.core.service.helper.TipoRicercaDocumentoEnum;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.LoggerModificheIstanze;
import it.gruppoinit.pal.gp.core.utils.StcUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.StcWsClient;
import it.init.sigepro.rte.AllegatoBinarioRequest;
import it.init.sigepro.rte.AllegatoBinarioResponse;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AltriSoggettiType;
import it.init.sigepro.rte.types.AnagrafeType;
import it.init.sigepro.rte.types.CampoDinamicoType;
import it.init.sigepro.rte.types.CampoSchedaType;
import it.init.sigepro.rte.types.CausaleOnereType;
import it.init.sigepro.rte.types.CircoscrizioneType;
import it.init.sigepro.rte.types.CittadinanzaType;
import it.init.sigepro.rte.types.CodiceDescrizioneType;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.CoordinateType;
import it.init.sigepro.rte.types.DatiCassaEdileType;
import it.init.sigepro.rte.types.DatiInailType;
import it.init.sigepro.rte.types.DatiInpsType;
import it.init.sigepro.rte.types.DatiIscrizioneAlboType;
import it.init.sigepro.rte.types.DettaglioAttivitaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DettaglioPraticaVisuraType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ElementoValoreCampoDinamicoType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.EstremiAttoEstesoType;
import it.init.sigepro.rte.types.EstremiAttoType;
import it.init.sigepro.rte.types.FrazioneType;
import it.init.sigepro.rte.types.InterventoType;
import it.init.sigepro.rte.types.IscrizioneRegistroType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.LocalizzazioneType;
import it.init.sigepro.rte.types.MetaDatoType;
import it.init.sigepro.rte.types.OneriPagamentiType;
import it.init.sigepro.rte.types.OneriScadenzeType;
import it.init.sigepro.rte.types.OneriType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.PersonaGiuridicaType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.ProcuraType;
import it.init.sigepro.rte.types.QuartiereType;
import it.init.sigepro.rte.types.RegistroREAType;
import it.init.sigepro.rte.types.RichiedenteType;
import it.init.sigepro.rte.types.RiferimentiAllegatoType;
import it.init.sigepro.rte.types.RiferimentoCatastaleType;
import it.init.sigepro.rte.types.RuoloType;
import it.init.sigepro.rte.types.SchedaType;
import it.init.sigepro.rte.types.SegnoType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.StatoIterType;
import it.init.sigepro.rte.types.StatoPraticaType;
import it.init.sigepro.rte.types.TempisticaProcedimentoType;
import it.init.sigepro.rte.types.TipoAttivitaType;
import it.init.sigepro.rte.types.TipoLocalizzazioneType;
import it.init.sigepro.rte.types.ValoreCampoDinamicoType;
import it.init.sigepro.rte.types.ValoreParametroType;

@Service
public class NlaHelperServiceImpl implements NlaHelperService {

    private static final String AIDA_IDENTIFICATIVO_PERSONA_FISICA = "PERSONA FISICA";
    private static final String CATASTO_EDILIZIO_URBANO = "Edilizio Urbano";
    private static final String CATASTO_TERRENI = "Terreni";
    private static final Logger log = LoggerFactory.getLogger(NlaHelperServiceImpl.class);
    public static final String ISTANZE_ALLEGATI_PREFIX = "E_";
    public static final String ALTRODATO_BACKOFFICE_CERCA_PRATICA_COLLEGATA_PADRE = "#CERCA_PRATICA_COLLEGATA_PADRE#";
    public static final String ALTRODATO_BACKOFFICE_TIPOLOGIA_ISTANZA = "#TIPOLOGIA_ISTANZA#";
    public static final String ALTRODATO_ANAGRAFE_CODICEANAGRAFE = "$CODICE_ANAGRAFE_BACKEND$";
    public static final String PEOPLE_SETTORE = "PEOPLE_SETTORE";
    public static final String PEOPLE_OPERAZIONI = "PEOPLE_OPERAZIONI";
    public static final String SIEDER_SETTORE = "SIEDER_SETTORE";
    public static final String SIEDER_OPERAZIONI = "SIEDER_OPERAZIONI";
    public static final String NLA_SETTORE = "NLA_SETTORE";
    public static final String NLA_OPERAZIONI = "NLA_OPERAZIONI";
    public static final String PEOPLE_HREF = "PEOPLE_HREF";
    public static final String ISTANZA_METADATO = "ISTANZA_METADATO";
    public static final String ASSERZIONE_SAML_AUTENTICAZIONE = "ASSERZIONE_SAML_AUTENTICAZIONE";
    @Autowired
    private ApplicationContext context;
    private AlberoprocService alberoprocService;
    private VerticalizzazioniService verticalizzazioniService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private InventarioprocedimentipeopleService inventarioprocedimentipeopleService;
    private AlberoprocpeoplehrefService alberoprocpeoplehrefService;
    private AlberoprocpeopleoperService alberoprocpeopleoperService;
    private AlberoprocEndoService alberoprocEndoService;
    private CategorieeventibaseService categorieeventibaseService;
    private ComuniService comuniService;
    private ComuniassociatiService comuniassociatiService;
    private FormegiuridicheService formegiuridicheService;
    private IstanzeallegatiService istanzeallegatiService;
    private IstanzeoneriService istanzeoneriService;
    private IstanzeprocureService istanzeprocureService;
    private IstanzeService istanzeService;
    private IstanzestradarioService istanzestradarioService;
    private MovimentiallegatiService movimentiallegatiService;
    private MovimentiNoSecurityService movimentiNoSecurityService;
    private TipisoggettoService tipisoggettoService;
    private StradarioService stradarioService;
    private StradariocoloreService stradariocoloreService;
    private OggettiMetadatiService oggettiMetadatiService;
    private OggettiService oggettiService;
    private FoArconfigurazioneService foArconfigurazioneService;
    private ConfigurazioneService configurazioneService;
    private IstanzeeventiService istanzeeventiService;
    private AutorizzazioniService autorizzazioniService;
    private TipisoggettopeopleService tipisoggettopeopleService;
    private TipicausalioneriService tipicausalioneriService;
    private TipimodalitapagamentoService tipimodalitapagamentoService;
    private TipiMovimentoService tipiMovimentoService;
    private TipiLocalizzazioniService tipiLocalizzazioniService;
    private Istanzedyn2modellitService istanzedyn2modellitService;
    private Istanzedyn2datiService istanzedyn2datiService;
    private AnagrafeService anagrafeService;
    private AlberoprocGruppiSmistService alberoprocGruppiSmistService;
    private GruppiEndoprocedimentiDService gruppiEndoprocedimentiDService;
    private Inventarioprocdyn2modellitService inventarioprocdyn2modellitService;
    private StcWsClient stcWsClient;
    private DocumentiistanzaService documentiistanzaService;
    private IIstanzeMetadatiService istanzeMetadatiService;

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Autowired
    public void setStcWsClient(StcWsClient stcWsClient) {

	this.stcWsClient = stcWsClient;
    }

    @Autowired
    public void setInventarioprocdyn2modellitService(Inventarioprocdyn2modellitService inventarioprocdyn2modellitService) {

	this.inventarioprocdyn2modellitService = inventarioprocdyn2modellitService;
    }

    @Autowired
    public void setGruppiEndoprocedimentiDService(GruppiEndoprocedimentiDService gruppiEndoprocedimentiDService) {

	this.gruppiEndoprocedimentiDService = gruppiEndoprocedimentiDService;
    }

    @Autowired
    public void setAlberoprocGruppiSmistService(AlberoprocGruppiSmistService alberoprocGruppiSmistService) {

	this.alberoprocGruppiSmistService = alberoprocGruppiSmistService;
    }

    //    @Autowired
    //    public void setContenttypesService(ContenttypesService contenttypesService) {
    //
    //	this.contenttypesService = contenttypesService;
    //    }
    @Autowired
    public void setTipiLocalizzazioniService(TipiLocalizzazioniService tipiLocalizzazioniService) {

	this.tipiLocalizzazioniService = tipiLocalizzazioniService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setInventarioprocedimentipeopleService(InventarioprocedimentipeopleService inventarioprocedimentipeopleService) {

	this.inventarioprocedimentipeopleService = inventarioprocedimentipeopleService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setAlberoprocpeoplehrefService(AlberoprocpeoplehrefService alberoprocpeoplehrefService) {

	this.alberoprocpeoplehrefService = alberoprocpeoplehrefService;
    }

    @Autowired
    public void setAlberoprocpeopleoperService(AlberoprocpeopleoperService alberoprocpeopleoperService) {

	this.alberoprocpeopleoperService = alberoprocpeopleoperService;
    }

    @Autowired
    public void setAlberoprocEndoService(AlberoprocEndoService alberoprocEndoService) {

	this.alberoprocEndoService = alberoprocEndoService;
    }

    @Autowired
    public void setCategorieeventibaseService(CategorieeventibaseService categorieeventibaseService) {

	this.categorieeventibaseService = categorieeventibaseService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setFormegiuridicheService(FormegiuridicheService formegiuridicheService) {

	this.formegiuridicheService = formegiuridicheService;
    }

    @Autowired
    public void setIstanzeallegatiService(IstanzeallegatiService istanzeallegatiService) {

	this.istanzeallegatiService = istanzeallegatiService;
    }

    @Autowired
    public void setIstanzeoneriService(IstanzeoneriService istanzeoneriService) {

	this.istanzeoneriService = istanzeoneriService;
    }

    @Autowired
    public void setIstanzeprocureService(IstanzeprocureService istanzeprocureService) {

	this.istanzeprocureService = istanzeprocureService;
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
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setMovimentiNoSecurityService(MovimentiNoSecurityService movimentiNoSecurityService) {

	this.movimentiNoSecurityService = movimentiNoSecurityService;
    }

    @Autowired
    public void setTipisoggettoService(TipisoggettoService tipisoggettoService) {

	this.tipisoggettoService = tipisoggettoService;
    }

    @Autowired
    public void setStradarioService(StradarioService stradarioService) {

	this.stradarioService = stradarioService;
    }

    @Autowired
    public void setStradariocoloreService(StradariocoloreService stradariocoloreService) {

	this.stradariocoloreService = stradariocoloreService;
    }

    @Autowired
    public void setOggettiMetadatiService(OggettiMetadatiService oggettiMetadatiService) {

	this.oggettiMetadatiService = oggettiMetadatiService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setFoArconfigurazioneService(FoArconfigurazioneService foArconfigurazioneService) {

	this.foArconfigurazioneService = foArconfigurazioneService;
    }

    @Autowired
    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setTipicausalioneriService(TipicausalioneriService tipicausalioneriService) {

	this.tipicausalioneriService = tipicausalioneriService;
    }

    @Autowired
    public void setTipimodalitapagamentoService(TipimodalitapagamentoService tipimodalitapagamentoService) {

	this.tipimodalitapagamentoService = tipimodalitapagamentoService;
    }

    @Autowired
    public void setTipisoggettopeopleService(TipisoggettopeopleService tipisoggettopeopleService) {

	this.tipisoggettopeopleService = tipisoggettopeopleService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setIstanzedyn2modellitService(Istanzedyn2modellitService istanzedyn2modellitService) {

	this.istanzedyn2modellitService = istanzedyn2modellitService;
    }

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Override
    public Integer generateCodiceIstanza() {

	return istanzeService.newIdFromSequencetable(new Istanze()).getCodice();
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    public IIstanzeMetadatiService getIstanzeMetadatiService() {

	return istanzeMetadatiService;
    }

    @Autowired
    public void setIstanzeMetadatiService(IIstanzeMetadatiService istanzeMetadatiService) {

	this.istanzeMetadatiService = istanzeMetadatiService;
    }

    @Override
    public synchronized String generateNumeroistanza(InserimentoPraticaNLARequest praticaNla, boolean isNuovaLogicaSuaper) {

	if (isNuovaLogicaSuaper) {
	    // con la nuova logica SUAPER non posso calcolare ora il numero istanza che non riesco a trovare gli interventi.
	    return "SUAPERTMP_" + calcolaNumeroIstanzaTemporaneo(praticaNla);
	}
	Istanze istanzeTemp = new Istanze();
	String numeroIstanza = "";
	try {
	    populateIntervento(praticaNla, istanzeTemp);
	    // if verticalizzazioneparametro.STC.USA_NUMEROISTANZA_ALTRO_SISTEMA = S ALLORA IL NUMERO ISTANZA E' lo stesso della domanda mittente 	    
	    Verticalizzazioniparametri tsd = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		    WebConstants.VERTICALIZZAZIONE_STC_USA_NUMISTANZA_ALTRO_SISTEMA);
	    Verticalizzazioniparametri listaNodi = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		    WebConstants.VERTICALIZZAZIONE_STC_USA_NUMISTANZA_ALTRO_SISTEMA_NODI);
	    if (tsd != null) {
		String valore = StringUtils.defaultString(tsd.getValore(), "N").trim();
		if (StringUtils.isNotBlank(valore)) {
		    if (valore.equalsIgnoreCase("S")) {
			boolean usaNumero = true;
			if (listaNodi != null) {
			    if (StringUtils.isNotBlank(listaNodi.getValore())) {
				usaNumero = false;
				String idNodo = StringUtils.defaultString(praticaNla.getSportelloMittente().getIdNodo());
				String[] nodis = listaNodi.getValore().split(",");
				for (String n : nodis) {
				    if (StringUtils.defaultString(n).equals(idNodo)) {
					usaNumero = true;
					break;
				    }
				}
			    }
			}
			if (usaNumero) {
			    // verifico che non inserisco sullo stesso modulo software
			    if (!stessoNodoEnteSoftware(praticaNla)) {
				numeroIstanza = praticaNla.getDettaglioPratica().getNumeroPratica();
			    }
			}
		    }
		}
	    }
	    if (StringUtils.isBlank(numeroIstanza)) {
		numeroIstanza = istanzeService.findProgressivoIstanza(istanzeTemp.getAlberoproc().getId().getCodice(), true);
	    }
	    if (StringUtils.isBlank(numeroIstanza)) {
		throw new Exception("istanzeService#findProgressivoIstanza: Non ha restituito un numero istanza!");
	    }
	    // BOCCI 2012-02-13 la funzione findProgressivoIstanza è stata modioficata per scrivere direttamente il progressivo 
	    // se il parametro scriviSubito è true
	    // istanzeService.scriviProgressivo(numeroIstanza, istanzeTemp.getAlberoproc().getId().getCodice(), codiceSoftware);
	} catch (Exception e) {
	    log.error("generateNumeroistanza: {}", e.getMessage());
	    Integer alberoproc = null;
	    if (istanzeTemp != null && istanzeTemp.getAlberoproc() != null && istanzeTemp.getAlberoproc().getId() != null) {
		alberoproc = istanzeTemp.getAlberoproc().getId().getCodice();
	    }
	    try {
		numeroIstanza = istanzeService.findProgressivoIstanza(alberoproc, true);
	    } catch (Exception ex) {
		log.error("generateNumeroistanza: {}", ex);
	    }
	    if (StringUtils.isBlank(numeroIstanza)) {
		numeroIstanza = calcolaNumeroIstanzaTemporaneo(praticaNla);
	    }
	    log.warn("generateNumeroistanza: Impossibile generare il numero istanza. Restituisco il numero temporaneo: {}", numeroIstanza);
	}
	return numeroIstanza;
    }

    private boolean stessoNodoEnteSoftware(InserimentoPraticaNLARequest praticaNla) {

	if (praticaNla != null) {
	    SportelloType mitt = praticaNla.getSportelloMittente();
	    SportelloType dest = praticaNla.getSportelloDestinatario();
	    if (mitt != null && dest != null) {
		String idNodoMitt = StringUtils.defaultString(mitt.getIdNodo());
		String idEnteMitt = StringUtils.defaultString(mitt.getIdEnte());
		String idSportelloMitt = StringUtils.defaultString(mitt.getIdSportello());
		String idNodoDest = StringUtils.defaultString(dest.getIdNodo());
		String idEnteDest = StringUtils.defaultString(dest.getIdEnte());
		String idSportelloDest = StringUtils.defaultString(dest.getIdSportello());
		if (checkSportelloPEC_O_PROTO_Client(mitt, false)) {
		    idSportelloMitt = idSportelloMitt.replace(WebConstants.PEC_CLIENT_IDSPORTELLO_SUFFIX, "")
			    .replace(WebConstants.AZIONI_PROTOCOLLO_CLIENT_IDSPORTELLO_SUFFIX, "");
		}
		if (checkSportelloPEC_O_PROTO_Client(dest, false)) {
		    idSportelloDest = idSportelloDest.replace(WebConstants.PEC_CLIENT_IDSPORTELLO_SUFFIX, "")
			    .replace(WebConstants.AZIONI_PROTOCOLLO_CLIENT_IDSPORTELLO_SUFFIX, "");
		}
		return (idNodoMitt.equalsIgnoreCase(idNodoDest) && (idEnteMitt.equalsIgnoreCase(idEnteDest))
			&& (idSportelloMitt.equalsIgnoreCase(idSportelloDest)));
	    }
	}
	return false;
    }

    public static void main(String[] args) {

	NlaHelperServiceImpl hlp = new NlaHelperServiceImpl();
	InserimentoPraticaNLARequest praticaNla = new InserimentoPraticaNLARequest();
	SportelloType mitt = new SportelloType();
	SportelloType dest = new SportelloType();
	mitt.setIdEnte("E256");
	dest.setIdEnte("E256");
	mitt.setIdNodo("400");
	dest.setIdNodo("400");
	mitt.setIdSportello("CE" + WebConstants.PEC_CLIENT_IDSPORTELLO_SUFFIX);
	dest.setIdSportello("CE" + WebConstants.AZIONI_PROTOCOLLO_CLIENT_IDSPORTELLO_SUFFIX);
	praticaNla.setSportelloDestinatario(dest);
	praticaNla.setSportelloMittente(mitt);
	System.out.println(hlp.stessoNodoEnteSoftware(praticaNla));
	System.out.println(" 31/01/2019".matches("^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](1[0-9]|2[0-9])\\d\\d$"));
    }

    /**
     * Qualora tutti i metodi di ricerca del numeroistanza fallissero
     * 
     * @param praticaNla
     * @return
     */
    private String calcolaNumeroIstanzaTemporaneo(InserimentoPraticaNLARequest praticaNla) {

	String cf = (String) EntityUtils.getNestedProperty(praticaNla, "dettaglioPratica.richiedente.anagrafica.codiceFiscale");
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy-hhmm-SSS");
	String dataOdierna = sdf.format(Calendar.getInstance().getTime());
	if (StringUtils.isNotBlank(cf)) {
	    return StringUtils.left(cf, 16) + "-" + dataOdierna;
	} else {
	    return ORMHelper.getSoftware() + "-" + dataOdierna;
	}
    }

    @Override
    public IstanzeNlaHelper populateIstanza(Istanze istanza, InserimentoPraticaNLARequest praticaNla, boolean passaProtocollo,
	    boolean isNuovaLogicaSuaper, boolean isDaLocale) {

	IstanzeNlaHelper istanzeNlaHelper = new IstanzeNlaHelper();
	if (istanza == null) {
	    istanza = new Istanze();
	}
	istanza.setCreatoDaStc(Boolean.TRUE);
	DettaglioPraticaType dettaglioPraticaType = praticaNla.getDettaglioPratica();
	if (dettaglioPraticaType == null) {
	    log.error("populateIstanza(): Dettaglio pratica vuoto.");
	    throw new RuntimeException("Il dettaglio pratica è vuoto.");
	}
	if (StringUtils.isNotBlank(dettaglioPraticaType.getAnnotazioni())) {
	    istanza.setLavoriestesa(StringUtils.left(dettaglioPraticaType.getAnnotazioni(), 4000));
	}
	if (StringUtils.isNotBlank(dettaglioPraticaType.getCodicePraticaTelematica())) {
	    istanza.setCodicepraticatel(dettaglioPraticaType.getCodicePraticaTelematica());
	}
	if (StringUtils.isNotBlank(dettaglioPraticaType.getInsegna())) {
	    istanza.setNomeattivita(dettaglioPraticaType.getInsegna());
	}
	// NUOVA LOGICA SUAPER	
	Integer numeroInterventi = Integer.valueOf(0);
	log.debug("isNuovaLogicaSuaper={}", isNuovaLogicaSuaper);
	if (isNuovaLogicaSuaper) {
	    // Nella nuova logica di BIND SUAPER GRUPPI_SMISTAMENTO popolo prima i procedimenti per dare errore in caso di prceedimento non mappato
	    // ENDOPROCEDIMENTI
	    log.debug("RECUPERO ENDOPROCEDIMENTI ");
	    populateProcedimenti2(praticaNla, istanza);
	    log.debug("RECUPERO INTERVENTO populateInterventoSuaper");
	    populateInterventoSuaper(praticaNla, istanza);
	    numeroInterventi = 1;
	    log.debug("is Inserimento locale {}", isDaLocale);
	    if (istanza.getNumeroistanza().startsWith("SUAPERTMP_")) {
		String numeroIstanza = generateNumeroistanzaFromIntervento(istanza);
		log.debug("nuovo numero istanza {}", numeroIstanza);
		if (StringUtils.isNotBlank(numeroIstanza)) {
		    istanzeNlaHelper.setNumeroistanzaprenotato(numeroIstanza);
		}
	    }
	} else {
	    // VECCHIA LOGICA
	    // INTERVENTO
	    // Deve essere necessariamente il primo passaggio, il valore recuperato mi permetterà di generare il numero istanza
	    // che deve essere inviato sia in caso di inserimento o no della pratica inviata da people.
	    // In un solo caso non potrà essere creato: se non è stato configurato nella verticalizzazione "VERTICALIZZAZIONE_STC" il
	    // parametro ALBEROPROC.SC_ID.
	    log.debug("RECUPERO INTERVENTO");
	    numeroInterventi = populateIntervento(praticaNla, istanza);
	    // ENDOPROCEDIMENTI
	    log.debug("RECUPERO ENDOPROCEDIMENTI");
	    populateProcedimenti2(praticaNla, istanza);
	}
	// BOCCI 2012-08-13 BUGZILLA 627 OPERATORE DI DEFAULT
	// Utente di default utilizzato per gli inserimenti istanza da STC (Es. on-line). Durante l'inserimento pratica da un sistema esterno viene associato come operatore quello individuato in questo campo
	populateOperatoreDefault(istanza);
	// DOMICILIO_ELETTRONICO
	if (dettaglioPraticaType != null) {
	    istanza.setDomicilioElettronico(dettaglioPraticaType.getDomicilioElettronico());
	}
	// SOFTWARE
	log.debug("RECUPERO SOFTWARE");
	istanza.getSoftware().setCodice(ORMHelper.getSoftware());
	// RICHIEDENTE
	// 2012-05-15 BOCCI: dopo la modifica sulla non obbligatorietà del richiedente se questo arriva nullo
	//	allora metto come richiedente dell'istanza l'azienda e se non presente anche questa rilancio eccezione.
	log.debug("RECUPERO RICHIEDENTE");
	PersonaFisicaType richiedente = null;
	if (dettaglioPraticaType.getRichiedente() != null && dettaglioPraticaType.getRichiedente().getAnagrafica() != null) {
	    richiedente = dettaglioPraticaType.getRichiedente().getAnagrafica();
	}
	if (richiedente == null && dettaglioPraticaType.getAziendaRichiedente() == null) {
	    // rilancio eccezione
	    log.error("populateIstanza(): Nella pratica ricevuta non sono presenti né un richiedente né una azienda.");
	    throw new RuntimeException("Nella pratica ricevuta non sono presenti né un richiedente né una azienda.");
	}
	if (richiedente == null) {
	    // 2012-05-15 BOCCI: dopo la modifica sulla non obbligatorietà del richiedente se questo arriva nullo
	    //	allora metto come richiedente dell'istanza l'azienda e se non presente anche questa rilancio eccezione.
	    populateRichiedenteDaAzienda(istanza, dettaglioPraticaType.getAziendaRichiedente(), praticaNla);
	} else {
	    AnagrafeType richiedenteAT = new AnagrafeType();
	    richiedenteAT.setPersonaFisica(richiedente);
	    populateAnagrafe(istanza.getRichiedente(), richiedenteAT, false, praticaNla);
	    //AZIENDA RICHIEDENTE
	    log.debug("RECUPERO AZIENDA RICHIEDENTE");
	    populateAzienda(istanza, dettaglioPraticaType.getAziendaRichiedente(), praticaNla);
	}
	//RICHIEDENTE IN QUALITA' DI
	log.debug("RECUPERO RICHIEDENTE IN QUALITA' DI");
	populateInQualitaDi(istanza, praticaNla);
	// TECNICO
	log.debug("RECUPERO TECNICO");
	populateAnagrafe(istanza.getProfessionista(), dettaglioPraticaType.getIntermediario(), true, praticaNla);
	boolean dbInfo = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_DBINFORMATICA);
	// Nel caso di DBINFORMATICA il richiedente Viene travasato anche nel Tecnico/Professionista FRANCO VOLPE 22/09/2017
	if (dbInfo) {
	    istanza.setProfessionista(istanza.getRichiedente());
	}
	// COMUNE ASSOCIATO
	log.debug("RECUPERO COMUNE ASSOCIATO");
	populateComuneAssociato(dettaglioPraticaType, istanza);
	// PROTOCOLLO LO DEVO METTERE DOPO AVER TROVATO IL COMUNE PER VERIFICARE LE IMPOSTAZIONI DELLA PROTOCOLLAZIONE
	log.debug("RECUPERO PROTOCOLLO");
	populateProtocollo(istanza, dettaglioPraticaType, passaProtocollo, istanza.getComune().getCodicecomune(), praticaNla.getSportelloMittente(),
		praticaNla.getSportelloDestinatario());
	// LOCALIZZAZIONE
	log.debug("RECUPERO LOCALIZZAZIONE");
	populateLocalizzazione(istanza, praticaNla, istanza.getComune());
	// DATA ED ORA
	log.debug("RECUPERO DATA ED ORA");
	populateData(istanza, dettaglioPraticaType.getDataPratica());
	//GIANPAOLO-ORA
	if (StringUtils.isNotBlank(dettaglioPraticaType.getOraDataPratica())) {
	    Date _date = addTimeToDate(istanza.getData(), dettaglioPraticaType.getOraDataPratica());
	    istanza.setData(_date);
	}
	if (dettaglioPraticaType.getNaturaFo() != null) {
	    istanza.setNatura(dettaglioPraticaType.getNaturaFo().value());
	}
	// DOCUMENTI
	log.debug("RECUPERO DOCUMENTI");
	populateDocumenti(istanza, praticaNla);
	// OGGETTO
	log.debug("RECUPERO OGGETTO");
	istanza.setLavori(dettaglioPraticaType.getOggetto());
	// SOGGETTI COLLEGATI
	log.debug("RECUPERO SOGGETTI COLLEGATI");
	populateSoggettiCollegati(istanza, praticaNla);
	// PROCURE
	log.debug("RECUPERO PROCURE");
	populateProcure(istanza, praticaNla);
	// STATO ISTANZA
	log.debug("RECUPERO STATO DELL' ISTANZA DALLA CONFIGURAZIONE");
	populateStatoIstanza(praticaNla, istanza);
	// ALTRI DATI 
	//(Vengono settati all'istanza tutti gli elementi che sono presenti nella sezione "Altri dati" della domanda proveniente dall'NLA)
	populateIstanzaAltridati(praticaNla, istanza);
	// ONERI
	// BOCCI-CHIOCCI 2012-08-13
	// In caso che non sia un inserimento pratica diretto (domanda on-line) togliere gli oneri della pratica in modo che 
	// il destinatario della pratica non abbia i riferimenti agli oneri, di seguito le questioni in sospeso:
	boolean isInserimentoDiretto = isInserimentoDiretto(praticaNla.getDettaglioPratica().getAltriDati());
	if (isInserimentoDiretto) {
	    populateIstanzaOneri(praticaNla, istanza);
	}
	// 2016-12-07 VERIFICO SE COLLEGARE ALTRE ISTANZE A QUELLA PRINCIPALE
	populateIstanzacollegataDaAltridati(praticaNla, istanza);
	populateTipologiaIstanzaDaAltridati(praticaNla, istanza);
	istanzeNlaHelper.setIstanze(istanza);
	istanzeNlaHelper.setNumeroInterventi(numeroInterventi);
	return istanzeNlaHelper;
    }

    private String generateNumeroistanzaFromIntervento(Istanze istanzeTemp) {

	String numeroIstanza = "";
	try {
	    if (StringUtils.isBlank(numeroIstanza)) {
		numeroIstanza = istanzeService.findProgressivoIstanza(istanzeTemp.getAlberoproc().getId().getCodice(), true);
	    }
	} catch (Exception e) {
	    log.error("generateNumeroistanza: {}", e.getMessage(), e);
	}
	return numeroIstanza;
    }

    private void populateInterventoSuaper(InserimentoPraticaNLARequest praticaNla, Istanze istanza) {

	Verticalizzazioniparametri vertAlberoproc = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_SUAPER,
		WebConstants.VERTICALIZZAZIONE_SUAPER_CODICEINTERVENTO_DEFAULT);
	Integer codiceIntervento = null;
	if (vertAlberoproc != null && StringUtils.isNotBlank(StringUtils.defaultString(vertAlberoproc.getValore()).trim())) {
	    if (Utilities.isInteger(StringUtils.defaultString(vertAlberoproc.getValore()).trim())) {
		codiceIntervento = Integer.parseInt(StringUtils.defaultString(vertAlberoproc.getValore()).trim());
	    }
	}
	log.debug("populateInterventoSuaper#codiceIntervento_default {}", codiceIntervento);
	// lista degli endoprocediment della pratica
	Set<Integer> codiciEndoprocedimenti = new HashSet<Integer>();
	if (istanza.getIstanzeprocedimentis() != null && istanza.getIstanzeprocedimentis().size() > 0) {
	    for (Istanzeprocedimenti ips : istanza.getIstanzeprocedimentis()) {
		if (ips.getId().getCodiceinventario() != null) {
		    codiciEndoprocedimenti.add(ips.getId().getCodiceinventario());
		}
	    }
	    log.debug("populateInterventoSuaper#lista degli endo attivati {}", codiciEndoprocedimenti);
	    // SE SONO PRESENTI ENDO PROCEDIMENTI
	    if (!codiciEndoprocedimenti.isEmpty()) {
		gestisciWarningEndoNonMappati(codiciEndoprocedimenti, istanza);
		// cerco le configurazioni dei gruppi che abbiano al loro interno gli endo della pratica e verifico se 1 2 o tre e più
		Set<Integer> codiciGruppiTrovatis = gruppiEndoprocedimentiDService.findByEndoprocedimenti(codiciEndoprocedimenti,
			ORMHelper.getSoftware());
		log.debug("populateInterventoSuaper#codiciGruppiTrovatis {}", codiciGruppiTrovatis);
		// quanti gruppi sono stati trovati in generale sulla pratica
		int gruppiEndoSize = codiciGruppiTrovatis.size();
		// Se ho trovato dei gruppi configurati per quei endoprocedimenti
		if (gruppiEndoSize > 0) {
		    // per ogni gruppo verifico i numendo warning per settare lo stato
		    List<ChiaveValoreBean<Integer, String>> warnings = gruppiEndoprocedimentiDService
			    .findByEndoprocedimentiConWarning(codiciEndoprocedimenti, ORMHelper.getSoftware());
		    log.debug("populateInterventoSuaper#warnings {}", warnings);
		    if (warnings.size() > 0) {
			// prendo il primo che setto un solo stato dell'istanza
			ChiaveValoreBean<Integer, String> warning = warnings.get(0);
			if (warning != null && StringUtils.isNotBlank(warning.getValore())) {
			    String tipomovimento = warning.getValore();
			    Tipimovimento tmwarning = tipiMovimentoService.findById(new TipimovimentoId(tipomovimento));
			    if (tmwarning != null) {
				Movimenti mm = new Movimenti();
				mm.setTipomovimento(tmwarning);
				mm.setData(Calendar.getInstance().getTime());
				istanza.getIstanzemovimentis().add(mm);
			    }
			}
		    }
		    // popolo la classe Helper
		    log.debug("populateInterventoSuaper#popolo helper");
		    GruppiSmistamentoClassiHelper helper = alberoprocGruppiSmistService.getGruppiSmistamentoClassiHelper();
		    // contiene la mappa con il numero dei gruppi configurati e la lista dei codici di configurazione
		    Map<Integer, Set<Integer>> mappaGruppiClassi = helper.getMappaGruppiClassi();
		    Map<Integer, List<GruppiSmistHelper>> mappaGruppiConf = helper.getMappaGruppiConf();
		    log.debug("populateInterventoSuaper#recuperate le mappe dell'helper");
		    // lista delle configurazioni di N elementi, ovvero quali sono le configurazioni che hann oconfigurato un solo, due  o tre gruppi
		    Set<Integer> confDiNElementi = null;
		    ArrayList<Integer[]> permPossibiliInClasseK3 = null;
		    if (gruppiEndoSize == 1) {
			// solo le configurazioni di 1 gruppo configurato
			log.debug("populateInterventoSuaper#confDiNElementi di classe 1");
			confDiNElementi = mappaGruppiClassi.get(1);
		    } else if (gruppiEndoSize == 2) {
			// solo le configurazioni di 2 gruppi configurati
			confDiNElementi = mappaGruppiClassi.get(2);
			log.debug("populateInterventoSuaper#confDiNElementi di classe 2");
		    } else if (gruppiEndoSize == 3) {
			// solo le configurazioni di 3 gruppi configurati
			confDiNElementi = mappaGruppiClassi.get(3);
			log.debug("populateInterventoSuaper#confDiNElementi di classe 3");
		    } else if (gruppiEndoSize > 3) {
			// più di tre gruppi trovati per la pratica devo applicare le permutazioni
			log.debug("populateInterventoSuaper#confDiNElementi di classe 3 con più di tre gruppi trovati");
			confDiNElementi = mappaGruppiClassi.get(3);
			Integer[] insieme = codiciGruppiTrovatis.toArray(new Integer[codiciGruppiTrovatis.size()]);
			log.debug("populateInterventoSuaper#cerco le permutazioni");
			permPossibiliInClasseK3 = GruppiSmistamentoClassiHelper.getPermPossibiliInClasseK3(insieme);
			if (log.isDebugEnabled()) {
			    log.debug("populateInterventoSuaper#permutazioni trovate {}", permPossibiliInClasseK3.size());
			    int i = 1;
			    for (Integer[] val : permPossibiliInClasseK3) {
				String perm = "";
				if (val != null) {
				    for (int j = 0; j < val.length; j++) {
					perm += val[j] + ",";
				    }
				}
				log.debug("populateInterventoSuaper#permutazione {}\t\t\t{}", i, perm);
				i++;
			    }
			}
		    }
		    // ok = trovo una sola riga di configurazione oppure più configurazioni con la stessa destinazione
		    if (confDiNElementi != null && confDiNElementi.size() > 0) {
			log.debug("populateInterventoSuaper#confDiNElementi {}", confDiNElementi.size());
			Integer codiceConf = null;
			Set<Integer> cartelleTrovate = new HashSet<Integer>();
			for (Integer codiceConfigurazione : confDiNElementi) {
			    log.debug("populateInterventoSuaper#verifico la Configurazione con codice {}", codiceConfigurazione);
			    // per ogni configurazione verifico se contiene gli endo trovati
			    List<GruppiSmistHelper> listSmh = mappaGruppiConf.get(codiceConfigurazione);
			    log.debug("populateInterventoSuaper#per la Configurazione con codice {} sono presenti {} smistamenti",
				    codiceConfigurazione, listSmh.size());
			    Set<Integer> gruppisConf = new HashSet<Integer>(3);
			    // aggiungo al gruppo gli endo
			    for (GruppiSmistHelper gruppiSmistHelper : listSmh) {
				gruppisConf.add(gruppiSmistHelper.getGruppo());
			    }
			    if (permPossibiliInClasseK3 == null) {
				log.debug("populateInterventoSuaper#verifico se  codiciGruppiTrovatis.containsAll(gruppisConf)");
				if (codiciGruppiTrovatis.containsAll(gruppisConf)) {
				    log.debug("populateInterventoSuaper#è vero che codiciGruppiTrovatis.containsAll(gruppisConf)");
				    // se la configurazione contiene gli endo trovati
				    codiceConf = codiceConfigurazione;
				    AlberoprocGruppiSmist conf = alberoprocGruppiSmistService.findById(new PkId(codiceConf));
				    cartelleTrovate.add(conf.getAlberoproc().getId().getCodice());
				}
			    } else {
				// tre o più gruppi
				log.debug("populateInterventoSuaper#ciclo le permutazioni");
				for (Integer[] codiciGruppoPerm : permPossibiliInClasseK3) {
				    Set<Integer> codiciGruppiTrovatisPermutati = new HashSet<Integer>(3);
				    codiciGruppiTrovatisPermutati.add(codiciGruppoPerm[0]);
				    codiciGruppiTrovatisPermutati.add(codiciGruppoPerm[1]);
				    codiciGruppiTrovatisPermutati.add(codiciGruppoPerm[2]);
				    log.debug("populateInterventoSuaper#verifico se  codiciGruppiTrovatis.containsAll(gruppisConf)..2");
				    if (codiciGruppiTrovatis.containsAll(gruppisConf)) {
					log.debug("populateInterventoSuaper#è vero che codiciGruppiTrovatis.containsAll(gruppisConf)..2");
					codiceConf = codiceConfigurazione;
					AlberoprocGruppiSmist conf = alberoprocGruppiSmistService.findById(new PkId(codiceConf));
					cartelleTrovate.add(conf.getAlberoproc().getId().getCodice());
				    }
				}
			    }
			}
			log.debug("populateInterventoSuaper#cartelleTrovate.size() {}", cartelleTrovate.size());
			if (cartelleTrovate.size() == 1) { // se ho trovato una sola riga o più con lo stesso  intervento 
			    AlberoprocGruppiSmist conf = alberoprocGruppiSmistService.findById(new PkId(codiceConf));
			    codiceIntervento = conf.getAlberoproc().getId().getCodice();
			    istanza.getAlberoproc().getId().setCodice(codiceIntervento);
			    settaProceduraSuaper(conf, codiceIntervento, codiciEndoprocedimenti, istanza);
			    return;
			}
		    } else {
			log.error("trovati 0 o più gruppi");
		    }
		}
	    }
	}
	log.debug("populateInterventoSuaper#FINITO NEL BIDONE {}", codiceIntervento);
	istanza.getAlberoproc().getId().setCodice(codiceIntervento);
    }

    private void gestisciWarningEndoNonMappati(Set<Integer> codiciEndoprocedimenti, Istanze istanza) {

	Set<Integer> list = gruppiEndoprocedimentiDService.findEndoProcedimentiNonPresentiInGruppi(codiciEndoprocedimenti);
	if (!list.isEmpty()) {
	    for (Integer codiceInventario : list) {
		Inventarioprocedimenti endo = inventarioprocedimentiService.findById(new PkId(codiceInventario));
		Istanzeeventi evento = new Istanzeeventi();
		evento.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI);
		evento.setIstanze(istanza);
		String err = "Il procedimento " + StringUtils.defaultIfEmpty(endo.getProcedimento(), "") + " (" + endo.getId() +
			     ") non è stato configurato nella funzionalità \"Gruppi di procedimenti per lo smistamento di pratiche STC\".";
		evento.setDescrizione(err);
		log.warn("gestisciWarningEndoNonMappati: {}", err);
		istanza.aggiungiEvento(evento);
	    }
	}
    }

    private void settaProceduraSuaper(AlberoprocGruppiSmist conf, Integer codiceIntervento, Set<Integer> codiciEndoprocedimenti, Istanze istanza) {

	log.debug("settaProceduraSuaper");
	if (conf.getTipiprocedureSCIA() != null || conf.getTipiprocedureOrdinario() != null) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceIntervento));
	    log.debug("settaProceduraSuaper#trovato l''intervento {}", codiceIntervento);
	    log.debug("settaProceduraSuaper#sono presenti configurazioni su procedura SCIA o ORDINARIO ricerco AlberoprocHelper per l''intervento {}",
		    codiceIntervento);
	    AlberoprocHelper ah = alberoprocService.findAlberoprocHelper(alberoproc);
	    Tipiprocedure tipoProcedura = ah.getTipoProcedura();
	    log.debug("settaProceduraSuaper#la procedura è presente ? {}", ah.getTipoProcedura() != null);
	    List<Inventarioprocedimenti> ps = inventarioprocedimentiService.findByCodiciInventario(codiciEndoprocedimenti);
	    //Set<Integer> codiciNaturaDegliEndo = new HashSet<Integer>();
	    NatureProcedureService.NATURA_BASE_ENUM naturaEndo = NatureProcedureService.NATURA_BASE_ENUM.comunicazione;
	    NatureProcedureService.NATURA_BASE_ENUM naturaProcedura = NatureProcedureService.NATURA_BASE_ENUM.comunicazione;
	    for (Inventarioprocedimenti ip : ps) {
		if (ip.getNaturaendo() != null && ip.getNaturaendo().getId() != null && ip.getNaturaendo().getId().getCodice() != null) {
		    // codiciNaturaDegliEndo.add(ip.getNaturaendo().getId().getCodice());
		    String naturaEndoBase = ip.getNaturaendo().getNaturabase();
		    if (StringUtils.isNotBlank(naturaEndoBase)) {
			log.debug("settaProceduraSuaper#la natura dell''endo {} endo è  {}", ip.getId().getCodice(), naturaEndoBase);
			if (naturaEndoBase.equals(NatureProcedureService.NATURA_BASE_ENUM.scia.name())) {
			    naturaEndo = NatureProcedureService.NATURA_BASE_ENUM.scia;
			}
			if (naturaEndoBase.equals(NatureProcedureService.NATURA_BASE_ENUM.ordinario.name())) {
			    naturaEndo = NatureProcedureService.NATURA_BASE_ENUM.ordinario;
			    // è il più alto esco la pratica è di tipo ordinario
			    break;
			}
		    }
		}
	    }
	    log.debug("settaProceduraSuaper#la natura degli endo è  {}", naturaEndo);
	    if (tipoProcedura != null) {
		Naturaendo ne = tipoProcedura.getNaturaendo();
		if (ne != null) {
		    if (StringUtils.isNotBlank(ne.getNaturabase())) {
			log.debug("settaProceduraSuaper#la natura della procedura {} è  {}", tipoProcedura.getId().getCodice(), ne.getNaturabase());
			naturaProcedura = NatureProcedureService.NATURA_BASE_ENUM.valueOf(ne.getNaturabase());
			if (naturaProcedura.name().equals(naturaEndo.name())) {
			    log.debug("settaProceduraSuaper#la natura della procedura e dell'endo sono uguali {}", naturaProcedura.name());
			    // sia gli endo che la procedura hanno la stessa natura base
			    // verifico se sovrascrivere la natura SCIA o ORDINARIA
			    if (naturaProcedura.name().equalsIgnoreCase(NatureProcedureService.NATURA_BASE_ENUM.scia.name())) {
				if (conf.getTipiprocedureSCIA() != null) {
				    istanza.setProcedura(conf.getTipiprocedureSCIA());
				    return;
				}
			    }
			    if (naturaProcedura.name().equalsIgnoreCase(NatureProcedureService.NATURA_BASE_ENUM.ordinario.name())) {
				if (conf.getTipiprocedureOrdinario() != null) {
				    istanza.setProcedura(conf.getTipiprocedureOrdinario());
				    return;
				}
			    }
			    // in questo caso ho comunicazione e setto quella
			    istanza.setProcedura(tipoProcedura);
			    return;
			} else {
			    log.debug("settaProceduraSuaper#la natura della procedura e dell''endo non sono uguali P={},E={}", naturaProcedura.name(),
				    naturaEndo);
			    // Natura Endo e natura Procedura non sono le stesse
			    int livelloNaturaEndo = getLivelloFromNatura(naturaEndo);
			    int livelloNaturaProcedura = getLivelloFromNatura(naturaProcedura);
			    log.debug("settaProceduraSuaper#livello natura endo {}, livello procedura {}", livelloNaturaEndo, livelloNaturaProcedura);
			    int livello = 0;
			    if (livelloNaturaEndo > livelloNaturaProcedura) {
				livello = livelloNaturaEndo;
			    } else {
				livello = livelloNaturaProcedura;
			    }
			    log.debug("settaProceduraSuaper#livello {}", livello);
			    NatureProcedureService.NATURA_BASE_ENUM natura = getNaturaFromLivello(livello);
			    if (natura.name().equalsIgnoreCase(NatureProcedureService.NATURA_BASE_ENUM.scia.name())) {
				if (conf.getTipiprocedureSCIA() != null) {
				    istanza.setProcedura(conf.getTipiprocedureSCIA());
				    return;
				}
			    }
			    if (natura.name().equalsIgnoreCase(NatureProcedureService.NATURA_BASE_ENUM.ordinario.name())) {
				if (conf.getTipiprocedureOrdinario() != null) {
				    istanza.setProcedura(conf.getTipiprocedureOrdinario());
				    return;
				}
			    }
			    istanza.setProcedura(tipoProcedura);
			    return;
			}
		    }
		}
	    }
	}
    }

    private int getLivelloFromNatura(NatureProcedureService.NATURA_BASE_ENUM natura) {

	if (natura.equals(NatureProcedureService.NATURA_BASE_ENUM.comunicazione)) {
	    return 1;
	}
	if (natura.equals(NatureProcedureService.NATURA_BASE_ENUM.scia)) {
	    return 2;
	}
	return 3;
    }

    private NatureProcedureService.NATURA_BASE_ENUM getNaturaFromLivello(int livello) {

	if (livello == 1) {
	    return NatureProcedureService.NATURA_BASE_ENUM.comunicazione;
	}
	if (livello == 2) {
	    return NatureProcedureService.NATURA_BASE_ENUM.scia;
	}
	return NatureProcedureService.NATURA_BASE_ENUM.ordinario;
    }

    private void populateTipologiaIstanzaDaAltridati(InserimentoPraticaNLARequest praticaNla, Istanze istanza) {

	if (praticaNla.getDettaglioPratica() != null) {
	    List<ParametroType> altriDati = praticaNla.getDettaglioPratica().getAltriDati();
	    String codiceTipologiaIstanza = "";
	    String descrizioneTipologiaIstanza = "";
	    if (altriDati != null) {
		for (ParametroType parametroType : altriDati) {
		    if (parametroType.getNome().equalsIgnoreCase(ALTRODATO_BACKOFFICE_TIPOLOGIA_ISTANZA)) {
			codiceTipologiaIstanza = StringUtils.defaultString(parametroType.getValore().get(0).getCodice()).trim();
			descrizioneTipologiaIstanza = StringUtils.defaultString(parametroType.getValore().get(0).getDescrizione()).trim();
			break;
		    }
		}
	    }
	    if (StringUtils.isNotBlank(codiceTipologiaIstanza) || StringUtils.isNotBlank(descrizioneTipologiaIstanza)) {
		Tipologiaistanza ti = new Tipologiaistanza();
		if (Utilities.isInteger(codiceTipologiaIstanza)) {
		    Integer codiceTipologiaIstanzaI = Integer.valueOf(codiceTipologiaIstanza);
		    if (codiceTipologiaIstanzaI != null) {
			ti.getId().setCodice(codiceTipologiaIstanzaI);
		    }
		}
		if (StringUtils.isNotBlank(descrizioneTipologiaIstanza)) {
		    ti.setTiDescrizione(descrizioneTipologiaIstanza);
		}
		istanza.setTipologiaistanza(ti);
	    }
	}
    }

    private void populateIstanzacollegataDaAltridati(InserimentoPraticaNLARequest praticaNla, Istanze istanza) {

	if (praticaNla.getDettaglioPratica() != null) {
	    boolean inserisciComePadre = false;
	    List<ParametroType> altriDati = praticaNla.getDettaglioPratica().getAltriDati();
	    String codiceIstanzaCollegata = "";
	    if (altriDati != null) {
		for (ParametroType parametroType : altriDati) {
		    if (parametroType.getNome().equalsIgnoreCase(ALTRODATO_BACKOFFICE_CERCA_PRATICA_COLLEGATA_PADRE)) {
			inserisciComePadre = true;
			codiceIstanzaCollegata = StringUtils.defaultString(parametroType.getValore().get(0).getCodice()).trim();
			break;
		    }
		}
	    }
	    // 
	    if (StringUtils.isNotBlank(codiceIstanzaCollegata)) {
		if (Utilities.isInteger(codiceIstanzaCollegata)) {
		    Integer codiceIstanzaCollegataI = Integer.valueOf(codiceIstanzaCollegata);
		    Istanze i = istanzeService.findById(new PkId(codiceIstanzaCollegataI));
		    Verticalizzazioniparametri tsd = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
			    WebConstants.VERTICALIZZAZIONE_STC_PRATICHE_COLLEGATE_INSERISCI_STESSO_ISTRUTTORE);
		    if (tsd != null) {
			if (StringUtils.isNotBlank(tsd.getValore())) {
			    if (StringUtils.defaultString(tsd.getValore().trim(), "N").equalsIgnoreCase("S")) {
				if (i.getIstruttore() != null) {
				    log.debug("Assegnazione stesso istruttore per le pratiche collegate abilitata assegno l'istruttore {}",
					    i.getIstruttore().toString());
				    istanza.setIstruttore(i.getIstruttore());
				    LoggerCancellazioni.log(
					    "###NLA-STC.IP### Assegnazione stesso istruttore per le pratiche collegate abilitata assegno l'istruttore " +
							    i.getIstruttore() + " per la pratica " + istanza.getNumeroistanza());
				} else {
				    log.error(
					    "Assegnazione stesso istruttore per le pratiche collegate abilitata ma NON è stato possibile recuperare l'istruttore nella pratica {}",
					    i);
				}
			    }
			}
		    }
		    if (i != null) {
			Istanzecollegate ic = new Istanzecollegate();
			if (inserisciComePadre) {
			    ic.setIstanza(i); // istanzapadre
			    // ic.setIstanzaDacollegare(istanza); // questa
			    ic.setIstanzaDacollegare(null); // questa
			} else {
			    // ic.setIstanza(istanza); // questa
			    ic.setIstanza(null); // questa
			    ic.setIstanzaDacollegare(i); // istanzapadre
			}
			istanza.getIstanzecollegates().add(ic);
		    }
		}
	    }
	}
    }

    /**
     * A partire dal codiceintervento popolato viene letta la configurazione di alberoproc.codiceoperatore_stc a ritroso
     * per recuperare se presente l'operatore da assegnare all'istanza
     * 
     * @param istanza
     */
    private void populateOperatoreDefault(Istanze istanza) {

	if (istanza != null) {
	    if (istanza.getAlberoproc() != null) {
		if (istanza.getAlberoproc().getId() != null) {
		    if (istanza.getAlberoproc().getId().getCodice() != null) {
			Alberoproc intervento = alberoprocService.findById(new PkId(istanza.getAlberoproc().getId().getCodice()));
			if (intervento != null) {
			    AlberoprocHelper helper = alberoprocService.findAlberoprocHelper(intervento);
			    if (helper != null) {
				if (helper.getOperatoreStc() != null) {
				    if (helper.getOperatoreStc().getId() != null) {
					if (helper.getOperatoreStc().getId().getCodice() != null) {
					    istanza.setResponsabile(helper.getOperatoreStc());
					}
				    }
				}
			    }
			}
		    }
		}
	    }
	}
    }

    private void populateRichiedenteDaAzienda(Istanze istanza, PersonaGiuridicaType aziendaRichiedente, InserimentoPraticaNLARequest praticaNla) {

	// 2012-05-15 BOCCI: dopo la modifica sulla non obbligatorietà del richiedente se questo arriva nullo
	//	allora metto come richiedente dell'istanza l'azienda e se non presente anche questa rilancio eccezione.
	AnagrafeType azienda = new AnagrafeType();
	azienda.setPersonaGiuridica(aziendaRichiedente);
	populateAnagrafe(istanza.getRichiedente(), azienda, false, praticaNla);
	// NEL CASO LA PRATICA VENGA DAL NODO AIDA E NELLA PersonaGiuridicaType.naturaGiuridica == "PERSONA FISICA" ALLORA
	// INSERISCO ISTANZE EVENTI CHE INDICANO ALL'OPERATORE DI UTILIZZARE LA FUNZIONALITA' DI TRASFORMA IN PERSONA FISICA
	NodoNLAEnum nodoAida = getTipoNodo(praticaNla.getSportelloMittente());
	if (nodoAida != null) {
	    if (nodoAida.equals(NodoNLAEnum.NLA_IDNODO_AIDA)) {
		if (StringUtils.defaultIfEmpty(aziendaRichiedente.getNaturaGiuridica(), "").equalsIgnoreCase(AIDA_IDENTIFICATIVO_PERSONA_FISICA)) {
		    Set<Istanzeeventi> eventi = istanza.getIstanzeeventis();
		    if (eventi == null) {
			eventi = new HashSet<Istanzeeventi>();
		    }
		    Istanzeeventi evento = new Istanzeeventi();
		    evento.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_STC_IP);
		    evento.setIstanze(istanza);
		    String messaggioEvento = Utilities.getMessageFromBundle(context, "eventi.stc.aida.trasformazione_richiedente_pg_pf", null);
		    evento.setDescrizione(messaggioEvento);
		    eventi.add(evento);
		    istanza.setIstanzeeventis(eventi);
		}
	    }
	}
    }

    private void populateIstanzaOneri(InserimentoPraticaNLARequest praticaNla, Istanze istanza) {

	List<OneriType> oneri = praticaNla.getDettaglioPratica().getOneri();
	if (oneri == null || oneri.isEmpty()) {
	    return;
	}
	for (OneriType oneriType : oneri) {
	    boolean onereNonDovuto = (oneriType.isNonDovuto() != null && oneriType.isNonDovuto());
	    // SE ONERE NON DOVUTO ALLORA SIGNIFICA CHE MI ARRIVA DA UN NODO STC CHE LO HA SPECIFICATO ALLORA
	    // DEVO COMUNQUE INSERIRE L'ONERE ANCHE SE CON IMPORTO=0
	    if (oneriType.getImporto() <= 0 && onereNonDovuto == false) {
		continue;
	    }
	    //...
	    // Verifico se l'onere è non dovuto, se non è duvuto setto la variabile nonDovuto a true
	    // e la setto in istanzeoneri sul campo "flagNondovuto"
	    BigDecimal importo = null;
	    Amministrazioni amm = null;
	    Inventarioprocedimenti endoMappato = null;
	    Boolean entrata = !SegnoType.USCITA.equals(oneriType.getSegno());
	    String codiceEndoStr = oneriType.getCodiceProcedimento();
	    if (StringUtils.isNotBlank(codiceEndoStr)) {
		ProcedimentoType procedimentoType = new ProcedimentoType();
		procedimentoType.setCodice(codiceEndoStr);
		endoMappato = findEndoMappato(praticaNla.getSportelloMittente(), praticaNla.getSportelloDestinatario(), procedimentoType);
		if (endoMappato != null) {
		    log.debug("Trovato endo {} per questo onere.", endoMappato.getProcedimento());
		    amm = endoMappato.getAmministrazioni();
		} else {
		    log.error("Non è stato possibile decodificare il procedimento con codice {}", codiceEndoStr);
		}
	    }
	    Tipicausalioneri causonere = null;
	    CausaleOnereType causale = oneriType.getCausale();
	    String codCausale = "";
	    String descrizioneCausale = "";
	    if (causale != null) {
		codCausale = causale.getId();
		descrizioneCausale = causale.getCausale();
		try {
		    causonere = this.decodeCausaleonere(causale, praticaNla);
		} catch (DecodificaOneriExceptions e) {
		    //è presente qualche errore di configurazione per cui non si risale all'onere
		    istanza.aggiungiEvento(Istanzeeventi.nuovoEventoIstanzaDaInserimentoPraticaSTC(istanza, e.getMessage()));
		}
	    }
	    if (causonere == null) {
		String messaggio = "Non è stato possibile trovare una causale per un onere [Codice: " + codCausale + ", Descrizione: " +
				   descrizioneCausale + "]. L'onere non è stato inserito.";
		istanza.aggiungiEvento(Istanzeeventi.nuovoEventoIstanzaDaInserimentoPraticaSTC(istanza, messaggio));
	    } else {
		log.debug("trovata la causale oneri per questo onere {}_{}", causonere.getCoDescrizione(), causonere.getId());
		if (oneriType.getScadenze().isEmpty()) {
		    log.debug("L'onere non ha scadenze ed sarà composto solamente di un record sulla base dati");
		    Istanzeoneri onere = new Istanzeoneri();
		    onere.setInventarioprocedimenti(endoMappato);
		    onere.setAmministrazioni(amm);
		    onere.setTipicausalioneri(causonere);
		    onere.setFlentratauscita(entrata);
		    importo = BigDecimal.valueOf(oneriType.getImporto());
		    onere.setPrezzo(importo);
		    onere.setPrezzoistruttoria(BigDecimal.ZERO);
		    onere.setNote(oneriType.getAnnotazioni());
		    onere.setFlagNondovuto(onereNonDovuto);
		    istanza.getIstanzeoneris().add(onere);
		} else {
		    log.debug(
			    "Sono presenti delle scadenze se sono presenti dei pagamenti allora avrò tanti record istanze oneri quanti sono i pagamenti altrimenti quante sono le scadenze  ({})",
			    oneriType.getScadenze().size());
		    List<OneriScadenzeType> scadenze = oneriType.getScadenze();
		    for (OneriScadenzeType oneriScadenze : scadenze) {
			Date dataScadenza = null;
			if (oneriScadenze.getDataScadenza() != null) {
			    dataScadenza = oneriScadenze.getDataScadenza().toGregorianCalendar().getTime();
			}
			Integer numRata = null;
			if (StringUtils.isNotBlank(oneriScadenze.getNumeroRata())) {
			    try {
				numRata = Integer.parseInt(oneriScadenze.getNumeroRata());
			    } catch (Exception e) {
				String evento = "Non è stato possibile recuperare il numerorata " + oneriScadenze.getNumeroRata();
				log.error(evento);
				istanza.aggiungiEvento(Istanzeeventi.nuovoEventoIstanzaDaInserimentoPraticaSTC(istanza, evento));
			    }
			}
			if (oneriScadenze.getPagamenti().isEmpty()) {
			    Istanzeoneri onere = new Istanzeoneri();
			    onere.setInventarioprocedimenti(endoMappato);
			    onere.setAmministrazioni(amm);
			    onere.setTipicausalioneri(causonere);
			    onere.setDatascadenza(dataScadenza);
			    onere.setFlentratauscita(entrata);
			    importo = BigDecimal.valueOf(oneriScadenze.getImportoRata());
			    onere.setPrezzo(importo);
			    onere.setPrezzoistruttoria(BigDecimal.ZERO);
			    onere.setNumerorata(numRata);
			    onere.setNote(oneriType.getAnnotazioni());
			    onere.setFlagNondovuto(onereNonDovuto);
			    istanza.getIstanzeoneris().add(onere);
			} else {
			    log.debug("Sono presenti dei pagamenti allora avrò tanti record istanze oneri quanti sono i pagamenti {}",
				    oneriScadenze.getPagamenti().size());
			    List<OneriPagamentiType> pagamenti = oneriScadenze.getPagamenti();
			    for (OneriPagamentiType oneriPagamenti : pagamenti) {
				Date dataPagamento = null;
				if (oneriPagamenti.getData() != null) {
				    dataPagamento = oneriPagamenti.getData().toGregorianCalendar().getTime();
				}
				Istanzeoneri onere = new Istanzeoneri();
				onere.setInventarioprocedimenti(endoMappato);
				onere.setAmministrazioni(amm);
				onere.setTipicausalioneri(causonere);
				onere.setDatascadenza(dataScadenza);
				onere.setFlentratauscita(entrata);
				importo = BigDecimal.valueOf(oneriScadenze.getImportoRata());
				onere.setPrezzo(importo);
				onere.setPrezzoistruttoria(BigDecimal.ZERO);
				onere.setNumerorata(numRata);
				onere.setImportopagato(BigDecimal.valueOf(oneriPagamenti.getImporto()));
				onere.setDatapagamento(dataPagamento);
				String descrizioneModalita = oneriPagamenti.getModalita();
				onere.setNote(oneriType.getAnnotazioni());
				log.debug("Descrizione modalità pagamento {}", descrizioneModalita);
				if (StringUtils.isNotBlank(descrizioneModalita)) {
				    List<Tipimodalitapagamento> tmps = tipimodalitapagamentoService.findByMpDescrestesa(descrizioneModalita, true);
				    if (tmps.isEmpty()) {
					String evento = "La modalità di pagamento " + descrizioneModalita + " utilizzata non è stata trovata";
					log.debug(evento);
					istanza.aggiungiEvento(Istanzeeventi.nuovoEventoIstanzaDaInserimentoPraticaSTC(istanza, evento));
				    } else {
					if (tmps.size() > 1) {
					    String evento = "Sono state trovate più modalità di pagamento con descrizione " + descrizioneModalita +
							    ", verrà utilizzata quella con codice " + tmps.get(0).getId().getCodice();
					    log.debug(evento);
					    istanza.aggiungiEvento(Istanzeeventi.nuovoEventoIstanzaDaInserimentoPraticaSTC(istanza, evento));
					}
					onere.setTipimodalitapagamento(tmps.get(0));
				    }
				}
				onere.setDocriferimento(oneriPagamenti.getRifDocumento());
				onere.setFlagNondovuto(onereNonDovuto);
				istanza.getIstanzeoneris().add(onere);
			    }
			}
		    }
		}
	    }
	}
    }

    /**
     * Se nodo Interno allora cerca direttamente per codice causale.<br/>
     * Se nodo PEOPLE allora cerca la causale per TIPICAUSALIONERI.CODICECAUSALEPEOPLE prima nel software corrente poi
     * in TT e se non trovata nel valore di default delle verticalizzazioni
     * verticalizzazioni.PEOPLE.CODICECAUSALEONEREDEFAULT.<br />
     * Se non trovata allora fa una ricerca per la descrizione della causale con software corrente e poi con software
     * TT.<br />
     * Se non trovata torna Null
     * 
     * @param causale
     * @param praticaNla
     * @return
     */
    private Tipicausalioneri decodeCausaleonere(CausaleOnereType causale, InserimentoPraticaNLARequest praticaNla) throws DecodificaOneriExceptions {

	String idCausale = causale.getId();
	String descrizioneCausale = causale.getCausale();
	String codiceSoftware = ORMHelper.getSoftware();
	boolean nodoInterno = isChiamataDaNodoInterno(praticaNla.getSportelloDestinatario(), praticaNla.getSportelloMittente(), true);
	NodoNLAEnum nodo = getTipoNodo(praticaNla.getSportelloMittente());
	boolean nodoSieder = NodoNLAEnum.NLA_IDNODO_SIEDER.equals(nodo);
	boolean nodoPeople = NodoNLAEnum.NLA_IDNODO_PEOPLE.equals(nodo);
	Verticalizzazioniparametri codiceCausaleDefault = null;
	if (nodoPeople) {
	    codiceCausaleDefault = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_PEOPLE,
		    WebConstants.VERTICALIZZAZIONE_PEOPLE_CODICECAUSALEONEREDEFAULT);
	}
	if (nodoSieder) {
	    codiceCausaleDefault = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_SIEDER,
		    WebConstants.VERTICALIZZAZIONE_SIEDER_CODICECAUSALEONEREDEFAULT);
	}
	String vertCausaleDefault = (codiceCausaleDefault != null && StringUtils.isNotBlank(codiceCausaleDefault.getValore()))
		? codiceCausaleDefault.getValore().trim()
		: null;
	Integer idCausaleDefault = (StringUtils.isNotEmpty(vertCausaleDefault)) ? Integer.parseInt(vertCausaleDefault) : null;
	DecodificaCausaleOnereRequest request = new DecodificaCausaleOnereRequest(idCausale, descrizioneCausale, codiceSoftware, nodoInterno,
		nodoSieder, nodoPeople, idCausaleDefault);
	return this.tipicausalioneriService.decodeCausaleonere(request);
    }

    private void populateIstanzaAltridati(InserimentoPraticaNLARequest praticaNla, Istanze istanza) {

	SportelloType sportelloMittente = praticaNla.getSportelloMittente();
	// boolean isDomandaAreariservata = checkSportello(sportelloMittente, NodoNLAEnum.NLA_IDNODO_AREARISERVATA)
	// se è area riservata cerco nel campo altri dati il record con nome:
	// 1 - AREARISERVATA_EVENTI --> creerò N oggetti istanza eventi con i campi:
	//	a- data 	: data dell'istanza
	//	b- descrizione 	: descrizione 
	//	c- categoriebase: codice di altri dati	
	List<ParametroType> parametroTypes = praticaNla.getDettaglioPratica().getAltriDati();
	Istanzeeventi istanzeeventi = null;
	Set<Istanzeeventi> listIstanzeeventis = istanza.getIstanzeeventis();
	if (listIstanzeeventis == null) {
	    listIstanzeeventis = new HashSet<Istanzeeventi>();
	}
	for (ParametroType parametroType : parametroTypes) {
	    if (ALTRI_DATI_AREARISERVATA_EVENTI.equals(parametroType.getNome())) {
		List<ValoreParametroType> valoreParametroTypes = parametroType.getValore();
		for (ValoreParametroType valoreParametroType : valoreParametroTypes) {
		    istanzeeventi = new Istanzeeventi();
		    Categorieeventibase categorieeventibase = categorieeventibaseService.findById(valoreParametroType.getCodice());
		    istanzeeventi.setCategorieeventibase(categorieeventibase);
		    istanzeeventi.setDescrizione(valoreParametroType.getDescrizione());
		    istanzeeventi.setFlagLetto(false);
		    try {
			istanzeeventi.setData(praticaNla.getDettaglioPratica().getDataPratica().toGregorianCalendar().getTime());
		    } catch (Exception e) {
			log.error("populateData(): La data della pratica non è nel formato corretto.");
			throw new RuntimeException("Errore, la data della pratica non è nel formato corretto.");
		    }
		    listIstanzeeventis.add(istanzeeventi);
		}
	    }
	    if (StringUtils.contains(parametroType.getNome(), ISTANZA_METADATO)) {
		List<ValoreParametroType> valoreParametroTypes = parametroType.getValore();
		for (ValoreParametroType valoreParametroType : valoreParametroTypes) {
		    IstanzeMetadati istanzeMetadati = new IstanzeMetadati();
		    IstanzeMetadatiId id = new IstanzeMetadatiId(ORMHelper.getIdcomune(), istanza.getId().getCodice(),
			    StringUtils.substringAfterLast(parametroType.getNome(), "ISTANZA_METADATO_"));
		    istanzeMetadati.setId(id);
		    istanzeMetadati.setValore(valoreParametroType.getDescrizione());
		    istanza.getIstanzeMetadati().add(istanzeMetadati);
		}
	    }
	    if (parametroType.getNome().equals(ASSERZIONE_SAML_AUTENTICAZIONE)) {
		Verticalizzazioniparametri tsd = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
			WebConstants.VERTICALIZZAZIONE_STC_INSERISCI_FILE_ASSERZIONE_SAML);
		if (tsd != null && StringUtils.defaultString(tsd.getValore(), "N").trim().equalsIgnoreCase("S")) {
		    List<ValoreParametroType> valoreParametroTypes = parametroType.getValore();
		    for (ValoreParametroType valoreParametroType : valoreParametroTypes) {
			try {
			    Oggetti oggetti = new Oggetti();
			    oggetti.setId(null);
			    oggetti.setNomefile(ASSERZIONE_SAML_AUTENTICAZIONE + ".txt");
			    oggetti.setOggetto(valoreParametroType.getDescrizione().getBytes());
			    oggettiService.insert(oggetti);
			    if (oggetti.getId() != null && oggetti.getId().getCodice() != null) {
				Date date = new Date();
				oggetti = oggettiService.findById(new PkId(oggetti.getId().getCodice()));
				Documentiistanza documentiistanza = new Documentiistanza();
				documentiistanza.setIstanza(istanza);
				documentiistanza.setOggetto(oggetti);
				documentiistanza.setData(date);
				documentiistanza.setDocumento(ASSERZIONE_SAML_AUTENTICAZIONE);
				istanza.getDocumentiistanzas().add(documentiistanza);
			    }
			} catch (Exception e) {
			    log.error("ASSERZIONE_SAML_AUTENTICAZIONE: Errore: ", e);
			}
		    }
		}
	    }
	}
	istanza.setIstanzeeventis(listIstanzeeventis);
	// Recupero del campo nomeattivita dell'oggetto istanza.Il campo viene passato nella sezione Altri dati secondo la logica
	// nome		: $ISTANZA_NOMEATTIVITA$
	// valore	: valore stringa del campo "nomeattivita"
	log.debug("Recupero del campo nomeattivita dalla sezione altri dati");
	ValoreParametroType valoreParametroType = StcUtils.getCampoDaAltriDati(praticaNla, ALTRI_DATI_DENOMINAZIONE_ATTIVITA);
	if (valoreParametroType != null) {
	    if (StringUtils.isNotBlank(valoreParametroType.getCodice())) {
		istanza.setNomeattivita(valoreParametroType.getCodice());
		log.debug("Valore nomeattivita {} recuperato", valoreParametroType.getCodice());
	    }
	}
	// RECUPERO NUMEROPROTOCOLLO_MITTENTE E DATA PROTOCOLLO MITTENTE
	// public final String ALTRI_DATI_ISTANZE_NUMEROPROTOCOLLO_MITTENTE = "$NUMERO_PROTOCOLLO_MITTENTE$";
	valoreParametroType = StcUtils.getCampoDaAltriDati(praticaNla, ALTRI_DATI_ISTANZE_NUMERO_PROTOCOLLO_MITTENTE);
	if (valoreParametroType != null) {
	    if (StringUtils.isNotBlank(valoreParametroType.getCodice())) {
		istanza.setTransientNumeroProtocolloMittente(valoreParametroType.getCodice());
		log.debug("Valore NUMEROPROTOCOLLO MITTENTE {} recuperato", valoreParametroType.getCodice());
	    }
	}
	valoreParametroType = StcUtils.getCampoDaAltriDati(praticaNla, ALTRI_DATI_ISTANZE_DATA_PROTOCOLLO_MITTENTE);
	if (valoreParametroType != null) {
	    if (StringUtils.isNotBlank(valoreParametroType.getCodice())) {
		String data = valoreParametroType.getCodice().trim();
		if (data.matches("^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](1[0-9]|2[0-9])\\d\\d$")) {
		    Date d = Utilities.parseDateString(data, false);
		    if (d != null) {
			istanza.setTransientDataProtocolloMittente(d);
			log.debug("Valore DATA PROTOCOLLO MITTENTE {} recuperato", valoreParametroType.getCodice());
		    }
		}
	    }
	}
	valoreParametroType = StcUtils.getCampoDaAltriDati(praticaNla, ALTRI_DATI_NATURA_ENDO_PRINCIPALE);
	if (valoreParametroType != null) {
	    if (StringUtils.isNotBlank(valoreParametroType.getCodice())) {
		istanza.setNaturaEndoPrincipale(valoreParametroType.getCodice().trim());
	    }
	}
    }

    private void populateProtocollo(Istanze istanza, DettaglioPraticaType dettaglioPraticaType, boolean passaProtocollo, String codiceComune,
	    SportelloType sportelloMittente, SportelloType sportelloDestinatario) {

	if (passaProtocollo) {
	    boolean copiaProtocollo = false;
	    // se verticalizzazione forza_protocollazione allora non vengono passati i riferimenti della protocollazione
	    // e di fatto causa la chiamata al servizio di protocollazione istanza
	    boolean stessoNodoEnte = this.checkIsStessoNodoStessoEnte(sportelloDestinatario, sportelloMittente);
	    if (stessoNodoEnte) {
		// Il protocollo lo passo se stesso nodo e stesso ente altrimenti mi si passa anche da REGIONE a AUA
		copiaProtocollo = true;
		// DA VERIFICARE EVENTUALI PROBLEMI CON PASSAPROT
	    }
	    // se verticalizzazione lista nodi copia protocollo contiene un nodo mittente configurato allora
	    // vengono passati i riferimenti della protocollazione. Es caso uso NODO INFOCAMERE GENOVA 
	    Verticalizzazioniparametri vpCopiaRifProto = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		    WebConstants.VERTICALIZZAZIONE_STC_LISTANODI_COPIA_PROTOCOLLO);
	    if (!copiaProtocollo && vpCopiaRifProto != null
		    && StringUtils.isNotBlank(StringUtils.defaultString(vpCopiaRifProto.getValore()).trim())) {
		String cercaIn = StringUtils.defaultString(vpCopiaRifProto.getValore()).trim();
		copiaProtocollo = Utilities.verificaPresenzaValoreIn(sportelloMittente.getIdNodo(), cercaIn);
	    }
	    Verticalizzazioniparametri vpForza = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		    WebConstants.VERTICALIZZAZIONE_STC_FORZAPROTOCOLLAZIONE);
	    if (vpForza != null && StringUtils.defaultString(vpForza.getValore(), "N").equalsIgnoreCase("S")) {
		copiaProtocollo = false;
	    }
	    if (copiaProtocollo) {
		istanza.setNumeroprotocollo(dettaglioPraticaType.getNumeroProtocolloGenerale());
		XMLGregorianCalendar dataProtocolloGen = dettaglioPraticaType.getDataProtocolloGenerale();
		if (dataProtocolloGen != null) {
		    try {
			istanza.setDataprotocollo(dataProtocolloGen.toGregorianCalendar().getTime());
		    } catch (Exception e) {
			log.error("populateProtocollo(): data protocollo non valida.");
			throw new RuntimeException("Errore nell'xml DettaglioPraticaType, il valore del tag DataProtocollo non è corretto.");
		    }
		}
		String idprotocollo = "";
		ValoreParametroType vpidp = Utilities.getCampoDaAltriDati(dettaglioPraticaType.getAltriDati(),
			ALTRI_DATI_ISTANZE_MOVIMENTI_FKIDPROTOCOLLO);
		if (vpidp != null && StringUtils.isNotBlank(vpidp.getCodice())) {
		    idprotocollo = vpidp.getCodice();
		    istanza.setFkidprotocollo(idprotocollo);
		}
	    }
	}
    }

    private void populateAnagrafe(Anagrafe anagrafe, AnagrafeType anagrafeType, boolean isIntermediario, InserimentoPraticaNLARequest praticaNla) {

	if (anagrafe != null) {
	    if (anagrafeType != null) {
		if (anagrafeType.getPersonaFisica() == null && anagrafeType.getPersonaGiuridica() == null) {
		    log.error("populateAnagrafe: {}", "Il tipo AnagrafeType.PersonaFisica e AnagrafeType.PersonaGiuridica sono entrambe nulli");
		    throw new RuntimeException("Il tipo AnagrafeType.PersonaFisica e AnagrafeType.PersonaGiuridica sono entrambe nulli");
		}
		PersonaFisicaType personaFisicaType = anagrafeType.getPersonaFisica();
		if (isIntermediario) {
		    anagrafe.setTipologia(-1);
		}
		//2011-11-29 BOCCI: SE NON E' TECNICO NON SETTO LA TIPOLOGIA (LA LASCIO A NULL) ALTRIMENTI PUO' SOVRASCRIVERE LA PROPRIETA' TIPOLOGIA
		// 		    DURANTE LA CUSTOMBINDDOMAINOBJECT	
		//		else {
		//		    anagrafe.setTipologia(0);
		//		}
		// END 2011-11-29 BOCCI: SE NON E' TECNICO NON SETTO LA TIPOLOGIA ALTRIMENTI PUO' SOVRASCRIVERE LA PROPRIETA' TIPOLOGIA
		if (personaFisicaType != null) {
		    anagrafe.setTipoanagrafe(WebConstants.PERSONA_FISICA);
		    anagrafe.setCodicefiscale(personaFisicaType.getCodiceFiscale());
		    anagrafe.setNominativo(personaFisicaType.getCognome());
		    anagrafe.setNome(personaFisicaType.getNome());
		    anagrafe.setEmail(personaFisicaType.getEmail());
		    anagrafe.setPec(personaFisicaType.getPec());
		    anagrafe.setTelefono(personaFisicaType.getTelefono());
		    anagrafe.setTelefonocellulare(personaFisicaType.getTelefonoCellulare());
		    XMLGregorianCalendar dataNascita = personaFisicaType.getDataNascita();
		    if (dataNascita != null) {
			anagrafe.setDatanascita(dataNascita.toGregorianCalendar().getTime());
		    }
		    anagrafe.getTitolo().setTitolo(personaFisicaType.getTitolo());
		    anagrafe.setSesso(personaFisicaType.getSesso());
		    LocalizzazioneType residenza = personaFisicaType.getResidenza();
		    if (residenza != null) {
			anagrafe.setCap(residenza.getCap());
			anagrafe.setIndirizzo(getIndirizzo(residenza));
			anagrafe.setCitta(residenza.getLocalita());
			if (StringUtils.isNotBlank(residenza.getProvincia())) {
			    if (residenza.getProvincia().length() == 2) {
				anagrafe.setProvincia(residenza.getProvincia());
			    }
			}
			ComuneType _comuneResidenza = residenza.getComune();
			if (_comuneResidenza != null) {
			    anagrafe.getComuneResidenza().setCf(_comuneResidenza.getCodiceCatastale());
			    anagrafe.getComuneResidenza().setCodiceistat(_comuneResidenza.getCodiceIstat());
			    anagrafe.getComuneResidenza().setComune(_comuneResidenza.getComune());
			}
		    }
		    /// CORRISPONDENZA
		    LocalizzazioneType corrispondenza = personaFisicaType.getCorrispondenza();
		    if (corrispondenza != null) {
			anagrafe.setCapcorrispondenza(corrispondenza.getCap());
			anagrafe.setIndirizzocorrispondenza(getIndirizzo(corrispondenza));
			anagrafe.setCittacorrispondenza(corrispondenza.getLocalita());
			if (StringUtils.isNotBlank(corrispondenza.getProvincia())) {
			    if (corrispondenza.getProvincia().length() == 2) {
				anagrafe.setProvinciacorrispondenza(corrispondenza.getProvincia());
			    }
			}
			ComuneType _comunecorrispondenza = corrispondenza.getComune();
			if (_comunecorrispondenza != null) {
			    anagrafe.getComunecorrispondenza().setCf(_comunecorrispondenza.getCodiceCatastale());
			    anagrafe.getComunecorrispondenza().setCodiceistat(_comunecorrispondenza.getCodiceIstat());
			    anagrafe.getComunecorrispondenza().setComune(_comunecorrispondenza.getComune());
			}
		    }
		    // CORRISPONDENZA    
		    ComuneType _comuneNascita = personaFisicaType.getComuneNascita();
		    if (_comuneNascita != null) {
			anagrafe.getComuneNascita().setCf(_comuneNascita.getCodiceCatastale());
			anagrafe.getComuneNascita().setCodiceistat(_comuneNascita.getCodiceIstat());
			anagrafe.getComuneNascita().setComune(_comuneNascita.getComune());
		    }
		    CittadinanzaType _cittadinanza = personaFisicaType.getCittadinanza();
		    if (_cittadinanza != null) {
			try {
			    Integer id = Integer.valueOf(_cittadinanza.getId());
			    anagrafe.getCittadinanza().setCodice(id);
			} catch (NumberFormatException e) {
			    log.warn("populateAnagrafe: PersonaFisicaType#CittadinanzaType#id non è un numero.");
			}
			if (StringUtils.isNotBlank(_cittadinanza.getDescrizione())) {
			    anagrafe.getCittadinanza().setCittadinanza(_cittadinanza.getDescrizione());
			}
			if (StringUtils.isNotBlank(_cittadinanza.getCodiceCatastale())) {
			    anagrafe.getCittadinanza().setCf(_cittadinanza.getCodiceCatastale());
			}
		    }
		    if (personaFisicaType.getDatiIscrizioneAlbo() != null) {
			DatiIscrizioneAlboType s = personaFisicaType.getDatiIscrizioneAlbo();
			anagrafe.setNumeroelencopro(s.getNumeroIscrizione());
			if (StringUtils.isNotBlank(s.getSiglaProvincia())) {
			    if (s.getSiglaProvincia().length() <= 2) {
				anagrafe.setProvinciaelencopro(s.getSiglaProvincia());
			    }
			}
			if (s.getTipoOrdineProfessionisti() != null) {
			    boolean isEp = false;
			    Elenchiprofessionalibase elenchiprofessionalibase = new Elenchiprofessionalibase();
			    if (StringUtils.isNotBlank(s.getTipoOrdineProfessionisti().getCodice())) {
				if (Utilities.isInteger(s.getTipoOrdineProfessionisti().getCodice())) {
				    isEp = true;
				    elenchiprofessionalibase.setId(Integer.parseInt(s.getTipoOrdineProfessionisti().getCodice()));
				}
			    }
			    if (StringUtils.isNotBlank(s.getTipoOrdineProfessionisti().getDescrizione())) {
				isEp = true;
				elenchiprofessionalibase.setEpDescrizione(s.getTipoOrdineProfessionisti().getDescrizione());
			    }
			    if (isEp) {
				anagrafe.setElenchiprofessionalibase(elenchiprofessionalibase);
			    }
			}
		    }
		} else {
		    PersonaGiuridicaType personaGiuridicaType = anagrafeType.getPersonaGiuridica();
		    anagrafe.setTipoanagrafe(WebConstants.PERSONA_GIURIDICA);
		    anagrafe.setFax(personaGiuridicaType.getFax());
		    anagrafe.setTelefono(personaGiuridicaType.getTelefono());
		    anagrafe.setTelefonocellulare(personaGiuridicaType.getTelefonoCellulare());
		    anagrafe.setNominativo(personaGiuridicaType.getRagioneSociale());
		    String partitaIva = personaGiuridicaType.getPartitaIva();
		    String codiceFiscale = personaGiuridicaType.getCodiceFiscale();
		    if (StringUtils.isBlank(codiceFiscale) && StringUtils.isBlank(partitaIva)) {
			log.error(
				"populateAnagrafe(): Errore nell'inserimento della persona giuridica[{}]: non è presente né codice fiscale né partita iva.",
				personaGiuridicaType.getRagioneSociale());
			throw new RuntimeException("Errore nell'inserimento della persona giuridica[" + personaGiuridicaType.getRagioneSociale() +
						   "]: non è presente né codice fiscale né partita iva.");
		    }
		    codiceFiscale = checkPiva(codiceFiscale, "");
		    anagrafe.setCodicefiscale(codiceFiscale);
		    partitaIva = checkPiva(partitaIva, codiceFiscale);
		    anagrafe.setPartitaiva(partitaIva);
		    anagrafe.setEmail(personaGiuridicaType.getEmail());
		    anagrafe.setPec(personaGiuridicaType.getPec());
		    // gestione campo data validità
		    if (personaGiuridicaType.getDataInizioAttivita() != null) {
			XMLGregorianCalendar _dataInizioAttivita = personaGiuridicaType.getDataInizioAttivita();
			Date dataInizioAttivita = Utilities.getDate(_dataInizioAttivita);
			anagrafe.setDataInizioAttivita(dataInizioAttivita);
		    }
		    gestFormaGiuridica(anagrafe, personaGiuridicaType, praticaNla);
		    LocalizzazioneType indSedeLegale = personaGiuridicaType.getSedeLegale();
		    if (indSedeLegale != null) {
			anagrafe.setCap(indSedeLegale.getCap());
			anagrafe.setIndirizzo(getIndirizzo(indSedeLegale));
			anagrafe.setCitta(indSedeLegale.getLocalita());
			if (StringUtils.isNotBlank(indSedeLegale.getProvincia())) {
			    if (indSedeLegale.getProvincia().length() == 2) {
				anagrafe.setProvincia(indSedeLegale.getProvincia());
			    }
			}
			ComuneType _comuneSedeLegale = indSedeLegale.getComune();
			if (_comuneSedeLegale != null) {
			    anagrafe.getComuneResidenza().setCf(_comuneSedeLegale.getCodiceCatastale());
			    anagrafe.getComuneResidenza().setCodiceistat(_comuneSedeLegale.getCodiceIstat());
			    anagrafe.getComuneResidenza().setComune(_comuneSedeLegale.getComune());
			}
		    }
		    LocalizzazioneType indCorrisp = personaGiuridicaType.getIndirizzoCorrispondenza();
		    if (indCorrisp != null) {
			anagrafe.setCapcorrispondenza(indCorrisp.getCap());
			anagrafe.setIndirizzocorrispondenza(getIndirizzo(indCorrisp));
			anagrafe.setCittacorrispondenza(indCorrisp.getLocalita());
			if (StringUtils.isNotBlank(indCorrisp.getProvincia())) {
			    if (indCorrisp.getProvincia().length() == 2) {
				anagrafe.setProvinciacorrispondenza(indCorrisp.getProvincia());
			    }
			}
			ComuneType _comuneCorriisp = indCorrisp.getComune();
			if (_comuneCorriisp != null) {
			    anagrafe.getComunecorrispondenza().setCf(_comuneCorriisp.getCodiceCatastale());
			    anagrafe.getComunecorrispondenza().setCodiceistat(_comuneCorriisp.getCodiceIstat());
			    anagrafe.getComunecorrispondenza().setComune(_comuneCorriisp.getComune());
			}
		    }
		    IscrizioneRegistroType cciaa = personaGiuridicaType.getIscrizioneCCIAA();
		    if (cciaa != null) {
			ComuneType _comuneCCIAA = cciaa.getComune();
			if (_comuneCCIAA != null) {
			    anagrafe.getComunecomregditte().setCf(_comuneCCIAA.getCodiceCatastale());
			    anagrafe.getComunecomregditte().setCodiceistat(_comuneCCIAA.getCodiceIstat());
			    anagrafe.getComunecomregditte().setComune(_comuneCCIAA.getComune());
			    if (personaGiuridicaType.getDataInizioAttivita() != null) {
				XMLGregorianCalendar _dataInizioAttivita = personaGiuridicaType.getDataInizioAttivita();
				Date dataInizioAttivita = Utilities.getDate(_dataInizioAttivita);
				anagrafe.setDataInizioAttivita(dataInizioAttivita);
			    }
			    //			    XMLGregorianCalendar dataCCIAA = cciaa.getData();
			    //			    if (dataCCIAA != null) {
			    //				anagrafe.setDataregditte(dataCCIAA.toGregorianCalendar().getTime());
			    //			    }
			}
			if (cciaa.getData() != null) {
			    if (cciaa.getData() != null) {
				XMLGregorianCalendar _dataCciaa = cciaa.getData();
				Date dataCciaa = Utilities.getDate(_dataCciaa);
				anagrafe.setDataregditte(dataCciaa);
			    }
			}
			if (StringUtils.isNotBlank(cciaa.getNumero())) {
			    anagrafe.setRegditte(cciaa.getNumero());
			}
		    }
		    RegistroREAType rea = personaGiuridicaType.getIscrizioneREA();
		    if (rea != null) {
			XMLGregorianCalendar dataRea = rea.getData();
			if (dataRea != null) {
			    anagrafe.setDataiscrrea(dataRea.toGregorianCalendar().getTime());
			}
			String numRea = rea.getNumero();
			if (StringUtils.isNotBlank(numRea)) {
			    anagrafe.setNumiscrrea(numRea);
			}
			String provRea = rea.getSiglaProvincia();
			if (StringUtils.isNotBlank(provRea)) {
			    if (provRea.length() == 2) {
				anagrafe.setProvinciarea(provRea.toUpperCase());
			    }
			}
		    }
		    if (personaGiuridicaType.getDatiIscrizioneAlbo() != null) {
			DatiIscrizioneAlboType s = personaGiuridicaType.getDatiIscrizioneAlbo();
			anagrafe.setNumeroelencopro(s.getNumeroIscrizione());
			if (StringUtils.isNotBlank(s.getSiglaProvincia())) {
			    if (s.getSiglaProvincia().length() <= 2) {
				anagrafe.setProvinciaelencopro(s.getSiglaProvincia());
			    }
			}
			if (s.getTipoOrdineProfessionisti() != null) {
			    boolean isEp = false;
			    Elenchiprofessionalibase elenchiprofessionalibase = new Elenchiprofessionalibase();
			    if (StringUtils.isNotBlank(s.getTipoOrdineProfessionisti().getCodice())) {
				if (Utilities.isInteger(s.getTipoOrdineProfessionisti().getCodice())) {
				    isEp = true;
				    elenchiprofessionalibase.setId(Integer.parseInt(s.getTipoOrdineProfessionisti().getCodice()));
				}
			    }
			    if (StringUtils.isNotBlank(s.getTipoOrdineProfessionisti().getDescrizione())) {
				isEp = true;
				elenchiprofessionalibase.setEpDescrizione(s.getTipoOrdineProfessionisti().getDescrizione());
			    }
			    if (isEp) {
				anagrafe.setElenchiprofessionalibase(elenchiprofessionalibase);
			    }
			}
		    }
		    if (personaGiuridicaType.getDatiInps() != null) {
			DatiInpsType di = personaGiuridicaType.getDatiInps();
			if (StringUtils.isNotBlank(di.getNumero())) {
			    anagrafe.setInpsMatricola(di.getNumero());
			}
			if (di.getSedeIscrizione() != null) {
			    if (StringUtils.isNotBlank(di.getSedeIscrizione().getCodice())
				    || StringUtils.isNotBlank(di.getSedeIscrizione().getDescrizione())) {
				Elencoinpsbase sedeInps = new Elencoinpsbase();
				sedeInps.setCodice(di.getSedeIscrizione().getCodice());
				sedeInps.setDescrizione(di.getSedeIscrizione().getDescrizione());
				anagrafe.setSedeInps(sedeInps);
			    }
			}
		    }
		    if (personaGiuridicaType.getDatiInail() != null) {
			DatiInailType di = personaGiuridicaType.getDatiInail();
			if (StringUtils.isNotBlank(di.getNumero())) {
			    anagrafe.setInailMatricola(di.getNumero());
			}
			if (di.getSedeIscrizione() != null) {
			    if (StringUtils.isNotBlank(di.getSedeIscrizione().getCodice())
				    || StringUtils.isNotBlank(di.getSedeIscrizione().getDescrizione())) {
				Elencoinailbase sedeInail = new Elencoinailbase();
				sedeInail.setCodice(di.getSedeIscrizione().getCodice());
				sedeInail.setDescrizione(di.getSedeIscrizione().getDescrizione());
				anagrafe.setSedeInail(sedeInail);
			    }
			}
		    }
		    if (personaGiuridicaType.getDatiCassaEdile() != null) {
			DatiCassaEdileType di = personaGiuridicaType.getDatiCassaEdile();
			if (StringUtils.isNotBlank(di.getNumero())) {
			    anagrafe.setCassaedileMatricola(di.getNumero());
			}
			if (di.getCassaEdile() != null) {
			    if (StringUtils.isNotBlank(di.getCassaEdile().getCodice())
				    || StringUtils.isNotBlank(di.getCassaEdile().getDescrizione())) {
				Elencocassaedilebase sedeCassaEdile = new Elencocassaedilebase();
				sedeCassaEdile.setCodice(di.getCassaEdile().getCodice());
				sedeCassaEdile.setDescrizione(di.getCassaEdile().getDescrizione());
				anagrafe.setSedeCassaedile(sedeCassaEdile);
			    }
			}
		    }
		}
	    }
	}
    }

    private void gestFormaGiuridica(Anagrafe anagrafe, PersonaGiuridicaType personaGiuridicaType, InserimentoPraticaNLARequest praticaNla) {

	if (StringUtils.isNotBlank(personaGiuridicaType.getNaturaGiuridica())) {
	    if (anagrafe == null) {
		log.warn("gestFormaGiuridica# Bind per la formagiuridica {} anagrafe nulla", personaGiuridicaType.getNaturaGiuridica());
		return;
	    }
	    if (log.isDebugEnabled()) {
		log.debug("gestFormaGiuridica# cerco di effettuare il bind per la formagiuridica {} di anagrafe {}",
			personaGiuridicaType.getNaturaGiuridica(), anagrafe.getNominativo());
	    }
	    if (Utilities.isInteger(personaGiuridicaType.getNaturaGiuridica().trim())) {
		if (log.isDebugEnabled()) {
		    log.debug("gestFormaGiuridica# il codice è intero cerco di recuperare le informazioni dalla base dati");
		}
		Integer codiceNaturaGiuridica = null;
		//.. BOCCI: SE LAPRATICA ARRIVA DA AREA RISERVATA O NLABACKOFFICE QUESTI MI PASSANO IL CODICE NUMERICO
		//.. CONTROLLO SE POSSO CONVERTIRLO IN NUMERICO E LO CERCO TRA LE FORMEGIURIDICHE
		codiceNaturaGiuridica = Integer.valueOf(personaGiuridicaType.getNaturaGiuridica().trim());
		if (codiceNaturaGiuridica != null) {
		    if (isChiamataDaNodoInterno(praticaNla.getSportelloDestinatario(), praticaNla.getSportelloMittente(), false)) {
			Formegiuridiche fg = formegiuridicheService.findById(new PkId(codiceNaturaGiuridica));
			if (fg != null) {
			    anagrafe.setFormagiuridica(fg);
			    return;
			} else {
			    log.warn(getClass() +
				     "#gestFormaGiuridica: Attenzione!!! Non è stato possibile collegare la formagiuridica con codice {} - {} alla anagrafe {}",
				    new Object[] { codiceNaturaGiuridica.intValue(), ORMHelper.getIdcomune(), anagrafe.getNominativo() });
			}
		    }
		}
	    }
	    if (log.isDebugEnabled()) {
		log.debug(
			"gestFormaGiuridica# cerco di recuperare la formagiuridica dalla mappatura FORMEGIURIDICHE.CODICECCIAA formegiuridicheService.findByCodiceRiFormegiuridiche");
	    }
	    // CERCO di trovare la forma giuridica dal campo Formegiuridiche.CODICECCIAA in join con la tabella
	    // RI_FORMEGIURIDICHE
	    Formegiuridiche fg = formegiuridicheService.findByCodiceRiFormegiuridiche(personaGiuridicaType.getNaturaGiuridica().trim());
	    if (fg != null) {
		anagrafe.setFormagiuridica(fg);
		if (log.isDebugEnabled()) {
		    log.debug("gestFormaGiuridica# trovata ed associata");
		}
	    } else {
		// NON E' MAPPATO CON RI_FORMEGIURIDICHE 
		// METTO QUELLO CHE HO TROVATO E VEDO SE LA CUSTOM BIND DI ANAGRAFE RISOLVE
		anagrafe.getFormagiuridica().setFormagiuridica(personaGiuridicaType.getNaturaGiuridica());
		log.warn(getClass() + "#gestFormaGiuridica: Attenzione!!! Non è stato possibile collegare la formagiuridica con codice {}-{} setto " +
			 "il valore anagrafe.getFormagiuridica().setFormagiuridica(personaGiuridicaType.getNaturaGiuridica()); ",
			personaGiuridicaType.getNaturaGiuridica().trim(), ORMHelper.getIdcomune());
	    }
	}
    }

    /**
     * BOCCI 2012-01-11 ANOMALIA ANAGRAFICHE AIDA (STC).<br/>
     * Da STC arrivano anagrafiche con PIVA "00000000" aziende che non hanno ancora la partita iva.<br />
     * La funzione controlla che la stringa passata contenga solamente caratteri 0 (zero) nel caso effettua una replace
     * con la stringaAlternativa che di solito è il codice fiscale.
     * 
     * @param piva
     * @param codiceFiscale
     * @return
     */
    private static String checkPiva(String piva, String stringaAlternativa) {

	if (StringUtils.isNotBlank(piva)) {
	    String result = new String(piva);
	    piva = piva.trim();
	    String pivaDaControllare = new String(piva);
	    pivaDaControllare = pivaDaControllare.replaceAll("0", "");
	    if (StringUtils.isBlank(pivaDaControllare)) {
		result = stringaAlternativa;
	    }
	    return result;
	} else {
	    return piva;
	}
    }

    private void populateInQualitaDi(Istanze istanza, InserimentoPraticaNLARequest inserimentoPraticaNLARequest) {

	DettaglioPraticaType dettaglioPraticaType = inserimentoPraticaNLARequest.getDettaglioPratica();
	if (dettaglioPraticaType.getRichiedente() != null) {
	    RuoloType ruolo = dettaglioPraticaType.getRichiedente().getRuolo();
	    if (ruolo != null) {
		try {
		    Tipisoggetto inQualitaDi = decodificaTipoSoggetto(ruolo, ORMHelper.getSoftware(), istanza, false, inserimentoPraticaNLARequest);
		    if (inQualitaDi != null) {
			istanza.setTipisoggetto(inQualitaDi);
			if (BooleanUtils.isTrue(inQualitaDi.getFlgSpecificadescrizione())) {
			    String descrizioneRuolo = StringUtils.defaultString(ruolo.getRuolo());
			    // SU IDRUOLO CI VA LA DESCRIZIONE DI ALTRO SPECIFICARE 
			    if (descrizioneRuolo.equalsIgnoreCase(inQualitaDi.getTiposoggetto())
				    && !Utilities.isInteger(StringUtils.defaultString(ruolo.getIdRuolo(), "0"))) {
				istanza.setDescrsoggetto(ruolo.getIdRuolo());
			    } else {
				if (StringUtils.isNotBlank(ruolo.getRuolo())) {
				    istanza.setDescrsoggetto(ruolo.getRuolo());
				} else {
				    istanza.setDescrsoggetto(inQualitaDi.getTiposoggetto());
				}
			    }
			}
		    }
		} catch (NumberFormatException e) {
		    log.error("populateInQualitaDi(): Il valore del tag codice del tag RuoloType non è nel formato corretto.");
		    throw new RuntimeException("Errore nell'xml RuoloType, il valore del tag codice non è corretto: '" + ruolo.getIdRuolo() + "'");
		}
	    }
	}
    }

    /**
     * La funzione cerca di recuperare un oggetto Tipisoggetto a partire da quanto specificato per la proprietà
     * RuoloType. <br />
     * Nel caso che la domanda provenga da un nodo INTERNO (NodoNLAEnum.NLA_IDNODO_AREARISERVATA,
     * NodoNLAEnum.NLA_IDNODO, NodoNLAEnum.NLA_IDNODO_PROTOCOLLOSIGEPRO) allora il bind verso tipisoggetto è delegato
     * alla ricerca in {@link TipisoggettoService#findByRuoloType(RuoloType, String)} .<br />
     * Negli altri casi la ricerca viene effettuata in TIPISOGGETTOPEOPLE
     * {@link TipisoggettopeopleService#findByRuoloType(RuoloType, String)}.<br />
     * Nel caso che isSoggettiCollegati sia true e non venga trovato un oggetto si tenta di recuperarlo da
     * verticalizzazioni (modulo=STC, parametro=TIPO_SOGGETTO_DEFAULT) e nel caso non sia stata impostata o ritorni null
     * viene rilanciata un'eccezione in quanto la tipologia soggetto è obbligatorio. <br />
     * Nel caso in cui tramite le informazioni passate non si riesca a recuperare una tipologia di soggetto vengono
     * inseriti in eventi istanza delle notifiche.
     * 
     * @param ruolo
     * @param codiceSoftware
     * @param istanza
     * @param isSoggettiCollegati
     * @param inserimentoPraticaNLARequest
     * @return
     */
    private Tipisoggetto decodificaTipoSoggetto(RuoloType ruolo, String codiceSoftware, Istanze istanza, boolean isSoggettiCollegati,
	    InserimentoPraticaNLARequest inserimentoPraticaNLARequest) {

	SportelloType mitt = inserimentoPraticaNLARequest.getSportelloMittente();
	Tipisoggetto tipisoggetto = null;
	log.debug("decodificaTipoSoggetto: prima di checkSportello(isDomandaPeople)");
	boolean isDomandaAreariservata = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO_AREARISERVATA);
	boolean isDomandaBackend = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO);
	boolean isDomandaProtocollo = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO_PROTOCOLLOSIGEPRO);
	try {
	    if (isDomandaAreariservata || isDomandaBackend || isDomandaProtocollo) {
		log.debug(
			"decodificaTipoSoggetto: isDomandaAreariservata || isDomandaBackend || isDomandaProtocollo, cerco il ruolo in TipisoggettoService");
		tipisoggetto = tipisoggettoService.findByRuoloType(ruolo, codiceSoftware);
	    } else {
		log.debug("decodificaTipoSoggetto: Non è domanda nodi interni cerco il ruolo in TipisoggettopeopleService");
		// IN CASO DI NODI NON INTERNI FACCIO IL BIND CON TABELLA TIPISOGGETTOPEOPLE
		try {
		    tipisoggetto = tipisoggettopeopleService.findByRuoloType(ruolo, codiceSoftware);
		} catch (BusinessValidationException e) {
		    log.error("decodificaTipoSoggetto-> Non è domanda nodi interni: errore" + e.getMessage());
		    // tipisoggetto = gestisciErroreBindTipisoggetto(istanza, isSoggettiCollegati, tipisoggetto, e);
		    // IN CASO DI BIND NON TROVATO CON TABELLA TIPISOGGETTO
		    log.warn("decodificaTipoSoggetto-> Non è domanda nodi interni: cerco il ruolo in TipisoggettoService");
		    tipisoggetto = tipisoggettoService.findByRuoloType(ruolo, codiceSoftware);
		}
	    }
	} catch (BusinessValidationException e) {
	    log.error("decodificaTipoSoggetto: " + e.getMessage());
	    tipisoggetto = gestisciErroreBindTipisoggetto(istanza, isSoggettiCollegati, tipisoggetto, e);
	}
	return tipisoggetto;
    }

    /**
     * Gestisce l'errore dei metodi di bind per la tipologia soggetti.
     * 
     * @param istanza
     * @param isSoggettiCollegati
     * @param tipisoggetto
     * @param e
     */
    private Tipisoggetto gestisciErroreBindTipisoggetto(Istanze istanza, boolean isSoggettiCollegati, Tipisoggetto tipisoggetto,
	    BusinessValidationException e) {

	Set<Istanzeeventi> eventi = istanza.getIstanzeeventis();
	if (eventi == null) {
	    eventi = new HashSet<Istanzeeventi>();
	}
	Istanzeeventi evento = new Istanzeeventi();
	evento.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_STC_IP);
	evento.setIstanze(istanza);
	if (isSoggettiCollegati) {
	    if (tipisoggetto == null) {
		tipisoggetto = populatetipisoggettoFromVerticalizzazione();
		String descrizione = "Errore nel recupero dell'informazione \"Tipo soggetto\" dei soggetti collegati: " + e.getMessage();
		if (tipisoggetto == null) {
		    descrizione += "Non e' stato inoltre possibile inserire una tipologia soggetto predefinita perché non impostata in Verticalizzazioni (modulo=STC, parametro=TIPO_SOGGETTO_DEFAULT)";
		    throw new RuntimeException(descrizione);
		}
		evento.setDescrizione(descrizione);
	    }
	} else {
	    // INSERISCO EVENTI ISTANZA
	    evento.setDescrizione("Errore nel recupero dell'informazione \"in Qualita' di\": " + e.getMessage());
	}
	eventi.add(evento);
	istanza.setIstanzeeventis(eventi);
	return tipisoggetto;
    }

    private Tipisoggetto populatetipisoggettoFromVerticalizzazione() {

	Integer codiceTipoSoggetto = null;
	Tipisoggetto ts = null;
	// usa il codice che si trova nella verticalizzazione STC-->TIPO_SOGGETTO_DEFAULT
	// LO CERCO NEL PARAMETRO STC.TIPO_SOGGETTO_DEFAULT CONFIGURATO NELLA VERTICALIZZAZIONE
	Verticalizzazioniparametri tsd = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		WebConstants.VERTICALIZZAZIONE_STC_TIPO_SOGGETTO_DEFAULT);
	if (tsd != null) {
	    if (StringUtils.isNotBlank(tsd.getValore())) {
		String codiceTipoSoggettoDefault = tsd.getValore().trim();
		try {
		    codiceTipoSoggetto = Integer.parseInt(codiceTipoSoggettoDefault);
		    ts = tipisoggettoService.findById(new PkId(codiceTipoSoggetto));
		    if (ts != null) {
			return ts;
		    }
		} catch (NumberFormatException e) {
		    log.error("findByRuoloType: VERTICALIZZAZIONE_STC_TIPO_SOGGETTO_DEFAULT = '{}' non è un numero valido",
			    codiceTipoSoggettoDefault);
		}
	    }
	}
	return null;
    }

    private void populateAzienda(Istanze istanza, PersonaGiuridicaType azienda, InserimentoPraticaNLARequest praticaNLA) {

	if (azienda != null) {
	    Anagrafe titolarelegale = new Anagrafe();
	    AnagrafeType _titolarelegale = new AnagrafeType();
	    _titolarelegale.setPersonaGiuridica(azienda);
	    populateAnagrafe(titolarelegale, _titolarelegale, false, praticaNLA);
	    istanza.setTitolarelegale(titolarelegale);
	}
    }

    private boolean checkPecProtocolloSportello(SportelloType sportello, String suffixSportello) {

	return StringUtils.defaultString(sportello.getIdSportello()).endsWith(suffixSportello);
    }

    // TODO FIXME C'E' UN METODO UGUALE IN NLA_WS
    private boolean checkSportelloPEC_O_PROTO_Client(SportelloType sportello, boolean isThrowException) {

	if (sportello != null) {
	    if (StringUtils.defaultString(sportello.getIdSportello()).endsWith(WebConstants.PEC_CLIENT_IDSPORTELLO_SUFFIX)) {
		if (isThrowException) {
		    throw new NotImplementedException("metodo non implementato");
		}
		return true;
	    } else if (StringUtils.defaultString(sportello.getIdSportello()).endsWith(WebConstants.AZIONI_PROTOCOLLO_CLIENT_IDSPORTELLO_SUFFIX)) {
		if (isThrowException) {
		    throw new NotImplementedException("metodo non implementato");
		}
		return true;
	    }
	}
	return false;
    }

    private Integer populateIntervento(InserimentoPraticaNLARequest request, Istanze istanza) {

	log.debug("populateIntervento: inizio");
	//(Utilizzata come variabile di ritorno per decidere l'evento da associare alla pratica)
	// Questa funzionalità è utilizzata solo per la ricerca : Individuazione tramite procedimenti.
	// Negli altri casi:
	//	1- Individuazione tramite settori/operazioni
	//	2- Individuazione tramite href
	// tra tutti quelli trovati viene scelto quello con "sc_codice" più grande.
	Integer numeroInterventiTrovati = 0;
	// Variabile che contiene il codice intervento che troveremo e setteremo all'istanza
	String codiceIntervento = "";
	SportelloType mitt = request.getSportelloMittente();
	SportelloType dest = request.getSportelloDestinatario();
	//recupero il software di interesse,Filtro comune a tutte le ricerche
	String codiceSoftware = dest.getIdSportello();
	log.debug("populateIntervento: codiceSoftware={}", codiceSoftware);
	//Verifico che la domanda venga da PEOPLE
	// Popolo l'oggetto SportelloType che conterà i valori con cui farò i confronti per decidere se la domanda in arrivo è PEOPLE
	//GESTIONE RECUPERO INTERVENTO SE LA PRATICA E' INVIATA DA UN NODO NLA AREA RISERVATA
	log.debug("populateIntervento: prima di checkSportello(isDomandaAreariservata)");
	boolean isDomandaAreariservata = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO_AREARISERVATA);
	log.debug("populateIntervento: checkSportello(isDomandaAreariservata)={}", isDomandaAreariservata);
	boolean isProtocolloSigepro = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO_PROTOCOLLOSIGEPRO);
	boolean isNlaComunicazioniInterne = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO_CART_COMINTERNE);
	boolean isNlaRFC239 = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO_RFC239);
	boolean isNlaConsole = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO_AR_CONSOLE);
	log.debug("populateIntervento: checkSportello(isProtocolloSigepro)={}", isProtocolloSigepro);
	boolean isSportelloPEC_O_PROTO_Client = checkSportelloPEC_O_PROTO_Client(mitt, false);
	log.debug("populateIntervento: checkSportelloPEC_O_PROTO_Client(mitt)={}", isSportelloPEC_O_PROTO_Client);
	if (isDomandaAreariservata || isProtocolloSigepro || isNlaComunicazioniInterne || isSportelloPEC_O_PROTO_Client || isNlaRFC239
		|| isNlaConsole) {
	    if (request.getDettaglioPratica().getIntervento() != null) {
		if (StringUtils.isNotBlank(request.getDettaglioPratica().getIntervento().getCodice())) {
		    codiceIntervento = request.getDettaglioPratica().getIntervento().getCodice();
		    if (Utilities.isInteger(codiceIntervento)) {
			log.debug("populateIntervento: codiceIntervento={}", codiceIntervento);
			istanza.getAlberoproc().getId().setCodice(Integer.parseInt(codiceIntervento));
			return 1;
		    } else {
			log.warn("Il codice intervento non è un intero {}", codiceIntervento);
			codiceIntervento = "";
		    }
		}
	    }
	}
	//GESTIONE RECUPERO INTERVENTO SE LA PRATICA E' INVIATA DA UN NODO NLA PEOPLE
	// variabile utilizzata per verificare se una delle ricerche ha trovato un 
	// intervento.
	// Appena viene trovato un intervento non è più necessario cercarne altri.
	boolean isInterventoTrovato = false;
	// variabile che indica il numero di interventi trovati
	// Il metodo controlla se la domanda che abbiamo ricevuto dall NLA è di tipo PEOPLE
	log.debug("populateIntervento: prima di checkSportello(isDomandaPeople)");
	boolean isDomandaPeople = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO_PEOPLE);
	log.debug("populateIntervento: checkSportello(isDomandaPeople)={}", isDomandaPeople);
	boolean isDomandaSieder = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO_SIEDER);
	log.debug("populateIntervento: checkSportello(isDomandaPeople)={}", isDomandaPeople);
	boolean isNlaSettoriOperazioni = false; // GENERICO NLDO NLA CHE VUOLE USARE LE MAPPATURE SETTORE OPERAZIONI USA L'ALTRO DATO NLA_SETTORE/OPERAZIONI 
	List<ParametroType> altriDati = request.getDettaglioPratica().getAltriDati();
	if (altriDati != null && !altriDati.isEmpty()) {
	    for (ParametroType parametroType : altriDati) {
		if (parametroType.getNome().equalsIgnoreCase(NLA_SETTORE)) {
		    isNlaSettoriOperazioni = true;
		    break;
		}
		if (parametroType.getNome().equalsIgnoreCase(NLA_OPERAZIONI)) {
		    isNlaSettoriOperazioni = true;
		    break;
		}
	    }
	}
	if (isDomandaPeople || isDomandaSieder || isNlaSettoriOperazioni) {
	    log.debug("Individuazione dell'intervento tramite settori/operazioni");
	    //---------------------------------------------------------------------------------------------------------------------------//
	    //-------------------------------------------Individuazione dell’intervento--------------------------------------------------//
	    //---------------------------------------Individuazione tramite settori/operazioni-------------------------------------------//
	    //------------------------------------------------------START----------------------------------------------------------------//
	    //---------------------------------------------------------------------------------------------------------------------------//
	    //---------------------------------------------------------------------------------------------------------------------------//
	    // Filtri utilizzati per effettuare la query su ALBEROPROCPEOPLER
	    // La tabella ALBEROPROCPEOPLER è in join con ALBEROPROC e permette il mapping tra i procedimenti
	    // inviati da PEOPLE e quelli presenti su SiGePro
	    String settore = "";
	    List<String> operazioni = new ArrayList<String>();
	    // Ricavo  i filtri definiti dal campo altriDati
	    // Altri dati contiene una lista di "parametroType".
	    // In questa lista saranno presenti:
	    //	1- Un parametroType con nome :PEOPLE_SETTORE
	    //  2- Da 1..n parametroType con nome :PEOPLE_OPERAZIONI
	    if (altriDati != null && !altriDati.isEmpty()) {
		// Cliclo la lista di parametroType per costruire i filtri da passare alla query.
		for (ParametroType parametroType : altriDati) {
		    // Quando trovo l'occorrenza parametroType con nome PEOPLE_SETTORE
		    // popolo la variabile il filtro settore
		    // Esistera un solo parametroType con nome PEOPLE_SETTORE
		    if (parametroType.getNome().equalsIgnoreCase(PEOPLE_SETTORE) || parametroType.getNome().equalsIgnoreCase(SIEDER_SETTORE)
			    || parametroType.getNome().equalsIgnoreCase(NLA_SETTORE)) {
			// valore di parametroType ritorna una lista di ValoreParametroType; sceglierò il primo
			//in quanto la lista sarà popolata con un solo elemento
			settore = parametroType.getValore().get(0).getCodice();
		    }
		    // Quando trovo l'occorrenza parametroType con nome PEOPLE_OPERAZIONI
		    // popolo la variabile il filtro operazioni
		    // possono esistere più parametroType con nome PEOPLE_SETTORE
		    if (parametroType.getNome().equalsIgnoreCase(PEOPLE_OPERAZIONI) || parametroType.getNome().equalsIgnoreCase(SIEDER_OPERAZIONI)
			    || parametroType.getNome().equalsIgnoreCase(NLA_OPERAZIONI)) {
			// valore di parametroType  ritorna una lista di ValoreParametroType.
			// Cliclo tutta la lista per recuperare tutti i codice degli elementi 
			// della lista per popolare il mio filtro
			List<ValoreParametroType> list = parametroType.getValore();
			for (ValoreParametroType valoreParametroType : list) {
			    operazioni.add(valoreParametroType.getCodice());
			}
		    }
		}
	    }
	    //E' il primo metodo di ricerca dell'intevento quindi non verifico se è già è stato trovato uno
	    //(isInterventoTrovato==true)
	    //Faccio la ricerca per settore,operazione e software
	    log.debug("populateIntervento: alberoprocpeopleoperService.findBySettoreAndOperazioniNlaPeople({},{},{},true)",
		    new Object[] { settore, operazioni, codiceSoftware });
	    List<Alberoprocpeopleoper> alberoprocpeopleopers = null;
	    if (operazioni != null && operazioni.size() > 0) {
		alberoprocpeopleopers = alberoprocpeopleoperService.findBySettoreAndOperazioniNlaPeople(settore, operazioni, codiceSoftware, true);
		// Controlla se sono stati trovati record nella tabella
		// se li trova mette a true "isInterventoTrovato"
		if (alberoprocpeopleopers != null && !alberoprocpeopleopers.isEmpty()) {
		    // Popolo la variabile codiceIntervento, che verra passata all'istanza,
		    // con il valore trovato su SiGePro tramite la tabella di Mapping
		    // Prendo il primo della lista ordinata per sc_codice desc
		    codiceIntervento = String.valueOf(alberoprocpeopleopers.get(0).getAlberoproc().getId().getCodice());
		    // Setto a true la variabile che mi indica che l'intervento è stato trovato e non devo continuare la ricerca
		    isInterventoTrovato = true;
		    // metto che il numero interventi trovato è 1 in quanto in per questo tipo di ricerca
		    // portò avere solo il valori 
		    //	1- 0 : non trovato
		    //	2- 1 : trovato (anche se ce ne ho più di uno prendo solo il primo)
		    numeroInterventiTrovati = 1;
		    log.debug("numeroInterventiTrovati " + String.valueOf(numeroInterventiTrovati));
		}
		//  Controllo che "isInterventoTrovato"
		// 	1- true  : non continuo la ricerca
		//	2- false : continuo la ricerca con la logica  Individuazione tramite settori/operazioni togliendo il filtro settore
		log.debug("isInterventoTrovato " + BooleanUtils.toStringTrueFalse(isInterventoTrovato));
		if (!isInterventoTrovato) {
		    log.debug("Individuazione dell’intervento tramite settori/operazioni non filtrando per settori");
		    //Faccio la ricerca per operazione e software
		    log.debug("populateIntervento: alberoprocpeopleoperService.findBySettoreAndOperazioniNlaPeople({},{},{},false)",
			    new Object[] { settore, operazioni, codiceSoftware });
		    List<Alberoprocpeopleoper> alberoprocpeopleopersWithoutSettore = alberoprocpeopleoperService
			    .findBySettoreAndOperazioniNlaPeople(settore, operazioni, codiceSoftware, false);
		    // Controlla se sono stati trovati record nella tabella
		    // se li trova mette a true "isInterventoTrovato"
		    if (alberoprocpeopleopersWithoutSettore != null && !alberoprocpeopleopersWithoutSettore.isEmpty()) {
			// Popolo la variabile codiceIntervento, che verra passata all'istanza,
			// con il valore trovato su SiGePro tramite la tabella di Mapping
			// Prendo il primo della lista ordinata per sc_codice desc
			codiceIntervento = String.valueOf(alberoprocpeopleopersWithoutSettore.get(0).getAlberoproc().getId().getCodice());
			// Setto a true la variabile che mi indica che l'intervento è stato trovato e non devo continuare la ricerca
			isInterventoTrovato = true;
			// metto che il numero interventi trovato è 1 in quanto in per questo tipo di ricerca
			// portò avere solo il valori 
			//	1- 0 : non trovato
			//	2- 1 : trovato (anche se ce ne ho più di uno prendo solo il primo)
			numeroInterventiTrovati = 1;
			log.debug("Numero interventi " + String.valueOf(numeroInterventiTrovati));
		    }
		}
	    }
	    //---------------------------------------------------------------------------------------------------------------------------//
	    //-------------------------------------------Individuazione dell’intervento--------------------------------------------------//
	    //--------------------------------------------Individuazione tramite href----------------------------------------------------//
	    //------------------------------------------------------START----------------------------------------------------------------//
	    //---------------------------------------------------------------------------------------------------------------------------//
	    // Controllo che non sia stato trovato un intervento tramite settori/operazioni (isInterventoTrovato==false)
	    // nel caso non sia stato trovato cerco l'intervento tramite gli HREF di people
	    log.debug("isInterventoTrovato " + BooleanUtils.toStringTrueFalse(isInterventoTrovato));
	    if (!isInterventoTrovato) {
		log.debug("Individuazione dell’intervento tramite href");
		//---------------------------------------------------------------------------------------------------------------------------//
		//------------------------------------------------DEFINIZIONE FILTRI---------------------------------------------------------//
		//------------------------------------------------------START----------------------------------------------------------------//
		//---------------------------------------------------------------------------------------------------------------------------//
		// Filtri utilizzati per effettuare la query su ALBEROPROCPEOPLEHREF
		// La tabella ALBEROPROCPEOPLEHREF è in join con ALBEROPROC e permette il mapping tra i procedimenti
		// inviati da PEOPLE e quelli presenti su SiGePro
		String nomeTag = "";
		List<String> listValori = null;
		//---------------------------------------------------------------------------------------------------------------------------//
		//------------------------------------------------------END------------------------------------------------------------------//
		//---------------------------------------------------------------------------------------------------------------------------//
		// Lista contenete tutti i record di mapping tra interventi people e interventi sigepro trovati.
		Alberoprocpeoplehref alberoprocpeoplehref = new Alberoprocpeoplehref();
		List<SchedaType> schedaTypes = request.getDettaglioPratica().getSchede();
		// Ciclo tutte le schede type
		for (SchedaType schedaType : schedaTypes) {
		    //Ogni scehda type contiene una lista di CampoSchedaType
		    List<CampoSchedaType> campoSchedaTypes = schedaType.getCampi();
		    // Ciclo la lista di CampoSchedaType
		    for (CampoSchedaType campoSchedaType : campoSchedaTypes) {
			// Per ogni CampoSchedaType vedo se ha un CampoDinamicoType
			// se si vado ad effettuare la query
			if (campoSchedaType.getCampoDinamico() != null) {
			    // Setto il primo filrto (nome tag)
			    // Lo prendo dal campo nome dall'oggetto ParametroType contenuto CampoDinamicoType (ParametroType.nome)
			    nomeTag = campoSchedaType.getCampoDinamico().getValoreUtente().getNome();
			    //Un oggetto ParametroType conterrà una lista di ValoriParametriType 
			    List<ElementoValoreCampoDinamicoType> parametroTypes = campoSchedaType.getCampoDinamico().getValoreUtente().getValore();
			    listValori = new ArrayList<String>();
			    // Ciclo questa lista e creo il secondo filtro da passare alla query
			    for (ElementoValoreCampoDinamicoType valoreParametroType : parametroTypes) {
				listValori.add(valoreParametroType.getCodice());
			    }
			    // Recupero un oggetto Alberoprocpeoplehref filtrando per software, nomeTag e listaValoriTag e ritorno il primo oggetto della
			    // lista che corrisponde a quello con alberoproc.sc_codice più grande (è così percchè sono ordinati desc per alberoproc.sc_codice )
			    log.debug("populateIntervento: alberoprocpeopleoperService.findByTagNlaPeople({},{},{})",
				    new Object[] { nomeTag, listValori, codiceSoftware });
			    if (listValori.size() > 0) {
				Alberoprocpeoplehref alberoprocpeoplehrefTemp = alberoprocpeoplehrefService.findByTagNlaPeople(nomeTag, listValori,
					codiceSoftware);
				// Controllo se l'oggetto alberoprocpeoplehref è vuoto o no
				// 1- Non vuoto
				// 2- Vuoto
				//1- Faccio un controllo quale dei due oggetti ha l'alberoproc.sc_codice maggiore
				if (alberoprocpeoplehref != null && alberoprocpeoplehref.getId().getCodice() != null) {
				    // Controllo il valore il valore che torna dal confronto lessografico tra le due stringhe passate
				    int controfronto = alberoprocpeoplehref.getAlberoproc().getScCodice()
					    .compareTo(alberoprocpeoplehrefTemp.getAlberoproc().getScCodice());
				    // Il valore sarà:
				    // 	1 - ">=0" se alberoprocpeoplehref.alberoproc.sc_codice maggiore lessicograficamente di alberoprocpeoplehrefTemp.alberoproc.sc_codice
				    //	2-  "<0"  se alberoprocpeoplehref.alberoproc.sc_codice minore lessicograficamente di alberoprocpeoplehrefTemp.alberoproc.sc_codice
				    // se confronto<0 Aggiorno il valore di alberoprocpeoplehref con quello appena trovato alberoprocpeoplehrefTemp
				    // Questo mi consente di avere a fine di tutti i cicli quello con alberoproc.sc_codice più grande tra tutti.
				    if (controfronto < 0) {
					alberoprocpeoplehref = alberoprocpeoplehrefTemp;
				    }
				    // 2- Allora signifa che è la prima volta che entro e setto il l'oggetto alberoprocpeoplehref come quello 
				    // trovato dalla query
				} else {
				    alberoprocpeoplehref = alberoprocpeoplehrefTemp;
				}
			    }
			}
		    }
		}
		// Controlla se sono stati trovati record nella tabella
		//se li trova mette a true "isInterventoTrovato"
		if (alberoprocpeoplehref != null && alberoprocpeoplehref.getId().getCodice() != null) {
		    // Popolo la variabile codiceIntervento, che verra passata all'istanza,
		    // con il valore trovato su SiGePro tramite la tabella di Mapping
		    codiceIntervento = String.valueOf(alberoprocpeoplehref.getAlberoproc().getId().getCodice());
		    // Setto a true la variabile che mi indica che l'intervento è stato trovato e non devo continuare la ricerca
		    isInterventoTrovato = true;
		    // metto che il numero interventi trovato è 1 in quanto in per questo tipo di ricerca
		    // portò avere solo il valori 
		    //	1- 0 : non trovato
		    //	2- 1 : trovato (anche se ce ne ho più di uno prendo solo il primo)
		    numeroInterventiTrovati = 1;
		    log.debug("Numero interventi " + String.valueOf(numeroInterventiTrovati));
		}
	    }
	}
	// Anche la ricerac tramite gli Href non ha prodotto risultati 
	//(isIntervento=false)
	log.debug("isInterventoTrovato " + BooleanUtils.toStringTrueFalse(isInterventoTrovato));
	if (!isInterventoTrovato) {
	    log.debug("Individuazione dell'intervento tramite procedimenti");
	    //---------------------------------------------------------------------------------------------------------------------------//
	    //-------------------------------------------Individuazione dell’intervento--------------------------------------------------//
	    //----------------------------------------Individuazione tramite procedimenti------------------------------------------------//
	    //------------------------------------------------------START----------------------------------------------------------------//
	    //---------------------------------------------------------------------------------------------------------------------------//
	    List<Integer> listCodiciProcedimenti = new ArrayList<Integer>();
	    // Creo una lista di codici procedimento
	    // recupero la lista di codici procedimento dalla lista di oggetti ProcedimentoType della request.
	    List<ProcedimentoType> listaProcedimenti = request.getDettaglioPratica().getProcedimenti();
	    if (listaProcedimenti != null) {
		for (ProcedimentoType procedimentoType : listaProcedimenti) {
		    Inventarioprocedimenti endoMappato = this.findEndoMappato(mitt, dest, procedimentoType);
		    if (endoMappato != null) {
			listCodiciProcedimenti.add(endoMappato.getId().getCodice());
		    }
		}
	    }
	    // Parametro che mi indica se devo recuperare l'interveto dalla verticalizzazione
	    // true	: si
	    // false 	: no
	    boolean recuperaDaVerticalizz = false;
	    // Codice che recupera l'interveto se la chiamata viene fatta da due nodi interni a SiGepro
	    altriDati = request.getDettaglioPratica().getAltriDati();
	    if (altriDati != null) {
		for (ParametroType parametroType : altriDati) {
		    if (parametroType.getNome().equalsIgnoreCase(NOTIFICA_ATTIVITA_ALTRO_DATO_ALBEROPROC)) {
			codiceIntervento = StringUtils.defaultString(parametroType.getValore().get(0).getCodice()).trim();
			if (StringUtils.isNotBlank(codiceIntervento) && codiceIntervento.equals("-1")) {
			    log.warn(
				    "codiceIntervento:  cerco ed imposto l'intervento {} della pratica con configurazione NOTIFICA_ATTIVITA_ALTRO_DATO_ALBEROPROC a -1",
				    codiceIntervento);
			    //  SE PASSATO -1 SULLA CONFIGURAZIONE DEI MAPPING ALLORA INTENDO METTERE IL CODICEINTERVENTO DELLA PRATICA INVIATA 
			    // (AD ESEMPIO UNA PRATICA DI CONSOLE CHE CONDIVIDE LO STESSO ALBERO SU BACKEND DIFFERENTI es da backend suap a geniocivile)
			    if (request.getDettaglioPratica().getIntervento() != null) {
				if (StringUtils.isNotBlank(request.getDettaglioPratica().getIntervento().getCodice())) {
				    codiceIntervento = StringUtils.defaultString(request.getDettaglioPratica().getIntervento().getCodice()).trim();
				    if (Utilities.isInteger(codiceIntervento)) {
					log.warn("populateIntervento: imposto l'intervento della pratica codiceIntervento={}", codiceIntervento);
					Integer ci = Integer.parseInt(codiceIntervento);
					Alberoproc ap = alberoprocService.findById(new PkId(ci));
					if (ap != null) {
					    istanza.getAlberoproc().getId().setCodice(ci);
					    return 1;
					}
					codiceIntervento = "";
				    } else {
					log.warn("Il codice intervento non è un intero {}", codiceIntervento);
					codiceIntervento = "";
				    }
				}
			    }
			}
			break;
		    }
		}
	    }
	    if (StringUtils.isBlank(codiceIntervento)) {
		//La query recupera una lista di AlberoprocEndo filtrando per :
		// 1- Lista di endoprocedimenti
		// 2- software
		// 3- flagPricipale uguale a 1
		if (!listCodiciProcedimenti.isEmpty()) {
		    List<AlberoprocEndo> listAlberoprocEndo = alberoprocEndoService.findByEndoprocedimentiAndSoftware(listCodiciProcedimenti,
			    codiceSoftware, true);
		    // Nel caso che:
		    // 1- Lista vuota o dim maggiore di uno recupero intervento (alberoproc) dalla verticalizzazione
		    // 2- Passo il valore alberoproc.id.codice dell'oggetto AlberoprocEndo
		    if (listAlberoprocEndo.isEmpty() || listAlberoprocEndo.size() > 1) {
			recuperaDaVerticalizz = true;
			numeroInterventiTrovati = listAlberoprocEndo.size();
			log.debug("Numero interventi " + String.valueOf(numeroInterventiTrovati));
			log.debug("Recupero dalla verticalizzazione " + BooleanUtils.toStringTrueFalse(recuperaDaVerticalizz));
		    } else {
			codiceIntervento = String.valueOf(listAlberoprocEndo.get(0).getAlberoproc().getId().getCodice());
			numeroInterventiTrovati = 1;
			log.debug("Numero interventi " + String.valueOf(numeroInterventiTrovati));
		    }
		} else {
		    recuperaDaVerticalizz = true;
		}
		if (recuperaDaVerticalizz) {
		    // Controllo preliminare che la verticalizzazione STC
		    if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC)) {
			// recupero dalla verticalizzazione l'intervento da utilizzare per l'inserimento istanza
			log.debug("Recupero dalla verticalizzazione l'intervento da utilizzare per l'inserimento istanza");
			Verticalizzazioniparametri vertAlberoproc = verticalizzazioniService
				.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC, "ALBEROPROC.SC_ID", ORMHelper.getSoftware());
			if (vertAlberoproc != null && StringUtils.isNotEmpty(vertAlberoproc.getValore())) {
			    codiceIntervento = vertAlberoproc.getValore();
			    numeroInterventiTrovati = 0;
			    log.debug("Numero interventi " + String.valueOf(numeroInterventiTrovati));
			} else {
			    String err = "Non è stato possibile individuare l'intervento (alberoproc) per la nuova pratica. Motivi: <br>" +
					 "- l'intervento non è stato passato dal mittente (tipi movimento/configurazione notifiche/albero procedimenti) <br>" +
					 "- l'intervento non è stato individuato tramite la ricerca per endoprocedimento (alberoproc_endo) <br>" +
					 "- l'intervento di default non è stato specificato per il modulo " + ORMHelper.getSoftware() +
					 " (config. regole/regola STC/param. ALBEROPROC.SC_ID)";
			    log.error("populateIntervento(): {}", err);
			    throw new RuntimeException(err);
			}
		    } else {
			String err = "Attenzione! La verticalizzazione STC non è attiva";
			log.error("populateIntervento(): {}", err);
			throw new RuntimeException(err);
		    }
		}
	    } else {
		numeroInterventiTrovati = 1;
	    }
	}
	if (Utilities.isInteger(codiceIntervento)) {
	    istanza.getAlberoproc().getId().setCodice(Integer.parseInt(codiceIntervento));
	} else {
	    log.warn("il Codice intervento [{}] non è numerico");
	}
	return numeroInterventiTrovati;
    }

    /**
     * Metodo per verificare se il nodo mittente(sportelloType.getIdNodo()) è del tipo richiesto (nodoNLAVertParamKey)
     * Il metodo rilancia eccezione di tipo RuntimeException nel caso in cui la vert STC non sia attiva
     * 
     * @param sportelloType
     *            sportello nodo NLA mittente
     * @param nodoNLAVertParamKey
     *            nome del nodo NLA (come inserito nella verticalizzazione STC)
     * @return true: se il nodo mittente è censito nella vert STC e corrisponde al nodo nodoNLAVertParamKey<br>
     *         false: se il nodo mittente NON è censito nella vert STC o NON corrisponde al nodo nodoNLAVertParamKey
     */
    @Override
    public boolean checkSportello(SportelloType sportelloType, NodoNLAEnum nodoNLAVertParamKey) {

	String err = "Attenzione! La verifica del nodo mittente [idNodo: " + sportelloType.getIdNodo() + ", idEnte: " + sportelloType.getIdEnte() +
		     ", idSportello: " + sportelloType.getIdSportello() + "] ha generato il seguente errore: ";
	boolean isTrovato = false;
	String idNodo = sportelloType.getIdNodo();
	if (StringUtils.isBlank(idNodo)) {
	    err += "Il valore idnodo del nodo NLA mittente è vuoto.";
	    log.error("checkSportello(idNodo={}, parametro={}): {}", new Object[] { idNodo, nodoNLAVertParamKey, err });
	    throw new RuntimeException(err);
	}
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC)) {
	    //recupero dalla vert STC tutti i parametri
	    List<Verticalizzazioniparametri> params = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC);
	    for (Verticalizzazioniparametri param : params) {
		//verifico se idNodo è presente tra i valori dei parametri che iniziano per NLA_IDNODO
		if (param.getVerticalizzazioniparametribase().getId().getParametro().startsWith("NLA_IDNODO")) {
		    if (idNodo.equals(param.getValore())) {
			//verifico se è quello cercato
			if (param.getVerticalizzazioniparametribase().getId().getParametro().equals(nodoNLAVertParamKey.name())) {
			    isTrovato = true;
			}
			break;
		    }
		}
	    }
	} else {
	    err += "La verticalizzazione STC non è attiva.";
	    log.error("checkSportello(idNodo={}, parametro={}): {}", new Object[] { idNodo, nodoNLAVertParamKey, err });
	    throw new RuntimeException(err);
	}
	return isTrovato;
    }

    private void populateLocalizzazione(Istanze istanza, InserimentoPraticaNLARequest praticaNla, Comuni comune) {

	boolean isDomandaAreariservata = checkSportello(praticaNla.getSportelloMittente(), NodoNLAEnum.NLA_IDNODO_AREARISERVATA);
	boolean isDomandaNLABackoffice = checkIsStessoNodoStessoEnte(praticaNla.getSportelloMittente(), praticaNla.getSportelloDestinatario());
	boolean isDomandaNLAProtocolloSigepro = checkSportello(praticaNla.getSportelloMittente(), NodoNLAEnum.NLA_IDNODO_PROTOCOLLOSIGEPRO);
	boolean isNlaConsole = checkSportello(praticaNla.getSportelloMittente(), NodoNLAEnum.NLA_IDNODO_AR_CONSOLE);
	Set<Istanzeeventi> listIstanzeEventiLocalizzazioni = istanza.getIstanzeeventis();
	if (listIstanzeEventiLocalizzazioni == null) {
	    listIstanzeEventiLocalizzazioni = new HashSet<Istanzeeventi>();
	}
	List<LocalizzazioneNelComuneType> localizzazioneNelComuneType = praticaNla.getDettaglioPratica().getLocalizzazione();
	if (localizzazioneNelComuneType != null && !localizzazioneNelComuneType.isEmpty()) {
	    String codiceComune = null;
	    if (comune != null) {
		codiceComune = comune.getCodicecomune();
	    }
	    int i = 0;
	    for (LocalizzazioneNelComuneType loc : localizzazioneNelComuneType) {
		// for (int i = 0; i < localizzazioneNelComuneType.size(); i++) {
		// LocalizzazioneNelComuneType loc = localizzazioneNelComuneType.get(i);
		Istanzestradario istStr = null;
		String codViario = StringUtils.defaultIfEmpty(loc.getCodiceViario(), "-1").trim();
		// BOCCI 23/08/2012 BUGZILLA ID 630 
		// Se il codice viario non è presente (o è negativo es. -1) allora si prova a cercare per descrizione sul db STRADARIO.DESCRIZIONE
		// - se viene trovata allora è il caso 1.
		// - se non viene trovata allora la via è quella impostata nel parametro STC.STRADARIO.CODICESTRADARIO e gli altri dati negli appositi campi.
		Stradario stradario = null;
		boolean nonInserireMaCreaEvento = false;
		String annotazioniStradario = "";
		log.debug("populateLocalizzazione#codViario: {}", loc.getCodiceViario());
		// Se pratica proviene da AREA RISERVATA recupero direttamente lo stradario
		boolean nodoInterno = false;
		if (isDomandaAreariservata || isDomandaNLABackoffice || isDomandaNLAProtocolloSigepro || isNlaConsole) {
		    nodoInterno = true;
		    log.debug("populateLocalizzazione#isNodoInterno: cerco la localizzazione con l'identificativo inviato dai nodi interni");
		    if (StringUtils.isNotBlank(loc.getId())) {
			stradario = stradarioService.findById(new PkId(Integer.parseInt(loc.getId())));
		    }
		} else {
		    if (codViario.equalsIgnoreCase("-1")) {
			log.debug("populateLocalizzazione#codViario: nullo o -1, ricerco per denominazione [{}]", loc.getDenominazione());
			// if (StringUtils.isNotBlank(loc.getId()) && loc.getId().equals("-1") && StringUtils.isNotBlank(loc.getCodiceViario()) && loc.getCodiceViario().equals("-1")) {
			// si prova a cercare per descrizione sul db STRADARIO.DESCRIZIONE
			List<Stradario> stradarios = stradarioService
				.findByDescrizione(StringUtils.defaultIfEmpty(loc.getDenominazione(), "via non decodificata"), codiceComune, 0, 10);// limito la ricerca a 10 risultati
			log.debug("populateLocalizzazione#codViario: trovati {} record su stradario per denominazione", stradarios.size());
			if (stradarios.size() == 1) {
			    log.debug("populateLocalizzazione#codViario: trovata la via per denominazione");
			    // se è stata trovata una via ed è l'unica la prendo
			    stradario = stradarios.get(0);
			}
		    }
		}
		if (stradario == null) {
		    //		    stradario = new Stradario();
		    //		    stradario.setDescrizione(loc.getDenominazione());
		    //		    stradario.setCodviario(loc.getCodiceViario());
		    //		    stradario.setComune(comune);
		    // stradario = stradarioService.bindDomainObject(stradario, PkId.class, "id.codice");
		    List<Stradario> stradarios = stradarioService.findListByCodiceViario(loc.getCodiceViario(), codiceComune, true);
		    if (stradarios.size() == 1) {
			stradario = stradarios.get(0);
		    } else {
			log.warn(
				"populateLocalizzazione# La ricerca dello stradario per codice viario:[{}], ha prodotto il seguente numero di record:[{}]",
				loc.getCodiceViario(), stradarios.size());
		    }
		}
		// 
		if (codViario.equalsIgnoreCase("-1")) { // se codice viario è passato e al passo precedente ha dato errore
							// non devo fare la ricerca seguente perché potrei trovare ercord non corretti
							// esempio nella ricerca di altri comuni dove via garibaldi potrebbe stare 
							// su più enti dello stesso idcomune
		    if (stradario == null) {
			log.debug("populateLocalizzazione# Cerco lo stradario tramite l'algoritmo findByMatchParziale");
			List<Stradario> stradarios = stradarioService.findByMatchParziale(loc.getDenominazione(), codiceComune, 0, 5);
			if (!stradarios.isEmpty()) {
			    if (stradarios.size() == 1) {
				stradario = stradarios.get(0);
				Istanzeeventi istanzeeventi = createEventiLocalizzazioneByMatchParziale(loc.getDenominazione(),
					stradario.getDescrizioneCompleta());
				listIstanzeEventiLocalizzazioni.add(istanzeeventi);
				log.warn(
					"populateLocalizzazione# lo stradario è stato trovato tramite ricerca parziale denominazione:[{}],stradario:[{}]",
					loc.getDenominazione(), stradario.getDescrizioneCompleta());
			    } else {
				log.error("populateLocalizzazione# La ricerca parziale per denominazione {} ha tornato più record",
					loc.getDenominazione());
			    }
			}
		    }
		}
		if (stradario == null) {
		    log.debug("populateLocalizzazione# Cerco lo stradario di default nelle verticalizzazioni");
		    // la lista tornata è vuota o maggiore di uno
		    // se non viene trovata allora la via è quella impostata nel parametro STC.STRADARIO.CODICESTRADARIO e gli altri dati negli appositi campi.
		    Verticalizzazioniparametri vertCodiceStradarioDefault = verticalizzazioniService
			    .getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC, "STRADARIO.CODICESTRADARIO");
		    if (vertCodiceStradarioDefault != null) {
			String codiceStradarioDefault = StringUtils.defaultIfEmpty(vertCodiceStradarioDefault.getValore(), "");
			log.debug("populateLocalizzazione#codiceStradarioDefault: {}, cerco nello stradario con questo id", codiceStradarioDefault);
			if (StringUtils.isNotBlank(codiceStradarioDefault)) {
			    try {
				annotazioniStradario = "Denominazione: " + loc.getDenominazione();
				stradario = stradarioService.findById(new PkId(Integer.parseInt(codiceStradarioDefault)));
				// crea un evento per l'istanza che notifica che la localizzazione non è stata 
				// trovata in modo esatto (Nel caso di applicativi di front che non sono collegati allo
				if (stradario == null) {
				    nonInserireMaCreaEvento = true;
				    log.error(
					    "Errore di configurazione per idcomune {} e parametro {}-{}, nessun record di stradario trovato per il valore {}",
					    new Object[] { ORMHelper.getIdcomune(), WebConstants.VERTICALIZZAZIONE_STC, "STRADARIO.CODICESTRADARIO",
						    codiceStradarioDefault });
				} else {
				    log.debug("Creo evento di localizzazione non definita in modo esatto");
				    Istanzeeventi istanzeeventi = createEventiLocalizzazioneNonDefinita(loc);
				    listIstanzeEventiLocalizzazioni.add(istanzeeventi);
				    log.debug("Creato evento di localizzazione non definita in modo esatto");
				}
			    } catch (NumberFormatException e) {
				nonInserireMaCreaEvento = true;
				log.warn("populateLocalizzazione(): STRADARIO.CODICESTRADARIO della verticalizzazione STC non valido: [{}]",
					vertCodiceStradarioDefault.getValore());
				/*@fabrizioc commentato perchè lo stradario non è più obbligatorio
				throw new RuntimeException(
				    "Errore nella configurazione: il valore STRADARIO.CODICESTRADARIO della verticalizzazione STC non è corretto");
				*/
			    }
			} else {
			    nonInserireMaCreaEvento = true;
			}
		    } else {
			nonInserireMaCreaEvento = true;
		    }
		}
		if (nonInserireMaCreaEvento) {
		    log.warn("populateLocalizzazione#nonInserireMaCreaEvento: creo evento localizzazione non definita");
		    // se il parametro STC.STRADARIO.CODICESTRADARIO non è configurato o si sono verificati errori nel recupero della localizzazione di default
		    // mettere tutti i dati in un alert
		    Istanzeeventi istanzeeventi = createEventiLocalizzazioneNonDefinita(loc);
		    listIstanzeEventiLocalizzazioni.add(istanzeeventi);
		} else {
		    istStr = new Istanzestradario();
		    istStr.setValido(Boolean.FALSE);
		    if (StringUtils.isNotBlank(annotazioniStradario)) {
			// le annotazioni dello stradario contengono la denominazione nel caso venga utilizzato il codicestradario di default
			istStr.setNote(annotazioniStradario);
		    }
		    istStr.setCivico(loc.getCivico());
		    istStr.setEsponente(loc.getEsponente());
		    if (StringUtils.isNotBlank(loc.getColore())) {
			Stradariocolore colore = stradariocoloreService.findById(new StradariocoloreId(loc.getColore()));
			if (colore == null) {
			    //
			    Istanzeeventi istanzeeventi = new Istanzeeventi();
			    istanzeeventi.setIstanze(istanza);
			    istanzeeventi.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_STC_IP);
			    istanzeeventi.setData(Calendar.getInstance().getTime());
			    String eventMessage = "Non è stato possibile individuare il colore con questo codice (" + loc.getColore() +
						  ") della localizzazione [" + loc.getDenominazione() + "," + loc.getCodiceViario() + "]";
			    istanzeeventi.setDescrizione(eventMessage);
			    log.warn(eventMessage);
			    istanza.getIstanzeeventis().add(istanzeeventi);
			}
			istStr.setStradariocolore(colore);
		    }
		    istStr.setScala(loc.getScala());
		    istStr.setInterno(loc.getInterno());
		    istStr.setEsponenteinterno(loc.getEsponenteInterno());
		    istStr.setPiano(loc.getPiano());
		    if (loc.getFrazione() != null) {
			istStr.setFrazione(loc.getFrazione().getDescrizione());
		    }
		    if (loc.getCircoscrizione() != null) {
			istStr.setCircoscrizione(loc.getCircoscrizione().getDescrizione());
		    } else {
			if (stradario.getStradariozone() != null && stradario.getStradariozone().getId() != null
				&& stradario.getStradariozone().getId().getCodice() != null) {
			    istStr.setCircoscrizione(stradario.getStradariozone().getZona());
			}
		    }
		    if (loc.getQuartiere() != null) {
			istStr.setQuartiere(loc.getQuartiere().getDescrizione());
		    }
		    if (loc.getKm() != null) {
			istStr.setKm(loc.getKm());
		    }
		    if (loc.getFabbricato() != null) {
			istStr.setFabbricato(loc.getFabbricato());
		    }
		    if (StringUtils.isNotBlank(loc.getUuid())) {
			istStr.setUuid(loc.getUuid());
		    }
		    if (loc.getCoordinate() != null) {
			istStr.setLatitudine(StringUtils.defaultString(loc.getCoordinate().getLatitudine(), "NON INDICATO"));
			istStr.setLongitudine(StringUtils.defaultString(loc.getCoordinate().getLongitudine(), "NON INDICATO"));
		    }
		    // gestione campi accessoTipo; accessoNumero; accessoDescrizione;
		    String _accessoTipo = StringUtils.defaultIfEmpty(loc.getAccessoTipo(), "");
		    String _accessoNumero = StringUtils.defaultIfEmpty(loc.getAccessoNumero(), "");
		    String _accessoDescrizione = StringUtils.defaultIfEmpty(loc.getAccessoDescrizione(), "");
		    istStr.setAccessoTipo(_accessoTipo);
		    istStr.setAccessoNumero(_accessoNumero);
		    istStr.setAccessoDescrizione(_accessoDescrizione);
		    if (loc.getTipo() != null) {
			TipiLocalizzazioni tl = null;
			String codice = loc.getTipo().getCodice();
			String descrizione = loc.getTipo().getDescrizione();
			if (nodoInterno) {
			    if (StringUtils.isNotBlank(codice)) {
				Integer codiceInt = null;
				if (Utilities.isInteger(codice)) {
				    codiceInt = Integer.parseInt(codice);
				}
				if (codiceInt != null) {
				    tl = tipiLocalizzazioniService.findById(new PkId(codiceInt));
				}
			    }
			}
			if (tl == null) {
			    List<TipiLocalizzazioni> tls = tipiLocalizzazioniService.findByDescrizione(descrizione, null, null);
			    if (tls.size() == 1) {
				tl = tls.get(0);
			    }
			}
			if (tl != null) {
			    istStr.setTipiLocalizzazioni(tl);
			} else {
			    Istanzeeventi istanzeeventi = new Istanzeeventi();
			    istanzeeventi.setIstanze(istanza);
			    istanzeeventi.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_STC_IP);
			    istanzeeventi.setData(Calendar.getInstance().getTime());
			    String eventMessage = "Non è stato possibile risolvere il tipo localizzazione con questi riferimenti (codice:" + codice +
						  ", descrizione: " + descrizione + ") della localizzazione [" + loc.getDenominazione() + "," +
						  loc.getCodiceViario() + "]";
			    istanzeeventi.setDescrizione(eventMessage);
			    log.warn(eventMessage);
			    istanza.getIstanzeeventis().add(istanzeeventi);
			}
		    }
		    if (loc.getCap() != null) {
			istStr.setCap(loc.getCap());
		    }
		    //TODO non è la cosa più corretta , ma nell'xml di stc non c'è modo di discriminare il primario in modo univoco (Eventuale modifica su STC)
		    // il primo indirizzo che recupero lo setto come primario 
		    if (i == 0) {
			istStr.setPrimario(true);
		    }
		    ValoreParametroType noteStradario = StcUtils.getCampoDaAltriDati(praticaNla, ALTRI_DATI_NOTE_ISTANZESTRADARIO + i);
		    if (noteStradario != null) {
			if (StringUtils.isNotBlank(noteStradario.getCodice())) {
			    istStr.setNote(noteStradario.getCodice());
			}
		    }
		    ValoreParametroType codCivicoStradario = StcUtils.getCampoDaAltriDati(praticaNla, ALTRI_DATI_CODCIVICO_ISTANZESTRADARIO + i);
		    if (codCivicoStradario != null) {
			if (StringUtils.isNotBlank(codCivicoStradario.getCodice())) {
			    istStr.setCodicecivico(codCivicoStradario.getCodice());
			}
		    }
		    istStr.setStradario(stradario);
		    List<RiferimentoCatastaleType> rifCat = loc.getRiferimentoCatastale();
		    if (rifCat != null && !rifCat.isEmpty()) {
			for (int j = 0; j < rifCat.size(); j++) {
			    RiferimentoCatastaleType rif = rifCat.get(j);
			    Istanzemappali istMap = new Istanzemappali();
			    istMap.setFoglio(rif.getFoglio());
			    istMap.setParticella(rif.getParticella());
			    istMap.setSub(rif.getSub());
			    istMap.setSezione(rif.getSezione());
			    istMap.setUnitaimmob(rif.getUnitaImobiliare());
			    if (CATASTO_TERRENI.equalsIgnoreCase(rif.getTipoCatasto())) {
				istMap.getCatasto().setCodice("T");
			    } else {
				istMap.getCatasto().setCodice("F");
			    }
			    istStr.getIstanzemappalis().add(istMap);
			}
		    }
		    i++; // SERVE PER CALCOLARE LO STRADARIO PRIMARIO
		    istanza.getIstanzestradarios().add(istStr);
		}
	    }
	}
	// BOCCI 2012-08-23 Lo stradario non è più obbligatorio
	//	else {
	//	    Verticalizzazioniparametri vertCodiceStradarioDefault = verticalizzazioniService.getVerticalizzazioniparametri(
	//		    WebConstants.VERTICALIZZAZIONE_STC, "STRADARIO.CODICESTRADARIO");
	//	    if (vertCodiceStradarioDefault != null) {
	//		Istanzestradario istStr = new Istanzestradario();
	//		try {
	//		    Integer codiceStradario = Integer.valueOf(vertCodiceStradarioDefault.getValore());
	//		    istStr.getStradario().getId().setCodice(codiceStradario);
	//		    istanza.getIstanzestradarios().add(istStr);
	//		    log.debug("Creo evento di localizzazione non definita in modo esatto");
	//		    Istanzeeventi istanzeeventi = createEventiLocalizzazioneNonDefinita(null);
	//		    listIstanzeEventiLocalizzazioni.add(istanzeeventi);
	//		    log.debug("Creato evento di localizzazione non definita in modo esatto");
	//		} catch (NumberFormatException e) {
	//		    log.warn("populateLocalizzazione(): STRADARIO.CODICESTRADARIO della verticalizzazione STC non valido: '{}'",
	//			    vertCodiceStradarioDefault.getValore());
	//		    /*@fabrizioc commentato perchè lo stradario non è più obbligatorio
	//		    throw new RuntimeException(
	//		        "Errore nella configurazione: il valore STRADARIO.CODICESTRADARIO della verticalizzazione STC non è corretto");
	//		    */
	//		}
	//	    }
	/*@fabrizioc commentato perchè lo stradario non è più obbligatorio
	else {
	log.error("populateLocalizzazione(): Errore nella configurazione: non è stato configurato il valore STRADARIO.CODICESTRADARIO della verticalizzazione STC");
	throw new RuntimeException(
		"Errore nella configurazione: non è stato configurato il valore STRADARIO.CODICESTRADARIO della verticalizzazione STC");
	}
	*/
	// 	}
	// Inserisce la lista di eventi che sono stati registrati.Viene messo alla fine in quanto possono essere
	/// inviate più localizzaioni ed ognuna può determinare un evento da registrare.
	istanza.setIstanzeeventis(listIstanzeEventiLocalizzazioni);
    }

    /**
     * Crea un evento istanza che notifica all'operatore che la localizzaizone non è stata trovata in modo esatto.
     * 
     * @return
     */
    private Istanzeeventi createEventiLocalizzazioneNonDefinita(LocalizzazioneNelComuneType loc) {

	//Inizializzazione evento da registrare
	Istanzeeventi istanzeeventi = new Istanzeeventi();
	//istanzeeventi.setIstanze(istanza);
	istanzeeventi.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_STC_IP);
	istanzeeventi.setData(Calendar.getInstance().getTime());
	String datiBusta = "";
	datiBusta += printVar("Denominazione: ", loc.getDenominazione());
	datiBusta += printVar("Civico: ", loc.getCivico());
	datiBusta += printVar("Esponente: ", loc.getEsponente());
	datiBusta += printVar("Colore: ", loc.getColore());
	datiBusta += printVar("Scala: ", loc.getScala());
	datiBusta += printVar("Piano: ", loc.getPiano());
	datiBusta += printVar("Interno: ", loc.getInterno());
	datiBusta += printVar("EsponenteInterno: ", loc.getEsponenteInterno());
	datiBusta += printVar("Fabbricato: ", loc.getFabbricato());
	datiBusta += printVar("Km: ", loc.getKm());
	// datiBusta += printVar("Cap: ", loc.get);
	if (loc.getFrazione() != null) {
	    datiBusta += "Frazione: [";
	    datiBusta += printVar("Codice: ", loc.getFrazione().getCodice());
	    datiBusta += printVar("Descrizione: ", loc.getFrazione().getDescrizione());
	    datiBusta += "]";
	}
	if (loc.getQuartiere() != null) {
	    datiBusta += "\nQuartiere: [";
	    datiBusta += printVar("Codice: ", loc.getQuartiere().getCodice());
	    datiBusta += printVar("Descrizione: ", loc.getQuartiere().getDescrizione());
	    datiBusta += "]";
	}
	if (loc.getCircoscrizione() != null) {
	    datiBusta += "\nCircoscrizione: [";
	    datiBusta += printVar("Codice: ", loc.getCircoscrizione().getCodice());
	    datiBusta += printVar("Descrizione: ", loc.getCircoscrizione().getDescrizione());
	    datiBusta += "]";
	}
	if (StringUtils.isNotBlank(loc.getId())) {
	    datiBusta += "(" + printVar("ID: ", loc.getId()) + ")";
	}
	if (StringUtils.isNotBlank(loc.getCodiceViario())) {
	    datiBusta += "(" + printVar("CodiceViario: ", loc.getCodiceViario()) + ")";
	}
	if (loc.getRiferimentoCatastale() != null) {
	    if (loc.getRiferimentoCatastale().size() > 0) {
		datiBusta += "\nRiferimentiCatastali: ";
		for (RiferimentoCatastaleType rc : loc.getRiferimentoCatastale()) {
		    datiBusta += "[";
		    datiBusta += printVar("TipoCatasto: ", rc.getTipoCatasto());
		    datiBusta += printVar("Foglio: ", rc.getFoglio());
		    datiBusta += printVar("Particella: ", rc.getParticella());
		    datiBusta += printVar("Sub: ", rc.getSub());
		    datiBusta += "]";
		}
	    }
	}
	istanzeeventi.setDescrizione("Non è stato possibile individuare la localizzazione esatta con i dati[" + datiBusta + "]");
	log.warn("insertEventoLocalizzazioneNonDefinita(): Non è stato possibile individuare la localizzazione esatta");
	return istanzeeventi;
    }

    /**
     * Crea un evento istanza che notifica all'operatore che la localizzaizone non è stata trovata in modo esatto.
     * 
     * @return
     */
    private Istanzeeventi createEventiLocalizzazioneByMatchParziale(String denominazioneStradarioUtente, String descrizioneStradarioDB) {

	//Inizializzazione evento da registrare
	Istanzeeventi istanzeeventi = new Istanzeeventi();
	//istanzeeventi.setIstanze(istanza);
	istanzeeventi.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_STC_IP);
	istanzeeventi.setData(Calendar.getInstance().getTime());
	String datiBusta = "";
	datiBusta += printVar("Denominazione: ", denominazioneStradarioUtente);
	istanzeeventi.setDescrizione(
		"La localizzazione [" + descrizioneStradarioDB + "] è stata ricavata attraverso il dato [" + datiBusta + "] indicato dall'utente.");
	log.warn("createEventiLocalizzazioneByMatchParziale(): La localizzazione è stata individuata tramite MatchParziale");
	return istanzeeventi;
    }

    private String printVar(String nomeVar, String value) {

	if (StringUtils.isNotBlank(value)) {
	    return ",\n" + nomeVar + ": " + value;
	}
	return "";
    }

    //    public static void main(String[] args) {
    //
    //	LocalizzazioneNelComuneType loc = new LocalizzazioneNelComuneType();
    //	loc.setDenominazione("denominazione");
    //	RiferimentoCatastaleType rct = new RiferimentoCatastaleType();
    //	rct.setFoglio("foglio");
    //	loc.getRiferimentoCatastale().add(rct);
    //	String rtb = ReflectionToStringBuilder.reflectionToString(loc, ToStringStyle.MULTI_LINE_STYLE);
    //	System.out.println(rtb.toString());
    //    }
    private void populateData(Istanze istanza, XMLGregorianCalendar dataPratica) {

	try {
	    istanza.setData(dataPratica.toGregorianCalendar().getTime());
	} catch (Exception e) {
	    log.error("populateData(): La data della pratica non è nel formato corretto.");
	    throw new RuntimeException("Errore, la data della pratica non è nel formato corretto.");
	}
    }

    private Date addTimeToDate(Date date, String oraDataPratica) {

	//	Date date = istanza.getData();
	date = Utilities.addTime(date, oraDataPratica);
	//	istanza.setData(date);
	return date;
    }

    private void populateAlberoprocdocumentiCat(Documentiistanza docIst, Set<AlberoprocDocumenti> alberoprocDocumentis) {

	if (null != alberoprocDocumentis && null != docIst) {
	    for (AlberoprocDocumenti alberoprocDocumenti : alberoprocDocumentis) {
		if (alberoprocDocumenti.getAlberoprocDocumenticat() != null) {
		    if (StringUtils.defaultString(alberoprocDocumenti.getDescrizione(), "-1000")
			    .equalsIgnoreCase(StringUtils.defaultString(docIst.getDocumento(), "-20000"))) {
			docIst.setAlberoprocDocumenticat(alberoprocDocumenti.getAlberoprocDocumenticat());
		    }
		}
	    }
	}
    }

    private void populateDocumenti(Istanze istanza, InserimentoPraticaNLARequest request) {

	boolean isNodoInterno = isChiamataDaNodoInterno(request.getSportelloDestinatario(), request.getSportelloMittente(), false);
	List<DocumentiType> documenti = request.getDettaglioPratica().getDocumenti();
	if (documenti != null && !documenti.isEmpty()) {
	    // cerco di riassociare la categoria dei documenti ai doc
	    Set<AlberoprocDocumenti> alberoprocDocumentis = null;
	    if (!EntityUtils.isNestedPropertyBlank(istanza, "alberoproc.id.codice")) {
		AlberoprocHelper helper = alberoprocService.findAlberoprocHelper(istanza.getAlberoproc());
		if (helper != null) {
		    alberoprocDocumentis = helper.getAlberoprocDocumentis();
		}
	    }
	    for (int i = 0; i < documenti.size(); i++) {
		DocumentiType _doc = documenti.get(i);
		Documentiistanza docIst = new Documentiistanza();
		// Ad esso si metteva la data della pratica
		if (_doc.getData() != null) {
		    Date dataReg = Utilities.getDate(_doc.getData());
		    docIst.setData(dataReg);
		} else {
		    docIst.setData(request.getDettaglioPratica().getDataPratica().toGregorianCalendar().getTime());
		}
		docIst.setPresente(true);
		docIst.setDocumento(_doc.getDocumento());
		docIst.setNote(_doc.getAnnotazioni());
		docIst.setStcIddocumento(_doc.getId());
		populateAlberoprocdocumentiCat(docIst, alberoprocDocumentis);
		Integer controlloOk = getValoreControlloOk(request.getDettaglioPratica().getAltriDati(), _doc.getId(),
			ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_ISTANZA);
		docIst.setControllook(controlloOk);
		if (_doc.getAllegati() != null) {
		    docIst.setStcIdallegato(_doc.getAllegati().getId());
		    if (isNodoInterno) {
			// BOCCI BUG 364 se la richiesta viene da nodi interni a SIGEPRO vengono passati direttamente i riferimenti interni agli oggetti
			if (StringUtils.isNotBlank(_doc.getAllegati().getId())) {
			    Oggetti oggetto = null;
			    if (_doc.getAllegati().getId().startsWith(CODICEOGGETTO_CONST_ALLEGATI)) {
				oggetto = getOggettoFromRiferimentoLocale(_doc.getAllegati().getId());
			    } else {
				oggetto = oggettiService.findById(new PkId(Integer.parseInt(_doc.getAllegati().getId())));
			    }
			    if (oggetto == null) {
				log.error("L'oggetto con riferimento {} non è stato trovato nella base dati", _doc.getAllegati().getId());
				throw new RuntimeException(
					"L'oggetto con riferimento " + _doc.getAllegati().getId() + " non è stato trovato nella base dati");
			    }
			    metadatiOggetto(oggetto, _doc);
			    docIst.setOggetto(oggetto);
			}
		    } else {
			if (_doc.getAllegati().getFile() != null) {
			    Oggetti ogg = new Oggetti();
			    byte[] content = Utilities.dataHandlerToBytes(_doc.getAllegati().getFile().getBinaryData());
			    ogg.setOggetto(content);
			    ogg.setNomefile(_doc.getAllegati().getFile().getFileName());
			    metadatiOggetto(ogg, _doc);
			    docIst.setOggetto(ogg);
			} else {
			    if (_doc.getAllegati().getId().startsWith(CODICEOGGETTO_CONST_ALLEGATI)) {
				Oggetti oggetto = getOggettoFromRiferimentoLocale(_doc.getAllegati().getId());
				metadatiOggetto(oggetto, _doc);
				docIst.setOggetto(oggetto);
			    }
			}
		    }
		}
		istanza.getDocumentiistanzas().add(docIst);
	    }
	}
    }

    @Override
    public Integer getValoreControlloOk(List<ParametroType> altriDati, String id, String chiaveDaAprire) {

	if (altriDati != null) {
	    for (ParametroType pt : altriDati) {
		if (pt != null && StringUtils.isNotBlank(pt.getNome())) {
		    if (pt.getNome().equalsIgnoreCase(chiaveDaAprire)) {
			List<ValoreParametroType> listaValori = pt.getValore();
			if (!listaValori.isEmpty()) {
			    for (ValoreParametroType vpt : listaValori) {
				if (StringUtils.isNotBlank(vpt.getCodice())) {
				    if (vpt.getCodice().equalsIgnoreCase(id)) {
					if (StringUtils.isNotBlank(vpt.getDescrizione())) {
					    String valoreControlloOk = vpt.getDescrizione().trim();
					    if ("null".equalsIgnoreCase(valoreControlloOk)) {
						return null;
					    }
					    if (Utilities.isInteger(valoreControlloOk)) {
						return Integer.parseInt(valoreControlloOk);
					    }
					}
				    }
				}
			    }
			}
			break;
		    }
		}
	    }
	}
	return null;
    }

    private Oggetti getOggettoFromRiferimentoLocale(String id) {

	if (StringUtils.isNotBlank(id)) {
	    if (id.indexOf(":") > 0) {
		String codiceOggettoStr = id.substring(id.indexOf(":") + 1);
		if (StringUtils.isNotBlank(codiceOggettoStr)) {
		    Integer codiceOggetto;
		    try {
			codiceOggetto = Integer.parseInt(codiceOggettoStr.trim());
		    } catch (Exception e) {
			log.error("Il codiceoggetto tornato dal riferimento allegato non è numerico. RIF(id={}, CO={})", id, codiceOggettoStr);
			throw new RuntimeException(
				"Il codiceoggetto tornato dal riferimento allegato non è numerico. RIF(id=" + id + ", CO=" + codiceOggettoStr + ")");
		    }
		    return oggettiService.findById(new PkId(codiceOggetto));
		} else {
		    return null;
		}
	    }
	}
	return null;
    }

    private void populateProcedimenti2(InserimentoPraticaNLARequest request, Istanze istanza) {

	boolean isNodoInterno = isChiamataDaNodoInterno(request.getSportelloDestinatario(), request.getSportelloMittente(), false);
	//cerco in altriDati il param $INSERIMENTO_DIRETTO$
	boolean isInserimentoDiretto = isInserimentoDiretto(request.getDettaglioPratica().getAltriDati());
	Set<Istanzeeventi> eventi = istanza.getIstanzeeventis();
	if (eventi == null) {
	    eventi = new HashSet<Istanzeeventi>();
	}
	SportelloType mitt = request.getSportelloMittente();
	SportelloType dest = request.getSportelloDestinatario();
	List<ProcedimentoType> listaProcedimenti = request.getDettaglioPratica().getProcedimenti();
	if (listaProcedimenti != null) {
	    for (ProcedimentoType procedimentoType : listaProcedimenti) {
		Inventarioprocedimenti endoMappato = findEndoMappato(mitt, dest, procedimentoType);
		if (endoMappato != null) {
		    this.setProcedimento(istanza, endoMappato, procedimentoType, isNodoInterno, request);
		} else {
		    String err = "Procedimento non mappato. Codice: " + procedimentoType.getCodice() + ", Descrizione: " +
				 StringUtils.defaultIfEmpty(procedimentoType.getDescrizione(), "");
		    log.warn("populateProcedimenti2(): Procedimento non mappato. Codice: {}, Descrizione: {}", procedimentoType.getCodice(),
			    procedimentoType.getDescrizione());
		    if (isInserimentoDiretto) {
			//Se è presente [$INSERIMENTODIRETTO$] si rilancia l'errore "Procedimento non mappato"
			throw new RuntimeException(err);
		    } else {
			//Se non è presente [$INSERIMENTODIRETTO$] si inserisce l'evento "Procedimento non mappato"
			Istanzeeventi evento = new Istanzeeventi();
			evento.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_STC_IP);
			evento.setDescrizione(err);
			evento.setIstanze(istanza);
			eventi.add(evento);
		    }
		}
		if (!eventi.isEmpty()) {
		    istanza.setIstanzeeventis(eventi);
		}
	    }
	}
    }

    private boolean isInserimentoDiretto(List<ParametroType> params) {

	boolean isInserimentoDiretto = false;
	for (ParametroType param : params) {
	    if (ALTRI_DATI_PARAM_INSERIMENTO_DIRETTO.equals(param.getNome())) {
		isInserimentoDiretto = true;
		break;
	    }
	}
	return isInserimentoDiretto;
    }

    private Inventarioprocedimenti findEndoMappato(SportelloType mitt, SportelloType dest, ProcedimentoType procedimentoType) {

	if (procedimentoType == null) {
	    return null;
	}
	Inventarioprocedimenti endoMappato = null;
	boolean isDomandaAreariservata = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO_AREARISERVATA);
	boolean isNlaCartComunicazioniInterne = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO_CART_COMINTERNE);
	boolean isNlarfc239 = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO_RFC239);
	boolean isNodoMittBackoffice = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO);
	boolean isNodoDestBackoffice = checkSportello(dest, NodoNLAEnum.NLA_IDNODO);
	boolean isNlaConsole = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO_AR_CONSOLE);
	boolean isNlaEnteNonLocale = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO_ENTE_NON_LOCALE);
	if (isDomandaAreariservata // 
		|| (isNodoDestBackoffice && isNodoMittBackoffice) //
		|| isNlaEnteNonLocale // nel caso di ente non locale si suppone che gli endoprocedimenti abbiano gli stessi codici di quelli locali
	) {
	    // nel caso dell'area riservata il codice del procedimento è quello del db (non si eseguono mapping)
	    Inventarioprocedimenti endo = findEndoBackoffice(Integer.valueOf(procedimentoType.getCodice()), dest.getIdSportello());
	    if (endo != null) {
		return endo;
	    }
	}
	if (isNlaCartComunicazioniInterne || isNlarfc239 || isNlaConsole) {
	    // In caso di NlaCartComunicazioniInterne - se codice è intero allora proviene da ns area riservata
	    // altrimenti da STAR e allora servono Mappature con nodo CART_COMINTERNE
	    if (Utilities.isInteger(procedimentoType.getCodice())) {
		Inventarioprocedimenti endo = findEndoBackoffice(Integer.valueOf(procedimentoType.getCodice()), dest.getIdSportello());
		if (endo != null) {
		    return endo;
		}
	    }
	}
	//Si costruisce il codice del procedimento da cercare utilizzando i dati del mittente [IDENTE][IDSPORTELLO][CODICE] --> E256SU1
	String codiceProcSTC = this.getCodiceProcSTC(mitt, procedimentoType.getCodice());
	Inventarioprocedimentipeople procSTC = inventarioprocedimentipeopleService.findByCodiceSTP(codiceProcSTC, dest.getIdSportello());
	if (procSTC != null) {
	    //Se viene trovato si verifica che il SOFTWARE del procedimento trovato sia uguale all'IDSPORTELLO destinatario
	    Inventarioprocedimenti endo = procSTC.getInventarioprocedimenti();
	    if (endo != null && endo.getSoftware().getCodice().equals(dest.getIdSportello())) {
		endoMappato = endo;
	    } else {
		//l'endo trovato è di TT controllo ATTIVAIN
		if (endo != null) {
		    Set<Inventarioprocedimentisoftware> attivaInList = endo.getInventarioprocedimentisoftwares();
		    if (attivaInList != null) {
			for (Inventarioprocedimentisoftware attivaIn : attivaInList) {
			    if (attivaIn.getSoftware().getCodice().equals(dest.getIdSportello())) {
				endoMappato = endo;
				break;
			    }
			}
		    }
		}
	    }
	}
	return endoMappato;
    }

    private Inventarioprocedimenti findEndoBackoffice(Integer codiceProcedimento, String idSportello) {

	Inventarioprocedimenti endo = inventarioprocedimentiService.findById(new PkId(codiceProcedimento));
	if (endo != null && endo.getSoftware().getCodice().equals(WebConstants.SOFTWARE_TT)) {
	    // Se viene trovato e l'endo è del software TTC 
	    // si verifica esista una riga in INVENTARIOPROCEDIMENTISOFTWARE con MODULOSOFTWARE (ATTIVAIN) uguale all'IDSPORTELLO destinatario
	    Set<Inventarioprocedimentisoftware> attivaInList = endo.getInventarioprocedimentisoftwares();
	    if (attivaInList != null) {
		for (Inventarioprocedimentisoftware attivaIn : attivaInList) {
		    if (attivaIn.getSoftware().getCodice().equals(idSportello)) {
			return endo;
		    }
		}
	    }
	} else {
	    return endo;
	}
	return null;
    }

    /**
     * se mittente è AIDA -> AIDA+CODICEPROCEDIMENTO<br>
     * se mittente è PEOPLE -> PEOPLE+CODICEPROCEDIMENTO<br>
     * se mittente è AREARISERVATA -> CODICEPROCEDIMENTO<br>
     * se mittente è PROTOCOLLOSIGEPRO -> CODICEPROCEDIMENTO<br>
     * in altri casi ->IDENTE+IDSPORTELLO+CODICEPROCEDIMENTO
     * 
     * @param mittente
     * @param codiceProcedimento
     * @return
     */
    private String getCodiceProcSTC(SportelloType mittente, String codiceProcedimento) {

	String prefix = "";
	int base = NodoNLAEnum.NLA_IDNODO.name().length() + 1;
	if (checkSportello(mittente, NodoNLAEnum.NLA_IDNODO_AREARISERVATA)) {
	    //no prefix
	} else if (checkSportello(mittente, NodoNLAEnum.NLA_IDNODO_AIDA)) {
	    prefix = NodoNLAEnum.NLA_IDNODO_AIDA.name().substring(base);
	} else if (checkSportello(mittente, NodoNLAEnum.NLA_IDNODO_PEOPLE)) {
	    prefix = NodoNLAEnum.NLA_IDNODO_PEOPLE.name().substring(base);
	} else if (checkSportello(mittente, NodoNLAEnum.NLA_IDNODO_CART_COMINTERNE) || checkSportello(mittente, NodoNLAEnum.NLA_IDNODO_RFC239)) {
	    prefix = ""; // NodoNLAEnum.NLA_IDNODO_CART_COMINTERNE.name().substring(base); // lo mette direttamente il nldo nla
	} else if (checkSportello(mittente, NodoNLAEnum.NLA_IDNODO_PROTOCOLLOSIGEPRO)) {
	    //no prefix
	} else {
	    prefix = mittente.getIdEnte() + mittente.getIdSportello();
	}
	return prefix + codiceProcedimento;
    }

    private void setProcedimento(Istanze istanza, Inventarioprocedimenti endo, ProcedimentoType pt, boolean isNodoInterno,
	    InserimentoPraticaNLARequest request) {

	Istanzeprocedimenti istProc = new Istanzeprocedimenti();
	try {
	    istProc.getId().setCodiceinventario(endo.getId().getCodice());
	    istProc.setInventarioprocedimenti(endo);
	    istProc.setPerprovvedimento(Boolean.TRUE);
	    istProc.setDataattivazione(new Date());
	    // @GIANPAOLO Aggiunto integrazione FVG_SUAP_INRETE
	    // RICCARDO 2018-07-19 il comportamento lo rendo valido solo per FVG_SUAP_INRETE 
	    // altrimenti mi modifica la logica di altre installazioni es: ER o Toscana 
	    if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE)) {
		istProc.setDescrizioneEndoprocedimento(StringUtils.defaultIfEmpty(pt.getDescrizione(), ""));
	    }
	    if (pt.getEstremiAtto() != null) {
		istProc.setProtNum(pt.getEstremiAtto().getRiferimento());
		if (pt.getEstremiAtto().getData() != null) {
		    istProc.setProtDel(pt.getEstremiAtto().getData().toGregorianCalendar().getTime());
		}
		istProc.setTipoAtto(pt.getEstremiAtto().getTipoAtto());
		istProc.setRilasciatoDa(pt.getEstremiAtto().getRilasciatoDa());
		istProc.setNote(pt.getEstremiAtto().getNote());
		istProc.setAcquisito(Boolean.TRUE);
	    }
	    List<DocumentiType> documentiEndo = pt.getDocumenti();
	    // recupero la lista dei documenti per l'endoprocedimento e li inserisco nell'array degli allegatiEndo
	    for (DocumentiType documentiType : documentiEndo) {
		Istanzeallegati istanzeAllegati = new Istanzeallegati();
		istanzeAllegati.setInventarioprocedimenti(endo);
		istanzeAllegati.setAllegatoextra(documentiType.getDocumento());
		istanzeAllegati.setSelezionato(Boolean.TRUE);
		istanzeAllegati.setPresente(Boolean.FALSE);
		istanzeAllegati.setNote(documentiType.getAnnotazioni());
		istanzeAllegati.setStcIddocumento(documentiType.getId());
		Integer controlloOk = getValoreControlloOk(request.getDettaglioPratica().getAltriDati(), documentiType.getId(),
			ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_ENDO);
		istanzeAllegati.setControllook(controlloOk);
		if (documentiType.getAllegati() != null) {
		    istanzeAllegati.setStcIdallegato(documentiType.getAllegati().getId());
		    // BOCCI BUG 364 se la richiesta viene da nodi interni a SIGEPRO vengono passati direttamente i riferimenti interni agli oggetti
		    if (isNodoInterno) {
			if (StringUtils.isNotBlank(documentiType.getAllegati().getId())) {
			    Oggetti oggetto = oggettiService.findById(new PkId(Integer.parseInt(documentiType.getAllegati().getId())));
			    metadatiOggetto(oggetto, documentiType);
			    istanzeAllegati.setOggetto(oggetto);
			    istanzeAllegati.setPresente(Boolean.TRUE);
			}
		    } else {
			if (documentiType.getAllegati().getFile() != null && documentiType.getAllegati().getFile().getBinaryData() != null
				&& StringUtils.isNotBlank(documentiType.getAllegati().getFile().getFileName())) {
			    Oggetti ogg = new Oggetti();
			    byte[] content = Utilities.dataHandlerToBytes(documentiType.getAllegati().getFile().getBinaryData());
			    ogg.setOggetto(content);
			    ogg.setNomefile(documentiType.getAllegati().getFile().getFileName());
			    istanzeAllegati.setOggetto(ogg);
			    metadatiOggetto(ogg, documentiType);
			    istanzeAllegati.setPresente(Boolean.TRUE);
			} else {
			    if (documentiType.getAllegati().getId().startsWith(CODICEOGGETTO_CONST_ALLEGATI)) {
				Oggetti oggetto = getOggettoFromRiferimentoLocale(documentiType.getAllegati().getId());
				metadatiOggetto(oggetto, documentiType);
				istanzeAllegati.setOggetto(oggetto);
				istanzeAllegati.setPresente(Boolean.TRUE);
			    }
			}
		    }
		}
		istanza.getIstanzeallegatis().add(istanzeAllegati);
	    }
	    istanza.getIstanzeprocedimentis().add(istProc);
	} catch (Exception e) {
	    log.error("setProcedimento(): Errore durante la costruzione dell' oggetto IstanzeProcedimenti {}", e);
	    throw new RuntimeException("Errore durante l'inserimento dell'endo: " + e.getMessage(), e);
	}
    }

    private void populateSoggettiCollegati(Istanze istanza, InserimentoPraticaNLARequest inserimentoPraticaNLARequest) {

	DettaglioPraticaType dettaglioPraticaType = inserimentoPraticaNLARequest.getDettaglioPratica();
	List<AltriSoggettiType> soggettiCollegati = dettaglioPraticaType.getAltriSoggetti();
	if (soggettiCollegati != null) {
	    for (AltriSoggettiType altriSoggettiType : soggettiCollegati) {
		Istanzerichiedenti istRich = new Istanzerichiedenti();
		RuoloType tipo = altriSoggettiType.getTipoRapporto();
		Tipisoggetto tipisoggetto = decodificaTipoSoggetto(tipo, ORMHelper.getSoftware(), istanza, true, inserimentoPraticaNLARequest);
		if (tipisoggetto != null) {
		    istRich.setTiposoggetto(tipisoggetto);
		    if (BooleanUtils.isTrue(tipisoggetto.getFlgSpecificadescrizione())) {
			if (tipo != null && StringUtils.isNotBlank(tipo.getRuolo())) {
			    istRich.setDescrsoggetto(tipo.getRuolo());
			} else {
			    istRich.setDescrsoggetto(tipisoggetto.getTiposoggetto());
			}
		    }
		}
		AnagrafeType _sogRich = altriSoggettiType.getSoggetto();
		populateAnagrafe(istRich.getRichiedente(), _sogRich, false, inserimentoPraticaNLARequest);
		AnagrafeType soggettoCollegato = altriSoggettiType.getAnagraficaCollegata();
		if (soggettoCollegato != null) {
		    populateAnagrafe(istRich.getAnagrafeCollegata(), soggettoCollegato, false, inserimentoPraticaNLARequest);
		}
		istanza.getIstanzerichiedentis().add(istRich);
	    }
	}
    }

    private void populateProcure(Istanze istanza, InserimentoPraticaNLARequest inserimentoPraticaNLARequest) {

	List<ProcuraType> procure = inserimentoPraticaNLARequest.getDettaglioPratica().getProcure();
	// gestione procure
	if (procure != null) {
	    for (ProcuraType procuraType : procure) {
		Istanzeprocure istproc = new Istanzeprocure();
		String cfRappresentato = procuraType.getCfRappresentato();
		String cfProcuratore = procuraType.getCfProcuratore();
		Oggetti oggetto = null;
		String stcIdDocumento = null;
		String stcIdAllegato = null;
		Oggetti oggettiDocIdent = null;
		String stcIdDocDocIde = null;
		String stcIdAllDocIde = null;
		//stcIdDocumento = procuraType.getProcura().getId();
		stcIdDocumento = (String) EntityUtils.getNestedProperty(procuraType.getProcura(), "id");
		stcIdAllegato = (String) EntityUtils.getNestedProperty(procuraType.getProcura(), "allegati.id");
		stcIdDocDocIde = (String) EntityUtils.getNestedProperty(procuraType.getDocumentoIdentita(), "id");
		stcIdAllDocIde = (String) EntityUtils.getNestedProperty(procuraType.getDocumentoIdentita(), "allegati.id");
		if (procuraType.getProcura() != null && procuraType.getProcura().getAllegati() != null) {
		    boolean isNodoInterno = isChiamataDaNodoInterno(inserimentoPraticaNLARequest.getSportelloDestinatario(),
			    inserimentoPraticaNLARequest.getSportelloMittente(), false);
		    if (isNodoInterno) {
			// BOCCI BUG 364 se la richiesta viene da nodi interni a SIGEPRO vengono passati direttamente i riferimenti interni agli oggetti
			Integer codiceOggetto = null;
			try {
			    codiceOggetto = Integer.parseInt(procuraType.getProcura().getAllegati().getId());
			    oggetto = oggettiService.findById(new PkId(codiceOggetto));
			    metadatiOggetto(oggetto, procuraType.getProcura());
			} catch (Exception e) {
			    log.error("populateSoggettiCollegati: Errore nel recupero del file procura: {}", e.getMessage());
			    // ANOMALIA NON DOVREBBE MAI SUCCEDERE in pratica se il nodo è interno allora dovrrebbe transitare sempre 
			    // il codiceoggetto che è un'intero
			    throw new RuntimeException("populateSoggettiCollegati: Errore nel recupero del file procura: " + e.getMessage(), e);
			}
		    } else {
			if (procuraType.getProcura().getAllegati().getFile() != null) {
			    oggetto = new Oggetti();
			    oggetto.setNomefile(procuraType.getProcura().getAllegati().getFile().getFileName());
			    byte[] content = Utilities.dataHandlerToBytes(procuraType.getProcura().getAllegati().getFile().getBinaryData());
			    oggetto.setOggetto(content);
			    metadatiOggetto(oggetto, procuraType.getProcura());
			} else {
			    if (procuraType.getProcura().getAllegati().getId().startsWith(CODICEOGGETTO_CONST_ALLEGATI)) {
				oggetto = getOggettoFromRiferimentoLocale(procuraType.getProcura().getAllegati().getId());
				metadatiOggetto(oggetto, procuraType.getProcura());
			    }
			}
		    }
		}
		if (procuraType.getDocumentoIdentita() != null && procuraType.getDocumentoIdentita().getAllegati() != null) {
		    boolean isNodoInterno = isChiamataDaNodoInterno(inserimentoPraticaNLARequest.getSportelloDestinatario(),
			    inserimentoPraticaNLARequest.getSportelloMittente(), false);
		    if (isNodoInterno) {
			// BOCCI BUG 364 se la richiesta viene da nodi interni a SIGEPRO vengono passati direttamente i riferimenti interni agli oggetti
			Integer codiceOggetto = null;
			try {
			    codiceOggetto = Integer.parseInt(procuraType.getDocumentoIdentita().getAllegati().getId());
			    oggettiDocIdent = oggettiService.findById(new PkId(codiceOggetto));
			    metadatiOggetto(oggettiDocIdent, procuraType.getDocumentoIdentita());
			} catch (Exception e) {
			    log.error("populateSoggettiCollegati: Errore nel recupero del file doc identità: {}", e.getMessage());
			    // ANOMALIA NON DOVREBBE MAI SUCCEDERE in pratica se il nodo è interno allora dovrrebbe transitare sempre 
			    // il codiceoggetto che è un'intero
			    throw new RuntimeException("populateSoggettiCollegati: Errore nel recupero del file doc identità: " + e.getMessage(), e);
			}
		    } else {
			if (procuraType.getDocumentoIdentita().getAllegati().getFile() != null) {
			    oggettiDocIdent = new Oggetti();
			    oggettiDocIdent.setNomefile(procuraType.getDocumentoIdentita().getAllegati().getFile().getFileName());
			    byte[] content = Utilities.dataHandlerToBytes(procuraType.getDocumentoIdentita().getAllegati().getFile().getBinaryData());
			    oggettiDocIdent.setOggetto(content);
			    metadatiOggetto(oggettiDocIdent, procuraType.getDocumentoIdentita());
			} else {
			    if (procuraType.getDocumentoIdentita().getAllegati().getId().startsWith(CODICEOGGETTO_CONST_ALLEGATI)) {
				oggettiDocIdent = getOggettoFromRiferimentoLocale(procuraType.getDocumentoIdentita().getAllegati().getId());
				metadatiOggetto(oggettiDocIdent, procuraType.getDocumentoIdentita());
			    }
			}
		    }
		}
		istproc.setStcIdDocumento(stcIdDocumento);
		istproc.setStcIdAllegato(stcIdAllegato);
		istproc.setOggetti(oggetto);
		istproc.setOggettiDocIdent(oggettiDocIdent);
		istproc.setStcIdDocDocIde(stcIdDocDocIde);
		istproc.setStcIdAllDocIde(stcIdAllDocIde);
		if (procuraType.getProcura() != null && StringUtils.isNotBlank(procuraType.getProcura().getId())) {
		    Integer controlloOk = getValoreControlloOk(inserimentoPraticaNLARequest.getDettaglioPratica().getAltriDati(),
			    procuraType.getProcura().getId(), ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_PROCURE);
		    istproc.setControllook(controlloOk);
		}
		populateAnagrafeProcura(istproc.getAnagrafeProcuratore(), cfProcuratore, inserimentoPraticaNLARequest);
		populateAnagrafeProcura(istproc.getAnagrafeRappresentato(), cfRappresentato, inserimentoPraticaNLARequest);
		istanza.getIstanzeprocures().add(istproc);
	    }
	}
	// fine gestione procure
    }

    private void populateAnagrafeProcura(Anagrafe anagrafe, String cfDaValutare, InserimentoPraticaNLARequest inserimentoPraticaNLARequest) {

	String cf = (String) EntityUtils.getNestedProperty(inserimentoPraticaNLARequest.getDettaglioPratica().getRichiedente(),
		"anagrafica.codiceFiscale");
	if (StringUtils.defaultIfEmpty(cf, "").equalsIgnoreCase(cfDaValutare)) {
	    AnagrafeType richiedente = new AnagrafeType();
	    richiedente.setPersonaFisica(inserimentoPraticaNLARequest.getDettaglioPratica().getRichiedente().getAnagrafica());
	    populateAnagrafe(anagrafe, richiedente, false, inserimentoPraticaNLARequest);
	    return;
	}
	cf = (String) EntityUtils.getNestedProperty(inserimentoPraticaNLARequest.getDettaglioPratica().getIntermediario(),
		"personaFisica.codiceFiscale");
	if (StringUtils.defaultIfEmpty(cf, "").equalsIgnoreCase(cfDaValutare)) {
	    AnagrafeType richiedente = new AnagrafeType();
	    richiedente.setPersonaFisica(inserimentoPraticaNLARequest.getDettaglioPratica().getIntermediario().getPersonaFisica());
	    populateAnagrafe(anagrafe, richiedente, true, inserimentoPraticaNLARequest);
	    return;
	}
	List<AltriSoggettiType> soggettiCollegati = inserimentoPraticaNLARequest.getDettaglioPratica().getAltriSoggetti();
	if (soggettiCollegati != null) {
	    for (AltriSoggettiType altriSoggetti : soggettiCollegati) {
		cf = (String) EntityUtils.getNestedProperty(altriSoggetti.getSoggetto(), "personaFisica.codiceFiscale");
		if (StringUtils.defaultIfEmpty(cf, "").equalsIgnoreCase(cfDaValutare)) {
		    AnagrafeType richiedente = new AnagrafeType();
		    richiedente.setPersonaFisica(altriSoggetti.getSoggetto().getPersonaFisica());
		    populateAnagrafe(anagrafe, richiedente, false, inserimentoPraticaNLARequest);
		    return;
		}
	    }
	}
	// SOGGETTO NON TROVATO RILANCIO L'ECCEZIONE 
	String err = "Errore nell'elaborazione delle procure dell'istanza: il codice fiscale [" + cfDaValutare +
		     "] non e' stato trovato tra i soggetti dell'istanza";
	log.error(err);
	throw new RuntimeException(err);
    }

    @Override
    public Comuni getComune(DettaglioPraticaType dettaglioPraticaType) {

	boolean isComuniassociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	if (isComuniassociati) {
	    if (dettaglioPraticaType.getCodiceComune() != null) {
		if (log.isDebugEnabled()) {
		    log.debug("NlaHelperService#populateComuneAssociato: dettaglioPraticaType.getCodiceComune()={}",
			    ReflectionToStringBuilder.toString(dettaglioPraticaType.getCodiceComune(), ToStringStyle.SHORT_PREFIX_STYLE));
		}
		// Ricavo codice comune:
		// 1- Se è popolato ComuneType.codicecatastale allora prendo il valore trovato
		// 2  Se è popolato ComuneType.codiceistat ricerco il comune con codice istat passato
		// 3- Se è popolato ComuneType.comune ricerco il comune con comune passato
		String codiceComune = "";
		String codiceIstat = "";
		String comune = "";
		// Cerco se è popolato il codicecatastale
		// Se si, setto il codiceComune = con il valore trovato ed esco dalla cascata di if-else
		Comuni comuni = new Comuni();
		Comuni filter = new Comuni();
		if (StringUtils.isNotBlank(dettaglioPraticaType.getCodiceComune().getCodiceCatastale())) {
		    codiceComune = dettaglioPraticaType.getCodiceComune().getCodiceCatastale();
		    comuni.setCodicecomune(codiceComune.trim());
		} else {//SE no 
			// Controllo se è popolato codiceistat  cerco il comune per quel codice istat e 
			// setto codiceComune = con il valore idcomune del comune trovato dalla query su comuni
			// ed esco dalla cascata di if-else
		    if (StringUtils.isNotBlank(dettaglioPraticaType.getCodiceComune().getCodiceIstat())) {
			codiceIstat = dettaglioPraticaType.getCodiceComune().getCodiceIstat();
			filter.setCodiceistat(codiceIstat.trim());
			comuni = comuniService.findByComune(filter);
		    } else {// Se non lo trovo
			    // Controllo se è popolato comune cerco il comune per nome comune e 
			    // setto codiceComune = con il valore idcomune del comune trovato dalla query su comuni
			    // ed esco dalla cascata di if-else
			if (StringUtils.isNotBlank(dettaglioPraticaType.getCodiceComune().getComune())) {
			    comune = dettaglioPraticaType.getCodiceComune().getComune();
			    filter.setCodiceistat(comune.trim());
			    comuni = comuniService.findByComune(filter);
			}
		    }
		}
		// Se trova comuni cerca se  è un comune associato
		if (comuni != null && StringUtils.isNotBlank(comuni.getCodicecomune())) {
		    ComuniassociatiId id = new ComuniassociatiId();
		    id.setCodicecomune(comuni.getCodicecomune());
		    id.setIdcomune(ORMHelper.getIdcomune());
		    Comuniassociati comuniassociati = comuniassociatiService.findById(id);
		    // Se è un comune associato lo setta alla comune della pratica
		    if (comuniassociati != null) {
			return comuniassociati.getComune();
		    } else {
			// Rilancia errore di configurazione
			String err = "Errore nella configurazione: il comune passato non è configurato come comune associato dell'installazione. RIF(codiceCatastale=" +
				     codiceComune + ", codiceIstat=" + codiceIstat + ", comune=" + comune + ")";
			log.error(err);
			throw new InvalidConfigurationException(err);
		    }
		    // Se non trova il comune setta come comune della pratica quello default	
		} else {
		    String err = "Errore nella configurazione: Non è stato possibile individuare il comune associato. RIF(codiceCatastale=" +
				 codiceComune + ", codiceIstat=" + codiceIstat + ", comune=" + comune + ")";
		    log.error(err);
		    throw new InvalidConfigurationException(err);
		}
	    } else {
		String err = "Non è stato possibile individuare il comune associato per la pratica. Il campo dettaglioPratica.codiceComune è nullo";
		log.error(err);
		throw new InvalidConfigurationException(err);
	    }
	} else {
	    // Non è installazione multi comune
	    List<Comuniassociati> comunis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	    if (comunis.size() == 1) {
		return comunis.get(0).getComune();
	    } else {
		String err = "Non è stato possibile individuare il comune associato per la pratica. Il campo dettaglioPratica.codiceComune è nullo";
		log.error(err);
		throw new InvalidConfigurationException(err);
	    }
	}
    }

    private void populateComuneAssociato(DettaglioPraticaType dettaglioPraticaType, Istanze istanza) {

	Comuni c = getComune(dettaglioPraticaType);
	istanza.setComune(c);
    }

    @Override
    public Movimenti populateMovimenti(InserimentoAttivitaNLARequest request, Istanze istanza, String codiceAmministrazione, boolean passaProtocollo,
	    boolean isPecOpRoto) {

	Movimenti mov = new Movimenti();
	DettaglioAttivitaType attivita = request.getDatiAttivita();
	try {
	    Integer codiceIstanza = Integer.valueOf(attivita.getIdPratica());
	    mov.getIstanza().getId().setCodice(codiceIstanza);
	} catch (NumberFormatException e) {
	    log.error("populateMovimenti: Il valore del tag idPratica del tag DettaglioAttivitaType non è nel formato corretto.");
	    throw new RuntimeException(
		    "Errore nell'xml DettaglioAttivitaType, il valore del tag idPratica non è corretto: '" + attivita.getIdPratica() + "'");
	}
	// BOCCI: 2011-10-26 Rimosso dopo valutazione con Chiocci 
	// (La descrizione dell'attività mittente va sempre messa in quanto l'attività destinataria è già presente nel tipomovimento).
	// setto la descrizione del mov come: "DESC MOVIMENTO DESTINATARIO (STC: DESC ATTIVITA' MITTENTE)"
	String movDesc = attivita.getTipoAttivita().getDescrizione();
	// BOCCI: 2011-10-26 Rimosso dopo valutazione con Chiocci 
	// (La descrizione dell'attività mittente va sempre messa in quanto l'attività destinataria è già presente nel tipomovimento).
	// if (tipimovimento != null) {
	//    movDesc = tipimovimento.getMovimento() + " (STC: " + movDesc + ")";
	// }
	Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(attivita.getTipoAttivita().getCodice()));
	// CHECK NPE
	if (tipimovimento == null) {
	    log.error("populateMovimenti: nessun tipomovimento trovato per il codice attività fornito [{}].", attivita.getTipoAttivita().getCodice());
	    throw new RuntimeException(
		    "Errore,  nessun tipomovimento trovato per il codice attività fornito: '" + attivita.getTipoAttivita().getCodice() + "'");
	}
	// la descrizione potrebbe rimanere quella del movimento a seconda del parametro di verticalizzazione
	Verticalizzazioniparametri nodiStcSovracriviDescrizione = verticalizzazioniService
		.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC, WebConstants.VERTICALIZZAZIONE_STC_NODI_NON_SOVR_DESC_ATTIVITA);
	String nodiStcSovracriviDescrizioneStr = "----";
	if (nodiStcSovracriviDescrizione != null && StringUtils.isNotBlank(nodiStcSovracriviDescrizione.getValore())) {
	    nodiStcSovracriviDescrizioneStr = nodiStcSovracriviDescrizione.getValore();
	}
	if (StringUtils.isNotBlank(nodiStcSovracriviDescrizioneStr) && request != null && request.getSportelloMittente() != null
		&& StringUtils.isNotBlank(request.getSportelloMittente().getIdNodo())) {
	    String idNodo = request.getSportelloMittente().getIdNodo();
	    String[] n = nodiStcSovracriviDescrizioneStr.split(",");
	    for (String ns : n) {
		if (StringUtils.defaultString(ns).trim().equalsIgnoreCase(idNodo)) {
		    movDesc = tipimovimento.getMovimento();
		    break;
		}
	    }
	}
	mov.setTipomovimento(tipimovimento);
	mov.getTipomovimento().getId().setTipomovimento(attivita.getTipoAttivita().getCodice());
	mov.setMovimento(movDesc);
	//
	GregorianCalendar oggi = new GregorianCalendar();
	// BOCCI 2015-08-24 
	//	la data di inserimento del movimento sarà la data in cui viene effettuata l'operazione STC e non la data dell'attività mittente
	// 	altrimenti il mittente potrebbe far compiere dei movimenti nel nodo destinatario nel passato (segnalazioni PISTOIA)
	// 
	// if (attivita.getDataAttivita() != null) {
	//    mov.setData(attivita.getDataAttivita().toGregorianCalendar().getTime());
	//    if (StringUtils.isNotBlank(attivita.getOraDataAttivita())) {
	//	Date _date = addTimeToDate(mov.getData(), attivita.getOraDataAttivita());
	//	mov.setData(_date);
	//    }
	// }
	//boolean isDisabilitaModificaDataNotifica = verticalizzazioniService.isAttivaAndParametroEqualsToValore(WebConstants.VERTICALIZZAZIONE_STC,
	//WebConstants.VERTICALIZZAZIONE_STC_DIS_MOD_DATA_NOTIFICA, "1");
	mov.setData(oggi.getTime());
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC)) {
	    String disModDataNotifica = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_STC,
		    WebConstants.VERTICALIZZAZIONE_STC_DIS_MOD_DATA_NOTIFICA);
	    if (!isPecOpRoto && resolveDisDataModifica(disModDataNotifica, "UFFICI")) {
		if (attivita.getDataAttivita() != null) {
		    mov.setData(attivita.getDataAttivita().toGregorianCalendar().getTime());
		    if (StringUtils.isNotBlank(attivita.getOraDataAttivita())) {
			Date _date = addTimeToDate(mov.getData(), attivita.getOraDataAttivita());
			mov.setData(_date);
		    } else {
			Date _date = addTimeToDate(mov.getData(), Utilities.getOrariosistema());
			mov.setData(_date);
		    }
		}
	    }
	    if (isPecOpRoto) {
		if (resolveDisDataModifica(disModDataNotifica, "PEC") || resolveDisDataModifica(disModDataNotifica, "PROTOCOLLO")) {
		    if (attivita.getDataAttivita() != null) {
			mov.setData(attivita.getDataAttivita().toGregorianCalendar().getTime());
			if (StringUtils.isNotBlank(attivita.getOraDataAttivita())) {
			    Date _date = addTimeToDate(mov.getData(), attivita.getOraDataAttivita());
			    mov.setData(_date);
			} else {
			    Date _date = addTimeToDate(mov.getData(), Utilities.getOrariosistema());
			    mov.setData(_date);
			}
		    }
		}
	    }
	}
	mov.setDatainserimento(oggi.getTime());
	// BOCCI 17/03/2022
	// SE MI ARRIVA UN SOLO PROCEDIMENTO ALLORA CONSIDERO PRINCIPALE QUELLO PER BUGFIXING CON NLA-INFOCAMERE CHE CREA LA PRATICA SENZA ENDO MA POI INVIA LE INTEGRAZIONI PER ENDO
	//Verifico che il procedimento principale sia mappato, se non è mappato inserisco l'attività (movimento) senza endo e avviso (eventi)
	ProcedimentoType procedimentoPrincipale = this.getProcedimentoPrincipale(attivita.getProcedimenti());
	SportelloType mitt = request.getSportelloMittente();
	SportelloType dest = request.getSportelloDestinatario();
	Inventarioprocedimenti endoPrincipaleMappato = this.findEndoMappato(mitt, dest, procedimentoPrincipale);
	if (endoPrincipaleMappato != null) {
	    mov.setEndoprocedimento(endoPrincipaleMappato);
	} else {
	    // il procedimento principale non è mappato o non c'è proprio
	    if (procedimentoPrincipale != null) {
		Istanzeeventi evento = new Istanzeeventi();
		evento.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_STC_IA);
		evento.setDescrizione("Procedimento principale non mappato. Codice: " + procedimentoPrincipale.getCodice() + ", Descrizione: " +
				      StringUtils.defaultIfEmpty(procedimentoPrincipale.getDescrizione(), ""));
		log.warn("populateMovimenti: Procedimento principale non mappato. Codice: {}, Descrizione: {}", procedimentoPrincipale.getCodice(),
			procedimentoPrincipale.getDescrizione());
		evento.setIstanze(istanza);
		istanzeeventiService.insert(evento);
	    } else {
		Istanzeeventi evento = new Istanzeeventi();
		evento.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_STC_IA);
		evento.setDescrizione("Nessun procedimento principale presente.");
		log.warn("populateMovimenti: Nessun procedimento principale presente.");
		evento.setIstanze(istanza);
		istanzeeventiService.insert(evento);
	    }
	}
	// BOCCI 2015-08-24 IL RESPONSABILE CHE INSERISCE IL MOVIMENTO VIENE IN PRIMA ISTANZA PRESO DALLA CONFIGURAZIONE DELL'ALBERO
	//		NEL CAMPO ALBEROPROC.OPERATORE_STC. SE NON PRESENTE VIENE PRESO IL CAMPO ISTANZA.CODICERESPONSABILE
	Integer codiceResponsabile = istanza.getResponsabile().getId().getCodice();
	Alberoproc ap = istanza.getAlberoproc();
	if (ap != null && ap.getId() != null && ap.getId().getCodice() != null) {
	    AlberoprocHelper s = alberoprocService.findAlberoprocHelper(ap);
	    if (s != null) {
		Responsabili ope = s.getOperatoreStc();
		if (ope != null && ope.getId() != null && ope.getId().getCodice() != null) {
		    codiceResponsabile = ope.getId().getCodice();
		}
	    }
	}
	mov.getResponsabile().getId().setCodice(codiceResponsabile);
	mov.setParere(attivita.getParere());
	mov.setNote(attivita.getNote());
	boolean associaAmmStc = true;
	associaAmmStc = !(isPecOpRoto || isAreaRiservata(mitt));
	if (StringUtils.isNotBlank(codiceAmministrazione)) {
	    log.debug("populateMovimenti: Setto l'amministrazione del movimento {}.", codiceAmministrazione);
	    try {
		Integer codiceAmm = Integer.valueOf(codiceAmministrazione);
		mov.getAmministrazioni().getId().setCodice(codiceAmm);
		if (associaAmmStc) {
		    mov.getAmministrazioniStc().getId().setCodice(codiceAmm);
		}
	    } catch (NumberFormatException e) {
		log.error("populateMovimenti: Il valore del codiceAmministrazione non è nel formato corretto.");
		throw new RuntimeException("Errore, il valore del codiceAmministrazione non è corretto: '" + codiceAmministrazione + "'");
	    }
	}
	mov.setEsito(Boolean.FALSE);
	if (attivita.isEsito()) {
	    mov.setEsito(Boolean.TRUE);
	}
	List<DocumentiType> listaDocumenti = attivita.getDocumenti();
	boolean isNodoInterno = isChiamataDaNodoInterno(request.getSportelloDestinatario(), request.getSportelloMittente(), false);
	if (listaDocumenti != null) {
	    for (DocumentiType documentiType : listaDocumenti) {
		Movimentiallegati allegato = new Movimentiallegati();
		allegato.setDescrizione(documentiType.getDocumento());
		// Ad oggi inserisce la data di sistema, sostituira con qiella in documento tipy in caso sia vuota lasciare il comportamento 
		// presnete ora
		if (documentiType.getData() != null) {
		    Date dataReg = Utilities.getDate(documentiType.getData());
		    allegato.setDataregistrazione(dataReg);
		} else {
		    allegato.setDataregistrazione(oggi.getTime());
		}
		allegato.setStcIddocumento(documentiType.getId());
		allegato.setNote(documentiType.getAnnotazioni());
		if (documentiType.getAllegati() != null) {
		    Integer valoreControlloOk = getValoreControlloOk(attivita.getAltriDati(), String.valueOf(documentiType.getAllegati().getId()),
			    NlaHelperService.ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_MOVIMENTO);
		    allegato.setControllook(valoreControlloOk);
		    if (StringUtils.isNotBlank(documentiType.getAllegati().getId())) {
			allegato.setStcIdallegato(documentiType.getAllegati().getId());
		    } else {
			allegato.setStcIdallegato(documentiType.getId());
		    }
		    Oggetti ogg = getOggettoFromDocumentiType(documentiType, isNodoInterno, request.getSportelloMittente(),
			    request.getSportelloDestinatario(), attivita.getIdPratica(), request.getToken());
		    allegato.setOggetto(ogg);
		}
		mov.getMovimentiallegatis().add(allegato);
	    }
	}
	mov.setCreatoDaStc(Boolean.TRUE);
	mov.setFlagDaLeggere(Boolean.TRUE);
	if (tipimovimento != null) {
	    if (tipimovimento.getFlagDisdavisionare() != null) {
		if (tipimovimento.getFlagDisdavisionare().booleanValue()) {
		    mov.setFlagDaLeggere(Boolean.FALSE);
		}
	    }
	}
	boolean copiaRifProtocolloInMittente = false;
	if (passaProtocollo) {
	    boolean copiaProtocollo = false;
	    boolean stessoNodoEnte = this.checkIsStessoNodoStessoEnte(request.getSportelloDestinatario(), request.getSportelloMittente());
	    if (stessoNodoEnte) {
		// Il protocollo lo passo se stesso nodo e stesso ente altrimenti mi si passa anche da REGIONE a AUA
		copiaProtocollo = true;
		// DA VERIFICARE EVENTUALI PROBLEMI CON PASSAPROT
	    }
	    // se verticalizzazione lista nodi copia protocollo contiene un nodo mittente configurato allora
	    // vengono passati i riferimenti della protocollazione. Es caso uso NODO INFOCAMERE GENOVA 
	    Verticalizzazioniparametri vpCopiaRifProto = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		    WebConstants.VERTICALIZZAZIONE_STC_LISTANODI_COPIA_PROTOCOLLO);
	    if (!copiaProtocollo && vpCopiaRifProto != null
		    && StringUtils.isNotBlank(StringUtils.defaultString(vpCopiaRifProto.getValore()).trim())) {
		String cercaIn = StringUtils.defaultString(vpCopiaRifProto.getValore()).trim();
		copiaProtocollo = Utilities.verificaPresenzaValoreIn(request.getSportelloMittente().getIdNodo(), cercaIn);
	    }
	    // se verticalizzazione forza_protocollazione allora non vengono passati i riferimenti della protocollazione
	    // e di fatto causa la chiamata al servizio di protocollazione istanza
	    Verticalizzazioniparametri vpForza = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		    WebConstants.VERTICALIZZAZIONE_STC_FORZAPROTOCOLLAZIONE);
	    if (vpForza != null && StringUtils.defaultString(vpForza.getValore(), "N").equalsIgnoreCase("S")) {
		copiaProtocollo = false;
	    }
	    if (copiaProtocollo) {
		mov.setNumeroprotocollo(request.getDatiAttivita().getNumeroProtocolloGenerale());
		if (request.getDatiAttivita().getDataProtocolloGenerale() != null) {
		    mov.setDataprotocollo(request.getDatiAttivita().getDataProtocolloGenerale().toGregorianCalendar().getTime());
		}
		String idprotocollo = "";
		ValoreParametroType vpidp = Utilities.getCampoDaAltriDati(request.getDatiAttivita().getAltriDati(),
			ALTRI_DATI_ISTANZE_MOVIMENTI_FKIDPROTOCOLLO);
		if (vpidp != null && StringUtils.isNotBlank(vpidp.getCodice())) {
		    idprotocollo = vpidp.getCodice();
		    mov.setFkidprotocollo(idprotocollo);
		}
	    } else {
		// siccome effettuo una nuova protocollazione, anche se presenti i riferimenti del protocollo mittente,
		// per non perderli li copio nei riferimenti del mittente.
		copiaRifProtocolloInMittente = true;
	    }
	} else {
	    copiaRifProtocolloInMittente = true;
	}
	if (copiaRifProtocolloInMittente) {
	    // BOCCI 2013-03-18 REDMINE#50 passaprot = false deve memorizzare questi riferimenti in due nuovi campi della tabella MOVIMENTI (NUMPROT_MITTENTE, DATA_PROT_MITTENTE).
	    if (StringUtils.isNotBlank(request.getDatiAttivita().getNumeroProtocolloGenerale())) {
		mov.setNumprotMittente(request.getDatiAttivita().getNumeroProtocolloGenerale());
	    }
	    if (request.getDatiAttivita().getDataProtocolloGenerale() != null) {
		mov.setDataProtMittente(request.getDatiAttivita().getDataProtocolloGenerale().toGregorianCalendar().getTime());
	    }
	}
	copiaMetadatiDaRequest(mov, request.getDatiAttivita().getAltriDati());
	return mov;
    }

    @Override
    public Oggetti getOggettoFromDocumentiType(DocumentiType documentiType, boolean isNodoInterno, SportelloType mittente, SportelloType destinatario,
	    String riferimentoPraticaStc, String tokenStc) {

	Oggetti oggetto = null;
	boolean isCodiceOggettoAltroSistema = isCodiceOggettoAltroSistema(mittente, destinatario);
	if (documentiType.getAllegati() != null) {
	    if (isNodoInterno) {
		// BOCCI BUG 364 se la richiesta viene da nodi interni a SIGEPRO vengono passati direttamente i riferimenti interni agli oggetti
		if (Utilities.isInteger(documentiType.getAllegati().getId())) {
		    oggetto = oggettiService.findById(new PkId(Integer.parseInt(documentiType.getAllegati().getId())));
		    metadatiOggetto(oggetto, documentiType);
		} else if (StringUtils.defaultString(documentiType.getAllegati().getId()).startsWith(CODICEOGGETTO_CONST_ALLEGATI)) {
		    oggetto = getOggettoFromRiferimentoLocale(documentiType.getAllegati().getId());
		    metadatiOggetto(oggetto, documentiType);
		} else if (documentiType.getAllegati().getFile() != null) {
		    oggetto = new Oggetti();
		    oggetto.setNomefile(documentiType.getAllegati().getFile().getFileName());
		    byte[] content = Utilities.dataHandlerToBytes(documentiType.getAllegati().getFile().getBinaryData());
		    oggetto.setOggetto(content);
		    metadatiOggetto(oggetto, documentiType);
		}
	    } else {
		if (documentiType.getAllegati().getFile() != null) {
		    // se c'è il datahandler allora lo recupero da quello
		    oggetto = new Oggetti();
		    oggetto.setNomefile(documentiType.getAllegati().getFile().getFileName());
		    byte[] content = Utilities.dataHandlerToBytes(documentiType.getAllegati().getFile().getBinaryData());
		    oggetto.setOggetto(content);
		    metadatiOggetto(oggetto, documentiType);
		} else {
		    // altrimenti lo cerco o per riferimento
		    oggetto = null;
		    if (isScaricaSubitoAllegatiFisiciPerNodo(mittente)) {
			oggetto = manageAllegatiDaScaricareSubito(documentiType, mittente, destinatario, riferimentoPraticaStc, tokenStc,
				isCodiceOggettoAltroSistema);
		    } else if (StringUtils.defaultString(documentiType.getAllegati().getId()).startsWith(CODICEOGGETTO_CONST_ALLEGATI)) {
			oggetto = getOggettoFromRiferimentoLocale(documentiType.getAllegati().getId());
		    }
		    if (oggetto != null) {
			metadatiOggetto(oggetto, documentiType);
		    }
		}
	    }
	}
	return oggetto;
    }

    private Oggetti manageAllegatiDaScaricareSubito(DocumentiType doc, SportelloType mittente, SportelloType destinatario,
	    String riferimentoPraticaStc, String tokenStc, boolean isCodiceOggettoAltroSistema) {

	Oggetti ogg = null;
	if (doc != null && doc.getAllegati() != null && StringUtils.isNotBlank(doc.getAllegati().getId())) {
	    log.debug("Non è presente il datahandler del doc {}, lo processo chiamando allegato binario", doc.getId());
	    AllegatoBinarioRequest request = new AllegatoBinarioRequest();
	    request.setToken(tokenStc);
	    request.setSportelloMittente(destinatario);
	    request.setSportelloDestinatario(mittente);
	    RiferimentiAllegatoType rif = new RiferimentiAllegatoType();
	    rif.setIdAllegato(doc.getAllegati().getId());
	    rif.setIdDocumento(doc.getId());
	    rif.setIdPratica(riferimentoPraticaStc);
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
					   doc.getAllegati().getId() + " dal sistema remoto identificato da " + mittente);
	    }
	    if (response != null) {
		try {
		    DataHandler dh = response.getBinaryData();
		    if (dh != null) {
			if (isCodiceOggettoAltroSistema) {
			    doc.setId(NlaHelperService.CODICEOGGETTO_CONST_ALLEGATI_CODICE_ALTRO_SISTEMA + doc.getAllegati().getId());
			}
			log.debug("Inserisco gli oggetti di allegato binario per il doc {}", dettaglioRequest);
			ogg = new Oggetti();
			ogg.setNomefile(response.getFileName());
			ogg.setOggetto(Utilities.dataHandlerToBytes(dh));
			oggettiService.insert(ogg);
			log.debug("Oggetti di allegato binario per il doc {} INSERITI", dettaglioRequest);
			doc.getAllegati().setFile(null);
			doc.getAllegati().setId(NlaHelperService.CODICEOGGETTO_CONST_ALLEGATI + ogg.getId().getCodice());
		    }
		} catch (Exception e) {
		    log.error("manageAllegatiDaScaricareSubito# errore nel recupero dell'allegato STC {}-{}: {}",
			    new Object[] { doc.getId(), doc.getAllegati().getId(), e });
		    throw new RuntimeException("non è stato possibile scaricare l'allegato " + doc.getDocumento() + " con id " + doc.getId() + ":" +
					       doc.getAllegati().getId() + " dal sistema remoto identificato da " + mittente);
		}
	    }
	}
	return ogg;
    }

    @Override
    public RichiestaPraticaNLAResponse populateRichiestaPraticaNLAResponse(Istanze istanza, boolean soloLeAttivitaEseguitePubblicate,
	    Set<Integer> soloDocESchedeDiQuestiEndo) {

	RichiestaPraticaNLAResponse response = new RichiestaPraticaNLAResponse();
	DettaglioPraticaVisuraType dettaglioPraticaVisuraType = new DettaglioPraticaVisuraType();
	response.setDettaglioPratica(dettaglioPraticaVisuraType);
	DettaglioPraticaType dettaglioPraticaType = new DettaglioPraticaType();
	GregorianCalendar dataPratica = new GregorianCalendar();
	dataPratica.setTime(istanza.getData());
	dettaglioPraticaType.setDataPratica(Utilities.getXMLGregorianCalendar(dataPratica));
	String orarioData = Utilities.getOrario(dataPratica.getTime());
	dettaglioPraticaType.setOraDataPratica(orarioData);
	dettaglioPraticaType.setIdPratica(istanza.getId().getCodice().toString());
	boolean copiaProtocollo = true;
	if (StringUtils.defaultString(istanza.getNumeroprotocollo(), "-").trim().equalsIgnoreCase("-")) {
	    // PRIMA ERA if (!StringUtils.defaultString(istanza.getNumeroprotocollo(), "-").trim().equalsIgnoreCase("-"))
	    // COMMENTATO PERCHE' ALL'EMPOLESE DA ERRORE NELLA COMUNICAZIONE VERSO VECCHIO AIDA / verificare eventualmente con Livorno che usa il -
	    copiaProtocollo = false;
	}
	if (copiaProtocollo) {
	    dettaglioPraticaType.setNumeroProtocolloGenerale(istanza.getNumeroprotocollo());
	    if (istanza.getDataprotocollo() != null) {
		GregorianCalendar dataProtocollo = new GregorianCalendar();
		dataProtocollo.setTime(istanza.getDataprotocollo());
		dettaglioPraticaType.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(dataProtocollo));
	    }
	}
	if (StringUtils.isNotBlank(istanza.getCodicepraticatel())) {
	    dettaglioPraticaType.setCodicePraticaTelematica(istanza.getCodicepraticatel());
	}
	if (StringUtils.isNotBlank(istanza.getNomeattivita())) {
	    dettaglioPraticaType.setInsegna(istanza.getNomeattivita());
	}
	if (StringUtils.isNotBlank(istanza.getFkidprotocollo())) {
	    ParametroType vpidp = new ParametroType();
	    vpidp.setNome(ALTRI_DATI_ISTANZE_MOVIMENTI_FKIDPROTOCOLLO);
	    ValoreParametroType vp = new ValoreParametroType();
	    vp.setCodice(istanza.getFkidprotocollo());
	    vp.setDescrizione(istanza.getFkidprotocollo());
	    vpidp.getValore().add(vp);
	    dettaglioPraticaType.getAltriDati().add(vpidp);
	}
	if (StringUtils.isNotBlank(istanza.getNaturaEndoPrincipale())) {
	    ParametroType vpidp = new ParametroType();
	    vpidp.setNome(ALTRI_DATI_NATURA_ENDO_PRINCIPALE);
	    ValoreParametroType vp = new ValoreParametroType();
	    vp.setCodice(istanza.getNaturaEndoPrincipale());
	    vp.setDescrizione(istanza.getNaturaEndoPrincipale());
	    vpidp.getValore().add(vp);
	    dettaglioPraticaType.getAltriDati().add(vpidp);
	}
	ComuneType codiceComune = new ComuneType();
	codiceComune.setCodiceCatastale(istanza.getComune().getCf());
	dettaglioPraticaType.setCodiceComune(codiceComune);
	dettaglioPraticaType.setDomicilioElettronico(istanza.getDomicilioElettronico());
	dettaglioPraticaVisuraType.getListaAttivita().addAll(populateListaAttivita(istanza, soloLeAttivitaEseguitePubblicate));
	dettaglioPraticaVisuraType.setStatoPratica(decodeStatoPratica(istanza));
	dettaglioPraticaVisuraType.setStatoIter(decodeStatoIter(istanza));
	String pwd = istanza.getPassword() == null ? "" : istanza.getPassword().trim();
	String pwdMd5 = Utilities.getHashText(pwd, Utilities.ALGORITHM_MD5, true);
	dettaglioPraticaVisuraType.setPasswordMD5(pwdMd5);
	if (!EntityUtils.isNestedPropertyBlank(istanza.getResponsabileProcedimento(), "id.codice")) {
	    dettaglioPraticaVisuraType.setResponsabileProcedimento(istanza.getResponsabileProcedimento().getResponsabile());
	}
	if (!EntityUtils.isNestedPropertyBlank(istanza.getIstruttore(), "id.codice")) {
	    dettaglioPraticaVisuraType.setIstruttorePratica(istanza.getIstruttore().getResponsabile());
	}
	dettaglioPraticaVisuraType.getListaAtti().addAll(populateListaAtti(istanza));
	// TEMPISTICA
	if (istanza.getIstanzeTempistica() != null) {
	    boolean isTempistica = false;
	    TempisticaProcedimentoType tp = new TempisticaProcedimentoType();
	    IstanzeTempistica it = istanza.getIstanzeTempistica();
	    if (it.getDatainizio() != null) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(it.getDatainizio());
		XMLGregorianCalendar xmlcalendar = Utilities.getXMLGregorianCalendar(calendar);
		tp.setDataInizio(xmlcalendar);
		isTempistica = true;
	    }
	    if (it.getDatafineeffettiva() != null) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(it.getDatafineeffettiva());
		XMLGregorianCalendar xmlcalendar = Utilities.getXMLGregorianCalendar(calendar);
		tp.setDataFine(xmlcalendar);
		isTempistica = true;
	    }
	    Integer durataGG = it.getTransientDurataProcedimento();
	    if (durataGG == null) {
		durataGG = it.getTransientDurataStimataProcedimento();
	    }
	    if (durataGG != null) {
		isTempistica = true;
		tp.setDurataGG(durataGG);
	    }
	    if (isTempistica) {
		dettaglioPraticaVisuraType.setTempisticaProcedimento(tp);
	    }
	}
	// settare gli oneri nell'xml
	populateOneriDaIstanza(istanza, dettaglioPraticaType);
	//
	it.gruppoinit.pal.gp.core.domain.Anagrafe professionista = istanza.getProfessionista();
	if (professionista != null) {
	    AnagrafeType _professionista = populateAnagrafeType(professionista);
	    dettaglioPraticaType.setIntermediario(_professionista);
	}
	it.gruppoinit.pal.gp.core.domain.Anagrafe titolareLegale = istanza.getTitolarelegale();
	if (titolareLegale != null) {
	    AnagrafeType aziendaRichiedente = populateAnagrafeType(titolareLegale);
	    if (aziendaRichiedente.getPersonaGiuridica() != null) {
		dettaglioPraticaType.setAziendaRichiedente(aziendaRichiedente.getPersonaGiuridica());
	    }
	}
	Alberoproc intervento = istanza.getAlberoproc();
	InterventoType _intervento = new InterventoType();
	_intervento.setCodice(intervento.getId().getCodice().toString());
	_intervento.setDescrizione(intervento.getVwAlberoproc().getScDescrizione());
	dettaglioPraticaType.setIntervento(_intervento);
	dettaglioPraticaType.setNumeroPratica(istanza.getNumeroistanza());
	dettaglioPraticaType.setOggetto(istanza.getLavori());
	dettaglioPraticaType.setAnnotazioni(istanza.getLavoriestesa());
	it.gruppoinit.pal.gp.core.domain.Anagrafe richiedente = istanza.getRichiedente();
	// 
	// BOCCI 2012-06-11 dopo la modifica sulla non obbligatorietà del richiedente se questo arriva nullo
	//	allora metto come richiedente dell'istanza l'azienda.
	//      Se richiedente è persona giuridica allora non popolo il tag richiedente ma aziendaRichiedente
	// 	e sovrascrivo l'eventuale aziendaRichiedente già presente
	if (richiedente != null) {
	    if (richiedente.getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_GIURIDICA)) {
		log.warn("Il richiedente della pratica è una persona giuridica lo setto nel campo dettaglioPraticaType.setAziendaRichiedente");
		if (dettaglioPraticaType.getAziendaRichiedente() != null && titolareLegale != null) {
		    log.warn(
			    "La pratica ha già definito un'azienda richiedente (ISTANZE.TITOLARELEGALE). La sovrascrivo con l'anagrafica di ISTANZE.CODICERICHIEDENTE");
		    istanzeeventiService
			    .insert("E' stata richiesta la visura dell'istanza e siccome il richiedente [" + richiedente.getDescrizioneRichiedente() +
				    "] è una persona giuridica è stato inviato come azienda richiedente al posto di [" +
				    titolareLegale.getDescrizioneRichiedente() + "] ", IstanzeeventiConstants.CATEGORIA_STC, null, istanza);
		}
		AnagrafeType aziendaRichiedente = populateAnagrafeType(richiedente);
		if (aziendaRichiedente.getPersonaGiuridica() != null) {
		    dettaglioPraticaType.setAziendaRichiedente(aziendaRichiedente.getPersonaGiuridica());
		}
		// Popolo solo il ruolo type se presente, mentre metto a null l'anagrafica
		// Questo perchè in fase di trasferimento di pratica se il richiedende dell'istanza è 
		// l'azienda riuscirò a passare il ruolo se popolato
		RichiedenteType richiedenteType = new RichiedenteType();
		richiedenteType.setAnagrafica(null);
		Tipisoggetto inQualitaDi = istanza.getTipisoggetto();
		if (EntityUtils.getNestedProperty(inQualitaDi, "id.codice") != null) {
		    RuoloType inQualitaDiLcl = new RuoloType();
		    inQualitaDiLcl.setIdRuolo(String.valueOf(inQualitaDi.getId().getCodice()));
		    inQualitaDiLcl.setRuolo(inQualitaDi.getTiposoggetto());
		    richiedenteType.setRuolo(inQualitaDiLcl);
		}
		dettaglioPraticaType.setRichiedente(richiedenteType);
	    } else {
		RichiedenteType richiedenteType = new RichiedenteType();
		AnagrafeType richiedenteLcl = populateAnagrafeType(richiedente);
		richiedenteType.setAnagrafica(richiedenteLcl.getPersonaFisica());
		Tipisoggetto inQualitaDi = istanza.getTipisoggetto();
		if (EntityUtils.getNestedProperty(inQualitaDi, "id.codice") != null) {
		    RuoloType inQualitaDiLcl = new RuoloType();
		    inQualitaDiLcl.setIdRuolo(String.valueOf(inQualitaDi.getId().getCodice()));
		    inQualitaDiLcl.setRuolo(inQualitaDi.getTiposoggetto());
		    richiedenteType.setRuolo(inQualitaDiLcl);
		}
		dettaglioPraticaType.setRichiedente(richiedenteType);
	    }
	}
	List<Istanzestradario> stradario = istanzestradarioService.findByIstanza(istanza.getId().getCodice());
	if (stradario != null && !stradario.isEmpty()) {
	    int i = 0;
	    for (Istanzestradario istanzestradario : stradario) {
		LocalizzazioneNelComuneType loc = new LocalizzazioneNelComuneType();
		loc.setCivico(istanzestradario.getCivico());
		String prefisso = "";
		if (StringUtils.isNotBlank(istanzestradario.getStradario().getPrefisso())) {
		    prefisso = istanzestradario.getStradario().getPrefisso() + " ";
		}
		loc.setDenominazione(prefisso + istanzestradario.getStradario().getDescrizione());
		loc.setEsponente(istanzestradario.getEsponente());
		loc.setId(String.valueOf(istanzestradario.getStradario().getId().getCodice()));
		if (StringUtils.isNotBlank(istanzestradario.getStradario().getCodviario())) {
		    loc.setCodiceViario(istanzestradario.getStradario().getCodviario());
		} else {
		    loc.setCodiceViario(String.valueOf(istanzestradario.getStradario().getId().getCodice()));
		}
		if (!EntityUtils.isNestedPropertyBlank(istanzestradario.getStradariocolore(), "id.codicecolore")) {
		    loc.setColore(istanzestradario.getStradariocolore().getId().getCodicecolore());
		}
		loc.setScala(istanzestradario.getScala());
		loc.setInterno(istanzestradario.getInterno());
		loc.setEsponenteInterno(istanzestradario.getEsponenteinterno());
		loc.setPiano(istanzestradario.getPiano());
		if (StringUtils.isNotBlank(istanzestradario.getKm())) {
		    loc.setKm(istanzestradario.getKm());
		}
		if (StringUtils.isNotBlank(istanzestradario.getFabbricato())) {
		    loc.setFabbricato(istanzestradario.getFabbricato());
		}
		if (StringUtils.isNotBlank(istanzestradario.getFrazione())) {
		    FrazioneType frazione = new FrazioneType();
		    frazione.setDescrizione(istanzestradario.getFrazione());
		    loc.setFrazione(frazione);
		}
		if (StringUtils.isNotBlank(istanzestradario.getCircoscrizione())) {
		    CircoscrizioneType circ = new CircoscrizioneType();
		    circ.setDescrizione(istanzestradario.getCircoscrizione());
		    loc.setCircoscrizione(circ);
		}
		if (StringUtils.isNotBlank(istanzestradario.getQuartiere())) {
		    QuartiereType quart = new QuartiereType();
		    quart.setDescrizione(istanzestradario.getQuartiere());
		    loc.setQuartiere(quart);
		}
		if (StringUtils.isNotBlank(StringUtils.defaultIfEmpty(istanzestradario.getNote(), "").trim())) {
		    ParametroType ns = new ParametroType();
		    ns.setNome(ALTRI_DATI_NOTE_ISTANZESTRADARIO + i);
		    ValoreParametroType vp = new ValoreParametroType();
		    vp.setCodice(istanzestradario.getNote());
		    vp.setDescrizione(vp.getCodice());
		    ns.getValore().add(vp);
		    dettaglioPraticaType.getAltriDati().add(ns);
		}
		if (StringUtils.isNotBlank(StringUtils.defaultIfEmpty(istanzestradario.getCodicecivico(), "").trim())) {
		    ParametroType ns = new ParametroType();
		    ns.setNome(ALTRI_DATI_CODCIVICO_ISTANZESTRADARIO + i);
		    ValoreParametroType vp = new ValoreParametroType();
		    vp.setCodice(istanzestradario.getCodicecivico());
		    vp.setDescrizione(vp.getCodice());
		    ns.getValore().add(vp);
		    dettaglioPraticaType.getAltriDati().add(ns);
		}
		if (StringUtils.isNotBlank(istanzestradario.getUuid())) {
		    loc.setUuid(istanzestradario.getUuid());
		}
		if (istanzestradario.getTipiLocalizzazioni() != null) {
		    TipoLocalizzazioneType tl = new TipoLocalizzazioneType();
		    tl.setCodice(String.valueOf(istanzestradario.getTipiLocalizzazioni().getId().getCodice()));
		    tl.setDescrizione(istanzestradario.getTipiLocalizzazioni().getDescrizione());
		    loc.setTipo(tl);
		}
		if (StringUtils.isNotBlank(istanzestradario.getLongitudine()) || StringUtils.isNotBlank(istanzestradario.getLatitudine())) {
		    CoordinateType coord = new CoordinateType();
		    coord.setLatitudine(istanzestradario.getLatitudine());
		    coord.setLongitudine(istanzestradario.getLongitudine());
		    loc.setCoordinate(coord);
		}
		if (StringUtils.isNotBlank(istanzestradario.getCap())) {
		    loc.setCap(istanzestradario.getCap());
		}
		i++;
		Set<Istanzemappali> mappalis = istanzestradario.getIstanzemappalis();
		for (Istanzemappali istanzemappali : mappalis) {
		    // BOCCI 2012-05-31 BUGZILLA ID 568
		    if (checkPopulateMappale(istanzemappali, istanza.getId().getCodice(), true)) {
			RiferimentoCatastaleType mappale = new RiferimentoCatastaleType();
			mappale.setFoglio(istanzemappali.getFoglio());
			mappale.setParticella(istanzemappali.getParticella());
			mappale.setSub(istanzemappali.getSub());
			if (StringUtils.isNotBlank(istanzemappali.getSezione())) {
			    mappale.setSezione(istanzemappali.getSezione());
			}
			if (StringUtils.isNotBlank(istanzemappali.getUnitaimmob())) {
			    mappale.setUnitaImobiliare(istanzemappali.getUnitaimmob());
			}
			if (istanzemappali.getCatasto().getCodice().equalsIgnoreCase("F")) {
			    mappale.setTipoCatasto(CATASTO_EDILIZIO_URBANO);
			} else {
			    mappale.setTipoCatasto(CATASTO_TERRENI);
			}
			loc.getRiferimentoCatastale().add(mappale);
		    }
		}
		dettaglioPraticaType.getLocalizzazione().add(loc);
	    }
	}
	boolean copyDocumentiPratica = true;
	boolean copySoloRiepilogoPraticaEDocumentiSchedeDinamiche = false;
	if (soloDocESchedeDiQuestiEndo != null && !soloDocESchedeDiQuestiEndo.isEmpty()) {
	    copyDocumentiPratica = false;
	    copySoloRiepilogoPraticaEDocumentiSchedeDinamiche = true; // BOCCI 2020-05-26 COPIO SOLO IL RIEPILOGO DELLA DOMANDA SE SPECIFICO LA LISTA DEGLI ENDO
	}
	//fabrizioc: POPULATE SCHEDE DINAMICHE
	log.debug("populateRichiestaPraticaNLAResponse: populate schede dinamiche");
	Set<Integer> codiciOggettoSchedeDinamiche = populateSchedeDinamiche(dettaglioPraticaType.getSchede(), istanza, soloDocESchedeDiQuestiEndo);
	if (copyDocumentiPratica) {
	    List<DocumentiistanzaDTO> documentiIstanzaSet = documentiistanzaService.findDocumentiistanzaDTOByIstanza(istanza.getId().getCodice());
	    for (DocumentiistanzaDTO documentiistanza : documentiIstanzaSet) {
		DocumentiType documento = new DocumentiType();
		documento.setId(String.valueOf(documentiistanza.getId().getCodice()));
		documento.setDocumento(documentiistanza.getDocumento());
		documento.setAnnotazioni(documentiistanza.getNote());
		addAltroDato(ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_ISTANZA, documentiistanza.getId().getCodice(), documentiistanza.getControllook(),
			dettaglioPraticaType);
		//http: //redmine/redmine/issues/872
		if (documentiistanza.getData() != null) {
		    GregorianCalendar dataIstaAllegato = new GregorianCalendar();
		    dataIstaAllegato.setTime(documentiistanza.getData());
		    XMLGregorianCalendar dataIstaAllegatoXml = Utilities.getXMLGregorianCalendar(dataIstaAllegato);
		    documento.setData(dataIstaAllegatoXml);
		}
		if (null != documentiistanza.getCodiceOggetto()) {
		    AllegatiType allegato = new AllegatiType();
		    allegato.setId(String.valueOf(documentiistanza.getCodiceOggetto()));
		    allegato.setAllegato(documentiistanza.getNomeFile());
		    addMetadatiOggetto(documentiistanza.getCodiceOggetto(), documento, allegato);
		    documento.setAllegati(allegato);
		}
		dettaglioPraticaType.getDocumenti().add(documento);
	    }
	} else {
	    if (copySoloRiepilogoPraticaEDocumentiSchedeDinamiche) {
		List<DocumentiistanzaDTO> documentiIstanzaSet = documentiistanzaService.findDocumentiistanzaDTOByIstanza(istanza.getId().getCodice());
		boolean riepilogoTrovato = false;
		for (DocumentiistanzaDTO documentiistanza : documentiIstanzaSet) {
		    if (null != documentiistanza.getCodiceOggetto()) {
			List<OggettiMetadati> list = oggettiMetadatiService.findByOggetto(documentiistanza.getCodiceOggetto(),
				WebConstants.OGGETTI_METADATI_TIPODOCUMENTO);
			boolean trovato = false;
			if (!riepilogoTrovato) {
			    if (!list.isEmpty()) {
				for (OggettiMetadati omd : list) {
				    if (omd != null && StringUtils.defaultString(omd.getValore())
					    .equalsIgnoreCase(TipoDocumentoType.RIEPILOGO_DOMANDA.value())) {
					trovato = true;
					riepilogoTrovato = true;
					break;
				    }
				}
			    }
			}
			if (!trovato) {
			    log.debug(
				    "populateRichiestaPraticaNLAResponse --> verifico se copiare anche doc schede dinamiche: non è doc riepilogo {},{},{}",
				    new Object[] { BooleanUtils.isTrue(documentiistanza.getFlgDaModelloDinamico()), codiciOggettoSchedeDinamiche,
					    codiciOggettoSchedeDinamiche.contains(documentiistanza.getCodiceOggetto()) });
			    if (BooleanUtils.isTrue(documentiistanza.getFlgDaModelloDinamico()) && codiciOggettoSchedeDinamiche != null
				    && codiciOggettoSchedeDinamiche.contains(documentiistanza.getCodiceOggetto())) {
				trovato = true;
				// devo copiare anche i file delle schede dinamiche ovvero quelle con 
				// FLG_DA_MODELLO_DINAMICO = 1 e trovati nel set dei dati delle schede degli endo selezionati
			    }
			}
			if (trovato) {
			    DocumentiType documento = new DocumentiType();
			    documento.setId(String.valueOf(documentiistanza.getId().getCodice()));
			    documento.setDocumento(documentiistanza.getDocumento());
			    documento.setAnnotazioni(documentiistanza.getNote());
			    addAltroDato(ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_ISTANZA, documentiistanza.getId().getCodice(),
				    documentiistanza.getControllook(), dettaglioPraticaType);
			    //http: //redmine/redmine/issues/872
			    if (documentiistanza.getData() != null) {
				GregorianCalendar dataIstaAllegato = new GregorianCalendar();
				dataIstaAllegato.setTime(documentiistanza.getData());
				XMLGregorianCalendar dataIstaAllegatoXml = Utilities.getXMLGregorianCalendar(dataIstaAllegato);
				documento.setData(dataIstaAllegatoXml);
			    }
			    AllegatiType allegato = new AllegatiType();
			    allegato.setId(String.valueOf(documentiistanza.getCodiceOggetto()));
			    allegato.setAllegato(documentiistanza.getNomeFile());
			    addMetadatiOggetto(documentiistanza.getCodiceOggetto(), documento, allegato);
			    documento.setAllegati(allegato);
			    dettaglioPraticaType.getDocumenti().add(documento);
			}
		    }
		}
	    }
	}
	// //////////////ENDOPROCEDIMENTI/////////////////////////////////////////////////////////////////
	Set<Istanzeprocedimenti> istanzeprocedimentis = istanza.getIstanzeprocedimentis();
	for (Istanzeprocedimenti istanzeprocedimenti : istanzeprocedimentis) {
	    Integer codiceInventario = istanzeprocedimenti.getId().getCodiceinventario();
	    boolean copy = true;
	    if (soloDocESchedeDiQuestiEndo != null && !soloDocESchedeDiQuestiEndo.isEmpty()
		    && !soloDocESchedeDiQuestiEndo.contains(codiceInventario)) {
		copy = false;
	    }
	    if (copy) {
		ProcedimentoType procedimento = new ProcedimentoType();
		procedimento.setCodice(String.valueOf(codiceInventario));
		/*commentato perchè ora si inviano i codici veri, cioè quelli di inventarioprocedimenti che saranno decodificati dall'nla dest con il nuovo metodo
		//Inventarioprocedimenti endo = inventarioprocedimentiService.findById(new PkId(istanzeprocedimenti.getId().getCodiceinventario()));	    
		Set<Inventarioprocedimentipeople> inventarioprocedimentipeoples = endo.getInventarioprocedimentipeoples();
		if (inventarioprocedimentipeoples.size() > 0) {
		String codProcPeople = "";
		for (Inventarioprocedimentipeople inventarioprocedimentipeople : inventarioprocedimentipeoples) {
		    codProcPeople = inventarioprocedimentipeople.getCodProcPeople();
		    break;
		}
		if (StringUtils.isNotBlank(codProcPeople)) {
		    procedimento.setCodice(codProcPeople);
		} else {
		    procedimento.setCodice(String.valueOf(istanzeprocedimenti.getId().getCodiceinventario()));
		}
		} else {
		procedimento.setCodice(String.valueOf(istanzeprocedimenti.getId().getCodiceinventario()));
		}
		*/
		procedimento.setDescrizione(istanzeprocedimenti.getInventarioprocedimenti().getProcedimento());
		if (istanzeprocedimenti.getDataattivazione() != null) {
		    GregorianCalendar gregorianCalendar = new GregorianCalendar();
		    gregorianCalendar.setTime(istanzeprocedimenti.getDataattivazione());
		    XMLGregorianCalendar dataAttivazione = Utilities.getXMLGregorianCalendar(gregorianCalendar);
		    procedimento.setDataAttivazione(dataAttivazione);
		}
		if (BooleanUtils.isTrue(istanzeprocedimenti.getAcquisito())) {
		    EstremiAttoType attoType = new EstremiAttoType();
		    if (StringUtils.isNotBlank(istanzeprocedimenti.getProtNum())) {
			attoType.setRiferimento(istanzeprocedimenti.getProtNum());
			if (istanzeprocedimenti.getProtDel() != null) {
			    GregorianCalendar calendar = new GregorianCalendar();
			    calendar.setTime(istanzeprocedimenti.getProtDel());
			    XMLGregorianCalendar xmlcalendar = Utilities.getXMLGregorianCalendar(calendar);
			    attoType.setData(xmlcalendar);
			    attoType.setTipoAtto(istanzeprocedimenti.getTipoAtto());
			    attoType.setRilasciatoDa(istanzeprocedimenti.getRilasciatoDa());
			    attoType.setNote(istanzeprocedimenti.getNote());
			    procedimento.setEstremiAtto(attoType);
			}
		    }
		}
		List<IstanzeallegatiDTO> istanzeallegatiSet = istanzeallegatiService.findIstanzeallegatiDTOByIstanzaAndEndo(
			istanza.getId().getCodice(), codiceInventario, TipoRicercaDocumentoEnum.RICERCA_TUTTI);
		for (IstanzeallegatiDTO istanzeallegati : istanzeallegatiSet) {
		    if (istanzeallegati.getCodiceOggetto() != null) {
			DocumentiType documento = new DocumentiType();
			documento.setId(ISTANZE_ALLEGATI_PREFIX + String.valueOf(istanzeallegati.getCodiceOggetto()));
			documento.setDocumento(istanzeallegati.getAllegatoextra());
			documento.setAnnotazioni(istanzeallegati.getNote());
			addAltroDato(ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_ENDO, istanzeallegati.getId().getCodice(), istanzeallegati.getControllook(),
				dettaglioPraticaType);
			if (null != istanzeallegati.getCodiceOggetto()) {
			    AllegatiType allegato = new AllegatiType();
			    allegato.setId(String.valueOf(istanzeallegati.getCodiceOggetto()));
			    allegato.setAllegato(istanzeallegati.getNomeFile());
			    addMetadatiOggetto(istanzeallegati.getCodiceOggetto(), documento, allegato);
			    documento.setAllegati(allegato);
			}
			procedimento.getDocumenti().add(documento);
		    }
		}
		dettaglioPraticaType.getProcedimenti().add(procedimento);
	    }
	}
	Set<Istanzerichiedenti> istanzerichiedentis = istanza.getIstanzerichiedentis();
	for (Istanzerichiedenti istanzerichiedenti : istanzerichiedentis) {
	    AltriSoggettiType altroSoggetto = new AltriSoggettiType();
	    AnagrafeType anagrafe = populateAnagrafeType(istanzerichiedenti.getRichiedente());
	    altroSoggetto.setSoggetto(anagrafe);
	    if (EntityUtils.getNestedProperty(istanzerichiedenti.getAnagrafeCollegata(), "id.codice") != null) {
		AnagrafeType anagrafeCollegata = populateAnagrafeType(istanzerichiedenti.getAnagrafeCollegata());
		altroSoggetto.setAnagraficaCollegata(anagrafeCollegata);
	    }
	    RuoloType ruolo = new RuoloType();
	    ruolo.setIdRuolo(String.valueOf(istanzerichiedenti.getTiposoggetto().getId().getCodice()));
	    ruolo.setRuolo(istanzerichiedenti.getTiposoggetto().getTiposoggetto());
	    altroSoggetto.setTipoRapporto(ruolo);
	    dettaglioPraticaType.getAltriSoggetti().add(altroSoggetto);
	}
	// POPULATE PROCURE
	List<Istanzeprocure> istanzeprocures = istanzeprocureService.findByIstanza(istanza.getId().getCodice());
	for (Istanzeprocure istanzeprocure : istanzeprocures) {
	    String cfProcuratore = (String) EntityUtils.getNestedProperty(istanzeprocure.getAnagrafeProcuratore(), "codicefiscale");
	    String cfRappresentato = (String) EntityUtils.getNestedProperty(istanzeprocure.getAnagrafeRappresentato(), "codicefiscale");
	    if (StringUtils.isBlank(cfProcuratore)) {
		throw new RuntimeException("Errore nel popolamento del tag procure. Il codice Fiscale del procuratore è vuoto");
	    }
	    if (StringUtils.isBlank(cfRappresentato)) {
		throw new RuntimeException("Errore nel popolamento del tag procure. Il codice Fiscale del rappresentato è vuoto");
	    }
	    ProcuraType procura = new ProcuraType();
	    procura.setCfProcuratore(cfProcuratore);
	    procura.setCfRappresentato(cfRappresentato);
	    if (EntityUtils.getNestedProperty(istanzeprocure.getOggetti(), "id.codice") != null) {
		Oggetti oggettoProcura = oggettiService.findByIdLazy(new PkId(istanzeprocure.getOggetti().getId().getCodice()));
		DocumentiType doc = new DocumentiType();
		doc.setId(String.valueOf(oggettoProcura.getId().getCodice()));
		doc.setDocumento(oggettoProcura.getNomefile());
		addAltroDato(ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_PROCURE, oggettoProcura.getId().getCodice(), istanzeprocure.getControllook(),
			dettaglioPraticaType);
		AllegatiType allegato = new AllegatiType();
		allegato.setId(String.valueOf(oggettoProcura.getId().getCodice()));
		allegato.setAllegato(oggettoProcura.getNomefile());
		addMetadatiOggetto(istanzeprocure.getOggetti().getId().getCodice(), doc, allegato);
		doc.setAllegati(allegato);
		doc.setTipoDocumento(TipoDocumentoType.PROCURA.value());
		procura.setProcura(doc);
	    }
	    dettaglioPraticaType.getProcure().add(procura);
	    // } else {
	    // // DA XSD NON E' OBBLIGATORIO E NON DEVO RILANCIARE ERRORE
	    //	throw new RuntimeException("Errore nel popolamento del tag procure. Il file della procura del procuratore è vuoto");
	    // }
	}
	// altri dati
	// setto il codice accreditamento utilizzato da nla-enti ed nla-pec per l'invio mail
	Configurazione confSportello = configurazioneService.findById(new ConfigurazioneId());
	String codAcc = null;
	if (confSportello != null) {
	    codAcc = confSportello.getCodiceaccreditamento();
	    if (StringUtils.isNotBlank(codAcc)) {
		ParametroType p = new ParametroType();
		p.setNome("IDENTIFICATIVO_SUAP");
		ValoreParametroType vp = new ValoreParametroType();
		vp.setCodice(codAcc);
		p.getValore().add(vp);
		dettaglioPraticaType.getAltriDati().add(p);
	    }
	}
	if (StringUtils.isNotBlank(codAcc)) {
	    dettaglioPraticaType.setCodiceAccreditamentoSUAP(codAcc);
	}
	// Popolo la sezione altri dati con un nuovo ParametroType con :
	// nome		: $ISTANZA_NOMEATTIVITA$
	// codice	: istanza.getNomeattivita()
	// descrizione	: istanza.getNomeattivita()
	if (EntityUtils.getNestedProperty(istanza, "id.codice") != null && StringUtils.isNotBlank(istanza.getNomeattivita())) {
	    log.debug("Aggiungo una nuovo ParametroType alla sezione altri dati contenuta in DettaglioPraticaType");
	    setCampoAltriDati(dettaglioPraticaType, istanza.getNomeattivita(), ALTRI_DATI_DENOMINAZIONE_ATTIVITA);
	}
	//
	log.debug("Setto l'oggetto DettaglioPraticaType creato alla response");
	response.getDettaglioPratica().setDettaglioPratica(dettaglioPraticaType);
	return response;
    }

    @Override
    public void addAltroDato(String chiaveAltroDato, Integer codice, Integer controllook, DettaglioPraticaType dettaglioPraticaType) {

	List<ParametroType> altriDati = dettaglioPraticaType.getAltriDati();
	if (altriDati != null) {
	    ParametroType param = null;
	    for (ParametroType pt : altriDati) {
		if (pt.getNome() != null && pt.getNome().equals(chiaveAltroDato)) {
		    param = pt;
		    break;
		}
	    }
	    if (param == null) {
		param = new ParametroType();
		param.setNome(chiaveAltroDato);
		dettaglioPraticaType.getAltriDati().add(param);
	    }
	    ValoreParametroType vpt = new ValoreParametroType();
	    vpt.setCodice(String.valueOf(codice));
	    vpt.setDescrizione(String.valueOf(controllook));
	    param.getValore().add(vpt);
	}
    }

    @Override
    public void addAltroDato(String chiaveAltroDato, Integer codice, Integer controllook, DettaglioAttivitaType dettaglioAttivitaType) {

	List<ParametroType> altriDati = dettaglioAttivitaType.getAltriDati();
	if (altriDati != null) {
	    ParametroType param = null;
	    for (ParametroType pt : altriDati) {
		if (pt.getNome() != null && pt.getNome().equals(chiaveAltroDato)) {
		    param = pt;
		    break;
		}
	    }
	    if (param == null) {
		param = new ParametroType();
		param.setNome(chiaveAltroDato);
		dettaglioAttivitaType.getAltriDati().add(param);
	    }
	    ValoreParametroType vpt = new ValoreParametroType();
	    vpt.setCodice(String.valueOf(codice));
	    vpt.setDescrizione(String.valueOf(controllook));
	    param.getValore().add(vpt);
	}
    }

    /**
     * Ritorna i riferimenti a codicioggetto dei campi upload delle schede dinamiche per recuperare i documenti salvati
     * si schede dinamiche
     * 
     * @param schede
     * @param istanza
     * @param soloDocESchedeDiQuestiEndo
     * @return
     */
    private Set<Integer> populateSchedeDinamiche(List<SchedaType> schede, Istanze istanza, Set<Integer> soloDocESchedeDiQuestiEndo) {

	Set<Integer> codiciOggetto = new HashSet<Integer>();
	if (schede == null) {
	    schede = new ArrayList<SchedaType>();
	}
	Set<Integer> schedeDaCopiare = new HashSet<Integer>();
	if (soloDocESchedeDiQuestiEndo != null && soloDocESchedeDiQuestiEndo.size() > 0) {
	    for (Integer codiceInventario : soloDocESchedeDiQuestiEndo) {
		List<Inventarioprocdyn2modellit> list = inventarioprocdyn2modellitService.findByInventarioprocedimento(codiceInventario);
		for (Inventarioprocdyn2modellit ipd2mt : list) {
		    schedeDaCopiare.add(ipd2mt.getId().getFkD2mtId());
		}
	    }
	}
	List<Istanzedyn2modellit> listaSchede = istanzedyn2modellitService.findByIstanza(istanza.getId());
	SchedaType schedaType;
	List<Istanzedyn2dati> listaCampiScheda;
	for (Istanzedyn2modellit scheda : listaSchede) {
	    Integer codiceScheda = scheda.getDyn2Modellit().getId().getCodice();
	    boolean copy = true;
	    if (schedeDaCopiare != null && schedeDaCopiare.size() > 0) {
		if (!schedeDaCopiare.contains(codiceScheda)) {
		    copy = false;
		}
	    }
	    if (copy) {
		listaCampiScheda = istanzedyn2datiService.findByIstanzaAndModello(istanza.getId(), scheda.getDyn2Modellit().getId());
		schedaType = new SchedaType();
		schedaType.setCodice(scheda.getDyn2Modellit().getId().getCodice().toString());
		schedaType.setNome(scheda.getDyn2Modellit().getCodiceScheda());
		schedaType.setDescrizione(scheda.getDyn2Modellit().getDescrizione());
		CampoSchedaType cst;
		CampoDinamicoType cdt;
		ValoreCampoDinamicoType valore;
		ElementoValoreCampoDinamicoType elValore;
		Set<Integer> campiGestiti = new HashSet<Integer>();
		for (Istanzedyn2dati istanzedyn2dati : listaCampiScheda) {
		    Integer codiceCampo = istanzedyn2dati.getId().getFkD2cId();
		    if (!campiGestiti.contains(codiceCampo)) {
			boolean isUpload = false;
			cst = new CampoSchedaType();
			cst.setCodice(istanzedyn2dati.getId().getFkD2cId().toString());
			cst.setDescrizione(istanzedyn2dati.getDyn2Campi().getNomecampo());
			if (Dyn2ModellitService.TipoControlloEnum.Upload.name().equalsIgnoreCase(istanzedyn2dati.getDyn2Campi().getTipodato())) {
			    isUpload = true;
			}
			cdt = new CampoDinamicoType();
			valore = new ValoreCampoDinamicoType();
			valore.setNome(istanzedyn2dati.getId().getFkD2cId().toString());
			List<Istanzedyn2datiDTO> s = istanzedyn2datiService.findDTOByIstanzaAndDyn2Campi(istanza.getId().getCodice(),
				istanzedyn2dati.getId().getFkD2cId());
			for (Istanzedyn2datiDTO idto : s) {
			    elValore = new ElementoValoreCampoDinamicoType();
			    if (isUpload && idto.getValore() != null && Utilities.isInteger(idto.getValore())) {
				codiciOggetto.add(Integer.parseInt(idto.getValore().trim()));
			    }
			    elValore.setCodice(idto.getValore());
			    elValore.setDescrizione(idto.getValoredecodificato());
			    elValore.setIndice(idto.getId().getIndice());
			    elValore.setIndiceMolteplicita(idto.getId().getIndiceMolteplicita());
			    valore.getValore().add(elValore);
			}
			cdt.setValoreUtente(valore);
			cst.setCampoDinamico(cdt);
			schedaType.getCampi().add(cst);
			campiGestiti.add(codiceCampo);
		    }
		}
		schede.add(schedaType);
	    }
	}
	return codiciOggetto;
    }

    /**
     * <pre>
     * Il metodo permette di aggiungere un nuovo valore nella sezione altri dati.E' possibile con il metodo aggiungere un nuovo valore nella 
     * sezione altri dati passando:
     *   1- l'oggetto DettaglioPraticaType che contiene la sezione altri dati 
     *   2- un valore stringa che rappresenta il valore che voglia passare
     *   3- un valore stringa che rappresenta il nome del ParametroType che stiamo aggiungendo e ci permetterà di recuperarlo in inserimento
     * 
     * &#64;param dettaglioPraticaType : oggetto che contiene la sezione altri dati a cui verrà aggiunto l'oggetto ParametroType
     * &#64;param value		   : valore che verrà associato a un campo di altri dati ValoreParametroType.descrizione ValoreParametroType.codice	
     * &#64;param nomeParametroType    : nome del parametro ParametroType.nome utilizzato per recuperare il dato.
     * 
     * </pre>
     */
    private void setCampoAltriDati(DettaglioPraticaType dettaglioPraticaType, String value, String nomeParametroType) {

	// Creao l'oggetto ParametroType
	ParametroType p = new ParametroType();
	// Il nome, sarà il tag con ciu lo riconosceremo in inserimento pratica
	log.debug("Creo il nuovo ParametroType con nome {}", nomeParametroType);
	p.setNome(nomeParametroType);
	// creo l'oggetto ValoreParametroType 
	ValoreParametroType vp = new ValoreParametroType();
	// Setto codice e descrizione con il valore passato
	log.debug("Creo il nuovo ValoreParametroType con codice {} e descrizione {}", value, value);
	vp.setCodice(value);
	vp.setDescrizione(value);
	// setto ValoreParametroType a ParametroType
	log.debug("Setto ValoreParametroType al ParametroType creato");
	p.getValore().add(vp);
	//Aggiungo alla sezione di altri dati il nuovo ParametroType
	log.debug("Aggiungo il nuovo ParametroType creato alla sezione altri dati");
	dettaglioPraticaType.getAltriDati().add(p);
    }

    /**
     * BOCCI 2012-05-31 BUGZILLA ID 568: Se nella riga di ISTANZEMAPPALI non sono presenti i dati FOGLIO, PARTICELLA e
     * TIPOCATASTO allora, durante la richiesta pratica response, non va compilato l'elemento
     * LocalozzazioneNelComuneType.riferimentoCatastale e va messo un evento,se la variabile
     * insertEvento==true(ISTANZEVENTI). Descrizione evento: "E' stata richiesta la visura dell'istanza e non sono stati
     * inviati i riferimenti catastali in quanto non completi. I dati minimi sono tipo catasto,foglio e particella.
     * [dettaglio: via xx, yy Sezione, foglio, particella, sub]"
     * 
     * @param istanzemappali
     * @param istanza
     * @return
     */
    private boolean checkPopulateMappale(Istanzemappali istanzemappali, Integer codiceIstanza, boolean insertEvento) {

	if (istanzemappali == null) {
	    return false;
	}
	boolean isFoglio = StringUtils.isNotBlank(istanzemappali.getFoglio());
	boolean isParticella = StringUtils.isNotBlank(istanzemappali.getParticella());
	boolean isCatasto = false;
	if (istanzemappali.getCatasto() != null && StringUtils.isNotBlank(istanzemappali.getCatasto().getCodice())) {
	    isCatasto = true;
	}
	if (isCatasto && isParticella && isFoglio) {
	    return true;
	}
	if (insertEvento) {
	    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	    Istanzestradario iststr = istanzestradarioService.findById(new PkId(istanzemappali.getIstanzestradario().getId().getCodice()));
	    String descrizioneStradario = iststr.getStradario().getDescrizioneCompleta();
	    String messaggio = "#VISURA ISTANZA WARNING#E' stata richiesta la visura dell'istanza " + istanza +
			       " e non sono stati inviati i riferimenti catastali in quanto non completi. " +
			       "I dati minimi sono  tipo catasto,foglio e particella. [dettaglio: " + descrizioneStradario + ", sezione: " +
			       StringUtils.defaultIfEmpty(istanzemappali.getSezione(), "") + ", foglio: " +
			       StringUtils.defaultIfEmpty(istanzemappali.getFoglio(), "") + ", particella: " +
			       StringUtils.defaultIfEmpty(istanzemappali.getParticella(), "") + ", sub: " +
			       StringUtils.defaultIfEmpty(istanzemappali.getSub(), "") + "]";
	    LoggerModificheIstanze.log(messaggio);
	}
	return false;
    }

    private void populateOneriDaIstanza(Istanze istanza, DettaglioPraticaType dettaglioPraticaType) {

	List<Istanzeoneri> oneris = istanzeoneriService.findByIstanza(istanza.getId().getCodice());
	for (Istanzeoneri istanzeoneri : oneris) {
	    OneriType onere = new OneriType();
	    if (istanzeoneri.getTipicausalioneri() != null) {
		if (istanzeoneri.getTipicausalioneri().getId() != null) {
		    if (istanzeoneri.getTipicausalioneri().getId().getCodice() != null) {
			CausaleOnereType causale = new CausaleOnereType();
			causale.setId(String.valueOf(istanzeoneri.getTipicausalioneri().getId().getCodice()));
			causale.setCausale(istanzeoneri.getTipicausalioneri().getCoDescrizione());
			onere.setCausale(causale);
		    }
		}
	    }
	    if (istanzeoneri.getPrezzo() != null) {
		onere.setImporto(istanzeoneri.getPrezzo().doubleValue());
	    }
	    if (StringUtils.isNotBlank(istanzeoneri.getNote())) {
		onere.setAnnotazioni(istanzeoneri.getNote());
	    }
	    if (BooleanUtils.isTrue(istanzeoneri.getFlentratauscita())) {
		onere.setSegno(SegnoType.ENTRATA);
	    } else {
		onere.setSegno(SegnoType.USCITA);
	    }
	    onere.setNonDovuto(istanzeoneri.getFlagNondovuto());
	    if (istanzeoneri.getInventarioprocedimenti() != null) {
		if (istanzeoneri.getInventarioprocedimenti().getId() != null) {
		    if (istanzeoneri.getInventarioprocedimenti().getId().getCodice() != null) {
			onere.setCodiceProcedimento(String.valueOf(istanzeoneri.getInventarioprocedimenti().getId().getCodice()));
		    }
		}
	    }
	    // popolare oneriscadenze
	    // ..
	    if (istanzeoneri.getPrezzo() != null) {
		OneriScadenzeType scadenza = new OneriScadenzeType();
		if (istanzeoneri.getDatascadenza() != null) {
		    GregorianCalendar c = new GregorianCalendar();
		    c.setTime(istanzeoneri.getDatascadenza());
		    scadenza.setDataScadenza(Utilities.getXMLGregorianCalendar(c));
		}
		if (istanzeoneri.getNumerorata() != null) {
		    // FIXME il numero rata va messo sempre a 1 o va utilizzato il numerorata di istanze oneri?
		    scadenza.setNumeroRata(String.valueOf(istanzeoneri.getNumerorata()));
		}
		scadenza.setImportoRata(istanzeoneri.getPrezzo().doubleValue());
		// POPOLARE ONERI PAGAMENTI
		// ..
		if (istanzeoneri.getDatapagamento() != null && istanzeoneri.getImportopagato() != null) {
		    OneriPagamentiType pagamento = new OneriPagamentiType();
		    // data pagamento
		    if (istanzeoneri.getDatapagamento() != null) {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(istanzeoneri.getDatapagamento());
			pagamento.setData(Utilities.getXMLGregorianCalendar(c));
		    }
		    // importo pagato
		    if (istanzeoneri.getImportopagato() != null) {
			pagamento.setImporto(istanzeoneri.getImportopagato().doubleValue());
		    }
		    // modalità
		    if (istanzeoneri.getTipimodalitapagamento() != null) {
			pagamento.setModalita(istanzeoneri.getTipimodalitapagamento().getMpDescrestesa());
		    }
		    // rifDocumento
		    pagamento.setRifDocumento(istanzeoneri.getDocriferimento());
		    //
		    scadenza.getPagamenti().add(pagamento);
		}
		onere.getScadenze().add(scadenza);
	    }
	    dettaglioPraticaType.getOneri().add(onere);
	}
    }

    /**
     * 
     * @param list
     * @return
     */
    private ProcedimentoType getProcedimentoPrincipale(List<ProcedimentoType> list) {

	ProcedimentoType pt = null;
	if (list != null) {
	    if (list.size() == 1) {
		// BOCCI 17/03/2022 SE MI ARRIVA UN SOLO PROCEDIMENTO ALLORA CONSIDERO PRINCIPALE QUELLO PER BUGFIXING CON
		// NLA-INFOCAMERE CHE CREA LA PRATICA SENZA ENDO MA POI INVIA LE INTEGRAZIONI PER ENDO
		return list.get(0);
	    }
	    for (ProcedimentoType procedimentoType : list) {
		if (BooleanUtils.isTrue(procedimentoType.isPrincipale())) {
		    pt = procedimentoType;
		    break;
		}
	    }
	}
	return pt;
    }

    public StatoPraticaType decodeStatoPratica(Istanze istanza) {

	StatoPraticaType statoP = null;
	Integer codComp = istanza.getChiusura().getStaticomportamento().getCodcomportamento();
	switch (codComp) {
	case -1:
	    statoP = StatoPraticaType.CHIUSA_NEGATIVAMENTE;
	    break;
	case 0:
	    statoP = StatoPraticaType.ATTIVA;
	    break;
	case 1:
	    statoP = StatoPraticaType.CHIUSA_POSITIVAMENTE;
	    break;
	default:
	    break;
	}
	return statoP;
    }

    @Override
    public StatoPraticaType decodeStatoPratica(Statiistanza statiistanza) {

	StatoPraticaType statoP = null;
	Integer codComp = statiistanza.getStaticomportamento().getCodcomportamento();
	switch (codComp) {
	case -1:
	    statoP = StatoPraticaType.CHIUSA_NEGATIVAMENTE;
	    break;
	case 0:
	    statoP = StatoPraticaType.ATTIVA;
	    break;
	case 1:
	    statoP = StatoPraticaType.CHIUSA_POSITIVAMENTE;
	    break;
	default:
	    break;
	}
	return statoP;
    }

    private StatoIterType decodeStatoIter(Istanze istanza) {

	if (!EntityUtils.isNestedPropertyBlank(istanza.getIstanzeTempistica(), "id.codice")) {
	    String statoIter = istanza.getIstanzeTempistica().getStato();
	    if (StringUtils.isNotBlank(statoIter)) {
		if (statoIter.equals(MovimentiTempisticaService.TIPO_EVENTO.I.name())) {
		    return StatoIterType.INTERROTTA;
		}
		if (statoIter.equals(MovimentiTempisticaService.TIPO_EVENTO.S.name())) {
		    return StatoIterType.SOSPESA;
		}
	    }
	}
	return null;
    }

    private List<EstremiAttoEstesoType> populateListaAtti(Istanze istanza) {

	List<EstremiAttoEstesoType> listaAtti = new ArrayList<EstremiAttoEstesoType>();
	IstanzaAutConcHelper istanzaAutConcHelper = autorizzazioniService.findAutEConcESubByIstanza(istanza);
	EstremiAttoEstesoType autAtto = null;
	GregorianCalendar autData = null;
	StringBuffer note = null;
	if (istanzaAutConcHelper.getAutorizzazioni() != null && !istanzaAutConcHelper.getAutorizzazioni().isEmpty()) {
	    for (Autorizzazioni aut : istanzaAutConcHelper.getAutorizzazioni()) {
		autAtto = new EstremiAttoEstesoType();
		autAtto.setNumero(aut.getAutoriznumero());
		autData = new GregorianCalendar();
		autData.setTime(aut.getAutorizdata());
		autAtto.setData(Utilities.getXMLGregorianCalendar(autData));
		autAtto.setTipoRegistro(aut.getTipologiaregistro().getTrDescrizione());
		note = new StringBuffer();
		note.append("AUTORIZZAZIONE");
		note.append(", rilasciata da: ").append(aut.getAutorizresponsabile());
		note.append(", nel Comune di: ").append(aut.getAutorizcomune().getComune());
		if (aut.getDataCessazione() != null) {
		    note.append(", Cessata il: ").append(Utilities.formatDate(aut.getDataCessazione(), false));
		} else {
		    if (aut.getDatascadenza() != null) {
			note.append(", Scadenza: ").append(Utilities.formatDate(aut.getDatascadenza(), false));
		    }
		}
		autAtto.setNote(note.toString());
		listaAtti.add(autAtto);
	    }
	}
	if (istanzaAutConcHelper.getConcessioni() != null && !istanzaAutConcHelper.getConcessioni().isEmpty()) {
	    for (AutorizzazioniConcessioni conc : istanzaAutConcHelper.getConcessioni()) {
		autAtto = new EstremiAttoEstesoType();
		autAtto.setNumero(conc.getAutorizzazioniByFkAutconcAutatt().getAutoriznumero());
		autData = new GregorianCalendar();
		autData.setTime(conc.getAutorizzazioniByFkAutconcAutatt().getAutorizdata());
		autAtto.setData(Utilities.getXMLGregorianCalendar(autData));
		autAtto.setTipoRegistro(conc.getAutorizzazioniByFkAutconcAutatt().getTipologiaregistro().getTrDescrizione());
		note = new StringBuffer();
		note.append("CONCESSIONE");
		note.append(",").append("Rilasciata da: ").append(conc.getAutorizzazioniByFkAutconcAutatt().getAutorizresponsabile());
		note.append(",").append("Dal Comune di: ").append(conc.getAutorizzazioniByFkAutconcAutatt().getAutorizcomune().getComune());
		if (conc.getAutorizzazioniByFkAutconcAutatt().getDataCessazione() != null) {
		    note.append(",").append("Cessata il: ")
			    .append(Utilities.formatDate(conc.getAutorizzazioniByFkAutconcAutatt().getDataCessazione(), false));
		} else {
		    if (conc.getAutorizzazioniByFkAutconcAutatt().getDatascadenza() != null) {
			note.append(",").append("Scadenza: ")
				.append(Utilities.formatDate(conc.getAutorizzazioniByFkAutconcAutatt().getDatascadenza(), false));
		    }
		}
		note.append(",").append("Manifestazione\\Giorno\\Posteggio: ").append(conc.getMercati().getDescrizione()).append("\\")
			.append(conc.getMercatiUso().getDescrizione()).append("\\").append(conc.getMercatiD().getCodiceposteggio());
		autAtto.setNote(note.toString());
		listaAtti.add(autAtto);
	    }
	}
	return listaAtti;
    }

    private List<DettaglioAttivitaType> populateListaAttivita(Istanze istanza, boolean soloLeAttivitaEseguitePubblicate) {

	List<DettaglioAttivitaType> listaAttivita = new ArrayList<DettaglioAttivitaType>();
	List<Movimenti> movEffettuati = movimentiNoSecurityService.findEseguitiByIstanza(istanza);
	if (!movEffettuati.isEmpty()) {
	    DettaglioAttivitaType att = null;
	    for (Movimenti mov : movEffettuati) {
		// BOCCI 2012-08-31 NON FACCIO VISUALIZZARE I MOVIMENTI CON MOVIMENTI.PUBBLICA==FALSE  		
		if (soloLeAttivitaEseguitePubblicate) { // Verifico anche se pubblicate
		    if (BooleanUtils.isTrue(mov.getPubblica())) {
			att = populateDettaglioAttivita(mov);
			listaAttivita.add(att);
		    }
		} else { // Il client chiede tutte le attività (es. nodo interno)
		    att = populateDettaglioAttivita(mov);
		    listaAttivita.add(att);
		}
	    }
	}
	// BOCCI 2012-08-31 NON FACCIO VISUALIZZARE I MOVIMENTI DA EFFETTUARE
	//	List<Movimenti> movDaEseguire = movimentiService.findDaEseguireByIstanza(istanza);
	//	if (!movDaEseguire.isEmpty()) {
	//	    DettaglioAttivitaType att = null;
	//	    for (Movimenti mov : movDaEseguire) {
	//		att = populateDettaglioAttivita(mov, false);
	//		listaAttivita.add(att);
	//	    }
	//	}
	return listaAttivita;
    }

    private DettaglioAttivitaType populateDettaglioAttivita(Movimenti mov) {

	DettaglioAttivitaType att = null;
	GregorianCalendar dataAttivita = null;
	GregorianCalendar dataScadenza = null;
	GregorianCalendar dataProtocollo = null;
	TipoAttivitaType tipoAttivita = null;
	ProcedimentoType endo = null;
	att = new DettaglioAttivitaType();
	att.setIdPratica(mov.getIstanza().getId().getCodice().toString());
	att.setIdAttivita(mov.getId().getCodice().toString());
	if (mov.getData() != null) {
	    dataAttivita = new GregorianCalendar();
	    dataAttivita.setTime(mov.getData());
	    att.setDataAttivita(Utilities.getXMLGregorianCalendar(dataAttivita));
	    // ORA-GIANPAOLO
	    String oraData = Utilities.getOrario(mov.getData());
	    att.setOraDataAttivita(oraData);
	}
	if (mov.getDataScadenza() != null) {
	    dataScadenza = new GregorianCalendar();
	    dataScadenza.setTime(mov.getDataScadenza());
	    att.setDataScadenza(Utilities.getXMLGregorianCalendar(dataScadenza));
	}
	att.setNumeroProtocolloGenerale(mov.getNumeroprotocollo());
	if (mov.getDataprotocollo() != null) {
	    dataProtocollo = new GregorianCalendar();
	    dataProtocollo.setTime(mov.getDataprotocollo());
	    att.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(dataProtocollo));
	}
	tipoAttivita = new TipoAttivitaType();
	// di default vengono inviati come codice tipoattività e descrizione tipo attività il tipomovimento del movimento che si esegue
	tipoAttivita.setCodice(mov.getTipomovimento().getId().getTipomovimento());
	tipoAttivita.setDescrizione(mov.getTipomovimento().getMovimento());
	att.setTipoAttivita(tipoAttivita);
	if (mov.getData() != null) {
	    // BOCCI 2012-08-31 se il movimento è da effettuare visualizzo l'esito
	    att.setEsito(BooleanUtils.toBoolean(mov.getEsito()));
	}
	// BOCCI 2012-08-31 NON FACCIO VISUALIZZARE IL PARERE CON MOVIMENTI.PUBBLICAPARERE==FALSE  
	if (BooleanUtils.isTrue(mov.getPubblicaparere())) {
	    att.setParere(mov.getParere());
	}
	att.setNote(mov.getNote());
	if (!EntityUtils.isNestedPropertyBlank(mov.getEndoprocedimento(), "id.codice")) {
	    endo = new ProcedimentoType();
	    endo.setCodice(mov.getEndoprocedimento().getId().getCodice().toString());
	    endo.setDescrizione(mov.getEndoprocedimento().getProcedimento());
	    endo.setPrincipale(Boolean.TRUE);
	    att.getProcedimenti().add(endo);
	}
	List<Movimentiallegati> allegatis = movimentiallegatiService.findByMovimento(mov.getId().getCodice());
	for (Movimentiallegati movimentiallegati : allegatis) {
	    // BOCCI 2012-08-31 NON FACCIO VISUALIZZARE L'ALLEGATO PARERE CON MOVIMENTIALLEGATI.FLAG_PUBBLICA==FALSE  
	    if (BooleanUtils.isTrue(movimentiallegati.getFlagPubblica())) {
		DocumentiType doc = new DocumentiType();
		doc.setId(String.valueOf(movimentiallegati.getId().getCodice()));
		doc.setDocumento(movimentiallegati.getDescrizione());
		doc.setAnnotazioni(movimentiallegati.getNote());
		if (EntityUtils.getNestedProperty(movimentiallegati.getOggetto(), "id.codice") != null) {
		    AllegatiType allegato = new AllegatiType();
		    allegato.setId(String.valueOf(movimentiallegati.getOggetto().getId().getCodice()));
		    allegato.setAllegato(movimentiallegati.getOggetto().getNomefile());
		    doc.setAllegati(allegato);
		    addMetadatiOggetto(movimentiallegati.getOggetto().getId().getCodice(), doc, allegato);
		}
		att.getDocumenti().add(doc);
	    }
	}
	return att;
    }

    /**
     * recupera da documentiType il valore di TipoDocumento se presente altrimenti null
     * 
     * @param documentiType
     * @return
     */
    private String getTipoDocumento(DocumentiType documentiType) {

	if (documentiType != null) {
	    if (StringUtils.isNotBlank(documentiType.getTipoDocumento())) {
		// if (StringUtils.isNotBlank(documentiType.getTipoDocumento().value())) {
		return documentiType.getTipoDocumento();
		//}
	    }
	}
	return null;
    }

    /**
     * Assegna all'oggetto il metadato WebConstants.OGGETTI_METADATI_TIPODOCUMENTO preso da documentiType
     * 
     * @param oggetto
     * @param doc
     */
    private void metadatiOggetto(Oggetti oggetto, DocumentiType documentiType) {

	String tipoDocumento = getTipoDocumento(documentiType);
	if (StringUtils.isNotBlank(tipoDocumento)) {
	    if (oggetto != null) {
		List<MetadatiBean> metadatis = new ArrayList<MetadatiBean>();
		if (documentiType.getAllegati() != null) {
		    if (documentiType.getAllegati().getMetaDati() != null) {
			List<MetaDatoType> s = documentiType.getAllegati().getMetaDati();
			for (MetaDatoType mdt : s) {
			    MetadatiBean mdb = new MetadatiBean();
			    mdb.setChiave(mdt.getCodice());
			    mdb.setValore(mdt.getValore());
			    metadatis.add(mdb);
			}
		    }
		}
		MetadatiBean md = new MetadatiBean();
		md.setChiave(WebConstants.OGGETTI_METADATI_TIPODOCUMENTO);
		md.setValore(tipoDocumento);
		metadatis.add(md);
		oggetto.setMetadatiTransient(metadatis);
	    }
	}
    }

    /**
     * <ol>
     * <li>recupera il metadato TIPO_DOCUMENTO (se presente) dall'oggetto</li>
     * <li>cerca di assegnare a documentiType il tipo documento se non da errore(l'eccezione non viene rilanciata)</li>
     * </ol>
     * 
     * @param codiceOggetto
     * @param documentoType
     * @param allegato
     *            TODO
     */
    @Override
    public void addMetadatiOggetto(Integer codiceOggetto, DocumentiType documentoType, AllegatiType allegato) {

	// controllo codiceoggetto != null
	// controllo documentiType != null
	if (codiceOggetto != null) {
	    if (documentoType != null) {
		List<OggettiMetadati> mds = oggettiMetadatiService.findByOggetto(codiceOggetto);
		for (OggettiMetadati omd : mds) {
		    // 2 cerca di assegnare a documentiType il tipo documento se non da errore
		    if (StringUtils.isNotBlank(omd.getValore())) {
			if (omd.getId().getChiave().equals(WebConstants.OGGETTI_METADATI_TIPODOCUMENTO)) {
			    try {
				// TipoDocumentoType tdt = TipoDocumentoType.fromValue(omd.getValore());
				documentoType.setTipoDocumento(omd.getValore());
			    } catch (Exception e) {
				log.error("metadaToTipoDocumentoType# errore nella conversione del metadato {} con valore {}, eccezione {}",
					new Object[] { WebConstants.OGGETTI_METADATI_TIPODOCUMENTO, omd.getValore(), e });
			    }
			}
			if (allegato != null) {
			    MetaDatoType md = new MetaDatoType();
			    md.setCodice(omd.getId().getChiave());
			    md.setValore(omd.getValore());
			    allegato.getMetaDati().add(md);
			}
		    }
		}
	    }
	}
    }

    @Override
    public AnagrafeType populateAnagrafeType(Anagrafe anagrafe) {

	AnagrafeType _anagrafe = new AnagrafeType();
	if (anagrafe.getTipoanagrafe().equals("F")) {
	    PersonaFisicaType pFisica = new PersonaFisicaType();
	    pFisica.getAltriDati().add(setCodiceAnagrafe(anagrafe));
	    pFisica.setCodiceFiscale(anagrafe.getCodicefiscale());
	    pFisica.setCognome(anagrafe.getNominativo());
	    pFisica.setNome(anagrafe.getNome() == null ? "" : anagrafe.getNome());
	    pFisica.setSesso(anagrafe.getSesso());
	    pFisica.setEmail(anagrafe.getEmail());
	    pFisica.setPec(anagrafe.getPec());
	    pFisica.setTelefono(anagrafe.getTelefono());
	    pFisica.setTelefonoCellulare(anagrafe.getTelefonocellulare());
	    Titoli titolo = anagrafe.getTitolo();
	    if (titolo != null) {
		pFisica.setTitolo(titolo.getTitolo());
	    }
	    if (anagrafe.getDatanascita() != null) {
		GregorianCalendar dataNascita = new GregorianCalendar();
		dataNascita.setTime(anagrafe.getDatanascita());
		pFisica.setDataNascita(Utilities.getXMLGregorianCalendar(dataNascita));
	    }
	    if (anagrafe.getComuneNascita() != null) {
		it.gruppoinit.pal.gp.core.domain.Comuni comuneNascita = anagrafe.getComuneNascita();
		ComuneType _comuneNascita = new ComuneType();
		_comuneNascita.setCodiceCatastale(comuneNascita.getCf());
		_comuneNascita.setComune(comuneNascita.getComune());
		pFisica.setComuneNascita(_comuneNascita);
	    }
	    // Residenza
	    //l'indirizzo è l'unico tag obbligatorio, quindi se vuoto l'ogg locType non deve essere settato.
	    // in alternativa popolare a stringa vuota l'indirizzo.
	    LocalizzazioneType _locResidenza = new LocalizzazioneType();
	    _locResidenza.setIndirizzo(anagrafe.getIndirizzo() == null ? "" : anagrafe.getIndirizzo());
	    _locResidenza.setCivico("");
	    _locResidenza.setLocalita(anagrafe.getCitta());
	    _locResidenza.setCap(anagrafe.getCap());
	    _locResidenza.setProvincia(anagrafe.getProvincia());
	    if (anagrafe.getComuneResidenza() != null) {
		it.gruppoinit.pal.gp.core.domain.Comuni comuneResidenza = anagrafe.getComuneResidenza();
		ComuneType _comuneResidenza = new ComuneType();
		_comuneResidenza.setCodiceCatastale(comuneResidenza.getCf());
		_comuneResidenza.setComune(comuneResidenza.getComune());
		_locResidenza.setComune(_comuneResidenza);
	    }
	    pFisica.setResidenza(_locResidenza);
	    // Indirizzo corrispondenza
	    if (StringUtils.isNotBlank(anagrafe.getIndirizzocorrispondenza())) {
		LocalizzazioneType indirizzoCorrispondenza = new LocalizzazioneType();
		indirizzoCorrispondenza.setIndirizzo(anagrafe.getIndirizzocorrispondenza());
		indirizzoCorrispondenza.setCap(anagrafe.getCapcorrispondenza());
		indirizzoCorrispondenza.setCivico("");
		if (anagrafe.getComunecorrispondenza() != null && anagrafe.getComunecorrispondenza().getCodicecomune() != null) {
		    ComuneType comuneCorr = new ComuneType();
		    comuneCorr.setCodiceCatastale(anagrafe.getComunecorrispondenza().getCf());
		    comuneCorr.setComune(anagrafe.getComunecorrispondenza().getComune());
		    indirizzoCorrispondenza.setComune(comuneCorr);
		}
		indirizzoCorrispondenza.setLocalita(anagrafe.getCittacorrispondenza());
		indirizzoCorrispondenza.setProvincia(anagrafe.getProvinciacorrispondenza());
		pFisica.setCorrispondenza(indirizzoCorrispondenza);
	    }
	    // ..
	    if (!EntityUtils.isNestedPropertyBlank(anagrafe.getCittadinanza(), "codice")) {
		CittadinanzaType cittadinanzaType = new CittadinanzaType();
		cittadinanzaType.setId(anagrafe.getCittadinanza().getCodice().toString());
		cittadinanzaType.setDescrizione(anagrafe.getCittadinanza().getCittadinanza());
		cittadinanzaType.setCodiceCatastale(anagrafe.getCittadinanza().getCf());
		pFisica.setCittadinanza(cittadinanzaType);
	    }
	    if (StringUtils.isNotBlank(anagrafe.getNumeroelencopro()) || StringUtils.isNotBlank(anagrafe.getProvinciaelencopro())
		    || anagrafe.getElenchiprofessionalibase() != null) {
		DatiIscrizioneAlboType s = populateAlboForAnagrafe(anagrafe);
		pFisica.setDatiIscrizioneAlbo(s);
	    }
	    // Set persona fisica
	    _anagrafe.setPersonaFisica(pFisica);
	} else {
	    PersonaGiuridicaType pGiuridica = new PersonaGiuridicaType();
	    pGiuridica.getAltriDati().add(setCodiceAnagrafe(anagrafe));
	    pGiuridica.setCodiceFiscale(anagrafe.getCodicefiscale());
	    pGiuridica.setFax(anagrafe.getFax());
	    pGiuridica.setPartitaIva(anagrafe.getPartitaiva());
	    pGiuridica.setRagioneSociale((anagrafe.getNominativo()).trim());
	    pGiuridica.setTelefono(anagrafe.getTelefono());
	    pGiuridica.setEmail(anagrafe.getEmail());
	    pGiuridica.setPec(anagrafe.getPec());
	    pGiuridica.setTelefonoCellulare(anagrafe.getTelefonocellulare());
	    Formegiuridiche formaGiuridica = anagrafe.getFormagiuridica();
	    if (formaGiuridica != null) {
		pGiuridica.setNaturaGiuridica(formaGiuridica.getFormagiuridica());
	    }
	    // Iscrizione CCIAA (registro ditte)
	    IscrizioneRegistroType iscrizioneCCIA = new IscrizioneRegistroType();
	    if (anagrafe.getComunecomregditte() != null && StringUtils.isNotBlank(anagrafe.getComunecomregditte().getCodicecomune())
		    && anagrafe.getDataregditte() != null && StringUtils.isNotBlank(anagrafe.getRegditte())) {
		// Comune di iscrizione CCIAA
		ComuneType comuneCCIA = new ComuneType();
		comuneCCIA.setCodiceCatastale(anagrafe.getComunecomregditte().getCf());
		iscrizioneCCIA.setComune(comuneCCIA);
		// Data iscrizione CCIAA
		GregorianCalendar dataCCIA = new GregorianCalendar();
		dataCCIA.setTime(anagrafe.getDataregditte());
		iscrizioneCCIA.setData(Utilities.getXMLGregorianCalendar(dataCCIA));
		// Numero iscrizione CCIAA
		iscrizioneCCIA.setNumero(anagrafe.getRegditte());
	    }
	    // Indirizzo corrispondenza
	    if (StringUtils.isNotBlank(anagrafe.getIndirizzocorrispondenza())) {
		LocalizzazioneType indirizzoCorrispondenza = new LocalizzazioneType();
		indirizzoCorrispondenza.setIndirizzo(anagrafe.getIndirizzocorrispondenza());
		indirizzoCorrispondenza.setCap(anagrafe.getCapcorrispondenza());
		indirizzoCorrispondenza.setCivico("");
		if (anagrafe.getComunecorrispondenza() != null && anagrafe.getComunecorrispondenza().getCodicecomune() != null) {
		    ComuneType comuneCorr = new ComuneType();
		    comuneCorr.setCodiceCatastale(anagrafe.getComunecorrispondenza().getCf());
		    comuneCorr.setComune(anagrafe.getComunecorrispondenza().getComune());
		    indirizzoCorrispondenza.setComune(comuneCorr);
		}
		indirizzoCorrispondenza.setLocalita(anagrafe.getCittacorrispondenza());
		indirizzoCorrispondenza.setProvincia(anagrafe.getProvinciacorrispondenza());
		pGiuridica.setIndirizzoCorrispondenza(indirizzoCorrispondenza);
	    }
	    // Sede legale
	    if (StringUtils.isNotBlank(anagrafe.getIndirizzo())) {
		LocalizzazioneType _sedeLegale = new LocalizzazioneType();
		_sedeLegale.setCap(anagrafe.getCap());
		_sedeLegale.setCivico("");
		_sedeLegale.setIndirizzo(anagrafe.getIndirizzo());
		_sedeLegale.setLocalita(anagrafe.getCitta());
		_sedeLegale.setProvincia(anagrafe.getProvincia());
		if (anagrafe.getComuneResidenza() != null) {
		    ComuneType _comuneSedeLegale = new ComuneType();
		    it.gruppoinit.pal.gp.core.domain.Comuni comuneSedeLegale = anagrafe.getComuneResidenza();
		    _comuneSedeLegale.setCodiceCatastale(comuneSedeLegale.getCf());
		    _comuneSedeLegale.setComune(comuneSedeLegale.getComune());
		    _sedeLegale.setComune(_comuneSedeLegale);
		}
		pGiuridica.setSedeLegale(_sedeLegale);
	    }
	    if (StringUtils.isNotBlank(anagrafe.getNumeroelencopro()) || StringUtils.isNotBlank(anagrafe.getProvinciaelencopro())
		    || anagrafe.getElenchiprofessionalibase() != null) {
		DatiIscrizioneAlboType s = populateAlboForAnagrafe(anagrafe);
		pGiuridica.setDatiIscrizioneAlbo(s);
	    }
	    // INPS
	    if (StringUtils.isNotBlank(anagrafe.getInpsMatricola())) {
		DatiInpsType inps = new DatiInpsType();
		inps.setNumero(anagrafe.getInpsMatricola());
		CodiceDescrizioneType si = new CodiceDescrizioneType(); // da modellazione l'elemento DEVE essere presente e lo devo mettere vuoto
		if (anagrafe.getSedeInps() != null) {
		    si.setCodice(anagrafe.getSedeInps().getCodice());
		    si.setDescrizione(anagrafe.getSedeInps().getDescrizione());
		}
		inps.setSedeIscrizione(si);
		pGiuridica.setDatiInps(inps);
	    }
	    // Inail
	    if (StringUtils.isNotBlank(anagrafe.getInailMatricola())) {
		DatiInailType inail = new DatiInailType();
		inail.setNumero(anagrafe.getInailMatricola());
		CodiceDescrizioneType si = new CodiceDescrizioneType(); // da modellazione l'elemento DEVE essere presente e lo devo mettere vuoto
		if (anagrafe.getSedeInail() != null) {
		    si.setCodice(anagrafe.getSedeInail().getCodice());
		    si.setDescrizione(anagrafe.getSedeInail().getDescrizione());
		}
		inail.setSedeIscrizione(si);
		pGiuridica.setDatiInail(inail);
	    }
	    // CassaEdile
	    if (StringUtils.isNotBlank(anagrafe.getCassaedileMatricola())) {
		DatiCassaEdileType cassaEdile = new DatiCassaEdileType();
		cassaEdile.setNumero(anagrafe.getCassaedileMatricola());
		CodiceDescrizioneType si = new CodiceDescrizioneType(); // da modellazione l'elemento DEVE essere presente e lo devo mettere vuoto
		if (anagrafe.getSedeCassaedile() != null) {
		    si.setCodice(anagrafe.getSedeCassaedile().getCodice());
		    si.setDescrizione(anagrafe.getSedeCassaedile().getDescrizione());
		}
		cassaEdile.setCassaEdile(si);
		pGiuridica.setDatiCassaEdile(cassaEdile);
	    }
	    _anagrafe.setPersonaGiuridica(pGiuridica);
	}
	return _anagrafe;
    }

    private ParametroType setCodiceAnagrafe(Anagrafe anagrafe) {

	ParametroType codiceAnagrafe = new ParametroType();
	codiceAnagrafe.setNome(ALTRODATO_ANAGRAFE_CODICEANAGRAFE);
	ValoreParametroType vpt = new ValoreParametroType();
	vpt.setCodice(String.valueOf(anagrafe.getId().getCodice()));
	vpt.setDescrizione(vpt.getCodice());
	codiceAnagrafe.getValore().add(vpt);
	return codiceAnagrafe;
    }

    private DatiIscrizioneAlboType populateAlboForAnagrafe(Anagrafe anagrafe) {

	DatiIscrizioneAlboType s = new DatiIscrizioneAlboType();
	if (StringUtils.isNotBlank(anagrafe.getNumeroelencopro())) {
	    s.setNumeroIscrizione(anagrafe.getNumeroelencopro());
	}
	if (StringUtils.isNotBlank(anagrafe.getProvinciaelencopro())) {
	    s.setSiglaProvincia(anagrafe.getProvinciaelencopro());
	}
	if (anagrafe.getElenchiprofessionalibase() != null) {
	    CodiceDescrizioneType cdb = new CodiceDescrizioneType();
	    cdb.setCodice(String.valueOf(anagrafe.getElenchiprofessionalibase().getId()));
	    s.setTipoOrdineProfessionisti(cdb);
	}
	return s;
    }

    private void populateStatoIstanza(InserimentoPraticaNLARequest request, Istanze istanza) {

	String codiceSoftware = request.getSportelloDestinatario().getIdSportello();
	FoArconfigurazioneId id = new FoArconfigurazioneId();
	id.setSoftware(codiceSoftware);
	// cerco l'oggetto FoArconfigurazione per il software passato dall'NLA
	FoArconfigurazione foArconfigurazione = foArconfigurazioneService.findById(id);
	// Se non esiste lo cerco per il software TT
	if (foArconfigurazione == null) {
	    id.setSoftware(WebConstants.SOFTWARE_TT);
	    foArconfigurazione = foArconfigurazioneService.findById(id);
	}
	// Se nei due passaggi trovo l'oggetto FoArconfigurazione,prendo l'oggetto stato istanza
	// e lo setto all'istanza che vado ad inserire
	if (foArconfigurazione != null) {
	    istanza.setChiusura(foArconfigurazione.getStatoInizialeIstanza());
	}
    }

    private String getIndirizzo(LocalizzazioneType loc) {

	String _ind = loc.getIndirizzo() == null ? "" : loc.getIndirizzo();
	String _civ = loc.getCivico() == null ? "" : loc.getCivico();
	if (StringUtils.isNotBlank(_civ)) {
	    _ind += ", " + _civ;
	}
	return _ind;
    }

    /**
     * La funzione controlla se i nodi chiamanti appartengono a nodi interni a SIGEPRO in modo tale da recuperare alcune
     * informazioni direttamente dalla Base dati piuttosto che da chiamate WS (es.: AllegatoBinario, oppure
     * all'inserimento pratica/attività assegnazione degli oggetti direttamente da codiceoggetto e non da
     * stc_idallegato).
     * 
     * @param destinatario
     * @param mittente
     * @return
     */
    @Override
    public boolean isChiamataDaNodoInterno(SportelloType destinatario, SportelloType mittente, boolean consideraDatiConsole) {

	String idnodomitt = StringUtils.defaultIfEmpty(mittente.getIdNodo(), "");
	String idnododest = StringUtils.defaultIfEmpty(destinatario.getIdNodo(), "");
	String identemitt = StringUtils.defaultIfEmpty(mittente.getIdEnte(), "");
	String identedest = StringUtils.defaultIfEmpty(destinatario.getIdEnte(), "");
	String idNodoBackend = "";
	String idNodoAreaRiservata = "";
	Verticalizzazioniparametri idNodoBE = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		NodoNLAEnum.NLA_IDNODO.name());
	if (idNodoBE != null) {
	    idNodoBackend = StringUtils.defaultIfEmpty(idNodoBE.getValore(), "").trim();
	}
	Verticalizzazioniparametri idNodoAR = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		NodoNLAEnum.NLA_IDNODO_AREARISERVATA.name());
	if (idNodoAR != null) {
	    idNodoAreaRiservata = StringUtils.defaultIfEmpty(idNodoAR.getValore(), "").trim();
	}
	log.debug("isChiamataDaNodoInterno: idnodomitt: {}, idnododest: {}, idnodoareariservata: {}, idnodobackend: {}",
		new Object[] { idnodomitt, idnododest, idNodoBackend, idNodoAreaRiservata });
	boolean isChiamataDaNodo = false;
	if ((idnodomitt.equalsIgnoreCase(idNodoBackend) || idnodomitt.equalsIgnoreCase(idNodoAreaRiservata))
		&& (idnododest.equalsIgnoreCase(idNodoBackend) || idnododest.equalsIgnoreCase(idNodoAreaRiservata))) {
	    if (identedest.equalsIgnoreCase(identemitt)) {
		isChiamataDaNodo = true;
	    }
	}
	if (!isChiamataDaNodo) {
	    if (consideraDatiConsole) {
		Verticalizzazioniparametri idNodoCONSOLE = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
			NodoNLAEnum.NLA_IDNODO_AR_CONSOLE.name());
		if (idNodoCONSOLE != null) {
		    String idNodoCONSOLEStr = StringUtils.defaultIfEmpty(idNodoCONSOLE.getValore(), "").trim();
		    if (idnodomitt.equalsIgnoreCase(idNodoCONSOLEStr)) {
			isChiamataDaNodo = true;
		    }
		}
	    }
	}
	return isChiamataDaNodo;
    }

    private boolean isAreaRiservata(SportelloType sportello) {

	String idnodomitt = StringUtils.defaultIfEmpty(sportello.getIdNodo(), "");
	String idNodoAreaRiservata = "";
	Verticalizzazioniparametri idNodoAR = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		NodoNLAEnum.NLA_IDNODO_AREARISERVATA.name());
	if (idNodoAR != null) {
	    idNodoAreaRiservata = StringUtils.defaultIfEmpty(idNodoAR.getValore(), "").trim();
	}
	log.debug("isChiamataDaNodoInterno: idnodoareariservata: {}", new Object[] { idNodoAreaRiservata });
	if (idnodomitt.equalsIgnoreCase(idNodoAreaRiservata)) {
	    return true;
	}
	return false;
    }

    @Override
    public ErroreType populateErroreType(String idnodo, String idente, String idsportello, String errCode, Exception e, String methodName) {

	ErroreType errore = new ErroreType();
	String eMessage = "";
	StringBuffer dumptrace = new StringBuffer();
	if (e != null) {
	    eMessage = e.getMessage() == null ? "NULL" : e.getMessage();
	    if (e.getStackTrace() != null) {
		StackTraceElement[] traces = e.getStackTrace();
		int i = 0;
		for (StackTraceElement trace : traces) {
		    if (trace != null) {
			i++;
			dumptrace = dumptrace.append(trace.toString()).append("\n");
			if (i > 20) {
			    break;
			}
		    }
		}
	    }
	}
	errore.setNumeroErrore(errCode + "-" + idnodo + "-" + idente + "-" + idsportello + " [" +
			       Utilities.formatDate(Calendar.getInstance().getTime(), true) + "]");
	String errMesg = "<b>Errore nella chiamata al metodo " + methodName + " del nodo NLA [" + idnodo + "]: <i>" + eMessage + "</i></b>.";
	if (dumptrace.length() > 0) {
	    errMesg += "\n\n\n<br />STACKTRACE:\n" + dumptrace.toString();
	}
	errore.setDescrizione(errMesg);
	return errore;
    }

    @Override
    public NodoNLAEnum getTipoNodo(SportelloType mittDest) {

	String idNodoMittDest = StringUtils.defaultIfEmpty(mittDest.getIdNodo(), "");
	List<Verticalizzazioniparametri> params = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC);
	String pname = "";
	String val = "";
	for (Verticalizzazioniparametri vp : params) {
	    pname = vp.getVerticalizzazioniparametribase().getId().getParametro();
	    if (pname.startsWith("NLA_IDNODO")) {
		val = StringUtils.defaultIfEmpty(vp.getValore(), "").trim();
		if (StringUtils.isNotBlank(val)) {
		    if (idNodoMittDest.equalsIgnoreCase(val)) {
			try {
			    return NodoNLAEnum.valueOf(pname);
			} catch (Exception e) {
			    log.error("Tipo nodo non riconosciuto {}", pname);
			    return NodoNLAEnum.NLA_IDNODO_SCONOSCIUTO;
			}
		    }
		}
	    }
	}
	return NodoNLAEnum.NLA_IDNODO_SCONOSCIUTO;
    }

    @Override
    public AnagrafeType populateAnagrafeType(Integer codiceAnagrafe) {

	Anagrafe anagrafe = anagrafeService.findById(new PkId(codiceAnagrafe));
	return populateAnagrafeType(anagrafe);
    }

    @Override
    public List<LocalizzazioneNelComuneType> populateLocalizzazione(Integer codiceIstanze) {

	List<Istanzestradario> stradario = istanzestradarioService.findByIstanza(codiceIstanze);
	// Set<Istanzestradario> stradario = istanza.getIstanzestradarios();
	List<LocalizzazioneNelComuneType> listLoc = new ArrayList<LocalizzazioneNelComuneType>();
	LocalizzazioneNelComuneType loc = null;
	if (stradario != null && !stradario.isEmpty()) {
	    int i = 0;
	    for (Istanzestradario istanzestradario : stradario) {
		loc = new LocalizzazioneNelComuneType();
		loc.setCivico(istanzestradario.getCivico());
		String prefisso = "";
		if (StringUtils.isNotBlank(istanzestradario.getStradario().getPrefisso())) {
		    prefisso = istanzestradario.getStradario().getPrefisso() + " ";
		}
		loc.setDenominazione(prefisso + istanzestradario.getStradario().getDescrizione());
		loc.setEsponente(istanzestradario.getEsponente());
		loc.setId(String.valueOf(istanzestradario.getStradario().getId().getCodice()));
		if (StringUtils.isNotBlank(istanzestradario.getStradario().getCodviario())) {
		    loc.setCodiceViario(istanzestradario.getStradario().getCodviario());
		} else {
		    loc.setCodiceViario(String.valueOf(istanzestradario.getStradario().getId().getCodice()));
		}
		if (!EntityUtils.isNestedPropertyBlank(istanzestradario.getStradariocolore(), "id.codicecolore")) {
		    loc.setColore(istanzestradario.getStradariocolore().getId().getCodicecolore());
		}
		loc.setScala(istanzestradario.getScala());
		loc.setInterno(istanzestradario.getInterno());
		loc.setEsponenteInterno(istanzestradario.getEsponenteinterno());
		loc.setPiano(istanzestradario.getPiano());
		if (StringUtils.isNotBlank(istanzestradario.getKm())) {
		    loc.setKm(istanzestradario.getKm());
		}
		if (StringUtils.isNotBlank(istanzestradario.getFabbricato())) {
		    loc.setFabbricato(istanzestradario.getFabbricato());
		}
		if (StringUtils.isNotBlank(istanzestradario.getFrazione())) {
		    FrazioneType frazione = new FrazioneType();
		    frazione.setDescrizione(istanzestradario.getFrazione());
		    loc.setFrazione(frazione);
		}
		if (StringUtils.isNotBlank(istanzestradario.getCircoscrizione())) {
		    CircoscrizioneType circ = new CircoscrizioneType();
		    circ.setDescrizione(istanzestradario.getCircoscrizione());
		    loc.setCircoscrizione(circ);
		}
		if (StringUtils.isNotBlank(istanzestradario.getQuartiere())) {
		    QuartiereType quart = new QuartiereType();
		    quart.setDescrizione(istanzestradario.getQuartiere());
		    loc.setQuartiere(quart);
		}
		//		if (StringUtils.isNotBlank(StringUtils.defaultIfEmpty(istanzestradario.getNote(), "").trim())) {
		//		    ParametroType ns = new ParametroType();
		//		    ns.setNome(ALTRI_DATI_NOTE_ISTANZESTRADARIO + i);
		//		    ValoreParametroType vp = new ValoreParametroType();
		//		    vp.setCodice(istanzestradario.getNote());
		//		    vp.setDescrizione(vp.getCodice());
		//		    ns.getValore().add(vp);
		//		    dettaglioPraticaType.getAltriDati().add(ns);
		//		}
		//		if (StringUtils.isNotBlank(StringUtils.defaultIfEmpty(istanzestradario.getCodicecivico(), "").trim())) {
		//		    ParametroType ns = new ParametroType();
		//		    ns.setNome(ALTRI_DATI_CODCIVICO_ISTANZESTRADARIO + i);
		//		    ValoreParametroType vp = new ValoreParametroType();
		//		    vp.setCodice(istanzestradario.getCodicecivico());
		//		    vp.setDescrizione(vp.getCodice());
		//		    ns.getValore().add(vp);
		//		    dettaglioPraticaType.getAltriDati().add(ns);
		//		}
		if (StringUtils.isNotBlank(istanzestradario.getUuid())) {
		    loc.setUuid(istanzestradario.getUuid());
		}
		if (istanzestradario.getTipiLocalizzazioni() != null) {
		    TipoLocalizzazioneType tl = new TipoLocalizzazioneType();
		    tl.setCodice(String.valueOf(istanzestradario.getTipiLocalizzazioni().getId().getCodice()));
		    tl.setDescrizione(istanzestradario.getTipiLocalizzazioni().getDescrizione());
		    loc.setTipo(tl);
		}
		if (StringUtils.isNotBlank(istanzestradario.getLongitudine()) || StringUtils.isNotBlank(istanzestradario.getLatitudine())) {
		    CoordinateType coord = new CoordinateType();
		    coord.setLatitudine(istanzestradario.getLatitudine());
		    coord.setLongitudine(istanzestradario.getLongitudine());
		    loc.setCoordinate(coord);
		}
		if (StringUtils.isNotBlank(istanzestradario.getCap())) {
		    loc.setCap(istanzestradario.getCap());
		}
		i++;
		Set<Istanzemappali> mappalis = istanzestradario.getIstanzemappalis();
		for (Istanzemappali istanzemappali : mappalis) {
		    // BOCCI 2012-05-31 BUGZILLA ID 568
		    if (checkPopulateMappale(istanzemappali, codiceIstanze, false)) {
			RiferimentoCatastaleType mappale = new RiferimentoCatastaleType();
			mappale.setFoglio(istanzemappali.getFoglio());
			mappale.setParticella(istanzemappali.getParticella());
			mappale.setSub(istanzemappali.getSub());
			if (StringUtils.isNotBlank(istanzemappali.getSezione())) {
			    mappale.setSezione(istanzemappali.getSezione());
			}
			if (StringUtils.isNotBlank(istanzemappali.getUnitaimmob())) {
			    mappale.setUnitaImobiliare(istanzemappali.getUnitaimmob());
			}
			if (istanzemappali.getCatasto().getCodice().equalsIgnoreCase("F")) {
			    mappale.setTipoCatasto(CATASTO_EDILIZIO_URBANO);
			} else {
			    mappale.setTipoCatasto(CATASTO_TERRENI);
			}
			loc.getRiferimentoCatastale().add(mappale);
		    }
		}
		listLoc.add(loc);
	    }
	}
	return listLoc;
    }

    @Override
    public RichiedenteType populateRichiedenteType(Anagrafe richiedente, Integer codiceTiposoggetto, String tiposoggetto) {

	RichiedenteType richiedenteType = null;
	if (richiedente != null) {
	    if (richiedente.getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_GIURIDICA)) {
		log.warn("Il richiedente della pratica è una persona giuridica lo setto nel campo dettaglioPraticaType.setAziendaRichiedente");
		// Popolo solo il ruolo type se presente, mentre metto a null l'anagrafica
		// Questo perchè in fase di trasferimento di pratica se il richiedende dell'istanza è 
		// l'azienda riuscirò a passare il ruolo se popolato
		richiedenteType = new RichiedenteType();
		richiedenteType.setAnagrafica(null);
		//Tipisoggetto inQualitaDi = istanza.getTipisoggetto();
		if (codiceTiposoggetto != null) {
		    RuoloType _inQualitaDi = new RuoloType();
		    _inQualitaDi.setIdRuolo(String.valueOf(codiceTiposoggetto));
		    _inQualitaDi.setRuolo(tiposoggetto);
		    richiedenteType.setRuolo(_inQualitaDi);
		}
	    } else {
		richiedenteType = new RichiedenteType();
		AnagrafeType _richiedente = populateAnagrafeType(richiedente);
		richiedenteType.setAnagrafica(_richiedente.getPersonaFisica());
		//Tipisoggetto inQualitaDi = istanza.getTipisoggetto();
		if (codiceTiposoggetto != null) {
		    RuoloType _inQualitaDi = new RuoloType();
		    _inQualitaDi.setIdRuolo(String.valueOf(codiceTiposoggetto));
		    _inQualitaDi.setRuolo(tiposoggetto);
		    richiedenteType.setRuolo(_inQualitaDi);
		}
	    }
	}
	return richiedenteType;
    }

    /**
     * torna una lista di documenti presa dalla request di cui sia non nulla la proprietà allegati
     * 
     * @param dettaglioPratica
     * @return
     */
    @Override
    public List<DocumentiType> getDocsPerPratica(DettaglioPraticaType dettaglioPratica) {

	List<DocumentiType> docs = new ArrayList<DocumentiType>();
	if (dettaglioPratica.getDocumenti() != null) {
	    for (DocumentiType doc : dettaglioPratica.getDocumenti()) {
		if (doc != null && doc.getAllegati() != null) {
		    docs.add(doc);
		}
	    }
	}
	if (dettaglioPratica.getProcedimenti() != null) {
	    for (ProcedimentoType proc : dettaglioPratica.getProcedimenti()) {
		if (proc.getDocumenti() != null) {
		    for (DocumentiType doc : proc.getDocumenti()) {
			if (doc != null && doc.getAllegati() != null) {
			    docs.add(doc);
			}
		    }
		}
	    }
	}
	if (dettaglioPratica.getProcure() != null) {
	    for (ProcuraType procura : dettaglioPratica.getProcure()) {
		DocumentiType doc = procura.getProcura();
		if (doc != null && doc.getAllegati() != null) {
		    docs.add(doc);
		}
	    }
	}
	return docs;
    }

    @Override
    public boolean verificaNuovaLogicaSuaper(InserimentoPraticaNLARequest praticaNla) {

	boolean isNuovaLogicaSuaper = false;
	// SE per il software è attivata la verticalizzazione SUAPER
	// SE NON è UNA PRATICA SIEDER
	// SE CI SONO CONFIGURAZIONI SULLE TABELLE ALBEROPROC_GRUPPI_SMIST
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_SUAPER, ORMHelper.getSoftware())) {
	    //
	    SportelloType mitt = praticaNla.getSportelloMittente();
	    boolean isDomandaSieder = checkSportello(mitt, NodoNLAEnum.NLA_IDNODO_SIEDER);
	    // LA NUOVA LOGICA NON SI APPLICA ALLE PRATICHE SIEDER CHE POSSONO VENIRE ANCHE NELLO STESSO SOFTWARE DI SUAPER/ACCESSO UNITARIO
	    log.debug("Nuova Logica SUAPER domandaSieder={}", isDomandaSieder);
	    if (!isDomandaSieder) {
		// SE ESISTE UNA CONFIGURAZIONE ALLORA VOGLIO LA NUOVA LOGICA
		int numConf = alberoprocGruppiSmistService.countBySoftware();
		log.debug("Nuova Logica SUAPER alberoprocGruppiSmist={}", numConf);
		if (numConf > 0) {
		    Verticalizzazioniparametri nodi_abilitati = verticalizzazioniService.getVerticalizzazioniparametri(
			    WebConstants.VERTICALIZZAZIONE_SUAPER, WebConstants.VERTICALIZZAZIONE_SUAPER_LISTA_NODI_GRUPPIINTERVENTI,
			    ORMHelper.getSoftware());
		    if (nodi_abilitati != null) {
			String nodoMitt = praticaNla.getSportelloMittente().getIdNodo();
			String idNodo = StringUtils.defaultString(nodi_abilitati.getValore()).trim();
			log.debug("Nuova Logica SUAPER nodi_abilitati={}", idNodo);
			if (StringUtils.isNotBlank(idNodo)) {
			    String[] listaNodi = idNodo.split(",");
			    for (String n : listaNodi) {
				if (StringUtils.defaultString(nodoMitt, "AAA_321").equalsIgnoreCase(StringUtils.defaultString(n, "NNN_123"))) {
				    log.debug("Nuova Logica SUAPER={}", isNuovaLogicaSuaper);
				    isNuovaLogicaSuaper = true;
				    break;
				}
			    }
			}
		    }
		}
	    }
	}
	log.debug("Nuova Logica SUAPER={}", isNuovaLogicaSuaper);
	if (isNuovaLogicaSuaper) {
	    isNuovaLogicaSuaper = !isApplicaVecchiaLogicaSUAPER(praticaNla);
	}
	return isNuovaLogicaSuaper;
    }

    /**
     * Il metodo verifica se la verticalizzazione VERTICALIZZAZIONE_SUAPER_VECCHIA_LOGICA_SE_UN_ENDO sia a 1 o S e nel
     * caso controlla se la domanda contiene un solo endo allora va applicata la vecchia logica di recupero intervento
     * per endo principale. In questo modo viene sovrascritta la modalità prevista dai gruppi_interventi
     * 
     * @param praticaNla
     * @return
     */
    private boolean isApplicaVecchiaLogicaSUAPER(InserimentoPraticaNLARequest praticaNla) {

	String applicaVecchiaLogica = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_SUAPER,
		WebConstants.VERTICALIZZAZIONE_SUAPER_VECCHIA_LOGICA_SE_UN_ENDO);
	if (StringUtils.defaultIfEmpty(applicaVecchiaLogica, "0").equalsIgnoreCase("1")
		|| StringUtils.defaultIfEmpty(applicaVecchiaLogica, "0").equalsIgnoreCase("S")) {
	    if (praticaNla != null && praticaNla.getDettaglioPratica() != null && praticaNla.getDettaglioPratica().getProcedimenti() != null
		    && praticaNla.getDettaglioPratica().getProcedimenti().size() == 1) {
		log.debug("Nuova Logica SUAPER={}, WebConstants.VERTICALIZZAZIONE_SUAPER_VECCHIA_LOGICA_SE_UN_ENDO:{}", true, applicaVecchiaLogica);
		return true;
	    }
	}
	return false;
    }

    @Override
    public boolean isNodoARConsole(SportelloType mittente) {

	boolean isArConsole = false;
	String idnodomitt = StringUtils.defaultIfEmpty(mittente.getIdNodo(), "");
	Verticalizzazioniparametri idNodoCONSOLE = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		NodoNLAEnum.NLA_IDNODO_AR_CONSOLE.name());
	if (idNodoCONSOLE != null) {
	    String idNodoCONSOLEStr = StringUtils.defaultIfEmpty(idNodoCONSOLE.getValore(), "").trim();
	    if (idnodomitt.equalsIgnoreCase(idNodoCONSOLEStr)) {
		isArConsole = true;
	    }
	}
	return isArConsole;
    }

    @Override
    public boolean checkIsStessoNodoEnteDifferente(SportelloType mittente, SportelloType destinatario) {

	log.debug("checkIsStessoNodoEnteDifferente# IdNodoMittente = {}, IdNodoDestinatario = {}, IdEnte Mittente = {}, IdEnteDestinatario = {}",
		new Object[] { mittente.getIdNodo(), destinatario.getIdNodo(), mittente.getIdEnte(), destinatario.getIdEnte() });
	if (mittente.getIdNodo().equals(destinatario.getIdNodo())) {
	    if (!mittente.getIdEnte().equals(destinatario.getIdEnte())) {
		log.debug("checkIsStessoNodoEnteDifferente# return = {}", true);
		return true;
	    }
	}
	log.debug("checkIsStessoNodoEnteDifferente# return = {}", false);
	return false;
    }

    @Override
    public boolean checkIsStessoNodoStessoEnte(SportelloType mittente, SportelloType destinatario) {

	log.debug("checkIsStessoNodoStessoEnte# IdNodoMittente = {}, IdNodoDestinatario = {}, IdEnte Mittente = {}, IdEnteDestinatario = {}",
		new Object[] { mittente.getIdNodo(), destinatario.getIdNodo(), mittente.getIdEnte(), destinatario.getIdEnte() });
	if (mittente.getIdNodo().equals(destinatario.getIdNodo())) {
	    if (mittente.getIdEnte().equals(destinatario.getIdEnte())) {
		log.debug("checkIsStessoNodoStessoEnte# return = {}", true);
		return true;
	    }
	}
	log.debug("checkIsStessoNodoStessoEnte# return = {}", false);
	return false;
    }

    /**
     * La funzione legge se è configurato il parametro di verticalizzazione
     * {@link WebConstants#VERTICALIZZAZIONE_STC_LISTA_NODI_MITT_DOWNLOAD_ALLS}. <br />
     * Se configurato, questo contiene una stringa che rappresenta gli id dei nodi che non devono aggiornare gli
     * attributi della scheda anagrafica (qualora venisse identificata per CF o PIVA). Questo controllo è stato
     * realizzato per la richiesta di Cervia (Provincia di Ravenna) che non vuole che ad ogni domanda proveniente da
     * PEOPLE produca una scheda storicizzata.
     * 
     * @param sportelloMittente
     * @return
     */
    @Override
    public boolean isScaricaSubitoAllegatiFisiciPerNodo(SportelloType sportello) {

	String valore = getValoreListaNodiSTCScaricaSubitoAllegati();
	if (StringUtils.isNotBlank(valore)) {
	    String idNodoMittente = StringUtils.defaultIfEmpty(sportello.getIdNodo(), "").trim();
	    String[] listaNodi = valore.split(",");
	    for (String nodo : listaNodi) {
		if (idNodoMittente.equalsIgnoreCase(StringUtils.defaultIfEmpty(nodo, "").trim())) {
		    return true;
		}
	    }
	}
	return false;
    }

    private String getValoreListaNodiSTCScaricaSubitoAllegati() {

	String valore = "";
	Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		WebConstants.VERTICALIZZAZIONE_STC_LISTA_NODI_MITT_DOWNLOAD_ALLS);
	if (vp != null) {
	    if (StringUtils.isNotBlank(vp.getValore())) {
		valore = vp.getValore();
	    }
	}
	return valore;
    }

    private boolean resolveDisDataModifica(String valore, String check) {

	return (StringUtils.defaultString(valore).indexOf(check) >= 0);
    }

    @Override
    public boolean isNodoEnteNonLocalePraticaZIP(SportelloType mittente) {

	boolean isArConsole = false;
	String idnodomitt = StringUtils.defaultIfEmpty(mittente.getIdNodo(), "");
	Verticalizzazioniparametri idNodoCONSOLE = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		NodoNLAEnum.NLA_IDNODO_ENTE_NON_LOCALE.name());
	if (idNodoCONSOLE != null) {
	    String idNodoCONSOLEStr = StringUtils.defaultIfEmpty(idNodoCONSOLE.getValore(), "").trim();
	    if (idnodomitt.equalsIgnoreCase(idNodoCONSOLEStr)) {
		isArConsole = true;
	    }
	}
	return isArConsole;
    }

    @Override
    public boolean isCodiceOggettoAltroSistema(SportelloType sportelloMittente, SportelloType sportelloDestinatario) {

	boolean isNodoArConsole = this.isNodoARConsole(sportelloMittente);
	boolean isStessoNodoEnteDifferente = this.checkIsStessoNodoEnteDifferente(sportelloMittente, sportelloDestinatario);
	boolean isNodoEnteNonLocalePraticaZIP = this.isNodoEnteNonLocalePraticaZIP(sportelloMittente);
	return isStessoNodoEnteDifferente || isNodoArConsole || isNodoEnteNonLocalePraticaZIP;
    }

    private void copiaMetadatiDaRequest(Movimenti mov, List<ParametroType> altriDati) {

	if (altriDati == null || altriDati.isEmpty()) {
	    return;
	}
	for (ParametroType parametroType : altriDati) {
	    if (StringUtils.defaultString(parametroType.getNome(), "").startsWith(NOTIFICA_ATTIVITA_PREFISSO_MOVIMENTO_METADATO) && // 
		    parametroType.getValore() != null && // 
		    !parametroType.getValore().isEmpty()) {
		String nomeMetadato = parametroType.getNome().replace(NOTIFICA_ATTIVITA_PREFISSO_MOVIMENTO_METADATO, "");
		String valore = parametroType.getValore().get(0).getCodice();
		if (StringUtils.isNotBlank(valore)) {
		    mov.getMetadatiDaInserire().add(new CodiceDescrizioneBean(nomeMetadato, valore));
		}
	    }
	}
    }
}
