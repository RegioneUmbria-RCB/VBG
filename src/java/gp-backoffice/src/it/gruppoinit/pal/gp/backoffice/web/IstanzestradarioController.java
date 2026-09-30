package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Catasto;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.NotificheAusl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeIstanze;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Stradariocolore;
import it.gruppoinit.pal.gp.core.domain.StradariocoloreId;
import it.gruppoinit.pal.gp.core.domain.TipiLocalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeMappaliComparator;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IstanzestradarioCommand;
import it.gruppoinit.pal.gp.core.domain.web.SessionDetails;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.verticalizzazione.IVerticalizzazioneCartograficoAttivoService;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.verticalizzazione.VerticalizzazioneCartograficoAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeStrRicalcoloRestClient;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.RicalcoloMaxReqParams;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.RicalcoloMaxRequest;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.RicalcoloAreeIstanzeDAO;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.CatastoService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.IstanzemappaliService;
import it.gruppoinit.pal.gp.core.service.NotificheAuslService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SitService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.StradariocoloreService;
import it.gruppoinit.pal.gp.core.service.TipiLocalizzazioniService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;
import it.gruppoinit.pal.gp.core.service.exception.RemoteCallException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.service.helper.SitCampiAmmessi;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.Sit;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("istanzestradarioCommand")
public class IstanzestradarioController extends BaseController<IstanzestradarioCommand> {

    private static final Logger log = LoggerFactory.getLogger(IstanzestradarioController.class);
    @Autowired
    private CatastoService catastoService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private IstanzemappaliService istanzemappaliService;
    @Autowired
    private IstanzestradarioService istanzestradarioService;
    @Autowired
    private NotificheAuslService notificheAuslService;
    @Autowired
    private SitService sitService;
    @Autowired
    private StradariocoloreService stradariocoloreService;
    @Autowired
    private StradarioService stradarioService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private VerticalizzazioniparametriService verticalizzazioniparametriService;
    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private TipiLocalizzazioniService tipiLocalizzazioniService;
    @Autowired
    private IstanzeeventiService istanzeeventiService;
    @Autowired
    private RicalcoloAreeIstanzeDAO ricalcoloAreeIstanzeDAO;

