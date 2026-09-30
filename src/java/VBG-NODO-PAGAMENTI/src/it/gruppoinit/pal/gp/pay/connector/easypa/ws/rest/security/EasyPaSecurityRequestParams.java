package it.gruppoinit.pal.gp.pay.connector.easypa.ws.rest.security;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.utils.IOAuth2Params;

public class EasyPaSecurityRequestParams implements IOAuth2Params {

    private String grantType;
    private String codiceIstituto;
    private String codiceEnte;
    private String idEnte;
    private String idDominio;

    private EasyPaSecurityRequestParams() {

	super();
    }

    public EasyPaSecurityRequestParams(String grantType, String codiceIstituto, String codiceEnte, String idEnte, String idDominio) {

	this();
	this.grantType = grantType;
	this.codiceIstituto = codiceIstituto;
	this.codiceEnte = codiceEnte;
	this.idEnte = idEnte;
	this.idDominio = idDominio;
    }

    public String getGrantType() {

	return grantType;
    }

    public String getCodiceIstituto() {

	return codiceIstituto;
    }

    public String getCodiceEnte() {

	return codiceEnte;
    }

    public String getIdEnte() {

	return idEnte;
    }

    public String getIdDominio() {

	return idDominio;
    }

    @Override
    public String toString() {

	return "[grant_type:" + StringUtils.defaultString(this.grantType) + //
		", codiceIstituto:" + StringUtils.defaultString(this.codiceIstituto) + //
		", codiceEnte:" + StringUtils.defaultString(this.codiceEnte) + //
		", idEnte:" + StringUtils.defaultString(this.idEnte) + // 
		", idDominio:" + StringUtils.defaultString(this.idDominio) + //
		"]";
    }

    @Override
    public boolean validateParams() {

	return (StringUtils.isNotBlank(this.getCodiceEnte()) && //
		StringUtils.isNotBlank(this.getCodiceIstituto()) && //
		StringUtils.isNotBlank(this.getGrantType()) && //
		StringUtils.isNotBlank(this.getIdDominio()) && // 
		StringUtils.isNotBlank(this.getIdEnte()));
    }

    @Override
    public String buildQueryString() {

	return "grant_type=" + this.getGrantType() + //
		"&codiceIstituto=" + this.getCodiceIstituto() + //
		"&codiceEnte=" + this.getCodiceEnte() + //
		"&idEnte=" + this.getIdEnte() + //
		"&idDominio=" + this.getIdDominio();
    }
}
