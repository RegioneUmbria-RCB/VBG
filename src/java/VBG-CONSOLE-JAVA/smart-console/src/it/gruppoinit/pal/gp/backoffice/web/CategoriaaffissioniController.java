package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Categoriaaffissioni;
import it.gruppoinit.pal.gp.core.service.CategoriaaffissioniService;
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
@SessionAttributes("categoriaaffissioni")
public class CategoriaaffissioniController extends BaseController<Categoriaaffissioni> {

    @Autowired
    private CategoriaaffissioniService categoriaaffissioniService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Categoriaaffissioni> categoriaaffissioniList = categoriaaffissioniService.findAll(null, null);
    // ModelMap model = new ModelMap(categoriaaffissioniList);
    // boolean export = createJMesaExport(request, response, categoriaaffissioniList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("categoriaaffissioniList", categoriaaffissioniList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Categoriaaffissioni categoriaaffissioni = new Categoriaaffissioni();
    // categoriaaffissioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(categoriaaffissioni);
    // model.addAttribute("categoriaaffissioni", categoriaaffissioni);
    // setPageAttributes(model);
    // return "categoriaaffissioni/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("categoriaaffissioni") Categoriaaffissioni categoriaaffissioni,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(categoriaaffissioni);
    // categoriaaffissioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // categoriaaffissioniService.insert(categoriaaffissioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(categoriaaffissioniService.getValidationMessages(), result, categoriaaffissioni,
    // e.getMessage());
    // fixRenderEntityProperty(categoriaaffissioni);
    // return "categoriaaffissioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + categoriaaffissioni.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Categoriaaffissioni categoriaaffissioni = categoriaaffissioniService.findById(id);
    // fixRenderEntityProperty(categoriaaffissioni);
    // model.addAttribute("categoriaaffissioni", categoriaaffissioni);
    // setPageAttributes(model);
    // return "categoriaaffissioni/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("categoriaaffissioni") Categoriaaffissioni categoriaaffissioni,
    // BindingResult result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(categoriaaffissioni);
    // try {
    // categoriaaffissioniService.update(categoriaaffissioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(categoriaaffissioniService.getValidationMessages(), result, categoriaaffissioni,
    // e.getMessage());
    // fixRenderEntityProperty(categoriaaffissioni);
    // return "categoriaaffissioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + categoriaaffissioni.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("categoriaaffissioni") Categoriaaffissioni categoriaaffissioni,
    // BindingResult result, SessionStatus status) {
    //
    // Categoriaaffissioni objToDelete = categoriaaffissioniService.findById(categoriaaffissioni.getId());
    // try {
    // categoriaaffissioniService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(categoriaaffissioniService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(categoriaaffissioni);
    // return "categoriaaffissioni/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Categoriaaffissioni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Categoriaaffissioni entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
