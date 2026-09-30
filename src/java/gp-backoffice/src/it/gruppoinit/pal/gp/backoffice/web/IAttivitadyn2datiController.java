package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2dati;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellit;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciHelper;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2datiService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2modellitService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.helper.SchedeDinamicheTL;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("iattivitadyn2dati")
public class IAttivitadyn2datiController extends BaseController<IAttivitadyn2dati> {

    @Autowired
    private IAttivitadyn2datiService iattivitadyn2datiService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private IAttivitadyn2modellitService iAttivitadyn2modellitService;
    @Autowired
    private IAttivitaService iAttivitaService;

    @RequestMapping
    public String ajaxViewModelli(Model model, @RequestParam("codiceAttivita") Integer codiceAttivita,
	    @RequestParam(required = false, value = "codiceModello") Integer codiceModello, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	List<IAttivitadyn2modellit> list = iAttivitadyn2modellitService.findByAttivita(codiceAttivita, null, null);
	if (!list.isEmpty()) {
	    if (codiceModello == null) {
		model.addAttribute("codiceModello", list.get(0).getDyn2Modellit().getId().getCodice());
		codiceModello = list.get(0).getDyn2Modellit().getId().getCodice();
	    }
	    model.addAttribute("codiceModello", codiceModello);
	    ModellidinamiciHelper helper = dyn2ModellitService.populateModellodinamicoForAttivita(codiceModello, codiceAttivita);
	    SchedeDinamicheTL.setRenderForPrint(true);
	    String html = "";
	    try {
		html = this.dyn2ModellitService.render(helper);
	    } finally {
		SchedeDinamicheTL.setRenderForPrint(false);
	    }
	    model.addAttribute("listaModelliAttivati", list);
	    model.addAttribute("html", html);
	}
	return "iattivitadyn2dati/listModelli";
    }

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<IAttivitadyn2dati> iattivitadyn2datiList = iattivitadyn2datiService.findAll(null, null);
    // ModelMap model = new ModelMap(iattivitadyn2datiList);
    // boolean export = createJMesaExport(request, response, iattivitadyn2datiList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("iattivitadyn2datiList", iattivitadyn2datiList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // IAttivitadyn2dati iattivitadyn2dati = new IAttivitadyn2dati();
    // iattivitadyn2dati.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(iattivitadyn2dati);
    // model.addAttribute("iattivitadyn2dati", iattivitadyn2dati);
    // setPageAttributes(model);
    // return "iattivitadyn2dati/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("iattivitadyn2dati") IAttivitadyn2dati iattivitadyn2dati, BindingResult
    // result, SessionStatus status) {
    //
    // fixMergeEntityProperty(iattivitadyn2dati);
    // iattivitadyn2dati.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // iattivitadyn2datiService.insert(iattivitadyn2dati);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(iattivitadyn2datiService.getValidationMessages(), result, iattivitadyn2dati,
    // e.getMessage());
    // fixRenderEntityProperty(iattivitadyn2dati);
    // return "iattivitadyn2dati/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + iattivitadyn2dati.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // IAttivitadyn2dati iattivitadyn2dati = iattivitadyn2datiService.findById(id);
    // fixRenderEntityProperty(iattivitadyn2dati);
    // model.addAttribute("iattivitadyn2dati", iattivitadyn2dati);
    // setPageAttributes(model);
    // return "iattivitadyn2dati/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("iattivitadyn2dati") IAttivitadyn2dati iattivitadyn2dati, BindingResult
    // result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(iattivitadyn2dati);
    // try {
    // iattivitadyn2datiService.update(iattivitadyn2dati);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(iattivitadyn2datiService.getValidationMessages(), result, iattivitadyn2dati,
    // e.getMessage());
    // fixRenderEntityProperty(iattivitadyn2dati);
    // return "iattivitadyn2dati/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + iattivitadyn2dati.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("iattivitadyn2dati") IAttivitadyn2dati iattivitadyn2dati, BindingResult
    // result, SessionStatus status) {
    //
    // IAttivitadyn2dati objToDelete = iattivitadyn2datiService.findById(iattivitadyn2dati.getId());
    // try {
    // iattivitadyn2datiService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(iattivitadyn2datiService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(iattivitadyn2dati);
    // return "iattivitadyn2dati/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(IAttivitadyn2dati entity) {

    }

    @Override
    protected void fixRenderEntityProperty(IAttivitadyn2dati entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
