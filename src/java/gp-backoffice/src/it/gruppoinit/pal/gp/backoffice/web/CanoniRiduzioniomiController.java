package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CanoniRiduzioniomi;
import it.gruppoinit.pal.gp.core.service.CanoniRiduzioniomiService;
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
@SessionAttributes("canoniriduzioniomi")
public class CanoniRiduzioniomiController extends BaseController<CanoniRiduzioniomi> {

    @Autowired
    private CanoniRiduzioniomiService canoniriduzioniomiService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CanoniRiduzioniomi> canoniriduzioniomiList = canoniriduzioniomiService.findAll(null, null);
    // ModelMap model = new ModelMap(canoniriduzioniomiList);
    // boolean export = createJMesaExport(request, response, canoniriduzioniomiList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("canoniriduzioniomiList", canoniriduzioniomiList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CanoniRiduzioniomi canoniriduzioniomi = new CanoniRiduzioniomi();
    // canoniriduzioniomi.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(canoniriduzioniomi);
    // model.addAttribute("canoniriduzioniomi", canoniriduzioniomi);
    // setPageAttributes(model);
    // return "canoniriduzioniomi/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("canoniriduzioniomi") CanoniRiduzioniomi canoniriduzioniomi, BindingResult
    // result, SessionStatus status) {
    //
    // fixMergeEntityProperty(canoniriduzioniomi);
    // canoniriduzioniomi.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // canoniriduzioniomiService.insert(canoniriduzioniomi);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canoniriduzioniomiService.getValidationMessages(), result, canoniriduzioniomi,
    // e.getMessage());
    // fixRenderEntityProperty(canoniriduzioniomi);
    // return "canoniriduzioniomi/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + canoniriduzioniomi.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CanoniRiduzioniomi canoniriduzioniomi = canoniriduzioniomiService.findById(id);
    // fixRenderEntityProperty(canoniriduzioniomi);
    // model.addAttribute("canoniriduzioniomi", canoniriduzioniomi);
    // setPageAttributes(model);
    // return "canoniriduzioniomi/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("canoniriduzioniomi") CanoniRiduzioniomi canoniriduzioniomi, BindingResult
    // result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(canoniriduzioniomi);
    // try {
    // canoniriduzioniomiService.update(canoniriduzioniomi);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canoniriduzioniomiService.getValidationMessages(), result, canoniriduzioniomi,
    // e.getMessage());
    // fixRenderEntityProperty(canoniriduzioniomi);
    // return "canoniriduzioniomi/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + canoniriduzioniomi.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("canoniriduzioniomi") CanoniRiduzioniomi canoniriduzioniomi, BindingResult
    // result, SessionStatus status) {
    //
    // CanoniRiduzioniomi objToDelete = canoniriduzioniomiService.findById(canoniriduzioniomi.getId());
    // try {
    // canoniriduzioniomiService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canoniriduzioniomiService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(canoniriduzioniomi);
    // return "canoniriduzioniomi/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CanoniRiduzioniomi entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CanoniRiduzioniomi entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
