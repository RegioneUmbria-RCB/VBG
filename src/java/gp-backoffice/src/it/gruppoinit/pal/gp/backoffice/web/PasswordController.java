package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.web.PasswordCommand;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("passwordCommand")
public class PasswordController extends BaseController<Responsabili> {

    @Autowired
    private ResponsabiliService responsabiliService;

    @RequestMapping
    public String view(Model model, HttpServletRequest request) {

	PasswordCommand passwordCommand = new PasswordCommand();
	model.addAttribute("passwordCommand", passwordCommand);
	return "password/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("passwordCommand") PasswordCommand passwordCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	try {
	    responsabiliService.updatePassword(responsabile, passwordCommand.getPassword(), passwordCommand.getNewPassword(),
		    passwordCommand.getNewPasswordConfirm());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, passwordCommand, false, e);
	    setPageAttributes(model);
	    return "password/form";
	}
	return "redirect:view.htm?status_msg=02";
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(Responsabili entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(Responsabili entity) {

	// TODO Auto-generated method stub
    }
}
