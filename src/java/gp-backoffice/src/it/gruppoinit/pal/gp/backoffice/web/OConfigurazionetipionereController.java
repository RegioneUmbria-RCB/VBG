package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OConfigurazionetipionere;
import it.gruppoinit.pal.gp.core.service.OConfigurazionetipionereService;
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
@SessionAttributes("oconfigurazionetipionere")
public class OConfigurazionetipionereController extends BaseController<OConfigurazionetipionere> {

    @Autowired
    private OConfigurazionetipionereService oconfigurazionetipionereService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OConfigurazionetipionere> oconfigurazionetipionereList = oconfigurazionetipionereService.findAll(null,
    // null);
    // ModelMap model = new ModelMap(oconfigurazionetipionereList);
    // boolean export = createJMesaExport(request, response, oconfigurazionetipionereList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("oconfigurazionetipionereList", oconfigurazionetipionereList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OConfigurazionetipionere oconfigurazionetipionere = new OConfigurazionetipionere();
    // oconfigurazionetipionere.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(oconfigurazionetipionere);
    // model.addAttribute("oconfigurazionetipionere", oconfigurazionetipionere);
    // setPageAttributes(model);
    // return "oconfigurazionetipionere/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("oconfigurazionetipionere") OConfigurazionetipionere
    // oconfigurazionetipionere, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(oconfigurazionetipionere);
    // oconfigurazionetipionere.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // oconfigurazionetipionereService.insert(oconfigurazionetipionere);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oconfigurazionetipionereService.getValidationMessages(), result,
    // oconfigurazionetipionere, e.getMessage());
    // fixRenderEntityProperty(oconfigurazionetipionere);
    // return "oconfigurazionetipionere/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oconfigurazionetipionere.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OConfigurazionetipionere oconfigurazionetipionere = oconfigurazionetipionereService.findById(id);
    // fixRenderEntityProperty(oconfigurazionetipionere);
    // model.addAttribute("oconfigurazionetipionere", oconfigurazionetipionere);
    // setPageAttributes(model);
    // return "oconfigurazionetipionere/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("oconfigurazionetipionere") OConfigurazionetipionere
    // oconfigurazionetipionere, BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(oconfigurazionetipionere);
    // try {
    // oconfigurazionetipionereService.update(oconfigurazionetipionere);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oconfigurazionetipionereService.getValidationMessages(), result,
    // oconfigurazionetipionere, e.getMessage());
    // fixRenderEntityProperty(oconfigurazionetipionere);
    // return "oconfigurazionetipionere/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oconfigurazionetipionere.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("oconfigurazionetipionere") OConfigurazionetipionere
    // oconfigurazionetipionere, BindingResult result,
    // SessionStatus status) {
    //
    // OConfigurazionetipionere objToDelete =
    // oconfigurazionetipionereService.findById(oconfigurazionetipionere.getId());
    // try {
    // oconfigurazionetipionereService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oconfigurazionetipionereService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(oconfigurazionetipionere);
    // return "oconfigurazionetipionere/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(OConfigurazionetipionere entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OConfigurazionetipionere entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