    @RequestMapping
    public String create(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request) {

	IstanzestradarioCommand istanzestradarioCommand = new IstanzestradarioCommand();
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, true);
	if (istanza.getIstanzestradarios().size() == 0) {
	    istanzestradarioCommand.getEntity().setPrimario(Boolean.TRUE);
	}
	istanzestradarioCommand.getEntity().setIstanza(istanza);
	istanzestradarioCommand.getEntity().getIstanzemappalis().add(new Istanzemappali());
	istanzestradarioCommand.setDisplayMode(IstanzestradarioCommand.NEW);
	model.addAttribute("codiceIstanza", codiceIstanza);
	model.addAttribute("cartograficoAttivo", false);
	prepareView(istanzestradarioCommand, model, request);
	return "istanzestradario/form";
    }

    private void prepareView(IstanzestradarioCommand istanzestradarioCommand, Model model, HttpServletRequest request) {

	if (istanzestradarioCommand.getEntity().getIstanzemappalis().size() == 0) {
	    model.addAttribute("mappaleInserito", false);
	} else {
	    model.addAttribute("mappaleInserito", true);
	}
	model.addAttribute("istanzestradarioCommand", istanzestradarioCommand);
	boolean isSitAttivo = false;
	String codiceComune = istanzestradarioCommand.getEntity().getIstanza().getComune().getCodicecomune();
	isSitAttivo = isVerticalizzazioneAttivaPerComune(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO, request, codiceComune);
	// Controllo che siano presenti le notifiche ASL, in tal caso deve essere presente per ogni indirizzo
	// cinfigurato il link alla funzionalità
	List<NotificheAusl> listNotificheAsl = notificheAuslService.findAll(null, null);
	boolean isNotificheASLPresenti = false;
	if (!listNotificheAsl.isEmpty()) {
	    isNotificheASLPresenti = true;
	}
	model.addAttribute("isNotificheASLPresenti", isNotificheASLPresenti);
	Set<String> campiGestiti = new HashSet<String>();
	if (isSitAttivo) {
	    campiGestiti = sitService.getCampiGestiti(ORMHelper.getToken(), ORMHelper.getSoftware());
	}
	model.addAttribute("campiGestiti", campiGestiti);
	Map<String, String> campiGestitoDettaglio = new HashMap<String, String>();
	if (isSitAttivo) {
	    campiGestitoDettaglio = sitService.getCampoDettaglioGestito(ORMHelper.getToken(), ORMHelper.getSoftware());
	}
	request.setAttribute("campiGestitoDettaglio", campiGestitoDettaglio);
	// Controlla dalla configurazione se c'è la possibilità di aggiungere un nuovo stradario
	Configurazione configurazione = configurazioneService.findById(new ConfigurazioneId(ORMHelper.getIdcomune(), ORMHelper.getSoftware()));
	model.addAttribute("isAddStradario", configurazione.getFlagVietainsstrdaistanze());
	// ordina la lista dei mappali in ordine alfabetico (sezione,foglio,particella,sub,unità imm)
	List<TipiLocalizzazioni> tipiLocalizzazionis = tipiLocalizzazioniService.findAll(null, null);
	model.addAttribute("tipiLocalizzazionis", tipiLocalizzazionis);
	Set<Istanzemappali> istanzemappalis = orderSet(istanzestradarioCommand.getEntity().getIstanzemappalis());
	istanzestradarioCommand.getEntity().setIstanzemappalis(istanzemappalis);
	setPageAttributes(model);
	fixRenderEntityProperty(istanzestradarioCommand);
	if (istanzestradarioCommand.getEntity().getIstanzemappalis().size() == 0) {
	    istanzestradarioCommand.getEntity().getIstanzemappalis().add(new Istanzemappali());
	}
	//Verifico se attiva integrazione con un cartografico
	boolean isCartograficoAttivo = this.isVerticalizzazioneAttiva(VerticalizzazioneCartograficoAttivoServiceImpl.NOME_VERTICALIZZAZIONE, request);
	istanzestradarioCommand.setVertCartograficoAttiva(isCartograficoAttivo);
    }

    private Set<Istanzemappali> orderSet(Set<Istanzemappali> istanzemappalis) {

	// Il metodo sort di collection prende come argomento solo la list
	// Trasformo il set il list
	List<Istanzemappali> istanzemappalisDaOrdinare = new ArrayList<Istanzemappali>(istanzemappalis);
	// Lo ordino con il comparator creato
	Collections.sort(istanzemappalisDaOrdinare, new IstanzeMappaliComparator());
	// Lo trasformo nuovamente in Set (dobbiamo ritrasformarlo in set perchè deve essere inserito all'interno 
	//dell'oggetto istanzestradario che come variabili ha un set di istanze mappali)
	Set<Istanzemappali> istanzemappalisOrdinati = new LinkedHashSet<Istanzemappali>();
	for (Istanzemappali istanzemappali : istanzemappalisDaOrdinare) {
	    istanzemappalisOrdinati.add(istanzemappali);
	}
	return istanzemappalisOrdinati;
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("istanzestradarioCommand") IstanzestradarioCommand istanzestradarioCommand,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	fixMergeEntityProperty(istanzestradarioCommand);
	try {
	    Set<Istanzemappali> istanzemappalis = istanzestradarioCommand.getEntity().getIstanzemappalis();
	    Catasto catasto = null;
	    for (Istanzemappali istanzemappali : istanzemappalis) {
		if (checkMappaleInsert(istanzemappali)) {
		    catasto = catastoService.findById(istanzemappali.getCatasto().getCodice());
		    istanzemappali.setCatasto(catasto);
		} else {
		    istanzestradarioCommand.getEntity().getIstanzemappalis().remove(istanzemappali);
		}
	    }
	    //if (BooleanUtils.isTrue(istanzestradarioCommand.getEntity().getValido())) {
	    // §§§BEGIN§§§
	    boolean isSitAttivo = false;
	    String codiceComune = istanzestradarioCommand.getEntity().getIstanza().getComune().getCodicecomune();
	    isSitAttivo = isVerticalizzazioneAttivaPerComune(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO, request, codiceComune);
	    if (isSitAttivo) {
		Istanzestradario filter = requestToIstanzestradario(request, false);
		try {
		    boolean isValido = sitService.effettuaValidazioneFormale(ORMHelper.getToken(), ORMHelper.getSoftware(), filter);
		    istanzestradarioCommand.getEntity().setValido(isValido);
		} catch (Exception e) {
		    log.error("Errore nella chiamata alla funzionalità effettuaValidazioneFormale: {}", e.getMessage());
		}
	    }
	    // §§§END§§§
	    // }
	    istanzestradarioService.insert(istanzestradarioCommand.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzestradarioCommand.getEntity(), true, e);
	    prepareView(istanzestradarioCommand, model, request);
	    return "istanzestradario/form";
	}
	status.setComplete();
	try {
	    log.debug("ricalcolo started...");
	    RicalcoloMaxRequest req = new RicalcoloMaxRequest();
	    
	    List<String> ricalcoloAreeIdL = new ArrayList<String>();
	    if (!StringUtils.isBlank(istanzestradarioCommand.getEntity().getIstanza().getUuid())) {
		List<RicalcoloAreeIstanze> testateId = ricalcoloAreeIstanzeDAO.findRicalcoloInProgressIst(istanzestradarioCommand.getEntity().getIstanza().getUuid());
		if(testateId != null && !testateId.isEmpty()){
		    for(RicalcoloAreeIstanze testataId : testateId){
			if(testataId != null){
			    ricalcoloAreeIdL.add(testataId.getIdRicalcoloAree());
			}
		    }
		}
	    } else {
		log.debug("no uuid istanza populated");
	    }
	    req.setRicalcoloAreeIdL(ricalcoloAreeIdL);
	    
	    RicalcoloMaxReqParams params = new RicalcoloMaxReqParams();
	    params.setToken(ORMHelper.getToken()); //Prendo questo
	    params.setSoftware(ORMHelper.getSoftware());
	    params.setType("singleistanza");
	    new IstanzeStrRicalcoloRestClient().ricalcolaArea(req, params);
	    log.debug("ricalcolo ended...");
	} catch (Exception e) {
	    log.error("Error in ricalcolo aree", e);
	    try {
		log.info("Inserting in event table");
		istanzeeventiService.insert("Errore sul ricalcolo aree: " + e.getMessage(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null,
			istanzestradarioCommand.getEntity().getIstanza());
		log.info("Insert executed");
	    } catch (Exception e1) {
		log.error("Error in insert eventi", e1); //va reso il meno bloccante possibile
	    }
	}
	return "redirect:view.htm?codice=" + istanzestradarioCommand.getEntity().getId().getCodice() + "&status_msg=01";
    }

    public static boolean checkMappaleInsert(Istanzemappali im) {

	if (im == null) {
	    return false;
	}
	// String catasto = StringUtils.defaultIfEmpty(im.getCatasto().getCodice(), "");
	String foglio = StringUtils.defaultIfEmpty(im.getFoglio(), "");
	String particella = StringUtils.defaultIfEmpty(im.getParticella(), "");
	String sub = StringUtils.defaultIfEmpty(im.getSub(), "");
	String sezione = StringUtils.defaultIfEmpty(im.getSezione(), "");
	String unitaimmob = StringUtils.defaultIfEmpty(im.getUnitaimmob(), "");
	// true solamente se la stringa concatenata da tutti i valori è diversa da Stringa vuota ""
	String result = foglio + particella + sub + sezione + unitaimmob;
	return StringUtils.isNotBlank(result);
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	IstanzestradarioCommand istanzestradarioCommand = new IstanzestradarioCommand();
	istanzestradarioCommand.setDisplayMode(IstanzestradarioCommand.EDIT);
	Istanzestradario istanzestradario = istanzestradarioService.findById(id);
	istanzestradarioCommand.setEntity(istanzestradario);
	checkAccessoInformazioni(istanzestradario.getIstanza(), false);
	prepareView(istanzestradarioCommand, model, request);
	model.addAttribute("codiceIstanza", istanzestradarioCommand.getEntity().getIstanza().getId().getCodice());
	IVerticalizzazioneCartograficoAttivoService vertCartografico = new VerticalizzazioneCartograficoAttivoServiceImpl(
		this.verticalizzazioniService);
	boolean isCartograficoAttivo = vertCartografico.isAttiva();
	model.addAttribute("cartograficoAttivo", isCartograficoAttivo);
	return "istanzestradario/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("istanzestradarioCommand") IstanzestradarioCommand istanzestradarioCommand,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(istanzestradarioCommand);
	try {
	    // Gestisce il recuperò dei valori del catasto di ogni singolo mappale per salvare eventuali modifiche
	    if (istanzestradarioCommand.getEntity().getIstanzemappalis().size() > 0) {
		Set<Istanzemappali> istanzemappaliTemps = istanzestradarioCommand.getEntity().getIstanzemappalis();
		Set<Istanzemappali> istanzemappalis = new LinkedHashSet<Istanzemappali>();
		int i = 0;
		for (Istanzemappali istanzemappali : istanzemappaliTemps) {
		    if (checkMappaleInsert(istanzemappali)) {
			if (request.getParameter("entity.istanzemappalis[" + i + "].catasto.codice") != null) {
			    Catasto catasto = catastoService.findById(request.getParameter("entity.istanzemappalis[" + i + "].catasto.codice"));
			    istanzemappali.setCatasto(catasto);
			}
			istanzemappalis.add(istanzemappali);
			i++;
		    }
		}
		istanzestradarioCommand.getEntity().setIstanzemappalis(istanzemappalis);
	    }
	    checkAccessoInformazioni(istanzestradarioCommand.getEntity().getIstanza(), true);
	    // if (!BooleanUtils.isTrue(istanzestradarioCommand.getEntity().getValido())) {
	    Istanzestradario filter = requestToIstanzestradario(request, false);
	    // §§§BEGIN§§§
	    boolean isSitAttivo = false;
	    String codiceComune = istanzestradarioCommand.getEntity().getIstanza().getComune().getCodicecomune();
	    isSitAttivo = isVerticalizzazioneAttivaPerComune(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO, request, codiceComune);
	    if (isSitAttivo) {
		try {
		    boolean isValido = sitService.effettuaValidazioneFormale(ORMHelper.getToken(), ORMHelper.getSoftware(), filter);
		    istanzestradarioCommand.getEntity().setValido(isValido);
		} catch (Exception e) {
		    log.error("Errore nella chiamata alla funzionalità effettuaValidazioneFormale: {}", e.getMessage());
		}
	    }
	    // §§§END§§§
	    //}
	    checkAccessoInformazioni(istanzestradarioCommand.getEntity().getIstanza(), true);
	    istanzestradarioService.update(istanzestradarioCommand.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzestradarioCommand.getEntity(), true, e);
	    prepareView(istanzestradarioCommand, model, request);
	    return "istanzestradario/form";
	}
	status.setComplete();
	try {
	    log.debug("ricalcolo started...");
	    RicalcoloMaxRequest req = new RicalcoloMaxRequest();
	    
	    List<String> ricalcoloAreeIdL = new ArrayList<String>();
	    if (!StringUtils.isBlank(istanzestradarioCommand.getEntity().getIstanza().getUuid())) {
		List<RicalcoloAreeIstanze> testateId = ricalcoloAreeIstanzeDAO.findRicalcoloInProgressIst(istanzestradarioCommand.getEntity().getIstanza().getUuid());
		if(testateId != null && !testateId.isEmpty()){
		    for(RicalcoloAreeIstanze testataId : testateId){
			if(testataId != null){
			    ricalcoloAreeIdL.add(testataId.getIdRicalcoloAree());
			}
		    }
		}
	    } else {
		log.debug("no uuid istanza populated");
	    }
	    req.setRicalcoloAreeIdL(ricalcoloAreeIdL);
	    
	    RicalcoloMaxReqParams params = new RicalcoloMaxReqParams();
	    params.setToken(ORMHelper.getToken()); //Prendo questo
	    params.setSoftware(ORMHelper.getSoftware());
	    params.setType("singleistanza");
	    new IstanzeStrRicalcoloRestClient().ricalcolaArea(req, params);
	    log.debug("ricalcolo ended...");
	} catch (Exception e) {
	    log.error("Error in ricalcolo aree", e);
	    try {
		log.info("Inserting in event table");
		istanzeeventiService.insert("Errore sul ricalcolo aree: " + e.getMessage(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null,
			istanzestradarioCommand.getEntity().getIstanza());
		log.info("Insert executed");
	    } catch (Exception e1) {
		log.error("Error in insert eventi", e1); //va reso il meno bloccante possibile
	    }
	}
	return "redirect:view.htm?codice=" + istanzestradarioCommand.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("istanzestradarioCommand") IstanzestradarioCommand istanzestradarioCommand,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	Istanzestradario objToDelete = istanzestradarioService.findById(istanzestradarioCommand.getEntity().getId());
	Istanze istanza = objToDelete.getIstanza();
	Integer codiceIstanza = istanza.getId().getCodice();
	try {
	    checkAccessoInformazioni(objToDelete.getIstanza(), true);
	    istanzestradarioService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    prepareView(istanzestradarioCommand, model, request);
	    return "istanzestradario/form";
	}
	status.setComplete();
	return "redirect:../istanze/view.htm?codice=" + codiceIstanza;
    }

    @RequestMapping
    public String addMappale(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	IstanzestradarioCommand istanzestradarioCommand = new IstanzestradarioCommand();
	istanzestradarioCommand.setDisplayMode(IstanzestradarioCommand.EDIT);
	Istanzestradario istanzestradario = istanzestradarioService.findById(id);
	istanzestradarioCommand.setEntity(istanzestradario);
	prepareView(istanzestradarioCommand, model, request);
	Istanzemappali nuovoMappale = new Istanzemappali();
	nuovoMappale.setIstanza(istanzestradario.getIstanza());
	nuovoMappale.setIstanzestradario(istanzestradario);
	istanzestradario.getIstanzemappalis().add(nuovoMappale);
	// Recupera la lista dell'oggetto catasto e mette in prima posizione quello salvato nella configurazione utente
	// e la setta sul model visualizzarla sulla combobox della jsp
	setListaCatasto(model);
	return "istanzestradario/form";
    }

    private void setListaCatasto(Model model) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	Configurazioneutente configurazioneutente = configurazioneutenteService
		.findById(new ConfigurazioneutenteId(responsabile.getId().getCodice(), WebConstants.CONF_UTENTE_CATASTO_SCELTO));
	List<Catasto> catastos = catastoService.findAll();
	// se non c'è nessuna configurazione salvata propone la lista di default
	if (configurazioneutente != null) {
	    Catasto catastoMemorizzato = catastoService.findById(configurazioneutente.getValore());
	    catastos.remove(catastoMemorizzato);
	    Set<Catasto> listCatasto = new LinkedHashSet<Catasto>();
	    listCatasto.add(catastoMemorizzato);
	    listCatasto.addAll(catastos);
	    model.addAttribute("catastoList", listCatasto);
	} else {
	    model.addAttribute("catastoList", catastos);
	}
    }

    @RequestMapping
    public String deleteMappale(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Istanzemappali mappale = istanzemappaliService.findById(id);
	Istanzestradario istanzestradario = mappale.getIstanzestradario();
	checkAccessoInformazioni(istanzestradario.getIstanza(), true);
	int codiceistanzastradario = istanzestradario.getId().getCodice().intValue();
	istanzemappaliService.delete(mappale);
	return "redirect:view.htm?codice=" + codiceistanzastradario;
    }

    @Override
    protected void setPageAttributes(Model model) {

	boolean isNotificheAusl = notificheAuslService.existsRecords();
	model.addAttribute("isNotificheAuslVisible", isNotificheAusl);
	boolean isStradariocolore = stradariocoloreService.existsRecords();
	model.addAttribute("isStradariocoloreVisible", isStradariocolore);
	Verticalizzazioniparametri verticalizzazioniPtiposit = verticalizzazioniService
		.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO, WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO_TIPOSIT);
	String tipoSit = "";
	if (verticalizzazioniPtiposit != null) {
	    tipoSit = verticalizzazioniPtiposit.getValore();
	}
	model.addAttribute(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO_TIPOSIT, tipoSit);
	List<Stradariocolore> stradariocoloreList = stradariocoloreService.findAll();
	model.addAttribute("stradariocoloreList", stradariocoloreList);
	List<Catasto> catastoList = catastoService.findAll();
	model.addAttribute("catastoList", catastoList);
    }

    @RequestMapping()
    public ModelAndView jsonCompilaSIT(@RequestParam("provenienza") String provenienza, HttpServletRequest request) {

	boolean isIstanze = StringUtils.defaultIfEmpty(provenienza, "").equalsIgnoreCase("ISTANZE") ? true : false;
	Istanzestradario filter = requestToIstanzestradario(request, isIstanze);
	Map<String, Object> model = new HashMap<String, Object>();
	SessionDetails sessionDetails = getSessionDetails(request);
	String eccezione = "";
	Sit sit = new Sit();
	boolean isEccezioneRemota = false;
	try {
	    SitCampiAmmessi campo = getCampoFromNome(((String) request.getParameter("idCampo").toUpperCase()));
	    sit = sitService.validaValore(sessionDetails.getToken(), campo, filter);
	} catch (RemoteCallException e) {
	    eccezione = e.getMessage();
	    isEccezioneRemota = true;
	} catch (Exception e) {
	    eccezione = e.getMessage();
	}
	model.put("isEccezioneRemota", isEccezioneRemota);
	model.put("errori", eccezione);
	model.put("datiSit", sit);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping()
    public String popupgeoreferenzia(@RequestParam("codice") Integer codice, HttpServletRequest request) {

	// checkaccesso istanza
	Istanzestradario is = istanzestradarioService.findById(new PkId(codice));
	checkAccessoInformazioni(is.getIstanza(), true);
	//recupera url da verticalizzazione
	String urlTo = BackofficeNETConstants.getURL_APP_ASPNET() +
		"/Istanze/SitFirenze/geoin.aspx?Software=" +
		ORMHelper.getSoftware() +
		"&Token=" +
		ORMHelper.getToken() +
		"&IdStradario=" +
		codice.intValue() +
		"&idElemento=idPuntoSit_id";
	// redirect a url
	if (urlTo.startsWith("http")) {
	    // se base URL è valorizzata allora redirect a http://base_url/+urlTo
	    return "redirect:" + urlTo;
	} else {
	    // urlTo 
	    return "redirect:../.." + urlTo;
	}
    }

    @RequestMapping()
    public String ajaxValidaSIT(@RequestParam("provenienza") String provenienza, HttpServletRequest request) {

	boolean isIstanze = StringUtils.defaultIfEmpty(provenienza, "").equalsIgnoreCase("ISTANZE") ? true : false;
	Istanzestradario filter = requestToIstanzestradario(request, isIstanze);
	SessionDetails sessionDetails = getSessionDetails(request);
	List<String> listaValori = new ArrayList<String>();
	String eccezione = "";
	String valore = request.getParameter("_objvalue");
	String isClick = request.getParameter("isClick");
	boolean isEccezioneRemota = false;
	try {
	    SitCampiAmmessi campo = getCampoFromNome(((String) request.getParameter("idCampo").toUpperCase()));
	    listaValori = sitService.getListaValori(sessionDetails.getToken(), campo, filter);
	    if (StringUtils.isNotBlank(valore)) {
		if (!listaValori.contains(valore)) {
		    eccezione = getMessageFromBundle("service_error.sit.valore_non_trovato", new Object[] { valore });
		    // eccezione = "Nei risultati non è stato trovato il valore ricercato <b>[" + valore + "]</b>";
		} else {
		    listaValori = new ArrayList<String>();
		    listaValori.add(valore);
		}
	    }
	} catch (RemoteCallException e) {
	    eccezione = e.getMessage();
	    isEccezioneRemota = true;
	} catch (Exception e) {
	    eccezione = e.getMessage();
	    isEccezioneRemota = true;
	}
	request.setAttribute("isEccezioneRemota", isEccezioneRemota);
	request.setAttribute("isClick", isClick);
	request.setAttribute("errori", eccezione);
	request.setAttribute("listaValori", listaValori);
	return "istanzestradario/listaValoriSit";
    }

    @RequestMapping()
    public String ajaxDettaglioSIT(@RequestParam("provenienza") String provenienza, HttpServletRequest request) {

	boolean isIstanze = StringUtils.defaultIfEmpty(provenienza, "").equalsIgnoreCase("ISTANZE") ? true : false;
	Istanzestradario filter = requestToIstanzestradario(request, isIstanze);
	SessionDetails sessionDetails = getSessionDetails(request);
	Map<String, String> mapValori = new HashMap<String, String>();
	String eccezione = "";
	String valore = request.getParameter("_objvalue");
	String isClick = request.getParameter("isClick");
	boolean isEccezioneRemota = false;
	System.out.println((String) request.getParameter("idCampo").toUpperCase());
	try {
	    SitCampiAmmessi campo = getCampoFromNome(((String) request.getParameter("idCampo").toUpperCase()));
	    /////////////////////////////////////////////////////////////////////////////////////////
	    mapValori = sitService.getDetailField(sessionDetails.getToken(), campo, filter);
	    if (!mapValori.isEmpty()) {
	    }
	} catch (RemoteCallException e) {
	    eccezione = e.getMessage();
	    isEccezioneRemota = true;
	} catch (Exception e) {
	    eccezione = e.getMessage();
	    isEccezioneRemota = true;
	}
	request.setAttribute("isEccezioneRemota", isEccezioneRemota);
	request.setAttribute("isClick", isClick);
	request.setAttribute("errori", eccezione);
	request.setAttribute("codiceIstanza", filter.getIstanza().getId().getCodice());
	Iterator iterator = mapValori.entrySet().iterator();
	while (iterator.hasNext()) {
	    Map.Entry mapEntry = (Map.Entry) iterator.next();
	    request.setAttribute(mapEntry.getKey().toString(), mapEntry.getValue());
	}
	request.setAttribute("mapValori", mapValori);
	return "istanzestradario/dettaglioValoriSit";
    }

    @RequestMapping()
    public void ajaxSITLink(@RequestParam("contesto") String contesto, @RequestParam("codiceistanza") Integer codiceistanza,
	    @RequestParam("boFeature") String boFeature, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	boolean sitAttivo = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO);
	if (!sitAttivo) {
	    return;
	}
	Verticalizzazioniparametri tipoSit = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO,
		WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO_TIPOSIT);
	StringBuffer sbuf = new StringBuffer();
	if (tipoSit != null) {
	    String ts = StringUtils.defaultIfEmpty(tipoSit.getValore(), "");
	    if (StringUtils.isNotBlank(ts)) {
		sbuf = populateSitLinks(ts, contesto, request, codiceistanza, boFeature);
	    }
	}
	try {
	    response.getWriter().write(sbuf.toString());
	} catch (IOException e) {
	    log.error("ajaxSITLink: {}", e.getMessage());
	}
	// §§§END§§§
    }

    @RequestMapping
    public String copiaMappale(@RequestParam("codiceMappale") Integer codiceMappale, @RequestParam("codiceIstanzaStrd") Integer codiceIstanzaStrd,
	    Model model, @ModelAttribute("istanzestradarioCommand") IstanzestradarioCommand istanzestradarioCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// Recupero l'istanza mappale sorgente da cui creare la copia
	Istanzemappali istanzemappaleSorgente = istanzemappaliService.findById(new PkId(codiceMappale));
	// Crea una copia dell'oggetto passato, con id null
	checkAccessoInformazioni(istanzemappaleSorgente.getIstanzestradario().getIstanza(), true);
	try {
	    Istanzemappali istanzemappaleReplica = replicaIstanzamappale(istanzemappaleSorgente, codiceIstanzaStrd);
	    istanzemappaliService.insert(istanzemappaleReplica);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzestradarioCommand.getEntity(), true, e);
	    prepareView(istanzestradarioCommand, model, request);
	    return "istanzestradario/form";
	}
	return "redirect:view.htm?codice=" + codiceIstanzaStrd;
    }

    /**
     * Il metodo copia tutta la localizzazione dall'istanza passata come istanzaSorgente su istanzaDestinatario
     * 
     * @param codiceIstanza
     * @param codiceIstanzaSorgente
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String copiaLocalizzazione(@RequestParam("codiceIstanzaDestinataria") Integer codiceIstanza,
	    @RequestParam("codiceIstanzaSorgente") Integer codiceIstanzaSorgente, Model model, HttpServletRequest request) {

	Istanze istanzaDestinatario = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanzaDestinatario, true);
	Istanze istanzaSorgente = istanzeService.findById(new PkId(codiceIstanzaSorgente));
	istanzestradarioService.copiaLocalizzazioniWithMappali(istanzaSorgente, istanzaDestinatario, true);
	return "redirect:../istanze/view.htm?codice=" + codiceIstanza + "&status_msg=02";
    }

    @RequestMapping
    public String separaMappale(@RequestParam("codiceMappale") Integer codiceMappale,
	    @RequestParam("codiceIstanzaStradario") Integer codiceIstanzaStradario, Model model,
	    @ModelAttribute("istanzestradarioCommand") IstanzestradarioCommand istanzestradarioCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// Recupero l'istanza mappale sorgente da cui creare la copia
	Istanzemappali istanzemappaleSorgente = istanzemappaliService.findById(new PkId(codiceMappale));
	checkAccessoInformazioni(istanzemappaleSorgente.getIstanzestradario().getIstanza(), true);
	// Creo un nuovo istanzestradario con le segunti caratteristiche
	// a- Associato all'istanza in esame
	// b- Associato alla stesso stradario dell' istanza stradario collegato al mappale scelto
	// c- Con i dati mappali del record istanzamappali scelto
	try {
	    istanzestradarioService.insertReplicaStradario(istanzemappaleSorgente);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzestradarioCommand.getEntity(), true, e);
	    prepareView(istanzestradarioCommand, model, request);
	    return "istanzestradario/form";
	}
	return "redirect:view.htm?codice=" + codiceIstanzaStradario;
    }

    @RequestMapping
    public String duplicaStradario(@RequestParam("codice") Integer codice, Model model,
	    @ModelAttribute("istanzestradarioCommand") IstanzestradarioCommand istanzestradarioCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// Recupero l'istanza mappale sorgente da cui creare la copia , prendo quello del command in modo
	// Da poter salvare le modifiche effettuate (la funzionalità duplica fa anche un update)
	Istanzestradario istanzeStradarioSorgente = istanzestradarioCommand.getEntity();
	checkAccessoInformazioni(istanzeStradarioSorgente.getIstanza(), true);
	// Creo un nuovo istanzestradario con le segunti caratteristiche
	// a- Associato all'istanza in esame
	// b- Associato alla stesso stradario dell' istanza stradario collegato al mappale scelto
	// c- Con tutti i  dati mappali del record istanzamappali scelto
	Istanzestradario istanzestradarioDuplicato = new Istanzestradario();
	try {
	    istanzestradarioDuplicato = istanzestradarioService.insertDuplicaStradario(istanzeStradarioSorgente);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzestradarioCommand.getEntity(), true, e);
	    prepareView(istanzestradarioCommand, model, request);
	    return "istanzestradario/form";
	}
	return "redirect:view.htm?codice=" + istanzestradarioDuplicato.getId().getCodice();
    }

    @RequestMapping
    public void ajaxFindAltriIndirizziByIstanza(@RequestParam("codiceIstanza") Integer codiceistanza, HttpServletResponse response)
	    throws IOException {

	Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	List<Istanzestradario> list = istanzestradarioService.findByIstanza(istanza.getId().getCodice());
	StringBuffer buffer = new StringBuffer();
	for (Istanzestradario istrads : list) {
	    if (BooleanUtils.isFalse(istrads.getPrimario())) {
		buffer = buffer.append("- ");
		buffer = buffer.append(istrads.getDescrizioneEstesaTransient());
		buffer = buffer.append("<br />");
	    }
	}
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void ajaxTrasformAndInsertVisuraInPdf(@RequestParam("codice") Integer codice, @RequestParam("value") String html, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	String status_msg = "01";
	try {
	    istanzestradarioService.trasformAndInsertVisuraInPdf(codice, html);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Errore nella creazione del file PDF:" + e.getMessage());
	    status_msg = "03";
	}
	response.setContentType("text/plain");
	response.getWriter().write("La visura è stata salavata nei documenti dell' istanza");
    }

    /**
     * Il metodo ritorna un oggetto istanza mappale replica del sorgente passato (l'oggetto avrà id null inmodo che
     * all'insert verrà inserito sul db un nuovo oggetto)
     * 
     * @param istanzemappaleSorgente
     * @param codiceIstanzaStrd
     * @return
     */
    private Istanzemappali replicaIstanzamappale(Istanzemappali istanzemappaleSorgente, Integer codiceIstanzaStrd) {

	Istanzemappali istanzaMappaleReplica = new Istanzemappali();
	if (istanzemappaleSorgente.getCatasto() != null && StringUtils.isNotBlank(istanzemappaleSorgente.getCatasto().getCodice())) {
	    istanzaMappaleReplica.setCatasto(istanzemappaleSorgente.getCatasto());
	}
	if (StringUtils.isNotBlank(istanzemappaleSorgente.getDescrizioneEstesa())) {
	    istanzaMappaleReplica.setDescrizioneEstesa(istanzemappaleSorgente.getDescrizioneEstesa());
	}
	if (StringUtils.isNotBlank(istanzemappaleSorgente.getFoglio())) {
	    istanzaMappaleReplica.setFoglio(istanzemappaleSorgente.getFoglio());
	}
	istanzaMappaleReplica.setIstanza(istanzemappaleSorgente.getIstanza());
	istanzaMappaleReplica.setIstanzestradario(istanzemappaleSorgente.getIstanzestradario());
	if (StringUtils.isNotBlank(istanzemappaleSorgente.getParticella())) {
	    istanzaMappaleReplica.setParticella(istanzemappaleSorgente.getParticella());
	}
	if (StringUtils.isNotBlank(istanzemappaleSorgente.getSezione())) {
	    istanzaMappaleReplica.setSezione(istanzemappaleSorgente.getSezione());
	}
	if (StringUtils.isNotBlank(istanzemappaleSorgente.getSub())) {
	    istanzaMappaleReplica.setSub(istanzemappaleSorgente.getSub());
	}
	if (StringUtils.isNotBlank(istanzemappaleSorgente.getTransientCodicecatasto())) {
	    istanzaMappaleReplica.setTransientCodicecatasto(istanzemappaleSorgente.getTransientCodicecatasto());
	}
	if (StringUtils.isNotBlank(istanzemappaleSorgente.getUnitaimmob())) {
	    istanzaMappaleReplica.setUnitaimmob(istanzemappaleSorgente.getUnitaimmob());
	}
	istanzaMappaleReplica.setPrimario(Boolean.FALSE);
	return istanzaMappaleReplica;
    }

    private StringBuffer populateSitLinks(String modulo, String contesto, HttpServletRequest request, Integer codiceistanza, String boFeature) {

	List<Verticalizzazioniparametri> params = verticalizzazioniparametriService.findParametriConfiguratiByModulo(modulo);
	StringBuffer sbuf = new StringBuffer();
	Istanzestradario is = requestToIstanzestradario(request, false);
	String url = "";
	String label = "";
	boolean proceed = false;
	for (Verticalizzazioniparametri vp : params) {
	    url = vp.getValore();
	    proceed = false;
	    if (contesto.equalsIgnoreCase("STRADARIO")) {
		if (vp.getVerticalizzazioniparametribase().getId().getParametro()
			.equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_SIT_URLCARTSTRADARIO)) {
		    label = "label.gis";
		    proceed = true;
		}
		if (vp.getVerticalizzazioniparametribase().getId().getParametro()
			.equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_SIT_URLRUESTRADARIO)) {
		    label = "label.rue";
		    proceed = true;
		}
		if (vp.getVerticalizzazioniparametribase().getId().getParametro()
			.equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_SIT_URLPOCSTRADARIO)) {
		    label = "label.poc";
		    proceed = true;
		}
	    } else {
		if (vp.getVerticalizzazioniparametribase().getId().getParametro()
			.equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_SIT_URLCARTMAPPALE)) {
		    label = "label.gis";
		    proceed = true;
		}
		if (vp.getVerticalizzazioniparametribase().getId().getParametro()
			.equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_SIT_URLRUEMAPPALE)) {
		    label = "label.rue";
		    proceed = true;
		}
		if (vp.getVerticalizzazioniparametribase().getId().getParametro()
			.equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_SIT_URLPOCMAPPALE)) {
		    label = "label.poc";
		    proceed = true;
		}
	    }
	    if (proceed) {
		sbuf.append(replaceUrlVariables(url, is, label, codiceistanza));
	    }
	}
	if (StringUtils.isNotBlank(boFeature)) {
	    try {
		Map<String, String> m = sitService.getBoFeatures(ORMHelper.getToken(), ORMHelper.getSoftware());
		for (Map.Entry<String, String> entry : m.entrySet()) {
		    String key = entry.getKey();
		    if (key.equalsIgnoreCase(boFeature)) {
			String valore = entry.getValue();
			sbuf.append(replaceUrlVariables(valore, is, "label.image.bofeature.gis", codiceistanza));
		    }
		}
	    } catch (RemoteCallException e) {
		log.error("getBoFeatures,  {}", e);
	    } catch (Exception e) {
		log.error("getBoFeatures, {}", e);
	    }
	}
	return sbuf;
    }

    private String replaceUrlVariables(String url, Istanzestradario is, String label, Integer codiceistanza) {

	String result = "&nbsp;<a href=\"javascript:void(0)\" onclick=\"elaboraLinkSIT('";
	// BEGIN ATTENZIONE!!! SOSTITUZIONI SPOSTATE NELLA JSP includes/javascriptSIT.jsp
	// url = url.replaceAll("\\$CODICEVIA\\$", StringUtils.defaultIfEmpty((String) EntityUtils.getNestedProperty(is, "stradario.codviario"), ""));
	// url = url.replaceAll("\\$CIVICO\\$", StringUtils.defaultIfEmpty(is.getCivico(), ""));
	// url = url.replaceAll("\\$COLORE\\$", String.valueOf(is.getStradariocolore().getId().getCodicecolore()));
	// url = url.replaceAll("\\$ISTANZA\\$", String.valueOf(codiceistanza));
	// url = url.replaceAll("\\$TOKEN\\$", ORMHelper.getToken());
	// url = url.replaceAll("\\$SOFTWARE\\$", ORMHelper.getSoftware());
	// url = url.replaceAll("\\$VIA\\$", StringUtils.defaultIfEmpty((String) EntityUtils.getNestedProperty(is, "stradario.descrizione"), ""));
	//	String catasto = "";
	//	String sezione = "";
	//	String foglio = "";
	//	String particella = "";
	//	String sub = "";
	//	if (is.getIstanzemappalis() != null) {
	//	    if (is.getIstanzemappalis().size() > 0) {
	//		for (Istanzemappali m : is.getIstanzemappalis()) {
	//		    catasto = m.getCatasto().getCodice();
	//		    foglio = m.getFoglio();
	//		    sezione = m.getSezione();
	//		    particella = m.getParticella();
	//		    sub = m.getSub();
	//		}
	//	    }
	//	}
	// url = url.replaceAll("\\$CATASTO\\$", StringUtils.defaultIfEmpty(catasto, ""));
	// url = url.replaceAll("\\$SEZIONE\\$", StringUtils.defaultIfEmpty(sezione, ""));
	// url = url.replaceAll("\\$FOGLIO\\$", StringUtils.defaultIfEmpty(foglio, ""));
	// url = url.replaceAll("\\$PARTICELLA\\$", StringUtils.defaultIfEmpty(particella, ""));
	// url = url.replaceAll("\\$SUB\\$", StringUtils.defaultIfEmpty(sub, ""));
	// END ATTENZIONE!!! SOSTITUZIONI SPOSTATE NELLA JSP includes/javascriptSIT.jsp
	result = result + url + "');\">" + getMessageFromBundle(label, null) + "</a>";
	return result;
    }

    private Istanzestradario requestToIstanzestradario(HttpServletRequest request, boolean isIstanze) {

	Istanzestradario result = new Istanzestradario();
	Stradario stradariodaDb = null;
	if (isIstanze) {
	    result.getStradario().setCodviario(request.getParameter("istanzeFilter.istanzestradario.stradario.codviario"));
	    if (!StringUtils.defaultIfEmpty(request.getParameter("istanzeFilter.istanzestradario.stradariocolore.id.codicecolore"), "").equals("")) {
		StradariocoloreId id = new StradariocoloreId(request.getParameter("istanzeFilter.istanzestradario.stradariocolore.id.codicecolore"));
		Stradariocolore colore = stradariocoloreService.findById(id);
		result.setStradariocolore(colore);
	    }
	    result.setScala(request.getParameter("istanzeFilter.istanzestradario.scala"));
	    result.setInterno(request.getParameter("istanzeFilter.istanzestradario.interno"));
	    result.setFrazione(request.getParameter("istanzeFilter.istanzestradario.frazione"));
	    result.setCap(request.getParameter("istanzeFilter.istanzestradario.cap"));
	    result.setCircoscrizione(request.getParameter("istanzeFilter.istanzestradario.circoscrizione"));
	    result.setCivico(request.getParameter("istanzeFilter.istanzestradario.civico"));
	    result.setAccessoTipo(request.getParameter("istanzeFilter.istanzestradario.accessoTipo"));
	    result.setAccessoNumero(request.getParameter("istanzeFilter.istanzestradario.accessoNumero"));
	    result.setAccessoDescrizione(request.getParameter("istanzeFilter.istanzestradario.accessoDescrizione"));
	    result.setCodicecivico(request.getParameter("istanzeFilter.istanzestradario.codicecivico"));
	    result.setEsponente(request.getParameter("istanzeFilter.istanzestradario.esponente"));
	    result.setEsponenteinterno(request.getParameter("istanzeFilter.istanzestradario.esponenteinterno"));
	    result.setFabbricato(request.getParameter("istanzeFilter.istanzestradario.fabbricato"));
	    result.setPiano(request.getParameter("istanzeFilter.istanzestradario.piano"));
	    result.setQuartiere(request.getParameter("istanzeFilter.istanzestradario.quartiere"));
	    if (!StringUtils.defaultIfEmpty(request.getParameter("istanzeFilter.istanzestradario.stradario.id.codice"), "").equals("")) {
		Integer codiceStradario = Integer.valueOf(request.getParameter("istanzeFilter.istanzestradario.stradario.id.codice"));
		result.getStradario().getId().setCodice(codiceStradario);
		stradariodaDb = stradarioService.findById(new PkId(codiceStradario));
	    }
	    Comuni comune = new Comuni();
	    comune.setCodicecomune(request.getParameter("entity.comune.codicecomune"));
	    result.getIstanza().setComune(comune);
	    Istanzemappali mappale = new Istanzemappali();
	    String codicecatasto = request.getParameter("istanzeFilter.istanzemappali.catasto.codice");
	    if (StringUtils.isNotBlank(codicecatasto)) {
		Catasto catasto = catastoService.findById(codicecatasto);
		mappale.setCatasto(catasto);
	    }
	    mappale.setSezione(request.getParameter("istanzeFilter.istanzemappali.sezione"));
	    mappale.setFoglio(request.getParameter("istanzeFilter.istanzemappali.foglio"));
	    mappale.setParticella(request.getParameter("istanzeFilter.istanzemappali.particella"));
	    mappale.setSub(request.getParameter("istanzeFilter.istanzemappali.sub"));
	    mappale.setUnitaimmob(request.getParameter("istanzeFilter.istanzemappali.unitaimmob"));
	    result.getIstanzemappalis().add(mappale);
	} else {
	    Comuni comune = new Comuni();
	    comune.setCodicecomune(getParameter(request, "istanza.comune.codicecomune"));
	    result.getIstanza().setComune(comune);
	    result.getStradario().setCodviario(getParameter(request, "stradario.codviario"));
	    result.getStradariocolore().getId().setCodicecolore(getParameter(request, "stradariocolore.id.codicecolore"));
	    result.setScala(getParameter(request, "scala"));
	    result.setInterno(getParameter(request, "interno"));
	    result.setFrazione(getParameter(request, "frazione"));
	    result.setCap(getParameter(request, "cap"));
	    result.setCircoscrizione(getParameter(request, "circoscrizione"));
	    result.setCivico(getParameter(request, "civico"));
	    result.setAccessoTipo(getParameter(request, "accessoTipo"));
	    result.setAccessoNumero(getParameter(request, "accessoNumero"));
	    result.setAccessoDescrizione(getParameter(request, "accessoDescrizione"));
	    result.setCodicecivico(getParameter(request, "codicecivico"));
	    result.setEsponente(getParameter(request, "esponente"));
	    result.setEsponenteinterno(getParameter(request, "esponenteinterno"));
	    result.setFabbricato(getParameter(request, "fabbricato"));
	    result.setPiano(request.getParameter("piano"));
	    result.setQuartiere(request.getParameter("quartiere"));
	    if (!StringUtils.defaultIfEmpty(getParameter(request, "stradario.id.codice"), "").equals("")) {
		Integer codiceStradario = Integer.valueOf(getParameter(request, "stradario.id.codice"));
		result.getStradario().getId().setCodice(codiceStradario);
		stradariodaDb = stradarioService.findById(new PkId(codiceStradario));
	    }
	    String daValidare = request.getParameter("daValidare");
	    Istanzemappali mappale = new Istanzemappali();
	    boolean isMappale = false;
	    if (StringUtils.isNotBlank(daValidare)) {
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
		    String nomeParametro = paramNames.nextElement();
		    if (nomeParametro.startsWith("entity.istanzemappalis[" + daValidare + "]")) {
			String valore = request.getParameter(nomeParametro);
			if (nomeParametro.indexOf("catasto.codice") > 0) {
			    if (StringUtils.isNotBlank(valore)) {
				Catasto catasto = catastoService.findById(valore);
				mappale.setCatasto(catasto);
				isMappale = true;
			    }
			} else if (nomeParametro.indexOf("sezione") > 0) {
			    mappale.setSezione(valore);
			    isMappale = true;
			} else if (nomeParametro.indexOf("foglio") > 0) {
			    mappale.setFoglio(valore);
			    isMappale = true;
			} else if (nomeParametro.indexOf("particella") > 0) {
			    mappale.setParticella(valore);
			    isMappale = true;
			} else if (nomeParametro.indexOf("sub") > 0) {
			    mappale.setSub(valore);
			    isMappale = true;
			} else if (nomeParametro.indexOf("unitaimmob") > 0) {
			    mappale.setUnitaimmob(valore);
			    isMappale = true;
			}
		    }
		}
		if (isMappale) {
		    result.getIstanzemappalis().add(mappale);
		}
	    }
	}
	if (stradariodaDb != null) {
	    result.getStradario().setDescrizione(stradariodaDb.getDescrizione());
	}
	return result;
    }

    private static SitCampiAmmessi getCampoFromNome(String nome) {

	if (nome.startsWith("TIPOCATASTO") || nome.startsWith("SEZIONE") || nome.startsWith("FOGLIO") || nome.startsWith("PARTICELLA")
		|| nome.startsWith("SUB") || nome.startsWith("UNITAIMMOB")) {
	    nome = nome.substring(0, nome.lastIndexOf("_"));
	    if (nome.indexOf("_ID") == -1) {
		nome = nome + "_ID";
	    }
	}
	return SitCampiAmmessi.valueOf(nome);
    }

    private String getParameter(HttpServletRequest request, String name) {

	return request.getParameter("entity." + name);
    }

    @Override
    protected void fixMergeEntityProperty(IstanzestradarioCommand command) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(IstanzestradarioCommand command) {

	if (command.getEntity() != null) {
	    Istanzestradario istanzestradario = command.getEntity();
	    if (EntityUtils.getNestedProperty(istanzestradario, "stradariocolore.id.codicecolore") == null) {
		istanzestradario.setStradariocolore(new Stradariocolore());
	    }
	    if (EntityUtils.getNestedProperty(istanzestradario, "stradario.id.codice") == null) {
		istanzestradario.setStradario(new Stradario());
	    }
	    if (EntityUtils.getNestedProperty(istanzestradario, "istanza.id.codice") == null) {
		istanzestradario.setIstanza(null);
	    }
	    if (EntityUtils.getNestedProperty(istanzestradario, "tipiLocalizzazioni.id.codice") == null) {
		istanzestradario.setTipiLocalizzazioni(new TipiLocalizzazioni());
	    }
	}
    }
}
