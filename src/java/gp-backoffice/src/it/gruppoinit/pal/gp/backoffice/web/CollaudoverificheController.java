package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Collaudoverifiche;
import it.gruppoinit.pal.gp.core.service.CollaudoverificheService;
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
@SessionAttributes("collaudoverifiche")
public class CollaudoverificheController extends BaseController<Collaudoverifiche> {

    @Autowired
    private CollaudoverificheService collaudoverificheService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Collaudoverifiche> collaudoverificheList = collaudoverificheService.findAll(null, null);
    // ModelMap model = new ModelMap(collaudoverificheList);
    // boolean export = createJMesaExport(request, response, collaudoverificheList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("collaudoverificheList", collaudoverificheList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Collaudoverifiche collaudoverifiche = new Collaudoverifiche();
    // collaudoverifiche.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(collaudoverifiche);
    // model.addAttribute("collaudoverifiche", collaudoverifiche);
    // setPageAttributes(model);
    // return "collaudoverifiche/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("collaudoverifiche") Collaudoverifiche collaudoverifiche, BindingResult
    // result, SessionStatus status) {
    //
    // fixMergeEntityProperty(collaudoverifiche);
    // collaudoverifiche.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // collaudoverificheService.insert(collaudoverifiche);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(collaudoverificheService.getValidationMessages(), result, collaudoverifiche,
    // e.getMessage());
    // fixRenderEntityProperty(collaudoverifiche);
    // return "collaudoverifiche/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + collaudoverifiche.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Collaudoverifiche collaudoverifiche = collaudoverificheService.findById(id);
    // fixRenderEntityProperty(collaudoverifiche);
    // model.addAttribute("collaudoverifiche", collaudoverifiche);
    // setPageAttributes(model);
    // return "collaudoverifiche/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("collaudoverifiche") Collaudoverifiche collaudoverifiche, BindingResult
    // result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(collaudoverifiche);
    // try {
    // collaudoverificheService.update(collaudoverifiche);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(collaudoverificheService.getValidationMessages(), result, collaudoverifiche,
    // e.getMessage());
    // fixRenderEntityProperty(collaudoverifiche);
    // return "collaudoverifiche/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + collaudoverifiche.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("collaudoverifiche") Collaudoverifiche collaudoverifiche, BindingResult
    // result, SessionStatus status) {
    //
    // Collaudoverifiche objToDelete = collaudoverificheService.findById(collaudoverifiche.getId());
    // try {
    // collaudoverificheService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(collaudoverificheService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(collaudoverifiche);
    // return "collaudoverifiche/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Collaudoverifiche entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Collaudoverifiche entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
