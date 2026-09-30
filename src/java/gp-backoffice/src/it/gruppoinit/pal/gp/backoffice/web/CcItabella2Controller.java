package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcItabella2;
import it.gruppoinit.pal.gp.core.service.CcItabella2Service;
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
@SessionAttributes("ccitabella2")
public class CcItabella2Controller extends BaseController<CcItabella2> {

    @Autowired
    private CcItabella2Service ccitabella2Service;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcItabella2> ccitabella2List = ccitabella2Service.findAll(null, null);
    // ModelMap model = new ModelMap(ccitabella2List);
    // boolean export = createJMesaExport(request, response, ccitabella2List);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccitabella2List", ccitabella2List);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcItabella2 ccitabella2 = new CcItabella2();
    // ccitabella2.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccitabella2);
    // model.addAttribute("ccitabella2", ccitabella2);
    // setPageAttributes(model);
    // return "ccitabella2/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccitabella2") CcItabella2 ccitabella2, BindingResult result, SessionStatus
    // status) {
    //
    // fixMergeEntityProperty(ccitabella2);
    // ccitabella2.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccitabella2Service.insert(ccitabella2);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccitabella2Service.getValidationMessages(), result, ccitabella2, e.getMessage());
    // fixRenderEntityProperty(ccitabella2);
    // return "ccitabella2/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccitabella2.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcItabella2 ccitabella2 = ccitabella2Service.findById(id);
    // fixRenderEntityProperty(ccitabella2);
    // model.addAttribute("ccitabella2", ccitabella2);
    // setPageAttributes(model);
    // return "ccitabella2/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccitabella2") CcItabella2 ccitabella2, BindingResult result, SessionStatus
    // status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccitabella2);
    // try {
    // ccitabella2Service.update(ccitabella2);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccitabella2Service.getValidationMessages(), result, ccitabella2, e.getMessage());
    // fixRenderEntityProperty(ccitabella2);
    // return "ccitabella2/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccitabella2.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccitabella2") CcItabella2 ccitabella2, BindingResult result, SessionStatus
    // status) {
    //
    // CcItabella2 objToDelete = ccitabella2Service.findById(ccitabella2.getId());
    // try {
    // ccitabella2Service.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccitabella2Service.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(ccitabella2);
    // return "ccitabella2/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcItabella2 entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcItabella2 entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
