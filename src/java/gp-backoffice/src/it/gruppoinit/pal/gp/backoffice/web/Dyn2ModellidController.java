package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Dyn2Basetipitesto;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellidtesti;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2BasetipitestoService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellidService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
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

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("dyn2modellid")
public class Dyn2ModellidController extends BaseController<Dyn2Modellid> {

    @Autowired
    private Dyn2ModellidService dyn2modellidService;
    @Autowired
    private Dyn2ModellitService dyModellitService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private Dyn2BasetipitestoService dyn2BasetipitestoService;

    @RequestMapping
    public ModelMap list(@RequestParam("codiceModelloT") Integer codiceModelloT, HttpServletRequest request, HttpServletResponse response) {

	Dyn2Modellit dyn2Modellit = dyModellitService.findById(new PkId(codiceModelloT));
	List<Dyn2Modellid> dyn2modellidList = dyn2modellidService.findByModelloT(dyn2Modellit);
	ModelMap model = new ModelMap(dyn2modellidList);
	boolean export = createJMesaExport(request, response, dyn2modellidList);
	if (export) {
	    return null;
	}
	model.addAttribute("dyn2modellidList", dyn2modellidList);
	model.addAttribute("dyn2Modellit", dyn2Modellit);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codiceModelloT") Integer codiceModelloT, Model model, HttpServletRequest request) {

	Dyn2Modellit dyn2Modellit = dyModellitService.findById(new PkId(codiceModelloT));
	Dyn2Modellid dyn2modellid = new Dyn2Modellid();
	dyn2modellid.setDyn2Modellit(dyn2Modellit);
	// di default alla crezione del modello d propone un tipo campo dinamico
	dyn2modellid.setTipocampoTransient(WebConstants.CAMPO_DINAMICO);
	fixRenderEntityProperty(dyn2modellid);
	List<Dyn2Basetipitesto> basetipitestos = dyn2BasetipitestoService.findAll(null, null);
	model.addAttribute("basetipitestos", basetipitestos);
	model.addAttribute("dyn2modellid", dyn2modellid);
	prepareview(dyn2modellid, request, model);
	setPageAttributes(model);
	return "dyn2modellid/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("dyn2modellid") Dyn2Modellid dyn2modellid, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(dyn2modellid);
	if (dyn2modellid.getTipocampoTransient().equals(WebConstants.CAMPO_DINAMICO)) {
	    if (EntityUtils.getNestedProperty(dyn2modellid.getDyn2Campi(), "id.codice") != null) {
		Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(dyn2modellid.getDyn2Campi().getId().getCodice()));
		dyn2modellid.setDyn2Campi(dyn2Campi);
	    }
	}
	if (dyn2modellid.getTipocampoTransient().equals(WebConstants.CAMPO_TESTO)) {
	    if (dyn2modellid.getDyn2Modellidtesti().getDyn2Basetipitesto() != null
		    && StringUtils.isNotBlank(dyn2modellid.getDyn2Modellidtesti().getDyn2Basetipitesto().getId())) {
		Dyn2Basetipitesto dyn2Basetipitesto = dyn2BasetipitestoService.findById(dyn2modellid.getDyn2Modellidtesti().getDyn2Basetipitesto()
			.getId());
		dyn2modellid.getDyn2Modellidtesti().setDyn2Basetipitesto(dyn2Basetipitesto);
	    }
	}
	try {
	    dyn2modellidService.insert(dyn2modellid);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, dyn2modellid, e);
	    fixRenderEntityProperty(dyn2modellid);
	    List<Dyn2Basetipitesto> basetipitestos = dyn2BasetipitestoService.findAll(null, null);
	    model.addAttribute("basetipitestos", basetipitestos);
	    prepareview(dyn2modellid, request, model);
	    return "dyn2modellid/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + dyn2modellid.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Dyn2Modellid dyn2modellid = dyn2modellidService.findById(id);
	fixRenderEntityProperty(dyn2modellid);
	List<Dyn2Basetipitesto> basetipitestos = dyn2BasetipitestoService.findAll(null, null);
	model.addAttribute("basetipitestos", basetipitestos);
	model.addAttribute("dyn2modellid", dyn2modellid);
	prepareview(dyn2modellid, request, model);
	setPageAttributes(model);
	return "dyn2modellid/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("dyn2modellid") Dyn2Modellid dyn2modellid, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(dyn2modellid);
	try {
	    dyn2modellidService.update(dyn2modellid);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, dyn2modellid, e);
	    fixRenderEntityProperty(dyn2modellid);
	    List<Dyn2Basetipitesto> basetipitestos = dyn2BasetipitestoService.findAll(null, null);
	    model.addAttribute("basetipitestos", basetipitestos);
	    prepareview(dyn2modellid, request, model);
	    return "dyn2modellid/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + dyn2modellid.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("dyn2modellid") Dyn2Modellid dyn2modellid, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Dyn2Modellid objToDelete = dyn2modellidService.findById(dyn2modellid.getId());
	try {
	    dyn2modellidService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, dyn2modellid, e);
	    fixRenderEntityProperty(dyn2modellid);
	    List<Dyn2Basetipitesto> basetipitestos = dyn2BasetipitestoService.findAll(null, null);
	    model.addAttribute("basetipitestos", basetipitestos);
	    prepareview(dyn2modellid, request, model);
	    return "dyn2modellid/form";
	}
	status.setComplete();
	return "redirect:list.htm?codiceModelloT=" + objToDelete.getDyn2Modellit().getId().getCodice();
    }

    @RequestMapping
    public String changeFlagMultiplo(@RequestParam("codice") Integer codice, HttpServletResponse response) throws Exception {

	Dyn2Modellid dyn2Modellid = dyn2modellidService.findById(new PkId(codice));
	if (BooleanUtils.isFalse(dyn2Modellid.getFlgMultiplo())) {
	    dyn2Modellid.setFlgMultiplo(Boolean.TRUE);
	} else {
	    dyn2Modellid.setFlgMultiplo(Boolean.FALSE);
	}
	dyn2modellidService.updateFlagMultiplo(dyn2Modellid);
	return "redirect:list.htm?codiceModelloT=" + dyn2Modellid.getDyn2Modellit().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(Dyn2Modellid entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Dyn2Modellid entity) {

	if (entity.getDyn2Campi() == null) {
	    entity.setDyn2Campi(new Dyn2Campi());
	}
	if (entity.getDyn2Modellidtesti() == null) {
	    entity.setDyn2Modellidtesti(new Dyn2Modellidtesti());
	}
	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
	if (entity.getDyn2RegoleAttivo() == null) {
	    entity.setDyn2RegoleAttivo(new Dyn2Regole());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    private void prepareview(Dyn2Modellid dyn2modellid, HttpServletRequest request, Model model) {

	// se modelli d ha codice NULL (fase di inserimento) può avere due comportamenti: 
	//1- campo transiet (TipocampoTransient) uguale a WebConstants.CAMPO_DINAMICO allora situazione di campo di dinamico
	//2- campo transiet (TipocampoTransient) diverso da WebConstants.CAMPO_DINAMICO (sarà sicuramete WebConstants.CAMPO_TESTO) allora situazione 
	// di campo di testo
	if (EntityUtils.getNestedProperty(dyn2modellid, "id.codice") == null) {
	    if (dyn2modellid.getTipocampoTransient().equals(WebConstants.CAMPO_DINAMICO)) {
		request.setAttribute("tipocampo", WebConstants.CAMPO_DINAMICO);
		dyn2modellid.setTipocampoTransient(WebConstants.CAMPO_DINAMICO);
	    } else {
		request.setAttribute("tipocampo", WebConstants.CAMPO_TESTO);
	    }
	    // se modelli d ha codice diverso NULL (fase di modifica) può avere due comportamenti: 
	    //1- campo transiet (TipocampoTransient) uguale a WebConstants.CAMPO_DINAMICO allora situazione di campo di dinamico
	    //2- campo transiet (TipocampoTransient) ugualeWebConstants.CAMPO_TESTO allora situazione  di campo di testo
	} else {
	    if (EntityUtils.getNestedProperty(dyn2modellid.getDyn2Campi(), "id.codice") != null
		    || (StringUtils.isNotBlank(dyn2modellid.getTipocampoTransient()) && dyn2modellid.getTipocampoTransient().equals(
			    WebConstants.CAMPO_DINAMICO))) {
		request.setAttribute("tipocampo", WebConstants.CAMPO_DINAMICO);
		dyn2modellid.setTipocampoTransient(WebConstants.CAMPO_DINAMICO);
	    } else {
		request.setAttribute("tipocampo", WebConstants.CAMPO_TESTO);
		dyn2modellid.setTipocampoTransient(WebConstants.CAMPO_TESTO);
	    }
	}
    }
}
