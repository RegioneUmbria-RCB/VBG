package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Istanzepeopled;
import it.gruppoinit.pal.gp.core.service.IstanzepeopledService;
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
@SessionAttributes("istanzepeopled")
public class IstanzepeopledController extends BaseController<Istanzepeopled> {

    @Autowired
    private IstanzepeopledService istanzepeopledService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Istanzepeopled> istanzepeopledList = istanzepeopledService.findAll(null, null);
    // ModelMap model = new ModelMap(istanzepeopledList);
    // boolean export = createJMesaExport(request, response, istanzepeopledList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("istanzepeopledList", istanzepeopledList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Istanzepeopled istanzepeopled = new Istanzepeopled();
    // istanzepeopled.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(istanzepeopled);
    // model.addAttribute("istanzepeopled", istanzepeopled);
    // setPageAttributes(model);
    // return "istanzepeopled/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("istanzepeopled") Istanzepeopled istanzepeopled, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(istanzepeopled);
    // istanzepeopled.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // istanzepeopledService.insert(istanzepeopled);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzepeopledService.getValidationMessages(), result, istanzepeopled, e.getMessage());
    // fixRenderEntityProperty(istanzepeopled);
    // return "istanzepeopled/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzepeopled.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Istanzepeopled istanzepeopled = istanzepeopledService.findById(id);
    // fixRenderEntityProperty(istanzepeopled);
    // model.addAttribute("istanzepeopled", istanzepeopled);
    // setPageAttributes(model);
    // return "istanzepeopled/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("istanzepeopled") Istanzepeopled istanzepeopled, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(istanzepeopled);
    // try {
    // istanzepeopledService.update(istanzepeopled);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzepeopledService.getValidationMessages(), result, istanzepeopled, e.getMessage());
    // fixRenderEntityProperty(istanzepeopled);
    // return "istanzepeopled/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzepeopled.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("istanzepeopled") Istanzepeopled istanzepeopled, BindingResult result,
    // SessionStatus status) {
    //
    // Istanzepeopled objToDelete = istanzepeopledService.findById(istanzepeopled.getId());
    // try {
    // istanzepeopledService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzepeopledService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(istanzepeopled);
    // return "istanzepeopled/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Istanzepeopled entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzepeopled entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
