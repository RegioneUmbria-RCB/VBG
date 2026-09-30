package it.gruppoinit.pal.gp.backoffice.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtTprofilassegnazione;
import it.gruppoinit.pal.gp.core.domain.ProtocolloFlusso;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.ResponsabilicomuniId;
import it.gruppoinit.pal.gp.core.domain.Responsabiliruoli;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliruoliId;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.ResponsabilisoftwareId;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipiresponsabili;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.helper.ConfigurazioneUtenteHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ResponsabiliCommand;
import it.gruppoinit.pal.gp.core.domain.web.ScadenzarioOperatoreChiaveValore;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.ProtocolloFlussoService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.RuoliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiresponsabiliService;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord.TIPO_OPERAZIONE;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Controller
@SessionAttributes("responsabile")
public class ResponsabiliController extends BaseController<Responsabili> {

    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private TipiresponsabiliService tipiresponsabiliService;
    @Autowired
    private RuoliService ruoliService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private ProtocolloFlussoService protocolloFlussoService;
    private static final Logger log = LoggerFactory.getLogger(ResponsabiliController.class);

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Responsabili> responsabili = responsabiliService.findAll(null, null);
	ModelMap model = new ModelMap(responsabili);
	boolean export = createJMesaExport(request, response, responsabili);
	if (export)
	    return null;
	model.addAttribute("responsabili", responsabili);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("responsabile") ResponsabiliCommand responsabile, BindingResult result, SessionStatus status, Model model) {

	Responsabili objToDelete = responsabiliService.findById(new PkId(responsabile.getEntity().getId().getCodice()));
	try {
	    responsabiliService.delete(objToDelete);
	    LoggerUpdaterecord.log(getMessaggio(LoggerUpdaterecord.TIPO_OPERAZIONE.CANCELLAZIONE, objToDelete),
		    getCurrentlyAuthenticatedUserDetails());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, e);
	    fixRenderEntityProperty(responsabile.getEntity());
	    return "responsabili/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    private String getMessaggio(TIPO_OPERAZIONE tipo, Responsabili responsabile) {

	return Utilities.formatMessage(
		"#RESPONSABILI# {0} operatore ''{1}'', userid ''{4}'', isAmministratore ''{2}'', isAmministratoreSoftware ''{3}''", tipo,
		responsabile, responsabile.getAmministratore(), responsabile.getAmministratoresoftware(), responsabile.getUserid());
    }

    @RequestMapping
    public String insert(@ModelAttribute("responsabile") ResponsabiliCommand responsabile, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	responsabile.getEntity().setAmministratore("0");
	responsabile.getEntity().setAmministratoresoftware("0");
	if (responsabile.getEntity().isAmministratoreTransient()) {
	    responsabile.getEntity().setAmministratore("1");
	} else {
	    if (responsabile.getEntity().isAmministratoresoftwareTransient()) {
		responsabile.getEntity().setAmministratoresoftware("1");
	    }
	}
	try {
	    responsabiliService.insert(responsabile.getEntity());
	    LoggerUpdaterecord.log(getMessaggio(LoggerUpdaterecord.TIPO_OPERAZIONE.INSERIMENTO, responsabile.getEntity()),
		    getCurrentlyAuthenticatedUserDetails());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, responsabile.getEntity(), true, e);
	    fixRenderEntityProperty(responsabile.getEntity());
	    setPageAttributes(model);
	    return "responsabili/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + responsabile.getEntity().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("responsabile") ResponsabiliCommand responsabile, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	responsabile.getEntity().setAmministratore("0");
	responsabile.getEntity().setAmministratoresoftware("0");
	if (responsabile.getEntity().isAmministratoreTransient()) {
	    responsabile.getEntity().setAmministratore("1");
	} else {
	    if (responsabile.getEntity().isAmministratoresoftwareTransient()) {
		responsabile.getEntity().setAmministratoresoftware("1");
	    }
	}
	try {
	    responsabiliService.update(responsabile.getEntity());
	    LoggerUpdaterecord.log(getMessaggio(LoggerUpdaterecord.TIPO_OPERAZIONE.AGGIORNAMENTO, responsabile.getEntity()),
		    getCurrentlyAuthenticatedUserDetails());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, responsabile.getEntity(), true, e);
	    fixRenderEntityProperty(responsabile.getEntity());
	    setPageAttributes(model);
	    return "responsabili/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + responsabile.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	ResponsabiliCommand responsabile = new ResponsabiliCommand();
	responsabile.getEntity().setAmministratoreTransient(false);
	responsabile.getEntity().setAmministratoresoftwareTransient(false);
	responsabile.setResponsabilisoftwareList(getListResponsabilisoftware(responsabile.getEntity()));
	responsabile.setResponsabilicomuniList(getListResponsabilicomuni(responsabile.getEntity()));
	fixRenderEntityProperty(responsabile.getEntity());
	model.addAttribute("responsabile", responsabile);
	setPageAttributes(model);
	return "responsabili/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Responsabili entity = responsabiliService.findById(id);
	if (StringUtils.defaultString(entity.getAmministratore(), "0").equals("1")) {
	    entity.setAmministratoreTransient(true);
	} else {
	    entity.setAmministratoreTransient(false);
	}
	if (StringUtils.defaultString(entity.getAmministratoresoftware(), "0").equals("1")) {
	    entity.setAmministratoresoftwareTransient(true);
	} else {
	    entity.setAmministratoresoftwareTransient(false);
	}
	fixRenderEntityProperty(entity);
	ResponsabiliCommand responsabile = new ResponsabiliCommand();
	responsabile.setEntity(entity);
	responsabile.setResponsabilisoftwareList(getListResponsabilisoftware(entity));
	responsabile.setResponsabilicomuniList(getListResponsabilicomuni(entity));
	model.addAttribute("responsabile", responsabile);
	setPageAttributes(model);
	return "responsabili/form";
    }

    @RequestMapping
    public String viewParametriscadenzario(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Responsabili entity = responsabiliService.findById(id);
	ResponsabiliCommand responsabile = new ResponsabiliCommand();
	responsabile.setEntity(entity);
	responsabile.setResponsabilisoftwareList(getListResponsabilisoftware(entity));
	responsabile.setResponsabilicomuniList(getListResponsabilicomuni(entity));
	if (entity.getScadOperatoreId() != null) {
	    ScadenzarioOperatoreChiaveValore scadOperatoreCvb = getScadOperatore(entity);
	    responsabile.setScadOperatore(scadOperatoreCvb);
	}
	model.addAttribute("responsabile", responsabile);
	setPageAttributes(model);
	setScadenzarioAttribute(responsabile, model, request);
	fixRenderEntityProperty(entity);
	return "responsabili/formScadenzario";
    }

    private ScadenzarioOperatoreChiaveValore getScadOperatore(Responsabili entity) {

	Responsabili scadOperatore = responsabiliService.findById(new PkId(entity.getScadOperatoreId()));
	ScadenzarioOperatoreChiaveValore scadOperatoreCvb = new ScadenzarioOperatoreChiaveValore();
	scadOperatoreCvb.setChiave(entity.getScadOperatoreId());
	scadOperatoreCvb.setValore(scadOperatore.getResponsabile());
	return scadOperatoreCvb;
    }

    private void setScadenzarioAttribute(ResponsabiliCommand responsabili, Model model, HttpServletRequest request) {

	List<Software> softwares = softwareService.findSoftwareAbilitati(responsabili.getEntity(), true);
	model.addAttribute("softwares", softwares);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_DA_EFFETTUARE, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_DA_VISIONARE, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_NON_NOTIFICATI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_RICHIESTE_FO, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_EVENTI_NON_LETTI, "1", request);
	//gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCADENZARIO_ORDINAMENTO_DATA, "DESC", request);
	//
	ConfigurazioneutenteId id = new ConfigurazioneutenteId(responsabili.getEntity().getId().getCodice(),
		WebConstants.CONF_UTENTE_PAGINA_CENTRALE);
	Configurazioneutente c = configurazioneutenteService.findById(id);
	if (c != null) {
	    request.setAttribute(WebConstants.CONF_UTENTE_PAGINA_CENTRALE, StringUtils.defaultString(c.getValore()).trim());
	}
	ConfigurazioneutenteId idConfigurazioneOrdinamentoDate = new ConfigurazioneutenteId(responsabili.getEntity().getId().getCodice(),
		WebConstants.CONF_UTENTE_SCADENZARIO_ORDINAMENTO_DATA);
	Configurazioneutente configurazioneutenteOrdinamentoDate = configurazioneutenteService.findById(idConfigurazioneOrdinamentoDate);
	if (configurazioneutenteOrdinamentoDate != null) {
	    // responsabili.setValoreOrdinamentoData(configurazioneutenteOrdinamentoDate.getValore());
	    responsabili.setValoreOrdinamentoData(configurazioneutenteOrdinamentoDate.getValore());
	} else {
	    responsabili.setValoreOrdinamentoData("ASC");
	}
	model.addAttribute("isBatchScadenzarioPage", Boolean.FALSE);
	/**
	 * Parametri per la gestione della visualizzazione delle colonne nelle tabelle "Movimenti effettuati","Movimenti
	 * da visionare" "Movimenti visionati","Nuove domanda da STC"
	 *
	 */
	boolean isVisualizzaAlemnoUnaColonna = false;
	ConfigurazioneUtenteHelper configurazioneUtenteStato = gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_STATO, "0",
		false);
	isVisualizzaAlemnoUnaColonna = configurazioneUtenteStato.getValore().equalsIgnoreCase("1") ? true : false;
	//
	ConfigurazioneUtenteHelper configurazioneUtenteSoftware = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_SOFTWARE, "0", false);
	isVisualizzaAlemnoUnaColonna = (configurazioneUtenteSoftware.getValore().equalsIgnoreCase("1") || isVisualizzaAlemnoUnaColonna) ? true
		: false;
	//
	ConfigurazioneUtenteHelper configurazioneUtenteIntervento = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_INTERVENTO, "0", false);
	isVisualizzaAlemnoUnaColonna = (configurazioneUtenteIntervento.getValore().equalsIgnoreCase("1") || isVisualizzaAlemnoUnaColonna) ? true
		: false;
	//
	ConfigurazioneUtenteHelper configurazioneUtenteProcedimenti = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_PROCEDURA, "0", false);
	isVisualizzaAlemnoUnaColonna = (configurazioneUtenteProcedimenti.getValore().equalsIgnoreCase("1") || isVisualizzaAlemnoUnaColonna) ? true
		: false;
	//
	ConfigurazioneUtenteHelper configurazioneUtentePosArchivio = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_POS_ARCHIVIO, "0", false);
	isVisualizzaAlemnoUnaColonna = (configurazioneUtentePosArchivio.getValore().equalsIgnoreCase("1") || isVisualizzaAlemnoUnaColonna) ? true
		: false;
	ConfigurazioneUtenteHelper configurazioneUtenteEndo = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_ENDOPROCEDIMENTO, "0", false);
	isVisualizzaAlemnoUnaColonna = (configurazioneUtenteEndo.getValore().equalsIgnoreCase("1") || isVisualizzaAlemnoUnaColonna) ? true : false;
	//	
	ConfigurazioneUtenteHelper configurazioneUtenteDataPresentazioneIstanza = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_DATA_ISTANZA, "0", false);
	isVisualizzaAlemnoUnaColonna = (configurazioneUtenteDataPresentazioneIstanza.getValore().equalsIgnoreCase("1")
		|| isVisualizzaAlemnoUnaColonna) ? true : false;
	//
	ConfigurazioneUtenteHelper configurazioneUtenteTermineProcedimento = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_TERMINE_PROCEDIMENTO, "0", false);
	isVisualizzaAlemnoUnaColonna = (configurazioneUtenteTermineProcedimento.getValore().equalsIgnoreCase("1") || isVisualizzaAlemnoUnaColonna)
		? true
		: false;
	//
	ConfigurazioneUtenteHelper configurazioneUtenteOperatore = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_OPERATORE, "0", false);
	isVisualizzaAlemnoUnaColonna = (configurazioneUtenteOperatore.getValore().equalsIgnoreCase("1") || isVisualizzaAlemnoUnaColonna) ? true
		: false;
	//
	ConfigurazioneUtenteHelper configurazioneUtenteAmministrazione = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_AMMINISTRAZIONE, "0", false);
	isVisualizzaAlemnoUnaColonna = (configurazioneUtenteAmministrazione.getValore().equalsIgnoreCase("1") || isVisualizzaAlemnoUnaColonna) ? true
		: false;
	//
	ConfigurazioneUtenteHelper configurazioneUtenteMovimentoFatto = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_MOVIMENTO_FATTO, "0", false);
	isVisualizzaAlemnoUnaColonna = (configurazioneUtenteMovimentoFatto.getValore().equalsIgnoreCase("1") || isVisualizzaAlemnoUnaColonna) ? true
		: false;
	//	
	ConfigurazioneUtenteHelper configurazioneUtenteRichiedente = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_RICHIEDENTE, "0", false);
	isVisualizzaAlemnoUnaColonna = (configurazioneUtenteRichiedente.getValore().equalsIgnoreCase("1") || isVisualizzaAlemnoUnaColonna) ? true
		: false;
	//
	List<ConfigurazioneUtenteHelper> configurazioneUtenteHelpers = new ArrayList<ConfigurazioneUtenteHelper>(0);
	configurazioneUtenteHelpers.add(configurazioneUtenteStato);
	configurazioneUtenteHelpers.add(configurazioneUtenteSoftware);
	configurazioneUtenteHelpers.add(configurazioneUtenteIntervento);
	configurazioneUtenteHelpers.add(configurazioneUtenteProcedimenti);
	configurazioneUtenteHelpers.add(configurazioneUtentePosArchivio);
	configurazioneUtenteHelpers.add(configurazioneUtenteEndo);
	configurazioneUtenteHelpers.add(configurazioneUtenteDataPresentazioneIstanza);
	configurazioneUtenteHelpers.add(configurazioneUtenteTermineProcedimento);
	configurazioneUtenteHelpers.add(configurazioneUtenteOperatore);
	configurazioneUtenteHelpers.add(configurazioneUtenteAmministrazione);
	configurazioneUtenteHelpers.add(configurazioneUtenteMovimentoFatto);
	configurazioneUtenteHelpers.add(configurazioneUtenteRichiedente);
	model.addAttribute("configurazioneUtenteHelpers", configurazioneUtenteHelpers);
	model.addAttribute("isVisualizzaAlemnoUnaColonna", isVisualizzaAlemnoUnaColonna);
    }

    @RequestMapping
    public String saveParametriscadenzario(@ModelAttribute("responsabile") ResponsabiliCommand responsabile, BindingResult result,
	    SessionStatus status, Model model, HttpServletRequest request) {

	Responsabili resp = responsabile.getEntity();
	Integer codiceResponsabile = resp.getId().getCodice();
	//  FIXME trick per far funzionare il salvataggio del campo sca_operatore.
	if (EntityUtils.getNestedProperty(responsabile.getScadOperatore(), "chiave") != null) {
	    resp.setScadOperatoreId(responsabile.getScadOperatore().getChiave());
	} else {
	    resp.setScadOperatoreId(null);
	}
	try {
	    responsabiliService.update(resp);
	    // Update parametro "_PAGINA_CENTRALE_" in configurazione utente
	    String centerPage = request.getParameter("center_page");
	    if (StringUtils.defaultString(getCurrentlyAuthenticatedUserDetails().getAmministratore()).equals("1")) {
		ConfigurazioneutenteId id = new ConfigurazioneutenteId(codiceResponsabile, WebConstants.CONF_UTENTE_PAGINA_CENTRALE);
		Configurazioneutente c = configurazioneutenteService.findById(id);
		if (c == null) {
		    c = new Configurazioneutente();
		    c.setId(id);
		    c.setResponsabile(resp);
		    c.setValore(StringUtils.defaultString(centerPage).trim());
		    configurazioneutenteService.insert(c);
		} else {
		    c.setValore(StringUtils.defaultString(centerPage).trim());
		    configurazioneutenteService.update(c);
		}
	    }
	    // Update parametro "SCADENZARIO_ORDINAMENTO_DATA" in configurazione utente
	    if (log.isDebugEnabled()) {
		log.debug("saveParametriscadenzario# Aggiorno il valore del parametro {} di configurazione utente",
			WebConstants.CONF_UTENTE_SCADENZARIO_ORDINAMENTO_DATA);
	    }
	    if (StringUtils.isNotBlank(responsabile.getValoreOrdinamentoData())) {
		if (log.isDebugEnabled()) {
		    log.debug("saveParametriscadenzario# Aggiorno il  parametro {} di configurazione utente con il valore {}",
			    new Object[] { WebConstants.CONF_UTENTE_SCADENZARIO_ORDINAMENTO_DATA, responsabile.getValoreOrdinamentoData() });
		}
		ConfigurazioneutenteId idOrdinamnetoDataScadezario = new ConfigurazioneutenteId(codiceResponsabile,
			WebConstants.CONF_UTENTE_SCADENZARIO_ORDINAMENTO_DATA);
		Configurazioneutente configurazioneUtenteOrdinamnetoDataScadezario = configurazioneutenteService
			.findById(idOrdinamnetoDataScadezario);
		if (configurazioneUtenteOrdinamnetoDataScadezario == null) {
		    if (log.isDebugEnabled()) {
			log.debug("saveParametriscadenzario# Caso inserimento nuovo parametro con valore {}",
				responsabile.getValoreOrdinamentoData());
		    }
		    configurazioneUtenteOrdinamnetoDataScadezario = new Configurazioneutente();
		    configurazioneUtenteOrdinamnetoDataScadezario.setId(idOrdinamnetoDataScadezario);
		    configurazioneUtenteOrdinamnetoDataScadezario.setResponsabile(resp);
		    configurazioneUtenteOrdinamnetoDataScadezario
			    .setValore(StringUtils.defaultString(responsabile.getValoreOrdinamentoData().trim()));
		    configurazioneutenteService.insert(configurazioneUtenteOrdinamnetoDataScadezario);
		} else {
		    if (log.isDebugEnabled()) {
			log.debug("saveParametriscadenzario# Caso update  parametro con valore {}", responsabile.getValoreOrdinamentoData());
		    }
		    configurazioneUtenteOrdinamnetoDataScadezario
			    .setValore(StringUtils.defaultString(responsabile.getValoreOrdinamentoData()).trim());
		    configurazioneutenteService.update(configurazioneUtenteOrdinamnetoDataScadezario);
		}
	    }
	} catch (Exception e) {
	    Responsabili entity = responsabiliService.findById(new PkId(codiceResponsabile));
	    if (entity.getScadOperatoreId() != null) {
		ScadenzarioOperatoreChiaveValore scadOperatoreCvb = getScadOperatore(entity);
		responsabile.setScadOperatore(scadOperatoreCvb);
	    }
	    copyErrorsToBindingResult(result, responsabile.getEntity(), true, e);
	    responsabile.setEntity(entity);
	    responsabile.setResponsabilisoftwareList(getListResponsabilisoftware(entity));
	    responsabile.setResponsabilicomuniList(getListResponsabilicomuni(entity));
	    model.addAttribute("responsabile", responsabile);
	    setPageAttributes(model);
	    setScadenzarioAttribute(responsabile, model, request);
	    fixRenderEntityProperty(entity);
	    return "responsabili/formScadenzario";
	}
	status.setComplete();
	return "redirect:viewParametriscadenzario.htm?codice=" + resp.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String createRuoli(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Responsabili responsabile = responsabiliService.findById(id);
	List<Ruoli> ruolis = ruoliService.findAll(null, null);
	Set<Responsabiliruoli> ruolisResp = responsabile.getResponsabiliruolis();
	for (Ruoli ruolo : ruolis) {
	    for (Responsabiliruoli responsabiliruoli : ruolisResp) {
		Ruoli ruoloResp = responsabiliruoli.getRuolo();
		if (ruolo.getId().getCodice().compareTo(ruoloResp.getId().getCodice()) == 0) {
		    ruolo.setRuoloResponsabileTransient(true);
		    break;
		}
	    }
	}
	fixRenderEntityProperty(responsabile);
	model.addAttribute("responsabile", responsabile);
	model.addAttribute("ruolis", ruolis);
	setPageAttributes(model);
	return "responsabili/ruoli";
    }

    @RequestMapping
    public String createParametriprotocollo(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Responsabili responsabile = responsabiliService.findById(id);
	List<ProtocolloFlusso> protocolloFlussoList = protocolloFlussoService.findAll(null, null);
	fixRenderEntityProperty(responsabile);
	model.addAttribute("responsabile", responsabile);
	model.addAttribute("protocolloFlussoList", protocolloFlussoList);
	setPageAttributes(model);
	return "responsabili/formParametriprotocollo";
    }

    @RequestMapping
    public String createPermessi(@RequestParam("codice") Integer codice, @RequestParam("permessisoftware") String permessisoftware,
	    @RequestParam(value = "status_msg", required = false) String status_msg, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Responsabili entity = responsabiliService.findById(id);
	fixRenderEntityProperty(entity);
	ResponsabiliCommand responsabile = new ResponsabiliCommand();
	responsabile.setEntity(entity);
	List<Software> softwareList = softwareService.findSoftwareAbilitati(entity);
	model.addAttribute("responsabile", responsabile);
	model.addAttribute("permessisoftware", permessisoftware);
	model.addAttribute("status_msg", status_msg);
	model.addAttribute("softwareList", softwareList);
	return "responsabili/formPermessi";
    }

    @RequestMapping
    public String createReplicapermessi(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Responsabili entity = responsabiliService.findById(id);
	List<Responsabili> responsabiliList = responsabiliService.findAll(null, null);
	// Elimino il Responsabile da cui replicare i permessi
	responsabiliList.remove(entity);
	fixRenderEntityProperty(entity);
	ResponsabiliCommand responsabile = new ResponsabiliCommand();
	responsabile.setEntity(entity);
	model.addAttribute("responsabile", responsabile);
	model.addAttribute("responsabiliList", responsabiliList);
	setPageAttributes(model);
	return "responsabili/formReplicapermessi";
    }

    @RequestMapping
    public String savePermessi(@ModelAttribute("responsabile") ResponsabiliCommand responsabile, BindingResult result,
	    @RequestParam("permessisoftware") String permessisoftware, SessionStatus status, Model model, HttpServletRequest request) {

	fixMergeEntityProperty(responsabile.getEntity());
	try {
	    responsabiliService.savePermessiSW(responsabile.getEntity(), responsabile.getNuoviPermessi(), permessisoftware);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, responsabile.getEntity(), true, e);
	    fixRenderEntityProperty(responsabile.getEntity());
	    return "responsabili/formPermessi";
	}
	status.setComplete();
	return "redirect:createPermessi.htm?codice=" + responsabile.getEntity().getId().getCodice() + "&permessisoftware=" + permessisoftware +
	       "&status_msg=02";
    }

    @RequestMapping
    public String saveParametriprotocollo(@ModelAttribute("responsabile") Responsabili responsabile, BindingResult result, SessionStatus status,
	    Model model, HttpServletRequest request) {

	fixMergeEntityProperty(responsabile);
	try {
	    responsabiliService.saveParametriprotocollo(responsabile);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, responsabile, e);
	    fixRenderEntityProperty(responsabile);
	    return "responsabili/formParametriprotocollo";
	}
	status.setComplete();
	return "redirect:createParametriprotocollo.htm?codice=" + responsabile.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String saveRuoli(@ModelAttribute("responsabile") Responsabili responsabile, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	fixMergeEntityProperty(responsabile);
	String[] ruolisId = request.getParameterValues("ruolis");
	Set<Responsabiliruoli> responsabiliruolis = new HashSet<Responsabiliruoli>();
	if (ruolisId != null) {
	    for (int i = 0; i < ruolisId.length; i++) {
		Ruoli ruoli = ruoliService.findById(new PkId(Integer.parseInt(ruolisId[i])));
		ResponsabiliruoliId id = new ResponsabiliruoliId();
		id.setCodiceresponsabile(responsabile.getId().getCodice());
		id.setIdruolo(ruoli.getId().getCodice());
		id.setIdcomune(ORMHelper.getIdcomune());
		Responsabiliruoli responsabiliruoli = new Responsabiliruoli();
		responsabiliruoli.setResponsabile(responsabile);
		responsabiliruoli.setRuolo(ruoli);
		responsabiliruoli.setId(id);
		responsabiliruolis.add(responsabiliruoli);
	    }
	}
	try {
	    responsabiliService.saveRuoli(responsabile, responsabiliruolis);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, responsabile, e);
	    fixRenderEntityProperty(responsabile);
	    return "responsabili/ruoli";
	}
	status.setComplete();
	return "redirect:createRuoli.htm?codice=" + responsabile.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String saveReplicapermessi(@ModelAttribute("responsabile") ResponsabiliCommand responsabile, BindingResult result, SessionStatus status,
	    Model model, HttpServletRequest request) {

	Responsabili entity = responsabiliService.findById(responsabile.getEntity().getId());
	fixMergeEntityProperty(responsabile.getEntity());
	String[] responsabilisId = request.getParameterValues("responsabiliList");
	if (responsabilisId != null) {
	    Set<Responsabili> responsabiliSet = new HashSet<Responsabili>();
	    for (int i = 0; i < responsabilisId.length; i++) {
		Responsabili resp = responsabiliService.findById(new PkId(Integer.parseInt(responsabilisId[i])));
		responsabiliSet.add(resp);
	    }
	    try {
		responsabiliService.saveReplicapermessi(responsabiliSet, entity);
	    } catch (Exception e) {
		copyErrorsToBindingResult(result, responsabile.getEntity(), true, e);
		fixRenderEntityProperty(responsabile.getEntity());
		setPageAttributes(model);
		return "responsabili/form";
	    }
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + responsabile.getEntity().getId().getCodice(); // + "&status_msg=02"
    }

    /**
     * Restituisce la lista di tutti i comuni che possono essere associati in una lista di Responsabilicomuni
     * 
     * @param responsabile
     * 
     */
    private Set<Responsabilicomuni> getListResponsabilicomuni(Responsabili responsabile) {

	Set<Responsabilicomuni> responsabilicomuniList = new LinkedHashSet<Responsabilicomuni>();
	List<Comuniassociati> comuniassociatiList = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	for (Comuniassociati comuniassociati : comuniassociatiList) {
	    Responsabilicomuni responsabilicomuni = new Responsabilicomuni();
	    ResponsabilicomuniId idRc = new ResponsabilicomuniId();
	    idRc.setCodicecomune(comuniassociati.getId().getCodicecomune());
	    idRc.setCodiceresponsabile(responsabile.getId().getCodice());
	    responsabilicomuni.setId(idRc);
	    responsabilicomuni.setResponsabile(responsabile);
	    responsabilicomuni.setComune(comuniassociati.getComune());
	    responsabilicomuniList.add(responsabilicomuni);
	}
	return responsabilicomuniList;
    }

    /**
     * Restituisce la lista di tutti i software che possono essere attivati in una lista di Responsabilisoftware
     * 
     * @param responsabile
     * 
     */
    public Set<Responsabilisoftware> getListResponsabilisoftware(Responsabili responsabile) {

	Set<Responsabilisoftware> responsabilisoftwareList = new LinkedHashSet<Responsabilisoftware>();
	List<Software> softwares = softwareService.findSoftwareAttivi(false);
	for (Software software : softwares) {
	    Responsabilisoftware responsabilisoftware = new Responsabilisoftware();
	    ResponsabilisoftwareId idSw = new ResponsabilisoftwareId(software.getCodice(), responsabile.getId().getCodice());
	    Software sw = softwareService.findById(software.getCodice());
	    responsabilisoftware.setSoftware(sw);
	    responsabilisoftware.setResponsabili(responsabile);
	    responsabilisoftware.setId(idSw);
	    responsabilisoftwareList.add(responsabilisoftware);
	}
	return responsabilisoftwareList;
    }

    @Override
    protected void fixMergeEntityProperty(Responsabili entity) {

	/*
	if (entity.getTiporesponsabile() != null && entity.getTiporesponsabile().getId() != null
		&& entity.getTiporesponsabile().getId().getCodice() == null) {
	    entity.setTiporesponsabile(null);
	}
	if (entity.getProtTprofilassegnazione() != null && entity.getProtTprofilassegnazione().getId() != null
		&& entity.getProtTprofilassegnazione().getId().getCodice() == null) {
	    entity.setProtTprofilassegnazione(null);
	}
	*/
    }

    @Override
    protected void fixRenderEntityProperty(Responsabili entity) {

	if (entity.getTiporesponsabile() == null) {
	    entity.setTiporesponsabile(new Tipiresponsabili());
	}
	if (entity.getProtTprofilassegnazione() == null) {
	    entity.setProtTprofilassegnazione(new ProtTprofilassegnazione());
	}
	if (entity.getScadSoftware() == null) {
	    entity.setScadSoftware(new Software());
	    entity.getScadSoftware().setCodice(null);
	}
	//	if (entity.getScadOperatore() == null) {
	//	    entity.setScadOperatore(new Responsabili());
	//	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	boolean docERAttivo = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER);
	model.addAttribute("isDocErAttivo", docERAttivo);
	boolean isAssegnazioneOperatori = verticalizzazioniService
		.isAttivaPerQualsiasiSoftware(WebConstants.VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI);
	model.addAttribute("isAssegnazioneOperatori", Boolean.valueOf(isAssegnazioneOperatori));
	/*
	 * Lista Tipiresponsabili
	 */
	model.addAttribute("tipiresponsabiliList", tipiresponsabiliService.findAll(null, null));
	// ---------------------------------INIZIO GESTIONE COMUNI
	// ASSOCIATI------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------------------
	// Serve per sapere se si tratta di un comune associato, in tal caso sul form deve comparire
	// una sezione dedicata
	Boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	model.addAttribute("isComuniAssociati", isComuniAssociati);
	// ----------------------------------------------------------------------------------------------------------------------------
	// ----------------------------------FINE GESTIONE CONFIGURAZIONE
	model.addAttribute("tipiresponsabiliList", tipiresponsabiliService.findAll(null, null));
	/*
	 * Controllo se è attiva la verticalizzazione con il modulo PROTOCOLLO_ATTIVO
	 */
	Verticalizzazioni verticalizzazioni_PROTOCOLLO_ATTIVO = verticalizzazioniService
		.findByModulo(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE);
	if (verticalizzazioni_PROTOCOLLO_ATTIVO != null) {
	    if (verticalizzazioni_PROTOCOLLO_ATTIVO.getAttivo() == 1) {
		model.addAttribute("vert_prot_attivo", true);
	    } else {
		model.addAttribute("vert_prot_attivo", false);
	    }
	} else {
	    model.addAttribute("vert_prot_attivo", false);
	}
    }
}
