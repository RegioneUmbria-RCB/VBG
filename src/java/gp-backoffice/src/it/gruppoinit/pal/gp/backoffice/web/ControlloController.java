package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Controllo;
import it.gruppoinit.pal.gp.core.service.ControlloService;
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
@SessionAttributes("controllo")
public class ControlloController extends BaseController<Controllo> {

    @Autowired
    private ControlloService controlloService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Controllo> controlloList = controlloService.findAll(null, null);
    // ModelMap model = new ModelMap(controlloList);
    // boolean export = createJMesaExport(request, response, controlloList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("controlloList", controlloList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Controllo controllo = new Controllo();
    // controllo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(controllo);
    // model.addAttribute("controllo", controllo);
    // setPageAttributes(model);
    // return "controllo/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("controllo") Controllo controllo, BindingResult result, SessionStatus
    // status) {
    //
    // fixMergeEntityProperty(controllo);
    // controllo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // controlloService.insert(controllo);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(controlloService.getValidationMessages(), result, controllo, e.getMessage());
    // fixRenderEntityProperty(controllo);
    // return "controllo/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + controllo.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Controllo controllo = controlloService.findById(id);
    // fixRenderEntityProperty(controllo);
    // model.addAttribute("controllo", controllo);
    // setPageAttributes(model);
    // return "controllo/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("controllo") Controllo controllo, BindingResult result, SessionStatus
    // status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(controllo);
    // try {
    // controlloService.update(controllo);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(controlloService.getValidationMessages(), result, controllo, e.getMessage());
    // fixRenderEntityProperty(controllo);
    // return "controllo/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + controllo.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("controllo") Controllo controllo, BindingResult result, SessionStatus
    // status) {
    //
    // Controllo objToDelete = controlloService.findById(controllo.getId());
    // try {
    // controlloService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(controlloService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(controllo);
    // return "controllo/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(Controllo entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Controllo entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
