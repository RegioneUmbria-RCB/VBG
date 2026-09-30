package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CanoniConfigurazione;
import it.gruppoinit.pal.gp.core.service.CanoniConfigurazioneService;
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
@SessionAttributes("canoniconfigurazione")
public class CanoniConfigurazioneController extends BaseController<CanoniConfigurazione> {

    @Autowired
    private CanoniConfigurazioneService canoniconfigurazioneService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CanoniConfigurazione> canoniconfigurazioneList = canoniconfigurazioneService.findAll(null, null);
    // ModelMap model = new ModelMap(canoniconfigurazioneList);
    // boolean export = createJMesaExport(request, response, canoniconfigurazioneList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("canoniconfigurazioneList", canoniconfigurazioneList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CanoniConfigurazione canoniconfigurazione = new CanoniConfigurazione();
    // canoniconfigurazione.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(canoniconfigurazione);
    // model.addAttribute("canoniconfigurazione", canoniconfigurazione);
    // setPageAttributes(model);
    // return "canoniconfigurazione/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("canoniconfigurazione") CanoniConfigurazione canoniconfigurazione,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(canoniconfigurazione);
    // canoniconfigurazione.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // canoniconfigurazioneService.insert(canoniconfigurazione);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canoniconfigurazioneService.getValidationMessages(), result, canoniconfigurazione,
    // e.getMessage());
    // fixRenderEntityProperty(canoniconfigurazione);
    // return "canoniconfigurazione/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + canoniconfigurazione.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CanoniConfigurazione canoniconfigurazione = canoniconfigurazioneService.findById(id);
    // fixRenderEntityProperty(canoniconfigurazione);
    // model.addAttribute("canoniconfigurazione", canoniconfigurazione);
    // setPageAttributes(model);
    // return "canoniconfigurazione/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("canoniconfigurazione") CanoniConfigurazione canoniconfigurazione,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(canoniconfigurazione);
    // try {
    // canoniconfigurazioneService.update(canoniconfigurazione);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canoniconfigurazioneService.getValidationMessages(), result, canoniconfigurazione,
    // e.getMessage());
    // fixRenderEntityProperty(canoniconfigurazione);
    // return "canoniconfigurazione/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + canoniconfigurazione.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("canoniconfigurazione") CanoniConfigurazione canoniconfigurazione,
    // BindingResult result, SessionStatus status) {
    //
    // CanoniConfigurazione objToDelete = canoniconfigurazioneService.findById(canoniconfigurazione.getId());
    // try {
    // canoniconfigurazioneService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canoniconfigurazioneService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(canoniconfigurazione);
    // return "canoniconfigurazione/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CanoniConfigurazione entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CanoniConfigurazione entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
