package it.gruppoinit.pal.gp.backoffice.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jmesa.web.ExportTableHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.backoffice.web.helper.ReportRecordElaboratiBean;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.AutorizzazioneSpuntistaHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiConcessioniHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.AssenzeFilter;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Concessioniuso;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistat;
import it.gruppoinit.pal.gp.core.domain.MercatiResponsabili;
import it.gruppoinit.pal.gp.core.domain.MercatiSpunte;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeTPrenot;
import it.gruppoinit.pal.gp.core.domain.Parametriesportazione;
import it.gruppoinit.pal.gp.core.domain.Pentahocfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.VwAssenzeconcessionari;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessioni;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.EsportazioniPentahoCommand;
import it.gruppoinit.pal.gp.core.domain.web.GiornataMercatoCommand;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioInfoRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.StatoPagamentoNodoHelper;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.PresenzaDaRegistrareBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoConcessionarioSegnatoPresente;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.IPagamentiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.utils.PresenzeNonPagateBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ConcessioniusoService;
import it.gruppoinit.pal.gp.core.service.EsportazioniService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.MercatiAppService;
import it.gruppoinit.pal.gp.core.service.MercatiDattivitaistatService;
import it.gruppoinit.pal.gp.core.service.MercatiResponsabiliService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiSpunteService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeTPrenotService;
import it.gruppoinit.pal.gp.core.service.PentahoService;
import it.gruppoinit.pal.gp.core.service.PentahocfgService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.RuoliUtentiEnum;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;
import it.gruppoinit.pal.gp.core.service.VwAssenzeconcessionariService;
import it.gruppoinit.pal.gp.core.service.VwPosteggiconcessioniService;
import it.gruppoinit.pal.gp.core.service.exception.MercatiAppException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.PresenzeSpuntistiHelper;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;
import it.gruppoinit.pal.gp.core.service.helper.TipoWSAnagrafeAttivoEnum;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

@Controller
@SessionAttributes(value = { "giornoPrecedente", "assenzeFilter", "esportazioniPentahoCommand" })
public class GestionepresenzeController extends BaseController<VwPosteggiconcessioni> {

    private static final Logger log = LoggerFactory.getLogger(GestionepresenzeController.class);
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private MercatipresenzeDService mercatipresenzeDService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private VwAssenzeconcessionariService vwAssenzeconcessionariService;
    @Autowired
    private VwPosteggiconcessioniService vwPosteggiconcessioniService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private MercatiResponsabiliService mercatiResponsabiliService;
    @Autowired
    private TipimodalitapagamentoService tipimodalitapagamentoService;
    @Autowired
    private MercatipresenzeTPrenotService mercatipresenzeTPrenotService;
    @Autowired
    private MercatiSpunteService mercatiSpunteService;
    @Autowired
    private IVerticalizzazioneComportamentiMercatiService vertComportamentiMercatiService;
    @Autowired
    private MercatiDattivitaistatService mercatiDattivitaistatService;
    @Autowired
    private MercatiAppService mercatiAppService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private EsportazioniService esportazioniService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private PentahocfgService pentahocfgService;
    @Autowired
    private PentahoService pentahoService;
    @Autowired
    private MailServiceWSClient mailServiceWSClient;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private NodoPagamentiService nodoPagamentiService;
    @Autowired
    private ConcessioniusoService concessioniusoService;
    @Autowired
    private IEventPublisher eventPublisher;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    private IPagamentiService pagamentiService;
    @Autowired
    private AutorizzazioniConcessioniService autorizzazioniConcessioniService;
    private IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService;

    @Autowired
    public void setPagamentiService(IPagamentiService pagamentiService) {

	this.pagamentiService = pagamentiService;
    }

    @Autowired
    public void setComportamentiMercatiService(IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService) {

	this.comportamentiMercatiService = comportamentiMercatiService;
    }

