/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import java.util.ArrayList;
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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazione;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloFlusso;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAlberoproc;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAltridati;
import it.gruppoinit.pal.gp.core.domain.TipimovStcMapping;
import it.gruppoinit.pal.gp.core.domain.TipimovStcModelli;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ProtocolloConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.ProtocolloFlussoService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipimovStcAlberoprocService;
import it.gruppoinit.pal.gp.core.service.TipimovStcAltridatiService;
import it.gruppoinit.pal.gp.core.service.TipimovStcMappingService;
import it.gruppoinit.pal.gp.core.service.TipimovStcMappingService.TipoNotificaAutomatica;
import it.gruppoinit.pal.gp.core.service.TipimovStcModelliService;

/**
 * @author francescop
 * 
 */
@Controller
@SessionAttributes(value = { "tipimovStcMapping", "tipimovStcAltridati", "tipimovStcModelli", "tipimovStcAlberoproc" })
public class ParametriSTCController extends BaseController<TipimovStcMapping> {

    @Autowired
    private TipimovStcMappingService tipimovStcMappingService;
    @Autowired
    private TipimovStcAltridatiService tipimovStcAltridatiService;
    @Autowired
    private TipimovStcModelliService tipimovStcModelliService;
    @Autowired
    private TipimovStcAlberoprocService tipimovStcAlberoprocService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private ProtocolloFlussoService protocolloFlussoService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private ProtocolloConfigurazioneService protocolloConfigurazioneService;
    @Autowired
    private MailtipoService mailtipoService;

    @RequestMapping
    public ModelMap list(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento,
	    @RequestParam(required = false, value = "viewTab") String viewTab, HttpServletRequest request, HttpServletResponse response) {

	TipimovimentoId tipomovimentoId = new TipimovimentoId();
	tipomovimentoId.setTipomovimento(idtipomovimento);
	List<TipimovStcMapping> tipiMovStcMaplist = tipimovStcMappingService.findByTipimovimento(tipomovimentoId);
	List<TipimovStcAltridati> tipiMovStcAltriDatilist = tipimovStcAltridatiService.findByTipimovimento(tipomovimentoId);
	List<TipimovStcModelli> tipimovStcModellilist = tipimovStcModelliService.findByTipimovimento(tipomovimentoId);
	List<TipimovStcAlberoproc> tipimovStcAlberoproclist = tipimovStcAlberoprocService.findByTipimovimento(tipomovimentoId);
	ModelMap model = new ModelMap(tipiMovStcMaplist);
	model.addAttribute("tipimovimento", tipiMovimentoService.findById(tipomovimentoId));
	model.addAttribute("idtipomovimento", idtipomovimento);
	model.addAttribute("tipiMovStcMaplist", tipiMovStcMaplist);
	model.addAttribute("tipiMovStcAltriDatilist", tipiMovStcAltriDatilist);
	model.addAttribute("tipimovStcModellilist", tipimovStcModellilist);
	model.addAttribute("tipimovStcAlberoproclist", tipimovStcAlberoproclist);
	if (viewTab != null) {
	    model.addAttribute("VIEW_TAB", viewTab);
	} else {
	    model.addAttribute("VIEW_TAB", "TAB_MAPPING");
	}
	return model;
    }

