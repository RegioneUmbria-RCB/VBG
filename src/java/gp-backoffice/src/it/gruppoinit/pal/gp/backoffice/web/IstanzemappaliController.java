package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.service.IstanzemappaliService;
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
@SessionAttributes("istanzemappali")
public class IstanzemappaliController extends BaseController<Istanzemappali> {

    @Autowired
    private IstanzemappaliService istanzemappaliService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Istanzemappali> istanzemappaliList = istanzemappaliService.findAll(null, null);
    // ModelMap model = new ModelMap(istanzemappaliList);
    // boolean export = createJMesaExport(request, response, istanzemappaliList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("istanzemappaliList", istanzemappaliList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Istanzemappali istanzemappali = new Istanzemappali();
    // istanzemappali.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(istanzemappali);
    // model.addAttribute("istanzemappali", istanzemappali);
    // setPageAttributes(model);
    // return "istanzemappali/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("istanzemappali") Istanzemappali istanzemappali, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(istanzemappali);
    // istanzemappali.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // istanzemappaliService.insert(istanzemappali);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzemappaliService.getValidationMessages(), result, istanzemappali, e.getMessage());
    // fixRenderEntityProperty(istanzemappali);
    // return "istanzemappali/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzemappali.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Istanzemappali istanzemappali = istanzemappaliService.findById(id);
    // fixRenderEntityProperty(istanzemappali);
    // model.addAttribute("istanzemappali", istanzemappali);
    // setPageAttributes(model);
    // return "istanzemappali/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("istanzemappali") Istanzemappali istanzemappali, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(istanzemappali);
    // try {
    // istanzemappaliService.update(istanzemappali);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzemappaliService.getValidationMessages(), result, istanzemappali, e.getMessage());
    // fixRenderEntityProperty(istanzemappali);
    // return "istanzemappali/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzemappali.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("istanzemappali") Istanzemappali istanzemappali, BindingResult result,
    // SessionStatus status) {
    //
    // Istanzemappali objToDelete = istanzemappaliService.findById(istanzemappali.getId());
    // try {
    // istanzemappaliService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzemappaliService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(istanzemappali);
    // return "istanzemappali/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(Istanzemappali entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzemappali entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
