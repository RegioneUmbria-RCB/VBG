package org.jmesa.core.preference;

import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

import org.apache.commons.lang.StringUtils;
import org.jmesa.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class UserPreferences implements Preferences {

    private final Logger logger = LoggerFactory.getLogger(UserPreferences.class);
    private PropertiesPreferences preferences;
    private ConfigurazioneutenteService configurazioneutenteService;
    private UserSecurityService userSecurityService;
    private final String LIMIT_ROWSELECT_MAXROWS = "limit.rowSelect.maxRows";
    private final String HTML_TOOLBAR_MAXROWSDROPLIST_INCREMENT = "html.toolbar.maxRowsDroplist.increments";
    private final String NUM_RECORD_LISTE = "NUMRECORDLISTE";

    public UserPreferences(String preferencesLocation, WebContext webContext) {

	preferences = new PropertiesPreferences(preferencesLocation, webContext);
	WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
	configurazioneutenteService = (ConfigurazioneutenteService) wac.getBean("configurazioneutenteServiceImpl");
	userSecurityService = (UserSecurityService) wac.getBean("userSecurityService");
    }

    @Override
    public String getPreference(String code) {

	String pref = preferences.getPreference(code);
	String prefMaxRowsDroplist = preferences.getPreference(HTML_TOOLBAR_MAXROWSDROPLIST_INCREMENT);
	String userPref = "";
	//limito la sovrascrittura delle jmesa props alla sola prop limit.rowSelect.maxRows
	if (LIMIT_ROWSELECT_MAXROWS.equals(code)) {
	    String userPrefTemp = this.getUserPreference(NUM_RECORD_LISTE);
	    // la MaxRows deve essere tra i valori di MaxRowsDroplist
	    if (StringUtils.isNotBlank(userPrefTemp)) {
		if (prefMaxRowsDroplist.indexOf(userPrefTemp) > -1) {
		    userPref = userPrefTemp;
		} else {
		    // Setto il valore recuperato dal file jmesa.properties
		    if (StringUtils.isNotBlank(pref)) {
			userPref = pref;
		    } else {
			logger.error("Il valore limit.rowSelect.maxRows sul file jmesa.properties è nullo o stringa vuota");
			throw new RuntimeException("Il valore limit.rowSelect.maxRows sul file jmesa.properties è nullo o stringa vuota");
		    }
		    //		    logger.warn("Il valore dell'impostazione utente 'Limite record nelle liste (NUMRECORDLISTE)' deve essere uno dei seguenti: "
		    //			    + prefMaxRowsDroplist);
		}
	    }
	}
	return StringUtils.isNotBlank(userPref) ? userPref : pref;
    }

    private String getUserPreference(String code) {

	String val = "";
	try {
	    LoggedUser userDetail = (LoggedUser) userSecurityService.getCurrentlyAuthenticatedUser();
	    ConfigurazioneutenteId confId = new ConfigurazioneutenteId(userDetail.getCodiceResponsabile(), code);
	    Configurazioneutente confUtente = configurazioneutenteService.findById(confId);
	    if (confUtente != null) {
		val = confUtente.getValore();
	    }
	} catch (Exception e) {
	    logger.error("Could not load the JMesa preferences.", e);
	}
	return val;
    }
}
