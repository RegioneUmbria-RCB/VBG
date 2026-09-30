package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcItabella4;
import it.gruppoinit.pal.gp.core.service.CcItabella4Service;
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
@SessionAttributes("ccitabella4")
public class CcItabella4Controller extends BaseController<CcItabella4> {

    @Autowired
    private CcItabella4Service ccitabella4Service;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcItabella4> ccitabella4List = ccitabella4Service.findAll(null, null);
    // ModelMap model = new ModelMap(ccitabella4List);
    // boolean export = createJMesaExport(request, response, ccitabella4List);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccitabella4List", ccitabella4List);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcItabella4 ccitabella4 = new CcItabella4();
    // ccitabella4.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccitabella4);
    // model.addAttribute("ccitabella4", ccitabella4);
    // setPageAttributes(model);
    // return "ccitabella4/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccitabella4") CcItabella4 ccitabella4, BindingResult result, SessionStatus
    // status) {
    //
    // fixMergeEntityProperty(ccitabella4);
    // ccitabella4.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccitabella4Service.insert(ccitabella4);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccitabella4Service.getValidationMessages(), result, ccitabella4, e.getMessage());
    // fixRenderEntityProperty(ccitabella4);
    // return "ccitabella4/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccitabella4.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcItabella4 ccitabella4 = ccitabella4Service.findById(id);
    // fixRenderEntityProperty(ccitabella4);
    // model.addAttribute("ccitabella4", ccitabella4);
    // setPageAttributes(model);
    // return "ccitabella4/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccitabella4") CcItabella4 ccitabella4, BindingResult result, SessionStatus
    // status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccitabella4);
    // try {
    // ccitabella4Service.update(ccitabella4);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccitabella4Service.getValidationMessages(), result, ccitabella4, e.getMessage());
    // fixRenderEntityProperty(ccitabella4);
    // return "ccitabella4/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccitabella4.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccitabella4") CcItabella4 ccitabella4, BindingResult result, SessionStatus
    // status) {
    //
    // CcItabella4 objToDelete = ccitabella4Service.findById(ccitabella4.getId());
    // try {
    // ccitabella4Service.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccitabella4Service.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(ccitabella4);
    // return "ccitabella4/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(CcItabella4 entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcItabella4 entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
