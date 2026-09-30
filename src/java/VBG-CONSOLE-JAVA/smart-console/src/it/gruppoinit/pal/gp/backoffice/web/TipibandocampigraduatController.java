package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.annotations.security.SecuredComponent;
import it.gruppoinit.annotations.security.SecuredMethod;
import it.gruppoinit.annotations.security.SecuredMethod.ReadWriteMethod;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibando;
import it.gruppoinit.pal.gp.core.domain.Tipibandocampigraduat;
import it.gruppoinit.pal.gp.core.domain.Tipigraduatoriet;
import it.gruppoinit.pal.gp.core.service.TipibandoService;
import it.gruppoinit.pal.gp.core.service.TipibandocampigraduatService;
import it.gruppoinit.pal.gp.core.service.TipigraduatorietService;

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

//DAELIMINARE @Controller
@SessionAttributes("tipibandocampigraduat")
@SecuredComponent(key = "annotations.tipibandocampigraduat.component", parentClass = TipigraduatorietController.class, rootElement = false)
public class TipibandocampigraduatController extends BaseController<Tipibandocampigraduat> {

    @Autowired
    private TipibandocampigraduatService tipibandocampigraduatService;
    @Autowired
    private TipibandoService tipibandoService;
    @Autowired
    private TipigraduatorietService tipigraduatorietService;

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.list", order = 1)
    public ModelMap list(@RequestParam("tipigraduatoriet.id.codice") Integer codiceGraduatoriet, HttpServletRequest request,
	    HttpServletResponse response) {

	// genero un filtro per visualizzare tipibandocampigraduat
	// solo del tipibandi a cui è collegato a partire dal codice
	// tipibando sulla request
	PkId tipigraduatorietPk = new PkId(codiceGraduatoriet);
	Tipigraduatoriet tipigraduatoriet = tipigraduatorietService.findById(tipigraduatorietPk);
	Tipibandocampigraduat filter = new Tipibandocampigraduat();
	filter.setTipigraduatoriet(tipigraduatoriet);
	List<Tipibandocampigraduat> tipibandocampigraduatList = tipibandocampigraduatService.findByTipigraduatoriet(filter);
	ModelMap model = new ModelMap(tipibandocampigraduatList);
	boolean export = createJMesaExport(request, response, tipibandocampigraduatList);
	if (export)
	    return null;
	model.addAttribute("tipigraduatoriet", tipigraduatoriet);
	model.addAttribute("tipibandocampigraduatList", tipibandocampigraduatList);
	PkId tipibandoPk = new PkId();
	tipibandoPk.setCodice(tipigraduatoriet.getTipibando().getId().getCodice());
	Tipibando tipibando = tipibandoService.findById(tipibandoPk);
	model.addAttribute("tipibando", tipibando);
	return model;
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.delete", methodType = ReadWriteMethod.WRITE, order = 6)
    public String delete(@ModelAttribute("tipibandocampigraduat") Tipibandocampigraduat tipibandocampigraduat, BindingResult result,
	    SessionStatus status) {

	Tipibandocampigraduat objToDelete = tipibandocampigraduatService.findById(tipibandocampigraduat.getId());
	try {
	    tipibandocampigraduatService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipibandocampigraduat);
	    return "tipibandocampigraduat/form";
	}
	status.setComplete();
	return "redirect:list.htm?tipigraduatoriet.id.codice=" + tipibandocampigraduat.getTipigraduatoriet().getId().getCodice();
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.insert", methodType = ReadWriteMethod.WRITE, order = 3)
    public String insert(@ModelAttribute("tipibandocampigraduat") Tipibandocampigraduat tipibandocampigraduat, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(tipibandocampigraduat);
	try {
	    tipibandocampigraduatService.insert(tipibandocampigraduat);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipibandocampigraduat, e);
	    fixRenderEntityProperty(tipibandocampigraduat);
	    return "tipibandocampigraduat/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipibandocampigraduat.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.update", methodType = ReadWriteMethod.WRITE, order = 5)
    public String update(@ModelAttribute("tipibandocampigraduat") Tipibandocampigraduat tipibandocampigraduat, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(tipibandocampigraduat);
	try {
	    tipibandocampigraduatService.update(tipibandocampigraduat);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipibandocampigraduat, e);
	    fixRenderEntityProperty(tipibandocampigraduat);
	    return "tipibandocampigraduat/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipibandocampigraduat.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.create", methodType = ReadWriteMethod.WRITE, order = 2)
    public String create(@RequestParam("tipigraduatoriet.id.codice") Integer codiceGraduatoriet, Model model) {

	// creo un nuovo tipibandocampigraduat a partire dal codice tipibando
	// ricavato dalla request
	PkId tipigraduatorietPk = new PkId(codiceGraduatoriet);
	Tipigraduatoriet tipigraduatoriet = tipigraduatorietService.findById(tipigraduatorietPk);
	Tipibandocampigraduat filter = new Tipibandocampigraduat();
	filter.setTipigraduatoriet(tipigraduatoriet);
	List<Tipibandocampigraduat> list = tipibandocampigraduatService.findByTipigraduatoriet(filter);
	Tipibandocampigraduat tipibandocampigraduat = newOrder(list, filter);
	if (tipibandocampigraduat != null) {
	    tipibandocampigraduat.setTipigraduatoriet(tipigraduatoriet);
	    fixRenderEntityProperty(tipibandocampigraduat);
	}
	model.addAttribute("tipibandocampigraduat", tipibandocampigraduat);
	setPageAttributes(model);
	return "tipibandocampigraduat/form";
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.view", methodType = ReadWriteMethod.READ, order = 3)
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipibandocampigraduat tipibandocampigraduat = tipibandocampigraduatService.findById(id);
	fixRenderEntityProperty(tipibandocampigraduat);
	model.addAttribute("tipibandocampigraduat", tipibandocampigraduat);
	setPageAttributes(model);
	return "tipibandocampigraduat/form";
    }

    @Override
    protected void fixMergeEntityProperty(Tipibandocampigraduat entity) {

	if (entity.getTipigraduatoriet() != null && entity.getTipigraduatoriet().getId() != null
		&& entity.getTipigraduatoriet().getId().getCodice() == null) {
	    entity.setTipigraduatoriet(null);
	}
	if (entity.getDyn2Campi() != null && entity.getDyn2Campi().getId() != null && entity.getDyn2Campi().getId().getCodice() == null) {
	    entity.setDyn2Campi(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Tipibandocampigraduat entity) {

	if (entity.getTipigraduatoriet() == null) {
	    entity.setTipigraduatoriet(new Tipigraduatoriet());
	}
	if (entity.getDyn2Campi() == null) {
	    entity.setDyn2Campi(new Dyn2Campi());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    /**
     * 
     * Metodo per la generazione del primo ordine disponibile.
     * 
     * @author francescop
     * 
     * @param list
     *            Lista di Tipibandocampigraduat. La lista è ordinata.
     * @param tipibandocampigraduat
     *            Setto il nuovo ordine disponibile all'oggetto tipibandocampigraduat
     * 
     * @return Tipibandocampigraduat
     * 
     */
    private Tipibandocampigraduat newOrder(List<Tipibandocampigraduat> list, Tipibandocampigraduat tipibandocampigraduat) {

	Integer order = 0;
	Integer orderLast = 0;
	Integer sizeList = list.size();
	if (list.size() < 9) {
	    if (sizeList != 0) {
		orderLast = list.get(sizeList - 1).getOrdine();
	    } else {
		orderLast = 0;
	    }
	    if (orderLast != 9) {
		order = orderLast;
		order += 1;
	    } else {
		order = 9;
	    }
	} else {
	    tipibandocampigraduat.setOrdine(null);
	    return tipibandocampigraduat;
	}
	tipibandocampigraduat.setOrdine(order);
	return tipibandocampigraduat;
    }
}