    /**
     * 
     * @param giornoMercato
     * @param codiceMercato
     * @param usoMercato
     * @param useHistoryBack
     *            : parametro opzionale che puoò assumere i valori true,false (se null allora si comporta come false).
     *            Utilizzato per impostare il bottone chiudi con il link diretto ad una pagina (FALSE) oppure con il
     *            link generico dell' historyback (TRUE)
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping
    public String list(@RequestParam("giornoMercato") Date giornoMercato, @RequestParam("codiceMercato") Integer codiceMercato,
	    @RequestParam("usoMercato") Integer usoMercato, @RequestParam(required = false, value = "useHistoryBack") Boolean useHistoryBack,
	    Model model, HttpServletRequest request, HttpServletResponse response) {

	// recupero il mercato
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	// recupero l'uso del mercato
	MercatiUso uso = mercatiUsoService.findById(new PkId(usoMercato));
	// recupero il giorno di mercato
	Calendar data = Calendar.getInstance();
	data.setTime(giornoMercato);
	MercatipresenzeT mercatipresenzeT = mercatipresenzeTService.findByDataregistrazioneAndMercatoAndMercatoUso(data, mercati, uso);
	model.addAttribute("mercato", mercati);
	model.addAttribute("uso", uso);
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	model.addAttribute("data", sdf.format(giornoMercato));
	// controllo se è un giorno di mercato
	if (mercatipresenzeT == null) {
	    return "gestionepresenze/list";
	}
	// inserisco i posteggi e i concessionari se è la prima volta che visualizzo la giornata di mercato
	mercatipresenzeTService.inserisciTuttiConcessionari(mercatipresenzeT);
	// recupero la lista posteggi ordinata per codiceposteggio
	List<MercatipresenzeDDTO> listaPosteggi = mercatipresenzeDService.findListaPosteggi(mercatipresenzeT);
	// inserisco le aut dai dyn2dati e le associo a mercatiPresenzed se va a buon fine l'inserimento
	// controlli per il checkbox utilizzato per la visualizzazione dei soli posteggi liberi
	if (request.getParameter("visPosteggiLiberi") != null) {
	    request.getSession().setAttribute("visPosteggiLiberi", request.getParameter("visPosteggiLiberi"));
	} else {
	    if (request.getSession().getAttribute("visPosteggiLiberi") == null) {
		request.getSession().setAttribute("visPosteggiLiberi", "false");
	    }
	}
	Set<AutorizzazioniConcessioni> autorizzazioniConcessioni = new HashSet<AutorizzazioniConcessioni>();
	for (MercatipresenzeDDTO mercatipresenzeDDTO : listaPosteggi) {
	    AutorizzazioniDTO autorizzazioni = mercatipresenzeDDTO.getAutorizzazioni();
	    List<AutorizzazioniConcessioni> autConc = autorizzazioniConcessioniService
		    .findByAutorizzazioneAttuale(autorizzazioni.getId().getCodice());
	    for (AutorizzazioniConcessioni autC : autConc) {
		if (autC.getAutorizzazioniByFkAutconcAutcoll() != null) {
		    autorizzazioniConcessioni.add(autC);
		}
	    }
	}
	model.addAttribute("giornoMercato", mercatipresenzeT);
	model.addAttribute("listaPosteggi", listaPosteggi);
	model.addAttribute("autorizzazioniConcessioni", autorizzazioniConcessioni);
	List<MercatiSpunte> mercatiSpuntes = mercatiSpunteService.findByMercato(codiceMercato);
	model.addAttribute("spuntaAttivaSelezionata", Boolean.TRUE);
	if (!mercatiSpuntes.isEmpty()) {
	    if (mercatipresenzeT.getMercatiSpunte() != null && mercatipresenzeT.getMercatiSpunte().getId() != null
		    && mercatipresenzeT.getMercatiSpunte().getId().getCodice() != null) {
		model.addAttribute("spuntaAttiva", mercatipresenzeT.getMercatiSpunte().getId().getCodice());
	    } else {
		mercatipresenzeT.setMercatiSpunte(mercatiSpuntes.get(0));
		mercatipresenzeTService.update(mercatipresenzeT);
		model.addAttribute("spuntaAttiva", mercatipresenzeT.getMercatiSpunte().getId().getCodice());
	    }
	}
	model.addAttribute("mercatiSpuntes", mercatiSpuntes);
	model.addAttribute((String) request.getSession().getAttribute("visPosteggiLiberi"));
	// flagMercatoContabilita=true : gestisco le registrazioni contabili
	// flagMercatoContabilita=false : non gestisco le registrazioni contabili
	model.addAttribute("flagMercatoContabilita", mercati.getFlagContabilita().booleanValue());
	String appSpunta = "";
	String urlAppSpuntaDigitale = this.vertComportamentiMercatiService.urlAppSpuntaDigitale();
	if (StringUtils.isNotBlank(urlAppSpuntaDigitale)) {
	    appSpunta = request.getContextPath() + "/mercati/appbootstrap.htm?idGiornata=" + mercatipresenzeT.getId().getCodice();
	}
	model.addAttribute("appSpuntaURL", appSpunta);
	TipoWSAnagrafeAttivoEnum anaws = anagrafeService.findServizioWSAnagrafeAttivo();
	model.addAttribute("anagrafeParixAttivo", Boolean.valueOf(!anaws.equals(TipoWSAnagrafeAttivoEnum.NESSUNO)));
	if (useHistoryBack != null) {
	    model.addAttribute("useHistoryBack", useHistoryBack);
	} else {
	    model.addAttribute("useHistoryBack", false);
	}
	boolean nascondiBottoneConcPres = this.vertComportamentiMercatiService.nascondiBottoneConcPres()
		|| Boolean.TRUE.equals(mercatipresenzeT.getFlagRegfatte());
	model.addAttribute("nascondiBottoneConcPres", nascondiBottoneConcPres);
	boolean isAttivaGiornateNulle = this.comportamentiMercatiService.isAttivaGiornateNulle();
	model.addAttribute("isAttivaGiornateNulle", isAttivaGiornateNulle);
	setPageAttributes(model, mercatipresenzeT);
	return "gestionepresenze/list";
    }

    @RequestMapping
    public String graficoPosteggi(@RequestParam("codiceMercato") Integer codiceMercato, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	// §§§BEGIN§§§
	Mercati mercato = mercatiService.findById(new PkId(codiceMercato));
	Set<MercatiUso> usi = mercato.getMercatiUsos();
	List<VwPosteggiconcessioni> list = null;
	MercatiUso uso = null;
	if (request.getParameter("codiceUso") != null && !request.getParameter("codiceUso").equals("")) {
	    Integer codiceUso = new Integer(request.getParameter("codiceUso"));
	    list = vwPosteggiconcessioniService.findPosteggiMercatoUso(codiceMercato, codiceUso, null, null);
	    uso = mercatiUsoService.findById(new PkId(codiceUso));
	}
	if (usi.size() == 1) {
	    for (MercatiUso mercatiUso : usi) {
		list = vwPosteggiconcessioniService.findPosteggiMercatoUso(codiceMercato, mercatiUso.getId().getCodice(), null, null);
		uso = mercatiUso;
	    }
	}
	model.addAttribute("listaPosteggiConcessionari", list);
	model.addAttribute("uso", uso);
	model.addAttribute("mercato", mercato);
	return "gestionepresenze/graficoPosteggi";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String ajaxGraficoPosteggi(@RequestParam("codiceMercato") Integer codiceMercato, @RequestParam("codiceUso") Integer codiceUso, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	Mercati mercato = mercatiService.findById(new PkId(codiceMercato));
	MercatiUso uso = null;
	uso = mercatiUsoService.findById(new PkId(codiceUso));
	List<VwPosteggiconcessioni> list = vwPosteggiconcessioniService.findPosteggiMercatoUso(codiceMercato, uso.getId().getCodice(), null, null);
	model.addAttribute("listaPosteggiConcessionari", list);
	model.addAttribute("uso", uso);
	model.addAttribute("mercato", mercato);
	return "gestionepresenze/ajaxGraficoPosteggi";
    }

    @RequestMapping
    public void ajaxModificaUsoGiornata(@RequestParam("giornoMercatoIdCodice") Integer giornoMercatoIdCodice,
	    @RequestParam("concessioneusoid") Integer concessioneusoid, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	MercatipresenzeT gm = mercatipresenzeTService.findById(new PkId(giornoMercatoIdCodice));
	String messaggio = "Operazione avvenuta correttamente";
	boolean giornataMercatoChiusa = mercatipresenzeTService.isGiornataMercatoChiusa(gm);
	if (!giornataMercatoChiusa) {
	    try {
		Concessioniuso cu = concessioniusoService.findById(new PkId(concessioneusoid));
		gm.setConcessioniuso(cu);
		mercatipresenzeTService.update(gm);
		Responsabili r = getCurrentlyAuthenticatedUserDetails();
		LoggerUpdaterecord.log(
			"L'operatore " + r + " ha modificato lo stato della giornata di mercato " + gm.getId() + " con nuovo uso " + concessioneusoid,
			r);
	    } catch (Exception e) {
		log.error("{}", e.getMessage(), e);
		messaggio = "Si è verificato un errore nel salvataggio delle operazioni " + e.getMessage();
	    }
	} else {
	    messaggio = "La giornata è chiusa e non può essere modificata";
	}
	response.getOutputStream().write(messaggio.getBytes());
    }

    @RequestMapping
    public void ajaxModificaFlagConteggiaPresAss(@RequestParam("idGiornata") Integer idGiornata, @RequestParam("valore") Integer valore, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	String messaggio = "Operazione avvenuta correttamente";
	try {
	    boolean valoreDaSalvare = false;
	    if (valore != null && valore.intValue() == 1) {
		valoreDaSalvare = true;
	    }
	    mercatipresenzeTService.aggiornaFlagConteggiaPresAss(idGiornata, valoreDaSalvare);
	} catch (Exception e) {
	    log.error("{}", e.getMessage(), e);
	    messaggio = "Si è verificato un errore nel salvataggio delle operazioni " + e.getMessage();
	}
	response.getOutputStream().write(messaggio.getBytes());
    }

    @RequestMapping
    public void ajaxModificaFlagPopolaConcessionari(@RequestParam("idGiornata") Integer idGiornata, @RequestParam("valore") Integer valore,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	String messaggio = "Operazione avvenuta correttamente";
	try {
	    boolean valoreDaSalvare = false;
	    if (valore != null && valore.intValue() == 1) {
		valoreDaSalvare = true;
	    }
	    mercatipresenzeTService.aggiornaFlagPopolaconcessionari(idGiornata, valoreDaSalvare);
	} catch (Exception e) {
	    log.error("{}", e.getMessage(), e);
	    messaggio = "Si è verificato un errore nel salvataggio delle operazioni " + e.getMessage();
	}
	response.getOutputStream().write(messaggio.getBytes());
    }

    @RequestMapping
    public String ajaxSegnaRinunciaPosteggio(@RequestParam("presenzaCodice") Integer presenzaCodice, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	MercatipresenzeD mpd = mercatipresenzeDService.findById(new PkId(presenzaCodice));
	List<MercatipresenzeDDTO> listaPosteggi = mercatipresenzeDService.findListaPosteggi(mpd.getMercatiPresenzeT());
	model.addAttribute("listaPosteggi", listaPosteggi);
	model.addAttribute("mpd", mpd);
	return "gestionepresenze/ajaxRinunciaPosteggio";
    }

    @RequestMapping
    public void ajaxUpdateSegnaRinunciaPosteggio(@RequestParam("presenzaCodice") Integer presenzaCodice,
	    @RequestParam("idposteggio") Integer idposteggio, @RequestParam(value = "rinuncia", required = false) Boolean rinuncia, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (rinuncia.booleanValue()) {
	    mercatipresenzeDService.updateSegnaRinunciaPosteggio(presenzaCodice, idposteggio);
	} else {
	    mercatipresenzeDService.updateRimuoviRinunciaPosteggio(presenzaCodice);
	}
	response.getOutputStream().write("OK".getBytes());
    }

    @RequestMapping
    public String ajaxMerceologiePosteggio(@RequestParam("idPosteggio") Integer idPosteggio, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	List<MercatiDattivitaistat> atts = mercatiDattivitaistatService.findAttivitaPosteggio(idPosteggio);
	model.addAttribute("atts", atts);
	return "gestionepresenze/ajaxMerceologiePosteggio";
    }

    @RequestMapping
    public String segnaZero(@RequestParam("codiceMercato") Integer codiceMercato, @RequestParam("zeroX") String zeroX,
	    @RequestParam("zeroY") String zeroY, HttpServletRequest request, HttpServletResponse response) {

	if (request.getSession().getAttribute("zeroX") == null) {
	    request.getSession().setAttribute("zeroX", zeroX);
	    request.getSession().setAttribute("zeroY", zeroY);
	}
	return "redirect:graficoPosteggi.htm?codiceMercato=" + codiceMercato + "&codiceUso=" + request.getParameter("codiceUso");
    }

    @RequestMapping
    public String inserisciCoordinate(@RequestParam("codiceMercato") Integer codiceMercato, @RequestParam("codicePosteggio") String codicePosteggio,
	    @RequestParam("coords") String coords, HttpServletRequest request, HttpServletResponse response) {

	Mercati mercato = mercatiService.findById(new PkId(codiceMercato));
	Set<MercatiD> posteggi = mercato.getMercatiDs();
	for (MercatiD posteggio : posteggi) {
	    if (posteggio.getCodiceposteggio().equals(codicePosteggio)) {
		posteggio.setCoordinate(coords);
		mercatiDService.update(posteggio);
		break;
	    }
	}
	return "redirect:graficoPosteggi.htm?codiceMercato=" + mercato.getId().getCodice() + "&codiceUso=" + request.getParameter("codiceUso");
    }

    @RequestMapping
    public String segnaPresenzaConcessionario(@RequestParam("giornoMercato") Date giornoMercato, @RequestParam("codiceMercato") Integer codiceMercato,
	    @RequestParam("usoMercato") Integer usoMercato, @RequestParam("idPosteggio") Integer idPosteggio, @RequestParam("codice") Integer id,
	    @RequestParam("codiceAnagrafe") Integer codiceAnagrafe, @RequestParam("idAut") Integer idAut,
	    @RequestParam(value = "catMerc", required = false) String catMerc, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	// recupero il mercato
	Mercati mercato = mercatiService.findById(new PkId(codiceMercato));
	// recupero l'uso del mercato
	MercatiUso uso = mercatiUsoService.findById(new PkId(usoMercato));
	// recupero il giorno del mercato
	Calendar data = Calendar.getInstance();
	data.setTime(giornoMercato);
	MercatipresenzeT giorno = mercatipresenzeTService.findByDataregistrazioneAndMercatoAndMercatoUso(data, mercato, uso);
	// recupero l'autorizzazione
	Autorizzazioni autorizzazioni = null;
	if (idAut != null) {
	    autorizzazioni = autorizzazioniService.findById(new PkId(idAut));
	}
	SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String _giornoMercato = dateFormat.format(giornoMercato);
	// segno le presenze
	Map<Integer, PosteggiConcessioniHelper> findPosteggiMercatoAllaData = mercatiService.findPosteggiMercatoAllaData(codiceMercato, usoMercato,
		giornoMercato);
	boolean presenzaSegnata = mercatipresenzeTService.segnaPresenzaConcessionario(giorno, idPosteggio, autorizzazioni, catMerc,
		findPosteggiMercatoAllaData.get(idPosteggio));
	if (!presenzaSegnata) {
	    // redirect a pagina ricerca autorizzazioni
	    return "redirect:../autorizzazioni/listDaGestionePresenze.htm?codiceAnagrafe=" + codiceAnagrafe + "&codiceMercato=" + codiceMercato +
		   "&usoMercato=" + usoMercato + "&giornoMercato=" + _giornoMercato + "&idPosteggio=" + idPosteggio + "&codice=" + id +
		   "&occupante=concessionario";
	}
	return "redirect:list.htm?codiceMercato=" + codiceMercato + "&usoMercato=" + usoMercato + "&giornoMercato=" + _giornoMercato;
    }

    @RequestMapping
    public String eliminaPresenza(@RequestParam("giornoMercato") Date giornoMercato, @RequestParam("codiceMercato") Integer codiceMercato,
	    @RequestParam("usoMercato") Integer usoMercato, @RequestParam("codPresenza") Integer idPresenza, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws MercatiAppException {

	// elimino la presenza
	mercatipresenzeTService.eliminaPresenzaOccupante(idPresenza);
	SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String dataFormat = dateFormat.format(giornoMercato);
	return "redirect:list.htm?codiceMercato=" + codiceMercato + "&usoMercato=" + usoMercato + "&giornoMercato=" + dataFormat;
    }

    @RequestMapping
    public void ajaxEliminaPresenzaSpuntista(@RequestParam("codPresenza") Integer codPresenza, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws MercatiAppException {

	// elimino la presenza
	MercatipresenzeD mpt = mercatipresenzeDService.findById(new PkId(codPresenza));
	if (mpt.isSpuntista()) {
	    mercatipresenzeTService.eliminaPresenzaOccupante(codPresenza);
	}
    }

    @RequestMapping
    public String segnaPresenzaSpuntista(@RequestParam("giornoMercato") Date giornoMercato, @RequestParam("codiceMercato") Integer codiceMercato,
	    @RequestParam("usoMercato") Integer usoMercato, @RequestParam("idPosteggio") Integer idPosteggio,
	    @RequestParam("codiceAnagrafe") Integer codAnagrafe, @RequestParam("idAut") Integer idAut,
	    @RequestParam(value = "catMerc", required = false) String catMerc, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws MercatiAppException {

	//1. Raccolta dati
	PresenzaDaRegistrareBean dati = new PresenzaDaRegistrareBean();
	dati.setGiornata(giornoMercato);
	dati.setCodiceMercato(codiceMercato);
	dati.setCodiceUso(usoMercato);
	dati.setCodicePosteggio(idPosteggio);
	dati.setCodiceAnagrafeSpuntista(codAnagrafe);
	dati.setCodiceAutorizzazione(idAut);
	dati.setCategoriaMerceologica(catMerc);
	//2. Assegnazione della presenza
	this.mercatiAppService.segnaPresenzaSpuntista(dati);
	//
	SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String dataFormat = dateFormat.format(giornoMercato);
	return "redirect:list.htm?codiceMercato=" + codiceMercato + "&usoMercato=" + usoMercato + "&giornoMercato=" + dataFormat;
    }

    @RequestMapping
    public void ajaxUpdateNoteGiornata(@RequestParam("idGiornata") Integer idGiornata, @RequestParam("note") String note, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	String risposta = "Dati aggiornati";
	try {
	    mercatipresenzeTService.updateNoteGiornata(idGiornata, note);
	} catch (Exception e) {
	    risposta = "Errore nel salvataggio dei dati: " + e.getMessage();
	}
	response.getOutputStream().write(risposta.getBytes("UTF-8"));
    }

    @RequestMapping
    public String updatePresenzaSpuntistaNoPosteggio(@RequestParam("giornoMercato") Date giornoMercato,
	    @RequestParam("codiceMercato") Integer codiceMercato, @RequestParam("usoMercato") Integer usoMercato,
	    @RequestParam("idPosteggio") Integer idPosteggio, @RequestParam("presenzaDId") Integer presenzaDId, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws MercatiAppException {

	this.mercatiAppService.updatePresenzaSpuntistaNoPosteggio(presenzaDId, codiceMercato, idPosteggio, usoMercato, giornoMercato);
	//
	SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String dataFormat = dateFormat.format(giornoMercato);
	return "redirect:list.htm?codiceMercato=" + codiceMercato + "&usoMercato=" + usoMercato + "&giornoMercato=" + dataFormat;
    }

    @RequestMapping
    public String segnaPresenzaSpuntistaDaGraduatoria(@RequestParam("idPresenzaD") Integer idPresenzaD,
	    @RequestParam("idAutorizzazione") Integer idAutorizzazione, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws MercatiAppException {

	MercatipresenzeD presD = this.mercatiAppService.segnaPresenzaSpuntistaDaGraduatoria(idPresenzaD, idAutorizzazione);
	//
	Integer codiceMercato = presD.getMercatiPresenzeT().getMercato().getId().getCodice();
	Integer usoMercato = presD.getMercatiPresenzeT().getMercatoUso().getId().getCodice();
	Date giornoMercato = presD.getMercatiPresenzeT().getDataRegistrazione();
	SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String dataFormat = dateFormat.format(giornoMercato);
	//
	return "redirect:list.htm?codiceMercato=" + codiceMercato + "&usoMercato=" + usoMercato + "&giornoMercato=" + dataFormat;
    }

    @RequestMapping
    public String segnaPresenzaSpuntistaDaLista(@RequestParam("idPresenzaDDest") Integer idPresenzaDDest,
	    @RequestParam("idMercatiPresenzeDSrc") Integer idMercatiPresenzeDSrc, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws MercatiAppException {

	//
	MercatipresenzeD presenza = this.mercatiAppService.segnaPresenzaSpuntistaDaLista(idMercatiPresenzeDSrc, idPresenzaDDest);
	//
	Integer codiceMercato = presenza.getMercatiPresenzeT().getMercato().getId().getCodice();
	Integer usoMercato = presenza.getMercatiPresenzeT().getMercatoUso().getId().getCodice();
	SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	Date giornoMercato = presenza.getMercatiPresenzeT().getDataRegistrazione();
	String dataFormat = dateFormat.format(giornoMercato);
	//
	return "redirect:list.htm?codiceMercato=" + codiceMercato + "&usoMercato=" + usoMercato + "&giornoMercato=" + dataFormat;
    }

    @RequestMapping
    public String segnaPresenzaSpuntistaNoPosteggio(@RequestParam("giornoMercato") Date giornoMercato,
	    @RequestParam("codiceMercato") Integer codiceMercato, @RequestParam("usoMercato") Integer usoMercato,
	    @RequestParam("codiceAnagrafe") Integer codAnagrafe, @RequestParam("idAut") Integer idAut,
	    @RequestParam(value = "catMerc", required = false) String catMerc, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws MercatiAppException {

	// recupero il mercato
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	// recupero l'uso del mercato
	MercatiUso uso = mercatiUsoService.findById(new PkId(usoMercato));
	// recupero il giorno del mercato
	Calendar data = Calendar.getInstance();
	data.setTime(giornoMercato);
	MercatipresenzeT giorno = mercatipresenzeTService.findByDataregistrazioneAndMercatoAndMercatoUso(data, mercati, uso);
	// recupero lo spuntista
	Anagrafe spuntista = anagrafeService.findById(new PkId(codAnagrafe));
	MercatipresenzeD mpd = mercatipresenzeDService.isAssegnatoPosteggioASpuntistaAndAutNellaGiornata(codiceMercato, usoMercato, idAut,
		giornoMercato);
	if (EntityUtils.getNestedProperty(mpd, "id.codice") != null) {
	    Autorizzazioni aut = autorizzazioniService.findById(new PkId(idAut));
	    StringBuilder sb = new StringBuilder();
	    sb.append("Lo spuntista ").append(spuntista.getDescrizioneRichiedente()).append(" con l'autorizzazione ")
		    .append(aut.getTransientEstremiAut());
	    if (mpd.getPosteggio() != null) {
		sb.append(" già presente per la giornata per il posteggio ").append(mpd.getPosteggio().getCodiceposteggio());
	    } else {
		sb.append(" già presente per la giornata");
	    }
	    FlashMessages.getWarnings().add(sb.toString());
	    String _g = Utilities.formatDate(giornoMercato, false);
	    return "redirect:../autorizzazioni/listDaGestionePresenze.htm?codiceAnagrafe=" + codAnagrafe + "&codiceMercato=" + codiceMercato +
		   "&usoMercato=" + usoMercato + "&giornoMercato=" + _g + "&idPosteggio=&codice=&occupante=spuntnop";
	} else {
	    mercatipresenzeTService.segnaPresenzaSpuntistaNoPosteggio(giorno, idAut, catMerc);
	}
	SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String dataFormat = dateFormat.format(giornoMercato);
	return "redirect:list.htm?codiceMercato=" + codiceMercato + "&usoMercato=" + usoMercato + "&giornoMercato=" + dataFormat;
    }

    @RequestMapping
    public String segnaPresentiTuttiConcessionari(@RequestParam("giornoMercato") Date giornoMercato,
	    @RequestParam("codiceMercato") Integer codiceMercato, @RequestParam("usoMercato") Integer usoMercato,
	    @RequestParam(required = false, value = "useHistoryBack") Boolean useHistoryBack, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	// recupero il mercato
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	// recupero l'uso del mercato
	MercatiUso uso = mercatiUsoService.findById(new PkId(usoMercato));
	// recupero il giorno del mercato
	Calendar data = Calendar.getInstance();
	data.setTime(giornoMercato);
	MercatipresenzeT giorno = mercatipresenzeTService.findByDataregistrazioneAndMercatoAndMercatoUso(data, mercati, uso);
	// segno presenti i concessionari
	mercatipresenzeTService.segnaPresentiTuttiConcessionari(giorno);
	SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String dataFormat = dateFormat.format(giornoMercato);
	return "redirect:list.htm?codiceMercato=" + codiceMercato + "&usoMercato=" + usoMercato + "&giornoMercato=" + dataFormat +
	       "&useHistoryBack=" + useHistoryBack;
    }

    @RequestMapping
    public String chiudiGiornoMercato(@RequestParam("mercato.id.codice") Integer codicemercato,
	    @RequestParam("mercatouso.id.codice") Integer codicemercatoUso, @RequestParam("data") Date data, SessionStatus status) {

	// Recupero gli oggetti necessari per effettuare al query sulla tabella
	// Mercatipreesnze_T
	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codicemercatoUso));
	Calendar date = Calendar.getInstance();
	date.setTime(data);
	MercatipresenzeT giornoMercato = mercatipresenzeTService.findByDataregistrazioneAndMercatoAndMercatoUso(date, mercati, mercatiUso);
	// Trasformo la data nel formato giusto richiesto dalla segnatura del
	// metodo del controllo a cui ritorno
	Date datecalendar = date.getTime();
	SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String giornomercato = sdf.format(datecalendar);
	// Chiudo il giorno del mercato ponendo il FLAG_PRESENZA=1
	try {
	    mercatipresenzeTService.closeMarketDay(giornoMercato);
	} catch (Exception e) {
	    log.error(e.getMessage());
	    FlashMessages.getWarnings().add(e.getMessage());
	    return "redirect:list.htm?codiceMercato=" + codicemercato + "&usoMercato=" + codicemercatoUso + "&giornoMercato=" + giornomercato;
	}
	return "redirect:list.htm?codiceMercato=" + codicemercato + "&usoMercato=" + codicemercatoUso + "&giornoMercato=" + giornomercato +
	       "&chiusura=ok";
    }

    @RequestMapping
    public String apriGiornoMercato(@RequestParam("mercato.id.codice") Integer codicemercato,
	    @RequestParam("mercatouso.id.codice") Integer codicemercatoUso, @RequestParam("data") Date data, SessionStatus status) {

	// Recupero gli oggetti necessari per effettuare al query sulla tabella
	// Mercatipreesnze_T
	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codicemercatoUso));
	Calendar date = Calendar.getInstance();
	date.setTime(data);
	MercatipresenzeT giornoMercato = mercatipresenzeTService.findByDataregistrazioneAndMercatoAndMercatoUso(date, mercati, mercatiUso);
	// Trasformo la data nel formato giusto richiesto dalla segnatura del
	// metodo del controllo a cui ritorno
	Date datecalendar = date.getTime();
	SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String giornomercato = sdf.format(datecalendar);
	// Riapro il giorno del mercato ponendo il FLAG_PRESENZA=0
	// la riapertura può essere fatta solo se il calendario non è storicizzato
	try {
	    mercatipresenzeTService.apriGiornoMercato(giornoMercato);
	} catch (Exception e) {
	    log.error(e.getMessage());
	    return "redirect:list.htm?codiceMercato=" + codicemercato + "&usoMercato=" + codicemercatoUso + "&giornoMercato=" + giornomercato +
		   "&apertura=ko";
	}
	return "redirect:list.htm?codiceMercato=" + codicemercato + "&usoMercato=" + codicemercatoUso + "&giornoMercato=" + giornomercato +
	       "&apertura=ok";
    }

    @RequestMapping()
    public String aggiornaFlagProprietario(@RequestParam("codiceMercato") Integer codicemercato, @RequestParam("usoMercato") Integer codicemercatoUso,
	    @RequestParam("giornoMercato") Date data, @RequestParam("codice") Integer codice, @RequestParam("flag") String flag) {

	MercatipresenzeD mercatipresenzeD = mercatipresenzeDService.findById(new PkId(codice));
	if (BooleanUtils.isTrue(Boolean.valueOf(flag))) {
	    mercatipresenzeD.setProprietario(Integer.valueOf(1));
	} else {
	    mercatipresenzeD.setProprietario(Integer.valueOf(0));
	}
	mercatipresenzeDService.update(mercatipresenzeD);
	SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	return "redirect:list.htm?codiceMercato=" + codicemercato + "&usoMercato=" + codicemercatoUso + "&giornoMercato=" + sdf.format(data);
    }

    @RequestMapping()
    public String aggiornaFlagGiustificazione(@RequestParam("codiceMercato") Integer codicemercato,
	    @RequestParam("usoMercato") Integer codicemercatoUso, @RequestParam("giornoMercato") Date data, @RequestParam("codice") Integer codice,
	    @RequestParam("flag") boolean flag, @RequestParam(value = "idAut", required = false) Integer idAut,
	    @RequestParam(value = "catMerc", required = false) String catMerc, @RequestParam(value = "dataFine", required = false) Date dataFine,
	    @RequestParam(value = "motivazione", required = false) String motivazione) {

	MercatipresenzeD mercatipresenzeD = mercatipresenzeDService.findById(new PkId(codice));
	Calendar date = Calendar.getInstance();
	date.setTime(data);
	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codicemercatoUso));
	MercatipresenzeT giornoMercato = mercatipresenzeTService.findByDataregistrazioneAndMercatoAndMercatoUso(date, mercati, mercatiUso);
	SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String dataGiornoMercato = sdf.format(data);
	Autorizzazioni autorizzazioni = null;
	if (idAut != null) {
	    autorizzazioni = autorizzazioniService.findById(new PkId(idAut));
	}
	Map<Integer, PosteggiConcessioniHelper> findPosteggiMercatoAllaData = mercatiService.findPosteggiMercatoAllaData(codicemercato,
		codicemercatoUso, data);
	boolean assenzaAggiornata = mercatipresenzeTService.segnaAssenzaGiustificataConcessionario(giornoMercato,
		mercatipresenzeD.getPosteggio().getId().getCodice(), autorizzazioni, catMerc, flag, motivazione,
		findPosteggiMercatoAllaData.get(mercatipresenzeD.getPosteggio().getId().getCodice()));
	if (!assenzaAggiornata) {
	    // redirect a pagina ricerca autorizzazioni
	    String _dataFine = Utilities.formatDate(dataFine, false);
	    return "redirect:../autorizzazioni/listDaGestionePresenze.htm?codiceAnagrafe=" +
		   mercatipresenzeD.getConcessionario().getId().getCodice() + "&codiceMercato=" + codicemercato + "&usoMercato=" + codicemercatoUso +
		   "&giornoMercato=" + dataGiornoMercato + "&idPosteggio=" + mercatipresenzeD.getPosteggio().getId().getCodice() + "&codice=" +
		   codice + "&occupante=assenza&flag=" + flag + "&dataFine=" + _dataFine;
	}
	if (dataFine != null) {
	    Calendar dataFineCal = Calendar.getInstance();
	    dataFineCal.setTime(dataFine);
	    if (dataFine.compareTo(data) > 0) {
		List<MercatipresenzeT> giorniMercato = mercatipresenzeTService.findByMercatoAndMercatoUsoAndDateInterval(codicemercato,
			codicemercatoUso, date, dataFineCal);
		for (MercatipresenzeT mercatipresenzeT : giorniMercato) {
		    mercatipresenzeTService.segnaAssenzaGiustificataConcessionario(mercatipresenzeT,
			    mercatipresenzeD.getPosteggio().getId().getCodice(), autorizzazioni, catMerc, flag, motivazione,
			    findPosteggiMercatoAllaData.get(mercatipresenzeD.getPosteggio().getId().getCodice()));
		}
	    }
	}
	return "redirect:list.htm?codiceMercato=" + codicemercato + "&usoMercato=" + codicemercatoUso + "&giornoMercato=" + dataGiornoMercato;
    }

    @RequestMapping()
    public String assegnaMotivazioneGiustificazione(@RequestParam("codiceMercato") Integer codicemercato,
	    @RequestParam("usoMercato") Integer codicemercatoUso, @RequestParam("giornoMercato") Date data, @RequestParam("codice") Integer codice,
	    @RequestParam("testo") String testo) {

	MercatipresenzeD mercatipresenzeD = mercatipresenzeDService.findById(new PkId(codice));
	mercatipresenzeD.setMotivazione(testo);
	mercatipresenzeDService.update(mercatipresenzeD);
	SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	return "redirect:list.htm?codiceMercato=" + codicemercato + "&usoMercato=" + codicemercatoUso + "&giornoMercato=" + sdf.format(data);
    }

    @RequestMapping()
    public String cancellaMotivazioneGiustificazione(@RequestParam("codiceMercato") Integer codicemercato,
	    @RequestParam("usoMercato") Integer codicemercatoUso, @RequestParam("giornoMercato") Date data, @RequestParam("codice") Integer codice) {

	MercatipresenzeD mercatipresenzeD = mercatipresenzeDService.findById(new PkId(codice));
	mercatipresenzeD.setMotivazione("");
	mercatipresenzeDService.update(mercatipresenzeD);
	SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	return "redirect:list.htm?codiceMercato=" + codicemercato + "&usoMercato=" + codicemercatoUso + "&giornoMercato=" + sdf.format(data);
    }

    @RequestMapping()
    public String createControlloAssenze(Model model) {

	AssenzeFilter assenzeFilter = new AssenzeFilter();
	List<MercatipresenzeT> anniList = mercatipresenzeTService.findAnniMercatiPresenti();
	model.addAttribute("anniList", anniList);
	model.addAttribute("assenzeFilter", assenzeFilter);
	setPageAttributes(model);
	return "gestionepresenze/assenzeform";
    }

    @RequestMapping()
    public String controlloAssenze(@ModelAttribute("assenzeFilter") AssenzeFilter assenzeFilter, BindingResult result, SessionStatus status,
	    Model model, HttpServletRequest request, HttpServletResponse response) {

	List<VwAssenzeconcessionari> risultato = vwAssenzeconcessionariService.findByAssenzeFilter(assenzeFilter);
	boolean export = createJMesaExport(request, response, risultato);
	if (export)
	    return null;
	MercatiUso mercatiUso = new MercatiUso();
	Mercati mercati = new Mercati();
	if (assenzeFilter.getMercatiUso().getId().getCodice() != null) {
	    mercatiUso = mercatiUsoService.findById(new PkId(assenzeFilter.getMercatiUso().getId().getCodice()));
	}
	if (assenzeFilter.getMercati().getId().getCodice() != null) {
	    mercati = mercatiService.findById(new PkId(assenzeFilter.getMercati().getId().getCodice()));
	}
	assenzeFilter.setMercati(mercati);
	assenzeFilter.setMercatiUso(mercatiUso);
	model.addAttribute("assenzeFilter", assenzeFilter);
	model.addAttribute("assenzeList", risultato);
	return "gestionepresenze/assenzelist";
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping
    public String chiudiAnnoMercato(@ModelAttribute("mercatipresenzet") MercatipresenzeT mercatipresenzet, BindingResult result, SessionStatus status,
	    Model model, @RequestParam("mercati.id.codice") Integer codicemercato, @RequestParam("mercatouso.id.codice") Integer codicemercatoUso,
	    @RequestParam("anno") Integer anno) {

	Mercati mercato = mercatiService.findById(new PkId(codicemercato));
	MercatiUso uso = mercatiUsoService.findById(new PkId(codicemercatoUso));
	try {
	    mercatipresenzeTService.chiudiAnnoMercato(mercato, uso, anno);
	} catch (Exception e) {
	    log.error(e.getMessage());
	    copyErrorsToBindingResult(result, "mercatipresenzet", e);
	    Map map = new HashMap();
	    map.put("codice", codicemercato);
	    map.put("mercatouso.id.codice", codicemercatoUso);
	    map.put("anno", anno);
	    map.put("step", "2");
	    model.addAttribute("commandName", "mercatipresenzet");
	    model.addAttribute("method", "../calendariomercato/view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	return "redirect:../calendariomercato/view.htm?codice=" + codicemercato + "&mercatouso.id.codice=" + codicemercatoUso + "&anno=" + anno +
	       "&step=2" + "&status_msg=02";
    }

    @RequestMapping
    public void ajaxWarningSpuntisti(@RequestParam(value = "idpresenzad") Integer idpresenzad, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	String result = "OK";
	List<String> avvisi = mercatipresenzeDService.avvisiSpuntisti(idpresenzad);
	if (!avvisi.isEmpty()) {
	    result = "Sono presenti i seguenti avvisi:<ul>";
	    for (String a : avvisi) {
		result += "<li>" + a + "</li>";
	    }
	    result += "</ul>";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public String listGiornateMercatoPerResponsabileEDataOdierna(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.debug("listMercatiPerResponsabileEData# Recupero l'utente loggato");
	Responsabili r = getCurrentlyAuthenticatedUserDetails();
	log.debug("listMercatiPerResponsabileEData# recuperlo la lista dei mercati per l'utente loggato={}[{}]", r.getResponsabile(),
		r.getId().getCodice());
	String _today = Utilities.getToday(false);
	log.debug("listMercatiPerResponsabileEData# Date string ={}", _today);
	Date date = Utilities.parseDateString(_today, false);
	log.debug("listMercatiPerResponsabileEData# Date={}", date);
	List<MercatiResponsabili> mercatiResponsabilis = mercatiResponsabiliService.findByResponsabileAndData(r.getId().getCodice(), date);
	log.debug("listGiornateMercatoPerResponsabileEDataOdierna# Numero mercati per la giornata {}  per l'utente loggato={}[{}]: {}",
		new Object[] { Utilities.formatDate(date, false), r.getResponsabile(), r.getId().getCodice(), mercatiResponsabilis.size() });
	// clico i mercati e creo il link per andare alla gestione delle presenze
	List<GiornataMercatoCommand> giornataMercatoCommands = new ArrayList<GiornataMercatoCommand>();
	GiornataMercatoCommand giornataMercatoCommand = null;
	for (MercatiResponsabili mercatiResponsabili : mercatiResponsabilis) {
	    Mercati mercato = mercatiService.findById(new PkId(mercatiResponsabili.getMercato().getId().getCodice()));
	    Integer codiceMercato = mercato.getId().getCodice();
	    String descrizioneMercato = mercato.getDescrizione();
	    StringBuilder sb = new StringBuilder("../gestionepresenze/list.htm?");
	    sb.append("giornoMercato=");
	    sb.append(Utilities.formatDate(date, false)).append("&codiceMercato=").append(codiceMercato);
	    List<MercatiUso> mercatiUsos = mercatiUsoService.findByMercato(mercato);
	    for (MercatiUso mercatiUso : mercatiUsos) {
		Integer codiceuso = mercatiUso.getId().getCodice();
		String descrizioneUso = mercatiUso.getDescrizione();
		List<MercatipresenzeT> list = mercatipresenzeTService.findByMercatoAndMercatoUsoAndDate(codiceMercato, codiceuso, date);
		if (!list.isEmpty()) {
		    giornataMercatoCommand = new GiornataMercatoCommand();
		    StringBuilder sb1 = new StringBuilder(sb);
		    sb1.append("&usoMercato=").append(codiceuso.toString());
		    sb1.append("&useHistoryBack=true");
		    giornataMercatoCommand.setCodiceMercato(codiceMercato);
		    giornataMercatoCommand.setDescrizioneMercato(descrizioneMercato);
		    giornataMercatoCommand.setCodiceUso(codiceuso);
		    giornataMercatoCommand.setDescrizioneUso(descrizioneUso);
		    giornataMercatoCommand.setDataGiornata(Utilities.formatDate(date, false));
		    log.debug("listGiornateMercatoPerResponsabileEDataOdierna# Link accesso alla giornata = {}", sb1.toString());
		    giornataMercatoCommand.setLinkGiornataGestionePresenze(sb1.toString());
		    giornataMercatoCommands.add(giornataMercatoCommand);
		}
	    }
	}
	model.addAttribute("giornataMercatoCommands", giornataMercatoCommands);
	model.addAttribute("responsabile", r.getResponsabile());
	return "gestionepresenze/listGiornateMercatiPerGiornataEResponsabile";
    }

    @RequestMapping
    public void ajaxContaPreferenze(@RequestParam(value = "idpresenzad") Integer idpresenzad, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String result = "";
	MercatipresenzeD presenza = mercatipresenzeDService.findById(new PkId(idpresenzad));
	List<MercatipresenzeTPrenot> prenot = mercatipresenzeTPrenotService
		.findByMercatipresenzeTAndPosteggio(presenza.getMercatiPresenzeT().getId().getCodice(), presenza.getPosteggio().getId().getCodice());
	int size = prenot.size();
	result = String.valueOf(size);
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public void ajaxImpostaFaseSpunta(@RequestParam(value = "giornoMercato") Integer giornoMercato,
	    @RequestParam(value = "mercatiSpuntaId") Integer mercatiSpuntaId, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	String result = "OK";
	try {
	    MercatipresenzeT presenza = mercatipresenzeTService.findById(new PkId(giornoMercato));
	    Responsabili r = getCurrentlyAuthenticatedUserDetails();
	    LoggerUpdaterecord.log("L'operatore " + r + " ha modificato lo stato della giornata di mercato " + giornoMercato, r);
	    MercatiSpunte ms = mercatiSpunteService.findById(new PkId(mercatiSpuntaId));
	    presenza.setMercatiSpunte(ms);
	    mercatipresenzeTService.update(presenza);
	} catch (Exception e) {
	    result = "KO";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public String ajaxVisualizzaPreferenze(@RequestParam(value = "idpresenzad") Integer idpresenzad, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	MercatipresenzeD presenza = mercatipresenzeDService.findById(new PkId(idpresenzad));
	List<MercatipresenzeTPrenot> prenots = mercatipresenzeTPrenotService
		.findByMercatipresenzeTAndPosteggio(presenza.getMercatiPresenzeT().getId().getCodice(), presenza.getPosteggio().getId().getCodice());
	model.addAttribute("prenots", prenots);
	return "gestionepresenze/ajaxVisualizzaPreferenze";
    }

    @RequestMapping
    public String ajaxFormDettaglioPagamento(@RequestParam(value = "idmercatipresenza") Integer idmercatipresenza, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws MercatiAppException {

	MercatipresenzeD mercpresd = mercatipresenzeDService.findById(new PkId(idmercatipresenza));
	String codiceComune = null;
	if (mercpresd.getMercatiPresenzeT().getMercato().getComune() != null) {
	    codiceComune = mercpresd.getMercatiPresenzeT().getMercato().getComune().getCodicecomune();
	}
	boolean isNodoPagamenti = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune).isAttiva();
	model.addAttribute("mercpresd", mercpresd);
	List<Tipimodalitapagamento> tmpags = tipimodalitapagamentoService.findAll(null, null, false);
	model.addAttribute("tmpags", tmpags);
	if (isNodoPagamenti) {
	    PosteggioInfoRestBean calcolaCostoPosteggio = mercatiAppService.calcolaCostoPosteggio(idmercatipresenza);
	    if (mercpresd.getDettPosizioneDebitoria() != null) {
		StatoPagamentoNodoHelper hlp = nodoPagamentiService.getStatoPagamentoSpuntistaHelper(idmercatipresenza);
		model.addAttribute("helper", hlp);
	    }
	    model.addAttribute("costoPosteggio", calcolaCostoPosteggio);
	    return "gestionepresenze/ajaxFormDettaglioPagamentoNodo";
	}
	return "gestionepresenze/ajaxFormDettaglioPagamento";
    }

    @RequestMapping
    public void ajaxAggiungiSpuntista(@RequestParam(value = "codiceMercato") Integer codiceMercato,
	    @RequestParam(value = "usoMercato") Integer usoMercato, @RequestParam("giornoMercato") Date giornoMercato,
	    @RequestParam(value = "autId") Integer autId, @RequestParam(value = "codiceAnagrafe") Integer codiceAnagrafe, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	// recupero il mercato
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	// recupero l'uso del mercato
	MercatiUso uso = mercatiUsoService.findById(new PkId(usoMercato));
	// recupero il giorno di mercato
	//Recupero l'autorizzazione 
	Autorizzazioni aut = autorizzazioniService.findById(new PkId(autId));
	Calendar data = Calendar.getInstance();
	data.setTime(giornoMercato);
	MercatipresenzeT mercatipresenzeT = mercatipresenzeTService.findByDataregistrazioneAndMercatoAndMercatoUso(data, mercati, uso);
	MercatipresenzeD mpd = mercatipresenzeDService.isAssegnatoPosteggioASpuntistaAndAutNellaGiornata(codiceMercato, usoMercato, autId,
		giornoMercato);
	if (EntityUtils.getNestedProperty(mpd, "id.codice") != null) {
	    response.setContentType("text/plain");
	    StringBuilder sb = new StringBuilder("<b>");
	    sb.append(" Autorizzazione ").append(aut.getTransientEstremiAut()).append(" associata al posteggio ")
		    .append(mpd.getPosteggio().getCodiceposteggio()).append("</b>");
	    response.getWriter().write(sb.toString());
	} else {
	    mercatipresenzeTService.segnaPresenzaSpuntistaNoPosteggio(mercatipresenzeT, autId, null);
	}
    }

    @RequestMapping
    public void ajaxSalvaPagamento(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	response.setContentType("text/plain");
	String mercatipresenzedid = request.getParameter("mercatipresenzedid");
	String modalitapagamento = request.getParameter("modalitapagamento");
	String importo_id = request.getParameter("importo_id");
	String riferimenti_pagamento = request.getParameter("riferimenti_pagamento");
	String flag_pagato_id = request.getParameter("flag_pagato_id");
	Tipimodalitapagamento tmp = null;
	String ret = "Dati salvati corettamente";
	try {
	    if (StringUtils.isNotBlank(modalitapagamento) && Utilities.isInteger(modalitapagamento)) {
		tmp = tipimodalitapagamentoService.findById(new PkId(Integer.parseInt(modalitapagamento)));
	    }
	    MercatipresenzeD mpd = mercatipresenzeDService.findById(new PkId(Integer.parseInt(mercatipresenzedid)));
	    if (mpd == null) {
		response.getWriter().write("Si è verificato un errore nel salvataggio dati");
		return;
	    }
	    mpd.setTipimodalitapagamento(tmp);
	    mpd.setRiferimentiPagamento(riferimenti_pagamento);
	    Boolean flagPagato = Boolean.FALSE;
	    if (StringUtils.defaultString(flag_pagato_id).equalsIgnoreCase("on")) {
		flagPagato = Boolean.TRUE;
	    }
	    if (StringUtils.isNotBlank(importo_id)) {
		importo_id = importo_id.replace(',', '.');
		Double importo = Double.valueOf(importo_id);
		mpd.setImporto(BigDecimal.valueOf(importo));
	    }
	    mpd.setFlagPagato(flagPagato);
	    mercatipresenzeDService.update(mpd);
	} catch (Exception e) {
	    ret = "Si è verificato un errore nel salvataggio dei dati";
	}
	response.getWriter().write(ret);
    }

    @RequestMapping
    public String ajaxListaSpuntistiPosteggio(@RequestParam(value = "presenzaCodice") Integer presenzaCodice, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	MercatipresenzeD mpd = mercatipresenzeDService.findById(new PkId(presenzaCodice));
	List<PresenzeSpuntistiHelper> presenzeSpuntistiNoPosteggio = mercatipresenzeDService
		.findListaPresentiSenzaPosteggio(mpd.getMercatiPresenzeT());
	List<PresenzeSpuntistiHelper> presenzeSpuntistiNoPosteggio2 = new ArrayList<PresenzeSpuntistiHelper>();
	for (PresenzeSpuntistiHelper ph : presenzeSpuntistiNoPosteggio) {
	    if (ph.getMercatipresenzeD() != null) {
		if (ph.getMercatipresenzeD().getPosteggioRinunciato() == null) {
		    presenzeSpuntistiNoPosteggio2.add(ph);
		}
	    }
	}
	model.addAttribute("spuntisti", presenzeSpuntistiNoPosteggio2);
	model.addAttribute("presenzaCodice", presenzaCodice);
	model.addAttribute("gestisciFasiSpunta", Boolean.FALSE);
	if (mpd.getMercatiPresenzeT().getMercatiSpunte() != null) {
	    model.addAttribute("gestisciFasiSpunta", Boolean.TRUE);
	}
	return "gestionepresenze/ajaxGraduatoriaSpuntistiPosteggio";
    }

    /**
     * @see #ajaxEsportaSpuntistiNoPosteggio(Integer, Integer, Date, Model, HttpServletRequest, HttpServletResponse)
     * @see #ajaxSpuntistiNoPosteggio(Integer, Integer, Date, Model, HttpServletRequest, HttpServletResponse)
     */
    @RequestMapping
    public String ajaxSpuntistiNoPosteggio(@RequestParam(value = "codiceMercato") Integer codiceMercato,
	    @RequestParam(value = "usoMercato") Integer usoMercato, @RequestParam("giornoMercato") Date giornoMercato, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	// recupero il mercato
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	// recupero l'uso del mercato
	MercatiUso uso = mercatiUsoService.findById(new PkId(usoMercato));
	// recupero il giorno di mercato
	Calendar data = Calendar.getInstance();
	data.setTime(giornoMercato);
	MercatipresenzeT mercatipresenzeT = mercatipresenzeTService.findByDataregistrazioneAndMercatoAndMercatoUso(data, mercati, uso);
	List<PresenzeSpuntistiHelper> presenzeSpuntistiNoPosteggio = mercatipresenzeDService.findListaPresentiSenzaPosteggio(mercatipresenzeT);
	model.addAttribute("spuntisti", presenzeSpuntistiNoPosteggio);
	model.addAttribute("giornoMercato", mercatipresenzeT);
	model.addAttribute("gestisciFasiSpunta", Boolean.FALSE);
	if (mercatipresenzeT.getMercatiSpunte() != null) {
	    model.addAttribute("gestisciFasiSpunta", Boolean.TRUE);
	}
	return "gestionepresenze/ajaxSpuntistiNoPosteggio";
    }

