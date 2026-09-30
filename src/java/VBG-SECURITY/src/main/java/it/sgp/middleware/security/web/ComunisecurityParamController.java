package it.sgp.middleware.security.web;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.sgp.middleware.security.domain.BaseCommand;
import it.sgp.middleware.security.domain.ComunisecurityParam;
import it.sgp.middleware.security.domain.ComunisecurityParamCommand;
import it.sgp.middleware.security.service.ComunisecurityParamService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("comunisecurityparam")
@RequestMapping("/comunisecurityparam")
public class ComunisecurityParamController extends BaseController<ComunisecurityParam> {

    @Override
    public Order getListDefaultOrder() {

	return new Order(Sort.Direction.ASC, "id");
    }

    @Autowired
    private ComunisecurityParamService comunisecurityparamService;

    @GetMapping("/list.htm")
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	ComunisecurityParamCommand command = new ComunisecurityParamCommand();
	List<ComunisecurityParam> comunisecurityparamList = comunisecurityparamService.findAll();
	Set<ComunisecurityParam> listaParametri = new LinkedHashSet<ComunisecurityParam>();
	for (ComunisecurityParam comunisecurityParam : comunisecurityparamList) {
	    listaParametri.add(comunisecurityParam);
	}
	command.setDisplayMode(BaseCommand.EDIT);
	command.setListaParametri(listaParametri);
	ModelMap model = new ModelMap(comunisecurityparamList);
	model.addAttribute("comunisecurityparam", command);
	return model;
    }

    @PostMapping("/update.htm")
    public String update(@ModelAttribute("comunisecurityparam") ComunisecurityParamCommand comunisecurityparamCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	try {
	    Set<ComunisecurityParam> listaParametri = comunisecurityparamCommand.getListaParametri();
	    for (ComunisecurityParam comunisecurityParam : listaParametri) {
		fixMergeEntityProperty(comunisecurityParam);
		comunisecurityparamService.update(comunisecurityParam);
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(e, result, comunisecurityparamCommand.getEntity(), true, e.getMessage());
	    return "comunisecurityparam/list";
	}
	status.setComplete();
	return "redirect:list.htm?status_msg=02";
    }

    @Override
    protected void fixMergeEntityProperty(ComunisecurityParam entity) {

	// non devo preparare niente
    }

    @Override
    protected void fixRenderEntityProperty(ComunisecurityParam entity) {

	// non devo preparare niente
    }

    @Override
    protected void setPageAttributes(Model model) {

	// non devo preparare niente
    }
}
