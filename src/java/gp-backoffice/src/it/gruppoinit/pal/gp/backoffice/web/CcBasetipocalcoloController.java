package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcBasetipocalcolo;
import it.gruppoinit.pal.gp.core.service.CcBasetipocalcoloService;
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
@SessionAttributes("ccbasetipocalcolo")
public class CcBasetipocalcoloController extends BaseController<CcBasetipocalcolo> {

    @Autowired
    private CcBasetipocalcoloService ccbasetipocalcoloService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcBasetipocalcolo> ccbasetipocalcoloList = ccbasetipocalcoloService.findAll(null, null);
    // ModelMap model = new ModelMap(ccbasetipocalcoloList);
    // boolean export = createJMesaExport(request, response, ccbasetipocalcoloList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccbasetipocalcoloList", ccbasetipocalcoloList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcBasetipocalcolo ccbasetipocalcolo = new CcBasetipocalcolo();
    // ccbasetipocalcolo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccbasetipocalcolo);
    // model.addAttribute("ccbasetipocalcolo", ccbasetipocalcolo);
    // setPageAttributes(model);
    // return "ccbasetipocalcolo/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccbasetipocalcolo") CcBasetipocalcolo ccbasetipocalcolo, BindingResult
    // result, SessionStatus status) {
    //
    // fixMergeEntityProperty(ccbasetipocalcolo);
    // ccbasetipocalcolo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccbasetipocalcoloService.insert(ccbasetipocalcolo);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccbasetipocalcoloService.getValidationMessages(), result, ccbasetipocalcolo,
    // e.getMessage());
    // fixRenderEntityProperty(ccbasetipocalcolo);
    // return "ccbasetipocalcolo/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccbasetipocalcolo.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcBasetipocalcolo ccbasetipocalcolo = ccbasetipocalcoloService.findById(id);
    // fixRenderEntityProperty(ccbasetipocalcolo);
    // model.addAttribute("ccbasetipocalcolo", ccbasetipocalcolo);
    // setPageAttributes(model);
    // return "ccbasetipocalcolo/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccbasetipocalcolo") CcBasetipocalcolo ccbasetipocalcolo, BindingResult
    // result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccbasetipocalcolo);
    // try {
    // ccbasetipocalcoloService.update(ccbasetipocalcolo);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccbasetipocalcoloService.getValidationMessages(), result, ccbasetipocalcolo,
    // e.getMessage());
    // fixRenderEntityProperty(ccbasetipocalcolo);
    // return "ccbasetipocalcolo/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccbasetipocalcolo.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccbasetipocalcolo") CcBasetipocalcolo ccbasetipocalcolo, BindingResult
    // result, SessionStatus status) {
    //
    // CcBasetipocalcolo objToDelete = ccbasetipocalcoloService.findById(ccbasetipocalcolo.getId());
    // try {
    // ccbasetipocalcoloService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccbasetipocalcoloService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(ccbasetipocalcolo);
    // return "ccbasetipocalcolo/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcBasetipocalcolo entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcBasetipocalcolo entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
