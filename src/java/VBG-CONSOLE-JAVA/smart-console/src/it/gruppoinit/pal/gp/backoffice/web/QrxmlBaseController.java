package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.InventarioprocQrt;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.QrxmlBase;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.QrxmlBaseService;

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

@Controller
@SessionAttributes("qrxmlBase")
public class QrxmlBaseController extends BaseController<QrxmlBase> {

    @Autowired
    private QrxmlBaseService qrxmlBaseService;
    @Autowired
    private OggettiService oggettiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	checkFunzionalitaConsolleRegionale(true);
	List<QrxmlBase> list = qrxmlBaseService.findAll(null, null);
	ModelMap model = new ModelMap(list);
	boolean export = createJMesaExport(request, response, list);
	if (export)
	    return null;
	model.addAttribute("list", list);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	QrxmlBase qrxmlBase = new QrxmlBase();
	fixRenderEntityProperty(qrxmlBase);
	model.addAttribute("qrxmlBase", qrxmlBase);
	setPageAttributes(model);
	return "qrxmlbase/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("qrxmlBase") QrxmlBase qrxmlBase, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(qrxmlBase);
	try {
	    qrxmlBaseService.insert(qrxmlBase);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, qrxmlBase, e);
	    fixRenderEntityProperty(qrxmlBase);
	    return "qrxmlbase/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + qrxmlBase.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	checkFunzionalitaConsolleRegionale(true);
	QrxmlBase qrxmlBase = qrxmlBaseService.findById(new PkId(codice));
	fixRenderEntityProperty(qrxmlBase);
	model.addAttribute("qrxmlBase", qrxmlBase);
	setPageAttributes(model);
	return "qrxmlbase/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("qrxmlBase") QrxmlBase qrxmlBase, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	checkFunzionalitaConsolleRegionale(true);
	fixMergeEntityProperty(qrxmlBase);
	try {
	    qrxmlBaseService.update(qrxmlBase);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, qrxmlBase, e);
	    fixRenderEntityProperty(qrxmlBase);
	    return "qrxmlbase/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + qrxmlBase.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("qrxmlBase") QrxmlBase qrxmlBase, BindingResult result, SessionStatus status) {

	checkFunzionalitaConsolleRegionale(true);
	QrxmlBase objToDelete = qrxmlBaseService.findById(qrxmlBase.getId());
	try {
	    qrxmlBaseService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(qrxmlBase);
	    return "qrxmlbase/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(QrxmlBase entity) {

    }

    @Override
    protected void fixRenderEntityProperty(QrxmlBase entity) {

	if (entity.getOggetti() == null) {
	    entity.setOggetti(new Oggetti());
	}
    }
}
