/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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
import it.gruppoinit.pal.gp.core.dao.helper.RaggruppamentoRiepiloghiIncassi;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegIoAssegnazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniInOut;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.helper.RegistrazioniFilterMercatoUsoPosteggioComparator;
import it.gruppoinit.pal.gp.core.domain.web.BaseCommand;
import it.gruppoinit.pal.gp.core.domain.web.RegistrazioniInOutCommand;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.RegIoAssegnazioniService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniCausaliService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniImportiService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniInOutService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;

/**
 * @author lucap
 * 
 */
@Controller
@SessionAttributes(value = { "registrazioniInOutCommand", "registrazioniInOut", "registrazioniFilter" })
public class RegistrazioniInOutController extends BaseController<RegistrazioniInOut> {

    private static final Logger log = LoggerFactory.getLogger(RegistrazioniInOutController.class);
    @Autowired
    private RegistrazioniInOutService registrazioniInOutService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private TipimodalitapagamentoService tipimodalitapagamentoService;
    @Autowired
    private RegistrazioniImportiService registrazioniImportiService;
    @Autowired
    private RegIoAssegnazioniService regIoAssegnazioniService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private RegistrazioniCausaliService registrazioniCausaliService;
    @Autowired
    private ContiService contiService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;

    @RequestMapping
    public ModelMap searchScadenze(HttpServletRequest request) {

	// §§§BEGIN§§§
	RegistrazioniInOutCommand command = new RegistrazioniInOutCommand();
	command.setFilter(new RegistrazioniFilter());
	command.setDisplayMode(BaseCommand.SEARCH);
	List<RegistrazioniCausali> registrazioniCausaliList = registrazioniCausaliService.findByAbilitato();
	ModelMap model = new ModelMap();
	model.addAttribute("registrazioniInOutCommand", command);
	model.addAttribute("registrazioniCausaliList", registrazioniCausaliList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<RegistrazioniInOut> registrazioniInOutList = registrazioniInOutService.findAll(null, null);
	ModelMap model = new ModelMap(registrazioniInOutList);
	boolean export = createJMesaExport(request, response, registrazioniInOutList);
	if (export)
	    return null;
	model.addAttribute("registrazioniInOutList", registrazioniInOutList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String searchScadenzeByIncassoOrAnagrafe(Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	RegistrazioniFilter filter = new RegistrazioniFilter();
	RegistrazioniInOutCommand command = new RegistrazioniInOutCommand();
	RegistrazioniInOut registrazioniInOut = null;
	Integer codiceIncasso = null;
	Integer codiceAnagrafe = null;
	Date dataDistintaDate = GregorianCalendar.getInstance().getTime();
	if (request.getParameter("codiceIncasso") != null) {
	    codiceIncasso = Integer.parseInt(request.getParameter("codiceIncasso"));
	    registrazioniInOut = registrazioniInOutService.findById(new PkId(codiceIncasso));
	}
	dataDistintaDate = getDataDistinta();
	Tipimodalitapagamento tipimodalitapagamento = getTipimodalitapagamento();
	Date dataIncassoDate = getDataIncasso();
	boolean visualizzaDettagli = getDettagli();
	registrazioniInOut.setVisualizzaDettagliTransient(visualizzaDettagli);
	if (request.getParameter("codiceAnagrafe") != null) {
	    codiceAnagrafe = Integer.parseInt(request.getParameter("codiceAnagrafe"));
	    registrazioniInOut = new RegistrazioniInOut();
	    Anagrafe anagrafe = anagrafeService.findById(new PkId(codiceAnagrafe));
	    registrazioniInOut.setAnagrafe(anagrafe);
	    registrazioniInOut.setDataDistinta(dataDistintaDate);
	    registrazioniInOut.setTipimodalitapagamento(tipimodalitapagamento);
	    registrazioniInOut.setVisualizzaDettagliTransient(visualizzaDettagli);
	    registrazioniInOut.setDataIncasso(dataIncassoDate);
	}
	command.setEntity(registrazioniInOut);
	filter.setAnagrafe(registrazioniInOut.getAnagrafe());
	// per evitare dopo l'update di ricaricare un entity con rimanenza nulla
	if (registrazioniInOut.getRimanenza().compareTo(new BigDecimal(0)) == 0) {
	    RegistrazioniInOut regio = new RegistrazioniInOut();
	    regio.setDataDistinta(dataDistintaDate);
	    regio.setTipimodalitapagamento(tipimodalitapagamento);
	    regio.setDataIncasso(dataIncassoDate);
	    regio.setVisualizzaDettagliTransient(visualizzaDettagli);
	    command.setEntity(regio);
	    command.getEntity().setAnagrafe(registrazioniInOut.getAnagrafe());
	}
	command.setFilter(filter);
	command.setDisplayMode(BaseCommand.LIST);
	// recupero le scadenze
	List<RegistrazioniImporti> scadenzeList = registrazioniImportiService.findByRegistrazioniFilter(command.getFilter());
	command.setScadenzeList(scadenzeList);
	model.addAttribute("incassiCompleti", scadenzeList);
	// recupero le modalità di pagamento
	List<Tipimodalitapagamento> tipimodalitapagamentoList = tipimodalitapagamentoService.findAll(null, null, false);
	// recupero gli incassi non completamente assegnati
	List<RegistrazioniInOut> regIODaAssegnare = registrazioniInOutService.findRegistrazioniInOutDaAssegnare(filter.getAnagrafe());
	model.addAttribute("regIODaAssegnare", regIODaAssegnare);
	model.addAttribute("tipimodalitapagamentoList", tipimodalitapagamentoList);
	model.addAttribute("registrazioniInOutCommand", command);
	return "registrazioniinout/listScadenzeAnagrafe";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String listScadenze(Model model, @ModelAttribute("registrazioniInOutCommand") RegistrazioniInOutCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	command.setDisplayMode(BaseCommand.LIST);
	command.setEntity(new RegistrazioniInOut());
	// recupero il filtro dalla sessione
	// per evitare oggetti nulli passati dalla jsp controllo l'oggetto filtro
	this.fixFilter(command.getFilter());
	// setto i filtri per il service
	if (command.getFilter().getRegistrazioniCausali().getId().getCodice() != null) {
	    RegistrazioniCausali registrazioniCausali = registrazioniCausaliService.findById(command.getFilter().getRegistrazioniCausali().getId());
	    command.getFilter().setRegistrazioniCausali(registrazioniCausali);
	}
	if (command.getFilter().getConti().getId().getCodice() != null) {
	    Conti conti = contiService.findById(command.getFilter().getConti().getId());
	    command.getFilter().setConti(conti);
	}
	if (command.getFilter().getAnagrafe().getId().getCodice() != null) {
	    Anagrafe anagrafe = anagrafeService.findById(command.getFilter().getAnagrafe().getId());
	    command.getFilter().setAnagrafe(anagrafe);
	}
	if (command.getFilter().getMercati().getId().getCodice() != null) {
	    Mercati mercati = mercatiService.findById(command.getFilter().getMercati().getId());
	    command.getFilter().setMercati(mercati);
	}
	if (command.getFilter().getMercatiUso().getId().getCodice() != null) {
	    MercatiUso uso = mercatiUsoService.findById(command.getFilter().getMercatiUso().getId());
	    command.getFilter().setMercatiUso(uso);
	}
	if (command.getFilter().getPosteggio().getId().getCodice() != null) {
	    MercatiD posteggio = mercatiDService.findById(command.getFilter().getPosteggio().getId());
	    command.getFilter().setPosteggio(posteggio);
	}
	if (command.getFilter().getAlberoproc().getId().getCodice() != null) {
	    Alberoproc alberoproc = alberoprocService.findById(command.getFilter().getAlberoproc().getId());
	    command.getFilter().setAlberoproc(alberoproc);
	}
	if (command.getFilter().getAmministrazioni().getId().getCodice() != null) {
	    Amministrazioni amministrazioni = amministrazioniService.findById(command.getFilter().getAmministrazioni().getId());
	    command.getFilter().setAmministrazioni(amministrazioni);
	}
	// recupero le scadenze
	List<RegistrazioniImporti> scadenzeList = registrazioniImportiService.findScadenzeByRegistrazioniFilter(command.getFilter());
	command.setScadenzeList(scadenzeList);
	boolean export = createJMesaExport(request, response, scadenzeList);
	if (export) {
	    return null;
	}
	RegistrazioniInOut registrazioniInOut = command.getEntity();
	Date dataDistintaDate = getDataDistinta();
	Tipimodalitapagamento tipimodalitapagamento = getTipimodalitapagamento();
	Date dataIncassoDate = getDataIncasso();
	boolean visualizzaDettagli = getDettagli();
	registrazioniInOut.setVisualizzaDettagliTransient(visualizzaDettagli);
	registrazioniInOut.setDataDistinta(dataDistintaDate);
	registrazioniInOut.setTipimodalitapagamento(tipimodalitapagamento);
	registrazioniInOut.setDataIncasso(dataIncassoDate);
	command.setEntity(registrazioniInOut);
	model.addAttribute("registrazioniInOutCommand", command);
	if (command.getFilter().getAnagrafe().getId().getCodice() == null) {
	    return "registrazioniinout/listScadenze";
	}
	// recupero le modalità di pagamento
	List<Tipimodalitapagamento> tipimodalitapagamentoList = tipimodalitapagamentoService.findAll(null, null, false);
	model.addAttribute("tipimodalitapagamentoList", tipimodalitapagamentoList);
	List<RegistrazioniImporti> incassiCompleti = registrazioniImportiService.findByRegistrazioniFilter(command.getFilter());
	model.addAttribute("incassiCompleti", incassiCompleti);
	// recupero gli incassi non completamente assegnati filtrati per anagrafe
	List<RegistrazioniInOut> regIODaAssegnare = registrazioniInOutService.findRegistrazioniInOutDaAssegnare(command.getFilter().getAnagrafe());
	model.addAttribute("regIODaAssegnare", regIODaAssegnare);
	return "registrazioniinout/listScadenzeAnagrafe";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String updateScadenze(Model model, @ModelAttribute("registrazioniInOutCommand") RegistrazioniInOutCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	if (command.getEntity().getDataDistinta() != null) {
	    ConfigurazioneutenteId dataDistintaConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		    WebConstants.CONF_UTENTE_REG_IO_DATADISTINTA);
	    Configurazioneutente tabConf = configurazioneutenteService.findById(dataDistintaConfId);
	    SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    String dataDistintaString = dateFormat.format(command.getEntity().getDataDistinta());
	    if (tabConf == null) {
		// .. inserisco i valori di default
		tabConf = new Configurazioneutente();
		tabConf.setId(dataDistintaConfId);
		tabConf.setResponsabile(responsabile);
		tabConf.setValore(dataDistintaString);
		configurazioneutenteService.insert(tabConf);
	    } else {
		tabConf.setValore(dataDistintaString);
		configurazioneutenteService.update(tabConf);
	    }
	}
	if (command.getEntity().getDataIncasso() != null) {
	    ConfigurazioneutenteId dataIncassoConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		    WebConstants.CONF_UTENTE_REG_IO_DATA_INCASSO);
	    Configurazioneutente dataIncassoTabConf = configurazioneutenteService.findById(dataIncassoConfId);
	    SimpleDateFormat dateFormatIncasso = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    String dataIncassoString = dateFormatIncasso.format(command.getEntity().getDataIncasso());
	    if (dataIncassoTabConf == null) {
		// .. inserisco i valori di default
		dataIncassoTabConf = new Configurazioneutente();
		dataIncassoTabConf.setId(dataIncassoConfId);
		dataIncassoTabConf.setResponsabile(responsabile);
		dataIncassoTabConf.setValore(dataIncassoString);
		configurazioneutenteService.insert(dataIncassoTabConf);
	    } else {
		dataIncassoTabConf.setValore(dataIncassoString);
		configurazioneutenteService.update(dataIncassoTabConf);
	    }
	}
	if (command.getEntity().getTipimodalitapagamento() != null && command.getEntity().getTipimodalitapagamento().getId().getCodice() != null) {
	    ConfigurazioneutenteId modalitaPagamentoConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		    WebConstants.CONF_UTENTE_REG_IO_MODALITA_PAGAMENTO);
	    Configurazioneutente modalitaPagamentoTabConf = configurazioneutenteService.findById(modalitaPagamentoConfId);
	    Tipimodalitapagamento tipimodalitapagamento = command.getEntity().getTipimodalitapagamento();
	    String tipimodalitapagamentoId = tipimodalitapagamento.getId().getCodice().toString();
	    if (modalitaPagamentoTabConf == null) {
		// .. inserisco i valori di default
		modalitaPagamentoTabConf = new Configurazioneutente();
		modalitaPagamentoTabConf.setId(modalitaPagamentoConfId);
		modalitaPagamentoTabConf.setResponsabile(responsabile);
		modalitaPagamentoTabConf.setValore(tipimodalitapagamentoId);
		configurazioneutenteService.insert(modalitaPagamentoTabConf);
	    } else {
		modalitaPagamentoTabConf.setValore(tipimodalitapagamentoId);
		configurazioneutenteService.update(modalitaPagamentoTabConf);
	    }
	}
	String dettagliId = String.valueOf(command.getEntity().isVisualizzaDettagliTransient());
	ConfigurazioneutenteId dettagliConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_VISUALIZZA_DETTAGLI);
	Configurazioneutente dettagliConf = configurazioneutenteService.findById(dettagliConfId);
	if (dettagliConf == null) {
	    // .. inserisco i valori di default
	    dettagliConf = new Configurazioneutente();
	    dettagliConf.setId(dettagliConfId);
	    dettagliConf.setResponsabile(responsabile);
	    dettagliConf.setValore(dettagliId);
	    configurazioneutenteService.insert(dettagliConf);
	} else {
	    dettagliConf.setValore(dettagliId);
	    configurazioneutenteService.update(dettagliConf);
	}
	command.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	if (command.getFilter().getAnagrafe().getId().getCodice() != null) {
	    command.getEntity().setAnagrafe(command.getFilter().getAnagrafe());
	}
	fixMergeEntityProperty(command.getEntity());
	try {
	    // validazione
	    if (command.getEntity().getImporto() == null || (command.getEntity().getImporto().compareTo(new BigDecimal(0)) <= 0)) {
		throw new RuntimeException("Campo importo obbligatorio");
	    }
	    if (command.getEntity().getDataIncasso() == null) {
		throw new RuntimeException("Campo Data incasso obbligatorio");
	    }
	    BigDecimal rimanenzaTOT = command.getEntity().getRimanenza();
	    BigDecimal sommaDaAssegnareTOT = new BigDecimal(0);
	    for (RegistrazioniImporti regI : command.getScadenzeList()) {
		if (regI.getTransientSommaDaAssegnare() != null) {
		    BigDecimal rimanenzaI = regI.getRimanenza();
		    sommaDaAssegnareTOT = sommaDaAssegnareTOT.add(regI.getTransientSommaDaAssegnare());
		    if (rimanenzaI.compareTo(regI.getTransientSommaDaAssegnare()) < 0) {
			throw new RuntimeException("Errore nei valori da assegnare inseriti. Alcuni valori superano la somma da incassare");
		    }
		}
	    }
	    if (rimanenzaTOT.compareTo(sommaDaAssegnareTOT) < 0) {
		throw new RuntimeException("Errore nei valori da assegnare inseriti. La loro somma supera il valore incassabile");
	    }
	    // aggiorno le scadenza
	    registrazioniInOutService.updateScadenze(command);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, command, e);
	    List<RegistrazioniInOut> regIODaAssegnare = registrazioniInOutService
		    .findRegistrazioniInOutDaAssegnare(command.getFilter().getAnagrafe());
	    List<Tipimodalitapagamento> tipimodalitapagamentoList = tipimodalitapagamentoService.findAll(null, null, false);
	    model.addAttribute("registrazioniInOutCommand", command);
	    model.addAttribute("regIODaAssegnare", regIODaAssegnare);
	    model.addAttribute("tipimodalitapagamentoList", tipimodalitapagamentoList);
	    return "registrazioniinout/listScadenzeAnagrafe";
	}
	return "redirect:listScadenze.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String ajaxDettaglioRegistrazioniIO(@RequestParam("codiceRegIO") Integer codiceRegIO, Model model,
	    @ModelAttribute("registrazioniInOutCommand") RegistrazioniInOutCommand command, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	PkId id = new PkId(codiceRegIO);
	RegistrazioniInOut registrazioniInOut = registrazioniInOutService.findById(id);
	if (registrazioniInOut == null) {
	    registrazioniInOut = new RegistrazioniInOut();
	    Date dataDistintaDate = getDataDistinta();
	    Tipimodalitapagamento tipimodalitapagamento = getTipimodalitapagamento();
	    Date dataIncassoDate = getDataIncasso();
	    boolean visualizzaDettagli = getDettagli();
	    registrazioniInOut.setVisualizzaDettagliTransient(visualizzaDettagli);
	    registrazioniInOut.setTipimodalitapagamento(tipimodalitapagamento);
	    registrazioniInOut.setDataIncasso(dataIncassoDate);
	    registrazioniInOut.setDataDistinta(dataDistintaDate);
	    command.setEntity(registrazioniInOut);
	} else {
	    command.setEntity(registrazioniInOut);
	}
	// recupero le modalità di pagamento
	List<Tipimodalitapagamento> tipimodalitapagamentoList = tipimodalitapagamentoService.findAll(null, null, false);
	model.addAttribute("tipimodalitapagamentoList", tipimodalitapagamentoList);
	model.addAttribute("entity", command.getEntity());
	if (log.isDebugEnabled())
	    log.debug("call dettaglioRegistrazioniIO with codiceRegIO: " + codiceRegIO);
	return "ajax/dettaglioRegistrazioniIO";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("registrazioniInOut") RegistrazioniInOut registrazioniInOut, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	Software software = softwareService.findById(ORMHelper.getSoftware());
	registrazioniInOut.setSoftware(software);
	try {
	    registrazioniInOutService.delete(registrazioniInOut);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioniInOut, e);
	    log.error(e.getMessage());
	    fixRenderEntityProperty(registrazioniInOut);
	    // model.addAttribute(registrazioniInOut);
	    return "registrazioniinout/form";
	}
	status.setComplete();
	return "redirect:list.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@ModelAttribute("registrazioniInOut") RegistrazioniInOut registrazioniInOut, BindingResult result, SessionStatus status,
	    Model model) {

	// §§§BEGIN§§§
	// aggiorna o inserisce la data distinta nella tabella configurazione utente
	// quando creo un nuovo incasso
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId dataDistintaConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_DATADISTINTA);
	Configurazioneutente tabConf = configurazioneutenteService.findById(dataDistintaConfId);
	SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String dataDistintaString = dateFormat.format(registrazioniInOut.getDataDistinta());
	if (tabConf == null) {
	    // .. inserisco i valori di default
	    tabConf = new Configurazioneutente();
	    tabConf.setId(dataDistintaConfId);
	    tabConf.setResponsabile(responsabile);
	    tabConf.setValore(dataDistintaString);
	    configurazioneutenteService.insert(tabConf);
	} else {
	    tabConf.setValore(dataDistintaString);
	    configurazioneutenteService.update(tabConf);
	}
	Software software = softwareService.findById(ORMHelper.getSoftware());
	registrazioniInOut.setSoftware(software);
	fixMergeEntityProperty(registrazioniInOut);
	try {
	    registrazioniInOutService.insert(registrazioniInOut);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioniInOut, e);
	    if (registrazioniInOut.getAnagrafe() != null) {
		Anagrafe anagrafe = anagrafeService.findById(registrazioniInOut.getAnagrafe().getId());
		registrazioniInOut.setAnagrafe(anagrafe);
	    }
	    List<Tipimodalitapagamento> tipimodalitapagamentoList = tipimodalitapagamentoService.findAll(null, null, false);
	    model.addAttribute("tipimodalitapagamentoList", tipimodalitapagamentoList);
	    fixRenderEntityProperty(registrazioniInOut);
	    return "registrazioniinout/form";
	}
	// /INSERIMENTO Modalità Pagamento nella configurazione utente
	ConfigurazioneutenteId modalitaPagamentoConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_MODALITA_PAGAMENTO);
	Configurazioneutente modalitaPagamentoTabConf = configurazioneutenteService.findById(modalitaPagamentoConfId);
	Tipimodalitapagamento tipimodalitapagamento = registrazioniInOut.getTipimodalitapagamento();
	String tipimodalitapagamentoId = tipimodalitapagamento.getId().getCodice().toString();
	if (modalitaPagamentoTabConf == null) {
	    // .. inserisco i valori di default
	    modalitaPagamentoTabConf = new Configurazioneutente();
	    modalitaPagamentoTabConf.setId(modalitaPagamentoConfId);
	    modalitaPagamentoTabConf.setResponsabile(responsabile);
	    modalitaPagamentoTabConf.setValore(tipimodalitapagamentoId);
	    configurazioneutenteService.insert(modalitaPagamentoTabConf);
	} else {
	    modalitaPagamentoTabConf.setValore(tipimodalitapagamentoId);
	    configurazioneutenteService.update(modalitaPagamentoTabConf);
	}
	// /INSERIMENTO Data Incasso nella configurazione utente
	ConfigurazioneutenteId dataIncassoConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_DATA_INCASSO);
	Configurazioneutente dataIncassoTabConf = configurazioneutenteService.findById(dataIncassoConfId);
	SimpleDateFormat dateFormatIncasso = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String dataIncassoString = dateFormatIncasso.format(registrazioniInOut.getDataIncasso());
	if (dataIncassoTabConf == null) {
	    // .. inserisco i valori di default
	    dataIncassoTabConf = new Configurazioneutente();
	    dataIncassoTabConf.setId(dataIncassoConfId);
	    dataIncassoTabConf.setResponsabile(responsabile);
	    dataIncassoTabConf.setValore(dataIncassoString);
	    configurazioneutenteService.insert(dataIncassoTabConf);
	} else {
	    dataIncassoTabConf.setValore(dataIncassoString);
	    configurazioneutenteService.update(dataIncassoTabConf);
	}
	// VISUALIZZAZIONE DETTAGLI : INSERIMENTO IN CONFIGURAZIONE UTENTE
	String dettagliId = String.valueOf(registrazioniInOut.isVisualizzaDettagliTransient());
	ConfigurazioneutenteId dettagliConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_VISUALIZZA_DETTAGLI);
	Configurazioneutente dettagliConf = configurazioneutenteService.findById(dettagliConfId);
	if (dettagliConf == null) {
	    // .. inserisco i valori di default
	    dettagliConf = new Configurazioneutente();
	    dettagliConf.setId(dettagliConfId);
	    dettagliConf.setResponsabile(responsabile);
	    dettagliConf.setValore(dettagliId);
	    configurazioneutenteService.insert(dettagliConf);
	} else {
	    dettagliConf.setValore(dettagliId);
	    configurazioneutenteService.update(dettagliConf);
	}
	setPageAttributes(model);
	status.setComplete();
	return "redirect:view.htm?codice=" + registrazioniInOut.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("registrazioniInOut") RegistrazioniInOut registrazioniInOut, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	Software software = softwareService.findById(ORMHelper.getSoftware());
	registrazioniInOut.setSoftware(software);
	fixMergeEntityProperty(registrazioniInOut);
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	if (registrazioniInOut.getDataDistinta() != null) {
	    ConfigurazioneutenteId dataDistintaConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		    WebConstants.CONF_UTENTE_REG_IO_DATADISTINTA);
	    Configurazioneutente tabConf = configurazioneutenteService.findById(dataDistintaConfId);
	    SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    String dataDistintaString = dateFormat.format(registrazioniInOut.getDataDistinta());
	    if (tabConf == null) {
		// .. inserisco i valori di default
		tabConf = new Configurazioneutente();
		tabConf.setId(dataDistintaConfId);
		tabConf.setResponsabile(responsabile);
		tabConf.setValore(dataDistintaString);
		configurazioneutenteService.insert(tabConf);
	    } else {
		tabConf.setValore(dataDistintaString);
		configurazioneutenteService.update(tabConf);
	    }
	}
	if (registrazioniInOut.getDataIncasso() != null) {
	    ConfigurazioneutenteId dataIncassoConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		    WebConstants.CONF_UTENTE_REG_IO_DATA_INCASSO);
	    Configurazioneutente dataIncassoTabConf = configurazioneutenteService.findById(dataIncassoConfId);
	    SimpleDateFormat dateFormatIncasso = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    String dataIncassoString = dateFormatIncasso.format(registrazioniInOut.getDataIncasso());
	    if (dataIncassoTabConf == null) {
		// .. inserisco i valori di default
		dataIncassoTabConf = new Configurazioneutente();
		dataIncassoTabConf.setId(dataIncassoConfId);
		dataIncassoTabConf.setResponsabile(responsabile);
		dataIncassoTabConf.setValore(dataIncassoString);
		configurazioneutenteService.insert(dataIncassoTabConf);
	    } else {
		dataIncassoTabConf.setValore(dataIncassoString);
		configurazioneutenteService.update(dataIncassoTabConf);
	    }
	}
	if (registrazioniInOut.getTipimodalitapagamento() != null && registrazioniInOut.getTipimodalitapagamento().getId().getCodice() != null) {
	    ConfigurazioneutenteId modalitaPagamentoConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		    WebConstants.CONF_UTENTE_REG_IO_MODALITA_PAGAMENTO);
	    Configurazioneutente modalitaPagamentoTabConf = configurazioneutenteService.findById(modalitaPagamentoConfId);
	    Tipimodalitapagamento tipimodalitapagamento = registrazioniInOut.getTipimodalitapagamento();
	    String tipimodalitapagamentoId = tipimodalitapagamento.getId().getCodice().toString();
	    if (modalitaPagamentoTabConf == null) {
		// .. inserisco i valori di default
		modalitaPagamentoTabConf = new Configurazioneutente();
		modalitaPagamentoTabConf.setId(modalitaPagamentoConfId);
		modalitaPagamentoTabConf.setResponsabile(responsabile);
		modalitaPagamentoTabConf.setValore(tipimodalitapagamentoId);
		configurazioneutenteService.insert(modalitaPagamentoTabConf);
	    } else {
		modalitaPagamentoTabConf.setValore(tipimodalitapagamentoId);
		configurazioneutenteService.update(modalitaPagamentoTabConf);
	    }
	}
	String dettagliId = String.valueOf(registrazioniInOut.isVisualizzaDettagliTransient());
	ConfigurazioneutenteId dettagliConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_VISUALIZZA_DETTAGLI);
	Configurazioneutente dettagliConf = configurazioneutenteService.findById(dettagliConfId);
	if (dettagliConf == null) {
	    // .. inserisco i valori di default
	    dettagliConf = new Configurazioneutente();
	    dettagliConf.setId(dettagliConfId);
	    dettagliConf.setResponsabile(responsabile);
	    dettagliConf.setValore(dettagliId);
	    configurazioneutenteService.insert(dettagliConf);
	} else {
	    dettagliConf.setValore(dettagliId);
	    configurazioneutenteService.update(dettagliConf);
	}
	try {
	    registrazioniInOutService.update(registrazioniInOut);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioniInOut, e);
	    fixRenderEntityProperty(registrazioniInOut);
	    // Ricerco tutti i tipimodalitapagamento
	    List<Tipimodalitapagamento> tipimodalitapagamentoList = tipimodalitapagamentoService.findAll(null, null, false);
	    model.addAttribute("tipimodalitapagamentoList", tipimodalitapagamentoList);
	    // model.addAttribute(registrazioniInOut);
	    return "registrazioniinout/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + registrazioniInOut.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	// Ricerco tutti i tipimodalitapagamento per il CheckBox
	List<Tipimodalitapagamento> tipimodalitapagamentoList = tipimodalitapagamentoService.findAll(null, null, false);
	RegistrazioniInOut registrazioniInOut = new RegistrazioniInOut();
	Date datadistinta = getDataDistinta();
	Tipimodalitapagamento tipimodalitapagamento = getTipimodalitapagamento();
	Date dataIncassoDate = getDataIncasso();
	boolean visualizzaDettagli = getDettagli();
	registrazioniInOut.setVisualizzaDettagliTransient(visualizzaDettagli);
	if (datadistinta == null) {
	    registrazioniInOut.setDataDistinta(GregorianCalendar.getInstance().getTime());
	} else {
	    registrazioniInOut.setDataDistinta(datadistinta);
	}
	if (dataIncassoDate == null) {
	    registrazioniInOut.setDataIncasso(GregorianCalendar.getInstance().getTime());
	} else {
	    registrazioniInOut.setDataIncasso(dataIncassoDate);
	}
	if (tipimodalitapagamento == null || tipimodalitapagamento.getId().getCodice() == null) {
	    registrazioniInOut.setTipimodalitapagamento(new Tipimodalitapagamento());
	} else {
	    registrazioniInOut.setTipimodalitapagamento(tipimodalitapagamento);
	}
	registrazioniInOut.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(registrazioniInOut);
	model.addAttribute("registrazioniInOut", registrazioniInOut);
	model.addAttribute("tipimodalitapagamentoList", tipimodalitapagamentoList);
	setPageAttributes(model);
	return "registrazioniinout/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	// Ricerco tutti i tipimodalitapagamento
	List<Tipimodalitapagamento> tipimodalitapagamentoList = tipimodalitapagamentoService.findAll(null, null, false);
	// Ricavo la RegistrazioneInOut
	PkId id = new PkId(codice);
	RegistrazioniInOut registrazioniInOut = registrazioniInOutService.findById(id);
	// Ricavo la lista di Registrazioni per quella determinata anagrafica (Per la list annidata)
	// List<Registrazioni> registrazioniList =
	// registrazioniService.findByAnagrafe(registrazioniInOut.getAnagrafe());
	fixRenderEntityProperty(registrazioniInOut);
	registrazioniInOut.setVisualizzaDettagliTransient(getDettagli());
	model.addAttribute("registrazioniInOut", registrazioniInOut);
	// model.addAttribute("registrazioniList", registrazioniList);
	model.addAttribute("tipimodalitapagamentoList", tipimodalitapagamentoList);
	setPageAttributes(model);
	return "registrazioniinout/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping()
    public String createStampaIVA(Model model) {

	// §§§BEGIN§§§
	List<RegistrazioniCausali> registrazioniCausaliList = registrazioniCausaliService.findAll(null, null);
	model.addAttribute("registrazioniCausaliList", registrazioniCausaliList);
	RegistrazioniFilter registrazioniFilter = new RegistrazioniFilter();
	model.addAttribute("registrazioniFilter", registrazioniFilter);
	return "registrazioniinout/createStampaIva";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping()
    public String stampaiva(Model model, @ModelAttribute("registrazioniFilter") RegistrazioniFilter registrazioniFilter, HttpServletRequest request,
	    HttpServletResponse response) {

	// §§§BEGIN§§§
	List<RegistrazioniFilter> registrazioniFilterList = registrazioniInOutService.findByDataAndAnagrafeAndMercato(registrazioniFilter);
	if (registrazioniFilter.getRaggruppamentoRiepiloghiIncassi() == RaggruppamentoRiepiloghiIncassi.ANAGRAFE_MERCATO_POSTEGGIO
		|| registrazioniFilter.getRaggruppamentoRiepiloghiIncassi() == RaggruppamentoRiepiloghiIncassi.NESSUN_RAGGRUPPAMENTO)
	    Collections.sort(registrazioniFilterList, new RegistrazioniFilterMercatoUsoPosteggioComparator());
	boolean export = createJMesaExport(request, response, registrazioniFilterList);
	if (export)
	    return null;
	if (registrazioniFilter.getMercatiUso().getId().getCodice() != null) {
	    MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(registrazioniFilter.getMercatiUso().getId().getCodice()));
	    registrazioniFilter.setMercatiUso(mercatiUso);
	}
	if (registrazioniFilter.getAnagrafe().getId().getCodice() != null) {
	    Anagrafe anagrafe = anagrafeService.findById(new PkId(registrazioniFilter.getAnagrafe().getId().getCodice()));
	    registrazioniFilter.setAnagrafe(anagrafe);
	}
	model.addAttribute("datainizio", registrazioniFilter.getDataInizio());
	model.addAttribute("datafine", registrazioniFilter.getDataFine());
	model.addAttribute("registrazioniFilter", registrazioniFilter);
	model.addAttribute("registrazioniFilterList", registrazioniFilterList);
	return "registrazioniinout/stampaiva";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String deleteAssegnazioni(@RequestParam("codice") Integer codiceRegIO, Model model,
	    @ModelAttribute("registrazioniInOut") RegistrazioniInOut registrazioniInOut, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	RegistrazioniInOut registrazioniInOut2 = registrazioniInOutService.findById(new PkId(codiceRegIO));
	Set<RegIoAssegnazioni> regIoAssegnazioniList = registrazioniInOut2.getRegIoAssegnazionis();
	try {
	    regIoAssegnazioniService.resetAssegnazioni(regIoAssegnazioniList);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioniInOut, e);
	    log.error(e.getMessage());
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("codice", codiceRegIO.toString());
	    model.addAttribute("commandName", "registrazioniInOut");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	return "redirect:view.htm?codice=" + registrazioniInOut2.getId().getCodice() + "&deleteAssegnazioni=ok";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String deleteAssegnazione(@RequestParam("codice") Integer codiceRegIOAss, Model model,
	    @ModelAttribute("registrazioniInOutCommand") RegistrazioniInOutCommand registrazioniInOutCommand, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	RegIoAssegnazioni regIoAssegnazioni = regIoAssegnazioniService.findById(new PkId(codiceRegIOAss));
	try {
	    regIoAssegnazioniService.delete(regIoAssegnazioni);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, regIoAssegnazioni, e);
	    log.error(e.getMessage());
	    Map<String, String> map = new HashMap<String, String>();
	    model.addAttribute("commandName", "registrazioniInOutCommand");
	    model.addAttribute("method", "listScadenze.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	return "redirect:listScadenze.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(RegistrazioniInOut entity) {

	// §§§BEGIN§§§
	if (entity.getAnagrafe() != null && entity.getAnagrafe().getId() != null && entity.getAnagrafe().getId().getCodice() == null) {
	    entity.setAnagrafe(null);
	}
	if (entity.getTipimodalitapagamento() != null && entity.getTipimodalitapagamento().getId() != null
		&& entity.getTipimodalitapagamento().getId().getCodice() == null) {
	    entity.setTipimodalitapagamento(null);
	}
	if (entity.getAmministrazioni() != null && entity.getAmministrazioni().getId() != null
		&& entity.getAmministrazioni().getId().getCodice() == null) {
	    entity.setAmministrazioni(null);
	}
	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null) || entity.getSoftware().getCodice().equals("")) {
	    entity.setSoftware(null);
	}
	// §§§END§§§
    }

    protected void fixRenderEntityProperty(RegistrazioniInOut entity) {

	// §§§BEGIN§§§
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	if (entity.getAnagrafe() == null) {
	    entity.setAnagrafe(new Anagrafe());
	}
	if (entity.getTipimodalitapagamento() == null) {
	    entity.setTipimodalitapagamento(new Tipimodalitapagamento());
	}
	if (entity.getAmministrazioni() == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
	// §§§END§§§
    }

    protected void fixFilter(RegistrazioniFilter filter) {

	// §§§BEGIN§§§
	if (filter.getRegistrazioniCausali() == null) {
	    filter.setRegistrazioniCausali(new RegistrazioniCausali());
	}
	if (filter.getConti() == null) {
	    filter.setConti(new Conti());
	}
	if (filter.getAnagrafe() == null) {
	    filter.setAnagrafe(new Anagrafe());
	}
	if (filter.getMercati() == null) {
	    filter.setMercati(new Mercati());
	}
	if (filter.getMercatiUso() == null) {
	    filter.setMercatiUso(new MercatiUso());
	}
	if (filter.getAlberoproc() == null) {
	    filter.setAlberoproc(new Alberoproc());
	}
	if (filter.getAmministrazioni() == null) {
	    filter.setAmministrazioni(new Amministrazioni());
	}
	// §§§END§§§
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    private Date getDataDistinta() {

	// §§§BEGIN§§§	
	Date dataDistintaDate = GregorianCalendar.getInstance().getTime();
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId dataDistintaConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_DATADISTINTA);
	Configurazioneutente dataDistintaConf = configurazioneutenteService.findById(dataDistintaConfId);
	if (dataDistintaConf != null) {
	    String dataDistinta = dataDistintaConf.getValore();
	    SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    try {
		dataDistintaDate = sdf.parse(dataDistinta);
	    } catch (ParseException e) {
	    }
	}
	return dataDistintaDate;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private Tipimodalitapagamento getTipimodalitapagamento() {

	// §§§BEGIN§§§
	Tipimodalitapagamento tipimodalitapagamento = new Tipimodalitapagamento();
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId modalitapagamentoConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_MODALITA_PAGAMENTO);
	Configurazioneutente modalitapagamentoConf = configurazioneutenteService.findById(modalitapagamentoConfId);
	if (modalitapagamentoConf != null) {
	    tipimodalitapagamento = tipimodalitapagamentoService.findById(new PkId(Integer.valueOf(modalitapagamentoConf.getValore())));
	}
	return tipimodalitapagamento;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private Date getDataIncasso() {

	// §§§BEGIN§§§
	Date dataIncassoDate = GregorianCalendar.getInstance().getTime();
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId dataIncassoConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_DATA_INCASSO);
	Configurazioneutente dataIncassoConf = configurazioneutenteService.findById(dataIncassoConfId);
	if (dataIncassoConf != null) {
	    String dataIncasso = dataIncassoConf.getValore();
	    SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    try {
		dataIncassoDate = sdf.parse(dataIncasso);
	    } catch (ParseException e) {
	    }
	}
	return dataIncassoDate;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private boolean getDettagli() {

	boolean visualizzaDettagli = false;
	// §§§BEGIN§§§
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId dettagliConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_VISUALIZZA_DETTAGLI);
	Configurazioneutente dettagliConf = configurazioneutenteService.findById(dettagliConfId);
	if (dettagliConf != null) {
	    visualizzaDettagli = Boolean.parseBoolean(dettagliConf.getValore());
	}
	// §§§END§§§
	return visualizzaDettagli;
    }

    @RequestMapping
    public String exportStampaIVA(@ModelAttribute("registrazioniFilter") RegistrazioniFilter registrazioniFilter, BindingResult result,
	    SessionStatus status, Model model, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	// /Lista filtrata della funzionalità stampa iva////
	List<RegistrazioniFilter> registrazioniFilterList = registrazioniInOutService.findByDataAndAnagrafeAndMercato(registrazioniFilter);
	if (registrazioniFilter.getRaggruppamentoRiepiloghiIncassi() == RaggruppamentoRiepiloghiIncassi.ANAGRAFE_MERCATO_POSTEGGIO
		|| registrazioniFilter.getRaggruppamentoRiepiloghiIncassi() == RaggruppamentoRiepiloghiIncassi.NESSUN_RAGGRUPPAMENTO)
	    Collections.sort(registrazioniFilterList, new RegistrazioniFilterMercatoUsoPosteggioComparator());
	if (registrazioniFilter.getMercatiUso().getId().getCodice() != null) {
	    MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(registrazioniFilter.getMercatiUso().getId().getCodice()));
	    registrazioniFilter.setMercatiUso(mercatiUso);
	}
	if (registrazioniFilter.getAnagrafe().getId().getCodice() != null) {
	    Anagrafe anagrafe = anagrafeService.findById(new PkId(registrazioniFilter.getAnagrafe().getId().getCodice()));
	    registrazioniFilter.setAnagrafe(anagrafe);
	}
	model.addAttribute("datainizio", registrazioniFilter.getDataInizio());
	model.addAttribute("datafine", registrazioniFilter.getDataFine());
	model.addAttribute("registrazioniFilter", registrazioniFilter);
	model.addAttribute("registrazioniFilterList", registrazioniFilterList);
	// ////////////////////
	// /////FILE EXCEL/////////
	HSSFWorkbook workbook = new HSSFWorkbook();
	try {
	    workbook = createExportExcel(registrazioniFilterList, registrazioniFilter.getRaggruppamentoRiepiloghiIncassi(), request);
	} catch (Exception e) {
	    log.error(e.getMessage());
	    result.reject("", e.getMessage());
	    Map<String, String> map = new HashMap<String, String>();
	    model.addAttribute("commandName", "registrazioniFilter");
	    model.addAttribute("method", "stampaiva.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	// ///////////////////////
	response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	String encoding = "UTF-8";
	String fn = "";
	try {
	    fn = new String("stampa_iva_pivot.xls".getBytes(encoding), encoding);
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	response.setHeader("Content-Disposition", "attachment;filename=\"" + fn + "\"");
	response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	response.setHeader("Pragma", "public");
	response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
	try {
	    OutputStream outputStream = response.getOutputStream();
	    workbook.write(outputStream);
	    outputStream.close();
	    outputStream.flush();
	    response.flushBuffer();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * Metodo per l'export di un file excel con pivot.
     * 
     * @param registrazioniFilterList
     * @param raggruppamentoRiepiloghiIncassi
     * @param request
     * @param request
     * @return
     */
    private HSSFWorkbook createExportExcel(List<RegistrazioniFilter> registrazioniFilterList,
	    RaggruppamentoRiepiloghiIncassi raggruppamentoRiepiloghiIncassi, HttpServletRequest request) {

	// §§§BEGIN§§§
	// CREO EXPORT IN EXCEL
	HSSFWorkbook workbook = null;
	// RECUPERO FILE
	InputStream inp = request.getSession().getServletContext().getResourceAsStream("/WEB-INF/export/stampa_iva_pivot.xls");
	//	DefaultResourceLoader resourceLoader = new DefaultResourceLoader();	
	//	Resource resource = resourceLoader.getResource();
	try {
	    //	    if (resource.exists()) {
	    //		inp = resource.getInputStream();
	    //	    } else {
	    //		throw new RuntimeException("Il file non esiste.");
	    //	    }
	    workbook = new HSSFWorkbook(new POIFSFileSystem(inp));
	    // renderer body
	    Collection<RegistrazioniFilter> items = registrazioniFilterList;
	    if (raggruppamentoRiepiloghiIncassi.equals(RaggruppamentoRiepiloghiIncassi.ANAGRAFE_MERCATO_POSTEGGIO)) {
		// RECUPERO GLI SHEETS PER ANAGRAFE, MERCATO, POSTEGGIO
		HSSFSheet sheet = workbook.getSheetAt(0);
		// ELIMINO GLI SHEETS NON DI INTERESSE
		workbook.removeSheetAt(2);
		workbook.removeSheetAt(2);
		workbook.removeSheetAt(2);
		workbook.removeSheetAt(2);
		workbook.removeSheetAt(2);
		workbook.removeSheetAt(2);
		workbook.removeSheetAt(2);
		workbook.removeSheetAt(2);
		// ////////////////////////////////////
		int rowcount = 1;
		for (RegistrazioniFilter item : items) {
		    HSSFRow r = sheet.createRow(rowcount++);
		    // NOMINATIVO
		    HSSFCell cellID = r.createCell(0);
		    cellID.setCellValue(new HSSFRichTextString(item.getAnagrafe().getDescrizioneRichiedente()));
		    // MERCATO
		    HSSFCell cellMERCATO = r.createCell(1);
		    cellMERCATO.setCellValue(new HSSFRichTextString(item.getMercati().getDescrizione()));
		    // USO
		    HSSFCell cellUSO = r.createCell(2);
		    cellUSO.setCellValue(new HSSFRichTextString(item.getMercatiUso().getDescrizione()));
		    // POSTEGGIO
		    HSSFCell cellPOSTEGGIO = r.createCell(3);
		    cellPOSTEGGIO.setCellValue(new HSSFRichTextString(item.getPosteggio().getCodiceposteggio()));
		    // IMPORTO
		    HSSFCell cellIMPORTO = r.createCell(4);
		    cellIMPORTO.setCellValue(item.getImporto().doubleValue());
		    // CONTO
		    HSSFCell cellCONTO = r.createCell(5);
		    cellCONTO.setCellValue(new HSSFRichTextString(item.getConti().getDescrizione()));
		}
		// Auto dimimensiona la larghezza delle colonne
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
	    }
	    if (raggruppamentoRiepiloghiIncassi.equals(RaggruppamentoRiepiloghiIncassi.CONTI)) {
		// RECUPERO GLI SHEETS PER CONTO
		HSSFSheet sheet = workbook.getSheetAt(2);
		// ELIMINO GLI SHEETS NON DI INTERESSE
		workbook.removeSheetAt(0);
		workbook.removeSheetAt(0);
		workbook.removeSheetAt(2);
		workbook.removeSheetAt(2);
		workbook.removeSheetAt(2);
		workbook.removeSheetAt(2);
		workbook.removeSheetAt(2);
		workbook.removeSheetAt(2);
		// ////////////////////////////////////
		int rowcount = 1;
		for (RegistrazioniFilter item : items) {
		    HSSFRow r = sheet.createRow(rowcount++);
		    // CONTO
		    HSSFCell cellCONTO = r.createCell(0);
		    cellCONTO.setCellValue(new HSSFRichTextString(item.getConti().getDescrizione()));
		    // IMPORTO
		    HSSFCell cellIMPORTO = r.createCell(1);
		    cellIMPORTO.setCellValue(item.getImporto().doubleValue());
		}
		// Auto dimimensiona la larghezza delle colonne
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
	    }
	    if (raggruppamentoRiepiloghiIncassi.equals(RaggruppamentoRiepiloghiIncassi.DATADISTINTA_CONTO)) {
		// RECUPERO GLI SHEETS PER DATA DISTINTA E CONTO
		HSSFSheet sheet = workbook.getSheetAt(4);
		// ELIMINO GLI SHEETS NON DI INTERESSE
		workbook.removeSheetAt(0);
		workbook.removeSheetAt(0);
		workbook.removeSheetAt(0);
		workbook.removeSheetAt(0);
		workbook.removeSheetAt(2);
		workbook.removeSheetAt(2);
		workbook.removeSheetAt(2);
		workbook.removeSheetAt(2);
		// ////////////////////////////////////
		int rowcount = 1;
		for (RegistrazioniFilter item : items) {
		    HSSFRow r = sheet.createRow(rowcount++);
		    // DATA DISTINTA
		    HSSFCell cellDADATA = r.createCell(0);
		    String outdateDallaData = "";
		    if (item.getDataDistinta() != null) {
			DateFormat myDateFormatOut = new SimpleDateFormat("dd/MM/yyyy");
			outdateDallaData = myDateFormatOut.format(item.getDataDistinta());
		    }
		    cellDADATA.setCellValue(new HSSFRichTextString(outdateDallaData));
		    // CONTO
		    HSSFCell cellCONTO = r.createCell(1);
		    cellCONTO.setCellValue(new HSSFRichTextString(item.getConti().getDescrizione()));
		    // IMPORTO
		    HSSFCell cellIMPORTO = r.createCell(2);
		    cellIMPORTO.setCellValue(item.getImporto().doubleValue());
		}
		// Auto dimimensiona la larghezza delle colonne
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
	    }
	    if (raggruppamentoRiepiloghiIncassi.equals(RaggruppamentoRiepiloghiIncassi.NESSUN_RAGGRUPPAMENTO)) {
		// RECUPERO GLI SHEETS PER ANAGRAFE, MERCATO, USO, POSTEGGIO, DATA DISTINTA, CONTO E IMPORTO
		HSSFSheet sheet = workbook.getSheetAt(6);
		// ELIMINO GLI SHEETS NON DI INTERESSE
		workbook.removeSheetAt(0);
		workbook.removeSheetAt(0);
		workbook.removeSheetAt(0);
		workbook.removeSheetAt(0);
		workbook.removeSheetAt(0);
		workbook.removeSheetAt(0);
		// ////////////////////////////////////
		int rowcount = 1;
		for (RegistrazioniFilter item : items) {
		    HSSFRow r = sheet.createRow(rowcount++);
		    // DATA distinta
		    HSSFCell cellDADATA = r.createCell(0);
		    DateFormat myDateFormatOut = new SimpleDateFormat("dd/MM/yyyy");
		    String outdateDallaData = "";
		    if (item.getDataDistinta() != null) {
			outdateDallaData = myDateFormatOut.format(item.getDataDistinta());
		    }
		    cellDADATA.setCellValue(new HSSFRichTextString(outdateDallaData));
		    // NOMINATIVO
		    HSSFCell cellID = r.createCell(1);
		    cellID.setCellValue(new HSSFRichTextString(item.getAnagrafe().getDescrizioneRichiedente()));
		    // MERCATO
		    HSSFCell cellMERCATO = r.createCell(2);
		    String mercato = "";
		    if (item.getMercati() != null) {
			mercato = StringUtils.defaultString(item.getMercati().getDescrizione());
		    }
		    cellMERCATO.setCellValue(new HSSFRichTextString(mercato));
		    // USO
		    HSSFCell cellUSO = r.createCell(3);
		    String uso = "";
		    if (item.getMercatiUso() != null) {
			uso = StringUtils.defaultString(item.getMercatiUso().getDescrizione());
		    }
		    cellUSO.setCellValue(new HSSFRichTextString(uso));
		    // POSTEGGIO
		    HSSFCell cellPOSTEGGIO = r.createCell(4);
		    String posteggio = "";
		    if (item.getPosteggio() != null) {
			posteggio = StringUtils.defaultString(item.getPosteggio().getCodiceposteggio());
		    }
		    cellPOSTEGGIO.setCellValue(new HSSFRichTextString(posteggio));
		    // IMPORTO
		    HSSFCell cellIMPORTO = r.createCell(5);
		    cellIMPORTO.setCellValue(item.getImporto().doubleValue());
		    // CONTO
		    HSSFCell cellCONTO = r.createCell(6);
		    cellCONTO.setCellValue(new HSSFRichTextString(item.getConti().getDescrizione()));
		    // IVA
		    HSSFCell cellIVA = r.createCell(7);
		    cellIVA.setCellValue(item.getIva().doubleValue());
		    // IMPONIBILE
		    HSSFCell cellIMPONIBILE = r.createCell(8);
		    cellIMPONIBILE.setCellValue(item.getImponibile().doubleValue());
		}
		// Auto dimimensiona la larghezza delle colonne
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	    throw new RuntimeException("Errore nell'export del file excel con pivot in Stampa iva: " + e.getLocalizedMessage());
	}
	return workbook;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
