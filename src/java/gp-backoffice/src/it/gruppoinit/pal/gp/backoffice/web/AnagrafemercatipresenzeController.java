package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Anagrafemercatipresenze;
import it.gruppoinit.pal.gp.core.service.AnagrafemercatipresenzeService;
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
@SessionAttributes("anagrafemercatipresenze")
public class AnagrafemercatipresenzeController extends BaseController<Anagrafemercatipresenze> {

    @Autowired
    private AnagrafemercatipresenzeService anagrafemercatipresenzeService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Anagrafemercatipresenze> anagrafemercatipresenzeList = anagrafemercatipresenzeService.findAll(null, null);
    // ModelMap model = new ModelMap(anagrafemercatipresenzeList);
    // boolean export = createJMesaExport(request, response, anagrafemercatipresenzeList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("anagrafemercatipresenzeList", anagrafemercatipresenzeList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Anagrafemercatipresenze anagrafemercatipresenze = new Anagrafemercatipresenze();
    // anagrafemercatipresenze.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(anagrafemercatipresenze);
    // model.addAttribute("anagrafemercatipresenze", anagrafemercatipresenze);
    // setPageAttributes(model);
    // return "anagrafemercatipresenze/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("anagrafemercatipresenze") Anagrafemercatipresenze anagrafemercatipresenze,
    // BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(anagrafemercatipresenze);
    // anagrafemercatipresenze.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // anagrafemercatipresenzeService.insert(anagrafemercatipresenze);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(anagrafemercatipresenzeService.getValidationMessages(), result,
    // anagrafemercatipresenze, e.getMessage());
    // fixRenderEntityProperty(anagrafemercatipresenze);
    // return "anagrafemercatipresenze/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + anagrafemercatipresenze.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Anagrafemercatipresenze anagrafemercatipresenze = anagrafemercatipresenzeService.findById(id);
    // fixRenderEntityProperty(anagrafemercatipresenze);
    // model.addAttribute("anagrafemercatipresenze", anagrafemercatipresenze);
    // setPageAttributes(model);
    // return "anagrafemercatipresenze/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("anagrafemercatipresenze") Anagrafemercatipresenze anagrafemercatipresenze,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(anagrafemercatipresenze);
    // try {
    // anagrafemercatipresenzeService.update(anagrafemercatipresenze);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(anagrafemercatipresenzeService.getValidationMessages(), result,
    // anagrafemercatipresenze, e.getMessage());
    // fixRenderEntityProperty(anagrafemercatipresenze);
    // return "anagrafemercatipresenze/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + anagrafemercatipresenze.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("anagrafemercatipresenze") Anagrafemercatipresenze anagrafemercatipresenze,
    // BindingResult result,
    // SessionStatus status) {
    //
    // Anagrafemercatipresenze objToDelete = anagrafemercatipresenzeService.findById(anagrafemercatipresenze.getId());
    // try {
    // anagrafemercatipresenzeService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(anagrafemercatipresenzeService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(anagrafemercatipresenze);
    // return "anagrafemercatipresenze/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Anagrafemercatipresenze entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Anagrafemercatipresenze entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
