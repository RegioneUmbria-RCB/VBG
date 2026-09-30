package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Istanzeaffissioni;
import it.gruppoinit.pal.gp.core.service.IstanzeaffissioniService;
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
@SessionAttributes("istanzeaffissioni")
public class IstanzeaffissioniController extends BaseController<Istanzeaffissioni> {

    @Autowired
    private IstanzeaffissioniService istanzeaffissioniService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Istanzeaffissioni> istanzeaffissioniList = istanzeaffissioniService.findAll(null, null);
    // ModelMap model = new ModelMap(istanzeaffissioniList);
    // boolean export = createJMesaExport(request, response, istanzeaffissioniList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("istanzeaffissioniList", istanzeaffissioniList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Istanzeaffissioni istanzeaffissioni = new Istanzeaffissioni();
    // istanzeaffissioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(istanzeaffissioni);
    // model.addAttribute("istanzeaffissioni", istanzeaffissioni);
    // setPageAttributes(model);
    // return "istanzeaffissioni/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("istanzeaffissioni") Istanzeaffissioni istanzeaffissioni, BindingResult
    // result, SessionStatus status) {
    //
    // fixMergeEntityProperty(istanzeaffissioni);
    // istanzeaffissioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // istanzeaffissioniService.insert(istanzeaffissioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzeaffissioniService.getValidationMessages(), result, istanzeaffissioni,
    // e.getMessage());
    // fixRenderEntityProperty(istanzeaffissioni);
    // return "istanzeaffissioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzeaffissioni.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Istanzeaffissioni istanzeaffissioni = istanzeaffissioniService.findById(id);
    // fixRenderEntityProperty(istanzeaffissioni);
    // model.addAttribute("istanzeaffissioni", istanzeaffissioni);
    // setPageAttributes(model);
    // return "istanzeaffissioni/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("istanzeaffissioni") Istanzeaffissioni istanzeaffissioni, BindingResult
    // result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(istanzeaffissioni);
    // try {
    // istanzeaffissioniService.update(istanzeaffissioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzeaffissioniService.getValidationMessages(), result, istanzeaffissioni,
    // e.getMessage());
    // fixRenderEntityProperty(istanzeaffissioni);
    // return "istanzeaffissioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzeaffissioni.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("istanzeaffissioni") Istanzeaffissioni istanzeaffissioni, BindingResult
    // result, SessionStatus status) {
    //
    // Istanzeaffissioni objToDelete = istanzeaffissioniService.findById(istanzeaffissioni.getId());
    // try {
    // istanzeaffissioniService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzeaffissioniService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(istanzeaffissioni);
    // return "istanzeaffissioni/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Istanzeaffissioni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzeaffissioni entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
