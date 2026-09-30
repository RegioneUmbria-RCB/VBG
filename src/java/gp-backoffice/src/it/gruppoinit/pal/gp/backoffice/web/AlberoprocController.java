/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.opensaml.artifact.InvalidArgumentException;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ContestiMailTipoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocArendo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocAteco;
import it.gruppoinit.pal.gp.core.domain.AlberoprocAtecoId;
import it.gruppoinit.pal.gp.core.domain.AlberoprocD2modtatt;
import it.gruppoinit.pal.gp.core.domain.AlberoprocD2modtattId;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenticat;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoId;
import it.gruppoinit.pal.gp.core.domain.AlberoprocLeggi;
import it.gruppoinit.pal.gp.core.domain.AlberoprocMovimenti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocOneri;
import it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoli;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoliId;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttori;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentiincomp;
import it.gruppoinit.pal.gp.core.domain.LdpDecodifiche;
import it.gruppoinit.pal.gp.core.domain.Leggi;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.RiTipiintervento;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.Tipibando;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.VwAlberoproc;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocDocumentiComparator;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocEndoComparator;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocLeggiComparator;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocProtAndFascHelper;
import it.gruppoinit.pal.gp.core.domain.helper.TipoDownload;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocCommand;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.alberoproc.beans.AlberoProcTipiSoggettoBean;
import it.gruppoinit.pal.gp.core.features.alberoproc.beans.AlberoProcTipiSoggettoWrapper;
import it.gruppoinit.pal.gp.core.features.alberoproc.esclusioni.AlberoprocComuniEsclusiService;
import it.gruppoinit.pal.gp.core.features.alberoproc.esclusioni.ComuniEsclusi;
import it.gruppoinit.pal.gp.core.features.alberoproc.metadati.AlberoprocMetadatiService;
import it.gruppoinit.pal.gp.core.features.alberoproc.metadati.ConfigurazioneMetadati;
import it.gruppoinit.pal.gp.core.features.alberoproc.metadati.MetadatoAlberoproc;
import it.gruppoinit.pal.gp.core.features.alberoproc.tempi.AlberoprocTempiService;
import it.gruppoinit.pal.gp.core.features.alberoproc.tempi.EliminaAlberoProcTempiRequest;
import it.gruppoinit.pal.gp.core.features.alberoproc.tempi.FindAlberoProcTempiRequest;
import it.gruppoinit.pal.gp.core.features.alberoproc.tempi.FindAlberoProcTempiResponse;
import it.gruppoinit.pal.gp.core.features.alberoproc.tempi.SalvaAlberoprocTempiRequest;
import it.gruppoinit.pal.gp.core.features.alberoproc.tempi.SalvaAlberoprocTempiResponse;
import it.gruppoinit.pal.gp.core.features.areariservata.IVerticalizzazioneAreaRiservataService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeStrRicalcoloRestClient;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.RespSessionData;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.movimenti.istanzecollegate.AlberoprocMovimentiService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AlberoprocArendoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocAtecoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocD2modtattService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocSoggFirmatariService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocumentiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocumenticatService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDyn2modellitService;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocLeggiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocOneriService;
import it.gruppoinit.pal.gp.core.service.AlberoprocProtocolloService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AlberoprocTipisoggettoService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AzioniService;
import it.gruppoinit.pal.gp.core.service.CartInvioDizionarioService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiincompService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeruoliService;
import it.gruppoinit.pal.gp.core.service.LdpDecodificheService;
import it.gruppoinit.pal.gp.core.service.LeggiService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.ProcediMarcheProxyService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.RuoliService;
import it.gruppoinit.pal.gp.core.service.RuoliUtentiEnum;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipibandoService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;
import it.gruppoinit.pal.gp.core.service.TipisoggettoService;
import it.gruppoinit.pal.gp.core.service.VwAlberoprocService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * @author francescop
 * @author gianpaolot
 * @author lucap
 * 
 */
@Controller
@SessionAttributes(value = { "alberoproc", "alberoprocLeggi", "alberoprocDocumenti", "alberoprocOneri", "alberoprocEndo", "alberoprocDyn2modellit",
    "alberoprocCommand", "alberoprocArendo", "alberoprocD2modtatt", "alberoprocprotocollo", "alberoprocMovimenti", "alberoProcTipiSoggettoWrapper" })
public class AlberoprocController extends BaseJsonController<Alberoproc> {

    private static final Logger log = LoggerFactory.getLogger(AlberoprocController.class);
    private static final String FLAG_PUBBLICA = "flag_pubblica";
    private static final String FLAG_PRINCIPALE = "flag_principale";
    private static final String FLAG_PROPOSTO = "flag_proposto";
    private static final String FLAG_RICHESTO_BACK = "flag_richiesto_bo";
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private CartInvioDizionarioService cartInvioDizionarioService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AlberoprocComuniEsclusiService alberoprocComuniEsclusiService;
    @Autowired
    private AlberoprocD2modtattService alberoprocD2modtattService;
    @Autowired
    private AlberoprocTipisoggettoService alberoprocTipisoggettoService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private RuoliService ruoliService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private AzioniService azioniService;
    @Autowired
    private TipologiaregistriService tipologiaregistriService;
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private AlberoprocLeggiService alberoprocLeggiService;
    @Autowired
    private AlberoprocDocumentiService alberoprocDocumentiService;
    @Autowired
    private AlberoprocOneriService alberoprocOneriService;
    @Autowired
    private AlberoprocEndoService alberoprocEndoService;
    @Autowired
    private AlberoprocDocumenticatService alberoprocDocumenticatService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private StpEndoTipo2Service stpEndoTipo2Service;
    @Autowired
    private LeggiService leggiService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private AlberoprocDyn2modellitService alberoprocDyn2modellitService;
    @Autowired
    private VwAlberoprocService vwAlberoprocService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private InventarioprocedimentiincompService inventarioprocedimentiincompService;
    @Autowired
    private AlberoprocAtecoService alberoprocAtecoService;
    @Autowired
    private AlberoprocArendoService alberoprocArendoService;
    @Autowired
    private TipiendoService tipiendoService;
    @Autowired
    private TipifamiglieendoService tipifamiglieendoService;
    @Autowired
    private TipicausalioneriService tipicausalioneriService;
    @Autowired
    private IstanzeruoliService istanzeruoliService;
    @Autowired
    private TipisoggettoService tipisoggettoService;
    @Autowired
    private AlberoprocProtocolloService alberoprocProtocolloService;
    @Autowired
    private ProtocollazioneService protocollazioneService;
    @Autowired
    private TipibandoService tipibandoService;
    @Autowired
    private LdpDecodificheService ldpDecodificheService;
    @Autowired
    private AlberoprocMetadatiService alberoprocMetadatiService;
    @Autowired
    private ProcediMarcheProxyService procediMarcheProxyService;
    @Autowired
    private IVerticalizzazioneAreaRiservataService verticalizzazioneAreaRiservataService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private AlberoprocMovimentiService alberoprocMovimentiService;
    private AlberoprocTempiService alberoprocTempiService;
    
    @Autowired
    private AlberoprocDocSoggFirmatariService alberoprocDocSoggFirmatariService;

    @Autowired
    public void setAlberoprocTempiService(AlberoprocTempiService alberoprocTempiService) {

	this.alberoprocTempiService = alberoprocTempiService;
    }

    /**
     * FUNZIONALITA' ALBEROPROC_RUOLI
     */
    @RequestMapping
    public String createRuoli(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	/*
	 * Viene settato il software poichè la funzionalità non viene chiamata utilizzando il menù
	 */
	ORMHelper.setSoftware(alberoproc.getSoftware().getCodice());
	Set<AlberoprocRuoli> alberoprocRuolis = alberoproc.getAlberoprocRuolis();
	List<Ruoli> ruolis = ruoliService.findAll(null, null);
	String scCodice = alberoproc.getScCodice();
	int lengthPadre = scCodice.length();
	int lengthTree = lengthPadre / 2;
	Set<Ruoli> ruolisPadre = new HashSet<Ruoli>();
	for (int i = 0; i < lengthTree - 1; i++) {
	    lengthPadre = lengthPadre - 2;
	    String sccodicePadre = scCodice.substring(0, lengthPadre);
	    Alberoproc alberoprocTemp = alberoprocService.findByScCodice(sccodicePadre);
	    Set<AlberoprocRuoli> alberoprocRuoliTempSet = alberoprocTemp.getAlberoprocRuolis();
	    for (AlberoprocRuoli alberoprocRuoli : alberoprocRuoliTempSet) {
		ruolisPadre.add(alberoprocRuoli.getRuoli());
	    }
	}
	List<Ruoli> ruoliFiglio = new ArrayList<Ruoli>();
	ruoliFiglio.addAll(ruolis);
	for (Ruoli ruoli : ruolis) {
	    for (Ruoli ruoliPadre : ruolisPadre) {
		if (ruoliPadre.getId().getCodice().compareTo(ruoli.getId().getCodice()) == 0) {
		    ruoliFiglio.remove(ruoli);
		}
	    }
	}
	for (Ruoli ruolo : ruoliFiglio) {
	    for (AlberoprocRuoli alberoprocRuoli : alberoprocRuolis) {
		Ruoli ruoloTemp = alberoprocRuoli.getRuoli();
		if (ruoloTemp.getId().getCodice().compareTo(ruolo.getId().getCodice()) == 0) {
		    ruolo.setRuoloAlberoprocTransient(true);
		    break;
		}
	    }
	}
	model.addAttribute("alberoproc", alberoproc);
	model.addAttribute("ruolisPadre", ruolisPadre);
	model.addAttribute("ruolisFiglio", ruoliFiglio);
	setPageAttributes(model);
	return "alberoproc/ruoli";
    }

    @RequestMapping
    public String saveRuoli(@ModelAttribute("alberoproc") Alberoproc alberoproc, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	fixMergeEntityProperty(alberoproc);
	Integer alberoprocId = alberoproc.getId().getCodice();
	String[] ruolisPadreId = request.getParameterValues("ruoliPadre");
	String[] ruolisFiglioId = request.getParameterValues("ruoliFiglio");
	Set<AlberoprocRuoli> alberoprocRuolis = new HashSet<AlberoprocRuoli>();
	if (ruolisPadreId != null) {
	    for (int i = 0; i < ruolisPadreId.length; i++) {
		Ruoli ruoli = ruoliService.findById(new PkId(Integer.parseInt(ruolisPadreId[i])));
		AlberoprocRuoliId id = new AlberoprocRuoliId();
		id.setFkScId(alberoproc.getId().getCodice());
		id.setFkIdruolo(ruoli.getId().getCodice());
		id.setIdcomune(ORMHelper.getIdcomune());
		AlberoprocRuoli alberoprocRuoliTemp = new AlberoprocRuoli();
		alberoprocRuoliTemp.setAlberoproc(alberoproc);
		alberoprocRuoliTemp.setRuoli(ruoli);
		alberoprocRuoliTemp.setId(id);
		alberoprocRuolis.add(alberoprocRuoliTemp);
	    }
	}
	if (ruolisFiglioId != null) {
	    for (int i = 0; i < ruolisFiglioId.length; i++) {
		Ruoli ruoli = ruoliService.findById(new PkId(Integer.parseInt(ruolisFiglioId[i])));
		AlberoprocRuoliId id = new AlberoprocRuoliId();
		id.setFkScId(alberoproc.getId().getCodice());
		id.setFkIdruolo(ruoli.getId().getCodice());
		id.setIdcomune(ORMHelper.getIdcomune());
		AlberoprocRuoli alberoprocRuoliTemp = new AlberoprocRuoli();
		alberoprocRuoliTemp.setAlberoproc(alberoproc);
		alberoprocRuoliTemp.setRuoli(ruoli);
		alberoprocRuoliTemp.setId(id);
		alberoprocRuolis.add(alberoprocRuoliTemp);
	    }
	}
	try {
	    alberoprocService.saveRuoli(alberoproc, alberoprocRuolis);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(alberoproc, false, "alberoproc", e);
	    return "redirect:createRuoli.htm?alberoproc.id.codice=" + alberoprocId + "&status_msg=03";
	}
	status.setComplete();
	return "redirect:createRuoli.htm?alberoproc.id.codice=" + alberoproc.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String list(Model model, @RequestParam(value = "gestione", required = false) Boolean gestione) {

	Alberoproc alberoproc = new Alberoproc();
	model.addAttribute("alberoproc", alberoproc);
	if (BooleanUtils.isTrue(gestione)) {
	    return "alberoproc/listgestione";
	}
	setPageAttributes(model);
	return "alberoproc/list";
    }

    /**
     * FUNZIONALITA' ALBEROPROC
     */
    @RequestMapping
    public String create(@RequestParam("codicepadre") Integer codice, Model model, HttpServletRequest request) {

	Alberoproc alberoprocPadre = alberoprocService.findById(new PkId(codice));
	model.addAttribute("alberoprocPadre", alberoprocPadre);
	Alberoproc alberoproc = new Alberoproc();
	if (alberoprocPadre == null) {
	    alberoproc.setAreaPrimaria(true);
	}
	alberoproc.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	model.addAttribute("alberoproc", alberoproc);
	model.addAttribute("entiEsclusiPresenti", false);
	this.setPageAttributes(model, null, request);
	fixRenderEntityProperty(alberoproc);
	return "alberoproc/form";
    }

    @RequestMapping
    public String spostaVoceAlbero(@RequestParam("sorgente") Integer sorgente, @RequestParam("destinazione") Integer destinazione, Model model,
	    HttpServletRequest request) {

	Responsabili resp = getCurrentlyAuthenticatedUserDetails();
	if (StringUtils.defaultIfEmpty(resp.getAmministratore(), "N").equalsIgnoreCase("N")) {
	    log.error("spostaVoceAlbero: L'operatore {} non può eseguire questa funzionalità amministrativa", resp.getResponsabile());
	    throw new SecurityException("L'operatore " + resp.getResponsabile() + " non può eseguire questa funzionalità amministrativa");
	}
	try {
	    alberoprocService.spostaVoceAlbero(sorgente, destinazione);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add(e.getMessage());
	}
	return "redirect:list.htm?" + WebConstants.SOFTWARE + "=" + ORMHelper.getSoftware() + "&gestione=true&_ts=" + System.currentTimeMillis();
    }

    @RequestMapping
    public String insert(@RequestParam("codicepadre") Integer codice, Model model, @ModelAttribute("alberoproc") Alberoproc alberoproc,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	Alberoproc alberoprocPadre = alberoprocService.findById(new PkId(codice));
	model.addAttribute("alberoprocPadre", alberoprocPadre);
	this.setPageAttributes(model, null, request);
	// recupero l'oggetto passato (recupero del file oggettoworkflow)
	if (EntityUtils.getNestedProperty(alberoproc.getOggettoWorkflowAreaRis(), "id.codice") != null) {
	    Oggetti oggetti = oggettiService.findById(new PkId(alberoproc.getOggettoWorkflowAreaRis().getId().getCodice()));
	    alberoproc.setOggettoWorkflowAreaRis(oggetti);
	}
	sistemaRequest(alberoproc, request);
	fixMergeEntityProperty(alberoproc);
	alberoproc.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    alberoprocService.insertAlberoproc(alberoproc, alberoprocPadre);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoproc, e);
	    fixRenderEntityProperty(alberoproc);
	    return "alberoproc/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	/*
	 * Recupero Normative e Documenti ereditate
	 */
	this.setPageAttributes(model, alberoproc, request);
	// questa find è necessaria perchè nel metodo sopra (setPageAttributes) ci sono dei service
	// che 'catchano' le eventuali eccezioni. Quando 'catchi' un'eccezione in un service la sessione di hibernate è svuotata
	// per cui è necessario ricaricarla.
	alberoproc = alberoprocService.findById(alberoproc.getId());
	model.addAttribute("entiEsclusiPresenti", this.alberoprocComuniEsclusiService.entiEsclusiPresenti(alberoproc.getScCodice()));
	model.addAttribute("alberoproc", alberoproc);
	List<Azioni> azionis = new ArrayList<Azioni>();
	azionis = azioniService.findAll(null, null);
	model.addAttribute("azionis", azionis);
	setPageAttributes(model);
	fixRenderEntityProperty(alberoproc);
	return "alberoproc/form";
    }

