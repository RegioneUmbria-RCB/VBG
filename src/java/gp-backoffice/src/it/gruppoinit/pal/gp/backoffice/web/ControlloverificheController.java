package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Controlloverifiche;
import it.gruppoinit.pal.gp.core.service.ControlloverificheService;
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
@SessionAttributes("controlloverifiche")
public class ControlloverificheController extends BaseController<Controlloverifiche> {

    @Autowired
    private ControlloverificheService controlloverificheService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Controlloverifiche> controlloverificheList = controlloverificheService.findAll(null, null);
    // ModelMap model = new ModelMap(controlloverificheList);
    // boolean export = createJMesaExport(request, response, controlloverificheList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("controlloverificheList", controlloverificheList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Controlloverifiche controlloverifiche = new Controlloverifiche();
    // controlloverifiche.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(controlloverifiche);
    // model.addAttribute("controlloverifiche", controlloverifiche);
    // setPageAttributes(model);
    // return "controlloverifiche/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("controlloverifiche") Controlloverifiche controlloverifiche, BindingResult
    // result, SessionStatus status) {
    //
    // fixMergeEntityProperty(controlloverifiche);
    // controlloverifiche.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // controlloverificheService.insert(controlloverifiche);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(controlloverificheService.getValidationMessages(), result, controlloverifiche,
    // e.getMessage());
    // fixRenderEntityProperty(controlloverifiche);
    // return "controlloverifiche/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + controlloverifiche.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Controlloverifiche controlloverifiche = controlloverificheService.findById(id);
    // fixRenderEntityProperty(controlloverifiche);
    // model.addAttribute("controlloverifiche", controlloverifiche);
    // setPageAttributes(model);
    // return "controlloverifiche/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("controlloverifiche") Controlloverifiche controlloverifiche, BindingResult
    // result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(controlloverifiche);
    // try {
    // controlloverificheService.update(controlloverifiche);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(controlloverificheService.getValidationMessages(), result, controlloverifiche,
    // e.getMessage());
    // fixRenderEntityProperty(controlloverifiche);
    // return "controlloverifiche/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + controlloverifiche.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("controlloverifiche") Controlloverifiche controlloverifiche, BindingResult
    // result, SessionStatus status) {
    //
    // Controlloverifiche objToDelete = controlloverificheService.findById(controlloverifiche.getId());
    // try {
    // controlloverificheService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(controlloverificheService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(controlloverifiche);
    // return "controlloverifiche/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(Controlloverifiche entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Controlloverifiche entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
