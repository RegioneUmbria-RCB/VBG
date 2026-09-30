package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CanoniConfigaree;
import it.gruppoinit.pal.gp.core.service.CanoniConfigareeService;
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
@SessionAttributes("canoniconfigaree")
public class CanoniConfigareeController extends BaseController<CanoniConfigaree> {

    @Autowired
    private CanoniConfigareeService canoniconfigareeService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CanoniConfigaree> canoniconfigareeList = canoniconfigareeService.findAll(null, null);
    // ModelMap model = new ModelMap(canoniconfigareeList);
    // boolean export = createJMesaExport(request, response, canoniconfigareeList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("canoniconfigareeList", canoniconfigareeList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CanoniConfigaree canoniconfigaree = new CanoniConfigaree();
    // canoniconfigaree.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(canoniconfigaree);
    // model.addAttribute("canoniconfigaree", canoniconfigaree);
    // setPageAttributes(model);
    // return "canoniconfigaree/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("canoniconfigaree") CanoniConfigaree canoniconfigaree, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(canoniconfigaree);
    // canoniconfigaree.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // canoniconfigareeService.insert(canoniconfigaree);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canoniconfigareeService.getValidationMessages(), result, canoniconfigaree,
    // e.getMessage());
    // fixRenderEntityProperty(canoniconfigaree);
    // return "canoniconfigaree/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + canoniconfigaree.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CanoniConfigaree canoniconfigaree = canoniconfigareeService.findById(id);
    // fixRenderEntityProperty(canoniconfigaree);
    // model.addAttribute("canoniconfigaree", canoniconfigaree);
    // setPageAttributes(model);
    // return "canoniconfigaree/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("canoniconfigaree") CanoniConfigaree canoniconfigaree, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(canoniconfigaree);
    // try {
    // canoniconfigareeService.update(canoniconfigaree);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canoniconfigareeService.getValidationMessages(), result, canoniconfigaree,
    // e.getMessage());
    // fixRenderEntityProperty(canoniconfigaree);
    // return "canoniconfigaree/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + canoniconfigaree.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("canoniconfigaree") CanoniConfigaree canoniconfigaree, BindingResult result,
    // SessionStatus status) {
    //
    // CanoniConfigaree objToDelete = canoniconfigareeService.findById(canoniconfigaree.getId());
    // try {
    // canoniconfigareeService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(canoniconfigareeService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(canoniconfigaree);
    // return "canoniconfigaree/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CanoniConfigaree entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CanoniConfigaree entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
