package it.gruppoinit.pal.gp.backoffice.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipidocumento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentodoctipo;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo.TipimovimentodoctipoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipidocumentoService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;

@Controller
@SessionAttributes("letteretipo")
public class LetteretipoController extends BaseController<Letteretipo> {

    @Autowired
    private LetteretipoService letteretipoService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private TipidocumentoService tipidocumentoService;
    @Autowired
    private TipimovimentodoctipoService tipimovimentodoctipoService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private TipiprocedureService tipiprocedureService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Letteretipo> letteretipoList = letteretipoService.findAll(null, null);
	ModelMap model = new ModelMap(letteretipoList);
	boolean export = createJMesaExport(request, response, letteretipoList);
	if (export)
	    return null;
	model.addAttribute("letteretipoList", letteretipoList);
	Configurazione configurazione = getConfigurazione();
	if (configurazione != null) {
	    if (EntityUtils.getNestedProperty(configurazione.getOggettomoddoctipo(), "id.codice") != null) {
		model.addAttribute("codiceoggettomoddoctipo", configurazione.getOggettomoddoctipo().getId().getCodice());
	    }
	}
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("letteretipo") Letteretipo letteretipo, BindingResult result, SessionStatus status) {

	Letteretipo objToDelete = letteretipoService.findById(letteretipo.getId());
	try {
	    letteretipoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(letteretipo);
	    setPageAttributes(model);
	    return "letteretipo/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("letteretipo") Letteretipo letteretipo, BindingResult result, SessionStatus status) {

	// recupero il software corrente e lo setto in letteretipo
	Software software = softwareService.findById(ORMHelper.getSoftware());
	letteretipo.setSoftware(software);
	// recupero l'oggetto corrente e lo inserisco in letteretipo
	Oggetti oggetto = oggettiService.findById(letteretipo.getFile().getId());
	letteretipo.setFile(oggetto);
	fixMergeEntityProperty(letteretipo);
	try {
	    letteretipoService.insert(letteretipo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, letteretipo, e);
	    fixRenderEntityProperty(letteretipo);
	    setPageAttributes(model);
	    return "letteretipo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + letteretipo.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("letteretipo") Letteretipo letteretipo, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// recupero il software corrente e lo setto in letteretipo
	Software software = softwareService.findById(ORMHelper.getSoftware());
	letteretipo.setSoftware(software);
	// recupero l'oggetto corrente e lo inserisco in letteretipo
	// Oggetti oggetto = oggettiService.findById(letteretipo.getFile().getId());
	// letteretipo.setFile(oggetto);
	// fixMergeEntityProperty(letteretipo);
	try {
	    letteretipoService.update(letteretipo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, letteretipo, e);
	    fixRenderEntityProperty(letteretipo);
	    setPageAttributes(model);
	    return "letteretipo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + letteretipo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	Letteretipo letteretipo = new Letteretipo();
	letteretipo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(letteretipo);
	model.addAttribute("letteretipo", letteretipo);
	setPageAttributes(model);
	return "letteretipo/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Letteretipo letteretipo = letteretipoService.findById(id);
	fixRenderEntityProperty(letteretipo);
	model.addAttribute("letteretipo", letteretipo);
	setPageAttributes(model);
	return "letteretipo/form";
    }

    @RequestMapping
    public String abilitaDisabilita(Model model, @ModelAttribute("letteretipo") Letteretipo letteretipo, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	letteretipo = letteretipoService.findById(letteretipo.getId());
	boolean disabilita = false;
	if (letteretipo.getFlagDisabilitato() == null || letteretipo.getFlagDisabilitato().booleanValue() == false) {
	    disabilita = true;
	    boolean check = letteretipoService.checkSeDisabilitare(letteretipo);
	    if (!check) {
		return "redirect:listaDipendenze.htm?codice=" + letteretipo.getId().getCodice();
	    }
	}
	letteretipo.setFlagDisabilitato(disabilita);
	try {
	    letteretipoService.update(letteretipo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, letteretipo, e);
	    fixRenderEntityProperty(letteretipo);
	    setPageAttributes(model);
	    return "letteretipo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + letteretipo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String listaDipendenze(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Letteretipo letteretipo = letteretipoService.findById(id);
	fixRenderEntityProperty(letteretipo);
	model.addAttribute("letteretipo", letteretipo);
	List<Tipidocumento> docs = tipidocumentoService.findByLetteretipo(letteretipo, 0, 10);
	model.addAttribute("tipidocumentos", docs);
	List<Tipiprocedure> procs = tipiprocedureService.findByLetteretipo(letteretipo, 0, 10);
	model.addAttribute("tipiprocedures", procs);
	List<Tipimovimentodoctipo> tdocs = tipimovimentodoctipoService.findByLetteretipo(letteretipo, 0, 10);
	model.addAttribute("tipimovimentodoctipos", tdocs);
	List<Tipimovimento> tmovs = tipiMovimentoService.findByLetteretipo(letteretipo, 0, 10);
	model.addAttribute("tipimovimentos", tmovs);
	setPageAttributes(model);
	return "letteretipo/listaDipendenze";
    }

    @Override
    protected void fixMergeEntityProperty(Letteretipo entity) {

	if (entity.getFile() != null && entity.getFile().getId() != null && entity.getFile().getId().getCodice() == null) {
	    entity.setFile(null);
	}
	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null || entity.getSoftware().getCodice().equals(""))) {
	    entity.setSoftware(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Letteretipo entity) {

	if (entity.getFile() == null) {
	    entity.setFile(new Oggetti());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	Configurazione configurazione = getConfigurazione();
	if (configurazione != null) {
	    if (EntityUtils.getNestedProperty(configurazione.getOggettomoddoctipo(), "id.codice") != null) {
		Integer codiceoggettomoddoctipo = configurazione.getOggettomoddoctipo().getId().getCodice();
		model.addAttribute("codiceoggettomoddoctipo", codiceoggettomoddoctipo);
	    }
	}
    }

    private Configurazione getConfigurazione() {

	ConfigurazioneId id = new ConfigurazioneId();
	id.setSoftware(WebConstants.SOFTWARE_TT);
	return configurazioneService.findById(id);
    }
}
