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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RiCariche;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Tipisoggettopeople;
import it.gruppoinit.pal.gp.core.domain.TipisoggettopeopleId;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipisoggettoService;
import it.gruppoinit.pal.gp.core.service.TipisoggettopeopleService;

/**
 * 
 * @author Riccardo Bocci
 */
@Controller
@SessionAttributes("tipisoggetto")
public class TipisoggettoController extends BaseController<Tipisoggetto> {

    @Autowired
    private TipisoggettoService tipisoggettoService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private TipisoggettopeopleService tipisoggettopeopleService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipisoggetto> tipisoggettoList = tipisoggettoService.findAll(null, null);
	ModelMap model = new ModelMap(tipisoggettoList);
	boolean export = createJMesaExport(request, response, tipisoggettoList);
	if (export) {
	    return null;
	}
	model.addAttribute("tipisoggettoList", tipisoggettoList);
	model.addAttribute("mapping_soggetti", isMappingEnabled());
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Tipisoggetto tipisoggetto = new Tipisoggetto();
	tipisoggetto.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(tipisoggetto);
	model.addAttribute("tipisoggetto", tipisoggetto);
	setPageAttributes(model);
	return "tipisoggetto/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipisoggetto") Tipisoggetto tipisoggetto, BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "tipisoggetto/form";
	}
	fixMergeEntityProperty(tipisoggetto);
	tipisoggetto.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    tipisoggettoService.insert(tipisoggetto);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipisoggetto, e);
	    fixRenderEntityProperty(tipisoggetto);
	    return "tipisoggetto/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipisoggetto.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipisoggetto tipisoggetto = tipisoggettoService.findById(id);
	fixRenderEntityProperty(tipisoggetto);
	model.addAttribute("tipisoggetto", tipisoggetto);
	setPageAttributes(model);
	return "tipisoggetto/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipisoggetto") Tipisoggetto tipisoggetto, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	if (result.hasErrors()) {
	    return "tipisoggetto/form";
	}
	fixMergeEntityProperty(tipisoggetto);
	try {
	    tipisoggettoService.update(tipisoggetto);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipisoggetto, e);
	    fixRenderEntityProperty(tipisoggetto);
	    return "tipisoggetto/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipisoggetto.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("tipisoggetto") Tipisoggetto tipisoggetto, BindingResult result, SessionStatus status) {

	Tipisoggetto objToDelete = tipisoggettoService.findById(tipisoggetto.getId());
	try {
	    tipisoggettoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipisoggetto);
	    return "tipisoggetto/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String updateMapping(Model model, @ModelAttribute("tipisoggetto") Tipisoggetto tipisoggetto, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	try {
	    String op = request.getParameter("op");
	    String tiporapprpeople = request.getParameter("tipo");
	    if (StringUtils.isNotBlank(op) && StringUtils.isNotBlank(tiporapprpeople)) {
		Tipisoggettopeople tsp = new Tipisoggettopeople();
		TipisoggettopeopleId tspId = new TipisoggettopeopleId();
		tspId.setIdcomune(ORMHelper.getIdcomune());
		tspId.setSoftware(ORMHelper.getSoftware());
		tspId.setTiporapprpeople(tiporapprpeople);
		if (op.equals("add")) {
		    Tipisoggettopeople _tsp = tipisoggettopeopleService.findById(tspId);
		    if (_tsp != null) {
			throw new RuntimeException(this.getMessageFromBundle("tipisoggetto.alert.mapping_presente",
				new Object[] { _tsp.getTipisoggetto().getTiposoggetto() }));
		    }
		    Tipisoggetto _tipisoggetto = tipisoggettoService.findById(tipisoggetto.getId());
		    tsp.setId(tspId);
		    tsp.setTipisoggetto(_tipisoggetto);
		    tipisoggettopeopleService.insert(tsp);
		} else {
		    tsp = tipisoggettopeopleService.findById(tspId);
		    tipisoggettopeopleService.delete(tsp);
		}
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipisoggetto, e);
	    fixRenderEntityProperty(tipisoggetto);
	    model.addAttribute("mapping_soggetti", true);
	    return "tipisoggetto/form";
	}
	return "redirect:view.htm?codice=" + tipisoggetto.getId().getCodice() + "&status_msg=02";
    }

    @Override
    protected void fixMergeEntityProperty(Tipisoggetto entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Tipisoggetto entity) {

	if (entity != null) {
	    if (entity.getRiCariche() == null) {
		entity.setRiCariche(new RiCariche());
	    }
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	model.addAttribute("mapping_soggetti", isMappingEnabled());
    }

    private boolean isMappingEnabled() {

	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_AIDA)
		|| verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_PEOPLE)) {
	    return true;
	}
	return false;
    }

    @RequestMapping
    public void ajaxDescrizioneEstesa(@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {

	Tipisoggetto o = tipisoggettoService.findById(new PkId(id));
	String message = StringUtils.defaultString(o.getDescrizioneEstesa()).trim();
	response.getWriter().write(message);
    }
}
