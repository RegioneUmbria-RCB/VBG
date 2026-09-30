package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcItabella3;
import it.gruppoinit.pal.gp.core.service.CcItabella3Service;
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
@SessionAttributes("ccitabella3")
public class CcItabella3Controller extends BaseController<CcItabella3> {

    @Autowired
    private CcItabella3Service ccitabella3Service;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcItabella3> ccitabella3List = ccitabella3Service.findAll(null, null);
    // ModelMap model = new ModelMap(ccitabella3List);
    // boolean export = createJMesaExport(request, response, ccitabella3List);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccitabella3List", ccitabella3List);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcItabella3 ccitabella3 = new CcItabella3();
    // ccitabella3.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccitabella3);
    // model.addAttribute("ccitabella3", ccitabella3);
    // setPageAttributes(model);
    // return "ccitabella3/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccitabella3") CcItabella3 ccitabella3, BindingResult result, SessionStatus
    // status) {
    //
    // fixMergeEntityProperty(ccitabella3);
    // ccitabella3.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccitabella3Service.insert(ccitabella3);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccitabella3Service.getValidationMessages(), result, ccitabella3, e.getMessage());
    // fixRenderEntityProperty(ccitabella3);
    // return "ccitabella3/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccitabella3.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcItabella3 ccitabella3 = ccitabella3Service.findById(id);
    // fixRenderEntityProperty(ccitabella3);
    // model.addAttribute("ccitabella3", ccitabella3);
    // setPageAttributes(model);
    // return "ccitabella3/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccitabella3") CcItabella3 ccitabella3, BindingResult result, SessionStatus
    // status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccitabella3);
    // try {
    // ccitabella3Service.update(ccitabella3);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccitabella3Service.getValidationMessages(), result, ccitabella3, e.getMessage());
    // fixRenderEntityProperty(ccitabella3);
    // return "ccitabella3/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccitabella3.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccitabella3") CcItabella3 ccitabella3, BindingResult result, SessionStatus
    // status) {
    //
    // CcItabella3 objToDelete = ccitabella3Service.findById(ccitabella3.getId());
    // try {
    // ccitabella3Service.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccitabella3Service.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(ccitabella3);
    // return "ccitabella3/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(CcItabella3 entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcItabella3 entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
