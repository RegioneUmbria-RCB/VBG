package it.gruppoinit.pal.gp.backoffice.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

import com.paevolution.ws.pagamenti_types.StatoPagamentoType;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.IstoneriDettPosizioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.helper.OneriEntrateUsciteAmministrazioneHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.PosizioniDebitorieIstanzeoneriBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.VerificaStatoPosizioniDebitorie;
import it.gruppoinit.pal.gp.core.features.oneri.ChiavePerCausaleDatPagamentoDataScadenzaTipologia;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeOneriListModel;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.CanoniConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.CcValiditacoefficientiService;
import it.gruppoinit.pal.gp.core.service.FidejussionestatiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzefidejussioniService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.OValiditacoefficientiService;
import it.gruppoinit.pal.gp.core.service.RaggruppamentocausalioneriService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipicausalioneridettaglioService;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes(value = { "istanzeoneri", "istanzeoneriHelper" })
public class IstanzeoneriController extends BaseController<Istanzeoneri> {

    @Autowired
    private IstanzeoneriService istanzeoneriService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private TipicausalioneriService tipicausalioneriService;
    @Autowired
    private TipimodalitapagamentoService tipimodalitapagamentoService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private CcValiditacoefficientiService ccValiditacoefficientiService;
    @Autowired
    private OValiditacoefficientiService oValiditacoefficientiService;
    @Autowired
    private CanoniConfigurazioneService canoniConfigurazioneService;
    @Autowired
    private IstanzefidejussioniService istanzefidejussioniService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private NodoPagamentiService nodoPagamentiService;
    @Autowired
    private RaggruppamentocausalioneriService raggruppamentocausalioneriService;
    @Autowired
    private IstanzerichiedentiService istanzerichiedentiService;
    @Autowired
    private FidejussionestatiService fidejussionestatiService;
    @Autowired
    private TipicausalioneridettaglioService tipicausalioneridettaglioService;
    private static final Logger log = LoggerFactory.getLogger(IstanzeoneriController.class);

