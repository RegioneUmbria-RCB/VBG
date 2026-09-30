/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import java.util.List;

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
import it.gruppoinit.pal.gp.core.domain.Staticomportamento;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.StaticomportamentoService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * @author francescop
 * 
 */
@Controller
@SessionAttributes("statiistanza")
public class StatiistanzaController extends BaseController<Statiistanza> {

    @Autowired
    private StatiistanzaService statiistanzaService;
    @Autowired
    private StaticomportamentoService staticomportamentoService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Statiistanza> statiistanzaList = statiistanzaService.findBySoftware(ORMHelper.getSoftware());
	ModelMap model = new ModelMap(statiistanzaList);
	model.addAttribute("statiistanzaList", statiistanzaList);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("statiistanza") Statiistanza statiistanza, BindingResult result, SessionStatus status) {

	Statiistanza objToDelete = statiistanzaService.findById(statiistanza.getId());
	try {
	    statiistanzaService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(statiistanza);
	    return "statiistanza/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("statiistanza") Statiistanza statiistanza, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	List<Staticomportamento> staticomportamenti = staticomportamentoService.findAll(null, null);
	model.addAttribute("staticomportamenti", staticomportamenti);
	fixMergeEntityProperty(statiistanza);
	try {
	    statiistanzaService.insert(statiistanza);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, statiistanza, e);
	    fixRenderEntityProperty(statiistanza);
	    request.setAttribute("error", "03");
	    return "statiistanza/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + statiistanza.getId().getCodicestato() + "&status_msg=01";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("statiistanza") Statiistanza statiistanza, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	List<Staticomportamento> staticomportamenti = staticomportamentoService.findAll(null, null);
	model.addAttribute("staticomportamenti", staticomportamenti);
	fixMergeEntityProperty(statiistanza);
	try {
	    statiistanzaService.update(statiistanza);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, statiistanza, e);
	    fixRenderEntityProperty(statiistanza);
	    request.setAttribute("error", "03");
	    return "statiistanza/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + statiistanza.getId().getCodicestato() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	Statiistanza statiistanza = new Statiistanza();
	StatiistanzaId statiistanzaId = new StatiistanzaId();
	statiistanzaId.setSoftware(ORMHelper.getSoftware());
	statiistanza.setId(statiistanzaId);
	List<Staticomportamento> staticomportamenti = staticomportamentoService.findAll(null, null);
	fixRenderEntityProperty(statiistanza);
	model.addAttribute("statiistanza", statiistanza);
	model.addAttribute("staticomportamenti", staticomportamenti);
	setPageAttributes(model);
	return "statiistanza/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") String codice, Model model, HttpServletRequest request) {

	StatiistanzaId statiistanzaId = new StatiistanzaId();
	statiistanzaId.setSoftware(ORMHelper.getSoftware());
	statiistanzaId.setCodicestato(codice);
	Statiistanza statiistanza = statiistanzaService.findById(statiistanzaId);
	fixRenderEntityProperty(statiistanza);
	List<Staticomportamento> staticomportamenti = staticomportamentoService.findAll(null, null);
	model.addAttribute("staticomportamenti", staticomportamenti);
	model.addAttribute("statiistanza", statiistanza);
	setPageAttributes(model);
	return "statiistanza/form";
    }

    @Override
    protected void fixMergeEntityProperty(Statiistanza entity) {

	// void method
    }

    @Override
    protected void fixRenderEntityProperty(Statiistanza entity) {

	// void method
    }

    @Override
    protected void setPageAttributes(Model model) {

	model.addAttribute("OSP_LIVORNO_ATTIVA", Boolean.FALSE);
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAIONE_SIT_LDP)) {
	    Integer inizioora = getCampo(WebConstants.VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_INIZIO_ORA);
	    if (inizioora != null) { // se il campo non è attivato la funzionalità occupazione suolo pubblico non è attiva
		model.addAttribute("OSP_LIVORNO_ATTIVA", Boolean.TRUE);
	    }
	}
    }

    private Integer getCampo(String name) {

	Verticalizzazioniparametri vcampo = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_SIT_LDP, name);
	if (vcampo != null) {
	    if (StringUtils.isNotBlank(vcampo.getValore())) {
		if (Utilities.isInteger(vcampo.getValore().trim())) {
		    Integer ret = Integer.parseInt(vcampo.getValore().trim());
		    return ret;
		}
	    }
	}
	return null;
    }
}
