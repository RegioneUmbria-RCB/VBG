package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcTabella3;
import it.gruppoinit.pal.gp.core.service.CcTabella3Service;
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
@SessionAttributes("cctabella3")
public class CcTabella3Controller extends BaseController<CcTabella3> {

    @Autowired
    private CcTabella3Service cctabella3Service;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcTabella3> cctabella3List = cctabella3Service.findAll(null, null);
    // ModelMap model = new ModelMap(cctabella3List);
    // boolean export = createJMesaExport(request, response, cctabella3List);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("cctabella3List", cctabella3List);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcTabella3 cctabella3 = new CcTabella3();
    // cctabella3.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(cctabella3);
    // model.addAttribute("cctabella3", cctabella3);
    // setPageAttributes(model);
    // return "cctabella3/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("cctabella3") CcTabella3 cctabella3, BindingResult result, SessionStatus
    // status) {
    //
    // fixMergeEntityProperty(cctabella3);
    // cctabella3.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // cctabella3Service.insert(cctabella3);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cctabella3Service.getValidationMessages(), result, cctabella3, e.getMessage());
    // fixRenderEntityProperty(cctabella3);
    // return "cctabella3/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cctabella3.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcTabella3 cctabella3 = cctabella3Service.findById(id);
    // fixRenderEntityProperty(cctabella3);
    // model.addAttribute("cctabella3", cctabella3);
    // setPageAttributes(model);
    // return "cctabella3/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("cctabella3") CcTabella3 cctabella3, BindingResult result, SessionStatus
    // status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(cctabella3);
    // try {
    // cctabella3Service.update(cctabella3);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cctabella3Service.getValidationMessages(), result, cctabella3, e.getMessage());
    // fixRenderEntityProperty(cctabella3);
    // return "cctabella3/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cctabella3.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("cctabella3") CcTabella3 cctabella3, BindingResult result, SessionStatus
    // status) {
    //
    // CcTabella3 objToDelete = cctabella3Service.findById(cctabella3.getId());
    // try {
    // cctabella3Service.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cctabella3Service.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(cctabella3);
    // return "cctabella3/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcTabella3 entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcTabella3 entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
