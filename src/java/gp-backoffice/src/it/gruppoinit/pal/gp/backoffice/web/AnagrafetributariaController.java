package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
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
import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.pal.gp.core.domain.AtEsitoGruppo;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.AnagrafeTributariaService;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.CreazioneTestataTracciatoModel;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.ListaEsitiTracciatoBean;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.TestataEsitoTracciatoModel;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json.AnTribDettaglioRigheTracciato;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json.AnagrafeTribRigheEsito;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Controller
@SessionAttributes(value = { "testataEsitoTracciatoModel", "creazioneTestataTracciatoModel" })
public class AnagrafetributariaController extends BaseJsonController<TestataEsitoTracciatoModel> {

    private Logger logger = LoggerFactory.getLogger(AnagrafetributariaController.class);
    @Autowired
    private AnagrafeTributariaService anagrafeTributariaService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	Integer offset = 0;
	Integer limit = null;
	List<ListaEsitiTracciatoBean> esitiSalvati = anagrafeTributariaService.findEsitiSalvati(offset, limit);
	ModelMap model = new ModelMap(esitiSalvati);
	model.addAttribute("esitiSalvati", esitiSalvati);
	return model;
    }

    @RequestMapping
    public String create(Model model, HttpServletRequest request, HttpServletResponse response) {

	model.addAttribute("creazioneTestataTracciatoModel", new CreazioneTestataTracciatoModel());
	setPageAttributes(model);
	return "anagrafetributaria/formCreazione";
    }

    @RequestMapping
    public String caricaTracciati(Model model,
	    @ModelAttribute("creazioneTestataTracciatoModel") CreazioneTestataTracciatoModel creazioneTestataTracciatoModel, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	Integer codice = null;
	// §§§BEGIN§§§
	try {
	    creazioneTestataTracciatoModel.valida();
	    Responsabili r = getCurrentlyAuthenticatedUserDetails();
	    String contenutoFileEsiti = getContenutoDaFile(creazioneTestataTracciatoModel.getFileEsito());
	    String contenutoFileTracciato = getContenutoDaFile(creazioneTestataTracciatoModel.getFileTracciato());
	    TestataEsitoTracciatoModel esito = anagrafeTributariaService.salvaNuovoEsitoTracciato(creazioneTestataTracciatoModel.getDescrizione(),
		    r.getId().getCodice(), contenutoFileEsiti, contenutoFileTracciato);
	    codice = esito.getId();
	} catch (Exception e) {
	    // errori di validazioni
	    logger.error("caricaTracciati: {}", e.getMessage(), e);
	    String errorString = "Errore nel savataggio: " + e.getMessage();
	    FlashMessages.getWarnings().add(errorString);
	    return "anagrafetributaria/formCreazione";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:view.htm?codice=" + codice;
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice,
	    @RequestParam(value = "codiceGruppoAnagrafetributaria", required = false) Integer codiceGruppoAnagrafetributaria, Model model,
	    HttpServletRequest request) {

	TestataEsitoTracciatoModel testataEsitoTracciatoModel = this.anagrafeTributariaService.getTestataById(codice);
	model.addAttribute("testataEsitoTracciatoModel", testataEsitoTracciatoModel);
	setPageAttributes(model);
	return "anagrafetributaria/form";
    }

    @RequestMapping
    public String delete(@RequestParam("id") Integer codice, Model model, HttpServletRequest request) {

	try {
	    this.anagrafeTributariaService.delete(codice);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add(e.getMessage());
	    return "redirect:view.htm?codice=" + codice + "&status_msg=03";
	}
	return "redirect:list.htm?status_msg=05";
    }

    @RequestMapping
    public void ajaxGetRighe(Model model, @RequestParam("idTestata") Integer idTestata, @RequestParam("offset") Integer offset,
	    @RequestParam("limit") Integer limit, HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

	if (offset == null) {
	    offset = 0;
	}
	if (limit == null) {
	    limit = 5;
	}
	AnagrafeTribRigheEsito esito = this.anagrafeTributariaService.getRigheEsito(idTestata, offset, limit);
	response.setContentType("application/json");
	response.getOutputStream().write(this.toJsonBytes(esito));
	response.getOutputStream().flush();
    }

    @RequestMapping
    public void ajaxDettaglioRigheTracciato(Model model, @RequestParam("idGruppo") Integer idGruppo, HttpServletRequest request,
	    HttpServletResponse response) throws IOException, JAXBException {

	List<AnTribDettaglioRigheTracciato> esito = this.anagrafeTributariaService.getDettaglioRigheTracciato(idGruppo);
	response.setContentType("application/json");
	String richiesta = Utilities.marshalJsonObject(esito, AnTribDettaglioRigheTracciato.class, false, Utilities.JAXB_ENCODING_UTF_8);
	response.getOutputStream().write(richiesta.getBytes());
	response.getOutputStream().flush();
    }

    @RequestMapping
    public void ajaxAggiornaValidaErrore(Model model, @RequestParam("idRigaErrore") Integer idRigaErrore,
	    @RequestParam("segnaValido") Boolean segnaValido, HttpServletRequest request, HttpServletResponse response) throws IOException {

	response.setContentType("plain/text");
	if (idRigaErrore != null && segnaValido != null) {
	    anagrafeTributariaService.aggiornaValidaErrore(idRigaErrore, segnaValido);
	    response.getOutputStream().write("OK".getBytes());
	} else {
	    response.getOutputStream().write("KO".getBytes());
	}
	response.getOutputStream().flush();
    }

    private String getContenutoDaFile(MultipartFile fileUpload) throws IOException {

	return IOUtils.toString(fileUpload.getInputStream(), "UTF-8");
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(TestataEsitoTracciatoModel entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(TestataEsitoTracciatoModel entity) {

	// TODO Auto-generated method stub
    }

    @RequestMapping
    public String addIstanzaAGruppo(@RequestParam("codiceGruppoAnagrafetributaria") Integer codiceGruppoAnagrafetributaria,
	    @RequestParam("codiceIstanzaAnagrafeTributaria") Integer codiceIstanzaAnagrafeTributaria, Model model, HttpServletRequest request) {

	AtEsitoGruppo gruppo = this.anagrafeTributariaService.aggiornaGruppoConIstanza(codiceGruppoAnagrafetributaria,
		codiceIstanzaAnagrafeTributaria);
	return "redirect:view.htm?codice=" + gruppo.getFkidAtTestata() + "&status_msg=02&codiceGruppoAnagrafetributaria=" +
	       gruppo.getId().getCodice();
    }
}
