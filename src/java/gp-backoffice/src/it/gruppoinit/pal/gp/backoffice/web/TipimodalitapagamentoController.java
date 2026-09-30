/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;

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
 * @author francescop
 * 
 */
@Controller
@SessionAttributes("tipimodalitapagamento")
public class TipimodalitapagamentoController extends BaseController<Tipimodalitapagamento> {

    @Autowired
    private TipimodalitapagamentoService tipimodalitapagamentoService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipimodalitapagamento> tipimodalitapagamentoList = tipimodalitapagamentoService.findAll(null, null);
	ModelMap model = new ModelMap(tipimodalitapagamentoList);
	boolean export = createJMesaExport(request, response, tipimodalitapagamentoList);
	if (export)
	    return null;
	model.addAttribute("tipimodalitapagamentoList", tipimodalitapagamentoList);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("tipimodalitapagamento") Tipimodalitapagamento tipimodalitapagamento, BindingResult result,
	    SessionStatus status) {

	Tipimodalitapagamento objToDelete = tipimodalitapagamentoService.findById(tipimodalitapagamento.getId());
	try {
	    tipimodalitapagamentoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipimodalitapagamento);
	    return "tipimodalitapagamento/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("tipimodalitapagamento") Tipimodalitapagamento tipimodalitapagamento, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(tipimodalitapagamento);
	try {
	    tipimodalitapagamentoService.insert(tipimodalitapagamento);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimodalitapagamento, e);
	    fixRenderEntityProperty(tipimodalitapagamento);
	    return "tipimodalitapagamento/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipimodalitapagamento.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("tipimodalitapagamento") Tipimodalitapagamento tipimodalitapagamento, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(tipimodalitapagamento);
	try {
	    tipimodalitapagamentoService.update(tipimodalitapagamento);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimodalitapagamento, e);
	    fixRenderEntityProperty(tipimodalitapagamento);
	    return "oggettiinfo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipimodalitapagamento.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	Tipimodalitapagamento tipimodalitapagamento = new Tipimodalitapagamento();
	fixRenderEntityProperty(tipimodalitapagamento);
	model.addAttribute("tipimodalitapagamento", tipimodalitapagamento);
	setPageAttributes(model);
	return "tipimodalitapagamento/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipimodalitapagamento tipimodalitapagamento = tipimodalitapagamentoService.findById(id);
	fixRenderEntityProperty(tipimodalitapagamento);
	model.addAttribute("tipimodalitapagamento", tipimodalitapagamento);
	setPageAttributes(model);
	return "tipimodalitapagamento/form";
    }

    @Override
    protected void fixMergeEntityProperty(Tipimodalitapagamento entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Tipimodalitapagamento entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}