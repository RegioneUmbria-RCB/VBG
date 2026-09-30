package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Tipiaffissioniformati;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiaffissioniformatiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 
 * @author 
 */
@Controller
@SessionAttributes("tipiaffissioniformati")
public class TipiaffissioniformatiController extends BaseController<Tipiaffissioniformati> {

    @Autowired
    private TipiaffissioniformatiService tipiaffissioniformatiService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Tipiaffissioniformati> tipiaffissioniformatiList = tipiaffissioniformatiService.findAll(null, null);
    // ModelMap model = new ModelMap(tipiaffissioniformatiList);
    // boolean export = createJMesaExport(request, response, tipiaffissioniformatiList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("tipiaffissioniformatiList", tipiaffissioniformatiList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Tipiaffissioniformati tipiaffissioniformati = new Tipiaffissioniformati();
    // tipiaffissioniformati.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(tipiaffissioniformati);
    // model.addAttribute("tipiaffissioniformati", tipiaffissioniformati);
    // setPageAttributes(model);
    // return "tipiaffissioniformati/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("tipiaffissioniformati") Tipiaffissioniformati tipiaffissioniformati,
    // BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(tipiaffissioniformati);
    // tipiaffissioniformati.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // tipiaffissioniformatiService.insert(tipiaffissioniformati);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(tipiaffissioniformatiService.getValidationMessages(), result, tipiaffissioniformati,
    // e.getMessage());
    // fixRenderEntityProperty(tipiaffissioniformati);
    // return "tipiaffissioniformati/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + tipiaffissioniformati.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Tipiaffissioniformati tipiaffissioniformati = tipiaffissioniformatiService.findById(id);
    // fixRenderEntityProperty(tipiaffissioniformati);
    // model.addAttribute("tipiaffissioniformati", tipiaffissioniformati);
    // setPageAttributes(model);
    // return "tipiaffissioniformati/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("tipiaffissioniformati") Tipiaffissioniformati tipiaffissioniformati,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(tipiaffissioniformati);
    // try {
    // tipiaffissioniformatiService.update(tipiaffissioniformati);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(tipiaffissioniformatiService.getValidationMessages(), result, tipiaffissioniformati,
    // e.getMessage());
    // fixRenderEntityProperty(tipiaffissioniformati);
    // return "tipiaffissioniformati/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + tipiaffissioniformati.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("tipiaffissioniformati") Tipiaffissioniformati tipiaffissioniformati,
    // BindingResult result,
    // SessionStatus status) {
    //
    // Tipiaffissioniformati objToDelete = tipiaffissioniformatiService.findById(tipiaffissioniformati.getId());
    // try {
    // tipiaffissioniformatiService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(tipiaffissioniformatiService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(tipiaffissioniformati);
    // return "tipiaffissioniformati/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(Tipiaffissioniformati entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Tipiaffissioniformati entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
