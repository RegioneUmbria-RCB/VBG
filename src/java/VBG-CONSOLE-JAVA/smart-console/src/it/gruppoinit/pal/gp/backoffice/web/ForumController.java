package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Forum;
import it.gruppoinit.pal.gp.core.domain.Messaggi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.ForumService;
import it.gruppoinit.pal.gp.core.service.MessaggiService;

import java.io.IOException;
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
 * 
 * @author Luca Proietti
 */
//DAELIMINARE @Controller
@SessionAttributes("forum")
public class ForumController extends BaseController<Forum> {

    @Autowired
    private ForumService forumService;
    @Autowired
    private MessaggiService messaggiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<Forum> forumList = forumService.findAll(null, null);
	ModelMap model = new ModelMap(forumList);
	boolean export = createJMesaExport(request, response, forumList);
	if (export) {
	    return null;
	}
	model.addAttribute("forumList", forumList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	Forum forum = new Forum();
	fixRenderEntityProperty(forum);
	model.addAttribute("forum", forum);
	setPageAttributes(model);
	return "forum/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@ModelAttribute("forum") Forum forum, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(forum);
	try {
	    forumService.insert(forum);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, forum, e);
	    fixRenderEntityProperty(forum);
	    return "forum/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + forum.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	Forum forum = forumService.findById(id);
	fixRenderEntityProperty(forum);
	model.addAttribute("forum", forum);
	setPageAttributes(model);
	return "forum/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(@ModelAttribute("forum") Forum forum, BindingResult result, SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(forum);
	try {
	    forumService.update(forum);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, forum, e);
	    fixRenderEntityProperty(forum);
	    return "forum/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + forum.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@ModelAttribute("forum") Forum forum, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	Forum objToDelete = forumService.findById(forum.getId());
	try {
	    forumService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(forum);
	    return "forum/form";
	}
	status.setComplete();
	return "redirect:list.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public void ajaxAbilitaDisabilita(@RequestParam("codice") Integer codice, @RequestParam("autorizzato") Boolean autorizzato, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	// Fa una chiamata ajax che va a modificare dinamicamente il campo autorizzato
	PkId id = new PkId(codice);
	Messaggi messaggio = messaggiService.findById(id);
	messaggio.setAutorizzato(autorizzato);
	fixMergeMessaggioProperty(messaggio);
	try {
	    messaggiService.update(messaggio);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento: " + e.getMessage(), null));
	}
	// §§§END§§§
    }

    @Override
    protected void fixMergeEntityProperty(Forum entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Forum entity) {

    }

    protected void fixMergeMessaggioProperty(Messaggi entity) {

	if (entity.getForum() != null && entity.getForum().getId() != null && entity.getForum().getId().getCodice() == null) {
	    entity.setForum(null);
	}
	if (entity.getMessaggi() != null && entity.getMessaggi().getId() != null && entity.getMessaggi().getId().getCodice() == null) {
	    entity.setMessaggi(null);
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
