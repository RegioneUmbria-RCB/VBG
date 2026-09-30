package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Istanzepeoplet;
import it.gruppoinit.pal.gp.core.service.IstanzepeopletService;
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
@SessionAttributes("istanzepeoplet")
public class IstanzepeopletController extends BaseController<Istanzepeoplet> {

    @Autowired
    private IstanzepeopletService istanzepeopletService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Istanzepeoplet> istanzepeopletList = istanzepeopletService.findAll(null, null);
    // ModelMap model = new ModelMap(istanzepeopletList);
    // boolean export = createJMesaExport(request, response, istanzepeopletList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("istanzepeopletList", istanzepeopletList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Istanzepeoplet istanzepeoplet = new Istanzepeoplet();
    // istanzepeoplet.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(istanzepeoplet);
    // model.addAttribute("istanzepeoplet", istanzepeoplet);
    // setPageAttributes(model);
    // return "istanzepeoplet/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("istanzepeoplet") Istanzepeoplet istanzepeoplet, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(istanzepeoplet);
    // istanzepeoplet.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // istanzepeopletService.insert(istanzepeoplet);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzepeopletService.getValidationMessages(), result, istanzepeoplet, e.getMessage());
    // fixRenderEntityProperty(istanzepeoplet);
    // return "istanzepeoplet/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzepeoplet.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Istanzepeoplet istanzepeoplet = istanzepeopletService.findById(id);
    // fixRenderEntityProperty(istanzepeoplet);
    // model.addAttribute("istanzepeoplet", istanzepeoplet);
    // setPageAttributes(model);
    // return "istanzepeoplet/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("istanzepeoplet") Istanzepeoplet istanzepeoplet, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(istanzepeoplet);
    // try {
    // istanzepeopletService.update(istanzepeoplet);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzepeopletService.getValidationMessages(), result, istanzepeoplet, e.getMessage());
    // fixRenderEntityProperty(istanzepeoplet);
    // return "istanzepeoplet/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzepeoplet.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("istanzepeoplet") Istanzepeoplet istanzepeoplet, BindingResult result,
    // SessionStatus status) {
    //
    // Istanzepeoplet objToDelete = istanzepeopletService.findById(istanzepeoplet.getId());
    // try {
    // istanzepeopletService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzepeopletService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(istanzepeoplet);
    // return "istanzepeoplet/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(Istanzepeoplet entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzepeoplet entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
