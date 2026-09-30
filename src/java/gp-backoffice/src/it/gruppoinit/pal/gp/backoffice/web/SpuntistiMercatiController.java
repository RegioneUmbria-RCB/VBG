package it.gruppoinit.pal.gp.backoffice.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.SpuntistiMercati;
import it.gruppoinit.pal.gp.core.domain.helper.SpuntistiMercatiDTO;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.SpuntistiMercatiService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.SpuntistiMercatiHelper;
import it.gruppoinit.pal.gp.core.utils.LoggerDisabilitaSpuntisti;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("spuntistimercati")
public class SpuntistiMercatiController extends BaseController<SpuntistiMercati> {

    private static final Logger log = LoggerFactory.getLogger(SpuntistiMercatiController.class);
    @Autowired
    private SpuntistiMercatiService spuntistimercatiService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private MercatiConfigurazioneService mercatiConfigurazioneService;

    @RequestMapping
    public String list(Model model, @RequestParam(value = "codiceIstanza", required = true) Integer codiceIstanza, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
	List<Autorizzazioni> autorizzazionis = autorizzazioniService.findByIstanza(codiceIstanza);
	List<SpuntistiMercati> mercatiInCuiESpuntista = spuntistimercatiService.findByIstanza(codiceIstanza, null);
	model.addAttribute("mercatiInCuiESpuntista", mercatiInCuiESpuntista);
	model.addAttribute("autorizzazionis", autorizzazionis);
	model.addAttribute("istanza", istanze);
	return "spuntistimercati/list";
    }

