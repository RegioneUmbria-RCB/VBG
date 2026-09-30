package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcClassisuperfici;
import it.gruppoinit.pal.gp.core.service.CcClassisuperficiService;
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
@SessionAttributes("ccclassisuperfici")
public class CcClassisuperficiController extends BaseController<CcClassisuperfici> {

    @Autowired
    private CcClassisuperficiService ccclassisuperficiService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcClassisuperfici> ccclassisuperficiList = ccclassisuperficiService.findAll(null, null);
    // ModelMap model = new ModelMap(ccclassisuperficiList);
    // boolean export = createJMesaExport(request, response, ccclassisuperficiList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccclassisuperficiList", ccclassisuperficiList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcClassisuperfici ccclassisuperfici = new CcClassisuperfici();
    // ccclassisuperfici.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccclassisuperfici);
    // model.addAttribute("ccclassisuperfici", ccclassisuperfici);
    // setPageAttributes(model);
    // return "ccclassisuperfici/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccclassisuperfici") CcClassisuperfici ccclassisuperfici, BindingResult
    // result, SessionStatus status) {
    //
    // fixMergeEntityProperty(ccclassisuperfici);
    // ccclassisuperfici.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccclassisuperficiService.insert(ccclassisuperfici);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccclassisuperficiService.getValidationMessages(), result, ccclassisuperfici,
    // e.getMessage());
    // fixRenderEntityProperty(ccclassisuperfici);
    // return "ccclassisuperfici/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccclassisuperfici.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcClassisuperfici ccclassisuperfici = ccclassisuperficiService.findById(id);
    // fixRenderEntityProperty(ccclassisuperfici);
    // model.addAttribute("ccclassisuperfici", ccclassisuperfici);
    // setPageAttributes(model);
    // return "ccclassisuperfici/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccclassisuperfici") CcClassisuperfici ccclassisuperfici, BindingResult
    // result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccclassisuperfici);
    // try {
    // ccclassisuperficiService.update(ccclassisuperfici);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccclassisuperficiService.getValidationMessages(), result, ccclassisuperfici,
    // e.getMessage());
    // fixRenderEntityProperty(ccclassisuperfici);
    // return "ccclassisuperfici/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccclassisuperfici.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccclassisuperfici") CcClassisuperfici ccclassisuperfici, BindingResult
    // result, SessionStatus status) {
    //
    // CcClassisuperfici objToDelete = ccclassisuperficiService.findById(ccclassisuperfici.getId());
    // try {
    // ccclassisuperficiService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccclassisuperficiService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(ccclassisuperfici);
    // return "ccclassisuperfici/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcClassisuperfici entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcClassisuperfici entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
