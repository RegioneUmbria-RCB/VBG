package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzelavoriD;
import it.gruppoinit.pal.gp.core.domain.IstanzelavoriT;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Lavoritipi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipiunitamisura;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzelavoriDService;
import it.gruppoinit.pal.gp.core.service.IstanzelavoriTService;
import it.gruppoinit.pal.gp.core.service.LavoritipiService;
import it.gruppoinit.pal.gp.core.service.TipiunitamisuraService;

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes(value = { "istanzelavorit", "istanzelavorit" })
public class IstanzelavoriTController extends BaseController<IstanzelavoriT> {

    @Autowired
    private IstanzelavoriTService istanzelavoritService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private IstanzestradarioService istanzestradarioService;
    @Autowired
    private LavoritipiService lavoritipiService;
    @Autowired
    private IstanzelavoriDService istanzelavoridService;
    @Autowired
    private TipicausalioneriService tipicausalioneriService;
    @Autowired
    private TipiunitamisuraService tipiunitamisuraService;
    @Autowired
    private IstanzeoneriService istanzeoneriService;

    @RequestMapping
    public String list(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, false);
	List<IstanzelavoriT> istanzelavoriTs = istanzelavoritService.findByIstanza(istanza);
	model.addAttribute("istanza", istanza);
	model.addAttribute("istanzelavorit", new IstanzelavoriT());
	model.addAttribute("istanzelavoriTs", istanzelavoriTs);
	// §§§END§§§
	return "istanzelavorit/list";
    }

    @RequestMapping
    public String create(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model) {

	// §§§BEGIN§§§
	IstanzelavoriT istanzelavorit = new IstanzelavoriT();
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, true);
	istanzelavorit.setIstanze(istanza);
	fixRenderEntityProperty(istanzelavorit);
	model.addAttribute("istanzelavorit", istanzelavorit);
	model.addAttribute("istanza", istanza);
	setPageAttributes(model);
	// §§§END§§§
	return "istanzelavorit/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("istanzelavorit") IstanzelavoriT istanzelavorit, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	if (EntityUtils.getNestedProperty(istanzelavorit, "istanzestradario") != null) {
	    Istanzestradario istanzestradario = istanzestradarioService.findById(new PkId(istanzelavorit.getIstanzestradario().getId().getCodice()));
	    istanzelavorit.setIstanzestradario(istanzestradario);
	}
	if (EntityUtils.getNestedProperty(istanzelavorit, "lavoritipi") != null) {
	    Lavoritipi lavoritipi = lavoritipiService.findById(new PkId(istanzelavorit.getLavoritipi().getId().getCodice()));
	    istanzelavorit.setLavoritipi(lavoritipi);
	}
	fixMergeEntityProperty(istanzelavorit);
	if (istanzelavorit.getIstanze() != null) {
	    checkAccessoInformazioni(istanzelavorit.getIstanze(), true);
	}
	try {
	    istanzelavoritService.insert(istanzelavorit);
	} catch (Exception e) {
	    model.addAttribute("istanzelavorit", istanzelavorit);
	    model.addAttribute("istanza", istanzelavorit.getIstanze());
	    copyErrorsToBindingResult(result, istanzelavorit, e);
	    if (EntityUtils.getNestedProperty(istanzelavorit, "istanzestradario") != null) {
		Istanzestradario istanzestradario = istanzestradarioService
			.findById(new PkId(istanzelavorit.getIstanzestradario().getId().getCodice()));
		istanzelavorit.setIstanzestradario(istanzestradario);
	    }
	    fixRenderEntityProperty(istanzelavorit);
	    return "istanzelavorit/form";
	}
	return "redirect:../istanzelavorit/list.htm?codiceIstanza=" + istanzelavorit.getIstanze().getId().getCodice();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String ajaxShowViewIstanzelavoriT(@RequestParam("codice") Integer codice, Model model, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	IstanzelavoriT istanzelavorit = istanzelavoritService.findById(new PkId(codice));
	model.addAttribute("istanzelavorit", istanzelavorit);
	// §§§END§§§
	return "ajax/dettaglioIstanzelavoriT";
    }

    @RequestMapping
    public void ajaxSaveIstanzelavorit(Model model, @ModelAttribute("istanzelavorit") IstanzelavoriT istanzelavoriT, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	try {
	    if (istanzelavoriT.getIstanze() != null) {
		checkAccessoInformazioni(istanzelavoriT.getIstanze(), true);
	    }
	    istanzelavoritService.update(istanzelavoriT);
	    response.getWriter().write(istanzelavoriT.getLavoro());
	    response.getWriter().write("#");
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.errore", null) + ": " + e.getMessage());
	}
	// §§§END§§§
    }

    @RequestMapping
    public void ajaxChangeQuantiaOrCostoUnitario(@RequestParam("codice") Integer codice, @RequestParam("costounitario") String costo,
	    @RequestParam("quantita") String quantita, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	IstanzelavoriD istanzelavoriD = istanzelavoridService.findById(new PkId(codice));
	istanzelavoriD.setQuantita(new BigDecimal(quantita));
	istanzelavoriD.setCostoUnitarioUm(new BigDecimal(costo));
	try {
	    if (istanzelavoriD.getIstanzelavoriT().getIstanze() != null) {
		checkAccessoInformazioni(istanzelavoriD.getIstanzelavoriT().getIstanze(), true);
	    }
	    istanzelavoridService.update(istanzelavoriD);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.getWriter().write(getMessageFromBundle("label.errore: " + e.getMessage(), null));
	}
	// §§§END§§§
    }

    @RequestMapping
    public String createIstanzelavoriD(@RequestParam("codiceIstanzaLavoriT") Integer codiceIstanzaLavoriT, Model model) {

	// §§§BEGIN§§§
	IstanzelavoriT istanzelavorit = istanzelavoritService.findById(new PkId(codiceIstanzaLavoriT));
	IstanzelavoriD istanzelavoriD = new IstanzelavoriD();
	istanzelavoriD.setIstanzelavoriT(istanzelavorit);
	fixRenderIstanzelavoriDProperty(istanzelavoriD);
	model.addAttribute("istanzelavorid", istanzelavoriD);
	model.addAttribute("istanzelavorit", istanzelavorit);
	setPageAttributes(model);
	// §§§END§§§
	return "istanzelavorit/formIstanzelavoriD";
    }

    @RequestMapping
    public String insertIstanzelavoriD(@RequestParam("codiceIstanzelavoriT") Integer codiceIstanzaLavoriT,
	    @ModelAttribute("istanzelavorid") IstanzelavoriD istanzelavorid, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	IstanzelavoriT istanzelavorit = istanzelavoritService.findById(new PkId(codiceIstanzaLavoriT));
	if (istanzelavorit.getIstanze() != null) {
	    checkAccessoInformazioni(istanzelavorit.getIstanze(), true);
	}
	if (EntityUtils.getNestedProperty(istanzelavorid, "tipicausalioneri") != null) {
	    Tipicausalioneri tipicausalioneri = tipicausalioneriService.findById(new PkId(istanzelavorid.getTipicausalioneri().getId().getCodice()));
	    istanzelavorid.setTipicausalioneri(tipicausalioneri);
	}
	if (EntityUtils.getNestedProperty(istanzelavorid, "tipiunitamisura") != null) {
	    Tipiunitamisura tipiunitamisura = tipiunitamisuraService.findById(new PkId(istanzelavorid.getTipiunitamisura().getId().getCodice()));
	    istanzelavorid.setTipiunitamisura(tipiunitamisura);
	}
	istanzelavorid.setIstanzelavoriT(istanzelavorit);
	try {
	    istanzelavoridService.insert(istanzelavorid);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzelavorid, e);
	    fixRenderIstanzelavoriDProperty(istanzelavorid);
	    return "istanzelavorit/formIstanzelavoriD";
	}
	return "redirect:../istanzelavorit/list.htm?codiceIstanza=" + istanzelavorid.getIstanzelavoriT().getIstanze().getId().getCodice();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String deleteIstanzelavoriD(@RequestParam("codiceIstanzeLavorid") Integer codiceIstanzeLavorid, HttpServletRequest request) {

	// §§§BEGIN§§§
	IstanzelavoriD istanzelavoriD = istanzelavoridService.findById(new PkId(codiceIstanzeLavorid));
	try {
	    if (istanzelavoriD.getIstanzelavoriT().getIstanze() != null) {
		checkAccessoInformazioni(istanzelavoriD.getIstanzelavoriT().getIstanze(), true);
	    }
	    istanzelavoridService.delete(istanzelavoriD);
	} catch (Exception e) {
	}
	return "redirect:../istanzelavorit/list.htm?codiceIstanza=" + istanzelavoriD.getIstanzelavoriT().getIstanze().getId().getCodice();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@RequestParam("codiceIstanzeLavorit") Integer codiceIstanzeLavorit, HttpServletRequest request) {

	// §§§BEGIN§§§
	IstanzelavoriT istanzelavoriT = istanzelavoritService.findById(new PkId(codiceIstanzeLavorit));
	if (istanzelavoriT.getIstanze() != null) {
	    checkAccessoInformazioni(istanzelavoriT.getIstanze(), true);
	}
	try {
	    istanzelavoritService.delete(istanzelavoriT);
	} catch (Exception e) {
	}
	return "redirect:../istanzelavorit/list.htm?codiceIstanza=" + istanzelavoriT.getIstanze().getId().getCodice();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String copiaOneri(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model,
	    @ModelAttribute("istanzelavorit") IstanzelavoriT istanzelavorit, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, true);
	try {
	    istanzeoneriService.inserisciOneriDaIstanzelavoriTs(istanza, getCurrentlyAuthenticatedUserDetails());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzelavorit, e);
	    List<IstanzelavoriT> istanzelavoriTs = istanzelavoritService.findByIstanza(istanza);
	    model.addAttribute("istanza", istanza);
	    model.addAttribute("istanzelavoriTs", istanzelavoriTs);
	    return "istanzelavorit/list";
	}
	return "redirect:../istanzelavorit/list.htm?codiceIstanza=" + istanza.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(IstanzelavoriT entity) {

    }

    @Override
    protected void fixRenderEntityProperty(IstanzelavoriT entity) {

	if (entity.getIstanze() == null) {
	    entity.setIstanze(new Istanze());
	}
	if (entity.getIstanzestradario() == null) {
	    entity.setIstanzestradario(new Istanzestradario());
	}
	if (entity.getLavoritipi() == null) {
	    entity.setLavoritipi(new Lavoritipi());
	}
    }

    private void fixRenderIstanzelavoriDProperty(IstanzelavoriD entity) {

	if (entity.getTipicausalioneri() == null) {
	    entity.setTipicausalioneri(new Tipicausalioneri());
	}
	if (entity.getTipiunitamisura() == null) {
	    entity.setTipiunitamisura(new Tipiunitamisura());
	}
	if (entity.getIstanzelavoriT() == null) {
	    entity.setIstanzelavoriT(new IstanzelavoriT());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
