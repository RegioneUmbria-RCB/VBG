package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.EquitaliaTracciatiCfg;
import it.gruppoinit.pal.gp.core.service.EquitaliaTracciatiCfgService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 
 * @author 
 */
@Controller
@SessionAttributes("equitaliatracciaticfg")
public class EquitaliaTracciatiCfgController extends BaseController<EquitaliaTracciatiCfg> {

    @Autowired
    private EquitaliaTracciatiCfgService equitaliatracciaticfgService;
    @Autowired
    private SoftwareService softwareService;

    //    @RequestMapping
    //    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    //	List<EquitaliaTracciatiCfg> equitaliatracciaticfgList = equitaliatracciaticfgService.findAll(null, null);
    //	ModelMap model = new ModelMap(equitaliatracciaticfgList);
    //	boolean export = createJMesaExport(request, response, equitaliatracciaticfgList);
    //	if (export) {
    //	    return null;
    //	}
    //	model.addAttribute("equitaliatracciaticfgList", equitaliatracciaticfgList);
    //	return model;
    //    }
    //
    //    @RequestMapping
    //    public String create(Model model) {
    //
    //	EquitaliaTracciatiCfg equitaliatracciaticfg = new EquitaliaTracciatiCfg();
    //	equitaliatracciaticfg.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    //	fixRenderEntityProperty(equitaliatracciaticfg);
    //	model.addAttribute("equitaliatracciaticfg", equitaliatracciaticfg);
    //	setPageAttributes(model);
    //	return "equitaliatracciaticfg/form";
    //    }
    //
    //    @RequestMapping
    //    public String insert(@ModelAttribute("equitaliatracciaticfg") EquitaliaTracciatiCfg equitaliatracciaticfg, BindingResult result,
    //	    SessionStatus status) {
    //
    //	fixMergeEntityProperty(equitaliatracciaticfg);
    //	equitaliatracciaticfg.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    //	try {
    //	    equitaliatracciaticfgService.insert(equitaliatracciaticfg);
    //	} catch (Exception e) {
    //	    copyErrorsToBindingResult(equitaliatracciaticfgService.getValidationMessages(), result, equitaliatracciaticfg, e.getMessage());
    //	    fixRenderEntityProperty(equitaliatracciaticfg);
    //	    return "equitaliatracciaticfg/form";
    //	}
    //	status.setComplete();
    //	return "redirect:view.htm?codice=" + equitaliatracciaticfg.getId().getCodice() + "&status_msg=01";
    //    }
    //
    //    @RequestMapping
    //    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    //	PkId id = new PkId(codice);
    //	EquitaliaTracciatiCfg equitaliatracciaticfg = equitaliatracciaticfgService.findById(id);
    //	fixRenderEntityProperty(equitaliatracciaticfg);
    //	model.addAttribute("equitaliatracciaticfg", equitaliatracciaticfg);
    //	setPageAttributes(model);
    //	return "equitaliatracciaticfg/form";
    //    }
    //
    //    @RequestMapping
    //    public String update(@ModelAttribute("equitaliatracciaticfg") EquitaliaTracciatiCfg equitaliatracciaticfg, BindingResult result,
    //	    SessionStatus status, HttpServletRequest request) {
    //
    //	fixMergeEntityProperty(equitaliatracciaticfg);
    //	try {
    //	    equitaliatracciaticfgService.update(equitaliatracciaticfg);
    //	} catch (Exception e) {
    //	    copyErrorsToBindingResult(equitaliatracciaticfgService.getValidationMessages(), result, equitaliatracciaticfg, e.getMessage());
    //	    fixRenderEntityProperty(equitaliatracciaticfg);
    //	    return "equitaliatracciaticfg/form";
    //	}
    //	status.setComplete();
    //	return "redirect:view.htm?codice=" + equitaliatracciaticfg.getId().getCodice() + "&status_msg=02";
    //    }
    //
    //    @RequestMapping
    //    public String delete(@ModelAttribute("equitaliatracciaticfg") EquitaliaTracciatiCfg equitaliatracciaticfg, BindingResult result,
    //	    SessionStatus status) {
    //
    //	EquitaliaTracciatiCfg objToDelete = equitaliatracciaticfgService.findById(equitaliatracciaticfg.getId());
    //	try {
    //	    equitaliatracciaticfgService.delete(objToDelete);
    //	} catch (Exception e) {
    //	    copyErrorsToBindingResult(equitaliatracciaticfgService.getValidationMessages(), result, objToDelete, e.getMessage());
    //	    fixRenderEntityProperty(equitaliatracciaticfg);
    //	    return "equitaliatracciaticfg/form";
    //	}
    //	status.setComplete();
    //	return "redirect:list.htm";
    //    }

    @Override
    protected void fixMergeEntityProperty(EquitaliaTracciatiCfg entity) {

    }

    @Override
    protected void fixRenderEntityProperty(EquitaliaTracciatiCfg entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
