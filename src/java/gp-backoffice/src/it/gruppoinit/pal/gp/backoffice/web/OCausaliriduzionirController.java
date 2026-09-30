package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OCausaliriduzionir;
import it.gruppoinit.pal.gp.core.service.OCausaliriduzionirService;
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
@SessionAttributes("ocausaliriduzionir")
public class OCausaliriduzionirController extends BaseController<OCausaliriduzionir> {

    @Autowired
    private OCausaliriduzionirService ocausaliriduzionirService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OCausaliriduzionir> ocausaliriduzionirList = ocausaliriduzionirService.findAll(null, null);
    // ModelMap model = new ModelMap(ocausaliriduzionirList);
    // boolean export = createJMesaExport(request, response, ocausaliriduzionirList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ocausaliriduzionirList", ocausaliriduzionirList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OCausaliriduzionir ocausaliriduzionir = new OCausaliriduzionir();
    // ocausaliriduzionir.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ocausaliriduzionir);
    // model.addAttribute("ocausaliriduzionir", ocausaliriduzionir);
    // setPageAttributes(model);
    // return "ocausaliriduzionir/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ocausaliriduzionir") OCausaliriduzionir ocausaliriduzionir, BindingResult
    // result, SessionStatus status) {
    //
    // fixMergeEntityProperty(ocausaliriduzionir);
    // ocausaliriduzionir.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ocausaliriduzionirService.insert(ocausaliriduzionir);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ocausaliriduzionirService.getValidationMessages(), result, ocausaliriduzionir,
    // e.getMessage());
    // fixRenderEntityProperty(ocausaliriduzionir);
    // return "ocausaliriduzionir/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ocausaliriduzionir.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OCausaliriduzionir ocausaliriduzionir = ocausaliriduzionirService.findById(id);
    // fixRenderEntityProperty(ocausaliriduzionir);
    // model.addAttribute("ocausaliriduzionir", ocausaliriduzionir);
    // setPageAttributes(model);
    // return "ocausaliriduzionir/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ocausaliriduzionir") OCausaliriduzionir ocausaliriduzionir, BindingResult
    // result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ocausaliriduzionir);
    // try {
    // ocausaliriduzionirService.update(ocausaliriduzionir);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ocausaliriduzionirService.getValidationMessages(), result, ocausaliriduzionir,
    // e.getMessage());
    // fixRenderEntityProperty(ocausaliriduzionir);
    // return "ocausaliriduzionir/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ocausaliriduzionir.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ocausaliriduzionir") OCausaliriduzionir ocausaliriduzionir, BindingResult
    // result, SessionStatus status) {
    //
    // OCausaliriduzionir objToDelete = ocausaliriduzionirService.findById(ocausaliriduzionir.getId());
    // try {
    // ocausaliriduzionirService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ocausaliriduzionirService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(ocausaliriduzionir);
    // return "ocausaliriduzionir/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(OCausaliriduzionir entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OCausaliriduzionir entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
