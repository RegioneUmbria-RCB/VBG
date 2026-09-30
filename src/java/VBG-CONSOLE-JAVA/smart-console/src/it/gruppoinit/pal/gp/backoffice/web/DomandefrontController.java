package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Domandefront;
import it.gruppoinit.pal.gp.core.service.DomandefrontService;
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
@SessionAttributes("domandefront")
public class DomandefrontController extends BaseController<Domandefront> {

    @Autowired
    private DomandefrontService domandefrontService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Domandefront> domandefrontList = domandefrontService.findAll(null, null);
    // ModelMap model = new ModelMap(domandefrontList);
    // boolean export = createJMesaExport(request, response, domandefrontList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("domandefrontList", domandefrontList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Domandefront domandefront = new Domandefront();
    // domandefront.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(domandefront);
    // model.addAttribute("domandefront", domandefront);
    // setPageAttributes(model);
    // return "domandefront/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("domandefront") Domandefront domandefront, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(domandefront);
    // domandefront.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // domandefrontService.insert(domandefront);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(domandefrontService.getValidationMessages(), result, domandefront, e.getMessage());
    // fixRenderEntityProperty(domandefront);
    // return "domandefront/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + domandefront.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Domandefront domandefront = domandefrontService.findById(id);
    // fixRenderEntityProperty(domandefront);
    // model.addAttribute("domandefront", domandefront);
    // setPageAttributes(model);
    // return "domandefront/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("domandefront") Domandefront domandefront, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(domandefront);
    // try {
    // domandefrontService.update(domandefront);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(domandefrontService.getValidationMessages(), result, domandefront, e.getMessage());
    // fixRenderEntityProperty(domandefront);
    // return "domandefront/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + domandefront.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("domandefront") Domandefront domandefront, BindingResult result,
    // SessionStatus status) {
    //
    // Domandefront objToDelete = domandefrontService.findById(domandefront.getId());
    // try {
    // domandefrontService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(domandefrontService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(domandefront);
    // return "domandefront/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Domandefront entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Domandefront entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
