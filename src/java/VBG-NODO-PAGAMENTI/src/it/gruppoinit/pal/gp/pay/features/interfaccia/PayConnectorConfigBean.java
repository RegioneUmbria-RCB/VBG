package it.gruppoinit.pal.gp.pay.features.interfaccia;

import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfig;

@XmlRootElement(name = "pay_connector_config")
public class PayConnectorConfigBean {

    private static final String WS_URL = "ws_url";
    private static final String WS_USR = "ws_usr";
    private static final String WS_PWD = "ws_pwd";
    private static final String WS_TIMEOUT = "ws_timeout";
    private static final String URL_PORTALE_PAGAMENTI = "url_portale_pagamenti";
    private static final String PWD_PORTALE_PAGAMENTI = "pwd_portale_pagamenti";
    private static final String IN_WS_USR = "in_ws_usr";
    private static final String IN_WS_PWD = "in_ws_pwd";
    private static final String IN_WS_TIMEOUT = "in_ws_timeout";
    //
    private ParametroEtichetta wsUrl;
    private ParametroEtichetta wsUsr;
    private ParametroEtichetta wsPwd;
    private ParametroEtichetta wsTimeout;
    private ParametroEtichetta urlPortalePagamenti;
    private ParametroEtichetta pwdPortalePagamenti;
    private ParametroEtichetta inWsUsr;
    private ParametroEtichetta inWsPwd;
    private ParametroEtichetta inWsTimeout;

    public ParametroEtichetta getWsUrl() {

	return wsUrl;
    }

    public void setWsUrl(ParametroEtichetta wsUrl) {

	this.wsUrl = wsUrl;
    }

    public ParametroEtichetta getWsUsr() {

	return wsUsr;
    }

    public void setWsUsr(ParametroEtichetta wsUsr) {

	this.wsUsr = wsUsr;
    }

    public ParametroEtichetta getWsPwd() {

	return wsPwd;
    }

    public void setWsPwd(ParametroEtichetta wsPwd) {

	this.wsPwd = wsPwd;
    }

    public ParametroEtichetta getWsTimeout() {

	return wsTimeout;
    }

    public void setWsTimeout(ParametroEtichetta wsTimeout) {

	this.wsTimeout = wsTimeout;
    }

    public ParametroEtichetta getUrlPortalePagamenti() {

	return urlPortalePagamenti;
    }

    public void setUrlPortalePagamenti(ParametroEtichetta urlPortalePagamenti) {

	this.urlPortalePagamenti = urlPortalePagamenti;
    }

    public ParametroEtichetta getPwdPortalePagamenti() {

	return pwdPortalePagamenti;
    }

    public void setPwdPortalePagamenti(ParametroEtichetta pwdPortalePagamenti) {

	this.pwdPortalePagamenti = pwdPortalePagamenti;
    }

    public ParametroEtichetta getInWsUsr() {

	return inWsUsr;
    }

    public void setInWsUsr(ParametroEtichetta inWsUsr) {

	this.inWsUsr = inWsUsr;
    }

    public ParametroEtichetta getInWsPwd() {

	return inWsPwd;
    }

    public void setInWsPwd(ParametroEtichetta inWsPwd) {

	this.inWsPwd = inWsPwd;
    }

    public ParametroEtichetta getInWsTimeout() {

	return inWsTimeout;
    }

    public void setInWsTimeout(ParametroEtichetta inWsTimeout) {

	this.inWsTimeout = inWsTimeout;
    }

    public static PayConnectorConfigBean popolaPayConnectorConfigBean(PayConnectorConfig pcc) {

	PayConnectorConfigBean ret = new PayConnectorConfigBean();
	if (pcc.getWsUrl() != null) {
	    ParametroEtichetta wsURL = new ParametroEtichetta(WS_URL, "");
	    ret.setWsUrl(wsURL);
	}
	if (pcc.getWsUsr() != null) {
	    ParametroEtichetta wsUSR = new ParametroEtichetta(WS_USR, "");
	    ret.setWsUsr(wsUSR);
	}
	if (pcc.getWsPwd() != null) {
	    ParametroEtichetta wsPWD = new ParametroEtichetta(WS_PWD, "");
	    ret.setWsPwd(wsPWD);
	}
	if (pcc.getWsTimeout() != null) {
	    ParametroEtichetta wsTimeout = new ParametroEtichetta(WS_TIMEOUT, "");
	    ret.setWsTimeout(wsTimeout);
	}
	if (pcc.getUrlPortalePagamenti() != null) {
	    ParametroEtichetta wsUrlPortalePagamenti = new ParametroEtichetta(URL_PORTALE_PAGAMENTI, "");
	    ret.setUrlPortalePagamenti(wsUrlPortalePagamenti);
	}
	if (pcc.getPwdPortalePagamenti() != null) {
	    ParametroEtichetta wsPwdPortalePagamenti = new ParametroEtichetta(PWD_PORTALE_PAGAMENTI, "");
	    ret.setPwdPortalePagamenti(wsPwdPortalePagamenti);
	}
	if (pcc.getInWsUsr() != null) {
	    ParametroEtichetta wsInWsUsr = new ParametroEtichetta(IN_WS_USR, "");
	    ret.setInWsUsr(wsInWsUsr);
	}
	if (pcc.getInWsPwd() != null) {
	    ParametroEtichetta wsInWsPwd = new ParametroEtichetta(IN_WS_PWD, "");
	    ret.setInWsPwd(wsInWsPwd);
	}
	if (pcc.getInWsTimeout() != null) {
	    ParametroEtichetta wsInWsTimeout = new ParametroEtichetta(IN_WS_TIMEOUT, "");
	    ret.setInWsTimeout(wsInWsTimeout);
	}
	return ret;
    }
}
