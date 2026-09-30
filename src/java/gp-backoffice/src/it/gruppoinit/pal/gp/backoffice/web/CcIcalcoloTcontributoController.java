package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcIcalcoloTcontributo;
import it.gruppoinit.pal.gp.core.service.CcIcalcoloTcontributoService;
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
@SessionAttributes("ccicalcolotcontributo")
public class CcIcalcoloTcontributoController extends BaseController<CcIcalcoloTcontributo> {

    @Autowired
    private CcIcalcoloTcontributoService ccicalcolotcontributoService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcIcalcoloTcontributo> ccicalcolotcontributoList = ccicalcolotcontributoService.findAll(null, null);
    // ModelMap model = new ModelMap(ccicalcolotcontributoList);
    // boolean export = createJMesaExport(request, response, ccicalcolotcontributoList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccicalcolotcontributoList", ccicalcolotcontributoList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcIcalcoloTcontributo ccicalcolotcontributo = new CcIcalcoloTcontributo();
    // ccicalcolotcontributo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccicalcolotcontributo);
    // model.addAttribute("ccicalcolotcontributo", ccicalcolotcontributo);
    // setPageAttributes(model);
    // return "ccicalcolotcontributo/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccicalcolotcontributo") CcIcalcoloTcontributo ccicalcolotcontributo,
    // BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(ccicalcolotcontributo);
    // ccicalcolotcontributo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccicalcolotcontributoService.insert(ccicalcolotcontributo);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolotcontributoService.getValidationMessages(), result, ccicalcolotcontributo,
    // e.getMessage());
    // fixRenderEntityProperty(ccicalcolotcontributo);
    // return "ccicalcolotcontributo/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccicalcolotcontributo.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcIcalcoloTcontributo ccicalcolotcontributo = ccicalcolotcontributoService.findById(id);
    // fixRenderEntityProperty(ccicalcolotcontributo);
    // model.addAttribute("ccicalcolotcontributo", ccicalcolotcontributo);
    // setPageAttributes(model);
    // return "ccicalcolotcontributo/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccicalcolotcontributo") CcIcalcoloTcontributo ccicalcolotcontributo,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccicalcolotcontributo);
    // try {
    // ccicalcolotcontributoService.update(ccicalcolotcontributo);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolotcontributoService.getValidationMessages(), result, ccicalcolotcontributo,
    // e.getMessage());
    // fixRenderEntityProperty(ccicalcolotcontributo);
    // return "ccicalcolotcontributo/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccicalcolotcontributo.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccicalcolotcontributo") CcIcalcoloTcontributo ccicalcolotcontributo,
    // BindingResult result,
    // SessionStatus status) {
    //
    // CcIcalcoloTcontributo objToDelete = ccicalcolotcontributoService.findById(ccicalcolotcontributo.getId());
    // try {
    // ccicalcolotcontributoService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolotcontributoService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(ccicalcolotcontributo);
    // return "ccicalcolotcontributo/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcIcalcoloTcontributo entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcIcalcoloTcontributo entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
