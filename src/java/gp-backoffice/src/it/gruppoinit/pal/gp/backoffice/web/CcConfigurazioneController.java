package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcConfigurazione;
import it.gruppoinit.pal.gp.core.service.CcConfigurazioneService;
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
@SessionAttributes("ccconfigurazione")
public class CcConfigurazioneController extends BaseController<CcConfigurazione> {

    @Autowired
    private CcConfigurazioneService ccconfigurazioneService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcConfigurazione> ccconfigurazioneList = ccconfigurazioneService.findAll(null, null);
    // ModelMap model = new ModelMap(ccconfigurazioneList);
    // boolean export = createJMesaExport(request, response, ccconfigurazioneList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccconfigurazioneList", ccconfigurazioneList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcConfigurazione ccconfigurazione = new CcConfigurazione();
    // ccconfigurazione.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccconfigurazione);
    // model.addAttribute("ccconfigurazione", ccconfigurazione);
    // setPageAttributes(model);
    // return "ccconfigurazione/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccconfigurazione") CcConfigurazione ccconfigurazione, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(ccconfigurazione);
    // ccconfigurazione.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccconfigurazioneService.insert(ccconfigurazione);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccconfigurazioneService.getValidationMessages(), result, ccconfigurazione,
    // e.getMessage());
    // fixRenderEntityProperty(ccconfigurazione);
    // return "ccconfigurazione/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccconfigurazione.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcConfigurazione ccconfigurazione = ccconfigurazioneService.findById(id);
    // fixRenderEntityProperty(ccconfigurazione);
    // model.addAttribute("ccconfigurazione", ccconfigurazione);
    // setPageAttributes(model);
    // return "ccconfigurazione/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccconfigurazione") CcConfigurazione ccconfigurazione, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccconfigurazione);
    // try {
    // ccconfigurazioneService.update(ccconfigurazione);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccconfigurazioneService.getValidationMessages(), result, ccconfigurazione,
    // e.getMessage());
    // fixRenderEntityProperty(ccconfigurazione);
    // return "ccconfigurazione/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccconfigurazione.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccconfigurazione") CcConfigurazione ccconfigurazione, BindingResult result,
    // SessionStatus status) {
    //
    // CcConfigurazione objToDelete = ccconfigurazioneService.findById(ccconfigurazione.getId());
    // try {
    // ccconfigurazioneService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccconfigurazioneService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(ccconfigurazione);
    // return "ccconfigurazione/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcConfigurazione entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcConfigurazione entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
