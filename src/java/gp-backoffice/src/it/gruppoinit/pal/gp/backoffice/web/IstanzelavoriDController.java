package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.IstanzelavoriD;
import it.gruppoinit.pal.gp.core.service.IstanzelavoriDService;
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
@SessionAttributes("istanzelavorid")
public class IstanzelavoriDController extends BaseController<IstanzelavoriD> {

    @Autowired
    private IstanzelavoriDService istanzelavoridService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<IstanzelavoriD> istanzelavoridList = istanzelavoridService.findAll(null, null);
    // ModelMap model = new ModelMap(istanzelavoridList);
    // boolean export = createJMesaExport(request, response, istanzelavoridList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("istanzelavoridList", istanzelavoridList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // IstanzelavoriD istanzelavorid = new IstanzelavoriD();
    // istanzelavorid.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(istanzelavorid);
    // model.addAttribute("istanzelavorid", istanzelavorid);
    // setPageAttributes(model);
    // return "istanzelavorid/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("istanzelavorid") IstanzelavoriD istanzelavorid, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(istanzelavorid);
    // istanzelavorid.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // istanzelavoridService.insert(istanzelavorid);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzelavoridService.getValidationMessages(), result, istanzelavorid, e.getMessage());
    // fixRenderEntityProperty(istanzelavorid);
    // return "istanzelavorid/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzelavorid.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // IstanzelavoriD istanzelavorid = istanzelavoridService.findById(id);
    // fixRenderEntityProperty(istanzelavorid);
    // model.addAttribute("istanzelavorid", istanzelavorid);
    // setPageAttributes(model);
    // return "istanzelavorid/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("istanzelavorid") IstanzelavoriD istanzelavorid, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(istanzelavorid);
    // try {
    // istanzelavoridService.update(istanzelavorid);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzelavoridService.getValidationMessages(), result, istanzelavorid, e.getMessage());
    // fixRenderEntityProperty(istanzelavorid);
    // return "istanzelavorid/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzelavorid.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("istanzelavorid") IstanzelavoriD istanzelavorid, BindingResult result,
    // SessionStatus status) {
    //
    // IstanzelavoriD objToDelete = istanzelavoridService.findById(istanzelavorid.getId());
    // try {
    // istanzelavoridService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzelavoridService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(istanzelavorid);
    // return "istanzelavorid/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(IstanzelavoriD entity) {

    }

    @Override
    protected void fixRenderEntityProperty(IstanzelavoriD entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
