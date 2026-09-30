package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Cdsinvitati2;
import it.gruppoinit.pal.gp.core.service.Cdsinvitati2Service;
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
@SessionAttributes("cdsinvitati2")
public class Cdsinvitati2Controller extends BaseController<Cdsinvitati2> {

    @Autowired
    private Cdsinvitati2Service cdsinvitati2Service;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Cdsinvitati2> cdsinvitati2List = cdsinvitati2Service.findAll(null, null);
    // ModelMap model = new ModelMap(cdsinvitati2List);
    // boolean export = createJMesaExport(request, response, cdsinvitati2List);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("cdsinvitati2List", cdsinvitati2List);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Cdsinvitati2 cdsinvitati2 = new Cdsinvitati2();
    // cdsinvitati2.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(cdsinvitati2);
    // model.addAttribute("cdsinvitati2", cdsinvitati2);
    // setPageAttributes(model);
    // return "cdsinvitati2/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("cdsinvitati2") Cdsinvitati2 cdsinvitati2, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(cdsinvitati2);
    // cdsinvitati2.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // cdsinvitati2Service.insert(cdsinvitati2);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cdsinvitati2Service.getValidationMessages(), result, cdsinvitati2, e.getMessage());
    // fixRenderEntityProperty(cdsinvitati2);
    // return "cdsinvitati2/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cdsinvitati2.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Cdsinvitati2 cdsinvitati2 = cdsinvitati2Service.findById(id);
    // fixRenderEntityProperty(cdsinvitati2);
    // model.addAttribute("cdsinvitati2", cdsinvitati2);
    // setPageAttributes(model);
    // return "cdsinvitati2/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("cdsinvitati2") Cdsinvitati2 cdsinvitati2, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(cdsinvitati2);
    // try {
    // cdsinvitati2Service.update(cdsinvitati2);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cdsinvitati2Service.getValidationMessages(), result, cdsinvitati2, e.getMessage());
    // fixRenderEntityProperty(cdsinvitati2);
    // return "cdsinvitati2/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cdsinvitati2.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("cdsinvitati2") Cdsinvitati2 cdsinvitati2, BindingResult result,
    // SessionStatus status) {
    //
    // Cdsinvitati2 objToDelete = cdsinvitati2Service.findById(cdsinvitati2.getId());
    // try {
    // cdsinvitati2Service.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cdsinvitati2Service.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(cdsinvitati2);
    // return "cdsinvitati2/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Cdsinvitati2 entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Cdsinvitati2 entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
