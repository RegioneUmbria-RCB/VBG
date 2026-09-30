package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Categorieeventibase;
import it.gruppoinit.pal.gp.core.service.CategorieeventibaseService;
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
@SessionAttributes("categorieeventibase")
public class CategorieeventibaseController extends BaseController<Categorieeventibase> {

    @Autowired
    private CategorieeventibaseService categorieeventibaseService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Categorieeventibase> categorieeventibaseList = categorieeventibaseService.findAll(null, null);
    // ModelMap model = new ModelMap(categorieeventibaseList);
    // boolean export = createJMesaExport(request, response, categorieeventibaseList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("categorieeventibaseList", categorieeventibaseList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Categorieeventibase categorieeventibase = new Categorieeventibase();
    // categorieeventibase.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(categorieeventibase);
    // model.addAttribute("categorieeventibase", categorieeventibase);
    // setPageAttributes(model);
    // return "categorieeventibase/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("categorieeventibase") Categorieeventibase categorieeventibase,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(categorieeventibase);
    // categorieeventibase.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // categorieeventibaseService.insert(categorieeventibase);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(categorieeventibaseService.getValidationMessages(), result, categorieeventibase,
    // e.getMessage());
    // fixRenderEntityProperty(categorieeventibase);
    // return "categorieeventibase/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + categorieeventibase.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Categorieeventibase categorieeventibase = categorieeventibaseService.findById(id);
    // fixRenderEntityProperty(categorieeventibase);
    // model.addAttribute("categorieeventibase", categorieeventibase);
    // setPageAttributes(model);
    // return "categorieeventibase/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("categorieeventibase") Categorieeventibase categorieeventibase,
    // BindingResult result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(categorieeventibase);
    // try {
    // categorieeventibaseService.update(categorieeventibase);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(categorieeventibaseService.getValidationMessages(), result, categorieeventibase,
    // e.getMessage());
    // fixRenderEntityProperty(categorieeventibase);
    // return "categorieeventibase/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + categorieeventibase.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("categorieeventibase") Categorieeventibase categorieeventibase,
    // BindingResult result, SessionStatus status) {
    //
    // Categorieeventibase objToDelete = categorieeventibaseService.findById(categorieeventibase.getId());
    // try {
    // categorieeventibaseService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(categorieeventibaseService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(categorieeventibase);
    // return "categorieeventibase/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Categorieeventibase entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Categorieeventibase entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
