package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcItabella1;
import it.gruppoinit.pal.gp.core.service.CcItabella1Service;
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
@SessionAttributes("ccitabella1")
public class CcItabella1Controller extends BaseController<CcItabella1> {

    @Autowired
    private CcItabella1Service ccitabella1Service;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcItabella1> ccitabella1List = ccitabella1Service.findAll(null, null);
    // ModelMap model = new ModelMap(ccitabella1List);
    // boolean export = createJMesaExport(request, response, ccitabella1List);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccitabella1List", ccitabella1List);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcItabella1 ccitabella1 = new CcItabella1();
    // ccitabella1.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccitabella1);
    // model.addAttribute("ccitabella1", ccitabella1);
    // setPageAttributes(model);
    // return "ccitabella1/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccitabella1") CcItabella1 ccitabella1, BindingResult result, SessionStatus
    // status) {
    //
    // fixMergeEntityProperty(ccitabella1);
    // ccitabella1.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccitabella1Service.insert(ccitabella1);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccitabella1Service.getValidationMessages(), result, ccitabella1, e.getMessage());
    // fixRenderEntityProperty(ccitabella1);
    // return "ccitabella1/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccitabella1.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcItabella1 ccitabella1 = ccitabella1Service.findById(id);
    // fixRenderEntityProperty(ccitabella1);
    // model.addAttribute("ccitabella1", ccitabella1);
    // setPageAttributes(model);
    // return "ccitabella1/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccitabella1") CcItabella1 ccitabella1, BindingResult result, SessionStatus
    // status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccitabella1);
    // try {
    // ccitabella1Service.update(ccitabella1);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccitabella1Service.getValidationMessages(), result, ccitabella1, e.getMessage());
    // fixRenderEntityProperty(ccitabella1);
    // return "ccitabella1/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccitabella1.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccitabella1") CcItabella1 ccitabella1, BindingResult result, SessionStatus
    // status) {
    //
    // CcItabella1 objToDelete = ccitabella1Service.findById(ccitabella1.getId());
    // try {
    // ccitabella1Service.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccitabella1Service.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(ccitabella1);
    // return "ccitabella1/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(CcItabella1 entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcItabella1 entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
