package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipidocumento;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.TipidocumentoService;

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
 * @author francescop
 */
@Controller
@SessionAttributes("tipidocumento")
public class TipidocumentoController extends BaseController<Tipidocumento> {

    @Autowired
    private TipidocumentoService tipidocumentoService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipidocumento> tipidocumentoList = tipidocumentoService.findAll(null, null);
	ModelMap model = new ModelMap(tipidocumentoList);
	boolean export = createJMesaExport(request, response, tipidocumentoList);
	if (export) {
	    return null;
	}
	model.addAttribute("tipidocumentoList", tipidocumentoList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Tipidocumento tipidocumento = new Tipidocumento();
	fixRenderEntityProperty(tipidocumento);
	model.addAttribute("tipidocumento", tipidocumento);
	setPageAttributes(model);
	return "tipidocumento/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipidocumento") Tipidocumento tipidocumento, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(tipidocumento);
	try {
	    tipidocumentoService.insert(tipidocumento);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipidocumento, e);
	    fixRenderEntityProperty(tipidocumento);
	    return "tipidocumento/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipidocumento.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipidocumento tipidocumento = tipidocumentoService.findById(id);
	fixRenderEntityProperty(tipidocumento);
	model.addAttribute("tipidocumento", tipidocumento);
	setPageAttributes(model);
	return "tipidocumento/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipidocumento") Tipidocumento tipidocumento, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(tipidocumento);
	try {
	    tipidocumentoService.update(tipidocumento);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipidocumento, e);
	    fixRenderEntityProperty(tipidocumento);
	    return "tipidocumento/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipidocumento.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("tipidocumento") Tipidocumento tipidocumento, BindingResult result, SessionStatus status) {

	Tipidocumento objToDelete = tipidocumentoService.findById(tipidocumento.getId());
	try {
	    tipidocumentoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipidocumento);
	    return "tipidocumento/form";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    @Override
    protected void fixMergeEntityProperty(Tipidocumento entity) {

	if (EntityUtils.isNestedPropertyBlank(entity.getLetteretipo(), "id.codice")) {
	    entity.setLetteretipo(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Tipidocumento entity) {

	if (EntityUtils.isNestedPropertyBlank(entity.getLetteretipo(), "id.codice")) {
	    entity.setLetteretipo(new Letteretipo());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
