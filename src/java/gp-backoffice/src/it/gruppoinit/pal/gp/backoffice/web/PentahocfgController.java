package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Pentahocfg;
import it.gruppoinit.pal.gp.core.domain.web.PentahocfgCommand;
import it.gruppoinit.pal.gp.core.service.PentahocfgService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("pentahocfg")
public class PentahocfgController extends BaseController<Pentahocfg> {

    @Autowired
    private PentahocfgService pentahocfgService;

    //    @RequestMapping
    //    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    //	List<Pentahocfg> pentahocfgList = pentahocfgService.findAll(null, null);
    //	ModelMap model = new ModelMap(pentahocfgList);
    //	boolean export = createJMesaExport(request, response, pentahocfgList);
    //	if (export) {
    //	    return null;
    //	}
    //	model.addAttribute("pentahocfgList", pentahocfgList);
    //	return model;
    //    }
    //    @RequestMapping
    //    public String create(Model model) {
    //
    //	PentahocfgCommand pentahocfg = new PentahocfgCommand();
    //	fixRenderEntityProperty(pentahocfg.getEntiry());
    //	model.addAttribute("pentahocfg", pentahocfg);
    //	setPageAttributes(model);
    //	return "pentahocfg/form";
    //    }
    @RequestMapping
    public String createOrView(Model model) {

	PentahocfgCommand pentahocfg = new PentahocfgCommand();
	Pentahocfg entity = pentahocfgService.findById(ORMHelper.getIdcomune());
	pentahocfg.setDisplayMode(PentahocfgCommand.VIEW);
	if (entity == null) {
	    entity = new Pentahocfg();
	    pentahocfg.setDisplayMode(PentahocfgCommand.NEW);
	}
	pentahocfg.setEntity(entity);
	fixRenderEntityProperty(pentahocfg.getEntity());
	model.addAttribute("pentahocfg", pentahocfg);
	setPageAttributes(model);
	return "pentahocfg/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("pentahocfg") PentahocfgCommand pentahocfg, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(pentahocfg.getEntity());
	try {
	    pentahocfgService.insert(pentahocfg.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, pentahocfg.getEntity(), true, e);
	    fixRenderEntityProperty(pentahocfg.getEntity());
	    return "pentahocfg/form";
	}
	status.setComplete();
	return "redirect:createOrView.htm?status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") String codice, Model model, HttpServletRequest request) {

	Pentahocfg pentahocfg = pentahocfgService.findById(codice);
	fixRenderEntityProperty(pentahocfg);
	model.addAttribute("pentahocfg", pentahocfg);
	setPageAttributes(model);
	return "pentahocfg/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("pentahocfg") PentahocfgCommand pentahocfg, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(pentahocfg.getEntity());
	try {
	    pentahocfgService.update(pentahocfg.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, pentahocfg.getEntity(), true, e);
	    fixRenderEntityProperty(pentahocfg.getEntity());
	    return "pentahocfg/form";
	}
	status.setComplete();
	return "redirect:createOrView.htm?status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("pentahocfg") Pentahocfg pentahocfg, BindingResult result, SessionStatus status) {

	Pentahocfg objToDelete = pentahocfgService.findById(pentahocfg.getIdcomune());
	try {
	    pentahocfgService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, pentahocfg, true, e);
	    fixRenderEntityProperty(pentahocfg);
	    return "pentahocfg/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Pentahocfg entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Pentahocfg entity) {

	if (entity.getMailtipo() == null) {
	    entity.setMailtipo(new Mailtipo());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