    @RequestMapping
    public ModelMap list(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "isModifica", required = false) String isModifica, HttpServletRequest request, HttpServletResponse response) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	// Controlla per questa istanza sono attivati gli oneri
	checkAccessoIstanzeOneri(istanza, false);
	// Setta la configurazione utente sul tipo di visualizzazione che si vuole avere delgli oneri :
	//1- Raggruppati (per tipo raggruppamento).
	//2- Dettaglio.
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ONERI_RAGGRUPPATI, "0", request);
	Map<ChiavePerCausaleDatPagamentoDataScadenzaTipologia, List<Istanzeoneri>> istanzeoneriListDettaglioOrRaggruppamento;
	//A seconda del tipo di visualizzazione vengono recuperate le informazioni
	if (StringUtils.isBlank((String) request.getAttribute(WebConstants.CONF_UTENTE_ONERI_RAGGRUPPATI))
		|| request.getAttribute(WebConstants.CONF_UTENTE_ONERI_RAGGRUPPATI).equals("0")) {
	    // Recupera le informazioni in modo che ogni elemento della lista contenga un oggetto composto da:
	    // 1- Una lista di istanzeoneri con lo stesso tipi di raggruppamento.
	    // 2- Il totale dell'importo degli oneri causali da pagare.
	    // 3- Il totale dell'importo delle oneri di istruttorie da pagare.
	    istanzeoneriListDettaglioOrRaggruppamento = istanzeoneriService.findDettaglioOneri(istanza);
	} else {
	    istanzeoneriListDettaglioOrRaggruppamento = istanzeoneriService.findRaggruppamentoOneri(istanza);
	}
	ModelMap model = new ModelMap(istanzeoneriListDettaglioOrRaggruppamento);
	//boolean export = createJMesaExport(request, response, istanzeoneriListDettaglioOrRaggruppamento);
	//if (export) {
	//    return null;
	//}
	// Gestione del controllo Avvertimenti. Se C'è un uscita verso un' amministrazione maggiore di un entrata sempre 
	//per la stessa amministrazione deve essere presente sulla pagina un avvertimento che lo notifica.
	List<OneriEntrateUsciteAmministrazioneHelper> oneriEntrateUsciteAmministraziones = istanzeoneriService
		.findOneriEntrateUsciteForAmministrazione(istanza);
	prepareList(request, model, istanza);
	model.addAttribute("oneriEntrateUsciteAmministraziones", oneriEntrateUsciteAmministraziones);
	model.addAttribute("istanzeOneriListModel", new IstanzeOneriListModel(istanzeoneriListDettaglioOrRaggruppamento));
	model.addAttribute("istanza", istanza);
	model.addAttribute("istanzeoneri", new Istanzeoneri());
	if (StringUtils.isNotBlank(isModifica)) {
	    model.addAttribute("isModifica", new Boolean(isModifica));
	} else {
	    model.addAttribute("isModifica", new Boolean(false));
	}
	// Variabile per controllare se mostrare il bottone "Calcolo oneri" (deve esistere almeno un record sulla tabella CC_VALIDITACOEFFICIENTI)
	boolean isExistRecord = ccValiditacoefficientiService.existRecordByCurrentSoftware();
	model.addAttribute("isExistRecord", isExistRecord);
	boolean isExistRecordOvalidita = oValiditacoefficientiService.existRecordByCurrentSoftware();
	model.addAttribute("isExistRecordOvalidita", isExistRecordOvalidita);
	boolean isExistRecordIstanzeFidejussioni = istanzefidejussioniService.existRecordByIstanza(codiceIstanza)
		|| fidejussionestatiService.existRecordsByCurrentSoftware();
	model.addAttribute("isExistRecordIstanzeFidejussioni", isExistRecordIstanzeFidejussioni);
	boolean isExistRecordCanoniConfigurazione = canoniConfigurazioneService.existRecordByCurrentSoftware();
	model.addAttribute("isExistRecordCanoniConfigurazione", isExistRecordCanoniConfigurazione);
	return model;
    }

    @RequestMapping
    public void ajaxChangeOnereValue(@RequestParam("codice") Integer codice, @RequestParam("valore") String valore,
	    @RequestParam("campo") String campo, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Istanzeoneri istanzeoneri = istanzeoneriService.findById(new PkId(codice));
	String messaggio = "";
	try {
	    String resp = "{\"msg\":\"@msg\",\"msgIntMora\":\"@msgIntMora\",\"errore\":\"@errore\"}";
	    checkAccessoIstanzeOneri(istanzeoneri.getIstanza(), true);
	    String risultato = istanzeoneriService.update(istanzeoneri, valore, campo);
	    //verifica interessi di mora
	    messaggio = istanzeoneriService.calcolaInteressiDiMora(istanzeoneri.getId().getCodice());
	    if (StringUtils.contains(messaggio, "Errore")) {
		resp = resp.replace("@errore", messaggio);
		resp = resp.replace("@msgIntMora", "");
	    } else {
		resp = resp.replace("@errore", "");
		resp = resp.replace("@msgIntMora", messaggio);
	    }
	    response.setContentType("application/json");
	    if (risultato.equals("OK")) {
		resp = resp.replace("@msg", getMessageFromBundle("label.datoaggiornato", null));
		response.getWriter().write(resp);
	    } else {
		resp = resp.replace("@msg", risultato);
		resp = resp.replace("@errore", risultato);
		response.getWriter().write(resp);
	    }
	} catch (Exception e) {
	    String resp = "{\"msg\":\"@msg\",\"msgIntMora\":\"@msgIntMora\",\"errore\":\"@errore\"}";
	    resp = resp.replace("@errore", e.getMessage() != null ? e.getMessage() : "");
	    resp = resp.replace("@msg", "");
	    resp = resp.replace("@msgIntMora", "");
	    response.setStatus(500);
	    response.getWriter().write(resp);
	    e.printStackTrace();
	}
    }

    //    /**
    //     * Il metodo fa il roolback dell'ultimo salvataggio effettutato in caso di errore
    //     * 
    //     * @param request
    //     * @param response
    //     * @return
    //     */
    //    @RequestMapping
    //    public String clear(HttpServletRequest request, HttpServletResponse response) {
    //
    //	HttpSession session = request.getSession();
    //	Integer codice = (Integer) session.getAttribute("codice");
    //	String valore = (String) session.getAttribute("oldvalue");
    //	String campo = (String) session.getAttribute("campo");
    //	clearSession(request);
    //	//	Integer codice = (Integer) request.getAttribute("codice");
    //	Istanzeoneri istanzeoneri = istanzeoneriService.findById(new PkId(codice));
    //	//try {
    //	//istanzeoneriService.update(istanzeoneri, valore, campo);
    //	try {
    //	    istanzeoneriService.update(istanzeoneri, valore, campo);
    //	} catch (Exception e) {
    //	    // TODO Auto-generated catch block
    //	    e.printStackTrace();
    //	}
    //	return "redirect:list.htm?codiceIstanza=" + istanzeoneri.getIstanza().getId().getCodice() + "&isModifica=false&status_msg=02";
    //    }
    @RequestMapping
    public String create(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request) throws Exception {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoIstanzeOneri(istanza, true);
	// Controlla per questa istanza sono attivati gli oneri
	Istanzeoneri istanzeoneri = new Istanzeoneri();
	istanzeoneri.setIstanza(istanza);
	fixRenderEntityProperty(istanzeoneri);
	model.addAttribute("istanza", istanza);
	model.addAttribute("istanzeoneri", istanzeoneri);
	prepareView(request, istanzeoneri, model);
	setPageAttributes(model, istanza.getComune().getCodicecomune());
	return "istanzeoneri/form";
    }

    private void prepareList(HttpServletRequest request, ModelMap model, Istanze istanza) {

	// Gestione della visualizzazione colonna Amministrazione
	boolean viewAmministrazioneAndEndo = false;
	viewAmministrazioneAndEndo = istanzeoneriService.isExistIstanzeOnereWithEndo(istanza);
	if (viewAmministrazioneAndEndo) {
	    viewAmministrazioneAndEndo = true;
	}
	model.addAttribute("viewAmministrazioneAndEndo", viewAmministrazioneAndEndo);
	// Gestione della visualizzazione colonna uscita e ribasso
	boolean viewUscitaAndRibasso = false;
	viewUscitaAndRibasso = istanzeoneriService.isExistIstanzeOnereWithUscita(istanza);
	if (viewUscitaAndRibasso) {
	    viewUscitaAndRibasso = true;
	    model.addAttribute("viewUscitaAndRibasso", viewUscitaAndRibasso);
	}
	// Gestisce il controllo se la lista dettaglio se visulaizzare quella con uscite o no
	boolean isExsistUscite = istanzeoneriService.isExistIstanzeOnereWithUscita(istanza);
	model.addAttribute("isExsistUscite", isExsistUscite);
    }

    private void prepareView(HttpServletRequest request, Istanzeoneri istanzeoneri, Model model) throws Exception {

	List<Tipimodalitapagamento> tipimodalitapagamentos = tipimodalitapagamentoService.findAll(null, null, false);
	model.addAttribute("tipimodalitapagamentos", tipimodalitapagamentos);
	model.addAttribute("istanza", istanzeoneri.getIstanza());
	model.addAttribute("istanzeoneri", istanzeoneri);
	if (istanzeoneri.getDatapagamento() != null) {
	    request.setAttribute("pagato", istanzeoneri.getDatapagamento());
	}
	VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamenti = new VerticalizzazioneNodoPagamentiServiceImpl(
		verticalizzazioniService, istanzeoneri.getIstanza().getComune().getCodicecomune());
	model.addAttribute("visualizzaInviaNodoPagamenti",
		istanzeoneri.isPosizionePagabileTramiteNodoPagamenti(verticalizzazioneNodoPagamenti, tipicausalioneridettaglioService));
	List<Istanzeoneri> istanzeOneri = null;
	istanzeOneri = nodoPagamentiService.findOneriInviabiliANodoPagamenti(istanzeoneri.getIstanza().getId().getCodice(),
		istanzeoneri.getTipicausalioneri().getId().getCodice());
	model.addAttribute("listaIstanzeOneri", istanzeOneri);
	model.addAttribute("mostra_salva_elimina", Boolean.valueOf(!istanzeoneri.isPresentiPosizioniDebitorie()));
	boolean creaPerSoggettiCollegati = verticalizzazioneNodoPagamenti.isAttiva() && verticalizzazioneNodoPagamenti.creaPerSoggettiCollegati();
	List<Istanzerichiedenti> istanzerichiedenti = istanzerichiedentiService.findByIstanza(istanzeoneri.getIstanza());
	model.addAttribute("creaPerSoggettiCollegati", creaPerSoggettiCollegati);
	model.addAttribute("istanzerichiedenti", istanzerichiedenti);
	// Gestione della visualizzazione sul form dei campi endoprocedimento e amministrazione
	if (EntityUtils.getNestedProperty(istanzeoneri.getTipicausalioneri(), "id.codice") != null
		&& istanzeoneri.getTipicausalioneri().getCoSerichiedeendo() != null
		&& istanzeoneri.getTipicausalioneri().getCoSerichiedeendo().equals(true)) {
	    request.setAttribute("viewEndoAndAmministrazione", true);
	} else {
	    request.setAttribute("viewEndoAndAmministrazione", false);
	}
	// Gestione visualizzazione campi per  entrata/uscita
	request.setAttribute("entrata_uscita", istanzeoneri.getFlentratauscita());
	// Gestisce la visualizzazione del campo "importo istruttoria",deve essere visibile se e solo se
	// 1- Esiste almeno un onere legato a un endoprocedimento o è attiva e messa a 1 la vericalizzazione 
	// VIS_ONERIIMPORTOISTRUTTORIA
	boolean isMostraImportoIstruttoria = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_VIS_ONERIIMPORTOISTRUTTORIA);
	//	if (!isMostraImportoIstruttoria) {
	//	    int cout = istanzeoneriService.countOneriByIstanza(istanza.getId().getCodice(), true);
	//	    isMostraImportoIstruttoria = (cout > 0 ? true : false);
	//	}
	request.setAttribute("isMostraImportoIstruttoria", isMostraImportoIstruttoria);
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("istanzeoneri") Istanzeoneri istanzeoneri, BindingResult result, SessionStatus status,
	    HttpServletRequest request) throws Exception {

	fixMergeEntityProperty(istanzeoneri);
	if (EntityUtils.getNestedProperty(istanzeoneri.getTipicausalioneri(), "id.codice") != null) {
	    Tipicausalioneri tipicausalioneri = tipicausalioneriService.findById(istanzeoneri.getTipicausalioneri().getId());
	    istanzeoneri.setTipicausalioneri(tipicausalioneri);
	}
	if (EntityUtils.getNestedProperty(istanzeoneri.getTipomovimento(), "id.tipomovimento") != null) {
	    Tipimovimento tipimovimento = tipiMovimentoService.findById(istanzeoneri.getTipomovimento().getId());
	    istanzeoneri.setTipomovimento(tipimovimento);
	}
	if (EntityUtils.getNestedProperty(istanzeoneri.getInventarioprocedimenti(), "id.codice") != null) {
	    Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(istanzeoneri.getInventarioprocedimenti().getId());
	    istanzeoneri.setInventarioprocedimenti(inventarioprocedimenti);
	}
	if (EntityUtils.getNestedProperty(istanzeoneri.getAmministrazioni(), "id.codice") != null) {
	    Amministrazioni amministrazioni = amministrazioniService.findById(istanzeoneri.getAmministrazioni().getId());
	    istanzeoneri.setAmministrazioni(amministrazioni);
	}
	checkAccessoIstanzeOneri(istanzeoneri.getIstanza(), true);
	try {
	    istanzeoneriService.insert(istanzeoneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeoneri, e);
	    fixRenderEntityProperty(istanzeoneri);
	    prepareView(request, istanzeoneri, model);
	    request.setAttribute("isPrimoingresso", "");
	    istanzeoneri.setTipicausalioneri(new Tipicausalioneri());
	    istanzeoneri.setFlentratauscita(true);
	    model.addAttribute("istanzeoneri", istanzeoneri);
	    return "istanzeoneri/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzeoneri.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) throws Exception {

	PkId id = new PkId(codice);
	Istanzeoneri istanzeoneri = istanzeoneriService.findById(id);
	checkAccessoIstanzeOneri(istanzeoneri.getIstanza(), false);
	fixRenderEntityProperty(istanzeoneri);
	prepareView(request, istanzeoneri, model);
	setPageAttributes(model, istanzeoneri.getIstanza().getComune().getCodicecomune());
	return "istanzeoneri/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("istanzeoneri") Istanzeoneri istanzeoneri, BindingResult result, SessionStatus status,
	    HttpServletRequest request) throws Exception {

	fixMergeEntityProperty(istanzeoneri);
	checkAccessoIstanzeOneri(istanzeoneri.getIstanza(), true);
	try {
	    //TODO verificare il numero rata
	    // verificaNumeroRata(istanzeoneri);
	    istanzeoneriService.update(istanzeoneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeoneri, e);
	    fixRenderEntityProperty(istanzeoneri);
	    prepareView(request, istanzeoneri, model);
	    return "istanzeoneri/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzeoneri.getId().getCodice() + "&status_msg=02";
    }

    //devo verificare che non sia già presente per quella causale, 
    //ad eccezione di oneri con posizioni debitorie chiuse negativamente o NON DOVUTI. 
    private void verificaNumeroRata(Istanzeoneri istanzeoneri) {

	Integer codiceCO = istanzeoneri.getTipicausalioneri().getId().getCodice();
	if (codiceCO != null) {
	    List<Istanzeoneri> listOneri = istanzeoneriService.findByIstanzaAndCausale(istanzeoneri.getIstanza().getId().getCodice(), codiceCO);
	    for (Istanzeoneri ioneri : listOneri) {
		if (!ioneri.getIstoneriDettPosizioni().isEmpty()) {
		    for (IstoneriDettPosizioni istDett : ioneri.getIstoneriDettPosizioni()) {
			//controllo se la posizione deb è chiusa oppure è onere non dovuto
			if (!(istDett.getDettPosizioneDebitoria().getStato().equals(StatoPagamentoType.ANNULLATO.name()))
				|| ioneri.getFlagNondovuto()) {
			    throw new BusinessValidationException(
				    "Non é possibile aggiornare il numero rata perché già presente un onere con quel numero rata");
			}
		    }
		}
	    }
	}
    }

    @RequestMapping
    public String ajaxRiferimentiPagamento(@RequestParam("codice") String codice, Model model, HttpServletResponse response) throws IOException {

	Istanzeoneri istanzeoneri = istanzeoneriService.findById(new PkId(Integer.parseInt(codice)));
	fixRenderEntityProperty(istanzeoneri);
	List<Tipimodalitapagamento> tipimodalitapagamentos = tipimodalitapagamentoService.findAll(null, null, false);
	model.addAttribute("tipimodalitapagamentos", tipimodalitapagamentos);
	model.addAttribute("istanzeoneriRiferimentiPagamento", istanzeoneri);
	log.debug("call dettaglio istanze oneri with codice: {}", codice);
	response.setContentType("text/plain");
	return "istanzeoneri/ajaxRiferimentiPagamento";
    }

    @RequestMapping
    public String updateRiferimentiPagamento(Model model, @ModelAttribute("istanzeoneriDettaglioPagamenti") Istanzeoneri istanzeoneri,
	    BindingResult result, SessionStatus status, HttpServletRequest request) throws Exception {

	//fixMergeEntityProperty(istanzeoneri);
	try {
	    Istanze istanza = istanzeService.findById(new PkId(istanzeoneri.getIstanza().getId().getCodice()));
	    checkAccessoIstanzeOneri(istanza, true);
	    istanzeoneriService.updateRiferimentiPagamento(istanzeoneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeoneri, e);
	    fixRenderEntityProperty(istanzeoneri);
	    prepareView(request, istanzeoneri, model);
	    model.addAttribute("istanzeoneriDettaglioPagamenti", istanzeoneri);
	    return "istanzeoneri/form";
	}
	model.addAttribute("istanzeoneri", istanzeoneri);
	status.setComplete();
	return "redirect:list.htm?codiceIstanza=" + istanzeoneri.getIstanza().getId().getCodice() + "&isModifica=true&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("istanzeoneri") Istanzeoneri istanzeoneri, BindingResult result, SessionStatus status) {

	Istanzeoneri objToDelete = istanzeoneriService.findById(istanzeoneri.getId());
	try {
	    Istanze istanza = istanzeService.findById(new PkId(objToDelete.getIstanza().getId().getCodice()));
	    checkAccessoIstanzeOneri(istanza, true);
	    istanzeoneriService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(objToDelete, false, null, e);
	    return "redirect:view.htm?codice=" + istanzeoneri.getId().getCodice() + "&status_msg=03";
	}
	status.setComplete();
	return "redirect:list.htm?codiceIstanza=" + objToDelete.getIstanza().getId().getCodice();
    }

    @RequestMapping
    public void ajaxIsCausaleCollegabileAdEndo(@RequestParam("codiceCausale") Integer codiceCausale, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	Tipicausalioneri tipicausalioneri = tipicausalioneriService.findById(new PkId(codiceCausale));
	try {
	    if (tipicausalioneri != null && tipicausalioneri.getCoSerichiedeendo().equals(true)) {
		response.getWriter().write("si");
	    } else {
		response.getWriter().write("no");
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	response.setContentType("text/plain");
    }

    @RequestMapping
    public void ajaxAmministrazioneByEndoprocedimento(@RequestParam("codiceEndo") Integer codiceEndo, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	Inventarioprocedimenti inventarioprocedimento = inventarioprocedimentiService.findById(new PkId(codiceEndo));
	try {
	    if (EntityUtils.getNestedProperty(inventarioprocedimento.getAmministrazioni(), "id.codice") != null) {
		response.getWriter().write(inventarioprocedimento.getAmministrazioni().getAmministrazione() + "," +
					   inventarioprocedimento.getAmministrazioni().getId().getCodice());
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	response.setContentType("text/plain");
    }

    @RequestMapping
    public void ajaxAggiornaStatoPagamenti(Model model, @RequestParam("idPosizioneDebitoria") Integer idPosizioneDebitoria,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	try {
	    VerificaStatoPosizioniDebitorie statoPosDeb = nodoPagamentiService.aggiornaStatoPagamentoByIdDettPosizioneDebitoria(idPosizioneDebitoria);
	    if (statoPosDeb != null && statoPosDeb.getIdPosizioneDebitoria() != null && statoPosDeb.getStatoAttuale() != null) {
		response.setContentType("text/plain");
		response.getOutputStream().write("OK".getBytes());
		response.getOutputStream().flush();
	    }
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("Errore aggiornamento stato posizione debitoria", e);
	}
    }

    @RequestMapping
    public void inserisciPosizionidebitorieDaIstanzeOneri(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam("codiceTipiCausali") Integer codiceTipiCausali, @RequestParam("isRateizzato") boolean isRateizzato,
	    @RequestParam(value = "codiceSoggettoCollegato[]", required = false) List<Integer> codiceSoggettoCollegato, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	Set<Integer> codiciIstanzeOneri = new HashSet<Integer>();
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoIstanzeOneri(istanza, true);
	List<Istanzeoneri> istanzeOneri = nodoPagamentiService.findOneriInviabiliANodoPagamenti(codiceIstanza, codiceTipiCausali);
	for (Istanzeoneri istanzaonere : istanzeOneri) {
	    codiciIstanzeOneri.add(istanzaonere.getId().getCodice());
	}
	List<String> erroriValidazione = nodoPagamentiService.validaInserimentoPosizioniDebitorieDaIstanzeOneri(codiciIstanzeOneri);
	if (!erroriValidazione.isEmpty()) {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    baos.write("<ul>".getBytes());
	    for (String errore : erroriValidazione) {
		baos.write(("<li>" + errore + "</li>" + "<br/>").getBytes());
	    }
	    baos.write("</ul>".getBytes());
	    response.getOutputStream().write(baos.toByteArray());
	    return;
	}
	Set<Integer> codiciAnagrafeSoggettiAggiuntivi = new HashSet<Integer>();
	if (!(codiceSoggettoCollegato == null || codiceSoggettoCollegato.isEmpty())) {
	    codiciAnagrafeSoggettiAggiuntivi.addAll(codiceSoggettoCollegato);
	}
	if (!codiciIstanzeOneri.isEmpty()) {
	    List<PosizioniDebitorieIstanzeoneriBean> posizionidebitorieDaIstanzeOneri = new ArrayList<PosizioniDebitorieIstanzeoneriBean>();
	    try {
		posizionidebitorieDaIstanzeOneri = nodoPagamentiService.inserisciPosizionidebitorieDaIstanzeOneri(codiciIstanzeOneri,
			codiciAnagrafeSoggettiAggiuntivi, isRateizzato);
	    } catch (Exception e1) {
		String message = "";
		if (e1.getMessage() != null) {
		    message = e1.getMessage();
		    message = message.replace("\n", "<br/>");
		}
		response.getOutputStream().write(message.getBytes());
		return;
	    }
	    if (posizionidebitorieDaIstanzeOneri == null || posizionidebitorieDaIstanzeOneri.isEmpty()) {
		throw new RuntimeException("Errore non è stato inserito nessun posizione debitoria da istanze oneri");
	    } else {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (PosizioniDebitorieIstanzeoneriBean posDebIstOnBean : posizionidebitorieDaIstanzeOneri) {
		    if (StringUtils.isNotEmpty(posDebIstOnBean.getMessaggioErrore())) {
			baos.write((posDebIstOnBean.getMessaggioErrore() + "<br/>").getBytes());
		    }
		    if (baos.toByteArray().length > 0) {
			response.getOutputStream().write(baos.toByteArray());
			return;
		    }
		}
	    }
	}
	try {
	    response.getOutputStream().write("OK".getBytes());
	    return;
	} catch (IOException e) {
	    log.error("errore nel scrivere l'esito sul response", e);
	}
    }

    /**
     * Controlla se è attivata la funzionalità di oneri per l'istanza e il responsabile passato
     * 
     * @param istanza
     * @param isUpdateOrDelete
     */
    private void checkAccessoIstanzeOneri(Istanze istanza, boolean isUpdateOrDelete) {

	checkAccessoInformazioni(istanza, isUpdateOrDelete);
	if (!EntityUtils.isNestedPropertyBlank(istanza, "id.codice")) {
	    Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	    TipoAccessoEnum tipoAccesso = istanzeoneriService.checkAccessoIstanzaOneri(istanza, responsabile);
	    if (tipoAccesso.equals(TipoAccessoEnum.NON_CONSENTITO)) {
		log.error("{}: L'operatore ({}) non ha accesso agli oneri dell'istanza [{}]",
			new String[] { ORMHelper.getSoftware(), responsabile.getResponsabile(), istanza.getNumeroistanza() });
		throw new SecurityException(ORMHelper.getSoftware() + ": L'operatore (" + responsabile.getResponsabile() +
					    ") non ha accesso agli oneri dell'istanza [" + istanza.getNumeroistanza() + "]");
	    }
	}
    }

    @Override
    protected void fixMergeEntityProperty(Istanzeoneri entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzeoneri entity) {

	if (entity.getTipicausalioneri() == null) {
	    entity.setTipicausalioneri(new Tipicausalioneri());
	}
	if (entity.getTipimodalitapagamento() == null) {
	    entity.setTipimodalitapagamento(new Tipimodalitapagamento());
	}
	if (entity.getTipomovimento() == null) {
	    entity.setTipomovimento(new Tipimovimento());
	}
	if (entity.getIstanza() == null) {
	    entity.setIstanza(new Istanze());
	}
	if (entity.getResponsabile() == null) {
	    entity.setResponsabile(new Responsabili());
	}
	if (entity.getAmministrazioni() == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
	if (entity.getInventarioprocedimenti() == null) {
	    entity.setInventarioprocedimenti(new Inventarioprocedimenti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	throw new NotImplementedException("Utilizzare il metodo setPageAttributes(Model model, String codiceComune)");
    }

    protected void setPageAttributes(Model model, String codiceComune) {

	model.addAttribute("verticalizzazioni_nodoPagamenti_attiva",
		new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune).isAttiva());
    }

    @RequestMapping
    public String listRate(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam(value = "idCO", required = false) Integer idCO,
	    @RequestParam(value = "codiceRaggruppamento", required = false) Integer codiceRaggruppamento, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	Istanze istanza = new Istanze(codiceIstanza);
	List<Istanzeoneri> listIstanzeoneri = new ArrayList<Istanzeoneri>();
	if (idCO == null && codiceRaggruppamento != null) {
	    Raggruppamentocausalioneri rco = raggruppamentocausalioneriService.findById(new PkId(codiceRaggruppamento));
	    listIstanzeoneri = istanzeoneriService.findByIstanzaAndRaggruppamentiAndData(istanza, rco, false, null);
	}
	if (idCO != null && codiceRaggruppamento == null) {
	    listIstanzeoneri = istanzeoneriService.findRateizzatiByIstanzaAndCausale(codiceIstanza, idCO);
	}
	for (Istanzeoneri istanzeoneri : listIstanzeoneri) {
	    boolean presentiPosDeb = istanzeoneri.isPresentiPosizioniDebitorie();
	    boolean presentiBoll = istanzeoneri.isCollegataABollettazione();
	    if (presentiPosDeb || presentiBoll) {
		String msgPosDeb = presentiPosDeb ? "per i quali sono state create posizioni debitorie, " : "";
		String msgBoll = presentiBoll ? " collegati ad una bollettazione" : "";
		FlashMessages.getWarnings().add("Non è possibile procedere. Ci sono oneri " + msgPosDeb + msgBoll);
		return "redirect:list.htm?codiceIstanza=" + codiceIstanza + "&status_msg=03";
	    }
	}
	model.addAttribute("istanza", istanza);
	model.addAttribute("listIstanzeoneri", listIstanzeoneri);
	model.addAttribute("codiceRaggruppamento", codiceRaggruppamento);
	model.addAttribute("codiceCausaleOneri", idCO);
	return "istanzeoneri/listRate";
    }
}
