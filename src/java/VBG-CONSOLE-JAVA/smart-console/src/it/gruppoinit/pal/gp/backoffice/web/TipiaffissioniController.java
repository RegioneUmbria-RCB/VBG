package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Tipiaffissioni;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiaffissioniService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 
 * @author 
 */
//DAELIMINARE @Controller
@SessionAttributes("tipiaffissioni")
public class TipiaffissioniController extends BaseController<Tipiaffissioni> {

    @Autowired
    private TipiaffissioniService tipiaffissioniService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Tipiaffissioni> tipiaffissioniList = tipiaffissioniService.findAll(null, null);
    // ModelMap model = new ModelMap(tipiaffissioniList);
    // boolean export = createJMesaExport(request, response, tipiaffissioniList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("tipiaffissioniList", tipiaffissioniList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Tipiaffissioni tipiaffissioni = new Tipiaffissioni();
    // tipiaffissioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(tipiaffissioni);
    // model.addAttribute("tipiaffissioni", tipiaffissioni);
    // setPageAttributes(model);
    // return "tipiaffissioni/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("tipiaffissioni") Tipiaffissioni tipiaffissioni, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(tipiaffissioni);
    // tipiaffissioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // tipiaffissioniService.insert(tipiaffissioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(tipiaffissioniService.getValidationMessages(), result, tipiaffissioni, e.getMessage());
    // fixRenderEntityProperty(tipiaffissioni);
    // return "tipiaffissioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + tipiaffissioni.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Tipiaffissioni tipiaffissioni = tipiaffissioniService.findById(id);
    // fixRenderEntityProperty(tipiaffissioni);
    // model.addAttribute("tipiaffissioni", tipiaffissioni);
    // setPageAttributes(model);
    // return "tipiaffissioni/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("tipiaffissioni") Tipiaffissioni tipiaffissioni, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(tipiaffissioni);
    // try {
    // tipiaffissioniService.update(tipiaffissioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(tipiaffissioniService.getValidationMessages(), result, tipiaffissioni, e.getMessage());
    // fixRenderEntityProperty(tipiaffissioni);
    // return "tipiaffissioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + tipiaffissioni.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("tipiaffissioni") Tipiaffissioni tipiaffissioni, BindingResult result,
    // SessionStatus status) {
    //
    // Tipiaffissioni objToDelete = tipiaffissioniService.findById(tipiaffissioni.getId());
    // try {
    // tipiaffissioniService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(tipiaffissioniService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(tipiaffissioni);
    // return "tipiaffissioni/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Tipiaffissioni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Tipiaffissioni entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
