package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcTabellaCaratterist;
import it.gruppoinit.pal.gp.core.service.CcTabellaCaratteristService;
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
@SessionAttributes("cctabellacaratterist")
public class CcTabellaCaratteristController extends BaseController<CcTabellaCaratterist> {

    @Autowired
    private CcTabellaCaratteristService cctabellacaratteristService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcTabellaCaratterist> cctabellacaratteristList = cctabellacaratteristService.findAll(null, null);
    // ModelMap model = new ModelMap(cctabellacaratteristList);
    // boolean export = createJMesaExport(request, response, cctabellacaratteristList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("cctabellacaratteristList", cctabellacaratteristList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcTabellaCaratterist cctabellacaratterist = new CcTabellaCaratterist();
    // cctabellacaratterist.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(cctabellacaratterist);
    // model.addAttribute("cctabellacaratterist", cctabellacaratterist);
    // setPageAttributes(model);
    // return "cctabellacaratterist/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("cctabellacaratterist") CcTabellaCaratterist cctabellacaratterist,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(cctabellacaratterist);
    // cctabellacaratterist.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // cctabellacaratteristService.insert(cctabellacaratterist);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cctabellacaratteristService.getValidationMessages(), result, cctabellacaratterist,
    // e.getMessage());
    // fixRenderEntityProperty(cctabellacaratterist);
    // return "cctabellacaratterist/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cctabellacaratterist.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcTabellaCaratterist cctabellacaratterist = cctabellacaratteristService.findById(id);
    // fixRenderEntityProperty(cctabellacaratterist);
    // model.addAttribute("cctabellacaratterist", cctabellacaratterist);
    // setPageAttributes(model);
    // return "cctabellacaratterist/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("cctabellacaratterist") CcTabellaCaratterist cctabellacaratterist,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(cctabellacaratterist);
    // try {
    // cctabellacaratteristService.update(cctabellacaratterist);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cctabellacaratteristService.getValidationMessages(), result, cctabellacaratterist,
    // e.getMessage());
    // fixRenderEntityProperty(cctabellacaratterist);
    // return "cctabellacaratterist/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cctabellacaratterist.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("cctabellacaratterist") CcTabellaCaratterist cctabellacaratterist,
    // BindingResult result, SessionStatus status) {
    //
    // CcTabellaCaratterist objToDelete = cctabellacaratteristService.findById(cctabellacaratterist.getId());
    // try {
    // cctabellacaratteristService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cctabellacaratteristService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(cctabellacaratterist);
    // return "cctabellacaratterist/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(CcTabellaCaratterist entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcTabellaCaratterist entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
