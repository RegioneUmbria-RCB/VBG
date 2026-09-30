package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CanoniRiduzioni;
import it.gruppoinit.pal.gp.core.service.CanoniRiduzioniService;
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
@SessionAttributes("canoniriduzioni")
public class CanoniRiduzioniController extends BaseController<CanoniRiduzioni> {

    @Autowired
    private CanoniRiduzioniService canoniriduzioniService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CanoniRiduzioni> canoniriduzioniList = canoniriduzioniService.findAll(null, null);
    // ModelMap model = new ModelMap(canoniriduzioniList);
    // boolean export = createJMesaExport(request, response, canoniriduzioniList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("canoniriduzioniList", canoniriduzioniList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CanoniRiduzioni canoniriduzioni = new CanoniRiduzioni();
    // canoniriduzioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(canoniriduzioni);
    // model.addAttribute("canoniriduzioni", canoniriduzioni);
    // setPageAttributes(model);
    // return "canoniriduzioni/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("canoniriduzioni") CanoniRiduzioni canoniriduzioni, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(canoniriduzioni);
    // canoniriduzioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // canoniriduzioniService.insert(canoniriduzioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canoniriduzioniService.getValidationMessages(), result, canoniriduzioni,
    // e.getMessage());
    // fixRenderEntityProperty(canoniriduzioni);
    // return "canoniriduzioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + canoniriduzioni.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CanoniRiduzioni canoniriduzioni = canoniriduzioniService.findById(id);
    // fixRenderEntityProperty(canoniriduzioni);
    // model.addAttribute("canoniriduzioni", canoniriduzioni);
    // setPageAttributes(model);
    // return "canoniriduzioni/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("canoniriduzioni") CanoniRiduzioni canoniriduzioni, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(canoniriduzioni);
    // try {
    // canoniriduzioniService.update(canoniriduzioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canoniriduzioniService.getValidationMessages(), result, canoniriduzioni,
    // e.getMessage());
    // fixRenderEntityProperty(canoniriduzioni);
    // return "canoniriduzioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + canoniriduzioni.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("canoniriduzioni") CanoniRiduzioni canoniriduzioni, BindingResult result,
    // SessionStatus status) {
    //
    // CanoniRiduzioni objToDelete = canoniriduzioniService.findById(canoniriduzioni.getId());
    // try {
    // canoniriduzioniService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canoniriduzioniService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(canoniriduzioni);
    // return "canoniriduzioni/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CanoniRiduzioni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CanoniRiduzioni entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
