package it.gruppoinit.pdd.utils;

public class ConfigurazioneRichiestaIscrizioneImpresaRiSPC extends ConfigurazioneSistema {

    private boolean mtomIscrizioneEnabled = false;
    private String urlWSPddIscrizione;
    // AUTHENTICATION ISCRIZIONE
    private boolean iscrizioneWsAuthUseAuthentication = false;
    private String iscrizioneWsAuthUserName = "";
    private String iscrizioneWsAuthPassword = "";
    private String iscrizioneWsAuthTipoAutenticazione = "";
    private String decorator = "";

    private ConfigurazioneRichiestaIscrizioneImpresaRiSPC() {

	super();
    }

    public ConfigurazioneRichiestaIscrizioneImpresaRiSPC(String idcomunealias) {

	this();
	this.mtomIscrizioneEnabled = StringToBoolean(getPropertyValueForIdComuneAlias("pdd.ri.ws.iscrizione.mtom.enabled", idcomunealias));
	this.urlWSPddIscrizione = getPropertyValueForIdComuneAlias("pdd.ri.ws.iscrizione.url", idcomunealias);
	this.iscrizioneWsAuthUseAuthentication = StringToBoolean(getPropertyValueForIdComuneAlias("pdd.ri.ws.iscrizione.auth.useauthentication",
		idcomunealias));
	this.iscrizioneWsAuthUserName = getPropertyValueForIdComuneAlias("pdd.ri.ws.iscrizione.auth.username", idcomunealias);
	this.iscrizioneWsAuthPassword = getPropertyValueForIdComuneAlias("pdd.ri.ws.iscrizione.auth.pwd", idcomunealias);
	this.iscrizioneWsAuthTipoAutenticazione = getPropertyValueForIdComuneAlias("pdd.ri.ws.iscrizione.auth.tipo", idcomunealias);
	this.decorator = getPropertyValueForIdComuneAlias("pdd.ri.ws.decorator", idcomunealias);
    }

    public void setDecorator(String decorator) {

	this.decorator = decorator;
    }

    public String getDecorator() {

	return decorator;
    }

    public boolean isMtomIscrizioneEnabled() {

	return mtomIscrizioneEnabled;
    }

    public String getUrlWSPddIscrizione() {

	return urlWSPddIscrizione;
    }

    public boolean isIscrizioneWsAuthUseAuthentication() {

	return iscrizioneWsAuthUseAuthentication;
    }

    public String getIscrizioneWsAuthUserName() {

	return iscrizioneWsAuthUserName;
    }

    public String getIscrizioneWsAuthPassword() {

	return iscrizioneWsAuthPassword;
    }

    public String getIscrizioneWsAuthTipoAutenticazione() {

	return iscrizioneWsAuthTipoAutenticazione;
    }
}
