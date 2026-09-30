package it.gruppoinit.pal.gp.backoffice.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiEnum;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggio;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.Riepilogomercato;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniService;

@Controller
@SessionAttributes("mercatipresenzeT")
public class ContabilitamercatiController extends BaseController<RegistrazioniFilter> {

    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private RegistrazioniService registrazioniService;
    @Autowired
    private MercatiUsoService mercatiUsoService;

    @RequestMapping
    public String createSearch(Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	MercatipresenzeT mercatipresenzeT = new MercatipresenzeT();
	List<MercatipresenzeT> anniList = mercatipresenzeTService.findAnniMercatiPresenti();
	model.addAttribute("anniList", anniList);
	model.addAttribute("mercatipresenzeT", mercatipresenzeT);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_GESTIONE_RICERCHE, "1", request);
	setPageAttributes(model);
	return "contabilitamercati/formsearch";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String search(@ModelAttribute("mercatipresenzeT") MercatipresenzeT mercatipresenzeT, BindingResult result, SessionStatus status,
	    Model model, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	Mercati mercati = mercatiService.findById(new PkId(mercatipresenzeT.getMercato().getId().getCodice()));
	List<Riepilogomercato> riepilogomercatiList = null;
	if (mercati == null) {
	    riepilogomercatiList = new ArrayList<Riepilogomercato>();
	    List<Mercati> mercatiList = mercatiService.findByFlagContabilita(null, true, MercatiEnum.ACTIVE);
	    for (Mercati mercati2 : mercatiList) {
		Set<MercatiUso> mercatiUsoSet = mercati2.getMercatiUsos();
		List<Riepilogomercato> answer = mercatipresenzeTService.findByFilterMercatoOrAnnoGroupByMercatoUso(mercati2,
			mercatipresenzeT.getAnno());
		if (!(answer == null || answer.size() == 0)) {
		    riepilogomercatiList.addAll(answer);
		} else {
		    for (MercatiUso mercatiUso : mercatiUsoSet) {
			Riepilogomercato riepilogo = new Riepilogomercato();
			riepilogo.setMercati(mercati2);
			riepilogo.setMercatiUso(mercatiUso);
			riepilogomercatiList.add(riepilogo);
		    }
		}
	    }
	} else {
	    riepilogomercatiList = mercatipresenzeTService.findByFilterMercatoOrAnnoGroupByMercatoUso(mercati, mercatipresenzeT.getAnno());
	    if (riepilogomercatiList == null || riepilogomercatiList.size() == 0) {
		Set<MercatiUso> mercatiUsoSet = mercati.getMercatiUsos();
		for (MercatiUso mercatiUso : mercatiUsoSet) {
		    Riepilogomercato riepilogo = new Riepilogomercato();
		    riepilogo.setMercati(mercati);
		    riepilogo.setMercatiUso(mercatiUso);
		    riepilogomercatiList.add(riepilogo);
		}
	    }
	}
	model.addAttribute("riepilogomercatiList", riepilogomercatiList);
	return "contabilitamercati/listcontabilitamercati";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String situazionecontabilemercatouso(Model model, @RequestParam("codice") Integer codice, @RequestParam("codiceuso") Integer codiceuso) {

	// §§§BEGIN§§§
	Mercati mercati = mercatiService.findById(new PkId(codice));
	MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceuso));
	// devo mettere il risultato in un vector perchè la jsp a cui mi ridirigo scorre un vettore di suituazioni
	// contabili
	// in quanto per la sua prima funzionalità doveva mostrare le situazioni contabili del mercato per tutti gli usi
	// mentre in questo caso devo visualizzare la situazione contabile di un mercato e per uno specifico uso
	List<Posteggio> list = registrazioniService.findSituazioneContabileByMercatoAndPosteggio(mercati, mercatiUso);
	Vector<List<Posteggio>> vector = new Vector<List<Posteggio>>();
	vector.add(list);
	model.addAttribute("mercato", mercati);
	model.addAttribute("posteggiolistmercatouso", vector);
	return "calendariomercato/situazionecontabile";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(RegistrazioniFilter entity) {

    }

    @Override
    protected void fixRenderEntityProperty(RegistrazioniFilter entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
