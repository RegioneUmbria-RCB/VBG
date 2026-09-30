package it.gruppoinit.sigeprosecurity.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;

public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    private ContestoType contesto = ContestoType.APP;
    private String url;
    private String userId;
    private String password;
    private String alias;

    public ContestoType getContesto() {

	return contesto;
    }

    public void setContesto(ContestoType contesto) {

	this.contesto = contesto;
    }

    public String getUrl() {

	return url;
    }

    public void setUrl(String url) {

	this.url = url;
    }

    public String getUserId() {

	return userId;
    }

    public void setUserId(String userId) {

	this.userId = userId;
    }

    public String getPassword() {

	return password;
    }

    public void setPassword(String password) {

	this.password = password;
    }

    public String getAlias() {

	return alias;
    }

    public void setAlias(String alias) {

	this.alias = alias;
    }

    public static SecurityConfig fromPayConnectorConfigValuesService(PayConnectorConfigValuesService service) {

	SecurityConfig params = new SecurityConfig();
	log.debug("SecurityParams leggo i parametri di configurazione");
	params.setUrl(service.getValoreParametroConfigurazione(ConfigParamNames.SECURITY_URL));
	params.setUserId(service.getValoreParametroConfigurazione(ConfigParamNames.SECURITY_USER));
	params.setPassword(service.getValoreParametroConfigurazione(ConfigParamNames.SECURITY_PWD));
	params.setAlias(service.getValoreParametroConfigurazione(ConfigParamNames.SECURITY_ALIAS));
	log.debug("SecurityParams Parametri letti");
	return params;
    }
}
