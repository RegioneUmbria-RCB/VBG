package it.sgp.middleware.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.wss4j.common.principal.WSUsernameTokenPrincipalImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.sgp.middleware.security.domain.ComunisecurityApp;
import it.sgp.middleware.security.exceptions.UsernameNotFoundException;
import it.sgp.middleware.security.service.ComunisecurityAppService;
import it.sgp.middleware.security.service.UserSecurityService;

@Service
@Transactional
public class UserSecurityServiceImpl implements UserDetailsService, UserSecurityService {

    private static final Logger logger = LoggerFactory.getLogger(UserSecurityServiceImpl.class);
    private ComunisecurityAppService comunisecurityAppService;

    @Autowired
    public void setComunisecurityAppService(ComunisecurityAppService comunisecurityAppService) {

	this.comunisecurityAppService = comunisecurityAppService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

	User user = null;
	logger.debug("loadUserByUsername: '{}'", username);
	if (StringUtils.isNotBlank(username)) {
	    ComunisecurityApp utente = comunisecurityAppService.findById(username);
	    if (utente == null) {
		logger.error("username '{}' not found", username);
		throw new UsernameNotFoundException(username);
	    }
	    List<GrantedAuthority> auths = populateGrantedAuthorities(utente);
	    user = new User(username, utente.getPassword(), true, true, true, true, auths);
	} else {
	    logger.error("username is empty");
	    throw new UsernameNotFoundException(username);
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
	    } else if (authentication.getPrincipal() instanceof WSUsernameTokenPrincipalImpl) {
		UserDetails details = (UserDetails) authentication.getDetails();
		return details;
	    } else {
		return null;
	    }
	} else {
	    return null;
	}
    }

    private List<GrantedAuthority> populateGrantedAuthorities(ComunisecurityApp utente) {

	List<GrantedAuthority> listAuths = new ArrayList<GrantedAuthority>();
	if (BooleanUtils.isTrue(utente.getAdmin())) {
	    listAuths.add(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));
	}
	listAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
	return listAuths;
    }
}
