package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Domandefrontalbero;
import it.gruppoinit.pal.gp.core.service.DomandefrontalberoService;
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
@SessionAttributes("domandefrontalbero")
public class DomandefrontalberoController extends BaseController<Domandefrontalbero> {

    @Autowired
    private DomandefrontalberoService domandefrontalberoService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Domandefrontalbero> domandefrontalberoList = domandefrontalberoService.findAll(null, null);
    // ModelMap model = new ModelMap(domandefrontalberoList);
    // boolean export = createJMesaExport(request, response, domandefrontalberoList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("domandefrontalberoList", domandefrontalberoList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Domandefrontalbero domandefrontalbero = new Domandefrontalbero();
    // domandefrontalbero.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(domandefrontalbero);
    // model.addAttribute("domandefrontalbero", domandefrontalbero);
    // setPageAttributes(model);
    // return "domandefrontalbero/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("domandefrontalbero") Domandefrontalbero domandefrontalbero, BindingResult
    // result, SessionStatus status) {
    //
    // fixMergeEntityProperty(domandefrontalbero);
    // domandefrontalbero.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // domandefrontalberoService.insert(domandefrontalbero);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(domandefrontalberoService.getValidationMessages(), result, domandefrontalbero,
    // e.getMessage());
    // fixRenderEntityProperty(domandefrontalbero);
    // return "domandefrontalbero/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + domandefrontalbero.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Domandefrontalbero domandefrontalbero = domandefrontalberoService.findById(id);
    // fixRenderEntityProperty(domandefrontalbero);
    // model.addAttribute("domandefrontalbero", domandefrontalbero);
    // setPageAttributes(model);
    // return "domandefrontalbero/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("domandefrontalbero") Domandefrontalbero domandefrontalbero, BindingResult
    // result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(domandefrontalbero);
    // try {
    // domandefrontalberoService.update(domandefrontalbero);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(domandefrontalberoService.getValidationMessages(), result, domandefrontalbero,
    // e.getMessage());
    // fixRenderEntityProperty(domandefrontalbero);
    // return "domandefrontalbero/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + domandefrontalbero.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("domandefrontalbero") Domandefrontalbero domandefrontalbero, BindingResult
    // result, SessionStatus status) {
    //
    // Domandefrontalbero objToDelete = domandefrontalberoService.findById(domandefrontalbero.getId());
    // try {
    // domandefrontalberoService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(domandefrontalberoService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(domandefrontalbero);
    // return "domandefrontalbero/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Domandefrontalbero entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Domandefrontalbero entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