    @RequestMapping
    public String create(Model model, @RequestParam("codice") Integer codice) {

	SpuntistiMercati spuntistimercati = new SpuntistiMercati();
	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(codice));
	spuntistimercati.setAutorizzazioni(autorizzazioni);
	fixRenderEntityProperty(spuntistimercati);
	model.addAttribute("spuntistimercati", spuntistimercati);
	model.addAttribute("codice", codice);
	setPageAttributes(model);
	return "spuntistimercati/form";
    }

    @RequestMapping
    public String ajaxMercatiSpuntisti(Model model, @RequestParam(required = false, value = "codiceIstanza") Integer codiceIstanza,
	    @RequestParam("idautorizzazione") Integer idautorizzazione,
	    @RequestParam(value = "codiceAnagrafe", required = false) Integer codiceAnagrafe, HttpServletRequest request,
	    HttpServletResponse response) {

	// .
	// Verifico se la configurazione su mercaticonfigurazione per la gestione della spunta è attiva 
	boolean isAttivaConfigurazioneSpuntisti = mercatiConfigurazioneService.isAttivaConfigurazioneSpuntista();
	//.
	// Da trasformare in oggetto delle tabella che raccoglie le info sui mercati in cui è già spuntista
	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(idautorizzazione));
	List<SpuntistiMercatiHelper> mercatiSpuntistiHelpers = new ArrayList<SpuntistiMercatiHelper>();
	if (codiceIstanza != null) {
	    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	    mercatiSpuntistiHelpers = spuntistimercatiService.findMercatiRichiestaSpuntaPerIstanza(codiceIstanza, idautorizzazione);
	    model.addAttribute("istanza", istanza);
	}
	// TODO recuperare i mercati per cui è spuntista raggruppandoli per istanza (e aut??)
	List<SpuntistiMercati> mercatiInCuiESpuntista = spuntistimercatiService.findByAutorizzazioni(idautorizzazione);
	model.addAttribute("autorizzazioni", autorizzazioni);
	model.addAttribute("mercatiSpuntistiHelpers", mercatiSpuntistiHelpers);
	model.addAttribute("mercatiInCuiESpuntista", mercatiInCuiESpuntista);
	model.addAttribute("codiceAnagrafe", codiceAnagrafe);
	model.addAttribute("isAttivaConfigurazioneSpuntisti", isAttivaConfigurazioneSpuntisti);
	return "spuntistimercati/ajaxDettaglioMercatiSpuntisti";
    }

    @RequestMapping
    public String insertSpuntistiMercati(Model model, @RequestParam(value = "codiceIstanza") Integer codiceIstanza,
	    @RequestParam("idautorizzazione") Integer idautorizzazione, HttpServletRequest request, HttpServletResponse response) {

	try {
	    spuntistimercatiService.insertSpuntistiMercati(codiceIstanza, idautorizzazione);
	} catch (Exception e) {
	    // TODO ----
	}
	String redirect = "redirect:../spuntistimercati/list.htm?codiceIstanza=" + codiceIstanza + "&status_msg=01";
	return redirect;
    }

    @RequestMapping
    public String ajaxListmercatispunta(Model model, @RequestParam("idautorizzazione") Integer idautorizzazione, HttpServletRequest request,
	    HttpServletResponse response) {

	try {
	    List<SpuntistiMercati> mercatiInCuiESpuntista = spuntistimercatiService.findByAutorizzazioni(idautorizzazione);
	    model.addAttribute("mercatiInCuiESpuntista", mercatiInCuiESpuntista);
	} catch (Exception e) {
	    //TODO ---
	}
	return "spuntistimercati/ajaxListaMercatiSpuntisti";
    }

    @RequestMapping
    public void ajaxAddSpuntaMercato(@RequestParam("codiceMercato") Integer codiceMercato,
	    @RequestParam(required = false, value = "codiceUso") Integer codiceUso, @RequestParam("idAutorizzazione") Integer idAutorizzazione,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	String result = "OK";
	try {
	    if (codiceMercato != null) {
		Autorizzazioni aut = autorizzazioniService.findById(new PkId(idAutorizzazione));
		Mercati m = mercatiService.findById(new PkId(codiceMercato));
		boolean trovato = spuntistimercatiService.existRecordPerAutMercatoAndUsoAttivo(idAutorizzazione, codiceMercato, codiceUso);
		if (trovato) {
		    result = "Attenzione!! L'autorizzazione  " + aut.getTransientEstremiAut() + " e' gia' assegnata al mercato per la spunta";
		} else {
		    if (codiceUso == null) {
			// devo controllare che il mercato ha un uso e non è stato impostato
			if (!m.getMercatiUsos().isEmpty()) {
			    result = "Attenzione!! Per il mercato " + m.getDescrizione() +
				     " scelto deve essere impostato anche il giorno prima dell'inserimento";
			} else {
			    MercatiUso mu = mercatiUsoService.findById(new PkId(codiceMercato));
			    SpuntistiMercati spuntistiMercati = populate(m, mu, aut);
			    spuntistimercatiService.insert(spuntistiMercati);
			}
		    } else {
			MercatiUso mu = mercatiUsoService.findById(new PkId(codiceMercato));
			SpuntistiMercati spuntistiMercati = populate(m, mu, aut);
			spuntistimercatiService.insert(spuntistiMercati);
		    }
		}
	    } else {
		result = "Attenzione!! Non è stato selezionato il mercato";
	    }
	} catch (Exception e) {
	    result = "Si e' verificato un errore durante l'inserimento del dato. (dettaglio:" + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public void ajaxEliminaSpuntista(@RequestParam("idRiga") Integer idRiga, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	String result = "OK";
	try {
	    SpuntistiMercati entity = spuntistimercatiService.findById(new PkId(idRiga));
	    spuntistimercatiService.delete(entity);
	} catch (Exception e) {
	    result = "Si e' verificato un errore nella cancellazione del dato. (dettaglio:" + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    private SpuntistiMercati populate(Mercati mercati, MercatiUso mercatiUso, Autorizzazioni autorizzazioni) {

	SpuntistiMercati spuntistiMercati = new SpuntistiMercati();
	spuntistiMercati.setAutorizzazioni(autorizzazioni);
	spuntistiMercati.setFlgAttivo(true);
	spuntistiMercati.setMercati(mercati);
	spuntistiMercati.setDataRegistrazione(new Date());
	spuntistiMercati.setDataDisattivazione(null);
	if (mercatiUso != null) {
	    spuntistiMercati.setMercatiUso(mercatiUso);
	}
	return spuntistiMercati;
    }

    //  @RequestMapping
    //    public String listSpuntistiMercato(@RequestParam("codiceMercato") Integer codiceMercato, Model model) {
    //
    //	//List<SpuntistiMercatiDTO> l = spuntistiMercatiService.findSpuntistaAssenteDa(110, 103, 365);
    //	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
    //	List<MercatiUso> mercatiUsos = mercatiUsoService.findByMercato(mercati);
    //	if (mercatiUsos.size() == 1) {
    //	    return "redirect:viewSpuntistiMercato.htm?codiceMercato=" + mercati.getId().getCodice() + "&codiceuso="
    //		    + mercatiUsos.get(0).getId().getCodice();
    //	} else {
    //	    model.addAttribute("mercatiUsos", mercatiUsos);
    //	    model.addAttribute("mercati", mercati);
    //	    return "mercati/listSpuntistiMercato";
    //	}
    //    }
    @RequestMapping
    public String viewSpuntistiMercato(@RequestParam("codiceMercato") Integer codiceMercato, @RequestParam("codiceuso") Integer codiceuso,
	    @RequestParam(required = false, value = "attivi") Boolean isAttivi, Model model) {

	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceuso));
	List<SpuntistiMercati> listSpuntistiMercati = spuntistimercatiService.findByMercatoEdUso(codiceMercato, codiceuso, isAttivi, true);
	model.addAttribute("listSpuntistiMercati", listSpuntistiMercati);
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatiUso", mercatiUso);
	model.addAttribute("isAttivi", isAttivi);
	//	List<ChiaveValoreBean<String, List<SpuntistiMercati>>> listSpuntistiMercati = new ArrayList<ChiaveValoreBean<String, List<SpuntistiMercati>>>();
	//	ChiaveValoreBean<String, List<SpuntistiMercati>> bean = null;
	//	for (MercatiUso mercatiUso : mercatiUsos) {
	//	    bean = new ChiaveValoreBean<String, List<SpuntistiMercati>>();
	//	    List<SpuntistiMercati> spuntistiMercatis = spuntistiMercatiService.findByMercatoEdUso(codiceMercato, mercatiUso.getId().getCodice());
	//	    bean.setChiave(mercatiUso.getDescrizione());
	//	    bean.setValore(spuntistiMercatis);
	//	    listSpuntistiMercati.add(bean);
	//	}
	//	model.addAttribute("listSpuntistiMercati", listSpuntistiMercati);
	//	model.addAttribute("mercati", mercati);
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findConfigurazione();
	boolean mostraSpuntistiDaDisabilitare = false;
	if (mercatiConfigurazione != null) {
	    if (mercatiConfigurazione.getGgAssenza() != null && mercatiConfigurazione.getGgAssenza() > 0) {
		mostraSpuntistiDaDisabilitare = true;
	    }
	}
	boolean isGraduatoriaSpuntistiAttiva = MercatiConfigurazione.checkConfigurazioneGradSpuntistiPerManifestazioni(mercatiConfigurazione);
	model.addAttribute("isGraduatoriaSpuntistiAttiva", isGraduatoriaSpuntistiAttiva);
	model.addAttribute("mostraSpuntistiDaDisabilitare", mostraSpuntistiDaDisabilitare);
	return "spuntistimercati/viewSpuntistiMercato";
    }

    @RequestMapping
    public String viewSpuntistiMercatoDaDisabilitare(@RequestParam("codiceMercato") Integer codiceMercato,
	    @RequestParam("codiceuso") Integer codiceuso, Model model) {

	Integer ggAssenzaPermssi = mercatiConfigurazioneService.getGGAssenzaMassima();
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceuso));
	//	List<SpuntistiMercatiDTO> listSpuntistiDadisabilitare = spuntistimercatiService.findSpuntistaAssenteDa(codiceMercato, codiceuso,
	//		ggAssenzaPermssi);
	List<SpuntistiMercatiDTO> listSpuntistiDadisabilitare = spuntistimercatiService.findSpuntistaAssenteDaWithDataUltimaPresenza(codiceMercato,
		codiceuso, ggAssenzaPermssi);
	model.addAttribute("listSpuntistiDadisabilitare", listSpuntistiDadisabilitare);
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatiUso", mercatiUso);
	return "spuntistimercati/viewSpuntistiMercatoDaDisabilitare";
    }

    @RequestMapping
    public String updateSpuntistiMercatoDaDisabilitare(@RequestParam("codiceMercato") Integer codiceMercato,
	    @RequestParam("codiceuso") Integer codiceuso, Model model) {

	Integer ggAssenzaPermssi = mercatiConfigurazioneService.getGGAssenzaMassima();
	try {
	    spuntistimercatiService.updateDisabilitaPerAssenza(codiceMercato, codiceuso, ggAssenzaPermssi);
	    String messageOk = getMessageFromBundle("label.spuntisti_disabilitati", null);
	    FlashMessages.getInfos().add(messageOk);
	    Responsabili r = getCurrentlyAuthenticatedUserDetails();
	    LoggerDisabilitaSpuntisti.logDisabilitaSpuntistiMercatiAndFiere(LoggerDisabilitaSpuntisti._DISABILITA_SPUNTISTA_MERCATI,
		    r.getResponsabile(), codiceMercato.toString(), codiceuso.toString());
	} catch (Exception e) {
	    log.error("updateSpuntistiMercatoDaDisabilitare# {} ", e);
	    String messageKO = getMessageFromBundle("label.spuntisti_disabilitati_errore", new Object[] { e.getMessage() });
	    FlashMessages.getInfos().add(messageKO);
	}
	return "redirect:../spuntistimercati/viewSpuntistiMercato.htm?codiceMercato=" + codiceMercato + "&codiceuso=" + codiceuso + "&status_msg=02";
    }

    @RequestMapping
    public String disabilitaSpuntistaFieraPerTermine(@RequestParam("codiceMercato") Integer codiceMercato,
	    @RequestParam("codiceuso") Integer codiceuso, Model model) {

	try {
	    spuntistimercatiService.updatedisabilitaSpuntistaFieraPerTermine(codiceMercato, codiceuso);
	    String messageOk = getMessageFromBundle("label.spuntisti_disabilitati", null);
	    FlashMessages.getInfos().add(messageOk);
	    Responsabili r = getCurrentlyAuthenticatedUserDetails();
	    LoggerDisabilitaSpuntisti.logDisabilitaSpuntistiMercatiAndFiere(LoggerDisabilitaSpuntisti._DISABILITA_SPUNTISTA_FIERE,
		    r.getResponsabile(), codiceMercato.toString(), codiceuso.toString());
	} catch (Exception e) {
	    log.error("disabilitaSpuntistaFieraPerTermine# {} ", e);
	    String messageKO = getMessageFromBundle("label.spuntisti_disabilitati_errore", new Object[] { e.getMessage() });
	    FlashMessages.getInfos().add(messageKO);
	}
	return "redirect:../spuntistimercati/viewSpuntistiMercato.htm?codiceMercato=" + codiceMercato + "&codiceuso=" + codiceuso;
    }

    @Override
    protected void fixMergeEntityProperty(SpuntistiMercati entity) {

    }

    @Override
    protected void fixRenderEntityProperty(SpuntistiMercati entity) {

	if (entity.getAutorizzazioni() == null) {
	    entity.setAutorizzazioni(new Autorizzazioni());
	}
	if (entity.getIstanze() == null) {
	    entity.setIstanze(new Istanze());
	}
	if (entity.getMercati() == null) {
	    entity.setMercati(new Mercati());
	}
	if (entity.getMercatiUso() == null) {
	    entity.setMercatiUso(new MercatiUso());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
