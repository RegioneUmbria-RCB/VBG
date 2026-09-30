package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiLivelloServizioHelper;
import it.gruppoinit.pal.gp.core.domain.web.MercatiLivelloServizioCommand;
import it.gruppoinit.pal.gp.core.service.MercatiLivelloServizioService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@SessionAttributes("mercatilivelloservizio")
public class MercatiLivelloServizioController extends BaseController<MercatiLivelloServizio> {

    private static final Logger log = LoggerFactory.getLogger(MercatiLivelloServizioController.class);
    @Autowired
    private MercatiLivelloServizioService mercatilivelloservizioService;
    @Autowired
    private MercatiUsoService mercatiUsoService;

    @RequestMapping
    public ModelMap list(@RequestParam("codicemercato") Integer codicemercato,
	    @RequestParam(required = false, value = "codiceuso") Integer codiceuso, HttpServletRequest request, HttpServletResponse response) {

	List<MercatiLivelloServizioHelper> mercatilivelloservizioList = new ArrayList<MercatiLivelloServizioHelper>();
	if (codiceuso != null) {
	    mercatilivelloservizioList = mercatilivelloservizioService.findByMecatoUso(codiceuso);
	} else {
	    mercatilivelloservizioList = mercatilivelloservizioService.findByMecato(codicemercato);
	}
	ModelMap model = new ModelMap(mercatilivelloservizioList);
	boolean export = createJMesaExport(request, response, mercatilivelloservizioList);
	if (export) {
	    return null;
	}
	model.addAttribute("mercatilivelloservizioList", mercatilivelloservizioList);
	model.addAttribute("codice_uso", codiceuso);
	model.addAttribute("codice_mercato", codicemercato);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam(required = false, value = "codiceuso") Integer codiceuso,
	    @RequestParam(required = false, value = "codicemercato") Integer codicemercato, Model model) {

	List<MercatiUso> usos = mercatiUsoService.findByMercato(codicemercato);
	MercatiLivelloServizioCommand mercatilivelloservizio = new MercatiLivelloServizioCommand();
	MercatiLivelloServizio entity = new MercatiLivelloServizio();
	if (codiceuso != null) {
	    MercatiUso uso = mercatiUsoService.findById(new PkId(codiceuso));
	    entity.setMercatiUso(uso);
	}
	mercatilivelloservizio.setEntity(entity);
	fixRenderEntityProperty(mercatilivelloservizio.getEntity());
	model.addAttribute("mercatilivelloservizio", mercatilivelloservizio);
	model.addAttribute("codiceuso", codiceuso);
	model.addAttribute("codicemercato", codicemercato);
	model.addAttribute("usos", usos);
	setPageAttributes(model);
	return "mercatilivelloservizio/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("mercatilivelloservizio") MercatiLivelloServizioCommand mercatilivelloservizio,
	    BindingResult result, SessionStatus status) {

	boolean inserimentoMultiUso = false;
	fixMergeEntityProperty(mercatilivelloservizio.getEntity());
	List<String> _usi = null;
	try {
	    if (StringUtils.isNotBlank(mercatilivelloservizio.getMercatiusi())) {
		String[] usi = StringUtils.split(mercatilivelloservizio.getMercatiusi(), ",");
		if (usi.length > 0) {
		    _usi = new ArrayList<String>();
		    _usi = new ArrayList<String>(Arrays.asList(usi));
		    inserimentoMultiUso = true;
		}
	    }
	    if (inserimentoMultiUso) {
		log.debug("insert# Inserimento multi uso (Giorni)");
		mercatilivelloservizioService.insert(mercatilivelloservizio.getEntity(), _usi);
	    } else {
		log.debug("insert# Inserimento singolo uso (Giorno)");
		mercatilivelloservizioService.insert(mercatilivelloservizio.getEntity());
	    }
	} catch (Exception e) {
	    if (inserimentoMultiUso) {
		String codiceUso = _usi.get(0);
		MercatiUso mu = mercatiUsoService.findById(new PkId(Integer.parseInt(codiceUso)));
		List<MercatiUso> mus = mercatiUsoService.findByMercato(mu.getMercati().getId().getCodice());
		model.addAttribute("codicemercato", mu.getMercati().getId().getCodice());
		model.addAttribute("usos", mus);
	    } else {
		model.addAttribute("codiceuso", mercatilivelloservizio.getEntity().getMercatiUso().getId());
	    }
	    copyErrorsToBindingResult(result, mercatilivelloservizio.getEntity(), true, e);
	    fixRenderEntityProperty(mercatilivelloservizio.getEntity());
	    return "mercatilivelloservizio/form";
	}
	status.setComplete();
	if (inserimentoMultiUso) {
	    return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F" + "&status_msg=02";
	} else {
	    return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F" + "&status_msg=02";
	    //  return "redirect:view.htm?codice=" + mercatilivelloservizio.getEntity().getId().getCodice() + "&status_msg=01";
	}
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, @RequestParam(required = false, value = "impostachiusura") String impostachiusura,
	    Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	MercatiLivelloServizioCommand mercatilivelloservizio = new MercatiLivelloServizioCommand();
	MercatiLivelloServizio entity = mercatilivelloservizioService.findById(id);
	mercatilivelloservizio.setEntity(entity);
	fixRenderEntityProperty(mercatilivelloservizio.getEntity());
	model.addAttribute("mercatilivelloservizio", mercatilivelloservizio);
	model.addAttribute("impostachiusura", BooleanUtils.toBoolean(impostachiusura));
	model.addAttribute("codiceuso", mercatilivelloservizio.getEntity().getMercatiUso().getId().getCodice());
	setPageAttributes(model);
	return "mercatilivelloservizio/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("mercatilivelloservizio") MercatiLivelloServizioCommand mercatilivelloservizio, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(mercatilivelloservizio.getEntity());
	try {
	    mercatilivelloservizioService.updateServzioAndServizioPosteggio(mercatilivelloservizio.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatilivelloservizio.getEntity(), true, e);
	    fixRenderEntityProperty(mercatilivelloservizio.getEntity());
	    return "mercatilivelloservizio/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatilivelloservizio.getEntity().getId().getCodice() + "&status_msg=02";
    }

    //    @RequestMapping
    //    public String delete(@ModelAttribute("mercatilivelloservizio") MercatiLivelloServizioCommand mercatilivelloservizio, BindingResult result,
    //	    SessionStatus status) {
    //
    //	MercatiLivelloServizio objToDelete = mercatilivelloservizioService.findById(mercatilivelloservizio.getId());
    //	try {
    //	    mercatilivelloservizioService.delete(objToDelete);
    //	} catch (Exception e) {
    //	    copyErrorsToBindingResult(result, objToDelete, true, e);
    //	    fixRenderEntityProperty(mercatilivelloservizio);
    //	    return "mercatilivelloservizio/form";
    //	}
    //	status.setComplete();
    //	return "redirect:list.htm";
    //    }
    @RequestMapping
    public String deleteSingoloLivello(@RequestParam(value = "codice") Integer codice,
	    @RequestParam(required = false, value = "codiceuso") Integer codiceuso, @RequestParam(value = "codicemercato") Integer codicemercato,
	    Model model) {

	MercatiLivelloServizio objToDelete = mercatilivelloservizioService.findById(new PkId(codice));
	try {
	    mercatilivelloservizioService.delete(objToDelete);
	} catch (Exception e) {
	    log.debug("deleteSingoloLivello# {}", e);
	    FlashMessages.getWarnings().add(e.getMessage());
	    if (codiceuso != null) {
		return "redirect:list.htm?codicemercato=" + codicemercato + "&codiceuso=" + codiceuso;
	    } else {
		return "redirect:list.htm?codicemercato=" + codicemercato;
	    }
	}
	String mess = getMessageFromBundle("02", null);
	FlashMessages.getInfos().add(mess);
	if (codiceuso != null) {
	    return "redirect:list.htm?codicemercato=" + codicemercato + "&codiceuso=" + codiceuso;
	} else {
	    return "redirect:list.htm?codicemercato=" + codicemercato;
	}
    }

    @RequestMapping
    public String ajaxDettaglio(@RequestParam("codiceuso") Integer codiceuso, @RequestParam(value = "codiceservizio") Integer codiceservizio,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	List<MercatiLivelloServizio> gds = mercatilivelloservizioService.findByUsoAndServizio(codiceuso, codiceservizio);
	model.addAttribute("gds", gds);
	return "mercatilivelloservizio/ajaxDettaglioMercatoLivelloServizi";
    }

    @RequestMapping
    public String createUpdateTariffaServizio(@RequestParam(required = true, value = "codiceservizio") Integer codiceservizio, Model model) {

	MercatiLivelloServizioCommand mercatilivelloservizio = new MercatiLivelloServizioCommand();
	MercatiLivelloServizio entityUpdate = mercatilivelloservizioService.findById(new PkId(codiceservizio));
	MercatiLivelloServizio entity = new MercatiLivelloServizio();
	entity = clone(entityUpdate);
	entity.setTariffa(null);
	if (entityUpdate.getDataFineValidita() != null) {
	    entity.setDataInizioValidita(Utilities.addAndremoveDays(entityUpdate.getDataFineValidita(), 1, true));
	    model.addAttribute("isDataInizioCalcolata", true);
	} else {
	    model.addAttribute("isDataInizioCalcolata", false);
	    entity.setDataInizioValidita(null);
	}
	entity.setDataFineValidita(null);
	mercatilivelloservizio.setEntity(entity);
	mercatilivelloservizio.setEntityupdate(entityUpdate);
	fixRenderEntityProperty(mercatilivelloservizio.getEntity());
	fixRenderEntityProperty(mercatilivelloservizio.getEntityupdate());
	model.addAttribute("mercatilivelloservizio", mercatilivelloservizio);
	setPageAttributes(model);
	return "mercatilivelloservizio/formAggiornaTariffa";
    }

    @RequestMapping
    public String aggiornaTariffa(@RequestParam("codiceservizio") Integer codiceservizio, Model model,
	    @ModelAttribute("mercatilivelloservizio") MercatiLivelloServizioCommand mercatilivelloservizio, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(mercatilivelloservizio.getEntity());
	MercatiLivelloServizio daAggiornare = mercatilivelloservizioService.findById(new PkId(mercatilivelloservizio.getEntityupdate().getId()
		.getCodice()));
	try {
	    mercatilivelloservizioService.aggiornaTariffa(daAggiornare, mercatilivelloservizio.getEntity());
	} catch (Exception e) {
	    daAggiornare = mercatilivelloservizioService.findById(new PkId(daAggiornare.getId().getCodice()));
	    mercatilivelloservizio.setEntityupdate(daAggiornare);
	    MercatiLivelloServizio entity = new MercatiLivelloServizio();
	    entity = clone(daAggiornare);
	    entity.setTariffa(null);
	    if (daAggiornare.getDataFineValidita() != null) {
		entity.setDataInizioValidita(Utilities.addAndremoveDays(daAggiornare.getDataFineValidita(), 1, true));
		model.addAttribute("isDataInizioCalcolata", true);
	    } else {
		model.addAttribute("isDataInizioCalcolata", false);
		entity.setDataInizioValidita(null);
	    }
	    entity.setDataFineValidita(null);
	    copyErrorsToBindingResult(result, mercatilivelloservizio.getEntity(), true, e);
	    fixRenderEntityProperty(mercatilivelloservizio.getEntity());
	    fixRenderEntityProperty(mercatilivelloservizio.getEntityupdate());
	    return "mercatilivelloservizio/formAggiornaTariffa";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F" + "&status_msg=02";
    }

    private MercatiLivelloServizio clone(MercatiLivelloServizio sorgente) {

	MercatiLivelloServizio entity = null;
	try {
	    entity = new MercatiLivelloServizio();
	    entity.setAttivo(sorgente.getAttivo());
	    entity.setDataFineValidita(sorgente.getDataFineValidita());
	    entity.setDataInizioValidita(sorgente.getDataInizioValidita());
	    entity.setDescrizione(sorgente.getDescrizione());
	    entity.setLivelloServizio(sorgente.getLivelloServizio());
	    entity.setMercatiUso(sorgente.getMercatiUso());
	    entity.setTariffa(sorgente.getTariffa());
	    entity.setId(null);
	} catch (Exception e) {
	    log.debug("clone# {}", e.getMessage());
	    throw new RuntimeException(e);
	}
	return entity;
    }

    @Override
    protected void fixMergeEntityProperty(MercatiLivelloServizio entity) {

    }

    @Override
    protected void fixRenderEntityProperty(MercatiLivelloServizio entity) {

	if (entity.getLivelloServizio() == null) {
	    entity.setLivelloServizio(new LivelloServizio());
	}
	if (entity.getMercatiUso() == null) {
	    entity.setMercatiUso(new MercatiUso());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
