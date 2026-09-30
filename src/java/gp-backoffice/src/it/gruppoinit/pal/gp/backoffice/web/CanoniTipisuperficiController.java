package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CanoniTipisuperfici;
import it.gruppoinit.pal.gp.core.service.CanoniTipisuperficiService;
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
@SessionAttributes("canonitipisuperfici")
public class CanoniTipisuperficiController extends BaseController<CanoniTipisuperfici> {

    @Autowired
    private CanoniTipisuperficiService canonitipisuperficiService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CanoniTipisuperfici> canonitipisuperficiList = canonitipisuperficiService.findAll(null, null);
    // ModelMap model = new ModelMap(canonitipisuperficiList);
    // boolean export = createJMesaExport(request, response, canonitipisuperficiList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("canonitipisuperficiList", canonitipisuperficiList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CanoniTipisuperfici canonitipisuperfici = new CanoniTipisuperfici();
    // canonitipisuperfici.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(canonitipisuperfici);
    // model.addAttribute("canonitipisuperfici", canonitipisuperfici);
    // setPageAttributes(model);
    // return "canonitipisuperfici/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("canonitipisuperfici") CanoniTipisuperfici canonitipisuperfici,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(canonitipisuperfici);
    // canonitipisuperfici.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // canonitipisuperficiService.insert(canonitipisuperfici);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canonitipisuperficiService.getValidationMessages(), result, canonitipisuperfici,
    // e.getMessage());
    // fixRenderEntityProperty(canonitipisuperfici);
    // return "canonitipisuperfici/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + canonitipisuperfici.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CanoniTipisuperfici canonitipisuperfici = canonitipisuperficiService.findById(id);
    // fixRenderEntityProperty(canonitipisuperfici);
    // model.addAttribute("canonitipisuperfici", canonitipisuperfici);
    // setPageAttributes(model);
    // return "canonitipisuperfici/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("canonitipisuperfici") CanoniTipisuperfici canonitipisuperfici,
    // BindingResult result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(canonitipisuperfici);
    // try {
    // canonitipisuperficiService.update(canonitipisuperfici);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canonitipisuperficiService.getValidationMessages(), result, canonitipisuperfici,
    // e.getMessage());
    // fixRenderEntityProperty(canonitipisuperfici);
    // return "canonitipisuperfici/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + canonitipisuperfici.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("canonitipisuperfici") CanoniTipisuperfici canonitipisuperfici,
    // BindingResult result, SessionStatus status) {
    //
    // CanoniTipisuperfici objToDelete = canonitipisuperficiService.findById(canonitipisuperfici.getId());
    // try {
    // canonitipisuperficiService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canonitipisuperficiService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(canonitipisuperfici);
    // return "canonitipisuperfici/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(CanoniTipisuperfici entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CanoniTipisuperfici entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
