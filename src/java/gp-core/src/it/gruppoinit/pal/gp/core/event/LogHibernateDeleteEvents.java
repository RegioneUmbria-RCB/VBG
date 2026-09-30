package it.gruppoinit.pal.gp.core.event;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import org.hibernate.HibernateException;
import org.hibernate.event.DeleteEvent;
import org.hibernate.event.def.DefaultDeleteEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.WebAuthenticationDetails;
import org.springframework.security.userdetails.User;

public class LogHibernateDeleteEvents extends DefaultDeleteEventListener {

    private static final long serialVersionUID = -4400565775594398143L;
    private static final Logger log = LoggerFactory.getLogger(LogHibernateDeleteEvents.class);

    @Override
    public void onDelete(DeleteEvent event) throws HibernateException {

	if (log.isDebugEnabled()) {
	    String username = "";
	    String remoteAddress = "";
	    SecurityContext sc = SecurityContextHolder.getContext();
	    WebAuthenticationDetails wad;
	    Authentication auth;
	    if (sc != null) {
		auth = sc.getAuthentication();
		if (auth != null) {
		    wad = (WebAuthenticationDetails) auth.getDetails();
		    if (wad != null) {
			remoteAddress = wad.getRemoteAddress();
		    }
		    User user = (User) sc.getAuthentication().getPrincipal();
		    if (user != null) {
			username = user.getUsername();
		    }
		}
	    }
	    log.debug("\nLOG DELETE EVENT \nHOST: " + remoteAddress + "\nUSER: " + username + "\nIDCOMUNE: " + ORMHelper.getIdcomune() + "\nOBJECT: "
		    + event.getObject() + "\n");
	}
	super.onDelete(event);
    }
}
