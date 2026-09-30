package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OTabellaabc;
import it.gruppoinit.pal.gp.core.service.OTabellaabcService;
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
@SessionAttributes("otabellaabc")
public class OTabellaabcController extends BaseController<OTabellaabc> {

    @Autowired
    private OTabellaabcService otabellaabcService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OTabellaabc> otabellaabcList = otabellaabcService.findAll(null, null);
    // ModelMap model = new ModelMap(otabellaabcList);
    // boolean export = createJMesaExport(request, response, otabellaabcList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("otabellaabcList", otabellaabcList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OTabellaabc otabellaabc = new OTabellaabc();
    // otabellaabc.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(otabellaabc);
    // model.addAttribute("otabellaabc", otabellaabc);
    // setPageAttributes(model);
    // return "otabellaabc/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("otabellaabc") OTabellaabc otabellaabc, BindingResult result, SessionStatus
    // status) {
    //
    // fixMergeEntityProperty(otabellaabc);
    // otabellaabc.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // otabellaabcService.insert(otabellaabc);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(otabellaabcService.getValidationMessages(), result, otabellaabc, e.getMessage());
    // fixRenderEntityProperty(otabellaabc);
    // return "otabellaabc/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + otabellaabc.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OTabellaabc otabellaabc = otabellaabcService.findById(id);
    // fixRenderEntityProperty(otabellaabc);
    // model.addAttribute("otabellaabc", otabellaabc);
    // setPageAttributes(model);
    // return "otabellaabc/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("otabellaabc") OTabellaabc otabellaabc, BindingResult result, SessionStatus
    // status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(otabellaabc);
    // try {
    // otabellaabcService.update(otabellaabc);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(otabellaabcService.getValidationMessages(), result, otabellaabc, e.getMessage());
    // fixRenderEntityProperty(otabellaabc);
    // return "otabellaabc/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + otabellaabc.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("otabellaabc") OTabellaabc otabellaabc, BindingResult result, SessionStatus
    // status) {
    //
    // OTabellaabc objToDelete = otabellaabcService.findById(otabellaabc.getId());
    // try {
    // otabellaabcService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(otabellaabcService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(otabellaabc);
    // return "otabellaabc/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(OTabellaabc entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OTabellaabc entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
