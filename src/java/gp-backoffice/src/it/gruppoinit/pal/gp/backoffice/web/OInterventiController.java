package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OInterventi;
import it.gruppoinit.pal.gp.core.service.OInterventiService;
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
@SessionAttributes("ointerventi")
public class OInterventiController extends BaseController<OInterventi> {

    @Autowired
    private OInterventiService ointerventiService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OInterventi> ointerventiList = ointerventiService.findAll(null, null);
    // ModelMap model = new ModelMap(ointerventiList);
    // boolean export = createJMesaExport(request, response, ointerventiList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ointerventiList", ointerventiList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OInterventi ointerventi = new OInterventi();
    // ointerventi.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ointerventi);
    // model.addAttribute("ointerventi", ointerventi);
    // setPageAttributes(model);
    // return "ointerventi/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ointerventi") OInterventi ointerventi, BindingResult result, SessionStatus
    // status) {
    //
    // fixMergeEntityProperty(ointerventi);
    // ointerventi.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ointerventiService.insert(ointerventi);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ointerventiService.getValidationMessages(), result, ointerventi, e.getMessage());
    // fixRenderEntityProperty(ointerventi);
    // return "ointerventi/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ointerventi.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OInterventi ointerventi = ointerventiService.findById(id);
    // fixRenderEntityProperty(ointerventi);
    // model.addAttribute("ointerventi", ointerventi);
    // setPageAttributes(model);
    // return "ointerventi/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ointerventi") OInterventi ointerventi, BindingResult result, SessionStatus
    // status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ointerventi);
    // try {
    // ointerventiService.update(ointerventi);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ointerventiService.getValidationMessages(), result, ointerventi, e.getMessage());
    // fixRenderEntityProperty(ointerventi);
    // return "ointerventi/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ointerventi.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ointerventi") OInterventi ointerventi, BindingResult result, SessionStatus
    // status) {
    //
    // OInterventi objToDelete = ointerventiService.findById(ointerventi.getId());
    // try {
    // ointerventiService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ointerventiService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(ointerventi);
    // return "ointerventi/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(OInterventi entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OInterventi entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
