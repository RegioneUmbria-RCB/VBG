package it.gruppoinit.pal.gp.pay.service.helper;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class RabbitConfig {

    public enum RABBT_PARAMS {
	RABBIT_HOSTNAME,
	RABBIT_PORT,
	RABBIT_USERNAME,
	RABBIT_PASSWORD,
	RABBIT_EXCHANGE_NAME,
	RABBIT_MESSAGGI_PAGAMENTI_SERVICE,
    }

    private String rabbitHostName;
    private String rabbitPort;
    private String rabbitUserName;
    private String rabbitPassword;
    private String rabbitExchangeName;
    private String rabbitMessaggiPagamentiService;

    private RabbitConfig() {

	super();
    }

    public static RabbitConfig fromSecurityParams(Map<String, String> securityParams) {

	RabbitConfig r = new RabbitConfig();
	Map<String, String> params = securityParams;
	r.rabbitHostName = params.get(RABBT_PARAMS.RABBIT_HOSTNAME.name());
	r.rabbitPort = params.get(RABBT_PARAMS.RABBIT_PORT.name());
	r.rabbitUserName = params.get(RABBT_PARAMS.RABBIT_USERNAME.name());
	r.rabbitPassword = params.get(RABBT_PARAMS.RABBIT_PASSWORD.name());
	r.rabbitMessaggiPagamentiService = params.get(RABBT_PARAMS.RABBIT_MESSAGGI_PAGAMENTI_SERVICE.name());
	return r;
    }

    public boolean isRabbitPagamentiServiceAvailable() {

	return StringUtils.isNotBlank(this.rabbitMessaggiPagamentiService);
    }

    public String getRabbitHostName() {

	return rabbitHostName;
    }

    public String getRabbitPort() {

	return rabbitPort;
    }

    public String getRabbitUserName() {

	return rabbitUserName;
    }

    public String getRabbitPassword() {

	return rabbitPassword;
    }

    public String getRabbitExchangeName() {

	return rabbitExchangeName;
    }

    public String getAggiornaStatoAPI() {

	if (isRabbitPagamentiServiceAvailable()) {
	    return this.rabbitMessaggiPagamentiService + "/rest/notifica/posizioni-debitorie/aggiorna-stato";
	}
	return null;
    }

    public String getAggiornaDataScadenzaAPI() {

	if (isRabbitPagamentiServiceAvailable()) {
	    return this.rabbitMessaggiPagamentiService + "/rest/notifica/posizioni-debitorie/aggiorna-data-scadenza";
	}
	return null;
    }
}
