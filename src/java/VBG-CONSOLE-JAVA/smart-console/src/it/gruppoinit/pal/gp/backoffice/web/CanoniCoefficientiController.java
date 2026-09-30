package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CanoniCoefficienti;
import it.gruppoinit.pal.gp.core.service.CanoniCoefficientiService;
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
@SessionAttributes("canonicoefficienti")
public class CanoniCoefficientiController extends BaseController<CanoniCoefficienti> {

    @Autowired
    private CanoniCoefficientiService canonicoefficientiService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CanoniCoefficienti> canonicoefficientiList = canonicoefficientiService.findAll(null, null);
    // ModelMap model = new ModelMap(canonicoefficientiList);
    // boolean export = createJMesaExport(request, response, canonicoefficientiList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("canonicoefficientiList", canonicoefficientiList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CanoniCoefficienti canonicoefficienti = new CanoniCoefficienti();
    // canonicoefficienti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(canonicoefficienti);
    // model.addAttribute("canonicoefficienti", canonicoefficienti);
    // setPageAttributes(model);
    // return "canonicoefficienti/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("canonicoefficienti") CanoniCoefficienti canonicoefficienti, BindingResult
    // result, SessionStatus status) {
    //
    // fixMergeEntityProperty(canonicoefficienti);
    // canonicoefficienti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // canonicoefficientiService.insert(canonicoefficienti);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canonicoefficientiService.getValidationMessages(), result, canonicoefficienti,
    // e.getMessage());
    // fixRenderEntityProperty(canonicoefficienti);
    // return "canonicoefficienti/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + canonicoefficienti.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CanoniCoefficienti canonicoefficienti = canonicoefficientiService.findById(id);
    // fixRenderEntityProperty(canonicoefficienti);
    // model.addAttribute("canonicoefficienti", canonicoefficienti);
    // setPageAttributes(model);
    // return "canonicoefficienti/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("canonicoefficienti") CanoniCoefficienti canonicoefficienti, BindingResult
    // result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(canonicoefficienti);
    // try {
    // canonicoefficientiService.update(canonicoefficienti);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canonicoefficientiService.getValidationMessages(), result, canonicoefficienti,
    // e.getMessage());
    // fixRenderEntityProperty(canonicoefficienti);
    // return "canonicoefficienti/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + canonicoefficienti.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("canonicoefficienti") CanoniCoefficienti canonicoefficienti, BindingResult
    // result, SessionStatus status) {
    //
    // CanoniCoefficienti objToDelete = canonicoefficientiService.findById(canonicoefficienti.getId());
    // try {
    // canonicoefficientiService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canonicoefficientiService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(canonicoefficienti);
    // return "canonicoefficienti/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CanoniCoefficienti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CanoniCoefficienti entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
