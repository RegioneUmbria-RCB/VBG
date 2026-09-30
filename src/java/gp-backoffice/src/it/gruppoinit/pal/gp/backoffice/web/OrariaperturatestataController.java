package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Giornisettimana;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Orariapertura;
import it.gruppoinit.pal.gp.core.domain.Orariaperturatestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiapertura;
import it.gruppoinit.pal.gp.core.domain.Tipiorario;
import it.gruppoinit.pal.gp.core.domain.web.OrariaperturatestataCommand;
import it.gruppoinit.pal.gp.core.domain.web.TipiorarioCommand;
import it.gruppoinit.pal.gp.core.service.GiornisectimanaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.OrariaperturatestataService;
import it.gruppoinit.pal.gp.core.service.TipiaperturaService;

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
 * @author Luca Proietti
 */
@Controller
@SessionAttributes("orariaperturatestata")
public class OrariaperturatestataController extends BaseController<Orariaperturatestata> {

    @Autowired
    private OrariaperturatestataService orariaperturatestataService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private GiornisectimanaService giornisectimanaService;
    @Autowired
    private TipiaperturaService tipiaperturaService;

    @RequestMapping
    public ModelMap list(@RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request, HttpServletResponse response) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, false);
	List<Orariaperturatestata> orariaperturatestataList = orariaperturatestataService.findByIstanza(istanza);
	ModelMap model = new ModelMap(orariaperturatestataList);
	boolean export = createJMesaExport(request, response, orariaperturatestataList);
	if (export) {
	    return null;
	}
	model.addAttribute("orariaperturatestataList", orariaperturatestataList);
	model.addAttribute("istanza", istanza);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model) {

	OrariaperturatestataCommand orariaperturatestata = new OrariaperturatestataCommand();
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, true);
	Orariaperturatestata entity = new Orariaperturatestata();
	entity.setIstanze(istanza);
	List<Integer> codicetipiaperturaList = new ArrayList<Integer>(7);
	Set<Orariapertura> orariaperturas = new LinkedHashSet<Orariapertura>(0);
	for (int i = 0; i < 7; i++) {
	    Orariapertura orariapertura = new Orariapertura();
	    Giornisettimana giornisettimana = giornisectimanaService.findById(i + 1);
	    orariapertura.setGiornisettimana(giornisettimana);
	    orariapertura.setOrariaperturatestata(entity);
	    orariaperturas.add(orariapertura);
	}
	entity.setOrariaperturas(orariaperturas);
	orariaperturatestata.setCodicetipiaperturaList(codicetipiaperturaList);
	fixRenderEntityProperty(entity);
	orariaperturatestata.setEntity(entity);
	List<Tipiapertura> tipiaperturaList = tipiaperturaService.findAll(null, null);
	orariaperturatestata.setTipiaperturaList(tipiaperturaList);
	orariaperturatestata.setDisplayMode(OrariaperturatestataCommand.NEW);
	model.addAttribute("orariaperturatestata", orariaperturatestata);
	setPageAttributes(model);
	return "orariaperturatestata/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("orariaperturatestata") OrariaperturatestataCommand orariaperturatestata, BindingResult result,
	    SessionStatus status) {

	Orariaperturatestata entity = orariaperturatestata.getEntity();
	checkAccessoInformazioni(entity.getIstanze(), true);
	List<Integer> codicetipiaperturaList = orariaperturatestata.getCodicetipiaperturaList();
	Set<Orariapertura> orariaperturas = orariaperturatestata.getEntity().getOrariaperturas();
	int i = 0;
	for (Orariapertura orariapertura : orariaperturas) {
	    Tipiapertura tipiapertura = tipiaperturaService.findById(new PkId(codicetipiaperturaList.get(i)));
	    orariapertura.setTipiapertura(tipiapertura);
	    i++;
	}
	entity.setOrariaperturas(orariaperturas);
	orariaperturatestata.setDisplayMode(TipiorarioCommand.VIEW);
	fixMergeEntityProperty(entity);
	try {
	    orariaperturatestataService.insert(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, orariaperturatestata.getEntity(), true, e);
	    fixRenderEntityProperty(orariaperturatestata.getEntity());
	    return "orariaperturatestata/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + orariaperturatestata.getEntity().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	OrariaperturatestataCommand orariaperturatestata = new OrariaperturatestataCommand();
	List<Tipiapertura> tipiaperturaList = tipiaperturaService.findAll(null, null);
	orariaperturatestata.setTipiaperturaList(tipiaperturaList);
	List<Integer> codicetipiaperturaList = new ArrayList<Integer>(7);
	Orariaperturatestata entity = orariaperturatestataService.findById(new PkId(codice));
	checkAccessoInformazioni(entity.getIstanze(), false);
	Set<Orariapertura> orariaperturas = entity.getOrariaperturas();
	for (Orariapertura orariapertura : orariaperturas) {
	    if (orariapertura.getTipiapertura() != null) {
		codicetipiaperturaList.add(orariapertura.getTipiapertura().getId().getCodice());
	    } else {
		codicetipiaperturaList.add(null);
	    }
	}
	orariaperturatestata.setCodicetipiaperturaList(codicetipiaperturaList);
	fixRenderEntityProperty(entity);
	orariaperturatestata.setEntity(entity);
	orariaperturatestata.setDisplayMode(OrariaperturatestataCommand.VIEW);
	model.addAttribute("orariaperturatestata", orariaperturatestata);
	setPageAttributes(model);
	return "orariaperturatestata/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("orariaperturatestata") OrariaperturatestataCommand orariaperturatestata, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Orariaperturatestata entity = orariaperturatestata.getEntity();
	checkAccessoInformazioni(entity.getIstanze(), true);
	List<Integer> codicetipiaperturaList = orariaperturatestata.getCodicetipiaperturaList();
	Set<Orariapertura> orariaperturas = orariaperturatestata.getEntity().getOrariaperturas();
	int i = 0;
	for (Orariapertura orariapertura : orariaperturas) {
	    Tipiapertura tipiapertura = tipiaperturaService.findById(new PkId(codicetipiaperturaList.get(i)));
	    orariapertura.setTipiapertura(tipiapertura);
	    i++;
	}
	entity.setOrariaperturas(orariaperturas);
	orariaperturatestata.setDisplayMode(TipiorarioCommand.VIEW);
	fixMergeEntityProperty(orariaperturatestata.getEntity());
	try {
	    orariaperturatestataService.update(orariaperturatestata.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, orariaperturatestata.getEntity(), true, e);
	    fixRenderEntityProperty(orariaperturatestata.getEntity());
	    return "orariaperturatestata/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + orariaperturatestata.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("orariaperturatestata") OrariaperturatestataCommand orariaperturatestata, BindingResult result,
	    SessionStatus status) {

	Istanze istanza = orariaperturatestata.getEntity().getIstanze();
	checkAccessoInformazioni(istanza, true);
	Orariaperturatestata objToDelete = orariaperturatestataService.findById(orariaperturatestata.getEntity().getId());
	try {
	    orariaperturatestataService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, orariaperturatestata.getEntity(), true, e);
	    fixRenderEntityProperty(orariaperturatestata.getEntity());
	    return "orariaperturatestata/form";
	}
	status.setComplete();
	return "redirect:list.htm?codiceIstanza=" + istanza.getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(Orariaperturatestata entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Orariaperturatestata entity) {

	if (entity.getTipiorario() == null) {
	    entity.setTipiorario(new Tipiorario());
	}
	if (entity.getIstanze() == null) {
	    entity.setIstanze(new Istanze());
	}
	// fixRender per la proprietà di secondo livello contenuta nel Set
	Set<Orariapertura> list = entity.getOrariaperturas();
	for (Orariapertura orariapertura : list) {
	    orariapertura.setOrariaperturatestata(entity);
	    if (orariapertura.getTipiapertura() == null) {
		orariapertura.setTipiapertura(new Tipiapertura());
	    }
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
