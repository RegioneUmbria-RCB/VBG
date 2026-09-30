package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.BollCfgCausalioneri;
import it.gruppoinit.pal.gp.core.domain.BollCfgCausalioneriId;
import it.gruppoinit.pal.gp.core.domain.BollCfgConti;
import it.gruppoinit.pal.gp.core.domain.BollCfgContiId;
import it.gruppoinit.pal.gp.core.domain.BollCfgMercati;
import it.gruppoinit.pal.gp.core.domain.BollCfgMercatiId;
import it.gruppoinit.pal.gp.core.domain.BollCfgRuoli;
import it.gruppoinit.pal.gp.core.domain.BollCfgRuoliId;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipoRate;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RangeRateizzazioni;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.TipiScadenza;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.arrotondamento.ElencoArrotondamentiCustom;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.BollCfgTipoMetadatiService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.DeleteMetadatoRequest;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.ElencoMetadatiResponse;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.InsertMetadatoRequest;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.InsertMetadatoResponse;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.MetadatoBollettazione;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.UpdateMetadatoRequest;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.UpdateMetadatoResponse;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.bollettazione.IBollCfgTipoRateService;
import it.gruppoinit.pal.gp.core.service.BollCfgCausalioneriService;
import it.gruppoinit.pal.gp.core.service.BollCfgContiService;
import it.gruppoinit.pal.gp.core.service.BollCfgMercatiService;
import it.gruppoinit.pal.gp.core.service.BollCfgRuoliService;
import it.gruppoinit.pal.gp.core.service.BollCfgTipoService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.RangeRateizzazioniService;
import it.gruppoinit.pal.gp.core.service.RuoliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiScadenzaService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("bollcfgtipo")
public class BollCfgTipoController extends BaseJsonController<BollCfgTipo> {

    @Autowired
    private BollCfgTipoService bollcfgtipoService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private TipiScadenzaService tipiScadenzaService;
    @Autowired
    private BollCfgRuoliService bllCfgRuoliService;
    @Autowired
    private RuoliService ruoliService;
    @Autowired
    private BollCfgCausalioneriService bollCfgCausalioneriService;
    @Autowired
    private TipicausalioneriService tipicausalioneriService;
    @Autowired
    private BollCfgMercatiService bollCfgMercatiService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private IBollCfgTipoRateService bollCfgTipoRateService;
    @Autowired
    private RangeRateizzazioniService rangeRateizzazioniService;
    @Autowired
    private ContiService contiService;
    @Autowired
    private BollCfgContiService bollCfgContiService;
    private BollCfgTipoMetadatiService metadatiService;

