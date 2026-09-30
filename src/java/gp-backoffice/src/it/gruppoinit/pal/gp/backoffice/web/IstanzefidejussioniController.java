package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Istanzefidejussioni;
import it.gruppoinit.pal.gp.core.service.IstanzefidejussioniService;
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
@SessionAttributes("istanzefidejussioni")
public class IstanzefidejussioniController extends BaseController<Istanzefidejussioni> {

    @Autowired
    private IstanzefidejussioniService istanzefidejussioniService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Istanzefidejussioni> istanzefidejussioniList = istanzefidejussioniService.findAll(null, null);
    // ModelMap model = new ModelMap(istanzefidejussioniList);
    // boolean export = createJMesaExport(request, response, istanzefidejussioniList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("istanzefidejussioniList", istanzefidejussioniList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Istanzefidejussioni istanzefidejussioni = new Istanzefidejussioni();
    // istanzefidejussioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(istanzefidejussioni);
    // model.addAttribute("istanzefidejussioni", istanzefidejussioni);
    // setPageAttributes(model);
    // return "istanzefidejussioni/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("istanzefidejussioni") Istanzefidejussioni istanzefidejussioni,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(istanzefidejussioni);
    // istanzefidejussioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // istanzefidejussioniService.insert(istanzefidejussioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzefidejussioniService.getValidationMessages(), result, istanzefidejussioni,
    // e.getMessage());
    // fixRenderEntityProperty(istanzefidejussioni);
    // return "istanzefidejussioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzefidejussioni.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Istanzefidejussioni istanzefidejussioni = istanzefidejussioniService.findById(id);
    // fixRenderEntityProperty(istanzefidejussioni);
    // model.addAttribute("istanzefidejussioni", istanzefidejussioni);
    // setPageAttributes(model);
    // return "istanzefidejussioni/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("istanzefidejussioni") Istanzefidejussioni istanzefidejussioni,
    // BindingResult result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(istanzefidejussioni);
    // try {
    // istanzefidejussioniService.update(istanzefidejussioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzefidejussioniService.getValidationMessages(), result, istanzefidejussioni,
    // e.getMessage());
    // fixRenderEntityProperty(istanzefidejussioni);
    // return "istanzefidejussioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzefidejussioni.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("istanzefidejussioni") Istanzefidejussioni istanzefidejussioni,
    // BindingResult result, SessionStatus status) {
    //
    // Istanzefidejussioni objToDelete = istanzefidejussioniService.findById(istanzefidejussioni.getId());
    // try {
    // istanzefidejussioniService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzefidejussioniService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(istanzefidejussioni);
    // return "istanzefidejussioni/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Istanzefidejussioni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzefidejussioni entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
