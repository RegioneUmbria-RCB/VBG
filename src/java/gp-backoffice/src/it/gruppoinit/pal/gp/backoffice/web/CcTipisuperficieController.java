package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcTipisuperficie;
import it.gruppoinit.pal.gp.core.service.CcTipisuperficieService;
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
@SessionAttributes("cctipisuperficie")
public class CcTipisuperficieController extends BaseController<CcTipisuperficie> {

    @Autowired
    private CcTipisuperficieService cctipisuperficieService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcTipisuperficie> cctipisuperficieList = cctipisuperficieService.findAll(null, null);
    // ModelMap model = new ModelMap(cctipisuperficieList);
    // boolean export = createJMesaExport(request, response, cctipisuperficieList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("cctipisuperficieList", cctipisuperficieList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcTipisuperficie cctipisuperficie = new CcTipisuperficie();
    // cctipisuperficie.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(cctipisuperficie);
    // model.addAttribute("cctipisuperficie", cctipisuperficie);
    // setPageAttributes(model);
    // return "cctipisuperficie/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("cctipisuperficie") CcTipisuperficie cctipisuperficie, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(cctipisuperficie);
    // cctipisuperficie.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // cctipisuperficieService.insert(cctipisuperficie);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cctipisuperficieService.getValidationMessages(), result, cctipisuperficie,
    // e.getMessage());
    // fixRenderEntityProperty(cctipisuperficie);
    // return "cctipisuperficie/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cctipisuperficie.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcTipisuperficie cctipisuperficie = cctipisuperficieService.findById(id);
    // fixRenderEntityProperty(cctipisuperficie);
    // model.addAttribute("cctipisuperficie", cctipisuperficie);
    // setPageAttributes(model);
    // return "cctipisuperficie/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("cctipisuperficie") CcTipisuperficie cctipisuperficie, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(cctipisuperficie);
    // try {
    // cctipisuperficieService.update(cctipisuperficie);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cctipisuperficieService.getValidationMessages(), result, cctipisuperficie,
    // e.getMessage());
    // fixRenderEntityProperty(cctipisuperficie);
    // return "cctipisuperficie/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cctipisuperficie.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("cctipisuperficie") CcTipisuperficie cctipisuperficie, BindingResult result,
    // SessionStatus status) {
    //
    // CcTipisuperficie objToDelete = cctipisuperficieService.findById(cctipisuperficie.getId());
    // try {
    // cctipisuperficieService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cctipisuperficieService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(cctipisuperficie);
    // return "cctipisuperficie/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(CcTipisuperficie entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcTipisuperficie entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
