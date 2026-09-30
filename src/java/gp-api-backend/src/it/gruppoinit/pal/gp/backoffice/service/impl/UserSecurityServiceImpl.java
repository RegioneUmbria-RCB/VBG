package it.gruppoinit.pal.gp.backoffice.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ResponsabiliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.rules.SecurityServiceRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * @author Fabrizio Corsetti
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
	fr.addFilterField(FilterUtils.equals("amministratore", "1", String.class));
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
		SecurityServiceRules securityServiceRules = (SecurityServiceRules) SigeproBusinessRules.getClassRules(SecurityServiceRules.class);
		boolean permettiUtenteLoggatoNullo = false;
		if (securityServiceRules != null) {
		    permettiUtenteLoggatoNullo = securityServiceRules.ispermettiUtenteLoggatoNullo();
		}
		if (permettiUtenteLoggatoNullo) {
		    // in caso di IMPORT ed altri applicativi che necessitano questa configurazione
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
	    logger.debug("## getcurrentlyauthudetails codice responsabile: {}", codice);
	    String key = getOperatoreInCacheKey(ORMHelper.getIdcomuneAlias(), codice);
	    logger.debug("## getcurrentlyauthudetails key: {}", key);
	    Responsabili responsabile = cachedResponsabili.get(key);
	    if (responsabile == null) {
		logger.debug("## getcurrentlyauthudetails responsabile non trovato nella cache, cerco per codice {} e idcomune {}", codice,
			ORMHelper.getIdcomune());
		responsabile = responsabiliDAO.findById(new PkId(codice));
		cachedResponsabili.put(key, responsabile);
	    }
	    logger.debug("## getcurrentlyauthudetails responsabile {} ", responsabile);
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
	Set<Configurazioneutente> configurazione = responsabile.getConfigurazioniutente();
	Map<String, Object> impostazioni = new HashMap<String, Object>();
	boolean stileSettato = false;
	for (Configurazioneutente configurazioneutente : configurazione) {
	    if (configurazioneutente.getId().getNomeparametro().equalsIgnoreCase(WebConstants.CSS_USER_PREF_STYLE)) {
		if (StringUtils.isNotBlank(configurazioneutente.getValore())) {
		    impostazioni.put(WebConstants.CSS_USER_PREF_STYLE, configurazioneutente.getValore());
		    stileSettato = true;
		} else {
		    impostazioni.put(WebConstants.CSS_USER_PREF_STYLE, WebConstants.CSS_DEFAULT_STYLE);
		    stileSettato = true;
		}
	    } else {
		impostazioni.put(configurazioneutente.getId().getNomeparametro(), configurazioneutente.getValore());
	    }
	}
	if (!stileSettato) {
	    impostazioni.put(WebConstants.CSS_USER_PREF_STYLE, WebConstants.CSS_DEFAULT_STYLE);
	}
	user.setImpostazioniUtente(impostazioni);
	user.setResponsabile(responsabile.getResponsabile());
	user.setEmail(responsabile.getEmail());
	user.setCodiceResponsabile(responsabile.getId().getCodice());
	if (!(responsabile.getAmministratore() == null || StringUtils.defaultIfEmpty(responsabile.getAmministratoresoftware(), "0").equals("0"))) {
	    user.setAmministratoreSoftware(true);
	}
	if (!(responsabile.getAmministratore() == null || StringUtils.defaultIfEmpty(responsabile.getAmministratore(), "0").equals("0"))) {
	    user.setAmministratore(true);
	}
	if (responsabile.getOggettoImmagine() != null) {
	    user.setCodiceOggettoImmagine(responsabile.getOggettoImmagine().getId().getCodice());
	}
    }

    /**
     * metodo per recuperare i ruoli del responsabile
     * 
     * @param responsabile
     * @return
     */
    private GrantedAuthority[] populateGrantedAuthorities(Responsabili responsabile) {

	// FIXME ad oggi i ruoli sono fissi, modificare per caricarli da db
	GrantedAuthority[] auths;
	if (StringUtils.defaultIfEmpty(responsabile.getAmministratore(), "0").equals("1")) {
	    auths = new GrantedAuthorityImpl[1];
	    if (BooleanUtils.isTrue(responsabile.getFlagEditlabel())) {
		auths = new GrantedAuthorityImpl[2];
	    }
	    auths[0] = new GrantedAuthorityImpl("ROLE_ADMINISTRATOR");
	    if (BooleanUtils.isTrue(responsabile.getFlagEditlabel())) {
		auths[1] = new GrantedAuthorityImpl("ROLE_EDITLABEL");
	    }
	} else {
	    auths = new GrantedAuthorityImpl[6];
	    if (BooleanUtils.isTrue(responsabile.getFlagEditlabel())) {
		auths = new GrantedAuthorityImpl[7];
	    }
	    auths[0] = new GrantedAuthorityImpl("ROLE_USER");
	    auths[1] = new GrantedAuthorityImpl("PERM_LIST");
	    auths[2] = new GrantedAuthorityImpl("PERM_VIEW");
	    auths[3] = new GrantedAuthorityImpl("PERM_INSERT");
	    auths[4] = new GrantedAuthorityImpl("PERM_UPDATE");
	    auths[5] = new GrantedAuthorityImpl("PERM_DELETE");
	    if (BooleanUtils.isTrue(responsabile.getFlagEditlabel())) {
		auths[6] = new GrantedAuthorityImpl("ROLE_EDITLABEL");
	    }
	}
	return auths;
    }
}
