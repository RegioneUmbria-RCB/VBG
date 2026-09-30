package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocAteco;
import it.gruppoinit.pal.gp.core.domain.AlberoprocAtecoId;
import it.gruppoinit.pal.gp.core.domain.Ateco;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AlberoprocAtecoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AtecoService;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 
 * @author Luca Proietti
 */
@Controller
@SessionAttributes("alberoprocateco")
public class AlberoprocAtecoController extends BaseController<AlberoprocAteco> {

    @Autowired
    private AlberoprocAtecoService alberoprocatecoService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AtecoService atecoService;

    @RequestMapping
    public String assegnaAteco(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	Ateco ateco = new Ateco();
	model.addAttribute("alberoproc", alberoproc);
	model.addAttribute("ateco", ateco);
	setPageAttributes(model);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ATECO_SOLO_MACROCATEGORIE, "1", request);
	return "alberoprocateco/form";
    }

    @RequestMapping
    public void ajaxAssegnaAteco(@RequestParam("codiceAteco") Integer codiceAteco, @RequestParam("codiceAlberoproc") Integer codiceAlberoproc,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Ateco ateco = atecoService.findById(codiceAteco);
	PkId id = new PkId(codiceAlberoproc);
	Alberoproc alberoproc = alberoprocService.findById(id);
	AlberoprocAteco alberoprocAteco = new AlberoprocAteco();
	AlberoprocAtecoId alberoprocAtecoId = new AlberoprocAtecoId(codiceAlberoproc, codiceAteco);
	alberoprocAtecoId.setFkIdateco(ateco.getId());
	alberoprocAtecoId.setFkScid(alberoproc.getId().getCodice());
	alberoprocAteco.setId(alberoprocAtecoId);
	alberoprocAteco.setAlberoproc(alberoproc);
	alberoprocAteco.setAteco(ateco);
	try {
	    alberoprocatecoService.insert(alberoprocAteco);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    String err = this.renderErrors(e, true);
	    response.getWriter().write(err);
	}
    }

    @RequestMapping
    public void ajaxEliminaAteco(@RequestParam("alberoproc.id.codice") Integer codiceAlberoproc, @RequestParam("codiceateco") Integer codiceAteco,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	AlberoprocAtecoId alberoprocAtecoId = new AlberoprocAtecoId(codiceAlberoproc, codiceAteco);
	AlberoprocAteco alberoprocAteco = alberoprocatecoService.findById(alberoprocAtecoId);
	try {
	    alberoprocatecoService.delete(alberoprocAteco);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento: " + e.getMessage(), null));
	}
    }

    @Override
    protected void fixMergeEntityProperty(AlberoprocAteco entity) {

    }

    @Override
    protected void fixRenderEntityProperty(AlberoprocAteco entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
