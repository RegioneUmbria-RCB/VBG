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
import it.gruppoinit.pal.gp.core.dao.helper.TipoSchedaEndo;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.NaturaendoId;
import it.gruppoinit.pal.gp.core.domain.StpModalitaApertura;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.NaturaendoService;
import it.gruppoinit.pal.gp.core.service.StpModalitaAperturaService;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("naturaendo")
public class NaturaendoController extends BaseController<Naturaendo> {

    @Autowired
    private NaturaendoService naturaendoService;
    @Autowired
    private StpModalitaAperturaService stpModalitaAperturaService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Naturaendo> naturaendoList = naturaendoService.findAll(null, null);
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

	Naturaendo naturaendo = new Naturaendo();
	fixRenderEntityProperty(naturaendo);
	List<Naturaendo> naturaendoList = naturaendoService.findAllExcludeNatura(null, OrderTypeEnum.DESC);
	boolean isVerticalizzazioneCARTAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	List<StpModalitaApertura> stpModalitaAperturaSchedaEndo1 = stpModalitaAperturaService
		.findStpModalitaByTipoScheda(TipoSchedaEndo.SCHEDA_TIPO_ENDO1);
	List<StpModalitaApertura> stpModalitaAperturaSchedaEndo2 = stpModalitaAperturaService
		.findStpModalitaByTipoScheda(TipoSchedaEndo.SCHEDA_TIPO_ENDO2);
	model.addAttribute("stpModalitasAperturaSchedaEndo1", stpModalitaAperturaSchedaEndo1);
	model.addAttribute("stpModalitasAperturaSchedaEndo2", stpModalitaAperturaSchedaEndo2);
	model.addAttribute("isVerticalizzazioneCARTAttiva", isVerticalizzazioneCARTAttiva);
	model.addAttribute("naturaendo", naturaendo);
	model.addAttribute("naturaendoList", naturaendoList);
	setPageAttributes(model);
	return "naturaendo/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("naturaendo") Naturaendo naturaendo, BindingResult result, SessionStatus status) {

