package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipigradtCfgRotazione;
import it.gruppoinit.pal.gp.core.domain.Tipigraduatoriet;
import it.gruppoinit.pal.gp.core.domain.web.TipigradtCfgRotazioneCommand;
import it.gruppoinit.pal.gp.core.service.TipigradtCfgRotazioneService;
import it.gruppoinit.pal.gp.core.service.TipigraduatorietService;

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

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("tipigradtcfgrotazione")
public class TipigradtCfgRotazioneController extends BaseController<TipigradtCfgRotazione> {

    @Autowired
    private TipigradtCfgRotazioneService tipigradtcfgrotazioneService;
    @Autowired
    private TipigraduatorietService tipigraduatorietService;

    @RequestMapping
    public ModelMap list(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	//	List<TipigradtCfgRotazione> tipigradtcfgrotazioneList = tipigradtcfgrotazioneService.findAll(null, null);
	List<TipigradtCfgRotazione> tipigradtcfgrotazioneList = tipigradtcfgrotazioneService.findByTipigraduatoriet(codice);
	Tipigraduatoriet tipigraduatoriet = tipigraduatorietService.findById(new PkId(codice));
	ModelMap model = new ModelMap(tipigradtcfgrotazioneList);
	boolean export = createJMesaExport(request, response, tipigradtcfgrotazioneList);
	if (export) {
	    return null;
	}
	model.addAttribute("tipigraduatoriet", tipigraduatoriet);
	model.addAttribute("tipigradtcfgrotazioneList", tipigradtcfgrotazioneList);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codiceTipigraduatoriat") Integer codiceTipigraduatoriat, Model model) {

	TipigradtCfgRotazioneCommand tipigradtcfgrotazione = new TipigradtCfgRotazioneCommand();
	TipigradtCfgRotazione entity = new TipigradtCfgRotazione();
	Tipigraduatoriet tipigraduatoriet = tipigraduatorietService.findById(new PkId(codiceTipigraduatoriat));
	entity.setTipigraduatoriet(tipigraduatoriet);
	tipigradtcfgrotazione.setEntity(entity);
	fixRenderEntityProperty(entity);
	model.addAttribute("tipigradtcfgrotazione", tipigradtcfgrotazione);
	setPageAttributes(model);
	return "tipigradtcfgrotazione/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipigradtcfgrotazione") TipigradtCfgRotazioneCommand tipigradtcfgrotazione, BindingResult result,
	    SessionStatus status) {

	TipigradtCfgRotazione entity = tipigradtcfgrotazione.getEntity();
	fixMergeEntityProperty(entity);
	try {
	    tipigradtcfgrotazioneService.insert(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipigradtcfgrotazione.getEntity(), true, e);
	    fixRenderEntityProperty(entity);
	    tipigradtcfgrotazione.setDyn2Modellit(new Dyn2Modellit());
	    
	    tipigradtcfgrotazione.setEntity(entity);
	    return "tipigradtcfgrotazione/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipigradtcfgrotazione.getEntity().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	TipigradtCfgRotazione entity = tipigradtcfgrotazioneService.findById(id);
	TipigradtCfgRotazioneCommand tipigradtcfgrotazione = new TipigradtCfgRotazioneCommand();
	tipigradtcfgrotazione.setEntity(entity);
	fixRenderEntityProperty(tipigradtcfgrotazione.getEntity());
	model.addAttribute("tipigradtcfgrotazione", tipigradtcfgrotazione);
	setPageAttributes(model);
	return "tipigradtcfgrotazione/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipigradtcfgrotazione") TipigradtCfgRotazioneCommand tipigradtcfgrotazione, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	TipigradtCfgRotazione entity = tipigradtcfgrotazione.getEntity();
	fixMergeEntityProperty(entity);
	try {
	    tipigradtcfgrotazioneService.update(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipigradtcfgrotazione.getEntity(), true, e);
	    fixRenderEntityProperty(entity);
	    tipigradtcfgrotazione.setDyn2Modellit(new Dyn2Modellit());
	    //	    tipigradtcfgrotazione.setDyn2ModellitPosteggio(new Dyn2Modellit());
	    tipigradtcfgrotazione.setEntity(entity);
	    return "tipigradtcfgrotazione/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipigradtcfgrotazione.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("tipigradtcfgrotazione") TipigradtCfgRotazioneCommand tipigradtcfgrotazione, BindingResult result,
	    SessionStatus status) {

	TipigradtCfgRotazione entity = tipigradtcfgrotazione.getEntity();
	TipigradtCfgRotazione objToDelete = tipigradtcfgrotazioneService.findById(entity.getId());
	try {
	    tipigradtcfgrotazioneService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(entity);
	    tipigradtcfgrotazione.setEntity(entity);
	    return "tipigradtcfgrotazione/form";
	}
	status.setComplete();
	return "redirect:list.htm?codice=" + tipigradtcfgrotazione.getEntity().getTipigraduatoriet().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(TipigradtCfgRotazione entity) {

    }

    @Override
    protected void fixRenderEntityProperty(TipigradtCfgRotazione entity) {

	if (entity.getCampiMercatoUso() == null) {
	    entity.setCampiMercatoUso(new Dyn2Campi());
	}
	if (entity.getCampiPosteggio() == null) {
	    entity.setCampiPosteggio(new Dyn2Campi());
	}
	
	if (entity.getCampiOrdine() == null) {
	    entity.setCampiOrdine(new Dyn2Campi());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
