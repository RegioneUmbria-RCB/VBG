package it.gruppoinit.service;

public class DeployProperties {

    private String wsTokenUrl;
    private String wsTokenUser;
    private String wsTokenPwd;
    private String stcUrl;
    private String stcUserid;
    private String stcPassword;
    private String stcMtom;
    private String stcTimeout;
    private String stcIdNodoMittente;
    private String stcIdEnteMittente;
    private String stcIdSportelloMittente;
    private String stcIdNodoDestinatario;
    private String stcIdSportelloDestinatario;
    private String wsEventiMtom;
    private String wsBackendUrl;
   
    private String idcomuneAlias;

    public DeployProperties(String wsTokenUrl, String wsTokenUser, String wsTokenPwd, String stcUrl, String stcUserid, String stcPassword,
	    String stcMtom, String stcTimeout, String stcIdNodoMittente, String stcIdEnteMittente, String stcIdSportelloMittente,
	    String stcIdNodoDestinatario, String stcIdSportelloDestinatario, String wsEventiMtom, String wsBackendUrl, 
	    String idcomuneAlias) {

	this.wsTokenUrl = wsTokenUrl;
	this.wsTokenUser = wsTokenUser;
	this.wsTokenPwd = wsTokenPwd;
	this.stcUrl = stcUrl;
	this.stcUserid = stcUserid;
	this.stcPassword = stcPassword;
	this.stcMtom = stcMtom;
	this.stcTimeout = stcTimeout;
	this.stcIdNodoMittente = stcIdNodoMittente;
	this.stcIdEnteMittente = stcIdEnteMittente;
	this.stcIdSportelloMittente = stcIdSportelloMittente;
	this.stcIdNodoDestinatario = stcIdNodoDestinatario;
	this.stcIdSportelloDestinatario = stcIdSportelloDestinatario;
	this.wsEventiMtom = wsEventiMtom;
	this.wsBackendUrl = wsBackendUrl;
	
	this.idcomuneAlias = idcomuneAlias;
    }

    public String getWsTokenUrl() {

	return wsTokenUrl;
    }

    public void setWsTokenUrl(String wsTokenUrl) {

	this.wsTokenUrl = wsTokenUrl;
    }

    public String getWsTokenUser() {

	return wsTokenUser;
    }

    public void setWsTokenUser(String wsTokenUser) {

	this.wsTokenUser = wsTokenUser;
    }

    public String getWsTokenPwd() {

	return wsTokenPwd;
    }

    public void setWsTokenPwd(String wsTokenPwd) {

	this.wsTokenPwd = wsTokenPwd;
    }

    public String getStcUrl() {

	return stcUrl;
    }

    public void setStcUrl(String stcUrl) {

	this.stcUrl = stcUrl;
    }

    public String getStcUserid() {

	return stcUserid;
    }

    public void setStcUserid(String stcUserid) {

	this.stcUserid = stcUserid;
    }

    public String getStcPassword() {

	return stcPassword;
    }

    public void setStcPassword(String stcPassword) {

	this.stcPassword = stcPassword;
    }

    public String getStcMtom() {

	return stcMtom;
    }

    public void setStcMtom(String stcMtom) {

	this.stcMtom = stcMtom;
    }

    public String getStcTimeout() {

	return stcTimeout;
    }

    public void setStcTimeout(String stcTimeout) {

	this.stcTimeout = stcTimeout;
    }

    public String getStcIdNodoMittente() {

	return stcIdNodoMittente;
    }

    public void setStcIdNodoMittente(String stcIdNodoMittente) {

	this.stcIdNodoMittente = stcIdNodoMittente;
    }

    public String getStcIdEnteMittente() {

	return stcIdEnteMittente;
    }

    public void setStcIdEnteMittente(String stcIdEnteMittente) {

	this.stcIdEnteMittente = stcIdEnteMittente;
    }

    public String getStcIdSportelloMittente() {

	return stcIdSportelloMittente;
    }

    public void setStcIdSportelloMittente(String stcIdSportelloMittente) {

	this.stcIdSportelloMittente = stcIdSportelloMittente;
    }
    public String getStcIdNodoDestinatario() {

	return stcIdNodoDestinatario;
    }

    public void setStcIdNodoDestinatario(String stcIdNodoDestinatario) {

	this.stcIdNodoDestinatario = stcIdNodoDestinatario;
    }

    //    public String getStcIdSportelloDestinatario() {
    //
    //	return stcIdSportelloDestinatario;
    //    }
    //
    //    public void setStcIdSportelloDestinatario(String stcIdSportelloDestinatario) {
    //
    //	this.stcIdSportelloDestinatario = stcIdSportelloDestinatario;
    //    }
    public String getWsEventiMtom() {

	return wsEventiMtom;
    }

    public void setWsEventiMtom(String wsEventiMtom) {

	this.wsEventiMtom = wsEventiMtom;
    }

    public String getWsBackendUrl() {

	return wsBackendUrl;
    }

    public void setWsBackendUrl(String wsBackendUrl) {

	this.wsBackendUrl = wsBackendUrl;
    }

   

    //    public String getIdcomuneAlias() {
    //
    //	return idcomuneAlias;
    //    }

    public void setIdcomuneAlias(String idcomuneAlias) {

	this.idcomuneAlias = idcomuneAlias;
    }
}
