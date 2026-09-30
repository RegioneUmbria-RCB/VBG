package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.backoffice.web.rest.TempiRispostaJson;
import it.gruppoinit.pal.gp.backoffice.web.rest.TempirispostaContainerJson;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ContestiMailTipoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedett;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipopareri;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.FoSoggettiesterni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Onericomportamento;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloRegistri;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.Tempirisposta;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovTipiSoggetto;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentidyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentidyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentodoctipo;
import it.gruppoinit.pal.gp.core.domain.TipimovimentodoctipoId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentooneri;
import it.gruppoinit.pal.gp.core.domain.TipimovimentooneriId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedureavvio;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.helper.TempirispostaHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.TempirispostaCommand;
import it.gruppoinit.pal.gp.core.domain.web.TipimovimentoCommand;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo.FasiDiEsecuzioneEnum;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo.TipimovimentodoctipoService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.OneritipirateizzazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.rabbitmq.IVerticalizzazioneRabbitMQService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipologiedettService;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipopareriService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.FoSoggettiesterniService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentisoftwareService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.OnericomportamentoService;
import it.gruppoinit.pal.gp.core.service.ProtocolloRegistriService;
import it.gruppoinit.pal.gp.core.service.ResponsabilisoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.TempirispostaService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipicontromovimentoService;
import it.gruppoinit.pal.gp.core.service.Tipimovimentidyn2modellitService;
import it.gruppoinit.pal.gp.core.service.TipimovimentooneriService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureavvioService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.TempirispostaHelperBean;
import it.gruppoinit.pal.gp.core.service.helper.TempirispostaValoriHelper;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes(value = { "tipimovimento", "tipimovimentodoctipo", "tipimovimentooneri", "contromovimento", "tempirispostaCommand",
	"tipimovimentidyn2modellit" })
public class TipimovimentoController extends BaseController<Tipimovimento> {

