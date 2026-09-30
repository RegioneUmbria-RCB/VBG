package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OConfigurazione;
import it.gruppoinit.pal.gp.core.service.OConfigurazioneService;
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
@SessionAttributes("oconfigurazione")
public class OConfigurazioneController extends BaseController<OConfigurazione> {

    @Autowired
    private OConfigurazioneService oconfigurazioneService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OConfigurazione> oconfigurazioneList = oconfigurazioneService.findAll(null, null);
    // ModelMap model = new ModelMap(oconfigurazioneList);
    // boolean export = createJMesaExport(request, response, oconfigurazioneList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("oconfigurazioneList", oconfigurazioneList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OConfigurazione oconfigurazione = new OConfigurazione();
    // oconfigurazione.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(oconfigurazione);
    // model.addAttribute("oconfigurazione", oconfigurazione);
    // setPageAttributes(model);
    // return "oconfigurazione/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("oconfigurazione") OConfigurazione oconfigurazione, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(oconfigurazione);
    // oconfigurazione.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // oconfigurazioneService.insert(oconfigurazione);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oconfigurazioneService.getValidationMessages(), result, oconfigurazione,
    // e.getMessage());
    // fixRenderEntityProperty(oconfigurazione);
    // return "oconfigurazione/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oconfigurazione.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OConfigurazione oconfigurazione = oconfigurazioneService.findById(id);
    // fixRenderEntityProperty(oconfigurazione);
    // model.addAttribute("oconfigurazione", oconfigurazione);
    // setPageAttributes(model);
    // return "oconfigurazione/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("oconfigurazione") OConfigurazione oconfigurazione, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(oconfigurazione);
    // try {
    // oconfigurazioneService.update(oconfigurazione);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oconfigurazioneService.getValidationMessages(), result, oconfigurazione,
    // e.getMessage());
    // fixRenderEntityProperty(oconfigurazione);
    // return "oconfigurazione/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oconfigurazione.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("oconfigurazione") OConfigurazione oconfigurazione, BindingResult result,
    // SessionStatus status) {
    //
    // OConfigurazione objToDelete = oconfigurazioneService.findById(oconfigurazione.getId());
    // try {
    // oconfigurazioneService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oconfigurazioneService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(oconfigurazione);
    // return "oconfigurazione/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(OConfigurazione entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OConfigurazione entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
