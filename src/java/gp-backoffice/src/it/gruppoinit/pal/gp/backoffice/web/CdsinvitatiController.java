package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Cdsinvitati;
import it.gruppoinit.pal.gp.core.service.CdsinvitatiService;
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
@SessionAttributes("cdsinvitati")
public class CdsinvitatiController extends BaseController<Cdsinvitati> {

    @Autowired
    private CdsinvitatiService cdsinvitatiService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Cdsinvitati> cdsinvitatiList = cdsinvitatiService.findAll(null, null);
    // ModelMap model = new ModelMap(cdsinvitatiList);
    // boolean export = createJMesaExport(request, response, cdsinvitatiList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("cdsinvitatiList", cdsinvitatiList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Cdsinvitati cdsinvitati = new Cdsinvitati();
    // cdsinvitati.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(cdsinvitati);
    // model.addAttribute("cdsinvitati", cdsinvitati);
    // setPageAttributes(model);
    // return "cdsinvitati/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("cdsinvitati") Cdsinvitati cdsinvitati, BindingResult result, SessionStatus
    // status) {
    //
    // fixMergeEntityProperty(cdsinvitati);
    // cdsinvitati.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // cdsinvitatiService.insert(cdsinvitati);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cdsinvitatiService.getValidationMessages(), result, cdsinvitati, e.getMessage());
    // fixRenderEntityProperty(cdsinvitati);
    // return "cdsinvitati/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cdsinvitati.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Cdsinvitati cdsinvitati = cdsinvitatiService.findById(id);
    // fixRenderEntityProperty(cdsinvitati);
    // model.addAttribute("cdsinvitati", cdsinvitati);
    // setPageAttributes(model);
    // return "cdsinvitati/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("cdsinvitati") Cdsinvitati cdsinvitati, BindingResult result, SessionStatus
    // status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(cdsinvitati);
    // try {
    // cdsinvitatiService.update(cdsinvitati);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cdsinvitatiService.getValidationMessages(), result, cdsinvitati, e.getMessage());
    // fixRenderEntityProperty(cdsinvitati);
    // return "cdsinvitati/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cdsinvitati.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("cdsinvitati") Cdsinvitati cdsinvitati, BindingResult result, SessionStatus
    // status) {
    //
    // Cdsinvitati objToDelete = cdsinvitatiService.findById(cdsinvitati.getId());
    // try {
    // cdsinvitatiService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cdsinvitatiService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(cdsinvitati);
    // return "cdsinvitati/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(Cdsinvitati entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Cdsinvitati entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
