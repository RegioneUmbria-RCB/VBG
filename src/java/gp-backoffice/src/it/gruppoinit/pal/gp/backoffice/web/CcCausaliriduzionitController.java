package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionir;
import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionit;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.CcCausaliriduzionitCommand;
import it.gruppoinit.pal.gp.core.service.CcCausaliriduzionirService;
import it.gruppoinit.pal.gp.core.service.CcCausaliriduzionitService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

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
@SessionAttributes("cccausaliriduzionit")
public class CcCausaliriduzionitController extends BaseController<CcCausaliriduzionit> {

    @Autowired
    private CcCausaliriduzionirService ccCausaliriduzionirService;
    @Autowired
    private CcCausaliriduzionitService cccausaliriduzionitService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<CcCausaliriduzionit> cccausaliriduzionitList = cccausaliriduzionitService.findAll(null, null);
	ModelMap model = new ModelMap(cccausaliriduzionitList);
	boolean export = createJMesaExport(request, response, cccausaliriduzionitList);
	if (export) {
	    return null;
	}
	model.addAttribute("cccausaliriduzionitList", cccausaliriduzionitList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	CcCausaliriduzionitCommand cccausaliriduzionit = new CcCausaliriduzionitCommand();
	CcCausaliriduzionit entity = new CcCausaliriduzionit();
	entity.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(cccausaliriduzionit.getEntity());
	cccausaliriduzionit.setDisplayMode(CcCausaliriduzionitCommand.NEW);
	model.addAttribute("cccausaliriduzionit", cccausaliriduzionit);
	setPageAttributes(model);
	return "cccausaliriduzionit/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("cccausaliriduzionit") CcCausaliriduzionitCommand cccausaliriduzionit, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(cccausaliriduzionit.getEntity());
	cccausaliriduzionit.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    cccausaliriduzionitService.insert(cccausaliriduzionit.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cccausaliriduzionit.getEntity(), true, e);
	    fixRenderEntityProperty(cccausaliriduzionit.getEntity());
	    cccausaliriduzionit.setDisplayMode(CcCausaliriduzionitCommand.NEW);
	    model.addAttribute("cccausaliriduzionit", cccausaliriduzionit);
	    return "cccausaliriduzionit/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + cccausaliriduzionit.getEntity().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	CcCausaliriduzionitCommand cccausaliriduzionit = new CcCausaliriduzionitCommand();
	PkId id = new PkId(codice);
	CcCausaliriduzionit entity = cccausaliriduzionitService.findById(id);
	cccausaliriduzionit.setEntiry(entity);
	fixRenderEntityProperty(cccausaliriduzionit.getEntity());
	cccausaliriduzionit.setDisplayMode(CcCausaliriduzionitCommand.VIEW);
	model.addAttribute("cccausaliriduzionit", cccausaliriduzionit);
	setPageAttributes(model);
	return "cccausaliriduzionit/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("cccausaliriduzionit") CcCausaliriduzionitCommand cccausaliriduzionit, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(cccausaliriduzionit.getEntity());
	try {
	    cccausaliriduzionitService.update(cccausaliriduzionit.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cccausaliriduzionit.getEntity(), true, e);
	    cccausaliriduzionit.setDisplayMode(CcCausaliriduzionitCommand.VIEW);
	    model.addAttribute("cccausaliriduzionit", cccausaliriduzionit);
	    fixRenderEntityProperty(cccausaliriduzionit.getEntity());
	    return "cccausaliriduzionit/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + cccausaliriduzionit.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("cccausaliriduzionit") CcCausaliriduzionitCommand cccausaliriduzionit, BindingResult result,
	    SessionStatus status) {

	CcCausaliriduzionit objToDelete = cccausaliriduzionitService.findById(cccausaliriduzionit.getEntity().getId());
	try {
	    cccausaliriduzionitService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cccausaliriduzionit.getEntity(), true, e);
	    fixRenderEntityProperty(cccausaliriduzionit.getEntity());
	    cccausaliriduzionit.setDisplayMode(CcCausaliriduzionitCommand.VIEW);
	    model.addAttribute("cccausaliriduzionit", cccausaliriduzionit);
	    return "cccausaliriduzionit/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String addccCausaliriduzionir(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	CcCausaliriduzionitCommand cccausaliriduzionit = new CcCausaliriduzionitCommand();
	PkId id = new PkId(codice);
	CcCausaliriduzionit entity = cccausaliriduzionitService.findById(id);
	cccausaliriduzionit.setEntiry(entity);
	fixRenderEntityProperty(cccausaliriduzionit.getEntity());
	cccausaliriduzionit.setDisplayMode(CcCausaliriduzionitCommand.EDIT);
	model.addAttribute("cccausaliriduzionit", cccausaliriduzionit);
	setPageAttributes(model);
	return "cccausaliriduzionit/form";
    }

    @RequestMapping
    public String insertccCausaliriduzionir(Model model, @ModelAttribute("cccausaliriduzionit") CcCausaliriduzionitCommand cccausaliriduzionit,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	CcCausaliriduzionit entity = cccausaliriduzionitService.findById(new PkId(cccausaliriduzionit.getEntity().getId().getCodice()));
	cccausaliriduzionit.getCcCausaliriduzionir().setCcCausaliriduzionit(entity);
	try {
	    ccCausaliriduzionirService.insert(cccausaliriduzionit.getCcCausaliriduzionir());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cccausaliriduzionit.getCcCausaliriduzionir(), true, "ccCausaliriduzionir", e);
	    fixRenderEntityProperty(cccausaliriduzionit.getEntity());
	    cccausaliriduzionit.setDisplayMode(CcCausaliriduzionitCommand.EDIT);
	    model.addAttribute("cccausaliriduzionit", cccausaliriduzionit);
	    return "cccausaliriduzionit/form";
	}
	status.setComplete();
	cccausaliriduzionit.setDisplayMode(CcCausaliriduzionitCommand.VIEW);
	model.addAttribute("cccausaliriduzionit", cccausaliriduzionit);
	return "redirect:view.htm?codice=" + cccausaliriduzionit.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteccCausaliriduzionir(@RequestParam("codiceCausaleR") Integer codiceR, @RequestParam("codiceCausaleT") Integer codiceT,
	    Model model, @ModelAttribute("cccausaliriduzionit") CcCausaliriduzionitCommand cccausaliriduzionit, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// Informazioni necessarie per tornare alla pagina di modifica
	cccausaliriduzionit = new CcCausaliriduzionitCommand();
	PkId id = new PkId(codiceT);
	CcCausaliriduzionit entity = cccausaliriduzionitService.findById(id);
	cccausaliriduzionit.setEntiry(entity);
	fixRenderEntityProperty(cccausaliriduzionit.getEntity());
	cccausaliriduzionit.setDisplayMode(CcCausaliriduzionitCommand.EDIT);
	model.addAttribute("cccausaliriduzionit", cccausaliriduzionit);
	setPageAttributes(model);
	// Recupero elemento da eliminare
	CcCausaliriduzionir causaliriduzionir = ccCausaliriduzionirService.findById(new PkId(codiceR));
	try {
	    ccCausaliriduzionirService.delete(causaliriduzionir);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cccausaliriduzionit.getCcCausaliriduzionir(), true, e);
	    fixRenderEntityProperty(cccausaliriduzionit.getEntity());
	}
	setPageAttributes(model);
	return "redirect:view.htm?codice=" + cccausaliriduzionit.getEntity().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(CcCausaliriduzionit entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcCausaliriduzionit entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
