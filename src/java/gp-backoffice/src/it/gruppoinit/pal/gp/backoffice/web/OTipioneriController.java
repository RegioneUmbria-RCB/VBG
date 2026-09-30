package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OTipioneri;
import it.gruppoinit.pal.gp.core.service.OTipioneriService;
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
@SessionAttributes("otipioneri")
public class OTipioneriController extends BaseController<OTipioneri> {

    @Autowired
    private OTipioneriService otipioneriService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OTipioneri> otipioneriList = otipioneriService.findAll(null, null);
    // ModelMap model = new ModelMap(otipioneriList);
    // boolean export = createJMesaExport(request, response, otipioneriList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("otipioneriList", otipioneriList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OTipioneri otipioneri = new OTipioneri();
    // otipioneri.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(otipioneri);
    // model.addAttribute("otipioneri", otipioneri);
    // setPageAttributes(model);
    // return "otipioneri/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("otipioneri") OTipioneri otipioneri, BindingResult result, SessionStatus
    // status) {
    //
    // fixMergeEntityProperty(otipioneri);
    // otipioneri.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // otipioneriService.insert(otipioneri);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(otipioneriService.getValidationMessages(), result, otipioneri, e.getMessage());
    // fixRenderEntityProperty(otipioneri);
    // return "otipioneri/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + otipioneri.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OTipioneri otipioneri = otipioneriService.findById(id);
    // fixRenderEntityProperty(otipioneri);
    // model.addAttribute("otipioneri", otipioneri);
    // setPageAttributes(model);
    // return "otipioneri/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("otipioneri") OTipioneri otipioneri, BindingResult result, SessionStatus
    // status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(otipioneri);
    // try {
    // otipioneriService.update(otipioneri);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(otipioneriService.getValidationMessages(), result, otipioneri, e.getMessage());
    // fixRenderEntityProperty(otipioneri);
    // return "otipioneri/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + otipioneri.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("otipioneri") OTipioneri otipioneri, BindingResult result, SessionStatus
    // status) {
    //
    // OTipioneri objToDelete = otipioneriService.findById(otipioneri.getId());
    // try {
    // otipioneriService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(otipioneriService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(otipioneri);
    // return "otipioneri/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(OTipioneri entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OTipioneri entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
