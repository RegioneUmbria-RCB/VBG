package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcDetermtipocalcolo;
import it.gruppoinit.pal.gp.core.service.CcDetermtipocalcoloService;
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
@SessionAttributes("ccdetermtipocalcolo")
public class CcDetermtipocalcoloController extends BaseController<CcDetermtipocalcolo> {

    @Autowired
    private CcDetermtipocalcoloService ccdetermtipocalcoloService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcDetermtipocalcolo> ccdetermtipocalcoloList = ccdetermtipocalcoloService.findAll(null, null);
    // ModelMap model = new ModelMap(ccdetermtipocalcoloList);
    // boolean export = createJMesaExport(request, response, ccdetermtipocalcoloList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccdetermtipocalcoloList", ccdetermtipocalcoloList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcDetermtipocalcolo ccdetermtipocalcolo = new CcDetermtipocalcolo();
    // ccdetermtipocalcolo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccdetermtipocalcolo);
    // model.addAttribute("ccdetermtipocalcolo", ccdetermtipocalcolo);
    // setPageAttributes(model);
    // return "ccdetermtipocalcolo/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccdetermtipocalcolo") CcDetermtipocalcolo ccdetermtipocalcolo,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(ccdetermtipocalcolo);
    // ccdetermtipocalcolo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccdetermtipocalcoloService.insert(ccdetermtipocalcolo);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccdetermtipocalcoloService.getValidationMessages(), result, ccdetermtipocalcolo,
    // e.getMessage());
    // fixRenderEntityProperty(ccdetermtipocalcolo);
    // return "ccdetermtipocalcolo/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccdetermtipocalcolo.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcDetermtipocalcolo ccdetermtipocalcolo = ccdetermtipocalcoloService.findById(id);
    // fixRenderEntityProperty(ccdetermtipocalcolo);
    // model.addAttribute("ccdetermtipocalcolo", ccdetermtipocalcolo);
    // setPageAttributes(model);
    // return "ccdetermtipocalcolo/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccdetermtipocalcolo") CcDetermtipocalcolo ccdetermtipocalcolo,
    // BindingResult result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccdetermtipocalcolo);
    // try {
    // ccdetermtipocalcoloService.update(ccdetermtipocalcolo);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccdetermtipocalcoloService.getValidationMessages(), result, ccdetermtipocalcolo,
    // e.getMessage());
    // fixRenderEntityProperty(ccdetermtipocalcolo);
    // return "ccdetermtipocalcolo/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccdetermtipocalcolo.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccdetermtipocalcolo") CcDetermtipocalcolo ccdetermtipocalcolo,
    // BindingResult result, SessionStatus status) {
    //
    // CcDetermtipocalcolo objToDelete = ccdetermtipocalcoloService.findById(ccdetermtipocalcolo.getId());
    // try {
    // ccdetermtipocalcoloService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccdetermtipocalcoloService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(ccdetermtipocalcolo);
    // return "ccdetermtipocalcolo/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcDetermtipocalcolo entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcDetermtipocalcolo entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
