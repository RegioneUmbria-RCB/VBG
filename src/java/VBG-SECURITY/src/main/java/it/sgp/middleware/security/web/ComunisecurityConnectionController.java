package it.sgp.middleware.security.web;

import it.sgp.middleware.security.domain.ComunisecurityConnection;
import it.sgp.middleware.security.domain.ComunisecurityConnectionId;
import it.sgp.middleware.security.service.ComunisecurityConnectionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
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
 * @author
 */
@Controller
@SessionAttributes("comunisecurityconnection")
public class ComunisecurityConnectionController extends BaseController<ComunisecurityConnection> {

    @Override
    public Order getListDefaultOrder() {

	return new Order(Sort.Direction.ASC, "ambiente");
    }

    /*
    @Autowired
    private ComunisecurityConnectionService comunisecurityconnectionService;
    
    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    
    	
    	List<ComunisecurityConnection> comunisecurityconnectionList = comunisecurityconnectionService.findAll(null, null);
    	ModelMap model = new ModelMap(comunisecurityconnectionList);
    	model.addAttribute("comunisecurityconnectionList", comunisecurityconnectionList);
    	return model;
    	
    	
    }
    
    @RequestMapping
    public String create(Model model) {
    
    	
    	ComunisecurityConnection comunisecurityconnection = new ComunisecurityConnection();
    	fixRenderEntityProperty(comunisecurityconnection);
    	model.addAttribute("comunisecurityconnection", comunisecurityconnection);
    	setPageAttributes(model);
    	return "comunisecurityconnection/form";
    	
    	
    }
    
    @RequestMapping
    public String insert(@ModelAttribute("comunisecurityconnection") ComunisecurityConnection comunisecurityconnection, BindingResult result,
    	    SessionStatus status) {
    
    	
    	fixMergeEntityProperty(comunisecurityconnection);
    	try {
    	    comunisecurityconnectionService.insert(comunisecurityconnection);
    	} catch (Exception e) {
    	    copyErrorsToBindingResult(comunisecurityconnectionService.getValidationMessages(), result, comunisecurityconnection, e.getMessage());
    	    fixRenderEntityProperty(comunisecurityconnection);
    	    return "comunisecurityconnection/form";
    	}
    	status.setComplete();
    	return "redirect:view.htm?codice=" + comunisecurityconnection.getId().getFkAlias() + "&status_msg=01";
    	
    	
    }
    
    @RequestMapping
    public String view(@RequestParam("codice") String codice, Model model, HttpServletRequest request) {
    
    	
    	ComunisecurityConnectionId id = new ComunisecurityConnectionId();
    	ComunisecurityConnection comunisecurityconnection = comunisecurityconnectionService.findById(id);
    	fixRenderEntityProperty(comunisecurityconnection);
    	model.addAttribute("comunisecurityconnection", comunisecurityconnection);
    	setPageAttributes(model);
    	return "comunisecurityconnection/form";
    	
    	
    }
    
    @RequestMapping
    public String update(@ModelAttribute("comunisecurityconnection") ComunisecurityConnection comunisecurityconnection, BindingResult result,
    	    SessionStatus status, HttpServletRequest request) {
    
    	
    	fixMergeEntityProperty(comunisecurityconnection);
    	try {
    	    comunisecurityconnectionService.update(comunisecurityconnection);
    	} catch (Exception e) {
    	    copyErrorsToBindingResult(comunisecurityconnectionService.getValidationMessages(), result, comunisecurityconnection, e.getMessage());
    	    fixRenderEntityProperty(comunisecurityconnection);
    	    return "comunisecurityconnection/form";
    	}
    	status.setComplete();
    	return "redirect:view.htm?codice=" + comunisecurityconnection.getId().getFkAlias() + "&status_msg=02";
    	
    	
    }
    
    @RequestMapping
    public String delete(@ModelAttribute("comunisecurityconnection") ComunisecurityConnection comunisecurityconnection, BindingResult result,
    	    SessionStatus status) {
    
    	
    	ComunisecurityConnection objToDelete = comunisecurityconnectionService.findById(comunisecurityconnection.getId());
    	try {
    	    comunisecurityconnectionService.delete(objToDelete);
    	} catch (Exception e) {
    	    copyErrorsToBindingResult(comunisecurityconnectionService.getValidationMessages(), result, objToDelete, e.getMessage());
    	    fixRenderEntityProperty(comunisecurityconnection);
    	    return "comunisecurityconnection/form";
    	}
    	status.setComplete();
    	return "redirect:list.htm";
    	
    	
    }
    */
    @Override
    protected void fixMergeEntityProperty(ComunisecurityConnection entity) {

    }

    @Override
    protected void fixRenderEntityProperty(ComunisecurityConnection entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
