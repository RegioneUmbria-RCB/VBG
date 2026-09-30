package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellit;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2modellitService;
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
@SessionAttributes("iattivitadyn2modellit")
public class IAttivitadyn2modellitController extends BaseController<IAttivitadyn2modellit> {

    @Autowired
    private IAttivitadyn2modellitService iattivitadyn2modellitService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<IAttivitadyn2modellit> iattivitadyn2modellitList = iattivitadyn2modellitService.findAll(null, null);
    // ModelMap model = new ModelMap(iattivitadyn2modellitList);
    // boolean export = createJMesaExport(request, response, iattivitadyn2modellitList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("iattivitadyn2modellitList", iattivitadyn2modellitList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // IAttivitadyn2modellit iattivitadyn2modellit = new IAttivitadyn2modellit();
    // iattivitadyn2modellit.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(iattivitadyn2modellit);
    // model.addAttribute("iattivitadyn2modellit", iattivitadyn2modellit);
    // setPageAttributes(model);
    // return "iattivitadyn2modellit/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("iattivitadyn2modellit") IAttivitadyn2modellit iattivitadyn2modellit,
    // BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(iattivitadyn2modellit);
    // iattivitadyn2modellit.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // iattivitadyn2modellitService.insert(iattivitadyn2modellit);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(iattivitadyn2modellitService.getValidationMessages(), result, iattivitadyn2modellit,
    // e.getMessage());
    // fixRenderEntityProperty(iattivitadyn2modellit);
    // return "iattivitadyn2modellit/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + iattivitadyn2modellit.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // IAttivitadyn2modellit iattivitadyn2modellit = iattivitadyn2modellitService.findById(id);
    // fixRenderEntityProperty(iattivitadyn2modellit);
    // model.addAttribute("iattivitadyn2modellit", iattivitadyn2modellit);
    // setPageAttributes(model);
    // return "iattivitadyn2modellit/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("iattivitadyn2modellit") IAttivitadyn2modellit iattivitadyn2modellit,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(iattivitadyn2modellit);
    // try {
    // iattivitadyn2modellitService.update(iattivitadyn2modellit);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(iattivitadyn2modellitService.getValidationMessages(), result, iattivitadyn2modellit,
    // e.getMessage());
    // fixRenderEntityProperty(iattivitadyn2modellit);
    // return "iattivitadyn2modellit/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + iattivitadyn2modellit.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("iattivitadyn2modellit") IAttivitadyn2modellit iattivitadyn2modellit,
    // BindingResult result,
    // SessionStatus status) {
    //
    // IAttivitadyn2modellit objToDelete = iattivitadyn2modellitService.findById(iattivitadyn2modellit.getId());
    // try {
    // iattivitadyn2modellitService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(iattivitadyn2modellitService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(iattivitadyn2modellit);
    // return "iattivitadyn2modellit/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(IAttivitadyn2modellit entity) {

    }

    @Override
    protected void fixRenderEntityProperty(IAttivitadyn2modellit entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
