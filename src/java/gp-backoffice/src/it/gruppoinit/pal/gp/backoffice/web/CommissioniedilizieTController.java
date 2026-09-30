package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.web.GenerateTable;
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
import it.gruppoinit.pal.gp.core.dao.helper.ContestiMailTipoEnum;
import it.gruppoinit.pal.gp.core.domain.CommedilizieCarica;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipopareri;
import it.gruppoinit.pal.gp.core.domain.CommedilizieVotazioni;
import it.gruppoinit.pal.gp.core.domain.CommedilizieVotiBase;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.CommissioniedilizieTCommand;
import it.gruppoinit.pal.gp.core.features.commissioni.ICommissioniService;
import it.gruppoinit.pal.gp.core.features.commissioni.appello.models.SoggettoPraticaModel;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.ICommissioniDettaglioService;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models.DettaglioCommissioneModel;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models.ElencoSoggettiIstanzaModel;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models.RigaCommissioneModel;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioneListModel;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioneModel;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.jmesa.IstanzePerCommissioneEdiliziaTable;
import it.gruppoinit.pal.gp.core.service.CommedilizieCaricaService;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipopareriService;
import it.gruppoinit.pal.gp.core.service.CommedilizieVotazioniService;
import it.gruppoinit.pal.gp.core.service.CommedilizieVotiBaseService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieRService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieTService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes(value = { "commissioniediliziet", "commissione" })
public class CommissioniedilizieTController extends BaseJsonController<CommissioniedilizieT> {

