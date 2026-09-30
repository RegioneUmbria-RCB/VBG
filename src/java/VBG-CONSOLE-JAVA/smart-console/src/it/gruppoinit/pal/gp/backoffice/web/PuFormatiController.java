package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.PuFormati;
import it.gruppoinit.pal.gp.core.service.PuFormatiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 
 * @author 
 */
//DAELIMINARE @Controller
@SessionAttributes("puformati")
public class PuFormatiController extends BaseController<PuFormati> {

    @Autowired
    private PuFormatiService puformatiService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<PuFormati> puformatiList = puformatiService.findAll(null, null);
    // ModelMap model = new ModelMap(puformatiList);
    // boolean export = createJMesaExport(request, response, puformatiList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("puformatiList", puformatiList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // PuFormati puformati = new PuFormati();
    // puformati.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(puformati);
    // model.addAttribute("puformati", puformati);
    // setPageAttributes(model);
    // return "puformati/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("puformati") PuFormati puformati, BindingResult result, SessionStatus
    // status) {
    //
    // fixMergeEntityProperty(puformati);
    // puformati.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // puformatiService.insert(puformati);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(puformatiService.getValidationMessages(), result, puformati, e.getMessage());
    // fixRenderEntityProperty(puformati);
    // return "puformati/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + puformati.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // PuFormati puformati = puformatiService.findById(id);
    // fixRenderEntityProperty(puformati);
    // model.addAttribute("puformati", puformati);
    // setPageAttributes(model);
    // return "puformati/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("puformati") PuFormati puformati, BindingResult result, SessionStatus
    // status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(puformati);
    // try {
    // puformatiService.update(puformati);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(puformatiService.getValidationMessages(), result, puformati, e.getMessage());
    // fixRenderEntityProperty(puformati);
    // return "puformati/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + puformati.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("puformati") PuFormati puformati, BindingResult result, SessionStatus
    // status) {
    //
    // PuFormati objToDelete = puformatiService.findById(puformati.getId());
    // try {
    // puformatiService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(puformatiService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(puformati);
    // return "puformati/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(PuFormati entity) {

    }

    @Override
    protected void fixRenderEntityProperty(PuFormati entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
