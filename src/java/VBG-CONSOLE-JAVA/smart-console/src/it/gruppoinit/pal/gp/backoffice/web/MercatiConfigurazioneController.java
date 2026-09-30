/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.Concessionitipi;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivita;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivitaId;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RangeRateizzazioni;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.service.ContiService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.MercatiCfgAttivitaService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.RangeRateizzazioniService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipologiaregistriService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author francescop
 */
//DAELIMINARE @Controller
@SessionAttributes(value = { "mercatiConfigurazione", "mercatiCfgAttivita" })
public class MercatiConfigurazioneController extends BaseController<MercatiConfigurazione> {

    @Autowired
    private MercatiConfigurazioneService mercatiConfigurazioneService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private MercatiCfgAttivitaService mercatiCfgAttivitaService;
    @Autowired
    private RangeRateizzazioniService rangeRateizzazioniService;
    @Autowired
    private TipologiaregistriService tipologiaregistriService;
    @Autowired
    private ContiService contiService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;

    @RequestMapping
    public String insert(@ModelAttribute("mercatiConfigurazione") MercatiConfigurazione mercatiConfigurazione, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(mercatiConfigurazione);
	try {
	    mercatiConfigurazioneService.insert(mercatiConfigurazione);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiConfigurazione, e);
	    fixRenderEntityProperty(mercatiConfigurazione);
	    request.setAttribute(WebConstants.DISPATCH, WebConstants.DISPATCH_CREATE);
	    return "mercaticonfigurazione/form";
	}
	status.setComplete();
	return "redirect:view.htm?status_msg=01";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public String update(@ModelAttribute("mercatiConfigurazione") MercatiConfigurazione mercatiConfigurazione, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// FIXME SPOSTARE NEL SERVICE PER TRANSAZIONALITA' !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	/*
	 * Verifico se il settore è stato modificato Se il settore è stato modificato allora elimina tutti i record di
	 * MercatiCfgAttivita legati al vecchio settore
	 */
	MercatiConfigurazione mercatiConfig = mercatiConfigurazioneService.findById(mercatiConfigurazione.getId());
	Settori settoriDB = mercatiConfig.getSettori();
	Settori settoriModel = mercatiConfigurazione.getSettori();
	if (settoriDB != null && settoriModel != null) {
	    if (!settoriDB.getId().getCodicesettore().equals(settoriModel.getId().getCodicesettore())) {
		List<MercatiCfgAttivita> mercatiCfgAttivitaList = mercatiCfgAttivitaService.findAll(null, null);
		for (Iterator iterator = mercatiCfgAttivitaList.iterator(); iterator.hasNext();) {
		    MercatiCfgAttivita mercatiCfgAttivita = (MercatiCfgAttivita) iterator.next();
		    try {
			mercatiCfgAttivitaService.delete(mercatiCfgAttivita);
		    } catch (Exception e) {
			copyErrorsToBindingResult(result, mercatiConfigurazione, e);
			fixRenderEntityProperty(mercatiConfigurazione);
			request.setAttribute(WebConstants.DISPATCH, WebConstants.DISPATCH_VIEW);
			return "mercaticonfigurazione/form";
		    }
		}
	    }
	}
	// FIXME bug di hibernate quando ho più chiavi esterne verso la stessa tabella
	if (!request.getParameter("contoDefault.id.codice").equals("")) {
	    Integer contoDefaultInteger = Integer.parseInt(request.getParameter("contoDefault.id.codice"));
	    PkId contoDefaultId = new PkId(contoDefaultInteger);
	    Conti contoDefault = contiService.findById(contoDefaultId);
	    mercatiConfigurazione.setContoDefault(contoDefault);
	} else {
	    mercatiConfigurazione.setContoDefault(null);
	}
	if (!request.getParameter("contoInteressi.id.codice").equals("")) {
	    Integer contoInteressiInteger = Integer.parseInt(request.getParameter("contoInteressi.id.codice"));
	    PkId contoInteressiId = new PkId(contoInteressiInteger);
	    Conti contoInteressi = contiService.findById(contoInteressiId);
	    mercatiConfigurazione.setContoInteressi(contoInteressi);
	} else {
	    mercatiConfigurazione.setContoInteressi(null);
	}
	if (!request.getParameter("registroAutorizzazioni.id.codice").equals("")) {
	    Integer registroAutorizzazioniId = Integer.parseInt(request.getParameter("registroAutorizzazioni.id.codice"));
	    PkId id = new PkId(registroAutorizzazioniId);
	    Tipologiaregistri registroAutorizzazioni = tipologiaregistriService.findById(id);
	    mercatiConfigurazione.setRegistroAutorizzazioni(registroAutorizzazioni);
	} else {
	    mercatiConfigurazione.setRegistroAutorizzazioni(null);
	}
	if (!request.getParameter("registroConcessioni.id.codice").equals("")) {
	    Integer registroConcessioniId = Integer.parseInt(request.getParameter("registroConcessioni.id.codice"));
	    PkId id = new PkId(registroConcessioniId);
	    Tipologiaregistri registroConcessioni = tipologiaregistriService.findById(id);
	    mercatiConfigurazione.setRegistroConcessioni(registroConcessioni);
	} else {
	    mercatiConfigurazione.setRegistroConcessioni(null);
	}
	if (!request.getParameter("dyn2CampiByFkMerconfDyn2campiCa.id.codice").equals("")) {
	    Integer dyn2CampiByFkMerconfDyn2campiCaId = Integer.parseInt(request.getParameter("dyn2CampiByFkMerconfDyn2campiCa.id.codice"));
	    PkId id = new PkId(dyn2CampiByFkMerconfDyn2campiCaId);
	    Dyn2Campi dyn2CampiByFkMerconfDyn2campiCa = dyn2CampiService.findById(id);
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiCa(dyn2CampiByFkMerconfDyn2campiCa);
	} else {
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiCa(null);
	}
	if (!request.getParameter("dyn2CampiByFkMerconfDyn2campiNa.id.codice").equals("")) {
	    Integer dyn2CampiByFkMerconfDyn2campiNaId = Integer.parseInt(request.getParameter("dyn2CampiByFkMerconfDyn2campiNa.id.codice"));
	    PkId id = new PkId(dyn2CampiByFkMerconfDyn2campiNaId);
	    Dyn2Campi dyn2CampiByFkMerconfDyn2campiNa = dyn2CampiService.findById(id);
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiNa(dyn2CampiByFkMerconfDyn2campiNa);
	} else {
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiNa(null);
	}
	if (!request.getParameter("dyn2CampiByFkMerconfDyn2campiDa.id.codice").equals("")) {
	    Integer dyn2CampiByFkMerconfDyn2campiDaId = Integer.parseInt(request.getParameter("dyn2CampiByFkMerconfDyn2campiDa.id.codice"));
	    PkId id = new PkId(dyn2CampiByFkMerconfDyn2campiDaId);
	    Dyn2Campi dyn2CampiByFkMerconfDyn2campiDa = dyn2CampiService.findById(id);
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiDa(dyn2CampiByFkMerconfDyn2campiDa);
	} else {
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiDa(null);
	}
	if (!request.getParameter("dyn2CampiByFkMerconfDyn2campiCodregaut.id.codice").equals("")) {
	    Integer dyn2CampiByFkMerconfDyn2campiCodregautId = Integer.parseInt(request
		    .getParameter("dyn2CampiByFkMerconfDyn2campiCodregaut.id.codice"));
	    PkId id = new PkId(dyn2CampiByFkMerconfDyn2campiCodregautId);
	    Dyn2Campi dyn2CampiByFkMerconfDyn2campiCodregaut = dyn2CampiService.findById(id);
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiCodregaut(dyn2CampiByFkMerconfDyn2campiCodregaut);
	} else {
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiCodregaut(null);
	}
	if (!request.getParameter("dyn2CampiByFkMerconfDyn2campiCm.id.codice").equals("")) {
	    Integer dyn2CampiByFkMerconfDyn2campiCmId = Integer.parseInt(request.getParameter("dyn2CampiByFkMerconfDyn2campiCm.id.codice"));
	    PkId id = new PkId(dyn2CampiByFkMerconfDyn2campiCmId);
	    Dyn2Campi dyn2CampiByFkMerconfDyn2campiCm = dyn2CampiService.findById(id);
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiCm(dyn2CampiByFkMerconfDyn2campiCm);
	} else {
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiCm(null);
	}
	// /////////////////////////////////////////////////////////////////////////////////////////
	fixMergeEntityProperty(mercatiConfigurazione);
	try {
	    mercatiConfigurazioneService.update(mercatiConfigurazione);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiConfigurazione, e);
	    fixRenderEntityProperty(mercatiConfigurazione);
	    request.setAttribute(WebConstants.DISPATCH, WebConstants.DISPATCH_VIEW);
	    return "mercaticonfigurazione/form";
	}
	status.setComplete();
	return "redirect:view.htm?status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	MercatiConfigurazioneId mercatiConfigurazioneId = new MercatiConfigurazioneId(softwareService.findById(ORMHelper.getSoftware()).getCodice());
	MercatiConfigurazione mercatiConfigurazione = new MercatiConfigurazione();
	mercatiConfigurazione.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	mercatiConfigurazione.setId(mercatiConfigurazioneId);
	fixRenderEntityProperty(mercatiConfigurazione);
	List<MercatiCfgAttivita> mercatiCfgAttivitaList = new ArrayList<MercatiCfgAttivita>();
	model.addAttribute("mercatiConfigurazione", mercatiConfigurazione);
	model.addAttribute("mercatiCfgAttivitaList", mercatiCfgAttivitaList);
	model.addAttribute(WebConstants.DISPATCH, WebConstants.DISPATCH_CREATE);
	setPageAttributes(model);
	return "mercaticonfigurazione/form";
    }

    @RequestMapping
    public String view(Model model, HttpServletRequest request) {

	MercatiConfigurazioneId mercatiConfigurazioneId = new MercatiConfigurazioneId(softwareService.findById(ORMHelper.getSoftware()).getCodice());
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findById(mercatiConfigurazioneId);
	if (mercatiConfigurazione == null) {
	    return "redirect:create.htm";
	}
	// recupero la lista dei campi di rateizzazione per vedere se gia esiste o no
	List<RangeRateizzazioni> listrateizzazioni = rangeRateizzazioniService.findAll(null, null);
	List<MercatiCfgAttivita> mercatiCfgAttivitaList = mercatiCfgAttivitaService.findAll(null, null);
	// List<MercatiIdentaut> listMercatiIdentAut = mercatiIdentautService.findAll(null, null);
	fixRenderEntityProperty(mercatiConfigurazione);
	model.addAttribute("mercatiConfigurazione", mercatiConfigurazione);
	model.addAttribute("mercatiCfgAttivitaList", mercatiCfgAttivitaList);
	model.addAttribute("listrateizzazioni", listrateizzazioni);
	// model.addAttribute("listidentaut", listMercatiIdentAut);
	model.addAttribute(WebConstants.DISPATCH, WebConstants.DISPATCH_VIEW);
	setPageAttributes(model);
	return "mercaticonfigurazione/form";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("mercatiConfigurazione") MercatiConfigurazione mercatiConfigurazione, BindingResult result,
	    SessionStatus status) {

	try {
	    mercatiConfigurazioneService.delete(mercatiConfigurazione);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiConfigurazione, e);
	    List<RangeRateizzazioni> listrateizzazioni = rangeRateizzazioniService.findAll(null, null);
	    List<MercatiCfgAttivita> mercatiCfgAttivitaList = mercatiCfgAttivitaService.findAll(null, null);
	    // List<MercatiIdentaut> listMercatiIdentAut = mercatiIdentautService.findAll(null, null);
	    fixRenderEntityProperty(mercatiConfigurazione);
	    model.addAttribute("mercatiCfgAttivitaList", mercatiCfgAttivitaList);
	    model.addAttribute("listrateizzazioni", listrateizzazioni);
	    // model.addAttribute("listidentaut", listMercatiIdentAut);
	    setPageAttributes(model);
	    return "mercaticonfigurazione/form";
	}
	return "redirect:create.htm";
    }

    @RequestMapping
    public String createMercatiCfgAttivita(Model model, @RequestParam("codicesettore") String codicesettore) {

	MercatiCfgAttivita mercatiCfgAttivita = new MercatiCfgAttivita();
	mercatiCfgAttivita.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	model.addAttribute("mercatiCfgAttivita", mercatiCfgAttivita);
	model.addAttribute("codicesettore", codicesettore);
	return "mercaticonfigurazione/formMercatiCfgAttivita";
    }

    @RequestMapping
    public String insertMercatiCfgAttivita(@RequestParam("codicesettore") String codicesettore,
	    @ModelAttribute("mercatiCfgAttivita") MercatiCfgAttivita mercatiCfgAttivita, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	MercatiCfgAttivitaId mercatiCfgAttivitaId = new MercatiCfgAttivitaId();
	mercatiCfgAttivitaId.setFkCodiceattivita((String) mercatiCfgAttivita.getAttivita().getId().getCodiceistat());
	mercatiCfgAttivita.setId(mercatiCfgAttivitaId);
	fixMergeMercatiCfgAttivita(mercatiCfgAttivita);
	try {
	    mercatiCfgAttivitaService.insert(mercatiCfgAttivita);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiCfgAttivita, e);
	    mercatiCfgAttivita.getId().setFkCodiceattivita(null);
	    fixRenderMercatiCfgAttivita(mercatiCfgAttivita);
	    request.setAttribute("codicesettore", codicesettore);
	    return "mercaticonfigurazione/formMercatiCfgAttivita";
	}
	status.setComplete();
	return "redirect:viewMercatiCfgAttivita.htm?codicesettore=" + codicesettore + "&codice=" + mercatiCfgAttivita.getId().getFkCodiceattivita()
		+ "&status_msg=01";
    }

    @RequestMapping
    public String viewMercatiCfgAttivita(@RequestParam("codicesettore") String codicesettore, @RequestParam("codice") String codice, Model model,
	    HttpServletRequest request) {

	MercatiCfgAttivitaId mercatiCfgAttivitaId = new MercatiCfgAttivitaId(codice);
	MercatiCfgAttivita mercatiCfgAttivita = mercatiCfgAttivitaService.findById(mercatiCfgAttivitaId);
	model.addAttribute("mercatiCfgAttivita", mercatiCfgAttivita);
	request.setAttribute("codicesettore", codicesettore);
	return "mercaticonfigurazione/formMercatiCfgAttivita";
    }

    @RequestMapping
    public String updateMercatiCfgAttivita(@RequestParam("codicesettore") String codicesettore,
	    @ModelAttribute("mercatiCfgAttivita") MercatiCfgAttivita mercatiCfgAttivita, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeMercatiCfgAttivita(mercatiCfgAttivita);
	try {
	    mercatiCfgAttivitaService.update(mercatiCfgAttivita);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiCfgAttivita, e);
	    fixRenderMercatiCfgAttivita(mercatiCfgAttivita);
	    request.setAttribute("codicesettore", codicesettore);
	    return "mercaticonfigurazione/formMercatiCfgAttivita";
	}
	status.setComplete();
	return "redirect:viewMercatiCfgAttivita.htm?codicesettore=" + codicesettore + "&codice="
		+ mercatiCfgAttivita.getAttivita().getId().getCodiceistat() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteMercatiCfgAttivita(Model model, @ModelAttribute("mercatiCfgAttivita") MercatiCfgAttivita mercatiCfgAttivita,
	    BindingResult result, SessionStatus status) {

	try {
	    mercatiCfgAttivitaService.delete(mercatiCfgAttivita);
	} catch (Exception e) {
	    Map<String, String> map = new HashMap<String, String>();
	    model.addAttribute("commandName", "mercatiCfgAttivita");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	return "redirect:view.htm";
    }

    @Override
    protected void fixMergeEntityProperty(MercatiConfigurazione entity) {

	if (entity.getSettori() != null && entity.getSettori().getId() != null
		&& (entity.getSettori().getId().getCodicesettore() == null || entity.getSettori().getId().getCodicesettore().equals(""))) {
	    entity.setSettori(null);
	}
	if (entity.getContoDefault() != null && entity.getContoDefault().getId() != null && (entity.getContoDefault().getId().getCodice() == null)) {
	    entity.setContoDefault(null);
	}
	if (entity.getContoInteressi() != null && entity.getContoInteressi().getId() != null
		&& (entity.getContoInteressi().getId().getCodice() == null)) {
	    entity.setContoInteressi(null);
	}
	if (entity.getRegistroAutorizzazioni() != null && entity.getRegistroAutorizzazioni().getId() != null
		&& entity.getRegistroAutorizzazioni().getId().getCodice() == null) {
	    entity.setRegistroAutorizzazioni(null);
	}
	if (entity.getRegistroConcessioni() != null && entity.getRegistroConcessioni().getId() != null
		&& entity.getRegistroConcessioni().getId().getCodice() == null) {
	    entity.setRegistroConcessioni(null);
	}
	if (entity.getTipoConcessione() != null && StringUtils.isBlank(entity.getTipoConcessione().getTipoconcessione())) {
	    entity.setTipoConcessione(null);
	}
	if (entity.getCausaleConcessione() != null && entity.getCausaleConcessione().getId() != null
		&& entity.getCausaleConcessione().getId().getCodice() == null) {
	    entity.setCausaleConcessione(null);
	}
	if (entity.getLetteraTipo() != null && entity.getLetteraTipo().getId() != null && entity.getLetteraTipo().getId().getCodice() == null) {
	    entity.setLetteraTipo(null);
	}
	if (entity.getDyn2Modellit() != null && entity.getDyn2Modellit().getId() != null && entity.getDyn2Modellit().getId().getCodice() == null) {
	    entity.setDyn2Modellit(null);
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiCa() != null && entity.getDyn2CampiByFkMerconfDyn2campiCa().getId() != null
		&& entity.getDyn2CampiByFkMerconfDyn2campiCa().getId().getCodice() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiCa(null);
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiCm() != null && entity.getDyn2CampiByFkMerconfDyn2campiCm().getId() != null
		&& entity.getDyn2CampiByFkMerconfDyn2campiCm().getId().getCodice() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiCm(null);
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiDa() != null && entity.getDyn2CampiByFkMerconfDyn2campiDa().getId() != null
		&& entity.getDyn2CampiByFkMerconfDyn2campiDa().getId().getCodice() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiDa(null);
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiNa() != null && entity.getDyn2CampiByFkMerconfDyn2campiNa().getId() != null
		&& entity.getDyn2CampiByFkMerconfDyn2campiNa().getId().getCodice() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiNa(null);
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiCodregaut() != null && entity.getDyn2CampiByFkMerconfDyn2campiCodregaut().getId() != null
		&& entity.getDyn2CampiByFkMerconfDyn2campiCodregaut().getId().getCodice() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiCodregaut(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(MercatiConfigurazione entity) {

	if (entity.getSettori() == null) {
	    entity.setSettori(new Settori());
	}
	if (entity.getContoDefault() == null) {
	    entity.setContoDefault(new Conti());
	}
	if (entity.getContoInteressi() == null) {
	    entity.setContoInteressi(new Conti());
	}
	if (entity.getRegistroAutorizzazioni() == null) {
	    entity.setRegistroAutorizzazioni(new Tipologiaregistri());
	}
	if (entity.getRegistroConcessioni() == null) {
	    entity.setRegistroConcessioni(new Tipologiaregistri());
	}
	if (entity.getTipoConcessione() == null) {
	    entity.setTipoConcessione(new Concessionitipi());
	}
	if (entity.getCausaleConcessione() == null) {
	    entity.setCausaleConcessione(new Concessionicausali());
	}
	if (entity.getLetteraTipo() == null) {
	    entity.setLetteraTipo(new Letteretipo());
	}
	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiCa() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiCa(new Dyn2Campi());
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiCm() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiCm(new Dyn2Campi());
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiDa() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiDa(new Dyn2Campi());
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiNa() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiNa(new Dyn2Campi());
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiCodregaut() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiCodregaut(new Dyn2Campi());
	}
    }

    protected void fixRenderMercatiCfgAttivita(MercatiCfgAttivita entity) {

	if (entity.getAttivita() == null) {
	    entity.setAttivita(new Attivita());
	}
    }

    protected void fixMergeMercatiCfgAttivita(MercatiCfgAttivita entity) {

	if (entity.getAttivita() != null && entity.getAttivita().getId() != null
		&& (entity.getAttivita().getId().getCodiceistat() == null || entity.getAttivita().getId().getCodiceistat() == "")) {
	    entity.setAttivita(null);
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
