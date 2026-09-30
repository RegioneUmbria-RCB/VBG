package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;

import org.springframework.security.userdetails.UserDetails;

public interface UserSecurityService {

    public UserDetails loadUserById(Integer id);

    public UserDetails loadUserByUsername(String username);

    public UserDetails loadAdministratorUser();

    public UserDetails loadAnyUser();

    public UserDetails getCurrentlyAuthenticatedUser();

    public Object getCurrentlyAuthenticatedUserDetails();

    @DeletableCacheElements
    public void resetObjectCached();
}
