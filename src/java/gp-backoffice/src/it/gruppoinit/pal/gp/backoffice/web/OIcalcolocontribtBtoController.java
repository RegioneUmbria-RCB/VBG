package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribtBto;
import it.gruppoinit.pal.gp.core.service.OIcalcolocontribtBtoService;
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
@SessionAttributes("oicalcolocontribtbto")
public class OIcalcolocontribtBtoController extends BaseController<OIcalcolocontribtBto> {

    @Autowired
    private OIcalcolocontribtBtoService oicalcolocontribtbtoService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OIcalcolocontribtBto> oicalcolocontribtbtoList = oicalcolocontribtbtoService.findAll(null, null);
    // ModelMap model = new ModelMap(oicalcolocontribtbtoList);
    // boolean export = createJMesaExport(request, response, oicalcolocontribtbtoList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("oicalcolocontribtbtoList", oicalcolocontribtbtoList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OIcalcolocontribtBto oicalcolocontribtbto = new OIcalcolocontribtBto();
    // oicalcolocontribtbto.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(oicalcolocontribtbto);
    // model.addAttribute("oicalcolocontribtbto", oicalcolocontribtbto);
    // setPageAttributes(model);
    // return "oicalcolocontribtbto/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("oicalcolocontribtbto") OIcalcolocontribtBto oicalcolocontribtbto,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(oicalcolocontribtbto);
    // oicalcolocontribtbto.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // oicalcolocontribtbtoService.insert(oicalcolocontribtbto);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolocontribtbtoService.getValidationMessages(), result, oicalcolocontribtbto,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolocontribtbto);
    // return "oicalcolocontribtbto/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oicalcolocontribtbto.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OIcalcolocontribtBto oicalcolocontribtbto = oicalcolocontribtbtoService.findById(id);
    // fixRenderEntityProperty(oicalcolocontribtbto);
    // model.addAttribute("oicalcolocontribtbto", oicalcolocontribtbto);
    // setPageAttributes(model);
    // return "oicalcolocontribtbto/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("oicalcolocontribtbto") OIcalcolocontribtBto oicalcolocontribtbto,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(oicalcolocontribtbto);
    // try {
    // oicalcolocontribtbtoService.update(oicalcolocontribtbto);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolocontribtbtoService.getValidationMessages(), result, oicalcolocontribtbto,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolocontribtbto);
    // return "oicalcolocontribtbto/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oicalcolocontribtbto.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("oicalcolocontribtbto") OIcalcolocontribtBto oicalcolocontribtbto,
    // BindingResult result, SessionStatus status) {
    //
    // OIcalcolocontribtBto objToDelete = oicalcolocontribtbtoService.findById(oicalcolocontribtbto.getId());
    // try {
    // oicalcolocontribtbtoService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolocontribtbtoService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolocontribtbto);
    // return "oicalcolocontribtbto/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(OIcalcolocontribtBto entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OIcalcolocontribtBto entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