	StpModalitaApertura modalitaAperturaScheda1 = stpModalitaAperturaService.findById(naturaendo.getStpModalitaAperturaSchedaEndo1().getId());
	naturaendo.setStpModalitaAperturaSchedaEndo1(modalitaAperturaScheda1);
	StpModalitaApertura modalitaAperturaScheda2 = stpModalitaAperturaService.findById(naturaendo.getStpModalitaAperturaSchedaEndo2().getId());
	naturaendo.setStpModalitaAperturaSchedaEndo2(modalitaAperturaScheda2);
	fixMergeEntityProperty(naturaendo);
	try {
	    naturaendoService.insert(naturaendo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, naturaendo, e);
	    List<Naturaendo> naturaendoList = naturaendoService.findAllExcludeNatura(null, OrderTypeEnum.DESC);
	    boolean isVerticalizzazioneCARTAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	    List<StpModalitaApertura> stpModalitaAperturaSchedaEndo1 = stpModalitaAperturaService
		    .findStpModalitaByTipoScheda(TipoSchedaEndo.SCHEDA_TIPO_ENDO1);
	    List<StpModalitaApertura> stpModalitaAperturaSchedaEndo2 = stpModalitaAperturaService
		    .findStpModalitaByTipoScheda(TipoSchedaEndo.SCHEDA_TIPO_ENDO2);
	    model.addAttribute("stpModalitasAperturaSchedaEndo1", stpModalitaAperturaSchedaEndo1);
	    model.addAttribute("stpModalitasAperturaSchedaEndo2", stpModalitaAperturaSchedaEndo2);
	    model.addAttribute("isVerticalizzazioneCARTAttiva", isVerticalizzazioneCARTAttiva);
	    model.addAttribute("naturaendoList", naturaendoList);
	    fixRenderEntityProperty(naturaendo);
	    return "naturaendo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + naturaendo.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	NaturaendoId id = new NaturaendoId(codice);
	Naturaendo naturaendo = naturaendoService.findById(id);
	List<Naturaendo> naturaendoList = naturaendoService.findAllExcludeNatura(naturaendo, OrderTypeEnum.DESC);
	naturaendoList = naturaendoService.getNatureendoByDipendenze(naturaendoList, naturaendo, true);
	fixRenderEntityProperty(naturaendo);
	boolean isVerticalizzazioneCARTAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	List<StpModalitaApertura> stpModalitaAperturaSchedaEndo1 = stpModalitaAperturaService
		.findStpModalitaByTipoScheda(TipoSchedaEndo.SCHEDA_TIPO_ENDO1);
	List<StpModalitaApertura> stpModalitaAperturaSchedaEndo2 = stpModalitaAperturaService
		.findStpModalitaByTipoScheda(TipoSchedaEndo.SCHEDA_TIPO_ENDO2);
	model.addAttribute("stpModalitasAperturaSchedaEndo1", stpModalitaAperturaSchedaEndo1);
	model.addAttribute("stpModalitasAperturaSchedaEndo2", stpModalitaAperturaSchedaEndo2);
	model.addAttribute("isVerticalizzazioneCARTAttiva", isVerticalizzazioneCARTAttiva);
	model.addAttribute("naturaendo", naturaendo);
	model.addAttribute("naturaendoList", naturaendoList);
	setPageAttributes(model);
	return "naturaendo/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("naturaendo") Naturaendo naturaendo, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	StpModalitaApertura modalitaAperturaScheda1 = stpModalitaAperturaService.findById(naturaendo.getStpModalitaAperturaSchedaEndo1().getId());
	naturaendo.setStpModalitaAperturaSchedaEndo1(modalitaAperturaScheda1);
	StpModalitaApertura modalitaAperturaScheda2 = stpModalitaAperturaService.findById(naturaendo.getStpModalitaAperturaSchedaEndo2().getId());
	naturaendo.setStpModalitaAperturaSchedaEndo2(modalitaAperturaScheda2);
	fixMergeEntityProperty(naturaendo);
	try {
	    naturaendoService.update(naturaendo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, naturaendo, e);
	    List<Naturaendo> naturaendoList = naturaendoService.findAllExcludeNatura(naturaendo, OrderTypeEnum.DESC);
	    naturaendoList = naturaendoService.getNatureendoByDipendenze(naturaendoList, naturaendo, true);
	    boolean isVerticalizzazioneCARTAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	    List<StpModalitaApertura> stpModalitaAperturaSchedaEndo1 = stpModalitaAperturaService
		    .findStpModalitaByTipoScheda(TipoSchedaEndo.SCHEDA_TIPO_ENDO1);
	    List<StpModalitaApertura> stpModalitaAperturaSchedaEndo2 = stpModalitaAperturaService
		    .findStpModalitaByTipoScheda(TipoSchedaEndo.SCHEDA_TIPO_ENDO2);
	    model.addAttribute("stpModalitasAperturaSchedaEndo1", stpModalitaAperturaSchedaEndo1);
	    model.addAttribute("stpModalitasAperturaSchedaEndo2", stpModalitaAperturaSchedaEndo2);
	    model.addAttribute("isVerticalizzazioneCARTAttiva", isVerticalizzazioneCARTAttiva);
	    model.addAttribute("naturaendoList", naturaendoList);
	    fixRenderEntityProperty(naturaendo);
	    return "naturaendo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + naturaendo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("naturaendo") Naturaendo naturaendo, BindingResult result, SessionStatus status) {

	Naturaendo objToDelete = naturaendoService.findById(naturaendo.getId());
	try {
	    naturaendoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    List<Naturaendo> naturaendoList = naturaendoService.findAllExcludeNatura(naturaendo, OrderTypeEnum.DESC);
	    naturaendoList = naturaendoService.getNatureendoByDipendenze(naturaendoList, naturaendo, true);
	    boolean isVerticalizzazioneCARTAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	    List<StpModalitaApertura> stpModalitaAperturaSchedaEndo1 = stpModalitaAperturaService
		    .findStpModalitaByTipoScheda(TipoSchedaEndo.SCHEDA_TIPO_ENDO1);
	    List<StpModalitaApertura> stpModalitaAperturaSchedaEndo2 = stpModalitaAperturaService
		    .findStpModalitaByTipoScheda(TipoSchedaEndo.SCHEDA_TIPO_ENDO2);
	    model.addAttribute("stpModalitasAperturaSchedaEndo1", stpModalitaAperturaSchedaEndo1);
	    model.addAttribute("stpModalitasAperturaSchedaEndo2", stpModalitaAperturaSchedaEndo2);
	    model.addAttribute("isVerticalizzazioneCARTAttiva", isVerticalizzazioneCARTAttiva);
	    model.addAttribute("naturaendoList", naturaendoList);
	    fixRenderEntityProperty(naturaendo);
	    return "naturaendo/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Naturaendo entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Naturaendo entity) {

	if (EntityUtils.getNestedProperty(entity, "stpModalitaAperturaSchedaEndo1") == null) {
	    entity.setStpModalitaAperturaSchedaEndo1(new StpModalitaApertura());
	}
	if (EntityUtils.getNestedProperty(entity, "stpModalitaAperturaSchedaEndo2") == null) {
	    entity.setStpModalitaAperturaSchedaEndo2(new StpModalitaApertura());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
