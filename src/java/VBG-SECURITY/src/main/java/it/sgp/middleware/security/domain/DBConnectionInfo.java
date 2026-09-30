package it.sgp.middleware.security.domain;

public class DBConnectionInfo {

    public DBConnectionInfo() {

    }

    private String alias;
    private String idComune;
    private String connectionString;
    private String dbUser;
    private String dbPassword;
    private String provider;
    private String dbOwner;
    private String dbMSName;

    public String getAlias() {

	return alias;
    }

    public void setAlias(String alias) {

	this.alias = alias;
    }

    public String getIdComune() {

	return idComune;
    }

    public void setIdComune(String idComune) {

	this.idComune = idComune;
    }

    public String getConnectionString() {

	return connectionString;
    }

    public void setConnectionString(String connectionString) {

	this.connectionString = connectionString;
    }

    public String getDbUser() {

	return dbUser;
    }

    public void setDbUser(String dbUser) {

	this.dbUser = dbUser;
    }

    public String getDbPassword() {

	return dbPassword;
    }

    public void setDbPassword(String dbPassword) {

	this.dbPassword = dbPassword;
    }

    public String getProvider() {

	return provider;
    }

    public void setProvider(String provider) {

	this.provider = provider;
    }

    public String getDbOwner() {

	return dbOwner;
    }

    public void setDbOwner(String dbOwner) {

	this.dbOwner = dbOwner;
    }

    public String getDbMSName() {

	return dbMSName;
    }

    public void setDbMSName(String dbMSName) {

	this.dbMSName = dbMSName;
    }
}
