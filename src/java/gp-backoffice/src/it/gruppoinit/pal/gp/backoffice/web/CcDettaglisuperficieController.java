package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcDettaglisuperficie;
import it.gruppoinit.pal.gp.core.service.CcDettaglisuperficieService;
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
@SessionAttributes("ccdettaglisuperficie")
public class CcDettaglisuperficieController extends BaseController<CcDettaglisuperficie> {

    @Autowired
    private CcDettaglisuperficieService ccdettaglisuperficieService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcDettaglisuperficie> ccdettaglisuperficieList = ccdettaglisuperficieService.findAll(null, null);
    // ModelMap model = new ModelMap(ccdettaglisuperficieList);
    // boolean export = createJMesaExport(request, response, ccdettaglisuperficieList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccdettaglisuperficieList", ccdettaglisuperficieList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcDettaglisuperficie ccdettaglisuperficie = new CcDettaglisuperficie();
    // ccdettaglisuperficie.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccdettaglisuperficie);
    // model.addAttribute("ccdettaglisuperficie", ccdettaglisuperficie);
    // setPageAttributes(model);
    // return "ccdettaglisuperficie/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccdettaglisuperficie") CcDettaglisuperficie ccdettaglisuperficie,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(ccdettaglisuperficie);
    // ccdettaglisuperficie.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccdettaglisuperficieService.insert(ccdettaglisuperficie);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccdettaglisuperficieService.getValidationMessages(), result, ccdettaglisuperficie,
    // e.getMessage());
    // fixRenderEntityProperty(ccdettaglisuperficie);
    // return "ccdettaglisuperficie/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccdettaglisuperficie.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcDettaglisuperficie ccdettaglisuperficie = ccdettaglisuperficieService.findById(id);
    // fixRenderEntityProperty(ccdettaglisuperficie);
    // model.addAttribute("ccdettaglisuperficie", ccdettaglisuperficie);
    // setPageAttributes(model);
    // return "ccdettaglisuperficie/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccdettaglisuperficie") CcDettaglisuperficie ccdettaglisuperficie,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccdettaglisuperficie);
    // try {
    // ccdettaglisuperficieService.update(ccdettaglisuperficie);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccdettaglisuperficieService.getValidationMessages(), result, ccdettaglisuperficie,
    // e.getMessage());
    // fixRenderEntityProperty(ccdettaglisuperficie);
    // return "ccdettaglisuperficie/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccdettaglisuperficie.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccdettaglisuperficie") CcDettaglisuperficie ccdettaglisuperficie,
    // BindingResult result, SessionStatus status) {
    //
    // CcDettaglisuperficie objToDelete = ccdettaglisuperficieService.findById(ccdettaglisuperficie.getId());
    // try {
    // ccdettaglisuperficieService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccdettaglisuperficieService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(ccdettaglisuperficie);
    // return "ccdettaglisuperficie/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcDettaglisuperficie entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcDettaglisuperficie entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
