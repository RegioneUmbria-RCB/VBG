package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Istanzeaffissioniassegnazioni;
import it.gruppoinit.pal.gp.core.service.IstanzeaffissioniassegnazioniService;
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
@SessionAttributes("istanzeaffissioniassegnazioni")
public class IstanzeaffissioniassegnazioniController extends BaseController<Istanzeaffissioniassegnazioni> {

    @Autowired
    private IstanzeaffissioniassegnazioniService istanzeaffissioniassegnazioniService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Istanzeaffissioniassegnazioni> istanzeaffissioniassegnazioniList =
    // istanzeaffissioniassegnazioniService.findAll(null, null);
    // ModelMap model = new ModelMap(istanzeaffissioniassegnazioniList);
    // boolean export = createJMesaExport(request, response, istanzeaffissioniassegnazioniList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("istanzeaffissioniassegnazioniList", istanzeaffissioniassegnazioniList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Istanzeaffissioniassegnazioni istanzeaffissioniassegnazioni = new Istanzeaffissioniassegnazioni();
    // istanzeaffissioniassegnazioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(istanzeaffissioniassegnazioni);
    // model.addAttribute("istanzeaffissioniassegnazioni", istanzeaffissioniassegnazioni);
    // setPageAttributes(model);
    // return "istanzeaffissioniassegnazioni/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("istanzeaffissioniassegnazioni") Istanzeaffissioniassegnazioni
    // istanzeaffissioniassegnazioni,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(istanzeaffissioniassegnazioni);
    // istanzeaffissioniassegnazioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // istanzeaffissioniassegnazioniService.insert(istanzeaffissioniassegnazioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzeaffissioniassegnazioniService.getValidationMessages(), result,
    // istanzeaffissioniassegnazioni,
    // e.getMessage());
    // fixRenderEntityProperty(istanzeaffissioniassegnazioni);
    // return "istanzeaffissioniassegnazioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzeaffissioniassegnazioni.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Istanzeaffissioniassegnazioni istanzeaffissioniassegnazioni = istanzeaffissioniassegnazioniService.findById(id);
    // fixRenderEntityProperty(istanzeaffissioniassegnazioni);
    // model.addAttribute("istanzeaffissioniassegnazioni", istanzeaffissioniassegnazioni);
    // setPageAttributes(model);
    // return "istanzeaffissioniassegnazioni/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("istanzeaffissioniassegnazioni") Istanzeaffissioniassegnazioni
    // istanzeaffissioniassegnazioni,
    // BindingResult result, SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(istanzeaffissioniassegnazioni);
    // try {
    // istanzeaffissioniassegnazioniService.update(istanzeaffissioniassegnazioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzeaffissioniassegnazioniService.getValidationMessages(), result,
    // istanzeaffissioniassegnazioni,
    // e.getMessage());
    // fixRenderEntityProperty(istanzeaffissioniassegnazioni);
    // return "istanzeaffissioniassegnazioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzeaffissioniassegnazioni.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("istanzeaffissioniassegnazioni") Istanzeaffissioniassegnazioni
    // istanzeaffissioniassegnazioni,
    // BindingResult result, SessionStatus status) {
    //
    // Istanzeaffissioniassegnazioni objToDelete =
    // istanzeaffissioniassegnazioniService.findById(istanzeaffissioniassegnazioni.getId());
    // try {
    // istanzeaffissioniassegnazioniService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzeaffissioniassegnazioniService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(istanzeaffissioniassegnazioni);
    // return "istanzeaffissioniassegnazioni/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Istanzeaffissioniassegnazioni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzeaffissioniassegnazioni entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
