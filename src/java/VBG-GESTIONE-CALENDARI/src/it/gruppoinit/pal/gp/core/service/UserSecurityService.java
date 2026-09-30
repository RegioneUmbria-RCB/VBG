package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.annotations.cache.DeletableCacheElements;

import org.springframework.security.userdetails.UserDetails;

public interface UserSecurityService {

    public UserDetails loadUserById(Integer id);

    public UserDetails loadUserByUsername(String username);

    public UserDetails loadAdministratorUser();

    public UserDetails getCurrentlyAuthenticatedUser();

    public Object getCurrentlyAuthenticatedUserDetails();

    @DeletableCacheElements
    public void resetObjectCached();
}
