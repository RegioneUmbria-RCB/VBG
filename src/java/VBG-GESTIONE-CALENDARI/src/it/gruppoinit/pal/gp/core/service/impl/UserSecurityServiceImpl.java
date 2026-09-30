package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.ResponsabiliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.rules.AppBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SecurityServiceRules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
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
import org.springframework.util.Assert;

/**
 * Implementazione del servizio di autenticazione.
 * 
 * @author fabrizioc
 * 
 */
public class UserSecurityServiceImpl implements UserSecurityService, UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserSecurityServiceImpl.class);
    private ResponsabiliDAO responsabiliDAO;
    private Map<String, Responsabili> cachedResponsabili = new HashMap<String, Responsabili>();

    @Autowired
    public void setResponsabiliDAO(ResponsabiliDAO responsabiliDAO) {

	this.responsabiliDAO = responsabiliDAO;
    }

    @Override
    public UserDetails loadUserById(Integer codiceResponsabile) {

	Assert.notNull(codiceResponsabile);
	GrantedAuthority[] auths = null;
	PkId responsabileId = new PkId();
	responsabileId.setCodice(codiceResponsabile);
	Responsabili responsabile = responsabiliDAO.findById(responsabileId);
	if (responsabile == null) {
	    logger.error("loadUserById: responsabile non trovato, codice: {}", codiceResponsabile);
	    throw new UsernameNotFoundException(String.valueOf(codiceResponsabile));
	}
	auths = populateGrantedAuthorities(responsabile);
	LoggedUser user = new LoggedUser(String.valueOf(codiceResponsabile), "", true, true, true, true, auths);
	loadUserConfiguration(responsabile, user);
	return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

	LoggedUser user = null;
	GrantedAuthority[] auths = null;
	if (StringUtils.isNotBlank(username)) {
	    Responsabili responsabile = responsabiliDAO.findByUserid(username);
	    if (responsabile == null) {
		logger.warn("loadUserByUsername: username non trovato: {}", username);
		throw new UsernameNotFoundException("userid non trovato: " + username);
	    }
	    auths = populateGrantedAuthorities(responsabile);
	    user = new LoggedUser(String.valueOf(responsabile.getId().getCodice()), responsabile.getPassword(), true, true, true, true, auths);
	    loadUserConfiguration(responsabile, user);
	} else {
	    logger.error("loadUserByUsername: username nullo! idcomunealias:{}", ORMHelper.getIdcomuneAlias());
	    throw new UsernameNotFoundException("userid nulla!");
	}
	return user;
    }

    @Override
    public UserDetails loadAdministratorUser() {

	LoggedUser user = null;
	GrantedAuthority[] auths = null;
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("amministratore", "1", Boolean.class));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("responsabile"));
	List<Responsabili> responsabilis = responsabiliDAO.findByFilterTable(filterTable);
	Responsabili responsabile;
	if (responsabilis.isEmpty()) {
	    logger.error("loadAdministratorUser: nessun utente amministratore trovato");
	    throw new UsernameNotFoundException("nessun utente amministratore trovato");
	} else {
	    responsabile = responsabilis.get(0);
	}
	auths = populateGrantedAuthorities(responsabile);
	user = new LoggedUser(String.valueOf(responsabile.getId().getCodice()), responsabile.getPassword(), true, true, true, true, auths);
	loadUserConfiguration(responsabile, user);
	return user;
    }

    @Override
    public UserDetails getCurrentlyAuthenticatedUser() {

	try {
	    SecurityContext context = SecurityContextHolder.getContext();
	    Authentication authentication = context.getAuthentication();
	    if (authentication != null) {
		return (UserDetails) authentication.getPrincipal();
	    } else {
		SecurityServiceRules securityServiceRules = (SecurityServiceRules) AppBusinessRules.getClassRules(SecurityServiceRules.class);
		boolean permettiUtenteLoggatoNullo = false;
		if (securityServiceRules != null) {
		    permettiUtenteLoggatoNullo = securityServiceRules.ispermettiUtenteLoggatoNullo();
		}
		if (permettiUtenteLoggatoNullo) {
		    // in caso di IMPORT ed altri applicativi che necessitano
		    // questa configurazione
		    UserDetails user = this.loadAdministratorUser();
		    logger.warn("Accesso con regola [{}] attivata. E' stato effettuato l'accesso come utente amministratore [{}]",
			    SecurityServiceRules.CustomRuleEnum.permettiUtenteLoggatoNullo.name(), user.getUsername());
		    return loadAdministratorUser();
		}
	    }
	    return null;
	} catch (Exception e) {
	    logger.error("getCurrentlyAuthenticatedUser:", e);
	    throw new RuntimeException("Errore durante il controllo dell'utente autenticato:" + e.getMessage(), e);
	}
    }

    @Override
    public Responsabili getCurrentlyAuthenticatedUserDetails() {

	Integer codice = null;
	try {
	    LoggedUser user = (LoggedUser) this.getCurrentlyAuthenticatedUser();
	    codice = user.getCodiceResponsabile();
	    String key = getOperatoreInCacheKey(ORMHelper.getIdcomuneAlias(), codice);
	    Responsabili responsabile = cachedResponsabili.get(key);
	    if (responsabile == null) {
		responsabile = responsabiliDAO.findById(new PkId(codice));
		cachedResponsabili.put(key, responsabile);
	    }
	    return responsabile;
	} catch (Exception e) {
	    logger.error("getCurrentlyAuthenticatedUserDetails: codiceresponsabile={}", codice, e);
	    throw new RuntimeException("Errore durante il recupero delle informazioni dell'utente autenticato: " + e.getMessage(), e);
	}
    }

    private String getOperatoreInCacheKey(String idcomuneAlias, Integer codiceOperatore) {

	return idcomuneAlias + "-" + String.valueOf(codiceOperatore);
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	this.cachedResponsabili = new HashMap<String, Responsabili>();
    }

    /**
     * metodo per caricare la configurazione utente nell'oggetto LoggedUser
     * 
     * @param responsabile
     * @param user
     */
    private void loadUserConfiguration(Responsabili responsabile, LoggedUser user) {

	logger.debug("loadUserConfiguration({})", responsabile.getResponsabile());
	user.setResponsabile(responsabile.getResponsabile());
	user.setCodiceResponsabile(responsabile.getId().getCodice());
	if (BooleanUtils.isTrue(responsabile.getAmministratore())) {
	    user.setAmministratore(true);
	}
	user.setReadonly(responsabile.getReadonly());
    }

    /**
     * metodo per recuperare i ruoli del responsabile
     * 
     * @param responsabile
     * @return
     */
    private GrantedAuthority[] populateGrantedAuthorities(Responsabili responsabile) {

	// TODO ad oggi i ruoli sono fissi, modificare per caricarli da db
	List<GrantedAuthorityImpl> roles = new ArrayList<GrantedAuthorityImpl>();
	roles.add(new GrantedAuthorityImpl("ROLE_USER"));
	if (BooleanUtils.isTrue(responsabile.getAmministratore())) {
	    roles.add(new GrantedAuthorityImpl("ROLE_ADMINISTRATOR"));
	}
	if (BooleanUtils.isTrue(responsabile.getGestioneFesteSagre())) {
	    roles.add(new GrantedAuthorityImpl("ROLE_GESTIONE_FESTE_SAGRE"));
	}
	if (BooleanUtils.isTrue(responsabile.getGestioneFiereMostre())) {
	    roles.add(new GrantedAuthorityImpl("ROLE_GESTIONE_FIERE_MOSTRE"));
	}
	if (BooleanUtils.isTrue(responsabile.getReadonly())) {
	    roles.add(new GrantedAuthorityImpl("ROLE_READONLY"));
	}
	if (BooleanUtils.isTrue(responsabile.getGestioneAreepubbliche())) {
	    roles.add(new GrantedAuthorityImpl("ROLE_GESTIONE_MANIFESTAZIONI_AREE_PUBBLICHE"));
	}
	if (BooleanUtils.isTrue(responsabile.getGestioneInserimentoAnagrafiche())) {
	    roles.add(new GrantedAuthorityImpl("ROLE_GESTIONE_INSERIMENTI_ANAGRAFICHE"));
	}
	return roles.toArray(new GrantedAuthorityImpl[roles.size()]);
    }
}
