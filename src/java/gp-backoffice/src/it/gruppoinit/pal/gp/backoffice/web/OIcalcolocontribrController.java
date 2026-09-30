package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribr;
import it.gruppoinit.pal.gp.core.service.OIcalcolocontribrService;
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
@SessionAttributes("oicalcolocontribr")
public class OIcalcolocontribrController extends BaseController<OIcalcolocontribr> {

    @Autowired
    private OIcalcolocontribrService oicalcolocontribrService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OIcalcolocontribr> oicalcolocontribrList = oicalcolocontribrService.findAll(null, null);
    // ModelMap model = new ModelMap(oicalcolocontribrList);
    // boolean export = createJMesaExport(request, response, oicalcolocontribrList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("oicalcolocontribrList", oicalcolocontribrList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OIcalcolocontribr oicalcolocontribr = new OIcalcolocontribr();
    // oicalcolocontribr.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(oicalcolocontribr);
    // model.addAttribute("oicalcolocontribr", oicalcolocontribr);
    // setPageAttributes(model);
    // return "oicalcolocontribr/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("oicalcolocontribr") OIcalcolocontribr oicalcolocontribr, BindingResult
    // result, SessionStatus status) {
    //
    // fixMergeEntityProperty(oicalcolocontribr);
    // oicalcolocontribr.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // oicalcolocontribrService.insert(oicalcolocontribr);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolocontribrService.getValidationMessages(), result, oicalcolocontribr,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolocontribr);
    // return "oicalcolocontribr/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oicalcolocontribr.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OIcalcolocontribr oicalcolocontribr = oicalcolocontribrService.findById(id);
    // fixRenderEntityProperty(oicalcolocontribr);
    // model.addAttribute("oicalcolocontribr", oicalcolocontribr);
    // setPageAttributes(model);
    // return "oicalcolocontribr/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("oicalcolocontribr") OIcalcolocontribr oicalcolocontribr, BindingResult
    // result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(oicalcolocontribr);
    // try {
    // oicalcolocontribrService.update(oicalcolocontribr);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolocontribrService.getValidationMessages(), result, oicalcolocontribr,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolocontribr);
    // return "oicalcolocontribr/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oicalcolocontribr.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("oicalcolocontribr") OIcalcolocontribr oicalcolocontribr, BindingResult
    // result, SessionStatus status) {
    //
    // OIcalcolocontribr objToDelete = oicalcolocontribrService.findById(oicalcolocontribr.getId());
    // try {
    // oicalcolocontribrService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolocontribrService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(oicalcolocontribr);
    // return "oicalcolocontribr/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(OIcalcolocontribr entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OIcalcolocontribr entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
