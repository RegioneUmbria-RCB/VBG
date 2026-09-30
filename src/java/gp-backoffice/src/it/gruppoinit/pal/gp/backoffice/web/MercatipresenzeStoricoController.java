package it.gruppoinit.pal.gp.backoffice.web;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeStorico;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeStoricoService;

@Controller
@SessionAttributes("mercatipresenzeStorico")
public class MercatipresenzeStoricoController extends BaseController<MercatipresenzeStorico> {

    @Autowired
    private MercatipresenzeStoricoService mercatipresenzeStoricoService;
    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiDService mercatiDService;
    private final String PRESENZEINREQUEST = "presenzeInRequest";

    @RequestMapping
    public String listStorico(Model model, @RequestParam("autId") Integer autId, HttpServletRequest request, HttpServletResponse response) {

	MercatipresenzeStorico mercatipresenzeStorico = new MercatipresenzeStorico();
	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(autId));
	mercatipresenzeStorico.setAutorizzazioni(autorizzazioni);
	List<MercatipresenzeStorico> listStorico = mercatipresenzeStoricoService.findByAutorizzazione(autorizzazioni);
	boolean export = createJMesaExport(request, response, listStorico);
	if (export) {
	    return null;
	}
	String[] catMercList = mercatipresenzeTService.findComboCategorieMerceologiche();
	fixRenderEntityProperty(mercatipresenzeStorico);
	model.addAttribute("mercatipresenzeStorico", mercatipresenzeStorico);
	model.addAttribute("catMercList", catMercList);
	model.addAttribute("listStorico", listStorico);
	setPageAttributes(model);
	return "mercatipresenzestorico/listStorico";
    }

    @RequestMapping
    public String create(Model model, @RequestParam("autId") Integer autId, HttpServletRequest request) {

	MercatipresenzeStorico mercatipresenzeStorico = new MercatipresenzeStorico();
	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(autId));
	mercatipresenzeStorico.setAutorizzazioni(autorizzazioni);
	String[] catMercList = mercatipresenzeTService.findComboCategorieMerceologiche();
	fixRenderEntityProperty(mercatipresenzeStorico);
	model.addAttribute("mercatipresenzeStorico", mercatipresenzeStorico);
	model.addAttribute("catMercList", catMercList);
	setPageAttributes(model);
	return "mercatipresenzestorico/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("mercatipresenzeStorico") MercatipresenzeStorico mercatipresenzeStorico, BindingResult result,
	    SessionStatus status, Model model, HttpServletRequest request, HttpServletResponse response) {

	try {
	    fixMergeEntityProperty(mercatipresenzeStorico);
	    mercatipresenzeStorico.setAnagrafe(mercatipresenzeStorico.getAutorizzazioni().getAnagrafe());	    
	    mercatipresenzeStoricoService.insert(mercatipresenzeStorico);
	} catch (Exception e) {
	    mercatipresenzeStorico.getId().setCodice(null);
	    copyErrorsToBindingResult(result, mercatipresenzeStorico, e);
	    String[] catMercList = mercatipresenzeTService.findComboCategorieMerceologiche();
	    // Si deve recupera da db l'autorizzazione perchè altrimenti da errore il VwEntilocali.geTautorizcomune
	    Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(mercatipresenzeStorico.getAutorizzazioni().getId().getCodice()));
	    mercatipresenzeStorico.setAutorizzazioni(autorizzazioni);
	    fixRenderEntityProperty(mercatipresenzeStorico);
	    model.addAttribute("mercatipresenzeStorico", mercatipresenzeStorico);
	    model.addAttribute("catMercList", catMercList);
	    setPageAttributes(model);
	    return "mercatipresenzestorico/form";
	}
	status.setComplete();
	return "redirect:view.htm?status_msg=01&codice=" + mercatipresenzeStorico.getId().getCodice();
    }

    @RequestMapping
    public String update(@ModelAttribute("mercatipresenzeStorico") MercatipresenzeStorico mercatipresenzeStorico, BindingResult result,
	    SessionStatus status, Model model, HttpServletRequest request, HttpServletResponse response) {

	try {
	    fixMergeEntityProperty(mercatipresenzeStorico);
	    mercatipresenzeStorico.setAnagrafe(mercatipresenzeStorico.getAutorizzazioni().getAnagrafe());
	    mercatipresenzeStoricoService.update(mercatipresenzeStorico);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatipresenzeStorico, e);
	    String[] catMercList = mercatipresenzeTService.findComboCategorieMerceologiche();
	    fixRenderEntityProperty(mercatipresenzeStorico);
	    model.addAttribute("mercatipresenzeStorico", mercatipresenzeStorico);
	    model.addAttribute("catMercList", catMercList);
	    setPageAttributes(model);
	    return "mercatipresenzestorico/form";
	}
	status.setComplete();
	return "redirect:view.htm?status_msg=02&codice=" + mercatipresenzeStorico.getId().getCodice();
    }

    @RequestMapping
    public String view(Model model, @RequestParam("codice") Integer codice, HttpServletRequest request) {

	MercatipresenzeStorico mercatipresenzeStorico = mercatipresenzeStoricoService.findById(new PkId(codice));
	String[] catMercList = mercatipresenzeTService.findComboCategorieMerceologiche();
	fixRenderEntityProperty(mercatipresenzeStorico);
	model.addAttribute("mercatipresenzeStorico", mercatipresenzeStorico);
	model.addAttribute("catMercList", catMercList);
	setPageAttributes(model);
	return "mercatipresenzestorico/form";
    }

    @RequestMapping
    public String delete(@ModelAttribute("mercatipresenzeStorico") MercatipresenzeStorico mercatipresenzeStorico, BindingResult result,
	    SessionStatus status, Model model, HttpServletRequest request, HttpServletResponse response) {

	Integer autId = mercatipresenzeStorico.getAutorizzazioni().getId().getCodice();
	try {
	    mercatipresenzeStoricoService.delete(mercatipresenzeStorico);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatipresenzeStorico, e);
	    String[] catMercList = mercatipresenzeTService.findComboCategorieMerceologiche();
	    fixRenderEntityProperty(mercatipresenzeStorico);
	    model.addAttribute("mercatipresenzeStorico", mercatipresenzeStorico);
	    model.addAttribute("catMercList", catMercList);
	    setPageAttributes(model);
	    return "mercatipresenzestorico/form";
	}
	status.setComplete();
	return "redirect:listStorico.htm?status_msg=05&autId=" + autId;
    }

    @RequestMapping
    public String createSearch(Model model, @RequestParam(value = "resetAttrs", required = false) String resetAttrs, HttpServletRequest request) {

	if (request.getSession().getAttribute(PRESENZEINREQUEST) != null) {
	    request.getSession().removeAttribute(PRESENZEINREQUEST);
	}
	MercatipresenzeStorico mercatipresenzeStorico = null;
	Set<Integer> anni = mercatipresenzeStoricoService.findAnni();
	String[] catMercList = mercatipresenzeTService.findComboCategorieMerceologiche();
	if (StringUtils.isNotBlank(resetAttrs) && resetAttrs.equalsIgnoreCase("true")) {
	    mercatipresenzeStorico = new MercatipresenzeStorico();
	} else {
	    mercatipresenzeStorico = (MercatipresenzeStorico) request.getSession(false).getAttribute("mercatipresenzeStorico");
	    if (mercatipresenzeStorico == null) {
		mercatipresenzeStorico = new MercatipresenzeStorico();
	    } else {
		Mercati mercato = mercatiService.findById(mercatipresenzeStorico.getMercato().getId());
		List<MercatiD> posteggi = mercatiDService.findAllByMercato(mercato);
		model.addAttribute("posteggiMercato", posteggi);
	    }
	}
	fixRenderEntityProperty(mercatipresenzeStorico);
	model.addAttribute("mercatipresenzeStorico", mercatipresenzeStorico);
	model.addAttribute("catMercList", catMercList);
	model.addAttribute("anni", anni);
	setPageAttributes(model);
	return "mercatipresenzestorico/search";
    }

    @RequestMapping
    public String listDaAutorizzazione(@RequestParam("autId") Integer autId, Model model, HttpServletRequest request, HttpServletResponse response) {

	MercatipresenzeStorico mercatipresenzeStorico = new MercatipresenzeStorico();
	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(autId));
	mercatipresenzeStorico.setAutorizzazioni(autorizzazioni);
	List<MercatipresenzeStorico> listPresenze = mercatipresenzeStoricoService.findSommaDellePresenze(mercatipresenzeStorico);
	boolean export = createJMesaExport(request, response, listPresenze);
	if (export) {
	    return null;
	}
	fixRenderEntityProperty(mercatipresenzeStorico);
	model.addAttribute("mercatiPresenzeStoricoList", listPresenze);
	model.addAttribute("mercatipresenzeStorico", mercatipresenzeStorico);
	return "mercatipresenzestorico/list";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public String list(@ModelAttribute("mercatipresenzeStorico") MercatipresenzeStorico mercatipresenzeStorico, BindingResult result,
	    SessionStatus status, Model model, HttpServletRequest request, HttpServletResponse response) {

	List<MercatipresenzeStorico> listPresenze = null;
	try {
	    if (request.getSession().getAttribute(PRESENZEINREQUEST) == null) {
		listPresenze = mercatipresenzeStoricoService.findSommaDellePresenze(mercatipresenzeStorico);
		request.getSession().setAttribute(PRESENZEINREQUEST, listPresenze);
	    } else {
		listPresenze = (List<MercatipresenzeStorico>) request.getSession().getAttribute(PRESENZEINREQUEST);
	    }
	    boolean export = createJMesaExport(request, response, listPresenze);
	    if (export) {
		return null;
	    }
	    fixRenderEntityProperty(mercatipresenzeStorico);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatipresenzeStorico, e);
	    fixRenderEntityProperty(mercatipresenzeStorico);
	    Set<Integer> anni = mercatipresenzeStoricoService.findAnni();
	    String[] catMercList = mercatipresenzeTService.findComboCategorieMerceologiche();
	    model.addAttribute("anni", anni);
	    model.addAttribute("catMercList", catMercList);
	    model.addAttribute("mercatipresenzeStorico", mercatipresenzeStorico);
	    setPageAttributes(model);
	    return "mercatipresenzestorico/search";
	}
	model.addAttribute("mercatiPresenzeStoricoList", listPresenze);
	model.addAttribute("mercatipresenzeStorico", mercatipresenzeStorico);
	return "mercatipresenzestorico/list";
    }

    @Override
    protected void fixMergeEntityProperty(MercatipresenzeStorico entity) {

	if (entity.getAnagrafe() != null && entity.getAnagrafe().getId().getCodice() == null) {
	    entity.setAnagrafe(null);
	}
	if (entity.getAutorizzazioni() != null && entity.getAutorizzazioni().getId().getCodice() == null) {
	    entity.setAutorizzazioni(null);
	}
	if (entity.getMercato() != null && entity.getMercato().getId().getCodice() == null) {
	    entity.setMercato(null);
	}
	if (entity.getMercatoUso() != null && entity.getMercatoUso().getId().getCodice() == null) {
	    entity.setMercatoUso(null);
	}
	if (entity.getPosteggio() != null && entity.getPosteggio().getId().getCodice() == null) {
	    entity.setPosteggio(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(MercatipresenzeStorico entity) {

	if (entity.getAnagrafe() == null) {
	    entity.setAnagrafe(new Anagrafe());
	}
	if (entity.getAutorizzazioni() == null) {
	    entity.setAutorizzazioni(new Autorizzazioni());
	}
	if (entity.getMercato() == null) {
	    entity.setMercato(new Mercati());
	}
	if (entity.getMercatoUso() == null) {
	    entity.setMercatoUso(new MercatiUso());
	}
	if (entity.getPosteggio() == null) {
	    entity.setPosteggio(new MercatiD());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