    private void sistemaRequest(Alberoproc alberoproc, HttpServletRequest request) {

	if (!StringUtils.defaultIfEmpty(request.getParameter("responsabile.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("responsabile.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    alberoproc.setResponsabile(responsabili);
	} else {
	    alberoproc.setResponsabile(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("respistruttoria.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("respistruttoria.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    alberoproc.setRespistruttoria(responsabili);
	} else {
	    alberoproc.setRespistruttoria(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("operatoreStc.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("operatoreStc.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    alberoproc.setOperatoreStc(responsabili);
	} else {
	    alberoproc.setOperatoreStc(null);
	}
	if (StringUtils.isNotBlank((String) request.getParameter("ldpOccupazionis.id.codice"))) {
	    String cod = (String) request.getParameter("ldpOccupazionis.id.codice");
	    if (Utilities.isInteger(cod)) {
		LdpDecodifiche c = ldpDecodificheService.findById(new PkId(Integer.parseInt(cod)));
		alberoproc.setLdpOccupazionis(c);
	    }
	} else {
	    alberoproc.setLdpOccupazionis(null);
	}
	if (StringUtils.isNotBlank((String) request.getParameter("ldpPeriodis.id.codice"))) {
	    String cod = (String) request.getParameter("ldpPeriodis.id.codice");
	    if (Utilities.isInteger(cod)) {
		LdpDecodifiche c = ldpDecodificheService.findById(new PkId(Integer.parseInt(cod)));
		alberoproc.setLdpPeriodis(c);
	    }
	} else {
	    alberoproc.setLdpPeriodis(null);
	}
	if (StringUtils.isNotBlank((String) request.getParameter("ldpGeometries.id.codice"))) {
	    String cod = (String) request.getParameter("ldpGeometries.id.codice");
	    if (Utilities.isInteger(cod)) {
		LdpDecodifiche c = ldpDecodificheService.findById(new PkId(Integer.parseInt(cod)));
		alberoproc.setLdpGeometries(c);
	    }
	} else {
	    alberoproc.setLdpGeometries(null);
	}
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("alberoproc") Alberoproc alberoproc, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	sistemaRequest(alberoproc, request);
	// recupero l'oggetto passato (recupero del file oggettoworkflow)
	if (EntityUtils.getNestedProperty(alberoproc.getOggettoWorkflowAreaRis(), "id.codice") != null) {
	    Oggetti oggetti = oggettiService.findById(new PkId(alberoproc.getOggettoWorkflowAreaRis().getId().getCodice()));
	    alberoproc.setOggettoWorkflowAreaRis(oggetti);
	}
	fixMergeEntityProperty(alberoproc);
	try {
	    alberoprocService.update(alberoproc);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoproc, e);
	    this.setPageAttributes(model, alberoproc, request);
	    fixRenderEntityProperty(alberoproc);
	    return "alberoproc/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("alberoproc") Alberoproc alberoproc, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Alberoproc objToDelete = alberoprocService.findById(alberoproc.getId());
	try {
	    alberoprocService.delete(objToDelete);
	} catch (Exception e) {
	    this.setPageAttributes(model, objToDelete, request);
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(objToDelete);
	    return "alberoproc/form";
	}
	status.setComplete();
	return "redirect:list.htm?status_msg=05";
    }

    /**
     * FUNZIONALITA' ALBEROPROC_LEGGI
     */
    @RequestMapping
    public String createLeggi(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	List<Leggi> leggis = leggiService.findAllWithOrder("leggitipi.id.codice");
	List<AlberoprocLeggi> alberoprocLeggis = alberoprocLeggiService.findByAlberoProc(codice);
	List<Leggi> leggiList = new ArrayList<Leggi>();
	leggiList.addAll(leggis);
	if (!alberoprocLeggis.isEmpty()) {
	    for (Leggi leggi : leggis) {
		for (AlberoprocLeggi alberoprocLeggi : alberoprocLeggis) {
		    if (alberoprocLeggi.getLegge().getId().getCodice().compareTo(leggi.getId().getCodice()) == 0) {
			leggiList.remove(leggi);
		    }
		}
	    }
	}
	AlberoprocLeggi alberoprocLeggi = new AlberoprocLeggi();
	alberoprocLeggi.setAlberoproc(alberoproc);
	model.addAttribute("alberoprocLeggi", alberoprocLeggi);
	model.addAttribute("alberoproc", alberoproc);
	model.addAttribute("leggiList", leggiList);
	setPageAttributes(model);
	return "alberoproc/leggi";
    }

    /**
     * FUNZIONALITA' ALBEROPROC_ARENDO
     */
    @RequestMapping
    public String createArendo(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	AlberoprocArendo alberoprocArendo = new AlberoprocArendo();
	alberoprocArendo.setAlberoproc(alberoproc);
	model.addAttribute("alberoprocArendo", alberoprocArendo);
	model.addAttribute("alberoproc", alberoproc);
	setPageAttributes(model);
	fixRenderEntityPropertyAlberoprocArendo(alberoprocArendo);
	return "alberoproc/arendo";
    }

    @RequestMapping
    public String insertArendo(@ModelAttribute("alberoproc") Alberoproc alberoproc,
	    @ModelAttribute("alberoprocArendo") AlberoprocArendo alberoprocArendo, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	if (EntityUtils.getNestedProperty(alberoprocArendo.getTipiendo(), "id.codice") != null) {
	    Tipiendo tipiendo = tipiendoService.findById(new PkId(alberoprocArendo.getTipiendo().getId().getCodice()));
	    alberoprocArendo.setTipiendo(tipiendo);
	}
	if (EntityUtils.getNestedProperty(alberoprocArendo.getTipifamiglieendo(), "id.codice") != null) {
	    Tipifamiglieendo tipifamiglieendo = tipifamiglieendoService
		    .findById(new PkId(alberoprocArendo.getTipifamiglieendo().getId().getCodice()));
	    alberoprocArendo.setTipifamiglieendo(tipifamiglieendo);
	}
	try {
	    alberoprocArendoService.insert(alberoprocArendo);
	} catch (Exception e) {
	    fixRenderEntityPropertyAlberoprocArendo(alberoprocArendo);
	    copyErrorsToBindingResult(result, alberoprocArendo, e);
	    model.addAttribute("alberoprocArendo", alberoprocArendo);
	    model.addAttribute("alberoproc", alberoproc);
	    return "alberoproc/arendo";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "#arendo_anchor";
    }

    @RequestMapping
    public String insertLegge(@ModelAttribute("alberoproc") Alberoproc alberoproc, @ModelAttribute("alberoprocLeggi") AlberoprocLeggi alberoprocLeggi,
	    BindingResult result, SessionStatus status, Model model, HttpServletRequest request) {

	try {
	    alberoprocLeggiService.insert(alberoprocLeggi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocLeggi, e);
	    return "alberoproc/leggi";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "#legge_anchor";
    }

    @RequestMapping
    public String eliminaLegge(Model model, @RequestParam("alberoproc.id.codice") Integer codicealberoproc,
	    @RequestParam("codicelegge") Integer codice, HttpServletRequest request) {

	AlberoprocLeggi alberoprocLeggi = alberoprocLeggiService.findById(new PkId(codice));
	try {
	    alberoprocLeggiService.delete(alberoprocLeggi);
	} catch (Exception e) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codicealberoproc));
	    this.setPageAttributes(model, alberoproc, request);
	    model.addAttribute("alberoproc", alberoproc);
	    List<String> error = new ArrayList<String>();
	    error.add(e.getMessage());
	    FlashMessages.setWarnings(error);
	    fixRenderEntityProperty(alberoproc);
	    return "alberoproc/form";
	}
	return "redirect:view.htm?codice=" + codicealberoproc;
    }

    @RequestMapping
    public String eliminaArendo(Model model, @RequestParam("alberoproc.id.codice") Integer codicealberoproc,
	    @RequestParam("codicearendo") Integer codice, HttpServletRequest request) {

	AlberoprocArendo alberoprocArendo = alberoprocArendoService.findById(new PkId(codice));
	try {
	    alberoprocArendoService.delete(alberoprocArendo);
	} catch (Exception e) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codicealberoproc));
	    this.setPageAttributes(model, alberoproc, request);
	    model.addAttribute("alberoproc", alberoproc);
	    fixRenderEntityProperty(alberoproc);
	    return "alberoproc/form";
	}
	return "redirect:view.htm?codice=" + codicealberoproc;
    }

    //////////////////////////////////
    /**
     * FUNZIONALITA' ALBEROPROC_TIPISOGGETTO
     */
    @RequestMapping
    public String createTipisoggetto(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	AlberoprocTipisoggetto alberoprocTipisoggetto = new AlberoprocTipisoggetto();
	alberoprocTipisoggetto.setAlberoproc(alberoproc);
	model.addAttribute("alberoprocTipisoggetto", alberoprocTipisoggetto);
	model.addAttribute("alberoproc", alberoproc);
	setPageAttributes(model);
	fixRenderEntityPropertyAlberoprocTipisoggetto(alberoprocTipisoggetto);
	return "alberoproc/alberoproctipisoggetto";
    }

    @RequestMapping
    public String insertTipisoggetto(@ModelAttribute("alberoproc") Alberoproc alberoproc,
	    @ModelAttribute("alberoprocTipisoggetto") AlberoprocTipisoggetto alberoprocTipisoggetto, BindingResult result, SessionStatus status,
	    Model model, HttpServletRequest request) {

	if (EntityUtils.getNestedProperty(alberoprocTipisoggetto.getTipisoggetto(), "id.codice") != null) {
	    Tipisoggetto tipifamiglieendo = tipisoggettoService.findById(new PkId(alberoprocTipisoggetto.getTipisoggetto().getId().getCodice()));
	    alberoprocTipisoggetto.setTipisoggetto(tipifamiglieendo);
	}
	try {
	    alberoprocTipisoggettoService.insert(alberoprocTipisoggetto);
	} catch (Exception e) {
	    fixRenderEntityPropertyAlberoprocTipisoggetto(alberoprocTipisoggetto);
	    copyErrorsToBindingResult(result, alberoprocTipisoggetto, e);
	    model.addAttribute("alberoprocTipisoggetto", alberoprocTipisoggetto);
	    model.addAttribute("alberoproc", alberoproc);
	    return "alberoproc/alberoproctipisoggetto";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "#artipisoggetto_anchor";
    }

    private void fixRenderEntityPropertyAlberoprocTipisoggetto(AlberoprocTipisoggetto alberoprocTipisoggetto) {

	if (alberoprocTipisoggetto.getTipisoggetto() == null) {
	    alberoprocTipisoggetto.setTipisoggetto(new Tipisoggetto());
	}
	if (alberoprocTipisoggetto.getAlberoproc() == null) {
	    alberoprocTipisoggetto.setAlberoproc(new Alberoproc());
	}
    }

    @RequestMapping
    public String deleteTiposoggetto(Model model, @RequestParam("alberoproc.id.codice") Integer codicealberoproc,
	    @RequestParam("codice") Integer codice, HttpServletRequest request) {

	AlberoprocTipisoggetto alberoprocTipisoggetto = alberoprocTipisoggettoService.findById(new PkId(codice));
	try {
	    alberoprocTipisoggettoService.delete(alberoprocTipisoggetto);
	} catch (Exception e) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codicealberoproc));
	    this.setPageAttributes(model, alberoproc, request);
	    fixRenderEntityProperty(alberoproc);
	    model.addAttribute("alberoproc", alberoproc);
	    return "alberoproc/form";
	}
	return "redirect:view.htm?codice=" + codicealberoproc;
    }

