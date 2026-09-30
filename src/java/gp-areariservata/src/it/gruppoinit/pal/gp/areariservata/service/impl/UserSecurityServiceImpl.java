package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.security.LoggedUser;
import it.gruppoinit.pal.gp.areariservata.service.AnagrafeARJService;
import it.gruppoinit.pal.gp.areariservata.web.util.DeployProperties;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

/**
 * Implementazione del servizio di autenticazione.
 * 
 * @author Fabrizio Corsetti
 * 
 */
public class UserSecurityServiceImpl implements UserSecurityService, UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserSecurityServiceImpl.class);
    @Autowired
    private AnagrafeARJService anagrafeARJService;
    @Autowired
    private DeployProperties deployProperties;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

	LoggedUser user = null;
	GrantedAuthority[] auths = null;
	if (StringUtils.isNotBlank(username)) {
	    Anagrafe anagrafe = anagrafeARJService.findByUserId(username, true);
	    if (anagrafe == null) {
		logger.error("loadUserByUsername: username [{}] non trovato!", username);
		throw new UsernameNotFoundException("userid non trovato: " + username);
	    }
	    auths = populateGrantedAuthorities(anagrafe);
	    String password = anagrafe.getPassword() != null ? anagrafe.getPassword() : "";
	    user = new LoggedUser(String.valueOf(anagrafe.getId().getCodice()), password, true, true, true, true, auths);
	    loadUserConfiguration(anagrafe, user);
	} else {
	    logger.error("loadUserByUsername: username nullo!");
	    throw new UsernameNotFoundException("userid nulla!");
	}
	return user;
    }

    @Override
    public UserDetails getCurrentlyAuthenticatedUser() {

	SecurityContext context = SecurityContextHolder.getContext();
	Authentication authentication = context.getAuthentication();
	if (authentication != null) {
	    if (authentication.getPrincipal() instanceof UserDetails) {
		UserDetails details = (UserDetails) authentication.getPrincipal();
		return details;
	    } else {
		logger.warn("getCurrentlyAuthenticatedUser: SecurityContext.getAuthentication().getPrincipal() return null");
		return null;
	    }
	} else {
	    logger.warn("getCurrentlyAuthenticatedUser: SecurityContext.getAuthentication() return null");
	    return null;
	}
    }

    @Override
    public Anagrafe getCurrentlyAuthenticatedUserDetails() {

	LoggedUser user = (LoggedUser) this.getCurrentlyAuthenticatedUser();
	if (user == null) {
	    return null;
	}
	Integer codice = user.getCodiceAnagrafe();
	return anagrafeARJService.findById(new PkId(codice));
    }

    private GrantedAuthority[] populateGrantedAuthorities(Anagrafe anagrafe) {

	GrantedAuthority[] auths = new GrantedAuthorityImpl[1];
	String guestUserId = deployProperties.getServiziUserid();
	if (guestUserId.equals(anagrafe.getCodicefiscale())) {
	    auths[0] = new GrantedAuthorityImpl("ROLE_GUEST");
	} else {
	    auths[0] = new GrantedAuthorityImpl("ROLE_USER");
	}
	return auths;
    }

    private void loadUserConfiguration(Anagrafe anagrafe, LoggedUser user) {

	logger.debug("loadUserConfiguration({})", anagrafe.getDescrizioneRichiedente());
	user.setAnagrafe(anagrafe.getNominativo() + " " + anagrafe.getNome());
	user.setCf(anagrafe.getCodicefiscale());
	user.setEmail(anagrafe.getEmail());
	user.setCodiceAnagrafe(anagrafe.getId().getCodice());
    }

    @Override
    public UserDetails loadUserById(Integer id) {

	throw new RuntimeException("loadUserById: metodo non supportato");
    }

    @Override
    public UserDetails loadAdministratorUser() {

	LoggedUser user = null;
	GrantedAuthority[] auths = null;
	String nlaUserid = deployProperties.getNlaUserid();
	Anagrafe anagrafe = anagrafeARJService.findByUserId(nlaUserid, true);
	if (anagrafe == null) {
	    logger.error("loadAdministratorUser: username [{}] non trovato!", nlaUserid);
	    throw new UsernameNotFoundException("loadAdministratorUser: username non trovato: " + nlaUserid);
	}
	auths = populateGrantedAuthorities(anagrafe);
	String password = anagrafe.getPassword() != null ? anagrafe.getPassword() : "";
	user = new LoggedUser(String.valueOf(anagrafe.getId().getCodice()), password, true, true, true, true, auths);
	loadUserConfiguration(anagrafe, user);
	return user;
    }

    @Override
    public void resetObjectCached() {

	throw new RuntimeException("resetObjectCached: metodo non supportato");
    }
}
