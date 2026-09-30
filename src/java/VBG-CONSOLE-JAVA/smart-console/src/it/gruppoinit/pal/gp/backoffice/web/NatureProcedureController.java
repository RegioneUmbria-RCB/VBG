package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.NatureProcedure;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.service.NatureProcedureService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;

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
@SessionAttributes("natureProcedure")
public class NatureProcedureController extends BaseController<NatureProcedure> {

    @Autowired
    private NatureProcedureService natureProcedureService;
    @Autowired
    private TipiprocedureService tipiprocedureService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<NatureProcedure> natureProcedureList = natureProcedureService.findAll(null, null);
	ModelMap model = new ModelMap(natureProcedureList);
	boolean export = createJMesaExport(request, response, natureProcedureList);
	if (export) {
	    return null;
	}
	model.addAttribute("natureProcedureList", natureProcedureList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	NatureProcedure natureProcedure = new NatureProcedure();
	Software software = softwareService.findById(ORMHelper.getSoftware());
	natureProcedure.setSoftware(software);
	fixRenderEntityProperty(natureProcedure);
	model.addAttribute("natureProcedure", natureProcedure);
	setPageAttributes(model);
	return "natureprocedure/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("natureProcedure") NatureProcedure natureProcedure, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(natureProcedure);
	try {
	    natureProcedureService.insert(natureProcedure);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, natureProcedure, e);
	    fixRenderEntityProperty(natureProcedure);
	    return "natureprocedure/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + natureProcedure.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	NatureProcedure natureProcedure = natureProcedureService.findById(new PkId(codice));
	model.addAttribute("natureProcedure", natureProcedure);
	setPageAttributes(model);
	return "natureprocedure/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("natureProcedure") NatureProcedure natureProcedure, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(natureProcedure);
	try {
	    natureProcedureService.update(natureProcedure);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, natureProcedure, e);
	    fixRenderEntityProperty(natureProcedure);
	    return "natureprocedure/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + natureProcedure.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("natureProcedure") NatureProcedure natureProcedure, BindingResult result, SessionStatus status) {

	NatureProcedure objToDelete = natureProcedureService.findById(natureProcedure.getId());
	try {
	    natureProcedureService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(natureProcedure);
	    return "natureprocedure/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(NatureProcedure entity) {

    }

    @Override
    protected void fixRenderEntityProperty(NatureProcedure entity) {

	if (entity.getTipiprocedure() == null) {
	    entity.setTipiprocedure(new Tipiprocedure());
	}
    }
}
