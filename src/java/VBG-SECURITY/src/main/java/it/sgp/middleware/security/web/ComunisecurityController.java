package it.sgp.middleware.security.web;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import it.sgp.middleware.security.domain.AmbienteEnum;
import it.sgp.middleware.security.domain.BaseCommand;
import it.sgp.middleware.security.domain.Comunisecurity;
import it.sgp.middleware.security.domain.ComunisecurityCommand;
import it.sgp.middleware.security.domain.ComunisecurityConnection;
import it.sgp.middleware.security.domain.ComunisecurityConnectionId;
import it.sgp.middleware.security.service.ComunisecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("comunisecurity")
@RequestMapping("/comunisecurity")
public class ComunisecurityController extends BaseController<Comunisecurity> {

    private static Logger log = LoggerFactory.getLogger(ComunisecurityController.class);
    @Autowired
    private ComunisecurityService comunisecurityService;

    @Override
    public Order getListDefaultOrder() {

	return new Order(Sort.Direction.ASC, "id");
    }

    @RequestMapping(path = "/list.htm", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
	    HttpServletRequest request) {

	PageRequest pageable = getPageFromRequest(page, size, request);
	Page<Comunisecurity> list = comunisecurityService.findAllByExamplePaginated(pageable,
		getExampleFromRequest(model, request, new Comunisecurity()));
	model.addAttribute("dataList", list);
	int totalPages = list.getTotalPages();
	if (totalPages > 0) {
	    List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
	    model.addAttribute("pageNumbers", pageNumbers);
	}
	model.addAttribute("currentPage", page.orElse(1));
	return "comunisecurity/list";
    }

    @GetMapping(path = "/create.htm")
    public String create(Model model) {

	ComunisecurityCommand command = new ComunisecurityCommand();
	command.setDisplayMode(BaseCommand.NEW);
	Comunisecurity comunisecurity = new Comunisecurity();
	Set<ComunisecurityConnection> listaConnessioni = populateConnections(comunisecurity);
	comunisecurity.setComunisecurityConnections(listaConnessioni);
	fixRenderEntityProperty(comunisecurity);
	command.setEntity(comunisecurity);
	model.addAttribute("comunisecurity", command);
	setPageAttributes(model);
	return "comunisecurity/form";
    }