    @Autowired
    private TipiMovimentoService tipimovimentoService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private TipologiaregistriService tipologiaregistriService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private FoSoggettiesterniService foSoggettiesterniService;
    @Autowired
    private TipimovimentodoctipoService tipimovimentodoctipoService;
    @Autowired
    private LetteretipoService letteretipoService;
    @Autowired
    private TipicausalioneriService tipicausalioneriService;
    @Autowired
    private TipimovimentooneriService tipimovimentooneriService;
    @Autowired
    private OnericomportamentoService onericomportamentoService;
    @Autowired
    private TipicontromovimentoService tipicontromovimentoService;
    @Autowired
    private TipiprocedureService tipiprocedureService;
    @Autowired
    private TempirispostaService tempirispostaService;
    @Autowired
    private Tipimovimentidyn2modellitService tipimovimentidyn2modellitService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private StatiistanzaService statiistanzaService;
    @Autowired
    private CommedilizieTipologiedettService commedilizieTipologiedettService;
    @Autowired
    private CommedilizieTipopareriService commedilizieTipopareriService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private InventarioprocedimentisoftwareService inventarioprocedimentisoftwareService;
    @Autowired
    private OneritipirateizzazioneService oneritipirateizzazioneService;
    @Autowired
    private ProtocolloRegistriService protocolloRegistriService;
    @Autowired
    private TipiprocedureavvioService tipiprocedureavvioService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private ResponsabilisoftwareService responsabilisoftwareService;
    @Autowired
    private IVerticalizzazioneRabbitMQService verticalizzazioneRabbitMQService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipimovimento> tipimovimentoList = tipimovimentoService.findAll(null, null);
	ModelMap model = new ModelMap(tipimovimentoList);
	boolean export = createJMesaExport(request, response, tipimovimentoList);
	if (export) {
	    return null;
	}
	model.addAttribute("tipimovimentoList", tipimovimentoList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	TipimovimentoCommand tipimovimento = new TipimovimentoCommand();
	Tipimovimento entity = new Tipimovimento();
	// controllo se esistono amministrazioni interne configurate per il comune in esame
	Boolean isAmministrazioniInterneEsistono = amministrazioniService.isAmministrazioneInternaEsiste();
	// verifico se le verticalizzazioni sono attive
	boolean isVerticalizzazioneSTCAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	boolean isVerticalizzazioneINFOCAMERAAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_INFOCAMERA);
	boolean isVerticalizzazionePROTOCOLLOAttiva = verticalizzazioniService
		.isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE);
	boolean isVerticalizzazioneAUTORIZACCESSIAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_AUTORIZ_ACCESSI);
	// recupero gli eventuali soggetti che possono effettuare il movimento in forntoffice
	List<FoSoggettiesterni> listaSoggettiesterni = foSoggettiesterniService.findAll(null, null);
	// recupero la lista della tipologia dei registri
	List<Tipologiaregistri> listaTipologiaregistri = tipologiaregistriService.findAll(null, null);
	List<Mailtipo> listaMailtipo = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.MAIL);
	// Filtra per ambito "P" sta per protocollo, senon attivo inutile fare la query, non deve comparire
	List<Mailtipo> listaMailtipoOggProt = new ArrayList<Mailtipo>();
	if (isVerticalizzazionePROTOCOLLOAttiva) {
	    listaMailtipoOggProt = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.PARERI);
	}
	// controllo se è attivo il software PR
	Boolean isSoftwarePRAttivo = softwareService.isSoftwareAttivo("PR");
	entity.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(entity);
	tipimovimento.setEntity(entity);
	tipimovimento.setDisplayMode(TipimovimentoCommand.NEW);
	model.addAttribute("tipimovimento", tipimovimento);
	model.addAttribute("listaMailtipo", listaMailtipo);
	model.addAttribute("listaMailtipoOggProt", listaMailtipoOggProt);
	model.addAttribute("software", tipimovimento.getEntity().getSoftware().getCodice());
	model.addAttribute("isAmministrazioniInterneEsistono", isAmministrazioniInterneEsistono);
	model.addAttribute("isVerticalizzazioneSTCAttiva", isVerticalizzazioneSTCAttiva);
	model.addAttribute("isVerticalizzazioneINFOCAMERAAttiva", isVerticalizzazioneINFOCAMERAAttiva);
	model.addAttribute("isVerticalizzazionePROTOCOLLOAttiva", isVerticalizzazionePROTOCOLLOAttiva);
	model.addAttribute("isVerticalizzazioneAUTORIZACCESSIAttiva", isVerticalizzazioneAUTORIZACCESSIAttiva);
	model.addAttribute("soggettiesterniList", listaSoggettiesterni);
	model.addAttribute("tipologiaregistriList", listaTipologiaregistri);
	model.addAttribute("isSoftwarePRAttivo", isSoftwarePRAttivo);
	setPageAttributes(model);
	return "tipimovimento/form";
    }

    @RequestMapping
    public String createChangeTipoContromovimento(@RequestParam("codiceControMov") Integer codice, Model model, HttpServletRequest request) {

	Tipicontromovimento tipicontromovimento = tipicontromovimentoService.findById(new PkId(codice));
	model.addAttribute("contromovimento", tipicontromovimento);
	return "tipimovimento/changeTipoContromovimento";
    }

    @RequestMapping
    public String updateTipoContromovimento(Model model, @ModelAttribute("contromovimento") Tipicontromovimento contromovimento, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeTipicontromovimetoProperty(contromovimento);
	try {
	    tipicontromovimentoService.aggiornaTipocontromovimento(contromovimento);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, contromovimento, e);
	    contromovimento.setTipocontromovimento(new Tipimovimento());
	    model.addAttribute("contromovimento", contromovimento);
	    return "tipimovimento/changeTipoContromovimento";
	}
	return "redirect:viewContromovimento.htm?codicecontromovimento=" + contromovimento.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("tipimovimento") TipimovimentoCommand tipimovimento, BindingResult result,
	    SessionStatus status) {

	Tipimovimento entity = tipimovimento.getEntity();
	fixMergeEntityProperty(entity);
	entity.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	// Se presente recupero l'oggetto tipologia registro
	if (entity.getTipologiaregistri() != null && entity.getTipologiaregistri().getId().getCodice() != null) {
	    Tipologiaregistri tipologiaregistri = tipologiaregistriService.findById(new PkId(entity.getTipologiaregistri().getId().getCodice()));
	    entity.setTipologiaregistri(tipologiaregistri);
	}
	// Se presente recupero l'oggetto soggetto esterno
	if (entity.getFoSoggettiesterni() != null && entity.getFoSoggettiesterni().getCodice() != null) {
	    FoSoggettiesterni foSoggettiesterni = foSoggettiesterniService.findById(entity.getFoSoggettiesterni().getCodice());
	    entity.setFoSoggettiesterni(foSoggettiesterni);
	}
	try {
	    tipimovimentoService.insert(entity);
	} catch (Exception e) {
	    // campi da recuperare per tornare correttamente al form di inserimenti in caso di errore in validazio
	    // controllo se esistono amministrazioni interne configurate per il comune in esame
	    Boolean isAmministrazioniInterneEsistono = amministrazioniService.isAmministrazioneInternaEsiste();
	    // verifico se le verticalizzazioni sono attive
	    boolean isVerticalizzazioneSTCAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	    boolean isVerticalizzazioneINFOCAMERAAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_INFOCAMERA);
	    boolean isVerticalizzazionePROTOCOLLOAttiva = verticalizzazioniService
		    .isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE);
	    // recupero gli eventuali soggetti che possono effettuare il movimento in forntoffice
	    List<FoSoggettiesterni> listaSoggettiesterni = foSoggettiesterniService.findAll(null, null);
	    List<Mailtipo> listaMailtipo = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.MAIL);
	    // Filtra per ambito "P" sta per protocollo, senon attivo inutile fare la query, non deve comparire
	    List<Mailtipo> listaMailtipoOggProt = new ArrayList<Mailtipo>();
	    if (isVerticalizzazionePROTOCOLLOAttiva) {
		listaMailtipoOggProt = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.PARERI);
	    }
	    // recupero la lista della tipologia dei registri
	    List<Tipologiaregistri> listaTipologiaregistri = tipologiaregistriService.findAll(null, null);
	    // controllo se è attivo il software PR
	    Boolean isSoftwarePRAttivo = softwareService.isSoftwareAttivo("PR");
	    copyErrorsToBindingResult(result, tipimovimento.getEntity(), true, e);
	    String codice = entity.getId().getTipomovimento();
	    if (StringUtils.isNotBlank(codice)) {
		codice = codice.replaceFirst(ORMHelper.getSoftware(), "");
	    }
	    entity.getId().setTipomovimento(codice);
	    fixRenderEntityProperty(entity);
	    tipimovimento.setEntity(entity);
	    tipimovimento.setDisplayMode(TipimovimentoCommand.NEW);
	    model.addAttribute("software", tipimovimento.getEntity().getSoftware().getCodice());
	    model.addAttribute("listaMailtipo", listaMailtipo);
	    model.addAttribute("listaMailtipoOggProt", listaMailtipoOggProt);
	    model.addAttribute("isAmministrazioniInterneEsistono", isAmministrazioniInterneEsistono);
	    model.addAttribute("isVerticalizzazioneSTCAttiva", isVerticalizzazioneSTCAttiva);
	    model.addAttribute("isVerticalizzazioneINFOCAMERAAttiva", isVerticalizzazioneINFOCAMERAAttiva);
	    model.addAttribute("isVerticalizzazionePROTOCOLLOAttiva", isVerticalizzazionePROTOCOLLOAttiva);
	    model.addAttribute("soggettiesterniList", listaSoggettiesterni);
	    model.addAttribute("tipologiaregistriList", listaTipologiaregistri);
	    model.addAttribute("isSoftwarePRAttivo", isSoftwarePRAttivo);
	    setPageAttributes(model);
	    return "tipimovimento/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getTipomovimento() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") String codice, Model model, HttpServletRequest request) {

	// Recupero i software attivi per l'operatore loggato. Sono necessari in caso di softare TT, per popolare 
	// il pannello che permette di scegliere il software.
	List<Software> softwareList = new ArrayList<Software>();
	Responsabili currentUser = (Responsabili) this.userSecurityService.getCurrentlyAuthenticatedUserDetails();
	if (ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
	    List<Responsabilisoftware> responsabilisoftwares = responsabilisoftwareService.findBySoftware(currentUser, new Software());
	    for (Responsabilisoftware responsabilisoftware : responsabilisoftwares) {
		softwareList.add(responsabilisoftware.getSoftware());
	    }
	} else {
	    Software software = softwareService.findById(ORMHelper.getSoftware());
	    softwareList.add(software);
	}
	model.addAttribute("softwareList", softwareList);
	prepareViewPage(model, codice, request);
	setPageAttributes(model);
	return "tipimovimento/form";
    }

    private void prepareViewPage(Model model, String codice, HttpServletRequest request) {

	TipimovimentoId id = new TipimovimentoId();
	id.setTipomovimento(codice);
	Tipimovimento entity = tipimovimentoService.findById(id);
	TipimovimentoCommand tipimovimento = new TipimovimentoCommand();
	tipimovimento.setEntity(entity);
	tipimovimento.setDisplayMode(TipimovimentoCommand.VIEW);
	// controllo se esistono amministrazioni interne configurate per il comune in esame
	Boolean isAmministrazioniInterneEsistono = amministrazioniService.isAmministrazioneInternaEsiste();
	// verifico se le verticalizzazioni sono attive
	boolean isVerticalizzazioneSTCAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	boolean isVerticalizzazioneINFOCAMERAAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_INFOCAMERA);
	boolean isVerticalizzazionePROTOCOLLOAttiva = verticalizzazioniService
		.isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE);
	boolean isVerticalizzazioneAUTORIZACCESSIAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_AUTORIZ_ACCESSI);
	boolean isVerticalizzazioneRABBITAttiva = this.verticalizzazioneRabbitMQService.isAttiva();
	// recupero gli eventuali soggetti che possono effettuare il movimento in forntoffice
	List<FoSoggettiesterni> listaSoggettiesterni = foSoggettiesterniService.findAll(null, null);
	// recupero la lista della tipologia dei registri
	List<Tipologiaregistri> listaTipologiaregistri = tipologiaregistriService.findAll(null, null);
	List<Mailtipo> listaMailtipo = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.MAIL);
	// Filtra per ambito "P" sta per protocollo, senon attivo inutile fare la query, non deve comparire
	List<Mailtipo> listaMailtipoOggProt = new ArrayList<Mailtipo>();
	if (isVerticalizzazionePROTOCOLLOAttiva) {
	    listaMailtipoOggProt = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.PARERI);
	}
	// controllo se è attivo il software PR
	Boolean isSoftwarePRAttivo = softwareService.isSoftwareAttivo("PR");
	//Recupero i tipicontromovimenti del tipomovimento passato ordinandoli per descrizione(movimento)
	List<Tipicontromovimento> listaTipiContromovimenti = tipicontromovimentoService.findByTipimovimento(entity);
	//Recupero i tipimovimenti per cui il tipomovimento passato è contromovimento oridinadoli per descrizione(movimento)
	List<Tipicontromovimento> listaTipiContromovimenti1 = tipicontromovimentoService.findByContromovimento(entity);
	fixRenderEntityProperty(tipimovimento.getEntity());
	// Recupero i soggetti che possono effettuare il movimento
	for (TipimovTipiSoggetto sogg : entity.getTipimovTipisoggetto()) {
	    int idSogg = sogg.getId().getCodice();
	    String descrizione = sogg.getTipisoggetto().getTiposoggetto();
	    tipimovimento.aggiungiSoggettoCheEffettuaIlMovimento(idSogg, descrizione);
	}
	request.setAttribute("codicemovimento", tipimovimento.getEntity().getId().getTipomovimento());
	model.addAttribute("software", tipimovimento.getEntity().getSoftware().getCodice());
	model.addAttribute("listaMailtipo", listaMailtipo);
	model.addAttribute("listaMailtipoOggProt", listaMailtipoOggProt);
	model.addAttribute("tipimovimento", tipimovimento);
	model.addAttribute("listaTipiContromovimenti", listaTipiContromovimenti);
	model.addAttribute("listaTipiContromovimenti1", listaTipiContromovimenti1);
	model.addAttribute("tipomovimentoinfo", tipimovimento.getEntity());
	model.addAttribute("isAmministrazioniInterneEsistono", isAmministrazioniInterneEsistono);
	model.addAttribute("isVerticalizzazioneSTCAttiva", isVerticalizzazioneSTCAttiva);
	model.addAttribute("isVerticalizzazioneINFOCAMERAAttiva", isVerticalizzazioneINFOCAMERAAttiva);
	model.addAttribute("isVerticalizzazionePROTOCOLLOAttiva", isVerticalizzazionePROTOCOLLOAttiva);
	model.addAttribute("isVerticalizzazioneAUTORIZACCESSIAttiva", isVerticalizzazioneAUTORIZACCESSIAttiva);
	model.addAttribute("isVerticalizzazioneRABBITAttiva", isVerticalizzazioneRABBITAttiva);
	model.addAttribute("soggettiesterniList", listaSoggettiesterni);
	model.addAttribute("tipologiaregistriList", listaTipologiaregistri);
	model.addAttribute("isSoftwarePRAttivo", isSoftwarePRAttivo);
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("tipimovimento") TipimovimentoCommand tipimovimento, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Tipimovimento entity = tipimovimento.getEntity();
	// Se presente recupero l'oggetto tipologia registro
	if (entity.getTipologiaregistri() != null && entity.getTipologiaregistri().getId().getCodice() != null) {
	    Tipologiaregistri tipologiaregistri = tipologiaregistriService.findById(new PkId(entity.getTipologiaregistri().getId().getCodice()));
	    entity.setTipologiaregistri(tipologiaregistri);
	}
	// Se presente recupero l'oggetto soggetto esterno
	if (entity.getFoSoggettiesterni() != null && entity.getFoSoggettiesterni().getCodice() != null) {
	    FoSoggettiesterni foSoggettiesterni = foSoggettiesterniService.findById(entity.getFoSoggettiesterni().getCodice());
	    entity.setFoSoggettiesterni(foSoggettiesterni);
	}
	// Se presente recupero la mailtipo per il dpr160 Altre comunicazioni
	if (EntityUtils.getNestedProperty(entity.getMailtipoByFkTipimovcomTelMailtipo(), "id.codice") != null) {
	    Mailtipo mail = mailtipoService.findById(entity.getMailtipoByFkTipimovcomTelMailtipo().getId());
	    entity.setMailtipoByFkTipimovcomTelMailtipo(mail);
	}
	// Se presente recupero la mailtipo per il dpr160 Ricevute telematiche
	if (EntityUtils.getNestedProperty(entity.getMailtipoByFkTipimovricTelMailtipo(), "id.codice") != null) {
	    Mailtipo mail = mailtipoService.findById(entity.getMailtipoByFkTipimovricTelMailtipo().getId());
	    entity.setMailtipoByFkTipimovricTelMailtipo(mail);
	}
	fixMergeEntityProperty(entity);
	try {
	    tipimovimentoService.update(entity);
	} catch (Exception e) {
	    // campi da recuperare per tornare correttamente al form di inserimento in caso di errore in validazione
	    // controllo se esistono amministrazioni interne configurate per il comune in esame
	    Boolean isAmministrazioniInterneEsistono = amministrazioniService.isAmministrazioneInternaEsiste();
	    // verifico se le verticalizzazioni sono attive
	    boolean isVerticalizzazioneSTCAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	    boolean isVerticalizzazioneINFOCAMERAAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_INFOCAMERA);
	    boolean isVerticalizzazionePROTOCOLLOAttiva = verticalizzazioniService
		    .isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE);
	    // recupero gli eventuali soggetti che possono effettuare il movimento in frontoffice
	    List<FoSoggettiesterni> listaSoggettiesterni = foSoggettiesterniService.findAll(null, null);
	    // recupero la lista della tipologia dei registri
	    List<Tipologiaregistri> listaTipologiaregistri = tipologiaregistriService.findAll(null, null);
	    List<Mailtipo> listaMailtipo = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.MAIL);
	    // Filtra per ambito "P" sta per protocollo, senon attivo inutile fare la query, non deve comparire
	    List<Mailtipo> listaMailtipoOggProt = new ArrayList<Mailtipo>();
	    if (isVerticalizzazionePROTOCOLLOAttiva) {
		listaMailtipoOggProt = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.PARERI);
	    }
	    // controllo se è attivo il software PR
	    Boolean isSoftwarePRAttivo = softwareService.isSoftwareAttivo("PR");
	    copyErrorsToBindingResult(result, tipimovimento.getEntity(), true, e);
	    fixRenderEntityProperty(entity);
	    tipimovimento.setEntity(entity);
	    tipimovimento.setDisplayMode(TipimovimentoCommand.VIEW);
	    model.addAttribute("software", tipimovimento.getEntity().getSoftware().getCodice());
	    model.addAttribute("listaMailtipo", listaMailtipo);
	    model.addAttribute("listaMailtipoOggProt", listaMailtipoOggProt);
	    model.addAttribute("isAmministrazioniInterneEsistono", isAmministrazioniInterneEsistono);
	    model.addAttribute("isVerticalizzazioneSTCAttiva", isVerticalizzazioneSTCAttiva);
	    model.addAttribute("isVerticalizzazioneINFOCAMERAAttiva", isVerticalizzazioneINFOCAMERAAttiva);
	    model.addAttribute("isVerticalizzazionePROTOCOLLOAttiva", isVerticalizzazionePROTOCOLLOAttiva);
	    model.addAttribute("soggettiesterniList", listaSoggettiesterni);
	    model.addAttribute("tipologiaregistriList", listaTipologiaregistri);
	    model.addAttribute("isSoftwarePRAttivo", isSoftwarePRAttivo);
	    setPageAttributes(model);
	    return "tipimovimento/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getTipomovimento() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("tipimovimento") TipimovimentoCommand tipimovimento, BindingResult result,
	    SessionStatus status) {

	Tipimovimento objToDelete = tipimovimentoService.findById(tipimovimento.getEntity().getId());
	try {
	    tipimovimentoService.delete(objToDelete);
	} catch (Exception e) {
	    // campi da recuperare per tornare correttamente al form di inserimenti in caso di errore in validazio
	    // controllo se esistono amministrazioni interne configurate per il comune in esame
	    Boolean isAmministrazioniInterneEsistono = amministrazioniService.isAmministrazioneInternaEsiste();
	    // verifico se le verticalizzazioni sono attive
	    boolean isVerticalizzazioneSTCAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	    boolean isVerticalizzazioneINFOCAMERAAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_INFOCAMERA);
	    boolean isVerticalizzazionePROTOCOLLOAttiva = verticalizzazioniService
		    .isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE);
	    // recupero gli eventuali soggetti che possono effettuare il movimento in forntoffice
	    List<FoSoggettiesterni> listaSoggettiesterni = foSoggettiesterniService.findAll(null, null);
	    // recupero la lista della tipologia dei registri
	    List<Tipologiaregistri> listaTipologiaregistri = tipologiaregistriService.findAll(null, null);
	    List<Mailtipo> listaMailtipo = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.MAIL);
	    // Filtra per ambito "P" sta per protocollo, senon attivo inutile fare la query, non deve comparire
	    List<Mailtipo> listaMailtipoOggProt = new ArrayList<Mailtipo>();
	    if (isVerticalizzazionePROTOCOLLOAttiva) {
		listaMailtipoOggProt = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.PARERI);
	    }
	    // controllo se è attivo il software PR
	    Boolean isSoftwarePRAttivo = softwareService.isSoftwareAttivo("PR");
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipimovimento.getEntity());
	    tipimovimento.setDisplayMode(TipimovimentoCommand.VIEW);
	    model.addAttribute("software", tipimovimento.getEntity().getSoftware().getCodice());
	    model.addAttribute("listaMailtipo", listaMailtipo);
	    model.addAttribute("listaMailtipoOggProt", listaMailtipoOggProt);
	    model.addAttribute("isAmministrazioniInterneEsistono", isAmministrazioniInterneEsistono);
	    model.addAttribute("isVerticalizzazioneSTCAttiva", isVerticalizzazioneSTCAttiva);
	    model.addAttribute("isVerticalizzazioneINFOCAMERAAttiva", isVerticalizzazioneINFOCAMERAAttiva);
	    model.addAttribute("isVerticalizzazionePROTOCOLLOAttiva", isVerticalizzazionePROTOCOLLOAttiva);
	    model.addAttribute("soggettiesterniList", listaSoggettiesterni);
	    model.addAttribute("tipologiaregistriList", listaTipologiaregistri);
	    model.addAttribute("isSoftwarePRAttivo", isSoftwarePRAttivo);
	    setPageAttributes(model);
	    return "tipimovimento/form";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    @RequestMapping
    public void deleteInternal(Model model, @RequestParam("codice") String tipimovimento) {

	TipimovimentoId id = new TipimovimentoId(tipimovimento);
	Tipimovimento objToDelete = tipimovimentoService.findById(id);
	tipimovimentoService.delete(objToDelete);
    }

    @RequestMapping
    public ModelMap listdocumentitipo(@RequestParam("tipimovimento.codice") String codicemovimento, HttpServletRequest request,
	    HttpServletResponse response) {

	Tipimovimento tipimovimento = tipimovimentoService.findById(new TipimovimentoId(ORMHelper.getIdcomune(), codicemovimento));
	Set<Tipimovimentodoctipo> tipimovimentodoctipoList = tipimovimento.getTipimovimentodoctipos();
	ModelMap model = new ModelMap(tipimovimentodoctipoList);
	boolean export = createJMesaExport(request, response, tipimovimentodoctipoList);
	if (export) {
	    return null;
	}
	model.addAttribute("tipimovimentodoctipoList", tipimovimentodoctipoList);
	model.addAttribute("tipomovimentoinfo", tipimovimento);
	return model;
    }

    @RequestMapping
    public String createTipiDocumento(@RequestParam("codicetipomovimento") String codicemovimento, Model model) {

	Tipimovimento tipimovimento = tipimovimentoService.findById(new TipimovimentoId(ORMHelper.getIdcomune(), codicemovimento));
	Tipimovimentodoctipo tipimovimentodoctipo = new Tipimovimentodoctipo();
	tipimovimentodoctipo.setTipomovimento(tipimovimento);
	fixRenderEntityPropertyTipimovimentodoc(tipimovimentodoctipo);
	model.addAttribute("tipimovimentodoctipo", tipimovimentodoctipo);
	model.addAttribute("tipimovimento", tipimovimento);
	model.addAttribute("fasiesecuzione", FasiDiEsecuzioneEnum.toMap());
	setPageAttributes(model);
	return "tipimovimento/formTipoDocumenti";
    }

    @RequestMapping
    public String insertDocumentoTipo(Model model, @ModelAttribute("tipimovimentodoctipo") Tipimovimentodoctipo tipimovimentodoctipo,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	if (tipimovimentodoctipo.getLetteretipo().getId().getCodice() != null) {
	    Letteretipo letteretipo = letteretipoService.findById(new PkId(tipimovimentodoctipo.getLetteretipo().getId().getCodice()));
	    tipimovimentodoctipo.setLetteretipo(letteretipo);
	    TipimovimentodoctipoId id = new TipimovimentodoctipoId();
	    id.setCodicelettera(tipimovimentodoctipo.getLetteretipo().getId().getCodice());
	    id.setIdcomune(ORMHelper.getIdcomune());
	    id.setTipomovimento(tipimovimentodoctipo.getTipomovimento().getId().getTipomovimento());
	    Tipimovimento tipimovimento = tipimovimentodoctipo.getTipomovimento();
	    tipimovimento.setCodicelettera(tipimovimentodoctipo.getLetteretipo().getId().getCodice().shortValue());
	    tipimovimentodoctipo.setTipomovimento(tipimovimento);
	    tipimovimentodoctipo.setId(id);
	}
	fixMergeEntityPropertyTipimovimentodoc(tipimovimentodoctipo);
	try {
	    tipimovimentodoctipoService.insert(tipimovimentodoctipo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovimentodoctipo, e);
	    fixRenderEntityPropertyTipimovimentodoc(tipimovimentodoctipo);
	    model.addAttribute("tipimovimento", tipimovimentodoctipo.getTipomovimento());
	    model.addAttribute("tipimovimentodoctipo", tipimovimentodoctipo);
	    model.addAttribute("fasiesecuzione", FasiDiEsecuzioneEnum.toMap());
	    return "tipimovimento/formTipoDocumenti";
	}
	status.setComplete();
	return "redirect:viewTipoDocumenti.htm?codiceMovimento=" + tipimovimentodoctipo.getId().getTipomovimento() + "&codiceLettera=" +
	       tipimovimentodoctipo.getId().getCodicelettera() + "&status_msg=02";
    }

    @RequestMapping
    public String viewTipoDocumenti(@RequestParam("codiceMovimento") String codicemovimento, @RequestParam("codiceLettera") Integer codicelettera,
	    Model model) {

	TipimovimentodoctipoId id = new TipimovimentodoctipoId();
	id.setIdcomune(ORMHelper.getIdcomune());
	id.setCodicelettera(codicelettera);
	id.setTipomovimento(codicemovimento);
	Tipimovimentodoctipo tipimovimentodoctipo = tipimovimentodoctipoService.findById(id);
	fixRenderEntityPropertyTipimovimentodoc(tipimovimentodoctipo);
	model.addAttribute("tipimovimentodoctipo", tipimovimentodoctipo);
	model.addAttribute("tipimovimento", tipimovimentodoctipo.getTipomovimento());
	model.addAttribute("fasiesecuzione", FasiDiEsecuzioneEnum.toMap());
	setPageAttributes(model);
	return "tipimovimento/formTipoDocumenti";
    }

    @RequestMapping
    public String updateDocumentoTipo(Model model, @ModelAttribute("tipimovimentodoctipo") Tipimovimentodoctipo tipimovimentodoctipo,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	if (tipimovimentodoctipo.getLetteretipo().getId().getCodice() != null) {
	    Letteretipo letteretipo = letteretipoService.findById(new PkId(tipimovimentodoctipo.getLetteretipo().getId().getCodice()));
	    tipimovimentodoctipo.setLetteretipo(letteretipo);
	    TipimovimentodoctipoId id = new TipimovimentodoctipoId();
	    id.setCodicelettera(tipimovimentodoctipo.getLetteretipo().getId().getCodice());
	    id.setIdcomune(ORMHelper.getIdcomune());
	    id.setTipomovimento(tipimovimentodoctipo.getTipomovimento().getId().getTipomovimento());
	    Tipimovimento tipimovimento = tipimovimentodoctipo.getTipomovimento();
	    tipimovimento.setCodicelettera(tipimovimentodoctipo.getLetteretipo().getId().getCodice().shortValue());
	    tipimovimentodoctipo.setTipomovimento(tipimovimento);
	    tipimovimentodoctipo.setId(id);
	}
	fixMergeEntityPropertyTipimovimentodoc(tipimovimentodoctipo);
	try {
	    tipimovimentodoctipoService.update(tipimovimentodoctipo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovimentodoctipo, e);
	    fixRenderEntityPropertyTipimovimentodoc(tipimovimentodoctipo);
	    model.addAttribute("tipimovimento", tipimovimentodoctipo.getTipomovimento());
	    model.addAttribute("tipimovimentodoctipo", tipimovimentodoctipo);
	    return "tipimovimento/formTipoDocumenti";
	}
	status.setComplete();
	return "redirect:viewTipoDocumenti.htm?codiceMovimento=" + tipimovimentodoctipo.getId().getTipomovimento() + "&codiceLettera=" +
	       tipimovimentodoctipo.getId().getCodicelettera() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteDocumentoTipo(@ModelAttribute("tipimovimentodoctipo") Tipimovimentodoctipo tipimovimentodoctipo, BindingResult result,
	    SessionStatus status) {

	Tipimovimentodoctipo objToDelete = tipimovimentodoctipoService.findById(tipimovimentodoctipo.getId());
	try {
	    tipimovimentodoctipoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityPropertyTipimovimentodoc(tipimovimentodoctipo);
	    return "tipimovimento/form";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    @RequestMapping
    public String deleteDocumentoTipoFromList(@RequestParam("codicedoctipo") Integer codicedoctipo,
	    @RequestParam("codicemovimento") String codicemovimento) {

	TipimovimentodoctipoId id = new TipimovimentodoctipoId();
	id.setIdcomune(ORMHelper.getIdcomune());
	id.setCodicelettera(codicedoctipo);
	id.setTipomovimento(codicemovimento);
	Tipimovimentodoctipo objToDelete = tipimovimentodoctipoService.findById(id);
	tipimovimentodoctipoService.delete(objToDelete);
	return "redirect:listdocumentitipo.htm?tipimovimento.codice=" + objToDelete.getTipomovimento().getId().getTipomovimento();
    }

    @RequestMapping
    public ModelMap listoneri(@RequestParam("tipimovimento.codice") String codicemovimento, HttpServletRequest request,
	    HttpServletResponse response) {

	Tipimovimento tipimovimento = tipimovimentoService.findById(new TipimovimentoId(ORMHelper.getIdcomune(), codicemovimento));
	Set<Tipimovimentooneri> tipimovimentooneriList = tipimovimento.getTipimovimentooneris();
	ModelMap model = new ModelMap(tipimovimentooneriList);
	boolean export = createJMesaExport(request, response, tipimovimentooneriList);
	if (export) {
	    return null;
	}
	model.addAttribute("tipimovimentooneriList", tipimovimentooneriList);
	model.addAttribute("tipomovimentoinfo", tipimovimento);
	return model;
    }

    @RequestMapping
    public String createOneri(@RequestParam("codicetipomovimento") String codicemovimento, Model model) {

	Tipimovimento tipimovimento = tipimovimentoService.findById(new TipimovimentoId(ORMHelper.getIdcomune(), codicemovimento));
	List<Onericomportamento> listComportamenti = onericomportamentoService.findAll(null, null);
	Tipimovimentooneri tipimovimentooneri = new Tipimovimentooneri();
	tipimovimentooneri.setTipimovimento(tipimovimento);
	fixRenderTipimovimentioneriProperty(tipimovimentooneri);
	model.addAttribute("tipimovimentooneri", tipimovimentooneri);
	model.addAttribute("tipimovimento", tipimovimento);
	model.addAttribute("listComportamenti", listComportamenti);
	model.addAttribute("typeform", "new");
	setPageAttributes(model);
	return "tipimovimento/formOneri";
    }

    @RequestMapping
    public String insertOneri(Model model, @ModelAttribute("tipimovimentooneri") Tipimovimentooneri tipimovimentooneri, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	if (result.hasErrors()) {
	    model.addAttribute("tipimovimento", tipimovimentooneri.getTipimovimento());
	    model.addAttribute("tipimovimentooneri", tipimovimentooneri);
	    model.addAttribute("typeform", "new");
	    return "tipimovimento/formOneri";
	}
	TipimovimentooneriId id = new TipimovimentooneriId();
	id.setIdcomune(ORMHelper.getIdcomune());
	if (tipimovimentooneri.getOnericomportamento().getCodicecomportamento() != null) {
	    Onericomportamento onericomportamento = onericomportamentoService
		    .findById(tipimovimentooneri.getOnericomportamento().getCodicecomportamento());
	    tipimovimentooneri.setOnericomportamento(onericomportamento);
	    id.setCodicecomportamento(tipimovimentooneri.getOnericomportamento().getCodicecomportamento());
	}
	if (tipimovimentooneri.getTipicausalioneri().getId().getCodice() != null) {
	    Tipicausalioneri tipicausalioneri = tipicausalioneriService
		    .findById(new PkId(tipimovimentooneri.getTipicausalioneri().getId().getCodice()));
	    tipimovimentooneri.setTipicausalioneri(tipicausalioneri);
	    id.setFkCoid(tipimovimentooneri.getTipicausalioneri().getId().getCodice());
	}
	id.setTipomovimento(tipimovimentooneri.getTipimovimento().getId().getTipomovimento());
	tipimovimentooneri.setId(id);
	fixMergeTipimovimentioneriProperty(tipimovimentooneri);
	try {
	    tipimovimentooneriService.insert(tipimovimentooneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovimentooneri, e);
	    fixRenderTipimovimentioneriProperty(tipimovimentooneri);
	    List<Onericomportamento> listComportamenti = onericomportamentoService.findAll(null, null);
	    model.addAttribute("listComportamenti", listComportamenti);
	    model.addAttribute("tipimovimento", tipimovimentooneri.getTipimovimento());
	    model.addAttribute("tipimovimentooneri", tipimovimentooneri);
	    model.addAttribute("typeform", "new");
	    return "tipimovimento/formOneri";
	}
	status.setComplete();
	return "redirect:viewOneri.htm?codiceMovimento=" + tipimovimentooneri.getId().getTipomovimento() + "&codiceComportamento=" +
	       tipimovimentooneri.getId().getCodicecomportamento() + "&codiceOnere=" + tipimovimentooneri.getId().getFkCoid() + "&status_msg=02";
    }

    @RequestMapping
    public String viewOneri(@RequestParam("codiceMovimento") String codicemovimento, @RequestParam("codiceComportamento") Integer codicecomportamento,
	    @RequestParam("codiceOnere") Integer codiceonere, Model model) {

	TipimovimentooneriId id = new TipimovimentooneriId();
	id.setIdcomune(ORMHelper.getIdcomune());
	id.setCodicecomportamento(codicecomportamento);
	id.setTipomovimento(codicemovimento);
	id.setFkCoid(codiceonere);
	Tipimovimentooneri tipimovimentooneri = tipimovimentooneriService.findById(id);
	fixRenderTipimovimentioneriProperty(tipimovimentooneri);
	List<Onericomportamento> listComportamenti = onericomportamentoService.findAll(null, null);
	model.addAttribute("listComportamenti", listComportamenti);
	model.addAttribute("tipimovimentooneri", tipimovimentooneri);
	model.addAttribute("tipimovimento", tipimovimentooneri.getTipimovimento());
	model.addAttribute("typeform", "view");
	setPageAttributes(model);
	return "tipimovimento/formOneri";
    }

    @RequestMapping
    public String deleteOneriFromList(@RequestParam("codiceMovimento") String codicemovimento,
	    @RequestParam("codiceComportamento") Integer codicecomportamento, @RequestParam("codiceOnere") Integer codiceonere) {

	TipimovimentooneriId id = new TipimovimentooneriId();
	id.setIdcomune(ORMHelper.getIdcomune());
	id.setFkCoid(codiceonere);
	id.setTipomovimento(codicemovimento);
	id.setCodicecomportamento(codicecomportamento);
	Tipimovimentooneri objToDelete = tipimovimentooneriService.findById(id);
	tipimovimentooneriService.delete(objToDelete);
	return "redirect:listoneri.htm?tipimovimento.codice=" + objToDelete.getTipimovimento().getId().getTipomovimento();
    }

    @RequestMapping
    public String deleteOneri(@ModelAttribute("tipimovimentooneri") Tipimovimentooneri tipimovimentooneri, BindingResult result,
	    SessionStatus status) {

	Tipimovimentooneri objToDelete = tipimovimentooneriService.findById(tipimovimentooneri.getId());
	try {
	    tipimovimentooneriService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderTipimovimentioneriProperty(tipimovimentooneri);
	    return "tipimovimento/form";
	}
	status.setComplete();
	return "redirect:listoneri.htm?tipimovimento.codice=" + tipimovimentooneri.getTipimovimento().getId().getTipomovimento();
    }

    @RequestMapping
    public String createContromovimento(@RequestParam("codicemovimento") String codicemovimento, Model model) {

	Tipimovimento tipimovimento = tipimovimentoService.findById(new TipimovimentoId(ORMHelper.getIdcomune(), codicemovimento));
	Tipicontromovimento contromovimento = new Tipicontromovimento();
	contromovimento.setTipomovimento(tipimovimento);
	Calendar calendar = GregorianCalendar.getInstance();
	Date datacreazione = calendar.getTime();
	contromovimento.setDatacreazione(datacreazione);
	boolean isVerticalizzazioneSTCAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	contromovimento.setSoloseesitonegativo(0);
	fixRenderTipicontromovimetoProperty(contromovimento);
	model.addAttribute("contromovimento", contromovimento);
	model.addAttribute("isVerticalizzazioneSTCAttiva", isVerticalizzazioneSTCAttiva);
	setPageAttributes(model);
	return "tipimovimento/formContromovimenti";
    }

    @RequestMapping
    public String viewContromovimento(@RequestParam("codicecontromovimento") Integer codicecontromovimento, Model model) {

	Tipicontromovimento contromovimento = tipicontromovimentoService.findById(new PkId(codicecontromovimento));
	fixRenderTipicontromovimetoProperty(contromovimento);
	boolean isVerticalizzazioneSTCAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	model.addAttribute("contromovimento", contromovimento);
	model.addAttribute("isVerticalizzazioneSTCAttiva", isVerticalizzazioneSTCAttiva);
	setPageAttributes(model);
	return "tipimovimento/formContromovimenti";
    }

    @RequestMapping
    public String insertContromovimento(Model model, @ModelAttribute("contromovimento") Tipicontromovimento contromovimento, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Tipimovimento tipimovimento = tipimovimentoService
		.findById(new TipimovimentoId(ORMHelper.getIdcomune(), contromovimento.getTipomovimento().getId().getTipomovimento()));
	contromovimento.setTipomovimento(tipimovimento);
	// FIXME bug di hibernate quando ho più chiavi esterne verso la stessa tabella
	// recupero amministrazione che effettua il movimento
	if (StringUtils.isNotBlank(request.getParameter("amministrazioniTipiMovimento.id.codice"))) {
	    String codiceamministrazione = request.getParameter("amministrazioniTipiMovimento.id.codice");
	    contromovimento.getAmministrazioniTipiMovimento().getId().getCodice();
	    Amministrazioni amministrazionimov = amministrazioniService.findById(new PkId(Integer.parseInt(codiceamministrazione)));
	    contromovimento.setAmministrazioniTipiMovimento(amministrazionimov);
	} else {
	    contromovimento.setAmministrazioniTipiMovimento(null);
	}
	// recupero amministrazione che effettua il contro movimento
	if (StringUtils.isNotBlank(request.getParameter("amministrazioniTipiContromovimento.id.codice"))) {
	    String codiceamministrazione = request.getParameter("amministrazioniTipiContromovimento.id.codice");
	    Amministrazioni amministrazionicontromv = amministrazioniService.findById(new PkId(Integer.parseInt(codiceamministrazione)));
	    contromovimento.setAmministrazioniTipiContromovimento(amministrazionicontromv);
	} else {
	    contromovimento.setAmministrazioniTipiContromovimento(null);
	}
	// recupero la procedura assoaciata
	if (contromovimento.getTipiprocedure().getId() != null && contromovimento.getTipiprocedure().getId().getCodice() != null) {
	    Tipiprocedure tipiprocedure = tipiprocedureService.findById(new PkId(contromovimento.getTipiprocedure().getId().getCodice()));
	    contromovimento.setTipiprocedure(tipiprocedure);
	}
	// recupero contro movimento associato
	if (contromovimento.getTipocontromovimento().getId() != null && contromovimento.getTipocontromovimento().getId().getTipomovimento() != null) {
	    Tipimovimento tipocontromovimento = tipimovimentoService
		    .findById(new TipimovimentoId(ORMHelper.getIdcomune(), contromovimento.getTipocontromovimento().getId().getTipomovimento()));
	    contromovimento.setTipocontromovimento(tipocontromovimento);
	}
	fixMergeTipicontromovimetoProperty(contromovimento);
	try {
	    tipicontromovimentoService.insert(contromovimento);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, contromovimento, e);
	    contromovimento.setTipomovimento(tipimovimento);
	    fixRenderTipicontromovimetoProperty(contromovimento);
	    model.addAttribute("contromovimento", contromovimento);
	    return "tipimovimento/formContromovimenti";
	}
	status.setComplete();
	return "redirect:viewContromovimento.htm?codicecontromovimento=" + contromovimento.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String updateContromovimento(Model model, @ModelAttribute("contromovimento") Tipicontromovimento contromovimento, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Tipimovimento tipimovimento = tipimovimentoService
		.findById(new TipimovimentoId(ORMHelper.getIdcomune(), contromovimento.getTipomovimento().getId().getTipomovimento()));
	contromovimento.setTipomovimento(tipimovimento);
	// recupero amministrazione che effettua il movimento
	if (StringUtils.isNotBlank(request.getParameter("amministrazioniTipiMovimento.id.codice"))) {
	    String codiceamministrazione = request.getParameter("amministrazioniTipiMovimento.id.codice");
	    contromovimento.getAmministrazioniTipiMovimento().getId().getCodice();
	    Amministrazioni amministrazionimov = amministrazioniService.findById(new PkId(Integer.parseInt(codiceamministrazione)));
	    contromovimento.setAmministrazioniTipiMovimento(amministrazionimov);
	} else {
	    contromovimento.setAmministrazioniTipiMovimento(null);
	}
	// recupero amministrazione che effettua il contro movimento
	if (StringUtils.isNotBlank(request.getParameter("amministrazioniTipiContromovimento.id.codice"))) {
	    String codiceamministrazione = request.getParameter("amministrazioniTipiContromovimento.id.codice");
	    Amministrazioni amministrazionicontromv = amministrazioniService.findById(new PkId(Integer.parseInt(codiceamministrazione)));
	    contromovimento.setAmministrazioniTipiContromovimento(amministrazionicontromv);
	} else {
	    contromovimento.setAmministrazioniTipiContromovimento(null);
	}
	// recupero la procedura assoaciata
	if (EntityUtils.getNestedProperty(contromovimento.getTipiprocedure(), "id.codice") != null) {
	    Tipiprocedure tipiprocedure = tipiprocedureService.findById(new PkId(contromovimento.getTipiprocedure().getId().getCodice()));
	    contromovimento.setTipiprocedure(tipiprocedure);
	}
	// recupero la tipi contro movimento
	if (EntityUtils.getNestedProperty(contromovimento.getTipocontromovimento(), "id.tipomovimento") != null) {
	    Tipimovimento tipicontromovimento = tipimovimentoService
		    .findById(new TipimovimentoId(ORMHelper.getIdcomune(), contromovimento.getTipocontromovimento().getId().getTipomovimento()));
	    contromovimento.setTipocontromovimento(tipicontromovimento);
	}
	fixMergeTipicontromovimetoProperty(contromovimento);
	try {
	    tipicontromovimentoService.updateAndEliminaTempiRispostaNonValidi(contromovimento);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, contromovimento, e);
	    contromovimento.setTipomovimento(tipimovimento);
	    fixRenderTipicontromovimetoProperty(contromovimento);
	    model.addAttribute("contromovimento", contromovimento);
	    return "tipimovimento/formContromovimenti";
	}
	status.setComplete();
	return "redirect:viewContromovimento.htm?codicecontromovimento=" + contromovimento.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteContromovimento(@ModelAttribute("contromovimento") Tipicontromovimento tipicontromovimento, BindingResult result,
	    SessionStatus status) {

	Tipicontromovimento objToDelete = tipicontromovimentoService.findById(tipicontromovimento.getId());
	try {
	    tipicontromovimentoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderTipicontromovimetoProperty(tipicontromovimento);
	    return "tipimovimento/formContromovimenti";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + objToDelete.getTipomovimento().getId().getTipomovimento();
    }

    @RequestMapping
    public String deleteTempirispostaContromovimento(Model model, @ModelAttribute("tempirispostaCommand") TempirispostaCommand tempirispostaCommand,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	if (result.hasErrors()) {
	    return "tipimovimento/formTempirisposta";
	}
	try {
	    tipicontromovimentoService.deleteTempirisposta(tempirispostaCommand.getTipicontromovimento());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tempirispostaCommand.getEntity(), e);
	    model.addAttribute("tempirispostaCommand", tempirispostaCommand);
	    return "tipimovimento/formTempirisposta";
	}
	status.setComplete();
	return "redirect:createTempirispostaContromovimento.htm?codicecontromovimento=" +
	       tempirispostaCommand.getTipicontromovimento().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String createTempirispostaContromovimento(@RequestParam("codicecontromovimento") Integer codicecontromovimento, Model model) {

	Tipicontromovimento tipocontromovimento = tipicontromovimentoService.findById(new PkId(codicecontromovimento));
	TempirispostaCommand tempirispostaCommand = new TempirispostaCommand();
	Tempirisposta tempirisposta = new Tempirisposta();
	tempirisposta.setAmministrazione(tipocontromovimento.getAmministrazioniTipiMovimento());
	tempirisposta.setTipiprocedure(tipocontromovimento.getTipiprocedure());
	tempirisposta.setTipicontromovimento(tipocontromovimento.getTipocontromovimento());
	tempirisposta.setTipimovimento(tipocontromovimento.getTipomovimento());
	fixRenderTempirispostaProperty(tempirisposta);
	tempirispostaCommand.setEntity(tempirisposta);
	List<TempirispostaHelper> list = tempirispostaService.findByTempirispostaHelperByTipoControMov(tipocontromovimento);
	tempirispostaCommand.setTempirispostaHelpers(list);
	tempirispostaCommand.setTipicontromovimento(tipocontromovimento);
	model.addAttribute("tempirispostaCommand", tempirispostaCommand);
	setPageAttributes(model);
	return "tipimovimento/formTempirisposta";
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void ajaxInsertTempirispostaContromovimento(Model model, HttpServletRequest request, HttpServletResponse response) //
	    throws IOException {

	try {
	    TempirispostaContainerJson tempiRisposta = Utilities.unMarshallJsonStream(request.getInputStream(), TempirispostaContainerJson.class,
		    true);
	    List<TempirispostaValoriHelper> l = popolaTempiRispostaJson(tempiRisposta);
	    TempirispostaHelperBean t = new TempirispostaHelperBean(tempiRisposta.getTipomovimento(), tempiRisposta.getTipocontromovimento(), l);
	    tempirispostaService.insertAndUpdateTempirisposta(t);
	    response.getOutputStream().write("{\"esito\": \"OK\"}".getBytes());
	} catch (Exception e) {
	    response.getOutputStream().write("{\"esito\": \"KO\"}".getBytes());
	}
    }

    private List<TempirispostaValoriHelper> popolaTempiRispostaJson(TempirispostaContainerJson tempiRisposta) {

	List<TempirispostaValoriHelper> l = new ArrayList<TempirispostaValoriHelper>();
	for (TempiRispostaJson tempirispostaValoriHelper : tempiRisposta.getTempi()) {
	    l.add(TempiRispostaJson.toTempirispostaValoriHelper(tempirispostaValoriHelper));
	}
	return l;
    }

    // GESTIONE DEI MODELLI
    @RequestMapping
    public ModelMap listmodelli(@RequestParam("codicemovimento") String codice, HttpServletRequest request, HttpServletResponse response) {

	Tipimovimento tipimovimento = tipimovimentoService.findById(new TipimovimentoId(ORMHelper.getIdcomune(), codice));
	Set<Tipimovimentidyn2modellit> tipimovimentidyn2modellits = tipimovimento.getTipimovimentidyn2modellits();
	ModelMap model = new ModelMap(tipimovimentidyn2modellits);
	boolean export = createJMesaExport(request, response, tipimovimentidyn2modellits);
	if (export) {
	    return null;
	}
	model.addAttribute("tipimovimentidyn2modellits", tipimovimentidyn2modellits);
	model.addAttribute("tipimovimento", tipimovimento);
	return model;
    }

    @RequestMapping
    public String createmodelli(@RequestParam("codicemovimento") String codice, Model model) {

	Tipimovimento tipimovimento = tipimovimentoService.findById(new TipimovimentoId(ORMHelper.getIdcomune(), codice));
	Tipimovimentidyn2modellit tipimovimentidyn2modellit = new Tipimovimentidyn2modellit();
	tipimovimentidyn2modellit.setTipimovimento(tipimovimento);
	// setto l'id
	Tipimovimentidyn2modellitId id = new Tipimovimentidyn2modellitId();
	id.setTipomovimento(codice);
	tipimovimentidyn2modellit.setId(id);
	fixRenderTipimovimentidyn2modellitProperty(tipimovimentidyn2modellit);
	model.addAttribute("tipimovimentidyn2modellit", tipimovimentidyn2modellit);
	setPageAttributes(model);
	return "tipimovimento/formModelli";
    }

    @RequestMapping
    public String insertModelli(Model model, @ModelAttribute("tipimovimentidyn2modellit") Tipimovimentidyn2modellit tipimovimentidyn2modellit,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "tipimovimento/formModelli";
	}
	// recupero i campi ajax
	if (tipimovimentidyn2modellit.getDyn2Modellit() != null && tipimovimentidyn2modellit.getDyn2Modellit().getId().getCodice() != null) {
	    Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(tipimovimentidyn2modellit.getDyn2Modellit().getId());
	    Tipimovimentidyn2modellitId id = tipimovimentidyn2modellit.getId();
	    id.setFkD2mtId(tipimovimentidyn2modellit.getDyn2Modellit().getId().getCodice());
	    tipimovimentidyn2modellit.setId(id);
	    tipimovimentidyn2modellit.setDyn2Modellit(dyn2Modellit);
	}
	fixMergeTipimovimentidyn2modellitProperty(tipimovimentidyn2modellit);
	try {
	    tipimovimentidyn2modellitService.insert(tipimovimentidyn2modellit);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovimentidyn2modellit, e);
	    model.addAttribute("tipimovimentidyn2modellit", tipimovimentidyn2modellit);
	    fixRenderTipimovimentidyn2modellitProperty(tipimovimentidyn2modellit);
	    return "tipimovimento/formModelli";
	}
	status.setComplete();
	return "redirect:listmodelli.htm?codicemovimento=" + tipimovimentidyn2modellit.getTipimovimento().getId().getTipomovimento();
    }

    @RequestMapping
    public String deleteModelli(@RequestParam("codicemodellot") Integer codicemodellot, @RequestParam("codicemovimento") String codice) {

	Tipimovimentidyn2modellitId id = new Tipimovimentidyn2modellitId();
	id.setFkD2mtId(codicemodellot);
	id.setTipomovimento(codice);
	Tipimovimentidyn2modellit objToDelete = tipimovimentidyn2modellitService.findById(id);
	tipimovimentidyn2modellitService.delete(objToDelete);
	return "redirect:listmodelli.htm?codicemovimento=" + codice;
    }

    @RequestMapping
    public void ajaxEsitoTipomovimento(@RequestParam("tipoMovimento") String tipoMovimento, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	TipimovimentoId id = new TipimovimentoId();
	id.setTipomovimento(tipoMovimento);
	Tipimovimento entity = tipimovimentoService.findById(id);
	if (entity == null) {
	    StringBuilder sb = new StringBuilder("<ul><li class='li_error'></li></ul><span class='error'>");
	    sb.append("Il tipo movimento [" + tipoMovimento + "] non esiste");
	    sb.append("</span>");
	    response.getWriter().write(sb.toString());
	} else {
	    String result = "LABEL_ESITO#LABEL_PUBBLICA#LABEL_PUBBLICA_PARERE";
	    String esito = entity.getTipologiaesito() == null ? "0" : (entity.getTipologiaesito().intValue() > 0 ? "1" : "0");
	    result = result.replaceFirst("LABEL_ESITO", esito);
	    String pubblica = entity.getFlagPubblicamovimento() == null ? "0" : (entity.getFlagPubblicamovimento().booleanValue() ? "1" : "0");
	    result = result.replaceFirst("LABEL_PUBBLICA", pubblica);
	    String pubblicaparere = entity.getFlagPubblicaparere() == null ? "0" : (entity.getFlagPubblicaparere().booleanValue() ? "1" : "0");
	    result = result.replaceFirst("LABEL_PUBBLICA_PARERE", pubblicaparere);
	    response.getWriter().write(result);
	}
    }

    @Override
    protected void fixMergeEntityProperty(Tipimovimento entity) {

	if (entity.getFoSoggettiesterni() != null && entity.getFoSoggettiesterni().getCodice() == null) {
	    entity.setFoSoggettiesterni(null);
	}
	if (entity.getTipologiaregistri() != null && entity.getTipologiaregistri().getId() != null
		&& entity.getTipologiaregistri().getId().getCodice() == null) {
	    entity.setTipologiaregistri(null);
	}
	if (EntityUtils.getNestedProperty(entity.getMailtipoByFkTipimovcomTelMailtipo(), "id.codice") == null) {
	    entity.setMailtipoByFkTipimovcomTelMailtipo(null);
	}
	if (EntityUtils.getNestedProperty(entity.getMailtipoByFkTipimovricTelMailtipo(), "id.codice") == null) {
	    entity.setMailtipoByFkTipimovricTelMailtipo(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Tipimovimento entity) {

	if (entity.getFoSoggettiesterni() == null) {
	    entity.setFoSoggettiesterni(new FoSoggettiesterni());
	}
	if (entity.getTipologiaregistri() == null) {
	    entity.setTipologiaregistri(new Tipologiaregistri());
	}
	if (entity.getMailtipoByFkTipimovcomTelMailtipo() == null) {
	    entity.setMailtipoByFkTipimovcomTelMailtipo(new Mailtipo());
	}
	if (entity.getMailtipoByFkTipimovricTelMailtipo() == null) {
	    entity.setMailtipoByFkTipimovricTelMailtipo(new Mailtipo());
	}
	if (entity.getLetteretipo() == null) {
	    entity.setLetteretipo(new Letteretipo());
	}
	if (entity.getStatoistanza() == null) {
	    entity.setStatoistanza(new Statiistanza());
	}
	if (entity.getLetteraTipoAllegati() == null) {
	    entity.setLetteraTipoAllegati(new Letteretipo());
	}
	if (entity.getMailtipoOggProt() == null) {
	    entity.setMailtipoOggProt(new Mailtipo());
	}
    }

    protected void fixMergeEntityPropertyTipimovimentodoc(Tipimovimentodoctipo tipimovimentodoctipo) {

	if (tipimovimentodoctipo.getLetteretipo() != null && tipimovimentodoctipo.getLetteretipo().getId() != null
		&& tipimovimentodoctipo.getLetteretipo().getId().getCodice() == null) {
	    tipimovimentodoctipo.setLetteretipo(null);
	}
	if (tipimovimentodoctipo.getTipomovimento() != null && tipimovimentodoctipo.getTipomovimento().getId() != null
		&& tipimovimentodoctipo.getTipomovimento().getId().getTipomovimento().equals("")) {
	    tipimovimentodoctipo.setTipomovimento(null);
	}
    }

    protected void fixRenderEntityPropertyTipimovimentodoc(Tipimovimentodoctipo tipimovimentodoctipo) {

	if (tipimovimentodoctipo.getLetteretipo() == null) {
	    tipimovimentodoctipo.setLetteretipo(new Letteretipo());
	}
	if (tipimovimentodoctipo.getTipomovimento() == null) {
	    tipimovimentodoctipo.setTipomovimento(new Tipimovimento());
	}
    }

    protected void fixMergeTipimovimentioneriProperty(Tipimovimentooneri tipimovimentooneri) {

	if (tipimovimentooneri.getOnericomportamento() != null && tipimovimentooneri.getOnericomportamento().getCodicecomportamento() == null) {
	    tipimovimentooneri.setOnericomportamento(null);
	}
	if (tipimovimentooneri.getTipicausalioneri() != null && tipimovimentooneri.getTipicausalioneri().getId() != null
		&& tipimovimentooneri.getTipicausalioneri().getId().getCodice() == null) {
	    tipimovimentooneri.setTipicausalioneri(null);
	}
	if (tipimovimentooneri.getTipimovimento() != null && tipimovimentooneri.getTipimovimento().getId() != null
		&& tipimovimentooneri.getTipimovimento().getId().getTipomovimento().equals("")) {
	    tipimovimentooneri.setTipimovimento(null);
	}
    }

    protected void fixRenderTipimovimentioneriProperty(Tipimovimentooneri tipimovimentooneri) {

	if (tipimovimentooneri.getOnericomportamento() == null) {
	    tipimovimentooneri.setOnericomportamento(new Onericomportamento());
	}
	if (tipimovimentooneri.getTipicausalioneri() == null) {
	    tipimovimentooneri.setTipicausalioneri(new Tipicausalioneri());
	}
	if (tipimovimentooneri.getTipimovimento() == null) {
	    tipimovimentooneri.setTipimovimento(new Tipimovimento());
	}
    }

    protected void fixMergeTipicontromovimetoProperty(Tipicontromovimento entity) {

	if (entity.getTipomovimento() != null && entity.getTipomovimento().getId() != null
		&& (entity.getTipomovimento().getId().getTipomovimento() == null
			|| entity.getTipomovimento().getId().getTipomovimento().equals(""))) {
	    entity.setTipomovimento(null);
	}
	if (entity.getTipiprocedure() != null && entity.getTipiprocedure().getId() != null && entity.getTipiprocedure().getId().getCodice() == null) {
	    entity.setTipiprocedure(null);
	}
	if (entity.getTipocontromovimento() != null && entity.getTipocontromovimento().getId() != null
		&& (entity.getTipocontromovimento().getId().getTipomovimento() == null
			|| entity.getTipocontromovimento().getId().getTipomovimento().equals(""))) {
	    entity.setTipocontromovimento(null);
	}
	if (entity.getAmministrazioniTipiMovimento() != null && entity.getAmministrazioniTipiMovimento().getId() != null
		&& entity.getAmministrazioniTipiMovimento().getId().getCodice() == null) {
	    entity.setAmministrazioniTipiMovimento(null);
	}
	if (entity.getAmministrazioniTipiContromovimento() != null && entity.getAmministrazioniTipiContromovimento().getId() != null
		&& entity.getAmministrazioniTipiContromovimento().getId().getCodice() == null) {
	    entity.setAmministrazioniTipiContromovimento(null);
	}
    }

    protected void fixRenderTipicontromovimetoProperty(Tipicontromovimento entity) {

	if (entity.getTipomovimento() == null) {
	    entity.setTipomovimento(new Tipimovimento());
	}
	if (entity.getTipocontromovimento() == null) {
	    entity.setTipocontromovimento(new Tipimovimento());
	}
	if (entity.getTipiprocedure() == null) {
	    entity.setTipiprocedure(new Tipiprocedure());
	}
	if (entity.getAmministrazioniTipiMovimento() == null) {
	    entity.setAmministrazioniTipiMovimento(new Amministrazioni());
	}
	if (entity.getAmministrazioniTipiContromovimento() == null) {
	    entity.setAmministrazioniTipiContromovimento(new Amministrazioni());
	}
	if (StringUtils.isBlank(entity.getPropostostc())) {
	    entity.setPropostostc("0");
	}
    }

    protected void fixMergeTempirispostaProperty(Tempirisposta entity) {

	if (entity.getTipiprocedure() != null && entity.getTipiprocedure().getId() != null && entity.getTipiprocedure().getId().getCodice() == null) {
	    entity.setTipiprocedure(null);
	}
	if (entity.getAmministrazione() != null && entity.getAmministrazione().getId() != null
		&& entity.getAmministrazione().getId().getCodice() == null) {
	    entity.setAmministrazione(null);
	}
	if (entity.getTipicontromovimento() != null && entity.getTipicontromovimento().getId() != null
		&& (entity.getTipicontromovimento().getId().getTipomovimento() == null
			|| entity.getTipicontromovimento().getId().getTipomovimento().equals(""))) {
	    entity.setTipicontromovimento(null);
	}
	if (entity.getTipimovimento() != null && entity.getTipimovimento().getId() != null
		&& (entity.getTipimovimento().getId().getTipomovimento() == null
			|| entity.getTipimovimento().getId().getTipomovimento().equals(""))) {
	    entity.setTipimovimento(null);
	}
    }

    protected void fixRenderTempirispostaProperty(Tempirisposta entity) {

	if (entity.getTipimovimento() == null) {
	    entity.setTipimovimento(new Tipimovimento());
	}
	if (entity.getTipicontromovimento() == null) {
	    entity.setTipicontromovimento(new Tipimovimento());
	}
	if (entity.getTipiprocedure() == null) {
	    entity.setTipiprocedure(new Tipiprocedure());
	}
	if (entity.getAmministrazione() == null) {
	    entity.setAmministrazione(new Amministrazioni());
	}
    }

    private void fixRenderTipimovimentidyn2modellitProperty(Tipimovimentidyn2modellit entity) {

	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
	if (entity.getTipimovimento() == null) {
	    entity.setTipimovimento(new Tipimovimento());
	}
    }

    private void fixMergeTipimovimentidyn2modellitProperty(Tipimovimentidyn2modellit entity) {

	if (entity.getDyn2Modellit() != null && entity.getDyn2Modellit().getId().getCodice() == null) {
	    entity.setDyn2Modellit(null);
	}
	if (entity.getTipimovimento() != null && !StringUtils.isNotBlank(entity.getTipimovimento().getId().getTipomovimento())) {
	    entity.setTipimovimento(null);
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	boolean isVerticalizzazioneSitAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO);
	model.addAttribute("isVerticalizzazioneSitAttiva", isVerticalizzazioneSitAttiva);
	List<Statiistanza> statiistanzaList = statiistanzaService.findAll(null, null);
	model.addAttribute("statiistanzaList", statiistanzaList);
    }

    @RequestMapping
    public String abilitaDisabilita(Model model, @ModelAttribute("tipimovimento") TipimovimentoCommand tipimovimento, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Tipimovimento tm = tipimovimento.getEntity();
	tm = tipimovimentoService.findById(tm.getId());
	boolean disabilita = false;
	if (tm.getFlagDisabilitato() == null || tm.getFlagDisabilitato().booleanValue() == false) {
	    disabilita = true;
	    boolean check = tipimovimentoService.checkSeDisabilitare(tm);
	    if (!check) {
		return "redirect:listaDipendenze.htm?codice=" + tm.getId().getTipomovimento();
	    }
	}
	tm.setFlagDisabilitato(disabilita);
	try {
	    tipimovimentoService.update(tm);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovimento, e);
	    fixRenderEntityProperty(tm);
	    tm = tipimovimentoService.findById(tm.getId());
	    prepareViewPage(model, tm.getId().getTipomovimento(), request);
	    setPageAttributes(model);
	    return "tipimovimento/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tm.getId().getTipomovimento() + "&status_msg=02";
    }

    @RequestMapping
    public String listaDipendenze(@RequestParam("codice") String codice, Model model, HttpServletRequest request) {

	TipimovimentoId id = new TipimovimentoId(codice);
	Tipimovimento tm = tipimovimentoService.findById(id);
	fixRenderEntityProperty(tm);
	model.addAttribute("tipimovimento", tm);
	List<Tipiprocedure> tipiprocedures = tipiprocedureService.findByTuttiCampiTipimovimento(tm.getId().getTipomovimento(), 0, 10);
	model.addAttribute("tipiprocedures", tipiprocedures);
	//	TIPIPROCEDUREAVVIO.TIPOMOVIMENTO 
	List<Tipiprocedureavvio> tipiprocedureavvios = tipiprocedureavvioService.findTipimovimento(tm.getId().getTipomovimento(), 0, 10);
	model.addAttribute("tipiprocedureavvios", tipiprocedureavvios);
	//	COMMEDILIZIE_TIPOLOGIEDETT.TIPOMOVIMENTO 
	List<CommedilizieTipologiedett> commedilizieTipologiedetts = commedilizieTipologiedettService.findTipimovimento(tm.getId().getTipomovimento(),
		0, 10);
	model.addAttribute("commedilizieTipologiedetts", commedilizieTipologiedetts);
	//	COMMEDILIZIE_TIPOPARERI.TIPOMOVIMENTO 
	List<CommedilizieTipopareri> commedilizieTipopareris = commedilizieTipopareriService
		.findConfigurazioniPerTipomovimento(tm.getId().getTipomovimento());
	model.addAttribute("commedilizieTipopareris", commedilizieTipopareris);
	List<Inventarioprocedimenti> inventarioprocedimentis = inventarioprocedimentiService.findByTipimovimento(tm.getId().getTipomovimento(), 0,
		10);
	//	INVENTARIOPROCEDIMENTI.TIPOMOVIMENTO 
	model.addAttribute("inventarioprocedimentis", inventarioprocedimentis);
	//	INVENTARIOPROCEDIMENTISOFTWARE.TIPOMOVIMENTO 
	List<Inventarioprocedimentisoftware> inventarioprocedimentisoftwares = inventarioprocedimentisoftwareService
		.findByTipimovimento(tm.getId().getTipomovimento(), 0, 10);
	model.addAttribute("inventarioprocedimentisoftwares", inventarioprocedimentisoftwares);
	//	ONERITIPIRATEIZZAZIONE.FK_TIPOMOV_DETERMDATAIN
	List<Oneritipirateizzazione> oneritipirateizzaziones = oneritipirateizzazioneService.findByTipimovimento(tm.getId().getTipomovimento(), 0,
		10);
	model.addAttribute("oneritipirateizzaziones", oneritipirateizzaziones);
	//	PROTOCOLLO_REGISTRI.IDTIPOMOVIMENTO
	List<ProtocolloRegistri> protocolloRegistris = protocolloRegistriService.findByTipimovimento(tm.getId().getTipomovimento(), 0, 10);
	model.addAttribute("protocolloRegistris", protocolloRegistris);
	//	TIPICONTROMOVIMENTO.TIPOCONTROMOVIMENTO
	List<Tipicontromovimento> tipicontromovimentomovs = tipicontromovimentoService.findByTipimovimento(tm.getId().getTipomovimento(), 0, 10);
	model.addAttribute("tipicontromovimentomovs", tipicontromovimentomovs);
	//	TIPICONTROMOVIMENTO.TIPOMOVIMENTO 
	List<Tipicontromovimento> tipicontromovimentocontros = tipicontromovimentoService.findByTipicontromovimento(tm.getId().getTipomovimento(), 0,
		10);
	model.addAttribute("tipicontromovimentocontros", tipicontromovimentocontros);
	setPageAttributes(model);
	return "tipimovimento/listaDipendenze";
    }
}
