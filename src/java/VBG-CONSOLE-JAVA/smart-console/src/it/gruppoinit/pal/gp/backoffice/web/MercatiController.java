package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Concessioniuso;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Giornisettimana;
import it.gruppoinit.pal.gp.core.domain.Manifestazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConsorzi;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Mercatistradario;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.service.ConcessioniusoService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.GiornisectimanaService;
import it.gruppoinit.pal.gp.core.service.ManifestazioniService;
import it.gruppoinit.pal.gp.core.service.MercatiConsorziService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.MercatistradarioService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

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

/**
 * 
 * @author gianpaolot
 */
//DAELIMINARE @Controller
@SessionAttributes(value = { "mercati", "mercatiuso", "mercatistradario", "mercatid", "mercaticonsorzi" })
public class MercatiController extends BaseController<Mercati> {

    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private ManifestazioniService manifestazioniService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private GiornisectimanaService giornisectimanaService;
    @Autowired
    private ConcessioniusoService concessioniusoService;
    @Autowired
    private MercatistradarioService mercatistradarioService;
    @Autowired
    private StradarioService stradarioService;
    @Autowired
    private MercatiConsorziService mercatiConsorziService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Mercati> mercatiList = mercatiService.findAll(null, null);
	ModelMap model = new ModelMap(mercatiList);
	boolean export = createJMesaExport(request, response, mercatiList);
	if (export) {
	    return null;
	}
	model.addAttribute("mercatiList", mercatiList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	// recupero le manifestazioni
	List<Manifestazioni> listmanifestazioni = manifestazioniService.findAll(null, null);
	Mercati mercati = new Mercati();
	mercati.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(mercati);
	model.addAttribute("mercati", mercati);
	model.addAttribute("listmanifestazioni", listmanifestazioni);
	setPageAttributes(model);
	return "mercati/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("mercati") Mercati mercati, BindingResult result, SessionStatus status) {

	// recupero la manifestazione passata
	Manifestazioni manifestazione = manifestazioniService.findById(mercati.getManifestazione().getCodice());
	mercati.setManifestazione(manifestazione);
	fixMergeEntityProperty(mercati);
	mercati.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    mercatiService.insert(mercati);
	} catch (Exception e) {
	    // recupero le manifestazioni
	    copyErrorsToBindingResult(result, mercati, e);
	    fixRenderEntityProperty(mercati);
	    setPageAttributes(model, mercati);
	    return "mercati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercati.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// recupero le manifestazioni
	PkId id = new PkId(codice);
	Mercati mercati = mercatiService.findById(id);
	fixRenderEntityProperty(mercati);
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatid", new MercatiD());
	setPageAttributes(model, mercati);
	setPageAttributes(model);
	return "mercati/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("mercati") Mercati mercati, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// recupero l'oggetto corrente
	Oggetti oggetto = oggettiService.findById(mercati.getOggetto().getId());
	mercati.setOggetto(oggetto);
	// recupero la manifestazione passata
	Manifestazioni manifestazione = manifestazioniService.findById(mercati.getManifestazione().getCodice());
	mercati.setManifestazione(manifestazione);
	fixMergeEntityProperty(mercati);
	try {
	    mercatiService.update(mercati);
	} catch (Exception e) {
	    // recupero le manifestazioni
	    copyErrorsToBindingResult(result, mercati, e);
	    fixRenderEntityProperty(mercati);
	    setPageAttributes(model, mercati);
	    return "mercati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercati.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("mercati") Mercati mercati, BindingResult result, SessionStatus status) {

	Mercati objToDelete = mercatiService.findById(mercati.getId());
	try {
	    mercatiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    setPageAttributes(model, mercati);
	    fixRenderEntityProperty(mercati);
	    return "mercati/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String createAltridati(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Mercati mercati = mercatiService.findById(id);
	fixRenderEntityProperty(mercati);
	model.addAttribute("mercati", mercati);
	setPageAttributes(model);
	return "mercati/formAltridati";
    }

    @RequestMapping
    public String insertAltridati(Model model, @ModelAttribute("mercati") Mercati mercati, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(mercati);
	try {
	    mercatiService.update(mercati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercati, e);
	    fixRenderEntityProperty(mercati);
	    return "mercati/formAltridati";
	}
	status.setComplete();
	return "redirect:createAltridati.htm?codice=" + mercati.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public ModelMap listgiorni(@RequestParam("codicemercato") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Mercati mercato = mercatiService.findById(new PkId(codice));
	List<MercatiUso> mercatiusoList = mercatiUsoService.findByMercato(mercato);
	ModelMap model = new ModelMap(mercatiusoList);
	boolean export = createJMesaExport(request, response, mercatiusoList);
	if (export)
	    return null;
	model.addAttribute("mercatiusoList", mercatiusoList);
	model.addAttribute("mercati", mercato);
	return model;
    }

    @RequestMapping
    public String createGiorni(@RequestParam("codicemercato") Integer codice, Model model, HttpServletRequest request) {

	MercatiUso mercatiUso = new MercatiUso();
	// setto il mercato corrente
	PkId id = new PkId(codice);
	Mercati mercati = mercatiService.findById(id);
	// recupero la lista dei giorni della settimana
	List<Giornisettimana> listagiorni = giornisectimanaService.findAll(null, null);
	mercatiUso.setMercati(mercati);
	fixRenderEntityProperty(mercati);
	fixRenderMercatiusoProperty(mercatiUso);
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatiuso", mercatiUso);
	model.addAttribute("listagiorni", listagiorni);
	setPageAttributes(model);
	return "mercati/formGiorni";
    }

    @RequestMapping
    public String insertGiorni(Model model, @ModelAttribute("mercatiuso") MercatiUso mercatiUso, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// recupero il giorno della settimana
	if (mercatiUso.getGiornisettimana().getId() != null) {
	    Giornisettimana giornisettimana = giornisectimanaService.findById(mercatiUso.getGiornisettimana().getId());
	    mercatiUso.setGiornisettimana(giornisettimana);
	}
	// recupero la conecssione uso
	if (mercatiUso.getConcessioniuso().getId() != null && mercatiUso.getConcessioniuso().getId().getCodice() != null) {
	    Concessioniuso concessioniuso = concessioniusoService.findById(new PkId(mercatiUso.getConcessioniuso().getId().getCodice()));
	    mercatiUso.setConcessioniuso(concessioniuso);
	}
	fixMergeMercatiusoProperty(mercatiUso);
	try {
	    mercatiUsoService.insert(mercatiUso);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiUso, e);
	    fixRenderMercatiusoProperty(mercatiUso);
	    // recupero la lista dei giorni della settimana
	    List<Giornisettimana> listagiorni = giornisectimanaService.findAll(null, null);
	    model.addAttribute("listagiorni", listagiorni);
	    model.addAttribute("mercati", mercatiUso.getMercati());
	    return "mercati/formGiorni";
	}
	status.setComplete();
	return "redirect:viewGiorni.htm?codicemercato=" + mercatiUso.getMercati().getId().getCodice() + "&codicemercatouso="
		+ mercatiUso.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String viewGiorni(@RequestParam("codicemercato") Integer codicemercato, @RequestParam("codicemercatouso") Integer codicemercatouso,
	    Model model) {

	PkId id = new PkId(codicemercato);
	Mercati mercati = mercatiService.findById(id);
	MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codicemercatouso));
	// recupero la lista dei giorni della settimana
	List<Giornisettimana> listagiorni = giornisectimanaService.findAll(null, null);
	fixRenderMercatiusoProperty(mercatiUso);
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatiuso", mercatiUso);
	model.addAttribute("listagiorni", listagiorni);
	setPageAttributes(model);
	return "mercati/formGiorni";
    }

    @RequestMapping
    public String updateGiorni(Model model, @ModelAttribute("mercatiuso") MercatiUso mercatiUso, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// recupero il giorno della settimana
	if (mercatiUso.getGiornisettimana().getId() != null) {
	    Giornisettimana giornisettimana = giornisectimanaService.findById(mercatiUso.getGiornisettimana().getId());
	    mercatiUso.setGiornisettimana(giornisettimana);
	}
	// recupero la conecssione uso
	if (mercatiUso.getConcessioniuso().getId() != null && mercatiUso.getConcessioniuso().getId().getCodice() != null) {
	    Concessioniuso concessioniuso = concessioniusoService.findById(new PkId(mercatiUso.getConcessioniuso().getId().getCodice()));
	    mercatiUso.setConcessioniuso(concessioniuso);
	}
	fixMergeMercatiusoProperty(mercatiUso);
	try {
	    mercatiUsoService.update(mercatiUso);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiUso, e);
	    fixRenderMercatiusoProperty(mercatiUso);
	    List<Giornisettimana> listagiorni = giornisectimanaService.findAll(null, null);
	    model.addAttribute("listagiorni", listagiorni);
	    model.addAttribute("mercati", mercatiUso.getMercati());
	    return "mercati/formGiorni";
	}
	status.setComplete();
	return "redirect:viewGiorni.htm?codicemercato=" + mercatiUso.getMercati().getId().getCodice() + "&codicemercatouso="
		+ mercatiUso.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteGiorni(Model model, @ModelAttribute("mercatiuso") MercatiUso mercatiuso, BindingResult result, SessionStatus status) {

	MercatiUso objToDelete = mercatiUsoService.findById(mercatiuso.getId());
	try {
	    mercatiUsoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderMercatiusoProperty(mercatiuso);
	    List<Giornisettimana> listagiorni = giornisectimanaService.findAll(null, null);
	    MercatiUso mercatiusoTemp = mercatiUsoService.findById(mercatiuso.getId());
	    model.addAttribute("listagiorni", listagiorni);
	    model.addAttribute("mercati", mercatiusoTemp.getMercati());
	    return "mercati/formGiorni";
	}
	return "redirect:listgiorni.htm?codicemercato=" + mercatiuso.getMercati().getId().getCodice();
    }

    @RequestMapping
    public String createConsorzio(@RequestParam("codicemercato") Integer codice, Model model, HttpServletRequest request) {

	MercatiConsorzi mercatic = new MercatiConsorzi();
	// setto il mercato corrente
	PkId id = new PkId(codice);
	Mercati mercati = mercatiService.findById(id);
	// recupero la lista dei giorni della settimana
	mercatic.setMercato(mercati);
	fixRenderEntityProperty(mercati);
	fixRenderMercatiConsorzi(mercatic);
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercaticonsorzi", mercatic);
	setPageAttributes(model);
	return "mercati/formConsorzi";
    }

    @RequestMapping
    public String insertConsorzio(Model model, @ModelAttribute("mercaticonsorzi") MercatiConsorzi mercatiConsorzi, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// recupero il giorno della settimana
	try {
	    mercatiConsorziService.insert(mercatiConsorzi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiConsorzi, e);
	    fixRenderMercatiConsorzi(mercatiConsorzi);
	    model.addAttribute("mercati", mercatiConsorzi.getMercato());
	    return "mercati/formConsorzi";
	}
	status.setComplete();
	return "redirect:viewConsorzio.htm?codicemercato=" + mercatiConsorzi.getMercato().getId().getCodice() + "&codice="
		+ mercatiConsorzi.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String viewConsorzio(@RequestParam("codicemercato") Integer codicemercato, @RequestParam("codice") Integer codice, Model model,
	    HttpServletRequest request) {

	PkId id = new PkId(codicemercato);
	Mercati mercati = mercatiService.findById(id);
	MercatiConsorzi mercatic = mercatiConsorziService.findById(new PkId(codice));
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercaticonsorzi", mercatic);
	setPageAttributes(model);
	fixRenderMercatiConsorzi(mercatic);
	return "mercati/formConsorzi";
    }

    private void fixRenderMercatiConsorzi(MercatiConsorzi mercatic) {

	if (mercatic.getConsorzio() == null) {
	    mercatic.setConsorzio(new Anagrafe());
	}
    }

    @RequestMapping
    public String updateConsorzio(Model model, @ModelAttribute("mercaticonsorzi") MercatiConsorzi mercatiConsorzi, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// recupero il giorno della settimana
	try {
	    mercatiConsorziService.update(mercatiConsorzi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiConsorzi, e);
	    fixRenderMercatiConsorzi(mercatiConsorzi);
	    model.addAttribute("mercati", mercatiConsorzi.getMercato());
	    return "mercati/formGiorni";
	}
	status.setComplete();
	return "redirect:viewConsorzio.htm?codicemercato=" + mercatiConsorzi.getMercato().getId().getCodice() + "&codice="
		+ mercatiConsorzi.getId().getCodice() + "&status_msg=02";
    }

    // 
    @RequestMapping
    public String deleteMercatiConsorzioList(@RequestParam("codicemercato") Integer codicemercato, @RequestParam("codice") Integer codice,
	    Model model, HttpServletRequest request) {

	MercatiConsorzi objToDelete = mercatiConsorziService.findById(new PkId(codice));
	try {
	    mercatiConsorziService.delete(objToDelete);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Non è stato possibile cancellare il consorzio a causa di: " + e.getMessage());
	    return "redirect:view.htm?codice=" + codicemercato + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + codicemercato + "&status_msg=05";
    }

    @RequestMapping
    public ModelMap listmercatistradario(@RequestParam("codicemercato") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Mercati mercato = mercatiService.findById(new PkId(codice));
	List<Mercatistradario> mercatistradarioList = mercatistradarioService.findByMercato(mercato);
	ModelMap model = new ModelMap(mercatistradarioList);
	boolean export = createJMesaExport(request, response, mercatistradarioList);
	if (export)
	    return null;
	model.addAttribute("mercatistradarioList", mercatistradarioList);
	model.addAttribute("mercati", mercato);
	return model;
    }

    @RequestMapping
    public String createMercatistradario(@RequestParam("codicemercato") Integer codice, Model model, HttpServletRequest request) {

	Mercatistradario mercatistradario = new Mercatistradario();
	// setto il mercato corrente
	PkId id = new PkId(codice);
	Mercati mercati = mercatiService.findById(id);
	fixRenderEntityProperty(mercati);
	fixRenderMercatistradarioProperty(mercatistradario);
	mercatistradario.setMercato(mercati);
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatistradario", mercatistradario);
	setPageAttributes(model);
	return "mercati/formMercatistradario";
    }

    @RequestMapping
    public String insertMercatistradario(Model model, @ModelAttribute("mercatistradario") Mercatistradario mercatistradario, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// recupero lo stradario
	if (mercatistradario.getStradario().getId() != null) {
	    Stradario stradario = stradarioService.findById(mercatistradario.getStradario().getId());
	    mercatistradario.setStradario(stradario);
	}
	fixMergeMercatistradarioProperty(mercatistradario);
	try {
	    mercatistradarioService.insert(mercatistradario);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatistradario, e);
	    fixRenderMercatistradarioProperty(mercatistradario);
	    model.addAttribute("mercati", mercatistradario.getMercato());
	    return "mercati/formMercatistradario";
	}
	status.setComplete();
	return "redirect:viewMercatistradario.htm?codicemercato=" + mercatistradario.getMercato().getId().getCodice() + "&codicemercatostradario="
		+ mercatistradario.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String viewMercatistradario(@RequestParam("codicemercato") Integer codicemercato,
	    @RequestParam("codicemercatostradario") Integer codicemercatostradario, Model model) {

	PkId id = new PkId(codicemercato);
	Mercati mercati = mercatiService.findById(id);
	Mercatistradario mercatistradario = mercatistradarioService.findById(new PkId(codicemercatostradario));
	fixRenderMercatistradarioProperty(mercatistradario);
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatistradario", mercatistradario);
	setPageAttributes(model);
	return "mercati/formMercatistradario";
    }

    @RequestMapping
    public String updateMercatistradario(Model model, @ModelAttribute("mercatistradario") Mercatistradario mercatistradario, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// recupero lo stradario
	if (mercatistradario.getStradario().getId() != null) {
	    Stradario stradario = stradarioService.findById(mercatistradario.getStradario().getId());
	    mercatistradario.setStradario(stradario);
	}
	fixMergeMercatistradarioProperty(mercatistradario);
	try {
	    mercatistradarioService.update(mercatistradario);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatistradario, e);
	    fixRenderMercatistradarioProperty(mercatistradario);
	    model.addAttribute("mercati", mercatistradario.getMercato());
	    return "mercati/formMercatistradario";
	}
	status.setComplete();
	return "redirect:viewMercatistradario.htm?codicemercato=" + mercatistradario.getMercato().getId().getCodice() + "&codicemercatostradario="
		+ mercatistradario.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteMercatistradario(Model model, @ModelAttribute("mercatistradario") Mercatistradario mercatistradario, BindingResult result,
	    SessionStatus status) {

	Mercatistradario objToDelete = mercatistradarioService.findById(mercatistradario.getId());
	try {
	    mercatistradarioService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderMercatistradarioProperty(mercatistradario);
	    model.addAttribute("mercati", objToDelete.getMercato());
	    return "mercati/formMercatistradario";
	}
	return "redirect:listmercatistradario.htm?codicemercato=" + mercatistradario.getMercato().getId().getCodice();
    }

    // Quando faccio la cancellazione direttamente dalla lista.Sulla cancellazione di uno stradario non ci sono
    // controlli
    @RequestMapping
    public String deleteMercatistradariofromList(@RequestParam("codice") Integer codice) {

	Mercatistradario objToDelete = mercatistradarioService.findById(new PkId(codice));
	mercatistradarioService.delete(objToDelete);
	return "redirect:listmercatistradario.htm?codicemercato=" + objToDelete.getMercato().getId().getCodice();
    }

    @RequestMapping
    public String updateCampoDinamicoMercato(@RequestParam("codiceMercato") Integer codicemercato,
	    @RequestParam("codiceCampoDinamico") Integer codiceCampoDinamico, HttpServletRequest request) {

	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(codiceCampoDinamico));
	mercati.setDyn2Campi(dyn2Campi);
	try {
	    mercatiService.update(mercati);
	} catch (Exception e) {
	    //	    copyErrorsToBindingResult(result, mercati, e);
	    //	    fixRenderEntityProperty(mercati);
	    //	    setPageAttributes(model, mercati);
	    //	    return "mercati/form";
	}
	return "redirect:../mercatid2cassegnaz/list.htm?codiceMercato=" + mercati.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String updateCampoDinamicoPreferenzaPosteggioMercato(@RequestParam("codiceMercato") Integer codicemercato,
	    @RequestParam("codiceCampoDinamico") Integer codiceCampoDinamico, HttpServletRequest request) {

	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(codiceCampoDinamico));
	mercati.setDyn2CampiPrefPosteggio(dyn2Campi);
	try {
	    mercatiService.update(mercati);
	} catch (Exception e) {
	    //	    copyErrorsToBindingResult(result, mercati, e);
	    //	    fixRenderEntityProperty(mercati);
	    //	    setPageAttributes(model, mercati);
	    //	    return "mercati/form";
	}
	return "redirect:../mercatid2cassegnaz/list.htm?codiceMercato=" + mercati.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deletePreferenzaUsoDyn2Dati(@RequestParam("codice") Integer codicemercato, HttpServletRequest request) {

	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	mercati.setDyn2Campi(null);
	try {
	    mercatiService.update(mercati);
	} catch (Exception e) {
	    //	    copyErrorsToBindingResult(result, mercati, e);
	    //	    fixRenderEntityProperty(mercati);
	    //	    setPageAttributes(model, mercati);
	    //	    return "mercati/form";
	}
	return "redirect:../mercatid2cassegnaz/list.htm?codiceMercato=" + mercati.getId().getCodice() + "&status_msg=02";
    }

    @Override
    protected void fixMergeEntityProperty(Mercati entity) {

	if (entity.getOggetto() != null && entity.getOggetto().getId() != null && entity.getOggetto().getId().getCodice() == null) {
	    entity.setOggetto(null);
	}
	if (entity.getManifestazione() != null && entity.getManifestazione().getCodice() == null) {
	    entity.setManifestazione(null);
	}
	if (entity.getDyn2Campi() != null && entity.getDyn2Campi().getId().getCodice() == null) {
	    entity.setDyn2Campi(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Mercati entity) {

	if (entity.getOggetto() == null) {
	    entity.setOggetto(new Oggetti());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	if (entity.getManifestazione() == null) {
	    entity.setManifestazione(new Manifestazioni());
	}
	if (entity.getDyn2Campi() == null) {
	    entity.setDyn2Campi(new Dyn2Campi());
	}
	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
    }

    protected void fixRenderMercatiusoProperty(MercatiUso entity) {

	if (entity.getConcessioniuso() == null) {
	    entity.setConcessioniuso(new Concessioniuso());
	}
	if (entity.getGiornisettimana() == null) {
	    entity.setGiornisettimana(new Giornisettimana());
	}
	if (entity.getMercati() == null) {
	    entity.setMercati(new Mercati());
	}
    }

    protected void fixMergeMercatiusoProperty(MercatiUso entity) {

	if (entity.getConcessioniuso() != null && entity.getConcessioniuso().getId() != null
		&& entity.getConcessioniuso().getId().getCodice() == null) {
	    entity.setConcessioniuso(null);
	}
	if (entity.getGiornisettimana() != null && entity.getGiornisettimana().getId() == null) {
	    entity.setGiornisettimana(null);
	}
	if (entity.getMercati() != null && entity.getMercati().getId() != null && entity.getMercati().getId().getCodice() == null) {
	    entity.setMercati(null);
	}
    }

    protected void fixRenderMercatistradarioProperty(Mercatistradario entity) {

	if (entity.getStradario() == null) {
	    entity.setStradario(new Stradario());
	}
	if (entity.getMercato() == null) {
	    entity.setMercato(new Mercati());
	}
    }

    protected void fixMergeMercatistradarioProperty(Mercatistradario entity) {

	if (entity.getStradario() != null && entity.getStradario().getId() != null && entity.getStradario().getId().getCodice() == null) {
	    entity.setStradario(null);
	}
	if (entity.getMercato() != null && entity.getMercato().getId() != null && entity.getMercato().getId().getCodice() == null) {
	    entity.setMercato(null);
	}
    }

    protected void setPageAttributes(Model model, Mercati entity) {

	List<Manifestazioni> listmanifestazioni = manifestazioniService.findAll(null, null);
	model.addAttribute("listmanifestazioni", listmanifestazioni);
	List<MercatiConsorzi> mercatiConsorzis = mercatiConsorziService.findByCodiceMercato(entity.getId().getCodice());
	model.addAttribute("consorzis", mercatiConsorzis);
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
