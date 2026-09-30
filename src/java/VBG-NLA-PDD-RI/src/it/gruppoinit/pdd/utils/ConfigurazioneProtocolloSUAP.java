package it.gruppoinit.pdd.utils;

public class ConfigurazioneProtocolloSUAP extends ConfigurazioneSistema {

    private boolean mtomDettaglioEnabled = false;
    private String urlWSPddDettaglio;
    private boolean dettaglioWsAuthUseAuthentication = false;
    private String dettaglioWsAuthUserName = "";
    private String dettaglioWsAuthPassword = "";
    private String dettaglioWsAuthTipoAutenticazione = "";
    private String decorator = "";
    private boolean comunicazioneSTD = false;

    private ConfigurazioneProtocolloSUAP() {

	super();
    }

    public ConfigurazioneProtocolloSUAP(String idcomunealias) {

	this();
	this.mtomDettaglioEnabled = StringToBoolean(getPropertyValueForIdComuneAlias("pdd.ri.ws.dettaglio.mtom.enabled", idcomunealias));
	this.urlWSPddDettaglio = getPropertyValueForIdComuneAlias("pdd.ri.ws.dettaglio.url", idcomunealias);
	this.dettaglioWsAuthUseAuthentication = StringToBoolean(
		getPropertyValueForIdComuneAlias("pdd.ri.ws.dettaglio.auth.useauthentication", idcomunealias));
	this.dettaglioWsAuthUserName = getPropertyValueForIdComuneAlias("pdd.ri.ws.dettaglio.auth.username", idcomunealias);
	this.dettaglioWsAuthPassword = getPropertyValueForIdComuneAlias("pdd.ri.ws.dettaglio.auth.pwd", idcomunealias);
	this.dettaglioWsAuthTipoAutenticazione = getPropertyValueForIdComuneAlias("pdd.ri.ws.dettaglio.auth.tipo", idcomunealias);
	this.decorator = getPropertyValueForIdComuneAlias("pdd.ri.ws.decorator", idcomunealias);
	this.comunicazioneSTD = StringToBoolean(getPropertyValueForIdComuneAlias("pdd.ri.ws.dettaglio.ws.comunicazione.std", idcomunealias));
    }

    public void setDecorator(String decorator) {

	this.decorator = decorator;
    }

    public String getDecorator() {

	return decorator;
    }

    public boolean isMtomDettaglioEnabled() {

	return mtomDettaglioEnabled;
    }

    public String getUrlWSPddDettaglio() {

	return urlWSPddDettaglio;
    }

    public boolean isDettaglioWsAuthUseAuthentication() {

	return dettaglioWsAuthUseAuthentication;
    }

    public String getDettaglioWsAuthUserName() {

	return dettaglioWsAuthUserName;
    }

    public String getDettaglioWsAuthPassword() {

	return dettaglioWsAuthPassword;
    }

    public String getDettaglioWsAuthTipoAutenticazione() {

	return dettaglioWsAuthTipoAutenticazione;
    }

    public boolean isComunicazioneSTD() {

	return comunicazioneSTD;
    }
}
