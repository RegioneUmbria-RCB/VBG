package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OIcalcoloDettaglior;
import it.gruppoinit.pal.gp.core.service.OIcalcoloDettagliorService;
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
@SessionAttributes("oicalcolodettaglior")
public class OIcalcoloDettagliorController extends BaseController<OIcalcoloDettaglior> {

    @Autowired
    private OIcalcoloDettagliorService oicalcolodettagliorService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OIcalcoloDettaglior> oicalcolodettagliorList = oicalcolodettagliorService.findAll(null, null);
    // ModelMap model = new ModelMap(oicalcolodettagliorList);
    // boolean export = createJMesaExport(request, response, oicalcolodettagliorList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("oicalcolodettagliorList", oicalcolodettagliorList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OIcalcoloDettaglior oicalcolodettaglior = new OIcalcoloDettaglior();
    // oicalcolodettaglior.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(oicalcolodettaglior);
    // model.addAttribute("oicalcolodettaglior", oicalcolodettaglior);
    // setPageAttributes(model);
    // return "oicalcolodettaglior/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("oicalcolodettaglior") OIcalcoloDettaglior oicalcolodettaglior,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(oicalcolodettaglior);
    // oicalcolodettaglior.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // oicalcolodettagliorService.insert(oicalcolodettaglior);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolodettagliorService.getValidationMessages(), result, oicalcolodettaglior,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolodettaglior);
    // return "oicalcolodettaglior/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oicalcolodettaglior.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OIcalcoloDettaglior oicalcolodettaglior = oicalcolodettagliorService.findById(id);
    // fixRenderEntityProperty(oicalcolodettaglior);
    // model.addAttribute("oicalcolodettaglior", oicalcolodettaglior);
    // setPageAttributes(model);
    // return "oicalcolodettaglior/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("oicalcolodettaglior") OIcalcoloDettaglior oicalcolodettaglior,
    // BindingResult result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(oicalcolodettaglior);
    // try {
    // oicalcolodettagliorService.update(oicalcolodettaglior);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolodettagliorService.getValidationMessages(), result, oicalcolodettaglior,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolodettaglior);
    // return "oicalcolodettaglior/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oicalcolodettaglior.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("oicalcolodettaglior") OIcalcoloDettaglior oicalcolodettaglior,
    // BindingResult result, SessionStatus status) {
    //
    // OIcalcoloDettaglior objToDelete = oicalcolodettagliorService.findById(oicalcolodettaglior.getId());
    // try {
    // oicalcolodettagliorService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolodettagliorService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolodettaglior);
    // return "oicalcolodettaglior/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(OIcalcoloDettaglior entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OIcalcoloDettaglior entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
