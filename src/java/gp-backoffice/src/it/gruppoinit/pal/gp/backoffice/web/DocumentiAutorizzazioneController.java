package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.DocumentiAutorizzazione;
import it.gruppoinit.pal.gp.core.domain.DocumentiAutorizzazioneId;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.DocumentiAutorizzazioneCommand;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneDTO;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.verticalizzazione.VerticalizzazioneWSAttiService;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AnagrafedocumentiService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocureService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.helper.TipoRicercaDocumentoEnum;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("documentiautorizzazione")
public class DocumentiAutorizzazioneController extends BaseController<DocumentiAutorizzazione> {

    @Autowired
    private DocumentiAutorizzazioneService documentiautorizzazioneService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private MovimentiallegatiService movimentiallegatiService;
    @Autowired
    private IstanzeallegatiService istanzeallegatiService;
    @Autowired
    private IstanzeprocureService istanzeprocureService;
    @Autowired
    private AnagrafedocumentiService anagrafedocumentiService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private DocumentiDaFirmareService documentidafirmareService;
    private VerticalizzazioneWSAttiService verticalizzazioneWSAttiService;

    @Autowired
    public void setVerticalizzazioneWSAttiService(VerticalizzazioneWSAttiService verticalizzazioneWSAttiService) {

	this.verticalizzazioneWSAttiService = verticalizzazioneWSAttiService;
    }