    private static final String FORM = "commissioniediliziet/form";
    @Autowired
    private CommissioniedilizieTService commissioniedilizietService;
    @Autowired
    private MovimentiNoSecurityService movimentiNoSecurityService;
    @Autowired
    private CommissioniedilizieRService commissioniedilizieRService;
    @Autowired
    private CommedilizieTipopareriService commedilizieTipopareriService;
    @Autowired
    private CommedilizieVotazioniService commedilizieVotazioniService;
    @Autowired
    private CommedilizieVotiBaseService commedilizieVotiBaseService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private LetteretipoService letteretipoService;
    @Autowired
    private DocumentMergeService documentMergeService;
    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private ICommissioniService commissioniService;
    @Autowired
    private ICommissioniDettaglioService commissioniDettaglioService;
    @Autowired
    private CommedilizieCaricaService commedilizieCaricaService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<CommissioneListModel> commissioniedilizietList = commissioniService
		.listaCommissioniPerOperatore(getCurrentlyAuthenticatedUserDetails().getId().getCodice(), null, null);
	ModelMap model = new ModelMap(commissioniedilizietList);
	boolean export = createJMesaExport(request, response, commissioniedilizietList);
	if (export) {
	    return null;
	}
	model.addAttribute("commissioniedilizietList", commissioniedilizietList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	model.addAttribute("commissione", new CommissioneModel());
	setPageAttributes(model);
	return FORM;
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("commissione") CommissioneModel commissione, BindingResult result, SessionStatus status) {

	try {
	    this.commissioniService.insert(commissione);
	    status.setComplete();
	    return "redirect:view.htm?codice=" + commissione.getId() + "&status_msg=01";
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commissione, true, e);
	    model.addAttribute("commissione", commissione);
	    return "redirect:create.htm";
	}
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	CommissioneModel commissione = this.commissioniService.getCommissione(codice);
	model.addAttribute("commissione", commissione);
	model.addAttribute("commissioniediliziet", "");
	setPageAttributes(model);
	return FORM;
    }

    @RequestMapping
    public String update(@RequestParam("codice") Integer codice, Model model, @ModelAttribute("commissione") CommissioneModel commissione,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	try {
	    commissione.setId(codice);
	    this.commissioniService.updateCommissione(commissione);
	    status.setComplete();
	    return "redirect:view.htm?codice=" + commissione.getId() + "&status_msg=02";
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commissione, true, e);
	    model.addAttribute("commissione", commissione);
	    return FORM;
	}
    }

    @RequestMapping
    public String riapri(Model model, @ModelAttribute("commissione") CommissioneModel commissione, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	try {
	    this.commissioniService.riapriCommissioneChiusa(commissione.getId());
	    status.setComplete();
	    return "redirect:view.htm?codice=" + commissione.getId() + "&status_msg=02";
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commissione, true, e);
	    model.addAttribute("commissione", commissione);
	    return FORM;
	}
    }

    @RequestMapping
    public String delete(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response) {

	try {
	    this.commissioniService.delete(codice);
	} catch (Exception e) {
	    if (e instanceof BusinessValidationException) {
		copyErrorsToFlashMessages(null, false, "", e);
		return "redirect:view.htm?codice=" + codice + "&status_msg=03";
	    }
	}
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    /**
     * Associa una convocazione come principale alla commissione
     * 
     * @param codiceCommissione
     * @param codiceConvocazione
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public String updateIdConvocazione(@RequestParam("codiceCommissione") Integer codiceCommissione,
	    @RequestParam("codiceConvocazione") Integer codiceConvocazione, Model model, HttpServletRequest request) {

	this.commissioniService.updateConvocazione(codiceCommissione, codiceConvocazione);
	return "redirect:view.htm?codice=" + codiceCommissione + "&status_msg=02";
    }

    /**
     * <pre>
     * Ritorna una jsp che contiene tutte le istanze che posso essere discusse dalla commissione; Inoltre mantiene alcune
     * informazioni sulla commissione modificabili: 
     * 1- Ora inizio commissione. 
     * 2- Ora fine commissione.
     * 
     * &#64;param codiceCommissione
     * &#64;param model
     * &#64;param commissioniediliziet
     * &#64;param result
     * &#64;param status
     * &#64;param request
     * &#64;param response
     * &#64;return
     * </pre>
     */
    @RequestMapping
    public String listCommissioniedilizieR(@RequestParam("codiceCommissione") Integer codiceCommissione, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	DettaglioCommissioneModel dettaglioCommissione = this.commissioniService.getDettaglioCommissione(codiceCommissione);
	List<CommedilizieCarica> cariche = commedilizieCaricaService.findAll(null, null);
	model.addAttribute("cariche", cariche);
	model.addAttribute("commissione", dettaglioCommissione);
	return "commissioniediliziet/listCommissioniedilizieR";
    }

    /**
     * Il metodo oltre ad aggiornare alcuni campi di commissioniedilizieT (orainizio,orafine) aggiorna anche il campo
     * ordine della commissioniR associate (child)
     * 
     * @param model
     * @param commissioniediliziet
     * @param result
     * @param status
     * @param request
     * @param response
     * @return
     */
    @RequestMapping
    public String updateCommissioniedilizieTAndChild(Model model, @ModelAttribute("commissione") DettaglioCommissioneModel commissione,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	try {
	    this.commissioniService.updateCommissioniedilizieTAndChild(commissione);
	} catch (Exception e) {
	    model.addAttribute("commissione", commissione);
	    return "commissioniediliziet/listCommissioniedilizieR";
	}
	return "redirect:listCommissioniedilizieR.htm?codiceCommissione=" + commissione.getId() + "&status_msg=02";
    }

    @RequestMapping
    public void ajaxAggiornaOrario(@RequestParam("idCommissione") Integer idCommissione, @RequestParam("orarioInizio") String orarioInizio,
	    @RequestParam("orarioFine") String orarioFine, Model model, HttpServletResponse response) throws IOException {

	response.setContentType("text/plain");
	try {
	    this.commissioniService.updateOrario(idCommissione, orarioInizio, orarioFine);
	    response.getWriter().write("Dato aggiornato");
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write("Errore aggiornamento: " + e.getMessage() + "");
	}
    }

    /**
     * Mostra un filtro data attraverso una finestra di dialog per la ricerca delle istanze che possono essere discusse
     * dalla commissione
     * 
     * @param codiceCommissione
     * @param model
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String ajaxShowFiltro(@RequestParam("codiceCommissioneT") Integer codiceCommissione, Model model, HttpServletResponse response)
	    throws IOException {

	// §§§BEGIN§§§
	CommissioniedilizieTCommand commissioniedilizieT = new CommissioniedilizieTCommand();
	CommissioniedilizieT entity = commissioniedilizietService.findById(new PkId(codiceCommissione));
	commissioniedilizieT.setEntity(entity);
	model.addAttribute("commissioniediliziet", commissioniedilizieT);
	return "ajax/filtroIstanzeDaDiscutere";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public void ajaxElencoSoggettiIstanza(Model model, @RequestParam("idRiga") Integer idRiga, HttpServletRequest request,
	    HttpServletResponse response) throws IOException, JAXBException {

	ElencoSoggettiIstanzaModel soggetti = this.commissioniService.getElencoSoggettiIstanza(idRiga);
	response.setContentType("application/json");
	response.getOutputStream().write(this.toJsonBytes(soggetti));
	response.getOutputStream().flush();
    }

    @RequestMapping
    public void ajaxGestisciSoggettiIstanza(Model model, @RequestParam("idRiga") Integer idRiga, @RequestParam("anagrafiche") String anagrafiche,
	    @RequestParam("cariche") String cariche, @RequestParam("soggettipratica") String soggettipratica, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	if (idRiga == null) {
	    throw new RuntimeException("Impossibile aggiungere soggetti senza passare la riga di riferimento");
	}
	List<SoggettoPraticaModel> listSoggetti = popolaModelDaRequest(soggettipratica, anagrafiche, cariche);
	try {
	    this.commissioniDettaglioService.gestisciSoggettiIstanza(idRiga, listSoggetti);
	    response.getOutputStream().write("OK".getBytes());
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getOutputStream().write(e.getMessage().getBytes());
	}
	response.setContentType("text/plain");
	response.getOutputStream().flush();
    }

    private List<SoggettoPraticaModel> popolaModelDaRequest(String soggettipratica, String anagrafiche, String cariche) {

	List<Integer> codiciAnagrafe = new ArrayList<Integer>();
	populateList(codiciAnagrafe, anagrafiche);
	List<Integer> codiciCariche = new ArrayList<Integer>();
	populateList(codiciCariche, cariche);
	List<Integer> codiciSoggetti = new ArrayList<Integer>();
	populateList(codiciSoggetti, soggettipratica);
	List<SoggettoPraticaModel> ret = new ArrayList<SoggettoPraticaModel>(codiciSoggetti.size());
	for (int i = 0; i < codiciSoggetti.size(); i++) {
	    SoggettoPraticaModel spm = new SoggettoPraticaModel();
	    spm.setCodiceAnagrafe(codiciSoggetti.get(i));
	    if (codiciAnagrafe.get(i) > 0) {
		spm.setSelezionato(true);
	    }
	    if (codiciCariche.get(i) > 0) {
		spm.setCodiceCarica(codiciCariche.get(i));
	    }
	    ret.add(spm);
	}
	return ret;
    }

    private void populateList(List<Integer> list, String proprieta) {

	if (StringUtils.isNotEmpty(proprieta)) {
	    proprieta = proprieta.replaceAll("^,", "-1,").replaceAll(",,", ",-2,").replaceAll(",$", ",-3");
	    StringTokenizer t = new StringTokenizer(proprieta, ",");
	    int i = 0;
	    while (t.hasMoreTokens()) {
		String nt = t.nextToken();
		if (StringUtils.isNotBlank(nt)) {
		    list.add(i, Integer.parseInt(nt));
		} else {
		    list.add(i, -Integer.parseInt(nt));
		}
		i++;
	    }
	} else {
	    list.add(-4);
	}
    }

    /**
     * Mostra tutte le istanze filtrate per data che possono essere discusse dalla commissione
     * 
     * @param model
     * @param commissioniediliziet
     * @param result
     * @param status
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String searchIstanzeInCommissione(Model model, @ModelAttribute("commissioniediliziet") CommissioniedilizieTCommand commissioniediliziet,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	CommissioniedilizieT entity = commissioniediliziet.getEntity();
	// recupera tutti i movimenti che possono essere discussi
	GenerateTable<Movimenti> istanzePerCommissioneEdiliziaTable = new IstanzePerCommissioneEdiliziaTable(commissioniediliziet.getDataFiltro(),
		entity);
	String htmlTable = istanzePerCommissioneEdiliziaTable.createJMesaList(request, response, "label.istanze_possibilita_discussione",
		"commissioniediliziet_id", false);
	if (htmlTable == null) {
	    return null;
	}
	int count = movimentiNoSecurityService.countMovimentiDaAssociareAllaCommissione(commissioniediliziet.getDataFiltro(), entity);
	model.addAttribute("htmltable", htmlTable);
	model.addAttribute("commissioniediliziet", commissioniediliziet);
	model.addAttribute("numeroIstanzeDaDiscutere", count);
	return "commissioniediliziet/listCommissioniedilizieRDiscutibili";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * Inserisce le istanze scelte tra quelle che possono essere discusse nella tabella COMMISSIONIEDILIZIE_R
     * 
     * @param model
     * @param commissioniediliziet
     * @param result
     * @param status
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String insertCommissioniedilizieR(Model model, @ModelAttribute("commissioniediliziet") CommissioniedilizieTCommand commissioniediliziet,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	CommissioniedilizieT entity = commissioniediliziet.getEntity();
	try {
	    if (StringUtils.isNotBlank(commissioniediliziet.getCodiciMovimentiScelti())) {
		String stringaDicodiciMovimento = commissioniediliziet.getCodiciMovimentiScelti();
		List<String> codiciMovimenti = Utilities.split(stringaDicodiciMovimento, ",");
		commissioniedilizieRService.insertMultiploCommissioniedilizieR(codiciMovimenti, entity);
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, entity, true, e);
	    fixRenderEntityProperty(entity);
	    model.addAttribute("commissioniedilizierList", entity.getCommissioniedilizieRs());
	    model.addAttribute("commissioniediliziet", commissioniediliziet);
	    return "commissioniediliziet/listCommissioniedilizieRDiscutibili";
	}
	model.addAttribute("commissioniediliziet", commissioniediliziet);
	return "redirect:listCommissioniedilizieR.htm?codiceCommissione=" + entity.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * Cancella una commissione edilizia r che era tra quelle da discutere
     * 
     * @param codiceCommissioneR
     * @param model
     * @param commissioniediliziet
     * @param result
     * @param status
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String deleteCommissioniedilizieR(@RequestParam("codiceCommissioneR") Integer codiceCommissioneR, Model model,
	    @ModelAttribute("commissione") DettaglioCommissioneModel commissioniediliziet, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	CommissioniedilizieT entity = commissioniedilizietService.findById(new PkId(commissioniediliziet.getId()));
	CommissioniedilizieR objectDlete = commissioniedilizieRService.findById(new PkId(codiceCommissioneR));
	try {
	    commissioniedilizieRService.delete(objectDlete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, entity, true, e);
	    fixRenderEntityProperty(entity);
	    model.addAttribute("commissioniedilizierList", entity.getCommissioniedilizieRs());
	    model.addAttribute("commissioniediliziet", commissioniediliziet);
	    return "commissioniediliziet/listCommissioniedilizieR";
	}
	model.addAttribute("commissioniediliziet", commissioniediliziet);
	return "redirect:listCommissioniedilizieR.htm?codiceCommissione=" + entity.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * <pre>
     * Presenta una maschera popolata con i campi per la gestione della discussione di una commissione edilizia r 
     * 	1-Presenta la lista dei partecipanti e consente a chi ha diritto di voto di esprimerlo
     * 	2- Salvare il tipo di parere dato dalla commissione
     * 	3- Salvare la la descrizione del parere
     * 
     * &#64;param codiceCommissioneR
     * &#64;param model
     * &#64;param request
     * &#64;param response
     * &#64;return
     * &#64;throws IOException
     * </pre>
     */
    @RequestMapping
    public String createEsitoCommissioniedilizieR(@RequestParam("codiceCommissioneR") Integer codiceCommissioneR,
	    @RequestParam(required = false, value = "ordine") Integer ordine,
	    @RequestParam(required = false, value = "codiceCommissioneT") Integer codiceCommissioneT, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	CommissioniedilizieTCommand commissioniediliziet = new CommissioniedilizieTCommand();
	// setto la commissione edilizia R che si deve discutere
	CommissioniedilizieR commissioniedilizieR = commissioniedilizieRService.findById(new PkId(codiceCommissioneR));
	commissioniediliziet.setCommissioniedilizieR(commissioniedilizieR);
	// setto la commissione edilizia 
	CommissioniedilizieT entity = commissioniedilizieR.getCommissioniedilizieT();
	commissioniediliziet.setEntity(entity);
	// setto la lista dell'appello
	// setto la tipologia dei pareri
	List<CommedilizieTipopareri> listaTipopareri = commedilizieTipopareriService.findAll(null, null);
	List<CommedilizieVotiBase> listVoti = commedilizieVotiBaseService.findAll(null, null);
	commissioniediliziet.setListTipopareri(listaTipopareri);
	// Se ordine e codiceCommissioneT (significa che stiamo discutendo una nuova commissioneediliziar);
	// recupero la lista dell'ultima commissioneediliziar discussa da cui prendo la lista commedilizieVotazionis 
	// se non ci sono commissioniedilizier discusse allora predo quella base salvata in commedilizieappello
	List<CommedilizieVotazioni> listAppello = commedilizieVotazioniService.findAppelloByCommissioniedilizieR(commissioniedilizieR);
	if (ordine != null && codiceCommissioneT != null) { // Se ordine e codiceCommissioneT sono presenti come parametri (significa che stiamo discutendo una nuova commissioneediliziar);
	    if (listAppello.isEmpty()) {
		listAppello = commedilizieVotazioniService.findByCommissioneOrdinePrecedenteDiscussa(commissioniedilizieR, codiceCommissioneT,
			ordine);
	    }
	}
	verificaInserisciNuoviConvocati(listAppello, commissioniedilizieR);
	commissioniediliziet.setListaAppello(listAppello);
	boolean oggettoPresente = false;
	if (BooleanUtils.isFalse(entity.getCommedilizieTipologie().getFlagUploadDocParere())) {
	    // verifico se ci sono allegati anche se la tipologia non prevede allegati
	    // questi possono comunque essere inviati da front
	    for (CommedilizieVotazioni commedilizieVotazioni : listAppello) {
		if (commedilizieVotazioni.getCodiceoggetto() != null) {
		    oggettoPresente = true;
		    break;
		}
	    }
	}
	model.addAttribute("visualizzaAllegatiConTipologiaDocParereFalse", Boolean.valueOf(oggettoPresente));
	List<Mailtipo> mailtipos = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.CONFERENZE);
	fixRenderCommedilizieVotazioniProperty(commissioniediliziet);
	model.addAttribute("commissioniediliziet", commissioniediliziet);
	model.addAttribute("listVoti", listVoti);
	model.addAttribute("mailtipos", mailtipos);
	request.setAttribute("numeroPresenti", listAppello.size());
	return "commissioniediliziet/formEsito";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * <pre>
     * Presenta una maschera popolata con i campi per la gestione della discussione di una commissione edilizia r 
     * 	1-Presenta la lista dei partecipanti e consente a chi ha diritto di voto di esprimerlo
     * 	2- Salvare il tipo di parere dato dalla commissione
     * 	3- Salvare la la descrizione del parere
     * 
     * &#64;param codiceCommissioneR
     * &#64;param model
     * &#64;param request
     * &#64;param response
     * &#64;return
     * &#64;throws IOException
     * </pre>
     */
    @RequestMapping
    public void ajaxSalvaDiscussioneEsitoCommissioniedilizieR(
	    @ModelAttribute("commissioniediliziet") CommissioniedilizieTCommand commissioniediliziet, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	// recupero la commissione che andrò a modificare
	List<CommedilizieVotazioni> list = commissioniediliziet.getListaAppello();
	List<CommedilizieVotazioni> commedilizieVotazionis = new ArrayList<CommedilizieVotazioni>();
	for (CommedilizieVotazioni commedilizieVotazioni : list) {
	    if (commedilizieVotazioni.getTransientVoto() != null) {
		CommedilizieVotiBase voto = commedilizieVotiBaseService.findById(commedilizieVotazioni.getTransientVoto());
		commedilizieVotazioni.setCommedilizieVotiBase(voto);
		commedilizieVotazionis.add(commedilizieVotazioni);
	    } else {
		CommedilizieVotiBase voto = new CommedilizieVotiBase();
		commedilizieVotazioni.setCommedilizieVotiBase(voto);
		commedilizieVotazionis.add(commedilizieVotazioni);
	    }
	}
	try {
	    commedilizieVotazioniService.salvaVotazioni(commedilizieVotazionis);
	    response.getOutputStream().write("OK".getBytes());
	} catch (Exception e) {
	    response.getOutputStream().write(e.getMessage().getBytes());
	}
    }

    @RequestMapping
    public String ajaxSezioneAllegati(@RequestParam("id_allegato") Integer id_allegato, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	CommedilizieVotazioni votazione = commedilizieVotazioniService.findById(new PkId(id_allegato));
	model.addAttribute("votazione", votazione);
	model.addAttribute("sola_lettura", request.getParameter("sola_lettura"));
	model.addAttribute("tipologiaUploadParere", request.getParameter("tipologiaUploadParere"));
	return "commissioniediliziet/ajaxSezioneAllegati";
    }

    /**
     * Quando vado all'esito verifico se chiuso non faccio niente altrimenti inserisco le righe di appello non presenti
     * (popolate da commedilizieVotazioniService.findAppelloByCommissioniedilizieR(commissioniedilizieR);)
     * 
     * @param listAppello
     * @param commissioniedilizieR
     */
    private void verificaInserisciNuoviConvocati(List<CommedilizieVotazioni> listAppello, CommissioniedilizieR commissioniedilizieR) {

	if (commissioniedilizieR.getMovimentoRientro() != null && commissioniedilizieR.getMovimentoRientro().getId() != null
		&& commissioniedilizieR.getMovimentoRientro().getId().getCodice() != null) {
	    return;
	}
	for (CommedilizieVotazioni commedilizieVotazioni : listAppello) {
	    if (commedilizieVotazioni.getId() != null && commedilizieVotazioni.getId().getCodice() == null) {
		commedilizieVotazioniService.insert(commedilizieVotazioni);
	    }
	}
    }

    /**
     * Inserisci le informazioni inseguito alla discussione di una commissione edilizia r
     * 
     * @param model
     * @param commissioniediliziet
     * @param result
     * @param status
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String insertEsitoCommissioniedilizieR(Model model,
	    @ModelAttribute("commissioniediliziet") CommissioniedilizieTCommand commissioniediliziet, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	// recupero la commissione che andrò a modificare
	CommissioniedilizieR commissioniedilizieR = commissioniediliziet.getCommissioniedilizieR();
	// recupero il tipo parere passato tramite il form
	if (commissioniediliziet.getCommissioniedilizieR().getCommedilizieTipopareri() != null
		&& commissioniediliziet.getCommissioniedilizieR().getCommedilizieTipopareri().getId().getCodice() != null) {
	    CommedilizieTipopareri commedilizieTipopareri = commedilizieTipopareriService
		    .findById(new PkId(commissioniediliziet.getCommissioniedilizieR().getCommedilizieTipopareri().getId().getCodice()));
	    commissioniedilizieR.setCommedilizieTipopareri(commedilizieTipopareri);
	}
	// creo le tutti  i record comm edilizie votazioni che andrò ad inserire parallelamente alla modifica
	// della commissione edilizia r.
	//(Saranno formate dai record su commedilizie votazioni filtrate per commissioni edilizia_r e quelli mancanti 
	//recuperati dalla lista di commedilizie appello filtrati per commissione edilizia t)
	List<CommedilizieVotazioni> list = commissioniediliziet.getListaAppello();
	List<CommedilizieVotazioni> commedilizieVotazionis = new ArrayList<CommedilizieVotazioni>();
	for (CommedilizieVotazioni commedilizieVotazioni : list) {
	    if (commedilizieVotazioni.getTransientVoto() != null) {
		CommedilizieVotiBase voto = commedilizieVotiBaseService.findById(commedilizieVotazioni.getTransientVoto());
		commedilizieVotazioni.setCommedilizieVotiBase(voto);
		commedilizieVotazionis.add(commedilizieVotazioni);
	    } else {
		CommedilizieVotiBase voto = new CommedilizieVotiBase();
		commedilizieVotazioni.setCommedilizieVotiBase(voto);
		commedilizieVotazionis.add(commedilizieVotazioni);
	    }
	}
	try {
	    commissioniedilizieRService.updateEsitoCommissioneediliziaR(commissioniedilizieR, commedilizieVotazionis,
		    commissioniediliziet.getParere());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commissioniediliziet.getCommissioniedilizieR(), true, "commissioniedilizieR", e);
	    fixRenderCommedilizieVotazioniProperty(commissioniediliziet);
	    List<CommedilizieVotiBase> listVoti = commedilizieVotiBaseService.findAll(null, null);
	    List<Mailtipo> mailtipos = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.CONFERENZE);
	    List<CommedilizieVotazioni> listAppello = commedilizieVotazioniService.findAppelloByCommissioniedilizieR(commissioniedilizieR);
	    model.addAttribute("listVoti", listVoti);
	    model.addAttribute("mailtipos", mailtipos);
	    request.setAttribute("numeroPresenti", listAppello.size());
	    return "commissioniediliziet/formEsito";
	}
	return "redirect:listCommissioniedilizieR.htm?codiceCommissione=" +
		commissioniedilizieR.getCommissioniedilizieT().getId().getCodice() +
		"&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * Cancella la discussione di una commissione edilizia r e la rende di nuovo discutibile
     * 
     * @param model
     * @param commissioniediliziet
     * @param result
     * @param status
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String deleteEsitoCommissioniedilizieR(Model model,
	    @ModelAttribute("commissioniediliziet") CommissioniedilizieTCommand commissioniediliziet, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	// recupero la commissione a cui andrò a cancellare l'esito
	CommissioniedilizieR commissioniedilizieR = commissioniediliziet.getCommissioniedilizieR();
	try {
	    commissioniedilizieRService.deleteEsitoCommissioneediliziaR(commissioniedilizieR);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commissioniediliziet, e);
	    fixRenderCommedilizieVotazioniProperty(commissioniediliziet);
	    List<CommedilizieVotiBase> listVoti = commedilizieVotiBaseService.findAll(null, null);
	    List<Mailtipo> mailtipos = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.CONFERENZE);
	    List<CommedilizieVotazioni> listAppello = commedilizieVotazioniService.findAppelloByCommissioniedilizieR(commissioniedilizieR);
	    model.addAttribute("listVoti", listVoti);
	    model.addAttribute("mailtipos", mailtipos);
	    request.setAttribute("numeroPresenti", listAppello.size());
	    return "commissioniediliziet/formEsito";
	}
	return "redirect:listCommissioniedilizieR.htm?codiceCommissione=" +
		commissioniedilizieR.getCommissioniedilizieT().getId().getCodice() +
		"&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * Riordina il campo ordine per tutte le commissioni r discusse
     * 
     * @param model
     * @param commissione
     * @param result
     * @param status
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String riordinaCommissioniedilizieR(Model model, @ModelAttribute("commissione") DettaglioCommissioneModel commissione,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException {

	try {
	    boolean ripetizioni = analizzaLista(commissione.getRighe());
	    if (ripetizioni) {
		List<String> errorivalidazione = new ArrayList<String>();
		errorivalidazione.add("Attenzione sono presenti più istanze con lo stesso numero di ordine!");
		FlashMessages.setWarnings(errorivalidazione);
		return "redirect:listCommissioniedilizieR.htm?codiceCommissione=" + commissione.getId();
	    }
	    commissioniedilizieRService.ordinaEsitoCommissioneediliziaR(commissione.getId(), commissione.getNumeroProtocollo(),
		    commissione.getRighe());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commissione, e);
	    List<CommedilizieVotiBase> listVoti = commedilizieVotiBaseService.findAll(null, null);
	    List<Mailtipo> mailtipos = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.CONFERENZE);
	    model.addAttribute("listVoti", listVoti);
	    model.addAttribute("mailtipos", mailtipos);
	}
	return "redirect:listCommissioniedilizieR.htm?codiceCommissione=" + commissione.getId() + "&status_msg=02";
    }

    private boolean analizzaLista(List<RigaCommissioneModel> righe) {

	Integer[] ordini = new Integer[righe.size()];
	int i = 0;
	for (RigaCommissioneModel riga : righe) {
	    ordini[i] = riga.getOrdine();
	    i++;
	}
	if (new HashSet<Integer>(Arrays.asList(ordini)).size() != righe.size()) {
	    return true;
	}
	return false;
    }

    @Override
    protected void fixMergeEntityProperty(CommissioniedilizieT entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CommissioniedilizieT entity) {

	if (entity.getCommedilizieTipologie() == null) {
	    entity.setCommedilizieTipologie(new CommedilizieTipologie());
	}
    }

    private void fixRenderCommedilizieVotazioniProperty(CommissioniedilizieTCommand commissioniediliziet) {

	List<CommedilizieVotazioni> listAppello = commissioniediliziet.getListaAppello();
	for (CommedilizieVotazioni commedilizieVotazioni : listAppello) {
	    if (commedilizieVotazioni.getCommedilizieVotiBase() != null) {
		commedilizieVotazioni.setTransientVoto(commedilizieVotazioni.getCommedilizieVotiBase().getId());
	    }
	}
	commissioniediliziet.setListaAppello(listAppello);
	if (commissioniediliziet.getMailtipo() == null) {
	    commissioniediliziet.setMailtipo(new Mailtipo());
	}
	fixRenderCommissioniedilizieRProperty(commissioniediliziet.getCommissioniedilizieR());
    }

    private void fixRenderCommissioniedilizieRProperty(CommissioniedilizieR entity) {

	if (entity.getCommedilizieTipopareri() == null) {
	    entity.setCommedilizieTipopareri(new CommedilizieTipopareri());
	}
	if (entity.getMovimentoRientro() == null) {
	    entity.setMovimentoRientro(new Movimenti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @RequestMapping
    public String listCommissioniDelMovimento(Model model, @RequestParam(value = "codiceMovimento") Integer codiceMovimento,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	Set<Integer> listaCommissioniDelMovimento = commissioniedilizieRService.findCodiciCommissioniByMovimento(codiceMovimento);
	Movimenti mov = movimentiNoSecurityService.findById(new PkId(codiceMovimento));
	boolean flagCDS = movimentiNoSecurityService.isMovimentoCDS(mov);
	if (flagCDS && commissioniedilizieRService.countCommissioniByIstanza(mov.getIstanza().getId().getCodice()) == 0) {
	    // Posso creare una sola CDS per Istanza
	    if (listaCommissioniDelMovimento.isEmpty()) {
		FlashMessages.getWarnings().add("Ricordarsi di salvare i dati");
		CommissioneModel cmodel = commissioniService.populateModelFromMovimento(codiceMovimento);
		model.addAttribute("commissione", cmodel);
		setPageAttributes(model);
		return FORM;
	    }
	}
	if (listaCommissioniDelMovimento.size() == 1) {
	    return "redirect:view.htm?codice=" + listaCommissioniDelMovimento.iterator().next();
	}
	throw new BusinessValidationException("Il movimento è presente in più commissioni con codici " + listaCommissioniDelMovimento);
    }

    @RequestMapping
    public String listCommissioniIstanza(Model model, @RequestParam(value = "codiceIstanza") Integer codiceIstanza, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	List<CommissioniedilizieT> listaCommissioniIstanza = commissioniedilizieRService.findCommissioniByIstanza(codiceIstanza);
	if (listaCommissioniIstanza.size() == 1) {
	    return "redirect:view.htm?codice=" + listaCommissioniIstanza.iterator().next().getId().getCodice();
	}
	model.addAttribute("listaCommissioniIstanza", listaCommissioniIstanza);
	model.addAttribute("codiceIstanza", codiceIstanza);
	return "commissioniediliziet/commissioniperistanza";
    }

    @RequestMapping
    public void createLetteraTipo(@RequestParam(value = "codiceCommissione") Integer codiceCommissione,
	    @RequestParam(value = "codiceLettera") Integer codiceLettera, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	byte[] out = null;
	Date date = new Date();
	Letteretipo letteretipo = letteretipoService.findById(new PkId(codiceLettera));
	String cType = contenttypesService.findMimeTypeByFileName(letteretipo.getFile().getNomefile());
	it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper userData = new DocumentMergeHelper();
	userData.addParam("CODICECOMMISSIONE", String.valueOf(codiceCommissione));
	out = documentMergeService.eseguiSostituzioniBaseDocumento(codiceLettera, userData);
	response.setHeader("Pragma", "public");
	response.setHeader("Cache-Control", "max-age=0");
	response.setHeader("Content-transfer-encoding", "binary");
	response.setContentLength(out.length);
	response.setContentType(cType);
	if (letteretipo.getFile().getNomefile().toLowerCase().endsWith(".odt")) {
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + date.getTime() + "_" + letteretipo.getDescrizione() + ".odt");
	} else if (letteretipo.getFile().getNomefile().toLowerCase().endsWith(".rtf")) {
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + date.getTime() + "_" + letteretipo.getDescrizione() + ".rtf");
	}
	ServletOutputStream outStream = response.getOutputStream();
	outStream.write(out);
	outStream.flush();
    }
}
