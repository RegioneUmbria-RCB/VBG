package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologieoggetto;
import it.gruppoinit.pal.gp.core.service.TipologieoggettoService;

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

@Controller
@SessionAttributes("tipologieoggetto")
public class TipologieoggettoController extends BaseController<Tipologieoggetto> {

    @Autowired
    private TipologieoggettoService tipologieoggettoService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipologieoggetto> tipologieoggettoList = tipologieoggettoService.findAll(null, null);
	ModelMap model = new ModelMap(tipologieoggettoList);
	boolean export = createJMesaExport(request, response, tipologieoggettoList);
	if (export)
	    return null;
	model.addAttribute("tipologieoggettoList", tipologieoggettoList);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("tipologieoggetto") Tipologieoggetto tipologieoggetto, BindingResult result, SessionStatus status) {

	Tipologieoggetto objToDelete = tipologieoggettoService.findById(tipologieoggetto.getId());
	try {
	    tipologieoggettoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipologieoggetto);
	    return "tipologieoggetto/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipologieoggetto") Tipologieoggetto tipologieoggetto, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(tipologieoggetto);
	try {
	    tipologieoggettoService.insert(tipologieoggetto);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipologieoggetto, e);
	    fixRenderEntityProperty(tipologieoggetto);
	    return "tipologieoggetto/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipologieoggetto.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipologieoggetto") Tipologieoggetto tipologieoggetto, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(tipologieoggetto);
	try {
	    tipologieoggettoService.update(tipologieoggetto);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipologieoggetto, e);
	    fixRenderEntityProperty(tipologieoggetto);
	    return "tipologieoggetto/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipologieoggetto.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	Tipologieoggetto tipologieoggetto = new Tipologieoggetto();
	fixRenderEntityProperty(tipologieoggetto);
	model.addAttribute("tipologieoggetto", tipologieoggetto);
	setPageAttributes(model);
	return "tipologieoggetto/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipologieoggetto tipologieoggetto = tipologieoggettoService.findById(id);
	fixRenderEntityProperty(tipologieoggetto);
	model.addAttribute("tipologieoggetto", tipologieoggetto);
	setPageAttributes(model);
	return "tipologieoggetto/form";
    }

    @Override
    protected void fixMergeEntityProperty(Tipologieoggetto entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Tipologieoggetto entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
