package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Istanzefrontoffice;
import it.gruppoinit.pal.gp.core.service.IstanzefrontofficeService;
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
@SessionAttributes("istanzefrontoffice")
public class IstanzefrontofficeController extends BaseController<Istanzefrontoffice> {

    @Autowired
    private IstanzefrontofficeService istanzefrontofficeService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Istanzefrontoffice> istanzefrontofficeList = istanzefrontofficeService.findAll(null, null);
    // ModelMap model = new ModelMap(istanzefrontofficeList);
    // boolean export = createJMesaExport(request, response, istanzefrontofficeList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("istanzefrontofficeList", istanzefrontofficeList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Istanzefrontoffice istanzefrontoffice = new Istanzefrontoffice();
    // istanzefrontoffice.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(istanzefrontoffice);
    // model.addAttribute("istanzefrontoffice", istanzefrontoffice);
    // setPageAttributes(model);
    // return "istanzefrontoffice/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("istanzefrontoffice") Istanzefrontoffice istanzefrontoffice, BindingResult
    // result, SessionStatus status) {
    //
    // fixMergeEntityProperty(istanzefrontoffice);
    // istanzefrontoffice.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // istanzefrontofficeService.insert(istanzefrontoffice);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzefrontofficeService.getValidationMessages(), result, istanzefrontoffice,
    // e.getMessage());
    // fixRenderEntityProperty(istanzefrontoffice);
    // return "istanzefrontoffice/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzefrontoffice.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Istanzefrontoffice istanzefrontoffice = istanzefrontofficeService.findById(id);
    // fixRenderEntityProperty(istanzefrontoffice);
    // model.addAttribute("istanzefrontoffice", istanzefrontoffice);
    // setPageAttributes(model);
    // return "istanzefrontoffice/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("istanzefrontoffice") Istanzefrontoffice istanzefrontoffice, BindingResult
    // result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(istanzefrontoffice);
    // try {
    // istanzefrontofficeService.update(istanzefrontoffice);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzefrontofficeService.getValidationMessages(), result, istanzefrontoffice,
    // e.getMessage());
    // fixRenderEntityProperty(istanzefrontoffice);
    // return "istanzefrontoffice/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzefrontoffice.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("istanzefrontoffice") Istanzefrontoffice istanzefrontoffice, BindingResult
    // result, SessionStatus status) {
    //
    // Istanzefrontoffice objToDelete = istanzefrontofficeService.findById(istanzefrontoffice.getId());
    // try {
    // istanzefrontofficeService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzefrontofficeService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(istanzefrontoffice);
    // return "istanzefrontoffice/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(Istanzefrontoffice entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzefrontoffice entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
