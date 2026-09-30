package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniD;
import it.gruppoinit.pal.gp.core.features.oneri.calcolo.canoni.IstanzecalcolocanoniDService;
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
@SessionAttributes("istanzecalcolocanonid")
public class IstanzecalcolocanoniDController extends BaseController<IstanzecalcolocanoniD> {

    @Autowired
    private IstanzecalcolocanoniDService istanzecalcolocanonidService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<IstanzecalcolocanoniD> istanzecalcolocanonidList = istanzecalcolocanonidService.findAll(null, null);
    // ModelMap model = new ModelMap(istanzecalcolocanonidList);
    // boolean export = createJMesaExport(request, response, istanzecalcolocanonidList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("istanzecalcolocanonidList", istanzecalcolocanonidList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // IstanzecalcolocanoniD istanzecalcolocanonid = new IstanzecalcolocanoniD();
    // istanzecalcolocanonid.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(istanzecalcolocanonid);
    // model.addAttribute("istanzecalcolocanonid", istanzecalcolocanonid);
    // setPageAttributes(model);
    // return "istanzecalcolocanonid/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("istanzecalcolocanonid") IstanzecalcolocanoniD istanzecalcolocanonid,
    // BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(istanzecalcolocanonid);
    // istanzecalcolocanonid.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // istanzecalcolocanonidService.insert(istanzecalcolocanonid);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzecalcolocanonidService.getValidationMessages(), result, istanzecalcolocanonid,
    // e.getMessage());
    // fixRenderEntityProperty(istanzecalcolocanonid);
    // return "istanzecalcolocanonid/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzecalcolocanonid.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // IstanzecalcolocanoniD istanzecalcolocanonid = istanzecalcolocanonidService.findById(id);
    // fixRenderEntityProperty(istanzecalcolocanonid);
    // model.addAttribute("istanzecalcolocanonid", istanzecalcolocanonid);
    // setPageAttributes(model);
    // return "istanzecalcolocanonid/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("istanzecalcolocanonid") IstanzecalcolocanoniD istanzecalcolocanonid,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(istanzecalcolocanonid);
    // try {
    // istanzecalcolocanonidService.update(istanzecalcolocanonid);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzecalcolocanonidService.getValidationMessages(), result, istanzecalcolocanonid,
    // e.getMessage());
    // fixRenderEntityProperty(istanzecalcolocanonid);
    // return "istanzecalcolocanonid/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzecalcolocanonid.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("istanzecalcolocanonid") IstanzecalcolocanoniD istanzecalcolocanonid,
    // BindingResult result,
    // SessionStatus status) {
    //
    // IstanzecalcolocanoniD objToDelete = istanzecalcolocanonidService.findById(istanzecalcolocanonid.getId());
    // try {
    // istanzecalcolocanonidService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzecalcolocanonidService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(istanzecalcolocanonid);
    // return "istanzecalcolocanonid/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(IstanzecalcolocanoniD entity) {

    }

    @Override
    protected void fixRenderEntityProperty(IstanzecalcolocanoniD entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