    /**
     * @see #ajaxEsportaSpuntistiNoPosteggio(Integer, Integer, Date, Model, HttpServletRequest, HttpServletResponse)
     * @see #ajaxSpuntistiNoPosteggio(Integer, Integer, Date, Model, HttpServletRequest, HttpServletResponse)
     */
    @RequestMapping
    public void ajaxEsportaSpuntistiNoPosteggio(@RequestParam(value = "codiceMercato") Integer codiceMercato,
	    @RequestParam(value = "usoMercato") Integer usoMercato, @RequestParam("giornoMercato") Date giornoMercato, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	// recupero il mercato
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	// recupero l'uso del mercato
	MercatiUso uso = mercatiUsoService.findById(new PkId(usoMercato));
	// recupero il giorno di mercato
	Calendar data = Calendar.getInstance();
	data.setTime(giornoMercato);
	MercatipresenzeT mercatipresenzeT = mercatipresenzeTService.findByDataregistrazioneAndMercatoAndMercatoUso(data, mercati, uso);
	List<PresenzeSpuntistiHelper> presenzeSpuntistiNoPosteggio = mercatipresenzeDService.findListaPresentiSenzaPosteggio(mercatipresenzeT);
	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista");
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "presenze");
	colonne.add(1, "data_cciaa");
	colonne.add(2, "data_autorizzazione");
	colonne.add(3, "numero_autorizzazione");
	colonne.add(4, "titolare");
	colonne.add(5, "fase spunta");
	result.setColonne(colonne);
	List<String[]> righe = new ArrayList<String[]>();
	int c = 0;
	for (PresenzeSpuntistiHelper scadenza : presenzeSpuntistiNoPosteggio) {
	    String[] riga = new String[colonne.size()];
	    riga[0] = String.valueOf(scadenza.getNumeropresenze());
	    if (scadenza.getDataCciaa() != null) {
		riga[1] = Utilities.formatDate(scadenza.getDataCciaa(), false);
	    } else {
		riga[1] = "";
	    }
	    if (scadenza.getDataAutorizzazione() != null) {
		riga[2] = Utilities.formatDate(scadenza.getDataAutorizzazione(), false);
	    } else {
		riga[2] = "";
	    }
	    if (scadenza.getAut() != null) {
		riga[3] = scadenza.getAut().getAutoriznumero();
	    } else {
		riga[3] = "";
	    }
	    String descrizioneAnagrafe = "";
	    if (scadenza.getMercatipresenzeD() != null) {
		if (scadenza.getMercatipresenzeD().getGerenteSpuntista() != null) {
		    descrizioneAnagrafe = scadenza.getMercatipresenzeD().getGerenteSpuntista().getDescrizioneRichiedente();
		} else {
		    descrizioneAnagrafe = scadenza.getMercatipresenzeD().getOccupante().getDescrizioneRichiedente();
		}
	    }
	    riga[4] = descrizioneAnagrafe;
	    riga[5] = StringUtils.defaultString(scadenza.getFaseSpunta(), "");
	    righe.add(c, riga);
	    c++;
	}
	result.setRighe(righe);
	byte[] b = generateExcelContent(result);
	response.setHeader("Pragma", "public");
	response.setHeader("Cache-Control", "max-age=0");
	String filename = "SpuntistiNoPosteggio.xls";
	response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
	response.setHeader("Content-transfer-encoding", "binary");
	response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	response.setContentLength(b.length);
	ServletOutputStream out = response.getOutputStream();
	out.write(b);
	out.flush();
    }

    /**
     * @see #ajaxEsportaGraduatoriaSpuntistiManifestazione(Integer, Model, HttpServletRequest, HttpServletResponse)
     * @see #ajaxGraduatoriaSpuntistiManifestazione(Integer, Model, HttpServletRequest, HttpServletResponse)
     */
    @RequestMapping
    public String ajaxGraduatoriaSpuntistiManifestazione(@RequestParam("idGiornoMercato") Integer idGiornoMercato, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	MercatipresenzeT mpt = mercatipresenzeTService.findById(new PkId(idGiornoMercato));
	List<AutorizzazioneSpuntistaHelper> spuntistiManifestazione = mercatipresenzeTService
		.graduatoriaSpuntistiManifestazione(mpt.getMercato().getId().getCodice(), mpt.getMercatoUso().getId().getCodice(), idGiornoMercato);
	boolean giornataMercatoChiusa = mercatipresenzeTService.isGiornataMercatoChiusa(idGiornoMercato);
	model.addAttribute("giornataMercatoChiusa", Boolean.valueOf(giornataMercatoChiusa));
	model.addAttribute("giornoMercato", mpt);
	model.addAttribute("spuntisti", spuntistiManifestazione);
	String comuniEsclusi = this.vertComportamentiMercatiService.restCercaComuneListEsclusi();
	if (StringUtils.isNotBlank(comuniEsclusi)) {
	    model.addAttribute("comuni_esclusi", comuniEsclusi.trim());
	}
	return "gestionepresenze/ajaxGraduatoriaSpuntistiNoPosteggio";
    }

    /**
     * @throws Exception
     * @see #ajaxEsportaGraduatoriaSpuntistiManifestazione(Integer, Model, HttpServletRequest, HttpServletResponse)
     * @see #ajaxGraduatoriaSpuntistiManifestazione(Integer, Model, HttpServletRequest, HttpServletResponse)
     */
    @RequestMapping
    public void ajaxEsportaGraduatoriaSpuntistiManifestazione(@RequestParam("idGiornoMercato") Integer idGiornoMercato, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	MercatipresenzeT mpt = mercatipresenzeTService.findById(new PkId(idGiornoMercato));
	List<AutorizzazioneSpuntistaHelper> spuntistiManifestazione = mercatipresenzeTService
		.graduatoriaSpuntistiManifestazione(mpt.getMercato().getId().getCodice(), mpt.getMercatoUso().getId().getCodice(), idGiornoMercato);
	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista");
	List<String> colonne = new ArrayList<String>();
	int pos = 0;
	colonne.add(pos++, "presenze");
	colonne.add(pos++, "data_cciaa");
	colonne.add(pos++, "data_autorizzazione");
	colonne.add(pos++, "numero_autorizzazione");
	colonne.add(pos++, "comune_autorizzazione");
	colonne.add(pos++, "autorizzazione_originaria");
	colonne.add(pos++, "titolare");
	colonne.add(pos++, "gerente");
	result.setColonne(colonne);
	List<String[]> righe = new ArrayList<String[]>();
	int c = 0;
	for (AutorizzazioneSpuntistaHelper scadenza : spuntistiManifestazione) {
	    String[] riga = new String[colonne.size()];
	    pos = 0;
	    riga[pos++] = String.valueOf(scadenza.getNumpresenze());
	    if (scadenza.getDataregditte() != null) {
		riga[pos++] = Utilities.formatDate(scadenza.getDataregditte(), false);
	    } else {
		riga[pos++] = "";
	    }
	    if (scadenza.getAutorizdata() != null) {
		riga[pos++] = Utilities.formatDate(scadenza.getAutorizdata(), false);
	    } else {
		riga[pos++] = "";
	    }
	    riga[pos++] = StringUtils.defaultString(scadenza.getAutoriznumero());
	    riga[pos++] = StringUtils.defaultString(scadenza.getAutorizcomune());
	    riga[pos++] = StringUtils.defaultString(scadenza.getAutoriginnumero());
	    riga[pos++] = StringUtils.defaultString(scadenza.getDescrizioneRichiedente());
	    riga[pos++] = StringUtils.defaultString(scadenza.getDescrizioneGerente());
	    righe.add(c, riga);
	    c++;
	}
	result.setRighe(righe);
	byte[] b = generateExcelContent(result);
	response.setHeader("Pragma", "public");
	response.setHeader("Cache-Control", "max-age=0");
	String filename = "GraduatoriaSpuntistiNoPosteggio.xls";
	response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
	response.setHeader("Content-transfer-encoding", "binary");
	response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	response.setContentLength(b.length);
	ServletOutputStream out = response.getOutputStream();
	out.write(b);
	out.flush();
    }

    private byte[] generateExcelContent(ExportTableHelper helper) throws IOException {

	HSSFWorkbook workbook = new HSSFWorkbook();
	HSSFSheet sheet = workbook.createSheet(StringUtils.defaultIfEmpty(helper.getTableCaption(), "Lista"));
	List<String> colonne = helper.getColonne();
	HSSFRow hssfRow = sheet.createRow(0);
	int columncount = 0;
	if (!colonne.isEmpty()) {
	    for (String colonna : colonne) {
		HSSFCell cell = hssfRow.createCell(columncount++);
		cell.setCellValue(colonna);
	    }
	}
	// renderer body
	int rowcount = 1;
	List<String[]> righe = helper.getRighe();
	if (!righe.isEmpty()) {
	    for (String[] riga : righe) {
		columncount = 0;
		HSSFRow r = sheet.createRow(rowcount++);
		for (String colValue : riga) {
		    HSSFCell cell = r.createCell(columncount++);
		    cell.setCellValue(colValue);
		}
	    }
	}
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	workbook.write(baos);
	return baos.toByteArray();
    }

    @Override
    protected void fixMergeEntityProperty(VwPosteggiconcessioni entity) {

	// non serve implenetare il metodo per questo controller
    }

    @Override
    protected void fixRenderEntityProperty(VwPosteggiconcessioni entity) {

	// non serve implenetare il metodo per questo controller
    }

    @Override
    protected void setPageAttributes(Model model) {

	model.addAttribute("GESTISCI_PROPRIETARIO", this.vertComportamentiMercatiService.gestisciProprietario());
    }

    protected void setPageAttributes(Model model, MercatipresenzeT mpt) {

	setPageAttributes(model);
	String codiceComune = null;
	if (mpt.getMercato().getComune() != null) {
	    codiceComune = mpt.getMercato().getComune().getCodicecomune();
	}
	boolean nodoPagamenti = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune).isAttiva();
	if (nodoPagamenti) {
	    nodoPagamenti = false;
	    mpt = mercatipresenzeTService.findById(new PkId(mpt.getId().getCodice()));
	    if (mpt.getMercato() != null && mpt.getMercato().getFlagAttivanodoPagam() != null
		    && mpt.getMercato().getFlagAttivanodoPagam().booleanValue()) {
		nodoPagamenti = true;
	    }
	}
	model.addAttribute("NODO_PAGAMENTI", Boolean.valueOf(nodoPagamenti));
	List<Concessioniuso> concessioniUsos = concessioniusoService.findAll(null, null);
	model.addAttribute("concessioniUsos", concessioniUsos);
	Integer codiceConcessioniUso = null;
	if (mpt.getConcessioniuso() != null && mpt.getConcessioniuso().getId() != null && mpt.getConcessioniuso().getId().getCodice() != null) {
	    codiceConcessioniUso = mpt.getConcessioniuso().getId().getCodice();
	} else {
	    if (mpt.getMercatoUso() != null && mpt.getMercatoUso().getConcessioniuso() != null
		    && mpt.getMercatoUso().getConcessioniuso().getId() != null
		    && mpt.getMercatoUso().getConcessioniuso().getId().getCodice() != null) {
		codiceConcessioniUso = mpt.getMercatoUso().getConcessioniuso().getId().getCodice();
	    }
	}
	model.addAttribute("concessioniUsoSelezionato", codiceConcessioniUso);
    }

    @RequestMapping
    public void ajaxApriAppello(@RequestParam(value = "idGiornata", required = true) Integer idGiornata, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	boolean giornataMercatoChiusa = mercatipresenzeTService.isGiornataMercatoChiusa(idGiornata);
	if (giornataMercatoChiusa) {
	    throw new InvalidConfigurationException("Non è possibile eseguire l'operazione se la giornata è chiusa");
	}
	MercatipresenzeT mpt = mercatipresenzeTService.findById(new PkId(idGiornata));
	String descrizioneGiorno = String.valueOf(idGiornata);
	if (mpt != null) {
	    descrizioneGiorno = mpt.getMercato().getDescrizione() + "(" + mpt.getMercato().getId() + ") - " + mpt.getMercatoUso().getDescrizione() +
				" - " + Utilities.formatDate(mpt.getDataRegistrazione(), false);
	}
	try {
	    mercatiAppService.riapriAppello(idGiornata);
	    LoggerUpdaterecord.log("Appello riaperto per la giornata " + descrizioneGiorno, getCurrentlyAuthenticatedUserDetails());
	} catch (MercatiAppException e) {
	    throw new InvalidConfigurationException(e.getErrore().getDescrizione());
	}
	response.getOutputStream().write("Appello riaperto correttamente".getBytes());
    }

    @RequestMapping
    public void ajaxChiudiAppello(@RequestParam(value = "idGiornata", required = true) Integer idGiornata, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	boolean giornataMercatoChiusa = mercatipresenzeTService.isGiornataMercatoChiusa(idGiornata);
	if (giornataMercatoChiusa) {
	    throw new InvalidConfigurationException("Non è possibile eseguire l'operazione se la giornata è chiusa");
	}
	MercatipresenzeT mpt = mercatipresenzeTService.findById(new PkId(idGiornata));
	String descrizioneGiorno = String.valueOf(idGiornata);
	if (mpt != null) {
	    descrizioneGiorno = mpt.getMercato().getDescrizione() + "(" + mpt.getMercato().getId() + ") - " + mpt.getMercatoUso().getDescrizione() +
				" - " + Utilities.formatDate(mpt.getDataRegistrazione(), false);
	}
	try {
	    mercatiAppService.terminaAppello(idGiornata);
	    LoggerUpdaterecord.log("Appello riaperto per la giornata " + descrizioneGiorno, getCurrentlyAuthenticatedUserDetails());
	} catch (MercatiAppException e) {
	    throw new InvalidConfigurationException(e.getErrore().getDescrizione());
	}
	response.getOutputStream().write("Appello terminato correttamente".getBytes());
    }

    /**
     * Crea il pannello per la scelta delle opzioni per l'export dei posteggi tramite il componente esterno Pentaho
     * 
     * @param model
     * @param contestoExport
     *            parametro che indica di tipo di contesto, se non passato di default prede il valore "ATT"
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String createExportModalitaPentaho(Model model, @RequestParam("giornoMercato") Integer giornoMercato,
	    @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "_comune") String comune, HttpServletRequest request, HttpServletResponse response) {

	EsportazioniPentahoCommand esportazioniPentahoCommand = new EsportazioniPentahoCommand();
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	esportazioniPentahoCommand.setResponsabili(responsabile);
	List<Esportazioni> listaEsportazioni = null;
	Esportazioni esportazioni = null;
	if (StringUtils.isBlank(codiceEsportazione)) {
	    listaEsportazioni = esportazioniService.findEsportazioni(TipicontestoesportazioniEnum.GIORNATA_MERCATO);
	    if (!listaEsportazioni.isEmpty()) {
		esportazioni = listaEsportazioni.get(0);
	    }
	} else {
	    List<PkId> ids = new ArrayList<PkId>();
	    String[] codici = codiceEsportazione.split("---");
	    String idComuneEsportazione = codici[1];
	    Integer codiceEsportazioneInt = Integer.parseInt(codici[0]);
	    PkId id = new PkId(idComuneEsportazione, codiceEsportazioneInt);
	    ids.add(id);
	    listaEsportazioni = esportazioniService.findEsportazioniEscludiRecord(TipicontestoesportazioniEnum.GIORNATA_MERCATO, ids);
	    esportazioni = esportazioniService.findById(id);
	    listaEsportazioni.add(0, esportazioni);
	}
	esportazioniPentahoCommand.setEsportazioni(esportazioni);
	model.addAttribute("listaEsportazioni", listaEsportazioni);
	model.addAttribute("esportazioniPentahoCommand", esportazioniPentahoCommand);
	model.addAttribute("codiceEsportazione", codiceEsportazione);
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(), comune,
		false);
	model.addAttribute("listMailConfig", listMailConfig);
	return "gestionepresenze/exportPentaho";
    }

    @RequestMapping
    public void ajaxExportModalitaPentaho(@RequestParam("giornoMercato") Integer giornoMercato,
	    @RequestParam(required = false, value = "email") String emailResponsabile,
	    @RequestParam(required = false, value = "isInviaMail") Boolean isInviaMail,
	    @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "idAccount") String idAccount, Model model,
	    @ModelAttribute("esportazioniPentahoCommand") EsportazioniPentahoCommand esportazioniPentahoCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException {

	log.debug("exportModalitaPentaho# Esportazione tramite funzionalità Penthao....");
	log.debug("exportModalitaPentaho# Ricerca i posteggi e li salva sulla tabella TMP_ESPORTAZIONI ");
	String sessionId = mercatiAppService.exportModalitaPentaho(giornoMercato, esportazioniPentahoCommand.getEsportazioni(),
		StringUtils.defaultIfEmpty(emailResponsabile, ""), BooleanUtils.toBoolean(isInviaMail));
	log.debug("exportModalitaPentaho# Invoco il link che attiva il job di pentaho");
	try {
	    Set<Parametriesportazione> parametriesportazione = esportazioniPentahoCommand.getEsportazioni().getParametriesportaziones();
	    Esportazioni e = esportazioniService.findById(new PkId(esportazioniPentahoCommand.getEsportazioni().getId().getCodice()));
	    String pathFile = pentahoService.callTrasformazione(e.getTrasformazione(), parametriesportazione, sessionId, response);
	    if (BooleanUtils.isTrue(isInviaMail) && StringUtils.isNotBlank(emailResponsabile)) {
		Pentahocfg pentahocfg = pentahocfgService.findById(ORMHelper.getIdcomune());
		if (EntityUtils.getNestedProperty(pentahocfg.getMailtipo(), "id.codice") != null) {
		    Mailtipo mailtipo = mailtipoService.findById(new PkId(pentahocfg.getMailtipo().getId().getCodice()));
		    MailMessageType mailMessage = mailtipoService.populateMailMessageForExport(emailResponsabile, pathFile, mailtipo);
		    // Non è previsto codice comune per i posteggi
		    String codiceComune = null;
		    Integer idAccountInt = null;
		    if (StringUtils.isNotBlank(idAccount)) {
			idAccountInt = Integer.parseInt(idAccount);
		    }
		    mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), idAccountInt, codiceComune, ORMHelper.getToken(), mailMessage);
		} else {
		    log.error("exportModalitaPentaho# Mail/Tipo  non configurato ");
		    FlashMessages.getWarnings()
			    .add("Attenzione, non è stato possibile inviare l'email. Mail tipo non presente nella configurazione di Penthao");
		}
	    }
	} catch (Exception e) {
	    log.error("Errore durante la chiamata alla trasformazione " + esportazioniPentahoCommand.getEsportazioni().getDescrizione() + ". Err: ",
		    e);
	    response.getOutputStream().write(("Errore nella generazione del report " + e.getMessage() + "").getBytes());
	}
    }

    @RequestMapping
    public String ajaxRiepilogoPagamentiNodo(@RequestParam("idPresenza") Integer idPresenza, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	StatoPagamentoNodoHelper hlp = nodoPagamentiService.getStatoPagamentoSpuntistaHelper(idPresenza);
	model.addAttribute("helper", hlp);
	MercatipresenzeD mpd = mercatipresenzeDService.findById(new PkId(idPresenza));
	model.addAttribute("mercpresd", mpd);
	return "gestionepresenze/ajaxRiepilogoPagamentiNodo";
    }

    @RequestMapping
    public String sistemaPosizioniDebitorieConcessionariDelGiorno(Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_POSIZIONI_DEBITORIE.name());
	return "gestionepresenze/sistemaPosizioniDebitorieConcessionariDelGiorno";
    }

    @RequestMapping
    public void ajaxSistemaPosizioniDebitorieConcessionariDelGiorno(@RequestParam("data") Date data, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_POSIZIONI_DEBITORIE.name());
	String id = String.valueOf(System.currentTimeMillis()) + "_" + Utilities.formatDate(data, false);
	List<Integer> mpds = mercatipresenzeDService.findPresenzeConcessionariSenzaPosizioniDebitorie(data);
	LoggerUpdaterecord.log("######################################################################");
	LoggerUpdaterecord.log(id + "==>SistemaPosizioniDebitorieConcessionariDelGiorno START AT: " + Utilities.formatDate(new Date(), true));
	log.debug("Cerco le presenze senza posizioni debitorie {}", mpds.size());
	LoggerUpdaterecord.log(id + "==>SistemaPosizioniDebitorieConcessionariDelGiorno " + mpds.size());
	StringBuilder sb = new StringBuilder();
	int posCreate = 0;
	int recordProcessati = 0;
	for (Integer mpd : mpds) {
	    recordProcessati++;
	    MercatipresenzeD mercatipresenzeD = mercatipresenzeDService.findById(new PkId(mpd));
	    log.debug("processo la presenza {}", mercatipresenzeD.getId().getCodice());
	    if (mercatipresenzeD.getDettPosizioneDebitoria() == null && mercatipresenzeD.getNumeropresenze() == 1) {
		// inserisci posizione debitoria sfruttando l'evento del concessionario presente
		try {
		    EventoConcessionarioSegnatoPresente evt = new EventoConcessionarioSegnatoPresente(mercatipresenzeD.getId().getCodice(), data);
		    eventPublisher.publish(evt);
		} catch (Exception e) {
		    log.error("{}", e);
		}
		mercatipresenzeD = mercatipresenzeDService.findById(new PkId(mercatipresenzeD.getId().getCodice()));
		if (mercatipresenzeD.getDettPosizioneDebitoria() != null) {
		    posCreate++;
		}
	    }
	    log.debug("record processati {} posizioni create {}", recordProcessati, posCreate);
	}
	sb.append("Processate ")// 
		.append(recordProcessati) //
		.append(" presenze")//
		.append(" create ")//
		.append(posCreate)//
		.append(" posizioni debitorie");
	LoggerUpdaterecord.log(id + "==>SistemaPosizioniDebitorieConcessionariDelGiorno report: " + sb.toString());
	LoggerUpdaterecord.log(id + "==>SistemaPosizioniDebitorieConcessionariDelGiorno START AT: " + Utilities.formatDate(new Date(), true));
	response.getOutputStream().write(sb.toString().getBytes());
    }

    @RequestMapping
    public String sistemaPosizioniDebitorie(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_POSIZIONI_DEBITORIE.name());
	return "gestionepresenze/sistemaPosizioniDebitorie";
    }

    @RequestMapping
    public void ajaxSistemaPosizioniDebitorieAvanzamento(HttpServletRequest request, HttpServletResponse response) throws IOException {

	ReportRecordElaboratiBean cdb = sistemaPagamentiAvanzamento.get(ORMHelper.getIdcomuneAlias());
	if (cdb == null) {
	    cdb = new ReportRecordElaboratiBean(0, 0);
	}
	response.setContentType("application/json");
	response.getOutputStream().write(("{\"totale\":" + cdb.getTotaleRecord() + ",\"elaborati\": " + cdb.getRecordElaborati() + ",\"errori\":" +
					  cdb.getErrori().size() + "}").getBytes());
    }

    @RequestMapping
    public void ajaxFermaSistemaPosizioniDebitorieAvanzamento(HttpServletRequest request, HttpServletResponse response) throws IOException {

	fermaProcedura = !fermaProcedura;
	response.setContentType("application/json");
	response.getOutputStream().write(("{\"fermaProceduraPosizioniDebitorieAvanzamento\":" + fermaProcedura + "}").getBytes());
    }

    @RequestMapping
    public void ajaxResetSistemaPosizioniDebitorieAvanzamento(HttpServletRequest request, HttpServletResponse response) throws IOException {

	sistemaPagamentiAvanzamento.put(ORMHelper.getIdcomuneAlias(), new ReportRecordElaboratiBean(0, 0));
    }

    public Map<String, ReportRecordElaboratiBean> sistemaPagamentiAvanzamento = new HashMap<String, ReportRecordElaboratiBean>();
    public boolean fermaProcedura = false;

    @RequestMapping
    public void ajaxVerificaESistemaPagamenti(@RequestParam("dalladata") Date dalladata, //
	    @RequestParam("alladata") Date alladata, //
	    @RequestParam("tipo") String tipologia, //
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	//1. Verifica dei permessi dell'utente
	userHasRole(true, RuoliUtentiEnum.GESTIONE_POSIZIONI_DEBITORIE.name());
	//2. Generazione id per log delle modifiche
	String id = String.valueOf(System.currentTimeMillis()) + "_" + Utilities.formatDate(Calendar.getInstance().getTime(), false);
	//3. Verifico la presenza dei battitori ed eventualmente se il tutto è configurato correttamente
	String codiceIstatBattitori[] = null;
	if (tipologia.equalsIgnoreCase("battitori")) {
	    codiceIstatBattitori = StringUtils.defaultString(this.vertComportamentiMercatiService.codiceIstatBattitori(), "").trim().split(",");
	    if (codiceIstatBattitori == null || codiceIstatBattitori.length == 0) {
		throw new InvalidConfigurationException("Non è stato configurato il parametro per identificare i battitori");
	    }
	}
	//4. Recupero le presenze non pagate
	List<PresenzeNonPagateBean> mpds = mercatipresenzeDService.findPresenzeNonAssociateAMetodoDiPagamento(dalladata, alladata,
		codiceIstatBattitori);
	LoggerUpdaterecord.log("######################################################################");
	LoggerUpdaterecord.log(id + "==>ajaxVerificaESistemaPagamenti START AT: " + Utilities.formatDate(new Date(), true));
	log.debug("Totale presenze senza un metodo di pagamento associato: {}", mpds.size());
	LoggerUpdaterecord.log(id + "==>ajaxVerificaESistemaPagamenti " + mpds.size());
	StringBuilder sb = new StringBuilder();
	int posCreate = 0;
	int recordProcessati = 0;
	tipologia = StringUtils.defaultString(tipologia.toLowerCase(), "battitori");
	sistemaPagamentiAvanzamento.put(ORMHelper.getIdcomuneAlias(), new ReportRecordElaboratiBean(mpds.size(), 0));
	for (PresenzeNonPagateBean mpd : mpds) {
	    if (fermaProcedura) {
		break;
	    }
	    recordProcessati++;
	    log.debug("processo la presenza {}", mpd.getIdpresenza());
	    boolean creaPagamento = true;
	    if (!tipologia.equalsIgnoreCase("tutti")) {
		creaPagamento = false;
		if (tipologia.equalsIgnoreCase("concessionari")) {
		    creaPagamento = !mpd.isSpuntista();
		} else {
		    if (mpd.isSpuntista()) {
			creaPagamento = true;
		    }
		}
	    }
	    ReportRecordElaboratiBean cdb = sistemaPagamentiAvanzamento.get(ORMHelper.getIdcomuneAlias());
	    if (!creaPagamento) {
		cdb.setRecordElaborati(recordProcessati);
		log.debug("record processati {} posizioni create {}", recordProcessati, posCreate);
		continue;
	    }
	    try {
		MercatipresenzeD mercatipresenzeD = mercatipresenzeDService.findById(new PkId(mpd.getIdpresenza()));
		this.pagamentiService.generaPagamentoPresenza(mercatipresenzeD);
		posCreate++;
		cdb.setRecordElaborati(recordProcessati);
	    } catch (Exception e) {
		String errore = "ERRORE nella creazione di un pagamento " + mpd.toString() + ": " + e.getMessage();
		cdb.getErrori().add(errore);
		log.error("ajaxVerificaESistemaPagamenti: {}", errore, e);
		LoggerUpdaterecord.log(id + "==>ajaxVerificaESistemaPagamenti: " + errore);
	    }
	    log.debug("record processati {} pagamenti creati {}", recordProcessati, posCreate);
	}
	sb.append("Processate ")// 
		.append(recordProcessati) //
		.append(" presenze")//
		.append(" creati ")//
		.append(posCreate)//
		.append(" pagamenti");
	ReportRecordElaboratiBean cdb = sistemaPagamentiAvanzamento.get(ORMHelper.getIdcomuneAlias());
	if (cdb.getErrori().size() > 0) {
	    sb.append("<br/>Errori: <ul>");//
	    for (String errori : cdb.getErrori()) {
		sb.append("<li>").append(errori).append("</li>"); //
	    }
	    sb.append("</ul>");//
	}
	LoggerUpdaterecord.log(id + "==>ajaxVerificaESistemaPagamenti report: " + sb.toString());
	LoggerUpdaterecord.log(id + "==>ajaxVerificaESistemaPagamenti START AT: " + Utilities.formatDate(new Date(), true));
	if (this.fermaProcedura) {
	    this.fermaProcedura = false;
	}
	response.getOutputStream().write(sb.toString().getBytes());
    }

    @RequestMapping
    public void ajaxReportSistemaPosizioniDebitorieAvanzamento(HttpServletRequest request, HttpServletResponse response) throws IOException {

	ReportRecordElaboratiBean cdb = sistemaPagamentiAvanzamento.get(ORMHelper.getIdcomuneAlias());
	StringBuilder sb = new StringBuilder();
	sb.append("Totale record ")// 
		.append(cdb.getTotaleRecord()) //
		.append(" elaborati ")//
		.append(cdb.getRecordElaborati())//
		.append(" posizioni debitorie");
	if (cdb.getErrori().size() > 0) {
	    sb.append("\nErrori: ");//
	    for (String errori : cdb.getErrori()) {
		sb.append("\n\t").append(errori).append("\n=================================\n\n"); //
	    }
	}
	response.setContentType("text/html");
	response.getOutputStream().write(sb.toString().getBytes());
    }

    @RequestMapping
    public void ajaxCancellaAnnullamentoGiornata(HttpServletRequest request, HttpServletResponse response) throws IOException {

	Integer idGiornata = Integer.parseInt(request.getParameter("id-giornata"));
	mercatipresenzeTService.segnaGiornataNonNulla(idGiornata);
	response.setStatus(200);
    }

    boolean checkPosteggioBattitori(String istatBattitori, PresenzeNonPagateBean mpd) {

	if (StringUtils.isBlank(istatBattitori)) {
	    return false;
	}
	List<MercatiDattivitaistat> attivitaPosteggio = mercatiDattivitaistatService.findAttivitaPosteggio(mpd.getIdposteggio());
	for (MercatiDattivitaistat mda : attivitaPosteggio) {
	    if (istatBattitori.indexOf(mda.getId().getFkcodiceattivitaistat()) >= 0) {
		return true;
	    }
	}
	return false;
    }
    /*
    @RequestMapping
    public void ajaxAnnullaPosizioneDebitoriaSpuntista(@RequestParam("idPresenza") Integer idPresenza, Model model, HttpServletRequest request,
        HttpServletResponse response) throws IOException {
    
    try {
        nodoPagamentiService.annullaPosizioneDebitoriaSpuntista(idPresenza);
        response.getOutputStream().write("OK".getBytes());
    } catch (FunzioneBusinessRemotaException e) {
        response.getOutputStream().write(e.getMessage().getBytes());
    }
    }
    
    @RequestMapping
    public void ajaxSegnaPosizioneDebitoriaPagatoOffline(@RequestParam("idPosizioneDebitoria") Integer idPosizioneDebitoria,
        @RequestParam("riferimentiPagamento") String riferimentiPagamento, Model model, HttpServletRequest request, HttpServletResponse response)
        throws IOException {
    
    try {
        if (StringUtils.isBlank(riferimentiPagamento)) {
    	riferimentiPagamento = "Pagato offline con altre forme di pagamento";
        }
        nodoPagamentiService.updatePosizioneDebitoriaSegnaPagata(idPosizioneDebitoria, riferimentiPagamento);
        response.getOutputStream().write("OK".getBytes());
    } catch (FunzioneBusinessRemotaException e) {
        response.getOutputStream().write(e.getMessage().getBytes());
    }
    }
    
    @RequestMapping
    public void ajaxAnnullaPosizioneDebitoria(@RequestParam("idPosizioneDebitoria") Integer idPosizioneDebitoria, Model model,
        HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    try {
        nodoPagamentiService.annullaPosizioneDebitoria(idPosizioneDebitoria);
        response.getOutputStream().write("OK".getBytes());
    } catch (FunzioneBusinessRemotaException e) {
        response.getOutputStream().write(e.getMessage().getBytes());
    }
    }
    */
}