    @RequestMapping
    public ModelMap list(@RequestParam("codiceautorizzazione") Integer codiceautorizzazione, HttpServletRequest request,
	    HttpServletResponse response) {

	List<DocumentiAutorizzazione> documentiautorizzazioneList = documentiautorizzazioneService.findByAutorizzazioni(codiceautorizzazione, null,
		null);
	ModelMap model = new ModelMap(documentiautorizzazioneList);
	boolean export = createJMesaExport(request, response, documentiautorizzazioneList);
	if (export) {
	    return null;
	}
	Autorizzazioni autorizzazione = autorizzazioniService.findById(new PkId(codiceautorizzazione));
	model.addAttribute("autorizzazione", autorizzazione);
	//------ INIZIO GESTIONE CONFIGURAZIONE UTENTE
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_DOC_ISTANZA, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_DOC_MOVIMENTO, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_ALLEGATI_ISTANZA, "1", false);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_PROCURE, "1", false);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_DOC_ANAGRAFE, "1", false);
	//------ FINE GESTIONE CONFIGURAZIONE UTENTE
	return model;
    }

    @RequestMapping
    public void completaAutorizzazione(@RequestParam("idautorizzazione") Integer idautorizzazione, HttpServletRequest request,
	    HttpServletResponse response) {

	this.autorizzazioniService.completaAutorizzazione(idautorizzazione, true);
	this.list(idautorizzazione, request, response);
    }

    /**
     * Restituisce la risorsa ajaxDettaglioDocumentiAutorizzazioni.jsp per visualizzare la lista dei documenti legati
     * all'autorizzazione.
     * 
     * @param codiceautorizzazione
     * @param codiceoggetto
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping
    public String ajaxDettaglioDocumentiAutorizzazione(@RequestParam("codiceautorizzazione") Integer codiceautorizzazione,
	    @RequestParam(required = false, value = "codiceoggettoinserito") Integer codiceoggetto, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	List<DocumentiAutorizzazioneDTO> documentiAutorizzazioneDTOs = documentiautorizzazioneService
		.findDocumentiAutorizzazioneDTOByAutorizzazione(codiceautorizzazione);
	Autorizzazioni autorizzazione = autorizzazioniService.findById(new PkId(codiceautorizzazione));
	setListDocumentPageAttributes(model, "documentiautorizzaziones", documentiAutorizzazioneDTOs, autorizzazione, "codiceoggettoinserito",
		codiceoggetto);
	model.addAttribute("mostraCheckPrincipale", !this.verticalizzazioneWSAttiService.isAttiva());
	return "documentiautorizzazione/ajaxDettaglioDocumentiAutorizzazioni";
    }

    @RequestMapping
    public void ajaxImpostaDocumentoPrincipale(@RequestParam("codiceautorizzazione") Integer codiceAutorizzazione,
	    @RequestParam("codiceoggetto") Integer codiceOggetto, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	String result = "OK";
	try {
	    this.documentiautorizzazioneService.impostaDocumentoPrincipale(codiceAutorizzazione, codiceOggetto);
	} catch (Exception e) {
	    result = "Si e' verificato un errore durante il tentativo di impostare il documento principale dell'autorizzazione. (dettaglio:" +
		    e.getMessage() +
		    ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    /**
     * Permette di rimuovere il collegamento di un documento legato all'autorizzazione. Elimina il documento
     * dell'autorizzazione selezionato dalla tabella DOCUMENTI_AUTORIZZAZIONE
     * 
     * @param codiceoggetto
     * @param codiceautorizzazione
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping
    public void ajaxEliminaDocumentoAutorizzazione(@RequestParam("codiceoggetto") Integer codiceoggetto,
	    @RequestParam("codiceautorizzazione") Integer codiceautorizzazione, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	String result = "OK";
	try {
	    DocumentiAutorizzazioneId id = new DocumentiAutorizzazioneId();
	    id.setCodiceoggetto(codiceoggetto);
	    id.setIdautorizzazione(codiceautorizzazione);
	    DocumentiAutorizzazione entity = documentiautorizzazioneService.findById(id); // recupero l'entity da eliminare
	    documentiautorizzazioneService.delete(entity);
	} catch (Exception e) {
	    result = "Si e' verificato un errore durante la cancellazione del documento autorizzazione. (dettaglio:" + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public String create(Model model) {

	DocumentiAutorizzazione documentiautorizzazione = new DocumentiAutorizzazione();
	fixRenderEntityProperty(documentiautorizzazione);
	model.addAttribute("documentiautorizzazione", documentiautorizzazione);
	setPageAttributes(model);
	return "documentiautorizzazione/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("documentiautorizzazione") DocumentiAutorizzazioneCommand documentiautorizzazione, BindingResult result,
	    SessionStatus status) {

	DocumentiAutorizzazione entity = documentiautorizzazione.getEntity();
	fixMergeEntityProperty(entity);
	try {
	    documentiautorizzazioneService.insert(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, documentiautorizzazione, e);
	    fixRenderEntityProperty(documentiautorizzazione.getEntity());
	    return "documentiautorizzazione/form";
	}
	status.setComplete();
	return "redirect:view.htm?codiceoggetto=" +
		entity.getId().getCodiceoggetto() +
		"&codiceautorizzazione=" +
		entity.getId().getIdautorizzazione() +
		"&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codiceoggetto") Integer codiceoggetto, @RequestParam("codiceautorizzazione") Integer codiceautorizzazione,
	    Model model, HttpServletRequest request) {

	DocumentiAutorizzazioneId id = new DocumentiAutorizzazioneId();
	id.setCodiceoggetto(codiceoggetto);
	id.setIdautorizzazione(codiceautorizzazione);
	DocumentiAutorizzazione documentiautorizzazione = documentiautorizzazioneService.findById(id);
	fixRenderEntityProperty(documentiautorizzazione);
	model.addAttribute("documentiautorizzazione", documentiautorizzazione);
	setPageAttributes(model);
	return "documentiautorizzazione/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("documentiautorizzazione") DocumentiAutorizzazione documentiautorizzazione, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(documentiautorizzazione);
	try {
	    documentiautorizzazioneService.update(documentiautorizzazione);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, documentiautorizzazione, e);
	    fixRenderEntityProperty(documentiautorizzazione);
	    return "documentiautorizzazione/form";
	}
	status.setComplete();
	return "redirect:view.htm?codiceoggetto=" +
		documentiautorizzazione.getId().getCodiceoggetto() +
		"&codiceautorizzazione=" +
		documentiautorizzazione.getId().getIdautorizzazione() +
		"&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("documentiautorizzazione") DocumentiAutorizzazione documentiautorizzazione, BindingResult result,
	    SessionStatus status) {

	DocumentiAutorizzazione objToDelete = documentiautorizzazioneService.findById(documentiautorizzazione.getId());
	try {
	    documentiautorizzazioneService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(documentiautorizzazione);
	    return "documentiautorizzazione/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    /**
     * Restituisce la tabella degli allegati della pratica in base alla tipologia di codice specificata dal parametro
     * tipocodice.
     * 
     * @param codicedocumento
     *            : il codice del documento da visualizzare nella tabella.
     * @param codiceoggetto
     *            : del documento della pratica. NB: saranno visualizzati solo i documenti che hanno un codiceoggetto !=
     *            null ovvero hanno possiedono un allegato fisico.
     * @param codiceautorizzazione
     * @param codicedocumentoInserito
     * @param codiceistanza
     * @param codiceanagrafe
     * @param tipocodice
     *            : specifica il tipo di tabella di documenti da visualizzare in base al codicedocumento passato come
     *            parametro. Per esempio se il codicedocumento identifica il documento dell'istanza allora tipocodice ==
     *            WebConstants.DOCAUTORIZZAZIONE_CODICE_DOCUMENTIISTANZA e quindi sarà restituita la risorsa
     *            ajaxDettaglioDocumentiIstanza.jsp che mostra la tabella dei documenti dell'istanza.
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping
    public String ajaxDettaglioDocumento(@RequestParam("codice") Integer codicedocumento,
	    @RequestParam(required = false, value = "codiceoggetto") Integer codiceoggetto,
	    @RequestParam("codiceautorizzazione") Integer codiceautorizzazione,
	    @RequestParam(required = false, value = "codicedocumentoInserito") Integer codicedocumentoInserito,
	    @RequestParam(required = false, value = "codiceistanza") Integer codiceistanza,
	    @RequestParam(required = false, value = "codiceanagrafe") Integer codiceanagrafe,
	    @RequestParam(required = false, value = "tipocodice") Integer tipocodice, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(codiceautorizzazione));
	if (tipocodice == WebConstants.DOCAUTORIZZAZIONE_CODICE_DOCUMENTIISTANZA) {
	    Istanze istanze = istanzeService.findById(new PkId(codiceistanza));
	    List<DocumentiistanzaDTO> documentiistanzaDTOs = documentiistanzaService
		    .findDocumentiistanzaDTOByIstanzaNonInDocAutorizzazione(codiceistanza, codiceautorizzazione);
	    model.addAttribute("istanza", istanze);
	    setListDocumentPageAttributes(model, "documentiistanzas", documentiistanzaDTOs, autorizzazioni, null, null);
	    return "documentiautorizzazione/ajaxDettaglioDocumentiIstanza";
	} else if (tipocodice == WebConstants.DOCAUTORIZZAZIONE_CODICE_MOVIMENTIALLEGATI) {
	    List<MovimentiallegatiDTO> movimentiallegatiDTOs = movimentiallegatiService
		    .findMovimentiallegatiDTOByIstanzaNonInDocAutorizzazione(codiceistanza, codiceautorizzazione);
	    setListDocumentPageAttributes(model, "movimentiallegatis", movimentiallegatiDTOs, autorizzazioni, null, null);
	    return "documentiautorizzazione/ajaxDettaglioAllegatiMovimenti";
	} else if (tipocodice == WebConstants.DOCAUTORIZZAZIONE_CODICE_ISTANZEALLEGATI) {
	    List<IstanzeallegatiDTO> istanzeallegatiDTOs = istanzeallegatiService.findIstanzeAllegatiDTOByIstanzaNonInDocAutorizzazione(codiceistanza,
		    codiceautorizzazione);
	    setListDocumentPageAttributes(model, "istanzeallegatis", istanzeallegatiDTOs, autorizzazioni, null, null);
	    return "documentiautorizzazione/ajaxDettaglioDocumentiEndo";
	} else if (tipocodice == WebConstants.DOCAUTORIZZAZIONE_CODICE_ANAGRAFEDOCUMENTI) {
	    Anagrafe anagrafe = anagrafeService.findById(new PkId(codiceanagrafe));
	    Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	    List<AnagrafedocumentiDTO> anagrafedocumentiDTOs = anagrafedocumentiService.findByIstanzaAndAnagrafeDTONonInDocAutorizzazione(istanza,
		    anagrafe, codiceautorizzazione);
	    setListDocumentPageAttributes(model, "anagrafedocumentis", anagrafedocumentiDTOs, autorizzazioni, null, null);
	    return "documentiautorizzazione/ajaxDettaglioDocumentiAnagrafe";
	} else if (tipocodice == WebConstants.DOCAUTORIZZAZIONE_CODICE_ISTANZEPROCURE) {
	    List<IstanzeprocureDTO> istanzeprocureDTOs = istanzeprocureService.findIstanzeprocureDTOByIstanzaNonInDocAutorizzazione(codiceistanza,
		    TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, codiceautorizzazione);
	    setListDocumentPageAttributes(model, "istanzeprocures", istanzeprocureDTOs, autorizzazioni, null, null);
	    return "documentiautorizzazione/ajaxDettaglioProcure";
	}
	return "documentiautorizzazione/list.htm?codiceautorizzazione=" + codiceautorizzazione;
    }

    /**
     * Permette di inserire un nuovo oggetto di tipo DocumentiAutorizzazione nella tabella DOCUMENTI_AUTORIZZAZIONE.
     * 
     * @param codiceOggetto
     *            : il codiceoggetto del documento della pratica che si vuole legare all'autorizzazione. NB: saranno
     *            legati all'autorizzazione tutti i documenti della pratica che hanno un codice oggetto != null ovvero
     *            che hanno un allegato fisico.
     * @param idAutorizzazione
     * @param codice
     *            : il codice del documento che si vuole legare all'autorizzazione.
     * @param tipocodice
     *            : specifica il tipo di collegamento che si vuole creare per il nuovo documento in base al codice
     *            passato come argomento. Per esempio se si vuole legare un allegato di un movimento all'autorizzazione,
     *            tipocodice == WebConstants.DOCAUTORIZZAZIONE_CODICE_MOVIMENTIALLEGATI quindi il nuovo documento
     *            dell'autorizzazione sarà collegato all'allegato del movimento e avrà come codiceoggetto il
     *            codiceOggetto dell'allegato stesso.
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping
    public void ajaxAggiungiDocumento(@RequestParam("codiceoggetto") Integer codiceOggetto,
	    @RequestParam("idautorizzazione") Integer idAutorizzazione, @RequestParam(required = false, value = "codice") Integer codice,
	    @RequestParam(required = false, value = "tipocodice") Integer tipocodice, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	String result = "OK";
	try {
	    documentiautorizzazioneService.insertDocumentoAutorizzazione(codiceOggetto, idAutorizzazione, codice, tipocodice);
	} catch (Exception e) {
	    result = "Si e' verificato un errore durante l'inserimento del dato. (dettaglio: " + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    /**
     * Restituisce la lista dei documenti dell'autorizzazione che si possono mettere alla firma. Solo i documenti
     * dell'autorizzazione collegati ai movimenti possono essere messi alla firma.
     * 
     * @param codiceautorizzazione
     * @param request
     * @param model
     * @param response
     * @return
     */
    @RequestMapping
    public String listDocumentiMessiAllaFirma(@RequestParam("codiceautorizzazione") Integer codiceautorizzazione, HttpServletRequest request,
	    Model model, HttpServletResponse response) {

	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(codiceautorizzazione));
	List<DocumentiAutorizzazioneDTO> documentiAutorizzazioneDTOs = documentiautorizzazioneService
		.findDocumentiAutorizzazioneDTOByAutorizzazione(codiceautorizzazione);
	List<DocumentiAutorizzazioneDTO> movimentiallegatis = new ArrayList<DocumentiAutorizzazioneDTO>();
	for (DocumentiAutorizzazioneDTO documentiAutorizzazioneDTO : documentiAutorizzazioneDTOs) {
	    if (documentiAutorizzazioneDTO.getCodiceMovimentiallegati() != null) {
		documentiAutorizzazioneDTO.setNumeroIstanza(autorizzazioni.getIstanza().getNumeroistanza());
		movimentiallegatis.add(documentiAutorizzazioneDTO);
	    }
	}
	if (!movimentiallegatis.isEmpty()) {
	    model.addAttribute("movimentiallegatis", movimentiallegatis);
	    List<DocumentiDaFirmare> documentiDaFirmareList = new ArrayList<DocumentiDaFirmare>();
	    for (DocumentiAutorizzazioneDTO documentiAutorizzazioneDTO : movimentiallegatis) {
		List<DocumentiDaFirmare> documentiDaFirmares = documentidafirmareService
			.findByMovimentiallegatiDaFirmare(documentiAutorizzazioneDTO.getCodiceMovimentiallegati());
		documentiDaFirmareList.addAll(documentiDaFirmares);
	    }
	    if (!documentiDaFirmareList.isEmpty()) {
		model.addAttribute("documentidafirmares", documentiDaFirmareList);
	    }
	}
	model.addAttribute("autorizzazione", autorizzazioni);
	return "documentiautorizzazione/listDocumentiMessiAllaFirma";
    }

    /**
     * Gestisce la visualizzazione del bottone "METTI ALLA FIRMA" nella pagina list.jsp. Se almeno uno degli allegati di
     * un movimento dell'istanza viene collegato all'autorizzazione, allora il bottone viene visualizzato con la
     * possibilità di mettere alla firma il documento o i documenti del movimento collegati all'autorizzazione.
     * 
     * @param codiceautorizzazione
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void ajaxShowMettiAllaFirmaBtn(@RequestParam("codiceautorizzazione") Integer codiceautorizzazione, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String result = "";
	List<DocumentiAutorizzazione> documentiautorizzazioneList = documentiautorizzazioneService.findByAutorizzazioni(codiceautorizzazione, null,
		null);
	// Lista dei documenti dei movimenti presenti in documenti autorizzazione
	// se questa lista è vuota non mostro il bottone metti alla firma
	List<DocumentiAutorizzazione> movimentiAllegatis = new ArrayList<DocumentiAutorizzazione>();
	if (!documentiautorizzazioneList.isEmpty()) {
	    for (DocumentiAutorizzazione documentiAutorizzazione : documentiautorizzazioneList) {
		if (EntityUtils.getNestedProperty(documentiAutorizzazione.getMovimentiallegati(), "id.codice") != null) {
		    movimentiAllegatis.add(documentiAutorizzazione);
		}
	    }
	    if (!movimentiAllegatis.isEmpty()) {
		result = "OK";
	    }
	}
	response.getOutputStream().write(result.getBytes());
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    /**
     * Metodo di utilità, utilizzato nei metodi ajaxDettaglioDocumento e ajaxDettaglioDocumentoAutorizzazione
     * 
     * @param model
     * @param list
     * @param listName
     * @param autorizzazione
     */
    private void setListDocumentPageAttributes(Model model, String listName, List<?> list, Autorizzazioni autorizzazione, String name,
	    Integer value) {

	model.addAttribute(listName, list);
	model.addAttribute("autorizzazione", autorizzazione);
	if (name != null && value != null) {
	    model.addAttribute(name, value);
	}
    }

    @Override
    protected void fixMergeEntityProperty(DocumentiAutorizzazione entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(DocumentiAutorizzazione entity) {

	// TODO Auto-generated method stub
    }
}
