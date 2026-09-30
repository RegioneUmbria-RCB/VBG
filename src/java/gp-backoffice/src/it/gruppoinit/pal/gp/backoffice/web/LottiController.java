package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Lotti;
import it.gruppoinit.pal.gp.core.domain.LottiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AreeService;
import it.gruppoinit.pal.gp.core.service.LottiService;

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
@SessionAttributes("lotti")
public class LottiController extends BaseController<Lotti> {

    @Autowired
    private LottiService lottiService;
    @Autowired
    private AreeService areeService;

    @RequestMapping
    public ModelMap list(@RequestParam("codiceArea") Integer codiceArea, HttpServletRequest request, HttpServletResponse response) {

	Aree aree = areeService.findById(new PkId(codiceArea));
	List<Lotti> lottiList = lottiService.findByAree(aree);
	ModelMap model = new ModelMap(lottiList);
	boolean export = createJMesaExport(request, response, lottiList);
	if (export) {
	    return null;
	}
	model.addAttribute("area", aree);
	model.addAttribute("lottiList", lottiList);
	return model;
    }

    @RequestMapping
    public String create(Model model, @RequestParam("codiceArea") Integer codiceArea) {

	Lotti lotti = new Lotti();
	lotti.getId().setCodicearea(codiceArea);
	Aree aree = areeService.findById(new PkId(codiceArea));
	lotti.setAree(aree);
	fixRenderEntityProperty(lotti);
	model.addAttribute("lotti", lotti);
	setPageAttributes(model);
	return "lotti/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("lotti") Lotti lotti, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(lotti);
	try {
	    lottiService.insert(lotti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, lotti, e);
	    fixRenderEntityProperty(lotti);
	    return "lotti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + lotti.getId().getCodicelotto() + "&codiceArea=" + lotti.getId().getCodicearea() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, @RequestParam("codiceArea") Integer codiceArea, Model model, HttpServletRequest request) {

	LottiId id = new LottiId();
	id.setCodicelotto(codice);
	id.setCodicearea(codiceArea);
	Lotti lotti = lottiService.findById(id);
	fixRenderEntityProperty(lotti);
	model.addAttribute("lotti", lotti);
	setPageAttributes(model);
	return "lotti/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("lotti") Lotti lotti, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(lotti);
	try {
	    lottiService.update(lotti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, lotti, e);
	    fixRenderEntityProperty(lotti);
	    return "lotti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + lotti.getId().getCodicelotto() + "&codiceArea=" + lotti.getId().getCodicearea() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@RequestParam("codiceArea") Integer codiceArea, @ModelAttribute("lotti") Lotti lotti, BindingResult result,
	    SessionStatus status) {

	LottiId id = new LottiId();
	id.setCodicearea(codiceArea);
	id.setCodicelotto(lotti.getId().getCodicelotto());
	Lotti objToDelete = lottiService.findById(id);
	try {
	    lottiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(lotti);
	    return "lotti/form";
	}
	status.setComplete();
	return "redirect:list.htm?codiceArea=" + codiceArea;
    }

    @Override
    protected void fixMergeEntityProperty(Lotti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Lotti entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
