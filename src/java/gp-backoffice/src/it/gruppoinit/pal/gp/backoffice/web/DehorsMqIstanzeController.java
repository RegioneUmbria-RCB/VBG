package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.DehorsMqIstanze;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DehorsMqIstanzeHelper;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniCommand;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.service.DehorsMqIstanzeService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.math.BigDecimal;
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

/**
 * 
 * @author
 */
@Controller
@SessionAttributes(value = { "autorizzazioniCommand" })
public class DehorsMqIstanzeController extends BaseController<DehorsMqIstanze> {

    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private DehorsMqIstanzeService dehorsmqistanzeService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<DehorsMqIstanze> dehorsmqistanzeList = dehorsmqistanzeService.findAll(null, null);
	ModelMap model = new ModelMap(dehorsmqistanzeList);
	boolean export = createJMesaExport(request, response, dehorsmqistanzeList);
	if (export) {
	    return null;
	}
	model.addAttribute("dehorsmqistanzeList", dehorsmqistanzeList);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codiceAut") Integer codiceAut, Model model) {

	AutorizzazioniCommand autorizzazioniCommand = new AutorizzazioniCommand();
	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(codiceAut));
	DehorsMqIstanze dehorsmqistanze = new DehorsMqIstanze();
	dehorsmqistanze.setAutorizzazioni(autorizzazioni);
	dehorsmqistanze.setIstanze(autorizzazioni.getIstanza());
	dehorsmqistanze.setCessata(false);
	autorizzazioniCommand.setEntity(autorizzazioni);
	autorizzazioniCommand.setDehorsMqIstanze(dehorsmqistanze);
	fixRenderEntityProperty(dehorsmqistanze);
	model.addAttribute("autorizzazioniCommand", autorizzazioniCommand);
	setPageAttributes(model);
	return "dehorsmqistanze/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("autorizzazioniCommand") AutorizzazioniCommand autorizzazioniCommand, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(autorizzazioniCommand.getDehorsMqIstanze());
	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(autorizzazioniCommand.getEntity().getId().getCodice()));
	autorizzazioniCommand.getDehorsMqIstanze().setAutorizzazioni(autorizzazioni);
	autorizzazioniCommand.getDehorsMqIstanze().setIstanze(autorizzazioni.getIstanza());
	autorizzazioniCommand.getDehorsMqIstanze().setCessata(false);
	try {
	    dehorsmqistanzeService.insert(autorizzazioniCommand.getDehorsMqIstanze(), new BigDecimal(0));
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, autorizzazioniCommand.getDehorsMqIstanze(), true, "dehorsMqIstanze", e);
	    fixRenderEntityProperty(autorizzazioniCommand.getDehorsMqIstanze());
	    return "dehorsmqistanze/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + autorizzazioniCommand.getDehorsMqIstanze().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	AutorizzazioniCommand autorizzazioniCommand = new AutorizzazioniCommand();
	DehorsMqIstanze dehorsmqistanze = dehorsmqistanzeService.findById(id);
	autorizzazioniCommand.setEntity(dehorsmqistanze.getAutorizzazioni());
	autorizzazioniCommand.setDehorsMqIstanze(dehorsmqistanze);
	autorizzazioniCommand.setDisplayMode(AutorizzazioniCommand.NEW);
	fixRenderEntityProperty(dehorsmqistanze);
	DehorsMqIstanzeHelper dehorsMqIstanzeHelper = dehorsmqistanzeService.findDehorsMqIstanzeHelper(dehorsmqistanze.getAree().getId().getCodice());
	// I disponibili saranno dati dalla somma di quelli disponili (la somma presente su DehorsMqIstanze) + quelli già assegnati
	// 
	model.addAttribute("disponibiliDaAssegnare", dehorsMqIstanzeHelper.getDisponibili().add(dehorsmqistanze.getMqassegnati()));
	model.addAttribute("autorizzazioniCommand", autorizzazioniCommand);
	model.addAttribute("_readonly", false);
	setPageAttributes(model);
	return "dehorsmqistanze/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("autorizzazioniCommand") AutorizzazioniCommand autorizzazioniCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(autorizzazioniCommand.getDehorsMqIstanze());
	DehorsMqIstanze dehorsmqistanze = dehorsmqistanzeService.findById(new PkId(autorizzazioniCommand.getDehorsMqIstanze().getId().getCodice()));
	BigDecimal mqAssegantiIniziali = dehorsmqistanze.getMqassegnati();
	dehorsmqistanze.setMqassegnati(autorizzazioniCommand.getDehorsMqIstanze().getMqassegnati());
	dehorsmqistanze.setMqrichiesti(autorizzazioniCommand.getDehorsMqIstanze().getMqrichiesti());
	try {
	    dehorsmqistanzeService.update(dehorsmqistanze, mqAssegantiIniziali);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, autorizzazioniCommand.getDehorsMqIstanze(), true, "dehorsMqIstanze", e);
	    fixRenderEntityProperty(autorizzazioniCommand.getDehorsMqIstanze());
	    DehorsMqIstanzeHelper dehorsMqIstanzeHelper = dehorsmqistanzeService.findDehorsMqIstanzeHelper(dehorsmqistanze.getAree().getId()
		    .getCodice());
	    // I disponibili saranno dati dalla somma di quelli disponili (la somma presente su DehorsMqIstanze) + quelli già assegnati
	    // 
	    model.addAttribute("disponibiliDaAssegnare", dehorsMqIstanzeHelper.getDisponibili().add(mqAssegantiIniziali));
	    model.addAttribute("autorizzazioniCommand", autorizzazioniCommand);
	    model.addAttribute("_readonly", false);
	    return "dehorsmqistanze/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + autorizzazioniCommand.getDehorsMqIstanze().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("autorizzazioniCommand") AutorizzazioniCommand autorizzazioniCommand, BindingResult result,
	    SessionStatus status) {

	DehorsMqIstanze objToDelete = dehorsmqistanzeService.findById(autorizzazioniCommand.getDehorsMqIstanze().getId());
	BigDecimal mqAssegantiIniziali = objToDelete.getMqassegnati();
	try {
	    dehorsmqistanzeService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, "dehorsMqIstanze", e);
	    fixRenderEntityProperty(autorizzazioniCommand.getDehorsMqIstanze());
	    DehorsMqIstanzeHelper dehorsMqIstanzeHelper = dehorsmqistanzeService.findDehorsMqIstanzeHelper(objToDelete.getAree().getId().getCodice());
	    // I disponibili saranno dati dalla somma di quelli disponili (la somma presente su DehorsMqIstanze) + quelli già assegnati
	    // 
	    model.addAttribute("disponibiliDaAssegnare", dehorsMqIstanzeHelper.getDisponibili().add(mqAssegantiIniziali));
	    model.addAttribute("autorizzazioniCommand", autorizzazioniCommand);
	    model.addAttribute("_readonly", false);
	    return "dehorsmqistanze/form";
	}
	status.setComplete();
	return "redirect:../autorizzazioni/viewAutorizzazione.htm?codice=" + objToDelete.getAutorizzazioni().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(DehorsMqIstanze entity) {

    }

    @Override
    protected void fixRenderEntityProperty(DehorsMqIstanze entity) {

	if (entity.getAree() == null) {
	    entity.setAree(new Aree());
	}
	if (entity.getAutorizzazioni() != null) {
	    entity.setAutorizzazioni(new Autorizzazioni());
	}
	if (entity.getIstanze() != null) {
	    entity.setIstanze(new Istanze());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
