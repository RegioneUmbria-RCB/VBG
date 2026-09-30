package it.sgp.middleware.security.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.sgp.middleware.security.domain.Comunisecurity;
import it.sgp.middleware.security.domain.ComunisecurityApp;
import it.sgp.middleware.security.domain.ComunisecurityAppCommand;
import it.sgp.middleware.security.service.ComunisecurityAppService;
import it.sgp.middleware.security.service.ComunisecurityService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("comunisecurityapp")
@RequestMapping("/comunisecurityapp")
public class ComunisecurityAppController extends BaseController<ComunisecurityApp> {

    @Autowired
    private ComunisecurityAppService comunisecurityappService;
    @Autowired
    private ComunisecurityService comunisecurityService;

    @Override
    public Order getListDefaultOrder() {

	return new Order(Sort.Direction.ASC, "id");
    }

    /**
     * 
     * @param model
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(path = "/list.htm", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
	    HttpServletRequest request) {

	// https://www.bezkoder.com/thymeleaf-pagination-and-sorting-example/
	// https://www.baeldung.com/spring-thymeleaf-pagination
	PageRequest pageable = getPageFromRequest(page, size, request);
	Page<ComunisecurityApp> list = comunisecurityappService.findAllByExamplePaginated(pageable,
		getExampleFromRequest(model, request, new ComunisecurityApp()));
	model.addAttribute("dataList", list);
	int totalPages = list.getTotalPages();
	if (totalPages > 0) {
	    List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
	    model.addAttribute("pageNumbers", pageNumbers);
	}
	model.addAttribute("currentPage", page.orElse(1));
	return "comunisecurityapp/list";
    }

    @GetMapping(path = "/create.htm")
    public String create(Model model) {

	ComunisecurityAppCommand command = new ComunisecurityAppCommand();
	ComunisecurityApp comunisecurityapp = new ComunisecurityApp();
	fixRenderEntityProperty(comunisecurityapp);
	command.setEntity(comunisecurityapp);
	command.setDisplayMode(ComunisecurityAppCommand.NEW);
	model.addAttribute("comunisecurityapp", command);
	setPageAttributes(model);
	return "comunisecurityapp/form";
    }

    @PostMapping(path = "/insert.htm")
    public String insert(Model model, @ModelAttribute("comunisecurityapp") ComunisecurityAppCommand comunisecurityappCommand, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(comunisecurityappCommand.getEntity());
	try {
	    comunisecurityappService.insert(comunisecurityappCommand.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(e, result, comunisecurityappCommand, true, e.getMessage());
	    setPageAttributes(model);
	    return "comunisecurityapp/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + comunisecurityappCommand.getEntity().getId() + "&status_msg=01";
    }

    @GetMapping(path = "/view.htm")
    public String view(@RequestParam("codice") String codice, Model model, HttpServletRequest request) {

	ComunisecurityAppCommand command = new ComunisecurityAppCommand();
	ComunisecurityApp comunisecurityapp = comunisecurityappService.findById(codice);
	fixRenderEntityProperty(comunisecurityapp);
	command.setEntity(comunisecurityapp);
	command.setDisplayMode(ComunisecurityAppCommand.EDIT);
	model.addAttribute("comunisecurityapp", command);
	setPageAttributes(model);
	return "comunisecurityapp/form";
    }

    @PostMapping(path = "/update.htm")
    public String update(Model model, @ModelAttribute("comunisecurityapp") ComunisecurityAppCommand comunisecurityappCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(comunisecurityappCommand.getEntity());
	try {
	    comunisecurityappService.update(comunisecurityappCommand.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(e, result, comunisecurityappCommand, true, e.getMessage());
	    setPageAttributes(model);
	    return "comunisecurityapp/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + comunisecurityappCommand.getEntity().getId() + "&status_msg=02";
    }

    @PostMapping(path = "/delete.htm")
    public String delete(Model model, @ModelAttribute("comunisecurityapp") ComunisecurityAppCommand comunisecurityappCommand, BindingResult result,
	    SessionStatus status) {

	ComunisecurityApp objToDelete = comunisecurityappService.findById(comunisecurityappCommand.getEntity().getId());
	try {
	    comunisecurityappService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(e, result, comunisecurityappCommand, true, e.getMessage());
	    setPageAttributes(model);
	    return "comunisecurityapp/form";
	}
	status.setComplete();
	return "redirect:list.htm?status_msg=05";
    }

    @Override
    protected void fixMergeEntityProperty(ComunisecurityApp entity) {

	if (entity == null) {
	    return;
	}
	if (entity.getComunisecurity() != null && StringUtils.isBlank(entity.getComunisecurity().getId())) {
	    entity.setComunisecurity(null);
	}
	if (entity.getAdmin() == null) {
	    entity.setAdmin(Boolean.FALSE);
	}
    }

    @Override
    protected void fixRenderEntityProperty(ComunisecurityApp entity) {

	if (entity.getComunisecurity() != null && StringUtils.isBlank(entity.getComunisecurity().getId())) {
	    entity.setComunisecurity(new Comunisecurity());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	List<Comunisecurity> listaAttivazioni = comunisecurityService.findAll();
	model.addAttribute("listaAttivazioni", listaAttivazioni);
    }
}
