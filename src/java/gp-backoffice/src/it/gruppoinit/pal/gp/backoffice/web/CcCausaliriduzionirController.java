package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionir;
import it.gruppoinit.pal.gp.core.service.CcCausaliriduzionirService;
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
@SessionAttributes("cccausaliriduzionir")
public class CcCausaliriduzionirController extends BaseController<CcCausaliriduzionir> {

    @Autowired
    private CcCausaliriduzionirService cccausaliriduzionirService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcCausaliriduzionir> cccausaliriduzionirList = cccausaliriduzionirService.findAll(null, null);
    // ModelMap model = new ModelMap(cccausaliriduzionirList);
    // boolean export = createJMesaExport(request, response, cccausaliriduzionirList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("cccausaliriduzionirList", cccausaliriduzionirList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcCausaliriduzionir cccausaliriduzionir = new CcCausaliriduzionir();
    // cccausaliriduzionir.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(cccausaliriduzionir);
    // model.addAttribute("cccausaliriduzionir", cccausaliriduzionir);
    // setPageAttributes(model);
    // return "cccausaliriduzionir/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("cccausaliriduzionir") CcCausaliriduzionir cccausaliriduzionir,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(cccausaliriduzionir);
    // cccausaliriduzionir.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // cccausaliriduzionirService.insert(cccausaliriduzionir);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cccausaliriduzionirService.getValidationMessages(), result, cccausaliriduzionir,
    // e.getMessage());
    // fixRenderEntityProperty(cccausaliriduzionir);
    // return "cccausaliriduzionir/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cccausaliriduzionir.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcCausaliriduzionir cccausaliriduzionir = cccausaliriduzionirService.findById(id);
    // fixRenderEntityProperty(cccausaliriduzionir);
    // model.addAttribute("cccausaliriduzionir", cccausaliriduzionir);
    // setPageAttributes(model);
    // return "cccausaliriduzionir/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("cccausaliriduzionir") CcCausaliriduzionir cccausaliriduzionir,
    // BindingResult result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(cccausaliriduzionir);
    // try {
    // cccausaliriduzionirService.update(cccausaliriduzionir);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cccausaliriduzionirService.getValidationMessages(), result, cccausaliriduzionir,
    // e.getMessage());
    // fixRenderEntityProperty(cccausaliriduzionir);
    // return "cccausaliriduzionir/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cccausaliriduzionir.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("cccausaliriduzionir") CcCausaliriduzionir cccausaliriduzionir,
    // BindingResult result, SessionStatus status) {
    //
    // CcCausaliriduzionir objToDelete = cccausaliriduzionirService.findById(cccausaliriduzionir.getId());
    // try {
    // cccausaliriduzionirService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cccausaliriduzionirService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(cccausaliriduzionir);
    // return "cccausaliriduzionir/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcCausaliriduzionir entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcCausaliriduzionir entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
