package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Naturaendobase;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.NaturaendobaseService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;

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
@SessionAttributes("naturaendo")
public class NaturaendoController extends BaseController<Naturaendobase> {

    @Autowired
    private NaturaendobaseService naturaendoService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	checkFunzionalitaConsolleRegionale(true);
	List<Naturaendobase> naturaendoList = naturaendoService.findAll(null, null);
	boolean isVerticalizzazioneCARTAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	ModelMap model = new ModelMap(naturaendoList);
	model.addAttribute("isVerticalizzazioneCARTAttiva", isVerticalizzazioneCARTAttiva);
	boolean export = createJMesaExport(request, response, naturaendoList);
	if (export) {
	    return null;
	}
	model.addAttribute("naturaendoList", naturaendoList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Naturaendobase naturaendo = new Naturaendobase();
	fixRenderEntityProperty(naturaendo);
	List<Naturaendobase> naturaendoList = naturaendoService.findAllExcludeNatura(null, OrderTypeEnum.DESC);
	model.addAttribute("naturaendo", naturaendo);
	model.addAttribute("naturaendoList", naturaendoList);
	setPageAttributes(model);
	return "naturaendo/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("naturaendo") Naturaendobase naturaendo, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(naturaendo);
	try {
	    naturaendoService.insert(naturaendo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, naturaendo, e);
	    List<Naturaendobase> naturaendoList = naturaendoService.findAllExcludeNatura(null, OrderTypeEnum.DESC);
	    boolean isVerticalizzazioneCARTAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	    model.addAttribute("isVerticalizzazioneCARTAttiva", isVerticalizzazioneCARTAttiva);
	    model.addAttribute("naturaendoList", naturaendoList);
	    fixRenderEntityProperty(naturaendo);
	    return "naturaendo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + naturaendo.getId() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	checkFunzionalitaConsolleRegionale(true);
	Naturaendobase naturaendo = naturaendoService.findById(codice);
	List<Naturaendobase> naturaendoList = naturaendoService.findAllExcludeNatura(naturaendo, OrderTypeEnum.DESC);
	naturaendoList = naturaendoService.getNatureendoByDipendenze(naturaendoList, naturaendo, true);
	fixRenderEntityProperty(naturaendo);
	boolean isVerticalizzazioneCARTAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	model.addAttribute("isVerticalizzazioneCARTAttiva", isVerticalizzazioneCARTAttiva);
	model.addAttribute("naturaendo", naturaendo);
	model.addAttribute("naturaendoList", naturaendoList);
	setPageAttributes(model);
	return "naturaendo/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("naturaendo") Naturaendobase naturaendo, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	checkFunzionalitaConsolleRegionale(true);
	fixMergeEntityProperty(naturaendo);
	try {
	    naturaendoService.update(naturaendo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, naturaendo, e);
	    List<Naturaendobase> naturaendoList = naturaendoService.findAllExcludeNatura(naturaendo, OrderTypeEnum.DESC);
	    naturaendoList = naturaendoService.getNatureendoByDipendenze(naturaendoList, naturaendo, true);
	    boolean isVerticalizzazioneCARTAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	    model.addAttribute("isVerticalizzazioneCARTAttiva", isVerticalizzazioneCARTAttiva);
	    model.addAttribute("naturaendoList", naturaendoList);
	    fixRenderEntityProperty(naturaendo);
	    return "naturaendo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + naturaendo.getId() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("naturaendo") Naturaendobase naturaendo, BindingResult result, SessionStatus status) {

	checkFunzionalitaConsolleRegionale(true);
	Naturaendobase objToDelete = naturaendoService.findById(naturaendo.getId());
	try {
	    naturaendoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    List<Naturaendobase> naturaendoList = naturaendoService.findAllExcludeNatura(naturaendo, OrderTypeEnum.DESC);
	    naturaendoList = naturaendoService.getNatureendoByDipendenze(naturaendoList, naturaendo, true);
	    model.addAttribute("naturaendoList", naturaendoList);
	    fixRenderEntityProperty(naturaendo);
	    return "naturaendo/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Naturaendobase entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Naturaendobase entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
