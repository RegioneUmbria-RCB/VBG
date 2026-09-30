package it.gruppoinit.pal.gp.backoffice.web.filter;

import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationCredentialsNotFoundException;
import org.springframework.security.AuthenticationException;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.switchuser.SwitchUserProcessingFilter;

public class CustomSwitchUserFilter extends SwitchUserProcessingFilter {

    @Override
    protected Authentication attemptSwitchUser(HttpServletRequest request) throws AuthenticationException {

	Authentication current = SecurityContextHolder.getContext().getAuthentication();
	// Put here all the checkings and initialization you want to check before switching.
	String utente = request.getParameter("j_username");
	if (current != null) {
	    LoggedUser r = (LoggedUser) current.getPrincipal();
	    LoggerCancellazioni.log("#####IMPERSONATE BEGIN####### L'utente " + r + " ha impersonato l'operatore " + utente);
	}
	return super.attemptSwitchUser(request);
    }

    @Override
    protected Authentication attemptExitUser(HttpServletRequest request) throws AuthenticationCredentialsNotFoundException {

	Authentication current = SecurityContextHolder.getContext().getAuthentication();
	// Checkings when switch back called.
	if (current != null) {
	    LoggedUser r = (LoggedUser) current.getPrincipal();
	    LoggerCancellazioni.log("#####IMPERSONATE END####### terminata l'impersonalizzazione dell'utente " + r);
	}
	return super.attemptExitUser(request);
    }
}
