package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OTabellad;
import it.gruppoinit.pal.gp.core.service.OTabelladService;
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
@SessionAttributes("otabellad")
public class OTabelladController extends BaseController<OTabellad> {

    @Autowired
    private OTabelladService otabelladService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OTabellad> otabelladList = otabelladService.findAll(null, null);
    // ModelMap model = new ModelMap(otabelladList);
    // boolean export = createJMesaExport(request, response, otabelladList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("otabelladList", otabelladList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OTabellad otabellad = new OTabellad();
    // otabellad.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(otabellad);
    // model.addAttribute("otabellad", otabellad);
    // setPageAttributes(model);
    // return "otabellad/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("otabellad") OTabellad otabellad, BindingResult result, SessionStatus
    // status) {
    //
    // fixMergeEntityProperty(otabellad);
    // otabellad.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // otabelladService.insert(otabellad);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(otabelladService.getValidationMessages(), result, otabellad, e.getMessage());
    // fixRenderEntityProperty(otabellad);
    // return "otabellad/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + otabellad.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OTabellad otabellad = otabelladService.findById(id);
    // fixRenderEntityProperty(otabellad);
    // model.addAttribute("otabellad", otabellad);
    // setPageAttributes(model);
    // return "otabellad/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("otabellad") OTabellad otabellad, BindingResult result, SessionStatus
    // status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(otabellad);
    // try {
    // otabelladService.update(otabellad);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(otabelladService.getValidationMessages(), result, otabellad, e.getMessage());
    // fixRenderEntityProperty(otabellad);
    // return "otabellad/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + otabellad.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("otabellad") OTabellad otabellad, BindingResult result, SessionStatus
    // status) {
    //
    // OTabellad objToDelete = otabelladService.findById(otabellad.getId());
    // try {
    // otabelladService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(otabelladService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(otabellad);
    // return "otabellad/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(OTabellad entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OTabellad entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
