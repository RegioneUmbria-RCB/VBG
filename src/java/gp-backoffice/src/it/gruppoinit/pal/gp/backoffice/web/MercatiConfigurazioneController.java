/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.Concessionitipi;
import it.gruppoinit.pal.gp.core.domain.Concessioniuso;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.MercatiCategorie;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivita;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivitaId;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgConti;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.PosteggiSettori;
import it.gruppoinit.pal.gp.core.domain.RangeRateizzazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.service.ConcessionicausaliService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.MercatiCfgAttivitaService;
import it.gruppoinit.pal.gp.core.service.MercatiCfgContiService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.RangeRateizzazioniService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniCausaliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * @author francescop
 */
@Controller
@SessionAttributes(value = { "mercatiConfigurazione", "mercatiCfgAttivita", "mercatiCfgConti" })
public class MercatiConfigurazioneController extends BaseController<MercatiConfigurazione> {

    @Autowired
    private MercatiConfigurazioneService mercatiConfigurazioneService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private MercatiCfgAttivitaService mercatiCfgAttivitaService;
    @Autowired
    private RegistrazioniCausaliService registrazioniCausaliService;
    @Autowired
    private RangeRateizzazioniService rangeRateizzazioniService;
    @Autowired
    private TipologiaregistriService tipologiaregistriService;
    @Autowired
    private ContiService contiService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private MercatiCfgContiService mercatiCfgContiService;
    @Autowired
    private ConcessionicausaliService concessionicausaliService;

