package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.PertinenzeCoefficienti;
import it.gruppoinit.pal.gp.core.service.PertinenzeCoefficientiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 
 * @author 
 */
//DAELIMINARE @Controller
@SessionAttributes("pertinenzecoefficienti")
public class PertinenzeCoefficientiController extends BaseController<PertinenzeCoefficienti> {

    @Autowired
    private PertinenzeCoefficientiService pertinenzecoefficientiService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<PertinenzeCoefficienti> pertinenzecoefficientiList = pertinenzecoefficientiService.findAll(null, null);
    // ModelMap model = new ModelMap(pertinenzecoefficientiList);
    // boolean export = createJMesaExport(request, response, pertinenzecoefficientiList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("pertinenzecoefficientiList", pertinenzecoefficientiList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // PertinenzeCoefficienti pertinenzecoefficienti = new PertinenzeCoefficienti();
    // pertinenzecoefficienti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(pertinenzecoefficienti);
    // model.addAttribute("pertinenzecoefficienti", pertinenzecoefficienti);
    // setPageAttributes(model);
    // return "pertinenzecoefficienti/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("pertinenzecoefficienti") PertinenzeCoefficienti pertinenzecoefficienti,
    // BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(pertinenzecoefficienti);
    // pertinenzecoefficienti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // pertinenzecoefficientiService.insert(pertinenzecoefficienti);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(pertinenzecoefficientiService.getValidationMessages(), result, pertinenzecoefficienti,
    // e.getMessage());
    // fixRenderEntityProperty(pertinenzecoefficienti);
    // return "pertinenzecoefficienti/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + pertinenzecoefficienti.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // PertinenzeCoefficienti pertinenzecoefficienti = pertinenzecoefficientiService.findById(id);
    // fixRenderEntityProperty(pertinenzecoefficienti);
    // model.addAttribute("pertinenzecoefficienti", pertinenzecoefficienti);
    // setPageAttributes(model);
    // return "pertinenzecoefficienti/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("pertinenzecoefficienti") PertinenzeCoefficienti pertinenzecoefficienti,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(pertinenzecoefficienti);
    // try {
    // pertinenzecoefficientiService.update(pertinenzecoefficienti);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(pertinenzecoefficientiService.getValidationMessages(), result, pertinenzecoefficienti,
    // e.getMessage());
    // fixRenderEntityProperty(pertinenzecoefficienti);
    // return "pertinenzecoefficienti/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + pertinenzecoefficienti.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("pertinenzecoefficienti") PertinenzeCoefficienti pertinenzecoefficienti,
    // BindingResult result,
    // SessionStatus status) {
    //
    // PertinenzeCoefficienti objToDelete = pertinenzecoefficientiService.findById(pertinenzecoefficienti.getId());
    // try {
    // pertinenzecoefficientiService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(pertinenzecoefficientiService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(pertinenzecoefficienti);
    // return "pertinenzecoefficienti/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(PertinenzeCoefficienti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(PertinenzeCoefficienti entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
