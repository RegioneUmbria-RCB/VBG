package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OIcalcolotot;
import it.gruppoinit.pal.gp.core.service.OIcalcolototService;
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
@SessionAttributes("oicalcolotot")
public class OIcalcolototController extends BaseController<OIcalcolotot> {

    @Autowired
    private OIcalcolototService oicalcolototService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OIcalcolotot> oicalcolototList = oicalcolototService.findAll(null, null);
    // ModelMap model = new ModelMap(oicalcolototList);
    // boolean export = createJMesaExport(request, response, oicalcolototList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("oicalcolototList", oicalcolototList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OIcalcolotot oicalcolotot = new OIcalcolotot();
    // oicalcolotot.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(oicalcolotot);
    // model.addAttribute("oicalcolotot", oicalcolotot);
    // setPageAttributes(model);
    // return "oicalcolotot/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("oicalcolotot") OIcalcolotot oicalcolotot, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(oicalcolotot);
    // oicalcolotot.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // oicalcolototService.insert(oicalcolotot);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolototService.getValidationMessages(), result, oicalcolotot, e.getMessage());
    // fixRenderEntityProperty(oicalcolotot);
    // return "oicalcolotot/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oicalcolotot.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OIcalcolotot oicalcolotot = oicalcolototService.findById(id);
    // fixRenderEntityProperty(oicalcolotot);
    // model.addAttribute("oicalcolotot", oicalcolotot);
    // setPageAttributes(model);
    // return "oicalcolotot/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("oicalcolotot") OIcalcolotot oicalcolotot, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(oicalcolotot);
    // try {
    // oicalcolototService.update(oicalcolotot);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolototService.getValidationMessages(), result, oicalcolotot, e.getMessage());
    // fixRenderEntityProperty(oicalcolotot);
    // return "oicalcolotot/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oicalcolotot.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("oicalcolotot") OIcalcolotot oicalcolotot, BindingResult result,
    // SessionStatus status) {
    //
    // OIcalcolotot objToDelete = oicalcolototService.findById(oicalcolotot.getId());
    // try {
    // oicalcolototService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolototService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(oicalcolotot);
    // return "oicalcolotot/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(OIcalcolotot entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OIcalcolotot entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
