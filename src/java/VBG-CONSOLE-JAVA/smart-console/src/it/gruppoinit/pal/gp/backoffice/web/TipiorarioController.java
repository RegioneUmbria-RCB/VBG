package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Giornisettimana;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipiapertura;
import it.gruppoinit.pal.gp.core.domain.Tipiorario;
import it.gruppoinit.pal.gp.core.domain.Tipiorariodettaglio;
import it.gruppoinit.pal.gp.core.domain.web.TipiorarioCommand;
import it.gruppoinit.pal.gp.core.service.GiornisectimanaService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiaperturaService;
import it.gruppoinit.pal.gp.core.service.TipiorarioService;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
 * @author lucap
 */
@Controller
@SessionAttributes("tipiorario")
public class TipiorarioController extends BaseController<Tipiorario> {

    @Autowired
    private TipiorarioService tipiorarioService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private GiornisectimanaService giornisectimanaService;
    @Autowired
    TipiaperturaService tipiaperturaService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipiorario> tipiorarioList = tipiorarioService.findAll(null, null);
	ModelMap model = new ModelMap(tipiorarioList);
	boolean export = createJMesaExport(request, response, tipiorarioList);
	if (export) {
	    return null;
	}
	model.addAttribute("tipiorarioList", tipiorarioList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	TipiorarioCommand tipiorario = new TipiorarioCommand();
	// Lista dei Tipiapertura
	List<Tipiapertura> tipiaperturaList = tipiaperturaService.findAll(null, null);
	tipiorario.setTipiaperturaList(tipiaperturaList);
	List<Integer> codicetipiaperturaList = new ArrayList<Integer>(7);
	// Set della lista di Tipiorariodettaglio in Tipiorario
	Set<Tipiorariodettaglio> tipiorariodettaglios = new LinkedHashSet<Tipiorariodettaglio>(0);
	Tipiorario entity = new Tipiorario();
	for (int i = 0; i < 7; i++) {
	    Tipiorariodettaglio tipiorariodettaglio = new Tipiorariodettaglio();
	    Giornisettimana giornisettimana = giornisectimanaService.findById(i + 1);
	    tipiorariodettaglio.setGiornisectimana(giornisettimana);
	    tipiorariodettaglio.setTipiorario(entity);
	    tipiorariodettaglios.add(tipiorariodettaglio);
	}
	tipiorario.setCodicetipiaperturaList(codicetipiaperturaList);
	entity.setTipiorariodettaglios(tipiorariodettaglios);
	entity.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(entity);
	tipiorario.setEntity(entity);
	tipiorario.setDisplayMode(TipiorarioCommand.NEW);
	model.addAttribute("tipiorario", tipiorario);
	setPageAttributes(model);
	return "tipiorario/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipiorario") TipiorarioCommand tipiorario, BindingResult result, SessionStatus status) {

	Tipiorario entity = tipiorario.getEntity();
	List<Integer> codicetipiaperturaList = tipiorario.getCodicetipiaperturaList();
	Set<Tipiorariodettaglio> tipiorariodettaglios = tipiorario.getEntity().getTipiorariodettaglios();
	int i = 0;
	for (Tipiorariodettaglio tipiorariodettaglio : tipiorariodettaglios) {
	    Tipiapertura tipiapertura = tipiaperturaService.findById(new PkId(codicetipiaperturaList.get(i)));
	    tipiorariodettaglio.setTipiapertura(tipiapertura);
	    i++;
	}
	entity.setTipiorariodettaglios(tipiorariodettaglios);
	tipiorario.setDisplayMode(TipiorarioCommand.VIEW);
	fixMergeEntityProperty(entity);
	entity.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    tipiorarioService.insert(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiorario.getEntity(), true, e);
	    fixRenderEntityProperty(tipiorario.getEntity());
	    return "tipiorario/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	TipiorarioCommand tipiorario = new TipiorarioCommand();
	// Lista dei Tipiapertura
	List<Tipiapertura> tipiaperturaList = tipiaperturaService.findAll(null, null);
	tipiorario.setTipiaperturaList(tipiaperturaList);
	List<Integer> codicetipiaperturaList = new ArrayList<Integer>(7);
	PkId id = new PkId(codice);
	Tipiorario entity = tipiorarioService.findById(id);
	Set<Tipiorariodettaglio> tipiorariodettaglios = entity.getTipiorariodettaglios();
	for (Tipiorariodettaglio tipiorariodettaglio : tipiorariodettaglios) {
	    if (tipiorariodettaglio.getTipiapertura() != null) {
		codicetipiaperturaList.add(tipiorariodettaglio.getTipiapertura().getId().getCodice());
	    } else {
		codicetipiaperturaList.add(null);
	    }
	}
	tipiorario.setCodicetipiaperturaList(codicetipiaperturaList);
	fixRenderEntityProperty(entity);
	tipiorario.setEntity(entity);
	tipiorario.setDisplayMode(TipiorarioCommand.VIEW);
	model.addAttribute("tipiorario", tipiorario);
	setPageAttributes(model);
	return "tipiorario/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipiorario") TipiorarioCommand tipiorario, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Tipiorario entity = tipiorario.getEntity();
	List<Integer> codicetipiaperturaList = tipiorario.getCodicetipiaperturaList();
	Set<Tipiorariodettaglio> tipiorariodettaglios = tipiorario.getEntity().getTipiorariodettaglios();
	int i = 0;
	for (Tipiorariodettaglio tipiorariodettaglio : tipiorariodettaglios) {
	    Tipiapertura tipiapertura = tipiaperturaService.findById(new PkId(codicetipiaperturaList.get(i)));
	    tipiorariodettaglio.setTipiapertura(tipiapertura);
	    i++;
	}
	entity.setTipiorariodettaglios(tipiorariodettaglios);
	tipiorario.setDisplayMode(TipiorarioCommand.VIEW);
	fixMergeEntityProperty(tipiorario.getEntity());
	try {
	    tipiorarioService.update(tipiorario.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiorario.getEntity(), true, e);
	    fixRenderEntityProperty(tipiorario.getEntity());
	    return "tipiorario/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("tipiorario") TipiorarioCommand tipiorario, BindingResult result, SessionStatus status) {

	Tipiorario objToDelete = tipiorarioService.findById(tipiorario.getEntity().getId());
	try {
	    tipiorarioService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, e);
	    fixRenderEntityProperty(tipiorario.getEntity());
	    return "tipiorario/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Tipiorario entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Tipiorario entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	// fixRender per la proprietà di secondo livello contenuta nel Set
	Set<Tipiorariodettaglio> list = entity.getTipiorariodettaglios();
	for (Tipiorariodettaglio tipiorariodettaglio : list) {
	    tipiorariodettaglio.setTipiorario(entity);
	    if (tipiorariodettaglio.getTipiapertura() == null) {
		tipiorariodettaglio.setTipiapertura(new Tipiapertura());
	    }
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