    @PostMapping(path = "/insert.htm")
    public String insert(Model model, @ModelAttribute("comunisecurity") ComunisecurityCommand comunisecurityCommand, BindingResult result,
	    SessionStatus status) {

	fixConnectionsId(comunisecurityCommand.getEntity());
	fixMergeEntityProperty(comunisecurityCommand.getEntity());
	try {
	    comunisecurityService.insert(comunisecurityCommand.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(e, result, comunisecurityCommand, true, e.getMessage());
	    setPageAttributes(model);
	    return "comunisecurity/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + comunisecurityCommand.getEntity().getId() + "&status_msg=01";
    }

    @GetMapping(path = "/view.htm")
    public String view(@RequestParam("codice") String codice, Model model, HttpServletRequest request) {

	ComunisecurityCommand command = new ComunisecurityCommand();
	command.setDisplayMode(BaseCommand.EDIT);
	Comunisecurity comunisecurity = comunisecurityService.findById(codice);
	fixRenderEntityProperty(comunisecurity);
	// Set<ComunisecurityConnection> connections = comunisecurity.getComunisecurityConnections();
	checkConnections(comunisecurity);
	command.setEntity(comunisecurity);
	model.addAttribute("comunisecurity", command);
	setPageAttributes(model);
	return "comunisecurity/form";
    }

    @PostMapping(path = "/update.htm")
    public String update(Model model, @ModelAttribute("comunisecurity") ComunisecurityCommand comunisecurityCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(comunisecurityCommand.getEntity());
	try {
	    comunisecurityService.update(comunisecurityCommand.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(e, result, comunisecurityCommand, true, e.getMessage());
	    setPageAttributes(model);
	    return "comunisecurity/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + comunisecurityCommand.getEntity().getId() + "&status_msg=02";
    }

    @PostMapping(path = "/delete.htm")
    public String delete(Model model, @ModelAttribute("comunisecurity") ComunisecurityCommand comunisecurityCommand, BindingResult result,
	    SessionStatus status) {

	Comunisecurity objToDelete = comunisecurityService.findById(comunisecurityCommand.getEntity().getId());
	try {
	    if (log.isDebugEnabled()) {
		log.debug("delete entity {}", objToDelete.getId());
	    }
	    comunisecurityService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(e, result, comunisecurityCommand, true, e.getMessage());
	    setPageAttributes(model);
	    return "comunisecurity/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @GetMapping(path = "/ajaxFindByDescrizioneOrAlias.htm")
    public void ajaxFindByDescrizioneOrAlias(@RequestParam String textToSearch, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	List<Comunisecurity> list = comunisecurityService.findByDescrizioneOrAlias(textToSearch);
	StringBuffer buffer = new StringBuffer("<ul>");
	int i = 0;
	boolean exceedsLimitsAjaxResult = false;
	for (Comunisecurity recordComuniSecurity : list) {
	    if (i < ajaxResultListLimit) {
		buffer.append("<li id='").append(recordComuniSecurity.getId()).append("'>")
			.append(StringUtils.defaultString(recordComuniSecurity.getDescrizione())).append(" (").append(recordComuniSecurity.getId())
			.append(")</li>");
	    } else {
		exceedsLimitsAjaxResult = true;
		buffer.append(getAjaxLimitExceedResultString(list.size()));
		break;
	    }
	    i++;
	}
	if (!exceedsLimitsAjaxResult) {
	    buffer.append("</ul>");
	}
	response.getWriter().write(buffer.toString());
    }

    @Override
    protected void fixMergeEntityProperty(Comunisecurity entity) {

	if (entity == null) {
	    return;
	}
	if (entity.getAttivo() == null) {
	    entity.setAttivo(Boolean.FALSE);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Comunisecurity entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    private Set<ComunisecurityConnection> populateConnections(Comunisecurity comunisecurity) {

	Set<ComunisecurityConnection> result = new LinkedHashSet<ComunisecurityConnection>();
	// areapersonale
	result.add(newConnection(AmbienteEnum.AREA_PERSONALE, comunisecurity));
	// ASP
	result.add(newConnection(AmbienteEnum.ASP, comunisecurity));
	// DOTNET
	result.add(newConnection(AmbienteEnum.DOTNET, comunisecurity));
	// Java
	result.add(newConnection(AmbienteEnum.JAVA, comunisecurity));
	return result;
    }

    private ComunisecurityConnection newConnection(AmbienteEnum ambiente, Comunisecurity comunisecurity) {

	ComunisecurityConnectionId id = new ComunisecurityConnectionId();
	id.setAmbiente(ambiente.toString());
	id.setFkAlias(comunisecurity.getId());
	ComunisecurityConnection connection = new ComunisecurityConnection();
	connection.setId(id);
	connection.setComunisecurity(comunisecurity);
	return connection;
    }

    private void checkConnections(Comunisecurity comunisecurity) {

	Set<ComunisecurityConnection> connections = comunisecurity.getComunisecurityConnections();
	int maxConnectionsNum = 4;
	if (connections.size() != maxConnectionsNum) {
	    if (connections.size() > maxConnectionsNum) {
		throw new RuntimeException("Sono possibili solamente " + maxConnectionsNum + " tipi di connessioni");
	    }
	    boolean aspTrovato = false;
	    boolean dotNetTrovato = false;
	    boolean javaTrovato = false;
	    boolean areaPersonaleTrovato = false;
	    for (ComunisecurityConnection comunisecurityConnection : connections) {
		String ambiente = comunisecurityConnection.getId().getAmbiente();
		if (ambiente.equalsIgnoreCase(AmbienteEnum.ASP.toString())) {
		    aspTrovato = true;
		}
		if (ambiente.equalsIgnoreCase(AmbienteEnum.DOTNET.toString())) {
		    dotNetTrovato = true;
		}
		if (ambiente.equalsIgnoreCase(AmbienteEnum.JAVA.toString())) {
		    javaTrovato = true;
		}
		if (ambiente.equalsIgnoreCase(AmbienteEnum.AREA_PERSONALE.toString())) {
		    areaPersonaleTrovato = true;
		}
	    }
	    if (!areaPersonaleTrovato) {
		connections.add(newConnection(AmbienteEnum.AREA_PERSONALE, comunisecurity));
	    }
	    if (!aspTrovato) {
		connections.add(newConnection(AmbienteEnum.ASP, comunisecurity));
	    }
	    if (!dotNetTrovato) {
		connections.add(newConnection(AmbienteEnum.DOTNET, comunisecurity));
	    }
	    if (!javaTrovato) {
		connections.add(newConnection(AmbienteEnum.JAVA, comunisecurity));
	    }
	}
    }

    private void fixConnectionsId(Comunisecurity comunisecurity) {

	Set<ComunisecurityConnection> connections = comunisecurity.getComunisecurityConnections();
	for (ComunisecurityConnection connection : connections) {
	    connection.getId().setFkAlias(comunisecurity.getId());
	}
    }
}
