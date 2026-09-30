package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.DomandefrontEndo;
import it.gruppoinit.pal.gp.core.service.DomandefrontEndoService;
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
@SessionAttributes("domandefrontendo")
public class DomandefrontEndoController extends BaseController<DomandefrontEndo> {

    @Autowired
    private DomandefrontEndoService domandefrontendoService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<DomandefrontEndo> domandefrontendoList = domandefrontendoService.findAll(null, null);
    // ModelMap model = new ModelMap(domandefrontendoList);
    // boolean export = createJMesaExport(request, response, domandefrontendoList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("domandefrontendoList", domandefrontendoList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // DomandefrontEndo domandefrontendo = new DomandefrontEndo();
    // domandefrontendo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(domandefrontendo);
    // model.addAttribute("domandefrontendo", domandefrontendo);
    // setPageAttributes(model);
    // return "domandefrontendo/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("domandefrontendo") DomandefrontEndo domandefrontendo, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(domandefrontendo);
    // domandefrontendo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // domandefrontendoService.insert(domandefrontendo);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(domandefrontendoService.getValidationMessages(), result, domandefrontendo,
    // e.getMessage());
    // fixRenderEntityProperty(domandefrontendo);
    // return "domandefrontendo/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + domandefrontendo.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // DomandefrontEndo domandefrontendo = domandefrontendoService.findById(id);
    // fixRenderEntityProperty(domandefrontendo);
    // model.addAttribute("domandefrontendo", domandefrontendo);
    // setPageAttributes(model);
    // return "domandefrontendo/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("domandefrontendo") DomandefrontEndo domandefrontendo, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(domandefrontendo);
    // try {
    // domandefrontendoService.update(domandefrontendo);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(domandefrontendoService.getValidationMessages(), result, domandefrontendo,
    // e.getMessage());
    // fixRenderEntityProperty(domandefrontendo);
    // return "domandefrontendo/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + domandefrontendo.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("domandefrontendo") DomandefrontEndo domandefrontendo, BindingResult result,
    // SessionStatus status) {
    //
    // DomandefrontEndo objToDelete = domandefrontendoService.findById(domandefrontendo.getId());
    // try {
    // domandefrontendoService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(domandefrontendoService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(domandefrontendo);
    // return "domandefrontendo/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(DomandefrontEndo entity) {

    }

    @Override
    protected void fixRenderEntityProperty(DomandefrontEndo entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