    @Autowired
    public void setMetadatiService(BollCfgTipoMetadatiService metadatiService) {

	this.metadatiService = metadatiService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<BollCfgTipo> bollcfgtipoList = bollcfgtipoService.findAll(null, null);
	ModelMap model = new ModelMap(bollcfgtipoList);
	model.addAttribute("bollcfgtipoList", bollcfgtipoList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	BollCfgTipo bollcfgtipo = new BollCfgTipo();
	bollcfgtipo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(bollcfgtipo);
	ElencoArrotondamentiCustom arrotondamentiCustom = new ElencoArrotondamentiCustom();
	model.addAttribute("arrotondamentiCustom", arrotondamentiCustom.get());
	model.addAttribute("bollcfgtipo", bollcfgtipo);
	setPageAttributes(model);
	return "bollcfgtipo/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("bollcfgtipo") BollCfgTipo bollcfgtipo, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(bollcfgtipo);
	bollcfgtipo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    bollcfgtipoService.insert(bollcfgtipo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bollcfgtipo, e);
	    fixRenderEntityProperty(bollcfgtipo);
	    setPageAttributes(model);
	    return "bollcfgtipo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + bollcfgtipo.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	BollCfgTipo bollcfgtipo = bollcfgtipoService.findById(id);
	List<BollCfgTipoRate> bollCfgTipoRate = bollCfgTipoRateService.findByTipo(bollcfgtipo.getId().getCodice());
	List<RangeRateizzazioni> rateizzazioni = rangeRateizzazioniService.findAll(null, null);
	fixRenderEntityProperty(bollcfgtipo);
	ElencoArrotondamentiCustom arrotondamentiCustom = new ElencoArrotondamentiCustom();
	model.addAttribute("arrotondamentiCustom", arrotondamentiCustom.get());
	model.addAttribute("bollcfgtipo", bollcfgtipo);
	model.addAttribute("bollCfgTipoRate", bollCfgTipoRate);
	model.addAttribute("rateizzazioni", rateizzazioni);
	setPageAttributes(model);
	return "bollcfgtipo/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("bollcfgtipo") BollCfgTipo bollcfgtipo, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(bollcfgtipo);
	try {
	    bollcfgtipoService.update(bollcfgtipo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bollcfgtipo, e);
	    fixRenderEntityProperty(bollcfgtipo);
	    setPageAttributes(model);
	    return "bollcfgtipo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + bollcfgtipo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("bollcfgtipo") BollCfgTipo bollcfgtipo, BindingResult result, SessionStatus status) {

	BollCfgTipo objToDelete = bollcfgtipoService.findById(bollcfgtipo.getId());
	try {
	    bollcfgtipoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(bollcfgtipo);
	    setPageAttributes(model);
	    return "bollcfgtipo/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(BollCfgTipo entity) {

    }

    @Override
    protected void fixRenderEntityProperty(BollCfgTipo entity) {

	if (entity.getFkTipiscadenzaId() == null) {
	    entity.setFkTipiscadenzaId(new TipiScadenza());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	if (entity.getLetteraAccompagnamento() == null) {
	    entity.setLetteraAccompagnamento(new Letteretipo());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	List<String> periodi = bollcfgtipoService.selectPeriodo();
	model.addAttribute("periodi", periodi);
	List<TipiScadenza> tipiScadenza = tipiScadenzaService.findAll(null, null);
	model.addAttribute("tipiScadenza", tipiScadenza);
	List<String> implementazioni = bollcfgtipoService.selectImplementazione();
	model.addAttribute("implementazioni", implementazioni);
	List<CodiceDescrizioneBean> titolaritaPagamenti = bollcfgtipoService.selectTitolaritaPagamenti();
	model.addAttribute("titolaritaPagamenti", titolaritaPagamenti);
	List<RangeRateizzazioni> rangeRateizzazioni = rangeRateizzazioniService.findAll(null, null);
	model.addAttribute("rateizzazioni", rangeRateizzazioni);
    }

    @RequestMapping
    public String ajaxAltriDati(@RequestParam("codiceBollcfgTipo") Integer codiceBollcfgTipo, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	List<MetadatiBean> metadati = this.metadatiService.findMetadatiConfigurati(codiceBollcfgTipo);
	model.addAttribute("metadati", metadati);
	return "bollcfgtipo/ajaxAltriDati";
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonInsertMetadato(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	InsertMetadatoResponse jsonResponse = null;
	response.setContentType("application/json");
	try {
	    InsertMetadatoRequest jsonRequest = fromJson(request.getInputStream(), InsertMetadatoRequest.class);
	    jsonResponse = new InsertMetadatoResponse(this.metadatiService.insertMetadato(jsonRequest));
	} catch (Exception e) {
	    response.setContentType("application/json");
	    jsonResponse = new InsertMetadatoResponse(e);
	} finally {
	    String res = toJson(jsonResponse, false);
	    response.getOutputStream().write(res.getBytes("utf-8"));
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonUpdateMetadato(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	UpdateMetadatoResponse jsonResponse = new UpdateMetadatoResponse();
	response.setContentType("application/json");
	try {
	    UpdateMetadatoRequest jsonRequest = fromJson(request.getInputStream(), UpdateMetadatoRequest.class);
	    this.metadatiService.updateMetadato(jsonRequest);
	} catch (Exception e) {
	    response.setContentType("application/json");
	    jsonResponse = new UpdateMetadatoResponse(e);
	} finally {
	    String res = toJson(jsonResponse, true);
	    response.getOutputStream().write(res.getBytes("utf-8"));
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.DELETE)
    public void jsonDeleteMetadato(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	DeleteMetadatoRequest jsonRequest = fromJson(request.getInputStream(), DeleteMetadatoRequest.class);
	this.metadatiService.deleteMetadato(jsonRequest);
	String res = "OK";
	response.getOutputStream().write(res.getBytes("utf-8"));
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.GET)
    public void jsonElencoMetadati(@RequestParam("codiceBollcfgTipo") Integer codiceBollcfgTipo, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws JAXBException {

	try {
	    List<MetadatoBollettazione> metadati = this.metadatiService.elencoMetadati(codiceBollcfgTipo);
	    ElencoMetadatiResponse jsonResponse = new ElencoMetadatiResponse(metadati);
	    response.setContentType("application/json");
	    String res = toJson(jsonResponse, true);
	    response.getOutputStream().write(res.getBytes("utf-8"));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @RequestMapping
    public String ajaxDettaglioRuoli(@RequestParam(required = false, value = "codiceRuolo") Integer codiceRuolo,
	    @RequestParam("codiceBollcfgTipo") Integer codiceBollcfgTipo, Model model, HttpServletRequest request, HttpServletResponse response) {

	List<BollCfgRuoli> ruoliList = bllCfgRuoliService.findByBollcfgTipo(codiceBollcfgTipo, null, null);
	model.addAttribute("ruoliList", ruoliList);
	model.addAttribute("codiceRuolo", codiceRuolo);
	return "bollcfgtipo/ajaxDettaglioRuoli";
    }

    @RequestMapping
    public void ajaxAssegnaRuoli(@RequestParam("codiceBollcfgTipo") Integer codiceBollcfgTipo, @RequestParam("codiceRuolo") Integer codiceRuolo,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	String result = "Ok";
	try {
	    BollCfgTipo cfgTipo = bollcfgtipoService.findById(new PkId(codiceBollcfgTipo));
	    Ruoli ruoli = ruoliService.findById(new PkId(codiceRuolo));
	    List<BollCfgRuoli> list = bllCfgRuoliService.findByBollcfgTipo(codiceBollcfgTipo, null, null);
	    boolean trovato = false;
	    for (BollCfgRuoli bllCfgRuoli : list) {
		if (bllCfgRuoli.getRuoli() != null && bllCfgRuoli.getRuoli().getId() != null && bllCfgRuoli.getRuoli().getId().getCodice() != null) {
		    if (codiceRuolo.equals(bllCfgRuoli.getRuoli().getId().getCodice())) {
			trovato = true;
			break;
		    }
		}
	    }
	    if (trovato) {
		result = "Attenzione! Il ruolo " + ruoli.getRuolo() + " è già stato assegnato alla bollettazione.";
	    } else {
		BollCfgRuoli entity = new BollCfgRuoli();
		BollCfgRuoliId id = new BollCfgRuoliId();
		id.setFkBollcfgtipoId(codiceBollcfgTipo);
		id.setFkRuoliId(codiceRuolo);
		entity.setId(id);
		entity.setBollCfgTipo(cfgTipo);
		entity.setRuoli(ruoli);
		bllCfgRuoliService.insert(entity);
	    }
	} catch (Exception e) {
	    result = "Si è verificato un errore durante l'inserimento del dato! (dettaglio: " + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public void ajaxEliminaRuoli(@RequestParam("codiceBollcfgTipo") Integer codiceBollcfgTipo, @RequestParam("codiceRuolo") Integer codiceRuolo,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	String result = "Ok";
	try {
	    BollCfgRuoliId id = new BollCfgRuoliId(codiceBollcfgTipo, codiceRuolo);
	    id.setFkBollcfgtipoId(codiceBollcfgTipo);
	    id.setFkRuoliId(codiceRuolo);
	    BollCfgRuoli cfgRuoli = bllCfgRuoliService.findById(id);
	    if (cfgRuoli != null) {
		bllCfgRuoliService.delete(cfgRuoli);
	    }
	} catch (Exception e) {
	    result = "Si è verificato un errore durante la cancellazione del dato! (" + e.getMessage() + ").";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public String ajaxDettaglioTipiCO(@RequestParam(required = false, value = "codiceCO") Integer codiceCO,
	    @RequestParam("codiceBollcfgTipo") Integer codiceBollcfgTipo, Model model, HttpServletRequest request, HttpServletResponse response) {

	List<BollCfgCausalioneri> causalioneriList = bollCfgCausalioneriService.findByBollCfgTipo(codiceBollcfgTipo, null, null);
	model.addAttribute("codiceCO", codiceCO);
	model.addAttribute("causalioneriList", causalioneriList);
	return "bollcfgtipo/ajaxDettaglioCO";
    }

    @RequestMapping
    public void ajaxAssegnaTipiCO(@RequestParam("codiceBollcfgTipo") Integer codiceBollcfgTipo, @RequestParam("codiceCO") Integer codiceCO,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	String result = "Ok";
	try {
	    BollCfgTipo bollCfgTipo = bollcfgtipoService.findById(new PkId(codiceBollcfgTipo));
	    Tipicausalioneri tipicausalioneri = tipicausalioneriService.findById(new PkId(codiceCO));
	    List<BollCfgCausalioneri> list = bollCfgCausalioneriService.findByBollCfgTipo(codiceBollcfgTipo, null, null);
	    boolean trovato = false;
	    for (BollCfgCausalioneri bollCfgCausalioneri : list) {
		if (bollCfgCausalioneri.getTipicausalioneri() != null && bollCfgCausalioneri.getTipicausalioneri().getId() != null
			&& bollCfgCausalioneri.getTipicausalioneri().getId().getCodice() != null) {
		    if (codiceCO.equals(bollCfgCausalioneri.getTipicausalioneri().getId().getCodice())) {
			trovato = true;
			break;
		    }
		}
	    }
	    if (trovato) {
		result = "Attenzione! Il Tipo causale " + tipicausalioneri.getCoDescrizione() + " è già stato assegnato.";
	    } else {
		BollCfgCausalioneri causalioneri = new BollCfgCausalioneri();
		BollCfgCausalioneriId id = new BollCfgCausalioneriId();
		id.setFk_bollcfgtipo_id(codiceBollcfgTipo);
		id.setFkCoId(codiceCO);
		causalioneri.setId(id);
		causalioneri.setBollCfgTipo(bollCfgTipo);
		causalioneri.setTipicausalioneri(tipicausalioneri);
		bollCfgCausalioneriService.insert(causalioneri);
	    }
	} catch (Exception e) {
	    result = "Si è verificato un errore durante l'inserimento del dato! (dettaglio: " + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public void ajaxEliminaTipiCO(@RequestParam("codiceBollcfgTipo") Integer codiceBollcfgTipo, @RequestParam("codiceCO") Integer codiceCO,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	String result = "Ok";
	try {
	    BollCfgCausalioneriId id = new BollCfgCausalioneriId(codiceBollcfgTipo, codiceCO);
	    BollCfgCausalioneri causalioneri = bollCfgCausalioneriService.findById(id);
	    if (causalioneri.getId() != null) {
		bollCfgCausalioneriService.delete(causalioneri);
	    }
	} catch (Exception e) {
	    result = "Si è verificato un errore durante la cancellazione del dato! (" + e.getMessage() + ").";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public String ajaxDettaglioMercati(@RequestParam(required = false, value = "codiceMercati") Integer codiceMercati,
	    @RequestParam("codiceBollcfgTipo") Integer codiceBollcfgTipo, Model model, HttpServletRequest request, HttpServletResponse response) {

	List<BollCfgMercati> cfgMercatiList = bollCfgMercatiService.findByBollcfgTipo(codiceBollcfgTipo, null, null);
	model.addAttribute("cfgMercatiList", cfgMercatiList);
	model.addAttribute("codiceMercati", codiceMercati);
	return "bollcfgtipo/ajaxDettaglioMercati";
    }

    @RequestMapping
    public void ajaxAssegnaMercati(@RequestParam("codiceBollcfgTipo") Integer codiceBollcfgTipo, @RequestParam("codiceMercati") Integer codiceMercati,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	String result = "Ok";
	try {
	    Software software = softwareService.findById(ORMHelper.getSoftware());
	    BollCfgTipo bollCfgTipo = bollcfgtipoService.findById(new PkId(codiceBollcfgTipo));
	    bollCfgTipo.setSoftware(software);
	    Mercati mercati = mercatiService.findById(new PkId(codiceMercati));
	    List<BollCfgMercati> list = bollCfgMercatiService.findByBollcfgTipo(codiceBollcfgTipo, null, null);
	    boolean trovato = false;
	    for (BollCfgMercati bollCfgMercati : list) {
		if (bollCfgMercati.getMercati() != null && bollCfgMercati.getMercati().getId() != null
			&& bollCfgMercati.getMercati().getId().getCodice() != null) {
		    if (codiceMercati.equals(bollCfgMercati.getMercati().getId().getCodice())) {
			trovato = true;
			break;
		    }
		}
	    }
	    if (trovato) {
		result = "Attenzione! Il mercato " + mercati.getDescrizione() + " è già stato assegnato.";
	    } else {
		BollCfgMercatiId id = new BollCfgMercatiId(codiceBollcfgTipo, codiceMercati);
		BollCfgMercati bollCfgMercati = new BollCfgMercati();
		bollCfgMercati.setId(id);
		bollCfgMercati.setBollCfgTipo(bollCfgTipo);
		bollCfgMercati.setMercati(mercati);
		bollCfgMercatiService.insert(bollCfgMercati);
	    }
	} catch (Exception e) {
	    result = "Si è verificato un errore nell'inserimento del mercato. (dettaglio: " + e.getMessage() + ").";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public void ajaxEliminaMercato(@RequestParam("codiceBollcfgTipo") Integer codiceBollcfgTipo, @RequestParam("codiceMercati") Integer codiceMercati,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	String result = "Ok";
	try {
	    BollCfgMercatiId id = new BollCfgMercatiId(codiceBollcfgTipo, codiceMercati);
	    BollCfgMercati bollCfgMercati = bollCfgMercatiService.findById(id);
	    if (bollCfgMercati != null) {
		bollCfgMercatiService.delete(bollCfgMercati);
	    }
	} catch (Exception e) {
	    result = "Si è verificato un errore durante la cancellazione del dato! (" + e.getMessage() + ").";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public String deleteTipoRata(@RequestParam("codiceTipoRata") Integer codiceTipoRata, Model model,
	    @ModelAttribute("bollcfgtipo") BollCfgTipo entity, BindingResult result, SessionStatus status, HttpServletRequest request,
	    HttpServletResponse response) {

	BollCfgTipoRate tipoRate = bollCfgTipoRateService.findById(new PkId(codiceTipoRata));
	try {
	    bollCfgTipoRateService.delete(tipoRate);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, entity, true, e);
	    fixRenderEntityProperty(entity);
	    return "bollcfgtipo/form";
	}
	return "redirect:view.htm?codice=" + entity.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String inserisciTipoRata(@RequestParam("codiceRateizzazione") Integer codiceRateizzazione, Model model,
	    @ModelAttribute("bollcfgtipo") BollCfgTipo entity, BindingResult result, SessionStatus status, HttpServletRequest request,
	    HttpServletResponse response) {

	BollCfgTipoRate tipoRate = new BollCfgTipoRate();
	tipoRate.setBollCfgTipo(entity);
	RangeRateizzazioni rangeRate = rangeRateizzazioniService.findById(new PkId(codiceRateizzazione));
	tipoRate.setRangeRateizzazioni(rangeRate);
	if (verificaSePresente(entity.getId().getCodice(), rangeRate.getId().getCodice())) {
	    FlashMessages.getWarnings().add("La rateizzazione è già presente!");
	    return "redirect:view.htm?codice=" + entity.getId().getCodice();
	}
	try {
	    this.bollCfgTipoRateService.insert(tipoRate);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, entity, true, e);
	    fixRenderEntityProperty(entity);
	    return "bollcfgtipo/form";
	}
	return "redirect:view.htm?codice=" + entity.getId().getCodice() + "&status_msg=02";
    }

    private boolean verificaSePresente(Integer idBoll, Integer idRange) {

	return this.bollCfgTipoRateService.verificaSePresente(idBoll, idRange);
    }

    @RequestMapping
    public void ajaxPopolaCampiRata(@RequestParam("codiceRateizzazione") Integer codiceRateizzazione, Model model,
	    @ModelAttribute("bollcfgtipo") BollCfgTipo entity, BindingResult result, SessionStatus status, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	if (codiceRateizzazione == null) {
	    throw new RuntimeException("Il codice rateizzazione non può essere nullo!");
	}
	RangeRateizzazioni rangeRateizzazioni = rangeRateizzazioniService.findById(new PkId(codiceRateizzazione));
	String json = "{\"descrizione\": \"" +
		rangeRateizzazioni.getTiporateizzazione().getDescrizione() +
		"\",\"rangeBasso\": \"" +
		rangeRateizzazioni.getRangeBasso() +
		"\",\"rangeAlto\": \"" +
		rangeRateizzazioni.getRangeAlto() +
		"\"}";
	response.setContentType("application/json");
	response.getOutputStream().write(json.getBytes());
    }

    @RequestMapping
    public String ajaxDettaglioConto(@RequestParam(required = false, value = "codiceConto") Integer codiceConto,
	    @RequestParam("codiceBollcfgTipo") Integer codiceBollcfgTipo, Model model, HttpServletRequest request, HttpServletResponse response) {

	List<BollCfgConti> contiList = bollCfgContiService.findByBollCfgTipo(codiceBollcfgTipo, null, null);
	model.addAttribute("codiceConto", codiceConto);
	model.addAttribute("contiList", contiList);
	return "bollcfgtipo/ajaxDettaglioConto";
    }

    @RequestMapping
    public void ajaxAssegnaConto(@RequestParam("codiceBollcfgTipo") Integer codiceBollcfgTipo, @RequestParam("codiceConto") Integer codiceConto,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	String result = "Ok";
	try {
	    BollCfgTipo bollCfgTipo = bollcfgtipoService.findById(new PkId(codiceBollcfgTipo));
	    Conti conto = contiService.findById(new PkId(codiceConto));
	    List<BollCfgConti> list = bollCfgContiService.findByBollCfgTipo(codiceBollcfgTipo, null, null);
	    boolean trovato = false;
	    for (BollCfgConti bollCfgConti : list) {
		if (bollCfgConti.getConto() != null && //
			bollCfgConti.getConto().getId() != null && // 
			bollCfgConti.getConto().getId().getCodice() != null && // 
			codiceConto.equals(bollCfgConti.getConto().getId().getCodice())) {
		    trovato = true;
		    break;
		}
	    }
	    if (trovato) {
		result = "Attenzione! Il conto " + conto.getDescrizione() + " è già stato assegnato.";
	    } else {
		BollCfgConti bollCfgConti = new BollCfgConti();
		BollCfgContiId id = new BollCfgContiId();
		id.setFkBollcfgtipoId(codiceBollcfgTipo);
		id.setFkContoId(codiceConto);
		bollCfgConti.setId(id);
		bollCfgConti.setBollCfgTipo(bollCfgTipo);
		bollCfgConti.setConto(conto);
		bollCfgContiService.insert(bollCfgConti);
	    }
	} catch (Exception e) {
	    result = "Si è verificato un errore durante l'inserimento del dato! (dettaglio: " + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public void ajaxEliminaConto(@RequestParam("codiceBollcfgTipo") Integer codiceBollcfgTipo, @RequestParam("codiceConto") Integer codiceConto,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	String result = "Ok";
	try {
	    BollCfgContiId id = new BollCfgContiId(codiceBollcfgTipo, codiceConto);
	    BollCfgConti bollCfgConti = bollCfgContiService.findById(id);
	    if (bollCfgConti.getId() != null) {
		bollCfgContiService.delete(bollCfgConti);
	    }
	} catch (Exception e) {
	    result = "Si è verificato un errore durante la cancellazione del dato! (" + e.getMessage() + ").";
	}
	response.getOutputStream().write(result.getBytes());
    }
}
