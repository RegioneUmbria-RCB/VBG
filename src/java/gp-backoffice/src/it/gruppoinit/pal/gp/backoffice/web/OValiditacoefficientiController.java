package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OValiditacoefficienti;
import it.gruppoinit.pal.gp.core.service.OValiditacoefficientiService;
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
@SessionAttributes("ovaliditacoefficienti")
public class OValiditacoefficientiController extends BaseController<OValiditacoefficienti> {

    @Autowired
    private OValiditacoefficientiService ovaliditacoefficientiService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OValiditacoefficienti> ovaliditacoefficientiList = ovaliditacoefficientiService.findAll(null, null);
    // ModelMap model = new ModelMap(ovaliditacoefficientiList);
    // boolean export = createJMesaExport(request, response, ovaliditacoefficientiList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ovaliditacoefficientiList", ovaliditacoefficientiList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OValiditacoefficienti ovaliditacoefficienti = new OValiditacoefficienti();
    // ovaliditacoefficienti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ovaliditacoefficienti);
    // model.addAttribute("ovaliditacoefficienti", ovaliditacoefficienti);
    // setPageAttributes(model);
    // return "ovaliditacoefficienti/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ovaliditacoefficienti") OValiditacoefficienti ovaliditacoefficienti,
    // BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(ovaliditacoefficienti);
    // ovaliditacoefficienti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ovaliditacoefficientiService.insert(ovaliditacoefficienti);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ovaliditacoefficientiService.getValidationMessages(), result, ovaliditacoefficienti,
    // e.getMessage());
    // fixRenderEntityProperty(ovaliditacoefficienti);
    // return "ovaliditacoefficienti/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ovaliditacoefficienti.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OValiditacoefficienti ovaliditacoefficienti = ovaliditacoefficientiService.findById(id);
    // fixRenderEntityProperty(ovaliditacoefficienti);
    // model.addAttribute("ovaliditacoefficienti", ovaliditacoefficienti);
    // setPageAttributes(model);
    // return "ovaliditacoefficienti/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ovaliditacoefficienti") OValiditacoefficienti ovaliditacoefficienti,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ovaliditacoefficienti);
    // try {
    // ovaliditacoefficientiService.update(ovaliditacoefficienti);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ovaliditacoefficientiService.getValidationMessages(), result, ovaliditacoefficienti,
    // e.getMessage());
    // fixRenderEntityProperty(ovaliditacoefficienti);
    // return "ovaliditacoefficienti/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ovaliditacoefficienti.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ovaliditacoefficienti") OValiditacoefficienti ovaliditacoefficienti,
    // BindingResult result,
    // SessionStatus status) {
    //
    // OValiditacoefficienti objToDelete = ovaliditacoefficientiService.findById(ovaliditacoefficienti.getId());
    // try {
    // ovaliditacoefficientiService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ovaliditacoefficientiService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(ovaliditacoefficienti);
    // return "ovaliditacoefficienti/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(OValiditacoefficienti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OValiditacoefficienti entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
