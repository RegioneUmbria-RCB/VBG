package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.DocumentiistanzaCommand;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeallegatiCommand;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.AllegatiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni.TIPO_DOCUMENTO;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes("istanzeallegati")
public class IstanzeallegatiController extends BaseController<Istanzeallegati> {

    @Autowired
    private IstanzeallegatiService istanzeallegatiService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private AllegatiService allegatiService;
    @Autowired
    private UserSecurityService userSecurityService;

    @RequestMapping
    public ModelMap list(@RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request, HttpServletResponse response) {

	Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanze, false);
	List<IstanzeallegatiHelper> istanzeallegatiHelperList = istanzeallegatiService.findIstanzeallegatiHelper(istanze);
	ModelMap model = new ModelMap(istanzeallegatiHelperList);
	model.addAttribute("istanzeallegatiList", istanzeallegatiHelperList);
	model.addAttribute("istanze", istanze);
	return model;
    }

    @RequestMapping
    public String createIstanzaAllegato(Model model, @RequestParam("codiceinventario") Integer codiceinventario,
	    @RequestParam("codiceistanza") Integer codiceIstanza,
	    @RequestParam(required = false, value = "isCaricamnetoMultiplo") Boolean isCaricamnetoMultiplo, HttpServletRequest request,
	    HttpServletResponse response) {

	if (isCaricamnetoMultiplo == null) {
	    isCaricamnetoMultiplo = false;
	}
	Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanze, true);
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codiceinventario));
	IstanzeallegatiCommand istanzeallegati = new IstanzeallegatiCommand();
	Istanzeallegati entity = new Istanzeallegati();
	entity.setInventarioprocedimenti(inventarioprocedimenti);
	entity.setIstanza(istanze);
	istanzeallegati.setEntity(entity);
	model.addAttribute("istanzeallegati", istanzeallegati);
	model.addAttribute("codiceinventario", codiceinventario);
	model.addAttribute("isCaricamnetoMultiplo", isCaricamnetoMultiplo);
	istanzeallegati.setDisplayMode(DocumentiistanzaCommand.NEW);
	setPageAttributes(model);
	return "istanzeallegati/formIstanzaallegato";
    }

    @RequestMapping
    public String insertIstanzaallegato(Model model, @ModelAttribute("istanzeallegati") IstanzeallegatiCommand istanzeallegati, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	List<MultipartFile> lMultipartFiles = Utilities.getFileFromMultipartRequest(request);
	Integer codiceDocumento = null;
	if (istanzeallegati.getEntity().getOggetto() != null && istanzeallegati.getEntity().getOggetto().getId().getCodice() != null) {
	    Oggetti oggetti = oggettiService.findById(istanzeallegati.getEntity().getOggetto().getId());
	    istanzeallegati.getEntity().setOggetto(oggetti);
	}
	try {
	    checkAccessoInformazioni(istanzeallegati.getEntity().getIstanza(), true);
	    //istanzeallegatiService.insert(istanzeallegati.getEntity());
	    codiceDocumento = istanzeallegatiService.insertSingoloOrMultiFile(istanzeallegati.getEntity(), lMultipartFiles);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeallegati.getEntity(), true, e);
	    fixRenderEntityProperty(istanzeallegati.getEntity());
	    istanzeallegati.setDisplayMode(DocumentiistanzaCommand.NEW);
	    boolean isCaricamnetoMultiplo = !lMultipartFiles.isEmpty();
	    model.addAttribute("isCaricamnetoMultiplo", isCaricamnetoMultiplo);
	    setPageAttributes(model);
	    return "istanzeallegati/formIstanzaallegato";
	}
	status.setComplete();
	return "redirect:viewIstanzaAllegato.htm?codice=" + codiceDocumento + "&status_msg=01";
    }

    @RequestMapping
    public String viewIstanzaAllegato(Model model, @RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Istanzeallegati entity = istanzeallegatiService.findById(new PkId(codice));
	checkAccessoInformazioni(entity.getIstanza(), false);
	IstanzeallegatiCommand istanzeallegati = new IstanzeallegatiCommand();
	istanzeallegati.setEntity(entity);
	fixRenderEntityProperty(istanzeallegati.getEntity());
	model.addAttribute("istanzeallegati", istanzeallegati);
	istanzeallegati.setDisplayMode(DocumentiistanzaCommand.VIEW);
	setPageAttributes(model);
	if (EntityUtils.getNestedProperty(entity.getOggetto(), "id.codice") != null) {
	    Integer codiceResponsabile = ((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails()).getId().getCodice();
	    oggettiService.updateFileRimuoviBloccoModifica(entity.getOggetto().getId().getCodice(), codiceResponsabile);
	}
	return "istanzeallegati/formIstanzaallegato";
    }

    @RequestMapping
    public String updateIstanzaallegato(Model model, @ModelAttribute("istanzeallegati") IstanzeallegatiCommand istanzeallegati, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	if (istanzeallegati.getEntity().getOggetto() != null && istanzeallegati.getEntity().getOggetto().getId().getCodice() != null) {
	    Oggetti oggetti = oggettiService.findById(istanzeallegati.getEntity().getOggetto().getId());
	    istanzeallegati.getEntity().setOggetto(oggetti);
	}
	try {
	    checkAccessoInformazioni(istanzeallegati.getEntity().getIstanza(), true);
	    loggaCancellazioneOggettoIstanza(request);
	    istanzeallegatiService.insert(istanzeallegati.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeallegati.getEntity(), true, e);
	    fixRenderEntityProperty(istanzeallegati.getEntity());
	    istanzeallegati.setDisplayMode(DocumentiistanzaCommand.VIEW);
	    setPageAttributes(model);
	    return "istanzeallegati/formIstanzaallegato";
	}
	status.setComplete();
	return "redirect:viewIstanzaAllegato.htm?codice=" + istanzeallegati.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String createIstanzaAllegatoDaEndo(Model model, @RequestParam("codiceinventario") Integer codiceinventario,
	    @RequestParam("codiceistanza") Integer codiceIstanza, HttpServletRequest request, HttpServletResponse response) {

	Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanze, true);
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codiceinventario));
	IstanzeallegatiCommand istanzeallegati = new IstanzeallegatiCommand();
	Istanzeallegati entity = new Istanzeallegati();
	entity.setInventarioprocedimenti(inventarioprocedimenti);
	entity.setIstanza(istanze);
	istanzeallegati.setEntity(entity);
	model.addAttribute("istanzeallegati", istanzeallegati);
	setPageAttributes(model);
	return "istanzeallegati/formIstanzaallegatoDaEndo";
    }

    @RequestMapping
    public String insertIstanzaallegatoDaEndo(Model model, @ModelAttribute("istanzeallegati") IstanzeallegatiCommand istanzeallegati,
	    BindingResult result, SessionStatus status) {

	if (istanzeallegati.getAllegati() != null && istanzeallegati.getAllegati().getId().getCodice() != null) {
	    Allegati allegati = allegatiService.findById(istanzeallegati.getAllegati().getId());
	    istanzeallegati.getEntity().setAllegati(allegati);
	    istanzeallegati.getEntity().setAllegatoextra(allegati.getAllegato());
	}
	//setto come nome extra il nome recuperato dall'allegato
	try {
	    checkAccessoInformazioni(istanzeallegati.getEntity().getIstanza(), true);
	    istanzeallegatiService.insertDaEndo(istanzeallegati.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeallegati.getEntity(), true, "allegati", e);
	    fixRenderEntityProperty(istanzeallegati.getEntity());
	    setPageAttributes(model);
	    return "istanzeallegati/formIstanzaallegatoDaEndo";
	}
	status.setComplete();
	return "redirect:viewIstanzaAllegato.htm?codice=" + istanzeallegati.getEntity().getId().getCodice() + "&status_msg=01";
    }

    /**
     * Il metodo attraverso una chiamata ajax va a modificare il checkbox selzionato
     * 
     * @param model
     * @param presentato
     * @param verificato
     * @param valido
     * @param request
     * @param response
     * @return
     */
    @RequestMapping
    public void ajxaChangeCheckboxvalue(Model model, @RequestParam(value = "codice") String codice,
	    @RequestParam(value = "presentato", required = false) String presentato, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Istanzeallegati istanzeallegati = istanzeallegatiService.findById(new PkId(Integer.parseInt(codice)));
	try {
	    checkAccessoInformazioni(istanzeallegati.getIstanza(), true);
	    // Logica: se arriva il flag diverso da null allora se:
	    // 1 - E' false significa che il cambiamento deve essere a true
	    // 2 - E' true significa che il cambiamento deve essere a false
	    //(il valore che ci arriva dalla jsp è il valore che è sul DB quindi se noi siamo nella funzionalità di cambiamento
	    //significa che dovremmo andare asalvare il valore opposto)
	    if (StringUtils.isNotBlank(presentato)) {
		boolean value = (presentato.equals("true") ? false : true);
		istanzeallegati.setPresente(Boolean.valueOf(value));
	    }
	    istanzeallegatiService.update(istanzeallegati);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	}
    }

    @RequestMapping
    public void ajaxAbilitaDisabilitaNecessario(@RequestParam("codice") Integer codice, @RequestParam("necessario") Boolean necessario, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	PkId id = new PkId(codice);
	Istanzeallegati istanzeallegati = istanzeallegatiService.findById(id);
	try {
	    checkAccessoInformazioni(istanzeallegati.getIstanza(), true);
	    istanzeallegati.setNecessario(necessario);
	    istanzeallegatiService.update(istanzeallegati);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	}
    }

    @RequestMapping
    public void ajaxChangeValueFieldValido(@RequestParam("codice") Integer codice, @RequestParam("valido") Integer valido, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	PkId id = new PkId(codice);
	Istanzeallegati istanzeallegati = istanzeallegatiService.findById(id);
	try {
	    checkAccessoInformazioni(istanzeallegati.getIstanza(), true);
	    istanzeallegati.setControllook(valido);
	    istanzeallegatiService.update(istanzeallegati);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	}
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("istanzeallegati") IstanzeallegatiCommand istanzeallegati, BindingResult result,
	    SessionStatus status) {

	Istanzeallegati objToDelete = istanzeallegatiService.findById(istanzeallegati.getEntity().getId());
	checkAccessoInformazioni(objToDelete.getIstanza(), true);
	Responsabili userlogged = getCurrentlyAuthenticatedUserDetails();
	if (StringUtils.isNotBlank(objToDelete.getStcIdallegato()) || StringUtils.isNotBlank(objToDelete.getStcIddocumento())) {
	    boolean isCancellaMail = userlogged.getFlagCancelladocumentistc() == null ? false
		    : userlogged.getFlagCancelladocumentistc().booleanValue();
	    if (!isCancellaMail) {
		String messaggioErrore = getMessageFromBundle("javascript.alert.operatore_non_puo_cancellare_documento_stc", null);
		throw new SecurityException(messaggioErrore);
	    }
	}
	String descrizioneDocumento = objToDelete.toString();
	String descrizioneIstanza = objToDelete.getIstanza().toString();
	try {
	    istanzeallegatiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(objToDelete);
	    setPageAttributes(model);
	    return "istanzeallegati/formIstanzaallegato";
	}
	LoggerCancellazioni.logCancellazioneDocumentoistanza(userlogged.toString(), descrizioneDocumento, descrizioneIstanza,
		TIPO_DOCUMENTO.Allegato_Endo);
	status.setComplete();
	return "redirect:list.htm?codiceIstanza=" + objToDelete.getIstanza().getId().getCodice() + "&status_msg=02";
    }

    @Override
    protected void fixMergeEntityProperty(Istanzeallegati entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzeallegati entity) {

	if (entity.getIstanza() == null) {
	    entity.setIstanza(new Istanze());
	}
	if (entity.getInventarioprocedimenti() == null) {
	    entity.setInventarioprocedimenti(new Inventarioprocedimenti());
	}
	if (entity.getAllegati() == null) {
	    entity.setAllegati(new Allegati());
	}
	if (entity.getOggetto() == null) {
	    entity.setOggetto(new Oggetti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	Responsabili resp = getCurrentlyAuthenticatedUserDetails();
	model.addAttribute("userlogged", resp);
    }
}
