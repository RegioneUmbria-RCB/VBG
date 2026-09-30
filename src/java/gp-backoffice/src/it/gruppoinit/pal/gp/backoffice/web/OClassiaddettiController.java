package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OClassiaddetti;
import it.gruppoinit.pal.gp.core.service.OClassiaddettiService;
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
@SessionAttributes("oclassiaddetti")
public class OClassiaddettiController extends BaseController<OClassiaddetti> {

    @Autowired
    private OClassiaddettiService oclassiaddettiService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OClassiaddetti> oclassiaddettiList = oclassiaddettiService.findAll(null, null);
    // ModelMap model = new ModelMap(oclassiaddettiList);
    // boolean export = createJMesaExport(request, response, oclassiaddettiList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("oclassiaddettiList", oclassiaddettiList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OClassiaddetti oclassiaddetti = new OClassiaddetti();
    // oclassiaddetti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(oclassiaddetti);
    // model.addAttribute("oclassiaddetti", oclassiaddetti);
    // setPageAttributes(model);
    // return "oclassiaddetti/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("oclassiaddetti") OClassiaddetti oclassiaddetti, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(oclassiaddetti);
    // oclassiaddetti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // oclassiaddettiService.insert(oclassiaddetti);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oclassiaddettiService.getValidationMessages(), result, oclassiaddetti, e.getMessage());
    // fixRenderEntityProperty(oclassiaddetti);
    // return "oclassiaddetti/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oclassiaddetti.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OClassiaddetti oclassiaddetti = oclassiaddettiService.findById(id);
    // fixRenderEntityProperty(oclassiaddetti);
    // model.addAttribute("oclassiaddetti", oclassiaddetti);
    // setPageAttributes(model);
    // return "oclassiaddetti/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("oclassiaddetti") OClassiaddetti oclassiaddetti, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(oclassiaddetti);
    // try {
    // oclassiaddettiService.update(oclassiaddetti);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oclassiaddettiService.getValidationMessages(), result, oclassiaddetti, e.getMessage());
    // fixRenderEntityProperty(oclassiaddetti);
    // return "oclassiaddetti/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oclassiaddetti.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("oclassiaddetti") OClassiaddetti oclassiaddetti, BindingResult result,
    // SessionStatus status) {
    //
    // OClassiaddetti objToDelete = oclassiaddettiService.findById(oclassiaddetti.getId());
    // try {
    // oclassiaddettiService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oclassiaddettiService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(oclassiaddetti);
    // return "oclassiaddetti/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(OClassiaddetti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OClassiaddetti entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
