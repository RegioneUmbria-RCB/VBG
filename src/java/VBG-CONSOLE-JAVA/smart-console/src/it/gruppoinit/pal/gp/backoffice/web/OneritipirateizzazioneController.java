package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.service.OneritipirateizzazioneService;
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
@SessionAttributes("oneritipirateizzazione")
public class OneritipirateizzazioneController extends BaseController<Oneritipirateizzazione> {

    @Autowired
    private OneritipirateizzazioneService oneritipirateizzazioneService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Oneritipirateizzazione> oneritipirateizzazioneList = oneritipirateizzazioneService.findAll(null, null);
    // ModelMap model = new ModelMap(oneritipirateizzazioneList);
    // boolean export = createJMesaExport(request, response, oneritipirateizzazioneList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("oneritipirateizzazioneList", oneritipirateizzazioneList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Oneritipirateizzazione oneritipirateizzazione = new Oneritipirateizzazione();
    // oneritipirateizzazione.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(oneritipirateizzazione);
    // model.addAttribute("oneritipirateizzazione", oneritipirateizzazione);
    // setPageAttributes(model);
    // return "oneritipirateizzazione/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("oneritipirateizzazione") Oneritipirateizzazione oneritipirateizzazione,
    // BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(oneritipirateizzazione);
    // oneritipirateizzazione.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // oneritipirateizzazioneService.insert(oneritipirateizzazione);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oneritipirateizzazioneService.getValidationMessages(), result, oneritipirateizzazione,
    // e.getMessage());
    // fixRenderEntityProperty(oneritipirateizzazione);
    // return "oneritipirateizzazione/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oneritipirateizzazione.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Oneritipirateizzazione oneritipirateizzazione = oneritipirateizzazioneService.findById(id);
    // fixRenderEntityProperty(oneritipirateizzazione);
    // model.addAttribute("oneritipirateizzazione", oneritipirateizzazione);
    // setPageAttributes(model);
    // return "oneritipirateizzazione/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("oneritipirateizzazione") Oneritipirateizzazione oneritipirateizzazione,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(oneritipirateizzazione);
    // try {
    // oneritipirateizzazioneService.update(oneritipirateizzazione);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oneritipirateizzazioneService.getValidationMessages(), result, oneritipirateizzazione,
    // e.getMessage());
    // fixRenderEntityProperty(oneritipirateizzazione);
    // return "oneritipirateizzazione/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + oneritipirateizzazione.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("oneritipirateizzazione") Oneritipirateizzazione oneritipirateizzazione,
    // BindingResult result,
    // SessionStatus status) {
    //
    // Oneritipirateizzazione objToDelete = oneritipirateizzazioneService.findById(oneritipirateizzazione.getId());
    // try {
    // oneritipirateizzazioneService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(oneritipirateizzazioneService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(oneritipirateizzazione);
    // return "oneritipirateizzazione/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Oneritipirateizzazione entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Oneritipirateizzazione entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
