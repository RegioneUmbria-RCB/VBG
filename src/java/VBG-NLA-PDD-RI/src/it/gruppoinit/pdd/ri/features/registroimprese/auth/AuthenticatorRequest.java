package it.gruppoinit.pdd.ri.features.registroimprese.auth;

import it.gruppoinit.pdd.utils.ConfigurazioneProtocolloSUAP;
import it.gruppoinit.pdd.utils.ConfigurazioneRichiestaIscrizioneImpresaRiSPC;

public class AuthenticatorRequest {

    private String idComuneAlias;
    private String idComune;
    private String codiceComune;
    private String tipoAutenticazione;
    private Object port;
    private String userName;
    private String password;

    public static AuthenticatorRequest fromConfigurazioneRichiestaIscrizione(String idComuneAlias, String tipoAutenticazione, Object port,
	    ConfigurazioneRichiestaIscrizioneImpresaRiSPC conf) {

	AuthenticatorRequest request = new AuthenticatorRequest();
	request.idComuneAlias = idComuneAlias;
	request.codiceComune = null;
	request.tipoAutenticazione = tipoAutenticazione;
	request.port = port;
	request.userName = conf.getIscrizioneWsAuthUserName();
	request.password = conf.getIscrizioneWsAuthPassword();
	return request;
    }

    public static AuthenticatorRequest fromConfigurazioneProtocolloSUAP(String idComuneAlias, String codiceComune, String tipoAutenticazione,
	    Object port, ConfigurazioneProtocolloSUAP conf) {

	AuthenticatorRequest request = new AuthenticatorRequest();
	request.idComuneAlias = idComuneAlias;
	request.codiceComune = codiceComune;
	request.tipoAutenticazione = tipoAutenticazione;
	request.port = port;
	request.userName = conf.getDettaglioWsAuthUserName();
	request.password = conf.getDettaglioWsAuthPassword();
	return request;
    }

    public String getIdComuneAlias() {

	return idComuneAlias;
    }

    public String getIdComune() {

	return idComune;
    }

    public String getCodiceComune() {

	return codiceComune;
    }

    public String getTipoAutenticazione() {

	return tipoAutenticazione;
    }

    public Object getPort() {

	return port;
    }

    public String getUserName() {

	return userName;
    }

    public String getPassword() {

	return password;
    }
}