    @RequestMapping
    public String createMapping(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model, HttpServletRequest request) {

	TipimovStcMapping tipimovStcMapping = new TipimovStcMapping();
	Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(ORMHelper.getIdcomune(), idtipomovimento));
	tipimovStcMapping.setTipimovimento(tipimovimento);
	prepareView(model, tipimovStcMapping, true, request);
	model.addAttribute("idtipomovimento", idtipomovimento);
	setPageAttributes(model);
	return "parametristc/formmapping";
    }

    private void prepareView(Model model, TipimovStcMapping tipimovStcMapping, boolean isCreate, HttpServletRequest request) {

	List<Amministrazioni> amministrazioniList = amministrazioniService.findAmministrazioniSTC();
	List<ProtocolloFlusso> protocolloFlussos = protocolloFlussoService.findByTipiFlussi(WebConstants.FLUSSO_PARTENZA, null,
		WebConstants.FLUSSO_INTERNO);
	//fabrizioc: Attenzione! popolo la entity solo se sono in create e la entity non proviene dal DB altrimenti Hibernate aggiorna il DB
	if (isCreate) {
	    // Recupero l'amministrazione configurata di default sulla verticalizzaizone PROTOCOLLO_ATTIVO
	    boolean isVerticalizzazioneProtocolloAttiva = verticalizzazioniService
		    .isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE);
	    if (isVerticalizzazioneProtocolloAttiva) {
		Verticalizzazioniparametri verticalizzazioniparametroAMMINISTRAZIONE = verticalizzazioniService.getVerticalizzazioniparametri(
			VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
			VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CODICEAMMINISTRAZIONEDEFAULT);
		if (verticalizzazioniparametroAMMINISTRAZIONE != null
			&& EntityUtils.getNestedProperty(tipimovStcMapping.getAmministrazioneMittente(), "id.codice") == null
			&& StringUtils.isNotBlank(verticalizzazioniparametroAMMINISTRAZIONE.getValore())) {
		    Amministrazioni amministrazione = amministrazioniService
			    .findById(new PkId(Integer.parseInt(verticalizzazioniparametroAMMINISTRAZIONE.getValore())));
		    if (EntityUtils.getNestedProperty(amministrazione, "id.codice") == null) {
			StringBuffer messerr = new StringBuffer(
				"Attenzione l'operazione non può essere completata. Nella verticalizzazione PROTOCOOLLO_ATTIVO");
			messerr.append(" è stata configurata un amministrazione di default (CODICEAMMINISTRAZIONEDEFAULT =")
				.append(verticalizzazioniparametroAMMINISTRAZIONE.getValore())
				.append(") inesistente. Inserirne una corretta e riprovare");
			throw new RuntimeException(messerr.toString());
		    }
		    tipimovStcMapping.setAmministrazioneMittente(amministrazione);
		}
	    }
	    // Recupero l'oggetto mail tipo in configurazione
	    ProtocolloConfigurazione protocolloConfigurazione = protocolloConfigurazioneService
		    .findById(new ProtocolloConfigurazioneId(ORMHelper.getSoftware()));
	    if (protocolloConfigurazione != null
		    && EntityUtils.getNestedProperty(protocolloConfigurazione.getMailtipoByFkMovimento(), "id.codice") != null) {
		Mailtipo mailtipo = mailtipoService.findById(protocolloConfigurazione.getMailtipoByFkMovimento().getId());
		tipimovStcMapping.setMailtipo(mailtipo);
	    }
	}
	model.addAttribute("tipimovStcMapping", tipimovStcMapping);
	model.addAttribute("amministrazioniList", amministrazioniList);
	model.addAttribute("protocolloFlussos", protocolloFlussos);
	TipoNotificaAutomatica tipo = tipimovStcMappingService.decodeTipoNotifica(tipimovStcMapping);
	if (tipo.equals(TipoNotificaAutomatica.NOTIFICA_AUTOMATICA) || tipo.equals(TipoNotificaAutomatica.NOTIFICA_AUTOMATICA_INSERIMENTO)) {
	    request.setAttribute("viewFlagAllegatiEdoc", true);
	} else {
	    request.setAttribute("viewFlagAllegatiEdoc", false);
	}
	if (BooleanUtils.isTrue(tipimovStcMapping.getFlagProtocolla())
		&& (tipo.equals(TipoNotificaAutomatica.NOTIFICA_AUTOMATICA) || tipo.equals(TipoNotificaAutomatica.NOTIFICA_AUTOMATICA_INSERIMENTO))) {
	    request.setAttribute("viewParametriProtocollo", true);
	} else {
	    request.setAttribute("viewParametriProtocollo", false);
	}
    }

    @RequestMapping
    public String insertMapping(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model,
	    @ModelAttribute("tipimovStcMapping") TipimovStcMapping tipimovStcMapping, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(tipimovStcMapping);
	if (EntityUtils.getNestedProperty(tipimovStcMapping.getAmministrazioneMittente(), "id.codice") != null) {
	    Amministrazioni amministrazioniMitt = amministrazioniService.findById(tipimovStcMapping.getAmministrazioneMittente().getId());
	    tipimovStcMapping.setAmministrazioneMittente(amministrazioniMitt);
	}
	if (EntityUtils.getNestedProperty(tipimovStcMapping.getMailtipo(), "id.codice") != null) {
	    Mailtipo mailtipo = mailtipoService.findById(tipimovStcMapping.getMailtipo().getId());
	    tipimovStcMapping.setMailtipo(mailtipo);
	}
	if (tipimovStcMapping.getProtocolloFlusso() != null && StringUtils.isNotBlank(tipimovStcMapping.getProtocolloFlusso().getCodice())) {
	    ProtocolloFlusso protocolloFlusso = protocolloFlussoService.findById(tipimovStcMapping.getProtocolloFlusso().getCodice());
	    tipimovStcMapping.setProtocolloFlusso(protocolloFlusso);
	}
	try {
	    tipimovStcMappingService.insert(tipimovStcMapping);
	} catch (Exception e) {
	    tipimovStcMapping.getId().setCodice(null);
	    copyErrorsToBindingResult(result, tipimovStcMapping, e);
	    fixRenderEntityProperty(tipimovStcMapping);
	    prepareView(model, tipimovStcMapping, false, request);
	    model.addAttribute("idtipomovimento", idtipomovimento);
	    return "parametristc/formmapping";
	}
	status.setComplete();
	return "redirect:viewMapping.htm?tipimovimento.idtipomovimento=" + idtipomovimento + "&id=" + tipimovStcMapping.getId().getCodice() +
	       "&status_msg=01";
    }

    @RequestMapping
    public String updateMapping(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model,
	    @ModelAttribute("tipimovStcMapping") TipimovStcMapping tipimovStcMapping, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(tipimovStcMapping);
	try {
	    tipimovStcMappingService.update(tipimovStcMapping);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovStcMapping, e);
	    fixRenderEntityProperty(tipimovStcMapping);
	    prepareView(model, tipimovStcMapping, false, request);
	    model.addAttribute("idtipomovimento", idtipomovimento);
	    return "parametristc/formmapping";
	}
	status.setComplete();
	return "redirect:viewMapping.htm?tipimovimento.idtipomovimento=" + idtipomovimento + "&id=" + tipimovStcMapping.getId().getCodice() +
	       "&status_msg=02";
    }

    @RequestMapping
    public String deleteMapping(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model,
	    @ModelAttribute("tipimovStcMapping") TipimovStcMapping tipimovStcMapping, BindingResult result, SessionStatus status) {

	TipimovStcMapping objToDelete = tipimovStcMappingService.findById(tipimovStcMapping.getId());
	try {
	    tipimovStcMappingService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    List<Amministrazioni> amministrazioniList = amministrazioniService.findAmministrazioniSTC();
	    model.addAttribute("amministrazioniList", amministrazioniList);
	    fixRenderEntityProperty(tipimovStcMapping);
	    model.addAttribute("idtipomovimento", idtipomovimento);
	    return "parametristc/formmapping";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    @RequestMapping
    public String viewMapping(@RequestParam("id") Integer idMapping, @RequestParam("tipimovimento.idtipomovimento") String idtipomovimento,
	    Model model, HttpServletRequest request) {

	PkId id = new PkId(idMapping);
	TipimovStcMapping tipimovStcMapping = tipimovStcMappingService.findById(id);
	//TipoNotificaAutomatica tipo = tipimovStcMappingService.decodeTipoNotifica(tipimovStcMapping);
	prepareView(model, tipimovStcMapping, false, request);
	fixRenderEntityProperty(tipimovStcMapping);
	model.addAttribute("VIEW_TAB", "TAB_MAPPING");
	setPageAttributes(model);
	model.addAttribute("idtipomovimento", idtipomovimento);
	//	if (tipo.equals(TipoNotificaAutomatica.NOTIFICA_AUTOMATICA) || tipo.equals(TipoNotificaAutomatica.NOTIFICA_AUTOMATICA_INSERIMENTO)) {
	//	    request.setAttribute("viewFlagAllegatiEdoc", true);
	//	} else {
	//	    request.setAttribute("viewFlagAllegatiEdoc", false);
	//	}
	//	if (BooleanUtils.isTrue(tipimovStcMapping.getFlagProtocolla()
	//		&& (tipo.equals(TipoNotificaAutomatica.NOTIFICA_AUTOMATICA) || tipo.equals(TipoNotificaAutomatica.NOTIFICA_AUTOMATICA_INSERIMENTO)))) {
	//	    request.setAttribute("viewParametriProtocollo", true);
	//	} else {
	//	    request.setAttribute("viewParametriProtocollo", false);
	//	}
	return "parametristc/formmapping";
    }

    @RequestMapping
    public String createAltridati(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model) {

	TipimovStcAltridati tipimovStcAltridati = new TipimovStcAltridati();
	Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(ORMHelper.getIdcomune(), idtipomovimento));
	tipimovStcAltridati.setTipimovimento(tipimovimento);
	List<Amministrazioni> amministrazioniList = findAmministrazioniTipimov(idtipomovimento);
	model.addAttribute("amministrazioniList", amministrazioniList);
	model.addAttribute("tipimovStcAltridati", tipimovStcAltridati);
	model.addAttribute("idtipomovimento", idtipomovimento);
	setPageAttributes(model);
	return "parametristc/formaltridati";
    }

    @RequestMapping
    public String insertAltridati(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model,
	    @ModelAttribute("tipimovStcAltridati") TipimovStcAltridati tipimovStcAltridati, BindingResult result, SessionStatus status) {

	fixMergeEntityPropertyAltridati(tipimovStcAltridati);
	try {
	    tipimovStcAltridatiService.insert(tipimovStcAltridati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovStcAltridati, e);
	    List<Amministrazioni> amministrazioniList = findAmministrazioniTipimov(idtipomovimento);
	    model.addAttribute("amministrazioniList", amministrazioniList);
	    fixRenderEntityPropertyAltridati(tipimovStcAltridati);
	    model.addAttribute("idtipomovimento", idtipomovimento);
	    return "parametristc/formaltridati";
	}
	status.setComplete();
	return "redirect:viewAltridati.htm?tipimovimento.idtipomovimento=" + idtipomovimento + "&id=" + tipimovStcAltridati.getId().getCodice() +
	       "&status_msg=01";
    }

    @RequestMapping
    public String updateAltridati(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model,
	    @ModelAttribute("tipimovStcAltridati") TipimovStcAltridati tipimovStcAltridati, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityPropertyAltridati(tipimovStcAltridati);
	try {
	    tipimovStcAltridatiService.update(tipimovStcAltridati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovStcAltridati, e);
	    fixRenderEntityPropertyAltridati(tipimovStcAltridati);
	    model.addAttribute("idtipomovimento", idtipomovimento);
	    List<Amministrazioni> amministrazioniList = findAmministrazioniTipimov(idtipomovimento);
	    model.addAttribute("amministrazioniList", amministrazioniList);
	    return "parametristc/formaltridati";
	}
	status.setComplete();
	return "redirect:viewAltridati.htm?tipimovimento.idtipomovimento=" + idtipomovimento + "&id=" + tipimovStcAltridati.getId().getCodice() +
	       "&status_msg=02";
    }

    @RequestMapping
    public String deleteAltridati(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model,
	    @ModelAttribute("tipimovStcAltridati") TipimovStcAltridati tipimovStcAltridati, BindingResult result, SessionStatus status) {

	TipimovStcAltridati objToDelete = tipimovStcAltridatiService.findById(tipimovStcAltridati.getId());
	List<Amministrazioni> amministrazioniList = findAmministrazioniTipimov(idtipomovimento);
	model.addAttribute("amministrazioniList", amministrazioniList);
	try {
	    tipimovStcAltridatiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityPropertyAltridati(tipimovStcAltridati);
	    model.addAttribute("idtipomovimento", idtipomovimento);
	    return "parametristc/formaltridati";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    @RequestMapping
    public String viewAltridati(@RequestParam("id") Integer idMapping, @RequestParam("tipimovimento.idtipomovimento") String idtipomovimento,
	    Model model, HttpServletRequest request) {

	PkId id = new PkId(idMapping);
	TipimovStcAltridati tipimovStcAltridati = tipimovStcAltridatiService.findById(id);
	fixRenderEntityPropertyAltridati(tipimovStcAltridati);
	List<Amministrazioni> amministrazioniList = findAmministrazioniTipimov(idtipomovimento);
	model.addAttribute("tipimovStcAltridati", tipimovStcAltridati);
	model.addAttribute("amministrazioniList", amministrazioniList);
	request.setAttribute("VIEW_TAB", "TAB_ALTRIDATI");
	setPageAttributes(model);
	model.addAttribute("idtipomovimento", idtipomovimento);
	return "parametristc/formaltridati";
    }

    @RequestMapping
    public String createModelli(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model) {

	TipimovStcModelli tipimovStcModelli = new TipimovStcModelli();
	Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(ORMHelper.getIdcomune(), idtipomovimento));
	tipimovStcModelli.setTipimovimento(tipimovimento);
	List<Amministrazioni> amministrazioniList = findAmministrazioniTipimov(idtipomovimento);
	fixRenderEntityPropertyModelli(tipimovStcModelli);
	model.addAttribute("tipimovStcModelli", tipimovStcModelli);
	model.addAttribute("idtipomovimento", idtipomovimento);
	model.addAttribute("amministrazioniList", amministrazioniList);
	setPageAttributes(model);
	return "parametristc/formmodelli";
    }

    @RequestMapping
    public String insertModelli(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model,
	    @ModelAttribute("tipimovStcModelli") TipimovStcModelli tipimovStcModelli, BindingResult result, SessionStatus status) {

	fixMergeEntityPropertyModelli(tipimovStcModelli);
	try {
	    tipimovStcModelliService.insert(tipimovStcModelli);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovStcModelli, e);
	    fixRenderEntityPropertyModelli(tipimovStcModelli);
	    List<Amministrazioni> amministrazioniList = findAmministrazioniTipimov(idtipomovimento);
	    model.addAttribute("amministrazioniList", amministrazioniList);
	    model.addAttribute("idtipomovimento", idtipomovimento);
	    return "parametristc/formmodelli";
	}
	status.setComplete();
	return "redirect:viewModelli.htm?tipimovimento.idtipomovimento=" + idtipomovimento + "&id=" + tipimovStcModelli.getId().getCodice() +
	       "&status_msg=01";
    }

    @RequestMapping
    public String updateModelli(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model,
	    @ModelAttribute("tipimovStcModelli") TipimovStcModelli tipimovStcModelli, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityPropertyModelli(tipimovStcModelli);
	try {
	    tipimovStcModelliService.update(tipimovStcModelli);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovStcModelli, e);
	    fixRenderEntityPropertyModelli(tipimovStcModelli);
	    model.addAttribute("idtipomovimento", idtipomovimento);
	    List<Amministrazioni> amministrazioniList = findAmministrazioniTipimov(idtipomovimento);
	    model.addAttribute("amministrazioniList", amministrazioniList);
	    return "parametristc/formmodelli";
	}
	status.setComplete();
	return "redirect:viewModelli.htm?tipimovimento.idtipomovimento=" + idtipomovimento + "&id=" + tipimovStcModelli.getId().getCodice() +
	       "&status_msg=02";
    }

    @RequestMapping
    public String deleteModelli(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model,
	    @ModelAttribute("tipimovStcModelli") TipimovStcModelli tipimovStcModelli, BindingResult result, SessionStatus status) {

	TipimovStcModelli objToDelete = tipimovStcModelliService.findById(tipimovStcModelli.getId());
	try {
	    tipimovStcModelliService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    List<Amministrazioni> amministrazioniList = findAmministrazioniTipimov(idtipomovimento);
	    model.addAttribute("amministrazioniList", amministrazioniList);
	    fixRenderEntityPropertyModelli(tipimovStcModelli);
	    model.addAttribute("idtipomovimento", idtipomovimento);
	    return "parametristc/formmodelli";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    @RequestMapping
    public String viewModelli(@RequestParam("id") Integer idModelli, @RequestParam("tipimovimento.idtipomovimento") String idtipomovimento,
	    Model model, HttpServletRequest request) {

	PkId id = new PkId(idModelli);
	TipimovStcModelli tipimovStcModelli = tipimovStcModelliService.findById(id);
	fixRenderEntityPropertyModelli(tipimovStcModelli);
	List<Amministrazioni> amministrazioniList = findAmministrazioniTipimov(idtipomovimento);
	model.addAttribute("tipimovStcModelli", tipimovStcModelli);
	model.addAttribute("amministrazioniList", amministrazioniList);
	model.addAttribute("VIEW_TAB", "TAB_MODELLI");
	setPageAttributes(model);
	model.addAttribute("idtipomovimento", idtipomovimento);
	return "parametristc/formmodelli";
    }

    @RequestMapping
    public String createAlberoproc(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model) {

	TipimovStcAlberoproc tipimovStcAlberoproc = new TipimovStcAlberoproc();
	Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(ORMHelper.getIdcomune(), idtipomovimento));
	tipimovStcAlberoproc.setTipimovimento(tipimovimento);
	List<Amministrazioni> amministrazioniList = findAmministrazioniTipimov(idtipomovimento);
	fixRenderEntityPropertyAlberoproc(tipimovStcAlberoproc);
	model.addAttribute("tipimovStcAlberoproc", tipimovStcAlberoproc);
	model.addAttribute("idtipomovimento", idtipomovimento);
	model.addAttribute("amministrazioniList", amministrazioniList);
	setPageAttributes(model);
	return "parametristc/formalberoproc";
    }

    @RequestMapping
    public String insertAlberoproc(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model,
	    @ModelAttribute("tipimovStcAlberoproc") TipimovStcAlberoproc tipimovStcAlberoproc, BindingResult result, SessionStatus status) {

	fixMergeEntityPropertyAlberoproc(tipimovStcAlberoproc);
	try {
	    tipimovStcAlberoprocService.insert(tipimovStcAlberoproc);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovStcAlberoproc, e);
	    fixRenderEntityPropertyAlberoproc(tipimovStcAlberoproc);
	    List<Amministrazioni> amministrazioniList = findAmministrazioniTipimov(idtipomovimento);
	    model.addAttribute("amministrazioniList", amministrazioniList);
	    model.addAttribute("idtipomovimento", idtipomovimento);
	    return "parametristc/formalberoproc";
	}
	status.setComplete();
	return "redirect:viewAlberoproc.htm?tipimovimento.idtipomovimento=" + idtipomovimento + "&id=" + tipimovStcAlberoproc.getId().getCodice() +
	       "&status_msg=01";
    }

    @RequestMapping
    public String updateAlberoproc(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model,
	    @ModelAttribute("tipimovStcAlberoproc") TipimovStcAlberoproc tipimovStcAlberoproc, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityPropertyAlberoproc(tipimovStcAlberoproc);
	try {
	    tipimovStcAlberoprocService.update(tipimovStcAlberoproc);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovStcAlberoproc, e);
	    fixRenderEntityPropertyAlberoproc(tipimovStcAlberoproc);
	    model.addAttribute("idtipomovimento", idtipomovimento);
	    List<Amministrazioni> amministrazioniList = findAmministrazioniTipimov(idtipomovimento);
	    model.addAttribute("amministrazioniList", amministrazioniList);
	    return "parametristc/formalberoproc";
	}
	status.setComplete();
	return "redirect:viewAlberoproc.htm?tipimovimento.idtipomovimento=" + idtipomovimento + "&id=" + tipimovStcAlberoproc.getId().getCodice() +
	       "&status_msg=02";
    }

    @RequestMapping
    public String deleteAlberoproc(@RequestParam("tipimovimento.idtipomovimento") String idtipomovimento, Model model,
	    @ModelAttribute("tipimovStcAlberoproc") TipimovStcAlberoproc tipimovStcAlberoproc, BindingResult result, SessionStatus status) {

	TipimovStcAlberoproc objToDelete = tipimovStcAlberoprocService.findById(tipimovStcAlberoproc.getId());
	try {
	    tipimovStcAlberoprocService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    List<Amministrazioni> amministrazioniList = findAmministrazioniTipimov(idtipomovimento);
	    model.addAttribute("amministrazioniList", amministrazioniList);
	    fixRenderEntityPropertyAlberoproc(tipimovStcAlberoproc);
	    model.addAttribute("idtipomovimento", idtipomovimento);
	    return "parametristc/formalberoproc";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    @RequestMapping
    public String viewAlberoproc(@RequestParam("id") Integer idAlberoproc, @RequestParam("tipimovimento.idtipomovimento") String idtipomovimento,
	    Model model, HttpServletRequest request) {

	PkId id = new PkId(idAlberoproc);
	TipimovStcAlberoproc tipimovStcAlberoproc = tipimovStcAlberoprocService.findById(id);
	fixRenderEntityPropertyAlberoproc(tipimovStcAlberoproc);
	List<Amministrazioni> amministrazioniList = findAmministrazioniTipimov(idtipomovimento);
	model.addAttribute("tipimovStcAlberoproc", tipimovStcAlberoproc);
	model.addAttribute("amministrazioniList", amministrazioniList);
	model.addAttribute("VIEW_TAB", "TAB_ALBEROPROC");
	setPageAttributes(model);
	model.addAttribute("idtipomovimento", idtipomovimento);
	return "parametristc/formalberoproc";
    }

    @Override
    protected void fixMergeEntityProperty(TipimovStcMapping entity) {

	if (StringUtils.isBlank(entity.getCodiceAttDest())) {
	    entity.setCodiceAttDest(entity.getTipimovimento().getId().getTipomovimento());
	}
    }

    @Override
    protected void fixRenderEntityProperty(TipimovStcMapping entity) {

	if (entity.getAmministrazioneMittente() == null) {
	    entity.setAmministrazioneMittente(new Amministrazioni());
	}
	if (entity.getAmministrazioni() == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
	if (entity.getProtocolloFlusso() == null) {
	    entity.setProtocolloFlusso(new ProtocolloFlusso());
	}
	if (entity.getMailtipo() == null) {
	    entity.setMailtipo(new Mailtipo());
	}
    }

    protected void fixMergeEntityPropertyAltridati(TipimovStcAltridati entity) {

    }

    protected void fixRenderEntityPropertyAltridati(TipimovStcAltridati entity) {

    }

    protected void fixMergeEntityPropertyModelli(TipimovStcModelli entity) {

	if (entity.getDyn2Modellit() != null && entity.getDyn2Modellit().getId() != null && entity.getDyn2Modellit().getId().getCodice() == null) {
	    entity.setDyn2Modellit(null);
	}
    }

    protected void fixRenderEntityPropertyModelli(TipimovStcModelli entity) {

	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
	if (entity.getTipimovimento() == null) {
	    entity.setTipimovimento(new Tipimovimento());
	}
    }

    private void fixMergeEntityPropertyAlberoproc(TipimovStcAlberoproc entity) {

    }

    private void fixRenderEntityPropertyAlberoproc(TipimovStcAlberoproc entity) {

	if (entity.getTipimovimento() == null) {
	    entity.setTipimovimento(new Tipimovimento());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    //    private List<Amministrazioni> trovaAmministrazioniMapping() {
    //
    //	List<Amministrazioni> amministrazionis = amministrazioniService.findByAmministrazione("", false);
    //	List<Amministrazioni> result = new ArrayList<Amministrazioni>();
    //	for (Amministrazioni amministrazioni : amministrazionis) {
    //	    String idEnte = amministrazioni.getStcIdente();
    //	    String idSportello = amministrazioni.getStcIdsportello();
    //	    if (!((null == idEnte || idEnte.equals("")) && (null == idSportello || idSportello.equals("")))) {
    //		result.add(amministrazioni);
    //	    }
    //	}
    //	return result;
    //    }
    private List<Amministrazioni> findAmministrazioniTipimov(String idtipomovimento) {

	TipimovimentoId id = new TipimovimentoId();
	id.setTipomovimento(idtipomovimento);
	List<TipimovStcMapping> listaMapping = tipimovStcMappingService.findByTipimovimento(id);
	List<Amministrazioni> result = new ArrayList<Amministrazioni>();
	for (TipimovStcMapping tipimovStcMapping : listaMapping) {
	    result.add(tipimovStcMapping.getAmministrazioni());
	}
	return result;
    }
}
