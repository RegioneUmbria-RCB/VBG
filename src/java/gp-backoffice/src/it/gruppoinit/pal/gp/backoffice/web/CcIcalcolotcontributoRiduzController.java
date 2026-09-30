package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcIcalcolotcontributoRiduz;
import it.gruppoinit.pal.gp.core.service.CcIcalcolotcontributoRiduzService;
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
@SessionAttributes("ccicalcolotcontributoriduz")
public class CcIcalcolotcontributoRiduzController extends BaseController<CcIcalcolotcontributoRiduz> {

    @Autowired
    private CcIcalcolotcontributoRiduzService ccicalcolotcontributoriduzService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcIcalcolotcontributoRiduz> ccicalcolotcontributoriduzList = ccicalcolotcontributoriduzService.findAll(null,
    // null);
    // ModelMap model = new ModelMap(ccicalcolotcontributoriduzList);
    // boolean export = createJMesaExport(request, response, ccicalcolotcontributoriduzList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccicalcolotcontributoriduzList", ccicalcolotcontributoriduzList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcIcalcolotcontributoRiduz ccicalcolotcontributoriduz = new CcIcalcolotcontributoRiduz();
    // ccicalcolotcontributoriduz.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccicalcolotcontributoriduz);
    // model.addAttribute("ccicalcolotcontributoriduz", ccicalcolotcontributoriduz);
    // setPageAttributes(model);
    // return "ccicalcolotcontributoriduz/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccicalcolotcontributoriduz") CcIcalcolotcontributoRiduz
    // ccicalcolotcontributoriduz, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(ccicalcolotcontributoriduz);
    // ccicalcolotcontributoriduz.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccicalcolotcontributoriduzService.insert(ccicalcolotcontributoriduz);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolotcontributoriduzService.getValidationMessages(), result,
    // ccicalcolotcontributoriduz, e.getMessage());
    // fixRenderEntityProperty(ccicalcolotcontributoriduz);
    // return "ccicalcolotcontributoriduz/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccicalcolotcontributoriduz.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcIcalcolotcontributoRiduz ccicalcolotcontributoriduz = ccicalcolotcontributoriduzService.findById(id);
    // fixRenderEntityProperty(ccicalcolotcontributoriduz);
    // model.addAttribute("ccicalcolotcontributoriduz", ccicalcolotcontributoriduz);
    // setPageAttributes(model);
    // return "ccicalcolotcontributoriduz/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccicalcolotcontributoriduz") CcIcalcolotcontributoRiduz
    // ccicalcolotcontributoriduz, BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccicalcolotcontributoriduz);
    // try {
    // ccicalcolotcontributoriduzService.update(ccicalcolotcontributoriduz);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolotcontributoriduzService.getValidationMessages(), result,
    // ccicalcolotcontributoriduz, e.getMessage());
    // fixRenderEntityProperty(ccicalcolotcontributoriduz);
    // return "ccicalcolotcontributoriduz/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccicalcolotcontributoriduz.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccicalcolotcontributoriduz") CcIcalcolotcontributoRiduz
    // ccicalcolotcontributoriduz, BindingResult result,
    // SessionStatus status) {
    //
    // CcIcalcolotcontributoRiduz objToDelete =
    // ccicalcolotcontributoriduzService.findById(ccicalcolotcontributoriduz.getId());
    // try {
    // ccicalcolotcontributoriduzService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolotcontributoriduzService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(ccicalcolotcontributoriduz);
    // return "ccicalcolotcontributoriduz/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(CcIcalcolotcontributoRiduz entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcIcalcolotcontributoRiduz entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
