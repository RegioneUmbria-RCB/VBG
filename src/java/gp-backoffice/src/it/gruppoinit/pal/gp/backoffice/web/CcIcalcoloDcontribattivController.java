package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontribattiv;
import it.gruppoinit.pal.gp.core.service.CcIcalcoloDcontribattivService;
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
@SessionAttributes("ccicalcolodcontribattiv")
public class CcIcalcoloDcontribattivController extends BaseController<CcIcalcoloDcontribattiv> {

    @Autowired
    private CcIcalcoloDcontribattivService ccicalcolodcontribattivService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcIcalcoloDcontribattiv> ccicalcolodcontribattivList = ccicalcolodcontribattivService.findAll(null, null);
    // ModelMap model = new ModelMap(ccicalcolodcontribattivList);
    // boolean export = createJMesaExport(request, response, ccicalcolodcontribattivList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccicalcolodcontribattivList", ccicalcolodcontribattivList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcIcalcoloDcontribattiv ccicalcolodcontribattiv = new CcIcalcoloDcontribattiv();
    // ccicalcolodcontribattiv.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccicalcolodcontribattiv);
    // model.addAttribute("ccicalcolodcontribattiv", ccicalcolodcontribattiv);
    // setPageAttributes(model);
    // return "ccicalcolodcontribattiv/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccicalcolodcontribattiv") CcIcalcoloDcontribattiv ccicalcolodcontribattiv,
    // BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(ccicalcolodcontribattiv);
    // ccicalcolodcontribattiv.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccicalcolodcontribattivService.insert(ccicalcolodcontribattiv);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolodcontribattivService.getValidationMessages(), result,
    // ccicalcolodcontribattiv, e.getMessage());
    // fixRenderEntityProperty(ccicalcolodcontribattiv);
    // return "ccicalcolodcontribattiv/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccicalcolodcontribattiv.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcIcalcoloDcontribattiv ccicalcolodcontribattiv = ccicalcolodcontribattivService.findById(id);
    // fixRenderEntityProperty(ccicalcolodcontribattiv);
    // model.addAttribute("ccicalcolodcontribattiv", ccicalcolodcontribattiv);
    // setPageAttributes(model);
    // return "ccicalcolodcontribattiv/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccicalcolodcontribattiv") CcIcalcoloDcontribattiv ccicalcolodcontribattiv,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccicalcolodcontribattiv);
    // try {
    // ccicalcolodcontribattivService.update(ccicalcolodcontribattiv);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolodcontribattivService.getValidationMessages(), result,
    // ccicalcolodcontribattiv, e.getMessage());
    // fixRenderEntityProperty(ccicalcolodcontribattiv);
    // return "ccicalcolodcontribattiv/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccicalcolodcontribattiv.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccicalcolodcontribattiv") CcIcalcoloDcontribattiv ccicalcolodcontribattiv,
    // BindingResult result,
    // SessionStatus status) {
    //
    // CcIcalcoloDcontribattiv objToDelete = ccicalcolodcontribattivService.findById(ccicalcolodcontribattiv.getId());
    // try {
    // ccicalcolodcontribattivService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolodcontribattivService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(ccicalcolodcontribattiv);
    // return "ccicalcolodcontribattiv/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcIcalcoloDcontribattiv entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcIcalcoloDcontribattiv entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
