package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RiTipiprocedimento;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.Subprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDocumenti;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedureavvio;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureavvioId;
import it.gruppoinit.pal.gp.core.domain.helper.TipoDownload;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.SessionDetails;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.NaturaendoService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.SubprocedureService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipicontromovimentoService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureDocumentiService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureDyn2modellitService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureavvioService;
import it.gruppoinit.pal.gp.core.ws.client.SigeprorendererWSClient;

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes(value = { "tipiprocedure", "tipiprocedureavvio", "subprocedure", "tipiproceduredocumenti", "tipiprocedureDyn2modellit" })
public class TipiprocedureController extends BaseController<Tipiprocedure> {

    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private TipiprocedureService tipiprocedureService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private LetteretipoService letteretipoService;
    @Autowired
    private NaturaendoService naturaendoService;
    @Autowired
    private TipiprocedureavvioService tipiprocedureavvioService;
    @Autowired
    private SubprocedureService subprocedureService;
    @Autowired
    private TipiprocedureDocumentiService tipiprocedureDocumentiService;
    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private TipiprocedureDyn2modellitService tipiprocedureDyn2modellitService;
    @Autowired
    private TipicontromovimentoService tipicontromovimentoService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private StatiistanzaService statiistanzaService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipiprocedure> tipiprocedureList = tipiprocedureService.findAll(null, null);
	ModelMap model = new ModelMap(tipiprocedureList);
	boolean export = createJMesaExport(request, response, tipiprocedureList);
	if (export) {
	    return null;
	}
	model.addAttribute("tipiprocedureList", tipiprocedureList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Tipiprocedure tipiprocedure = new Tipiprocedure();
	tipiprocedure.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(tipiprocedure);
	model.addAttribute("tipiprocedure", tipiprocedure);
	model.addAttribute("isProceduraUsataInIstanze", Boolean.FALSE);
	setPageAttributes(model);
	return "tipiprocedure/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("tipiprocedure") Tipiprocedure tipiprocedure, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	if (result.hasErrors()) {
	    model.addAttribute("isProceduraUsataInIstanze", Boolean.FALSE);
	    setPageAttributes(model);
	    return "tipiprocedure/form";
	}
	// recupera tutti i campi passati tramite id (campi ajax)
	tipiprocedure = findAjaxField(tipiprocedure, request);
	fixMergeEntityProperty(tipiprocedure);
	tipiprocedure.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    tipiprocedureService.insert(tipiprocedure);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiprocedure, e);
	    fixRenderEntityProperty(tipiprocedure);
	    model.addAttribute("isProceduraUsataInIstanze", Boolean.FALSE);
	    setPageAttributes(model);
	    return "tipiprocedure/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiprocedure.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipiprocedure tipiprocedure = tipiprocedureService.findById(id);
	prepareViewPage(model, tipiprocedure, codice, request);
	setPageAttributes(model);
	return "tipiprocedure/form";
    }

    private void prepareViewPage(Model model, Tipiprocedure tipiprocedure, Integer codice, HttpServletRequest request) {

	// creao un oggetto procedure avvio per vedere se esiste un adi default già configurata
	Tipiprocedureavvio tipiprocedureavvio = new Tipiprocedureavvio();
	tipiprocedureavvio.setTipoProcedura(tipiprocedure);
	TipiprocedureavvioId idProceduraAvvio = new TipiprocedureavvioId();
	idProceduraAvvio.setIdcomune(ORMHelper.getIdcomune());
	idProceduraAvvio.setCodiceprocedura(codice);
	tipiprocedureavvio.setId(idProceduraAvvio);
	fixRenderEntityProperty(tipiprocedure);
	Boolean isMovimentoAvvioDefaultPresent = tipiprocedureavvioService.isMovimentoAvvioDefault(tipiprocedureavvio);
	int isProceduraUsataInIstanze = istanzeService.countByProcedure(tipiprocedure);
	model.addAttribute("isProceduraUsataInIstanze", new Boolean(isProceduraUsataInIstanze > 0));
	model.addAttribute("tipiprocedure", tipiprocedure);
	model.addAttribute("isMovimentoAvvioDefaultPresent", isMovimentoAvvioDefaultPresent);
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("tipiprocedure") Tipiprocedure tipiprocedure, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	if (result.hasErrors()) {
	    setPageAttributes(model);
	    return "tipiprocedure/form";
	}
	// recupera tutti i campi passati tramite id (campi ajax)
	tipiprocedure = findAjaxField(tipiprocedure, request);
	fixMergeEntityProperty(tipiprocedure);
	try {
	    tipiprocedureService.update(tipiprocedure);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiprocedure, e);
	    fixRenderEntityProperty(tipiprocedure);
	    setPageAttributes(model);
	    return "tipiprocedure/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiprocedure.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("tipiprocedure") Tipiprocedure tipiprocedure, BindingResult result, SessionStatus status) {

	Tipiprocedure objToDelete = tipiprocedureService.findById(tipiprocedure.getId());
	try {
	    tipiprocedureService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipiprocedure);
	    setPageAttributes(model);
	    return "tipiprocedure/form";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    @RequestMapping
    public String createMovimentoAvvio(Model model, @RequestParam("codiceprocedura") Integer codiceprocedura) {

	Tipiprocedure tipiprocedure = tipiprocedureService.findById(new PkId(codiceprocedura));
	Tipiprocedureavvio tipiprocedureavvio = new Tipiprocedureavvio();
	tipiprocedureavvio.setTipoProcedura(tipiprocedure);
	TipiprocedureavvioId id = new TipiprocedureavvioId();
	id.setCodiceprocedura(codiceprocedura);
	tipiprocedureavvio.setId(id);
	fixRenderEntityProperty(tipiprocedure);
	Boolean isMovimentoDefaultPresent = tipiprocedureavvioService.isMovimentoAvvioDefault(tipiprocedureavvio);
	model.addAttribute("tipiprocedureavvio", tipiprocedureavvio);
	model.addAttribute("isMovimentoDefaultPresent", isMovimentoDefaultPresent);
	setPageAttributes(model);
	return "tipiprocedure/formMovimentoAvvio";
    }

    @RequestMapping
    public String insertMovimentoavvio(@ModelAttribute("tipiprocedureavvio") Tipiprocedureavvio tipiprocedureavvio, BindingResult result,
	    SessionStatus status) {

	if (tipiprocedureavvio.getTipoMovimento() != null && tipiprocedureavvio.getTipoMovimento().getId().getTipomovimento() != null) {
	    Tipimovimento tipoMovimento = tipiMovimentoService.findById(tipiprocedureavvio.getTipoMovimento().getId());
	    tipiprocedureavvio.setTipoMovimento(tipoMovimento);
	    TipiprocedureavvioId id = tipiprocedureavvio.getId();
	    id.setTipomovimento(tipoMovimento.getId().getTipomovimento());
	}
	fixMergeTipiprocedureavvioProperty(tipiprocedureavvio);
	try {
	    tipiprocedureavvioService.insert(tipiprocedureavvio);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiprocedureavvio, e);
	    fixRenderTipiprocedureavvioProperty(tipiprocedureavvio);
	    return "tipiprocedure/formMovimentoAvvio";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiprocedureavvio.getTipoProcedura().getId().getCodice();
    }

    @RequestMapping
    public String viewMovimentoAvvio(Model model, @RequestParam("codiceprocedura") Integer codiceprocedura,
	    @RequestParam("codicemovimento") String codicemovimento) {

	TipiprocedureavvioId id = new TipiprocedureavvioId();
	id.setTipomovimento(codicemovimento);
	id.setCodiceprocedura(codiceprocedura);
	Tipiprocedureavvio tipiprocedureavvio = tipiprocedureavvioService.findById(id);
	fixRenderTipiprocedureavvioProperty(tipiprocedureavvio);
	Boolean isMovimentoDefaultPresent = tipiprocedureavvioService.isMovimentoAvvioDefault(tipiprocedureavvio);
	model.addAttribute("tipiprocedureavvio", tipiprocedureavvio);
	model.addAttribute("isMovimentoDefaultPresent", isMovimentoDefaultPresent);
	setPageAttributes(model);
	return "tipiprocedure/formMovimentoAvvio";
    }

    @RequestMapping
    public String updateMovimentoavvio(@ModelAttribute("tipiprocedureavvio") Tipiprocedureavvio tipiprocedureavvio, BindingResult result,
	    SessionStatus status) {

	if (tipiprocedureavvio.getTipoMovimento() != null && tipiprocedureavvio.getTipoMovimento().getId().getTipomovimento() != null) {
	    Tipimovimento tipoMovimento = tipiMovimentoService.findById(tipiprocedureavvio.getTipoMovimento().getId());
	    tipiprocedureavvio.setTipoMovimento(tipoMovimento);
	    TipiprocedureavvioId id = tipiprocedureavvio.getId();
	    id.setTipomovimento(tipoMovimento.getId().getTipomovimento());
	}
	fixMergeTipiprocedureavvioProperty(tipiprocedureavvio);
	try {
	    tipiprocedureavvioService.update(tipiprocedureavvio);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiprocedureavvio, e);
	    fixRenderTipiprocedureavvioProperty(tipiprocedureavvio);
	    return "tipiprocedure/formMovimentoAvvio";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiprocedureavvio.getTipoProcedura().getId().getCodice();
    }

    @RequestMapping
    public String eliminaMovimentoAvvio(@RequestParam("codiceprocedura") Integer codiceprocedura,
	    @RequestParam("codicemovimento") String codicemovimento, Model model, @ModelAttribute("tipiprocedure") Tipiprocedure tipiprocedure,
	    BindingResult result, SessionStatus status) {

	TipiprocedureavvioId id = new TipiprocedureavvioId();
	id.setCodiceprocedura(codiceprocedura);
	id.setTipomovimento(codicemovimento);
	Tipiprocedureavvio tipiprocedureavvio = tipiprocedureavvioService.findById(id);
	try {
	    tipiprocedureavvioService.delete(tipiprocedureavvio);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiprocedureavvio, e);
	    fixRenderTipiprocedureavvioProperty(tipiprocedureavvio);
	    return "tipiprocedure/form";
	}
	return "redirect:view.htm?codice=" + tipiprocedureavvio.getTipoProcedura().getId().getCodice();
    }

    @RequestMapping
    public ModelMap listsubprocedure(@RequestParam("codicetipoprocedura") Integer codicetipoprocedura, HttpServletRequest request,
	    HttpServletResponse response) {

	Tipiprocedure tipiprocedure = tipiprocedureService.findById(new PkId(codicetipoprocedura));
	Set<Subprocedure> subprocedureList = tipiprocedure.getSubprocedures();
	ModelMap model = new ModelMap(subprocedureList);
	boolean export = createJMesaExport(request, response, subprocedureList);
	if (export) {
	    return null;
	}
	model.addAttribute("subprocedureList", subprocedureList);
	model.addAttribute("tipiprocedure", tipiprocedure);
	return model;
    }

    @RequestMapping
    public String createSubprocedura(Model model, @RequestParam("codicetipoprocedura") Integer codicetipoprocedura) {

	Subprocedure subprocedure = new Subprocedure();
	Tipiprocedure tipiprocedure = tipiprocedureService.findById(new PkId(codicetipoprocedura));
	Integer maxOrdine = tipiprocedure.getSubprocedures().size() + 1;
	subprocedure.setNumerosubprocedura(maxOrdine.shortValue());
	subprocedure.setTipiprocedura(tipiprocedure);
	fixRenderSubprocedureProperty(subprocedure);
	model.addAttribute("subprocedure", subprocedure);
	model.addAttribute("tipiprocedure", tipiprocedure);
	setPageAttributes(model);
	return "tipiprocedure/formSubprocedure";
    }

    @RequestMapping
    public String insertSubprocedura(Model model, @ModelAttribute("subprocedure") Subprocedure subprocedure, BindingResult result,
	    SessionStatus status) {

	if (result.hasErrors()) {
	    return "tipiprocedure/formSubprocedure";
	}
	fixMergeSubprocedureProperty(subprocedure);
	try {
	    subprocedureService.insert(subprocedure);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, subprocedure, e);
	    fixRenderSubprocedureProperty(subprocedure);
	    model.addAttribute("tipiprocedure", subprocedure.getTipiprocedura());
	    return "tipiprocedure/formSubprocedure";
	}
	status.setComplete();
	return "redirect:viewSubprocedura.htm?codicesubprocedura=" + subprocedure.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String viewSubprocedura(@RequestParam("codicesubprocedura") Integer codicesubprocedura, Model model, HttpServletRequest request) {

	PkId id = new PkId(codicesubprocedura);
	Subprocedure subprocedure = subprocedureService.findById(id);
	fixRenderSubprocedureProperty(subprocedure);
	model.addAttribute("subprocedure", subprocedure);
	model.addAttribute("tipiprocedure", subprocedure.getTipiprocedura());
	setPageAttributes(model);
	return "tipiprocedure/formSubprocedure";
    }

    @RequestMapping
    public String updateSubprocedura(Model model, @ModelAttribute("subprocedure") Subprocedure subprocedure, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeSubprocedureProperty(subprocedure);
	if (result.hasErrors()) {
	    return "tipiprocedure/formSubprocedure";
	}
	try {
	    subprocedureService.update(subprocedure);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, subprocedure, e);
	    fixRenderSubprocedureProperty(subprocedure);
	    model.addAttribute("tipiprocedure", subprocedure.getTipiprocedura());
	    return "tipiprocedure/formSubprocedure";
	}
	status.setComplete();
	return "redirect:viewSubprocedura.htm?codicesubprocedura=" + subprocedure.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteSubprocedura(Model model, @ModelAttribute("subprocedure") Subprocedure subprocedure, BindingResult result,
	    SessionStatus status) {

	Subprocedure objToDelete = subprocedureService.findById(subprocedure.getId());
	try {
	    subprocedureService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderSubprocedureProperty(subprocedure);
	    model.addAttribute("tipiprocedure", subprocedure.getTipiprocedura());
	    return "tipiprocedure/formSubprocedure";
	}
	status.setComplete();
	return "redirect:listsubprocedure.htm?codicetipoprocedura=" + objToDelete.getTipiprocedura().getId().getCodice();
    }

    @RequestMapping
    public ModelMap listdocumenti(@RequestParam("codicetipoprocedura") Integer codicetipoprocedura, HttpServletRequest request,
	    HttpServletResponse response) {

	Tipiprocedure tipiprocedure = tipiprocedureService.findById(new PkId(codicetipoprocedura));
	Set<TipiprocedureDocumenti> tipiproceduredocumentiList = tipiprocedure.getTipiprocedureDocumentis();
	ModelMap model = new ModelMap(tipiproceduredocumentiList);
	model.addAttribute("tipiproceduredocumentiList", tipiproceduredocumentiList);
	model.addAttribute("tipiprocedure", tipiprocedure);
	return model;
    }

    @RequestMapping
    public String createDocumenti(Model model, @RequestParam("codicetipoprocedura") Integer codicetipoprocedura) {

	/*
	 * Creo oggetto Tipodowload e popolo la lista di AlberoprocDocumenti
	 */
	List<TipoDownload> tipoDownloads = TipoDownload.getTipoDownloads();
	model.addAttribute("tipoDownloads", tipoDownloads);
	TipiprocedureDocumenti tipiprocedureDocumenti = new TipiprocedureDocumenti();
	Tipiprocedure tipiprocedure = tipiprocedureService.findById(new PkId(codicetipoprocedura));
	tipiprocedureDocumenti.setTipiprocedura(tipiprocedure);
	fixRenderTipiprocedureDocumentiProperty(tipiprocedureDocumenti);
	model.addAttribute("tipiproceduredocumenti", tipiprocedureDocumenti);
	model.addAttribute("tipiprocedure", tipiprocedure);
	setPageAttributes(model);
	return "tipiprocedure/formDocumenti";
    }

    @RequestMapping
    public String insertDocumenti(Model model, @ModelAttribute("tipiproceduredocumenti") TipiprocedureDocumenti tipiprocedureDocumenti,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	if (tipiprocedureDocumenti.getOggetto() != null && tipiprocedureDocumenti.getOggetto().getId() != null) {
	    Oggetti oggetto = oggettiService.findById(tipiprocedureDocumenti.getOggetto().getId());
	    tipiprocedureDocumenti.setOggetto(oggetto);
	}
	fixMergeTipiprocedureDocumentiProperty(tipiprocedureDocumenti);
	String[] valueType = request.getParameterValues("tipoDownloads");
	try {
	    String foTipiDownload = "";
	    if (valueType != null) {
		for (String type : valueType) {
		    foTipiDownload = foTipiDownload.concat(type) + ",";
		}
		tipiprocedureDocumenti.setFoTipodownload(foTipiDownload);
	    } else {
		tipiprocedureDocumenti.setFoTipodownload(null);
	    }
	    tipiprocedureDocumentiService.insert(tipiprocedureDocumenti);
	} catch (Exception e) {
	    /*
	     * Creo oggetto Tipodowload e popolo la lista di AlberoprocDocumenti
	     */
	    List<TipoDownload> tipoDownloads = TipoDownload.getTipoDownloads();
	    model.addAttribute("tipoDownloads", tipoDownloads);
	    copyErrorsToBindingResult(result, tipiprocedureDocumenti, e);
	    fixRenderTipiprocedureDocumentiProperty(tipiprocedureDocumenti);
	    model.addAttribute("tipiprocedure", tipiprocedureDocumenti.getTipiprocedura());
	    return "tipiprocedure/formDocumenti";
	}
	status.setComplete();
	return "redirect:viewDocumenti.htm?codicedocumento=" + tipiprocedureDocumenti.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String viewDocumenti(@RequestParam("codicedocumento") Integer codicedocumento, Model model, HttpServletRequest request) {

	/*
	 * Creo oggetto Tipodowload e popolo la lista di AlberoprocDocumenti
	 */
	List<TipoDownload> tipoDownloads = TipoDownload.getTipoDownloads();
	model.addAttribute("tipoDownloads", tipoDownloads);
	TipiprocedureDocumenti tipiprocedureDocumenti = tipiprocedureDocumentiService.findById(new PkId(codicedocumento));
	if (tipiprocedureDocumenti.getFoTipodownload() != null) {
	    String[] tipodown = tipiprocedureDocumenti.getFoTipodownload().split(",");
	    Set<TipoDownload> tipoDownloadList = TipoDownload.fromString(tipodown, tipoDownloads);
	    tipiprocedureDocumenti.setTipoDownloads(tipoDownloadList);
	}
	fixRenderTipiprocedureDocumentiProperty(tipiprocedureDocumenti);
	model.addAttribute("tipiproceduredocumenti", tipiprocedureDocumenti);
	model.addAttribute("tipiprocedure", tipiprocedureDocumenti.getTipiprocedura());
	setPageAttributes(model);
	return "tipiprocedure/formDocumenti";
    }

    @RequestMapping
    public ModelMap listmodelli(@RequestParam("codicetipoprocedura") Integer codicetipoprocedura, HttpServletRequest request,
	    HttpServletResponse response) {

	Tipiprocedure tipiprocedure = tipiprocedureService.findById(new PkId(codicetipoprocedura));
	//Set<TipiprocedureDyn2modellit> tipiprocedureDyn2modellits = tipiprocedure.getTipiprocedureDyn2modellits();
	List<TipiprocedureDyn2modellit> tipiprocedureDyn2modellits = tipiprocedureDyn2modellitService.findByTipoprocedura(codicetipoprocedura);
	ModelMap model = new ModelMap(tipiprocedureDyn2modellits);
	boolean export = createJMesaExport(request, response, tipiprocedureDyn2modellits);
	if (export) {
	    return null;
	}
	model.addAttribute("tipiprocedureDyn2modellits", tipiprocedureDyn2modellits);
	model.addAttribute("tipiprocedure", tipiprocedure);
	return model;
    }

    @RequestMapping
    public String viewModelli(@RequestParam("codicetipoprocedura") Integer codicetipoprocedura, @RequestParam("codicemodello") Integer codicemodello,
	    Model model) {

	TipiprocedureDyn2modellitId id = new TipiprocedureDyn2modellitId(codicetipoprocedura, codicemodello);
	TipiprocedureDyn2modellit tipiprocedureDyn2modellit = tipiprocedureDyn2modellitService.findById(id);
	fixRenderTipiprocedureDyn2modellitProperty(tipiprocedureDyn2modellit);
	Boolean flagmultiplo = Boolean.TRUE;
	if (tipiprocedureDyn2modellit.getFlagTipofirma() != null && tipiprocedureDyn2modellit.getFlagTipofirma().equals(2)) {
	    if (EntityUtils.getNestedProperty(tipiprocedureDyn2modellit.getDyn2Modellit(), "id.codice") != null) {
		if (tipiprocedureDyn2modellit.getDyn2Modellit().getDyn2Modellids() != null
			&& !tipiprocedureDyn2modellit.getDyn2Modellit().getDyn2Modellids().isEmpty()) {
		    Set<Dyn2Modellid> dyn2Modellids = tipiprocedureDyn2modellit.getDyn2Modellit().getDyn2Modellids();
		    for (Dyn2Modellid dyn2Modellid : dyn2Modellids) {
			if (dyn2Modellid.getFlgMultiplo() == null || !dyn2Modellid.getFlgMultiplo()) {
			    flagmultiplo = Boolean.FALSE;
			}
		    }
		}
	    }
	}
	if (!flagmultiplo) {
	    model.addAttribute("flagmultiplo", flagmultiplo);
	}
	model.addAttribute("view", Boolean.TRUE);
	model.addAttribute("tipiprocedureDyn2modellit", tipiprocedureDyn2modellit);
	setPageAttributes(model);
	return "tipiprocedure/formModelli";
    }

    @RequestMapping
    public String createmodelli(@RequestParam("codicetipoprocedura") Integer codice, Model model) {

	Tipiprocedure tipiprocedure = tipiprocedureService.findById(new PkId(codice));
	TipiprocedureDyn2modellit tipiprocedureDyn2modellit = new TipiprocedureDyn2modellit();
	tipiprocedureDyn2modellit.setTipiprocedure(tipiprocedure);
	TipiprocedureDyn2modellitId id = new TipiprocedureDyn2modellitId();
	id.setFkCodiceprocedura(codice);
	tipiprocedureDyn2modellit.setId(id);
	fixRenderTipiprocedureDyn2modellitProperty(tipiprocedureDyn2modellit);
	model.addAttribute("tipiprocedureDyn2modellit", tipiprocedureDyn2modellit);
	setPageAttributes(model);
	return "tipiprocedure/formModelli";
    }

    @RequestMapping
    public String insertModelli(Model model, @ModelAttribute("tipiprocedureDyn2modellit") TipiprocedureDyn2modellit tipiprocedureDyn2modellit,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "tipiprocedure/formModelli";
	}
	// recupero i campi ajax
	if (tipiprocedureDyn2modellit.getDyn2Modellit() != null && tipiprocedureDyn2modellit.getDyn2Modellit().getId().getCodice() != null) {
	    Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(tipiprocedureDyn2modellit.getDyn2Modellit().getId());
	    TipiprocedureDyn2modellitId id = tipiprocedureDyn2modellit.getId();
	    id.setFkD2mtId(tipiprocedureDyn2modellit.getDyn2Modellit().getId().getCodice());
	    tipiprocedureDyn2modellit.setId(id);
	    tipiprocedureDyn2modellit.setDyn2Modellit(dyn2Modellit);
	}
	fixMergeTipiprocedureDyn2modellitProperty(tipiprocedureDyn2modellit);
	try {
	    tipiprocedureDyn2modellitService.insert(tipiprocedureDyn2modellit);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiprocedureDyn2modellit, e);
	    fixRenderTipiprocedureDyn2modellitProperty(tipiprocedureDyn2modellit);
	    model.addAttribute("tipiprocedureDyn2modellit", tipiprocedureDyn2modellit);
	    return "tipiprocedure/formModelli";
	}
	status.setComplete();
	return "redirect:viewModelli.htm?codicetipoprocedura=" +
		tipiprocedureDyn2modellit.getTipiprocedure().getId().getCodice() +
		"&codicemodello=" +
		tipiprocedureDyn2modellit.getId().getFkD2mtId() +
		"&status_msg=01";
    }

    @RequestMapping
    public String updateModelli(Model model, @ModelAttribute("tipiprocedureDyn2modellit") TipiprocedureDyn2modellit tipiprocedureDyn2modellit,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "alberoproc/formModelli";
	}
	fixMergeTipiprocedureDyn2modellitProperty(tipiprocedureDyn2modellit);
	try {
	    tipiprocedureDyn2modellitService.update(tipiprocedureDyn2modellit);
	} catch (Exception e) {
	    model.addAttribute("view", Boolean.TRUE);
	    copyErrorsToBindingResult(result, tipiprocedureDyn2modellit, e);
	    model.addAttribute("tipiprocedureDyn2modellit", tipiprocedureDyn2modellit);
	    fixRenderTipiprocedureDyn2modellitProperty(tipiprocedureDyn2modellit);
	    return "tipiprocedure/formModelli";
	}
	status.setComplete();
	return "redirect:viewModelli.htm?codicetipoprocedura=" +
		tipiprocedureDyn2modellit.getTipiprocedure().getId().getCodice() +
		"&codicemodello=" +
		tipiprocedureDyn2modellit.getId().getFkD2mtId() +
		"&status_msg=02";
    }

    @RequestMapping
    public String deleteModelli(@ModelAttribute("tipiprocedureDyn2modellit") TipiprocedureDyn2modellit tipiprocedureDyn2modellit,
	    BindingResult result, SessionStatus status) {

	TipiprocedureDyn2modellit objToDelete = tipiprocedureDyn2modellitService.findById(tipiprocedureDyn2modellit.getId());
	try {
	    tipiprocedureDyn2modellitService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderTipiprocedureDyn2modellitProperty(objToDelete);
	    return "tipiprocedure/formModelli";
	}
	status.setComplete();
	return "redirect:listmodelli.htm?codicetipoprocedura=" + objToDelete.getTipiprocedure().getId().getCodice();
    }

    @RequestMapping
    public String updateDocumenti(Model model, @ModelAttribute("tipiproceduredocumenti") TipiprocedureDocumenti tipiprocedureDocumenti,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	if (tipiprocedureDocumenti.getOggetto() != null && tipiprocedureDocumenti.getOggetto().getId() != null) {
	    Oggetti oggetto = oggettiService.findById(tipiprocedureDocumenti.getOggetto().getId());
	    tipiprocedureDocumenti.setOggetto(oggetto);
	}
	fixMergeTipiprocedureDocumentiProperty(tipiprocedureDocumenti);
	String[] valueType = request.getParameterValues("tipoDownloads");
	try {
	    String foTipiDownload = "";
	    if (valueType != null) {
		for (String type : valueType) {
		    foTipiDownload = foTipiDownload.concat(type) + ",";
		}
		tipiprocedureDocumenti.setFoTipodownload(foTipiDownload);
	    } else {
		tipiprocedureDocumenti.setFoTipodownload(null);
	    }
	    tipiprocedureDocumentiService.update(tipiprocedureDocumenti);
	} catch (Exception e) {
	    /*
	     * Creo oggetto Tipodowload e popolo la lista di AlberoprocDocumenti
	     */
	    List<TipoDownload> tipoDownloads = TipoDownload.getTipoDownloads();
	    model.addAttribute("tipoDownloads", tipoDownloads);
	    copyErrorsToBindingResult(result, tipiprocedureDocumenti, e);
	    fixRenderTipiprocedureDocumentiProperty(tipiprocedureDocumenti);
	    model.addAttribute("tipiprocedure", tipiprocedureDocumenti.getTipiprocedura());
	    return "tipiprocedure/formDocumenti";
	}
	status.setComplete();
	return "redirect:viewDocumenti.htm?codicedocumento=" + tipiprocedureDocumenti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteDocumenti(@RequestParam("codicedocumento") Integer codicedocumento) {

	TipiprocedureDocumenti objToDelete = tipiprocedureDocumentiService.findById(new PkId(codicedocumento));
	// try {
	tipiprocedureDocumentiService.delete(objToDelete);
	// } catch (Exception e) {
	// copyErrorsToBindingResult(subprocedureService.getValidationMessages(), result, objToDelete, e.getMessage());
	// fixRenderSubprocedureProperty(subprocedure);
	// model.addAttribute("tipiprocedure", subprocedure.getTipiprocedura());
	// return "tipiprocedure/formDocumenti";
	// }
	// status.setComplete();
	return "redirect:listdocumenti.htm?codicetipoprocedura=" + objToDelete.getTipiprocedura().getId().getCodice();
    }

    @RequestMapping
    public void documentoDownload(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileId") Integer id,
	    @RequestParam("codicetipoprocedura") Integer codiceprocedura) throws IOException {

	Oggetti oggetto = oggettiService.findById(new PkId(id));
	if (oggetto != null) {
	    byte[] b = oggetto.getOggetto();
	    String filename = oggetto.getNomefile();
	    if (filename != null && !filename.equals("")) {
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		response.setHeader("Content-transfer-encoding", "binary");
		String ext = filename.substring(filename.lastIndexOf('.') + 1);
		String cType = contenttypesService.findMimeTypeByFileName(filename);
		response.setContentType(cType);
		response.setContentLength(b.length);
		ServletOutputStream out = response.getOutputStream();
		out.write(b);
		out.flush();
		out.close();
	    } else {
		throw new RuntimeException("File senza nome. (id=" + id + ")");
	    }
	} else {
	    throw new RuntimeException("File non trovato. (id=" + id + ")");
	}
    }

    @RequestMapping
    public void ajaxGrafico(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) throws IOException {

	response.setHeader("Pragma", "public");
	response.setHeader("Cache-Control", "max-age=0");
	// response.setHeader("Content-Disposition", "attachment; filename=" + filename);
	response.setHeader("Content-transfer-encoding", "binary");
	SessionDetails sessionDetails = getSessionDetails(request);
	byte[] filecontent = SigeprorendererWSClient.generaGraficoDaDBConToken(sessionDetails.getToken(), codice);
	String cType = contenttypesService.findMimeTypeByFileName("temp.gif");
	response.setContentType(cType);
	response.setContentLength(filecontent.length);
	ServletOutputStream out = response.getOutputStream();
	out.write(filecontent);
	out.flush();
	out.close();
    }

    @RequestMapping
    public String aggiornaDataInizioProcedura(@RequestParam("codice") Integer codice, @ModelAttribute("tipiprocedure") Tipiprocedure tipiprocedure,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	tipiprocedure = tipiprocedureService.findById(new PkId(codice));
	try {
	    istanzeService.updateDataInizioIstanzaPerProcedura(tipiprocedure, WebConstants.NUMERO_ISTANZE_AGGIORNABILI_PER_CICLO_DALLA_PROCEDURA);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiprocedure, e);
	    fixRenderEntityProperty(tipiprocedure);
	    return "tipiprocedure/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiprocedure.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String aggiornaDataValiditaProcedura(@RequestParam("codice") Integer codice, @ModelAttribute("tipiprocedure") Tipiprocedure tipiprocedure,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	tipiprocedure = tipiprocedureService.findById(new PkId(codice));
	try {
	    istanzeService.updateDataValiditaIstanzaPerProcedura(tipiprocedure, WebConstants.NUMERO_ISTANZE_AGGIORNABILI_PER_CICLO_DALLA_PROCEDURA);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiprocedure, e);
	    fixRenderEntityProperty(tipiprocedure);
	    return "tipiprocedure/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiprocedure.getId().getCodice() + "&status_msg=02";
    }

    @Override
    protected void fixMergeEntityProperty(Tipiprocedure entity) {

	if (entity.getNaturaendo() != null && entity.getNaturaendo().getId() != null && entity.getNaturaendo().getId().getCodice() == null) {
	    entity.setNaturaendo(null);
	}
	if (entity.getLetteretipo() != null && entity.getLetteretipo().getId() != null && entity.getLetteretipo().getId().getCodice() == null) {
	    entity.setLetteretipo(null);
	}
	if (entity.getTipimovimentoCds() != null && entity.getTipimovimentoCds().getId() != null
		&& (entity.getTipimovimentoCds().getId().getTipomovimento() == null
			|| entity.getTipimovimentoCds().getId().getTipomovimento().equals(""))) {
	    entity.setTipimovimentoCds(null);
	}
	if (entity.getTipimovimentoChiusuraCds() != null && entity.getTipimovimentoChiusuraCds().getId() != null
		&& (entity.getTipimovimentoChiusuraCds().getId().getTipomovimento() == null
			|| entity.getTipimovimentoChiusuraCds().getId().getTipomovimento().equals(""))) {
	    entity.setTipimovimentoChiusuraCds(null);
	}
	if (entity.getTipimovimentoChiusuraContraddittorio() != null && entity.getTipimovimentoChiusuraContraddittorio().getId() != null
		&& (entity.getTipimovimentoChiusuraContraddittorio().getId().getTipomovimento() == null
			|| entity.getTipimovimentoChiusuraContraddittorio().getId().getTipomovimento().equals(""))) {
	    entity.setTipimovimentoChiusuraContraddittorio(null);
	}
	if (entity.getTipimovimentoChiusura() != null && entity.getTipimovimentoChiusura().getId() != null
		&& (entity.getTipimovimentoChiusura().getId().getTipomovimento() == null
			|| entity.getTipimovimentoChiusura().getId().getTipomovimento().equals(""))) {
	    entity.setTipimovimentoChiusura(null);
	}
	if (entity.getTipimovimentoEsitoAut() != null && entity.getTipimovimentoEsitoAut().getId() != null
		&& (entity.getTipimovimentoEsitoAut().getId().getTipomovimento() == null
			|| entity.getTipimovimentoEsitoAut().getId().getTipomovimento().equals(""))) {
	    entity.setTipimovimentoEsitoAut(null);
	}
	if (entity.getTipimovimentoIntegrazioneDocumentale() != null && entity.getTipimovimentoIntegrazioneDocumentale().getId() != null
		&& (entity.getTipimovimentoIntegrazioneDocumentale().getId().getTipomovimento() == null
			|| entity.getTipimovimentoIntegrazioneDocumentale().getId().getTipomovimento().equals(""))) {
	    entity.setTipimovimentoIntegrazioneDocumentale(null);
	}
	if (entity.getTipimovimentoPubblicita() != null && entity.getTipimovimentoPubblicita().getId() != null
		&& (entity.getTipimovimentoPubblicita().getId().getTipomovimento() == null
			|| entity.getTipimovimentoPubblicita().getId().getTipomovimento().equals(""))) {
	    entity.setTipimovimentoPubblicita(null);
	}
	if (entity.getTipimovimentoSospensione() != null && entity.getTipimovimentoSospensione().getId() != null
		&& (entity.getTipimovimentoSospensione().getId().getTipomovimento() == null
			|| entity.getTipimovimentoSospensione().getId().getTipomovimento().equals(""))) {
	    entity.setTipimovimentoSospensione(null);
	}
	if (entity.getTipimovimentoTrasmissioneNegativa() != null && entity.getTipimovimentoTrasmissioneNegativa().getId() != null
		&& (entity.getTipimovimentoTrasmissioneNegativa().getId().getTipomovimento() == null
			|| entity.getTipimovimentoTrasmissioneNegativa().getId().getTipomovimento().equals(""))) {
	    entity.setTipimovimentoTrasmissioneNegativa(null);
	}
	if (entity.getTipimovimentoDeterminazione() != null && entity.getTipimovimentoDeterminazione().getId() != null
		&& (entity.getTipimovimentoDeterminazione().getId().getTipomovimento() == null
			|| entity.getTipimovimentoDeterminazione().getId().getTipomovimento().equals(""))) {
	    entity.setTipimovimentoDeterminazione(null);
	}
	if (entity.getTipimovimentoConsiglioDeiMinistri() != null && entity.getTipimovimentoConsiglioDeiMinistri().getId() != null
		&& (entity.getTipimovimentoConsiglioDeiMinistri().getId().getTipomovimento() == null
			|| entity.getTipimovimentoConsiglioDeiMinistri().getId().getTipomovimento().equals(""))) {
	    entity.setTipimovimentoConsiglioDeiMinistri(null);
	}
	if (entity.getOggettoModello() != null && entity.getOggettoModello() != null && entity.getOggettoModello().getId().getCodice() == null) {
	    entity.setOggettoModello(null);
	}
	if (entity.getOggettoModelloDiagramma() != null && entity.getOggettoModelloDiagramma() != null
		&& entity.getOggettoModelloDiagramma().getId().getCodice() == null) {
	    entity.setOggettoModelloDiagramma(null);
	}
	if (entity.getOggettoModelloDomandaOnline() != null && entity.getOggettoModelloDomandaOnline() != null
		&& entity.getOggettoModelloDomandaOnline().getId().getCodice() == null) {
	    entity.setOggettoModelloDomandaOnline(null);
	}
	if (entity.getOggettoModelloPrecompilato() != null && entity.getOggettoModelloPrecompilato() != null
		&& entity.getOggettoModelloPrecompilato().getId().getCodice() == null) {
	    entity.setOggettoModelloPrecompilato(null);
	}
	if (entity.getRiTipiprocedimento() != null && StringUtils.isBlank(entity.getRiTipiprocedimento().getCodice())) {
	    entity.setRiTipiprocedimento(null);
	}
    }

    private void fixMergeTipiprocedureDyn2modellitProperty(TipiprocedureDyn2modellit entity) {

	if (entity.getTipiprocedure() != null && entity.getTipiprocedure().getId() != null && entity.getTipiprocedure().getId().getCodice() == null) {
	    entity.setTipiprocedure(null);
	}
	if (entity.getDyn2Modellit() != null && entity.getDyn2Modellit().getId() != null && entity.getDyn2Modellit().getId().getCodice() == null) {
	    entity.setDyn2Modellit(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Tipiprocedure entity) {

	if (entity.getTipimovimentoCds() == null) {
	    entity.setTipimovimentoCds(new Tipimovimento());
	}
	if (entity.getTipimovimentoAudizioneContraddittorio() == null) {
	    entity.setTipimovimentoAudizioneContraddittorio(new Tipimovimento());
	}
	if (entity.getTipimovimentoChiusuraCds() == null) {
	    entity.setTipimovimentoChiusuraCds(new Tipimovimento());
	}
	if (entity.getTipimovimentoChiusuraContraddittorio() == null) {
	    entity.setTipimovimentoChiusuraContraddittorio(new Tipimovimento());
	}
	if (entity.getTipimovimentoChiusura() == null) {
	    entity.setTipimovimentoChiusura(new Tipimovimento());
	}
	if (entity.getTipimovimentoDeterminazione() == null) {
	    entity.setTipimovimentoDeterminazione(new Tipimovimento());
	}
	if (entity.getTipimovimentoEsitoAut() == null) {
	    entity.setTipimovimentoEsitoAut(new Tipimovimento());
	}
	if (entity.getTipimovimentoIntegrazioneDocumentale() == null) {
	    entity.setTipimovimentoIntegrazioneDocumentale(new Tipimovimento());
	}
	if (entity.getTipimovimentoPubblicita() == null) {
	    entity.setTipimovimentoPubblicita(new Tipimovimento());
	}
	if (entity.getTipimovimentoSospensione() == null) {
	    entity.setTipimovimentoSospensione(new Tipimovimento());
	}
	if (entity.getTipimovimentoTrasmissioneNegativa() == null) {
	    entity.setTipimovimentoTrasmissioneNegativa(new Tipimovimento());
	}
	if (entity.getTipimovimentoConsiglioDeiMinistri() == null) {
	    entity.setTipimovimentoConsiglioDeiMinistri(new Tipimovimento());
	}
	if (entity.getLetteretipo() == null) {
	    entity.setLetteretipo(new Letteretipo());
	}
	if (entity.getNaturaendo() == null) {
	    entity.setNaturaendo(new Naturaendo());
	}
	if (entity.getOggettoModello() == null) {
	    entity.setOggettoModello(new Oggetti());
	}
	if (entity.getOggettoModelloDiagramma() == null) {
	    entity.setOggettoModelloDiagramma(new Oggetti());
	}
	if (entity.getOggettoModelloDomandaOnline() == null) {
	    entity.setOggettoModelloDomandaOnline(new Oggetti());
	}
	if (entity.getOggettoModelloPrecompilato() == null) {
	    entity.setOggettoModelloPrecompilato(new Oggetti());
	}
	if (entity.getOggettoCertificatoInvio() == null) {
	    entity.setOggettoCertificatoInvio(new Oggetti());
	}
	if (entity.getRiTipiprocedimento() == null) {
	    entity.setRiTipiprocedimento(new RiTipiprocedimento());
	}
	if (entity.getOggettoRicevutaCart() == null) {
	    entity.setOggettoRicevutaCart(new Oggetti());
	}
	if (entity.getStatochiusuraistanza() == null) {
	    entity.setStatochiusuraistanza(new Statiistanza());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	boolean isCart = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	model.addAttribute("CART_ATTIVO", Boolean.valueOf(isCart));
	List<Statiistanza> statiistanzaList = statiistanzaService.findByStatocomportamentoChiuse();
	model.addAttribute("statiistanzaList", statiistanzaList);
    }

    protected void fixMergeTipiprocedureavvioProperty(Tipiprocedureavvio entity) {

	if (entity.getTipoProcedura() != null && entity.getTipoProcedura().getId() != null && entity.getTipoProcedura().getId().getCodice() == null) {
	    entity.setTipoProcedura(null);
	}
	if (entity.getTipoMovimento() != null && entity.getTipoMovimento().getId() != null
		&& (entity.getTipoMovimento().getId().getTipomovimento() == null
			|| entity.getTipoMovimento().getId().getTipomovimento().equals(""))) {
	    entity.setTipoMovimento(null);
	}
    }

    protected void fixRenderTipiprocedureavvioProperty(Tipiprocedureavvio entity) {

	if (entity.getTipoMovimento() == null) {
	    entity.setTipoMovimento(new Tipimovimento());
	}
	if (entity.getTipoProcedura() == null) {
	    entity.setTipoProcedura(new Tipiprocedure());
	}
    }

    protected void fixMergeSubprocedureProperty(Subprocedure entity) {

	if (entity.getTipiprocedura() != null && entity.getTipiprocedura().getId() != null && entity.getTipiprocedura().getId().getCodice() == null) {
	    entity.setTipiprocedura(null);
	}
    }

    protected void fixRenderSubprocedureProperty(Subprocedure entity) {

	if (entity.getTipiprocedura() == null) {
	    entity.setTipiprocedura(new Tipiprocedure());
	}
    }

    protected void fixMergeTipiprocedureDocumentiProperty(TipiprocedureDocumenti entity) {

	if (entity.getTipiprocedura() != null && entity.getTipiprocedura().getId() != null && entity.getTipiprocedura().getId().getCodice() == null) {
	    entity.setTipiprocedura(null);
	}
	if (entity.getOggetto() != null && entity.getOggetto() != null && entity.getOggetto().getId().getCodice() == null) {
	    entity.setOggetto(null);
	}
    }

    protected void fixRenderTipiprocedureDocumentiProperty(TipiprocedureDocumenti entity) {

	if (entity.getTipiprocedura() == null) {
	    entity.setTipiprocedura(new Tipiprocedure());
	}
	if (entity.getOggetto() == null) {
	    entity.setOggetto(new Oggetti());
	}
    }

    protected void fixRenderTipiprocedureDyn2modellitProperty(TipiprocedureDyn2modellit entity) {

	if (entity.getTipiprocedure() == null) {
	    entity.setTipiprocedure(new Tipiprocedure());
	}
	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
    }

    /**
     * Il metodo prende in ingresso un oggetto tipi procedure e popola tutti i campi che vengono passaita dal form
     * tramite o un campo ajax (è stato creato per evitare di duplicare codice nei controller insert e update)
     * 
     * @param entity
     * @return
     */
    private Tipiprocedure findAjaxField(Tipiprocedure entity, HttpServletRequest request) {

	//	if (entity.getOggettoModello() != null && entity.getOggettoModello().getId().getCodice() != null) {
	//	    Oggetti oggettoModello = oggettiService.findById(entity.getOggettoModello().getId());
	//	    entity.setOggettoModello(oggettoModello);
	//	}
	//	if (entity.getOggettoModelloDiagramma() != null && entity.getOggettoModelloDiagramma().getId().getCodice() != null) {
	//	    Oggetti oggettoModelloDiagramma = oggettiService.findById(entity.getOggettoModelloDiagramma().getId());
	//	    entity.setOggettoModelloDiagramma(oggettoModelloDiagramma);
	//	}
	//	if (entity.getOggettoModelloDomandaOnline() != null && entity.getOggettoModelloDomandaOnline().getId().getCodice() != null) {
	//	    Oggetti oggettoOggettoModelloDomandaOnline = oggettiService.findById(entity.getOggettoModelloDomandaOnline().getId());
	//	    entity.setOggettoModelloDomandaOnline(oggettoOggettoModelloDomandaOnline);
	//	}
	//	if (entity.getOggettoModelloPrecompilato() != null && entity.getOggettoModelloPrecompilato().getId().getCodice() != null) {
	//	    Oggetti oggettoOggettoModelloPrecompilato = oggettiService.findById(entity.getOggettoModelloPrecompilato().getId());
	//	    entity.setOggettoModelloPrecompilato(oggettoOggettoModelloPrecompilato);
	//	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("oggettoModello.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("oggettoModello.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Oggetti oggettoModello = oggettiService.findById(codiceId);
	    entity.setOggettoModello(oggettoModello);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("oggettoModelloDiagramma.id.getCodice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("oggettoModelloDiagramma.id.getCodice"));
	    PkId codiceId = new PkId(codice);
	    Oggetti oggettoModelloDiagramma = oggettiService.findById(codiceId);
	    entity.setOggettoModelloDiagramma(oggettoModelloDiagramma);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("oggettoModelloDomandaOnline.id.getCodice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("oggettoModelloDomandaOnline.id.getCodice"));
	    PkId codiceId = new PkId(codice);
	    Oggetti oggettoOggettoModelloDomandaOnline = oggettiService.findById(codiceId);
	    entity.setOggettoModelloDomandaOnline(oggettoOggettoModelloDomandaOnline);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("oggettoModelloPrecompilato.id.getCodice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("oggettoModelloPrecompilato.id.getCodice"));
	    PkId codiceId = new PkId(codice);
	    Oggetti oggettoOggettoModelloPrecompilato = oggettiService.findById(codiceId);
	    entity.setOggettoModelloPrecompilato(oggettoOggettoModelloPrecompilato);
	}
	if (entity.getNaturaendo() != null && entity.getNaturaendo().getId().getCodice() != null) {
	    Naturaendo naturaendo = naturaendoService.findById(entity.getNaturaendo().getId());
	    entity.setNaturaendo(naturaendo);
	}
	if (entity.getLetteretipo() != null && entity.getLetteretipo().getId().getCodice() != null) {
	    Letteretipo letteretipo = letteretipoService.findById(entity.getLetteretipo().getId());
	    entity.setLetteretipo(letteretipo);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("tipimovimentoAudizioneContraddittorio.id.tipomovimento"), "").equals("")) {
	    //	if (entity.getTipimovimentoAudizioneContraddittorio() != null
	    //		&& entity.getTipimovimentoAudizioneContraddittorio().getId().getTipomovimento() != null) {
	    String codicemovimento = request.getParameter("tipimovimentoAudizioneContraddittorio.id.tipomovimento");
	    Tipimovimento tipimovimentoAudizioneContraddittorio = tipiMovimentoService.findById(new TipimovimentoId(codicemovimento));
	    entity.setTipimovimentoAudizioneContraddittorio(tipimovimentoAudizioneContraddittorio);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("tipimovimentoCds.id.tipomovimento"), "").equals("")) {
	    //	if (entity.getTipimovimentoAudizioneContraddittorio() != null
	    //		&& entity.getTipimovimentoAudizioneContraddittorio().getId().getTipomovimento() != null) {
	    String codicemovimento = request.getParameter("tipimovimentoCds.id.tipomovimento");
	    // if (entity.getTipimovimentoCds() != null && entity.getTipimovimentoCds().getId().getTipomovimento() != null) {
	    Tipimovimento tipimovimentoCds = tipiMovimentoService.findById(new TipimovimentoId(codicemovimento));
	    entity.setTipimovimentoCds(tipimovimentoCds);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("tipimovimentoChiusuraCds.id.tipomovimento"), "").equals("")) {
	    String codicemovimento = request.getParameter("tipimovimentoChiusuraCds.id.tipomovimento");
	    // if (entity.getTipimovimentoChiusuraCds() != null && entity.getTipimovimentoChiusuraCds().getId().getTipomovimento() != null) {
	    Tipimovimento tipimovimentoChiusuraCds = tipiMovimentoService.findById(new TipimovimentoId(codicemovimento));
	    entity.setTipimovimentoChiusuraCds(tipimovimentoChiusuraCds);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("tipimovimentoChiusuraContraddittorio.id.tipomovimento"), "").equals("")) {
	    String codicemovimento = request.getParameter("tipimovimentoChiusuraContraddittorio.id.tipomovimento");
	    // if (entity.getTipimovimentoChiusuraContraddittorio() != null && entity.getTipimovimentoChiusuraContraddittorio().getId().getTipomovimento() != null) {
	    Tipimovimento tipimovimentoChiusuraContraddittorio = tipiMovimentoService.findById(new TipimovimentoId(codicemovimento));
	    entity.setTipimovimentoChiusuraContraddittorio(tipimovimentoChiusuraContraddittorio);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("tipimovimentoChiusura.id.tipomovimento"), "").equals("")) {
	    String codicemovimento = request.getParameter("tipimovimentoChiusura.id.tipomovimento");
	    // if (entity.getTipimovimentoChiusura() != null && entity.getTipimovimentoChiusura().getId().getTipomovimento() != null) {
	    Tipimovimento tipimovimentoChiusura = tipiMovimentoService.findById(new TipimovimentoId(codicemovimento));
	    entity.setTipimovimentoChiusura(tipimovimentoChiusura);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("tipimovimentoDeterminazione.id.tipomovimento"), "").equals("")) {
	    String codicemovimento = request.getParameter("tipimovimentoDeterminazione.id.tipomovimento");
	    // if (entity.getTipimovimentoDeterminazione() != null && entity.getTipimovimentoDeterminazione().getId().getTipomovimento() != null) {
	    Tipimovimento tipimovimentoDeterminazione = tipiMovimentoService.findById(new TipimovimentoId(codicemovimento));
	    entity.setTipimovimentoDeterminazione(tipimovimentoDeterminazione);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("tipimovimentoEsitoAut.id.tipomovimento"), "").equals("")) {
	    String codicemovimento = request.getParameter("tipimovimentoEsitoAut.id.tipomovimento");
	    // if (entity.getTipimovimentoEsitoAut() != null && entity.getTipimovimentoEsitoAut().getId().getTipomovimento() != null) {
	    Tipimovimento tipimovimentoEsitoAut = tipiMovimentoService.findById(new TipimovimentoId(codicemovimento));
	    entity.setTipimovimentoEsitoAut(tipimovimentoEsitoAut);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("tipimovimentoIntegrazioneDocumentale.id.tipomovimento"), "").equals("")) {
	    String codicemovimento = request.getParameter("tipimovimentoIntegrazioneDocumentale.id.tipomovimento");
	    // if (entity.getTipimovimentoIntegrazioneDocumentale() != null 		&& entity.getTipimovimentoIntegrazioneDocumentale().getId().getTipomovimento() != null) {
	    Tipimovimento tipimovimentoIntegrazioneDocumentale = tipiMovimentoService.findById(new TipimovimentoId(codicemovimento));
	    entity.setTipimovimentoIntegrazioneDocumentale(tipimovimentoIntegrazioneDocumentale);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("tipimovimentoPubblicita.id.tipomovimento"), "").equals("")) {
	    String codicemovimento = request.getParameter("tipimovimentoPubblicita.id.tipomovimento");
	    // if (entity.getTipimovimentoPubblicita() != null && entity.getTipimovimentoPubblicita().getId().getTipomovimento() != null) {
	    Tipimovimento tipimovimentoPubblicita = tipiMovimentoService.findById(new TipimovimentoId(codicemovimento));
	    entity.setTipimovimentoPubblicita(tipimovimentoPubblicita);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("tipimovimentoSospensione.id.tipomovimento"), "").equals("")) {
	    String codicemovimento = request.getParameter("tipimovimentoSospensione.id.tipomovimento");
	    // if (entity.getTipimovimentoSospensione() != null && entity.getTipimovimentoSospensione().getId().getTipomovimento() != null) {
	    Tipimovimento tipimovimentoSospensione = tipiMovimentoService.findById(new TipimovimentoId(codicemovimento));
	    entity.setTipimovimentoSospensione(tipimovimentoSospensione);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("tipimovimentoTrasmissioneNegativa.id.tipomovimento"), "").equals("")) {
	    String codicemovimento = request.getParameter("tipimovimentoTrasmissioneNegativa.id.tipomovimento");
	    //if (entity.getTipimovimentoTrasmissioneNegativa() != null && entity.getTipimovimentoTrasmissioneNegativa().getId().getTipomovimento() != null) {
	    Tipimovimento tipimovimentoTrasmissioneNegativa = tipiMovimentoService.findById(new TipimovimentoId(codicemovimento));
	    entity.setTipimovimentoTrasmissioneNegativa(tipimovimentoTrasmissioneNegativa);
	}
	if (StringUtils.isNotBlank(request.getParameter("tipimovimentoConsiglioDeiMinistri.id.tipomovimento"))) {
	    String codicemovimento = request.getParameter("tipimovimentoConsiglioDeiMinistri.id.tipomovimento");
	    Tipimovimento tipimovimentoConsiglioDeiMinistri = tipiMovimentoService.findById(new TipimovimentoId(codicemovimento));
	    entity.setTipimovimentoConsiglioDeiMinistri(tipimovimentoConsiglioDeiMinistri);
	} else {
	    entity.setTipimovimentoConsiglioDeiMinistri(null);
	}
	return entity;
    }

    @RequestMapping
    public String abilitaDisabilita(Model model, @ModelAttribute("tipiprocedure") Tipiprocedure tipiprocedure, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	tipiprocedure = tipiprocedureService.findById(tipiprocedure.getId());
	boolean disabilita = false;
	if (tipiprocedure.getFlagDisabilitato() == null || tipiprocedure.getFlagDisabilitato().booleanValue() == false) {
	    disabilita = true;
	    boolean check = tipiprocedureService.checkSeDisabilitare(tipiprocedure);
	    if (!check) {
		return "redirect:listaDipendenze.htm?codice=" + tipiprocedure.getId().getCodice();
	    }
	}
	tipiprocedure.setFlagDisabilitato(disabilita);
	try {
	    tipiprocedureService.update(tipiprocedure);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiprocedure, e);
	    fixRenderEntityProperty(tipiprocedure);
	    tipiprocedure = tipiprocedureService.findById(tipiprocedure.getId());
	    prepareViewPage(model, tipiprocedure, tipiprocedure.getId().getCodice(), request);
	    setPageAttributes(model);
	    return "tipiprocedure/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiprocedure.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String listaDipendenze(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipiprocedure tipiprocedure = tipiprocedureService.findById(id);
	fixRenderEntityProperty(tipiprocedure);
	model.addAttribute("tipiprocedure", tipiprocedure);
	//
	List<Alberoproc> alberoprocs = alberoprocService.findByTipiprocedure(tipiprocedure.getId().getCodice(), 0, 20);
	model.addAttribute("alberoprocs", alberoprocs);
	List<Tipicontromovimento> tipicontromovimentos = tipicontromovimentoService.findByTipiprocedure(tipiprocedure.getId().getCodice(), 0, 20);
	model.addAttribute("tipicontromovimentos", tipicontromovimentos);
	setPageAttributes(model);
	return "tipiprocedure/listaDipendenze";
    }
}
