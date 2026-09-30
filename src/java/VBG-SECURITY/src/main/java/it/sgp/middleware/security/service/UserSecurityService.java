package it.sgp.middleware.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserSecurityService extends UserDetailsService {

    public UserDetails getCurrentlyAuthenticatedUser();
}
