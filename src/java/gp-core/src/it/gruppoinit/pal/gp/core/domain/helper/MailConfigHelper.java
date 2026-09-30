package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.MailConfig;

import java.util.ArrayList;
import java.util.List;

public class MailConfigHelper {

    
    private String codiceSoftware;
    private String software;
    private String codiceComune;
    private String comune;
    private List<MailConfig> mailConfigs = new ArrayList<MailConfig>();

    public String getCodiceSoftware() {

	return codiceSoftware;
    }

    public void setCodiceSoftware(String codiceSoftware) {

	this.codiceSoftware = codiceSoftware;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getCodiceComune() {

	return codiceComune;
    }

    public void setCodiceComune(String codiceComune) {

	this.codiceComune = codiceComune;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public List<MailConfig> getMailConfigs() {

	return mailConfigs;
    }

    public void setMailConfigs(List<MailConfig> mailConfigs) {

	this.mailConfigs = mailConfigs;
    }
}
