package it.gruppoinit.pal.gp.core.features.rabbitmq;

import org.springframework.security.Authentication;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class EnvVariables {

    private String idcomuneAlias;
    private String idcomune;
    private String software;
    private String token;
    private String hibernateSFKeyUrl;
    private Authentication authToken;

    private EnvVariables(String idcomuneAlias, String idcomune, String software, String token, String hibernateSFKeyUrl, Authentication authToken) {

	this();
	this.idcomuneAlias = idcomuneAlias;
	this.idcomune = idcomune;
	this.software = software;
	this.token = token;
	this.hibernateSFKeyUrl = hibernateSFKeyUrl;
	this.authToken = authToken;
    }

    public String getIdcomuneAlias() {

	return idcomuneAlias;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public String getSoftware() {

	return software;
    }

    public String getToken() {

	return token;
    }

    public String getHibernateSFKeyUrl() {

	return hibernateSFKeyUrl;
    }

    public Authentication getAuthToken() {

	return authToken;
    }

    private EnvVariables() {

	// non fa niente
	super();
    }

    public static EnvVariables fromORMHElper(Authentication authToken) {

	return new EnvVariables(ORMHelper.getIdcomuneAlias(), ORMHelper.getIdcomune(), ORMHelper.getSoftware(), ORMHelper.getToken(),
		ORMHelper.getHibernateSFKeyUrl(), authToken);
    }
}
