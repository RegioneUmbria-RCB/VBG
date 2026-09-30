package it.gruppoinit.mailservice.oggetti;

public class MailServerConfigBean {

    private String smtpHost;
    private String smtpPort;
    private String smtpUserId;
    private String smtpPassword;
    private boolean smtpUseAuth;
    private boolean smtpUseSSL;
    private boolean smtpUseTLS;
    private String smtpSender;
    // Campi da popolare in caso di READ
    private String imapHost;
    private String imapPort;
    private String imapUserId;
    private String imapPassword;
    private boolean imapUseAuth;
    private boolean imapUseSSL;
    private boolean imapUseTLS;
    private String imapSender;
    //FIXME queste proprietà dovrebbero essere recuperate dal backend. al momento le recuperiamo da deploy.properties
    private String socksHost;
    private String socksPort;
    private String socksUsername;
    private String socksPassword;

    public String getSocksHost() {

	return socksHost;
    }

    public void setSocksHost(String socksHost) {

	this.socksHost = socksHost;
    }

    public String getSocksPort() {

	return socksPort;
    }

    public void setSocksPort(String socksPort) {

	this.socksPort = socksPort;
    }

    public String getSocksUsername() {

	return socksUsername;
    }

    public void setSocksUsername(String socksUsername) {

	this.socksUsername = socksUsername;
    }

    public String getSocksPassword() {

	return socksPassword;
    }

    public void setSocksPassword(String socksPassword) {

	this.socksPassword = socksPassword;
    }

    public String getSmtpHost() {

	return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {

	this.smtpHost = smtpHost;
    }

    public String getSmtpPort() {

	return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {

	this.smtpPort = smtpPort;
    }

    public String getSmtpUserId() {

	return smtpUserId;
    }

    public void setSmtpUserId(String smtpUserId) {

	this.smtpUserId = smtpUserId;
    }

    public String getSmtpPassword() {

	return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {

	this.smtpPassword = smtpPassword;
    }

    public boolean isSmtpUseAuth() {

	return smtpUseAuth;
    }

    public void setSmtpUseAuth(boolean smtpUseAuth) {

	this.smtpUseAuth = smtpUseAuth;
    }

    public boolean isSmtpUseSSL() {

	return smtpUseSSL;
    }

    public void setSmtpUseSSL(boolean smtpUseSSL) {

	this.smtpUseSSL = smtpUseSSL;
    }

    public boolean isSmtpUseTLS() {

	return smtpUseTLS;
    }

    public void setSmtpUseTLS(boolean smtpUseTLS) {

	this.smtpUseTLS = smtpUseTLS;
    }

    public String getSmtpSender() {

	return smtpSender;
    }

    public void setSmtpSender(String smtpSender) {

	this.smtpSender = smtpSender;
    }

    public String getImapHost() {

	return imapHost;
    }

    public void setImapHost(String imapHost) {

	this.imapHost = imapHost;
    }

    public String getImapPort() {

	return imapPort;
    }

    public void setImapPort(String imapPort) {

	this.imapPort = imapPort;
    }

    public String getImapUserId() {

	return imapUserId;
    }

    public void setImapUserId(String imapUserId) {

	this.imapUserId = imapUserId;
    }

    public String getImapPassword() {

	return imapPassword;
    }

    public void setImapPassword(String imapPassword) {

	this.imapPassword = imapPassword;
    }

    public boolean isImapUseAuth() {

	return imapUseAuth;
    }

    public void setImapUseAuth(boolean imapUseAuth) {

	this.imapUseAuth = imapUseAuth;
    }

    public boolean isImapUseSSL() {

	return imapUseSSL;
    }

    public void setImapUseSSL(boolean imapUseSSL) {

	this.imapUseSSL = imapUseSSL;
    }

    public boolean isImapUseTLS() {

	return imapUseTLS;
    }

    public void setImapUseTLS(boolean imapUseTLS) {

	this.imapUseTLS = imapUseTLS;
    }

    public String getImapSender() {

	return imapSender;
    }

    public void setImapSender(String imapSender) {

	this.imapSender = imapSender;
    }
}
