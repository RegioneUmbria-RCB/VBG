package it.gruppoinit.pal.gp.core.features.rubrica;

public class LDAPProperties {

    private String ldapCn;
    private String ldapHost;
    private String ldapLoginType;
    private Integer ldapPort;
    private String ldapPsw;
    private String ldapSearchBaseDn;
    private String ldapUid;
    private String ldapUser;
    private String ldapUserAttrs;
    private String ldapUserDn;
    private String ldapSearchFilter;
    private boolean sslRelax;

    public LDAPProperties(String ldapCn, String ldapHost, String ldapLoginType, Integer ldapPort, String ldapPsw, String ldapSearchBaseDn,
	    String ldapUid, String ldapUser, String ldapUserAttrs, String ldapUserDn, String ldapSearchFilter, boolean sslRelax) {

	this.ldapCn = ldapCn;
	this.ldapHost = ldapHost;
	this.ldapLoginType = ldapLoginType;
	this.ldapPort = ldapPort;
	this.ldapPsw = ldapPsw;
	this.ldapSearchBaseDn = ldapSearchBaseDn;
	this.ldapUid = ldapUid;
	this.ldapUser = ldapUser;
	this.ldapUserAttrs = ldapUserAttrs;
	this.ldapUserDn = ldapUserDn;
	this.ldapSearchFilter = ldapSearchFilter;
	this.sslRelax = sslRelax;
    }

    public String getLdapCn() {

	return ldapCn;
    }

    public String getLdapHost() {

	return ldapHost;
    }

    public String getLdapLoginType() {

	return ldapLoginType;
    }

    public Integer getLdapPort() {

	return ldapPort;
    }

    public String getLdapPsw() {

	return ldapPsw;
    }

    public String getLdapSearchBaseDn() {

	return ldapSearchBaseDn;
    }

    public String getLdapUid() {

	return ldapUid;
    }

    public String getLdapUser() {

	return ldapUser;
    }

    /**
     * Può tornare con size == 0
     * 
     * @return
     */
    public String[] getLdapUserAttrs() {

	String[] ret = new String[] {};
	if (ldapUserAttrs != null) {
	    ret = ldapUserAttrs.split(";");
	}
	return ret;
    }

    public String getLdapUserDn() {

	return ldapUserDn;
    }

    public String getLdapFilter() {

	return ldapSearchFilter;
    }

    public void setLdapSearchBaseDn(String ldapSearchBaseDn) {

	this.ldapSearchBaseDn = ldapSearchBaseDn;
    }

    public void setLdapSearchFilter(String ldapSearchFilter) {

	this.ldapSearchFilter = ldapSearchFilter;
    }

    public boolean isSslRelax() {

	return sslRelax;
    }
}
