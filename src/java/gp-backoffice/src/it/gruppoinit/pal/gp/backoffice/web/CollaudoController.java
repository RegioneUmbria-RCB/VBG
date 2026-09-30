package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Collaudo;
import it.gruppoinit.pal.gp.core.service.CollaudoService;
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
@SessionAttributes("collaudo")
public class CollaudoController extends BaseController<Collaudo> {

    @Autowired
    private CollaudoService collaudoService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Collaudo> collaudoList = collaudoService.findAll(null, null);
    // ModelMap model = new ModelMap(collaudoList);
    // boolean export = createJMesaExport(request, response, collaudoList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("collaudoList", collaudoList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Collaudo collaudo = new Collaudo();
    // collaudo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(collaudo);
    // model.addAttribute("collaudo", collaudo);
    // setPageAttributes(model);
    // return "collaudo/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("collaudo") Collaudo collaudo, BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(collaudo);
    // collaudo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // collaudoService.insert(collaudo);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(collaudoService.getValidationMessages(), result, collaudo, e.getMessage());
    // fixRenderEntityProperty(collaudo);
    // return "collaudo/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + collaudo.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Collaudo collaudo = collaudoService.findById(id);
    // fixRenderEntityProperty(collaudo);
    // model.addAttribute("collaudo", collaudo);
    // setPageAttributes(model);
    // return "collaudo/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("collaudo") Collaudo collaudo, BindingResult result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(collaudo);
    // try {
    // collaudoService.update(collaudo);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(collaudoService.getValidationMessages(), result, collaudo, e.getMessage());
    // fixRenderEntityProperty(collaudo);
    // return "collaudo/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + collaudo.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("collaudo") Collaudo collaudo, BindingResult result, SessionStatus status) {
    //
    // Collaudo objToDelete = collaudoService.findById(collaudo.getId());
    // try {
    // collaudoService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(collaudoService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(collaudo);
    // return "collaudo/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(Collaudo entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Collaudo entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
