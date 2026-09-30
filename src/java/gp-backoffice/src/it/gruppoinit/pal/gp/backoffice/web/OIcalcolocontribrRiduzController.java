package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribrRiduz;
import it.gruppoinit.pal.gp.core.service.OIcalcolocontribrRiduzService;
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
@SessionAttributes("oicalcolocontribrriduz")
public class OIcalcolocontribrRiduzController extends BaseController<OIcalcolocontribrRiduz> {

    @Autowired
    private OIcalcolocontribrRiduzService oicalcolocontribrriduzService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OIcalcolocontribrRiduz> oicalcolocontribrriduzList = oicalcolocontribrriduzService.findAll(null, null);
    // ModelMap model = new ModelMap(oicalcolocontribrriduzList);
    // boolean export = createJMesaExport(request, response, oicalcolocontribrriduzList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("oicalcolocontribrriduzList", oicalcolocontribrriduzList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OIcalcolocontribrRiduz oicalcolocontribrriduz = new OIcalcolocontribrRiduz();
    // oicalcolocontribrriduz.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(oicalcolocontribrriduz);
    // model.addAttribute("oicalcolocontribrriduz", oicalcolocontribrriduz);
    // setPageAttributes(model);
    // return "oicalcolocontribrriduz/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("oicalcolocontribrriduz") OIcalcolocontribrRiduz oicalcolocontribrriduz,
    // BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(oicalcolocontribrriduz);
    // oicalcolocontribrriduz.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // oicalcolocontribrriduzService.insert(oicalcolocontribrriduz);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolocontribrriduzService.getValidationMessages(), result, oicalcolocontribrriduz,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolocontribrriduz);
    // return "oicalcolocontribrriduz/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oicalcolocontribrriduz.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OIcalcolocontribrRiduz oicalcolocontribrriduz = oicalcolocontribrriduzService.findById(id);
    // fixRenderEntityProperty(oicalcolocontribrriduz);
    // model.addAttribute("oicalcolocontribrriduz", oicalcolocontribrriduz);
    // setPageAttributes(model);
    // return "oicalcolocontribrriduz/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("oicalcolocontribrriduz") OIcalcolocontribrRiduz oicalcolocontribrriduz,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(oicalcolocontribrriduz);
    // try {
    // oicalcolocontribrriduzService.update(oicalcolocontribrriduz);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolocontribrriduzService.getValidationMessages(), result, oicalcolocontribrriduz,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolocontribrriduz);
    // return "oicalcolocontribrriduz/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oicalcolocontribrriduz.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("oicalcolocontribrriduz") OIcalcolocontribrRiduz oicalcolocontribrriduz,
    // BindingResult result,
    // SessionStatus status) {
    //
    // OIcalcolocontribrRiduz objToDelete = oicalcolocontribrriduzService.findById(oicalcolocontribrriduz.getId());
    // try {
    // oicalcolocontribrriduzService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolocontribrriduzService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolocontribrriduz);
    // return "oicalcolocontribrriduz/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(OIcalcolocontribrRiduz entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OIcalcolocontribrRiduz entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
