/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Ricerche;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;

/**
 * @author francescop
 * 
 */
@Controller
public class JSONMailController extends BaseController<Ricerche> {

    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private IstanzeService istanzeService;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping
    public ModelAndView recuperaOggettoCorpo(@RequestParam("chiave_ricerca") String chiave_ricerca,
	    @RequestParam("codiceistanza") Integer codiceistanza, @RequestParam("codicemovimento") Integer codicemovimento,
	    HttpServletResponse response) throws IOException {

	Istanze istanza = null;
	Movimenti movimento = null;
	if (codiceistanza != null) {
	    istanza = istanzeService.findById(new PkId(codiceistanza));
	}
	if (codicemovimento != null) {
	    movimento = movimentiService.findById(new PkId(codicemovimento));
	}
	Integer id = Integer.parseInt(chiave_ricerca);
	Mailtipo mailtipo = mailtipoService.findById(new PkId(id));
	Map model = new HashMap();
	Mailtipo mailtipoReplace = mailtipoService.replaceOggettoCorpo(mailtipo, istanza, movimento);
	model.put("mailtipo", mailtipoReplace);
	return new ModelAndView("jsonView", model);
    }

    @Override
    protected void fixMergeEntityProperty(Ricerche entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Ricerche entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
