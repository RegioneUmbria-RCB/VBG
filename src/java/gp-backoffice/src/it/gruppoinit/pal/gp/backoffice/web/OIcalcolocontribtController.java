package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribt;
import it.gruppoinit.pal.gp.core.service.OIcalcolocontribtService;
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
@SessionAttributes("oicalcolocontribt")
public class OIcalcolocontribtController extends BaseController<OIcalcolocontribt> {

    @Autowired
    private OIcalcolocontribtService oicalcolocontribtService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OIcalcolocontribt> oicalcolocontribtList = oicalcolocontribtService.findAll(null, null);
    // ModelMap model = new ModelMap(oicalcolocontribtList);
    // boolean export = createJMesaExport(request, response, oicalcolocontribtList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("oicalcolocontribtList", oicalcolocontribtList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OIcalcolocontribt oicalcolocontribt = new OIcalcolocontribt();
    // oicalcolocontribt.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(oicalcolocontribt);
    // model.addAttribute("oicalcolocontribt", oicalcolocontribt);
    // setPageAttributes(model);
    // return "oicalcolocontribt/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("oicalcolocontribt") OIcalcolocontribt oicalcolocontribt, BindingResult
    // result, SessionStatus status) {
    //
    // fixMergeEntityProperty(oicalcolocontribt);
    // oicalcolocontribt.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // oicalcolocontribtService.insert(oicalcolocontribt);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolocontribtService.getValidationMessages(), result, oicalcolocontribt,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolocontribt);
    // return "oicalcolocontribt/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oicalcolocontribt.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OIcalcolocontribt oicalcolocontribt = oicalcolocontribtService.findById(id);
    // fixRenderEntityProperty(oicalcolocontribt);
    // model.addAttribute("oicalcolocontribt", oicalcolocontribt);
    // setPageAttributes(model);
    // return "oicalcolocontribt/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("oicalcolocontribt") OIcalcolocontribt oicalcolocontribt, BindingResult
    // result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(oicalcolocontribt);
    // try {
    // oicalcolocontribtService.update(oicalcolocontribt);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolocontribtService.getValidationMessages(), result, oicalcolocontribt,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolocontribt);
    // return "oicalcolocontribt/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oicalcolocontribt.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("oicalcolocontribt") OIcalcolocontribt oicalcolocontribt, BindingResult
    // result, SessionStatus status) {
    //
    // OIcalcolocontribt objToDelete = oicalcolocontribtService.findById(oicalcolocontribt.getId());
    // try {
    // oicalcolocontribtService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolocontribtService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(oicalcolocontribt);
    // return "oicalcolocontribt/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(OIcalcolocontribt entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OIcalcolocontribt entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
