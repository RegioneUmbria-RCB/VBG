package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.AlberoprocSorteggiHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.SorteggiCategorie;
import it.gruppoinit.pal.gp.core.domain.Sorteggidettaglio;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestatainfo;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.BaseCommand;
import it.gruppoinit.pal.gp.core.domain.web.SorteggitestataCommand;
import it.gruppoinit.pal.gp.core.domain.web.SorteggitestataFilter;
import it.gruppoinit.pal.gp.core.features.sorteggi.FiltriSorteggioBean;
import it.gruppoinit.pal.gp.core.features.sorteggi.SorteggioResponse;
import it.gruppoinit.pal.gp.core.features.sorteggi.TipologiaStatoSorteggioIstanzaEnum;
import it.gruppoinit.pal.gp.core.features.sorteggi.categorie.SorteggiCategorieService;
import it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio.SorteggidettaglioService;
import it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio.SorteggioDettaglioDTO;
import it.gruppoinit.pal.gp.core.features.sorteggi.testata.SorteggitestataService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.NaturaendoService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author Luca Proietti
 */
@Controller
@SessionAttributes(value = { "sorteggitestata", "sorteggitestataCommand" })
public class SorteggitestataController extends BaseController<Sorteggitestata> {

    private static final String DISPLAY_NONE = "display:none;";
    private static final String TESTATA_ATTRIBUTE = "sorteggitestata";
    private static final String COMMAND = "sorteggitestataCommand";
    private static final String FORM_TESTATA = "sorteggitestata/form";
    private static final String FORM_MOVIMENTO = "sorteggitestata/formMovimento";
    private static final Logger log = LoggerFactory.getLogger(SorteggitestataController.class);
    private SorteggitestataService sorteggitestataService;
    private StatiistanzaService statiistanzaService;
    private SorteggiCategorieService sorteggiCategorieService;
    private TipiMovimentoService tipiMovimentoService;
    private AmministrazioniService amministrazioniService;
    private SorteggidettaglioService sorteggidettaglioService;
    private IstanzeService istanzeService;
    private VerticalizzazioniService verticalizzazioniService;
    private NaturaendoService naturaendoService;
    private AlberoprocService alberoprocService;

    @Autowired
    public void setSorteggitestataService(SorteggitestataService sorteggitestataService) {

	this.sorteggitestataService = sorteggitestataService;
    }

    @Autowired
    public void setStatiistanzaService(StatiistanzaService statiistanzaService) {

	this.statiistanzaService = statiistanzaService;
    }

