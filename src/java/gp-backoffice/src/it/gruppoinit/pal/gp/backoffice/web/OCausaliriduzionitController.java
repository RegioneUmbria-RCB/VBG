package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OCausaliriduzionit;
import it.gruppoinit.pal.gp.core.service.OCausaliriduzionitService;
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
@SessionAttributes("ocausaliriduzionit")
public class OCausaliriduzionitController extends BaseController<OCausaliriduzionit> {

    @Autowired
    private OCausaliriduzionitService ocausaliriduzionitService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OCausaliriduzionit> ocausaliriduzionitList = ocausaliriduzionitService.findAll(null, null);
    // ModelMap model = new ModelMap(ocausaliriduzionitList);
    // boolean export = createJMesaExport(request, response, ocausaliriduzionitList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ocausaliriduzionitList", ocausaliriduzionitList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OCausaliriduzionit ocausaliriduzionit = new OCausaliriduzionit();
    // ocausaliriduzionit.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ocausaliriduzionit);
    // model.addAttribute("ocausaliriduzionit", ocausaliriduzionit);
    // setPageAttributes(model);
    // return "ocausaliriduzionit/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ocausaliriduzionit") OCausaliriduzionit ocausaliriduzionit, BindingResult
    // result, SessionStatus status) {
    //
    // fixMergeEntityProperty(ocausaliriduzionit);
    // ocausaliriduzionit.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ocausaliriduzionitService.insert(ocausaliriduzionit);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ocausaliriduzionitService.getValidationMessages(), result, ocausaliriduzionit,
    // e.getMessage());
    // fixRenderEntityProperty(ocausaliriduzionit);
    // return "ocausaliriduzionit/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ocausaliriduzionit.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OCausaliriduzionit ocausaliriduzionit = ocausaliriduzionitService.findById(id);
    // fixRenderEntityProperty(ocausaliriduzionit);
    // model.addAttribute("ocausaliriduzionit", ocausaliriduzionit);
    // setPageAttributes(model);
    // return "ocausaliriduzionit/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ocausaliriduzionit") OCausaliriduzionit ocausaliriduzionit, BindingResult
    // result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ocausaliriduzionit);
    // try {
    // ocausaliriduzionitService.update(ocausaliriduzionit);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ocausaliriduzionitService.getValidationMessages(), result, ocausaliriduzionit,
    // e.getMessage());
    // fixRenderEntityProperty(ocausaliriduzionit);
    // return "ocausaliriduzionit/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ocausaliriduzionit.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ocausaliriduzionit") OCausaliriduzionit ocausaliriduzionit, BindingResult
    // result, SessionStatus status) {
    //
    // OCausaliriduzionit objToDelete = ocausaliriduzionitService.findById(ocausaliriduzionit.getId());
    // try {
    // ocausaliriduzionitService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ocausaliriduzionitService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(ocausaliriduzionit);
    // return "ocausaliriduzionit/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(OCausaliriduzionit entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OCausaliriduzionit entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
