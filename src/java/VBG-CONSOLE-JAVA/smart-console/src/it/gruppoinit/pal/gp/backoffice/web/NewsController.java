package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.News;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.NewsService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.HashSet;
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
@SessionAttributes("news")
public class NewsController extends BaseController<News> {

    @Autowired
    private NewsService newsService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	Set<Software> softwareList = new HashSet<Software>();
	Set<Responsabilisoftware> responsabilisoftwareList = responsabile.getSoftwareAbilitati();
	for (Responsabilisoftware responsabilisoftware : responsabilisoftwareList) {
	    softwareList.add(responsabilisoftware.getSoftware());
	}
	List<News> newsList = newsService.findByFilter(softwareList);
	ModelMap model = new ModelMap(newsList);
	boolean export = createJMesaExport(request, response, newsList);
	if (export) {
	    return null;
	}
	model.addAttribute("newsList", newsList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	News news = new News();
	fixRenderEntityProperty(news);
	model.addAttribute("news", news);
	setPageAttributes(model);
	return "news/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("news") News news, BindingResult result, SessionStatus status) {

	if (news.getOggettiByFkNews1Oggetti() != null) {
	    news.setImgtesta1(news.getOggettiByFkNews1Oggetti().getNomefile());
	}
	if (news.getOggettiByFkNews2Oggetti() != null) {
	    news.setImgfondo1(news.getOggettiByFkNews2Oggetti().getNomefile());
	}
	if (news.getOggettiByFkNews3Oggetti() != null) {
	    news.setImgfondo2(news.getOggettiByFkNews3Oggetti().getNomefile());
	}
	if (news.getOggettiByFkNews4Oggetti() != null) {
	    news.setImgfondo3(news.getOggettiByFkNews4Oggetti().getNomefile());
	}
	try {
	    newsService.insert(news);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, news, e);
	    fixRenderEntityProperty(news);
	    setPageAttributes(model);
	    return "news/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + news.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	News news = newsService.findById(id);
	fixRenderEntityProperty(news);
	model.addAttribute("news", news);
	setPageAttributes(model);
	return "news/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("news") News news, BindingResult result, SessionStatus status, HttpServletRequest request) {

	if (news.getOggettiByFkNews1Oggetti() != null) {
	    news.setImgtesta1(news.getOggettiByFkNews1Oggetti().getNomefile());
	} else {
	    news.setImgtesta1(null);
	}
	if (news.getOggettiByFkNews2Oggetti() != null) {
	    news.setImgfondo1(news.getOggettiByFkNews2Oggetti().getNomefile());
	} else {
	    news.setImgfondo1(null);
	}
	if (news.getOggettiByFkNews3Oggetti() != null) {
	    news.setImgfondo2(news.getOggettiByFkNews3Oggetti().getNomefile());
	} else {
	    news.setImgfondo2(null);
	}
	if (news.getOggettiByFkNews4Oggetti() != null) {
	    news.setImgfondo3(news.getOggettiByFkNews4Oggetti().getNomefile());
	} else {
	    news.setImgfondo3(null);
	}
	try {
	    newsService.update(news);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, news, e);
	    fixRenderEntityProperty(news);
	    setPageAttributes(model);
	    return "news/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + news.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("news") News news, BindingResult result, SessionStatus status) {

	News objToDelete = newsService.findById(news.getId());
	try {
	    newsService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(news);
	    setPageAttributes(model);
	    return "news/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(News entity) {

	if (entity.getOggettiByFkNews1Oggetti() != null && entity.getOggettiByFkNews1Oggetti().getId() != null
		&& entity.getOggettiByFkNews1Oggetti().getId().getCodice() == null) {
	    entity.setOggettiByFkNews1Oggetti(null);
	}
	if (entity.getOggettiByFkNews2Oggetti() != null && entity.getOggettiByFkNews2Oggetti().getId() != null
		&& entity.getOggettiByFkNews2Oggetti().getId().getCodice() == null) {
	    entity.setOggettiByFkNews2Oggetti(null);
	}
	if (entity.getOggettiByFkNews3Oggetti() != null && entity.getOggettiByFkNews3Oggetti().getId() != null
		&& entity.getOggettiByFkNews3Oggetti().getId().getCodice() == null) {
	    entity.setOggettiByFkNews3Oggetti(null);
	}
	if (entity.getOggettiByFkNews4Oggetti() != null && entity.getOggettiByFkNews4Oggetti().getId() != null
		&& entity.getOggettiByFkNews4Oggetti().getId().getCodice() == null) {
	    entity.setOggettiByFkNews4Oggetti(null);
	}
	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null || entity.getSoftware().getCodice().equals(""))) {
	    entity.setSoftware(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(News entity) {

	if (entity.getOggettiByFkNews1Oggetti() == null) {
	    entity.setOggettiByFkNews1Oggetti(new Oggetti());
	}
	if (entity.getOggettiByFkNews2Oggetti() == null) {
	    entity.setOggettiByFkNews2Oggetti(new Oggetti());
	}
	if (entity.getOggettiByFkNews3Oggetti() == null) {
	    entity.setOggettiByFkNews3Oggetti(new Oggetti());
	}
	if (entity.getOggettiByFkNews4Oggetti() == null) {
	    entity.setOggettiByFkNews4Oggetti(new Oggetti());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	List<Software> listSoftware = softwareService.findSoftwareAbilitati(responsabile);
	model.addAttribute("listSoftware", listSoftware);
    }
}