    @RequestMapping
    public String insert(@ModelAttribute("mercatiConfigurazione") MercatiConfigurazione mercatiConfigurazione, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(mercatiConfigurazione);
	try {
	    if (mercatiConfigurazione.getGradIntervalloDate() != null) {
		mercatiConfigurazione.setGradIntervalloDate(this.validaIntervalloDate(mercatiConfigurazione.getGradIntervalloDate()));
	    }
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
	if (!request.getParameter("causaleCanone.id.codice").equals("")) {
	    Integer causaleCanone = Integer.parseInt(request.getParameter("causaleCanone.id.codice"));
	    PkId causaleCanoneId = new PkId(causaleCanone);
	    RegistrazioniCausali regCausaleCanone = registrazioniCausaliService.findById(causaleCanoneId);
	    mercatiConfigurazione.setCausaleCanone(regCausaleCanone);
	} else {
	    mercatiConfigurazione.setCausaleCanone(null);
	}
	if (!request.getParameter("causaleAumento.id.codice").equals("")) {
	    Integer causaleAumento = Integer.parseInt(request.getParameter("causaleAumento.id.codice"));
	    PkId causaleAumentoId = new PkId(causaleAumento);
	    RegistrazioniCausali regCausaleAumento = registrazioniCausaliService.findById(causaleAumentoId);
	    mercatiConfigurazione.setCausaleAumento(regCausaleAumento);
	} else {
	    mercatiConfigurazione.setCausaleAumento(null);
	}
	if (!request.getParameter("causaleDiminuzione.id.codice").equals("")) {
	    Integer causaleDiminuzione = Integer.parseInt(request.getParameter("causaleDiminuzione.id.codice"));
	    PkId causaleDiminuzioneId = new PkId(causaleDiminuzione);
	    RegistrazioniCausali regCausaleDiminuzione = registrazioniCausaliService.findById(causaleDiminuzioneId);
	    mercatiConfigurazione.setCausaleDiminuzione(regCausaleDiminuzione);
	} else {
	    mercatiConfigurazione.setCausaleDiminuzione(null);
	}
	if (!request.getParameter("causaleTransazione.id.codice").equals("")) {
	    Integer causaleTransazione = Integer.parseInt(request.getParameter("causaleTransazione.id.codice"));
	    PkId causaleTransazioneId = new PkId(causaleTransazione);
	    RegistrazioniCausali regCausaleTransazione = registrazioniCausaliService.findById(causaleTransazioneId);
	    mercatiConfigurazione.setCausaleTransazione(regCausaleTransazione);
	} else {
	    mercatiConfigurazione.setCausaleTransazione(null);
	}
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
	    Integer dyn2CampiByFkMerconfDyn2campiCodregautId = Integer
		    .parseInt(request.getParameter("dyn2CampiByFkMerconfDyn2campiCodregaut.id.codice"));
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
	if (!request.getParameter("dyn2CampiByFkMerconfDyn2campiMerc.id.codice").equals("")) {
	    Integer dyn2CampiByFkMerconfDyn2campiMercId = Integer.parseInt(request.getParameter("dyn2CampiByFkMerconfDyn2campiMerc.id.codice"));
	    PkId id = new PkId(dyn2CampiByFkMerconfDyn2campiMercId);
	    Dyn2Campi dyn2CampiByFkMerconfDyn2campiMerc = dyn2CampiService.findById(id);
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiMerc(dyn2CampiByFkMerconfDyn2campiMerc);
	} else {
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiMerc(null);
	}
	if (!request.getParameter("dyn2CampiByFkMerconfDyn2campiMercUso.id.codice").equals("")) {
	    Integer dyn2CampiByFkMerconfDyn2campiMercUsoId = Integer.parseInt(request.getParameter("dyn2CampiByFkMerconfDyn2campiMercUso.id.codice"));
	    PkId id = new PkId(dyn2CampiByFkMerconfDyn2campiMercUsoId);
	    Dyn2Campi dyn2CampiByFkMerconfDyn2campiMercUso = dyn2CampiService.findById(id);
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiMercUso(dyn2CampiByFkMerconfDyn2campiMercUso);
	} else {
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiMercUso(null);
	}
	if (!request.getParameter("dyn2CampiByFkMerconfDyn2campiPost.id.codice").equals("")) {
	    Integer dyn2CampiByFkMerconfDyn2campiPostId = Integer.parseInt(request.getParameter("dyn2CampiByFkMerconfDyn2campiPost.id.codice"));
	    PkId id = new PkId(dyn2CampiByFkMerconfDyn2campiPostId);
	    Dyn2Campi dyn2CampiByFkMerconfDyn2campiPost = dyn2CampiService.findById(id);
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiPost(dyn2CampiByFkMerconfDyn2campiPost);
	} else {
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiPost(null);
	}
	if (!request.getParameter("dyn2CampiByFkMerconfDyn2campiAss.id.codice").equals("")) {
	    Integer dyn2CampiByFkMerconfDyn2campiAssId = Integer.parseInt(request.getParameter("dyn2CampiByFkMerconfDyn2campiAss.id.codice"));
	    PkId id = new PkId(dyn2CampiByFkMerconfDyn2campiAssId);
	    Dyn2Campi dyn2CampiByFkMerconfDyn2campiAss = dyn2CampiService.findById(id);
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiAss(dyn2CampiByFkMerconfDyn2campiAss);
	} else {
	    mercatiConfigurazione.setDyn2CampiByFkMerconfDyn2campiAss(null);
	}
	if (!request.getParameter("causaleAcqRiottenimento.id.codice").equals("")) {
	    Integer idCausale = Integer.parseInt(request.getParameter("causaleAcqRiottenimento.id.codice"));
	    PkId id = new PkId(idCausale);
	    Concessionicausali caus = concessionicausaliService.findById(id);
	    mercatiConfigurazione.setCausaleAcqRiottenimento(caus);
	} else {
	    mercatiConfigurazione.setCausaleAcqRiottenimento(null);
	}
	if (!request.getParameter("causaleCessRiottenimento.id.codice").equals("")) {
	    Integer idCausale = Integer.parseInt(request.getParameter("causaleCessRiottenimento.id.codice"));
	    PkId id = new PkId(idCausale);
	    Concessionicausali caus = concessionicausaliService.findById(id);
	    mercatiConfigurazione.setCausaleCessRiottenimento(caus);
	} else {
	    mercatiConfigurazione.setCausaleCessRiottenimento(null);
	}
	if (!request.getParameter("causaleCessSistema.id.codice").equals("")) {
	    Integer idCausale = Integer.parseInt(request.getParameter("causaleCessSistema.id.codice"));
	    PkId id = new PkId(idCausale);
	    Concessionicausali caus = concessionicausaliService.findById(id);
	    mercatiConfigurazione.setCausaleCessSistema(caus);
	} else {
	    mercatiConfigurazione.setCausaleCessSistema(null);
	}
	// /////////////////////////////////////////////////////////////////////////////////////////
	fixMergeEntityProperty(mercatiConfigurazione);
	try {
	    if (mercatiConfigurazione.getGradIntervalloDate() != null) {
		mercatiConfigurazione.setGradIntervalloDate(this.validaIntervalloDate(mercatiConfigurazione.getGradIntervalloDate()));
	    }
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

    private String validaIntervalloDate(String gradIntervalloDate) {

	String[] interv = gradIntervalloDate.split("-");
	String _gradIntervalloDate = "";
	for (int i = 0; i < interv.length; i++) {
	    String data = interv[i].trim();
	    String regex = "(0[1-9]|[1-2][0-9]|3[0-1])/(0[1-9]|1[0-2])";
	    boolean match = Pattern.matches(regex, data);
	    if (!match) {
		throw new IllegalArgumentException("Formato data in 'Intervallo date presenze' non corretto");
	    }
	    _gradIntervalloDate += data;
	    if (i + 1 < interv.length) {
		_gradIntervalloDate += "-";
	    }
	}
	return _gradIntervalloDate;
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
	return "redirect:viewMercatiCfgAttivita.htm?codicesettore=" + codicesettore + "&codice=" + mercatiCfgAttivita.getId().getFkCodiceattivita() +
	       "&status_msg=01";
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
	return "redirect:viewMercatiCfgAttivita.htm?codicesettore=" + codicesettore + "&codice=" +
	       mercatiCfgAttivita.getAttivita().getId().getCodiceistat() + "&status_msg=02";
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

    @RequestMapping
    public String listMercatiCfgConti(Model model, HttpServletRequest request, HttpServletResponse response) {

	List<MercatiCfgConti> list = mercatiCfgContiService.findAll(null, null);
	model.addAttribute("list", list);
	return "mercaticonfigurazione/listMercatiCfgConti";
    }

    @RequestMapping
    public String createMercatiCfgConti(Model model) {

	MercatiCfgConti mercatiCfgConti = new MercatiCfgConti();
	if (mercatiCfgConti.getConcessioniuso() == null) {
	    mercatiCfgConti.setConcessioniuso(new Concessioniuso());
	}
	if (mercatiCfgConti.getSoftware() == null) {
	    mercatiCfgConti.setSoftware(new Software());
	}
	if (mercatiCfgConti.getSoftware() == null) {
	    mercatiCfgConti.setSoftware(new Software());
	    mercatiCfgConti.getSoftware().setCodice(ORMHelper.getSoftware());
	}
	if (mercatiCfgConti.getMercatiCategorie() == null) {
	    mercatiCfgConti.setMercatiCategorie(new MercatiCategorie());
	}
	if (mercatiCfgConti.getPosteggiSettori() == null) {
	    mercatiCfgConti.setPosteggiSettori(new PosteggiSettori());
	}
	if (mercatiCfgConti.getConti() == null) {
	    mercatiCfgConti.setConti(new Conti());
	}
	if (mercatiCfgConti.getAttivita() == null) {
	    mercatiCfgConti.setAttivita(new Attivita());
	}
	mercatiCfgConti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	model.addAttribute("mercatiCfgConti", mercatiCfgConti);
	return "mercaticonfigurazione/formMercatiCfgConti";
    }

    @RequestMapping
    public String insertMercatiCfgConti(@ModelAttribute("mercatiCfgConti") MercatiCfgConti mercatiCfgConti, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeMercatiCfgConti(mercatiCfgConti);
	try {
	    mercatiCfgContiService.insert(mercatiCfgConti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiCfgConti, e);
	    fixRenderMercatiCfgConti(mercatiCfgConti);
	    return "mercaticonfigurazione/formMercatiCfgConti";
	}
	status.setComplete();
	return "redirect:viewMercatiCfgConti.htm?codice=" + mercatiCfgConti.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public void exportMercatiCfgConti(HttpServletRequest request, HttpServletResponse response) throws Exception {

	List<MercatiCfgConti> list = mercatiCfgContiService.findAll(null, null);
	StringBuilder sb = new StringBuilder();
	sb.append("categoria_mercato,settore_posteggio,conto,uso,cat_merc,inizio_val,fine_val,");
	sb.append("importo,calcola_mq");
	sb.append("\r\n");
	for (MercatiCfgConti agr : list) {
	    if (agr.getMercatiCategorie() != null) {
		sb.append("\"").append(formattaStringaCSV(agr.getMercatiCategorie().getDescrizione())).append("\",");
	    } else {
		sb.append("\"\",");
	    }
	    if (agr.getPosteggiSettori() != null) {
		sb.append("\"").append(formattaStringaCSV(agr.getPosteggiSettori().getSettore())).append("\",");
	    } else {
		sb.append("\"\",");
	    }
	    if (agr.getConti() != null) {
		sb.append("\"").append(formattaStringaCSV(agr.getConti().getDescrizione())).append("\",");
	    } else {
		sb.append("\"\",");
	    }
	    if (agr.getConcessioniuso() != null) {
		sb.append("\"").append(formattaStringaCSV(agr.getConcessioniuso().getDescrizione())).append("\",");
	    } else {
		sb.append("\"\",");
	    }
	    if (agr.getAttivita() != null) {
		sb.append("\"").append(formattaStringaCSV(agr.getAttivita().getIstat())).append("\",");
	    } else {
		sb.append("\"\",");
	    }
	    if (agr.getDatainizioval() != null) {
		sb.append("\"").append(formattaStringaCSV(Utilities.formatDate(agr.getDatainizioval(), false))).append("\",");
	    } else {
		sb.append("\"\",");
	    }
	    if (agr.getDatafineval() != null) {
		sb.append("\"").append(formattaStringaCSV(Utilities.formatDate(agr.getDatafineval(), false))).append("\",");
	    } else {
		sb.append("\"\",");
	    }
	    if (agr.getImporto() != null) {
		sb.append("\"").append(formattaStringaCSV(String.valueOf(agr.getImporto()).replace(".", ","))).append("\",");
	    } else {
		sb.append("\"\",");
	    }
	    sb.append("\"").append(formattaStringaCSV(BooleanUtils.isTrue(agr.getFlagMoltiplicaMq()) ? "Si" : "No")).append("\"");
	    sb.append("\r\n");
	}
	response.setHeader("Content-Disposition", "attachment; filename=\"configurazione_coefficienti.csv\"");
	response.setContentType("text/csv");
	IOUtils.copy(IOUtils.toInputStream(sb.toString()), response.getOutputStream());
    }

    private Object formattaStringaCSV(String valore) {

	return StringUtils.defaultString(valore).replace("\"", "\"\"");
    }

    private void fixRenderMercatiCfgConti(MercatiCfgConti entity) {

	if (entity.getAttivita() == null) {
	    entity.setAttivita(new Attivita());
	}
	if (entity.getConcessioniuso() == null) {
	    entity.setConcessioniuso(new Concessioniuso());
	}
	if (entity.getConti() == null) {
	    entity.setConti(new Conti());
	}
	if (entity.getMercatiCategorie() == null) {
	    entity.setMercatiCategorie(new MercatiCategorie());
	}
	if (entity.getPosteggiSettori() == null) {
	    entity.setPosteggiSettori(new PosteggiSettori());
	}
    }

    private void fixMergeMercatiCfgConti(MercatiCfgConti entity) {

	// IMPLEMENTATO NEL SERVICE
    }

    @RequestMapping
    public String viewMercatiCfgConti(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	MercatiCfgConti mercatiCfgConti = mercatiCfgContiService.findById(new PkId(codice));
	fixRenderMercatiCfgConti(mercatiCfgConti);
	model.addAttribute("mercatiCfgConti", mercatiCfgConti);
	return "mercaticonfigurazione/formMercatiCfgConti";
    }

    @RequestMapping
    public String updateMercatiCfgConti(@ModelAttribute("mercatiCfgConti") MercatiCfgConti mercatiCfgConti, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeMercatiCfgConti(mercatiCfgConti);
	try {
	    mercatiCfgContiService.update(mercatiCfgConti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiCfgConti, e);
	    fixRenderMercatiCfgConti(mercatiCfgConti);
	    return "mercaticonfigurazione/formMercatiCfgConti";
	}
	status.setComplete();
	return "redirect:viewMercatiCfgConti.htm?codice=" + mercatiCfgConti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteMercatiCfgConti(Model model, @ModelAttribute("mercatiCfgConti") MercatiCfgConti mercatiCfgConti, BindingResult result,
	    SessionStatus status) {

	try {
	    mercatiCfgContiService.delete(mercatiCfgConti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiCfgConti, e);
	    fixRenderMercatiCfgConti(mercatiCfgConti);
	    return "mercaticonfigurazione/formMercatiCfgConti";
	}
	return "redirect:listMercatiCfgConti.htm";
    }

    @Override
    protected void fixMergeEntityProperty(MercatiConfigurazione entity) {

	if (entity.getSettori() != null && entity.getSettori().getId() != null
		&& (entity.getSettori().getId().getCodicesettore() == null || entity.getSettori().getId().getCodicesettore().equals(""))) {
	    entity.setSettori(null);
	}
	if (entity.getCausaleAumento() != null && entity.getCausaleAumento().getId() != null
		&& (entity.getCausaleAumento().getId().getCodice() == null)) {
	    entity.setCausaleAumento(null);
	}
	if (entity.getCausaleCanone() != null && entity.getCausaleCanone().getId() != null
		&& (entity.getCausaleCanone().getId().getCodice() == null)) {
	    entity.setCausaleCanone(null);
	}
	if (entity.getCausaleDiminuzione() != null && entity.getCausaleDiminuzione().getId() != null
		&& (entity.getCausaleDiminuzione().getId().getCodice() == null)) {
	    entity.setCausaleDiminuzione(null);
	}
	if (entity.getCausaleTransazione() != null && entity.getCausaleTransazione().getId() != null
		&& (entity.getCausaleTransazione().getId().getCodice() == null)) {
	    entity.setCausaleTransazione(null);
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
	if (entity.getDyn2CampiByFkMerconfDyn2campiMerc() != null && entity.getDyn2CampiByFkMerconfDyn2campiMerc().getId() != null
		&& entity.getDyn2CampiByFkMerconfDyn2campiMerc().getId().getCodice() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiMerc(null);
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiMercUso() != null && entity.getDyn2CampiByFkMerconfDyn2campiMercUso().getId() != null
		&& entity.getDyn2CampiByFkMerconfDyn2campiMercUso().getId().getCodice() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiMercUso(null);
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiPost() != null && entity.getDyn2CampiByFkMerconfDyn2campiPost().getId() != null
		&& entity.getDyn2CampiByFkMerconfDyn2campiPost().getId().getCodice() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiPost(null);
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiAss() != null && entity.getDyn2CampiByFkMerconfDyn2campiAss().getId() != null
		&& entity.getDyn2CampiByFkMerconfDyn2campiAss().getId().getCodice() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiAss(null);
	}
	if (entity.getDyn2CampiByFkMCfgMercSpunt() != null && entity.getDyn2CampiByFkMCfgMercSpunt().getId() != null
		&& entity.getDyn2CampiByFkMCfgMercSpunt().getId().getCodice() == null) {
	    entity.setDyn2CampiByFkMCfgMercSpunt(null);
	}
	if (entity.getDyn2CampiByFkMCfgUsoSpunt() != null && entity.getDyn2CampiByFkMCfgUsoSpunt().getId() != null
		&& entity.getDyn2CampiByFkMCfgUsoSpunt().getId().getCodice() == null) {
	    entity.setDyn2CampiByFkMCfgUsoSpunt(null);
	}
	if (entity.getCausaleAcqRiottenimento() != null && entity.getCausaleAcqRiottenimento().getId() != null
		&& entity.getCausaleAcqRiottenimento().getId().getCodice() == null) {
	    entity.setCausaleAcqRiottenimento(null);
	}
	if (entity.getCausaleCessRiottenimento() != null && entity.getCausaleCessRiottenimento().getId() != null
		&& entity.getCausaleCessRiottenimento().getId().getCodice() == null) {
	    entity.setCausaleCessRiottenimento(null);
	}
	if (entity.getCausaleCessSistema() != null && entity.getCausaleCessSistema().getId() != null
		&& entity.getCausaleCessSistema().getId().getCodice() == null) {
	    entity.setCausaleCessSistema(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(MercatiConfigurazione entity) {

	if (entity.getSettori() == null) {
	    entity.setSettori(new Settori());
	}
	if (entity.getCausaleAumento() == null) {
	    entity.setCausaleAumento(new RegistrazioniCausali());
	}
	if (entity.getCausaleCanone() == null) {
	    entity.setCausaleCanone(new RegistrazioniCausali());
	}
	if (entity.getCausaleDiminuzione() == null) {
	    entity.setCausaleDiminuzione(new RegistrazioniCausali());
	}
	if (entity.getCausaleTransazione() == null) {
	    entity.setCausaleTransazione(new RegistrazioniCausali());
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
	if (entity.getDyn2CampiByFkMerconfDyn2campiMerc() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiMerc(new Dyn2Campi());
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiMercUso() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiMercUso(new Dyn2Campi());
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiPost() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiPost(new Dyn2Campi());
	}
	if (entity.getDyn2CampiByFkMerconfDyn2campiAss() == null) {
	    entity.setDyn2CampiByFkMerconfDyn2campiAss(new Dyn2Campi());
	}
	if (entity.getDyn2CampiByFkMCfgMercSpunt() == null) {
	    entity.setDyn2CampiByFkMCfgMercSpunt(new Dyn2Campi());
	}
	if (entity.getDyn2CampiByFkMCfgUsoSpunt() == null) {
	    entity.setDyn2CampiByFkMCfgUsoSpunt(new Dyn2Campi());
	}
	if (entity.getCausaleAcqRiottenimento() == null) {
	    entity.setCausaleAcqRiottenimento(new Concessionicausali());
	}
	if (entity.getCausaleCessRiottenimento() == null) {
	    entity.setCausaleCessRiottenimento(new Concessionicausali());
	}
	if (entity.getCausaleCessSistema() == null) {
	    entity.setCausaleCessSistema(new Concessionicausali());
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
