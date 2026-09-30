package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Chiusureistanza;
import it.gruppoinit.pal.gp.core.service.ChiusureistanzaService;
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
@SessionAttributes("chiusureistanza")
public class ChiusureistanzaController extends BaseController<Chiusureistanza> {

    @Autowired
    private ChiusureistanzaService chiusureistanzaService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Chiusureistanza> chiusureistanzaList = chiusureistanzaService.findAll(null, null);
    // ModelMap model = new ModelMap(chiusureistanzaList);
    // boolean export = createJMesaExport(request, response, chiusureistanzaList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("chiusureistanzaList", chiusureistanzaList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Chiusureistanza chiusureistanza = new Chiusureistanza();
    // chiusureistanza.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(chiusureistanza);
    // model.addAttribute("chiusureistanza", chiusureistanza);
    // setPageAttributes(model);
    // return "chiusureistanza/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("chiusureistanza") Chiusureistanza chiusureistanza, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(chiusureistanza);
    // chiusureistanza.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // chiusureistanzaService.insert(chiusureistanza);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(chiusureistanzaService.getValidationMessages(), result, chiusureistanza,
    // e.getMessage());
    // fixRenderEntityProperty(chiusureistanza);
    // return "chiusureistanza/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + chiusureistanza.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Chiusureistanza chiusureistanza = chiusureistanzaService.findById(id);
    // fixRenderEntityProperty(chiusureistanza);
    // model.addAttribute("chiusureistanza", chiusureistanza);
    // setPageAttributes(model);
    // return "chiusureistanza/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("chiusureistanza") Chiusureistanza chiusureistanza, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(chiusureistanza);
    // try {
    // chiusureistanzaService.update(chiusureistanza);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(chiusureistanzaService.getValidationMessages(), result, chiusureistanza,
    // e.getMessage());
    // fixRenderEntityProperty(chiusureistanza);
    // return "chiusureistanza/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + chiusureistanza.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("chiusureistanza") Chiusureistanza chiusureistanza, BindingResult result,
    // SessionStatus status) {
    //
    // Chiusureistanza objToDelete = chiusureistanzaService.findById(chiusureistanza.getId());
    // try {
    // chiusureistanzaService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(chiusureistanzaService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(chiusureistanza);
    // return "chiusureistanza/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Chiusureistanza entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Chiusureistanza entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
