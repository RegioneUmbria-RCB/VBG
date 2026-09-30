package it.gruppoinit.pal.gp.core.features.mailtipo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.mailservice.schemas.messages.AttachmentType;
import it.gruppoinit.mailservice.schemas.messages.AttachmentsType;
import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AnagrafeVerificheMailDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ContestiMailTipoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Domandestc;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzearee;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.IstanzeprocedimentiId;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Sorteggidettaglio;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeareeService;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.features.mailtipo.sostituzioni.SegnapostiSorteggiBean;
import it.gruppoinit.pal.gp.core.features.mailtipo.sostituzioni.SegnapostiSorteggiResolver;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto.ZipLogicoTabellaHashMailResolver;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoMailResolver;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.SegnapostoMailFactory;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.FormatUtils;
import it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio.SorteggidettaglioService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.DomandestcService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.IstanzemappaliService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.LocalizzazioneType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.PersonaGiuridicaType;

@Service
public class MailtipoServiceImpl extends BaseServiceImpl<Mailtipo, PkId> implements MailtipoService {

    private static Logger log = LoggerFactory.getLogger(MailtipoServiceImpl.class);
    private MailtipoDAO mailtipoDAO;
    private AmministrazioniService amministrazioniService;
    private IstanzeService istanzeService;
    private AlberoprocService alberoprocService;
    private IstanzeareeService istanzeareeService;
    private IstanzemappaliService istanzemappaliService;
    private ConfigurazioneService configurazioneService;
    private ResponsabiliService responsabiliService;
    private IstanzeprocedimentiService istanzeprocedimentiService;
    private IstanzestradarioService istanzestradarioService;
    private IstanzerichiedentiService istanzerichiedentiService;
    private MovimentiService movimentiService;
    private SorteggidettaglioService sorteggidettaglioService;
    private AutorizzazioniService autorizzazioniService;
    private DomandestcService domandestcService;
    private ComuniService comuniService;
    private UserSecurityService userSecurityService;
    private MovimentiallegatiService movimentiallegatiService;
    private OggettiService oggettiService;
    private MovimentiZipLogicoService movimentiZipLogicoService;
    private ISegnapostoService segnapostoService;
    private Istanzedyn2datiService istanzedyn2datiService;
    @Autowired
    private AnagrafeVerificheMailDAO anagrafeVerificheMailDAO;

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Autowired
    public void setSegnapostoService(ISegnapostoService segnapostoService) {

	this.segnapostoService = segnapostoService;
    }

