package it.gruppoinit.pal.gp.pay.connector.openweb;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.utils.IOAuth2Params;

public class OpenWebSecurityParams implements IOAuth2Params {

    private String userName;
    private String password;
    private String grantType;

    public OpenWebSecurityParams(String userName, String password, String grantType) {

	super();
	this.userName = userName;
	this.password = password;
	this.grantType = grantType;
    }

    public String getUserName() {

	return userName;
    }

    public String getPassword() {

	return password;
    }

    public String getGrantType() {

	return grantType;
    }

    @Override
    public boolean validateParams() {

	return (StringUtils.isNotBlank(this.getPassword()) && //
		StringUtils.isNotBlank(this.getUserName()) && //
		StringUtils.isNotBlank(this.getGrantType()));
    }

    @Override
    public String buildQueryString() {

	return "grant_type=" +
		this.getGrantType() + //
		"&username=" +
		this.getUserName() + //
		"&password=" +
		this.getPassword();
    }

    @Override
    public String toString() {

	return "[grant_type:" +
		StringUtils.defaultString(this.grantType) + //
		", username:" +
		StringUtils.defaultString(this.userName) + //
		", password:" +
		StringUtils.defaultString(this.password) + //
		"]";
    }
}
