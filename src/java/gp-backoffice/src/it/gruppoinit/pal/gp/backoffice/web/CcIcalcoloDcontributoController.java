package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontributo;
import it.gruppoinit.pal.gp.core.service.CcIcalcoloDcontributoService;
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
@SessionAttributes("ccicalcolodcontributo")
public class CcIcalcoloDcontributoController extends BaseController<CcIcalcoloDcontributo> {

    @Autowired
    private CcIcalcoloDcontributoService ccicalcolodcontributoService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcIcalcoloDcontributo> ccicalcolodcontributoList = ccicalcolodcontributoService.findAll(null, null);
    // ModelMap model = new ModelMap(ccicalcolodcontributoList);
    // boolean export = createJMesaExport(request, response, ccicalcolodcontributoList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccicalcolodcontributoList", ccicalcolodcontributoList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcIcalcoloDcontributo ccicalcolodcontributo = new CcIcalcoloDcontributo();
    // ccicalcolodcontributo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccicalcolodcontributo);
    // model.addAttribute("ccicalcolodcontributo", ccicalcolodcontributo);
    // setPageAttributes(model);
    // return "ccicalcolodcontributo/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccicalcolodcontributo") CcIcalcoloDcontributo ccicalcolodcontributo,
    // BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(ccicalcolodcontributo);
    // ccicalcolodcontributo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccicalcolodcontributoService.insert(ccicalcolodcontributo);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolodcontributoService.getValidationMessages(), result, ccicalcolodcontributo,
    // e.getMessage());
    // fixRenderEntityProperty(ccicalcolodcontributo);
    // return "ccicalcolodcontributo/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccicalcolodcontributo.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcIcalcoloDcontributo ccicalcolodcontributo = ccicalcolodcontributoService.findById(id);
    // fixRenderEntityProperty(ccicalcolodcontributo);
    // model.addAttribute("ccicalcolodcontributo", ccicalcolodcontributo);
    // setPageAttributes(model);
    // return "ccicalcolodcontributo/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccicalcolodcontributo") CcIcalcoloDcontributo ccicalcolodcontributo,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccicalcolodcontributo);
    // try {
    // ccicalcolodcontributoService.update(ccicalcolodcontributo);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolodcontributoService.getValidationMessages(), result, ccicalcolodcontributo,
    // e.getMessage());
    // fixRenderEntityProperty(ccicalcolodcontributo);
    // return "ccicalcolodcontributo/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccicalcolodcontributo.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccicalcolodcontributo") CcIcalcoloDcontributo ccicalcolodcontributo,
    // BindingResult result,
    // SessionStatus status) {
    //
    // CcIcalcoloDcontributo objToDelete = ccicalcolodcontributoService.findById(ccicalcolodcontributo.getId());
    // try {
    // ccicalcolodcontributoService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolodcontributoService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(ccicalcolodcontributo);
    // return "ccicalcolodcontributo/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcIcalcoloDcontributo entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcIcalcoloDcontributo entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
