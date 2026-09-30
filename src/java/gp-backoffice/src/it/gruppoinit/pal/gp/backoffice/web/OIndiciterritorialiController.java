package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OIndiciterritoriali;
import it.gruppoinit.pal.gp.core.service.OIndiciterritorialiService;
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
@SessionAttributes("oindiciterritoriali")
public class OIndiciterritorialiController extends BaseController<OIndiciterritoriali> {

    @Autowired
    private OIndiciterritorialiService oindiciterritorialiService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OIndiciterritoriali> oindiciterritorialiList = oindiciterritorialiService.findAll(null, null);
    // ModelMap model = new ModelMap(oindiciterritorialiList);
    // boolean export = createJMesaExport(request, response, oindiciterritorialiList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("oindiciterritorialiList", oindiciterritorialiList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OIndiciterritoriali oindiciterritoriali = new OIndiciterritoriali();
    // oindiciterritoriali.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(oindiciterritoriali);
    // model.addAttribute("oindiciterritoriali", oindiciterritoriali);
    // setPageAttributes(model);
    // return "oindiciterritoriali/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("oindiciterritoriali") OIndiciterritoriali oindiciterritoriali,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(oindiciterritoriali);
    // oindiciterritoriali.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // oindiciterritorialiService.insert(oindiciterritoriali);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oindiciterritorialiService.getValidationMessages(), result, oindiciterritoriali,
    // e.getMessage());
    // fixRenderEntityProperty(oindiciterritoriali);
    // return "oindiciterritoriali/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oindiciterritoriali.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OIndiciterritoriali oindiciterritoriali = oindiciterritorialiService.findById(id);
    // fixRenderEntityProperty(oindiciterritoriali);
    // model.addAttribute("oindiciterritoriali", oindiciterritoriali);
    // setPageAttributes(model);
    // return "oindiciterritoriali/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("oindiciterritoriali") OIndiciterritoriali oindiciterritoriali,
    // BindingResult result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(oindiciterritoriali);
    // try {
    // oindiciterritorialiService.update(oindiciterritoriali);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oindiciterritorialiService.getValidationMessages(), result, oindiciterritoriali,
    // e.getMessage());
    // fixRenderEntityProperty(oindiciterritoriali);
    // return "oindiciterritoriali/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oindiciterritoriali.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("oindiciterritoriali") OIndiciterritoriali oindiciterritoriali,
    // BindingResult result, SessionStatus status) {
    //
    // OIndiciterritoriali objToDelete = oindiciterritorialiService.findById(oindiciterritoriali.getId());
    // try {
    // oindiciterritorialiService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oindiciterritorialiService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(oindiciterritoriali);
    // return "oindiciterritoriali/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(OIndiciterritoriali entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OIndiciterritoriali entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