    @Autowired
    public void setSorteggiCategorieService(SorteggiCategorieService sorteggiCategorieService) {

	this.sorteggiCategorieService = sorteggiCategorieService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setSorteggidettaglioService(SorteggidettaglioService sorteggidettaglioService) {

	this.sorteggidettaglioService = sorteggidettaglioService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setNaturaendoService(NaturaendoService naturaendoService) {

	this.naturaendoService = naturaendoService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Sorteggitestata> sorteggitestataList = sorteggitestataService.findAll(null, null);
	ModelMap model = new ModelMap(sorteggitestataList);
	model.addAttribute("sorteggitestataList", sorteggitestataList);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam(value = "codiceAlgoritmo", required = false) Integer codiceAlgoritmo, Model model) {

	SorteggitestataCommand sorteggitestata = new SorteggitestataCommand();
	if (codiceAlgoritmo != null) {
	    sorteggitestata.setCodiceAlgoritmo(codiceAlgoritmo);
	    setPageAttributes(model, null, codiceAlgoritmo);
	} else {
	    sorteggitestata.setCodiceAlgoritmo(WebConstants.SORTEGGIO_STANDARD);
	    setPageAttributes(model, null, WebConstants.SORTEGGIO_STANDARD);
	}
	Sorteggitestata entity = new Sorteggitestata();
	sorteggitestata.setEntity(entity);
	SorteggitestataFilter sorteggitestataFilter = new SorteggitestataFilter();
	sorteggitestataFilter.setDataSorteggio(new Date());
	sorteggitestata.setSorteggitestataFilter(sorteggitestataFilter);
	setCommandAttributes(sorteggitestata);
	sorteggitestata.setDisplayMode(BaseCommand.NEW);
	model.addAttribute(TESTATA_ATTRIBUTE, sorteggitestata);
	return FORM_TESTATA;
    }

    @RequestMapping
    public String sorteggia(Model model, @RequestParam("sorteggia") Boolean sorteggia,
	    @ModelAttribute("sorteggitestata") SorteggitestataCommand sorteggitestata, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	SorteggitestataFilter filter = sorteggitestata.getSorteggitestataFilter();
	//1. Popolo i filtri
	populateHelpersFromRequest(request, filter);
	//2. Verifico se devo fare il sorteggio
	if (sorteggitestata.getDisplayMode() == BaseCommand.NEW && Boolean.TRUE.equals(sorteggia)) {
	    log.debug("sorteggia# Display mode = {}, sorteggia = {} ", sorteggitestata.getDisplayMode(), sorteggia);
	    return effettuaSorteggio(model, filter, sorteggitestata, result, request, response);
	} else {
	    return mostraSorteggi(model, sorteggitestata, request, response);
	}
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response) {

	SorteggitestataCommand sorteggitestata = new SorteggitestataCommand();
	Sorteggitestata entity = sorteggitestataService.findById(new PkId(codice));
	sorteggitestata.setEntity(entity);
	// Recuperare il codice Algoritmo dai sorteggiotestatainfos mi serve per visualizzare il campo pratica obbligatoria 
	for (Sorteggitestatainfo sorteggitestatainfo : entity.getSorteggitestatainfos()) {
	    if (sorteggitestatainfo.getId().getNome().equals("label.codice_algoritmo")) {
		if (sorteggitestatainfo.getValore().equals("0")) {
		    sorteggitestata.setCodiceAlgoritmo(WebConstants.SORTEGGIO_STANDARD);
		} else {
		    sorteggitestata.setCodiceAlgoritmo(WebConstants.SORTEGGIO_REGIONE_EMILIA_ROMAGNA);
		}
	    }
	}
	setCommandAttributes(sorteggitestata);
	sorteggitestata.setDisplayMode(BaseCommand.VIEW);
	List<SorteggioDettaglioDTO> sorteggidettaglioDTOList = this.sorteggitestataService.findDettaglioDTO(entity.getId().getCodice());
	sorteggitestata.setSorteggidettaglioDTOList(sorteggidettaglioDTOList);
	boolean export = createJMesaExport(request, response, sorteggidettaglioDTOList);
	if (export) {
	    return null;
	}
	model.addAttribute(TESTATA_ATTRIBUTE, sorteggitestata);
	setPageAttributes(model, request, null);
	fixRenderEntityProperty(entity);
	return FORM_TESTATA;
    }

    @RequestMapping
    public String update(@ModelAttribute("sorteggitestata") SorteggitestataCommand sorteggitestata, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(sorteggitestata.getEntity());
	try {
	    // (FIX Gianpaolo)
	    // Posso fare questo perchè il bottone salva non aggiona niente se non un oggetto che viene inserto, nel caso si 
	    // preme salva senza oggetto non fa niente, se la funzionalità dovrà aggiornare altri campi dovrà
	    // essere rivista la jsp nella sua logica di visualizzazione e invio campi 
	    Sorteggitestata soteggioBD = sorteggitestataService.findById(new PkId(sorteggitestata.getEntity().getId().getCodice()));
	    if (EntityUtils.getNestedProperty(sorteggitestata.getEntity().getOggetto(), "id.codice") != null) {
		log.debug("update# update sorteggiotesta = {} con oggetto = {}", sorteggitestata.getEntity().getId().getCodice(),
			sorteggitestata.getEntity().getOggetto().getId().getCodice());
		soteggioBD.setOggetto(sorteggitestata.getEntity().getOggetto());
	    } else {
		soteggioBD.setOggetto(null);
	    }
	    sorteggitestataService.update(soteggioBD);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, sorteggitestata.getEntity(), true, e);
	    fixRenderEntityProperty(sorteggitestata.getEntity());
	    return FORM_TESTATA;
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + sorteggitestata.getEntity().getId().getCodice() + "&sorteggidettaglio_id_f_sorteggiata=Si&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("sorteggitestata") SorteggitestataCommand sorteggitestata, BindingResult result, SessionStatus status,
	    HttpServletRequest request) throws Exception {

	String redirectautorizzaCancellazione = autorizzaCancellazioneDato(WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI_PWD_SORTEGGI,
		"redirect:delete.htm", "../sorteggitestata/view.htm?codice=" + sorteggitestata.getEntity().getId().getCodice(), request);
	if (StringUtils.isNotBlank(redirectautorizzaCancellazione)) {
	    return redirectautorizzaCancellazione;
	}
	Sorteggitestata objToDelete = sorteggitestataService.findById(sorteggitestata.getEntity().getId());
	try {
	    sorteggitestataService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, sorteggitestata.getEntity(), true, e);
	    fixRenderEntityProperty(sorteggitestata.getEntity());
	    return FORM_TESTATA;
	} finally {
	    cleanCancellazioniAttribute(WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI_PWD_SORTEGGI, request);
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String eliminaSorteggitestata(Model model, @RequestParam("codice") Integer codiceSorteggio, HttpServletRequest request) throws Exception {

	String redirectautorizzaCancellazione = autorizzaCancellazioneDato(WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI_PWD_SORTEGGI,
		"redirect:eliminaSorteggitestata.htm?codice=" + codiceSorteggio, "../sorteggitestata/list.htm", request);
	if (StringUtils.isNotBlank(redirectautorizzaCancellazione)) {
	    return redirectautorizzaCancellazione;
	}
	try {
	    Sorteggitestata objToDelete = sorteggitestataService.findById(new PkId(codiceSorteggio));
	    sorteggitestataService.delete(objToDelete);
	} finally {
	    cleanCancellazioniAttribute(WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI_PWD_SORTEGGI, request);
	}
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Sorteggitestata entity) {

	// Metodo non implementato
    }

    @Override
    protected void fixRenderEntityProperty(Sorteggitestata entity) {

	if (entity.getOggetto() == null) {
	    entity.setOggetto(new Oggetti());
	}
	if (entity.getComune() == null) {
	    entity.setComune(new Comuni());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	if (entity.getCategoria() == null) {
	    entity.setCategoria(new SorteggiCategorie());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	// Metodo non implementato
    }

    private void populateHelpersFromRequest(HttpServletRequest request, SorteggitestataFilter filter) {

	String[] codiceAlberoproc = request.getParameterValues("helper.codiceAlberoproc");
	if (codiceAlberoproc == null) {
	    return;
	}
	String[] descrizioneAlberoproc = request.getParameterValues("helper.descrizioneAlberoproc");
	String[] necessario = request.getParameterValues("helper.necessario");
	String[] peso = request.getParameterValues("helper.peso");
	String[] scCodice = request.getParameterValues("helper.scCodice");
	filter.setAlberoprocSorteggiHelpers(new ArrayList<AlberoprocSorteggiHelper>());
	for (int i = 0; i < codiceAlberoproc.length; i++) {
	    String codiceAlb = codiceAlberoproc[i];
	    if (StringUtils.isNotBlank(codiceAlb) && Utilities.isInteger(codiceAlb)) {
		String desc = descrizioneAlberoproc[i];
		String necessarioVal = null;
		if (necessario != null) {
		    necessarioVal = necessario[i];
		}
		String pesoVal = null;
		if (peso != null) {
		    pesoVal = peso[i];
		}
		if (StringUtils.isBlank(pesoVal) || !Utilities.isInteger(pesoVal)) {
		    pesoVal = "0";
		}
		String scCodiceVal = scCodice[i];
		AlberoprocSorteggiHelper ash = new AlberoprocSorteggiHelper(Integer.valueOf(codiceAlb), desc, Integer.valueOf(pesoVal),
			Boolean.valueOf(necessarioVal), scCodiceVal);
		filter.getAlberoprocSorteggiHelpers().add(ash);
	    }
	}
    }

    private String effettuaSorteggio(Model model, SorteggitestataFilter filter, SorteggitestataCommand sorteggitestata, BindingResult result,
	    HttpServletRequest request, HttpServletResponse response) {

	try {
	    FiltriSorteggioBean filtri = FiltriSorteggioBean.fromSorteggitestataFilter(this.alberoprocService, filter);
	    filtri.setCodiceAlgoritmo(sorteggitestata.getCodiceAlgoritmo());
	    if (sorteggitestata.getCodiceAlgoritmo() != null && sorteggitestata.getCodiceAlgoritmo() == WebConstants.SORTEGGIO_STANDARD) {
		log.debug("sorteggia# sorteggio = {} ", sorteggitestata.getCodiceAlgoritmo());
		SorteggioResponse sorteggio = sorteggitestataService.sorteggia(sorteggitestata.getEntity(), filtri);
		sorteggitestata.setEntity(sorteggio.getTestata());
		sorteggitestata.setSorteggidettaglioDTOList(sorteggio.getDettagli());
	    } else if (sorteggitestata.getCodiceAlgoritmo() != null
		    && sorteggitestata.getCodiceAlgoritmo() == WebConstants.SORTEGGIO_REGIONE_EMILIA_ROMAGNA) {
		log.debug("sorteggia# sorteggio = {} ", sorteggitestata.getCodiceAlgoritmo());
		sorteggitestata.setEntity(sorteggitestataService.sorteggiaLR152013(sorteggitestata, filtri));
	    } else if (sorteggitestata.getCodiceAlgoritmo() != null) {
		log.debug("sorteggia# sorteggio = NULL effettuo sorteggio standard ");
		SorteggioResponse sorteggio = sorteggitestataService.sorteggia(sorteggitestata.getEntity(), filtri);
		sorteggitestata.setEntity(sorteggio.getTestata());
		sorteggitestata.setSorteggidettaglioDTOList(sorteggio.getDettagli());
	    }
	    setCommandAttributes(sorteggitestata);
	    setPageAttributes(model, request, sorteggitestata.getCodiceAlgoritmo());
	    boolean export = createJMesaExport(request, response, sorteggitestata.getSorteggidettaglioList());
	    if (export) {
		return null;
	    }
	    if (Boolean.TRUE.equals(filter.getSalva())) {
		log.debug("sorteggia# salva sorteggio = {} ", filter.getSalva());
		sorteggitestata.setDisplayMode(BaseCommand.VIEW);
		return "redirect:view.htm?codice=" + sorteggitestata.getEntity().getId().getCodice() +
		       "&sorteggidettaglio_id_f_sorteggiata=Si&status_msg=01";
	    } else {
		if (sorteggitestata.getSorteggidettaglioDTOList() == null) {
		    sorteggitestata.setSorteggidettaglioDTOList(new ArrayList<SorteggioDettaglioDTO>());
		}
		sorteggitestata.setSorteggidettaglioDTOList(sorteggitestata.getSorteggidettaglioDTOList());
		sorteggitestata.setDisplayMode(BaseCommand.NEW);
		model.addAttribute(TESTATA_ATTRIBUTE, sorteggitestata);
		return FORM_TESTATA;
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, sorteggitestata.getSorteggitestataFilter(), true, "sorteggitestataFilter", e);
	    fixRenderEntityProperty(sorteggitestata.getEntity());
	    setCommandAttributes(sorteggitestata);
	    model.addAttribute(TESTATA_ATTRIBUTE, sorteggitestata);
	    setPageAttributes(model, request, sorteggitestata.getCodiceAlgoritmo());
	    return FORM_TESTATA;
	}
    }

    private String mostraSorteggi(Model model, SorteggitestataCommand sorteggitestata, HttpServletRequest request, HttpServletResponse response) {

	if (sorteggitestata.getDisplayMode() == BaseCommand.VIEW) {
	    setPageAttributes(model, request, sorteggitestata.getCodiceAlgoritmo());
	    Sorteggitestata entity = sorteggitestataService.findById(new PkId(sorteggitestata.getEntity().getId().getCodice()));
	    sorteggitestata.setEntity(entity);
	    setCommandAttributes(sorteggitestata);
	    boolean export = createJMesaExport(request, response, sorteggitestata.getSorteggidettaglioDTOList());
	    if (export) {
		return null;
	    }
	} else {
	    setCommandAttributes(sorteggitestata);
	    setPageAttributes(model, request, sorteggitestata.getCodiceAlgoritmo());
	    boolean export = createJMesaExport(request, response, sorteggitestata.getSorteggidettaglioDTOList());
	    if (export) {
		return null;
	    }
	}
	model.addAttribute(TESTATA_ATTRIBUTE, sorteggitestata);
	fixRenderEntityProperty(sorteggitestata.getEntity());
	return FORM_TESTATA;
    }

    private void setPageAttributes(Model model, HttpServletRequest request, Integer codiceAlgoritmo) {

	if (request != null) {
	    gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SORT_TEST_VISSORTEGGIATE_DIV, "1", request);
	    gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SORT_TEST_VISNONSORTEGGIATE_DIV, "0", request);
	    gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SORT_TEST_VISFILTRI_DIV, "0", request);
	}
	List<Integer> codiciIntervento = new ArrayList<Integer>();
	model.addAttribute("codiciIntervento", codiciIntervento);
	List<String> descrizioniAlberoproc = new ArrayList<String>();
	model.addAttribute("descrizioniAlberoproc", descrizioniAlberoproc);
	List<Integer> pesiIntervento = new ArrayList<Integer>();
	model.addAttribute("pesiIntervento", pesiIntervento);
	List<Boolean> checkIntervento = new ArrayList<Boolean>();
	model.addAttribute("checkIntervento", checkIntervento);
	model.addAttribute("sorteggilr152013", Boolean.FALSE);
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_SORTEGGI)) {
	    Verticalizzazioniparametri ver = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_SORTEGGI,
		    WebConstants.VERTICALIZZAZIONE_PARAM_LR_152013_ER);
	    if (ver != null && StringUtils.isNotBlank(ver.getValore()) && ver.getValore().equals("1")) {
		model.addAttribute("sorteggilr152013", Boolean.TRUE);
	    }
	}
	if (codiceAlgoritmo != null && codiceAlgoritmo == WebConstants.SORTEGGIO_STANDARD) {
	    model.addAttribute("filtriistanza", "");
	    model.addAttribute("filtrimovimento", "");
	    model.addAttribute("filtriestrazione", "");
	    model.addAttribute("filtriesclusioneistanze", "");
	    model.addAttribute("filtriintervallotemporale", DISPLAY_NONE);
	    model.addAttribute("filtriprocedimento", DISPLAY_NONE);
	    model.addAttribute("filtripercentuale", "");
	    model.addAttribute("filtriestrazionegruppi", "");
	    model.addAttribute("filtriarrotondamento", "");
	    model.addAttribute("filtrodatamovimentodalAl", "");
	    model.addAttribute("filtrotipomovimento", "");
	    model.addAttribute("filtrotiporicercamovimento", "");
	    model.addAttribute("filtrotipologiaesito", "");
	    model.addAttribute("filtroarchiviopratiche", "");
	    model.addAttribute("filtrotipologiaintervento", "");
	    model.addAttribute("filtroprocedura", "");
	    model.addAttribute("filtrostato", "");
	    model.addAttribute("filtropeso", "");
	    model.addAttribute("filtroobbligatorio", "");
	}
	if (codiceAlgoritmo != null && codiceAlgoritmo == WebConstants.SORTEGGIO_REGIONE_EMILIA_ROMAGNA) {
	    model.addAttribute("filtriistanza", DISPLAY_NONE);
	    model.addAttribute("filtrimovimento", "");
	    model.addAttribute("filtriestrazione", "");
	    model.addAttribute("filtriesclusioneistanze", DISPLAY_NONE);
	    model.addAttribute("filtriintervallotemporale", "");
	    model.addAttribute("filtriprocedimento", "");
	    model.addAttribute("filtripercentuale", "");
	    model.addAttribute("filtriestrazionegruppi", DISPLAY_NONE);
	    model.addAttribute("filtriarrotondamento", DISPLAY_NONE);
	    model.addAttribute("filtrodatamovimentodalAl", DISPLAY_NONE);
	    model.addAttribute("filtrotipomovimento", "");
	    model.addAttribute("filtrotiporicercamovimento", DISPLAY_NONE);
	    model.addAttribute("filtrotipologiaesito", DISPLAY_NONE);
	    model.addAttribute("filtroarchiviopratiche", DISPLAY_NONE);
	    model.addAttribute("filtrotipologiaintervento", DISPLAY_NONE);
	    model.addAttribute("filtroprocedura", DISPLAY_NONE);
	    model.addAttribute("filtrostato", DISPLAY_NONE);
	    model.addAttribute("filtropeso", DISPLAY_NONE);
	    model.addAttribute("filtroobbligatorio", DISPLAY_NONE);
	}
    }

    private void setCommandAttributes(SorteggitestataCommand command) {

	List<Statiistanza> statiistanzaList = statiistanzaService.findBySoftware(ORMHelper.getSoftware());
	command.setStatiistanzaList(statiistanzaList);
	List<Sorteggitestata> sorteggitestataList = sorteggitestataService.findAllSenzaCategoria();
	command.setSorteggitestataList(sorteggitestataList);
	List<SorteggiCategorie> sorteggiCategorieList = sorteggiCategorieService.findAll(null, null);
	command.setSorteggiCategorieList(sorteggiCategorieList);
	if (EntityUtils.getNestedProperty(command.getSorteggitestataFilter(), "tipoMovimento.id.tipomovimento") != null) {
	    Tipimovimento tipimovimento = tipiMovimentoService
		    .findById(new TipimovimentoId(command.getSorteggitestataFilter().getTipoMovimento().getId().getTipomovimento()));
	    command.getSorteggitestataFilter().setTipoMovimento(tipimovimento);
	}
	List<Naturaendo> naturaendoList = naturaendoService.findAll(null, null);
	command.setNaturaendoList(naturaendoList);
    }

    @RequestMapping
    public String createMovimento(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	SorteggitestataCommand sorteggitestataCommand = new SorteggitestataCommand();
	Sorteggitestata sorteggitestata = sorteggitestataService.findById(id);
	Movimenti movimento = new Movimenti();
	sorteggitestataCommand.setMovimento(movimento);
	fixRenderEntityProperty(sorteggitestata);
	movimento.setData(new Date());
	model.addAttribute(TESTATA_ATTRIBUTE, sorteggitestata);
	//
	model.addAttribute(COMMAND, sorteggitestataCommand);
	return FORM_MOVIMENTO;
    }

    @RequestMapping
    public String saveMovimento(@RequestParam("codice") Integer codice,
	    @ModelAttribute("sorteggitestataCommand") SorteggitestataCommand sorteggitestataCommand, BindingResult result, SessionStatus status,
	    Model model, HttpServletRequest request) {

	sorteggitestataCommand.getMovimento().setResponsabile(getCurrentlyAuthenticatedUserDetails());
	try {
	    sorteggitestataService.salvaMovimento(codice, sorteggitestataCommand.getMovimento(),
		    sorteggitestataCommand.getTipologiaStatoSorteggioIstanza());
	} catch (Exception e) {
	    if (EntityUtils.getNestedProperty(sorteggitestataCommand.getMovimento(), "tipomovimento.id.tipomovimento") != null) {
		Tipimovimento tipimovimento = tipiMovimentoService
			.findById(new TipimovimentoId(sorteggitestataCommand.getMovimento().getTipomovimento().getId().getTipomovimento()));
		sorteggitestataCommand.getMovimento().setTipomovimento(tipimovimento);
	    }
	    copyErrorsToBindingResult(result, sorteggitestataCommand, false, e);
	    model.addAttribute(TESTATA_ATTRIBUTE, sorteggitestataService.findById(new PkId(codice)));
	    model.addAttribute(COMMAND, sorteggitestataCommand);
	    return FORM_MOVIMENTO;
	}
	status.setComplete();
	Integer codiceamministrazione = 0;
	if (EntityUtils.getNestedProperty(sorteggitestataCommand, "movimento.amministrazioni.id.codice") != null) {
	    codiceamministrazione = sorteggitestataCommand.getMovimento().getAmministrazioni().getId().getCodice();
	}
	return "redirect:viewMovimento.htm?codice=" + codice + "&codicetipomovimento=" +
	       sorteggitestataCommand.getMovimento().getTipomovimento().getId().getTipomovimento() + "&codiceamministrazione=" +
	       codiceamministrazione + "&data=" + sorteggitestataCommand.getMovimento().getData().getTime() + "&tipologiaStatoSorteggioIstanza=" +
	       sorteggitestataCommand.getTipologiaStatoSorteggioIstanza() + "&status_msg=02";
    }

    @RequestMapping
    public String viewMovimento(@RequestParam("codice") Integer codice, @RequestParam("codicetipomovimento") String codicetipomovimento,
	    @RequestParam(value = "codiceamministrazione", required = false) Integer codiceamministrazione,
	    @RequestParam(value = "tipologiaStatoSorteggioIstanza", required = false) TipologiaStatoSorteggioIstanzaEnum tipologiaStatoSorteggioIstanza,
	    @RequestParam("data") Long data, Model model, HttpServletRequest request, HttpServletResponse response) {

	SorteggitestataCommand sorteggitestataCommand = new SorteggitestataCommand();
	Sorteggitestata sorteggitestata = sorteggitestataService.findById(new PkId(codice));
	fixRenderEntityProperty(sorteggitestata);
	Movimenti movimento = new Movimenti();
	movimento.setData(new Date(data));
	movimento.setTipomovimento(tipiMovimentoService.findById(new TipimovimentoId(codicetipomovimento)));
	if (codiceamministrazione != null && codiceamministrazione != 0) {
	    movimento.setAmministrazioni(amministrazioniService.findById(new PkId(codiceamministrazione)));
	}
	sorteggitestataCommand.setTipologiaStatoSorteggioIstanza(tipologiaStatoSorteggioIstanza);
	model.addAttribute(TESTATA_ATTRIBUTE, sorteggitestata);
	sorteggitestataCommand.setMovimento(movimento);
	model.addAttribute(COMMAND, sorteggitestataCommand);
	model.addAttribute("view", true);
	return FORM_MOVIMENTO;
    }

    @RequestMapping
    public void ajaxFindSorteggidettaglioByIstanza(@RequestParam("codiceIstanza") Integer codiceistanza, HttpServletResponse response)
	    throws IOException {

	Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	List<Sorteggidettaglio> list = sorteggidettaglioService.findAllByIstanza(istanza);
	StringBuilder buffer = new StringBuilder();
	for (Sorteggidettaglio sorteggidettaglio : list) {
	    if (BooleanUtils.isTrue(sorteggidettaglio.getSorteggiata())) {
		String labelSorteggio = getMessageFromBundle("label.sorteggio", null);
		buffer = buffer //
			.append(labelSorteggio) //
			.append(": ") //
			.append(sorteggidettaglio.getSorteggitestata().getStDescrizione()) //
			.append("\n");
	    }
	}
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }
}
