package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("tipiendo")
public class TipiendoregController extends BaseController<Tipiendo> {

    @Autowired
    private TipiendoService tipiendoService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private TipifamiglieendoService tipifamiglieendoService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;

    @RequestMapping
    public ModelMap list(@RequestParam(required = false, value = "codiceTipofamigliaEndo") Integer codiceTipofamigliaEndo,
	    HttpServletRequest request, HttpServletResponse response) {

	Tipiendo tipiendo = new Tipiendo();
	tipiendo.getId().setIdcomune(ORMHelper.getIdcomunebase());
	//Imposto la famiglia endo se è stata passato il codice.
	Tipifamiglieendo tipifamiglieendo = tipifamiglieendoService.findById(new PkId(ORMHelper.getIdcomunebase(), codiceTipofamigliaEndo));
	tipiendo.setTipifamiglieendo(tipifamiglieendo);
	List<Tipiendo> tipiendoList = tipiendoService.findByTipiendo(tipiendo, null, null);
	ModelMap model = new ModelMap(tipiendoList);
	boolean export = createJMesaExport(request, response, tipiendoList);
	if (export)
	    return null;
	model.addAttribute("tipiendoList", tipiendoList);
	model.addAttribute("codicetipifamiglieendo", codiceTipofamigliaEndo);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("tipiendo") Tipiendo tipiendo, BindingResult result, SessionStatus status) {

	Tipiendo objToDelete = tipiendoService.findById(tipiendo.getId());
	try {
	    tipiendoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiendo, e);
	    fixRenderEntityProperty(tipiendo);
	    return "tipiendoreg/form";
	}
	status.setComplete();
	//return "redirect:list.htm";
	return getHistoryBack();
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipiendo") Tipiendo tipiendo, BindingResult result, SessionStatus status) {

	Software software = softwareService.findById(ORMHelper.getSoftware());
	tipiendo.setSoftware(software);
	// controllo se esitste e recupero l'oggetto inserito nella riceca ajax
	if (tipiendo.getTipifamiglieendo() != null && tipiendo.getTipifamiglieendo().getId().getCodice() != null) {
	    Tipifamiglieendo tipifamiglieendo = tipifamiglieendoService.findById(new PkId(ORMHelper.getIdcomunebase(), tipiendo.getTipifamiglieendo()
		    .getId().getCodice()));
	    tipiendo.setTipifamiglieendo(tipifamiglieendo);
	}
	fixMergeEntityProperty(tipiendo);
	try {
	    tipiendoService.insert(tipiendo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiendo, e);
	    fixRenderEntityProperty(tipiendo);
	    return "tipiendoreg/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiendo.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipiendo") Tipiendo tipiendo, BindingResult result, SessionStatus status, HttpServletRequest request) {

	// controllo se esitste e recupero l'oggetto inserito nella riceca ajax
	if (tipiendo.getTipifamiglieendo() != null && tipiendo.getTipifamiglieendo().getId().getCodice() != null) {
	    Tipifamiglieendo tipifamiglieendo = tipifamiglieendoService.findById(new PkId(ORMHelper.getIdcomunebase(), tipiendo.getTipifamiglieendo()
		    .getId().getCodice()));
	    tipiendo.setTipifamiglieendo(tipifamiglieendo);
	}
	fixMergeEntityProperty(tipiendo);
	try {
	    tipiendoService.update(tipiendo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiendo, e);
	    fixRenderEntityProperty(tipiendo);
	    return "tipiendoreg/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiendo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(@RequestParam(required = false, value = "codiceTipofamigliaEndo") Integer codiceTipofamigliaEndo, Model model) {

	Tipiendo tipiendo = new Tipiendo();
	tipiendo.getId().setIdcomune(ORMHelper.getIdcomunebase());
	//Imposta la famiglia endo se è stato passato il codice
	Tipifamiglieendo tipifamiglieendo = tipifamiglieendoService.findById(new PkId(ORMHelper.getIdcomunebase(), codiceTipofamigliaEndo));
	tipiendo.setTipifamiglieendo(tipifamiglieendo);
	fixRenderEntityProperty(tipiendo);
	model.addAttribute("tipiendo", tipiendo);
	setPageAttributes(model);
	return "tipiendoreg/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(ORMHelper.getIdcomunebase(), codice);
	Tipiendo tipiendo = tipiendoService.findById(id);
	fixRenderEntityProperty(tipiendo);
	model.addAttribute("tipiendo", tipiendo);
	setPageAttributes(model);
	return "tipiendoreg/form";
    }

    @RequestMapping
    public ModelMap listEndoprocedimenti(@RequestParam("codicecategoriaendo") Integer codicecategoriaendo, HttpServletRequest request,
	    HttpServletResponse response) {

	Tipiendo tipiendo = tipiendoService.findById(new PkId(ORMHelper.getIdcomunebase(), codicecategoriaendo));
	List<Inventarioprocedimenti> inventarioprocedimentiList = inventarioprocedimentiService.findByTipoendo(tipiendo);
	ModelMap model = new ModelMap(inventarioprocedimentiList);
	boolean export = createJMesaExport(request, response, inventarioprocedimentiList);
	if (export)
	    return null;
	model.addAttribute("tipiendo", tipiendo);
	model.addAttribute("codicecategoriaendo", tipiendo.getId().getCodice());
	model.addAttribute("inventarioprocedimentiList", inventarioprocedimentiList);
	return model;
    }

    @Override
    protected void fixMergeEntityProperty(Tipiendo entity) {

	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null || entity.getSoftware().getCodice().equals(" "))) {
	    entity.setSoftware(null);
	}
	if (entity.getTipifamiglieendo() != null && entity.getTipifamiglieendo().getId() != null
		&& entity.getTipifamiglieendo().getId().getCodice() == null) {
	    entity.setTipifamiglieendo(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Tipiendo entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	if (entity.getTipifamiglieendo() == null) {
	    entity.setTipifamiglieendo(new Tipifamiglieendo());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
