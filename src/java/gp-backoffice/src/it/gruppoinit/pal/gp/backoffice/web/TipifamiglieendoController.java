package it.gruppoinit.pal.gp.backoffice.web;

/**
 * @author gianpaolot
 */
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;

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
@SessionAttributes("tipifamiglieendo")
public class TipifamiglieendoController extends BaseController<Tipifamiglieendo> {

    @Autowired
    private TipifamiglieendoService tipifamiglieendoService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipifamiglieendo> tipifamiglieendoList = tipifamiglieendoService.findAll(null, null);
	ModelMap model = new ModelMap(tipifamiglieendoList);
	boolean export = createJMesaExport(request, response, tipifamiglieendoList);
	if (export)
	    return null;
	model.addAttribute("tipifamiglieendoList", tipifamiglieendoList);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("tipifamiglieendo") Tipifamiglieendo tipifamiglieendo, BindingResult result,
	    SessionStatus status) {

	Tipifamiglieendo objToDelete = tipifamiglieendoService.findById(tipifamiglieendo.getId());
	try {
	    tipifamiglieendoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipifamiglieendo, e);
	    fixRenderEntityProperty(tipifamiglieendo);
	    return "tipifamiglieendo/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipifamiglieendo") Tipifamiglieendo tipifamiglieendo, BindingResult result, SessionStatus status) {

	Software software = softwareService.findById(ORMHelper.getSoftware());
	tipifamiglieendo.setSoftware(software);
	try {
	    tipifamiglieendoService.insert(tipifamiglieendo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipifamiglieendo, e);
	    fixRenderEntityProperty(tipifamiglieendo);
	    return "tipifamiglieendo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipifamiglieendo.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipifamiglieendo") Tipifamiglieendo tipifamiglieendo, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(tipifamiglieendo);
	try {
	    tipifamiglieendoService.update(tipifamiglieendo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipifamiglieendo, e);
	    fixRenderEntityProperty(tipifamiglieendo);
	    return "tipifamiglieendo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipifamiglieendo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	Tipifamiglieendo tipifamiglieendo = new Tipifamiglieendo();
	fixRenderEntityProperty(tipifamiglieendo);
	model.addAttribute("tipifamiglieendo", tipifamiglieendo);
	setPageAttributes(model);
	return "tipifamiglieendo/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipifamiglieendo tipifamiglieendo = tipifamiglieendoService.findById(id);
	fixRenderEntityProperty(tipifamiglieendo);
	model.addAttribute("tipifamiglieendo", tipifamiglieendo);
	setPageAttributes(model);
	return "tipifamiglieendo/form";
    }

    @Override
    protected void fixMergeEntityProperty(Tipifamiglieendo entity) {

	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null || entity.getSoftware().getCodice().equals(" "))) {
	    entity.setSoftware(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Tipifamiglieendo entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
