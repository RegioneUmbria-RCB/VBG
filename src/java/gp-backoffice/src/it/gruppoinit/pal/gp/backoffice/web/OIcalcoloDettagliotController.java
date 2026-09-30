package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.OIcalcoloDettagliot;
import it.gruppoinit.pal.gp.core.service.OIcalcoloDettagliotService;
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
@SessionAttributes("oicalcolodettagliot")
public class OIcalcoloDettagliotController extends BaseController<OIcalcoloDettagliot> {

    @Autowired
    private OIcalcoloDettagliotService oicalcolodettagliotService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<OIcalcoloDettagliot> oicalcolodettagliotList = oicalcolodettagliotService.findAll(null, null);
    // ModelMap model = new ModelMap(oicalcolodettagliotList);
    // boolean export = createJMesaExport(request, response, oicalcolodettagliotList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("oicalcolodettagliotList", oicalcolodettagliotList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // OIcalcoloDettagliot oicalcolodettagliot = new OIcalcoloDettagliot();
    // oicalcolodettagliot.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(oicalcolodettagliot);
    // model.addAttribute("oicalcolodettagliot", oicalcolodettagliot);
    // setPageAttributes(model);
    // return "oicalcolodettagliot/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("oicalcolodettagliot") OIcalcoloDettagliot oicalcolodettagliot,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(oicalcolodettagliot);
    // oicalcolodettagliot.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // oicalcolodettagliotService.insert(oicalcolodettagliot);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolodettagliotService.getValidationMessages(), result, oicalcolodettagliot,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolodettagliot);
    // return "oicalcolodettagliot/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oicalcolodettagliot.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // OIcalcoloDettagliot oicalcolodettagliot = oicalcolodettagliotService.findById(id);
    // fixRenderEntityProperty(oicalcolodettagliot);
    // model.addAttribute("oicalcolodettagliot", oicalcolodettagliot);
    // setPageAttributes(model);
    // return "oicalcolodettagliot/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("oicalcolodettagliot") OIcalcoloDettagliot oicalcolodettagliot,
    // BindingResult result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(oicalcolodettagliot);
    // try {
    // oicalcolodettagliotService.update(oicalcolodettagliot);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolodettagliotService.getValidationMessages(), result, oicalcolodettagliot,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolodettagliot);
    // return "oicalcolodettagliot/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oicalcolodettagliot.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("oicalcolodettagliot") OIcalcoloDettagliot oicalcolodettagliot,
    // BindingResult result, SessionStatus status) {
    //
    // OIcalcoloDettagliot objToDelete = oicalcolodettagliotService.findById(oicalcolodettagliot.getId());
    // try {
    // oicalcolodettagliotService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oicalcolodettagliotService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(oicalcolodettagliot);
    // return "oicalcolodettagliot/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(OIcalcoloDettagliot entity) {

    }

    @Override
    protected void fixRenderEntityProperty(OIcalcoloDettagliot entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