    @Autowired
    public void setMovimentiZipLogicoService(MovimentiZipLogicoService movimentiZipLogicoService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setDomandestcService(DomandestcService domandestcService) {

	this.domandestcService = domandestcService;
    }

    @Autowired
    public void setIstanzemappaliService(IstanzemappaliService istanzemappaliService) {

	this.istanzemappaliService = istanzemappaliService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setSorteggidettaglioService(SorteggidettaglioService sorteggidettaglioService) {

	this.sorteggidettaglioService = sorteggidettaglioService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setIstanzerichiedentiService(IstanzerichiedentiService istanzerichiedentiService) {

	this.istanzerichiedentiService = istanzerichiedentiService;
    }

    @Autowired
    public void setIstanzestradarioService(IstanzestradarioService istanzestradarioService) {

	this.istanzestradarioService = istanzestradarioService;
    }

    @Autowired
    public void setIstanzeprocedimentiService(IstanzeprocedimentiService istanzeprocedimentiService) {

	this.istanzeprocedimentiService = istanzeprocedimentiService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setIstanzeareeService(IstanzeareeService istanzeareeService) {

	this.istanzeareeService = istanzeareeService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setMailtipoDAO(MailtipoDAO mailtipoDAO) {

	this.mailtipoDAO = mailtipoDAO;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Override
    protected Class<Mailtipo> getEntityClass() {

	return Mailtipo.class;
    }

    @Override
    public void delete(Mailtipo entity) {

	if (isDeleteAllowed(entity)) {
	    mailtipoDAO.delete(entity);
	}
    }

    @Override
    public List<Mailtipo> findAll(Integer firstResult, Integer maxResult) {

	return mailtipoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Mailtipo findById(PkId id) {

	return mailtipoDAO.findById(id);
    }

    @Override
    public void insert(Mailtipo entity) {

	if (validateEntity(entity)) {
	    if (validateInsertOrUpdate(entity)) {
		mailtipoDAO.insert(entity);
	    }
	}
    }

    @Override
    public void update(Mailtipo entity) {

	if (validateEntity(entity)) {
	    if (validateInsertOrUpdate(entity)) {
		mailtipoDAO.update(entity);
	    }
	}
    }

    @Override
    public List<Mailtipo> findByFilter(Mailtipo filter) {

	return mailtipoDAO.findByFilter(filter);
    }

    @Override
    protected boolean isDeleteAllowed(Mailtipo entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getProtocolloRegistris().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "PROTOCOLLO_REGISTRI", null));
	}
	if (entity.getTipimovimentosForFkTipimovcomTelMailtipo().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIMOVIMENTO", null));
	}
	if (entity.getTipimovimentosForFkTipimovricTelMailtipo().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIMOVIMENTO", null));
	}
	if (entity.getTipimovimentoComunicazionis().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIMOVIMENTO_COMUNICAZIONI", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    /**
     * validazione lato service:<br/>
     * se ambito = mail allora sono obbligatori sia oggetto che corpo<br />
     * se ambito = protocollo allora obbligatorio oggetto<br />
     * se ambito = conferenze allora obbligatorio corpo
     */
    private boolean validateInsertOrUpdate(Mailtipo entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getAmbito().equalsIgnoreCase("M")) {
	    if (StringUtils.isBlank(entity.getOggetto())) {
		_ivs.add(new InvalidValue("field.required", Mailtipo.class, "oggetto", entity.getOggetto(), entity));
	    }
	    if (StringUtils.isBlank(entity.getCorpo())) {
		_ivs.add(new InvalidValue("field.required", Mailtipo.class, "corpo", entity.getOggetto(), entity));
	    }
	} else if (entity.getAmbito().equalsIgnoreCase("P")) {
	    if (StringUtils.isBlank(entity.getOggetto())) {
		_ivs.add(new InvalidValue("field.required", Mailtipo.class, "oggetto", entity.getOggetto(), entity));
	    }
	} else if (entity.getAmbito().equalsIgnoreCase("C")) {
	    if (StringUtils.isBlank(entity.getCorpo())) {
		_ivs.add(new InvalidValue("field.required", Mailtipo.class, "corpo", entity.getOggetto(), entity));
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }

    private List<String> getCodici() {

	List<String> codiciList = new ArrayList<String>();
	codiciList.add("");
	String _1 = "[1]";
	codiciList.add(_1);
	String _2 = "[2]";
	codiciList.add(_2);
	String _3 = "[3]";
	codiciList.add(_3);
	String _4 = "[4]";
	codiciList.add(_4);
	String _5 = "[5]";
	codiciList.add(_5);
	String _6 = "[6]";
	codiciList.add(_6);
	String _7 = "[7]";
	codiciList.add(_7);
	String _8 = "[8]";
	codiciList.add(_8);
	String _9 = "[9]";
	codiciList.add(_9);
	String _10 = "[10]";
	codiciList.add(_10);
	String _11 = "[11]";
	codiciList.add(_11);
	String _12 = "[12]";
	codiciList.add(_12);
	String _13 = "[13]";
	codiciList.add(_13);
	String _14 = "[14]";
	codiciList.add(_14);
	String _15 = "[15]";
	codiciList.add(_15);
	String _16 = "[16]";
	codiciList.add(_16);
	String _17 = "[17]";
	codiciList.add(_17);
	String _18 = "[18]";
	codiciList.add(_18);
	String _19 = "[19]";
	codiciList.add(_19);
	String _20 = "[20]";
	codiciList.add(_20);
	String _21 = "[21]";
	codiciList.add(_21);
	String _22 = "[22]";
	codiciList.add(_22);
	String _23 = "[23]";
	codiciList.add(_23);
	String _24 = "[24]";
	codiciList.add(_24);
	String _25 = "[25]";
	codiciList.add(_25);
	String _26 = "[26]";
	codiciList.add(_26);
	String _27 = "[27]";
	codiciList.add(_27);
	String _28 = "[28]";
	codiciList.add(_28);
	String _29 = "[29]";
	codiciList.add(_29);
	String _30 = "[30]";
	codiciList.add(_30);
	String _31 = "[31]";
	codiciList.add(_31);
	String _32 = "[32]";
	codiciList.add(_32);
	String _33 = "[33]";
	codiciList.add(_33);
	String _34 = "[34]";
	codiciList.add(_34);
	String _35 = "[35]";
	codiciList.add(_35);
	String _36 = "[36]";
	codiciList.add(_36);
	String _37 = "[37]";
	codiciList.add(_37);
	String _38 = "[38(";
	codiciList.add(_38);
	String _39 = "[39(";
	codiciList.add(_39);
	String _40 = "[40]";
	codiciList.add(_40);
	String _41 = "[41]";
	codiciList.add(_41);
	String _42 = "[42]";
	codiciList.add(_42);
	String _DATIGEN_DEN = "[DATIGEN_DEN]";
	codiciList.add(_DATIGEN_DEN);
	String _DATISPO_DEN = "[DATISPO_DEN]";
	codiciList.add(_DATISPO_DEN);
	String _AZRIC_DEN = "[AZRIC_DEN]";
	codiciList.add(_AZRIC_DEN);
	String _AZRIC_CF = "[AZRIC_CF]";
	codiciList.add(_AZRIC_CF);
	String _AZRIC_PI = "[AZRIC_PI]";
	codiciList.add(_AZRIC_PI);
	String _CPT = "[CPT]";
	codiciList.add(_CPT);
	String _DATIGEN_COD_ACCR = "[DATIGEN_COD_ACCR]";
	codiciList.add(_DATIGEN_COD_ACCR);
	//codice fiscale del richiedente.
	String _RIC_CF = "[RIC_CF]";
	codiciList.add(_RIC_CF);
	//partita iva del richiedente.
	String _RIC_PIVA = "[RIC_PIVA]";
	codiciList.add(_RIC_PIVA);
	//identificativo univoco del sistema di protocollazione integrato.
	String _MOVIMENTI_FKIDPROTOCOLLO = "[MOVIMENTI_FKIDPROTOCOLLO]";
	codiciList.add(_MOVIMENTI_FKIDPROTOCOLLO);
	//numero del protocollo (senza /anno)
	String _MOVIMENTI_NUMPROT = "[MOVIMENTI_NUMPROT]";
	codiciList.add(_MOVIMENTI_NUMPROT);
	//anno del protocollo
	String _MOVIMENTI_ANNOPROT = "[MOVIMENTI_ANNOPROT]";
	codiciList.add(_MOVIMENTI_ANNOPROT);
	// Dati sulla localizzazione estesa (Indirizzo, civico / esponente colore, se presente km usato al posto del civico)
	String _LOC_ESTESA = "[LOC_ESTESA]";
	codiciList.add(_LOC_ESTESA);
	String _ESPONENTE = "[ESPONENTE]";
	codiciList.add(_ESPONENTE);
	String _COLORE = "[COLORE]";
	codiciList.add(_COLORE);
	String _SCALA = "[SCALA]";
	codiciList.add(_SCALA);
	String _PIANO = "[PIANO]";
	codiciList.add(_PIANO);
	String _INTERNO = "[INTERNO]";
	codiciList.add(_INTERNO);
	String _ESPONENTE_INTERNO = "[ESPONENTE_INTERNO]";
	codiciList.add(_ESPONENTE_INTERNO);
	String _FABBRICATO = "[FABBRICATO]";
	codiciList.add(_FABBRICATO);
	String _KM = "[KM]";
	codiciList.add(_KM);
	String _CAP = "[CAP]";
	codiciList.add(_CAP);
	String _FRAZIONE = "[FRAZIONE]";
	codiciList.add(_FRAZIONE);
	String _CIRCOSCRIZIONE = "[CIRCOSCRIZIONE]";
	codiciList.add(_CIRCOSCRIZIONE);
	String _QUARTIERE = "[QUARTIERE]";
	codiciList.add(_QUARTIERE);
	String _NOTE = "[NOTE]";
	codiciList.add(_NOTE);
	String _COMUNE_ISTANZA = "[COMUNE_ISTANZA]";
	codiciList.add(_COMUNE_ISTANZA);
	String _INQUALITADI = "[INQUALITADI]";
	codiciList.add(_INQUALITADI);
	String _INTERVENTODAALBEROPRIMAVOCE = "[INTERVENTODAALBEROPRIMAVOCE]";
	codiciList.add(_INTERVENTODAALBEROPRIMAVOCE);
	String _MOV_DATAPROT = "[MOV_DATAPROT]";
	codiciList.add(_MOV_DATAPROT);
	codiciList.add("[RIC_CN]");
	codiciList.add("[RIC_DN]");
	/////////////////////////////////////////////////////////////////////////////
	codiciList.add("[MOV_ENDO_ATTONUM]");
	codiciList.add("[MOV_ENDO_ATTODATA]");
	codiciList.add("[MOV_ENDO_ATTOTIPO]");
	codiciList.add("[MOV_ENDO_ATTOENTE]");
	codiciList.add("[MOV_ENDO_ATTONOTE]");
	codiciList.add("[LOCALIZZAZIONE_VIA]");
	codiciList.add("[RESP_ISTRUTORIA]");
	codiciList.add("[UTENTE_LOGGATO]");
	codiciList.add("[ALBERO_LIVELLO(");
	codiciList.add("[AMMINISTRAZIONE(");
	codiciList.add("[AMMINISTRAZIONE_PEC(");
	codiciList.add("[AMMINISTRAZIONE_MAIL(");
	codiciList.add("[AMMINISTRAZIONE_PIVA(");
	codiciList.add("[AMMINISTRAZIONE_REFERENTE(");
	codiciList.add("[TEL_RESP_ISTRUTORIA]");
	codiciList.add("[POS_ARCHIVIO]");
	codiciList.add("[RIC_TEL]");
	codiciList.add("[INTERMEDIARIO]");
	codiciList.add("[INTERM_CF]");
	codiciList.add("[INTERM_TEL]");
	codiciList.add("[INTERM_MAIL]");
	codiciList.add("[INTERM_PEC]");
	codiciList.add("[RIC_MAIL]");
	codiciList.add("[RIC_PEC]");
	codiciList.add("[IND_RESP_ISTRUTTORIA]");
	codiciList.add("[MAIL_RESP_ISTRUTTORIA]");
	codiciList.add("[NUM_PRATICA_PADRE]");
	codiciList.add("[ANNO_ISTANZA]");
	codiciList.add("[ALLEGATI_MOVIMENTO]");
	codiciList.add("[ZIPLOGICO_NUM_FILE]");
	codiciList.add(ZipLogicoTabellaHashMailResolver.TAG);
	codiciList.add("[DATASCADENZAISTANZA]");
	return codiciList;
    }

    private static String alberoprocPattern = "(\\[ALBERO_LIVELLO\\((\\d)*\\)\\])";
    private static Pattern pattern = Pattern.compile(alberoprocPattern);

    private static Map<String, String> trovaSegnapostoAlbero(String testoOggettoCorpo) {

	Matcher matcher = pattern.matcher(testoOggettoCorpo);
	Map<String, String> map = new HashMap<String, String>();
	while (matcher.find()) {
	    map.put(matcher.group(1), matcher.group(2));
	}
	return map;
    }

    public static void main(String[] args) {

	String testosegnaposto = "testo lungo [ALBERO_LIVELLO(1)] [ALBERO_LIVELLO(1)] altro testo [ALBERO_LIVELLO(2)] fine";
	// testosegnaposto = "testo lungo  fine";
	System.out.println(trovaSegnapostoAlbero(testosegnaposto));
	//	if (matcher.matches()) {
	//	    String hours = matcher.replaceAll("$1");
	//	    System.out.println(hours);
	//	}
    }

    @Override
    public Mailtipo replaceOggettoCorpo(Mailtipo mailtipo, Istanze istanza, Movimenti movimento) {

	return this.replaceOggettoCorpo(mailtipo, istanza, movimento, null);
    }

    @Override
    public Mailtipo replaceOggettoCorpo(Mailtipo mailtipo, Istanze istanza, Movimenti movimento, Sorteggitestata sorteggio) {

	Mailtipo mailtipoReplace = new Mailtipo();
	String oggetto = mailtipo.getOggetto();
	String corpo = mailtipo.getCorpo();
	String oggettoReplace = "";
	String corpoReplace = "";
	/*
	 * CODICI che devono essere sostituiti
	 */
	List<String> codiciList = getCodici();
	/*
	 * Variabili da sostituire
	 */
	String richiedenteReplace1 = "";
	String indirizzoReplace2 = "";
	String cittaReplace3 = "";
	String capReplace4 = "";
	String provinciaReplace5 = "";
	String dataIstanzaReplace6 = "";
	String nprotocolloReplace7 = "";
	String dataprotocolloReplace8 = "";
	String tipointerventoReplace9 = "";
	String proceduraReplace10 = "";
	String areaReplace11 = "";
	String codicelottoReplace12 = "";
	String lavoriReplace13 = "";
	String foglioReplace14 = "";
	String particellaReplace15 = "";
	String subReplace16 = "";
	String responsabileReplace17 = "";
	String responsabileprocReplace18 = "";
	String inventarioProcListReplace19 = "";
	String numeroistanzaReplace20 = "";
	String impiantoReplace21 = "";
	String istanzestradarioCivicoReplace22 = "";
	String stradarioReplace23 = "";
	String passwordReplace24 = "";
	String flagviaReplace25 = "";
	String varianteprReplace26 = "";
	String dataodiernaReplace27 = "";
	String tecnicoReplace28 = "";
	String soggetticollegatiReplace29 = "";
	String settoriReplace30 = "";
	String attivitaReplace31 = "";
	String movimentoReplace32 = "";
	String inventarioprocedimentiReplace33 = "";
	String numerodataprotocolloReplace34 = "";
	String esitoReplace35 = "";
	String parereReplace36 = "";
	String datamovReplace37 = "";
	String autnumReplace38 = "";
	String autdataReplace39 = "";
	String istpeopleReplace40 = "";
	// Nuove sostituizioni
	String sorteggidettagliodataReplace41 = "";
	String sorteggidettagliodescrReplace42 = "";
	String datiGeneraliDenominazione43 = "";
	String datiSportelloDenominazione44 = "";
	String aziendaRichiedenteDenominazione45 = "";
	String aziendaRichiedenteCF46 = "";
	String aziendaRichiedentePI47 = "";
	String codicepraticatel48 = "";
	String codAccreditamento49 = "";
	//codice fiscale del richiedente.
	String richiedenteCF50 = "";
	//partita iva del richiedente.
	String richiedentePI51 = "";
	//identificativo univoco del sistema di protocollazione integrato.
	String protocolloIdMov52 = "";
	//numero del protocollo (senza /anno)
	String numeroProtocolloMov53 = "";
	//anno del protocollo
	String annoProtocolloMov54 = "";
	// Dati sulla localizzazione estesa
	String localizzazioneEstesa55 = "";
	//Dati dell'istanza : comune dell'istanza
	String comuneIstanza69 = "";
	//Dati dell'istanza : in qualitità di (tipisoggetto.tiposoggetto)
	String tipisoggetto70 = "";
	//Dati dell'istanza : rappresneta il primo livello gerarchico della voce dell'albero scelta
	String nodopadreIntervento71 = "";
	String dataProtocolloMovimento72 = "";
	// Mappa che conterrà i valori relativi alle informazioni della localizzazione dell'istanza
	//// "[ESPONENTE]";[COLORE]";"[SCALA]";"[PIANO]"; "[INTERNO]";"[ESPONENTE_INTERNO]";"[FABBRICATO]";"[KM]";
	// "[CAP]";"[FRAZIONE]";"[CIRCOSCRIZIONE]";"[QUARTIERE]"; "[NOTE]";
	String richiedenteComuneNascita73 = "";
	String richiedenteDataNascita74 = "";
	// Dati del movimento (Endo procedimenti)
	// [MOV_ENDO_ATTONUM],[MOV_ENDO_ATTODATA],[MOV_ENDO_ATTOTIPO],[MOV_ENDO_ATTOENTE],[MOV_ENDO_ATTONOTE]
	String movEndoNumeroAtto75 = "";
	String movEndoDataAtto76 = "";
	String movEndoTipoAtto77 = "";
	String movEndoEnteAtto78 = "";
	String movEndoNoteAtto79 = "";
	String localizzazioneVia80 = "";
	String responsabileIstruttoria81 = "";
	String utenteLoggato82 = "";
	// Campi amministrazione
	String descAmministrazione84 = "";
	String pecAmministrazione85 = "";
	String emailAmministrazione86 = "";
	String piAmministrazione87 = "";
	String referenteAmministrazione88 = "";
	String telefonoRespIstruttoria89 = "";
	String posizioneArchivio90 = "";
	String richiedenteTelefono91 = "";
	String descIntermediario92 = "";
	String cfIntermediario93 = "";
	String telIntermediario94 = "";
	String emailIntermediario95 = "";
	String pecIntermediario96 = "";
	String ric_email97 = "";
	String ric_pec98 = "";
	String indirizzoRespIstruttoria99 = "";
	String mailRespIstruttoria100 = "";
	String numPraticaPadre101 = ""; //codiciList.add("[NUM_PRATICA_PADRE]")
	String annoPratica102 = "";
	String allegatiMovimento103 = "";
	String zipLogicoNumFile104 = "";
	String zipLogicoTabellaHash105 = "";
	String dataScadenzaIstanza106 = "";
	Map<String, String> infoLocalizzazioneIstanza = new HashMap<String, String>();
	Istanze istanze = null;
	Map<String, ISegnapostoMailResolver> resolver = new SegnapostoMailFactory(this.movimentiZipLogicoService, this.segnapostoService)
		.getResolvers(movimento);
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	if (EntityUtils.getNestedProperty(movimento, "id.codice") != null) {
	    Movimenti mov = movimentiService.findById(movimento.getId());
	    istanze = istanzeService.findById(mov.getIstanza().getId());
	    movimentoReplace32 = mov.getMovimento();
	    Inventarioprocedimenti inventarioprocedimenti = mov.getEndoprocedimento();
	    if (inventarioprocedimenti != null) {
		inventarioprocedimentiReplace33 = inventarioprocedimenti.getProcedimento();
	    }
	    if (mov.getNumeroprotocollo() != null) {
		numerodataprotocolloReplace34 = mov.getNumeroprotocollo();
		// numero del protocollo (senza /anno)
		String[] values = mov.getNumeroprotocollo().split("/");
		numeroProtocolloMov53 = values[0];
	    }
	    // Popolo con la data del protocollo:
	    //	1- Il campo data (gg/MM/yyyy) del segnaposto [34] numerodataprotocolloReplace34 (concateno la data al numero)
	    //	2- In campo anno (yyyy) del segnaposto [54] annoProtocolloMov54
	    //	3- il campo data (gg/MM/yyyy) del segnaposto [MOV_DATAPROT] dataProtocolloMovimento72 (riporto solo la data)
	    if (mov.getDataprotocollo() != null) {
		numerodataprotocolloReplace34 = numerodataprotocolloReplace34.concat(" ");
		numerodataprotocolloReplace34 = numerodataprotocolloReplace34.concat(format.format(mov.getDataprotocollo()));
		//anno del protocollo
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
		annoProtocolloMov54 = simpleDateformat.format(mov.getDataprotocollo());
		// data del protocollo formattata come gg/MM/yyyy
		dataProtocolloMovimento72 = format.format(mov.getDataprotocollo());
	    }
	    if (mov.getEsito() != null) {
		if (mov.getEsito().booleanValue()) {
		    esitoReplace35 = "POSITIVO";
		} else {
		    esitoReplace35 = "NEGATIVO";
		}
	    }
	    if (mov.getParere() != null) {
		parereReplace36 = mov.getParere();
	    }
	    //identificativo univoco del sistema di protocollazione integrato.
	    if (mov.getFkidprotocollo() != null) {
		protocolloIdMov52 = mov.getFkidprotocollo();
	    }
	    // Nel caso di invio mail automatica (comunicazioni da movimento) potremmo notificare l'inserimento di una nuova scadenza o di un contromovimento
	    // e la data non è presente
	    if (mov.getData() != null) {
		datamovReplace37 = format.format(mov.getData());
	    }
	    //Estraggo i campi dell'endo procedimento associato al movimento 
	    // Controllo se il movimento ha associato un endo procedimento
	    if (EntityUtils.getNestedProperty(mov.getEndoprocedimento(), "id.codice") != null) {
		Integer codiceEndo = mov.getEndoprocedimento().getId().getCodice();
		Istanzeprocedimenti istanzeprocedimenti = istanzeprocedimentiService
			.findById(new IstanzeprocedimentiId(mov.getIstanza().getId().getCodice(), codiceEndo));
		if (EntityUtils.getNestedProperty(istanzeprocedimenti, "id.codiceistanza") != null) {
		    // estraggo i campi :
		    //[MOV_ENDO_ATTONUM]
		    //[MOV_ENDO_ATTODATA]
		    //[MOV_ENDO_ATTOTIPO]
		    //[MOV_ENDO_ATTOENTE]
		    //[MOV_ENDO_ATTONOTE]
		    movEndoNumeroAtto75 = StringUtils.isNotBlank(istanzeprocedimenti.getProtNum()) ? istanzeprocedimenti.getProtNum() : "";
		    if (istanzeprocedimenti.getProtDel() != null) {
			movEndoDataAtto76 = format.format(istanzeprocedimenti.getProtDel());
		    }
		    movEndoTipoAtto77 = StringUtils.isNotBlank(istanzeprocedimenti.getTipoAtto()) ? istanzeprocedimenti.getTipoAtto() : "";
		    movEndoEnteAtto78 = StringUtils.isNotBlank(istanzeprocedimenti.getRilasciatoDa()) ? istanzeprocedimenti.getRilasciatoDa() : "";
		    movEndoNoteAtto79 = StringUtils.isNotBlank(istanzeprocedimenti.getNote()) ? istanzeprocedimenti.getNote() : "";
		}
	    }
	    List<Movimentiallegati> movalls = movimentiallegatiService.findByMovimento(mov.getId().getCodice());
	    boolean almenoUno = false;
	    allegatiMovimento103 = "";
	    for (Movimentiallegati ma : movalls) {
		if (ma.getOggetto() != null && ma.getOggetto().getId() != null && ma.getOggetto().getId().getCodice() != null) {
		    almenoUno = true;
		    Oggetti findByIdLazy = oggettiService.findByIdLazy(new PkId(ma.getOggetto().getId().getCodice()));
		    allegatiMovimento103 += "<li>" + findByIdLazy.getNomefile() + "</li>";
		}
	    }
	    if (almenoUno) {
		allegatiMovimento103 = "<ul>" + allegatiMovimento103 + "</ul>";
	    }
	    zipLogicoNumFile104 = this.movimentiZipLogicoService.contaDocumenti(mov.getId().getCodice()).toString();
	    zipLogicoTabellaHash105 = "";
	    ISegnapostoMailResolver resZipLogico = resolver.get(ZipLogicoTabellaHashMailResolver.TAG);
	    if (resZipLogico != null) {
		zipLogicoTabellaHash105 = resZipLogico.sostituisci();
	    }
	}
	if (EntityUtils.getNestedProperty(istanza, "id.codice") != null) {
	    istanze = istanzeService.findById(istanza.getId());
	}
	if (istanze != null) {
	    oggetto = this.popolaDyn2Campi(oggetto, istanze.getId().getCodice());
	    corpo = this.popolaDyn2Campi(corpo, istanze.getId().getCodice());
	    /**
	     * TODO 38 e 39
	     */
	    // Controllo nelle domandestc cercando per codiceistanza
	    List<Domandestc> d = domandestcService.findByIstanza(istanze.getId().getCodice());
	    if (d != null) {
		if (d.size() > 0) {
		    istpeopleReplace40 = d.get(0).getIdDomandamitt();
		} else {
		    // se per codice istanza non trovo il valore, controllo per codice istanza prenotato
		    // Si è reso necessario in caso di protocollazione automatica. Quando va a protocollare la pratica non risulta ancora inserita 
		    // dal sistema e DomandeSTC non ha ancora il campo codiceistanza popolato.
		    d = domandestcService.findByCodiceIstanzaPrenotato(istanze.getId().getCodice());
		    if (!d.isEmpty()) {
			istpeopleReplace40 = d.get(0).getIdDomandamitt();
		    }
		}
	    }
	    Sorteggidettaglio sorteggidettaglio = sorteggidettaglioService.findByIstanza(istanze);
	    if (sorteggidettaglio != null) {
		if (sorteggidettaglio.getSorteggitestata() != null) {
		    if (sorteggidettaglio.getSorteggitestata().getStDatasorteggio() != null) {
			sorteggidettagliodataReplace41 = format.format(sorteggidettaglio.getSorteggitestata().getStDatasorteggio());
		    }
		    sorteggidettagliodescrReplace42 = sorteggidettaglio.getSorteggitestata().getStDescrizione();
		}
	    }
	    Anagrafe richiedente = istanze.getRichiedente();
	    if (richiedente != null) {
		if (richiedente.getNominativo() != null) {
		    richiedenteReplace1 = richiedente.getNominativo() + " ";
		}
		if (richiedente.getNome() != null) {
		    richiedenteReplace1 = richiedenteReplace1.concat(richiedente.getNome());
		}
		if (richiedente.getIndirizzo() != null) {
		    indirizzoReplace2 = richiedente.getIndirizzo();
		}
		if (richiedente.getCitta() != null) {
		    cittaReplace3 = richiedente.getCitta();
		}
		if (richiedente.getCap() != null) {
		    capReplace4 = richiedente.getCap();
		}
		if (richiedente.getProvincia() != null) {
		    provinciaReplace5 = richiedente.getProvincia();
		}
		if (StringUtils.isNotBlank(richiedente.getCodicefiscale())) {
		    richiedenteCF50 = richiedente.getCodicefiscale();
		}
		if (StringUtils.isNotBlank(richiedente.getPartitaiva())) {
		    richiedentePI51 = richiedente.getPartitaiva();
		}
		if (richiedente.getComuneNascita() != null) {
		    richiedenteComuneNascita73 = StringUtils.defaultString(richiedente.getComuneNascita().getComune());
		}
		if (richiedente.getDatanascita() != null) {
		    SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
		    String datanascita = "";
		    try {
			datanascita = sdf.format(richiedente.getDatanascita());
		    } catch (Exception e) {
			log.error("replaceOggettoCorpo# errore nella conversione della data {}: {}", richiedente.getDatanascita(), e);
		    }
		    richiedenteDataNascita74 = datanascita;
		}
		richiedenteTelefono91 = StringUtils.defaultIfEmpty(richiedente.getTelefono(), "");
		ric_email97 = StringUtils.defaultIfEmpty(richiedente.getEmail(), "");
		ric_pec98 = StringUtils.defaultIfEmpty(richiedente.getPec(), "");
	    }
	    // Dati del professionista (INTERMEDIARIO)
	    if (EntityUtils.getNestedProperty(istanze.getProfessionista(), "id.codice") != null) {
		Anagrafe professionista = istanze.getProfessionista();
		if (StringUtils.isNotBlank(professionista.getNominativo())) {
		    descIntermediario92 = professionista.getNominativo() + " ";
		}
		if (StringUtils.isNotBlank(professionista.getNome())) {
		    descIntermediario92 = descIntermediario92.concat(professionista.getNome());
		}
		cfIntermediario93 = StringUtils.defaultIfEmpty(professionista.getCodicefiscale(), "");
		telIntermediario94 = StringUtils.defaultIfEmpty(professionista.getTelefono(), "");
		emailIntermediario95 = StringUtils.defaultIfEmpty(professionista.getEmail(), "");
		pecIntermediario96 = StringUtils.defaultIfEmpty(professionista.getPec(), "");
	    }
	    if (istanze.getData() != null) {
		dataIstanzaReplace6 = format.format(istanze.getData());
		annoPratica102 = new SimpleDateFormat("yyyy").format(istanze.getData());
	    }
	    if (istanze.getNumeroprotocollo() != null) {
		nprotocolloReplace7 = istanze.getNumeroprotocollo();
	    }
	    if (istanze.getCodicepraticatel() != null) {
		codicepraticatel48 = istanze.getCodicepraticatel();
	    }
	    if (istanze.getDataprotocollo() != null) {
		dataprotocolloReplace8 = format.format(istanze.getDataprotocollo());
	    }
	    if (istanze.getAlberoproc() != null) {
		Alberoproc alberoproc = alberoprocService.findById(istanze.getAlberoproc().getId());
		tipointerventoReplace9 = alberoproc.getVwAlberoproc().getScDescrizione();
	    }
	    Tipiprocedure tipiprocedure = istanze.getProcedura();
	    if (tipiprocedure != null) {
		proceduraReplace10 = tipiprocedure.getProcedura();
	    }
	    Istanzearee istanzearee = istanzeareeService.findByPrimarioIstanza(istanze);
	    if (istanzearee != null) {
		Aree area = istanzearee.getArea();
		areaReplace11 = area.getDenominazione();
	    }
	    if (istanze.getCodicelotto() != null) {
		codicelottoReplace12 = istanze.getCodicelotto().toString();
	    }
	    if (istanze.getLavori() != null) {
		lavoriReplace13 = istanze.getLavori();
	    }
	    Istanzemappali istanzemappali = istanzemappaliService.findByPrimarioIstanza(istanze);
	    if (istanzemappali != null) {
		if (istanzemappali.getFoglio() != null) {
		    foglioReplace14 = istanzemappali.getFoglio();
		}
		if (istanzemappali.getParticella() != null) {
		    particellaReplace15 = istanzemappali.getParticella();
		}
		if (istanzemappali.getSub() != null) {
		    subReplace16 = istanzemappali.getSub();
		}
	    }
	    Responsabili responsabile = istanze.getResponsabile();
	    if (responsabile != null) {
		responsabileReplace17 = responsabile.getResponsabile();
	    }
	    Responsabili responsabileproc = istanze.getResponsabileProcedimento();
	    ConfigurazioneId id = new ConfigurazioneId(ORMHelper.getIdcomune(), ORMHelper.getSoftware());
	    Configurazione configurazione = configurazioneService.findById(id);
	    if (responsabileproc != null) {
		responsabileprocReplace18 = responsabileproc.getResponsabile();
	    } else {
		if (EntityUtils.getNestedProperty(configurazione.getResponsabili(), "id.codice") != null) {
		    Integer codiceresponsabile = configurazione.getResponsabili().getId().getCodice();
		    Responsabili responsabileConf = responsabiliService.findById(new PkId(codiceresponsabile.intValue()));
		    responsabileprocReplace18 = responsabileConf.getResponsabile();
		} else {
		    responsabileprocReplace18 = configurazione.getResponsabile();
		}
	    }
	    datiSportelloDenominazione44 = configurazione.getDenominazione();
	    codAccreditamento49 = configurazione.getCodiceaccreditamento();
	    if (StringUtils.isBlank(codAccreditamento49)) {
		id.setSoftware(WebConstants.SOFTWARE_TT);
		Configurazione configurazioneGen = configurazioneService.findById(id);
		datiGeneraliDenominazione43 = configurazioneGen.getDenominazione();
		codAccreditamento49 = configurazioneGen.getCodiceaccreditamento();
	    }
	    List<Istanzeprocedimenti> istanzeprocedimentis = istanzeprocedimentiService.findByIstanze(istanze);
	    for (Istanzeprocedimenti istanzeprocedimenti : istanzeprocedimentis) {
		String amministrazione = "";
		if (EntityUtils.getNestedProperty(istanzeprocedimenti.getInventarioprocedimenti().getAmministrazioni(), "id.codice") != null) {
		    amministrazione = istanzeprocedimenti.getInventarioprocedimenti().getAmministrazioni().getAmministrazione();
		}
		inventarioProcListReplace19 = inventarioProcListReplace19.concat(amministrazione).concat(" - ")
			.concat(istanzeprocedimenti.getInventarioprocedimenti().getProcedimento()).concat("<br/>");
	    }
	    numeroistanzaReplace20 = istanze.getNumeroistanza();
	    if (istanze.getImpianto() != null) {
		impiantoReplace21 = istanze.getImpianto().getImpianto();
	    }
	    Istanzestradario istanzestradario = istanzestradarioService.findPrimarioByCodiceIstanza(istanze.getId().getCodice());
	    if (istanzestradario != null) {
		if (istanzestradario.getCivico() != null) {
		    istanzestradarioCivicoReplace22 = istanzestradario.getCivico();
		}
		if (istanzestradario.getStradario() != null) {
		    Stradario stradario = istanzestradario.getStradario();
		    if (stradario.getPrefisso() != null) {
			stradarioReplace23 = stradarioReplace23.concat(stradario.getPrefisso()).concat(" ");
			//GIANPAOLO
			localizzazioneVia80 = localizzazioneVia80.concat(stradario.getPrefisso()).concat(" ");
		    }
		    if (stradario.getDescrizione() != null) {
			stradarioReplace23 = stradarioReplace23.concat(stradario.getDescrizione()).concat(" ");
			localizzazioneVia80 = localizzazioneVia80.concat(stradario.getDescrizione());
		    }
		    if (stradario.getLocfraz() != null) {
			stradarioReplace23 = stradarioReplace23.concat(stradario.getLocfraz());
		    }
		}
		// Gestione creazione stringa località estesa (segna posto [LOC_ESTESA]) quando esiste il primario
		localizzazioneEstesa55 = createSegnapostoLOC_ESTESA(istanzestradario);
		// Gestione segnaposto delle singole informazioni contenute in istanze stradario primazio :
		// "[ESPONENTE]";[COLORE]";"[SCALA]";"[PIANO]"; "[INTERNO]";"[ESPONENTE_INTERNO]";"[FABBRICATO]";"[KM]";
		// "[CAP]";"[FRAZIONE]";"[CIRCOSCRIZIONE]";"[QUARTIERE]"; "[NOTE]";
		infoLocalizzazioneIstanza = populateInformazioneIstanzestradario(istanzestradario);
	    } else {
		// non è stato specificato uno stradario primario cerco il primo dei non primario
		List<Istanzestradario> istanzestradarios = istanzestradarioService.findByIstanza(istanze.getId().getCodice());
		for (Istanzestradario istanzestradario2 : istanzestradarios) {
		    if (istanzestradario2.getCivico() != null) {
			istanzestradarioCivicoReplace22 = istanzestradario2.getCivico();
		    }
		    if (istanzestradario2.getStradario() != null) {
			Stradario stradario = istanzestradario2.getStradario();
			if (stradario.getPrefisso() != null) {
			    stradarioReplace23 = stradarioReplace23.concat(stradario.getPrefisso()).concat(" ");
			}
			if (stradario.getDescrizione() != null) {
			    stradarioReplace23 = stradarioReplace23.concat(stradario.getDescrizione()).concat(" ");
			}
			if (stradario.getLocfraz() != null) {
			    stradarioReplace23 = stradarioReplace23.concat(stradario.getLocfraz());
			}
		    }
		    // Gestione creazione stringa località estesa (segna posto [LOC_ESTESA]) quando non esiste il primario
		    localizzazioneEstesa55 = createSegnapostoLOC_ESTESA(istanzestradario2);
		    // Gestione segnaposto delle singole informazioni contenute in istanze stradario non primazio :
		    // "[ESPONENTE]";[COLORE]";"[SCALA]";"[PIANO]"; "[INTERNO]";"[ESPONENTE_INTERNO]";"[FABBRICATO]";"[KM]";
		    // "[CAP]";"[FRAZIONE]";"[CIRCOSCRIZIONE]";"[QUARTIERE]"; "[NOTE]";
		    infoLocalizzazioneIstanza = populateInformazioneIstanzestradario(istanzestradario2);
		    break;
		}
	    }
	    if (istanze.getPassword() != null) {
		passwordReplace24 = istanze.getPassword();
	    }
	    if (istanze.getFlagvia() != null) {
		Short flagvia = istanze.getFlagvia();
		if (flagvia == 0) {
		    flagviaReplace25 = "Non prevista";
		}
		if (flagvia == 1) {
		    flagviaReplace25 = "VIA Regionale";
		}
		if (flagvia == 2) {
		    flagviaReplace25 = "VIA Nazionale";
		}
		if (flagvia == 3) {
		    flagviaReplace25 = "VIA Comunale";
		}
	    }
	    if (istanze.getVariantepr() == null) {
		varianteprReplace26 = "No";
	    } else {
		if (istanze.getVariantepr()) {
		    varianteprReplace26 = "Si";
		} else {
		    varianteprReplace26 = "No";
		}
	    }
	    dataodiernaReplace27 = format.format(new Date());
	    Anagrafe professionista = istanze.getProfessionista();
	    if (professionista != null) {
		tecnicoReplace28 = tecnicoReplace28.concat(StringUtils.defaultIfEmpty(professionista.getNominativo(), "")).concat(" ")
			.concat(StringUtils.defaultIfEmpty(professionista.getNome(), ""));
	    }
	    List<Istanzerichiedenti> istanzerichiedentis = istanzerichiedentiService.findByIstanza(istanze);
	    for (Istanzerichiedenti istanzerichiedenti : istanzerichiedentis) {
		if (istanzerichiedenti.getRichiedente() != null) {
		    if (istanzerichiedenti.getRichiedente().getNominativo() != null) {
			soggetticollegatiReplace29 = soggetticollegatiReplace29.concat(istanzerichiedenti.getRichiedente().getNominativo());
		    }
		    if (istanzerichiedenti.getRichiedente().getNome() != null) {
			soggetticollegatiReplace29 = soggetticollegatiReplace29.concat(" ")
				.concat(StringUtils.defaultIfEmpty(istanzerichiedenti.getRichiedente().getNome(), "")).concat("<br/>");
		    }
		}
	    }
	    // if (istanze.getSettore() != null) {
	    // settoriReplace30 = istanze.getSettore().getSettore();
	    // }
	    // if (istanze.getAttivita() != null) {
	    // attivitaReplace31 = istanze.getAttivita().getIstat();
	    // }
	    Anagrafe aziendaRich = istanze.getTitolarelegale();
	    if (aziendaRich != null) {
		aziendaRichiedenteDenominazione45 = aziendaRich.getNominativo() != null ? aziendaRich.getNominativo() : "";
		aziendaRichiedenteDenominazione45 += aziendaRich.getFormagiuridica() != null
			? " " + aziendaRich.getFormagiuridica().getFormagiuridica()
			: "";
		aziendaRichiedenteCF46 = aziendaRich.getCodicefiscale() != null ? aziendaRich.getCodicefiscale() : "";
		aziendaRichiedentePI47 = aziendaRich.getPartitaiva() != null ? aziendaRich.getPartitaiva() : "";
		if (StringUtils.isNotBlank(aziendaRichiedentePI47)) {
		    aziendaRichiedenteCF46 = aziendaRichiedentePI47;
		}
	    }
	    // Creazione del tag COMUNE_ISTANZA, rappresenta il comune a cui è associata l'istanza (codicecomune)
	    if (istanze.getComune() != null) {
		comuneIstanza69 = istanze.getComune().getComune();
	    }
	    // Creazione del tag INQUALITADI
	    if (EntityUtils.getNestedProperty(istanze.getTipisoggetto(), "id.codice") != null) {
		tipisoggetto70 = istanze.getTipisoggetto().getTiposoggetto();
	    }
	    // Creazione del tag INTERVENTODAALBEROPRIMAVOCE, rappresenta il primo livello gerarchico della voce dell'albero scelta.
	    if (EntityUtils.getNestedProperty(istanze.getAlberoproc(), "id.codice") != null) {
		nodopadreIntervento71 = alberoprocService.findDescrizionePrimaVoceAlberoproc(istanze.getAlberoproc().getScCodice());
	    }
	    // Creazione del tag RESP_ISTRUTTORIA, rappresenta il responsabile istruttore associato all'istanza
	    if (EntityUtils.getNestedProperty(istanze.getIstruttore(), "id.codice") != null) {
		responsabileIstruttoria81 = istanze.getIstruttore().getResponsabile();
		telefonoRespIstruttoria89 = StringUtils.isNotBlank(istanze.getIstruttore().getTelefonolavoro())
			? istanze.getIstruttore().getTelefonolavoro()
			: StringUtils.defaultIfEmpty(istanze.getIstruttore().getTelefonocellulare(), "");
		indirizzoRespIstruttoria99 = StringUtils.defaultIfEmpty(istanze.getIstruttore().getIndirizzo(), "");
		mailRespIstruttoria100 = StringUtils.defaultIfEmpty(istanze.getIstruttore().getEmail(), "");
	    }
	    if (EntityUtils.getNestedProperty(istanze, "id.codice") != null) {
		numPraticaPadre101 = StringUtils.defaultString(istanzeService.findNumeroIstanzaPadre(istanze.getId().getCodice()));
	    }
	    // Creazione del tag [UTENTE_LOGGATO]
	    Responsabili resp = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    if (EntityUtils.getNestedProperty(resp, "id.codice") != null) {
		utenteLoggato82 = resp.getResponsabile();
	    }
	    posizioneArchivio90 = StringUtils.defaultIfEmpty(istanze.getPosizionearchivio(), "");
	    if (istanze.getIstanzeTempistica() != null) {
		dataScadenzaIstanza106 = FormatUtils.dateFormat(istanze.getIstanzeTempistica().getDatafine());
	    }
	}
	if (sorteggio != null) {
	    SegnapostiSorteggiBean segnapostiSorteggi = new SegnapostiSorteggiResolver(sorteggio).get();
	    sorteggidettagliodataReplace41 = Utilities.formatDate(segnapostiSorteggi.getDataSorteggio(), false);
	    sorteggidettagliodescrReplace42 = segnapostiSorteggi.getDescrizione();
	}
	/*
	 * REPLACE OGGETTO
	 */
	oggetto = StringUtils.defaultIfEmpty(oggetto, "");
	oggettoReplace = oggettoReplace.concat(oggetto);
	oggettoReplace = oggettoReplace.replace(codiciList.get(1), StringUtils.defaultIfEmpty(richiedenteReplace1, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(2), StringUtils.defaultIfEmpty(indirizzoReplace2, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(3), StringUtils.defaultIfEmpty(cittaReplace3, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(4), StringUtils.defaultIfEmpty(capReplace4, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(5), StringUtils.defaultIfEmpty(provinciaReplace5, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(6), StringUtils.defaultIfEmpty(dataIstanzaReplace6, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(7), StringUtils.defaultIfEmpty(nprotocolloReplace7, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(8), StringUtils.defaultIfEmpty(dataprotocolloReplace8, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(9), StringUtils.defaultIfEmpty(tipointerventoReplace9, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(10), StringUtils.defaultIfEmpty(proceduraReplace10, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(11), StringUtils.defaultIfEmpty(areaReplace11, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(12), StringUtils.defaultIfEmpty(codicelottoReplace12, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(13), StringUtils.defaultIfEmpty(lavoriReplace13, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(14), StringUtils.defaultIfEmpty(foglioReplace14, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(15), StringUtils.defaultIfEmpty(particellaReplace15, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(16), StringUtils.defaultIfEmpty(subReplace16, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(17), StringUtils.defaultIfEmpty(responsabileReplace17, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(18), StringUtils.defaultIfEmpty(responsabileprocReplace18, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(19), StringUtils.defaultIfEmpty(inventarioProcListReplace19, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(20), StringUtils.defaultIfEmpty(numeroistanzaReplace20, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(21), StringUtils.defaultIfEmpty(impiantoReplace21, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(22), StringUtils.defaultIfEmpty(istanzestradarioCivicoReplace22, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(23), StringUtils.defaultIfEmpty(stradarioReplace23, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(24), StringUtils.defaultIfEmpty(passwordReplace24, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(25), StringUtils.defaultIfEmpty(flagviaReplace25, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(26), StringUtils.defaultIfEmpty(varianteprReplace26, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(27), StringUtils.defaultIfEmpty(dataodiernaReplace27, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(28), StringUtils.defaultIfEmpty(tecnicoReplace28, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(29), StringUtils.defaultIfEmpty(soggetticollegatiReplace29, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(30), StringUtils.defaultIfEmpty(settoriReplace30, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(31), StringUtils.defaultIfEmpty(attivitaReplace31, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(32), StringUtils.defaultIfEmpty(movimentoReplace32, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(33), StringUtils.defaultIfEmpty(inventarioprocedimentiReplace33, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(34), StringUtils.defaultIfEmpty(numerodataprotocolloReplace34, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(35), StringUtils.defaultIfEmpty(esitoReplace35, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(36), StringUtils.defaultIfEmpty(parereReplace36, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(37), StringUtils.defaultIfEmpty(datamovReplace37, ""));
	// [38(codReg)] e [39(codReg)]
	int indice38 = oggettoReplace.indexOf(codiciList.get(38));
	if (indice38 != -1) {
	    indice38 = indice38 + 4;
	    int indiceparentesi = oggettoReplace.indexOf(")]");
	    String codreg = oggettoReplace.substring(indice38, indiceparentesi);
	    Integer codReg = Integer.parseInt(codreg);
	    List<Autorizzazioni> autList = autorizzazioniService.findByIstanzaRegistro(istanze, codReg);
	    if (autList != null && !autList.isEmpty()) {
		autnumReplace38 = autList.get(0).getAutoriznumero();
		oggettoReplace = oggettoReplace.replace(codiciList.get(38) + codreg + ")]", StringUtils.defaultIfEmpty(autnumReplace38, ""));
	    } else {
		oggettoReplace = oggettoReplace.replace(codiciList.get(38) + codreg + ")]", "");
	    }
	}
	int indice39 = oggettoReplace.indexOf(codiciList.get(39));
	if (indice39 != -1) {
	    indice39 = indice39 + 4;
	    int indiceparentesi = oggettoReplace.indexOf(")]");
	    String codreg = oggettoReplace.substring(indice39, indiceparentesi);
	    Integer codReg = Integer.parseInt(codreg);
	    List<Autorizzazioni> autList = autorizzazioniService.findByIstanzaRegistro(istanze, codReg);
	    if (autList != null && !autList.isEmpty()) {
		autdataReplace39 = format.format(autList.get(0).getAutorizdata());
		oggettoReplace = oggettoReplace.replace(codiciList.get(39) + codreg + ")]", StringUtils.defaultIfEmpty(autdataReplace39, ""));
	    } else {
		oggettoReplace = oggettoReplace.replace(codiciList.get(39) + codreg + ")]", "");
	    }
	}
	//	int indiceOggetto83 = oggettoReplace.indexOf(codiciList.get(83));
	//	if (indiceOggetto83 != -1) {
	//	    indiceOggetto83 = indiceOggetto83 + 16;
	//	    int indiceparentesi = oggettoReplace.indexOf(")]");
	//	    String num = oggettoReplace.substring(indiceOggetto83, indiceparentesi);
	//	    String descAlbero = "";
	//	    try {
	//		Integer _num = Integer.parseInt(num);
	//		descAlbero = alberoprocService.findDescrizioneInterventoFromLivello(istanze.getAlberoproc().getId().getCodice(), _num);
	//		oggettoReplace = oggettoReplace.replace(codiciList.get(83) + _num + ")]", StringUtils.defaultIfEmpty(descAlbero, ""));
	//	    } catch (NumberFormatException ne) {
	//		log.error("Codice livello albero non corretto, deve essere un numero");
	//	    }
	//	}
	oggettoReplace = oggettoReplace.replace(codiciList.get(40), StringUtils.defaultIfEmpty(istpeopleReplace40, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(41), StringUtils.defaultIfEmpty(sorteggidettagliodataReplace41, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(42), StringUtils.defaultIfEmpty(sorteggidettagliodescrReplace42, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(43), StringUtils.defaultIfEmpty(datiGeneraliDenominazione43, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(44), StringUtils.defaultIfEmpty(datiSportelloDenominazione44, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(45), StringUtils.defaultIfEmpty(aziendaRichiedenteDenominazione45, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(46), StringUtils.defaultIfEmpty(aziendaRichiedenteCF46, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(47), StringUtils.defaultIfEmpty(aziendaRichiedentePI47, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(48), StringUtils.defaultIfEmpty(codicepraticatel48, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(49), StringUtils.defaultIfEmpty(codAccreditamento49, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(50), StringUtils.defaultIfEmpty(richiedenteCF50, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(51), StringUtils.defaultIfEmpty(richiedentePI51, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(52), StringUtils.defaultIfEmpty(protocolloIdMov52, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(53), StringUtils.defaultIfEmpty(numeroProtocolloMov53, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(54), StringUtils.defaultIfEmpty(annoProtocolloMov54, ""));
	// replace nel sezione oggetto del segna posto [LOC_ESTESA] posizione 55 nella lista
	oggettoReplace = oggettoReplace.replace(codiciList.get(55), StringUtils.defaultIfEmpty(localizzazioneEstesa55, ""));
	// replace nel sezione oggetto dei segna posto 
	// [ESPONENTE] 		posizione 56 ;
	// [COLORE] 		posizione 57;
	// [SCALA]		posizione 58;
	// [PIANO]		posizione 59; 
	// [INTERNO]		posizione 60;
	// [ESPONENTE_INTERNO]	posizione 61;
	// [FABBRICATO]		posizione 62;
	// [KM]			posizione 63;
	// [CAP]		posizione 64;
	// [FRAZIONE] 		posizione 65;
	// [CIRCOSCRIZIONE] 	posizione 66;
	// [QUARTIERE] 		posizione 67; 
	// [NOTE]		posizione 68;
	oggettoReplace = oggettoReplace.replace(codiciList.get(56), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("ESPONENTE"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(57), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("COLORE"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(58), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("SCALA"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(59), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("PIANO"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(60), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("INTERNO"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(61),
		StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("ESPONENTE_INTERNO"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(62), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("FABBRICATO"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(63), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("KM"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(64), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("CAP"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(65), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("FRAZIONE"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(66), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("CIRCOSCRIZIONE"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(67), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("QUARTIERE"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(68), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("NOTE"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(69), StringUtils.defaultIfEmpty(comuneIstanza69, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(70), StringUtils.defaultIfEmpty(tipisoggetto70, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(71), StringUtils.defaultIfEmpty(nodopadreIntervento71, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(72), StringUtils.defaultIfEmpty(dataProtocolloMovimento72, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(73), StringUtils.defaultIfEmpty(richiedenteComuneNascita73, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(74), StringUtils.defaultIfEmpty(richiedenteDataNascita74, ""));
	// Replace oggetto campi del movimento riferiti all'endo
	oggettoReplace = oggettoReplace.replace(codiciList.get(75), StringUtils.defaultIfEmpty(movEndoNumeroAtto75, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(76), StringUtils.defaultIfEmpty(movEndoDataAtto76, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(77), StringUtils.defaultIfEmpty(movEndoTipoAtto77, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(78), StringUtils.defaultIfEmpty(movEndoEnteAtto78, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(79), StringUtils.defaultIfEmpty(movEndoNoteAtto79, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(80), StringUtils.defaultIfEmpty(localizzazioneVia80, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(81), StringUtils.defaultIfEmpty(responsabileIstruttoria81, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(82), StringUtils.defaultIfEmpty(utenteLoggato82, ""));
	// 83 
	Map<String, String> mappaOggettoIntervento = trovaSegnapostoAlbero(oggettoReplace);
	// Creazione del tag [-ALBERO_LIVELLO(N)-], indica la descrizione dell 'intervento a partire dal nodo foglia. La descrizione risale fino 
	// N livelli indietro, dove N è il parametro passato in fase di creazione del tag sul documento/lettera tipo
	if (!mappaOggettoIntervento.isEmpty()) {
	    for (Entry<String, String> sp : mappaOggettoIntervento.entrySet()) {
		String segnaposto = sp.getKey();
		String valore = sp.getValue();
		if (Utilities.isInteger(valore)) {
		    try {
			Integer _num = Integer.parseInt(valore);
			valore = alberoprocService.findDescrizioneInterventoFromLivello(istanze.getAlberoproc().getId().getCodice(), _num);
		    } catch (NumberFormatException e) {
			log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", segnaposto);
		    }
		}
		oggettoReplace = oggettoReplace.replace(segnaposto, valore);
	    }
	    //	    String argument = placeholder.substring(15, placeholder.length() - 1).trim();
	    //	    String val = "";
	    //	    try {
	    //		Integer _num = Integer.parseInt(argument);
	    //		val = alberoprocService.findDescrizioneInterventoFromLivello(data.getIstanza().getAlberoproc().getId().getCodice(), _num);
	    //	    } catch (NumberFormatException e) {
	    //		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    //		val = placeholder;
	    //	    }
	}
	// Amministrazione
	String segnapostoAmministrazione = createSegnaPostoConCodice(codiciList.get(84), oggettoReplace);
	Amministrazioni amministrazioni = getAmministrazione(segnapostoAmministrazione);
	if (amministrazioni != null) {
	    descAmministrazione84 = amministrazioni.getAmministrazione();
	    oggettoReplace = oggettoReplace.replace(segnapostoAmministrazione, StringUtils.defaultIfEmpty(descAmministrazione84, ""));
	} else {
	    oggettoReplace = oggettoReplace.replace(segnapostoAmministrazione, "");
	}
	//	String pecAmministrazione85 = "";
	String segnapostoAmministrazionePec = createSegnaPostoConCodice(codiciList.get(85), oggettoReplace);
	Amministrazioni amministrazioniPec = getAmministrazione(segnapostoAmministrazionePec);
	if (amministrazioniPec != null) {
	    pecAmministrazione85 = amministrazioniPec.getPec();
	    oggettoReplace = oggettoReplace.replace(segnapostoAmministrazionePec, StringUtils.defaultIfEmpty(pecAmministrazione85, ""));
	} else {
	    oggettoReplace = oggettoReplace.replace(segnapostoAmministrazionePec, "");
	}
	//	String emailAmministrazione86 = "";
	String segnapostoAmministrazioneMail = createSegnaPostoConCodice(codiciList.get(86), oggettoReplace);
	Amministrazioni amministrazioniMail = getAmministrazione(segnapostoAmministrazioneMail);
	if (amministrazioniMail != null) {
	    emailAmministrazione86 = amministrazioniMail.getEmail();
	    oggettoReplace = oggettoReplace.replace(segnapostoAmministrazioneMail, StringUtils.defaultIfEmpty(emailAmministrazione86, ""));
	} else {
	    oggettoReplace = oggettoReplace.replace(segnapostoAmministrazioneMail, "");
	}
	//	String piAmministrazione87 = "";
	String segnapostoAmministrazionePI = createSegnaPostoConCodice(codiciList.get(87), oggettoReplace);
	Amministrazioni amministrazioniPI = getAmministrazione(segnapostoAmministrazionePI);
	if (amministrazioniPI != null) {
	    piAmministrazione87 = amministrazioniPI.getPartitaiva();
	    oggettoReplace = oggettoReplace.replace(segnapostoAmministrazionePI, StringUtils.defaultIfEmpty(piAmministrazione87, ""));
	} else {
	    oggettoReplace = oggettoReplace.replace(segnapostoAmministrazionePI, "");
	}
	//	String referenteAmministrazione88 = "";
	String segnapostoAmministrazioneRef = createSegnaPostoConCodice(codiciList.get(88), oggettoReplace);
	Amministrazioni amministrazioniRef = getAmministrazione(segnapostoAmministrazioneRef);
	if (amministrazioniRef != null) {
	    referenteAmministrazione88 = amministrazioniRef.getReferente();
	    oggettoReplace = oggettoReplace.replace(segnapostoAmministrazioneRef, StringUtils.defaultIfEmpty(referenteAmministrazione88, ""));
	} else {
	    oggettoReplace = oggettoReplace.replace(segnapostoAmministrazioneRef, "");
	}
	oggettoReplace = oggettoReplace.replace(codiciList.get(89), StringUtils.defaultIfEmpty(telefonoRespIstruttoria89, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(90), StringUtils.defaultIfEmpty(posizioneArchivio90, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(91), StringUtils.defaultIfEmpty(richiedenteTelefono91, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(92), StringUtils.defaultIfEmpty(descIntermediario92, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(93), StringUtils.defaultIfEmpty(cfIntermediario93, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(94), StringUtils.defaultIfEmpty(telIntermediario94, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(95), StringUtils.defaultIfEmpty(emailIntermediario95, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(96), StringUtils.defaultIfEmpty(pecIntermediario96, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(97), StringUtils.defaultIfEmpty(ric_email97, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(98), StringUtils.defaultIfEmpty(ric_pec98, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(99), StringUtils.defaultIfEmpty(indirizzoRespIstruttoria99, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(100), StringUtils.defaultIfEmpty(mailRespIstruttoria100, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(101), StringUtils.defaultIfEmpty(numPraticaPadre101, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(102), StringUtils.defaultIfEmpty(annoPratica102, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(106), StringUtils.defaultIfEmpty(dataScadenzaIstanza106, ""));
	/*
	 * REPLACE CORPO
	 */
	if (StringUtils.isNotBlank(corpo)) {
	    corpoReplace = corpoReplace.concat(corpo);
	    corpoReplace = corpoReplace.replace(codiciList.get(1), StringUtils.defaultIfEmpty(richiedenteReplace1, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(2), StringUtils.defaultIfEmpty(indirizzoReplace2, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(3), StringUtils.defaultIfEmpty(cittaReplace3, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(4), StringUtils.defaultIfEmpty(capReplace4, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(5), StringUtils.defaultIfEmpty(provinciaReplace5, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(6), StringUtils.defaultIfEmpty(dataIstanzaReplace6, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(7), StringUtils.defaultIfEmpty(nprotocolloReplace7, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(8), StringUtils.defaultIfEmpty(dataprotocolloReplace8, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(9), StringUtils.defaultIfEmpty(tipointerventoReplace9, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(10), StringUtils.defaultIfEmpty(proceduraReplace10, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(11), StringUtils.defaultIfEmpty(areaReplace11, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(12), StringUtils.defaultIfEmpty(codicelottoReplace12, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(13), StringUtils.defaultIfEmpty(lavoriReplace13, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(14), StringUtils.defaultIfEmpty(foglioReplace14, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(15), StringUtils.defaultIfEmpty(particellaReplace15, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(16), StringUtils.defaultIfEmpty(subReplace16, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(17), StringUtils.defaultIfEmpty(responsabileReplace17, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(18), StringUtils.defaultIfEmpty(responsabileprocReplace18, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(19), StringUtils.defaultIfEmpty(inventarioProcListReplace19, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(20), StringUtils.defaultIfEmpty(numeroistanzaReplace20, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(21), StringUtils.defaultIfEmpty(impiantoReplace21, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(22), StringUtils.defaultIfEmpty(istanzestradarioCivicoReplace22, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(23), StringUtils.defaultIfEmpty(stradarioReplace23, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(24), StringUtils.defaultIfEmpty(passwordReplace24, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(25), StringUtils.defaultIfEmpty(flagviaReplace25, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(26), StringUtils.defaultIfEmpty(varianteprReplace26, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(27), StringUtils.defaultIfEmpty(dataodiernaReplace27, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(28), StringUtils.defaultIfEmpty(tecnicoReplace28, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(29), StringUtils.defaultIfEmpty(soggetticollegatiReplace29, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(30), StringUtils.defaultIfEmpty(settoriReplace30, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(31), StringUtils.defaultIfEmpty(attivitaReplace31, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(32), StringUtils.defaultIfEmpty(movimentoReplace32, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(33), StringUtils.defaultIfEmpty(inventarioprocedimentiReplace33, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(34), StringUtils.defaultIfEmpty(numerodataprotocolloReplace34, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(35), StringUtils.defaultIfEmpty(esitoReplace35, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(36), StringUtils.defaultIfEmpty(parereReplace36, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(37), StringUtils.defaultIfEmpty(datamovReplace37, ""));
	    // [38(codReg)] e [39(codReg)]
	    int indice38Corpo = corpoReplace.indexOf(codiciList.get(38));
	    if (indice38Corpo != -1) {
		indice38Corpo = indice38Corpo + 4;
		int indiceparentesi = corpoReplace.indexOf(")]");
		String codreg = corpoReplace.substring(indice38Corpo, indiceparentesi);
		Integer codReg = Integer.parseInt(codreg);
		List<Autorizzazioni> autList = autorizzazioniService.findByIstanzaRegistro(istanze, codReg);
		if (autList != null && !autList.isEmpty()) {
		    autnumReplace38 = autList.get(0).getAutoriznumero();
		    corpoReplace = corpoReplace.replace(codiciList.get(38) + codreg + ")]", StringUtils.defaultIfEmpty(autnumReplace38, ""));
		} else {
		    corpoReplace = corpoReplace.replace(codiciList.get(38) + codreg + ")]", "");
		}
	    }
	    int indice39Corpo = corpoReplace.indexOf(codiciList.get(39));
	    if (indice39Corpo != -1) {
		indice39Corpo = indice39Corpo + 4;
		int indiceparentesi = corpoReplace.indexOf(")]");
		String codreg = corpoReplace.substring(indice39Corpo, indiceparentesi);
		Integer codReg = Integer.parseInt(codreg);
		if (EntityUtils.getNestedProperty(istanze, "id.codice") != null) {
		    List<Autorizzazioni> autList = autorizzazioniService.findByIstanzaRegistro(istanze, codReg);
		    if (autList != null && !autList.isEmpty()) {
			autdataReplace39 = format.format(autList.get(0).getAutorizdata());
			corpoReplace = corpoReplace.replace(codiciList.get(39) + codreg + ")]", StringUtils.defaultIfEmpty(autdataReplace39, ""));
		    } else {
			corpoReplace = corpoReplace.replace(codiciList.get(39) + codreg + ")]", "");
		    }
		} else {
		    corpoReplace = corpoReplace.replace(codiciList.get(39) + codreg + ")]", "");
		}
	    }
	    mappaOggettoIntervento = trovaSegnapostoAlbero(corpoReplace);
	    // Creazione del tag [-ALBERO_LIVELLO(N)-], indica la descrizione dell 'intervento a partire dal nodo foglia. La descrizione risale fino 
	    // N livelli indietro, dove N è il parametro passato in fase di creazione del tag sul documento/lettera tipo
	    if (!mappaOggettoIntervento.isEmpty()) {
		for (Entry<String, String> sp : mappaOggettoIntervento.entrySet()) {
		    String segnaposto = sp.getKey();
		    String valore = sp.getValue();
		    if (Utilities.isInteger(valore)) {
			try {
			    Integer _num = Integer.parseInt(valore);
			    valore = alberoprocService.findDescrizioneInterventoFromLivello(istanze.getAlberoproc().getId().getCodice(), _num);
			} catch (NumberFormatException e) {
			    log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", segnaposto);
			}
		    }
		    corpoReplace = corpoReplace.replace(segnaposto, valore);
		}
	    }
	    //	    int indiceCorpo83 = corpoReplace.indexOf(codiciList.get(83));
	    //	    if (indiceCorpo83 != -1) {
	    //		indiceCorpo83 = indiceCorpo83 + 16;
	    //		int indiceparentesi = corpoReplace.indexOf(")]");
	    //		String num = corpoReplace.substring(indiceCorpo83, indiceparentesi);
	    //		String descAlbero = "";
	    //		try {
	    //		    Integer _num = Integer.parseInt(num);
	    //		    descAlbero = alberoprocService.findDescrizioneInterventoFromLivello(istanze.getAlberoproc().getId().getCodice(), _num);
	    //		    corpoReplace = corpoReplace.replace(codiciList.get(83) + _num + ")]", StringUtils.defaultIfEmpty(descAlbero, ""));
	    //		} catch (NumberFormatException ne) {
	    //		    log.error("Codice livello albero non corretto, deve essere un numero");
	    //		}
	    //	    }
	    corpoReplace = corpoReplace.replace(codiciList.get(40), StringUtils.defaultIfEmpty(istpeopleReplace40, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(41), StringUtils.defaultIfEmpty(sorteggidettagliodataReplace41, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(42), StringUtils.defaultIfEmpty(sorteggidettagliodescrReplace42, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(43), StringUtils.defaultIfEmpty(datiGeneraliDenominazione43, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(44), StringUtils.defaultIfEmpty(datiSportelloDenominazione44, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(45), StringUtils.defaultIfEmpty(aziendaRichiedenteDenominazione45, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(46), StringUtils.defaultIfEmpty(aziendaRichiedenteCF46, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(47), StringUtils.defaultIfEmpty(aziendaRichiedentePI47, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(48), StringUtils.defaultIfEmpty(codicepraticatel48, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(49), StringUtils.defaultIfEmpty(codAccreditamento49, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(50), StringUtils.defaultIfEmpty(richiedenteCF50, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(51), StringUtils.defaultIfEmpty(richiedentePI51, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(52), StringUtils.defaultIfEmpty(protocolloIdMov52, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(53), StringUtils.defaultIfEmpty(numeroProtocolloMov53, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(54), StringUtils.defaultIfEmpty(annoProtocolloMov54, ""));
	    // replase nel sezione corpo mail del segnaposto [LOC_ESTESA] posizione 55 nella lista
	    corpoReplace = corpoReplace.replace(codiciList.get(55), StringUtils.defaultIfEmpty(localizzazioneEstesa55, ""));
	    // replace nel sezione oggetto dei segna posto 
	    // [ESPONENTE] 		posizione 56 ;
	    // [COLORE] 		posizione 57;
	    // [SCALA]			posizione 58;
	    // [PIANO]			posizione 59; 
	    // [INTERNO]		posizione 60;
	    // [ESPONENTE_INTERNO]	posizione 61;
	    // [FABBRICATO]		posizione 62;
	    // [KM]			posizione 63;
	    // [CAP]			posizione 64;
	    // [FRAZIONE] 		posizione 65;
	    // [CIRCOSCRIZIONE] 	posizione 66;
	    // [QUARTIERE] 		posizione 67; 
	    // [NOTE]			posizione 68;
	    corpoReplace = corpoReplace.replace(codiciList.get(56), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("ESPONENTE"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(57), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("COLORE"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(58), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("SCALA"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(59), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("PIANO"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(60), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("INTERNO"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(61),
		    StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("ESPONENTE_INTERNO"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(62), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("FABBRICATO"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(63), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("KM"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(64), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("CAP"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(65), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("FRAZIONE"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(66), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("CIRCOSCRIZIONE"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(67), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("QUARTIERE"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(68), StringUtils.defaultIfEmpty(infoLocalizzazioneIstanza.get("NOTE"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(69), StringUtils.defaultIfEmpty(comuneIstanza69, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(70), StringUtils.defaultIfEmpty(tipisoggetto70, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(71), StringUtils.defaultIfEmpty(nodopadreIntervento71, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(72), StringUtils.defaultIfEmpty(dataProtocolloMovimento72, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(73), StringUtils.defaultIfEmpty(richiedenteComuneNascita73, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(74), StringUtils.defaultIfEmpty(richiedenteDataNascita74, ""));
	    //replase nella sezione oggetto dei segna posti
	    //[MOV_ENDO_ATTONUM]
	    //[MOV_ENDO_ATTODATA]
	    //[MOV_ENDO_ATTOTIPO]
	    //[MOV_ENDO_ATTOENTE]
	    //[MOV_ENDO_ATTONOTE]
	    corpoReplace = corpoReplace.replace(codiciList.get(75), StringUtils.defaultIfEmpty(movEndoNumeroAtto75, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(76), StringUtils.defaultIfEmpty(movEndoDataAtto76, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(77), StringUtils.defaultIfEmpty(movEndoTipoAtto77, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(78), StringUtils.defaultIfEmpty(movEndoEnteAtto78, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(79), StringUtils.defaultIfEmpty(movEndoNoteAtto79, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(80), StringUtils.defaultIfEmpty(localizzazioneVia80, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(81), StringUtils.defaultIfEmpty(responsabileIstruttoria81, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(82), StringUtils.defaultIfEmpty(utenteLoggato82, ""));
	    // 83
	    corpoReplace = corpoReplace.replace(segnapostoAmministrazione, StringUtils.defaultIfEmpty(descAmministrazione84, ""));
	    corpoReplace = corpoReplace.replace(segnapostoAmministrazionePec, StringUtils.defaultIfEmpty(pecAmministrazione85, ""));
	    corpoReplace = corpoReplace.replace(segnapostoAmministrazioneMail, StringUtils.defaultIfEmpty(emailAmministrazione86, ""));
	    corpoReplace = corpoReplace.replace(segnapostoAmministrazionePI, StringUtils.defaultIfEmpty(piAmministrazione87, ""));
	    corpoReplace = corpoReplace.replace(segnapostoAmministrazioneRef, StringUtils.defaultIfEmpty(referenteAmministrazione88, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(89), StringUtils.defaultIfEmpty(telefonoRespIstruttoria89, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(90), StringUtils.defaultIfEmpty(posizioneArchivio90, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(91), StringUtils.defaultIfEmpty(richiedenteTelefono91, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(92), StringUtils.defaultIfEmpty(descIntermediario92, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(93), StringUtils.defaultIfEmpty(cfIntermediario93, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(94), StringUtils.defaultIfEmpty(telIntermediario94, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(95), StringUtils.defaultIfEmpty(emailIntermediario95, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(96), StringUtils.defaultIfEmpty(pecIntermediario96, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(97), StringUtils.defaultIfEmpty(ric_email97, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(98), StringUtils.defaultIfEmpty(ric_pec98, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(99), StringUtils.defaultIfEmpty(indirizzoRespIstruttoria99, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(100), StringUtils.defaultIfEmpty(mailRespIstruttoria100, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(101), StringUtils.defaultIfEmpty(numPraticaPadre101, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(102), StringUtils.defaultIfEmpty(annoPratica102, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(103), StringUtils.defaultIfEmpty(allegatiMovimento103, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(104), StringUtils.defaultIfEmpty(zipLogicoNumFile104, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(105), StringUtils.defaultIfEmpty(zipLogicoTabellaHash105, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(106), StringUtils.defaultIfEmpty(dataScadenzaIstanza106, ""));
	}
	mailtipoReplace.setCorpo(corpoReplace);
	mailtipoReplace.setOggetto(oggettoReplace);
	return mailtipoReplace;
    }

    @Override
    public Mailtipo replaceOggettoCorpoProtocollo(Mailtipo mailtipo, Istanze istanza, Movimenti movimento) {

	Mailtipo result = new Mailtipo();
	if (mailtipo.getProtocolloOggettoMail() != null) {
	    result.setOggetto(mailtipo.getProtocolloOggettoMail() + "");
	}
	if (mailtipo.getProtocolloCorpoMail() != null) {
	    result.setCorpo(mailtipo.getProtocolloCorpoMail() + "");
	}
	result = replaceOggettoCorpo(result, istanza, movimento);
	if (StringUtils.isNotBlank(result.getOggetto())) {
	    result.setProtocolloOggettoMail(result.getOggetto());
	}
	if (StringUtils.isNotBlank(result.getCorpo())) {
	    result.setProtocolloCorpoMail(result.getCorpo());
	}
	result.setOggetto(null);
	result.setCorpo(null);
	return result;
    }

    private Amministrazioni getAmministrazione(String segnapostoAmministrazione) {

	Amministrazioni amministrazione = null;
	if (StringUtils.isNotBlank(segnapostoAmministrazione)) {
	    Integer codice = getCodiceTag(segnapostoAmministrazione);
	    if (codice != null) {
		amministrazione = amministrazioniService.findById(new PkId(codice));
		if (EntityUtils.getNestedProperty(amministrazione, "id.codice") != null) {
		    return amministrazione;
		}
	    }
	}
	return amministrazione;
    }

    // Ritorna il segna posto con il codice che dobbiamo sostituire
    // ES Sul corpo c'è AMMINISTRAZIONE(129), dinamicamente il metodo recupera questa stringa dal corpo
    // semplicemente passando AMMINISTRAZIONE(
    private String createSegnaPostoConCodice(String segnaposto, String oggettoReplace) {

	String segnapostoConcodice = oggettoReplace;
	int indice = oggettoReplace.indexOf(segnaposto);
	if (indice != -1) {
	    segnapostoConcodice = oggettoReplace.substring(indice);
	    segnapostoConcodice = StringUtils.substringAfter(segnapostoConcodice, "(");
	    segnapostoConcodice = StringUtils.substringBefore(segnapostoConcodice, ")");
	    Integer _codAmm = null;
	    try {
		_codAmm = Integer.parseInt(segnapostoConcodice);
	    } catch (NumberFormatException e) {
		log.error("getCodiece# Il codice stringa {}, non rappresenta un intero ", segnapostoConcodice);
	    }
	    if (_codAmm != null) {
		segnapostoConcodice = segnaposto + _codAmm.toString() + ")]";
	    }
	} else {
	    segnapostoConcodice = "";
	}
	return segnapostoConcodice;
    }

    private Integer getCodiceTag(String segnapostoConcodice) {

	segnapostoConcodice = StringUtils.substringAfter(segnapostoConcodice, "(");
	segnapostoConcodice = StringUtils.substringBefore(segnapostoConcodice, ")");
	//indice84 = indice84 + 4;
	//int indiceparentesi = oggettoReplace.indexOf(")]");
	//String codreg = oggettoReplace.substring(indice84, indiceparentesi);
	Integer _codAmm = null;
	try {
	    _codAmm = Integer.parseInt(segnapostoConcodice);
	} catch (NumberFormatException e) {
	    log.error("getCodiece# Il codice stringa {}, non rappresenta un intero ", segnapostoConcodice);
	}
	return _codAmm;
    }

    /**
     * @see MailtipoDAO#findAllBySoftwareAndTT(ContestiMailTipoEnum contestiMailTipoEnum)
     */
    @Override
    public List<Mailtipo> findAllBySoftwareAndTT(ContestiMailTipoEnum contestiMailTipoEnum) {

	return mailtipoDAO.findAllBySoftwareAndTT(contestiMailTipoEnum);
    }

    @Override
    public String getOggettoProtocollazioneDefault() {

	return "Protocollo dell'istanza numero ";
    }

    @Override
    public String getOggettoFascicolazioneDefault() {

	return "Fascicolo dell'istanza numero ";
    }

    /**
     * Popola il segna posto con le informazioni recuperate da istanze stradario secondo il pattern:
     * 
     * a- Prefisso Via, civico/esponente colore : se l'informazione km non presente b- Prefisso Via, km/esponete colre :
     * se l'informazione km presente
     * 
     * "Colore" ed "Esponente" possono essere non valorizzati
     * 
     * @param istanzestradario
     * @return
     */
    private String createSegnapostoLOC_ESTESA(Istanzestradario istanzestradario) {

	String _LOC_ESTESA = "";
	// Controllo che sia presente lo stradario
	if (EntityUtils.getNestedProperty(istanzestradario.getStradario(), "id.codice") != null) {
	    Stradario str = istanzestradario.getStradario();
	    // Concateno prefisso e descrizione dello strdario se presenti
	    if (StringUtils.isNotBlank(str.getPrefisso())) {
		_LOC_ESTESA = _LOC_ESTESA.concat(str.getPrefisso()).concat(" ");
	    }
	    if (StringUtils.isNotBlank(str.getDescrizione())) {
		_LOC_ESTESA = _LOC_ESTESA.concat(str.getDescrizione()).concat(" ");
	    }
	    // se esiste il civico e concateno il civico
	    if (StringUtils.isNotBlank(istanzestradario.getCivico())) {
		_LOC_ESTESA = _LOC_ESTESA.concat(istanzestradario.getCivico());
	    }
	    // Controllo se istanze stradario ha popolato il campo esponente , se si lo concateno
	    if (StringUtils.isNotBlank(istanzestradario.getEsponente())) {
		_LOC_ESTESA = _LOC_ESTESA.concat("/").concat(istanzestradario.getEsponente());
	    }
	    _LOC_ESTESA = _LOC_ESTESA.concat(" ");
	    // Controllo se istanze stradario ha popolato il campo colore , se si lo concateno
	    if (EntityUtils.getNestedProperty(istanzestradario.getStradariocolore(), "id.codicecolore") != null) {
		//		String colore = "";
		//		// Controllo se nella stringa già esiste il carattere "/" se no allora oltre al colore concateno il carattere mancante
		//		colore = StringUtils.contains(_LOC_ESTESA, "/") ? " " + istanzestradario.getStradariocolore().getColore()
		//			: "/ " + istanzestradario.getStradariocolore().getColore();
		_LOC_ESTESA = _LOC_ESTESA.concat(istanzestradario.getStradariocolore().getColore());
	    }
	    // Se instanze strdario ha popolato il campo Km allora lo uso
	    if (StringUtils.isNotBlank(istanzestradario.getKm())) {
		_LOC_ESTESA = _LOC_ESTESA.concat(", ");
		_LOC_ESTESA = _LOC_ESTESA.concat("Km ").concat(istanzestradario.getKm());
	    }
	}
	return _LOC_ESTESA;
    }

    /**
     * Popola i vari segnaposti con le informazione recuperate da istanze stradario
     * 
     * @param istanzestradario
     * @param esponente56
     * @param colore57
     * @param scala58
     * @param piano59
     * @param interno60
     * @param esponeneteInterno61
     * @param fabbricato62
     * @param km63
     * @param cap64
     * @param frazione65
     * @param circoscrizione66
     * @param quartiere67
     * @param note68
     */
    private Map<String, String> populateInformazioneIstanzestradario(Istanzestradario istanzestradario) {

	Map<String, String> risultato = new HashMap<String, String>();
	String esponente56 = StringUtils.isNotBlank(istanzestradario.getEsponente()) ? istanzestradario.getEsponente() : "";
	risultato.put("ESPONENTE", esponente56);
	String colore57 = (EntityUtils.getNestedProperty(istanzestradario.getStradariocolore(), "id.codicecolore") != null
		&& StringUtils.isNotBlank(istanzestradario.getStradariocolore().getColore())) ? istanzestradario.getStradariocolore().getColore()
			: "";
	risultato.put("COLORE", colore57);
	String scala58 = StringUtils.isNotBlank(istanzestradario.getScala()) ? istanzestradario.getScala() : "";
	risultato.put("SCALA", scala58);
	String piano59 = StringUtils.isNotBlank(istanzestradario.getPiano()) ? istanzestradario.getPiano() : "";
	risultato.put("PIANO", piano59);
	String interno60 = StringUtils.isNotBlank(istanzestradario.getInterno()) ? istanzestradario.getInterno() : "";
	risultato.put("INTERNO", interno60);
	String esponeneteInterno61 = StringUtils.isNotBlank(istanzestradario.getEsponenteinterno()) ? istanzestradario.getEsponenteinterno() : "";
	risultato.put("ESPONENTE_INTERNO", esponeneteInterno61);
	String fabbricato62 = StringUtils.isNotBlank(istanzestradario.getFabbricato()) ? istanzestradario.getFabbricato() : "";
	risultato.put("FABBRICATO", fabbricato62);
	String km63 = StringUtils.isNotBlank(istanzestradario.getKm()) ? istanzestradario.getKm() : "";
	risultato.put("KM", km63);
	String cap64 = StringUtils.isNotBlank(istanzestradario.getCap()) ? istanzestradario.getCap() : "";
	risultato.put("CAP", cap64);
	String frazione65 = StringUtils.isNotBlank(istanzestradario.getFrazione()) ? istanzestradario.getFrazione() : "";
	risultato.put("FRAZIONE", frazione65);
	String circoscrizione66 = StringUtils.isNotBlank(istanzestradario.getCircoscrizione()) ? istanzestradario.getCircoscrizione() : "";
	risultato.put("CIRCOSCRIZIONE", circoscrizione66);
	String quartiere67 = StringUtils.isNotBlank(istanzestradario.getQuartiere()) ? istanzestradario.getQuartiere() : "";
	risultato.put("QUARTIERE", quartiere67);
	String note68 = StringUtils.isNotBlank(istanzestradario.getNote()) ? istanzestradario.getNote() : "";
	risultato.put("NOTE", note68);
	return risultato;
    }

    @Override
    public Mailtipo eseguiSostituzioniFrontend(int codicemailtipo, DettaglioPraticaType dp) {

	Mailtipo mail = this.findById(new PkId(codicemailtipo));
	if (mail == null) {
	    log.error("eseguiSostituzioniFrontend# Lettera tipo non trovata {}", new PkId(codicemailtipo));
	    throw new RuntimeException("Lettera tipo non trovata " + new PkId(codicemailtipo));
	}
	if (!"F".equalsIgnoreCase(mail.getAmbito())) {
	    String ambito = getMessageFromBundle("mailtipo.label.ambito.item_frontend", new Object[] {});
	    log.error("eseguiSostituzioniFrontend# La mail recuperata con codice [{}] non appartiene all'ambito [codice: F, descrizione: {}] ",
		    mail.getId(), ambito);
	    throw new RuntimeException(
		    "La mail recuperata con codice [" + mail.getId() + "] non appartiene all'ambito [codice: F, descrizione:" + ambito + "] ");
	}
	String oggetto = StringUtils.defaultString(mail.getOggetto());
	String corpo = StringUtils.defaultString(mail.getCorpo());
	if (dp != null) {
	    String dataOdierna = Utilities.getToday(false);
	    oggetto = oggetto.replace("[27]", StringUtils.defaultString(dataOdierna));
	    corpo = corpo.replace("[27]", StringUtils.defaultString(dataOdierna));
	    String richiedente = "";
	    String inQualitaDi = "";
	    String cfRichiedente = "";
	    String residenzaRichiedente = "";
	    String cittaRichiedente = "";
	    String capRichiedente = "";
	    String provinciaRichiedente = "";
	    String comuneNascitaRichiedente = "";
	    String dataNascitaRichiedente = "";
	    if (dp.getRichiedente() != null) {
		if (dp.getRichiedente().getRuolo() != null) {
		    if (StringUtils.isNotBlank(dp.getRichiedente().getRuolo().getRuolo())) {
			inQualitaDi = dp.getRichiedente().getRuolo().getRuolo();
		    }
		}
		if (dp.getRichiedente().getAnagrafica() != null) {
		    PersonaFisicaType r = dp.getRichiedente().getAnagrafica();
		    if (StringUtils.isNotBlank(r.getCognome())) {
			richiedente = r.getCognome() + " ";
		    }
		    if (StringUtils.isNotBlank(r.getNome())) {
			richiedente += r.getNome();
		    }
		    if (StringUtils.isNotBlank(r.getCodiceFiscale())) {
			cfRichiedente = r.getCodiceFiscale();
		    }
		    if (r.getResidenza() != null) {
			LocalizzazioneType residenza = r.getResidenza();
			residenzaRichiedente = residenza.getIndirizzo();
			if (StringUtils.isNotBlank(residenza.getCivico())) {
			    residenzaRichiedente += ", civico " + residenza.getCivico();
			}
			if (StringUtils.isNotBlank(residenza.getCap())) {
			    capRichiedente = residenza.getCap();
			}
			if (StringUtils.isNotBlank(residenza.getLocalita())) {
			    cittaRichiedente = residenza.getLocalita();
			}
			if (StringUtils.isNotBlank(residenza.getProvincia())) {
			    provinciaRichiedente = residenza.getProvincia();
			}
			if (r.getDataNascita() != null) {
			    dataNascitaRichiedente = Utilities.formatDate(Utilities.getDate(r.getDataNascita()), false);
			}
			if (r.getComuneNascita() != null) {
			    ComuneType c = r.getComuneNascita();
			    if (StringUtils.isNotBlank(c.getComune())) {
				comuneNascitaRichiedente = c.getComune();
			    } else {
				Comuni comuneDB = null;
				if (StringUtils.isNotBlank(c.getCodiceCatastale())) {
				    comuneDB = comuniService.findById(c.getCodiceCatastale());
				}
				if (StringUtils.isNotBlank(c.getCodiceIstat())) {
				    Comuni filter = new Comuni();
				    filter.setCodiceistat(c.getCodiceIstat());
				    comuneDB = comuniService.findByComune(filter);
				}
				if (comuneDB != null) {
				    comuneNascitaRichiedente = comuneDB.getComune();
				}
			    }
			}
		    }
		}
	    }
	    oggetto = oggetto.replace("[1]", StringUtils.defaultString(richiedente));
	    corpo = corpo.replace("[1]", StringUtils.defaultString(richiedente));
	    oggetto = oggetto.replace("[INQUALITADI]", StringUtils.defaultString(inQualitaDi));
	    corpo = corpo.replace("[INQUALITADI]", StringUtils.defaultString(inQualitaDi));
	    oggetto = oggetto.replace("[RIC_CF]", StringUtils.defaultString(cfRichiedente));
	    corpo = corpo.replace("[RIC_CF]", StringUtils.defaultString(cfRichiedente));
	    oggetto = oggetto.replace("[2]", StringUtils.defaultString(residenzaRichiedente));
	    corpo = corpo.replace("[2]", StringUtils.defaultString(residenzaRichiedente));
	    oggetto = oggetto.replace("[3]", StringUtils.defaultString(cittaRichiedente));
	    corpo = corpo.replace("[3]", StringUtils.defaultString(cittaRichiedente));
	    oggetto = oggetto.replace("[4]", StringUtils.defaultString(capRichiedente));
	    corpo = corpo.replace("[4]", StringUtils.defaultString(capRichiedente));
	    oggetto = oggetto.replace("[5]", StringUtils.defaultString(provinciaRichiedente));
	    corpo = corpo.replace("[5]", StringUtils.defaultString(provinciaRichiedente));
	    oggetto = oggetto.replace("[RIC_CN]", StringUtils.defaultString(comuneNascitaRichiedente));
	    corpo = corpo.replace("[RIC_CN]", StringUtils.defaultString(comuneNascitaRichiedente));
	    oggetto = oggetto.replace("[RIC_DN]", StringUtils.defaultString(dataNascitaRichiedente));
	    corpo = corpo.replace("[RIC_DN]", StringUtils.defaultString(dataNascitaRichiedente));
	    String istanzaData = "";
	    if (dp.getDataPratica() != null) {
		istanzaData = Utilities.formatDate(Utilities.getDate(dp.getDataPratica()), false);
	    }
	    oggetto = oggetto.replace("[6]", StringUtils.defaultString(istanzaData));
	    corpo = corpo.replace("[6]", StringUtils.defaultString(istanzaData));
	    String oggettoIstanza = "";
	    if (StringUtils.isNotBlank(dp.getOggetto())) {
		oggettoIstanza = dp.getOggetto();
	    }
	    oggetto = oggetto.replace("[13]", StringUtils.defaultString(oggettoIstanza));
	    corpo = corpo.replace("[13]", StringUtils.defaultString(oggettoIstanza));
	    String azienda = "";
	    String aziendaCF = "";
	    if (dp.getAziendaRichiedente() != null) {
		PersonaGiuridicaType pg = dp.getAziendaRichiedente();
		if (StringUtils.isNotBlank(pg.getRagioneSociale())) {
		    azienda = pg.getRagioneSociale();
		}
		if (StringUtils.isNotBlank(pg.getPartitaIva())) {
		    aziendaCF = pg.getPartitaIva();
		}
		if (StringUtils.isNotBlank(pg.getCodiceFiscale())) {
		    aziendaCF = pg.getCodiceFiscale();
		}
	    }
	    oggetto = oggetto.replace("[AZRIC_CF]", StringUtils.defaultString(aziendaCF));
	    corpo = corpo.replace("[AZRIC_CF]", StringUtils.defaultString(aziendaCF));
	    oggetto = oggetto.replace("[AZRIC_DEN]", StringUtils.defaultString(azienda));
	    corpo = corpo.replace("[AZRIC_DEN]", StringUtils.defaultString(azienda));
	}
	Mailtipo mailtipo = new Mailtipo();
	mailtipo.setOggetto(oggetto);
	mailtipo.setCorpo(corpo);
	return mailtipo;
    }

    @Override
    public Mailtipo eseguiSostituzioniAnagrafe(int codicemailtipo, Anagrafe anagrafe) {

	Mailtipo mail = this.findById(new PkId(codicemailtipo));
	if (mail == null) {
	    log.error("eseguiSostituzioniFrontend# Lettera tipo non trovata {}", new PkId(codicemailtipo));
	    throw new RuntimeException("Lettera tipo non trovata " + new PkId(codicemailtipo));
	}
	if (!"A".equalsIgnoreCase(mail.getAmbito())) {
	    String ambito = getMessageFromBundle("mailtipo.label.ambito.item_anagrafe", new Object[] {});
	    log.error("eseguiSostituzioniFrontend# La mail recuperata con codice [{}] non appartiene all'ambito [codice: A, descrizione: {}] ",
		    mail.getId(), ambito);
	    throw new RuntimeException(
		    "La mail recuperata con codice [" + mail.getId() + "] non appartiene all'ambito [codice: A, descrizione:" + ambito + "] ");
	}
	String oggetto = StringUtils.defaultString(mail.getOggetto());
	String corpo = StringUtils.defaultString(mail.getCorpo());
	if (anagrafe != null) {
	    String dataOdierna = Utilities.getToday(false);
	    oggetto = oggetto.replace("[27]", dataOdierna);
	    corpo = corpo.replace("[27]", dataOdierna);
	    String richiedente = "";
	    String cfRichiedente = "";
	    String residenzaRichiedente = "";
	    String cittaRichiedente = "";
	    String capRichiedente = "";
	    String provinciaRichiedente = "";
	    String comuneNascitaRichiedente = "";
	    String dataNascitaRichiedente = "";
	    String passwordClear = "";
	    if (StringUtils.isNotBlank(anagrafe.getNominativo())) {
		richiedente = anagrafe.getNominativo() + " ";
	    }
	    if (StringUtils.isNotBlank(anagrafe.getNome())) {
		richiedente += anagrafe.getNome();
	    }
	    if (StringUtils.isNotBlank(anagrafe.getCodicefiscale())) {
		cfRichiedente = anagrafe.getCodicefiscale();
	    }
	    residenzaRichiedente = anagrafe.getIndirizzo();
	    if (StringUtils.isNotBlank(anagrafe.getCap())) {
		capRichiedente = anagrafe.getCap();
	    }
	    if (StringUtils.isNotBlank(anagrafe.getProvincia())) {
		provinciaRichiedente = anagrafe.getProvincia();
	    }
	    if (anagrafe.getDatanascita() != null) {
		dataNascitaRichiedente = Utilities.formatDate(anagrafe.getDatanascita(), false);
	    }
	    if (anagrafe.getComuneNascita() != null) {
		Comuni c = anagrafe.getComuneNascita();
		comuneNascitaRichiedente = c.getComune();
	    }
	    if (anagrafe.getPasswordClear() != null) {
		passwordClear = anagrafe.getPasswordClear();
	    }
	    String email = "";
	    if (anagrafe.getEmail() != null) {
		email = anagrafe.getEmail();
	    }
	    String pec = "";
	    if (anagrafe.getPec() != null) {
		pec = anagrafe.getPec();
	    }
	    oggetto = oggetto.replace("[1]", StringUtils.defaultString(richiedente));
	    corpo = corpo.replace("[1]", StringUtils.defaultString(richiedente));
	    oggetto = oggetto.replace("[RIC_CF]", StringUtils.defaultString(cfRichiedente));
	    corpo = corpo.replace("[RIC_CF]", StringUtils.defaultString(cfRichiedente));
	    oggetto = oggetto.replace("[2]", StringUtils.defaultString(residenzaRichiedente));
	    corpo = corpo.replace("[2]", StringUtils.defaultString(residenzaRichiedente));
	    oggetto = oggetto.replace("[3]", StringUtils.defaultString(cittaRichiedente));
	    corpo = corpo.replace("[3]", StringUtils.defaultString(cittaRichiedente));
	    oggetto = oggetto.replace("[4]", StringUtils.defaultString(capRichiedente));
	    corpo = corpo.replace("[4]", StringUtils.defaultString(capRichiedente));
	    oggetto = oggetto.replace("[5]", StringUtils.defaultString(provinciaRichiedente));
	    corpo = corpo.replace("[5]", StringUtils.defaultString(provinciaRichiedente));
	    oggetto = oggetto.replace("[RIC_CN]", StringUtils.defaultString(comuneNascitaRichiedente));
	    corpo = corpo.replace("[RIC_CN]", StringUtils.defaultString(comuneNascitaRichiedente));
	    oggetto = oggetto.replace("[RIC_DN]", StringUtils.defaultString(dataNascitaRichiedente));
	    corpo = corpo.replace("[RIC_DN]", StringUtils.defaultString(dataNascitaRichiedente));
	    oggetto = oggetto.replace("[RIC_PWD]", StringUtils.defaultString(passwordClear));
	    corpo = corpo.replace("[RIC_PWD]", StringUtils.defaultString(passwordClear));
	    oggetto = oggetto.replace("[RIC_MAIL]", StringUtils.defaultString(email));
	    corpo = corpo.replace("[RIC_MAIL]", StringUtils.defaultString(email));
	    oggetto = oggetto.replace("[RIC_PEC]", pec);
	    corpo = corpo.replace("[RIC_PEC]", pec);
	    if (oggetto.indexOf("CODICE_VERIFICA_MAIL") >= 0 || corpo.indexOf("CODICE_VERIFICA_MAIL") >= 0) {
		String codiceVerifica = anagrafeVerificheMailDAO.recuperaUltimoCodiceVerificaPerAnagrafe(anagrafe.getId().getCodice());
		oggetto = oggetto.replace("[CODICE_VERIFICA_MAIL]", codiceVerifica);
		corpo = corpo.replace("[CODICE_VERIFICA_MAIL]", codiceVerifica);
	    }
	}
	Mailtipo mailtipo = new Mailtipo();
	mailtipo.setOggetto(oggetto);
	mailtipo.setCorpo(corpo);
	return mailtipo;
    }

    @Override
    public MailMessageType populateMailMessageForExport(String emailDestinatario, String pathFile, Mailtipo mailtipo) {

	MailMessageType mailMessageType = new MailMessageType();
	mailMessageType.setDestinatari(emailDestinatario);
	mailMessageType.setOggetto(StringUtils.defaultIfEmpty(mailtipo.getOggetto(), ""));
	mailMessageType.setCorpoMail(StringUtils.defaultIfEmpty(mailtipo.getCorpo(), ""));
	mailMessageType.setInviaComeHtml(true);
	AttachmentsType attachmentsType = new AttachmentsType();
	try {
	    File file = new File(pathFile);
	    byte[] responseByte = Utilities.getBytesFromFile(file);
	    AttachmentType att = new AttachmentType();
	    att.setFileName(file.getName());
	    att.setMimeType("application/zip");
	    att.setBinaryData(Utilities.bytesToDataHandler(responseByte));
	    attachmentsType.getAttachment().add(att);
	    mailMessageType.setAttachments(attachmentsType);
	} catch (IOException e) {
	    log.error("Errore mentre si popolava l'oggetto MailMessageType:{}", e.getMessage());
	}
	return mailMessageType;
    }

    /**
     * Metodo che si occupa di sostituire ai TAG della forma <b>[DYN(nomecampo)]</b> oppure
     * <b>[DYN(nomecampo,"separatore")]</b> i dati provenienti dai campi dinamici
     * 
     * @param testoDaSostituire
     * @param codiceIstanza
     * @return
     */
    public String popolaDyn2Campi(String testoDaSostituire, Integer codiceIstanza) {

	//gli passo il corpo o l'oggetto, e  ogni segnaposto che trovo lo metto dentro una mappa che poi verrà ciclata per andare a sostituire i TAG 
	//presenti al suo interno e alla fine restituisco il corpo o l'oggetto con i TAG sostituiti
	if (StringUtils.isEmpty(testoDaSostituire)) {
	    return testoDaSostituire;
	}
	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("Impossibile effettuare la sostituzione dei Tag dei campi dinamici senza passare il codice istanza");
	}
	String paramReg = "(\\[DYN\\(([^(),]+)\\)\\])|(\\[DYN\\(([^(),]+)([,\\s])+([^())]+)\\)\\])";
	String nomeCampo = "";
	StringBuffer sb = new StringBuffer();
	Pattern pattern = Pattern.compile(paramReg, Pattern.DOTALL);
	Matcher matcher = pattern.matcher(testoDaSostituire);
	while (matcher.find()) {
	    String valoreDaSostituire = "";
	    if (StringUtils.isNotBlank(matcher.group(2))) {
		nomeCampo = matcher.group(2);
		Istanzedyn2dati valoreDatoDyn = istanzedyn2datiService.findByIstanzaAndNomeCampoAndMolteplicita(codiceIstanza, nomeCampo, 0);
		valoreDaSostituire = valoreDatoDyn == null ? "" : valoreDatoDyn.getValoredecodificato();
	    } else {
		nomeCampo = matcher.group(4);
		String separatore = matcher.group(6);
		String _separatore = separatore.substring(1, separatore.length() - 1);
		List<Istanzedyn2dati> listaDatyDyn = istanzedyn2datiService.findByIstanzaAndNomeCampo(codiceIstanza, nomeCampo);
		StringBuffer strb = new StringBuffer();
		for (int i = 0; i < listaDatyDyn.size(); i++) {
		    strb.append(listaDatyDyn.get(i).getValoredecodificato());
		    if (listaDatyDyn.size() - 1 > i) {
			strb.append(_separatore);
		    }
		}
		valoreDaSostituire = strb.toString();
	    }
	    matcher.appendReplacement(sb, valoreDaSostituire == null ? "" : StringUtils.defaultString(valoreDaSostituire));
	}
	matcher.appendTail(sb);
	return sb.toString();
    }
}