    @RequestMapping
    public String createMetadato(@RequestParam("alberoproc.id.codice") Integer codicealberoproc, Model model, HttpServletRequest request) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codicealberoproc));
	MetadatoAlberoproc metadatoAlberoproc = new MetadatoAlberoproc(codicealberoproc, alberoproc.getDescrizioneCompleta(), null, null);
	model.addAttribute("metadatoAlberoproc", metadatoAlberoproc);
	model.addAttribute("alberoproc", alberoproc);
	model.addAttribute("listaMetadatiConfigurabili", alberoprocMetadatiService.listaMetadatiConfigurabili());
	setPageAttributes(model);
	return "alberoproc/alberoprocmetadati";
    }

    @RequestMapping
    public String deleteMetadato(Model model, @RequestParam("alberoproc.id.codice") Integer codicealberoproc, @RequestParam("chiave") String chiave,
	    HttpServletRequest request) {

	try {
	    this.alberoprocMetadatiService.delete(codicealberoproc, chiave);
	} catch (Exception e) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codicealberoproc));
	    this.setPageAttributes(model, alberoproc, request);
	    fixRenderEntityProperty(alberoproc);
	    model.addAttribute("alberoproc", alberoproc);
	    return "alberoproc/form";
	}
	return "redirect:view.htm?codice=" + codicealberoproc;
    }

    @RequestMapping
    public String insertMetadato(@ModelAttribute("alberoproc") Alberoproc alberoproc,
	    @ModelAttribute("alberoprocMetadati") MetadatoAlberoproc metadatoAlberoproc, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	try {
	    this.alberoprocMetadatiService.insert(metadatoAlberoproc);
	} catch (Exception e) {
	    fixRenderEntityProperty(alberoproc);
	    copyErrorsToBindingResult(result, metadatoAlberoproc, e);
	    model.addAttribute("metadatoAlberoproc", metadatoAlberoproc);
	    model.addAttribute("alberoproc", alberoproc);
	    model.addAttribute("listaMetadatiConfigurabili", alberoprocMetadatiService.listaMetadatiConfigurabili());
	    return "alberoproc/alberoprocmetadati";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "#albmetadati_anchor";
    }

    //////////////////////////////////
    @RequestMapping
    public String eliminaAteco(Model model, @RequestParam("alberoproc.id.codice") Integer codicealberoproc,
	    @RequestParam("codiceateco") Integer codiceateco, HttpServletRequest request) {

	AlberoprocAtecoId id = new AlberoprocAtecoId(codicealberoproc, codiceateco);
	AlberoprocAteco alberoprocAteco = alberoprocAtecoService.findById(id);
	try {
	    alberoprocAtecoService.delete(alberoprocAteco);
	} catch (Exception e) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codicealberoproc));
	    this.setPageAttributes(model, alberoproc, request);
	    fixRenderEntityProperty(alberoproc);
	    model.addAttribute("alberoproc", alberoproc);
	    return "alberoproc/form";
	}
	return "redirect:view.htm?codice=" + codicealberoproc;
    }

    /**
     * FUNZIONALITA' ALBEROPROC_DOCUMENTI
     */
    @RequestMapping
    public String createDocumenti(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request) {

	/*
	 * Recupero le categorie. In view viene effettuato il controllo se sono presenti categorie per il software
	 * corrente.
	 */
	List<AlberoprocDocumenticat> alberoprocDocumenticats = alberoprocDocumenticatService.findAll(null, null);
	model.addAttribute("alberoprocDocumenticats", alberoprocDocumenticats);
	/*
	 * Creo oggetto Tipodowload e popolo la lista di AlberoprocDocumenti
	 */
	List<TipoDownload> tipoDownloads = TipoDownload.getTipoDownloads();
	model.addAttribute("tipoDownloads", tipoDownloads);
	/*
	 * Determino l'ordine massimo e lo incremento di 10. L'incremento viene utilizzato per facilitare lo spostamento
	 * dei vari ordini.
	 */
	Integer ordine = alberoprocDocumentiService.findMaxOrder();
	ordine = ordine + 10;
	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	AlberoprocDocumenti alberoprocDocumenti = new AlberoprocDocumenti();
	alberoprocDocumenti.setAlberoproc(alberoproc);
	alberoprocDocumenti.setOrdine(ordine);
	model.addAttribute("alberoprocDocumenti", alberoprocDocumenti);
	model.addAttribute("alberoproc", alberoproc);
	setPageAttributes(model);
	model.addAttribute("alberoProcTipiSoggettoWrapper", findTipiSoggettoForDocumento(alberoproc));
	return "alberoproc/documenti";
    }
    
    private AlberoProcTipiSoggettoWrapper findTipiSoggettoForDocumento(Alberoproc entity){

	AlberoProcTipiSoggettoWrapper wrapper = new AlberoProcTipiSoggettoWrapper();
	wrapper.setAlberoProcTipiSoggettoBeans(new ArrayList<AlberoProcTipiSoggettoBean>());	
	
	String scCodice = entity.getScCodice();
	int lengthCodice = scCodice.length();
	int lengthTree = lengthCodice / 2;	

	Map<Integer,Tipisoggetto> tipisoggettoMap = new HashMap<Integer,Tipisoggetto>();
	
	if(entity.getAlberoprocTipisoggettos() != null && !entity.getAlberoprocTipisoggettos().isEmpty()){
	    for(AlberoprocTipisoggetto tiposogg : entity.getAlberoprocTipisoggettos()){
		tipisoggettoMap.put(tiposogg.getTipisoggetto().getId().getCodice(), tiposogg.getTipisoggetto());
	    }	
	}	
	for (int i = 0; i < lengthTree - 1; i++) {
		lengthCodice = lengthCodice - 2;
		String sccodicePadre = scCodice.substring(0, lengthCodice);
		Alberoproc alberoprocTemp = alberoprocService.findByScCodice(sccodicePadre);				
		if (alberoprocTemp.getAlberoprocTipisoggettos() != null && !alberoprocTemp.getAlberoprocTipisoggettos().isEmpty()) {		    
		    for(AlberoprocTipisoggetto tiposogg : alberoprocTemp.getAlberoprocTipisoggettos()){
			tipisoggettoMap.put(tiposogg.getTipisoggetto().getId().getCodice(), tiposogg.getTipisoggetto());
		    }		    
		}		
	}
	
	for(Map.Entry<Integer,Tipisoggetto> entry : tipisoggettoMap.entrySet()){
	    AlberoProcTipiSoggettoBean bean = new AlberoProcTipiSoggettoBean();
	    bean.setTipisoggetto(entry.getValue());
	    bean.setDocumentiTipiSoggettoChecked(false);
	    wrapper.getAlberoProcTipiSoggettoBeans().add(bean);
	}
	
	return wrapper;
    }
    
    /*
     * La logica di questo metodo sara questa: raccogliamo ricorsivamente i tipisoggetto come ci siamo detti, ma va controllato:
     * 1 che quelli in tabella docsoggfirmatari devono essere presenti, in questo modo risultano checked
     * 2 se un alberoproctiposoggetto viene eliminato nonostante il warning, fin quando il recor e presente in docsoggfirmatari sulla view
     * di un documento, deve risultare visibile e checkato
     */
    private AlberoProcTipiSoggettoWrapper findTipiSoggettoForDocumentoChecked(Alberoproc entity, Integer codicedocumento){
	
	AlberoprocDocumenti apDoc = alberoprocDocumentiService.findById(new PkId(codicedocumento));
	
	Set<AlberoprocDocSoggFirmatari> righe = apDoc.getAlberoprocDocSoggFirmataris();
	Set<Integer> codiciSoggettoSet = new HashSet<Integer>();
	for(AlberoprocDocSoggFirmatari riga : righe){
	    codiciSoggettoSet.add(riga.getTipisoggetto().getId().getCodice());
	}
	
	AlberoProcTipiSoggettoWrapper wrapper = findTipiSoggettoForDocumento(entity);
	Set<Integer> codiciSoggettoByRecursiveSet = new HashSet<Integer>();
	for(AlberoProcTipiSoggettoBean bean : wrapper.getAlberoProcTipiSoggettoBeans()){
	    codiciSoggettoByRecursiveSet.add(bean.getTipisoggetto().getId().getCodice());
	    if(codiciSoggettoSet.contains(bean.getTipisoggetto().getId().getCodice())){
		bean.setDocumentiTipiSoggettoChecked(true);
	    }
	}
	
	for(Integer codsoggetto : codiciSoggettoSet){
	    if(codiciSoggettoByRecursiveSet.contains(codsoggetto)){
		continue;
	    }
	    AlberoProcTipiSoggettoBean bean = new AlberoProcTipiSoggettoBean();
	    bean.setTipisoggetto(tipisoggettoService.findById(new PkId(codsoggetto)));
	    bean.setDocumentiTipiSoggettoChecked(true);
	    wrapper.getAlberoProcTipiSoggettoBeans().add(bean);
	}
	
	return wrapper;
    }

    @RequestMapping
    public String insertDocumenti(@ModelAttribute("alberoproc") Alberoproc alberoproc, 
	    @ModelAttribute("alberoprocDocumenti") AlberoprocDocumenti alberoprocDocumenti, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {
	
	/*
	 * Imposto le checbox
	 */
	HttpSession session = request.getSession(false);
	AlberoProcTipiSoggettoWrapper wrapper = (AlberoProcTipiSoggettoWrapper) session.getAttribute("alberoProcTipiSoggettoWrapper");
	String[] parameterValuesChck = request.getParameterValues("griglia_tipisoggetto_name");
	Set<Integer> chckBoxesSelected = new HashSet<Integer>();
	if(parameterValuesChck != null){
	    for(String s : parameterValuesChck){
		chckBoxesSelected.add(Integer.parseInt(s));
	    }
	}
	for(AlberoProcTipiSoggettoBean bean : wrapper.getAlberoProcTipiSoggettoBeans()){
	    bean.setDocumentiTipiSoggettoChecked(chckBoxesSelected.contains(bean.getTipisoggetto().getId().getCodice()));
	}
	

	/*
	 * Recupero le categorie. In view viene effettuato il controllo se sono presenti categorie per il software
	 * corrente.
	 */
	List<AlberoprocDocumenticat> alberoprocDocumenticats = alberoprocDocumenticatService.findAll(null, null);
	model.addAttribute("alberoprocDocumenticats", alberoprocDocumenticats);
	String[] valueType = request.getParameterValues("tipoDownloads");
	/*
	 * Creo oggetto Tipodowload e popolo la lista di AlberoprocDocumenti
	 */
	List<TipoDownload> tipoDownloads = TipoDownload.getTipoDownloads();
	model.addAttribute("tipoDownloads", tipoDownloads);
	try {
	    String foTipiDownload = "";
	    if (valueType != null) {
		for (String type : valueType) {
		    foTipiDownload = foTipiDownload.concat(type) + ",";
		}
		alberoprocDocumenti.setFoTipodownload(foTipiDownload);
	    } else {
		alberoprocDocumenti.setFoTipodownload(null);
	    }
	    
	    List<Tipisoggetto> tsList = new ArrayList<Tipisoggetto>();
	    for (AlberoProcTipiSoggettoBean bean : wrapper.getAlberoProcTipiSoggettoBeans()) {
		if (!bean.isDocumentiTipiSoggettoChecked()) {
		    continue;
		}
		tsList.add(bean.getTipisoggetto());
	    }
	    alberoprocDocumentiService.insert(alberoprocDocumenti, tsList, false);
	    
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocDocumenti, e);
	    fixRenderEntityPropertyAlberoprocDocumenti(alberoprocDocumenti);
	    return "alberoproc/documenti";
	}
	status.setComplete();
	return "redirect:viewDocumenti.htm?alberoprocDocumenti.id.codice=" + alberoprocDocumenti.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String viewDocumenti(@RequestParam("alberoprocDocumenti.id.codice") Integer codice, Model model, HttpServletRequest request) {

	/*
	 * Recupero le categorie. In view viene effettuato il controllo se sono presenti categorie per il software
	 * corrente.
	 */
	List<AlberoprocDocumenticat> alberoprocDocumenticats = alberoprocDocumenticatService.findAll(null, null);
	model.addAttribute("alberoprocDocumenticats", alberoprocDocumenticats);
	/*
	 * Creo oggetto Tipodowload e popolo la lista di AlberoprocDocumenti
	 */
	List<TipoDownload> tipoDownloads = TipoDownload.getTipoDownloads();
	model.addAttribute("tipoDownloads", tipoDownloads);
	AlberoprocDocumenti alberoprocDocumenti = alberoprocDocumentiService.findById(new PkId(codice));
	if (alberoprocDocumenti.getFoTipodownload() != null) {
	    String[] tipodown = alberoprocDocumenti.getFoTipodownload().split(",");
	    Set<TipoDownload> tipoDownloadList = TipoDownload.fromString(tipodown, tipoDownloads);
	    alberoprocDocumenti.setTipoDownloads(tipoDownloadList);
	}
	fixRenderEntityPropertyAlberoprocDocumenti(alberoprocDocumenti);
	model.addAttribute("alberoprocDocumenti", alberoprocDocumenti);
	Alberoproc alberoprocTemp = alberoprocService.findById(alberoprocDocumenti.getAlberoproc().getId());
	model.addAttribute("alberoproc", alberoprocTemp);
	model.addAttribute("alberoProcTipiSoggettoWrapper", findTipiSoggettoForDocumentoChecked(alberoprocTemp, codice));
	return "alberoproc/documenti";
    }

    private void fixRenderEntityPropertyAlberoprocDocumenti(AlberoprocDocumenti entity) {

	if (entity.getOggetto() == null) {
	    entity.setOggetto(new Oggetti());
	}
	if (entity.getAlberoprocDocumenticat() == null) {
	    entity.setAlberoprocDocumenticat(new AlberoprocDocumenticat());
	}
    }

    private void fixRenderEntityPropertyAlberoprocArendo(AlberoprocArendo entity) {

	if (entity.getTipiendo() == null) {
	    entity.setTipiendo(new Tipiendo());
	}
	if (entity.getTipifamiglieendo() == null) {
	    entity.setTipifamiglieendo(new Tipifamiglieendo());
	}
	if (entity.getAlberoproc() == null) {
	    entity.setAlberoproc(new Alberoproc());
	}
    }

    @RequestMapping
    public String updateDocumenti(@ModelAttribute("alberoproc") Alberoproc alberoproc, 
	    @ModelAttribute("alberoprocDocumenti") AlberoprocDocumenti alberoprocDocumenti, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	/*
	 * Imposto le checbox
	 */
	HttpSession session = request.getSession(false);
	AlberoProcTipiSoggettoWrapper wrapper = (AlberoProcTipiSoggettoWrapper) session.getAttribute("alberoProcTipiSoggettoWrapper");
	String[] parameterValuesChck = request.getParameterValues("griglia_tipisoggetto_name");
	Set<Integer> chckBoxesSelected = new HashSet<Integer>();
	if(parameterValuesChck != null){
	    for(String s : parameterValuesChck){
		chckBoxesSelected.add(Integer.parseInt(s));
	    }
	}
	for(AlberoProcTipiSoggettoBean bean : wrapper.getAlberoProcTipiSoggettoBeans()){
	    bean.setDocumentiTipiSoggettoChecked(chckBoxesSelected.contains(bean.getTipisoggetto().getId().getCodice()));
	}
	
	
	
	String[] valueType = request.getParameterValues("tipoDownloads");
	/*
	 * Creo oggetto Tipodowload e popolo la lista di AlberoprocDocumenti
	 */
	List<TipoDownload> tipoDownloads = TipoDownload.getTipoDownloads();
	model.addAttribute("tipoDownloads", tipoDownloads);
	try {
	    if (valueType != null) {
		String foTipiDownload = "";
		for (String type : valueType) {
		    foTipiDownload = foTipiDownload.concat(type) + ",";
		}
		alberoprocDocumenti.setFoTipodownload(foTipiDownload);
	    } else {
		alberoprocDocumenti.setFoTipodownload(null);
	    }	    
	    
	    List<Tipisoggetto> tsList = new ArrayList<Tipisoggetto>();
	    for (AlberoProcTipiSoggettoBean bean : wrapper.getAlberoProcTipiSoggettoBeans()) {
		if (!bean.isDocumentiTipiSoggettoChecked()) {
		    continue;
		}
		tsList.add(bean.getTipisoggetto());
	    }
	    alberoprocDocumentiService.insert(alberoprocDocumenti, tsList, true);
	    
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocDocumenti, e);
	    fixRenderEntityPropertyAlberoprocDocumenti(alberoprocDocumenti);
	    return "alberoproc/documenti";
	}
	status.setComplete();
	return "redirect:viewDocumenti.htm?alberoprocDocumenti.id.codice=" + alberoprocDocumenti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String eliminaDocumento(Model model, @RequestParam("alberoproc.id.codice") Integer codicealberoproc,
	    @RequestParam("codicedocumento") Integer codice, HttpServletRequest request) {

	AlberoprocDocumenti alberoprocDocumenti = alberoprocDocumentiService.findById(new PkId(codice));
	try {
	    alberoprocDocumentiService.delete(alberoprocDocumenti);
	} catch (Exception e) {
	    log.error("eliminaDocumento codicealberoproc={}, codicedocumento={}", new Object[] { codicealberoproc, codice, e });
	    FlashMessages.getWarnings().add(e.getMessage());
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codicealberoproc));
	    this.setPageAttributes(model, alberoproc, request);
	    fixRenderEntityProperty(alberoproc);
	    model.addAttribute("alberoproc", alberoproc);
	    return "alberoproc/form";
	}
	return "redirect:view.htm?codice=" + codicealberoproc;
    }

    @RequestMapping
    public String listSoggettoADocumento(@RequestParam("codiceDocumento") Integer codice, Model model, HttpServletRequest request) {

	return null;
    }

    @RequestMapping
    public String aggiungiSoggettoADocumento(@RequestParam("codiceDocumento") Integer codice, Model model, HttpServletRequest request) {

	/*
	 * Recupero le categorie. In view viene effettuato il controllo se sono presenti categorie per il software
	 * corrente.
	 */
	List<AlberoprocDocumenticat> alberoprocDocumenticats = alberoprocDocumenticatService.findAll(null, null);
	model.addAttribute("alberoprocDocumenticats", alberoprocDocumenticats);
	/*
	 * Creo oggetto Tipodowload e popolo la lista di AlberoprocDocumenti
	 */
	List<TipoDownload> tipoDownloads = TipoDownload.getTipoDownloads();
	model.addAttribute("tipoDownloads", tipoDownloads);
	/*
	 * Determino l'ordine massimo e lo incremento di 10. L'incremento viene utilizzato per facilitare lo spostamento
	 * dei vari ordini.
	 */
	Integer ordine = alberoprocDocumentiService.findMaxOrder();
	ordine = ordine + 10;
	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	AlberoprocDocumenti alberoprocDocumenti = new AlberoprocDocumenti();
	alberoprocDocumenti.setAlberoproc(alberoproc);
	alberoprocDocumenti.setOrdine(ordine);
	model.addAttribute("alberoprocDocumenti", alberoprocDocumenti);
	model.addAttribute("alberoproc", alberoproc);
	setPageAttributes(model);
	return "alberoproc/documenti";
    }

    /**
     * FUNZIONALITA' ALBEROPROC_ONERI
     */
    @RequestMapping
    public ModelMap listOneri(@RequestParam("alberoproc.id.codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Alberoproc alberoprocTemp = alberoprocService.findById(new PkId(codice));
	List<AlberoprocOneri> alberoprocOneris = alberoprocOneriService.findAllByAlberoproc(codice);
	ModelMap model = new ModelMap(alberoprocOneris);
	model.addAttribute("alberoprocOneriList", alberoprocOneris);
	model.addAttribute("alberoproc", alberoprocTemp);
	return model;
    }

    @RequestMapping
    public String deleteOneri(Model model, @ModelAttribute("alberoprocOneri") AlberoprocOneri alberoprocOneri, BindingResult result,
	    SessionStatus status) {

	AlberoprocOneri objToDelete = alberoprocOneriService.findById(alberoprocOneri.getId());
	try {
	    alberoprocOneriService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    return "alberoproc/form";
	}
	status.setComplete();
	return "redirect:listOneri.htm?alberoproc.id.codice=" + objToDelete.getAlberoproc().getId().getCodice();
    }

    @RequestMapping
    public String insertOneri(Model model, @ModelAttribute("alberoprocOneri") AlberoprocOneri alberoprocOneri, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityPropertyOneri(alberoprocOneri);
	try {
	    alberoprocOneriService.insert(alberoprocOneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocOneri, e);
	    fixRenderEntityPropertyOneri(alberoprocOneri);
	    return "alberoproc/oneri";
	}
	status.setComplete();
	return "redirect:viewOneri.htm?alberoprocOneri.id.codice=" + alberoprocOneri.getId().getCodice() + "&status_msg=01";
    }

    private void fixRenderEntityPropertyOneri(AlberoprocOneri entity) {

	if (entity.getTipicausalioneri() == null) {
	    entity.setTipicausalioneri(new Tipicausalioneri());
	}
	if (entity.getDyn2Campi() == null) {
	    entity.setDyn2Campi(new Dyn2Campi());
	}
	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
    }

    private void fixMergeEntityPropertyOneri(AlberoprocOneri entity) {

	if (entity.getTipicausalioneri() != null && entity.getTipicausalioneri().getId() != null
		&& entity.getTipicausalioneri().getId().getCodice() == null) {
	    entity.setTipicausalioneri(null);
	}
    }

    @RequestMapping
    public String updateOneri(Model model, @ModelAttribute("alberoprocOneri") AlberoprocOneri alberoprocOneri, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityPropertyOneri(alberoprocOneri);
	try {
	    alberoprocOneriService.update(alberoprocOneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocOneri, e);
	    fixRenderEntityPropertyOneri(alberoprocOneri);
	    return "alberoproc/oneri";
	}
	status.setComplete();
	return "redirect:viewOneri.htm?alberoprocOneri.id.codice=" + alberoprocOneri.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String createOneri(@RequestParam("alberoproc.id.codice") Integer codice, Model model) {

	Alberoproc alberoprocTemp = alberoprocService.findById(new PkId(codice));
	AlberoprocOneri alberoprocOneri = new AlberoprocOneri();
	alberoprocOneri.setAlberoproc(alberoprocTemp);
	fixRenderEntityPropertyOneri(alberoprocOneri);
	Boolean isImportoIstruttoriaImpostabile = Boolean.FALSE;
	model.addAttribute("isImportoIstruttoriaImpostabile", isImportoIstruttoriaImpostabile);
	model.addAttribute("alberoprocOneri", alberoprocOneri);
	setPageAttributes(model);
	return "alberoproc/oneri";
    }

    @RequestMapping
    public String viewOneri(@RequestParam("alberoprocOneri.id.codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	AlberoprocOneri alberoprocOneri = alberoprocOneriService.findById(id);
	fixRenderEntityPropertyOneri(alberoprocOneri);
	Alberoproc alberoprocTemp = alberoprocService.findById(alberoprocOneri.getAlberoproc().getId());
	model.addAttribute("alberoproc", alberoprocTemp);
	model.addAttribute("alberoprocOneri", alberoprocOneri);
	Boolean isImportoIstruttoriaImpostabile = tipicausalioneriService.isImportoIstruttoriaImpostabile(alberoprocOneri.getTipicausalioneri());
	model.addAttribute("isImportoIstruttoriaImpostabile", isImportoIstruttoriaImpostabile);
	setPageAttributes(model);
	return "alberoproc/oneri";
    }

    /**
     * FUNZIONALITA' ALBEROPROC_ENDO
     */
    @RequestMapping
    public String createEndo(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request) {

	List<Azioni> azionis = new ArrayList<Azioni>();
	azionis = azioniService.findAll(null, null);
	model.addAttribute("azionis", azionis);
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	model.addAttribute("alberoproc", alberoproc);
	AlberoprocEndo alberoprocEndo = new AlberoprocEndo();
	AlberoprocEndoId id = new AlberoprocEndoId();
	id.setFkscid(codice);
	alberoprocEndo.setId(id);
	alberoprocEndo.setAlberoproc(alberoproc);
	fixRenderEntityPropertyEndo(alberoprocEndo);
	model.addAttribute("alberoprocEndo", alberoprocEndo);
	return "alberoproc/endo";
    }

    @RequestMapping
    public String insertEndo(@RequestParam("alberoproc.id.codice") Integer codice, Model model,
	    @ModelAttribute("alberoprocEndo") AlberoprocEndo alberoprocEndo, BindingResult result, SessionStatus status, HttpServletRequest request) {

	List<Azioni> azionis = new ArrayList<Azioni>();
	if (!ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_CE)) {
	    azionis = azioniService.findAll(null, null);
	}
	model.addAttribute("azionis", azionis);
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	model.addAttribute("alberoproc", alberoproc);
	fixMergeEntityPropertyEndo(alberoprocEndo);
	try {
	    alberoprocEndoService.insert(alberoprocEndo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocEndo, e);
	    fixRenderEntityPropertyEndo(alberoprocEndo);
	    alberoprocEndo.setInventarioprocedimento(new Inventarioprocedimenti());
	    model.addAttribute("alberoprocEndo", alberoprocEndo);
	    return "alberoproc/endo";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "&status_msg=01";
    }

    private void fixRenderEntityPropertyEndo(AlberoprocEndo entity) {

	if (entity.getInventarioprocedimento() == null) {
	    entity.setInventarioprocedimento(new Inventarioprocedimenti());
	}
	if (entity.getAlberoproc() == null) {
	    entity.setAlberoproc(new Alberoproc());
	}
	if (entity.getAzione() == null) {
	    entity.setAzione(new Azioni());
	}
    }

    private void fixMergeEntityPropertyEndo(AlberoprocEndo entity) {

	if (entity.getInventarioprocedimento() != null && entity.getInventarioprocedimento().getId() != null
		&& entity.getInventarioprocedimento().getId().getCodice() == null) {
	    entity.setInventarioprocedimento(null);
	}
	if (entity.getAlberoproc() != null && entity.getAlberoproc().getId() != null && entity.getAlberoproc().getId().getCodice() == null) {
	    entity.setAlberoproc(null);
	}
	if (entity.getAzione().getAzId().compareTo(0) == 0) {
	    entity.setAzione(null);
	}
    }

    @RequestMapping
    public ModelMap listEndo(@RequestParam("alberoproc.id.codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Alberoproc alberoprocTemp = alberoprocService.findById(new PkId(codice));
	List<AlberoprocEndo> alberoprocEndos = alberoprocEndoService.findAllByAlberoproc(codice);
	ModelMap model = new ModelMap(alberoprocEndos);
	model.addAttribute("alberoprocEndosList", alberoprocEndos);
	model.addAttribute("alberoproc", alberoprocTemp);
	return model;
    }

    @RequestMapping
    public void ajaxFindProgressivo(@RequestParam("codiceAlberoProc") Integer codiceAlberoProc, HttpServletRequest request,
	    HttpServletResponse response) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceAlberoProc));
	if (alberoproc == null) {
	    return;
	}
	//	String progressivo = alberoprocService.findProgressivo(alberoproc);
	String progressivo = istanzeService.findProgressivoIstanza(codiceAlberoProc, false);
	try {
	    response.getWriter().write(progressivo);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping
    public String deleteEndo(Model model, @RequestParam("alberoproc.id.codice") Integer codiceAlberoproc,
	    @RequestParam("codiceinventario") Integer codiceInventario, HttpServletRequest request) {

	AlberoprocEndoId id = new AlberoprocEndoId();
	id.setFkscid(codiceAlberoproc);
	id.setCodiceinventario(codiceInventario);
	AlberoprocEndo objToDelete = alberoprocEndoService.findById(id);
	try {
	    alberoprocEndoService.delete(objToDelete);
	} catch (Exception e) {
	    return "redirect:listEndo.htm?alberoproc.id.codice=" + objToDelete.getAlberoproc().getId().getCodice();
	}
	return "redirect:view.htm?codice=" + objToDelete.getAlberoproc().getId().getCodice();
    }

    // GESTIONE DEI MODELLI
    @RequestMapping
    public ModelMap listmodelli(@RequestParam("codiceprocedimento") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	List<AlberoprocDyn2modellit> alberoprocDyn2modellits = alberoprocDyn2modellitService.findByAlberoProc(codice);
	ModelMap model = new ModelMap(alberoprocDyn2modellits);
	boolean export = createJMesaExport(request, response, alberoprocDyn2modellits);
	if (export) {
	    return null;
	}
	String scCodice = alberoproc.getScCodice();
	List<AlberoprocHelper> alberoprocHelpers = alberoprocService.findModelliTIstanzaEreditati(scCodice);
	model.addAttribute("alberoprocHelpers", alberoprocHelpers);
	model.addAttribute("alberoprocDyn2modellits", alberoprocDyn2modellits);
	model.addAttribute("alberoproc", alberoproc);
	return model;
    }

    @RequestMapping
    public ModelMap comuniesclusi(@RequestParam("codiceprocedimento") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	List<ComuniEsclusi> comuniEsclusi = this.alberoprocComuniEsclusiService.findByAlberoProc(alberoproc.getScCodice());
	ModelMap model = new ModelMap(comuniEsclusi);
	model.addAttribute("comuniEsclusi", comuniEsclusi);
	model.addAttribute("alberoproc", alberoproc);
	model.addAttribute("elencoComuni", this.comuniassociatiService.findAll());
	return model;
    }

    @RequestMapping
    public String createmodelli(@RequestParam("codiceprocedimento") Integer codice, Model model) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	AlberoprocDyn2modellit alberoprocDyn2modellit = new AlberoprocDyn2modellit();
	alberoprocDyn2modellit.setAlberoproc(alberoproc);
	// setto l'id
	AlberoprocDyn2modellitId id = new AlberoprocDyn2modellitId();
	id.setFkScId(codice);
	alberoprocDyn2modellit.setId(id);
	fixRenderAlberoprocDyn2modellitProperty(alberoprocDyn2modellit);
	model.addAttribute("alberoprocDyn2modellit", alberoprocDyn2modellit);
	setPageAttributes(model);
	return "alberoproc/formModelli";
    }

    @RequestMapping
    public String viewModelli(@RequestParam("codiceprocedimento") Integer codice, @RequestParam("codicemodello") Integer codicemodello, Model model) {

	AlberoprocDyn2modellitId id = new AlberoprocDyn2modellitId(ORMHelper.getIdcomune(), codice, codicemodello);
	AlberoprocDyn2modellit alberoprocDyn2modellit = alberoprocDyn2modellitService.findById(id);
	fixRenderAlberoprocDyn2modellitProperty(alberoprocDyn2modellit);
	Boolean flagmultiplo = Boolean.TRUE;
	if (alberoprocDyn2modellit.getFlagTipofirma() != null && alberoprocDyn2modellit.getFlagTipofirma().equals(2)) {
	    if (EntityUtils.getNestedProperty(alberoprocDyn2modellit.getDyn2Modellit(), "id.codice") != null) {
		if (alberoprocDyn2modellit.getDyn2Modellit().getDyn2Modellids() != null
			&& !alberoprocDyn2modellit.getDyn2Modellit().getDyn2Modellids().isEmpty()) {
		    Set<Dyn2Modellid> dyn2Modellids = alberoprocDyn2modellit.getDyn2Modellit().getDyn2Modellids();
		    for (Dyn2Modellid dyn2Modellid : dyn2Modellids) {
			if (dyn2Modellid.getFlgMultiplo() == null || !dyn2Modellid.getFlgMultiplo()) {
			    flagmultiplo = Boolean.FALSE;
			}
		    }
		}
	    }
	}
	if (!flagmultiplo) {
	    model.addAttribute("flagmultiplo", flagmultiplo);
	}
	model.addAttribute("view", Boolean.TRUE);
	model.addAttribute("alberoprocDyn2modellit", alberoprocDyn2modellit);
	setPageAttributes(model);
	return "alberoproc/formModelli";
    }

    @RequestMapping
    public String insertModelli(Model model, @ModelAttribute("alberoprocDyn2modellit") AlberoprocDyn2modellit alberoprocDyn2modellit,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "alberoproc/formModelli";
	}
	// recupero i campi ajax
	if (alberoprocDyn2modellit.getDyn2Modellit() != null && alberoprocDyn2modellit.getDyn2Modellit().getId().getCodice() != null) {
	    Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(alberoprocDyn2modellit.getDyn2Modellit().getId());
	    AlberoprocDyn2modellitId id = alberoprocDyn2modellit.getId();
	    id.setFkD2mtId(alberoprocDyn2modellit.getDyn2Modellit().getId().getCodice());
	    alberoprocDyn2modellit.setId(id);
	    alberoprocDyn2modellit.setDyn2Modellit(dyn2Modellit);
	}
	fixMergeAlberoprocDyn2modellitProperty(alberoprocDyn2modellit);
	try {
	    alberoprocDyn2modellitService.insert(alberoprocDyn2modellit);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocDyn2modellit, e);
	    fixRenderAlberoprocDyn2modellitProperty(alberoprocDyn2modellit);
	    model.addAttribute("alberoprocDyn2modellit", alberoprocDyn2modellit);
	    return "alberoproc/formModelli";
	}
	status.setComplete();
	return "redirect:viewModelli.htm?codiceprocedimento=" + alberoprocDyn2modellit.getAlberoproc().getId().getCodice() + "&codicemodello=" +
	       alberoprocDyn2modellit.getId().getFkD2mtId() + "&status_msg=01";
    }

    @RequestMapping
    public String updateModelli(Model model, @ModelAttribute("alberoprocDyn2modellit") AlberoprocDyn2modellit alberoprocDyn2modellit,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "alberoproc/formModelli";
	}
	fixMergeAlberoprocDyn2modellitProperty(alberoprocDyn2modellit);
	try {
	    alberoprocDyn2modellitService.update(alberoprocDyn2modellit);
	} catch (Exception e) {
	    model.addAttribute("view", Boolean.TRUE);
	    copyErrorsToBindingResult(result, alberoprocDyn2modellit, e);
	    model.addAttribute("alberoprocDyn2modellit", alberoprocDyn2modellit);
	    fixRenderAlberoprocDyn2modellitProperty(alberoprocDyn2modellit);
	    return "alberoproc/formModelli";
	}
	status.setComplete();
	return "redirect:viewModelli.htm?codiceprocedimento=" + alberoprocDyn2modellit.getAlberoproc().getId().getCodice() + "&codicemodello=" +
	       alberoprocDyn2modellit.getId().getFkD2mtId() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteModelli(@ModelAttribute("alberoprocDyn2modellit") AlberoprocDyn2modellit alberoprocDyn2modellit, BindingResult result,
	    SessionStatus status) {

	AlberoprocDyn2modellit objToDelete = alberoprocDyn2modellitService.findById(alberoprocDyn2modellit.getId());
	try {
	    alberoprocDyn2modellitService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderAlberoprocDyn2modellitProperty(objToDelete);
	    return "alberoproc/formModelli";
	}
	status.setComplete();
	return "redirect:listmodelli.htm?codiceprocedimento=" + objToDelete.getAlberoproc().getId().getCodice();
    }

    // GESTIONE DEGLI ENDOPROCEDIMENTI DI UN PROCEDIMENTO INCOMPATIBILI TRA LORO
    @RequestMapping
    public String listincompatibili(@RequestParam("codiceendo") Integer codice, @RequestParam("codicealberoproc") Integer codiceAlberoproc,
	    Model model, HttpServletRequest request) {

	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codice));
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceAlberoproc));
	VwAlberoproc vwAlberoproc = vwAlberoprocService.findById(new PkId(codiceAlberoproc));
	AlberoprocCommand alberoprocCommand = new AlberoprocCommand();
	Set<AlberoprocEndo> alberoprocEndos = alberoproc.getAlberoprocEndos();
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	// alla collezione degli endo procedimenti configurati per il procedimento devo sottrarre quello che sto///
	// valutando///////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	AlberoprocEndoId id = new AlberoprocEndoId(ORMHelper.getIdcomune(), codiceAlberoproc, codice);
	AlberoprocEndo alberoprocEndo = alberoprocEndoService.findById(id);
	alberoprocEndos.remove(alberoprocEndo);
	alberoproc.setAlberoprocEndos(alberoprocEndos);
	// setto tutti i valori di cui ho bisogno sul command
	alberoprocCommand.setInventarioprocedimenti(inventarioprocedimenti);
	alberoprocCommand.setVwAlberoproc(vwAlberoproc);
	alberoprocCommand.setAlberoproc(alberoproc);
	for (AlberoprocEndo alberoprocEndoTemp : alberoprocEndos) {
	    Set<Inventarioprocedimentiincomp> list = inventarioprocedimenti.getInventarioprocedimentiincomps();
	    for (Inventarioprocedimentiincomp inventarioprocedimentiincomp : list) {
		if (inventarioprocedimentiincomp.getInventarioprocedimentoincompatibile().equals(alberoprocEndoTemp.getInventarioprocedimento())) {
		    alberoprocEndoTemp.getInventarioprocedimento().setFlagIncompatibile(true);
		    break;
		}
	    }
	}
	request.setAttribute("endoList", alberoprocCommand.getAlberoproc().getAlberoprocEndos());
	model.addAttribute("alberoprocCommand", alberoprocCommand);
	return "alberoproc/listincompatibili";
    }

    /**
     * Il metodo andrà a rimuovere o aggiungere compatibilità dell'endoprocedimento scelto con gli altri
     * endoproecedimenti configurati sullo stesso procedimento
     * 
     * @param model
     * @param alberoprocDyn2modellit
     * @param result
     * @param status
     * @return
     */
    @RequestMapping
    public String addOrRemoveIncompatibilitaendo(Model model, @ModelAttribute("alberoprocCommand") AlberoprocCommand alberoprocCommand,
	    BindingResult result, SessionStatus status) {

	Inventarioprocedimenti inventarioprocedimenti = alberoprocCommand.getInventarioprocedimenti();
	String codiciDegliEndoIncompatibili = alberoprocCommand.getListaDiCodiciDegliEndoprocedimentiIncompatibili();
	String codiciTotaliDegliEndoPerUnProcedimento = alberoprocCommand.getListaDiCodiciDeiEndoPerUnProcedimento();
	inventarioprocedimentiincompService.addOrRemoveIncompatibilitaendo(inventarioprocedimenti, codiciDegliEndoIncompatibili,
		codiciTotaliDegliEndoPerUnProcedimento);
	status.setComplete();
	return "redirect:listincompatibili.htm?codiceendo=" + inventarioprocedimenti.getId().getCodice() + "&codicealberoproc=" +
	       alberoprocCommand.getAlberoproc().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(Alberoproc entity) {

	if (entity.getTipologiaregistro() != null && entity.getTipologiaregistro().getId() != null
		&& entity.getTipologiaregistro().getId().getCodice() == null) {
	    entity.setTipologiaregistro(null);
	}
	if (entity.getTipoProcedura() != null && entity.getTipoProcedura().getId() != null && entity.getTipoProcedura().getId().getCodice() == null) {
	    entity.setTipoProcedura(null);
	}
	if (entity.getAzione() != null && entity.getAzione().getAzId() == null) {
	    entity.setAzione(null);
	}
	if (entity.getResponsabile() != null && entity.getResponsabile().getId() != null && entity.getResponsabile().getId().getCodice() == null) {
	    entity.setResponsabile(null);
	}
	if (entity.getRespistruttoria() != null && entity.getRespistruttoria().getId() != null
		&& entity.getRespistruttoria().getId().getCodice() == null) {
	    entity.setRespistruttoria(null);
	}
	if (entity.getRiTipiintervento() != null && StringUtils.isBlank(entity.getRiTipiintervento().getCodice())) {
	    entity.setRiTipiintervento(null);
	}
	if (entity.getFoArjStepsTestata() != null && entity.getFoArjStepsTestata().getId() == null
		&& entity.getFoArjStepsTestata().getId().getCodice() != null) {
	    entity.setFoArjStepsTestata(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Alberoproc entity) {

	if (entity.getAmministrazioni() == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
	if (entity.getMercato() == null) {
	    entity.setMercato(new Mercati());
	}
	if (entity.getMercatoUso() == null) {
	    entity.setMercatoUso(new MercatiUso());
	}
	if (entity.getTipologiaregistro() == null) {
	    entity.setTipologiaregistro(new Tipologiaregistri());
	}
	if (entity.getTipoProcedura() == null) {
	    entity.setTipoProcedura(new Tipiprocedure());
	}
	if (entity.getAzione() == null) {
	    entity.setAzione(new Azioni());
	}
	if (entity.getResponsabile() == null) {
	    entity.setResponsabile(new Responsabili());
	}
	if (entity.getRespistruttoria() == null) {
	    entity.setRespistruttoria(new Responsabili());
	}
	if (entity.getOperatoreStc() == null) {
	    entity.setOperatoreStc(new Responsabili());
	}
	if (entity.getOggettoWorkflowAreaRis() == null) {
	    entity.setOggettoWorkflowAreaRis(new Oggetti());
	}
	if (entity.getRiTipiintervento() == null) {
	    entity.setRiTipiintervento(new RiTipiintervento());
	}
	if (entity.getFoArjStepsTestata() == null) {
	    entity.setFoArjStepsTestata(new FoArjStepsTestata());
	}
	if (entity.getGruppiIstruttori() == null) {
	    entity.setGruppiIstruttori(new GruppiIstruttori());
	}
	if (entity.getLdpOccupazionis() == null) {
	    entity.setLdpOccupazionis(new LdpDecodifiche());
	}
	if (entity.getLdpGeometries() == null) {
	    entity.setLdpGeometries(new LdpDecodifiche());
	}
	if (entity.getLdpPeriodis() == null) {
	    entity.setLdpPeriodis(new LdpDecodifiche());
	}
    }

    private void fixRenderAlberoprocDyn2modellitProperty(AlberoprocDyn2modellit entity) {

	if (entity.getAlberoproc() == null) {
	    entity.setAlberoproc(new Alberoproc());
	}
	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
    }

    private void fixMergeAlberoprocDyn2modellitProperty(AlberoprocDyn2modellit entity) {

	if (entity.getAlberoproc() != null && entity.getAlberoproc().getId() != null && entity.getAlberoproc().getId().getCodice() == null) {
	    entity.setAlberoproc(null);
	}
	if (entity.getDyn2Modellit() != null && entity.getDyn2Modellit().getId() != null && entity.getDyn2Modellit().getId().getCodice() == null) {
	    entity.setDyn2Modellit(null);
	}
    }

    /**
     * Metodo per settare nel model tutti i parametri necessari per la pagina di Alberoproc.<br/>
     * Sostituisce il metodo: protected void setPageAttributes(Model model);
     * 
     * @param model
     * @param entity
     * @param request
     */
    private void setPageAttributes(Model model, Alberoproc entity, HttpServletRequest request) {

	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ALBEROPROC_LIST_ATECO, "0", request);
	model.addAttribute("CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE, "1", request)));
	model.addAttribute("CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE",
		leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE, "0", request));
	/*
	 * Lista TIPOLOGIA REGISTRI
	 */
	model.addAttribute("tipologiaregistriList", tipologiaregistriService.findAll(null, null));
	/*
	 * Lista AZIONI
	 */
	model.addAttribute("azioniList", azioniService.findAll(null, null));
	/*
	 * Controllo se in configurazione è attivo il progrossivo per l'istanze
	 */
	ConfigurazioneId configurazioneId = new ConfigurazioneId(ORMHelper.getIdcomune(), ORMHelper.getSoftware());
	Configurazione configurazione = configurazioneService.findById(configurazioneId);
	if (configurazione == null) {
	    throw new RuntimeException("Attenzione! Non è stata trovata la configurazione per il software [" + ORMHelper.getSoftware() + "]");
	}
	if (BooleanUtils.isTrue(configurazione.getFlagAttivacontatorealberoproc())) {
	    model.addAttribute("conf_contatore", true);
	} else {
	    model.addAttribute("conf_contatore", false);
	}
	List<LdpDecodifiche> ldpOccupazionis = ldpDecodificheService.findByContesto(WebConstants.LDP_DECODIFICHE_CONTESTI.OCCUPAZIONE.name());
	List<LdpDecodifiche> ldpGeometries = ldpDecodificheService.findByContesto(WebConstants.LDP_DECODIFICHE_CONTESTI.GEOMETRIA.name());
	List<LdpDecodifiche> ldpPeriodis = ldpDecodificheService.findByContesto(WebConstants.LDP_DECODIFICHE_CONTESTI.PERIODO.name());
	model.addAttribute("ldpOccupazionis", ldpOccupazionis);
	model.addAttribute("ldpGeometries", ldpGeometries);
	model.addAttribute("ldpPeriodis", ldpPeriodis);
	boolean isLdpContesti = false;
	if (!(ldpOccupazionis.isEmpty() || ldpGeometries.isEmpty() || ldpPeriodis.isEmpty())) {
	    isLdpContesti = true;
	}
	model.addAttribute("isLdpContesti", isLdpContesti);
	/*
	 * Controllo se è attiva la verticalizzazione con il modulo REPLICAISTANZE
	 */
	Verticalizzazioni verticalizzazioni = verticalizzazioniService.findByModulo(WebConstants.VERTICALIZZAZIONE_REPLICAISTANZE);
	if (verticalizzazioni != null) {
	    if (verticalizzazioni.getAttivo() == 1) {
		model.addAttribute("vert_replicaistanze_attivo", true);
	    } else {
		model.addAttribute("vert_replicaistanze_attivo", false);
	    }
	} else {
	    model.addAttribute("vert_replicaistanze_attivo", false);
	}
	boolean verticalizzazioni_OSSERVATORIO_REGIONALE = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_OSSERVATORIO_REGIONALE);
	model.addAttribute("vert_osservatorio_attivo", verticalizzazioni_OSSERVATORIO_REGIONALE);
	boolean verticalizzazioni_OSSERVATORIO_FVG = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_OSSERVATORIO_FVG);
	model.addAttribute("vert_osservatorio_fvg_attivo", verticalizzazioni_OSSERVATORIO_FVG);
	/*
	 * Controllo se è attiva la verticalizzazione con il modulo PROTOCOLLO_ATTIVO
	 */
	boolean verticalizzazioni_PROTOCOLLO_ATTIVO = verticalizzazioniService
		.isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE);
	model.addAttribute("vert_prot_attivo", verticalizzazioni_PROTOCOLLO_ATTIVO);
	/*
	 * Controllo se sono presenti dei mercati attivi per il software corrente
	 */
	List<Mercati> mercatis = mercatiService.findAllMercatiAttivi(null, null);
	if (mercatis.isEmpty()) {
	    model.addAttribute("mercati_attivi", false);
	} else {
	    model.addAttribute("mercati_attivi", true);
	}
	if (!EntityUtils.isNestedPropertyBlank(entity, "id.codice")) {
	    /*
	     * Recupero la lista delle endoprocedimenti collegati alla voce dell'albero
	     */
	    List<AlberoprocEndo> alberoprocEndos = alberoprocEndoService.findAllByAlberoproc(entity.getId().getCodice());
	    model.addAttribute("alberoprocEndosList", alberoprocEndos);
	    /*
	     * Recupero la lista delle leggi ordinate per descrizione legge
	     */
	    List<AlberoprocLeggi> alberoprocLeggis = alberoprocLeggiService.findByAlberoProc(entity.getId().getCodice());
	    model.addAttribute("alberoprocLeggiList", alberoprocLeggis);
	    /*
	     * Recupero la lista delle documenti ordinati per ordine (asc) e descrizione (asc)
	     */
	    List<AlberoprocDocumenti> alberoprocDocumentis = alberoprocDocumentiService.findByAlberoProc(entity.getId().getCodice());
	    model.addAttribute("alberoprocDocumentiList", alberoprocDocumentis);
	    /*
	     * Recupero la lista degli endo procedimenti per l'area riservata
	     */
	    List<AlberoprocArendo> alberoprocArendos = alberoprocArendoService.findByAlberoProc(entity.getId().getCodice());
	    model.addAttribute("alberoprocArendos", alberoprocArendos);
	    /*
	     * Recupero degli ATECO associati alla voce dell'albero passata ordinati per codice (asc)
	     */
	    List<AlberoprocAteco> alberoprocAtecos = alberoprocAtecoService.findByAlberoproc(entity);
	    model.addAttribute("alberoprocAtecos", alberoprocAtecos);
	    /*
	     * Recupero degli TIPISOGGETTO associati alla voce dell'albero passata ordinati per codice (asc)
	     */
	    List<AlberoprocTipisoggetto> alberoprocTipisoggettos = alberoprocTipisoggettoService.findByAlberoprocId(entity.getId().getCodice(), null,
		    null);
	    model.addAttribute("alberoprocTipisoggettos", alberoprocTipisoggettos);
	    List<AlberoprocMovimenti> findByAlberoprocId = alberoprocMovimentiService.findByAlberoprocId(entity.getId().getCodice());
	    model.addAttribute("alberoprocMovimentis", findByAlberoprocId);
	    /*
	     * Recupero la lista dei metadati
	     */
	    ConfigurazioneMetadati cm = this.alberoprocMetadatiService.findByAlberoproc(entity.getId().getCodice());
	    Set<MetadatoAlberoproc> metadatis = cm.getMetadatiRamo();
	    model.addAttribute("metadatis", metadatis);
	    /*
	     * Recupero il SC_CODICE
	     */
	    model.addAttribute("SC_CODICE", entity.getScCodice());
	    /*
	     * Controllo se è attiva la VERTICALIZZAZIONE PEOPLE
	     */
	    Verticalizzazioni verticalizzazioni_PEOPLE = verticalizzazioniService.findByModulo(WebConstants.VERTICALIZZAZIONE_PEOPLE);
	    if (verticalizzazioni_PEOPLE != null) {
		if (verticalizzazioni_PEOPLE.getAttivo() == 1) {
		    model.addAttribute("vert_people_attivo", true);
		} else {
		    model.addAttribute("vert_people_attivo", false);
		}
	    } else {
		model.addAttribute("vert_people_attivo", false);
	    }
	    Verticalizzazioni verticalizzazioni_SIEDER = verticalizzazioniService.findByModulo(WebConstants.VERTICALIZZAZIONE_SIEDER);
	    if (verticalizzazioni_SIEDER != null) {
		if (verticalizzazioni_SIEDER.getAttivo() == 1) {
		    model.addAttribute("vert_sieder_attivo", true);
		} else {
		    model.addAttribute("vert_sieder_attivo", false);
		}
	    } else {
		model.addAttribute("vert_sieder_attivo", false);
	    }
	    /*
	     * Controllo se è attiva la VERTICALIZZAZIONE CART
	     */
	    Verticalizzazioni verticalizzazioni_CART = verticalizzazioniService.findByModulo(WebConstants.VERTICALIZZAZIONE_CART);
	    if (verticalizzazioni_CART != null) {
		if (verticalizzazioni_CART.getAttivo() == 1) {
		    model.addAttribute("vert_cart_attivo", true);
		    StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findbyAlberoproc(entity.getId().getCodice());
		    boolean showSchedaSpiegazione = false;
		    boolean showPannelloControllo = false;
		    Integer codiceAlberoproc = entity.getId().getCodice();
		    boolean isPubblicabileSuCART = alberoprocService.checkSePubblicabileSuCART(codiceAlberoproc);
		    model.addAttribute("pubblicabile_manualmente_cart", isPubblicabileSuCART);
		    model.addAttribute("pubblicato_cart", Boolean.FALSE);
		    if (stpEndoTipo2 != null) {
			model.addAttribute("pubblicato_cart", Boolean.TRUE);
			if (StringUtils.defaultIfEmpty(stpEndoTipo2.getTipo(), "").equalsIgnoreCase(StpEndoTipo2Service.TIPO_ENDO)) {
			    Oggetti oggetto = null;
			    if (stpEndoTipo2 != null) {
				model.addAttribute("stpendo2_codice", stpEndoTipo2.getCodiceStp());
				oggetto = stpEndoTipo2.getOggetti();
			    }
			    if (EntityUtils.getNestedProperty(oggetto, "id.codice") != null) {
				showSchedaSpiegazione = true;
			    }
			    LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
			    Responsabili responsabiliLoggato = responsabiliService.findById(new PkId(user.getCodiceResponsabile()));
			    if (responsabiliLoggato.getAmministratore().equals("1") || responsabiliLoggato.getAmministratoresoftware().equals("1")) {
				if (stpEndoTipo2.getCodiceStp() != null) {
				    showPannelloControllo = true;
				}
			    }
			}
		    }
		    model.addAttribute("cart_schedaspiegazione", showSchedaSpiegazione);
		    model.addAttribute("cart_pannellocontrollo", showPannelloControllo);
		} else {
		    model.addAttribute("vert_cart_attivo", false);
		}
	    } else {
		model.addAttribute("vert_cart_attivo", false);
	    }
	    boolean verticalizzazioni_LIVORNO_SERVIZI_CITTADINI = verticalizzazioniService
		    .isAttiva(WebConstants.VERTICALIZZAZIONE_LIVORNO_SERVIZI_CITTADINO);
	    model.addAttribute("vert_livorno_servizi_cittadini_attivo", verticalizzazioni_LIVORNO_SERVIZI_CITTADINI);
	    String scCodice = entity.getScCodice();
	    int lengthCodice = scCodice.length();
	    int lengthTree = lengthCodice / 2;
	    Set<AlberoprocLeggi> alberoprocLeggiEreditate = new HashSet<AlberoprocLeggi>(0);
	    Set<AlberoprocDocumenti> alberoprocDocumentiEreditati = new HashSet<AlberoprocDocumenti>(0);
	    Set<AlberoprocArendo> alberoprocArendoEreditate = new HashSet<AlberoprocArendo>(0);
	    Set<AlberoprocEndo> alberoprocEndoEreditate = new HashSet<AlberoprocEndo>(0);
	    Set<AlberoprocTipisoggetto> alberoprocTipisoggettoEreditatis = new HashSet<AlberoprocTipisoggetto>(0);
	    Set<AlberoprocMovimenti> alberoprocAlberoprocMovimentiEreditatis = new HashSet<AlberoprocMovimenti>(0);
	    // model.addAttribute("alberoprocMovimentis", alberoprocMovimentiService.findByAlberoprocId(entity.getId().getCodice()));
	    boolean arendoSettati = false;
	    boolean movimentiSettati = false;
	    // L'albero viene visitato in modalità da foglia a radice, non appena trovo un elemento della 
	    // gerarchia che contiene iniziovalidita e fine validita, fermo la ricerca dataInizioFineValiditaTrovata=true.
	    // Il valore della variabile di controllo viene calcolato in fase di inizializzazione, se la voce 
	    //dell'albero ha i campi inizioValita e fineValidita gia popolati, la ricerca nelle gerarchie superiori non verrà fatta
	    boolean dataInizioFineValiditaTrovata = false;
	    if (entity.getInizioValidita() != null && entity.getFineValidita() != null) {
		dataInizioFineValiditaTrovata = true;
	    }
	    for (int i = 0; i < lengthTree - 1; i++) {
		lengthCodice = lengthCodice - 2;
		String sccodicePadre = scCodice.substring(0, lengthCodice);
		Alberoproc alberoprocTemp = alberoprocService.findByScCodice(sccodicePadre);
		if (alberoprocTemp.getAlberoprocLeggis() != null && !alberoprocTemp.getAlberoprocLeggis().isEmpty()) {
		    alberoprocLeggiEreditate.addAll(alberoprocTemp.getAlberoprocLeggis());
		}
		if (alberoprocTemp.getAlberoprocMovimentis() != null && !alberoprocTemp.getAlberoprocMovimentis().isEmpty() && !movimentiSettati) {
		    alberoprocAlberoprocMovimentiEreditatis.addAll(alberoprocTemp.getAlberoprocMovimentis());
		    movimentiSettati = true;
		}
		if (alberoprocTemp.getAlberoprocDocumentis() != null && !alberoprocTemp.getAlberoprocDocumentis().isEmpty()) {
		    alberoprocDocumentiEreditati.addAll(alberoprocTemp.getAlberoprocDocumentis());
		}
		if (alberoprocTemp.getAlberoprocArendos() != null && !alberoprocTemp.getAlberoprocArendos().isEmpty()) {
		    // BOCCI 2012-06-13 La sezione endo ereditati, se visibile, deve far vedere solamente gli endo ereditati dalla voce di albero immediatamente precedente. Si risale alla prima voce dell'albero che definisce alberoproc_arendo e stop.
		    if (arendoSettati == false) {
			alberoprocArendoEreditate.addAll(alberoprocTemp.getAlberoprocArendos());
			arendoSettati = true;
		    }
		}
		if (alberoprocTemp.getAlberoprocEndos() != null && !alberoprocTemp.getAlberoprocEndos().isEmpty()) {
		    alberoprocEndoEreditate.addAll(alberoprocTemp.getAlberoprocEndos());
		}
		if (alberoprocTemp.getAlberoprocTipisoggettos() != null && !alberoprocTemp.getAlberoprocTipisoggettos().isEmpty()) {
		    alberoprocTipisoggettoEreditatis.addAll(alberoprocTemp.getAlberoprocTipisoggettos());
		}
		if (!dataInizioFineValiditaTrovata) {
		    if (alberoprocTemp.getInizioValidita() != null && alberoprocTemp.getFineValidita() != null) {
			model.addAttribute("dataInizioValiditaEreditata", Utilities.formatDate(alberoprocTemp.getInizioValidita(), true));
			model.addAttribute("dataFineValiditaEreditata", Utilities.formatDate(alberoprocTemp.getFineValidita(), true));
			// Indica il codice intervento del quale eridita data inizio e data fine
			model.addAttribute("codiceInterEriditaDate", alberoprocTemp.getId().getCodice());
			dataInizioFineValiditaTrovata = true;
		    }
		}
	    }
	    // ordino tutte le liste trovate prima di meterle sul model
	    //Oridna per il campo descrizione dell'oggetto Legge
	    List<AlberoprocLeggi> alberoprocLeggiEreditateOrdinate = new ArrayList<AlberoprocLeggi>(alberoprocLeggiEreditate);
	    Collections.sort(alberoprocLeggiEreditateOrdinate, new AlberoprocLeggiComparator());
	    // Ordina prima per campo ordine e poi per campo descrizione
	    List<AlberoprocDocumenti> alberoprocDocumentiEreditatiOrdinati = new ArrayList<AlberoprocDocumenti>(alberoprocDocumentiEreditati);
	    Collections.sort(alberoprocDocumentiEreditatiOrdinati, new AlberoprocDocumentiComparator());
	    // Ordina prima per campo ordine e poi per campo descrizione	
	    List<AlberoprocEndo> alberoprocEndoEreditatiOrdinati = new ArrayList<AlberoprocEndo>(alberoprocEndoEreditate);
	    Collections.sort(alberoprocEndoEreditatiOrdinati, new AlberoprocEndoComparator());
	    model.addAttribute("alberoprocLeggiEreditate", alberoprocLeggiEreditateOrdinate);
	    model.addAttribute("alberoprocDocumentiEreditati", alberoprocDocumentiEreditatiOrdinati);
	    model.addAttribute("alberoprocArendoEreditate", alberoprocArendoEreditate);
	    model.addAttribute("alberoprocEndoEreditate", alberoprocEndoEreditatiOrdinati);
	    model.addAttribute("alberoprocTipisoggettoEreditati", alberoprocTipisoggettoEreditatis);
	    model.addAttribute("alberoprocMetadatiEreditati", cm.getMetadatiRamiPadre());
	    model.addAttribute("alberoprocMetadatiEreditati", cm.getMetadatiRamiPadre());
	    model.addAttribute("alberoprocMovimentiEreditati", alberoprocAlberoprocMovimentiEreditatis);
	    //collegamento a ProcediMarche
	    if (procediMarcheProxyService.isVerticalizzazioneConfigurata()) {
		StpEndoTipo2 collegamentoPM = this.stpEndoTipo2Service.findbyAlberoproc(entity.getId().getCodice());
		if (collegamentoPM != null && collegamentoPM.getCodiceStp() != null) {
		    model.addAttribute("collegamentoPM", collegamentoPM);
		}
	    }
	}
	boolean arjAttiva = this.verticalizzazioneAreaRiservataService.isAttiva()
		&& this.verticalizzazioneAreaRiservataService.isAreaRiservataJavaAttiva();
	model.addAttribute("ARJ_ATTIVA", arjAttiva);
	boolean centroServizi = this.verticalizzazioneAreaRiservataService.isAttiva() && this.verticalizzazioneAreaRiservataService.isCentroServizi();
	model.addAttribute("CENTRO_SERVIZI", centroServizi);
	boolean areariservataredirect = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_AREARISERVATA_REDIRECT);
	model.addAttribute("AREARISERVATA_REDIRECT", areariservataredirect);
	/*
	 * Controllo se esistono tipibando con flag_bolkestein=1
	 */
	List<Tipibando> tipibandos = tipibandoService.findAll(null, null);
	for (Tipibando tipibando : tipibandos) {
	    model.addAttribute("flag_bolkestein", false);
	    if (BooleanUtils.isTrue(tipibando.getFlagBolkestein())) {
		model.addAttribute("flag_bolkestein", true);
		break;
	    }
	}
	model.addAttribute("isSpostaPratica", Boolean.valueOf(userHasRole(false, RuoliUtentiEnum.SPOSTAMENTO_PRATICHE.name())
		&& (entity == null ? false : BooleanUtils.isFalse(entity.getScPadre()))));
    }

    private String toCheckedString(String valore) {

	String result = "";
	if (StringUtils.defaultIfEmpty(valore, "0").equalsIgnoreCase("1")) {
	    result = " checked ";
	}
	return result;
    }

    @Override
    protected void setPageAttributes(Model model) {

	boolean attivoAccessoAtti = false;
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ACCESSO_AGLI_ATTI)) {
	    attivoAccessoAtti = true;
	    model.addAttribute("attivoAccessoAtti", attivoAccessoAtti);
	}
	/*
	 * Controllo se è attiva e configurata la verticalizzazione PROCEDI_MARCHE
	 */
	boolean verticalizzazioni_PROCEDI_MARCHE = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE);
	Verticalizzazioniparametri param;
	if (verticalizzazioni_PROCEDI_MARCHE) {
	    param = this.verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE,
		    WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE_URL);
	    verticalizzazioni_PROCEDI_MARCHE = verticalizzazioni_PROCEDI_MARCHE && param != null && StringUtils.isNotBlank(param.getValore());
	}
	if (verticalizzazioni_PROCEDI_MARCHE) {
	    param = this.verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE,
		    WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE_CF_ENTE);
	    verticalizzazioni_PROCEDI_MARCHE = verticalizzazioni_PROCEDI_MARCHE && param != null && StringUtils.isNotBlank(param.getValore());
	}
	model.addAttribute("vert_procedi_marche", verticalizzazioni_PROCEDI_MARCHE);
    }

    @RequestMapping
    public String updateistanzeruolo(@RequestParam("codiceAlberoproc") Integer codiceAlberoproc, @RequestParam("idruolo") Integer idruolo,
	    Model model, HttpServletRequest request) {

	checkAccesso();
	istanzeruoliService.insertInstanzeRuoloDaAlberoproc(codiceAlberoproc, idruolo);
	return "redirect:createRuoli.htm?alberoproc.id.codice=" + codiceAlberoproc + "&status_msg=02";
    }

    @RequestMapping
    public String deleteistanzeruolo(@RequestParam("codiceAlberoproc") Integer codiceAlberoproc, @RequestParam("idruolo") Integer idruolo,
	    Model model, HttpServletRequest request) {

	checkAccesso();
	istanzeruoliService.deleteInstanzeRuoloDaAlberoproc(codiceAlberoproc, idruolo);
	return "redirect:createRuoli.htm?alberoproc.id.codice=" + codiceAlberoproc + "&status_msg=02";
    }

    @RequestMapping
    public String deleteistanzeruoli(@RequestParam("codiceAlberoproc") Integer codiceAlberoproc, Model model, HttpServletRequest request) {

	checkAccesso();
	istanzeruoliService.deleteInstanzeRuoliDaAlberoproc(codiceAlberoproc);
	return "redirect:createRuoli.htm?alberoproc.id.codice=" + codiceAlberoproc + "&status_msg=02";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////GESTIONE DEI MODELLI DELL' ATTIVITA///////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping
    public ModelMap listmodelliAttivita(@RequestParam("codiceprocedimento") Integer codice, HttpServletRequest request,
	    HttpServletResponse response) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	List<AlberoprocD2modtatt> alberoprocD2modtatts = alberoprocD2modtattService.findByAlberoProc(codice);
	ModelMap model = new ModelMap(alberoprocD2modtatts);
	boolean export = createJMesaExport(request, response, alberoprocD2modtatts);
	if (export) {
	    return null;
	}
	model.addAttribute("alberoprocD2modtatts", alberoprocD2modtatts);
	model.addAttribute("alberoproc", alberoproc);
	return model;
    }

    @RequestMapping
    public String createmodelliAttivita(@RequestParam("codiceprocedimento") Integer codice, Model model) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	AlberoprocD2modtatt alberoprocD2modtatt = new AlberoprocD2modtatt();
	alberoprocD2modtatt.setAlberoproc(alberoproc);
	// setto l'id
	AlberoprocD2modtattId id = new AlberoprocD2modtattId();
	id.setFkScId(codice);
	alberoprocD2modtatt.setId(id);
	fixRenderAlberoprocD2modtattProperty(alberoprocD2modtatt);
	model.addAttribute("alberoprocD2modtatt", alberoprocD2modtatt);
	setPageAttributes(model);
	return "alberoproc/formModelliAttivita";
    }

    @RequestMapping
    public String viewModelliAttivita(@RequestParam("codiceprocedimento") Integer codice, @RequestParam("codicemodello") Integer codicemodello,
	    Model model) {

	AlberoprocD2modtattId id = new AlberoprocD2modtattId(codice, codicemodello);
	AlberoprocD2modtatt alberoprocD2modtatt = alberoprocD2modtattService.findById(id);
	fixRenderAlberoprocD2modtattProperty(alberoprocD2modtatt);
	model.addAttribute("view", Boolean.TRUE);
	model.addAttribute("alberoprocD2modtatt", alberoprocD2modtatt);
	setPageAttributes(model);
	return "alberoproc/formModelliAttivita";
    }

    @RequestMapping
    public String insertModelliAttivita(Model model, @ModelAttribute("alberoprocD2modtatt") AlberoprocD2modtatt alberoprocD2modtatt,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "alberoproc/formModelliAttivita";
	}
	// recupero i campi ajax
	if (alberoprocD2modtatt.getDyn2Modellit() != null && alberoprocD2modtatt.getDyn2Modellit().getId().getCodice() != null) {
	    Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(alberoprocD2modtatt.getDyn2Modellit().getId());
	    AlberoprocD2modtattId id = alberoprocD2modtatt.getId();
	    id.setFkD2mtId(alberoprocD2modtatt.getDyn2Modellit().getId().getCodice());
	    alberoprocD2modtatt.setId(id);
	    alberoprocD2modtatt.setDyn2Modellit(dyn2Modellit);
	}
	try {
	    alberoprocD2modtattService.insert(alberoprocD2modtatt);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocD2modtatt, e);
	    fixRenderAlberoprocD2modtattProperty(alberoprocD2modtatt);
	    model.addAttribute("alberoprocD2modtatt", alberoprocD2modtatt);
	    return "alberoproc/formModelliAttivita";
	}
	status.setComplete();
	return "redirect:viewModelliAttivita.htm?codiceprocedimento=" + alberoprocD2modtatt.getAlberoproc().getId().getCodice() + "&codicemodello=" +
	       alberoprocD2modtatt.getId().getFkD2mtId() + "&status_msg=01";
    }

    @RequestMapping
    public String updateModelliAttivita(Model model, @ModelAttribute("alberoprocD2modtatt") AlberoprocD2modtatt alberoprocD2modtatt,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "alberoproc/formModelliAttivita";
	}
	//fixMergeAlberoprocDyn2modellitProperty(alberoprocDyn2modellit);
	try {
	    alberoprocD2modtattService.update(alberoprocD2modtatt);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocD2modtatt, e);
	    model.addAttribute("alberoprocD2modtatt", alberoprocD2modtatt);
	    fixRenderAlberoprocD2modtattProperty(alberoprocD2modtatt);
	    model.addAttribute("view", Boolean.TRUE);
	    return "alberoproc/formModelliAttivita";
	}
	status.setComplete();
	return "redirect:viewModelliAttivita.htm?codiceprocedimento=" + alberoprocD2modtatt.getAlberoproc().getId().getCodice() + "&codicemodello=" +
	       alberoprocD2modtatt.getId().getFkD2mtId() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteModelliAttivita(Model model, @ModelAttribute("alberoprocD2modtatt") AlberoprocD2modtatt alberoprocD2modtatt,
	    BindingResult result, SessionStatus status) {

	AlberoprocD2modtatt objToDelete = alberoprocD2modtattService.findById(alberoprocD2modtatt.getId());
	try {
	    alberoprocD2modtattService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderAlberoprocD2modtattProperty(objToDelete);
	    model.addAttribute("view", Boolean.TRUE);
	    return "alberoproc/formModelliAttivita";
	}
	status.setComplete();
	return "redirect:listmodelliAttivita.htm?codiceprocedimento=" + objToDelete.getAlberoproc().getId().getCodice();
    }

    @RequestMapping
    public void ajaxChangeFlagAlberoprocEndo(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam("codicealberoproc") Integer codicealberoproc, @RequestParam("tipoFlag") String tipoFlag, HttpServletResponse response)
	    throws Exception {

	AlberoprocEndo alberoprocEndo = alberoprocEndoService.findById(new AlberoprocEndoId(ORMHelper.getIdcomune(), codicealberoproc, codiceendo));
	String message = "";
	try {
	    if (tipoFlag.equalsIgnoreCase(FLAG_PUBBLICA))
		if (BooleanUtils.isFalse(alberoprocEndo.getFlagPubblica())) {
		    alberoprocEndo.setFlagPubblica(Boolean.TRUE);
		} else {
		    alberoprocEndo.setFlagPubblica(Boolean.FALSE);
		}
	    if (tipoFlag.equalsIgnoreCase(FLAG_PROPOSTO)) {
		if (BooleanUtils.isFalse(alberoprocEndo.getFlagRichiesto())) {
		    alberoprocEndo.setFlagRichiesto(Boolean.TRUE);
		    // nel caso sia proposto deve essere anche richiesto da BO
		    alberoprocEndo.setFlagRichiestoBo(Boolean.TRUE);
		} else {
		    alberoprocEndo.setFlagRichiesto(Boolean.FALSE);
		}
	    }
	    if (tipoFlag.equalsIgnoreCase(FLAG_PRINCIPALE)) {
		if (BooleanUtils.isFalse(alberoprocEndo.getFlagPrincipale())) {
		    alberoprocEndo.setFlagPrincipale(Boolean.TRUE);
		    // nel caso sia proposto deve essere anche richiesto da BO
		    alberoprocEndo.setFlagRichiestoBo(Boolean.TRUE);
		} else {
		    alberoprocEndo.setFlagPrincipale(Boolean.FALSE);
		}
	    }
	    if (tipoFlag.equalsIgnoreCase(FLAG_RICHESTO_BACK)) {
		if (BooleanUtils.isFalse(alberoprocEndo.getFlagRichiestoBo())) {
		    alberoprocEndo.setFlagRichiestoBo(Boolean.TRUE);
		} else {
		    alberoprocEndo.setFlagRichiestoBo(Boolean.FALSE);
		}
	    }
	    alberoprocEndoService.update(alberoprocEndo);
	    message = getMessageFromBundle("label.datoaggiornato", null);
	} catch (BusinessValidationException e) {
	    response.setStatus(500);
	    if (!e.getInvalidValues().isEmpty()) {
		message = getMessageFromBundle(e.getInvalidValues().get(0).getMessage(), null);
	    } else {
		message = e.getMessage().toString();
	    }
	}
	response.getWriter().write(message);
    }

    @RequestMapping
    public void ajaxChangeSelectBoxAzioneEndo(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam("codicealberoproc") Integer codicealberoproc, @RequestParam("codiceAzione") Integer codiceAzione,
	    HttpServletResponse response) throws Exception {

	try {
	    AlberoprocEndo alberoprocEndo = alberoprocEndoService
		    .findById(new AlberoprocEndoId(ORMHelper.getIdcomune(), codicealberoproc, codiceendo));
	    String message = "";
	    Azioni azione = azioniService.findById(codiceAzione);
	    alberoprocEndo.setAzione(azione);
	    alberoprocEndoService.update(alberoprocEndo);
	    message = getMessageFromBundle("label.datoaggiornato", null);
	    response.getWriter().write(message);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping
    public void ajaxCancellaEsclusioneComune(@RequestParam("codiceinterventoproc") Integer codiceInterventoProc,
	    @RequestParam("codicecomune") String codiceComune, HttpServletResponse response) throws Exception {

	String message = "";
	try {
	    if (codiceInterventoProc == null) {
		throw new InvalidArgumentException("Non è stato passato l'id dell'intervento");
	    }
	    if (StringUtils.isBlank(codiceComune)) {
		throw new InvalidArgumentException("Non è stato passato il codice dell'ente");
	    }
	    this.alberoprocComuniEsclusiService.delete(codiceInterventoProc, codiceComune);
	} catch (Exception e) {
	    response.setStatus(500);
	    message = e.getMessage();
	} finally {
	    response.getWriter().write(message);
	}
    }

    @RequestMapping
    public void ajaxAggiungiComuniEsclusi(@RequestParam("codiceinterventoproc") Integer codiceInterventoProc, @RequestParam("comuni") String[] comuni,
	    HttpServletResponse response) throws Exception {

	String message = "";
	try {
	    this.alberoprocComuniEsclusiService.aggiungiEnti(codiceInterventoProc, comuni);
	} catch (Exception e) {
	    response.setStatus(500);
	    message = e.getMessage();
	} finally {
	    response.getWriter().write(message);
	}
    }

    /**
     * Metodo che gestisce la modifica dei paramtri di protocollazione presenti all'interno del tabella ALBEROPROC
     * 
     * @param codice
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String parametriprotocollazione(@RequestParam("codice") Integer codice, @RequestParam("codiceComune") String codiceComune, Model model,
	    HttpServletRequest request) {

	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	/*
	 * Recupera le informazioni per popolare i parametri di protocollazione
	 */
	setParametriInModel(model, request, codiceComune, alberoproc.getSoftware().getCodice());
	// questa find è necessaria perchè nel metodo sopra (setPageAttributes) ci sono dei service
	// che 'catchano' le eventuali eccezioni. Quando 'catchi' un'eccezione in un service la sessione di hibernate è svuotata
	// per cui è necessario ricaricarla.
	model.addAttribute("alberoproc", alberoproc);
	setPageAttributes(model);
	fixRenderEntityProperty(alberoproc);
	return "alberoproc/formParametriProtocollazione";
    }

    @RequestMapping
    public String listparametriProtAndFasc(@RequestParam("codiceAlberoproc") Integer codice, Model model, HttpServletRequest request) {

	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	List<AlberoprocProtAndFascHelper> alberoprocProtAndFascHelpers = alberoprocProtocolloService.findByComuniPerOperatore(codice, responsabile);
	model.addAttribute("alberoprocProtAndFascHelpers", alberoprocProtAndFascHelpers);
	Boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	model.addAttribute("isComuniAssociati", isComuniAssociati);
	Set<Responsabilicomuni> responsabilicomunis = responsabiliService.findListResponsabilicomuni(responsabile);
	model.addAttribute("responsabilicomunis", responsabilicomunis);
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	model.addAttribute("alberoproc", alberoproc);
	return "alberoproc/listparametriProtAndFasc";
    }

    @RequestMapping
    public String ajaxRiepilogoConfigurazioniProt(@RequestParam("codicealberoproc") Integer codicealberoproc, Model model,
	    HttpServletResponse response) throws Exception {

	Map<String, AlberoprocProtocollo> m = alberoprocProtocolloService.findConfigurazioniHelper(codicealberoproc);
	model.addAttribute("configAlberoproc", m);
	return "alberoproc/ajaxRiepilogoConfigurazioniProt";
    }

    @RequestMapping
    public String createparametriProtAndFasc(@RequestParam("codice") Integer codice, @RequestParam("codiceComune") String codiceComune, Model model,
	    HttpServletRequest request) {

	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	AlberoprocProtocollo alberoprocprotocollo = new AlberoprocProtocollo();
	alberoprocprotocollo.setTestoFascicolo(new Mailtipo());
	alberoprocprotocollo.setTestoProtocollo(new Mailtipo());
	alberoprocprotocollo.setAlberoproc(alberoproc);
	if (StringUtils.isNotBlank(codiceComune)) {
	    Comuni c = comuniService.findById(codiceComune);
	    alberoprocprotocollo.setComuni(c);
	}
	/*
	 * Recupera le informazioni per popolare i parametri di protocollazione
	 */
	model.addAttribute("alberoproc", alberoproc);
	model.addAttribute("alberoprocprotocollo", alberoprocprotocollo);
	setParametriInModel(model, request, codiceComune, alberoproc.getSoftware().getCodice());
	setPageAttributes(model);
	fixRenderEntityProperty(alberoproc);
	return "alberoproc/formParametriProtAndFasc";
    }

    public static String decodificaTipo(Integer val, boolean isProt) {

	if (val == null) {
	    return "";
	}
	Map<Integer, String> presenteM = new HashMap<Integer, String>();
	presenteM.put(0, "Non protocollare");
	presenteM.put(1, "Protocolla da inserimento normale");
	presenteM.put(2, "Protocolla da on line");
	presenteM.put(4, "Protocolla da inserimento rapido");
	presenteM.put(8, "Controlla l'impostazione dei rami padre");
	Map<Integer, String> presenteF = new HashMap<Integer, String>();
	presenteF.put(0, "Non fascicolare");
	presenteF.put(1, "Fascicola da inserimento normale");
	presenteF.put(2, "Fascicola da on line");
	presenteF.put(4, "Fascicola da inserimento rapido");
	presenteF.put(8, "Fascicola l'impostazione dei rami padre");
	List<Integer> numeri = new ArrayList<Integer>();
	numeri.add(0);
	numeri.add(1);
	numeri.add(2);
	numeri.add(4);
	numeri.add(8);
	String result = "<ul>";
	for (Integer n : numeri) {
	    boolean presente = false;
	    if (val == 0) {
		presente = true;
	    } else {
		presente = ((val & n.intValue()) == n.intValue() && n.intValue() != 0) ? true : false;
	    }
	    if (presente) {
		if (isProt) {
		    result += "<li>" + presenteM.get(n) + "</li>";
		} else {
		    result += "<li>" + presenteF.get(n) + "</li>";
		}
		if (val == 0) {
		    break;
		}
	    }
	}
	return result + "</ul>";
    }

    @RequestMapping
    public String insertParametriProtAndFasc(Model model, @ModelAttribute("alberoprocprotocollo") AlberoprocProtocollo alberoprocProtocollo,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	// Recupero i parametri per il protocollo del campo scProtautomatica da inserire come scelta multipla 
	setProtAndFascAutomatica(alberoprocProtocollo, request);
	fixMergeAlberoprocProtocolloProperty(alberoprocProtocollo);
	String codiceComune = null;
	manageRequestParam(alberoprocProtocollo, request);
	if (alberoprocProtocollo.getComuni() != null) {
	    codiceComune = alberoprocProtocollo.getComuni().getCodicecomune();
	}
	try {
	    alberoprocProtocolloService.insert(alberoprocProtocollo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocProtocollo, e);
	    /*
	     * Recupera le informazioni per popolare i parametri di protocollazione
	     */
	    setParametriInModel(model, request, codiceComune, alberoprocProtocollo.getAlberoproc().getSoftware().getCodice());
	    fixRenderAlberoprocProtocolloProperty(alberoprocProtocollo);
	    return "alberoproc/formParametriProtAndFasc";
	}
	status.setComplete();
	return "redirect:viewParametriProtAndFasc.htm?codice=" + alberoprocProtocollo.getId().getCodice() + "&status_msg=01";
    }

    private void manageRequestParam(AlberoprocProtocollo alberoprocProtocollo, HttpServletRequest request) {

	if (!StringUtils.defaultIfEmpty(request.getParameter("testoProtocollo.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("testoProtocollo.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Mailtipo m = mailtipoService.findById(codiceId);
	    alberoprocProtocollo.setTestoProtocollo(m);
	} else {
	    alberoprocProtocollo.setTestoProtocollo(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("testoFascicolo.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("testoFascicolo.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Mailtipo m = mailtipoService.findById(codiceId);
	    alberoprocProtocollo.setTestoFascicolo(m);
	} else {
	    alberoprocProtocollo.setTestoFascicolo(null);
	}
    }

    @RequestMapping
    public String viewParametriProtAndFasc(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	AlberoprocProtocollo alberoprocProtocollo = alberoprocProtocolloService.findById(id);
	String codiceComune = null;
	if (alberoprocProtocollo.getComuni() != null) {
	    codiceComune = alberoprocProtocollo.getComuni().getCodicecomune();
	}
	/*
	 * Recupera le informazioni per popolare i parametri di protocollazione
	 */
	setParametriInModel(model, request, codiceComune, alberoprocProtocollo.getAlberoproc().getSoftware().getCodice());
	fixRenderAlberoprocProtocolloProperty(alberoprocProtocollo);
	// E' necessario in caso nel metodo  "setParametriInModel(....)" generi un errore, vieni chiusa la sessione
	// e dalla jsp non si riesce più a fare il get degli oggetti
	id = new PkId(codice);
	alberoprocProtocollo = alberoprocProtocolloService.findById(id);
	model.addAttribute("alberoproc", alberoprocProtocollo.getAlberoproc());
	model.addAttribute("alberoprocprotocollo", alberoprocProtocollo);
	return "alberoproc/formParametriProtAndFasc";
    }

    @RequestMapping
    public String viewModificaAlbero(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	List<Alberoproc> figli = alberoprocService.findAlberoprocFigli(alberoproc.getScCodice(), false, null, false);
	model.addAttribute("listaDescFigli", figli);
	model.addAttribute("idRamoDaSpostare", alberoproc.getId().getCodice());
	return "alberoproc/formModificaAlbero";
    }

    @RequestMapping
    public void ajaxModificaAlbero(@RequestParam("scId") Integer codiceNuovo, @RequestParam("idRamoDaSpostare") Integer codiceVecchio, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	alberoprocService.spostaVoceAlbero(codiceVecchio, codiceNuovo);
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	String descrizioneNuovo = "";
	if (codiceNuovo == 0) {
	    descrizioneNuovo = "Albero degli Interventi";
	} else {
	    Alberoproc nuovo = alberoprocService.findById(new PkId(codiceNuovo));
	    descrizioneNuovo = nuovo.getScDescrizione();
	}
	Alberoproc vecchio = alberoprocService.findById(new PkId(codiceVecchio));
	LoggerCancellazioni.logSpostamentoAlberoProc(responsabile.getResponsabile(), descrizioneNuovo, vecchio.getScDescrizione());
	response.setContentType("text/plain");
	response.getOutputStream().write("OK".getBytes());
	response.flushBuffer();
    }

    @RequestMapping
    public String updateParametriProtAndFasc(Model model, @ModelAttribute("alberoprocprotocollo") AlberoprocProtocollo alberoprocProtocollo,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	// Recupero i parametri per il protocollo del campo scProtautomatica da inserire come scelta multipla 
	setProtAndFascAutomatica(alberoprocProtocollo, request);
	fixMergeAlberoprocProtocolloProperty(alberoprocProtocollo);
	manageRequestParam(alberoprocProtocollo, request);
	String codiceComune = null;
	if (alberoprocProtocollo.getComuni() != null) {
	    codiceComune = alberoprocProtocollo.getComuni().getCodicecomune();
	}
	try {
	    alberoprocProtocolloService.update(alberoprocProtocollo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocProtocollo, e);
	    /*
	     * Recupera le informazioni per popolare i parametri di protocollazione
	     */
	    setParametriInModel(model, request, codiceComune, alberoprocProtocollo.getAlberoproc().getSoftware().getCodice());
	    fixRenderAlberoprocProtocolloProperty(alberoprocProtocollo);
	    return "alberoproc/formParametriProtAndFasc";
	}
	status.setComplete();
	return "redirect:viewParametriProtAndFasc.htm?codice=" + alberoprocProtocollo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteParametriProtAndFasc(Model model, @ModelAttribute("alberoprocprotocollo") AlberoprocProtocollo alberoprocProtocollo,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	AlberoprocProtocollo objToDelete = alberoprocProtocolloService.findById(alberoprocProtocollo.getId());
	String codiceComune = null;
	if (alberoprocProtocollo.getComuni() != null) {
	    codiceComune = alberoprocProtocollo.getComuni().getCodicecomune();
	}
	try {
	    alberoprocProtocolloService.delete(objToDelete);
	} catch (Exception e) {
	    /*
	     * Recupera le informazioni per popolare i parametri di protocollazione
	     */
	    setParametriInModel(model, request, codiceComune, alberoprocProtocollo.getAlberoproc().getSoftware().getCodice());
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderAlberoprocProtocolloProperty(objToDelete);
	    return "alberoproc/formParametriProtAndFasc";
	}
	status.setComplete();
	return "redirect:listparametriProtAndFasc.htm?codiceAlberoproc=" + objToDelete.getAlberoproc().getId().getCodice() + "&status_msg=03";
    }

    // Il metodo recupera dalla request i valori selezionati per i campi scFascautomatica e scProtautomatica
    // sono campi a scelt amultipla.
    private void setProtAndFascAutomatica(AlberoprocProtocollo alberoprocProtocollo, HttpServletRequest request) {

	String[] _scProtautomatica = request.getParameterValues("scProtautomatica");
	Integer totProtAutomatica = null;
	if (_scProtautomatica != null) {
	    totProtAutomatica = Integer.valueOf(0);
	    for (String value : _scProtautomatica) {
		Integer codice_protAutomatica = Integer.parseInt(value);
		totProtAutomatica += codice_protAutomatica;
	    }
	    alberoprocProtocollo.setScProtautomatica(totProtAutomatica);
	}
	// Recupero i parametri per la fascicolazione del campo scProtautomatica da inserire come scelta multipla 
	String[] _scFascautomatica = request.getParameterValues("scFascautomatica");
	Integer totscFascautomatica = null;
	if (_scFascautomatica != null) {
	    totscFascautomatica = Integer.valueOf(0);
	    for (String value : _scFascautomatica) {
		Integer codice_protAutomatica = Integer.parseInt(value);
		totscFascautomatica += codice_protAutomatica;
	    }
	    alberoprocProtocollo.setScFascautomatica(totscFascautomatica);
	}
    }

    @RequestMapping
    public String pubblicaSuCart(@RequestParam("codiceAlberoproc") Integer codiceAlberoproc, @RequestParam("pubblica") Boolean pubblica, Model model,
	    HttpServletRequest request) {

	Alberoproc ap = alberoprocService.findById(new PkId(codiceAlberoproc));
	try {
	    alberoprocService.updatePubblicaSuCart(codiceAlberoproc, pubblica.booleanValue());
	    String result = getMessageFromBundle("02", new Object[] {});
	    FlashMessages.getInfos().add(result);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(ap, false, null, e);
	}
	return "redirect:view.htm?codice=" + codiceAlberoproc;
    }

    private void setParametriInModel(Model model, HttpServletRequest request, String codiceComune, String software) {

	/*
	 * Controllo se è attiva la verticalizzazione con il modulo PROTOCOLLO_ATTIVO
	 */
	boolean verticalizzazioni_PROTOCOLLO_ATTIVO = verticalizzazioniService
		.isAttivaPerComuneESoftware(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE, software, codiceComune);
	if (verticalizzazioni_PROTOCOLLO_ATTIVO) {
	    model.addAttribute("vert_prot_attivo", true);
	    /*
	     * Lista CLASSIFICA
	     */
	    try {
		model.addAttribute("classificaList", protocollazioneService.getListaClassifiche(software, codiceComune));
	    } catch (Exception e) {
		List<String> warnings = new ArrayList<String>(0);
		warnings.add(e.getMessage());
		FlashMessages.setWarnings(warnings);
	    }
	    /*
	     * Lista TIPO DOCUMENTO
	     */
	    try {
		model.addAttribute("documentiList", protocollazioneService.getListaTipiDocumento(software, codiceComune));
	    } catch (Exception e) {
		List<String> warnings = new ArrayList<String>(0);
		warnings.add(e.getMessage());
		FlashMessages.setWarnings(warnings);
	    }
	    /*
	     * Lista MAILTIPO
	     */
	    model.addAttribute("mailtipoList", mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.ALL));
	    Verticalizzazioniparametri gestisciFascicoloParam = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTISCI_FASCICOLAZIONE, codiceComune, software);
	    boolean gestiscifascicolo = false;
	    if (gestisciFascicoloParam != null) {
		if (StringUtils.defaultIfEmpty(gestisciFascicoloParam.getValore(), "0").equalsIgnoreCase("1")) {
		    gestiscifascicolo = true;
		}
	    }
	    model.addAttribute("gestisciFascicolo", Boolean.valueOf(gestiscifascicolo));
	} else {
	    model.addAttribute("vert_prot_attivo", false);
	}
	// setto la lista della amministrazioni
	//TODO Devono essere filtrate in qualche modo
	List<Amministrazioni> amministrazionis = amministrazioniService.findAll(null, null);
	model.addAttribute("amministrazionis", amministrazionis);
    }

    @RequestMapping
    public void bonificaGerarchiaAlbero(HttpServletResponse response) throws Exception {

	cartInvioDizionarioService.bonificaGerarchiaAlbero();
	response.getOutputStream().write("Bonifica Effettuata".getBytes());
    }

    private void fixRenderAlberoprocD2modtattProperty(AlberoprocD2modtatt alberoprocD2modtatt) {

	if (alberoprocD2modtatt.getAlberoproc() == null) {
	    alberoprocD2modtatt.setAlberoproc(new Alberoproc());
	}
	if (alberoprocD2modtatt.getDyn2Modellit() == null) {
	    alberoprocD2modtatt.setDyn2Modellit(new Dyn2Modellit());
	}
    }

    private void checkAccesso() {

	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	if (StringUtils.defaultIfEmpty(responsabili.getAmministratore(), "0").equals("0")) {
	    throw new SecurityException("Utente non abilitato alla funzionalità");
	}
    }

    private void fixRenderAlberoprocProtocolloProperty(AlberoprocProtocollo alberoprocProtocollo) {

	if (alberoprocProtocollo.getAlberoproc() == null) {
	    alberoprocProtocollo.setAlberoproc(new Alberoproc());
	}
	if (alberoprocProtocollo.getAmministrazioni() == null) {
	    alberoprocProtocollo.setAmministrazioni(new Amministrazioni());
	}
	if (alberoprocProtocollo.getComuni() == null) {
	    alberoprocProtocollo.setComuni(new Comuni());
	}
	if (alberoprocProtocollo.getTestoFascicolo() == null) {
	    alberoprocProtocollo.setTestoFascicolo(new Mailtipo());
	}
	if (alberoprocProtocollo.getTestoProtocollo() == null) {
	    alberoprocProtocollo.setTestoProtocollo(new Mailtipo());
	}
    }

    private void fixMergeAlberoprocProtocolloProperty(AlberoprocProtocollo alberoprocProtocollo) {

	// Il fix viene eseguito nel service dopo prima dlel'insert e update
    }

    @RequestMapping
    public String createTipimovimento(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	AlberoprocMovimenti alberoprocMovimenti = new AlberoprocMovimenti();
	alberoprocMovimenti.setAlberoproc(alberoproc);
	model.addAttribute("alberoprocMovimenti", alberoprocMovimenti);
	model.addAttribute("alberoproc", alberoproc);
	setPageAttributes(model);
	fixRenderEntityPropertyAlberoprocMovimenti(alberoprocMovimenti);
	return "alberoproc/alberoproctipimov";
    }

    @RequestMapping
    public String insertTipimovimento(@ModelAttribute("alberoprocMovimenti") AlberoprocMovimenti alberoprocMovimenti, BindingResult result,
	    SessionStatus status, Model model, HttpServletRequest request) {

	Alberoproc alberoproc = alberoprocMovimenti.getAlberoproc();
	if (EntityUtils.getNestedProperty(alberoprocMovimenti.getTipimovimento(), "id.tipomovimento") != null) {
	    Tipimovimento tipifamiglieendo = tipiMovimentoService
		    .findById(new TipimovimentoId(alberoprocMovimenti.getTipimovimento().getId().getTipomovimento()));
	    alberoprocMovimenti.setTipimovimento(tipifamiglieendo);
	}
	if (EntityUtils.getNestedProperty(alberoprocMovimenti.getAmministrazioni(), "id.codice") != null) {
	    Amministrazioni amm = amministrazioniService.findById(new PkId(alberoprocMovimenti.getAmministrazioni().getId().getCodice()));
	    alberoprocMovimenti.setAmministrazioni(amm);
	}
	try {
	    alberoprocMovimentiService.insert(alberoprocMovimenti);
	} catch (Exception e) {
	    fixRenderEntityPropertyAlberoprocMovimenti(alberoprocMovimenti);
	    copyErrorsToBindingResult(result, alberoprocMovimenti, e);
	    model.addAttribute("alberoprocMovimenti", alberoprocMovimenti);
	    model.addAttribute("alberoproc", alberoproc);
	    return "alberoproc/alberoproctipimov";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "#tipimovimento_anchor";
    }

    private void fixRenderEntityPropertyAlberoprocMovimenti(AlberoprocMovimenti alberoprocMovimenti) {

	if (alberoprocMovimenti.getTipimovimento() == null) {
	    alberoprocMovimenti.setTipimovimento(new Tipimovimento());
	}
	if (alberoprocMovimenti.getAmministrazioni() == null) {
	    alberoprocMovimenti.setAmministrazioni(new Amministrazioni());
	}
	if (alberoprocMovimenti.getAlberoproc() == null) {
	    alberoprocMovimenti.setAlberoproc(new Alberoproc());
	}
    }

    @RequestMapping
    public String deleteTipimovimento(Model model, @RequestParam("alberoproc.id.codice") Integer codicealberoproc,
	    @RequestParam("codice") Integer codice, HttpServletRequest request) {

	AlberoprocMovimenti alberoprocMovimenti = alberoprocMovimentiService.findById(new PkId(codice));
	try {
	    alberoprocMovimentiService.delete(alberoprocMovimenti);
	} catch (Exception e) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codicealberoproc));
	    this.setPageAttributes(model, alberoproc, request);
	    fixRenderEntityProperty(alberoproc);
	    model.addAttribute("alberoproc", alberoproc);
	    return "alberoproc/form";
	}
	return "redirect:view.htm?codice=" + codicealberoproc;
    }

    @RequestMapping
    public void ajaxChangeTipiSoggettoDescrizione(@RequestParam("id") Integer id, @RequestParam("descrizione") String descrizione,
	    HttpServletResponse response) throws IOException {

	AlberoprocTipisoggetto ts = alberoprocTipisoggettoService.findById(new PkId(id));
	String message = "";
	try {
	    ts.setOverrideDescrizione(descrizione);
	    alberoprocTipisoggettoService.update(ts);
	    message = getMessageFromBundle("label.datoaggiornato", null);
	} catch (BusinessValidationException e) {
	    response.setStatus(500);
	    if (!e.getInvalidValues().isEmpty()) {
		message = getMessageFromBundle(e.getInvalidValues().get(0).getMessage(), null);
	    } else {
		message = e.getMessage();
	    }
	}
	response.getWriter().write(message);
    }

    @RequestMapping
    public void ajaxChangeTipiSoggettoObb(@RequestParam("id") Integer id, @RequestParam("obbligatorio") Boolean obbligatorio,
	    HttpServletResponse response) throws IOException {

	AlberoprocTipisoggetto ts = alberoprocTipisoggettoService.findById(new PkId(id));
	String message = "";
	try {
	    ts.setObbligatorio(BooleanUtils.toBoolean(obbligatorio));
	    alberoprocTipisoggettoService.update(ts);
	    message = getMessageFromBundle("label.datoaggiornato", null);
	} catch (BusinessValidationException e) {
	    response.setStatus(500);
	    if (!e.getInvalidValues().isEmpty()) {
		message = getMessageFromBundle(e.getInvalidValues().get(0).getMessage(), null);
	    } else {
		message = e.getMessage();
	    }
	}
	response.getWriter().write(message);
    }

    @RequestMapping
    public void ajaxChangeTipiSoggettoOccurrence(@RequestParam("id") Integer id, @RequestParam("occorrenze") Integer occorrenze,
	    HttpServletResponse response) throws IOException {

	AlberoprocTipisoggetto ts = alberoprocTipisoggettoService.findById(new PkId(id));
	String message = "";
	try {
	    ts.setOccorrenzeMax(occorrenze);
	    alberoprocTipisoggettoService.update(ts);
	    message = getMessageFromBundle("label.datoaggiornato", null);
	} catch (BusinessValidationException e) {
	    response.setStatus(500);
	    if (!e.getInvalidValues().isEmpty()) {
		message = getMessageFromBundle(e.getInvalidValues().get(0).getMessage(), null);
	    } else {
		message = e.getMessage();
	    }
	}
	response.getWriter().write(message);
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void jsonFindAlberoProcTempi(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    FindAlberoProcTempiRequest jsonRequest = fromJson(request.getInputStream(), FindAlberoProcTempiRequest.class);
	    FindAlberoProcTempiResponse jsonResponse = this.alberoprocTempiService.findTempiFromAlberoProcId(jsonRequest.getCodiceIntervento());
	    response.setContentType("application/json");
	    String test = toJson(jsonResponse, true);
	    response.getOutputStream().write(test.getBytes("utf-8"));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void jsonSalvatempi(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    SalvaAlberoprocTempiRequest jsonRequest = fromJson(request.getInputStream(), SalvaAlberoprocTempiRequest.class);
	    SalvaAlberoprocTempiResponse jsonResponse = this.alberoprocTempiService.salvaAlberoprocTempi(jsonRequest);
	    response.setContentType("application/json");
	    String test = toJson(jsonResponse, true);
	    response.getOutputStream().write(test.getBytes("utf-8"));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void jsonEliminaAlberoProcTempi(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    EliminaAlberoProcTempiRequest jsonRequest = fromJson(request.getInputStream(), EliminaAlberoProcTempiRequest.class);
	    this.alberoprocTempiService.eliminaAlberoProcTempi(jsonRequest);
	    response.setContentType("application/json");
	    response.getOutputStream().write("".getBytes("utf-8"));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }
    
    @RequestMapping
    public void ajaxCheckUsingTipiSoggetto(@RequestParam("idAlberoproc") Integer idAlberoproc, @RequestParam("idTipiSoggetto") Integer idTipiSoggetto, HttpServletRequest request, HttpServletResponse response) {

	List<AlberoprocCommand> commands = alberoprocService.findAlberoprocHierarchyNoCache(idAlberoproc);
	List<Integer> idsalberoprocF = new ArrayList<Integer>();
	for(AlberoprocCommand command : commands){
	    idsalberoprocF.add(command.getId());
	}
		
	Map<String,Boolean> jsonOutMap = new HashMap<String,Boolean>();	
	AlberoprocTipisoggetto alberoprocTipisoggetto = alberoprocTipisoggettoService.findById(new PkId(idTipiSoggetto));
	if(alberoprocTipisoggetto == null){
	    log.debug("alberoprocTipisoggetto null");
	    jsonOutMap.put("usingTipiSoggetto", false);
	}else if(alberoprocTipisoggetto.getTipisoggetto() == null){
	    log.debug("alberoprocTipisoggetto.getTipisoggetto() null");
	    jsonOutMap.put("usingTipiSoggetto", false);
	}else{
	    log.debug("verifichiamo soggetti in uso");
	    Integer counttipisoggetto = alberoprocDocSoggFirmatariService.countSoggettiInUsoForDocuments(idsalberoprocF, alberoprocTipisoggettoService.findById(new PkId(idTipiSoggetto)).getTipisoggetto().getId().getCodice());
	    jsonOutMap.put("usingTipiSoggetto", counttipisoggetto > 0);
	}
		
	ObjectMapper objectMapper = new ObjectMapper();
	String json;
	try {
	    json = objectMapper.writeValueAsString(jsonOutMap);
	} catch (JsonProcessingException e1) {
	    log.error("Error eseguendo la get sul parsing", e1);
	    throw new RuntimeException("Error eseguendo la get sul parsing");
	}
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	
	try {
	    response.getWriter().write(json);
	} catch (IOException e) {
	    log.error("ERRORE", e);
	    throw new RuntimeException("ERRORE");
	}
	
    }
}
