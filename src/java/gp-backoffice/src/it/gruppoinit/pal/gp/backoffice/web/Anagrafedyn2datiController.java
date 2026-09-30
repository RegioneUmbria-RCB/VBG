package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2dati;
import it.gruppoinit.pal.gp.core.service.Anagrafedyn2datiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("anagrafedyn2dati")
public class Anagrafedyn2datiController extends BaseController<Anagrafedyn2dati> {

    @Autowired
    private Anagrafedyn2datiService anagrafedyn2datiService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Anagrafedyn2dati> anagrafedyn2datiList = anagrafedyn2datiService.findAll(null, null);
    // ModelMap model = new ModelMap(anagrafedyn2datiList);
    // boolean export = createJMesaExport(request, response, anagrafedyn2datiList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("anagrafedyn2datiList", anagrafedyn2datiList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Anagrafedyn2dati anagrafedyn2dati = new Anagrafedyn2dati();
    // fixRenderEntityProperty(anagrafedyn2dati);
    // model.addAttribute("anagrafedyn2dati", anagrafedyn2dati);
    // setPageAttributes(model);
    // return "anagrafedyn2dati/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("anagrafedyn2dati") Anagrafedyn2dati anagrafedyn2dati, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(anagrafedyn2dati);
    // try {
    // anagrafedyn2datiService.insert(anagrafedyn2dati);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(anagrafedyn2datiService.getValidationMessages(), result, anagrafedyn2dati,
    // e.getMessage());
    // fixRenderEntityProperty(anagrafedyn2dati);
    // return "anagrafedyn2dati/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + anagrafedyn2dati.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Anagrafedyn2dati anagrafedyn2dati = anagrafedyn2datiService.findById(id);
    // fixRenderEntityProperty(anagrafedyn2dati);
    // model.addAttribute("anagrafedyn2dati", anagrafedyn2dati);
    // setPageAttributes(model);
    // return "anagrafedyn2dati/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("anagrafedyn2dati") Anagrafedyn2dati anagrafedyn2dati, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(anagrafedyn2dati);
    // try {
    // anagrafedyn2datiService.update(anagrafedyn2dati);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(anagrafedyn2datiService.getValidationMessages(), result, anagrafedyn2dati,
    // e.getMessage());
    // fixRenderEntityProperty(anagrafedyn2dati);
    // return "anagrafedyn2dati/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + anagrafedyn2dati.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("anagrafedyn2dati") Anagrafedyn2dati anagrafedyn2dati, BindingResult result,
    // SessionStatus status) {
    //
    // Anagrafedyn2dati objToDelete = anagrafedyn2datiService.findById(anagrafedyn2dati.getId());
    // try {
    // anagrafedyn2datiService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(anagrafedyn2datiService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(anagrafedyn2dati);
    // return "anagrafedyn2dati/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Anagrafedyn2dati entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Anagrafedyn2dati entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
